package com.starpilot.starview

import kotlin.math.abs
import kotlin.math.exp
import kotlin.math.hypot
import kotlin.math.max
import kotlin.math.min

/**
 * 2.90.0 (owner, Nov 17, a video of 41 s on a boulevard: "car shifting the roads jumping ... nice and smooth like
 * Tesla"): what keeps the Scene view still. Until now every frame was drawn from that instant's best guess, so every
 * change of mind snapped onto the screen: the lane card went 1 of 3, 2 of 3, 1 of 3, 1 of 2, 2 of 3, 1 of 2 in those
 * 41 s and each time the lines changed kind or vanished, the map came and went, and the road moved over by a lane.
 *
 * The rules here, each a small piece with no Android in it (tested on its own):
 *  - [Held]: a reading is taken up only after it held steady, and not dropped the moment it is unsure;
 *  - [Lanes]: the lane card's count for the drawing - 3 s to change, no flipping back for 10 s, kept for 8 s while the
 *    card is unsure; a lane change we make ourselves moves the count at once ([Lines.shift]);
 *  - [Lines]: the camera's four lines followed by where they are on the road, not by their number: each fades in and
 *    out, moves smoothly, and changes its paint (dashed / solid / yellow) by cross-fading once the new paint held;
 *  - [Ease]: a number that moves toward its target smoothly (the map coming and going, the road's sides);
 *  - [Pose]: a correction of where we are on the map slides in at walking pace instead of jumping.
 * Nothing here decides what is true - it only decides how fast the picture may follow.
 */
object SceneSteady {
  private const val NONE = Int.MIN_VALUE

  /** A whole number that changes only after its replacement held steady: [firstMs] when nothing is known yet,
   *  [takeMs] otherwise, and not back to the value it just left within [backMs]. A reading below 0 means "not sure":
   *  the last value stays for [keepMs]. */
  class Held(private val firstMs: Long, private val takeMs: Long, private val backMs: Long, private val keepMs: Long) {
    var value = -1; private set
    /** when [value] last changed (the caller's clock, ms) */
    var changedAt = Long.MIN_VALUE / 4; private set
    /** the value before the last change */
    var before = -1; private set
    private var cand = NONE; private var candAt = 0L
    private var unsure = false; private var unsureAt = 0L

    fun put(v: Int, now: Long, take: Long = takeMs): Int {
      if (v == value) { cand = NONE; unsure = false; return value }
      if (v < 0) {
        cand = NONE
        if (!unsure) { unsure = true; unsureAt = now }
        if (now - unsureAt >= keepMs) set(-1, now)
        return value
      }
      unsure = false
      if (v != cand) { cand = v; candAt = now }
      val need = if (value < 0) firstMs else take
      if (now - candAt >= need && !(value >= 0 && v == before && now - changedAt < backMs)) set(v, now)
      return value
    }

    /** known for sure (our own lane change): at once, and not counted as a flip */
    fun force(v: Int, now: Long) { if (v != value) { before = -1; value = v; changedAt = now }; cand = NONE; unsure = false }

    private fun set(v: Int, now: Long) { before = value; value = v; changedAt = now; cand = NONE; unsure = false }
  }

  /** The lane card's count as the Scene draws it. Both sides change together (one change of the picture, not two half
   *  a second apart); a side the card is unsure of keeps its last count for 8 s. */
  class Lanes {
    private val both = Held(600, 3000, 10_000, 0)     // (left + 1) * 32 + (right + 1)
    private val edge = Held(0, 3000, 10_000, 0)       // the left edge's paint, + 1 (0 = only what the camera reads)
    private val unsureAt = longArrayOf(-1L, -1L)
    private var known = false

    private fun side(i: Int) = if (!known) -1 else (if (i == 0) both.value / 32 else both.value % 32) - 1

    /** [raw]: LaneSceneView.Lanes.live(); [shift]: we crossed a line to the right (1) / left (-1) since the last call */
    fun update(raw: LaneSceneView.Lanes?, shift: Int, now: Long): LaneSceneView.Lanes? {
      if (shift != 0 && known) {
        val l = side(0); val r = side(1)
        // one lane over: one more on the side we came from, one fewer on the side we went to (when there was one)
        val l2 = if (l >= 0 && l + shift >= 0) l + shift else l
        val r2 = if (r >= 0 && r - shift >= 0) r - shift else r
        both.force((l2 + 1) * 32 + (r2 + 1), now)
      }
      val want = IntArray(2)
      for (i in 0..1) {
        val v = (if (raw == null) -1 else if (i == 0) raw.left else raw.right).coerceIn(-1, 30)
        if (v >= 0) { unsureAt[i] = -1L; want[i] = v }
        else {
          if (unsureAt[i] < 0) unsureAt[i] = now
          want[i] = if (now - unsureAt[i] >= 8000) -1 else side(i)
        }
      }
      if (!known && want[0] < 0 && want[1] < 0) return if (raw == null) null else LaneSceneView.Lanes(-1, -1, edge.put(raw.leftEdge + 1, now) - 1)
      val code = (want[0] + 1) * 32 + (want[1] + 1)
      // only forgetting (a side unsure for 8 s): no further wait
      if (known && (0..1).all { want[it] == side(it) || want[it] < 0 }) both.force(code, now) else both.put(code, now)
      known = both.value >= 0
      if (raw != null) edge.put(raw.leftEdge + 1, now)
      if (side(0) < 0 && side(1) < 0 && raw == null) { known = false; return null }
      return LaneSceneView.Lanes(side(0), side(1), edge.value - 1)
    }
  }

  /** The speed limit sign: another limit than the one shown replaces it only after it was read for [holdNs] without
   *  the shown one coming back; the first one shows at once. Speeds in m/s. */
  class SpeedHold(private val holdNs: Long = 2_500_000_000L) {
    var shown = Float.NaN; private set
    private var cand = Float.NaN; private var candNs = 0L
    private fun ok(v: Float) = v.isFinite() && v > 0f
    private fun same(a: Float, b: Float) = abs(a - b) < 0.3f
    fun clear() { shown = Float.NaN; cand = Float.NaN }
    fun put(fresh: Float, nowNs: Long): Float {
      if (!ok(shown) || same(fresh, shown)) { shown = fresh; cand = Float.NaN; return fresh }
      if (!ok(cand) || !same(fresh, cand)) { cand = fresh; candNs = nowNs }
      if (nowNs - candNs >= holdNs) { shown = fresh; cand = Float.NaN }
      return shown
    }
  }

  /** A number easing toward its target: [tau] seconds to cover 63 % of the way. */
  class Ease(private val tau: Double, var v: Double = Double.NaN) {
    fun to(target: Double, dt: Double): Double {
      v = if (!v.isFinite() || !target.isFinite()) target else v + (target - v) * (1 - exp(-max(0.0, dt) / tau))
      return v
    }
    fun set(x: Double) { v = x }
  }

  /** One of the camera's lines, followed over time. */
  class Line(n: Int) {
    val y = FloatArray(n)              // metres right of us at the model's distances (smoothed)
    var x: FloatArray = y              // the model's distances ahead
    var r = 0.0                        // metres right of us at [AT] m
    var alpha = 0f                     // 0..1, how strongly it's drawn
    var slot = -1                      // the camera's number for it now (-1 = lost, fading out)
    var prob = 0f
    var reach = 0.0
    var on = false
    /** the paint drawn (LaneMap style, -1 = none), the one it's fading from, and how far the fade is (0..1) */
    var style = NONE; var from = NONE; var mix = 1f
    internal val held = Held(0, 900, 4000, 0)
    internal var mixAt = 0L
  }

  /**
   * The camera's four lane lines as lines on the road. openpilot numbers them by where they are around the car (1 and
   * 2 are our lane's), so at a lane change the same paint gets another number, and a line the model drops for a moment
   * was gone from one frame to the next. Here a line is the same line while it stays within [MATCH] m of where it was.
   */
  class Lines {
    val tracks = ArrayList<Line>()
    /** set by [update] when we crossed one of our lane's lines: 1 = to the right, -1 = to the left */
    var shift = 0; private set
    private var lastNs = 0L

    companion object {
      const val AT = 10.0
      private const val MATCH = 1.3
      private const val TAU_Y = 0.10
      private const val FADE_IN = 0.30
      private const val FADE_OUT = 0.45
      private const val MIX_MS = 400L
      private const val YELLOW_MS = 2500L
    }

    /**
     * [xs] / [ys] / [prob]: the model's lines 0..3; [want]: false = the lane card says it's past the road's edge (not
     * drawn); [style]: the paint each would get now (LaneMap style, -1 = none); [reach]: how far each is drawn.
     */
    fun update(xs: Array<FloatArray>, ys: Array<FloatArray>, prob: FloatArray, want: BooleanArray, style: IntArray, reach: DoubleArray, nowNs: Long) {
      val dt = if (lastNs == 0L) 0.0 else ((nowNs - lastNs) / 1e9).coerceIn(0.0, 0.5)
      val fresh = lastNs == 0L || nowNs - lastNs > 1_500_000_000L
      lastNs = nowNs
      if (fresh) tracks.clear()
      val nowMs = nowNs / 1_000_000
      shift = 0
      val taken = BooleanArray(tracks.size)
      var cross = 0
      for (i in 0 until min(4, xs.size)) {
        val n = min(xs[i].size, ys[i].size)
        if (n < 2) continue
        val r = LaneFit.interp(xs[i], ys[i], AT)
        // appears at 40 %, goes at 25 % (it flickered around one threshold)
        var best = -1; var bd = MATCH
        for (k in taken.indices) if (!taken[k] && tracks[k].y.size == n) { val t = tracks[k]; val d = abs(t.r - r); if (d < bd) { bd = d; best = k } }
        val was = if (best >= 0) tracks[best] else null
        val seen = prob[i] >= (if (was != null && was.on) 0.25f else 0.40f)
        if (was == null && !seen) continue
        val t = was ?: Line(n).also { it.r = r; System.arraycopy(ys[i], 0, it.y, 0, n); it.held.put(style[i] + 1, nowMs); it.style = style[i]; tracks.add(it) }
        if (was != null) {
          taken[best] = true
          // our own lane's line with another number, on the other side of us: we drove over it
          if (t.slot in 1..2 && i in 1..2 && t.slot != i && t.alpha > 0.5f && seen) cross += if (i < t.slot) 1 else -1
          val k = (1 - exp(-dt / TAU_Y)).toFloat()
          for (j in 0 until n) t.y[j] += (ys[i][j] - t.y[j]) * k
          t.r += (r - t.r) * k
        }
        t.x = xs[i]; t.slot = i; t.prob = prob[i]; t.reach = reach[i]; t.on = seen
        // paint: the new kind has to hold (longer to lose yellow: the line reader loses the colour in shade, at night)
        val cur = t.style
        val yellowNow = cur == LaneMap.EDGE_YELLOW || cur == LaneMap.DOUBLE_YELLOW
        val yellowNew = style[i] == LaneMap.EDGE_YELLOW || style[i] == LaneMap.DOUBLE_YELLOW
        // a paint once known stays through 4 s of "not known" (grey): the reader loses a line behind a car, in a gap
        val unknownNew = style[i] == LaneMap.CURB && cur != LaneMap.CURB && cur >= 0
        val got = t.held.put(style[i] + 1, nowMs, if (unknownNew) 4000L else if (yellowNow && !yellowNew) YELLOW_MS else 1500L) - 1
        if (got != cur) { t.from = cur; t.style = got; t.mixAt = nowMs; t.mix = 0f }
        if (t.mix < 1f) t.mix = ((nowMs - t.mixAt).toFloat() / MIX_MS).coerceIn(0f, 1f)
        val target = if (seen && want[i]) ((prob[i] - 0.3f) / 0.4f).coerceIn(0.3f, 1f) else 0f
        t.alpha = step(t.alpha, target, dt, fresh)
      }
      // lines the camera lost: they fade where they were
      for (k in taken.indices) if (!taken[k]) { val t = tracks[k]; t.slot = -1; t.on = false; t.alpha = step(t.alpha, 0f, dt, false) }
      tracks.removeAll { it.slot < 0 && it.alpha < 0.02f }
      if (cross != 0) shift = if (cross > 0) 1 else -1
    }

    private fun step(a: Float, target: Float, dt: Double, fresh: Boolean): Float {
      if (fresh) return target                    // the first picture shows what's there at once: nothing to fade from
      if (dt <= 0.0) return a
      val tau = if (target > a) FADE_IN else FADE_OUT
      return (a + (target - a) * (1 - exp(-dt / tau))).toFloat()
    }
  }

  /**
   * Which way a car faces. The camera's detector guesses a heading for every car in every frame, and the guess flickers:
   * drawn as it came, cars spun around in the middle of the road (owner, Nov 17: "have you ever seen a car flipping
   * around"). A car on the road faces along it - our way, or toward us - and which of the two changes only on evidence
   * that lasts: how it moves over the ground (its place from frame to frame plus our own speed), and, standing still,
   * what the detector says most of the time. It is drawn across the road only while it is seen moving across.
   */
  class Facing {
    private var pr = Double.NaN; private var pf = Double.NaN
    private var vr = 0.0; private var vf = 0.0; private var have = false
    private var score = 0.0                 // > 0: toward us
    private var opposite = false
    private var crossS = 0.0
    private var cross = false
    private var crossPsi = 0.0

    /** [r] / [f]: where it is now (m right / ahead, as reported); [vEgo]: our speed; [road]: the road's direction there;
     *  [hint]: the detector's heading (NaN = none); [farSide]: it is past the yellow line on our left - the other
     *  direction's side, so it faces us unless it is seen driving our way (owner, Nov 17: "a car going the wrong
     *  direction, it's after the yellow line"). Returns the direction to draw (radians, right = positive). */
    fun update(r: Double, f: Double, vEgo: Double, road: Double, hint: Double, dt: Double, farSide: Boolean = false): Double {
      if (pr.isFinite() && dt > 1e-3 && abs(r - pr) < 6 && abs(f - pf) < 12) {
        val k = 1 - exp(-dt / 0.8)
        val ir = (r - pr) / dt; val jf = (f - pf) / dt + vEgo
        if (!have) { vr = ir * 0.3; vf = jf * 0.3; have = true } else { vr += (ir - vr) * k; vf += (jf - vf) * k }
      } else if (!pr.isFinite()) {
        // first sight: the detector's word, until the motion says otherwise
        if (farSide) { opposite = true; score = 1.0 }
        else if (hint.isFinite()) { opposite = kotlin.math.cos(hint - road) < 0; score = if (opposite) 0.6 else -0.6 }
      }
      pr = r; pf = f
      // along the road: our way or toward us
      val along = vf * kotlin.math.cos(road) + vr * kotlin.math.sin(road)
      if (have && along < -3.0) score += 3 * dt else if (have && along > 2.0) score -= 3 * dt
      else if (farSide) score += dt
      else if (hint.isFinite()) score += (if (kotlin.math.cos(hint - road) < 0) 1 else -1) * dt
      score = score.coerceIn(-1.5, 1.5)
      if (opposite && score < -0.75) opposite = false else if (!opposite && score > 0.75) opposite = true
      // across the road: only while it really moves sideways
      val across = -vf * kotlin.math.sin(road) + vr * kotlin.math.cos(road)
      val moving = have && abs(across) > 2.5 && abs(across) > abs(along)
      crossS = (crossS + (if (moving) dt else -dt)).coerceIn(0.0, 1.2)
      if (!cross && crossS >= 0.6) cross = true else if (cross && crossS <= 0.0) cross = false
      if (cross && moving) crossPsi = kotlin.math.atan2(vr, vf)
      return if (cross) crossPsi else road + (if (opposite) Math.PI else 0.0)
    }
  }

  /**
   * Where we are on the map, for the drawing: the last fit carried on by the car's own motion, and when a new fit
   * disagrees with where the last one had got to, the difference slides out: 63 % in [TAU] s and never faster sideways
   * than [RATE] m/s - about a lane change's pace, so the road never jumps under the van. A difference too big to be
   * the same place (another road, a rebuilt map) is taken at once.
   */
  class Pose {
    private var last: LaneFit.Fit? = null
    private var ox = 0.0; private var oy = 0.0; private var oh = 0.0
    private var lastNs = 0L

    companion object { const val TAU = 0.45; const val RATE = 1.3 }

    /** a fit's place now. On LaneRibbon's road: as far further along that road as the car has driven since, in the
     *  same place across it - the van stays in its lane round a bend between two fits. Else by the car's own motion. */
    private fun carried(f: LaneFit.Fit, odoNow: DoubleArray): LaneFit.Pose {
      val g = f.ribbon
      if (g != null && odoNow.size > 3 && f.odo.size > 3) g.poseAt(odoNow[3] - f.odo[3], f.dh)?.let { return it }
      return LaneFit.carry(f.pose, f.odo, odoNow)
    }

    fun pose(f: LaneFit.Fit, odoNow: DoubleArray, nowNs: Long): LaneFit.Pose {
      val p = carried(f, odoNow)
      val dt = if (lastNs == 0L) 0.0 else ((nowNs - lastNs) / 1e9).coerceIn(0.0, 0.5)
      lastNs = nowNs
      // let the remaining difference out
      val d = hypot(ox, oy)
      if (d > 1e-9 || oh != 0.0) {
        // on LaneRibbon's road our place is steady by the way it is worked out (a lane, and the camera's place in it):
        // it is followed closely - sliding it in slowly left the van trailing beside its lane round every bend
        val tight = f.ribbon != null
        val k = exp(-dt / (if (tight) 0.12 else TAU))
        val left = if (tight) d * k else max(d * k, d - RATE * dt)        // whichever is slower
        val s = if (d > 1e-9) left / d else 0.0
        ox *= s; oy *= s; oh *= k
      }
      val l = last
      if (l !== f) {
        if (l != null && !l.hidden && LaneFit.sameFrame(l.built, f.built)) {
          val q = carried(l, odoNow)
          val dx = q.x - p.x + ox; val dy = q.y - p.y + oy
          val dh = Math.IEEEremainder(q.h - p.h, 2 * Math.PI) + oh
          if (hypot(dx, dy) < 6.0 && abs(dh) < 0.2) { ox = dx; oy = dy; oh = dh } else { ox = 0.0; oy = 0.0; oh = 0.0 }
        } else { ox = 0.0; oy = 0.0; oh = 0.0 }
        last = f
      }
      return LaneFit.Pose(p.x + ox, p.y + oy, p.h + oh)
    }
  }
}
