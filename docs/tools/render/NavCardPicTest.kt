package com.starpilot.starview

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import org.json.JSONArray
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import java.io.File
import java.io.FileOutputStream

/**
 * 2.90.9 pictures and checks: the turn card with the street big (no "Turn left onto"), "Where to?" turned into "End"
 * while a route is on, and the route list's "via ..." made from a Google route's steps. Out: -DpicOut (/tmp).
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [35], qualifiers = "w1280dp-h800dp-land-xhdpi")
class NavCardPicTest {
  private val out = System.getProperty("picOut") ?: "/tmp"

  private fun render(v: View, wDp: Int, hDp: Int, name: String) {
    val d = v.resources.displayMetrics.density
    val w = (wDp * d).toInt()
    v.measure(View.MeasureSpec.makeMeasureSpec(w, View.MeasureSpec.EXACTLY),
      if (hDp > 0) View.MeasureSpec.makeMeasureSpec((hDp * d).toInt(), View.MeasureSpec.EXACTLY)
      else View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
    v.layout(0, 0, w, v.measuredHeight)
    val b = Bitmap.createBitmap(w, v.measuredHeight, Bitmap.Config.ARGB_8888)
    v.draw(Canvas(b))
    FileOutputStream(File(out, name)).use { b.compress(Bitmap.CompressFormat.PNG, 100, it) }
  }

  @Test fun theStreetWithoutTheWordsTheArrowSays() {
    fun s(t: String) = NavOverlay.street(t)
    assertEquals("86th St" to "", s("Turn left onto 86th St"))
    assertEquals("Belt Pkwy" to "toward Verrazzano Br", s("Merge onto Belt Pkwy toward Verrazzano Br"))
    assertEquals("Exit 11S" to "toward Flatbush Ave", s("Take exit 11S toward Flatbush Ave"))
    assertEquals("Rockaway Blvd" to "toward JFK Airport", s("Take exit 7B onto Rockaway Blvd toward JFK Airport"))
    assertEquals("Belt Pkwy" to "", s("Continue straight to stay on Belt Pkwy"))
    assertEquals("I-278 W" to "", s("Keep left to take I-278 W"))
    assertEquals("Ocean Pkwy" to "", s("Head north on Ocean Pkwy"))
    assertEquals("Keep right at the fork" to "", s("Keep right at the fork"))
    assertEquals("Make a U-turn" to "", s("Make a U-turn"))
  }

  @Test fun theMainRoadsOfAGoogleRoute() {
    val steps = googleSteps().map { Triple("", it.first, it.second) }
    assertEquals("Belt Pkwy · Beach Channel Dr · Seagirt Blvd", NavVia.fromSteps(steps))
    // Mapbox names its steps' roads: those are used as they are
    assertEquals("Belt Parkway · Marine Parkway", NavVia.fromSteps(listOf(Triple("Avenue U", "", 400.0),
      Triple("Belt Parkway", "", 9000.0), Triple("Marine Parkway", "", 1400.0), Triple("", "Arrive", 0.0))))
  }

  private fun googleSteps() = listOf(
    "Head east on Avenue U toward West 7th St" to 450.0, "Turn right onto Stillwell Ave" to 900.0,
    "Use the right lane to merge onto Belt Pkwy" to 9200.0, "Take exit 11S toward Rockaway" to 600.0,
    "Merge onto Flatbush Ave" to 1100.0, "Continue onto Marine Pkwy Bridge" to 1300.0,
    "Turn left onto Beach Channel Dr" to 7800.0, "Continue onto Seagirt Blvd" to 2600.0,
    "Turn left onto Rockaway Tpke" to 1200.0, "Turn right onto Burnside Ave" to 300.0, "Arrive at your destination" to 0.0)

  @Test fun pictures() {
    val ctx = RuntimeEnvironment.getApplication()
    for ((name, text, mod) in listOf(
      Triple("card_86th", "Turn left onto 86th St", "left"),
      Triple("card_exit", "Take exit 7B onto Rockaway Blvd toward JFK Airport", "slightRight"),
      Triple("card_long", "Turn right onto Shore Parkway Service Road", "right"),
      Triple("card_fork", "Keep right at the fork", "slightRight"))) {
      val nav = JSONObject(NavOverlay.SAMPLE.toString()).put("maneuverPrimaryText", text).put("maneuverModifier", mod)
        .put("maneuverType", if (name == "card_exit") "off ramp" else if (name == "card_fork") "fork" else "turn")
        .put("maneuverDistance", 1609.0).put("nextManeuverType", "turn").put("nextManeuverModifier", "right")
        .put("nextManeuverDistance", 1700.0).put("distanceRemaining", 30600.0).put("timeRemaining", 4980.0)
        .put("valid", true).put("destination", "Work")
      val t = Telemetry(); t.memParams = mapOf("NavInstructionState" to nav)
      val card = NavCardView(ctx); card.t = t
      render(card, 332, 113, "${name}_owner.png")       // the owner's card size (Nov 19 screenshot)
      render(card, 440, 150, "${name}_default.png")
    }
    // "Where to?" and "End": the button as MainActivity makes it, set by WhereEnd
    for (route in listOf(false, true)) {
      val b = TextView(ctx)
      val dp = ctx.resources.displayMetrics.density
      b.textSize = 15f; b.setTypeface(null, 1); b.setTextColor(Color.WHITE); b.gravity = Gravity.CENTER
      b.setPadding((18 * dp).toInt(), (10 * dp).toInt(), (18 * dp).toInt(), (10 * dp).toInt())
      b.background = GradientDrawable().apply { cornerRadius = 22 * dp; setColor(Color.argb(150, 20, 22, 28)); setStroke(dp.toInt(), Color.argb(90, 255, 255, 255)) }
      b.text = if (route) WhereEnd.END else WhereEnd.WHERE
      b.measure(0, 0)
      render(b, (b.measuredWidth / dp).toInt() + 1, (b.measuredHeight / dp).toInt(), if (route) "btn_end.png" else "btn_where.png")
    }
    // the route list with a Google route (no summary): the real MapPane.fillRoutes
    val routes = JSONArray().put(JSONObject().put("index", 0).put("durationSeconds", 3540.0).put("distanceMeters", 27350.0)
      .put("via", "").put("classes", JSONArray().put("motorway").put("toll")))
      .put(JSONObject().put("index", 1).put("durationSeconds", 3900.0).put("distanceMeters", 31000.0).put("via", "")
        .put("classes", JSONArray().put("motorway")))
    val stepsA = JSONArray(); var at = 0.0
    for ((txt, m) in googleSteps()) { stepsA.put(JSONObject().put("text", txt).put("type", "turn").put("road", "").put("startMeters", at).put("distanceMeters", m)); at += m }
    val stepsB = JSONArray(); at = 0.0
    for ((txt, m) in listOf("Head east on Avenue U" to 450.0, "Turn left onto Ocean Pkwy" to 3000.0, "Use the right lane to merge onto Belt Pkwy" to 11000.0,
      "Take exit 17S onto Cross Bay Blvd toward Rockaway" to 9500.0, "Turn left onto Beach Channel Dr" to 5200.0, "Turn left onto Rockaway Tpke" to 1500.0,
      "Arrive at your destination" to 0.0)) {
      stepsB.put(JSONObject().put("text", txt).put("type", "turn").put("road", "").put("startMeters", at).put("distanceMeters", m)); at += m
    }
    val line = "_p~iF~ps|U_ulLnnqC_mqNvxq`@"      // a made-up shape: the list only needs the steps
    val j = JSONObject().put("routes", routes).put("details", JSONArray().put(JSONObject().put("polyline", line).put("steps", stepsA))
      .put(JSONObject().put("polyline", line).put("steps", stepsB)))
    val parsed = Nav.INSTANCE.parseRoutes(j)
    val dest = JSONObject().put("name", "Work")
    fun set(field: String, v: Any?) { Nav::class.java.getDeclaredField(field).apply { isAccessible = true }.set(null, v) }
    set("preview", Nav.Preview("pic", dest, parsed.first, parsed.second))
    set("previewing", dest)
    val host = object : MapPane.Host {
      override fun closeMap() {}
      override fun getData(): DataClient = DataClient(Telemetry()) { _, _ -> }
      override fun getNight(): Boolean = true
      override fun getSettings(): UiSettings = UiSettings(ctx)
      override fun getTelemetry(): Telemetry = Telemetry()
      override fun navigateTo(name: String, address: String, lat: Double, lon: Double) {}
      override fun openSearch() {}
    }
    val pane = MapPane(ctx, host)
    val col = LinearLayout(ctx).apply { orientation = LinearLayout.VERTICAL; setBackgroundColor(Color.rgb(31, 36, 44)); setPadding(30, 20, 30, 20) }
    MapPane::class.java.getDeclaredMethod("fillRoutes", LinearLayout::class.java).apply { isAccessible = true }.invoke(pane, col)
    render(col, 1440, 0, "routes.png")
    set("previewing", null); set("preview", null)
  }
}
