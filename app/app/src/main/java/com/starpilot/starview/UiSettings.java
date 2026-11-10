package com.starpilot.starview;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* compiled from: UiSettings.kt */
public final class UiSettings {
    private static final Map<String, Boolean> DEFAULTS;
    private final SharedPreferences prefs;
    public static final Companion Companion = new Companion(null);
    private static final ConcurrentHashMap<String, Boolean> SHARED = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, String> STR = new ConcurrentHashMap<>();
    private static final List<Item> ITEMS = CollectionsKt.listOf((Object[]) new Item[]{new Item("model_ui", "Model overlay (path, lanes, edges)", true, "Road"), new Item("acceleration_path", "Acceleration-colored path", true, "Road"), new Item("rainbow_path", "Rainbow path", false, "Road"), new Item("path_edges", "Path edges", true, "Road"), new Item("lane_centering_cue", "Lane centering cue (blue lane line while the comma's lane centering steers)", true, "Road"), new Item("lane_lines", "Lane lines", true, "Road"), new Item("lane_change_lane", "Lane change: highlight the lane it goes into (Scene)", true, "Road"), new Item("car_memory", "Remembered cars: a car beside you the radar lost keeps moving (pale blue in the Scene); lane changes wait for it", true, "Road"), new Item("lane_memory_lines", "Remembered lane lines: the lanes beside you the camera saw before (Scene, faint dashes)", true, "Road"), new Item("path_pulse", "Path pulses when accelerating / braking (Scene)", true, "Road"), new Item("road_edges", "Road edges", true, "Road"), new Item("lead_marker", "Lead markers", true, "Road"), new Item("radar_tracks", "Surrounding traffic from radar (parked and passing cars)", true, "Road"), new Item("show_speed", "Current speed", true, "Widgets"), new Item("show_max", "MAX set speed", true, "Widgets"), new Item("speed_limit", "Speed limit sign", true, "Widgets"), new Item("vienna", "EU-style speed limit sign", false, "HUD"), new Item("wheel_button", "Steering wheel / experimental button", true, "Widgets"), new Item("egpu_status", "eGPU status icon (gray = loading, green = active)", true, "Widgets"), new Item("rotating_wheel", "Wheel icon rotates with steering", true, "HUD"), new Item("personality_button", "Following-distance button", true, "Widgets"), new Item("mode_text", "Driving mode (ACC - Chill, Always On Lateral, ...)", false, "Widgets"), new Item("clock", "Clock", false, "Widgets"), new Item("clock_24h", "Clock: 24-hour", false, "HUD"), new Item("dev_sidebar", "Developer sidebar (acceleration and what openpilot learned: delay, friction, steer ratio)", false, "Widgets"), new Item("side_views", "Side views from the driver camera (blinker / blind spot; set up on the comma in Galaxy's PiP)", true, "Widgets"), new Item("wheel_tint", "Wheel brake and acceleration colors", false, "HUD"), new Item("dm_icon", "Driver monitoring icon", true, "Widgets"), new Item("road_name", "Road name", true, "Widgets"), new Item("torque_bar", "Steering torque bar", true, "HUD"), new Item("alerts", "Alerts", true, "HUD"), new Item("alert_tint", "Alert screen tint (orange / red wash behind alerts)", true, "HUD"), new Item("aether_gauge", "Curve / stop / lead gauge", true, "Widgets"), new Item("nav_card", "Navigation card", true, "Widgets"), new Item("slow_card", "Why it's slowing (shows what openpilot brakes for; tap it for the history)", true, "Widgets"), new Item("lane_suggest", "Change lanes? - asks when the model or the route wants a lane change; Change = signal it (sound alert)", true, "Widgets"), new Item("lane_nudge_confirm", "Change lanes? - a light steering nudge toward that side also confirms", true, "Blinker"), new Item("exit_signals", "Automatic exit signals: takes the route's exits and forks by itself (signals ~8 s before; Cancel on the card)", true, "Blinker"), new Item("lane_pass", "Change lanes? - pass a slower car: asks to move left when the car ahead holds you 5+ mph under your set speed (above 30 mph; always waits for your nudge)", true, "Blinker"), new Item("lane_return", "Change lanes? - move back right after passing: asks once the passed car is behind you and the right lane ahead is clear (always waits for your nudge)", true, "Blinker"), new Item("lane_ends_ask", "Change lanes? - lane ends ahead: asks to merge away from a lane that ends (the road data's lane count drops ahead; always waits for your nudge)", false, "Blinker"), new Item("lane_auto", "Change lanes? - automatic: signals by itself after a 3 s countdown (No cancels)", false, "Blinker"), new Item("blinker_turn", "Check the street map for turns without a route (StarPilot's Turn Signal Guidance)", true, "Blinker"), new Item("turn_signals", "Automatic turn signals before route turns (with a destination; Cancel on the card)", false, "Blinker"), new Item("blink_card", "Blinker buttons - test (hold to signal through the relay box)", true, "Widgets"), new Item("lane_card", "Lane tracker - test (which lane you're in and how many; tap it for the history)", true, "Widgets"), new Item("nudge_hint", "Exit nudge prompt (when to push the wheel for a route exit / fork)", true, "Widgets"), new Item("gps_card", "GPS card (the comma's fix, accuracy, satellites and signal; tap it for GPS & sensors)", true, "Widgets"), new Item("weather", "Weather badge", true, "Widgets"), new Item("stopped_timer", "Stopped timer", true, "HUD"), new Item("compass", "Compass", false, "Widgets"), new Item("dev_metrics", "Device metrics strip (CPU / temp / RAM)", false, "Widgets"), new Item("status_border", "Status color border", true, "Effects"), new Item("signal_flashes", "Turn-signal edge flashes", true, "Effects"), new Item("blindspot_flashes", "Blind-spot edge flashes", true, "Effects"), new Item("csc_glow", "Curve speed: border glow and the CURVE label", true, "Effects"), new Item("scene_view", "Scene view", false, "Internal"), new Item("map_open", "Map open", false, "Internal"), new Item("battery_saver", "Battery saver", false, "Internal"), new Item("map_auto_open", "Open the map when a route starts", true, "Internal"), new Item("map_traffic", "Live traffic on every road", true, "Internal"), new Item("map_north_up", "North up", false, "Internal"), new Item("scene_cars", "Scene: show other cars, people and traffic lights", false, "Camera"), new Item("map_roads", "Street map from OpenStreetMap under the Scene (test: placed by GPS, orange outlines)", true, "Camera"), new Item("detector_test", "Object detection in the camera view (cars, people, bikes, lights)", true, "Camera"), new Item("detector_boxes", "Show the detection boxes on the video", true, "Camera"), new Item("wide_camera", "Use the wide camera in the camera view", false, "Camera"), new Item("offroad_home", "Home screen while the car is off (drive stats, alerts)", true, "Camera"), new Item("comma_screen_off", "Turn the comma's own screen off while the tablet is connected", true, "Camera"), new Item("stats", "Diagnostics panel", false, "Camera")});

    /* compiled from: UiSettings.kt */
    public static final class Item {
        private final boolean def;
        private final String group;
        private final String key;
        private final String title;

        public Item(String key, String title, boolean def, String group) {
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(title, "title");
            Intrinsics.checkNotNullParameter(group, "group");
            this.key = key;
            this.title = title;
            this.def = def;
            this.group = group;
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

    public UiSettings(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        SharedPreferences sharedPreferences = ctx.getSharedPreferences("starview_ui", 0);
        Intrinsics.checkNotNullExpressionValue(sharedPreferences, "getSharedPreferences(...)");
        this.prefs = sharedPreferences;
        if (!SHARED.isEmpty()) {
            return;
        }
        for (Item i : ITEMS) {
            SHARED.put(i.getKey(), Boolean.valueOf(this.prefs.getBoolean(i.getKey(), i.getDef())));
        }
        Map<String, ?> all = this.prefs.getAll();
        Intrinsics.checkNotNullExpressionValue(all, "getAll(...)");
        for (Map.Entry<String, ?> entry : all.entrySet()) {
            String k = entry.getKey();
            Object v = entry.getValue();
            if ((v instanceof Boolean) && !SHARED.containsKey(k)) {
                SHARED.put(k, v);
            }
        }
    }

    /* compiled from: UiSettings.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final List<Item> getITEMS() {
            return UiSettings.ITEMS;
        }
    }

    static {
        Iterable $this$associate$iv = ITEMS;
        int capacity$iv = RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault($this$associate$iv, 10)), 16);
        Map destination$iv$iv = new LinkedHashMap(capacity$iv);
        for (Object element$iv$iv : $this$associate$iv) {
            Item it = (Item) element$iv$iv;
            Pair pair = TuplesKt.to(it.getKey(), Boolean.valueOf(it.getDef()));
            destination$iv$iv.put(pair.getFirst(), pair.getSecond());
        }
        DEFAULTS = destination$iv$iv;
    }

    private final ConcurrentHashMap<String, Boolean> getCache() {
        return SHARED;
    }

    public final boolean get(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        Boolean bool = getCache().get(key);
        if (bool == null && (bool = DEFAULTS.get(key)) == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public final void set(String key, boolean v) {
        Intrinsics.checkNotNullParameter(key, "key");
        getCache().put(key, Boolean.valueOf(v));
        this.prefs.edit().putBoolean(key, v).apply();
    }

    public final String getStr(String key, String def) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(def, "def");
        String str = STR.get(key);
        if (str != null) {
            return str;
        }
        String it = this.prefs.getString(key, def);
        if (it != null) {
            STR.put(key, it);
        } else {
            it = null;
        }
        return it == null ? def : it;
    }

    public final void setStr(String key, String v) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(v, "v");
        STR.put(key, v);
        this.prefs.edit().putString(key, v).apply();
    }

    public final float getFloat(String key, float def) {
        Intrinsics.checkNotNullParameter(key, "key");
        return this.prefs.getFloat(key, def);
    }

    public final void setFloat(String key, float v) {
        Intrinsics.checkNotNullParameter(key, "key");
        this.prefs.edit().putFloat(key, v).apply();
    }
}
