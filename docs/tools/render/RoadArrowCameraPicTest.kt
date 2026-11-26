package com.starpilot.starview

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import java.io.File
import java.io.FileOutputStream

/**
 * 2.90.14: the road arrow (and the arrival's house number) as the camera view draws them - RoadArrowPainter with the
 * camera's stretch (RoadArrowCameraView.BOOST), ArrivalTag - on still frames of the owner's own camera, with a camera
 * fitted to each frame by hand. The placement on a real drive is the Scene's (SceneSeqTest); this is the look.
 * laneData/cam_city.jpg, cam_hwy.jpg (frames, never in git); writes picOut/cam_*.png. CAM_PHASES=0.2,0.5 for a film's
 * frames.
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [35], qualifiers = "w1472dp-h920dp-land-xhdpi")
class RoadArrowCameraPicTest {
  private val out = System.getProperty("picOut") ?: "/tmp"
  private val dir = System.getProperty("laneData") ?: "/tmp"
  private val w = 2944; private val h = 1848

  /** a camera [f] px, its middle at [cx], the horizon at [hy], [camH] m over the road */
  private class Cam(val f: Double, val cx: Double, val hy: Double, val camH: Double) {
    val proj = RoadArrowPainter.Proj { r, fw, ht, o ->
      if (fw < 0.5) false else { o[0] = (cx + f * r / fw).toFloat(); o[1] = (hy + f * (camH - ht) / fw).toFloat(); true }
    }
  }

  private fun picture(bg: String, cam: Cam, name: String, phase: Double, draw: (Canvas, Cam) -> Unit) {
    val src = BitmapFactory.decodeFile(File(dir, bg).path) ?: return
    val b = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
    val c = Canvas(b)
    c.drawBitmap(src, Rect(0, 0, src.width, src.height), Rect(0, 0, w, h), Paint(Paint.FILTER_BITMAP_FLAG))
    draw(c, cam)
    FileOutputStream(File(out, name)).use { b.compress(Bitmap.CompressFormat.PNG, 100, it) }
  }

  /** a two-way street, our lane 1.8 m right of its middle line; the route turns right at [dist] onto a cross street */
  private fun cityTurn(dist: Double): RoadArrow.Shape? {
    val rr = ArrayList<Double>(); val ff = ArrayList<Double>(); val ss = ArrayList<Double>()
    var s = 0.0
    while (s <= dist + 45) { if (s <= dist) { rr.add(-1.8); ff.add(s) } else { rr.add(-1.8 + s - dist); ff.add(dist) }; ss.add(s); s += 1.0 }
    return RoadArrow.shape(RoadArrow.Kind.TURN, 1, dist, 1.8, 1.8, { a, d -> doubleArrayOf(d - 1.8, a) },
      { doubleArrayOf(-5.4, 5.4, 999.0, -5.4, 5.4) }, rr.toDoubleArray(), ff.toDoubleArray(), ss.toDoubleArray())
  }

  /** a highway: the exit's lane is the one right of ours ([right] m to its middle), the exit [dist] m on */
  private fun exitLane(dist: Double, right: Double): RoadArrow.Shape? {
    val rr = doubleArrayOf(0.0, 0.0); val ff = doubleArrayOf(0.0, 400.0); val ss = doubleArrayOf(0.0, 400.0)
    return RoadArrow.shape(RoadArrow.Kind.LANE, 1, dist, 0.0, 0.0, { a, d -> doubleArrayOf(d, a) },
      { doubleArrayOf(-1.8, right + 1.8, 999.0, -1.8, right + 1.8) }, rr, ff, ss)
  }

  @Test fun camera() {
    val dp = RuntimeEnvironment.getApplication().resources.displayMetrics.density
    val painter = RoadArrowPainter(dp)
    val tag = ArrivalTag(dp)
    RoadArrowStyle.color = 0; RoadArrowStyle.light = 0f; RoadArrowStyle.opacity = 1f; RoadArrowStyle.running = true
    val phases = (System.getenv("CAM_PHASES") ?: "0.55").split(",").map { it.trim().toDouble() }
    // fitted by hand to the owner's frames (the mock-ups' cameras, at this picture's size)
    val city = Cam(4044.0, 1455.0, 946.0, 1.3)
    val hwy = Cam(2386.0, 1472.0, 987.0, 2.76)
    for ((i, ph) in phases.withIndex()) {
      val sfx = if (phases.size > 1) String.format("_%03d", i) else ""
      picture("cam_city.jpg", city, "cam_city$sfx.png", ph) { c, cam ->
        cityTurn(23.0)?.let { RoadArrowCameraView.inPicture(it, cam.proj, w.toFloat(), h.toFloat(), Double.MAX_VALUE, 0.0) }
          ?.let { painter.paint(c, it.first, cam.proj, ph, it.second, RoadArrowCameraView.BOOST, h.toFloat()) }
      }
      picture("cam_city.jpg", city, "cam_city_flat$sfx.png", ph) { c, cam ->
        cityTurn(23.0)?.let { RoadArrowCameraView.inPicture(it, cam.proj, w.toFloat(), h.toFloat(), Double.MAX_VALUE, 0.0) }
          ?.let { painter.paint(c, it.first, cam.proj, ph, it.second, 1.0, h.toFloat()) }
      }
      picture("cam_hwy.jpg", hwy, "cam_hwy$sfx.png", ph) { c, cam ->
        exitLane(420.0, 2.9)?.let { RoadArrowCameraView.inPicture(it, cam.proj, w.toFloat(), h.toFloat(), Double.MAX_VALUE, 0.0) }
          ?.let { painter.paint(c, it.first, cam.proj, ph, it.second, RoadArrowCameraView.BOOST, h.toFloat()) }
      }
    }
    // the house number at the right curb, 120 ft ahead
    picture("cam_city.jpg", city, "cam_city_tag.png", 0.5) { c, cam ->
      val o = FloatArray(2)
      val r = 7.2; val f = 36.0
      cam.proj.at(r, f, 0.0, o); val gx = o[0]; val gy = o[1]
      cam.proj.at(r, f, ArrivalTag.STICK, o)
      tag.paint(c, "1525", gx, gy, o[0], o[1], cam.f / f, cam.f / f, 1.0)
    }
  }
}
