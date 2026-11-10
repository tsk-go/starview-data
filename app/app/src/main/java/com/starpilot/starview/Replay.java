package com.starpilot.starview;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.json.JSONArray;
import org.json.JSONObject;

public final class Replay {
   public static final Replay INSTANCE = new Replay();
   private static volatile long gotAtNs;
   private static volatile JSONObject status;

   // $VF: synthetic method
   public static void $r8$lambda$BkRSlzaylFoUTzd9dcCfAvsxmjQ(DataClient var0, List var1, Activity var2, DialogInterface var3, int var4) {
      pick$lambda$4$lambda$3(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$DO4JBHUJx2OiIz93mWJRi3rAJaw(Activity var0, DataClient var1, JSONObject var2) {
      return pick$lambda$4(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$aRMq9O0QmNsi_okugC4lYcryBZc(List var0, int var1, Activity var2, JSONObject var3) {
      return pick$lambda$4$lambda$3$lambda$2(var0, var1, var2, var3);
   }

   private Replay() {
   }

   // $VF: synthetic method
   public static void control$default(Replay var0, String var1, double var2, Function1 var4, int var5, Object var6) {
      if ((var5 & 2) != 0) {
         var2 = 0.0;
      }

      if ((var5 & 4) != 0) {
         var4 = null;
      }

      var0.control(var1, var2, var4);
   }

   private static final Unit pick$lambda$4(Activity var0, DataClient var1, JSONObject var2) {
      Intrinsics.checkNotNullParameter(var2, "r");
      if (var0.isFinishing()) {
         return Unit.INSTANCE;
      } else if (!var2.optBoolean("ok")) {
         Builder var8 = new Builder((Context)var0);
         String var9 = var2.optString("error", "update its StarView patch");
         var8.setMessage("The comma can't replay drives: " + var9).setPositiveButton("OK", null).show();
         return Unit.INSTANCE;
      } else {
         JSONArray var5 = var2.optJSONArray("drives");
         int var3;
         if (var5 != null) {
            var3 = var5.length();
         } else {
            var3 = 0;
         }

         Iterable var10 = RangesKt.until(0, var3);
         var var4 = new ArrayList();
         Iterator var6 = var10.iterator();

         while (var6.hasNext()) {
            var3 = ((IntIterator)var6).nextInt();
            if (var5 != null) {
               var2 = var5.optJSONObject(var3);
            } else {
               var2 = null;
            }

            if (var2 != null) {
               var4.add(var2);
            }
         }

         List var14 = var4;
         if (var14.isEmpty()) {
            new Builder((Context)var0).setMessage("No recorded drives on the comma.").setPositiveButton("OK", null).show();
            return Unit.INSTANCE;
         } else {
            Builder var12 = new Builder((Context)var0).setTitle("Replay a drive");
            Iterable var18 = var14;
            var var15 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var18, 10));

            for (JSONObject var7 : var18) {
               var15.add(INSTANCE.label(var7));
            }

            List var16 = var15;
            Collection var17 = var16;
            var12.setItems(var17.toArray(new String[0]), new Replay$$ExternalSyntheticLambda2(var1, var14, var0)).setNegativeButton("Cancel", null).show();
            return Unit.INSTANCE;
         }
      }
   }

   private static final void pick$lambda$4$lambda$3(DataClient var0, List var1, Activity var2, DialogInterface var3, int var4) {
      var0.request(
         "replay_start",
         MapsKt.mapOf(new Pair[]{TuplesKt.to("route", ((JSONObject)var1.get(var4)).optString("route")), TuplesKt.to("at", 0)}),
         new Replay$$ExternalSyntheticLambda0(var1, var4, var2)
      );
   }

   private static final Unit pick$lambda$4$lambda$3$lambda$2(List var0, int var1, Activity var2, JSONObject var3) {
      Intrinsics.checkNotNullParameter(var3, "a");
      if (var3.optBoolean("ok")) {
         BlinkLog var7 = BlinkLog.INSTANCE;
         String var4 = INSTANCE.label((JSONObject)var0.get(var1));
         var7.add("Drive replay started: " + var4);
         var2.finish();
      } else {
         Builder var5 = new Builder((Context)var2);
         String var6 = var3.optString("error");
         var5.setMessage("Couldn't start it: " + var6).setPositiveButton("OK", null).show();
      }

      return Unit.INSTANCE;
   }

   // $VF: synthetic method
   public static double position$default(Replay var0, long var1, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = System.nanoTime();
      }

      return var0.position(var1);
   }

   public final String clock(double var1) {
      int var3 = RangesKt.coerceAtLeast((int)var1, 0);
      String var4;
      if (var3 >= 3600) {
         var4 = String.format("%d:%02d:%02d", Arrays.copyOf(new Object[]{var3 / 3600, var3 / 60 % 60, var3 % 60}, 3));
      } else {
         var4 = String.format("%d:%02d", Arrays.copyOf(new Object[]{var3 / 60, var3 % 60}, 2));
      }

      Intrinsics.checkNotNullExpressionValue(var4, "format(...)");
      return var4;
   }

   public final void control(String var1, double var2, Function1<? super JSONObject, Unit> var4) {
      Intrinsics.checkNotNullParameter(var1, "op");
      DataClient var5 = Link.INSTANCE.getData();
      if (var5 != null) {
         var5.request("replay_control", MapsKt.mapOf(new Pair[]{TuplesKt.to("op", var1), TuplesKt.to("value", var2)}), var4);
      }
   }

   public final boolean getActive() {
      JSONObject var3 = status;
      boolean var2 = false;
      boolean var1 = var2;
      if (var3 != null) {
         var1 = var2;
         if (var3.optBoolean("active", false)) {
            var1 = true;
         }
      }

      return var1;
   }

   public final JSONObject getStatus() {
      return status;
   }

   public final boolean getSupported() {
      boolean var1;
      if (status != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public final String label(JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "d");
      long var3 = var1.optLong("start", 0L);
      String var5;
      if (var3 > 0L) {
         var5 = new SimpleDateFormat("EEE MMM d · h:mm a", Locale.US).format(new Date(1000 * var3));
      } else {
         var5 = var1.optString("route");
      }

      int var2 = var1.optInt("minutes", var1.optInt("segments", 0));
      String var6 = "video";
      if (!var1.optBoolean("video")) {
         var6 = "no video";
      }

      String var7;
      if (!var1.optBoolean("full", true)) {
         var7 = "light log";
      } else {
         var7 = null;
      }

      String var8 = CollectionsKt.joinToString$default(CollectionsKt.listOfNotNull(new String[]{var6, var7}), " · ", null, null, 0, null, null, 62, null);
      return var5 + " · " + var2 + " min · " + var8;
   }

   public final void pick(Activity var1) {
      Intrinsics.checkNotNullParameter(var1, "act");
      DataClient var2 = Link.INSTANCE.getData();
      if (var2 != null && var2.getConnected()) {
         DataClient.request$default(var2, "replay_list", null, new Replay$$ExternalSyntheticLambda1(var1, var2), 2, null);
      } else {
         new Builder((Context)var1).setMessage("Not connected to the comma.").setPositiveButton("OK", null).show();
      }
   }

   public final double position(long var1) {
      JSONObject var7 = status;
      if (var7 == null) {
         return 0.0;
      } else {
         double var5 = var7.optDouble("t", 0.0);
         if (this.getActive() && !var7.optBoolean("paused") && !var7.optBoolean("loading")) {
            double var3 = RangesKt.coerceIn((var1 - gotAtNs) / 1.0E9, 0.0, 1.5) * var7.optDouble("speed", 1.0);
            return RangesKt.coerceAtMost(var5 + var3, var7.optDouble("dur", var5 + var3));
         } else {
            return var5;
         }
      }
   }

   public final void update(JSONObject var1) {
      status = var1;
      gotAtNs = System.nanoTime();
   }
}
