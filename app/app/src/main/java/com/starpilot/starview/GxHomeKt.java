package com.starpilot.starview;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Result.Companion;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.ranges.RangesKt;
import kotlin.sequences.SequencesKt;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.json.JSONObject;

public final class GxHomeKt {
   private static final DateTimeFormatter gxHomeDateFmt = DateTimeFormatter.ofPattern("MMM d", Locale.US);
   private static final DateTimeFormatter gxHomeTimeFmt = DateTimeFormatter.ofPattern("h:mm a", Locale.US);

   // $VF: synthetic method
   public static String $r8$lambda$gejD4WXFquER2_7xHA2NC3Eh4Ug/* $VF was: $r8$lambda$gejD4WXFquER2_7xHA2NC3Eh4Ug*/(MatchResult var0) {
      return gxHomeNatural$lambda$5(var0);
   }

   // $VF: synthetic method
   public static String $r8$lambda$raVZqq4Xm7zUe7Z08ENolCJKBVg(MatchResult var0) {
      return gxHomeNatural$lambda$4(var0);
   }

   // $VF: synthetic method
   public static final String access$gxHomeBytes(Object var0) {
      return gxHomeBytes(var0);
   }

   // $VF: synthetic method
   public static final String access$gxHomeDec(Object var0) {
      return gxHomeDec(var0);
   }

   // $VF: synthetic method
   public static final String access$gxHomeDuration(Object var0) {
      return gxHomeDuration(var0);
   }

   // $VF: synthetic method
   public static final String access$gxHomeFmt(double var0) {
      return gxHomeFmt(var0);
   }

   // $VF: synthetic method
   public static final String access$gxHomeInt(Object var0) {
      return gxHomeInt(var0);
   }

   // $VF: synthetic method
   public static final int access$gxHomeNatural(String var0, String var1) {
      return gxHomeNatural(var0, var1);
   }

   // $VF: synthetic method
   public static final double access$gxHomeNum(Object var0) {
      return gxHomeNum(var0);
   }

   // $VF: synthetic method
   public static final String access$gxHomePct(Object var0) {
      return gxHomePct(var0);
   }

   // $VF: synthetic method
   public static final String access$gxHomeRange(String var0, String var1) {
      return gxHomeRange(var0, var1);
   }

   // $VF: synthetic method
   public static final boolean access$gxHomeTruthy(Object var0) {
      return gxHomeTruthy(var0);
   }

   private static final String gxHomeBytes(Object var0) {
      double var1 = Math.max(0.0, gxHomeNum(var0));
      String var3;
      if (var1 >= 1.0737418E9F) {
         String var4 = gxHomeDec(var1 / 1.0737418E9F);
         var0 = new StringBuilder().append(var4);
         var3 = " GB";
      } else if (var1 >= 1048576.0) {
         String var6 = gxHomeDec(var1 / 1048576.0);
         var0 = new StringBuilder().append(var6);
         var3 = " MB";
      } else {
         String var7 = gxHomeInt(var1 / 1024);
         var0 = new StringBuilder().append(var7);
         var3 = " KB";
      }

      return var0.append(var3).toString();
   }

   private static final double gxHomeClamp(Object var0, double var1, double var3) {
      return RangesKt.coerceIn(gxHomeNum(var0), var1, var3);
   }

   // $VF: synthetic method
   static double gxHomeClamp$default(Object var0, double var1, double var3, int var5, Object var6) {
      if ((var5 & 2) != 0) {
         var1 = 0.0;
      }

      if ((var5 & 4) != 0) {
         var3 = 100.0;
      }

      return gxHomeClamp(var0, var1, var3);
   }

   private static final String gxHomeDec(Object var0) {
      var0 = new DecimalFormat("#,##0.#", new DecimalFormatSymbols(Locale.US)).format(gxHomeNum(var0));
      Intrinsics.checkNotNullExpressionValue(var0, "format(...)");
      return var0;
   }

   private static final String gxHomeDuration(Object var0) {
      long var3 = Math.max(0L, Math.round(gxHomeNum(var0)));
      long var5 = 3600;
      long var1 = var3 / var5;
      var3 = var3 % var5 / 60;
      if (var1 > 0L) {
         var0 = new StringBuilder().append(var1).append("h ");
      } else {
         var0 = new StringBuilder();
      }

      return var0.append(var3).append("m").toString();
   }

   private static final String gxHomeFmt(double var0) {
      boolean var4 = Double.isInfinite(var0);
      boolean var3 = true;
      boolean var2;
      if (!var4 && !Double.isNaN(var0)) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (var2) {
         if (var0 == Math.floor(var0)) {
            var2 = var3;
         } else {
            var2 = false;
         }

         if (var2 && Math.abs(var0) < 1.0E15) {
            String var7 = String.valueOf((long)var0);
            return var7;
         }
      }

      String var5 = new BigDecimal(String.valueOf(var0)).stripTrailingZeros().toPlainString();
      Intrinsics.checkNotNullExpressionValue(var5, "toPlainString(...)");
      return var5;
   }

   private static final String gxHomeInt(Object var0) {
      StringCompanionObject var1 = StringCompanionObject.INSTANCE;
      var0 = String.format(Locale.US, "%,d", Arrays.copyOf(new Object[]{Math.round(gxHomeNum(var0))}, 1));
      Intrinsics.checkNotNullExpressionValue(var0, "format(...)");
      return var0;
   }

   private static final int gxHomeNatural(String var0, String var1) {
      Regex var5 = new Regex("\\d+|\\D+");
      Locale var6 = Locale.ROOT;
      Intrinsics.checkNotNullExpressionValue(var6, "ROOT");
      var0 = var0.toLowerCase(var6);
      Intrinsics.checkNotNullExpressionValue(var0, "toLowerCase(...)");
      List var18 = SequencesKt.toList(SequencesKt.map(Regex.findAll$default(var5, var0, 0, 2, null), new GxHomeKt$$ExternalSyntheticLambda0()));
      Regex var9 = new Regex("\\d+|\\D+");
      Locale var15 = Locale.ROOT;
      Intrinsics.checkNotNullExpressionValue(var15, "ROOT");
      var1 = var1.toLowerCase(var15);
      Intrinsics.checkNotNullExpressionValue(var1, "toLowerCase(...)");
      List var7 = SequencesKt.toList(SequencesKt.map(Regex.findAll$default(var9, var1, 0, 2, null), new GxHomeKt$$ExternalSyntheticLambda1()));
      int var2 = 0;

      for (int var4 = Math.min(var18.size(), var7.size()); var2 < var4; var2++) {
         var0 = (String)var18.get(var2);
         String var16 = (String)var7.get(var2);
         int var3;
         if (Character.isDigit(var0.charAt(0)) && Character.isDigit(var16.charAt(0))) {
            BigInteger var13 = StringsKt.toBigIntegerOrNull(var0);
            BigInteger var11 = var13;
            if (var13 == null) {
               var11 = BigInteger.ZERO;
            }

            BigInteger var17 = StringsKt.toBigIntegerOrNull(var16);
            BigInteger var14 = var17;
            if (var17 == null) {
               var14 = BigInteger.ZERO;
            }

            var3 = var11.compareTo(var14);
         } else {
            var3 = var0.compareTo(var16);
         }

         if (var3 != 0) {
            return var3;
         }
      }

      return var18.size() - var7.size();
   }

   private static final String gxHomeNatural$lambda$4(MatchResult var0) {
      Intrinsics.checkNotNullParameter(var0, "it");
      return var0.getValue();
   }

   private static final String gxHomeNatural$lambda$5(MatchResult var0) {
      Intrinsics.checkNotNullParameter(var0, "it");
      return var0.getValue();
   }

   private static final double gxHomeNum(Object var0) {
      double var1;
      double var3;
      boolean var6;
      label48: {
         var6 = true;
         var3 = 0.0;
         if (var0 != null && !Intrinsics.areEqual(var0, JSONObject.NULL)) {
            if (var0 instanceof Number) {
               var1 = ((Number)var0).doubleValue();
               break label48;
            }

            if (var0 instanceof Boolean) {
               if ((Boolean)var0) {
                  var1 = 1.0;
                  break label48;
               }
            } else if (var0 instanceof String) {
               var0 = StringsKt.trim(var0).toString();
               boolean var5;
               if (var0.length() == 0) {
                  var5 = true;
               } else {
                  var5 = false;
               }

               if (var5) {
                  var0 = "0";
               }

               Double var8 = StringsKt.toDoubleOrNull(var0);
               if (var8 != null) {
                  var1 = var8;
                  break label48;
               }
            }
         }

         var1 = 0.0;
      }

      boolean var9;
      if (!Double.isInfinite(var1) && !Double.isNaN(var1)) {
         var9 = var6;
      } else {
         var9 = false;
      }

      if (var9) {
         var3 = var1;
      }

      return var3;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private static final LocalDateTime gxHomeParseDate(String var0) {
      boolean var1 = StringsKt.isBlank(var0);
      Object var4 = null;
      if (var1) {
         return null;
      } else {
         Object var19;
         label123:
         try {
            var19 = Result.Companion;
            var19 = (LocalDateTime)KtCompat.resultOf(OffsetDateTime.parse(var0).atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime());
         } catch (Throwable var16) {
            var19 = Result.Companion;
            var19 = (LocalDateTime)KtCompat.resultOf(ResultKt.createFailure(var16));
            break label123;
         }

         Object var3 = var19;
         if (KtCompat.isFailure(var19)) {
            var3 = null;
         }

         var3 = (LocalDateTime)var3;
         var19 = (LocalDateTime)var3;
         if (var3 == null) {
            label117:
            try {
               Companion var24 = Result.Companion;
               var19 = (LocalDateTime)KtCompat.resultOf(LocalDateTime.parse(var0));
            } catch (Throwable var15) {
               Companion var22 = Result.Companion;
               var19 = (LocalDateTime)KtCompat.resultOf(ResultKt.createFailure(var15));
               break label117;
            }

            var3 = var19;
            if (KtCompat.isFailure(var19)) {
               var3 = null;
            }

            var3 = (LocalDateTime)var3;
            var19 = (LocalDateTime)var3;
            if (var3 == null) {
               label112:
               try {
                  Companion var25 = Result.Companion;
                  var18 = KtCompat.resultOf(LocalDate.parse(var0).atStartOfDay());
               } catch (Throwable var14) {
                  Companion var17 = Result.Companion;
                  var18 = KtCompat.resultOf(ResultKt.createFailure(var14));
                  break label112;
               }

               if (KtCompat.isFailure(var18)) {
                  var18 = var4;
               }

               var19 = (LocalDateTime)var18;
            }
         }

         return var19;
      }
   }

   private static final String gxHomePct(Object var0) {
      long var1 = Math.round(gxHomeClamp$default(var0, 0.0, 0.0, 6, null));
      return var1 + "%";
   }

   private static final String gxHomeRange(String var0, String var1) {
      if (StringsKt.isBlank(var0)) {
         return "No drives yet";
      } else {
         LocalDateTime var2 = gxHomeParseDate(var0);
         if (var2 == null) {
            return var0;
         } else {
            LocalDateTime var4 = gxHomeParseDate(var1);
            if (var4 != null && var4.isAfter(var2)) {
               if (Intrinsics.areEqual(var2.toLocalDate(), var4.toLocalDate())) {
                  var1 = var2.format(gxHomeDateFmt);
                  String var12 = var2.format(gxHomeTimeFmt);
                  var0 = var4.format(gxHomeTimeFmt);
                  return var1 + ", " + var12 + "-" + var0;
               } else {
                  var1 = var2.format(gxHomeDateFmt);
                  String var11 = var2.format(gxHomeTimeFmt);
                  String var3 = var4.format(gxHomeDateFmt);
                  var0 = var4.format(gxHomeTimeFmt);
                  return var1 + ", " + var11 + "-" + var3 + ", " + var0;
               }
            } else {
               var0 = var2.format(gxHomeDateFmt);
               var1 = var2.format(gxHomeTimeFmt);
               return var0 + ", " + var1;
            }
         }
      }
   }

   private static final boolean gxHomeTruthy(Object var0) {
      boolean var5 = false;
      boolean var4 = var5;
      if (var0 != null) {
         if (Intrinsics.areEqual(var0, JSONObject.NULL)) {
            var4 = var5;
         } else if (var0 instanceof Boolean) {
            var4 = (Boolean)var0;
         } else if (var0 instanceof Number) {
            double var1 = ((Number)var0).doubleValue();
            boolean var3;
            if (var1 == 0.0) {
               var3 = true;
            } else {
               var3 = false;
            }

            if (!var3 && !Double.isNaN(var1)) {
               var4 = true;
            } else {
               var4 = var5;
            }
         } else if (var0 instanceof String) {
            var4 = var5;
            if (((CharSequence)var0).length() > 0) {
               var4 = true;
            }
         } else {
            var4 = true;
         }
      }

      return var4;
   }
}
