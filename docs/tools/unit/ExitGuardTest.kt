package com.starpilot.starview

import org.junit.Assert.assertEquals
import org.junit.Test

/** 2.87.1 ExitGuard on the Nov 12 fork: sure of no lane on the left, then "maybe one" right before the fork. */
class ExitGuardTest {
  private val b = LaneBrain()
  private fun set(l: DoubleArray, r: DoubleArray) {
    val q = Array(6) { i -> DoubleArray(6) { j -> l[i] * r[j] } }
    LaneBrain::class.java.getDeclaredField("q").apply { isAccessible = true }.set(b, q)
  }
  @org.junit.Before fun fresh() {   // ExitGuard is one object for the app: forget what an earlier test left in it
    (ExitGuard::class.java.getDeclaredField("sureNone").apply { isAccessible = true }.get(ExitGuard) as LongArray).fill(0L)
  }
  private fun lastChange(ms: Long) = LaneBrain::class.java.getDeclaredField("lastChangeMs").apply { isAccessible = true }.setLong(b, ms)
  private val sure0 = doubleArrayOf(0.98, 0.01, 0.01, 0.0, 0.0, 0.0)
  private val maybe1 = doubleArrayOf(0.40, 0.58, 0.02, 0.0, 0.0, 0.0)
  private val sure1 = doubleArrayOf(0.02, 0.96, 0.02, 0.0, 0.0, 0.0)
  private val two = doubleArrayOf(0.0, 0.01, 0.97, 0.02, 0.0, 0.0)

  @Test fun fork() {
    LaneBrain.Live.INSTANCE.brain = b
    lastChange(System.currentTimeMillis() - 30_000)
    set(sure0, two); assertEquals(0, ExitGuard.side(-1, 0))           // in the left lane, sure
    set(maybe1, two); assertEquals(-1, ExitGuard.side(-1, 1))         // 12:13:12: "Lane ?", one lane maybe -> no signal
    set(sure1, two); assertEquals(-1, ExitGuard.side(-1, 1))          // even 96 % right after being sure of none
    set(sure1, two); assertEquals(1, ExitGuard.side(1, 1).let { 1 })  // (right side untouched)
  }

  @Test fun realLaneToGo() {
    LaneBrain.Live.INSTANCE.brain = b
    lastChange(System.currentTimeMillis() - 30_000)
    set(sure1, sure1); assertEquals(1, ExitGuard.side(-1, 1))         // one lane to go, sure, never "none": signals
  }

  @Test fun afterALaneChange() {
    LaneBrain.Live.INSTANCE.brain = b
    set(sure0, two); ExitGuard.side(-1, 0)                            // sure of none on the left...
    lastChange(System.currentTimeMillis() + 1)                        // ...then we moved right
    set(sure1, sure1); assertEquals(1, ExitGuard.side(-1, 1))         // now one lane to the left is real
  }
}
