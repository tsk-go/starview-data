package com.starpilot.starview;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.OkHttpClient.Builder;
import org.json.JSONObject;

public final class StorageInfo {
   public static final StorageInfo INSTANCE = new StorageInfo();
   private static final Lazy http$delegate = LazyKt.lazy(new StorageInfo$$ExternalSyntheticLambda0());

   // $VF: synthetic method
   public static OkHttpClient $r8$lambda$ztqFHg2G0pZy59s9fT3Ujdfo5KQ() {
      return http_delegate$lambda$0();
   }

   private StorageInfo() {
   }

   // $VF: synthetic method
   public static JSONObject fetch$default(StorageInfo var0, String var1, int var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = 8090;
      }

      return var0.fetch(var1, var2);
   }

   private final OkHttpClient getHttp() {
      return (OkHttpClient)http$delegate.getValue();
   }

   private static final OkHttpClient http_delegate$lambda$0() {
      return new Builder().addInterceptor(Link.INSTANCE.getAuthInterceptor()).connectTimeout(3L, TimeUnit.SECONDS).readTimeout(5L, TimeUnit.SECONDS).build();
   }

   public final StorageInfo.Copying copying(JSONObject var1) {
      if (var1 != null) {
         var1 = var1.optJSONObject("stick");
         if (var1 != null) {
            JSONObject var16 = var1.optJSONObject("progress");
            if (var16 != null) {
               String var14 = var16.optString("phase", "copying");
               if (!Intrinsics.areEqual(var14, "copying")) {
                  Intrinsics.checkNotNull(var14);
                  boolean var24;
                  if (var14.length() > 0) {
                     var24 = true;
                  } else {
                     var24 = false;
                  }

                  String var23;
                  if (var24) {
                     StringBuilder var21 = new StringBuilder();
                     char var2 = var14.charAt(0);
                     String var28 = String.valueOf(var2);
                     Intrinsics.checkNotNull(var28, "null cannot be cast to non-null type java.lang.String");
                     var28 = var28.toUpperCase(Locale.ROOT);
                     Intrinsics.checkNotNullExpressionValue(var28, "toUpperCase(...)");
                     StringBuilder var22 = var21.append(var28);
                     var28 = var14.substring(1);
                     Intrinsics.checkNotNullExpressionValue(var28, "substring(...)");
                     var23 = var22.append(var28).toString();
                  } else {
                     var23 = var14;
                  }

                  return new StorageInfo.Copying(var14, null, var23 + "…");
               }

               long var12 = var16.optLong("total_bytes");
               long var10 = var16.optLong("done_bytes");
               if (var12 <= 0L) {
                  return new StorageInfo.Copying(var14, 100, "Nothing new to copy");
               }

               int var7 = RangesKt.coerceIn((int)(100 * var10 / var12), 0, 100);
               double var5 = var16.optDouble("eta_s", Double.NaN);
               double var3 = var16.optDouble("rate_MBps", Double.NaN);
               StringBuilder var19 = new StringBuilder();
               String var15 = INSTANCE.size(var10);
               String var17 = INSTANCE.size(var12);
               int var8 = var16.optInt("done_files");
               int var9 = var16.optInt("total_files");
               var19.append(var7 + "%  ·  " + var15 + " of " + var17 + "  ·  " + var8 + " of " + var9 + " files");
               if (!Double.isNaN(var3) && var3 > 0.0) {
                  var15 = String.format("%.0f", Arrays.copyOf(new Object[]{var3}, 1));
                  Intrinsics.checkNotNullExpressionValue(var15, "format(...)");
                  var19.append("  ·  " + var15 + " MB/s");
               }

               if (!Double.isNaN(var5) && var5 > 0.0) {
                  var15 = INSTANCE.dur(var5);
                  var19.append("  ·  " + var15 + " left");
               }

               Unit var27 = Unit.INSTANCE;
               String var20 = var19.toString();
               Intrinsics.checkNotNullExpressionValue(var20, "toString(...)");
               return new StorageInfo.Copying(var14, var7, var20);
            }
         }
      }

      return null;
   }

   public final String dur(double var1) {
      int var4 = MathKt.roundToInt(var1 / 60);
      String var5;
      if (var4 < 1) {
         var5 = "under a minute";
      } else {
         StringBuilder var6;
         if (var4 < 60) {
            var6 = new StringBuilder().append("about ").append(var4);
         } else {
            int var3 = var4 / 60;
            var6 = new StringBuilder().append("about ").append(var3).append(" h ").append(var4 % 60);
         }

         var5 = var6.append(" min").toString();
      }

      return var5;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final JSONObject fetch(String var1, int var2) {
      Intrinsics.checkNotNullParameter(var1, "host");
      Object var3 = null;

      Response var23;
      try {
         OkHttpClient var6 = this.getHttp();
         okhttp3.Request.Builder var4 = new okhttp3.Request.Builder();
         StringBuilder var5 = new StringBuilder();
         var23 = var6.newCall(var4.url(var5.append("http://").append(var1).append(":").append(var2).append("/storage").toString()).build()).execute();
      } catch (Exception var20) {
         return (JSONObject)var3;
      }

      label82: {
         try {
            Response var25 = var23;
            if (var25.isSuccessful()) {
               ResponseBody var26 = var25.body();
               Intrinsics.checkNotNull(var26);
               var22 = new JSONObject(var26.string());
               break label82;
            }
         } catch (Throwable var21) {
            Throwable var24 = var21;

            try {
               throw var24;
            } finally {
               try {
                  CloseableKt.closeFinally(var23, var24);
               } catch (Exception var18) {
                  return (JSONObject)var3;
               }
            }
         }

         var22 = null;
      }

      try {
         CloseableKt.closeFinally(var23, null);
      } catch (Exception var17) {
         var22 = (JSONObject)var3;
      }

      return var22;
   }

   public final String gb(JSONObject var1, String var2) {
      Intrinsics.checkNotNullParameter(var1, "o");
      Intrinsics.checkNotNullParameter(var2, "k");
      double var3 = var1.optDouble(var2, Double.NaN);
      String var5;
      if (Double.isNaN(var3)) {
         var5 = "?";
      } else if (var3 >= 100.0) {
         var5 = String.valueOf(MathKt.roundToInt(var3));
      } else {
         var5 = String.format("%.1f", Arrays.copyOf(new Object[]{var3}, 1));
         Intrinsics.checkNotNullExpressionValue(var5, "format(...)");
      }

      return var5;
   }

   public final String mb(int var1) {
      return this.size(var1 * 1000000L);
   }

   public final List<Pair<String, String>> rows(JSONObject var1) {
      if (var1 == null) {
         return CollectionsKt.listOf(TuplesKt.to("Storage", "comma not reachable (or starviewd too old: install the latest starviewd.py)"));
      } else {
         ArrayList var8 = new ArrayList();
         JSONObject var6 = var1.optJSONObject("internal");
         if (var6 != null) {
            Collection var7 = var8;
            String var10 = this.gb(var6, "free_GB");
            String var9 = this.gb(var6, "total_GB");
            var7.add(TuplesKt.to("Internal storage", var10 + " GB free of " + var9 + " GB  ·  openpilot records here"));
            var7 = var8;
            int var4 = var6.optInt("segments");
            var9 = this.whenStr(var6.optDouble("newest_time"));
            String var28 = this.whenStr(var6.optDouble("oldest_time"));
            var7.add(TuplesKt.to("Drives on the comma", var4 + " segments  ·  newest " + var9 + "  ·  oldest " + var28));
         }

         JSONObject var45 = var1.optJSONObject("stick");
         if (var45 != null && var45.optBoolean("present")) {
            Collection var36 = var8;
            String var30;
            if (var45.has("stick_free_GB")) {
               String var29 = this.gb(var45, "stick_free_GB");
               String var46 = this.gb(var45, "stick_size_GB");
               var30 = var29 + " GB free of " + var46 + " GB  ·  archive copy, only used while parked";
            } else {
               var30 = "connected, no copy finished yet";
            }

            var36.add(TuplesKt.to("USB stick", var30));
            StorageInfo.Copying var11 = this.copying(var1);
            if (var11 != null) {
               var8.add(TuplesKt.to("Copying to the stick now", var11.getLine()));
            }

            double var2 = var45.optDouble("last_copy_time", Double.NaN);
            var36 = var8;
            boolean var5 = Double.isNaN(var2);
            JSONObject var31 = "–";
            String var12;
            if (var5) {
               var12 = "–";
            } else {
               String var47 = this.whenStr(var2);
               String var13 = this.mb(var45.optInt("last_copy_MB"));
               var12 = var47 + "  ·  " + var13;
            }

            var36.add(TuplesKt.to("Last copy to the stick", var12));
            if (var45.has("stick_segments")) {
               Collection var14 = var8;
               int var23 = var45.optInt("stick_segments");
               String var38 = this.whenStr(var45.optDouble("stick_newest_time"));
               String var48 = this.whenStr(var45.optDouble("stick_oldest_time"));
               var14.add(TuplesKt.to("Drives on the stick", var23 + " segments  ·  newest " + var38 + "  ·  oldest " + var48));
            }

            var2 = var45.optDouble("run_time", Double.NaN);
            String var39 = var45.optString("result");
            Collection var49 = var8;
            String var15;
            if (Double.isNaN(var2)) {
               if (var45.has("time")) {
                  var15 = var45.optString("time");
               } else {
                  var15 = "never";
               }
            } else {
               var15 = this.whenStr(var2);
            }

            CharSequence var40 = var39;
            boolean var24;
            if (var40.length() == 0) {
               var24 = 1;
            } else {
               var24 = 0;
            }

            if (!var24) {
               var31 = var40;
            }

            String var41;
            if (!var45.optBoolean("auto", true)) {
               var41 = "  ·  auto copy OFF";
            } else {
               var41 = "  ·  auto copy every 5 min while parked";
            }

            var49.add(TuplesKt.to("Last check", var15 + "  ·  " + var31 + var41));
            var31 = var45.optJSONObject("auto_last");
            if (var31 != null) {
               var2 = var31.optDouble("time", Double.NaN);
               String var16;
               if (var31.optBoolean("running")) {
                  var16 = "running now";
               } else if (var31.isNull("rc")) {
                  StorageInfo var17 = INSTANCE;
                  String var33 = var31.optString("out");
                  Intrinsics.checkNotNullExpressionValue(var33, "optString(...)");
                  var var18 = var17.runLine(var33);
                  if (var18.length() == 0) {
                     var24 = (boolean)1;
                  } else {
                     var24 = (boolean)0;
                  }

                  if (var24) {
                     var18 = "stopped";
                  }

                  var16 = var18;
               } else {
                  StorageInfo var42 = INSTANCE;
                  String var19 = var31.optString("out");
                  Intrinsics.checkNotNullExpressionValue(var19, "optString(...)");
                  var var20 = var42.runLine(var19);
                  if (var20.length() == 0) {
                     var24 = (boolean)1;
                  } else {
                     var24 = (boolean)0;
                  }

                  if (var24) {
                     var24 = var31.optInt("rc");
                     var20 = "exit " + var24;
                  }

                  var16 = var20;
               }

               Collection var34 = var8;
               String var43 = INSTANCE.whenStr(var2);
               var34.add(TuplesKt.to("Last automatic copy", var43 + "  ·  " + var16));
            }

            return var8;
         } else {
            var8.add(TuplesKt.to("USB stick", "not connected"));
            return var8;
         }
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public final String runLine(String var1) {
      Intrinsics.checkNotNullParameter(var1, "out");
      String var3 = StringsKt.trimStart(var1).toString();
      int var2 = 0;
      if (!StringsKt.startsWith$default(var3, "{", false, 2, null)) {
         return var1;
      } else {
         JSONObject var5;
         try {
            var5 = new JSONObject(var1);
            var3 = var5.optString("result");
         } catch (Exception var10) {
            return StringsKt.take(var1, 80);
         }

         label50: {
            try {
               if (var3.length() != 0) {
                  break label50;
               }
            } catch (Exception var9) {
               return StringsKt.take(var1, 80);
            }

            var2 = 1;
         }

         if (var2) {
            var3 = "done";
         }

         String var14;
         try {
            var14 = var3;
         } catch (Exception var8) {
            return StringsKt.take(var1, 80);
         }

         var3 = var14;

         label38: {
            try {
               if (!Intrinsics.areEqual(var14, "ok")) {
                  break label38;
               }
            } catch (Exception var7) {
               return StringsKt.take(var1, 80);
            }

            var3 = "finished";
         }

         var14 = var3;

         try {
            if (var5.has("copied_MB")) {
               var14 = this.mb(var5.optInt("copied_MB"));
               var2 = var5.optInt("files");
               StringBuilder var16 = new StringBuilder();
               var14 = var16.append(var3).append("  ·  ").append(var14).append(" copied, ").append(var2).append(" files").toString();
            }
         } catch (Exception var6) {
            var14 = StringsKt.take(var1, 80);
         }

         return var14;
      }
   }

   public final String size(long var1) {
      String var3;
      if (var1 >= 1000000000L) {
         var3 = String.format("%.1f GB", Arrays.copyOf(new Object[]{var1 / 1.0E9}, 1));
         Intrinsics.checkNotNullExpressionValue(var3, "format(...)");
      } else {
         var1 /= 1000000L;
         var3 = var1 + " MB";
      }

      return var3;
   }

   public final String whenStr(double var1) {
      if (!Double.isNaN(var1) && !(var1 <= 0.0)) {
         Calendar var3 = Calendar.getInstance();
         var3.setTimeInMillis((long)(1000 * var1));
         Calendar var4 = Calendar.getInstance();
         String var5 = String.format("%02d:%02d", Arrays.copyOf(new Object[]{var3.get(11), var3.get(12)}, 2));
         Intrinsics.checkNotNullExpressionValue(var5, "format(...)");
         StringBuilder var7;
         String var8;
         switch ((var4.get(1) - var3.get(1)) * 366 + var4.get(6) - var3.get(6)) {
            case 0:
               var7 = new StringBuilder();
               var8 = "today ";
               break;
            case 1:
               var7 = new StringBuilder();
               var8 = "yesterday ";
               break;
            default:
               String var6 = new SimpleDateFormat("MMM d HH:mm", Locale.US).format(var3.getTime());
               Intrinsics.checkNotNullExpressionValue(var6, "format(...)");
               return var6;
         }

         return var7.append(var8).append(var5).toString();
      } else {
         return "–";
      }
   }

   public static final class Copying {
      private final String line;
      private final Integer pct;
      private final String phase;

      public Copying(String var1, Integer var2, String var3) {
         Intrinsics.checkNotNullParameter(var1, "phase");
         Intrinsics.checkNotNullParameter(var3, "line");
         super();
         this.phase = var1;
         this.pct = var2;
         this.line = var3;
      }

      public final String getLine() {
         return this.line;
      }

      public final Integer getPct() {
         return this.pct;
      }

      public final String getPhase() {
         return this.phase;
      }
   }
}
