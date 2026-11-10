package com.starpilot.starview

import ai.comma.cereal.Log as CLog
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import org.capnproto.ReaderOptions
import org.capnproto.Serialize
import org.json.JSONObject
import java.nio.ByteBuffer
import java.util.concurrent.TimeUnit

/** WebSocket /data client: raw capnp log.Event frames -> Telemetry. */
class DataClient(private val telemetry: Telemetry, private val onStatus: (String, Boolean) -> Unit) {
  companion object {
    private const val TAG = "StarView/Data"
    // what we ask the daemon for (Hz, 0 = native rate)
    val SUBSCRIBE = mapOf(
      "carState" to 20, "selfdriveState" to 20, "controlsState" to 10, "carControl" to 10, "carOutput" to 10,
      "liveParameters" to 5, "longitudinalPlan" to 5, "deviceState" to 0, "roadCameraState" to 1,
      "liveCalibration" to 0, "modelV2" to 0, "radarState" to 0, "driverMonitoringState" to 10,
      "slcState" to 5, "starpilotLateralState" to 10, "liveDelay" to 1, "liveTorqueParameters" to 1, "carParams" to 0,
      "starpilotPlan" to 0, "starpilotCarState" to 10, "starpilotRadarState" to 10, "mapdOut" to 0,
      "liveTracks" to 10, "gpsLocationExternal" to 0, "gpsLocation" to 0, "livePose" to 20,
      "accelerometer" to 10, "gyroscope" to 10, "ubloxGnss" to 0,
    )
    private val READER_OPTS = ReaderOptions(64L * 1024 * 1024 / 8, 64)  // 64 MB traversal, nesting 64
  }

  private val http = OkHttpClient.Builder()
    .pingInterval(5, TimeUnit.SECONDS)
    .readTimeout(0, TimeUnit.MILLISECONDS)
    .build()
  private var ws: WebSocket? = null
  @Volatile var connected = false
    private set
  var hello: JSONObject? = null
    private set
  private var pingSentNs = 0L

  fun connect(host: String, port: Int) {
    close()
    val req = Link.withSession(Request.Builder().url("ws://$host:$port/data"), host, port).build()
    ws = http.newWebSocket(req, object : WebSocketListener() {
      override fun onOpen(webSocket: WebSocket, response: Response) {
        connected = true
        webSocket.send(JSONObject(mapOf("subscribe" to JSONObject(SUBSCRIBE))).toString())
        onStatus("data: connected", true)
      }

      override fun onMessage(webSocket: WebSocket, text: String) {
        try {
          val j = JSONObject(text)
          when {
            j.optString("type") == "hello" -> { hello = j; onStatus("data: hello from ${j.optString("dongleId")} ${j.optString("branch")} ${j.optString("commit")}", true) }
            j.has("pong") -> telemetry.rttMs = (System.nanoTime() - pingSentNs) / 1e6
            j.optString("type") == "params" -> telemetry.applyParams(j)
            j.optString("type") == "rtk" -> {
              // the comma's RTK fix, 10x a second (starviewd patch 33)
              val now = System.currentTimeMillis()
              val lat = j.optDouble("lat", Double.NaN); val lon = j.optDouble("lon", Double.NaN)
              val acc = j.optDouble("hAccM", Double.NaN); val hd = j.optDouble("headingDeg", Double.NaN)
              val v = j.optDouble("speedMs", Double.NaN)
              RtkPosition.INSTANCE.feedFast(lat, lon, acc, hd, v, j.optString("rtk", "none"), j.optLong("ageMs", 0L), now)
              if (lat.isFinite() && acc.isFinite() && acc < 3.0)
                OsmRoads.INSTANCE.onFix(lat, lon, hd.toFloat(), v.toFloat(), acc.toFloat(), j.optDouble("headingAccDeg", Double.NaN).toFloat(), 0L, 0L, true)
            }
            j.optString("type") == "ack" -> {
              val id = j.optString("id", "")
              val cb = if (id.isNotEmpty()) pending.remove(id) else null
              if (cb != null) { main.post { cb(j) }; return }
              val ok = j.optBoolean("ok")
              val msg = if (ok) { val keys = j.keys().asSequence().filter { it !in setOf("ok", "type", "id") }.toList(); "OK " + keys.joinToString(" ") { "$it=${j.opt(it)}" } } else "Rejected: ${j.optString("error")}"
              if (!ok) Log.w(TAG, msg)
              onStatus("ack: $msg", true)
            }
          }
        } catch (e: Exception) { Log.w(TAG, "text: $e") }
      }

      override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        try {
          val n = bytes[0].toInt() and 0xff
          val name = bytes.substring(1, 1 + n).utf8()
          val payload = bytes.asByteBuffer().apply { position(1 + n) }.slice()
          telemetry.count(name, payload.remaining())
          val msg = Serialize.read(payload.order(java.nio.ByteOrder.LITTLE_ENDIAN), READER_OPTS)
          val evt = msg.getRoot(CLog.Event.factory)
          telemetry.apply(evt)
        } catch (e: Exception) {
          Log.w(TAG, "frame: $e")
        }
      }

      override fun onClosing(webSocket: WebSocket, code: Int, reason: String) { webSocket.close(1000, null) }
      override fun onClosed(webSocket: WebSocket, code: Int, reason: String) { ws.let { cur -> if (cur != null && webSocket !== cur) return }; connected = false; onStatus("data: closed ($code)", false) }
      override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        ws.let { cur -> if (cur != null && webSocket !== cur) return }
        connected = false
        if (response?.code == 401 && Link.paired) {
          Link.forgetSession()  // pass expired or the comma restarted: the reconnect signs in again
          onStatus("data: signing in again", false)
        } else if (response?.code == 401) {
          Link.pairingRequired = true
          onStatus("data: pairing required - on the comma open Settings › starview › show qr, then scan it", false)
        } else onStatus("data: ${t.message}", false)
      }
    })
  }

  fun control(req: JSONObject) { ws?.send(req.toString()) }

  private val pending = java.util.concurrent.ConcurrentHashMap<String, (JSONObject) -> Unit>()
  private val main = android.os.Handler(android.os.Looper.getMainLooper())
  private val reqCounter = java.util.concurrent.atomic.AtomicInteger(1)

  /** Send a control action and get its ack on the main thread (instead of the generic toast). */
  fun request(action: String, args: Map<String, Any?> = emptyMap(), cb: ((JSONObject) -> Unit)? = null) {
    val id = "r" + reqCounter.getAndIncrement()
    val j = JSONObject(); j.put("action", action); j.put("id", id)
    for ((k, v) in args) j.put(k, v)
    if (cb != null) {
      pending[id] = cb
      main.postDelayed({ pending.remove(id)?.invoke(JSONObject().put("ok", false).put("error", "timeout")) }, 90_000)
    }
    val sent = ws?.send(j.toString()) ?: false
    if (!sent && cb != null) { pending.remove(id); main.post { cb(JSONObject().put("ok", false).put("error", "not connected")) } }
  }

  fun ping() {
    pingSentNs = System.nanoTime()
    ws?.send("""{"ping":1}""")
  }

  fun close() {
    ws?.close(1000, "bye"); ws = null; connected = false
  }
}
