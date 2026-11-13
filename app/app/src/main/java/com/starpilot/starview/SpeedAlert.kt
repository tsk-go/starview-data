package com.starpilot.starview

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import kotlin.math.roundToInt

/**
 * 2.88.0: the speed limit warning. How far over the posted limit is fine is the owner's own list (owner: "when I drive
 * 30 mph it should only alert me plus five or 40 plus 7, I want to be able to add my own limits"): rows of (limit,
 * amount); a limit uses the first row at or above it, a limit above every row the "higher" amount. Past it the speed
 * sign flashes red (SpeedSignView) and the chosen sound (AlertSounds) plays once, [Conf.times] in a row. It plays again
 * only after the speed was back under for WAIT_UNDER_MS, or when the limit drops to a lower one. Edited in Settings >
 * Widgets (SpeedAlertPage); kept in SharedPreferences "speed_alert", per unit (mph / km/h).
 * Before 2.88.0 it was a fixed 9 mph (15 km/h) and no sound.
 */
object SpeedAlert {
  private const val TAG = "StarView/SpeedAlert"
  private const val PREFS = "speed_alert"
  const val WAIT_UNDER_MS = 10_000L
  /** "back under" means this much below the warning speed */
  const val UNDER_BY = 2
  /** never two warnings closer than this */
  const val MIN_GAP_MS = 5_000L

  class Row(val limit: Int, val over: Int)
  class Conf(val rows: List<Row>, val higher: Int, val sound: Int, val times: Int) {
    /** how far over [limit] is fine */
    fun allowed(limit: Int): Int = rows.firstOrNull { limit <= it.limit }?.over ?: higher
  }

  fun defaults(metric: Boolean) =
    if (metric) Conf(listOf(Row(50, 8), Row(70, 10)), 15, 5, 3) else Conf(listOf(Row(30, 5), Row(40, 7)), 9, 5, 3)

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
    val rows = p.getString("rows_$u", null)?.let { s ->
      s.split(',').mapNotNull { r -> r.split(':').takeIf { it.size == 2 }?.let { Row(it[0].toIntOrNull() ?: return@let null, it[1].toIntOrNull() ?: return@let null) } }
    } ?: d.rows
    val out = Conf(rows.sortedBy { it.limit }, p.getInt("higher_$u", d.higher), p.getInt("sound", d.sound), p.getInt("times", d.times))
    conf = out; confMetric = metric
    return out
  }

  fun save(ctx: Context, metric: Boolean, c: Conf) {
    val u = if (metric) "kph" else "mph"
    prefs(ctx).edit()
      .putString("rows_$u", c.rows.sortedBy { it.limit }.joinToString(",") { "${it.limit}:${it.over}" })
      .putInt("higher_$u", c.higher).putInt("sound", c.sound).putInt("times", c.times).apply()
    conf = Conf(c.rows.sortedBy { it.limit }, c.higher, c.sound, c.times); confMetric = metric
  }

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
      val lim = SpeedSignView.postedMps(t)
      if (lim.isNaN()) return
      val k = if (metric) 3.6f else 2.2369363f
      val shown = (lim * k).roundToInt()
      val speed = (SpeedSignView.speedMps(t) * k).roundToInt()
      val c = conf(ctx, metric)
      if (alarm.step(shown, speed, c.allowed(shown), System.currentTimeMillis()) && c.sound >= 0)
        AlertSounds.play(ctx, c.sound, c.times)
    } catch (e: Exception) {
      val now = System.currentTimeMillis()
      if (now - failAt > 10_000) { failAt = now; Log.w(TAG, "tick", e) }
    }
  }
}
