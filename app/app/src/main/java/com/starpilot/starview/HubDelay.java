package com.starpilot.starview;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.ranges.RangesKt;

public final class HubDelay {
   public static final HubDelay INSTANCE = new HubDelay();
   public static final int MAX = 300;
   public static final int MIN = 1;
   private static final List<Integer> PRESETS = CollectionsKt.listOf(new Integer[]{1, 5, 15, 30, 60, 120, 300});

   private HubDelay() {
   }

   private static final int step$grid(int var0, boolean var1) {
      byte var2 = 15;
      byte var3;
      if (var1) {
         if (var0 < 10) {
            var3 = 1;
         } else if (var0 < 60) {
            var3 = 5;
         } else if (var0 < 120) {
            var3 = var2;
         } else {
            var3 = 30;
         }
      } else if (var0 <= 10) {
         var3 = 1;
      } else if (var0 <= 60) {
         var3 = 5;
      } else if (var0 <= 120) {
         var3 = var2;
      } else {
         var3 = 30;
      }

      return var3;
   }

   public final int clamp(int var1) {
      return RangesKt.coerceIn(var1, 1, 300);
   }

   public final List<Integer> getPRESETS() {
      return PRESETS;
   }

   public final int step(int var1, boolean var2) {
      int var3 = step$grid(var1, var2);
      if (var2) {
         var1 = var1 / var3 + 1;
      } else {
         var1 = (var1 + var3 - 1) / var3 - 1;
      }

      return this.clamp(var1 * var3);
   }

   public final String text(int var1) {
      String var3;
      if (var1 < 60) {
         var3 = var1 + " min";
      } else if (var1 % 60 == 0) {
         var1 /= 60;
         var3 = var1 + " h";
      } else {
         int var2 = var1 / 60;
         var3 = var2 + " h " + var1 % 60 + " min";
      }

      return var3;
   }
}
