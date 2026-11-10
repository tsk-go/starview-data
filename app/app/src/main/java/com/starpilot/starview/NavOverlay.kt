package com.starpilot.starview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
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

  val BG = Color.argb(245, 15, 13, 23)
  val BORDER = Color.argb(190, 160, 126, 220)
  val SUB = Color.rgb(222, 214, 244)
  val TRIP = Color.rgb(214, 210, 226)
  val ETA = Color.rgb(235, 225, 255)
  val TRACK = Color.argb(60, 160, 126, 220)
  val FILL = Color.rgb(178, 150, 255)
  val CHIP = Color.rgb(44, 37, 70)
  val CHIP_TEXT = Color.rgb(199, 174, 247)
  val LANE_ON = Color.rgb(78, 62, 128)
  val LANE_OFF = Color.rgb(34, 30, 46)
  val LANE_DIM = Color.rgb(112, 104, 134)
  val ROUTE = Color.rgb(164, 139, 255)
  val ROUTE_CASE = Color.argb(165, 30, 18, 60)

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
    p.style = Paint.Style.STROKE; p.strokeWidth = 2 * u; p.color = NavOverlay.BORDER
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
        c.drawBitmap(it, null, rect, p)
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
    icon(type, mod)?.let { rect.set(x + lw + 18 * u, y + 4 * u, x + lw + 18 * u + s, y + 4 * u + s); c.drawBitmap(it, null, rect, p) }
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
        c.drawBitmap(it, null, rect, if (lane.ok) p else dim)
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

/** The see-through map (HUD widget "navMap"): street lines from the tablet's offline map and the route, heading up. */
class NavMapView(ctx: Context, attrs: AttributeSet? = null) : View(ctx, attrs) {
  lateinit var t: Telemetry
  private val d = resources.displayMetrics.density
  private val road = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.STROKE; strokeCap = Paint.Cap.ROUND; strokeJoin = Paint.Join.ROUND }
  private val route = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.STROKE; strokeCap = Paint.Cap.ROUND; strokeJoin = Paint.Join.ROUND }
  private val fill = Paint(Paint.ANTI_ALIAS_FLAG)
  private val label = Paint(Paint.ANTI_ALIAS_FLAG).apply { textSize = 11.5f * resources.displayMetrics.density; typeface = Typeface.DEFAULT_BOLD }
  private val path = Path()
  private val labelBoxes = ArrayList<RectF>()
  private var scale = Float.NaN          // px per metre, eased toward the speed's zoom

  private companion object {
    const val EARTH = 40075016.686
    val SKIP = setOf("sidewalk", "footway", "cycleway", "path", "steps", "pedestrian", "track", "bridleway", "corridor")
    val MAJOR = setOf("motorway", "trunk", "primary", "secondary", "motorway_link", "trunk_link", "primary_link")
  }

  override fun onDraw(c: Canvas) {
    if (!::t.isInitialized) return
    val w = width.toFloat(); val h = height.toFloat()
    if (w <= 0f || h <= 0f) return
    val u = h / 250f                                  // 1 dp at the default 250 dp height (the editor scales the view)
    val cx = w / 2; val cy = h * 0.82f
    val lat = NavPos.lat(t); val lon = NavPos.lon(t)
    if (lat.isNaN() || lon.isNaN() || (lat == 0.0 && lon == 0.0)) {
      if (NavOverlay.editing) sample(c, w, h, u)
      return
    }
    val b = Math.toRadians(NavPos.bearing(t).toDouble())
    val cb = cos(b); val sb = sin(b)
    // the view shows ~200 m ahead in town, up to ~700 m at highway speed
    val ahead = (180.0 + t.vEgo * 10.0).coerceIn(180.0, 700.0)
    val target = ((cy - 8 * u) / ahead).toFloat()
    scale = if (scale.isNaN()) target else scale + (target - scale) * 0.15f
    val k = scale
    fun sx(de: Double, dn: Double) = cx + ((de * cb - dn * sb) * k).toFloat()
    fun sy(de: Double, dn: Double) = cy - ((de * sb + dn * cb) * k).toFloat()
    val reach = hypot(w.toDouble(), h.toDouble()) / k

    // streets
    val osm = OsmRoads.INSTANCE
    val local = osm.toLocal(lat, lon)
    val e0 = local.first; val n0 = local.second
    labelBoxes.clear()
    if (!e0.isNaN() && !n0.isNaN()) {
      val ways = osm.near(e0, n0, reach).filter { it.kind !in SKIP }
      for (pass in 0..1) {                                       // dark edge first, then the line, so crossings stay clean
        for (way in ways) {
          val e = way.e; val n = way.n
          if (e.size < 2) continue
          path.rewind()
          for (i in e.indices) {
            val px = sx(e[i] - e0, n[i] - n0); val py = sy(e[i] - e0, n[i] - n0)
            if (i == 0) path.moveTo(px, py) else path.lineTo(px, py)
          }
          val major = way.highway || way.kind in MAJOR
          if (pass == 0) { road.color = Color.argb(95, 0, 0, 0); road.strokeWidth = (if (major) 6f else 4.5f) * u }
          else { road.color = Color.argb(if (major) 175 else 135, 255, 255, 255); road.strokeWidth = (if (major) 2.6f else 1.6f) * u }
          c.drawPath(path, road)
        }
      }
      // names: one per street, near the car but not on it, never overlapping
      for (way in ways.sortedByDescending { it.highway || it.kind in MAJOR }) {
        if (labelBoxes.size >= 6 || way.name.isEmpty()) continue
        var best = -1; var bestD = Double.MAX_VALUE
        for (i in way.e.indices) {
          val px = sx(way.e[i] - e0, way.n[i] - n0); val py = sy(way.e[i] - e0, way.n[i] - n0)
          if (px < 30 * u || px > w - 30 * u || py < 14 * u || py > h - 14 * u) continue
          val dd = hypot((px - cx).toDouble(), (py - cy).toDouble())
          if (dd < 40 * u) continue
          if (dd < bestD) { bestD = dd; best = i }
        }
        if (best < 0) continue
        val px = sx(way.e[best] - e0, way.n[best] - n0); val py = sy(way.e[best] - e0, way.n[best] - n0)
        val tw = label.measureText(way.name)
        val box = RectF(px - tw / 2 - 3 * u, py - 12 * u, px + tw / 2 + 3 * u, py + 3 * u)
        if (box.left < 0 || box.right > w || labelBoxes.any { RectF.intersects(it, box) }) continue
        labelBoxes.add(box)
        label.style = Paint.Style.STROKE; label.strokeWidth = 3 * u; label.color = Color.argb(160, 0, 0, 0)
        c.drawText(way.name, px - tw / 2, py, label)
        label.style = Paint.Style.FILL; label.color = Color.argb(215, 255, 255, 255)
        c.drawText(way.name, px - tw / 2, py, label)
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
      path.rewind()
      path.moveTo(cx, cy)
      var i = max(i0, 1)
      while (i < line.size) {
        val de = (line.mx[i] - mx0) * m; val dn = -(line.my[i] - my0) * m
        path.lineTo(sx(de, dn), sy(de, dn))
        if (line.cum[i] > stop) break
        i++
      }
      route.color = NavOverlay.ROUTE_CASE; route.strokeWidth = 9 * u; c.drawPath(path, route)
      route.color = NavOverlay.ROUTE; route.strokeWidth = 5 * u; c.drawPath(path, route)
    }

    car(c, cx, cy, u)
    if (visibility == VISIBLE && !NavOverlay.editing) postInvalidateDelayed(200)
  }

  private fun car(c: Canvas, cx: Float, cy: Float, u: Float) {
    fill.style = Paint.Style.FILL; fill.color = Color.argb(235, 74, 92, 214)
    c.drawCircle(cx, cy, 14 * u, fill)
    fill.style = Paint.Style.STROKE; fill.strokeWidth = 1.5f * u; fill.color = Color.argb(215, 255, 255, 255)
    c.drawCircle(cx, cy, 14 * u, fill)
    fill.style = Paint.Style.FILL; fill.color = Color.WHITE
    path.rewind()
    path.moveTo(cx, cy - 8 * u); path.lineTo(cx + 6 * u, cy + 6 * u); path.lineTo(cx, cy + 3 * u); path.lineTo(cx - 6 * u, cy + 6 * u); path.close()
    c.drawPath(path, fill)
  }

  /** What the widget looks like, for the screen editor when there is no position yet. */
  private fun sample(c: Canvas, w: Float, h: Float, u: Float) {
    val cx = w / 2; val cy = h * 0.82f
    for (pass in 0..1) {
      road.color = if (pass == 0) Color.argb(95, 0, 0, 0) else Color.argb(150, 255, 255, 255)
      road.strokeWidth = (if (pass == 0) 4.5f else 1.8f) * u
      c.drawLine(cx, 0f, cx, h, road)
      c.drawLine(w * 0.04f, h * 0.26f, w * 0.96f, h * 0.26f, road)
      c.drawLine(w * 0.08f, h * 0.62f, w * 0.92f, h * 0.62f, road)
      c.drawLine(w * 0.25f, h * 0.05f, w * 0.25f, h * 0.95f, road)
      c.drawLine(w * 0.77f, 0f, w * 0.77f, h * 0.9f, road)
    }
    path.rewind()
    path.moveTo(cx, cy); path.lineTo(cx, h * 0.26f + 14 * u); path.quadTo(cx, h * 0.26f, cx + 14 * u, h * 0.26f); path.lineTo(w, h * 0.26f)
    route.color = NavOverlay.ROUTE_CASE; route.strokeWidth = 9 * u; c.drawPath(path, route)
    route.color = NavOverlay.ROUTE; route.strokeWidth = 5 * u; c.drawPath(path, route)
    car(c, cx, cy, u)
  }
}
