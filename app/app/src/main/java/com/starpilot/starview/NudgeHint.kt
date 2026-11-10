package com.starpilot.starview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import org.json.JSONObject

/**
 * "Nudge for the exit" prompt (2.23.0). StarPilot's route lane positioning (desire_helper.py, toggle
 * NavLanePositioningAllowed) only takes an exit / fork when, close to the split, the driver pushes the wheel toward
 * it. Nothing told the driver when. This mirrors desire_helper's rules so the prompt lights up exactly when a nudge
 * will be accepted:
 *   maneuverType "off ramp" / "fork" with a left/right modifier; lane guidance (activeLaneDirection) on that side;
 *   distance <= interp(vEgo, [0,15,30] m/s, [25,90,160] m), x0.6 for an ambiguous split (several same-side lanes and
 *   <= 2 other lanes); not the "edge lane of a small split" case; blind spot clear, opposite blinker off; lane wide
 *   enough (laneWidth* >= LaneDetectionWidth).
 * States: SOON (within 2.5x the window: get ready), READY (nudge now), WAIT (in the window but blocked + why).
 */
object NudgeLogic {
  enum class State { NONE, SOON, READY, WAIT }
  class Result(val state: State, val right: Boolean = false, val distanceM: Double = 0.0, val windowM: Double = 0.0, val why: String = "")

  private val KEEP_V = doubleArrayOf(0.0, 15.0, 30.0)
  private val KEEP_D = doubleArrayOf(25.0, 90.0, 160.0)

  fun interp(x: Double, xs: DoubleArray, ys: DoubleArray): Double {
    if (x <= xs[0]) return ys[0]
    for (i in 1 until xs.size) if (x <= xs[i]) return ys[i - 1] + (ys[i] - ys[i - 1]) * (x - xs[i - 1]) / (xs[i] - xs[i - 1])
    return ys.last()
  }

  fun evaluate(nav: JSONObject?, vEgo: Double, leftBlinker: Boolean, rightBlinker: Boolean, leftBlindspot: Boolean,
               rightBlindspot: Boolean, laneWidthLeft: Double, laneWidthRight: Double, minLaneWidthM: Double): Result {
    if (nav == null || !nav.optBoolean("valid", false)) return Result(State.NONE)
    val type = nav.optString("maneuverType"); val mod = nav.optString("maneuverModifier")
    if (type != "off ramp" && type != "fork") return Result(State.NONE)
    if (mod !in setOf("slightLeft", "left", "sharpLeft", "slightRight", "right", "sharpRight")) return Result(State.NONE)
    val lane = nav.optString("activeLaneDirection")
    val right = when (lane) { "slightRight", "right" -> true; "slightLeft", "left" -> false; else -> return Result(State.NONE) }
    val dist = nav.optDouble("maneuverDistance", Double.NaN); if (dist.isNaN()) return Result(State.NONE)
    val same = nav.optInt("sameSideLaneCount", 0); val total = nav.optInt("laneCount", 0)
    val ambiguous = same > 1 && (total <= 0 || (total - same).coerceAtLeast(0) <= 2)
    val window = interp(vEgo, KEEP_V, KEEP_D) * (if (ambiguous) 0.6 else 1.0)
    if (ambiguous && nav.optBoolean("activeLaneAtRoadEdge", false) && nav.optBoolean("hasSharedSameSideLane", false)) return Result(State.NONE)
    if (dist > window * 2.5) return Result(State.NONE)
    if (dist > window) return Result(State.SOON, right, dist, window)
    val why = when {
      right && rightBlindspot -> "car in your right blind spot"
      !right && leftBlindspot -> "car in your left blind spot"
      right && leftBlinker -> "left blinker is on"
      !right && rightBlinker -> "right blinker is on"
      minLaneWidthM > 0 && (if (right) laneWidthRight else laneWidthLeft) < minLaneWidthM -> "no lane seen on that side yet"
      else -> ""
    }
    return Result(if (why.isEmpty()) State.READY else State.WAIT, right, dist, window, why)
  }
}

class NudgeHintView(ctx: Context, attrs: AttributeSet? = null) : View(ctx, attrs) {
  private val d = resources.displayMetrics.density
  private fun dp(v: Float) = v * d
  private val p = Paint(Paint.ANTI_ALIAS_FLAG)
  private val tp = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.WHITE; typeface = Typeface.DEFAULT_BOLD }
  var result: NudgeLogic.Result = NudgeLogic.Result(NudgeLogic.State.NONE)
  var metric = false

  private fun fmt(m: Double) = if (metric) "%d m".format(m.toInt()) else "%d ft".format((m * 3.28084 / 10).toInt() * 10)

  override fun onDraw(c: Canvas) {
    val r = result; if (r.state == NudgeLogic.State.NONE) return
    val w = width.toFloat(); val h = height.toFloat()
    val (bg, title) = when (r.state) {
      NudgeLogic.State.READY -> Color.rgb(28, 150, 70) to "Nudge ${if (r.right) "RIGHT" else "LEFT"} now"
      NudgeLogic.State.WAIT -> Color.rgb(200, 60, 50) to "Wait: ${r.why}"
      else -> Color.argb(225, 210, 140, 20) to "${if (r.right) "Exit right" else "Exit left"} in ${fmt(r.distanceM)}: get ready"
    }
    // pulse while READY so it catches the eye
    val alpha = if (r.state == NudgeLogic.State.READY) (200 + 55 * Math.sin(System.currentTimeMillis() / 150.0)).toInt() else 230
    p.color = bg; p.alpha = alpha.coerceIn(0, 255)
    c.drawRoundRect(RectF(0f, 0f, w, h), dp(18f), dp(18f), p)
    // arrow
    val ax = if (r.right) w - dp(46f) else dp(46f); val ay = h * 0.42f; val s = dp(18f)
    val path = Path().apply {
      if (r.right) { moveTo(ax - s, ay - s * 0.6f); lineTo(ax + s * 0.2f, ay - s * 0.6f); lineTo(ax + s * 0.2f, ay - s * 1.2f); lineTo(ax + s * 1.2f, ay); lineTo(ax + s * 0.2f, ay + s * 1.2f); lineTo(ax + s * 0.2f, ay + s * 0.6f); lineTo(ax - s, ay + s * 0.6f) }
      else { moveTo(ax + s, ay - s * 0.6f); lineTo(ax - s * 0.2f, ay - s * 0.6f); lineTo(ax - s * 0.2f, ay - s * 1.2f); lineTo(ax - s * 1.2f, ay); lineTo(ax - s * 0.2f, ay + s * 1.2f); lineTo(ax - s * 0.2f, ay + s * 0.6f); lineTo(ax + s, ay + s * 0.6f) }
      close()
    }
    p.color = Color.WHITE; p.alpha = 255; c.drawPath(path, p)
    // text
    tp.textAlign = Paint.Align.CENTER; tp.textSize = dp(21f)
    var t = title; while (t.length > 4 && tp.measureText(t) > w - dp(120f)) t = t.dropLast(2) + "…"
    c.drawText(t, w / 2, h * 0.47f, tp)
    tp.textSize = dp(13f); tp.typeface = Typeface.DEFAULT
    val sub = when (r.state) {
      NudgeLogic.State.READY -> "push the wheel toward the exit: StarPilot moves over (${fmt(r.distanceM)} left)"
      NudgeLogic.State.WAIT -> "the nudge won't be taken until this clears"
      else -> "the nudge window opens ${fmt(r.windowM)} before the split"
    }
    c.drawText(sub, w / 2, h * 0.72f, tp); tp.typeface = Typeface.DEFAULT_BOLD
    // countdown bar through the window
    if (r.state != NudgeLogic.State.SOON && r.windowM > 0) {
      val f = (r.distanceM / r.windowM).toFloat().coerceIn(0f, 1f)
      p.color = Color.argb(90, 255, 255, 255); c.drawRoundRect(RectF(dp(16f), h - dp(12f), w - dp(16f), h - dp(7f)), dp(3f), dp(3f), p)
      p.color = Color.WHITE; c.drawRoundRect(RectF(dp(16f), h - dp(12f), dp(16f) + (w - dp(32f)) * f, h - dp(7f)), dp(3f), dp(3f), p)
    }
    if (r.state == NudgeLogic.State.READY) postInvalidateDelayed(50)
  }
}
