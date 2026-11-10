package com.starpilot.starview;

import android.view.View;

public final class ExtraViewsKt {
   // $VF: synthetic method
   public static final float access$dpf(View var0, float var1) {
      return dpf(var0, var1);
   }

   private static final float dpf(View var0, float var1) {
      return var0.getResources().getDisplayMetrics().density * var1;
   }
}
