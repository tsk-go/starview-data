package com.starpilot.starview

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * 2.90.6: the lane card shows what the Scene shows with a fix under 2 ft (owner, Nov 18 at night in traffic: "Scene
 * view is correct but the card doesn't even know"), and counts through lanes, the turn lanes added after them ("Lane 1
 * of 3 + turn lane").
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35])
class LaneCardFollowTest {
  /** the card as it was on Nov 18: lanes left 0 (99 %), right 1 (93 %) - "Lane 1 of 2" on a road of 3 - its road count
   *  from [src] */
  private fun card(lanes: Int, src: String, conf: Double): LaneBrain {
    val c = LaneBrain()
    val q = Array(6) { DoubleArray(6) { 1e-4 } }
    q[0][1] = 0.92; q[0][2] = 0.06; q[1][1] = 0.01
    LaneBrain::class.java.getDeclaredField("q").apply { isAccessible = true }.set(c, q)
    LaneBrain::class.java.getDeclaredField("model").apply { isAccessible = true }
      .set(c, LaneBrain.RoadModel(lanes, src, conf, 0, 0, "", "", false, false))
    return c
  }

  private fun scene(vararg v: Int) { LaneAnchor.rtkLane = v; LaneAnchor.rtkLaneMs = System.currentTimeMillis() }

  @After fun clear() { LaneAnchor.rtkLane = null }

  @Test fun theCardShowsTheScenesLaneAndCount() {
    val c = card(3, "your count", 0.97)
    scene(1, 3, 0, 0)
    c.hybAnswer()
    assertEquals(1, c.laneFromLeft().first)
    assertEquals(3, c.laneCount().first)
    assertEquals("Scene, RTK", c.laneCount().second)
    assertEquals("Lane 1 of 3", c.label())
    // the survey's "1 lane" and the camera's count don't change it (Nov 18: "1 lane (HPMS) · map 5")
    val d = card(1, "HPMS", 0.75)
    scene(2, 5, 0, 0)
    repeat(3) { d.hybAnswer() }
    assertEquals(2, d.laneFromLeft().first)
    assertEquals(5, d.laneCount().first)
    assertEquals("Lane 2 of 5", d.label())
  }

  @Test fun turnLanesAreAddedAfterTheThroughLanes() {
    val c = card(3, "HPMS", 0.9)
    scene(2, 4, 1, 0)
    c.hybAnswer()
    assertEquals("Lane 1 of 3 + turn lane ←", c.label())
    scene(1, 4, 1, 0)
    assertEquals("Left turn lane (left of 3 lanes)", c.label())
    scene(5, 5, 1, 1)
    assertEquals("Right turn lane (right of 3 lanes)", c.label())
    scene(2, 5, 2, 0)
    assertEquals("Left turn lane 2 of 2 (left of 3 lanes)", c.label())
  }

  @Test fun anExitLaneTheScenesRoadDoesntCountStaysOnTheCard() {
    // 2.90.7: Nov 18, the card had "Lane 3 of 3 + exit lane →" and 2.90.6 took the exit lane away (the map's road counts
    // 3 there). The card's count is the Scene's plus that lane; its side follows the card's own camera, a little
    val c = card(3, "HPMS", 0.9)
    LaneBrain::class.java.getDeclaredField("model").apply { isAccessible = true }
      .set(c, LaneBrain.RoadModel(3, "HPMS", 0.9, 0, 1, "", "exit", false, false))
    val q = Array(6) { DoubleArray(6) { 1e-4 } }
    q[2][1] = 0.9; q[2][0] = 0.09
    LaneBrain::class.java.getDeclaredField("q").apply { isAccessible = true }.set(c, q)
    scene(3, 3, 0, 0)
    repeat(3) { c.hybAnswer() }
    assertEquals(4, c.laneCount().first)
    assertEquals("Lane 3 of 3 + exit lane →", c.label())
  }

  @Test fun aRampThatOpensOffOurLaneIsNoLane() {
    // 2.90.7: owner, Nov 18: "there is no exit lane from lane 3, there's an opening" - the route's exit leaves straight
    // from the edge lane (ExitTake.opening): the map's ramp is no lane beside us
    val c = LaneBrain()
    val m = LaneBrain.RoadModel(3, "HPMS", 0.9, 0, 1, "", "exit", false, false)
    assertEquals(1, c.hybModel(m).getExtraR())
    ExitTake.opening = 1
    try {
      assertEquals(0, c.hybModel(m).getExtraR())
      assertEquals(1, LaneBrain.RoadModel(3, "HPMS", 0.9, 1, 0, "exit", "", false, false).let { c.hybModel(it).getExtraL() })
    } finally { ExitTake.opening = 0 }
  }

  @Test fun withoutTheSceneTheCardGoesByItsOwn() {
    val c = card(3, "your count", 0.97)
    c.hybAnswer()
    assertNull(c.hybCount())
    assertEquals(2, c.laneCount().first)
    assertFalse(c.laneCount().second.contains("Scene"))
    // an old one (the Scene stopped: no fix) neither
    LaneAnchor.rtkLane = intArrayOf(1, 3, 0, 0); LaneAnchor.rtkLaneMs = System.currentTimeMillis() - 2000
    assertNull(c.hybCount()); assertNull(c.hybLabel())
  }

  @Test fun whichLanesAreForTurningOnly() {
    assertTrue(LaneAnchor.turnOnly("left", -1))
    assertTrue(LaneAnchor.turnOnly("left;reverse", -1))
    assertFalse(LaneAnchor.turnOnly("left;through", -1))
    assertFalse(LaneAnchor.turnOnly("slight_left", -1))       // an exit lane: the card has those from the ramps
    assertFalse(LaneAnchor.turnOnly("", -1))
    assertFalse(LaneAnchor.turnOnly("none", 1))
    assertTrue(LaneAnchor.turnOnly("right", 1))
    assertFalse(LaneAnchor.turnOnly("through;right", 1))
    assertFalse(LaneAnchor.turnOnly("left", 1))
  }
}
