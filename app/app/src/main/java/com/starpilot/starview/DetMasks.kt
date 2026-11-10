package com.starpilot.starview

import android.content.Context
import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.min

/**
 * Detector masks (2.24.0): parts of each camera picture the detector must ignore - our own hood, the dash, the mirror
 * mount, reflections in the windshield. Polygons in frame fractions (0..1, x0,y0,x1,y1,...), one list per camera, saved
 * with the tablet settings. The detector paints them YOLOX's padding grey before every run, so nothing is found there.
 */
object DetMasks {
  @Volatile var version = 0; private set
  private val polys = arrayOf(ArrayList<FloatArray>(), ArrayList<FloatArray>())   // [road, wide]
  private val keep = BooleanArray(2)   // 2.24.1, per camera: false = ignore inside the areas, true = only look inside them
  private var prefs: android.content.SharedPreferences? = null
  private fun i(wide: Boolean) = if (wide) 1 else 0
  private fun key(wide: Boolean) = if (wide) "det_mask_wide" else "det_mask_road"

  @Synchronized fun init(ctx: Context) {
    if (prefs != null) return
    val p = ctx.applicationContext.getSharedPreferences("starview_ui", Context.MODE_PRIVATE); prefs = p
    for (w in booleanArrayOf(false, true)) { polys[i(w)].addAll(decode(p.getString(key(w), "") ?: "")); keep[i(w)] = p.getBoolean(key(w) + "_keep", false) }
    version++
  }

  @Synchronized fun get(wide: Boolean): List<FloatArray> = polys[i(wide)].map { it.copyOf() }
  @Synchronized fun set(wide: Boolean, list: List<FloatArray>) {
    val p = polys[i(wide)]; p.clear(); p.addAll(list.filter { it.size >= 6 }.map { it.copyOf() })
    prefs?.edit()?.putString(key(wide), encode(p))?.apply(); version++
  }

  @Synchronized fun keep(wide: Boolean) = keep[i(wide)]
  @Synchronized fun setKeep(wide: Boolean, v: Boolean) { keep[i(wide)] = v; prefs?.edit()?.putBoolean(key(wide) + "_keep", v)?.apply(); version++ }
  /** What the detector must not see, at model-input size: the areas, or (keep mode) everything outside them. */
  fun maskFor(wide: Boolean, w: Int, h: Int): BooleanArray? {
    val (l, k) = synchronized(this) { get(wide) to keep[i(wide)] }
    val m = raster(l, w, h) ?: return null
    if (k) for (j in m.indices) m[j] = !m[j]
    return m
  }

  fun encode(l: List<FloatArray>) = l.joinToString("|") { p -> p.joinToString(",") { String.format(java.util.Locale.US, "%.4f", it) } }
  fun decode(s: String): List<FloatArray> =
    s.split('|').mapNotNull { p -> p.split(',').mapNotNull { it.toFloatOrNull() }.takeIf { it.size >= 6 && it.size % 2 == 0 }?.toFloatArray() }

  /** Pixels of a w x h picture inside any polygon (pixel centres, even-odd per polygon, union of polygons); null = none. */
  fun raster(list: List<FloatArray>, w: Int, h: Int): BooleanArray? {
    if (list.isEmpty() || w <= 0 || h <= 0) return null
    val m = BooleanArray(w * h); val xs = FloatArray(256)
    for (p in list) {
      val n = p.size / 2
      for (y in 0 until h) {
        val fy = (y + 0.5f) / h; var k = 0
        for (a in 0 until n) {
          val b = (a + 1) % n; val ay = p[2 * a + 1]; val by = p[2 * b + 1]
          if ((ay <= fy) != (by <= fy) && k < xs.size) xs[k++] = (p[2 * a] + (fy - ay) / (by - ay) * (p[2 * b] - p[2 * a])) * w
        }
        xs.sort(0, k)
        var j = 0
        while (j + 1 < k) {
          val x0 = max(0, ceil(xs[j] - 0.5f).toInt()); val x1 = min(w - 1, ceil(xs[j + 1] - 0.5f).toInt() - 1)
          for (x in x0..x1) m[y * w + x] = true
          j += 2
        }
      }
    }
    return m
  }

  /** Point (frame fractions) inside polygon p (even-odd). */
  fun inside(p: FloatArray, fx: Float, fy: Float): Boolean {
    val n = p.size / 2; var c = false
    for (a in 0 until n) {
      val b = (a + n - 1) % n; val ay = p[2 * a + 1]; val by = p[2 * b + 1]
      if ((ay > fy) != (by > fy) && fx < (p[2 * b] - p[2 * a]) * (fy - ay) / (by - ay) + p[2 * a]) c = !c
    }
    return c
  }
}
