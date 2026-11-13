package com.starpilot.starview

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.os.Handler
import android.os.Looper
import android.util.Log
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.exp
import kotlin.math.sin

/**
 * 2.88.0: the speed limit warning's sounds, made in code (no sound files): each under a second, played once per
 * warning, [times] in a row (owner: "I don't want it to keep beeping, just like a triple or four beeps"). The same
 * formulas as the samples the owner picked from (docs/tools/unit/AlertSoundsTest.kt writes them as WAV files).
 * Played like the spoken directions (AmyVoice: alarm usage, sonification), so it follows that volume.
 */
object AlertSounds {
  private const val TAG = "StarView/AlertSounds"
  const val RATE = 48_000
  /** silence between two plays of the same sound */
  const val GAP_S = 0.25

  class Sound(val name: String, val make: () -> FloatArray)

  // ------------------------------------------------------------------ building blocks
  private fun env(n: Int, attack: Double, release: Double, decay: Double): FloatArray {
    val e = FloatArray(n) { 1f }
    val a = (attack * RATE).toInt().coerceAtMost(n); val r = (release * RATE).toInt().coerceAtMost(n)
    for (i in 0 until n) {
      var v = 1.0
      if (decay > 0) v *= exp(-i.toDouble() / RATE / decay)
      if (i < a) v *= i.toDouble() / a
      if (i >= n - r) v *= (n - 1 - i).toDouble() / r
      e[i] = v.toFloat()
    }
    return e
  }

  /** a note at [f] Hz with overtones [partials] (multiple of f, loudness) */
  private fun tone(f: Double, dur: Double, partials: DoubleArray, attack: Double, release: Double, decay: Double = 0.0): FloatArray {
    val n = (dur * RATE).toInt()
    val e = env(n, attack, release, decay)
    return FloatArray(n) { i ->
      val t = i.toDouble() / RATE
      var x = 0.0
      var k = 0
      while (k < partials.size) { x += partials[k + 1] * sin(2 * PI * f * partials[k] * t); k += 2 }
      (x * e[i]).toFloat()
    }
  }

  /** a short sweep from [f0] to [f1] Hz */
  private fun chirp(f0: Double, f1: Double, dur: Double): FloatArray {
    val n = (dur * RATE).toInt()
    val e = env(n, 0.004, 0.015, 0.0)
    var ph = 0.0
    return FloatArray(n) { i ->
      val f = f0 + (f1 - f0) * i / n
      ph += 2 * PI * f / RATE
      (sin(ph) * e[i]).toFloat()
    }
  }

  private fun beep(f: Double, d: Double) = tone(f, d, doubleArrayOf(1.0, 1.0, 2.0, 0.15), 0.005, 0.015)
  private fun softBeep(f: Double, d: Double) = tone(f, d, doubleArrayOf(1.0, 1.0, 2.0, 0.08), 0.02, 0.04)
  private fun bell(f: Double, decay: Double = 0.22, dur: Double = 0.45) =
    tone(f, dur, doubleArrayOf(1.0, 1.0, 2.0, 0.25, 3.0, 0.08, 4.2, 0.05), 0.002, 0.03, decay)
  private fun marimba(f: Double) = tone(f, 0.3, doubleArrayOf(1.0, 1.0, 4.0, 0.12, 10.0, 0.03), 0.002, 0.02, 0.09)

  /** notes placed at their start times (s), then made 70 % of full scale */
  private fun seq(vararg notes: Pair<Double, FloatArray>): FloatArray {
    val end = notes.maxOf { it.first + it.second.size.toDouble() / RATE } + 0.05
    val out = FloatArray((end * RATE).toInt())
    for ((s, x) in notes) {
      val o = (s * RATE).toInt()
      for (i in x.indices) if (o + i < out.size) out[o + i] += x[i]
    }
    val peak = out.maxOf { abs(it) }.coerceAtLeast(1e-6f)
    for (i in out.indices) out[i] = out[i] / peak * 0.7f
    return out
  }

  private fun every(step: Double, n: Int, note: (Int) -> FloatArray) =
    seq(*Array(n) { i -> Pair(i * step, note(i)) })

  // ------------------------------------------------------------------ the sounds (the order is what's saved: only add at the end)
  val ALL: List<Sound> = listOf(
    Sound("Triple beep") { every(0.15, 3) { beep(1000.0, 0.09) } },
    Sound("Four quick beeps") { every(0.10, 4) { beep(1400.0, 0.055) } },
    Sound("Triple chime") { every(0.16, 3) { bell(1319.0) } },
    Sound("Rising three") { every(0.12, 3) { marimba(doubleArrayOf(1047.0, 1319.0, 1568.0)[it]) } },
    Sound("Falling three") { every(0.12, 3) { marimba(doubleArrayOf(1568.0, 1319.0, 1047.0)[it]) } },
    Sound("Double double") { seq(*doubleArrayOf(0.0, 0.10, 0.32, 0.42).map { Pair(it, beep(1200.0, 0.06)) }.toTypedArray()) },
    Sound("Ding-dong") { seq(Pair(0.0, bell(784.0, 0.3, 0.6)), Pair(0.28, bell(659.0, 0.3, 0.6))) },
    Sound("Soft low triple") { every(0.20, 3) { softBeep(660.0, 0.12) } },
    Sound("Chirps") { every(0.13, 3) { chirp(900.0, 1600.0, 0.07) } },
    Sound("Ping ping") { every(0.18, 2) { bell(1760.0, 0.12, 0.3) } },
    Sound("Rising four") { every(0.09, 4) { marimba(doubleArrayOf(1047.0, 1319.0, 1568.0, 2093.0)[it]) } },
    Sound("Two-tone beeps") { every(0.11, 4) { beep(if (it % 2 == 0) 1000.0 else 1300.0, 0.08) } },
    Sound("High triangle") { every(0.14, 3) { bell(2637.0, 0.18, 0.4) } },
    Sound("Low double") { every(0.22, 2) { beep(520.0, 0.14) } },
  )

  private val cache = HashMap<Long, ShortArray>()

  /** sound [index] played [times] in a row, 16-bit mono at RATE */
  @Synchronized fun pcm(index: Int, times: Int): ShortArray {
    val key = index.toLong() * 100 + times
    cache[key]?.let { return it }
    val one = ALL[index].make()
    val gap = (GAP_S * RATE).toInt()
    val n = times.coerceIn(1, 9)
    val out = ShortArray(one.size * n + gap * (n - 1))
    for (k in 0 until n) {
      val o = k * (one.size + gap)
      for (i in one.indices) out[o + i] = (one[i] * 32767f).toInt().coerceIn(-32768, 32767).toShort()
    }
    if (cache.size > 8) cache.clear()
    cache[key] = out
    return out
  }

  @Volatile private var playing: AudioTrack? = null

  /** plays it now (stops one still playing); never throws */
  fun play(ctx: Context, index: Int, times: Int) {
    if (index !in ALL.indices) return
    Thread({
      try {
        val pcm = pcm(index, times)
        playing?.let { try { it.stop(); it.release() } catch (_: Exception) {} }
        val track = AudioTrack.Builder()
          .setAudioAttributes(AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build())
          .setAudioFormat(AudioFormat.Builder().setEncoding(AudioFormat.ENCODING_PCM_16BIT).setSampleRate(RATE)
            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO).build())
          .setTransferMode(AudioTrack.MODE_STATIC).setBufferSizeInBytes(pcm.size * 2).build()
        track.write(pcm, 0, pcm.size)
        playing = track
        track.play()
        val ms = pcm.size * 1000L / RATE + 300
        Handler(Looper.getMainLooper()).postDelayed({
          try { if (playing === track) playing = null; track.release() } catch (_: Exception) {}
        }, ms)
      } catch (e: Exception) {
        Log.w(TAG, "play $index: $e")
      }
    }, "alert-sound").start()
  }
}
