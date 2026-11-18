package com.starpilot.starview

import android.util.TypedValue
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import org.json.JSONArray
import org.json.JSONObject
import java.util.WeakHashMap

/**
 * What Galaxy's settings page does to a row before showing it, for what came after the rows were recovered
 * (StarPilot's Nov 17-18 testing update; galaxy-setting-row.js):
 *
 *  - displayScale "selected-profile" (the global acceleration / deceleration profile selectors): "StarPilot Default"
 *    is shown as "Normal" and isn't offered as a choice. The Default button still goes by the saved values (Galaxy's
 *    showDefault reads the raw row there).
 *  - learnedValue (Developer Parameters: steering delay, torque factor / friction / ratio): the learned value, shown
 *    beside the slider's value.
 *
 * Called from GalaxyRow's constructor (the row's JSON goes through [row] before anything reads it) and from
 * GalaxySettingsView.sliderRow (the slider's header, [learned]). Galaxy's other new row field, acceptedFindingValue,
 * only marks the FLM page's manual coordinates, which StarView doesn't draw.
 */
object GalaxyMirror {
  private const val PROFILE_DEFAULT = "StarPilot Default"
  private const val PROFILE_SHOWN = "Normal"
  private val learnedOf = WeakHashMap<GalaxyRow, String>()

  /** the row's JSON as Galaxy presents it (a copy when it changes anything); remembers [r]'s learned value */
  @JvmStatic
  fun row(r: GalaxyRow, o: JSONObject): JSONObject {
    try {
      val learned = o.optString("learnedValue", "")
      synchronized(learnedOf) { if (learned.isNotEmpty() && learned != "null") learnedOf[r] = learned else learnedOf.remove(r) }
      if (o.optString("displayScale", "") != "selected-profile") return o
      val c = JSONObject(o.toString())
      val value = o.optString("value", "")
      val initial = if (o.isNull("defaultValue")) null else o.optString("defaultValue", "")
      fun shown(v: String) = if (v == PROFILE_DEFAULT) PROFILE_SHOWN else v
      c.put("value", shown(value))
      // StarView shows Default when the shown value and the shown default differ; Galaxy goes by the saved ones. So
      // the default is shown like the value when the two are the same, and kept as saved (what Default then saves)
      // when they aren't - "Normal" chosen against a "StarPilot Default" default still offers Default.
      if (initial != null) c.put("defaultValue", if (initial == value) shown(value) else initial)
      val choices = o.optJSONArray("choices")
      if (choices != null) {
        val kept = JSONArray()
        for (i in 0 until choices.length()) { val ch = choices.opt(i)?.toString() ?: continue; if (ch != PROFILE_DEFAULT) kept.put(ch) }
        c.put("choices", kept)
      }
      return c
    } catch (e: Exception) {
      return o
    }
  }

  /** the learned value beside a slider's value (Galaxy's gx-setting-learned: small, secondary text) */
  @JvmStatic
  fun learned(header: LinearLayout, r: GalaxyRow) {
    val text = synchronized(learnedOf) { learnedOf[r] } ?: return
    try {
      val d = header.context.resources.displayMetrics.density
      val tv = TextView(header.context)
      tv.text = text
      tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
      tv.setTextColor(CarUi.Companion.getDIM())
      tv.gravity = Gravity.END or Gravity.CENTER_VERTICAL
      tv.setPadding((8 * d).toInt(), 0, (4 * d).toInt(), 0)
      tv.maxLines = 2
      header.addView(tv, LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT))
    } catch (e: Exception) {
    }
  }
}
