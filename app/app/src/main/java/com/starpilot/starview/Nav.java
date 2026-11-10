package com.starpilot.starview;

import android.os.Handler;
import android.os.Looper;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

public final class Nav {
   public static final Nav INSTANCE = new Nav();
   private static volatile double along = Double.NaN;
   private static final HashSet<String> announced = new HashSet<>();
   private static boolean busy;
   private static long busySinceMs;
   private static volatile JSONObject destination;
   private static String detailKey;
   private static volatile boolean enabled = true;
   private static volatile String error = "";
   private static String fasterSaid = "";
   private static volatile boolean hasKey = true;
   private static int hint = -1;
   private static double lastAlong = Double.NaN;
   private static long lastAskMs;
   private static String lastStatus = "";
   private static volatile List<Nav.Line> lines = CollectionsKt.emptyList();
   private static volatile boolean loaded;
   private static volatile int look = -1;
   private static final Lazy main$delegate = LazyKt.lazy(new Nav$$ExternalSyntheticLambda2());
   private static volatile boolean offRoute;
   private static volatile JSONObject options = new JSONObject();
   private static final ExecutorService parser = Executors.newSingleThreadExecutor();
   private static volatile Nav.Preview preview;
   private static volatile String previewError = "";
   private static int previewSeq;
   private static volatile JSONObject previewing;
   private static String routeKey;
   private static volatile List<Nav.Route> routes = CollectionsKt.emptyList();
   private static final HashSet<String> said = new HashSet<>();
   private static Nav.Line saidLine;
   private static volatile int selected;
   private static volatile String status = "";
   private static volatile boolean supported = true;
   private static volatile String tileKey;
   private static volatile JSONObject usage = new JSONObject();
   private static volatile int version;

   // $VF: synthetic method
   public static void $r8$lambda$9b8yxP0Ph3chE3iq8CaWQd2hMlI(JSONObject var0) {
      poll$lambda$14$lambda$13(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Brc3ir8N5fGTHlvly0hPwr3Dk8U(int var0, Function1 var1, JSONObject var2, JSONObject var3) {
      return startPreview$lambda$5(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$DKcmvg5Ijf23iS91o3zEI_rSzlQ/* $VF was: $r8$lambda$DKcmvg5Ijf23iS91o3zEI_rSzlQ*/(
      String var0, List var1, JSONObject var2, String var3, int var4, JSONObject var5, JSONObject var6, boolean var7, boolean var8, List var9
   ) {
      return parse$lambda$21(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$JPd6JAZxebVstP9m9Viq4NCl0HA(int var0, DataClient var1, Function1 var2, JSONObject var3) {
      return choose$lambda$30(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$L0peh_FnKQIljOYnEzibW98DfqA/* $VF was: $r8$lambda$L0peh_FnKQIljOYnEzibW98DfqA*/(
      DataClient var0, Function1 var1, JSONObject var2
   ) {
      return setOptions$lambda$32(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$TBGWCdthwu1Z6VS8OyNUB5gBtRI(Function1 var0, DataClient var1, JSONObject var2) {
      return go$lambda$7(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$UL5WEMdVicxA4D9MwPptoHxdDhI(Function0 var0, JSONObject var1) {
      poll$lambda$14$lambda$13$lambda$12(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$_KKDjZIMgZlohBURZtsTztBnk48(JSONObject var0) {
      return poll$lambda$14(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$eYVc3m0qMDHa_oEgUuJjcv_l3Qo/* $VF was: $r8$lambda$eYVc3m0qMDHa_oEgUuJjcv_l3Qo*/(
      DataClient var0, Function1 var1, JSONObject var2
   ) {
      return cancel$lambda$33(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$qy_ocxJ4ESeyIxvEzc5SSOn0vp0(JSONObject var0, JSONObject var1, int var2, Function1 var3) {
      startPreview$lambda$5$lambda$4(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static void $r8$lambda$ww_5v2FN47rQQJfRYTKnASzYICI(int var0, Nav.Preview var1, Function1 var2) {
      startPreview$lambda$5$lambda$4$lambda$3(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Handler $r8$lambda$xCQlHFjNKnXCkZ136tPeIRewF0I() {
      return main_delegate$lambda$0();
   }

   private Nav() {
   }

   // $VF: synthetic method
   public static void cancel$default(Nav var0, DataClient var1, Function1 var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = null;
      }

      var0.cancel(var1, var2);
   }

   private static final Unit cancel$lambda$33(DataClient var0, Function1 var1, JSONObject var2) {
      Intrinsics.checkNotNullParameter(var2, "j");
      boolean var3 = var2.optBoolean("ok");
      Object var4 = null;
      if (var3) {
         destination = null;
         routes = CollectionsKt.emptyList();
         lines = CollectionsKt.emptyList();
         detailKey = null;
         version++;
         INSTANCE.refresh(var0);
      }

      if (var1 != null) {
         String var5;
         if (var2.optBoolean("ok")) {
            var5 = (String)var4;
         } else {
            var5 = var2.optString("error");
         }

         var1.invoke(var5);
      }

      return Unit.INSTANCE;
   }

   // $VF: synthetic method
   public static void choose$default(Nav var0, DataClient var1, int var2, Function1 var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = null;
      }

      var0.choose(var1, var2, var3);
   }

   private static final Unit choose$lambda$30(int var0, DataClient var1, Function1 var2, JSONObject var3) {
      Intrinsics.checkNotNullParameter(var3, "j");
      if (var3.optBoolean("ok")) {
         selected = var0;
         version++;
         INSTANCE.refresh(var1);
      }

      if (var2 != null) {
         String var4;
         if (var3.optBoolean("ok")) {
            var4 = null;
         } else {
            var4 = var3.optString("error");
         }

         var2.invoke(var4);
      }

      return Unit.INSTANCE;
   }

   // $VF: synthetic method
   public static int currentStep$default(Nav var0, Nav.Line var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = var0.getLine();
      }

      return var0.currentStep(var1);
   }

   // $VF: synthetic method
   public static String distanceText$default(Nav var0, double var1, boolean var3, boolean var4, int var5, Object var6) {
      if ((var5 & 4) != 0) {
         var4 = false;
      }

      return var0.distanceText(var1, var3, var4);
   }

   private final Handler getMain() {
      return (Handler)main$delegate.getValue();
   }

   private static final Unit go$lambda$7(Function1 var0, DataClient var1, JSONObject var2) {
      Intrinsics.checkNotNullParameter(var2, "j");
      boolean var4 = var2.optBoolean("ok");
      boolean var3 = true;
      if (!var4) {
         Object var5 = var2.optString("error");
         if (var5.length() != 0) {
            var3 = false;
         }

         if (var3) {
            var5 = "no answer";
         }

         var0.invoke(var5);
         return Unit.INSTANCE;
      } else {
         previewSeq++;
         preview = null;
         previewing = null;
         look = -1;
         previewError = "";
         INSTANCE.newDestination(var1);
         var0.invoke(null);
         return Unit.INSTANCE;
      }
   }

   private final String key(String var1, double var2) {
      int var4 = MathKt.roundToInt(var2 / 50);
      return var1 + "@" + var4;
   }

   private static final Handler main_delegate$lambda$0() {
      return new Handler(Looper.getMainLooper());
   }

   private final Function0<Unit> parse(JSONObject var1) {
      Pair var6 = this.parseRoutes(var1);
      List var11 = (List)var6.component1();
      List var16 = (List)var6.component2();
      boolean var4 = var1.has("details");
      Object var9 = null;
      if (!var4) {
         var16 = null;
      }

      JSONObject var13 = var1.optJSONObject("destination");
      String var12 = var1.optString("status");
      boolean var2 = false;
      int var3 = var1.optInt("selectedRoute", 0);
      JSONObject var8 = var1.optJSONObject("options");
      JSONObject var7 = var8;
      if (var8 == null) {
         var7 = new JSONObject();
      }

      JSONObject var10 = var1.optJSONObject("usage");
      var8 = var10;
      if (var10 == null) {
         var8 = new JSONObject();
      }

      boolean var5 = var1.optBoolean("hasKey", true);
      var4 = var1.optBoolean("enabled", true);
      var var14 = var1.optString("tileKey");
      if (var14.length() == 0) {
         var2 = true;
      }

      if (var2) {
         var14 = (String)var9;
      }

      return new Nav$$ExternalSyntheticLambda10(var14, var11, var13, var12, var3, var7, var8, var5, var4, var16);
   }

   private static final Unit parse$lambda$21(
      String var0, List var1, JSONObject var2, String var3, int var4, JSONObject var5, JSONObject var6, boolean var7, boolean var8, List var9
   ) {
      tileKey = var0;
      routes = var1;
      destination = var2;
      status = var3;
      selected = var4;
      options = var5;
      usage = var6;
      hasKey = var7;
      enabled = var8;
      if (var9 != null) {
         lines = var9;
      } else if (var1.isEmpty()) {
         lines = CollectionsKt.emptyList();
      }

      if (var2 == null) {
         lines = CollectionsKt.emptyList();
         announced.clear();
         detailKey = null;
      }

      return Unit.INSTANCE;
   }

   // $VF: synthetic method
   public static void poll$default(Nav var0, DataClient var1, boolean var2, long var3, boolean var5, int var6, Object var7) {
      if ((var6 & 8) != 0) {
         var5 = false;
      }

      var0.poll(var1, var2, var3, var5);
   }

   private static final Unit poll$lambda$14(JSONObject var0) {
      Intrinsics.checkNotNullParameter(var0, "j");
      boolean var2 = var0.optBoolean("ok");
      boolean var1 = false;
      if (!var2) {
         busy = false;
         String var3 = var0.optString("error");
         Intrinsics.checkNotNull(var3);
         if (StringsKt.contains$default(var3, "unknown action", false, 2, null)) {
            supported = false;
            loaded = true;
            version++;
         } else {
            if (var3.length() > 0) {
               var1 = true;
            }

            if (var1 && !Intrinsics.areEqual(var3, "timeout")) {
               error = var3;
               version++;
            }
         }

         return Unit.INSTANCE;
      } else {
         supported = true;
         error = "";
         if (var0.optBoolean("unchanged")) {
            busy = false;
            if (!loaded) {
               loaded = true;
               version++;
            }

            return Unit.INSTANCE;
         } else {
            parser.execute(new Nav$$ExternalSyntheticLambda4(var0));
            return Unit.INSTANCE;
         }
      }
   }

   private static final void poll$lambda$14$lambda$13(JSONObject var0) {
      Function0 var1;
      try {
         var1 = INSTANCE.parse(var0);
      } catch (Exception var2) {
         var1 = null;
      }

      INSTANCE.getMain().post(new Nav$$ExternalSyntheticLambda1(var1, var0));
   }

   private static final void poll$lambda$14$lambda$13$lambda$12(Function0 var0, JSONObject var1) {
      boolean var3 = false;
      busy = false;
      if (var0 != null) {
         var0.invoke();
         var var5 = var1.optString("routeKey");
         boolean var2;
         if (var5.length() == 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         Object var4 = null;
         if (var2) {
            var5 = null;
         }

         routeKey = var5;
         if (var1.has("details")) {
            var var6 = var1.optString("detailKey");
            var2 = var3;
            if (var6.length() == 0) {
               var2 = true;
            }

            if (var2) {
               var6 = (String)var4;
            }

            detailKey = var6;
         }
      }

      loaded = true;
      version++;
   }

   // $VF: synthetic method
   public static void setOptions$default(Nav var0, DataClient var1, JSONObject var2, Function1 var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = null;
      }

      var0.setOptions(var1, var2, var3);
   }

   private static final Unit setOptions$lambda$32(DataClient var0, Function1 var1, JSONObject var2) {
      Intrinsics.checkNotNullParameter(var2, "j");
      if (var2.optBoolean("ok")) {
         JSONObject var3 = var2.optJSONObject("options");
         if (var3 != null) {
            options = var3;
         }

         version++;
         INSTANCE.refresh(var0);
      }

      if (var1 != null) {
         String var4;
         if (var2.optBoolean("ok")) {
            var4 = null;
         } else {
            var4 = var2.optString("error");
         }

         var1.invoke(var4);
      }

      return Unit.INSTANCE;
   }

   private static final Unit startPreview$lambda$5(int var0, Function1 var1, JSONObject var2, JSONObject var3) {
      Intrinsics.checkNotNullParameter(var3, "j");
      if (var0 != previewSeq) {
         return Unit.INSTANCE;
      } else if (!var3.optBoolean("ok")) {
         var var5 = var3.optString("error");
         boolean var4;
         if (var5.length() == 0) {
            var4 = true;
         } else {
            var4 = false;
         }

         if (var4) {
            var5 = "no answer";
         }

         String var6 = var5;
         Intrinsics.checkNotNull(var6);
         if (StringsKt.contains$default(var6, "unknown action", false, 2, null)) {
            previewing = null;
         } else {
            previewError = var6;
         }

         version++;
         var1.invoke(var6);
         return Unit.INSTANCE;
      } else {
         parser.execute(new Nav$$ExternalSyntheticLambda9(var3, var2, var0, var1));
         return Unit.INSTANCE;
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private static final void startPreview$lambda$5$lambda$4(JSONObject var0, JSONObject var1, int var2, Function1 var3) {
      label24: {
         JSONObject var5;
         List var6;
         List var7;
         String var8;
         Nav.Preview var13;
         try {
            Pair var4 = INSTANCE.parseRoutes(var0);
            var6 = (List)var4.component1();
            var7 = (List)var4.component2();
            var8 = var0.optString("previewId");
            Intrinsics.checkNotNullExpressionValue(var8, "optString(...)");
            var5 = var0.optJSONObject("destination");
         } catch (Exception var10) {
            var11 = null;
            break label24;
         }

         var0 = var5;
         if (var5 == null) {
            var0 = var1;
         }

         try {
            var13 = new Nav.Preview(var8, var0, var6, var7);
         } catch (Exception var9) {
            var11 = null;
            break label24;
         }

         var11 = var13;
      }

      INSTANCE.getMain().post(new Nav$$ExternalSyntheticLambda3(var2, var11, var3));
   }

   private static final void startPreview$lambda$5$lambda$4$lambda$3(int var0, Nav.Preview var1, Function1 var2) {
      if (var0 == previewSeq) {
         if (var1 != null && !var1.getRoutes().isEmpty()) {
            preview = var1;
            previewing = var1.getDestination();
         } else {
            previewError = "No route found";
         }

         version++;
         String var3;
         if (var1 != null && !var1.getRoutes().isEmpty()) {
            var3 = null;
         } else {
            var3 = previewError;
         }

         var2.invoke(var3);
      }
   }

   public final void cancel(DataClient var1, Function1<? super String, Unit> var2) {
      Intrinsics.checkNotNullParameter(var1, "data");
      DataClient.request$default(var1, "nav_cancel", null, new Nav$$ExternalSyntheticLambda7(var1, var2), 2, null);
   }

   public final void cancelPreview() {
      previewSeq++;
      preview = null;
      previewing = null;
      look = -1;
      previewError = "";
      version++;
   }

   public final void choose(DataClient var1, int var2, Function1<? super String, Unit> var3) {
      Intrinsics.checkNotNullParameter(var1, "data");
      var1.request("nav_route", MapsKt.mapOf(TuplesKt.to("index", var2)), new Nav$$ExternalSyntheticLambda11(var2, var1, var3));
   }

   public final int currentStep(Nav.Line var1) {
      double var2 = along;
      if (var1 != null) {
         List var7 = var1.getSteps();
         if (var7 != null) {
            boolean var4;
            if (!Double.isInfinite(var2) && !Double.isNaN(var2)) {
               var4 = 1;
            } else {
               var4 = 0;
            }

            if (var4 && !var7.isEmpty()) {
               int var5 = 0;
               var4 = 0;

               for (int var6 = var7.size(); var4 < var6; var4++) {
                  if (((Nav.Step)var7.get(var4)).getStartM() <= 1.0 + var2) {
                     var5 = var4;
                  }
               }

               return var5;
            }

            return -1;
         }
      }

      return -1;
   }

   public final double[] decodePolyline(String var1) {
      Intrinsics.checkNotNullParameter(var1, "s");
      ArrayList var9 = new ArrayList(var1.length() / 3);
      int var2 = 0;
      int var5 = 0;
      int var4 = 0;

      while (var2 < var1.length()) {
         label39:
         for (int var3 = 0; var3 < 2; var3++) {
            int var6 = 0;
            int var7 = 0;

            for (int var8 = var2; var8 < var1.length(); var8 = var2) {
               var2 = var8 + 1;
               var8 = var1.charAt(var8) - '?';
               var7 |= (var8 & 31) << var6;
               var6 += 5;
               if (var8 < 32) {
                  if ((var7 & 1) != 0) {
                     var6 = ~(var7 >> 1);
                  } else {
                     var6 = var7 >> 1;
                  }

                  if (var3 == 0) {
                     var5 += var6;
                  } else {
                     var4 += var6;
                  }
                  continue label39;
               }
            }

            return CollectionsKt.toDoubleArray(var9);
         }

         var9.add(var5 / 100000.0);
         var9.add(var4 / 100000.0);
      }

      return CollectionsKt.toDoubleArray(var9);
   }

   public final String distanceText(double var1, boolean var3, boolean var4) {
      if (!var3) {
         var1 = 3.28084 * var1;
         if (var1 < 1000.0) {
            int var12 = RangesKt.coerceAtLeast(MathKt.roundToInt(var1 / 50) * 50, 50);
            String var15;
            if (var4) {
               var15 = "feet";
            } else {
               var15 = "ft";
            }

            return var12 + " " + var15;
         } else {
            var1 /= 5280;
            String var14;
            if (var1 < 10.0) {
               var14 = String.format("%.1f mi", Arrays.copyOf(new Object[]{var1}, 1));
               Intrinsics.checkNotNullExpressionValue(var14, "format(...)");
            } else {
               int var11 = MathKt.roundToInt(var1);
               var14 = var11 + " mi";
            }

            return var14;
         }
      } else {
         StringBuilder var6;
         String var7;
         if (var1 < 1000.0) {
            int var5 = RangesKt.coerceAtLeast(MathKt.roundToInt(var1 / 50) * 50, 50);
            var6 = new StringBuilder().append(var5);
            var7 = " m";
         } else {
            if (var1 < 10000.0) {
               String var13 = String.format("%.1f km", Arrays.copyOf(new Object[]{var1 / 1000}, 1));
               Intrinsics.checkNotNullExpressionValue(var13, "format(...)");
               return var13;
            }

            int var10 = MathKt.roundToInt(var1 / 1000);
            var6 = new StringBuilder().append(var10);
            var7 = " km";
         }

         return var6.append(var7).toString();
      }
   }

   public final String durationText(double var1) {
      int var4 = RangesKt.coerceAtLeast(MathKt.roundToInt(var1 / 60), 1);
      StringBuilder var5;
      if (var4 < 60) {
         var5 = new StringBuilder().append(var4);
      } else {
         int var3 = var4 / 60;
         var5 = new StringBuilder().append(var3).append(" h ").append(var4 % 60);
      }

      return var5.append(" min").toString();
   }

   public final String encodePolyline(List<Pair<Double, Double>> var1) {
      Intrinsics.checkNotNullParameter(var1, "points");
      StringBuilder var19 = new StringBuilder();
      long var13 = 0L;
      long var7 = 0L;

      for (Pair var21 : var1) {
         double var2 = ((Number)var21.component1()).doubleValue();
         double var4 = ((Number)var21.component2()).doubleValue();
         long var17 = Math.round(var2 * 100000.0);
         long var15 = Math.round(100000.0 * var4);

         for (int var6 = 0; var6 < 2; var6++) {
            long var11 = new long[]{var17 - var13, var15 - var7}[var6];
            if (var11 < 0L) {
               var11 = ~(var11 << 1);
            } else {
               var11 <<= 1;
            }

            while (var11 >= 32L) {
               var19.append((char)((var11 & 31L | 32L) + 63));
               var11 >>= 5;
            }

            var19.append((char)(63 + var11));
         }

         var13 = var17;
         var7 = var15;
      }

      String var22 = var19.toString();
      Intrinsics.checkNotNullExpressionValue(var22, "toString(...)");
      return var22;
   }

   public final String etaText(double var1) {
      String var3 = DateFormat.getTimeInstance(3).format(new Date(System.currentTimeMillis() + (long)(1000 * var1)));
      Intrinsics.checkNotNullExpressionValue(var3, "format(...)");
      return var3;
   }

   public final int faster() {
      Nav.Route var7 = this.getRoute();
      byte var5 = -1;
      if (var7 == null) {
         return -1;
      } else {
         int var3 = -1;
         double var1 = var7.getDurationS();

         for (Nav.Route var6 : routes) {
            if (var6.getIndex() != var7.getIndex() && !var6.getClosed() && var6.getDurationS() < var1) {
               var3 = var6.getIndex();
               var1 = var6.getDurationS();
            }
         }

         int var4 = var5;
         if (var3 >= 0) {
            var4 = var5;
            if (var7.getDurationS() - var1 >= 180.0) {
               var4 = var5;
               if (var1 <= var7.getDurationS() * 0.92) {
                  var4 = var3;
               }
            }
         }

         return var4;
      }
   }

   public final void forget() {
      routeKey = null;
      detailKey = null;
      destination = null;
      routes = CollectionsKt.emptyList();
      lines = CollectionsKt.emptyList();
      status = "";
      loaded = false;
      version++;
   }

   public final boolean getActive() {
      boolean var1;
      if (destination != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public final double getAlong() {
      return along;
   }

   public final JSONObject getDestination() {
      return destination;
   }

   public final boolean getEnabled() {
      return enabled;
   }

   public final String getError() {
      return error;
   }

   public final boolean getHasKey() {
      return hasKey;
   }

   public final boolean getInPreview() {
      boolean var1;
      if (previewing != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public final Nav.Line getLine() {
      return CollectionsKt.getOrNull(lines, selected);
   }

   public final List<Nav.Line> getLines() {
      return lines;
   }

   public final boolean getLoaded() {
      return loaded;
   }

   public final int getLook() {
      return look;
   }

   public final boolean getOffRoute() {
      return offRoute;
   }

   public final JSONObject getOptions() {
      return options;
   }

   public final Nav.Preview getPreview() {
      return preview;
   }

   public final String getPreviewError() {
      return previewError;
   }

   public final JSONObject getPreviewing() {
      return previewing;
   }

   public final Nav.Route getRoute() {
      return CollectionsKt.getOrNull(routes, selected);
   }

   public final List<Nav.Route> getRoutes() {
      return routes;
   }

   public final int getSelected() {
      return selected;
   }

   public final JSONObject getShownDestination() {
      JSONObject var2 = previewing;
      JSONObject var1 = var2;
      if (var2 == null) {
         var1 = destination;
      }

      return var1;
   }

   public final Nav.Line getShownLine() {
      return CollectionsKt.getOrNull(this.getShownLines(), this.getShownSelected());
   }

   public final List<Nav.Line> getShownLines() {
      List var3;
      if (this.getInPreview()) {
         Nav.Preview var1 = preview;
         if (var1 != null) {
            List var2 = var1.getLines();
            if (var2 != null) {
               return var2;
            }
         }

         var3 = CollectionsKt.emptyList();
      } else {
         var3 = lines;
      }

      return var3;
   }

   public final Nav.Route getShownRoute() {
      Iterator var3 = this.getShownRoutes().iterator();

      boolean var1;
      Object var2;
      do {
         if (!var3.hasNext()) {
            var2 = null;
            break;
         }

         var2 = var3.next();
         if (((Nav.Route)var2).getIndex() == INSTANCE.getShownSelected()) {
            var1 = true;
         } else {
            var1 = false;
         }
      } while (!var1);

      return (Nav.Route)var2;
   }

   public final List<Nav.Route> getShownRoutes() {
      List var3;
      if (this.getInPreview()) {
         Nav.Preview var1 = preview;
         if (var1 != null) {
            List var2 = var1.getRoutes();
            if (var2 != null) {
               return var2;
            }
         }

         var3 = CollectionsKt.emptyList();
      } else {
         var3 = routes;
      }

      return var3;
   }

   public final int getShownSelected() {
      int var1;
      if (look >= 0) {
         var1 = look;
      } else if (this.getInPreview()) {
         var1 = 0;
      } else {
         var1 = selected;
      }

      return var1;
   }

   public final String getStatus() {
      return status;
   }

   public final boolean getSupported() {
      return supported;
   }

   public final String getTileKey() {
      return tileKey;
   }

   public final JSONObject getUsage() {
      return usage;
   }

   public final int getVersion() {
      return version;
   }

   public final void go(DataClient var1, Function1<? super String, Unit> var2) {
      Intrinsics.checkNotNullParameter(var1, "data");
      Intrinsics.checkNotNullParameter(var2, "then");
      Nav.Preview var4 = preview;
      if (var4 == null) {
         var2.invoke("No routes yet");
      } else {
         int var3 = this.getShownSelected();
         var1.request(
            "nav_go",
            MapsKt.mapOf(new Pair[]{TuplesKt.to("previewId", var4.getId()), TuplesKt.to("index", var3)}),
            new Nav$$ExternalSyntheticLambda6(var2, var1)
         );
      }
   }

   public final String incidentWords(Nav.Incident var1) {
      Intrinsics.checkNotNullParameter(var1, "i");
      String var2;
      if (var1.getClosed() || Intrinsics.areEqual(var1.getType(), "road_closure")) {
         var2 = "Road closed";
      } else if (Intrinsics.areEqual(var1.getType(), "accident")) {
         var2 = "Crash reported";
      } else if (Intrinsics.areEqual(var1.getType(), "construction")) {
         var2 = "Construction";
      } else if (Intrinsics.areEqual(var1.getType(), "disabled_vehicle")) {
         var2 = "Stalled vehicle";
      } else if (Intrinsics.areEqual(var1.getType(), "lane_restriction")) {
         var2 = "Lane closed";
      } else if (Intrinsics.areEqual(var1.getType(), "road_hazard")) {
         var2 = "Hazard";
      } else if (Intrinsics.areEqual(var1.getType(), "congestion")) {
         var2 = "Traffic jam";
      } else if (Intrinsics.areEqual(var1.getType(), "weather")) {
         var2 = "Weather hazard";
      } else if (!Intrinsics.areEqual(var1.getType(), "planned_event") && !Intrinsics.areEqual(var1.getType(), "mass_transit")) {
         var2 = "Traffic incident";
      } else {
         var2 = "Event";
      }

      return var2;
   }

   public final double latOf(double var1) {
      return Math.toDegrees(Math.atan(Math.sinh((1.0 - 2.0 * var1) * Math.PI)));
   }

   public final double lonOf(double var1) {
      return 360.0 * var1 - 180.0;
   }

   public final double mercX(double var1) {
      return (180.0 + var1) / 360.0;
   }

   public final double mercY(double var1) {
      var1 = Math.toRadians(RangesKt.coerceIn(var1, -85.0, 85.0));
      return (1.0 - Math.log(Math.tan(var1) + 1.0 / Math.cos(var1)) / Math.PI) / 2.0;
   }

   public final double metres(double var1, double var3, double var5, double var7) {
      double var9 = (var5 - var1) * 111195.0;
      var1 = (var7 - var3) * 111195.0 * Math.cos(Math.toRadians((var1 + var5) / 2));
      return Math.sqrt(var1 * var1 + var9 * var9);
   }

   public final void newDestination(DataClient var1) {
      Intrinsics.checkNotNullParameter(var1, "data");
      routes = CollectionsKt.emptyList();
      lines = CollectionsKt.emptyList();
      detailKey = null;
      version++;
      this.refresh(var1);
   }

   public final Pair<Nav.Step, Double> nextLanes() {
      Nav.Line var6 = this.getLine();
      Object var7 = null;
      if (var6 == null) {
         return null;
      } else {
         double var1 = along;
         boolean var5 = Double.isInfinite(var1);
         boolean var4 = true;
         boolean var3;
         if (!var5 && !Double.isNaN(var1)) {
            var3 = true;
         } else {
            var3 = false;
         }

         if (!var3) {
            return null;
         } else {
            Nav.Step var8 = CollectionsKt.getOrNull(var6.getSteps(), this.currentStep(var6) + 1);
            if (var8 == null) {
               return null;
            } else {
               var1 = var8.getStartM() - var1;
               Pair var11 = (Pair)var7;
               if (var8.getLanes().size() >= 2) {
                  if (0.0 <= var1 && var1 <= 3000.0) {
                     var3 = var4;
                  } else {
                     var3 = false;
                  }

                  var11 = (Pair)var7;
                  if (var3) {
                     var11 = TuplesKt.to(var8, var1);
                  }
               }

               return var11;
            }
         }
      }
   }

   public final Nav.Line parseLine(JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "o");
      String var10 = var1.optString("polyline");
      Intrinsics.checkNotNullExpressionValue(var10, "optString(...)");
      double[] var18 = this.decodePolyline(var10);
      if (var18.length < 4) {
         return null;
      } else {
         int var4 = var18.length / 2;
         double[] var40 = new double[var4];
         double[] var15 = new double[var4];
         double[] var14 = new double[var4];

         for (int var5 = 0; var5 < var4; var5++) {
            var40[var5] = this.mercX(var18[var5 * 2 + 1]);
            var15[var5] = this.mercY(var18[var5 * 2]);
            if (var5 > 0) {
               var14[var5] = var14[var5 - 1] + this.metres(var18[var5 * 2 - 2], var18[var5 * 2 - 1], var18[var5 * 2], var18[var5 * 2 + 1]);
            }
         }

         JSONArray var11 = var1.optJSONArray("traffic");
         JSONArray var16 = var11;
         if (var11 == null) {
            var16 = new JSONArray();
         }

         double[] var13 = new double[var16.length()];
         double[] var12 = new double[var16.length()];
         int[] var42 = new int[var16.length()];
         int var32 = 0;

         for (int var6 = var16.length(); var32 < var6; var32++) {
            JSONArray var17 = var16.optJSONArray(var32);
            if (var17 != null) {
               var13[var32] = var17.optDouble(0);
               var12[var32] = var17.optDouble(1);
               var42[var32] = var17.optInt(2);
            }
         }

         ArrayList var48 = new ArrayList();
         JSONArray var19 = var1.optJSONArray("incidents");
         double[] var43;
         int[] var46;
         double[] var47;
         if (var19 != null) {
            int var37 = var19.length();
            var32 = 0;

            for (JSONArray var53 = var19; var32 < var37; var32++) {
               JSONObject var23 = var53.optJSONObject(var32);
               if (var23 != null) {
                  String var22 = var23.optString("id");
                  Intrinsics.checkNotNullExpressionValue(var22, "optString(...)");
                  String var20 = var23.optString("type");
                  Intrinsics.checkNotNullExpressionValue(var20, "optString(...)");
                  String var60 = var23.optString("subType");
                  Intrinsics.checkNotNullExpressionValue(var60, "optString(...)");
                  String var21 = var23.optString("impact");
                  Intrinsics.checkNotNullExpressionValue(var21, "optString(...)");
                  String var24 = var23.optString("description");
                  Intrinsics.checkNotNullExpressionValue(var24, "optString(...)");
                  var48.add(
                     new Nav.Incident(
                        var22,
                        var20,
                        var60,
                        var21,
                        var24,
                        var23.optBoolean("closed"),
                        var23.optDouble("startMeters"),
                        var23.optDouble("endMeters"),
                        var23.optDouble("latitude"),
                        var23.optDouble("longitude")
                     )
                  );
               }
            }

            var47 = var12;
            var12 = var40;
            var46 = var42;
            var43 = var13;
         } else {
            var47 = var12;
            var43 = var13;
            var46 = var42;
            var12 = var40;
         }

         ArrayList var72 = new ArrayList();
         JSONArray var55 = var1.optJSONArray("steps");
         ArrayList var41;
         if (var55 != null) {
            boolean var7 = false;
            int var38 = 0;
            var32 = var55.length();
            var41 = var48;

            for (JSONArray var49 = var55; var38 < var32; var38++) {
               JSONObject var73 = var49.optJSONObject(var38);
               if (var73 != null) {
                  var19 = var73.optJSONArray("lanes");
                  List var58;
                  if (var19 != null) {
                     boolean var8 = false;
                     Iterable var56 = RangesKt.until(0, var19.length());
                     var var64 = new ArrayList();
                     Iterator var68 = var56.iterator();

                     while (var68.hasNext()) {
                        JSONObject var74 = var19.optJSONObject(((IntIterator)var68).nextInt());
                        if (var74 != null) {
                           var64.add(var74);
                        }
                     }

                     List var57 = var64;
                     Iterable var65 = var57;
                     var var75 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var65, 10));

                     for (JSONObject var26 : var65) {
                        JSONArray var27 = var26.optJSONArray("d");
                        List var70;
                        if (var27 == null) {
                           var70 = CollectionsKt.emptyList();
                        } else {
                           Iterable var28 = RangesKt.until(0, var27.length());
                           var var69 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var28, 10));
                           Iterator var79 = var28.iterator();

                           while (var79.hasNext()) {
                              var69.add(var27.optString(((IntIterator)var79).nextInt()));
                           }

                           var70 = var69;
                        }

                        boolean var9 = var26.optBoolean("ok");
                        String var78 = var26.optString("use");
                        Intrinsics.checkNotNullExpressionValue(var78, "optString(...)");
                        var75.add(new Nav.Lane(var70, var9, var78));
                     }

                     var58 = var75;
                  } else {
                     var58 = CollectionsKt.emptyList();
                  }

                  String var77 = var73.optString("text");
                  Intrinsics.checkNotNullExpressionValue(var77, "optString(...)");
                  String var71 = var73.optString("type");
                  Intrinsics.checkNotNullExpressionValue(var71, "optString(...)");
                  String var62 = var73.optString("modifier");
                  Intrinsics.checkNotNullExpressionValue(var62, "optString(...)");
                  String var76 = var73.optString("road");
                  Intrinsics.checkNotNullExpressionValue(var76, "optString(...)");
                  String var66 = var73.optString("ref");
                  Intrinsics.checkNotNullExpressionValue(var66, "optString(...)");
                  var72.add(
                     new Nav.Step(
                        var77,
                        var71,
                        var62,
                        var76,
                        var66,
                        var73.optDouble("startMeters"),
                        var73.optDouble("distanceMeters"),
                        var73.optDouble("durationSeconds"),
                        var73.optDouble("latitude"),
                        var73.optDouble("longitude"),
                        var58
                     )
                  );
               }
            }
         } else {
            var41 = var48;
         }

         Nav.Step var50 = CollectionsKt.lastOrNull(var72);
         double var2;
         if (var50 != null) {
            var2 = var50.getStartM() + var50.getDistanceM();
         } else {
            var2 = 0.0;
         }

         if (var2 > 1.0 && var14[var4 - 1] > 1.0) {
            var2 /= var14[var4 - 1];

            for (int var35 = 0; var35 < var4; var35++) {
               var14[var35] *= var2;
            }
         }

         JSONArray var51 = var1.optJSONArray("voice");
         JSONArray var29 = var51;
         if (var51 == null) {
            var29 = new JSONArray();
         }

         var48 = new ArrayList();
         ArrayList var67 = new ArrayList();
         var4 = 0;

         for (int var39 = var29.length(); var4 < var39; var4++) {
            JSONArray var59 = var29.optJSONArray(var4);
            if (var59 != null) {
               String var63 = var59.optString(1);
               Intrinsics.checkNotNull(var63);
               boolean var36;
               if (var63.length() > 0) {
                  var36 = true;
               } else {
                  var36 = false;
               }

               if (var36) {
                  var48.add(var59.optDouble(0));
                  var67.add(var63);
               }
            }
         }

         return new Nav.Line(var12, var15, var14, var43, var47, var46, var41, var72, CollectionsKt.toDoubleArray(var48), var67);
      }
   }

   public final Pair<List<Nav.Route>, List<Nav.Line>> parseRoutes(JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "j");
      ArrayList var13 = new ArrayList();
      JSONArray var12 = var1.optJSONArray("routes");
      JSONArray var11 = var12;
      if (var12 == null) {
         var11 = new JSONArray();
      }

      int var8 = 0;

      for (int var9 = var11.length(); var8 < var9; var8++) {
         JSONObject var14 = var11.optJSONObject(var8);
         if (var14 != null) {
            int var10 = var14.optInt("index", var8);
            double var6 = var14.optDouble("durationSeconds", 0.0);
            double var4 = var14.optDouble("distanceMeters", 0.0);
            double var2;
            if (var14.isNull("typicalSeconds")) {
               var2 = Double.NaN;
            } else {
               var2 = var14.optDouble("typicalSeconds", Double.NaN);
            }

            String var15 = var14.optString("via");
            Intrinsics.checkNotNullExpressionValue(var15, "optString(...)");
            JSONArray var16 = var14.optJSONArray("classes");
            List var24;
            if (var16 != null) {
               Iterable var17 = RangesKt.until(0, var16.length());
               var var23 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var17, 10));
               Iterator var27 = var17.iterator();

               while (var27.hasNext()) {
                  var23.add(var16.optString(((IntIterator)var27).nextInt()));
               }

               var24 = var23;
            } else {
               var24 = CollectionsKt.emptyList();
            }

            var13.add(new Nav.Route(var10, var6, var4, var2, var15, var24, var14.optInt("incidents", 0), var14.optBoolean("closed", false)));
         }
      }

      var12 = var1.optJSONArray("details");
      List var21;
      if (var12 != null) {
         Iterable var18 = RangesKt.until(0, var12.length());
         var var22 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var18, 10));
         Iterator var26 = var18.iterator();

         while (var26.hasNext()) {
            var1 = var12.optJSONObject(((IntIterator)var26).nextInt());
            Nav.Line var20;
            if (var1 != null) {
               var20 = INSTANCE.parseLine(var1);
            } else {
               var20 = null;
            }

            var22.add(var20);
         }

         var21 = var22;
      } else {
         var21 = CollectionsKt.emptyList();
      }

      return TuplesKt.to(var13, var21);
   }

   public final void poll(DataClient var1, boolean var2, long var3, boolean var5) {
      Intrinsics.checkNotNullParameter(var1, "data");
      if (var1.getConnected()) {
         boolean var6 = busy;
         long var7 = 10000L;
         if (!var6 || var3 - busySinceMs >= 10000L) {
            if (this.getActive() || var2) {
               var7 = 2000L;
            }

            if (!var5 && var3 - lastAskMs < var7) {
               return;
            }

            lastAskMs = var3;
            busy = true;
            busySinceMs = var3;
            HashMap var9 = new HashMap();
            String var10 = routeKey;
            if (var10 != null) {
               var9.put("routeKey", var10);
            }

            var10 = detailKey;
            if (var10 != null) {
               var9.put("detailKey", var10);
            }

            var1.request("nav_state", var9, new Nav$$ExternalSyntheticLambda0());
            return;
         }
      }
   }

   public final void refresh(DataClient var1) {
      Intrinsics.checkNotNullParameter(var1, "data");
      routeKey = null;
      lastAskMs = 0L;
      if (!busy) {
         this.poll(var1, true, System.currentTimeMillis(), true);
      }
   }

   public final Pair<Double, Double> remaining() {
      Nav.Route var4 = this.getRoute();
      if (var4 == null) {
         return null;
      } else {
         Nav.Line var5 = this.getLine();
         double var1 = along;
         if (var5 != null) {
            boolean var3;
            if (!Double.isInfinite(var1) && !Double.isNaN(var1)) {
               var3 = true;
            } else {
               var3 = false;
            }

            if (var3) {
               var1 = RangesKt.coerceAtLeast(var5.getLength() - var1, 0.0);
               return TuplesKt.to(var4.getDurationS() * (var1 / RangesKt.coerceAtLeast(var5.getLength(), 1.0)), var1);
            }
         }

         return TuplesKt.to(var4.getDurationS(), var4.getDistanceM());
      }
   }

   public final String routeAhead() {
      Nav.Line var7 = this.getLine();
      if (var7 == null) {
         return null;
      } else {
         Double var6 = along;
         double var1 = var6.doubleValue();
         boolean var3;
         if (!Double.isInfinite(var1) && !Double.isNaN(var1)) {
            var3 = 1;
         } else {
            var3 = 0;
         }

         if (!var3) {
            var6 = null;
         }

         if (var6 != null) {
            var1 = var6;
         } else {
            var1 = 0.0;
         }

         var3 = var7.indexAt(var1);
         int var5 = var7.indexAt(80000.0 + var1);
         if (var5 - var3 < 1) {
            return null;
         } else {
            int var4 = Math.max(1, (var5 - var3) / 150);

            for (var10 = new ArrayList(); var3 < var5; var3 += var4) {
               var10.add(TuplesKt.to(this.latOf(var7.getMy()[var3]), this.lonOf(var7.getMx()[var3])));
            }

            var10.add(TuplesKt.to(this.latOf(var7.getMy()[var5]), this.lonOf(var7.getMx()[var5])));
            return this.encodePolyline(var10);
         }
      }
   }

   public final void setLook(int var1) {
      look = var1;
   }

   public final void setOptions(DataClient var1, JSONObject var2, Function1<? super String, Unit> var3) {
      Intrinsics.checkNotNullParameter(var1, "data");
      Intrinsics.checkNotNullParameter(var2, "patch");
      var1.request("nav_options", MapsKt.mapOf(TuplesKt.to("options", var2)), new Nav$$ExternalSyntheticLambda8(var1, var3));
   }

   public final void startPreview(
      DataClient var1, Map<String, ? extends Object> var2, JSONObject var3, Map<String, ? extends Object> var4, Function1<? super String, Unit> var5
   ) {
      Intrinsics.checkNotNullParameter(var1, "data");
      Intrinsics.checkNotNullParameter(var2, "place");
      Intrinsics.checkNotNullParameter(var3, "shown");
      Intrinsics.checkNotNullParameter(var5, "then");
      previewSeq++;
      int var6 = previewSeq;
      previewing = var3;
      preview = null;
      look = -1;
      previewError = "";
      version++;
      HashMap var7 = new HashMap(var2);
      if (var4 != null) {
         var7.put("origin", new JSONObject(var4));
      }

      var1.request("nav_preview", var7, new Nav$$ExternalSyntheticLambda5(var6, var5, var3));
   }

   public final void track(double var1, double var3, float var5, boolean var6, boolean var7, String var8) {
      Intrinsics.checkNotNullParameter(var8, "mode");
      if (!Intrinsics.areEqual(status, lastStatus)) {
         if (var7 && Intrinsics.areEqual(var8, "all") && Intrinsics.areEqual(lastStatus, "guiding") && Intrinsics.areEqual(status, "routing") && var5 > 3.0F) {
            Speech.INSTANCE.speak("Rerouting", false);
         }

         lastStatus = status;
      }

      Nav.Line var18 = this.getLine();
      if (var18 != null) {
         boolean var13;
         if (!Double.isInfinite(var1) && !Double.isNaN(var1)) {
            var13 = 1;
         } else {
            var13 = 0;
         }

         if (var13) {
            if (!Double.isInfinite(var3) && !Double.isNaN(var3)) {
               var13 = (boolean)1;
            } else {
               var13 = (boolean)0;
            }

            if (var13) {
               Triple var19 = var18.locate(var1, var3, hint);
               var1 = ((Number)var19.component1()).doubleValue();
               var3 = ((Number)var19.component2()).doubleValue();
               int var14 = ((Number)var19.component3()).intValue();
               hint = var14;
               boolean var17;
               if (var3 > 60.0) {
                  var17 = true;
               } else {
                  var17 = false;
               }

               offRoute = var17;
               if (offRoute) {
                  var3 = Double.NaN;
               } else {
                  var3 = var1;
               }

               along = var3;
               if (var18 != saidLine) {
                  saidLine = var18;
                  lastAlong = Double.NaN;
                  if (!offRoute) {
                     var13 = 0;

                     for (int var15 = var18.getVoiceAt().length; var13 < var15; var13++) {
                        if (var18.getVoiceAt()[var13] < var1 - 30.0) {
                           said.add(this.key(var18.getVoiceText().get(var13), var18.getVoiceAt()[var13]));
                        }
                     }
                  }
               }

               if (var7 && !Intrinsics.areEqual(var8, "off") && !offRoute && !(var5 < 0.5F)) {
                  if (Intrinsics.areEqual(var8, "all")) {
                     var3 = var5;
                     int var42 = -1;
                     var13 = 0;
                     int var16 = var18.getVoiceAt().length;

                     while (var13 < var16) {
                        double var9 = var18.getVoiceAt()[var13];
                        var14 = var42;
                        if (!(var9 > var3 * 1.2 + var1)) {
                           if (var9 < var1 - 120.0) {
                              var14 = var42;
                           } else {
                              var14 = var42;
                              if (said.add(this.key(var18.getVoiceText().get(var13), var9))) {
                                 var14 = var13;
                              }
                           }
                        }

                        var13++;
                        var42 = var14;
                     }

                     if (var42 >= 0) {
                        Speech.INSTANCE.speak(var18.getVoiceText().get(var42), false);
                     }
                  }

                  lastAlong = var1;
                  if (var5 < 3.0F) {
                     return;
                  }

                  for (Nav.Incident var43 : var18.getIncidents()) {
                     double var31 = var43.getStartM() - var1;
                     boolean var35;
                     if (150.0 <= var31 && var31 <= 1700.0) {
                        var35 = true;
                     } else {
                        var35 = false;
                     }

                     if (var35) {
                        var8 = var43.getId();
                        if (var8.length() == 0) {
                           var35 = true;
                        } else {
                           var35 = false;
                        }

                        if (var35) {
                           var3 = var43.getLat();
                           double var11 = var43.getLon();
                           var8 = var3 + "," + var11;
                        }

                        var8 = var8;
                        if (announced.add(var8)) {
                           Speech var27 = Speech.INSTANCE;
                           String var44 = this.incidentWords(var43);
                           String var49 = Speech.INSTANCE.spoken(this.distanceText(var31, var6, true));
                           var27.speak(var44 + " ahead, in " + var49, false);
                           break;
                        }
                     }
                  }

                  var14 = this.faster();
                  Iterator var45 = routes.iterator();

                  do {
                     if (!var45.hasNext()) {
                        var28 = null;
                        break;
                     }

                     var28 = var45.next();
                     if (((Nav.Route)var28).getIndex() == var14) {
                        var37 = true;
                     } else {
                        var37 = false;
                     }
                  } while (!var37);

                  Nav.Route var29 = (Nav.Route)var28;
                  Nav.Route var50 = this.getRoute();
                  if (var29 != null && var50 != null) {
                     var14 = var29.getIndex();
                     var13 = MathKt.roundToInt(var29.getDurationS() / 60);
                     String var46 = var14 + "|" + var13;
                     if (!Intrinsics.areEqual(var46, fasterSaid)) {
                        fasterSaid = var46;
                        Speech var47 = Speech.INSTANCE;
                        var8 = StringsKt.replace$default(this.durationText(var50.getDurationS() - var29.getDurationS()), "min", "minutes", false, 4, null);
                        var47.speak("A faster route is available. It saves " + var8 + ".", false);
                     }
                  }

                  return;
               }

               if (offRoute) {
                  var1 = Double.NaN;
               }

               lastAlong = var1;
               return;
            }
         }
      }

      along = Double.NaN;
      hint = -1;
      lastAlong = Double.NaN;
   }

   public final String voiceMode(UiSettings var1) {
      Intrinsics.checkNotNullParameter(var1, "s");
      String var3 = "all";
      String var2 = var1.getStr("nav_voice", "all");
      String var4 = var3;
      if (SetsKt.setOf(new String[]{"all", "alerts", "off"}).contains(var2)) {
         var4 = var2;
      }

      return var4;
   }

   public static final class Incident {
      private final boolean closed;
      private final String description;
      private final double endM;
      private final String id;
      private final String impact;
      private final double lat;
      private final double lon;
      private final double startM;
      private final String subType;
      private final String type;

      public Incident(String var1, String var2, String var3, String var4, String var5, boolean var6, double var7, double var9, double var11, double var13) {
         Intrinsics.checkNotNullParameter(var1, "id");
         Intrinsics.checkNotNullParameter(var2, "type");
         Intrinsics.checkNotNullParameter(var3, "subType");
         Intrinsics.checkNotNullParameter(var4, "impact");
         Intrinsics.checkNotNullParameter(var5, "description");
         super();
         this.id = var1;
         this.type = var2;
         this.subType = var3;
         this.impact = var4;
         this.description = var5;
         this.closed = var6;
         this.startM = var7;
         this.endM = var9;
         this.lat = var11;
         this.lon = var13;
      }

      public final boolean getClosed() {
         return this.closed;
      }

      public final String getDescription() {
         return this.description;
      }

      public final double getEndM() {
         return this.endM;
      }

      public final String getId() {
         return this.id;
      }

      public final String getImpact() {
         return this.impact;
      }

      public final double getLat() {
         return this.lat;
      }

      public final double getLon() {
         return this.lon;
      }

      public final double getStartM() {
         return this.startM;
      }

      public final String getSubType() {
         return this.subType;
      }

      public final String getType() {
         return this.type;
      }
   }

   public static final class Lane {
      private final List<String> arrows;
      private final boolean ok;
      private final String use;

      public Lane(List<String> var1, boolean var2, String var3) {
         Intrinsics.checkNotNullParameter(var1, "arrows");
         Intrinsics.checkNotNullParameter(var3, "use");
         super();
         this.arrows = var1;
         this.ok = var2;
         this.use = var3;
      }

      public final List<String> getArrows() {
         return this.arrows;
      }

      public final boolean getOk() {
         return this.ok;
      }

      public final String getUse() {
         return this.use;
      }
   }

   public static final class Line {
      private final double[] cum;
      private final List<Nav.Incident> incidents;
      private final double[] mx;
      private final double[] my;
      private final List<Nav.Step> steps;
      private final double[] trafficFrom;
      private final int[] trafficLevel;
      private final double[] trafficTo;
      private final double[] voiceAt;
      private final List<String> voiceText;

      public Line(
         double[] var1,
         double[] var2,
         double[] var3,
         double[] var4,
         double[] var5,
         int[] var6,
         List<Nav.Incident> var7,
         List<Nav.Step> var8,
         double[] var9,
         List<String> var10
      ) {
         Intrinsics.checkNotNullParameter(var1, "mx");
         Intrinsics.checkNotNullParameter(var2, "my");
         Intrinsics.checkNotNullParameter(var3, "cum");
         Intrinsics.checkNotNullParameter(var4, "trafficFrom");
         Intrinsics.checkNotNullParameter(var5, "trafficTo");
         Intrinsics.checkNotNullParameter(var6, "trafficLevel");
         Intrinsics.checkNotNullParameter(var7, "incidents");
         Intrinsics.checkNotNullParameter(var8, "steps");
         Intrinsics.checkNotNullParameter(var9, "voiceAt");
         Intrinsics.checkNotNullParameter(var10, "voiceText");
         super();
         this.mx = var1;
         this.my = var2;
         this.cum = var3;
         this.trafficFrom = var4;
         this.trafficTo = var5;
         this.trafficLevel = var6;
         this.incidents = var7;
         this.steps = var8;
         this.voiceAt = var9;
         this.voiceText = var10;
      }

      // $VF: synthetic method
      public Line(
         double[] var1,
         double[] var2,
         double[] var3,
         double[] var4,
         double[] var5,
         int[] var6,
         List var7,
         List var8,
         double[] var9,
         List var10,
         int var11,
         DefaultConstructorMarker var12
      ) {
         if ((var11 & 256) != 0) {
            var9 = new double[0];
         }

         if ((var11 & 512) != 0) {
            var10 = CollectionsKt.emptyList();
         }

         this(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10);
      }

      // $VF: synthetic method
      public static Triple locate$default(Nav.Line var0, double var1, double var3, int var5, int var6, Object var7) {
         if ((var6 & 4) != 0) {
            var5 = -1;
         }

         return var0.locate(var1, var3, var5);
      }

      private static final Triple<Double, Double, Integer> locate$scan(Nav.Line var0, double var1, double var3, double var5, int var7, int var8) {
         double var13 = Double.MAX_VALUE;
         double var11 = 0.0;
         int var25 = var7;

         while (var7 < var8) {
            double var19 = var0.mx[var7];
            double var15 = var0.my[var7];
            double var21 = var0.mx[var7 + 1] - var19;
            double var17 = var0.my[var7 + 1] - var15;
            double var23 = var21 * var21 + var17 * var17;
            double var9 = 0.0;
            if (var23 > 0.0) {
               var9 = RangesKt.coerceIn(((var1 - var19) * var21 + (var3 - var15) * var17) / var23, 0.0, 1.0);
            }

            var19 = var19 + var9 * var21 - var1;
            var15 = var15 + var9 * var17 - var3;
            var17 = var19 * var19 + var15 * var15;
            var15 = var13;
            if (var17 < var13) {
               var13 = var0.cum[var7];
               var15 = var0.cum[var7 + 1];
               var11 = var0.cum[var7];
               var25 = var7;
               var11 = var13 + (var15 - var11) * var9;
               var15 = var17;
            }

            var7++;
            var13 = var15;
         }

         return new Triple<>(var11, Math.sqrt(var13) * var5, var25);
      }

      public final double[] getCum() {
         return this.cum;
      }

      public final List<Nav.Incident> getIncidents() {
         return this.incidents;
      }

      public final double getLength() {
         boolean var3;
         if (this.cum.length == 0) {
            var3 = true;
         } else {
            var3 = false;
         }

         double var1;
         if (var3) {
            var1 = 0.0;
         } else {
            var1 = this.cum[this.cum.length - 1];
         }

         return var1;
      }

      public final double[] getMx() {
         return this.mx;
      }

      public final double[] getMy() {
         return this.my;
      }

      public final int getSize() {
         return this.mx.length;
      }

      public final List<Nav.Step> getSteps() {
         return this.steps;
      }

      public final double[] getTrafficFrom() {
         return this.trafficFrom;
      }

      public final int[] getTrafficLevel() {
         return this.trafficLevel;
      }

      public final double[] getTrafficTo() {
         return this.trafficTo;
      }

      public final double[] getVoiceAt() {
         return this.voiceAt;
      }

      public final List<String> getVoiceText() {
         return this.voiceText;
      }

      public final int indexAt(double var1) {
         int var4 = 0;
         int var3 = this.cum.length - 1;

         while (var4 < var3) {
            int var5 = (var4 + var3) / 2;
            if (this.cum[var5] < var1) {
               var4 = var5 + 1;
            } else {
               var3 = var5;
            }
         }

         return var4;
      }

      public final int levelAt(double var1) {
         int var5 = 0;
         int var4 = this.trafficFrom.length - 1;
         int var3 = -1;

         while (var5 <= var4) {
            int var6 = (var5 + var4) / 2;
            if (this.trafficFrom[var6] <= var1) {
               var3 = var6;
               var5 = var6 + 1;
            } else {
               var4 = var6 - 1;
            }
         }

         if (var3 >= 0 && var1 <= this.trafficTo[var3]) {
            var3 = this.trafficLevel[var3];
         } else {
            var3 = 0;
         }

         return var3;
      }

      public final Triple<Double, Double, Integer> locate(double var1, double var3, int var5) {
         var3 = Nav.INSTANCE.mercX(var3);
         double var6 = Nav.INSTANCE.mercY(var1);
         var1 = Math.cos(Math.toRadians(var1)) * 4.0075016686E7;
         if (this.getSize() < 2) {
            return new Triple<>(0.0, Double.MAX_VALUE, 0);
         } else {
            if (var5 >= 0) {
               Triple var8 = locate$scan(this, var3, var6, var1, RangesKt.coerceAtLeast(var5 - 60, 0), RangesKt.coerceAtMost(var5 + 400, this.getSize() - 1));
               if (((Number)var8.getSecond()).doubleValue() < 60.0) {
                  return var8;
               }
            }

            return locate$scan(this, var3, var6, var1, 0, this.getSize() - 1);
         }
      }
   }

   public static final class Preview {
      private final JSONObject destination;
      private final String id;
      private final List<Nav.Line> lines;
      private final List<Nav.Route> routes;

      public Preview(String var1, JSONObject var2, List<Nav.Route> var3, List<Nav.Line> var4) {
         Intrinsics.checkNotNullParameter(var1, "id");
         Intrinsics.checkNotNullParameter(var2, "destination");
         Intrinsics.checkNotNullParameter(var3, "routes");
         Intrinsics.checkNotNullParameter(var4, "lines");
         super();
         this.id = var1;
         this.destination = var2;
         this.routes = var3;
         this.lines = var4;
      }

      public final JSONObject getDestination() {
         return this.destination;
      }

      public final String getId() {
         return this.id;
      }

      public final List<Nav.Line> getLines() {
         return this.lines;
      }

      public final List<Nav.Route> getRoutes() {
         return this.routes;
      }
   }

   public static final class Route {
      private final List<String> classes;
      private final boolean closed;
      private final double distanceM;
      private final double durationS;
      private final int incidents;
      private final int index;
      private final double typicalS;
      private final String via;

      public Route(int var1, double var2, double var4, double var6, String var8, List<String> var9, int var10, boolean var11) {
         Intrinsics.checkNotNullParameter(var8, "via");
         Intrinsics.checkNotNullParameter(var9, "classes");
         super();
         this.index = var1;
         this.durationS = var2;
         this.distanceM = var4;
         this.typicalS = var6;
         this.via = var8;
         this.classes = var9;
         this.incidents = var10;
         this.closed = var11;
      }

      public final List<String> getClasses() {
         return this.classes;
      }

      public final boolean getClosed() {
         return this.closed;
      }

      public final double getDistanceM() {
         return this.distanceM;
      }

      public final double getDurationS() {
         return this.durationS;
      }

      public final int getIncidents() {
         return this.incidents;
      }

      public final int getIndex() {
         return this.index;
      }

      public final double getTypicalS() {
         return this.typicalS;
      }

      public final String getVia() {
         return this.via;
      }
   }

   public static final class Step {
      private final double distanceM;
      private final double durationS;
      private final List<Nav.Lane> lanes;
      private final double lat;
      private final double lon;
      private final String modifier;
      private final String ref;
      private final String road;
      private final double startM;
      private final String text;
      private final String type;

      public Step(
         String var1,
         String var2,
         String var3,
         String var4,
         String var5,
         double var6,
         double var8,
         double var10,
         double var12,
         double var14,
         List<Nav.Lane> var16
      ) {
         Intrinsics.checkNotNullParameter(var1, "text");
         Intrinsics.checkNotNullParameter(var2, "type");
         Intrinsics.checkNotNullParameter(var3, "modifier");
         Intrinsics.checkNotNullParameter(var4, "road");
         Intrinsics.checkNotNullParameter(var5, "ref");
         Intrinsics.checkNotNullParameter(var16, "lanes");
         super();
         this.text = var1;
         this.type = var2;
         this.modifier = var3;
         this.road = var4;
         this.ref = var5;
         this.startM = var6;
         this.distanceM = var8;
         this.durationS = var10;
         this.lat = var12;
         this.lon = var14;
         this.lanes = var16;
      }

      // $VF: synthetic method
      public Step(
         String var1,
         String var2,
         String var3,
         String var4,
         String var5,
         double var6,
         double var8,
         double var10,
         double var12,
         double var14,
         List var16,
         int var17,
         DefaultConstructorMarker var18
      ) {
         if ((var17 & 1024) != 0) {
            var16 = CollectionsKt.emptyList();
         }

         this(var1, var2, var3, var4, var5, var6, var8, var10, var12, var14, var16);
      }

      public final double getDistanceM() {
         return this.distanceM;
      }

      public final double getDurationS() {
         return this.durationS;
      }

      public final List<Nav.Lane> getLanes() {
         return this.lanes;
      }

      public final double getLat() {
         return this.lat;
      }

      public final double getLon() {
         return this.lon;
      }

      public final String getModifier() {
         return this.modifier;
      }

      public final String getRef() {
         return this.ref;
      }

      public final String getRoad() {
         return this.road;
      }

      public final double getStartM() {
         return this.startM;
      }

      public final String getText() {
         return this.text;
      }

      public final String getType() {
         return this.type;
      }
   }
}
