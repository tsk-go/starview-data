package com.starpilot.starview

import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/** Nov 20 sync: the update's AGNOS step (StarPilot's updater, stage "agnos" with its own percent) on the Software page */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35])
class SoftwareAgnosTest {
  private fun status(fast: JSONObject) = JSONObject()
    .put("installed", JSONObject().put("branch", "testing").put("commit", "bf5ec026fe"))
    .put("updater", JSONObject().put("state", "updating...").put("fast", fast))
    .put("operations", JSONObject())

  @Test fun theAgnosStepShowsItsOwnPercent() {
    val r = FastUpdate.INSTANCE.from70(status(JSONObject().put("stage", "agnos")
      .put("detail", "Downloading and writing AGNOS system").put("percent", 37)))
    assertEquals(37, r.getInt("progressPercent"))
    assertEquals("Downloading and writing AGNOS system", r.getString("progressLabel"))
    // no percent given: between "applying" (70) and "submodules" (85)
    val r2 = FastUpdate.INSTANCE.from70(status(JSONObject().put("stage", "agnos").put("detail", "Checking AGNOS operating system update...")))
    assertEquals(78, r2.getInt("progressPercent"))
  }

  @Test fun theOtherStepsAreAsBefore() {
    assertEquals(70, FastUpdate.INSTANCE.from70(status(JSONObject().put("stage", "applying").put("detail", "x").put("percent", 12)))
      .getInt("progressPercent"))
    assertEquals(25, FastUpdate.INSTANCE.from70(status(JSONObject().put("stage", "fetching"))).getInt("progressPercent"))
  }
}
