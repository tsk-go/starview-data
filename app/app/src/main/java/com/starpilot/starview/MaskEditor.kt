package com.starpilot.starview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import kotlin.math.hypot
import kotlin.math.min

/**
 * 2.24.0: draw the detector masks ([DetMasks]) on a still of the whole camera picture (not the zoomed road view, which
 * hides the hood). Tap to place corners, tap the first corner (or Finish) to close an area, drag any corner to move it,
 * tap inside an area to select it. Every change is saved and used by the detector at once.
 */
@SuppressLint("ViewConstructor")
class MaskEditor(ctx: Context, private val det: Detector, var wide: Boolean, private val onCamera: (Boolean) -> Unit, private val onDone: () -> Unit) : View(ctx) {
  private val ui = CarUi(ctx, false)
  private val d = ctx.resources.displayMetrics.density
  private var polys = ArrayList(DetMasks.get(wide))
  private val cur = ArrayList<Float>()
  private var sel = -1
  private var dragP = -2; private var dragV = -1; private var downX = 0f; private var downY = 0f; private var moved = false
  private var img: android.graphics.Bitmap? = null
  private val pic = RectF()
  private val fill = Paint(Paint.ANTI_ALIAS_FLAG)
  private val line = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.STROKE; strokeWidth = 3 * d }
  private val text = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.WHITE; textSize = 17 * d; textAlign = Paint.Align.CENTER }
  private val path = Path(); private val all = Path()
  private fun outline(into: Path, p: FloatArray) { for (v in 0 until p.size / 2) { if (v == 0) into.moveTo(sx(p[0]), sy(p[1])) else into.lineTo(sx(p[2 * v]), sy(p[2 * v + 1])) }; into.close() }
  val bar: LinearLayout = LinearLayout(ctx).apply { orientation = LinearLayout.VERTICAL; gravity = android.view.Gravity.CENTER_HORIZONTAL; setPadding(ui.dp(12), ui.dp(12), ui.dp(12), ui.dp(8)) }
  private val row = LinearLayout(ctx).apply { orientation = LinearLayout.HORIZONTAL }
  private val paintCam: (Int) -> Unit
  private val paintMode: (Int) -> Unit
  private val poll = object : Runnable { override fun run() { val s = det.snap; if (s != null && det.snapWide == wide) { img = s; invalidate() }; det.snapWant = if (wide) 2 else 1; postDelayed(this, 1000) } }

  init {
    setBackgroundColor(Color.rgb(11, 13, 18))
    val (chips, p) = ui.chips(listOf("Road camera", "Wide camera")) { i -> camera(i == 1) }; paintCam = p; p(if (wide) 1 else 0)
    bar.addView(row, LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)); row.addView(chips, LinearLayout.LayoutParams(ui.dp(400), LinearLayout.LayoutParams.WRAP_CONTENT))
    for ((label, act) in listOf<Pair<String, () -> Unit>>("Finish area" to { close() }, "Undo corner" to { if (cur.isNotEmpty()) { cur.removeAt(cur.size - 1); cur.removeAt(cur.size - 1); invalidate() } },
        "Delete area" to { if (cur.isNotEmpty()) cur.clear() else if (sel in polys.indices) { polys.removeAt(sel); sel = -1; save() }; invalidate() })) {
      row.addView(View(ctx), LinearLayout.LayoutParams(ui.dp(10), 1)); row.addView(ui.button(label, false, act))
    }
    row.addView(View(ctx), LinearLayout.LayoutParams(ui.dp(10), 1)); row.addView(ui.button("Done", true) { close(); onDone() })
    // 2.24.1: what the areas mean for this camera
    val (modes, pm) = ui.chips(listOf("Ignore inside the areas", "Only look inside the areas")) { i -> DetMasks.setKeep(wide, i == 1); paintMode(i); invalidate() }
    paintMode = pm; pm(if (DetMasks.keep(wide)) 1 else 0)
    bar.addView(modes, LinearLayout.LayoutParams(ui.dp(620), LinearLayout.LayoutParams.WRAP_CONTENT).apply { topMargin = ui.dp(8) })
  }

  override fun onAttachedToWindow() { super.onAttachedToWindow(); post(poll) }
  override fun onDetachedFromWindow() { removeCallbacks(poll); det.snapWant = 0; super.onDetachedFromWindow() }

  private fun camera(w: Boolean) {
    if (w == wide) return
    close(); wide = w; polys = ArrayList(DetMasks.get(w)); sel = -1; img = null; paintCam(if (w) 1 else 0); paintMode(if (DetMasks.keep(w)) 1 else 0); onCamera(w); invalidate()
  }
  private fun save() = DetMasks.set(wide, polys)
  private fun close() { if (cur.size >= 6) { polys.add(cur.toFloatArray()); sel = polys.size - 1; save() }; cur.clear(); invalidate() }

  private fun fx(x: Float) = ((x - pic.left) / pic.width()).coerceIn(0f, 1f)
  private fun fy(y: Float) = ((y - pic.top) / pic.height()).coerceIn(0f, 1f)
  private fun sx(f: Float) = pic.left + f * pic.width()
  private fun sy(f: Float) = pic.top + f * pic.height()

  /** Nearest corner within reach: (polygon index or -1 for the one being drawn, corner index). */
  private fun corner(x: Float, y: Float): Pair<Int, Int>? {
    var best: Pair<Int, Int>? = null; var bd = 30 * d
    fun look(pi: Int, p: List<Float>) { for (v in 0 until p.size / 2) { val dd = hypot(sx(p[2 * v]) - x, sy(p[2 * v + 1]) - y); if (dd < bd) { bd = dd; best = pi to v } } }
    look(-1, cur); for ((i, p) in polys.withIndex()) look(i, p.toList())
    return best
  }

  @SuppressLint("ClickableViewAccessibility")
  override fun onTouchEvent(e: MotionEvent): Boolean {
    when (e.actionMasked) {
      MotionEvent.ACTION_DOWN -> { downX = e.x; downY = e.y; moved = false; val c = corner(e.x, e.y); dragP = c?.first ?: -2; dragV = c?.second ?: -1 }
      MotionEvent.ACTION_MOVE -> {
        if (hypot(e.x - downX, e.y - downY) > 8 * d) moved = true
        if (moved && dragP > -2) { if (dragP == -1) { cur[2 * dragV] = fx(e.x); cur[2 * dragV + 1] = fy(e.y) } else { polys[dragP][2 * dragV] = fx(e.x); polys[dragP][2 * dragV + 1] = fy(e.y) }; invalidate() }
      }
      MotionEvent.ACTION_UP -> {
        if (moved) { if (dragP >= 0) save(); return true }
        if (img == null || !pic.contains(e.x, e.y)) return true
        if (dragP == -1 && dragV == 0 && cur.size >= 6) { close(); return true }
        if (cur.isEmpty()) { val hit = polys.indexOfLast { DetMasks.inside(it, fx(e.x), fy(e.y)) }; if (hit >= 0) { sel = hit; invalidate(); return true } }
        if (cur.size < 200) { cur.add(fx(e.x)); cur.add(fy(e.y)); sel = -1; invalidate() }
      }
    }
    return true
  }

  override fun onDraw(c: Canvas) {
    val top = bar.height.toFloat() + 8 * d; val b = img
    if (b == null) {
      c.drawText("Waiting for the ${if (wide) "wide" else "road"} camera… (it streams while the car is on)", width / 2f, height / 2f, text); return
    }
    val s = min(width / b.width.toFloat(), (height - top - 40 * d) / b.height); val w = b.width * s; val h = b.height * s
    pic.set((width - w) / 2, top, (width + w) / 2, top + h)
    c.drawBitmap(b, null, pic, null)
    val keep = DetMasks.keep(wide)
    fill.color = Color.argb(170, 114, 114, 114)
    if (keep && polys.isNotEmpty()) {   // only look inside: grey out everything else
      all.rewind(); for (p in polys) outline(all, p)
      c.save(); c.clipOutPath(all); c.drawRect(pic, fill); c.restore()
    }
    for ((i, p) in polys.withIndex()) {
      path.rewind(); outline(path, p)
      if (!keep) { fill.color = Color.argb(170, 114, 114, 114); c.drawPath(path, fill) }
      line.color = if (i == sel) CarUi.Companion.getACCENT() else Color.argb(200, 255, 255, 255); c.drawPath(path, line)
      fill.color = line.color; for (v in 0 until p.size / 2) c.drawCircle(sx(p[2 * v]), sy(p[2 * v + 1]), 7 * d, fill)
    }
    if (cur.isNotEmpty()) {
      line.color = CarUi.Companion.getACCENT(); fill.color = CarUi.Companion.getACCENT()
      for (v in 0 until cur.size / 2) { val x = sx(cur[2 * v]); val y = sy(cur[2 * v + 1]); c.drawCircle(x, y, (if (v == 0) 11 else 7) * d, fill); if (v > 0) c.drawLine(sx(cur[2 * v - 2]), sy(cur[2 * v - 1]), x, y, line) }
    }
    c.drawText(when {
      cur.isNotEmpty() -> "Tap to add corners. Tap the first corner or Finish area to close it."
      keep -> "Draw around what the detector should look at (the road through the windshield). Grey = ignored."
      else -> "Draw around what the detector should ignore (hood, dash, mirror mount, reflections). Grey = ignored."
    }, width / 2f, pic.bottom + 28 * d, text)
  }
}
