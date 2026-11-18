package com.starpilot.starview

import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.ceil
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin

/**
 * 2.90.0 (owner, Nov 17, on the map drawn as it is at an exit and on a ramp: "still looks horrible ... rebuild the
 * entire thing", then, on two mock-ups of this: "definitely looks much better"): the road is generated, not traced.
 *
 * The map's own lines (LaneMap: every road piece with its lanes, offset from OpenStreetMap's centre line) are fine on
 * a straight road and a tangle wherever roads meet: pieces that don't join, lines crossing lines, every ramp of an
 * interchange with its own markings. So the map is only asked for facts - where our road goes, how many lanes it has
 * our way and the other way, where another road leaves or crosses it - and the picture is made from those:
 *
 *  - the thread: our lane followed through the map, from 230 ft behind us to 1400 ft ahead, carrying on at each end
 *    of a road piece onto the piece that continues it (same road, least turn; onto a ramp only when nothing else
 *    does - or when openpilot's own path already heads that way, [review]). It is cut into steps of 10 ft and
 *    smoothed over 160 ft, and a step once made does not change, so the road ahead cannot wobble as we come nearer;
 *  - the ribbon: around the thread, the lanes at an even 12 ft, their count easing over 140 ft where it changes; our
 *    way's left edge yellow (double on a two-way road, with the other way's lanes beyond it in grey), the right edge
 *    white, dashes between lanes - all fixed to the road and drawn off the one smooth thread, so lines cannot cross
 *    or pile up. Inside a crossing the lines stop;
 *  - branches: a road that leaves ours ahead, or crosses it, is a plain strip of road going its way (no markings but
 *    its kerbs). Roads joining from behind and roads that don't touch ours are not drawn at all.
 *
 * Where we are on it comes from LaneAnchor (which lane, where in the lane, which way we point). The result is a
 * LaneMap.Built of its own with a LaneFit.Fit on it, so the Scene draws it like any map; [Geo] goes with it for the
 * things placed along the road (the path openpilot drives, the cars around).
 */
object LaneRibbon {
  const val STEP = 3.05                 // one dash; a dash and its gap are 4 steps
  const val LW = 3.6
  private const val AHEAD = 430.0
  private const val BEHIND = 70.0
  private const val SHOULDER = 0.9
  private const val KS = 8               // the thread is smoothed over +-8 steps
  private const val KN = 7               // the lane count eases over +-7 steps
  private val MINOR = setOf("residential", "service", "living_street", "unclassified", "road", "track")

  class Smp(val x: Double, val y: Double, val wayId: Long, val ci: Int, val n: Int, val opp: Int, val oneway: Boolean, val hw: String) {
    var sx = x; var sy = y
    var nl = ci.toDouble(); var nr = (n - 1 - ci).toDouble(); var no = opp.toDouble()
    var done = false; var gap = false
    var openL = false; var openR = false   // a road leaves on that side here: no edge line across its mouth
    var tx = 0.0; var ty = 1.0           // the smoothed thread's direction here
  }

  /** a road meeting ours near step [idx] of the thread: along way [wayId] from its point [v] going [d] */
  internal class Arm(val idx: Long, val wayId: Long, val v: Int, val d: Int)

  /** where the road could go on two ways (step [idx]): what was taken, the others, and the walk as it stood there */
  internal class Fork(val idx: Long, val key: Long, val took: Long, val opts: List<LongArray>, val wayId: Long, val dir: Int,
                      val x: Double, val y: Double, val h: Double, val rem: Double, val prevWay: Long, val prevV: Int, val prevD: Int) {
    var votes = 0; var want = -1
  }

  class State {
    internal val smp = ArrayList<Smp>()
    internal var first = 0L                 // the number of smp[0] (steps are numbered for good)
    internal val arms = ArrayList<Arm>()
    internal val forks = ArrayList<Fork>()
    internal val forced = HashMap<Long, Long>()   // crossing point -> the way to take there (openpilot heads that way)
    internal var endWay = Long.MIN_VALUE; internal var endDir = 0
    internal var endX = 0.0; internal var endY = 0.0; internal var endH = 0.0
    internal var rem = 0.0                  // m to the next step
    internal var prevWay = Long.MIN_VALUE; internal var prevV = 0; internal var prevD = 0   // the piece we came along
    internal var dead = false
    internal var van = 0.0                  // where we are on the thread (step number, with a fraction)
    internal var pose: LaneFit.Pose? = null
    internal var odoD = Double.NaN          // the car's own distance count at the last step
    internal var index: HashMap<Long, Int>? = null; internal var indexOf: LaneMap.Built? = null
    internal fun at(a: Long): Smp? = smp.getOrNull((a - first).toInt())
    val steps: Int get() = smp.size
    /** tests: how often the road ahead was laid again (a fork taken the other way, a road left) */
    var relaid = 0; internal set
  }

  /**
   * The ribbon as numbers, for what is placed along it: [place] turns "so far ahead along the road, so far right of
   * the thread" into a place on the map.
   */
  class Geo(private val sx: DoubleArray, private val sy: DoubleArray, private val tx: DoubleArray, private val ty: DoubleArray,
            private val nl: DoubleArray, private val nr: DoubleArray, private val no: DoubleArray, private val two: BooleanArray,
            /** our step (index into these, with a fraction) and how far right of the thread we are, the middle of our lane */
            val van: Double, val d: Double, val laneD: Double) {
    val size get() = sx.size
    /** (x, y, bearing) at [ahead] m along the road from us, [d] m right of the thread; null past its ends */
    fun place(ahead: Double, d: Double): DoubleArray? {
      val s = van + ahead / STEP
      val i = s.toInt()
      if (s < 0 || i >= sx.size - 1) return null
      val t = s - i
      val x = sx[i] + (sx[i + 1] - sx[i]) * t; val y = sy[i] + (sy[i + 1] - sy[i]) * t
      return doubleArrayOf(x + ty[i] * d, y - tx[i] * d, atan2(tx[i], ty[i]))
    }
    /** where we are [ahead] m further along the road than at the fit, as far right of the thread as then, pointing
     *  [yaw] off the road's direction; null past the thread's end */
    fun poseAt(ahead: Double, yaw: Double): LaneFit.Pose? {
      val p = place(ahead, d) ?: return null
      val q = place(ahead + 2 * STEP, d) ?: p
      return LaneFit.Pose(p[0], p[1], q[2] - yaw)
    }
    /** our way's lanes at [ahead]: (left edge, right edge) in m right of the thread, and the far edge of the other way's
     *  lanes (= the left edge on a one-way road) */
    fun edges(ahead: Double): DoubleArray {
      val i = (van + ahead / STEP).toInt().coerceIn(0, sx.size - 1)
      val l = -(nl[i] + 0.5) * LW
      return doubleArrayOf(l, (nr[i] + 0.5) * LW, if (two[i]) l - no[i] * LW else l)
    }
  }

  private fun wayIndex(b: LaneMap.Built, st: State): HashMap<Long, Int> {
    var m = st.index
    if (m == null || st.indexOf !== b) { m = HashMap(b.ways.size * 2); for ((i, w) in b.ways.withIndex()) m[w.id] = i; st.index = m; st.indexOf = b }
    return m
  }

  private fun wrap(a: Double) = Math.IEEEremainder(a, 2 * Math.PI)
  private fun link(hw: String) = hw.endsWith("_link")

  /** the lanes of way [w] going [dir] along its points, left to right as driven, seen from ([x], [y]) */
  private fun lanesOf(w: LaneMap.BWay, dir: Int, x: Double, y: Double): List<Pair<LaneMap.MLane, LaneAnchor.Proj>> {
    val out = ArrayList<Pair<LaneMap.MLane, LaneAnchor.Proj>>()
    for (l in w.lanes) if ((l.dir < 0) == (dir < 0)) { val p = LaneAnchor.project(l.x, l.y, x, y) ?: continue; out.add(l to p) }
    out.sortBy { -it.second.side }
    return out
  }

  /** The thread from scratch at lane [lane] of way [wi], where ([x], [y]) is on it. */
  private fun start(b: LaneMap.Built, st: State, wi: Int, lane: LaneMap.MLane, x: Double, y: Double) {
    st.smp.clear(); st.arms.clear(); st.forks.clear(); st.forced.clear()
    st.first = 0L; st.dead = false; st.rem = 0.0; st.prevWay = Long.MIN_VALUE
    st.relaid++
    val w = b.ways[wi]
    val dir = if (lane.dir < 0) -1 else 1
    val ls = lanesOf(w, dir, x, y)
    val ci = ls.indexOfFirst { it.first === lane }.coerceAtLeast(0)
    val p = LaneAnchor.project(lane.x, lane.y, x, y) ?: return
    val opp = w.lanes.size - ls.size
    // behind us: back along this lane, and on back along the roads that lead into it (what we drove before the
    // thread began - the road must not start under the van)
    val back = ArrayList<Smp>()
    var need = STEP; var done = 0.0
    var qx = p.cx; var qy = p.cy
    var bw = w; var bwi = wi; var bdir = dir; var bl = lane; var bci = ci; var bn = ls.size; var bopp = opp
    var k = p.k - 1
    var hops = 0
    while (done < BEHIND && hops < 8) {
      var h = Double.NaN
      while (k >= 0 && done < BEHIND) {
        val ax = bl.x[k].toDouble(); val ay = bl.y[k].toDouble()
        val l = hypot(qx - ax, qy - ay)
        if (l > 1e-6) h = atan2(qx - ax, qy - ay)
        var at = need
        while (l > 1e-6 && at <= l && done < BEHIND) { back.add(Smp(qx + (ax - qx) * at / l, qy + (ay - qy) * at / l, bw.id, bci, bn, bopp, bw.oneway, bw.hw)); at += STEP; done += STEP }
        need = at - l
        qx = ax; qy = ay; k--
      }
      if (done >= BEHIND) break
      // the road that leads into this piece's start (least turn)
      val sv = if (bdir > 0) 0 else bw.px.size - 1
      if (!h.isFinite()) h = atan2(bw.px[sv + bdir] - bw.px[sv], bw.py[sv + bdir] - bw.py[sv])
      var nu = -1; var nd = 0; var bs = 1.0
      for (o in b.at[bw.keys[sv]] ?: IntArray(0)) {
        val ow = o ushr 20; val ov = o and 0xfffff
        if (ow == bwi) continue
        val u = b.ways[ow]
        for (d in intArrayOf(1, -1)) {
          if (ov - d !in u.px.indices || u.flow == -d || u.lanes.none { (it.dir < 0) == (d < 0) }) continue
          val turn = abs(wrap(atan2(u.px[ov] - u.px[ov - d], u.py[ov] - u.py[ov - d]) - h))
          if (turn < bs) { bs = turn; nu = ow; nd = d }
        }
      }
      if (nu < 0) break
      bwi = nu; bw = b.ways[nu]; bdir = nd
      val bls = lanesOf(bw, bdir, qx, qy)
      if (bls.isEmpty()) break
      bci = bls.indices.minByOrNull { abs(bls[it].second.side) } ?: 0
      bl = bls[bci].first; bn = bls.size; bopp = bw.lanes.size - bn
      k = bls[bci].second.k - 1
      hops++
    }
    back.reverse()
    st.smp.addAll(back)
    st.first = -back.size.toLong()
    st.endWay = w.id; st.endDir = dir; st.endX = p.cx; st.endY = p.cy; st.endH = p.bearing
    st.van = 0.0
  }

  /** Follows the road on from where the thread ends until it reaches [AHEAD] m past us. */
  private fun extend(b: LaneMap.Built, st: State) {
    val idx = wayIndex(b, st)
    var guard = 0
    while (!st.dead && (st.first + st.smp.size - st.van) * STEP < AHEAD && guard++ < 60) {
      val wi = idx[st.endWay] ?: run { st.dead = true; return }
      val w = b.ways[wi]
      val ls = lanesOf(w, st.endDir, st.endX, st.endY)
      if (ls.isEmpty()) { st.dead = true; return }
      val ci = ls.indices.minByOrNull { abs(ls[it].second.side) } ?: 0
      val (lane, p) = ls[ci]
      val opp = w.lanes.size - ls.size
      val from = st.first + st.smp.size
      // along this lane to its end. The map's pieces rarely join exactly (the next piece's lane starts a few feet to
      // the side): the thread carries on from where it is and eases onto the lane over 150 ft - no jog at the joint
      var qx = p.cx; var qy = p.cy
      val jx = st.endX - p.cx; val jy = st.endY - p.cy
      val jog = hypot(jx, jy) > 0.3
      var run = 0.0
      var ox = st.endX; var oy = st.endY
      fun to(ax: Double, ay: Double) {
        val l = hypot(ax - qx, ay - qy)
        if (l < 1e-6) return
        var at = st.rem
        while (at <= l) {
          val k = if (jog) ((run + at) / 45.0).coerceIn(0.0, 1.0).let { 1 - it * it * (3 - 2 * it) } else 0.0
          ox = qx + (ax - qx) * at / l + jx * k; oy = qy + (ay - qy) * at / l + jy * k
          st.smp.add(Smp(ox, oy, w.id, ci, ls.size, opp, w.oneway, w.hw)); at += STEP
        }
        st.rem = at - l
        run += l
        st.endH = atan2(ax - qx, ay - qy)
        qx = ax; qy = ay
      }
      for (k in p.k until lane.x.size) to(lane.x[k].toDouble(), lane.y[k].toDouble())
      // (a piece shorter than the easing: the next one takes over from where the thread got to)
      val kEnd = if (jog) (run / 45.0).coerceIn(0.0, 1.0).let { 1 - it * it * (3 - 2 * it) } else 0.0
      st.endX = qx + jx * kEnd; st.endY = qy + jy * kEnd
      val to = st.first + st.smp.size
      // which road carries ours on
      val endV = if (st.endDir > 0) w.px.size - 1 else 0
      val key = w.keys[endV]
      var bestU = -1; var bestD = 0; var bestS = Double.MAX_VALUE
      val opts = ArrayList<LongArray>()
      val must = st.forced[key]
      for (o in b.at[key] ?: IntArray(0)) {
        val ow = o ushr 20; val ov = o and 0xfffff
        if (ow == wi) continue
        val u = b.ways[ow]
        for (d in intArrayOf(1, -1)) {
          if (ov + d !in u.px.indices || u.flow == -d) continue
          if (u.lanes.none { (it.dir < 0) == (d < 0) }) continue
          val turn = abs(wrap(atan2(u.px[ov + d] - u.px[ov], u.py[ov + d] - u.py[ov]) - st.endH))
          if (turn > 1.9) continue
          var s = Math.toDegrees(turn) + (if (link(u.hw) && !link(w.hw)) 25.0 else 0.0) - (if (u.name.isNotBlank() && u.name == w.name) 20.0 else 0.0) +
            4 * abs(u.rank - w.rank)
          if (turn < 1.0) opts.add(longArrayOf(u.id, ov.toLong(), d.toLong()))
          if (must != null && must == u.id) s = -1000.0
          if (s < bestS) { bestS = s; bestU = ow; bestD = d }
        }
      }
      // every other road at the points we passed on this piece: a branch
      for (v in w.px.indices) {
        val others = b.at[w.keys[v]] ?: continue
        if (others.size < 2) continue
        var near = -1L; var nd = 16.0
        for (a in from until to) { val s = st.at(a) ?: continue; val dd = hypot(s.x - w.px[v], s.y - w.py[v]); if (dd < nd) { nd = dd; near = a } }
        if (near < 0) continue
        for (o in others) {
          val ow = o ushr 20; val ov = o and 0xfffff
          if (ow == wi) continue
          val u = b.ways[ow]
          for (d in intArrayOf(1, -1)) {
            if (ov + d !in u.px.indices) continue
            if (v == endV && ow == bestU && d == bestD) continue          // that's our road going on
            if (u.id == st.prevWay && ov == st.prevV && d == st.prevD) continue   // and that's where we came from
            val rel = wrap(atan2(u.px[ov + d] - u.px[ov], u.py[ov + d] - u.py[ov]) - st.endH)
            val ang = abs(rel).let { min(it, Math.PI - it) }
            val crossing = ang > 0.6 && !link(u.hw)
            // a road crossing ours: no lines across the crossing. One leaving ahead at a slant (a ramp): our edge
            // line opens for its mouth on that side. One joining from behind is not drawn.
            if (!crossing && cos(rel) < 0.2) continue
            if (st.arms.none { it.wayId == u.id && it.v == ov && it.d == d }) st.arms.add(Arm(near, u.id, ov, d))
            if (crossing) {
              val half = ceil((u.width / 2 + 2.0) / STEP).toLong()
              for (a in near - half..near + half) st.at(a)?.gap = true
            } else for (a in near - 1..near + 8) st.at(a)?.let { if (sin(rel) > 0) it.openR = true else it.openL = true }
          }
        }
      }
      if (bestU < 0) { st.dead = true; break }
      if (opts.size >= 2) st.forks.add(Fork(to - 1, key, b.ways[bestU].id, opts, w.id, st.endDir, st.endX, st.endY, st.endH, st.rem, st.prevWay, st.prevV, st.prevD))
      st.prevWay = w.id; st.prevV = endV; st.prevD = -st.endDir
      st.endWay = b.ways[bestU].id; st.endDir = bestD
    }
  }

  /** Smooths what can be smoothed for good (a step needs its neighbours on both sides; the last ones wait). */
  private fun settle(st: State) {
    val n = st.smp.size
    for (i in 0 until n) {
      val s = st.smp[i]
      if (s.done) continue
      if (i + KS >= n && !st.dead) break
      var wx = 0.0; var wy = 0.0; var ws = 0.0
      for (j in -KS..KS) { val q = st.smp.getOrNull(i + j) ?: continue; val k = (KS + 1 - abs(j)).toDouble().let { it * it }; wx += q.x * k; wy += q.y * k; ws += k }
      s.sx = wx / ws; s.sy = wy / ws
      var l = 0.0; var r = 0.0; var o = 0.0; var c = 0.0
      for (j in -KN..KN) { val q = st.smp.getOrNull(i + j) ?: continue; val k = (KN + 1 - abs(j)).toDouble(); l += q.ci * k; r += (q.n - 1 - q.ci) * k; o += q.opp * k; c += k }
      s.nl = l / c; s.nr = r / c; s.no = o / c
      s.done = true
    }
    // directions, from the smoothed neighbours (settled ones only)
    for (i in 0 until n) {
      val s = st.smp[i]
      if (!s.done) break
      val a = st.smp[max(0, i - 1)]; val z = st.smp[min(n - 1, i + 1)].let { if (it.done) it else s }
      val l = hypot(z.sx - a.sx, z.sy - a.sy)
      if (l > 1e-6) { s.tx = (z.sx - a.sx) / l; s.ty = (z.sy - a.sy) / l } else if (i > 0) { s.tx = st.smp[i - 1].tx; s.ty = st.smp[i - 1].ty }
    }
  }

  /** where ([x], [y]) is along the thread: the step number with a fraction, near step [hint]; NaN when not near it */
  private fun station(st: State, x: Double, y: Double, hint: Double, reach: Int): Double {
    var best = 25.0; var res = Double.NaN
    val i0 = max(0, (hint - st.first).toInt() - reach); val i1 = min(st.smp.size - 2, (hint - st.first).toInt() + reach)
    for (i in i0..i1) {
      val a = st.smp[i]; val z = st.smp[i + 1]
      val dx = z.x - a.x; val dy = z.y - a.y
      val l2 = dx * dx + dy * dy
      if (l2 < 1e-6) continue
      val t = (((x - a.x) * dx + (y - a.y) * dy) / l2).coerceIn(0.0, 1.0)
      val d = hypot(x - (a.x + t * dx), y - (a.y + t * dy))
      if (d < best) { best = d; res = st.first + i + t }
    }
    return res
  }

  /**
   * A fork 80 - 360 ft ahead, looked at again with openpilot's own path ([cam]'s, in the car's frame): when the path
   * heads along another branch than the thread took (by 8 ft, three looks in a row), the thread is laid again from
   * the fork along that branch - before we get there, so the road under the van never changes.
   */
  private fun review(b: LaneMap.Built, st: State, cam: LaneFit.Cam): Boolean {
    val pose = st.pose ?: return false
    val xs = cam.pathX ?: return false; val ys = cam.pathY ?: return false
    if (xs.size < 3) return false
    val idx = wayIndex(b, st)
    for (fk in st.forks) {
      val ahead = (fk.idx - st.van) * STEP
      if (ahead < 25 || ahead > 110) continue
      var best = -1; var bestE = Double.MAX_VALUE; var tookE = Double.MAX_VALUE
      for ((k, o) in fk.opts.withIndex()) {
        val u = b.ways[idx[o[0]] ?: continue]
        // 100 ft along this branch
        var v = o[1].toInt(); val d = o[2].toInt()
        var len = 0.0; var qx = u.px[v]; var qy = u.py[v]
        while (v + d in u.px.indices && len < 30.0) {
          val l = hypot(u.px[v + d] - u.px[v], u.py[v + d] - u.py[v])
          if (len + l >= 30.0) { val t = (30.0 - len) / l; qx = u.px[v] + (u.px[v + d] - u.px[v]) * t; qy = u.py[v] + (u.py[v + d] - u.py[v]) * t; len = 30.0; break }
          len += l; v += d; qx = u.px[v]; qy = u.py[v]
        }
        if (len < 15.0) continue
        val f = pose.fwd(qx, qy)
        if (f < 10 || f > xs.last()) continue
        val e = abs(pose.right(qx, qy) - LaneFit.interp(xs, ys, f))
        if (o[0] == fk.took) tookE = e
        if (e < bestE) { bestE = e; best = k }
      }
      if (best >= 0 && fk.opts[best][0] != fk.took && tookE != Double.MAX_VALUE && tookE - bestE > 2.5) {
        if (fk.want == best) fk.votes++ else { fk.want = best; fk.votes = 1 }
        if (fk.votes >= 3) {
          // lay the road again from this fork along the other branch
          st.forced[fk.key] = fk.opts[best][0]
          val keep = (fk.idx - st.first + 1).toInt().coerceIn(0, st.smp.size)
          st.smp.subList(keep, st.smp.size).clear()
          for (i in max(0, keep - KS - 1) until keep) st.smp[i].done = false
          st.arms.removeAll { it.idx > fk.idx - 12 }
          st.endWay = fk.wayId; st.endDir = fk.dir; st.endX = fk.x; st.endY = fk.y; st.endH = fk.h; st.rem = fk.rem
          st.prevWay = fk.prevWay; st.prevV = fk.prevV; st.prevD = fk.prevD
          st.dead = false
          st.forks.removeAll { it.idx >= fk.idx }
          st.relaid++
          return true
        }
      } else { fk.votes = 0; fk.want = -1 }
    }
    return false
  }

  /**
   * One step, after LaneAnchor's: [an] is its memory (which road, which lane, where in it). Returns the ribbon and
   * where we are on it; null = no road (the Scene shows the camera's own).
   */
  fun update(b: LaneMap.Built, an: LaneAnchor.State, st: State, cam: LaneFit.Cam?, odo: DoubleArray, now: Long): LaneFit.Fit? {
    val lane = an.mlane ?: return null
    val wi = an.wayIdx
    if (wi !in b.ways.indices) return null
    val wayId = b.ways[wi].id
    // where we are on the thread; a thread that doesn't hold our road (we left it: an exit taken, a turn) starts over
    var s = if (st.smp.size < 2) Double.NaN else station(st, an.px, an.py, st.van, 40)
    var same: Smp? = null
    if (s.isFinite()) {
      val i = (s - st.first).toInt()
      for (j in 0..10) { same = st.smp.getOrNull(i + j)?.takeIf { it.wayId == wayId } ?: st.smp.getOrNull(i - j)?.takeIf { it.wayId == wayId }; if (same != null) break }
    }
    val fresh = same == null
    if (same == null) {
      start(b, st, wi, lane, an.px, an.py)
      extend(b, st); settle(st)
      s = station(st, an.px, an.py, 0.0, 40)
      if (!s.isFinite()) return null
      same = st.smp.getOrNull((s - st.first).toInt()) ?: return null
    }
    // how far along: the car's own distance count carries us on, and the GPS place only nudges it (a twentieth of the
    // difference each time) - taken as it comes, the GPS moved us a few feet back and forth along the road 5 times a
    // second, and every dash with us
    if (!fresh && st.odoD.isFinite() && odo.size > 3) {
      val pred = st.van + (odo[3] - st.odoD) / STEP
      if (abs(pred - s) * STEP < 15.0) s = pred + (s - pred) * 0.06
    }
    if (odo.size > 3) st.odoD = odo[3]
    st.van = s
    if (cam != null) review(b, st, cam)
    extend(b, st)
    // forget what is far behind (whole dashes, so the dashes stay where they are)
    val drop = (((s - st.first) * STEP - BEHIND - 4 * STEP) / (4 * STEP)).toInt() * 4
    if (drop > 0) { st.smp.subList(0, drop).clear(); st.first += drop; st.arms.removeAll { it.idx < st.first }; st.forks.removeAll { it.idx < st.first } }
    settle(st)
    // where we are on it: the lane LaneAnchor has us in, counted from the thread's lane
    val i = (s - st.first).toInt().coerceIn(0, st.smp.size - 2)
    val a = st.smp[i]; val z = st.smp[i + 1]
    if (!a.done || !z.done) return null
    val t = s - st.first - i
    val laneD = (an.lane - same.ci) * LW
    val d = laneD + an.off * (LW / an.wMap)
    val (out, geo) = build(b, st, now, i + t, d, laneD) ?: return null
    val cx = a.sx + (z.sx - a.sx) * t; val cy = a.sy + (z.sy - a.sy) * t
    val f = st.smp[min(st.smp.size - 1, i + 2)].let { if (it.done) it else z }
    val pose = LaneFit.Pose(cx + a.ty * d, cy - a.tx * d, atan2(f.tx, f.ty) - an.yaw)
    st.pose = pose
    return LaneFit.Fit(out, pose, odo, now, 0.0, false, 0.0, an.yaw, null, null, 0, 0).also { it.ribbon = geo }
  }

  private fun build(b: LaneMap.Built, st: State, now: Long, van: Double, vanD: Double, laneD: Double): Pair<LaneMap.Built, Geo>? {
    var n = st.smp.indexOfFirst { !it.done }.let { if (it < 0) st.smp.size else it }
    if (n < 4) return null
    val sm = st.smp
    // a road that comes back around (a loop ramp crossing over where we are now) is drawn up to where it has turned
    // 100 degrees from our direction: its far part would lie across our own road
    val vi = van.toInt().coerceIn(0, n - 1)
    val h0 = atan2(sm[vi].tx, sm[vi].ty)
    for (i in vi until n) if (abs(wrap(atan2(sm[i].tx, sm[i].ty) - h0)) > 1.75) { n = i; break }
    if (n < 4) return null
    val ways = ArrayList<LaneMap.BWay>()
    val idx = wayIndex(b, st)

    // ---------------------------------------------------------------- our road
    fun pt(i: Int, d: Double, xs: FloatArray, ys: FloatArray, k: Int) { val s = sm[i]; xs[k] = (s.sx + s.ty * d).toFloat(); ys[k] = (s.sy - s.tx * d).toFloat() }
    val lines = ArrayList<LaneMap.MLine>()
    /** a line at [d](step) wherever [on](step), cut at crossings; [dash]: it starts on a whole dash */
    fun line(style: Int, dash: Boolean, on: (Smp) -> Boolean, d: (Smp) -> Double) {
      var a = -1
      for (i in 0..n) {
        val ok = i < n && on(sm[i]) && !sm[i].gap
        if (ok && a < 0) { if (!dash || (st.first + i) % 4 == 0L) a = i }
        if (!ok && a >= 0) {
          if (i - a >= 2) { val xs = FloatArray(i - a); val ys = FloatArray(i - a); for (k in a until i) pt(k, d(sm[k]), xs, ys, k - a); lines.add(LaneMap.MLine(style, xs, ys)) }
          a = -1
        }
      }
    }
    fun twoWay(s: Smp) = !s.oneway
    fun plain(s: Smp) = twoWay(s) && s.hw in MINOR && s.n <= 1          // a small street: no line down its middle
    val left = { s: Smp -> -(s.nl + 0.5) * LW }
    line(LaneMap.SOLID_WHITE, false, { !it.openR }) { (it.nr + 0.5) * LW }
    line(LaneMap.EDGE_YELLOW, false, { !twoWay(it) && !it.openL }, left)
    line(LaneMap.DOUBLE_YELLOW, false, { twoWay(it) && !plain(it) }, left)
    for (j in 1..6) {
      line(LaneMap.DASH_WHITE, true, { it.nr >= j - 0.4 }) { (j - 0.5) * LW }
      line(LaneMap.DASH_WHITE, true, { it.nl >= j - 0.4 }) { -(j - 0.5) * LW }
      line(LaneMap.DASH_DIM, true, { twoWay(it) && it.no >= j + 0.6 }) { left(it) - j * LW }
    }
    line(LaneMap.SOLID_DIM, false, { twoWay(it) && !plain(it) && it.no > 0.4 }) { left(it) - it.no * LW }
    val farL = { s: Smp -> left(s) - (if (twoWay(s)) s.no * LW else 0.0) - SHOULDER }
    val farR = { s: Smp -> (s.nr + 0.5) * LW + SHOULDER }
    line(LaneMap.CURB, false, { !it.openL }) { farL(it) + 0.1 }
    line(LaneMap.CURB, false, { !it.openR }) { farR(it) - 0.1 }
    val lx = FloatArray(n); val ly = FloatArray(n); val rx = FloatArray(n); val ry = FloatArray(n)
    val px = DoubleArray(n); val py = DoubleArray(n)
    var minX = Double.MAX_VALUE; var minY = Double.MAX_VALUE; var maxX = -Double.MAX_VALUE; var maxY = -Double.MAX_VALUE
    for (i in 0 until n) {
      pt(i, farL(sm[i]), lx, ly, i); pt(i, farR(sm[i]), rx, ry, i)
      px[i] = sm[i].sx; py[i] = sm[i].sy
      minX = min(minX, px[i]); maxX = max(maxX, px[i]); minY = min(minY, py[i]); maxY = max(maxY, py[i])
    }
    val road = LaneMap.BWay(1L, sm[0].hw, "", true, 1, 2 * LW, px, py, LongArray(n), lines, lx, ly, rx, ry, emptyList(), emptyList(), 1,
      minX - 40, minY - 40, maxX + 40, maxY + 40)

    // ---------------------------------------------------------------- the roads that leave or cross it
    val vanI = van.toInt()
    for (arm in st.arms) {
      val at = (arm.idx - st.first).toInt()
      if (at !in 0 until n || at < vanI - 12 || at > vanI + 110) continue
      var wi = idx[arm.wayId] ?: continue
      var v = arm.v; var d = arm.d
      val cx = ArrayList<Double>(); val cy = ArrayList<Double>(); val hw = ArrayList<Double>()
      var len = 0.0
      var hops = 0
      var half = 3.0
      fun add(x: Double, y: Double) {
        // points at most 13 ft apart, so what lies on our road can be told
        if (cx.isNotEmpty()) {
          val l = hypot(x - cx.last(), y - cy.last())
          val m = ceil(l / 4.0).toInt().coerceAtLeast(1)
          val ax = cx.last(); val ay = cy.last()
          for (k in 1..m) { cx.add(ax + (x - ax) * k / m); cy.add(ay + (y - ay) * k / m); hw.add(half) }
          len += l
        } else { cx.add(x); cy.add(y); hw.add(half) }
      }
      while (len < 300.0 && hops < 12) {
        val u = b.ways[wi]
        half = (u.width / 2).coerceIn(2.2, 12.0)
        while (v in u.px.indices && len < 300.0) { add(u.px[v], u.py[v]); v += d }
        if (len >= 300.0) break
        // on along the road that carries this one on (least turn)
        val endV = v - d
        val h = if (cx.size >= 2) atan2(cx.last() - cx[cx.size - 2], cy.last() - cy[cy.size - 2]) else break
        var nu = -1; var nv = 0; var nd = 0; var bs = 1.3
        for (o in b.at[u.keys[endV]] ?: IntArray(0)) {
          val ow = o ushr 20; val ov = o and 0xfffff
          if (ow == wi) continue
          val q = b.ways[ow]
          for (dd in intArrayOf(1, -1)) {
            if (ov + dd !in q.px.indices) continue
            val turn = abs(wrap(atan2(q.px[ov + dd] - q.px[ov], q.py[ov + dd] - q.py[ov]) - h))
            if (turn < bs) { bs = turn; nu = ow; nv = ov + dd; nd = dd }
          }
        }
        if (nu < 0) break
        wi = nu; v = nv; d = nd; hops++
      }
      val m = cx.size
      if (m < 3 || len < 12.0) continue
      // a branch that ends in sight narrows to a point over its last 100 ft (never a road sawn off square)
      if (len < 290.0) { var back = 0.0; for (i in m - 1 downTo 1) { hw[i] = hw[i] * min(1.0, back / 30.0); back += hypot(cx[i] - cx[i - 1], cy[i] - cy[i - 1]) } }
      val alx = FloatArray(m); val aly = FloatArray(m); val arx = FloatArray(m); val ary = FloatArray(m)
      var bx0 = Double.MAX_VALUE; var by0 = Double.MAX_VALUE; var bx1 = -Double.MAX_VALUE; var by1 = -Double.MAX_VALUE
      for (i in 0 until m) {
        val a = max(0, i - 1); val z = min(m - 1, i + 1)
        val l = hypot(cx[z] - cx[a], cy[z] - cy[a]).coerceAtLeast(1e-6)
        val tx = (cx[z] - cx[a]) / l; val ty = (cy[z] - cy[a]) / l
        alx[i] = (cx[i] - ty * hw[i]).toFloat(); aly[i] = (cy[i] + tx * hw[i]).toFloat()
        arx[i] = (cx[i] + ty * hw[i]).toFloat(); ary[i] = (cy[i] - tx * hw[i]).toFloat()
        bx0 = min(bx0, cx[i]); bx1 = max(bx1, cx[i]); by0 = min(by0, cy[i]); by1 = max(by1, cy[i])
      }
      // its kerbs start past our road's edge
      fun onRoad(x: Double, y: Double): Boolean {
        for (k in max(0, at - 30)..min(n - 1, at + 30)) {
          val s = sm[k]
          val f = (x - s.sx) * s.tx + (y - s.sy) * s.ty
          if (abs(f) > STEP) continue
          val dd = (x - s.sx) * s.ty - (y - s.sy) * s.tx
          if (dd > farL(s) - 0.6 && dd < farR(s) + 0.6) return true
        }
        return false
      }
      val alines = ArrayList<LaneMap.MLine>()
      for ((ex, ey) in listOf(alx to aly, arx to ary)) {
        var a = -1
        for (i in 0..m) {
          val ok = i < m && !onRoad(ex[i].toDouble(), ey[i].toDouble())
          if (ok && a < 0) a = i
          if (!ok && a >= 0) { if (i - a >= 2) alines.add(LaneMap.MLine(LaneMap.CURB, ex.copyOfRange(a, i), ey.copyOfRange(a, i))); a = -1 }
        }
      }
      ways.add(LaneMap.BWay(arm.wayId * 4 + arm.d + 2, "", "", true, 0, 0.0, cx.toDoubleArray(), cy.toDoubleArray(), LongArray(m), alines,
        alx, aly, arx, ary, emptyList(), emptyList(), 1, bx0 - 20, by0 - 20, bx1 + 20, by1 + 20))
    }
    ways.add(road)
    val geo = Geo(DoubleArray(n) { sm[it].sx }, DoubleArray(n) { sm[it].sy }, DoubleArray(n) { sm[it].tx }, DoubleArray(n) { sm[it].ty },
      DoubleArray(n) { sm[it].nl }, DoubleArray(n) { sm[it].nr }, DoubleArray(n) { sm[it].no }, BooleanArray(n) { !sm[it].oneway }, van, vanD, laneD)
    return LaneMap.Built(b.lat0, b.lon0, b.kx, b.cx, b.cy, ways, emptyMap(), now) to geo
  }
}
