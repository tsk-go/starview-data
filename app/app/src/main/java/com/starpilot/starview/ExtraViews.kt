package com.starpilot.starview

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

private fun View.dpf(v: Float) = v * resources.displayMetrics.density

/** AetherGauge (aethergauge.py): curve speed / forced stop / red light / lead state in a small card. */
class AetherGaugeView(ctx: Context, attrs: AttributeSet? = null) : View(ctx, attrs) {
  lateinit var t: Telemetry
  private val p = Paint(Paint.ANTI_ALIAS_FLAG)
  private val tp = Paint(Paint.ANTI_ALIAS_FLAG).apply { textAlign = Paint.Align.CENTER; typeface = Typeface.DEFAULT_BOLD; color = Color.WHITE }
  private val path = Path()
  private var lastActiveNs = 0L
  var active = false; private set

  private fun conv(ms: Float) = ms * (if (t.isMetric) 3.6f else 2.23694f)
  private fun dist(m: Float) = if (t.isMetric) "${m.roundToInt()} m" else "${(m * 3.28084f).roundToInt()} ft"

  data class Src(val value: String, val unit: String, val label: String, val color: Int, val curve: Boolean)

  private fun source(): Src? {
    val cruiseSet = t.cruiseEnabled && t.planVCruise > 0f
    return when {
      t.forcingStop && !t.redLight -> Src(if (t.forcingStopLength > 0f) dist(t.forcingStopLength) else "", "", "STOP", Color.rgb(255, 30, 60), false)
      t.experimentalMode && t.redLight -> Src(dist(t.forcingStopLength), "", "RED LIGHT", Color.rgb(255, 60, 60), false)
      (t.cscControlling || t.cscTraining) && cruiseSet -> {
        val col = if (t.cscTraining) Color.rgb(112, 192, 216) else OverlayView.Companion.cscColor(t.roadCurvature)
        Src(conv(t.cscSpeed).roundToInt().toString(), if (t.isMetric) "km/h" else "mph", if (t.cscTraining) "LEARNING" else "CURVE", col, true)
      }
      t.experimentalMode && t.trackingLead && t.leadOne.status -> {
        val stopped = t.leadOne.vLead < 0.5f
        Src(if (stopped) "STOPPED" else "SLOW", "", "LEAD", if (stopped) Color.rgb(255, 60, 60) else Color.rgb(255, 191, 0), false)
      }
      else -> null
    }
  }

  override fun onDraw(c: Canvas) {
    if (!::t.isInitialized || !t.started) { active = false; return }
    val src = source()
    val now = System.nanoTime()
    if (src != null) lastActiveNs = now
    active = src != null || (now - lastActiveNs) < 500_000_000L
    if (src == null) return
    val w = width.toFloat(); val h = height.toFloat(); val rr = dpf(14f)
    p.style = Paint.Style.FILL; p.color = Color.argb(166, 0, 0, 0); c.drawRoundRect(RectF(0f, 0f, w, h), rr, rr, p)
    p.style = Paint.Style.STROKE; p.strokeWidth = dpf(2f); p.color = OverlayView.Companion.withAlpha(src.color, 200); c.drawRoundRect(RectF(1f, 1f, w - 1, h - 1), rr, rr, p)
    // mini road: trapezoid with chevrons, curving if it's a curve source
    val roadTop = dpf(10f); val roadBot = h * 0.52f
    val wb = w * 0.46f; val wt = w * 0.22f; val cx = w / 2
    val bend = if (src.curve) (t.roadCurvature.coerceIn(-0.02f, 0.02f) / 0.02f) * w * 0.18f else 0f
    path.rewind()
    path.moveTo(cx - wb / 2, roadBot); path.lineTo(cx + wb / 2, roadBot)
    path.quadTo(cx + wt / 2 + bend * 0.5f, (roadTop + roadBot) / 2, cx + wt / 2 - bend, roadTop)
    path.lineTo(cx - wt / 2 - bend, roadTop)
    path.quadTo(cx - wt / 2 + bend * 0.5f, (roadTop + roadBot) / 2, cx - wb / 2, roadBot); path.close()
    p.style = Paint.Style.FILL; p.color = OverlayView.Companion.withAlpha(src.color, 90); c.drawPath(path, p)
    p.style = Paint.Style.STROKE; p.strokeWidth = dpf(2f); p.color = src.color; c.drawPath(path, p)
    for (i in 0 until 4) {
      val f = 0.15f + i * 0.22f
      val y = roadBot - (roadBot - roadTop) * f
      val hw = (wb + (wt - wb) * f) * 0.28f
      val x = cx - bend * f * f
      path.rewind(); path.moveTo(x - hw, y + dpf(3f)); path.lineTo(x, y - dpf(3f)); path.lineTo(x + hw, y + dpf(3f))
      p.strokeWidth = dpf(1.5f); p.color = OverlayView.Companion.withAlpha(Color.WHITE, 160); c.drawPath(path, p)
    }
    // text
    tp.color = Color.WHITE
    tp.textSize = if (src.value.length > 4) h * 0.15f else h * 0.26f
    c.drawText(src.value, cx, h * 0.76f, tp)
    tp.textSize = h * 0.10f; tp.color = OverlayView.Companion.withAlpha(Color.WHITE, 180)
    c.drawText(if (src.unit.isNotEmpty()) src.unit else src.label, cx, h * 0.90f, tp)
    if (src.unit.isNotEmpty()) { tp.textSize = h * 0.09f; tp.color = OverlayView.Companion.withAlpha(src.color, 230); c.drawText(src.label, cx, roadBot + h * 0.08f, tp) }
  }
}

/** Weather badge (weather_icon.py): OpenWeather id -> simple glyph, day/night background. */
class WeatherView(ctx: Context, attrs: AttributeSet? = null) : View(ctx, attrs) {
  lateinit var t: Telemetry
  private val p = Paint(Paint.ANTI_ALIAS_FLAG)

  override fun onDraw(c: Canvas) {
    if (!::t.isInitialized || !t.started || t.weatherId <= 0) return
    val w = width.toFloat(); val h = height.toFloat(); val rr = dpf(12f)
    p.style = Paint.Style.FILL; p.color = if (t.weatherDay) Color.rgb(135, 206, 235) else Color.rgb(25, 25, 112)
    c.drawRoundRect(RectF(0f, 0f, w, h), rr, rr, p)
    p.style = Paint.Style.STROKE; p.strokeWidth = dpf(2f); p.color = Color.BLACK; c.drawRoundRect(RectF(1f, 1f, w - 1, h - 1), rr, rr, p)
    val id = t.weatherId; val cx = w / 2; val cy = h / 2; val r = w * 0.2f
    p.style = Paint.Style.FILL
    fun cloud(y: Float, col: Int) {
      p.color = col
      c.drawCircle(cx - r * 0.7f, y, r * 0.75f, p); c.drawCircle(cx + r * 0.1f, y - r * 0.4f, r * 0.9f, p); c.drawCircle(cx + r * 0.9f, y, r * 0.7f, p)
      c.drawRoundRect(RectF(cx - r * 1.4f, y, cx + r * 1.5f, y + r * 0.7f), r * 0.3f, r * 0.3f, p)
    }
    fun sunOrMoon(x: Float, y: Float, rad: Float) {
      if (t.weatherDay) { p.color = Color.rgb(255, 200, 0); c.drawCircle(x, y, rad, p) }
      else { p.color = Color.rgb(240, 240, 200); c.drawCircle(x, y, rad, p); p.color = if (t.weatherDay) Color.rgb(135, 206, 235) else Color.rgb(25, 25, 112); c.drawCircle(x + rad * 0.45f, y - rad * 0.3f, rad * 0.8f, p) }
    }
    when {
      id in 200..299 -> { cloud(cy - r * 0.3f, Color.rgb(90, 90, 110)); p.color = Color.rgb(255, 220, 0); val pt = Path(); pt.moveTo(cx + r * 0.2f, cy + r * 0.2f); pt.lineTo(cx - r * 0.3f, cy + r * 1.0f); pt.lineTo(cx + r * 0.05f, cy + r * 1.0f); pt.lineTo(cx - r * 0.3f, cy + r * 1.7f); pt.lineTo(cx + r * 0.5f, cy + r * 0.8f); pt.lineTo(cx + r * 0.15f, cy + r * 0.8f); pt.close(); c.drawPath(pt, p) }
      id in 300..599 -> { cloud(cy - r * 0.4f, Color.rgb(200, 200, 210)); p.color = Color.rgb(80, 140, 255); p.strokeWidth = dpf(3f); p.style = Paint.Style.STROKE; for (i in -1..1) c.drawLine(cx + i * r * 0.7f, cy + r * 0.6f, cx + i * r * 0.7f - r * 0.25f, cy + r * 1.5f, p); p.style = Paint.Style.FILL }
      id in 600..699 -> { cloud(cy - r * 0.4f, Color.rgb(220, 220, 230)); p.color = Color.WHITE; for (i in -1..1) c.drawCircle(cx + i * r * 0.7f, cy + r * 1.1f, r * 0.18f, p) }
      id in 700..799 -> { p.color = Color.rgb(190, 190, 200); p.strokeWidth = dpf(3f); p.style = Paint.Style.STROKE; for (i in 0..3) c.drawLine(cx - r * 1.3f, cy - r * 0.6f + i * r * 0.5f, cx + r * 1.3f, cy - r * 0.6f + i * r * 0.5f, p); p.style = Paint.Style.FILL }
      id == 800 -> sunOrMoon(cx, cy, r * 1.1f)
      id in 801..802 -> { sunOrMoon(cx + r * 0.6f, cy - r * 0.6f, r * 0.8f); cloud(cy + r * 0.2f, Color.rgb(235, 235, 240)) }
      else -> cloud(cy, Color.rgb(180, 180, 190))
    }
  }
}

/** Navigation card (navigation_card.py): maneuver icon, distance, primary/secondary text, next maneuver; tap = collapse, hold = cancel. */
class NavCardView(ctx: Context, attrs: AttributeSet? = null) : View(ctx, attrs) {
  lateinit var t: Telemetry
  private val p = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
  private val tp = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.WHITE; typeface = Typeface.DEFAULT_BOLD }
  var collapsed = false

  private fun icon(type: String, modifier: String): Bitmap? = NavIcons.INSTANCE.icon(context, type, modifier)

  private fun fmtDist(m: Float): String {
    if (t.isMetric) {
      if (m < 1000) { val step = if (m < 500) 25 else 50; return "${(m / step).roundToInt() * step} m" }
      val km = m / 1000f; return if (km >= 10f) "${floor(km).toInt()} km" else "%.1f km".format(km)
    }
    val ft = m * 3.28084f
    if (ft < 1000) { val step = if (ft <= 100) 10 else 50; return "${(ft / step).roundToInt() * step} ft" }
    val mi = ft / 5280f; return if (mi >= 10f) "${floor(mi).toInt()} mi" else "%.1f mi".format(mi)
  }

  override fun onDraw(c: Canvas) {
    if (!::t.isInitialized) return
    val nav = t.navCard(System.currentTimeMillis())?.first ?: return
    collapsed = t.mBool("NavInstructionCollapsed", false)
    val w = width.toFloat(); val h = height.toFloat(); val rr = dpf(16f)
    p.style = Paint.Style.FILL; p.color = Color.argb(192, 0, 0, 0)
    val ic = icon(nav.optString("maneuverType"), nav.optString("maneuverModifier"))
    if (collapsed) {
      val s = min(w, h)
      c.drawRoundRect(RectF(w - s, 0f, w, s), rr, rr, p)
      ic?.let { c.drawBitmap(it, null, RectF(w - s + s * 0.15f, s * 0.15f, w - s * 0.15f, s * 0.85f), p) }
      return
    }
    c.drawRoundRect(RectF(0f, 0f, w, h), rr, rr, p)
    val pad = dpf(12f); val iconS = h * 0.5f
    ic?.let { c.drawBitmap(it, null, RectF(pad, pad, pad + iconS, pad + iconS), p) }
    tp.textAlign = Paint.Align.LEFT
    tp.textSize = dpf(16f); tp.typeface = Typeface.DEFAULT_BOLD
    c.drawText(fmtDist(nav.optDouble("maneuverDistance", 0.0).toFloat()), pad, pad + iconS + dpf(20f), tp)
    val tx = pad + iconS + dpf(14f); val maxW = w - tx - pad
    val primary = nav.optString("maneuverPrimaryText")
    var size = dpf(22f)
    tp.textSize = size
    while (tp.measureText(primary) > maxW * 2 && size > dpf(15f)) { size -= dpf(1f); tp.textSize = size }
    val lines = wrap(primary, maxW, 2)
    var y = pad + size
    for (l in lines) { c.drawText(l, tx, y, tp); y += size * 1.1f }
    val secondary = nav.optString("maneuverSecondaryText")
    if (secondary.isNotEmpty()) { tp.typeface = Typeface.DEFAULT; tp.textSize = dpf(13f); tp.color = Color.argb(180, 255, 255, 255); c.drawText(wrap(secondary, maxW, 1).firstOrNull() ?: "", tx, y + dpf(2f), tp); tp.color = Color.WHITE }
    val nextType = nav.optString("nextManeuverType"); val nextMod = nav.optString("nextManeuverModifier")
    if (nextType.isNotEmpty() || nextMod.isNotEmpty()) {
      p.color = Color.argb(50, 255, 255, 255); c.drawRect(tx, h - dpf(34f), w - pad, h - dpf(33f), p)
      tp.typeface = Typeface.DEFAULT; tp.textSize = dpf(13f); c.drawText("Then", tx, h - dpf(12f), tp)
      icon(nextType, nextMod)?.let { c.drawBitmap(it, null, RectF(tx + dpf(44f), h - dpf(30f), tx + dpf(66f), h - dpf(8f)), p) }
    }
  }

  private fun wrap(text: String, maxW: Float, maxLines: Int): List<String> {
    val words = text.split(" "); val out = ArrayList<String>(); var cur = ""
    for (wd in words) {
      val cand = if (cur.isEmpty()) wd else "$cur $wd"
      if (tp.measureText(cand) <= maxW || cur.isEmpty()) cur = cand else { out.add(cur); cur = wd; if (out.size == maxLines) break }
    }
    if (out.size < maxLines && cur.isNotEmpty()) out.add(cur)
    if (out.size == maxLines && text != out.joinToString(" ")) out[maxLines - 1] = out[maxLines - 1].trimEnd() + "…"
    return out
  }
}

/** Speed-limit sources bubble (slc_speed_limit.py sources panel), shown while SpeedLimitSources is on. */
class SlcSourcesView(ctx: Context, attrs: AttributeSet? = null) : View(ctx, attrs) {
  lateinit var t: Telemetry
  private val p = Paint(Paint.ANTI_ALIAS_FLAG)
  private val tp = Paint(Paint.ANTI_ALIAS_FLAG).apply { typeface = Typeface.DEFAULT_BOLD }

  override fun onDraw(c: Canvas) {
    if (!::t.isInitialized || !t.started) return
    val on = if (t.sp7) t.s("slc_sources_sp7") else t.pBool("SpeedLimitSources", false)
    if (!on) return
    val conv = if (t.isMetric) 3.6f else 2.23694f
    // StarPilot 7: slcState's readings (each source's own limit), when they are fresh (3 s)
    val slc = t.slc?.takeIf { System.nanoTime() - t.slcAtNs < 3_000_000_000L }
    val names = mapOf("dashboard" to "DASH", "map" to "MAP", "vision" to "VISION", "online" to "ONLINE")
    val rows = slc?.readings?.filter { it.enabled } ?: emptyList()
    val w = width.toFloat(); val h = height.toFloat(); val rr = dpf(12f)
    p.style = Paint.Style.FILL; p.color = Color.argb(175, 0, 0, 0); c.drawRoundRect(RectF(0f, 0f, w, h), rr, rr, p)
    p.style = Paint.Style.STROKE; p.strokeWidth = 1f; p.color = Color.argb(80, 196, 205, 208); c.drawRoundRect(RectF(0.5f, 0.5f, w - 0.5f, h - 0.5f), rr, rr, p)
    if (rows.isEmpty()) {
      tp.textAlign = Paint.Align.CENTER; tp.textSize = dpf(14f); tp.color = Color.rgb(166, 166, 166)
      val msg = if (slc == null) "No speed limit data" else if (slc.readings.isEmpty()) "No speed limit" else "No sources enabled"
      c.drawText(msg, w / 2, h / 2 + dpf(5f), tp)
      return
    }
    val rowH = h / rows.size
    val used = if (slc!!.kind == "valid") slc.source else slc.acceptedSource
    for ((i, r) in rows.withIndex()) {
      val y = i * rowH
      val active = r.source == used
      if (i > 0) { p.style = Paint.Style.FILL; p.color = Color.argb(100, 196, 205, 208); c.drawRect(dpf(10f), y, w - dpf(10f), y + 1f, p) }
      if (active) { p.style = Paint.Style.FILL; p.color = Color.argb(230, 196, 205, 208); c.drawRoundRect(RectF(dpf(6f), y + rowH * 0.25f, dpf(10f), y + rowH * 0.75f), 2f, 2f, p) }
      tp.textAlign = Paint.Align.LEFT; tp.textSize = min(rowH * 0.36f, dpf(20f)); tp.color = if (active) Color.WHITE else Color.rgb(166, 166, 166)
      c.drawText(names[r.source] ?: r.source.uppercase(), dpf(18f), y + rowH * 0.64f, tp)
      tp.textAlign = Paint.Align.RIGHT
      c.drawText(if (r.kind == "valid" && r.speedMps > 0f) (r.speedMps * conv).roundToInt().toString() else "–", w - dpf(14f), y + rowH * 0.64f, tp)
    }
  }

}
