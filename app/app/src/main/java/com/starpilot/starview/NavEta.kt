package com.starpilot.starview

import kotlin.math.abs
import kotlin.math.max

/**
 * 2.89.4: one arrival time. The map's bottom line ("7:51 PM  23 min · 7.4 mi") came from the tablet's own copy of the
 * route: the time the route had when it was fetched, scaled by the distance left. The drive screen's turn card shows
 * the comma's remaining time, which the comma keeps up with live traffic (its route's time scaled by Google's latest
 * traffic when a Google key is set, Mapbox's traffic refresh every 5 minutes otherwise) - so in slow traffic the two
 * disagreed (owner, Nov 16: 23 min on the map, 31 min and 7:59 PM on the card). The map now takes the comma's numbers
 * too (patch in MapPane.refreshBars, right after Nav.remaining()), and keeps its own when the comma has no live
 * instruction or is clearly on another trip.
 */
object NavEta {
  @JvmStatic
  fun remaining(own: Pair<Double, Double>?): Pair<Double, Double>? {
    if (own == null) return null
    val nav = try { Link.telemetry?.navState() } catch (_: Exception) { null } ?: return own
    val s = nav.optDouble("timeRemaining", Double.NaN)
    val m = nav.optDouble("distanceRemaining", Double.NaN)
    if (!s.isFinite() || s <= 0.0 || !m.isFinite() || m <= 0.0) return own
    // the same trip: the comma's distance left within a mile, or a fifth, of the tablet's
    if (abs(m - own.second) > max(1600.0, own.second * 0.2)) return own
    return Pair(s, m)
  }
}
