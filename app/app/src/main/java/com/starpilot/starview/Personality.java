package com.starpilot.starview;

import java.util.Locale;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;

public final class Personality {
   public static final Personality INSTANCE = new Personality();
   private static final String[] LABELS = new String[]{"Aggressive", "Standard", "Relaxed"};
   private static final String[] NAMES = new String[]{"aggressive", "standard", "relaxed"};

   private Personality() {
   }

   public final String[] getLABELS() {
      return LABELS;
   }

   public final String[] getNAMES() {
      return NAMES;
   }

   public final int index(String var1) {
      Intrinsics.checkNotNullParameter(var1, "name");
      String[] var4 = NAMES;
      var1 = var1.toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var1, "toLowerCase(...)");
      int var3 = ArraysKt.indexOf(var4, var1);
      int var2 = var3;
      if (var3 < 0) {
         var2 = 1;
      }

      return var2;
   }

   public final int next(String var1) {
      Intrinsics.checkNotNullParameter(var1, "name");
      return (this.index(var1) + 2) % 3;
   }
}
