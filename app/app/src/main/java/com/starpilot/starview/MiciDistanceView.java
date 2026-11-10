package com.starpilot.starview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.graphics.PorterDuff.Mode;
import android.util.AttributeSet;
import android.view.View;
import java.util.List;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;

public final class MiciDistanceView extends View {
   private float alphaF;
   private final float[] barGreen;
   private final float[] barOn;
   private final List<Pair<Pair<Float, Float>, Pair<Float, Float>>> barSizes;
   private final float[] barY;
   private final List<Pair<Bitmap, Bitmap>> bars;
   private final Bitmap car;
   private final Bitmap carGreen;
   private float carGreenF;
   private final Pair<Pair<Float, Float>, Pair<Float, Float>> carSize;
   private float carWhite;
   private double changedAtS;
   private boolean isDown;
   private long lastNs;
   private int lastPersonality;
   private final Paint p;
   private final PorterDuffColorFilter red;
   public Telemetry t;

   public MiciDistanceView(Context var1, AttributeSet var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      super(var1, var2);
      this.car = this.bmp(R.drawable.mici_car);
      this.carGreen = this.bmp(R.drawable.mici_car_green);
      Pair var5 = TuplesKt.to(this.bmp(R.drawable.mici_distance_1), this.bmp(R.drawable.mici_distance_1_green));
      Pair var3 = TuplesKt.to(this.bmp(R.drawable.mici_distance_2), this.bmp(R.drawable.mici_distance_2_green));
      this.bars = CollectionsKt.listOf(new Pair[]{var5, var3, TuplesKt.to(this.bmp(R.drawable.mici_distance_3), this.bmp(R.drawable.mici_distance_3_green))});
      Float var4 = 35.0F;
      this.carSize = TuplesKt.to(TuplesKt.to(var4, 27.0F), TuplesKt.to(50.0F, 42.0F));
      this.barSizes = CollectionsKt.listOf(
         new Pair[]{
            TuplesKt.to(TuplesKt.to(32.0F, 7.0F), TuplesKt.to(60.0F, var4)),
            TuplesKt.to(TuplesKt.to(40.0F, 9.0F), TuplesKt.to(68.0F, 37.0F)),
            TuplesKt.to(TuplesKt.to(48.0F, 11.0F), TuplesKt.to(76.0F, 39.0F))
         }
      );
      this.barY = new float[]{122.0F, 136.0F, 152.0F};
      this.p = new Paint(3);
      this.red = new PorterDuffColorFilter(Color.rgb(200, 32, 48), Mode.MULTIPLY);
      this.barOn = new float[3];
      this.barGreen = new float[3];
      this.lastPersonality = -1;
      this.changedAtS = -10.0;
   }

   // $VF: synthetic method
   public MiciDistanceView(Context var1, AttributeSet var2, int var3, DefaultConstructorMarker var4) {
      if ((var3 & 2) != 0) {
         var2 = null;
      }

      this(var1, var2);
   }

   private final Bitmap bmp(int var1) {
      return BitmapFactory.decodeResource(this.getResources(), var1);
   }

   private static final void onDraw$draw(
      float var0,
      float var1,
      MiciDistanceView var2,
      Canvas var3,
      float var4,
      Bitmap var5,
      Pair<Float, Float> var6,
      float var7,
      float var8,
      PorterDuffColorFilter var9
   ) {
      if (!(var8 <= 0.004F)) {
         float var11 = ((Number)var6.getFirst()).floatValue() * var0;
         float var10 = ((Number)var6.getSecond()).floatValue() * var0;
         var1 = (var7 - 126.0F) * var0 + var1;
         Paint var15 = var2.p;
         var7 = 255;
         if (var2.isDown) {
            var0 = 0.6F;
         } else {
            var0 = 1.0F;
         }

         var15.setAlpha(RangesKt.coerceIn(MathKt.roundToInt(var7 * var8 * var0), 0, 255));
         var2.p.setColorFilter((ColorFilter)var9);
         var0 = 2;
         var3.drawBitmap(var5, null, new RectF(var4 - var11 / var0, var1 - var10 / var0, var11 / var0 + var4, var10 / var0 + var1), var2.p);
         var2.p.setColorFilter(null);
      }
   }

   // $VF: synthetic method
   static void onDraw$draw$default(
      float var0,
      float var1,
      MiciDistanceView var2,
      Canvas var3,
      float var4,
      Bitmap var5,
      Pair var6,
      float var7,
      float var8,
      PorterDuffColorFilter var9,
      int var10,
      Object var11
   ) {
      if ((var10 & 512) != 0) {
         var9 = null;
      }

      onDraw$draw(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9);
   }

   private final int personalityRaw() {
      String var2 = this.getT().getPersonality();
      byte var1;
      if (Intrinsics.areEqual(var2, "aggressive")) {
         var1 = 0;
      } else if (Intrinsics.areEqual(var2, "relaxed")) {
         var1 = 2;
      } else {
         var1 = 1;
      }

      return var1;
   }

   public final Telemetry getT() {
      Telemetry var1 = this.t;
      if (var1 != null) {
         return var1;
      } else {
         Intrinsics.throwUninitializedPropertyAccessException("t");
         return null;
      }
   }

   public final boolean isDown() {
      return this.isDown;
   }

   protected void onDraw(Canvas var1) {
      Intrinsics.checkNotNullParameter(var1, "c");
      if (this.t != null) {
         long var22 = System.nanoTime();
         float var5;
         if (this.lastNs == 0L) {
            var5 = 0.05F;
         } else {
            var5 = RangesKt.coerceIn((float)(var22 - this.lastNs) / 1.0E9F, 0.001F, 0.2F);
         }

         this.lastNs = var22;
         double var2 = var22 / 1.0E9;
         boolean var20 = this.getT().getEnabled();
         float var6 = this.alphaF;
         float var4;
         if (var20 && Intrinsics.areEqual(this.getT().getAlertSize(), "none")) {
            var4 = 1.0F;
         } else {
            var4 = 0.45F;
         }

         this.alphaF = BigUiViewsKt.ease(var6, var4, var5, 0.05F);
         float var8 = Math.min((this.getWidth() - 4.0F) / 76.0F, (this.getHeight() - 4.0F) / 94.0F);
         float var9 = this.getWidth() / 2.0F;
         float var7 = this.getHeight() / 2.0F;
         boolean var21 = this.getT().getLeadOne().getStatus();
         var20 = this.getT().getLongActive();
         boolean var17 = false;
         boolean var15;
         if (!var20 && !this.getT().getEnabled()) {
            var15 = 0;
         } else {
            var15 = 1;
         }

         boolean var16;
         if (var21 && var15) {
            var16 = true;
         } else {
            var16 = false;
         }

         if (var16) {
            var4 = 0.0F;
         } else if (var21) {
            var4 = 1.0F;
         } else {
            var4 = 0.35F;
         }

         if (!var15) {
            this.carWhite = var4;
            this.carGreenF = 0.0F;
         }

         this.carWhite = BigUiViewsKt.ease(this.carWhite, var4, var5, 0.1F);
         var6 = this.carGreenF;
         if (var16) {
            var4 = 1.0F;
         } else {
            var4 = 0.0F;
         }

         this.carGreenF = BigUiViewsKt.ease(var6, var4, var5, 0.1F);
         Bitmap var24 = this.car;
         Intrinsics.checkNotNullExpressionValue(var24, "car");
         onDraw$draw$default(var8, var7, this, var1, var9, var24, this.carSize.getFirst(), 100.0F, this.carWhite * this.alphaF, null, 512, null);
         var24 = this.carGreen;
         Intrinsics.checkNotNullExpressionValue(var24, "carGreen");
         onDraw$draw$default(var8, var7, this, var1, var9, var24, this.carSize.getSecond(), 100.0F, this.carGreenF * this.alphaF, null, 512, null);
         int var18 = this.personalityRaw();
         if (this.lastPersonality >= 0 && var18 != this.lastPersonality) {
            this.changedAtS = var2;
         }

         this.lastPersonality = var18;
         if (var2 - this.changedAtS < 2.5) {
            var16 = true;
         } else {
            var16 = false;
         }

         var20 = this.getT().getTrafficMode();
         var15 = var17;
         if (this.getT().getGasPressed()) {
            var15 = var17;
            if (Intrinsics.areEqual(this.getT().getOpState(), "overriding")) {
               var15 = (boolean)1;
            }
         }

         if (var15 && var2 % 0.375 > 0.1875) {
            var4 = 0.3888889F;
         } else {
            var4 = 1.0F;
         }

         if (var20) {
            var15 = 1;
         } else {
            var15 = var18 + 1;
         }

         byte var19 = 0;

         for (int var39 = var19; var39 < 3; var39++) {
            float[] var43 = this.barOn;
            float var10 = this.barOn[var39];
            if (var39 < var15) {
               var6 = 1.0F;
            } else {
               var6 = 0.0F;
            }

            var43[var39] = BigUiViewsKt.ease(var10, var6, var5, 0.1F);
            float[] var44 = this.barGreen;
            var10 = this.barGreen[var39];
            if (var16 && var39 == var15 - 1 && !var20) {
               var6 = 1.0F;
            } else {
               var6 = 0.0F;
            }

            var44[var39] = BigUiViewsKt.ease(var10, var6, var5, 0.1F);
            float var12 = 1;
            float var13 = this.barOn[var39];
            float var14 = this.barOn[var39];
            var10 = this.barGreen[var39];
            float var11 = this.alphaF;
            var24 = this.bars.get(var39).getFirst();
            Intrinsics.checkNotNullExpressionValue(var24, "<get-first>(...)");
            Bitmap var26 = var24;
            Pair var25 = this.barSizes.get(var39).getFirst();
            var6 = this.barY[var39];
            PorterDuffColorFilter var46;
            if (var20 && var39 < var15) {
               var46 = this.red;
            } else {
               var46 = null;
            }

            onDraw$draw(var8, var7, this, var1, var9, var26, var25, var6, ((var12 - var13) * 0.35F + (var14 - var10) * 0.9F * var4) * var11, var46);
            var24 = this.bars.get(var39).getSecond();
            Intrinsics.checkNotNullExpressionValue(var24, "<get-second>(...)");
            onDraw$draw$default(
               var8,
               var7,
               this,
               var1,
               var9,
               var24,
               this.barSizes.get(var39).getSecond(),
               this.barY[var39],
               this.barGreen[var39] * var4 * this.alphaF,
               null,
               512,
               null
            );
         }
      }
   }

   public final void setDown(boolean var1) {
      this.isDown = var1;
   }

   public final void setT(Telemetry var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.t = var1;
   }
}
