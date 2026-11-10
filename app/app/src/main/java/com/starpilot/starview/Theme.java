package com.starpilot.starview;


public final class Theme {
   public static final Theme INSTANCE = new Theme();

   private Theme() {
   }

   // $VF: synthetic method
   public static boolean isNight$default(Theme var0, double var1, double var3, long var5, int var7, Object var8) {
      if ((var7 & 4) != 0) {
         var5 = System.currentTimeMillis();
      }

      return var0.isNight(var1, var3, var5);
   }

   public final boolean isNight(double var1, double var3, long var5) {
      boolean var7;
      if (this.sunElevationDeg(var1, var3, var5) < -6.0) {
         var7 = true;
      } else {
         var7 = false;
      }

      return var7;
   }

   public final double sunElevationDeg(double var1, double var3, long var5) {
      double var7 = var5 / 8.64E7 + 2440587.5 - 2451545.0;
      double var9 = Math.toRadians((0.98560028 * var7 + 357.529) % 360.0);
      double var11 = Math.toRadians((Math.sin(var9) * 1.915 + (0.98564736 * var7 + 280.459) % 360.0 + Math.sin(2 * var9) * 0.02) % 360.0);
      double var13 = Math.toRadians(23.439 - 3.6E-7 * var7);
      var9 = Math.toDegrees(Math.atan2(Math.cos(var13) * Math.sin(var11), Math.cos(var11)));
      var11 = Math.asin(Math.sin(var13) * Math.sin(var11));
      var3 = Math.toRadians((((15.0 * ((24.06570982441908 * var7 + 18.697374558) % 24.0) + var3) % 360.0 - var9) % 360.0 + 540.0) % 360.0 - 180.0);
      var1 = Math.toRadians(var1);
      return Math.toDegrees(Math.asin(Math.sin(var1) * Math.sin(var11) + Math.cos(var1) * Math.cos(var11) * Math.cos(var3)));
   }
}
