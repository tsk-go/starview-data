package com.starpilot.starview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;

public final class AetherGaugeView extends View {
   private boolean active;
   private long lastActiveNs;
   private final Paint p;
   private final Path path;
   public Telemetry t;
   private final Paint tp;

   public AetherGaugeView(Context var1, AttributeSet var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      super(var1, var2);
      this.p = new Paint(1);
      Paint var3 = new Paint(1);
      var3.setTextAlign(Align.CENTER);
      var3.setTypeface(Typeface.DEFAULT_BOLD);
      var3.setColor(-1);
      this.tp = var3;
      this.path = new Path();
   }

   // $VF: synthetic method
   public AetherGaugeView(Context var1, AttributeSet var2, int var3, DefaultConstructorMarker var4) {
      if ((var3 & 2) != 0) {
         var2 = null;
      }

      this(var1, var2);
   }

   private final float conv(float var1) {
      float var2;
      if (this.getT().isMetric()) {
         var2 = 3.6F;
      } else {
         var2 = 2.23694F;
      }

      return var2 * var1;
   }

   private final String dist(float var1) {
      String var3;
      StringBuilder var4;
      if (this.getT().isMetric()) {
         int var2 = MathKt.roundToInt(var1);
         var4 = new StringBuilder().append(var2);
         var3 = " m";
      } else {
         int var5 = MathKt.roundToInt(3.28084F * var1);
         var4 = new StringBuilder().append(var5);
         var3 = " ft";
      }

      return var4.append(var3).toString();
   }

   private final AetherGaugeView.Src source() {
      boolean var3 = this.getT().getCruiseEnabled();
      int var2 = 1;
      boolean var1;
      if (var3 && this.getT().getPlanVCruise() > 0.0F) {
         var1 = 1;
      } else {
         var1 = 0;
      }

      AetherGaugeView.Src var4;
      if (this.getT().getForcingStop() && !this.getT().getRedLight()) {
         String var12;
         if (this.getT().getForcingStopLength() > 0.0F) {
            var12 = this.dist(this.getT().getForcingStopLength());
         } else {
            var12 = "";
         }

         var4 = new AetherGaugeView.Src(var12, "", "STOP", Color.rgb(255, 30, 60), false);
      } else if (this.getT().getExperimentalMode() && this.getT().getRedLight()) {
         var4 = new AetherGaugeView.Src(this.dist(this.getT().getForcingStopLength()), "", "RED LIGHT", Color.rgb(255, 60, 60), false);
      } else if ((this.getT().getCscControlling() || this.getT().getCscTraining()) && var1) {
         if (this.getT().getCscTraining()) {
            var1 = Color.rgb(112, 192, 216);
         } else {
            var1 = OverlayView.Companion.cscColor(this.getT().getRoadCurvature());
         }

         var2 = MathKt.roundToInt(this.conv(this.getT().getCscSpeed()));
         String var11;
         if (this.getT().isMetric()) {
            var11 = "km/h";
         } else {
            var11 = "mph";
         }

         String var5;
         if (this.getT().getCscTraining()) {
            var5 = "LEARNING";
         } else {
            var5 = "CURVE";
         }

         var4 = new AetherGaugeView.Src(String.valueOf(var2), var11, var5, var1, true);
      } else if (this.getT().getExperimentalMode() && this.getT().getTrackingLead() && this.getT().getLeadOne().getStatus()) {
         boolean var6;
         if (this.getT().getLeadOne().getVLead() < 0.5F) {
            var6 = var2;
         } else {
            var6 = 0;
         }

         String var10;
         if (var6) {
            var10 = "STOPPED";
         } else {
            var10 = "SLOW";
         }

         if (var6) {
            var6 = Color.rgb(255, 60, 60);
         } else {
            var6 = Color.rgb(255, 191, 0);
         }

         var4 = new AetherGaugeView.Src(var10, "", "LEAD", var6, false);
      } else {
         var4 = null;
      }

      return var4;
   }

   public final boolean getActive() {
      return this.active;
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
      if (this.t != null && this.getT().getStarted()) {
         AetherGaugeView.Src var18 = this.source();
         long var14 = System.nanoTime();
         if (var18 != null) {
            this.lastActiveNs = var14;
         }

         boolean var16;
         if (var18 == null && var14 - this.lastActiveNs >= 500000000L) {
            var16 = false;
         } else {
            var16 = true;
         }

         this.active = var16;
         if (var18 != null) {
            float var4 = this.getWidth();
            float var5 = this.getHeight();
            float var3 = ExtraViewsKt.access$dpf(this, 14.0F);
            this.p.setStyle(Style.FILL);
            this.p.setColor(Color.argb(166, 0, 0, 0));
            var1.drawRoundRect(new RectF(0.0F, 0.0F, var4, var5), var3, var3, this.p);
            this.p.setStyle(Style.STROKE);
            this.p.setStrokeWidth(ExtraViewsKt.access$dpf(this, 2.0F));
            this.p.setColor(OverlayView.Companion.withAlpha(var18.getColor(), 200));
            float var2 = 1;
            var1.drawRoundRect(new RectF(1.0F, 1.0F, var4 - var2, var5 - var2), var3, var3, this.p);
            float var10 = ExtraViewsKt.access$dpf(this, 10.0F);
            float var7 = 0.52F * var5;
            float var9 = 0.46F * var4;
            float var8 = var4 * 0.22F;
            float var11 = 2;
            float var6 = var4 / var11;
            if (var18.getCurve()) {
               var2 = RangesKt.coerceIn(this.getT().getRoadCurvature(), -0.02F, 0.02F) / 0.02F * var4 * 0.18F;
            } else {
               var2 = 0.0F;
            }

            var3 = var2;
            this.path.rewind();
            this.path.moveTo(var6 - var9 / var11, var7);
            this.path.lineTo(var9 / var11 + var6, var7);
            this.path.quadTo(var8 / var11 + var6 + var2 * 0.5F, (var10 + var7) / var11, var8 / var11 + var6 - var2, var10);
            this.path.lineTo(var6 - var8 / var11 - var2, var10);
            this.path.quadTo(var6 - var8 / var11 + 0.5F * var2, (var10 + var7) / var11, var6 - var9 / var11, var7);
            this.path.close();
            this.p.setStyle(Style.FILL);
            this.p.setColor(OverlayView.Companion.withAlpha(var18.getColor(), 90));
            var1.drawPath(this.path, this.p);
            this.p.setStyle(Style.STROKE);
            this.p.setStrokeWidth(ExtraViewsKt.access$dpf(this, 2.0F));
            this.p.setColor(var18.getColor());
            var1.drawPath(this.path, this.p);

            for (int var13 = 0; var13 < 4; var13++) {
               float var12 = var13 * 0.22F + 0.15F;
               var4 = var7 - (var7 - var10) * var12;
               var11 = (var9 + (var8 - var9) * var12) * 0.28F;
               var12 = var6 - var3 * var12 * var12;
               this.path.rewind();
               this.path.moveTo(var12 - var11, ExtraViewsKt.access$dpf(this, 3.0F) + var4);
               this.path.lineTo(var12, var4 - ExtraViewsKt.access$dpf(this, 3.0F));
               this.path.lineTo(var12 + var11, ExtraViewsKt.access$dpf(this, 3.0F) + var4);
               this.p.setStrokeWidth(ExtraViewsKt.access$dpf(this, 1.5F));
               this.p.setColor(OverlayView.Companion.withAlpha(-1, 160));
               var1.drawPath(this.path, this.p);
            }

            this.tp.setColor(-1);
            Paint var17 = this.tp;
            if (var18.getValue().length() > 4) {
               var2 = 0.15F * var5;
            } else {
               var2 = var5 * 0.26F;
            }

            var17.setTextSize(var2);
            var1.drawText(var18.getValue(), var6, 0.76F * var5, this.tp);
            this.tp.setTextSize(0.1F * var5);
            this.tp.setColor(OverlayView.Companion.withAlpha(-1, 180));
            boolean var25;
            if (var18.getUnit().length() > 0) {
               var25 = true;
            } else {
               var25 = false;
            }

            String var27;
            if (var25) {
               var27 = var18.getUnit();
            } else {
               var27 = var18.getLabel();
            }

            var1.drawText(var27, var6, 0.9F * var5, this.tp);
            if (var18.getUnit().length() > 0) {
               var25 = true;
            } else {
               var25 = false;
            }

            if (var25) {
               this.tp.setTextSize(0.09F * var5);
               this.tp.setColor(OverlayView.Companion.withAlpha(var18.getColor(), 230));
               var1.drawText(var18.getLabel(), var6, 0.08F * var5 + var7, this.tp);
            }
         }
      } else {
         this.active = false;
      }
   }

   public final void setT(Telemetry var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.t = var1;
   }

   public static final class Src {
      private final int color;
      private final boolean curve;
      private final String label;
      private final String unit;
      private final String value;

      public Src(String var1, String var2, String var3, int var4, boolean var5) {
         Intrinsics.checkNotNullParameter(var1, "value");
         Intrinsics.checkNotNullParameter(var2, "unit");
         Intrinsics.checkNotNullParameter(var3, "label");
         super();
         this.value = var1;
         this.unit = var2;
         this.label = var3;
         this.color = var4;
         this.curve = var5;
      }

      // $VF: synthetic method
      public static AetherGaugeView.Src copy$default(
         AetherGaugeView.Src var0, String var1, String var2, String var3, int var4, boolean var5, int var6, Object var7
      ) {
         if ((var6 & 1) != 0) {
            var1 = var0.value;
         }

         if ((var6 & 2) != 0) {
            var2 = var0.unit;
         }

         if ((var6 & 4) != 0) {
            var3 = var0.label;
         }

         if ((var6 & 8) != 0) {
            var4 = var0.color;
         }

         if ((var6 & 16) != 0) {
            var5 = var0.curve;
         }

         return var0.copy(var1, var2, var3, var4, var5);
      }

      public final String component1() {
         return this.value;
      }

      public final String component2() {
         return this.unit;
      }

      public final String component3() {
         return this.label;
      }

      public final int component4() {
         return this.color;
      }

      public final boolean component5() {
         return this.curve;
      }

      public final AetherGaugeView.Src copy(String var1, String var2, String var3, int var4, boolean var5) {
         Intrinsics.checkNotNullParameter(var1, "value");
         Intrinsics.checkNotNullParameter(var2, "unit");
         Intrinsics.checkNotNullParameter(var3, "label");
         return new AetherGaugeView.Src(var1, var2, var3, var4, var5);
      }

      @Override
      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof AetherGaugeView.Src)) {
            return false;
         } else {
            var1 = var1;
            if (!Intrinsics.areEqual(this.value, var1.value)) {
               return false;
            } else if (!Intrinsics.areEqual(this.unit, var1.unit)) {
               return false;
            } else if (!Intrinsics.areEqual(this.label, var1.label)) {
               return false;
            } else {
               return this.color != var1.color ? false : this.curve == var1.curve;
            }
         }
      }

      public final int getColor() {
         return this.color;
      }

      public final boolean getCurve() {
         return this.curve;
      }

      public final String getLabel() {
         return this.label;
      }

      public final String getUnit() {
         return this.unit;
      }

      public final String getValue() {
         return this.value;
      }

      @Override
      public int hashCode() {
         return (((this.value.hashCode() * 31 + this.unit.hashCode()) * 31 + this.label.hashCode()) * 31 + Integer.hashCode(this.color)) * 31
            + Boolean.hashCode(this.curve);
      }

      @Override
      public String toString() {
         String var3 = this.value;
         String var4 = this.unit;
         String var5 = this.label;
         int var1 = this.color;
         boolean var2 = this.curve;
         return "Src(value=" + var3 + ", unit=" + var4 + ", label=" + var5 + ", color=" + var1 + ", curve=" + var2 + ")";
      }
   }
}
