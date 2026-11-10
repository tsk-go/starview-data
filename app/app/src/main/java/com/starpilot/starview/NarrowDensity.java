package com.starpilot.starview;

import android.content.Context;
import android.content.res.Configuration;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

public final class NarrowDensity {
   public static final NarrowDensity INSTANCE = new NarrowDensity();

   private NarrowDensity() {
   }

   // $VF: synthetic method
   public static Context wrap$default(NarrowDensity var0, Context var1, int var2, float var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 1000;
      }

      if ((var4 & 4) != 0) {
         var3 = 0.62F;
      }

      return var0.wrap(var1, var2, var3);
   }

   public final Context wrap(Context var1, int var2, float var3) {
      Intrinsics.checkNotNullParameter(var1, "base");
      Configuration var5 = new Configuration(var1.getResources().getConfiguration());
      int var4 = var5.screenWidthDp;
      if (var4 > 0 && var4 < var2 && var5.densityDpi > 0) {
         var3 = Math.max(var3, (float)var4 / var2);
         var5.densityDpi = RangesKt.coerceAtLeast((int)(var5.densityDpi * var3), 80);
         var5.screenWidthDp = (int)(var4 / var3);
         var5.screenHeightDp = (int)(var5.screenHeightDp / var3);
         var5.smallestScreenWidthDp = (int)(var5.smallestScreenWidthDp / var3);
         var1 = var1.createConfigurationContext(var5);
         Intrinsics.checkNotNullExpressionValue(var1, "createConfigurationContext(...)");
         return var1;
      } else {
         return var1;
      }
   }
}
