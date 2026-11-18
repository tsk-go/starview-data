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
 *  - the ribbon: around the thread, the lanes at an even 12 ft, their count easing over 240 ft where it changes; our
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
  private const val KEEP = 320.0         // what is behind us is kept this far: a fork there can still be taken the other way
  private const val SHOULDER = 0.9
  private const val KS = 8               // the thread is smoothed over +-8 steps
  private const val KN = 24              // the lane count eases over 24 steps (240 ft): before it grows, after it shrinks
  private val WT = DoubleArray(KN + 1) { val t = (it + 0.5) / (KN + 1); 6 * t * (1 - t) }
  private const val NONE = Int.MIN_VALUE
  private const val J_TURN = 0; private const val J_SAME = 1; private const val J_LEAVE = 2; private const val J_JOIN = 3
  private val MINOR = setOf("residential", "service", "living_street", "unclassified", "road", "track")

  class Smp(val x: Double, val y: Double, val wayId: Long, val ci: Int, val n: Int, val opp: Int, val oneway: Boolean, val hw: String) {
    var sx = x; var sy = y
    var nl = ci.toDouble(); var nr = (n - 1 - ci).toDouble(); var no = opp.toDouble()
    var done = false; var gap = false
    var openL = false; var openR = false   // a road leaves on that side here: no edge line across its mouth
    var tx = 0.0; var ty = 1.0           // the smoothed thread's direction here
    /** lane [ci] + this = the lane's number counted along the whole thread: the same lane keeps it from piece to piece */
    var gb = 0
    /** a step of the straight run onto this piece (an exit's start, a merge's end): the piece it comes from - on the
     *  map we are still on that one here - and that piece's [gb] */
    var via = Long.MIN_VALUE; var viaGb = 0
    /** the thread's own lane here, by that numbering ([ci] + [gb]; on a straight run that crosses lanes, in between) */
    var g = 0.0
    /** 2.90.2: parking lanes (m wide) on our right and on our left (on a two-way road: across the street, beyond the
     *  other way's lanes), as on the map here, and eased like the lane counts */
    var pr = 0.0; var pl = 0.0
    var spr = 0.0; var spl = 0.0
    fun park(w: LaneMap.BWay, dir: Int): Smp { pr = if (dir > 0) w.parkR else w.parkL; pl = if (dir > 0) w.parkL else w.parkR; spr = pr; spl = pl; return this }
  }

  /** a road meeting ours near step [idx] of the thread: along way [wayId] from its point [v] going [d] */
  internal class Arm(var idx: Long, val wayId: Long, val v: Int, val d: Int)

  /** where the road could go on two ways (step [idx]): what was taken, the others, and the walk as it stood there */
  internal class Fork(var idx: Long, val key: Long, val took: Long, val opts: List<LongArray>, val wayId: Long, val dir: Int,
                      val x: Double, val y: Double, val h: Double, val rem: Double, val prevWay: Long, val prevV: Int, val prevD: Int,
                      val jKind: Int, val jCi: Int, val base: Int) {
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
    // how the thread comes onto the piece it takes up next (see extend): which lane, and the joint it is at
    internal var jKind = J_TURN; internal var jCi = -1; internal var jSkip = 0.0; internal var jX = 0.0; internal var jY = 0.0
    internal var jSide = 0                  // an exit: it leaves on the right (1) / the left (-1)
    // lane numbers along the thread (Smp.gb): the next piece's, when the joint says how its lanes carry on ([NONE]: by
    // the thread's own lane, which was lane [pCi] with [pBase] on the piece before)
    internal var jBase = 0; internal var pCi = -1; internal var pBase = 0
    // the lane offset (m right of the middle line) the thread takes on the piece it's on, that piece's lane count, our
    // lane on it, and whether it's two-way (see extend: a left-turn bay)
    internal var offNow = Double.NaN; internal var offN = 0; internal var offCi = 0; internal var offTwo = false
    // the next steps are where our road narrows away from the lanes it left: no edge line on that side (1 left, 2 right)
    internal var jOpen = 0; internal var jOpenN = 0
    /** tests: what the thread did at each joint, when set */
    var trace: ArrayList<String>? = null
    internal var strayMs = 0L               // since when LaneAnchor has had us on a road that isn't the thread's
    internal var laneD = Double.NaN         // our lane's middle, m right of the thread (at the last step)
    internal var dWas = Double.NaN; internal var dRes = 0.0; internal var dMs = 0L   // a lane's jump being slid over
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
            val van: Double, val d: Double, val laneD: Double,
            private val pl: DoubleArray = DoubleArray(sx.size), private val pr: DoubleArray = DoubleArray(sx.size)) {
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
    /** our way's lanes at [ahead]: (left edge, right edge) in m right of the thread, the far edge of the other way's
     *  lanes (= the left edge on a one-way road), and the street's kerbs left and right (past the parking lanes) */
    fun edges(ahead: Double): DoubleArray {
      val i = (van + ahead / STEP).toInt().coerceIn(0, sx.size - 1)
      val l = -(nl[i] + 0.5) * LW
      val far = if (two[i]) l - no[i] * LW else l
      val r = (nr[i] + 0.5) * LW
      return doubleArrayOf(l, r, far, far - max(SHOULDER, pl[i]), r + max(SHOULDER, pr[i]))
    }
    /** the parking lane (m wide) on the left / right at [ahead], 0 = none */
    fun parking(ahead: Double): DoubleArray {
      val i = (van + ahead / STEP).toInt().coerceIn(0, sx.size - 1)
      return doubleArrayOf(pl[i], pr[i])
    }
  }

  private fun wayIndex(b: LaneMap.Built, st: State): HashMap<Long, Int> {
    var m = st.index
    if (m == null || st.indexOf !== b) { m = HashMap(b.ways.size * 2); for ((i, w) in b.ways.withIndex()) m[w.id] = i; st.index = m; st.indexOf = b }
    return m
  }

  private fun wrap(a: Double) = Math.IEEEremainder(a, 2 * Math.PI)
  private fun link(hw: String) = hw.endsWith("_link")

  /**
   * The lanes of way [w] going [dir] along its points, left to right as driven, each seen from ([x], [y]). In the
   * road's own order (each lane's distance from the road's middle line, at the middle of the piece) - not by where
   * their lines are beside us: at the ends of a piece the map bends those lines together, some end before others, and
   * ordered by them the lanes swapped places there (a ramp's left lane became the last lane of the road it joined).
   */
  internal fun lanesOf(w: LaneMap.BWay, dir: Int, x: Double, y: Double): List<Pair<LaneMap.MLane, LaneAnchor.Proj>> {
    val out = ArrayList<Pair<LaneMap.MLane, LaneAnchor.Proj>>()
    val n = w.px.size
    val cx = FloatArray(n) { w.px[if (dir > 0) it else n - 1 - it].toFloat() }
    val cy = FloatArray(n) { w.py[if (dir > 0) it else n - 1 - it].toFloat() }
    val off = HashMap<LaneMap.MLane, Double>()
    for (l in w.lanes) if ((l.dir < 0) == (dir < 0)) { val p = LaneAnchor.project(l.x, l.y, x, y) ?: continue; out.add(l to p); off[l] = laneOff(cx, cy, l) }
    out.sortBy { off[it.first] }
    return out
  }

  /** 2.90.2: how far right of [lane]'s middle ([x], [y]) is, going [dir] on way [w] - measured from the road's middle
   *  line, like the thread (the map's own lane lines bend in and out near the piece's ends; on the Nov 18 recording
   *  one lay 5 ft off where we were parked) */
  internal fun fromLane(w: LaneMap.BWay, dir: Int, lane: LaneMap.MLane, x: Double, y: Double): Double {
    val n = w.px.size
    val cx = FloatArray(n) { w.px[if (dir > 0) it else n - 1 - it].toFloat() }
    val cy = FloatArray(n) { w.py[if (dir > 0) it else n - 1 - it].toFloat() }
    val pr = LaneAnchor.project(cx, cy, x, y) ?: return Double.NaN
    return pr.side - laneOffset(w, cx, cy, lane)
  }

  /** [lane]'s distance right of the road's middle line ([cx], [cy], in its direction), without the painted median the
   *  map puts between the two directions of a stretch between turn bays (on Nov 18 a whole city block got one: 10 ft
   *  of road that isn't there, our lane drawn where the parked cars are) */
  private fun laneOffset(w: LaneMap.BWay, cx: FloatArray, cy: FloatArray, lane: LaneMap.MLane): Double {
    val o = laneOff(cx, cy, lane)
    return if (w.oneway || w.median <= 0 || abs(o) < w.median / 2) o else o - Math.signum(o) * w.median / 2
  }

  /** the offset of [lane] going [dir] on [w] from its middle line (as [laneOffset]) */
  private fun laneOffset(w: LaneMap.BWay, dir: Int, lane: LaneMap.MLane): Double {
    val n = w.px.size
    return laneOffset(w, FloatArray(n) { w.px[if (dir > 0) it else n - 1 - it].toFloat() }, FloatArray(n) { w.py[if (dir > 0) it else n - 1 - it].toFloat() }, lane)
  }

  /** how far right of the road's middle line ([cx], [cy], in our direction) lane [lane] runs, at the middle of the piece */
  private fun laneOff(cx: FloatArray, cy: FloatArray, lane: LaneMap.MLane): Double {
    var len = 0.0
    for (k in 1 until lane.x.size) len += hypot((lane.x[k] - lane.x[k - 1]).toDouble(), (lane.y[k] - lane.y[k - 1]).toDouble())
    var mx = lane.x[0].toDouble(); var my = lane.y[0].toDouble()
    var at = 0.0
    for (k in 1 until lane.x.size) {
      val l = hypot((lane.x[k] - lane.x[k - 1]).toDouble(), (lane.y[k] - lane.y[k - 1]).toDouble())
      if (at + l >= len / 2 && l > 1e-6) { val t = (len / 2 - at) / l; mx = lane.x[k - 1] + (lane.x[k] - lane.x[k - 1]) * t; my = lane.y[k - 1] + (lane.y[k] - lane.y[k - 1]) * t; break }
      at += l
    }
    return LaneAnchor.project(cx, cy, mx, my)?.side ?: 0.0
  }

  /**
   * Our lane on a piece of road as a line of its own: the map's line down the middle of the road, moved over by the
   * lane's distance from it. LaneMap's own lane lines are not followed: near the ends of a piece it bends them to meet
   * the next piece's lanes (its tapers), and followed one after the other those bends were S-bends in the thread
   * (owner, Nov 17: "fix the S-bends"). The distance is taken at the middle of the piece, away from those ends. In
   * the direction we drive.
   */
  private fun laneLine(w: LaneMap.BWay, dir: Int, lane: LaneMap.MLane, offset: Double = Double.NaN): Pair<FloatArray, FloatArray> {
    val n = w.px.size
    val cx = FloatArray(n) { w.px[if (dir > 0) it else n - 1 - it].toFloat() }
    val cy = FloatArray(n) { w.py[if (dir > 0) it else n - 1 - it].toFloat() }
    val off = if (offset.isFinite()) offset else laneOffset(w, cx, cy, lane)
    val ox = FloatArray(n); val oy = FloatArray(n)
    for (i in 0 until n) {
      val a = max(0, i - 1); val z = min(n - 1, i + 1)
      val dx = (cx[z] - cx[a]).toDouble(); val dy = (cy[z] - cy[a]).toDouble()
      val l = hypot(dx, dy).coerceAtLeast(1e-6)
      ox[i] = (cx[i] + dy / l * off).toFloat(); oy[i] = (cy[i] - dx / l * off).toFloat()
    }
    return ox to oy
  }

  /** The thread from scratch at lane [lane] of way [wi], where ([x], [y]) is on it. */
  private fun start(b: LaneMap.Built, st: State, wi: Int, lane: LaneMap.MLane, x: Double, y: Double) {
    st.smp.clear(); st.arms.clear(); st.forks.clear(); st.forced.clear()
    st.first = 0L; st.dead = false; st.rem = 0.0; st.prevWay = Long.MIN_VALUE
    st.jKind = J_TURN; st.jCi = -1; st.jSkip = 0.0; st.jSide = 0; st.jOpenN = 0
    st.jBase = 0; st.pCi = -1; st.pBase = 0
    st.offNow = Double.NaN
    st.strayMs = 0L; st.laneD = Double.NaN
    st.relaid++
    st.trace?.add("laid from scratch")
    val w = b.ways[wi]
    val dir = if (lane.dir < 0) -1 else 1
    val ls = lanesOf(w, dir, x, y)
    val ci = ls.indexOfFirst { it.first === lane }.coerceAtLeast(0)
    var (blx, bly) = laneLine(w, dir, lane)
    val p = LaneAnchor.project(blx, bly, x, y) ?: return
    val opp = w.lanes.size - ls.size
    // behind us: back along this lane, and on back along the roads that lead into it (what we drove before the
    // thread began - the road must not start under the van)
    val back = ArrayList<Smp>()
    var need = STEP; var done = 0.0
    var qx = p.cx; var qy = p.cy
    var bw = w; var bwi = wi; var bdir = dir; var bci = ci; var bn = ls.size; var bopp = opp
    var bgb = 0
    var k = p.k - 1
    var hops = 0
    while (done < BEHIND && hops < 8) {
      var h = Double.NaN
      while (k >= 0 && done < BEHIND) {
        val ax = blx[k].toDouble(); val ay = bly[k].toDouble()
        val l = hypot(qx - ax, qy - ay)
        if (l > 1e-6) h = atan2(qx - ax, qy - ay)
        var at = need
        while (l > 1e-6 && at <= l && done < BEHIND) { back.add(Smp(qx + (ax - qx) * at / l, qy + (ay - qy) * at / l, bw.id, bci, bn, bopp, bw.oneway, bw.hw).also { it.gb = bgb; it.g = (bci + bgb).toDouble(); it.park(bw, bdir) }); at += STEP; done += STEP }
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
      val was = bci
      bci = bls.indices.minByOrNull { abs(bls[it].second.side) } ?: 0
      bgb += was - bci
      bn = bls.size; bopp = bw.lanes.size - bn
      val line = laneLine(bw, bdir, bls[bci].first)
      blx = line.first; bly = line.second
      k = (LaneAnchor.project(blx, bly, qx, qy) ?: break).k - 1
      hops++
    }
    back.reverse()
    st.smp.addAll(back)
    st.first = -back.size.toLong()
    st.endWay = w.id; st.endDir = dir; st.endX = p.cx; st.endY = p.cy; st.endH = p.bearing
    st.van = 0.0
  }

  /**
   * Follows the road on from where the thread ends until it reaches [AHEAD] m past us.
   *
   * How the thread passes from one piece of road to the next ([State.jKind], set when the next piece is chosen). The
   * map draws a road as a line down its middle and its lanes are worked out from that line, which is wrong near every
   * joint in one of three ways (the S-bends of the first version, owner Nov 17: "fix the S-bends"):
   *  - the same road going on with a lane more or fewer ([J_SAME]): the middle line shifts half a lane, the lanes do
   *    not. Our lane stays our lane - counted from the left edge, where lanes rarely change (from the right edge when
   *    the new lane is a left-turn lane) - and the few feet between the two pieces' lines are let out over 800 ft;
   *  - a road leaving ours ([J_LEAVE], an exit): the map starts it at the middle of our road, so its first 130 ft cut
   *    across our lanes. They are left out: the thread runs straight from our lane to where the exit has got clear;
   *  - our road joining another ([J_JOIN], a merge): the map ends it at the middle of that road, so its last 100 ft
   *    cut across that road's lanes. They are left out too: the thread runs straight on to the first lane of that
   *    road on the side we come from.
   * A turn at a crossing ([J_TURN]) eases onto the new road over 150 ft as before.
   */
  private fun extend(b: LaneMap.Built, st: State) {
    val idx = wayIndex(b, st)
    var guard = 0
    while (!st.dead && (st.first + st.smp.size - st.van) * STEP < AHEAD && guard++ < 60) {
      val wi = idx[st.endWay] ?: run { st.dead = true; return }
      val w = b.ways[wi]
      val ls = lanesOf(w, st.endDir, st.endX, st.endY)
      if (ls.isEmpty()) { st.dead = true; return }
      val kind = st.jKind
      val ci = if (st.jCi in ls.indices) st.jCi else ls.indices.minByOrNull { abs(ls[it].second.side) } ?: 0
      st.jKind = J_TURN; st.jCi = -1
      val base = if (st.jBase != NONE) st.jBase else if (st.pCi >= 0) st.pBase + st.pCi - ci else 0
      // 2.90.2: a two-way road going on with a lane more or fewer on our left (a left-turn bay: the map draws that
      // block a lane wider): our lane goes straight on and the new lane opens on its left - the map's middle line
      // stays where it was, and each piece's own lane offset made the road swing a lane's width sideways and back at
      // every bay (the wobble a block ahead on Nov 18)
      val natural = laneOffset(w, st.endDir, ls[ci].first)
      val hold = kind == J_SAME && !w.oneway && st.offTwo && st.offNow.isFinite() && ls.size - ci == st.offN - st.offCi &&
        abs(natural - st.offNow) < 3.5
      val off = if (hold) st.offNow else natural
      st.offNow = off; st.offN = ls.size; st.offCi = ci; st.offTwo = !w.oneway
      val (lx, ly) = laneLine(w, st.endDir, ls[ci].first, off)
      var p = LaneAnchor.project(lx, ly, st.endX, st.endY) ?: run { st.dead = true; return }
      val opp = w.lanes.size - ls.size
      val from = st.first + st.smp.size
      // where on this piece the thread takes it up: beside where it is now - or, at an exit or a merge, past the part
      // the map draws across the other road's lanes, with a straight run up to there
      var bridge = false
      if ((kind == J_LEAVE || kind == J_JOIN) && st.jSkip > 0) {
        val node = LaneAnchor.project(lx, ly, st.jX, st.jY)
        if (node != null) {
          /** the piece's point [dist] m past the joint; null past its end */
          fun past(dist: Double): LaneAnchor.Proj? {
            var left = dist
            var k = node.k; var t = node.t
            var sx = node.cx; var sy = node.cy
            while (k < lx.size && left > 0) {
              val ax = lx[k - 1].toDouble(); val ay = ly[k - 1].toDouble()
              val dx = lx[k] - ax; val dy = ly[k] - ay
              val l = hypot(dx, dy) * (1 - t)
              if (l >= left) { t += left / hypot(dx, dy).coerceAtLeast(1e-6); sx = ax + dx * t; sy = ay + dy * t; left = 0.0; break }
              left -= l; sx = lx[k].toDouble(); sy = ly[k].toDouble(); t = 0.0; k++
            }
            return if (k < lx.size) LaneAnchor.Proj(sx, sy, node.bearing, 0.0, 0.0, 0.0, k, t) else null
          }
          var skip = st.jSkip
          var q = past(skip)
          if (kind == J_LEAVE && st.jSide != 0) {
            // an exit the map still has inside our own lane there (it starts at the middle of the road and can take a
            // long while to get out): on until it is clear of our lane on its own side, so the run never swings the
            // other way first - an S-bend again
            val c = cos(st.endH); val sn = sin(st.endH)
            while (q != null && skip < st.jSkip + 80.0 && ((q.cx - st.endX) * c - (q.cy - st.endY) * sn) * st.jSide < 0.5) {
              val more = past(skip + 5.0) ?: break
              skip += 5.0; q = more
            }
          }
          if (q != null) { p = q; bridge = true }
        }
      }
      var qx = p.cx; var qy = p.cy
      val ease = if (kind == J_SAME) 250.0 else 45.0
      val jx = if (bridge) 0.0 else st.endX - p.cx; val jy = if (bridge) 0.0 else st.endY - p.cy
      val jog = hypot(jx, jy) > 0.3
      if (st.trace != null) st.trace!!.add(String.format(java.util.Locale.US, "step %d: onto %s %s lane %d of %d, %s, %.1f m to the side%s", st.first + st.smp.size, w.hw,
        w.name, ci + 1, ls.size, arrayOf("turn", "same road", "leaving", "joining")[kind], hypot(st.endX - p.cx, st.endY - p.cy), if (bridge) " (straight run)" else ""))
      var run = 0.0
      val nV = w.px.size
      val goingUp = st.endDir > 0
      fun wayV(k: Int) = if (goingUp) k else nV - 1 - k               // the lane line's point k is the road's point ...
      // a point inside this piece where we leave it for another road (openpilot's path heads there, or we took it)
      var stopK = -1
      for (k in max(1, p.k - 1) until lx.size - 1) {
        val f = st.forced[w.keys[wayV(k)]] ?: continue
        if (f != w.id && (k >= p.k || hypot(lx[k] - p.cx, ly[k] - p.cy) < 2.5)) { stopK = k; break }
      }
      fun put(x: Double, y: Double) {
        val q = Smp(x, y, w.id, ci, ls.size, opp, w.oneway, w.hw)
        q.gb = base; q.g = (ci + base).toDouble(); q.park(w, st.endDir)
        if (st.jOpenN > 0) { if (st.jOpen and 1 != 0) q.openL = true; if (st.jOpen and 2 != 0) q.openR = true; st.jOpenN-- }
        st.smp.add(q)
      }
      fun fall(d: Double) = if (jog) (d / ease).coerceIn(0.0, 1.0).let { 1 - it * it * (3 - 2 * it) } else 0.0
      fun to(ax: Double, ay: Double) {
        val l = hypot(ax - qx, ay - qy)
        if (l < 1e-6) return
        var at = st.rem
        while (at <= l) { val k = fall(run + at); put(qx + (ax - qx) * at / l + jx * k, qy + (ay - qy) * at / l + jy * k); at += STEP }
        st.rem = at - l
        run += l
        st.endH = atan2(ax - qx, ay - qy)
        qx = ax; qy = ay
      }
      if (bridge) {
        // the straight run from where the thread is to where it takes the piece up
        val l = hypot(p.cx - st.endX, p.cy - st.endY)
        var at = st.rem
        while (l > 1e-6 && at <= l) {
          put(st.endX + (p.cx - st.endX) * at / l, st.endY + (p.cy - st.endY) * at / l)
          if (st.pCi >= 0) st.smp.last().let { it.via = st.prevWay; it.viaGb = st.pBase; it.g = (st.pCi + st.pBase) + (ci + base - st.pCi - st.pBase) * (at / l) }
          at += STEP
        }
        if (l > 1e-6) st.rem = at - l
      }
      for (k in p.k until lx.size) { if (stopK >= 0 && k > stopK) break; to(lx[k].toDouble(), ly[k].toDouble()) }
      // (a piece shorter than the easing: the next one takes over from where the thread got to)
      val kEnd = fall(run)
      st.endX = qx + jx * kEnd; st.endY = qy + jy * kEnd
      var to = st.first + st.smp.size
      // which road carries ours on
      val endV = if (stopK >= 0) wayV(stopK) else if (goingUp) nV - 1 else 0
      val key = w.keys[endV]
      var bestU = -1; var bestD = 0; var bestV = 0; var bestS = Double.MAX_VALUE; var bestRel = 0.0
      val opts = ArrayList<LongArray>()
      val rels = ArrayList<Double>()
      val must = st.forced[key]
      if (stopK >= 0) { opts.add(longArrayOf(w.id, endV.toLong(), st.endDir.toLong())); rels.add(0.0) }   // staying on it is the other way
      for (o in b.at[key] ?: IntArray(0)) {
        val ow = o ushr 20; val ov = o and 0xfffff
        if (ow == wi) continue
        val u = b.ways[ow]
        for (d in intArrayOf(1, -1)) {
          if (ov + d !in u.px.indices || u.flow == -d) continue
          if (u.lanes.none { (it.dir < 0) == (d < 0) }) continue
          val rel = wrap(atan2(u.px[ov + d] - u.px[ov], u.py[ov + d] - u.py[ov]) - st.endH)
          val turn = abs(rel)
          if (turn > 1.9) continue
          var s = Math.toDegrees(turn) + (if (link(u.hw) && !link(w.hw)) 25.0 else 0.0) - (if (u.name.isNotBlank() && u.name == w.name) 20.0 else 0.0) +
            4 * abs(u.rank - w.rank)
          if (turn < 1.0) { opts.add(longArrayOf(u.id, ov.toLong(), d.toLong())); rels.add(rel) }
          if (must != null && must == u.id) s = -1000.0
          if (s < bestS) { bestS = s; bestU = ow; bestD = d; bestV = ov; bestRel = rel }
        }
      }
      // every other road at the points we passed on this piece: a branch
      for (v in w.px.indices) {
        if (if (goingUp) v > endV else v < endV) continue                // (past where we left this piece)
        val others = b.at[w.keys[v]] ?: continue
        if (others.size < 2) continue
        // our direction at this point of the piece
        val lk = if (goingUp) v else nV - 1 - v
        val hv = if (v == endV) st.endH else atan2((lx[min(lx.size - 1, lk + 1)] - lx[max(0, lk - 1)]).toDouble(), (ly[min(lx.size - 1, lk + 1)] - ly[max(0, lk - 1)]).toDouble())
        val ways = ArrayList<LongArray>()
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
            val rel = wrap(atan2(u.px[ov + d] - u.px[ov], u.py[ov + d] - u.py[ov]) - hv)
            val ang = abs(rel).let { min(it, Math.PI - it) }
            if (v != endV && abs(rel) < 1.0 && u.flow != -d && u.lanes.any { (it.dir < 0) == (d < 0) }) ways.add(longArrayOf(u.id, ov.toLong(), d.toLong()))
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
        // a road we could take from the middle of this piece: a fork like the ones at its end
        if (ways.isNotEmpty() && st.forks.none { it.key == w.keys[v] }) st.at(near)?.let { q ->
          ways.add(0, longArrayOf(w.id, v.toLong(), st.endDir.toLong()))
          st.forks.add(Fork(near, w.keys[v], w.id, ways, w.id, st.endDir, q.x, q.y, hv, STEP, st.prevWay, st.prevV, st.prevD, kind, ci, base))
        }
      }
      if (bestU < 0) { st.dead = true; break }
      // ---------------------------------------------------------------- how we pass onto it
      val u = b.ways[bestU]
      val ul = lanesOf(u, bestD, st.endX, st.endY)
      val n = ls.size; val un = ul.size
      val into = bestV > 0 && bestV < u.px.size - 1                       // it goes on behind the joint too: we join it
      val fork = opts.size >= 2
      // of the roads going on from here, ours is the rightmost (1) / the leftmost (-1)
      val side = if (!fork) 0 else if (rels.all { it <= bestRel + 1e-9 }) 1 else if (rels.all { it >= bestRel - 1e-9 }) -1 else 0
      st.jX = w.px[endV]; st.jY = w.py[endV]; st.jSkip = 0.0; st.jSide = 0
      // how the next piece's lanes carry ours on: lane i here is lane i + sh there (a turn: the thread's own lane)
      var sh = NONE
      if (abs(bestRel) > 0.6 || un == 0) { st.jKind = J_TURN; st.jCi = -1 }
      else if (into || (link(w.hw) && !link(u.hw))) {
        // joining: the first lane of that road on the side we come from (where the thread was 120 ft back)
        st.jKind = J_JOIN
        val back = st.smp.getOrNull(st.smp.size - 1 - 12) ?: st.smp.getOrNull((from - st.first).toInt())
        val near = ul.minByOrNull { abs(it.second.side) }!!.first
        val fromRight = if (back == null) true else (LaneAnchor.project(near.x, near.y, back.x, back.y)?.side ?: 1.0) > 0
        // (a ramp of two lanes brings both: its lanes are the road's first ones on that side, in their order)
        sh = if (un > n && fromRight) un - n else 0
        st.jCi = (ci + sh).coerceIn(0, un - 1)
        // the end of our road runs across that road's lanes on the map (the map brings a ramp in to the middle of the
        // road): taken out again - its last 100 ft, and on back for as long as the thread still lies in the lane we
        // join or beyond it, so the straight run to that lane never swings back first (an S-bend again: the recorded
        // ramp came in over 300 ft, in four pieces). Up to 400 ft, never past a fork or a road leaving, nor to within
        // 120 ft of where we are.
        if (un > n) {
          var mine = 0
          while (mine < 40 && st.smp.getOrNull(st.smp.size - 1 - mine)?.wayId == w.id) mine++
          val least = min(10, mine / 2)
          val (tx, ty) = laneLine(u, bestD, ul[st.jCi].first)
          var floor = max(st.first + 2, st.van.toLong() + 12)
          for (f in st.forks) floor = max(floor, f.idx + 1)
          var cut = 0
          while (cut < 40) {
            val k = st.smp.size - 1 - cut                               // the step that would be the thread's end
            if (k < 2 || st.first + k <= floor) break
            if (cut >= least) {
              val q = st.smp[k]
              val off = LaneAnchor.project(tx, ty, q.x, q.y)?.side ?: break
              if ((if (fromRight) off else -off) >= 0.5) break          // clear of that lane, on the side we come from
              if (st.arms.any { it.idx >= st.first + k }) break
            }
            cut++
          }
          if (cut > 0) {
            st.smp.subList(st.smp.size - cut, st.smp.size).clear()
            val e = st.smp.last()
            st.endX = e.x; st.endY = e.y; st.rem = STEP
            to = st.first + st.smp.size
            for (a in st.arms) if (a.idx >= to) a.idx = to - 1
            st.jSkip = 15.0
          }
        }
      } else if (fork && un < n && side != 0) {
        // leaving: an exit on the right carries on from our road's right lanes, one on the left from its left lanes
        st.jKind = J_LEAVE
        sh = if (side > 0) un - n else 0
        st.jCi = (ci + sh).coerceIn(0, un - 1)
        var len = 0.0
        for (k in 1 until u.px.size) len += hypot(u.px[k] - u.px[k - 1], u.py[k] - u.py[k - 1])
        st.jSkip = min(40.0, len * 0.5); st.jSide = side
      } else {
        // the same road going on: our lane by its number from the left - from the right when what changes is a
        // left-turn lane coming or going
        st.jKind = J_SAME
        val leftTurn = { l: List<Pair<LaneMap.MLane, LaneAnchor.Proj>> -> l.isNotEmpty() && "left" in l[0].first.turn }
        sh = if (un != n && leftTurn(if (un > n) ul else ls) && !leftTurn(if (un > n) ls else ul)) un - n else 0
        st.jCi = (ci + sh).coerceIn(0, un - 1)
      }
      st.jBase = if (sh == NONE) NONE else base - sh
      st.pCi = ci; st.pBase = base
      st.jOpenN = 0
      if (fork && st.jKind != J_TURN && st.jCi >= 0) {
        // where our road narrows away from the lanes it left (they carry on as the branch): no edge line on that side
        // until it has (a line there ran slanting across the road we left)
        st.jOpen = (if (st.jCi < ci) 1 else 0) or (if (un - 1 - st.jCi < n - 1 - ci) 2 else 0)
        if (st.jOpen != 0) st.jOpenN = KN + 2
      }
      if (fork && st.forks.none { it.key == key }) st.forks.add(Fork(to - 1, key, u.id, opts, w.id, st.endDir, st.endX, st.endY, st.endH, st.rem, st.prevWay, st.prevV, st.prevD, kind, ci, base))
      st.prevWay = w.id; st.prevV = endV; st.prevD = -st.endDir
      st.endWay = u.id; st.endDir = bestD
    }
  }

  /** Smooths what can be smoothed for good (a step needs its neighbours on both sides; the last ones wait). */
  private fun settle(st: State) {
    val n = st.smp.size
    for (i in 0 until n) {
      val s = st.smp[i]
      if (s.done) continue
      if (i + KN >= n && !st.dead) break
      var wx = 0.0; var wy = 0.0; var ws = 0.0
      for (j in -KS..KS) { val q = st.smp.getOrNull(i + j) ?: continue; val k = (KS + 1 - abs(j)).toDouble().let { it * it }; wx += q.x * k; wy += q.y * k; ws += k }
      s.sx = wx / ws; s.sy = wy / ws
      // the lane count eases over 240 ft, in an S-shape: before the place where it grows (the lanes of a road we join
      // are beside us before we are on it) and after the place where it shrinks (up to an exit our road keeps its full
      // width; the lanes we leave carry on as the branch from there - narrowing before it left a notch between the two)
      fun ease(of: (Smp) -> Int): Double {
        var a = 0.0; var wa = 0.0; var z = 0.0; var wz = 0.0
        for (m in 0..KN) {
          st.smp.getOrNull(i + m)?.let { a += of(it) * WT[m]; wa += WT[m] }
          st.smp.getOrNull(i - m)?.let { z += of(it) * WT[m]; wz += WT[m] }
        }
        return max(if (wa > 0) a / wa else 0.0, if (wz > 0) z / wz else 0.0)
      }
      s.nl = ease { it.ci }; s.nr = ease { it.n - 1 - it.ci }; s.no = ease { it.opp }
      fun easeD(of: (Smp) -> Double): Double {
        var a = 0.0; var wa = 0.0; var z = 0.0; var wz = 0.0
        for (m in 0..KN) {
          st.smp.getOrNull(i + m)?.let { a += of(it) * WT[m]; wa += WT[m] }
          st.smp.getOrNull(i - m)?.let { z += of(it) * WT[m]; wz += WT[m] }
        }
        return max(if (wa > 0) a / wa else 0.0, if (wz > 0) z / wz else 0.0)
      }
      s.spr = easeD { it.pr }; s.spl = easeD { it.pl }
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

  /** Lays the thread again from fork [fk] along the branch that is way [wayId] (what lies before the fork stays as it
   *  is, steps and dashes with it). */
  private fun relay(st: State, fk: Fork, wayId: Long, why: String) {
    st.forced[fk.key] = wayId
    val keep = (fk.idx - st.first + 1).toInt().coerceIn(0, st.smp.size)
    st.smp.subList(keep, st.smp.size).clear()
    for (i in max(0, keep - KN - 1) until keep) { st.smp[i].done = false; if (i >= keep - 14) { st.smp[i].openL = false; st.smp[i].openR = false } }
    st.arms.removeAll { it.idx > fk.idx - 12 }
    st.jOpenN = 0
    st.endWay = fk.wayId; st.endDir = fk.dir; st.endX = fk.x; st.endY = fk.y; st.endH = fk.h; st.rem = fk.rem
    st.jKind = fk.jKind; st.jCi = fk.jCi; st.jSkip = 0.0; st.jSide = 0; st.jBase = fk.base
    st.offNow = Double.NaN
    st.prevWay = fk.prevWay; st.prevV = fk.prevV; st.prevD = fk.prevD
    st.dead = false
    st.forks.removeAll { it.idx >= fk.idx }
    st.relaid++
    st.trace?.add("laid again from step ${fk.idx}: $why")
  }

  /** does the branch [o] (way, point, direction) lead to way [wayId] within a few pieces, carrying straight on? */
  private fun leads(b: LaneMap.Built, idx: HashMap<Long, Int>, o: LongArray, wayId: Long): Boolean {
    var wi = idx[o[0]] ?: return false
    var d = o[2].toInt()
    for (hop in 0 until 8) {
      val u = b.ways[wi]
      if (u.id == wayId) return true
      val endV = if (d > 0) u.px.size - 1 else 0
      if (endV - d !in u.px.indices) return false
      val h = atan2(u.px[endV] - u.px[endV - d], u.py[endV] - u.py[endV - d])
      var nu = -1; var nd = 0; var bs = 1.0
      for (q in b.at[u.keys[endV]] ?: IntArray(0)) {
        val ow = q ushr 20; val ov = q and 0xfffff
        if (ow == wi) continue
        val z = b.ways[ow]
        for (dd in intArrayOf(1, -1)) {
          if (ov + dd !in z.px.indices || z.flow == -dd) continue
          if (z.id == wayId) return true
          val turn = abs(wrap(atan2(z.px[ov + dd] - z.px[ov], z.py[ov + dd] - z.py[ov]) - h))
          if (turn < bs) { bs = turn; nu = ow; nd = dd }
        }
      }
      if (nu < 0) return false
      wi = nu; d = nd
    }
    return false
  }

  /**
   * A fork up to 400 ft ahead, looked at again with openpilot's own path ([cam]'s, in the car's frame): when the path
   * heads along another branch than the thread took (by 6 ft at 100 and 200 ft past the fork, three looks in a row),
   * the thread is laid again from the fork along that branch - before we get there, so the road under the van never
   * changes.
   */
  private fun review(b: LaneMap.Built, st: State, cam: LaneFit.Cam): Boolean {
    val pose = st.pose ?: return false
    val xs = cam.pathX ?: return false; val ys = cam.pathY ?: return false
    if (xs.size < 3) return false
    val idx = wayIndex(b, st)
    for (fk in st.forks) {
      val ahead = (fk.idx - st.van) * STEP
      if (ahead < 8 || ahead > 120) continue
      var best = -1; var bestE = Double.MAX_VALUE; var tookE = Double.MAX_VALUE
      for ((k, o) in fk.opts.withIndex()) {
        val u = b.ways[idx[o[0]] ?: continue]
        // 100 ft and 200 ft along this branch, against the path at the same distance ahead of us
        var e = 0.0; var ne = 0
        for (want in doubleArrayOf(30.0, 60.0)) {
          var v = o[1].toInt(); val d = o[2].toInt()
          var len = 0.0; var qx = u.px[v]; var qy = u.py[v]
          while (v + d in u.px.indices && len < want) {
            val l = hypot(u.px[v + d] - u.px[v], u.py[v + d] - u.py[v])
            if (len + l >= want) { val t = (want - len) / l; qx = u.px[v] + (u.px[v + d] - u.px[v]) * t; qy = u.py[v] + (u.py[v + d] - u.py[v]) * t; len = want; break }
            len += l; v += d; qx = u.px[v]; qy = u.py[v]
          }
          if (len < want * 0.6) continue
          val f = pose.fwd(qx, qy)
          if (f < 6 || f > xs.last()) continue
          e += abs(pose.right(qx, qy) - LaneFit.interp(xs, ys, f)); ne++
        }
        if (ne == 0) continue
        e /= ne
        if (o[0] == fk.took) tookE = e
        if (e < bestE) { bestE = e; best = k }
      }
      if (best >= 0 && fk.opts[best][0] != fk.took && tookE != Double.MAX_VALUE && tookE - bestE > 1.8) {
        if (fk.want == best) fk.votes++ else { fk.want = best; fk.votes = 1 }
        if (fk.votes >= 3) { relay(st, fk, fk.opts[best][0], "openpilot's path takes the other branch"); return true }
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
    fun mine(at: Double): Smp? {
      if (!at.isFinite()) return null
      val i = (at - st.first).toInt()
      fun on(q: Smp?) = q?.takeIf { it.wayId == wayId || it.via == wayId }
      for (j in 0..10) (on(st.smp.getOrNull(i + j)) ?: on(st.smp.getOrNull(i - j)))?.let { return it }
      // on a long straight run (a ramp's end taken out over 400 ft) every piece it stands for is the road we're on
      if (st.smp.getOrNull(i)?.let { it.via != Long.MIN_VALUE } == true) for (j in 11..50) st.smp.getOrNull(i - j)?.takeIf { it.wayId == wayId }?.let { return it }
      return null
    }
    var same = mine(s)
    if (same == null && st.smp.size >= 2) {
      // we took the other branch of a fork we have just passed (openpilot's path didn't show it in time): the thread is
      // laid again from that fork - what lies before it stays, and so do the steps and the dashes
      val idx = wayIndex(b, st)
      val near = if (s.isFinite()) s else st.van
      val fk = st.forks.lastOrNull { f -> f.idx <= near + 6 && near - f.idx < 105 && f.took != wayId && f.opts.any { it[0] != f.took && leads(b, idx, it, wayId) } }
      if (fk != null) {
        relay(st, fk, fk.opts.first { it[0] != fk.took && leads(b, idx, it, wayId) }[0], "we took the other branch")
        extend(b, st); settle(st)
        s = station(st, an.px, an.py, near, 60)
        same = mine(s)
      }
    }
    // LaneAnchor has us on a road that is neither the thread's nor a branch of it - a ramp running beside ours that
    // only joins it further on, say (the GPS is a lane or two off). We cannot have got there by driving, so for 4 s the
    // thread stays and we stay in our lane on it; only if that goes on is the road laid from scratch.
    // (A road that crosses ours or leaves it right here is another matter: that is a turn we made.)
    var held = false
    val turned = same == null && s.isFinite() && st.arms.any { it.wayId == wayId && abs(it.idx - s) < 25 }
    if (same == null && !turned && s.isFinite() && st.laneD.isFinite()) {
      if (st.strayMs == 0L) st.strayMs = now
      if (now - st.strayMs in 0..4000) { same = st.smp.getOrNull((s - st.first).toInt()); held = same != null }
    }
    if (!held) st.strayMs = 0L
    val fresh = same == null
    if (same == null) {
      if (st.smp.size >= 2) st.trace?.add("off the thread: on ${b.ways[wi].hw} '${b.ways[wi].name}' ${b.ways[wi].id}, " +
        (if (turned) "a road that meets ours here" else if (!s.isFinite()) "nowhere near it" else "for 4 s") +
        "; ${st.forks.size} forks behind and ahead, we're at step ${st.van.toInt()}")
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
    val drop = (((s - st.first) * STEP - KEEP - 4 * STEP) / (4 * STEP)).toInt() * 4
    if (drop > 0) { st.smp.subList(0, drop).clear(); st.first += drop; st.arms.removeAll { it.idx < st.first }; st.forks.removeAll { it.idx < st.first } }
    settle(st)
    // where we are on it: the lane LaneAnchor has us in, counted from the thread's lane
    val i = (s - st.first).toInt().coerceIn(0, st.smp.size - 2)
    val a = st.smp[i]; val z = st.smp[i + 1]
    if (!a.done || !z.done) return null
    val t = s - st.first - i
    // (by the lanes' numbers along the thread: on a straight run onto the next piece the map still has us on the last)
    val laneD = if (held) st.laneD else (an.lane + (if (same.wayId == wayId || same.via != wayId) same.gb else same.viaGb) - (if (a.via != Long.MIN_VALUE) a.g else same.g)) * LW
    st.laneD = laneD
    // (parked at the curb: in the middle of the parking lane beside our lane - a lane here is LW wide, the parking lane
    // as wide as the map has it)
    val parkD = if (an.parkedD == 0.0) 0.0 else Math.signum(an.parkedD) * (LW / 2 + (abs(an.parkedD) - an.wMap / 2))
    var d = laneD + an.off * (LW / an.wMap) + parkD
    // LaneAnchor changing its mind about our lane moves us a whole lane at once (driving across a line does not: there
    // the lane and our place in it change together). That is slid over at 4 ft a second, not jumped.
    val dt = if (st.dMs == 0L) 0.0 else ((now - st.dMs) / 1000.0).coerceIn(0.0, 1.0)
    st.dMs = now
    if (!fresh && st.dWas.isFinite() && abs(d - st.dWas) > 1.2) st.dRes += d - st.dWas
    if (fresh) st.dRes = 0.0
    st.dWas = d
    st.dRes -= Math.signum(st.dRes) * min(abs(st.dRes), 1.2 * dt)
    d -= st.dRes
    // LaneAnchor is told which roads we can be on: the thread's around us and the branches of the forks near us. A
    // road that only runs beside ours is taken by it only when nothing else is left.
    val vi = (s - st.first).toInt()
    val reach = if (a.via != Long.MIN_VALUE) 50 else 12      // (on a straight run: back to the pieces it stands for)
    val can = HashSet<Long>()
    for (k in max(0, vi - reach)..min(st.smp.size - 1, vi + 70)) { can.add(st.smp[k].wayId); if (st.smp[k].via != Long.MIN_VALUE) can.add(st.smp[k].via) }
    for (f in st.forks) if (f.idx > s - 40 && f.idx < s + 70) for (o in f.opts) can.add(o[0])
    for (a in st.arms) if (a.idx > s - 20 && a.idx < s + 40) can.add(a.wayId)
    an.expect = can
    // ... and how the lanes of those roads carry on into one another. It took a lane over from the lanes that were
    // beside it on the map, and the map ends a ramp at the middle of the road it joins: on the recorded drive we came
    // down a ramp in its left lane and were put in the middle lane of the road it joined (and slid over there).
    val nums = HashMap<Long, Int>()
    fun num(q: Smp) { if (q.wayId !in nums) nums[q.wayId] = q.gb; if (q.via != Long.MIN_VALUE && q.via !in nums) nums[q.via] = q.viaGb }
    for (k in vi.coerceIn(0, st.smp.size - 1)..min(st.smp.size - 1, vi + 70)) num(st.smp[k])
    for (k in min(st.smp.size - 1, vi - 1) downTo max(0, vi - reach)) num(st.smp[k])
    an.laneNums = nums
    val (out, geo) = build(b, st, now, i + t, d, laneD) ?: return null
    val cx = a.sx + (z.sx - a.sx) * t; val cy = a.sy + (z.sy - a.sy) * t
    val f = st.smp[min(st.smp.size - 1, i + 2)].let { if (it.done) it else z }
    val pose = LaneFit.Pose(cx + a.ty * d, cy - a.tx * d, atan2(f.tx, f.ty) - an.yaw)
    st.pose = pose
    return LaneFit.Fit(out, pose, odo, now, 0.0, false, 0.0, an.yaw, null, null, 0, 0).also { it.ribbon = geo }
  }

  private fun build(b: LaneMap.Built, st: State, now: Long, vanAt: Double, vanD: Double, laneD: Double): Pair<LaneMap.Built, Geo>? {
    var n = st.smp.indexOfFirst { !it.done }.let { if (it < 0) st.smp.size else it }
    // drawn from 230 ft behind us on (the scene shows less than that). The thread itself reaches 1000 ft back, for the
    // forks there - and on a ramp that comes around, that part lay across the road ahead like a street crossing it.
    val o = max(0, vanAt.toInt() - (BEHIND / STEP).toInt())
    n -= o
    if (n < 4) return null
    val sm = st.smp.subList(o, o + n)
    val van = vanAt - o
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
        if (ok && a < 0) { if (!dash || (st.first + o + i) % 4 == 0L) a = i }
        if (!ok && a >= 0) {
          if (i - a >= 2) { val xs = FloatArray(i - a); val ys = FloatArray(i - a); for (k in a until i) pt(k, d(sm[k]), xs, ys, k - a); lines.add(LaneMap.MLine(style, xs, ys)) }
          a = -1
        }
      }
    }
    fun twoWay(s: Smp) = !s.oneway
    fun plain(s: Smp) = twoWay(s) && s.hw in MINOR && s.n <= 1          // a small street: no line down its middle
    val left = { s: Smp -> -(s.nl + 0.5) * LW }
    // (no edge line beside a parking lane: the street's own paint has none there either)
    line(LaneMap.SOLID_WHITE, false, { !it.openR && it.spr < 1.2 }) { (it.nr + 0.5) * LW }
    line(LaneMap.EDGE_YELLOW, false, { !twoWay(it) && !it.openL && it.spl < 1.2 }, left)
    line(LaneMap.DOUBLE_YELLOW, false, { twoWay(it) && !plain(it) }, left)
    for (j in 1..6) {
      line(LaneMap.DASH_WHITE, true, { it.nr >= j - 0.4 }) { (j - 0.5) * LW }
      line(LaneMap.DASH_WHITE, true, { it.nl >= j - 0.4 }) { -(j - 0.5) * LW }
      line(LaneMap.DASH_DIM, true, { twoWay(it) && it.no >= j + 0.6 }) { left(it) - j * LW }
    }
    line(LaneMap.SOLID_DIM, false, { twoWay(it) && !plain(it) && it.no > 0.4 && it.spl < 1.2 }) { left(it) - it.no * LW }
    // the street's edge: past the lanes, a parking lane where the map has one, else a narrow shoulder
    val farL = { s: Smp -> left(s) - (if (twoWay(s)) s.no * LW else 0.0) - max(SHOULDER, s.spl) }
    val farR = { s: Smp -> (s.nr + 0.5) * LW + max(SHOULDER, s.spr) }
    // (the kerbs come at the end: they bend round into the streets that cross ours)
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
    /** where (x, y) lies against our kerbs: [sample, m along from it, m past the kerb on that side (> 0 outside), side
     *  (1 right, -1 left)]; null when beside none of the samples near [near] */
    fun kerbAt(x: Double, y: Double, near: Int): DoubleArray? {
      var best: DoubleArray? = null
      for (k in max(0, near - 40)..min(n - 1, near + 40)) {
        val s = sm[k]
        val f = (x - s.sx) * s.tx + (y - s.sy) * s.ty
        if (abs(f) > STEP * 0.75) continue
        val dd = (x - s.sx) * s.ty - (y - s.sy) * s.tx
        if (best != null && abs(f) >= abs(best[1])) continue
        best = doubleArrayOf(k.toDouble(), f, if (dd >= 0) dd - farR(s) else farL(s) - dd, if (dd >= 0) 1.0 else -1.0)
      }
      return best
    }
    /** our kerb on [side] at station [sta] (sample number with a fraction) */
    fun kerbPt(sta: Double, side: Int): DoubleArray {
      val i = sta.toInt().coerceIn(0, n - 2); val t = (sta - i).coerceIn(0.0, 1.0)
      fun at(k: Int): DoubleArray { val q = sm[k]; val dd = if (side > 0) farR(q) else farL(q); return doubleArrayOf(q.sx + q.ty * dd, q.sy - q.tx * dd) }
      val a = at(i); val z = at(i + 1)
      return doubleArrayOf(a[0] + (z[0] - a[0]) * t, a[1] + (z[1] - a[1]) * t)
    }
    // 2.90.2 (owner, Nov 18: "the corners are not sharp edges, it's a little bit rounded"): where a street's kerb meets
    // ours the corner is rounded off - 15 ft - and the asphalt fills it. Per side, the stretches of our kerb left out:
    // the street's mouth and the parts that bend round into it [from station, to station]
    val cut = arrayOf(ArrayList<DoubleArray>(), ArrayList<DoubleArray>())        // left, right
    val city = sm[min(van.toInt().coerceAtLeast(0), n - 1)].hw !in LaneMap.FAST
    val vanI = van.toInt()
    for (arm in st.arms) {
      val at = (arm.idx - st.first).toInt() - o
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
      if (cx.size < 3 || len < 12.0) continue
      // it starts up to 40 ft back under our own road, so its square end never shows beside it - but no farther than our
      // far kerb (2.90.2: a street going off on one side only stuck out 20 ft on the other)
      run {
        val l = hypot(cx[1] - cx[0], cy[1] - cy[0]).coerceAtLeast(1e-6)
        val ux = (cx[1] - cx[0]) / l; val uy = (cy[1] - cy[0]) / l
        var back = 12.0
        while (back > 1.0) {
          val bx = cx[0] - ux * back; val by = cy[0] - uy * back
          if (listOf(-1.0, 1.0).all { sg -> val q = kerbAt(bx + uy * hw[0] * sg, by - ux * hw[0] * sg, at); q == null || q[2] < -0.3 }) break
          back -= 0.5
        }
        cx.add(0, cx[0] - ux * back); cy.add(0, cy[0] - uy * back); hw.add(0, hw[0])
      }
      val m = cx.size
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
      // where each of its edges comes out past our kerb (a corner): [x, y, station, side, edge point, part of the way]
      fun corner(ex: FloatArray, ey: FloatArray): DoubleArray? {
        var prev: DoubleArray? = null
        for (i in 0 until m) {
          val q = kerbAt(ex[i].toDouble(), ey[i].toDouble(), at)
          if (q != null && prev != null && prev[2] <= 0 && q[2] > 0 && prev[3] == q[3] && abs(q[0] - prev[0]) <= 2) {
            val t = prev[2] / (prev[2] - q[2])
            val x = ex[i - 1] + (ex[i] - ex[i - 1]) * t; val y = ey[i - 1] + (ey[i] - ey[i - 1]) * t
            // only a street meeting ours at a good angle (not a ramp leaving at a slant)
            val dx = (ex[i] - ex[i - 1]).toDouble(); val dy = (ey[i] - ey[i - 1]).toDouble()
            val s = sm[q[0].toInt()]
            if (abs(dx * s.ty - dy * s.tx) / hypot(dx, dy).coerceAtLeast(1e-6) < 0.5) return null
            return doubleArrayOf(x, y, (prev[0] + prev[1] / STEP) * (1 - t) + (q[0] + q[1] / STEP) * t, q[3], i.toDouble(), t)
          }
          prev = q
        }
        return null
      }
      val edges = listOf(alx to aly, arx to ary)
      val cs = if (city) edges.map { (ex, ey) -> corner(ex, ey) } else listOf(null, null)
      val alines = ArrayList<LaneMap.MLine>()
      for ((e, c) in edges.zip(cs)) {
        val (ex, ey) = e
        if (c == null) {
          var a = -1
          for (i in 0..m) {
            val ok = i < m && !onRoad(ex[i].toDouble(), ey[i].toDouble())
            if (ok && a < 0) a = i
            if (!ok && a >= 0) { if (i - a >= 2) alines.add(LaneMap.MLine(LaneMap.CURB, ex.copyOfRange(a, i), ey.copyOfRange(a, i))); a = -1 }
          }
          continue
        }
        // the rounded corner: from our kerb R before it, round to the street's kerb R past it
        val other = cs.firstOrNull { it != null && it !== c && it[3] == c[3] }
        val away = if (other != null) Math.signum(c[2] - other[2]) else run {
          val mid = kerbAt(cx[min(m - 1, c[4].toInt())], cy[min(m - 1, c[4].toInt())], at)
          if (mid != null) Math.signum(c[2] - (mid[0] + mid[1] / STEP)) else 1.0
        }
        val r = min(4.6, max(0.0, len - 12.0) / 3)
        if (r < 1.5 || away == 0.0) continue
        val s1 = c[2] + away * r / STEP
        if (s1 < 0 || s1 > n - 1) continue
        val p1 = kerbPt(s1, c[3].toInt())
        // the point r m on along the street's edge from the corner, and the edge from there
        var k = c[4].toInt(); var qx = c[0]; var qy = c[1]; var left = r
        while (k < m) {
          val l = hypot(ex[k] - qx, ey[k] - qy)
          if (l >= left) { qx += (ex[k] - qx) * left / l; qy += (ey[k] - qy) * left / l; left = 0.0; break }
          left -= l; qx = ex[k].toDouble(); qy = ey[k].toDouble(); k++
        }
        if (left > 0) continue
        val arc = 10
        val ax = FloatArray(arc + 1); val ay = FloatArray(arc + 1)
        for (j in 0..arc) {
          val t = j.toDouble() / arc; val u = 1 - t
          ax[j] = (u * u * p1[0] + 2 * u * t * c[0] + t * t * qx).toFloat(); ay[j] = (u * u * p1[1] + 2 * u * t * c[1] + t * t * qy).toFloat()
        }
        // the street's own kerb carries on from there
        if (m - k >= 1) {
          val kx = FloatArray(m - k + 1); val ky = FloatArray(m - k + 1)
          kx[0] = qx.toFloat(); ky[0] = qy.toFloat()
          for (i in k until m) { kx[i - k + 1] = ex[i]; ky[i - k + 1] = ey[i] }
          alines.add(LaneMap.MLine(LaneMap.CURB, kx, ky))
        }
        // the asphalt inside the bend, and the kerb along it. Its outline goes round the same way as the roads' (the
        // corner on the right of the bend): all the asphalt is one shape, and a piece going round the other way cancels
        // where it overlaps another - a light seam along the street's edge
        val cxF = FloatArray(arc + 1) { c[0].toFloat() }; val cyF = FloatArray(arc + 1) { c[1].toFloat() }
        val onRight = (c[0] - p1[0]) * (qy - p1[1]) - (c[1] - p1[1]) * (qx - p1[0]) > 0
        // the kerb along the bend starts 4 in inside the asphalt like ours does and ends on the street's own kerb
        val mx = c[0] - (p1[0] + qx) / 2; val my = c[1] - (p1[1] + qy) / 2
        val kxs = FloatArray(arc + 1); val kys = FloatArray(arc + 1)
        for (j in 0..arc) {
          val a0 = max(0, j - 1); val a1 = min(arc, j + 1)
          var nx = -(ay[a1] - ay[a0]).toDouble(); var ny = (ax[a1] - ax[a0]).toDouble()
          val nl = hypot(nx, ny).coerceAtLeast(1e-6); nx /= nl; ny /= nl
          if (nx * mx + ny * my < 0) { nx = -nx; ny = -ny }
          val inset = 0.1 * (1 - j.toDouble() / arc)
          kxs[j] = (ax[j] + nx * inset).toFloat(); kys[j] = (ay[j] + ny * inset).toFloat()
        }
        ways.add(LaneMap.BWay(arm.wayId * 4 + arm.d + 2 + (if (e.first === alx) 1_000_000_000_000L else 2_000_000_000_000L), "", "", true, 0, 0.0,
          DoubleArray(arc + 1) { ax[it].toDouble() }, DoubleArray(arc + 1) { ay[it].toDouble() }, LongArray(arc + 1),
          listOf(LaneMap.MLine(LaneMap.CURB, kxs, kys)), if (onRight) ax else cxF, if (onRight) ay else cyF, if (onRight) cxF else ax,
          if (onRight) cyF else ay, emptyList(), emptyList(), 1,
          min(p1[0], qx) - 20, min(p1[1], qy) - 20, max(p1[0], qx) + 20, max(p1[1], qy) + 20))
        cut[if (c[3] > 0) 1 else 0].add(doubleArrayOf(min(s1, c[2]), max(s1, c[2])))
      }
      // our kerb also stops across the street's mouth, between its two corners
      val c0 = cs[0]; val c1 = cs[1]
      if (c0 != null && c1 != null && c0[3] == c1[3]) cut[if (c0[3] > 0) 1 else 0].add(doubleArrayOf(min(c0[2], c1[2]), max(c0[2], c1[2])))
      ways.add(LaneMap.BWay(arm.wayId * 4 + arm.d + 2, "", "", true, 0, 0.0, cx.toDoubleArray(), cy.toDoubleArray(), LongArray(m), alines,
        alx, aly, arx, ary, emptyList(), emptyList(), 1, bx0 - 20, by0 - 20, bx1 + 20, by1 + 20))
    }
    // ---------------------------------------------------------------- our kerbs
    for ((si, side) in listOf(0 to -1, 1 to 1)) {
      val cuts = cut[si]
      fun inCut(sta: Double) = cuts.firstOrNull { sta >= it[0] - 1e-6 && sta <= it[1] + 1e-6 }
      // where a street crosses with no rounded corner found: left out as before (the crossing's samples)
      fun gone(i: Int): Boolean {
        val s = sm[i]
        if (if (side > 0) s.openR else s.openL) return true
        if (inCut(i.toDouble()) != null) return true
        // (a street crossing on one side only, a T: the kerb across from it stays)
        return s.gap && cut.all { c -> c.none { i >= it[0] - 6 && i <= it[1] + 6 } }
      }
      val xs = ArrayList<Float>(); val ys = ArrayList<Float>()
      fun flush() { if (xs.size >= 2) lines.add(LaneMap.MLine(LaneMap.CURB, xs.toFloatArray(), ys.toFloatArray())); xs.clear(); ys.clear() }
      fun put(sta: Double) { val p = kerbPt(sta, side); val inset = if (side > 0) -0.1 else 0.1
        val i = sta.toInt().coerceIn(0, n - 1); xs.add((p[0] + sm[i].ty * inset).toFloat()); ys.add((p[1] - sm[i].tx * inset).toFloat()) }
      for (i in 0 until n) {
        if (gone(i)) {
          // ends exactly where a cut begins
          if (xs.isNotEmpty()) { cuts.filter { it[0] > i - 1 && it[0] <= i }.minByOrNull { it[0] }?.let { put(it[0]) }; flush() }
          continue
        }
        // starts exactly where a cut ends
        if (xs.isEmpty() && i > 0) cuts.filter { it[1] >= i - 1 && it[1] < i }.maxByOrNull { it[1] }?.let { put(it[1]) }
        put(i.toDouble())
      }
      flush()
    }
    ways.add(road)
    val geo = Geo(DoubleArray(n) { sm[it].sx }, DoubleArray(n) { sm[it].sy }, DoubleArray(n) { sm[it].tx }, DoubleArray(n) { sm[it].ty },
      DoubleArray(n) { sm[it].nl }, DoubleArray(n) { sm[it].nr }, DoubleArray(n) { sm[it].no }, BooleanArray(n) { !sm[it].oneway }, van, vanD, laneD,
      DoubleArray(n) { sm[it].spl }, DoubleArray(n) { sm[it].spr })
    return LaneMap.Built(b.lat0, b.lon0, b.kx, b.cx, b.cy, ways, emptyMap(), now) to geo
  }
}
