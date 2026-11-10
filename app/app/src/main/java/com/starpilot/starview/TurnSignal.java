package com.starpilot.starview;

import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.json.JSONObject;

public final class TurnSignal {
   public static final double ARM_M = 400.0;
   public static final double BASE_M = 15.0;
   public static final TurnSignal.Companion Companion = new TurnSignal.Companion(null);
   public static final double HEADING_MIN = Math.PI / 6;
   public static final double HEADING_YAW = 1.0995574287564276;
   public static final double LAW_M = 30.5;
   public static final double LEAD_S = 12.0;
   public static final double LOW_LEAD_S = 5.0;
   public static final long MAX_HOLD_MS = 180000L;
   public static final double MAX_M = 250.0;
   public static final double MIN_M = 30.0;
   public static final float STEER_BACK = 0.25F;
   public static final float STEER_TURN = 90.0F;
   public static final double TURNED = Math.PI * 3.0 / 10.0;
   public static final double YAW_BACK = 0.33;
   private long calmSince;
   private double dKnown;
   private int dir;
   private double distM;
   private double gap;
   private boolean gyroSeen;
   private double heading;
   private int holdSide;
   private String key;
   private boolean laneHold;
   private long lastMs;
   private boolean moved;
   private long navGoneSince;
   private String result;
   private long rightSince;
   private long since;
   private String skipKey;
   private double stamp;
   private double startedAtM;
   private TurnSignal.State state = TurnSignal.State.IDLE;
   private float steerPeak;
   private String street = "";
   private double tKnown;
   private double travelled;
   private long wrongSince;
   private double yawPeak;

   public TurnSignal() {
      this.result = "";
      this.distM = Double.NaN;
      this.startedAtM = Double.NaN;
      this.gap = Double.NaN;
      this.key = "";
      this.skipKey = "";
      this.stamp = Double.NaN;
      this.dKnown = Double.NaN;
   }

   private final void finish(String var1, long var2) {
      this.result = var1;
      this.state = TurnSignal.State.DONE;
      this.holdSide = 0;
      this.since = var2;
      this.skipKey = this.key;
   }

   public final void cancel(long var1) {
      if (this.state == TurnSignal.State.SIGNALING || this.state == TurnSignal.State.WAITING) {
         this.finish("cancelled", var1);
      }
   }

   public final int getDir() {
      return this.dir;
   }

   public final double getDistM() {
      return this.distM;
   }

   public final double getGap() {
      return this.gap;
   }

   public final int getHoldSide() {
      return this.holdSide;
   }

   public final boolean getLaneHold() {
      return this.laneHold;
   }

   public final String getResult() {
      return this.result;
   }

   public final double getStartedAtM() {
      return this.startedAtM;
   }

   public final TurnSignal.State getState() {
      return this.state;
   }

   public final String getStreet() {
      return this.street;
   }

   public final void reset() {
      this.state = TurnSignal.State.IDLE;
      this.holdSide = 0;
      this.key = "";
      this.lastMs = 0L;
      this.laneHold = false;
      this.wrongSince = 0L;
      this.rightSince = 0L;
   }

   public final void update(TurnSignal.In var1) {
      Intrinsics.checkNotNullParameter(var1, "i");
      long var11 = var1.getNowMs();
      double var2;
      if (this.lastMs == 0L) {
         var2 = 0.0;
      } else {
         var2 = RangesKt.coerceIn((var11 - this.lastMs) / 1000.0, 0.0, 0.5);
      }

      this.lastMs = var11;
      this.travelled = this.travelled + Math.max(var1.getVEgo(), 0.0F) * var2;
      if (this.state == TurnSignal.State.SIGNALING && (this.distM <= 40.0 || this.moved)) {
         this.heading = this.heading + var1.getYawRate() * var2;
      }

      JSONObject var18 = var1.getNav();
      String var17 = "";
      byte var9 = 0;
      String var16 = "";
      int var8;
      String var14;
      String var15;
      if (var18 != null) {
         var16 = var18.optString("maneuverType");
         String var19 = var18.optString("maneuverModifier");
         var15 = var18.optString("maneuverPrimaryText");
         var14 = var16 + "|" + var19 + "|" + var15;
         TurnSignal.Companion var43 = Companion;
         Intrinsics.checkNotNull(var16);
         Intrinsics.checkNotNull(var19);
         var8 = var43.turnDir(var16, var19);
         this.navGoneSince = 0L;
      } else {
         var14 = var17;
         var8 = var9;
         var15 = var16;
         if (this.navGoneSince == 0L) {
            this.navGoneSince = var11;
            var15 = var16;
            var8 = var9;
            var14 = var17;
         }
      }

      boolean var29;
      if (var18 == null && var11 - this.navGoneSince > 3000L) {
         var29 = true;
      } else {
         var29 = false;
      }

      if (var18 != null && (Intrinsics.areEqual(var14, this.key) || this.state == TurnSignal.State.IDLE || this.state == TurnSignal.State.DONE)) {
         var2 = var18.optDouble("updatedAtMonotonic", Double.NaN);
         double var4 = var18.optDouble("maneuverDistance", Double.NaN);
         boolean var10;
         if (!Double.isInfinite(var4) && !Double.isNaN(var4)) {
            var10 = true;
         } else {
            var10 = false;
         }

         if (var10) {
            if (var2 == this.stamp) {
               var10 = true;
            } else {
               var10 = false;
            }

            if (!var10 || !Intrinsics.areEqual(var14, this.key)) {
               this.stamp = var2;
               this.dKnown = var4;
               this.tKnown = this.travelled;
            }
         }
      }

      var2 = this.dKnown - (this.travelled - this.tKnown);
      if (var1.getWrongLane()) {
         this.rightSince = 0L;
         if (this.wrongSince == 0L) {
            this.wrongSince = var11;
         }

         if (var11 - this.wrongSince >= 1000L) {
            this.laneHold = true;
         }
      } else {
         this.wrongSince = 0L;
         if (this.rightSince == 0L) {
            this.rightSince = var11;
         }

         if (var11 - this.rightSince >= 1500L) {
            this.laneHold = false;
         }
      }

      TurnSignal.State var42 = this.state;
      switch (TurnSignal.WhenMappings.$EnumSwitchMapping$0[var42.ordinal()]) {
         case 1:
            boolean var35 = false;
            if (var18 != null && !Intrinsics.areEqual(var14, this.skipKey)) {
               this.skipKey = "";
            }

            if (var8 != 0 && !Intrinsics.areEqual(var14, this.skipKey)) {
               if (!Double.isInfinite(var2) && !Double.isNaN(var2)) {
                  var29 = true;
               } else {
                  var29 = false;
               }

               if (var29) {
                  var29 = var35;
                  if (0.0 <= var2) {
                     var29 = var35;
                     if (var2 <= 400.0) {
                        var29 = true;
                     }
                  }

                  if (var29) {
                     this.state = TurnSignal.State.WAITING;
                     this.key = var14;
                     this.dir = var8;
                     this.street = var15;
                     this.gap = Double.NaN;
                     this.distM = var2;
                  }
               }
            }
            break;
         case 2:
            if (var29 || var18 != null && !Intrinsics.areEqual(var14, this.key)) {
               this.state = TurnSignal.State.IDLE;
               this.key = "";
               return;
            }

            this.distM = var2;
            var2 = var1.getCrossGap();
            boolean var28;
            if (!Double.isInfinite(var2) && !Double.isNaN(var2)) {
               var28 = true;
            } else {
               var28 = false;
            }

            if (var28) {
               this.gap = var1.getCrossGap();
            }

            var2 = Companion.startDistance(var1.getVEgo(), var1.getMinLcSpeed(), var1.getUseTurnDesires(), this.gap);
            boolean var36;
            if (this.dir < 0) {
               var36 = var1.getRightBlinker();
            } else {
               var36 = var1.getLeftBlinker();
            }

            if (this.distM < -10.0) {
               this.state = TurnSignal.State.IDLE;
               this.skipKey = this.key;
               this.key = "";
            } else if (this.distM <= var2 && this.distM > 3.0 && !var1.getBusy() && !var1.getParked() && !var36 && !this.laneHold) {
               this.state = TurnSignal.State.SIGNALING;
               this.since = var11;
               this.heading = 0.0;
               this.calmSince = 0L;
               this.moved = false;
               this.holdSide = this.dir;
               this.startedAtM = this.distM;
               this.steerPeak = 0.0F;
               this.yawPeak = 0.0;
            }
            break;
         case 3:
            if (var1.getParked()) {
               this.state = TurnSignal.State.WAITING;
               this.holdSide = 0;
               this.result = "in park - let go";
               return;
            }

            if (this.laneHold && !this.moved && this.steerPeak < 45.0F && this.heading * -this.dir < Math.toRadians(15.0)) {
               this.state = TurnSignal.State.WAITING;
               this.holdSide = 0;
               String var20;
               if (this.dir < 0) {
                  var20 = "left";
               } else {
                  var20 = "right";
               }

               this.result = "not in a lane that turns " + var20 + " - let go";
               return;
            }

            var15 = "";
            this.holdSide = this.dir;
            this.distM = var2;
            if (var18 != null && !Intrinsics.areEqual(var14, this.key) && !this.moved) {
               this.moved = true;
               if (this.distM > 40.0) {
                  this.finish("route changed - stopped", var11);
                  return;
               }
            }

            boolean var13;
            if (this.dir < 0) {
               var13 = var1.getRightBlinker();
            } else {
               var13 = var1.getLeftBlinker();
            }

            boolean var27;
            if (this.heading * -this.dir >= Math.PI * 3.0 / 10.0) {
               var27 = true;
            } else {
               var27 = false;
            }

            boolean var33;
            if (this.street.length() > 0) {
               var33 = true;
            } else {
               var33 = false;
            }

            var14 = var15;
            if (var33) {
               var14 = this.street;
               var14 = " onto " + var14;
            }

            var14 = "turned" + var14;
            if (this.distM <= 40.0 || this.moved) {
               if (Math.abs(var1.getYawRate()) > 0.02F) {
                  this.gyroSeen = true;
               }

               float var6 = var1.getYawRate() * -this.dir;
               if (var6 > this.yawPeak) {
                  this.yawPeak = var6;
               }

               var2 = this.heading * -this.dir;
               float var7 = var1.getSteerDeg();
               if (!Float.isInfinite(var7) && !Float.isNaN(var7)) {
                  var33 = true;
               } else {
                  var33 = false;
               }

               if (var33 && var1.getVEgo() > 0.5F) {
                  var7 = var1.getSteerDeg() * -this.dir;
                  if (var7 > this.steerPeak) {
                     this.steerPeak = var7;
                  }

                  if (this.steerPeak >= 90.0F && var7 <= Math.max(20.0F, this.steerPeak * 0.25F) && (!this.gyroSeen || var2 >= Math.PI / 6)) {
                     this.finish(var14, var11);
                     return;
                  }
               }

               if (var2 >= 1.0995574287564276 && this.yawPeak > 0.1 && var6 <= this.yawPeak * 0.33) {
                  this.finish(var14, var11);
                  return;
               }
            }

            if (var27) {
               if (Math.abs(var1.getYawRate()) < 0.06F) {
                  if (this.calmSince == 0L) {
                     this.calmSince = var11;
                  }

                  if (var11 - this.calmSince >= 500L) {
                     this.finish(var14, var11);
                     return;
                  }
               } else {
                  this.calmSince = 0L;
               }
            }

            if (var13) {
               this.finish("other blinker on - stopped", var11);
            } else if (var29 && this.distM > 40.0) {
               this.finish("route ended - stopped", var11);
            } else if (!var27 && this.distM < -30.0) {
               this.finish("drove past the turn - stopped", var11);
            } else if (var11 - this.since > 180000L) {
               this.finish("held 3 minutes - stopped", var11);
            }
            break;
         case 4:
            this.holdSide = 0;
            if (var11 - this.since > 3000L) {
               this.state = TurnSignal.State.IDLE;
               this.key = "";
            }
            break;
         default:
            throw new NoWhenBranchMatchedException();
      }
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      public final double lastCrossGap(List<Pair<Double, String>> var1, double var2, String var4) {
         Intrinsics.checkNotNullParameter(var1, "cross");
         Intrinsics.checkNotNullParameter(var4, "turnStreet");
         double var5 = Double.NaN;

         for (Pair var11 : var1) {
            double var7 = ((Number)var11.component1()).doubleValue();
            String var16 = (String)var11.component2();
            var7 = var2 - var7;
            if (!(var7 < 15.0)) {
               int var9 = var4.length();
               boolean var10 = true;
               boolean var14;
               if (var9 > 0) {
                  var14 = true;
               } else {
                  var14 = false;
               }

               if (var14) {
                  if (var16.length() > 0) {
                     var14 = var10;
                  } else {
                     var14 = false;
                  }

                  if (var14 && OsmRoads.INSTANCE.sameStreet(var16, var4)) {
                     continue;
                  }
               }

               if (Double.isNaN(var5) || var7 < var5) {
                  var5 = var7;
               }
            }
         }

         return var5;
      }

      public final double startDistance(double var1, double var3, boolean var5, double var6) {
         double var10 = this.window(var1) * 0.9;
         double var8 = var10;
         if (var1 < var3) {
            var8 = var10;
            if (var5) {
               var8 = Math.min(var10, Math.max(35.5, var1 * 5.0 + 10.0));
            }
         }

         boolean var12;
         if (!Double.isInfinite(var6) && !Double.isNaN(var6)) {
            var12 = true;
         } else {
            var12 = false;
         }

         var1 = var8;
         if (var12) {
            var1 = var8;
            if (var6 - 5.0 >= 30.5) {
               var1 = Math.min(var8, var6 - 5.0);
            }
         }

         return Math.max(var1, 31.5);
      }

      public final int turnDir(String var1, String var2) {
         Intrinsics.checkNotNullParameter(var1, "type");
         Intrinsics.checkNotNullParameter(var2, "mod");
         boolean var4 = Intrinsics.areEqual(var1, "turn");
         byte var3 = 0;
         if (var4) {
            label39: {
               switch (var2.hashCode()) {
                  case -1572118767:
                     if (!var2.equals("sharpLeft")) {
                        return var3;
                     }
                     break;
                  case -1485380526:
                     if (!var2.equals("sharpRight")) {
                        return var3;
                     }
                     break label39;
                  case 3317767:
                     if (!var2.equals("left")) {
                        return var3;
                     }
                     break;
                  case 108511772:
                     if (!var2.equals("right")) {
                        return var3;
                     }
                     break label39;
                  default:
                     return var3;
               }

               var3 = -1;
               return var3;
            }

            var3 = 1;
         }

         return var3;
      }

      public final double window(double var1) {
         return RangesKt.coerceIn(Math.max(var1, 0.0) * 12.0 + 15.0, 30.0, 250.0);
      }
   }

   public static final class In {
      private final boolean busy;
      private final double crossGap;
      private final boolean leftBlinker;
      private final float minLcSpeed;
      private final JSONObject nav;
      private final long nowMs;
      private final boolean parked;
      private final boolean rightBlinker;
      private final float steerDeg;
      private final boolean useTurnDesires;
      private final float vEgo;
      private final boolean wrongLane;
      private final float yawRate;

      public In(
         long var1,
         JSONObject var3,
         float var4,
         float var5,
         boolean var6,
         boolean var7,
         float var8,
         boolean var9,
         double var10,
         boolean var12,
         boolean var13,
         float var14,
         boolean var15
      ) {
         this.nowMs = var1;
         this.nav = var3;
         this.vEgo = var4;
         this.yawRate = var5;
         this.leftBlinker = var6;
         this.rightBlinker = var7;
         this.minLcSpeed = var8;
         this.useTurnDesires = var9;
         this.crossGap = var10;
         this.busy = var12;
         this.parked = var13;
         this.steerDeg = var14;
         this.wrongLane = var15;
      }

      // $VF: synthetic method
      public In(
         long var1,
         JSONObject var3,
         float var4,
         float var5,
         boolean var6,
         boolean var7,
         float var8,
         boolean var9,
         double var10,
         boolean var12,
         boolean var13,
         float var14,
         boolean var15,
         int var16,
         DefaultConstructorMarker var17
      ) {
         if ((var16 & 256) != 0) {
            var10 = Double.NaN;
         }

         if ((var16 & 512) != 0) {
            var12 = false;
         }

         if ((var16 & 1024) != 0) {
            var13 = false;
         }

         if ((var16 & 2048) != 0) {
            var14 = Float.NaN;
         }

         if ((var16 & 4096) != 0) {
            var15 = false;
         }

         this(var1, var3, var4, var5, var6, var7, var8, var9, var10, var12, var13, var14, var15);
      }

      public final boolean getBusy() {
         return this.busy;
      }

      public final double getCrossGap() {
         return this.crossGap;
      }

      public final boolean getLeftBlinker() {
         return this.leftBlinker;
      }

      public final float getMinLcSpeed() {
         return this.minLcSpeed;
      }

      public final JSONObject getNav() {
         return this.nav;
      }

      public final long getNowMs() {
         return this.nowMs;
      }

      public final boolean getParked() {
         return this.parked;
      }

      public final boolean getRightBlinker() {
         return this.rightBlinker;
      }

      public final float getSteerDeg() {
         return this.steerDeg;
      }

      public final boolean getUseTurnDesires() {
         return this.useTurnDesires;
      }

      public final float getVEgo() {
         return this.vEgo;
      }

      public final boolean getWrongLane() {
         return this.wrongLane;
      }

      public final float getYawRate() {
         return this.yawRate;
      }
   }

   public static enum State {
      DONE,
      IDLE,
      SIGNALING,
      WAITING;
      private static final EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(TurnSignal.State.$VALUES);
      private static final TurnSignal.State[] $VALUES = $values();

      // $VF: synthetic method
      private static final TurnSignal.State[] $values() {
         return new TurnSignal.State[]{IDLE, WAITING, SIGNALING, DONE};
      }

      public static EnumEntries<TurnSignal.State> getEntries() {
         return $ENTRIES;
      }
   }

   // $VF: synthetic class
   public final class WhenMappings {
      public static final int[] $EnumSwitchMapping$0;

      static {
         int[] var0 = new int[TurnSignal.State.values().length];

         try {
            var0[TurnSignal.State.IDLE.ordinal()] = 1;
         } catch (NoSuchFieldError var5) {
         }

         try {
            var0[TurnSignal.State.WAITING.ordinal()] = 2;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[TurnSignal.State.SIGNALING.ordinal()] = 3;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[TurnSignal.State.DONE.ordinal()] = 4;
         } catch (NoSuchFieldError var2) {
         }

         $EnumSwitchMapping$0 = var0;
      }
   }
}
