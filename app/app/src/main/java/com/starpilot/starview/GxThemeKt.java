package com.starpilot.starview;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;

public final class GxThemeKt {
   private static final Map<String, String> GXTHEME_BUCKET = MapsKt.mapOf(
      new Pair[]{
         TuplesKt.to("colors", "DownloadableColors"),
         TuplesKt.to("distance_icons", "DownloadableDistanceIcons"),
         TuplesKt.to("icons", "DownloadableIcons"),
         TuplesKt.to("sounds", "DownloadableSounds"),
         TuplesKt.to("steering_wheel", "DownloadableWheels"),
         TuplesKt.to("turn_signals", "DownloadableSignals")
      }
   );
   private static final LinkedHashMap<String, String> GXTHEME_COLOR_LABELS;
   private static final List<String> GXTHEME_COMPS = CollectionsKt.listOf(
      new String[]{"colors", "distance_icons", "icons", "sounds", "steering_wheel", "turn_signals"}
   );
   private static final Map<String, String> GXTHEME_COMP_FLAG = MapsKt.mapOf(
      new Pair[]{
         TuplesKt.to("colors", "hasColors"),
         TuplesKt.to("distance_icons", "hasDistanceIcons"),
         TuplesKt.to("icons", "hasIcons"),
         TuplesKt.to("sounds", "hasSounds"),
         TuplesKt.to("steering_wheel", "hasSteeringWheel"),
         TuplesKt.to("turn_signals", "hasTurnSignals")
      }
   );
   private static final Map<String, String> GXTHEME_COMP_LABELS = MapsKt.mapOf(
      new Pair[]{
         TuplesKt.to("colors", "Colors"),
         TuplesKt.to("distance_icons", "Distance Icons"),
         TuplesKt.to("icons", "Icons"),
         TuplesKt.to("sounds", "Sounds"),
         TuplesKt.to("steering_wheel", "Steering Wheel"),
         TuplesKt.to("turn_signals", "Turn Signals")
      }
   );
   private static final List<String> GXTHEME_DISTANCE = CollectionsKt.listOf(new String[]{"traffic", "aggressive", "standard", "relaxed"});
   private static final int GXTHEME_MAX_BYTES = 5242880;
   private static final Map<String, String> GXTHEME_PARAM = MapsKt.mapOf(
      new Pair[]{
         TuplesKt.to("colors", "ColorScheme"),
         TuplesKt.to("distance_icons", "DistanceIconPack"),
         TuplesKt.to("icons", "IconPack"),
         TuplesKt.to("sounds", "SoundPack"),
         TuplesKt.to("turn_signals", "SignalAnimation"),
         TuplesKt.to("steering_wheel", "WheelIcon")
      }
   );
   private static final String GXTHEME_SEQ = "__sequential__";
   private static final List<String> GXTHEME_SOUNDS = CollectionsKt.listOf(new String[]{"disengage", "engage", "prompt", "startup"});
   private static final Map<String, String> GXTHEME_SOUND_LABELS = MapsKt.mapOf(
      new Pair[]{TuplesKt.to("disengage", "Disengage"), TuplesKt.to("engage", "Engage"), TuplesKt.to("prompt", "Prompt"), TuplesKt.to("startup", "Startup")}
   );

   static {
      Pair var0 = TuplesKt.to("LaneLines", "Lane Lines");
      Pair var6 = TuplesKt.to("LeadMarker", "Lead Marker");
      Pair var3 = TuplesKt.to("Path", "Path");
      Pair var1 = TuplesKt.to("PathEdge", "Path Edge");
      Pair var5 = TuplesKt.to("Sidebar1", "Sidebar Top");
      Pair var4 = TuplesKt.to("Sidebar2", "Sidebar Middle");
      Pair var2 = TuplesKt.to("Sidebar3", "Sidebar Bottom");
      GXTHEME_COLOR_LABELS = MapsKt.linkedMapOf(new Pair[]{var0, var6, var3, var1, var5, var4, var2});
   }

   // $VF: synthetic method
   public static final Map access$getGXTHEME_BUCKET$p() {
      return GXTHEME_BUCKET;
   }

   // $VF: synthetic method
   public static final LinkedHashMap access$getGXTHEME_COLOR_LABELS$p() {
      return GXTHEME_COLOR_LABELS;
   }

   // $VF: synthetic method
   public static final List access$getGXTHEME_COMPS$p() {
      return GXTHEME_COMPS;
   }

   // $VF: synthetic method
   public static final Map access$getGXTHEME_COMP_FLAG$p() {
      return GXTHEME_COMP_FLAG;
   }

   // $VF: synthetic method
   public static final Map access$getGXTHEME_COMP_LABELS$p() {
      return GXTHEME_COMP_LABELS;
   }

   // $VF: synthetic method
   public static final List access$getGXTHEME_DISTANCE$p() {
      return GXTHEME_DISTANCE;
   }

   // $VF: synthetic method
   public static final Map access$getGXTHEME_PARAM$p() {
      return GXTHEME_PARAM;
   }

   // $VF: synthetic method
   public static final List access$getGXTHEME_SOUNDS$p() {
      return GXTHEME_SOUNDS;
   }

   // $VF: synthetic method
   public static final Map access$getGXTHEME_SOUND_LABELS$p() {
      return GXTHEME_SOUND_LABELS;
   }
}
