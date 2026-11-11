package com.starpilot.starview

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import org.json.JSONObject
import kotlin.math.max

/**
 * Where each HUD widget sits on the main screen, whether it is shown, and how big it is - one layout per theme
 * (day / night), edited by long-pressing the road view ([HudEditor]).
 *
 * Every editable widget is a direct child of the root FrameLayout, anchored top|start; the position is stored as a
 * fraction of the screen so the same layout works after a resolution change, the size as a scale factor applied with
 * View.scaleX/Y (pivot at the top-left, so the stored point stays the widget's corner). Widgets with no saved entry
 * use [Anchor] defaults that reproduce the original XML arrangement.
 */
class HudLayout(ctx: Context) {
  class Anchor(val gravity: Int, val dx: Int, val dy: Int)             // dp offsets from the anchored edges
  /** free (2.85.8): width and height are set on their own (dp in [State.w] / [State.h], default [defW] x [defH]) and the
   *  view is really that size - its content is not stretched (the see-through map shows more or less of the map). */
  class Item(val id: Int, val key: String, val label: String, val anchor: Anchor, val canHide: Boolean = true, val defaultOn: Boolean = true,
             val free: Boolean = false, val defW: Int = 0, val defH: Int = 0)
  /** x, y: top-left as a fraction of the window it was placed in; cw, ch (2.29.1): that window's size at full HUD scale
   *  (px; NaN = placed before 2.29.1, when the app was always full-screen landscape). */
  class State(var on: Boolean = true, var x: Float = Float.NaN, var y: Float = Float.NaN, var scale: Float = 1f,
              var cw: Float = Float.NaN, var ch: Float = Float.NaN,
              var w: Float = Float.NaN, var h: Float = Float.NaN, var zoom: Float = 1f)   // 2.85.8: free size (dp), map zoom

  private val prefs = ctx.getSharedPreferences("starview_hud", Context.MODE_PRIVATE)
  private val d = ctx.resources.displayMetrics.density
  private fun dp(v: Int) = (v * d).toInt()
  val items: List<Item> = listOf(
    Item(R.id.maxCard, "maxCard", "MAX set speed", Anchor(Gravity.TOP or Gravity.START, 24, 20)),
    Item(R.id.slc, "slc", "Speed limit", Anchor(Gravity.TOP or Gravity.START, 126, 20)),
    Item(R.id.slcSources, "slcSources", "Speed limit sources", Anchor(Gravity.TOP or Gravity.START, 228, 20)),
    Item(R.id.gauge, "gauge", "Curve / stop gauge", Anchor(Gravity.TOP or Gravity.START, 24, 134)),
    Item(R.id.compass, "compass", "Compass", Anchor(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 64)),
    Item(R.id.gear, "gear", "Settings", Anchor(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 14), canHide = false),
    Item(R.id.wheel, "wheel", "Steering wheel", Anchor(Gravity.TOP or Gravity.END, 24, 20)),
    Item(R.id.egpu, "egpu", "eGPU status", Anchor(Gravity.TOP or Gravity.END, 24, 188)),
    Item(R.id.navCard, "navCard", "Navigation - turn card", Anchor(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 72)),
    Item(R.id.navMap, "navMap", "Navigation - see-through map", Anchor(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 228), free = true, defW = 250, defH = 440),
    Item(R.id.nudgeHint, "nudgeHint", "Exit nudge prompt", Anchor(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 100)),
    Item(R.id.slowCard, "slowCard", "Why it's slowing", Anchor(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 200)),
    Item(R.id.laneCard, "laneCard", "Lane tracker (test)", Anchor(Gravity.TOP or Gravity.END, 24, 250)),
    Item(R.id.laneSuggest, "laneSuggest", "Change lanes? (confirm)", Anchor(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 310), canHide = false),
    Item(R.id.blinkCard, "blinkCard", "Blinker buttons (test)", Anchor(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 64)),
    Item(R.id.gpsCard, "gpsCard", "GPS satellites", Anchor(Gravity.TOP or Gravity.START, 24, 250)),
    Item(R.id.weather, "weather", "Weather", Anchor(Gravity.BOTTOM or Gravity.START, 28, 150)),
    Item(R.id.speedBox, "speedBox", "Speed", Anchor(Gravity.BOTTOM or Gravity.START, 28, 16)),
    Item(R.id.roadName, "roadName", "Road name", Anchor(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 10)),
    Item(R.id.devMetrics, "devMetrics", "Device metrics", Anchor(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 34)),
    Item(R.id.personality, "personality", "Following distance", Anchor(Gravity.BOTTOM or Gravity.END, 130, 20)),
    Item(R.id.dm, "dm", "Driver monitoring", Anchor(Gravity.BOTTOM or Gravity.END, 24, 20)),
    Item(R.id.slcActions, "slcActions", "Speed limit buttons (Accept / Decline)", Anchor(Gravity.TOP or Gravity.START, 126, 132)),
    Item(R.id.curveLabel, "curveLabel", "Curve speed label", Anchor(Gravity.TOP or Gravity.END, 134, 26)),
    Item(R.id.clockTv, "clock", "Clock", Anchor(Gravity.TOP or Gravity.END, 134, 66), defaultOn = false),
    Item(R.id.modeText, "modeText", "Driving mode", Anchor(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 58), defaultOn = false),
    Item(R.id.devSidebar, "devSidebar", "Developer sidebar", Anchor(Gravity.TOP or Gravity.END, 24, 250), defaultOn = false),
    Item(R.id.pipLeft, "pipLeft", "Side view - left", Anchor(Gravity.BOTTOM or Gravity.START, 24, 24)),
    Item(R.id.pipRight, "pipRight", "Side view - right", Anchor(Gravity.BOTTOM or Gravity.END, 24, 24)),
    // 2.85.3: the bottom buttons and chips are widgets too (they were fixed in a row and overlapped)
    Item(R.id.hudScene, "sceneBtn", "Scene / camera button", Anchor(Gravity.BOTTOM or Gravity.START, 24, 24)),
    Item(R.id.hudWhere, "whereBtn", "Where to? button", Anchor(Gravity.BOTTOM or Gravity.START, 168, 24)),
    Item(R.id.hudMap, "mapBtn", "Map button", Anchor(Gravity.BOTTOM or Gravity.START, 330, 24)),
    Item(R.id.hudBookmark, "bookmarkBtn", "Bookmark button", Anchor(Gravity.BOTTOM or Gravity.START, 426, 24)),
    Item(R.id.hudMic, "micBtn", "Voice button", Anchor(Gravity.BOTTOM or Gravity.START, 486, 24)),
    Item(R.id.hudQuick, "quickBtn", "Quick menu button", Anchor(Gravity.BOTTOM or Gravity.START, 546, 24)),
    Item(R.id.hudSaver, "saverPill", "Battery saver pill", Anchor(Gravity.BOTTOM or Gravity.START, 690, 24)),
    Item(R.id.hudFavBar, "favBar", "Favorites bar", Anchor(Gravity.BOTTOM or Gravity.START, 24, 84)),
    Item(R.id.hudIdeas, "ideasChip", "Why it's easing off", Anchor(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 70)),
  )

  val states = HashMap<String, State>()
  var theme = "day"; private set
  /** 2.29.0: the whole HUD shrinks with the window (split screen): 1 full screen, down to 0.5 (set by MainActivity). */
  var windowScale = 1f
  /** The whole display (px, long and short side) - the window layouts saved before 2.29.1 were made in. */
  var displayLong = 0f; var displayShort = 0f

  /**
   * A saved spot, in a window of a different size (split screen, rotated): kept at the same distance from the edge it
   * is near - left third: from the left, right third: from the right, middle: from the centre (same per axis) - in
   * full-scale px, so it scales with the rest. Before, a fraction of the window: a widget placed next to the right edge
   * slid away from it (the GPU badge over the wheel button in split screen).
   */
  private fun keep(v0: Float, b: Float, saved: Float, now: Float): Float {
    val c = v0 + b / 2
    return when { c < saved / 3 -> v0; c > 2 * saved / 3 -> now - (saved - v0); else -> now / 2 + (c - saved / 2) - b / 2 }
  }
  /** Saved top-left (px) in the current window for a widget drawn w x h at window scale ws. */
  private fun savedPos(s: State, rw: Int, rh: Int, w: Int, h: Int): Pair<Int, Int> {
    val ws = windowScale
    val sw = if (s.cw.isNaN()) (if (displayLong > 0f) displayLong else rw / ws) else s.cw
    val sh = if (s.ch.isNaN()) (if (displayShort > 0f) displayShort else rh / ws) else s.ch
    val bw = w / ws; val bh = h / ws                                // full-scale size
    val x = keep(s.x * sw, bw, sw, rw / ws) * ws; val y = keep(s.y * sh, bh, sh, rh / ws) * ws
    return x.toInt() to y.toInt()
  }
  /** Store a spot placed now (px, top-left) in the current window. */
  fun place(s: State, left: Float, top: Float, rw: Int, rh: Int) {
    s.x = left / rw; s.y = top / rh; s.cw = rw / windowScale; s.ch = rh / windowScale
  }

  /** One layout for day and night (before: one per theme - the first found is taken over). */
  fun load(theme: String) {
    this.theme = theme
    restore(prefs.getString(KEY, null) ?: prefs.getString("layout_day", null) ?: prefs.getString("layout_night", null))
  }

  /** The layout as JSON (saved, and sent along with a bookmark). */
  fun snapshot(): String {
    val o = JSONObject()
    for ((k, s) in states) o.put(k, JSONObject().put("on", s.on).put("x", if (s.x.isNaN()) JSONObject.NULL else s.x.toDouble()).put("y", if (s.y.isNaN()) JSONObject.NULL else s.y.toDouble()).put("s", s.scale.toDouble())
      .put("cw", if (s.cw.isNaN()) JSONObject.NULL else s.cw.toDouble()).put("ch", if (s.ch.isNaN()) JSONObject.NULL else s.ch.toDouble())
      .put("w", if (s.w.isNaN()) JSONObject.NULL else s.w.toDouble()).put("h", if (s.h.isNaN()) JSONObject.NULL else s.h.toDouble()).put("z", s.zoom.toDouble()))
    return o.toString()
  }

  fun restore(raw: String?) {
    states.clear()
    if (raw == null) return
    runCatching {
      val o = JSONObject(raw)
      for (k in o.keys()) { val e = o.getJSONObject(k); states[k] = State(e.optBoolean("on", true), e.optDouble("x", Double.NaN).toFloat(), e.optDouble("y", Double.NaN).toFloat(), e.optDouble("s", 1.0).toFloat(),
        e.optDouble("cw", Double.NaN).toFloat(), e.optDouble("ch", Double.NaN).toFloat(),
        e.optDouble("w", Double.NaN).toFloat(), e.optDouble("h", Double.NaN).toFloat(), e.optDouble("z", 1.0).toFloat()) }
    }
  }

  fun save() { prefs.edit().putString(KEY, snapshot()).apply() }

  fun reset() { states.clear(); prefs.edit().remove(KEY).remove("layout_day").remove("layout_night").apply() }

  private fun defaultOn(key: String) = items.firstOrNull { it.key == key }?.defaultOn ?: true

  /** A free-size widget's width / height (dp): the saved one, else its default. */
  fun freeW(item: Item): Float = states[item.key]?.w?.takeIf { !it.isNaN() } ?: item.defW.toFloat()
  fun freeH(item: Item): Float = states[item.key]?.h?.takeIf { !it.isNaN() } ?: item.defH.toFloat()
  private fun dp(v: Float) = (v * d).toInt()
  /** The view's own size (px, before scale): a free-size widget's set size right away (the view lays out a frame later). */
  private fun pxW(v: View, item: Item) = if (item.free) dp(freeW(item)) else if (v.width > 0) v.width else v.measuredWidth
  private fun pxH(v: View, item: Item) = if (item.free) dp(freeH(item)) else if (v.height > 0) v.height else v.measuredHeight
  fun state(key: String): State = states.getOrPut(key) { State(defaultOn(key)) }
  fun enabled(key: String) = states[key]?.on ?: defaultOn(key)

  companion object { const val KEY = "layout" }

  /** Move every editable widget out of its XML container and directly under the root, anchored top|start. */
  fun adopt(root: FrameLayout) {
    for (it in items) {
      val v = root.findViewById<View>(it.id) ?: continue
      val parent = v.parent as? ViewGroup ?: continue
      val w = v.layoutParams.width; val h = v.layoutParams.height
      if (parent !== root) { parent.removeView(v); root.addView(v, FrameLayout.LayoutParams(w, h, Gravity.TOP or Gravity.START)) }
      else { (v.layoutParams as FrameLayout.LayoutParams).gravity = Gravity.TOP or Gravity.START }
      v.pivotX = 0f; v.pivotY = 0f
    }
    // the emptied containers would still take space / eat touches: drop them
    for (id in intArrayOf(R.id.topLeft)) root.findViewById<View>(id)?.let { root.removeView(it) }
    val leftovers = (0 until root.childCount).map { root.getChildAt(it) }.filter { it is ViewGroup && it.id == View.NO_ID && (it as ViewGroup).childCount == 0 }
    for (v in leftovers) root.removeView(v)
  }

  /** A widget made in code after [adopt] ran: given its item's id, anchored top|start (no margins), pivot at its corner. */
  fun adoptLate(v: View?, id: Int) {
    if (v == null) return
    v.id = id
    val old = v.layoutParams
    v.layoutParams = FrameLayout.LayoutParams(old?.width ?: ViewGroup.LayoutParams.WRAP_CONTENT, old?.height ?: ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.TOP or Gravity.START)
    v.pivotX = 0f; v.pivotY = 0f
  }

  /** Default top-left position (px) for a widget of the given size, from its XML-equivalent anchor. */
  fun defaultPos(item: Item, rootW: Int, rootH: Int, w: Int, h: Int): Pair<Int, Int> {
    val g = item.anchor.gravity; val ws = windowScale
    val x = when {
      g and Gravity.END == Gravity.END -> rootW - w - (dp(item.anchor.dx) * ws).toInt()
      g and Gravity.CENTER_HORIZONTAL == Gravity.CENTER_HORIZONTAL -> (rootW - w) / 2
      else -> (dp(item.anchor.dx) * ws).toInt()
    }
    val y = when {
      g and Gravity.BOTTOM == Gravity.BOTTOM -> rootH - h - (dp(item.anchor.dy) * ws).toInt()
      else -> (dp(item.anchor.dy) * ws).toInt()
    }
    return x to y
  }

  /** Apply positions / scales to the views. Call after layout (sizes must be known) - cheap enough for every tick. */
  fun apply(root: FrameLayout) {
    val rw = root.width; val rh = root.height
    if (rw == 0 || rh == 0) return
    for (it in items) {
      val v = root.findViewById<View>(it.id) ?: continue
      val lp = v.layoutParams as? FrameLayout.LayoutParams ?: continue
      val s = states[it.key]
      if (it.free) {
        // 2.85.8: a free-size widget is really its width x height (scale stays 1); an old uniform size becomes w x h once
        if (s != null && s.scale != 1f && s.w.isNaN()) { s.w = it.defW * s.scale; s.h = it.defH * s.scale; s.scale = 1f }
        val fw = dp(freeW(it)); val fh = dp(freeH(it))
        if (lp.width != fw || lp.height != fh) { lp.width = fw; lp.height = fh; v.layoutParams = lp }
        (v as? NavMapView)?.zoom = s?.zoom ?: 1f
      }
      val sc = (if (it.free) 1f else (s?.scale ?: 1f)) * windowScale
      // the size as drawn (scaled about the top-left corner), so right / bottom anchored widgets keep their margin
      val w = (pxW(v, it) * sc).toInt(); val h = (pxH(v, it) * sc).toInt()
      val (dx, dy) = defaultPos(it, rw, rh, w, h)
      // a saved spot is kept on screen when the window gets smaller
      val sp = if (s == null || s.x.isNaN() || s.y.isNaN()) null else savedPos(s, rw, rh, w, h)
      val x = sp?.first?.coerceIn(0, max(0, rw - w)) ?: dx
      val y = sp?.second?.coerceIn(0, max(0, rh - h)) ?: dy
      if (lp.leftMargin != x || lp.topMargin != y) { lp.leftMargin = x; lp.topMargin = y; lp.gravity = Gravity.TOP or Gravity.START; v.layoutParams = lp }
      if (v.scaleX != sc) { v.scaleX = sc; v.scaleY = sc }
    }
  }

  /** Screen rect of a widget as drawn (position + scale), in root coordinates. */
  fun rect(root: FrameLayout, item: Item): android.graphics.RectF? {
    val v = root.findViewById<View>(item.id) ?: return null
    val lp = v.layoutParams as? FrameLayout.LayoutParams ?: return null
    val sc = (if (item.free) 1f else (states[item.key]?.scale ?: 1f)) * windowScale
    val w = pxW(v, item) * sc; val h = pxH(v, item) * sc
    return android.graphics.RectF(lp.leftMargin.toFloat(), lp.topMargin.toFloat(), lp.leftMargin + w, lp.topMargin + h)
  }
}
