package com.starpilot.starview

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Typeface
import kotlin.math.abs

/**
 * 2.90.14, from 500 ft before the destination ([Arrival]): the house number in a blue label on a thin stick, standing
 * at the curb on the destination's side as far along our road as the route says the destination is - on the generated
 * road (LaneRibbon) at its kerb, without it beside our path. The side is the arrival step's ("on the right"), else where
 * the destination's point lies against our road; not known - no label (the card still shows the address). The Scene and
 * the camera view draw it alike, each with its own projection.
 */
class ArrivalTag(private val dp: Float) {
  companion object {
    /** the stick's height (m) */
    const val STICK = 3.4

    /** where it stands (m right, m ahead of the comma camera); [onRoad]: the anchored map's road ([fit]'s ribbon) may
     *  place it at the kerb */
    fun spot(arr: Arrival.Now, fit: LaneFit.Fit?, pose: LaneFit.Pose?, travelled: Double, onRoad: Boolean): DoubleArray? {
      val ahead = arr.distM
      if (arr.addr.tag().isEmpty() || ahead < 2.0 || ahead > Arrival.SHOW_M + 5) return null
      var side = arr.side
      if (side == 0 && fit != null && pose != null && arr.lat.isFinite() && arr.lon.isFinite()) {
        val p = fit.built.xy(arr.lat, arr.lon)
        val rr = pose.right(p[0], p[1])
        if (abs(rr) > 3.0) side = if (rr > 0) 1 else -1
      }
      if (side == 0) return null
      var r = side * 7.0; var f = ahead
      val geo = if (onRoad) fit?.ribbon else null
      if (geo != null && fit != null && pose != null) {
        val adv = travelled - (fit.odo.getOrNull(3) ?: travelled)
        val e = geo.edges(adv + ahead)
        geo.place(adv + ahead, if (side > 0) e[4] + 1.2 else e[3] - 1.2)?.let { r = pose.right(it[0], it[1]); f = pose.fwd(it[0], it[1]) }
      }
      return doubleArrayOf(r, f)
    }

    /** it fades in over the first 80 ft */
    fun fade(arr: Arrival.Now): Double = ((Arrival.SHOW_M + 5 - arr.distM) / 25.0).coerceIn(0.0, 1.0)
  }

  private val p = Paint(Paint.ANTI_ALIAS_FLAG)
  private val rect = RectF()
  private val path = Path()
  private val font: Typeface by lazy { Typeface.create("sans-serif-black", Typeface.NORMAL) ?: Typeface.DEFAULT_BOLD }

  /** its foot on the road at ([gx], [gy]) and the stick's top at ([tx], [ty]); [perFoot] / [perTop]: how many pixels a
   *  metre spans there */
  fun paint(c: Canvas, label: String, gx: Float, gy: Float, tx: Float, ty: Float, perFoot: Double, perTop: Double, fade: Double) {
    fun argb(a: Double, r: Int, g: Int, b: Int) = Color.argb((a * fade).toInt().coerceIn(0, 255), r, g, b)
    // a soft spot of light where it stands, and the stick
    p.style = Paint.Style.FILL
    val rx = (perFoot * 0.5).toFloat().coerceIn(4 * dp, 40 * dp)
    p.color = argb(110.0, 40, 140, 255)
    rect.set(gx - rx, gy - rx * 0.32f, gx + rx, gy + rx * 0.32f); c.drawOval(rect, p)
    p.style = Paint.Style.STROKE; p.strokeWidth = (perFoot * 0.06).toFloat().coerceIn(1.5f * dp, 4 * dp)
    p.color = argb(170.0, 255, 255, 255)
    c.drawLine(gx, gy, tx, ty, p)
    // the label: as tall as 1 m there, but always readable and never huge
    val hPx = perTop.toFloat().coerceIn(30 * dp, 58 * dp)
    p.style = Paint.Style.FILL; p.typeface = font; p.textSize = hPx * 0.62f; p.textAlign = Paint.Align.CENTER
    val tw = p.measureText(label) + hPx * 0.7f
    val ptr = hPx * 0.22f
    rect.set(tx - tw / 2, ty - ptr - hPx, tx + tw / 2, ty - ptr)
    val rad = hPx * 0.28f
    p.color = argb(70.0, 60, 160, 255)                                     // a little glow around it
    c.drawRoundRect(rect.left - 5 * dp, rect.top - 5 * dp, rect.right + 5 * dp, rect.bottom + 5 * dp, rad + 5 * dp, rad + 5 * dp, p)
    p.color = argb(240.0, 30, 110, 245)
    c.drawRoundRect(rect, rad, rad, p)
    path.rewind(); path.moveTo(tx - ptr, ty - ptr - 1); path.lineTo(tx + ptr, ty - ptr - 1); path.lineTo(tx, ty); path.close()
    c.drawPath(path, p)
    p.style = Paint.Style.STROKE; p.strokeWidth = 2 * dp; p.color = argb(235.0, 255, 255, 255)
    c.drawRoundRect(rect, rad, rad, p)
    p.style = Paint.Style.FILL; p.color = argb(255.0, 255, 255, 255)
    val fm = p.fontMetrics
    c.drawText(label, tx, rect.centerY() - (fm.ascent + fm.descent) / 2, p)
  }
}
