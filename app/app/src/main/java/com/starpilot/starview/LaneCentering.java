package com.starpilot.starview;

import kotlin.jvm.internal.Intrinsics;

public final class LaneCentering {
   private static final float EPS = 1.0E-6F;
   public static final LaneCentering INSTANCE = new LaneCentering();
   private static final long MAX_AGE_NS = 400000000L;
   private static final float MAX_CORRECTION = 0.01F;

   private LaneCentering() {
   }

   // $VF: synthetic method
   public static int direction$default(LaneCentering var0, Telemetry var1, long var2, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = System.nanoTime();
      }

      return var0.direction(var1, var2);
   }

   public final int direction(int var1, boolean var2, boolean var3, float var4, float var5) {
      byte var6 = 0;
      if (var1 == 1 && var2 && var3) {
         boolean var7;
         if (!Float.isInfinite(var4) && !Float.isNaN(var4)) {
            var7 = 1;
         } else {
            var7 = 0;
         }

         if (var7) {
            if (!Float.isInfinite(var5) && !Float.isNaN(var5)) {
               var7 = (boolean)1;
            } else {
               var7 = (boolean)0;
            }

            if (var7) {
               if (!(Math.abs(var4) > 0.01F) && !(Math.abs(var5) > Math.abs(var4) + 1.0E-8F) && !(var4 * var5 < 0.0F)) {
                  if (var5 > 1.0E-6F) {
                     var7 = 1;
                  } else {
                     var7 = var6;
                     if (var5 < -1.0E-6F) {
                        var7 = -1;
                     }
                  }

                  return var7;
               }

               return 0;
            }
         }

         return 0;
      } else {
         return 0;
      }
   }

   public final int direction(Telemetry var1, long var2) {
      Intrinsics.checkNotNullParameter(var1, "t");
      org.capnproto.SlcPeek.LaneCentering var4 = var1.getLc();
      if (var4 == null) {
         return 0;
      } else {
         return var2 - var1.getLcAtNs() > 400000000L ? 0 : this.direction(var4.version, var4.lateralActive, var1.getLatActive(), var4.requested, var4.applied);
      }
   }
}
