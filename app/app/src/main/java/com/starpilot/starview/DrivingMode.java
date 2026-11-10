package com.starpilot.starview;

import android.graphics.Color;
import java.util.Arrays;
import java.util.Calendar;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import org.capnproto.SlcPeek.Curve;
import org.capnproto.SlcPeek.Slc;

public final class DrivingMode {
   public static final DrivingMode INSTANCE = new DrivingMode();

   private DrivingMode() {
   }

   // $VF: synthetic method
   public static String clock$default(DrivingMode var0, Calendar var1, boolean var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = Calendar.getInstance();
      }

      return var0.clock(var1, var2);
   }

   // $VF: synthetic method
   public static Pair curveLabel$default(DrivingMode var0, Telemetry var1, long var2, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = System.nanoTime();
      }

      return var0.curveLabel(var1, var2);
   }

   public final String clock(Calendar var1, boolean var2) {
      Intrinsics.checkNotNullParameter(var1, "now");
      String var5;
      if (var2) {
         var5 = String.format("%02d:%02d", Arrays.copyOf(new Object[]{var1.get(11), var1.get(12)}, 2));
         Intrinsics.checkNotNullExpressionValue(var5, "format(...)");
      } else {
         int var4 = var1.get(10);
         int var3 = var1.get(12);
         String var6;
         if (var1.get(9) == 0) {
            var6 = "AM";
         } else {
            var6 = "PM";
         }

         var5 = String.format("%d:%02d %s", Arrays.copyOf(new Object[]{(var4 + 11) % 12 + 1, var3, var6}, 3));
         Intrinsics.checkNotNullExpressionValue(var5, "format(...)");
      }

      return var5;
   }

   public final Pair<String, Integer> curveLabel(Telemetry var1, long var2) {
      Intrinsics.checkNotNullParameter(var1, "t");
      Slc var8 = var1.slcLive(var2);
      if (var8 != null) {
         Curve var14 = var8.curve;
         if (var14 != null) {
            if (var14.configured && Intrinsics.areEqual(var1.getAlertSize(), "none")) {
               boolean var7 = var1.isMetric();
               if (var14.applied && var14.controlling && var1.getLongActive() && var14.hasCeiling) {
                  float var5 = var14.ceilingMps;
                  float var4;
                  if (var7) {
                     var4 = 3.6F;
                  } else {
                     var4 = 2.2369363F;
                  }

                  int var13 = MathKt.roundToInt(var5 * var4);
                  String var10;
                  if (var7) {
                     var10 = "KM/H";
                  } else {
                     var10 = "MPH";
                  }

                  String var9 = "CURVE " + var13 + " " + var10;
                  OverlayView.Companion var11 = OverlayView.Companion;
                  if (var14.hasRoadCurvature) {
                     var4 = var14.roadCurvature;
                  } else {
                     var4 = 0.0F;
                  }

                  return TuplesKt.to(var9, var11.cscColor(var4));
               }

               if (var14.training && var1.getStarted()) {
                  int var6 = MathKt.roundToInt(var14.progress);
                  return TuplesKt.to("CURVE LEARNING " + var6 + "%", Color.rgb(112, 192, 216));
               }

               return null;
            }

            return null;
         }
      }

      return null;
   }

   public final String describe(Telemetry var1) {
      Intrinsics.checkNotNullParameter(var1, "t");
      boolean var2;
      if (var1.getGasPressed() && var1.getEnabled() && Intrinsics.areEqual(var1.getOpState(), "overriding")) {
         var2 = true;
      } else {
         var2 = false;
      }

      boolean var4 = var1.getLatActive();
      boolean var3 = var1.getLongActive();
      String var5;
      if (var2) {
         var5 = "ACC - Override";
      } else if (var4 && var1.mBool("SwitchbackModeEnabled", false)) {
         var5 = "Switchback Mode";
      } else if (var3) {
         if (var1.getTrafficMode()) {
            var5 = "Traffic Mode";
         } else if (var1.getExperimentalMode()) {
            var5 = "ACC - Experimental";
         } else {
            var5 = "ACC - Chill";
         }
      } else if (var4) {
         var5 = "Always On Lateral";
      } else if (var1.getCruiseEnabled()) {
         var5 = "Stock ACC";
      } else {
         var5 = "Disengaged";
      }

      return var5;
   }
}
