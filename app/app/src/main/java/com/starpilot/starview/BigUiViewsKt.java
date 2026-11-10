package com.starpilot.starview;

import android.view.View;

public final class BigUiViewsKt {
   // $VF: synthetic method
   public static final float access$dpf(View var0, float var1) {
      return dpf(var0, var1);
   }

   private static final float dpf(View var0, float var1) {
      return var0.getResources().getDisplayMetrics().density * var1;
   }

   public static final float ease(float var0, float var1, float var2, float var3) {
      return (var1 - var0) * (1.0F - (float)Math.exp(-var2 / var3)) + var0;
   }
}
