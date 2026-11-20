package com.starpilot.starview

import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.math.PI

/**
 * 2.90.7: ExitTake on a made-up parkway shaped like the owner's exit (Nov 17 / 18): three lanes north, an on-ramp joining
 * on the right 300 m (1000 ft) before the split, the exit ramp leaving the right lane at the split with no exit lane; the
 * camera's road edge on the right 3 ft past our line, out to 15 ft at the on-ramp's striped merge and again over the 150
 * ft opening before the split. Driven in the right lane at 34 mph with a fix under 2 ft and the route's step "off ramp,
 * right" 110 ft past the split (on the ramp, as Mapbox puts it).
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35])
class ExitTakeTest {
  private val lat0 = 40.7; private val lon0 = -73.98
  private val ky = 110540.0; private val kx = 111320.0 * Math.cos(Math.toRadians(lat0))
  private val split = 500.0                     // m north of lat0
  private lateinit var b: LaneMap.Built
  private lateinit var mid: DoubleArray         // the lanes' middles (m east of the parkway's line)

  private fun pt(x: Double, y: Double) = """{"lat":${lat0 + y / ky},"lon":${lon0 + x / kx}}"""
  private fun way(id: Int, hw: String, lanes: Int, vararg xy: Double) =
    """{"type":"way","id":$id,"tags":{"highway":"$hw","name":"${if (hw == "motorway") "Test Parkway" else ""}","oneway":"yes","lanes":"$lanes"},"geometry":[${(0 until xy.size / 2).joinToString(",") { pt(xy[2 * it], xy[2 * it + 1]) }}]}"""

  private fun build(laneBeforeSplit: Int = 3) {
    LaneMap.offline = true
    LaneMap.parse("""{"elements":[${listOf(
      way(301, "motorway", 3, 0.0, -600.0, 0.0, 200.0),
      way(302, "motorway", laneBeforeSplit, 0.0, 200.0, 0.0, split),
      way(303, "motorway", 3, 0.0, split, 0.0, 1300.0),
      way(304, "motorway_link", 1, 45.0, 60.0, 20.0, 150.0, 0.0, 200.0),            // the on-ramp, joining on the right
      way(305, "motorway_link", 1, 0.0, split, 12.0, split + 60.0, 60.0, split + 150.0)  // the exit
    ).joinToString(",")}]}""")
    LaneMap.buildNow(lat0, lon0)
    b = LaneMap.built!!
    mid = b.ways.first { it.id == 301L }.lanes.map { it.x[0].toDouble() }.sorted().toDoubleArray()
    assertEquals(3, mid.size)
  }

  // the map's frame starts where the first build was: ours here, and none left for the next test class
  @Before fun setUp() { ExitTake.reset(); ExitTake.commaHasExit = true; LaneMap.built = null }
  @org.junit.After fun tearDown() { LaneMap.built = null; ExitTake.reset() }

  private val grid = FloatArray(33) { it * it * 0.1f }
  /** the camera in the right lane: our lines at +-1.8 m, the right road edge [room] m past our right line */
  private fun cam(room: Double): LaneFit.Cam {
    val ys = floatArrayOf(-5.4f, -1.8f, 1.8f, 5.4f)
    return LaneFit.Cam(Array(4) { grid }, Array(4) { i -> FloatArray(33) { ys[i] } }, floatArrayOf(0.9f, 0.95f, 0.95f, 0.1f),
      Array(2) { grid }, arrayOf(FloatArray(33) { -9f }, FloatArray(33) { (1.8 + room).toFloat() }), floatArrayOf(0.5f, 0.6f))
  }

  private class Run(val states: MutableList<Pair<Double, ExitTake.St>> = ArrayList(), var signalAt: Double = Double.NaN,
                    var moveAt: Double = Double.NaN, val whys: MutableSet<String> = HashSet(), var capMin: Double = Double.NaN)

  /** drives north from y = -300 m in lane [lane] (0 = left) at [v] m/s; [room] (y) the camera's room on the right; [lc]
   *  when openpilot changes lanes (y) after the tablet said "lane"; [bsm] (y) a car in the right blind spot */
  private fun drive(lane: Int = 2, v: Double = 15.2, room: (Double) -> Double, lcAfter: Double = 0.6,
                    bsm: (Double) -> Boolean = { false }, until: Double = split + 200, mapSure: Boolean = true,
                    laneSure: Double = 1.0): Run {
    val run = Run()
    val st = LaneAnchor.State()
    var y = -300.0; var t = 0.0; var laneFrom = Double.NaN; var x = mid[lane]
    while (y < until) {
      val lc = laneFrom.isFinite() && t - laneFrom >= lcAfter && t - laneFrom < lcAfter + 3.0
      if (lc) x += (3.6 / 3.0) * 0.2
      val c = cam(room(y))
      val f = LaneAnchor.run(b, LaneFit.Pose(x, y, 0.0), 0.5, c, doubleArrayOf(x, y, PI / 2, y + 300), (t * 1000).toLong() + 1000, st,
        null, null, 0, v, 0.05, if (lc) 1 else 0)
      val nav = JSONObject().put("valid", true).put("maneuverType", "off ramp").put("maneuverModifier", "right")
        .put("maneuverDistance", split + 34 - y)
      ExitTake.tick(b, f, true, c, ExitTake.Snap(v, false, ExitTake.heldSide > 0, false, bsm(y), true, if (lc) 1 else 0, true, nav),
        (t * 1000).toLong() + 1000, mapSure, laneSure)
      val s = ExitTake.state
      if (run.states.lastOrNull()?.second != s) run.states.add(y to s)
      if (s == ExitTake.St.SIGNAL && run.signalAt.isNaN()) run.signalAt = split - y
      if (s == ExitTake.St.MOVE && run.moveAt.isNaN()) { run.moveAt = split - y; laneFrom = t }
      if (ExitTake.heldSide != 0) run.whys.add(ExitTake.heldWhy)
      val cap = ExitTake.capNow
      if (cap.isFinite()) run.capMin = if (run.capMin.isNaN()) cap else minOf(run.capMin, cap)
      y += v * 0.2; t += 0.2
    }
    return run
  }

  /** the owner's exit: room at the on-ramp's merge (y 150-230) and over the 150 ft (46 m) before the split */
  private val owners: (Double) -> Double = { y -> if (y in 150.0..230.0 || y in split - 46..split + 20) 4.0 else 0.9 }

  @Test fun theExitIsTakenInsideTheOpeningOnly() {
    build()
    val r = drive(room = owners)
    assertTrue("signalled ~5 s before the opening: ${r.signalAt} m before the split, ${r.states}", r.signalAt in 100.0..125.0)
    assertTrue("moved only inside the opening: ${r.moveAt} m before the split, ${r.states}", r.moveAt in 12.0..46.5)
    assertEquals("a signal only, then go: ${r.whys}", setOf("exit", "lane"), r.whys)
    assertTrue("done: ${r.states}", r.states.any { it.second == ExitTake.St.DONE })
    assertTrue("never missed: ${r.states}", r.states.none { it.second == ExitTake.St.MISSED })
  }

  @Test fun aFixShiftedSidewaysStillTakesTheExit() {
    // 2.90.11 (Nov 19 evening): the fix 4 - 5 ft off to the side of the map's lanes all the way (LaneAnchor's doubt), our
    // lane sure by the camera and the lane card: the exit is taken as with the map trusted
    build()
    val r = drive(room = owners, mapSure = false, laneSure = 1.0)
    assertTrue("signalled: ${r.states}", r.signalAt in 100.0..125.0)
    assertTrue("moved inside the opening: ${r.moveAt} ${r.states}", r.moveAt in 12.0..46.5)
    assertTrue("done: ${r.states}", r.states.any { it.second == ExitTake.St.DONE })
  }

  @Test fun theMapInDoubtAndOurLaneUnsureNothingStarts() {
    build()
    val r = drive(room = owners, mapSure = false, laneSure = 0.6, until = split - 100)
    assertTrue("never tracked: ${r.states}", r.states.all { it.second == ExitTake.St.IDLE })
    assertEquals(0, ExitTake.heldSide)
  }

  @Test fun noRoomNoMove() {
    build()
    val r = drive(room = { y -> if (y in 150.0..230.0) 4.0 else 0.9 })
    assertTrue("no move: ${r.states}", r.moveAt.isNaN())
    assertTrue("missed, said why: ${r.states} ${ExitTake.note}", r.states.any { it.second == ExitTake.St.MISSED })
    assertEquals(0, ExitTake.heldSide)
  }

  @Test fun aCarInTheBlindSpotKeepsUsOnTheRoad() {
    build()
    val r = drive(room = owners, bsm = { y -> y > split - 80 })
    assertTrue("no move: ${r.states}", r.moveAt.isNaN())
    assertTrue("missed: ${r.states}", r.states.any { it.second == ExitTake.St.MISSED })
  }

  @Test fun inTheMiddleLaneNothingButTheCardKnows() {
    build()
    val r = drive(lane = 1, room = owners, until = split - 100)
    val opening = ExitTake.opening
    assertTrue("never tracked from the middle lane: ${r.states}", r.states.all { it.second == ExitTake.St.IDLE })
    assertEquals("but there is no exit lane on the right", 1, opening)
  }

  @Test fun anExitLaneIsLeftToTheOldWay() {
    build(laneBeforeSplit = 4)          // a lane added on the right before the split: an exit lane to move into
    val r = drive(room = owners, until = split - 100)
    assertTrue("not ours: ${r.states}", r.states.all { it.second == ExitTake.St.IDLE })
    assertEquals(0, ExitTake.opening)
  }

  @Test fun slowingDownForTheRamp() {
    build()
    val r = drive(v = 20.0, room = owners, lcAfter = 0.6)
    assertTrue("capped down to about 35 mph: ${r.capMin}", r.capMin in 15.0..16.5)
  }
}
