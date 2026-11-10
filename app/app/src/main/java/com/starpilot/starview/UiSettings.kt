package com.starpilot.starview

import android.content.Context
import android.content.SharedPreferences

/**
 * Tablet-local display settings. These replace the comma's (mici-flavored) UI toggles for everything that is
 * purely how the tablet draws. Behavior settings (units, experimental mode, personality, calibration) still
 * come from the comma.
 */
class UiSettings(ctx: Context) {
  class Item(val key: String, val title: String, val def: Boolean, val group: String)

  companion object {
    private val SHARED = java.util.concurrent.ConcurrentHashMap<String, Boolean>()
    private val STR = java.util.concurrent.ConcurrentHashMap<String, String>()

    val ITEMS = listOf(
      Item("model_ui", "Model overlay (path, lanes, edges)", true, "Road"),
      Item("acceleration_path", "Acceleration-colored path", true, "Road"),
      Item("rainbow_path", "Rainbow path", false, "Road"),
      Item("path_edges", "Path edges", true, "Road"),
      Item("lane_centering_cue", "Lane centering cue (blue lane line while the comma's lane centering steers)", true, "Road"),
      Item("lane_lines", "Lane lines", true, "Road"),
      Item("lane_change_lane", "Lane change: highlight the lane it goes into (Scene)", true, "Road"),
      Item("car_memory", "Remembered cars: a car beside you the radar lost keeps moving (pale blue in the Scene); lane changes wait for it", true, "Road"),
      Item("lane_memory_lines", "Remembered lane lines: the lanes beside you the camera saw before (Scene, faint dashes)", true, "Road"),
      Item("path_pulse", "Path pulses when accelerating / braking (Scene)", true, "Road"),
      Item("road_edges", "Road edges", true, "Road"),
      Item("lead_marker", "Lead markers", true, "Road"),
      Item("radar_tracks", "Surrounding traffic from radar (parked and passing cars)", true, "Road"),
      Item("show_speed", "Current speed", true, "Widgets"),
      Item("show_max", "MAX set speed", true, "Widgets"),
      Item("speed_limit", "Speed limit sign", true, "Widgets"),
      Item("vienna", "EU-style speed limit sign", false, "HUD"),
      Item("wheel_button", "Steering wheel / experimental button", true, "Widgets"),
      Item("egpu_status", "eGPU status icon (gray = loading, green = active)", true, "Widgets"),
      Item("rotating_wheel", "Wheel icon rotates with steering", true, "HUD"),
      Item("personality_button", "Following-distance button", true, "Widgets"),
      Item("mode_text", "Driving mode (ACC - Chill, Always On Lateral, ...)", false, "Widgets"),
      Item("clock", "Clock", false, "Widgets"),
      Item("clock_24h", "Clock: 24-hour", false, "HUD"),
      Item("dev_sidebar", "Developer sidebar (acceleration and what openpilot learned: delay, friction, steer ratio)", false, "Widgets"),
      Item("side_views", "Side views from the driver camera (blinker / blind spot; set up on the comma in Galaxy's PiP)", true, "Widgets"),
      Item("wheel_tint", "Wheel brake and acceleration colors", false, "HUD"),
      Item("dm_icon", "Driver monitoring icon", true, "Widgets"),
      Item("road_name", "Road name", true, "Widgets"),
      Item("torque_bar", "Steering torque bar", true, "HUD"),
      Item("alerts", "Alerts", true, "HUD"),
      Item("alert_tint", "Alert screen tint (orange / red wash behind alerts)", true, "HUD"),
      Item("aether_gauge", "Curve / stop / lead gauge", true, "Widgets"),
      Item("nav_card", "Navigation card", true, "Widgets"),
      Item("nav_map", "Navigation see-through map (street lines while the car is on, and the route while one is set)", true, "Widgets"),
      Item("slow_card", "Why it's slowing (shows what openpilot brakes for; tap it for the history)", true, "Widgets"),
      Item("lane_suggest", "Change lanes? - asks when the model or the route wants a lane change; Change = signal it (sound alert)", true, "Widgets"),
      Item("lane_nudge_confirm", "Change lanes? - a light steering nudge toward that side also confirms", true, "Blinker"),
      Item("exit_signals", "Automatic exit signals: takes the route's exits and forks by itself (signals ~8 s before; Cancel on the card)", true, "Blinker"),
      Item("lane_pass", "Change lanes? - pass a slower car: asks to move left when the car ahead holds you 5+ mph under your set speed (above 30 mph; always waits for your nudge)", true, "Blinker"),
      Item("lane_return", "Change lanes? - move back right after passing: asks once the passed car is behind you and the right lane ahead is clear (always waits for your nudge)", true, "Blinker"),
      Item("lane_ends_ask", "Change lanes? - lane ends ahead: asks to merge away from a lane that ends (the road data's lane count drops ahead; always waits for your nudge)", false, "Blinker"),
      Item("lane_auto", "Change lanes? - automatic: signals by itself after a 3 s countdown (No cancels)", false, "Blinker"),
      Item("blinker_turn", "Check the street map for turns without a route (StarPilot's Turn Signal Guidance)", true, "Blinker"),
      Item("turn_signals", "Automatic turn signals before route turns (with a destination; Cancel on the card)", false, "Blinker"),
      Item("blink_card", "Blinker buttons - test (hold to signal through the relay box)", true, "Widgets"),
      Item("lane_card", "Lane tracker - test (which lane you're in and how many; tap it for the history)", true, "Widgets"),
      Item("nudge_hint", "Exit nudge prompt (when to push the wheel for a route exit / fork)", true, "Widgets"),
      Item("gps_card", "GPS card (the comma's fix, accuracy, satellites and signal; tap it for GPS & sensors)", true, "Widgets"),
      Item("weather", "Weather badge", true, "Widgets"),
      Item("stopped_timer", "Stopped timer", true, "HUD"),
      Item("compass", "Compass", false, "Widgets"),
      Item("dev_metrics", "Device metrics strip (CPU / temp / RAM)", false, "Widgets"),
      Item("status_border", "Status color border", true, "Effects"),
      Item("signal_flashes", "Turn-signal edge flashes", true, "Effects"),
      Item("blindspot_flashes", "Blind-spot edge flashes", true, "Effects"),
      Item("csc_glow", "Curve speed: border glow and the CURVE label", true, "Effects"),
      Item("scene_view", "Scene view", false, "Internal"),
      Item("map_open", "Map open", false, "Internal"),
      Item("battery_saver", "Battery saver", false, "Internal"),
      Item("map_auto_open", "Open the map when a route starts", true, "Internal"),
      Item("map_traffic", "Live traffic on every road", true, "Internal"),
      Item("map_north_up", "North up", false, "Internal"),
      Item("scene_cars", "Scene: show other cars, people and traffic lights", false, "Camera"),
      Item("map_roads", "Street map from OpenStreetMap under the Scene (test: placed by GPS, orange outlines)", true, "Camera"),
      Item("detector_test", "Object detection in the camera view (cars, people, bikes, lights)", true, "Camera"),
      Item("detector_boxes", "Show the detection boxes on the video", true, "Camera"),
      Item("wide_camera", "Use the wide camera in the camera view", false, "Camera"),
      Item("offroad_home", "Home screen while the car is off (drive stats, alerts)", true, "Camera"),
      Item("comma_screen_off", "Turn the comma's own screen off while the tablet is connected", true, "Camera"),
      Item("stats", "Diagnostics panel", false, "Camera")
    )
    private val DEFAULTS = ITEMS.associate { it.key to it.def }
  }

  private val prefs: SharedPreferences = ctx.getSharedPreferences("starview_ui", Context.MODE_PRIVATE)
  // one shared cache for every instance (main screen + settings screen), so a toggle applies immediately everywhere
  private val cache get() = SHARED

  init {
    if (SHARED.isEmpty()) {
      for (i in ITEMS) SHARED[i.key] = prefs.getBoolean(i.key, i.def)
      // switches that live outside ITEMS (kept by other screens) load too
      for ((k, v) in prefs.all) if (v is Boolean && !SHARED.containsKey(k)) SHARED[k] = v
    }
  }

  fun get(key: String): Boolean = cache[key] ?: DEFAULTS[key] ?: false
  fun set(key: String, v: Boolean) { cache[key] = v; prefs.edit().putBoolean(key, v).apply() }
  // string choices (theme_mode: auto|day|night), shared like the booleans
  fun getStr(key: String, def: String): String = STR[key] ?: prefs.getString(key, def)?.also { STR[key] = it } ?: def
  fun setStr(key: String, v: String) { STR[key] = v; prefs.edit().putString(key, v).apply() }
  fun getFloat(key: String, def: Float): Float = prefs.getFloat(key, def)
  fun setFloat(key: String, v: Float) { prefs.edit().putFloat(key, v).apply() }
}
