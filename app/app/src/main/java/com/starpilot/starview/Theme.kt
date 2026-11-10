package com.starpilot.starview

import kotlin.math.PI
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sin

/** Day/night from sun elevation (NOAA-style approximation). Night below civil twilight (-6°). */
object Theme {
  fun sunElevationDeg(latDeg: Double, lonDeg: Double, epochMs: Long): Double {
    val jd = epochMs / 86400000.0 + 2440587.5
    val d = jd - 2451545.0
    val g = Math.toRadians((357.529 + 0.98560028 * d) % 360.0)
    val q = (280.459 + 0.98564736 * d) % 360.0
    val l = Math.toRadians((q + 1.915 * sin(g) + 0.020 * sin(2 * g)) % 360.0)
    val e = Math.toRadians(23.439 - 0.00000036 * d)
    val ra = Math.toDegrees(kotlin.math.atan2(cos(e) * sin(l), cos(l)))
    val dec = asin(sin(e) * sin(l))
    val gmst = (18.697374558 + 24.06570982441908 * d) % 24.0
    val lst = (gmst * 15.0 + lonDeg) % 360.0
    var ha = Math.toRadians(((lst - ra) % 360.0 + 540.0) % 360.0 - 180.0)
    val lat = Math.toRadians(latDeg)
    val el = asin(sin(lat) * sin(dec) + cos(lat) * cos(dec) * cos(ha))
    return Math.toDegrees(el)
  }

  fun isNight(latDeg: Double, lonDeg: Double, epochMs: Long = System.currentTimeMillis()): Boolean =
    sunElevationDeg(latDeg, lonDeg, epochMs) < -6.0
}
