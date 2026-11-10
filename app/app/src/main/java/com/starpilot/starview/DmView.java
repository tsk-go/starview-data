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
import androidx.core.graphics.ColorUtils;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;

public final class DmView extends View {
   private final Bitmap bg;
   private final Bitmap cone;
   private float fadeF;
   private float greenF;
   private long lastNs;
   private final Paint p;
   private final Bitmap person;
   private float pitchF;
   private float rotF;
   public Telemetry t;
   private float yawF;

   public DmView(Context var1, AttributeSet var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      super(var1, var2);
      this.bg = BitmapFactory.decodeResource(this.getResources(), R.drawable.dm_background);
      this.person = BitmapFactory.decodeResource(this.getResources(), R.drawable.dm_person);
      this.cone = BitmapFactory.decodeResource(this.getResources(), R.drawable.dm_cone);
      this.p = new Paint(3);
      this.greenF = 1.0F;
   }

   // $VF: synthetic method
   public DmView(Context var1, AttributeSet var2, int var3, DefaultConstructorMarker var4) {
      if ((var3 & 2) != 0) {
         var2 = null;
      }

      this(var1, var2);
   }

   private static final float onDraw$lp(float var0, float var1, float var2, float var3) {
      return (var2 - var1) * (var0 / (var3 + var0)) + var1;
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
         long var9 = System.nanoTime();
         float var3;
         if (this.lastNs == 0L) {
            var3 = 0.05F;
         } else {
            var3 = RangesKt.coerceIn((float)(var9 - this.lastNs) / 1.0E9F, 0.001F, 0.2F);
         }

         this.lastNs = var9;
         boolean var8;
         if (this.getT().s("dm_icon") && this.getT().getStarted()) {
            var8 = 0;
         } else {
            var8 = 1;
         }

         float var2;
         if (var8) {
            var2 = 0.0F;
         } else if (!this.getT().getDmPolicyVision()) {
            var2 = 0.35F;
         } else {
            var2 = 1.0F;
         }

         this.fadeF = onDraw$lp(var3, this.fadeF, var2, 0.1F);
         if (!(this.fadeF < 0.02F)) {
            float var4 = this.getT().getDmPitch();
            var2 = (float)Math.toRadians(6.0);
            float var5 = -this.getT().getDmYaw();
            this.pitchF = onDraw$lp(var3, this.pitchF, var4 + var2, 0.05F);
            this.yawF = onDraw$lp(var3, this.yawF, var5, 0.05F);
            var2 = this.pitchF;
            var4 = 2;
            var2 = (float)Math.toDegrees(Math.atan2(var2 * var4, this.yawF));
            var5 = this.rotF;
            this.rotF = onDraw$lp(var3, this.rotF, this.rotF + (((var2 - var5 + 180.0F) % 360.0F + 360.0F) % 360.0F - 180.0F), 0.1F);
            var5 = this.greenF;
            if (this.getT().getDmAwareness() < 95) {
               var2 = 0.0F;
            } else {
               var2 = 1.0F;
            }

            this.greenF = onDraw$lp(var3, var5, var2, 0.1F);
            float var6 = Math.min(this.getWidth(), this.getHeight());
            var2 = this.getWidth() / 2.0F;
            var5 = this.getHeight() / 2.0F;
            var3 = 0.6666667F * var6;
            var6 = 0.578125F * var6;
            Paint var11 = this.p;
            float var7 = 255;
            var11.setAlpha(MathKt.roundToInt(this.fadeF * var7));
            var1.drawBitmap(this.bg, null, new RectF(var2 - var3 / var4, var5 - var3 / var4, var2 + var3 / var4, var5 + var3 / var4), this.p);
            this.p.setAlpha(MathKt.roundToInt(this.fadeF * 229.5F));
            var1.drawBitmap(this.person, null, new RectF(var2 - var6 / var4, var5 - var6 / var4, var6 / var4 + var2, var5 + var6 / var4), this.p);
            if (this.getT().getDmPolicyVision()) {
               var8 = ColorUtils.blendARGB(Color.rgb(255, 115, 0), Color.rgb(0, 255, 64), this.greenF);
               this.p.setColorFilter((ColorFilter)(new PorterDuffColorFilter(var8, Mode.SRC_IN)));
               this.p.setAlpha(MathKt.roundToInt(var7 * this.fadeF));
               var1.save();
               var1.rotate(this.rotF - 90.0F, var2, var5);
               var1.drawBitmap(this.cone, null, new RectF(var2 - var6 / var4, var5 - var6 / var4, var6 / var4 + var2, var6 / var4 + var5), this.p);
               var1.restore();
               this.p.setColorFilter(null);
            }

            this.p.setAlpha(255);
         }
      }
   }

   public final void setT(Telemetry var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.t = var1;
   }
}
