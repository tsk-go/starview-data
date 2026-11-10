package com.starpilot.starview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class DevSidebarView extends View {
   private final Paint bg;
   private final Paint box;
   public Telemetry t;
   private final Paint tp;

   public DevSidebarView(Context var1, AttributeSet var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      super(var1, var2);
      Paint var3 = new Paint(1);
      var3.setStyle(Style.STROKE);
      var3.setColor(Color.argb(85, 255, 255, 255));
      this.box = var3;
      Paint var4 = new Paint(1);
      var4.setColor(Color.argb(200, 0, 0, 0));
      this.bg = var4;
      Paint var5 = new Paint(1);
      var5.setColor(-1);
      var5.setTextAlign(Align.CENTER);
      var5.setTypeface(Typeface.create(Typeface.DEFAULT, 1));
      this.tp = var5;
   }

   // $VF: synthetic method
   public DevSidebarView(Context var1, AttributeSet var2, int var3, DefaultConstructorMarker var4) {
      if ((var3 & 2) != 0) {
         var2 = null;
      }

      this(var1, var2);
   }

   // $VF: synthetic method
   public static List rows$default(DevSidebarView var0, long var1, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = System.nanoTime();
      }

      return var0.rows(var1);
   }

   private static final String rows$shown(float var0, int var1, String var2) {
      boolean var3;
      if (!Float.isInfinite(var0) && !Float.isNaN(var0)) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (var3) {
         String var4 = String.format("%." + var1 + "f", Arrays.copyOf(new Object[]{var0}, 1));
         Intrinsics.checkNotNullExpressionValue(var4, "format(...)");
         var2 = var4 + var2;
      } else {
         var2 = "—";
      }

      return var2;
   }

   // $VF: synthetic method
   static String rows$shown$default(float var0, int var1, String var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var1 = 5;
      }

      if ((var3 & 4) != 0) {
         var2 = "";
      }

      return rows$shown(var0, var1, var2);
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
         List var12 = rows$default(this, 0L, 1, null);
         float var3 = BigUiViewsKt.access$dpf(this, 6.0F);
         int var10 = var12.size();
         float var5 = (this.getHeight() - (var10 - 1) * var3) / var10;
         this.box.setStrokeWidth(BigUiViewsKt.access$dpf(this, 1.5F));
         Iterator var13 = var12.iterator();
         int var8 = 0;

         while (var13.hasNext()) {
            int var11 = var8 + 1;
            Pair var15 = (Pair)var13.next();
            float var6 = var8 * (var5 + var3);
            RectF var14 = new RectF(
               this.box.getStrokeWidth(),
               this.box.getStrokeWidth() + var6,
               this.getWidth() - this.box.getStrokeWidth(),
               var6 + var5 - this.box.getStrokeWidth()
            );
            float var2 = Math.min(var14.height(), var14.width()) * 0.3F;
            var1.drawRoundRect(var14, var2, var2, this.bg);
            var1.drawRoundRect(var14, var2, var2, this.box);
            Iterator var20 = CollectionsKt.listOf(new String[]{(String)var15.getFirst(), (String)var15.getSecond()}).iterator();
            int var9 = 0;

            while (true) {
               var10 = var9;
               if (!var20.hasNext()) {
                  var10 = var10;
                  var8 = var11;
                  break;
               }

               var9++;
               String var16 = (String)var20.next();
               float var4 = var5 * 0.26F;
               this.tp.setTextSize(var4);

               while (this.tp.measureText(var16) > var14.width() - BigUiViewsKt.access$dpf(this, 10.0F) && var4 > BigUiViewsKt.access$dpf(this, 8.0F)) {
                  this.tp.setTextSize(--var4);
               }

               float var7 = var14.centerX();
               if (var10 == 0) {
                  var4 = 0.4F;
               } else {
                  var4 = 0.78F;
               }

               var1.drawText(var16, var7, var4 * var5 + var6, this.tp);
            }
         }
      }
   }

   public final List<Pair<String, String>> rows(long var1) {
      boolean var8 = this.getT().isMetric();
      float var3;
      if (var8) {
         var3 = 1.0F;
      } else {
         var3 = 3.28084F;
      }

      String var9;
      if (var8) {
         var9 = "m/s²";
      } else {
         var9 = "ft/s²";
      }

      var8 = this.getT().getStarted();
      boolean var6;
      if (var8 && this.getT().getDelayAtNs() != 0L && var1 - this.getT().getDelayAtNs() < 10000000000L) {
         var6 = true;
      } else {
         var6 = false;
      }

      boolean var7;
      if (var8 && this.getT().getTorqueAtNs() != 0L && var1 - this.getT().getTorqueAtNs() < 10000000000L && this.getT().getTorqueUseParams()) {
         var7 = true;
      } else {
         var7 = false;
      }

      float var5 = Float.NaN;
      float var4;
      if (var8) {
         var4 = this.getT().getAEgo() * var3;
      } else {
         var4 = Float.NaN;
      }

      Pair var10 = TuplesKt.to("ACCEL", rows$shown(var4, 2, " " + var9));
      if (var8) {
         var3 = this.getT().getMaxAccel() * var3;
      } else {
         var3 = Float.NaN;
      }

      Pair var11 = TuplesKt.to("MAX ACCEL", rows$shown(var3, 2, " " + var9));
      if (var6) {
         var9 = "LEARNED DELAY";
      } else {
         var9 = "BASE DELAY";
      }

      Telemetry var12 = this.getT();
      if (var6) {
         var3 = var12.getLearnedDelay();
      } else {
         var3 = var12.getBaseDelay();
      }

      Pair var27 = TuplesKt.to(var9, rows$shown$default(var3, 0, null, 6, null));
      if (var7) {
         var9 = "LEARNED FRICTION";
      } else {
         var9 = "BASE FRICTION";
      }

      Telemetry var13 = this.getT();
      if (var7) {
         var3 = var13.getFrictionLearned();
      } else {
         var3 = var13.getBaseFriction();
      }

      Pair var28 = TuplesKt.to(var9, rows$shown$default(var3, 0, null, 6, null));
      if (var7) {
         var9 = "LEARNED LAT ACCEL";
      } else {
         var9 = "BASE LAT ACCEL";
      }

      Telemetry var14 = this.getT();
      if (var7) {
         var3 = var14.getLatAccelLearned();
      } else {
         var3 = var14.getBaseLatAccel();
      }

      Pair var15 = TuplesKt.to(var9, rows$shown$default(var3, 0, null, 6, null));
      if (var8 && this.getT().getSteerRatioLive() > 0.0F) {
         var3 = this.getT().getSteerRatioLive();
      } else {
         var3 = Float.NaN;
      }

      Pair var26 = TuplesKt.to("STEER RATIO", rows$shown$default(var3, 0, null, 6, null));
      var3 = var5;
      if (var8) {
         var3 = this.getT().getStiffness();
      }

      Pair var29 = TuplesKt.to("STEER STIFF", rows$shown$default(var3, 0, null, 6, null));
      return CollectionsKt.listOf(new Pair[]{var10, var11, var27, var28, var15, var26, var29});
   }

   public final void setT(Telemetry var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.t = var1;
   }
}
