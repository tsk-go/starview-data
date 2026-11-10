package com.starpilot.starview

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 * What the painted lines really look like (2.13.4). openpilot's model says WHERE the 4 lane lines are, not what kind of
 * paint they are. This looks at the road camera picture along each line, 6..40 m ahead: a brightness profile across the
 * line (±0.48 m) every 0.5 m. A narrow bright stripe = paint; stripe colour = white or yellow; two stripes close
 * together = double line; paint on most of the length = solid, on and off = dashed, nothing = no line.
 * Answers are voted over time, so a car hiding a line for a moment doesn't flip it.
 */
class LaneMarks {
  companion object {
    const val UNKNOWN = 0; const val NONE = 1; const val DASHED = 2; const val SOLID = 3
    private val NAMES = arrayOf("?", "none", "dash", "solid")
  }
  class Mark(val kind: Int, val yellow: Boolean, val double: Boolean)

  @Volatile var marks: Array<Mark> = Array(4) { Mark(UNKNOWN, false, false) }; private set
  @Volatile var debug = "waiting"; private set
  @Volatile var lastNs = 0L; private set
  private val score = Array(4) { FloatArray(4) }            // per line: votes for UNKNOWN(unused)/NONE/DASHED/SOLID
  private val yel = FloatArray(4); private val dbl = FloatArray(4)
  private val prof = IntArray(25); private val profU = IntArray(25); private val profV = IntArray(25)
  private val tmp = IntArray(25)

  fun wants(nowNs: Long) = nowNs - lastNs > 200_000_000L

  /** Road-camera frame (not the wide one) + the model's lane lines + calibration. leadX: lead car distance (m) or 0. */
  fun analyze(y: ByteArray, u: ByteArray, v: ByteArray, yStride: Int, uvStride: Int, uvPix: Int, fw: Int, fh: Int,
              rpy: FloatArray, m: ModelSnapshot?, leadX: Float, nowNs: Long) {
    lastNs = nowNs
    if (m == null || m.laneX.size < 4) return
    val c = Projection.camFor(fw, fh, false)
    val k = doubleArrayOf(c.f, 0.0, c.cx, 0.0, c.f, c.cy, 0.0, 0.0, 1.0)
    val ct = Projection.mul(k, Projection.mul(Projection.VIEW_FROM_DEVICE, Projection.rotFromEuler(rpy[0].toDouble(), rpy[1].toDouble(), rpy[2].toDouble())))
    fun proj(x: Double, yy: Double, z: Double, out: DoubleArray): Boolean {
      val p = Projection.mulv(ct, x, yy, z); if (p[2] < 0.5) return false
      out[0] = p[0] / p[2]; out[1] = p[1] / p[2]; return true
    }
    val a = DoubleArray(2); val b = DoubleArray(2)
    val sb = StringBuilder()
    val maxX = if (leadX > 3f) min(40.0, leadX - 3.0) else 40.0
    for (li in 0 until 4) {
      val prob = m.laneProb.getOrElse(li) { 0f }
      val xs = m.laneX[li]; val ys = m.laneY[li]; val zs = m.laneZ[li]
      var rows = 0; var present = 0; var trans = 0; var prev = -1; var nYel = 0; var nDbl = 0
      if (prob > 0.12f && xs.size > 4) {
        var x = 6.0
        var j = 1
        while (x <= maxX) {
          while (j < xs.size - 1 && xs[j] < x) j++
          if (xs[j] < x) break
          val f = ((x - xs[j - 1]) / max(0.01f, xs[j] - xs[j - 1])).coerceIn(0.0, 1.0)
          val ly = ys[j - 1] + (ys[j] - ys[j - 1]) * f; val lz = zs[j - 1] + (zs[j] - zs[j - 1]) * f
          if (proj(x, ly, lz, a) && proj(x, ly + 0.5, lz, b)) {
            val ppm = (b[0] - a[0]) * 2.0; val dvpm = (b[1] - a[1]) * 2.0      // pixels per metre across the line
            if (abs(ppm) * 0.1 >= 1.5) {
              var inside = true
              for (s in 0 until 25) {
                val off = (s - 12) * 0.04
                val px = (a[0] + off * ppm).toInt(); val py = (a[1] + off * dvpm).toInt()
                if (px < 2 || py < 2 || px >= fw - 2 || py >= fh - 2) { inside = false; break }
                prof[s] = y[py * yStride + px].toInt() and 0xff
                val uvi = (py shr 1) * uvStride + (px shr 1) * uvPix
                profU[s] = u[uvi].toInt() and 0xff; profV[s] = v[uvi].toInt() and 0xff
              }
              if (inside) {
                rows++
                // background: median of the outer samples (>= 0.32 m from the line)
                var nb = 0; for (s in 0 until 25) if (abs(s - 12) >= 8) tmp[nb++] = prof[s]
                java.util.Arrays.sort(tmp, 0, nb); val bg = tmp[nb / 2]
                val thr = max(18, bg * 12 / 100)
                // stripes within ±0.28 m: runs of bright samples no wider than 0.24 m
                var runs = 0; var s = 5; var bright = 0; var yelSum = 0
                while (s <= 19) {
                  if (prof[s] - bg > thr) {
                    var e = s; while (e + 1 <= 19 && prof[e + 1] - bg > thr) e++
                    if (e - s + 1 <= 6) { runs++; for (q in s..e) { bright++; yelSum += (128 - profU[q]) + (profV[q] - 128) } }
                    s = e + 1
                  } else s++
                }
                val here = runs > 0
                if (here) {
                  present++
                  var bgYel = 0; var nbY = 0; for (q in 0 until 25) if (abs(q - 12) >= 8) { bgYel += (128 - profU[q]) + (profV[q] - 128); nbY++ }
                  if (yelSum / bright - bgYel / max(1, nbY) > 22) nYel++
                  if (runs >= 2) nDbl++
                }
                val st = if (here) 1 else 0
                if (prev >= 0 && st != prev) trans++
                prev = st
              }
            }
          }
          x += 0.5
        }
      }
      // this frame's answer, then vote
      val sc = score[li]
      if (rows >= 8) {
        val cover = present.toFloat() / rows
        val kind = when { cover < 0.12f -> NONE; cover > 0.75f -> SOLID; trans >= 2 -> DASHED; cover > 0.5f -> SOLID; else -> DASHED }
        for (q in 1..3) sc[q] += 0.1f * ((if (q == kind) 1f else 0f) - sc[q])
        if (present >= 3) {
          yel[li] += 0.1f * ((if (nYel * 2 > present) 1f else 0f) - yel[li])
          dbl[li] += 0.1f * ((if (nDbl * 5 > present * 2) 1f else 0f) - dbl[li])
        }
      } else if (prob <= 0.12f) {
        for (q in 1..3) sc[q] *= 0.95f
      }
      var best = UNKNOWN; var bv = 0.45f
      for (q in 1..3) if (sc[q] > bv) { bv = sc[q]; best = q }
      // hysteresis: keep what is shown unless the new answer clearly wins
      val shown = marks[li]
      if (shown.kind != UNKNOWN && best != shown.kind && sc[shown.kind] > 0.3f && bv - sc[shown.kind] < 0.25f) best = shown.kind
      val isYel = if (shown.yellow) yel[li] > 0.35f else yel[li] > 0.65f
      val isDbl = if (shown.double) dbl[li] > 0.35f else dbl[li] > 0.65f
      marks = marks.copyOf().also { it[li] = Mark(best, isYel, isDbl) }
      val mk = marks[li]
      sb.append(arrayOf("LL", "L", "R", "RR")[li]).append(':').append(NAMES[mk.kind])
      if (mk.kind >= DASHED) { if (mk.double) sb.append(" dbl"); sb.append(if (mk.yellow) " yel" else " wht") }
      sb.append(" (").append(rows).append(")  ")
    }
    debug = sb.toString().trim()
  }
}
