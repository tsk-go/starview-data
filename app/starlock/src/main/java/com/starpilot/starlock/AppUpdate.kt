package com.starpilot.starlock

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInstaller
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Updates StarLock from the GitHub releases of tsk-go/starview-data (tag "starlock-v<version>", built and signed there by
 * the "StarView app" workflow from the source in app/starlock). Nothing runs in the background: only when
 * "Check for updates" is tapped.
 * The APK is installed with the system installer, which shows its own confirmation and only accepts an APK signed
 * with the same key as the installed app.
 */
object AppUpdate {
  const val REPO = "tsk-go/starview-data"
  private const val TAG = "StarLock/Update"

  class Release(val version: String, val apkUrl: String, val size: Long, val notes: String, val page: String)

  private val http: OkHttpClient by lazy {
    OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).build()
  }
  private val main = Handler(Looper.getMainLooper())

  @Volatile var busy = false
    private set

  fun installed(ctx: Context): String =
    try { ctx.packageManager.getPackageInfo(ctx.packageName, 0).versionName ?: "0" } catch (e: Exception) { "0" }

  /** true when version a is newer than b ("2.84.10" > "2.84.9"). */
  fun newer(a: String, b: String): Boolean {
    val x = a.trimStart('v').split('.', '-').map { it.toIntOrNull() ?: 0 }
    val y = b.trimStart('v').split('.', '-').map { it.toIntOrNull() ?: 0 }
    for (i in 0 until maxOf(x.size, y.size)) {
      val p = x.getOrElse(i) { 0 }; val q = y.getOrElse(i) { 0 }
      if (p != q) return p > q
    }
    return false
  }

  /** The newest StarLock release (tag "starlock-v<version>" with an APK; StarView's and data releases are skipped). Blocking. */
  fun latest(): Release {
    val req = Request.Builder().url("https://api.github.com/repos/$REPO/releases?per_page=30")
      .header("Accept", "application/vnd.github+json").build()
    http.newCall(req).execute().use { r ->
      if (!r.isSuccessful) throw RuntimeException("GitHub said HTTP ${r.code}")
      val list = org.json.JSONArray(r.body!!.string())
      var best: Release? = null
      for (k in 0 until list.length()) {
        val o = list.getJSONObject(k)
        val tag = o.optString("tag_name")
        if (o.optBoolean("draft") || o.optBoolean("prerelease") || !Regex("starlock-v\\d+(\\.\\d+)*").matches(tag)) continue
        val assets = o.optJSONArray("assets") ?: continue
        for (i in 0 until assets.length()) {
          val a = assets.getJSONObject(i)
          if (!a.optString("name").endsWith(".apk")) continue
          val rel = Release(tag.removePrefix("starlock-v"), a.optString("browser_download_url"), a.optLong("size"), o.optString("body"), o.optString("html_url"))
          if (best == null || newer(rel.version, best.version)) best = rel
          break
        }
      }
      return best ?: throw RuntimeException("no StarLock release on GitHub yet")
    }
  }

  /** Checks on a thread of its own; [done] runs on the main thread with the release (null = failed) and a message. */
  fun check(ctx: Context, done: (Release?, String) -> Unit) {
    val have = installed(ctx)
    Thread {
      val res: Pair<Release?, String> = try {
        val rel = latest()
        if (newer(rel.version, have)) rel to "StarLock ${rel.version} is available (you have $have, ${rel.size / 1048576} MB)"
        else null to "You have the newest version ($have)"
      } catch (e: Exception) {
        Log.w(TAG, "check: $e")
        null to "Couldn't check: ${e.message ?: e.javaClass.simpleName}"
      }
      main.post { done(res.first, res.second) }
    }.start()
  }

  /** Downloads and hands the APK to the system installer. [status] runs on the main thread. */
  fun install(ctx: Context, rel: Release, status: (String, Boolean) -> Unit) {
    if (busy) return
    val app = ctx.applicationContext
    if (Build.VERSION.SDK_INT >= 26 && !app.packageManager.canRequestPackageInstalls()) {
      status("Allow StarLock to install apps (the switch on the next screen), then tap again", true)
      try {
        ctx.startActivity(Intent(android.provider.Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,
          android.net.Uri.parse("package:" + app.packageName)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
      } catch (e: Exception) { Log.w(TAG, "settings: $e") }
      return
    }
    busy = true
    Thread {
      val dir = File(app.cacheDir, "update").apply { mkdirs() }
      dir.listFiles()?.forEach { it.delete() }
      val apk = File(dir, "StarLock-${rel.version}.apk")
      try {
        http.newCall(Request.Builder().url(rel.apkUrl).build()).execute().use { r ->
          if (!r.isSuccessful) throw RuntimeException("download: HTTP ${r.code}")
          val body = r.body!!
          val total = if (body.contentLength() > 0) body.contentLength() else rel.size
          var got = 0L; var shown = -1
          body.byteStream().use { inp ->
            apk.outputStream().use { out ->
              val buf = ByteArray(256 * 1024)
              while (true) {
                val n = inp.read(buf); if (n < 0) break
                out.write(buf, 0, n); got += n
                val pct = if (total > 0) (got * 100 / total).toInt() else -1
                if (pct >= shown + 5 || (pct == 100 && shown != 100)) { shown = pct; main.post { status("Downloading StarLock ${rel.version}… $pct%", false) } }
              }
            }
          }
          if (rel.size > 0 && got != rel.size) throw RuntimeException("download incomplete (${got / 1048576} of ${rel.size / 1048576} MB)")
        }
        main.post { status("Installing StarLock ${rel.version}…", false) }
        val pi = app.packageManager.packageInstaller
        val params = PackageInstaller.SessionParams(PackageInstaller.SessionParams.MODE_FULL_INSTALL)
        params.setAppPackageName(app.packageName)
        params.setSize(apk.length())
        val id = pi.createSession(params)
        pi.openSession(id).use { s ->
          apk.inputStream().use { inp -> s.openWrite("base.apk", 0, apk.length()).use { out -> inp.copyTo(out, 256 * 1024); s.fsync(out) } }
          val flags = PendingIntent.FLAG_UPDATE_CURRENT or (if (Build.VERSION.SDK_INT >= 31) PendingIntent.FLAG_MUTABLE else 0)
          val cb = PendingIntent.getBroadcast(app, id, Intent(app, AppUpdateReceiver::class.java), flags)
          s.commit(cb.intentSender)
        }
        main.post { status("Confirm the install on the system screen. StarLock restarts as ${rel.version}.", false) }
      } catch (e: Exception) {
        Log.w(TAG, "install: $e")
        apk.delete()
        main.post { status("Update failed: ${e.message ?: e.javaClass.simpleName}", true) }
      } finally {
        busy = false
      }
    }.start()
  }
}

/** The "Updates" group's body: a status line and the Check for updates / Install button (styled like StarLock's buttons). */
fun appUpdateViews(ctx: Context, d: Float, muted: Int): android.view.View {
  val box = android.widget.LinearLayout(ctx).apply { orientation = android.widget.LinearLayout.VERTICAL }
  val status = android.widget.TextView(ctx).apply {
    textSize = 13f; setTextColor(muted); text = "You have StarLock " + AppUpdate.installed(ctx)
  }
  var rel: AppUpdate.Release? = null
  var checking = false
  val btn = android.widget.Button(ctx).apply {
    text = "Check for updates"; isAllCaps = false; textSize = 14f; setTextColor(android.graphics.Color.WHITE)
    background = android.graphics.drawable.GradientDrawable().apply {
      cornerRadius = 14 * d; setColor(android.graphics.Color.argb(38, 255, 255, 255))
    }
  }
  btn.setOnClickListener {
    val r = rel
    if (checking || AppUpdate.busy) return@setOnClickListener
    if (r == null) {
      checking = true
      status.text = "Checking…"
      AppUpdate.check(ctx) { found, msg ->
        checking = false
        rel = found
        status.text = msg
        if (found != null) btn.text = "Install " + found.version
      }
    } else {
      AppUpdate.install(ctx, r) { msg, _ -> status.text = msg }
    }
  }
  box.addView(status)
  box.addView(btn, android.widget.LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.WRAP_CONTENT, (40 * d).toInt()).apply {
    topMargin = (6 * d).toInt()
  })
  return box
}

/** The system installer's answer: asks the user to confirm, or says why it failed. */
class AppUpdateReceiver : BroadcastReceiver() {
  override fun onReceive(ctx: Context, intent: Intent) {
    when (val st = intent.getIntExtra(PackageInstaller.EXTRA_STATUS, PackageInstaller.STATUS_FAILURE)) {
      PackageInstaller.STATUS_PENDING_USER_ACTION -> {
        @Suppress("DEPRECATION")
        val confirm = if (Build.VERSION.SDK_INT >= 33) intent.getParcelableExtra(Intent.EXTRA_INTENT, Intent::class.java)
                      else intent.getParcelableExtra<Intent>(Intent.EXTRA_INTENT)
        if (confirm != null) ctx.startActivity(confirm.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
      }
      PackageInstaller.STATUS_SUCCESS -> {}
      else -> {
        val msg = intent.getStringExtra(PackageInstaller.EXTRA_STATUS_MESSAGE) ?: "status $st"
        Log.w("StarLock/Update", "installer: $st $msg")
        if (st != PackageInstaller.STATUS_FAILURE_ABORTED) Toast.makeText(ctx, "StarLock update not installed: $msg", Toast.LENGTH_LONG).show()
      }
    }
  }
}
