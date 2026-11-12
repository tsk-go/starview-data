package com.starpilot.starview

import android.content.Context
import android.util.Log
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.File
import java.util.Locale
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.hypot
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToLong

/**
 * 2.87.0: the road lane by lane, built here from OpenStreetMap's lane tags - the way osm2streets does it - so it works
 * on roads never driven before. Tags read: lanes, lanes:forward / backward / both_ways, turn:lanes (and :forward /
 * :backward), width, parking, placement, oneway, layer / bridge / tunnel, name / ref.
 *
 * Streets come in 0.01 deg tiles (same grid as OsmRoads): OsmRoads' own Overpass tile when it has one (it keeps every
 * tag), else our own download of only the drivable roads, kept in files/lanes/. Everything runs on one background
 * thread: download, read, build. The build covers the roads within 1.2 km of the car and is redone after 400 m or when
 * a new tile arrives; the screen reads [built] (never changed after it is published).
 *
 * Frame: metres east (x) / north (y) of [Built.lat0], [Built.lon0]. Lanes are listed left to right looking along the
 * way. US conventions (right-hand traffic, yellow on the left of a one-way divided road).
 */
object LaneMap {
  private const val TAG = "StarView/Lanes"
  const val TILE = 0.01
  private const val KY = 110540.0
  private const val BUILD_R = 1200.0
  private const val REBUILD_AFTER = 400.0
  private const val TILE_R = 700.0
  private const val REFRESH_MS = 60L * 24 * 3600 * 1000
  private const val TAPER = 150.0
  private const val PARK_W = 2.4
  private const val WIDEN = 80.0

  // line styles
  const val EDGE_WHITE = 0
  const val EDGE_YELLOW = 1
  const val DASH_WHITE = 2
  const val SOLID_WHITE = 3
  const val DOUBLE_YELLOW = 4
  const val CURB = 5
  fun isEdge(style: Int) = style == EDGE_WHITE || style == EDGE_YELLOW || style == CURB

  val DRIVE = setOf("motorway", "motorway_link", "trunk", "trunk_link", "primary", "primary_link", "secondary",
    "secondary_link", "tertiary", "tertiary_link", "unclassified", "residential")
  val FAST = setOf("motorway", "motorway_link", "trunk", "trunk_link")
  private val MAJOR = setOf("motorway", "motorway_link", "trunk", "trunk_link", "primary", "primary_link", "secondary",
    "secondary_link")
  private val WIDTH = mapOf("motorway" to 3.65, "motorway_link" to 3.65, "trunk" to 3.6, "trunk_link" to 3.6,
    "primary" to 3.3, "primary_link" to 3.4, "secondary" to 3.2, "secondary_link" to 3.3, "tertiary" to 3.1,
    "tertiary_link" to 3.2, "unclassified" to 3.0, "residential" to 3.0)
  private val ONEWAY_LANES = mapOf("motorway" to 2, "trunk" to 2, "primary" to 2)
  private val KEEP_TAGS = setOf("highway", "name", "ref", "oneway", "junction", "lanes", "lanes:forward",
    "lanes:backward", "lanes:both_ways", "turn:lanes", "turn:lanes:forward", "turn:lanes:backward", "width",
    "parking:both", "parking:left", "parking:right", "parking:lane:both", "parking:lane:left", "parking:lane:right",
    "placement", "layer", "bridge", "tunnel", "destination", "destination:ref", "destination:lanes")
  private val URLS = listOf("https://overpass-api.de/api/interpreter", "https://overpass.private.coffee/api/interpreter",
    "https://overpass.kumi.systems/api/interpreter")

  /** One OSM way as downloaded. */
  class OsmWay(val id: Long, val tags: Map<String, String>, val lat: DoubleArray, val lon: DoubleArray)

  class MLine(val style: Int, val x: FloatArray, val y: FloatArray) {
    /** distance along the line at each point (dashes stay put on the road) */
    val s: FloatArray = FloatArray(x.size).also { for (i in 1 until x.size) it[i] = it[i - 1] + hypot(x[i] - x[i - 1], y[i] - y[i - 1]) }
  }
  /** a lane's centre line, in its direction of travel */
  class MLane(val dir: Int, val turn: String, val x: FloatArray, val y: FloatArray)
  class MArrow(val x: Float, val y: Float, val heading: Float, val turn: String)

  class BWay(
    val id: Long, val hw: String, val name: String, val oneway: Boolean, val layer: Int, val width: Double,
    val px: DoubleArray, val py: DoubleArray, val keys: LongArray,
    var lines: List<MLine>, val left: FloatArray, val leftY: FloatArray, val right: FloatArray, val rightY: FloatArray,
    val lanes: List<MLane>, val arrows: List<MArrow>,
    /** which way traffic may go along the points: 1 = first to last, -1 = last to first, 0 = both */
    val flow: Int,
    val minX: Double, val minY: Double, val maxX: Double, val maxY: Double,
  ) {
    fun near(x: Double, y: Double, r: Double) = x > minX - r && x < maxX + r && y > minY - r && y < maxY + r
    /** road class: motorway 10 .. residential 4, a ramp half a step below its road */
    val rank: Double get() = rankOf(hw)
  }

  fun rankOf(hw: String): Double {
    val base = when (hw.removeSuffix("_link")) {
      "motorway" -> 10.0; "trunk" -> 9.0; "primary" -> 8.0; "secondary" -> 7.0; "tertiary" -> 6.0; "unclassified" -> 5.0
      else -> 4.0
    }
    return if (hw.endsWith("_link")) base - 0.5 else base
  }

  class Built(val lat0: Double, val lon0: Double, val kx: Double, val cx: Double, val cy: Double, val ways: List<BWay>,
              /** node key -> (way index shl 20) or vertex index, for every way through that point */
              val at: Map<Long, IntArray>, val atMs: Long) {
    fun xy(lat: Double, lon: Double): DoubleArray = doubleArrayOf((lon - lon0) * kx, (lat - lat0) * KY)
  }

  @Volatile var built: Built? = null
    private set
  @Volatile var status = "no position yet"
    private set
  @Volatile var enabled = true

  private var dir: File? = null
  private var osmDir: File? = null
  private val raw = ConcurrentHashMap<Long, OsmWay>()
  private val tiles = ConcurrentHashMap<String, Int>()         // 0 loading, 1 loaded, 2 failed
  private val failedAt = ConcurrentHashMap<String, Long>()
  private val tileWays = ConcurrentHashMap<String, LongArray>()
  private val worker = Executors.newSingleThreadExecutor { r -> Thread(r, "lanes").apply { priority = Thread.MIN_PRIORITY + 1 } }
  private val http by lazy { OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS).readTimeout(40, TimeUnit.SECONDS).build() }
  @Volatile private var lastPosMs = 0L
  @Volatile private var buildLat = Double.NaN
  @Volatile private var buildLon = Double.NaN
  @Volatile private var buildQueued = false
  @Volatile private var dirty = false
  @Volatile private var lastLat = Double.NaN
  @Volatile private var lastLon = Double.NaN

  fun init(ctx: Context) {
    if (dir == null) {
      dir = File(ctx.filesDir, "lanes").apply { mkdirs() }
      osmDir = File(ctx.filesDir, "osm")
    }
  }

  /** For tests: tiles already on disk, no downloads. */
  @Volatile var offline = false

  /** The car's position (any thread, any rate; works at most once a second). */
  fun onPos(lat: Double, lon: Double, bearingDeg: Double, speed: Double) {
    if (!enabled || lat.isNaN() || lon.isNaN() || (lat == 0.0 && lon == 0.0)) return
    val now = System.currentTimeMillis()
    if (now - lastPosMs < 1000) return
    lastPosMs = now
    lastLat = lat; lastLon = lon
    val kx = 111320.0 * cos(Math.toRadians(lat))
    // tiles around the car and around a point ahead (more ahead at speed)
    val ahead = min(900.0, 200.0 + speed * 25.0)
    val b = Math.toRadians(if (bearingDeg.isNaN()) 0.0 else bearingDeg)
    val pts = listOf(doubleArrayOf(lat, lon), doubleArrayOf(lat + ahead * cos(b) / KY, lon + ahead * kotlin.math.sin(b) / kx))
    for (p in pts) {
      val y0 = floor((p[0] - TILE_R / KY) / TILE).toInt(); val y1 = floor((p[0] + TILE_R / KY) / TILE).toInt()
      val x0 = floor((p[1] - TILE_R / kx) / TILE).toInt(); val x1 = floor((p[1] + TILE_R / kx) / TILE).toInt()
      for (ty in y0..y1) for (tx in x0..x1) want(ty, tx)
    }
    val bl = built
    val far = buildLat.isNaN() || dist(lat, lon, buildLat, buildLon) > REBUILD_AFTER
    if ((far || dirty || bl == null) && !buildQueued) {
      buildQueued = true
      worker.execute { try { rebuild() } catch (e: Throwable) { Log.w(TAG, "build", e) } finally { buildQueued = false } }
    }
  }

  private fun dist(la1: Double, lo1: Double, la2: Double, lo2: Double): Double =
    hypot((lo1 - lo2) * 111320.0 * cos(Math.toRadians(la1)), (la1 - la2) * KY)

  private fun want(ty: Int, tx: Int) {
    val k = "${ty}_$tx"
    when (tiles[k]) {
      0, 1 -> return
      2 -> if (System.currentTimeMillis() - (failedAt[k] ?: 0L) < 60_000) return
    }
    tiles[k] = 0
    worker.execute { load(ty, tx, k) }
  }

  private fun load(ty: Int, tx: Int, k: String) {
    try {
      val text = readTile(ty, tx, k)
      if (text == null) { tiles[k] = 2; failedAt[k] = System.currentTimeMillis(); return }
      val ids = parse(text)
      tileWays[k] = ids
      tiles[k] = 1
      dirty = true
      status = "${tiles.count { it.value == 1 }} tiles, ${raw.size} roads"
    } catch (e: Throwable) {
      Log.w(TAG, "tile $k: $e")
      tiles[k] = 2; failedAt[k] = System.currentTimeMillis()
      status = "download failed: ${(e.message ?: e.toString()).take(40)}"
    }
  }

  private fun readTile(ty: Int, tx: Int, k: String): String? {
    osmDir?.let { od -> File(od, "$k.json").takeIf { it.isFile && it.length() > 20 }?.let { return it.readText() } }
    val d = dir ?: return null
    val f = File(d, "$k.json")
    val have = f.isFile && f.length() > 20
    if (have && (offline || System.currentTimeMillis() - f.lastModified() < REFRESH_MS)) return f.readText()
    if (offline) return null
    status = "downloading lanes…"
    val s = ty * TILE; val w = tx * TILE
    val bb = String.format(Locale.US, "%.4f,%.4f,%.4f,%.4f", s, w, s + TILE, w + TILE)
    val q = "[out:json][timeout:30];way[\"highway\"~\"^(motorway|trunk|primary|secondary|tertiary)(_link)?$|^(unclassified|residential)$\"]($bb);out tags geom;"
    var err = ""
    for (u in URLS) {
      try {
        val req = Request.Builder().url(u).header("User-Agent", "StarView/2.87 (openpilot tablet display)")
          .post(FormBody.Builder().add("data", q).build()).build()
        http.newCall(req).execute().use { r ->
          val body = r.body?.string() ?: ""
          if (r.isSuccessful && body.trimStart().startsWith("{")) {
            val tmp = File(f.path + ".tmp"); tmp.writeText(body); tmp.renameTo(f)
            return body
          }
          err = "HTTP ${r.code}"
        }
      } catch (e: Exception) { err = e.message ?: e.toString() }
    }
    if (have) return f.readText()      // an old copy beats nothing
    throw java.io.IOException(err)
  }

  /** Overpass JSON (out tags geom) -> raw ways; returns the ids it held. */
  fun parse(text: String): LongArray {
    val els = JSONObject(text).optJSONArray("elements") ?: return LongArray(0)
    val ids = ArrayList<Long>()
    for (i in 0 until els.length()) {
      val o = els.optJSONObject(i) ?: continue
      if (o.optString("type") != "way") continue
      val t = o.optJSONObject("tags") ?: continue
      val hw = t.optString("highway")
      if (hw !in DRIVE || t.optString("area") == "yes") continue
      val g = o.optJSONArray("geometry") ?: continue
      val la = ArrayList<Double>(); val lo = ArrayList<Double>()
      for (j in 0 until g.length()) {
        val p = g.optJSONObject(j) ?: continue
        la.add(p.optDouble("lat")); lo.add(p.optDouble("lon"))
      }
      if (la.size < 2) continue
      val tags = HashMap<String, String>()
      for (key in t.keys()) if (key in KEEP_TAGS) tags[key] = t.optString(key)
      val id = o.optLong("id")
      raw[id] = OsmWay(id, tags, la.toDoubleArray(), lo.toDoubleArray())
      ids.add(id)
    }
    return ids.toLongArray()
  }

  fun nodeKey(lat: Double, lon: Double): Long = (lat * 1e7).roundToLong() * 3_600_000_001L + (lon * 1e7).roundToLong()

  // ------------------------------------------------------------------------------------------------ the build
  /** Tests: build around this spot now, from the ways already read ([parse]). */
  fun buildNow(lat: Double, lon: Double) { lastLat = lat; lastLon = lon; rebuild() }

  /** Rebuild around the last position (the worker thread). */
  fun rebuild() {
    val lat = lastLat; val lon = lastLon
    if (lat.isNaN()) return
    dirty = false
    // forget tiles far away
    for ((k, ids) in tileWays) {
      val p = k.split('_')
      val cla = (p[0].toInt() + 0.5) * TILE; val clo = (p[1].toInt() + 0.5) * TILE
      if (dist(lat, lon, cla, clo) > 4000) { tileWays.remove(k); tiles.remove(k); for (id in ids) raw.remove(id) }
    }
    val old = built
    val (lat0, lon0) = if (old != null && dist(lat, lon, old.lat0, old.lon0) < 40_000) old.lat0 to old.lon0 else lat to lon
    val b = build(raw.values, lat0, lon0, lat, lon, BUILD_R)
    built = b
    buildLat = lat; buildLon = lon
    status = "${tiles.count { it.value == 1 }} tiles, ${b.ways.size} roads built"
  }

  class Lane(val dir: Int, val turn: String, val w: Double)
  class Layout(val lanes: List<Lane>, val parkL: Double, val parkR: Double, val oneway: Boolean)

  private fun num(v: String?): Double? {
    if (v.isNullOrBlank()) return null
    return v.trim().split(' ')[0].replace("m", "").toDoubleOrNull()
  }

  private fun turns(v: String?, n: Int): List<String> {
    if (v.isNullOrEmpty()) return List(n) { "" }
    val parts = v.split('|')
    if (parts.size == n || n == 0) return parts
    return (parts + List(n) { "" }).take(n)
  }

  private fun parkW(v: String?) = if (v in setOf("lane", "parallel", "street_side", "half_on_kerb")) PARK_W else 0.0

  fun layout(t: Map<String, String>): Layout {
    val hw = t["highway"] ?: "residential"
    val w = WIDTH[hw] ?: 3.0
    val ow = t["oneway"]
    val oneway = ow in setOf("yes", "1", "true", "-1") || hw == "motorway" || hw == "motorway_link" || t["junction"] == "roundabout"
    val park = t["parking:both"] ?: t["parking:lane:both"] ?: ""
    val parkL = parkW(t["parking:left"] ?: t["parking:lane:left"] ?: park)
    val parkR = parkW(t["parking:right"] ?: t["parking:lane:right"] ?: park)
    var lanes: List<Lane>
    if (oneway) {
      val tl = t["turn:lanes"] ?: ""
      val n = (num(t["lanes"])?.toInt() ?: 0).takeIf { it > 0 }
        ?: (if (tl.isNotEmpty()) tl.split('|').size else (ONEWAY_LANES[hw] ?: 1))
      lanes = turns(tl, n).map { Lane(1, it, w) }
      if (ow == "-1") lanes = turns(tl, n).map { Lane(-1, it, w) }.reversed()
    } else {
      val both = num(t["lanes:both_ways"])?.toInt() ?: 0
      var nf = num(t["lanes:forward"]); var nb = num(t["lanes:backward"])
      val tf = t["turn:lanes:forward"] ?: ""; val tb = t["turn:lanes:backward"] ?: ""
      if (nf == null && tf.isNotEmpty()) nf = tf.split('|').size.toDouble()
      if (nb == null && tb.isNotEmpty()) nb = tb.split('|').size.toDouble()
      val total = num(t["lanes"])
      if (nf == null && nb == null) {
        val rest = if (total != null && total > 0) (total - both).toInt() else 2
        nf = ((rest + 1) / 2).toDouble(); nb = (rest / 2).toDouble()
      } else if (nf == null) {
        nf = max(1.0, if (total != null && total > 0) total - both - nb!! else 1.0)
      } else if (nb == null) {
        nb = max(1.0, if (total != null && total > 0) total - both - nf else 1.0)
      }
      val back = turns(tb, nb!!.toInt()).map { Lane(-1, it, w) }.reversed()
      lanes = back + List(both) { Lane(0, "both", w) } + turns(tf, nf!!.toInt()).map { Lane(1, it, w) }
    }
    val wt = num(t["width"])
    if (wt != null && lanes.isNotEmpty()) {
      val inner = wt - parkL - parkR
      if (inner > 2.5 * lanes.size) lanes = lanes.map { Lane(it.dir, it.turn, inner / lanes.size) }
    }
    return Layout(lanes, parkL, parkR, oneway)
  }

  /** the carriageway's centre relative to the way line, from placement=left_of:N / right_of:N / middle_of:N
   *  (one-way roads; null = no placement tag) */
  private fun placement(t: Map<String, String>, lanes: List<Lane>): Double? {
    val p = t["placement"] ?: return null
    val m = Regex("^(left_of|right_of|middle_of):(\\d+)$").find(p.trim()) ?: return null
    val k = m.groupValues[2].toInt()
    if (k < 1 || k > lanes.size) return null
    val W = lanes.sumOf { it.w }
    var x = -W / 2
    for (i in 0 until k - 1) x += lanes[i].w
    when (m.groupValues[1]) {
      "right_of" -> x += lanes[k - 1].w
      "middle_of" -> x += lanes[k - 1].w / 2
    }
    return -x
  }

  private class Info(val w: OsmWay, val t: Map<String, String>, val lay: Layout, val px: DoubleArray, val py: DoubleArray,
                     val keys: LongArray, val W: Double, val key: String, val hw: String) {
    var median = 0.0; var s0 = 0.0; var send = 0.0; var place: Double? = null
    /** the road before this one is this wide where they meet (NaN: none straight on): the side away from the ramp
     *  widens or narrows over [WIDEN] m instead of a step */
    var wPrev = Double.NaN; var keepLeft = true
    /** this end leaves / joins a bigger road at a slant (a ramp): the road narrows to its point there (a gore) */
    var gateA = false; var gateB = false
    val a get() = keys[0]
    val b get() = keys[keys.size - 1]
  }

  private fun unit(dx: Double, dy: Double): DoubleArray { val l = hypot(dx, dy).takeIf { it > 0 } ?: 1.0; return doubleArrayOf(dx / l, dy / l) }

  fun build(ways: Collection<OsmWay>, lat0: Double, lon0: Double, clat: Double, clon: Double, r: Double): Built {
    val kx = 111320.0 * cos(Math.toRadians(lat0))
    val cx = (clon - lon0) * kx; val cy = (clat - lat0) * KY
    val info = ArrayList<Info>()
    for (w in ways) {
      val n = w.lat.size
      val px = DoubleArray(n) { (w.lon[it] - lon0) * kx }
      val py = DoubleArray(n) { (w.lat[it] - lat0) * KY }
      var close = false
      for (i in 0 until n) if (hypot(px[i] - cx, py[i] - cy) < r) { close = true; break }
      if (!close) continue
      val lay = layout(w.tags)
      if (lay.lanes.isEmpty()) continue
      val keys = LongArray(n) { nodeKey(w.lat[it], w.lon[it]) }
      info.add(Info(w, w.tags, lay, px, py, keys, lay.lanes.sumOf { it.w }, w.tags["name"] ?: w.tags["ref"] ?: "",
        w.tags["highway"] ?: "residential").also { it.place = if (lay.oneway) placement(w.tags, lay.lanes) else null })
    }
    val ends = HashMap<Long, MutableList<Int>>()
    for ((i, w) in info.withIndex()) { ends.getOrPut(w.a) { ArrayList() }.add(i); ends.getOrPut(w.b) { ArrayList() }.add(i) }

    fun dirAt(i: Int, node: Long): DoubleArray {
      val w = info[i]; val n = w.px.size
      return if (w.a == node) unit(w.px[1] - w.px[0], w.py[1] - w.py[0]) else unit(w.px[n - 2] - w.px[n - 1], w.py[n - 2] - w.py[n - 1])
    }
    fun len(w: Info): Double { var s = 0.0; for (i in 1 until w.px.size) s += hypot(w.px[i] - w.px[i - 1], w.py[i] - w.py[i - 1]); return s }

    // two-way roads: a stretch narrower than its neighbours of the same name has a painted median (turn bays cut out of it)
    for ((i, w) in info.withIndex()) {
      if (w.lay.oneway || w.key.isEmpty()) continue
      val nb = (ends[w.a].orEmpty() + ends[w.b].orEmpty()).filter { it != i && !info[it].lay.oneway && info[it].key == w.key && info[it].hw == w.hw }
      if (nb.isEmpty()) continue
      val mx = nb.maxOf { info[it].W }
      val lw = WIDTH[w.hw] ?: 3.0
      if (mx - w.W > 1.0 && mx - w.W < 2.2 * lw) w.median = mx - w.W
    }
    // one-way roads: where the lane count changes, the edge away from the ramp stays continuous
    repeat(4) {
      for ((i, w) in info.withIndex()) {
        if (!w.lay.oneway) continue
        val node = w.a
        val preds = ends[node].orEmpty().filter { it != i && info[it].lay.oneway && info[it].b == node && (info[it].key == w.key || info[it].hw == w.hw) }
        if (preds.isEmpty()) continue
        val dIn = dirAt(i, node)
        fun straight(j: Int): Double { val dj = dirAt(j, node); return -(dj[0] * dIn[0] + dj[1] * dIn[1]) }
        val j = preds.maxByOrNull { straight(it) }!!
        if (straight(j) < 0.85) continue
        val p = info[j]
        var side = 0
        for (k in ends[node].orEmpty()) {
          if (k == i || k == j) continue
          val v = dirAt(k, node)
          val fwd = v[0] * dIn[0] + v[1] * dIn[1]
          if (max(fwd, -fwd) < 0.5) continue
          side = if (dIn[0] * v[1] - dIn[1] * v[0] < 0) 1 else -1
        }
        val pEnd = (p.place ?: 0.0) + p.send
        if (w.place != null) { w.send = 0.0; continue }
        var s0 = if (side >= 0) (-p.W / 2 + pEnd) + w.W / 2 else (p.W / 2 + pEnd) - w.W / 2
        if (abs(s0) > 2.5 * (WIDTH[w.hw] ?: 3.0)) s0 = 0.0
        w.s0 = s0
        w.send = s0 * max(0.0, 1 - len(w) / TAPER)
        if (abs(p.W - w.W) > 0.5) { w.wPrev = p.W; w.keepLeft = side >= 0 }
      }
    }

    // ramps: an end on a bigger road, meeting it at a slant, starts / ends in a point (OSM puts that end on the
    // bigger road's centre line; drawn full width it would stick out of it sideways)
    val through = HashMap<Long, MutableList<Int>>()
    for ((i, w) in info.withIndex()) for (k in w.keys) through.getOrPut(k) { ArrayList() }.add(i)
    for ((i, w) in info.withIndex()) {
      if (!w.lay.oneway || w.px.size < 2) continue
      for (atA in listOf(true, false)) {
        val node = if (atA) w.a else w.b
        val dw = dirAt(i, node)
        var gate = false
        for (j in through[node].orEmpty()) {
          if (j == i) continue
          val u = info[j]
          val ru = rankOf(u.hw); val rw = rankOf(w.hw)
          if (!(ru > rw || (ru == rw && u.W > w.W + 0.5 && u.key != w.key))) continue
          val k = u.keys.indexOf(node)
          val k2 = if (k + 1 < u.keys.size) k + 1 else k - 1
          val du = unit(u.px[k2] - u.px[k], u.py[k2] - u.py[k])
          if (abs(du[0] * dw[0] + du[1] * dw[1]) > 0.82) { gate = true; break }
        }
        if (atA) w.gateA = gate else w.gateB = gate
      }
    }
    val out = ArrayList<BWay>(info.size)
    for (w in info) out.add(geometry(w))
    val at = HashMap<Long, IntArray>()
    for ((wi, bw) in out.withIndex()) for ((vi, k) in bw.keys.withIndex()) {
      val prev = at[k]
      val code = (wi shl 20) or vi
      at[k] = if (prev == null) intArrayOf(code) else prev + code
    }
    trim(out, at)
    return Built(lat0, lon0, kx, cx, cy, out, at, System.currentTimeMillis())
  }

  /** A ramp or side road is drawn up to its junction node, which OSM puts on the bigger road's centre line: its lines
   *  would run across the bigger road's lanes. They stop where they enter its carriageway (within 120 m of the shared
   *  node; the same road going on is left alone). */
  private fun trim(ways: List<BWay>, at: Map<Long, IntArray>) {
    for ((wi, w) in ways.withIndex()) {
      val nodes = HashMap<Int, Int>()                        // other way -> one shared vertex of w
      for ((vi, k) in w.keys.withIndex()) for (code in at[k] ?: continue) { val o = code ushr 20; if (o != wi) nodes.putIfAbsent(o, vi) }
      if (nodes.isEmpty()) continue
      var lines = w.lines
      for ((o, vi) in nodes) {
        val u = ways[o]
        val major = u.rank > w.rank || (u.rank == w.rank && u.width > w.width + 0.5 && u.name != w.name)
        if (!major) continue
        val nx = w.px[vi]; val ny = w.py[vi]
        val px = FloatArray(u.left.size + u.right.size); val py = FloatArray(px.size)
        for (i in u.left.indices) { px[i] = u.left[i]; py[i] = u.leftY[i] }
        for (i in u.right.indices) { px[u.left.size + i] = u.right[u.right.size - 1 - i]; py[u.left.size + i] = u.rightY[u.right.size - 1 - i] }
        val out = ArrayList<MLine>()
        for (ln in lines) {
          var cur = ArrayList<Int>()
          fun flush() { if (cur.size >= 2) out.add(MLine(ln.style, FloatArray(cur.size) { ln.x[cur[it]] }, FloatArray(cur.size) { ln.y[cur[it]] })); cur = ArrayList() }
          for (i in ln.x.indices) {
            val inside = hypot(ln.x[i] - nx, ln.y[i] - ny) < 120 && insidePoly(ln.x[i], ln.y[i], px, py)
            if (inside) flush() else cur.add(i)
          }
          flush()
        }
        lines = out
      }
      w.lines = lines
    }
  }

  private fun insidePoly(x: Float, y: Float, px: FloatArray, py: FloatArray): Boolean {
    var c = false
    var j = px.size - 1
    for (i in px.indices) {
      if ((py[i] > y) != (py[j] > y) && x < (px[j] - px[i]) * (y - py[i]) / (py[j] - py[i]) + px[i]) c = !c
      j = i
    }
    return c
  }

  private fun resample(px: DoubleArray, py: DoubleArray, step: Double = 5.0): Pair<DoubleArray, DoubleArray> {
    val ox = ArrayList<Double>(); val oy = ArrayList<Double>()
    ox.add(px[0]); oy.add(py[0])
    for (i in 0 until px.size - 1) {
      val L = hypot(px[i + 1] - px[i], py[i + 1] - py[i])
      val k = max(1, (L / step).toInt())
      for (j in 1..k) { ox.add(px[i] + (px[i + 1] - px[i]) * j / k); oy.add(py[i] + (py[i + 1] - py[i]) * j / k) }
    }
    return ox.toDoubleArray() to oy.toDoubleArray()
  }

  /** the polyline moved sideways by d[i] (> 0 = right of the direction of travel) */
  private fun offset(x: DoubleArray, y: DoubleArray, d: DoubleArray): Pair<FloatArray, FloatArray> {
    val n = x.size
    val ox = FloatArray(n); val oy = FloatArray(n)
    for (i in 0 until n) {
      var nx: Double; var ny: Double
      if (i == 0 || i == n - 1) {
        val a = if (i == 0) 0 else n - 2
        val dx = x[a + 1] - x[a]; val dy = y[a + 1] - y[a]
        val L = hypot(dx, dy).takeIf { it > 0 } ?: 1.0
        nx = dy / L; ny = -dx / L
      } else {
        val ax = x[i] - x[i - 1]; val ay = y[i] - y[i - 1]
        val bx = x[i + 1] - x[i]; val by = y[i + 1] - y[i]
        val La = hypot(ax, ay).takeIf { it > 0 } ?: 1.0; val Lb = hypot(bx, by).takeIf { it > 0 } ?: 1.0
        val n1x = ay / La; val n1y = -ax / La
        val n2x = by / Lb; val n2y = -bx / Lb
        var mx = n1x + n2x; var my = n1y + n2y
        val Lm = hypot(mx, my)
        if (Lm < 1e-6) { nx = n1x; ny = n1y } else {
          mx /= Lm; my /= Lm
          val c = max(0.5, mx * n1x + my * n1y)
          nx = mx / c; ny = my / c
        }
      }
      ox[i] = (x[i] + d[i] * nx).toFloat(); oy[i] = (y[i] + d[i] * ny).toFloat()
    }
    return ox to oy
  }

  private fun geometry(w: Info): BWay {
    val t = w.t
    val lanes = w.lay.lanes
    var layer = num(t["layer"])?.toInt() ?: 0
    if (t["bridge"] != null && t["bridge"] != "no" && layer == 0) layer = 1
    if (t["tunnel"] != null && t["tunnel"] != "no" && layer == 0) layer = -1
    val major = w.hw in MAJOR
    val med = w.median
    val W = w.W + med
    val (x, y) = resample(w.px, w.py)
    val n = x.size
    val s = DoubleArray(n).also { for (i in 1 until n) it[i] = it[i - 1] + hypot(x[i] - x[i - 1], y[i] - y[i - 1]) }
    val base = w.place ?: 0.0
    val shift = DoubleArray(n) { base + w.s0 * max(0.0, 1 - s[it] / TAPER) }
    // a lane added / dropped where this road starts: the outer side moves out / in over WIDEN m (no step)
    val widen = min(WIDEN, s[n - 1] * 0.6)
    val extra = DoubleArray(n) { if (w.wPrev.isNaN() || widen <= 0) 0.0 else (w.wPrev - w.W) * max(0.0, 1 - s[it] / widen) }
    val rightOut = w.keepLeft
    val gate = min(60.0, s[n - 1] * 0.4)
    val narrow = DoubleArray(n) {
      var k = 1.0
      if (w.gateA && gate > 0) k = min(k, s[it] / gate)
      if (w.gateB && gate > 0) k = min(k, (s[n - 1] - s[it]) / gate)
      k.coerceIn(0.0, 1.0)
    }
    // lane boundaries left to right, the median as an extra gap between the two directions
    val edges = ArrayList<Double>(); edges.add(-W / 2)
    for ((k, l) in lanes.withIndex()) {
      if (med > 0 && k > 0 && lanes[k - 1].dir != l.dir) edges.add(edges.last() + med)
      edges.add(edges.last() + l.w)
    }
    val bounds = ArrayList<DoubleArray>()
    var ke = 0
    for ((k, l) in lanes.withIndex()) {
      if (med > 0 && k > 0 && lanes[k - 1].dir != l.dir) ke++
      bounds.add(doubleArrayOf(edges[ke], edges[ke + 1])); ke++
    }
    val lines = ArrayList<MLine>()
    fun at(c: Double, side: Int = 0) = offset(x, y, DoubleArray(n) {
      (c + shift[it] + when { side > 0 && rightOut -> extra[it]; side < 0 && !rightOut -> -extra[it]; else -> 0.0 }) * narrow[it]
    })
    fun add(style: Int, c: Double, side: Int = 0) { val (ox, oy) = at(c, side); lines.add(MLine(style, ox, oy)) }
    val l0 = edges.first() - w.lay.parkL; val r0 = edges.last() + w.lay.parkR
    val (lx, ly) = at(l0, -1); val (rx, ry) = at(r0, 1)
    add(if (w.lay.oneway && major) EDGE_YELLOW else if (major) EDGE_WHITE else CURB, edges.first(), -1)
    add(if (major) EDGE_WHITE else CURB, edges.last(), 1)
    if (w.lay.parkL > 0) add(CURB, l0, -1)
    if (w.lay.parkR > 0) add(CURB, r0, 1)
    val fast = w.hw in FAST
    for (k in 1 until lanes.size) {
      val a = lanes[k - 1]; val b = lanes[k]
      if (a.dir != b.dir) {
        if (med > 0) { add(DOUBLE_YELLOW, bounds[k - 1][1]); add(DOUBLE_YELLOW, bounds[k][0] - 0.3) }
        else add(DOUBLE_YELLOW, bounds[k][0] - 0.15)
        continue
      }
      val ta = a.turn.isNotEmpty() && "through" !in a.turn && a.turn != "none"
      val tb = b.turn.isNotEmpty() && "through" !in b.turn && b.turn != "none"
      val style = if (ta != tb && !fast) SOLID_WHITE else DASH_WHITE
      if (style == DASH_WHITE && !major && (w.hw == "residential" || w.hw == "unclassified")) continue
      add(style, bounds[k][0])
    }
    val mlanes = ArrayList<MLane>(); val arrows = ArrayList<MArrow>()
    for ((k, l) in lanes.withIndex()) {
      var (ox, oy) = at((bounds[k][0] + bounds[k][1]) / 2)
      if (l.dir < 0) { ox = ox.reversedArray(); oy = oy.reversedArray() }
      mlanes.add(MLane(l.dir, l.turn, ox, oy))
      if (l.turn.isNotEmpty() && l.turn !in setOf("none", "both", "through") && s[n - 1] > 25) {
        // 14 m before the lane's end
        var acc = 0.0; var target = 0.0
        for (i in 1 until ox.size) acc += hypot((ox[i] - ox[i - 1]).toDouble(), (oy[i] - oy[i - 1]).toDouble())
        target = max(0.0, acc - 14.0)
        acc = 0.0
        for (i in 1 until ox.size) {
          val L = hypot((ox[i] - ox[i - 1]).toDouble(), (oy[i] - oy[i - 1]).toDouble())
          if (acc + L >= target && L > 0) {
            val f = (target - acc) / L
            arrows.add(MArrow((ox[i - 1] + f * (ox[i] - ox[i - 1])).toFloat(), (oy[i - 1] + f * (oy[i] - oy[i - 1])).toFloat(),
              atan2((ox[i] - ox[i - 1]).toDouble(), (oy[i] - oy[i - 1]).toDouble()).toFloat(), l.turn))
            break
          }
          acc += L
        }
      }
    }
    var minX = Double.MAX_VALUE; var minY = Double.MAX_VALUE; var maxX = -Double.MAX_VALUE; var maxY = -Double.MAX_VALUE
    for (i in 0 until n) { minX = min(minX, x[i]); maxX = max(maxX, x[i]); minY = min(minY, y[i]); maxY = max(maxY, y[i]) }
    val pad = W / 2 + 6
    val flow = if (!w.lay.oneway) 0 else if (t["oneway"] == "-1") -1 else 1
    return BWay(w.w.id, w.hw, w.key, w.lay.oneway, layer, W, w.px, w.py, w.keys, lines, lx, ly, rx, ry, mlanes, arrows, flow,
      minX - pad, minY - pad, maxX + pad, maxY + pad)
  }
}
