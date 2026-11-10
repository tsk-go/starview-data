package com.starpilot.starview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.json.JSONObject;

public final class GxModelLabPage extends GxPage {
   private LinearLayout availBox;
   private boolean cfgEnabled;
   private boolean chestnutReady;
   private boolean dirty;
   private JSONObject download;
   private String error;
   private int gen;
   private LinearLayout headBox;
   private boolean isOnroad;
   private String lastSig;
   private String lateral;
   private boolean loading;
   private String longitudinal;
   private String message;
   private List<? extends JSONObject> models;
   private final boolean needsOffroad;
   private LinearLayout pairBox;
   private boolean polling;
   private JSONObject runtime;
   private LinearLayout runtimeBox;
   private boolean saving;
   private JSONObject summary;

   // $VF: synthetic method
   public static Unit $r8$lambda$_vtaFmW9rqXql6cG6dhpWN8q8c0/* $VF was: $r8$lambda$_vtaFmW9rqXql6cG6dhpWN8q8c0*/(GxModelLabPage var0) {
      return renderPair$lambda$39(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$0GRiUci_qcCQL6aeXCCVaWGC_a8/* $VF was: $r8$lambda$0GRiUci_qcCQL6aeXCCVaWGC_a8*/(
      GxModelLabPage var0, String var1, JSONObject var2
   ) {
      return save$lambda$17(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$4vHA_n2hBLCFBQbpM9YBHSjN7Gs/* $VF was: $r8$lambda$4vHA_n2hBLCFBQbpM9YBHSjN7Gs*/(GxModelLabPage var0) {
      return renderPair$lambda$43(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$6u3YV3Nm34QXbBHJLXcRulAgax8(GxModelLabPage var0) {
      return build$lambda$1(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$C2Oi_PHYpDFiNxrZukTgnsBTnj0(GxModelLabPage var0, String var1) {
      return deleteModel$lambda$25(var0, var1);
   }

   // $VF: synthetic method
   public static CharSequence $r8$lambda$ElqQoZ_g5oZvdwk363sIS97pNIw(JSONObject var0) {
      return render$lambda$26(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$KV9f_BiHuLpHkrWbPU1MeKXa4bY/* $VF was: $r8$lambda$KV9f_BiHuLpHkrWbPU1MeKXa4bY*/(GxModelLabPage var0) {
      return build$lambda$1$lambda$0(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Km7QT8kZKZGuEGvq5T9YhL3t62M(GxModelLabPage var0, String var1) {
      return renderAvailable$lambda$33(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$KumQ5FvyQt1Fg_FYEVxr2vDxhe8(GxModelLabPage var0, int var1, Function0 var2, String var3, JSONObject var4) {
      return refresh$lambda$9(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$NfO_9_IGlm2iMHsW2JkE_VU0I_A/* $VF was: $r8$lambda$NfO_9_IGlm2iMHsW2JkE_VU0I_A*/(GxModelLabPage var0) {
      return prepareModel$lambda$20$lambda$19(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Y0lSYx2dylmQksr9ApNvROCuTB4(GxModelLabPage var0) {
      return renderPair$lambda$45(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$YgMozNqnFXxu2Qs4iYKdzN_Syp8(GxModelLabPage var0) {
      return renderPair$lambda$46(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$itaJSSZDG9HfqnBJnipx5Qa8Or8(GxModelLabPage var0, String var1, JSONObject var2) {
      return prepareModel$lambda$20(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$kZyo6CDGdjBtass4RocjiPcdnqM(GxModelLabPage var0, List var1, int var2) {
      return renderPair$lambda$43$lambda$42(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$lsv0SGhK_o3PFxnAIQeX7ibkZto/* $VF was: $r8$lambda$lsv0SGhK_o3PFxnAIQeX7ibkZto*/(
      GxModelLabPage var0, String var1, JSONObject var2
   ) {
      return deleteModel$lambda$25$lambda$24(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$mIhBfpOgmc7_uPjzEIwoPLqolfA(GxModelLabPage var0, String var1) {
      return renderAvailable$lambda$34(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$ppkjr3S4xZgaITOXTk3p48_4RMY(GxModelLabPage var0) {
      return renderPair$lambda$47(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$ywRl6M7JDj_jp5Os_L81vp1E9y0(GxModelLabPage var0, List var1, int var2) {
      return renderPair$lambda$39$lambda$38(var0, var1, var2);
   }

   public GxModelLabPage(Activity var1, GxHost var2) {
      Intrinsics.checkNotNullParameter(var1, "act");
      Intrinsics.checkNotNullParameter(var2, "gx");
      super(var1, var2);
      this.needsOffroad = true;
      this.loading = true;
      this.error = "";
      this.message = "";
      this.lateral = "";
      this.longitudinal = "";
      this.runtime = new JSONObject();
      this.download = new JSONObject();
      this.summary = new JSONObject();
      this.models = CollectionsKt.emptyList();
      this.lastSig = "";
   }

   private final void applyPayload(JSONObject var1) {
      this.chestnutReady = var1.optBoolean("chestnutReady");
      this.isOnroad = var1.optBoolean("isOnroad");
      this.error = GxModelsKt.gxModelsText$default(var1, "configurationError", null, 4, null);
      JSONObject var3 = var1.optJSONObject("runtime");
      JSONObject var2 = var3;
      if (var3 == null) {
         var2 = new JSONObject();
      }

      this.runtime = var2;
      var3 = var1.optJSONObject("download");
      var2 = var3;
      if (var3 == null) {
         var2 = new JSONObject();
      }

      this.download = var2;
      var3 = var1.optJSONObject("summary");
      var2 = var3;
      if (var3 == null) {
         var2 = new JSONObject();
      }

      this.summary = var2;
      this.models = GxModelsKt.access$gxModelsObjects(var1.optJSONArray("models"));
      var2 = var1.optJSONObject("configuration");
      var1 = var2;
      if (var2 == null) {
         var1 = new JSONObject();
      }

      this.cfgEnabled = var1.optBoolean("enabled");
      if (!this.dirty) {
         this.lateral = GxModelsKt.gxModelsText$default(var1, "lateralModel", null, 4, null);
         this.longitudinal = GxModelsKt.gxModelsText$default(var1, "longitudinalModel", null, 4, null);
      }

      this.normalizeSelection();
   }

   private final List<JSONObject> available() {
      Iterable var2 = this.models;
      var var1 = new ArrayList();

      for (var var2_it : var2) {

         var2 = var2_it;
         if (((JSONObject)var2).optBoolean("modelLabArtifactAvailable")) {
            var1.add(var2);
         }
      }

      return var1;
   }

   private static final Unit build$lambda$1(GxModelLabPage var0) {
      if (!var0.polling) {
         var0.polling = true;
         var0.refresh(new GxModelLabPage$$ExternalSyntheticLambda8(var0));
      }

      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$1$lambda$0(GxModelLabPage var0) {
      var0.polling = false;
      return Unit.INSTANCE;
   }

   private final JSONObject byId(String var1) {
      Iterator var4 = this.models.iterator();

      while (true) {
         boolean var2 = var4.hasNext();
         Object var3 = null;
         if (var2) {
            var3 = var4.next();
            if (!Intrinsics.areEqual(GxModelsKt.gxModelsText$default((JSONObject)var3, "value", null, 4, null), var1)) {
               continue;
            }

            var5 = var3;
            break;
         }

         var5 = var3;
         break;
      }

      return (JSONObject)var5;
   }

   private final List<JSONObject> candidates() {
      Iterable var5 = this.ready();
      var var4 = new ArrayList();

      for (Object var6 : var5) {
         JSONObject var7 = (JSONObject)var6;
         int var1 = this.lateral.length();
         boolean var3 = true;
         boolean var8;
         if (var1 == 0) {
            var8 = true;
         } else {
            var8 = false;
         }

         boolean var2 = var3;
         if (!var8) {
            if (!Intrinsics.areEqual(GxModelsKt.gxModelsText$default(var7, "value", null, 4, null), this.lateral)) {
               var2 = var3;
            } else {
               var2 = false;
            }
         }

         if (var2) {
            var4.add(var6);
         }
      }

      return var4;
   }

   private final void deleteModel(String var1) {
      if (!this.saving) {
         boolean var2;
         if (var1.length() == 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         if (!var2) {
            String var5;
            label24: {
               JSONObject var4 = this.byId(var1);
               if (var4 != null) {
                  CharSequence var3 = null;
                  CharSequence var6 = GxModelsKt.gxModelsText$default(var4, "label", null, 4, null);
                  if (!StringsKt.isBlank(var6)) {
                     var3 = var6;
                  }

                  String var7 = (String)var3;
                  var5 = var7;
                  if (var7 != null) {
                     break label24;
                  }
               }

               var5 = var1;
            }

            this.confirm(
               "Delete eGPU variant",
               "Delete the eGPU variant for \"" + var5 + "\"? The normal on-device model will not be removed.",
               "Delete",
               new GxModelLabPage$$ExternalSyntheticLambda5(this, var1)
            );
            return;
         }
      }
   }

   private static final Unit deleteModel$lambda$25(GxModelLabPage var0, String var1) {
      if (var0.saving) {
         return Unit.INSTANCE;
      } else {
         var0.saving = true;
         var0.error = "";
         var0.message = "";
         var0.render(true);
         var0.gen++;
         GxPage.send$default(
            var0,
            "DELETE",
            "/api/model-laboratory/artifact",
            new JSONObject().put("model", var1),
            0L,
            new GxModelLabPage$$ExternalSyntheticLambda6(var0),
            8,
            null
         );
         return Unit.INSTANCE;
      }
   }

   private static final Unit deleteModel$lambda$25$lambda$24(GxModelLabPage var0, String var1, JSONObject var2) {
      var0.saving = false;
      if (var1 != null) {
         var0.error = var1;
         var0.toast(var1);
      } else {
         var0.dirty = false;
         if (var2 != null) {
            var0.applyPayload(var2);
         }

         label23: {
            if (var2 != null) {
               var1 = var2.optString("message");
               if (var1 != null) {
                  CharSequence var6 = var1;
                  CharSequence var4 = var6;
                  if (StringsKt.isBlank(var6)) {
                     var4 = null;
                  }

                  String var7 = (String)var4;
                  var1 = var7;
                  if (var7 != null) {
                     break label23;
                  }
               }
            }

            var1 = "eGPU variant deleted.";
         }

         var0.message = var1;
         var0.toast("eGPU variant deleted");
      }

      var0.render(true);
      return Unit.INSTANCE;
   }

   private final String modelLabel(String var1) {
      JSONObject var3 = this.byId(var1);
      if (var3 != null) {
         CharSequence var2 = null;
         CharSequence var7 = GxModelsKt.gxModelsText$default(var3, "label", null, 4, null);
         if (!StringsKt.isBlank(var7)) {
            var2 = var7;
         }

         String var8 = (String)var2;
         if (var8 != null) {
            return var8;
         }
      }

      CharSequence var6 = var1;
      Object var4 = var6;
      if (StringsKt.isBlank(var6)) {
         var4 = "not selected";
      }

      return (String)var4;
   }

   private final void normalizeSelection() {
      List var7 = this.ready();
      var var4 = var7;
      boolean var3 = var4 instanceof Collection;
      boolean var2 = false;
      boolean var1;
      if (var3 && ((Collection)var4).isEmpty()) {
         var1 = true;
      } else {
         Iterator var9 = var4.iterator();

         while (true) {
            if (!var9.hasNext()) {
               var1 = true;
               break;
            }

            if (Intrinsics.areEqual(GxModelsKt.gxModelsText$default((JSONObject)var9.next(), "value", null, 4, null), this.lateral)) {
               var1 = false;
               break;
            }
         }
      }

      String var5 = "";
      if (var1) {
         label62: {
            JSONObject var10 = CollectionsKt.firstOrNull(var7);
            if (var10 != null) {
               String var6 = GxModelsKt.gxModelsText$default(var10, "value", null, 4, null);
               var11 = var6;
               if (var6 != null) {
                  break label62;
               }
            }

            var11 = "";
         }

         this.lateral = var11;
      }

      var4 = var7;
      if (var4 instanceof Collection && ((Collection)var4).isEmpty()) {
         var1 = true;
      } else {
         Iterator var13 = var4.iterator();

         while (true) {
            if (!var13.hasNext()) {
               var1 = true;
               break;
            }

            if (Intrinsics.areEqual(GxModelsKt.gxModelsText$default((JSONObject)var13.next(), "value", null, 4, null), this.longitudinal)) {
               var1 = var2;
               break;
            }
         }
      }

      if (var1) {
         Iterator var16 = var7.iterator();

         do {
            if (!var16.hasNext()) {
               var14 = null;
               break;
            }

            var14 = var16.next();
         } while (Intrinsics.areEqual(GxModelsKt.gxModelsText$default((JSONObject)var14, "value", null, 4, null), this.lateral));

         JSONObject var17 = (JSONObject)var14;
         String var15 = var5;
         if (var17 != null) {
            var15 = GxModelsKt.gxModelsText$default(var17, "value", null, 4, null);
            if (var15 == null) {
               var15 = var5;
            }
         }

         this.longitudinal = var15;
      }
   }

   private final void onLateralChange() {
      this.dirty = true;
      JSONObject var2 = this.byId(this.longitudinal);
      JSONObject var1 = this.byId(this.lateral);
      if (var2 != null
         && var1 != null
         && Intrinsics.areEqual(GxModelsKt.gxModelsText$default(var2, "value", null, 4, null), GxModelsKt.gxModelsText$default(var1, "value", null, 4, null))) {
         label15: {
            var1 = CollectionsKt.firstOrNull(this.candidates());
            if (var1 != null) {
               String var5 = GxModelsKt.gxModelsText$default(var1, "value", null, 4, null);
               var4 = var5;
               if (var5 != null) {
                  break label15;
               }
            }

            var4 = "";
         }

         this.longitudinal = var4;
      }
   }

   private final String pickLabel(JSONObject var1) {
      String var2 = GxModelsKt.gxModelsText$default(var1, "label", null, 4, null);
      String var3 = GxModelsKt.gxModelsText$default(var1, "version", null, 4, null);
      return var2 + " · " + var3;
   }

   private final void prepareModel(String var1) {
      if (!this.saving) {
         boolean var2;
         if (var1.length() == 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         if (!var2) {
            this.saving = true;
            this.error = "";
            this.message = "";
            this.render(true);
            GxPage.send$default(
               this,
               "POST",
               "/api/model-laboratory/download",
               new JSONObject().put("model", var1),
               0L,
               new GxModelLabPage$$ExternalSyntheticLambda0(this),
               8,
               null
            );
            return;
         }
      }
   }

   private static final Unit prepareModel$lambda$20(GxModelLabPage var0, String var1, JSONObject var2) {
      if (var1 != null) {
         var0.saving = false;
         var0.error = var1;
         var0.toast(var1);
         var0.render(true);
         return Unit.INSTANCE;
      } else {
         label20: {
            if (var2 != null) {
               var1 = var2.optString("message");
               if (var1 != null) {
                  CharSequence var6 = var1;
                  CharSequence var4 = var6;
                  if (StringsKt.isBlank(var6)) {
                     var4 = null;
                  }

                  String var7 = (String)var4;
                  var1 = var7;
                  if (var7 != null) {
                     break label20;
                  }
               }
            }

            var1 = "eGPU variant download queued.";
         }

         var0.message = var1;
         var0.toast("eGPU variant download queued");
         var0.refresh(new GxModelLabPage$$ExternalSyntheticLambda12(var0));
         return Unit.INSTANCE;
      }
   }

   private static final Unit prepareModel$lambda$20$lambda$19(GxModelLabPage var0) {
      var0.saving = false;
      var0.render(true);
      return Unit.INSTANCE;
   }

   private final List<JSONObject> ready() {
      Iterable var2 = this.available();
      var var1 = new ArrayList();

      for (var var2_it : var2) {

         var2 = var2_it;
         if (((JSONObject)var2).optBoolean("modelLabArtifactInstalled")) {
            var1.add(var2);
         }
      }

      return var1;
   }

   private final void refresh(Function0<Unit> var1) {
      this.gen++;
      this.getJson("/api/model-laboratory", new GxModelLabPage$$ExternalSyntheticLambda9(this, this.gen, var1));
   }

   // $VF: synthetic method
   static void refresh$default(GxModelLabPage var0, Function0 var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = null;
      }

      var0.refresh(var1);
   }

   private static final Unit refresh$lambda$9(GxModelLabPage var0, int var1, Function0 var2, String var3, JSONObject var4) {
      if (var0.getShown() && var1 == var0.gen) {
         if (var3 == null && var4 != null) {
            var0.applyPayload(var4);
         } else {
            if (var3 == null) {
               var3 = "bad reply";
            }

            var0.error = var3;
         }

         var0.loading = false;
         render$default(var0, false, 1, null);
         if (var2 != null) {
            var2.invoke();
         }

         return Unit.INSTANCE;
      } else {
         if (var2 != null) {
            var2.invoke();
         }

         return Unit.INSTANCE;
      }
   }

   private final void render(boolean var1) {
      if (this.runtimeBox != null) {
         String var2 = CollectionsKt.joinToString$default(
            CollectionsKt.listOf(
               new Object[]{
                  this.loading,
                  this.saving,
                  this.error,
                  this.message,
                  this.dirty,
                  this.chestnutReady,
                  this.isOnroad,
                  this.cfgEnabled,
                  this.lateral,
                  this.longitudinal,
                  this.runtime.toString(),
                  this.download.toString(),
                  this.summary.toString(),
                  CollectionsKt.joinToString$default(this.models, "|", null, null, 0, null, new GxModelLabPage$$ExternalSyntheticLambda7(), 30, null)
               }
            ),
            "\u0001",
            null,
            null,
            0,
            null,
            null,
            62,
            null
         );
         if (var1 || !Intrinsics.areEqual(var2, this.lastSig)) {
            this.lastSig = var2;
            this.renderHead();
            this.renderAvailable();
            this.renderPair();
            this.renderRuntime();
         }
      }
   }

   // $VF: synthetic method
   static void render$default(GxModelLabPage var0, boolean var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = false;
      }

      var0.render(var1);
   }

   private static final CharSequence render$lambda$26(JSONObject var0) {
      Intrinsics.checkNotNullParameter(var0, "it");
      String var1 = var0.toString();
      Intrinsics.checkNotNullExpressionValue(var1, "toString(...)");
      return var1;
   }

   private final void renderAvailable() {
      LinearLayout var7 = this.availBox;
      Object var6 = null;
      LinearLayout var5 = var7;
      if (var7 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("availBox");
         var5 = null;
      }

      var5.removeAllViews();
      if (!this.loading) {
         int var2 = this.summary.optInt("ready");
         int var1 = this.summary.optInt("published");
         boolean var4 = false;
         var1 = Math.max(var1 - var2, 0);
         String var8 = var2
            + " downloaded · "
            + var1
            + " available to download\nDownload eGPU-compatible small models. These are separate from the small models in Model Manager because they are compiled for the eGPU.";
         var7 = this.availBox;
         var5 = var7;
         if (var7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("availBox");
            var5 = null;
         }

         this.note(var8, (ViewGroup)var5);
         List var17 = this.available();
         if (var17.isEmpty()) {
            var5 = this.availBox;
            if (var5 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("availBox");
               var5 = (LinearLayout)var6;
            }

            var5.addView((View)this.empty("No eGPU variants are published in the model manifest."));
         } else {
            var6 = this.download;
            String var10 = GxModelsKt.gxModelsText$default((JSONObject)var6, "model", null, 4, null);
            boolean var3 = var4;
            if (!this.saving) {
               var3 = var4;
               if (!this.isOnroad) {
                  boolean var14;
                  if (var10.length() == 0) {
                     var14 = true;
                  } else {
                     var14 = false;
                  }

                  var3 = var4;
                  if (var14) {
                     var3 = true;
                  }
               }
            }

            for (JSONObject var12 : var17) {
               String var9 = GxModelsKt.gxModelsText$default(var12, "value", null, 4, null);
               var4 = var12.optBoolean("modelLabArtifactInstalled");
               var6 = GxModelsKt.gxModelsText$default(var12, "series", null, 4, null);
               TextView var18 = (TextView)var6;
               if (StringsKt.isBlank((CharSequence)var6)) {
                  var18 = "Unknown series";
               }

               CharSequence var32 = GxModelsKt.gxModelsText$default(var12, "version", null, 4, null);
               var6 = var32;
               if (StringsKt.isBlank(var32)) {
                  var6 = "unknown version";
               }

               CharSequence var36 = GxModelsKt.gxModelsText$default(var12, "modelSize", null, 4, null);
               var7 = var36;
               if (StringsKt.isBlank(var36)) {
                  var7 = "small";
               }

               if (var4) {
                  var8 = "eGPU variant downloaded";
               } else {
                  var8 = "eGPU variant not downloaded";
               }

               String var34 = var9 + " · " + var18 + "\n" + var6 + " · " + var7 + " · " + var8;
               GxPage var38 = this;
               var6 = GxModelsKt.gxModelsText$default(var12, "label", null, 4, null);
               var18 = var6;
               if (StringsKt.isBlank((CharSequence)var6)) {
                  var18 = var9;
               }

               String var39 = (String)var18;
               var6 = this.availBox;
               var18 = var6;
               if (var6 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("availBox");
                  var18 = null;
               }

               GxPage.Row var35 = GxPage.row$default(var38, var39, var34, (ViewGroup)var18, 0, 8, null);
               if (!var4) {
                  if (Intrinsics.areEqual(var10, var9)) {
                     var6 = GxModelsKt.gxModelsText$default(this.download, "progress", null, 4, null);
                     var18 = var6;
                     if (StringsKt.isBlank((CharSequence)var6)) {
                        var18 = "starting…";
                     }

                     var18 = "Downloading · " + var18;
                  } else {
                     var18 = "Download eGPU variant";
                  }

                  GxModelsKt.access$gxModelsEnable(
                     (View)GxPage.Row.button$default(var35, (String)var18, false, new GxModelLabPage$$ExternalSyntheticLambda2(this, var9), 2, null), var3
                  );
               } else {
                  var18 = GxPage.Row.button$default(var35, "Delete eGPU variant", false, new GxModelLabPage$$ExternalSyntheticLambda3(this, var9), 2, null);
                  var18.setTextColor(CarUi.Companion.getWARN());
                  GxModelsKt.access$gxModelsEnable((View)var18, var3);
               }
            }
         }
      }
   }

   private static final Unit renderAvailable$lambda$33(GxModelLabPage var0, String var1) {
      var0.prepareModel(var1);
      return Unit.INSTANCE;
   }

   private static final Unit renderAvailable$lambda$34(GxModelLabPage var0, String var1) {
      var0.deleteModel(var1);
      return Unit.INSTANCE;
   }

   private final void renderHead() {
      LinearLayout var6 = this.headBox;
      LinearLayout var5 = null;
      LinearLayout var4 = null;
      LinearLayout var3 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("headBox");
         var3 = null;
      }

      var3.removeAllViews();
      if (this.loading) {
         var3 = this.headBox;
         if (var3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("headBox");
            var3 = var4;
         }

         var3.addView((View)this.empty("Loading laboratory status..."));
      } else {
         var3 = this.headBox;
         if (var3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("headBox");
            var3 = var5;
         }

         var4 = this.card((ViewGroup)var3);
         this.text("Model Laboratory", (ViewGroup)var4, 21.0F, CarUi.Companion.getTEXT(), true);
         var5 = new LinearLayout((Context)this.getAct());
         boolean var2 = false;
         var5.setOrientation(0);
         var4.addView((View)var5);
         String var11;
         if (this.chestnutReady) {
            var11 = "Chestnut ready";
         } else {
            var11 = "Chestnut required";
         }

         ViewGroup var17 = (ViewGroup)var5;
         int var1;
         if (this.chestnutReady) {
            var1 = CarUi.Companion.getACCENT();
         } else {
            var1 = CarUi.Companion.getWARN();
         }

         this.text(var11, var17, 17.0F, var1, true).setPadding(0, this.getUi().dp(4), this.getUi().dp(24), this.getUi().dp(4));
         String var12;
         if (this.isOnroad) {
            var12 = "Onroad · locked";
         } else {
            var12 = "Parked · configurable";
         }

         ViewGroup var16 = (ViewGroup)var5;
         if (this.isOnroad) {
            var1 = CarUi.Companion.getWARN();
         } else {
            var1 = CarUi.Companion.getACCENT();
         }

         this.text(var12, var16, 17.0F, var1, true);
         GxPage.text$default(
            this, "Use the lateral judgment of one small model and the longitudinal judgment of another.", (ViewGroup)var4, 0.0F, 0, false, 28, null
         );
         boolean var8;
         if (this.error.length() > 0) {
            var8 = true;
         } else {
            var8 = false;
         }

         if (var8) {
            GxPage.text$default(this, this.error, (ViewGroup)var4, 17.0F, CarUi.Companion.getWARN(), false, 16, null);
         }

         var8 = var2;
         if (this.message.length() > 0) {
            var8 = true;
         }

         if (var8) {
            GxPage.text$default(this, this.message, (ViewGroup)var4, 17.0F, CarUi.Companion.getACCENT(), false, 16, null);
         }
      }
   }

   private final void renderPair() {
      LinearLayout var6 = this.pairBox;
      LinearLayout var5 = null;
      LinearLayout var4 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("pairBox");
         var4 = null;
      }

      var4.removeAllViews();
      if (!this.loading) {
         GxPage var7 = this;
         var6 = this.pairBox;
         var4 = var6;
         if (var6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pairBox");
            var4 = null;
         }

         GxPage.Row var34 = GxPage.row$default(
            var7, "Model Laboratory pair", "Choose from downloaded eGPU variant combinations below.", (ViewGroup)var4, 0, 8, null
         );
         String var17;
         if (this.cfgEnabled) {
            var17 = "Enabled";
         } else {
            var17 = "Disabled";
         }

         TextView var18 = var34.value(var17);
         int var1;
         if (this.cfgEnabled) {
            var1 = CarUi.Companion.getACCENT();
         } else {
            var1 = CarUi.Companion.getDIM();
         }

         var18.setTextColor(var1);
         GxPage var8 = this;
         var1 = this.lateral.length();
         boolean var3 = false;
         boolean var11;
         if (var1 == 0) {
            var11 = true;
         } else {
            var11 = false;
         }

         String var35 = "Choose a model";
         String var19;
         if (var11) {
            var19 = "Choose a model";
         } else {
            label94: {
               JSONObject var20 = this.byId(this.lateral);
               if (var20 != null) {
                  String var41 = this.pickLabel(var20);
                  var19 = var41;
                  if (var41 != null) {
                     break label94;
                  }
               }

               var19 = this.lateral;
            }
         }

         String var9 = "Path shape, curvature, lane geometry, and driving desire\nSelected: " + var19;
         LinearLayout var42 = this.pairBox;
         var4 = var42;
         if (var42 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pairBox");
            var4 = null;
         }

         GxPage.Row.button$default(
            GxPage.row$default(var8, "Lateral model", var9, (ViewGroup)var4, 0, 8, null),
            "Choose",
            false,
            new GxModelLabPage$$ExternalSyntheticLambda13(this),
            2,
            null
         );
         var7 = this;
         if (this.longitudinal.length() == 0) {
            var11 = true;
         } else {
            var11 = false;
         }

         String var22;
         if (var11) {
            var22 = var35;
         } else {
            label85: {
               JSONObject var23 = this.byId(this.longitudinal);
               if (var23 != null) {
                  String var36 = this.pickLabel(var23);
                  var22 = var36;
                  if (var36 != null) {
                     break label85;
                  }
               }

               var22 = this.longitudinal;
            }
         }

         String var46 = "Speed, acceleration, stopping, leads, and scene confidence\nSelected: " + var22;
         var6 = this.pairBox;
         var4 = var6;
         if (var6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pairBox");
            var4 = null;
         }

         GxPage.Row.button$default(
            GxPage.row$default(var7, "Longitudinal model", var46, (ViewGroup)var4, 0, 8, null),
            "Choose",
            false,
            new GxModelLabPage$$ExternalSyntheticLambda14(this),
            2,
            null
         );
         var4 = this.pairBox;
         if (var4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pairBox");
            var4 = var5;
         }

         var5 = this.card((ViewGroup)var4);
         TextView var38 = new TextView((Context)this.getAct());
         String var26 = this.modelLabel(this.lateral);
         String var44 = this.modelLabel(this.longitudinal);
         var38.setText(var26 + "  steers   ⇄   " + var44 + "  paces");
         var38.setTextSize(19.0F);
         var38.setTextColor(CarUi.Companion.getTEXT());
         var38.setTypeface(Typeface.DEFAULT_BOLD);
         var5.addView((View)var38);
         String var27 = this.selectionError();
         if (var27.length() > 0) {
            var11 = true;
         } else {
            var11 = false;
         }

         if (var11) {
            GxPage.text$default(this, var27, (ViewGroup)var5, 15.0F, CarUi.Companion.getWARN(), false, 16, null);
         }

         boolean var2;
         label76: {
            Pair var45 = TuplesKt.to("Enable for next drive", new GxModelLabPage$$ExternalSyntheticLambda15(this));
            Pair var39 = TuplesKt.to("Disable", new GxModelLabPage$$ExternalSyntheticLambda16(this));
            Pair var47 = TuplesKt.to("Refresh", new GxModelLabPage$$ExternalSyntheticLambda17(this));
            ViewGroup var31 = (ViewGroup)var5;
            var5 = this.buttons(new Pair[]{var45, var39, var47}, var31, true);
            var40 = var5.getChildAt(0);
            Intrinsics.checkNotNullExpressionValue(var40, "getChildAt(...)");
            if (!this.saving) {
               if (var27.length() == 0) {
                  var11 = true;
               } else {
                  var11 = false;
               }

               if (var11) {
                  var2 = true;
                  break label76;
               }
            }

            var2 = false;
         }

         GxModelsKt.access$gxModelsEnable(var40, var2);
         View var28 = var5.getChildAt(1);
         Intrinsics.checkNotNullExpressionValue(var28, "getChildAt(...)");
         var2 = var3;
         if (!this.saving) {
            var2 = var3;
            if (!this.isOnroad) {
               var2 = var3;
               if (this.cfgEnabled) {
                  var2 = true;
               }
            }
         }

         GxModelsKt.access$gxModelsEnable(var28, var2);
         View var29 = var5.getChildAt(2);
         Intrinsics.checkNotNullExpressionValue(var29, "getChildAt(...)");
         GxModelsKt.access$gxModelsEnable(var29, this.saving ^ true);
      }
   }

   private static final Unit renderPair$lambda$39(GxModelLabPage var0) {
      List var2 = var0.ready();
      Collection var3 = CollectionsKt.listOf("Choose a model");
      Iterable var1 = var2;
      var var4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var1, 10));

      for (JSONObject var7 : var1) {
         String var6 = var0.pickLabel(var7);
         String var8;
         if (Intrinsics.areEqual(GxModelsKt.gxModelsText$default(var7, "value", null, 4, null), var0.lateral)) {
            var8 = "  ✓";
         } else {
            var8 = "";
         }

         var4.add(var6 + var8);
      }

      List var9 = var4;
      var0.pick("Lateral model", CollectionsKt.plus(var3, var9), new GxModelLabPage$$ExternalSyntheticLambda1(var0, var2));
      return Unit.INSTANCE;
   }

   private static final Unit renderPair$lambda$39$lambda$38(GxModelLabPage var0, List var1, int var2) {
      String var3;
      if (var2 == 0) {
         var3 = "";
      } else {
         var3 = GxModelsKt.gxModelsText$default((JSONObject)var1.get(var2 - 1), "value", null, 4, null);
      }

      var0.lateral = var3;
      var0.onLateralChange();
      var0.render(true);
      return Unit.INSTANCE;
   }

   private static final Unit renderPair$lambda$43(GxModelLabPage var0) {
      List var2 = var0.candidates();
      Collection var3 = CollectionsKt.listOf("Choose a model");
      Iterable var1 = var2;
      var var4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var1, 10));

      for (JSONObject var7 : var1) {
         String var6 = var0.pickLabel(var7);
         String var8;
         if (Intrinsics.areEqual(GxModelsKt.gxModelsText$default(var7, "value", null, 4, null), var0.longitudinal)) {
            var8 = "  ✓";
         } else {
            var8 = "";
         }

         var4.add(var6 + var8);
      }

      List var9 = var4;
      var0.pick("Longitudinal model", CollectionsKt.plus(var3, var9), new GxModelLabPage$$ExternalSyntheticLambda10(var0, var2));
      return Unit.INSTANCE;
   }

   private static final Unit renderPair$lambda$43$lambda$42(GxModelLabPage var0, List var1, int var2) {
      String var3;
      if (var2 == 0) {
         var3 = "";
      } else {
         var3 = GxModelsKt.gxModelsText$default((JSONObject)var1.get(var2 - 1), "value", null, 4, null);
      }

      var0.longitudinal = var3;
      var0.dirty = true;
      var0.render(true);
      return Unit.INSTANCE;
   }

   private static final Unit renderPair$lambda$45(GxModelLabPage var0) {
      var0.save(true);
      return Unit.INSTANCE;
   }

   private static final Unit renderPair$lambda$46(GxModelLabPage var0) {
      var0.save(false);
      return Unit.INSTANCE;
   }

   private static final Unit renderPair$lambda$47(GxModelLabPage var0) {
      refresh$default(var0, null, 1, null);
      return Unit.INSTANCE;
   }

   private final void renderRuntime() {
      LinearLayout var3 = this.runtimeBox;
      Object var4 = null;
      LinearLayout var2 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("runtimeBox");
         var2 = null;
      }

      var2.removeAllViews();
      if (!this.loading) {
         GxPage var5 = this;
         var3 = this.runtimeBox;
         var2 = var3;
         if (var3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("runtimeBox");
            var2 = null;
         }

         TextView var7 = GxPage.row$default(var5, "State", null, (ViewGroup)var2, 0, 8, null).value(this.runtimeState());
         int var1;
         if (this.runtime.optBoolean("active")) {
            var1 = CarUi.Companion.getACCENT();
         } else {
            var1 = CarUi.Companion.getTEXT();
         }

         var7.setTextColor(var1);
         var5 = this;
         var3 = this.runtimeBox;
         var2 = var3;
         if (var3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("runtimeBox");
            var2 = null;
         }

         GxPage.row$default(var5, "Lateral", null, (ViewGroup)var2, 0, 8, null)
            .value(this.modelLabel(GxModelsKt.gxModelsText$default(this.runtime, "lateralModel", null, 4, null)));
         var5 = this;
         var3 = this.runtimeBox;
         var2 = var3;
         if (var3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("runtimeBox");
            var2 = null;
         }

         GxPage.row$default(var5, "Longitudinal", null, (ViewGroup)var2, 0, 8, null)
            .value(this.modelLabel(GxModelsKt.gxModelsText$default(this.runtime, "longitudinalModel", null, 4, null)));
         String var10 = GxModelsKt.gxModelsText$default(this.runtime, "error", null, 4, null);
         if (StringsKt.isBlank(var10)) {
            var10 = null;
         }

         if (var10 != null) {
            LinearLayout var19 = this.runtimeBox;
            var3 = var19;
            if (var19 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("runtimeBox");
               var3 = null;
            }

            var3 = this.card((ViewGroup)var3);
            GxPage.text$default(this, var10, (ViewGroup)var3, 17.0F, CarUi.Companion.getWARN(), false, 16, null);
         }

         var2 = this.runtimeBox;
         if (var2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("runtimeBox");
            var2 = (LinearLayout)var4;
         }

         this.note(
            "Both roles evaluate the same frame at 20 Hz. A runtime failure suppresses that frame and falls back to the built-in QCOM model.", (ViewGroup)var2
         );
      }
   }

   private final String runtimeState() {
      String var1;
      if (this.runtime.optBoolean("active")) {
         var1 = "Pair active";
      } else if (this.runtime.optBoolean("requested")) {
         var1 = "Pair requested";
      } else {
         var1 = "Inactive";
      }

      return var1;
   }

   private final void save(boolean var1) {
      if (!this.saving) {
         if (var1) {
            String var3 = this.selectionError();
            boolean var2;
            if (var3.length() > 0) {
               var2 = true;
            } else {
               var2 = false;
            }

            if (var2) {
               this.error = var3;
               this.render(true);
               return;
            }
         }

         this.saving = true;
         this.error = "";
         this.message = "";
         this.render(true);
         this.gen++;
         GxPage.send$default(
            this,
            "PUT",
            "/api/model-laboratory",
            new JSONObject().put("enabled", var1).put("lateralModel", this.lateral).put("longitudinalModel", this.longitudinal),
            0L,
            new GxModelLabPage$$ExternalSyntheticLambda4(this),
            8,
            null
         );
      }
   }

   private static final Unit save$lambda$17(GxModelLabPage var0, String var1, JSONObject var2) {
      var0.saving = false;
      if (var1 != null) {
         var0.error = var1;
         var0.toast(var1);
      } else {
         var0.dirty = false;
         if (var2 != null) {
            var0.applyPayload(var2);
         }

         label23: {
            if (var2 != null) {
               var1 = var2.optString("message");
               if (var1 != null) {
                  CharSequence var6 = var1;
                  CharSequence var4 = var6;
                  if (StringsKt.isBlank(var6)) {
                     var4 = null;
                  }

                  String var7 = (String)var4;
                  var1 = var7;
                  if (var7 != null) {
                     break label23;
                  }
               }
            }

            var1 = "Model Laboratory configuration saved.";
         }

         var0.message = var1;
         var0.toast("Model Laboratory saved");
      }

      var0.render(true);
      return Unit.INSTANCE;
   }

   private final String selectionError() {
      if (this.isOnroad) {
         return "Park before changing the laboratory pair.";
      } else if (this.ready().size() < 2) {
         return "Download at least two eGPU variants before composing a pair.";
      } else {
         JSONObject var1 = this.byId(this.lateral);
         JSONObject var2 = this.byId(this.longitudinal);
         if (var1 != null && var2 != null) {
            if (Intrinsics.areEqual(
               GxModelsKt.gxModelsText$default(var1, "value", null, 4, null), GxModelsKt.gxModelsText$default(var2, "value", null, 4, null)
            )) {
               return "Lateral and longitudinal models must be different.";
            } else if (!var1.optBoolean("modelLabArtifactInstalled") || !var2.optBoolean("modelLabArtifactInstalled")) {
               return "Download both eGPU variants first.";
            } else {
               return !this.chestnutReady ? "Connect a firmware-ready Chestnut to enable this pair." : "";
            }
         } else {
            return "Choose two downloaded eGPU variants.";
         }
      }
   }

   @Override
   public void build() {
      this.headBox = GxPage.box$default(this, null, 1, null);
      GxPage.header$default(this, "Available models", null, 2, null);
      this.availBox = GxPage.box$default(this, null, 1, null);
      GxPage.header$default(this, "Compose a pair", null, 2, null);
      this.pairBox = GxPage.box$default(this, null, 1, null);
      GxPage.header$default(this, "Runtime", null, 2, null);
      this.runtimeBox = GxPage.box$default(this, null, 1, null);
      this.render(true);
      this.every(2000L, new GxModelLabPage$$ExternalSyntheticLambda11(this));
   }

   @Override
   public boolean getNeedsOffroad() {
      return this.needsOffroad;
   }

   @Override
   public void onHide() {
      this.gen++;
      this.polling = false;
   }
}
