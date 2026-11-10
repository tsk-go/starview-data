package com.starpilot.starview;

import java.util.Arrays;
import java.util.Locale;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.text.StringsKt;

public final class SlowReason {
   public static final float BRAKE_A = -0.45F;
   public static final SlowReason INSTANCE = new SlowReason();

   private SlowReason() {
   }

   private final String dist(float var1, boolean var2) {
      String var4;
      StringBuilder var5;
      if (var2) {
         int var3 = MathKt.roundToInt(var1);
         var5 = new StringBuilder().append(var3);
         var4 = " m";
      } else {
         int var6 = MathKt.roundToInt(3.28084F * var1);
         var5 = new StringBuilder().append(var6);
         var4 = " ft";
      }

      return var5.append(var4).toString();
   }

   private final String leadText(Lead var1, SlowReason.In var2) {
      if (!var1.getStatus()) {
         return "No lead reported";
      } else {
         float var3 = -var1.getVRel();
         String var5;
         if (var2.getVEgo() > 1.0F) {
            String var4 = String.format("%.1f", Arrays.copyOf(new Object[]{var1.getDRel() / var2.getVEgo()}, 1));
            Intrinsics.checkNotNullExpressionValue(var4, "format(...)");
            var5 = " · " + var4 + " s gap";
         } else {
            var5 = "";
         }

         String var9;
         if (var3 > 0.5F) {
            var9 = this.spd(var3, var2.getMetric());
            var9 = "closing " + var9;
         } else if (var3 < -0.5F) {
            var9 = "pulling away";
         } else {
            var9 = "same speed";
         }

         String var6 = this.dist(var1.getDRel(), var2.getMetric());
         String var7 = this.spd(var1.getVLead(), var2.getMetric());
         return var6 + " ahead · " + var9 + var5 + " · lead " + var7;
      }
   }

   private final String spd(float var1, boolean var2) {
      StringBuilder var4;
      String var5;
      if (var2) {
         int var3 = MathKt.roundToInt(3.6F * var1);
         var4 = new StringBuilder().append(var3);
         var5 = " km/h";
      } else {
         int var6 = MathKt.roundToInt(2.23694F * var1);
         var4 = new StringBuilder().append(var6);
         var5 = " mph";
      }

      return var4.append(var5).toString();
   }

   public final boolean braking(SlowReason.In var1) {
      Intrinsics.checkNotNullParameter(var1, "i");
      boolean var2;
      if (!var1.getEngaged()
         || var1.getBrakePressed()
         || var1.getGasPressed()
         || !(var1.getVEgo() > 0.5F)
         || !(var1.getATarget() <= -0.45F) && !(var1.getActAccel() <= -0.45F)) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public final SlowReason.Out explain(SlowReason.In var1) {
      Intrinsics.checkNotNullParameter(var1, "i");
      String var8 = var1.getSource().toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var8, "toLowerCase(...)");
      if (var1.getFcw()) {
         return new SlowReason.Out(SlowReason.Kind.FCW, "Forward collision warning", this.leadText(var1.getLead0(), var1));
      } else {
         Lead var7 = null;
         boolean var5 = StringsKt.startsWith$default(var8, "lead", false, 2, null);
         String var6 = "Lead car (camera only)";
         if (var5) {
            if (!Intrinsics.areEqual(var8, "lead1") && !Intrinsics.areEqual(var8, "lead2")) {
               var7 = var1.getLead0();
            } else {
               var7 = var1.getLead1();
            }

            int var24 = var7.getRadar() ^ true;
            if (var24 && var7.getModelProb() < 0.7F) {
               var24 = MathKt.roundToInt(var7.getModelProb() * 100);
               var6 = "Lead car? (camera only, " + var24 + "% sure)";
            } else if (!var24) {
               var6 = "Lead car (radar)";
            }

            return new SlowReason.Out(SlowReason.Kind.LEAD, var6, this.leadText(var7, var1));
         } else if (Intrinsics.areEqual(var8, "e2e")) {
            String var20;
            if (var1.getRedLight()) {
               var20 = "red light";
            } else if (var1.getStopSign()) {
               var20 = "stop sign";
            } else if (var1.getForcingStop()) {
               if (var1.getForcingStopLength() > 0.0F) {
                  String var21 = this.dist(var1.getForcingStopLength(), var1.getMetric());
                  var20 = "stop in " + var21;
               } else {
                  var20 = "holding at a stop";
               }
            } else if (var1.getApproachStop() > 0.0F) {
               String var22 = this.dist(var1.getApproachStop(), var1.getMetric());
               var20 = "stop ahead, " + var22;
            } else {
               var20 = var7;
            }

            SlowReason.Kind var40 = SlowReason.Kind.MODEL;
            if (var20 != null) {
               var6 = "Model sees a " + var20;
               if (var6 != null) {
                  return new SlowReason.Out(var40, "Driving model (Experimental mode)", var6);
               }
            }

            String var23 = "The model wants to slow down here - nothing else asked for it";
            return new SlowReason.Out(var40, "Driving model (Experimental mode)", var23);
         } else {
            var7 = var1.getLead0();
            if (var7.getStatus()
               && !var1.getForcingStop()
               && (var1.getTrackingLead() || var7.getDRel() < Math.max(35.0F, var1.getVEgo() * 3.0F))
               && var7.getVRel() < 1.5F) {
               if (!var7.getRadar() && var7.getModelProb() < 0.7F) {
                  int var4 = MathKt.roundToInt(var7.getModelProb() * 100);
                  var6 = "Lead car? (camera only, " + var4 + "% sure)";
               } else if (var7.getRadar()) {
                  var6 = "Lead car (radar)";
               }

               return new SlowReason.Out(SlowReason.Kind.LEAD, var6, this.leadText(var7, var1));
            } else {
               float var2 = var1.getPlanVCruise();
               SlowReason.Out var13;
               if (var1.getForcingStop()) {
                  SlowReason.Kind var34 = SlowReason.Kind.STOP;
                  if (var1.getRedLight()) {
                     var6 = "Stopping for a red light";
                  } else if (var1.getStopSign()) {
                     var6 = "Stopping for a stop sign";
                  } else {
                     var6 = "Forced stop";
                  }

                  String var12;
                  if (var1.getForcingStopLength() > 0.0F) {
                     String var11 = this.dist(var1.getForcingStopLength(), var1.getMetric());
                     var12 = "Stop in " + var11;
                  } else {
                     var12 = "Holding at the stop";
                  }

                  var13 = new SlowReason.Out(var34, var6, var12);
               } else if (var1.getApproachStop() > 0.0F) {
                  SlowReason.Kind var27 = SlowReason.Kind.STOP;
                  String var14 = this.dist(var1.getApproachStop(), var1.getMetric());
                  var13 = new SlowReason.Out(var27, "Stop ahead", "Detected stop " + var14 + " ahead");
               } else if (var1.getCscControlling() && var1.getCscSpeed() > 0.0F) {
                  SlowReason.Kind var31 = SlowReason.Kind.CURVE;
                  String var19 = this.spd(var1.getCscSpeed(), var1.getMetric());
                  var13 = new SlowReason.Out(var31, "Curve ahead", "Curve speed controller: slowing to " + var19);
               } else {
                  float var3 = var1.getSlcLimit();
                  var6 = "";
                  if (var3 > 0.0F
                     && var1.getSlcLimit() + var1.getSlcOffset() < var1.getVEgo() + 0.3F
                     && Math.abs(var2 - (var1.getSlcLimit() + var1.getSlcOffset())) < 0.6F) {
                     SlowReason.Kind var39 = SlowReason.Kind.LIMIT;
                     var8 = this.spd(var1.getSlcLimit(), var1.getMetric());
                     var8 = "Speed limit " + var8;
                     String var45 = this.spd(var1.getSlcLimit() + var1.getSlcOffset(), var1.getMetric());
                     if (!StringsKt.isBlank(var1.getSlcSource())) {
                        String var18 = var1.getSlcSource();
                        var6 = " - from " + var18;
                     }

                     var13 = new SlowReason.Out(var39, var8, "Target " + var45 + var6);
                  } else if (!var1.getPulseGlideCoasting() && !var1.getForceCoast()) {
                     if (var2 > 0.0F && var2 < var1.getVEgo() - 0.3F) {
                        SlowReason.Kind var30 = SlowReason.Kind.TARGET;
                        String var37 = this.spd(var2, var1.getMetric());
                        String var38 = "Target speed " + var37;
                        String var16;
                        if (var1.getSetSpeed() > 0.0F && Math.abs(var1.getSetSpeed() - var2) > 0.5F) {
                           String var17 = this.spd(var1.getSetSpeed(), var1.getMetric());
                           var16 = "Below your set speed " + var17;
                        } else {
                           var16 = "Your set speed";
                        }

                        var13 = new SlowReason.Out(var30, var38, var16);
                     } else {
                        SlowReason.Kind var9 = SlowReason.Kind.OTHER;
                        var8 = var1.getSource();
                        String var36;
                        if (var7.getStatus()) {
                           String var35 = this.leadText(var7, var1);
                           var36 = " · lead " + var35;
                        } else {
                           var36 = " · no lead";
                        }

                        String var10 = this.spd(Math.max(var2, 0.0F), var1.getMetric());
                        if (var1.getExperimental()) {
                           var6 = " · Experimental mode";
                        }

                        var13 = new SlowReason.Out(var9, "Planner (no reason given)", "Plan source: " + var8 + var36 + " · target " + var10 + var6);
                     }
                  } else {
                     SlowReason.Kind var29 = SlowReason.Kind.COAST;
                     String var15;
                     if (var1.getForceCoast()) {
                        var15 = "Coast is forced";
                     } else {
                        var15 = "Pulse & glide: glide phase";
                     }

                     var13 = new SlowReason.Out(var29, "Coasting", var15);
                  }
               }

               return var13;
            }
         }
      }
   }

   public static final class Event {
      private long endMs;
      private float minA;
      private SlowReason.Out reason;
      private final long startMs;
      private float vEnd;
      private final float vStart;

      public Event(long var1, long var3, float var5, float var6, float var7, SlowReason.Out var8) {
         Intrinsics.checkNotNullParameter(var8, "reason");
         super();
         this.startMs = var1;
         this.endMs = var3;
         this.vStart = var5;
         this.vEnd = var6;
         this.minA = var7;
         this.reason = var8;
      }

      public final long getEndMs() {
         return this.endMs;
      }

      public final float getMinA() {
         return this.minA;
      }

      public final SlowReason.Out getReason() {
         return this.reason;
      }

      public final long getStartMs() {
         return this.startMs;
      }

      public final float getVEnd() {
         return this.vEnd;
      }

      public final float getVStart() {
         return this.vStart;
      }

      public final void setEndMs(long var1) {
         this.endMs = var1;
      }

      public final void setMinA(float var1) {
         this.minA = var1;
      }

      public final void setReason(SlowReason.Out var1) {
         Intrinsics.checkNotNullParameter(var1, "<set-?>");
         this.reason = var1;
      }

      public final void setVEnd(float var1) {
         this.vEnd = var1;
      }
   }

   public static final class In {
      private final float aTarget;
      private final float actAccel;
      private final float approachStop;
      private final boolean brakePressed;
      private final boolean cscControlling;
      private final float cscSpeed;
      private final boolean engaged;
      private final boolean experimental;
      private final boolean fcw;
      private final boolean forceCoast;
      private final boolean forcingStop;
      private final float forcingStopLength;
      private final boolean gasPressed;
      private final Lead lead0;
      private final Lead lead1;
      private final boolean metric;
      private final float planVCruise;
      private final boolean pulseGlideCoasting;
      private final boolean redLight;
      private final float setSpeed;
      private final float slcLimit;
      private final float slcOffset;
      private final String slcSource;
      private final String source;
      private final boolean stopSign;
      private final boolean trackingLead;
      private final float vEgo;

      public In(
         boolean var1,
         float var2,
         float var3,
         float var4,
         boolean var5,
         boolean var6,
         String var7,
         boolean var8,
         Lead var9,
         Lead var10,
         boolean var11,
         boolean var12,
         float var13,
         boolean var14,
         boolean var15,
         float var16,
         boolean var17,
         float var18,
         float var19,
         float var20,
         String var21,
         float var22,
         float var23,
         boolean var24,
         boolean var25,
         boolean var26,
         boolean var27
      ) {
         Intrinsics.checkNotNullParameter(var7, "source");
         Intrinsics.checkNotNullParameter(var9, "lead0");
         Intrinsics.checkNotNullParameter(var10, "lead1");
         Intrinsics.checkNotNullParameter(var21, "slcSource");
         super();
         this.engaged = var1;
         this.vEgo = var2;
         this.aTarget = var3;
         this.actAccel = var4;
         this.brakePressed = var5;
         this.gasPressed = var6;
         this.source = var7;
         this.fcw = var8;
         this.lead0 = var9;
         this.lead1 = var10;
         this.experimental = var11;
         this.forcingStop = var12;
         this.forcingStopLength = var13;
         this.redLight = var14;
         this.stopSign = var15;
         this.approachStop = var16;
         this.cscControlling = var17;
         this.cscSpeed = var18;
         this.slcLimit = var19;
         this.slcOffset = var20;
         this.slcSource = var21;
         this.planVCruise = var22;
         this.setSpeed = var23;
         this.pulseGlideCoasting = var24;
         this.forceCoast = var25;
         this.metric = var26;
         this.trackingLead = var27;
      }

      // $VF: synthetic method
      public In(
         boolean var1,
         float var2,
         float var3,
         float var4,
         boolean var5,
         boolean var6,
         String var7,
         boolean var8,
         Lead var9,
         Lead var10,
         boolean var11,
         boolean var12,
         float var13,
         boolean var14,
         boolean var15,
         float var16,
         boolean var17,
         float var18,
         float var19,
         float var20,
         String var21,
         float var22,
         float var23,
         boolean var24,
         boolean var25,
         boolean var26,
         boolean var27,
         int var28,
         DefaultConstructorMarker var29
      ) {
         if ((var28 & 67108864) != 0) {
            var27 = false;
         }

         this(
            var1,
            var2,
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
            var27
         );
      }

      public final float getATarget() {
         return this.aTarget;
      }

      public final float getActAccel() {
         return this.actAccel;
      }

      public final float getApproachStop() {
         return this.approachStop;
      }

      public final boolean getBrakePressed() {
         return this.brakePressed;
      }

      public final boolean getCscControlling() {
         return this.cscControlling;
      }

      public final float getCscSpeed() {
         return this.cscSpeed;
      }

      public final boolean getEngaged() {
         return this.engaged;
      }

      public final boolean getExperimental() {
         return this.experimental;
      }

      public final boolean getFcw() {
         return this.fcw;
      }

      public final boolean getForceCoast() {
         return this.forceCoast;
      }

      public final boolean getForcingStop() {
         return this.forcingStop;
      }

      public final float getForcingStopLength() {
         return this.forcingStopLength;
      }

      public final boolean getGasPressed() {
         return this.gasPressed;
      }

      public final Lead getLead0() {
         return this.lead0;
      }

      public final Lead getLead1() {
         return this.lead1;
      }

      public final boolean getMetric() {
         return this.metric;
      }

      public final float getPlanVCruise() {
         return this.planVCruise;
      }

      public final boolean getPulseGlideCoasting() {
         return this.pulseGlideCoasting;
      }

      public final boolean getRedLight() {
         return this.redLight;
      }

      public final float getSetSpeed() {
         return this.setSpeed;
      }

      public final float getSlcLimit() {
         return this.slcLimit;
      }

      public final float getSlcOffset() {
         return this.slcOffset;
      }

      public final String getSlcSource() {
         return this.slcSource;
      }

      public final String getSource() {
         return this.source;
      }

      public final boolean getStopSign() {
         return this.stopSign;
      }

      public final boolean getTrackingLead() {
         return this.trackingLead;
      }

      public final float getVEgo() {
         return this.vEgo;
      }
   }

   public static enum Kind {
      COAST,
      CURVE,
      FCW,
      LEAD,
      LIMIT,
      MODEL,
      OTHER,
      STOP,
      TARGET;
      private static final EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(SlowReason.Kind.$VALUES);
      private static final SlowReason.Kind[] $VALUES = $values();

      // $VF: synthetic method
      private static final SlowReason.Kind[] $values() {
         return new SlowReason.Kind[]{FCW, LEAD, MODEL, STOP, CURVE, LIMIT, TARGET, COAST, OTHER};
      }

      public static EnumEntries<SlowReason.Kind> getEntries() {
         return $ENTRIES;
      }
   }

   public static final class Out {
      private final String detail;
      private final SlowReason.Kind kind;
      private final String title;

      public Out(SlowReason.Kind var1, String var2, String var3) {
         Intrinsics.checkNotNullParameter(var1, "kind");
         Intrinsics.checkNotNullParameter(var2, "title");
         Intrinsics.checkNotNullParameter(var3, "detail");
         super();
         this.kind = var1;
         this.title = var2;
         this.detail = var3;
      }

      public final String getDetail() {
         return this.detail;
      }

      public final SlowReason.Kind getKind() {
         return this.kind;
      }

      public final String getTitle() {
         return this.title;
      }
   }

   public static final class Tracker {
      public static final SlowReason.Tracker.Companion Companion = new SlowReason.Tracker.Companion(null);
      public static final long HOLD_MS = 4000L;
      public static final int MAX_HISTORY = 40;
      private SlowReason.Event current;
      private final ArrayDeque<SlowReason.Event> history = new ArrayDeque<>();
      private long lastBrakeMs;
      private Function1<? super SlowReason.Event, Unit> onEvent;
      private SlowReason.Out shown;

      private final void finish(SlowReason.Event var1) {
         if (var1.getEndMs() - var1.getStartMs() >= 300L) {
            this.history.addFirst(var1);

            while (this.history.size() > 40) {
               this.history.removeLast();
            }

            Function1 var2 = this.onEvent;
            if (var2 != null) {
               var2.invoke(var1);
            }
         }
      }

      public final SlowReason.Event getCurrent() {
         return this.current;
      }

      public final ArrayDeque<SlowReason.Event> getHistory() {
         return this.history;
      }

      public final Function1<SlowReason.Event, Unit> getOnEvent() {
         return this.onEvent;
      }

      public final SlowReason.Out getShown() {
         return this.shown;
      }

      public final void setOnEvent(Function1<? super SlowReason.Event, Unit> var1) {
         this.onEvent = var1;
      }

      public final void update(SlowReason.In var1, long var2) {
         Intrinsics.checkNotNullParameter(var1, "i");
         if (SlowReason.INSTANCE.braking(var1)) {
            SlowReason.Out var7 = SlowReason.INSTANCE.explain(var1);
            float var4 = Math.min(var1.getATarget(), var1.getActAccel());
            SlowReason.Event var6 = this.current;
            SlowReason.Event var5 = var6;
            if (var6 == null) {
               var5 = new SlowReason.Event(var2, var2, var1.getVEgo(), var1.getVEgo(), var4, var7);
               this.current = var5;
            }

            var5.setEndMs(var2);
            var5.setVEnd(var1.getVEgo());
            if (var4 <= var5.getMinA()) {
               var5.setMinA(var4);
               var5.setReason(var7);
            }

            this.shown = var7;
            this.lastBrakeMs = var2;
         } else {
            SlowReason.Event var8 = this.current;
            if (var8 != null && var2 - this.lastBrakeMs > 600L) {
               this.finish(var8);
               this.current = null;
            }

            if (var2 - this.lastBrakeMs > 4000L) {
               this.shown = null;
            }
         }
      }

      public static final class Companion {
         private Companion() {
         }

         // $VF: synthetic method
         public Companion(DefaultConstructorMarker var1) {
            this();
         }
      }
   }
}
