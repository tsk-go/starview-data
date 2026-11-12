package com.starpilot.starview

import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer

/**
 * 2.86.6: the voice note's speech recognizer listener, keeping the whole note. The tablet's recognizer splits what you
 * say at the pauses and hands back only the stretch it is on, and 2.84.1 kept only that: "Why did it turn on the
 * blinker to turn left, although I was in the left lane" was saved as "Although I was in the left lane". Now:
 *  - VoiceNote asks for a segmented session (Android 13+): each stretch comes back in onSegmentResults, the end of the
 *    note in onEndOfSegmentedSession;
 *  - a recognizer that ignores that: a partial result that doesn't carry on from the one before (fewer words, another
 *    first word) starts a new stretch, and the one before is kept. The recognizer's own stretches replace these guesses.
 * After every change the whole text goes into VoiceNote's partial, so each way a note ends (results, an error, the 6 s
 * wait after the microphone stops) saves all of it. All callbacks come on the main thread.
 */
class VoiceListen(private val note: VoiceNote) : RecognitionListener {
  private val done = ArrayList<String>()      // finished stretches
  private var given = 0                       // done[0 until given] came from the recognizer, the rest were guessed
  private var cur = ""                        // the stretch being heard now

  override fun onPartialResults(b: Bundle?) {
    val t = first(b) ?: return
    if (cur.isNotEmpty() && newStretch(cur, t)) done.add(cur)
    cur = t
    note.hybText(text())
  }

  override fun onSegmentResults(b: Bundle) {
    while (done.size > given) done.removeAt(done.size - 1)
    first(b)?.let { done.add(it) }
    given = done.size
    cur = ""
    note.hybText(text())
  }

  override fun onEndOfSegmentedSession() {
    note.hybText(text())
    note.hybDeliver(text())
  }

  override fun onResults(b: Bundle?) {
    first(b)?.let { last(it) }
    note.hybText(text())
    note.hybDeliver(text())
  }

  override fun onError(error: Int) {
    note.hybDeliver(text())
  }

  override fun onReadyForSpeech(p: Bundle?) {}
  override fun onBeginningOfSpeech() {}
  override fun onRmsChanged(v: Float) {}
  override fun onBufferReceived(b: ByteArray?) {}
  override fun onEndOfSpeech() {}
  override fun onEvent(t: Int, p: Bundle?) {}

  /** The final result of a plain session: the last stretch (2.84.1's notes show that), or now and then the whole note,
   *  or only the end of the stretch the partial results already had. */
  private fun last(f: String) {
    val fw = words(f)
    val bw = done.flatMap { words(it) }
    val cw = words(cur)
    when {
      bw.isNotEmpty() && fw.size > bw.size && fw.subList(0, bw.size) == bw -> { done.clear(); given = 0; cur = f }
      cw.size > fw.size && cw.subList(cw.size - fw.size, cw.size) == fw -> {}
      else -> cur = f
    }
  }

  /** Everything heard, stretch after stretch ("." between two when the first doesn't end a sentence). */
  fun text(): String {
    val sb = StringBuilder()
    for (s in done + cur) {
      val t = s.trim()
      if (t.isEmpty()) continue
      if (sb.isNotEmpty()) sb.append(if (sb.last().isLetterOrDigit()) ". " else " ")
      sb.append(t)
    }
    return sb.toString()
  }

  companion object {
    fun first(b: Bundle?): String? =
      b?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)?.firstOrNull()?.trim()?.takeIf { it.isNotEmpty() }

    fun words(s: String): List<String> =
      s.lowercase().split(Regex("[^a-z0-9']+")).filter { it.isNotEmpty() }

    /** A partial result that starts over: fewer words than the stretch before, its first two words not the same. A
     *  one-word stretch is never kept this way (the recognizer still trying the first word looks like that). */
    fun newStretch(prev: String, next: String): Boolean {
      val p = words(prev)
      val n = words(next)
      if (n.isEmpty() || p.size < 2 || n.size >= p.size) return false
      if (n[0] == p[0]) return false
      return !(n.size >= 2 && n[1] == p[1])
    }
  }
}
