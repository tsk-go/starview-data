package com.starpilot.starview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Paint.Style;
import android.view.View;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

public final class Spark extends View {
   private final int color;
   private final List<String> labels;
   private final float maxV;
   private final Paint p;
   private final List<Float> values;
   private final Float warnAt;

   public Spark(Context var1, List<Float> var2, float var3, int var4, Float var5, List<String> var6) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "values");
      Intrinsics.checkNotNullParameter(var6, "labels");
      super(var1);
      this.values = var2;
      this.maxV = var3;
      this.color = var4;
      this.warnAt = var5;
      this.labels = var6;
      this.p = new Paint(1);
   }

   // $VF: synthetic method
   public Spark(Context var1, List var2, float var3, int var4, Float var5, List var6, int var7, DefaultConstructorMarker var8) {
      if ((var7 & 16) != 0) {
         var5 = null;
      }

      if ((var7 & 32) != 0) {
         var6 = CollectionsKt.emptyList();
      }

      this(var1, var2, var3, var4, var5, var6);
   }

   protected void onDraw(Canvas var1) {
      Intrinsics.checkNotNullParameter(var1, "c");
      float var3 = this.getResources().getDisplayMetrics().density;
      float var4 = this.getWidth();
      float var5 = this.getHeight();
      float var2;
      if (!this.labels.isEmpty()) {
         var2 = 18 * var3;
      } else {
         var2 = 0.0F;
      }

      var5 -= var2;
      this.p.setColor(Color.rgb(34, 40, 55));
      this.p.setStyle(Style.FILL);
      var2 = 10;
      var1.drawRoundRect(0.0F, 0.0F, var4, var5, var2 * var3, var2 * var3, this.p);
      if (this.warnAt != null) {
         this.p.setColor(Color.rgb(120, 60, 40));
         this.p.setStrokeWidth(var3);
         var2 = var5 - this.warnAt / this.maxV * var5;
         var1.drawLine(0.0F, var2, var4, var2, this.p);
      }

      Iterable var12 = this.values;
      var var11 = new ArrayList();

      for (Object var13 : var12) {
         var2 = ((Number)var13).floatValue();
         boolean var7;
         if (!Float.isInfinite(var2) && !Float.isNaN(var2)) {
            var7 = true;
         } else {
            var7 = false;
         }

         if (var7) {
            var11.add(var13);
         }
      }

      List var25 = var11;
      if (this.values.size() >= 2 && !var25.isEmpty()) {
         Path var26 = new Path();
         boolean var21 = false;
         Iterator var29 = this.values.iterator();
         int var8 = 0;

         while (var29.hasNext()) {
            int var9 = var8 + 1;
            float var6 = ((Number)var29.next()).floatValue();
            boolean var23;
            if (!Float.isInfinite(var6) && !Float.isNaN(var6)) {
               var23 = true;
            } else {
               var23 = false;
            }

            if (!var23) {
               var21 = false;
               var8 = var9;
            } else {
               var2 = var8 * var4 / (this.values.size() - 1);
               var6 = var5 - RangesKt.coerceIn(var6 / this.maxV, 0.0F, 1.0F) * (var5 - 6 * var3) - 3 * var3;
               if (!var21) {
                  var26.moveTo(var2, var6);
                  var21 = true;
                  var8 = var9;
               } else {
                  var26.lineTo(var2, var6);
                  var8 = var9;
               }
            }
         }

         this.p.setStyle(Style.STROKE);
         this.p.setStrokeWidth(2.5F * var3);
         this.p.setColor(this.color);
         var1.drawPath(var26, this.p);
         this.p.setStyle(Style.FILL);
      }

      if (!this.labels.isEmpty()) {
         this.p.setColor(Color.rgb(150, 160, 180));
         this.p.setTextSize(12 * var3);
         Iterator var27 = this.labels.iterator();
         int var22 = 0;

         while (true) {
            int var24 = var22;
            if (!var27.hasNext()) {
               break;
            }

            var22++;
            String var30 = (String)var27.next();
            if (this.labels.size() == 1) {
               var2 = 0.0F;
            } else {
               var2 = var24 * (var4 - this.p.measureText(var30)) / (this.labels.size() - 1);
            }

            var1.drawText(var30, var2, 14 * var3 + var5, this.p);
         }
      }
   }
}
