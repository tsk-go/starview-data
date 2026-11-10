package com.starpilot.starview;

import kotlin.collections.ArraysKt;
import kotlin.collections.SetsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.json.JSONObject;

public final class NudgeLogic {
   public static final NudgeLogic INSTANCE = new NudgeLogic();
   private static final double[] KEEP_D = new double[]{25.0, 90.0, 160.0};
   private static final double[] KEEP_V = new double[]{0.0, 15.0, 30.0};

   private NudgeLogic() {
   }

   public final NudgeLogic.Result evaluate(
      JSONObject var1, double var2, boolean var4, boolean var5, boolean var6, boolean var7, double var8, double var10, double var12
   ) {
      if (var1 != null) {
         boolean var19 = false;
         if (var1.optBoolean("valid", false)) {
            String var22 = var1.optString("maneuverType");
            String var23 = var1.optString("maneuverModifier");
            if (!Intrinsics.areEqual(var22, "off ramp") && !Intrinsics.areEqual(var22, "fork")) {
               return new NudgeLogic.Result(NudgeLogic.State.NONE, false, 0.0, 0.0, null, 30, null);
            }

            if (!SetsKt.setOf(new String[]{"slightLeft", "left", "sharpLeft", "slightRight", "right", "sharpRight"}).contains(var23)) {
               return new NudgeLogic.Result(NudgeLogic.State.NONE, false, 0.0, 0.0, null, 30, null);
            }

            var22 = var1.optString("activeLaneDirection");
            if (var22 != null) {
               boolean var21;
               label133: {
                  label132: {
                     switch (var22.hashCode()) {
                        case -711874727:
                           if (!var22.equals("slightRight")) {
                              return new NudgeLogic.Result(NudgeLogic.State.NONE, false, 0.0, 0.0, null, 30, null);
                           }
                           break;
                        case -438788310:
                           if (!var22.equals("slightLeft")) {
                              return new NudgeLogic.Result(NudgeLogic.State.NONE, false, 0.0, 0.0, null, 30, null);
                           }
                           break label132;
                        case 3317767:
                           if (!var22.equals("left")) {
                              return new NudgeLogic.Result(NudgeLogic.State.NONE, false, 0.0, 0.0, null, 30, null);
                           }
                           break label132;
                        case 108511772:
                           if (!var22.equals("right")) {
                              return new NudgeLogic.Result(NudgeLogic.State.NONE, false, 0.0, 0.0, null, 30, null);
                           }
                           break;
                        default:
                           return new NudgeLogic.Result(NudgeLogic.State.NONE, false, 0.0, 0.0, null, 30, null);
                     }

                     var21 = true;
                     break label133;
                  }

                  var21 = false;
               }

               double var14 = var1.optDouble("maneuverDistance", Double.NaN);
               if (Double.isNaN(var14)) {
                  return new NudgeLogic.Result(NudgeLogic.State.NONE, false, 0.0, 0.0, null, 30, null);
               }

               int var20 = var1.optInt("sameSideLaneCount", 0);
               int var18 = var1.optInt("laneCount", 0);
               boolean var28;
               if (var20 <= 1 || var18 > 0 && RangesKt.coerceAtLeast(var18 - var20, 0) > 2) {
                  var28 = false;
               } else {
                  var28 = true;
               }

               double var16 = this.interp(var2, KEEP_V, KEEP_D);
               if (var28) {
                  var2 = 0.6;
               } else {
                  var2 = 1.0;
               }

               var16 *= var2;
               if (var28 && var1.optBoolean("activeLaneAtRoadEdge", false) && var1.optBoolean("hasSharedSameSideLane", false)) {
                  return new NudgeLogic.Result(NudgeLogic.State.NONE, false, 0.0, 0.0, null, 30, null);
               }

               if (var14 > 2.5 * var16) {
                  return new NudgeLogic.Result(NudgeLogic.State.NONE, false, 0.0, 0.0, null, 30, null);
               }

               if (var14 > var16) {
                  return new NudgeLogic.Result(NudgeLogic.State.SOON, var21, var14, var16, null, 16, null);
               }

               String var24;
               if (var21 && var7) {
                  var24 = "car in your right blind spot";
               } else if (!var21 && var6) {
                  var24 = "car in your left blind spot";
               } else if (var21 && var4) {
                  var24 = "left blinker is on";
               } else if (!var21 && var5) {
                  var24 = "right blinker is on";
               } else {
                  label151: {
                     if (var12 > 0.0) {
                        if (var21) {
                           var2 = var10;
                        } else {
                           var2 = var8;
                        }

                        if (var2 < var12) {
                           var24 = "no lane seen on that side yet";
                           break label151;
                        }
                     }

                     var24 = "";
                  }
               }

               var28 = var19;
               if (var24.length() == 0) {
                  var28 = true;
               }

               NudgeLogic.State var31;
               if (var28) {
                  var31 = NudgeLogic.State.READY;
               } else {
                  var31 = NudgeLogic.State.WAIT;
               }

               return new NudgeLogic.Result(var31, var21, var14, var16, var24);
            }

            return new NudgeLogic.Result(NudgeLogic.State.NONE, false, 0.0, 0.0, null, 30, null);
         }
      }

      return new NudgeLogic.Result(NudgeLogic.State.NONE, false, 0.0, 0.0, null, 30, null);
   }

   public final double interp(double var1, double[] var3, double[] var4) {
      Intrinsics.checkNotNullParameter(var3, "xs");
      Intrinsics.checkNotNullParameter(var4, "ys");
      if (var1 <= var3[0]) {
         return var4[0];
      } else {
         int var5 = 1;

         for (int var6 = var3.length; var5 < var6; var5++) {
            if (var1 <= var3[var5]) {
               return var4[var5 - 1] + (var4[var5] - var4[var5 - 1]) * (var1 - var3[var5 - 1]) / (var3[var5] - var3[var5 - 1]);
            }
         }

         return ArraysKt.last(var4);
      }
   }

   public static final class Result {
      private final double distanceM;
      private final boolean right;
      private final NudgeLogic.State state;
      private final String why;
      private final double windowM;

      public Result(NudgeLogic.State var1, boolean var2, double var3, double var5, String var7) {
         Intrinsics.checkNotNullParameter(var1, "state");
         Intrinsics.checkNotNullParameter(var7, "why");
         super();
         this.state = var1;
         this.right = var2;
         this.distanceM = var3;
         this.windowM = var5;
         this.why = var7;
      }

      // $VF: synthetic method
      public Result(NudgeLogic.State var1, boolean var2, double var3, double var5, String var7, int var8, DefaultConstructorMarker var9) {
         if ((var8 & 2) != 0) {
            var2 = false;
         }

         double var10 = 0.0;
         if ((var8 & 4) != 0) {
            var3 = 0.0;
         }

         if ((var8 & 8) != 0) {
            var5 = var10;
         }

         if ((var8 & 16) != 0) {
            var7 = "";
         }

         this(var1, var2, var3, var5, var7);
      }

      public final double getDistanceM() {
         return this.distanceM;
      }

      public final boolean getRight() {
         return this.right;
      }

      public final NudgeLogic.State getState() {
         return this.state;
      }

      public final String getWhy() {
         return this.why;
      }

      public final double getWindowM() {
         return this.windowM;
      }
   }

   public static enum State {
      NONE,
      READY,
      SOON,
      WAIT;
      private static final EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(NudgeLogic.State.$VALUES);
      private static final NudgeLogic.State[] $VALUES = $values();

      // $VF: synthetic method
      private static final NudgeLogic.State[] $values() {
         return new NudgeLogic.State[]{NONE, SOON, READY, WAIT};
      }

      public static EnumEntries<NudgeLogic.State> getEntries() {
         return $ENTRIES;
      }
   }
}
