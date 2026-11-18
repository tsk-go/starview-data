package com.starpilot.starview

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.sin

/**
 * 2.90.0: LaneAnchor (the map is the road, the camera places us on it) on a made-up straight one-way road of three
 * lanes going north, driven at 34 mph in the middle lane with a GPS that wanders 10 ft to either side.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35])
class LaneAnchorTest {
  private val lat0 = 40.7; private val lon0 = -74.0
  private lateinit var b: LaneMap.Built
  private lateinit var mid: DoubleArray        // the three lanes' middles (m east)
  private val w get() = mid[1] - mid[0]

  @Before fun road() {
    LaneMap.offline = true
    LaneMap.parse("""{"elements":[{"type":"way","id":1,"tags":{"highway":"primary","name":"Test Road","oneway":"yes","lanes":"3"},
      "geometry":[{"lat":${lat0 - 0.004},"lon":$lon0},{"lat":${lat0 + 0.012},"lon":$lon0}]}]}""")
    LaneMap.buildNow(lat0, lon0)
    b = LaneMap.built!!
    mid = b.ways[0].lanes.map { it.x[0].toDouble() }.sorted().toDoubleArray()
    assertEquals(3, mid.size)
  }

  private val grid = FloatArray(33) { it * it * 0.1f }
  /** the camera in [lane] (0 = left), [off] m right of its middle */
  private fun cam(lane: Int, off: Double): LaneFit.Cam {
    val l = (-w / 2 - off).toFloat(); val r = (w / 2 - off).toFloat()
    val ys = floatArrayOf(l - w.toFloat(), l, r, r + w.toFloat())
    val eL = (-(lane + 0.5) * w - off - 0.5).toFloat(); val eR = ((2.5 - lane) * w - off + 0.5).toFloat()
    return LaneFit.Cam(Array(4) { grid }, Array(4) { i -> FloatArray(33) { ys[i] } }, floatArrayOf(0.9f, 0.95f, 0.95f, 0.9f),
      Array(2) { grid }, arrayOf(FloatArray(33) { eL }, FloatArray(33) { eR }), floatArrayOf(0.5f, 0.5f))
  }

  private class Drive(var lane: Int, var off: Double = 0.2)

  /** [seconds] of driving; [each] is called 4 times a second with (s, state, pose) after the step */
  private fun drive(seconds: Double, d: Drive, st: LaneAnchor.State, gpsBias: Double = 0.0, card: (Double) -> LaneSceneView.Lanes? = { null },
                    acc: (Double) -> Double = { 3.3 }, cross: (Double) -> Int = { 0 }, each: (Double, LaneFit.Pose) -> Unit = { _, _ -> }) {
    var t = 0.0
    while (t <= seconds) {
      val sh = cross(t)
      val soon = (1..16).firstOrNull { cross(t + it * 0.25) != 0 }       // drifting toward the line for 4 s before
      if (sh != 0) { d.lane += sh; d.off = -sh * (w / 2 - 0.1) }     // just over the line
      else if (soon != null) d.off += (cross(t + soon * 0.25) * (w / 2 - 0.1) - d.off) / soon
      else d.off += (0.2 - d.off) * 0.2
      val x = mid[d.lane] + d.off; val y = -300.0 + 15.0 * t
      val a = acc(t)
      val gps = if (a > 10) LaneFit.Pose(x + 40, y - 80, 0.0) else LaneFit.Pose(x + gpsBias + 3.0 * sin(t / 3.0), y + 1.5 * sin(t / 5.0), Math.toRadians(1.5 * sin(t)))
      val f = LaneAnchor.run(b, gps, a, cam(d.lane, d.off), doubleArrayOf(x, y, PI / 2, 15.0 * t), (t * 1000).toLong() + 1000, st, card(t), null, sh)
      assertNotNull("a fit at $t s", f)
      each(t, f!!.pose)
      t += 0.25
    }
  }

  @Test fun theLaneCardChangingItsMindDoesNotMoveTheRoad() {
    val st = LaneAnchor.State(); val d = Drive(1)
    // right for 7 s, then "1 of 3", "2 of 3", "1 of 2", "2 of 3", "3 of 3" ... 7 s each, as in the owner's video
    val wrong = listOf(1 to 1, 0 to 2, 1 to 1, 0 to 1, 1 to 1, 2 to 0, 1 to 1, 0 to 2)
    drive(56.0, d, st, card = { t -> wrong[(t / 7).toInt().coerceAtMost(wrong.size - 1)].let { LaneSceneView.Lanes(it.first, it.second, -1) } }) { t, pose ->
      if (t > 3) {
        assertEquals("the lane drawn at $t s", 1, st.lane)
        assertTrue("on the road within a foot at $t s: ${pose.x}", abs(pose.x - (mid[1] + d.off)) < 0.35)
        assertTrue("pointing along the road at $t s", abs(pose.h) < 0.02)
      }
    }
  }

  @Test fun drivingAcrossALineMovesUsOneLaneOverAtOnceAndSmoothly() {
    val st = LaneAnchor.State(); val d = Drive(1)
    var last = Double.NaN
    drive(20.0, d, st, cross = { t -> if (t == 10.0) 1 else 0 }) { t, pose ->
      if (t >= 10.0) assertEquals("the lane drawn at $t s", 2, st.lane)
      else if (t > 3) assertEquals(1, st.lane)
      if (last.isFinite() && t > 3) assertTrue("no jump at $t s: ${pose.x - last}", abs(pose.x - last) < 0.6)
      last = pose.x
    }
  }

  @Test fun aWrongStartIsPutRightByWhatLasts() {
    // the GPS is a lane off to the left the whole way, so we start out drawn in the left lane; the road edges and the
    // lane card say the middle one, and keep saying it
    val st = LaneAnchor.State(); val d = Drive(1)
    var fixedAt = Double.NaN
    drive(40.0, d, st, gpsBias = -w, card = { LaneSceneView.Lanes(1, 1, -1) }) { t, _ -> if (fixedAt.isNaN() && st.lane == 1) fixedAt = t }
    assertTrue("put right at $fixedAt s", fixedAt < 30.0)
    assertEquals(1, st.lane)
  }

  @Test fun underABridgeWithNoGpsWeCarryOnInOurLane() {
    val st = LaneAnchor.State(); val d = Drive(2)
    drive(40.0, d, st, acc = { t -> if (t in 10.0..30.0) 50.0 else 3.3 }) { t, pose ->
      if (t > 3) {
        assertEquals("the lane drawn at $t s", 2, st.lane)
        assertTrue("where we are at $t s: ${pose.y} for ${-300 + 15 * t}", abs(pose.y - (-300 + 15 * t)) < 4.0)
      }
    }
  }
}
