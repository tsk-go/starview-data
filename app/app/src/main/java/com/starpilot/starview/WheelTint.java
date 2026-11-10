package com.starpilot.starview;

import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;

public final class WheelTint {
   private float amount;
   private long lastNs;
   private float target;

   private static final int update$ch(int[] var0, float var1, int var2) {
      return RangesKt.coerceIn(MathKt.roundToInt(255 + (var0[var2] - 255) * var1), 0, 255);
   }

   public final int update(float var1, boolean var2, long var3) {
      label51: {
         if (this.lastNs != 0L && var2) {
            boolean var8;
            if (!Float.isInfinite(var1) && !Float.isNaN(var1)) {
               var8 = true;
            } else {
               var8 = false;
            }

            if (var8 && var3 >= this.lastNs) {
               break label51;
            }
         }

         this.target = 0.0F;
         this.amount = 0.0F;
         this.lastNs = var3;
      }

      float var7 = 0.0F;
      float var5 = var7;
      if (var2) {
         boolean var12;
         if (!Float.isInfinite(var1) && !Float.isNaN(var1)) {
            var12 = true;
         } else {
            var12 = false;
         }

         var5 = var7;
         if (var12) {
            float var6;
            if (this.target * var1 > 0.0F) {
               var6 = 0.15F;
            } else {
               var6 = 0.25F;
            }

            var5 = var7;
            if (Math.abs(var1) > var6) {
               var5 = RangesKt.coerceIn(var1 / 2.0F, -1.0F, 1.0F);
            }
         }
      }

      var1 = (float)(var3 - this.lastNs) / 1.0E9F;
      this.lastNs = var3;
      this.target = var5;
      this.amount = this.amount + (var5 - this.amount) * (1.0F - (float)Math.exp(-var1 / 0.08F));
      int[] var9;
      if (this.amount < 0.0F) {
         var9 = new int[]{255, 0, 0};
      } else {
         var9 = new int[]{22, 127, 64};
      }

      var1 = Math.abs(this.amount);
      int var13 = update$ch(var9, var1, 0);
      return update$ch(var9, var1, 1) << 8 | var13 << 16 | 0xFF000000 | update$ch(var9, var1, 2);
   }
}
