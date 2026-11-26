package com.starpilot.starview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import kotlin.math.abs
import kotlin.math.roundToInt

/**
 * 2.90.14: the screen editor's Edit tab for "Navigation - road arrow" (HudEditor.hybOwnPanel): it is drawn on the road,
 * not a box on the screen, so instead of the size and the position: a picture of it as set, its colour, light or dark,
 * how see-through, the running light, where it shows, and taking it off the screen. Every change is kept at once
 * (RoadArrowStyle) and shows on the road straight away.
 */
object RoadArrowPanel {
  fun build(content: LinearLayout, ui: CarUi, refresh: () -> Unit, takeOff: () -> Unit) {
    val ctx = content.context
    RoadArrowStyle.load(ctx)
    fun set(change: () -> Unit) { change(); RoadArrowStyle.save(ctx); refresh() }
    content.addView(Swatch(ctx), LinearLayout.LayoutParams(-1, ui.dp(170)).also { it.bottomMargin = ui.dp(4) })
    content.addView(ui.note("On the road ahead with a route: a turn from 500 ft, an exit or a fork from half a mile, on the lane to take."))

    content.addView(ui.header("Color"))
    val names = RoadArrowStyle.COLORS
    for (row in names.indices.chunked(3)) {
      val line = LinearLayout(ctx).also { it.orientation = LinearLayout.HORIZONTAL }
      for ((k, i) in row.withIndex()) {
        val lp = LinearLayout.LayoutParams(0, ui.dp(54), 1f)
        if (k > 0) lp.marginStart = ui.dp(8)
        line.addView(chip(ctx, ui, i, i == RoadArrowStyle.color) { set { RoadArrowStyle.color = i } }, lp)
      }
      content.addView(line, LinearLayout.LayoutParams(-1, -2).also { it.bottomMargin = ui.dp(8) })
    }

    content.addView(ui.header("Light or dark"))
    stepRow(content, ui, "Shade", lightName(RoadArrowStyle.light),
      { set { RoadArrowStyle.light = (RoadArrowStyle.light - 0.25f).coerceIn(-1f, 1f) } },
      { set { RoadArrowStyle.light = (RoadArrowStyle.light + 0.25f).coerceIn(-1f, 1f) } })

    content.addView(ui.header("See-through"))
    val see = ((1 - RoadArrowStyle.opacity) * 100).roundToInt()
    stepRow(content, ui, "How much", if (see <= 0) "None (solid)" else "$see %",
      { set { RoadArrowStyle.opacity = ((RoadArrowStyle.opacity * 10).roundToInt() + 1).coerceIn(3, 10) / 10f } },
      { set { RoadArrowStyle.opacity = ((RoadArrowStyle.opacity * 10).roundToInt() - 1).coerceIn(3, 10) / 10f } })

    content.addView(ui.header("Running light"))
    val run = LinearLayout(ctx).also { it.orientation = LinearLayout.HORIZONTAL; it.gravity = Gravity.CENTER_VERTICAL }
    run.addView(TextView(ctx).also {
      it.text = "Light runs along it toward the turn"; it.textSize = 17f; it.setTextColor(Color.WHITE)
    }, LinearLayout.LayoutParams(0, -2, 1f))
    val sw = ui.bigSwitch()
    sw.isChecked = RoadArrowStyle.running
    sw.setOnCheckedChangeListener { _, on -> set { RoadArrowStyle.running = on } }
    run.addView(sw)
    content.addView(run, LinearLayout.LayoutParams(-1, -2).also { it.bottomMargin = ui.dp(8) })

    content.addView(ui.header("Show it in"))
    for ((i, name) in RoadArrowStyle.WHERE.withIndex()) {
      content.addView(ui.button(name, i == RoadArrowStyle.where) { set { RoadArrowStyle.where = i } },
        LinearLayout.LayoutParams(-1, -2).also { it.bottomMargin = ui.dp(8) })
    }

    content.addView(ui.header("Screen"))
    content.addView(ui.button("Take it off the screen", false) { takeOff() }, LinearLayout.LayoutParams(-1, -2).also { it.bottomMargin = ui.dp(8) })
  }

  fun lightName(l: Float): String {
    val k = (abs(l) * 4).roundToInt()
    if (k == 0) return "As it comes"
    val word = if (l > 0) "lighter" else "darker"
    return when (k) { 1 -> "A little $word"; 2 -> word.replaceFirstChar { it.uppercase() }; 3 -> "Much $word"; else -> if (l > 0) "Lightest" else "Darkest" }
  }

  /** a colour to pick: its own gradient (light at the van, deep at the arrowhead, with today's light / dark), its name */
  private fun chip(ctx: Context, ui: CarUi, i: Int, on: Boolean, pick: () -> Unit): View {
    val (name, pair) = RoadArrowStyle.COLORS[i]
    val near = RoadArrowStyle.shadeOf(pair.second); val far = RoadArrowStyle.shadeOf(pair.first)
    val bg = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, intArrayOf(near, far))
    bg.cornerRadius = ui.dp(12).toFloat()
    if (on) bg.setStroke(ui.dp(3), Color.WHITE) else bg.setStroke(ui.dp(1), Color.argb(90, 255, 255, 255))
    val lum = (Color.red(near) * 0.3 + Color.green(near) * 0.59 + Color.blue(near) * 0.11) / 255
    return TextView(ctx).also {
      it.text = if (on) "✓ $name" else name
      it.textSize = 16f; it.typeface = Typeface.DEFAULT_BOLD; it.gravity = Gravity.CENTER
      it.setTextColor(if (lum > 0.72) Color.rgb(16, 22, 34) else Color.WHITE)
      if (lum <= 0.72) it.setShadowLayer(4f, 0f, 1f, Color.argb(150, 0, 0, 0))
      it.background = bg
      it.setOnClickListener { pick() }
    }
  }

  private fun stepRow(content: LinearLayout, ui: CarUi, name: String, value: String, minus: () -> Unit, plus: () -> Unit) {
    val ctx = content.context
    val row = LinearLayout(ctx).also { it.orientation = LinearLayout.HORIZONTAL; it.gravity = Gravity.CENTER_VERTICAL }
    row.addView(ui.stepper("−") { minus() })
    row.addView(TextView(ctx).also {
      it.text = "$name\n$value"; it.textSize = 18f; it.setTextColor(Color.WHITE); it.gravity = Gravity.CENTER
    }, LinearLayout.LayoutParams(0, -2, 1f))
    row.addView(ui.stepper("+") { plus() })
    content.addView(row, LinearLayout.LayoutParams(-1, -2).also { it.bottomMargin = ui.dp(10) })
  }

  /**
   * The arrow as set, on a made-up street corner: a right turn 80 ft ahead, seen from behind and above like the Scene,
   * drawn by the same RoadArrowPainter (and its running light running).
   */
  class Swatch(ctx: Context) : View(ctx) {
    /** tests: the running light's place; NaN = by the clock */
    var phase = Double.NaN
    private val dp = ctx.resources.displayMetrics.density
    private val painter = RoadArrowPainter(dp * 0.6f)
    private val t0 = System.nanoTime()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path()
    private val clip = Path()
    private val box = RectF()
    private val p = FloatArray(2)
    // a camera behind and above the van looking down at the corner, like the Scene's; [k], [cx], [cy] fit the picture
    private var k = 1.0; private var cx = 0.0; private var cy = 0.0
    private val back = 16.0; private val camH = 11.0
    private val sinT = kotlin.math.sin(Math.toRadians(40.0)); private val cosT = kotlin.math.cos(Math.toRadians(40.0))
    private val proj = RoadArrowPainter.Proj { r, f, h, out ->
      val dy = f + back; val dz = camH - h
      val depth = dy * cosT + dz * sinT
      if (depth < 0.5) false else { out[0] = (cx + k * r / depth).toFloat(); out[1] = (cy - k * (dy * sinT - dz * cosT) / depth).toFloat(); true }
    }
    private val dist = 20.0
    private val shape: RoadArrow.Shape? by lazy {
      // the route: up our street, then right along the cross street (its middle at [dist]); it ends 50 ft into it so
      // the arrowhead stays in the picture
      val rr = ArrayList<Double>(); val ff = ArrayList<Double>(); val ss = ArrayList<Double>()
      var s = 0.0
      while (s <= dist + 15) {
        if (s <= dist) { rr.add(0.0); ff.add(s) } else { rr.add(s - dist); ff.add(dist) }
        ss.add(s); s += 1.0
      }
      RoadArrow.shape(RoadArrow.Kind.TURN, 1, dist, 1.8, 1.8, { a, d -> doubleArrayOf(d, a) },
        { doubleArrayOf(-3.6, 3.6, 999.0, -3.6, 3.6) }, rr.toDoubleArray(), ff.toDoubleArray(), ss.toDoubleArray())
    }

    private fun at(r: Double, f: Double): Boolean = proj.at(r, f, 0.0, p)

    /** a flat piece through (r, f, h) corners */
    private fun poly(c: Canvas, vararg q: Double) {
      path.rewind()
      var i = 0
      while (i + 2 < q.size) { proj.at(q[i], q[i + 1], q[i + 2], p); if (i == 0) path.moveTo(p[0], p[1]) else path.lineTo(p[0], p[1]); i += 3 }
      path.close(); c.drawPath(path, paint)
    }

    private fun quad(r0: Double, f0: Double, r1: Double, f1: Double): Path {
      path.rewind()
      at(r0, f0); path.moveTo(p[0], p[1]); at(r1, f0); path.lineTo(p[0], p[1])
      at(r1, f1); path.lineTo(p[0], p[1]); at(r0, f1); path.lineTo(p[0], p[1]); path.close()
      return path
    }

    override fun onDraw(c: Canvas) {
      val w = width.toDouble(); val h = height.toDouble()
      if (w <= 0 || h <= 0) return
      // the van and the arrow's ends fill the picture
      k = 1.0; cx = 0.0; cy = 0.0
      var x0 = 1e9; var x1 = -1e9; var y0 = 1e9; var y1 = -1e9
      for (q in arrayOf(doubleArrayOf(0.8, -2.4, 0.0), doubleArrayOf(2.8, -2.4, 0.0), doubleArrayOf(-3.6, 3.6, 0.0),
          doubleArrayOf(1.8, dist - 6, 0.0), doubleArrayOf(18.0, dist - 1.8, 0.0), doubleArrayOf(18.0, dist + 4.0, 0.0))) {
        proj.at(q[0], q[1], q[2], p)
        x0 = minOf(x0, p[0].toDouble()); x1 = maxOf(x1, p[0].toDouble()); y0 = minOf(y0, p[1].toDouble()); y1 = maxOf(y1, p[1].toDouble())
      }
      k = minOf(w * 0.86 / (x1 - x0), h * 0.82 / (y1 - y0))
      cx = w / 2 - k * (x0 + x1) / 2; cy = h * 0.47 - k * (y0 + y1) / 2
      box.set(0f, 0f, width.toFloat(), height.toFloat())
      clip.rewind(); clip.addRoundRect(box, 14 * dp, 14 * dp, Path.Direction.CW)
      c.save(); c.clipPath(clip)
      paint.shader = LinearGradient(0f, 0f, 0f, height.toFloat(), Color.rgb(28, 33, 41), Color.rgb(14, 17, 22), Shader.TileMode.CLAMP)
      c.drawRect(box, paint); paint.shader = null
      // the streets
      paint.color = Color.rgb(46, 52, 62)
      c.drawPath(quad(-3.6, -12.0, 3.6, 140.0), paint)
      c.drawPath(quad(-60.0, dist - 3.6, 80.0, dist + 3.6), paint)
      // the kerbs and the middle line
      paint.style = Paint.Style.STROKE; paint.strokeWidth = 1.4f * dp
      paint.color = Color.argb(110, 220, 226, 236)
      for (r in doubleArrayOf(-3.6, 3.6)) for ((a, b) in listOf(-12.0 to dist - 3.6, dist + 3.6 to 140.0)) {
        at(r, a); val x0 = p[0]; val y0 = p[1]; at(r, b); c.drawLine(x0, y0, p[0], p[1], paint)
      }
      for (f in doubleArrayOf(dist - 3.6, dist + 3.6)) for ((a, b) in listOf(-60.0 to -3.6, 3.6 to 80.0)) {
        at(a, f); val x0 = p[0]; val y0 = p[1]; at(b, f); c.drawLine(x0, y0, p[0], p[1], paint)
      }
      paint.color = Color.argb(150, 230, 190, 70)
      var f = -12.0
      while (f < 140) { if (f + 3 < dist - 3.6 || f > dist + 3.6) { at(0.0, f); val x0 = p[0]; val y0 = p[1]; at(0.0, f + 3); c.drawLine(x0, y0, p[0], p[1], paint) }; f += 7.5 }
      paint.style = Paint.Style.FILL
      val sh = shape
      if (sh != null) {
        val ph = if (phase.isNaN()) ((System.nanoTime() - t0) / 0.5e9) % 1.0 else phase
        painter.paint(c, sh, proj, ph)
      }
      // the van, from behind: its roof and its back with the tail lights
      paint.color = Color.rgb(196, 204, 218)
      poly(c, 0.8, 2.6, 1.8, 2.8, 2.6, 1.8, 2.8, -2.4, 1.8, 0.8, -2.4, 1.8)
      paint.color = Color.rgb(150, 158, 174)
      poly(c, 0.8, -2.4, 0.15, 2.8, -2.4, 0.15, 2.8, -2.4, 1.8, 0.8, -2.4, 1.8)
      paint.color = Color.rgb(52, 60, 74)
      poly(c, 1.05, -2.4, 1.05, 2.55, -2.4, 1.05, 2.55, -2.4, 1.65, 1.05, -2.4, 1.65)
      paint.color = Color.rgb(225, 45, 45)
      poly(c, 0.85, -2.4, 0.75, 1.1, -2.4, 0.75, 1.1, -2.4, 1.0, 0.85, -2.4, 1.0)
      poly(c, 2.5, -2.4, 0.75, 2.75, -2.4, 0.75, 2.75, -2.4, 1.0, 2.5, -2.4, 1.0)
      c.restore()
      if (!RoadArrowStyle.on) {
        paint.color = Color.argb(150, 0, 0, 0); c.drawRoundRect(box, 14 * dp, 14 * dp, paint)
      }
      if (RoadArrowStyle.running && phase.isNaN()) postInvalidateOnAnimation()
    }
  }
}
