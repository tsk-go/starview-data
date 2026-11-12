package com.starpilot.starview

/**
 * 2.87.1: when the route's exit / fork may switch the blinker on by itself ("lane change confirmed by the route").
 *
 * The screen tick hands LaneSuggest the lanes between us and the exit's side (LaneBrain.sideLanes, -1 = not sure), and
 * LaneSuggest.exitDue (2.86.6) signals only when that is exactly 1. Nov 12, a fork on the left: the van had already moved
 * to the left lane (the lane card 97-99 % sure: Lane 1 of 3); 200 ft before the fork the camera saw the road widen on the
 * left (gore / shoulder: "left road edge 0-1 lanes away") and the map had a merge on the left, the tracker said "maybe
 * one lane on the left" (Lane ?, 21 %; sideLanes 1 at >= 50 %), the left blinker came on and openpilot started moving
 * left. The owner: the same at a few places, every time.
 *
 * Now the count is passed on only when the tracker is >= 80 % sure of exactly one lane on that side, and not when, in
 * the last 20 s, it was >= 90 % sure of none there and no lane change happened since: a lane that "appears" on the exit's
 * side right before the exit is the road widening. Unsure or already there: the driver decides (-1).
 */
object ExitGuard {
  private const val KEEP_MS = 20_000L
  /** the last time the tracker was >= 90 % sure of no lane our way on the left [0] / right [1] */
  private val sureNone = LongArray(2)

  /** From the screen tick, every time: [dir] the exit's side (-1 left, 1 right, 0 none), [n] sideLanes' count with its
   *  50 % check (-1 = not sure). Returns what LaneSuggest gets. */
  @JvmStatic
  fun side(dir: Int, n: Int): Int {
    val b = LaneBrain.Live.INSTANCE.brain ?: return n
    val now = System.currentTimeMillis()
    for (s in 0..1) {
      val (k, p) = b.side(if (s == 0) -1 else 1)
      if (k == 0 && p >= 0.9) sureNone[s] = now
    }
    if (dir == 0 || n != 1) return n
    val (k, p) = b.side(dir)
    if (k != 1 || p < 0.8) return -1
    val none = sureNone[if (dir < 0) 0 else 1]
    if (none != 0L && now - none < KEEP_MS && b.lastChangeMs < none) return -1
    return n
  }
}
