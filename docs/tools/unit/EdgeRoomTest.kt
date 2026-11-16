package com.starpilot.starview

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

/** 2.89.3 a road edge past a sure lane count is a wide shoulder (LaneBrain.hybEdgeRoom), on the Nov 16 Belt Parkway
 *  drive: 3 lanes our way (your count), in the middle lane, a right shoulder about as wide as a lane. */
class EdgeRoomTest {
  private val b = LaneBrain()
  private fun field(name: String) = LaneBrain::class.java.getDeclaredField(name).apply { isAccessible = true }
  private fun model(lanes: Int, conf: Double, extraR: Int = 0) =
    field("model").set(b, LaneBrain.RoadModel(lanes, "your count", conf, 0, extraR, "", "", false, false))
  private fun belief(l: Int, r: Int) {   // p (what the camera saw so far) sure of (l, r), the answer redone
    val p = Array(6) { i -> DoubleArray(6) { j -> if (i == l && j == r) 1.0 else 0.001 } }
    field("p").set(b, p)
    answer()
  }
  private fun answer() = LaneBrain::class.java.getDeclaredMethod("answer").apply { isAccessible = true }.invoke(b)
  private val edgesSide = LaneBrain::class.java.declaredMethods.first { it.name == "edges\$side\$12" }.apply { isAccessible = true }

  /** The camera's right road edge `gapM` past our right line, every 0.1 s for `secs`, as edges() hands it over. */
  private fun rightEdge(gapM: Float, secs: Int, t0: Long = 1_000_000L) {
    val vote = field("vEdgeR").get(b)
    for (k in 0 until secs * 10) {
      edgesSide.invoke(null, b, 3.8f, t0 + k * 100L, 1, vote, 1.75f + gapM, 1.75f, 0.95f, null, Float.NaN)
      answer()
    }
  }

  @Test fun rangeKeepsWhatFits() {
    model(3, 0.97)
    belief(1, 1)
    assertEquals(11, b.hybEdgeRoom(12, 1))      // "1-2 lanes away": only 1 fits beside 1 lane on the left
    assertNull(b.hybEdgeRoom(22, 1))            // "2 lanes away": a shoulder wider than 12 ft, says nothing
    assertEquals(11, b.hybEdgeRoom(11, 1))
    assertEquals(0, b.hybEdgeRoom(0, 1))        // right past our line: untouched
    assertEquals(1, b.hybEdgeRoom(2, 1))        // "0-2" -> "0-1"
    model(3, 0.97, extraR = 1)                  // the map's exit lane on the right: 4 in all, 2 fit
    assertEquals(22, b.hybEdgeRoom(22, 1))
    model(3, 0.75)                              // HPMS off the highway: not sure enough, as before
    assertEquals(22, b.hybEdgeRoom(22, 1))
    model(0, 0.0)
    assertEquals(22, b.hybEdgeRoom(22, 1))
  }

  @Test fun stuckOnFourComesBack() {          // the video: "Lane 2 of 4 (camera)" while the edge read 1-2 lanes away
    model(3, 0.97)
    belief(1, 2)
    assertEquals(2, b.side(1).first)
    rightEdge(7.0f, 10)                         // one 11.5 ft lane + an 11.5 ft shoulder
    assertEquals(1, b.side(1).first)
    assertEquals(3, b.laneCount().first)
    assertEquals("your count", b.laneCount().second)
  }

  @Test fun withoutASureCountAsBefore() {     // HPMS off the highway (0.75): the "1-2" range can't tell, 4 stays
    model(3, 0.75)
    belief(1, 2)
    rightEdge(7.0f, 10)
    assertEquals(2, b.side(1).first)
  }

  @Test fun wideShoulderAddsNoLane() {         // the edge "2 lanes away" again and again no longer builds up a 4th lane
    model(3, 0.97)
    belief(1, 1)
    rightEdge(8.0f, 30)
    assertEquals(1, b.side(1).first)
    assertEquals(3, b.laneCount().first)
  }
}
