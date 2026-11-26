package com.starpilot.starview

import android.content.Context
import android.graphics.Color

/**
 * 2.90.14: the road arrow's settings (the screen editor's "Navigation - road arrow" card, RoadArrowPanel): colour,
 * light / dark, see-through, the running light, and where it shows - kept in SharedPreferences "road_arrow" on the
 * tablet. On / off is the screen layout's (HudLayout sets [on]), so the editor's widget list and layouts take it along.
 */
object RoadArrowStyle {
  /** name, (deep colour at the arrowhead, light colour at the van) */
  val COLORS = listOf(
    "Blue" to (Color.rgb(14, 78, 235) to Color.rgb(45, 165, 255)),
    "Cyan" to (Color.rgb(0, 140, 205) to Color.rgb(90, 225, 255)),
    "Green" to (Color.rgb(20, 150, 80) to Color.rgb(110, 230, 150)),
    "Purple" to (Color.rgb(110, 60, 230) to Color.rgb(190, 150, 255)),
    "White" to (Color.rgb(190, 200, 222) to Color.rgb(255, 255, 255)),
    "Amber" to (Color.rgb(230, 130, 10) to Color.rgb(255, 205, 100)))
  val WHERE = listOf("Scene and camera", "Scene only", "Camera only")

  /** set by HudLayout from the layout's "roadArrow" */
  @JvmField var on = true
  @JvmField var color = 0
  /** -1 darker .. 0 as it comes .. 1 lighter */
  @JvmField var light = 0f
  /** 0.3 faint .. 1 solid */
  @JvmField var opacity = 1f
  @JvmField var running = true
  /** index into [WHERE] */
  @JvmField var where = 0
  private var loaded = false

  val scene get() = on && where != 2
  val camera get() = on && where != 1

  fun load(ctx: Context) {
    if (loaded) return
    loaded = true
    try {
      val p = ctx.applicationContext.getSharedPreferences("road_arrow", 0)
      color = p.getInt("color", 0).coerceIn(0, COLORS.size - 1)
      light = p.getFloat("light", 0f).coerceIn(-1f, 1f); opacity = p.getFloat("opacity", 1f).coerceIn(0.3f, 1f)
      running = p.getBoolean("running", true); where = p.getInt("where", 0).coerceIn(0, WHERE.size - 1)
    } catch (_: Exception) {
    }
  }

  fun save(ctx: Context) {
    try {
      ctx.applicationContext.getSharedPreferences("road_arrow", 0).edit().putInt("color", color)
        .putFloat("light", light).putFloat("opacity", opacity).putBoolean("running", running).putInt("where", where).apply()
    } catch (_: Exception) {
    }
  }

  /** [c] lighter or darker by [light] */
  fun shadeOf(c: Int): Int {
    val l = light
    val t = if (l > 0) Color.WHITE else Color.BLACK
    val k = 0.55f * kotlin.math.abs(l)
    return Color.rgb((Color.red(c) + (Color.red(t) - Color.red(c)) * k).toInt(), (Color.green(c) + (Color.green(t) - Color.green(c)) * k).toInt(),
      (Color.blue(c) + (Color.blue(t) - Color.blue(c)) * k).toInt())
  }

  /** the arrowhead's (deep) colour */
  fun far(): Int = shadeOf(COLORS[color].second.first)
  /** the colour at the van (lighter) */
  fun near(): Int = shadeOf(COLORS[color].second.second)

  fun mix(a: Int, b: Int, k: Float): Int = Color.rgb((Color.red(a) + (Color.red(b) - Color.red(a)) * k).toInt(),
    (Color.green(a) + (Color.green(b) - Color.green(a)) * k).toInt(), (Color.blue(a) + (Color.blue(b) - Color.blue(a)) * k).toInt())
}
