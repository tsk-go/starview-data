package com.starpilot.starview

import org.json.JSONObject

/**
 * 2.90.14 (owner, Nov 25: "a hundred feet before the destination I should see the address that I put in so I can look
 * for the house number"; then "500 ft" and "keep both"): from 500 ft before the destination the turn card shows the
 * address with the house number big ([NavCardView]), and the Scene stands the number on a stick at the curb where the
 * destination is ([LaneSceneView]). Both start when the comma's current instruction is the arrival (so a last turn
 * inside those 500 ft still shows as the turn first) and the address is the destination the tablet already has from the
 * comma (nav_state's destination: name and full address).
 */
object Arrival {
  const val SHOW_M = 152.4           // 500 ft
  const val ARRIVED_M = 12.0         // "Arrived" only at the house (40 ft): before that the feet count down

  /** [number] the house number ("" when the place has none), [street] without it, [town] ("Brooklyn, New York"),
   *  [name] the place's own name when it isn't just its address ("Home", "Joe's Pizza"), else "" */
  data class Address(val number: String, val street: String, val town: String, val name: String) {
    /** what the Scene's tag says: the house number, else a short name */
    fun tag(): String = number.ifEmpty { if (name.length in 1..18) name else "" }
  }

  /** [side] 1 the destination is on the right, -1 on the left, 0 not known */
  class Now(val distM: Double, val arrived: Boolean, val side: Int, val addr: Address, val lat: Double, val lon: Double)

  private val NUMBER = Regex("^(\\d+[A-Za-z]?(?:\\s*[-–]\\s*\\d+[A-Za-z]?)?)\\s+(\\S.*)$")
  private val COUNTRY = setOf("united states", "united states of america", "usa", "us")
  private val ZIP = Regex("\\s+\\d{5}(?:-\\d{4})?$")
  private val STREET = Regex("(?i)\\b(street|st|avenue|ave|road|rd|boulevard|blvd|parkway|pkwy|place|pl|drive|dr|lane|ln|" +
    "court|ct|way|terrace|ter|highway|hwy|expressway|expy|turnpike|tpke|plaza|square|sq|circle|cir|loop|row|walk)\\b")

  /** "1525 East 3rd Street, Brooklyn, New York 11230, United States" -> 1525 / East 3rd Street / Brooklyn, New York */
  fun parse(name: String?, address: String?): Address {
    val n = name?.trim().orEmpty()
    val a = address?.trim().orEmpty()
    val parts = (a.ifEmpty { n }).split(',').map { it.trim() }.filter { it.isNotEmpty() && it.lowercase() !in COUNTRY }
    val i = parts.indexOfFirst { NUMBER.matches(it) }
    val number: String; val street: String; val rest: List<String>
    if (i >= 0) {
      val m = NUMBER.find(parts[i])!!
      number = m.groupValues[1].replace(Regex("\\s+"), ""); street = m.groupValues[2].trim(); rest = parts.drop(i + 1)
    } else {
      number = ""
      // no house number: the first part (not the place's own name) is its street only when it reads like one, else
      // it is already the town ("Queens, New York 11430" for an airport)
      val others = parts.filter { !it.equals(n, ignoreCase = true) }
      street = others.firstOrNull()?.takeIf { STREET.containsMatchIn(it) } ?: ""
      rest = if (street.isEmpty()) others else others.drop(1)
    }
    val town = rest.take(2).map { it.replace(ZIP, "").trim() }.filter { it.isNotEmpty() && !it.all { c -> c.isDigit() } }.joinToString(", ")
    val ownName = when {
      n.isEmpty() -> ""
      NUMBER.matches(n.substringBefore(',')) -> ""                         // the name is an address itself
      n.equals(street, ignoreCase = true) -> ""
      else -> n
    }
    return Address(number, street, town, ownName)
  }

  /** the arrival step's side: Mapbox's modifier ("right", "slight left", ...), else 0 */
  fun side(modifier: String?): Int {
    val m = modifier?.lowercase().orEmpty()
    return when {
      m.contains("right") -> 1
      m.contains("left") -> -1
      else -> 0
    }
  }

  /** arriving now: the comma's current instruction is the arrival, within [SHOW_M]; null otherwise */
  fun of(nav: JSONObject?, dest: JSONObject?): Now? {
    if (nav == null || nav.optString("maneuverType") != "arrive") return null
    val d = nav.optDouble("maneuverDistance", Double.NaN)
    val rem = nav.optDouble("distanceRemaining", Double.NaN)
    val dist = if (!d.isNaN()) d else rem
    if (dist.isNaN() || dist > SHOW_M) return null
    val name = dest?.optString("name")?.takeIf { it.isNotEmpty() } ?: nav.optString("destination")
    val addr = parse(name, dest?.optString("address"))
    if (addr.number.isEmpty() && addr.street.isEmpty() && addr.name.isEmpty()) return null
    val arrived = dist < ARRIVED_M || (!rem.isNaN() && rem < ARRIVED_M)
    return Now(dist, arrived, side(nav.optString("maneuverModifier")), addr,
      dest?.optDouble("latitude", Double.NaN) ?: Double.NaN, dest?.optDouble("longitude", Double.NaN) ?: Double.NaN)
  }
}
