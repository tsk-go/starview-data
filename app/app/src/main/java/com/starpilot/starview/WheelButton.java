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
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.util.AttributeSet;
import android.view.View;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class WheelButton extends View {
   private final Bitmap exp;
   private boolean isDown;
   private final Paint p;
   public Telemetry t;
   private final WheelTint tint;
   private final Bitmap wheel;

   public WheelButton(Context var1, AttributeSet var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      super(var1, var2);
      this.wheel = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_wheel);
      this.exp = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_experimental);
      this.p = new Paint(3);
      this.tint = new WheelTint();
   }

   // $VF: synthetic method
   public WheelButton(Context var1, AttributeSet var2, int var3, DefaultConstructorMarker var4) {
      if ((var3 & 2) != 0) {
         var2 = null;
      }

      this(var1, var2);
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
         float var5 = Math.min(this.getWidth(), this.getHeight());
         float var4 = this.getWidth() / 2.0F;
         float var3 = this.getHeight() / 2.0F;
         Telemetry var10 = this.getT();
         boolean var9 = false;
         int var7;
         if (var10.tBool("simple_mode", false)) {
            var7 = Color.argb(166, 0, 0, 0);
         } else if (this.getT().mBool("SwitchbackModeEnabled", false)) {
            var7 = Color.rgb(139, 108, 197);
         } else if (this.getT().getAolEnabled() && !this.getT().getEnabled()) {
            var7 = Color.rgb(10, 186, 181);
         } else if (this.getT().getTrafficMode() && this.getT().getEnabled()) {
            var7 = Color.rgb(201, 34, 49);
         } else if (this.getT().getExperimentalMode() && this.getT().getEnabled()) {
            var7 = Color.rgb(218, 111, 37);
         } else if (this.getT().getEnabled()) {
            var7 = Color.rgb(22, 127, 64);
         } else {
            var7 = Color.argb(166, 0, 0, 0);
         }

         this.p.setStyle(Style.FILL);
         this.p.setColor(var7);
         Paint var16 = this.p;
         if (this.isDown) {
            var7 = 180;
         } else {
            var7 = Color.alpha(var7);
         }

         var16.setAlpha(var7);
         float var2 = 2;
         var1.drawCircle(var4, var3, var5 / var2, this.p);
         WheelTint var17 = this.tint;
         float var6 = this.getT().getAEgo();
         boolean var8 = var9;
         if (this.getT().s("wheel_tint")) {
            var8 = var9;
            if (this.getT().getStarted()) {
               var8 = true;
            }
         }

         var7 = var17.update(var6, var8, System.nanoTime());
         Paint var11 = this.p;
         ColorFilter var18;
         if (var7 != -1) {
            var18 = (ColorFilter)(new PorterDuffColorFilter(var7, Mode.MULTIPLY));
         } else {
            var18 = null;
         }

         var11.setColorFilter(var18);
         Bitmap var19;
         if (this.getT().getExperimentalMode()) {
            var19 = this.exp;
         } else {
            var19 = this.wheel;
         }

         var5 = 0.75F * var5;
         var11 = this.p;
         short var15;
         if (!this.getT().getEngageable() && !this.getT().getEnabled()) {
            var15 = 120;
         } else {
            var15 = 255;
         }

         var11.setAlpha(var15);
         var1.save();
         if (!this.getT().getExperimentalMode() && this.getT().s("rotating_wheel")) {
            var1.rotate(-this.getT().getSteeringAngleDeg(), var4, var3);
         }

         var1.drawBitmap(var19, null, new RectF(var4 - var5 / var2, var3 - var5 / var2, var5 / var2 + var4, var5 / var2 + var3), this.p);
         var1.restore();
         this.p.setAlpha(255);
         this.p.setColorFilter(null);
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
