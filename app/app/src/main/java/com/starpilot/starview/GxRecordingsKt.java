package com.starpilot.starview;

import android.graphics.Color;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.Regex;
import kotlin.text.StringsKt;

public final class GxRecordingsKt {
   private static final int GxRecordingsDanger = Color.rgb(255, 120, 120);
   private static final int GxRecordingsMaxRendered = 250;
   private static final List<String> GxRecordingsMonths = CollectionsKt.listOf(
      new String[]{"january", "february", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december"}
   );

   // $VF: synthetic method
   public static final int access$getGxRecordingsDanger$p() {
      return GxRecordingsDanger;
   }

   // $VF: synthetic method
   public static final List access$getGxRecordingsMonths$p() {
      return GxRecordingsMonths;
   }

   // $VF: synthetic method
   public static final String access$gxRecordingsBytes(double var0) {
      return gxRecordingsBytes(var0);
   }

   // $VF: synthetic method
   public static final String access$gxRecordingsDuration(int var0) {
      return gxRecordingsDuration(var0);
   }

   // $VF: synthetic method
   public static final String access$gxRecordingsNorm(String var0) {
      return gxRecordingsNorm(var0);
   }

   // $VF: synthetic method
   public static final String access$gxRecordingsSafeName(String var0) {
      return gxRecordingsSafeName(var0);
   }

   private static final String gxRecordingsBytes(double var0) {
      if (var0 <= 0.0) {
         return "0 MB";
      } else {
         var0 /= 1000000.0;
         String var4;
         if (var0 >= 1000.0) {
            StringCompanionObject var2 = StringCompanionObject.INSTANCE;
            var4 = String.format(Locale.US, "%.2f GB", Arrays.copyOf(new Object[]{var0 / 1000}, 1));
         } else {
            StringCompanionObject var5 = StringCompanionObject.INSTANCE;
            var4 = String.format(Locale.US, "%.1f MB", Arrays.copyOf(new Object[]{var0}, 1));
         }

         Intrinsics.checkNotNullExpressionValue(var4, "format(...)");
         return var4;
      }
   }

   private static final String gxRecordingsDuration(int var0) {
      int var1 = var0 / 3600;
      var0 = var0 % 3600 / 60;
      StringBuilder var2 = null;
      if (var1 > 0) {
         var2 = new StringBuilder();
         var2 = var2.append(var1).append("h ");
      } else {
         var2 = new StringBuilder();
      }

      return var2.append(var0).append("m").toString();
   }

   private static final String gxRecordingsNorm(String var0) {
      if (var0 == null) {
         var0 = "";
      }

      var0 = Normalizer.normalize(var0, Form.NFKD);
      Intrinsics.checkNotNullExpressionValue(var0, "normalize(...)");
      CharSequence var3 = var0;
      var0 = new Regex("\\p{M}").replace(var3, "");
      Locale var1 = Locale.ROOT;
      Intrinsics.checkNotNullExpressionValue(var1, "ROOT");
      var0 = var0.toLowerCase(var1);
      Intrinsics.checkNotNullExpressionValue(var0, "toLowerCase(...)");
      CharSequence var6 = var0;
      CharSequence var7 = new Regex("(\\d+)(?:st|nd|rd|th)\\b").replace(var6, "$1");
      return StringsKt.trim(new Regex("[^\\p{L}\\p{N}]+").replace(var7, " ")).toString();
   }

   private static final String gxRecordingsSafeName(String var0) {
      CharSequence var2 = var0;
      CharSequence var1 = StringsKt.trim(new Regex("[\\\\/:*?\"<>|]+").replace(var2, "_")).toString();
      Object var3 = var1;
      if (StringsKt.isBlank(var1)) {
         var3 = "route";
      }

      return (String)var3;
   }
}
