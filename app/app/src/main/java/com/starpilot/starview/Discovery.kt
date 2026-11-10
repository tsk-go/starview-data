package com.starpilot.starview

import android.util.Log
import org.json.JSONObject
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketTimeoutException

/**
 * Finds a starviewd on any attached network by UDP broadcast ("STARVIEW? <nonce>" -> JSON reply on :8091).
 * Once paired, a reply counts only if it proves it holds our pairing key for this nonce (and has the dongle id from
 * the QR code); anyone else can answer, but can't fake that.
 */
object Discovery {
  private const val TAG = "StarView/Discovery"
  const val PORT = 8091

  /** [verified]: proved it holds our pairing key (always false while unpaired). */
  data class Found(val host: String, val port: Int, val dongleId: String, val branch: String, val commit: String, val verified: Boolean)

  private fun broadcastAddresses(): List<InetAddress> {
    val out = mutableListOf<InetAddress>()
    try {
      for (nif in NetworkInterface.getNetworkInterfaces()) {
        if (!nif.isUp || nif.isLoopback) continue
        for (ia in nif.interfaceAddresses) {
          val b = ia.broadcast ?: continue
          out.add(b)
        }
      }
    } catch (e: Exception) {
      Log.w(TAG, "enumerating interfaces: $e")
    }
    out.add(InetAddress.getByName("255.255.255.255"))
    return out.distinct()
  }

  /** Prefix -> interface name, for every IPv4 interface address. */
  private fun subnets(): List<Triple<Int, Int, String>> {  // (network, mask, ifname)
    val out = mutableListOf<Triple<Int, Int, String>>()
    try {
      for (nif in NetworkInterface.getNetworkInterfaces()) {
        if (!nif.isUp || nif.isLoopback) continue
        for (ia in nif.interfaceAddresses) {
          val a = ia.address.address; if (a.size != 4) continue
          val ip = java.nio.ByteBuffer.wrap(a).int
          val mask = if (ia.networkPrefixLength <= 0) 0 else (-1 shl (32 - ia.networkPrefixLength)).toInt()
          out.add(Triple(ip and mask, mask, nif.name))
        }
      }
    } catch (_: Exception) {}
    return out
  }

  /** Interface a responder lives on, e.g. "rndis0" (USB tether), "wlan0" (WiFi), or "" if unknown. */
  fun interfaceFor(host: String): String {
    val ip = try { java.nio.ByteBuffer.wrap(InetAddress.getByName(host).address).int } catch (_: Exception) { return "" }
    for ((net, mask, name) in subnets()) if ((ip and mask) == net) return name
    return ""
  }

  /** True when some USB-type interface (tether) is up with an IPv4 address. Cheap: no network traffic. */
  fun usbInterfaceUp(): Boolean = subnets().any { isUsb(it.third) }

  /** USB tether links only. Not eth: a USB-Ethernet adapter can be a whole LAN (the comma doesn't trust it either). */
  fun isUsb(ifname: String) = ifname.startsWith("rndis") || ifname.startsWith("ncm") || ifname.startsWith("usb")

  /**
   * Blocks up to [timeoutMs], collecting every responder. Paired: only responders that proved they hold our key
   * count; if none did, returns an unverified one so the caller can say "scan the QR again" (and won't sign in to it).
   * Among those, prefers one reached over the USB tether over WiFi, so a tablet with WiFi on still uses the cable.
   */
  fun find(timeoutMs: Int = 1500): Found? {
    val sock = DatagramSocket()
    val found = LinkedHashMap<String, Found>()
    try {
      sock.broadcast = true
      sock.soTimeout = 250
      val cn = Link.nonce()
      val payload = "STARVIEW? $cn".toByteArray()
      val targets = broadcastAddresses()
      Log.i(TAG, "broadcasting to $targets")
      val deadline = System.currentTimeMillis() + timeoutMs
      var lastSend = 0L
      val buf = ByteArray(2048)
      var firstReplyAt = 0L
      while (System.currentTimeMillis() < deadline) {
        if (System.currentTimeMillis() - lastSend > 500) {
          for (t in targets) {
            try { sock.send(DatagramPacket(payload, payload.size, t, PORT)) } catch (e: Exception) { Log.d(TAG, "send $t: $e") }
          }
          lastSend = System.currentTimeMillis()
        }
        try {
          val p = DatagramPacket(buf, buf.size)
          sock.receive(p)
          val j = JSONObject(String(p.data, 0, p.length))
          if (j.optString("type") == "starview") {
            val host = p.address.hostAddress ?: continue
            val dongle = j.optString("dongleId")
            val verified = Link.commaProofOk(dongle, cn, "", j.optString("proof"))
            if (found[host]?.verified == true && !verified) continue  // a later fake reply can't replace a verified one
            found[host] = Found(host, j.optInt("port", 8090), dongle, j.optString("branch"), j.optString("commit"), verified)
            if (firstReplyAt == 0L) firstReplyAt = System.currentTimeMillis()
            if (isUsb(interfaceFor(host)) && (verified || !Link.paired)) break  // the one we want; stop early
          }
        } catch (_: SocketTimeoutException) {
        } catch (e: Exception) {
          Log.d(TAG, "recv: $e")
        }
        if (firstReplyAt != 0L && System.currentTimeMillis() - firstReplyAt > 600) break  // give USB 600 ms to answer too
      }
      if (found.isEmpty()) return null
      val pool = found.values.filter { it.verified }.ifEmpty { found.values.toList() }
      val best = pool.firstOrNull { isUsb(interfaceFor(it.host)) } ?: pool.first()
      Log.i(TAG, "responders: ${found.values.map { "${it.host}(${interfaceFor(it.host)}${if (it.verified) ", verified" else ""})" }} -> ${best.host}")
      return best
    } finally {
      sock.close()
    }
  }
}
