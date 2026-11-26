package com.starpilot.starview

import android.content.Context
import android.graphics.Canvas
import android.util.Log
import android.view.View
import kotlin.math.abs

/**
 * 2.90.14 (owner, Nov 25: "on scene view and also in camera view a blue arrow when I need to make a turn"): the road
 * arrow and the arrival's house number on the camera's own picture, just over the road view's drawing (OverlayView),
 * through the same camera projection as openpilot's path there. Placed like in the Scene - on the anchored map's road
 * (LaneAnchor), the van on it by the camera's lines and the RTK fix - but only while that fix is better than 2 ft: on
 * the real road a few feet off is the wrong lane or the curb. A car ahead in the arrow's way covers the rest of it.
 * Hidden while the Scene is shown (MainActivity.hybSceneTick).
 */
class RoadArrowCameraView(ctx: Context, private val overlay: OverlayView) : View(ctx) {
  companion object {
    /** the RTK fix it needs (m): 2 ft */
    const val ACC_M = 0.61
    /** the far part drawn up to this many times longer along the road (RoadArrowPainter.stretch) */
    const val BOOST = 3.0

    /**
     * The part of [sh] in a [w] x [h] picture seen through [proj]: from where it comes up from under the bottom (its
     * start fades in there, and its colour runs from there) to where it leaves at a side (the arrowhead then stands at
     * the edge, pointing out), or to a car ahead in its way at [leadF] m ahead, [leadR] m right (no arrowhead: the car
     * covers the rest). With whether it keeps its arrowhead.
     */
    fun inPicture(sh: RoadArrow.Shape, proj: RoadArrowPainter.Proj, w: Float, h: Float, leadF: Double, leadR: Double): Pair<RoadArrow.Shape, Boolean>? {
      if (w <= 0f || h <= 0f) return null
      val p = FloatArray(2)
      var a = -1; var z = -1; var head = true
      for (i in 0 until sh.size) {
        if (!proj.at(sh.r[i], sh.f[i], 0.0, p)) { if (a >= 0) break else continue }
        if (a < 0) { if (p[1] <= h * 1.08f) a = i else continue }
        if (sh.f[i] > leadF && abs(sh.r[i] - leadR) < 1.3) { head = false; break }
        if (p[0] < w * 0.03f || p[0] > w * 0.97f || p[1] < 0f) break
        z = i
      }
      if (a < 0 || z < 0) return null
      return RoadArrow.part(sh, a, z + 1)?.let { it to head }
    }
  }

  var t: Telemetry? = null
  private val dp = ctx.resources.displayMetrics.density
  private val painter = RoadArrowPainter(dp)
  private val tag = ArrivalTag(dp)
  private val sPose = SceneSteady.Pose()
  private val t0 = System.nanoTime()
  private var failAt = 0L
  // the overlay's camera, unclipped (Projection.map leaves out what is past the screen's edge; the arrow starts under
  // the bottom of the picture)
  private var calib = DoubleArray(9)
  private var camF = 1.0; private var camCx = 0.0; private var camCy = 0.0
  private var wide = false; private var scale = 1.0; private var tx = 0.0; private var ty = 0.0; private var camH = 1.22
  private var depth = 1.0
  private val proj = RoadArrowPainter.Proj { r, f, h, out -> at(r, f, h, out) }
  private val p = FloatArray(2)

  init {
    isClickable = false; isFocusable = false
    setWillNotDraw(false)
  }

  private fun at(r: Double, f: Double, h: Double, out: FloatArray): Boolean {
    val m = calib
    val z = camH - h
    val x = m[0] * f + m[1] * r + m[2] * z; val y = m[3] * f + m[4] * r + m[5] * z; val w = m[6] * f + m[7] * r + m[8] * z
    if (w < 0.5) return false
    var u = x / w - camCx; var v = y / w - camCy
    if (wide) { val d = Projection.pinholeToFisheye(u, v, camF); u = d[0]; v = d[1] }
    out[0] = ((camCx + u) * scale + tx).toFloat(); out[1] = ((camCy + v) * scale + ty).toFloat()
    depth = w
    return true
  }

  private fun camera(pr: Projection): Boolean {
    if (!pr.valid) return false
    val c = pr.cam()
    val k = doubleArrayOf(c.f, 0.0, c.cx, 0.0, c.f, c.cy, 0.0, 0.0, 1.0)
    val rpy = pr.rpy
    calib = Projection.mul(k, Projection.mul(Projection.VIEW_FROM_DEVICE, Projection.rotFromEuler(rpy[0], rpy[1], rpy[2])))
    camF = c.f; camCx = c.cx; camCy = c.cy; wide = pr.wide
    scale = pr.videoScale; tx = pr.videoTx; ty = pr.videoTy; camH = pr.pathOffsetZ
    return true
  }

  override fun onDraw(c: Canvas) {
    val drew = try { drawOn(c) } catch (e: Exception) {
      val now = System.currentTimeMillis()
      if (now - failAt > 10_000) { failAt = now; Log.w("StarView/Arrow", "camera", e) }
      false
    }
    // while something is drawn it follows the car every frame; otherwise a look 4x a second
    if (drew) postInvalidateOnAnimation() else postInvalidateDelayed(250)
  }

  private fun drawOn(c: Canvas): Boolean {
    val t = t ?: return false
    if (!t.started || !t.calValid) return false
    val now = System.currentTimeMillis()
    val inp = if (RoadArrowStyle.camera) RoadArrow.live(t) else null
    val arr = Arrival.of(t.navCard(now)?.first, Nav.INSTANCE.destination)
    if (inp == null && arr == null) return false
    RoadArrowStyle.load(context)
    val rtk = RtkPosition.INSTANCE
    if (!rtk.fast(now) || !(rtk.accM <= ACC_M)) return false
    val fit = LaneAnchor.fit ?: return false
    if (now - fit.atMs > 2000 || fit.hidden || fit.ribbon == null) return false
    if (!camera(overlay.proj)) return false
    val nowNs = System.nanoTime()
    val odo = Odometry.at(nowNs)
    val pose = sPose.pose(fit, odo, nowNs)
    var drew = false
    if (inp != null) {
      val lead = t.leadOne
      val lf = if (lead.status && lead.dRel > 0) lead.dRel + Traffic.BUMPER - 0.5 else Double.MAX_VALUE
      val sh = RoadArrow.shapeOn(inp, fit, pose, odo[3])?.let { inPicture(it, proj, width.toFloat(), height.toFloat(), lf, -lead.yRel.toDouble()) }
      if (sh != null) {
        painter.paint(c, sh.first, proj, ((nowNs - t0) / 0.5e9) % 1.0, sh.second, BOOST, height.toFloat())
        drew = true
      }
    }
    if (arr != null) {
      val spot = ArrivalTag.spot(arr, fit, pose, odo[3], true)
      if (spot != null && at(spot[0], spot[1], 0.0, p)) {
        val gx = p[0]; val gy = p[1]; val gz = depth
        if (at(spot[0], spot[1], ArrivalTag.STICK, p)) {
          val perM = camF * scale
          tag.paint(c, arr.addr.tag(), gx, gy, p[0], p[1], perM / gz, perM / depth, ArrivalTag.fade(arr))
          drew = true
        }
      }
    }
    return drew
  }
}
