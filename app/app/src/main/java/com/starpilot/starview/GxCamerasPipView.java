package com.starpilot.starview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path.Direction;
import android.view.MotionEvent;
import android.view.ViewParent;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

final class GxCamerasPipView extends GxCamerasSnapView {
   private String armSide;
   private float downX;
   private float downY;
   private float[] drag;
   private boolean dragLeft;
   private boolean isC4;
   private float[] leftC;
   private boolean moved;
   private Function0<Unit> onChanged;
   private Function0<Unit> onPlaced;
   private Function1<? super String, Unit> onWrongHalf;
   private float[] rightC;
   private boolean tracking;
   private float zoom;

   // $VF: synthetic method
   public static Unit $r8$lambda$0SOIIIVFJh4gEEgonmIYyS_V894() {
      return onPlaced$lambda$1();
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$5TBRSZQkdct6xJuqc3AbcuxOfR4(String var0) {
      return onWrongHalf$lambda$2(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$xgID3vXXZa1hjqSJ_7yk5fZ7xEY() {
      return onChanged$lambda$0();
   }

   public GxCamerasPipView(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      super(var1);
      this.zoom = 240.0F;
      this.onChanged = new GxCamerasPipView$$ExternalSyntheticLambda0();
      this.onPlaced = new GxCamerasPipView$$ExternalSyntheticLambda1();
      this.onWrongHalf = new GxCamerasPipView$$ExternalSyntheticLambda2();
   }

   private final void c3(Canvas var1, Bitmap var2, float[] var3, int var4) {
      Pair var12 = TuplesKt.to(var3[0], var3[1]);
      float var6 = ((Number)var12.component1()).floatValue();
      float var7 = ((Number)var12.component2()).floatValue();
      float var8 = this.zoom;
      float var5 = 2;
      var8 /= var5;
      float var11 = (this.getCw() - var6) * this.nW() / this.getCw();
      float var9 = this.nH() * var7 / this.getCh();
      float var10 = this.zoom * this.nW() / this.getCw();
      Path var13 = new Path();
      var13.addCircle(var6, var7, var8, Direction.CW);
      var1.save();
      var1.clipPath(var13);
      this.getPaint().reset();
      this.getPaint().setColor(-16777216);
      var1.drawCircle(var6, var7, var8, this.getPaint());
      var1.scale(-1.0F, 1.0F, var6, 0.0F);
      this.drawCrop(
         var1,
         var2,
         new RectF(var11 - var10 / var5, var9 - var10 / var5, var10 / var5 + var11, var10 / var5 + var9),
         new RectF(var6 - var8, var7 - var8, var6 + var8, var7 + var8)
      );
      var1.restore();
      this.getPaint().reset();
      this.getPaint().setAntiAlias(true);
      this.getPaint().setStyle(Style.STROKE);
      this.getPaint().setColor(var4);
      this.getPaint().setStrokeWidth(this.u(2.5F));
      var1.drawCircle(var6, var7, var8, this.getPaint());
   }

   private final void c4(Canvas var1, Bitmap var2, float[] var3, int var4) {
      Pair var14 = TuplesKt.to(var3[0], var3[1]);
      float var8 = ((Number)var14.component1()).floatValue();
      float var10 = ((Number)var14.component2()).floatValue();
      float var6 = Math.max(60.0F, Math.min(this.zoom * 1.1F, this.getCw() * 0.55F));
      float var7 = var6 / 1.9833333F;
      float var5 = var7;
      if (var7 > this.getCh() * 0.5F) {
         var5 = this.getCh() * 0.5F;
         var6 = var5 * 1.9833333F;
      }

      var7 = 2;
      RectF var15 = new RectF(var8 - var6 / var7, var10 - var5 / var7, var6 / var7 + var8, var5 / var7 + var10);
      float var9 = (this.getCw() - var8) * this.nW() / this.getCw();
      float var12 = this.nH() * var10 / this.getCh();
      float var11 = this.zoom * this.nW() / this.getCw();
      var10 = var11 / (var6 / var5);
      var5 = Math.min(var6, var5) * 0.22F;
      Path var13 = new Path();
      var13.addRoundRect(var15, var5, var5, Direction.CW);
      var1.save();
      var1.clipPath(var13);
      this.getPaint().reset();
      this.getPaint().setColor(-16777216);
      var1.drawRect(var15, this.getPaint());
      var1.scale(-1.0F, 1.0F, var8, 0.0F);
      this.drawCrop(var1, var2, new RectF(var9 - var11 / var7, var12 - var10 / var7, var9 + var11 / var7, var10 / var7 + var12), var15);
      var1.restore();
      this.getPaint().reset();
      this.getPaint().setAntiAlias(true);
      this.getPaint().setStyle(Style.STROKE);
      this.getPaint().setColor(var4);
      this.getPaint().setStrokeWidth(this.u(2.5F));
      var1.drawRoundRect(var15, var5, var5, this.getPaint());
   }

   private final float nH() {
      Bitmap var2 = this.getBmp();
      int var1;
      if (var2 != null) {
         var1 = var2.getHeight();
      } else {
         var1 = 1080;
      }

      return var1;
   }

   private final float nW() {
      Bitmap var2 = this.getBmp();
      int var1;
      if (var2 != null) {
         var1 = var2.getWidth();
      } else {
         var1 = 1920;
      }

      return var1;
   }

   private static final Unit onChanged$lambda$0() {
      return Unit.INSTANCE;
   }

   private static final Unit onPlaced$lambda$1() {
      return Unit.INSTANCE;
   }

   private static final Unit onWrongHalf$lambda$2(String var0) {
      Intrinsics.checkNotNullParameter(var0, "it");
      return Unit.INSTANCE;
   }

   @Override
   public void drawOverlay(Canvas var1, Bitmap var2) {
      Intrinsics.checkNotNullParameter(var1, "c");
      Intrinsics.checkNotNullParameter(var2, "b");
      float var4 = this.zoom;
      float var3 = 2;
      var4 /= var3;

      for (Triple var8 : CollectionsKt.listOf(
         new Triple[]{
            new Triple<>(this.leftC, GxCamerasKt.access$getGX_CAM_BLUE$p(), "LEFT SIDE OF VEHICLE - LEFT HERE"),
            new Triple<>(this.rightC, GxCamerasKt.access$getGX_CAM_ORANGE$p(), "RIGHT SIDE OF VEHICLE - RIGHT HERE")
         }
      )) {
         float[] var6 = (float[])var8.component1();
         int var5 = ((Number)var8.component2()).intValue();
         String var12 = (String)var8.component3();
         if (var6 != null) {
            if (this.isC4) {
               this.c4(var1, var2, var6, var5);
            } else {
               this.c3(var1, var2, var6, var5);
            }

            this.dot(var1, var6[0], var6[1], this.u(6.0F), -1, var5);
            this.getPaint().reset();
            this.getPaint().setAntiAlias(true);
            this.getPaint().setColor(var5);
            this.getPaint().setTextSize(this.u(13.0F));
            this.getPaint().setTypeface(Typeface.create(Typeface.MONOSPACE, 1));
            this.getPaint().setTextAlign(Align.CENTER);
            this.getPaint().setShadowLayer(this.u(3.0F), 0.0F, 0.0F, -16777216);
            var1.drawText(var12, var6[0], var6[1] - var4 - this.u(8.0F), this.getPaint());
         }
      }

      String var9 = this.armSide;
      if (var9 != null) {
         this.getPaint().reset();
         this.getPaint().setAntiAlias(true);
         this.getPaint().setColor(-1);
         this.getPaint().setTextSize(this.u(15.0F));
         this.getPaint().setTypeface(Typeface.create(Typeface.MONOSPACE, 1));
         this.getPaint().setTextAlign(Align.CENTER);
         this.getPaint().setShadowLayer(this.u(3.0F), 0.0F, 0.0F, -16777216);
         String var10;
         if (Intrinsics.areEqual(var9, "left")) {
            var10 = "Tap LEFT HERE for the LEFT SIDE OF VEHICLE";
         } else {
            var10 = "Tap RIGHT HERE for the RIGHT SIDE OF VEHICLE";
         }

         var1.drawText(var10, this.getCw() / var3, this.getCh() - this.u(18.0F), this.getPaint());
      }
   }

   public final String getArmSide() {
      return this.armSide;
   }

   public final float[] getLeftC() {
      return this.leftC;
   }

   public final Function0<Unit> getOnChanged() {
      return this.onChanged;
   }

   public final Function0<Unit> getOnPlaced() {
      return this.onPlaced;
   }

   public final Function1<String, Unit> getOnWrongHalf() {
      return this.onWrongHalf;
   }

   public final float[] getRightC() {
      return this.rightC;
   }

   public final float getZoom() {
      return this.zoom;
   }

   public final boolean isC4() {
      return this.isC4;
   }

   public boolean onTouchEvent(MotionEvent var1) {
      Intrinsics.checkNotNullParameter(var1, "e");
      if (this.getBmp() == null) {
         return false;
      } else {
         float var4 = this.cx(var1.getX());
         float var3 = this.cy(var1.getY());
         switch (var1.getActionMasked()) {
            case 0:
               this.drag = null;
               if (this.armSide == null) {
                  float var14 = this.u(36.0F);
                  float[] var7 = this.leftC;
                  float[] var16 = this.rightC;
                  if (var7 != null && (float)Math.hypot(var7[0] - var4, var7[1] - var3) < var14) {
                     this.drag = var7;
                     this.dragLeft = true;
                  } else if (var16 != null && (float)Math.hypot(var16[0] - var4, var16[1] - var3) < var14) {
                     this.drag = var16;
                     this.dragLeft = false;
                  }

                  if (this.drag == null) {
                     return false;
                  }
               }

               this.tracking = true;
               this.moved = false;
               this.downX = var1.getX();
               this.downY = var1.getY();
               ViewParent var12 = this.getParent();
               if (var12 != null) {
                  var12.requestDisallowInterceptTouchEvent(true);
               }

               return true;
            case 1:
               if (!this.tracking) {
                  return false;
               } else {
                  this.tracking = false;
                  ViewParent var10 = this.getParent();
                  if (var10 != null) {
                     var10.requestDisallowInterceptTouchEvent(false);
                  }

                  if (this.drag != null) {
                     this.drag = null;
                     if (this.moved) {
                        this.onChanged.invoke();
                     }

                     return true;
                  } else {
                     String var6 = this.armSide;
                     if (var6 == null) {
                        return true;
                     } else {
                        if (!(var4 < 0.0F) && !(var3 < 0.0F) && !(var4 > this.getCw()) && !(var3 > this.getCh())) {
                           boolean var5;
                           if (var4 < this.getCw() / 2) {
                              var5 = true;
                           } else {
                              var5 = false;
                           }

                           if ((!Intrinsics.areEqual(var6, "left") || var5) && (!Intrinsics.areEqual(var6, "right") || !var5)) {
                              float var13 = GxCamerasKt.access$gxCamRound(var4);
                              var3 = GxCamerasKt.access$gxCamRound(var3);
                              float[] var11 = new float[]{var13, var3};
                              if (Intrinsics.areEqual(var6, "left")) {
                                 this.leftC = var11;
                              } else {
                                 this.rightC = var11;
                              }

                              this.armSide = null;
                              this.onPlaced.invoke();
                              return true;
                           }

                           this.onWrongHalf.invoke(var6);
                           this.invalidate();
                           return true;
                        }

                        return true;
                     }
                  }
               }
            case 2:
               if (!this.tracking) {
                  return false;
               }

               if (!this.moved && (float)Math.hypot(var1.getX() - this.downX, var1.getY() - this.downY) > 10 * this.getDensity()) {
                  this.moved = true;
               }

               float[] var9 = this.drag;
               if (this.moved && var9 != null) {
                  float var2;
                  if (this.dragLeft) {
                     var2 = RangesKt.coerceIn(var4, 0.0F, this.getCw() / 2 - 1);
                  } else {
                     var2 = RangesKt.coerceIn(var4, this.getCw() / 2, this.getCw());
                  }

                  var9[0] = var2;
                  var9[1] = RangesKt.coerceIn(var3, 0.0F, this.getCh());
                  this.invalidate();
               }

               return true;
            case 3:
               this.tracking = false;
               this.drag = null;
               ViewParent var8 = this.getParent();
               if (var8 != null) {
                  var8.requestDisallowInterceptTouchEvent(false);
               }

               return true;
            default:
               return this.tracking;
         }
      }
   }

   public final void setArmSide(String var1) {
      this.armSide = var1;
   }

   public final void setC4(boolean var1) {
      this.isC4 = var1;
   }

   public final void setLeftC(float[] var1) {
      this.leftC = var1;
   }

   public final void setOnChanged(Function0<Unit> var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.onChanged = var1;
   }

   public final void setOnPlaced(Function0<Unit> var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.onPlaced = var1;
   }

   public final void setOnWrongHalf(Function1<? super String, Unit> var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.onWrongHalf = var1;
   }

   public final void setRightC(float[] var1) {
      this.rightC = var1;
   }

   public final void setZoom(float var1) {
      this.zoom = var1;
   }
}
