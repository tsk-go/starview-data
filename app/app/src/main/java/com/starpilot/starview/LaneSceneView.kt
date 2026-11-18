package com.starpilot.starview

import android.content.Context
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Shader
import android.view.Choreographer
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import kotlin.math.abs
import kotlin.math.atan
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.exp
import kotlin.math.floor
import kotlin.math.hypot
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.tan

/**
 * 2.87.0: the Scene view, drawn like a Tesla's: the van from behind and above, the road lane by lane, the cars around.
 *
 * What is drawn, near to far:
 *  - openpilot's own lane lines and road edges (what the camera sees) up to about 130 ft, dashed / solid / yellow as
 *    the camera's line reader (LaneMarks) or the map says;
 *  - beyond that, the road from LaneMap (OpenStreetMap's lane tags), lined up with the camera's lines by LaneFit:
 *    every lane, its lines, the turn arrows of turn lanes, the ramps and roads reachable from ours. When the map
 *    doesn't line up with what the camera sees, it is left out and only the camera's lines are drawn;
 *  - the path openpilot drives (blue while it steers), the lane a lane change goes into (blue), a lane with a car in
 *    the blind spot (red);
 *  - the cars (radar and the camera's object detection), the one openpilot follows in blue, remembered cars pale.
 * Plain Canvas drawing at up to 30 frames a second, no 3D engine: the road users are pictures (SceneSprites).
 * Pinch: zoom, two fingers up / down: tilt, double tap: back to the start, hold: screen editor (as before).
 * The old 3D Scene stays available (Settings: "Scene: new style" off).
 */
class LaneSceneView(ctx: Context) : View(ctx) {
  var t: Telemetry? = null
  var night = true
  var showTraffic = false
  var detector: Detector? = null
  var carMemory: CarMemory? = null
  /** the screen it's on: a hold opens the screen editor, a zoom / tilt is kept (null in tests) */
  var host: MainActivity? = null
  var dist = DEF_DIST
    set(v) { field = v.coerceIn(10f, 45f) }
  var elev = DEF_ELEV
    set(v) { field = v.coerceIn(12f, 48f) }
  /** tests: draw this instead of the live data */
  var frame: Frame? = null
  /** 2.90.0: the picture holds still (SceneSteady). false = every frame from that instant's guess, as before (kept to
   *  compare the two on a recorded drive) */
  var steady = true
  /** 2.90.0: the map is the road and the only lines drawn; the camera only places us on it (LaneAnchor). false = the
   *  camera's lines near the van joined to the map's beyond (LaneFit), as before. Needs [steady]. */
  var anchor = true

  companion object {
    const val DEF_DIST = 17.24f
    const val DEF_ELEV = 30f
    private const val FOV_HALF = 21.0          // vertical, degrees
    private const val FMIN = -6.0
    private const val FMAX = 300.0
    private const val DASH = 3.05
    private const val PERIOD = 12.2
    private const val EGO_BACK = 0.29         // the van's middle, behind the comma camera (m)
    private val GENERIC = arrayOf("car_sedan", "car_suv", "car_hatch", "car_coupe")
    /** Traffic's id for radarState's leadOne, the car openpilot follows (its LEAD_ID_BASE) */
    private const val FOLLOWED_ID = -100L
    /** 2.89.2: a traffic light hangs this high over the road (the bottom of its head, m), from a mast arm */
    private const val HANG = 4.6
    /** a signal head's height (m) */
    private const val LIGHT_H = 1.1
  }

  /** [pole]: 2.89.2, a traffic light's mast pole sideways (m right of us; NaN = 4 m right of the light) */
  class Thing(val id: Long, val model: String, var r: Double, var f: Double, var psi: Double, val lead: Boolean, val remembered: Boolean,
              val pole: Double = Double.NaN) {
    /** 2.90.0: how strongly it's drawn (a road user fades in when first seen and out when lost) */
    var alpha = 1.0
    /** 2.90.0: a vehicle from the camera's detector: its heading as the detector guessed it (NaN = no guess) and the
     *  road's direction where it is. Set = [psi] is worked out from how it moves (SceneSteady.Facing). */
    var hint = Double.NaN
    var road = Double.NaN
    /** a place and a direction that can be drawn (no NaN, no infinity) */
    fun ok() = r.isFinite() && f.isFinite() && psi.isFinite()
  }

  /** Everything one drawing needs (the live one is made from the telemetry; tests make their own). */
  class Frame(val model: ModelSnapshot?, val fit: LaneFit.Fit?, val pose: LaneFit.Pose?, val travelled: Double,
              val things: List<Thing>, val latActive: Boolean, val blindL: Boolean, val blindR: Boolean,
              val marks: Array<LaneMarks.Mark>?, val lanes: Lanes? = null,
              /** 2.90.0: how much of the map is drawn (0 = the camera's road only .. 1), and the moment drawn */
              val mapAlpha: Float = if (pose != null && fit != null && !fit.hidden) 1f else 0f, val atNs: Long = System.nanoTime())

  /**
   * 2.87.1: the lane card's count (LaneBrain), when it's sure (70 %, each side on its own): lanes our way left / right
   * of us (-1 = not sure) and how the left edge of our side is painted (LaneMap.EDGE_YELLOW on a one-way or divided road,
   * DOUBLE_YELLOW for the centre line of a bigger two-way road, -1 = only what the camera's line reader sees: a small
   * street often has none). It tells which of the camera's four lines is a road edge (solid), a line between lanes, or
   * past the edge (not drawn).
   */
  class Lanes(val left: Int, val right: Int, val leftEdge: Int) {
    /** camera line [i] (0 = two over on the left .. 3 = two over on the right): one of the ROLE_ values */
    fun role(i: Int): Int {
      val n = if (i < 2) left else right
      if (n < 0) return ROLE_UNKNOWN
      val near = i == 1 || i == 2                 // the lines of our own lane
      return when {
        near && n == 0 -> if (i == 1) ROLE_LEFT_EDGE else ROLE_RIGHT_EDGE
        near -> ROLE_INNER
        n == 0 -> ROLE_BEYOND
        n == 1 -> if (i == 0) ROLE_LEFT_EDGE else ROLE_RIGHT_EDGE
        else -> ROLE_INNER
      }
    }
    companion object {
      const val ROLE_UNKNOWN = 0; const val ROLE_INNER = 1; const val ROLE_LEFT_EDGE = 2; const val ROLE_RIGHT_EDGE = 3
      const val ROLE_BEYOND = 4
      private val MINOR = setOf("residential", "service", "living_street", "unclassified", "road", "track")

      /** from the live lane tracker; null when it isn't running */
      fun live(): Lanes? {
        val b = LaneBrain.Live.INSTANCE.brain ?: return null
        val (l, pl) = b.side(-1)
        val (r, pr) = b.side(1)
        val road = b.road
        val leftEdge = when {
          road == null -> -1
          road.oneway || road.highway -> LaneMap.EDGE_YELLOW
          road.kind in MINOR -> -1
          else -> LaneMap.DOUBLE_YELLOW
        }
        return Lanes(if (pl >= 0.7) l else -1, if (pr >= 0.7) r else -1, leftEdge)
      }
    }
  }

  // ------------------------------------------------------------------------------------------- colours
  private class Pal(val top: Int, val hor: Int, val road: Int, val white: Int, val yellow: Int, val curb: Int,
                    val arrow: Int, val path: Int, val pathOff: Int, val change: Int, val blind: Int, val shadow: Int)
  private val NIGHT = Pal(0xFF07090D.toInt(), 0xFF141920.toInt(), 0xFF1D232D.toInt(), 0xFFDFE4EA.toInt(),
    0xFFD7B43E.toInt(), 0xFF5D6672.toInt(), 0xD9DFE4EA.toInt(), 0xFF3B86FF.toInt(), 0xFF7F8A99.toInt(),
    0xFF3B86FF.toInt(), 0xFFFF4D4D.toInt(), 0xFF000000.toInt())
  private val DAY = Pal(0xFFC3CAD3.toInt(), 0xFFE3E7EC.toInt(), 0xFFB4BCC6.toInt(), 0xFFFFFFFF.toInt(),
    0xFFE4B42C.toInt(), 0xFF8A949F.toInt(), 0xE6FFFFFF.toInt(), 0xFF1F6FFF.toInt(), 0xFF6F7A88.toInt(),
    0xFF1F6FFF.toInt(), 0xFFE53935.toInt(), 0xFF3A4048.toInt())

  // ------------------------------------------------------------------------------------------- camera
  private var W = 1f; private var H = 1f; private var F = 1.0
  private var fMin = FMIN
  private var camU = 0.0; private var camF = 0.0; private var sp = 0.0; private var cp = 1.0
  private var px = 0f; private var py = 0f; private var pz = 0.0

  private fun setCamera() {
    W = width.toFloat(); H = height.toFloat()
    F = (H / 2) / tan(Math.toRadians(FOV_HALF))
    val e = Math.toRadians(elev.toDouble())
    val alpha = atan((SceneSprites.egoCy - 0.5) * 2 * tan(Math.toRadians(FOV_HALF)))
    val p = e - alpha
    sp = sin(p); cp = cos(p)
    camU = dist * sin(e)
    camF = -EGO_BACK - dist * cos(e)
    fMin = camF + 2.0          // below the bottom of the screen, still in front of the camera
  }

  /** ground point (metres right, ahead of the comma camera, up) to the screen: [px], [py]; false if behind */
  private fun proj(r: Double, f: Double, h: Double = 0.0): Boolean {
    val dy = h - camU; val df = f - camF
    val zc = -dy * sp + df * cp
    if (zc < 0.3) return false
    val yc = dy * cp + df * sp
    px = (W / 2 + F * r / zc).toFloat(); py = (H / 2 - F * yc / zc).toFloat(); pz = zc
    return true
  }
  private fun yAt(f: Double): Float { proj(0.0, f); return py }

  /** fill [path] with a colour fading from [ca] at screen height [ya] to [cb] at [yb] (distance = screen height) */
  private fun grad(c: Canvas, ya: Float, yb: Float, ca: Int, cb: Int) {
    pFill.color = -0x1000000
    pFill.shader = LinearGradient(0f, ya, 0f, yb, ca, cb, Shader.TileMode.CLAMP)
    c.drawPath(path, pFill)
    pFill.shader = null
  }

  // ------------------------------------------------------------------------------------------- state
  private val smooth = LaneFit.Smooth()
  // 2.90.0 (SceneSteady): what the picture remembers between frames
  private val sPose = SceneSteady.Pose()
  private val sLanes = SceneSteady.Lanes()
  private val sLines = SceneSteady.Lines()
  private val crossings = SceneSteady.Crossings()
  private val mapEase = SceneSteady.Ease(0.16, 0.0)
  private var shownFit: LaneFit.Fit? = null      // the last fit that lined up: carried on while the next ones don't
  private var shownNs = 0L
  private var shownOdo = 0.0
  private var frameNs = 0L
  private var pendingShift = 0
  private val covEase = arrayOf(SceneSteady.Ease(0.4), SceneSteady.Ease(0.4))
  private val edgeY = arrayOfNulls<FloatArray>(2)
  private val edgeA = FloatArray(2)
  private val edgeOn = BooleanArray(2)
  private var drawNs = 0L
  private var roadL: DoubleArray? = null; private var roadR: DoubleArray? = null
  private val roadFar = SceneSteady.Ease(0.4)
  private val roadA = SceneSteady.Ease(0.25, 0.0)
  private val traffic = Traffic()
  private val seen = HashMap<Long, Seen>()
  private var lastNs = 0L
  private var lastFrameNs = 0L
  private val pFill = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.FILL }
  private val pBmp = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
  private val pLead = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG).apply {
    colorFilter = ColorMatrixColorFilter(ColorMatrix().apply { setScale(0.52f, 0.72f, 1.18f, 1f) })
  }
  private val pGhost = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG).apply {
    colorFilter = ColorMatrixColorFilter(ColorMatrix().apply { setScale(0.75f, 0.86f, 1.05f, 1f) }); alpha = 120
  }
  private val path = Path()
  private val src = Rect()
  private val dst = RectF()
  private var rs = DoubleArray(512); private var fs = DoubleArray(512)

  init {
    SceneSprites.init(ctx)
    SceneSprites.preload()
    isClickable = true
  }

  // ------------------------------------------------------------------------------------------- frame loop
  private val tick = object : Choreographer.FrameCallback {
    override fun doFrame(nowNs: Long) {
      if (!isAttachedToWindow || visibility != VISIBLE) return
      val tt = t
      val gap = if (tt != null && tt.started) 30_000_000L else 400_000_000L
      if (nowNs - lastFrameNs >= gap) { lastFrameNs = nowNs; invalidate() }
      Choreographer.getInstance().postFrameCallback(this)
    }
  }
  private var looping = false
  private fun loop(on: Boolean) {
    if (on == looping) return
    looping = on
    if (on) Choreographer.getInstance().postFrameCallback(tick) else Choreographer.getInstance().removeFrameCallback(tick)
  }
  override fun onAttachedToWindow() { super.onAttachedToWindow(); loop(visibility == VISIBLE) }
  override fun onDetachedFromWindow() { loop(false); super.onDetachedFromWindow() }
  override fun onVisibilityChanged(changedView: View, v: Int) { super.onVisibilityChanged(changedView, v); loop(isAttachedToWindow && visibility == VISIBLE) }

  // ------------------------------------------------------------------------------------------- touch
  private var gesturing = false
  private var twoY = Float.NaN
  private val scale = ScaleGestureDetector(ctx, object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
    override fun onScale(d: ScaleGestureDetector): Boolean { dist /= d.scaleFactor; gesturing = true; invalidate(); return true }
  })
  private val taps = GestureDetector(ctx, object : GestureDetector.SimpleOnGestureListener() {
    override fun onDoubleTap(e: MotionEvent): Boolean { dist = DEF_DIST; elev = DEF_ELEV; host?.hybSceneView(dist, elev); invalidate(); return true }
    override fun onLongPress(e: MotionEvent) { if (!EdgeGuard.inSystemGestureZone(this@LaneSceneView, e.x, e.y)) host?.hybSceneHold() }
  })
  override fun onTouchEvent(e: MotionEvent): Boolean {
    scale.onTouchEvent(e)
    if (e.pointerCount >= 2) {
      val y = (e.getY(0) + e.getY(1)) / 2
      if (!twoY.isNaN() && e.actionMasked == MotionEvent.ACTION_MOVE && !scale.isInProgress) { elev += (y - twoY) * 0.08f; gesturing = true; invalidate() }
      twoY = y
    } else twoY = Float.NaN
    if (e.actionMasked == MotionEvent.ACTION_UP || e.actionMasked == MotionEvent.ACTION_CANCEL) {
      if (gesturing) { gesturing = false; host?.hybSceneView(dist, elev) }
      twoY = Float.NaN
    }
    if (e.pointerCount < 2 && !gesturing) taps.onTouchEvent(e)
    return true
  }

  // ------------------------------------------------------------------------------------------- live data
  private fun liveFrame(t: Telemetry): Frame {
    val nowNs = System.nanoTime()
    val odo = Odometry.at(nowNs)
    var fit = if (steady && anchor) LaneAnchor.fit else LaneFit.fit
    if (fit != null && System.currentTimeMillis() - fit.atMs > 2000) fit = null
    if (steady && anchor) LaneAnchor.marks = detector?.laneMarks?.marks
    val m = t.model
    val things = ArrayList<Thing>()
    val nowMs = System.currentTimeMillis()
    for (v in traffic.update(t, nowMs, showTraffic && t.s("radar_tracks"), true)) {
      if (!showTraffic && !v.lead) continue
      val name = GENERIC[v.kind and 3]
      val L = SceneSprites.models[name]?.L ?: 4.6f
      // 2.87.1: only the car openpilot follows (radarState's leadOne) is blue, not the second lead or the cars beside us
      things.add(Thing(v.id, name, v.y, v.x + L / 2, roadHeading(m, v.x), v.id == FOLLOWED_ID, false).also { if (v.id != FOLLOWED_ID) it.road = it.psi })
    }
    if (showTraffic) {
      val det = detector
      if (det != null && det.isRunning && nowMs - det.objectsAtMs < 700) {
        for (o in det.objects) {
          val name = when (o.cls) {
            0 -> "person"; 1 -> "bicycle"; 2 -> GENERIC[(o.id and 3L).toInt()]; 3 -> "motorcycle"; 5 -> "bus"; 7 -> "truck"
            9 -> "traffic_light"; 11 -> "stop_sign"; else -> null
          } ?: continue
          val vehicle = o.cls == 2 || o.cls == 3 || o.cls == 5 || o.cls == 7
          val cx = o.cx.toDouble(); val cy = o.cy.toDouble()
          // 2.87.1: the detector says NaN when it doesn't know how far a car is (left out) or which way it faces (then
          // it goes the road's way, like the radar's cars). A NaN reaching the drawing turned every frame grey.
          if (!cx.isFinite() || !cy.isFinite()) continue
          if (vehicle && things.any { abs(it.f - cx) < cx * 0.2 + 4 && abs(it.r - cy) < cx * 0.06 + 2.2 }) continue
          val hd = o.heading.toDouble()
          // 2.89.2: traffic lights and stop signs face us (they were drawn from behind: black boxes standing in the lane)
          val facing = o.cls == 9 || o.cls == 11
          val psi = if (!vehicle) (if (facing) Math.PI else 0.0) else if (hd.isFinite()) hd else roadHeading(m, cx)
          val pole = if (o.cls == 9) curbRight(m, cx, cy) else Double.NaN
          things.add(Thing(-o.id - 5_000_000L, name, cy, cx, psi, false, false, pole).also { if (vehicle) { it.hint = hd; it.road = roadHeading(m, cx) } })
        }
      }
      if (t.s("car_memory")) carMemory?.ghosts?.forEach { g ->
        val name = GENERIC[(g.id and 3L).toInt()]
        val L = SceneSprites.models[name]?.L ?: 4.6f
        things.add(Thing(-1_000_000L - g.id, name, g.y, g.x + L / 2, roadHeading(m, g.x), false, true))
      }
    }
    val lanes = try { Lanes.live() } catch (e: Exception) { null }
    return frameOf(m, fit, odo, nowNs, things, t.latActive, t.leftBlindspot, t.rightBlindspot, detector?.laneMarks?.marks, lanes, t.vEgo.toDouble())
  }

  /** 2.90.0: [r] / [f] is left of a yellow line drawn on our left (the other direction's side of the road) */
  private fun pastYellow(r: Double, f: Double): Boolean {
    for (t in sLines.tracks) {
      if (t.alpha < 0.5f || t.r >= 0 || (t.style != LaneMap.EDGE_YELLOW && t.style != LaneMap.DOUBLE_YELLOW)) continue
      if (r < LaneFit.interp(t.x, t.y, f.coerceIn(0.0, 80.0)) - 0.6) return true
    }
    return false
  }

  private class Seen(var r: Double, var f: Double, var psi: Double, var ns: Long, var alpha: Double, var th: Thing) {
    var facing: SceneSteady.Facing? = null
  }

  /**
   * One frame from what is known at [nowNs] (the live loop and the replay tests both come through here): where we are
   * on the map, the road users eased toward where they were seen, the lane card's count.
   * 2.90.0, with [steady]: the map stays through a few fits that don't line up (the last good one carried on by the
   * car's own motion for up to 2.5 s / 200 ft) and then fades instead of vanishing; a correction of our place on it
   * slides in; road users fade in and out; the lane card's count goes through SceneSteady.Lanes.
   */
  fun frameOf(m: ModelSnapshot?, fitIn: LaneFit.Fit?, odo: DoubleArray, nowNs: Long, thingsIn: List<Thing>, latActive: Boolean,
              blindL: Boolean, blindR: Boolean, marks: Array<LaneMarks.Mark>?, rawLanes: Lanes?, vEgo: Double = 0.0): Frame {
    val things = ArrayList<Thing>(thingsIn)
    things.removeAll { !it.ok() }
    // ease every road user toward where it was seen (radar and camera jump a little from frame to frame)
    val dt = if (lastNs == 0L) 0.0 else ((nowNs - lastNs) / 1e9).coerceIn(0.0, 0.5)
    val first = lastNs == 0L
    lastNs = nowNs
    val k = 1 - exp(-dt / 0.12)
    // 2.90.0: its direction turns slower than its place (the detector's heading flickers, and each turn is another picture)
    val kPsi = if (steady) 1 - exp(-dt / 0.35) else k
    for (th in things) {
      var s = seen[th.id]
      // 2.90.0: a detected car faces the way it moves, not the way the detector guessed in this frame
      if (steady && th.road.isFinite()) {
        val fc = s?.facing ?: SceneSteady.Facing()
        th.psi = fc.update(th.r, th.f, vEgo, th.road, th.hint, dt, pastYellow(th.r, th.f))
        if (s != null) s.facing = fc else { s = Seen(th.r, th.f, th.psi, nowNs, if (first) 1.0 else 0.0, th).also { it.facing = fc }; seen[th.id] = s; th.alpha = s.alpha; continue }
      }
      if (s == null || abs(s.f - th.f) > 8 || abs(s.r - th.r) > 3 || !(s.r + s.f + s.psi).isFinite()) {
        val nw = Seen(th.r, th.f, th.psi, nowNs, if (steady && !first && s == null) 0.0 else 1.0, th)
        nw.facing = s?.facing
        seen[th.id] = nw; th.alpha = nw.alpha; continue
      }
      s.r += (th.r - s.r) * k; s.f += (th.f - s.f) * k
      val dPsi = Math.IEEEremainder(th.psi - s.psi, 2 * Math.PI)
      // turned around (it faces us after all): at once - easing through it would be the car spinning on the spot
      if (steady && abs(dPsi) > 1.75) s.psi = th.psi else s.psi += dPsi * kPsi
      s.ns = nowNs
      s.alpha = min(1.0, s.alpha + dt / 0.3)
      th.r = s.r; th.f = s.f; th.psi = s.psi; th.alpha = s.alpha; s.th = th
    }
    if (steady) {
      // a road user no longer reported fades out where it was (0.3 s) instead of blinking off
      for (s in seen.values) if (s.ns != nowNs) {
        s.alpha -= dt / 0.3
        if (s.alpha > 0.02) things.add(Thing(s.th.id, s.th.model, s.r, s.f, s.psi, s.th.lead, s.th.remembered, s.th.pole).also { it.alpha = s.alpha })
      }
      seen.values.removeAll { it.ns != nowNs && it.alpha <= 0.02 }
    } else seen.values.removeAll { nowNs - it.ns > 2_000_000_000L }

    if (!steady) {
      val pose = if (fitIn != null && !fitIn.hidden) smooth.pose(fitIn, odo, nowNs) else null
      return Frame(m, fitIn, pose, odo[3], things, latActive, blindL, blindR, marks, rawLanes, atNs = nowNs)
    }
    val fdt = if (frameNs == 0L) 0.0 else ((nowNs - frameNs) / 1e9).coerceIn(0.0, 0.5)
    val startUp = frameNs == 0L || nowNs - frameNs > 1_500_000_000L
    frameNs = nowNs
    val good = fitIn != null && !fitIn.hidden
    val kept = shownFit
    val fit: LaneFit.Fit?
    val target: Double
    if (good) { fit = fitIn; shownFit = fitIn; shownNs = nowNs; shownOdo = odo[3]; target = 1.0 }
    else if (kept != null && (fitIn == null || LaneFit.sameFrame(kept.built, fitIn.built))) {
      // no fit that lines up right now: the last one that did, carried on - shown as it was for a little while, then fading
      fit = kept
      target = if (nowNs - shownNs < 2_500_000_000L && odo[3] - shownOdo < 60.0) 1.0 else 0.0
    } else { fit = null; shownFit = null; target = 0.0 }
    if (startUp) mapEase.set(target)
    var a = mapEase.to(target, fdt)
    if (fit == null) { a = 0.0; mapEase.set(0.0) }
    if (target == 0.0 && a < 0.02) { shownFit = null; a = 0.0 }
    val pose = if (fit != null && a > 0.0) sPose.pose(fit, odo, nowNs) else null
    val lanes = sLanes.update(rawLanes, pendingShift, nowNs / 1_000_000)
    pendingShift = 0
    return Frame(m, fit, pose, odo[3], things, latActive, blindL, blindR, marks, lanes, if (pose != null) a.toFloat() else 0f, nowNs)
  }

  /** 2.89.2: where a traffic light's mast pole stands: the right road edge openpilot sees at [f] m (+1 m), else 4 m right
   *  of the light; never left of it */
  private fun curbRight(m: ModelSnapshot?, f: Double, r: Double): Double {
    if (m != null && m.edgeStd.size > 1 && m.edgeStd[1] <= 1.5f && f <= m.edgeX[1].last() + 20) {
      val e = LaneFit.interp(m.edgeX[1], m.edgeY[1], f) + 1.0
      if (e.isFinite()) return max(e, r + 1.0)
    }
    return r + 4.0
  }

  /** the road's direction at [x] m ahead, from openpilot's path (radians, right = positive) */
  private fun roadHeading(m: ModelSnapshot?, x: Double): Double {
    val xs = m?.posX ?: return 0.0
    val ys = m.posY
    if (xs.size < 3) return 0.0
    val a = LaneFit.interp(xs, ys, x - 4); val b = LaneFit.interp(xs, ys, x + 4)
    return atan2(b - a, 8.0)
  }

  // ------------------------------------------------------------------------------------------- drawing
  private var failAt = 0L
  /** never let a drawing problem take StarView down mid-drive: the frame is skipped and logged (once in 10 s) */
  override fun onDraw(c: Canvas) {
    try {
      drawAll(c)
    } catch (e: Throwable) {
      c.drawColor(if (night) NIGHT.hor else DAY.hor)
      val now = System.currentTimeMillis()
      if (now - failAt > 10_000) { failAt = now; android.util.Log.w("StarView/Scene2", "draw", e) }
    }
  }

  private fun drawAll(c: Canvas) {
    if (width < 2 || height < 2) return
    val fr = frame ?: t?.let { liveFrame(it) } ?: Frame(null, null, null, 0.0, emptyList(), false, false, false, null)
    val pal = if (night) NIGHT else DAY
    setCamera()
    val yHor = (H / 2 - F * sp / cp).toFloat()
    // sky
    pFill.color = -0x1000000
    pFill.shader = LinearGradient(0f, 0f, 0f, max(1f, yHor), pal.top, pal.hor, Shader.TileMode.CLAMP)
    c.drawRect(0f, 0f, W, H, pFill)
    pFill.shader = null
    pFill.color = pal.hor
    c.drawRect(0f, max(0f, yHor), W, H, pFill)

    val m = fr.model
    val mapOn = fr.pose != null && fr.fit != null && !fr.fit.hidden
    val styleAt = ArrayList<DoubleArray>()          // map lines' (right, style) at 20 m ahead, for the camera's lines
    val ddt = if (drawNs == 0L) -1.0 else ((fr.atNs - drawNs) / 1e9).let { if (it < 0 || it > 0.6) -1.0 else it }
    drawNs = fr.atNs
    if (!steady) {
      if (mapOn) drawMap(c, fr.fit!!, fr.pose!!, pal, styleAt, m, -1.0)
      else if (m != null) drawCamRoad(c, m, pal)
    } else {
      // 2.90.0: the map and the camera's own road cross-fade (the map used to appear and vanish between two frames)
      val a = if (mapOn) fr.mapAlpha else 0f
      anchorDraw = anchor
      if (a < 0.995f && m != null) drawCamRoadSteady(c, m, pal, 1f - a, ddt)
      if (a >= 0.995f) drawMap(c, fr.fit!!, fr.pose!!, pal, styleAt, m, ddt)
      else if (a > 0.005f) {
        val sc = c.saveLayerAlpha(0f, 0f, W, H, (a * 255).roundToInt())
        try { drawMap(c, fr.fit!!, fr.pose!!, pal, styleAt, m, ddt) } finally { c.restoreToCount(sc) }
      }
    }
    if (m != null) {
      val ma = if (mapOn) fr.mapAlpha else 0f
      // with the map as the road (LaneAnchor) its lines are the lines: the camera's own are only followed (for the
      // moment we cross one) and drawn where there is no map
      if (steady && anchor) drawCamLinesSteady(c, m, fr, pal, 0f, styleAt, ddt, 1f - ma)
      else if (steady) drawCamLinesSteady(c, m, fr, pal, ma, styleAt, ddt, 1f)
      else drawCamLines(c, m, fr, pal, mapOn, styleAt)
      drawLaneTints(c, m, fr, pal)
      drawPath(c, m, fr, pal)
    }
    // fog: everything fades into the horizon
    val y0 = yAt(110.0); val y1 = yAt(270.0)
    pFill.color = -0x1000000
    pFill.shader = LinearGradient(0f, y1, 0f, y0, pal.hor, pal.hor and 0x00FFFFFF, Shader.TileMode.CLAMP)
    c.drawRect(0f, max(0f, yHor - 2), W, y0, pFill)
    pFill.shader = null
    drawThings(c, fr, pal)
  }

  // ---------------------------------------------------------------- the map
  private fun ensure(n: Int) { if (rs.size < n) { rs = DoubleArray(n * 2); fs = DoubleArray(n * 2) } }

  private fun drawMap(c: Canvas, fit: LaneFit.Fit, pose: LaneFit.Pose, pal: Pal, styleAt: ArrayList<DoubleArray>, m: ModelSnapshot?, dt: Double) {
    // what the camera covers sideways (10 m ahead): map lines there give way to the camera's own near the car; the
    // rest (a ramp beside us, the far side of a wide road) are drawn all the way
    var covL = Double.NaN; var covR = Double.NaN
    if (m != null) {
      for (i in 0 until 4) if (m.laneProb[i] >= 0.35f) { val r = LaneFit.interp(m.laneX[i], m.laneY[i], 10.0); covL = if (covL.isNaN()) r else min(covL, r); covR = if (covR.isNaN()) r else max(covR, r) }
      for (e in 0 until min(2, m.edgeStd.size)) if (m.edgeStd[e] <= 1.2f) { val r = LaneFit.interp(m.edgeX[e], m.edgeY[e], 10.0); covL = if (covL.isNaN()) r else min(covL, r); covR = if (covR.isNaN()) r else max(covR, r) }
    }
    if (steady) {
      // 2.90.0: what the camera covers widens and narrows smoothly (a map line near the car switched between faded and
      // full as a camera line came and went)
      if (dt < 0) { covEase[0].set(covL); covEase[1].set(covR) }
      covL = covEase[0].to(covL, dt); covR = covEase[1].to(covR, dt)
    }
    val b = fit.built
    val reach = fit.reach
    val ways = ArrayList<LaneMap.BWay>()
    val beside = alongside(fit, pose)
    for ((i, w) in b.ways.withIndex()) {
      if (reach != null && i < reach.size && !reach[i] && !(beside != null && i < beside.size && beside[i])) continue
      if (w.near(pose.x, pose.y, FMAX)) ways.add(w)
    }
    ways.sortBy { it.layer }
    // road surface
    path.rewind()
    for (w in ways) strip(w.left, w.leftY, w.right, w.rightY, pose)
    pFill.color = pal.road
    c.drawPath(path, pFill)
    // lines, fading in where the camera's own lines fade out (25 - 40 m)
    val ya = yAt(25.0); val yb = yAt(40.0)
    val dim = if (night) 0xFF78808C.toInt() else 0xFF98A1AC.toInt()      // the other direction's lines (LaneRibbon)
    for ((style, color) in listOf(LaneMap.DASH_DIM to dim, LaneMap.SOLID_DIM to dim, LaneMap.EDGE_WHITE to pal.white, LaneMap.DASH_WHITE to pal.white, LaneMap.SOLID_WHITE to pal.white,
        LaneMap.EDGE_YELLOW to pal.yellow, LaneMap.DOUBLE_YELLOW to pal.yellow, LaneMap.CURB to pal.curb)) {
      for (covered in listOf(true, false)) {
        path.rewind()
        for (w in ways) for (ln in w.lines) if (ln.style == style && coveredBy(ln, pose, covL, covR) == covered) mapLine(ln, pose, styleAt)
        if (covered) grad(c, ya, yb, color and 0x00FFFFFF, color) else { pFill.color = color; c.drawPath(path, pFill) }
      }
    }
    // turn arrows on the turn lanes
    path.rewind()
    for (w in ways) for (a in w.arrows) arrow(a, pose)
    grad(c, ya, yb, pal.arrow and 0x00FFFFFF, pal.arrow)
  }

  private var anchorDraw = false
  /** tests: the last line crossing the camera's lines showed (1 right, -1 left), 0 once read */
  var crossed = 0
  private var besideFit: LaneFit.Fit? = null
  private var besideWays: BooleanArray? = null

  /** 2.89.2 (owner, Nov 16, on the highway: "lines the other side ... make it look good"): roads we can't drive onto from
   *  here were left out, so the highway's other side (and a road running beside it) was bare ground with its cars
   *  floating on it. Also drawn now: a road on our level (OSM layer) running alongside ours (within 25 degrees) within
   *  70 m sideways somewhere from 30 m behind to 220 m ahead. A road on another level (a viaduct's street below) stays
   *  out. Worked out once per fit. */
  private fun alongside(fit: LaneFit.Fit, pose: LaneFit.Pose): BooleanArray? {
    if (fit === besideFit) return besideWays
    besideFit = fit
    val m = fit.match
    val b = fit.built
    if (m == null || m.way !in b.ways.indices) { besideWays = null; return null }
    val layer = b.ways[m.way].layer
    val fx = sin(pose.h); val fy = cos(pose.h)
    val out = BooleanArray(b.ways.size)
    for ((i, w) in b.ways.withIndex()) {
      if (w.layer != layer || !w.near(pose.x, pose.y, 250.0)) continue
      for (j in 0 until w.px.size - 1) {
        val f = pose.fwd(w.px[j], w.py[j])
        if (f < -30 || f > 220 || abs(pose.right(w.px[j], w.py[j])) > 70) continue
        val dx = w.px[j + 1] - w.px[j]; val dy = w.py[j + 1] - w.py[j]
        val l = hypot(dx, dy)
        if (l < 1e-3) continue
        if (abs(fx * dy - fy * dx) / l < 0.42) { out[i] = true; break }
      }
    }
    besideWays = out
    return out
  }

  /** a map line the camera's own lines stand for near the car: where it's nearest to the car within 30 m ahead, it
   *  lies between the camera's outermost lines (+-1 m) */
  private fun coveredBy(ln: LaneMap.MLine, pose: LaneFit.Pose, covL: Double, covR: Double): Boolean {
    if (covL.isNaN() || (steady && anchorDraw)) return false
    var best = Double.MAX_VALUE; var r = 0.0; var any = false
    for (i in ln.x.indices) {
      val f = pose.fwd(ln.x[i].toDouble(), ln.y[i].toDouble())
      if (f < -5 || f > 30) continue
      val d = abs(f - 10)
      if (d < best) { best = d; r = pose.right(ln.x[i].toDouble(), ln.y[i].toDouble()); any = true }
    }
    // 2.90.0: 7 ft past the camera's outer lines, not 3: a map line half a lane off the camera's own (the fit is never
    // exact) was drawn beside it - two double yellow lines next to each other. A full lane further out is another line.
    val pad = if (steady) 2.2 else 1.0
    return !any || (r > covL - pad && r < covR + pad)
  }

  /** road surface between two side lines, cut where it leaves the drawn range */
  private fun strip(lx: FloatArray, ly: FloatArray, rx: FloatArray, ry: FloatArray, pose: LaneFit.Pose) {
    val n = min(lx.size, rx.size)
    ensure(2 * n)
    var start = -1
    for (i in 0..n) {
      val ok = i < n && run {
        val fl = pose.fwd(lx[i].toDouble(), ly[i].toDouble()); val fr = pose.fwd(rx[i].toDouble(), ry[i].toDouble())
        fl in fMin..FMAX && fr in fMin..FMAX
      }
      if (ok && start < 0) start = i
      if (!ok && start >= 0) {
        if (i - start >= 2) {
          var first = true
          // 2.89.2: the piece before the first point in range, cut at the near edge (fMin) - starting at that point left a
          // straight cut across a road beside us on the screen (the points are 5 m apart or more)
          if (start > 0 && nearEdge(lx, ly, start, pose)) { path.moveTo(px, py); first = false }
          for (j in start until i) { if (proj(pose.right(lx[j].toDouble(), ly[j].toDouble()), pose.fwd(lx[j].toDouble(), ly[j].toDouble()))) { if (first) path.moveTo(px, py) else path.lineTo(px, py); first = false } }
          for (j in i - 1 downTo start) { if (proj(pose.right(rx[j].toDouble(), ry[j].toDouble()), pose.fwd(rx[j].toDouble(), ry[j].toDouble()))) path.lineTo(px, py) }
          if (start > 0 && nearEdge(rx, ry, start, pose)) path.lineTo(px, py)
          path.close()
        }
        start = -1
      }
    }
  }

  /** the point of side line [xs]/[ys] between [j]-1 (out of range) and [j] where it is fMin ahead, projected (px, py) */
  private fun nearEdge(xs: FloatArray, ys: FloatArray, j: Int, pose: LaneFit.Pose): Boolean {
    val f0 = pose.fwd(xs[j - 1].toDouble(), ys[j - 1].toDouble()); val f1 = pose.fwd(xs[j].toDouble(), ys[j].toDouble())
    if (f0 >= fMin || f1 <= f0) return false
    val k = ((fMin - f0) / (f1 - f0)).coerceIn(0.0, 1.0)
    val x = xs[j - 1] + (xs[j] - xs[j - 1]) * k; val y = ys[j - 1] + (ys[j] - ys[j - 1]) * k
    return proj(pose.right(x, y), pose.fwd(x, y))
  }

  private fun mapLine(ln: LaneMap.MLine, pose: LaneFit.Pose, styleAt: ArrayList<DoubleArray>) {
    val n = ln.x.size
    ensure(n)
    for (i in 0 until n) { rs[i] = pose.right(ln.x[i].toDouble(), ln.y[i].toDouble()); fs[i] = pose.fwd(ln.x[i].toDouble(), ln.y[i].toDouble()) }
    // where it crosses 20 m ahead (for painting the camera's lines like the map's)
    for (i in 1 until n) if ((fs[i - 1] - 20) * (fs[i] - 20) <= 0 && fs[i] != fs[i - 1] && fs[i] > fs[i - 1]) {
      val k = (20 - fs[i - 1]) / (fs[i] - fs[i - 1])
      styleAt.add(doubleArrayOf(rs[i - 1] + k * (rs[i] - rs[i - 1]), ln.style.toDouble()))
    }
    val w = if (ln.style == LaneMap.CURB) 0.12 else 0.15
    when (ln.style) {
      LaneMap.DASH_WHITE, LaneMap.DASH_DIM -> dashed(n, ln.s, w)
      LaneMap.DOUBLE_YELLOW -> { runs(n, -0.1, 0.11); runs(n, 0.1, 0.11) }
      else -> runs(n, 0.0, w)
    }
  }

  /** the drawn parts (fMin..FMAX ahead) of rs/fs[0 until n] as ribbons [w] wide, moved sideways by [off] */
  private fun runs(n: Int, off: Double, w: Double) {
    var start = -1
    for (i in 0..n) {
      val ok = i < n && fs[i] in fMin..FMAX && abs(rs[i]) < 200
      if (ok && start < 0) {
        start = i
        // 2.89.2: from the near edge (fMin), not from the first point past it (a line beside us started mid-screen)
        if (i > 0 && fs[i - 1] < fMin && fs[i] > fs[i - 1] && abs(rs[i - 1]) < 200) {
          val k = (fMin - fs[i - 1]) / (fs[i] - fs[i - 1])
          rs[i - 1] += (rs[i] - rs[i - 1]) * k; fs[i - 1] = fMin
          start = i - 1
        }
      }
      if (!ok && start >= 0) { if (i - start >= 2) ribbon(start, i, off, w); start = -1 }
    }
  }

  /** dashes 10 ft long every 40 ft, fixed to the road (by distance along the line) */
  private fun dashed(n: Int, s: FloatArray, w: Double) {
    val tr = DoubleArray(16); val tf = DoubleArray(16)
    var k = 0
    while (k * PERIOD < s[n - 1]) {
      val a = k * PERIOD; val b = a + DASH
      k++
      var i = 1
      while (i < n && s[i] < a) i++
      if (i >= n) break
      var m = 0
      fun put(j0: Int, j1: Int, at: Double) {
        val L = (s[j1] - s[j0]).toDouble()
        val q = if (L <= 0) 0.0 else (at - s[j0]) / L
        if (m < 16) { tr[m] = rs[j0] + q * (rs[j1] - rs[j0]); tf[m] = fs[j0] + q * (fs[j1] - fs[j0]); m++ }
      }
      put(i - 1, i, a)
      var j = i
      while (j < n && s[j] < b) { if (m < 16) { tr[m] = rs[j]; tf[m] = fs[j]; m++ }; j++ }
      if (j < n) put(j - 1, j, b) else if (m < 16) { tr[m] = rs[n - 1]; tf[m] = fs[n - 1]; m++ }
      if (m < 2) continue
      var inRange = true
      for (q in 0 until m) if (tf[q] !in fMin..FMAX || abs(tr[q]) > 200) { inRange = false; break }
      if (inRange) ribbonOf(tr, tf, 0, m, 0.0, w)
    }
  }

  private fun ribbon(a: Int, b: Int, off: Double, w: Double) = ribbonOf(rs, fs, a, b, off, w)

  /** a flat band along r/f[a until b] (metres), [w] wide, its middle [off] right of the line */
  private fun ribbonOf(r: DoubleArray, f: DoubleArray, a: Int, b: Int, off: Double, w: Double) {
    val n = b - a
    if (n < 2) return
    val lx = FloatArray(n); val ly = FloatArray(n); val rx = FloatArray(n); val ry = FloatArray(n)
    for (q in 0 until n) {
      val i = a + q
      val i0 = max(a, i - 1); val i1 = min(b - 1, i + 1)
      var dr = r[i1] - r[i0]; var df = f[i1] - f[i0]
      val L = hypot(dr, df).takeIf { it > 1e-6 } ?: 1.0
      dr /= L; df /= L
      // right of the direction of travel: (df, -dr)
      val nr = df; val nf = -dr
      if (!proj(r[i] + nr * (off - w / 2), f[i] + nf * (off - w / 2))) return
      lx[q] = px; ly[q] = py
      if (!proj(r[i] + nr * (off + w / 2), f[i] + nf * (off + w / 2))) return
      rx[q] = px; ry[q] = py
    }
    path.moveTo(lx[0], ly[0])
    for (q in 1 until n) path.lineTo(lx[q], ly[q])
    for (q in n - 1 downTo 0) path.lineTo(rx[q], ry[q])
    path.close()
  }

  private fun arrow(a: LaneMap.MArrow, pose: LaneFit.Pose) {
    val f0 = pose.fwd(a.x.toDouble(), a.y.toDouble())
    if (f0 < 12 || f0 > 160) return
    val r0 = pose.right(a.x.toDouble(), a.y.toDouble())
    val h = a.heading - pose.h                  // the arrow's direction in the car's frame (right = positive)
    val ca = cos(h); val sa = sin(h)
    fun poly(vararg pts: Double) {
      // pts: (across, along) pairs in metres, along = the lane's direction
      var first = true
      var i = 0
      while (i < pts.size) {
        val c2 = pts[i]; val al = pts[i + 1]; i += 2
        if (!proj(r0 + al * sa + c2 * ca, f0 + al * ca - c2 * sa)) return
        if (first) path.moveTo(px, py) else path.lineTo(px, py)
        first = false
      }
      path.close()
    }
    val parts = a.turn.split(';')
    poly(-0.16, -2.6, 0.16, -2.6, 0.16, 0.0, -0.16, 0.0)                        // shaft
    for (p in parts) {
      when {
        p == "through" -> poly(-0.16, 0.0, 0.16, 0.0, 0.16, 0.6, 0.55, 0.6, 0.0, 2.0, -0.55, 0.6, -0.16, 0.6)
        p.contains("left") -> poly(-0.16, -0.3, -0.16, 0.05, -0.9, 0.05, -0.9, 0.45, -1.7, -0.12, -0.9, -0.7, -0.9, -0.3)
        p.contains("right") -> poly(0.16, -0.3, 0.16, 0.05, 0.9, 0.05, 0.9, 0.45, 1.7, -0.12, 0.9, -0.7, 0.9, -0.3)
      }
    }
  }

  // ---------------------------------------------------------------- the camera's road
  private fun lineReach(m: ModelSnapshot, i: Int): Double = min(m.laneX[i].last().toDouble(), 90.0 * (0.6 + 0.4 * m.laneProb[i]))

  /** no map: a road surface between the camera's road edges (or a little past its outer lane lines) */
  private fun drawCamRoad(c: Canvas, m: ModelSnapshot, pal: Pal) {
    val left = sideLine(m, true) ?: return
    val right = sideLine(m, false) ?: return
    val far = min(left.third, right.third)
    path.rewind()
    var first = true
    var f = FMIN
    while (f <= far) { if (proj(LaneFit.interp(left.first, left.second, f), f)) { if (first) path.moveTo(px, py) else path.lineTo(px, py); first = false }; f += 3.0 }
    f -= 3.0
    while (f >= FMIN) { if (proj(LaneFit.interp(right.first, right.second, f), f)) path.lineTo(px, py); f -= 3.0 }
    path.close()
    grad(c, yAt(far * 0.55), yAt(far), pal.road, pal.road and 0x00FFFFFF)
  }

  /** (ahead, right, reach) of the left or right side of the road as the camera sees it */
  private fun sideLine(m: ModelSnapshot, left: Boolean): Triple<FloatArray, FloatArray, Double>? {
    val e = if (left) 0 else 1
    if (m.edgeStd.size > e && m.edgeStd[e] <= 1.2f) return Triple(m.edgeX[e], m.edgeY[e], min(70.0, m.edgeX[e].last().toDouble()))
    val order = if (left) intArrayOf(0, 1) else intArrayOf(3, 2)
    for (i in order) if (m.laneProb[i] >= 0.35f) {
      val ys = FloatArray(m.laneY[i].size) { m.laneY[i][it] + if (left) -0.6f else 0.6f }
      return Triple(m.laneX[i], ys, lineReach(m, i))
    }
    return null
  }

  /** 2.90.0: no map: the camera's road surface, its sides moving smoothly (they jumped between the road edge and the
   *  outer lane line as the camera saw one or the other) and the whole fading in and out. [dt] < 0: nothing to ease from */
  private fun drawCamRoadSteady(c: Canvas, m: ModelSnapshot, pal: Pal, alpha: Float, dt: Double) {
    val left = sideLine(m, true); val right = sideLine(m, false)
    val ok = left != null && right != null
    val n = 27
    if (ok) {
      val far = min(left!!.third, right!!.third)
      var l = roadL; var r = roadR
      if (l == null || r == null || dt < 0 || !roadFar.v.isFinite()) {
        l = DoubleArray(n) { LaneFit.interp(left.first, left.second, FMIN + it * 3.0) }
        r = DoubleArray(n) { LaneFit.interp(right.first, right.second, FMIN + it * 3.0) }
        roadL = l; roadR = r; roadFar.set(far)
      } else {
        val k = 1 - exp(-dt / 0.4)
        for (i in 0 until n) {
          val f = FMIN + i * 3.0
          l[i] += (LaneFit.interp(left.first, left.second, f) - l[i]) * k
          r[i] += (LaneFit.interp(right.first, right.second, f) - r[i]) * k
        }
        roadFar.to(far, dt)
      }
    }
    if (dt < 0) roadA.set(if (ok) 1.0 else 0.0)
    val a = roadA.to(if (ok) 1.0 else 0.0, max(0.0, dt)) * alpha
    val l = roadL ?: return; val r = roadR ?: return
    val far = roadFar.v
    if (!far.isFinite() || a < 0.02) return
    path.rewind()
    var first = true
    var i = 0
    while (i < n && FMIN + i * 3.0 <= far) { if (proj(l[i], FMIN + i * 3.0)) { if (first) path.moveTo(px, py) else path.lineTo(px, py); first = false }; i++ }
    i--
    while (i >= 0) { if (proj(r[i], FMIN + i * 3.0)) path.lineTo(px, py); i-- }
    path.close()
    val col = (pal.road and 0x00FFFFFF) or ((a * 255).roundToInt().coerceIn(0, 255) shl 24)
    grad(c, yAt(far * 0.55), yAt(far), col, col and 0x00FFFFFF)
  }

  /** how camera line [i] is painted right now (LaneMap style, -1 = not at all): the map line it lies on, the camera's
   *  line reader, and which line of the road it is by the lane card's count */
  private fun lineStyle(i: Int, m: ModelSnapshot, fr: Frame, role: Int, styleAt: ArrayList<DoubleArray>, reach: Double): Int {
    val r20 = LaneFit.interp(m.laneX[i], m.laneY[i], min(20.0, reach * 0.5))
    val near = styleAt.minByOrNull { abs(it[0] - r20) }
    var mapStyle = if (near != null && abs(near[0] - r20) < 0.9) near[1].toInt() else -1
    if (mapStyle == LaneMap.CURB || mapStyle == LaneMap.EDGE_WHITE) mapStyle = LaneMap.SOLID_WHITE
    val mk = fr.marks?.getOrNull(i)
    val rd = if (mk == null || mk.kind < LaneMarks.DASHED) -1 else when {
      mk.yellow && mk.double -> LaneMap.DOUBLE_YELLOW
      mk.yellow -> LaneMap.EDGE_YELLOW
      mk.kind == LaneMarks.SOLID -> LaneMap.SOLID_WHITE
      else -> LaneMap.DASH_WHITE
    }
    return when (role) {
      Lanes.ROLE_LEFT_EDGE -> if (mapStyle == LaneMap.EDGE_YELLOW || mapStyle == LaneMap.DOUBLE_YELLOW) mapStyle
        else if (fr.lanes!!.leftEdge >= 0) fr.lanes.leftEdge else rd
      Lanes.ROLE_RIGHT_EDGE -> LaneMap.SOLID_WHITE
      Lanes.ROLE_INNER -> if (rd == LaneMap.SOLID_WHITE || (rd < 0 && mapStyle == LaneMap.SOLID_WHITE)) LaneMap.SOLID_WHITE else LaneMap.DASH_WHITE
      else -> if (rd >= 0) rd else if (mapStyle >= 0) mapStyle else LaneMap.DASH_WHITE
    }
  }

  /**
   * 2.90.0 (owner, Nov 17, on the first try of this version: "the yellow line is jumping ... take a look at Tesla"):
   * the paint belongs to the line. Until now the lane card's count said which of the camera's lines is the road's left
   * edge, and that one was painted yellow - so each time the count changed its mind the yellow hopped a lane over, or
   * went from single to double. Now a line is painted the way the camera's line reader sees it (yellow or white, solid,
   * dashed, double); with no reading, like the map's line it lies on; with neither, plain grey ([LaneMap.CURB]: a line
   * is there, its paint isn't known) - never by a guess. SceneSteady.Lines keeps that paint on the same line of the
   * road as it moves across the screen. The count only still says which lines lie past the road's edge (not drawn).
   */
  private fun linePaint(i: Int, m: ModelSnapshot, fr: Frame, styleAt: ArrayList<DoubleArray>, reach: Double): Int {
    val mk = fr.marks?.getOrNull(i)
    if (mk != null && mk.kind >= LaneMarks.DASHED) return when {
      mk.yellow && mk.double -> LaneMap.DOUBLE_YELLOW
      mk.yellow -> LaneMap.EDGE_YELLOW
      mk.kind == LaneMarks.SOLID -> LaneMap.SOLID_WHITE
      else -> LaneMap.DASH_WHITE
    }
    val r20 = LaneFit.interp(m.laneX[i], m.laneY[i], min(20.0, reach * 0.5))
    val near = styleAt.minByOrNull { abs(it[0] - r20) }
    if (near != null && abs(near[0] - r20) < 0.9) return near[1].toInt().let { if (it == LaneMap.EDGE_WHITE) LaneMap.SOLID_WHITE else it }
    return LaneMap.CURB
  }

  private val lsStyle = IntArray(4); private val lsWant = BooleanArray(4); private val lsReach = DoubleArray(4)

  /**
   * 2.90.0: the camera's lines and road edges, held still (SceneSteady.Lines): each line is followed by where it is on
   * the road, fades in and out, and changes its paint by cross-fading once the new paint held. [mapA]: how much of the
   * map is drawn - with it the camera's lines hand over to the map's at 80 - 130 ft, without it they run as far as seen.
   */
  private fun drawCamLinesSteady(c: Canvas, m: ModelSnapshot, fr: Frame, pal: Pal, mapA: Float, styleAt: ArrayList<DoubleArray>, dt: Double, show: Float) {
    if (m.laneX.size >= 4 && m.laneY.size >= 4 && m.laneProb.size >= 4) {
      for (i in 0 until 4) {
        val role = fr.lanes?.role(i) ?: Lanes.ROLE_UNKNOWN
        lsWant[i] = role != Lanes.ROLE_BEYOND
        val full = lineReach(m, i)
        lsReach[i] = full + (min(42.0, full) - full) * mapA
        lsStyle[i] = linePaint(i, m, fr, styleAt, lsReach[i])
      }
      sLines.update(m.laneX, m.laneY, m.laneProb, lsWant, lsStyle, lsReach, fr.atNs)
      val cr = crossings.update(sLines.shift, m.laneChanging, m.laneChangeFinishing, m.laneChangeDir, fr.atNs / 1_000_000, m.laneProb[1], m.laneProb[2])
      if (cr != 0) { pendingShift = cr; if (anchor && frame == null) LaneAnchor.pendingShift = cr; crossed = cr }
      if (show > 0.02f) for (t in sLines.tracks) {
        if (t.alpha < 0.02f) continue
        if (t.mix < 1f) camLine(c, t, t.from, t.alpha * (1 - t.mix) * show, fr, pal, mapA)
        camLine(c, t, t.style, t.alpha * t.mix * show, fr, pal, mapA)
      }
    }
    // road edges: each comes and goes softly (seen at a spread of 1.0 m, lost at 1.4) and moves smoothly
    for (e in 0 until 2) {
      val has = m.edgeStd.size > e && m.edgeX.size > e && m.edgeY.size > e && m.edgeX[e].size >= 2
      val std = if (has) m.edgeStd[e] else 9f
      edgeOn[e] = std <= (if (edgeOn[e]) 1.4f else 1.0f)
      var ys = edgeY[e]
      if (has) {
        val src = m.edgeY[e]
        if (ys == null || ys.size != src.size || dt < 0 || abs(LaneFit.interp(m.edgeX[e], src, 10.0) - LaneFit.interp(m.edgeX[e], ys, 10.0)) > 2.0) {
          if (ys != null && ys.size == src.size && dt >= 0) edgeA[e] *= 0.3f       // somewhere else: it comes in again
          ys = src.copyOf(); edgeY[e] = ys
        } else { val k = (1 - exp(-dt / 0.12)).toFloat(); for (j in ys.indices) ys[j] += (src[j] - ys[j]) * k }
      }
      val target = if (edgeOn[e]) 1f else 0f
      edgeA[e] = if (dt < 0) target else (edgeA[e] + (target - edgeA[e]) * (1 - exp(-dt / (if (target > edgeA[e]) 0.3 else 0.45)))).toFloat()
      if (!has || ys == null || edgeA[e] * show < 0.02f) continue
      val xs = m.edgeX[e]
      val full = min(70.0, xs.last().toDouble())
      val reach = full + (min(42.0, full) - full) * mapA
      val n = (((reach - FMIN) / 3.0).toInt() + 1).coerceAtMost(120)
      if (n < 2) continue
      ensure(n)
      for (k in 0 until n) { val f = FMIN + k * 3.0; fs[k] = f; rs[k] = LaneFit.interp(xs, ys, f) }
      path.rewind()
      runs(n, 0.0, 0.12)
      val col = (pal.curb and 0x00FFFFFF) or (((pal.curb ushr 24) * edgeA[e] * show).roundToInt().coerceIn(0, 255) shl 24)
      grad(c, yAt(45.0 + (25.0 - 45.0) * mapA), yAt(70.0 + (40.0 - 70.0) * mapA), col, col and 0x00FFFFFF)
    }
  }

  private fun camLine(c: Canvas, t: SceneSteady.Line, style: Int, a: Float, fr: Frame, pal: Pal, mapA: Float) {
    if (style < 0 || a < 0.02f) return
    val reach = t.reach
    val n = (((reach - FMIN) / 3.0).toInt() + 1).coerceAtMost(120)
    if (n < 2) return
    ensure(n)
    for (k in 0 until n) { val f = FMIN + k * 3.0; fs[k] = f; rs[k] = LaneFit.interp(t.x, t.y, f) }
    path.rewind()
    when (style) {
      LaneMap.DASH_WHITE -> camDashes(n, fr.travelled)
      LaneMap.DOUBLE_YELLOW -> { runs(n, -0.1, 0.11); runs(n, 0.1, 0.11) }
      LaneMap.CURB -> runs(n, 0.0, 0.12)
      else -> runs(n, 0.0, 0.15)
    }
    val color = if (style == LaneMap.EDGE_YELLOW || style == LaneMap.DOUBLE_YELLOW) pal.yellow else if (style == LaneMap.CURB) pal.curb else pal.white
    val col = (color and 0x00FFFFFF) or ((a * 255).roundToInt().coerceIn(0, 255) shl 24)
    val fa = reach * 0.65 + (25.0 - reach * 0.65) * mapA; val fb = reach + (40.0 - reach) * mapA
    grad(c, yAt(fa), yAt(fb), col, col and 0x00FFFFFF)
  }

  private fun drawCamLines(c: Canvas, m: ModelSnapshot, fr: Frame, pal: Pal, mapOn: Boolean, styleAt: ArrayList<DoubleArray>) {
    for (i in 0 until 4) {
      val p = m.laneProb[i]
      if (p < 0.35f) continue
      // 2.87.1: past the road's edge (the lane card is sure there's no lane our way there): the camera's line there is a
      // shoulder or a barrier, not a lane line - drawing it made a 3-lane road look like 4 or 5
      val role = fr.lanes?.role(i) ?: Lanes.ROLE_UNKNOWN
      if (role == Lanes.ROLE_BEYOND) continue
      val reach = if (mapOn) min(42.0, lineReach(m, i)) else lineReach(m, i)
      val xs = m.laneX[i]; val ys = m.laneY[i]
      // how it's painted: the map line it lies on, the camera's line reader, and (2.87.1) which line of the road it is
      val r20 = LaneFit.interp(xs, ys, min(20.0, reach * 0.5))
      val near = styleAt.minByOrNull { abs(it[0] - r20) }
      var mapStyle = if (near != null && abs(near[0] - r20) < 0.9) near[1].toInt() else -1
      if (mapStyle == LaneMap.CURB || mapStyle == LaneMap.EDGE_WHITE) mapStyle = LaneMap.SOLID_WHITE
      val mk = fr.marks?.getOrNull(i)
      val rd = if (mk == null || mk.kind < LaneMarks.DASHED) -1 else when {
        mk.yellow && mk.double -> LaneMap.DOUBLE_YELLOW
        mk.yellow -> LaneMap.EDGE_YELLOW
        mk.kind == LaneMarks.SOLID -> LaneMap.SOLID_WHITE
        else -> LaneMap.DASH_WHITE
      }
      val style = when (role) {
        // the edges are solid: yellow on the left (double for a two-way road's centre line), white on the right
        Lanes.ROLE_LEFT_EDGE -> if (mapStyle == LaneMap.EDGE_YELLOW || mapStyle == LaneMap.DOUBLE_YELLOW) mapStyle
          else if (fr.lanes!!.leftEdge >= 0) fr.lanes.leftEdge else rd
        Lanes.ROLE_RIGHT_EDGE -> LaneMap.SOLID_WHITE
        // between two lanes our way: white, solid only where the reader (or, without a reading, the map) says so
        Lanes.ROLE_INNER -> if (rd == LaneMap.SOLID_WHITE || (rd < 0 && mapStyle == LaneMap.SOLID_WHITE)) LaneMap.SOLID_WHITE else LaneMap.DASH_WHITE
        else -> if (rd >= 0) rd else if (mapStyle >= 0) mapStyle else LaneMap.DASH_WHITE
      }
      if (style < 0) continue                       // a small two-way street's middle with no paint seen
      val color = if (style == LaneMap.EDGE_YELLOW || style == LaneMap.DOUBLE_YELLOW) pal.yellow else pal.white
      path.rewind()
      val n = (((reach - FMIN) / 3.0).toInt() + 1).coerceAtMost(120)
      ensure(n)
      for (k in 0 until n) { val f = FMIN + k * 3.0; fs[k] = f; rs[k] = LaneFit.interp(xs, ys, f) }
      when (style) {
        LaneMap.DASH_WHITE -> camDashes(n, fr.travelled)
        LaneMap.DOUBLE_YELLOW -> { runs(n, -0.1, 0.11); runs(n, 0.1, 0.11) }
        else -> runs(n, 0.0, 0.15)
      }
      val a = ((p - 0.3f) / 0.4f).coerceIn(0.3f, 1f)
      val col = (color and 0x00FFFFFF) or ((a * 255).roundToInt() shl 24)
      val (fa, fb) = if (mapOn) 25.0 to 40.0 else reach * 0.65 to reach
      grad(c, yAt(fa), yAt(fb), col, col and 0x00FFFFFF)
    }
    // road edges
    path.rewind()
    var any = false
    for (e in 0 until 2) {
      if (m.edgeStd.size <= e || m.edgeStd[e] > 1.2f) continue
      val xs = m.edgeX[e]; val ys = m.edgeY[e]
      val reach = min(if (mapOn) 42.0 else 70.0, xs.last().toDouble())
      val n = (((reach - FMIN) / 3.0).toInt() + 1).coerceAtMost(120)
      ensure(n)
      for (k in 0 until n) { val f = FMIN + k * 3.0; fs[k] = f; rs[k] = LaneFit.interp(xs, ys, f) }
      runs(n, 0.0, 0.12); any = true
    }
    if (any) {
      val (fa, fb) = if (mapOn) 25.0 to 40.0 else 45.0 to 70.0
      grad(c, yAt(fa), yAt(fb), pal.curb, pal.curb and 0x00FFFFFF)
    }
  }

  /** the camera's dashed lines: dashes move past the van as it drives (their place on the road isn't known) */
  private fun camDashes(n: Int, travelled: Double) {
    val tr = DoubleArray(4); val tf = DoubleArray(4)
    val phase = travelled % PERIOD
    var a = -phase
    val far = fs[n - 1]
    while (a < far) {
      val b = a + DASH
      val a2 = max(a, fs[0]); val b2 = min(b, far)
      if (b2 - a2 > 0.3) {
        for (q in 0 until 3) { val f = a2 + (b2 - a2) * q / 2; tf[q] = f; tr[q] = interpD(f, n) }
        ribbonOf(tr, tf, 0, 3, 0.0, 0.15)
      }
      a += PERIOD
    }
  }
  private fun interpD(f: Double, n: Int): Double {
    val k = ((f - fs[0]) / 3.0).coerceIn(0.0, (n - 1).toDouble())
    val i = floor(k).toInt().coerceAtMost(n - 2).coerceAtLeast(0)
    return rs[i] + (k - i) * (rs[i + 1] - rs[i])
  }

  /** the lane a lane change goes into (blue), a lane with a car in the blind spot (red) */
  private fun drawLaneTints(c: Canvas, m: ModelSnapshot, fr: Frame, pal: Pal) {
    fun lane(a: Int, b: Int, to: Double, color: Int) {
      if (m.laneProb[a] < 0.3f || m.laneProb[b] < 0.3f) return
      path.rewind()
      var f = 0.0; var first = true
      while (f <= to) { if (proj(LaneFit.interp(m.laneX[a], m.laneY[a], f), f)) { if (first) path.moveTo(px, py) else path.lineTo(px, py); first = false }; f += 3.0 }
      f -= 3.0
      while (f >= 0) { if (proj(LaneFit.interp(m.laneX[b], m.laneY[b], f), f)) path.lineTo(px, py); f -= 3.0 }
      path.close()
      val col = (color and 0x00FFFFFF) or (0x48 shl 24)
      grad(c, yAt(to * 0.5), yAt(to), col, col and 0x00FFFFFF)
    }
    // Telemetry's laneChangeDir: -1 left, 1 right (2.87.1: 2.87.0 took 1 for left and 2 for right)
    if (m.laneChanging && t?.s("lane_change_lane") != false) {
      if (m.laneChangeDir < 0) lane(0, 1, 60.0, pal.change) else if (m.laneChangeDir > 0) lane(2, 3, 60.0, pal.change)
    }
    if (fr.blindL) lane(0, 1, 18.0, pal.blind)
    if (fr.blindR) lane(2, 3, 18.0, pal.blind)
  }

  /** openpilot's path: a band the van's width, blue while openpilot steers */
  private fun drawPath(c: Canvas, m: ModelSnapshot, fr: Frame, pal: Pal) {
    val xs = m.posX; val ys = m.posY
    if (xs.size < 3) return
    var to = min(70.0, xs.last().toDouble())
    var n = (to / 2.0).toInt() + 1
    ensure(n)
    val geo = if (steady && anchor) fr.fit?.ribbon else null
    val pose = fr.pose
    if (geo != null && pose != null && fr.mapAlpha > 0.5f && !m.laneChanging) {
      // 2.90.0: on the generated road the path runs along our lane (from where we are in it to its middle within 100 ft);
      // openpilot's own path follows the real road's bends, which the drawn road only has roughly
      val adv = fr.travelled - (fr.fit?.odo?.getOrNull(3) ?: fr.travelled)       // driven since the fit
      var k = 0
      while (k < n) {
        val f = k * 2.0
        val p = geo.place(adv + f, geo.laneD + (geo.d - geo.laneD) * exp(-f / 12.0)) ?: break
        fs[k] = pose.fwd(p[0], p[1]); rs[k] = pose.right(p[0], p[1]); k++
      }
      n = k
      if (n < 2) return
      // it starts under the van (drawn a moment behind where the road has us) and is on the lane within 60 ft
      val r0 = rs[0]
      for (j in 0 until n) rs[j] -= r0 * exp(-max(0.0, fs[j]) / 6.0)
      to = fs[n - 1]
    } else
    for (k in 0 until n) { val f = k * 2.0; fs[k] = f; rs[k] = LaneFit.interp(xs, ys, f) }
    path.rewind()
    ribbon(0, n, 0.0, 1.7)
    val color = if (fr.latActive) pal.path else pal.pathOff
    val a = if (fr.latActive) 0x80 else 0x38
    val col = (color and 0x00FFFFFF) or (a shl 24)
    grad(c, yAt(8.0), yAt(to), col, col and 0x00FFFFFF)
  }

  // ---------------------------------------------------------------- road users and the van

  private fun drawThings(c: Canvas, fr: Frame, pal: Pal) {
    class D(val z: Double, val th: Thing?)
    val list = ArrayList<D>()
    for (t0 in fr.things) { val th = onRibbon(t0, fr); if (!th.ok() || th.f < FMIN || th.f > 200 || abs(th.r) > 40) continue; if (proj(th.r, th.f)) list.add(D(pz, th)) }
    if (proj(0.0, -EGO_BACK)) list.add(D(pz, null))
    list.sortByDescending { it.z }
    for (d in list) {
      if (d.th == null) { drawEgo(c, pal); continue }
      // 2.87.1: a road user that can't be drawn is left out; the rest of the picture stays
      val n = c.saveCount
      try {
        drawThing(c, d.th, pal)
      } catch (e: Exception) {
        c.restoreToCount(n)
        pFill.shader = null
        val now = System.currentTimeMillis()
        if (now - thingFailAt > 10_000) { thingFailAt = now; android.util.Log.w("StarView/Scene2", "thing ${d.th.model}", e) }
      }
    }
  }
  private var thingFailAt = 0L

  /**
   * 2.90.0: a road user on the generated road (LaneRibbon). The camera and the radar say where it is against us and
   * against the path we'll drive; the drawn road bends only roughly like the real one, so it is put the same distance
   * along the drawn road and the same distance beside our drawn path - a car in the next lane stays in the next lane
   * round a bend. A vehicle is drawn in the middle of its lane, facing along it (toward us in the other way's lanes),
   * unless it is seen driving across.
   */
  private fun onRibbon(th: Thing, fr: Frame): Thing {
    val geo = (if (steady && anchor) fr.fit?.ribbon else null) ?: return th
    val pose = fr.pose ?: return th
    if (fr.mapAlpha < 0.5f || !th.ok() || th.f < -8 || th.f > 190) return th
    val m = fr.model
    val vehicle = th.model.startsWith("car") || th.model == "truck" || th.model == "bus" || th.model == "motorcycle"
    val beside = th.r - (if (m != null && m.posX.size >= 3 && th.f > 0) LaneFit.interp(m.posX, m.posY, th.f) else 0.0)
    var d = geo.d + beside
    val adv = fr.travelled - (fr.fit?.odo?.getOrNull(3) ?: fr.travelled)
    val e = geo.edges(adv + th.f)
    val pk = geo.parking(adv + th.f)
    val across = th.road.isFinite() && abs(abs(Math.IEEEremainder(th.psi - th.road, 2 * Math.PI)) - Math.PI / 2) < Math.PI / 4
    // 2.90.2: a vehicle the camera puts past the kerb is on the street all the same - its side-to-side guess is off by
    // that much (the bus beside us on Nov 18 stood on bare ground left of the road); then in the parking lane there
    // (up to 13 ft past it: farther out it is off the street - a lot, a driveway)
    if (vehicle && !across && e[4] - e[3] > 3.0) {
      if (d > e[4] - 1.0 && d < e[4] + 4.0) d = e[4] - 1.0
      if (d < e[3] + 1.0 && d > e[3] - 4.0) d = e[3] + 1.0
    }
    // parked: in a parking lane, facing the way its side of the street goes
    val parkR = vehicle && !across && pk[1] > 1.2 && d > e[1] + 0.4
    val parkL = vehicle && !across && pk[0] > 1.2 && d < e[2] - 0.4
    if (parkR) d += (e[1] + pk[1] / 2 - d) * 0.75
    if (parkL) d += (e[2] - pk[0] / 2 - d) * 0.75
    val onRoad = d > e[2] - 1.2 && d < e[1] + 1.2
    if (vehicle && onRoad && !across && !parkR && !parkL) {
      val mid = Math.round(d / LaneRibbon.LW) * LaneRibbon.LW
      d += (mid - d) * 0.75
    }
    val p = geo.place(adv + th.f, d) ?: return th
    var psi = th.psi
    if (vehicle && !across) {
      val away = if (th.road.isFinite()) abs(Math.IEEEremainder(th.psi - th.road, 2 * Math.PI)) > Math.PI / 2 else false
      val twoWay = e[2] < e[0] - 1.0
      val otherSide = twoWay && (parkL || onRoad && d < e[0] - 0.3)
      psi = p[2] - pose.h + (if (otherSide || (away && !onRoad && !parkR && !parkL)) Math.PI else 0.0)
    }
    // a traffic light's pole stands on the corner nearest to it, not always on the right (the arm of a light over the
    // far side reached across the whole street)
    var pole = if (th.pole.isFinite()) th.pole + (pose.right(p[0], p[1]) - th.r) else th.pole
    if (th.model == "traffic_light") {
      val pd = if (d - e[3] < e[4] - d) e[3] - 0.5 else e[4] + 0.5
      geo.place(adv + th.f, pd)?.let { pole = pose.right(it[0], it[1]) }
    }
    return Thing(th.id, th.model, pose.right(p[0], p[1]), pose.fwd(p[0], p[1]), psi, th.lead, th.remembered, pole).also { it.alpha = th.alpha }
  }

  /** a soft dark oval on the road under a car (its footprint, blurred at the edges) */
  private fun shadow(c: Canvas, r: Double, f: Double, psi: Double, L: Double, Wd: Double, pal: Pal, alpha: Int) {
    val ca = cos(psi); val sa = sin(psi)
    var x0 = Float.MAX_VALUE; var x1 = -Float.MAX_VALUE; var y0 = Float.MAX_VALUE; var y1 = -Float.MAX_VALUE
    for ((al, ac) in listOf(-L / 2 to -Wd / 2, L / 2 to -Wd / 2, L / 2 to Wd / 2, -L / 2 to Wd / 2)) {
      if (!proj(r + al * sa + ac * ca, f + al * ca - ac * sa)) return
      x0 = min(x0, px); x1 = max(x1, px); y0 = min(y0, py); y1 = max(y1, py)
    }
    val cx = (x0 + x1) / 2; val cy = (y0 + y1) / 2
    val rx = max(1f, (x1 - x0) / 2); val ry = max(1f, (y1 - y0) / 2)
    // Android refuses a gradient of NaN or infinite size (IllegalArgumentException): no shadow then
    if (!(cx.isFinite() && cy.isFinite() && rx.isFinite() && ry.isFinite())) return
    val col = pal.shadow and 0x00FFFFFF
    pFill.color = -0x1000000
    pFill.shader = android.graphics.RadialGradient(cx, cy, rx, intArrayOf(col or (alpha shl 24), col or ((alpha * 0.7f).toInt() shl 24), col),
      floatArrayOf(0f, 0.65f, 1f), Shader.TileMode.CLAMP)
    c.save()
    c.scale(1f, ry / rx, cx, cy)
    c.drawCircle(cx, cy, rx, pFill)
    c.restore()
    pFill.shader = null
  }

  private fun drawThing(c: Canvas, th: Thing, pal: Pal) {
    val mdl = SceneSprites.models[th.model] ?: return
    val vehicle = th.model.startsWith("car") || th.model == "truck" || th.model == "bus" || th.model == "motorcycle"
    val fade = ((200 - th.f) / 60).coerceIn(0.0, 1.0) * th.alpha.coerceIn(0.0, 1.0)
    if (vehicle) shadow(c, th.r, th.f, th.psi, mdl.L + 1.0, mdl.W + 0.7, pal, (0x80 * fade).toInt())
    // 2.89.2: a traffic light hangs over the road from a mast arm (it stood in the lane like a post before). 2.90.2
    // (owner, Nov 18: "the traffic lights don't look right"): drawn as a signal head - a dark housing with its three
    // lenses, facing us - under the arm, instead of a picture of two heads side by side
    if (th.model == "traffic_light") { mast(c, th, HANG + LIGHT_H + 0.15, fade); signal(c, th, fade, pal); return }
    val lift = 0.0
    if (!proj(th.r, th.f, lift)) return
    val ax = px; val ay = py; val z = pz
    // which picture: the height we look from, and the turn of the car against our line of sight
    val df = th.f - camF
    val el = Math.toDegrees(atan2(camU - lift, hypot(th.r, df)))
    var pi = 0
    for (k in mdl.pitches.indices) if (abs(mdl.pitches[k] - el) < abs(mdl.pitches[pi] - el)) pi = k
    var yaw = Math.toDegrees(th.psi - atan2(th.r, df))
    yaw = ((yaw + 540) % 360) - 180
    val mirror = yaw < 0
    var yi = 0
    for (k in mdl.yaws.indices) if (abs(mdl.yaws[k] - abs(yaw)) < abs(mdl.yaws[yi] - abs(yaw))) yi = k
    val sh = mdl.sheets[pi]
    val bmp = SceneSprites.bitmap(sh.file) ?: return
    val col = yi % sh.cols; val row = yi / sh.cols
    src.set(col * sh.cw, row * sh.ch, (col + 1) * sh.cw, (row + 1) * sh.ch)
    val k = (F / z / mdl.ppm[pi]).toFloat()
    dst.set(ax - sh.ax * k, ay - sh.ay * k, ax + (sh.cw - sh.ax) * k, ay + (sh.ch - sh.ay) * k)
    val p = when { th.lead -> pLead; th.remembered -> pGhost; else -> pBmp }
    val a0 = p.alpha
    p.alpha = (a0 * fade).toInt()
    try {
      if (mirror) { c.save(); c.scale(-1f, 1f, ax, ay) }
      c.drawBitmap(bmp, src, dst, p)
      if (mirror) c.restore()
    } finally {
      p.alpha = a0
    }
  }

  private val pMast = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.STROKE; strokeCap = Paint.Cap.ROUND }
  private val rLight = RectF()

  /** a signal head hanging at [HANG] m: housing 0.4 x 1.1 m with a hood over each of its red / amber / green lenses
   *  (which one is lit isn't known: all three are drawn as unlit glass, a touch of their colour) */
  private fun signal(c: Canvas, th: Thing, fade: Double, pal: Pal) {
    if (!proj(th.r, th.f, HANG + LIGHT_H)) return
    val x = px; val top = py; val z = pz
    if (!proj(th.r, th.f, HANG)) return
    val bottom = py
    if (!(x.isFinite() && top.isFinite() && bottom.isFinite())) return
    val k = (F / z).toFloat()                       // pixels per metre there
    val w = 0.42f * k; val h = bottom - top
    if (h < 2f) return
    val a = (255 * fade).toInt().coerceIn(0, 255)
    pFill.shader = null
    pFill.color = (a shl 24) or (if (night) 0x1C1F23 else 0x2B2F35)
    rLight.set(x - w / 2, top, x + w / 2, bottom)
    c.drawRoundRect(rLight, 0.08f * k, 0.08f * k, pFill)
    val lens = intArrayOf(0x7A2626, 0x7A5C1E, 0x1F6A43)
    val glow = intArrayOf(0xC23B32, 0xC9902A, 0x2E9B63)
    for (i in 0 until 3) {
      val cy = top + h * (0.18f + 0.32f * i)
      val rr = 0.13f * k
      pFill.color = (a shl 24) or 0x101215
      c.drawCircle(x, cy, rr * 1.18f, pFill)                 // the hood's shadow round the lens
      pFill.color = (a shl 24) or lens[i]
      c.drawCircle(x, cy, rr, pFill)
      pFill.color = ((a * 0.55f).toInt() shl 24) or glow[i]
      c.drawCircle(x - rr * 0.25f, cy - rr * 0.25f, rr * 0.45f, pFill)   // a glint on the glass
    }
  }

  /** a traffic light's mast: the arm at [top] m from the light out to its pole at the roadside, and the pole */
  private fun mast(c: Canvas, th: Thing, top: Double, fade: Double) {
    val pole = if (th.pole.isFinite()) th.pole else th.r + 4.0
    if (!proj(th.r, th.f, top)) return
    val x0 = px; val y0 = py; val z0 = pz
    if (!proj(pole, th.f, top)) return
    val x1 = px; val y1 = py
    if (!proj(pole, th.f, 0.0)) return
    val x2 = px; val y2 = py
    if (!(x0.isFinite() && y0.isFinite() && x1.isFinite() && y1.isFinite() && x2.isFinite() && y2.isFinite())) return
    val k = (F / z0).toFloat()
    pMast.color = (((0xE0 * fade).toInt()) shl 24) or (if (night) 0x5A6068 else 0x4A5058)
    pMast.strokeWidth = max(1.5f, 0.13f * k)
    c.drawLine(x0, y0, x1, y1, pMast)
    pMast.strokeWidth = max(2f, 0.22f * k)
    c.drawLine(x1, y1, x2, y2, pMast)
  }

  private fun drawEgo(c: Canvas, pal: Pal) {
    val cells = SceneSprites.ego
    if (cells.isEmpty()) return
    var e = cells[0]
    for (x in cells) if (abs(x.elev - elev) < abs(e.elev - elev)) e = x
    val bmp = SceneSprites.bitmap(e.file) ?: return
    shadow(c, 0.0, -EGO_BACK, 0.0, 6.0, 2.6, pal, 0x90)
    if (!proj(0.0, -EGO_BACK)) return
    val k = (H / SceneSprites.egoH) * (SceneSprites.egoD / dist)
    dst.set(px + e.x * k, py + e.y * k, px + (e.x + e.w) * k, py + (e.y + e.h) * k)
    c.drawBitmap(bmp, null, dst, pBmp)
  }
}
