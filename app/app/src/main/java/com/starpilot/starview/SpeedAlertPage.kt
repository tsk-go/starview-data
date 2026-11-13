package com.starpilot.starview

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Typeface
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView

/**
 * 2.88.0: the speed limit warning's settings (SpeedAlert) - the owner's own list of limits and how far over each is
 * fine, the sound (AlertSounds, or none) and how many times in a row it plays. Every change is saved at once.
 * 2.88.1: tapping the speed sign opens it over the driving screen (showDialog), and its own page, Tablet > Settings >
 * "Speed limit warning" (a Rail after "Voice alerts" in SettingsActivity.areas,
 * opened by hybPage at the start of buildPageUnsafe). 2.88.0 put it only under the Widgets switches, a page the settings
 * list never shows (Widgets are the screen editor's), so the owner couldn't find it; it stays there too (settings search
 * of the speed sign opens that page).
 */
object SpeedAlertPage {
  private const val TAG = "StarView/SpeedAlertPage"
  private const val LIMIT_STEP = 5
  private const val MIN_LIMIT = 5
  private const val MAX_LIMIT = 130
  private const val MAX_OVER = 30
  private const val MAX_TIMES = 5

  @JvmStatic
  fun addTo(a: Activity, group: String, col: LinearLayout) {
    if (group != "Widgets") return
    try {
      build(a, col)
    } catch (e: Exception) {
      Log.w(TAG, "page", e)
    }
  }

  const val PAGE = "tablet:speedalert"

  /** SettingsActivity.buildPageUnsafe: our page for [key], or null for every other page */
  @JvmStatic
  fun hybPage(a: Activity, key: String?): View? {
    if (key != PAGE) return null
    val ui = CarUi(a, false)
    val col = LinearLayout(a).apply {
      orientation = LinearLayout.VERTICAL
      setPadding(ui.dp(10), ui.dp(4), ui.dp(24), ui.dp(60))      // as SettingsActivity.page()
    }
    try {
      build(a, col, header = false)
    } catch (e: Exception) {
      Log.w(TAG, "page", e)
    }
    return ScrollView(a).apply { addView(col) }
  }

  @Volatile private var open: Dialog? = null

  /** 2.88.1: tapping the speed sign opens this page over the driving screen, with a Close button */
  fun showDialog(a: Activity) {
    if (open?.isShowing == true || a.isFinishing) return
    val ui = CarUi(a, false)
    val bg = CarUi.Companion.getBG()
    val root = LinearLayout(a).apply { orientation = LinearLayout.VERTICAL }
    val bar = LinearLayout(a).apply {
      orientation = LinearLayout.HORIZONTAL
      gravity = Gravity.CENTER_VERTICAL
      setPadding(ui.dp(22), ui.dp(12), ui.dp(16), ui.dp(8))
    }
    bar.addView(TextView(a).apply {
      text = "Speed limit warning"
      textSize = 24f
      setTextColor(CarUi.Companion.getTEXT())
      typeface = Typeface.DEFAULT_BOLD
    }, LinearLayout.LayoutParams(0, -2, 1f))
    val d = Dialog(a)
    bar.addView(ui.button("Close", true) { try { d.dismiss() } catch (_: Exception) {} })
    root.addView(bar, LinearLayout.LayoutParams(-1, -2))
    val col = LinearLayout(a).apply {
      orientation = LinearLayout.VERTICAL
      setPadding(ui.dp(14), 0, ui.dp(18), ui.dp(24))
    }
    build(a, col, header = false)
    root.addView(ScrollView(a).apply { addView(col) }, LinearLayout.LayoutParams(-1, 0, 1f))
    d.requestWindowFeature(Window.FEATURE_NO_TITLE)
    d.setContentView(root)
    d.setOnDismissListener { if (open === d) open = null }
    val w = d.window ?: return
    w.setBackgroundDrawable(ui.rounded(bg, 18, null))
    val dm = a.resources.displayMetrics
    w.setLayout((dm.widthPixels * 0.72f).toInt(), (dm.heightPixels * 0.9f).toInt())
    // keep the driving screen full screen: show without focus, copy its system UI flags, then take focus
    w.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
    d.show()
    w.decorView.systemUiVisibility = a.window.decorView.systemUiVisibility
    w.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
    open = d
  }

  fun build(ctx: Context, col: LinearLayout, header: Boolean = true) {
    val ui = CarUi(ctx, false)
    if (header) col.addView(ui.header("Speed limit warning"))
    col.addView(ui.note("Past the amount for the posted limit, the speed sign flashes red and the sound plays once. A limit " +
      "between two rows uses the next higher row (35 uses the 40 row). It plays again after 10 seconds back under, or when " +
      "the limit drops."))
    val box = LinearLayout(ctx).apply { orientation = LinearLayout.VERTICAL }
    col.addView(box, LinearLayout.LayoutParams(-1, -2))
    fill(ctx, ui, box)
  }

  private fun fill(ctx: Context, ui: CarUi, box: LinearLayout) {
    box.removeAllViews()
    val metric = SpeedAlert.metric(ctx)
    val unit = if (metric) "km/h" else "mph"
    val c = SpeedAlert.conf(ctx, metric)
    fun save(rows: List<SpeedAlert.Row> = c.rows, higher: Int = c.higher, sound: Int = c.sound, times: Int = c.times) {
      SpeedAlert.save(ctx, metric, SpeedAlert.Conf(rows, higher, sound, times))
      box.post { fill(ctx, ui, box) }
    }

    // the list: one row per limit
    val rows = c.rows
    for ((i, r) in rows.withIndex()) {
      val lo = if (i == 0) MIN_LIMIT else rows[i - 1].limit + LIMIT_STEP
      val hi = if (i == rows.size - 1) MAX_LIMIT else rows[i + 1].limit - LIMIT_STEP
      fun with(limit: Int = r.limit, over: Int = r.over) = rows.mapIndexed { k, x -> if (k == i) SpeedAlert.Row(limit, over) else x }
      val row = ui.row("Limit ${r.limit} $unit or less", "Warns at ${r.limit + r.over + 1} $unit and up", 0, null, null).first
      stepper(ctx, ui, row, "${r.limit}", { if (r.limit - LIMIT_STEP >= lo) save(with(limit = r.limit - LIMIT_STEP)) },
        { if (r.limit + LIMIT_STEP <= hi) save(with(limit = r.limit + LIMIT_STEP)) })
      gap(ctx, ui, row, 18)
      stepper(ctx, ui, row, "+${r.over}", { if (r.over > 0) save(with(over = r.over - 1)) },
        { if (r.over < MAX_OVER) save(with(over = r.over + 1)) })
      gap(ctx, ui, row, 18)
      row.addView(ui.stepper("✕") { save(rows.filterIndexed { k, _ -> k != i }) })
      box.addView(row, ui.lp(0, ui.gap))
    }
    val top = rows.lastOrNull()?.limit
    val hrow = ui.row(if (top == null) "Any limit" else "Higher limits",
      (if (top == null) "" else "Above $top $unit: ") + "warns at the limit +${c.higher + 1} $unit and up", 0, null, null).first
    stepper(ctx, ui, hrow, "+${c.higher}", { if (c.higher > 0) save(higher = c.higher - 1) },
      { if (c.higher < MAX_OVER) save(higher = c.higher + 1) })
    box.addView(hrow, ui.lp(0, ui.gap))
    if ((top ?: 0) + LIMIT_STEP <= MAX_LIMIT) {
      val add = ui.button("+  Add a limit", false) {
        val l = ((top ?: 20) + 10).coerceAtMost(MAX_LIMIT)
        save(rows + SpeedAlert.Row(l, c.higher))
      }
      box.addView(add, LinearLayout.LayoutParams(-2, -2).apply { bottomMargin = ui.dp(6); leftMargin = ui.dp(4) })
    }

    // the sound
    box.addView(ui.header("Warning sound"))
    val trow = ui.row("Play it", if (c.sound < 0) "No sound: only the red sign" else
      "${AlertSounds.ALL[c.sound].name}, ${if (c.times == 1) "once" else "${c.times} times in a row"}", 0, null, null).first
    stepper(ctx, ui, trow, "${c.times}x", { if (c.times > 1) save(times = c.times - 1) },
      { if (c.times < MAX_TIMES) save(times = c.times + 1) })
    gap(ctx, ui, trow, 18)
    trow.addView(ui.button("▶  Test", true) { if (c.sound >= 0) AlertSounds.play(ctx, c.sound, c.times) })
    box.addView(trow, ui.lp(0, ui.gap))
    // two columns: "No sound" and every sound; tap = choose it and hear it
    val names = listOf("No sound") + AlertSounds.ALL.map { it.name }
    var line: LinearLayout? = null
    for ((k, name) in names.withIndex()) {
      if (k % 2 == 0) {
        line = LinearLayout(ctx).apply { orientation = LinearLayout.HORIZONTAL }
        box.addView(line, ui.lp(0, ui.gap))
      }
      val idx = k - 1
      val chosen = idx == c.sound
      val cell = TextView(ctx).apply {
        text = if (chosen) "✓  $name" else name
        textSize = 20f
        setTextColor(if (chosen) CarUi.Companion.getACCENT() else CarUi.Companion.getTEXT())
        if (chosen) typeface = Typeface.DEFAULT_BOLD
        gravity = Gravity.CENTER_VERTICAL
        minHeight = ui.dp(64)
        setPadding(ui.dp(20), ui.dp(10), ui.dp(16), ui.dp(10))
        background = if (chosen) ui.rounded(CarUi.Companion.getCARD_ON(), 12, CarUi.Companion.getACCENT()) else ui.rowBg()
        setOnClickListener {
          save(sound = idx)
          if (idx >= 0) AlertSounds.play(ctx, idx, c.times)
        }
      }
      line!!.addView(cell, LinearLayout.LayoutParams(0, -2, 1f).apply { if (k % 2 == 0) rightMargin = ui.dp(8) })
    }
    if (names.size % 2 == 1) line!!.addView(View(ctx), LinearLayout.LayoutParams(0, 1, 1f))
  }

  /** [-] value [+] at the end of a row */
  private fun stepper(ctx: Context, ui: CarUi, row: LinearLayout, value: String, minus: () -> Unit, plus: () -> Unit) {
    row.addView(ui.stepper("−") { minus() })
    val tv = TextView(ctx).apply {
      text = value
      textSize = 22f
      setTextColor(CarUi.Companion.getTEXT())
      typeface = Typeface.DEFAULT_BOLD
      minWidth = ui.dp(70)
      gravity = Gravity.CENTER
    }
    row.addView(tv, LinearLayout.LayoutParams(-2, -2).apply { leftMargin = ui.dp(6); rightMargin = ui.dp(6) })
    row.addView(ui.stepper("+") { plus() })
  }

  private fun gap(ctx: Context, ui: CarUi, row: LinearLayout, dp: Int) = row.addView(View(ctx), LinearLayout.LayoutParams(ui.dp(dp), 1))
}
