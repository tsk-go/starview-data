package com.starpilot.starview

import android.app.Activity
import android.content.res.ColorStateList
import android.util.Log
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import org.json.JSONObject

/**
 * Galaxy › Navigation › "Turn slowdown distance" (StarPilot's Nov 24 testing update), on StarView's Tablet › Settings ›
 * Navigation map page: how early the comma starts slowing for a turn on the route, 0-100 % (50 = StarPilot's own).
 * Like Galaxy's: a slider, Default and Save; saved only while parked, used from the next drive. The comma reads it with
 * nav_state's options and sets it through nav_options (upstream's own check; refused while driving, in Galaxy's words).
 * Shown above the Google maps section (GoogleMaps.addTo calls [addTo] first: no new patch of the shipped code).
 */
object TurnSlowdown {
  private const val TAG = "StarView/TurnSlowdown"
  const val DEFAULT = 50

  fun addTo(a: Activity, col: LinearLayout) {
    try {
      val ui = CarUi(a, false)
      col.addView(ui.header("Turn slowdown distance"), ui.lp(0, ui.gap))
      col.addView(ui.note("The same as Galaxy › Navigation. How early the comma starts slowing down for a turn on your " +
        "route: lower starts later, higher starts earlier (50% is StarPilot's own). Save while parked; it applies from " +
        "the next drive."), ui.lp(0, ui.gap))
      val box = LinearLayout(a).apply { orientation = LinearLayout.VERTICAL }
      col.addView(box, LinearLayout.LayoutParams(-1, -2))
      box.addView(ui.note("Loading…"))
      val d = Link.data
      if (d == null) {
        box.removeAllViews(); box.addView(ui.note("Not connected to the comma."))
        return
      }
      d.request("nav_state", emptyMap<String, Any?>()) { r ->
        a.runOnUiThread { fill(a, ui, box, r) }
      }
    } catch (e: Exception) {
      Log.w(TAG, "page", e)
    }
  }

  /** the saved percent from nav_state / nav_options' answer, or null when the comma doesn't have the setting */
  fun percent(r: JSONObject): Int? {
    val o = r.optJSONObject("options") ?: return null
    if (!o.has("turnSlowdownPercent")) return null
    val p = o.optInt("turnSlowdownPercent", -1)
    return if (p in 0..100) p else null
  }

  private fun fill(a: Activity, ui: CarUi, box: LinearLayout, r: JSONObject) {
    if (a.isFinishing) return
    box.removeAllViews()
    val saved = if (r.optBoolean("ok")) percent(r) else null
    if (saved == null) {
      box.addView(ui.note(if (r.optBoolean("ok")) "This comma's StarPilot doesn't have it yet (it came with the Nov 24 update)."
                          else r.optString("error", "The comma didn't answer.")))
      return
    }
    var draft = saved
    val (row, _) = ui.row("Turn slowdown distance", "Lower starts later; higher starts earlier.", 0, null, null)
    val value = TextView(a).apply {
      textSize = 18f
      setTextColor(CarUi.Companion.getTEXT())
      setPadding(ui.dp(14), 0, ui.dp(6), 0)
      text = "$draft%"
    }
    row.addView(value)
    box.addView(row, ui.lp(0, 0))
    val bar = SeekBar(a).apply {
      max = 100
      progress = draft
      minimumHeight = ui.dp(48)
      thumbTintList = ColorStateList.valueOf(-1)
      progressTintList = ColorStateList.valueOf(CarUi.Companion.getACCENT())
    }
    box.addView(bar, LinearLayout.LayoutParams(-1, -2))
    val buttons = LinearLayout(a).apply { orientation = LinearLayout.HORIZONTAL }
    lateinit var save: TextView
    lateinit var reset: TextView
    fun refresh() {
      value.text = "$draft%"
      save.isEnabled = draft != saved
      save.alpha = if (draft != saved) 1f else 0.45f
      reset.isEnabled = draft != DEFAULT
      reset.alpha = if (draft != DEFAULT) 1f else 0.45f
    }
    reset = ui.button("Default", false) { draft = DEFAULT; bar.progress = DEFAULT; refresh() }
    save = ui.button("Save", true) {
      val d = Link.data
      if (d == null) {
        toast(a, "Not connected to the comma.")
      } else {
        save.isEnabled = false
        d.request("nav_options", mapOf("options" to JSONObject().put("turnSlowdownPercent", draft))) { res ->
          a.runOnUiThread {
            if (!res.optBoolean("ok")) toast(a, res.optString("error", "Couldn't save"))
            fill(a, ui, box, if (res.optBoolean("ok")) res else JSONObject().put("ok", true).put("options",
              JSONObject().put("turnSlowdownPercent", saved)))
          }
        }
      }
    }
    buttons.addView(reset)
    buttons.addView(android.view.View(a), LinearLayout.LayoutParams(ui.dp(10), 1))
    buttons.addView(save)
    box.addView(buttons, ui.lp(0, ui.gap))
    bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
      override fun onProgressChanged(sb: SeekBar, p: Int, fromUser: Boolean) { if (fromUser) { draft = p; refresh() } }
      override fun onStartTrackingTouch(sb: SeekBar) {}
      override fun onStopTrackingTouch(sb: SeekBar) {}
    })
    refresh()
  }

  private fun toast(a: Activity, text: String) = Toast.makeText(a, text, Toast.LENGTH_LONG).show()
}
