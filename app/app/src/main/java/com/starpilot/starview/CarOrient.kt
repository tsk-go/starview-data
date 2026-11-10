package com.starpilot.starview

import android.content.Context
import android.util.Log
import org.tensorflow.lite.Interpreter
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min

/**
 * Which way a car is facing, from its picture: a small network (MobileNetV3-small, trained on the ~26k labelled cars of
 * the KITTI object set) looks at one car box and returns how likely each of 16 viewing angles is.
 *
 * The angle is KITTI's "observation angle" alpha - the car's heading relative to our line of sight to it:
 *   -90 deg = we see its back, +90 = its front, 0 = its right side (nose to the right of the picture), +-180 = nose left.
 * So heading (calibrated frame) = bearing + alpha + 90 deg.
 *
 * Runs on the CPU (2 threads, ~3-6 ms a car); the detector asks for a few cars per frame and adds the answers up per track.
 */
class CarOrient(private val ctx: Context) {
  companion object {
    private const val TAG = "StarView/Orient"
    const val ASSET = "det/car_orient_96.lite"
    const val R = 96; const val NB = 16
    private val MEAN = floatArrayOf(0.485f, 0.456f, 0.406f); private val STD = floatArrayOf(0.229f, 0.224f, 0.225f)
    /** Bin centre angles (rad): -pi + (k + 0.5) * 2pi / NB. */
    val CENTRES = FloatArray(NB) { (-Math.PI + (it + 0.5) * 2 * Math.PI / NB).toFloat() }
  }

  private var interp: Interpreter? = null
  @Volatile var failed = ""; private set
  @Volatile var ms = 0.0; private set
  private val inBuf: ByteBuffer = ByteBuffer.allocateDirect(R * R * 3 * 4).order(ByteOrder.nativeOrder())
  private val px = FloatArray(R * R * 3)
  private val out = Array(1) { FloatArray(NB) }
  private val pad = FloatArray(3) { (114f / 255f - MEAN[it]) / STD[it] }

  private fun ensure(): Interpreter? {
    interp?.let { return it }
    if (failed.isNotEmpty()) return null
    return try {
      val bytes = ctx.assets.open(ASSET).use { it.readBytes() }
      val buf = ByteBuffer.allocateDirect(bytes.size).order(ByteOrder.nativeOrder()); buf.put(bytes); buf.rewind()
      Interpreter(buf, Interpreter.Options().setNumThreads(2)).also { interp = it }
    } catch (e: Throwable) { Log.w(TAG, "load: $e"); failed = e.message ?: e.toString(); null }
  }

  fun close() { try { interp?.close() } catch (_: Throwable) {}; interp = null }

  /**
   * Viewing-angle probabilities (16 bins) for the car in box (u0..u1, v0..v1) of a YUV frame, or null.
   * [aspK] > 1 stretches the crop sideways (the wide camera's fisheye squeezes cars near the picture's sides).
   * The crop matches training: box + 5 % each side, letterboxed into 96x96 with grey, ImageNet-normalised RGB.
   */
  fun classify(y: ByteArray, u: ByteArray, v: ByteArray, yStride: Int, uvStride: Int, uvPixStride: Int, fw: Int, fh: Int,
               u0: Float, v0: Float, u1: Float, v1: Float, aspK: Float = 1f): FloatArray? {
    val it = ensure() ?: return null
    val t0 = System.nanoTime()
    val pw = (u1 - u0) * 0.05f; val ph = (v1 - v0) * 0.05f
    val cx0 = max(0f, u0 - pw); val cy0 = max(0f, v0 - ph); val cx1 = min(fw - 1f, u1 + pw); val cy1 = min(fh - 1f, v1 + ph)
    val cw = (cx1 - cx0) * aspK; val ch = cy1 - cy0
    if (cw < 8f || ch < 8f) return null
    val s = R / max(cw, ch)
    val ow = cw * s; val oh = ch * s
    val ox = (R - ow) / 2f; val oy = (R - oh) / 2f
    val sx = 1f / (s * aspK); val sy = 1f / s
    var k = 0
    for (j in 0 until R) {
      val inY = j >= oy && j < oy + oh
      val fy = cy0 + (j + 0.5f - oy) * sy - 0.5f
      val y0i = floor(fy).toInt().coerceIn(0, fh - 1); val y1i = min(fh - 1, y0i + 1); val wy = (fy - floor(fy)).coerceIn(0f, 1f)
      for (i in 0 until R) {
        if (!inY || i < ox || i >= ox + ow) { px[k] = pad[0]; px[k + 1] = pad[1]; px[k + 2] = pad[2]; k += 3; continue }
        val fx = cx0 + (i + 0.5f - ox) * sx - 0.5f
        val x0i = floor(fx).toInt().coerceIn(0, fw - 1); val x1i = min(fw - 1, x0i + 1); val wx = (fx - floor(fx)).coerceIn(0f, 1f)
        // luma bilinear (small cars get enlarged), chroma nearest (it is half resolution anyway)
        val a = (y[y0i * yStride + x0i].toInt() and 0xff) * (1 - wx) + (y[y0i * yStride + x1i].toInt() and 0xff) * wx
        val b = (y[y1i * yStride + x0i].toInt() and 0xff) * (1 - wx) + (y[y1i * yStride + x1i].toInt() and 0xff) * wx
        val yy = (a * (1 - wy) + b * wy) - 16f
        val uvI = (((fy + 0.5f).toInt().coerceIn(0, fh - 1)) shr 1) * uvStride + (((fx + 0.5f).toInt().coerceIn(0, fw - 1)) shr 1) * uvPixStride
        val uu = (u[uvI].toInt() and 0xff) - 128f; val vv = (v[uvI].toInt() and 0xff) - 128f
        val rr = (1.164f * yy + 1.596f * vv).coerceIn(0f, 255f)
        val gg = (1.164f * yy - 0.391f * uu - 0.813f * vv).coerceIn(0f, 255f)
        val bb = (1.164f * yy + 2.018f * uu).coerceIn(0f, 255f)
        px[k] = (rr / 255f - MEAN[0]) / STD[0]; px[k + 1] = (gg / 255f - MEAN[1]) / STD[1]; px[k + 2] = (bb / 255f - MEAN[2]) / STD[2]
        k += 3
      }
    }
    inBuf.rewind(); inBuf.asFloatBuffer().put(px); inBuf.rewind()
    return try {
      it.run(inBuf, out)
      val dt = (System.nanoTime() - t0) / 1e6; ms = if (ms == 0.0) dt else ms * 0.9 + dt * 0.1
      out[0].copyOf()
    } catch (e: Throwable) { Log.w(TAG, "run: $e"); failed = e.message ?: e.toString(); close(); null }
  }
}
