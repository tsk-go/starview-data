package com.starpilot.starview

import android.content.Context
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.concurrent.TimeUnit
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/** The one comma link, shared between MainActivity (owner) and SettingsActivity (user). */
object Link {
  @Volatile var telemetry: Telemetry? = null
  @Volatile var data: DataClient? = null
  @Volatile var host: String? = null
  const val GALAXY_PORT = 8082
  const val DAEMON_PORT = 8090

  /**
   * Pairing key, scanned once from the QR code on the comma (Settings › starview › show qr). It never leaves the
   * tablet: on every connect the comma first proves it holds the same key (GET /hello), then the tablet proves it
   * (POST /auth) and gets a session pass, which is what goes with every request (header X-StarView-Session; ?s= from
   * the terminal WebView). A device that only pretends to be the comma fails the first step and gets nothing.
   */
  @Volatile var termKey: String = ""
    private set
  /** Dongle id from the QR code; a comma answering with another one is not ours. Empty if the QR had none. */
  @Volatile var pairedDongle: String = ""
    private set
  /** The comma answered 401, or doesn't have our key any more: this tablet has to scan the comma's QR code. */
  @Volatile var pairingRequired = false
  const val SESSION_HEADER = "X-StarView-Session"

  @Volatile private var session: String = ""
  @Volatile private var sessionHost: String? = null
  @Volatile private var sessionUntilMs = 0L

  val paired get() = termKey.isNotEmpty()

  /** Accept a scanned `starview://pair?k=<key>&p=<port>&d=<dongle>&h=<hosts>` code. Returns false if it isn't one. */
  fun pairFromUri(ctx: Context, text: String): Boolean {
    val uri = try { android.net.Uri.parse(text.trim()) } catch (_: Exception) { return false }
    if (uri.scheme != "starview" || uri.host != "pair") return false
    val k = uri.getQueryParameter("k") ?: return false
    if (k.length < 16 || !k.all { it.isLetterOrDigit() }) return false
    val d = uri.getQueryParameter("d") ?: ""
    termKey = k; pairedDongle = d; forgetSession()
    pairingRequired = false
    ctx.getSharedPreferences("starview", Context.MODE_PRIVATE).edit()
      .putString("term_key", k)
      .putString("paired_dongle", d)
      .putString("paired_hosts", uri.getQueryParameter("h") ?: "")
      .apply()
    return true
  }

  fun loadKey(ctx: Context) {
    if (termKey.isNotEmpty()) return
    val prefs = ctx.getSharedPreferences("starview", Context.MODE_PRIVATE)
    termKey = prefs.getString("term_key", "") ?: ""
    pairedDongle = prefs.getString("paired_dongle", "") ?: ""
  }

  // ------------------------------------------------------------------ proofs (same as starviewd's pairing.py)
  private val rng = SecureRandom()
  fun nonce(): String = ByteArray(16).also { rng.nextBytes(it) }.joinToString("") { "%02x".format(it) }

  private fun mac(vararg parts: String): String {
    val m = Mac.getInstance("HmacSHA256")
    m.init(SecretKeySpec(termKey.toByteArray(Charsets.UTF_8), "HmacSHA256"))
    return m.doFinal(parts.joinToString("|").toByteArray(Charsets.UTF_8)).joinToString("") { "%02x".format(it) }
  }

  private fun same(a: String, b: String) = MessageDigest.isEqual(a.toByteArray(), b.toByteArray())

  /** True when a reply from [dongleId] proves it holds our key for nonce [cn] (and, from /hello, challenge [sn]). */
  fun commaProofOk(dongleId: String, cn: String, sn: String, proof: String): Boolean {
    if (!paired || proof.isEmpty()) return false
    if (pairedDongle.isNotEmpty() && dongleId != pairedDongle) return false
    return same(proof, mac("srv", dongleId, cn, sn))
  }

  // ------------------------------------------------------------------ session pass
  enum class Auth { OK, UNPAIRED, NOT_OUR_COMMA, OLD_BRIDGE, UNREACHABLE }

  private val http by lazy { OkHttpClient.Builder().connectTimeout(3, TimeUnit.SECONDS).readTimeout(3, TimeUnit.SECONDS).build() }

  /** The session pass for the bridge at [h]:[port], if we have one. */
  fun sessionFor(h: String?, port: Int = DAEMON_PORT): String =
    if (h != null && "$h:$port" == sessionHost && System.currentTimeMillis() < sessionUntilMs) session else ""

  fun forgetSession() { session = ""; sessionHost = null; sessionUntilMs = 0L }

  /** Adds the session pass for [h]:[port], if we have one. */
  fun withSession(b: Request.Builder, h: String?, port: Int = DAEMON_PORT): Request.Builder =
    sessionFor(h, port).let { if (it.isNotEmpty()) b.header(SESSION_HEADER, it) else b }

  /**
   * Makes sure we hold a session pass for [h]. Blocking (network): call off the UI thread.
   * OK: we have one. UNPAIRED: no key yet (connect without; fine on the USB cable while the comma trusts it).
   * NOT_OUR_COMMA: it couldn't prove it has our key (another device, or the comma was unpaired): nothing was sent.
   * OLD_BRIDGE: comma without /hello (older StarPilot): connect without, never send the key.
   */
  @Synchronized fun ensureSession(h: String, port: Int = DAEMON_PORT): Auth {
    if (!paired) return Auth.UNPAIRED
    if (sessionFor(h, port).isNotEmpty()) return Auth.OK
    forgetSession()
    try {
      val cn = nonce()
      val hello = http.newCall(Request.Builder().url("http://$h:$port/hello?n=$cn").build()).execute().use { r ->
        if (r.code == 404) return Auth.OLD_BRIDGE
        if (!r.isSuccessful) return Auth.UNREACHABLE
        JSONObject(r.body?.string() ?: return Auth.UNREACHABLE)
      }
      val dongle = hello.optString("dongleId"); val sn = hello.optString("sn")
      if (!commaProofOk(dongle, cn, sn, hello.optString("proof"))) {
        pairingRequired = true
        return Auth.NOT_OUR_COMMA
      }
      val body = JSONObject().put("sn", sn).put("cn", cn).put("proof", mac("cli", dongle, sn, cn))
      http.newCall(Request.Builder().url("http://$h:$port/auth").post(body.toString().toRequestBody("application/json".toMediaType())).build()).execute().use { r ->
        if (r.code == 401) { pairingRequired = true; return Auth.NOT_OUR_COMMA }
        if (!r.isSuccessful) return Auth.UNREACHABLE
        val j = JSONObject(r.body?.string() ?: return Auth.UNREACHABLE)
        val s = j.optString("session"); if (s.isEmpty()) return Auth.UNREACHABLE
        session = s; sessionHost = "$h:$port"
        sessionUntilMs = System.currentTimeMillis() + (j.optLong("expires_s", 3600L) * 1000L * 9 / 10)
        pairingRequired = false
        return Auth.OK
      }
    } catch (_: Exception) {
      return Auth.UNREACHABLE
    }
  }

  /**
   * For OkHttp clients making plain HTTP calls to the comma (files, storage): adds the session pass, and on a 401
   * (pass expired, or the comma restarted) gets a new one and retries once. Never sends the key.
   */
  val authInterceptor = Interceptor { ch ->
    val req = ch.request(); val h = req.url.host; val port = req.url.port
    if (paired && sessionFor(h, port).isEmpty()) ensureSession(h, port)
    val first = ch.proceed(withSession(req.newBuilder(), h, port).build())
    if (first.code != 401 || !paired || req.body?.isOneShot() == true) return@Interceptor first
    forgetSession()
    if (ensureSession(h, port) != Auth.OK) return@Interceptor first
    first.close()
    ch.proceed(withSession(req.newBuilder(), h, port).build())
  }
}
