package com.starpilot.starview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class SideView extends View {
   private final Paint p;
   private final Paint ring;
   private String side;
   public Telemetry t;

   public SideView(Context var1, AttributeSet var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      super(var1, var2);
      this.side = "left";
      this.p = new Paint(3);
      Paint var3 = new Paint(1);
      var3.setStyle(Style.STROKE);
      this.ring = var3;
   }

   // $VF: synthetic method
   public SideView(Context var1, AttributeSet var2, int var3, DefaultConstructorMarker var4) {
      if ((var3 & 2) != 0) {
         var2 = null;
      }

      this(var1, var2);
   }

   public final String getSide() {
      return this.side;
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

   protected void onDraw(Canvas var1) {
      Intrinsics.checkNotNullParameter(var1, "c");
      if (this.t != null) {
         Bitmap var8 = SideCam.INSTANCE.getImages().get(this.side);
         float var2 = Math.min(this.getWidth(), this.getHeight());
         float var4 = this.getWidth() / 2.0F;
         float var3 = this.getHeight() / 2.0F;
         boolean var6;
         if (var8 != null && System.currentTimeMillis() - SideCam.INSTANCE.getImageAtMs() < 1500L) {
            var6 = 1;
         } else {
            var6 = 0;
         }

         if (var6) {
            Intrinsics.checkNotNull(var8);
            float var5 = 2;
            var1.drawBitmap(var8, null, new RectF(var4 - var2 / var5, var3 - var2 / var5, var2 / var5 + var4, var2 / var5 + var3), this.p);
         } else {
            this.p.setColor(Color.argb(150, 0, 0, 0));
            var1.drawCircle(var4, var3, var2 / 2, this.p);
         }

         boolean var7;
         if (Intrinsics.areEqual(this.side, "left")) {
            var7 = this.getT().getLeftBlindspot();
         } else {
            var7 = this.getT().getRightBlindspot();
         }

         this.ring.setStrokeWidth(3.0F * var2 / 600.0F * 2.0F);
         Paint var11 = this.ring;
         if (var7) {
            var6 = Color.rgb(255, 60, 60);
         } else {
            var6 = Color.rgb(94, 229, 238);
         }

         var11.setColor(var6);
         float var9 = 2;
         var1.drawCircle(var4, var3, var2 / var9 - this.ring.getStrokeWidth() / var9, this.ring);
      }
   }

   public final void setSide(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.side = var1;
   }

   public final void setT(Telemetry var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.t = var1;
   }
}
