package com.starpilot.starview;

import android.view.View;

public final class HudViewsKt {
   // $VF: synthetic method
   public static final float access$dp(View var0, float var1) {
      return dp(var0, var1);
   }

   private static final float dp(View var0, float var1) {
      return var0.getResources().getDisplayMetrics().density * var1;
   }
}
