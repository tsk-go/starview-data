package com.starpilot.starview

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.hypot

/**
 * 2.90.0: LaneRibbon (the road is generated, not traced) on made-up maps of the three places where the map's own lines
 * went wrong (owner, Nov 17: "fix the S-bends and the after-exit moment"): a road that gains and loses a lane, an exit
 * the map starts at the middle of the road, and a ramp it ends at the middle of the road it joins - and on what is drawn
 * of the road behind us. 2.90.2: a left-turn bay on a city street, and the rounded corners of a cross street. Each is driven at
 * 34 mph with LaneAnchor placing us, and the thread the road is drawn around is checked for bends that aren't there.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35])
class LaneRibbonTest {
  private val lat0 = 40.7; private val lon0 = -74.0
  private val kx = 111320.0 * Math.cos(Math.toRadians(lat0)); private val ky = 110540.0
  private fun way(id: Int, hw: String, lanes: Int, vararg xy: Double): String {
    val pts = (0 until xy.size / 2).joinToString(",") { """{"lat":${lat0 + xy[2 * it + 1] / ky},"lon":${lon0 + xy[2 * it] / kx}}""" }
    return """{"type":"way","id":$id,"tags":{"highway":"$hw","name":"R$id","oneway":"yes","lanes":"$lanes"},"geometry":[$pts]}"""
  }
  private fun build(vararg ways: String): LaneMap.Built {
    LaneMap.offline = true
    LaneMap.parse("""{"elements":[${ways.joinToString(",")}]}""")
    LaneMap.buildNow(lat0, lon0)
    return LaneMap.built!!
  }

  private val grid = FloatArray(33) { it * it * 0.1f }
  private fun cam(): LaneFit.Cam {
    val ys = floatArrayOf(-5.4f, -1.8f, 1.8f, 5.4f)
    return LaneFit.Cam(Array(4) { grid }, Array(4) { i -> FloatArray(33) { ys[i] } }, floatArrayOf(0.9f, 0.95f, 0.95f, 0.9f),
      Array(2) { grid }, arrayOf(FloatArray(33) { -9f }, FloatArray(33) { 9f }), floatArrayOf(2f, 2f))
  }

  private var last: LaneFit.Fit? = null      // what the last step of [drive] gave to draw
  private var anchor: LaneAnchor.State? = null   // where LaneAnchor has us in [drive]

  /** drives along [path] (map metres east / north, at least 2 points) and returns the ribbon's memory at the end */
  private fun drive(b: LaneMap.Built, path: List<DoubleArray>, until: Double = Double.MAX_VALUE, each: (LaneRibbon.State) -> Unit = {}): LaneRibbon.State {
    val ast = LaneAnchor.State(); val rst = LaneRibbon.State()
    anchor = ast
    rst.trace = ArrayList()
    var dist = 0.0; var t = 0.0
    var seg = 0; var at = 0.0
    while (seg < path.size - 1 && dist < until) {
      val a = path[seg]; val z = path[seg + 1]
      val l = hypot(z[0] - a[0], z[1] - a[1])
      if (at > l) { at -= l; seg++; continue }
      val x = a[0] + (z[0] - a[0]) * at / l; val y = a[1] + (z[1] - a[1]) * at / l
      val h = atan2(z[0] - a[0], z[1] - a[1])
      val odo = doubleArrayOf(x, y, PI / 2 - h, dist)
      val now = (t * 1000).toLong() + 1000
      val f = LaneAnchor.run(b, LaneFit.Pose(x, y, h), 0.5, cam(), odo, now, ast, null, null, 0)
      assertNotNull("on a road at ${x.toInt()}, ${y.toInt()}", f)
      val r = LaneRibbon.update(b, ast, rst, cam(), odo, now)
      assertNotNull("a road drawn at ${x.toInt()}, ${y.toInt()}: ${rst.trace}", r)
      last = r
      each(rst)
      at += 15.0 * 0.25; dist += 15.0 * 0.25; t += 0.25
    }
    return rst
  }

  /** the settled thread as (x, y) */
  private fun thread(st: LaneRibbon.State) = st.smp.filter { it.done }.map { doubleArrayOf(it.sx, it.sy) }

  /** the biggest turn of the thread from one 10 ft step to the next (degrees), and its sum of turns to and fro */
  private fun bends(p: List<DoubleArray>): Pair<Double, Double> {
    var worst = 0.0; var total = 0.0
    for (i in 2 until p.size) {
      val a = atan2(p[i - 1][0] - p[i - 2][0], p[i - 1][1] - p[i - 2][1]); val z = atan2(p[i][0] - p[i - 1][0], p[i][1] - p[i - 1][1])
      val d = abs(Math.toDegrees(Math.IEEEremainder(z - a, 2 * PI)))
      worst = maxOf(worst, d); total += d
    }
    return worst to total
  }

  @Test fun aLaneMoreAndALaneFewerDoNotBendTheRoad() {
    // straight north: 3 lanes, then 4 for 1000 ft, then 3 again. The map's middle line is straight, so its lanes jump
    // half a lane sideways at both joints.
    val b = build(way(1, "primary", 3, 0.0, -400.0, 0.0, 0.0), way(2, "primary", 4, 0.0, 0.0, 0.0, 300.0), way(3, "primary", 3, 0.0, 300.0, 0.0, 700.0))
    val x0 = b.ways.first { it.id == 1L }.lanes.minOf { it.x[0].toDouble() }        // the left lane
    var worst = 0.0
    val st = drive(b, listOf(doubleArrayOf(x0, -380.0), doubleArrayOf(x0, 250.0))) { s ->
      val (w, _) = bends(thread(s)); worst = maxOf(worst, w)
    }
    assertTrue("the thread never turns more than half a degree in a step: $worst", worst < 0.5)
    val xs = thread(st).map { it[0] }
    assertTrue("and stays within a lane of where it began: ${xs.minOrNull()} .. ${xs.maxOrNull()}", xs.max() - xs.min() < 2.2)
    assertTrue("laid once: ${st.trace}", st.relaid == 1)
  }

  @Test fun theRoadBehindUsIsKeptButNotDrawn() {
    // 1000 ft of what we drove stays in memory (a fork there can still be taken the other way), but only 230 ft of it is
    // drawn: on a ramp that comes around, the rest lay across the road ahead like a street crossing it
    val b = build(way(1, "primary", 2, 0.0, -700.0, 0.0, 700.0))
    val x0 = b.ways[0].lanes.minOf { it.x[0].toDouble() }
    val st = drive(b, listOf(doubleArrayOf(x0, -680.0), doubleArrayOf(x0, 0.0)))
    val geo = last!!.ribbon!!
    val behind = geo.van * LaneRibbon.STEP
    assertTrue("drawn from 200 - 240 ft behind us: ${behind.toInt()} m", behind > 60 && behind < 74)
    val road = last!!.built.ways.last()
    assertTrue("nothing drawn further back: ${road.py.min()}", road.py.min() > -75.0)
    assertTrue("while the thread still reaches 1000 ft back: ${st.smp.first().y}", st.smp.first().y < -280.0)
  }

  @Test fun anExitTheMapStartsAtTheMiddleOfTheRoadLeavesFromOurLane() {
    // 3 lanes north; at (0, 0) an exit ramp starts - on the map at the road's middle line - and swings off to the right
    val b = build(way(1, "motorway", 3, 0.0, -400.0, 0.0, 0.0), way(2, "motorway", 3, 0.0, 0.0, 0.0, 500.0),
      way(3, "motorway_link", 1, 0.0, 0.0, 3.0, 40.0, 9.0, 90.0, 30.0, 180.0, 80.0, 280.0, 150.0, 380.0))
    val xr = b.ways.first { it.id == 1L }.lanes.maxOf { it.x[0].toDouble() }        // the right lane
    // we drive the right lane and then where the ramp really is: straight on at first, then away to the right
    val st = drive(b, listOf(doubleArrayOf(xr, -380.0), doubleArrayOf(xr, 0.0), doubleArrayOf(xr + 1.0, 40.0), doubleArrayOf(11.0, 90.0), doubleArrayOf(30.0, 180.0), doubleArrayOf(80.0, 280.0)))
    val p = thread(st).filter { it[1] > -60 && it[1] < 200 }
    assertTrue("the thread is on the ramp: ${p.lastOrNull()?.toList()} ${st.trace}", p.isNotEmpty() && p.last()[0] > 20.0)
    // from our lane it only ever moves right: never back toward the middle of the road on its way to the ramp
    var most = -1e9
    for (q in p) { assertTrue("no swing to the left at y ${q[1].toInt()}: ${q[0]} after $most\n${st.trace}", q[0] > most - 0.35); most = maxOf(most, q[0]) }
    assertTrue("laid from scratch once, and once more from the fork: ${st.trace}", st.relaid <= 2)
  }

  @Test fun aRampTheMapEndsAtTheMiddleOfTheRoadJoinsItsFirstLane() {
    // a ramp from the south-east that the map ends at the middle line of a 3-lane road at (0, 0)
    val b = build(way(1, "motorway", 3, 0.0, -400.0, 0.0, 0.0), way(2, "motorway", 3, 0.0, 0.0, 0.0, 500.0),
      way(3, "motorway_link", 1, 90.0, -420.0, 40.0, -250.0, 16.0, -120.0, 9.0, -40.0, 0.0, 0.0))
    val xr = b.ways.first { it.id == 2L }.lanes.maxOf { it.x[0].toDouble() }        // the road's right lane
    // we come down the ramp and merge into the right lane (never further left)
    val st = drive(b, listOf(doubleArrayOf(88.0, -410.0), doubleArrayOf(40.0, -250.0), doubleArrayOf(16.0, -120.0), doubleArrayOf(xr + 4.0, -40.0), doubleArrayOf(xr, 20.0), doubleArrayOf(xr, 300.0)), until = 425.0)
    val p = thread(st).filter { it[1] > -60 && it[1] < 250 }
    assertTrue("the thread around the joint: ${p.size} steps ${st.trace}", p.size > 20)
    for (q in p) assertTrue("the thread never swings in past the right lane (x $xr) at y ${q[1].toInt()}: ${q[0]}\n${st.trace}", q[0] > xr - 0.9)
    assertTrue("laid once: ${st.trace}", st.relaid == 1)
  }
  /** a ramp of two lanes joining a road that goes on with four, from the left ([m] 1) or the right ([m] -1): we come down
   *  the ramp in its left lane */
  private fun twoLaneRamp(m: Double): Pair<LaneRibbon.State, List<Int>> {
    // the road is 2 lanes before the ramp and 4 after it. The map ends the ramp at the road's middle line, where its
    // two lanes lie on the road's two middle lanes - really they become the two lanes on the side the ramp comes from.
    val b = build(way(1, "motorway", 2, 0.0, -400.0, 0.0, 0.0), way(2, "motorway", 4, 0.0, 0.0, 0.0, 500.0),
      way(3, "motorway_link", 2, -100.0 * m, -400.0, -40.0 * m, -200.0, -14.0 * m, -80.0, -4.0 * m, -25.0, 0.0, 0.0))
    // the ramp's left lane as it really runs: into the road's first lane (from the left) / its third (from the right)
    val x = if (m > 0) -5.4 else 1.8
    val seen = ArrayList<Int>()
    val st = drive(b, listOf(doubleArrayOf(-100.0 * m - 1.8, -400.0), doubleArrayOf(-40.0 * m - 1.8, -200.0), doubleArrayOf(-14.0 * m - 1.8 + (x + 1.8) * 0.3, -80.0),
      doubleArrayOf(-4.0 * m - 1.8 + (x + 1.8) * 0.75, -25.0), doubleArrayOf(x, 0.0), doubleArrayOf(x, 250.0))) {
      if (anchor!!.wayId == 2L) seen.add(anchor!!.lane)
    }
    return st to seen
  }

  @Test fun aTwoLaneRampFromTheLeftBringsItsLanesAsTheRoadsFirstTwo() {
    val (st, seen) = twoLaneRamp(1.0)
    assertTrue("on the road it joined: ${st.trace}", seen.size > 20)
    assertTrue("we are in the road's first lane from the moment we are on it, and stay there: $seen\n${st.trace}", seen.all { it == 0 })
    assertTrue("the thread too: ${st.trace}", st.smp.filter { it.wayId == 2L }.all { it.ci == 0 })
    assertTrue("laid once: ${st.trace}", st.relaid == 1)
  }

  @Test fun aTwoLaneRampFromTheRightBringsItsLanesAsTheRoadsLastTwo() {
    val (st, seen) = twoLaneRamp(-1.0)
    assertTrue("on the road it joined: ${st.trace}", seen.size > 20)
    assertTrue("we are in the road's third lane (the ramp's left one) from the moment we are on it: $seen\n${st.trace}", seen.all { it == 2 })
    assertTrue("the thread too: ${st.trace}", st.smp.filter { it.wayId == 2L }.all { it.ci == 2 })
    assertTrue("laid once: ${st.trace}", st.relaid == 1)
  }

  @Test fun aRampTheMapBringsInOverALongWayJoinsWithoutSwingingBack() {
    // like the recorded ramp: two lanes, in short pieces, brought in to the middle of a 3-lane road over 500 ft - for
    // its last 190 ft the map has its left lane inside the road's first lane and beyond, and its last piece is 100 ft
    // long. We come down its left lane.
    val b = build(way(1, "motorway", 1, 0.0, -500.0, 0.0, 0.0), way(2, "motorway", 3, 0.0, 0.0, 0.0, 500.0),
      way(3, "motorway_link", 2, -60.0, -500.0, -30.0, -300.0, -10.0, -150.0), way(4, "motorway_link", 2, -10.0, -150.0, -5.0, -100.0),
      way(5, "motorway_link", 2, -5.0, -100.0, -2.5, -60.0), way(6, "motorway_link", 2, -2.5, -60.0, -1.2, -30.0),
      way(7, "motorway_link", 2, -1.2, -30.0, 0.0, 0.0))
    val x1 = -LaneRibbon.LW                                              // the road's first lane
    val seen = ArrayList<Int>()
    val st = drive(b, listOf(doubleArrayOf(-61.8, -500.0), doubleArrayOf(-31.8, -300.0), doubleArrayOf(-11.8, -150.0), doubleArrayOf(x1, 0.0), doubleArrayOf(x1, 250.0))) {
      if (anchor!!.wayId == 2L) seen.add(anchor!!.lane)
    }
    val p = thread(st).filter { it[1] > -150 && it[1] < 200 }
    assertTrue("the thread around the joint: ${p.size} steps ${st.trace}", p.size > 80)
    var most = -1e9
    for (q in p) {
      assertTrue("the thread never goes in past the first lane (x $x1) at y ${q[1].toInt()}: ${q[0]}\n${st.trace}", q[0] < x1 + 0.35)
      assertTrue("nor swings back out at y ${q[1].toInt()}: ${q[0]} after $most", q[0] > most - 0.35); most = maxOf(most, q[0])
    }
    assertTrue("we are in the road's first lane from the moment we are on it: $seen", seen.size > 20 && seen.all { it == 0 })
    assertTrue("laid once: ${st.trace}", st.relaid == 1)
  }

  /** a two-way street of [lanes] lanes (or "f|b" lanes forward / backward with [turnF] the forward turn lanes) with
   *  parking both sides, through the points */
  private fun street(id: Int, hw: String, lanes: String, turnF: String = "", vararg xy: Double): String {
    val pts = (0 until xy.size / 2).joinToString(",") { """{"lat":${lat0 + xy[2 * it + 1] / ky},"lon":${lon0 + xy[2 * it] / kx}}""" }
    val l = if ('|' in lanes) lanes.split('|').let { """"lanes":"${it[0].toInt() + it[1].toInt()}","lanes:forward":"${it[0]}","lanes:backward":"${it[1]}"""" } else """"lanes":"$lanes""""
    val t = if (turnF.isEmpty()) "" else ""","turn:lanes:forward":"$turnF""""
    return """{"type":"way","id":$id,"tags":{"highway":"$hw","name":"S$id","oneway":"no",$l$t,"parking:both":"lane"},"geometry":[$pts]}"""
  }

  @Test fun aLeftTurnBayDoesNotSwingTheRoad() {
    // 2.90.2, Nov 18: a block of two-way street, a 100 ft piece with a left-turn lane added our way
    // (the map makes it a lane wider, and gives the blocks beside it a painted median to match), then the street again.
    // The road ahead swung sideways and back there; our lane goes straight on and the turn lane opens on its left.
    val b = build(street(1, "tertiary", "2", "", 0.0, -500.0, 0.0, 0.0), street(2, "tertiary", "2|1", "left|", 0.0, 0.0, 0.0, 30.0),
      street(3, "tertiary", "2", "", 0.0, 30.0, 0.0, 500.0))
    val x0 = 1.6
    val st = drive(b, listOf(doubleArrayOf(x0, -480.0), doubleArrayOf(x0, -150.0)))
    val p = thread(st).filter { it[1] > -200 && it[1] < 300 }
    assertTrue("the thread around the bay: ${p.size} steps", p.size > 100)
    for (q in p) assertTrue("our lane runs straight on at y ${q[1].toInt()}: x ${q[0]}\n${st.trace}", abs(q[0] - p[0][0]) < 0.3)
    assertTrue("our lane, not the middle of the street: ${p[0][0]}", abs(p[0][0] - x0) < 0.6)
  }

  @Test fun aSmallStreetHasItsMiddleLineWhenTheMapCountsItsLanes() {
    // 2.90.4 (owner, Nov 18, on a two-way residential street with a double yellow line and parking both sides: "No
    // lines"): a small two-way street had no line down its middle, and since 2.90.2 no edge line beside its parking
    // lanes either. The map counting its lanes (or lane_markings=yes) means they are painted.
    val pts = listOf(0.0 to -400.0, 0.0 to 400.0).joinToString(",") { """{"lat":${lat0 + it.second / ky},"lon":${lon0 + it.first / kx}}""" }
    for ((tags, painted) in listOf(""""lanes":"2",""" to true, "" to false, """"lane_markings":"yes",""" to true,
                                   """"lanes":"2","lane_markings":"no",""" to false)) {
      val b = build("""{"type":"way","id":1,"tags":{"highway":"residential","name":"S1","oneway":"no",$tags"parking:both":"lane"},"geometry":[$pts]}""")
      drive(b, listOf(doubleArrayOf(1.5, -380.0), doubleArrayOf(1.5, -60.0)))
      val road = last!!.built.ways.single { it.id == 1L }
      assertEquals("{$tags}: a double yellow line", painted, road.lines.any { it.style == LaneMap.DOUBLE_YELLOW })
    }
  }

  @Test fun aStreetGoingOffOnOneSideStaysOnThatSide() {
    // 2.90.2: a one-way street starting at ours and going off to the left only (a T): the start of its asphalt, laid
    // under our road, stuck out 20 ft past our right kerb
    val b = build(street(1, "tertiary", "2", "", 0.0, -400.0, 0.0, 0.0, 0.0, 400.0), street(2, "residential", "1", "", 0.0, 0.0, -300.0, 0.0))
    drive(b, listOf(doubleArrayOf(1.6, -380.0), doubleArrayOf(1.6, -60.0)))
    val ways = last!!.built.ways
    val road = ways.single { it.id == 1L }
    val kerb = road.right.indices.filter { abs(road.rightY[it]) < 30 }.maxOf { road.right[it] }
    val arm = ways.filter { it.id != 1L && it.id < 1_000_000_000_000L }
    assertTrue("the street is drawn: ${arm.size}", arm.isNotEmpty())
    for (w in arm) for (x in w.left + w.right) assertTrue("no asphalt past our right kerb ($kerb): $x", x <= kerb + 0.1)
  }

  /** the signed area of a way's asphalt outline (its left side out, its right side back) */
  private fun turn(w: LaneMap.BWay): Double {
    val xs = w.left.toList() + w.right.reversed(); val ys = w.leftY.toList() + w.rightY.reversed()
    var a = 0.0
    for (i in xs.indices) { val j = (i + 1) % xs.size; a += xs[i].toDouble() * ys[j] - xs[j].toDouble() * ys[i] }
    return a
  }

  @Test fun aCrossStreetsCornersAreRounded() {
    // 2.90.2 (owner, Nov 18: "the corners are not sharp edges, it's a little bit rounded"): a street crossing ours
    val b = build(street(1, "tertiary", "2", "", 0.0, -400.0, 0.0, 0.0, 0.0, 400.0), street(2, "residential", "2", "", -300.0, 0.0, 0.0, 0.0, 300.0, 0.0))
    drive(b, listOf(doubleArrayOf(1.6, -380.0), doubleArrayOf(1.6, -60.0)))
    val ways = last!!.built.ways
    val corners = ways.filter { it.id >= 1_000_000_000_000L }
    assertTrue("four rounded corners: ${corners.size}", corners.size == 4)
    for (c in corners) {
      val k = c.lines.single { it.style == LaneMap.CURB }
      val dx = abs(k.x.last() - k.x.first()); val dy = abs(k.y.last() - k.y.first())
      assertTrue("each bends from along our street to along the cross street: $dx x $dy", dx > 3 && dy > 3 && dx < 7 && dy < 7)
      // the bend lies inside the square corner (its middle is nearer the crossing than the square's corner would be)
      val m = k.x.size / 2
      val sq = c.left.all { it == c.left[0] } && c.leftY.all { it == c.leftY[0] }
      val cx = if (sq) c.left[0] else c.right[0]; val cy = if (sq) c.leftY[0] else c.rightY[0]
      val corner = hypot((cx - 0.0).toDouble(), (cy - 0.0).toDouble())
      assertTrue("rounded off, not square", hypot(k.x[m].toDouble(), k.y[m].toDouble()) > corner + 0.5)
      // its asphalt goes round the same way as the roads' (the Scene fills all the asphalt as one shape: a piece going
      // round the other way left a light seam where it met the street)
      assertTrue("the same way round as our road", Math.signum(turn(c)) == Math.signum(turn(ways.single { it.id == 1L })))
    }
    // and our kerbs stop where the bends start
    val road = ways.single { it.id == 1L }
    for (k in road.lines.filter { it.style == LaneMap.CURB }) for (j in k.y.indices)
      assertTrue("no kerb across the crossing: ${k.x[j]}, ${k.y[j]}", abs(k.y[j]) > 4.0)
  }
}
