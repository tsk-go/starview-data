package com.starpilot.starview;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

public final class GxVehiclePage extends GxPage {
   private String checking;
   private int curTab;
   private String doorBusy;
   private final HashMap<String, TextView> featureValues;
   private final List<GxVehiclePage.Feature> features;
   private final HashMap<String, GxVehiclePage.Gate> gates;
   private LinearLayout keyBox;
   private List<? extends JSONObject> keys;
   private boolean keysLoaded;
   private TextView lockBtn;
   private Function1<? super Integer, Unit> selectTab;
   private final HashMap<String, String> status;
   private TextView unlockBtn;

   // $VF: synthetic method
   public static Unit $r8$lambda$2IfUNI2Cl3pl2JggRCptkAaB_4M/* $VF was: $r8$lambda$2IfUNI2Cl3pl2JggRCptkAaB_4M*/(int var0) {
      return selectTab$lambda$0(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$3EogI2Cyq5EhOA24LP_Lvh5RC_0/* $VF was: $r8$lambda$3EogI2Cyq5EhOA24LP_Lvh5RC_0*/(GxVehiclePage var0, JSONObject var1) {
      return paintKeys$lambda$18(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$5zlxx2Btud_IKOEDs7Mvu4x1Qk4(GxVehiclePage var0, String var1, Object var2) {
      return editKey$lambda$28$lambda$27(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$95RpwbdnmGLg4Sw7giofdiSlB2A(GxVehiclePage var0, JSONObject var1, String var2, String var3) {
      return paintKeys$lambda$19(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$AXXH7zmzNtJIwBbhXNjeroOm2p8(GxVehiclePage var0) {
      return buildDoors$lambda$8(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$FAEgf6Cg5CPF6buB37BXo_SfKhg/* $VF was: $r8$lambda$FAEgf6Cg5CPF6buB37BXo_SfKhg*/(GxVehiclePage var0, int var1) {
      return build$lambda$1(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$OO4E05Vkn5rXize8n01FOrGdPF0(GxVehiclePage var0, String var1, JSONArray var2) {
      return loadKeys$lambda$16(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$OzrECthnW1Oxlisk88j5dqIVpCE(GxVehiclePage var0, GxVehiclePage.Feature var1, boolean var2) {
      return gateFor$lambda$7(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$QioES_6P__D3gaqrPZI2mqqdsJE/* $VF was: $r8$lambda$QioES_6P__D3gaqrPZI2mqqdsJE*/(GxVehiclePage var0, GxVehiclePage.Feature var1) {
      return buildFeatures$lambda$3(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$_AZwHDe_1Ne2ALvWmnNKjfx4hZM/* $VF was: $r8$lambda$_AZwHDe_1Ne2ALvWmnNKjfx4hZM*/(
      GxVehiclePage var0, int var1, LinearLayout var2
   ) {
      return build$lambda$2(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$cwx_QpCwDQplhJXumPYaJM4CHCQ/* $VF was: $r8$lambda$cwx_QpCwDQplhJXumPYaJM4CHCQ*/(GxVehiclePage var0, String var1) {
      return paintKeys$lambda$20(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$gULyPkN39uWpq0MWdGX_ApeF1_c/* $VF was: $r8$lambda$gULyPkN39uWpq0MWdGX_ApeF1_c*/(
      GxVehiclePage var0, GxVehiclePage.Feature var1, boolean var2
   ) {
      return openFeature$lambda$6(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$i20KQ_N_taUYBiIeUfl4Vi49dX4/* $VF was: $r8$lambda$i20KQ_N_taUYBiIeUfl4Vi49dX4*/(GxVehiclePage var0) {
      return buildDoors$lambda$9(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$ix4mzDDya38m0bkvTp1XfVZ8p9U(
      EditText var0, EditText var1, JSONObject var2, GxVehiclePage var3, String var4, DialogInterface var5, int var6
   ) {
      editKey$lambda$28(var0, var1, var2, var3, var4, var5, var6);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$qsAnOe3HiNRYeed77y1P5FL6eRE(GxVehiclePage var0, String var1) {
      return removeKey$lambda$31(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$sYncOeA5DZuksi60It1iwWgHxoI(GxVehiclePage var0, String var1, Object var2) {
      return removeKey$lambda$31$lambda$30(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$tsT2M2UsqV_2DiJIgd8Un5HDsXo/* $VF was: $r8$lambda$tsT2M2UsqV_2DiJIgd8Un5HDsXo*/(
      GxVehiclePage var0, String var1, String var2, JSONObject var3
   ) {
      return doorAct$lambda$14(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$xoGvYezbG4Xf2m0rz1ZFLv_2D2o(GxVehiclePage var0, String var1, JSONObject var2) {
      return applyKey$lambda$32(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$yAL9oAUhLX68cieKcUxLVp26aQ0(GxVehiclePage var0, GxVehiclePage.Feature var1, Function1 var2, int var3, String var4, String var5) {
      return check$lambda$5(var0, var1, var2, var3, var4, var5);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$z6HBkcZTQF_MR1CZ_uWjAsZ6MVU(GxVehiclePage var0) {
      return buildTsk$lambda$15(var0);
   }

   public GxVehiclePage(Activity var1, GxHost var2) {
      Intrinsics.checkNotNullParameter(var1, "act");
      Intrinsics.checkNotNullParameter(var2, "gx");
      super(var1, var2);
      GxVehiclePage.Feature var4 = new GxVehiclePage.Feature("doors", "Lock/Unlock Doors", "Send lock or unlock commands remotely to your vehicle.", 1);
      GxVehiclePage.Feature var3 = new GxVehiclePage.Feature("tsk", "Toyota Security Keys", "Manage and apply security keys for secOC protected devices.", 2);
      this.features = CollectionsKt.listOf(new GxVehiclePage.Feature[]{var4, var3});
      this.status = new HashMap<>();
      this.checking = "";
      this.featureValues = new HashMap<>();
      this.gates = new HashMap<>();
      this.selectTab = new GxVehiclePage$$ExternalSyntheticLambda9();
      this.doorBusy = "";
      this.keys = CollectionsKt.emptyList();
   }

   private final void applyKey(JSONObject var1) {
      GxPage.send$default(
         this,
         "POST",
         "/api/tsk_key_set",
         new JSONObject()
            .put("name", GxPage.str$default(this, var1, "name", null, 2, null))
            .put("value", GxPage.str$default(this, var1, "value", null, 2, null)),
         0L,
         new GxVehiclePage$$ExternalSyntheticLambda10(this),
         8,
         null
      );
   }

   private static final Unit applyKey$lambda$32(GxVehiclePage var0, String var1, JSONObject var2) {
      if (var1 == null) {
         var1 = "Key applied!";
      } else {
         var1 = "Apply failed...";
      }

      var0.toast(var1);
      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$1(GxVehiclePage var0, int var1) {
      var0.curTab = var1;
      if (var1 > 0) {
         var0.gateFor(var0.features.get(var1 - 1));
      }

      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$2(GxVehiclePage var0, int var1, LinearLayout var2) {
      Intrinsics.checkNotNullParameter(var2, "box");
      switch (var1) {
         case 0:
            var0.buildFeatures(var2);
            break;
         case 1:
            var0.buildDoors(var2);
            break;
         default:
            var0.buildTsk(var2);
      }

      return Unit.INSTANCE;
   }

   private final void buildDoors(LinearLayout var1) {
      var1 = this.makeGate("doors", var1);
      this.header("Lock/Unlock Doors", (ViewGroup)var1);
      LinearLayout var2 = this.card((ViewGroup)var1);
      GxPage.text$default(this, "Remotely lock or unlock your car doors using the buttons below.", (ViewGroup)var2, 18.0F, 0, false, 24, null);
      GxPage var6 = this;
      ViewGroup var8 = (ViewGroup)var2;
      Boolean var4 = true;
      Triple var3 = new Triple<>("Lock Doors", var4, new GxVehiclePage$$ExternalSyntheticLambda17(this));
      Triple var10 = new Triple<>("Unlock Doors", var4, new GxVehiclePage$$ExternalSyntheticLambda18(this));
      List var9 = CollectionsKt.listOf(new Triple[]{var3, var10});
      List var7 = GxVehicleKt.access$gxVehicleButtons(var6, var8, var9, true);
      this.lockBtn = (TextView)var7.get(0);
      this.unlockBtn = (TextView)var7.get(1);
      this.gateFor(this.features.get(0));
   }

   private static final Unit buildDoors$lambda$8(GxVehiclePage var0) {
      var0.doorAct("lock");
      return Unit.INSTANCE;
   }

   private static final Unit buildDoors$lambda$9(GxVehiclePage var0) {
      var0.doorAct("unlock");
      return Unit.INSTANCE;
   }

   private final void buildFeatures(LinearLayout var1) {
      this.header("Vehicle Features", (ViewGroup)var1);

      for (GxVehiclePage.Feature var3 : this.features) {
         GxPage.Row var4 = GxPage.row$default(this, var3.getName(), var3.getDesc(), (ViewGroup)var1, 0, 8, null);
         this.featureValues.put(var3.getKey(), var4.value(""));
         GxPage.Row.button$default(var4, "Open", false, new GxVehiclePage$$ExternalSyntheticLambda11(this, var3), 2, null);
      }

      this.note("These features verify vehicle compatibility when launched.", (ViewGroup)var1);
      this.paintFeatures();
   }

   private static final Unit buildFeatures$lambda$3(GxVehiclePage var0, GxVehiclePage.Feature var1) {
      var0.openFeature(var1);
      return Unit.INSTANCE;
   }

   private final void buildTsk(LinearLayout var1) {
      var1 = this.makeGate("tsk", var1);
      this.header("Toyota Security Keys", (ViewGroup)var1);
      this.keyBox = this.box((ViewGroup)var1);
      Pair var2 = TuplesKt.to("Add Key", new GxVehiclePage$$ExternalSyntheticLambda12(this));
      ViewGroup var3 = (ViewGroup)var1;
      this.buttons(new Pair[]{var2}, var3, true);
      this.note("Apply Key sets the selected key as the active security key.", (ViewGroup)var1);
      this.paintKeys();
      this.loadKeys();
      this.gateFor(this.features.get(1));
   }

   private static final Unit buildTsk$lambda$15(GxVehiclePage var0) {
      var0.editKey(null, "", "");
      return Unit.INSTANCE;
   }

   private final void check(GxVehiclePage.Feature var1, Function1<? super Boolean, Unit> var2) {
      boolean var3;
      if (this.checking.length() > 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (!var3) {
         this.checking = var1.getKey();
         this.paintFeatures();
         GxPage var5 = this;
         String var4 = this.enc(var1.getKey());
         GxPage.call$default(
            var5, "GET", "/api/car_features_check?tool=" + var4, null, 0L, new GxVehiclePage$$ExternalSyntheticLambda5(this, var1, var2), 12, null
         );
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private static final Unit check$lambda$5(GxVehiclePage var0, GxVehiclePage.Feature var1, Function1 var2, int var3, String var4, String var5) {
      Intrinsics.checkNotNullParameter(var4, "text");
      var0.checking = "";
      boolean var7 = false;
      boolean var8;
      if (var3 < 0) {
         var8 = true;
      } else {
         var8 = false;
      }

      boolean var12;
      if (200 <= var3 && var3 < 300) {
         var12 = true;
      } else {
         var12 = false;
      }

      boolean var6 = var7;
      if (var12) {
         label63:
         try {
            Result.Companion var16 = Result.Companion;
            JSONObject var17 = new JSONObject(var4);
            var13 = KtCompat.resultOf(var17.optBoolean("result", false));
         } catch (Throwable var11) {
            Result.Companion var15 = Result.Companion;
            var13 = KtCompat.resultOf(ResultKt.createFailure(var11));
            break label63;
         }

         Object var18 = var13;
         if (KtCompat.isFailure(var13)) {
            var18 = false;
         }

         var6 = var7;
         if ((Boolean)var18) {
            var6 = true;
         }
      }

      Map var19 = var0.status;
      String var9 = var1.getKey();
      if (var6) {
         var4 = "allowed";
      } else {
         var4 = "denied";
      }

      var19.put(var9, var4);
      var0.paintFeatures();
      var2.invoke(var8);
      if (var0.curTab > 0 && var0.features.get(var0.curTab - 1) != var1) {
         var0.gateFor(var0.features.get(var0.curTab - 1));
      }

      return Unit.INSTANCE;
   }

   private final void doorAct(String var1) {
      boolean var2;
      if (this.doorBusy.length() > 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (!var2) {
         this.doorBusy = var1;
         this.paintDoors();
         this.send("POST", "/api/doors/" + var1, null, 60L, new GxVehiclePage$$ExternalSyntheticLambda16(this, var1));
      }
   }

   private static final Unit doorAct$lambda$14(GxVehiclePage var0, String var1, String var2, JSONObject var3) {
      var0.doorBusy = "";
      var0.paintDoors();
      if (var2 != null) {
         CharSequence var10 = var2;
         Object var6 = var10;
         if (StringsKt.isBlank(var10)) {
            if (Intrinsics.areEqual(var1, "lock")) {
               var1 = "Failed to lock doors.";
            } else {
               var1 = "Failed to unlock doors.";
            }

            var6 = var1;
         }

         var0.toast((String)var6);
      } else {
         label33: {
            if (var3 != null) {
               var2 = GxPage.str$default(var0, var3, "message", null, 2, null);
               if (var2 != null) {
                  CharSequence var11 = var2;
                  CharSequence var8 = var11;
                  if (StringsKt.isBlank(var11)) {
                     var8 = null;
                  }

                  var2 = (String)var8;
                  if (var2 != null) {
                     var1 = var2;
                     break label33;
                  }
               }
            }

            if (Intrinsics.areEqual(var1, "lock")) {
               var1 = "Doors locked!";
            } else {
               var1 = "Doors unlocked!";
            }
         }

         var0.toast(var1);
      }

      return Unit.INSTANCE;
   }

   private final void editKey(JSONObject var1, String var2, String var3) {
      EditText var4 = GxVehicleKt.gxVehicleEdit$default(this, "Enter key name...", var2, 0, 8, null);
      EditText var5 = GxVehicleKt.gxVehicleEdit$default(this, "Enter key value...", var3, 0, 8, null);
      TextView var9 = new TextView((Context)this.getAct());
      var9.setText("A key with this name already exists.");
      var9.setTextColor(GxVehicleKt.access$getGX_VEHICLE_ERR$p());
      var9.setTextSize(15.0F);
      var9.setVisibility(8);
      if (var1 != null) {
         var2 = GxPage.str$default(this, var1, "name", null, 2, null);
      } else {
         var2 = null;
      }

      var4.addTextChangedListener(new TextWatcher(var9, this, var2) {
         final TextView $dup;
         final String $origName;
         final GxVehiclePage this$0;

         {
            this.$dup = var1;
            this.this$0 = var2x;
            this.$origName = var3x;
         }

         public void afterTextChanged(Editable var1) {
            TextView var4x;
            GxVehiclePage var5x;
            String var6;
            label18: {
               var4x = this.$dup;
               var5x = this.this$0;
               var6 = this.$origName;
               if (var1 != null) {
                  String var7 = var1.toString();
                  if (var7 != null) {
                     String var3x = StringsKt.trim(var7).toString();
                     var8 = var3x;
                     if (var3x != null) {
                        break label18;
                     }
                  }
               }

               var8 = "";
            }

            byte var2x;
            if (GxVehiclePage.editKey$isDup(var5x, var6, var8)) {
               var2x = 0;
            } else {
               var2x = 8;
            }

            var4x.setVisibility(var2x);
         }

         public void beforeTextChanged(CharSequence var1, int var2x, int var3x, int var4x) {
         }

         public void onTextChanged(CharSequence var1, int var2x, int var3x, int var4x) {
         }
      });
      LinearLayout var6 = new LinearLayout((Context)this.getAct());
      var6.setOrientation(1);
      var6.setPadding(this.getUi().dp(22), this.getUi().dp(8), this.getUi().dp(22), 0);
      TextView var7 = new TextView((Context)this.getAct());
      var7.setText("Key Name");
      var7.setTextColor(CarUi.Companion.getDIM());
      var7.setTextSize(14.0F);
      var6.addView((View)var7);
      var6.addView((View)var4);
      var6.addView((View)var9);
      TextView var10 = new TextView((Context)this.getAct());
      var10.setText("Key Value");
      var10.setTextColor(CarUi.Companion.getDIM());
      var10.setTextSize(14.0F);
      var10.setPadding(0, this.getUi().dp(12), 0, 0);
      var6.addView((View)var10);
      var6.addView((View)var5);
      Builder var12 = new Builder((Context)this.getAct());
      if (var1 == null) {
         var3 = "Add Key";
      } else {
         var3 = "Edit Key";
      }

      var12.setTitle(var3)
         .setView((View)var6)
         .setPositiveButton("Save", new GxVehiclePage$$ExternalSyntheticLambda1(var4, var5, var1, this, var2))
         .setNegativeButton("Cancel", null)
         .show();
   }

   private static final boolean editKey$isDup(GxVehiclePage var0, String var1, String var2) {
      var var6 = var0.keys;
      boolean var5 = var6 instanceof Collection;
      boolean var4 = false;
      if (!var5 || !((Collection)var6).isEmpty()) {
         for (JSONObject var7 : var6) {
            boolean var3;
            if (Intrinsics.areEqual(GxPage.str$default(var0, var7, "name", null, 2, null), var2)
               && !Intrinsics.areEqual(GxPage.str$default(var0, var7, "name", null, 2, null), var1)) {
               var3 = true;
            } else {
               var3 = false;
            }

            if (var3) {
               var4 = true;
               break;
            }
         }
      }

      return var4;
   }

   private static final void editKey$lambda$28(EditText var0, EditText var1, JSONObject var2, GxVehiclePage var3, String var4, DialogInterface var5, int var6) {
      String var14 = StringsKt.trim(var0.getText().toString()).toString();
      String var13 = StringsKt.trim(var1.getText().toString()).toString();
      Regex var10 = new Regex("\\s");
      boolean var7 = true;
      boolean var15;
      if (var2 != null
         && Intrinsics.areEqual(var14, GxPage.str$default(var3, var2, "name", null, 2, null))
         && Intrinsics.areEqual(var13, GxPage.str$default(var3, var2, "value", null, 2, null))) {
         var15 = true;
      } else {
         var15 = false;
      }

      String var11;
      if (editKey$isDup(var3, var4, var14)) {
         var11 = "A key with this name already exists.";
      } else {
         if (var14.length() != 0) {
            var7 = false;
         }

         if (var7 || var13.length() < 10 || var10.containsMatchIn(var14) || var10.containsMatchIn(var13)) {
            var11 = "Invalid input or duplicate name. The key needs a name and a value of at least 10 characters, without spaces.";
         } else if (var15) {
            var11 = "Nothing changed.";
         } else {
            var11 = null;
         }
      }

      if (var11 != null) {
         var3.toast(var11);
         if (!var15) {
            var3.editKey(var2, var14, var13);
         }
      } else {
         JSONArray var12 = new JSONArray();

         for (JSONObject var9 : var3.keys) {
            if (var2 == null || !Intrinsics.areEqual(GxPage.str$default(var3, var9, "name", null, 2, null), var4)) {
               var12.put(var9);
            }
         }

         var12.put(new JSONObject().put("name", var14).put("value", var13));
         GxPage.json$default(var3, "POST", "/api/tsk_keys", var12, 0L, new GxVehiclePage$$ExternalSyntheticLambda2(var3), 8, null);
      }
   }

   private static final Unit editKey$lambda$28$lambda$27(GxVehiclePage var0, String var1, Object var2) {
      if (var1 != null) {
         var0.toast("Save failed... (" + var1 + ")");
         return Unit.INSTANCE;
      } else {
         JSONArray var3;
         if (var2 instanceof JSONArray) {
            var3 = (JSONArray)var2;
         } else {
            var3 = null;
         }

         if (var3 != null) {
            var0.keys = var0.objects(var3);
         }

         var0.paintKeys();
         var0.toast("Saved key!");
         return Unit.INSTANCE;
      }
   }

   private final void gateFor(GxVehiclePage.Feature var1) {
      GxVehiclePage.Gate var4 = this.gates.get(var1.getKey());
      if (var4 != null) {
         String var3 = this.status.get(var1.getKey());
         boolean var2 = false;
         if (var3 != null) {
            switch (var3.hashCode()) {
               case -1335395429:
                  if (var3.equals("denied")) {
                     var4.getText().setText(this.notSupported(var1));
                     var4.getCard().setVisibility(0);
                     var4.getContent().setVisibility(8);
                     return;
                  }
                  break;
               case -911343192:
                  if (var3.equals("allowed")) {
                     var4.getCard().setVisibility(8);
                     var4.getContent().setVisibility(0);
                     return;
                  }
            }
         }

         var4.getText().setText("Checking vehicle compatibility...");
         var4.getCard().setVisibility(0);
         var4.getContent().setVisibility(8);
         if (this.checking.length() == 0) {
            var2 = true;
         }

         if (var2) {
            this.check(var1, new GxVehiclePage$$ExternalSyntheticLambda19(this, var1));
         }
      }
   }

   private static final Unit gateFor$lambda$7(GxVehiclePage var0, GxVehiclePage.Feature var1, boolean var2) {
      var0.gateFor(var1);
      return Unit.INSTANCE;
   }

   private final void loadKeys() {
      this.getArray("/api/tsk_keys", new GxVehiclePage$$ExternalSyntheticLambda0(this));
   }

   private static final Unit loadKeys$lambda$16(GxVehiclePage var0, String var1, JSONArray var2) {
      if (var1 == null && var2 != null) {
         var0.keys = var0.objects(var2);
      } else {
         var0.toast("Failed to load keys...");
      }

      var0.keysLoaded = true;
      var0.paintKeys();
      return Unit.INSTANCE;
   }

   private final LinearLayout makeGate(String var1, LinearLayout var2) {
      LinearLayout var4 = this.card((ViewGroup)var2);
      TextView var3 = GxPage.text$default(this, "", (ViewGroup)var4, 18.0F, CarUi.Companion.getDIM(), false, 16, null);
      var2 = this.box((ViewGroup)var2);
      this.gates.put(var1, new GxVehiclePage.Gate((View)var4, var3, var2));
      return var2;
   }

   private final String notSupported(GxVehiclePage.Feature var1) {
      String var2 = var1.getName();
      return var2 + " is not supported for your current vehicle.";
   }

   private final void openFeature(GxVehiclePage.Feature var1) {
      boolean var2;
      if (this.checking.length() > 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (!var2) {
         String var3 = this.status.get(var1.getKey());
         if (var3 != null) {
            switch (var3.hashCode()) {
               case -1335395429:
                  if (var3.equals("denied")) {
                     this.toast(this.notSupported(var1));
                     return;
                  }
                  break;
               case -911343192:
                  if (var3.equals("allowed")) {
                     this.selectTab.invoke(var1.getTab());
                     return;
                  }
            }
         }

         this.check(var1, new GxVehiclePage$$ExternalSyntheticLambda8(this, var1));
      }
   }

   private static final Unit openFeature$lambda$6(GxVehiclePage var0, GxVehiclePage.Feature var1, boolean var2) {
      if (Intrinsics.areEqual(var0.status.get(var1.getKey()), "allowed")) {
         var0.selectTab.invoke(var1.getTab());
      } else if (var2) {
         var0.toast("Could not check vehicle compatibility.");
      } else {
         var0.toast(var0.notSupported(var1));
      }

      return Unit.INSTANCE;
   }

   private final void paintDoors() {
      TextView var4 = this.lockBtn;
      boolean var2 = true;
      if (var4 != null) {
         String var3;
         if (Intrinsics.areEqual(this.doorBusy, "lock")) {
            var3 = "Locking...";
         } else {
            var3 = "Lock Doors";
         }

         var4.setText(var3);
         View var6 = (View)var4;
         boolean var1;
         if (this.doorBusy.length() == 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         GxVehicleKt.access$gxVehicleEnable(var6, var1);
      }

      var4 = this.unlockBtn;
      if (var4 != null) {
         String var7;
         if (Intrinsics.areEqual(this.doorBusy, "unlock")) {
            var7 = "Unlocking...";
         } else {
            var7 = "Unlock Doors";
         }

         var4.setText(var7);
         View var8 = (View)var4;
         boolean var5;
         if (this.doorBusy.length() == 0) {
            var5 = var2;
         } else {
            var5 = false;
         }

         GxVehicleKt.access$gxVehicleEnable(var8, var5);
      }
   }

   private final void paintFeatures() {
      for (GxVehiclePage.Feature var1 : this.features) {
         TextView var3 = this.featureValues.get(var1.getKey());
         if (var3 != null) {
            if (Intrinsics.areEqual(this.checking, var1.getKey())) {
               var3.setText("Checking...");
               var3.setTextColor(CarUi.Companion.getDIM());
            } else if (Intrinsics.areEqual(this.status.get(var1.getKey()), "denied")) {
               var3.setText("Not supported");
               var3.setTextColor(GxVehicleKt.access$getGX_VEHICLE_ERR$p());
            } else if (Intrinsics.areEqual(this.status.get(var1.getKey()), "allowed")) {
               var3.setText("Supported");
               var3.setTextColor(CarUi.Companion.getACCENT());
            } else {
               var3.setText("");
            }
         }
      }
   }

   private final void paintKeys() {
      LinearLayout var5 = this.keyBox;
      if (var5 != null) {
         var5.removeAllViews();
         if (!this.keysLoaded) {
            var5.addView((View)this.empty("Loading keys..."));
         } else if (this.keys.isEmpty()) {
            this.note("No saved keys yet.", (ViewGroup)var5);
         } else {
            for (JSONObject var4 : this.keys) {
               String var3 = GxPage.str$default(this, var4, "name", null, 2, null);
               String var7 = GxPage.str$default(this, var4, "value", null, 2, null);
               GxPage var8 = this;
               CharSequence var2 = var3;
               GxPage.Row var1 = var2;
               if (StringsKt.isBlank(var2)) {
                  var1 = "(no name)";
               }

               var1 = GxPage.row$default(var8, (String)var1, var7, (ViewGroup)var5, 0, 8, null);
               var1.getDesc().setTypeface(Typeface.MONOSPACE);
               var1.button("Apply Key", true, new GxVehiclePage$$ExternalSyntheticLambda13(this, var4));
               GxPage.Row.button$default(var1, "Edit", false, new GxVehiclePage$$ExternalSyntheticLambda14(this, var4, var3, var7), 2, null);
               GxPage.Row.button$default(var1, "Delete", false, new GxVehiclePage$$ExternalSyntheticLambda15(this, var3), 2, null);
            }
         }
      }
   }

   private static final Unit paintKeys$lambda$18(GxVehiclePage var0, JSONObject var1) {
      var0.applyKey(var1);
      return Unit.INSTANCE;
   }

   private static final Unit paintKeys$lambda$19(GxVehiclePage var0, JSONObject var1, String var2, String var3) {
      var0.editKey(var1, var2, var3);
      return Unit.INSTANCE;
   }

   private static final Unit paintKeys$lambda$20(GxVehiclePage var0, String var1) {
      var0.removeKey(var1);
      return Unit.INSTANCE;
   }

   private final void removeKey(String var1) {
      this.confirm(
         "Confirm Delete", "Are you sure you want to delete the key \"" + var1 + "\"?", "Yes, Delete", new GxVehiclePage$$ExternalSyntheticLambda3(this, var1)
      );
   }

   private static final Unit removeKey$lambda$31(GxVehiclePage var0, String var1) {
      GxPage var2 = var0;
      var1 = var0.enc(var1);
      GxPage.json$default(var2, "DELETE", "/api/tsk_keys?name=" + var1, null, 0L, new GxVehiclePage$$ExternalSyntheticLambda4(var0), 12, null);
      return Unit.INSTANCE;
   }

   private static final Unit removeKey$lambda$31$lambda$30(GxVehiclePage var0, String var1, Object var2) {
      if (var1 != null) {
         var0.toast("Delete failed...");
         return Unit.INSTANCE;
      } else {
         JSONArray var3;
         if (var2 instanceof JSONArray) {
            var3 = (JSONArray)var2;
         } else {
            var3 = null;
         }

         if (var3 != null) {
            var0.keys = var0.objects(var3);
         } else {
            var0.loadKeys();
         }

         var0.paintKeys();
         var0.toast("Deleted key!");
         return Unit.INSTANCE;
      }
   }

   private static final Unit selectTab$lambda$0(int var0) {
      return Unit.INSTANCE;
   }

   @Override
   public void build() {
      this.selectTab = GxPage.tabs$default(
         this,
         CollectionsKt.listOf(new String[]{"Vehicle Features", "Lock/Unlock Doors", "Toyota Security Keys"}),
         0,
         new GxVehiclePage$$ExternalSyntheticLambda6(this),
         new GxVehiclePage$$ExternalSyntheticLambda7(this),
         2,
         null
      );
   }

   @Override
   public void onShow() {
      this.status.clear();
      this.checking = "";
      this.paintFeatures();
      if (this.curTab > 0) {
         this.gateFor(this.features.get(this.curTab - 1));
      }

      if (this.keyBox != null) {
         this.loadKeys();
      }
   }

   private static final class Feature {
      private final String desc;
      private final String key;
      private final String name;
      private final int tab;

      public Feature(String var1, String var2, String var3, int var4) {
         Intrinsics.checkNotNullParameter(var1, "key");
         Intrinsics.checkNotNullParameter(var2, "name");
         Intrinsics.checkNotNullParameter(var3, "desc");
         super();
         this.key = var1;
         this.name = var2;
         this.desc = var3;
         this.tab = var4;
      }

      public final String getDesc() {
         return this.desc;
      }

      public final String getKey() {
         return this.key;
      }

      public final String getName() {
         return this.name;
      }

      public final int getTab() {
         return this.tab;
      }
   }

   private static final class Gate {
      private final View card;
      private final LinearLayout content;
      private final TextView text;

      public Gate(View var1, TextView var2, LinearLayout var3) {
         Intrinsics.checkNotNullParameter(var1, "card");
         Intrinsics.checkNotNullParameter(var2, "text");
         Intrinsics.checkNotNullParameter(var3, "content");
         super();
         this.card = var1;
         this.text = var2;
         this.content = var3;
      }

      public final View getCard() {
         return this.card;
      }

      public final LinearLayout getContent() {
         return this.content;
      }

      public final TextView getText() {
         return this.text;
      }
   }
}
