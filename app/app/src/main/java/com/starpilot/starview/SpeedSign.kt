package com.starpilot.starview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import kotlin.math.roundToInt

/**
 * The posted speed limit as a US road sign (MUTCD R2-1, the one on New York roads), like Waze's. The whole sign flashes
 * red (white letters) twice a second while the car is past the warning amount for that limit (2.88.0: the owner's own
 * list, SpeedAlert; 9 mph / 15 km/h before). HUD widget "speedSign" (setting
 * "speed_sign"); hidden while the limit is unknown. The limit is the same one the speed-limit card shows (the comma's
 * speed limit controller: map, navigation, the car's own sign reading), the speed the same as the big speed number.
 */
class SpeedSignView(ctx: Context, attrs: AttributeSet? = null) : View(ctx, attrs) {
  lateinit var t: Telemetry
  private val p = Paint(Paint.ANTI_ALIAS_FLAG)
  private val tp = Paint(Paint.ANTI_ALIAS_FLAG).apply {
    textAlign = Paint.Align.CENTER
    typeface = Typeface.create("sans-serif-condensed", Typeface.BOLD)
  }
  private val rect = RectF()
  private var drawnKey = ""

  companion object {
    val RED = Color.rgb(214, 18, 32)
    const val SAMPLE = 25                 // what the screen editor shows

    // 2.86.4: the last limit read stays while the reading goes stale / unknown / absent (the comma's own screen does
    // the same: unified_speed_presentation.displayed_limit_mps keeps last_received_speed_limit_mps). 2.86.3 showed
    // only a "valid" reading, so the sign came for a moment and went again. Forgotten when the car turns off.
    @Volatile private var lastMps = Float.NaN

    /** 2.86.5: with no combined reading, a source's own (the ones the speed limit controller uses first) */
    private val ORDER = listOf("dashboard", "map", "mapbox", "navigation", "vision")
    private fun ok(v: Float) = v.isFinite() && v > 0f

    /** The posted limit in m/s (NaN when unknown): the speed limit controller's reading (also in display-only mode),
     *  else any source's own valid reading (2.86.5: like Waze, the sign shows a limit whenever one is known, even
     *  from a source the controller doesn't use), else the last one this drive, else the accepted one. */
    fun postedMps(t: Telemetry): Float {
      if (!t.started) {
        lastMps = Float.NaN
        return Float.NaN
      }
      val live = t.slcLive(System.nanoTime())
      var fresh = Float.NaN
      if (live != null) {
        if (live.kind == "valid" && ok(live.speedMps)) {
          fresh = live.speedMps
        } else {
          val rs = live.readings.filter { it.kind == "valid" && ok(it.speedMps) }
          val r = rs.firstOrNull { it.enabled } ?: rs.minByOrNull { ORDER.indexOf(it.source).let { i -> if (i < 0) 99 else i } }
          if (r != null) fresh = r.speedMps
        }
      } else {
        for (v in floatArrayOf(t.slcSpeedLimit, t.slcMapSpeedLimit, t.slcMapbox)) if (ok(v)) { fresh = v; break }
      }
      if (ok(fresh)) {
        lastMps = fresh
        return fresh
      }
      if (ok(lastMps)) return lastMps
      if (live != null && live.hasAccepted && ok(live.acceptedMps)) return live.acceptedMps
      return Float.NaN
    }

    /** The speed the big number shows, m/s (2.86.5: SpeedShown, the true speed learned from GPS). */
    fun speedMps(t: Telemetry): Float =
      SpeedShown.mps(t, if (!t.tBool("use_wheel_speed", false) && t.vEgoCluster > 0f) t.vEgoCluster else t.vEgo)
  }

  /** (number shown, flashing now) - null while there is nothing to show */
  private fun state(): Pair<Int, Boolean>? {
    if (!::t.isInitialized) return null
    if (NavOverlay.editing) return Pair(SAMPLE, false)
    val lim = postedMps(t)
    if (lim.isNaN()) return null
    val k = if (t.isMetric) 3.6f else 2.2369363f
    val shown = (lim * k).roundToInt()
    // 2.88.0: the speed as shown, past the owner's amount for this limit (SpeedAlert): 30 + 5 flashes at 36 and up
    val over = (speedMps(t) * k).roundToInt() > shown + SpeedAlert.allowed(shown, t.isMetric)
    return Pair(shown, over)
  }

  /** true while there is a limit to show (the screen tick shows / hides the widget with it) */
  fun hasLimit(): Boolean = state() != null

  /** From the screen tick: redraws only when the number or the flash changes. */
  fun tick() {
    val s = state()
    val on = s != null && s.second && (System.nanoTime() / 250_000_000L) % 2L == 0L
    val key = if (s == null) "" else "${s.first}/${s.second}/$on"
    if (key != drawnKey) invalidate()
  }

  override fun onDraw(c: Canvas) {
    val s = state() ?: run { drawnKey = ""; return }
    val w = width.toFloat(); val h = height.toFloat()
    if (w <= 0f || h <= 0f) return
    val on = s.second && (System.nanoTime() / 250_000_000L) % 2L == 0L
    drawnKey = "${s.first}/${s.second}/$on"
    drawSign(c, w, h, s.first, on)
  }

  /** MUTCD R2-1, 24 x 30 in: white, a black border inset from the edge, SPEED / LIMIT, the number. */
  fun drawSign(c: Canvas, w: Float, h: Float, number: Int, red: Boolean) {
    val u = w / 24f                                   // one inch of the real sign
    val bg = if (red) RED else Color.WHITE
    val ink = if (red) Color.WHITE else Color.BLACK
    // the sign's plate, with a faint dark edge so it reads on a white road too
    p.style = Paint.Style.FILL; p.color = bg
    rect.set(0.5f * u, 0.5f * u, w - 0.5f * u, h - 0.5f * u)
    c.drawRoundRect(rect, 1.5f * u, 1.5f * u, p)
    p.style = Paint.Style.STROKE; p.strokeWidth = 0.5f * u; p.color = Color.argb(150, 0, 0, 0)
    c.drawRoundRect(rect, 1.5f * u, 1.5f * u, p)
    // the border
    p.strokeWidth = 0.75f * u
    p.color = ink
    val inset = 0.5f * u + 0.625f * u + p.strokeWidth / 2
    rect.set(inset, inset, w - inset, h - inset)
    c.drawRoundRect(rect, 1.1f * u, 1.1f * u, p)
    // SPEED / LIMIT (4 in letters) and the number (10 in)
    tp.color = ink
    tp.textSize = 4.6f * u
    tp.letterSpacing = 0.04f
    c.drawText("SPEED", w / 2, h * 0.255f, tp)
    c.drawText("LIMIT", w / 2, h * 0.43f, tp)
    tp.letterSpacing = 0f
    tp.textSize = (if (number >= 100) 10.5f else 13.5f) * u
    c.drawText(number.toString(), w / 2, h * 0.86f, tp)
  }
}
