package com.starpilot.starview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.view.MotionEvent;
import android.view.ViewParent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

final class GxCamerasRegionView extends GxCamerasSnapView {
   private String annotating;
   private float downX;
   private float downY;
   private float[] drag;
   private final ArrayList<float[]> left;
   private boolean moved;
   private Function0<Unit> onChanged;
   private Function0<Unit> onClose;
   private final ArrayList<float[]> right;
   private boolean tracking;

   // $VF: synthetic method
   public static Unit $r8$lambda$9s2gVsQW_tszJzQdv30E7snfM00() {
      return onChanged$lambda$0();
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$AEBks_fMaJECELS_Hh_ICRTRSA0() {
      return onClose$lambda$1();
   }

   public GxCamerasRegionView(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      super(var1);
      this.left = new ArrayList<>();
      this.right = new ArrayList<>();
      this.onChanged = new GxCamerasRegionView$$ExternalSyntheticLambda0();
      this.onClose = new GxCamerasRegionView$$ExternalSyntheticLambda1();
   }

   private final float[] hit(float var1, float var2, List<float[]> var3) {
      float var6 = this.u(26.0F);
      Object var7 = null;
      float var4 = Float.MAX_VALUE;
      Iterator var8 = var3.iterator();
      float[] var9 = (float[])var7;

      while (var8.hasNext()) {
         var7 = (float[])var8.next();
         float var5 = (float)Math.hypot(((Object[])var7)[0] - var1, ((Object[])var7)[1] - var2);
         if (var5 < var6 && var5 < var4) {
            var4 = var5;
            var9 = (float[])var7;
         }
      }

      return var9;
   }

   private static final Unit onChanged$lambda$0() {
      return Unit.INSTANCE;
   }

   private static final Unit onClose$lambda$1() {
      return Unit.INSTANCE;
   }

   private final void poly(Canvas var1, List<float[]> var2, int var3, int var4, String var5, boolean var6) {
      if (var2.size() < 2) {
         for (float[] var17 : var2) {
            this.dot(var1, var17[0], var17[1], this.u(7.0F), var4, null);
         }
      } else {
         Path var15 = new Path();
         var15.moveTo(((float[])var2.get(0))[0], ((float[])var2.get(0))[1]);
         int var13 = 1;

         for (int var14 = var2.size(); var13 < var14; var13++) {
            var15.lineTo(((float[])var2.get(var13))[0], ((float[])var2.get(var13))[1]);
         }

         if (var2.size() >= 3) {
            var15.close();
         }

         this.getPaint().reset();
         this.getPaint().setAntiAlias(true);
         if (var2.size() >= 3) {
            this.getPaint().setStyle(Style.FILL);
            this.getPaint().setColor(var3);
            var1.drawPath(var15, this.getPaint());
         }

         this.getPaint().setStyle(Style.STROKE);
         this.getPaint().setColor(var4);
         this.getPaint().setStrokeWidth(this.u(2.5F));
         this.getPaint().setStrokeJoin(Join.ROUND);
         var1.drawPath(var15, this.getPaint());
         Iterator var16 = var2.iterator();

         for (int var18 = 0; var16.hasNext(); var18++) {
            float[] var21 = (float[])var16.next();
            if (var6 && var18 == 0 && var2.size() >= 3) {
               this.dot(var1, var21[0], var21[1], this.u(14.0F), Color.argb(90, 255, 255, 255), var4);
            }

            this.dot(var1, var21[0], var21[1], this.u(7.0F), -1, var4);
         }

         if (var2.size() >= 3) {
            Iterator var22 = var2.iterator();
            double var9 = 0.0;
            double var7 = 0.0;

            while (var22.hasNext()) {
               float[] var24 = (float[])var22.next();
               var7 += var24[0];
            }

            float var12 = (float)var7 / var2.size();
            var16 = var2.iterator();
            var7 = var9;

            while (var16.hasNext()) {
               float[] var23 = (float[])var16.next();
               var7 += var23[1];
            }

            float var11 = (float)var7 / var2.size();
            this.getPaint().reset();
            this.getPaint().setAntiAlias(true);
            this.getPaint().setColor(-1);
            this.getPaint().setTextSize(this.u(13.0F));
            this.getPaint().setTypeface(Typeface.create(Typeface.MONOSPACE, 1));
            this.getPaint().setTextAlign(Align.CENTER);
            this.getPaint().setShadowLayer(this.u(3.0F), 0.0F, 0.0F, -16777216);
            var1.drawText(var5, var12, this.u(5.0F) + var11, this.getPaint());
         }
      }
   }

   @Override
   public void drawOverlay(Canvas var1, Bitmap var2) {
      Intrinsics.checkNotNullParameter(var1, "c");
      Intrinsics.checkNotNullParameter(var2, "b");
      this.poly(
         var1,
         this.left,
         Color.argb(64, 13, 110, 253),
         GxCamerasKt.access$getGX_CAM_BLUE$p(),
         "LEFT SIDE OF VEHICLE - LEFT HERE",
         Intrinsics.areEqual(this.annotating, "left")
      );
      this.poly(
         var1,
         this.right,
         Color.argb(64, 253, 126, 20),
         GxCamerasKt.access$getGX_CAM_ORANGE$p(),
         "RIGHT SIDE OF VEHICLE - RIGHT HERE",
         Intrinsics.areEqual(this.annotating, "right")
      );
   }

   public final String getAnnotating() {
      return this.annotating;
   }

   public final ArrayList<float[]> getLeft() {
      return this.left;
   }

   public final Function0<Unit> getOnChanged() {
      return this.onChanged;
   }

   public final Function0<Unit> getOnClose() {
      return this.onClose;
   }

   public final ArrayList<float[]> getRight() {
      return this.right;
   }

   public boolean onTouchEvent(MotionEvent var1) {
      Intrinsics.checkNotNullParameter(var1, "e");
      if (this.getBmp() == null) {
         return false;
      } else {
         float var3 = RangesKt.coerceIn(this.cx(var1.getX()), 0.0F, this.getCw());
         float var2 = RangesKt.coerceIn(this.cy(var1.getY()), 0.0F, this.getCh());
         switch (var1.getActionMasked()) {
            case 0:
               String var6;
               float[] var13;
               label102: {
                  var6 = this.annotating;
                  ArrayList var12;
                  if (Intrinsics.areEqual(var6, "left")) {
                     var12 = this.left;
                  } else {
                     if (!Intrinsics.areEqual(var6, "right")) {
                        float[] var5 = this.hit(var3, var2, this.left);
                        var13 = var5;
                        if (var5 != null) {
                           break label102;
                        }
                     }

                     var12 = this.right;
                  }

                  var13 = this.hit(var3, var2, var12);
               }

               this.drag = var13;
               if (var6 == null && this.drag == null) {
                  return false;
               } else {
                  this.tracking = true;
                  this.moved = false;
                  this.downX = var1.getX();
                  this.downY = var1.getY();
                  ViewParent var10 = this.getParent();
                  if (var10 != null) {
                     var10.requestDisallowInterceptTouchEvent(true);
                  }

                  return true;
               }
            case 1:
               if (!this.tracking) {
                  return false;
               } else {
                  this.tracking = false;
                  ViewParent var4 = this.getParent();
                  if (var4 != null) {
                     var4.requestDisallowInterceptTouchEvent(false);
                  }

                  if (this.moved) {
                     if (this.drag != null) {
                        this.onChanged.invoke();
                     }

                     this.drag = null;
                     return true;
                  } else {
                     this.drag = null;
                     String var11 = this.annotating;
                     if (var11 == null) {
                        return true;
                     } else {
                        if (!(this.cx(var1.getX()) < 0.0F)
                           && !(this.cy(var1.getY()) < 0.0F)
                           && !(this.cx(var1.getX()) > this.getCw())
                           && !(this.cy(var1.getY()) > this.getCh())) {
                           ArrayList var9;
                           if (Intrinsics.areEqual(var11, "left")) {
                              var9 = this.left;
                           } else {
                              var9 = this.right;
                           }

                           if (var9.size() >= 3 && (float)Math.hypot(var3 - ((float[])var9.get(0))[0], var2 - ((float[])var9.get(0))[1]) < this.u(26.0F)) {
                              this.onClose.invoke();
                              return true;
                           }

                           if (var9.size() >= 64) {
                              return true;
                           }

                           var9.add(new float[]{GxCamerasKt.access$gxCamRound(var3), GxCamerasKt.access$gxCamRound(var2)});
                           this.onChanged.invoke();
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

               float[] var8 = this.drag;
               if (this.moved && var8 != null) {
                  var8[0] = var3;
                  var8[1] = var2;
                  this.invalidate();
               }

               return true;
            case 3:
               this.tracking = false;
               this.drag = null;
               ViewParent var7 = this.getParent();
               if (var7 != null) {
                  var7.requestDisallowInterceptTouchEvent(false);
               }

               return true;
            default:
               return this.tracking;
         }
      }
   }

   public final void setAnnotating(String var1) {
      this.annotating = var1;
   }

   public final void setOnChanged(Function0<Unit> var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.onChanged = var1;
   }

   public final void setOnClose(Function0<Unit> var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.onClose = var1;
   }
}
