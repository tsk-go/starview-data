package com.starpilot.starview

import android.app.ActivityOptions
import android.app.AlarmManager
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
 * Updates StarView from the GitHub releases of tsk-go/starview-data (built and signed there by the "StarView app"
 * workflow from the source in app/). "Check for updates" checks when tapped; from 2.85.8 "Install updates
 * automatically" (on unless switched off) asks GitHub once a minute - a conditional request (ETag), so "nothing new" is
 * a tiny 304 that GitHub doesn't count against its limit - and installs a new version at once, even while driving.
 * Android 12+ lets an app update itself without the confirmation screen (setRequireUserAction); the system installer
 * still only accepts an APK signed with the same key as the installed app. After the install Android has closed
 * StarView: [Relaunch] opens it again (and the comma's starviewd does too, when the tablet's connection stays gone).
 */
object AppUpdate {
  const val REPO = "tsk-go/starview-data"
  private const val TAG = "StarView/Update"

  class Release(val version: String, val apkUrl: String, val size: Long, val notes: String, val page: String)

  private val http: OkHttpClient by lazy {
    OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).build()
  }
  private val main = Handler(Looper.getMainLooper())

  @Volatile var busy = false
    private set

  private const val PREFS = "starview_update"
  fun autoOn(ctx: Context): Boolean = ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getBoolean("auto", true)
  fun setAuto(ctx: Context, on: Boolean) { ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit().putBoolean("auto", on).apply() }

  @Volatile private var autoStarted = false
  private var etag: String? = null
  private var lastSeen: Release? = null
  private var tried: String? = null; private var triedAt = 0L

  /** The automatic updates: one daemon thread for the app's life, a check a minute (first one 20 s after start). */
  fun startAuto(ctx: Context) {
    if (autoStarted) return
    autoStarted = true
    val app = ctx.applicationContext
    Thread({
      var wait = 20_000L
      while (true) {
        try { Thread.sleep(wait) } catch (e: InterruptedException) { return@Thread }
        wait = 60_000L
        try {
          if (!autoOn(app) || busy) continue
          // never the "allow installs" settings screen from here (it could come up while driving): the button asks for it
          if (Build.VERSION.SDK_INT >= 26 && !app.packageManager.canRequestPackageInstalls()) continue
          val rel = latestIfChanged() ?: continue
          val now = System.currentTimeMillis()
          if (!newer(rel.version, installed(app))) continue
          if (rel.version == tried && now - triedAt < 30 * 60_000L) continue   // one try per version per half hour
          tried = rel.version; triedAt = now
          Log.i(TAG, "auto: installing ${rel.version}")
          if (inFront()) main.post { Toast.makeText(app, "Updating StarView to ${rel.version} - it restarts in a few seconds", Toast.LENGTH_LONG).show() }
          install(app, rel) { msg, failed -> if (failed) Log.w(TAG, "auto: $msg") }
        } catch (e: RateLimited) {
          wait = 15 * 60_000L
        } catch (e: Exception) {
          Log.w(TAG, "auto: $e")
        }
      }
    }, "starview-autoupdate").apply { isDaemon = true; priority = Thread.MIN_PRIORITY }.start()
  }

  private class RateLimited : RuntimeException()

  /** StarView is what's on screen (one of its activities visible), not just running behind another app. */
  fun inFront(): Boolean = try {
    val i = android.app.ActivityManager.RunningAppProcessInfo()
    android.app.ActivityManager.getMyMemoryState(i)
    i.importance == android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
  } catch (e: Exception) { true }

  /** The newest app release, asked with the last ETag: the same release again when GitHub says "not modified". */
  private fun latestIfChanged(): Release? {
    val b = Request.Builder().url("https://api.github.com/repos/$REPO/releases?per_page=10").header("Accept", "application/vnd.github+json")
    etag?.let { b.header("If-None-Match", it) }
    http.newCall(b.build()).execute().use { r ->
      if (r.code == 304) return lastSeen
      if (r.code == 403 || r.code == 429) throw RateLimited()
      if (!r.isSuccessful) throw RuntimeException("GitHub said HTTP ${r.code}")
      val rel = pick(org.json.JSONArray(r.body!!.string()))
      etag = r.header("ETag"); lastSeen = rel
      return rel
    }
  }

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

  /** The newest app release (tag "v<version>" with an APK; data releases like "hpms-2024" are skipped). Blocking. */
  fun latest(): Release {
    val req = Request.Builder().url("https://api.github.com/repos/$REPO/releases?per_page=30")
      .header("Accept", "application/vnd.github+json").build()
    http.newCall(req).execute().use { r ->
      if (!r.isSuccessful) throw RuntimeException("GitHub said HTTP ${r.code}")
      return pick(org.json.JSONArray(r.body!!.string())) ?: throw RuntimeException("no StarView release on GitHub yet")
    }
  }

  private fun pick(list: org.json.JSONArray): Release? {
      var best: Release? = null
      for (k in 0 until list.length()) {
        val o = list.getJSONObject(k)
        val tag = o.optString("tag_name")
        if (o.optBoolean("draft") || o.optBoolean("prerelease") || !Regex("v\\d+(\\.\\d+)*").matches(tag)) continue
        val assets = o.optJSONArray("assets") ?: continue
        for (i in 0 until assets.length()) {
          val a = assets.getJSONObject(i)
          if (!a.optString("name").endsWith(".apk") || a.optString("state", "uploaded") != "uploaded") continue
          val rel = Release(tag.removePrefix("v"), a.optString("browser_download_url"), a.optLong("size"), o.optString("body"), o.optString("html_url"))
          if (best == null || newer(rel.version, best.version)) best = rel
          break
        }
      }
      return best
  }

  /** Checks on a thread of its own; [done] runs on the main thread with the release (null = failed) and a message. */
  fun check(ctx: Context, done: (Release?, String) -> Unit) {
    val have = installed(ctx)
    Thread {
      val res: Pair<Release?, String> = try {
        val rel = latest()
        if (newer(rel.version, have)) rel to "StarView ${rel.version} is available (you have $have, ${rel.size / 1048576} MB)"
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
      status("Allow StarView to install apps (the switch on the next screen), then tap again", true)
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
      val apk = File(dir, "StarView-${rel.version}.apk")
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
                if (pct >= shown + 5 || (pct == 100 && shown != 100)) { shown = pct; main.post { status("Downloading StarView ${rel.version}… $pct%", false) } }
              }
            }
          }
          if (rel.size > 0 && got != rel.size) throw RuntimeException("download incomplete (${got / 1048576} of ${rel.size / 1048576} MB)")
        }
        main.post { status("Installing StarView ${rel.version}…", false) }
        val pi = app.packageManager.packageInstaller
        val params = PackageInstaller.SessionParams(PackageInstaller.SessionParams.MODE_FULL_INSTALL)
        params.setAppPackageName(app.packageName)
        params.setSize(apk.length())
        // 2.85.8: no confirmation screen - an app updating itself may (Android 12+, UPDATE_PACKAGES_WITHOUT_USER_ACTION)
        if (Build.VERSION.SDK_INT >= 31) params.setRequireUserAction(PackageInstaller.SessionParams.USER_ACTION_NOT_REQUIRED)
        val id = pi.createSession(params)
        pi.openSession(id).use { s ->
          apk.inputStream().use { inp -> s.openWrite("base.apk", 0, apk.length()).use { out -> inp.copyTo(out, 256 * 1024); s.fsync(out) } }
          val flags = PendingIntent.FLAG_UPDATE_CURRENT or (if (Build.VERSION.SDK_INT >= 31) PendingIntent.FLAG_MUTABLE else 0)
          val cb = PendingIntent.getBroadcast(app, id, Intent(app, AppUpdateReceiver::class.java), flags)
          // 2.85.12: StarView on screen -> it comes back on screen. Another app in front (or the screen off) -> it comes
          // back behind that app (its comma link, turn signals, lane prompts, RTK corrections run again), not over it
          Relaunch.arm(app, 45_000L, back = !inFront())
          s.commit(cb.intentSender)
        }
        main.post { status("Installing - StarView closes and opens again as ${rel.version}.", false) }
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

/** Settings > Tablet > About: "Updates" row with the Check for updates / Install button. */
fun appUpdateRow(ctx: Context, ui: CarUi): android.view.View {
  val (row, desc) = ui.row("Updates", "Checks GitHub (" + AppUpdate.REPO + ") for a newer StarView", 0, null, null)
  var rel: AppUpdate.Release? = null
  var checking = false
  lateinit var btn: android.widget.TextView
  btn = ui.button("Check for updates", false) {
    val r = rel
    if (checking || AppUpdate.busy) return@button
    if (r == null) {
      checking = true
      desc?.text = "Checking…"
      AppUpdate.check(ctx) { found, msg ->
        checking = false
        rel = found
        desc?.text = msg
        if (found != null) btn.text = "Install " + found.version
      }
    } else {
      AppUpdate.install(ctx, r) { msg, _ -> desc?.text = msg }
    }
  }
  row.addView(btn)
  // 2.85.8: automatic updates (on unless switched off), with what it means while driving
  val col = android.widget.LinearLayout(ctx).apply { orientation = android.widget.LinearLayout.VERTICAL }
  col.addView(row)
  val (arow, _) = ui.row("Install updates automatically",
    "A new StarView installs as soon as it's published (checked every minute) - even while driving. Warning: StarView " +
    "closes during the install and opens again by itself; the driving screen is gone for about 10-30 seconds. " +
    "Switch off to update only with the button above.", 0, null, null)
  val sw = ui.bigSwitch()
  sw.isChecked = AppUpdate.autoOn(ctx)
  sw.setOnCheckedChangeListener { _, on -> AppUpdate.setAuto(ctx, on) }
  arow.addView(sw)
  col.addView(arow, android.widget.LinearLayout.LayoutParams(-1, -2).apply { topMargin = ui.dp(3) })
  // 2.85.10: without "display over other apps" Android keeps StarView closed after it updated itself
  if (!Overlay.allowed(ctx)) {
    val (orow, _) = ui.row("Open again after an update",
      "Android only lets StarView reopen by itself if it may display over other apps. Tap Allow and switch it on " +
      "(or the comma can: adb shell appops set com.starpilot.starview SYSTEM_ALERT_WINDOW allow).", 0, null, null)
    orow.addView(ui.button("Allow", false) { Overlay.ask(ctx) })
    col.addView(orow, android.widget.LinearLayout.LayoutParams(-1, -2).apply { topMargin = ui.dp(3) })
  }
  return col
}

/**
 * Opening StarView again after it updated itself (Android closes the app to replace it and doesn't reopen it):
 * MY_PACKAGE_REPLACED starts it at once when Android allows that, and an alarm-clock alarm (its PendingIntent may
 * start an activity from the background) a moment later in case it didn't. Before the install a second alarm is set
 * further out, in case the broadcast never comes. MainActivity cancels both when it starts. The comma's starviewd is
 * the backstop: car on and no tablet connection for a few seconds -> it starts StarView over adb.
 */
object Relaunch {
  private const val MARK = "relaunch_pending"
  private fun pi(ctx: Context): PendingIntent {
    val i = Intent(ctx, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    val opts = if (Build.VERSION.SDK_INT >= 34) ActivityOptions.makeBasic()
      .setPendingIntentCreatorBackgroundActivityStartMode(ActivityOptions.MODE_BACKGROUND_ACTIVITY_START_ALLOWED).toBundle() else null
    return PendingIntent.getActivity(ctx, 7701, i, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE, opts)
  }
  /** [back]: StarView wasn't on screen - it is started behind the app in front (no alarm: that would open it on top). */
  fun arm(ctx: Context, inMs: Long, back: Boolean = false) {
    try {
      java.io.File(ctx.filesDir, MARK).writeText(if (back) "back" else "front")
      if (back) return
      val am = ctx.getSystemService(AlarmManager::class.java)
      am.setAlarmClock(AlarmManager.AlarmClockInfo(System.currentTimeMillis() + inMs, null), pi(ctx))
    } catch (e: Exception) { Log.w("StarView/Update", "relaunch arm: $e") }
  }
  fun pending(ctx: Context) = java.io.File(ctx.filesDir, MARK).exists()
  fun back(ctx: Context) = try { java.io.File(ctx.filesDir, MARK).readText().trim() == "back" } catch (e: Exception) { false }
  /** The extra that makes MainActivity go straight behind the app in front (moveTaskToBack) once it's created. */
  const val EXTRA_BACK = "sv_back"
  fun cancel(ctx: Context) {
    try {
      java.io.File(ctx.filesDir, MARK).delete()
      ctx.getSystemService(AlarmManager::class.java).cancel(pi(ctx))
    } catch (e: Exception) { Log.w("StarView/Update", "relaunch cancel: $e") }
  }
}

/** The app was just replaced by a newer version: open it again (only after an update StarView installed itself). */
class AppReplacedReceiver : BroadcastReceiver() {
  override fun onReceive(ctx: Context, intent: Intent) {
    if (intent.action != Intent.ACTION_MY_PACKAGE_REPLACED || !Relaunch.pending(ctx)) return
    Log.w("StarView/Update", "updated to ${AppUpdate.installed(ctx)}: opening StarView")
    val app = ctx.applicationContext
    // 2.85.10: Android (2.85.9 on the tablet) doesn't let an app in the background open its screen, and the alarm didn't
    // either. An app allowed to "display over other apps" may, while one of its windows is on screen (Android 15+): a
    // 1-pixel see-through window for a few seconds, then the driving screen. The comma can allow it over adb.
    val pending = goAsync()
    val main = Handler(Looper.getMainLooper())
    val wm = app.getSystemService(android.view.WindowManager::class.java)
    var dot: android.view.View? = null
    if (Overlay.allowed(app)) {
      try {
        val v = android.view.View(app)
        val lp = android.view.WindowManager.LayoutParams(1, 1, android.view.WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
          android.view.WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
          android.graphics.PixelFormat.TRANSLUCENT)
        lp.gravity = android.view.Gravity.TOP or android.view.Gravity.START
        wm.addView(v, lp); dot = v
      } catch (e: Exception) { Log.w("StarView/Update", "reopen window: $e") }
    } else Log.w("StarView/Update", "reopen: 'display over other apps' not allowed - StarView may stay closed")
    val back = Relaunch.back(app)
    if (!back) Relaunch.arm(app, 2_500L)
    main.postDelayed({
      try {
        val i = Intent(app, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        if (back) i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION).putExtra(Relaunch.EXTRA_BACK, true)
        app.startActivity(i)
      } catch (e: Exception) { Log.w("StarView/Update", "reopen: $e") }
    }, 500L)
    main.postDelayed({
      dot?.let { try { wm.removeView(it) } catch (e: Exception) {} }
      pending.finish()
    }, 4_000L)
  }
}

/** "Display over other apps" for StarView: what lets it open its screen again after it updated itself. */
object Overlay {
  fun allowed(ctx: Context): Boolean = Build.VERSION.SDK_INT < 23 || android.provider.Settings.canDrawOverlays(ctx)
  fun ask(ctx: Context) {
    try {
      ctx.startActivity(Intent(android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
        android.net.Uri.parse("package:" + ctx.packageName)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    } catch (e: Exception) { Log.w("StarView/Update", "overlay settings: $e") }
  }
}

/** The system installer's answer: asks the user to confirm, or says why it failed. */
class AppUpdateReceiver : BroadcastReceiver() {
  override fun onReceive(ctx: Context, intent: Intent) {
    when (val st = intent.getIntExtra(PackageInstaller.EXTRA_STATUS, PackageInstaller.STATUS_FAILURE)) {
      PackageInstaller.STATUS_PENDING_USER_ACTION -> {
        Relaunch.cancel(ctx)          // still running: the alarm would bring StarView over the confirmation screen
        @Suppress("DEPRECATION")
        val confirm = if (Build.VERSION.SDK_INT >= 33) intent.getParcelableExtra(Intent.EXTRA_INTENT, Intent::class.java)
                      else intent.getParcelableExtra<Intent>(Intent.EXTRA_INTENT)
        if (confirm != null) ctx.startActivity(confirm.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
      }
      PackageInstaller.STATUS_SUCCESS -> {}
      else -> {
        Relaunch.cancel(ctx)
        val msg = intent.getStringExtra(PackageInstaller.EXTRA_STATUS_MESSAGE) ?: "status $st"
        Log.w("StarView/Update", "installer: $st $msg")
        if (st != PackageInstaller.STATUS_FAILURE_ABORTED) Toast.makeText(ctx, "StarView update not installed: $msg", Toast.LENGTH_LONG).show()
      }
    }
  }
}
