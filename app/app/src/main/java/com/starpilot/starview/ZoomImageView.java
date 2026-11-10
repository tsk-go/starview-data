package com.starpilot.starview;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.drawable.Drawable;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ViewParent;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

public final class ZoomImageView extends ImageView {
   private Matrix base;
   private final GestureDetector gestures;
   private final Matrix m;
   private final float maxZoom;
   private final ScaleGestureDetector scaler;
   private float zoom;

   // $VF: synthetic method
   public static void $r8$lambda$hdQ_PoqNZhS22rl1bS4ChM2Zds8(ZoomImageView var0) {
      setImageDrawable$lambda$0(var0);
   }

   public ZoomImageView(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      super(var1);
      this.m = new Matrix();
      this.base = new Matrix();
      this.zoom = 1.0F;
      this.maxZoom = 6.0F;
      this.setScaleType(ScaleType.MATRIX);
      this.scaler = new ScaleGestureDetector(var1, (OnScaleGestureListener)(new SimpleOnScaleGestureListener() {

         public boolean onScale(ScaleGestureDetector var1) {
            Intrinsics.checkNotNullParameter(var1, "d");
            ZoomImageView.this.zoomBy(var1.getScaleFactor(), var1.getFocusX(), var1.getFocusY());
            return true;
         }
      }));
      this.gestures = new GestureDetector(var1, (OnGestureListener)(new SimpleOnGestureListener() {

         public boolean onDoubleTap(MotionEvent var1) {
            Intrinsics.checkNotNullParameter(var1, "e");
            if (ZoomImageView.this.zoom > 1.01F) {
               ZoomImageView.this.fit();
            } else {
               ZoomImageView.this.zoomBy(2.5F, var1.getX(), var1.getY());
            }

            return true;
         }

         public boolean onDown(MotionEvent var1) {
            Intrinsics.checkNotNullParameter(var1, "e");
            return true;
         }

         public boolean onScroll(MotionEvent var1, MotionEvent var2, float var3, float var4) {
            Intrinsics.checkNotNullParameter(var2, "e2");
            if (ZoomImageView.this.zoom <= 1.01F) {
               return false;
            } else {
               ZoomImageView.this.m.postTranslate(-var3, -var4);
               ZoomImageView.this.clamp();
               return true;
            }
         }
      }));
   }

   private final void clamp() {
      Drawable var4 = this.getDrawable();
      if (var4 != null) {
         float var3;
         float var8;
         label37: {
            var10 = new RectF(0.0F, 0.0F, var4.getIntrinsicWidth(), var4.getIntrinsicHeight());
            this.m.mapRect(var10);
            float var1 = 0.0F;
            var3 = 0.0F;
            if (var10.width() <= this.getWidth()) {
               var8 = this.getWidth() / 2.0F;
               var1 = var10.centerX();
            } else {
               if (var10.left > 0.0F) {
                  var1 = -var10.left;
               }

               if (!(var10.right < this.getWidth())) {
                  var8 = var1;
                  break label37;
               }

               var8 = this.getWidth();
               var1 = var10.right;
            }

            var8 -= var1;
         }

         float var7;
         label30: {
            if (var10.height() <= this.getHeight()) {
               var7 = this.getHeight() / 2.0F;
               var3 = var10.centerY();
            } else {
               var7 = var3;
               if (var10.top > 0.0F) {
                  var7 = -var10.top;
               }

               if (!(var10.bottom < this.getHeight())) {
                  break label30;
               }

               var7 = this.getHeight();
               var3 = var10.bottom;
            }

            var7 -= var3;
         }

         this.m.postTranslate(var8, var7);
         this.setImageMatrix(this.m);
      }
   }

   private static final void setImageDrawable$lambda$0(ZoomImageView var0) {
      var0.fit();
   }

   private final void zoomBy(float var1, float var2, float var3) {
      var1 = RangesKt.coerceIn(this.zoom * var1, 1.0F, this.maxZoom) / this.zoom;
      this.zoom *= var1;
      this.m.postScale(var1, var1, var2, var3);
      this.clamp();
   }

   public final void fit() {
      Drawable var2 = this.getDrawable();
      if (var2 != null) {
         if (this.getWidth() != 0 && this.getHeight() != 0) {
            Matrix var1 = new Matrix();
            var1.setRectToRect(
               new RectF(0.0F, 0.0F, var2.getIntrinsicWidth(), var2.getIntrinsicHeight()),
               new RectF(0.0F, 0.0F, this.getWidth(), this.getHeight()),
               ScaleToFit.CENTER
            );
            this.base = var1;
            this.m.set(this.base);
            this.zoom = 1.0F;
            this.setImageMatrix(this.m);
         }
      }
   }

   protected void onSizeChanged(int var1, int var2, int var3, int var4) {
      super.onSizeChanged(var1, var2, var3, var4);
      this.fit();
   }

   public boolean onTouchEvent(MotionEvent var1) {
      Intrinsics.checkNotNullParameter(var1, "e");
      this.scaler.onTouchEvent(var1);
      this.gestures.onTouchEvent(var1);
      if (this.zoom > 1.01F) {
         ViewParent var2 = this.getParent();
         if (var2 != null) {
            var2.requestDisallowInterceptTouchEvent(true);
         }
      }

      return true;
   }

   public void setImageDrawable(Drawable var1) {
      super.setImageDrawable(var1);
      this.post(new ZoomImageView$$ExternalSyntheticLambda0(this));
   }
}
