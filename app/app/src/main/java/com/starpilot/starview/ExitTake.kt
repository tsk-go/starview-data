package com.starpilot.starview

import android.os.Handler
import android.os.Looper
import android.util.Log
import org.json.JSONObject
import kotlin.math.abs
import kotlin.math.hypot
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

/**
 * 2.90.7: the route's exit taken from our own lane.
 *
 * Owner, Nov 17 and 18, the same exit both evenings: "it doesn't turn on the blinker for the exit, and it's not slowing
 * down"; then "there is no exit lane from lane 3, there's an opening for the exit ... it's a very short opening window,
 * so it has to be built carefully". Measured on his export: the ramp opens off the edge lane about 150 ft before the
 * split (where the map's ramp leaves our road, the gore's tip) - under 3 s at 34 mph; before that, the edge line and a
 * wall. The camera's road edge on that side jumped from 3 ft to 10-20 ft beside our lane at the opening (and also 1000 ft
 * earlier, at the striped area where an on-ramp joins - only the map tells the two apart). The lane card had counted the
 * ramp as an exit lane beside us ("1 lane to go"); the old exit signal only moves us *into* an exit lane, so here it
 * never came - and had it come (8 s before the route's point, which lies 110 ft onto the ramp), openpilot would have
 * moved right about 3 s before there was room.
 *
 * So, with the RTK fix and the map placing us in the edge lane and the route's next step an exit on that side whose ramp
 * leaves straight from our road (no lane added on that side before the split):
 *  - SIGNAL ~5 s before the opening: the blinker as a signal only (the comma's "exit" reason, fork patch 80: no
 *    automatic lane change, never a turn). A comma without it gets no early blinker.
 *  - MOVE: "lane" on the same hold - the comma's usual confirm (adjacent lane seen, blind spot clear, 0.5 s) - only
 *    while the fix puts us between 400 ft and 40 ft before the split, past any on-ramp's end, the camera shows the room
 *    open on that side (its road edge at least 6 ft out and 3 ft more than before the window), the blind spot is clear,
 *    20-55 mph, openpilot steering.
 *  - GOING while openpilot changes lanes that way; DONE after.
 *  - MISSED: no start by 40 ft before the split (no room seen, blind spot, too slow / fast, driver's other blinker,
 *    openpilot not steering): the blinker goes off at once, we stay on the road, the card says why.
 *  - The speed: from the start the planner's set speed is capped down to 35 mph at the opening (0.8 m/s² profile), only
 *    lower, through starviewd's "advice"; let go 300 ft past the split, or at once when the exit is missed. Not while
 *    New ideas sends its own advice (one writer).
 * Also, for the lane card and the old exit signal: [opening] - the route's exit on that side has no exit lane (whatever
 * lane we're in), so the card doesn't count the ramp as a lane and [ExitGuard] never lets the old signal fire for it.
 */
object ExitTake {
  enum class St { IDLE, TRACK, SIGNAL, MOVE, GOING, DONE, MISSED }

  /** what the screen's 5 Hz worker hands over from Telemetry */
  class Snap(val v: Double, val leftBlinker: Boolean, val rightBlinker: Boolean, val leftBsm: Boolean, val rightBsm: Boolean,
             val latActive: Boolean, val lcDir: Int, val on: Boolean, val nav: JSONObject?)

  /** an exit ahead: its side, the split (m east / north on the map), how far ahead along us, the ramp, whether it
   *  leaves straight from our road's edge lane (no lane added on that side first), and where an on-ramp joining on that
   *  side within 600 ft before the split ends (NaN: none) - the camera's room counts only 100 ft past it */
  class Exit(val side: Int, val x: Double, val y: Double, val along: Double, val ramp: Long, val opening: Boolean,
             val mx: Double = Double.NaN, val my: Double = Double.NaN)

  const val SIGNAL_S = 5.0
  const val OPENING_M = 46.0                 // 150 ft: the opening on the owner's exit
  const val WINDOW_M = 122.0                 // 400 ft: the move may start from here ...
  const val LAST_M = 12.0                    // ... to 40 ft before the split
  const val MIN_V = 20 * 0.44704
  const val MAX_V = 55 * 0.44704
  const val RAMP_V = 35 * 0.44704
  const val SLOW_A = 0.8
  const val ROOM_MIN = 1.9                   // m beyond our line: the camera's edge at least ~6 ft out ...
  const val ROOM_GAIN = 1.0                  // ... and 3 ft more than it was before the window

  @Volatile var state = St.IDLE; private set
  /** the route's exit on this side leaves from the edge lane - no exit lane (-1 left, 1 right, 0 none / not known) */
  @Volatile var opening = 0; internal set
  @Volatile var openingMs = 0L; private set
  @Volatile var splitM = Double.NaN; private set
  @Volatile var note = ""; private set
  /** the comma passes the "exit" reason on (fork patch 80): from its answer to a blink request */
  @Volatile var commaHasExit = false

  // the blinker and the set-speed cap wanted now (the sender below repeats them), and when that was last decided
  @Volatile private var hold = 0
  @Volatile private var holdWhy = ""
  @Volatile private var cap = Double.NaN
  @Volatile private var decidedMs = 0L
  /** for the tests: the blinker side and reason wanted now, the set-speed cap (m/s, NaN = none) */
  internal val heldSide get() = hold
  internal val heldWhy get() = holdWhy
  internal val capNow get() = cap

  private var exit: Exit? = null
  private var open: Exit? = null
  private var side = 0
  private var base = ArrayList<Double>()
  private var stateMs = 0L
  private var probed = false

  internal fun reset() {
    state = St.IDLE; opening = 0; splitM = Double.NaN; note = ""; hold = 0; holdWhy = ""; cap = Double.NaN
    exit = null; open = null; side = 0; base.clear()
  }

  /** the side the route's next step leaves to (an exit / fork), its distance (m); null when it isn't one */
  fun routeExit(nav: JSONObject?): Pair<Int, Double>? {
    if (nav == null || !nav.optBoolean("valid", false)) return null
    val type = nav.optString("maneuverType")
    if (type != "off ramp" && type != "fork") return null
    val side = when (nav.optString("maneuverModifier")) { "slightRight", "right", "sharpRight" -> 1; "slightLeft", "left", "sharpLeft" -> -1; else -> 0 }
    val d = nav.optDouble("maneuverDistance", Double.NaN)
    return if (side == 0 || !d.isFinite()) null else side to d
  }

  private fun link(hw: String) = hw.endsWith("_link")
  private fun oursAt(w: LaneMap.BWay, our: LaneMap.BWay) = !link(w.hw) && (w.hw == our.hw || (our.name.isNotEmpty() && w.name == our.name))
  private fun lanesOurWay(w: LaneMap.BWay): Int { val d = if (w.flow < 0) -1 else 1; return w.lanes.count { it.dir == d } }

  /**
   * The exit the route takes on [side], [dMan] m ahead by the route: a ramp (`*_link`, one way) whose first point is a
   * point of our road ([our] and the pieces going on from it: same kind or name), ahead of [pose] by 0..700 m and
   * between 250 m before and 30 m after the route's point (the route puts its point on the ramp), heading off to that
   * side. [of]: our piece's lanes our way (when the road going on from the split isn't in the map). Pure (tests).
   */
  fun find(b: LaneMap.Built, pose: LaneFit.Pose, our: LaneMap.BWay, of: Int, side: Int, dMan: Double): Exit? {
    var best: Exit? = null
    for (r in b.ways) {
      if (!link(r.hw) || r.flow == 0 || r.px.size < 2) continue
      val i0 = if (r.flow > 0) 0 else r.px.size - 1
      val step = if (r.flow > 0) 1 else -1
      val sx = r.px[i0]; val sy = r.py[i0]
      val along = pose.fwd(sx, sy)
      if (along < 0 || along > 700 || along < dMan - 250 || along > dMan + 30) continue
      if (abs(pose.right(sx, sy)) > of * 3.7 + 6) continue
      val key = r.keys[i0]
      // the pieces of our road through that point: the one coming to it (lanes before the split) and the one going on
      val pieces = b.ways.filter { it !== r && oursAt(it, our) && key in it.keys }
      if (pieces.isEmpty()) continue
      fun endsAt(w: LaneMap.BWay) = w.keys.indexOf(key).let { k -> if (w.flow < 0) k == 0 else k == w.keys.size - 1 }
      val into = pieces.firstOrNull { endsAt(it) } ?: pieces.first()
      val cont = pieces.firstOrNull { !endsAt(it) }
      // off to that side: the ramp 40 m on against our heading
      var j = i0; var run = 0.0
      while (j + step in r.px.indices && run < 40) { run += hypot(r.px[j + step] - r.px[j], r.py[j + step] - r.py[j]); j += step }
      val off = pose.right(r.px[j], r.py[j]) - pose.right(sx, sy)
      if (off * side < 2.0) continue
      // where an on-ramp ends on that side within 180 m before the split: the room the camera sees there is its merge
      var mx = Double.NaN; var my = Double.NaN; var mAlong = -1e9
      for (m in b.ways) {
        if (!link(m.hw) || m.flow == 0 || m === r || m.px.size < 2) continue
        val e = if (m.flow > 0) m.px.size - 1 else 0
        val ea = pose.fwd(m.px[e], m.py[e])
        if (ea > along || ea < along - 180) continue
        if (b.ways.none { it !== m && oursAt(it, our) && m.keys[e] in it.keys }) continue
        val back = if (m.flow > 0) max(0, e - 1) else min(m.px.size - 1, e + 1)
        if ((pose.right(m.px[back], m.py[back]) - pose.right(m.px[e], m.py[e])) * side <= 0) continue
        if (ea > mAlong) { mAlong = ea; mx = m.px[e]; my = m.py[e] }
      }
      // no exit lane: the road has no more lanes coming to the split than going on from it (an exit lane, or a lane
      // that ends as the ramp, is one more before it)
      val opening = lanesOurWay(into) <= (cont?.let { lanesOurWay(it) } ?: of)
      val ex = Exit(side, sx, sy, along, r.id, opening, mx, my)
      if (best == null || abs(along - dMan) < abs(best.along - dMan)) best = ex
    }
    return best
  }

  /** how far past our lane's line on [side] the camera's road edge is, 15 m ahead (m); NaN when it doesn't know */
  fun room(cam: LaneFit.Cam, side: Int): Double {
    if (cam.estd.size < 2 || cam.lx.size < 4) return Double.NaN
    val ei = if (side > 0) 1 else 0
    val li = if (side > 0) 2 else 1
    val edge = LaneFit.interp(cam.ex[ei], cam.ey[ei], 15.0)
    val line = if (cam.lp[li] >= 0.3f && cam.lx[li].size >= 2) LaneFit.interp(cam.lx[li], cam.ly[li], 15.0) else side * 1.8
    return (edge - line) * side
  }

  /**
   * One step, on the screen's 5 Hz worker right after LaneAnchor: [f] LaneAnchor's fit (null: no place), [rtkGood] a fix
   * under 2 ft. Pure apart from the volatile fields the sender reads (tests call it directly).
   */
  fun tick(b: LaneMap.Built, f: LaneFit.Fit?, rtkGood: Boolean, cam: LaneFit.Cam, s: Snap, now: Long) {
    decidedMs = now
    val m = f?.match
    val route = routeExit(s.nav)
    val our = m?.let { b.ways.getOrNull(it.way) }
    // the exit ahead, whatever lane we're in (the lane card and ExitGuard use [opening])
    val e = if (route != null && our != null && f != null && m.of in 1..6) find(b, f.pose, our, m.of, route.first, route.second) else null
    // kept for that ramp until we're past it (a moment without the route or the map doesn't put the lane back)
    if (e != null && e.opening) { open = e; openingMs = now }
    else if (e != null) open = null
    open?.let { o -> val a = f?.pose?.fwd(o.x, o.y) ?: Double.NaN; if (now - openingMs > 3000 && (!a.isFinite() || a < -60 || a > 800)) open = null }
    opening = open?.side ?: 0
    val v = s.v
    when (state) {
      St.IDLE -> {
        if (e != null && e.opening && s.on && rtkGood && s.latActive && m != null && (if (e.side > 0) m.lane == m.of else m.lane == 1)) {
          exit = e; side = e.side; base.clear(); go(St.TRACK, now, "")
        }
      }
      St.TRACK, St.SIGNAL, St.MOVE -> run step@{
        val ex = exit!!
        // the same exit (the route may be refreshed, the fix moves on): its split is where it was
        val along = if (f != null) f.pose.fwd(ex.x, ex.y) else Double.NaN
        splitM = along
        val inEdge = m != null && (if (side > 0) m.lane == m.of else m.lane == 1)
        val routeSame = route != null && route.first == side
        val otherBlinker = if (side > 0) s.leftBlinker else s.rightBlinker
        val bsm = if (side > 0) s.rightBsm else s.leftBsm
        val r = room(cam, side)
        // past an on-ramp's end on that side (its striped merge looks like room to the camera)
        val pastMerge = ex.mx.isNaN() || f == null || f.pose.fwd(ex.mx, ex.my) <= -30.0
        if (r.isFinite() && pastMerge && along > WINDOW_M && along < 400) { base.add(r); if (base.size > 40) base.removeAt(0) }
        val baseRoom = if (base.size >= 5) base.sorted()[base.size / 2] else 1.0
        if (s.lcDir == side) { go(St.GOING, now, "openpilot is moving over"); hold = side; holdWhy = "lane"; cap = speedCap(along, v); return@step }
        val stop = when {
          !along.isFinite() || f == null -> "no place on the map"
          !rtkGood -> "no RTK fix under 2 ft"
          !routeSame -> "the route changed"
          !s.latActive -> "openpilot isn't steering"
          otherBlinker -> "the other blinker is on"
          !inEdge -> "not in the edge lane"
          else -> ""
        }
        if (stop.isNotEmpty()) { miss(now, stop); return@step }
        if (along < LAST_M) { miss(now, if (state == St.MOVE) "openpilot didn't start in time" else when {
          bsm -> "car in the blind spot"; v < MIN_V -> "too slow for a lane change"; v > MAX_V -> "too fast"
          else -> "no room opened" }); return@step }
        // the blinker: a signal only from ~5 s before the opening (needs the comma's "exit" reason)
        val sigAt = (v * SIGNAL_S + OPENING_M).coerceIn(91.0, 244.0)
        if (state == St.TRACK && along <= sigAt && commaHasExit && v >= MIN_V) go(St.SIGNAL, now, "signalling for the exit")
        // the move: inside the window, past an on-ramp's end, the room open, nothing beside us, 20-55 mph
        val open = r.isFinite() && r >= max(ROOM_MIN, baseRoom + ROOM_GAIN)
        if (state != St.MOVE && along <= WINDOW_M && pastMerge && open && !bsm && v in MIN_V..MAX_V) go(St.MOVE, now, "moving into the exit")
        hold = if (state == St.MOVE) side else if (state == St.SIGNAL) side else 0
        holdWhy = if (state == St.MOVE) "lane" else "exit"
        cap = speedCap(along, v)
      }
      St.GOING -> {
        val ex = exit!!
        val along = if (f != null) f.pose.fwd(ex.x, ex.y) else splitM - v * 0.2
        splitM = along
        hold = side; holdWhy = "lane"
        cap = if (along > -90) speedCap(along, v) else Double.NaN
        if (s.lcDir != side) go(St.DONE, now, "on the ramp")
      }
      St.DONE, St.MISSED -> {
        hold = 0; holdWhy = ""
        val ex = exit
        val along = if (ex != null && f != null) f.pose.fwd(ex.x, ex.y) else Double.NaN
        splitM = along
        cap = if (state == St.DONE && along.isFinite() && along > -90) speedCap(along, v) else Double.NaN
        if (!along.isFinite() || along < -150 || now - stateMs > 30_000) { go(St.IDLE, now, ""); exit = null; side = 0; cap = Double.NaN }
      }
    }
    // does this comma pass the "exit" reason on? Asked once, with both blinkers off (nothing held: "off" lets go of nothing)
    if (state == St.TRACK && !probed && !s.leftBlinker && !s.rightBlinker) { probed = true; probe() }
    kick()
  }

  /** the set speed: down to the ramp's speed at the opening, at most [SLOW_A] on the way there */
  private fun speedCap(along: Double, v: Double): Double {
    val c = sqrt(RAMP_V * RAMP_V + 2 * SLOW_A * max(0.0, along - OPENING_M))
    return if (c < v + 2.0) c else Double.NaN
  }

  private fun go(st: St, now: Long, text: String) {
    if (st != state) Log.w(TAG, "$state -> $st${if (text.isNotEmpty()) ": $text" else ""} (split ${"%.0f".format(splitM / 0.3048)} ft)")
    state = st; stateMs = now; note = text
  }

  private fun miss(now: Long, reason: String) {
    hold = 0; holdWhy = ""; cap = Double.NaN
    go(St.MISSED, now, "exit not taken: $reason")
  }

  // ------------------------------------------------------------------ the sender (main thread, 4x a second)
  private const val TAG = "StarView/ExitTake"
  private val main by lazy { Handler(Looper.getMainLooper()) }
  @Volatile private var running = false
  private var sentSide = 0
  private var sentCap = false
  private var beat = 0

  private fun kick() {
    if (running || (hold == 0 && sentSide == 0 && cap.isNaN() && !sentCap)) return
    running = true
    try { main.post(sender) } catch (e: Throwable) { running = false }
  }

  private val sender = object : Runnable {
    override fun run() {
      val dc = Link.data
      val fresh = System.currentTimeMillis() - decidedMs < 1200       // the worker stopped deciding: let go
      val want = if (fresh) hold else 0
      try {
        if (dc != null && dc.connected) {
          if (want != 0) {
            dc.request("blink", mapOf("side" to if (want < 0) "left" else "right", "why" to holdWhy)) { r -> learn(r) }
            sentSide = want
          } else if (sentSide != 0) {
            dc.request("blink", mapOf("side" to "off")) { r -> learn(r) }
            sentSide = 0
          }
          val c = if (fresh) cap else Double.NaN
          if (beat++ % 2 == 0) {
            if (c.isFinite() && !ideasBusy()) { dc.request("advice", mapOf("cap" to c, "why" to "exit")); sentCap = true }
            else if (sentCap) { if (!ideasBusy()) dc.request("advice", emptyMap()); sentCap = false }
          }
        }
      } catch (e: Throwable) { Log.w(TAG, "send", e) }
      if (want != 0 || sentSide != 0 || (fresh && cap.isFinite()) || sentCap) main.postDelayed(this, 250)
      else running = false
    }
  }

  private fun probe() {
    try { main.post { Link.data?.takeIf { it.connected }?.request("blink", mapOf("side" to "off")) { r -> learn(r) } } } catch (e: Throwable) {}
  }

  private fun learn(r: JSONObject) {
    val w = r.optJSONArray("whys") ?: return
    var has = false
    for (i in 0 until w.length()) if (w.optString(i) == "exit") has = true
    commaHasExit = has
  }

  /** New ideas is sending its own advice (one writer on the comma: then we leave the set speed alone) */
  private fun ideasBusy(): Boolean = try {
    Ideas.INSTANCE.getParts().any { it.getCap() != null || it.getFollow() > 0.0 || it.getOffset() != 0.0 }
  } catch (e: Throwable) { false }

  /** for the lane card's title: what it is doing about the exit ("" = nothing to say) */
  fun cardText(): String {
    val a = if (side > 0) "→" else "←"
    return when (state) {
      St.TRACK -> if (splitM.isFinite()) "exit $a in ${(splitM / 0.3048 / 10).toInt() * 10} ft" else "exit $a ahead"
      St.SIGNAL -> "signalling for the exit $a"
      St.MOVE, St.GOING -> "taking the exit $a"
      St.MISSED -> if (System.currentTimeMillis() - stateMs < 10_000) note else ""
      else -> ""
    }
  }

  /** for the bookmark / log */
  fun status(): String = if (state == St.IDLE) "" else
    "$state, split ${if (splitM.isFinite()) "%.0f ft".format(splitM / 0.3048) else "?"}${if (note.isNotEmpty()) ", $note" else ""}"

}
