package com.starpilot.starview;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.WeakHashMap;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

public final class GalaxyExtras {
   public static final GalaxyExtras INSTANCE = new GalaxyExtras();
   private static final WeakHashMap<LinearLayout, Function0<Unit>> refreshers = new WeakHashMap<>();

   // $VF: synthetic method
   public static Unit $r8$lambda$BzNneRUg2C07uugD4AUngbBXJwY(
      boolean var0, List var1, String var2, Context var3, List var4, Galaxy7 var5, JSONObject var6, LinearLayout var7, CarUi var8, int var9
   ) {
      return cloudProvider$load$lambda$17$lambda$16(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Ca_BOz6GfV0luTkocjBGnaTpGaI(Context var0, JSONObject var1, String var2) {
      return soundPacks$draw$lambda$2$lambda$1(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$D9Fe96cbgH6klyMRp_scpcrAe5I/* $VF was: $r8$lambda$D9Fe96cbgH6klyMRp_scpcrAe5I*/(
      LinearLayout var0, CarUi var1, Handler var2, Galaxy7 var3, Context var4, JSONObject var5, String var6
   ) {
      return soundPacks$lambda$8$lambda$7(var0, var1, var2, var3, var4, var5, var6);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$FfaSfmUus_BWKKDMWNwZfDXIisA/* $VF was: $r8$lambda$FfaSfmUus_BWKKDMWNwZfDXIisA*/(
      JSONObject var0, Context var1, Galaxy7 var2, String var3
   ) {
      return soundPacks$draw$lambda$4(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$L8E9KFgMea5F9RAz9k5Y5giHeM0(Context var0, JSONObject var1, String var2) {
      return soundPacks$draw$lambda$4$lambda$3(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$NtObmHhOwoFBFDzCSXxcos_QMK8/* $VF was: $r8$lambda$NtObmHhOwoFBFDzCSXxcos_QMK8*/(
      LinearLayout var0, CarUi var1, Context var2, Galaxy7 var3, JSONObject var4, String var5
   ) {
      return cloudProvider$load$lambda$17(var0, var1, var2, var3, var4, var5);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$V_WKxGqx3iWFO7vk1SQumEVN_IE/* $VF was: $r8$lambda$V_WKxGqx3iWFO7vk1SQumEVN_IE*/(
      Galaxy7 var0, LinearLayout var1, CarUi var2, Handler var3, Context var4
   ) {
      return soundPacks$lambda$8(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static void $r8$lambda$ZqmdhsBUA5_En58qSGVyk83zZok/* $VF was: $r8$lambda$ZqmdhsBUA5_En58qSGVyk83zZok*/(
      Galaxy7 var0, List var1, int var2, JSONObject var3, Context var4, LinearLayout var5, CarUi var6, DialogInterface var7, int var8
   ) {
      cloudProvider$load$lambda$17$lambda$16$lambda$15(var0, var1, var2, var3, var4, var5, var6, var7, var8);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$a80fBaVJ2fKuNWfIAVYoz3_te2s(Context var0, Galaxy7 var1, LinearLayout var2, CarUi var3, JSONObject var4, String var5) {
      return cloudProvider$load$lambda$17$lambda$16$lambda$15$lambda$14(var0, var1, var2, var3, var4, var5);
   }

   // $VF: synthetic method
   public static void $r8$lambda$dCvFyMMoyFTQBZ2UTwgTFwoD_QA/* $VF was: $r8$lambda$dCvFyMMoyFTQBZ2UTwgTFwoD_QA*/() {
      soundPacks$draw$lambda$6();
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$qllumUFzeLlfSdEdK88Cez9zBVo(Galaxy7 var0, JSONObject var1, Context var2) {
      return soundPacks$draw$lambda$2(var0, var1, var2);
   }

   private GalaxyExtras() {
   }

   private static final void cloudProvider$load(Galaxy7 var0, LinearLayout var1, CarUi var2, Context var3) {
      var0.get("/api/connect/provider", new GalaxyExtras$$ExternalSyntheticLambda1(var1, var2, var3, var0));
   }

   private static final Unit cloudProvider$load$lambda$17(LinearLayout var0, CarUi var1, Context var2, Galaxy7 var3, JSONObject var4, String var5) {
      var0.removeAllViews();
      if (var4 == null) {
         String var15;
         if (var5 == null) {
            var15 = "Cloud provider is unavailable";
         } else {
            var15 = var5;
         }

         var0.addView((View)var1.note(var15));
         return Unit.INSTANCE;
      } else {
         JSONArray var8 = var4.optJSONArray("providers");
         JSONArray var18 = var8;
         if (var8 == null) {
            var18 = new JSONArray();
         }

         Iterable var9 = RangesKt.until(0, var18.length());
         var var21 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var9, 10));
         Iterator var24 = var9.iterator();

         while (var24.hasNext()) {
            var21.add(var18.getJSONObject(((IntIterator)var24).nextInt()).optString("id"));
         }

         List var25 = var21;
         Iterable var10 = RangesKt.until(0, var18.length());
         var var22 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var10, 10));
         Iterator var26 = var10.iterator();

         while (var26.hasNext()) {
            var22.add(var18.getJSONObject(((IntIterator)var26).nextInt()).optString("label"));
         }

         List var27 = var22;
         String var23 = var4.optString("selected");
         boolean var7 = var4.optBoolean("canSelect");
         LinearLayout var11 = new LinearLayout(var2);
         var11.setOrientation(1);
         var11.setBackground((Drawable)CarUi.rounded$default(var1, CarUi.Companion.getCARD(), 0, null, 6, null));
         var11.setPadding(var1.dp(20), var1.dp(14), var1.dp(18), var1.dp(14));
         if (var7) {
            var5 = "Applies after the next reboot (no automatic reboot).";
         } else {
            var5 = "Change it while parked with the car off.";
         }

         LinearLayout var20 = (LinearLayout)CarUi.row$default(var1, "Upload drives to", var5, 0, null, null, 28, null).component1();
         var20.setBackground(null);
         var20.setPadding(0, 0, 0, 0);
         var20.setMinimumHeight(0);
         var11.addView((View)var20);
         Pair var12 = var1.chips(var27, new GalaxyExtras$$ExternalSyntheticLambda2(var7, var25, var23, var2, var27, var3, var4, var0, var1));
         LinearLayout var16 = (LinearLayout)var12.component1();
         Function1 var13 = (Function1)var12.component2();
         var16.setPadding(0, var1.dp(10), 0, 0);
         var11.addView((View)var16);
         if (var13 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("paint");
            var13 = null;
         }

         var13.invoke(var25.indexOf(var23));
         float var6;
         if (var7) {
            var6 = 1.0F;
         } else {
            var6 = 0.6F;
         }

         var11.setAlpha(var6);
         var0.addView((View)var11);
         if (var4.optBoolean("restartRequired")) {
            String var17 = CollectionsKt.getOrNull(var27, var25.indexOf(var23));
            String var14 = var17;
            if (var17 == null) {
               var14 = var23;
            }

            var0.addView((View)var1.note(var14 + " applies after the next reboot."));
         }

         return Unit.INSTANCE;
      }
   }

   private static final Unit cloudProvider$load$lambda$17$lambda$16(
      boolean var0, List var1, String var2, Context var3, List var4, Galaxy7 var5, JSONObject var6, LinearLayout var7, CarUi var8, int var9
   ) {
      if (var0 && !Intrinsics.areEqual(var1.get(var9), var2)) {
         Builder var11 = new Builder(var3);
         Object var10 = var4.get(var9);
         Builder var12 = var11.setMessage("Use " + var10 + " after the next device reboot? Going back to comma restores its saved cloud identity.");
         Object var13 = var4.get(var9);
         var12.setPositiveButton("Use " + var13, new GalaxyExtras$$ExternalSyntheticLambda5(var5, var1, var9, var6, var3, var7, var8))
            .setNegativeButton("Cancel", null)
            .show();
         return Unit.INSTANCE;
      } else {
         return Unit.INSTANCE;
      }
   }

   private static final void cloudProvider$load$lambda$17$lambda$16$lambda$15(
      Galaxy7 var0, List var1, int var2, JSONObject var3, Context var4, LinearLayout var5, CarUi var6, DialogInterface var7, int var8
   ) {
      JSONObject var9 = new JSONObject().put("provider", var1.get(var2)).put("revision", var3.optString("revision")).put("confirmed", true);
      Intrinsics.checkNotNullExpressionValue(var9, "put(...)");
      var0.post("/api/connect/provider", var9, new GalaxyExtras$$ExternalSyntheticLambda9(var4, var0, var5, var6));
   }

   private static final Unit cloudProvider$load$lambda$17$lambda$16$lambda$15$lambda$14(
      Context var0, Galaxy7 var1, LinearLayout var2, CarUi var3, JSONObject var4, String var5
   ) {
      if (var5 != null) {
         INSTANCE.toast(var0, var5);
      }

      cloudProvider$load(var1, var2, var3, var0);
      return Unit.INSTANCE;
   }

   private final void refresh(LinearLayout var1) {
      WeakHashMap var2 = refreshers;
      if (var1 != null) {
         Function0 var3 = (Function0)var2.get(var1);
         if (var3 != null) {
            var3.invoke();
         }
      } else {
         Collection var4 = var2.values();
         Intrinsics.checkNotNullExpressionValue(var4, "<get-values>(...)");
         Iterator var5 = CollectionsKt.toList(var4).iterator();

         while (var5.hasNext()) {
            ((Function0)var5.next()).invoke();
         }
      }
   }

   // $VF: synthetic method
   static void refresh$default(GalaxyExtras var0, LinearLayout var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = null;
      }

      var0.refresh(var1);
   }

   private static final void soundPacks$draw(LinearLayout var0, CarUi var1, Handler var2, Galaxy7 var3, Context var4, JSONObject var5, String var6) {
      var0.removeAllViews();
      if (var5 == null) {
         if (var6 == null) {
            var6 = "Loading…";
         }

         var0.addView((View)var1.note(var6));
      } else {
         JSONObject var15 = var5.optJSONObject("job");
         boolean var8;
         if (var15 != null && SetsKt.setOf(new String[]{"downloading", "verifying"}).contains(var15.optString("state"))) {
            var8 = true;
         } else {
            var8 = false;
         }

         JSONArray var14 = var5.optJSONArray("packs");
         JSONArray var19 = var14;
         if (var14 == null) {
            var19 = new JSONArray();
         }

         int var9 = var19.length();
         int var10 = 0;

         for (JSONArray var25 = var19; var10 < var9; var10++) {
            boolean var11;
            boolean var13;
            String var16;
            JSONObject var17;
            label74: {
               var17 = var25.getJSONObject(var10);
               var16 = var17.optString("id");
               var13 = var17.optBoolean("installed");
               if (var8) {
                  if (var15 != null) {
                     var6 = var15.optString("pack");
                  } else {
                     var6 = null;
                  }

                  if (Intrinsics.areEqual(var6, var16)) {
                     var11 = true;
                     break label74;
                  }
               }

               var11 = false;
            }

            if (var11) {
               Intrinsics.checkNotNull(var15);
               var6 = var15.optString("state");
               int var12 = (int)(var15.optLong("bytes") * 100.0 / RangesKt.coerceAtLeast(var15.optLong("total"), 1L));
               var6 = var6 + "… " + var12 + " %";
            } else if (var13) {
               var6 = "Installed - choose it with Sound Pack above";
            } else {
               var6 = null;
            }

            String var27 = var17.optString("name", var16);
            Intrinsics.checkNotNullExpressionValue(var27, "optString(...)");
            LinearLayout var23 = (LinearLayout)CarUi.row$default(var1, var27, var6, 0, null, null, 28, null).component1();
            if (var11) {
               var23.addView((View)CarUi.button$default(var1, "Cancel", false, new GalaxyExtras$$ExternalSyntheticLambda6(var3, var15, var4), 2, null));
            } else if (!var13) {
               TextView var26 = CarUi.button$default(var1, "Install", false, new GalaxyExtras$$ExternalSyntheticLambda7(var5, var4, var3, var16), 2, null);
               float var7;
               if (var8) {
                  var7 = 0.45F;
               } else {
                  var7 = 1.0F;
               }

               var26.setAlpha(var7);
               if (!var8) {
                  var13 = true;
               } else {
                  var13 = false;
               }

               var26.setEnabled(var13);
               var23.addView((View)var26);
            }

            var0.addView((View)var23, (LayoutParams)CarUi.lp$default(var1, 0, 0, 3, null));
         }

         if (var15 != null && Intrinsics.areEqual(var15.optString("state"), "failed")) {
            String var18 = var15.optString("error");
            var0.addView((View)var1.note("Last download failed: " + var18));
         }

         if (var8 && var0.isAttachedToWindow()) {
            var2.postDelayed(new GalaxyExtras$$ExternalSyntheticLambda8(), 1000L);
         }
      }
   }

   private static final Unit soundPacks$draw$lambda$2(Galaxy7 var0, JSONObject var1, Context var2) {
      JSONObject var3 = new JSONObject();
      Intrinsics.checkNotNull(var1);
      var1 = var3.put("job", var1.optString("id"));
      Intrinsics.checkNotNullExpressionValue(var1, "put(...)");
      var0.post("/api/sounds/cancel", var1, new GalaxyExtras$$ExternalSyntheticLambda3(var2));
      return Unit.INSTANCE;
   }

   private static final Unit soundPacks$draw$lambda$2$lambda$1(Context var0, JSONObject var1, String var2) {
      if (var2 != null) {
         INSTANCE.toast(var0, var2);
      }

      refresh$default(INSTANCE, null, 1, null);
      return Unit.INSTANCE;
   }

   private static final Unit soundPacks$draw$lambda$4(JSONObject var0, Context var1, Galaxy7 var2, String var3) {
      if (!var0.optBoolean("parked")) {
         INSTANCE.toast(var1, "Sound packs are installed while parked");
         return Unit.INSTANCE;
      } else {
         var0 = new JSONObject().put("pack", var3);
         Intrinsics.checkNotNullExpressionValue(var0, "put(...)");
         var2.post("/api/sounds/download", var0, new GalaxyExtras$$ExternalSyntheticLambda4(var1));
         return Unit.INSTANCE;
      }
   }

   private static final Unit soundPacks$draw$lambda$4$lambda$3(Context var0, JSONObject var1, String var2) {
      if (var2 != null) {
         INSTANCE.toast(var0, var2);
      }

      refresh$default(INSTANCE, null, 1, null);
      return Unit.INSTANCE;
   }

   private static final void soundPacks$draw$lambda$6() {
      refresh$default(INSTANCE, null, 1, null);
   }

   private static final Unit soundPacks$lambda$8(Galaxy7 var0, LinearLayout var1, CarUi var2, Handler var3, Context var4) {
      var0.get("/api/sounds", new GalaxyExtras$$ExternalSyntheticLambda10(var1, var2, var3, var0, var4));
      return Unit.INSTANCE;
   }

   private static final Unit soundPacks$lambda$8$lambda$7(LinearLayout var0, CarUi var1, Handler var2, Galaxy7 var3, Context var4, JSONObject var5, String var6) {
      soundPacks$draw(var0, var1, var2, var3, var4, var5, var6);
      return Unit.INSTANCE;
   }

   private final void toast(Context var1, String var2) {
      Toast.makeText(var1, var2, 1).show();
   }

   public final void cloudProvider(Context var1, CarUi var2, LinearLayout var3) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "ui");
      Intrinsics.checkNotNullParameter(var3, "col");
      Galaxy7 var4 = Galaxy7.Companion.current();
      if (var4 != null) {
         LinearLayout var5 = new LinearLayout(var1);
         var5.setOrientation(1);
         var3.addView((View)var2.header("Cloud provider"));
         var3.addView((View)var5, (LayoutParams)CarUi.lp$default(var2, 0, 0, 3, null));
         cloudProvider$load(var4, var5, var2, var1);
      }
   }

   public final void soundPacks(Context var1, CarUi var2, LinearLayout var3) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "ui");
      Intrinsics.checkNotNullParameter(var3, "col");
      Galaxy7 var5 = Galaxy7.Companion.current();
      if (var5 != null) {
         LinearLayout var4 = new LinearLayout(var1);
         var4.setOrientation(1);
         var3.addView((View)var2.header("Sound packs"));
         var3.addView((View)var4, (LayoutParams)CarUi.lp$default(var2, 0, 0, 3, null));
         Handler var6 = new Handler(Looper.getMainLooper());
         refreshers.put(var4, new GalaxyExtras$$ExternalSyntheticLambda0(var5, var4, var2, var6, var1));
         this.refresh(var4);
      }
   }

   public static final class QuickSelect extends FrameLayout {
      public static final GalaxyExtras.QuickSelect.Companion Companion = new GalaxyExtras.QuickSelect.Companion(null);
      public static final String SET_SPEED = "__starpilot_controller_action__:set_speed";
      private final LinearLayout col;
      private JSONObject data;
      private final Galaxy7 g;
      private String last;
      private final Handler main;
      private final Runnable poll;
      private boolean saving;
      private final CarUi ui;
      private boolean visible;

      // $VF: synthetic method
      public static Unit $r8$lambda$_3m9KfRf_YlfPerhVzTEzM2qzl4/* $VF was: $r8$lambda$_3m9KfRf_YlfPerhVzTEzM2qzl4*/(
         boolean var0, GalaxyExtras.QuickSelect var1, int var2
      ) {
         return draw$lambda$10(var0, var1, var2);
      }

      // $VF: synthetic method
      public static void $r8$lambda$HS08s5eWkt_FTkbmXgeAayxjQ74/* $VF was: $r8$lambda$HS08s5eWkt_FTkbmXgeAayxjQ74*/(
         GalaxyExtras.QuickSelect var0, int var1, String var2, CompoundButton var3, boolean var4
      ) {
         draw$lambda$12(var0, var1, var2, var3, var4);
      }

      // $VF: synthetic method
      public static void $r8$lambda$KXUbiQ5ZoUVemt3PQDTK3hY7vvg(GalaxyExtras.QuickSelect var0, int var1, List var2, DialogInterface var3, int var4) {
         pick$lambda$19(var0, var1, var2, var3, var4);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$_AMNo6yfCDER0uc0_v7Zm3YKkEA(boolean var0, double var1, GalaxyExtras.QuickSelect var3, int var4) {
         return draw$lambda$14(var0, var1, var3, var4);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$ejnHLSd7Ko0ZqBPQjaatOUtcVt4(GalaxyExtras.QuickSelect var0, JSONObject var1, String var2) {
         return update$lambda$21(var0, var1, var2);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$uoFmu3MEzgnWyW7A9IyLVVF7cc4(GalaxyExtras.QuickSelect var0, JSONObject var1, String var2) {
         return load$lambda$3(var0, var1, var2);
      }

      // $VF: synthetic method
      public static Unit $r8$lambda$v_92MEX6jFWllNPAw5LAx9nRYM4(boolean var0, double var1, GalaxyExtras.QuickSelect var3, int var4) {
         return draw$lambda$15(var0, var1, var3, var4);
      }

      public QuickSelect(Context var1, CarUi var2) {
         Intrinsics.checkNotNullParameter(var1, "ctx");
         Intrinsics.checkNotNullParameter(var2, "ui");
         super(var1);
         this.ui = var2;
         this.g = Galaxy7.Companion.current();
         LinearLayout var4 = new LinearLayout(var1);
         var4.setOrientation(1);
         var4.setPadding(this.ui.dp(10), this.ui.dp(4), this.ui.dp(24), this.ui.dp(60));
         this.col = var4;
         this.last = "";
         this.main = new Handler(Looper.getMainLooper());
         this.poll = new Runnable() {

            @Override
            public void run() {
               if (GalaxyExtras.QuickSelect.this.visible) {
                  if (!GalaxyExtras.QuickSelect.this.saving) {
                     GalaxyExtras.QuickSelect.this.load();
                  }

                  GalaxyExtras.QuickSelect.this.main.postDelayed(this, 3000L);
               }
            }
         };
         ScrollView var3 = new ScrollView(var1);
         var3.addView((View)this.col);
         this.addView((View)var3, (LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -1)));
      }

      private final void draw() {
         JSONObject var9 = this.data;
         if (var9 != null) {
            JSONArray var6 = var9.optJSONArray("slots");
            JSONArray var7 = var9.optJSONArray("states");
            boolean var5 = var9.optBoolean("editable");
            String var18 = "" + var6 + var7 + var5;
            if (!Intrinsics.areEqual(var18, this.last)) {
               this.last = var18;
               this.col.removeAllViews();
               this.col
                  .addView(
                     (View)this.ui.note("Three buttons on the comma's driving screen (and wheel buttons set to Quick Select 1-3). Pick what each one does.")
                  );
               if (!var9.optBoolean("valid", true)) {
                  this.col.addView((View)this.ui.note("The saved Quick Select was damaged; saving fixes it."));
               }

               JSONArray var8 = var9.optJSONArray("slots");
               if (var8 != null) {
                  var7 = var9.optJSONArray("states");
                  var5 = var9.optBoolean("editable");
                  int var4 = var8.length();

                  for (int var3 = 0; var3 < var4; var3++) {
                     JSONObject var14 = var8.getJSONObject(var3);
                     JSONObject var11;
                     if (var7 != null) {
                        var11 = var7.optJSONObject(var3);
                     } else {
                        var11 = null;
                     }

                     this.col.addView((View)this.ui.header("Quick Select " + (var3 + 1)));
                     String var10;
                     if (var14.isNull("key")) {
                        var10 = null;
                     } else {
                        var10 = var14.optString("key");
                     }

                     Iterable var12 = this.options();
                     Iterator var15 = var12.iterator();

                     while (true) {
                        if (!var15.hasNext()) {
                           var29 = null;
                           break;
                        }

                        Object var13 = var15.next();
                        if (Intrinsics.areEqual(((JSONObject)var13).optString("key"), var10)) {
                           var29 = var13;
                           break;
                        }
                     }

                     CarUi var16;
                     label85: {
                        var42 = (JSONObject)var29;
                        var16 = this.ui;
                        if (var11 != null) {
                           String var30 = var11.optString("stateLabel");
                           if (var30 != null) {
                              CharSequence var36 = var30;
                              CharSequence var32 = var36;
                              if (StringsKt.isBlank(var36)) {
                                 var32 = null;
                              }

                              var31 = (String)var32;
                              break label85;
                           }
                        }

                        var31 = null;
                     }

                     label90: {
                        if (var11 != null) {
                           String var21 = var11.optString("reason");
                           if (var21 != null) {
                              CharSequence var37 = var21;
                              CharSequence var23 = var37;
                              if (StringsKt.isBlank(var37)) {
                                 var23 = null;
                              }

                              var22 = (String)var23;
                              break label90;
                           }
                        }

                        var22 = null;
                     }

                     CharSequence var33 = CollectionsKt.joinToString$default(
                        CollectionsKt.listOfNotNull(new String[]{var31, var22}), " · ", null, null, 0, null, null, 62, null
                     );
                     CharSequence var24 = var33;
                     if (StringsKt.isBlank(var33)) {
                        var24 = null;
                     }

                     LinearLayout var38;
                     label96: {
                        var38 = (LinearLayout)CarUi.row$default(var16, "Action", (String)var24, 0, null, null, 28, null).component1();
                        var16 = this.ui;
                        if (var42 != null) {
                           String var34 = var42.optString("label");
                           var25 = var34;
                           if (var34 != null) {
                              break label96;
                           }
                        }

                        var25 = "None";
                     }

                     TextView var26 = CarUi.button$default(
                        var16, var25, false, new GalaxyExtras$QuickSelect$$ExternalSyntheticLambda1(var5, this, var3), 2, null
                     );
                     var26.setMinWidth(this.ui.dp(220));
                     var38.addView((View)var26);
                     this.col.addView((View)var38, (LayoutParams)CarUi.lp$default(this.ui, 0, 0, 3, null));
                     if (var10 != null) {
                        for (Pair var39 : CollectionsKt.listOf(
                           new Pair[]{TuplesKt.to("enabled", "Use this button"), TuplesKt.to("show_onroad", "Show on the driving screen")}
                        )) {
                           String var35 = (String)var39.component1();
                           String var40 = (String)var39.component2();
                           LinearLayout var43 = (LinearLayout)CarUi.row$default(this.ui, var40, null, 0, null, null, 30, null).component1();
                           Switch var41 = this.ui.bigSwitch();
                           var41.setChecked(var14.optBoolean(var35));
                           var41.setEnabled(var5);
                           var41.setOnCheckedChangeListener(new GalaxyExtras$QuickSelect$$ExternalSyntheticLambda2(this, var3, var35));
                           var43.addView((View)var41);
                           this.col.addView((View)var43, (LayoutParams)CarUi.lp$default(this.ui, 0, 0, 3, null));
                        }

                        if (Intrinsics.areEqual(var10, "__starpilot_controller_action__:set_speed")) {
                           LinearLayout var20 = (LinearLayout)CarUi.row$default(this.ui, "Set speed", "5 to 145", 0, null, null, 28, null).component1();
                           double var1 = var14.optDouble("value", 50.0);
                           TextView var28 = new TextView(this.getContext());
                           var28.setText(GalaxyRow.Companion.fmt(var1));
                           var28.setTextSize(22.0F);
                           var28.setTextColor(CarUi.Companion.getTEXT());
                           var28.setMinWidth(this.ui.dp(70));
                           var28.setGravity(17);
                           var20.addView((View)this.ui.stepper("−", new GalaxyExtras$QuickSelect$$ExternalSyntheticLambda3(var5, var1, this, var3)));
                           var20.addView((View)var28);
                           var20.addView((View)this.ui.stepper("+", new GalaxyExtras$QuickSelect$$ExternalSyntheticLambda4(var5, var1, this, var3)));
                           this.col.addView((View)var20, (LayoutParams)CarUi.lp$default(this.ui, 0, 0, 3, null));
                        }
                     }
                  }

                  if (!var5) {
                     this.col.addView((View)this.ui.note("Quick Select can't be changed right now."));
                  }
               }
            }
         }
      }

      private static final Unit draw$lambda$10(boolean var0, GalaxyExtras.QuickSelect var1, int var2) {
         if (var0) {
            var1.pick(var2);
         }

         return Unit.INSTANCE;
      }

      private static final void draw$lambda$12(GalaxyExtras.QuickSelect var0, int var1, String var2, CompoundButton var3, boolean var4) {
         if (var0.saving) {
            var3.setChecked(var4 ^ true);
         } else {
            JSONObject var5 = new JSONObject().put(var2, var4);
            Intrinsics.checkNotNullExpressionValue(var5, "put(...)");
            var0.update(var1, var5);
         }
      }

      private static final Unit draw$lambda$14(boolean var0, double var1, GalaxyExtras.QuickSelect var3, int var4) {
         if (var0) {
            double var5 = 5;
            if (var1 - var5 >= 5.0) {
               JSONObject var7 = new JSONObject().put("value", var1 - var5);
               Intrinsics.checkNotNullExpressionValue(var7, "put(...)");
               var3.update(var4, var7);
            }
         }

         return Unit.INSTANCE;
      }

      private static final Unit draw$lambda$15(boolean var0, double var1, GalaxyExtras.QuickSelect var3, int var4) {
         if (var0) {
            double var5 = 5;
            if (var1 + var5 <= 145.0) {
               JSONObject var7 = new JSONObject().put("value", var5 + var1);
               Intrinsics.checkNotNullExpressionValue(var7, "put(...)");
               var3.update(var4, var7);
            }
         }

         return Unit.INSTANCE;
      }

      private final void load() {
         Galaxy7 var1 = this.g;
         if (var1 == null) {
            GalaxyExtras.QuickSelect var2 = this;
            var2.col.removeAllViews();
            var2.col.addView((View)var2.ui.note("Not connected to the comma."));
         } else {
            var1.get("/api/favorites/slots", new GalaxyExtras$QuickSelect$$ExternalSyntheticLambda0(this));
         }
      }

      private static final Unit load$lambda$3(GalaxyExtras.QuickSelect var0, JSONObject var1, String var2) {
         if (var1 != null) {
            var0.data = var1;
            var0.draw();
         } else if (var0.data == null) {
            var0.col.removeAllViews();
            LinearLayout var4 = var0.col;
            CarUi var3 = var0.ui;
            if (var2 == null) {
               var2 = "Galaxy didn't answer";
            }

            var4.addView((View)var3.note(var2));
         }

         return Unit.INSTANCE;
      }

      private final List<JSONObject> options() {
         JSONObject var1 = this.data;
         if (var1 != null) {
            JSONArray var4 = var1.optJSONArray("options");
            if (var4 != null) {
               Iterable var3 = RangesKt.until(0, var4.length());
               var var2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var3, 10));
               Iterator var6 = var3.iterator();

               while (var6.hasNext()) {
                  var2.add(var4.getJSONObject(((IntIterator)var6).nextInt()));
               }

               return var2;
            }
         }

         return CollectionsKt.emptyList();
      }

      private final void pick(int var1) {
         Iterable var3 = this.options();
         var var2 = new ArrayList();

         for (Object var4 : var3) {
            if (((JSONObject)var4).optBoolean("available", true)) {
               var2.add(var4);
            }
         }

         List var16 = var2;
         Collection var5 = CollectionsKt.listOf("None");
         Iterable var9 = var16;
         var var7 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var9, 10));

         for (JSONObject var13 : var9) {
            String var8 = var13.optString("label");
            String var14 = var13.optString("section");
            Intrinsics.checkNotNull(var14);
            String var15;
            if (!StringsKt.isBlank(var14)) {
               var15 = "  (" + var14 + ")";
            } else {
               var15 = "";
            }

            var7.add(var8 + var15);
         }

         List var10 = var7;
         List var11 = CollectionsKt.plus(var5, var10);
         new Builder(this.getContext())
            .setTitle("Quick Select " + (var1 + 1))
            .setItems(var11.toArray(new String[0]), new GalaxyExtras$QuickSelect$$ExternalSyntheticLambda5(this, var1, var16))
            .setNegativeButton("Cancel", null)
            .show();
      }

      private static final void pick$lambda$19(GalaxyExtras.QuickSelect var0, int var1, List var2, DialogInterface var3, int var4) {
         JSONObject var7 = new JSONObject();
         Object var5;
         if (var4 == 0) {
            var5 = JSONObject.NULL;
         } else {
            var5 = ((JSONObject)var2.get(var4 - 1)).optString("key");
         }

         var5 = var7.put("key", var5);
         Intrinsics.checkNotNullExpressionValue(var5, "put(...)");
         var0.update(var1, (JSONObject)var5);
      }

      private final void update(int var1, JSONObject var2) {
         JSONObject var10 = this.data;
         if (var10 != null) {
            Galaxy7 var9 = this.g;
            if (var9 != null) {
               if (var10.optBoolean("editable") && !this.saving) {
                  JSONArray var5 = var10.optJSONArray("slots");
                  if (var5 != null) {
                     JSONArray var11 = new JSONArray();
                     int var4 = 0;
                     int var3 = var5.length();

                     while (var4 < var3) {
                        JSONObject var12 = new JSONObject(var5.getJSONObject(var4).toString());
                        JSONArray var17;
                        if (var4 != var1) {
                           var17 = var5;
                        } else {
                           if (var2.has("key")) {
                              String var6;
                              if (var2.isNull("key")) {
                                 var6 = null;
                              } else {
                                 var6 = var2.optString("key");
                              }

                              Iterator var7 = this.options().iterator();

                              Object var8;
                              do {
                                 if (!var7.hasNext()) {
                                    var8 = null;
                                    break;
                                 }

                                 var8 = var7.next();
                              } while (!Intrinsics.areEqual(((JSONObject)var8).optString("key"), var6));

                              JSONArray var19 = var5;
                              JSONObject var13 = (JSONObject)var8;
                              if (var6 != null && var13 == null) {
                                 return;
                              }

                              label71: {
                                 var12.remove("value");
                                 if (var13 != null) {
                                    String var15 = var13.optString("label");
                                    if (var15 != null) {
                                       var8 = StringsKt.take(var15, 32);
                                       var16 = (String)var8;
                                       if (var8 != null) {
                                          break label71;
                                       }
                                    }
                                 }

                                 var16 = "";
                              }

                              var12.put("label", var16);
                              if (var13 == null) {
                                 var12.put("enabled", false);
                                 var12.put("show_onroad", false);
                              }

                              var5 = var5;
                              if (Intrinsics.areEqual(var6, "__starpilot_controller_action__:set_speed")) {
                                 var12.put("value", 50);
                                 var5 = var19;
                              }
                           }

                           Iterator var20 = var2.keys();
                           Intrinsics.checkNotNullExpressionValue(var20, "keys(...)");

                           while (true) {
                              var17 = var5;
                              if (!var20.hasNext()) {
                                 break;
                              }

                              String var18 = (String)var20.next();
                              var12.put(var18, var2.get(var18));
                           }
                        }

                        if (!Intrinsics.areEqual(var12.optString("key"), "__starpilot_controller_action__:set_speed")) {
                           var12.remove("value");
                        }

                        var11.put(var12);
                        var4++;
                        var5 = var17;
                     }

                     this.saving = true;
                     var2 = new JSONObject().put("revision", var10.optString("revision")).put("slots", var11);
                     Intrinsics.checkNotNullExpressionValue(var2, "put(...)");
                     var9.post("/api/favorites/slots", var2, new GalaxyExtras$QuickSelect$$ExternalSyntheticLambda6(this));
                  }
               }
            }
         }
      }

      private static final Unit update$lambda$21(GalaxyExtras.QuickSelect var0, JSONObject var1, String var2) {
         var0.saving = false;
         if (var1 != null && var1.has("slots")) {
            var0.data = var1;
            var0.last = "";
            var0.draw();
         } else {
            GalaxyExtras var3 = GalaxyExtras.INSTANCE;
            Context var4 = var0.getContext();
            Intrinsics.checkNotNullExpressionValue(var4, "getContext(...)");
            if (var2 == null) {
               var2 = "Not saved";
            }

            var3.toast(var4, var2);
            var0.last = "";
            var0.load();
         }

         return Unit.INSTANCE;
      }

      public final void hide() {
         this.visible = false;
         this.main.removeCallbacks(this.poll);
      }

      public final void show() {
         this.visible = true;
         this.load();
         this.main.removeCallbacks(this.poll);
         this.main.postDelayed(this.poll, 3000L);
      }

      public static final class Companion {
         private Companion() {
         }

         // $VF: synthetic method
         public Companion(DefaultConstructorMarker var1) {
            this();
         }
      }
   }
}
