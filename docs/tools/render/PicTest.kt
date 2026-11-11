package com.starpilot.starview

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import android.widget.FrameLayout
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
@Config(sdk = [35], qualifiers = "w1472dp-h920dp-land-xhdpi")
class PicTest {
  private val out = System.getProperty("picOut") ?: "/tmp"
  private fun render(v: View, wPx: Int, hPx: Int, name: String) {
    v.measure(View.MeasureSpec.makeMeasureSpec(wPx, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(hPx, View.MeasureSpec.EXACTLY))
    v.layout(0, 0, wPx, hPx)
    val b = Bitmap.createBitmap(wPx, hPx, Bitmap.Config.ARGB_8888)
    v.draw(Canvas(b))
    FileOutputStream(File(out, name)).use { b.compress(Bitmap.CompressFormat.PNG, 100, it) }
  }

  @Test fun maps() {
    val ctx = RuntimeEnvironment.getApplication()
    val d = ctx.resources.displayMetrics.density
    NavOverlay.editing = true
    for ((name, spec) in listOf("tall" to Triple(250, 440, 1f), "wide" to Triple(440, 250, 1f), "zoom" to Triple(250, 440, 1.6f), "out" to Triple(250, 440, 0.64f))) {
      val m = NavMapView(ctx); m.t = Telemetry(); m.zoom = spec.third
      render(m, (spec.first * d).toInt(), (spec.second * d).toInt(), "m_$name.png")
    }
    NavOverlay.editing = false
  }

  @Test fun editor() {
    val ctx = RuntimeEnvironment.getApplication()
    NavOverlay.editing = true
    val root = FrameLayout(ctx)
    val hud = HudLayout(ctx)
    val card = NavCardView(ctx); card.t = Telemetry(); card.id = R.id.navCard; root.addView(card, FrameLayout.LayoutParams((440 * 2), (150 * 2)))
    val map = NavMapView(ctx); map.t = Telemetry(); map.id = R.id.navMap; root.addView(map, FrameLayout.LayoutParams((250 * 2), (440 * 2)))
    val item = hud.items.first { it.key == "navMap" }
    val st = hud.state("navMap"); st.w = 420f; st.h = 300f; st.zoom = 1.25f
    st.x = 0.02f; st.y = 0.42f
    root.measure(View.MeasureSpec.makeMeasureSpec(2944, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(1840, View.MeasureSpec.EXACTLY))
    root.layout(0, 0, 2944, 1840)
    hud.apply(root)
    root.measure(View.MeasureSpec.makeMeasureSpec(2944, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(1840, View.MeasureSpec.EXACTLY))
    root.layout(0, 0, 2944, 1840)
    val e = HudEditor(ctx, root, hud, { kotlin.Unit }, { kotlin.Unit })
    val f = HudEditor::class.java.getDeclaredField("selected"); f.isAccessible = true; f.set(e, item)
    val t = HudEditor::class.java.getDeclaredField("tab"); t.isAccessible = true; t.setInt(e, 1)
    val r = HudEditor::class.java.getDeclaredMethod("refresh"); r.isAccessible = true; r.invoke(e)
    render(e, 2944, 1840, "editor_free.png")
    NavOverlay.editing = false
  }
}
