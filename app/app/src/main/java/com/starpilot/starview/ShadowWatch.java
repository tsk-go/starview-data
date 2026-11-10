package com.starpilot.starview;

import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;

public final class ShadowWatch {
   public static final ShadowWatch INSTANCE = new ShadowWatch();
   private static float lastCx = Float.NaN;
   private static volatile boolean nearCorner;
   private static float[] prev;
   private static int steps;
   private static volatile boolean wanted;

   private ShadowWatch() {
   }

   public final void feed(float[] var1, int var2, boolean var3, long var4) {
      Intrinsics.checkNotNullParameter(var1, "grid");
      float var8 = (float)ArraysKt.average(var1);
      byte var14 = 0;
      if (!(var8 < 95.0F) && !var3) {
         float var6 = 0.0F;
         int var11 = 0;
         int var9 = 0;

         for (int var15 = var1.length / var2; var9 < var15; var9++) {
            int var10 = 0;

            while (var10 < var2) {
               int var12 = var2 / 4;
               boolean var13;
               if (var10 < var2 * 3 / 4 && var12 <= var10) {
                  var13 = true;
               } else {
                  var13 = false;
               }

               float var7 = var6;
               var12 = var11;
               if (!var13) {
                  var7 = var6;
                  var12 = var11;
                  if (var1[var9 * var2 + var10] < 0.45F * var8) {
                     var7 = var6 + var10;
                     var12 = var11 + 1;
                  }
               }

               var10++;
               var6 = var7;
               var11 = var12;
            }
         }

         float[] var16 = prev;
         prev = var1;
         if (var11 >= 3 && var11 <= 40 && var16 != null) {
            var6 /= var11;
            var2 = var14;
            if (!Float.isNaN(lastCx)) {
               float var19 = Math.abs(var6 - lastCx);
               boolean var20;
               if (0.3F <= var19 && var19 <= 3.0F) {
                  var20 = true;
               } else {
                  var20 = false;
               }

               var2 = var14;
               if (var20) {
                  var2 = steps + 1;
               }
            }

            steps = var2;
            lastCx = var6;
            if (steps >= 4 && nearCorner) {
               Ideas.INSTANCE.getSense().setShadowAtMs(var4);
            }
         } else {
            lastCx = Float.NaN;
            steps = 0;
         }
      } else {
         prev = var1;
         steps = 0;
      }
   }

   public final boolean getNearCorner() {
      return nearCorner;
   }

   public final boolean getWanted() {
      return wanted;
   }

   public final void setNearCorner(boolean var1) {
      nearCorner = var1;
   }

   public final void setWanted(boolean var1) {
      wanted = var1;
   }
}
