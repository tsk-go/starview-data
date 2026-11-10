package com.starpilot.starview;

import android.graphics.Color;
import kotlin.math.MathKt;

public final class GxCamerasKt {
   private static final int GX_CAM_BLUE = Color.rgb(13, 110, 253);
   private static final String GX_CAM_LEFT = "LEFT SIDE OF VEHICLE - LEFT HERE";
   private static final int GX_CAM_ORANGE = Color.rgb(253, 126, 20);
   private static final int GX_CAM_RED = Color.rgb(255, 99, 99);
   private static final String GX_CAM_RIGHT = "RIGHT SIDE OF VEHICLE - RIGHT HERE";

   // $VF: synthetic method
   public static final int access$getGX_CAM_BLUE$p() {
      return GX_CAM_BLUE;
   }

   // $VF: synthetic method
   public static final int access$getGX_CAM_ORANGE$p() {
      return GX_CAM_ORANGE;
   }

   // $VF: synthetic method
   public static final int access$getGX_CAM_RED$p() {
      return GX_CAM_RED;
   }

   // $VF: synthetic method
   public static final int access$gxCamRound(double var0) {
      return gxCamRound(var0);
   }

   // $VF: synthetic method
   public static final int access$gxCamRound(float var0) {
      return gxCamRound(var0);
   }

   private static final int gxCamRound(double var0) {
      boolean var4 = Double.isInfinite(var0);
      int var3 = 0;
      boolean var2;
      if (!var4 && !Double.isNaN(var0)) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (var2) {
         var3 = MathKt.roundToInt(var0);
      }

      return var3;
   }

   private static final int gxCamRound(float var0) {
      boolean var3 = Float.isInfinite(var0);
      int var2 = 0;
      boolean var1;
      if (!var3 && !Float.isNaN(var0)) {
         var1 = true;
      } else {
         var1 = false;
      }

      if (var1) {
         var2 = MathKt.roundToInt(var0);
      }

      return var2;
   }
}
