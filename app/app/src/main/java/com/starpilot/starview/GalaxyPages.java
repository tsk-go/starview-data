package com.starpilot.starview;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.json.JSONObject;

public final class GalaxyPages {
   public static final GalaxyPages INSTANCE = new GalaxyPages();
   private static final HashMap<String, GalaxyPages.Hit> cache = new HashMap<>();
   private static final Handler main = new Handler(Looper.getMainLooper());

   // $VF: synthetic method
   public static void $r8$lambda$DxUTVJpeLWCx4SxiHabz14kjiac(Function1 var0, GalaxyPages.Hit var1) {
      get$lambda$2$lambda$1(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$K0roXqrbSrXBiVz1DwDo0YJnquM(Function1 var0) {
      get$lambda$3(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$dFV9oaPABUvvEJDXBYWZwvurQQo(String var0, Function1 var1, JSONObject var2, String var3) {
      return get$lambda$4(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$hOtEOD4TipwSBqVbHPKfniiNGC4(String var0, int var1, ArrayList var2, Function1 var3, String var4, HashSet var5, JSONObject var6) {
      return crawl$visit$lambda$8(var0, var1, var2, var3, var4, var5, var6);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$vSTSdaErmx1FvTIDRXCrCWuUxGU(Function1 var0, JSONObject var1) {
      return summary$lambda$6(var0, var1);
   }

   private GalaxyPages() {
   }

   private static final void crawl$visit(
      HashSet<String> var0, ArrayList<SettingsIndex.Entry> var1, Function1<? super List<SettingsIndex.Entry>, Unit> var2, String var3, int var4, String var5
   ) {
      if (var0.add(var3)) {
         INSTANCE.get(var3, 60000L, new GalaxyPages$$ExternalSyntheticLambda1(var3, var4, var1, var2, var5, var0));
      }
   }

   private static final Unit crawl$visit$lambda$8(String var0, int var1, ArrayList var2, Function1 var3, String var4, HashSet var5, JSONObject var6) {
      if (var6 == null) {
         return Unit.INSTANCE;
      } else {
         CharSequence var10 = var6.optString("title");
         ArrayList var9 = var10;
         if (StringsKt.isBlank(var10)) {
            var9 = var4;
         }

         var9 = (String)var9;
         List var12 = GalaxyRow.Companion.visible(var0, GalaxyRow.Companion.parse(var6));
         ArrayList var11 = new ArrayList();
         boolean var8 = Intrinsics.areEqual(var0, "hub");
         String var23 = "|";
         String var19 = "gsub:";
         if (!var8) {
            for (GalaxyRow var15 : var12) {
               boolean var7;
               if (var15.getPage().length() == 0) {
                  var7 = true;
               } else {
                  var7 = false;
               }

               if (var7 && !StringsKt.isBlank(var15.getLabel()) && (var15.getControl() != GalaxyRow.Control.READOUT || !StringsKt.isBlank(var15.getValue()))) {
                  String var13 = var15.getLabel();
                  String var14 = var15.withUnit(GalaxyRow.shown$default(var15, null, 1, null));
                  Intrinsics.checkNotNull(var9);
                  var11.add(
                     new SettingsIndex.Entry(
                        var13,
                        var14,
                        (String)var9,
                        "gsub:" + var0 + "|" + var9,
                        var15.getReason(),
                        var15.getUnit(),
                        SettingIcons.INSTANCE.forRow(var0, var15.getLabel(), "", var15.getControl())
                     )
                  );
               }
            }
         }

         Iterator var26 = var12.iterator();
         var4 = (String)var9;
         var9 = var11;
         var0 = var23;

         while (var26.hasNext()) {
            GalaxyRow var24 = (GalaxyRow)var26.next();
            boolean var20;
            if (var24.getPage().length() > 0) {
               var20 = true;
            } else {
               var20 = false;
            }

            if (var20) {
               String var25 = var24.getLabel();
               Intrinsics.checkNotNull(var4);
               String var27 = var24.getPage();
               String var28 = var24.getLabel();
               var9.add(
                  new SettingsIndex.Entry(var25, "", var4, var19 + var27 + var0 + var28, var24.getValue(), "", SettingIcons.INSTANCE.forPage(var24.getPage()))
               );
               if (var1 < 3) {
                  crawl$visit(var5, var2, var3, var24.getPage(), var1 + 1, var24.getLabel());
               }
            }
         }

         var2.addAll(var9);
         var3.invoke(new ArrayList(var2));
         return Unit.INSTANCE;
      }
   }

   // $VF: synthetic method
   public static void get$default(GalaxyPages var0, String var1, long var2, Function1 var4, int var5, Object var6) {
      if ((var5 & 2) != 0) {
         var2 = 15000L;
      }

      var0.get(var1, var2, var4);
   }

   private static final void get$lambda$2$lambda$1(Function1 var0, GalaxyPages.Hit var1) {
      var0.invoke(var1.getPage());
   }

   private static final void get$lambda$3(Function1 var0) {
      var0.invoke(null);
   }

   private static final Unit get$lambda$4(String var0, Function1 var1, JSONObject var2, String var3) {
      if (var2 != null) {
         cache.put(var0, new GalaxyPages.Hit(System.currentTimeMillis(), var2));
      }

      var1.invoke(var2);
      return Unit.INSTANCE;
   }

   private static final Unit summary$lambda$6(Function1 var0, JSONObject var1) {
      SettingsIndex.Summary var5;
      if (var1 != null) {
         SettingsIndex var2 = SettingsIndex.INSTANCE;
         List var3 = GalaxyRow.Companion.parse(var1);
         String var4 = var1.optString("title");
         Intrinsics.checkNotNullExpressionValue(var4, "optString(...)");
         var5 = var2.summary(var3, var4);
      } else {
         var5 = null;
      }

      var0.invoke(var5);
      return Unit.INSTANCE;
   }

   public final void crawl(Function1<? super List<SettingsIndex.Entry>, Unit> var1) {
      Intrinsics.checkNotNullParameter(var1, "onEntries");
      ArrayList var2 = new ArrayList();
      crawl$visit(new HashSet<>(), var2, var1, "hub", 0, "Driving controls");
   }

   public final void get(String var1, long var2, Function1<? super JSONObject, Unit> var4) {
      Intrinsics.checkNotNullParameter(var1, "page");
      Intrinsics.checkNotNullParameter(var4, "done");
      GalaxyPages.Hit var6 = cache.get(var1);
      if (var6 != null) {
         boolean var5;
         if (System.currentTimeMillis() - var6.getAt() < var2) {
            var5 = true;
         } else {
            var5 = false;
         }

         if (!var5) {
            var6 = null;
         }

         if (var6 != null) {
            main.post(new GalaxyPages$$ExternalSyntheticLambda2(var4, var6));
            return;
         }
      }

      Galaxy7 var7 = Galaxy7.Companion.current();
      if (var7 == null) {
         main.post(new GalaxyPages$$ExternalSyntheticLambda3(var4));
      } else {
         String var8 = Uri.encode(var1);
         var7.get("/api/settings/pages/" + var8, new GalaxyPages$$ExternalSyntheticLambda4(var1, var4));
      }
   }

   public final void summary(String var1, Function1<? super SettingsIndex.Summary, Unit> var2) {
      Intrinsics.checkNotNullParameter(var1, "page");
      Intrinsics.checkNotNullParameter(var2, "done");
      get$default(this, var1, 0L, new GalaxyPages$$ExternalSyntheticLambda0(var2), 2, null);
   }

   private static final class Hit {
      private final long at;
      private final JSONObject page;

      public Hit(long var1, JSONObject var3) {
         Intrinsics.checkNotNullParameter(var3, "page");
         super();
         this.at = var1;
         this.page = var3;
      }

      public final long getAt() {
         return this.at;
      }

      public final JSONObject getPage() {
         return this.page;
      }
   }
}
