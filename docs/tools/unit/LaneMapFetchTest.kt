package com.starpilot.starview

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.net.InetAddress
import java.net.ServerSocket
import kotlin.concurrent.thread

/**
 * 2.90.10 (owner, Nov 19: no road in the Scene for a mile of a street the tablet hadn't had yet): the free map servers
 * were overloaded and the tablet asked them for one square at a time, each server in turn for up to ~a minute - the
 * squares came 5 - 15 minutes apart, after we had driven through them. Map servers stood in for by local ones here.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35])
class LaneMapFetchTest {
  private val servers = ArrayList<ServerSocket>()
  private val good = """{"version":0.6,"generator":"Overpass API","elements":[{"type":"way","id":1,"tags":{"highway":"primary"},"geometry":[{"lat":40.58,"lon":-73.83},{"lat":40.581,"lon":-73.831}]}]}"""
  private val q = "[out:json];way(40.58,-73.84,40.59,-73.83);out tags geom;"

  /** a map server answering [code] / [body] after [delayMs]; its address (a bare socket: the test classpath is
   *  Android's, without the JDK's own little web server) */
  private fun server(delayMs: Long, code: Int, body: String): String {
    val s = ServerSocket(0, 50, InetAddress.getByName("127.0.0.1"))
    servers.add(s)
    thread(isDaemon = true) {
      while (!s.isClosed) {
        val c = try { s.accept() } catch (e: Exception) { break }
        thread(isDaemon = true) {
          try {
            c.use { sock ->
              val inp = sock.getInputStream().bufferedReader(Charsets.ISO_8859_1)
              var len = 0
              while (true) { val l = inp.readLine() ?: break; if (l.isEmpty()) break; if (l.startsWith("Content-Length:", true)) len = l.substringAfter(':').trim().toInt() }
              repeat(len) { inp.read() }
              Thread.sleep(delayMs)
              val b = body.toByteArray()
              val out = sock.getOutputStream()
              out.write("HTTP/1.1 $code X\r\nContent-Type: application/json\r\nContent-Length: ${b.size}\r\nConnection: close\r\n\r\n".toByteArray())
              out.write(b); out.flush()
            }
          } catch (e: Exception) { }
        }
      }
    }
    return "http://127.0.0.1:${s.localPort}/api/interpreter"
  }

  @After fun stop() { for (s in servers) try { s.close() } catch (e: Exception) { } }

  private fun timed(f: () -> String): Pair<String, Long> { val t0 = System.nanoTime(); val r = f(); return r to (System.nanoTime() - t0) / 1_000_000 }

  @Test fun aServerThatTakesLongDoesNotHoldUpTheSquare() {
    val slow = server(25_000, 200, good); val quick = server(0, 200, good)
    LaneMap.urls = listOf(slow, quick)
    val (body, ms) = timed { LaneMap.fetch(q) }
    assertEquals(good, body)
    assertTrue("the next server is asked after 6 s, not after the first gives up: $ms ms", ms in 5_500..9_000)
    // and the one that answered is asked first next time
    val (_, ms2) = timed { LaneMap.fetch(q) }
    assertTrue("asked first the next time: $ms2 ms", ms2 < 2_000)
  }

  @Test fun aServerThatSaysItIsBusyIsPassedAtOnce() {
    val busy = server(0, 504, "<html>Gateway Timeout</html>"); val quick = server(300, 200, good)
    LaneMap.urls = listOf(busy, quick)
    val (body, ms) = timed { LaneMap.fetch(q) }
    assertEquals(good, body)
    assertTrue("no wait after a server says it's busy: $ms ms", ms < 3_000)
  }

  @Test fun noServerAnsweringGivesUpWithinTheLimit() {
    LaneMap.urls = listOf(server(0, 429, "rate limited"), server(0, 504, "busy"))
    val t0 = System.nanoTime()
    val e = runCatching { LaneMap.fetch(q) }.exceptionOrNull()
    assertTrue("failed: $e", e is java.io.IOException)
    assertTrue("at once when all have said no", (System.nanoTime() - t0) / 1_000_000 < 3_000)
  }

  @Test fun anAnswerTheServerBrokeOffIsNotKept() {
    // kept for 60 days, an answer cut short would leave the square empty that long
    assertTrue(LaneMap.goodAnswer(good))
    assertTrue("water: nothing there", LaneMap.goodAnswer("""{"version":0.6,"generator":"Overpass API","elements":[]}"""))
    assertFalse(LaneMap.goodAnswer("""{"version":0.6,"remark":"runtime error: Query timed out in \"query\" at line 1 after 31 seconds.","elements":[]}"""))
    assertFalse("cut short", LaneMap.goodAnswer(good.dropLast(40)))
    assertFalse(LaneMap.goodAnswer("<html>Too Many Requests</html>"))
  }

  @Test fun theSquareNearestTheCarComesFirstAndOnesFarBehindAreLetGo() {
    // the car at 40.5838, -73.8312 (square 4058_-7384); squares asked before, along the way it came
    val keys = listOf("4058_-7389", "4058_-7388", "4058_-7386", "4058_-7385", "4058_-7384", "4058_-7383")
    val (best, gone) = LaneMap.pick(keys, 40.5838, -73.8312, emptySet())
    assertEquals("4058_-7384", best)
    assertEquals("more than 1.5 miles behind", listOf("4058_-7389", "4058_-7388"), gone)
    // a square of the route is never let go
    val (_, gone2) = LaneMap.pick(keys, 40.5838, -73.8312, setOf("4058_-7389"))
    assertEquals(listOf("4058_-7388"), gone2)
  }

  @Test fun theRouteBringsEverySquareAlongIt() {
    LaneMap.offline = true
    LaneMap.urls = listOf(server(0, 504, "busy"))        // (nothing is downloaded here)
    // a route 6 km due east along 40.5845 (0.0055 inside its row of squares, 450 ft from the next row)
    val nav = Nav.INSTANCE
    val lons = doubleArrayOf(-73.900, -73.830)
    val line = Nav.Line(DoubleArray(2) { nav.mercX(lons[it]) }, DoubleArray(2) { nav.mercY(40.5845) }, doubleArrayOf(0.0, 5900.0),
      DoubleArray(0), DoubleArray(0), IntArray(0), emptyList(), emptyList(), DoubleArray(0), emptyList())
    LaneMap.onRoute(line)
    val sq = LaneMap.routeSquares
    for (tx in -7390..-7383) assertTrue("square 4058_$tx along the route: $sq", "4058_$tx" in sq)
    assertTrue("nothing 650 ft away from it: $sq", sq.none { it.startsWith("4057_") || it.startsWith("4059_") })
    LaneMap.onRoute(null)
    assertTrue(LaneMap.routeSquares.isEmpty())
  }
}
