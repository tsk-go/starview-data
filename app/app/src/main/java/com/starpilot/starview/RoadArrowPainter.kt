package com.starpilot.starview

import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.CornerPathEffect
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.graphics.Shader
import kotlin.math.abs
import kotlin.math.hypot
import kotlin.math.max
import kotlin.math.min

/**
 * 2.90.14: the road arrow as agreed with the owner (road-arrow-design.md): a deep, rounded ribbon with a little
 * thickness - light at the van, deep at the arrowhead, its edges shaded darker, a thin light line on its top edge - white
 * raised arrows on it, a tight contact shadow, a faint glow on the road, the start fading in, and light running along it
 * twice a second. Where it goes is [RoadArrow]'s; the view hands its own projection (the Scene's, the camera's, the
 * screen editor's little picture) as [Proj]. Colours and the rest from [RoadArrowStyle].
 */
class RoadArrowPainter(private val dp: Float) {
  /** (m right, m ahead of the comma camera, m up) -> screen px in out[0], out[1]; false when it can't be seen */
  fun interface Proj { fun at(r: Double, f: Double, h: Double, out: FloatArray): Boolean }

  companion object {
    const val H = 0.16           // the ribbon's thickness (m)
    const val W = 1.9            // its width
    const val HEAD_LEN = 2.6
    const val HEAD_W = 3.4

    private fun alpha(color: Int, a: Int) = (color and 0x00FFFFFF) or (a.coerceIn(0, 255) shl 24)
  }

  private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
  private val top = Path()
  private val base = Path()
  private val tmp = Path()
  private val bounds = RectF()
  private val p = FloatArray(2)
  private val round = CornerPathEffect(7 * dp)
  private val roundChev = CornerPathEffect(2.5f * dp)
  private val glowBlur = BlurMaskFilter(16 * dp, BlurMaskFilter.Blur.NORMAL)
  private val shadowBlur = BlurMaskFilter(4.5f * dp, BlurMaskFilter.Blur.NORMAL)
  private val edgeBlur = BlurMaskFilter(5 * dp, BlurMaskFilter.Blur.NORMAL)
  private val flareBlur = BlurMaskFilter(10 * dp, BlurMaskFilter.Blur.NORMAL)
  private val chevBlur = BlurMaskFilter(2.5f * dp, BlurMaskFilter.Blur.NORMAL)
  private val dstIn = PorterDuffXfermode(PorterDuff.Mode.DST_IN)

  /** how much its size along the road (toward the horizon) grows far off, at most (1: not at all) */
  private var boost = 1.0
  /** the stretch along the road at [f] m ahead: the camera, low over the road, squeezes a turn's far part into a sliver
   *  (the mock-up the owner approved drew it wider there); from 25 ft, up to [boost] times */
  private fun stretch(f: Double) = if (boost <= 1.0) 1.0 else (f / 8.0).coerceIn(1.0, boost)

  /** [phase]: the running light's place on its trip, 0..1; [head] false: cut short (a car ahead covers the rest);
   *  [boost]: see [stretch]; [bottom]: the picture's bottom edge (px), where the start fades in from when it lies below */
  fun paint(c: Canvas, sh: RoadArrow.Shape, proj: Proj, phase: Double, head: Boolean = true, boost: Double = 1.0,
            bottom: Float = Float.MAX_VALUE) {
    this.boost = boost
    if (!outline(base, sh, proj, W, 0.0, head = head) || !outline(top, sh, proj, W, H, head = head)) return
    base.computeBounds(bounds, true)
    val pad = 40 * dp
    val l = bounds.left - pad; val tp = bounds.top - pad; val r = bounds.right + pad; val b = bounds.bottom + pad
    val near = RoadArrowStyle.near(); val far = RoadArrowStyle.far()
    val layer = c.saveLayerAlpha(l, tp, r, b, (255 * RoadArrowStyle.opacity).toInt())
    try {
      paint.reset(); paint.isAntiAlias = true; paint.style = Paint.Style.FILL
      // a faint glow on the road around it, and a tight contact shadow under it
      paint.maskFilter = glowBlur
      paint.color = alpha(RoadArrowStyle.mix(near, far, 0.5f), 45)
      c.drawPath(base, paint)
      paint.maskFilter = shadowBlur
      paint.color = Color.argb(150, 0, 0, 0)
      c.save(); c.translate(2 * dp, 3 * dp); c.drawPath(base, paint); c.restore()
      paint.maskFilter = null
      paint.pathEffect = round
      // its sides: the outline on the road, darker, under the top
      paint.color = RoadArrowStyle.mix(far, Color.BLACK, 0.45f)
      c.drawPath(base, paint)
      // the top: light at the van, deep at the arrowhead
      proj.at(sh.r[0], sh.f[0], H, p); val x0 = p[0]; val y0 = p[1]
      proj.at(sh.r[sh.size - 1], sh.f[sh.size - 1], H, p); val x1 = p[0]; val y1 = p[1]
      paint.shader = LinearGradient(x0, y0, x1, y1, near, far, Shader.TileMode.CLAMP)
      c.drawPath(top, paint)
      paint.shader = null
      c.save()
      c.clipPath(top)
      // darker toward its edges (depth)
      paint.style = Paint.Style.STROKE; paint.strokeWidth = 8 * dp
      paint.maskFilter = edgeBlur
      paint.color = Color.argb(85, 6, 28, 95)
      c.drawPath(top, paint)
      paint.style = Paint.Style.FILL
      // the running light: a band of light from the van to the arrowhead, twice a second
      var flare = 0.0
      if (RoadArrowStyle.running) {
        val sp = phase * (sh.length + 4) - 2
        if (outline(tmp, sh, proj, W * 1.05, H, sp - 1.6, sp + 1.6, false)) {
          paint.maskFilter = edgeBlur
          paint.color = Color.argb(150, 205, 240, 255)
          c.drawPath(tmp, paint)
        }
        if (head) flare = 1 - min(1.0, abs(sh.length - HEAD_LEN - sp) / 4.0)
      }
      paint.maskFilter = null
      c.restore()
      // a thin light line on the top's edge
      paint.style = Paint.Style.STROKE; paint.strokeWidth = 1.3f * dp
      paint.color = alpha(RoadArrowStyle.mix(near, Color.WHITE, 0.45f), 185)
      c.drawPath(top, paint)
      paint.style = Paint.Style.FILL
      // the arrowhead lights up as the light arrives
      if (flare > 0 && outline(tmp, sh, proj, W, H, sh.length - HEAD_LEN - 1.7)) {
        paint.maskFilter = flareBlur
        paint.color = Color.argb((150 * flare).toInt(), 110, 200, 255)
        c.drawPath(tmp, paint)
        paint.maskFilter = null
      }
      raisedArrows(c, sh, proj, near, if (head) HEAD_LEN else 0.0)
      // the start fades in from the van
      proj.at(sh.r[0], sh.f[0], 0.0, p)
      val yStart = min(p[1], bottom)
      val fadeTo = yStart - (yStart - bounds.top) * 0.22f
      paint.pathEffect = null
      paint.xfermode = dstIn
      paint.shader = LinearGradient(0f, yStart, 0f, fadeTo, 0, Color.BLACK, Shader.TileMode.CLAMP)
      c.drawRect(l, tp, r, b, paint)
      paint.xfermode = null; paint.shader = null
    } finally {
      c.restoreToCount(layer)
    }
  }

  /** the ribbon's outline at height [h] between [from] and [to] m along it (with the arrowhead when it reaches it) */
  private fun outline(out: Path, sh: RoadArrow.Shape, proj: Proj, w: Double, h: Double, from: Double = -1.0,
                      to: Double = Double.MAX_VALUE, head: Boolean = true): Boolean {
    out.rewind()
    val n = sh.size
    val bodyEnd = if (head) sh.length - HEAD_LEN else sh.length
    var a = -1; var z = -1
    for (i in 0 until n) if (sh.s[i] >= from && sh.s[i] <= min(to, bodyEnd)) { if (a < 0) a = i; z = i }
    if (a < 0 || z - a < 1) return false
    val m = z - a + 1
    val lx = FloatArray(m); val ly = FloatArray(m); val rx = FloatArray(m); val ry = FloatArray(m)
    var dr = 0.0; var df = 1.0
    for (k in 0 until m) {
      val i = a + k
      val j0 = max(0, i - 1); val j1 = min(n - 1, i + 1)
      dr = sh.r[j1] - sh.r[j0]; df = sh.f[j1] - sh.f[j0]
      val l = max(1e-6, hypot(dr, df)); dr /= l; df /= l
      val kf = stretch(sh.f[i])
      if (!proj.at(sh.r[i] - df * w / 2, sh.f[i] + dr * w / 2 * kf, h, p)) return false
      lx[k] = p[0]; ly[k] = p[1]
      if (!proj.at(sh.r[i] + df * w / 2, sh.f[i] - dr * w / 2 * kf, h, p)) return false
      rx[k] = p[0]; ry[k] = p[1]
    }
    out.moveTo(lx[0], ly[0])
    for (k in 1 until m) out.lineTo(lx[k], ly[k])
    if (head && to >= bodyEnd) {
      val i = z
      val hw = HEAD_W * w / W
      val kf = stretch(sh.f[i])
      if (proj.at(sh.r[i] - df * hw / 2, sh.f[i] + dr * hw / 2 * kf, h, p)) out.lineTo(p[0], p[1])
      if (proj.at(sh.r[n - 1], sh.f[i] + (sh.f[n - 1] - sh.f[i]) * kf, h, p)) out.lineTo(p[0], p[1])
      if (proj.at(sh.r[i] + df * hw / 2, sh.f[i] - dr * hw / 2 * kf, h, p)) out.lineTo(p[0], p[1])
    }
    for (k in m - 1 downTo 0) out.lineTo(rx[k], ry[k])
    out.close()
    return true
  }

  /** white chevrons standing a little off the ribbon's top, pale sides, each with a small shadow on the ribbon */
  private fun raisedArrows(c: Canvas, sh: RoadArrow.Shape, proj: Proj, near: Int, headLen: Double) {
    val len = 0.8; val wid = W * 0.56; val th = 0.32; val up = 0.07
    val chev = doubleArrayOf(-len / 2, wid / 2, -len / 2 + th, wid / 2, len / 2, 0.0, -len / 2 + th, -wid / 2, -len / 2, -wid / 2, len / 2 - th, 0.0)
    paint.pathEffect = roundChev
    val side = RoadArrowStyle.mix(near, Color.WHITE, 0.5f)
    var s = 1.8
    var i = 0
    while (s < sh.length - headLen - 0.6) {
      while (i < sh.size - 2 && sh.s[i + 1] < s) i++
      val j1 = min(sh.size - 1, i + 1)
      var dr = sh.r[j1] - sh.r[i]; var df = sh.f[j1] - sh.f[i]
      val ln = max(1e-6, hypot(dr, df)); dr /= ln; df /= ln
      fun chevAt(out: Path, h: Double): Boolean {
        out.rewind()
        var k = 0
        while (k < chev.size) {
          val a = chev[k]; val bb = chev[k + 1]
          // a along the ribbon, bb to its left
          if (!proj.at(sh.r[i] + dr * a - df * bb, sh.f[i] + (df * a + dr * bb) * stretch(sh.f[i]), h, p)) return false
          if (k == 0) out.moveTo(p[0], p[1]) else out.lineTo(p[0], p[1])
          k += 2
        }
        out.close()
        return true
      }
      if (chevAt(tmp, H)) {
        paint.maskFilter = chevBlur
        paint.color = Color.argb(140, 6, 28, 95)
        c.save(); c.translate(1.5f * dp, 2.5f * dp); c.drawPath(tmp, paint); c.restore()
        paint.maskFilter = null
        paint.color = side
        c.drawPath(tmp, paint)
        if (chevAt(tmp, H + up)) { paint.color = Color.WHITE; c.drawPath(tmp, paint) }
      }
      s += 1.7 * stretch(sh.f[i])      // as far apart as they are long
    }
    paint.pathEffect = null
  }
}
