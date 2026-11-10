package com.starpilot.starview;

import android.view.View;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Result.Companion;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

public final class GxModelsKt {
   // $VF: synthetic method
   public static final void access$gxModelsEnable(View var0, boolean var1) {
      gxModelsEnable(var0, var1);
   }

   // $VF: synthetic method
   public static final String access$gxModelsFileSizeText(JSONObject var0) {
      return gxModelsFileSizeText(var0);
   }

   // $VF: synthetic method
   public static final String access$gxModelsHardwareLabel(JSONObject var0) {
      return gxModelsHardwareLabel(var0);
   }

   // $VF: synthetic method
   public static final boolean access$gxModelsMatchesHardware(JSONObject var0, String var1) {
      return gxModelsMatchesHardware(var0, var1);
   }

   // $VF: synthetic method
   public static final int access$gxModelsNatCompare(String var0, String var1) {
      return gxModelsNatCompare(var0, var1);
   }

   // $VF: synthetic method
   public static final List access$gxModelsObjects(JSONArray var0) {
      return gxModelsObjects(var0);
   }

   // $VF: synthetic method
   public static final long access$gxModelsReleasedTs(String var0) {
      return gxModelsReleasedTs(var0);
   }

   // $VF: synthetic method
   public static final String access$gxModelsText(JSONObject var0, String var1, String var2) {
      return gxModelsText(var0, var1, var2);
   }

   private static final void gxModelsEnable(View var0, boolean var1) {
      var0.setEnabled(var1);
      var0.setClickable(var1);
      float var2;
      if (var1) {
         var2 = 1.0F;
      } else {
         var2 = 0.4F;
      }

      var0.setAlpha(var2);
   }

   private static final String gxModelsFileSizeText(JSONObject var0) {
      Double var4 = gxModelsSafeBytes(var0, "fileSizeBytes");
      Double var5 = gxModelsSafeBytes(var0, "declaredSizeBytes");
      Double var3 = gxModelsSafeBytes(var0, "downloadedBytes");
      if (var0.optBoolean("partial") || Intrinsics.areEqual(var0.optString("sizeStatus"), "partial")) {
         label39: {
            var11 = "unknown";
            if (var3 != null) {
               String var9 = gxModelsFormatBytes(var3.doubleValue());
               var8 = var9;
               if (var9 != null) {
                  break label39;
               }
            }

            var8 = "unknown";
         }

         String var10 = var11;
         if (var5 != null) {
            double var1 = var5.doubleValue();
            var10 = gxModelsFormatBytes(var1);
            if (var10 == null) {
               var10 = var11;
            }
         }

         return "Partial: " + var8 + " / " + var10;
      } else if (var4 != null && var5 != null && !Intrinsics.areEqual(var4, var5)) {
         String var7 = gxModelsFormatBytes(var4);
         return var7 + " · size mismatch";
      } else if (var4 != null) {
         return gxModelsFormatBytes(var4);
      } else if (var5 != null) {
         String var6 = gxModelsFormatBytes(var5);
         return var6 + " · declared";
      } else {
         return "Unavailable";
      }
   }

   private static final String gxModelsFormatBytes(double var0) {
      String var3;
      if (var0 >= 1.0E9) {
         StringCompanionObject var2 = StringCompanionObject.INSTANCE;
         var3 = String.format(Locale.US, "%.2f GB", Arrays.copyOf(new Object[]{var0 / 1.0E9}, 1));
      } else {
         StringCompanionObject var4 = StringCompanionObject.INSTANCE;
         var3 = String.format(Locale.US, "%.1f MB", Arrays.copyOf(new Object[]{var0 / 1000000.0}, 1));
      }

      Intrinsics.checkNotNullExpressionValue(var3, "format(...)");
      return var3;
   }

   private static final String gxModelsHardwareLabel(JSONObject var0) {
      String var1 = (String)var0.opt("requiresGpu");
      if (Intrinsics.areEqual(var1, true)) {
         var1 = "GPU · External";
      } else if (Intrinsics.areEqual(var1, false)) {
         var1 = "Comma · On-device";
      } else {
         var1 = "Hardware unknown";
      }

      return var1;
   }

   private static final boolean gxModelsMatchesHardware(JSONObject var0, String var1) {
      boolean var3 = Intrinsics.areEqual(var1, "gpu");
      boolean var2 = true;
      if (var3) {
         var2 = Intrinsics.areEqual(var0.opt("requiresGpu"), true);
      } else if (Intrinsics.areEqual(var1, "comma")) {
         var2 = Intrinsics.areEqual(var0.opt("requiresGpu"), false);
      }

      return var2;
   }

   private static final int gxModelsNatCompare(String var0, String var1) {
      int var4 = 0;
      int var5 = 0;

      while (var4 < var0.length() && var5 < var1.length()) {
         char var2 = var0.charAt(var4);
         char var3 = var1.charAt(var5);
         if (Character.isDigit(var2) && Character.isDigit(var3)) {
            int var11 = var4;

            while (var11 < var0.length() && Character.isDigit(var0.charAt(var11))) {
               var11++;
            }

            int var7 = var5;

            while (var7 < var1.length() && Character.isDigit(var1.charAt(var7))) {
               var7++;
            }

            String var8 = var0.substring(var4, var11);
            Intrinsics.checkNotNullExpressionValue(var8, "substring(...)");
            var8 = StringsKt.trimStart(var8, new char[]{'0'});
            String var9 = var1.substring(var5, var7);
            Intrinsics.checkNotNullExpressionValue(var9, "substring(...)");
            var9 = StringsKt.trimStart(var9, new char[]{'0'});
            if (var8.length() != var9.length()) {
               return var8.length() - var9.length();
            }

            var4 = var8.compareTo(var9);
            if (var4 != 0) {
               return var4;
            }

            var4 = var11;
            var5 = var7;
         } else {
            int var6 = Intrinsics.compare(Character.toLowerCase(var2), Character.toLowerCase(var3));
            if (var6 != 0) {
               return var6;
            }

            var4++;
            var5++;
         }
      }

      return var0.length() - var4 - (var1.length() - var5);
   }

   private static final List<JSONObject> gxModelsObjects(JSONArray var0) {
      List var4;
      if (var0 == null) {
         var4 = CollectionsKt.emptyList();
      } else {
         Iterable var2 = RangesKt.until(0, var0.length());
         var var1 = new ArrayList();
         Iterator var3 = var2.iterator();

         while (var3.hasNext()) {
            JSONObject var5 = var0.optJSONObject(((IntIterator)var3).nextInt());
            if (var5 != null) {
               var1.add(var5);
            }
         }

         var4 = var1;
      }

      return var4;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private static final long gxModelsReleasedTs(String var0) {
      var0 = StringsKt.trim(var0).toString();
      boolean var1;
      if (var0.length() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      if (var1) {
         return 0L;
      } else {
         try {
            Companion var55 = Result.Companion;
            return Instant.parse(var0).toEpochMilli();
         } catch (Throwable var48) {
            Companion var5 = Result.Companion;
            KtCompat.resultOf(ResultKt.createFailure(var48));

            try {
               Companion var54 = Result.Companion;
               return OffsetDateTime.parse(var0).toInstant().toEpochMilli();
            } catch (Throwable var47) {
               Companion var4 = Result.Companion;
               KtCompat.resultOf(ResultKt.createFailure(var47));

               try {
                  var4 = Result.Companion;
                  return LocalDateTime.parse(var0).toInstant(ZoneOffset.UTC).toEpochMilli();
               } catch (Throwable var46) {
                  var5 = Result.Companion;
                  KtCompat.resultOf(ResultKt.createFailure(var46));

                  try {
                     var4 = Result.Companion;
                     return LocalDate.parse(StringsKt.take(var0, 10)).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
                  } catch (Throwable var45) {
                     var5 = Result.Companion;
                     KtCompat.resultOf(ResultKt.createFailure(var45));

                     for (String var58 : CollectionsKt.listOf(
                        new String[]{"yyyy/MM/dd", "MM/dd/yyyy", "M/d/yyyy", "MMMM d, yyyy", "MMM d, yyyy", "d MMMM yyyy", "d MMM yyyy"}
                     )) {
                        try {
                           Companion var6 = Result.Companion;
                           return LocalDate.parse(var0, DateTimeFormatter.ofPattern(var58, Locale.US)).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
                        } catch (Throwable var44) {
                           var5 = Result.Companion;
                           KtCompat.resultOf(ResultKt.createFailure(var44));
                           continue;
                        }
                     }

                     for (String var61 : CollectionsKt.listOf(new String[]{"MMMM yyyy", "MMM yyyy", "yyyy-MM"})) {
                        try {
                           var5 = Result.Companion;
                           return YearMonth.parse(var0, DateTimeFormatter.ofPattern(var61, Locale.US))
                              .atDay(1)
                              .atStartOfDay(ZoneOffset.UTC)
                              .toInstant()
                              .toEpochMilli();
                        } catch (Throwable var43) {
                           Companion var62 = Result.Companion;
                           KtCompat.resultOf(ResultKt.createFailure(var43));
                           continue;
                        }
                     }

                     return 0L;
                  }
               }
            }
         }
      }
   }

   private static final Double gxModelsSafeBytes(JSONObject var0, String var1) {
      Number var6 = (Number)var0.opt(var1);
      boolean var5 = var6 instanceof Number;
      Object var9 = null;
      if (var5) {
         var6 = var6;
      } else {
         var6 = null;
      }

      if (var6 == null) {
         return null;
      } else {
         double var2 = var6.doubleValue();
         Double var8 = (Double)var9;
         if (var2 > 0.0) {
            boolean var4;
            if (var2 == Math.floor(var2)) {
               var4 = true;
            } else {
               var4 = false;
            }

            var8 = (Double)var9;
            if (var4) {
               var8 = (Double)var9;
               if (var2 <= 9.007199254740991E15) {
                  var8 = var2;
               }
            }
         }

         return var8;
      }
   }

   private static final String gxModelsText(JSONObject var0, String var1, String var2) {
      String var3;
      if (var0 != null && var0.has(var1) && !var0.isNull(var1)) {
         var3 = var0.optString(var1, var2);
         Intrinsics.checkNotNullExpressionValue(var3, "optString(...)");
      } else {
         var3 = var2;
      }

      return var3;
   }

   // $VF: synthetic method
   static String gxModelsText$default(JSONObject var0, String var1, String var2, int var3, Object var4) {
      if ((var3 & 4) != 0) {
         var2 = "";
      }

      return gxModelsText(var0, var1, var2);
   }
}
