package com.starpilot.starview;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

public final class UiSettings {
   public static final UiSettings.Companion Companion = new UiSettings.Companion(null);
   private static final Map<String, Boolean> DEFAULTS;
   private static final List<UiSettings.Item> ITEMS;
   private static final ConcurrentHashMap<String, Boolean> SHARED = new ConcurrentHashMap<>();
   private static final ConcurrentHashMap<String, String> STR = new ConcurrentHashMap<>();
   private final SharedPreferences prefs;

   static {
      UiSettings.Item var40 = new UiSettings.Item("model_ui", "Model overlay (path, lanes, edges)", true, "Road");
      UiSettings.Item var35 = new UiSettings.Item("acceleration_path", "Acceleration-colored path", true, "Road");
      UiSettings.Item var28 = new UiSettings.Item("rainbow_path", "Rainbow path", false, "Road");
      UiSettings.Item var22 = new UiSettings.Item("path_edges", "Path edges", true, "Road");
      UiSettings.Item var33 = new UiSettings.Item(
         "lane_centering_cue", "Lane centering cue (blue lane line while the comma's lane centering steers)", true, "Road"
      );
      UiSettings.Item var25 = new UiSettings.Item("lane_lines", "Lane lines", true, "Road");
      UiSettings.Item var43 = new UiSettings.Item("lane_change_lane", "Lane change: highlight the lane it goes into (Scene)", true, "Road");
      UiSettings.Item var20 = new UiSettings.Item(
         "car_memory", "Remembered cars: a car beside you the radar lost keeps moving (pale blue in the Scene); lane changes wait for it", true, "Road"
      );
      UiSettings.Item var29 = new UiSettings.Item(
         "lane_memory_lines", "Remembered lane lines: the lanes beside you the camera saw before (Scene, faint dashes)", true, "Road"
      );
      UiSettings.Item var17 = new UiSettings.Item("path_pulse", "Path pulses when accelerating / braking (Scene)", true, "Road");
      UiSettings.Item var64 = new UiSettings.Item("road_edges", "Road edges", true, "Road");
      UiSettings.Item var7 = new UiSettings.Item("lead_marker", "Lead markers", true, "Road");
      UiSettings.Item var50 = new UiSettings.Item("radar_tracks", "Surrounding traffic from radar (parked and passing cars)", true, "Road");
      UiSettings.Item var26 = new UiSettings.Item("show_speed", "Current speed", true, "Widgets");
      UiSettings.Item var0 = new UiSettings.Item("show_max", "MAX set speed", true, "Widgets");
      UiSettings.Item var67 = new UiSettings.Item("speed_limit", "Speed limit sign", true, "Widgets");
      UiSettings.Item var47 = new UiSettings.Item("vienna", "EU-style speed limit sign", false, "HUD");
      UiSettings.Item var36 = new UiSettings.Item("wheel_button", "Steering wheel / experimental button", true, "Widgets");
      UiSettings.Item var48 = new UiSettings.Item("egpu_status", "eGPU status icon (gray = loading, green = active)", true, "Widgets");
      UiSettings.Item var21 = new UiSettings.Item("rotating_wheel", "Wheel icon rotates with steering", true, "HUD");
      UiSettings.Item var68 = new UiSettings.Item("personality_button", "Following-distance button", true, "Widgets");
      UiSettings.Item var8 = new UiSettings.Item("mode_text", "Driving mode (ACC - Chill, Always On Lateral, ...)", false, "Widgets");
      UiSettings.Item var18 = new UiSettings.Item("clock", "Clock", false, "Widgets");
      UiSettings.Item var30 = new UiSettings.Item("clock_24h", "Clock: 24-hour", false, "HUD");
      UiSettings.Item var19 = new UiSettings.Item(
         "dev_sidebar", "Developer sidebar (acceleration and what openpilot learned: delay, friction, steer ratio)", false, "Widgets"
      );
      UiSettings.Item var62 = new UiSettings.Item(
         "side_views", "Side views from the driver camera (blinker / blind spot; set up on the comma in Galaxy's PiP)", true, "Widgets"
      );
      UiSettings.Item var66 = new UiSettings.Item("wheel_tint", "Wheel brake and acceleration colors", false, "HUD");
      UiSettings.Item var65 = new UiSettings.Item("dm_icon", "Driver monitoring icon", true, "Widgets");
      UiSettings.Item var49 = new UiSettings.Item("road_name", "Road name", true, "Widgets");
      UiSettings.Item var15 = new UiSettings.Item("torque_bar", "Steering torque bar", true, "HUD");
      UiSettings.Item var9 = new UiSettings.Item("alerts", "Alerts", true, "HUD");
      UiSettings.Item var41 = new UiSettings.Item("alert_tint", "Alert screen tint (orange / red wash behind alerts)", true, "HUD");
      UiSettings.Item var58 = new UiSettings.Item("aether_gauge", "Curve / stop / lead gauge", true, "Widgets");
      UiSettings.Item var4 = new UiSettings.Item("nav_card", "Navigation card", true, "Widgets");
      UiSettings.Item var69 = new UiSettings.Item("slow_card", "Why it's slowing (shows what openpilot brakes for; tap it for the history)", true, "Widgets");
      UiSettings.Item var45 = new UiSettings.Item(
         "lane_suggest", "Change lanes? - asks when the model or the route wants a lane change; Change = signal it (sound alert)", true, "Widgets"
      );
      UiSettings.Item var27 = new UiSettings.Item(
         "lane_nudge_confirm", "Change lanes? - a light steering nudge toward that side also confirms", true, "Blinker"
      );
      UiSettings.Item var24 = new UiSettings.Item(
         "exit_signals", "Automatic exit signals: takes the route's exits and forks by itself (signals ~8 s before; Cancel on the card)", true, "Blinker"
      );
      UiSettings.Item var59 = new UiSettings.Item(
         "lane_pass",
         "Change lanes? - pass a slower car: asks to move left when the car ahead holds you 5+ mph under your set speed (above 30 mph; always waits for your nudge)",
         true,
         "Blinker"
      );
      UiSettings.Item var12 = new UiSettings.Item(
         "lane_return",
         "Change lanes? - move back right after passing: asks once the passed car is behind you and the right lane ahead is clear (always waits for your nudge)",
         true,
         "Blinker"
      );
      UiSettings.Item var5 = new UiSettings.Item(
         "lane_ends_ask",
         "Change lanes? - lane ends ahead: asks to merge away from a lane that ends (the road data's lane count drops ahead; always waits for your nudge)",
         false,
         "Blinker"
      );
      UiSettings.Item var23 = new UiSettings.Item(
         "lane_auto", "Change lanes? - automatic: signals by itself after a 3 s countdown (No cancels)", false, "Blinker"
      );
      UiSettings.Item var60 = new UiSettings.Item(
         "blinker_turn", "Check the street map for turns without a route (StarPilot's Turn Signal Guidance)", true, "Blinker"
      );
      UiSettings.Item var42 = new UiSettings.Item(
         "turn_signals", "Automatic turn signals before route turns (with a destination; Cancel on the card)", false, "Blinker"
      );
      UiSettings.Item var31 = new UiSettings.Item("blink_card", "Blinker buttons - test (hold to signal through the relay box)", true, "Widgets");
      UiSettings.Item var16 = new UiSettings.Item(
         "lane_card", "Lane tracker - test (which lane you're in and how many; tap it for the history)", true, "Widgets"
      );
      UiSettings.Item var38 = new UiSettings.Item("nudge_hint", "Exit nudge prompt (when to push the wheel for a route exit / fork)", true, "Widgets");
      UiSettings.Item var13 = new UiSettings.Item(
         "gps_card", "GPS card (the comma's fix, accuracy, satellites and signal; tap it for GPS & sensors)", true, "Widgets"
      );
      UiSettings.Item var51 = new UiSettings.Item("weather", "Weather badge", true, "Widgets");
      UiSettings.Item var46 = new UiSettings.Item("stopped_timer", "Stopped timer", true, "HUD");
      UiSettings.Item var54 = new UiSettings.Item("compass", "Compass", false, "Widgets");
      UiSettings.Item var14 = new UiSettings.Item("dev_metrics", "Device metrics strip (CPU / temp / RAM)", false, "Widgets");
      UiSettings.Item var61 = new UiSettings.Item("status_border", "Status color border", true, "Effects");
      UiSettings.Item var34 = new UiSettings.Item("signal_flashes", "Turn-signal edge flashes", true, "Effects");
      UiSettings.Item var52 = new UiSettings.Item("blindspot_flashes", "Blind-spot edge flashes", true, "Effects");
      UiSettings.Item var55 = new UiSettings.Item("csc_glow", "Curve speed: border glow and the CURVE label", true, "Effects");
      UiSettings.Item var1 = new UiSettings.Item("scene_view", "Scene view", false, "Internal");
      UiSettings.Item var56 = new UiSettings.Item("map_open", "Map open", false, "Internal");
      UiSettings.Item var6 = new UiSettings.Item("battery_saver", "Battery saver", false, "Internal");
      UiSettings.Item var10 = new UiSettings.Item("map_auto_open", "Open the map when a route starts", true, "Internal");
      UiSettings.Item var57 = new UiSettings.Item("map_traffic", "Live traffic on every road", true, "Internal");
      UiSettings.Item var3 = new UiSettings.Item("map_north_up", "North up", false, "Internal");
      UiSettings.Item var2 = new UiSettings.Item("scene_cars", "Scene: show other cars, people and traffic lights", false, "Camera");
      UiSettings.Item var32 = new UiSettings.Item(
         "map_roads", "Street map from OpenStreetMap under the Scene (test: placed by GPS, orange outlines)", true, "Camera"
      );
      UiSettings.Item var63 = new UiSettings.Item("detector_test", "Object detection in the camera view (cars, people, bikes, lights)", true, "Camera");
      UiSettings.Item var53 = new UiSettings.Item("detector_boxes", "Show the detection boxes on the video", true, "Camera");
      UiSettings.Item var37 = new UiSettings.Item("wide_camera", "Use the wide camera in the camera view", false, "Camera");
      UiSettings.Item var39 = new UiSettings.Item("offroad_home", "Home screen while the car is off (drive stats, alerts)", true, "Camera");
      UiSettings.Item var11 = new UiSettings.Item("comma_screen_off", "Turn the comma's own screen off while the tablet is connected", true, "Camera");
      UiSettings.Item var44 = new UiSettings.Item("stats", "Diagnostics panel", false, "Camera");
      ITEMS = CollectionsKt.listOf(
         new UiSettings.Item[]{
            var40,
            var35,
            var28,
            var22,
            var33,
            var25,
            var43,
            var20,
            var29,
            var17,
            var64,
            var7,
            var50,
            var26,
            var0,
            var67,
            var47,
            var36,
            var48,
            var21,
            var68,
            var8,
            var18,
            var30,
            var19,
            var62,
            var66,
            var65,
            var49,
            var15,
            var9,
            var41,
            var58,
            var4,
            var69,
            var45,
            var27,
            var24,
            var59,
            var12,
            var5,
            var23,
            var60,
            var42,
            var31,
            var16,
            var38,
            var13,
            var51,
            var46,
            var54,
            var14,
            var61,
            var34,
            var52,
            var55,
            var1,
            var56,
            var6,
            var10,
            var57,
            var3,
            var2,
            var32,
            var63,
            var53,
            var37,
            var39,
            var11,
            var44
         }
      );
      Iterable var71 = ITEMS;
      Map var70 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(var71, 10)), 16));

      for (UiSettings.Item var73 : var71) {
         Pair var74 = TuplesKt.to(var73.getKey(), var73.getDef());
         var70.put(var74.getFirst(), var74.getSecond());
      }

      DEFAULTS = var70;
   }

   public UiSettings(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      super();
      SharedPreferences var4 = var1.getSharedPreferences("starview_ui", 0);
      Intrinsics.checkNotNullExpressionValue(var4, "getSharedPreferences(...)");
      this.prefs = var4;
      if (SHARED.isEmpty()) {
         for (UiSettings.Item var5 : ITEMS) {
            SHARED.put(var5.getKey(), this.prefs.getBoolean(var5.getKey(), var5.getDef()));
         }

         Map var6 = this.prefs.getAll();
         Intrinsics.checkNotNullExpressionValue(var6, "getAll(...)");

         for (Entry var3 : var6.entrySet()) {
            String var8 = (String)var3.getKey();
            Object var9 = var3.getValue();
            if (var9 instanceof Boolean && !SHARED.containsKey(var8)) {
               SHARED.put(var8, (Boolean)var9);
            }
         }
      }
   }

   private final ConcurrentHashMap<String, Boolean> getCache() {
      return SHARED;
   }

   public final boolean get(String var1) {
      Intrinsics.checkNotNullParameter(var1, "key");
      Boolean var3 = this.getCache().get(var1);
      Boolean var4;
      if (var3 != null) {
         var4 = var3;
      } else {
         var4 = DEFAULTS.get(var1);
         if (var4 == null) {
            return false;
         }
      }

      return var4;
   }

   public final float getFloat(String var1, float var2) {
      Intrinsics.checkNotNullParameter(var1, "key");
      return this.prefs.getFloat(var1, var2);
   }

   public final String getStr(String var1, String var2) {
      Intrinsics.checkNotNullParameter(var1, "key");
      Intrinsics.checkNotNullParameter(var2, "def");
      String var4 = STR.get(var1);
      String var3 = var4;
      if (var4 == null) {
         var3 = this.prefs.getString(var1, var2);
         if (var3 != null) {
            STR.put(var1, var3);
            var1 = var3;
         } else {
            var1 = null;
         }

         var3 = var1;
         if (var1 == null) {
            var3 = var2;
         }
      }

      return var3;
   }

   public final void set(String var1, boolean var2) {
      Intrinsics.checkNotNullParameter(var1, "key");
      this.getCache().put(var1, var2);
      this.prefs.edit().putBoolean(var1, var2).apply();
   }

   public final void setFloat(String var1, float var2) {
      Intrinsics.checkNotNullParameter(var1, "key");
      this.prefs.edit().putFloat(var1, var2).apply();
   }

   public final void setStr(String var1, String var2) {
      Intrinsics.checkNotNullParameter(var1, "key");
      Intrinsics.checkNotNullParameter(var2, "v");
      STR.put(var1, var2);
      this.prefs.edit().putString(var1, var2).apply();
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      public final List<UiSettings.Item> getITEMS() {
         return UiSettings.ITEMS;
      }
   }

   public static final class Item {
      private final boolean def;
      private final String group;
      private final String key;
      private final String title;

      public Item(String var1, String var2, boolean var3, String var4) {
         Intrinsics.checkNotNullParameter(var1, "key");
         Intrinsics.checkNotNullParameter(var2, "title");
         Intrinsics.checkNotNullParameter(var4, "group");
         super();
         this.key = var1;
         this.title = var2;
         this.def = var3;
         this.group = var4;
      }

      public final boolean getDef() {
         return this.def;
      }

      public final String getGroup() {
         return this.group;
      }

      public final String getKey() {
         return this.key;
      }

      public final String getTitle() {
         return this.title;
      }
   }
}
