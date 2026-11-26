package com.starpilot.starview

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/** 2.90.14: the road arrow's numbers (RoadArrow, the camera view's trim). JVM only. */
class RoadArrowTest {
  /** a two-way street (its middle line 1.8 m left of us), the route turning right at [dist] onto a cross street */
  private fun turn(dist: Double): RoadArrow.Shape? {
    val rr = ArrayList<Double>(); val ff = ArrayList<Double>(); val ss = ArrayList<Double>()
    var s = 0.0
    while (s <= dist + 45) { if (s <= dist) { rr.add(-1.8); ff.add(s) } else { rr.add(-1.8 + s - dist); ff.add(dist) }; ss.add(s); s += 1.0 }
    return RoadArrow.shape(RoadArrow.Kind.TURN, 1, dist, 1.8, 1.8, { a, d -> doubleArrayOf(d - 1.8, a) },
      { doubleArrayOf(-5.4, 5.4, 999.0, -5.4, 5.4) }, rr.toDoubleArray(), ff.toDoubleArray(), ss.toDoubleArray())
  }

  @Test fun whichInstructionsGetAnArrow() {
    assertEquals(RoadArrow.Kind.TURN, RoadArrow.kind("turn", "right", 100.0))
    assertEquals(RoadArrow.Kind.NONE, RoadArrow.kind("turn", "right", 200.0))          // a city turn from 500 ft
    assertEquals(RoadArrow.Kind.LANE, RoadArrow.kind("off ramp", "", 700.0))            // an exit from half a mile
    assertEquals(RoadArrow.Kind.LANE, RoadArrow.kind("fork", "slight left", 300.0))
    assertEquals(RoadArrow.Kind.NONE, RoadArrow.kind("arrive", "right", 50.0))
    assertEquals(RoadArrow.Kind.NONE, RoadArrow.kind("turn", "uturn", 50.0))
    assertEquals(RoadArrow.Kind.NONE, RoadArrow.kind("continue", "straight", 50.0))
    assertEquals(1, RoadArrow.side("off ramp", ""))
  }

  @Test fun aRightTurnGoesUpOurLaneThenIntoTheCrossStreet() {
    val sh = turn(30.0)!!
    assertEquals(0.0, sh.r[0], 0.3)                         // starts in our lane (its middle: the street's middle line 6 ft left)
    assertTrue(sh.r.last() > 15)                            // ends well into the cross street
    assertEquals(30.0 - 1.8, sh.f.last(), 0.8)              // in its near lane
    for (i in 1 until sh.size) assertEquals(0.8, sh.s[i] - sh.s[i - 1], 1e-6)
  }

  @Test fun aPartStartsAtZero() {
    val sh = turn(30.0)!!
    val p = RoadArrow.part(sh, 5, 20)!!
    assertEquals(15, p.size)
    assertEquals(0.0, p.s[0], 1e-9)
    assertEquals(sh.f[5], p.f[0], 1e-9)
    assertNull(RoadArrow.part(sh, 5, 7))
  }

  /** a camera 1.3 m over the road looking ahead, 2944 x 1848 px */
  private val cam = RoadArrowPainter.Proj { r, f, h, o ->
    if (f < 0.5) false else { o[0] = (1455 + 4044 * r / f).toFloat(); o[1] = (946 + 4044 * (1.3 - h) / f).toFloat(); true }
  }

  @Test fun theCameraKeepsWhatIsInItsPicture() {
    val sh = turn(23.0)!!
    val (p, head) = RoadArrowCameraView.inPicture(sh, cam, 2944f, 1848f, Double.MAX_VALUE, 0.0)!!
    assertTrue(head)
    // it comes up from just under the picture's bottom, not from under the van
    assertTrue(p.f[0] > 5.0)
    val o = FloatArray(2); cam.at(p.r.last(), p.f.last(), 0.0, o)
    assertTrue(o[0] <= 2944 * 0.97f)                          // the arrowhead stays in the picture
  }

  @Test fun aCarAheadCoversTheRest() {
    val sh = turn(23.0)!!
    val (p, head) = RoadArrowCameraView.inPicture(sh, cam, 2944f, 1848f, 12.0, 0.2)!!
    assertFalse(head)
    assertTrue(p.f.last() <= 12.0 + 1e-9)
    assertNotNull(p)
  }
}
