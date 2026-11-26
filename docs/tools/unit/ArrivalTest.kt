package com.starpilot.starview

import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/** 2.90.14: the destination's address read for the arrival card and the Scene's house-number label (made-up places). */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35])
class ArrivalTest {
  private fun p(name: String?, address: String?) = Arrival.parse(name, address)

  @Test fun theHouseNumberStreetAndTown() {
    assertEquals(Arrival.Address("1525", "East 3rd Street", "Brooklyn, New York", ""),
      p("1525 East 3rd Street", "1525 East 3rd Street, Brooklyn, New York 11230, United States"))
    // Google's form, and a saved place's own name kept
    assertEquals(Arrival.Address("1525", "E 3rd St", "Brooklyn, NY", "Home"), p("Home", "1525 E 3rd St, Brooklyn, NY 11230, USA"))
    // a business: its name first in the address, the number in the next part
    assertEquals(Arrival.Address("4210", "Main St", "Queens, NY", "Joe's Pizza"), p("Joe's Pizza", "Joe's Pizza, 4210 Main St, Queens, NY 11355, USA"))
    // numbers with a letter or a range
    assertEquals("12A", p(null, "12A Bay Ridge Pkwy, Brooklyn, NY").number)
    assertEquals("101-15", p(null, "101-15 Queens Blvd, Forest Hills, NY 11375").number)
    // only a name and no address: the name itself is the address
    assertEquals(Arrival.Address("88", "Avenue U", "Brooklyn", ""), p("88 Avenue U, Brooklyn", null))
  }

  @Test fun placesWithoutAHouseNumber() {
    // the first part is the town, not a street
    assertEquals(Arrival.Address("", "", "Queens, NY", "JFK Airport"), p("JFK Airport", "Queens, NY 11430, USA"))
    assertEquals("JFK Airport", p("JFK Airport", "Queens, NY 11430, USA").tag())
    // a street without a number
    assertEquals(Arrival.Address("", "Shore Pkwy", "Brooklyn, NY", "Bay Park"), p("Bay Park", "Shore Pkwy, Brooklyn, NY 11214"))
    // a long name makes no label (the card still shows it)
    assertEquals("", p("The Big Long Shopping Center Name", "Queens, NY").tag())
    assertEquals(Arrival.Address("", "", "", "Home"), p("Home", null))
  }

  @Test fun fromFiveHundredFeetOnTheArrivalOnly() {
    val dest = JSONObject().put("name", "Home").put("address", "1525 E 3rd St, Brooklyn, NY 11230").put("latitude", 40.6).put("longitude", -73.97)
    fun nav(type: String, m: Double, mod: String = "right") = JSONObject().put("maneuverType", type).put("maneuverDistance", m)
      .put("maneuverModifier", mod).put("distanceRemaining", m)
    assertNull(Arrival.of(nav("arrive", 160.0), dest))                     // 525 ft: not yet
    assertNull(Arrival.of(nav("turn", 100.0), dest))                       // a last turn first: still the turn
    val a = Arrival.of(nav("arrive", 120.0), dest)
    assertNotNull(a); a!!
    assertEquals("1525", a.addr.number); assertEquals(1, a.side); assertEquals(false, a.arrived)
    assertEquals(-1, Arrival.of(nav("arrive", 30.0, "left"), dest)!!.side)
    assertEquals(false, Arrival.of(nav("arrive", 30.0, "left"), dest)!!.arrived)      // 100 ft: still counting
    assertEquals(true, Arrival.of(nav("arrive", 10.0, "left"), dest)!!.arrived)
    assertEquals(0, Arrival.of(nav("arrive", 60.0, ""), dest)!!.side)
    // no copy of the destination on the tablet: the instruction's own destination name
    assertEquals("Home", Arrival.of(nav("arrive", 60.0).put("destination", "Home"), null)!!.addr.name)
  }
}
