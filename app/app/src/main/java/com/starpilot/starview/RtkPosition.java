package com.starpilot.starview;

import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

public final class RtkPosition {
   private static final double AHEAD_MAX_S = 2.0;
   public static final RtkPosition INSTANCE = new RtkPosition();
   public static final double MAX_ACC_M = 10.0;
   public static final long MAX_AGE_MS = 2500L;
   private static volatile double accM = Double.NaN;
   private static volatile long atMs;
   private static volatile long fastMs;
   private static volatile double heading = Double.NaN;
   private static volatile String kind = "none";
   private static volatile double lat = Double.NaN;
   private static volatile double lon = Double.NaN;

   private RtkPosition() {
   }

   public final boolean fast(long var1) {
      boolean var3;
      if (fastMs > 0L && var1 - fastMs < 600L) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public final void feed(RtkStatus var1, long var2) {
      if (var1 != null && var1.getHasFix()) {
         double var4 = var1.getHAccM();
         boolean var10 = Double.isInfinite(var4);
         boolean var9 = true;
         boolean var8;
         if (!var10 && !Double.isNaN(var4)) {
            var8 = true;
         } else {
            var8 = false;
         }

         if (var8 && !(var1.getHAccM() > 10.0)) {
            if (this.fast(var2)) {
               return;
            }

            lat = var1.getLat();
            lon = var1.getLon();
            atMs = var2;
            var4 = var1.getSpeedMs();
            if (!Double.isInfinite(var4) && !Double.isNaN(var4)) {
               var8 = true;
            } else {
               var8 = false;
            }

            if (var8 && var1.getSpeedMs() > 1.5) {
               var4 = var1.getHeadingDeg();
               if (!Double.isInfinite(var4) && !Double.isNaN(var4)) {
                  var8 = var9;
               } else {
                  var8 = false;
               }

               if (var8) {
                  var4 = var1.getHeadingDeg();
                  double var6 = 360;
                  heading = (var4 % var6 + var6) % var6;
               }
            }

            return;
         }
      }
   }

   public final void feedFast(double var1, double var3, double var5, double var7, double var9, String var11, long var12, long var14) {
      Intrinsics.checkNotNullParameter(var11, "kind");
      boolean var18 = Double.isInfinite(var1);
      boolean var17 = true;
      boolean var16;
      if (!var18 && !Double.isNaN(var1)) {
         var16 = true;
      } else {
         var16 = false;
      }

      if (var16) {
         if (!Double.isInfinite(var3) && !Double.isNaN(var3)) {
            var16 = true;
         } else {
            var16 = false;
         }

         if (var16) {
            if (!Double.isInfinite(var5) && !Double.isNaN(var5)) {
               var16 = true;
            } else {
               var16 = false;
            }

            if (var16 && !(var5 > 10.0)) {
               var12 = RangesKt.coerceIn(var12, 0L, 1000L);
               lat = var1;
               lon = var3;
               atMs = var14 - var12;
               fastMs = var14;
               accM = var5;
               kind = var11;
               if (!Double.isInfinite(var9) && !Double.isNaN(var9)) {
                  var16 = true;
               } else {
                  var16 = false;
               }

               if (var16 && var9 > 1.5) {
                  if (!Double.isInfinite(var7) && !Double.isNaN(var7)) {
                     var16 = var17;
                  } else {
                     var16 = false;
                  }

                  if (var16) {
                     var1 = 360;
                     heading = (var7 % var1 + var1) % var1;
                  }
               }

               return;
            }
         }
      }
   }

   public final double getAccM() {
      return accM;
   }

   public final double getHeading() {
      return heading;
   }

   public final String getKind() {
      return kind;
   }

   public final double[] now(long var1, double var3) {
      long var10 = atMs;
      if (var10 != 0L && var1 - var10 <= 2500L) {
         double var5 = lat;
         boolean var9;
         if (!Double.isInfinite(var5) && !Double.isNaN(var5)) {
            var9 = true;
         } else {
            var9 = false;
         }

         if (var9) {
            double var7 = RangesKt.coerceIn((var1 - var10) / 1000.0, 0.0, 2.0);
            var5 = heading;
            if (!Double.isInfinite(var5) && !Double.isNaN(var5)) {
               var9 = true;
            } else {
               var9 = false;
            }

            if (var9) {
               if (!Double.isInfinite(var3) && !Double.isNaN(var3)) {
                  var9 = true;
               } else {
                  var9 = false;
               }

               if (var9 && !(var3 <= 0.3)) {
                  var3 *= var7;
                  var7 = Math.toRadians(var5);
                  var5 = Math.cos(var7) * var3 / 111195.0;
                  var3 = Math.sin(var7) * var3 / (Math.cos(Math.toRadians(lat)) * 111195.0);
                  return new double[]{lat + var5, lon + var3};
               }
            }

            return new double[]{lat, lon};
         }
      }

      return null;
   }

   public final void reset() {
      lat = Double.NaN;
      lon = Double.NaN;
      atMs = 0L;
      heading = Double.NaN;
      fastMs = 0L;
   }
}
