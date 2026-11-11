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

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [35], qualifiers = "w1280dp-h800dp-land-xhdpi")
class NavRenderTest {
  private fun render(v: View, wDp: Int, hDp: Int, out: String) {
    val d = v.resources.displayMetrics.density
    val w = (wDp * d).toInt(); val h = (hDp * d).toInt()
    v.measure(View.MeasureSpec.makeMeasureSpec(w, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(h, View.MeasureSpec.EXACTLY))
    v.layout(0, 0, w, h)
    val b = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
    v.draw(Canvas(b))
    FileOutputStream(File(System.getProperty("navRenderOut") ?: "/tmp", out)).use { b.compress(Bitmap.CompressFormat.PNG, 100, it) }
  }

  @Test fun picture() {
    val ctx = RuntimeEnvironment.getApplication()
    val nav = JSONObject(NavOverlay.SAMPLE.toString()).put("maneuverPrimaryText", "Turn right onto Avenue R").put("maneuverDistance", 380.0).put("valid", true)
    val t = Telemetry(); t.memParams = mapOf("NavInstructionState" to nav)
    val card = NavCardView(ctx); card.t = t; render(card, 440, 150, "pic_card.png")
    NavOverlay.editing = true
    val m = NavMapView(ctx); m.t = Telemetry(); render(m, 440, 250, "pic_map.png")
    NavOverlay.editing = false
  }
}
