package com.starpilot.starview

import android.app.Activity
import android.app.AlertDialog
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Typeface
import android.text.InputType
import android.util.Log
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import org.json.JSONObject
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicReference

/**
 * 2.89.0: Galaxy's Google navigation (StarPilot's Nov 16 update) on the tablet - the owner: "whatever was added to Galaxy
 * should be added also automatically to the app". All of it lives on the comma (nav_link.py, action nav_google), the same
 * settings Galaxy › Navigation changes:
 *  - Map: Google's map with live traffic. The tablet's map asks the comma for the styles trafficGoogleLight / -Dark
 *    (style(), patched into MapPane.MapCanvas.style()); the comma joins Google's tiles inside upstream's free allowance
 *    and sends Mapbox's traffic map when Google can't. The map's corner credit says Google (attribution()).
 *  - Routes & search: Google's traffic routes and place search for navigation, the tablet's search and its route
 *    previews (Mapbox when Google can't).
 *  - The key: the owner's own Google Cloud key, saved on the comma (never sent back to the tablet), with Galaxy's test.
 * Shared links (shared()): what StarView can't read itself (Waze links...) the comma reads with upstream's share rules.
 * The settings are a section of Tablet › Settings › Navigation map (addTo, at the end of tabletNavPage).
 */
object GoogleMaps {
  private const val TAG = "StarView/GoogleMaps"
  private const val POLL_MS = 60_000L

  @Volatile var map = false
    private set
  @Volatile private var copyright = ""
  @Volatile private var polledAt = 0L
  @Volatile private var polling = false

  /** The tablet's map style: Google's when Galaxy's Map provider is Google (its tiles always carry traffic). */
  @JvmStatic
  fun style(style: String): String {
    poll(false)
    if (!map) return style
    return if (style.endsWith("Dark") || style == "dark") "trafficGoogleDark" else "trafficGoogleLight"
  }

  /** The map's corner credit. */
  @JvmStatic
  fun attribution(mapbox: String): String =
    if (!map) mapbox else "Map data © Google" + if (copyright.isNotBlank() && !copyright.contains("Google")) " · $copyright" else ""

  private fun poll(now: Boolean) {
    val t = System.currentTimeMillis()
    if (polling || (!now && t - polledAt < POLL_MS)) return
    val d = Link.data ?: return
    polling = true
    polledAt = t
    d.request("nav_google", mapOf("op" to "status")) { r -> polling = false; take(r) }
  }

  private fun take(r: JSONObject) {
    if (!r.optBoolean("ok")) return
    map = r.optBoolean("map")
    copyright = r.optString("copyright", "")
  }

  /** From the share thread (MainActivity.shareIntent): StarView's own reading when it found a place, else the comma's. */
  @JvmStatic
  fun shared(text: String?, local: SharedPlace.Place?): SharedPlace.Place? {
    if (text.isNullOrBlank() || (local != null && local.hasCoords)) return local
    val d = Link.data ?: return local
    val out = AtomicReference<JSONObject?>(null)
    val done = CountDownLatch(1)
    try {
      d.request("nav_share_parse", mapOf("text" to text.take(8000))) { r -> out.set(r); done.countDown() }
      done.await(8, TimeUnit.SECONDS)
    } catch (e: Exception) {
      Log.w(TAG, "share", e)
    }
    val r = out.get() ?: return local
    val p = r.optJSONObject("place")
    if (p != null && p.has("latitude") && p.has("longitude")) {
      return SharedPlace.Place(p.optString("name", "Shared destination"), p.optString("address", ""),
        p.optDouble("latitude"), p.optDouble("longitude"), "")
    }
    val q = r.optString("query", "")
    return if (q.isNotBlank() && (local == null || local.query.isBlank())) SharedPlace.Place(local?.name ?: q, local?.address ?: "",
      Double.NaN, Double.NaN, q) else local
  }

  // ---------------------------------------------------------------- settings (Tablet › Settings › Navigation map)
  @JvmStatic
  fun addTo(a: Activity, col: LinearLayout) {
    try {
      val ui = CarUi(a, false)
      col.addView(ui.header("Google maps"), ui.lp(0, ui.gap))
      col.addView(ui.note("The same as Galaxy › Navigation. Google's map shows live traffic on every road; Google's routes " +
        "use live traffic and Google finds places as you type. Mapbox stays the backup: when Google can't (no internet on " +
        "the comma, offline mode on, or this month's free amount used up) Mapbox does it. It needs your own Google Cloud key " +
        "(billing on, with Map Tiles, Routes and Places (New) allowed); StarPilot stops at 99% of each free amount, so you " +
        "aren't charged."), ui.lp(0, ui.gap))
      val box = LinearLayout(a).apply { orientation = LinearLayout.VERTICAL }
      col.addView(box, LinearLayout.LayoutParams(-1, -2))
      box.addView(ui.note("Loading…"))
      request(a, mapOf("op" to "status")) { r -> fill(a, ui, box, r) }
    } catch (e: Exception) {
      Log.w(TAG, "page", e)
    }
  }

  private fun request(a: Activity, args: Map<String, Any?>, cb: (JSONObject) -> Unit) {
    val d = Link.data
    if (d == null) { a.runOnUiThread { cb(JSONObject().put("ok", false).put("error", "Not connected to the comma.")) }; return }
    d.request("nav_google", args) { r -> a.runOnUiThread { if (r.optBoolean("ok")) take(r); cb(r) } }
  }

  private fun fill(a: Activity, ui: CarUi, box: LinearLayout, r: JSONObject) {
    if (a.isFinishing) return
    box.removeAllViews()
    if (!r.optBoolean("ok")) {
      box.addView(ui.note(r.optString("error", "The comma didn't answer.")))
      return
    }
    val hasKey = r.optBoolean("hasKey")
    fun set(patch: JSONObject, then: ((JSONObject) -> Unit)? = null) {
      request(a, mapOf("op" to "set", "set" to patch)) { res ->
        if (!res.optBoolean("ok")) toast(a, res.optString("error", "Couldn't save"))
        if (res.optBoolean("ok")) fill(a, ui, box, res)
        then?.invoke(res)
      }
    }
    fun choice(title: String, desc: String, key: String) {
      val google = r.optString(key) == "google"
      val row = ui.row(title, desc, 0, null, null).first
      row.addView(ui.button("Mapbox", !google) { if (google) set(JSONObject().put(key, "mapbox")) })
      row.addView(android.view.View(a), LinearLayout.LayoutParams(ui.dp(10), 1))
      row.addView(ui.button("Google", google) {
        if (!hasKey) toast(a, "Add your Google key first (below)") else if (!google) set(JSONObject().put(key, "google"))
      })
      box.addView(row, ui.lp(0, ui.gap))
    }
    val failures = r.optJSONObject("failures")
    val mapFail = failures?.optString("map", "")?.takeIf { it.isNotBlank() && it != "null" }
    val routeFail = failures?.optString("routes", "")?.takeIf { it.isNotBlank() && it != "null" }
    choice("Map", if (r.optBoolean("map")) "Google's map with live traffic" + (mapFail?.let { " - now Mapbox: $it" } ?: "")
      else "Mapbox's map (traffic as set under ⚙ on the map)", "mapProvider")
    choice("Routes & search", if (r.optBoolean("routes")) "Google's traffic routes and place search" + (routeFail?.let { " - now Mapbox: $it" } ?: "")
      else "Mapbox's routes and place search", "routeProvider")

    // the key
    val keyRow = ui.row("Google key", if (hasKey) "Saved on the comma" else "None yet - Map and Routes stay on Mapbox", 0, null, null)
    keyRow.first.addView(ui.button(if (hasKey) "Change" else "Add key", !hasKey) { enterKey(a) { k -> set(JSONObject().put("googleKey", k)) { res ->
      if (res.optBoolean("ok")) test(a, ui, box, null) } } })
    if (hasKey) {
      keyRow.first.addView(android.view.View(a), LinearLayout.LayoutParams(ui.dp(10), 1))
      keyRow.first.addView(ui.button("Test", false) { test(a, ui, box, null) })
      keyRow.first.addView(android.view.View(a), LinearLayout.LayoutParams(ui.dp(10), 1))
      keyRow.first.addView(ui.stepper("✕") {
        AlertDialog.Builder(a).setTitle("Remove the Google key?").setMessage("The map, routes and search go back to Mapbox.")
          .setPositiveButton("Remove") { _, _ -> set(JSONObject().put("googleKey", "").put("mapProvider", "mapbox").put("routeProvider", "mapbox")) }
          .setNegativeButton("Cancel", null).show()
      })
    }
    box.addView(keyRow.first, ui.lp(0, ui.gap))

    // this month's use
    val u = r.optJSONObject("usage")
    if (u != null) {
      fun line(name: String, k: String): String? {
        val o = u.optJSONObject(k) ?: return null
        return "$name ${"%,d".format(o.optInt("monthUsed"))} of ${"%,d".format(o.optInt("monthFree"))}"
      }
      val parts = listOfNotNull(line("map tiles", "map"), line("traffic routes", "routes"), line("other routes", "essentials"),
        line("place searches", "places"), line("typing suggestions", "suggestions"))
      box.addView(ui.row("Used this month (free amount)", parts.joinToString(" · "), 0, null, null).first, ui.lp(0, ui.gap))
    }
  }

  private fun test(a: Activity, ui: CarUi, box: LinearLayout, key: String?) {
    toast(a, "Testing the key on Google's map, routes and places…")
    request(a, mapOf("op" to "test") + (key?.let { mapOf("key" to it) } ?: emptyMap())) { r ->
      val t = r.optJSONObject("test")
      if (t == null) { toast(a, r.optString("error", "The test didn't run")); return@request }
      fun said(o: JSONObject?, name: String) = if (o == null) "$name: ?" else "$name: " + (if (o.optBoolean("ok")) "works" else o.optString("message", "refused"))
      AlertDialog.Builder(a).setTitle("Google key test")
        .setMessage(listOf(said(t, "Map"), said(t.optJSONObject("routes"), "Routes"), said(t.optJSONObject("places"), "Places")).joinToString("\n\n"))
        .setPositiveButton("OK", null).show()
      if (r.optBoolean("ok")) fill(a, ui, box, r)
    }
  }

  private fun enterKey(a: Activity, then: (String) -> Unit) {
    val input = EditText(a).apply {
      inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
      hint = "AIza…"
      typeface = Typeface.MONOSPACE
      isSingleLine = true
    }
    try {   // a key copied from the Google Cloud console fills in by itself
      val clip = (a.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).primaryClip
      val text = clip?.getItemAt(0)?.coerceToText(a)?.toString()?.trim().orEmpty()
      if (text.length in 20..128 && text.all { it.isLetterOrDigit() || it == '_' || it == '-' }) input.setText(text)
    } catch (_: Exception) {}
    val pad = (a.resources.displayMetrics.density * 20).toInt()
    val wrap = LinearLayout(a).apply { setPadding(pad, pad / 2, pad, 0); addView(input, LinearLayout.LayoutParams(-1, -2)) }
    AlertDialog.Builder(a).setTitle("Google Maps API key")
      .setMessage("From console.cloud.google.com › APIs & Services › Credentials. It's saved on the comma only.")
      .setView(wrap)
      .setPositiveButton("Save") { _, _ -> input.text.toString().trim().takeIf { it.isNotEmpty() }?.let(then) }
      .setNegativeButton("Cancel", null).show()
  }

  private fun toast(a: Activity, s: String) = android.widget.Toast.makeText(a, s, android.widget.Toast.LENGTH_LONG).show()
}
