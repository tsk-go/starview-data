package com.starpilot.starview;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;

public final class JournalPagesKt {
   private static final SimpleDateFormat dayFmt = new SimpleDateFormat("EEE MMM d, h:mm a", Locale.US);
   private static final SimpleDateFormat timeFmt = new SimpleDateFormat("h:mm a", Locale.US);

   // $VF: synthetic method
   public static final String access$dur(double var0) {
      return dur(var0);
   }

   // $VF: synthetic method
   public static final SimpleDateFormat access$getDayFmt$p() {
      return dayFmt;
   }

   // $VF: synthetic method
   public static final SimpleDateFormat access$getTimeFmt$p() {
      return timeFmt;
   }

   // $VF: synthetic method
   public static final String access$miles(double var0) {
      return miles(var0);
   }

   private static final String dur(double var0) {
      String var6;
      if (var0 < 3600.0) {
         long var4 = Math.round(var0 / 60);
         var6 = var4 + " min";
      } else {
         StringCompanionObject var7 = StringCompanionObject.INSTANCE;
         Locale var8 = Locale.US;
         double var2 = 3600;
         var6 = String.format(var8, "%dh %02dm", Arrays.copyOf(new Object[]{(int)(var0 / var2), (int)Math.round(var0 % var2 / 60)}, 2));
         Intrinsics.checkNotNullExpressionValue(var6, "format(...)");
      }

      return var6;
   }

   private static final String miles(double var0) {
      String var6;
      if (var0 < 1609.0) {
         long var4 = Math.round(var0 / 0.3048 / 10);
         long var2 = 10;
         var6 = var4 * var2 + " ft";
      } else {
         StringCompanionObject var7 = StringCompanionObject.INSTANCE;
         var6 = String.format(Locale.US, "%.1f mi", Arrays.copyOf(new Object[]{var0 / 1609.344}, 1));
         Intrinsics.checkNotNullExpressionValue(var6, "format(...)");
      }

      return var6;
   }
}
