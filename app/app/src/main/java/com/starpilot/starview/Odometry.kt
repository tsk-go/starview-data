package com.starpilot.starview

import android.content.Context
import android.content.SharedPreferences
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin
import kotlin.math.tan

/**
 * 2.28.4: dead reckoning (where the car went since the app started: x ahead at start, y left, yaw ccw) from the car's
 * wheel speed and the comma's gyro, integrated on every carState message (20 Hz) on the telemetry thread.
 *
 * It used to be integrated in the Scene's frame loop, which Android stops while the app is in the background: back in
 * front, the Scene still had the car where it was when it left, the GPS fixes looked like jumps of the length driven
 * meanwhile and were ignored for 10 s (2.28.1's jump filter). Telemetry keeps running in the background (it is what
 * feeds Waze its mock location), so this keeps counting.
 */
object Odometry {
  private const val HIST_NS = 8_000_000_000L
  private class Pose(val tNs: Long, val x: Double, val y: Double, val yaw: Double, val v: Double, val yawRate: Double, val dist: Double, val mono: Long = 0L)
  private val hist = ArrayDeque<Pose>()
  private val lock = Any()
  private var lastMono = 0L
  private var x = 0.0; private var y = 0.0; private var yaw = 0.0; private var dist = 0.0
  /** >0: the gyro's z axis counts left turns positive, <0: right (learned from the steering in turns). */
  private var gyroAgree = 0
  private var savedAgree = 0
  @Volatile private var prefs: SharedPreferences? = null
  @Volatile var yawSrc = "steering"; private set

  /** The gyro's learned direction survives a restart (saved when it settles or changes a lot). */
  fun init(ctx: Context) {
    val p = ctx.applicationContext.getSharedPreferences("starview", 0)
    prefs = p
    gyroAgree = p.getInt("gyro_agree", 0).coerceIn(-50, 50)
    savedAgree = gyroAgree
  }

  private fun saveAgree() {
    val p = prefs ?: return
    if ((gyroAgree > 5) == (savedAgree > 5) && (gyroAgree < -5) == (savedAgree < -5) && abs(gyroAgree - savedAgree) < 20) return
    savedAgree = gyroAgree
    p.edit().putInt("gyro_agree", gyroAgree).apply()
  }

  /** +1: the gyro's z counts left turns positive, -1: right (or not learned yet). */
  val gyroSign: Int get() = if (gyroAgree > 5) 1 else -1

  /** Telemetry thread, after carState was read into t. monoNs = the event's logMonoTime (comma clock). */
  fun onCarState(monoNs: Long, t: Telemetry) {
    val now = System.nanoTime()
    val dt = if (lastMono == 0L) 0.0 else ((monoNs - lastMono) / 1e9).let { if (it < 0.0 || it > 1.0) 0.0 else it }
    lastMono = monoNs
    // backing up counts as negative speed (the Scene and the lane map move backwards too)
    val v = if (t.gear == "reverse") -abs(t.vEgo.toDouble()) else t.vEgo.toDouble().coerceAtLeast(0.0)
    // yaw rate from the steering wheel, minus the learned straight-ahead offset (liveParameters), with the learned steer
    // ratio when there is one (Sienna default 15.5, wheelbase 3.03 m; positive angle = left)
    val sr = if (t.steerRatioLive > 5f) t.steerRatioLive.toDouble() else 15.5
    val steerYaw = v * tan(Math.toRadians((t.steeringAngleDeg - t.angleOffsetDeg).toDouble()) / sr) / 3.03 / (1 + v * v / 900.0)
    // the comma's gyro (livePose, bias removed by locationd) when it is coming in; its z axis sign is learned
    val gz = t.poseYawZ.toDouble()
    var yawRate = steerYaw; var src = "steering"
    if (gz.isFinite() && now - t.poseAtNs < 500_000_000L) {
      if (v > 3.0 && abs(steerYaw) > 0.04 && abs(gz) > 0.04) { gyroAgree = (gyroAgree + (if (gz * steerYaw > 0) 1 else -1)).coerceIn(-50, 50); saveAgree() }
      yawRate = if (gyroAgree > 5) gz else -gz
      src = if (abs(gyroAgree) > 5) "gyro" else "gyro (direction not learned yet)"
    }
    if (abs(v) < 0.2) yawRate = 0.0                                     // standing still: nothing turns
    yawSrc = src
    synchronized(lock) {
      yaw += yawRate * dt; x += v * cos(yaw) * dt; y += v * sin(yaw) * dt; dist += abs(v) * dt
      hist.addLast(Pose(now, x, y, yaw, v, yawRate, dist, monoNs))
      while (hist.size > 2 && now - hist.first().tNs > HIST_NS) hist.removeFirst()
    }
  }

  /** The newest (speed, yaw rate). */
  fun motion(): DoubleArray = synchronized(lock) {
    hist.lastOrNull()?.let { doubleArrayOf(it.v, it.yawRate) } ?: doubleArrayOf(0.0, 0.0)
  }

  /** A comma logMonoTime -> this phone's System.nanoTime, from the carState samples kept (null before any). */
  fun monoToLocal(monoNs: Long): Long? = synchronized(lock) {
    var off = Long.MAX_VALUE
    for (p in hist) if (p.mono != 0L) off = min(off, p.tNs - p.mono)
    if (off == Long.MAX_VALUE) null else monoNs + off
  }

  /**
   * The pose at time t (System.nanoTime): (x, y, yaw, dist). Between samples interpolated; after the newest one carried
   * forward with its speed and turn rate (at most 0.25 s, so the Scene moves smoothly at 60 fps between 20 Hz samples).
   */
  fun at(t: Long): DoubleArray = synchronized(lock) {
    if (hist.isEmpty()) return doubleArrayOf(0.0, 0.0, 0.0, 0.0)
    val last = hist.last()
    if (t >= last.tNs) {
      val dt = ((t - last.tNs) / 1e9).coerceIn(0.0, 0.25)
      val yw = last.yaw + last.yawRate * dt
      val mid = last.yaw + last.yawRate * dt / 2
      return doubleArrayOf(last.x + last.v * cos(mid) * dt, last.y + last.v * sin(mid) * dt, yw, last.dist + last.v * dt)
    }
    val first = hist.first()
    if (t <= first.tNs) return doubleArrayOf(first.x, first.y, first.yaw, first.dist)
    var i = hist.size - 1; while (i > 0 && hist[i - 1].tNs > t) i--
    val p = hist[i - 1]; val q = hist[i]; val k = (t - p.tNs).toDouble() / max(1L, q.tNs - p.tNs)
    return doubleArrayOf(p.x + k * (q.x - p.x), p.y + k * (q.y - p.y), p.yaw + k * Math.IEEEremainder(q.yaw - p.yaw, 2 * Math.PI), p.dist + k * (q.dist - p.dist))
  }
}
