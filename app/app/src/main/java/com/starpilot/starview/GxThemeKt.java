package com.starpilot.starview;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;

/* compiled from: GxTheme.kt */
public final class GxThemeKt {
    private static final int GXTHEME_MAX_BYTES = 5242880;
    private static final String GXTHEME_SEQ = "__sequential__";
    static final LinkedHashMap<String, String> GXTHEME_COLOR_LABELS = MapsKt.linkedMapOf(TuplesKt.to("LaneLines", "Lane Lines"), TuplesKt.to("LeadMarker", "Lead Marker"), TuplesKt.to("Path", "Path"), TuplesKt.to("PathEdge", "Path Edge"), TuplesKt.to("Sidebar1", "Sidebar Top"), TuplesKt.to("Sidebar2", "Sidebar Middle"), TuplesKt.to("Sidebar3", "Sidebar Bottom"));
    static final List<String> GXTHEME_DISTANCE = CollectionsKt.listOf(new String[]{"traffic", "aggressive", "standard", "relaxed"});
    static final List<String> GXTHEME_SOUNDS = CollectionsKt.listOf(new String[]{"disengage", "engage", "prompt", "startup"});
    static final Map<String, String> GXTHEME_SOUND_LABELS = MapsKt.mapOf(TuplesKt.to("disengage", "Disengage"), TuplesKt.to("engage", "Engage"), TuplesKt.to("prompt", "Prompt"), TuplesKt.to("startup", "Startup"));
    static final List<String> GXTHEME_COMPS = CollectionsKt.listOf(new String[]{"colors", "distance_icons", "icons", "sounds", "steering_wheel", "turn_signals"});
    static final Map<String, String> GXTHEME_COMP_LABELS = MapsKt.mapOf(TuplesKt.to("colors", "Colors"), TuplesKt.to("distance_icons", "Distance Icons"), TuplesKt.to("icons", "Icons"), TuplesKt.to("sounds", "Sounds"), TuplesKt.to("steering_wheel", "Steering Wheel"), TuplesKt.to("turn_signals", "Turn Signals"));
    static final Map<String, String> GXTHEME_COMP_FLAG = MapsKt.mapOf(TuplesKt.to("colors", "hasColors"), TuplesKt.to("distance_icons", "hasDistanceIcons"), TuplesKt.to("icons", "hasIcons"), TuplesKt.to("sounds", "hasSounds"), TuplesKt.to("steering_wheel", "hasSteeringWheel"), TuplesKt.to("turn_signals", "hasTurnSignals"));
    static final Map<String, String> GXTHEME_PARAM = MapsKt.mapOf(TuplesKt.to("colors", "ColorScheme"), TuplesKt.to("distance_icons", "DistanceIconPack"), TuplesKt.to("icons", "IconPack"), TuplesKt.to("sounds", "SoundPack"), TuplesKt.to("turn_signals", "SignalAnimation"), TuplesKt.to("steering_wheel", "WheelIcon"));
    static final Map<String, String> GXTHEME_BUCKET = MapsKt.mapOf(TuplesKt.to("colors", "DownloadableColors"), TuplesKt.to("distance_icons", "DownloadableDistanceIcons"), TuplesKt.to("icons", "DownloadableIcons"), TuplesKt.to("sounds", "DownloadableSounds"), TuplesKt.to("steering_wheel", "DownloadableWheels"), TuplesKt.to("turn_signals", "DownloadableSignals"));
}
