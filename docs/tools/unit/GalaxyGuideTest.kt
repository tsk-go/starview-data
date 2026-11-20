package com.starpilot.starview

import android.widget.LinearLayout
import android.widget.TextView
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/** Nov 18 (2) sync: Galaxy's settings pages' wiki guide links (upstream `wiki-links.js` SETTINGS_WIKI_LINKS) */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35])
class GalaxyGuideTest {
  @Test fun thePagesGuideAsGalaxyHasIt() {
    assertEquals("https://wiki.firestar.link/usage/settings/#lane-changes", GalaxyMirror.wikiLink("lane_change"))
    assertEquals("https://wiki.firestar.link/exclusive/lane-centering/", GalaxyMirror.wikiLink("lane"))
    assertEquals("https://wiki.firestar.link/usage/settings/", GalaxyMirror.wikiLink("hub"))
    assertEquals("https://wiki.firestar.link/exclusive/speed-limit-controller/#vision-speed-limits", GalaxyMirror.wikiLink("slc/sources"))
    // a page without its own guide gets none (never the general one)
    assertNull(GalaxyMirror.wikiLink("device"))
    // Nov 20 sync: "Longitudinal options" is part of Galaxy's new Developer page, which has no guide of its own
    assertNull(GalaxyMirror.wikiLink("driving_options"))
    assertNull(GalaxyMirror.wikiLink("developer"))
    assertNull(GalaxyMirror.wikiLink(""))
    assertNull(GalaxyMirror.wikiLink(null))
  }

  @Test fun theLinkIsAddedOnlyWithAGuide() {
    val col = LinearLayout(RuntimeEnvironment.getApplication())
    GalaxyMirror.guide(col, "device")
    assertEquals(0, col.childCount)
    GalaxyMirror.guide(col, "wheel")
    assertEquals(1, col.childCount)
    assertEquals(true, (col.getChildAt(0) as TextView).text.endsWith("Guide"))
  }
}
