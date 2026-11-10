package com.starpilot.starview;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.json.JSONObject;

public final class LaneSuggest {
   public static final long AUTO_MS = 3000L;
   public static final float CAR_NUDGE = 100.0F;
   public static final long CLEAR_MS = 1000L;
   public static final LaneSuggest.Companion Companion = new LaneSuggest.Companion(null);
   public static final long EXIT_GAP_MS = 1500L;
   public static final double EXIT_LEAD_S = 8.0;
   public static final int EXIT_MAX_TRIES = 5;
   public static final double EXIT_MIN_M = 25.0;
   public static final double EXIT_START_MAX_M = 320.0;
   public static final double EXIT_START_MIN_M = 120.0;
   public static final long EXIT_TRY_MS = 6000L;
   public static final float LIGHT_NUDGE = 60.0F;
   public static final long LIGHT_NUDGE_MS = 150L;
   public static final float PASS_HEADWAY_S = 4.0F;
   public static final float PASS_HELD_BACK = 1.34F;
   public static final long PASS_HOLD_MS = 5000L;
   public static final float PASS_MARGIN = 2.235F;
   public static final float PASS_MAX_DIST = 120.0F;
   public static final float PASS_MIN_SPEED = 13.41F;
   public static final long RETURN_AFTER_MS = 8000L;
   public static final float RETURN_FREE_S = 6.0F;
   public static final float RETURN_GAP_S = 2.0F;
   public static final long RETURN_HOLD_MS = 3000L;
   public static final long RETURN_WINDOW_MS = 180000L;
   public static final long ROUTE_HOLD_MS = 2000L;
   public static final double TURN_LANE_M = 92.0;
   public static final long WAIT_MODEL_MS = 30000L;
   public static final long WAIT_MS = 60000L;
   private boolean armed;
   private boolean autoAsk;
   private boolean beepNow;
   private long clearSince;
   private String confirmedBy;
   private final long[] cool;
   private int dir;
   private final float[] ema;
   private String exitDoneKey;
   private String exitKey;
   private long exitRetryAt;
   private int exitTries;
   private long firstWait;
   private final long[] highSince;
   private int holdSide;
   private int homeLeft;
   private String kind;
   private long lastBeep;
   private long lastMs;
   private int lcDir;
   private long lightSince;
   private long mergeLostSince;
   private float nudgeTorque;
   private long owedAt;
   private int owedRight;
   private long passLostSince;
   private long passSince;
   private boolean reasked;
   private String result;
   private long returnLostSince;
   private long returnSince;
   private long routeLostSince;
   private long routeSince;
   private int routeSinceDir;
   private long since;
   private LaneSuggest.State state = LaneSuggest.State.IDLE;
   private boolean test;
   private long waitSince;
   private String why = "";

   public LaneSuggest() {
      this.kind = "";
      this.homeLeft = -1;
      this.exitKey = "";
      this.exitDoneKey = "";
      this.result = "";
      this.cool = new long[2];
      this.ema = new float[2];
      this.highSince = new long[2];
      this.confirmedBy = "";
   }

   private final void ask(int var1, String var2, long var3, String var5) {
      this.state = LaneSuggest.State.ASKING;
      this.dir = var1;
      this.why = var2;
      this.kind = var5;
      this.since = var3;
      this.lastBeep = 0L;
      this.confirmedBy = "";
      this.autoAsk = false;
      this.reasked = false;
      this.armed = false;
      this.firstWait = 0L;
   }

   // $VF: synthetic method
   public static void confirm$default(LaneSuggest var0, long var1, String var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var3 = "tap";
      }

      var0.confirm(var1, var3);
   }

   private final boolean exitDue(LaneSuggest.In var1, long var2) {
      boolean var7 = var1.getExitOn();
      boolean var8 = false;
      if (var7 && var1.getExitDir() != 0 && var1.getCommaConfirms()) {
         boolean var6;
         if (var1.getExitKey().length() == 0) {
            var6 = true;
         } else {
            var6 = false;
         }

         if (!var6 && !Intrinsics.areEqual(var1.getExitKey(), this.exitDoneKey)) {
            if (!Intrinsics.areEqual(var1.getExitKey(), this.exitKey) || this.exitTries < 5 && (this.exitRetryAt == 0L || var2 >= this.exitRetryAt)) {
               if (var1.getExitSideLanes() >= 2) {
                  return false;
               }

               if (var1.getExitInLane()) {
                  return false;
               }

               double var4 = var1.getExitDist();
               if (!Double.isInfinite(var4) && !Double.isNaN(var4)) {
                  var6 = true;
               } else {
                  var6 = false;
               }

               var7 = var8;
               if (var6) {
                  var7 = var8;
                  if (var4 >= 25.0) {
                     var7 = var8;
                     if (var4 <= RangesKt.coerceIn(var1.getVEgo() * 8.0, 120.0, 320.0)) {
                        var7 = true;
                     }
                  }
               }

               return var7;
            }

            return false;
         }
      }

      return false;
   }

   private final boolean exitGone(LaneSuggest.In var1) {
      boolean var7 = Intrinsics.areEqual(var1.getExitKey(), this.exitKey);
      boolean var6 = true;
      boolean var5 = var6;
      if (var7) {
         double var2 = var1.getExitDist();
         boolean var4;
         if (!Double.isInfinite(var2) && !Double.isNaN(var2)) {
            var4 = true;
         } else {
            var4 = false;
         }

         var5 = var6;
         if (var4) {
            if (var1.getExitDist() < 25.0) {
               var5 = var6;
            } else {
               var5 = false;
            }
         }
      }

      return var5;
   }

   private final void finish(String var1, long var2) {
      this.holdSide = 0;
      this.state = LaneSuggest.State.RESULT;
      this.result = var1;
      this.since = var2;
   }

   private final int idx(int var1) {
      byte var2;
      if (var1 < 0) {
         var2 = 0;
      } else {
         var2 = 1;
      }

      return var2;
   }

   private final boolean nudged(LaneSuggest.In var1, long var2) {
      if (!var1.getNudgeConfirm()) {
         this.lightSince = 0L;
         return false;
      } else {
         float var4;
         if (this.dir < 0) {
            var4 = var1.getSteeringTorque();
         } else {
            var4 = -var1.getSteeringTorque();
         }

         boolean var5;
         if (var1.getSteeringPressed() && var4 > 0.0F && var1.getNudgeTorque() <= 100.0F) {
            var5 = true;
         } else {
            var5 = false;
         }

         if (var4 >= var1.getNudgeTorque()) {
            if (this.lightSince == 0L) {
               this.lightSince = var2;
            }
         } else {
            this.lightSince = 0L;
         }

         boolean var6;
         if (this.lightSince != 0L && var2 - this.lightSince >= 150L) {
            var6 = true;
         } else {
            var6 = false;
         }

         if (!var5 && !var6) {
            return false;
         } else {
            this.nudgeTorque = var1.getSteeringTorque();
            this.lightSince = 0L;
            return true;
         }
      }
   }

   private final boolean passCond(LaneSuggest.In var1) {
      if (var1.getPassOn()
         && var1.getEngaged()
         && var1.getVEgo() >= 13.41F
         && var1.getVSet() > 0.0F
         && var1.getLeadOn()
         && var1.getLeadD() > 0.0F
         && var1.getLeadD() <= Math.min(120.0F, Math.max(30.0F, var1.getVEgo() * 4.0F))
         && var1.getLeadV() <= var1.getVSet() - 2.235F
         && var1.getVEgo() <= var1.getVSet() - 1.34F) {
         int var2 = var1.getLeftLanes();
         boolean var3 = true;
         if (var2 >= 1 && var1.getRouteSoonDir() <= 0) {
            return var3;
         }
      }

      return false;
   }

   private final boolean returnCond(LaneSuggest.In var1, long var2) {
      boolean var7 = var1.getReturnOn();
      boolean var6 = false;
      if (!var7 || this.owedRight <= 0 || var2 - this.owedAt < 8000L || !var1.getEngaged() || var1.getVEgo() < 13.41F || var1.getVSet() <= 0.0F) {
         return false;
      } else if (var1.getRightLanes() < 1 || var1.getRightBsm() || var1.getRouteSoonDir() < 0 || var1.getLaneChanging()) {
         return false;
      } else if (var1.getRLeadOn() && !(var1.getRLeadD() <= 0.0F)) {
         boolean var4;
         if (var1.getRLeadD() >= var1.getVEgo() * 6.0F) {
            var4 = true;
         } else {
            var4 = false;
         }

         boolean var5;
         if (var1.getRLeadV() >= var1.getVSet() - 2.235F && var1.getRLeadD() >= var1.getVEgo() * 2.0F) {
            var5 = true;
         } else {
            var5 = false;
         }

         if (var4 || var5) {
            var6 = true;
         }

         return var6;
      } else {
         return true;
      }
   }

   private final boolean routeGone(long var1) {
      boolean var3;
      if (Intrinsics.areEqual(this.kind, "route") && !this.test && this.routeLostSince != 0L && var1 - this.routeLostSince >= 1000L) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   private final String side(int var1) {
      String var2;
      if (var1 < 0) {
         var2 = "LEFT";
      } else {
         var2 = "RIGHT";
      }

      return var2;
   }

   private final String speed(float var1, boolean var2) {
      String var4;
      StringBuilder var5;
      if (var2) {
         int var3 = Math.round(3.6F * var1);
         var5 = new StringBuilder().append(var3);
         var4 = " km/h";
      } else {
         int var6 = Math.round(2.23694F * var1);
         var5 = new StringBuilder().append(var6);
         var4 = " mph";
      }

      return var5.append(var4).toString();
   }

   public final long autoLeftMs(long var1) {
      return RangesKt.coerceAtLeast(this.since + 3000L - var1, 0L);
   }

   public final void confirm(long var1, String var3) {
      Intrinsics.checkNotNullParameter(var3, "by");
      if (this.state == LaneSuggest.State.ASKING) {
         this.firstWait = 0L;
         this.state = LaneSuggest.State.SIGNALING;
         this.since = var1;
         this.holdSide = this.dir;
         this.confirmedBy = var3;
      }
   }

   public final void dismiss(long var1) {
      if (this.state == LaneSuggest.State.ASKING || this.state == LaneSuggest.State.SIGNALING) {
         this.test = false;
      }

      LaneSuggest.State var3 = this.state;
      switch (LaneSuggest.WhenMappings.$EnumSwitchMapping$0[var3.ordinal()]) {
         case 3:
         case 4:
            this.state = LaneSuggest.State.IDLE;
            this.armed = false;
            this.cool[this.idx(this.dir)] = '\uea60' + var1;
            if (Intrinsics.areEqual(this.kind, "return")) {
               this.owedRight = 0;
            }
            break;
         case 5:
            if (Intrinsics.areEqual(this.kind, "exit")) {
               this.exitDoneKey = this.exitKey;
               this.exitRetryAt = 0L;
            }

            this.finish("cancelled", var1);
      }
   }

   public final boolean getArmed() {
      return this.armed;
   }

   public final boolean getAutoAsk() {
      return this.autoAsk;
   }

   public final boolean getBeepNow() {
      return this.beepNow;
   }

   public final String getConfirmedBy() {
      return this.confirmedBy;
   }

   public final int getDir() {
      return this.dir;
   }

   public final int getExitTry() {
      return this.exitTries;
   }

   public final int getHoldSide() {
      return this.holdSide;
   }

   public final int getHomeLeft() {
      return this.homeLeft;
   }

   public final String getKind() {
      return this.kind;
   }

   public final String getLabel() {
      String var1 = this.kind;
      switch (var1.hashCode()) {
         case -934396624:
            if (var1.equals("return")) {
               return "BACK";
            }
            break;
         case 3127582:
            if (var1.equals("exit")) {
               return "ROUTE";
            }
            break;
         case 3433489:
            if (var1.equals("pass")) {
               return "PASS";
            }
            break;
         case 3556498:
            if (var1.equals("test")) {
               return "TEST";
            }
            break;
         case 103785528:
            if (var1.equals("merge")) {
               return "LANE ENDS";
            }
            break;
         case 104069929:
            if (var1.equals("model")) {
               return "MODEL";
            }
            break;
         case 108704329:
            if (var1.equals("route")) {
               return "ROUTE";
            }
      }

      return "";
   }

   public final float getNudgeTorque() {
      return this.nudgeTorque;
   }

   public final int getOwedRight() {
      return this.owedRight;
   }

   public final boolean getReasked() {
      return this.reasked;
   }

   public final String getResult() {
      return this.result;
   }

   public final LaneSuggest.State getState() {
      return this.state;
   }

   public final boolean getTest() {
      return this.test;
   }

   public final String getWhy() {
      return this.why;
   }

   public final void reset() {
      this.state = LaneSuggest.State.IDLE;
      this.holdSide = 0;
      this.test = false;
      this.autoAsk = false;
   }

   public final void testAsk(int var1, String var2, long var3) {
      Intrinsics.checkNotNullParameter(var2, "w");
      byte var5 = 1;
      this.test = true;
      if (var1 < 0) {
         var5 = -1;
      }

      this.ask(var5, var2, var3, "test");
   }

   public final void update(LaneSuggest.In var1) {
      Intrinsics.checkNotNullParameter(var1, "i");
      long var13 = var1.getNowMs();
      float var2;
      if (this.lastMs == 0L) {
         var2 = 0.0F;
      } else {
         var2 = RangesKt.coerceIn((float)(var13 - this.lastMs) / 1000.0F, 0.0F, 0.5F);
      }

      this.lastMs = var13;
      this.beepNow = false;
      float[] var17 = var1.getPred();
      int var5 = 0;

      while (true) {
         int var6 = 3;
         if (var5 >= 2) {
            if (var1.getLaneChanging()) {
               if (var1.getLaneChangeDir() != 0) {
                  if (this.lcDir == 0 && var1.getLaneChangeDir() < 0 && this.owedRight == 0) {
                     this.homeLeft = var1.getLeftLanes();
                  }

                  this.lcDir = var1.getLaneChangeDir();
               }
            } else if (this.lcDir != 0) {
               if (this.lcDir < 0) {
                  if (var1.getReturnOn()) {
                     this.owedRight = Math.min(this.owedRight + 1, 3);
                     this.owedAt = var13;
                  }
               } else if (this.owedRight > 0) {
                  this.owedRight--;
               }

               this.lcDir = 0;
            }

            if (this.owedRight > 0 && var13 - this.owedAt > 180000L) {
               this.owedRight = 0;
            }

            if (this.owedRight > 0 && this.homeLeft >= 0 && var1.getLeftLanes() >= this.homeLeft && this.lcDir == 0) {
               this.owedRight = 0;
            }

            if (this.owedRight == 0 && this.lcDir == 0) {
               this.homeLeft = -1;
            }

            if (this.returnCond(var1, var13)) {
               if (this.returnSince == 0L) {
                  this.returnSince = var13;
               }

               this.returnLostSince = 0L;
            } else {
               this.returnSince = 0L;
               if (this.returnLostSince == 0L) {
                  this.returnLostSince = var13;
               }
            }

            if (var1.getMergeDir() == 0 || Intrinsics.areEqual(this.kind, "merge") && var1.getMergeDir() != this.dir) {
               if (this.mergeLostSince == 0L) {
                  this.mergeLostSince = var13;
               }
            } else {
               this.mergeLostSince = 0L;
            }

            if (var1.getRouteDir() == 0 || var1.getRouteDir() != this.routeSinceDir) {
               this.routeSinceDir = var1.getRouteDir();
               long var11;
               if (var1.getRouteDir() != 0) {
                  var11 = var13;
               } else {
                  var11 = 0L;
               }

               this.routeSince = var11;
            } else if (this.routeSince == 0L) {
               this.routeSince = var13;
            }

            if (var1.getRouteDir() == 0 || Intrinsics.areEqual(this.kind, "route") && var1.getRouteDir() != this.dir) {
               if (this.routeLostSince == 0L) {
                  this.routeLostSince = var13;
               }
            } else {
               this.routeLostSince = 0L;
            }

            if (this.passCond(var1)) {
               if (this.passSince == 0L) {
                  this.passSince = var13;
               }

               this.passLostSince = 0L;
            } else {
               this.passSince = 0L;
               if (this.passLostSince == 0L) {
                  this.passLostSince = var13;
               }
            }

            boolean var28;
            if (var1.getEngaged()
               && var1.getVEgo() >= var1.getMinLcSpeed()
               && !var1.getLaneChanging()
               && !var1.getLeftBlinker()
               && !var1.getRightBlinker()
               && !var1.getBrake()
               && !var1.getBusy()) {
               var28 = 1;
            } else {
               var28 = 0;
            }

            label552: {
               label551: {
                  LaneSuggest.State var43 = this.state;
                  var6 = LaneSuggest.WhenMappings.$EnumSwitchMapping$0[var43.ordinal()];
                  Object var19 = null;
                  Integer var47 = null;
                  String var44 = "pass";
                  switch (var6) {
                     case 1:
                     case 2:
                        if (this.state == LaneSuggest.State.RESULT && var13 - this.since > 4000L) {
                           this.state = LaneSuggest.State.IDLE;
                        }

                        if ((this.state == LaneSuggest.State.IDLE || this.exitRetryAt != 0L && var13 >= this.exitRetryAt) && var28 && this.exitDue(var1, var13)
                           )
                         {
                           if (!Intrinsics.areEqual(var1.getExitKey(), this.exitKey)) {
                              this.exitKey = var1.getExitKey();
                              this.exitTries = 0;
                           }

                           this.exitTries++;
                           this.exitRetryAt = 0L;
                           this.ask(var1.getExitDir(), var1.getExitWhy(), var13, "exit");
                           this.state = LaneSuggest.State.SIGNALING;
                           this.holdSide = this.dir;
                           this.confirmedBy = "route";
                           return;
                        }

                        if (this.state == LaneSuggest.State.IDLE && var28) {
                           var47 = new int[]{-1, 1};
                           int var36 = ((Object[])var47).length;
                           var28 = 0;

                           while (true) {
                              if (var28 >= var36) {
                                 var47 = (Integer)var19;
                                 break;
                              }

                              int var8 = (int)((Object[])var47)[var28];
                              boolean var35;
                              if (this.highSince[this.idx(var8)] == 0L
                                 || var13 - this.highSince[this.idx(var8)] < 1000L
                                 || var13 < this.cool[this.idx(var8)]
                                 || var8 > 0 && var1.getHighway() && var1.getLanesTotal() >= 3) {
                                 var35 = false;
                              } else {
                                 var35 = true;
                              }

                              if (var35) {
                                 var47 = var8;
                                 break;
                              }

                              var28++;
                           }

                           if (var47 != null) {
                              var28 = var47;
                              String var50 = this.side(var47).toLowerCase(Locale.ROOT);
                              Intrinsics.checkNotNullExpressionValue(var50, "toLowerCase(...)");
                              this.ask(var28, "the driving model wants to move " + var50, var13, "model");
                           } else if (var1.getRouteDir() != 0
                              && this.routeSince != 0L
                              && var13 - this.routeSince >= 2000L
                              && var13 >= this.cool[this.idx(var1.getRouteDir())]) {
                              this.ask(var1.getRouteDir(), var1.getRouteWhy(), var13, "route");
                           } else if (var1.getMergeDir() != 0 && var13 >= this.cool[this.idx(var1.getMergeDir())]) {
                              this.ask(var1.getMergeDir(), var1.getMergeWhy(), var13, "merge");
                           } else if (this.passSince != 0L && var13 - this.passSince >= 5000L && var13 >= this.cool[0] && !var1.getLeftBsm()) {
                              String var51 = this.speed(var1.getLeadV(), var1.getMetric());
                              var19 = this.speed(var1.getVSet(), var1.getMetric());
                              this.ask(-1, "slower car ahead at " + var51 + " - your set speed is " + var19, var13, "pass");
                           } else if (this.returnSince != 0L && var13 - this.returnSince >= 3000L && var13 >= this.cool[1]) {
                              this.ask(1, "you passed - the right lane is clear, move back right", var13, "return");
                           }

                           boolean var41;
                           if (this.state == LaneSuggest.State.ASKING
                              && var1.getAuto()
                              && var1.getCommaConfirms()
                              && !Intrinsics.areEqual(this.kind, var44)
                              && !Intrinsics.areEqual(this.kind, "return")
                              && !Intrinsics.areEqual(this.kind, "merge")) {
                              var41 = true;
                           } else {
                              var41 = false;
                           }

                           this.autoAsk = var41;
                        }

                        Unit var27 = Unit.INSTANCE;
                        break;
                     case 3:
                        if (this.test) {
                           if (this.nudged(var1, var13)) {
                              this.confirm(var13, "nudge");
                              return;
                           }

                           if (var13 - this.since > 12000L) {
                              this.state = LaneSuggest.State.IDLE;
                              this.test = false;
                              return;
                           }

                           if (var13 - this.lastBeep >= 2000L) {
                              this.beepNow = true;
                              this.lastBeep = var13;
                           }

                           return;
                        }

                        if (!var1.getEngaged() || var1.getLaneChanging() || var1.getBrake()) {
                           break label551;
                        }

                        boolean var39;
                        if (this.dir < 0) {
                           var39 = var1.getRightBlinker();
                        } else {
                           var39 = var1.getLeftBlinker();
                        }

                        if (var39) {
                           break label551;
                        }

                        if (this.dir < 0) {
                           var39 = var1.getLeftBsm();
                        } else {
                           var39 = var1.getRightBsm();
                        }

                        if (var39) {
                           this.state = LaneSuggest.State.WAITING;
                           if (this.firstWait == 0L) {
                              this.firstWait = var13;
                           }

                           this.waitSince = this.firstWait;
                           this.clearSince = 0L;
                           return;
                        }

                        if (var13 - this.since > 12000L) {
                           this.state = LaneSuggest.State.IDLE;
                           this.cool[this.idx(this.dir)] = 30000 + var13;
                           return;
                        }

                        if (Intrinsics.areEqual(this.kind, "pass") && this.passLostSince != 0L && var13 - this.passLostSince >= 2000L) {
                           this.state = LaneSuggest.State.IDLE;
                           this.cool[0] = 20000 + var13;
                           return;
                        }

                        if (Intrinsics.areEqual(this.kind, "return") && this.returnLostSince != 0L && var13 - this.returnLostSince >= 2000L) {
                           this.state = LaneSuggest.State.IDLE;
                           this.cool[1] = 20000 + var13;
                           return;
                        }

                        if (Intrinsics.areEqual(this.kind, "merge") && this.mergeLostSince != 0L && var13 - this.mergeLostSince >= 2000L) {
                           this.state = LaneSuggest.State.IDLE;
                           this.cool[this.idx(this.dir)] = 20000 + var13;
                           return;
                        }

                        if (this.routeGone(var13)) {
                           this.state = LaneSuggest.State.IDLE;
                           this.cool[this.idx(this.dir)] = 20000 + var13;
                           return;
                        }

                        if (this.nudged(var1, var13)) {
                           this.confirm(var13, "nudge");
                           return;
                        }

                        if (this.autoAsk && var13 - this.since >= 3000L) {
                           this.confirm(var13, "auto");
                           return;
                        }

                        if (var13 - this.lastBeep >= 2000L) {
                           this.beepNow = true;
                           this.lastBeep = var13;
                        }

                        Unit var26 = Unit.INSTANCE;
                        break;
                     case 4:
                        if (!var1.getEngaged() || var1.getLaneChanging() || var1.getBrake() || var1.getVEgo() < var1.getMinLcSpeed()) {
                           break label552;
                        }

                        boolean var37;
                        if (this.dir < 0) {
                           var37 = var1.getRightBlinker();
                        } else {
                           var37 = var1.getLeftBlinker();
                        }

                        if (var37) {
                           break label552;
                        }

                        long var15 = this.waitSince;
                        long var42;
                        if (Intrinsics.areEqual(this.kind, "model")) {
                           var42 = 30000L;
                        } else {
                           var42 = 60000L;
                        }

                        if (var13 - var15 > var42) {
                           this.state = LaneSuggest.State.IDLE;
                           this.cool[this.idx(this.dir)] = 30000 + var13;
                           return;
                        }

                        if (Intrinsics.areEqual(this.kind, "pass") && this.passLostSince != 0L && var13 - this.passLostSince >= 2000L) {
                           this.state = LaneSuggest.State.IDLE;
                           this.cool[0] = 20000 + var13;
                           return;
                        }

                        if (Intrinsics.areEqual(this.kind, "return") && this.owedRight == 0) {
                           this.state = LaneSuggest.State.IDLE;
                           return;
                        }

                        if (Intrinsics.areEqual(this.kind, "exit") && this.exitGone(var1)) {
                           this.state = LaneSuggest.State.IDLE;
                           this.exitDoneKey = this.exitKey;
                           return;
                        }

                        if (this.routeGone(var13)) {
                           this.state = LaneSuggest.State.IDLE;
                           this.cool[this.idx(this.dir)] = 20000 + var13;
                           return;
                        }

                        if (!this.armed && this.nudged(var1, var13)) {
                           this.armed = true;
                           this.confirmedBy = "nudge";
                        }

                        if (this.dir < 0) {
                           var37 = var1.getLeftBsm();
                        } else {
                           var37 = var1.getRightBsm();
                        }

                        if (var37) {
                           this.clearSince = 0L;
                        } else {
                           if (this.clearSince == 0L) {
                              this.clearSince = var13;
                           }

                           if (var13 - this.clearSince >= 1000L) {
                              if (this.armed) {
                                 this.state = LaneSuggest.State.SIGNALING;
                                 this.since = var13;
                                 this.holdSide = this.dir;
                              } else {
                                 this.state = LaneSuggest.State.ASKING;
                                 this.since = var13;
                                 this.lastBeep = 0L;
                                 this.reasked = true;
                              }
                           }
                        }

                        Unit var25 = Unit.INSTANCE;
                        break;
                     case 5:
                        if (this.test) {
                           this.holdSide = this.dir;
                           if (var13 - this.since > 8000L) {
                              this.finish("test done - the blinker was held 8 seconds. openpilot only changes lanes while driving", var13);
                              this.test = false;
                           }

                           return;
                        }

                        boolean var9;
                        if (this.dir < 0) {
                           var9 = var1.getLeftBsm();
                        } else {
                           var9 = var1.getRightBsm();
                        }

                        if (var9 && !var1.getLaneChanging() && var1.getEngaged() && !var1.getBrake()) {
                           this.state = LaneSuggest.State.WAITING;
                           if (this.firstWait == 0L) {
                              this.firstWait = var13;
                           }

                           this.waitSince = this.firstWait;
                           this.clearSince = 0L;
                           this.armed = true;
                           this.holdSide = 0;
                           return;
                        }

                        String var46;
                        if (!var1.getEngaged()) {
                           var46 = "openpilot disengaged - stopped";
                        } else if (var1.getBrake()) {
                           var46 = "brake pressed - stopped";
                        } else if (var1.getVEgo() < var1.getMinLcSpeed()) {
                           var46 = "too slow for a lane change - stopped";
                        } else {
                           boolean var10;
                           if (this.dir < 0) {
                              var10 = var1.getRightBlinker();
                           } else {
                              var10 = var1.getLeftBlinker();
                           }

                           if (var10) {
                              var46 = "other blinker on - stopped";
                           } else if (Intrinsics.areEqual(this.kind, "exit") && this.exitGone(var1)) {
                              this.exitDoneKey = this.exitKey;
                              var46 = "the exit is behind us - not taken";
                           } else if (!var1.getLaneChanging() && this.routeGone(var13)) {
                              this.cool[this.idx(this.dir)] = 20000 + var13;
                              var46 = "already in a lane for the route - stopped";
                           } else if (Intrinsics.areEqual(this.kind, "exit") && var13 - this.since > 6000L) {
                              if (this.exitTries < 5) {
                                 this.exitRetryAt = 1500L + var13;
                                 var46 = "openpilot hasn't moved over yet - signaling again";
                              } else {
                                 this.exitDoneKey = this.exitKey;
                                 var46 = "openpilot didn't take the exit - take it yourself";
                              }
                           } else if (var13 - this.since > 8000L) {
                              if (var9) {
                                 String var45 = this.side(this.dir).toLowerCase(Locale.ROOT);
                                 Intrinsics.checkNotNullExpressionValue(var45, "toLowerCase(...)");
                                 var46 = "a car stayed in the " + var45 + " blind spot - not changed";
                              } else if (var1.getCommaConfirms()) {
                                 var46 = "openpilot didn't start it (lane too narrow or lane changes off)";
                              } else {
                                 var46 = "openpilot didn't start it (lane too narrow, lane changes off, or it wants a nudge)";
                              }
                           } else {
                              var46 = var47;
                           }
                        }

                        if (var46 != null) {
                           this.finish(var46, var13);
                           return;
                        }

                        this.holdSide = this.dir;
                        if (var1.getLaneChanging() && var1.getLaneChangeDir() == this.dir) {
                           this.state = LaneSuggest.State.CHANGING;
                           this.since = var13;
                           this.holdSide = 0;
                        }

                        Unit var24 = Unit.INSTANCE;
                        break;
                     case 6:
                        this.holdSide = 0;
                        if (!var1.getLaneChanging()) {
                           if (Intrinsics.areEqual(this.kind, "exit")) {
                              this.exitDoneKey = this.exitKey;
                              this.exitRetryAt = 0L;
                           }

                           String var20;
                           if (Intrinsics.areEqual(this.kind, "exit")) {
                              var20 = "moved over for the exit";
                           } else {
                              String var21 = this.side(this.dir).toLowerCase(Locale.ROOT);
                              Intrinsics.checkNotNullExpressionValue(var21, "toLowerCase(...)");
                              var20 = "changed lanes " + var21;
                           }

                           this.finish(var20, var13);
                           long[] var22 = this.cool;
                           var6 = this.idx(this.dir);
                           if (Intrinsics.areEqual(this.kind, "pass")) {
                              var28 = (char)45000;
                           } else {
                              var28 = (char)10000;
                           }

                           var22[var6] = var28 + var13;
                        } else if (var13 - this.since > 15000L) {
                           this.finish("lane change took too long", var13);
                        }

                        Unit var23 = Unit.INSTANCE;
                        break;
                     default:
                        throw new NoWhenBranchMatchedException();
                  }

                  return;
               }

               this.state = LaneSuggest.State.IDLE;
               this.cool[this.idx(this.dir)] = 15000 + var13;
               return;
            }

            this.state = LaneSuggest.State.IDLE;
            this.cool[this.idx(this.dir)] = 15000 + var13;
            return;
         }

         int var7 = new int[]{-1, 1}[var5];
         if (var7 >= 0) {
            var6 = 4;
         }

         float var3;
         if (var17 != null && var17.length >= 32) {
            var3 = Math.max(var17[var6 + 8], var17[var6 + 16]);
         } else {
            var3 = 0.0F;
         }

         float var4 = (float)Math.exp(-var2 / 0.5F);
         float[] var18 = this.ema;
         var6 = this.idx(var7);
         var18[var6] += (var3 - this.ema[this.idx(var7)]) * (1.0F - var4);
         if (this.ema[this.idx(var7)] > 0.6F) {
            if (this.highSince[this.idx(var7)] == 0L) {
               this.highSince[this.idx(var7)] = var13;
            }
         } else {
            this.highSince[this.idx(var7)] = 0L;
         }

         var5++;
      }
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      // $VF: synthetic method
      public static Nav.Lane ourLaneAt$default(
         LaneSuggest.Companion var0, List var1, LaneBrain var2, int var3, double var4, boolean var6, int var7, Object var8
      ) {
         if ((var7 & 8) != 0) {
            var4 = 0.6;
         }

         if ((var7 & 16) != 0) {
            var6 = false;
         }

         return var0.ourLaneAt(var1, var2, var3, var4, var6);
      }

      // $VF: synthetic method
      public static Pair routeNeed$default(LaneSuggest.Companion var0, JSONObject var1, LaneBrain var2, boolean var3, List var4, int var5, Object var6) {
         if ((var5 & 8) != 0) {
            var4 = CollectionsKt.emptyList();
         }

         return var0.routeNeed(var1, var2, var3, var4);
      }

      public final boolean arrowToward(Nav.Lane var1, int var2) {
         Intrinsics.checkNotNullParameter(var1, "l");
         boolean var6 = var1.getArrows().isEmpty();
         boolean var5 = true;
         boolean var4 = var5;
         if (!var6) {
            var var9 = var1.getArrows();
            boolean var11;
            if (var9 instanceof Collection && ((Collection)var9).isEmpty()) {
               var11 = false;
            } else {
               Iterator var10 = var9.iterator();

               while (true) {
                  if (!var10.hasNext()) {
                     var11 = false;
                     break;
                  }

                  boolean var3;
                  label47: {
                     String var7 = (String)var10.next();
                     if (!Intrinsics.areEqual(var7, "none")) {
                        CharSequence var8 = var7;
                        if (var2 > 0) {
                           var4 = StringsKt.contains$default(var8, "right", false, 2, null);
                        } else if (!StringsKt.contains$default(var8, "left", false, 2, null) && !StringsKt.contains$default(var7, "uturn", false, 2, null)) {
                           var4 = false;
                        } else {
                           var4 = true;
                        }

                        if (!var4) {
                           var3 = false;
                           break label47;
                        }
                     }

                     var3 = true;
                  }

                  if (var3) {
                     var11 = true;
                     break;
                  }
               }
            }

            if (var11) {
               var4 = var5;
            } else {
               var4 = false;
            }
         }

         return var4;
      }

      public final LaneSuggest.Companion.Exit exitNeed(JSONObject var1, boolean var2) {
         if (var1 != null) {
            boolean var7 = false;
            if (var1.optBoolean("valid", false)) {
               String var12 = var1.optString("maneuverType");
               String var13 = var1.optString("maneuverModifier");
               boolean var8 = Intrinsics.areEqual(var12, "off ramp");
               String var9 = "fork";
               if (!var8 && !Intrinsics.areEqual(var12, "fork")) {
                  return null;
               }

               if (var13 != null) {
                  String var11;
                  int var17;
                  label74: {
                     label73: {
                        var17 = var13.hashCode();
                        var11 = "right";
                        switch (var17) {
                           case -1572118767:
                              if (!var13.equals("sharpLeft")) {
                                 return null;
                              }
                              break;
                           case -1485380526:
                              if (!var13.equals("sharpRight")) {
                                 return null;
                              }
                              break label73;
                           case -711874727:
                              if (!var13.equals("slightRight")) {
                                 return null;
                              }
                              break label73;
                           case -438788310:
                              if (!var13.equals("slightLeft")) {
                                 return null;
                              }
                              break;
                           case 3317767:
                              if (!var13.equals("left")) {
                                 return null;
                              }
                              break;
                           case 108511772:
                              if (!var13.equals("right")) {
                                 return null;
                              }
                              break label73;
                           default:
                              return null;
                        }

                        var17 = -1;
                        break label74;
                     }

                     var17 = 1;
                  }

                  double var3 = var1.optDouble("maneuverDistance", Double.NaN);
                  int var6 = var7;
                  if (!Double.isInfinite(var3)) {
                     var6 = var7;
                     if (!Double.isNaN(var3)) {
                        var6 = 1;
                     }
                  }

                  if (!var6) {
                     return null;
                  }

                  String var14 = var1.optString("maneuverPrimaryText");
                  String var16 = var9;
                  if (Intrinsics.areEqual(var12, "off ramp")) {
                     var16 = "exit";
                  }

                  StringBuilder var10;
                  if (var2) {
                     var6 = (int)(var3 / 10);
                     var10 = new StringBuilder().append(var6 * 10);
                     var9 = " m";
                  } else {
                     var6 = (int)(3.28084 * var3 / 50);
                     var10 = new StringBuilder().append(var6 * 50);
                     var9 = " ft";
                  }

                  String var15 = var10.append(var9).toString();
                  var9 = var11;
                  if (var17 < 0) {
                     var9 = "left";
                  }

                  Intrinsics.checkNotNull(var14);
                  String var22;
                  if (!StringsKt.isBlank(var14)) {
                     var22 = " - " + var14;
                  } else {
                     var22 = "";
                  }

                  return new LaneSuggest.Companion.Exit(var17, var3, var16 + " on the " + var9 + " in " + var15 + var22, var12 + "|" + var13 + "|" + var14);
               }

               return null;
            }
         }

         return null;
      }

      public final boolean inLaneFor(LaneSuggest.Companion.Exit var1, LaneBrain var2, List<Nav.Lane> var3) {
         Intrinsics.checkNotNullParameter(var1, "ex");
         Intrinsics.checkNotNullParameter(var2, "brain");
         Intrinsics.checkNotNullParameter(var3, "lanes");
         int var5 = var2.inExtra();
         int var4 = var1.getDir();
         boolean var6 = true;
         if (var5 == var4) {
            return true;
         } else {
            Nav.Lane var8 = ourLaneAt$default(this, var3, var2, var1.getDir(), 0.0, false, 24, null);
            if (var8 != null) {
               return var8.getOk();
            } else {
               Pair var7 = var2.sideLanes(var1.getDir());
               var4 = ((Number)var7.component1()).intValue();
               if (((Number)var7.component2()).doubleValue() < 0.6) {
                  return false;
               } else {
                  if (!Intrinsics.areEqual(var1.getType(), "fork") || var4 != 0) {
                     var6 = false;
                  }

                  return var6;
               }
            }
         }
      }

      public final boolean notTurnLane(JSONObject var1, LaneBrain var2, Pair<Nav.Step, Double> var3) {
         Intrinsics.checkNotNullParameter(var2, "brain");
         boolean var8 = false;
         if (var1 != null && var3 != null && var1.optBoolean("valid", false)) {
            TurnSignal.Companion var10 = TurnSignal.Companion;
            String var11 = var1.optString("maneuverType");
            Intrinsics.checkNotNullExpressionValue(var11, "optString(...)");
            String var9 = var1.optString("maneuverModifier");
            Intrinsics.checkNotNullExpressionValue(var9, "optString(...)");
            int var7 = var10.turnDir(var11, var9);
            if (var7 == 0) {
               return false;
            } else {
               double var4 = var1.optDouble("maneuverDistance", Double.NaN);
               boolean var6;
               if (!Double.isInfinite(var4) && !Double.isNaN(var4)) {
                  var6 = true;
               } else {
                  var6 = false;
               }

               if (!var6 || var4 > 92.0 || Math.abs(((Number)var3.getSecond()).doubleValue() - var4) > 60.0) {
                  return false;
               } else if (var2.inExtra() == var7) {
                  return false;
               } else {
                  Nav.Lane var12 = this.ourLaneAt(((Nav.Step)var3.getFirst()).getLanes(), var2, var7, 0.8, true);
                  if (var12 == null) {
                     return false;
                  } else {
                     if (!var12.getOk() && !this.arrowToward(var12, var7)) {
                        var8 = true;
                     }

                     return var8;
                  }
               }
            }
         } else {
            return false;
         }
      }

      public final Nav.Lane ourLaneAt(List<Nav.Lane> var1, LaneBrain var2, int var3, double var4, boolean var6) {
         Intrinsics.checkNotNullParameter(var1, "lanes");
         Intrinsics.checkNotNullParameter(var2, "brain");
         int var7 = var1.size();
         if (var7 >= 2 && var3 != 0) {
            Pair var9 = var2.side(-var3);
            int var8 = ((Number)var9.component1()).intValue();
            if (((Number)var9.component2()).doubleValue() >= var4 && var8 < var7) {
               Object var11;
               if (var3 > 0) {
                  var11 = var1.get(var8);
               } else {
                  var11 = var1.get(var7 - 1 - var8);
               }

               return (Nav.Lane)var11;
            } else if (var6) {
               return null;
            } else {
               Pair var12 = var2.side(var3);
               var8 = ((Number)var12.component1()).intValue();
               if (((Number)var12.component2()).doubleValue() >= var4 && var8 < var7) {
                  Object var10;
                  if (var3 > 0) {
                     var10 = var1.get(var7 - 1 - var8);
                  } else {
                     var10 = var1.get(var8);
                  }

                  return (Nav.Lane)var10;
               } else {
                  return null;
               }
            }
         } else {
            return null;
         }
      }

      public final Pair<Integer, String> routeNeed(JSONObject var1, LaneBrain var2, boolean var3, List<Nav.Lane> var4) {
         Intrinsics.checkNotNullParameter(var2, "tr");
         Intrinsics.checkNotNullParameter(var4, "lanes");
         String var12 = "";
         Integer var14 = 0;
         if (var1 != null && var1.optBoolean("valid", false)) {
            String var13 = var1.optString("maneuverType");
            String var15 = var1.optString("maneuverModifier");
            if (!Intrinsics.areEqual(var13, "off ramp") && !Intrinsics.areEqual(var13, "fork") && !Intrinsics.areEqual(var13, "turn")) {
               return TuplesKt.to(var14, "");
            } else if (var15 != null) {
               byte var9;
               label112: {
                  label111: {
                     switch (var15.hashCode()) {
                        case -1572118767:
                           if (!var15.equals("sharpLeft")) {
                              return TuplesKt.to(var14, "");
                           }
                           break;
                        case -1485380526:
                           if (!var15.equals("sharpRight")) {
                              return TuplesKt.to(var14, "");
                           }
                           break label111;
                        case -711874727:
                           if (!var15.equals("slightRight")) {
                              return TuplesKt.to(var14, "");
                           }
                           break label111;
                        case -438788310:
                           if (!var15.equals("slightLeft")) {
                              return TuplesKt.to(var14, "");
                           }
                           break;
                        case 3317767:
                           if (!var15.equals("left")) {
                              return TuplesKt.to(var14, "");
                           }
                           break;
                        case 108511772:
                           if (!var15.equals("right")) {
                              return TuplesKt.to(var14, "");
                           }
                           break label111;
                        default:
                           return TuplesKt.to(var14, "");
                     }

                     var9 = -1;
                     break label112;
                  }

                  var9 = 1;
               }

               double var5 = var1.optDouble("maneuverDistance", Double.NaN);
               boolean var10;
               if (!Double.isInfinite(var5) && !Double.isNaN(var5)) {
                  var10 = 1;
               } else {
                  var10 = 0;
               }

               if (var10 && !(var5 > 1600.0) && !(var5 < 150.0)) {
                  Pair var16 = var2.sideLanes(var9);
                  int var11 = ((Number)var16.component1()).intValue();
                  double var7 = ((Number)var16.component2()).doubleValue();
                  if (var11 >= 1 && !(var7 < 0.6)) {
                     Nav.Lane var17 = ourLaneAt$default(this, var4, var2, var9, 0.0, false, 24, null);
                     if (var17 != null && var17.getOk()) {
                        var10 = (boolean)1;
                     } else {
                        var10 = (boolean)0;
                     }

                     if (var10) {
                        return TuplesKt.to(var14, "");
                     } else {
                        StringBuilder var18;
                        String var20;
                        if (var3) {
                           var5 = (int)(var5 / 100) / 10.0;
                           var18 = new StringBuilder().append(var5);
                           var20 = " km";
                        } else if (var5 < 400.0) {
                           var10 = (int)(3.28084 * var5 / 50);
                           var18 = new StringBuilder().append(var10 * 50);
                           var20 = " ft";
                        } else {
                           var5 = (int)(var5 / 1609.34 * 10) / 10.0;
                           var18 = new StringBuilder().append(var5);
                           var20 = " mi";
                        }

                        String var27 = var18.append(var20).toString();
                        String var19;
                        if (Intrinsics.areEqual(var13, "off ramp")) {
                           var19 = "exit";
                        } else if (Intrinsics.areEqual(var13, "fork")) {
                           var19 = "fork";
                        } else {
                           var19 = "turn";
                        }

                        String var21;
                        if (var9 < 0) {
                           var21 = "left";
                        } else {
                           var21 = "right";
                        }

                        String var22 = var12;
                        if (var11 > 1) {
                           var22 = "s";
                        }

                        return TuplesKt.to(Integer.valueOf(var9), var19 + " on the " + var21 + " in " + var27 + " - " + var11 + " lane" + var22 + " to go");
                     }
                  } else {
                     return TuplesKt.to(var14, "");
                  }
               } else {
                  return TuplesKt.to(var14, "");
               }
            } else {
               return TuplesKt.to(var14, "");
            }
         } else {
            return TuplesKt.to(var14, "");
         }
      }

      public final int routeSoon(JSONObject var1) {
         byte var5 = 0;
         if (var1 != null && var1.optBoolean("valid", false)) {
            String var6 = var1.optString("maneuverType");
            if (!Intrinsics.areEqual(var6, "off ramp") && !Intrinsics.areEqual(var6, "fork") && !Intrinsics.areEqual(var6, "turn")) {
               return 0;
            } else {
               double var2 = var1.optDouble("maneuverDistance", Double.NaN);
               boolean var4;
               if (!Double.isInfinite(var2) && !Double.isNaN(var2)) {
                  var4 = 1;
               } else {
                  var4 = 0;
               }

               if (var4 && !(var2 > 3200.0)) {
                  String var7 = var1.optString("maneuverModifier");
                  var4 = var5;
                  if (var7 != null) {
                     label90: {
                        switch (var7.hashCode()) {
                           case -1572118767:
                              if (!var7.equals("sharpLeft")) {
                                 return var5;
                              }
                              break;
                           case -1485380526:
                              if (!var7.equals("sharpRight")) {
                                 return var5;
                              }
                              break label90;
                           case -711874727:
                              if (!var7.equals("slightRight")) {
                                 return var5;
                              }
                              break label90;
                           case -438788310:
                              if (!var7.equals("slightLeft")) {
                                 return var5;
                              }
                              break;
                           case 3317767:
                              if (!var7.equals("left")) {
                                 return var5;
                              }
                              break;
                           case 108511772:
                              if (!var7.equals("right")) {
                                 return var5;
                              }
                              break label90;
                           default:
                              return var5;
                        }

                        var4 = -1;
                        return var4;
                     }

                     var4 = 1;
                  }

                  return var4;
               } else {
                  return 0;
               }
            }
         } else {
            return 0;
         }
      }

      public static final class Exit {
         private final int dir;
         private final double dist;
         private final String key;
         private final String why;

         public Exit(int var1, double var2, String var4, String var5) {
            Intrinsics.checkNotNullParameter(var4, "why");
            Intrinsics.checkNotNullParameter(var5, "key");
            super();
            this.dir = var1;
            this.dist = var2;
            this.why = var4;
            this.key = var5;
         }

         public final int getDir() {
            return this.dir;
         }

         public final double getDist() {
            return this.dist;
         }

         public final String getKey() {
            return this.key;
         }

         public final String getType() {
            return StringsKt.substringBefore$default(this.key, "|", null, 2, null);
         }

         public final String getWhy() {
            return this.why;
         }
      }
   }

   public static final class In {
      private final boolean auto;
      private final boolean brake;
      private final boolean busy;
      private final boolean commaConfirms;
      private final boolean engaged;
      private final int exitDir;
      private final double exitDist;
      private final boolean exitInLane;
      private final String exitKey;
      private final boolean exitOn;
      private final int exitSideLanes;
      private final String exitWhy;
      private final boolean highway;
      private final int laneChangeDir;
      private final boolean laneChanging;
      private final int lanesTotal;
      private final float leadD;
      private final boolean leadOn;
      private final float leadV;
      private final boolean leftBlinker;
      private final boolean leftBsm;
      private final int leftLanes;
      private final int mergeDir;
      private final String mergeWhy;
      private final boolean metric;
      private final float minLcSpeed;
      private final long nowMs;
      private final boolean nudgeConfirm;
      private final float nudgeTorque;
      private final boolean passOn;
      private final float[] pred;
      private final float rLeadD;
      private final boolean rLeadOn;
      private final float rLeadV;
      private final boolean returnOn;
      private final boolean rightBlinker;
      private final boolean rightBsm;
      private final int rightLanes;
      private final int routeDir;
      private final int routeSoonDir;
      private final String routeWhy;
      private final boolean steeringPressed;
      private final float steeringTorque;
      private final float vEgo;
      private final float vSet;

      public In(
         long var1,
         boolean var3,
         float var4,
         float var5,
         float[] var6,
         boolean var7,
         int var8,
         boolean var9,
         boolean var10,
         boolean var11,
         boolean var12,
         boolean var13,
         int var14,
         String var15,
         boolean var16,
         float var17,
         boolean var18,
         boolean var19,
         boolean var20,
         boolean var21,
         boolean var22,
         float var23,
         boolean var24,
         float var25,
         float var26,
         int var27,
         int var28,
         boolean var29,
         boolean var30,
         int var31,
         double var32,
         String var34,
         String var35,
         int var36,
         boolean var37,
         int var38,
         String var39,
         float var40,
         boolean var41,
         int var42,
         boolean var43,
         float var44,
         float var45,
         int var46,
         boolean var47
      ) {
         Intrinsics.checkNotNullParameter(var15, "routeWhy");
         Intrinsics.checkNotNullParameter(var34, "exitWhy");
         Intrinsics.checkNotNullParameter(var35, "exitKey");
         Intrinsics.checkNotNullParameter(var39, "mergeWhy");
         super();
         this.nowMs = var1;
         this.engaged = var3;
         this.vEgo = var4;
         this.minLcSpeed = var5;
         this.pred = var6;
         this.laneChanging = var7;
         this.laneChangeDir = var8;
         this.leftBlinker = var9;
         this.rightBlinker = var10;
         this.leftBsm = var11;
         this.rightBsm = var12;
         this.brake = var13;
         this.routeDir = var14;
         this.routeWhy = var15;
         this.steeringPressed = var16;
         this.steeringTorque = var17;
         this.nudgeConfirm = var18;
         this.auto = var19;
         this.commaConfirms = var20;
         this.busy = var21;
         this.passOn = var22;
         this.vSet = var23;
         this.leadOn = var24;
         this.leadD = var25;
         this.leadV = var26;
         this.leftLanes = var27;
         this.routeSoonDir = var28;
         this.metric = var29;
         this.exitOn = var30;
         this.exitDir = var31;
         this.exitDist = var32;
         this.exitWhy = var34;
         this.exitKey = var35;
         this.exitSideLanes = var36;
         this.exitInLane = var37;
         this.mergeDir = var38;
         this.mergeWhy = var39;
         this.nudgeTorque = var40;
         this.returnOn = var41;
         this.rightLanes = var42;
         this.rLeadOn = var43;
         this.rLeadD = var44;
         this.rLeadV = var45;
         this.lanesTotal = var46;
         this.highway = var47;
      }

      // $VF: synthetic method
      public In(
         long var1,
         boolean var3,
         float var4,
         float var5,
         float[] var6,
         boolean var7,
         int var8,
         boolean var9,
         boolean var10,
         boolean var11,
         boolean var12,
         boolean var13,
         int var14,
         String var15,
         boolean var16,
         float var17,
         boolean var18,
         boolean var19,
         boolean var20,
         boolean var21,
         boolean var22,
         float var23,
         boolean var24,
         float var25,
         float var26,
         int var27,
         int var28,
         boolean var29,
         boolean var30,
         int var31,
         double var32,
         String var34,
         String var35,
         int var36,
         boolean var37,
         int var38,
         String var39,
         float var40,
         boolean var41,
         int var42,
         boolean var43,
         float var44,
         float var45,
         int var46,
         boolean var47,
         int var48,
         int var49,
         DefaultConstructorMarker var50
      ) {
         if ((var48 & 4096) != 0) {
            var14 = 0;
         }

         if ((var48 & 8192) != 0) {
            var15 = "";
         }

         if ((var48 & 16384) != 0) {
            var16 = false;
         }

         if ((32768 & var48) != 0) {
            var17 = 0.0F;
         }

         if ((65536 & var48) != 0) {
            var18 = true;
         }

         if ((131072 & var48) != 0) {
            var19 = false;
         }

         if ((262144 & var48) != 0) {
            var20 = true;
         }

         if ((524288 & var48) != 0) {
            var21 = false;
         }

         if ((1048576 & var48) != 0) {
            var22 = false;
         }

         if ((2097152 & var48) != 0) {
            var23 = 0.0F;
         }

         if ((4194304 & var48) != 0) {
            var24 = false;
         }

         if ((8388608 & var48) != 0) {
            var25 = 0.0F;
         }

         if ((16777216 & var48) != 0) {
            var26 = 0.0F;
         }

         if ((33554432 & var48) != 0) {
            var27 = -1;
         }

         if ((67108864 & var48) != 0) {
            var28 = 0;
         }

         if ((134217728 & var48) != 0) {
            var29 = false;
         }

         if ((268435456 & var48) != 0) {
            var30 = false;
         }

         if ((536870912 & var48) != 0) {
            var31 = 0;
         }

         if ((1073741824 & var48) != 0) {
            var32 = Double.NaN;
         }

         if ((var48 & -2147483648) != 0) {
            var34 = "";
         }

         if ((var49 & 1) != 0) {
            var35 = "";
         }

         if ((var49 & 2) != 0) {
            var36 = -1;
         }

         if ((var49 & 4) != 0) {
            var37 = false;
         }

         if ((var49 & 8) != 0) {
            var38 = 0;
         }

         if ((var49 & 16) != 0) {
            var39 = "";
         }

         if ((var49 & 32) != 0) {
            var40 = 60.0F;
         }

         if ((var49 & 64) != 0) {
            var41 = false;
         }

         if ((var49 & 128) != 0) {
            var42 = -1;
         }

         if ((var49 & 256) != 0) {
            var43 = false;
         }

         if ((var49 & 512) != 0) {
            var44 = 0.0F;
         }

         if ((var49 & 1024) != 0) {
            var45 = 0.0F;
         }

         if ((var49 & 2048) != 0) {
            var46 = -1;
         }

         if ((var49 & 4096) != 0) {
            var47 = false;
         }

         this(
            var1,
            var3,
            var4,
            var5,
            var6,
            var7,
            var8,
            var9,
            var10,
            var11,
            var12,
            var13,
            var14,
            var15,
            var16,
            var17,
            var18,
            var19,
            var20,
            var21,
            var22,
            var23,
            var24,
            var25,
            var26,
            var27,
            var28,
            var29,
            var30,
            var31,
            var32,
            var34,
            var35,
            var36,
            var37,
            var38,
            var39,
            var40,
            var41,
            var42,
            var43,
            var44,
            var45,
            var46,
            var47
         );
      }

      public final boolean getAuto() {
         return this.auto;
      }

      public final boolean getBrake() {
         return this.brake;
      }

      public final boolean getBusy() {
         return this.busy;
      }

      public final boolean getCommaConfirms() {
         return this.commaConfirms;
      }

      public final boolean getEngaged() {
         return this.engaged;
      }

      public final int getExitDir() {
         return this.exitDir;
      }

      public final double getExitDist() {
         return this.exitDist;
      }

      public final boolean getExitInLane() {
         return this.exitInLane;
      }

      public final String getExitKey() {
         return this.exitKey;
      }

      public final boolean getExitOn() {
         return this.exitOn;
      }

      public final int getExitSideLanes() {
         return this.exitSideLanes;
      }

      public final String getExitWhy() {
         return this.exitWhy;
      }

      public final boolean getHighway() {
         return this.highway;
      }

      public final int getLaneChangeDir() {
         return this.laneChangeDir;
      }

      public final boolean getLaneChanging() {
         return this.laneChanging;
      }

      public final int getLanesTotal() {
         return this.lanesTotal;
      }

      public final float getLeadD() {
         return this.leadD;
      }

      public final boolean getLeadOn() {
         return this.leadOn;
      }

      public final float getLeadV() {
         return this.leadV;
      }

      public final boolean getLeftBlinker() {
         return this.leftBlinker;
      }

      public final boolean getLeftBsm() {
         return this.leftBsm;
      }

      public final int getLeftLanes() {
         return this.leftLanes;
      }

      public final int getMergeDir() {
         return this.mergeDir;
      }

      public final String getMergeWhy() {
         return this.mergeWhy;
      }

      public final boolean getMetric() {
         return this.metric;
      }

      public final float getMinLcSpeed() {
         return this.minLcSpeed;
      }

      public final long getNowMs() {
         return this.nowMs;
      }

      public final boolean getNudgeConfirm() {
         return this.nudgeConfirm;
      }

      public final float getNudgeTorque() {
         return this.nudgeTorque;
      }

      public final boolean getPassOn() {
         return this.passOn;
      }

      public final float[] getPred() {
         return this.pred;
      }

      public final float getRLeadD() {
         return this.rLeadD;
      }

      public final boolean getRLeadOn() {
         return this.rLeadOn;
      }

      public final float getRLeadV() {
         return this.rLeadV;
      }

      public final boolean getReturnOn() {
         return this.returnOn;
      }

      public final boolean getRightBlinker() {
         return this.rightBlinker;
      }

      public final boolean getRightBsm() {
         return this.rightBsm;
      }

      public final int getRightLanes() {
         return this.rightLanes;
      }

      public final int getRouteDir() {
         return this.routeDir;
      }

      public final int getRouteSoonDir() {
         return this.routeSoonDir;
      }

      public final String getRouteWhy() {
         return this.routeWhy;
      }

      public final boolean getSteeringPressed() {
         return this.steeringPressed;
      }

      public final float getSteeringTorque() {
         return this.steeringTorque;
      }

      public final float getVEgo() {
         return this.vEgo;
      }

      public final float getVSet() {
         return this.vSet;
      }
   }

   public static enum State {
      ASKING,
      CHANGING,
      IDLE,
      RESULT,
      SIGNALING,
      WAITING;
      private static final EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(LaneSuggest.State.$VALUES);
      private static final LaneSuggest.State[] $VALUES = $values();

      // $VF: synthetic method
      private static final LaneSuggest.State[] $values() {
         return new LaneSuggest.State[]{IDLE, ASKING, WAITING, SIGNALING, CHANGING, RESULT};
      }

      public static EnumEntries<LaneSuggest.State> getEntries() {
         return $ENTRIES;
      }
   }

   // $VF: synthetic class
   public final class WhenMappings {
      public static final int[] $EnumSwitchMapping$0;

      static {
         int[] var0 = new int[LaneSuggest.State.values().length];

         try {
            var0[LaneSuggest.State.IDLE.ordinal()] = 1;
         } catch (NoSuchFieldError var7) {
         }

         try {
            var0[LaneSuggest.State.RESULT.ordinal()] = 2;
         } catch (NoSuchFieldError var6) {
         }

         try {
            var0[LaneSuggest.State.ASKING.ordinal()] = 3;
         } catch (NoSuchFieldError var5) {
         }

         try {
            var0[LaneSuggest.State.WAITING.ordinal()] = 4;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[LaneSuggest.State.SIGNALING.ordinal()] = 5;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[LaneSuggest.State.CHANGING.ordinal()] = 6;
         } catch (NoSuchFieldError var2) {
         }

         $EnumSwitchMapping$0 = var0;
      }
   }
}
