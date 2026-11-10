package com.starpilot.starview

import android.content.Context
import android.net.Uri
import android.util.Log
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.gpu.GpuDelegate
import java.io.File
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.math.abs
import kotlin.math.atan
import kotlin.math.hypot
import kotlin.math.max
import kotlin.math.min

/**
 * Scene layout: what the ground around us is - road, sidewalk, grass, buildings - from the wide camera, drawn on the
 * Scene's ground (Tesla style).
 *
 * A few times a second a wide-camera frame is straightened (the fisheye bent back to a level, plain-lens view 640x224,
 * horizon fixed at row 144, calibration applied) and labelled pixel by pixel by a street-scene network (SegFormer-B0,
 * Cityscapes classes). Every ground cell of a 40 x 40 m map in front of us (0.5 m cells) then looks up its pixel in
 * that straightened view - a level camera at height h sees ground point (X ahead, Y right) at
 * column cx + f*Y/X, row horizon + f*h/X - and takes its label.
 *
 * The network (4.5 MB) is too big for the app itself: it is imported once from a file (Settings > Tablet > Camera).
 */
class LayoutSeg(private val ctx: Context) {
  companion object {
    private const val TAG = "StarView/Layout"
    const val FILE = "layout_segformer_b0.lite"
    const val OW = 640; const val OH = 224; const val FP = 200f; const val HR = 144
    const val MW = 160; const val MH = 56; const val NC = 19
    const val DARK = 55                     // mean luma below this: night / garage
    const val GX = 80; const val GY = 80; const val CELL = 0.5f          // x 0..40 m ahead, y -20..20 m
    // Cityscapes: 0 road 1 sidewalk 2 building 3 wall 4 fence 5 pole 6 light 7 sign 8 vegetation 9 terrain 10 sky
    //             11 person 12 rider 13 car 14 truck 15 bus 16 train 17 motorcycle 18 bicycle
    /** Map groups drawn on the ground: 0 road, 1 sidewalk / curb, 2 grass / terrain, 3 building / wall / fence, 255 nothing. */
    val GROUP = IntArray(NC) { when (it) { 0 -> 0; 1 -> 1; 8, 9 -> 2; 2, 3, 4 -> 3; else -> 255 } }
    private val MEAN = floatArrayOf(0.485f, 0.456f, 0.406f); private val STD = floatArrayOf(0.229f, 0.224f, 0.225f)
  }

  /** Newest ground map: [GX * GY] groups, row = metres ahead / CELL. atNs = when its camera frame was taken. */
  class Grid(val g: ByteArray, val atNs: Long)
  @Volatile var grid: Grid? = null; private set
  @Volatile var enabled = true
  @Volatile var status = "off"; private set
  @Volatile var ms = 0.0; private set
  @Volatile var accel = "-"; private set
  /** Why the GPU refused the model (shown on the diagnostics panel), empty = no error. */
  @Volatile var gpuError = ""; private set

  fun modelFile() = File(ctx.filesDir, FILE)
  val hasModel get() = modelFile().length() > 1_000_000

  /** Copy a picked file into the app (Settings button). Returns an error text or null. */
  fun importModel(uri: Uri): String? = try {
    val tmp = File(ctx.filesDir, "$FILE.part")
    ctx.contentResolver.openInputStream(uri)!!.use { i -> tmp.outputStream().use { o -> i.copyTo(o) } }
    val head = tmp.inputStream().use { val b = ByteArray(8); it.read(b); b }
    if (tmp.length() < 1_000_000 || String(head, 4, 4) != "TFL3") { tmp.delete(); "That file isn't the layout model (.lite)" }
    else { close(); tmp.renameTo(modelFile()); status = "model imported"; null }
  } catch (e: Exception) { "Import failed: ${e.message}" }

  // ---- input side (detector thread) ----
  private val px = FloatArray(OW * OH * 3)
  private var lut = IntArray(0); private var lutKey = ""
  @Volatile private var busy = false
  @Volatile var dark = false; private set
  private var lastFeedNs = 0L
  private var feedH = 1.22f

  fun wantsFrame(nowNs: Long) = enabled && hasModel && !busy && status.startsWith("error").not() && nowNs - lastFeedNs > 250_000_000L

  /** Straighten one wide-camera YUV frame into the network's input (called with the frame still valid). */
  fun feed(y: ByteArray, u: ByteArray, v: ByteArray, yStride: Int, uvStride: Int, uvPixStride: Int, fw: Int, fh: Int,
           rpy: FloatArray, height: Float, frameNs: Long) {
    if (busy) return
    lastFeedNs = System.nanoTime()
    val key = "$fw:$fh:${"%.3f".format(rpy[0])}:${"%.3f".format(rpy[1])}:${"%.3f".format(rpy[2])}"
    if (key != lutKey) { buildLut(fw, fh, rpy); lutKey = key }
    val l = lut; var k = 0
    // too dark to judge (night, garage): both training sets are daylight only - pause instead of drawing guesses
    var lsum = 0L; var ln = 0
    for (i in 0 until OW * OH step 97) { val p = l[i]; if (p >= 0) { lsum += y[(p / fw) * yStride + p % fw].toInt() and 0xff; ln++ } }
    if (ln > 0 && lsum / ln < DARK) { dark = true; grid = null; status = "paused: too dark"; return }
    dark = false
    for (i in 0 until OW * OH) {
      val p = l[i]
      if (p < 0) { px[k] = (0.45f - MEAN[0]) / STD[0]; px[k + 1] = (0.45f - MEAN[1]) / STD[1]; px[k + 2] = (0.45f - MEAN[2]) / STD[2]; k += 3; continue }
      val sx = p % fw; val sy = p / fw
      val yy = (y[sy * yStride + sx].toInt() and 0xff) - 16f
      val uvI = (sy shr 1) * uvStride + (sx shr 1) * uvPixStride
      val uu = (u[uvI].toInt() and 0xff) - 128f; val vv = (v[uvI].toInt() and 0xff) - 128f
      val r = (1.164f * yy + 1.596f * vv).coerceIn(0f, 255f); val g = (1.164f * yy - 0.391f * uu - 0.813f * vv).coerceIn(0f, 255f)
      val b = (1.164f * yy + 2.018f * uu).coerceIn(0f, 255f)
      px[k] = (r / 255f - MEAN[0]) / STD[0]; px[k + 1] = (g / 255f - MEAN[1]) / STD[1]; px[k + 2] = (b / 255f - MEAN[2]) / STD[2]; k += 3
    }
    feedH = height; pendingNs = frameNs
    busy = true
    synchronized(lock) { lock.notifyAll() }
  }

  /** Straightened pixel -> wide-frame pixel: level virtual camera (calibrated frame) -> device -> fisheye. */
  private fun buildLut(fw: Int, fh: Int, rpy: FloatArray) {
    val c = Projection.camFor(fw, fh, true)
    val rot = Projection.mul(Projection.VIEW_FROM_DEVICE, Projection.rotFromEuler(rpy[0].toDouble(), rpy[1].toDouble(), rpy[2].toDouble()))
    val out = IntArray(OW * OH)
    for (j in 0 until OH) for (i in 0 until OW) {
      val d = Projection.mulv(rot, 1.0, ((i + 0.5 - OW / 2.0) / FP), ((j + 0.5 - HR) / FP))   // calibrated ray (fwd, right, down)
      if (d[2] <= 1e-6) { out[j * OW + i] = -1; continue }
      val xn = d[0] / d[2]; val yn = d[1] / d[2]; val rn = hypot(xn, yn)
      val s = if (rn < 1e-9) c.f else c.f * atan(rn) / rn                                   // equidistant fisheye
      val sx = (c.cx + xn * s).toInt(); val sy = (c.cy + yn * s).toInt()
      out[j * OW + i] = if (sx in 0 until fw && sy in 0 until fh) sy * fw + sx else -1
    }
    lut = out
  }

  // ---- network side (own thread) ----
  private val lock = Object()
  @Volatile private var running = false
  private var worker: Thread? = null
  private var pendingNs = 0L
  private var interp: Interpreter? = null; private var gpu: GpuDelegate? = null
  private val inBuf: ByteBuffer = ByteBuffer.allocateDirect(OW * OH * 3 * 4).order(ByteOrder.nativeOrder())
  private val outBuf: ByteBuffer = ByteBuffer.allocateDirect(MW * MH * NC * 4).order(ByteOrder.nativeOrder())
  private val cls = ByteArray(MW * MH)
  private val lg = FloatArray(MW * MH * NC)
  /** Class with the highest bilinearly blended score at model-pixel position (fy, fx). */
  private fun interpClass(fy: Float, fx: Float): Int {
    val y0 = fy.toInt().coerceIn(0, MH - 1); val x0 = fx.toInt().coerceIn(0, MW - 1)
    val y1 = min(MH - 1, y0 + 1); val x1 = min(MW - 1, x0 + 1)
    val wy = (fy - y0).coerceIn(0f, 1f); val wx = (fx - x0).coerceIn(0f, 1f)
    val a = (y0 * MW + x0) * NC; val b = (y0 * MW + x1) * NC; val c = (y1 * MW + x0) * NC; val d = (y1 * MW + x1) * NC
    val w00 = (1 - wy) * (1 - wx); val w01 = (1 - wy) * wx; val w10 = wy * (1 - wx); val w11 = wy * wx
    var best = 0; var bv = -Float.MAX_VALUE
    for (k in 0 until NC) {
      val v = lg[a + k] * w00 + lg[b + k] * w01 + lg[c + k] * w10 + lg[d + k] * w11
      if (v > bv) { bv = v; best = k }
    }
    return best
  }
  private val blockRow = IntArray(MW); private val blockCls = IntArray(MW)

  /** Starts the worker once; after that only [enabled] turns the work on and off (switching views never waits). */
  fun start() {
    if (running) return; running = true
    worker = Thread({ loop() }, "starview-layout").also { it.priority = Thread.NORM_PRIORITY - 2; it.isDaemon = true; it.start() }
  }
  /** Pause: the worker frees the network itself once it's done with the current frame - freeing it from here while
   *  the GPU was still busy (the first run compiles for many seconds) could crash the app on Scene -> Camera. */
  fun stop() { enabled = false; grid = null; synchronized(lock) { lock.notifyAll() } }
  /** App closing. */
  fun shutdown() { enabled = false; running = false; grid = null; synchronized(lock) { lock.notifyAll() } }
  private fun close() { try { interp?.close() } catch (_: Throwable) {}; interp = null; try { gpu?.close() } catch (_: Throwable) {}; gpu = null }

  private var loadedMtime = 0L
  private fun ensure(): Interpreter? {
    interp?.let { if (modelFile().lastModified() == loadedMtime) return it; close() }   // a newer model was imported
    loadedMtime = modelFile().lastModified()
    if (!hasModel) { status = "no model imported"; return null }
    status = "loading…"
    val bytes = modelFile().readBytes()
    val buf = ByteBuffer.allocateDirect(bytes.size).order(ByteOrder.nativeOrder()).apply { put(bytes); rewind() }
    // 1. GPU with its compiled-kernel cache (fast starts), 2. GPU without the cache (09-28: the cache file "couldn't be
    // opened" and that alone aborted the GPU), 3. CPU. The reason for any fallback is shown on the diagnostics line.
    val cache = File(ctx.cacheDir, "gpu_layout").apply { mkdirs() }
    val errors = ArrayList<String>()
    for (withCache in listOf(true, false)) {
      var d: GpuDelegate? = null
      try {
        val o = GpuDelegate.Options().setPrecisionLossAllowed(true).setQuantizedModelsAllowed(true)
          .setInferencePreference(GpuDelegate.Options.INFERENCE_PREFERENCE_SUSTAINED_SPEED)
        if (withCache) o.setSerializationParams(cache.absolutePath, "layout_b0_v3_${modelFile().length()}")
        d = GpuDelegate(o)
        val it = Interpreter(buf, Interpreter.Options().addDelegate(d))
        gpu = d; accel = if (withCache) "gpu" else "gpu (no cache)"; interp = it
        gpuError = if (errors.isEmpty()) "" else "cache: " + errors[0].take(100)
        return it
      } catch (e: Throwable) {
        Log.w(TAG, "GPU (cache=$withCache): $e"); errors += (e.message ?: e.toString()).replace("\n", " ")
        try { d?.close() } catch (_: Throwable) {}
        if (withCache) cache.listFiles()?.forEach { f -> if (f.name.startsWith("layout_")) f.delete() }   // stale / half-written cache
      }
    }
    gpuError = errors.last().take(160)
    return Interpreter(buf, Interpreter.Options().setNumThreads(3)).also { accel = "cpu"; interp = it }
  }

  private fun loop() {
    try { loopInner() } finally { close() }
  }
  private fun loopInner() {
    while (running) {
      if (!enabled) { if (interp != null) { close(); status = "off" }; busy = false }
      synchronized(lock) { if (running && (!busy || !enabled)) lock.wait(500) }
      if (!running || !busy || !enabled) continue
      try {
        val it = ensure() ?: run { busy = false; Thread.sleep(2000); null } ?: continue
        val t0 = System.nanoTime()
        inBuf.rewind(); inBuf.asFloatBuffer().put(px); inBuf.rewind(); outBuf.rewind()
        val atNs = pendingNs; val h = feedH
        busy = false                                  // input copied: the next frame may be prepared meanwhile
        it.run(inBuf, outBuf)
        // label per model pixel (argmax over 19 classes)
        val fb = outBuf.also { b -> b.rewind() }.asFloatBuffer(); fb.get(lg)
        for (p in 0 until MW * MH) {
          var best = 0; var bv = lg[p * NC]
          for (c in 1 until NC) { val x = lg[p * NC + c]; if (x > bv) { bv = x; best = c } }
          cls[p] = best.toByte()
        }
        // ground map. Open ground (road / sidewalk / grass) counts only where nothing stands in front of it; things
        // standing up (walls, fences, buildings, hedges / trees) are drawn as a 2.5 m strip where they meet the ground -
        // looking straight through them would paint a wall's face as a huge "building" area behind it.
        for (col in 0 until MW) {
          var br = -1; var bc = -1
          var row = MH - 3                                    // the last rows can show our own hood / dash
          while (row >= 0) { val c = cls[row * MW + col].toInt(); if (c != 0 && c != 1 && c != 9) { br = row; bc = c; break }; row-- }
          blockRow[col] = br; blockCls[col] = bc
        }
        val g = ByteArray(GX * GY) { 255.toByte() }
        for (r in 0 until GX) {
          val X = (r + 0.5f) * CELL
          val vv = (HR + FP * h / X) / 4f
          if (vv >= MH || vv < 0) continue
          val row = vv.toInt()
          for (c in 0 until GY) {
            val Y = -GY * CELL / 2 + (c + 0.5f) * CELL
            val uu = (OW / 2f + FP * Y / X) / 4f
            if (uu < 0 || uu >= MW) continue
            val col = uu.toInt()
            if (row > blockRow[col]) {
              // open ground: smooth class boundaries by blending the 4 nearest model pixels' scores (far away one model
              // pixel covers metres of ground, so taking it whole draws blocky staircases); a single frame is trusted to 30 m
              if (X > 30f) continue
              var gr = GROUP[interpClass(vv - 0.5f, uu - 0.5f)]
              if (gr == 255) gr = GROUP[cls[row * MW + col].toInt()]
              g[r * GY + c] = gr.toByte(); continue
            }
            // cars etc. hide what is behind them (unknown; the map memory fills it from other moments). Not "road under
            // the car": a car's lowest pixel is often not where it touches the ground -> long diagonal road streaks (2.12.6)
            val grp = when (blockCls[col]) { 2, 3, 4 -> 3; 8 -> 2; else -> -1 }
            if (grp < 0) continue
            val baseV = (blockRow[col] + 1) * 4f - HR                 // pixels below the horizon where it meets the ground
            val xb = if (baseV > 0f) FP * h / baseV else 999f
            if (X >= xb && X <= xb + 2.5f) g[r * GY + c] = grp.toByte()
          }
        }
        grid = Grid(g, atNs)
        val dt = (System.nanoTime() - t0) / 1e6; ms = if (ms == 0.0) dt else ms * 0.8 + dt * 0.2
        status = "running"
      } catch (e: Throwable) {
        Log.e(TAG, "layout: $e"); status = "error: ${e.message}"; busy = false; close()
        try { Thread.sleep(3000) } catch (_: InterruptedException) {}
        status = "retrying"
      }
    }
  }
}
