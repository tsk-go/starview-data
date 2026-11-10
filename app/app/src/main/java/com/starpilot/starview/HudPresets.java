package com.starpilot.starview;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Result.Companion;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.json.JSONObject;

public final class HudPresets {
   private static final Map<String, Pair<Float, Float>> COCKPIT;
   private static final Set<String> FOCUS_KEYS = SetsKt.setOf(new String[]{"speedBox", "maxCard", "slc", "navCard", "laneSuggest", "gear", "nudgeHint", "dm"});
   public static final HudPresets INSTANCE = new HudPresets();

   static {
      Float var3 = 0.7F;
      Pair var1 = TuplesKt.to("navCard", TuplesKt.to(var3, 0.04F));
      Pair var2 = TuplesKt.to("slowCard", TuplesKt.to(var3, 0.34F));
      Pair var0 = TuplesKt.to("gpsCard", TuplesKt.to(var3, 0.52F));
      Pair var4 = TuplesKt.to("laneCard", TuplesKt.to(var3, var3));
      Float var5 = 0.02F;
      Float var6 = 0.03F;
      Pair var8 = TuplesKt.to("maxCard", TuplesKt.to(var5, var6));
      Pair var7 = TuplesKt.to("slc", TuplesKt.to(0.1F, var6));
      Pair var9 = TuplesKt.to("speedBox", TuplesKt.to(var5, 0.8F));
      COCKPIT = MapsKt.mapOf(new Pair[]{var1, var2, var0, var4, var8, var7, var9, TuplesKt.to("gauge", TuplesKt.to(var5, 0.22F))});
   }

   private HudPresets() {
   }

   private final SharedPreferences prefs(Context var1) {
      return var1.getSharedPreferences("starview_hud", 0);
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final String cockpit(String var1, List<String> var2) {
      Intrinsics.checkNotNullParameter(var2, "allKeys");

      label47:
      try {
         Companion var3 = Result.Companion;
         HudPresets var4 = this;
         JSONObject var16 = new JSONObject(var4.everything(var1, var2));
         var9 = KtCompat.resultOf(var16);
      } catch (Throwable var8) {
         Companion var13 = Result.Companion;
         var9 = KtCompat.resultOf(ResultKt.createFailure(var8));
         break label47;
      }

      JSONObject var17 = new JSONObject();
      Object var14 = var9;
      if (KtCompat.isFailure(var9)) {
         var14 = var17;
      }

      JSONObject var5 = (JSONObject)var14;

      for (Entry var10 : COCKPIT.entrySet()) {
         String var18 = (String)var10.getKey();
         Pair var6 = (Pair)var10.getValue();
         var14 = var5.optJSONObject(var18);
         JSONObject var11 = (JSONObject)var14;
         if (var14 == null) {
            var11 = new JSONObject();
            var5.put(var18, var11);
         }

         var11.put("on", true)
            .put("x", ((Number)var6.getFirst()).floatValue())
            .put("y", ((Number)var6.getSecond()).floatValue())
            .put("cw", JSONObject.NULL)
            .put("ch", JSONObject.NULL);
         if (!var11.has("s")) {
            var11.put("s", 1.0);
         }
      }

      var1 = var5.toString();
      Intrinsics.checkNotNullExpressionValue(var1, "toString(...)");
      return var1;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final void delete(Context var1, String var2) {
      String var3 = "{}";
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "name");

      label63: {
         JSONObject var5;
         String var17;
         try {
            Companion var4 = Result.Companion;
            HudPresets var16 = this;
            var17 = var16.prefs(var1).getString("presets", "{}");
         } catch (Throwable var11) {
            Companion var12 = Result.Companion;
            var3 = (String)KtCompat.resultOf(ResultKt.createFailure(var11));
            break label63;
         }

         if (var17 != null) {
            var3 = var17;
         }

         label54:
         try {
            var5 = new JSONObject(var3);
            var3 = (String)KtCompat.resultOf(var5);
         } catch (Throwable var10) {
            Companion var14 = Result.Companion;
            var3 = (String)KtCompat.resultOf(ResultKt.createFailure(var10));
            break label54;
         }
      }

      JSONObject var19 = new JSONObject();
      Object var18 = var3;
      if (KtCompat.isFailure(var3)) {
         var18 = var19;
      }

      JSONObject var15 = (JSONObject)var18;
      var15.remove(var2);
      this.prefs(var1).edit().putString("presets", var15.toString()).apply();
   }

   public final String everything(String var1, List<String> var2) {
      Intrinsics.checkNotNullParameter(var2, "allKeys");
      return this.only(var1, var2, CollectionsKt.toSet(var2));
   }

   public final boolean focusOn(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      boolean var2;
      if (this.prefs(var1).getString("before_focus", null) != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public final Map<String, Pair<Float, Float>> getCOCKPIT() {
      return COCKPIT;
   }

   public final Set<String> getFOCUS_KEYS() {
      return FOCUS_KEYS;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final String only(String var1, List<String> var2, Set<String> var3) {
      Intrinsics.checkNotNullParameter(var2, "allKeys");
      Intrinsics.checkNotNullParameter(var3, "keep");

      label87: {
         JSONObject var19;
         try {
            Companion var17 = Result.Companion;
            HudPresets var18 = this;
         } catch (Throwable var12) {
            Companion var4 = Result.Companion;
            var13 = KtCompat.resultOf(ResultKt.createFailure(var12));
            break label87;
         }

         if (var1 == null) {
            var1 = "{}";
         }

         label78:
         try {
            var19 = new JSONObject(var1);
            var13 = KtCompat.resultOf(var19);
         } catch (Throwable var11) {
            Companion var20 = Result.Companion;
            var13 = KtCompat.resultOf(ResultKt.createFailure(var11));
            break label78;
         }
      }

      JSONObject var5 = new JSONObject();
      JSONObject var21 = (JSONObject)var13;
      if (KtCompat.isFailure(var13)) {
         var21 = var5;
      }

      var21 = var21;

      for (String var6 : var2) {
         JSONObject var16 = var21.optJSONObject(var6);
         JSONObject var14 = var16;
         if (var16 == null) {
            var14 = new JSONObject().put("s", 1.0);
            var21.put(var6, var14);
         }

         var14.put("on", var3.contains(var6));
      }

      var1 = var21.toString();
      Intrinsics.checkNotNullExpressionValue(var1, "toString(...)");
      return var1;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final void save(Context var1, String var2, String var3) {
      String var4 = "{}";
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "name");
      Intrinsics.checkNotNullParameter(var3, "layout");

      label63: {
         JSONObject var6;
         String var17;
         try {
            Companion var15 = Result.Companion;
            HudPresets var16 = this;
            var17 = var16.prefs(var1).getString("presets", "{}");
         } catch (Throwable var12) {
            Companion var5 = Result.Companion;
            var4 = (String)KtCompat.resultOf(ResultKt.createFailure(var12));
            break label63;
         }

         if (var17 != null) {
            var4 = var17;
         }

         label54:
         try {
            var6 = new JSONObject(var4);
            var4 = (String)KtCompat.resultOf(var6);
         } catch (Throwable var11) {
            Companion var18 = Result.Companion;
            var4 = (String)KtCompat.resultOf(ResultKt.createFailure(var11));
            break label54;
         }
      }

      JSONObject var20 = new JSONObject();
      Object var19 = var4;
      if (KtCompat.isFailure(var4)) {
         var19 = var20;
      }

      JSONObject var14 = (JSONObject)var19;
      var14.put(var2, var3);
      this.prefs(var1).edit().putString("presets", var14.toString()).apply();
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public final Map<String, String> saved(Context var1) {
      String var2 = "{}";
      Intrinsics.checkNotNullParameter(var1, "ctx");

      label94: {
         JSONObject var20;
         try {
            Companion var3 = Result.Companion;
            HudPresets var4 = this;
            var12 = var4.prefs(var1).getString("presets", "{}");
         } catch (Throwable var10) {
            Companion var15 = Result.Companion;
            var11 = KtCompat.resultOf(ResultKt.createFailure(var10));
            break label94;
         }

         if (var12 == null) {
            var12 = var2;
         }

         label85:
         try {
            var20 = new JSONObject(var12);
            var11 = KtCompat.resultOf(var20);
         } catch (Throwable var9) {
            Companion var16 = Result.Companion;
            var11 = KtCompat.resultOf(ResultKt.createFailure(var9));
            break label85;
         }
      }

      var2 = (String)var11;
      if (KtCompat.isFailure(var11)) {
         var2 = null;
      }

      JSONObject var13 = (JSONObject)var2;
      Map var14;
      if (var13 != null) {
         Iterator var18 = var13.keys();
         Intrinsics.checkNotNullExpressionValue(var18, "keys(...)");
         Sequence var21 = SequencesKt.asSequence(var18);
         LinkedHashMap var19 = new LinkedHashMap();

         for (var var21_it : var21) {

            var21 = var21_it;
            var19.put(var21, var13.getString((String)var21));
         }

         var14 = var19;
      } else {
         var14 = MapsKt.emptyMap();
      }

      return var14;
   }

   public final boolean toggleFocus(Context var1, HudLayout var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "hud");
      String var4 = this.prefs(var1).getString("before_focus", null);
      boolean var3;
      if (var4 != null) {
         var2.restore(var4);
         var2.save();
         this.prefs(var1).edit().remove("before_focus").apply();
         var3 = false;
      } else {
         var4 = var2.snapshot();
         this.prefs(var1).edit().putString("before_focus", var4).apply();
         Iterable var5 = var2.getItems();
         var var6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var5, 10));
         Iterator var9 = var5.iterator();

         while (var9.hasNext()) {
            var6.add(((HudLayout.Item)var9.next()).getKey());
         }

         List var7 = var6;
         var2.restore(this.only(var4, var7, FOCUS_KEYS));
         var2.save();
         var3 = true;
      }

      return var3;
   }
}
