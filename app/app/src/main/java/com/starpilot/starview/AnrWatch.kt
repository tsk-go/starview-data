package com.starpilot.starview

import android.content.Context
import android.os.Handler
import android.os.Looper
import java.io.File
import java.util.concurrent.atomic.AtomicLong

/**
 * 2.85.4: when the screen's thread is stuck for 4 s ("isn't responding"), writes where it is stuck to
 * files/last_anr.txt (three samples a second apart), read from the comma with
 * `adb -d shell run-as com.starpilot.starview cat files/last_anr.txt`. Costs one message every 2 s.
 */
object AnrWatch {
  @Volatile private var started = false

  fun start(ctx: Context) {
    if (started) return
    started = true
    val file = File(ctx.applicationContext.filesDir, "last_anr.txt")
    val version = try { ctx.packageManager.getPackageInfo(ctx.packageName, 0).versionName ?: "?" } catch (e: Exception) { "?" }
    val main = Handler(Looper.getMainLooper())
    val answered = AtomicLong(0)
    Thread({
      var n = 0L
      while (true) {
        n++
        val ask = n
        main.post { answered.set(ask) }
        try { Thread.sleep(4000) } catch (e: InterruptedException) { return@Thread }
        if (answered.get() >= ask) continue
        val sb = StringBuilder("StarView $version: screen thread stuck since about ${java.util.Date(System.currentTimeMillis() - 4000)}\n")
        for (k in 1..3) {
          sb.append("\n--- sample $k ---\n")
          for (f in Looper.getMainLooper().thread.stackTrace.take(40)) sb.append("  at ").append(f).append('\n')
          if (answered.get() >= ask) { sb.append("(recovered)\n"); break }
          try { Thread.sleep(1000) } catch (e: InterruptedException) { return@Thread }
        }
        try { file.writeText(sb.toString()) } catch (e: Exception) {}
        while (answered.get() < ask) { try { Thread.sleep(500) } catch (e: InterruptedException) { return@Thread } }
      }
    }, "anr-watch").apply { isDaemon = true; priority = Thread.MIN_PRIORITY }.start()
  }
}
