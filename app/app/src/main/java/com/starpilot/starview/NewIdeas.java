package com.starpilot.starview;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings.Global;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;

public final class NewIdeas {
   private static final List<NewIdeas.Idea> ALL;
   private static final Set<String> CAMERA = SetsKt.setOf(
      new String[]{
         "ghosts",
         "shadows",
         "wheels",
         "lead_blinkers",
         "dooring",
         "green_countdown",
         "hybrid",
         "day_view",
         "model_disagree",
         "school_bus",
         "rain_camera",
         "parking",
         "actuated_lights",
         "double_parked",
         "left_gap"
      }
   );
   private static final Map<String, String> FIRST;
   public static final NewIdeas INSTANCE = new NewIdeas();

   // $VF: synthetic method
   public static void $r8$lambda$IH6N_lcdjNF82irEvjaaorR6gC4(Context var0) {
      importNightModel$lambda$4(var0);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$Sq_kYYIm1QgJOUWaIKTTcK0BYEo(File var0) {
      return importNightModel$lambda$4$lambda$2(var0);
   }

   static {
      Pair var7 = TuplesKt.to("shadows", "First version: warns only (no slowing), sunny days at corners; a trained model later.");
      Pair var0 = TuplesKt.to("wheels", "First version: the camera's view of cars beside you moving sideways toward your lane (not the wheels themselves yet).");
      Pair var10 = TuplesKt.to(
         "lane_memory",
         "Shifts up to 1 ft, only while Lane Centering is steering (two lines or one); with no lines at all the comma doesn't steer by it. Needs RTK fixed."
      );
      Pair var16 = TuplesKt.to("crowd_line", "Shifts up to 1 ft toward the car ahead's line, only while Lane Centering is steering.");
      Pair var4 = TuplesKt.to(
         "teach", "Follows your taught path up to 1 ft inside the lane, at your taught speed (never faster than your set speed). Needs RTK fixed."
      );
      Pair var14 = TuplesKt.to("carsick", "First version: a comfort score per drive (from the driver's head) - it doesn't change the steering yet.");
      Pair var15 = TuplesKt.to("model_disagree", "Saves a lane picture whenever the lane model and the lane card disagree (for the Fix screen and training).");
      Pair var9 = TuplesKt.to(
         "night_school",
         "Train on your PC with ml/lane_count/night_school.py; the tablet picks up the newest model from Download/StarView/models when a drive starts."
      );
      Pair var13 = TuplesKt.to(
         "eyes", "Eases off when you watch one side for over a second; where you look when stopped (the tablet, the radio) is learned and ignored."
      );
      Pair var1 = TuplesKt.to("dooring", "Someone next to a parked car's street side: shifts up to 1 ft away (Lane Centering on) and eases off.");
      Pair var11 = TuplesKt.to("sirens", "Needs the microphone (asked when you turn it on). Warns and adds room; it doesn't pull over by itself.");
      Pair var3 = TuplesKt.to(
         "school_bus",
         "First version: colour and flashing lights from the camera. It can't stop the car by itself (the comma never goes under 10 mph for advice): YOU stop."
      );
      Pair var6 = TuplesKt.to("rain_camera", "First version: counts wiper sweeps across the picture; may miss slow intermittent wipers.");
      Pair var12 = TuplesKt.to("horn", "Needs the microphone. It can't tell which side the honk came from.");
      Pair var8 = TuplesKt.to("parking", "Spots seen in the last 20 minutes, near a destination set on the map.");
      Pair var5 = TuplesKt.to("speed_cams", "Only the cameras someone put on OpenStreetMap (New York doesn't publish its list).");
      Pair var2 = TuplesKt.to("left_gap", "Shows the time only - you decide when to go.");
      FIRST = MapsKt.mapOf(new Pair[]{var7, var0, var10, var16, var4, var14, var15, var9, var13, var1, var11, var3, var6, var12, var8, var5, var2});
      NewIdeas.Idea var31 = new NewIdeas.Idea(
         "speed_bumps",
         "Speed bumps",
         "Bumps from the map (traffic calming) and every bump the car felt before (RTK + motion sensors, to the inch): slows down smoothly before each one instead of hitting it, and back up after.",
         "New York specials"
      );
      NewIdeas.Idea var36 = new NewIdeas.Idea(
         "nyc_cutins",
         "Highway cut-in guard",
         "Watches the cars beside you on the highway - drifting toward your lane, front wheels turning, blinker on - and remembers the spots where people cut in. Eases off early and opens the gap smoothly instead of braking hard when they dive in.",
         "New York specials"
      );
      NewIdeas.Idea var27 = new NewIdeas.Idea(
         "ghosts",
         "Object memory",
         "A person who walks behind a parked truck doesn't vanish: a \"ghost\" keeps moving at their last speed and the car slows where it could step out.",
         "Seeing what the camera can't"
      );
      NewIdeas.Idea var41 = new NewIdeas.Idea(
         "shadows",
         "Shadows at blind corners",
         "On sunny days a moving shadow on the ground at a corner means someone is coming before you can see them.",
         "Seeing what the camera can't"
      );
      NewIdeas.Idea var40 = new NewIdeas.Idea(
         "wheels",
         "Wheels before cars",
         "A neighbour's front wheels turning toward you - about half a second before the car itself moves over.",
         "Seeing what the camera can't"
      );
      NewIdeas.Idea var49 = new NewIdeas.Idea(
         "lead_blinkers",
         "Other cars' blinkers",
         "The car ahead signals right: it's about to brake - ease off early instead of late.",
         "Seeing what the camera can't"
      );
      NewIdeas.Idea var24 = new NewIdeas.Idea(
         "dooring",
         "Dooring zone",
         "Parked cars with someone in the driver's seat, brake lights on or a door opening: keep further from the parked cars for that stretch.",
         "Seeing what the camera can't"
      );
      NewIdeas.Idea var39 = new NewIdeas.Idea(
         "sirens",
         "Hearing sirens",
         "The microphones hear a siren and which side it's on: warns you early and gets ready to pull right.",
         "Seeing what the camera can't"
      );
      NewIdeas.Idea var19 = new NewIdeas.Idea(
         "lane_memory",
         "Your lanes from memory",
         "Every drive logged with RTK: after a few passes it knows where you really drive on each block. Faded lines or sun glare: lane centering follows your lane from memory.",
         "Memory of your streets"
      );
      NewIdeas.Idea var34 = new NewIdeas.Idea(
         "potholes",
         "Pothole memory",
         "The motion sensors feel every hole at an exact spot; next time it moves a little inside the lane to miss it, or slows.",
         "Memory of your streets"
      );
      NewIdeas.Idea var50 = new NewIdeas.Idea(
         "green_countdown",
         "Countdown to green",
         "New York lights run fixed cycles: it learns each light's timing from what it saw before, shows \"green in 12 s\" and coasts to arrive on green.",
         "Memory of your streets"
      );
      NewIdeas.Idea var25 = new NewIdeas.Idea(
         "spot_settings",
         "Settings per spot",
         "Where you take over or people cut in, it changes following distance, speed or lane offset just for those 500 ft.",
         "Memory of your streets"
      );
      NewIdeas.Idea var51 = new NewIdeas.Idea(
         "teach",
         "Teach mode",
         "Drive a tricky spot once with \"learn\" on (your driveway, a weird merge, a tight double-parked block): RTK keeps the path to the inch and the car follows it there next time.",
         "Memory of your streets"
      );
      NewIdeas.Idea var32 = new NewIdeas.Idea(
         "day_view",
         "Daytime view at night",
         "In the dark or in glare the tablet lays the same spot from a clear-day pass over the picture, so you see the lane lines anyway.",
         "Memory of your streets"
      );
      NewIdeas.Idea var22 = new NewIdeas.Idea(
         "crowd_line",
         "Follow the crowd's line",
         "No paint (snow, construction, fresh asphalt): the paths of the 3-4 cars ahead show where the lane really is.",
         "Learning from people"
      );
      NewIdeas.Idea var38 = new NewIdeas.Idea(
         "eyes",
         "Your eyes as a sensor",
         "The driver camera sees where you look: staring at something on the right makes the car careful there, or hints you're about to merge.",
         "Learning from people"
      );
      NewIdeas.Idea var33 = new NewIdeas.Idea(
         "takeover_why",
         "One tap after every takeover",
         "When you take over the tablet asks why (too close, swerved, wrong lane, braked late): every takeover becomes a labelled clip.",
         "Learning from people"
      );
      NewIdeas.Idea var28 = new NewIdeas.Idea(
         "voice_nudges",
         "Voice nudges",
         "\"Move over a bit\", \"take the next left\", \"stay behind the bus\" - a lane offset, a turn, a following distance.",
         "Learning from people"
      );
      NewIdeas.Idea var46 = new NewIdeas.Idea(
         "carsick",
         "Carsickness score",
         "The driver camera sees heads swaying: steering smoothness tuned until passengers' heads stop moving.",
         "Learning from people"
      );
      NewIdeas.Idea var29 = new NewIdeas.Idea(
         "unsure_slow",
         "Slow down when unsure",
         "When the model's lines and path get uncertain, a few mph less - like a person in fog or a work zone.",
         "Learning from people"
      );
      NewIdeas.Idea var20 = new NewIdeas.Idea(
         "self_labels",
         "Lane pictures that label themselves",
         "RTK knows your lane and the drive log every lane change: the lane pictures label themselves, no tapping.",
         "Training the models"
      );
      NewIdeas.Idea var26 = new NewIdeas.Idea(
         "model_disagree",
         "Two models side by side",
         "The big and small model run together; a clip is kept only where they disagree - the hard cases worth training on.",
         "Training the models"
      );
      NewIdeas.Idea var18 = new NewIdeas.Idea(
         "night_school",
         "Night school",
         "Parked at home, the eGPU trains on the day's hard clips (takeovers, disagreements, wrong lanes): in the morning a model fitted to your streets.",
         "Training the models"
      );
      NewIdeas.Idea var44 = new NewIdeas.Idea(
         "friends_fleet",
         "Friends fleet",
         "A few commas share one small map in a cloud folder - potholes, light timings, lanes, takeover spots - and every car learns from all of them.",
         "Training the models"
      );
      NewIdeas.Idea var43 = new NewIdeas.Idea(
         "school_bus",
         "School buses",
         "A yellow bus with its red lights flashing: slows down hard and tells you to STOP (New York law, both directions).",
         "From around the world"
      );
      NewIdeas.Idea var21 = new NewIdeas.Idea(
         "weather", "Weather offsets", "Rain, snow or fog (the comma's weather): more following room; in snow no faster than 40 mph.", "From around the world"
      );
      NewIdeas.Idea var35 = new NewIdeas.Idea(
         "rain_camera", "Rain from the camera", "No internet? The camera sees the wipers sweep: treated as rain (more room).", "From around the world"
      );
      NewIdeas.Idea var17 = new NewIdeas.Idea(
         "follow_flow", "Follow the flow", "On the highway never faster than the cars beside you (their typical speed + 3 mph).", "From around the world"
      );
      NewIdeas.Idea var52 = new NewIdeas.Idea(
         "far_lead", "Far lead", "Radar sees slow traffic far ahead before the model does: eases off early instead of braking late.", "From around the world"
      );
      NewIdeas.Idea var23 = new NewIdeas.Idea(
         "streak", "Takeover-free streak", "Miles since your last takeover and your best streak; a chip every 5 miles.", "From around the world"
      );
      NewIdeas.Idea var45 = new NewIdeas.Idea(
         "parking",
         "Open curb spots",
         "The camera notes open spaces between parked cars as you drive; near your destination it shows the closest one ahead.",
         "From around the world"
      );
      NewIdeas.Idea var37 = new NewIdeas.Idea(
         "speed_cams", "Speed cameras", "Speed cameras from the map: a chip, and no faster than the limit (+2) near one.", "From around the world"
      );
      NewIdeas.Idea var42 = new NewIdeas.Idea(
         "city_potholes",
         "The city's pothole list",
         "New York's open 311 pothole reports (last 45 days) join your pothole memory before you ever hit them.",
         "From around the world"
      );
      NewIdeas.Idea var47 = new NewIdeas.Idea(
         "actuated_lights",
         "Lights that change on demand",
         "Lights without a fixed cycle: how long their reds usually last, so the countdown still guesses.",
         "From around the world"
      );
      NewIdeas.Idea var53 = new NewIdeas.Idea(
         "sun_glare",
         "Sun glare",
         "The sun low and right in front of you (from where you are and the time): more room before the camera is blinded.",
         "From around the world"
      );
      NewIdeas.Idea var48 = new NewIdeas.Idea(
         "double_parked", "Double-parked memory", "Blocks and hours where your lane is usually blocked: a little slower there.", "From around the world"
      );
      NewIdeas.Idea var30 = new NewIdeas.Idea("horn", "Horn detection", "The microphone hears a honk: a chip and a little more room.", "From around the world");
      NewIdeas.Idea var54 = new NewIdeas.Idea(
         "left_gap", "Unprotected lefts", "Waiting to turn left: seconds until the next oncoming car reaches you.", "From around the world"
      );
      NewIdeas.Idea var55 = new NewIdeas.Idea(
         "hybrid",
         "Hybrid-smart driving",
         "The Sienna hybrid: light timing, hills from your RTK logs and stops on the route - coast and regen at the right moments for more mpg.",
         "The car itself"
      );
      ALL = CollectionsKt.listOf(
         new NewIdeas.Idea[]{
            var31,
            var36,
            var27,
            var41,
            var40,
            var49,
            var24,
            var39,
            var19,
            var34,
            var50,
            var25,
            var51,
            var32,
            var22,
            var38,
            var33,
            var28,
            var46,
            var29,
            var20,
            var26,
            var18,
            var44,
            var43,
            var21,
            var35,
            var17,
            var52,
            var23,
            var45,
            var37,
            var42,
            var47,
            var53,
            var48,
            var30,
            var54,
            var55
         }
      );
   }

   private NewIdeas() {
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private static final void importNightModel$lambda$4(Context var0) {
      File[] var11;
      try {
         File var10 = new File(Environment.getExternalStorageDirectory(), "Download/StarView/models");
         NewIdeas$$ExternalSyntheticLambda1 var9 = new NewIdeas$$ExternalSyntheticLambda1();
         var11 = var10.listFiles(var9);
      } catch (Exception var18) {
         return;
      }

      if (var11 != null) {
         boolean var1;
         label87: {
            label86: {
               try {
                  if (var11.length == 0) {
                     break label86;
                  }
               } catch (Exception var17) {
                  return;
               }

               var1 = 0;
               break label87;
            }

            var1 = 1;
         }

         File var20;
         if (var1) {
            var20 = null;
         } else {
            var20 = var11[0];

            int var2;
            try {
               var2 = ArraysKt.getLastIndex(var11);
            } catch (Exception var16) {
               return;
            }

            if (var2 != 0) {
               long var5;
               try {
                  var5 = var20.lastModified();
               } catch (Exception var15) {
                  return;
               }

               var1 = 1;
               File var22 = var20;
               if (1 <= var2) {
                  while (true) {
                     var22 = var11[var1];

                     long var7;
                     try {
                        var7 = var22.lastModified();
                     } catch (Exception var14) {
                        return;
                     }

                     long var3 = var5;
                     if (var5 < var7) {
                        var20 = var22;
                        var3 = var7;
                     }

                     var22 = var20;
                     if (var1 == var2) {
                        break;
                     }

                     var1++;
                     var5 = var3;
                  }
               }

               var20 = var22;
            }
         }

         if (var20 != null) {
            try {
               File var24 = new File(var0.getFilesDir(), "lane_count.lite");
               if (var24.isFile() && var24.lastModified() >= var20.lastModified()) {
                  return;
               }
            } catch (Exception var13) {
               return;
            }

            try {
               Uri var21 = Uri.fromFile(var20);
               Intrinsics.checkNotNullExpressionValue(var21, "fromFile(...)");
               LaneModelKt.importLaneModel(var0, var21);
            } catch (Exception var12) {
            }
         }
      }
   }

   private static final boolean importNightModel$lambda$4$lambda$2(File var0) {
      String var3 = var0.getName();
      Intrinsics.checkNotNullExpressionValue(var3, "getName(...)");
      boolean var2 = false;
      boolean var1 = var2;
      if (StringsKt.startsWith$default(var3, "lane_count", false, 2, null)) {
         String var4 = var0.getName();
         Intrinsics.checkNotNullExpressionValue(var4, "getName(...)");
         var1 = var2;
         if (StringsKt.endsWith$default(var4, ".lite", false, 2, null)) {
            var1 = true;
         }
      }

      return var1;
   }

   public final String deviceName(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      String var2 = Global.getString(var1.getContentResolver(), "device_name");
      String var3 = var2;
      if (var2 == null) {
         var3 = Build.MODEL;
      }

      Intrinsics.checkNotNull(var3);
      CharSequence var4 = var3;
      return StringsKt.take(new Regex("[^A-Za-z0-9_-]").replace(var4, "_"), 32);
   }

   public final List<NewIdeas.Idea> getALL() {
      return ALL;
   }

   public final Set<String> getBUILT() {
      Iterable var2 = ALL;
      var var1 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var2, 10));
      Iterator var4 = var2.iterator();

      while (var4.hasNext()) {
         var1.add(((NewIdeas.Idea)var4.next()).getId());
      }

      List var3 = var1;
      return CollectionsKt.toSet(var3);
   }

   public final Map<String, String> getFIRST() {
      return FIRST;
   }

   public final void importNightModel(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      new Thread(new NewIdeas$$ExternalSyntheticLambda0(var1)).start();
   }

   public final String key(String var1) {
      Intrinsics.checkNotNullParameter(var1, "id");
      return "crazy_" + var1;
   }

   public final boolean needsCamera(UiSettings var1) {
      Intrinsics.checkNotNullParameter(var1, "s");
      var var4 = CAMERA;
      boolean var3 = var4 instanceof Collection;
      boolean var2 = false;
      if (!var3 || !((Collection)var4).isEmpty()) {
         for (String var5 : var4) {
            if (var1.get(INSTANCE.key(var5))) {
               var2 = true;
               break;
            }
         }
      }

      return var2;
   }

   public final boolean on(UiSettings var1, String var2) {
      Intrinsics.checkNotNullParameter(var1, "s");
      Intrinsics.checkNotNullParameter(var2, "id");
      boolean var3;
      if (this.getBUILT().contains(var2) && var1.get(this.key(var2))) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public static final class Idea {
      private final String desc;
      private final String id;
      private final String section;
      private final String title;

      public Idea(String var1, String var2, String var3, String var4) {
         Intrinsics.checkNotNullParameter(var1, "id");
         Intrinsics.checkNotNullParameter(var2, "title");
         Intrinsics.checkNotNullParameter(var3, "desc");
         Intrinsics.checkNotNullParameter(var4, "section");
         super();
         this.id = var1;
         this.title = var2;
         this.desc = var3;
         this.section = var4;
      }

      public final String getDesc() {
         return this.desc;
      }

      public final String getId() {
         return this.id;
      }

      public final String getSection() {
         return this.section;
      }

      public final String getTitle() {
         return this.title;
      }
   }
}
