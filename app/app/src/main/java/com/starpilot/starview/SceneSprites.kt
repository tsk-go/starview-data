package com.starpilot.starview

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import org.json.JSONObject

/**
 * 2.87.0: the road users and the van for LaneSceneView, as pictures made from the Scene's own 3D models (assets/models)
 * - each model seen from three heights (8 / 16 / 28 deg) and every 15 deg around, the van from the chase camera at
 * 12-48 deg (assets/scene2, made by docs/tools/scene2/). A picture is read the first time it is needed and kept.
 */
object SceneSprites {
  private const val TAG = "StarView/Sprites"
  class Sheet(val file: String, val cw: Int, val ch: Int, val ax: Float, val ay: Float, val cols: Int)
  class Model(val name: String, val L: Float, val W: Float, val H: Float, val yaws: FloatArray, val pitches: FloatArray,
              val ppm: FloatArray, val sheets: List<Sheet>)
  class Ego(val elev: Float, val file: String, val x: Float, val y: Float, val w: Int, val h: Int)

  val models = HashMap<String, Model>()
  val ego = ArrayList<Ego>()
  var egoD = 17.24f; private set
  var egoCy = 0.8f; private set
  var egoH = 2000f; private set
  private val bitmaps = HashMap<String, Bitmap?>()
  private var ctx: Context? = null

  @Synchronized fun init(c: Context) {
    if (ctx != null) return
    ctx = c.applicationContext
    try {
      val j = JSONObject(c.assets.open("scene2/sprites.json").bufferedReader().readText())
      val ms = j.getJSONObject("models")
      for (k in ms.keys()) {
        val m = ms.getJSONObject(k)
        fun fa(n: String) = m.getJSONArray(n).let { a -> FloatArray(a.length()) { a.getDouble(it).toFloat() } }
        val sh = m.getJSONArray("sheets")
        models[k] = Model(k, m.getDouble("L").toFloat(), m.getDouble("W").toFloat(), m.getDouble("H").toFloat(), fa("yaws"),
          fa("pitches"), fa("ppm"), List(sh.length()) { i ->
            val s = sh.getJSONObject(i)
            Sheet(s.getString("file"), s.getInt("cw"), s.getInt("ch"), s.getDouble("ax").toFloat(), s.getDouble("ay").toFloat(), s.getInt("cols"))
          })
      }
      val e = j.getJSONObject("ego")
      egoD = e.getDouble("D").toFloat(); egoCy = e.getDouble("cy").toFloat(); egoH = e.getDouble("h").toFloat()
      val cells = e.getJSONArray("cells")
      for (i in 0 until cells.length()) {
        val c2 = cells.getJSONObject(i)
        ego.add(Ego(c2.getDouble("elev").toFloat(), c2.getString("file"), c2.getDouble("x").toFloat(), c2.getDouble("y").toFloat(),
          c2.getInt("w"), c2.getInt("h")))
      }
    } catch (e: Exception) {
      Log.w(TAG, "sprites: $e")
    }
  }

  @Synchronized fun bitmap(file: String): Bitmap? {
    if (bitmaps.containsKey(file)) return bitmaps[file]
    val b = try {
      ctx?.assets?.open("scene2/$file")?.use { BitmapFactory.decodeStream(it) }
    } catch (e: Exception) { Log.w(TAG, "$file: $e"); null }
    bitmaps[file] = b
    return b
  }

  /** the pictures used on almost every drive, read ahead off the screen thread */
  fun preload() {
    Thread({
      try {
        for (e in ego) if (e.elev == 30f) bitmap(e.file)
        for (n in listOf("car_sedan", "car_suv", "car_hatch", "car_coupe")) models[n]?.sheets?.forEach { bitmap(it.file) }
      } catch (_: Throwable) {}
    }, "sprites").apply { priority = Thread.MIN_PRIORITY }.start()
  }
}
