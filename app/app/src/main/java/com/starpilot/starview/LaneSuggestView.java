package com.starpilot.starview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.media.ToneGenerator;
import android.os.Handler;
import android.os.Looper;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.json.JSONObject;

public final class LaneSuggestView extends View {
   public static final LaneSuggestView.Companion Companion = new LaneSuggestView.Companion(null);
   private static volatile Boolean lastCommaConfirms;
   private long askedAt;
   private final Paint bg;
   private final Paint btn;
   private volatile Boolean commaConfirms;
   private long crossAt;
   private double crossGap;
   private final float d;
   private String holdWhy;
   private int holding;
   private boolean lastArmed;
   private boolean lastLaneHold;
   private long lastSpoke;
   private LaneSuggest.State lastState;
   private TurnSignal.State lastTurn;
   private final LaneSuggest logic;
   private final Handler main;
   private boolean metric;
   private final RectF noR;
   private final RectF okR;
   private final Paint p;
   private long probedAt;
   private final Runnable repeat;
   private boolean repeated;
   private final TextPaint sub;
   private final TextPaint title;
   private ToneGenerator tone;
   private final TurnSignal turn;
   private boolean wasUp;

   // $VF: synthetic method
   public static Unit $r8$lambda$QOy89AQd7G7Szx2_9rFBcDH904w/* $VF was: $r8$lambda$QOy89AQd7G7Szx2_9rFBcDH904w*/(LaneSuggestView var0, JSONObject var1) {
      return probe$lambda$3(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$ZOZTfzxGNGGSDPMKH4GfR6PZlrQ(LaneSuggestView var0, JSONObject var1) {
      return send$lambda$9(var0, var1);
   }

   public LaneSuggestView(Context var1, AttributeSet var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      super(var1, var2);
      this.logic = new LaneSuggest();
      this.turn = new TurnSignal();
      this.d = this.getResources().getDisplayMetrics().density;
      this.bg = new Paint(1);
      this.p = new Paint(1);
      TextPaint var3 = new TextPaint(1);
      var3.setColor(-1);
      var3.setTypeface(Typeface.DEFAULT_BOLD);
      var3.setTextSize(this.d * 20.0F);
      this.title = var3;
      TextPaint var4 = new TextPaint(1);
      var4.setColor(Color.rgb(225, 230, 240));
      var4.setTextSize(this.d * 14.0F);
      this.sub = var4;
      Paint var5 = new Paint(1);
      var5.setColor(-1);
      var5.setTypeface(Typeface.DEFAULT_BOLD);
      var5.setTextSize(this.d * 17.0F);
      var5.setTextAlign(Align.CENTER);
      this.btn = var5;
      this.okR = new RectF();
      this.noR = new RectF();
      this.main = new Handler(Looper.getMainLooper());
      this.holdWhy = "";
      this.lastState = LaneSuggest.State.IDLE;
      this.lastTurn = TurnSignal.State.IDLE;
      this.crossGap = Double.NaN;
      this.repeat = new Runnable() {

         @Override
         public void run() {
            if (LaneSuggestView.this.holding != 0) {
               LaneSuggestView var2x = LaneSuggestView.this;
               String var1x;
               if (LaneSuggestView.this.holding < 0) {
                  var1x = "left";
               } else {
                  var1x = "right";
               }

               var2x.send(var1x, LaneSuggestView.this.holdWhy);
               LaneSuggestView.this.main.postDelayed(this, 250L);
            }
         }
      };
   }

   // $VF: synthetic method
   public LaneSuggestView(Context var1, AttributeSet var2, int var3, DefaultConstructorMarker var4) {
      if ((var3 & 2) != 0) {
         var2 = null;
      }

      this(var1, var2);
   }

   private final void arrow(Canvas var1, float var2, float var3, float var4, int var5) {
      Path var6 = new Path();
      if (var5 < 0) {
         var6.moveTo(var2 - var4, var3);
         var6.lineTo(var2, var3 - var4 * 0.8F);
         var6.lineTo(var2, var3 - var4 * 0.35F);
         var6.lineTo(var2 + var4, var3 - var4 * 0.35F);
         var6.lineTo(var2 + var4, var4 * 0.35F + var3);
         var6.lineTo(var2, 0.35F * var4 + var3);
         var6.lineTo(var2, 0.8F * var4 + var3);
      } else {
         var6.moveTo(var2 + var4, var3);
         var6.lineTo(var2, var3 - var4 * 0.8F);
         var6.lineTo(var2, var3 - var4 * 0.35F);
         var6.lineTo(var2 - var4, var3 - var4 * 0.35F);
         var6.lineTo(var2 - var4, var4 * 0.35F + var3);
         var6.lineTo(var2, 0.35F * var4 + var3);
         var6.lineTo(var2, 0.8F * var4 + var3);
      }

      var6.close();
      this.p.setColor(-1);
      var1.drawPath(var6, this.p);
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private final void beep() {
      ToneGenerator var2;
      try {
         var2 = this.tone;
      } catch (Exception var5) {
         return;
      }

      ToneGenerator var1 = var2;
      if (var2 == null) {
         try {
            var1 = new ToneGenerator(4, 90);
            this.tone = var1;
         } catch (Exception var4) {
            return;
         }
      }

      try {
         var1.startTone(28, 350);
      } catch (Exception var3) {
      }
   }

   private final String far(double var1) {
      boolean var3;
      if (!Double.isInfinite(var1) && !Double.isNaN(var1)) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      String var4;
      if (!var3) {
         var4 = "";
      } else if (this.metric) {
         StringBuilder var5;
         if (var1 < 1000.0) {
            var3 = (int)(var1 / 10);
            var5 = new StringBuilder().append(var3 * 10);
            var4 = " m";
         } else {
            var1 = (int)(var1 / 100) / 10.0;
            var5 = new StringBuilder().append(var1);
            var4 = " km";
         }

         var4 = var5.append(var4).toString();
      } else if (var1 < 300.0) {
         var3 = Math.max(0, (int)(3.28084 * var1 / 10) * 10);
         var4 = var3 + " ft";
      } else {
         var1 = (int)(var1 / 1609.34 * 10) / 10.0;
         var4 = var1 + " mi";
      }

      return var4;
   }

   private static final Unit probe$lambda$3(LaneSuggestView var0, JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "r");
      if (var1.optBoolean("ok")) {
         var0.setCommaConfirms(var1.optBoolean("signal", false));
      }

      return Unit.INSTANCE;
   }

   private final void say(String var1, AmySynth.Style var2, List<String> var3, long var4) {
      if (Speech.INSTANCE.getEnabled()) {
         if (Speech.INSTANCE.getUseAmy() && AmyVoice.INSTANCE.getReady()) {
            AmyVoice.INSTANCE.play(var2, var3);
         } else {
            Speech.speak$default(Speech.INSTANCE, var1, false, 2, null);
         }

         this.lastSpoke = var4;
      }
   }

   private final void send(String var1, String var2) {
      DataClient var4 = Link.INSTANCE.getData();
      if (var4 != null && var4.getConnected()) {
         boolean var3;
         if (var2.length() == 0) {
            var3 = true;
         } else {
            var3 = false;
         }

         Map var5;
         if (var3) {
            var5 = MapsKt.mapOf(TuplesKt.to("side", var1));
         } else {
            var5 = MapsKt.mapOf(new Pair[]{TuplesKt.to("side", var1), TuplesKt.to("why", var2)});
         }

         var4.request("blink", var5, new LaneSuggestView$$ExternalSyntheticLambda1(this));
      }
   }

   private static final Unit send$lambda$9(LaneSuggestView var0, JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "r");
      if (var1.optBoolean("ok")) {
         var0.setCommaConfirms(var1.optBoolean("signal", false));
      }

      return Unit.INSTANCE;
   }

   private final void setCommaConfirms(Boolean var1) {
      this.commaConfirms = var1;
      lastCommaConfirms = var1;
   }

   private final boolean turnCard() {
      boolean var1;
      if (this.logic.getState() == LaneSuggest.State.IDLE && this.turn.getState() != TurnSignal.State.IDLE) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public final double crossGapFor(JSONObject var1, long var2) {
      if (this.turn.getState() == TurnSignal.State.WAITING && var1 != null) {
         if (var2 - this.crossAt < 1000L) {
            return this.crossGap;
         } else {
            this.crossAt = var2;
            OsmRoads.Fix var14 = OsmRoads.INSTANCE.getLastFix();
            if (var14 != null && !Double.isNaN(OsmRoads.INSTANCE.getLat0()) && System.nanoTime() - var14.getTNs() <= 3000000000L) {
               float var12 = var14.getBearingDeg();
               boolean var13;
               if (!Float.isInfinite(var12) && !Float.isNaN(var12)) {
                  var13 = true;
               } else {
                  var13 = false;
               }

               if (var13 && !(var14.getSpeed() < 2.0F)) {
                  double var4 = this.turn.getDistM();
                  if (!Double.isInfinite(var4) && !Double.isNaN(var4)) {
                     var13 = true;
                  } else {
                     var13 = false;
                  }

                  if (var13 && !(var4 <= 0.0)) {
                     Pair var15 = OsmRoads.INSTANCE.toLocal(var14.getLat(), var14.getLon());
                     double var10 = ((Number)var15.component1()).doubleValue();
                     double var8 = ((Number)var15.component2()).doubleValue();
                     double var6 = Math.toRadians(var14.getBearingDeg());
                     TurnSignal.Companion var20 = TurnSignal.Companion;
                     List var21 = OsmRoads.INSTANCE.crossStreetsAhead(var10, var8, (Math.PI / 2) - var6, var4 + 20.0);
                     String var16 = var1.optString("maneuverPrimaryText");
                     Intrinsics.checkNotNullExpressionValue(var16, "optString(...)");
                     var4 = var20.lastCrossGap(var21, var4, var16);
                     if (!Double.isInfinite(var4) && !Double.isNaN(var4)) {
                        var13 = true;
                     } else {
                        var13 = false;
                     }

                     if (var13) {
                        this.crossGap = var4;
                     }

                     return this.crossGap;
                  }

                  return this.crossGap;
               }
            }

            return this.crossGap;
         }
      } else {
         if (this.turn.getState() == TurnSignal.State.IDLE) {
            this.crossGap = Double.NaN;
         }

         return this.crossGap;
      }
   }

   public final Boolean getCommaConfirms() {
      return this.commaConfirms;
   }

   public final LaneSuggest getLogic() {
      return this.logic;
   }

   public final boolean getMetric() {
      return this.metric;
   }

   public final TurnSignal getTurn() {
      return this.turn;
   }

   protected void onDetachedFromWindow() {
      super.onDetachedFromWindow();
      this.main.removeCallbacks(this.repeat);
      if (this.holding != 0) {
         this.send("off", "");
      }

      this.holding = 0;
      ToneGenerator var1 = this.tone;
      if (var1 != null) {
         var1.release();
      }

      this.tone = null;
   }

   protected void onDraw(Canvas var1) {
      Intrinsics.checkNotNullParameter(var1, "c");
      float var11 = this.getWidth();
      float var7 = this.getHeight();
      float var10 = this.d * 14.0F;
      long var16 = System.currentTimeMillis();
      boolean var14;
      if (var16 / 400 % 2 == 0L) {
         var14 = 1;
      } else {
         var14 = 0;
      }

      float var4 = var10 + this.d * 28.0F;
      float var6 = 2;
      float var8 = var7 / var6;
      float var9 = this.d * 24.0F;
      float var5 = var4 + var9 + this.d * 18.0F;
      float var13 = this.d * 104.0F;
      float var12 = var7 - var6 * var10;
      this.okR.setEmpty();
      this.noR.setEmpty();
      boolean var15 = this.turnCard();
      String var19 = "RIGHT";
      String var18 = "";
      if (var15) {
         TurnSignal.State var48 = this.turn.getState();
         Paint var49 = this.bg;
         if (var48 == TurnSignal.State.SIGNALING) {
            if (var14) {
               var14 = Color.rgb(20, 90, 160);
            } else {
               var14 = Color.rgb(14, 62, 112);
            }
         } else {
            var14 = Color.argb(220, 30, 34, 42);
         }

         var49.setColor(var14);
         var1.drawRoundRect(new RectF(0.0F, 0.0F, var11, var7), this.d * 18.0F, this.d * 18.0F, this.bg);
         this.arrow(var1, var4, var8, var9, this.turn.getDir());
         if (this.turn.getDir() < 0) {
            var19 = "LEFT";
         }

         if (var48 == TurnSignal.State.SIGNALING) {
            this.noR.set(var11 - var10 - 0.9F * var13, var10, var11 - var10, var10 + var12);
            this.p.setColor(Color.argb(160, 0, 0, 0));
            var1.drawRoundRect(this.noR, this.d * 12.0F, this.d * 12.0F, this.p);
            var1.drawText("Cancel", this.noR.centerX(), this.noR.centerY() + this.d * 6.0F, this.btn);
            var4 = this.noR.left - var5 - this.d * 10.0F;
            var1.drawText(
               TextUtils.ellipsize("Turn signal " + var19, this.title, var4, TruncateAt.END).toString(), var5, var7 / var6 - this.d * 4.0F, (Paint)this.title
            );
            double var2 = this.turn.getDistM();
            boolean var30;
            if (this.turn.getStreet().length() > 0) {
               var30 = true;
            } else {
               var30 = false;
            }

            if (var30) {
               var19 = this.turn.getStreet();
               var19 = "onto " + var19;
            } else {
               var19 = "route turn";
            }

            if (!Double.isInfinite(var2) && !Double.isNaN(var2)) {
               var30 = true;
            } else {
               var30 = false;
            }

            if (var30 && var2 > 5.0) {
               var18 = this.far(var2);
               var18 = " · " + var18;
            }

            var1.drawText(TextUtils.ellipsize(var19 + var18, this.sub, var4, TruncateAt.END).toString(), var5, var7 / var6 + this.d * 20.0F, (Paint)this.sub);
            this.postInvalidateDelayed(200L);
         } else {
            var1.drawText(
               TextUtils.ellipsize(this.turn.getResult(), this.sub, var11 - var5 - var10, TruncateAt.END).toString(),
               var5,
               var7 / var6 + this.d * 6.0F,
               (Paint)this.sub
            );
         }
      } else {
         LaneSuggest.State var21 = this.logic.getState();
         Paint var20 = this.bg;
         switch (LaneSuggestView.WhenMappings.$EnumSwitchMapping$0[var21.ordinal()]) {
            case 1:
               if (var14) {
                  var14 = Color.rgb(200, 120, 0);
               } else {
                  var14 = Color.rgb(90, 55, 0);
               }
               break;
            case 2:
               var14 = Color.rgb(20, 90, 160);
               break;
            case 3:
               var14 = Color.rgb(20, 120, 60);
               break;
            case 4:
            default:
               var14 = Color.argb(220, 30, 34, 42);
               break;
            case 5:
               var14 = Color.rgb(110, 70, 10);
         }

         var20.setColor(var14);
         var1.drawRoundRect(new RectF(0.0F, 0.0F, var11, var7), this.d * 18.0F, this.d * 18.0F, this.bg);
         this.arrow(var1, var4, var8, var9, this.logic.getDir());
         if (this.logic.getDir() < 0) {
            var19 = "LEFT";
         }

         switch (LaneSuggestView.WhenMappings.$EnumSwitchMapping$0[var21.ordinal()]) {
            case 1:
               if (this.logic.getAutoAsk()) {
                  this.noR.set(var11 - var10 - 0.9F * var13, var10, var11 - var10, var10 + var12);
                  this.p.setColor(Color.argb(160, 0, 0, 0));
                  var1.drawRoundRect(this.noR, this.d * 12.0F, this.d * 12.0F, this.p);
                  var1.drawText("No", this.noR.centerX(), this.noR.centerY() + this.d * 6.0F, this.btn);
                  var4 = this.noR.left - var5 - this.d * 10.0F;
                  var14 = (int)((this.logic.autoLeftMs(var16) + 999) / 1000);
                  var1.drawText(
                     TextUtils.ellipsize("Changing lanes " + var19 + " in " + var14 + "…", this.title, var4, TruncateAt.END).toString(),
                     var5,
                     var7 / var6 - this.d * 4.0F,
                     (Paint)this.title
                  );
               } else {
                  this.noR.set(var11 - var10 - 0.7F * var13, var10, var11 - var10, var10 + var12);
                  this.okR.set(this.noR.left - this.d * 10.0F - var13, var10, this.noR.left - this.d * 10.0F, var10 + var12);
                  this.p.setColor(Color.rgb(34, 150, 74));
                  var1.drawRoundRect(this.okR, this.d * 12.0F, this.d * 12.0F, this.p);
                  var1.drawText("Change", this.okR.centerX(), this.okR.centerY() + this.d * 6.0F, this.btn);
                  this.p.setColor(Color.argb(160, 0, 0, 0));
                  var1.drawRoundRect(this.noR, this.d * 12.0F, this.d * 12.0F, this.p);
                  var1.drawText("No", this.noR.centerX(), this.noR.centerY() + this.d * 6.0F, this.btn);
                  var4 = this.okR.left - var5 - this.d * 10.0F;
                  var1.drawText(
                     TextUtils.ellipsize("Change lanes " + var19 + "?", this.title, var4, TruncateAt.END).toString(),
                     var5,
                     var7 / var6 - this.d * 4.0F,
                     (Paint)this.title
                  );
               }

               boolean var28;
               if (this.logic.getLabel().length() > 0) {
                  var28 = true;
               } else {
                  var28 = false;
               }

               if (var28) {
                  var18 = this.logic.getLabel();
                  var18 = var18 + "  ·  ";
               }

               var19 = this.logic.getWhy();
               var1.drawText(TextUtils.ellipsize(var18 + var19, this.sub, var4, TruncateAt.END).toString(), var5, var7 / var6 + this.d * 20.0F, (Paint)this.sub);
               break;
            case 2:
               this.noR.set(var11 - var10 - 0.9F * var13, var10, var11 - var10, var10 + var12);
               this.p.setColor(Color.argb(160, 0, 0, 0));
               var1.drawRoundRect(this.noR, this.d * 12.0F, this.d * 12.0F, this.p);
               var1.drawText("Cancel", this.noR.centerX(), this.noR.centerY() + this.d * 6.0F, this.btn);
               var4 = this.noR.left - var5 - this.d * 10.0F;
               var15 = Intrinsics.areEqual(this.logic.getKind(), "exit");
               StringBuilder var47;
               if (var15) {
                  String var46;
                  if (StringsKt.startsWith$default(this.logic.getWhy(), "fork", false, 2, null)) {
                     var46 = "Fork";
                  } else {
                     var46 = "Exit";
                  }

                  var47 = new StringBuilder().append(var46).append(" ").append(var19);
                  var19 = " - signaling…";
               } else {
                  var47 = new StringBuilder().append("Signaling ").append(var19);
                  var19 = "…";
               }

               var1.drawText(
                  TextUtils.ellipsize(var47.append(var19).toString(), this.title, var4, TruncateAt.END).toString(),
                  var5,
                  var7 / var6 - this.d * 4.0F,
                  (Paint)this.title
               );
               var19 = this.logic.getConfirmedBy();
               if (Intrinsics.areEqual(var19, "nudge")) {
                  var18 = "confirmed by your nudge - ";
               } else if (Intrinsics.areEqual(var19, "auto")) {
                  var18 = "automatic - ";
               }

               if (var15) {
                  var18 = this.logic.getWhy();
               } else {
                  var18 = var18 + "waiting for openpilot to start the lane change";
               }

               var1.drawText(TextUtils.ellipsize(var18, this.sub, var4, TruncateAt.END).toString(), var5, var7 / var6 + this.d * 20.0F, (Paint)this.sub);
               break;
            case 3:
               var1.drawText("Changing lanes " + var19, var5, var7 / var6 + this.d * 8.0F, (Paint)this.title);
               break;
            case 4:
            default:
               var1.drawText(
                  TextUtils.ellipsize(this.logic.getResult(), this.sub, var11 - var5 - var10, TruncateAt.END).toString(),
                  var5,
                  var7 / var6 + this.d * 6.0F,
                  (Paint)this.sub
               );
               break;
            case 5:
               this.noR.set(var11 - var10 - 0.7F * var13, var10, var11 - var10, var10 + var12);
               this.p.setColor(Color.argb(160, 0, 0, 0));
               var1.drawRoundRect(this.noR, this.d * 12.0F, this.d * 12.0F, this.p);
               var1.drawText("No", this.noR.centerX(), this.noR.centerY() + this.d * 6.0F, this.btn);
               var4 = this.noR.left - var5 - this.d * 10.0F;
               StringBuilder var40;
               if (this.logic.getArmed()) {
                  var18 = var19.toLowerCase(Locale.ROOT);
                  Intrinsics.checkNotNullExpressionValue(var18, "toLowerCase(...)");
                  var40 = new StringBuilder().append("Car on your ").append(var18);
                  var18 = " - goes when clear";
               } else {
                  var18 = var19.toLowerCase(Locale.ROOT);
                  Intrinsics.checkNotNullExpressionValue(var18, "toLowerCase(...)");
                  var40 = new StringBuilder().append("Car on your ").append(var18);
                  var18 = " - waiting";
               }

               var1.drawText(
                  TextUtils.ellipsize(var40.append(var18).toString(), this.title, var4, TruncateAt.END).toString(),
                  var5,
                  var7 / var6 - this.d * 4.0F,
                  (Paint)this.title
               );
               if (this.logic.getArmed()) {
                  var18 = "confirmed - signals once the blind spot is clear · No cancels";
               } else {
                  var18 = "asks again once it's clear · nudge now to go when it's clear";
               }

               var1.drawText(TextUtils.ellipsize(var18, this.sub, var4, TruncateAt.END).toString(), var5, var7 / var6 + this.d * 20.0F, (Paint)this.sub);
         }

         if (var21 == LaneSuggest.State.ASKING || var21 == LaneSuggest.State.WAITING || var21 == LaneSuggest.State.SIGNALING) {
            this.postInvalidateDelayed(200L);
         }
      }
   }

   public boolean onTouchEvent(MotionEvent var1) {
      Intrinsics.checkNotNullParameter(var1, "e");
      if (var1.getActionMasked() != 1) {
         return true;
      } else {
         long var2 = System.currentTimeMillis();
         if (this.turnCard()) {
            if (this.noR.contains(var1.getX(), var1.getY())) {
               this.turn.cancel(var2);
               this.performHapticFeedback(1);
            }
         } else if (this.okR.contains(var1.getX(), var1.getY()) && this.logic.getState() == LaneSuggest.State.ASKING) {
            LaneSuggest.confirm$default(this.logic, var2, null, 2, null);
            this.performHapticFeedback(1);
         } else if (this.noR.contains(var1.getX(), var1.getY())) {
            this.logic.dismiss(var2);
            this.performHapticFeedback(1);
         }

         this.invalidate();
         return true;
      }
   }

   public final void probe(long var1) {
      DataClient var4 = Link.INSTANCE.getData();
      boolean var3;
      if (var4 != null && var4.getConnected()) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (var3 != this.wasUp) {
         this.wasUp = var3;
         if (var3) {
            this.setCommaConfirms(null);
         }
      }

      if (var3 && this.commaConfirms == null && this.holding == 0 && var1 - this.probedAt >= 10000L) {
         this.probedAt = var1;
         Intrinsics.checkNotNull(var4);
         var4.request("blink", MapsKt.mapOf(TuplesKt.to("side", "off")), new LaneSuggestView$$ExternalSyntheticLambda0(this));
      }
   }

   public final void setMetric(boolean var1) {
      this.metric = var1;
   }

   public final void stop() {
      if (this.logic.getState() == LaneSuggest.State.ASKING && Speech.INSTANCE.getUseAmy()) {
         AmyVoice.INSTANCE.stop();
      }

      this.logic.dismiss(System.currentTimeMillis());
      this.turn.reset();
      this.main.removeCallbacks(this.repeat);
      if (this.holding != 0) {
         this.send("off", "");
      }

      this.holding = 0;
      this.holdWhy = "";
      this.setVisibility(8);
   }

   public final void testAsk(String var1, String var2) {
      Intrinsics.checkNotNullParameter(var1, "side");
      Intrinsics.checkNotNullParameter(var2, "why");
      LaneSuggest var5 = this.logic;
      byte var3;
      if (Intrinsics.areEqual(var1, "right")) {
         var3 = 1;
      } else {
         var3 = -1;
      }

      CharSequence var4 = var2;
      Object var6 = var4;
      if (StringsKt.isBlank(var4)) {
         var6 = "the driving model wants to move " + var1;
      }

      var5.testAsk(var3, (String)var6, System.currentTimeMillis());
   }

   public final void tick(LaneSuggest.In var1, TurnSignal.In var2) {
      long var11;
      if (var1 != null) {
         var11 = var1.getNowMs();
      } else if (var2 != null) {
         var11 = var2.getNowMs();
      } else {
         var11 = System.currentTimeMillis();
      }

      this.probe(var11);
      LaneSuggest var13 = this.logic;
      if (var1 != null) {
         var13.update(var1);
      } else if (var13.getState() != LaneSuggest.State.IDLE && !this.logic.getTest()) {
         this.logic.reset();
      }

      if (var2 != null && Intrinsics.areEqual(this.commaConfirms, true)) {
         this.turn.update(var2);
      } else if (this.turn.getState() != TurnSignal.State.IDLE) {
         this.turn.reset();
      }

      LaneSuggest.State var59 = this.logic.getState();
      String var79;
      if (this.logic.getDir() < 0) {
         var79 = "left";
      } else {
         var79 = "right";
      }

      boolean var6;
      if (Speech.INSTANCE.getUseAmy() && AmyVoice.INSTANCE.getReady()) {
         var6 = 1;
      } else {
         var6 = 0;
      }

      LaneSuggest.State var21 = this.lastState;
      String var17 = "left";
      String var14;
      if (var59 != var21) {
         if (this.logic.getDir() < 0) {
            var14 = "LEFT";
         } else {
            var14 = "RIGHT";
         }

         switch (LaneSuggestView.WhenMappings.$EnumSwitchMapping$0[var59.ordinal()]) {
            case 1:
               if (this.lastState != LaneSuggest.State.WAITING) {
                  BlinkLog var97 = BlinkLog.INSTANCE;
                  String var35;
                  if (this.logic.getAutoAsk()) {
                     var35 = " (automatic, 3 s)";
                  } else {
                     var35 = "";
                  }

                  String var122 = this.logic.getWhy();
                  var97.add("Change lanes? " + var14 + " asked" + var35 + " - " + var122);
               }

               Unit var36 = Unit.INSTANCE;
               break;
            case 2:
               BlinkLog var16;
               label436: {
                  var16 = BlinkLog.INSTANCE;
                  String var30 = this.logic.getConfirmedBy();
                  switch (var30.hashCode()) {
                     case 114595:
                        if (var30.equals("tap")) {
                           var32 = "tapping Change";
                           break label436;
                        }
                        break;
                     case 3005871:
                        if (var30.equals("auto")) {
                           var32 = "automatic mode";
                           break label436;
                        }
                        break;
                     case 105172251:
                        if (var30.equals("nudge")) {
                           String var33 = String.format("%.0f", Arrays.copyOf(new Object[]{this.logic.getNudgeTorque()}, 1));
                           Intrinsics.checkNotNullExpressionValue(var33, "format(...)");
                           var32 = "a steering nudge (torque " + var33 + ")";
                           break label436;
                        }
                        break;
                     case 108704329:
                        if (var30.equals("route")) {
                           String var95 = this.logic.getWhy();
                           String var31;
                           if (this.logic.getExitTry() > 1) {
                              int var7 = this.logic.getExitTry();
                              var31 = ", try " + var7;
                           } else {
                              var31 = "";
                           }

                           var32 = "the route (" + var95 + var31 + ")";
                           break label436;
                        }
                  }

                  var32 = this.logic.getConfirmedBy();
               }

               String var96;
               if (this.logic.getTest()) {
                  var96 = " (parked test)";
               } else {
                  var96 = "";
               }

               var16.add("Blinker " + var14 + " ON - lane change confirmed by " + var32 + var96);
               Unit var34 = Unit.INSTANCE;
               break;
            case 3:
               BlinkLog.INSTANCE.add("Blinker off - openpilot started the lane change " + var14);
               Unit var29 = Unit.INSTANCE;
               break;
            case 4:
               BlinkLog var27 = BlinkLog.INSTANCE;
               var14 = this.logic.getResult();
               var27.add("Change lanes: " + var14);
               Unit var28 = Unit.INSTANCE;
               break;
            case 5:
               BlinkLog var15 = BlinkLog.INSTANCE;
               String var24;
               if (this.logic.getArmed()) {
                  String var23 = var14.toLowerCase(Locale.ROOT);
                  Intrinsics.checkNotNullExpressionValue(var23, "toLowerCase(...)");
                  var24 = "Blinker off - car in the " + var23 + " blind spot, goes again once it's clear";
               } else {
                  String var25 = var14.toLowerCase(Locale.ROOT);
                  Intrinsics.checkNotNullExpressionValue(var25, "toLowerCase(...)");
                  var24 = "Change lanes? " + var14 + " - car in the " + var25 + " blind spot, waiting to ask again";
               }

               var15.add(var24);
               Unit var26 = Unit.INSTANCE;
               break;
            case 6:
               if (this.lastState == LaneSuggest.State.ASKING) {
                  BlinkLog.INSTANCE.add("Change lanes? " + var14 + " - not answered / No");
               } else if (this.lastState == LaneSuggest.State.WAITING) {
                  BlinkLog.INSTANCE.add("Change lanes? " + var14 + " - stopped waiting (No, gave up, or no longer needed)");
               }

               Unit var22 = Unit.INSTANCE;
               break;
            default:
               throw new NoWhenBranchMatchedException();
         }

         String var37;
         switch (LaneSuggestView.WhenMappings.$EnumSwitchMapping$0[var59.ordinal()]) {
            case 1:
               this.askedAt = var11;
               this.repeated = false;
               boolean var8 = StringsKt.startsWith$default(this.logic.getWhy(), "the driving model", false, 2, null) ^ true;
               if (this.lastState == LaneSuggest.State.WAITING) {
                  BlinkLog var85 = BlinkLog.INSTANCE;
                  String var48;
                  if (this.logic.getDir() < 0) {
                     var48 = "LEFT";
                  } else {
                     var48 = "RIGHT";
                  }

                  var85.add("Change lanes? " + var48 + " asked again - the blind spot is clear");
                  this.say("Clear now. Change lanes " + var79 + "?", AmySynth.Style.ASK, CollectionsKt.listOf("ask_" + var79), var11);
                  var37 = "ask_";
               } else {
                  var37 = "ask_";
                  if (this.logic.getAutoAsk()) {
                     Iterable var125 = AmyPrompts.INSTANCE.ask(this.logic.getDir(), this.logic.getWhy());
                     boolean var10 = false;
                     var var142 = new ArrayList();
                     boolean var9 = false;

                     for (Object var19 : var125) {
                        String var20 = (String)var19;
                        boolean var71;
                        if (!StringsKt.startsWith$default(var20, var37, false, 2, null) && !StringsKt.startsWith$default(var20, "lanes", false, 2, null)) {
                           var71 = true;
                        } else {
                           var71 = false;
                        }

                        if (var71) {
                           var142.add(var19);
                        }
                     }

                     List var87 = var142;
                     List var126 = CollectionsKt.plus(var87, "changing");
                     if (var8) {
                        String var100 = StringsKt.substringBefore$default(Speech.INSTANCE.spoken(this.logic.getWhy()), " - ", null, 2, null);
                        boolean var72;
                        if (var100.length() > 0) {
                           var72 = true;
                        } else {
                           var72 = false;
                        }

                        var14 = var100;
                        if (var72) {
                           StringBuilder var89 = new StringBuilder();
                           String var143 = String.valueOf(var100.charAt(0));
                           Intrinsics.checkNotNull(var143, "null cannot be cast to non-null type java.lang.String");
                           String var144 = var143.toUpperCase(Locale.ROOT);
                           Intrinsics.checkNotNullExpressionValue(var144, "toUpperCase(...)");
                           StringBuilder var90 = var89.append(var144);
                           var100 = var100.substring(1);
                           Intrinsics.checkNotNullExpressionValue(var100, "substring(...)");
                           var14 = var90.append(var100).toString();
                        }

                        var14 = var14 + ". ";
                     } else {
                        var14 = "";
                     }

                     this.say(var14 + "Changing lanes " + var79 + ".", AmySynth.Style.ASK, var126, var11);
                  } else if (Intrinsics.areEqual(this.logic.getKind(), "pass")) {
                     String var127 = "Slower car ahead. Change lanes " + var79 + "?";
                     AmySynth.Style var92 = AmySynth.Style.ASK;
                     List var102 = CollectionsKt.listOf("ask_" + var79);
                     var37 = "ask_";
                     this.say(var127, var92, var102, var11);
                  } else {
                     var14 = "ask_";
                     StringBuilder var49;
                     if (!var8) {
                        var49 = new StringBuilder().append("Change lanes ");
                     } else {
                        String var103 = Speech.INSTANCE.spoken(this.logic.getWhy());
                        boolean var73;
                        if (var103.length() > 0) {
                           var73 = true;
                        } else {
                           var73 = false;
                        }

                        String var50 = var103;
                        if (var73) {
                           StringBuilder var51 = new StringBuilder();
                           String var128 = String.valueOf(var103.charAt(0));
                           Intrinsics.checkNotNull(var128, "null cannot be cast to non-null type java.lang.String");
                           var128 = var128.toUpperCase(Locale.ROOT);
                           Intrinsics.checkNotNullExpressionValue(var128, "toUpperCase(...)");
                           StringBuilder var52 = var51.append(var128);
                           var103 = var103.substring(1);
                           Intrinsics.checkNotNullExpressionValue(var103, "substring(...)");
                           var50 = var52.append(var103).toString();
                        }

                        var49 = new StringBuilder().append(var50).append(". Change lanes ");
                     }

                     String var130 = var49.append(var79).append("?").toString();
                     AmySynth.Style var105 = AmySynth.Style.ASK;
                     List var53 = AmyPrompts.INSTANCE.ask(this.logic.getDir(), this.logic.getWhy());
                     this.say(var130, var105, var53, var11);
                     var37 = var14;
                  }
               }

               Unit var94 = Unit.INSTANCE;
               break;
            case 2:
               if (!Intrinsics.areEqual(this.logic.getKind(), "exit")) {
                  this.say("Signaling " + var79 + ".", AmySynth.Style.SHORT, CollectionsKt.listOf("sig_" + var79), var11);
               } else if (this.logic.getExitTry() == 1) {
                  String var46;
                  if (StringsKt.startsWith$default(this.logic.getWhy(), "fork", false, 2, null)) {
                     var46 = "Fork";
                  } else {
                     var46 = "Exit";
                  }

                  this.say(var46 + " on the " + var79 + ". Signaling.", AmySynth.Style.SHORT, CollectionsKt.listOf("sig_" + var79), var11);
               }

               Unit var47 = Unit.INSTANCE;
               var37 = "ask_";
               break;
            case 3:
               if (!Intrinsics.areEqual(this.logic.getConfirmedBy(), "auto")) {
                  this.say("Changing lanes.", AmySynth.Style.SHORT, CollectionsKt.listOf("changing"), var11);
               }

               Unit var45 = Unit.INSTANCE;
               var37 = "ask_";
               break;
            case 4:
               if (!StringsKt.contains$default(this.logic.getResult(), "signaling again", false, 2, null)) {
                  Pair var40 = AmyPrompts.INSTANCE.result(this.logic.getResult(), this.logic.getDir());
                  AmySynth.Style var84 = (AmySynth.Style)var40.component1();
                  String var98 = (String)var40.component2();
                  String var41;
                  if (StringsKt.startsWith$default(this.logic.getResult(), "changed lanes", false, 2, null)) {
                     var41 = "Done.";
                  } else {
                     String var42 = StringsKt.replace$default(this.logic.getResult(), " - ", ". ", false, 4, null);
                     boolean var70;
                     if (var42.length() > 0) {
                        var70 = true;
                     } else {
                        var70 = false;
                     }

                     if (var70) {
                        StringBuilder var123 = new StringBuilder();
                        char var3 = var42.charAt(0);
                        String var18 = String.valueOf(var3);
                        Intrinsics.checkNotNull(var18, "null cannot be cast to non-null type java.lang.String");
                        var18 = var18.toUpperCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(var18, "toUpperCase(...)");
                        var123 = var123.append(var18);
                        String var43 = var42.substring(1);
                        Intrinsics.checkNotNullExpressionValue(var43, "substring(...)");
                        var42 = var123.append(var43).toString();
                     }

                     var41 = var42 + ".";
                  }

                  List var99 = CollectionsKt.listOf(var98);
                  this.say(var41, var84, var99, var11);
               }

               Unit var44 = Unit.INSTANCE;
               var37 = "ask_";
               break;
            case 5:
               if (var6) {
                  AmyVoice.INSTANCE.ping();
               } else {
                  StringBuilder var38;
                  if (this.logic.getArmed()) {
                     var38 = new StringBuilder().append("Car on your ").append(var79);
                     var14 = ". Waiting.";
                  } else {
                     var38 = new StringBuilder().append("Car on your ").append(var79);
                     var14 = ". I'll ask again.";
                  }

                  this.say(var38.append(var14).toString(), AmySynth.Style.SHORT, CollectionsKt.emptyList(), var11);
               }

               Unit var39 = Unit.INSTANCE;
               var37 = "ask_";
               break;
            default:
               var37 = "ask_";
               if (this.lastState == LaneSuggest.State.ASKING && var6) {
                  AmyVoice.INSTANCE.stop();
               }

               Unit var82 = Unit.INSTANCE;
         }

         LaneSuggest.State var106 = LaneSuggest.State.SIGNALING;
         if (var59 == var106 && Intrinsics.areEqual(this.logic.getConfirmedBy(), "nudge")) {
            this.performHapticFeedback(16);
         }

         this.lastState = var59;
         var14 = var37;
      } else {
         var14 = "ask_";
      }

      TurnSignal.State var145 = this.turn.getState();
      if (this.turn.getLaneHold() != this.lastLaneHold) {
         this.lastLaneHold = this.turn.getLaneHold();
         if (this.turn.getLaneHold() && this.turn.getState() == TurnSignal.State.WAITING) {
            BlinkLog var107 = BlinkLog.INSTANCE;
            String var60;
            if (this.turn.getDir() < 0) {
               var60 = "left";
            } else {
               var60 = "right";
            }

            var107.add("Turn signal held off - not in a lane that turns " + var60 + " (route lanes + lane card)");
         }
      }

      if (var145 != this.lastTurn) {
         String var61;
         if (this.turn.getDir() < 0) {
            var61 = var17;
         } else {
            var61 = "right";
         }

         switch (LaneSuggestView.WhenMappings.$EnumSwitchMapping$1[var145.ordinal()]) {
            case 1:
               BlinkLog var146 = BlinkLog.INSTANCE;
               var17 = var61.toUpperCase(Locale.ROOT);
               Intrinsics.checkNotNullExpressionValue(var17, "toUpperCase(...)");
               boolean var74;
               if (this.turn.getStreet().length() > 0) {
                  var74 = true;
               } else {
                  var74 = false;
               }

               String var114;
               if (var74) {
                  var114 = this.turn.getStreet();
                  var114 = " onto " + var114;
               } else {
                  var114 = "";
               }

               String var147 = this.far(this.turn.getStartedAtM());
               double var4 = this.turn.getGap();
               if (!Double.isInfinite(var4) && !Double.isNaN(var4)) {
                  var74 = true;
               } else {
                  var74 = false;
               }

               String var134;
               if (var74) {
                  var134 = this.far(this.turn.getGap());
                  var134 = " (last cross street " + var134 + " before the turn)";
               } else {
                  var134 = "";
               }

               var146.add("Blinker " + var17 + " ON - route turn" + var114 + ", " + var147 + " before it" + var134);
               Unit var115 = Unit.INSTANCE;
               break;
            case 2:
               BlinkLog var111 = BlinkLog.INSTANCE;
               String var132 = this.turn.getResult();
               var111.add("Turn signal off - " + var132);
               Unit var112 = Unit.INSTANCE;
               break;
            case 3:
               if (this.lastTurn == TurnSignal.State.SIGNALING) {
                  BlinkLog var131 = BlinkLog.INSTANCE;
                  String var109 = this.turn.getResult();
                  var131.add("Turn signal off - " + var109);
               }

               Unit var110 = Unit.INSTANCE;
               break;
            default:
               Unit var108 = Unit.INSTANCE;
         }

         switch (LaneSuggestView.WhenMappings.$EnumSwitchMapping$1[var145.ordinal()]) {
            case 1:
               double var66 = this.turn.getStartedAtM();
               boolean var77;
               if (120.0 <= var66 && var66 <= 350.0) {
                  var77 = true;
               } else {
                  var77 = false;
               }

               List var120;
               if (var77) {
                  String var119 = AmyPrompts.INSTANCE.distKey(var66);
                  var120 = CollectionsKt.listOf("turn_" + var61 + "_" + var119);
               } else {
                  var120 = CollectionsKt.emptyList();
               }

               this.say("Signaling " + var61 + " for the turn.", AmySynth.Style.SHORT, CollectionsKt.plus(var120, "sig_" + var61), var11);
               Unit var64 = Unit.INSTANCE;
               break;
            case 2:
               if (!StringsKt.startsWith$default(this.turn.getResult(), "turned", false, 2, null)) {
                  String var62;
                  if (StringsKt.startsWith$default(this.turn.getResult(), "cancelled", false, 2, null)) {
                     var62 = "stop_cancelled";
                  } else if (StringsKt.startsWith$default(this.turn.getResult(), "other blinker", false, 2, null)) {
                     var62 = "stop_blinker";
                  } else {
                     var62 = "stop_generic";
                  }

                  String var135 = StringsKt.substringBefore$default(this.turn.getResult(), " - ", null, 2, null);
                  boolean var76;
                  if (var135.length() > 0) {
                     var76 = true;
                  } else {
                     var76 = false;
                  }

                  String var116 = var135;
                  if (var76) {
                     StringBuilder var117 = new StringBuilder();
                     var17 = String.valueOf(var135.charAt(0));
                     Intrinsics.checkNotNull(var17, "null cannot be cast to non-null type java.lang.String");
                     var17 = var17.toUpperCase(Locale.ROOT);
                     Intrinsics.checkNotNullExpressionValue(var17, "toUpperCase(...)");
                     StringBuilder var118 = var117.append(var17);
                     var135 = var135.substring(1);
                     Intrinsics.checkNotNullExpressionValue(var135, "substring(...)");
                     var116 = var118.append(var135).toString();
                  }

                  this.say(var116 + ".", AmySynth.Style.STOPPED, CollectionsKt.listOf(var62), var11);
               }
            default:
               Unit var63 = Unit.INSTANCE;
         }

         this.lastTurn = var145;
      }

      if (this.logic.getArmed() && !this.lastArmed && var59 == LaneSuggest.State.WAITING) {
         BlinkLog var121 = BlinkLog.INSTANCE;
         String var55;
         if (this.logic.getDir() < 0) {
            var55 = "LEFT";
         } else {
            var55 = "RIGHT";
         }

         String var137 = String.format("%.0f", Arrays.copyOf(new Object[]{this.logic.getNudgeTorque()}, 1));
         Intrinsics.checkNotNullExpressionValue(var137, "format(...)");
         var121.add("Change lanes " + var55 + " confirmed by a steering nudge (torque " + var137 + ") - signals once the blind spot is clear");
         this.performHapticFeedback(16);
      }

      this.lastArmed = this.logic.getArmed();
      if (var59 == LaneSuggest.State.ASKING
         && !this.logic.getAutoAsk()
         && !this.repeated
         && var11 - this.askedAt > 7000L
         && (!var6 || !AmyVoice.INSTANCE.busy(var11))) {
         this.repeated = true;
         this.say("Change lanes " + var79 + "?", AmySynth.Style.SHORT, CollectionsKt.listOf(var14 + var79), var11);
      }

      if (this.logic.getBeepNow()) {
         if (var6) {
            if (!AmyVoice.INSTANCE.busy(var11) && var11 - this.lastSpoke > 1500L) {
               AmyVoice.INSTANCE.ping();
            }
         } else if (!Speech.INSTANCE.getEnabled() || var11 - this.lastSpoke > 2500L) {
            this.beep();
         }
      }

      if (var6 && AmyVoice.INSTANCE.busy(var11)) {
         this.lastSpoke = var11;
      }

      Pair var57;
      if (this.logic.getHoldSide() != 0) {
         var6 = this.logic.getHoldSide();
         String var56;
         if (this.logic.getTest()) {
            var56 = "";
         } else {
            var56 = "lane";
         }

         var57 = TuplesKt.to(var6, var56);
      } else if (this.turn.getHoldSide() != 0) {
         var57 = TuplesKt.to(this.turn.getHoldSide(), "turn");
      } else {
         var57 = TuplesKt.to(0, "");
      }

      int var78 = ((Number)var57.component1()).intValue();
      String var58 = (String)var57.component2();
      if (var78 != this.holding || !Intrinsics.areEqual(var58, this.holdWhy)) {
         var6 = this.holding;
         this.holding = var78;
         this.holdWhy = var58;
         this.main.removeCallbacks(this.repeat);
         if (var78 != 0) {
            this.repeat.run();
         } else if (var6 != 0) {
            this.send("off", "");
         }
      }

      byte var69;
      if (var59 != LaneSuggest.State.IDLE || var145 != TurnSignal.State.IDLE && var145 != TurnSignal.State.WAITING) {
         var69 = 0;
      } else {
         var69 = 8;
      }

      this.setVisibility(var69);
      this.invalidate();
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      public final Boolean getLastCommaConfirms() {
         return LaneSuggestView.lastCommaConfirms;
      }

      public final void setLastCommaConfirms(Boolean var1) {
         LaneSuggestView.lastCommaConfirms = var1;
      }
   }

   // $VF: synthetic class
   public final class WhenMappings {
      public static final int[] $EnumSwitchMapping$0;
      public static final int[] $EnumSwitchMapping$1;

      static {
         int[] var0 = new int[LaneSuggest.State.values().length];

         try {
            var0[LaneSuggest.State.ASKING.ordinal()] = 1;
         } catch (NoSuchFieldError var10) {
         }

         try {
            var0[LaneSuggest.State.SIGNALING.ordinal()] = 2;
         } catch (NoSuchFieldError var9) {
         }

         try {
            var0[LaneSuggest.State.CHANGING.ordinal()] = 3;
         } catch (NoSuchFieldError var8) {
         }

         try {
            var0[LaneSuggest.State.RESULT.ordinal()] = 4;
         } catch (NoSuchFieldError var7) {
         }

         try {
            var0[LaneSuggest.State.WAITING.ordinal()] = 5;
         } catch (NoSuchFieldError var6) {
         }

         try {
            var0[LaneSuggest.State.IDLE.ordinal()] = 6;
         } catch (NoSuchFieldError var5) {
         }

         $EnumSwitchMapping$0 = var0;
         var0 = new int[TurnSignal.State.values().length];

         try {
            var0[TurnSignal.State.SIGNALING.ordinal()] = 1;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[TurnSignal.State.DONE.ordinal()] = 2;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[TurnSignal.State.WAITING.ordinal()] = 3;
         } catch (NoSuchFieldError var2) {
         }

         $EnumSwitchMapping$1 = var0;
      }
   }
}
