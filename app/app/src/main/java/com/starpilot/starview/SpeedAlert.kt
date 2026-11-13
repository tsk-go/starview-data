package com.starpilot.starview

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import kotlin.math.roundToInt

/**
 * 2.88.0: the speed limit warning. How far over the posted limit is fine is the owner's own list (owner: "when I drive
 * 30 mph it should only alert me plus five or 40 plus 7, I want to be able to add my own limits"): rows of (limit,
 * amount); a limit uses the first row at or above it (2.88.2: above every row, the last row; no rows, no warning - the
 * "Higher limits" line is gone, owner: "just remove the line higher limits"). Past it the speed
 * sign flashes red (SpeedSignView) and the chosen sound (AlertSounds) plays once, [Conf.times] in a row. It plays again
 * only after the speed was back under for WAIT_UNDER_MS, or when the limit drops to a lower one. Edited in Settings >
 * Widgets (SpeedAlertPage); kept in SharedPreferences "speed_alert", per unit (mph / km/h).
 * Before 2.88.0 it was a fixed 9 mph (15 km/h) and no sound.
 * 2.88.2 (owner: "there should always be a sign ... if there is zero from map and vision, a minimum of 20"): while no
 * limit is known yet (none read this drive) the sign shows Conf.fallback (20 mph / 30 km/h; 0 = no sign) and the
 * warning uses it too. A limit read once stays for the drive (SpeedSignView.postedMps), so this is the drive's start.
 */
object SpeedAlert {
  private const val TAG = "StarView/SpeedAlert"
  private const val PREFS = "speed_alert"
  const val WAIT_UNDER_MS = 10_000L
  /** "back under" means this much below the warning speed */
  const val UNDER_BY = 2
  /** never two warnings closer than this */
  const val MIN_GAP_MS = 5_000L
  /** "how far over is fine" with no rows at all: no warning */
  const val NEVER = 10_000

  class Row(val limit: Int, val over: Int)
  /** [fallback]: 2.88.2, the limit used while none is known (0 = no sign then) */
  class Conf(val rows: List<Row>, val sound: Int, val times: Int, val fallback: Int) {
    /** how far over [limit] is fine */
    fun allowed(limit: Int): Int = rows.firstOrNull { limit <= it.limit }?.over ?: rows.lastOrNull()?.over ?: NEVER
  }

  fun defaults(metric: Boolean) =
    if (metric) Conf(listOf(Row(50, 8), Row(70, 10), Row(110, 15)), 5, 3, 30)
    else Conf(listOf(Row(30, 5), Row(40, 7), Row(65, 9)), 5, 3, 20)

  @Volatile private var conf: Conf? = null
  @Volatile private var confMetric = false
  @Volatile private var prefs: SharedPreferences? = null

  private fun prefs(ctx: Context): SharedPreferences =
    prefs ?: ctx.applicationContext.getSharedPreferences(PREFS, 0).also { prefs = it }

  /** the units the comma uses, as last seen while driving (the settings page has no Telemetry) */
  fun metric(ctx: Context): Boolean = prefs(ctx).getBoolean("metric", false)

  fun conf(ctx: Context, metric: Boolean): Conf {
    val c = conf
    if (c != null && confMetric == metric) return c
    val p = prefs(ctx)
    val d = defaults(metric)
    val u = if (metric) "kph" else "mph"
    var rows = p.getString("rows_$u", null)?.let { s ->
      s.split(',').mapNotNull { r -> r.split(':').takeIf { it.size == 2 }?.let { Row(it[0].toIntOrNull() ?: return@let null, it[1].toIntOrNull() ?: return@let null) } }
    }?.sortedBy { it.limit } ?: d.rows
    val out = Conf(rows, p.getInt("sound", d.sound), p.getInt("times", d.times), p.getInt("fallback_$u", d.fallback))
    // 2.88.2: a "Higher limits" amount saved by 2.88.0 / 2.88.1 becomes a row of its own (65 mph / 110 km/h, or 5 above
    // the last row), so what warns doesn't change
    if (p.contains("higher_$u")) {
      val h = p.getInt("higher_$u", 0)
      val last = rows.lastOrNull()
      if (last != null && last.over != h) {
        rows = rows + Row(maxOf(last.limit + 5, if (metric) 110 else 65), h)
        save(ctx, metric, Conf(rows, out.sound, out.times, out.fallback))
      }
      p.edit().remove("higher_$u").apply()
      return conf(ctx, metric)
    }
    conf = out; confMetric = metric
    return out
  }

  fun save(ctx: Context, metric: Boolean, c: Conf) {
    val u = if (metric) "kph" else "mph"
    prefs(ctx).edit()
      .putString("rows_$u", c.rows.sortedBy { it.limit }.joinToString(",") { "${it.limit}:${it.over}" })
      .putInt("sound", c.sound).putInt("times", c.times).putInt("fallback_$u", c.fallback).apply()
    conf = Conf(c.rows.sortedBy { it.limit }, c.sound, c.times, c.fallback); confMetric = metric
  }

  /** the limit shown while none is known, in the shown unit (0 = none: the sign hides) */
  fun fallback(ctx: Context, metric: Boolean): Int = conf(ctx, metric).fallback

  /** how far over the shown limit is fine, in the shown unit (the speed sign's flash; 9 / 15 before any settings) */
  fun allowed(limit: Int, metric: Boolean): Int {
    val c = conf
    return if (c != null && confMetric == metric) c.allowed(limit) else defaults(metric).allowed(limit)
  }

  /** When to sound: once per time over; again after WAIT_UNDER_MS back under, or when the limit drops. */
  class Alarm {
    private var armed = true
    private var lastLimit = -1
    private var underSince = 0L
    private var lastPlay = 0L

    fun reset() { armed = true; lastLimit = -1; underSince = 0L }

    /** [speed] and [limit] as shown (whole mph or km/h); true = play now */
    fun step(limit: Int, speed: Int, allowed: Int, now: Long): Boolean {
      if (lastLimit >= 0 && limit < lastLimit) armed = true          // a lower limit: say so again
      lastLimit = limit
      if (speed > limit + allowed) {
        underSince = 0L
        if (armed && now - lastPlay >= MIN_GAP_MS) { armed = false; lastPlay = now; return true }
        armed = false
        return false
      }
      if (!armed) {
        if (speed <= limit + allowed - UNDER_BY) {
          if (underSince == 0L) underSince = now
          if (now - underSince >= WAIT_UNDER_MS) { armed = true; underSince = 0L }
        } else underSince = 0L
      }
      return false
    }
  }

  private val alarm = Alarm()
  private var failAt = 0L

  /** From the screen tick (MainActivity.hybSignTick), whether the speed sign is shown or not. */
  @JvmStatic
  fun tick(ctx: Context, t: Telemetry) {
    try {
      if (!t.started) { alarm.reset(); return }
      val metric = t.isMetric
      if (metric != prefs(ctx).getBoolean("metric", false)) prefs(ctx).edit().putBoolean("metric", metric).apply()
      val c = conf(ctx, metric)
      val shown = SpeedSignView.shownLimit(t, c.fallback)
      if (shown <= 0) return
      val k = if (metric) 3.6f else 2.2369363f
      val speed = (SpeedSignView.speedMps(t) * k).roundToInt()
      if (alarm.step(shown, speed, c.allowed(shown), System.currentTimeMillis()) && c.sound >= 0)
        AlertSounds.play(ctx, c.sound, c.times)
    } catch (e: Exception) {
      val now = System.currentTimeMillis()
      if (now - failAt > 10_000) { failAt = now; Log.w(TAG, "tick", e) }
    }
  }
}
