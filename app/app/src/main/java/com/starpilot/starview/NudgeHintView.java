package com.starpilot.starview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.view.View;
import java.util.Arrays;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;

public final class NudgeHintView extends View {
   private final float d;
   private boolean metric;
   private final Paint p;
   private NudgeLogic.Result result;
   private final Paint tp;

   public NudgeHintView(Context var1, AttributeSet var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      super(var1, var2);
      this.d = this.getResources().getDisplayMetrics().density;
      this.p = new Paint(1);
      Paint var3 = new Paint(1);
      var3.setColor(-1);
      var3.setTypeface(Typeface.DEFAULT_BOLD);
      this.tp = var3;
      this.result = new NudgeLogic.Result(NudgeLogic.State.NONE, false, 0.0, 0.0, null, 30, null);
   }

   // $VF: synthetic method
   public NudgeHintView(Context var1, AttributeSet var2, int var3, DefaultConstructorMarker var4) {
      if ((var3 & 2) != 0) {
         var2 = null;
      }

      this(var1, var2);
   }

   private final float dp(float var1) {
      return this.d * var1;
   }

   private final String fmt(double var1) {
      String var3;
      if (this.metric) {
         var3 = String.format("%d m", Arrays.copyOf(new Object[]{(int)var1}, 1));
      } else {
         var3 = String.format("%d ft", Arrays.copyOf(new Object[]{(int)(3.28084 * var1 / 10) * 10}, 1));
      }

      Intrinsics.checkNotNullExpressionValue(var3, "format(...)");
      return var3;
   }

   public final boolean getMetric() {
      return this.metric;
   }

   public final NudgeLogic.Result getResult() {
      return this.result;
   }

   protected void onDraw(Canvas var1) {
      Intrinsics.checkNotNullParameter(var1, "c");
      NudgeLogic.Result var10 = this.result;
      if (var10.getState() != NudgeLogic.State.NONE) {
         float var4 = this.getWidth();
         float var3 = this.getHeight();
         NudgeLogic.State var9 = var10.getState();
         Pair var18;
         switch (NudgeHintView.WhenMappings.$EnumSwitchMapping$0[var9.ordinal()]) {
            case 1:
               int var15 = Color.rgb(28, 150, 70);
               String var20;
               if (var10.getRight()) {
                  var20 = "RIGHT";
               } else {
                  var20 = "LEFT";
               }

               var18 = TuplesKt.to(var15, "Nudge " + var20 + " now");
               break;
            case 2:
               int var14 = Color.rgb(200, 60, 50);
               String var19 = var10.getWhy();
               var18 = TuplesKt.to(var14, "Wait: " + var19);
               break;
            default:
               int var7 = Color.argb(225, 210, 140, 20);
               String var17;
               if (var10.getRight()) {
                  var17 = "Exit right";
               } else {
                  var17 = "Exit left";
               }

               String var11 = this.fmt(var10.getDistanceM());
               var18 = TuplesKt.to(var7, var17 + " in " + var11 + ": get ready");
         }

         int var8 = ((Number)var18.component1()).intValue();
         String var21 = (String)var18.component2();
         int var16;
         if (var10.getState() == NudgeLogic.State.READY) {
            var16 = (int)(200 + 55 * Math.sin(System.currentTimeMillis() / 150.0));
         } else {
            var16 = 230;
         }

         this.p.setColor(var8);
         this.p.setAlpha(RangesKt.coerceIn(var16, 0, 255));
         var1.drawRoundRect(new RectF(0.0F, 0.0F, var4, var3), this.dp(18.0F), this.dp(18.0F), this.p);
         float var2;
         if (var10.getRight()) {
            var2 = var4 - this.dp(46.0F);
         } else {
            var2 = this.dp(46.0F);
         }

         float var5 = 0.42F * var3;
         float var6 = this.dp(18.0F);
         Path var27 = new Path();
         if (var10.getRight()) {
            var27.moveTo(var2 - var6, var5 - var6 * 0.6F);
            var27.lineTo(var6 * 0.2F + var2, var5 - var6 * 0.6F);
            var27.lineTo(var6 * 0.2F + var2, var5 - var6 * 1.2F);
            var27.lineTo(var6 * 1.2F + var2, var5);
            var27.lineTo(var6 * 0.2F + var2, var5 + 1.2F * var6);
            var27.lineTo(var2 + 0.2F * var6, var6 * 0.6F + var5);
            var27.lineTo(var2 - var6, var5 + 0.6F * var6);
         } else {
            var27.moveTo(var2 + var6, var5 - var6 * 0.6F);
            var27.lineTo(var2 - var6 * 0.2F, var5 - var6 * 0.6F);
            var27.lineTo(var2 - var6 * 0.2F, var5 - var6 * 1.2F);
            var27.lineTo(var2 - var6 * 1.2F, var5);
            var27.lineTo(var2 - var6 * 0.2F, var5 + 1.2F * var6);
            var27.lineTo(var2 - 0.2F * var6, var6 * 0.6F + var5);
            var27.lineTo(var2 + var6, var5 + 0.6F * var6);
         }

         var27.close();
         this.p.setColor(-1);
         this.p.setAlpha(255);
         var1.drawPath(var27, this.p);
         this.tp.setTextAlign(Align.CENTER);
         this.tp.setTextSize(this.dp(21.0F));

         while (var21.length() > 4 && this.tp.measureText(var21) > var4 - this.dp(120.0F)) {
            String var22 = StringsKt.dropLast(var21, 2);
            var21 = var22 + "…";
         }

         var2 = 2;
         var1.drawText(var21, var4 / var2, 0.47F * var3, this.tp);
         this.tp.setTextSize(this.dp(13.0F));
         this.tp.setTypeface(Typeface.DEFAULT);
         var9 = var10.getState();
         String var25;
         switch (NudgeHintView.WhenMappings.$EnumSwitchMapping$0[var9.ordinal()]) {
            case 1:
               String var26 = this.fmt(var10.getDistanceM());
               var25 = "push the wheel toward the exit: StarPilot moves over (" + var26 + " left)";
               break;
            case 2:
               var25 = "the nudge won't be taken until this clears";
               break;
            default:
               String var24 = this.fmt(var10.getWindowM());
               var25 = "the nudge window opens " + var24 + " before the split";
         }

         var1.drawText(var25, var4 / var2, 0.72F * var3, this.tp);
         this.tp.setTypeface(Typeface.DEFAULT_BOLD);
         if (var10.getState() != NudgeLogic.State.SOON && var10.getWindowM() > 0.0) {
            var2 = RangesKt.coerceIn((float)(var10.getDistanceM() / var10.getWindowM()), 0.0F, 1.0F);
            this.p.setColor(Color.argb(90, 255, 255, 255));
            var1.drawRoundRect(
               new RectF(this.dp(16.0F), var3 - this.dp(12.0F), var4 - this.dp(16.0F), var3 - this.dp(7.0F)), this.dp(3.0F), this.dp(3.0F), this.p
            );
            this.p.setColor(-1);
            var1.drawRoundRect(
               new RectF(this.dp(16.0F), var3 - this.dp(12.0F), this.dp(16.0F) + (var4 - this.dp(32.0F)) * var2, var3 - this.dp(7.0F)),
               this.dp(3.0F),
               this.dp(3.0F),
               this.p
            );
         }

         if (var10.getState() == NudgeLogic.State.READY) {
            this.postInvalidateDelayed(50L);
         }
      }
   }

   public final void setMetric(boolean var1) {
      this.metric = var1;
   }

   public final void setResult(NudgeLogic.Result var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.result = var1;
   }

   // $VF: synthetic class
   public final class WhenMappings {
      public static final int[] $EnumSwitchMapping$0;

      static {
         int[] var0 = new int[NudgeLogic.State.values().length];

         try {
            var0[NudgeLogic.State.READY.ordinal()] = 1;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[NudgeLogic.State.WAIT.ordinal()] = 2;
         } catch (NoSuchFieldError var2) {
         }

         $EnumSwitchMapping$0 = var0;
      }
   }
}
