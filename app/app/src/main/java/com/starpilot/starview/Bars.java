package com.starpilot.starview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class Bars extends View {
   private final int color;
   private final List<String> labels;
   private final Paint p;
   private final List<String> topLabels;
   private final List<Float> values;

   public Bars(Context var1, List<Float> var2, List<String> var3, int var4, List<String> var5) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "values");
      Intrinsics.checkNotNullParameter(var3, "labels");
      Intrinsics.checkNotNullParameter(var5, "topLabels");
      super(var1);
      this.values = var2;
      this.labels = var3;
      this.color = var4;
      this.topLabels = var5;
      this.p = new Paint(1);
   }

   // $VF: synthetic method
   public Bars(Context var1, List var2, List var3, int var4, List var5, int var6, DefaultConstructorMarker var7) {
      if ((var6 & 16) != 0) {
         var5 = CollectionsKt.emptyList();
      }

      this(var1, var2, var3, var4, var5);
   }

   protected void onDraw(Canvas var1) {
      Intrinsics.checkNotNullParameter(var1, "c");
      float var3 = this.getResources().getDisplayMetrics().density;
      float var6 = this.getWidth();
      float var2 = this.getHeight();
      float var4 = 20;
      float var5 = var2 - var4 * var3;
      Iterable var15 = this.values;
      var var14 = new ArrayList();

      for (Object var16 : var15) {
         var2 = ((Number)var16).floatValue();
         boolean var12;
         if (!Float.isInfinite(var2) && !Float.isNaN(var2)) {
            var12 = true;
         } else {
            var12 = false;
         }

         if (var12) {
            var14.add(var16);
         }
      }

      List var24 = var14;
      Float var25 = CollectionsKt.maxOrNull(var24);
      if (var25 != null) {
         var2 = var25;
      } else {
         var2 = 1.0F;
      }

      var2 = Math.max(var2, 0.001F);
      var6 /= Math.max(this.values.size(), 1);
      float var7 = var6 * 0.6F;
      Iterator var26 = this.values.iterator();

      for (int var23 = 0; var26.hasNext(); var23++) {
         float var10 = ((Number)var26.next()).floatValue();
         float var9 = var23;
         float var8 = 2;
         var9 = var9 * var6 + (var6 - var7) / var8;
         boolean var13;
         if (!Float.isInfinite(var10) && !Float.isNaN(var10)) {
            var13 = true;
         } else {
            var13 = false;
         }

         if (var13 && var10 > 0.0F) {
            var10 = var5 - var10 / var2 * (var5 - var4 * var3);
            this.p.setColor(this.color);
            float var11 = 6;
            Paint var28 = this.p;
            var1.drawRoundRect(var9, var10, var9 + var7, var5, var11 * var3, var11 * var3, var28);
            String var29 = CollectionsKt.getOrNull(this.topLabels, var23);
            if (var29 != null) {
               this.p.setColor(-1);
               this.p.setTextSize(13 * var3);
               var1.drawText(var29, var9 + var7 / var8 - this.p.measureText(var29) / var8, var10 - 5 * var3, this.p);
            }
         } else {
            this.p.setColor(Color.rgb(50, 56, 70));
            var1.drawRect(var9, var5 - var8 * var3, var9 + var7, var5, this.p);
         }

         String var30 = CollectionsKt.getOrNull(this.labels, var23);
         if (var30 != null) {
            this.p.setColor(Color.rgb(150, 160, 180));
            this.p.setTextSize(12 * var3);
            var1.drawText(var30, var9 + var7 / var8 - this.p.measureText(var30) / var8, this.getHeight() - 4 * var3, this.p);
         }
      }
   }
}
