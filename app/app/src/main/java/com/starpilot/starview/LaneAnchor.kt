package com.starpilot.starview

import kotlin.math.PI
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
  /** 2.90.5: our lane and the lanes our way (from the left, 1..) by the RTK fix, for the lane card: only on a map the
   *  camera has shown to be right here, or during a lane change the fix is following; null otherwise. [rtkLaneMs]: when */
  @Volatile var rtkLane: IntArray? = null
  @Volatile var rtkLaneMs = 0L
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
    /** from LaneRibbon: for those roads, what to add to a lane's place from the left to get its number along the
     *  thread - the same lane keeps its number from one piece of road to the next */
    internal var laneNums: Map<Long, Int>? = null
    // for LaneRibbon: the road piece and lane we're on, the point of that lane's middle beside us, its width on the map
    internal var wayIdx = -1
    internal var mlane: LaneMap.MLane? = null
    internal var px = 0.0; internal var py = 0.0
    internal var wMap = 3.6
    // 2.90.2: parked at the curb. How likely we're in the parking lane beside our edge lane (only on a road the map
    // has one, and only with a precise fix: it lies a lane's width from the lane), whether we're drawn there, and so
    // how far right of our lane's middle that is (0 = in the lane; LaneRibbon slides us there and back)
    internal var park = 0.02
    internal var parkMs = 0L
    internal var parked = false
    internal var parkedD = 0.0
    /** since when the GPS heading (moving) has been half a turn from ours: we were set down the wrong way round */
    internal var flipMs = 0L
    // 2.90.5: a lane change by the RTK fix and the car's own motion. Our heading against the road's (radians, right =
    // positive: the RTK's while it has a fix and we move, carried on by the gyro between), whether the last run had a
    // perfect fix, the road's bearing then; the lane change being followed - its way (1 right, -1 left), how far
    // sideways we have moved since it began (m, its way = positive), how many lanes over that has counted, when the
    // signal was last on and when the fix was last perfect, and the last crossing it counted (way, when)
    internal var hRel = Double.NaN
    internal var rtkGood = false
    internal var roadBrg = Double.NaN
    internal var trk = false; internal var trkDir = 0; internal var trkLat = 0.0; internal var trkN = 0
    internal var trkSignalMs = 0L; internal var trkFixMs = 0L
    internal var rtkShiftDir = 0; internal var rtkShiftMs = 0L
    /** 2.90.5: how far the map's lanes have been shown to lie where they are (0..1): the RTK fix's place in our lane on
     *  the map against the camera's place in our lane, while the camera sees our lines */
    internal var trust = 0.0
    /** the lane drawn and how sure (for the tests and the developer's line) */
    val lane: Int get() = shown
    val sure: Double get() = p.getOrElse(shown) { 0.0 }
  }

  private val live = State()
  private val ribbon = LaneRibbon.State()

  /** LaneFit's worker thread, after its own fit. */
  internal fun tick(b: LaneMap.Built, gps: LaneFit.Pose, acc: Double, cam: LaneFit.Cam, odo: DoubleArray, now: Long, hint: LaneSceneView.Lanes?, speed: Double,
                    rtkAcc: Double = Double.NaN, signal: Int = 0) {
    val shift = pendingShift
    pendingShift = 0
    fit = try {
      // where we are (which road, which lane), then the road drawn around it (LaneRibbon)
      if (run(b, gps, acc, cam, odo, now, live, hint, marks, shift, speed, rtkAcc, signal) == null) null else LaneRibbon.update(b, live, ribbon, cam, odo, now)
    } catch (e: Throwable) { android.util.Log.w("StarView/LaneAnchor", "run", e); null }
  }

  private class Cand(val wi: Int, val lane: LaneMap.MLane, val pr: Proj)

  /**
   * One step. [gps]: the GPS place and heading on the map (x / y / h may be NaN: standing still, no fix); [hint]: the
   * lane card's count; [shift]: we crossed a line (1 right, -1 left). null = no road here.
   */
  fun run(b: LaneMap.Built, gps: LaneFit.Pose, acc: Double, cam: LaneFit.Cam, odo: DoubleArray, now: Long, st: State,
          hint: LaneSceneView.Lanes?, marks: Array<LaneMarks.Mark>?, shift: Int, speed: Double = Double.NaN,
          rtkAcc: Double = Double.NaN, signal: Int = 0): LaneFit.Fit? {
    val fresh = st.pose == null || st.built == null || !LaneFit.sameFrame(st.built!!, b) || now - st.lastMs > 3000 || now < st.lastMs
    val ref = if (fresh) null else LaneFit.carry(st.pose!!, st.odo!!, odo)
    // a GPS place worth using: under a bridge deck or in a tunnel the fix drifts off by 100 ft and more (its own
    // accuracy says so) - then we go on along our lane by the car's own motion
    val gpsOk = gps.x.isFinite() && gps.y.isFinite() && acc <= 10.0
    val qx = if (gpsOk) gps.x else ref?.x ?: Double.NaN
    val qy = if (gpsOk) gps.y else ref?.y ?: Double.NaN
    // 2.90.2: the GPS heading comes from moving. Standing still it is noise (parked at the curb on Nov 18 it swung
    // round the whole compass in 5 minutes), and a road chosen by it was the wrong one or the wrong way round: then
    // only the heading we carried on from moving counts, and starting while standing, the side of the road we're on
    var gh = if (!speed.isFinite() || speed >= 2.0) gps.h else Double.NaN
    if (ref != null && gh.isFinite() && cos(gh - ref.h) < -0.3 && speed >= 3.0) {
      // moving, and the GPS says the other way round from the heading we carry (set down while standing): start over
      if (st.flipMs == 0L) st.flipMs = now
      if (now - st.flipMs > 1500) { st.flipMs = 0L; reset(st); status = "turned round"; return null }
    } else st.flipMs = 0L
    var href = ref?.h ?: gh
    if (!href.isFinite() && qx.isFinite() && qy.isFinite()) href = standingHeading(b, qx, qy)
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
      // at 45 mph and more we are on the highway, not on the street under it (on the recorded drive the first place
      // found on an elevated highway was the boulevard below, for 16 s)
      if (speed.isFinite() && speed > 20.0 && w.hw !in LaneMap.FAST) score += 6.0
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
    // every lane of that road our way, left to right in the road's own order. (Only the lanes whose lines were beside
    // us, ordered by where those lines were, lost and swapped lanes at the ends of a piece: the map bends the lines
    // together there, and some end before others.)
    val dir = if (cands.first { it.wi == bestWay }.lane.dir < 0) -1 else 1
    val lanes = LaneRibbon.lanesOf(w, dir, qx, qy).map { Cand(bestWay, it.first, it.second) }
    val n = lanes.size
    val lat = DoubleArray(n) { -lanes[it].pr.side }

    // ---------------------------------------------------------------- which lane
    var p = DoubleArray(n)
    val hop = if (fresh || w.id == st.wayId || n == 0) null else st.laneNums?.let { m -> val a = m[st.wayId]; val z = m[w.id]; if (a != null && z != null) a - z else null }
    val sigGps = hypot(max(acc, 0.3), 2.0)                 // the map's own lines are a few feet off too
    if (fresh || st.p.isEmpty()) {
      for (j in 0 until n) p[j] = exp(-0.5 * (lat[j] / sigGps).pow(2)) + 0.02
    } else if (w.id != st.wayId || n != st.p.size) {
      // another piece of road (or a lane more or fewer). LaneRibbon knows how the lanes of the two carry on into one
      // another (an exit takes our road's right lanes, a ramp from the left brings the first lanes...): each lane takes
      // over from the lane it is. Without that, from the lanes that were where it is on the map - which the map has
      // wrong right at an exit or a merge (it ends a ramp at the middle of the road it joins).
      if (hop != null) for (i in st.p.indices) p[(i + hop).coerceIn(0, n - 1)] += st.p[i]
      else for (j in 0 until n) { var s = 1e-3; for (i in st.p.indices) s += st.p[i] * exp(-0.5 * ((lat[j] - st.lat[i]) / 1.2).pow(2)); p[j] = s }
    } else p = st.p.copyOf()
    var shown = if (fresh || st.shown !in 0 until st.p.size) -1 else st.shown
    if (shown >= 0 && (w.id != st.wayId || n != st.p.size)) {
      // drawn in the lane that is ours (or the one where our lane was)
      val was = st.lat[shown]
      shown = if (hop != null) (shown + hop).coerceIn(0, n - 1) else (0 until n).minByOrNull { abs(lat[it] - was) } ?: -1
    }
    // ---------------------------------------------------------------- 2.90.5: a lane change by the RTK fix
    // With a perfect fix (under 2 ft: owner, Nov 18 - 1 ft was too tight for a float fix) and the blinker on (or
    // openpilot changing lanes), we follow how far we move
    // sideways against the road - which needs no map to be right - and are in the next lane when half way over. Between
    // fixes and for up to 4 s without one (a bridge, the elevated tracks) the car's own motion carries it: our heading
    // against the road, last from the RTK, turned by the gyro; a fix that jumps where the car didn't go is not taken.
    // Owner, Nov 18: the camera loses its lines while we cross, the Scene moved over 16 s after the change began.
    val rtkGood = gpsOk && rtkAcc.isFinite() && rtkAcc <= 0.6
    val sameRoad = !fresh && w.id == st.wayId && n == st.p.size && st.lat.size == n
    val refJ = if (shown in 0 until n) shown else -1
    val roadBrg = if (refJ >= 0) lanes[refJ].pr.bearing else Double.NaN
    val ds = st.odo?.let { (odo[3] - it[3]).coerceIn(0.0, 60.0) } ?: 0.0
    var dLat = Double.NaN
    if (sameRoad && refJ >= 0 && roadBrg.isFinite()) {
      if (st.hRel.isFinite() && st.roadBrg.isFinite() && st.odo != null)
        st.hRel = wrap(st.hRel - (odo[2] - st.odo!![2]) - wrap(roadBrg - st.roadBrg))
      val pred = if (st.hRel.isFinite()) ds * sin(st.hRel) else Double.NaN
      dLat = if (rtkGood && st.rtkGood) {
        val d = -(lat[refJ] - st.lat[refJ])
        if (pred.isFinite() && abs(d - pred) > 0.3 + 0.1 * ds) pred else d
      } else pred
    }
    if (rtkGood && speed.isFinite() && speed > 3.0 && gps.h.isFinite() && roadBrg.isFinite()) st.hRel = wrap(gps.h - roadBrg)
    if (fresh || !roadBrg.isFinite()) st.hRel = Double.NaN
    st.rtkGood = rtkGood; st.roadBrg = roadBrg
    if (rtkGood) st.trkFixMs = now
    if (signal != 0) st.trkSignalMs = now
    var rtkShift = 0
    if (!st.trk && signal != 0 && rtkGood && sameRoad && refJ >= 0) {
      st.trk = true; st.trkDir = if (signal < 0) -1 else 1; st.trkLat = 0.0; st.trkN = 0
    }
    if (st.trk) {
      if (dLat.isFinite()) { st.trkLat += st.trkDir * dLat; st.off += dLat }
      val lw = laneWidth(lat, refJ.coerceAtLeast(0))
      if (st.trkLat >= (st.trkN + 0.5) * lw + 0.15 && st.trkN < 3) { st.trkN++; rtkShift = st.trkDir }
      else if (st.trkLat <= (st.trkN - 0.5) * lw - 0.15 && st.trkN > 0) { st.trkN--; rtkShift = -st.trkDir }
      if (rtkShift != 0) { st.rtkShiftDir = rtkShift; st.rtkShiftMs = now }
      // done: 3 s after the signal went off, the way changed under us, or 4 s without a perfect fix
      if (now - st.trkSignalMs > 3000 || !sameRoad || now - st.trkFixMs > 4000) st.trk = false
    }
    // while we follow it ourselves the camera's and openpilot's word on the same crossing isn't counted again
    val viewShift = if (st.trk || (shift == st.rtkShiftDir && now - st.rtkShiftMs in 0..6000)) 0 else shift
    val sh = viewShift + rtkShift
    if (sh != 0 && n > 0) {
      // we drove across a line: the whole belief moves one lane over with us
      val q = DoubleArray(n)
      for (j in 0 until n) q[(j + sh).coerceIn(0, n - 1)] += p[j]
      p = q
      if (shown >= 0) shown = (shown + sh).coerceIn(0, n - 1)
      st.off -= sh * laneWidth(lat, shown.coerceAtLeast(0))
    }
    norm(p)
    // 2.90.4: standing still we can't change lanes, and what we see doesn't change either - only what's in the way: at a
    // light the car beside us hid the line on our left, the lane card counted no lane there (99 %) and in 15 s the belief
    // moved us over into it, the car beside us drawn across the yellow line (owner, Nov 18: "messed up position wise").
    // So standing, the belief stays as it was when we stopped (from the start, the GPS place alone).
    val standing = speed.isFinite() && speed < 1.0
    // a little doubt creeps in with time (a lane change the camera missed)
    val eps = if (standing) 0.0 else min(0.02, 0.03 * dt)
    val q = DoubleArray(n)
    for (j in 0 until n) { q[j] += p[j] * (1 - 2 * eps); q[max(0, j - 1)] += p[j] * eps; q[min(n - 1, j + 1)] += p[j] * eps }
    p = q
    // what speaks for each lane now. The same reading comes again and again (5 times a second) with the same error, and
    // the lane card keeps a wrong count for seconds on end (7 s at a time in the owner's video). We did not change lanes
    // without crossing a line, so apart from that the belief may only move on what lasts: each reading counts once per
    // 8 s (once per 2 s in the first 10 s, while finding the lane).
    if (fresh) st.bornMs = now
    val a = if (standing) 0.0 else min(1.0, dt / (if (now - st.bornMs < 10_000) 2.0 else 8.0))
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
    // 2.90.5: with a perfect fix on a road whose lanes the camera has shown to lie where the map has them, the fix says
    // which lane we're in - strongly, at every step (a map 10 ft off, as on a street on Nov 18, never gets here)
    if (rtkGood && st.trust >= 0.6 && !standing) {
      val k = min(1.0, dt / 0.5)
      val sig = hypot(0.8, rtkAcc)
      for (j in 0 until n) p[j] *= exp(-0.5 * (lat[j] / sig).pow(2)).pow(k) + 1e-6
    }
    norm(p)
    // the lane drawn: the likeliest at the start; later another one only once it has been clearly the likelier for 3 s
    val top = (0 until n).maxByOrNull { p[it] } ?: 0
    if (shown < 0) { shown = top; st.cand = -1 }
    else if (top != shown && p[top] > 0.75) {
      if (st.cand != top) { st.cand = top; st.candMs = now }
      if (now - st.candMs >= 3000) { shown = top; st.cand = -1 }
    } else st.cand = -1

    // ---------------------------------------------------------------- parked at the curb (2.90.2)
    val wMap = laneWidth(lat, shown)
    run {
      // the parking lane beside our edge lane: on the right; on a one-way road on the left too
      val pr = if (dir > 0) w.parkR else w.parkL
      val pl = if (w.oneway) (if (dir > 0) w.parkL else w.parkR) else 0.0
      val side = when { pr > 0 && shown == n - 1 -> 1; pl > 0 && shown == 0 -> -1; else -> 0 }
      val dPark = if (side == 0) 0.0 else side * (wMap / 2 + (if (side > 0) pr else pl) / 2)
      val precise = gpsOk && acc <= 1.5
      if (side == 0 || (speed.isFinite() && speed > 3.0) || atCrossing(b, bestWay, qx, qy)) {
        // driving (or no parking lane here, or waiting at a crossing - where the fix being a few feet off to the side
        // looked like pulling over): not parked, and soon not thought so either
        st.park = max(0.02, st.park * exp(-dt * 2.0))
      } else if (precise) {
        // where the fix lies: our lane's middle or the parking lane's (the map's lines are a foot or so off too)
        val sig = hypot(max(acc, 0.2), 1.0)
        val off = LaneRibbon.fromLane(w, dir, lanes[shown].lane, qx, qy).takeIf { it.isFinite() } ?: -lat[shown]
        val inLane = exp(-0.5 * (off / sig).pow(2))
        val inPark = exp(-0.5 * ((off - dPark) / sig).pow(2))
        var odds = st.park / (1 - st.park)
        odds *= ((inPark + 1e-6) / (inLane + 1e-6)).pow(min(1.0, dt / 1.5))
        st.park = (odds / (1 + odds)).coerceIn(0.02, 0.98)
      }
      val slow = !speed.isFinite() || speed < 1.5
      if (!st.parked && side != 0 && slow && st.park > 0.8) { if (st.parkMs == 0L) st.parkMs = now; if (now - st.parkMs >= 2000 || fresh) st.parked = true }
      else if (st.parked && (side == 0 || st.park < 0.4 || !slow && speed > 3.0)) st.parked = false
      if (!(st.park > 0.8 && slow)) st.parkMs = 0L
      st.parkedD = if (st.parked) dPark else 0.0
    }

    // ---------------------------------------------------------------- where in the lane, which way we point
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
    // 2.90.5: the map's lanes against the camera: the RTK fix's place right of our lane's middle on the map, and the
    // camera's place in our lane, while it sees our lines and we aren't changing lanes. Within 2 ft: the map is right
    // here (2 s of that to trust it); 3 ft and more apart: it isn't (gone in half a second)
    if (rtkGood && !st.trk && meas.isFinite() && !st.parked && shown in 0 until n) {
      val err = -lat[shown] - meas
      st.trust = if (abs(err) < 0.6) min(1.0, st.trust + dt / 2.0) else if (abs(err) > 1.0) max(0.0, st.trust - dt / 0.5) else st.trust
    } else if (!rtkGood) st.trust = max(0.0, st.trust - dt / 10.0)
    if (st.trk && st.rtkGood) { /* moved sideways by the fix above */ }
    else if (st.parked) st.off *= exp(-dt / 1.0)             // at the curb there are no lines of our own to go by
    else if (meas.isFinite()) st.off = if (fresh) meas.coerceIn(-lim, lim) else st.off + (meas.coerceIn(-lim, lim) - st.off) * (1 - exp(-dt / 0.25))
    else st.off *= exp(-dt / 3.0)
    var slope = 0.0; var ns = 0
    for (i in 1..2) if (ok(i) && cam.lx[i].last() >= 10f) { slope += (LaneFit.interp(cam.lx[i], cam.ly[i], 10.0) - LaneFit.interp(cam.lx[i], cam.ly[i], 2.0)) / 8.0; ns++ }
    if (st.trk && st.hRel.isFinite()) st.yaw = (-st.hRel).coerceIn(-0.35, 0.35)       // 2.90.5: turning across, as the van does
    else if (ns > 0) { val y = atan(slope / ns).coerceIn(-0.35, 0.35); st.yaw = if (fresh) y else st.yaw + (y - st.yaw) * (1 - exp(-dt / 0.3)) }
    else st.yaw *= exp(-dt / 3.0)

    val c = lanes[shown]
    val h = bearingAhead(c.lane.x, c.lane.y, c.pr, 6.0) - st.yaw
    val pose = LaneFit.Pose(c.pr.cx + cos(c.pr.bearing) * (st.off + st.parkedD), c.pr.cy - sin(c.pr.bearing) * (st.off + st.parkedD), h)
    val same = w.lanes.filter { l -> lanes.any { it.lane === l } }
    val match = LaneFit.Match(bestWay, shown + 1, n, lanes.map { it.lane.turn }, w.name, w.hw, abs(st.off), if (c.lane.dir < 0) -1 else 1)
    val rc = LaneFit.reachFrom(b, match, pose, now)
    var nl = 0; for (i in 0 until min(4, cam.lp.size)) if (cam.lp[i] >= 0.35f) nl++
    var ne = 0; for (i in 0 until min(2, cam.estd.size)) if (cam.estd[i] <= 1.2f) ne++
    val f = LaneFit.Fit(b, pose, odo, now, 0.0, false, 0.0, st.yaw, match, rc, nl, ne)
    st.wayId = w.id; st.p = p; st.lat = lat; st.shown = shown; st.lastMs = now; st.pose = pose; st.odo = odo; st.built = b
    // 2.90.5: for the lane card - our lane by the RTK fix on a map shown to be right, or a lane change it is following
    rtkLane = if (rtkGood && (st.trust >= 0.6 || st.trk) && n in 1..6) intArrayOf(shown + 1, n) else null
    rtkLaneMs = now
    st.reach = f.reachIds
    st.wayIdx = bestWay; st.mlane = c.lane; st.px = c.pr.cx; st.py = c.pr.cy; st.wMap = wMap
    status = String.format(java.util.Locale.US, "lane %d of %d (%.0f %%) on %s, %+.1f m in it%s%s", shown + 1, n, p[shown] * 100,
      w.name.ifEmpty { w.hw }, st.off, if (same.size != n) " ?" else "",
      if (st.parked) String.format(java.util.Locale.US, ", parked (%.0f %%)", st.park * 100) else if (st.park > 0.1) String.format(java.util.Locale.US, ", parking %.0f %%", st.park * 100) else "")
    return f
  }

  /** Standing still with no heading yet: the direction of the lane nearest to us - we stand on our side of the road
   *  (right-hand traffic: next to our lanes, or in the parking lane beside them). NaN = no road near: none within 33 ft
   *  (2.90.3: 20 ft left the Scene empty parked at the curb of a street whose middle line the map has 10 ft off - the
   *  RTK fix 26 ft from it, 21 ft from our lane's middle). */
  private fun standingHeading(b: LaneMap.Built, x: Double, y: Double): Double {
    var best = 10.0; var h = Double.NaN
    for (w in b.ways) {
      if (!w.near(x, y, 15.0)) continue
      for (ln in w.lanes) { val pr = project(ln.x, ln.y, x, y) ?: continue; if (pr.over < 3.0 && pr.dist < best) { best = pr.dist; h = pr.bearing } }
    }
    return h
  }

  /** whether (x, y) is where a street crosses or meets road [wi]: within that street's half width + 20 ft of where
   *  the two meet (its stop line and crosswalk; nobody parks there) */
  private fun atCrossing(b: LaneMap.Built, wi: Int, x: Double, y: Double): Boolean {
    if (!x.isFinite() || !y.isFinite()) return false
    val w = b.ways[wi]
    for (k in w.keys.indices) {
      val d = hypot(w.px[k] - x, w.py[k] - y)
      if (d > 30.0) continue
      val a = max(0, k - 1); val z = min(w.px.size - 1, k + 1)
      if (a == z) continue
      val hx = w.px[z] - w.px[a]; val hy = w.py[z] - w.py[a]; val hl = hypot(hx, hy).coerceAtLeast(1e-6)
      for (code in b.at[w.keys[k]] ?: continue) {
        val o = code ushr 20; val ov = code and 0xfffff
        if (o == wi || o !in b.ways.indices) continue
        val u = b.ways[o]
        val un = if (ov + 1 < u.px.size) ov + 1 else ov - 1
        if (un < 0) continue
        val ux = u.px[un] - u.px[ov]; val uy = u.py[un] - u.py[ov]; val ul = hypot(ux, uy).coerceAtLeast(1e-6)
        if (abs(hx * uy - hy * ux) / (hl * ul) < 0.5) continue          // the same road going on, or a slip road
        if (d < u.width / 2 + 6.0) return true
      }
    }
    return false
  }

  private fun reset(st: State) { st.park = 0.02; st.parked = false; st.parkedD = 0.0; st.parkMs = 0L; st.expect = null; st.laneNums = null; st.mlane = null; st.wayIdx = -1; st.farMs = 0L; st.pose = null; st.p = DoubleArray(0); st.shown = -1; st.cand = -1; st.wayId = Long.MIN_VALUE; st.reach = null; st.trk = false; st.hRel = Double.NaN; st.rtkGood = false; st.trust = 0.0 }

  private fun wrap(a: Double): Double { var x = a; while (x > PI) x -= 2 * PI; while (x < -PI) x += 2 * PI; return x }

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
