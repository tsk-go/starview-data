package com.starpilot.starview;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

public final class Projection {
   private static final Projection.Companion.Cam AR_OX_ECAM = new Projection.Companion.Cam(1928, 1208, 567.0);
   private static final Projection.Companion.Cam AR_OX_FCAM = new Projection.Companion.Cam(1928, 1208, 2648.0);
   public static final double CLIP_MARGIN = 500.0;
   public static final Projection.Companion Companion = new Projection.Companion(null);
   public static final double HEIGHT_INIT = 1.22;
   private static final Projection.Companion.Cam OS_ECAM = new Projection.Companion.Cam(1344, 760, 425.25);
   private static final Projection.Companion.Cam OS_FCAM = new Projection.Companion.Cam(1344, 760, 1141.5);
   private static final double[] VIEW_FROM_DEVICE = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0};
   private double[] calib;
   private Projection.Companion.Cam camNow;
   private int frameH;
   private int frameW;
   private double[] full;
   private double fullH;
   private double fullW;
   private double pathOffsetZ;
   private double[] rpy = new double[]{0.0, 0.0, 0.0};
   private boolean valid;
   private double videoScale;
   private double videoTx;
   private double videoTy;
   private double viewH;
   private double viewW;
   private boolean wide;
   private double xOffset;
   private double yOffset;
   private double zoom;

   public Projection() {
      this.pathOffsetZ = 1.22;
      this.zoom = 1.0;
      this.videoScale = 1.0;
      this.full = new double[9];
      this.calib = new double[9];
      this.camNow = OS_FCAM;
   }

   public final Projection.Companion.Cam cam() {
      Projection.Companion.Cam var1;
      if (this.frameH > 0) {
         var1 = Companion.camFor(this.frameW, this.frameH, this.wide);
      } else if (this.frameW == 1344) {
         if (this.wide) {
            var1 = OS_ECAM;
         } else {
            var1 = OS_FCAM;
         }
      } else if (this.wide) {
         var1 = AR_OX_ECAM;
      } else {
         var1 = AR_OX_FCAM;
      }

      return var1;
   }

   public final int getFrameH() {
      return this.frameH;
   }

   public final int getFrameW() {
      return this.frameW;
   }

   public final double getFullH() {
      return this.fullH;
   }

   public final double getFullW() {
      return this.fullW;
   }

   public final double getPathOffsetZ() {
      return this.pathOffsetZ;
   }

   public final double[] getRpy() {
      return this.rpy;
   }

   public final boolean getValid() {
      return this.valid;
   }

   public final double getVideoScale() {
      return this.videoScale;
   }

   public final double getVideoTx() {
      return this.videoTx;
   }

   public final double getVideoTy() {
      return this.videoTy;
   }

   public final double getViewH() {
      return this.viewH;
   }

   public final double getViewW() {
      return this.viewW;
   }

   public final boolean getWide() {
      return this.wide;
   }

   public final double getXOffset() {
      return this.xOffset;
   }

   public final double getYOffset() {
      return this.yOffset;
   }

   public final double getZoom() {
      return this.zoom;
   }

   public final boolean map(double var1, double var3, double var5, float[] var7) {
      Intrinsics.checkNotNullParameter(var7, "out");
      if (this.wide) {
         double[] var9 = Companion.mulv(this.calib, var1, var3, var5);
         if (var9[2] < 1.0E-6) {
            return false;
         }

         Projection.Companion.Cam var8 = this.camNow;
         var9 = Companion.pinholeToFisheye(var9[0] / var9[2] - var8.getCx(), var9[1] / var9[2] - var8.getCy(), var8.getF());
         var3 = (var8.getCx() + var9[0]) * this.videoScale + this.videoTx;
         var1 = (var8.getCy() + var9[1]) * this.videoScale + this.videoTy;
      } else {
         double[] var12 = Companion.mulv(this.full, var1, var3, var5);
         if (Math.abs(var12[2]) < 1.0E-6) {
            return false;
         }

         var3 = var12[0] / var12[2];
         var1 = var12[1] / var12[2];
      }

      if (!(var3 < -500.0) && !(var3 > this.viewW + 500.0) && !(var1 < -500.0) && !(var1 > this.viewH + 500.0)) {
         var7[0] = (float)var3;
         var7[1] = (float)var1;
         return true;
      } else {
         return false;
      }
   }

   public final float[] ribbon(float[] var1, float[] var2, float[] var3, double var4, double var6, int var8, boolean var9) {
      Intrinsics.checkNotNullParameter(var1, "x");
      Intrinsics.checkNotNullParameter(var2, "y");
      Intrinsics.checkNotNullParameter(var3, "z");
      int var15 = Math.min(var8 + 1, Math.min(var1.length, Math.min(var2.length, var3.length)));
      if (var15 < 2) {
         return null;
      } else {
         float[] var16 = new float[var15];
         float[] var19 = new float[var15];
         float[] var20 = new float[var15];
         float[] var18 = new float[var15];
         float[] var17 = new float[2];
         var8 = 0;
         float var10 = Float.MAX_VALUE;

         for (int var14 = 0; var14 < var15; var14++) {
            if (this.map(var1[var14], var2[var14] - var4, var3[var14] + var6, var17)) {
               float var13 = var17[0];
               float var12 = var17[1];
               if (this.map(var1[var14], var2[var14] + var4, var3[var14] + var6, var17)) {
                  float var11 = var10;
                  if (!var9) {
                     if (var12 > var10) {
                        continue;
                     }

                     var11 = var12;
                  }

                  var16[var8] = var13;
                  var19[var8] = var12;
                  var20[var8] = var17[0];
                  var18[var8] = var17[1];
                  var8++;
                  var10 = var11;
               }
            }
         }

         if (var8 < 2) {
            return null;
         } else {
            var1 = new float[var8 * 4];

            for (int var23 = 0; var23 < var8; var23++) {
               var1[var23 * 2] = var16[var23];
               var1[var23 * 2 + 1] = var19[var23];
            }

            for (int var24 = 0; var24 < var8; var24++) {
               var15 = var8 - 1 - var24;
               var1[(var8 + var24) * 2] = var20[var15];
               var1[(var8 + var24) * 2 + 1] = var18[var15];
            }

            return var1;
         }
      }
   }

   public final void setFrameH(int var1) {
      this.frameH = var1;
   }

   public final void setFrameW(int var1) {
      this.frameW = var1;
   }

   public final void setFullH(double var1) {
      this.fullH = var1;
   }

   public final void setFullW(double var1) {
      this.fullW = var1;
   }

   public final void setPathOffsetZ(double var1) {
      this.pathOffsetZ = var1;
   }

   public final void setRpy(double[] var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.rpy = var1;
   }

   public final void setValid(boolean var1) {
      this.valid = var1;
   }

   public final void setVideoScale(double var1) {
      this.videoScale = var1;
   }

   public final void setVideoTx(double var1) {
      this.videoTx = var1;
   }

   public final void setVideoTy(double var1) {
      this.videoTy = var1;
   }

   public final void setViewH(double var1) {
      this.viewH = var1;
   }

   public final void setViewW(double var1) {
      this.viewW = var1;
   }

   public final void setWide(boolean var1) {
      this.wide = var1;
   }

   public final void setXOffset(double var1) {
      this.xOffset = var1;
   }

   public final void setYOffset(double var1) {
      this.yOffset = var1;
   }

   public final void setZoom(double var1) {
      this.zoom = var1;
   }

   public final void update() {
      if (this.frameW != 0) {
         boolean var15;
         if (this.viewW == 0.0) {
            var15 = 1;
         } else {
            var15 = 0;
         }

         if (!var15) {
            Projection.Companion.Cam var16 = this.cam();
            double var1 = var16.getF();
            double var3 = var16.getCx();
            double var5 = var16.getF();
            double var7 = var16.getCy();
            double[] var17 = new double[]{var1, 0.0, var3, 0.0, var5, var7, 0.0, 0.0, 1.0};
            double[] var18 = Companion.rotFromEuler(this.rpy[0], this.rpy[1], this.rpy[2]);
            var18 = Companion.mul(VIEW_FROM_DEVICE, var18);
            var17 = Companion.mul(var17, var18);
            var18 = Companion.mulv(var17, 1000.0, 0.0, 0.0);
            var7 = this.viewW;
            double var9 = this.viewH;
            var15 = this.frameH;
            var1 = 1344.0;
            if ((var15 <= 0 || !((double)this.frameW / this.frameH > 1.68)) && this.frameW != 1344) {
               var1 = 1928.0;
            }

            if (this.wide) {
               var3 = 2.0;
            } else {
               var3 = 1.1;
            }

            var1 = var3 * var1 / var16.getW();
            if (this.fullW > 0.0 && var7 < this.fullW * 0.98) {
               var5 = Math.max(this.fullH, 1.0);
               double var11 = this.fullW;
               var3 = 2;
               var1 = Math.max(var1, Math.max(var11 / (var16.getCx() * var3), var5 / (var16.getCy() * var3)));
               var1 = Math.max(var7 / this.fullW * var1, var7 / (var3 * var16.getCx()));
            } else {
               var3 = 2;
               var1 = Math.max(var1, Math.max(var7 / (var16.getCx() * var3), var9 / (var3 * var16.getCy())));
            }

            var3 = var16.getCx();
            double var34 = 2;
            double var13 = Math.max(0.0, var3 * var1 - var7 / var34 - 5.0);
            var3 = Math.max(0.0, var16.getCy() * var1 - var9 / var34 - 5.0);
            var5 = 0.0;
            if (Math.abs(var18[2]) > 1.0E-6) {
               var5 = RangesKt.coerceIn((var18[0] / var18[2] - var16.getCx()) * var1, -var13, var13);
               var3 = RangesKt.coerceIn((var18[1] / var18[2] - var16.getCy()) * var1, -var3, var3);
            } else {
               var3 = 0.0;
            }

            this.zoom = var1;
            this.xOffset = var5;
            this.yOffset = var3;
            this.videoScale = var1;
            this.videoTx = var7 / var34 - var5 - var16.getCx() * var1;
            this.videoTy = var9 / var34 - var3 - var16.getCy() * var1;
            var5 = this.videoTx;
            var3 = this.videoTy;
            this.full = Companion.mul(new double[]{var1, 0.0, var5, 0.0, var1, var3, 0.0, 0.0, 1.0}, var17);
            this.calib = var17;
            this.camNow = var16;
            this.valid = true;
            return;
         }
      }

      this.valid = false;
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      public final Projection.Companion.Cam camFor(int var1, int var2, boolean var3) {
         boolean var4;
         if ((double)var1 / var2 > 1.68) {
            var4 = true;
         } else {
            var4 = false;
         }

         Projection.Companion.Cam var5;
         if (var4) {
            if (var3) {
               var5 = this.getOS_ECAM();
            } else {
               var5 = this.getOS_FCAM();
            }
         } else if (var3) {
            var5 = this.getAR_OX_ECAM();
         } else {
            var5 = this.getAR_OX_FCAM();
         }

         return new Projection.Companion.Cam(var1, var2, var5.getF() * var1 / var5.getW());
      }

      public final double[] fisheyeToPinhole(double var1, double var3, double var5) {
         double var7 = Math.hypot(var1, var3);
         if (var7 < 1.0E-9) {
            return new double[]{var1, var3};
         } else {
            double var9 = var7 / var5;
            if (var9 > 1.4) {
               return null;
            } else {
               var5 = Math.tan(var9) * var5 / var7;
               return new double[]{var1 * var5, var3 * var5};
            }
         }
      }

      public final Projection.Companion.Cam getAR_OX_ECAM() {
         return Projection.AR_OX_ECAM;
      }

      public final Projection.Companion.Cam getAR_OX_FCAM() {
         return Projection.AR_OX_FCAM;
      }

      public final Projection.Companion.Cam getOS_ECAM() {
         return Projection.OS_ECAM;
      }

      public final Projection.Companion.Cam getOS_FCAM() {
         return Projection.OS_FCAM;
      }

      public final double[] getVIEW_FROM_DEVICE() {
         return Projection.VIEW_FROM_DEVICE;
      }

      public final double[] groundHomographyRows(float[] var1, float var2, int var3, int var4, boolean var5) {
         Intrinsics.checkNotNullParameter(var1, "rpy");
         Projection.Companion.Cam var14 = this.camFor(var3, var4, var5);
         double var8 = var14.getF();
         double var10 = var14.getCx();
         double var12 = var14.getF();
         double var6 = var14.getCy();
         double[] var15 = this.mul(this.getVIEW_FROM_DEVICE(), this.rotFromEuler(var1[0], var1[1], var1[2]));
         double[] var16 = this.mul(new double[]{var8, 0.0, var10, 0.0, var12, var6, 0.0, 0.0, 1.0}, var15);
         return new double[]{var16[0], var16[1], var2 * var16[2], var16[3], var16[4], var2 * var16[5], var16[6], var16[7], var2 * var16[8]};
      }

      public final Projection.Companion.GroundInverse groundInverse(float[] var1, float var2, int var3, int var4, boolean var5) {
         Intrinsics.checkNotNullParameter(var1, "rpy");
         Projection.Companion.Cam var6 = this.camFor(var3, var4, var5);
         return new Projection.Companion.GroundInverse(
            (float)var6.getF(), (float)var6.getCx(), (float)var6.getCy(), this.inverse3(this.groundHomographyRows(var1, var2, var3, var4, var5)), var5
         );
      }

      public final double[] inverse3(double[] var1) {
         Intrinsics.checkNotNullParameter(var1, "m");
         double var14 = var1[0];
         double var2 = var1[1];
         double var10 = var1[2];
         double var16 = var1[3];
         double var18 = var1[4];
         double var4 = var1[5];
         double var8 = var1[6];
         double var6 = var1[7];
         double var12 = var1[8];
         double var20 = (var18 * var12 - var4 * var6) * var14 - (var16 * var12 - var4 * var8) * var2 + (var16 * var6 - var18 * var8) * var10;
         if (Math.abs(var20) < 1.0E-12) {
            return null;
         } else {
            var20 = 1 / var20;
            return new double[]{
               (var18 * var12 - var4 * var6) * var20,
               (var10 * var6 - var2 * var12) * var20,
               (var2 * var4 - var10 * var18) * var20,
               (var4 * var8 - var16 * var12) * var20,
               (var14 * var12 - var10 * var8) * var20,
               (var10 * var16 - var14 * var4) * var20,
               (var16 * var6 - var18 * var8) * var20,
               (var2 * var8 - var14 * var6) * var20,
               (var14 * var18 - var2 * var16) * var20
            };
         }
      }

      public final double[] mul(double[] var1, double[] var2) {
         Intrinsics.checkNotNullParameter(var1, "a");
         Intrinsics.checkNotNullParameter(var2, "b");
         double[] var5 = new double[9];

         for (int var3 = 0; var3 < 3; var3++) {
            for (int var4 = 0; var4 < 3; var4++) {
               var5[var3 * 3 + var4] = var1[var3 * 3] * var2[var4] + var1[var3 * 3 + 1] * var2[var4 + 3] + var1[var3 * 3 + 2] * var2[var4 + 6];
            }
         }

         return var5;
      }

      public final double[] mulv(double[] var1, double var2, double var4, double var6) {
         Intrinsics.checkNotNullParameter(var1, "a");
         return new double[]{
            var1[0] * var2 + var1[1] * var4 + var1[2] * var6,
            var1[3] * var2 + var1[4] * var4 + var1[5] * var6,
            var1[6] * var2 + var1[7] * var4 + var1[8] * var6
         };
      }

      public final double[] pinholeToFisheye(double var1, double var3, double var5) {
         double var7 = Math.hypot(var1, var3);
         if (var7 < 1.0E-9) {
            return new double[]{var1, var3};
         } else {
            var5 = Math.atan(var7 / var5) * var5 / var7;
            return new double[]{var1 * var5, var3 * var5};
         }
      }

      public final double[] rotFromEuler(double var1, double var3, double var5) {
         double var7 = Math.cos(var1);
         double var9 = Math.sin(var1);
         var1 = Math.cos(var3);
         var3 = Math.sin(var3);
         double var11 = Math.cos(var5);
         double var15 = Math.sin(var5);
         double var17 = -var9;
         var5 = -var3;
         double var13 = -var15;
         double[] var19 = this.mul(
            new double[]{var1, 0.0, var3, 0.0, 1.0, 0.0, var5, 0.0, var1}, new double[]{1.0, 0.0, 0.0, 0.0, var7, var17, 0.0, var9, var7}
         );
         return this.mul(new double[]{var11, var13, 0.0, var15, var11, 0.0, 0.0, 0.0, 1.0}, var19);
      }

      public static final class Cam {
         private final double f;
         private final int h;
         private final int w;

         public Cam(int var1, int var2, double var3) {
            this.w = var1;
            this.h = var2;
            this.f = var3;
         }

         // $VF: synthetic method
         public static Projection.Companion.Cam copy$default(Projection.Companion.Cam var0, int var1, int var2, double var3, int var5, Object var6) {
            if ((var5 & 1) != 0) {
               var1 = var0.w;
            }

            if ((var5 & 2) != 0) {
               var2 = var0.h;
            }

            if ((var5 & 4) != 0) {
               var3 = var0.f;
            }

            return var0.copy(var1, var2, var3);
         }

         public final int component1() {
            return this.w;
         }

         public final int component2() {
            return this.h;
         }

         public final double component3() {
            return this.f;
         }

         public final Projection.Companion.Cam copy(int var1, int var2, double var3) {
            return new Projection.Companion.Cam(var1, var2, var3);
         }

         @Override
         public boolean equals(Object var1) {
            if (this == var1) {
               return true;
            } else if (!(var1 instanceof Projection.Companion.Cam)) {
               return false;
            } else {
               var1 = var1;
               if (this.w != var1.w) {
                  return false;
               } else {
                  return this.h != var1.h ? false : Double.compare(this.f, var1.f) == 0;
               }
            }
         }

         public final double getCx() {
            return this.w / 2.0;
         }

         public final double getCy() {
            return this.h / 2.0;
         }

         public final double getF() {
            return this.f;
         }

         public final int getH() {
            return this.h;
         }

         public final int getW() {
            return this.w;
         }

         @Override
         public int hashCode() {
            return (Integer.hashCode(this.w) * 31 + Integer.hashCode(this.h)) * 31 + Double.hashCode(this.f);
         }

         @Override
         public String toString() {
            int var3 = this.w;
            int var4 = this.h;
            double var1 = this.f;
            return "Cam(w=" + var3 + ", h=" + var4 + ", f=" + var1 + ")";
         }
      }

      public static final class GroundInverse {
         private final float cx;
         private final float cy;
         private final float f;
         private final boolean fisheye;
         private final double[] hInv;

         public GroundInverse(float var1, float var2, float var3, double[] var4, boolean var5) {
            this.f = var1;
            this.cx = var2;
            this.cy = var3;
            this.hInv = var4;
            this.fisheye = var5;
         }

         // $VF: synthetic method
         public GroundInverse(float var1, float var2, float var3, double[] var4, boolean var5, int var6, DefaultConstructorMarker var7) {
            if ((var6 & 16) != 0) {
               var5 = false;
            }

            this(var1, var2, var3, var4, var5);
         }

         public final float getCx() {
            return this.cx;
         }

         public final float getCy() {
            return this.cy;
         }

         public final float getF() {
            return this.f;
         }

         public final boolean getFisheye() {
            return this.fisheye;
         }

         public final float[] ground(float var1, float var2) {
            double[] var26 = this.hInv;
            if (var26 == null) {
               return null;
            } else {
               float[] var25 = this.undistort(var1, var2);
               if (var25 == null) {
                  return null;
               } else {
                  var2 = var25[0];
                  var1 = var25[1];
                  double var21 = var26[0];
                  double var15 = var2;
                  double var23 = var26[1];
                  double var19 = var1;
                  double var17 = var26[2];
                  double var5 = var26[3];
                  double var7 = var2;
                  double var9 = var26[4];
                  double var11 = var1;
                  double var13 = var26[5];
                  double var3 = var26[6] * var2 + var26[7] * var1 + var26[8];
                  if (var3 < 1.0E-6) {
                     return null;
                  } else {
                     var15 = (var21 * var15 + var23 * var19 + var17) / var3;
                     return var15 <= 0.0 ? null : new float[]{(float)var15, (float)((var5 * var7 + var9 * var11 + var13) / var3)};
                  }
               }
            }
         }

         public final float offAxis(float var1, float var2) {
            var1 = (float)Math.hypot(var1 - this.cx, var2 - this.cy);
            if (this.fisheye) {
               var1 /= this.f;
            } else {
               var1 = (float)Math.atan(var1 / this.f);
            }

            return var1;
         }

         public final float[] undistort(float var1, float var2) {
            if (!this.fisheye) {
               return new float[]{var1, var2};
            } else {
               double[] var3 = Projection.Companion.fisheyeToPinhole(var1 - this.cx, var2 - this.cy, this.f);
               return var3 == null ? null : new float[]{(float)(this.cx + var3[0]), (float)(this.cy + var3[1])};
            }
         }
      }
   }
}
