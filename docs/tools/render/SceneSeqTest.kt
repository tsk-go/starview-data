package com.starpilot.starview

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import org.json.JSONArray
import org.json.JSONObject
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.sin

/**
 * The Scene view as a film of a recorded drive, off the tablet, twice: as it was (every frame from that instant's
 * guess) and held still (2.90.0, SceneSteady) - to see and count what jumps. Same data as LaneSceneTest
 * (laneData/drive_*.json at 4 a second, osm_all.json); laneData/seq.txt lists the stretches, one per line:
 * "window,YYYY-MM-DD HH:MM:SS (Eastern),seconds,name[,day][,flip][,spin]" (flip: the lane card changes its mind like in
 * the owner's Nov 17 video; spin: three made-up detected cars whose guessed heading flickers). The 30 s before a stretch
 * are replayed without drawing. Writes picOut/<name>_before/NNNN.png, picOut/<name>_after/NNNN.png (10 a second) and
 * picOut/<name>.txt (what changed from one frame to the next). Drive data never goes in git.
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [35], qualifiers = "w1472dp-h920dp-land-xhdpi")
class SceneSeqTest {
  private val out = System.getProperty("picOut") ?: "/tmp"
  private val dir = System.getProperty("laneData") ?: "/tmp"

  private fun fa(a: JSONArray) = FloatArray(a.length()) { a.getDouble(it).toFloat() }
  private fun mix(a: FloatArray, b: FloatArray, k: Float) = FloatArray(a.size) { a[it] + (b.getOrElse(it) { _ -> a[it] } - a[it]) * k }

  private class Rec(val t: Double, val f: JSONObject, val x: FloatArray, val px: FloatArray, val py: FloatArray, val lanes: Array<FloatArray>,
                    val lp: FloatArray, val edges: Array<FloatArray>, val estd: FloatArray, val lstd: FloatArray,
                    var ox: Double = 0.0, var oy: Double = 0.0, var yaw: Double = 0.0, var dist: Double = 0.0)

  private fun snap(a: Rec, b: Rec, k: Float): ModelSnapshot {
    val z = FloatArray(a.x.size)
    return ModelSnapshot(0L, mix(a.px, b.px, k), mix(a.py, b.py, k), z, z,
      Array(4) { a.x }, Array(4) { mix(a.lanes[it], b.lanes[it], k) }, Array(4) { z }, mix(a.lp, b.lp, k),
      Array(2) { a.x }, Array(2) { mix(a.edges[it], b.edges[it], k) }, Array(2) { z }, mix(a.estd, b.estd, k),
      mix(a.lstd, b.lstd, k), z, false, 0f, false, 0, false, FloatArray(8), 0f)
  }

  @Test fun film() {
    val ctx = RuntimeEnvironment.getApplication()
    LaneMap.offline = true
    LaneMap.parse(File(dir, "osm_all.json").readText())
    for (line in File(dir, "seq.txt").readLines().map { it.trim() }.filter { it.isNotBlank() && !it.startsWith("#") }) {
      val p = line.split(',')
      val t0 = LocalDateTime.parse(p[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toEpochSecond(ZoneOffset.UTC) + 5 * 3600.0
      val secs = p[2].toDouble()
      val name = p[3]
      val all = JSONArray(File(dir, "drive_${p[0]}.json").readText())
      val recs = ArrayList<Rec>()
      for (i in 0 until all.length()) {
        val f = all.getJSONObject(i)
        val t = f.getDouble("t")
        if (t < t0 - 30 || t > t0 + secs + 0.5) continue
        val ln = f.getJSONArray("lanes"); val ed = f.getJSONArray("edges")
        recs.add(Rec(t, f, fa(f.getJSONArray("lx")), fa(f.getJSONArray("px")), fa(f.getJSONArray("py")), Array(4) { fa(ln.getJSONArray(it)) },
          fa(f.getJSONArray("lp")), Array(2) { fa(ed.getJSONArray(it)) }, fa(f.getJSONArray("estd")), fa(f.getJSONArray("lstd"))))
      }
      if (recs.size < 8) continue
      // the car's own motion, like the tablet's Odometry: speed and heading added up (smooth), not GPS positions
      for (i in 1 until recs.size) {
        val a = recs[i - 1]; val b = recs[i]
        val yaw = PI / 2 - Math.toRadians(b.f.getDouble("bearing"))
        val d = (a.f.getDouble("speed") + b.f.getDouble("speed")) / 2 * (b.t - a.t)
        b.yaw = yaw; b.ox = a.ox + d * cos(yaw); b.oy = a.oy + d * sin(yaw); b.dist = a.dist + d
      }
      recs[0].yaw = recs[1].yaw
      val log = StringBuilder()
      for (steady in listOf(false, true)) {
        val mode = if (steady) "after" else "before"
        val od = File(out, "${name}_$mode").apply { mkdirs() }
        LaneFit.steadyJumps = steady
        val v = LaneSceneView(ctx)
        v.steady = steady
        v.night = "day" !in p
        val w = 1472; val h = 920
        v.measure(View.MeasureSpec.makeMeasureSpec(w, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(h, View.MeasureSpec.EXACTLY))
        v.layout(0, 0, w, h)
        val bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val cv = Canvas(bmp)
        var fit: LaneFit.Fit? = null
        var built: LaneMap.Built? = null
        var bLat = 0.0; var bLon = 0.0
        var ri = 0
        var n = 0
        var hidden = 0; var toggles = 0; var jumps = 0; var lastHidden: Boolean? = null
        var lastShift = Double.NaN
        val rnd = java.util.Random(7)
        var hint = doubleArrayOf(0.0, 0.0, PI)
        var hintAt = -1.0
        var lastLane: IntArray? = null
        var t = t0 - 29.0
        while (t <= t0 + secs) {
          // every recorded moment up to now: a fit (the tablet fits 5 times a second; the recording has 4)
          while (ri < recs.size - 1 && recs[ri + 1].t <= t) {
            ri++
            val r = recs[ri]
            val lat = r.f.getDouble("lat"); val lon = r.f.getDouble("lon")
            if (built == null || hypot((lon - bLon) * 84000, (lat - bLat) * 110540) > 400) { LaneMap.buildNow(lat, lon); built = LaneMap.built; bLat = lat; bLon = lon }
            val b = built!!
            val xy = b.xy(lat, lon)
            val odo = doubleArrayOf(r.ox, r.oy, r.yaw, r.dist)
            val cam = LaneFit.Cam.of(snap(r, r, 0f))!!
            fit = LaneFit.run(b, LaneFit.Pose(xy[0], xy[1], Math.toRadians(r.f.getDouble("bearing"))), r.f.getDouble("acc"), cam, odo, (r.t * 1000).toLong(), fit)
            if (r.t >= t0) {
              if (fit.hidden) hidden++
              if (lastHidden != null && lastHidden != fit.hidden) { toggles++; log.append(String.format("%s %6.2f s: map %s (%s)\n", mode, r.t - t0, if (fit.hidden) "hidden" else "shown", LaneFit.status)) }
              if (!fit.hidden && lastShift.isFinite() && abs(fit.shift - lastShift) > 1.2) { jumps++; log.append(String.format("%s %6.2f s: the fit moved %.1f m sideways\n", mode, r.t - t0, fit.shift - lastShift)) }
            }
            lastHidden = fit.hidden; lastShift = if (fit.hidden) Double.NaN else fit.shift
          }
          val a = recs[ri]; val b2 = recs[minOf(ri + 1, recs.size - 1)]
          val k = if (b2.t > a.t) ((t - a.t) / (b2.t - a.t)).coerceIn(0.0, 1.0) else 0.0
          val m = snap(a, b2, k.toFloat())
          val odo = doubleArrayOf(a.ox + (b2.ox - a.ox) * k, a.oy + (b2.oy - a.oy) * k, a.yaw + Math.IEEEremainder(b2.yaw - a.yaw, 2 * PI) * k, a.dist + (b2.dist - a.dist) * k)
          val things = ArrayList<LaneSceneView.Thing>()
          if (!a.f.isNull("lead") && !b2.f.isNull("lead")) {
            val la = a.f.getJSONArray("lead"); val lb = b2.f.getJSONArray("lead")
            val L = SceneSprites.models["car_sedan"]?.L ?: 4.2f
            things.add(LaneSceneView.Thing(-100, "car_sedan", la.getDouble(1) + (lb.getDouble(1) - la.getDouble(1)) * k, la.getDouble(0) + (lb.getDouble(0) - la.getDouble(0)) * k + L / 2, 0.0, true, false))
          }
          val ts = t - t0
          val vEgo = a.f.getDouble("speed")
          if ("spin" in p && ts >= 0) {
            // what the detector does: three cars (one ahead in the lane on our right going our way, one parked on the right that we
            // pass, one coming toward us), each with a heading guess that is right most of the time and flips
            if (ts - hintAt >= 0.3) { hintAt = ts; hint = doubleArrayOf(pick(rnd, 0.0), pick(rnd, 0.0), pick(rnd, PI)) }
            fun car(id: Long, model: String, r: Double, f: Double, hd: Double) {
              if (f < -6 || f > 120) return
              things.add(LaneSceneView.Thing(id, model, r, f, if (steady) 0.0 else hd, false, false).also { if (steady) { it.hint = hd; it.road = 0.0 } })
            }
            car(-5_000_001, "car_suv", 3.6, 20.0 + 2 * sin(ts / 3), hint[0])
            car(-5_000_002, "car_hatch", 7.4, 60.0 - (ts % 12.0) * vEgo.coerceAtLeast(4.0), hint[1])
            car(-5_000_003, "car_sedan", -7.4, 110.0 - (ts % 8.0) * (vEgo + 12.0), hint[2])
          }
          // the lane card: from the lane the map puts us in; "flip": changing its mind as in the owner's video
          val mt = fit?.match
          var lanes = if (mt != null) LaneSceneView.Lanes(mt.lane - 1, mt.of - mt.lane, LaneMap.DOUBLE_YELLOW) else null
          if ("flip" in p && ts >= 0) {
            val script = arrayOf(intArrayOf(0, 2), intArrayOf(1, 1), intArrayOf(0, 2), intArrayOf(0, 1), intArrayOf(1, 1), intArrayOf(0, 1))
            val s = script[((ts / secs) * script.size).toInt().coerceIn(0, script.size - 1)]
            lanes = LaneSceneView.Lanes(s[0], s[1], LaneMap.DOUBLE_YELLOW)
          }
          // the camera's line reader, reading the road right: our lane's left line is the double yellow when the map
          // puts us in the left lane, the right one solid in the right lane, the rest dashed (the reader itself isn't
          // in the recording)
          if (mt != null) lastLane = intArrayOf(mt.lane, mt.of)
          val ll = lastLane
          val marks = if (ll == null) null else Array(4) { i ->
            val leftN = ll[0] - 1; val rightN = ll[1] - ll[0]
            when {
              (i == 1 && leftN == 0) || (i == 0 && leftN == 1) -> LaneMarks.Mark(LaneMarks.SOLID, true, true)
              (i == 2 && rightN == 0) || (i == 3 && rightN == 1) -> LaneMarks.Mark(LaneMarks.SOLID, false, false)
              (i == 0 && leftN == 0) || (i == 3 && rightN == 0) -> LaneMarks.Mark(LaneMarks.NONE, false, false)
              else -> LaneMarks.Mark(LaneMarks.DASHED, false, false)
            }
          }
          val nowNs = ((t - (t0 - 30)) * 1e9).toLong() + 1_000_000_000L
          val fr = v.frameOf(m, fit, odo, nowNs, things, true, false, false, marks, lanes, vEgo)
          v.frame = fr
          if (t >= t0) {
            v.draw(cv)
            FileOutputStream(File(od, String.format("%04d.png", n))).use { bmp.compress(Bitmap.CompressFormat.PNG, 100, it) }
            n++
          } else if (t >= t0 - 3) v.draw(cv)        // the picture's memory is warm when the film starts
          t += 0.1
        }
        log.append("$mode: $n frames, fits hidden $hidden, map shown/hidden changes $toggles, sideways jumps of the fit $jumps\n")
      }
      File(out, "$name.txt").writeText(log.toString())
    }
  }

  private fun pick(r: java.util.Random, right: Double): Double = when (r.nextInt(10)) { 0, 1 -> right + PI; 2 -> right + PI / 2; 3 -> right - PI / 2; 4 -> Double.NaN; else -> right }
}
