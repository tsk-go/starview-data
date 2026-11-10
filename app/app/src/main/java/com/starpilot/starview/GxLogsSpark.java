package com.starpilot.starview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.view.View;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

final class GxLogsSpark extends View {
   private final Paint paint;
   private List<Double> values;

   public GxLogsSpark(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      super(var1);
      this.values = CollectionsKt.emptyList();
      Paint var2 = new Paint(1);
      var2.setStyle(Style.STROKE);
      var2.setColor(CarUi.Companion.getACCENT());
      var2.setStrokeWidth(var1.getResources().getDisplayMetrics().density * 2.5F);
      var2.setStrokeJoin(Join.ROUND);
      this.paint = var2;
   }

   public final List<Double> getValues() {
      return this.values;
   }

   protected void onDraw(Canvas var1) {
      Intrinsics.checkNotNullParameter(var1, "canvas");
      super.onDraw(var1);
      if (this.values.size() >= 2) {
         float var7 = this.getWidth();
         float var5 = this.getHeight();
         Path var10 = new Path();
         Iterator var11 = this.values.iterator();
         int var8 = 0;

         while (true) {
            int var9 = var8;
            if (!var11.hasNext()) {
               var1.drawPath(var10, this.paint);
               return;
            }

            var8++;
            double var2 = ((Number)var11.next()).doubleValue();
            float var4 = var9 * var7 / 29.0F;
            float var6 = var5 - (float)(RangesKt.coerceIn(var2, 0.0, 100.0) / 100.0 * var5);
            if (var9 == 0) {
               var10.moveTo(var4, var6);
            } else {
               var10.lineTo(var4, var6);
            }
         }
      }
   }

   public final void setValues(List<Double> var1) {
      Intrinsics.checkNotNullParameter(var1, "v");
      this.values = var1;
      this.invalidate();
   }
}
