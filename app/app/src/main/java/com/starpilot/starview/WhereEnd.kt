package com.starpilot.starview

import android.app.AlertDialog
import android.view.View
import android.widget.TextView
import android.widget.Toast

/**
 * 2.90.9 (owner, Nov 19: "a button where to for navigation that goes away once there is a destination, instead of it
 * going away it should turn into end"): the driving screen's "Where to?" button stays while a route is on and reads
 * "End"; a tap asks "End the route to <place>?" (End / Keep), like the map's own End, so a bump can't end a trip.
 * Back to "Where to?" when the route ends. Called every screen tick (MainActivity.hybWhereTick, patched into 2.84.1's
 * tick right after it set the button's visibility - which it hid with a route on).
 */
object WhereEnd {
  const val WHERE = "⌕  Where to?"
  const val END = "✕  End"

  /** [show]: connected, map closed, no editor open (2.84.1's rule without "no route"). [route]: a route is on.
   *  [where]: MainActivity.openDestinations (private in 2.84.1's MainActivity, so MainActivity hands it over). */
  @JvmStatic
  fun tick(a: MainActivity, b: TextView, data: DataClient, show: Boolean, route: Boolean, where: Runnable) {
    val v = if (show) View.VISIBLE else View.GONE
    if (b.visibility != v) b.visibility = v
    val state = if (route) END else WHERE
    if (b.tag == state) return
    b.tag = state
    b.text = state
    b.setOnClickListener { if (b.tag == END) confirm(a, data) else where.run() }
  }

  @JvmStatic
  fun confirm(a: MainActivity, data: DataClient) {
    val name = Nav.INSTANCE.destination?.optString("name")?.takeIf { it.isNotEmpty() } ?: "the destination"
    AlertDialog.Builder(a).setMessage("End the route to $name?")
      .setPositiveButton("End") { _, _ ->
        Nav.INSTANCE.cancel(data) { e: String? ->
          if (e != null) a.runOnUiThread { Toast.makeText(a, e, Toast.LENGTH_SHORT).show() }
        }
      }
      .setNegativeButton("Keep", null).show()
  }
}
