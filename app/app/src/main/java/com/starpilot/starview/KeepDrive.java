package com.starpilot.starview;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.Regex;
import kotlin.text.RegexOption;
import org.json.JSONArray;
import org.json.JSONObject;

public final class KeepDrive {
   public static final KeepDrive INSTANCE = new KeepDrive();

   // $VF: synthetic method
   public static Unit $r8$lambda$9yySkEo1lCwcrIpKz9c_6u_e2NI/* $VF was: $r8$lambda$9yySkEo1lCwcrIpKz9c_6u_e2NI*/(Context var0, JSONObject var1, String var2) {
      return keepNewest$lambda$3(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$e9veODMTXpDYXMVvK3JjR_voA1s(Context var0, String var1, JSONObject var2, String var3) {
      return applyPending$lambda$4(var0, var1, var2, var3);
   }

   private KeepDrive() {
   }

   private static final Unit applyPending$lambda$4(Context var0, String var1, JSONObject var2, String var3) {
      boolean var6 = false;
      boolean var4;
      if (var3 != null && new Regex("turn off the vehicle|active segment|refresh|sign in|timeout|connect", RegexOption.IGNORE_CASE).containsMatchIn(var3)) {
         var4 = true;
      } else {
         var4 = false;
      }

      boolean var5;
      label24: {
         if (var2 == null) {
            var5 = var6;
            if (var3 == null) {
               break label24;
            }

            var5 = var6;
            if (var4) {
               break label24;
            }
         }

         var5 = true;
      }

      if (var5) {
         KeepDrive var7 = INSTANCE;
         Intrinsics.checkNotNull(var0);
         Set var8 = var7.prefs(var0).getStringSet("pending", SetsKt.emptySet());
         Intrinsics.checkNotNull(var8);
         Set var9 = CollectionsKt.toMutableSet(var8);
         var9.remove(var1);
         INSTANCE.prefs(var0).edit().putStringSet("pending", var9).apply();
         if (var2 == null) {
            Toast.makeText(var0, "Couldn't keep the drive: " + var3, 1).show();
         }
      }

      return Unit.INSTANCE;
   }

   private static final Unit keepNewest$lambda$3(Context var0, JSONObject var1, String var2) {
      if (var1 != null) {
         JSONArray var9 = var1.optJSONArray("routes");
         if (var9 != null) {
            Iterable var10 = RangesKt.until(0, var9.length());
            var var12 = new ArrayList();
            Iterator var11 = var10.iterator();

            while (var11.hasNext()) {
               JSONObject var19 = var9.optJSONObject(((IntIterator)var11).nextInt());
               if (var19 != null) {
                  var12.add(var19);
               }
            }

            List var13 = var12;
            var11 = var13.iterator();
            Object var14;
            if (!var11.hasNext()) {
               var14 = null;
            } else {
               var14 = (KeepDrive)var11.next();
               if (var11.hasNext()) {
                  double var3 = ((JSONObject)var14).optDouble("startTime", 0.0);
                  Object var17 = var14;

                  do {
                     Object var20 = var11.next();
                     double var7 = ((JSONObject)var20).optDouble("startTime", 0.0);
                     var14 = (KeepDrive)var17;
                     double var5 = var3;
                     if (Double.compare(var3, var7) < 0) {
                        var14 = (KeepDrive)var20;
                        var5 = var7;
                     }

                     var17 = var14;
                     var3 = var5;
                  } while (var11.hasNext());
               }
            }

            JSONObject var18 = (JSONObject)var14;
            if (var18 != null) {
               if (var18.optBoolean("preserved")) {
                  keepNewest$toast(var0, "This drive is already kept");
                  return Unit.INSTANCE;
               }

               var14 = INSTANCE;
               Intrinsics.checkNotNull(var0);
               var2 = var18.optString("routeId");
               Intrinsics.checkNotNullExpressionValue(var2, "optString(...)");
               var14.request(var0, var2);
               keepNewest$toast(var0, "This drive will be kept (Galaxy saves it once you park)");
               INSTANCE.applyPending(var0);
               return Unit.INSTANCE;
            }
         }
      }

      if (var2 == null) {
         var2 = "No drive found";
      }

      keepNewest$toast(var0, var2);
      return Unit.INSTANCE;
   }

   private static final void keepNewest$toast(Context var0, String var1) {
      Toast.makeText(var0, var1, 1).show();
   }

   private final SharedPreferences prefs(Context var1) {
      return var1.getSharedPreferences("starview_keep", 0);
   }

   public final void applyPending(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Galaxy7 var2 = Galaxy7.Companion.current();
      if (var2 != null) {
         var1 = var1.getApplicationContext();
         Intrinsics.checkNotNull(var1);
         Set var3 = this.prefs(var1).getStringSet("pending", SetsKt.emptySet());
         Intrinsics.checkNotNull(var3);

         for (String var5 : CollectionsKt.toList(var3)) {
            JSONObject var7 = new JSONObject().put("action", "preserve").put("routeId", var5).put("preserved", true);
            Intrinsics.checkNotNullExpressionValue(var7, "put(...)");
            var2.post("/api/recordings/action", var7, new KeepDrive$$ExternalSyntheticLambda0(var1, var5));
         }
      }
   }

   public final void keepNewest(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      var1 = var1.getApplicationContext();
      Galaxy7 var2 = Galaxy7.Companion.current();
      if (var2 == null) {
         keepNewest$toast(var1, "Not connected to the comma");
      } else {
         var2.get("/api/recordings/local", new KeepDrive$$ExternalSyntheticLambda1(var1));
      }
   }

   public final void request(Context var1, String var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "routeId");
      Set var3 = this.prefs(var1).getStringSet("pending", SetsKt.emptySet());
      Intrinsics.checkNotNull(var3);
      var3 = CollectionsKt.toMutableSet(var3);
      var3.add(var2);
      this.prefs(var1).edit().putStringSet("pending", var3).apply();
   }
}
