package com.starpilot.starview

import android.content.Context
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.File
import java.io.InputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.concurrent.TimeUnit
import java.util.zip.GZIPInputStream
import kotlin.math.floor

/**
 * 2.26.0: offline street maps on the tablet - the same OpenStreetMap files StarPilot's mapd downloads for the comma
 * (https://map-data.pfeifer.dev, rebuilt every few days), downloaded and kept here, so the comma does no work for it.
 *
 * Server layout (pfeiferj/mapd, MIT): offline/<lat>/<lon>.tar.gz per 2 x 2 deg square (south-west corner, even degrees),
 * holding one file per 0.25 deg box, "minLat_minLon_maxLat_maxLon" (%f), each a packed Cap'n Proto `Offline` message:
 *   Offline { minLat, minLon, maxLat, maxLon :Float64; ways :List(Way); overlap :Float64 }
 *   Way { name :Text; ...; nodes :List(Coordinates); lanes :UInt8; oneWay :Bool; id :Int64; highwayClass :enum; ... }
 * One-way streets are stored in their legal direction (mapd reverses oneway=-1). Only real streets: motorway down to
 * residential / living_street (no service roads, no sidewalks).
 * Stored here as files/mapd/<lat>/<lon>/<box files> (+ ".done" with the download time).
 */
object MapdMaps {
  private const val TAG = "StarView/Maps"
  const val GROUP = 2
  const val BOX = 0.25
  private const val URL = "https://map-data.pfeifer.dev/offline/%d/%d.tar.gz"
  /** highwayClass enum (cereal/offline/offline.capnp) -> OSM highway tag. */
  val CLASSES = arrayOf("unknown", "motorway", "motorway_link", "trunk", "trunk_link", "primary", "primary_link", "secondary",
    "secondary_link", "tertiary", "tertiary_link", "unclassified", "residential", "living_street")

  class W(val id: Long, val name: String, val kind: String, val lanes: Int, val oneway: Boolean, val lat: DoubleArray, val lon: DoubleArray)
  /** rows: mapd's download rows (lat, lon from, lon to - 2 deg steps) where it has them (Alaska across the date line,
   *  New York's empty squares left out), else the bounding box rectangle. */
  class State(val code: String, val name: String, val minLat: Double, val minLon: Double, val maxLat: Double, val maxLon: Double, val rows: List<IntArray> = emptyList())

  // ------------------------------------------------------------------ packed Cap'n Proto, just enough for Offline
  fun unpack(src: ByteArray): ByteArray {
    var out = ByteArray(maxOf(64, src.size * 3)); var o = 0
    fun need(n: Int) { if (o + n > out.size) out = out.copyOf(maxOf(out.size * 2, o + n)) }
    var i = 0
    while (i < src.size) {
      val tag = src[i++].toInt() and 0xff
      need(8)
      for (b in 0 until 8) out[o + b] = if (tag and (1 shl b) != 0) src[i++] else 0
      o += 8
      if (tag == 0) { val n = (src[i++].toInt() and 0xff) * 8; need(n); java.util.Arrays.fill(out, o, o + n, 0); o += n }
      else if (tag == 0xff) { val n = (src[i++].toInt() and 0xff) * 8; need(n); System.arraycopy(src, i, out, o, n); i += n; o += n }
    }
    return out.copyOf(o)
  }

  private class Msg(raw: ByteArray) {
    val b: ByteBuffer = ByteBuffer.wrap(raw).order(ByteOrder.LITTLE_ENDIAN)
    val seg: IntArray                                                  // first word of each segment
    init {
      val n = b.getInt(0) + 1
      seg = IntArray(n); var w = (4 * (n + 1) + 7) / 8
      for (k in 0 until n) { seg[k] = w; w += b.getInt(4 + 4 * k) }
    }
    fun w(s: Int, i: Int): Long = b.getLong((seg[s] + i) * 8)
  }
  /** Where a pointer leads: segment, the pointer describing the object (a far pointer's landing pad resolved), first word. */
  private class Ref(val s: Int, val p: Long, val at: Int)
  private fun off(p: Long) = p.toInt() shr 2
  private fun follow(m: Msg, s: Int, at: Int): Ref? {
    val p = m.w(s, at); if (p == 0L) return null
    if ((p and 3L) != 2L) return Ref(s, p, at + 1 + off(p))
    val ts = (p ushr 32).toInt(); val pos = ((p ushr 3) and 0x1FFFFFFFL).toInt()
    if ((p and 4L) == 0L) { val q = m.w(ts, pos); return Ref(ts, q, pos + 1 + off(q)) }
    val pad = m.w(ts, pos); val tag = m.w(ts, pos + 1)                 // double far: pad -> content, tag describes it
    return Ref((pad ushr 32).toInt(), tag, ((pad ushr 3) and 0x1FFFFFFFL).toInt())
  }
  private class Lst(val s: Int, val start: Int, val count: Int, val dw: Int, val pc: Int)
  private fun compositeList(m: Msg, r: Ref?): Lst? {
    if (r == null || (r.p and 3L) != 1L || ((r.p ushr 32) and 7L).toInt() != 7) return null
    val tag = m.w(r.s, r.at)
    return Lst(r.s, r.at + 1, off(tag), ((tag ushr 32) and 0xffff).toInt(), ((tag ushr 48) and 0xffff).toInt())
  }
  private fun text(m: Msg, r: Ref?): String {
    if (r == null || (r.p and 3L) != 1L || ((r.p ushr 32) and 7L).toInt() != 2) return ""
    val n = (r.p ushr 35).toInt() - 1; if (n <= 0) return ""
    val a = ByteArray(n); val pos = (m.seg[r.s] + r.at) * 8
    for (k in 0 until n) a[k] = m.b.get(pos + k)
    return String(a, Charsets.UTF_8)
  }

  /** All streets in one box file. */
  fun readBox(packed: ByteArray): List<W> {
    val m = Msg(unpack(packed))
    val root = follow(m, 0, 0) ?: return emptyList()
    val rdw = ((root.p ushr 32) and 0xffff).toInt()
    val ways = compositeList(m, follow(m, root.s, root.at + rdw)) ?: return emptyList()
    val out = ArrayList<W>(ways.count)
    for (k in 0 until ways.count) {
      val e = ways.start + k * (ways.dw + ways.pc); val s = ways.s; val dw = ways.dw
      if (ways.pc < 3) continue
      val nodes = compositeList(m, follow(m, s, e + dw + 2)) ?: continue
      if (nodes.count < 2 || nodes.dw < 2) continue
      val lat = DoubleArray(nodes.count); val lon = DoubleArray(nodes.count)
      for (j in 0 until nodes.count) { val ne = nodes.start + j * (nodes.dw + nodes.pc); lat[j] = Double.fromBits(m.w(nodes.s, ne)); lon[j] = Double.fromBits(m.w(nodes.s, ne + 1)) }
      val base = (m.seg[s] + e) * 8
      val lanes = if (dw > 5) m.b.get(base + 40).toInt() and 0xff else 0
      val oneway = dw > 5 && (m.b.get(base + 41).toInt() and 1) != 0
      val cls = if (dw > 5) (m.b.getShort(base + 42).toInt() and 0xffff) else 0
      val id = if (dw > 9) m.w(s, e + 9) else 0L
      out.add(W(id, text(m, follow(m, s, e + dw)).ifEmpty { text(m, follow(m, s, e + dw + 1)) }, CLASSES.getOrElse(cls) { "unknown" }, lanes, oneway, lat, lon))
    }
    return out
  }

  // ------------------------------------------------------------------ files on the tablet
  private var dir: File? = null
  private var prefs: android.content.SharedPreferences? = null
  var states: List<State> = emptyList(); private set

  fun init(ctx: Context) {
    if (dir != null) return
    dir = File(ctx.filesDir, "mapd").apply { mkdirs() }
    prefs = ctx.applicationContext.getSharedPreferences("starview_maps", Context.MODE_PRIVATE)
    states = try {
      val o = JSONObject(ctx.assets.open("maps/us_states.json").bufferedReader().readText())
      o.keys().asSequence().map { k -> val s = o.getJSONObject(k); val b = s.getJSONObject("bounding_box")
        val rows = s.optJSONArray("download_rows")?.let { a -> (0 until a.length()).map { i -> val r = a.getJSONArray(i); intArrayOf(r.getInt(0), r.getInt(1), r.getInt(2)) } } ?: emptyList()
        State(k, s.getString("full_name"), b.getDouble("min_lat"), b.getDouble("min_lon"), b.getDouble("max_lat"), b.getDouble("max_lon"), rows) }.sortedBy { it.name }.toList()
    } catch (e: Exception) { Log.e(TAG, "states", e); emptyList() }
  }

  fun selected(): Set<String> = prefs?.getStringSet("states", emptySet()) ?: emptySet()
  private fun setSelected(s: Set<String>) { prefs?.edit()?.putStringSet("states", HashSet(s))?.apply() }

  /** The 2-degree squares a state needs (same rule as mapd's adjustedBounds). */
  fun groupsOf(st: State): List<Pair<Int, Int>> {
    if (st.rows.isNotEmpty()) return st.rows.flatMap { r -> (r[1] until r[2] step GROUP).map { r[0] to it } }
    val la0 = floor(st.minLat / GROUP).toInt() * GROUP; val lo0 = floor(st.minLon / GROUP).toInt() * GROUP
    var la1 = floor(st.maxLat / GROUP).toInt() * GROUP; var lo1 = floor(st.maxLon / GROUP).toInt() * GROUP
    if (st.maxLat > la1) la1 += GROUP; if (st.maxLon > lo1) lo1 += GROUP
    val out = ArrayList<Pair<Int, Int>>()
    var a = la0; while (a < la1) { var o = lo0; while (o < lo1) { out.add(a to o); o += GROUP }; a += GROUP }
    return out
  }
  private fun groupDir(g: Pair<Int, Int>) = File(dir, "${g.first}/${g.second}")
  private val doneCache = java.util.concurrent.ConcurrentHashMap<Pair<Int, Int>, Long>()
  fun groupDone(g: Pair<Int, Int>): Long = doneCache.getOrPut(g) { try { File(groupDir(g), ".done").readText().trim().toLong() } catch (_: Exception) { 0L } }
  fun stateBytes(st: State): Long = groupsOf(st).sumOf { g -> groupDir(g).listFiles()?.sumOf { it.length() } ?: 0L }
  /** 0 not downloaded, 1 partly, 2 complete; and the oldest download time. */
  fun stateStatus(st: State): Pair<Int, Long> {
    val t = groupsOf(st).map { groupDone(it) }
    val n = t.count { it > 0 }
    return Pair(if (n == 0) 0 else if (n == t.size) 2 else 1, t.filter { it > 0 }.minOrNull() ?: 0L)
  }

  /** The box file for a position, if its square is downloaded (null = not downloaded; empty file list = no streets there). */
  fun boxFileFor(lat: Double, lon: Double): Pair<Boolean, File?> {
    val g = Pair(floor(lat / GROUP).toInt() * GROUP, floor(lon / GROUP).toInt() * GROUP)
    if (dir == null || groupDone(g) == 0L) return Pair(false, null)
    val la = floor(lat / BOX) * BOX; val lo = floor(lon / BOX) * BOX
    val f = File(groupDir(g), String.format(java.util.Locale.US, "%f_%f_%f_%f", la, lo, la + BOX, lo + BOX))
    return Pair(true, if (f.isFile) f else null)
  }

  // ------------------------------------------------------------------ downloads (one at a time, in the background)
  @Volatile var busy = ""; private set              // "" idle, else what is going on
  @Volatile var lastError = ""; private set
  var onChange: (() -> Unit)? = null
  private val http by lazy { OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).build() }
  private val worker = java.util.concurrent.Executors.newSingleThreadExecutor { r -> Thread(r, "starview-maps").apply { isDaemon = true; priority = Thread.MIN_PRIORITY } }
  @Volatile private var cancel = false

  private fun changed() { try { onChange?.invoke() } catch (_: Exception) {} }

  /** Download (or with force, refresh) every square a state needs. */
  fun download(st: State, force: Boolean = false) {
    setSelected(selected() + st.code)
    worker.execute {
      cancel = false; lastError = ""
      val gs = groupsOf(st).filter { force || groupDone(it) == 0L }
      for ((i, g) in gs.withIndex()) {
        if (cancel) break
        busy = "${st.name}: area ${i + 1} of ${gs.size}"; changed()
        try { fetchGroup(g) { mb -> busy = "${st.name}: area ${i + 1} of ${gs.size}, ${"%.0f".format(mb)} MB"; changed() } }
        catch (e: Exception) { Log.w(TAG, "group $g", e); lastError = "${st.name}: ${e.message?.take(60)}" }
      }
      busy = ""; changed()
    }
  }
  fun cancelAll() { cancel = true }

  /** Forget a state; squares another kept state still needs stay. */
  fun delete(st: State) {
    val keep = selected() - st.code; setSelected(keep)
    worker.execute {
      val needed = states.filter { it.code in keep }.flatMap { groupsOf(it) }.toSet()
      for (g in groupsOf(st)) if (g !in needed) { groupDir(g).deleteRecursively(); doneCache.remove(g) }
      changed()
    }
  }

  private fun fetchGroup(g: Pair<Int, Int>, progress: (Double) -> Unit) {
    val target = groupDir(g); val tmp = File(dir, "tmp_${g.first}_${g.second}")
    tmp.deleteRecursively(); tmp.mkdirs()
    val req = Request.Builder().url(String.format(java.util.Locale.US, URL, g.first, g.second)).header("User-Agent", "StarView (openpilot tablet display)").build()
    http.newCall(req).execute().use { r ->
      if (r.code == 404) { target.deleteRecursively(); target.mkdirs(); File(target, ".done").writeText(System.currentTimeMillis().toString()); tmp.deleteRecursively(); doneCache.remove(g); return }   // sea / no roads
      if (!r.isSuccessful) throw java.io.IOException("HTTP ${r.code}")
      val counted = object : java.io.FilterInputStream(r.body!!.byteStream()) {
        var n = 0L; var last = 0L
        override fun read(b: ByteArray, off: Int, len: Int): Int { val k = super.read(b, off, len); if (k > 0) { n += k; if (n - last > 2_000_000) { last = n; progress(n / 1e6) } }; return k }
      }
      untar(GZIPInputStream(counted, 65536), tmp)
    }
    File(tmp, ".done").writeText(System.currentTimeMillis().toString())
    target.parentFile?.mkdirs(); target.deleteRecursively(); doneCache.remove(g)
    if (!tmp.renameTo(target)) throw java.io.IOException("could not store the map files")
    doneCache.remove(g)
  }

  /** Minimal tar reader: regular files only, stored flat (the archive's offline/<lat>/<lon>/ prefix dropped). */
  fun untar(inp: InputStream, into: File) {
    val h = ByteArray(512); val buf = ByteArray(65536)
    fun readFully(a: ByteArray, n: Int): Boolean { var o = 0; while (o < n) { val k = inp.read(a, o, n - o); if (k < 0) return false; o += k }; return true }
    while (readFully(h, 512)) {
      if (h.all { it.toInt() == 0 }) break
      val name = String(h, 0, 100, Charsets.US_ASCII).trimEnd('\u0000')
      val prefix = String(h, 345, 155, Charsets.US_ASCII).trimEnd('\u0000')
      val size = String(h, 124, 12, Charsets.US_ASCII).trim('\u0000', ' ').ifEmpty { "0" }.toLong(8)
      val type = h[156].toInt().toChar()
      val full = if (prefix.isNotEmpty()) "$prefix/$name" else name
      val base = full.substringAfterLast('/')
      val keep = (type == '0' || type == '\u0000') && base.isNotEmpty() && !base.startsWith(".") && base.matches(Regex("[-0-9._]+"))
      var left = size
      val out = if (keep) java.io.BufferedOutputStream(File(into, base).outputStream()) else null
      try {
        while (left > 0) { val k = inp.read(buf, 0, minOf(buf.size.toLong(), left).toInt()); if (k < 0) throw java.io.EOFException("tar cut short"); out?.write(buf, 0, k); left -= k }
      } finally { out?.close() }
      val pad = ((512 - size % 512) % 512).toInt(); if (pad > 0 && !readFully(h, pad)) break
    }
  }
}
