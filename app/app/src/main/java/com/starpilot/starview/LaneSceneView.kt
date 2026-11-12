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
  }

  class Thing(val id: Long, val model: String, var r: Double, var f: Double, var psi: Double, val lead: Boolean, val remembered: Boolean) {
    /** a place and a direction that can be drawn (no NaN, no infinity) */
    fun ok() = r.isFinite() && f.isFinite() && psi.isFinite()
  }

  /** Everything one drawing needs (the live one is made from the telemetry; tests make their own). */
  class Frame(val model: ModelSnapshot?, val fit: LaneFit.Fit?, val pose: LaneFit.Pose?, val travelled: Double,
              val things: List<Thing>, val latActive: Boolean, val blindL: Boolean, val blindR: Boolean,
              val marks: Array<LaneMarks.Mark>?)

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
  private val traffic = Traffic()
  private val seen = HashMap<Long, DoubleArray>()      // id -> r, f, psi, last ns
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
    var fit = LaneFit.fit
    if (fit != null && System.currentTimeMillis() - fit.atMs > 2000) fit = null
    val pose = if (fit != null && !fit.hidden) smooth.pose(fit, odo, nowNs) else null
    val m = t.model
    val things = ArrayList<Thing>()
    val nowMs = System.currentTimeMillis()
    for (v in traffic.update(t, nowMs, showTraffic && t.s("radar_tracks"), true)) {
      if (!showTraffic && !v.lead) continue
      val name = GENERIC[v.kind and 3]
      val L = SceneSprites.models[name]?.L ?: 4.6f
      // 2.87.1: only the car openpilot follows (radarState's leadOne) is blue, not the second lead or the cars beside us
      things.add(Thing(v.id, name, v.y, v.x + L / 2, roadHeading(m, v.x), v.id == FOLLOWED_ID, false))
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
          val psi = if (!vehicle) 0.0 else if (hd.isFinite()) hd else roadHeading(m, cx)
          things.add(Thing(-o.id - 5_000_000L, name, cy, cx, psi, false, false))
        }
      }
      if (t.s("car_memory")) carMemory?.ghosts?.forEach { g ->
        val name = GENERIC[(g.id and 3L).toInt()]
        val L = SceneSprites.models[name]?.L ?: 4.6f
        things.add(Thing(-1_000_000L - g.id, name, g.y, g.x + L / 2, roadHeading(m, g.x), false, true))
      }
    }
    things.removeAll { !it.ok() }
    // ease every road user toward where it was seen (radar and camera jump a little from frame to frame)
    val dt = if (lastNs == 0L) 0.0 else (nowNs - lastNs) / 1e9
    lastNs = nowNs
    val k = 1 - exp(-dt / 0.12)
    for (th in things) {
      val s = seen[th.id]
      if (s == null || abs(s[1] - th.f) > 8 || abs(s[0] - th.r) > 3 || !(s[0] + s[1] + s[2]).isFinite()) { seen[th.id] = doubleArrayOf(th.r, th.f, th.psi, nowNs.toDouble()); continue }
      s[0] += (th.r - s[0]) * k; s[1] += (th.f - s[1]) * k; s[2] += (th.psi - s[2]) * k; s[3] = nowNs.toDouble()
      th.r = s[0]; th.f = s[1]; th.psi = s[2]
    }
    seen.values.removeAll { nowNs - it[3] > 2e9 }
    return Frame(m, fit, pose, odo[3], things, t.latActive, t.leftBlindspot, t.rightBlindspot, detector?.laneMarks?.marks)
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
    if (mapOn) drawMap(c, fr.fit!!, fr.pose!!, pal, styleAt, m)
    else if (m != null) drawCamRoad(c, m, pal)
    if (m != null) {
      drawCamLines(c, m, fr, pal, mapOn, styleAt)
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

  private fun drawMap(c: Canvas, fit: LaneFit.Fit, pose: LaneFit.Pose, pal: Pal, styleAt: ArrayList<DoubleArray>, m: ModelSnapshot?) {
    // what the camera covers sideways (10 m ahead): map lines there give way to the camera's own near the car; the
    // rest (a ramp beside us, the far side of a wide road) are drawn all the way
    var covL = Double.NaN; var covR = Double.NaN
    if (m != null) {
      for (i in 0 until 4) if (m.laneProb[i] >= 0.35f) { val r = LaneFit.interp(m.laneX[i], m.laneY[i], 10.0); covL = if (covL.isNaN()) r else min(covL, r); covR = if (covR.isNaN()) r else max(covR, r) }
      for (e in 0 until min(2, m.edgeStd.size)) if (m.edgeStd[e] <= 1.2f) { val r = LaneFit.interp(m.edgeX[e], m.edgeY[e], 10.0); covL = if (covL.isNaN()) r else min(covL, r); covR = if (covR.isNaN()) r else max(covR, r) }
    }
    val b = fit.built
    val reach = fit.reach
    val ways = ArrayList<LaneMap.BWay>()
    for ((i, w) in b.ways.withIndex()) {
      if (reach != null && i < reach.size && !reach[i]) continue
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
    for ((style, color) in listOf(LaneMap.EDGE_WHITE to pal.white, LaneMap.DASH_WHITE to pal.white, LaneMap.SOLID_WHITE to pal.white,
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

  /** a map line the camera's own lines stand for near the car: where it's nearest to the car within 30 m ahead, it
   *  lies between the camera's outermost lines (+-1 m) */
  private fun coveredBy(ln: LaneMap.MLine, pose: LaneFit.Pose, covL: Double, covR: Double): Boolean {
    if (covL.isNaN()) return false
    var best = Double.MAX_VALUE; var r = 0.0; var any = false
    for (i in ln.x.indices) {
      val f = pose.fwd(ln.x[i].toDouble(), ln.y[i].toDouble())
      if (f < -5 || f > 30) continue
      val d = abs(f - 10)
      if (d < best) { best = d; r = pose.right(ln.x[i].toDouble(), ln.y[i].toDouble()); any = true }
    }
    return !any || (r > covL - 1.0 && r < covR + 1.0)
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
          for (j in start until i) { if (proj(pose.right(lx[j].toDouble(), ly[j].toDouble()), pose.fwd(lx[j].toDouble(), ly[j].toDouble()))) { if (first) path.moveTo(px, py) else path.lineTo(px, py); first = false } }
          for (j in i - 1 downTo start) { if (proj(pose.right(rx[j].toDouble(), ry[j].toDouble()), pose.fwd(rx[j].toDouble(), ry[j].toDouble()))) path.lineTo(px, py) }
          path.close()
        }
        start = -1
      }
    }
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
      LaneMap.DASH_WHITE -> dashed(n, ln.s, w)
      LaneMap.DOUBLE_YELLOW -> { runs(n, -0.1, 0.11); runs(n, 0.1, 0.11) }
      else -> runs(n, 0.0, w)
    }
  }

  /** the drawn parts (fMin..FMAX ahead) of rs/fs[0 until n] as ribbons [w] wide, moved sideways by [off] */
  private fun runs(n: Int, off: Double, w: Double) {
    var start = -1
    for (i in 0..n) {
      val ok = i < n && fs[i] in fMin..FMAX && abs(rs[i]) < 200
      if (ok && start < 0) start = i
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

  private fun drawCamLines(c: Canvas, m: ModelSnapshot, fr: Frame, pal: Pal, mapOn: Boolean, styleAt: ArrayList<DoubleArray>) {
    for (i in 0 until 4) {
      val p = m.laneProb[i]
      if (p < 0.35f) continue
      val reach = if (mapOn) min(42.0, lineReach(m, i)) else lineReach(m, i)
      val xs = m.laneX[i]; val ys = m.laneY[i]
      // how it's painted: the camera's line reader, else the map line it lies on, else a dashed white line
      var style = LaneMap.DASH_WHITE
      val mk = fr.marks?.getOrNull(i)
      val r20 = LaneFit.interp(xs, ys, min(20.0, reach * 0.5))
      val near = styleAt.minByOrNull { abs(it[0] - r20) }
      if (near != null && abs(near[0] - r20) < 0.9) style = near[1].toInt()
      if (style == LaneMap.CURB || style == LaneMap.EDGE_WHITE) style = LaneMap.SOLID_WHITE
      if (mk != null && mk.kind >= LaneMarks.DASHED) {
        style = when {
          mk.yellow && mk.double -> LaneMap.DOUBLE_YELLOW
          mk.yellow -> LaneMap.EDGE_YELLOW
          mk.kind == LaneMarks.SOLID -> LaneMap.SOLID_WHITE
          else -> LaneMap.DASH_WHITE
        }
      }
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
    val to = min(70.0, xs.last().toDouble())
    val n = (to / 2.0).toInt() + 1
    ensure(n)
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
    for (th in fr.things) { if (!th.ok() || th.f < FMIN || th.f > 200 || abs(th.r) > 40) continue; if (proj(th.r, th.f)) list.add(D(pz, th)) }
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
    val fade = ((200 - th.f) / 60).coerceIn(0.0, 1.0)
    if (vehicle) shadow(c, th.r, th.f, th.psi, mdl.L + 1.0, mdl.W + 0.7, pal, (0x80 * fade).toInt())
    if (!proj(th.r, th.f)) return
    val ax = px; val ay = py; val z = pz
    // which picture: the height we look from, and the turn of the car against our line of sight
    val df = th.f - camF
    val el = Math.toDegrees(atan2(camU, hypot(th.r, df)))
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
