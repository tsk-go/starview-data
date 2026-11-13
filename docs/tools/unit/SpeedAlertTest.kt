package com.starpilot.starview

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import java.io.DataOutputStream
import java.io.File
import java.io.FileOutputStream

/**
 * 2.88.0 speed limit warning: the owner's list (which amount a limit gets), when the sound plays, every sound as a WAV
 * file made by the app's own code (AlertSounds), and a picture of the settings section. Writes to -DpicOut.
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [35], qualifiers = "w1472dp-h920dp-land-xhdpi")
class SpeedAlertTest {
  private val out = System.getProperty("picOut") ?: "/tmp"

  @Test fun amounts() {
    val c = SpeedAlert.Conf(listOf(SpeedAlert.Row(30, 5), SpeedAlert.Row(40, 7), SpeedAlert.Row(55, 8)), 9, 5, 3)
    assertEquals(5, c.allowed(25)); assertEquals(5, c.allowed(30))
    assertEquals(7, c.allowed(35)); assertEquals(7, c.allowed(40))
    assertEquals(8, c.allowed(45)); assertEquals(9, c.allowed(65))
    assertEquals(9, SpeedAlert.Conf(emptyList(), 9, 5, 3).allowed(30))
  }

  @Test fun onceThenAgain() {
    val a = SpeedAlert.Alarm()
    var t = 1_000_000L
    fun at(limit: Int, speed: Int, dt: Long = 1000L): Boolean { t += dt; return a.step(limit, speed, 5, t) }
    assertFalse(at(30, 35))                       // 35 in a 30 (+5): fine
    assertTrue(at(30, 36))                        // 36: warn
    assertFalse(at(30, 38)); assertFalse(at(30, 36))
    assertFalse(at(30, 34))                       // under the warning, not 2 under yet
    assertFalse(at(30, 36))                       // over again right away: no repeat
    repeat(11) { assertFalse(at(30, 33)) }        // 10 s back under (33 = 2 under the warning)
    assertTrue(at(30, 36))                        // then over again: warn again
    assertFalse(at(30, 37))
    assertTrue(at(25, 37, 6000))                  // the limit drops while over: warn again
  }

  @Test fun sounds() {
    for ((i, s) in AlertSounds.ALL.withIndex()) for (n in listOf(1, 3)) {
      val pcm = AlertSounds.pcm(i, n)
      val name = "%02d-%s%s.wav".format(i + 1, s.name.lowercase().replace(' ', '-'), if (n == 1) "" else "-x$n")
      wav(File(out, name), pcm)
    }
  }

  @Test fun page() {
    val ctx = RuntimeEnvironment.getApplication()
    val col = LinearLayout(ctx).apply { orientation = LinearLayout.VERTICAL; setBackgroundColor(CarUi.Companion.getBG()) }
    SpeedAlert.save(ctx, false, SpeedAlert.Conf(listOf(SpeedAlert.Row(30, 5), SpeedAlert.Row(40, 7), SpeedAlert.Row(55, 8)), 9, 5, 3))
    SpeedAlertPage.build(ctx, col)
    val w = 1100 * 2
    col.measure(View.MeasureSpec.makeMeasureSpec(w, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
    col.layout(0, 0, w, col.measuredHeight)
    val b = Bitmap.createBitmap(w, col.measuredHeight, Bitmap.Config.ARGB_8888)
    col.draw(Canvas(b))
    FileOutputStream(File(out, "speed_alert_page.png")).use { b.compress(Bitmap.CompressFormat.PNG, 100, it) }
  }

  private fun wav(f: File, pcm: ShortArray) {
    DataOutputStream(FileOutputStream(f)).use { o ->
      fun le32(v: Int) { o.write(v and 255); o.write(v shr 8 and 255); o.write(v shr 16 and 255); o.write(v shr 24 and 255) }
      fun le16(v: Int) { o.write(v and 255); o.write(v shr 8 and 255) }
      val bytes = pcm.size * 2
      o.writeBytes("RIFF"); le32(36 + bytes); o.writeBytes("WAVEfmt "); le32(16); le16(1); le16(1)
      le32(AlertSounds.RATE); le32(AlertSounds.RATE * 2); le16(2); le16(16); o.writeBytes("data"); le32(bytes)
      for (s in pcm) le16(s.toInt())
    }
  }
}
