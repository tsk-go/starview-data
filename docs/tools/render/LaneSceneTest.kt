package com.starpilot.starview

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
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
import kotlin.math.hypot

/**
 * The new Scene view on a recorded drive, off the tablet: the drive's model lines and GPS (laneData/drive_*.json),
 * OpenStreetMap ways (laneData/osm_all.json), LaneMap + LaneFit replayed over the 90 s before each moment, then
 * LaneSceneView drawn at the tablet's size. Moments in laneData/picks.txt: "window,YYYY-MM-DD HH:MM:SS (Eastern),name
 * [,day][,cars][,nan][,lcL|lcR][,top][,zoom=D][,tilt=E]" (nan: two cars with the detector's NaN; lcL / lcR: a lane
 * change to that side). Writes picOut/<name>.png (+ _top.png: bird's-eye check) and log.txt.
 * Drive data never goes in git.
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [35], qualifiers = "w1472dp-h920dp-land-xhdpi")
class LaneSceneTest {
  private val out = System.getProperty("picOut") ?: "/tmp"
  private val dir = System.getProperty("laneData") ?: "/tmp"
  private val picks = File(dir, "picks.txt").readLines().map { it.trim() }.filter { it.isNotBlank() && !it.startsWith("#") }

  private fun fa(a: JSONArray) = FloatArray(a.length()) { a.getDouble(it).toFloat() }

  private fun snapshot(f: JSONObject, lcDir: Int = 0): ModelSnapshot {
    val x = fa(f.getJSONArray("lx"))
    val n = x.size
    val lanes = f.getJSONArray("lanes")
    val edges = f.getJSONArray("edges")
    val z = FloatArray(n)
    return ModelSnapshot(0L, fa(f.getJSONArray("px")), fa(f.getJSONArray("py")), z, z,
      Array(4) { x }, Array(4) { fa(lanes.getJSONArray(it)) }, Array(4) { z }, fa(f.getJSONArray("lp")),
      Array(2) { x }, Array(2) { fa(edges.getJSONArray(it)) }, Array(2) { z }, fa(f.getJSONArray("estd")),
      fa(f.getJSONArray("lstd")), z, false, 0f, lcDir != 0, lcDir, false, FloatArray(8), 0f)
  }

  /** bird's-eye check around the fitted car (1 px = 12.5 cm, the car at the bottom middle, ahead = up): roads it
   *  draws (blue-grey, white lines), roads left out (faint), the camera's lines (green) and edges (orange) */
  private fun top(b: LaneMap.Built, f: JSONObject, fit: LaneFit.Fit, name: String) {
    val w = 1600
    val h = 1600
    val k = 8f
    val bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
    val c = Canvas(bmp)
    c.drawColor(0xFF101418.toInt())
    val p = Paint(Paint.ANTI_ALIAS_FLAG)
    p.style = Paint.Style.STROKE
    val fill = Paint()
    fun sx(r: Double) = (w / 2 + r * k).toFloat()
    fun sy(fw: Double) = (h - 200 - fw * k).toFloat()
    val pose = fit.pose
    val reach = fit.reach
    for (wi in b.ways.indices) {
      val wy = b.ways[wi]
      if (!wy.near(pose.x, pose.y, 120.0)) continue
      val inReach = reach == null || reach[wi]
      val sp = Path()
      for (i in wy.left.indices) {
        val r = pose.right(wy.left[i].toDouble(), wy.leftY[i].toDouble())
        val fw = pose.fwd(wy.left[i].toDouble(), wy.leftY[i].toDouble())
        if (i == 0) sp.moveTo(sx(r), sy(fw)) else sp.lineTo(sx(r), sy(fw))
      }
      for (i in wy.right.indices.reversed()) {
        val r = pose.right(wy.right[i].toDouble(), wy.rightY[i].toDouble())
        val fw = pose.fwd(wy.right[i].toDouble(), wy.rightY[i].toDouble())
        sp.lineTo(sx(r), sy(fw))
      }
      sp.close()
      fill.color = if (inReach) -0x6fd5c5b0 else 0x30808080
      c.drawPath(sp, fill)
      p.color = if (inReach) -0x171310 else 0x60808890
      for (ln in wy.lines) {
        val lp = Path()
        for (i in ln.x.indices) {
          val r = pose.right(ln.x[i].toDouble(), ln.y[i].toDouble())
          val fw = pose.fwd(ln.x[i].toDouble(), ln.y[i].toDouble())
          if (i == 0) lp.moveTo(sx(r), sy(fw)) else lp.lineTo(sx(r), sy(fw))
        }
        p.strokeWidth = if (LaneMap.isEdge(ln.style)) 3f else 1.5f
        c.drawPath(lp, p)
      }
    }
    val x = fa(f.getJSONArray("lx"))
    for ((arr, col) in listOf(f.getJSONArray("lanes") to -0xc2237c, f.getJSONArray("edges") to -0x6800)) {
      p.color = col
      p.strokeWidth = 4f
      for (i in 0 until arr.length()) {
        val y = fa(arr.getJSONArray(i))
        val lp = Path()
        for (j in x.indices) {
          if (x[j] > 75) break
          if (j == 0) lp.moveTo(sx(y[j].toDouble()), sy(x[j].toDouble())) else lp.lineTo(sx(y[j].toDouble()), sy(x[j].toDouble()))
        }
        c.drawPath(lp, p)
      }
    }
    FileOutputStream(File(out, name)).use { bmp.compress(Bitmap.CompressFormat.PNG, 100, it) }
  }

  @Test fun drive() {
    val ctx = RuntimeEnvironment.getApplication()
    LaneMap.offline = true
    LaneMap.parse(File(dir, "osm_all.json").readText())
    val log = StringBuilder()
    for (pick in picks) {
      val p = pick.split(',')
      val frames = JSONArray(File(dir, "drive_${p[0]}.json").readText())
      val t0 = LocalDateTime.parse(p[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toEpochSecond(ZoneOffset.UTC) + 5 * 3600.0
      var fit: LaneFit.Fit? = null
      var built: LaneMap.Built? = null
      var bLat = 0.0
      var bLon = 0.0
      var travelled = 0.0
      var last: JSONObject? = null
      var prevXY: DoubleArray? = null
      val costs = ArrayList<String>()
      for (i in 0 until frames.length()) {
        val f = frames.getJSONObject(i)
        val t = f.getDouble("t")
        if (t < t0 - 90 || t > t0 + 0.13) continue
        val lat = f.getDouble("lat")
        val lon = f.getDouble("lon")
        if (built == null || hypot((lon - bLon) * 84000, (lat - bLat) * 110540) > 400) {
          LaneMap.buildNow(lat, lon); built = LaneMap.built; bLat = lat; bLon = lon
        }
        val b = built!!
        val xy = b.xy(lat, lon)
        prevXY?.let { travelled += hypot(xy[0] - it[0], xy[1] - it[1]) }
        prevXY = xy
        val brg = Math.toRadians(f.getDouble("bearing"))
        val odo = doubleArrayOf(xy[0], xy[1], PI / 2 - brg, travelled)
        val cam = LaneFit.Cam.of(snapshot(f))!!
        fit = LaneFit.run(b, LaneFit.Pose(xy[0], xy[1], brg), f.getDouble("acc"), cam, odo, (t * 1000).toLong(), fit)
        costs.add(String.format("%.2f%s%+.1f", fit.cost, if (fit.hidden) "H" else "", fit.shift))
        last = f
      }
      val f = last ?: continue
      val fitN = fit!!
      val things = ArrayList<LaneSceneView.Thing>()
      if (!f.isNull("lead")) {
        val ld = f.getJSONArray("lead")
        val L = SceneSprites.models["car_sedan"]?.L ?: 4.2f
        things.add(LaneSceneView.Thing(1, "car_sedan", ld.getDouble(1), ld.getDouble(0) + L / 2, 0.0, true, false))
      }
      if ("cars" in p) {     // a few made-up cars, to see how they're drawn (marked in the picks file)
        things.add(LaneSceneView.Thing(2, "car_suv", -3.6, 9.0, 0.0, false, false))
        things.add(LaneSceneView.Thing(3, "truck", 3.7, 26.0, 0.0, false, false))
        things.add(LaneSceneView.Thing(4, "car_hatch", 3.6, -4.0, 0.0, false, true))
      }
      if ("nan" in p) {      // what the camera's car detector hands over when it doesn't know (2.87.1)
        things.add(LaneSceneView.Thing(5, "car_suv", 3.6, 16.0, Double.NaN, false, false))
        things.add(LaneSceneView.Thing(6, "car_coupe", Double.NaN, Double.NaN, 0.0, false, false))
      }
      val lcDir = if ("lcR" in p) 1 else if ("lcL" in p) -1 else 0
      val v = LaneSceneView(ctx)
      v.night = "day" !in p
      p.firstOrNull { it.startsWith("zoom=") }?.let { v.dist = it.substring(5).toFloat() }
      p.firstOrNull { it.startsWith("tilt=") }?.let { v.elev = it.substring(5).toFloat() }
      v.frame = LaneSceneView.Frame(snapshot(f, lcDir), fitN, if (fitN.hidden) null else fitN.pose, travelled, things, true,
        false, false, null)
      val w = 2944
      val h = 1840
      v.measure(View.MeasureSpec.makeMeasureSpec(w, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(h, View.MeasureSpec.EXACTLY))
      v.layout(0, 0, w, h)
      val bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
      v.draw(Canvas(bmp))
      FileOutputStream(File(out, "${p[2]}.png")).use { bmp.compress(Bitmap.CompressFormat.PNG, 100, it) }
      if ("top" in p) {
        top(built!!, f, fitN, "${p[2]}_top.png")
        val r = fitN.reach
        if (r != null) for (wi in r.indices) if (r[wi]) {
          val wy = built.ways[wi]
          var fmin = 1e9; var fmax = -1e9; var rmin = 1e9; var rmax = -1e9
          for (i in wy.left.indices) {
            val ff = fitN.pose.fwd(wy.left[i].toDouble(), wy.leftY[i].toDouble()); val rr = fitN.pose.right(wy.left[i].toDouble(), wy.leftY[i].toDouble())
            fmin = minOf(fmin, ff); fmax = maxOf(fmax, ff); rmin = minOf(rmin, rr); rmax = maxOf(rmax, rr)
          }
          log.append("   reach ${wy.id} ${wy.hw} '${wy.name}' flow ${wy.flow} f ${fmin.toInt()}..${fmax.toInt()} r ${rmin.toInt()}..${rmax.toInt()}\n")
        }
      }
      val m = fitN.match
      val line = "${p[2]}: cost ${"%.2f".format(fitN.cost)} hidden=${fitN.hidden} shift ${"%+.2f".format(fitN.shift)} m turn ${"%+.1f".format(Math.toDegrees(fitN.dh))} deg" +
        " lines ${fitN.camLines} edges ${fitN.camEdges}" + (if (m != null) " lane ${m.lane} of ${m.of} on ${m.name} (${m.hw}) turns ${m.turns}" else " no lane") +
        " | seq ${costs.takeLast(40).joinToString(" ")}"
      log.append(line).append('\n')
    }
    File(out, "log.txt").writeText(log.toString())
  }
}
