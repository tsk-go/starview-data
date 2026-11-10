package com.starpilot.starview;

import java.util.Arrays;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;

public final class GpsFilter {
   public static final GpsFilter.Companion Companion = new GpsFilter.Companion(null);
   public static final double LEVER_M = 2.2;
   private double[] anc;
   private double ancAcc;
   private long ancMs;
   private double[] ancOdo;
   private double avgE;
   private double avgN;
   private double avgW;
   private double cE;
   private double cN;
   private double[] cand;
   private int candN;
   private double[] candOdo;
   private long candStartMs;
   private double fE;
   private double fN;
   private boolean fOK;
   private double[] fOdo;
   private double holdE;
   private double holdN;
   private double holdW;
   private double kx;
   private float lastMovingBearing;
   private volatile double lastRejectM;
   private long lastUbloxMs;
   private double lat0 = Double.NaN;
   private double lon0 = Double.NaN;
   private double phi;
   private volatile long rejected;
   private volatile String status;
   private long stoppedSinceMs;

   public GpsFilter() {
      this.kx = 1.0;
      this.ancOdo = new double[4];
      this.ancAcc = 10.0;
      this.phi = Double.NaN;
      this.candOdo = new double[4];
      this.lastUbloxMs = -1000000L;
      this.stoppedSinceMs = -1L;
      this.lastMovingBearing = Float.NaN;
      this.status = "waiting for the GPS";
      this.fOdo = new double[4];
   }

   private final void advance(double[] var1) {
      if (!Double.isNaN(this.phi)) {
         double[] var2 = this.moved(var1, this.fOdo);
         this.fE = this.fE + var2[0];
         this.fN = this.fN + var2[1];
      }

      var1 = Arrays.copyOf(var1, var1.length);
      Intrinsics.checkNotNullExpressionValue(var1, "copyOf(...)");
      this.fOdo = var1;
      this.fE = this.fE + this.cE * 0.1;
      this.fN = this.fN + this.cN * 0.1;
      this.cE *= 0.9;
      this.cN *= 0.9;
   }

   private final float drBearing(double[] var1) {
      float var2;
      if (Double.isNaN(this.phi)) {
         var2 = this.lastMovingBearing;
      } else {
         var2 = (float)(((90.0 - Math.toDegrees(this.phi + var1[2])) % 360.0 + 360.0) % 360.0);
      }

      return var2;
   }

   private final boolean holding(long var1) {
      boolean var3;
      if (this.stoppedSinceMs >= 0L && var1 - this.stoppedSinceMs >= 1000L) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   private final double[] moved(double[] var1, double[] var2) {
      double var7 = var1[0] - var2[0];
      double var3 = var1[1] - var2[1];
      if (Double.isNaN(this.phi)) {
         return new double[]{0.0, 0.0};
      } else {
         double var9 = Math.cos(this.phi);
         double var5 = Math.sin(this.phi);
         return new double[]{var9 * var7 - var5 * var3, var5 * var7 + var9 * var3};
      }
   }

   // $VF: synthetic method
   public static GpsFilter.Out onFix$default(
      GpsFilter var0,
      long var1,
      double var3,
      double var5,
      float var7,
      float var8,
      float var9,
      float var10,
      String var11,
      double[] var12,
      float var13,
      double var14,
      int var16,
      Object var17
   ) {
      if ((var16 & 1024) != 0) {
         var14 = Double.NaN;
      }

      return var0.onFix(var1, var3, var5, var7, var8, var9, var10, var11, var12, var13, var14);
   }

   private final double[] predictEn(double[] var1) {
      double[] var2 = this.anc;
      if (var2 == null) {
         return null;
      } else {
         var1 = this.moved(var1, this.ancOdo);
         return new double[]{var2[0] + var1[0], var2[1] + var1[1]};
      }
   }

   private final double[] toEn(double var1, double var3) {
      if (Double.isNaN(this.lat0)) {
         this.lat0 = var1;
         this.lon0 = var3;
         this.kx = Math.cos(Math.toRadians(var1)) * 111320.0;
      }

      return new double[]{(var3 - this.lon0) * this.kx, (var1 - this.lat0) * 110540.0};
   }

   private final double[] toLl(double var1, double var3) {
      return new double[]{this.lat0 + var3 / 110540.0, this.lon0 + var1 / this.kx};
   }

   public final double[] fused(long var1, double[] var3) {
      Intrinsics.checkNotNullParameter(var3, "odo");
      if (this.fOK && !Double.isNaN(this.phi) && this.anc != null && var1 - this.ancMs <= 60000L) {
         this.advance(var3);
         if (this.holding(var1) && this.holdW > 0.0) {
            this.fE = this.holdE;
            this.fN = this.holdN;
            this.cE = 0.0;
            this.cN = 0.0;
         }

         double[] var5 = this.toLl(this.fE, this.fN);
         float var4;
         if (this.holding(var1)) {
            var4 = this.lastMovingBearing;
         } else {
            var4 = this.drBearing(var3);
         }

         return new double[]{var5[0], var5[1], var4, (var1 - this.ancMs) / 1000.0};
      } else {
         return null;
      }
   }

   public final double getLastRejectM() {
      return this.lastRejectM;
   }

   public final long getRejected() {
      return this.rejected;
   }

   public final String getStatus() {
      return this.status;
   }

   public final boolean isHolding(long var1) {
      return this.holding(var1);
   }

   public final GpsFilter.Out onFix(
      long var1, double var3, double var5, float var7, float var8, float var9, float var10, String var11, double[] var12, float var13, double var14
   ) {
      Intrinsics.checkNotNullParameter(var11, "src");
      Intrinsics.checkNotNullParameter(var12, "odo");
      boolean var28;
      if (!Double.isInfinite(var3) && !Double.isNaN(var3)) {
         var28 = 1;
      } else {
         var28 = 0;
      }

      if (var28) {
         if (!Double.isInfinite(var5) && !Double.isNaN(var5)) {
            var28 = (boolean)1;
         } else {
            var28 = (boolean)0;
         }

         if (var28 && (!(Math.abs(var3) < 1.0E-6) || !(Math.abs(var5) < 1.0E-6))) {
            if (!Float.isInfinite(var7) && !Float.isNaN(var7)) {
               var28 = (boolean)1;
            } else {
               var28 = (boolean)0;
            }

            double var16;
            if (var28 && var7 > 0.0F) {
               var16 = var7;
            } else {
               var16 = 10.0;
            }

            if (var16 > 50.0) {
               var11 = String.format("ignoring a fix with ±%.0f m", Arrays.copyOf(new Object[]{var16}, 1));
               Intrinsics.checkNotNullExpressionValue(var11, "format(...)");
               this.status = var11;
               return null;
            }

            if (Intrinsics.areEqual(var11, "ublox")) {
               this.lastUbloxMs = var1;
            } else if (var1 - this.lastUbloxMs < 2000L) {
               return null;
            }

            double[] var32 = this.toEn(var3, var5);
            boolean var29;
            if (this.anc != null && var1 - this.ancMs < 120000L) {
               var29 = true;
            } else {
               var29 = false;
            }

            label246: {
               if (var29) {
                  double[] var41 = this.predictEn(var12);
                  Intrinsics.checkNotNull(var41);
                  double var24 = var12[3];
                  double var20 = this.ancOdo[3];
                  double var22 = Math.max(25.0, 2.5 * var16);
                  double var18;
                  if (Double.isNaN(this.phi)) {
                     var18 = 1.0;
                  } else {
                     var18 = 0.08;
                  }

                  double var26 = Math.hypot(var32[0] - var41[0], var32[1] - var41[1]);
                  if (var26 <= var22 + var18 * (var24 - var20)) {
                     var28 = (boolean)1;
                     break label246;
                  }

                  this.rejected++;
                  this.lastRejectM = var26;
                  var11 = String.format("ignored a jump of %.0f m", Arrays.copyOf(new Object[]{var26}, 1));
                  Intrinsics.checkNotNullExpressionValue(var11, "format(...)");
                  this.status = var11;
               }

               var28 = (boolean)0;
            }

            if (var28) {
               boolean var71 = false;
               var28 = var71;
            } else {
               label236: {
                  double[] var43 = this.cand;
                  if (var43 != null) {
                     GpsFilter var34 = this;
                     double[] var33 = var34.moved(var12, var34.candOdo);
                     double var56 = 2;
                     double var53;
                     if (Double.isNaN(var34.phi)) {
                        var53 = var12[3] - var34.candOdo[3];
                     } else {
                        var53 = (var12[3] - var34.candOdo[3]) * 0.05;
                     }

                     if (Math.hypot(var32[0] - (var43[0] + var33[0]), var32[1] - (var43[1] + var33[1])) <= var56 * var16 + 15.0 + var53) {
                        var28 = (boolean)1;
                     } else {
                        var28 = (boolean)0;
                     }

                     if (var28) {
                        var28 = (boolean)1;
                        break label236;
                     }
                  }

                  var28 = (boolean)0;
               }

               if (!var28) {
                  this.cand = var32;
                  double[] var44 = Arrays.copyOf(var12, var12.length);
                  Intrinsics.checkNotNullExpressionValue(var44, "copyOf(...)");
                  this.candOdo = var44;
                  this.candStartMs = var1;
                  this.candN = 1;
               } else {
                  this.cand = var32;
                  double[] var45 = Arrays.copyOf(var12, var12.length);
                  Intrinsics.checkNotNullExpressionValue(var45, "copyOf(...)");
                  this.candOdo = var45;
                  this.candN++;
               }

               if (var29) {
                  var28 = (short)5000;
               } else {
                  var28 = (short)4000;
               }

               if (!(var16 <= 30.0) || this.candN < 8 || var1 - this.candStartMs < var28) {
                  if (!var29) {
                     var1 = (var1 - this.candStartMs) / 1000;
                     this.status = "checking the GPS (" + var1 + " s steady)";
                  }

                  return null;
               }

               boolean var30 = true;
               if (var29) {
                  var28 /= 1000;
                  var11 = "GPS moved over to a new position it kept for " + var28 + " s";
               } else {
                  var11 = "GPS trusted";
               }

               this.status = var11;
               if (this.holding(var1)) {
                  this.holdW = 0.0;
               }

               var28 = (boolean)1;
            }

            this.cand = null;
            this.anc = var32;
            double[] var47 = Arrays.copyOf(var12, var12.length);
            Intrinsics.checkNotNullExpressionValue(var47, "copyOf(...)");
            this.ancOdo = var47;
            this.ancMs = var1;
            this.ancAcc = var16;
            if (StringsKt.startsWith$default(this.status, "checking", false, 2, null)
               || StringsKt.startsWith$default(this.status, "waiting", false, 2, null)
               || StringsKt.startsWith$default(this.status, "ignor", false, 2, null)) {
               this.status = "GPS trusted";
            }

            if (!Double.isInfinite(var14) && !Double.isNaN(var14)) {
               var29 = true;
            } else {
               var29 = false;
            }

            if (!var29) {
               var14 = 0.0;
            }

            boolean var31 = Double.isNaN(this.phi);
            if (!Float.isInfinite(var10) && !Float.isNaN(var10)) {
               var29 = true;
            } else {
               var29 = false;
            }

            double var54;
            if (var29 && var10 > 0.0F) {
               var54 = var10;
            } else {
               var54 = 10.0;
            }

            double var57 = 25.0;
            var10 = 1.5F;
            if (var31) {
               var7 = 1.5F;
            } else {
               var7 = 3.0F;
            }

            label195: {
               if (var8 > var7) {
                  if (var31) {
                     var7 = var10;
                  } else {
                     var7 = 2.0F;
                  }

                  if (var13 > var7) {
                     if (!Float.isInfinite(var9) && !Float.isNaN(var9)) {
                        var29 = true;
                     } else {
                        var29 = false;
                     }

                     if (var29) {
                        if (!var31) {
                           var57 = 20.0;
                        }

                        if (var54 <= var57 && Math.abs(var14) < 0.4) {
                           var29 = true;
                           break label195;
                        }
                     }
                  }
               }

               var29 = false;
            }

            var57 = 0.2;
            if (var29) {
               var7 = (float)(var9 + Math.toDegrees(Math.atan(2.2 * var14 / Math.max(1.5, (double)var13))));
               var14 = Math.toRadians(90.0 - var7) - var12[2];
               var54 = RangesKt.coerceIn(8.0 / var54 * (8.0 / var54), 0.1, 1.0);
               if (!Double.isNaN(this.phi)) {
                  var14 = this.phi + Math.atan2(Math.sin(var14 - this.phi), Math.cos(var14 - this.phi)) * (var54 * 0.2);
               }

               this.phi = var14;
               this.lastMovingBearing = (var7 % 360.0F + 360.0F) % 360.0F;
            } else {
               var7 = var9;
            }

            if (this.fOK && !var28 && !Double.isNaN(this.phi)) {
               this.advance(var12);
               if (var16 <= 5.0) {
                  var14 = 0.35;
               } else if (var16 <= 15.0) {
                  var14 = var57;
               } else {
                  var14 = 0.1;
               }

               this.cE = (var32[0] - this.fE - this.cE) * var14 + this.cE;
               this.cN = (var32[1] - this.fN - this.cN) * var14 + this.cN;
            } else {
               this.fE = var32[0];
               this.fN = var32[1];
               this.cE = 0.0;
               this.cN = 0.0;
               double[] var48 = Arrays.copyOf(var12, var12.length);
               Intrinsics.checkNotNullExpressionValue(var48, "copyOf(...)");
               this.fOdo = var48;
               this.fOK = true;
            }

            if (this.holding(var1)) {
               var3 = 1.0 / Math.max(1.0, var16 * var16);
               this.avgE = (this.avgE * this.avgW + var32[0] * var3) / (this.avgW + var3);
               this.avgN = (this.avgN * this.avgW + var32[1] * var3) / (this.avgW + var3);
               this.avgW += var3;
               if (this.holdW <= 0.0) {
                  this.holdE = this.avgE;
                  this.holdN = this.avgN;
                  this.holdW = 1.0;
               } else if (var1 - this.stoppedSinceMs > 5000L && Math.hypot(this.avgE - this.holdE, this.avgN - this.holdN) > 10.0) {
                  this.holdE = this.holdE + (this.avgE - this.holdE) * 0.02;
                  this.holdN = this.holdN + (this.avgN - this.holdN) * 0.02;
               }

               this.fE = this.holdE;
               this.fN = this.holdN;
               this.cE = 0.0;
               this.cN = 0.0;
               double[] var49 = this.toLl(this.holdE, this.holdN);
               return new GpsFilter.Out(var49[0], var49[1], this.lastMovingBearing, 0.0F, (float)var16, "held");
            }

            if (!var29) {
               var7 = this.drBearing(var12);
            }

            return new GpsFilter.Out(var3, var5, var7, var8, (float)var16, "live");
         }
      }

      return null;
   }

   public final void onSpeed(long var1, float var3, double[] var4) {
      Intrinsics.checkNotNullParameter(var4, "odo");
      if (var3 < 0.1F) {
         if (this.stoppedSinceMs < 0L) {
            this.stoppedSinceMs = var1;
            this.avgW = 0.0;
            var4 = this.predictEn(var4);
            if (var4 != null) {
               this.holdE = var4[0];
               this.holdN = var4[1];
               this.holdW = 1.0;
            } else {
               this.holdW = 0.0;
            }
         }
      } else if (var3 > 0.3F && this.stoppedSinceMs >= 0L) {
         this.stoppedSinceMs = -1L;
         if (this.holdW > 0.0 && this.anc != null) {
            this.anc = new double[]{this.holdE, this.holdN};
            var4 = Arrays.copyOf(var4, var4.length);
            Intrinsics.checkNotNullExpressionValue(var4, "copyOf(...)");
            this.ancOdo = var4;
         }
      }
   }

   public final double[] predict(long var1, double[] var3) {
      Intrinsics.checkNotNullParameter(var3, "odo");
      if (this.anc == null || var1 - this.ancMs > 120000L) {
         return null;
      } else if (this.holding(var1) && this.holdW > 0.0) {
         var3 = this.toLl(this.holdE, this.holdN);
         return new double[]{var3[0], var3[1], this.lastMovingBearing, 0.0};
      } else if (Double.isNaN(this.phi) && var3[3] - this.ancOdo[3] > 5.0) {
         return null;
      } else {
         double[] var4 = this.predictEn(var3);
         if (var4 == null) {
            return null;
         } else {
            var4 = this.toLl(var4[0], var4[1]);
            return new double[]{var4[0], var4[1], this.drBearing(var3), var3[3] - this.ancOdo[3]};
         }
      }
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }
   }

   public static final class Out {
      private final float acc;
      private final float bearing;
      private final String kind;
      private final double lat;
      private final double lon;
      private final float speed;

      public Out(double var1, double var3, float var5, float var6, float var7, String var8) {
         Intrinsics.checkNotNullParameter(var8, "kind");
         super();
         this.lat = var1;
         this.lon = var3;
         this.bearing = var5;
         this.speed = var6;
         this.acc = var7;
         this.kind = var8;
      }

      public final float getAcc() {
         return this.acc;
      }

      public final float getBearing() {
         return this.bearing;
      }

      public final String getKind() {
         return this.kind;
      }

      public final double getLat() {
         return this.lat;
      }

      public final double getLon() {
         return this.lon;
      }

      public final float getSpeed() {
         return this.speed;
      }
   }
}
