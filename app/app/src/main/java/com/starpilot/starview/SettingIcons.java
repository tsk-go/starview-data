package com.starpilot.starview;

import com.starpilot.starview.GalaxyRow;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.RegexOption;
import kotlin.text.StringsKt;

/* compiled from: SettingIcons.kt */
public final class SettingIcons {
    public static final SettingIcons INSTANCE = new SettingIcons();
    private static final Map<String, Pair<String, String>> PAGES = MapsKt.mapOf(TuplesKt.to("profiles", TuplesKt.to("planner", "speed")), TuplesKt.to("conditional", TuplesKt.to("conditional", "speed")), TuplesKt.to("conditional/cem", TuplesKt.to("conditional", "speed")), TuplesKt.to("conditional/ccm", TuplesKt.to("coast", "speed")), TuplesKt.to("curve", TuplesKt.to("curve", "speed")), TuplesKt.to("slc", TuplesKt.to("slc", "speed")), TuplesKt.to("speed", TuplesKt.to("planner", "speed")), TuplesKt.to("lane", TuplesKt.to("lane", "steer")), TuplesKt.to("lane_change", TuplesKt.to("lanechange", "steer")), TuplesKt.to("torque", TuplesKt.to("torque", "steer")), TuplesKt.to("aol", TuplesKt.to("aol", "steer")), TuplesKt.to("steering", TuplesKt.to("wheel", "steer")), TuplesKt.to("traffic", TuplesKt.to("traffic", "person")), TuplesKt.to("personalities", TuplesKt.to("standard", "person")), TuplesKt.to("aggressive", TuplesKt.to("aggressive", "person")), TuplesKt.to("standard", TuplesKt.to("standard", "person")), TuplesKt.to("relaxed", TuplesKt.to("relaxed", "person")), TuplesKt.to("wheel", TuplesKt.to("buttons", "display")), TuplesKt.to("display", TuplesKt.to("brightness", "display")), TuplesKt.to("appearance", TuplesKt.to("eye", "display")), TuplesKt.to("quickselect", TuplesKt.to("star", "display")), TuplesKt.to("sounds", TuplesKt.to("speaker", "sound")), TuplesKt.to("pip", TuplesKt.to("mirror", "cam")), TuplesKt.to("sentry", TuplesKt.to("sentry", "cam")), TuplesKt.to("vasm", TuplesKt.to("radar", "cam")), TuplesKt.to("cameras", TuplesKt.to("camera", "cam")), TuplesKt.to("vehicle", TuplesKt.to("car", "device")), TuplesKt.to("data", TuplesKt.to("upload", "device")), TuplesKt.to("developer", TuplesKt.to("code", "device")), TuplesKt.to("more", TuplesKt.to("new", "device")));
    private static final List<Pair<Regex, String>> RULES;

    /* compiled from: SettingIcons.kt */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[GalaxyRow.Control.values().length];
            try {
                iArr[GalaxyRow.Control.SWITCH.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[GalaxyRow.Control.SLIDER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[GalaxyRow.Control.SELECT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[GalaxyRow.Control.ACTION.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* compiled from: SettingIcons.kt */
    public static final class Icon {
        private final String group;
        private final boolean known;
        private final String name;

        public Icon(String name, String group, boolean known) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(group, "group");
            this.name = name;
            this.group = group;
            this.known = known;
        }

        public /* synthetic */ Icon(String str, String str2, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, str2, (i & 4) != 0 ? true : z);
        }

        public final String getGroup() {
            return this.group;
        }

        public final boolean getKnown() {
            return this.known;
        }

        public final String getName() {
            return this.name;
        }
    }

    private SettingIcons() {
    }

    static {
        Iterable $this$map$iv = CollectionsKt.listOf(new Pair[]{TuplesKt.to("lane departure", "lane"), TuplesKt.to("screen tint", "palette"), TuplesKt.to("reset|restore default", "reset"), TuplesKt.to("preset", "layers"), TuplesKt.to("jerk", "jerk"), TuplesKt.to("personality", "standard"), TuplesKt.to("wheel brake and acceleration", "pedal"), TuplesKt.to("faster takeoff|takeoff", "takeoff"), TuplesKt.to("launch", "rocket"), TuplesKt.to("brake pause", "brake"), TuplesKt.to("deceleration|braking", "decel"), TuplesKt.to("acceleration", "accel"), TuplesKt.to("force stop|stop distance|stopped lead", "stop"), TuplesKt.to("stop light", "light"), TuplesKt.to("model stop time", "timeout"), TuplesKt.to("follow gap|change gap|close lane-change gap", "gap"), TuplesKt.to("following a lead", "lead"), TuplesKt.to("follow|higher-speed values", "distance"), TuplesKt.to("short press|^hold$", "buttons"), TuplesKt.to("speed limit controller", "slc"), TuplesKt.to("speed limit sign|limit sign", "sign"), TuplesKt.to("offset|mph and above", "plusminus"), TuplesKt.to("source", "layers"), TuplesKt.to("confirmation", "check"), TuplesKt.to("previous accepted", "history"), TuplesKt.to("curves with lead", "conditional"), TuplesKt.to("curve", "curve"), TuplesKt.to("following a lead|lead indicator|lead info|approaching lead|lead vehicle|slower lead|with a lead|speed with lead", "lead"), TuplesKt.to("border", "border"), TuplesKt.to("signal", "blinker"), TuplesKt.to("lane width|adjacent lane width|lane offset", "width"), TuplesKt.to("automatic lane change", "aol"), TuplesKt.to("lane change|lane-change", "lanechange"), TuplesKt.to("lane centering", "lane"), TuplesKt.to("path preference", "map"), TuplesKt.to("always on lateral", "aol"), TuplesKt.to("pause steering|resume delay", "pause"), TuplesKt.to("turn assist", "turn"), TuplesKt.to("torque", "torque"), TuplesKt.to("learning progress", "plots"), TuplesKt.to("saved learning|saved cornering", "data"), TuplesKt.to("brightness", "brightness"), TuplesKt.to("screen timeout|interaction timeout", "timeout"), TuplesKt.to("turn off comma screen", "screenoff"), TuplesKt.to("widget", "widgets"), TuplesKt.to("sidebar", "sidebar"), TuplesKt.to("custom display", "slider"), TuplesKt.to("driver camera", "car"), TuplesKt.to("camera view", "camera"), TuplesKt.to("sound pack", "pack"), TuplesKt.to("distracted", "eye"), TuplesKt.to("refused", "stop"), TuplesKt.to("chime|disengagement alert", "chime"), TuplesKt.to("warning", "bell"), TuplesKt.to("prompt", "vibrate"), TuplesKt.to("crop|region", "crop"), TuplesKt.to("blind spot", "mirror"), TuplesKt.to("camera availability|camera", "camera"), TuplesKt.to("motion monitoring", "sentry"), TuplesKt.to("spot monitoring", "radar"), TuplesKt.to("sensitivity|strength", "slider"), TuplesKt.to("confidence", "check"), TuplesKt.to("smoothing", "wave"), TuplesKt.to("persistence|delay", "timeout"), TuplesKt.to("upload", "upload"), TuplesKt.to("voacc", "lead"), TuplesKt.to("open road", "map"), TuplesKt.to("remember manual", "bookmark"), TuplesKt.to("saved driving mode|experimental conditions|chill conditions", "conditional"), TuplesKt.to("speed threshold|speed margin|minimum .*speed|speed below|pause .* below|below", "gauge2"), TuplesKt.to("show ", "eye"), TuplesKt.to("longitudinal planner", "planner"), TuplesKt.to("model", "chip"), TuplesKt.to("vehicle", "car"), TuplesKt.to("language", "globe"), TuplesKt.to("theme", "palette"), TuplesKt.to("road texture", "brightness"), TuplesKt.to("detector mask", "crop"), TuplesKt.to("downloading", "update"), TuplesKt.to("on this tablet|storage", "data"), TuplesKt.to("reconnect", "reset"), TuplesKt.to("receiver", "satellite"), TuplesKt.to("correction|rtk", "satellite"), TuplesKt.to("mountpoint", "pin"), TuplesKt.to("open the map|live traffic|street map|map open", "map"), TuplesKt.to("north up|compass", "gps"), TuplesKt.to("map on the right", "sidebar"), TuplesKt.to("gps", "gps"), TuplesKt.to("braking log|slowing", "decel"), TuplesKt.to("steering nudge|nudge", "wheel"), TuplesKt.to("exit", "turn"), TuplesKt.to("pass a slower|move back right|change lanes", "lanechange"), TuplesKt.to("turn signal|blinker", "blinker"), TuplesKt.to("voice engine|new voice|amy|recorded", "mic"), TuplesKt.to("voice|volume|speaker", "speaker"), TuplesKt.to("status", "info"), TuplesKt.to("views", "camera"), TuplesKt.to("starview", "star"), TuplesKt.to("enable openpilot", "power"), TuplesKt.to("safe mode", "sentry"), TuplesKt.to("accelerator pedal|pedal", "pedal"), TuplesKt.to("lane departure", "lane"), TuplesKt.to("driver monitoring", "eye"), TuplesKt.to("microphone", "mic"), TuplesKt.to("metric", "gauge2"), TuplesKt.to("pair this tablet|password|ssh key", "key"), TuplesKt.to("pair", "link"), TuplesKt.to("regulatory", "info"), TuplesKt.to("tethering", "hotspot"), TuplesKt.to("roaming|apn|cellular", "cell"), TuplesKt.to("hidden network|wi-fi|wifi", "wifi"), TuplesKt.to("adb|debug", "code"), TuplesKt.to("ssh", "terminal"), TuplesKt.to("joystick", "buttons"), TuplesKt.to("longitudinal control", "planner"), TuplesKt.to("update", "update"), TuplesKt.to("error log", "console"), TuplesKt.to("branch|version", "history"), TuplesKt.to("path|overlay", "lane"), TuplesKt.to("lane line|road edge|lane tracker|adjacent lane", "lane"), TuplesKt.to("shading", "mirror"), TuplesKt.to("surrounding traffic|remembered car|other cars", "car"), TuplesKt.to("stopping point|red light|traffic light", "light"), TuplesKt.to("current speed|max set speed", "gauge2"), TuplesKt.to("speed limit", "sign"), TuplesKt.to("egpu", "chip"), TuplesKt.to("wheel icon|steering wheel", "wheel"), TuplesKt.to("following-distance", "distance"), TuplesKt.to("badge", "star"), TuplesKt.to("road name", "map"), TuplesKt.to("torque bar", "torque"), TuplesKt.to("alert", "bell"), TuplesKt.to("tint", "palette"), TuplesKt.to("gauge", "gauge2"), TuplesKt.to("navigation", "turn"), TuplesKt.to("weather", "brightness"), TuplesKt.to("timer", "timeout"), TuplesKt.to("metrics|diagnostic", "plots"), TuplesKt.to("edge flash|glow", "border"), TuplesKt.to("scene", "camera"), TuplesKt.to("detection", "eye"), TuplesKt.to("wide camera", "camera"), TuplesKt.to("home screen", "info"), TuplesKt.to("own screen", "screenoff"), TuplesKt.to("map", "map")});
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            Pair pair = (Pair) item$iv$iv;
            String k = (String) pair.component1();
            String v = (String) pair.component2();
            destination$iv$iv.add(TuplesKt.to(new Regex(k, RegexOption.IGNORE_CASE), v));
        }
        RULES = (List) destination$iv$iv;
    }

    public final Icon forLabel(String label, String group) {
        Object element$iv;
        Intrinsics.checkNotNullParameter(label, "label");
        Intrinsics.checkNotNullParameter(group, "group");
        Iterable $this$firstOrNull$iv = RULES;
        Iterator it = $this$firstOrNull$iv.iterator();
        while (true) {
            if (!it.hasNext()) {
                element$iv = null;
                break;
            }
            element$iv = it.next();
            if (((Regex) ((Pair) element$iv).getFirst()).containsMatchIn(label)) {
                break;
            }
        }
        Pair it2 = (Pair) element$iv;
        if (it2 == null) {
            return new Icon("info", group, false);
        }
        return new Icon((String) it2.getSecond(), group, false, 4, null);
    }

    public final String group(String page) {
        String second;
        Intrinsics.checkNotNullParameter(page, "page");
        Pair<String, String> pair = PAGES.get(page);
        if (pair != null && (second = pair.getSecond()) != null) {
            return second;
        }
        Pair<String, String> pair2 = PAGES.get(StringsKt.substringBefore(page, '/', page));
        return pair2 != null ? pair2.getSecond() : "device";
    }

    public final Icon forPage(String page) {
        Intrinsics.checkNotNullParameter(page, "page");
        Pair it = PAGES.get(page);
        if (it == null) {
            it = PAGES.get(StringsKt.substringBefore(page, '/', page));
        }
        if (it == null) {
            return new Icon("new", "device", false);
        }
        return new Icon(((String) it.getFirst()), ((String) it.getSecond()), false, 4, null);
    }

    public static /* synthetic */ Icon forRow$default(SettingIcons settingIcons, String str, String str2, String str3, GalaxyRow.Control control, int i, Object obj) {
        if ((i & 4) != 0) {
            str3 = "";
        }
        if ((i & 8) != 0) {
            control = null;
        }
        return settingIcons.forRow(str, str2, str3, control);
    }

    public final Icon forRow(String page, String label, String linkPage, GalaxyRow.Control control) {
        Object element$iv;
        String str;
        Pair it;
        Intrinsics.checkNotNullParameter(page, "page");
        Intrinsics.checkNotNullParameter(label, "label");
        Intrinsics.checkNotNullParameter(linkPage, "linkPage");
        if ((linkPage.length() > 0) && (it = PAGES.get(linkPage)) != null) {
            return new Icon(((String) it.getFirst()), ((String) it.getSecond()), false, 4, null);
        }
        String g = group(page);
        Iterable $this$firstOrNull$iv = RULES;
        Iterator it2 = $this$firstOrNull$iv.iterator();
        while (true) {
            if (!it2.hasNext()) {
                element$iv = null;
                break;
            }
            element$iv = it2.next();
            if (((Regex) ((Pair) element$iv).getFirst()).containsMatchIn(label)) {
                break;
            }
        }
        Pair it3 = (Pair) element$iv;
        if (it3 != null) {
            return new Icon((String) it3.getSecond(), g, false, 4, null);
        }
        if (new Regex("^(enable|use|allow) ", RegexOption.IGNORE_CASE).containsMatchIn(label) && PAGES.containsKey(page)) {
            return new Icon((String) ((Pair) MapsKt.getValue(PAGES, page)).getFirst(), g, false, 4, null);
        }
        switch (control == null ? -1 : WhenMappings.$EnumSwitchMapping$0[control.ordinal()]) {
            case 1:
                str = "toggle";
                break;
            case 2:
                str = "slider";
                break;
            case 3:
                str = "layers";
                break;
            case 4:
                str = "check";
                break;
            default:
                str = "info";
                break;
        }
        return new Icon(str, g, false);
    }
}
