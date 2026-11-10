package com.starpilot.starview

import kotlin.math.abs
import kotlin.math.hypot

/**
 * Turns openpilot's per-frame radar/vision leads and raw radar points into a stable list of vehicles for the 3D scene.
 *
 * openpilot itself only reports what it needs to drive: up to two leads in our lane (radarState), StarPilot's adjacent
 * leads, and the raw radar point list (liveTracks). The raw list is where everything else lives - cars two lanes over,
 * traffic parked along a city street, a car waiting at a side road - but it is noisy, so we keep a small tracker here:
 * a point has to be seen a few frames in a row before it becomes a car, it survives brief dropouts, it is classified as
 * moving or stationary against our own speed, and anything that coincides with a lead is dropped (the lead wins).
 *
 * All positions are in openpilot's ego frame: x forward from the comma camera, y left. Leads' dRel is bumper-to-bumper,
 * so [BUMPER] is added to get to camera-relative x. Radar yRel is negated like the on-device UI does (it is positive to
 * the right as it comes out of radard).
 */
class Traffic {
  class Vehicle(val id: Long) {
    var x = 0.0; var y = 0.0          // rear-bumper x, centre y (ego frame)
    var vRel = 0f                     // relative longitudinal speed, m/s
    var vAbs = 0f                     // absolute speed, m/s
    var brake = false                 // decelerating hard enough to show lights
    var stationary = false
    var lead = false                  // came from radarState / starpilotRadarState (vision-confirmed)
    var remembered = false            // kept on screen by CarMemory after the radar lost it
    var kind = 0                      // generic model variant (stable per id)
    var hits = 0; var lastSeen = 0L; var lastV = Float.NaN; var lastVTime = 0L; var accel = 0f
  }

  private val byId = HashMap<Long, Vehicle>()
  private var lastRadarFrame = -1L
  val vehicles = ArrayList<Vehicle>()

  companion object {
    const val BUMPER = 2.3            // comma camera -> front bumper, m
    private const val LEAD_ID_BASE = -100L
    private const val MIN_HITS = 3    // radar frames (at 10 Hz) before a point is trusted
    private const val KEEP_MS = 600L  // survive dropouts this long
    private const val MAX_Y = 9.0     // ignore radar returns further sideways than this (m)
    private const val MAX_X = 110.0
  }

  /** Call once per UI tick. Returns the vehicles to draw. */
  fun update(t: Telemetry, nowMs: Long, includeRadar: Boolean, includeAdjacent: Boolean): List<Vehicle> {
    // 1. leads: always present when reported. Fixed ids per slot so smoothing in the renderer stays continuous.
    val leads = ArrayList<Lead>(4)
    leads.add(t.leadOne); leads.add(t.leadTwo)
    if (includeAdjacent) { leads.add(t.leadLeft); leads.add(t.leadRight) }
    for ((i, l) in leads.withIndex()) {
      val id = LEAD_ID_BASE - i
      if (!l.status || l.dRel <= 0.5f) { byId.remove(id); continue }
      val v = byId.getOrPut(id) { Vehicle(id).also { it.kind = i % 4; it.lead = true } }
      v.x = l.dRel + BUMPER; v.y = -l.yRel.toDouble(); v.vRel = l.vRel; v.vAbs = l.vLead
      v.stationary = l.vLead < 0.5f; v.lastSeen = nowMs; v.hits = MIN_HITS
      updateBrake(v, l.vLead, nowMs)
    }

    // 2. raw radar points (only when a new liveTracks frame arrived)
    if (includeRadar && t.radarFrame != lastRadarFrame) {
      lastRadarFrame = t.radarFrame
      val vEgo = t.vEgo
      for (p in t.radar) {
        if (p.dRel <= 0f || p.dRel > MAX_X || abs(p.yRel) > MAX_Y) continue
        val v = byId.getOrPut(p.id) { Vehicle(p.id).also { it.kind = ((p.id xor (p.id ushr 7)) and 3L).toInt() } }
        v.x = p.dRel + BUMPER; v.y = -p.yRel.toDouble(); v.vRel = p.vRel; v.vAbs = vEgo + p.vRel
        v.stationary = abs(v.vAbs) < 1.2f
        v.hits++; v.lastSeen = nowMs
        updateBrake(v, v.vAbs, nowMs)
      }
    } else if (!includeRadar) {
      byId.values.removeAll { !it.lead }
    }

    // 3. expire, dedupe, publish
    byId.values.removeAll { nowMs - it.lastSeen > KEEP_MS }
    vehicles.clear()
    val leadsOut = byId.values.filter { it.lead }
    for (v in byId.values) {
      if (v.hits < MIN_HITS) continue
      if (!v.lead) {
        // the same physical car is usually both a lead and a radar point: keep the lead, drop the point
        var dup = false
        for (l in leadsOut) if (hypot(l.x - v.x, l.y - v.y) < 3.0) { dup = true; break }
        if (dup) continue
        // radar clutter: stationary returns right on our path far ahead are signs/bridges more often than cars
        if (v.stationary && abs(v.y) < 1.0 && v.x > 60) continue
      }
      vehicles.add(v)
    }
    return vehicles
  }

  private fun updateBrake(v: Vehicle, speed: Float, nowMs: Long) {
    if (!v.lastV.isNaN() && nowMs > v.lastVTime) {
      val dt = (nowMs - v.lastVTime) / 1000f
      if (dt in 0.02f..1f) { val a = (speed - v.lastV) / dt; v.accel = v.accel * 0.7f + a * 0.3f }
    }
    v.lastV = speed; v.lastVTime = nowMs
    v.brake = v.accel < -0.9f && speed > 0.3f
  }
}
