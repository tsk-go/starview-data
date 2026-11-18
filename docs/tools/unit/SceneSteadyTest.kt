package com.starpilot.starview

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.math.PI
import kotlin.math.abs

/** 2.90.0: the rules that keep the Scene view still (SceneSteady), on made-up readings. Plain JVM, no Android. */
class SceneSteadyTest {
  private fun lanes(l: Int, r: Int) = LaneSceneView.Lanes(l, r, LaneMap.DOUBLE_YELLOW)

  /** the owner's Nov 17 video: the lane card went 1 of 3, 2 of 3, 1 of 3, 1 of 2, 2 of 3, 1 of 2 within 41 s */
  /** the owner's Nov 17 video: the lane card went 1 of 3, 2 of 3, 1 of 3, 1 of 2, 2 of 3, 1 of 2 within 41 s. Each
   *  reading lasted about 7 s, so the drawing does follow - but never twice within 3 s. */
  @Test fun theVideosLaneCardNeverChangesTheDrawingTwiceInARow() {
    val s = SceneSteady.Lanes()
    val script = listOf(0 to 2, 1 to 1, 0 to 2, 0 to 1, 1 to 1, 0 to 1)
    var last: Pair<Int, Int>? = null
    var lastAt = -10_000L
    var ms = 0L
    while (ms < 41_000) {
      val (l, r) = script[(ms * script.size / 41_000).toInt()]
      val out = s.update(lanes(l, r), 0, ms)!!
      val now = out.left to out.right
      if (last != null && now != last) { assertTrue("at $ms after $lastAt", ms - lastAt >= 2900); lastAt = ms }
      last = now
      ms += 33
    }
  }

  @Test fun aFlickerNeverReachesTheDrawing() {
    val s = SceneSteady.Lanes()
    for (ms in 0L..2000 step 33) s.update(lanes(1, 1), 0, ms)
    var ms = 2000L
    while (ms < 60_000) {                      // 2 s of "2 of 3", 1 s of "1 of 3", over and over
      val odd = (ms / 1000) % 3 == 2L
      val out = s.update(if (odd) lanes(0, 2) else lanes(1, 1), 0, ms)!!
      assertEquals(1, out.left); assertEquals(1, out.right)
      ms += 33
    }
  }

  @Test fun aRealChangeIsTakenAfterThreeSeconds() {
    val s = SceneSteady.Lanes()
    for (ms in 0L..5000 step 33) s.update(lanes(1, 1), 0, ms)
    var out = lanes(1, 1)
    for (ms in 5033L..7900 step 33) out = s.update(lanes(1, 2), 0, ms)!!
    assertEquals(1, out.right)                                      // 2.9 s of the new count: not yet
    for (ms in 7933L..8200 step 33) out = s.update(lanes(1, 2), 0, ms)!!
    assertEquals(2, out.right)
  }

  @Test fun unsureKeepsTheCountForEightSeconds() {
    val s = SceneSteady.Lanes()
    val unsure = LaneSceneView.Lanes(-1, -1, -1)
    for (ms in 0L..3000 step 33) s.update(lanes(0, 2), 0, ms)
    var out = lanes(0, 2)
    for (ms in 3033L..10_900 step 33) out = s.update(unsure, 0, ms)!!
    assertEquals(2, out.right)
    for (ms in 10_933L..11_200 step 33) out = s.update(unsure, 0, ms)!!
    assertEquals(-1, out.right)
  }

  @Test fun ourOwnLaneChangeMovesTheCountAtOnce() {
    val s = SceneSteady.Lanes()
    for (ms in 0L..3000 step 33) s.update(lanes(0, 2), 0, ms)
    val out = s.update(lanes(0, 2), 1, 3033)!!                     // we crossed the line to the right
    assertEquals(1, out.left); assertEquals(1, out.right)
    // the lane card catches up a second later: nothing more to change
    for (ms in 3066L..9000 step 33) { val o = s.update(if (ms < 4000) lanes(0, 2) else lanes(1, 1), 0, ms)!!; assertEquals(1, o.left); assertEquals(1, o.right) }
  }

  private val xs = FloatArray(33) { it * it * 0.1f }
  private fun line(r: Float) = FloatArray(33) { r }
  private fun feed(l: SceneSteady.Lines, rs: FloatArray, prob: FloatArray, style: IntArray, ms: Long) =
    l.update(Array(4) { xs }, Array(4) { line(rs[it]) }, prob, BooleanArray(4) { true }, style, DoubleArray(4) { 60.0 }, ms * 1_000_000 + 1)

  @Test fun aLineThatDropsOutForAMomentStaysOnTheScreen() {
    val l = SceneSteady.Lines()
    val rs = floatArrayOf(-5.4f, -1.8f, 1.8f, 5.4f)
    val st = IntArray(4) { LaneMap.DASH_WHITE }
    var ms = 0L
    while (ms < 2000) { feed(l, rs, floatArrayOf(0.9f, 0.9f, 0.9f, 0.9f), st, ms); ms += 33 }
    var low = 1f
    while (ms < 2300) {                         // 0.3 s without line 3
      feed(l, rs, floatArrayOf(0.9f, 0.9f, 0.9f, 0.1f), st, ms); ms += 33
      low = minOf(low, l.tracks.first { abs(it.r - 5.4) < 0.5 }.alpha)
    }
    assertTrue("still half there: $low", low > 0.45f)
    while (ms < 3500) { feed(l, rs, floatArrayOf(0.9f, 0.9f, 0.9f, 0.9f), st, ms); ms += 33 }
    assertTrue(l.tracks.first { abs(it.r - 5.4) < 0.5 }.alpha > 0.95f)
    assertEquals(4, l.tracks.size)
  }

  @Test fun paintChangesOnlyAfterItHeldAndYellowLongest() {
    val l = SceneSteady.Lines()
    val rs = floatArrayOf(-5.4f, -1.8f, 1.8f, 5.4f)
    val pr = floatArrayOf(0.9f, 0.9f, 0.9f, 0.9f)
    val base = intArrayOf(LaneMap.DOUBLE_YELLOW, LaneMap.DASH_WHITE, LaneMap.DASH_WHITE, LaneMap.SOLID_WHITE)
    var ms = 0L
    while (ms < 1000) { feed(l, rs, pr, base, ms); ms += 33 }
    // half a second of another reading on every line: nothing changes
    val other = intArrayOf(LaneMap.DASH_WHITE, LaneMap.SOLID_WHITE, LaneMap.SOLID_WHITE, LaneMap.DASH_WHITE)
    while (ms < 1500) { feed(l, rs, pr, other, ms); ms += 33 }
    fun at(r: Double) = l.tracks.first { abs(it.r - r) < 0.5 }
    assertEquals(LaneMap.DOUBLE_YELLOW, at(-5.4).style); assertEquals(LaneMap.DASH_WHITE, at(-1.8).style)
    // 1.2 s more: the white lines changed (cross-fading), the yellow one hasn't (it needs 2.5 s)
    while (ms < 2700) { feed(l, rs, pr, other, ms); ms += 33 }
    assertEquals(LaneMap.SOLID_WHITE, at(-1.8).style); assertEquals(LaneMap.DOUBLE_YELLOW, at(-5.4).style)
    while (ms < 4200) { feed(l, rs, pr, other, ms); ms += 33 }
    assertEquals(LaneMap.DASH_WHITE, at(-5.4).style)
  }

  @Test fun crossingALineIsSeenAsALaneChange() {
    val l = SceneSteady.Lines()
    val pr = floatArrayOf(0.9f, 0.9f, 0.9f, 0.9f)
    val st = IntArray(4) { LaneMap.DASH_WHITE }
    var ms = 0L
    var shifts = 0
    var off = 0.0                                // how far right we've moved (m); lines every 3.6 m
    while (ms < 8000) {
      if (ms in 2000..6000) off = (ms - 2000) / 4000.0 * 3.6
      // the camera numbers the lines around us: 1 is the first on our left, 2 the first on our right
      val rel = ((-1.8 - off) % 3.6 + 3.6) % 3.6 - 3.6           // the nearest line on the left, -3.6..0
      val rs = floatArrayOf((rel - 3.6).toFloat(), rel.toFloat(), (rel + 3.6).toFloat(), (rel + 7.2).toFloat())
      feed(l, rs, pr, st, ms)
      shifts += l.shift
      for (t in l.tracks) assertTrue("no line blinked: ${t.alpha}", t.slot < 0 || t.alpha > 0.25f || ms < 400 || abs(t.r) > 6.5)
      ms += 50
    }
    assertEquals(1, shifts)
  }

  @Test fun aCarNeverSpinsOnTheSpot() {
    // going our way 60 ft ahead at our speed; the detector's guess is right 60 % of the time and anything otherwise
    val f = SceneSteady.Facing()
    val rnd = java.util.Random(3)
    var hint = 0.0
    var t = 0.0
    while (t < 30) {
      if ((t * 10).toInt() % 3 == 0) hint = when (rnd.nextInt(10)) { 0, 1 -> PI; 2 -> PI / 2; 3 -> -PI / 2; else -> 0.0 }
      val psi = f.update(-3.6, 18.0 + rnd.nextGaussian() * 0.4, 12.0, 0.0, hint, 0.033)
      assertEquals("at $t s", 0.0, psi, 1e-9)
      t += 0.033
    }
  }

  @Test fun anOncomingCarFacesUsAndACrossingOneGoesAcross() {
    val on = SceneSteady.Facing()
    var psi = 0.0
    var t = 0.0
    while (t < 3) { psi = on.update(-7.0, 90.0 - 24.0 * t, 12.0, 0.0, 0.0, 0.05); t += 0.05 }   // closing at 24 m/s, we do 12
    assertEquals(PI, psi, 1e-9)
    val cross = SceneSteady.Facing()
    t = 0.0
    while (t < 3) { psi = cross.update(-15.0 + 8.0 * t, 25.0, 0.0, 0.0, Double.NaN, 0.05); t += 0.05 }   // left to right at 8 m/s, we stand
    assertEquals(PI / 2, psi, 0.15)
    // it stops in front of us: it stays as it was (no spin back along the road at once)
    val stopped = cross.update(9.0, 25.0, 0.0, 0.0, Double.NaN, 0.05)
    assertEquals(PI / 2, stopped, 0.15)
  }

  @Test fun aCarPastTheYellowLineFacesUsUnlessItDrivesOurWay() {
    // standing past the yellow line (we stand too), the detector guessing "our way": it faces us
    val a = SceneSteady.Facing()
    var psi = 0.0
    var t = 0.0
    while (t < 5) { psi = a.update(-5.5, 20.0, 0.0, 0.0, 0.0, 0.05, true); t += 0.05 }
    assertEquals(PI, psi, 1e-9)
    // but one really driving our way over there (passing, a turn lane) is drawn our way
    val b = SceneSteady.Facing()
    t = 0.0
    while (t < 5) { psi = b.update(-5.5, 20.0 + 2.0 * t, 12.0, 0.0, Double.NaN, 0.05, true); t += 0.05 }
    assertEquals(0.0, psi, 1e-9)
  }

  @Test fun theSpeedLimitSignHolds() {
    val h = SceneSteady.SpeedHold()
    val s = 1_000_000_000L
    assertEquals(17.88f, h.put(17.88f, 0), 0f)            // 40 mph: the first one shows at once
    assertEquals(17.88f, h.put(15.65f, 1 * s), 0f)        // 35 for 2 s: still 40
    assertEquals(17.88f, h.put(15.65f, 3 * s), 0f)
    assertEquals(17.88f, h.put(17.88f, 3 * s + 1), 0f)    // 40 again: the 35 is forgotten
    assertEquals(17.88f, h.put(15.65f, 4 * s), 0f)
    assertEquals(17.88f, h.put(15.65f, 6 * s), 0f)
    assertEquals(15.65f, h.put(15.65f, 6 * s + s / 2 + 1), 0f)   // read for 2.5 s: now 35
  }

  @Test fun aLineCrossedCountsOnceWhoeverTellsIt() {
    // 2.90.5 (owner, Nov 18: openpilot changed lanes, the camera's lines never showed it, the Scene moved over 10 s
    // later). [ms, camera, openpilot's state ("" off, "s" starting, "f" finishing), its direction] -> what the Scene does
    fun run(vararg steps: Array<Any>): List<Int> {
      val c = SceneSteady.Crossings()
      return steps.map { (ms, cam, st, dir) -> c.update(cam as Int, st != "", st == "f", dir as Int, (ms as Int).toLong()) }
    }
    fun s(ms: Int, cam: Int, st: String, dir: Int) = arrayOf<Any>(ms, cam, st, dir)
    // openpilot alone (the camera missed it): at once, once
    assertEquals(listOf(0, 0, -1, 0, 0), run(s(0, 0, "", 0), s(1000, 0, "s", -1), s(4000, 0, "f", -1), s(4500, 0, "f", -1), s(5000, 0, "", 0)))
    // the camera first, openpilot 1 s later: once
    assertEquals(listOf(-1, 0, 0), run(s(3000, -1, "s", -1), s(4000, 0, "f", -1), s(4500, 0, "", 0)))
    // openpilot first, the camera after: once
    assertEquals(listOf(1, 0), run(s(4000, 0, "f", 1), s(4800, 1, "", 0)))
    // two lane changes to the left, 8 s apart, each told by both: twice
    assertEquals(listOf(-1, 0, 0, -1, 0, 0), run(s(3000, -1, "s", -1), s(3800, 0, "f", -1), s(4500, 0, "", 0),
      s(11000, 0, "f", -1), s(11400, -1, "f", -1), s(12000, 0, "", 0)))
    // finishing unseen (starting, then off): counted at the end - 5 s of starting, as on Nov 18
    assertEquals(listOf(0, 0, -1, 0), run(s(17000, 0, "s", -1), s(20000, 0, "s", -1), s(22900, 0, "", 0), s(23500, 0, "", 0)))
    // ... but not a change ended after a moment of starting
    assertEquals(listOf(0, 0), run(s(1000, 0, "s", 1), s(2200, 0, "", 0)))
    // the model losing our lane's lines while we cross and finding them again: counted then, not again at the end
    val c = SceneSteady.Crossings()
    val seen = listOf(c.update(0, true, false, -1, 17000, 0.99f, 0.99f), c.update(0, true, false, -1, 18600, 0.2f, 0.1f),
      c.update(0, true, false, -1, 20000, 0.1f, 0.05f), c.update(0, true, false, -1, 21600, 0.64f, 0.92f),
      c.update(0, true, true, -1, 22600, 0.8f, 0.9f), c.update(0, false, false, 0, 23000, 0.9f, 0.9f))
    assertEquals(listOf(0, 0, 0, -1, 0, 0), seen)
    // by hand, no openpilot lane change: the camera alone
    assertEquals(listOf(1, 0, -1), run(s(1000, 1, "", 0), s(2000, 0, "", 0), s(9000, -1, "", 0)))
    // the camera seeing it twice (back over at once): both count, and openpilot's own then matches the last
    assertEquals(listOf(-1, 1, -1, 0), run(s(1000, -1, "s", -1), s(1300, 1, "s", -1), s(1600, -1, "s", -1), s(2500, 0, "f", -1)))
  }
}
