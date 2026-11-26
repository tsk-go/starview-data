package com.starpilot.starview

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import org.json.JSONObject
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import java.io.File
import java.io.FileOutputStream

/**
 * 2.90.14 pictures: the turn card from 500 ft before the destination - the house number big, the street, the town, which
 * side - drawn by the real NavCardView at the owner's card size and the default one. Made-up places. Out: -DpicOut.
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [35], qualifiers = "w1280dp-h800dp-land-xhdpi")
class ArrivalPicTest {
  private val out = System.getProperty("picOut") ?: "/tmp"

  private fun render(v: View, wDp: Int, hDp: Int, name: String) {
    val d = v.resources.displayMetrics.density
    val w = (wDp * d).toInt(); val h = (hDp * d).toInt()
    v.measure(View.MeasureSpec.makeMeasureSpec(w, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(h, View.MeasureSpec.EXACTLY))
    v.layout(0, 0, w, h)
    val b = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
    v.draw(Canvas(b))
    FileOutputStream(File(out, name)).use { b.compress(Bitmap.CompressFormat.PNG, 100, it) }
  }

  @Test fun pictures() {
    val ctx = RuntimeEnvironment.getApplication()
    val field = Nav::class.java.getDeclaredField("destination").apply { isAccessible = true }
    for ((name, dest, m, mod) in listOf(
      Quad("arrive_home", JSONObject().put("name", "Home").put("address", "1525 E 3rd St, Brooklyn, NY 11230, USA"), 30.5, "right"),
      Quad("arrive_address", JSONObject().put("name", "1525 East 3rd Street").put("address", "1525 East 3rd Street, Brooklyn, New York 11230, United States"), 120.0, "right"),
      Quad("arrive_arrived", JSONObject().put("name", "1525 East 3rd Street").put("address", "1525 East 3rd Street, Brooklyn, New York 11230, United States"), 8.0, ""),
      Quad("arrive_business", JSONObject().put("name", "Joe's Pizza").put("address", "Joe's Pizza, 4210 Main St, Queens, NY 11355, USA"), 75.0, "left"),
      Quad("arrive_long", JSONObject().put("name", "2211 Shore Parkway Service Road").put("address", "2211 Shore Parkway Service Road, Brooklyn, NY 11214"), 140.0, "right"),
      Quad("arrive_noNumber", JSONObject().put("name", "JFK Airport").put("address", "Queens, NY 11430, USA"), 90.0, "left"))) {
      field.set(null, dest)
      val nav = JSONObject(NavOverlay.SAMPLE.toString()).put("maneuverType", "arrive").put("maneuverModifier", mod)
        .put("maneuverPrimaryText", "Your destination is on the $mod").put("maneuverDistance", m).put("distanceRemaining", m)
        .put("timeRemaining", m / 6.0).put("valid", true).put("destination", dest.optString("name"))
        .put("nextManeuverType", "").put("nextManeuverModifier", "")
      val t = Telemetry(); t.memParams = mapOf("NavInstructionState" to nav)
      val card = NavCardView(ctx); card.t = t
      render(card, 332, 113, "${name}_owner.png")       // the owner's card size (Nov 19 screenshot)
      render(card, 440, 150, "${name}_default.png")
    }
    field.set(null, null)
  }

  private data class Quad(val a: String, val b: JSONObject, val c: Double, val d: String)
}
