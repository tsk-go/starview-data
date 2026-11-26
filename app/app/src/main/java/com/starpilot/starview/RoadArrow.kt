package com.starpilot.starview

import kotlin.math.abs
import kotlin.math.hypot
import kotlin.math.max
import kotlin.math.min

/**
 * 2.90.14 (owner, Nov 25: "on scene view and also in camera view a blue arrow when I need to make a turn ... on the
 * actual road"; "on the highway if I need to do a slide right or an actual ride there should be a flashing arrow on the
 * lane the one I need to take"): the road arrow's shape. The look (agreed from mock-ups, project doc
 * road-arrow-design.md) is drawn by LaneSceneView; this is where it goes:
 *  - a city turn (left / right / sharp, from 500 ft): from just ahead of the van along our lane, then round into the
 *    street the route turns onto (the route's own line there, a lane's half width right of it);
 *  - a lane to take (an exit, a fork, a slight turn, a merge, from half a mile): from our lane over to the edge lane on
 *    that side (unless we're already in it), then, once close, onto the route's line (the ramp).
 * Our lane and the lanes beside it come from the Scene's own road (LaneRibbon), so the arrow lies on the road as drawn.
 * [shape] is pure numbers (the caller passes the road and the route already in the car's frame); [shapeOn] does that for
 * the Scene and the camera view from the anchored map (LaneAnchor's fit) and where the car is on it.
 */
object RoadArrow {
  const val CITY_M = 152.4          // a city turn shows from 500 ft
  const val LANE_M = 800.0          // a lane to take from half a mile
  const val AFTER_M = 28.0          // how far past the turn the arrow reaches
  const val START_F = 3.6           // it starts just ahead of the van's nose (m ahead of the comma camera)
  const val MAX_F = 190.0           // and stays where the Scene draws the road clearly
  const val TURN_R = 7.0            // a city turn's radius
  const val LW = LaneRibbon.LW

  enum class Kind { NONE, TURN, LANE }

  /** what the comma's route says: its line (lat / lon, metres along), where we are on it, the next instruction */
  class Input(val lat: DoubleArray, val lon: DoubleArray, val cum: DoubleArray, val along: Double,
              val type: String, val modifier: String, val dist: Double)

  private var liveLine: Any? = null
  private var liveLat = DoubleArray(0)
  private var liveLon = DoubleArray(0)

  /** the comma's route and next instruction now, when it calls for an arrow (UI thread) */
  fun live(t: Telemetry?): Input? {
    val nav = t?.navCard(System.currentTimeMillis())?.first ?: return null
    val type = nav.optString("maneuverType"); val mod = nav.optString("maneuverModifier")
    val dist = nav.optDouble("maneuverDistance", Double.NaN)
    if (kind(type, mod, dist) == Kind.NONE || !Nav.INSTANCE.active) return null
    val line = Nav.INSTANCE.line ?: return null
    val along = Nav.INSTANCE.along
    if (along.isNaN() || line.size < 2) return null
    if (liveLine !== line) {
      liveLine = line
      liveLat = DoubleArray(line.size) { Nav.INSTANCE.latOf(line.my[it]) }
      liveLon = DoubleArray(line.size) { Nav.INSTANCE.lonOf(line.mx[it]) }
    }
    return Input(liveLat, liveLon, line.cum, along, type, mod, dist)
  }

  /** the arrow for [inp] on the generated road of [fit] (LaneRibbon), the car at [pose] having driven [travelled] m
   *  (Odometry); null without that road */
  fun shapeOn(inp: Input, fit: LaneFit.Fit, pose: LaneFit.Pose, travelled: Double): Shape? {
    val kind = kind(inp.type, inp.modifier, inp.dist)
    if (kind == Kind.NONE) return null
    val geo = fit.ribbon ?: return null
    val adv = travelled - (fit.odo.getOrNull(3) ?: travelled)
    // the route around the turn, in the car's frame
    val cum = inp.cum
    val lo = inp.along + inp.dist - 25; val hi = inp.along + inp.dist + AFTER_M + 15
    var i0 = 0; while (i0 < cum.size - 1 && cum[i0 + 1] < lo) i0++
    var i1 = i0; while (i1 < cum.size - 1 && cum[i1] < hi) i1++
    val rr = DoubleArray(i1 - i0 + 1); val ff = DoubleArray(rr.size); val ss = DoubleArray(rr.size)
    for (k in rr.indices) {
      val i = i0 + k
      val p = fit.built.xy(inp.lat[i], inp.lon[i])
      rr[k] = pose.right(p[0], p[1]); ff[k] = pose.fwd(p[0], p[1]); ss[k] = cum[i] - inp.along
    }
    return shape(kind, side(inp.type, inp.modifier), inp.dist, geo.laneD, geo.d,
      { a, d -> geo.place(adv + a, d)?.let { doubleArrayOf(pose.right(it[0], it[1]), pose.fwd(it[0], it[1])) } },
      { a -> geo.edges(adv + a) }, rr, ff, ss)
  }

  /** the points [from] until [to] of [sh], measured from its new start; null when too few are left */
  fun part(sh: Shape, from: Int, to: Int): Shape? {
    val a = from.coerceIn(0, sh.size); val b = to.coerceIn(a, sh.size)
    if (b - a < 4) return null
    if (a == 0 && b == sh.size) return sh
    val s0 = sh.s[a]
    return Shape(sh.r.copyOfRange(a, b), sh.f.copyOfRange(a, b), DoubleArray(b - a) { sh.s[a + it] - s0 }, sh.kind)
  }

  /** what the comma's next instruction calls for: Mapbox's maneuver type and modifier, and how far ahead it is */
  fun kind(type: String, modifier: String, dist: Double): Kind {
    if (dist.isNaN() || dist < 0) return Kind.NONE
    val t = type.lowercase(); val m = modifier.lowercase()
    if (t in NEVER || m.contains("uturn") || m == "straight") return Kind.NONE
    val lane = t == "off ramp" || t == "fork" || t == "on ramp" || t == "merge" || m.startsWith("slight")
    return when {
      lane -> if (dist <= LANE_M && side(type, modifier) != 0) Kind.LANE else Kind.NONE
      m.contains("left") || m.contains("right") -> if (dist <= CITY_M) Kind.TURN else Kind.NONE
      else -> Kind.NONE
    }
  }
  private val NEVER = setOf("arrive", "depart", "roundabout", "rotary", "roundabout turn", "exit roundabout", "exit rotary",
    "notification", "continue")

  /** 1 right, -1 left; an exit without a side is on the right (US) */
  fun side(type: String, modifier: String): Int {
    val m = modifier.lowercase()
    return when {
      m.contains("right") -> 1
      m.contains("left") -> -1
      type.lowercase() == "off ramp" -> 1
      else -> 0
    }
  }

  /** the arrow's middle line in the car's frame (m right, m ahead of the comma camera) and the length along it */
  class Shape(val r: DoubleArray, val f: DoubleArray, val s: DoubleArray, val kind: Kind) {
    val size get() = r.size
    val length get() = if (s.isEmpty()) 0.0 else s.last()
  }

  /**
   * [place] (m ahead along our road, m right of its thread) -> (m right, m ahead) of the car, null past the road's end;
   * [edges] (m ahead) -> LaneRibbon.Geo.edges; [laneD] our lane's middle and [vanD] where the van is (m right of the thread);
   * [routeR] / [routeF] / [routeS]: the route's line in the car's frame with its distance along the route from the car.
   */
  fun shape(kind: Kind, side: Int, dist: Double, laneD: Double, vanD: Double, place: (Double, Double) -> DoubleArray?,
            edges: (Double) -> DoubleArray, routeR: DoubleArray, routeF: DoubleArray, routeS: DoubleArray): Shape? {
    if (kind == Kind.NONE) return null
    val r = ArrayList<Double>(); val f = ArrayList<Double>()
    fun add(p: DoubleArray?) { if (p != null && p[0].isFinite() && p[1].isFinite()) { r.add(p[0]); f.add(p[1]) } }
    // it starts under the van ([vanD]: m right of the thread) and eases into the lane over 12 m (moving between lanes,
    // the van isn't in the middle of one)
    fun fromVan(x: Double, d: Double): Double {
      val u = ((x - START_F) / 12.0).coerceIn(0.0, 1.0)
      return if (vanD.isFinite()) vanD + (d - vanD) * u * u * (3 - 2 * u) else d
    }
    var join: DoubleArray? = null
    var aEnd: Double                          // where our lane's part ends (m along)
    var joinFrom = Double.NaN                 // where the route's part starts (m along) and how far right of the line
    var joinRight = 0.0
    when (kind) {
      Kind.TURN -> {
        aEnd = max(START_F + 1.0, dist - TURN_R)
        var x = START_F
        while (x <= aEnd) { add(place(x, fromVan(x, laneD))); x += 1.0 }
        // after the turn: the route's line, a lane's half width right of it (the lane we turn into)
        joinFrom = dist + TURN_R * 0.75; joinRight = LW / 2
        join = routeOffset(routeR, routeF, routeS, joinFrom, dist + AFTER_M, joinRight) ?: return finish(r, f, kind)
      }
      Kind.LANE -> {
        // the lane to take is the edge lane on that side where each point is: an exit lane that opens further on is
        // moved into where it opens, not before (the road's lanes ease in and out, so does the arrow)
        val from = START_F + 6.0
        val over = (dist * 0.35).coerceIn(25.0, 60.0)
        val close = dist <= 150.0
        aEnd = if (close) max(START_F + 1.0, dist - 12.0) else min(dist, MAX_F)
        var x = START_F
        while (x <= aEnd) {
          val u = ((x - from) / over).coerceIn(0.0, 1.0)
          val e = edges(x)
          val move = (if (side > 0) e[1] - LW / 2 else e[0] + LW / 2) - laneD
          add(place(x, fromVan(x, laneD + move * u * u * (3 - 2 * u))))
          x += 1.0
        }
        if (close) { joinFrom = dist + 8.0; join = routeOffset(routeR, routeF, routeS, joinFrom, dist + AFTER_M, 0.0) }
      }
      Kind.NONE -> return null
    }
    var b = join
    if (b != null && r.size >= 2 && b.size >= 4) {
      // the route's line and the Scene's road can lie a few feet apart: the route's part is moved over to meet our
      // lane's part where they join, easing back to the route's own place over 30 m (else an S-bend at the join)
      val aS = joinFrom
      val r0 = routeOffset(routeR, routeF, routeS, aEnd, aEnd + 1.0, joinRight)
      if (r0 != null && !aS.isNaN()) {
        val dx = r[r.size - 1] - r0[0]; val dy = f[f.size - 1] - r0[1]
        val moved = b.copyOf()
        var k = 0
        while (k + 1 < moved.size) {
          val w = (1 - (aS + k / 2 - aEnd) / 30.0).coerceIn(0.0, 1.0)
          moved[k] += dx * w; moved[k + 1] += dy * w
          k += 2
        }
        b = moved
      }
      // a smooth curve from where our lane's part ends into the route's part (a cubic, its ends along both)
      val n = r.size
      val ax = r[n - 1]; val ay = f[n - 1]
      val adx = r[n - 1] - r[n - 2]; val ady = f[n - 1] - f[n - 2]
      val bx = b[0]; val by = b[1]
      val bdx = b[2] - b[0]; val bdy = b[3] - b[1]
      val chord = hypot(bx - ax, by - ay)
      val ha = chord * 0.5 / max(1e-6, hypot(adx, ady)); val hb = chord * 0.5 / max(1e-6, hypot(bdx, bdy))
      val c1x = ax + adx * ha; val c1y = ay + ady * ha
      val c2x = bx - bdx * hb; val c2y = by - bdy * hb
      val steps = max(4, (chord / 0.8).toInt())
      for (i in 1 until steps) {
        val t = i.toDouble() / steps; val u = 1 - t
        r.add(u * u * u * ax + 3 * u * u * t * c1x + 3 * u * t * t * c2x + t * t * t * bx)
        f.add(u * u * u * ay + 3 * u * u * t * c1y + 3 * u * t * t * c2y + t * t * t * by)
      }
      var i = 0
      while (i + 1 < b.size) { r.add(b[i]); f.add(b[i + 1]); i += 2 }
    }
    return finish(r, f, kind)
  }

  /** the route's line between [a] and [b] m along it, [right] m right of it, as (r, f) pairs, resampled every metre */
  private fun routeOffset(rr: DoubleArray, ff: DoubleArray, ss: DoubleArray, a: Double, b: Double, right: Double): DoubleArray? {
    if (rr.size < 2) return null
    val out = ArrayList<Double>()
    var s = a
    while (s <= b) {
      var i = 0
      while (i < ss.size - 2 && ss[i + 1] < s) i++
      if (s < ss[0] || s > ss[ss.size - 1]) { s += 1.0; continue }
      val t = ((s - ss[i]) / max(1e-6, ss[i + 1] - ss[i])).coerceIn(0.0, 1.0)
      val dx = rr[i + 1] - rr[i]; val dy = ff[i + 1] - ff[i]
      val l = max(1e-6, hypot(dx, dy))
      // right of the direction (dx ahead-ish): (dy, -dx)
      out.add(rr[i] + dx * t + dy / l * right); out.add(ff[i] + dy * t - dx / l * right)
      s += 1.0
    }
    return if (out.size >= 4) out.toDoubleArray() else null
  }

  /** resample every 0.8 m, round off kinks (3-point average twice), cut past [MAX_F] ahead */
  private fun finish(r: List<Double>, f: List<Double>, kind: Kind): Shape? {
    if (r.size < 2) return null
    var xs = r.toDoubleArray(); var ys = f.toDoubleArray()
    repeat(4) {
      val nx = xs.copyOf(); val ny = ys.copyOf()
      for (i in 1 until xs.size - 1) { nx[i] = (xs[i - 1] + 2 * xs[i] + xs[i + 1]) / 4; ny[i] = (ys[i - 1] + 2 * ys[i] + ys[i + 1]) / 4 }
      xs = nx; ys = ny
    }
    val or = ArrayList<Double>(); val of = ArrayList<Double>(); val os = ArrayList<Double>()
    or.add(xs[0]); of.add(ys[0]); os.add(0.0)
    var acc = 0.0; var next = 0.8
    for (i in 1 until xs.size) {
      val seg = hypot(xs[i] - xs[i - 1], ys[i] - ys[i - 1])
      if (seg < 1e-9) continue
      while (acc + seg >= next) {
        val t = (next - acc) / seg
        val x = xs[i - 1] + (xs[i] - xs[i - 1]) * t; val y = ys[i - 1] + (ys[i] - ys[i - 1]) * t
        if (y > MAX_F) return if (or.size >= 2) Shape(or.toDoubleArray(), of.toDoubleArray(), os.toDoubleArray(), kind) else null
        or.add(x); of.add(y); os.add(next)
        next += 0.8
      }
      acc += seg
    }
    return if (or.size >= 2) Shape(or.toDoubleArray(), of.toDoubleArray(), os.toDoubleArray(), kind) else null
  }
}
