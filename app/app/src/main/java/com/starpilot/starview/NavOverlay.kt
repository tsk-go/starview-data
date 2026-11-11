package com.starpilot.starview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.PorterDuffXfermode
import android.graphics.LinearGradient
import android.graphics.PathMeasure
import android.graphics.Shader
import android.graphics.RectF
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import org.json.JSONObject
import java.util.Date
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.hypot
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.math.sin

/**
 * 2.85.0: navigation on the road view the way StarPort shows it on the car screen - a dark turn card (StarPilot's
 * onroad_navigation.py: arrow, distance, street, "Then" chip, miles and minutes left, ETA, progress, lanes near the turn)
 * and a see-through map under it (street lines and the route, no background). Both are HUD widgets: moved, resized and
 * switched off in the screen editor like the other cards; a tap on either opens the full navigation screen.
 */
object NavOverlay {
  /** The screen editor is open: draw a sample when there is no route, so the widgets can be placed. */
  @JvmField @Volatile var editing = false

  const val THEN_MAX_GAP_M = 400.0
  const val LANES_SHOW_M = 800.0
  const val FAR_TURN_M = 1500.0

  // 2.85.5: classic and clean (neutral dark card, white text, the usual navigation blue) instead of StarPort's purple
  val BG = Color.argb(240, 18, 19, 22)
  val BORDER = Color.argb(60, 255, 255, 255)
  val SUB = Color.rgb(228, 230, 235)
  val TRIP = Color.rgb(200, 203, 210)
  val ETA = Color.WHITE
  val TRACK = Color.argb(40, 255, 255, 255)
  val FILL = Color.rgb(66, 133, 244)
  val CHIP = Color.argb(34, 255, 255, 255)
  val CHIP_TEXT = Color.rgb(228, 230, 235)
  val LANE_ON = Color.argb(70, 255, 255, 255)
  val LANE_OFF = Color.argb(18, 255, 255, 255)
  val LANE_DIM = Color.rgb(120, 124, 132)
  val ROUTE = Color.rgb(66, 133, 244)
  val ROUTE_CASE = Color.argb(200, 13, 60, 140)
  val CAR = Color.rgb(66, 133, 244)

  /** A lane arrow from the route ("slight right") as NavIcons' modifier ("slightRight"). */
  fun laneModifier(a: String): String = when (a) {
    "slight right" -> "slightRight"; "slight left" -> "slightLeft"
    "sharp right" -> "sharpRight"; "sharp left" -> "sharpLeft"
    else -> a
  }

  /** Distance as the turn card shows it: feet / miles (or m / km), rounded the way StarPilot does. */
  fun dist(m: Double, metric: Boolean): String {
    if (metric) {
      if (m < 1000) { val step = if (m < 500) 25 else 50; return "${(m / step).roundToInt() * step} m" }
      val km = m / 1000; return if (km >= 10) "${floor(km).toInt()} km" else "%.1f km".format(km)
    }
    val ft = m * 3.28084
    if (ft < 1000) { val step = if (ft <= 100) 10 else 50; return "${(ft / step).roundToInt() * step} ft" }
    val mi = ft / 5280; return if (mi >= 10) "${floor(mi).toInt()} mi" else "%.1f mi".format(mi)
  }

  fun minutes(s: Double): String {
    val m = max(1, (s / 60).roundToInt())
    return if (m < 60) "$m min" else "${m / 60} h ${m % 60} min"
  }

  /** The turn card's content when there is no route and the screen editor is open. */
  val SAMPLE: JSONObject = JSONObject()
    .put("maneuverType", "turn").put("maneuverModifier", "right").put("maneuverPrimaryText", "Turn right onto Main St")
    .put("maneuverDistance", 480.0).put("nextManeuverType", "turn").put("nextManeuverModifier", "left")
    .put("nextManeuverDistance", 760.0).put("distanceRemaining", 19950.0).put("timeRemaining", 1080.0)
}

/** Where the car is and which way it points, from the same sources the map uses (RTK, the comma's GPS, the phone). */
object NavPos {
  fun lat(t: Telemetry): Double {
    RtkPosition.INSTANCE.now(System.currentTimeMillis(), t.vEgo.toDouble())?.let { return it[0] }
    val g = GpsBridge.INSTANCE
    if (!g.outLat.isNaN() && System.currentTimeMillis() - g.lastLiveMs < 30000) return g.outLat
    return t.gpsLat
  }

  fun lon(t: Telemetry): Double {
    RtkPosition.INSTANCE.now(System.currentTimeMillis(), t.vEgo.toDouble())?.let { return it[1] }
    val g = GpsBridge.INSTANCE
    if (!g.outLon.isNaN() && System.currentTimeMillis() - g.lastLiveMs < 30000) return g.outLon
    return t.gpsLon
  }

  fun bearing(t: Telemetry): Float {
    if (RtkPosition.INSTANCE.now(System.currentTimeMillis(), t.vEgo.toDouble()) != null) {
      val hd = RtkPosition.INSTANCE.heading
      if (!hd.isNaN() && !hd.isInfinite()) return hd.toFloat()
    }
    val g = GpsBridge.INSTANCE
    return if (!g.outBearing.isNaN() && System.currentTimeMillis() - g.lastLiveMs < 30000) g.outBearing else t.bearingDeg
  }
}

/** The turn card (HUD widget "navCard"). */
class NavCardView(ctx: Context, attrs: AttributeSet? = null) : View(ctx, attrs) {
  lateinit var t: Telemetry
  var collapsed = false
  private val p = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
  private val dim = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG).apply { colorFilter = PorterDuffColorFilter(NavOverlay.LANE_DIM, PorterDuff.Mode.SRC_IN) }
  /** Icons at full strength, whatever the last shape's color was (they used to take its transparency). */
  private val ip = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
  private val tp = Paint(Paint.ANTI_ALIAS_FLAG)
  private val rect = RectF()
  private var totalKey = ""
  private var total = 0.0
  private val timeFormat by lazy { android.text.format.DateFormat.getTimeFormat(context) }

  private fun icon(type: String, modifier: String) = NavIcons.INSTANCE.icon(context, type, modifier)

  override fun onDraw(c: Canvas) {
    if (!::t.isInitialized) return
    val nowMs = System.currentTimeMillis()
    val nav = t.navCard(nowMs)?.first ?: if (NavOverlay.editing) NavOverlay.SAMPLE else return
    val metric = t.isMetric
    val w = width.toFloat(); val h = height.toFloat()
    if (w <= 0f || h <= 0f) return
    val u = h / 150f                                    // 1 dp at the default 150 dp height (the editor scales the view)

    // background: StarPort's dark card with the purple edge
    p.style = Paint.Style.FILL; p.color = NavOverlay.BG
    rect.set(u, u, w - u, h - u)
    c.drawRoundRect(rect, 18 * u, 18 * u, p)
    p.style = Paint.Style.STROKE; p.strokeWidth = 1.5f * u; p.color = NavOverlay.BORDER
    c.drawRoundRect(rect, 18 * u, 18 * u, p)
    p.style = Paint.Style.FILL

    val type = nav.optString("maneuverType"); val mod = nav.optString("maneuverModifier")
    val distM = nav.optDouble("maneuverDistance", 0.0)
    val remaining = nav.optDouble("distanceRemaining", Double.NaN)
    val remainingS = nav.optDouble("timeRemaining", Double.NaN)
    val arrived = type == "arrive" && (distM < 40 || (!remaining.isNaN() && remaining < 40))
    val pad = 16 * u

    // lanes: from the tablet's own copy of the route, for this turn, once it is close
    val lanes = if (arrived || distM > NavOverlay.LANES_SHOW_M) null else Nav.INSTANCE.nextLanes()?.let { (step, d) ->
      if (abs(d - distM) < 150 && step.lanes.size >= 2) step.lanes else null
    }

    val header = h * (if (lanes != null) 0.50f else 0.62f)
    val iconS = min(64 * u, header - 12 * u)
    if (lanes == null) {
      icon(if (arrived) "arrive" else type, if (arrived) "" else mod)?.let {
        rect.set(pad, (header - iconS) / 2 + 2 * u, pad + iconS, (header + iconS) / 2 + 2 * u)
        c.drawBitmap(it, null, rect, ip)
      }
    }
    val x = if (lanes != null) pad + 2 * u else pad + iconS + 14 * u
    val textW = w - pad - x
    val distSize = 30 * u; val textSize = (if (lanes != null) 19 else 18) * u
    tp.textAlign = Paint.Align.LEFT
    tp.typeface = Typeface.DEFAULT_BOLD; tp.textSize = distSize; tp.color = Color.WHITE
    var y = 12 * u + distSize * 0.92f
    c.drawText(if (arrived) "Arrived" else NavOverlay.dist(distM, metric), x, y, tp)
    tp.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL); tp.textSize = textSize; tp.color = NavOverlay.SUB
    val far = distM > NavOverlay.FAR_TURN_M
    val text = if (arrived) nav.optString("destination") else nav.optString("maneuverPrimaryText")
    y += 6 * u
    for (line in wrap(text, textW, if (far || lanes != null || arrived) 1 else 2)) { y += textSize; c.drawText(line, x, y, tp); y += 2 * u }
    if (arrived) return

    // bottom row: progress bar and ETA
    val right = w - pad
    val barCenter = h - 17 * u
    var barRight = right
    if (!remainingS.isNaN()) {
      val eta = "ETA " + timeFormat.format(Date(nowMs + (remainingS * 1000).toLong()))
      tp.typeface = Typeface.DEFAULT_BOLD; tp.textSize = 19 * u; tp.color = NavOverlay.ETA
      val ew = tp.measureText(eta)
      c.drawText(eta, right - ew, barCenter + 7 * u, tp)
      barRight -= ew + 14 * u
    }
    if (lanes != null) {
      laneStrip(c, lanes, pad, header + 4 * u, right, barCenter - 14 * u)
    } else {
      thenChip(c, nav, distM, pad, header + 2 * u, u)
      if (!remaining.isNaN() && !remainingS.isNaN()) {
        val trip = NavOverlay.dist(remaining, metric) + " | " + NavOverlay.minutes(remainingS)
        tp.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL); tp.textSize = 17 * u; tp.color = NavOverlay.TRIP
        c.drawText(trip, right - tp.measureText(trip), header + 20 * u, tp)
      }
    }
    // progress: how much of the drive is done (the most left seen for this destination is the whole)
    val key = nav.optString("destination")
    if (key != totalKey) { totalKey = key; total = 0.0 }
    if (!remaining.isNaN()) total = max(total, remaining)
    if (total > 0 && barRight > pad + 20 * u) {
      val thick = 6 * u
      rect.set(pad, barCenter - thick / 2, barRight, barCenter + thick / 2)
      p.color = NavOverlay.TRACK; c.drawRoundRect(rect, thick, thick, p)
      val done = (1 - remaining / total).coerceIn(0.0, 1.0).toFloat()
      if (done > 0f) {
        rect.right = rect.left + max(thick, (barRight - pad) * done)
        p.color = NavOverlay.FILL; c.drawRoundRect(rect, thick, thick, p)
      }
    }
  }

  /** "Then <arrow>" when the turn after this one comes right after it. */
  private fun thenChip(c: Canvas, nav: JSONObject, distM: Double, x: Float, y: Float, u: Float) {
    val type = nav.optString("nextManeuverType"); val mod = nav.optString("nextManeuverModifier")
    if (type.isEmpty() && mod.isEmpty()) return
    val gap = nav.optDouble("nextManeuverDistance", Double.NaN) - distM
    if (gap.isNaN() || gap <= 1 || gap > NavOverlay.THEN_MAX_GAP_M) return
    tp.typeface = Typeface.DEFAULT_BOLD; tp.textSize = 15 * u
    val lw = tp.measureText("Then"); val s = 20 * u
    rect.set(x, y, x + lw + s + 26 * u, y + 28 * u)
    p.color = NavOverlay.CHIP; c.drawRoundRect(rect, 14 * u, 14 * u, p)
    tp.color = NavOverlay.CHIP_TEXT; c.drawText("Then", x + 12 * u, y + 19.5f * u, tp)
    icon(type, mod)?.let { rect.set(x + lw + 18 * u, y + 4 * u, x + lw + 18 * u + s, y + 4 * u + s); c.drawBitmap(it, null, rect, ip) }
  }

  /** One cell per lane with its arrow: the lanes to be in lit, the others dim. */
  private fun laneStrip(c: Canvas, lanes: List<Nav.Lane>, left: Float, top: Float, right: Float, bottom: Float) {
    val height = bottom - top
    if (height <= 4f) return
    val gap = max(4f, height * 0.12f)
    val cell = min(height * 1.2f, (right - left + gap) / lanes.size - gap)
    var x = left + (right - left - (cell + gap) * lanes.size + gap) / 2
    val s = height * 0.8f
    for (lane in lanes) {
      rect.set(x, top, x + cell, bottom)
      p.color = if (lane.ok) NavOverlay.LANE_ON else NavOverlay.LANE_OFF
      c.drawRoundRect(rect, height * 0.3f, height * 0.3f, p)
      val dir = if (lane.ok && lane.use.isNotEmpty()) lane.use else lane.arrows.firstOrNull() ?: "straight"
      icon("turn", NavOverlay.laneModifier(dir))?.let {
        rect.set(x + (cell - s) / 2, top + (height - s) / 2, x + (cell + s) / 2, top + (height + s) / 2)
        c.drawBitmap(it, null, rect, if (lane.ok) ip else dim)
      }
      x += cell + gap
    }
  }

  private fun wrap(text: String, maxW: Float, maxLines: Int): List<String> {
    if (text.isEmpty()) return emptyList()
    val words = text.split(" "); val out = ArrayList<String>(); var cur = ""
    for (wd in words) {
      val cand = if (cur.isEmpty()) wd else "$cur $wd"
      if (tp.measureText(cand) <= maxW || cur.isEmpty()) cur = cand else { out.add(cur); cur = wd; if (out.size == maxLines) break }
    }
    if (out.size < maxLines && cur.isNotEmpty()) out.add(cur)
    if (out.size == maxLines && text != out.joinToString(" ")) {
      var last = out[maxLines - 1].trimEnd() + "…"
      while (last.length > 2 && tp.measureText(last) > maxW) last = last.dropLast(2) + "…"
      out[maxLines - 1] = last
    }
    return out
  }
}

/**
 * The see-through map (HUD widget "navMap"), heading up: street lines from the tablet's offline map and the route on a
 * faint shade whose edges fade out (no frame), street names written along the streets - like StarPort's map and
 * Tesla's corner map.
 */
class NavMapView(ctx: Context, attrs: AttributeSet? = null) : View(ctx, attrs) {
  lateinit var t: Telemetry
  private val d = resources.displayMetrics.density
  private val road = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.STROKE; strokeCap = Paint.Cap.ROUND; strokeJoin = Paint.Join.ROUND }
  private val route = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.STROKE; strokeCap = Paint.Cap.ROUND; strokeJoin = Paint.Join.ROUND }
  private val fill = Paint(Paint.ANTI_ALIAS_FLAG)
  private val label = Paint(Paint.ANTI_ALIAS_FLAG).apply { textSize = 11.5f * resources.displayMetrics.density; typeface = Typeface.DEFAULT_BOLD }
  private val fade = Paint(Paint.ANTI_ALIAS_FLAG).apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN) }
  private val path = Path()
  private val measure = PathMeasure()
  private val labelBoxes = ArrayList<RectF>()
  private var scale = Float.NaN          // px per metre, eased toward the speed's zoom
  private val redraw = Runnable { invalidate() }   // one pending redraw at most
  private var fadeX: LinearGradient? = null
  private var fadeY: LinearGradient? = null
  private var fadeFor = 0L
  /** On the Scene view (a light picture): a lighter shade so the map blends in. The camera view keeps the darker one. */
  @JvmField var light = false
  /** 2.85.8: the owner's zoom from the screen editor (1 = ~180 m ahead in town); set by HudLayout.apply. */
  @JvmField var zoom = 1f

  /** A street on screen: x0, y0, x1, y1, ... */
  private class Street(val pts: FloatArray, val name: String, val major: Boolean)
  private val streets = ArrayList<Street>()

  private companion object {
    const val EARTH = 40075016.686
    val SKIP = setOf("sidewalk", "footway", "cycleway", "path", "steps", "pedestrian", "track", "bridleway", "corridor")
    val MAJOR = setOf("motorway", "trunk", "primary", "secondary", "motorway_link", "trunk_link", "primary_link")
    val SHADE = Color.argb(120, 10, 14, 22)
    val SHADE_LIGHT = Color.argb(60, 10, 14, 22)
  }

  override fun onDraw(c: Canvas) {
    if (!::t.isInitialized) return
    // redraw 5x a second while it is on screen (also while there is no position yet)
    removeCallbacks(redraw)
    if (visibility == VISIBLE && !NavOverlay.editing) postDelayed(redraw, 200)
    val w = width.toFloat(); val h = height.toFloat()
    if (w <= 0f || h <= 0f) return
    // 2.85.8: the map's scale no longer follows the box: width and height are set on their own and only show more or less
    val u = d
    val cx = w / 2; val cy = h * 0.74f
    streets.clear()
    var routePts: FloatArray? = null
    val lat = NavPos.lat(t); val lon = NavPos.lon(t)
    if (lat.isNaN() || lon.isNaN() || (lat == 0.0 && lon == 0.0)) {
      if (!NavOverlay.editing) return
      routePts = sample(cx, cy, 177f * u * zoom / 180f)
    } else {
      val b = Math.toRadians(NavPos.bearing(t).toDouble())
      val cb = cos(b); val sb = sin(b)
      // the view shows ~200 m ahead in town, up to ~700 m at highway speed
      val ahead = (180.0 + t.vEgo * 10.0).coerceIn(180.0, 700.0)
      val target = (177f * u * zoom / ahead).toFloat()   // px per metre: 1 dp per metre in town at zoom 1, the owner's zoom on top
      scale = if (scale.isNaN()) target else scale + (target - scale) * 0.15f
      val k = scale
      fun sx(de: Double, dn: Double) = cx + ((de * cb - dn * sb) * k).toFloat()
      fun sy(de: Double, dn: Double) = cy - ((de * sb + dn * cb) * k).toFloat()
      val reach = hypot(w.toDouble(), h.toDouble()) / k

      // streets from the offline map
      val osm = OsmRoads.INSTANCE
      val local = osm.toLocal(lat, lon)
      val e0 = local.first; val n0 = local.second
      if (!e0.isNaN() && !n0.isNaN()) {
        for (way in osm.near(e0, n0, reach)) {
          if (way.kind in SKIP || way.e.size < 2) continue
          val pts = FloatArray(way.e.size * 2)
          for (i in way.e.indices) { pts[2 * i] = sx(way.e[i] - e0, way.n[i] - n0); pts[2 * i + 1] = sy(way.e[i] - e0, way.n[i] - n0) }
          streets.add(Street(pts, way.name, way.highway || way.kind in MAJOR))
        }
      }

      // the route still ahead, from the tablet's copy of it
      val line = if (Nav.INSTANCE.active) Nav.INSTANCE.line else null
      if (line != null && line.size >= 2) {
        val m = EARTH * cos(Math.toRadians(lat))
        val mx0 = Nav.INSTANCE.mercX(lon); val my0 = Nav.INSTANCE.mercY(lat)
        val along = Nav.INSTANCE.along
        val i0 = if (along.isNaN() || along.isInfinite()) 0 else line.indexAt(along).coerceIn(0, line.size - 1)
        val stop = (if (along.isNaN()) 0.0 else along) + reach * 1.5
        val out = ArrayList<Float>()
        out.add(cx); out.add(cy)
        var i = max(i0, 1)
        while (i < line.size) {
          val de = (line.mx[i] - mx0) * m; val dn = -(line.my[i] - my0) * m
          out.add(sx(de, dn)); out.add(sy(de, dn))
          if (line.cum[i] > stop) break
          i++
        }
        routePts = out.toFloatArray()
      }
    }
    paint(c, w, h, u, cx, cy, routePts)
  }

  private fun pathOf(pts: FloatArray) {
    path.rewind()
    path.moveTo(pts[0], pts[1])
    var i = 2
    while (i + 1 < pts.size) { path.lineTo(pts[i], pts[i + 1]); i += 2 }
  }

  private fun paint(c: Canvas, w: Float, h: Float, u: Float, cx: Float, cy: Float, routePts: FloatArray?) {
    val layer = c.saveLayer(0f, 0f, w, h, null)
    fill.style = Paint.Style.FILL; fill.color = if (light) SHADE_LIGHT else SHADE
    c.drawRect(0f, 0f, w, h, fill)
    for (pass in 0..1) {                                         // dark edge first, then the line, so crossings stay clean
      for (s in streets) {
        pathOf(s.pts)
        if (pass == 0) { road.color = Color.argb(80, 0, 0, 0); road.strokeWidth = (if (s.major) 6f else 4.5f) * u }
        else { road.color = Color.argb(if (s.major) 185 else 140, 255, 255, 255); road.strokeWidth = (if (s.major) 2.6f else 1.6f) * u }
        c.drawPath(path, road)
      }
    }
    labels(c, w, h, u, cx, cy, routePts)
    if (routePts != null && routePts.size >= 4) {
      pathOf(routePts)
      route.color = NavOverlay.ROUTE_CASE; route.strokeWidth = 9 * u; c.drawPath(path, route)
      route.color = NavOverlay.ROUTE; route.strokeWidth = 5 * u; c.drawPath(path, route)
    }
    car(c, cx, cy, u)
    // fade every edge out: the shade, the streets and the names melt into the road view
    val key = (w.toLong() shl 32) or h.toLong()
    if (fadeX == null || fadeFor != key) {
      fadeFor = key
      val clear = Color.argb(0, 0, 0, 0); val full = Color.argb(255, 0, 0, 0)
      fadeX = LinearGradient(0f, 0f, w, 0f, intArrayOf(clear, full, full, clear), floatArrayOf(0f, 0.17f, 0.83f, 1f), Shader.TileMode.CLAMP)
      fadeY = LinearGradient(0f, 0f, 0f, h, intArrayOf(clear, full, full, clear), floatArrayOf(0f, 0.2f, 0.86f, 1f), Shader.TileMode.CLAMP)
    }
    fade.shader = fadeX; c.drawRect(0f, 0f, w, h, fade)
    fade.shader = fadeY; c.drawRect(0f, 0f, w, h, fade)
    c.restoreToCount(layer)
  }

  /**
   * Street names written along their street (kept upright), where the street runs longest inside the clear middle of
   * the view; one per name, never over another name or the car.
   */
  private fun labels(c: Canvas, w: Float, h: Float, u: Float, cx: Float, cy: Float, routePts: FloatArray?) {
    labelBoxes.clear()
    val done = HashSet<String>()
    val l = w * 0.14f; val r = w * 0.86f; val top = h * 0.16f; val bot = h * 0.9f
    val run = ArrayList<Float>(); val best = ArrayList<Float>()
    val pos = FloatArray(2)
    for (s in streets.sortedByDescending { it.major }) {
      if (labelBoxes.size >= 7 || s.name.isEmpty() || s.name in done) continue
      // the longest stretch of the street inside the clear middle (each segment clipped to it)
      run.clear(); best.clear(); var runLen = 0f; var bestLen = 0f
      val p = s.pts
      var i = 0
      while (i + 3 < p.size) {
        val seg = clip(p[i], p[i + 1], p[i + 2], p[i + 3], l, top, r, bot)
        if (seg == null) {
          run.clear(); runLen = 0f
        } else {
          val joined = run.size >= 2 && abs(run[run.size - 2] - seg[0]) < 0.5f && abs(run[run.size - 1] - seg[1]) < 0.5f
          if (!joined) { run.clear(); runLen = 0f; run.add(seg[0]); run.add(seg[1]) }
          run.add(seg[2]); run.add(seg[3]); runLen += hypot(seg[2] - seg[0], seg[3] - seg[1])
          if (runLen > bestLen) { bestLen = runLen; best.clear(); best.addAll(run) }
        }
        i += 2
      }
      val tw = label.measureText(s.name)
      if (best.size < 4 || bestLen < tw + 14 * u) continue
      path.rewind()
      val forward = best[best.size - 2] >= best[0]                // write left to right so the text is upright
      if (forward) { path.moveTo(best[0], best[1]); var j = 2; while (j + 1 < best.size) { path.lineTo(best[j], best[j + 1]); j += 2 } }
      else { path.moveTo(best[best.size - 2], best[best.size - 1]); var j = best.size - 4; while (j >= 0) { path.lineTo(best[j], best[j + 1]); j -= 2 } }
      measure.setPath(path, false)
      val len = measure.length
      // the middle of the stretch first, then toward either end: the first spot that is clear
      var off = -1f; var box: RectF? = null
      for (f in floatArrayOf(0.5f, 0.3f, 0.7f, 0.15f, 0.85f)) {
        val o = (len * f - tw / 2).coerceIn(0f, len - tw)
        var x0 = Float.MAX_VALUE; var y0 = Float.MAX_VALUE; var x1 = -Float.MAX_VALUE; var y1 = -Float.MAX_VALUE
        for (q in 0..4) {
          measure.getPosTan(o + tw * q / 4f, pos, null)
          x0 = min(x0, pos[0]); y0 = min(y0, pos[1]); x1 = max(x1, pos[0]); y1 = max(y1, pos[1])
        }
        val b = RectF(x0 - 9 * u, y0 - 9 * u, x1 + 9 * u, y1 + 9 * u)
        if (labelBoxes.any { RectF.intersects(it, b) }) continue
        if (b.contains(cx, cy) || hypot(b.centerX() - cx, b.centerY() - cy) < 30 * u) continue
        if (routePts != null && crosses(routePts, b)) continue      // never over the route
        off = o; box = b; break
      }
      if (box == null) continue
      labelBoxes.add(box); done.add(s.name)
      val v = label.textSize * 0.36f                              // the text's middle on the street
      label.style = Paint.Style.STROKE; label.strokeWidth = 3.2f * u; label.color = Color.argb(175, 8, 10, 16)
      c.drawTextOnPath(s.name, path, off, v, label)
      label.style = Paint.Style.FILL; label.color = Color.argb(235, 255, 255, 255)
      c.drawTextOnPath(s.name, path, off, v, label)
    }
  }

  /** Whether the polyline passes through the box. */
  private fun crosses(pts: FloatArray, box: RectF): Boolean {
    var i = 0
    while (i + 3 < pts.size) {
      if (clip(pts[i], pts[i + 1], pts[i + 2], pts[i + 3], box.left, box.top, box.right, box.bottom) != null) return true
      i += 2
    }
    return false
  }

  /** The part of the segment inside the rectangle (Liang-Barsky), or null. */
  private fun clip(x0: Float, y0: Float, x1: Float, y1: Float, l: Float, t: Float, r: Float, b: Float): FloatArray? {
    var t0 = 0f; var t1 = 1f
    val dx = x1 - x0; val dy = y1 - y0
    val ps = floatArrayOf(-dx, dx, -dy, dy); val qs = floatArrayOf(x0 - l, r - x0, y0 - t, b - y0)
    for (k in 0..3) {
      val pk = ps[k]; val qk = qs[k]
      if (pk == 0f) { if (qk < 0f) return null }
      else {
        val tt = qk / pk
        if (pk < 0f) { if (tt > t1) return null; if (tt > t0) t0 = tt } else { if (tt < t0) return null; if (tt < t1) t1 = tt }
      }
    }
    if (t1 - t0 < 1e-4f) return null
    return floatArrayOf(x0 + t0 * dx, y0 + t0 * dy, x0 + t1 * dx, y0 + t1 * dy)
  }

  private fun car(c: Canvas, cx: Float, cy: Float, u: Float) {
    fill.style = Paint.Style.FILL; fill.color = NavOverlay.CAR
    c.drawCircle(cx, cy, 14 * u, fill)
    fill.style = Paint.Style.STROKE; fill.strokeWidth = 1.5f * u; fill.color = Color.argb(215, 255, 255, 255)
    c.drawCircle(cx, cy, 14 * u, fill)
    fill.style = Paint.Style.FILL; fill.color = Color.WHITE
    path.rewind()
    path.moveTo(cx, cy - 8 * u); path.lineTo(cx + 6 * u, cy + 6 * u); path.lineTo(cx, cy + 3 * u); path.lineTo(cx - 6 * u, cy + 6 * u); path.close()
    c.drawPath(path, fill)
  }

  /** What the widget looks like, for the screen editor when there is no position yet: a few named streets and a route,
   *  laid out in metres around the car so the editor shows the zoom and the box size as they will be. */
  private fun sample(cx: Float, cy: Float, k: Float): FloatArray {
    fun st(name: String, major: Boolean, vararg en: Float) {
      val a = FloatArray(en.size); for (i in en.indices) a[i] = if (i % 2 == 0) cx + en[i] * k else cy - en[i] * k
      streets.add(Street(a, name, major))
    }
    st("Ocean Parkway", true, 0f, -900f, 0f, 1400f)
    for ((x, n) in listOf(-260f to "East 3rd Street", -170f to "East 5th Street", -85f to "", 85f to "East 7th Street", 170f to "", 260f to "East 9th Street"))
      st(n, false, x, -900f, x + 25f, 1400f)
    for ((y, n) in listOf(-200f to "Avenue T", -60f to "Avenue S", 90f to "Avenue R", 240f to "Avenue Q", 390f to "Avenue P", 690f to "Avenue N"))
      st(n, false, -900f, y, 900f, y + 6f)
    st("Kings Highway", true, -900f, 520f, 900f, 560f)
    // the route: up Ocean Parkway, right onto Avenue R
    return floatArrayOf(cx, cy, cx, cy - 84f * k, cx + 6f * k, cy - 90f * k, cx + 900f * k, cy - 96f * k)
  }
}
