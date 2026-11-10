package com.starpilot.starview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Paint.Align;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.view.View;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.ranges.RangesKt;

final class GxTuningChartView extends View {
   private final Paint axis;
   private final float d;
   private final Paint grid;
   private final Paint label;
   private final Path path;
   private final int rangeDigits;
   private final double rangeFloor;
   private final double rangeMult;
   private final double roundDiv;
   private final List<GxTuningSample> samples;
   private final List<Pair<String, Integer>> series;
   private final Paint stroke;
   private final String unit;

   public GxTuningChartView(
      Context var1, List<Pair<String, Integer>> var2, String var3, int var4, double var5, double var7, double var9, List<GxTuningSample> var11
   ) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "series");
      Intrinsics.checkNotNullParameter(var3, "unit");
      Intrinsics.checkNotNullParameter(var11, "samples");
      super(var1);
      this.series = var2;
      this.unit = var3;
      this.rangeDigits = var4;
      this.rangeFloor = var5;
      this.rangeMult = var7;
      this.roundDiv = var9;
      this.samples = var11;
      this.d = var1.getResources().getDisplayMetrics().density;
      Paint var12 = new Paint(1);
      var12.setStrokeWidth(this.d);
      var12.setColor(Color.argb(64, 128, 128, 128));
      this.grid = var12;
      Paint var13 = new Paint(1);
      var13.setStrokeWidth(this.d);
      var13.setColor(Color.rgb(170, 170, 170));
      this.axis = var13;
      Paint var14 = new Paint(1);
      var14.setColor(CarUi.Companion.getDIM());
      var14.setTextSize(13 * this.d);
      this.label = var14;
      Paint var15 = new Paint(1);
      var15.setStyle(Style.STROKE);
      var15.setStrokeWidth(this.d * 2.2F);
      var15.setStrokeJoin(Join.ROUND);
      var15.setStrokeCap(Cap.ROUND);
      this.stroke = var15;
      this.path = new Path();
      this.setBackgroundColor(Color.rgb(6, 8, 11));
   }

   private final String fmt(double var1) {
      StringCompanionObject var4 = StringCompanionObject.INSTANCE;
      Locale var5 = Locale.US;
      int var3 = this.rangeDigits;
      String var6 = String.format(var5, "%." + var3 + "f", Arrays.copyOf(new Object[]{var1}, 1));
      Intrinsics.checkNotNullExpressionValue(var6, "format(...)");
      return var6;
   }

   private static final float onDraw$y(float var0, double var1, double var3, double var5, float var7, double var8) {
      return (float)(var0 + (var1 - RangesKt.coerceIn(var8, var3, var1)) / var5 * (var7 - 2 * var0));
   }

   protected void onDraw(Canvas var1) {
      Intrinsics.checkNotNullParameter(var1, "c");
      super.onDraw(var1);
      float var16 = this.getWidth();
      float var14 = this.getHeight();
      float var15 = 10;
      float var17 = var15 * this.d;
      int var21 = this.samples.size();
      if (var21 <= 1) {
         this.label.setTextAlign(Align.CENTER);
         var15 = 2;
         var1.drawText("Waiting for enough live samples...", var16 / var15, var14 / var15, this.label);
      } else {
         Iterator var28 = this.samples.iterator();
         double var4 = 0.0;

         while (var28.hasNext()) {
            GxTuningSample var26 = (GxTuningSample)var28.next();
            Iterator var27 = this.series.iterator();
            double var2 = var4;

            while (true) {
               var4 = var2;
               if (!var27.hasNext()) {
                  break;
               }

               String var29 = (String)((Pair)var27.next()).component1();
               Double var58 = var26.getV().get(var29);
               if (var58 != null) {
                  var4 = var58;
               } else {
                  var4 = 0.0;
               }

               var4 = Math.abs(var4);
               if (var4 > var2) {
                  var2 = var4;
               }
            }
         }

         double var30 = Math.max(this.rangeFloor, Math.ceil(this.rangeMult * var4 * this.roundDiv) / this.roundDiv);
         var4 = var30;
         double var31 = -var30;
         double var10 = Math.max(1.0E-6, var30 - var31);
         byte var22 = 3;
         int var20 = 1;
         Iterator var50 = CollectionsKt.listOf(new Double[]{var30 * 0.5, 0.0, 0.5 * var31}).iterator();

         while (var50.hasNext()) {
            double var6 = ((Number)var50.next()).doubleValue();
            float var19 = onDraw$y(var17, var4, var31, var10, var14, var6);
            float var18 = onDraw$y(var17, var4, var31, var10, var14, var6);
            byte var23;
            if (var6 == 0.0) {
               var23 = (byte)var20;
            } else {
               var23 = 0;
            }

            Paint var47;
            if (var23 != 0) {
               var47 = this.axis;
            } else {
               var47 = this.grid;
            }

            var1.drawLine(0.0F, var19, var16, var18, var47);
         }

         byte var46;
         if (this.unit.length() > 0) {
            var46 = (byte)var20;
         } else {
            var46 = 0;
         }

         String var49;
         if (var46 != 0) {
            var49 = this.unit;
            var49 = " " + var49;
         } else {
            var49 = "";
         }

         this.label.setTextAlign(Align.LEFT);
         String var51 = this.fmt(var4);
         String var52 = var51 + var49;
         float var42 = 4;
         var1.drawText(var52, this.d * var42, var17 + this.d * var15, this.label);
         var1.drawText("0", this.d * var42, onDraw$y(var17, var4, var31, var10, var14, 0.0) - var22 * this.d, this.label);
         String var53 = this.fmt(var31);
         var1.drawText(var53 + var49, this.d * var42, var14 - 5 * this.d, this.label);
         double var36 = System.currentTimeMillis() / 1000.0;
         double var12 = CollectionsKt.last(this.samples).getTs();
         double var8 = 0.0;
         var36 = Math.max(0.0, var36 - var12);
         this.label.setTextAlign(Align.RIGHT);
         String var54;
         if (var36 < 1.0) {
            long var24 = Math.round(1000 * var36);
            var54 = var24 + " ms";
         } else {
            StringCompanionObject var55 = StringCompanionObject.INSTANCE;
            var54 = String.format(Locale.US, "%.1f s", Arrays.copyOf(new Object[]{var36}, var20));
            Intrinsics.checkNotNullExpressionValue(var54, "format(...)");
         }

         var1.drawText("-" + var54, var16 - var42 * this.d, var17 + var15 * this.d, this.label);
         var50 = this.series.iterator();
         var36 = var31;
         var30 = var8;

         while (var50.hasNext()) {
            Pair var59 = (Pair)var50.next();
            String var57 = (String)var59.component1();
            var20 = ((Number)var59.component2()).intValue();
            this.stroke.setColor(var20);
            this.path.reset();

            for (int var45 = 0; var45 < var21; var45++) {
               var15 = (float)var45 / (var21 - 1) * var16;
               Double var60 = this.samples.get(var45).getV().get(var57);
               if (var60 != null) {
                  var8 = var60;
               } else {
                  var8 = var30;
               }

               var42 = onDraw$y(var17, var4, var36, var10, var14, var8);
               Path var61 = this.path;
               if (var45 == 0) {
                  var61.moveTo(var15, var42);
               } else {
                  var61.lineTo(var15, var42);
               }
            }

            var1.drawPath(this.path, this.stroke);
         }
      }
   }
}
