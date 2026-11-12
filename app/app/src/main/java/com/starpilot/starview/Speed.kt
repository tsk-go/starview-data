package com.starpilot.starview

import android.content.Context
import android.content.SharedPreferences
import kotlin.math.abs

/**
 * 2.86.5: the big speed number as fast as the car really goes. The comma's dash-speed estimate (Toyota: wheel speed x
 * 1.015) showed 59 while the Sienna's dash and the GPS both said 60 - the wheels read a little low (tire size / wear).
 * The ratio GPS speed / wheel speed is learned while driving steadily above 20 mph (one sample per GPS fix with a
 * good position, averaged over minutes, kept across restarts); the number is then the wheel speed (instant, smooth)
 * times that ratio. Before enough samples: the comma's dash estimate as before. The tablet's "use wheel speed"
 * setting still shows the plain wheel speed. Called from 2.84.1's screen tick (app/hybrid/patches.py).
 */
object SpeedShown {
  private const val PREFS = "starview_speedcal"
  private var prefs: SharedPreferences? = null
  @Volatile private var k = Float.NaN          // GPS speed / wheel speed
  private var sum = 0.0; private var n = 0     // the first samples, averaged before k is used
  private var lastFixNs = 0L
  private var savedMs = 0L

  fun init(ctx: Context) {
    if (prefs != null) return
    val p = ctx.applicationContext.getSharedPreferences(PREFS, 0)
    prefs = p
    val s = p.getFloat("k", Float.NaN)
    if (s.isFinite() && s in 0.9f..1.1f) k = s
  }

  /** The speed to show (m/s): shown = what 2.84.1 would show (dash estimate, or wheel speed with "use wheel speed"). */
  @JvmStatic fun mps(t: Telemetry, shown: Float): Float {
    if (t.tBool("use_wheel_speed", false)) return shown
    learn(t)
    val kk = k
    return if (kk.isFinite() && t.vEgo > 0f) t.vEgo * kk else shown
  }

  private fun learn(t: Telemetry) {
    val v = t.vEgo
    if (v < 9f || abs(t.aEgo) > 0.3f) return                 // above 20 mph, not speeding up / slowing down
    val now = System.nanoTime()
    var best: GpsFixInfo? = null
    for (f in t.gpsFixes.values) {
      if (f.hasFix && f.hAcc in 0f..10f && now - f.atNs < 400_000_000L && (best == null || f.atNs > best.atNs)) best = f
    }
    val f = best ?: return
    if (f.atNs == lastFixNs) return                            // one sample per fix
    lastFixNs = f.atNs
    val r = f.speed / v
    if (!r.isFinite() || r < 0.9f || r > 1.1f) return
    if (k.isNaN()) {
      sum += r; n++
      if (n >= 100) k = (sum / n).toFloat()                   // ~10 s of steady driving
    } else {
      k += 0.002f * (r - k)                                    // then follows over several minutes
    }
    val ms = System.currentTimeMillis()
    if (k.isFinite() && ms - savedMs > 30_000L) {
      savedMs = ms
      prefs?.edit()?.putFloat("k", k)?.apply()
    }
  }
}
