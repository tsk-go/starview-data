package com.starpilot.starview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class WeatherView extends View {
   private final Paint p;
   public Telemetry t;

   public WeatherView(Context var1, AttributeSet var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      super(var1, var2);
      this.p = new Paint(1);
   }

   // $VF: synthetic method
   public WeatherView(Context var1, AttributeSet var2, int var3, DefaultConstructorMarker var4) {
      if ((var3 & 2) != 0) {
         var2 = null;
      }

      this(var1, var2);
   }

   private static final void onDraw$cloud(WeatherView var0, Canvas var1, float var2, float var3, float var4, int var5) {
      var0.p.setColor(var5);
      var1.drawCircle(var2 - var3 * 0.7F, var4, 0.75F * var3, var0.p);
      var1.drawCircle(0.1F * var3 + var2, var4 - 0.4F * var3, var3 * 0.9F, var0.p);
      var1.drawCircle(0.9F * var3 + var2, var4, var3 * 0.7F, var0.p);
      var1.drawRoundRect(new RectF(var2 - 1.4F * var3, var4, 1.5F * var3 + var2, 0.7F * var3 + var4), var3 * 0.3F, 0.3F * var3, var0.p);
   }

   private static final void onDraw$sunOrMoon(WeatherView var0, Canvas var1, float var2, float var3, float var4) {
      if (var0.getT().getWeatherDay()) {
         var0.p.setColor(Color.rgb(255, 200, 0));
         var1.drawCircle(var2, var3, var4, var0.p);
      } else {
         var0.p.setColor(Color.rgb(240, 240, 200));
         var1.drawCircle(var2, var3, var4, var0.p);
         Paint var6 = var0.p;
         int var5;
         if (var0.getT().getWeatherDay()) {
            var5 = Color.rgb(135, 206, 235);
         } else {
            var5 = Color.rgb(25, 25, 112);
         }

         var6.setColor(var5);
         var1.drawCircle(0.45F * var4 + var2, var3 - 0.3F * var4, 0.8F * var4, var0.p);
      }
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
      if (this.t != null && this.getT().getStarted() && this.getT().getWeatherId() > 0) {
         float var2 = this.getWidth();
         float var4 = this.getHeight();
         float var3 = ExtraViewsKt.access$dpf(this, 12.0F);
         this.p.setStyle(Style.FILL);
         Paint var9 = this.p;
         int var6;
         if (this.getT().getWeatherDay()) {
            var6 = Color.rgb(135, 206, 235);
         } else {
            var6 = Color.rgb(25, 25, 112);
         }

         var9.setColor(var6);
         var1.drawRoundRect(new RectF(0.0F, 0.0F, var2, var4), var3, var3, this.p);
         this.p.setStyle(Style.STROKE);
         this.p.setStrokeWidth(ExtraViewsKt.access$dpf(this, 2.0F));
         this.p.setColor(-16777216);
         boolean var7 = true;
         float var5 = 1;
         var1.drawRoundRect(new RectF(1.0F, 1.0F, var2 - var5, var4 - var5), var3, var3, this.p);
         int var8 = this.getT().getWeatherId();
         var5 = 2;
         var3 = var2 / var5;
         var4 /= var5;
         var2 *= 0.2F;
         this.p.setStyle(Style.FILL);
         boolean var14;
         if (200 <= var8 && var8 < 300) {
            var14 = true;
         } else {
            var14 = false;
         }

         if (var14) {
            onDraw$cloud(this, var1, var3, var2, var4 - var2 * 0.3F, Color.rgb(90, 90, 110));
            this.p.setColor(Color.rgb(255, 220, 0));
            Path var22 = new Path();
            var22.moveTo(var2 * 0.2F + var3, var4 + 0.2F * var2);
            var22.lineTo(var3 - var2 * 0.3F, var2 * 1.0F + var4);
            var22.lineTo(0.05F * var2 + var3, var2 * 1.0F + var4);
            var22.lineTo(var3 - 0.3F * var2, 1.7F * var2 + var4);
            var22.lineTo(var3 + 0.5F * var2, var2 * 0.8F + var4);
            var22.lineTo(0.15F * var2 + var3, var4 + 0.8F * var2);
            var22.close();
            var1.drawPath(var22, this.p);
         } else {
            if (300 <= var8 && var8 < 600) {
               var14 = true;
            } else {
               var14 = false;
            }

            if (var14) {
               onDraw$cloud(this, var1, var3, var2, var4 - 0.4F * var2, Color.rgb(200, 200, 210));
               this.p.setColor(Color.rgb(80, 140, 255));
               this.p.setStrokeWidth(ExtraViewsKt.access$dpf(this, 3.0F));
               this.p.setStyle(Style.STROKE);

               for (int var16 = -1; var16 < 2; var16++) {
                  var1.drawLine(var3 + var16 * var2 * 0.7F, var4 + var2 * 0.6F, var16 * var2 * 0.7F + var3 - 0.25F * var2, var4 + 1.5F * var2, this.p);
               }

               this.p.setStyle(Style.FILL);
            } else {
               if (600 <= var8 && var8 < 700) {
                  var14 = true;
               } else {
                  var14 = false;
               }

               if (var14) {
                  onDraw$cloud(this, var1, var3, var2, var4 - 0.4F * var2, Color.rgb(220, 220, 230));
                  this.p.setColor(-1);

                  for (int var18 = -1; var18 < 2; var18++) {
                     var1.drawCircle(var18 * var2 * 0.7F + var3, 1.1F * var2 + var4, 0.18F * var2, this.p);
                  }
               } else {
                  if (700 <= var8 && var8 < 800) {
                     var14 = true;
                  } else {
                     var14 = false;
                  }

                  if (var14) {
                     this.p.setColor(Color.rgb(190, 190, 200));
                     this.p.setStrokeWidth(ExtraViewsKt.access$dpf(this, 3.0F));
                     this.p.setStyle(Style.STROKE);

                     for (int var20 = 0; var20 < 4; var20++) {
                        var1.drawLine(
                           var3 - 1.3F * var2,
                           var20 * var2 * 0.5F + (var4 - var2 * 0.6F),
                           var3 + 1.3F * var2,
                           var20 * var2 * 0.5F + (var4 - var2 * 0.6F),
                           this.p
                        );
                     }

                     this.p.setStyle(Style.FILL);
                  } else if (var8 == 800) {
                     onDraw$sunOrMoon(this, var1, var3, var4, 1.1F * var2);
                  } else {
                     if (801 <= var8 && var8 < 803) {
                        var14 = var7;
                     } else {
                        var14 = false;
                     }

                     if (var14) {
                        onDraw$sunOrMoon(this, var1, var2 * 0.6F + var3, var4 - 0.6F * var2, var2 * 0.8F);
                        onDraw$cloud(this, var1, var3, var2, var4 + 0.2F * var2, Color.rgb(235, 235, 240));
                     } else {
                        onDraw$cloud(this, var1, var3, var2, var4, Color.rgb(180, 180, 190));
                     }
                  }
               }
            }
         }
      }
   }

   public final void setT(Telemetry var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.t = var1;
   }
}
