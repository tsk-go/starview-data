package com.starpilot.starview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.view.View;
import android.view.View.MeasureSpec;
import kotlin.jvm.internal.Intrinsics;

class GxCamerasSnapView extends View {
   private final Paint bg;
   private Bitmap bmp;
   private final float density;
   private final Paint paint;
   private String placeholder;
   private final RectF rect;
   private float s;

   public GxCamerasSnapView(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      super(var1);
      this.density = var1.getResources().getDisplayMetrics().density;
      this.placeholder = "Loading camera snapshot...";
      this.rect = new RectF();
      this.s = 1.0F;
      this.paint = new Paint(3);
      Paint var2 = new Paint();
      var2.setColor(Color.rgb(34, 34, 34));
      this.bg = var2;
   }

   private final void fit() {
      this.s = Math.min(this.getWidth() / this.getCw(), this.getHeight() / this.getCh());
      float var3 = this.getCw() * this.s;
      float var2 = this.getCh() * this.s;
      RectF var5 = this.rect;
      float var4 = this.getWidth();
      float var1 = 2;
      var5.set((var4 - var3) / var1, (this.getHeight() - var2) / var1, (this.getWidth() + var3) / var1, (this.getHeight() + var2) / var1);
   }

   public final float cx(float var1) {
      return (var1 - this.rect.left) / this.s;
   }

   public final float cy(float var1) {
      return (var1 - this.rect.top) / this.s;
   }

   protected final void dot(Canvas var1, float var2, float var3, float var4, int var5, Integer var6) {
      Intrinsics.checkNotNullParameter(var1, "c");
      this.paint.reset();
      this.paint.setAntiAlias(true);
      this.paint.setStyle(Style.FILL);
      this.paint.setColor(var5);
      var1.drawCircle(var2, var3, var4, this.paint);
      if (var6 != null) {
         this.paint.setStyle(Style.STROKE);
         this.paint.setColor(var6);
         this.paint.setStrokeWidth(this.u(2.0F));
         var1.drawCircle(var2, var3, var4, this.paint);
      }
   }

   protected final void drawCrop(Canvas var1, Bitmap var2, RectF var3, RectF var4) {
      Intrinsics.checkNotNullParameter(var1, "c");
      Intrinsics.checkNotNullParameter(var2, "b");
      Intrinsics.checkNotNullParameter(var3, "src");
      Intrinsics.checkNotNullParameter(var4, "dst");
      if (!(var3.width() <= 0.0F) && !(var3.height() <= 0.0F)) {
         float var5 = Math.max(var3.left, 0.0F);
         float var10 = Math.max(var3.top, 0.0F);
         float var8 = Math.min(var3.right, (float)var2.getWidth());
         float var6 = Math.min(var3.bottom, (float)var2.getHeight());
         if (!(var8 <= var5) && !(var6 <= var10)) {
            float var7 = var4.width() / var3.width();
            float var9 = var4.height() / var3.height();
            var3 = new RectF(
               var4.left + (var5 - var3.left) * var7,
               var4.top + (var10 - var3.top) * var9,
               var4.left + (var8 - var3.left) * var7,
               var4.top + (var6 - var3.top) * var9
            );
            this.paint.reset();
            this.paint.setFilterBitmap(true);
            var1.drawBitmap(var2, new Rect((int)var5, (int)var10, (int)var8, (int)var6), var3, this.paint);
         }
      }
   }

   public void drawOverlay(Canvas var1, Bitmap var2) {
      Intrinsics.checkNotNullParameter(var1, "c");
      Intrinsics.checkNotNullParameter(var2, "b");
   }

   public final Bitmap getBmp() {
      return this.bmp;
   }

   public final float getCh() {
      Bitmap var2 = this.bmp;
      float var1;
      if (var2 != null) {
         var1 = GxCamerasKt.access$gxCamRound((float)Math.min(var2.getWidth(), 1280) * var2.getHeight() / var2.getWidth());
      } else {
         var1 = 480.0F;
      }

      return var1;
   }

   public final float getCw() {
      Bitmap var2 = this.bmp;
      float var1;
      if (var2 != null) {
         var1 = Math.min(var2.getWidth(), 1280);
      } else {
         var1 = 640.0F;
      }

      return var1;
   }

   public final float getDensity() {
      return this.density;
   }

   protected final Paint getPaint() {
      return this.paint;
   }

   public final String getPlaceholder() {
      return this.placeholder;
   }

   protected final RectF getRect() {
      return this.rect;
   }

   protected final float getS() {
      return this.s;
   }

   protected void onDraw(Canvas var1) {
      Intrinsics.checkNotNullParameter(var1, "c");
      this.fit();
      var1.drawRect(this.rect, this.bg);
      Bitmap var5 = this.bmp;
      if (var5 == null) {
         this.paint.reset();
         this.paint.setAntiAlias(true);
         this.paint.setColor(Color.rgb(136, 136, 136));
         this.paint.setTextSize(18 * this.density);
         this.paint.setTextAlign(Align.CENTER);
         this.paint.setTypeface(Typeface.MONOSPACE);
         var1.drawText(this.placeholder, this.getWidth() / 2.0F, this.getHeight() / 2.0F, this.paint);
      } else {
         var1.save();
         var1.translate(this.rect.left, this.rect.top);
         var1.scale(this.s, this.s);
         var1.save();
         var1.scale(-1.0F, 1.0F, this.getCw() / 2, 0.0F);
         this.paint.reset();
         this.paint.setFilterBitmap(true);
         var1.drawBitmap(var5, null, new RectF(0.0F, 0.0F, this.getCw(), this.getCh()), this.paint);
         var1.restore();
         this.paint.reset();
         this.paint.setAntiAlias(true);
         float var4 = this.u(32.0F);
         this.paint.setColor(Color.argb(166, 0, 0, 0));
         this.paint.setStyle(Style.FILL);
         var1.drawRect(0.0F, 0.0F, this.getCw(), var4, this.paint);
         this.paint.setColor(-1);
         this.paint.setTextSize(this.u(13.0F));
         this.paint.setTypeface(Typeface.DEFAULT_BOLD);
         this.paint.setTextAlign(Align.CENTER);
         float var3 = this.getCw();
         float var2 = 4;
         var1.drawText("LEFT SIDE OF VEHICLE - LEFT HERE", var3 / var2, var4 * 0.66F, this.paint);
         var1.drawText("RIGHT SIDE OF VEHICLE - RIGHT HERE", this.getCw() * 3 / var2, 0.66F * var4, this.paint);
         this.drawOverlay(var1, var5);
         var1.restore();
      }
   }

   protected void onMeasure(int var1, int var2) {
      var1 = MeasureSpec.getSize(var1);
      float var3 = this.getResources().getDisplayMetrics().heightPixels;
      this.setMeasuredDimension(var1, GxCamerasKt.access$gxCamRound(Math.min(var1 * this.getCh() / this.getCw(), var3 * 0.72F)));
   }

   public final void setBmp(Bitmap var1) {
      this.bmp = var1;
      this.requestLayout();
      this.invalidate();
   }

   public final void setPlaceholder(String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.placeholder = var1;
   }

   protected final void setS(float var1) {
      this.s = var1;
   }

   public final float u(float var1) {
      return this.density * var1 / this.s;
   }
}
