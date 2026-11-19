package com.starpilot.starview

/**
 * 2.90.9 (owner, Nov 19: "before clicking on it underneath it should say major highways where it's going to take me"):
 * the main roads of a route for the route list's "via ..." line. Mapbox sends its own summary ("Belt Parkway, Marine
 * Parkway"), which is kept; Google's routes come without one (and their steps without road names), so then it is made
 * from the route's steps: each step's road - its name, else the road its instruction leads onto ("Turn left onto
 * 86th St" -> 86th St) - with the distance driven on it; the three longest (at least half a mile or a twentieth of the
 * drive), in driving order. Patched into 2.84.1's MapPane.fillRoutes and fillLook in place of Route.getVia().
 */
object NavVia {
  private const val MAX = 3
  private const val MIN_M = 800.0

  @JvmStatic
  fun of(r: Nav.Route): String {
    if (r.via.isNotEmpty()) return r.via
    return try {
      val line = Nav.INSTANCE.shownLines.getOrNull(r.index) ?: return ""
      fromSteps(line.steps.map { Triple(it.road, it.text, it.distanceM) })
    } catch (e: Exception) {
      ""
    }
  }

  /** (road, instruction, metres driven on it) per step -> "A · B · C" */
  @JvmStatic
  fun fromSteps(steps: List<Triple<String, String, Double>>): String {
    val total = steps.sumOf { it.third.coerceAtLeast(0.0) }
    if (total <= 0) return ""
    val first = LinkedHashMap<String, Int>()
    val metres = HashMap<String, Double>()
    for ((i, s) in steps.withIndex()) {
      val name = s.first.trim().ifEmpty {
        val (big, _) = NavOverlay.street(s.second)
        if (big != s.second.trim() && !big.startsWith("Exit ")) big else ""
      }
      if (name.isEmpty() || s.third <= 0) continue
      first.putIfAbsent(name, i)
      metres[name] = (metres[name] ?: 0.0) + s.third
    }
    val floor = minOf(MIN_M, total / 20)
    return metres.entries.filter { it.value >= floor }.sortedByDescending { it.value }.take(MAX)
      .sortedBy { first[it.key] }.joinToString(" · ") { it.key }
  }
}
