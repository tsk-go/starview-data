package com.starpilot.starview

import android.util.Log
import java.util.PriorityQueue
import java.util.concurrent.Executors
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.exp
import kotlin.math.hypot
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin

/**
 * 2.87.0: lines LaneMap's lanes up with what the camera sees. GPS (even RTK) puts the map a few feet off sideways and a
 * degree or two off in heading, so the map is moved until its lines lie on openpilot's own lane lines and road edges
 * (modelV2): up to 23 ft sideways in 0.1 m steps and 5 deg in 0.5 deg steps, scored by how far each camera line sits
 * from the nearest map line at 8 / 15 / 22 / 30 / 40 m ahead, plus a pull toward the GPS position and toward the last
 * fit (no flipping between two lanes that look alike). When even the best spot misses by more than about 3 ft on
 * average, the map is left out ([Fit.hidden]): the camera's own lines are shown and never a lane under the car that
 * isn't there.
 *
 * The fit gives the car's corrected position and heading on the map at the moment of the fit; between fits (5 a
 * second) the car's own motion (Odometry) carries it on, so the drawing stays smooth. Also worked out here: which lane
 * the car is in ([Match]) and which roads can be reached along the roads within 1400 ft ([Fit.reach]: the ramp
 * crossing overhead is not ours).
 *
 * Car frame used here: right / fwd in metres. Map: LaneMap's metres east / north; headings are bearings in radians
 * (clockwise from north).
 */
object LaneFit {
  private const val TAG = "StarView/LaneFit"
  private val ZS = doubleArrayOf(8.0, 15.0, 22.0, 30.0, 40.0)
  private const val CAM_TO = 90.0
  const val HIDE_COST = 1.0
  private const val SHOW_COST = 0.85
  private const val REACH = 420.0

  class Pose(val x: Double, val y: Double, val h: Double) {
    fun right(px: Double, py: Double): Double = (px - x) * cos(h) - (py - y) * sin(h)
    fun fwd(px: Double, py: Double): Double = (px - x) * sin(h) + (py - y) * cos(h)
  }

  /** openpilot's lane lines (0..3, left to right) and road edges (0 left, 1 right): metres ahead / right */
  class Cam(val lx: Array<FloatArray>, val ly: Array<FloatArray>, val lp: FloatArray, val ex: Array<FloatArray>,
            val ey: Array<FloatArray>, val estd: FloatArray) {
    companion object {
      fun of(m: ModelSnapshot): Cam? {
        val lx = m.laneX; val ly = m.laneY
        if (lx.size < 4 || ly.size < 4 || lx[0].size < 2) return null
        return Cam(lx, ly, m.laneProb, m.edgeX, m.edgeY, m.edgeStd)
      }
    }
  }

  /** [dir]: the car drives along the way's points (1) or against them (-1) */
  class Match(val way: Int, val lane: Int, val of: Int, val turns: List<String>, val name: String, val hw: String, val offM: Double,
              val dir: Int)

  class Fit(val built: LaneMap.Built, val pose: Pose, val odo: DoubleArray, val atMs: Long, val cost: Double,
            val hidden: Boolean, val shift: Double, val dh: Double, val match: Match?, val reach: BooleanArray?,
            val camLines: Int, val camEdges: Int) {
    /** the OSM ids of [reach] (the next fit keeps to these, even after the map is rebuilt: a road stacked over ours,
     *  like the bridge's other level, looks the same to the camera) */
    val reachIds: Set<Long>? = reach?.let { r -> HashSet<Long>().also { s -> for (i in r.indices) if (r[i]) s.add(built.ways[i].id) } }
  }

  fun sameFrame(a: LaneMap.Built, b: LaneMap.Built) = a === b || (a.lat0 == b.lat0 && a.lon0 == b.lon0)

  @Volatile var fit: Fit? = null
    private set
  @Volatile var status = ""
    private set

  private val worker = Executors.newSingleThreadExecutor { r -> Thread(r, "lanefit").apply { priority = Thread.MIN_PRIORITY + 1 } }
  @Volatile private var busy = false
  @Volatile private var lastStart = 0L
  @Volatile private var hiddenRuns = 0
  @Volatile private var shownRuns = 0
  @Volatile private var jumpRuns = 0
  @Volatile private var reachAt = 0L
  @Volatile private var reachWay = -1
  @Volatile private var reachMemo: BooleanArray? = null
  @Volatile private var reachB: LaneMap.Built? = null

  /** From the screen tick (any rate): a new fit at most 5 times a second, on its own thread. */
  fun tick(t: Telemetry) {
    val now = System.currentTimeMillis()
    if (busy || now - lastStart < 200) return
    val p = NavPos.pos(t)
    if (p[0].isNaN()) return
    LaneMap.onPos(p[0], p[1], p[2], t.vEgo.toDouble())
    val b = LaneMap.built ?: return
    val m = t.model ?: return
    val cam = Cam.of(m) ?: return
    val odo = Odometry.at(System.nanoTime())
    val rtk = RtkPosition.INSTANCE
    val acc = if (rtk.fast(now) && rtk.accM.isFinite()) max(0.5, rtk.accM) else 3.0
    val xy = b.xy(p[0], p[1])
    val gps = Pose(xy[0], xy[1], Math.toRadians(p[2]))
    busy = true; lastStart = now
    worker.execute {
      try { fit = run(b, gps, acc, cam, odo, now, fit) } catch (e: Throwable) { Log.w(TAG, "fit", e) } finally { busy = false }
    }
  }

  /** The car's corrected pose now: the last fit carried on by the car's own motion; null when there is none (or it is
   *  older than 2 s). */
  fun poseNow(f: Fit, odoNow: DoubleArray): Pose = carry(f.pose, f.odo, odoNow)

  fun carry(p: Pose, o0: DoubleArray, o1: DoubleArray): Pose {
    val dx = o1[0] - o0[0]; val dy = o1[1] - o0[1]
    val fwd = dx * cos(o0[2]) + dy * sin(o0[2])
    val left = -dx * sin(o0[2]) + dy * cos(o0[2])
    return Pose(p.x + fwd * sin(p.h) - left * cos(p.h), p.y + fwd * cos(p.h) + left * sin(p.h), p.h - (o1[2] - o0[2]))
  }

  /** One fit (pure: the tests call it with a recorded drive). */
  fun run(b: LaneMap.Built, gps: Pose, acc: Double, cam: Cam, odo: DoubleArray, now: Long, prev: Fit?): Fit {
    // ------------------------------------------------ camera samples
    class S(val zi: Int, val r: Double, val w: Double, val edge: Boolean)
    val samples = ArrayList<S>()
    var nLines = 0; var nEdges = 0
    for (i in 0 until 4) {
      val p = cam.lp[i]
      if (p < 0.35f) continue
      nLines++
      if (i != 1 && i != 2) continue
      val reach = CAM_TO * (0.6 + 0.4 * p)
      val xs = cam.lx[i]; val ys = cam.ly[i]
      for ((zi, z) in ZS.withIndex()) if (z <= min(reach, xs.last().toDouble())) samples.add(S(zi, interp(xs, ys, z), p.toDouble(), false))
    }
    for (i in 0 until 2) {
      if (cam.estd[i] > 1.2f) continue
      nEdges++
      val xs = cam.ex[i]; val ys = cam.ey[i]
      for (k in 0 until 3) if (ZS[k] <= xs.last()) samples.add(S(k, interp(xs, ys, ZS[k]), 0.6, true))
    }
    val live = prev != null && sameFrame(prev.built, b) && !prev.hidden && now - prev.atMs < 1500
    val prevPose = if (live) carry(prev!!.pose, prev.odo, odo) else null
    val allowed = if (live) prev!!.reachIds else null
    if (samples.size < 3) return hiddenFit(b, gps, odo, now, nLines, nEdges, "camera sees too little")

    // ------------------------------------------------ map lines near the car, in its frame
    val segs = ArrayList<FloatArray>()      // r0, f0, r1, f1, edge(1/0)
    for (w in b.ways) {
      if (!w.near(gps.x, gps.y, 60.0)) continue
      if (allowed != null && w.id !in allowed) continue
      for (ln in w.lines) {
        val edge = if (LaneMap.isEdge(ln.style)) 1f else 0f
        var pr = 0.0; var pf = 0.0
        for (k in ln.x.indices) {
          val r = gps.right(ln.x[k].toDouble(), ln.y[k].toDouble()); val f = gps.fwd(ln.x[k].toDouble(), ln.y[k].toDouble())
          if (k > 0 && max(pf, f) > 2 && min(pf, f) < 50 && abs(pr) < 40 && abs(r) < 40)
            segs.add(floatArrayOf(pr.toFloat(), pf.toFloat(), r.toFloat(), f.toFloat(), edge))
          pr = r; pf = f
        }
      }
    }
    if (segs.isEmpty()) return hiddenFit(b, gps, odo, now, nLines, nEdges, "no map here")

    // the last fit's offsets from this GPS pose, for the pull toward it
    var pShift = Double.NaN; var pDh = Double.NaN
    if (prevPose != null) {
      pDh = wrap(gps.h - prevPose.h)                                       // h' = h - dh
      pShift = -(prevPose.x - gps.x) * cos(prevPose.h) + (prevPose.y - gps.y) * sin(prevPose.h)
    }
    val sigma = if (acc < 1.0) 2.5 else 3.5
    var best = doubleArrayOf(Double.MAX_VALUE, 0.0, 0.0)
    val xsLine = Array(ZS.size) { DoubleArray(64) }; val nLine = IntArray(ZS.size)
    val xsEdge = Array(ZS.size) { DoubleArray(64) }; val nEdge = IntArray(ZS.size)
    var dhDeg = -5.0
    while (dhDeg <= 5.001) {
      val th = Math.toRadians(dhDeg)
      val c = cos(th); val s = sin(th)
      nLine.fill(0); nEdge.fill(0)
      // rotate the map by the heading change: right' = r cos + f sin, fwd' = f cos - r sin
      for (sg in segs) {
        val r0 = sg[0] * c + sg[1] * s; val f0 = sg[1] * c - sg[0] * s
        val r1 = sg[2] * c + sg[3] * s; val f1 = sg[3] * c - sg[2] * s
        val L = hypot(r1 - r0, f1 - f0)
        if (L < 1e-6 || (f1 - f0) / L < 0.8) continue                    // pointing ahead only
        for ((zi, z) in ZS.withIndex()) {
          if ((f0 - z) * (f1 - z) > 0) continue
          val k = if (f1 == f0) 0.0 else (z - f0) / (f1 - f0)
          val x = r0 + k * (r1 - r0)
          if (nLine[zi] < 64) { xsLine[zi][nLine[zi]] = x; nLine[zi]++ }
          if (sg[4] > 0 && nEdge[zi] < 64) { xsEdge[zi][nEdge[zi]] = x; nEdge[zi]++ }
        }
      }
      var shift = -7.0
      while (shift <= 7.001) {
        var cost = 0.0
        for (sm in samples) {
          val zi = sm.zi
          val arr = if (sm.edge) xsEdge[zi] else xsLine[zi]
          val n = if (sm.edge) nEdge[zi] else nLine[zi]
          var d = 2.5
          for (j in 0 until n) { val dd = abs(sm.r - (arr[j] + shift)); if (dd < d) d = dd }
          cost += sm.w * d * d
        }
        cost = cost / samples.size + 0.3 * (shift / sigma) * (shift / sigma) + 0.05 * (dhDeg / 3.0) * (dhDeg / 3.0)
        if (!pShift.isNaN()) {
          cost += 0.05 * (shift - pShift) * (shift - pShift) + 0.03 * ((dhDeg - Math.toDegrees(pDh)) / 1.5).let { it * it }
        }
        if (cost < best[0]) best = doubleArrayOf(cost, shift, dhDeg)
        shift += 0.1
      }
      dhDeg += 0.5
    }
    val cost = best[0]; var shift = best[1]; var dh = Math.toRadians(best[2])
    // a jump of more than 4 ft from where the last fit carried on to has to hold for two fits in a row
    if (!pShift.isNaN() && abs(shift - pShift) > 1.2) {
      jumpRuns++
      if (jumpRuns < 2) { shift = pShift; dh = pDh }
    } else jumpRuns = 0
    // hide / show with some hysteresis
    val wasHidden = prev?.hidden ?: true
    val hidden: Boolean
    if (wasHidden) {
      shownRuns = if (cost < SHOW_COST) shownRuns + 1 else 0
      hidden = shownRuns < 2
      if (!hidden) hiddenRuns = 0
    } else {
      hiddenRuns = if (cost > HIDE_COST) hiddenRuns + 1 else 0
      hidden = hiddenRuns >= 3
      if (hidden) shownRuns = 0
    }
    val h2 = gps.h - dh
    val pose = Pose(gps.x - shift * cos(h2), gps.y + shift * sin(h2), h2)
    val match = matchLane(b, pose, allowed)
    val reach = if (match != null) reachFrom(b, match, pose, now) else null
    status = String.format(java.util.Locale.US, "fit %.2f, %+.1f m, %+.1f°%s", cost, shift, Math.toDegrees(dh), if (hidden) " (map hidden)" else "")
    return Fit(b, pose, odo, now, cost, hidden, shift, dh, if (hidden) null else match, reach, nLines, nEdges)
  }

  private fun hiddenFit(b: LaneMap.Built, gps: Pose, odo: DoubleArray, now: Long, nl: Int, ne: Int, why: String): Fit {
    status = why
    return Fit(b, gps, odo, now, 9.0, true, 0.0, 0.0, null, null, nl, ne)
  }

  private fun wrap(a: Double): Double = Math.IEEEremainder(a, 2 * Math.PI)

  fun interp(xs: FloatArray, ys: FloatArray, x: Double): Double {
    if (x <= xs[0]) return ys[0].toDouble()
    for (i in 1 until xs.size) if (xs[i] >= x) {
      val k = (x - xs[i - 1]) / max(1e-6, (xs[i] - xs[i - 1]).toDouble())
      return ys[i - 1] + k * (ys[i] - ys[i - 1])
    }
    return ys.last().toDouble()
  }

  /** The lane under the car: the nearest lane centre running our way within 13 ft. */
  fun matchLane(b: LaneMap.Built, pose: Pose, allowed: Set<Long>? = null): Match? {
    var bestD = 4.0; var bw = -1; var bl: LaneMap.MLane? = null
    for ((wi, w) in b.ways.withIndex()) {
      if (!w.near(pose.x, pose.y, 8.0)) continue
      if (allowed != null && w.id !in allowed) continue
      for (ln in w.lanes) {
        for (k in 1 until ln.x.size) {
          val r0 = pose.right(ln.x[k - 1].toDouble(), ln.y[k - 1].toDouble()); val f0 = pose.fwd(ln.x[k - 1].toDouble(), ln.y[k - 1].toDouble())
          val r1 = pose.right(ln.x[k].toDouble(), ln.y[k].toDouble()); val f1 = pose.fwd(ln.x[k].toDouble(), ln.y[k].toDouble())
          val dr = r1 - r0; val df = f1 - f0; val L2 = dr * dr + df * df
          if (L2 < 1e-6 || df / Math.sqrt(L2) < 0.8) continue
          val tt = (-(r0 * dr + f0 * df) / L2).coerceIn(0.0, 1.0)
          val d = hypot(r0 + tt * dr, f0 + tt * df)
          if (d < bestD) { bestD = d; bw = wi; bl = ln }
        }
      }
    }
    val ln = bl ?: return null
    val w = b.ways[bw]
    val same = w.lanes.filter { it.dir == ln.dir }
    // left to right as the car sees them (the lane list already is, for both directions of travel)
    val sorted = same.sortedBy { l ->
      var bestF = Double.MAX_VALUE; var r = 0.0
      for (k in l.x.indices) { val f = abs(pose.fwd(l.x[k].toDouble(), l.y[k].toDouble())); if (f < bestF) { bestF = f; r = pose.right(l.x[k].toDouble(), l.y[k].toDouble()) } }
      r
    }
    return Match(bw, sorted.indexOf(ln) + 1, sorted.size, sorted.map { it.turn }, w.name, w.hw, bestD, if (ln.dir < 0) -1 else 1)
  }

  /** The roads to draw and to fit to: where the car can legally drive on from here within [REACH] m (one-way roads
   *  only their way; never from a highway down to a local road - that's the street under the bridge), the road behind
   *  it for 260 ft, and the roads that meet those (merges, the highway going on past our exit, cross streets). Not the other carriageway, not a level of a
   *  bridge stacked over ours. Worked out again when the car's road changes, else once a second. */
  private fun reachFrom(b: LaneMap.Built, m: Match, pose: Pose, now: Long): BooleanArray {
    val memo = reachMemo
    if (memo != null && memo.size == b.ways.size && reachB === b && reachWay == m.way && now - reachAt < 1000) return memo
    val out = BooleanArray(b.ways.size)
    val w0 = b.ways[m.way]
    var i0 = 0; var d0 = Double.MAX_VALUE
    for (i in w0.px.indices) { val d = hypot(w0.px[i] - pose.x, w0.py[i] - pose.y); if (d < d0) { d0 = d; i0 = i } }
    walk(b, m.way, i0, m.dir, REACH, out)
    walk(b, m.way, i0, -m.dir, 80.0, out)       // behind the car (against the traffic)
    // and every road meeting those (a merge, the highway going on past our exit, a cross street) - one step only
    val walked = out.copyOf()
    for ((wi, w) in b.ways.withIndex()) {
      if (!walked[wi]) continue
      for (k in w.keys) for (o in b.at[k] ?: continue) {
        val ow = o ushr 20
        if (!(w.hw in LaneMap.FAST && b.ways[ow].hw !in LaneMap.FAST)) out[ow] = true
      }
    }
    reachMemo = out; reachWay = m.way; reachAt = now; reachB = b
    return out
  }

  /** marks the ways reachable from way [w0] point [i0] going [dir] (+1 = up the point list) within [limit] m; with
   *  dir taken against the traffic ([back]) it follows the roads the traffic comes from */
  private fun walk(b: LaneMap.Built, w0: Int, i0: Int, dir: Int, limit: Double, out: BooleanArray) {
    val back = dir != (b.ways[w0].flow.takeIf { it != 0 } ?: dir)
    val seen = HashMap<Long, Double>()
    val q = PriorityQueue<DoubleArray>(64, compareBy { it[0] })
    q.add(doubleArrayOf(0.0, w0.toDouble(), i0.toDouble(), dir.toDouble()))
    while (q.isNotEmpty()) {
      val e = q.poll()!!
      val d = e[0]; val wi = e[1].toInt(); val vi = e[2].toInt(); val dv = e[3].toInt()
      val code = (wi.toLong() shl 22) or (vi.toLong() shl 1) or (if (dv > 0) 1L else 0L)
      if (d > limit || (seen[code] ?: Double.MAX_VALUE) <= d) continue
      seen[code] = d
      out[wi] = true
      val w = b.ways[wi]
      val j = vi + dv
      if (j in w.px.indices) q.add(doubleArrayOf(d + hypot(w.px[j] - w.px[vi], w.py[j] - w.py[vi]), wi.toDouble(), j.toDouble(), dv.toDouble()))
      val fast = w.hw in LaneMap.FAST
      for (o in b.at[w.keys[vi]] ?: continue) {
        val ow = o ushr 20
        if (ow == wi) continue
        val u = b.ways[ow]
        if (if (back) (u.hw in LaneMap.FAST && w.hw !in LaneMap.FAST) else (fast && u.hw !in LaneMap.FAST)) continue
        val k = o and 0xfffff
        // the way(s) we may go on it: with its traffic (against it when walking back), both on a two-way road
        val dirs = if (u.flow == 0) intArrayOf(1, -1) else intArrayOf(if (back) -u.flow else u.flow)
        for (nd in dirs) q.add(doubleArrayOf(d, ow.toDouble(), k.toDouble(), nd.toDouble()))
      }
    }
  }

  /** Smoothing between fits for the drawing: the corrected pose now, eased over 0.3 s when a new fit moves it. */
  class Smooth {
    private var last: Fit? = null
    private var ox = 0.0; private var oy = 0.0; private var oh = 0.0; private var atNs = 0L
    fun pose(f: Fit, odoNow: DoubleArray, nowNs: Long): Pose {
      val p = carry(f.pose, f.odo, odoNow)
      val l = last
      if (l !== f) {
        if (l != null && !l.hidden && sameFrame(l.built, f.built)) {
          val q = carry(l.pose, l.odo, odoNow)
          val dx = q.x - p.x + ox * k(nowNs); val dy = q.y - p.y + oy * k(nowNs); val dh = wrap(q.h - p.h) + oh * k(nowNs)
          if (hypot(dx, dy) < 3.0 && abs(dh) < 0.1) { ox = dx; oy = dy; oh = dh } else { ox = 0.0; oy = 0.0; oh = 0.0 }
        } else { ox = 0.0; oy = 0.0; oh = 0.0 }
        last = f; atNs = nowNs
      }
      val kk = k(nowNs)
      return Pose(p.x + ox * kk, p.y + oy * kk, p.h + oh * kk)
    }
    private fun k(nowNs: Long): Double = exp(-(nowNs - atNs) / 1e9 / 0.12)
  }
}
