package com.starpilot.starview;

import kotlin.ranges.RangesKt;

public final class PathPulse {
   public static final float COAST_ACCEL = 0.25F;
   public static final float DIM = 0.35F;
   public static final float FULL_ACCEL = 1.5F;
   public static final PathPulse INSTANCE = new PathPulse();
   public static final double PERIOD_M = 6.0;
   public static final double SPEED_M_S = 8.0;

   private PathPulse() {
   }

   public final double advance(double var1, float var3, float var4, float var5) {
      return (Math.signum(var3) * 8.0 * var4 * var5 + var1) % 6000.0;
   }

   public final float alpha(float var1, double var2, float var4) {
      if (var4 <= 0.0F) {
         return 1.0F;
      } else {
         var2 = Math.cos((var1 + var2) * (Math.PI * 2) / 6.0) * 0.5 + 0.5;
         var1 = (float)(var2 * var2 * var2);
         var4 = 1.0F - 0.65F * var4;
         return (1.0F - var4) * var1 + var4;
      }
   }

   public final float broken(float var1) {
      return RangesKt.coerceIn((Math.abs(var1) - 0.25F) / 1.25F, 0.0F, 1.0F);
   }
}
