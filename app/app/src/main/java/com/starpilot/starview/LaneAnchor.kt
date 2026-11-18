package com.starpilot.starview

import kotlin.math.abs
import kotlin.math.atan
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.exp
import kotlin.math.hypot
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * 2.90.0 (owner, Nov 17: "why can TomTom, Google Maps, Waze and every other map show nice steady lines ... keeping
 * patching it is not going to work, you have to come up with a much greater idea"): the map is the road, the camera
 * only says where we are on it.
 *
 * Until now the Scene drew two roads and joined them: the camera's own lines near the van and the map's beyond, with
 * the map moved about every fifth of a second until its lines lay on the camera's (LaneFit). Wherever the two
 * disagreed - and a road with several lanes looks the same one lane over - the join moved, the map came and went, the
 * lines changed paint. A navigation map never does that because it draws one road, the map's, which cannot move, and
 * only works out where on it the car is. This does the same, lane by lane:
 *
 *  - which road: the nearest one going our way, kept unless another is clearly nearer (and reachable from ours);
 *  - which lane: a belief over the road's lanes that is carried from moment to moment, not guessed afresh. It moves a
 *    lane over when we drive across a line (the camera sees that for certain), and otherwise only drifts with what
 *    lasts: the GPS side-to-side place, the camera's road edges against the map's, the yellow line on our left, the
 *    lane card's count. The lane drawn changes only when another lane has been clearly the likelier one for 3 s;
 *  - where in the lane, and which way we point: from the camera's two lines of our own lane - the one thing openpilot
 *    measures well - against the map's lane;
 *  - how far along: the GPS place slid onto that lane.
 *
 * The result is a LaneFit.Fit like LaneFit's own, so the drawing takes it as it is; the map's lines are then the only
 * lines drawn. No road nearby = no fit, and the Scene shows the camera's own road (held still, SceneSteady).
 * Car frame and map frame as in LaneFit.
 */
object LaneAnchor {
  @Volatile var fit: LaneFit.Fit? = null
    private set
  @Volatile var status = ""
    private set
  /** from the Scene: we drove across one of our lane's lines since the last run (1 = to the right, -1 = left) */
  @Volatile var pendingShift = 0
  /** from the Scene: the camera's line reader (which paint), null = not running */
  @Volatile var marks: Array<LaneMarks.Mark>? = null

  /** nearest point of a line: the point, the line's bearing there, how far right of the line we are, the distance,
   *  how far past its ends (m), and where on it (segment, part) */
  class Proj(val cx: Double, val cy: Double, val bearing: Double, val side: Double, val dist: Double, val over: Double, val k: Int, val t: Double)

  fun project(x: FloatArray, y: FloatArray, px: Double, py: Double): Proj? {
    var best = Double.MAX_VALUE; var res: Proj? = null
    for (k in 1 until min(x.size, y.size)) {
      val ax = x[k - 1].toDouble(); val ay = y[k - 1].toDouble()
      val dx = x[k] - ax; val dy = y[k] - ay
      val l2 = dx * dx + dy * dy
      if (l2 < 1e-6) continue
      val t = ((px - ax) * dx + (py - ay) * dy) / l2
      val tc = t.coerceIn(0.0, 1.0)
      val cx = ax + tc * dx; val cy = ay + tc * dy
      val d = hypot(px - cx, py - cy)
      if (d < best) {
        best = d
        val l = sqrt(l2)
        val over = if (k == 1 && t < 0) -t * l else if (k == min(x.size, y.size) - 1 && t > 1) (t - 1) * l else 0.0
        res = Proj(cx, cy, atan2(dx, dy), ((px - cx) * dy - (py - cy) * dx) / l, d, over, k, tc)
      }
    }
    return res
  }

  /** the line's bearing [ahead] m further on from [p] */
  private fun bearingAhead(x: FloatArray, y: FloatArray, p: Proj, ahead: Double): Double {
    var left = ahead
    var k = p.k
    var t = p.t
    while (k < x.size) {
      val dx = (x[k] - x[k - 1]).toDouble(); val dy = (y[k] - y[k - 1]).toDouble()
      val l = hypot(dx, dy)
      if (l * (1 - t) >= left || k == x.size - 1) return if (l > 1e-3) atan2(dx, dy) else p.bearing
      left -= l * (1 - t); t = 0.0; k++
    }
    return p.bearing
  }

  /** What is carried from one moment to the next (one drive; the tests keep their own). */
  class State {
    internal var wayId = Long.MIN_VALUE
    internal var p = DoubleArray(0)            // the belief: one number per lane of our direction, left to right
    internal var lat = DoubleArray(0)          // those lanes' middles, m right of the GPS place (at the last run)
    internal var shown = -1                    // the lane we're drawn in
    internal var cand = -1; internal var candMs = 0L
    internal var off = 0.0                     // m right of our lane's middle
    internal var yaw = 0.0                     // the lane's direction against ours (radians, right = positive)
    internal var lastMs = 0L
    internal var bornMs = 0L
    internal var farMs = 0L
    internal var pose: LaneFit.Pose? = null
    internal var odo: DoubleArray? = null
    internal var built: LaneMap.Built? = null
    internal var reach: Set<Long>? = null
    /** from LaneRibbon: the roads we can be on by driving (its thread around us, the branches of the forks near us) */
    internal var expect: Set<Long>? = null
    // for LaneRibbon: the road piece and lane we're on, the point of that lane's middle beside us, its width on the map
    internal var wayIdx = -1
    internal var mlane: LaneMap.MLane? = null
    internal var px = 0.0; internal var py = 0.0
    internal var wMap = 3.6
    /** the lane drawn and how sure (for the tests and the developer's line) */
    val lane: Int get() = shown
    val sure: Double get() = p.getOrElse(shown) { 0.0 }
  }

  private val live = State()
  private val ribbon = LaneRibbon.State()

  /** LaneFit's worker thread, after its own fit. */
  internal fun tick(b: LaneMap.Built, gps: LaneFit.Pose, acc: Double, cam: LaneFit.Cam, odo: DoubleArray, now: Long, hint: LaneSceneView.Lanes?) {
    val shift = pendingShift
    pendingShift = 0
    fit = try {
      // where we are (which road, which lane), then the road drawn around it (LaneRibbon)
      if (run(b, gps, acc, cam, odo, now, live, hint, marks, shift) == null) null else LaneRibbon.update(b, live, ribbon, cam, odo, now)
    } catch (e: Throwable) { android.util.Log.w("StarView/LaneAnchor", "run", e); null }
  }

  private class Cand(val wi: Int, val lane: LaneMap.MLane, val pr: Proj)

  /**
   * One step. [gps]: the GPS place and heading on the map (x / y / h may be NaN: standing still, no fix); [hint]: the
   * lane card's count; [shift]: we crossed a line (1 right, -1 left). null = no road here.
   */
  fun run(b: LaneMap.Built, gps: LaneFit.Pose, acc: Double, cam: LaneFit.Cam, odo: DoubleArray, now: Long, st: State,
          hint: LaneSceneView.Lanes?, marks: Array<LaneMarks.Mark>?, shift: Int): LaneFit.Fit? {
    val fresh = st.pose == null || st.built == null || !LaneFit.sameFrame(st.built!!, b) || now - st.lastMs > 3000 || now < st.lastMs
    val ref = if (fresh) null else LaneFit.carry(st.pose!!, st.odo!!, odo)
    // a GPS place worth using: under a bridge deck or in a tunnel the fix drifts off by 100 ft and more (its own
    // accuracy says so) - then we go on along our lane by the car's own motion
    val gpsOk = gps.x.isFinite() && gps.y.isFinite() && acc <= 10.0
    val qx = if (gpsOk) gps.x else ref?.x ?: Double.NaN
    val qy = if (gpsOk) gps.y else ref?.y ?: Double.NaN
    val href = ref?.h ?: gps.h
    if (!qx.isFinite() || !qy.isFinite() || !href.isFinite()) { status = "no place yet"; if (fresh) reset(st); return null }
    val dt = if (fresh) 0.25 else ((now - st.lastMs) / 1000.0).coerceIn(0.02, 1.0)

    // ---------------------------------------------------------------- which road
    val cands = ArrayList<Cand>()
    var bestWay = -1; var bestScore = 14.0 + min(acc, 15.0)
    // locked onto a road the GPS has been 30 ft and more away from for 5 s: start over
    val lost = !fresh && st.farMs != 0L && now - st.farMs > 5000
    val reach = if (fresh || lost) null else st.expect ?: st.reach
    var own = Double.MAX_VALUE
    for ((wi, w) in b.ways.withIndex()) {
      if (!w.near(qx, qy, 40.0)) continue
      var score = Double.MAX_VALUE
      for (ln in w.lanes) {
        val pr = project(ln.x, ln.y, qx, qy) ?: continue
        val along = cos(pr.bearing - href)
        if (along < 0.7 || pr.over > 3.0) continue
        cands.add(Cand(wi, ln, pr))
        score = min(score, max(0.0, abs(pr.side) - 1.8) + 20 * (1 - along))
      }
      if (score == Double.MAX_VALUE) continue
      if (w.id == st.wayId) own = score
      // the road we're on stays unless another is clearly nearer; one we can't drive onto from here (the bridge's
      // other level, the road alongside) only when nothing else is left
      if (!fresh && !lost && w.id != st.wayId) score += if (reach != null && w.id !in reach) 25.0 else 4.0
      if (score < bestScore) { bestScore = score; bestWay = wi }
    }
    if (bestWay < 0) { status = "no road here"; reset(st); return null }
    if (gpsOk && own > 9.0 && own != Double.MAX_VALUE) { if (st.farMs == 0L) st.farMs = now } else st.farMs = 0L
    if (lost) st.farMs = 0L
    val w = b.ways[bestWay]
    val lanes = cands.filter { it.wi == bestWay }.sortedBy { -it.pr.side }       // left to right
    val n = lanes.size
    val lat = DoubleArray(n) { -lanes[it].pr.side }

    // ---------------------------------------------------------------- which lane
    var p = DoubleArray(n)
    val sigGps = hypot(max(acc, 0.3), 2.0)                 // the map's own lines are a few feet off too
    if (fresh || st.p.isEmpty()) {
      for (j in 0 until n) p[j] = exp(-0.5 * (lat[j] / sigGps).pow(2)) + 0.02
    } else if (w.id != st.wayId || n != st.p.size) {
      // another piece of road (or a lane more or fewer): each lane takes over from the lanes that were where it is
      for (j in 0 until n) { var s = 1e-3; for (i in st.p.indices) s += st.p[i] * exp(-0.5 * ((lat[j] - st.lat[i]) / 1.2).pow(2)); p[j] = s }
    } else p = st.p.copyOf()
    var shown = if (fresh || st.shown !in 0 until st.p.size) -1 else st.shown
    if (shown >= 0 && (w.id != st.wayId || n != st.p.size)) {
      // drawn in the lane that is where our lane was
      val was = st.lat[shown]
      shown = (0 until n).minByOrNull { abs(lat[it] - was) } ?: -1
    }
    if (shift != 0 && n > 0) {
      // we drove across a line: the whole belief moves one lane over with us
      val q = DoubleArray(n)
      for (j in 0 until n) q[(j + shift).coerceIn(0, n - 1)] += p[j]
      p = q
      if (shown >= 0) shown = (shown + shift).coerceIn(0, n - 1)
      st.off -= shift * laneWidth(lat, shown.coerceAtLeast(0))
    }
    norm(p)
    // a little doubt creeps in with time (a lane change the camera missed)
    val eps = min(0.02, 0.03 * dt)
    val q = DoubleArray(n)
    for (j in 0 until n) { q[j] += p[j] * (1 - 2 * eps); q[max(0, j - 1)] += p[j] * eps; q[min(n - 1, j + 1)] += p[j] * eps }
    p = q
    // what speaks for each lane now. The same reading comes again and again (5 times a second) with the same error, and
    // the lane card keeps a wrong count for seconds on end (7 s at a time in the owner's video). We did not change lanes
    // without crossing a line, so apart from that the belief may only move on what lasts: each reading counts once per
    // 8 s (once per 2 s in the first 10 s, while finding the lane).
    if (fresh) st.bornMs = now
    val a = min(1.0, dt / (if (now - st.bornMs < 10_000) 2.0 else 8.0))
    val guess = if (shown >= 0) st.off else 0.0
    for (j in 0 until n) {
      var l = 1.0
      if (gpsOk) l *= exp(-0.5 * (lat[j] / sigGps).pow(2))
      // the camera's road edges against the map's (weak: parked cars and shoulders aren't in the map)
      val c = lanes[j].pr
      val nx = cos(c.bearing); val ny = -sin(c.bearing)
      var eL = Double.NaN; var eR = Double.NaN
      for ((ex, ey) in listOf(w.left to w.leftY, w.right to w.rightY)) {
        val e = project(ex, ey, c.cx, c.cy) ?: continue
        val v = (e.cx - c.cx) * nx + (e.cy - c.cy) * ny
        if (v < 0) eL = -v else eR = v
      }
      if (cam.estd.size > 1) {
        if (cam.estd[0] <= 1.0f && eL.isFinite()) l *= 0.3 + 0.7 * exp(-0.5 * ((-LaneFit.interp(cam.ex[0], cam.ey[0], 8.0) - (eL + guess)) / 2.0).pow(2))
        if (cam.estd[1] <= 1.0f && eR.isFinite()) l *= 0.3 + 0.7 * exp(-0.5 * ((LaneFit.interp(cam.ex[1], cam.ey[1], 8.0) - (eR - guess)) / 2.0).pow(2))
      }
      // a yellow line on our left: the leftmost lane our way
      val mk = marks?.getOrNull(1)
      if (mk != null && mk.kind >= LaneMarks.DASHED && mk.yellow) l *= if (j == 0) 1.0 else 0.17
      // the lane card: so many lanes our way on our left / right
      if (hint != null) {
        if (hint.left >= 0) l *= if (j == min(hint.left, n - 1)) 1.0 else 0.55
        if (hint.right >= 0) l *= if (j == max(0, n - 1 - hint.right)) 1.0 else 0.55
      }
      p[j] *= l.pow(a)
    }
    norm(p)
    // the lane drawn: the likeliest at the start; later another one only once it has been clearly the likelier for 3 s
    val top = (0 until n).maxByOrNull { p[it] } ?: 0
    if (shown < 0) { shown = top; st.cand = -1 }
    else if (top != shown && p[top] > 0.75) {
      if (st.cand != top) { st.cand = top; st.candMs = now }
      if (now - st.candMs >= 3000) { shown = top; st.cand = -1 }
    } else st.cand = -1

    // ---------------------------------------------------------------- where in the lane, which way we point
    val wMap = laneWidth(lat, shown)
    fun ok(i: Int) = cam.lp.size > i && cam.lp[i] >= 0.5f && cam.lx[i].size >= 2
    val yL = if (ok(1)) LaneFit.interp(cam.lx[1], cam.ly[1], 5.0) else Double.NaN
    val yR = if (ok(2)) LaneFit.interp(cam.lx[2], cam.ly[2], 5.0) else Double.NaN
    val meas = when {
      yL.isFinite() && yR.isFinite() && yR - yL in 2.2..5.2 -> -(yL + yR) / 2 * (wMap / (yR - yL))
      yL.isFinite() && yL < 0 -> -(yL + wMap / 2)
      yR.isFinite() && yR > 0 -> -(yR - wMap / 2)
      else -> Double.NaN
    }
    val lim = wMap / 2 + 0.4
    if (meas.isFinite()) st.off = if (fresh) meas.coerceIn(-lim, lim) else st.off + (meas.coerceIn(-lim, lim) - st.off) * (1 - exp(-dt / 0.25))
    else st.off *= exp(-dt / 3.0)
    var slope = 0.0; var ns = 0
    for (i in 1..2) if (ok(i) && cam.lx[i].last() >= 10f) { slope += (LaneFit.interp(cam.lx[i], cam.ly[i], 10.0) - LaneFit.interp(cam.lx[i], cam.ly[i], 2.0)) / 8.0; ns++ }
    if (ns > 0) { val y = atan(slope / ns).coerceIn(-0.35, 0.35); st.yaw = if (fresh) y else st.yaw + (y - st.yaw) * (1 - exp(-dt / 0.3)) }
    else st.yaw *= exp(-dt / 3.0)

    val c = lanes[shown]
    val h = bearingAhead(c.lane.x, c.lane.y, c.pr, 6.0) - st.yaw
    val pose = LaneFit.Pose(c.pr.cx + cos(c.pr.bearing) * st.off, c.pr.cy - sin(c.pr.bearing) * st.off, h)
    val same = w.lanes.filter { l -> lanes.any { it.lane === l } }
    val match = LaneFit.Match(bestWay, shown + 1, n, lanes.map { it.lane.turn }, w.name, w.hw, abs(st.off), if (c.lane.dir < 0) -1 else 1)
    val rc = LaneFit.reachFrom(b, match, pose, now)
    var nl = 0; for (i in 0 until min(4, cam.lp.size)) if (cam.lp[i] >= 0.35f) nl++
    var ne = 0; for (i in 0 until min(2, cam.estd.size)) if (cam.estd[i] <= 1.2f) ne++
    val f = LaneFit.Fit(b, pose, odo, now, 0.0, false, 0.0, st.yaw, match, rc, nl, ne)
    st.wayId = w.id; st.p = p; st.lat = lat; st.shown = shown; st.lastMs = now; st.pose = pose; st.odo = odo; st.built = b
    st.reach = f.reachIds
    st.wayIdx = bestWay; st.mlane = c.lane; st.px = c.pr.cx; st.py = c.pr.cy; st.wMap = wMap
    status = String.format(java.util.Locale.US, "lane %d of %d (%.0f %%) on %s, %+.1f m in it%s", shown + 1, n, p[shown] * 100,
      w.name.ifEmpty { w.hw }, st.off, if (same.size != n) " ?" else "")
    return f
  }

  private fun reset(st: State) { st.expect = null; st.mlane = null; st.wayIdx = -1; st.farMs = 0L; st.pose = null; st.p = DoubleArray(0); st.shown = -1; st.cand = -1; st.wayId = Long.MIN_VALUE; st.reach = null }

  private fun norm(p: DoubleArray) {
    var s = 0.0
    for (v in p) s += v
    if (s <= 0 || !s.isFinite()) { p.fill(1.0 / max(1, p.size)); return }
    for (i in p.indices) p[i] = max(1e-4, p[i] / s)
  }

  /** the lane's width on the map: the distance to the lane beside it (12 ft when it's the only one) */
  private fun laneWidth(lat: DoubleArray, i: Int): Double {
    val d = when {
      lat.size < 2 || i !in lat.indices -> 3.6
      i + 1 < lat.size -> lat[i + 1] - lat[i]
      else -> lat[i] - lat[i - 1]
    }
    return if (d in 2.4..5.0) d else 3.6
  }
}
