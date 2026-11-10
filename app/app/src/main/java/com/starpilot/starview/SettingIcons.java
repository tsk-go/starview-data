package com.starpilot.starview;

import java.util.ArrayList;
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

public final class SettingIcons {
   public static final SettingIcons INSTANCE = new SettingIcons();
   private static final Map<String, Pair<String, String>> PAGES;
   private static final List<Pair<Regex, String>> RULES;

   static {
      Pair var21 = TuplesKt.to("profiles", TuplesKt.to("planner", "speed"));
      Pair var25 = TuplesKt.to("conditional", TuplesKt.to("conditional", "speed"));
      Pair var10 = TuplesKt.to("conditional/cem", TuplesKt.to("conditional", "speed"));
      Pair var28 = TuplesKt.to("conditional/ccm", TuplesKt.to("coast", "speed"));
      Pair var12 = TuplesKt.to("curve", TuplesKt.to("curve", "speed"));
      Pair var0 = TuplesKt.to("slc", TuplesKt.to("slc", "speed"));
      Pair var13 = TuplesKt.to("speed", TuplesKt.to("planner", "speed"));
      Pair var26 = TuplesKt.to("lane", TuplesKt.to("lane", "steer"));
      Pair var1 = TuplesKt.to("lane_change", TuplesKt.to("lanechange", "steer"));
      Pair var14 = TuplesKt.to("torque", TuplesKt.to("torque", "steer"));
      Pair var27 = TuplesKt.to("aol", TuplesKt.to("aol", "steer"));
      Pair var23 = TuplesKt.to("steering", TuplesKt.to("wheel", "steer"));
      Pair var17 = TuplesKt.to("traffic", TuplesKt.to("traffic", "person"));
      Pair var15 = TuplesKt.to("personalities", TuplesKt.to("standard", "person"));
      Pair var24 = TuplesKt.to("aggressive", TuplesKt.to("aggressive", "person"));
      Pair var6 = TuplesKt.to("standard", TuplesKt.to("standard", "person"));
      Pair var3 = TuplesKt.to("relaxed", TuplesKt.to("relaxed", "person"));
      Pair var5 = TuplesKt.to("wheel", TuplesKt.to("buttons", "display"));
      Pair var11 = TuplesKt.to("display", TuplesKt.to("brightness", "display"));
      Pair var4 = TuplesKt.to("appearance", TuplesKt.to("eye", "display"));
      Pair var22 = TuplesKt.to("quickselect", TuplesKt.to("star", "display"));
      Pair var20 = TuplesKt.to("sounds", TuplesKt.to("speaker", "sound"));
      Pair var16 = TuplesKt.to("pip", TuplesKt.to("mirror", "cam"));
      Pair var7 = TuplesKt.to("sentry", TuplesKt.to("sentry", "cam"));
      Pair var18 = TuplesKt.to("vasm", TuplesKt.to("radar", "cam"));
      Pair var2 = TuplesKt.to("cameras", TuplesKt.to("camera", "cam"));
      Pair var9 = TuplesKt.to("vehicle", TuplesKt.to("car", "device"));
      Pair var19 = TuplesKt.to("data", TuplesKt.to("upload", "device"));
      Pair var8 = TuplesKt.to("developer", TuplesKt.to("code", "device"));
      Pair var29 = TuplesKt.to("more", TuplesKt.to("new", "device"));
      PAGES = MapsKt.mapOf(
         new Pair[]{
            var21,
            var25,
            var10,
            var28,
            var12,
            var0,
            var13,
            var26,
            var1,
            var14,
            var27,
            var23,
            var17,
            var15,
            var24,
            var6,
            var3,
            var5,
            var11,
            var4,
            var22,
            var20,
            var16,
            var7,
            var18,
            var2,
            var9,
            var19,
            var8,
            var29
         }
      );
      Pair var63 = TuplesKt.to("lane departure", "lane");
      Pair var138 = TuplesKt.to("screen tint", "palette");
      Pair var86 = TuplesKt.to("reset|restore default", "reset");
      Pair var144 = TuplesKt.to("preset", "layers");
      Pair var57 = TuplesKt.to("jerk", "jerk");
      Pair var118 = TuplesKt.to("personality", "standard");
      Pair var44 = TuplesKt.to("wheel brake and acceleration", "pedal");
      var10 = TuplesKt.to("faster takeoff|takeoff", "takeoff");
      Pair var90 = TuplesKt.to("launch", "rocket");
      Pair var75 = TuplesKt.to("brake pause", "brake");
      Pair var81 = TuplesKt.to("deceleration|braking", "decel");
      Pair var70 = TuplesKt.to("acceleration", "accel");
      Pair var41 = TuplesKt.to("force stop|stop distance|stopped lead", "stop");
      Pair var93 = TuplesKt.to("stop light", "light");
      Pair var122 = TuplesKt.to("model stop time", "timeout");
      var7 = TuplesKt.to("follow gap|change gap|close lane-change gap", "gap");
      Pair var71 = TuplesKt.to("following a lead", "lead");
      var24 = TuplesKt.to("follow|higher-speed values", "distance");
      Pair var114 = TuplesKt.to("short press|^hold$", "buttons");
      Pair var67 = TuplesKt.to("speed limit controller", "slc");
      Pair var56 = TuplesKt.to("speed limit sign|limit sign", "sign");
      Pair var47 = TuplesKt.to("offset|mph and above", "plusminus");
      var21 = TuplesKt.to("source", "layers");
      Pair var110 = TuplesKt.to("confirmation", "check");
      var16 = TuplesKt.to("previous accepted", "history");
      var20 = TuplesKt.to("curves with lead", "conditional");
      Pair var37 = TuplesKt.to("curve", "curve");
      Pair var53 = TuplesKt.to("following a lead|lead indicator|lead info|approaching lead|lead vehicle|slower lead|with a lead|speed with lead", "lead");
      Pair var34 = TuplesKt.to("border", "border");
      Pair var66 = TuplesKt.to("signal", "blinker");
      Pair var132 = TuplesKt.to("lane width|adjacent lane width|lane offset", "width");
      Pair var129 = TuplesKt.to("automatic lane change", "aol");
      var18 = TuplesKt.to("lane change|lane-change", "lanechange");
      Pair var105 = TuplesKt.to("lane centering", "lane");
      var9 = TuplesKt.to("path preference", "map");
      Pair var82 = TuplesKt.to("always on lateral", "aol");
      Pair var61 = TuplesKt.to("pause steering|resume delay", "pause");
      var22 = TuplesKt.to("turn assist", "turn");
      Pair var112 = TuplesKt.to("torque", "torque");
      Pair var79 = TuplesKt.to("learning progress", "plots");
      Pair var51 = TuplesKt.to("saved learning|saved cornering", "data");
      Pair var139 = TuplesKt.to("brightness", "brightness");
      Pair var72 = TuplesKt.to("screen timeout|interaction timeout", "timeout");
      Pair var115 = TuplesKt.to("turn off comma screen", "screenoff");
      Pair var83 = TuplesKt.to("widget", "widgets");
      Pair var39 = TuplesKt.to("sidebar", "sidebar");
      var2 = TuplesKt.to("custom display", "slider");
      Pair var123 = TuplesKt.to("driver camera", "car");
      Pair var38 = TuplesKt.to("camera view", "camera");
      Pair var74 = TuplesKt.to("sound pack", "pack");
      Pair var133 = TuplesKt.to("distracted", "eye");
      Pair var76 = TuplesKt.to("refused", "stop");
      Pair var134 = TuplesKt.to("chime|disengagement alert", "chime");
      var3 = TuplesKt.to("warning", "bell");
      Pair var49 = TuplesKt.to("prompt", "vibrate");
      var28 = TuplesKt.to("crop|region", "crop");
      Pair var140 = TuplesKt.to("blind spot", "mirror");
      Pair var73 = TuplesKt.to("camera availability|camera", "camera");
      Pair var141 = TuplesKt.to("motion monitoring", "sentry");
      Pair var30 = TuplesKt.to("spot monitoring", "radar");
      Pair var103 = TuplesKt.to("sensitivity|strength", "slider");
      Pair var78 = TuplesKt.to("confidence", "check");
      Pair var127 = TuplesKt.to("smoothing", "wave");
      Pair var58 = TuplesKt.to("persistence|delay", "timeout");
      Pair var31 = TuplesKt.to("upload", "upload");
      Pair var100 = TuplesKt.to("voacc", "lead");
      Pair var65 = TuplesKt.to("open road", "map");
      Pair var125 = TuplesKt.to("remember manual", "bookmark");
      Pair var104 = TuplesKt.to("saved driving mode|experimental conditions|chill conditions", "conditional");
      Pair var46 = TuplesKt.to("speed threshold|speed margin|minimum .*speed|speed below|pause .* below|below", "gauge2");
      Pair var48 = TuplesKt.to("show ", "eye");
      Pair var32 = TuplesKt.to("longitudinal planner", "planner");
      Pair var137 = TuplesKt.to("model", "chip");
      Pair var142 = TuplesKt.to("vehicle", "car");
      Pair var87 = TuplesKt.to("language", "globe");
      Pair var131 = TuplesKt.to("theme", "palette");
      Pair var91 = TuplesKt.to("road texture", "brightness");
      var29 = TuplesKt.to("detector mask", "crop");
      var11 = TuplesKt.to("downloading", "update");
      Pair var120 = TuplesKt.to("on this tablet|storage", "data");
      Pair var116 = TuplesKt.to("reconnect", "reset");
      Pair var68 = TuplesKt.to("receiver", "satellite");
      Pair var80 = TuplesKt.to("correction|rtk", "satellite");
      var19 = TuplesKt.to("mountpoint", "pin");
      Pair var92 = TuplesKt.to("open the map|live traffic|street map|map open", "map");
      Pair var43 = TuplesKt.to("north up|compass", "gps");
      Pair var102 = TuplesKt.to("map on the right", "sidebar");
      Pair var136 = TuplesKt.to("gps", "gps");
      var0 = TuplesKt.to("braking log|slowing", "decel");
      Pair var124 = TuplesKt.to("steering nudge|nudge", "wheel");
      Pair var99 = TuplesKt.to("exit", "turn");
      var5 = TuplesKt.to("pass a slower|move back right|change lanes", "lanechange");
      Pair var88 = TuplesKt.to("turn signal|blinker", "blinker");
      Pair var94 = TuplesKt.to("voice engine|new voice|amy|recorded", "mic");
      Pair var121 = TuplesKt.to("voice|volume|speaker", "speaker");
      Pair var113 = TuplesKt.to("status", "info");
      var15 = TuplesKt.to("views", "camera");
      Pair var111 = TuplesKt.to("starview", "star");
      Pair var126 = TuplesKt.to("enable openpilot", "power");
      Pair var84 = TuplesKt.to("safe mode", "sentry");
      Pair var107 = TuplesKt.to("accelerator pedal|pedal", "pedal");
      Pair var98 = TuplesKt.to("lane departure", "lane");
      Pair var50 = TuplesKt.to("driver monitoring", "eye");
      var1 = TuplesKt.to("microphone", "mic");
      var14 = TuplesKt.to("metric", "gauge2");
      Pair var54 = TuplesKt.to("pair this tablet|password|ssh key", "key");
      Pair var101 = TuplesKt.to("pair", "link");
      var12 = TuplesKt.to("regulatory", "info");
      var25 = TuplesKt.to("tethering", "hotspot");
      Pair var95 = TuplesKt.to("roaming|apn|cellular", "cell");
      Pair var35 = TuplesKt.to("hidden network|wi-fi|wifi", "wifi");
      var17 = TuplesKt.to("adb|debug", "code");
      var4 = TuplesKt.to("ssh", "terminal");
      Pair var108 = TuplesKt.to("joystick", "buttons");
      var23 = TuplesKt.to("longitudinal control", "planner");
      Pair var52 = TuplesKt.to("update", "update");
      Pair var55 = TuplesKt.to("error log", "console");
      Pair var45 = TuplesKt.to("branch|version", "history");
      var27 = TuplesKt.to("path|overlay", "lane");
      Pair var62 = TuplesKt.to("lane line|road edge|lane tracker|adjacent lane", "lane");
      Pair var97 = TuplesKt.to("shading", "mirror");
      Pair var117 = TuplesKt.to("surrounding traffic|remembered car|other cars", "car");
      Pair var128 = TuplesKt.to("stopping point|red light|traffic light", "light");
      Pair var135 = TuplesKt.to("current speed|max set speed", "gauge2");
      Pair var85 = TuplesKt.to("speed limit", "sign");
      Pair var33 = TuplesKt.to("egpu", "chip");
      Pair var59 = TuplesKt.to("wheel icon|steering wheel", "wheel");
      Pair var36 = TuplesKt.to("following-distance", "distance");
      Pair var109 = TuplesKt.to("badge", "star");
      Pair var119 = TuplesKt.to("road name", "map");
      Pair var60 = TuplesKt.to("torque bar", "torque");
      Pair var40 = TuplesKt.to("alert", "bell");
      Pair var69 = TuplesKt.to("tint", "palette");
      Pair var143 = TuplesKt.to("gauge", "gauge2");
      Pair var64 = TuplesKt.to("navigation", "turn");
      var6 = TuplesKt.to("weather", "brightness");
      var26 = TuplesKt.to("timer", "timeout");
      Pair var42 = TuplesKt.to("metrics|diagnostic", "plots");
      Pair var96 = TuplesKt.to("edge flash|glow", "border");
      var8 = TuplesKt.to("scene", "camera");
      Pair var77 = TuplesKt.to("detection", "eye");
      Pair var130 = TuplesKt.to("wide camera", "camera");
      var13 = TuplesKt.to("home screen", "info");
      Pair var106 = TuplesKt.to("own screen", "screenoff");
      Pair var89 = TuplesKt.to("map", "map");
      Iterable var149 = CollectionsKt.listOf(
         new Pair[]{
            var63,
            var138,
            var86,
            var144,
            var57,
            var118,
            var44,
            var10,
            var90,
            var75,
            var81,
            var70,
            var41,
            var93,
            var122,
            var7,
            var71,
            var24,
            var114,
            var67,
            var56,
            var47,
            var21,
            var110,
            var16,
            var20,
            var37,
            var53,
            var34,
            var66,
            var132,
            var129,
            var18,
            var105,
            var9,
            var82,
            var61,
            var22,
            var112,
            var79,
            var51,
            var139,
            var72,
            var115,
            var83,
            var39,
            var2,
            var123,
            var38,
            var74,
            var133,
            var76,
            var134,
            var3,
            var49,
            var28,
            var140,
            var73,
            var141,
            var30,
            var103,
            var78,
            var127,
            var58,
            var31,
            var100,
            var65,
            var125,
            var104,
            var46,
            var48,
            var32,
            var137,
            var142,
            var87,
            var131,
            var91,
            var29,
            var11,
            var120,
            var116,
            var68,
            var80,
            var19,
            var92,
            var43,
            var102,
            var136,
            var0,
            var124,
            var99,
            var5,
            var88,
            var94,
            var121,
            var113,
            var15,
            var111,
            var126,
            var84,
            var107,
            var98,
            var50,
            var1,
            var14,
            var54,
            var101,
            var12,
            var25,
            var95,
            var35,
            var17,
            var4,
            var108,
            var23,
            var52,
            var55,
            var45,
            var27,
            var62,
            var97,
            var117,
            var128,
            var135,
            var85,
            var33,
            var59,
            var36,
            var109,
            var119,
            var60,
            var40,
            var69,
            var143,
            var64,
            var6,
            var26,
            var42,
            var96,
            var8,
            var77,
            var130,
            var13,
            var106,
            var89
         }
      );
      var var146 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var149, 10));

      for (Pair var154 : var149) {
         String var150 = (String)var154.component1();
         String var155 = (String)var154.component2();
         var146.add(TuplesKt.to(new Regex(var150, RegexOption.IGNORE_CASE), var155));
      }

      List var147 = var146;
      RULES = var147;
   }

   private SettingIcons() {
   }

   // $VF: synthetic method
   public static SettingIcons.Icon forRow$default(SettingIcons var0, String var1, String var2, String var3, GalaxyRow.Control var4, int var5, Object var6) {
      if ((var5 & 4) != 0) {
         var3 = "";
      }

      if ((var5 & 8) != 0) {
         var4 = null;
      }

      return var0.forRow(var1, var2, var3, var4);
   }

   public final SettingIcons.Icon forLabel(String var1, String var2) {
      Intrinsics.checkNotNullParameter(var1, "label");
      Intrinsics.checkNotNullParameter(var2, "group");
      Iterator var4 = RULES.iterator();

      while (true) {
         if (var4.hasNext()) {
            Object var3 = var4.next();
            if (!((Regex)((Pair)var3).getFirst()).containsMatchIn(var1)) {
               continue;
            }

            var5 = var3;
            break;
         }

         var5 = null;
         break;
      }

      Pair var6 = (Pair)var5;
      SettingIcons.Icon var7;
      if (var6 != null) {
         var7 = new SettingIcons.Icon((String)var6.getSecond(), var2, false, 4, null);
      } else {
         var7 = new SettingIcons.Icon("info", var2, false);
      }

      return var7;
   }

   public final SettingIcons.Icon forPage(String var1) {
      Intrinsics.checkNotNullParameter(var1, "page");
      Pair var3 = PAGES.get(var1);
      Pair var2 = var3;
      if (var3 == null) {
         var2 = PAGES.get(StringsKt.substringBefore$default(var1, '/', null, 2, null));
      }

      SettingIcons.Icon var4;
      if (var2 != null) {
         var4 = new SettingIcons.Icon((String)var2.getFirst(), (String)var2.getSecond(), false, 4, null);
      } else {
         var4 = new SettingIcons.Icon("new", "device", false);
      }

      return var4;
   }

   public final SettingIcons.Icon forRow(String var1, String var2, String var3, GalaxyRow.Control var4) {
      Intrinsics.checkNotNullParameter(var1, "page");
      Intrinsics.checkNotNullParameter(var2, "label");
      Intrinsics.checkNotNullParameter(var3, "linkPage");
      boolean var5;
      if (var3.length() > 0) {
         var5 = 1;
      } else {
         var5 = 0;
      }

      if (var5) {
         Pair var9 = PAGES.get(var3);
         if (var9 != null) {
            return new SettingIcons.Icon((String)var9.getFirst(), (String)var9.getSecond(), false, 4, null);
         }
      }

      String var6 = this.group(var1);
      Iterator var7 = RULES.iterator();

      do {
         if (!var7.hasNext()) {
            var10 = null;
            break;
         }

         var10 = var7.next();
      } while (!((Regex)((Pair)var10).getFirst()).containsMatchIn(var2));

      Pair var11 = (Pair)var10;
      if (var11 != null) {
         return new SettingIcons.Icon((String)var11.getSecond(), var6, false, 4, null);
      } else if (new Regex("^(enable|use|allow) ", RegexOption.IGNORE_CASE).containsMatchIn(var2) && PAGES.containsKey(var1)) {
         return new SettingIcons.Icon(MapsKt.getValue(PAGES, var1).getFirst(), var6, false, 4, null);
      } else {
         if (var4 == null) {
            var5 = -1;
         } else {
            var5 = SettingIcons.WhenMappings.$EnumSwitchMapping$0[var4.ordinal()];
         }

         switch (var5) {
            case 1:
               var1 = "toggle";
               break;
            case 2:
               var1 = "slider";
               break;
            case 3:
               var1 = "layers";
               break;
            case 4:
               var1 = "check";
               break;
            default:
               var1 = "info";
         }

         return new SettingIcons.Icon(var1, var6, false);
      }
   }

   public final String group(String var1) {
      Intrinsics.checkNotNullParameter(var1, "page");
      Pair var2 = PAGES.get(var1);
      if (var2 != null) {
         String var3 = (String)var2.getSecond();
         if (var3 != null) {
            return var3;
         }
      }

      Pair var4 = PAGES.get(StringsKt.substringBefore$default(var1, '/', null, 2, null));
      String var5;
      if (var4 != null) {
         var5 = (String)var4.getSecond();
      } else {
         var5 = "device";
      }

      return var5;
   }

   public static final class Icon {
      private final String group;
      private final boolean known;
      private final String name;

      public Icon(String var1, String var2, boolean var3) {
         Intrinsics.checkNotNullParameter(var1, "name");
         Intrinsics.checkNotNullParameter(var2, "group");
         super();
         this.name = var1;
         this.group = var2;
         this.known = var3;
      }

      // $VF: synthetic method
      public Icon(String var1, String var2, boolean var3, int var4, DefaultConstructorMarker var5) {
         if ((var4 & 4) != 0) {
            var3 = true;
         }

         this(var1, var2, var3);
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

   // $VF: synthetic class
   public final class WhenMappings {
      public static final int[] $EnumSwitchMapping$0;

      static {
         int[] var0 = new int[GalaxyRow.Control.values().length];

         try {
            var0[GalaxyRow.Control.SWITCH.ordinal()] = 1;
         } catch (NoSuchFieldError var5) {
         }

         try {
            var0[GalaxyRow.Control.SLIDER.ordinal()] = 2;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[GalaxyRow.Control.SELECT.ordinal()] = 3;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[GalaxyRow.Control.ACTION.ordinal()] = 4;
         } catch (NoSuchFieldError var2) {
         }

         $EnumSwitchMapping$0 = var0;
      }
   }
}
