package com.starpilot.starview

import android.content.Context
import android.content.res.Configuration
import kotlin.math.max

/**
 * 2.30.1: settings-type screens in a narrow window (split screen next to Waze, portrait). Their layouts are made for
 * the full landscape tablet; at half width the content column got ~350 dp and words broke letter by letter. In a
 * window narrower than [targetDp], the activity runs at a lower density so it still has [targetDp] of width: the same
 * arrangement as full screen, drawn smaller (never below [minScale]). The activity is recreated when the window size
 * changes (no configChanges for these), so this is re-evaluated each time.
 */
object NarrowDensity {
  fun wrap(base: Context, targetDp: Int = 1000, minScale: Float = 0.62f): Context {
    val cfg = Configuration(base.resources.configuration)
    val w = cfg.screenWidthDp
    if (w <= 0 || w >= targetDp || cfg.densityDpi <= 0) return base
    val k = max(minScale, w / targetDp.toFloat())
    cfg.densityDpi = (cfg.densityDpi * k).toInt().coerceAtLeast(80)
    cfg.screenWidthDp = (w / k).toInt()
    cfg.screenHeightDp = (cfg.screenHeightDp / k).toInt()
    cfg.smallestScreenWidthDp = (cfg.smallestScreenWidthDp / k).toInt()
    return base.createConfigurationContext(cfg)
  }
}
