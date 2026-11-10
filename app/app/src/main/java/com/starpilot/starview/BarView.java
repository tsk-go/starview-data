package com.starpilot.starview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

public final class BarView extends View {
   private final Paint back;
   private final Paint fill;
   private float fraction;
   private final RectF r;

   public BarView(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      super(var1);
      Paint var2 = new Paint(1);
      var2.setColor(Color.rgb(44, 52, 64));
      this.back = var2;
      this.fill = new Paint(1);
      this.r = new RectF();
   }

   public final float getFraction() {
      return this.fraction;
   }

   protected void onDraw(Canvas var1) {
      Intrinsics.checkNotNullParameter(var1, "c");
      float var4 = this.getHeight();
      float var3 = this.getWidth();
      float var2 = var4 / 2;
      this.r.set(0.0F, 0.0F, var3, var4);
      var1.drawRoundRect(this.r, var2, var2, this.back);
      if (!(this.fraction <= 0.0F)) {
         Paint var6 = this.fill;
         int var5;
         if (this.fraction >= 1.0F) {
            var5 = CarUi.Companion.getACCENT();
         } else {
            var5 = Color.rgb(96, 165, 250);
         }

         var6.setColor(var5);
         this.r.set(0.0F, 0.0F, Math.max(var4, this.fraction * var3), var4);
         var1.drawRoundRect(this.r, var2, var2, this.fill);
      }
   }

   public final void setFraction(float var1) {
      var1 = RangesKt.coerceIn(var1, 0.0F, 1.0F);
      boolean var2;
      if (var1 == this.fraction) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (!var2) {
         this.fraction = var1;
         this.invalidate();
      }
   }
}
