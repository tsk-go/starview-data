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

/**
 * 2.90.14: the screen editor with "Navigation - road arrow" picked (its own settings: colour, light / dark, see-through,
 * running light, where it shows) and the widget list it sits in. PIC_OUT=dir; files editor_arrow_*.png.
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [35], qualifiers = "w1472dp-h920dp-land-xhdpi")
class RoadArrowPanelPicTest {
  private val out = System.getProperty("picOut") ?: "/tmp"
  private fun render(v: View, wPx: Int, hPx: Int, name: String) {
    v.measure(View.MeasureSpec.makeMeasureSpec(wPx, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(hPx, View.MeasureSpec.EXACTLY))
    v.layout(0, 0, wPx, hPx)
    val b = Bitmap.createBitmap(wPx, hPx, Bitmap.Config.ARGB_8888)
    v.draw(Canvas(b))
    FileOutputStream(File(out, name)).use { b.compress(Bitmap.CompressFormat.PNG, 100, it) }
  }

  private fun swatches(v: View): List<RoadArrowPanel.Swatch> =
    if (v is RoadArrowPanel.Swatch) listOf(v) else if (v is android.view.ViewGroup) (0 until v.childCount).flatMap { swatches(v.getChildAt(it)) } else emptyList()

  private fun scrolls(v: View): List<android.widget.ScrollView> =
    if (v is android.widget.ScrollView) listOf(v) else if (v is android.view.ViewGroup) (0 until v.childCount).flatMap { scrolls(v.getChildAt(it)) } else emptyList()

  private fun editor(tab: Int, name: String, bottom: Boolean = false, style: () -> Unit) {
    val ctx = RuntimeEnvironment.getApplication()
    NavOverlay.editing = true
    val root = FrameLayout(ctx)
    val hud = HudLayout(ctx)
    val card = NavCardView(ctx); card.t = Telemetry(); card.id = R.id.navCard; root.addView(card, FrameLayout.LayoutParams(880, 300))
    root.measure(View.MeasureSpec.makeMeasureSpec(2944, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(1840, View.MeasureSpec.EXACTLY))
    root.layout(0, 0, 2944, 1840)
    hud.apply(root)
    RoadArrowStyle.load(ctx)
    RoadArrowStyle.color = 0; RoadArrowStyle.light = 0f; RoadArrowStyle.opacity = 1f; RoadArrowStyle.running = true; RoadArrowStyle.where = 0
    style()
    val e = HudEditor(ctx, root, hud, { kotlin.Unit }, { kotlin.Unit })
    val f = HudEditor::class.java.getDeclaredField("selected"); f.isAccessible = true; f.set(e, hud.items.first { it.key == "roadArrow" })
    val t = HudEditor::class.java.getDeclaredField("tab"); t.isAccessible = true; t.setInt(e, tab)
    val r = HudEditor::class.java.getDeclaredMethod("refresh"); r.isAccessible = true; r.invoke(e)
    e.measure(View.MeasureSpec.makeMeasureSpec(2944, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(1840, View.MeasureSpec.EXACTLY))
    e.layout(0, 0, 2944, 1840)
    for (s in swatches(e)) s.phase = 0.55
    if (bottom) for (sv in scrolls(e)) sv.scrollTo(0, 100000)
    render(e, 2944, 1840, name)
    NavOverlay.editing = false
  }

  @Test fun panel() {
    editor(1, "editor_arrow.png") {}
    editor(1, "editor_arrow_green_light.png") { RoadArrowStyle.color = 2; RoadArrowStyle.light = 0.5f; RoadArrowStyle.opacity = 0.7f }
    editor(1, "editor_arrow_bottom.png", bottom = true) {}
    editor(1, "editor_arrow_amber_dark.png", bottom = true) { RoadArrowStyle.color = 5; RoadArrowStyle.light = -0.5f; RoadArrowStyle.running = false; RoadArrowStyle.where = 1 }
    editor(0, "editor_arrow_list.png") {}
  }
}
