package com.starpilot.starview

import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin

/**
 * Port of openpilot's AugmentedRoadView._calc_frame_matrix + ModelRenderer._map_to_screen.
 * All 3x3 matrices are row-major DoubleArray(9).
 */
class Projection {
  companion object {
    // camera intrinsics by sensor / frame size (common/transformations/camera.py)
    data class Cam(val w: Int, val h: Int, val f: Double) { val cx get() = w / 2.0; val cy get() = h / 2.0 }
    val AR_OX_FCAM = Cam(1928, 1208, 2648.0); val AR_OX_ECAM = Cam(1928, 1208, 567.0)
    val OS_FCAM = Cam(1344, 760, 1141.5); val OS_ECAM = Cam(1344, 760, 425.25)
    const val HEIGHT_INIT = 1.22
    const val CLIP_MARGIN = 500.0

    // view_frame_from_device_frame = transpose([[0,0,1],[1,0,0],[0,1,0]])
    val VIEW_FROM_DEVICE = doubleArrayOf(0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0)

    fun mul(a: DoubleArray, b: DoubleArray): DoubleArray {
      val r = DoubleArray(9)
      for (i in 0..2) for (j in 0..2) r[i * 3 + j] = a[i * 3] * b[j] + a[i * 3 + 1] * b[3 + j] + a[i * 3 + 2] * b[6 + j]
      return r
    }
    fun mulv(a: DoubleArray, x: Double, y: Double, z: Double): DoubleArray =
      doubleArrayOf(a[0] * x + a[1] * y + a[2] * z, a[3] * x + a[4] * y + a[5] * z, a[6] * x + a[7] * y + a[8] * z)

    /** Intrinsics for a decoded frame: sensor by aspect ratio (AR0231/OX03C10 1928x1208 = 1.60, OS04C10 1344x760 = 1.77), scaled to the frame. */
    fun camFor(frameW: Int, frameH: Int, wide: Boolean): Cam {
      val os = frameW.toDouble() / frameH > 1.68
      val c = if (os) (if (wide) OS_ECAM else OS_FCAM) else (if (wide) AR_OX_ECAM else AR_OX_FCAM)
      return Cam(frameW, frameH, c.f * frameW / c.w)
    }

    /**
     * Ground -> pixel homography, row-major. A ground point (X fwd, Y right) is (X, Y, +h) in the calibrated frame
     * (z down, origin at the camera): pixel_h = K · view_from_device · device_from_calib · (X, Y, h) = H · (X, Y, 1).
     */
    fun groundHomographyRows(rpy: FloatArray, height: Float, frameW: Int, frameH: Int, wide: Boolean): DoubleArray {
      val c = camFor(frameW, frameH, wide)
      val k = doubleArrayOf(c.f, 0.0, c.cx, 0.0, c.f, c.cy, 0.0, 0.0, 1.0)
      val ct = mul(k, mul(VIEW_FROM_DEVICE, rotFromEuler(rpy[0].toDouble(), rpy[1].toDouble(), rpy[2].toDouble())))
      return doubleArrayOf(ct[0], ct[1], height * ct[2], ct[3], ct[4], height * ct[5], ct[6], ct[7], height * ct[8])
    }

    fun inverse3(m: DoubleArray): DoubleArray? {
      val a = m[0]; val b = m[1]; val c = m[2]; val d = m[3]; val e = m[4]; val f = m[5]; val g = m[6]; val h = m[7]; val i = m[8]
      val det = a * (e * i - f * h) - b * (d * i - f * g) + c * (d * h - e * g)
      if (abs(det) < 1e-12) return null
      val r = 1 / det
      return doubleArrayOf((e * i - f * h) * r, (c * h - b * i) * r, (b * f - c * e) * r, (f * g - d * i) * r, (a * i - c * g) * r, (c * d - a * f) * r, (d * h - e * g) * r, (b * g - a * h) * r, (a * e - b * d) * r)
    }

    /**
     * The wide camera is a fisheye: a pixel r from the centre sees the ray at angle r/f (equidistant), not atan(r/f).
     * Fitted on a long straight wall in a drive recording: with this the wall's edge is straight to under a pixel,
     * without it it bows by 12 px - which is why a straight row of parked cars came out as an arc.
     * Distorted pixel offset -> the offset a plain (pinhole) camera would have put it at. null past ~80 degrees.
     */
    fun fisheyeToPinhole(du: Double, dv: Double, f: Double): DoubleArray? {
      val r = kotlin.math.hypot(du, dv); if (r < 1e-9) return doubleArrayOf(du, dv)
      val th = r / f; if (th > 1.40) return null
      val s = f * kotlin.math.tan(th) / r
      return doubleArrayOf(du * s, dv * s)
    }
    /** The other way: pinhole offset -> where the fisheye puts it. */
    fun pinholeToFisheye(du: Double, dv: Double, f: Double): DoubleArray {
      val r = kotlin.math.hypot(du, dv); if (r < 1e-9) return doubleArrayOf(du, dv)
      val s = f * kotlin.math.atan(r / f) / r
      return doubleArrayOf(du * s, dv * s)
    }

    /** Pixel -> ground for one camera; used by the vision tracker. */
    class GroundInverse(val f: Float, val cx: Float, val cy: Float, private val hInv: DoubleArray?, val fisheye: Boolean = false) {
      /** Wide camera: the pixel a pinhole camera would have seen (u, v) at. */
      fun undistort(u: Float, v: Float): FloatArray? {
        if (!fisheye) return floatArrayOf(u, v)
        val p = fisheyeToPinhole((u - cx).toDouble(), (v - cy).toDouble(), f.toDouble()) ?: return null
        return floatArrayOf((cx + p[0]).toFloat(), (cy + p[1]).toFloat())
      }
      /** Angle (rad) between the optical axis and the ray through pixel (u, v). */
      fun offAxis(u: Float, v: Float): Float {
        val r = kotlin.math.hypot(u - cx, v - cy)
        return if (fisheye) r / f else kotlin.math.atan(r / f)
      }
      /** (X fwd, Y right) of the ground point under pixel (u, v), or null if the ray doesn't hit the road ahead. */
      fun ground(u0: Float, v0: Float): FloatArray? {
        val m = hInv ?: return null
        val uv = undistort(u0, v0) ?: return null
        val u = uv[0]; val v = uv[1]
        val x = m[0] * u + m[1] * v + m[2]; val y = m[3] * u + m[4] * v + m[5]; val w = m[6] * u + m[7] * v + m[8]
        if (w < 1e-6) return null
        val gx = x / w; if (gx <= 0) return null
        return floatArrayOf(gx.toFloat(), (y / w).toFloat())
      }
    }
    fun groundInverse(rpy: FloatArray, height: Float, frameW: Int, frameH: Int, wide: Boolean): GroundInverse {
      val c = camFor(frameW, frameH, wide)
      return GroundInverse(c.f.toFloat(), c.cx.toFloat(), c.cy.toFloat(), inverse3(groundHomographyRows(rpy, height, frameW, frameH, wide)), wide)
    }

    /** openpilot euler2rot: Rz(yaw) * Ry(pitch) * Rx(roll) */
    fun rotFromEuler(roll: Double, pitch: Double, yaw: Double): DoubleArray {
      val cr = cos(roll); val sr = sin(roll); val cp = cos(pitch); val sp = sin(pitch); val cy = cos(yaw); val sy = sin(yaw)
      val rx = doubleArrayOf(1.0, 0.0, 0.0, 0.0, cr, -sr, 0.0, sr, cr)
      val ry = doubleArrayOf(cp, 0.0, sp, 0.0, 1.0, 0.0, -sp, 0.0, cp)
      val rz = doubleArrayOf(cy, -sy, 0.0, sy, cy, 0.0, 0.0, 0.0, 1.0)
      return mul(rz, mul(ry, rx))
    }
  }

  // inputs
  var frameW = 0; var frameH = 0            // decoded video frame size
  var wide = false
  var viewW = 0.0; var viewH = 0.0           // content rect (screen) size in px
  var fullW = 0.0; var fullH = 0.0           // 2.29.1: the whole display in landscape (long, short side, px); 0 = unknown
  var rpy = doubleArrayOf(0.0, 0.0, 0.0)
  var pathOffsetZ = HEIGHT_INIT

  // outputs
  var zoom = 1.0; var xOffset = 0.0; var yOffset = 0.0
  var videoScale = 1.0; var videoTx = 0.0; var videoTy = 0.0    // place the video: screen = frame_px * scale + t
  private var full = DoubleArray(9)           // screen <- car space
  private var calib = DoubleArray(9)          // frame px (pinhole) <- car space
  private var camNow = OS_FCAM
  var valid = false

  fun cam(): Cam = if (frameH > 0) camFor(frameW, frameH, wide)
    else if (frameW == 1344) (if (wide) OS_ECAM else OS_FCAM) else (if (wide) AR_OX_ECAM else AR_OX_FCAM)

  fun update() {
    if (frameW == 0 || viewW == 0.0) { valid = false; return }
    val c = cam()
    val k = doubleArrayOf(c.f, 0.0, c.cx, 0.0, c.f, c.cy, 0.0, 0.0, 1.0)
    val deviceFromCalib = rotFromEuler(rpy[0], rpy[1], rpy[2])
    val viewFromCalib = mul(VIEW_FROM_DEVICE, deviceFromCalib)
    val calibTransform = mul(k, viewFromCalib)
    val kep = mulv(calibTransform, 1000.0, 0.0, 0.0)

    val w = viewW; val h = viewH
    // the same field of view whatever size the video comes in (zoom scaled by the camera's native width)
    val nativeW = if ((frameH > 0 && frameW.toDouble() / frameH > 1.68) || frameW == 1344) 1344.0 else 1928.0
    var z = (if (wide) 2.0 else 1.1) * nativeW / c.w
    if (fullW > 0.0 && w < fullW * 0.98) {
      // 2.29.1: a window narrower than the full screen (split screen, portrait) shows the same view as full screen,
      // smaller - zoomed by the width ratio, centred, with dark space above / below where the window is taller.
      // Before it zoomed in to fill the window (zoom 3.9 instead of 2.4 in a half-split): much less of the road.
      val fh = max(fullH, 1.0)
      val zFull = max(z, max(fullW / (2 * c.cx), fh / (2 * c.cy)))
      z = max(zFull * (w / fullW), w / (2 * c.cx))
    } else z = max(z, max(w / (2 * c.cx), h / (2 * c.cy)))
    val margin = 5.0
    val maxX = max(0.0, c.cx * z - w / 2 - margin)
    val maxY = max(0.0, c.cy * z - h / 2 - margin)
    var xo = 0.0; var yo = 0.0
    if (abs(kep[2]) > 1e-6) {
      xo = ((kep[0] / kep[2] - c.cx) * z).coerceIn(-maxX, maxX)
      yo = ((kep[1] / kep[2] - c.cy) * z).coerceIn(-maxY, maxY)
    }
    zoom = z; xOffset = xo; yOffset = yo
    videoScale = z
    videoTx = (w / 2 - xo) - c.cx * z
    videoTy = (h / 2 - yo) - c.cy * z
    val videoTransform = doubleArrayOf(z, 0.0, videoTx, 0.0, z, videoTy, 0.0, 0.0, 1.0)
    full = mul(videoTransform, calibTransform)
    calib = calibTransform; camNow = c
    valid = true
  }

  /** car-space (x fwd, y, z) -> screen px, or null if behind/outside the clip region. */
  fun map(x: Double, y: Double, z: Double, out: FloatArray): Boolean {
    val sx: Double; val sy: Double
    if (wide) {
      // fisheye: bend the straight-camera position the way the lens does, so lines sit on the picture's curved road
      val p = mulv(calib, x, y, z)
      if (p[2] < 1e-6) return false
      val c = camNow
      val d = pinholeToFisheye(p[0] / p[2] - c.cx, p[1] / p[2] - c.cy, c.f)
      sx = (c.cx + d[0]) * videoScale + videoTx; sy = (c.cy + d[1]) * videoScale + videoTy
    } else {
      val p = mulv(full, x, y, z)
      if (abs(p[2]) < 1e-6) return false
      sx = p[0] / p[2]; sy = p[1] / p[2]
    }
    if (sx < -CLIP_MARGIN || sx > viewW + CLIP_MARGIN || sy < -CLIP_MARGIN || sy > viewH + CLIP_MARGIN) return false
    out[0] = sx.toFloat(); out[1] = sy.toFloat()
    return true
  }

  /**
   * Ribbon polygon around a 3D line: returns packed floats [L0x,L0y,...,Lk, Rk,...,R0] or null.
   * yOff = half width (m), zOff added to z. maxIdx = last index to use (inclusive).
   * allowInvert=false drops points whose screen y stops decreasing (hill guard), like the reference.
   */
  fun ribbon(x: FloatArray, y: FloatArray, z: FloatArray, yOff: Double, zOff: Double, maxIdx: Int, allowInvert: Boolean): FloatArray? {
    val n = min(maxIdx + 1, min(x.size, min(y.size, z.size)))
    if (n < 2) return null
    val lx = FloatArray(n); val ly = FloatArray(n); val rx = FloatArray(n); val ry = FloatArray(n)
    val tmp = FloatArray(2)
    var k = 0
    var minY = Float.MAX_VALUE
    for (i in 0 until n) {
      if (!map(x[i].toDouble(), y[i] - yOff, z[i] + zOff, tmp)) continue
      val lX = tmp[0]; val lY = tmp[1]
      if (!map(x[i].toDouble(), y[i] + yOff, z[i] + zOff, tmp)) continue
      if (!allowInvert) {
        if (lY > minY) continue
        minY = lY
      }
      lx[k] = lX; ly[k] = lY; rx[k] = tmp[0]; ry[k] = tmp[1]; k++
    }
    if (k < 2) return null
    val out = FloatArray(k * 4)
    for (i in 0 until k) { out[i * 2] = lx[i]; out[i * 2 + 1] = ly[i] }
    for (i in 0 until k) { val j = k - 1 - i; out[(k + i) * 2] = rx[j]; out[(k + i) * 2 + 1] = ry[j] }
    return out
  }
}
