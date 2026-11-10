package com.starpilot.starview

import android.view.View

/**
 * Screen-edge zones used by Android's own gestures (swipe up from the bottom = home / recent apps, swipe in from the
 * sides = back). A press that starts there is the start of a system gesture, never our long-press.
 */
object EdgeGuard {
  fun inSystemGestureZone(v: View, x: Float, y: Float): Boolean {
    val d = v.resources.displayMetrics.density
    var bottom = 64 * d; var side = 28 * d; var top = 36 * d
    try {
      @Suppress("DEPRECATION")
      v.rootWindowInsets?.systemGestureInsets?.let { g ->
        bottom = maxOf(bottom, g.bottom * 2f); side = maxOf(side, g.left.toFloat(), g.right.toFloat()); top = maxOf(top, g.top.toFloat())
      }
    } catch (_: Throwable) {}
    val loc = IntArray(2); v.getLocationOnScreen(loc)
    val sx = x + loc[0]; val sy = y + loc[1]
    val dm = v.resources.displayMetrics
    return sy > dm.heightPixels - bottom || sy < top || sx < side || sx > dm.widthPixels - side
  }
}
