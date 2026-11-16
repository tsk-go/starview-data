package com.starpilot.starview

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import android.widget.LinearLayout
import org.json.JSONObject
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import java.io.File
import java.io.FileOutputStream

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [35], qualifiers = "w1472dp-h920dp-land-xhdpi")
class GooglePicTest {
  private val out = System.getProperty("picOut") ?: "/tmp"

  private fun status(on: Boolean, key: Boolean) = JSONObject("""{"ok":true,"mapProvider":"${if (on) "google" else "mapbox"}",
    "routeProvider":"${if (on) "google" else "mapbox"}","hasKey":$key,"map":$on,"routes":$on,"copyright":"",
    "failures":{"map":null,"routes":null},
    "usage":{"map":{"monthUsed":1240,"monthFree":100000},"routes":{"monthUsed":31,"monthFree":5000},
      "essentials":{"monthUsed":4,"monthFree":10000},"places":{"monthUsed":12,"monthFree":5000},
      "suggestions":{"monthUsed":210,"monthFree":10000}}}""")

  private fun shot(name: String, r: JSONObject) {
    val a = Robolectric.buildActivity(Activity::class.java).setup().get()
    val ui = CarUi(a, false)
    val col = LinearLayout(a).apply { orientation = LinearLayout.VERTICAL; setBackgroundColor(CarUi.Companion.getBG()); setPadding(ui.dp(10), ui.dp(4), ui.dp(24), ui.dp(20)) }
    GoogleMaps.addTo(a, col)
    val box = col.getChildAt(col.childCount - 1) as LinearLayout
    val fill = GoogleMaps::class.java.getDeclaredMethod("fill", Activity::class.java, CarUi::class.java, LinearLayout::class.java, JSONObject::class.java)
    fill.isAccessible = true
    fill.invoke(GoogleMaps, a, ui, box, r)
    val w = 1100 * 2
    col.measure(View.MeasureSpec.makeMeasureSpec(w, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
    col.layout(0, 0, w, col.measuredHeight)
    val b = Bitmap.createBitmap(w, col.measuredHeight, Bitmap.Config.ARGB_8888)
    col.draw(Canvas(b))
    FileOutputStream(File(out, name)).use { b.compress(Bitmap.CompressFormat.PNG, 100, it) }
  }

  @Test fun pages() {
    shot("google_off.png", status(false, false))
    shot("google_on.png", status(true, true))
  }
}
