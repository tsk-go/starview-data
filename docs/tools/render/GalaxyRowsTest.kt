package com.starpilot.starview

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import org.json.JSONArray
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import java.io.File
import java.io.FileOutputStream

/**
 * Galaxy's settings rows as StarPilot's Nov 18 testing update presents them (GalaxyMirror, 2.90.1): the global
 * acceleration / deceleration profile selectors ("selected-profile": "StarPilot Default" shown as "Normal", not offered)
 * and the learned value beside the Developer Parameters sliders. The rows are built the way Galaxy's settings.py
 * _projection sends them. Writes picOut/galaxy_rows.png (the page as the tablet draws it).
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [35], qualifiers = "w1472dp-h920dp-land-xhdpi")
class GalaxyRowsTest {
  private val out = System.getProperty("picOut") ?: "/tmp"
  private val profiles = JSONArray(listOf("StarPilot Default", "Comfort", "Normal", "Sport", "Sport+"))

  private fun profileRow(label: String, value: String, initial: String) = JSONObject()
    .put("label", label).put("value", value).put("choices", profiles).put("available", true)
    .put("reason", "Chooses the curve used by personalities set to Use global preset.")
    .put("defaultValue", initial).put("resetAvailable", true).put("displayScale", "selected-profile")

  private fun slider(label: String, value: String, learned: String) = JSONObject()
    .put("label", label).put("value", value).put("minimum", 0.0).put("maximum", 0.5).put("step", 0.01)
    .put("unit", "s").put("available", true).put("reason", "Delay between a steering command and the car's response.")
    .put("defaultValue", "0.2").put("resetAvailable", true).put("learnedValue", learned)

  @Test fun selectedProfileShowsNormalAndHidesStarPilotDefault() {
    val fresh = GalaxyRow(0, profileRow("Selected Acceleration Profile", "StarPilot Default", "StarPilot Default"))
    assertEquals("Normal", fresh.value)
    assertFalse(fresh.choices.contains("StarPilot Default"))
    assertEquals(GalaxyRow.Control.SELECT, fresh.control)
    assertFalse("fresh: no Default button (Galaxy goes by the saved values)", fresh.showDefault)
    val normal = GalaxyRow(1, profileRow("Selected Acceleration Profile", "Normal", "StarPilot Default"))
    assertEquals("Normal", normal.value)
    assertTrue("Normal chosen against the StarPilot Default default: Default offered, as in Galaxy", normal.showDefault)
    assertEquals("StarPilot Default", normal.defaultValue)
    val sport = GalaxyRow(2, profileRow("Selected Deceleration Profile", "Sport", "Comfort"))
    assertEquals("Sport", sport.value); assertTrue(sport.showDefault)
    val other = GalaxyRow(3, JSONObject().put("label", "x").put("value", "StarPilot Default").put("choices", profiles))
    assertTrue("other rows untouched", other.choices.contains("StarPilot Default"))
    assertEquals("StarPilot Default", other.value)
  }

  @Test fun page() {
    val ctx = RuntimeEnvironment.getApplication()
    val ui = CarUi(ctx, false)
    val v = GalaxySettingsView(ctx, ui, GalaxySettingsView.Root("Settings", null, emptyList(), null), { _, _ -> kotlin.Unit }, null, null)
    val rows = JSONArray()
      .put(profileRow("Selected Acceleration Profile", "StarPilot Default", "StarPilot Default"))
      .put(profileRow("Selected Deceleration Profile", "Sport", "Comfort"))
      .put(slider("Steering Delay", "0.22", "Learned · 0.24 s"))
      .put(slider("Steering Delay (nothing learned yet)", "0.2", "Learned · —"))
      .put(slider("A row without a learned value", "0.2", ""))
    val page = JSONObject().put("title", "Developer Parameters").put("page", "developer").put("rows", rows)
    val draw = GalaxySettingsView::class.java.getDeclaredMethod("draw", JSONObject::class.java); draw.isAccessible = true
    draw.invoke(v, page)
    val w = 1600; val h = 1500
    v.measure(View.MeasureSpec.makeMeasureSpec(w, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(h, View.MeasureSpec.EXACTLY))
    v.layout(0, 0, w, h)
    val b = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
    Canvas(b).drawColor(CarUi.Companion.getBG())
    v.draw(Canvas(b))
    FileOutputStream(File(out, "galaxy_rows.png")).use { b.compress(Bitmap.CompressFormat.PNG, 100, it) }
  }
}
