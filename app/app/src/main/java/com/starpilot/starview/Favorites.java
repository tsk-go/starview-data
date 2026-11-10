package com.starpilot.starview;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Handler;
import android.os.Looper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

public final class Favorites {
   public static final Favorites INSTANCE = new Favorites();
   private static volatile List<? extends JSONObject> list = CollectionsKt.emptyList();
   private static final Lazy main$delegate = LazyKt.lazy(new Favorites$$ExternalSyntheticLambda1());
   private static volatile int version;

   // $VF: synthetic method
   public static Handler $r8$lambda$1YJx52WgZGVm5gjEyZ3DEPoEvdc() {
      return main_delegate$lambda$0();
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$1kMrCAqdc7dDJ_WJhimiA_igMrs/* $VF was: $r8$lambda$1kMrCAqdc7dDJ_WJhimiA_igMrs*/(Function1 var0, JSONObject var1) {
      return save$lambda$5(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$7tpYX1p87kxRa4B8GL_Aar82gcU/* $VF was: $r8$lambda$7tpYX1p87kxRa4B8GL_Aar82gcU*/(
      Address var0, Function1 var1, DataClient var2, String var3, String var4, String var5
   ) {
      saveFound$lambda$9$lambda$8(var0, var1, var2, var3, var4, var5);
   }

   // $VF: synthetic method
   public static void $r8$lambda$BlrW_Ta1_E3dJXRN7Qz2sUhWsZY/* $VF was: $r8$lambda$BlrW_Ta1_E3dJXRN7Qz2sUhWsZY*/(
      Context var0, String var1, String var2, Function1 var3, DataClient var4, String var5
   ) {
      saveFound$lambda$9(var0, var1, var2, var3, var4, var5);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$TzwzSsIWWJ0HWPFQiK_oF1nXXRQ(Function1 var0, JSONObject var1) {
      return label$lambda$11(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$pqCqTp_PUIvJW2DouUEgrROhsW0/* $VF was: $r8$lambda$pqCqTp_PUIvJW2DouUEgrROhsW0*/(Function0 var0, JSONObject var1) {
      return load$lambda$4(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$xS269JeNIQNO5D1aeYhEe59uflk(Context var0, JSONObject var1, Function1 var2, JSONObject var3) {
      return remove$lambda$10(var0, var1, var2, var3);
   }

   private Favorites() {
   }

   private final Handler getMain() {
      return (Handler)main$delegate.getValue();
   }

   private static final Unit label$lambda$11(Function1 var0, JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "r");
      String var3;
      if (var1.optBoolean("ok")) {
         INSTANCE.took(var1);
         var3 = null;
      } else {
         Favorites var2 = INSTANCE;
         String var4 = var1.optString("error");
         Intrinsics.checkNotNullExpressionValue(var4, "optString(...)");
         var3 = var2.unsupported(var4);
      }

      var0.invoke(var3);
      return Unit.INSTANCE;
   }

   // $VF: synthetic method
   public static void load$default(Favorites var0, DataClient var1, Function0 var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = null;
      }

      var0.load(var1, var2);
   }

   private static final Unit load$lambda$4(Function0 var0, JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "r");
      if (var1.optBoolean("ok")) {
         INSTANCE.took(var1);
      }

      if (var0 != null) {
         var0.invoke();
      }

      return Unit.INSTANCE;
   }

   private static final Handler main_delegate$lambda$0() {
      return new Handler(Looper.getMainLooper());
   }

   private static final Unit remove$lambda$10(Context var0, JSONObject var1, Function1 var2, JSONObject var3) {
      Intrinsics.checkNotNullParameter(var3, "r");
      String var4;
      if (var3.optBoolean("ok")) {
         INSTANCE.took(var3);
         INSTANCE.setPinned(new UiSettings(var0), var1, false);
         var4 = null;
      } else {
         Favorites var5 = INSTANCE;
         String var6 = var3.optString("error");
         Intrinsics.checkNotNullExpressionValue(var6, "optString(...)");
         var4 = var5.unsupported(var6);
      }

      var2.invoke(var4);
      return Unit.INSTANCE;
   }

   // $VF: synthetic method
   public static void save$default(
      Favorites var0, DataClient var1, String var2, String var3, double var4, double var6, String var8, Function1 var9, int var10, Object var11
   ) {
      if ((var10 & 32) != 0) {
         var8 = null;
      }

      var0.save(var1, var2, var3, var4, var6, var8, var9);
   }

   private static final Unit save$lambda$5(Function1 var0, JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "r");
      String var3;
      if (var1.optBoolean("ok")) {
         INSTANCE.took(var1);
         var3 = null;
      } else {
         Favorites var2 = INSTANCE;
         String var4 = var1.optString("error");
         Intrinsics.checkNotNullExpressionValue(var4, "optString(...)");
         var3 = var2.unsupported(var4);
      }

      var0.invoke(var3);
      return Unit.INSTANCE;
   }

   // $VF: synthetic method
   public static void saveFound$default(
      Favorites var0, Context var1, DataClient var2, String var3, String var4, String var5, Function1 var6, int var7, Object var8
   ) {
      if ((var7 & 16) != 0) {
         var5 = null;
      }

      var0.saveFound(var1, var2, var3, var4, var5, var6);
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private static final void saveFound$lambda$9(Context var0, String var1, String var2, Function1 var3, DataClient var4, String var5) {
      Object var7 = null;

      label75: {
         label74: {
            label79: {
               Geocoder var8;
               Locale var9;
               try {
                  if (!Geocoder.isPresent()) {
                     break label74;
                  }

                  var9 = Locale.getDefault();
               } catch (Exception var17) {
                  break label79;
               }

               Iterator var10;
               try {
                  var8 = new Geocoder(var0, var9);
                  Iterable var23 = CollectionsKt.listOf(new String[]{var1, var2});
                  ArrayList var18 = new ArrayList();
                  var19 = var18;
                  var10 = var23.iterator();
               } catch (Exception var15) {
                  break label79;
               }

               while (true) {
                  boolean var6;
                  label63: {
                     label62: {
                        try {
                           if (!var10.hasNext()) {
                              break;
                           }

                           var9 = (Locale)var10.next();
                           if (((String)var9).length() > 0) {
                              break label62;
                           }
                        } catch (Exception var16) {
                           break label79;
                        }

                        var6 = false;
                        break label63;
                     }

                     var6 = true;
                  }

                  if (var6) {
                     try {
                        var19.add(var9);
                     } catch (Exception var14) {
                        break label79;
                     }
                  }
               }

               try {
                  var20 = var19;
               } catch (Exception var13) {
                  break label79;
               }

               try {
                  var22 = var8.getFromLocationName(CollectionsKt.joinToString$default(var20, ", ", null, null, 0, null, null, 62, null), 1);
               } catch (Exception var12) {
                  break label79;
               }

               var21 = (Address)var7;
               if (var22 == null) {
                  break label75;
               }

               try {
                  var21 = CollectionsKt.firstOrNull(var22);
                  break label75;
               } catch (Exception var11) {
               }
            }

            var21 = null;
            break label75;
         }

         var21 = (Address)var7;
      }

      INSTANCE.getMain().post(new Favorites$$ExternalSyntheticLambda0(var21, var3, var4, var1, var2, var5));
   }

   private static final void saveFound$lambda$9$lambda$8(Address var0, Function1 var1, DataClient var2, String var3, String var4, String var5) {
      if (var0 != null && var0.hasLatitude()) {
         Favorites var8 = INSTANCE;
         var4 = var4;
         boolean var6;
         if (var4.length() == 0) {
            var6 = true;
         } else {
            var6 = false;
         }

         if (var6) {
            String var7 = var0.getAddressLine(0);
            var4 = var7;
            if (var7 == null) {
               var4 = "";
            }
         }

         var8.save(var2, var3, var4, var0.getLatitude(), var0.getLongitude(), var5, var1);
      } else {
         var1.invoke("Couldn't find that address to save it - search for the street address instead");
      }
   }

   private final void took(JSONObject var1) {
      JSONArray var5 = var1.optJSONArray("favorites");
      if (var5 != null) {
         Iterable var3 = RangesKt.until(0, var5.length());
         var var2 = new ArrayList();
         Iterator var7 = var3.iterator();

         while (var7.hasNext()) {
            JSONObject var4 = var5.optJSONObject(((IntIterator)var7).nextInt());
            if (var4 != null) {
               var2.add(var4);
            }
         }

         List var6 = var2;
         list = var6;
         version++;
      }
   }

   private final String unsupported(String var1) {
      CharSequence var3 = var1;
      CharSequence var4 = "unknown action";
      boolean var2 = false;
      if (StringsKt.contains$default(var3, var4, false, 2, null)) {
         var1 = "The comma needs the StarView navigation update (patch 10) for saved places";
      } else {
         var1 = var1;
         if (var1.length() == 0) {
            var2 = true;
         }

         if (var2) {
            var1 = "No answer from the comma";
         }

         var1 = var1;
      }

      return var1;
   }

   public final JSONObject find(double var1, double var3) {
      Iterator var7 = list.iterator();

      Object var5;
      JSONObject var6;
      do {
         if (!var7.hasNext()) {
            var5 = null;
            break;
         }

         var5 = var7.next();
         var6 = (JSONObject)var5;
      } while (!INSTANCE.same(var6, var1, var3));

      return (JSONObject)var5;
   }

   public final List<JSONObject> getList() {
      return list;
   }

   public final int getVersion() {
      return version;
   }

   public final String icon(JSONObject var1) {
      String var2;
      if (var1 != null) {
         var2 = var1.optString("label");
      } else {
         var2 = null;
      }

      String var3;
      if (Intrinsics.areEqual(var2, "home")) {
         var3 = "\ud83c\udfe0";
      } else if (Intrinsics.areEqual(var2, "work")) {
         var3 = "\ud83d\udcbc";
      } else {
         var3 = "★";
      }

      return var3;
   }

   public final void label(DataClient var1, JSONObject var2, String var3, Function1<? super String, Unit> var4) {
      Intrinsics.checkNotNullParameter(var2, "f");
      Intrinsics.checkNotNullParameter(var4, "then");
      if (var1 == null) {
         var4.invoke("Not connected to the comma");
      } else {
         var1.request(
            "nav_label",
            MapsKt.mapOf(new Pair[]{TuplesKt.to("id", var2.optString("id")), TuplesKt.to("label", var3)}),
            new Favorites$$ExternalSyntheticLambda4(var4)
         );
      }
   }

   public final void load(DataClient var1, Function0<Unit> var2) {
      if (var1 != null && var1.getConnected()) {
         DataClient.request$default(var1, "nav_list", null, new Favorites$$ExternalSyntheticLambda6(var2), 2, null);
      } else {
         if (var2 != null) {
            var2.invoke();
         }
      }
   }

   public final Pair<String, String> pinTitle(JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "p");
      JSONObject var3 = this.find(var1.optDouble("latitude"), var1.optDouble("longitude"));
      JSONObject var2 = var3;
      if (var3 == null) {
         var2 = var1;
      }

      return TuplesKt.to(this.icon(var2), this.title(var2));
   }

   public final boolean pinned(UiSettings var1, double var2, double var4) {
      Intrinsics.checkNotNullParameter(var1, "s");
      var var9 = this.pins(var1);
      boolean var7 = var9 instanceof Collection;
      boolean var6 = false;
      if (!var7 || !((Collection)var9).isEmpty()) {
         for (JSONObject var8 : var9) {
            if (INSTANCE.same(var8, var2, var4)) {
               var6 = true;
               break;
            }
         }
      }

      return var6;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public final List<JSONObject> pins(UiSettings var1) {
      Intrinsics.checkNotNullParameter(var1, "s");

      JSONArray var2;
      Iterator var12;
      try {
         var2 = new JSONArray(var1.getStr("fav_pins", "[]"));
         Iterable var3 = RangesKt.until(0, var2.length());
         ArrayList var10 = new ArrayList();
         var11 = var10;
         var12 = var3.iterator();
      } catch (Exception var7) {
         return CollectionsKt.emptyList();
      }

      while (true) {
         JSONObject var4;
         try {
            if (!var12.hasNext()) {
               break;
            }

            var4 = var2.optJSONObject(((IntIterator)var12).nextInt());
         } catch (Exception var8) {
            return CollectionsKt.emptyList();
         }

         if (var4 != null) {
            try {
               var11.add(var4);
            } catch (Exception var6) {
               return CollectionsKt.emptyList();
            }
         }
      }

      try {
         var9 = var11;
      } catch (Exception var5) {
         var9 = CollectionsKt.emptyList();
      }

      return var9;
   }

   public final void remove(Context var1, DataClient var2, JSONObject var3, Function1<? super String, Unit> var4) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var3, "f");
      Intrinsics.checkNotNullParameter(var4, "then");
      if (var2 == null) {
         var4.invoke("Not connected to the comma");
      } else {
         var2.request("nav_unfavorite", MapsKt.mapOf(TuplesKt.to("id", var3.optString("id"))), new Favorites$$ExternalSyntheticLambda5(var1, var3, var4));
      }
   }

   public final boolean same(JSONObject var1, double var2, double var4) {
      Intrinsics.checkNotNullParameter(var1, "a");
      boolean var6;
      if (Math.abs(var1.optDouble("latitude") - var2) < 2.0E-5 && Math.abs(var1.optDouble("longitude") - var4) < 2.0E-5) {
         var6 = true;
      } else {
         var6 = false;
      }

      return var6;
   }

   public final void save(DataClient var1, String var2, String var3, double var4, double var6, String var8, Function1<? super String, Unit> var9) {
      Intrinsics.checkNotNullParameter(var2, "name");
      Intrinsics.checkNotNullParameter(var3, "address");
      Intrinsics.checkNotNullParameter(var9, "then");
      if (var1 == null) {
         var9.invoke("Not connected to the comma");
      } else {
         HashMap var10 = new HashMap(
            MapsKt.mapOf(new Pair[]{TuplesKt.to("name", var2), TuplesKt.to("address", var3), TuplesKt.to("latitude", var4), TuplesKt.to("longitude", var6)})
         );
         if (var8 != null) {
            var10.put("label", var8);
         }

         var1.request("nav_favorite", var10, new Favorites$$ExternalSyntheticLambda2(var9));
      }
   }

   public final void saveFound(Context var1, DataClient var2, String var3, String var4, String var5, Function1<? super String, Unit> var6) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var3, "name");
      Intrinsics.checkNotNullParameter(var4, "address");
      Intrinsics.checkNotNullParameter(var6, "then");
      new Thread(new Favorites$$ExternalSyntheticLambda3(var1, var3, var4, var6, var2, var5)).start();
   }

   public final void setPinned(UiSettings var1, JSONObject var2, boolean var3) {
      Intrinsics.checkNotNullParameter(var1, "s");
      Intrinsics.checkNotNullParameter(var2, "f");
      double var6 = var2.optDouble("latitude");
      double var4 = var2.optDouble("longitude");
      Iterable var9 = this.pins(var1);
      boolean var8 = false;
      var var10 = new ArrayList();

      for (Object var12 : var9) {
         JSONObject var13 = (JSONObject)var12;
         if (!INSTANCE.same(var13, var6, var4)) {
            var10.add(var12);
         }
      }

      List var16 = var10;
      JSONArray var15 = new JSONArray();
      Iterator var17 = var16.iterator();

      while (var17.hasNext()) {
         var15.put((JSONObject)var17.next());
      }

      if (var3) {
         var15.put(
            new JSONObject()
               .put("name", var2.optString("name"))
               .put("address", var2.optString("address"))
               .put("latitude", var6)
               .put("longitude", var4)
               .put("label", var2.optString("label"))
         );
      }

      String var14 = var15.toString();
      Intrinsics.checkNotNullExpressionValue(var14, "toString(...)");
      var1.setStr("fav_pins", var14);
      version++;
   }

   public final String title(JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "f");
      String var2 = var1.optString("label");
      String var3;
      if (Intrinsics.areEqual(var2, "home")) {
         var3 = "Home";
      } else if (Intrinsics.areEqual(var2, "work")) {
         var3 = "Work";
      } else {
         var3 = var1.optString("name");
      }

      return var3;
   }
}
