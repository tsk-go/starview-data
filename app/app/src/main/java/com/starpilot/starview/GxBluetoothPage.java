package com.starpilot.starview;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref.ObjectRef;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

public final class GxBluetoothPage extends GxPage {
   private LinearLayout btAvailBox;
   private boolean btAvailable;
   private boolean btBuilt;
   private String btBusy;
   private List<? extends JSONObject> btDevices;
   private boolean btDiscovering;
   private boolean btEnabled;
   private TextView btErrNote;
   private String btError;
   private boolean btInflight;
   private LinearLayout btKnownBox;
   private String btListSig;
   private boolean btLoaded;
   private boolean btOffroad;
   private TextView btOffroadNote;
   private EditText btPairEdit;
   private String btPairingAddress;
   private TextView btPowerBtn;
   private GxPage.Row btPowerRow;
   private JSONObject btPrompt;
   private LinearLayout btPromptBox;
   private String btPromptSig;
   private TextView btRefreshBtn;
   private TextView btScanBtn;
   private String btSelectedAudio;
   private final boolean needsOffroad;
   private int tab;
   private boolean wcAvailable;
   private LinearLayout wcBox;
   private String wcBusy;
   private List<? extends JSONObject> wcControllerSlots;
   private List<? extends JSONObject> wcDevices;
   private boolean wcDisconnectOffroad;
   private String wcError;
   private boolean wcInflight;
   private String wcJoystick;
   private Map<String, String> wcKeySection;
   private JSONObject wcLastTested;
   private boolean wcLearning;
   private Integer wcLearningSlot;
   private boolean wcLoaded;
   private List<? extends JSONObject> wcMappings;
   private boolean wcOffroad;
   private List<? extends JSONObject> wcOptions;
   private String wcRaw;
   private double wcRemaining;
   private List<String> wcSectionOrder;
   private String wcSig;
   private List<? extends JSONObject> wcSlots;
   private double wcSpeedMax;
   private double wcSpeedMin;
   private String wcSpeedUnit;
   private boolean wcTesting;

   // $VF: synthetic method
   public static Unit $r8$lambda$__kr_UmLRjeq7xi_kQq8meiqpF8/* $VF was: $r8$lambda$__kr_UmLRjeq7xi_kQq8meiqpF8*/(GxBluetoothPage var0, JSONObject var1) {
      return btPaint$lambda$20(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$2Lv0BHSwFSOJXysaKkEzLOX4OuY(GxBluetoothPage var0, int var1) {
      return wcPaint$lambda$45(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$9tERY9WylFerMz2Tv3cdDQAynuo(ObjectRef var0, GxBluetoothPage var1, int var2, String var3, View var4) {
      pickAction$optionButton$lambda$88(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Eleuu0wn6bn7jQwEqF0VRERigYg(GxBluetoothPage var0, int var1) {
      return wcPaint$lambda$53(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$FoxtlPk6k5aq8DkKmBaipyo3sYw(GxBluetoothPage var0, String var1, JSONObject var2) {
      return btRefresh$lambda$8(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$GPulxxyYU6iiisPOK0MDowD0J3A(GxBluetoothPage var0, double var1, int var3, String var4) {
      return wcPaint$lambda$58(var0, var1, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$LNOcK7CbYQfrCsLbGt_xlA7bOgY(GxBluetoothPage var0) {
      return paintPrompt$lambda$23(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Lp70zs2Hx1SAYk1QZbnMC2nLmBc(GxBluetoothPage var0, String var1, JSONObject var2) {
      return wcRequest$lambda$33(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$M4oG6z_282_kJmPkRgCsKrCeoT8(GxBluetoothPage var0) {
      return buildBt$lambda$5(var0);
   }

   // $VF: synthetic method
   public static CharSequence $r8$lambda$M9azZdnxtR5nljYZZ_UO0pzD6EU/* $VF was: $r8$lambda$M9azZdnxtR5nljYZZ_UO0pzD6EU*/(JSONObject var0) {
      return btPaint$lambda$10(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$MNSiV9lfunNHfDllMWh_j7O7gTM/* $VF was: $r8$lambda$MNSiV9lfunNHfDllMWh_j7O7gTM*/(GxBluetoothPage var0) {
      return paintPrompt$lambda$22(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Nm_BD3JT2UMV_5yNP_NRxQlIH5A(GxBluetoothPage var0) {
      return wcPaint$lambda$39(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$PucQfz_ZCD4flL7DZjSNLSq6eN4(GxBluetoothPage var0, String var1, JSONObject var2) {
      return wcRefresh$lambda$32(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$R7UHA0qtyCFuzglFIgsqTp_J1s0(GxBluetoothPage var0, String var1, boolean var2) {
      return wcPaint$lambda$43(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Rape7WshlNUNSHKRqFoMkahq5HQ(GxBluetoothPage var0, boolean var1) {
      return wcPaint$lambda$41(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$UjKHxRgVlh_RYuH5MYaRouqnx40(GxBluetoothPage var0) {
      return buildBt$lambda$6(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Zb8CCPYNTWGgqu_2TKKWdxDwjr4(GxBluetoothPage var0) {
      return wcPaint$lambda$47(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Zxs3SO7ue4H89CmrwhQ4RIlxhSU(GxBluetoothPage var0) {
      return build$lambda$3(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$_tVI6__L2_1Zhsj_5pmeWU_PZ_k/* $VF was: $r8$lambda$_tVI6__L2_1Zhsj_5pmeWU_PZ_k*/(
      GxBluetoothPage var0, int var1, LinearLayout var2
   ) {
      return build$lambda$1(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$c_GPnnLLYhjI6ENK_RmcmxSbHb0(GxBluetoothPage var0, int var1) {
      return build$lambda$0(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$ccfuydxsZr9h1QFpMfMzYHCDKRM(GxBluetoothPage var0, int var1) {
      return wcPaint$lambda$49(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$eoAUzLX5bUE41YwJHTH6XT2EaWw(GxBluetoothPage var0) {
      return wcPaint$lambda$37(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$f09XwWiiBYuhuEcPky8cbLHz8Pw(GxBluetoothPage var0, String var1, JSONArray var2) {
      return buildWc$lambda$27(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$fu_yOz_p9qyaF1aQWbr_LdefeS0/* $VF was: $r8$lambda$fu_yOz_p9qyaF1aQWbr_LdefeS0*/(GxBluetoothPage var0, JSONObject var1) {
      return btPaint$lambda$16(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$hRrR0kcbzsdQek_IR2Drp5Ds86k(Function0 var0) {
      return slotCard$lambda$64(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$kL_xbSU_x8GBtsa8BZtJqWnnja4/* $VF was: $r8$lambda$kL_xbSU_x8GBtsa8BZtJqWnnja4*/(
      GxBluetoothPage var0, String var1, JSONObject var2
   ) {
      return btPaint$lambda$18(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$pD_IvT6Cp0e_XNkZ59uz_n5J_H4/* $VF was: $r8$lambda$pD_IvT6Cp0e_XNkZ59uz_n5J_H4*/(GxBluetoothPage var0, JSONObject var1) {
      return btPaint$lambda$14(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$sPVYfyvswM6jSD_vClEdBUxe9fs(GxBluetoothPage var0, JSONObject var1) {
      return btPaint$lambda$15(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$uZDqaMf_lWr_XTHe8hCDcSow338(GxBluetoothPage var0) {
      return buildBt$lambda$4(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$wYnUJDJx_IEawe33uQlY1CiDo8k/* $VF was: $r8$lambda$wYnUJDJx_IEawe33uQlY1CiDo8k*/(
      GxBluetoothPage var0, int var1, String var2, String var3
   ) {
      return wcPaint$lambda$58$lambda$57(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$wr_rJ08WJmzR_UAJoBtaHTEMP_U/* $VF was: $r8$lambda$wr_rJ08WJmzR_UAJoBtaHTEMP_U*/(GxBluetoothPage var0) {
      return wcPaint$lambda$39$lambda$38(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$xKiQl0DabvB1KxVqakLH4t8_k54/* $VF was: $r8$lambda$xKiQl0DabvB1KxVqakLH4t8_k54*/(
      GxBluetoothPage var0, String var1, String var2, JSONObject var3
   ) {
      return btRequest$lambda$9(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static void $r8$lambda$xeOrHOeGqNExaAU8hUoPTPeUm9g(
      String var0,
      HashSet var1,
      String var2,
      LinearLayout var3,
      EditText var4,
      List var5,
      TextView var6,
      List var7,
      GxBluetoothPage var8,
      String var9,
      ObjectRef var10,
      int var11,
      View var12
   ) {
      pickAction$render$lambda$95$lambda$94(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$yyZ5Pt_ozemA7jC0gnTQnJ75NH8/* $VF was: $r8$lambda$yyZ5Pt_ozemA7jC0gnTQnJ75NH8*/(GxBluetoothPage var0, JSONObject var1) {
      return slotTail$lambda$70(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$z_O9mGFk58UMztuFQHZhcsTC3mU/* $VF was: $r8$lambda$z_O9mGFk58UMztuFQHZhcsTC3mU*/(GxBluetoothPage var0) {
      return build$lambda$2(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$zevtBWXw7_tM1Scksw_1sd63lQc(GxBluetoothPage var0, JSONObject var1) {
      return btPaint$lambda$18$lambda$17(var0, var1);
   }

   public GxBluetoothPage(Activity var1, GxHost var2) {
      Intrinsics.checkNotNullParameter(var1, "act");
      Intrinsics.checkNotNullParameter(var2, "gx");
      super(var1, var2);
      this.needsOffroad = true;
      this.btBusy = "";
      this.btSelectedAudio = "";
      this.btPairingAddress = "";
      this.btDevices = CollectionsKt.emptyList();
      this.btError = "";
      this.btPromptSig = "";
      this.btListSig = "";
      this.wcBusy = "";
      this.wcDevices = CollectionsKt.emptyList();
      this.wcMappings = CollectionsKt.emptyList();
      this.wcSlots = CollectionsKt.emptyList();
      this.wcControllerSlots = CollectionsKt.emptyList();
      this.wcOptions = CollectionsKt.emptyList();
      this.wcJoystick = "";
      this.wcSpeedUnit = "mph";
      this.wcSpeedMin = 5.0;
      this.wcSpeedMax = 90.0;
      this.wcError = "";
      this.wcRaw = "";
      this.wcSig = "";
      this.wcKeySection = MapsKt.emptyMap();
      this.wcSectionOrder = CollectionsKt.emptyList();
   }

   private final String addr(JSONObject var1) {
      String var2 = GxPage.str$default(this, var1, "address", null, 2, null).toUpperCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var2, "toUpperCase(...)");
      return var2;
   }

   private final String anchorKey(String var1) {
      int var2 = var1.hashCode();
      String var3 = "ConditionalExperimental";
      switch (var2) {
         case -1223291282:
            if (!var1.equals("ConditionalExperimental")) {
               return var1;
            }
            break;
         case -1119957410:
            if (!var1.equals("ConditionalChill")) {
               return var1;
            }
            break;
         case -878798333:
            if (!var1.equals("__starpilot_controller_action__:cycle_driving_personality")) {
               return var1;
            }

            return "CustomPersonalities";
         case -370744618:
            if (!var1.equals("__starpilot_favorite_action__:toggle_traffic_mode")) {
               return var1;
            }

            return "CustomPersonalities";
         case 499325643:
            if (!var1.equals("ExperimentalMode")) {
               return var1;
            }
            break;
         default:
            return var1;
      }

      return var3;
   }

   private final void btPaint() {
      if (this.btBuilt) {
         GxPage.Row var8 = this.btPowerRow;
         GxPage.Row var7 = var8;
         if (var8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btPowerRow");
            var7 = null;
         }

         String var67;
         if (this.btEnabled) {
            var67 = "On";
         } else {
            var67 = "Off";
         }

         var7.setTitle("Bluetooth " + var67);
         TextView var68 = this.btPowerBtn;
         TextView var36 = var68;
         if (var68 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btPowerBtn");
            var36 = null;
         }

         String var69;
         if (this.btEnabled) {
            var69 = "Turn Off";
         } else {
            var69 = "Turn On";
         }

         var36.setText(var69);
         TextView var70 = this.btPowerBtn;
         TextView var37 = var70;
         if (var70 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btPowerBtn");
            var37 = null;
         }

         View var38 = (View)var37;
         boolean var3;
         if (this.btAvailable && this.offroadOk()) {
            var3 = true;
         } else {
            var3 = false;
         }

         GxVehicleKt.access$gxVehicleEnable(var38, var3);
         TextView var71 = this.btOffroadNote;
         TextView var39 = var71;
         if (var71 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btOffroadNote");
            var39 = null;
         }

         var3 = this.btLoaded;
         byte var2 = 8;
         byte var1;
         if (var3 && !this.btOffroad) {
            var1 = 0;
         } else {
            var1 = 8;
         }

         var39.setVisibility(var1);
         TextView var72 = this.btErrNote;
         TextView var40 = var72;
         if (var72 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btErrNote");
            var40 = null;
         }

         var40.setText(this.btError);
         TextView var73 = this.btErrNote;
         TextView var41 = var73;
         if (var73 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btErrNote");
            var41 = null;
         }

         var1 = var2;
         if (!StringsKt.isBlank(this.btError)) {
            var1 = (byte)0;
         }

         var41.setVisibility(var1);
         TextView var74 = this.btScanBtn;
         TextView var42 = var74;
         if (var74 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btScanBtn");
            var42 = null;
         }

         String var75;
         if (this.btDiscovering) {
            var75 = "Searching…";
         } else {
            var75 = "Search for Devices";
         }

         var42.setText(var75);
         TextView var76 = this.btScanBtn;
         TextView var43 = var76;
         if (var76 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btScanBtn");
            var43 = null;
         }

         View var44 = (View)var43;
         if (this.btOffroad && this.btEnabled && this.offroadOk()) {
            var3 = true;
         } else {
            var3 = false;
         }

         GxVehicleKt.access$gxVehicleEnable(var44, var3);
         TextView var77 = this.btRefreshBtn;
         TextView var45 = var77;
         if (var77 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btRefreshBtn");
            var45 = null;
         }

         View var46 = (View)var45;
         if (this.btBusy.length() == 0) {
            var3 = true;
         } else {
            var3 = false;
         }

         label281: {
            GxVehicleKt.access$gxVehicleEnable(var46, var3);
            JSONObject var47 = this.btPrompt;
            if (var47 != null) {
               String var78 = var47.toString();
               var48 = var78;
               if (var78 != null) {
                  break label281;
               }
            }

            var48 = "";
         }

         var3 = Intrinsics.areEqual(this.btBusy, "pairing_response");
         String var79 = var48 + "|" + var3;
         if (!Intrinsics.areEqual(var79, this.btPromptSig)) {
            this.btPromptSig = var79;
            this.paintPrompt();
         }

         String var9 = CollectionsKt.joinToString$default(this.btDevices, null, null, null, 0, null, new GxBluetoothPage$$ExternalSyntheticLambda9(), 31, null);
         String var10 = this.btSelectedAudio;
         String var11 = this.btPairingAddress;
         String var49 = this.btBusy;
         boolean var5 = this.btOffroad;
         var3 = this.btDiscovering;
         boolean var4 = this.btLoaded;
         String var12 = var9 + "|" + var10 + "|" + var11 + "|" + var49 + "|" + var5 + "|" + var3 + "|" + var4;
         if (!Intrinsics.areEqual(var12, this.btListSig)) {
            this.btListSig = var12;
            LinearLayout var87 = this.btKnownBox;
            LinearLayout var50 = var87;
            if (var87 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("btKnownBox");
               var50 = null;
            }

            var50.removeAllViews();
            LinearLayout var88 = this.btAvailBox;
            LinearLayout var51 = var88;
            if (var88 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("btAvailBox");
               var51 = null;
            }

            var51.removeAllViews();
            if (!this.btLoaded) {
               LinearLayout var66 = this.btKnownBox;
               if (var66 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("btKnownBox");
                  var66 = null;
               }

               var66.addView((View)this.empty("Loading..."));
            } else {
               Iterable var89 = this.btDevices;
               var var52 = new ArrayList();
               Iterator var94 = var89.iterator();

               while (true) {
                  var3 = var94.hasNext();
                  var11 = "connected";
                  var9 = "paired";
                  if (!var3) {
                     List var95 = var52;
                     Iterable var53 = this.btDevices;
                     var var13 = new ArrayList();

                     for (Object var15 : var53) {
                        JSONObject var80 = (JSONObject)var15;
                        boolean var21;
                        if (!var80.optBoolean("paired") && !var80.optBoolean("trusted") && !var80.optBoolean("connected")) {
                           var21 = true;
                        } else {
                           var21 = false;
                        }

                        if (var21) {
                           var13.add(var15);
                        }
                     }

                     List var105 = var13;
                     if (var95.isEmpty()) {
                        LinearLayout var81 = this.btKnownBox;
                        LinearLayout var54 = var81;
                        if (var81 == null) {
                           Intrinsics.throwUninitializedPropertyAccessException("btKnownBox");
                           var54 = null;
                        }

                        this.note("No saved devices yet.", (ViewGroup)var54);
                     }

                     for (JSONObject var106 : var95) {
                        CharSequence var101 = GxPage.str$default(this, var106, "name", null, 2, null);
                        CarUi.Companion var55 = var101;
                        if (StringsKt.isBlank(var101)) {
                           var55 = GxPage.str$default(this, var106, "address", null, 2, null);
                        }

                        String var16 = (String)var55;
                        var4 = var106.optBoolean("audio");
                        var3 = var106.optBoolean("controller");
                        var5 = var106.optBoolean(var11);
                        boolean var6 = var106.optBoolean(var9);
                        LinearLayout var102 = this.btKnownBox;
                        var55 = var102;
                        if (var102 == null) {
                           Intrinsics.throwUninitializedPropertyAccessException("btKnownBox");
                           var55 = null;
                        }

                        LinearLayout var103 = this.card((ViewGroup)var55);
                        GxPage var17 = this;
                        ViewGroup var18 = (ViewGroup)var103;
                        var55 = CarUi.Companion;
                        if (var5) {
                           var1 = var55.getACCENT();
                        } else {
                           var1 = var55.getTEXT();
                        }

                        GxPage.text$default(var17, var16, var18, 21.0F, var1, false, 16, null);
                        var17 = this;
                        String var58;
                        if (var4 && var3) {
                           var58 = "Audio · Controller";
                        } else if (var4) {
                           var58 = "Audio";
                        } else if (var3) {
                           var58 = "Controller";
                        } else {
                           var58 = "Bluetooth";
                        }

                        String var109 = this.statusOf(var106);
                        GxPage.text$default(var17, var58 + " · " + var109, (ViewGroup)var103, 0.0F, 0, false, 28, null);
                        ArrayList var108 = new ArrayList();
                        if (var6 || var5) {
                           String var59;
                           if (var5) {
                              var59 = "Disconnect";
                           } else {
                              var59 = "Connect";
                           }

                           if (this.btBusy.length() == 0) {
                              var3 = true;
                           } else {
                              var3 = false;
                           }

                           var108.add(new Triple<>(var59, var3, new GxBluetoothPage$$ExternalSyntheticLambda10(this, var106)));
                        }

                        if (var4) {
                           String var60;
                           if (this.isAudioSel(var106)) {
                              var60 = "Stop Using for Audio";
                           } else {
                              var60 = "Use for Audio";
                           }

                           if (this.btBusy.length() == 0) {
                              var3 = true;
                           } else {
                              var3 = false;
                           }

                           var108.add(new Triple<>(var60, var3, new GxBluetoothPage$$ExternalSyntheticLambda12(this, var106)));
                        }

                        if (var4 && var5) {
                           var108.add(new Triple<>("Test Audio", this.offroadOk(), new GxBluetoothPage$$ExternalSyntheticLambda13(this, var106)));
                        }

                        if (var6) {
                           var108.add(new Triple<>("Forget", this.offroadOk(), new GxBluetoothPage$$ExternalSyntheticLambda14(this, var16, var106)));
                        }

                        if (!var108.isEmpty()) {
                           GxVehicleKt.gxVehicleButtons$default(this, (ViewGroup)var103, var108, false, 8, null);
                        }
                     }

                     if (var105.isEmpty()) {
                        String var61;
                        if (this.btDiscovering) {
                           var61 = "Searching for nearby devices…";
                        } else {
                           var61 = "No nearby devices found.";
                        }

                        LinearLayout var92 = this.btAvailBox;
                        LinearLayout var83 = var92;
                        if (var92 == null) {
                           Intrinsics.throwUninitializedPropertyAccessException("btAvailBox");
                           var83 = null;
                        }

                        this.note(var61, (ViewGroup)var83);
                     }

                     for (JSONObject var93 : var105) {
                        GxPage var99 = this;
                        CharSequence var84 = GxPage.str$default(this, var93, "name", null, 2, null);
                        Object var62 = var84;
                        if (StringsKt.isBlank(var84)) {
                           var62 = GxPage.str$default(this, var93, "address", null, 2, null);
                        }

                        var12 = (String)var62;
                        String var104 = this.statusOf(var93);
                        LinearLayout var85 = this.btAvailBox;
                        var62 = var85;
                        if (var85 == null) {
                           Intrinsics.throwUninitializedPropertyAccessException("btAvailBox");
                           var62 = null;
                        }

                        var8 = GxPage.row$default(var99, var12, var104, (ViewGroup)var62, 0, 8, null);
                        if (this.isPairing(var93)) {
                           var62 = "Pairing…";
                        } else {
                           var62 = "Pair";
                        }

                        label231: {
                           var62 = (View)var8.button((String)var62, true, new GxBluetoothPage$$ExternalSyntheticLambda15(this, var93));
                           if (this.btOffroad) {
                              boolean var23;
                              if (this.btBusy.length() == 0) {
                                 var23 = true;
                              } else {
                                 var23 = false;
                              }

                              if (var23 && !this.isPairing(var93)) {
                                 var3 = true;
                                 break label231;
                              }
                           }

                           var3 = false;
                        }

                        GxVehicleKt.access$gxVehicleEnable((View)var62, var3);
                     }

                     return;
                  }

                  var9 = (String)var94.next();
                  JSONObject var98 = (JSONObject)var9;
                  boolean var20;
                  if (!var98.optBoolean("paired") && !var98.optBoolean("trusted") && !var98.optBoolean("connected")) {
                     var20 = false;
                  } else {
                     var20 = true;
                  }

                  if (var20) {
                     var52.add(var9);
                  }
               }
            }
         }
      }
   }

   private static final CharSequence btPaint$lambda$10(JSONObject var0) {
      Intrinsics.checkNotNullParameter(var0, "it");
      String var1 = var0.toString();
      Intrinsics.checkNotNullExpressionValue(var1, "toString(...)");
      return var1;
   }

   private static final Unit btPaint$lambda$14(GxBluetoothPage var0, JSONObject var1) {
      String var2;
      if (var1.optBoolean("connected")) {
         var2 = "disconnect";
      } else {
         var2 = "connect";
      }

      var1 = new JSONObject().put("address", GxPage.str$default(var0, var1, "address", null, 2, null));
      Intrinsics.checkNotNullExpressionValue(var1, "put(...)");
      var0.btRequest(var2, var1);
      return Unit.INSTANCE;
   }

   private static final Unit btPaint$lambda$15(GxBluetoothPage var0, JSONObject var1) {
      JSONObject var2 = new JSONObject();
      String var3;
      if (var0.isAudioSel(var1)) {
         var3 = "";
      } else {
         var3 = GxPage.str$default(var0, var1, "address", null, 2, null);
      }

      var1 = var2.put("address", var3);
      Intrinsics.checkNotNullExpressionValue(var1, "put(...)");
      var0.btRequest("select_audio", var1);
      return Unit.INSTANCE;
   }

   private static final Unit btPaint$lambda$16(GxBluetoothPage var0, JSONObject var1) {
      var1 = new JSONObject().put("address", GxPage.str$default(var0, var1, "address", null, 2, null));
      Intrinsics.checkNotNullExpressionValue(var1, "put(...)");
      var0.btRequest("test_audio", var1);
      return Unit.INSTANCE;
   }

   private static final Unit btPaint$lambda$18(GxBluetoothPage var0, String var1, JSONObject var2) {
      var0.confirm("Forget " + var1 + "?", null, "Forget", new GxBluetoothPage$$ExternalSyntheticLambda23(var0, var2));
      return Unit.INSTANCE;
   }

   private static final Unit btPaint$lambda$18$lambda$17(GxBluetoothPage var0, JSONObject var1) {
      var1 = new JSONObject().put("address", GxPage.str$default(var0, var1, "address", null, 2, null));
      Intrinsics.checkNotNullExpressionValue(var1, "put(...)");
      var0.btRequest("forget", var1);
      return Unit.INSTANCE;
   }

   private static final Unit btPaint$lambda$20(GxBluetoothPage var0, JSONObject var1) {
      var1 = new JSONObject().put("address", GxPage.str$default(var0, var1, "address", null, 2, null));
      Intrinsics.checkNotNullExpressionValue(var1, "put(...)");
      var0.btRequest("pair", var1);
      return Unit.INSTANCE;
   }

   private final void btRefresh() {
      if (!this.btInflight) {
         this.btInflight = true;
         this.getJson("/api/bluetooth/status", new GxBluetoothPage$$ExternalSyntheticLambda0(this));
      }
   }

   private static final Unit btRefresh$lambda$8(GxBluetoothPage var0, String var1, JSONObject var2) {
      var0.btInflight = false;
      if (var1 == null && var2 != null) {
         label33: {
            var0.btAvailable = var2.optBoolean("available");
            var0.btEnabled = var2.optBoolean("enabled");
            var0.btDiscovering = var2.optBoolean("discovering");
            var0.btOffroad = var2.optBoolean("offroad");
            var0.btSelectedAudio = GxPage.str$default(var0, var2, "selected_audio", null, 2, null);
            var0.btPairingAddress = GxPage.str$default(var0, var2, "pairing_address", null, 2, null);
            JSONArray var4 = var2.optJSONArray("devices");
            if (var4 != null) {
               List var9 = var0.objects(var4);
               var5 = var9;
               if (var9 != null) {
                  break label33;
               }
            }

            var5 = CollectionsKt.emptyList();
         }

         var0.btDevices = var5;
         var0.btPrompt = var2.optJSONObject("prompt");
         var0.btError = GxPage.str$default(var0, var2, "error", null, 2, null);
      } else {
         label28: {
            var0.btAvailable = false;
            if (var2 != null) {
               String var6 = GxPage.str$default(var0, var2, "error", null, 2, null);
               if (var6 != null) {
                  CharSequence var3 = var6;
                  CharSequence var7 = var3;
                  if (StringsKt.isBlank(var3)) {
                     var7 = null;
                  }

                  String var8 = (String)var7;
                  if (var8 != null) {
                     var1 = var8;
                     break label28;
                  }
               }
            }

            if (var1 == null) {
               var1 = "Bluetooth service unavailable";
            }
         }

         var0.btError = var1;
      }

      var0.btLoaded = true;
      var0.btPaint();
      return Unit.INSTANCE;
   }

   private final void btRequest(String var1, JSONObject var2) {
      boolean var3;
      if (this.btBusy.length() > 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (!var3) {
         this.btBusy = var1;
         this.btPaint();
         GxPage.send$default(this, "POST", "/api/bluetooth/" + var1, var2, 0L, new GxBluetoothPage$$ExternalSyntheticLambda24(this, var1), 8, null);
      }
   }

   // $VF: synthetic method
   static void btRequest$default(GxBluetoothPage var0, String var1, JSONObject var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = new JSONObject();
      }

      var0.btRequest(var1, var2);
   }

   private static final Unit btRequest$lambda$9(GxBluetoothPage var0, String var1, String var2, JSONObject var3) {
      var0.btBusy = "";
      if (var2 != null) {
         var0.btError = var2;
         var0.toast(var2);
      } else {
         var0.btError = "";
         if (Intrinsics.areEqual(var1, "test_audio")) {
            double var4 = 3000.0;
            if (var3 != null) {
               var4 = var3.optDouble("audio_test_delay_ms", 3000.0);
            }

            long var6 = Math.round(var4 / 1000.0);
            var0.toast("Test sound plays in " + var6 + " s");
         }
      }

      var0.btPaint();
      if (var2 == null) {
         var0.btRefresh();
      }

      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$0(GxBluetoothPage var0, int var1) {
      var0.tab = var1;
      if (var0.getShown()) {
         if (var1 == 0) {
            var0.btRefresh();
         } else {
            var0.wcRefresh();
         }
      }

      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$1(GxBluetoothPage var0, int var1, LinearLayout var2) {
      Intrinsics.checkNotNullParameter(var2, "box");
      if (var1 == 0) {
         var0.buildBt(var2);
      } else {
         var0.buildWc(var2);
      }

      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$2(GxBluetoothPage var0) {
      if (var0.tab == 0) {
         var0.btRefresh();
      }

      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$3(GxBluetoothPage var0) {
      if (var0.tab == 1) {
         var0.wcRefresh();
      }

      return Unit.INSTANCE;
   }

   private final void buildBt(LinearLayout var1) {
      this.header("Bluetooth Devices", (ViewGroup)var1);
      this.btPowerRow = GxPage.row$default(this, "Bluetooth", null, (ViewGroup)var1, 0, 8, null);
      GxPage.Row var3 = this.btPowerRow;
      GxPage.Row var2 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("btPowerRow");
         var2 = null;
      }

      this.btPowerBtn = GxPage.Row.button$default(var2, "Turn On", false, new GxBluetoothPage$$ExternalSyntheticLambda6(this), 2, null);
      this.btOffroadNote = GxVehicleKt.access$gxVehicleNotice(
         this, "Scanning, pairing, and forgetting devices are available offroad only.", CarUi.Companion.getWARN(), (ViewGroup)var1
      );
      this.btErrNote = GxVehicleKt.access$gxVehicleNotice(this, "", GxVehicleKt.access$getGX_VEHICLE_ERR$p(), (ViewGroup)var1);
      this.btPromptBox = this.box((ViewGroup)var1);
      GxPage var6 = this;
      ViewGroup var8 = (ViewGroup)var1;
      Triple var4 = new Triple<>("Search for Devices", true, new GxBluetoothPage$$ExternalSyntheticLambda7(this));
      Triple var5 = new Triple<>("Refresh", true, new GxBluetoothPage$$ExternalSyntheticLambda8(this));
      List var9 = CollectionsKt.listOf(new Triple[]{var4, var5});
      List var7 = GxVehicleKt.access$gxVehicleButtons(var6, var8, var9, true);
      this.btScanBtn = (TextView)var7.get(0);
      this.btRefreshBtn = (TextView)var7.get(1);
      this.header("My Devices", (ViewGroup)var1);
      this.btKnownBox = this.box((ViewGroup)var1);
      this.header("Available Devices", (ViewGroup)var1);
      this.btAvailBox = this.box((ViewGroup)var1);
      this.btBuilt = true;
      this.btPaint();
   }

   private static final Unit buildBt$lambda$4(GxBluetoothPage var0) {
      JSONObject var1 = new JSONObject().put("enabled", var0.btEnabled ^ true);
      Intrinsics.checkNotNullExpressionValue(var1, "put(...)");
      var0.btRequest("power", var1);
      return Unit.INSTANCE;
   }

   private static final Unit buildBt$lambda$5(GxBluetoothPage var0) {
      String var1;
      if (var0.btDiscovering) {
         var1 = "stop_scan";
      } else {
         var1 = "scan";
      }

      btRequest$default(var0, var1, null, 2, null);
      return Unit.INSTANCE;
   }

   private static final Unit buildBt$lambda$6(GxBluetoothPage var0) {
      var0.btRefresh();
      return Unit.INSTANCE;
   }

   private final void buildWc(LinearLayout var1) {
      this.note("Map buttons to favorites or controller-only actions, or explicitly select one gamepad for Joystick Mode.", (ViewGroup)var1);
      this.wcBox = this.box((ViewGroup)var1);
      this.wcPaint();
      this.getArray("/assets/components/tools/device_settings_layout.json", new GxBluetoothPage$$ExternalSyntheticLambda22(this));
   }

   private static final Unit buildWc$lambda$27(GxBluetoothPage var0, String var1, JSONArray var2) {
      if (var1 == null && var2 != null) {
         HashMap var3 = new HashMap();
         ArrayList var4 = new ArrayList();

         for (JSONObject var7 : var0.objects(var2)) {
            CharSequence var12 = var7.optString("name");
            CharSequence var8 = var12;
            if (StringsKt.isBlank(var12)) {
               var8 = null;
            }

            String var6 = (String)var8;
            if (var6 != null) {
               JSONArray var9 = var7.optJSONArray("params");
               if (var9 != null) {
                  var4.add(var6);
                  Iterator var14 = var0.objects(var9).iterator();

                  while (var14.hasNext()) {
                     CharSequence var13 = ((JSONObject)var14.next()).optString("key");
                     CharSequence var10 = var13;
                     if (StringsKt.isBlank(var13)) {
                        var10 = null;
                     }

                     var1 = (String)var10;
                     if (var1 != null) {
                        var3.put(var1, var6);
                     }
                  }
               }
            }
         }

         var0.wcKeySection = var3;
         var0.wcSectionOrder = var4;
         return Unit.INSTANCE;
      } else {
         return Unit.INSTANCE;
      }
   }

   private final String category(JSONObject var1) {
      String var3 = this.wcKeySection.get(this.anchorKey(GxPage.str$default(this, var1, "key", null, 2, null)));
      String var2 = var3;
      if (var3 == null) {
         if (Intrinsics.areEqual(GxPage.str$default(this, var1, "section", null, 2, null), "Actions")) {
            var2 = "Controller Actions";
         } else {
            CharSequence var5 = GxPage.str$default(this, var1, "section", null, 2, null);
            Object var4 = var5;
            if (StringsKt.isBlank(var5)) {
               var4 = "Other";
            }

            var2 = (String)var4;
         }
      }

      return var2;
   }

   private final boolean configured(JSONObject var1) {
      boolean var4 = false;
      boolean var3 = var4;
      if (var1 != null) {
         var3 = var4;
         if (var1.optBoolean("enabled")) {
            boolean var2;
            if (GxPage.str$default(this, var1, "key", null, 2, null).length() > 0) {
               var2 = true;
            } else {
               var2 = false;
            }

            var3 = var4;
            if (var2) {
               var3 = true;
            }
         }
      }

      return var3;
   }

   private final String eventName(JSONObject var1) {
      CharSequence var3 = GxPage.str$default(this, var1, "event_name", null, 2, null);
      Object var2 = var3;
      if (StringsKt.isBlank(var3)) {
         var2 = var1.opt("event_code");
         Object var4 = var2;
         if (var2 == null) {
            var4 = "";
         }

         var2 = "Button " + var4;
      }

      return (String)var2;
   }

   private final String fmtNum(double var1) {
      boolean var3;
      if (var1 == Math.floor(var1)) {
         var3 = true;
      } else {
         var3 = false;
      }

      String var4;
      if (var3) {
         var4 = String.valueOf((long)var1);
      } else {
         var4 = String.valueOf(var1);
      }

      return var4;
   }

   private final boolean isAudioSel(JSONObject var1) {
      String var2 = this.btSelectedAudio.toUpperCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var2, "toUpperCase(...)");
      return Intrinsics.areEqual(var2, this.addr(var1));
   }

   private final boolean isPairing(JSONObject var1) {
      int var2 = this.btPairingAddress.length();
      boolean var3 = true;
      boolean var5;
      if (var2 > 0) {
         var5 = true;
      } else {
         var5 = false;
      }

      if (var5) {
         String var4 = this.btPairingAddress.toUpperCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var4, "toUpperCase(...)");
         if (Intrinsics.areEqual(var4, this.addr(var1))) {
            return var3;
         }
      }

      return false;
   }

   private final boolean learningAt(int var1) {
      if (this.wcLearning) {
         Integer var3 = this.wcLearningSlot;
         if (var3 != null && var3 == var1) {
            return true;
         }
      }

      return false;
   }

   private final String listenLabel(int var1) {
      if (!this.learningAt(var1)) {
         return "Learn Button";
      } else {
         var1 = Math.max(0, (int)Math.ceil(this.wcRemaining));
         String var2;
         if (var1 > 0) {
            var2 = "Listening (" + var1 + "s)";
         } else {
            var2 = "Listening...";
         }

         return var2;
      }
   }

   private final List<JSONObject> mappingsOf(int var1) {
      Iterable var4 = this.wcMappings;
      var var5 = new ArrayList();

      for (Object var6 : var4) {
         var4 = (Iterable)((JSONObject)var6).opt("slot");
         Number var9;
         if (var4 instanceof Number) {
            var9 = (Number)var4;
         } else {
            var9 = null;
         }

         boolean var3 = false;
         boolean var2 = var3;
         if (var9 != null) {
            var2 = var3;
            if (var9.intValue() == var1) {
               var2 = true;
            }
         }

         if (var2) {
            var5.add(var6);
         }
      }

      return var5;
   }

   private final boolean needsPairValue(JSONObject var1) {
      boolean var2;
      if (var1 == null
         || !Intrinsics.areEqual(GxPage.str$default(this, var1, "kind", null, 2, null), "pin")
            && !Intrinsics.areEqual(GxPage.str$default(this, var1, "kind", null, 2, null), "passkey")) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   private final boolean offroadOk() {
      boolean var4 = this.btOffroad;
      boolean var3 = false;
      boolean var2 = var3;
      if (var4) {
         boolean var1;
         if (this.btBusy.length() == 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         var2 = var3;
         if (var1) {
            var2 = true;
         }
      }

      return var2;
   }

   private final void onActionSelect(int var1, String var2) {
      if (!this.wcDisabled()) {
         JSONObject var4 = this.optionByKey(var2);
         String var3;
         if (var4 != null) {
            var3 = GxPage.str$default(this, var4, "value_type", null, 2, null);
         } else {
            var3 = null;
         }

         if (Intrinsics.areEqual(var3, "speed")) {
            var3 = this.slotValue(CollectionsKt.getOrNull(this.wcControllerSlots, var1), var4);
         } else {
            var3 = (String)JSONObject.NULL;
         }

         Intrinsics.checkNotNull(var3);
         JSONObject var5 = new JSONObject().put("slot", var1).put("key", var2).put("value", var3);
         Intrinsics.checkNotNullExpressionValue(var5, "put(...)");
         this.wcRequest("action", var5);
      }
   }

   private final JSONObject optionByKey(String var1) {
      Iterator var4 = this.wcOptions.iterator();

      while (true) {
         if (var4.hasNext()) {
            Object var2 = var4.next();
            JSONObject var3 = (JSONObject)var2;
            if (!Intrinsics.areEqual(GxPage.str$default(this, var3, "key", null, 2, null), var1)) {
               continue;
            }

            var5 = var2;
            break;
         }

         var5 = null;
         break;
      }

      return (JSONObject)var5;
   }

   private final void paintPrompt() {
      LinearLayout var4 = this.btPromptBox;
      Object var3 = null;
      LinearLayout var2 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("btPromptBox");
         var2 = null;
      }

      var2.removeAllViews();
      this.btPairEdit = null;
      JSONObject var17 = this.btPrompt;
      if (var17 != null) {
         String var6 = GxPage.str$default(this, var17, "kind", null, 2, null);
         var2 = this.btPromptBox;
         if (var2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btPromptBox");
            var2 = (LinearLayout)var3;
         }

         LinearLayout var5 = this.card((ViewGroup)var2);
         var5.setBackground((Drawable)this.getUi().rounded(CarUi.Companion.getCARD_ON(), 14, CarUi.Companion.getACCENT()));
         GxPage var7 = this;
         var3 = GxPage.str$default(this, var17, "name", null, 2, null);
         Object var9 = var3;
         if (StringsKt.isBlank((CharSequence)var3)) {
            var9 = "Bluetooth device";
         }

         label53: {
            GxPage.text$default(var7, "Pairing request · " + var9, (ViewGroup)var5, 21.0F, CarUi.Companion.getTEXT(), false, 16, null);
            var3 = this;
            switch (var6.hashCode()) {
               case -1385570183:
                  if (var6.equals("authorization")) {
                     var9 = "Allow this device to connect?";
                     break label53;
                  }
                  break;
               case -792038226:
                  if (var6.equals("passkey")) {
                     var9 = "Enter the device passkey.";
                     break label53;
                  }
                  break;
               case 110997:
                  if (var6.equals("pin")) {
                     var9 = "Enter the PIN supplied by the device.";
                     break label53;
                  }
                  break;
               case 2099153973:
                  if (var6.equals("confirmation")) {
                     var9 = "Confirm the pairing request.";
                     break label53;
                  }
            }

            var9 = "Follow the instructions on the device.";
         }

         GxPage.text$default((GxPage)var3, (String)var9, (ViewGroup)var5, 17.0F, 0, false, 24, null);
         var9 = GxPage.str$default(this, var17, "value", null, 2, null);
         if (!StringsKt.isBlank((CharSequence)var9)) {
            this.text("Passkey: " + var9, (ViewGroup)var5, 24.0F, CarUi.Companion.getACCENT(), true);
         }

         if (this.needsPairValue(var17)) {
            var3 = this;
            if (Intrinsics.areEqual(var6, "pin")) {
               var9 = "PIN";
            } else {
               var9 = "Passkey";
            }

            var9 = GxVehicleKt.access$gxVehicleEdit((GxPage)var3, (String)var9, "", 2);
            var5.addView((View)var9, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, -2)));
            this.btPairEdit = (EditText)var9;
         }

         if (!var17.optBoolean("display_only")) {
            boolean var1 = Intrinsics.areEqual(this.btBusy, "pairing_response");
            GxVehicleKt.access$gxVehicleButtons(
               this,
               (ViewGroup)var5,
               CollectionsKt.listOf(
                  new Triple[]{
                     new Triple<>("Allow", var1 ^ true, new GxBluetoothPage$$ExternalSyntheticLambda20(this)),
                     new Triple<>("Cancel", var1 ^ true, new GxBluetoothPage$$ExternalSyntheticLambda21(this))
                  }
               ),
               true
            );
         }
      }
   }

   private static final Unit paintPrompt$lambda$22(GxBluetoothPage var0) {
      var0.respondPairing(true);
      return Unit.INSTANCE;
   }

   private static final Unit paintPrompt$lambda$23(GxBluetoothPage var0) {
      var0.respondPairing(false);
      return Unit.INSTANCE;
   }

   private final void pickAction(int var1) {
      if (!this.wcDisabled()) {
         List var6 = this.wcOptions;
         JSONObject var4 = CollectionsKt.getOrNull(this.wcControllerSlots, var1);
         JSONObject var3 = var4;
         if (var4 == null) {
            var3 = new JSONObject();
         }

         String var7 = GxPage.str$default(this, var3, "key", null, 2, null);
         Iterator var5 = var6.iterator();

         JSONObject var8;
         do {
            if (!var5.hasNext()) {
               var4 = null;
               break;
            }

            var4 = (JSONObject)var5.next();
            var8 = var4;
         } while (!Intrinsics.areEqual(GxPage.str$default(this, var8, "key", null, 2, null), var7));

         HashSet var9;
         TextView var10;
         JSONObject var11;
         label98: {
            var11 = var4;
            var9 = new HashSet();
            var38 = new LinearLayout((Context)this.getAct());
            var38.setOrientation(1);
            var38.setPadding(this.getUi().dp(22), this.getUi().dp(4), this.getUi().dp(22), 0);
            var10 = new TextView((Context)this.getAct());
            if (var11 != null) {
               String var29 = GxPage.str$default(this, var11, "label", null, 2, null);
               if (var29 != null) {
                  CharSequence var36 = var29;
                  CharSequence var30 = var36;
                  if (StringsKt.isBlank(var36)) {
                     var30 = null;
                  }

                  String var31 = (String)var30;
                  if (var31 != null) {
                     var19 = var31;
                     break label98;
                  }
               }
            }

            CharSequence var32 = GxPage.str$default(this, var3, "label", null, 2, null);
            var3 = var32;
            if (StringsKt.isBlank(var32)) {
               CharSequence var33 = var7;
               var3 = var33;
               if (StringsKt.isBlank(var33)) {
                  var3 = "Not configured";
               }

               var3 = (String)var3;
            }

            var19 = (String)var3;
         }

         boolean var2;
         if (var7.length() > 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         String var34;
         if (var2 && var11 == null) {
            var34 = " (unavailable)";
         } else {
            var34 = "";
         }

         var10.setText("Current: " + var19 + var34);
         var10.setTextColor(CarUi.Companion.getDIM());
         var10.setTextSize(16.0F);
         var38.addView((View)var10);
         EditText var40 = GxVehicleKt.gxVehicleEdit$default(this, "Search by name, setting or description", "", 0, 8, null);
         var38.addView((View)var40);
         TextView var12 = new TextView((Context)this.getAct());
         var12.setTextColor(CarUi.Companion.getDIM());
         var12.setTextSize(14.0F);
         var12.setPadding(0, this.getUi().dp(4), 0, this.getUi().dp(4));
         var38.addView((View)var12);
         LinearLayout var37 = new LinearLayout((Context)this.getAct());
         var37.setOrientation(1);
         ScrollView var35 = new ScrollView((Context)this.getAct());
         var35.addView((View)var37);
         var38.addView(
            (View)var35,
            (LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, (int)(this.getAct().getResources().getDisplayMetrics().heightPixels * 0.5)))
         );
         ObjectRef var39 = new ObjectRef();
         Iterable var13 = var6;
         var var20 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var13, 10));
         Iterator var41 = var13.iterator();

         while (var41.hasNext()) {
            var20.add(this.category((JSONObject)var41.next()));
         }

         List var21 = var20;
         Set var14 = CollectionsKt.toSet(var21);
         Iterable var22 = this.wcSectionOrder;
         var var42 = new ArrayList();

         for (Object var16 : var22) {
            if (var14.contains((String)var16)) {
               var42.add(var16);
            }
         }

         List var23 = var42;
         Collection var43 = var23;
         Iterable var24 = var6;
         var var44 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var24, 10));
         Iterator var45 = var24.iterator();

         while (var45.hasNext()) {
            var44.add(this.category((JSONObject)var45.next()));
         }

         List var25 = var44;
         List var26 = CollectionsKt.distinct(CollectionsKt.plus(var43, var25));
         var40.addTextChangedListener(
            new TextWatcher(var35, var37, var40, var6, var12, var26, var9, this, var7, var39, var1) {
               final List<String> $catOrder;
               final TextView $count;
               final ObjectRef<AlertDialog> $dialog;
               final HashSet<String> $expanded;
               final int $i;
               final String $key;
               final List<JSONObject> $options;
               final LinearLayout $results;
               final EditText $search;
               final ScrollView $sv;
               final GxBluetoothPage this$0;

               {
                  this.$sv = var1;
                  this.$results = var2x;
                  this.$search = var3x;
                  this.$options = var4x;
                  this.$count = var5x;
                  this.$catOrder = var6x;
                  this.$expanded = var7x;
                  this.this$0 = var8x;
                  this.$key = var9x;
                  this.$dialog = var10x;
                  this.$i = var11x;
               }

               public void afterTextChanged(Editable var1) {
                  GxBluetoothPage.pickAction$render(
                     this.$results, this.$search, this.$options, this.$count, this.$catOrder, this.$expanded, this.this$0, this.$key, this.$dialog, this.$i
                  );
                  this.$sv.scrollTo(0, 0);
               }

               public void beforeTextChanged(CharSequence var1, int var2x, int var3x, int var4x) {
               }

               public void onTextChanged(CharSequence var1, int var2x, int var3x, int var4x) {
               }
            }
         );
         var39.element = (T)new Builder((Context)this.getAct())
            .setTitle("Controller Action #" + (var1 + 1))
            .setView((View)var38)
            .setNegativeButton("Cancel", null)
            .create();
         pickAction$render(var37, var40, var6, var12, var26, var9, this, var7, var39, var1);
         AlertDialog var27;
         if (var39.element == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dialog");
            var27 = null;
         } else {
            var27 = (AlertDialog)var39.element;
         }

         var27.show();
      }
   }

   private static final View pickAction$optionButton(GxBluetoothPage var0, String var1, ObjectRef<AlertDialog> var2, int var3, JSONObject var4, String var5) {
      String var7;
      String var9;
      label44: {
         var9 = "";
         if (var4 != null) {
            var7 = GxPage.str$default(var0, var4, "key", null, 2, null);
            if (var7 != null) {
               break label44;
            }
         }

         var7 = "";
      }

      LinearLayout var10 = new LinearLayout((Context)var0.getAct());
      var10.setOrientation(1);
      var10.setPadding(var0.getUi().dp(16), var0.getUi().dp(10), var0.getUi().dp(16), var0.getUi().dp(10));
      var10.setMinimumHeight(var0.getUi().dp(60));
      CarUi var11 = var0.getUi();
      int var6;
      if (Intrinsics.areEqual(var7, var1)) {
         var6 = CarUi.Companion.getCARD_ON();
      } else {
         var6 = CarUi.Companion.getCARD();
      }

      Integer var8;
      if (Intrinsics.areEqual(var7, var1)) {
         var8 = CarUi.Companion.getACCENT();
      } else {
         var8 = null;
      }

      var10.setBackground((Drawable)var11.rounded(var6, 10, var8));
      TextView var17 = new TextView((Context)var0.getAct());
      String var16 = var9;
      if (Intrinsics.areEqual(var7, var1)) {
         var16 = "✓ ";
      }

      label38: {
         if (var4 != null) {
            var14 = GxPage.str$default(var0, var4, "label", null, 2, null);
            if (var14 != null) {
               break label38;
            }
         }

         var14 = "Not configured";
      }

      var17.setText(var16 + var14);
      if (Intrinsics.areEqual(var7, var1)) {
         var6 = CarUi.Companion.getACCENT();
      } else {
         var6 = CarUi.Companion.getTEXT();
      }

      var17.setTextColor(var6);
      var17.setTextSize(18.0F);
      var17.setTypeface(Typeface.DEFAULT_BOLD);
      var10.addView((View)var17);
      if (!StringsKt.isBlank(var5)) {
         TextView var12 = new TextView((Context)var0.getAct());
         var12.setText(var5);
         var12.setTextColor(CarUi.Companion.getDIM());
         var12.setTextSize(14.0F);
         var10.addView((View)var12);
      }

      var10.setOnClickListener(new GxBluetoothPage$$ExternalSyntheticLambda11(var2, var0, var3, var7));
      android.widget.LinearLayout.LayoutParams var13 = new android.widget.LinearLayout.LayoutParams(-1, -2);
      var13.bottomMargin = var0.getUi().dp(6);
      var10.setLayoutParams((LayoutParams)var13);
      return (View)var10;
   }

   private static final void pickAction$optionButton$lambda$88(ObjectRef var0, GxBluetoothPage var1, int var2, String var3, View var4) {
      AlertDialog var5;
      if (var0.element == null) {
         Intrinsics.throwUninitializedPropertyAccessException("dialog");
         var5 = null;
      } else {
         var5 = (AlertDialog)var0.element;
      }

      var5.dismiss();
      var1.onActionSelect(var2, var3);
   }

   private static final void pickAction$render(
      LinearLayout var0,
      EditText var1,
      List<? extends JSONObject> var2,
      TextView var3,
      List<String> var4,
      HashSet<String> var5,
      GxBluetoothPage var6,
      String var7,
      ObjectRef<AlertDialog> var8,
      int var9
   ) {
      var0.removeAllViews();
      String var20 = StringsKt.trim(var1.getText().toString()).toString().toLowerCase(Locale.ROOT);
      String var17 = "toLowerCase(...)";
      Intrinsics.checkNotNullExpressionValue(var20, "toLowerCase(...)");
      CharSequence var16 = var20;
      Regex var18 = new Regex("\\s+");
      int var11 = 0;
      Iterable var47 = var18.split(var16, 0);
      var var38 = new ArrayList();
      Iterator var48 = var47.iterator();

      while (true) {
         boolean var15 = var48.hasNext();
         byte var10 = 1;
         if (!var15) {
            List var22 = var38;
            Iterable var49 = var2;
            var var23 = new ArrayList();
            Iterable var39 = var49;
            Iterator var24 = var49.iterator();
            var10 = (byte)var11;

            while (var24.hasNext()) {
               Object var25 = var24.next();
               JSONObject var52 = (JSONObject)var25;
               String[] var21 = new String[6];
               var21[var10] = GxPage.str$default(var6, var52, "label", null, 2, null);
               var21[1] = GxPage.str$default(var6, var52, "key", null, 2, null);
               var21[2] = GxPage.str$default(var6, var52, "description", null, 2, null);
               var21[3] = GxPage.str$default(var6, var52, "picker_label", null, 2, null);
               var21[4] = GxPage.str$default(var6, var52, "picker_description", null, 2, null);
               var21[5] = var6.category(var52);
               String var27 = CollectionsKt.joinToString$default(CollectionsKt.listOf(var21), " ", null, null, 0, null, null, 62, null)
                  .toLowerCase(Locale.ROOT);
               Intrinsics.checkNotNullExpressionValue(var27, var17);
               var var60 = var22;
               boolean var33;
               Iterable var40;
               Iterable var43;
               String var50;
               if (var60 instanceof Collection && ((Collection)var60).isEmpty()) {
                  var33 = true;
                  var43 = var39;
                  var40 = var49;
                  var50 = var17;
               } else {
                  Iterator var26 = var60.iterator();

                  while (true) {
                     if (!var26.hasNext()) {
                        var50 = var17;
                        var43 = var39;
                        var33 = true;
                        var40 = var49;
                        break;
                     }

                     String var53 = (String)var26.next();
                     CharSequence var28 = var27;
                     CharSequence var29 = var53;
                     var10 = 0;
                     if (!StringsKt.contains$default(var28, var29, false, 2, null)) {
                        var33 = false;
                        var43 = var39;
                        var40 = var49;
                        var50 = var17;
                        break;
                     }

                     var10 = 0;
                     var17 = var17;
                  }
               }

               if (var33) {
                  var23.add(var25);
               }

               var17 = var50;
               var49 = var40;
               var39 = var43;
            }

            byte var12 = 6;
            List var51 = var23;
            int var14 = var51.size();
            var11 = var51.size();
            int var13 = 1;
            String var41;
            if (var11 == 1) {
               var41 = "action";
            } else {
               var41 = "actions";
            }

            var3.setText(var14 + " " + var41);
            var0.addView(pickAction$optionButton(var6, var7, var8, var9, null, "Clear this assignment"));
            Iterator var58 = var4.iterator();
            byte var35 = var10;
            String var42 = var20;
            var10 = (byte)var13;

            while (var58.hasNext()) {
               var20 = (String)var58.next();
               Iterable var61 = var51;
               var var44 = new ArrayList();

               for (Object var65 : var61) {
                  if (Intrinsics.areEqual(var6.category((JSONObject)var65), var20)) {
                     var44.add(var65);
                  }
               }

               List var63 = var44;
               if (!var63.isEmpty()) {
                  if (var42.length() > 0) {
                     var13 = var10;
                  } else {
                     var13 = var35;
                  }

                  if (var13 != 0 || var5.contains(var20)) {
                     var35 = var10;
                  }

                  TextView var66 = new TextView((Context)var6.getAct());
                  if (var35 != 0) {
                     var17 = "▾";
                  } else {
                     var17 = "▸";
                  }

                  var13 = var63.size();
                  var66.setText(var17 + "  " + var20 + " (" + var13 + ")");
                  var66.setTextColor(CarUi.Companion.getTEXT());
                  var66.setTextSize(18.0F);
                  var66.setTypeface(Typeface.DEFAULT_BOLD);
                  var66.setMinHeight(var6.getUi().dp(56));
                  var66.setGravity(16);
                  var66.setPadding(var6.getUi().dp(var12), var6.getUi().dp(8), var6.getUi().dp(var12), var6.getUi().dp(8));
                  var66.setOnClickListener(
                     new GxBluetoothPage$$ExternalSyntheticLambda3(var42, var5, var20, var0, var1, var2, var3, var4, var6, var7, var8, var9)
                  );
                  var0.addView((View)var66);
                  if (var35 != 0) {
                     for (JSONObject var46 : var63) {
                        String[] var67 = new String[]{var20, null};
                        var67[var10] = GxPage.str$default(var6, var46, "description", null, 2, null);
                        Iterable var70 = CollectionsKt.listOf(var67);
                        var var68 = new ArrayList();

                        for (Object var72 : var70) {
                           if (!StringsKt.isBlank((String & CharSequence)var72)) {
                              var68.add(var72);
                           }
                        }

                        var22 = var68;
                        var0.addView(
                           pickAction$optionButton(
                              var6, var7, var8, var9, var46, CollectionsKt.joinToString$default(var22, " · ", null, null, 0, null, null, 62, null)
                           )
                        );
                     }
                  }

                  var35 = 0;
               }
            }

            if (var51.isEmpty()) {
               TextView var30 = new TextView((Context)var6.getAct());
               var30.setText("No matching actions. Try another search or category.");
               var30.setTextColor(CarUi.Companion.getDIM());
               var30.setTextSize(16.0F);
               var30.setPadding(var35, var6.getUi().dp(10), var35, var6.getUi().dp(10));
               var0.addView((View)var30);
            }

            return;
         }

         Object var19 = var48.next();
         if (((String)var19).length() <= 0) {
            var10 = 0;
         }

         if (var10) {
            var38.add(var19);
         }
      }
   }

   private static final void pickAction$render$lambda$95$lambda$94(
      String var0,
      HashSet var1,
      String var2,
      LinearLayout var3,
      EditText var4,
      List var5,
      TextView var6,
      List var7,
      GxBluetoothPage var8,
      String var9,
      ObjectRef var10,
      int var11,
      View var12
   ) {
      boolean var13;
      if (var0.length() == 0) {
         var13 = true;
      } else {
         var13 = false;
      }

      if (var13) {
         if (var1.contains(var2)) {
            var1.remove(var2);
         } else {
            var1.add(var2);
         }

         pickAction$render(var3, var4, var5, var6, var7, var1, var8, var9, var10, var11);
      }
   }

   private final void respondPairing(boolean var1) {
      JSONObject var5 = this.btPrompt;
      if (var5 != null) {
         if (!Intrinsics.areEqual(this.btBusy, "pairing_response")) {
            String var8;
            label33: {
               EditText var3 = this.btPairEdit;
               if (var3 != null) {
                  Editable var6 = var3.getText();
                  if (var6 != null) {
                     var8 = var6.toString();
                     if (var8 != null) {
                        String var4 = StringsKt.trim(var8).toString();
                        var8 = var4;
                        if (var4 != null) {
                           break label33;
                        }
                     }
                  }
               }

               var8 = "";
            }

            if (var1 && this.needsPairValue(var5)) {
               boolean var2;
               if (var8.length() == 0) {
                  var2 = true;
               } else {
                  var2 = false;
               }

               if (var2) {
                  this.btError = "Enter the value to continue pairing.";
                  this.btPaint();
                  return;
               }
            }

            JSONObject var9 = new JSONObject().put("prompt_id", GxPage.str$default(this, var5, "id", null, 2, null)).put("accepted", var1).put("value", var8);
            Intrinsics.checkNotNullExpressionValue(var9, "put(...)");
            this.btRequest("pairing_response", var9);
         }
      }
   }

   private final LinearLayout slotCard(LinearLayout var1, String var2, String var3, String var4, boolean var5, Function0<Unit> var6) {
      var1 = this.card((ViewGroup)var1);
      LinearLayout var7 = new LinearLayout((Context)this.getAct());
      var7.setOrientation(0);
      var7.setGravity(16);
      LinearLayout var8 = new LinearLayout((Context)this.getAct());
      var8.setOrientation(1);
      TextView var9 = new TextView((Context)this.getAct());
      var9.setText(var2);
      var9.setTextColor(CarUi.Companion.getDIM());
      var9.setTextSize(14.0F);
      var8.addView((View)var9);
      TextView var11 = new TextView((Context)this.getAct());
      var11.setText(var3);
      var11.setTextColor(CarUi.Companion.getTEXT());
      var11.setTextSize(21.0F);
      var8.addView((View)var11);
      var7.addView((View)var8, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
      if (var4 != null) {
         TextView var12 = this.getUi().button(var4, Intrinsics.areEqual(var4, "Learn Button") ^ true, new GxBluetoothPage$$ExternalSyntheticLambda27(var6));
         GxVehicleKt.access$gxVehicleEnable((View)var12, var5);
         var7.addView((View)var12);
      }

      View var13 = (View)var7;
      android.widget.LinearLayout.LayoutParams var15 = new android.widget.LinearLayout.LayoutParams(-1, -2);
      var15.bottomMargin = this.getUi().dp(8);
      Unit var14 = Unit.INSTANCE;
      var1.addView(var13, (LayoutParams)var15);
      return var1;
   }

   private static final Unit slotCard$lambda$64(Function0 var0) {
      var0.invoke();
      return Unit.INSTANCE;
   }

   private final void slotTail(LinearLayout var1, int var2) {
      if (this.learningAt(var2)) {
         this.text("Press one button on your controller, macropad, or keyboard.", (ViewGroup)var1, 17.0F, CarUi.Companion.getACCENT(), true);
      }

      List var5 = this.mappingsOf(var2);
      if (var5.isEmpty()) {
         GxPage.text$default(this, "No buttons mapped.", (ViewGroup)var1, 15.0F, CarUi.Companion.getDIM(), false, 16, null);
      } else {
         for (JSONObject var8 : var5) {
            LinearLayout var6 = new LinearLayout((Context)this.getAct());
            boolean var4 = false;
            var6.setOrientation(0);
            var6.setGravity(16);
            var6.setPadding(this.getUi().dp(12), this.getUi().dp(6), this.getUi().dp(6), this.getUi().dp(6));
            var6.setBackground((Drawable)CarUi.rounded$default(this.getUi(), CarUi.Companion.getCARD_ON(), 10, null, 4, null));
            LinearLayout var7 = new LinearLayout((Context)this.getAct());
            var7.setOrientation(1);
            TextView var9 = new TextView((Context)this.getAct());
            var9.setText(this.eventName(var8));
            var9.setTextColor(CarUi.Companion.getTEXT());
            var9.setTextSize(18.0F);
            var9.setTypeface(Typeface.DEFAULT_BOLD);
            var7.addView((View)var9);
            String var10 = GxPage.str$default(this, var8, "device_name", null, 2, null);
            if (!StringsKt.isBlank(var10)) {
               var9 = new TextView((Context)this.getAct());
               var9.setText(var10);
               var9.setTextColor(CarUi.Companion.getDIM());
               var9.setTextSize(14.0F);
               var7.addView((View)var9);
            }

            var6.addView((View)var7, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
            TextView var15 = CarUi.button$default(this.getUi(), "Remove", false, new GxBluetoothPage$$ExternalSyntheticLambda4(this, var8), 2, null);
            View var13 = (View)var15;
            boolean var3 = var4;
            if (!this.wcDisabled()) {
               var3 = var4;
               if (!this.wcTesting) {
                  var3 = true;
               }
            }

            GxVehicleKt.access$gxVehicleEnable(var13, var3);
            var6.addView((View)var15);
            View var14 = (View)var6;
            android.widget.LinearLayout.LayoutParams var16 = new android.widget.LinearLayout.LayoutParams(-1, -2);
            var16.topMargin = this.getUi().dp(6);
            Unit var12 = Unit.INSTANCE;
            var1.addView(var14, (LayoutParams)var16);
         }
      }
   }

   private static final Unit slotTail$lambda$70(GxBluetoothPage var0, JSONObject var1) {
      var1 = new JSONObject().put("id", GxPage.str$default(var0, var1, "id", null, 2, null));
      Intrinsics.checkNotNullExpressionValue(var1, "put(...)");
      var0.wcRequest("delete", var1);
      return Unit.INSTANCE;
   }

   private final double slotValue(JSONObject var1, JSONObject var2) {
      if (var1 != null && var1.has("value") && !var1.isNull("value")) {
         double var3 = var1.optDouble("value");
         if (!Double.isNaN(var3)) {
            return var3;
         }
      }

      if (var2 != null && var2.has("default_value") && !var2.isNull("default_value")) {
         double var5 = var2.optDouble("default_value");
         if (!Double.isNaN(var5)) {
            return var5;
         }
      }

      return 30.0;
   }

   private final String statusOf(JSONObject var1) {
      String var2;
      if (this.isPairing(var1)) {
         var2 = "Pairing…";
      } else if (var1.optBoolean("connected")) {
         if (this.isAudioSel(var1)) {
            var2 = "Connected · Audio output";
         } else {
            var2 = "Connected";
         }
      } else if (var1.optBoolean("paired")) {
         var2 = "Saved";
      } else {
         var2 = "Ready to pair";
      }

      return var2;
   }

   private final String targetName(int var1) {
      StringBuilder var2;
      String var3;
      if (var1 < 3) {
         var1++;
         var2 = new StringBuilder();
         var3 = "Favorite #";
      } else {
         var1 -= 2;
         var2 = new StringBuilder();
         var3 = "Controller Action #";
      }

      return var2.append(var3).append(var1).toString();
   }

   private final boolean wcDisabled() {
      boolean var4 = this.wcOffroad;
      boolean var3 = true;
      boolean var2 = var3;
      if (var4) {
         boolean var1;
         if (this.wcBusy.length() > 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         if (var1) {
            var2 = var3;
         } else {
            var2 = false;
         }
      }

      return var2;
   }

   private final void wcPaint() {
      LinearLayout var10 = this.wcBox;
      if (var10 != null) {
         String var11 = this.wcRaw;
         String var8 = this.wcBusy;
         String var9 = this.wcError;
         boolean var5 = this.wcLoaded;
         var11 = var11 + "|" + var8 + "|" + var9 + "|" + var5;
         if (!Intrinsics.areEqual(var11, this.wcSig)) {
            this.wcSig = var11;
            var10.removeAllViews();
            if (!this.wcLoaded) {
               var10.addView((View)this.empty("Loading..."));
            } else {
               if (!this.wcOffroad) {
                  GxVehicleKt.access$gxVehicleNotice(
                     this, "Mappings can only be changed while offroad. Mapped buttons continue working onroad.", CarUi.Companion.getWARN(), (ViewGroup)var10
                  );
               }

               if (!StringsKt.isBlank(this.wcError)) {
                  GxVehicleKt.access$gxVehicleNotice(this, this.wcError, GxVehicleKt.access$getGX_VEHICLE_ERR$p(), (ViewGroup)var10);
               }

               if (!this.wcAvailable && this.wcMappings.isEmpty()) {
                  this.note("The wheel control service is starting.", (ViewGroup)var10);
               }

               if (!this.wcDisabled() && !this.wcMappings.isEmpty()) {
                  var5 = true;
               } else {
                  var5 = false;
               }

               GxPage var12 = this;
               ViewGroup var57 = (ViewGroup)var10;
               if (this.wcTesting) {
                  var8 = "Stop Testing";
               } else {
                  var8 = "Test Buttons";
               }

               Triple var13 = new Triple<>(var8, var5, new GxBluetoothPage$$ExternalSyntheticLambda29(this));
               Triple var27 = new Triple<>("Clear All", var5, new GxBluetoothPage$$ExternalSyntheticLambda30(this));
               List var28 = CollectionsKt.listOf(new Triple[]{var13, var27});
               GxVehicleKt.access$gxVehicleButtons(var12, var57, var28, true);
               if (this.wcTesting) {
                  LinearLayout var81 = this.card((ViewGroup)var10);
                  JSONObject var14 = this.wcLastTested;
                  Integer var29;
                  if (var14 == null) {
                     var29 = CarUi.Companion.getDIM();
                     var9 = "Waiting for a button";
                  } else if (var14.optBoolean("mapped")) {
                     var29 = CarUi.Companion.getACCENT();
                     var9 = "Successful";
                  } else {
                     var29 = GxVehicleKt.access$getGX_VEHICLE_ERR$p();
                     var9 = "Not mapped";
                  }

                  Pair var30 = TuplesKt.to(var9, var29);
                  var9 = (String)var30.component1();
                  int var3 = ((Number)var30.component2()).intValue();
                  var81.setBackground((Drawable)this.getUi().rounded(CarUi.Companion.getCARD(), 14, var3));
                  this.text(var9, (ViewGroup)var81, 21.0F, var3, true);
                  GxPage var86 = this;
                  if (var14 == null) {
                     var8 = "Press a button to verify its mapping. Controller inputs are temporarily consumed while testing is enabled.";
                  } else {
                     CharSequence var60 = GxPage.str$default(this, var14, "device_name", null, 2, null);
                     var8 = var60;
                     if (StringsKt.isBlank(var60)) {
                        var8 = "External input";
                     }

                     String var15 = var8;
                     var9 = this.eventName(var14);
                     StringBuilder var62;
                     if (var14.optBoolean("mapped")) {
                        var8 = (String)var14.opt("slot");
                        Number var34;
                        if (var8 instanceof Number) {
                           var34 = (Number)var8;
                        } else {
                           var34 = null;
                        }

                        if (var34 != null) {
                           var3 = var34.intValue();
                        } else {
                           var3 = 0;
                        }

                        var8 = this.targetName(var3);
                        var62 = new StringBuilder().append(var9).append(" on ").append(var15).append(" is mapped to ").append(var8);
                        var8 = ".";
                     } else {
                        var62 = new StringBuilder().append(var9).append(" on ").append(var15);
                        var8 = " does not have a mapping.";
                     }

                     var8 = var62.append(var8).toString();
                  }

                  GxPage.text$default(var86, var8, (ViewGroup)var81, 17.0F, 0, false, 24, null);
               }

               GxVehicleKt.access$gxVehicleEnable(
                  (View & Switch)this.toggle(
                        "Disconnect controllers when offroad",
                        "After two minutes offroad, paired controllers disconnect to save battery and reconnect when the car starts. Bluetooth and audio-only devices stay connected.",
                        this.wcDisconnectOffroad,
                        (ViewGroup)var10,
                        new GxBluetoothPage$$ExternalSyntheticLambda31(this)
                     )
                     .component2(),
                  this.wcDisabled() ^ true
               );
               this.header("Connected input devices", (ViewGroup)var10);
               this.note(
                  "Favorite buttons are the default, with controller-only actions available below. Only the selected gamepad controls Joystick Mode.",
                  (ViewGroup)var10
               );
               if (this.wcDevices.isEmpty()) {
                  this.note("Connect or pair a controller, macropad, or keyboard.", (ViewGroup)var10);
               }

               for (JSONObject var87 : this.wcDevices) {
                  boolean var6 = var87.optBoolean("joystick_capable");
                  GxPage var92 = this;
                  CharSequence var63 = GxPage.str$default(this, var87, "name", null, 2, null);
                  var8 = var63;
                  if (StringsKt.isBlank(var63)) {
                     var8 = GxPage.str$default(this, var87, "device_id", null, 2, null);
                  }

                  var9 = var8;
                  if (var6) {
                     var8 = "Buttons and joystick axes";
                  } else {
                     var8 = "Buttons only";
                  }

                  GxPage.Row var65 = GxPage.row$default(var92, var9, var8, (ViewGroup)var10, 0, 8, null);
                  if (var6) {
                     String var88 = GxPage.str$default(this, var87, "device_id", null, 2, null);
                     var6 = Intrinsics.areEqual(var88, this.wcJoystick);
                     if (var6) {
                        var8 = "Enabled for Joystick Mode";
                     } else {
                        var8 = "Enable for Joystick Mode";
                     }

                     GxVehicleKt.access$gxVehicleEnable(
                        (View)var65.button(var8, var6, new GxBluetoothPage$$ExternalSyntheticLambda32(this, var88, var6)), this.wcDisabled() ^ true
                     );
                  }
               }

               this.header("On-screen Favorites", (ViewGroup)var10);
               this.note("These remain linked to the three Favorite Slots shown elsewhere in StarPilot.", (ViewGroup)var10);
               Iterator var83 = this.wcSlots.iterator();

               for (int var18 = 0; var83.hasNext(); var18++) {
                  JSONObject var93 = (JSONObject)var83.next();
                  boolean var7 = this.configured(var93);
                  String var89 = "Favorite #" + (var18 + 1);
                  if (var7) {
                     CharSequence var66 = GxPage.str$default(this, var93, "label", null, 2, null);
                     var8 = var66;
                     if (StringsKt.isBlank(var66)) {
                        var8 = GxPage.str$default(this, var93, "key", null, 2, null);
                     }

                     var8 = var8;
                  } else {
                     var8 = "Not configured";
                  }

                  if (var7) {
                     var9 = this.listenLabel(var18);
                  } else {
                     var9 = null;
                  }

                  boolean var23;
                  if (var7 && !this.wcDisabled() && !this.wcTesting) {
                     var23 = true;
                  } else {
                     var23 = false;
                  }

                  LinearLayout var68 = this.slotCard(var10, var89, var8, var9, var23, new GxBluetoothPage$$ExternalSyntheticLambda33(this, var18));
                  GxPage var90 = this;
                  if (var7) {
                     var8 = "Pressing any mapped button will run this favorite.";
                  } else {
                     var8 = "Choose and enable this slot in Toggles → Favorites.";
                  }

                  GxPage.text$default(var90, var8, (ViewGroup)var68, 0.0F, 0, false, 28, null);
                  this.slotTail(var68, var18);
               }

               var var43 = CollectionsKt.take(this.wcSlots, 3);
               boolean var19;
               if (var43 instanceof Collection && ((Collection)var43).isEmpty()) {
                  var19 = true;
               } else {
                  Iterator var44 = var43.iterator();

                  while (true) {
                     if (!var44.hasNext()) {
                        var19 = true;
                        break;
                     }

                     if (this.configured((JSONObject)var44.next())) {
                        var19 = false;
                        break;
                     }
                  }
               }

               if (var19) {
                  this.note("Choose and enable these slots in Toggles to map buttons to them.", (ViewGroup)var10);
                  var12 = this;
                  Pair var69 = TuplesKt.to("Open Favorites", new GxBluetoothPage$$ExternalSyntheticLambda34(this));
                  ViewGroup var45 = (ViewGroup)var10;
                  GxPage.buttons$default(var12, new Pair[]{var69}, var45, false, 4, null);
               }

               this.header("Controller-only Actions", (ViewGroup)var10);
               this.note("Ten additional actions for physical buttons. These never appear as on-screen Favorites.", (ViewGroup)var10);
               Iterator var85 = this.wcControllerSlots.iterator();

               for (int var20 = 0; var85.hasNext(); var20++) {
                  JSONObject var96 = (JSONObject)var85.next();
                  int var4 = var20 + 3;
                  String var91 = GxPage.str$default(this, var96, "key", null, 2, null);
                  JSONObject var16 = this.optionByKey(var91);
                  String var94 = "Controller Action #" + (var20 + 1);
                  if (var96.optBoolean("enabled")) {
                     CharSequence var71 = GxPage.str$default(this, var96, "label", null, 2, null);
                     var8 = var71;
                     if (StringsKt.isBlank(var71)) {
                        var8 = "Configured";
                     }

                     var8 = var8;
                  } else {
                     var8 = "Not configured";
                  }

                  var11 = this.listenLabel(var4);
                  boolean var24;
                  if (var96.optBoolean("enabled") && !this.wcDisabled() && !this.wcTesting) {
                     var24 = true;
                  } else {
                     var24 = false;
                  }

                  label249: {
                     var95 = this.slotCard(var10, var94, var8, var11, var24, new GxBluetoothPage$$ExternalSyntheticLambda35(this, var4));
                     if (var16 != null) {
                        var8 = GxPage.str$default(this, var16, "label", null, 2, null);
                        if (var8 != null) {
                           CharSequence var73 = var8;
                           CharSequence var49 = var73;
                           if (StringsKt.isBlank(var73)) {
                              var49 = null;
                           }

                           var8 = (String)var49;
                           if (var8 != null) {
                              break label249;
                           }
                        }
                     }

                     CharSequence var74 = GxPage.str$default(this, var96, "label", null, 2, null);
                     var8 = var74;
                     if (StringsKt.isBlank(var74)) {
                        CharSequence var75 = var91;
                        var8 = var75;
                        if (StringsKt.isBlank(var75)) {
                           var8 = "Not configured";
                        }

                        var8 = var8;
                     }

                     var8 = var8;
                  }

                  GxVehicleKt.gxVehicleButtons$default(
                     this,
                     (ViewGroup)var95,
                     CollectionsKt.listOf(
                        new Triple<>(var8 + " · Choose action", this.wcDisabled() ^ true, new GxBluetoothPage$$ExternalSyntheticLambda1(this, var20))
                     ),
                     false,
                     8,
                     null
                  );
                  if (var16 != null) {
                     var8 = GxPage.str$default(this, var16, "value_type", null, 2, null);
                  } else {
                     var8 = null;
                  }

                  var24 = Intrinsics.areEqual(var8, "speed");
                  if (var24) {
                     double var1 = this.slotValue(var96, var16);
                     LinearLayout var54 = new LinearLayout((Context)this.getAct());
                     var54.setOrientation(0);
                     var54.setGravity(16);
                     TextView var76 = new TextView((Context)this.getAct());
                     String var97 = this.wcSpeedUnit;
                     var76.setText("Set speed (" + var97 + ")");
                     var76.setTextColor(CarUi.Companion.getTEXT());
                     var76.setTextSize(18.0F);
                     var54.addView((View)var76, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
                     TextView var77 = new TextView((Context)this.getAct());
                     var77.setText(this.fmtNum(var1));
                     var77.setTextColor(CarUi.Companion.getTEXT());
                     var77.setTextSize(22.0F);
                     var77.setTypeface(Typeface.DEFAULT_BOLD);
                     var77.setPadding(this.getUi().dp(12), 0, this.getUi().dp(12), 0);
                     var54.addView((View)var77);
                     TextView var78 = CarUi.button$default(
                        this.getUi(), "Change", false, new GxBluetoothPage$$ExternalSyntheticLambda2(this, var1, var20, var91), 2, null
                     );
                     GxVehicleKt.access$gxVehicleEnable((View)var78, this.wcDisabled() ^ true);
                     var54.addView((View)var78);
                     View var98 = (View)var54;
                     android.widget.LinearLayout.LayoutParams var55 = new android.widget.LinearLayout.LayoutParams(-1, -2);
                     var55.bottomMargin = this.getUi().dp(6);
                     Unit var79 = Unit.INSTANCE;
                     var95.addView(var98, (LayoutParams)var55);
                  }

                  GxPage var80 = this;
                  if (var24) {
                     var8 = "Uses the current mph/km/h setting and applies while openpilot is engaged on software-controlled cruise.";
                  } else if (Intrinsics.areEqual(var91, "__starpilot_controller_action__:selfie")) {
                     var8 = "Captures in the background and saves the driver-camera image in Galaxy → Sentry Mode.";
                  } else {
                     var8 = "Controller-only action. It does not create an on-screen Favorite button.";
                  }

                  GxPage.text$default(var80, var8, (ViewGroup)var95, 0.0F, 0, false, 28, null);
                  this.slotTail(var95, var4);
               }
            }
         }
      }
   }

   private static final Unit wcPaint$lambda$37(GxBluetoothPage var0) {
      String var1;
      if (var0.wcTesting) {
         var1 = "test-stop";
      } else {
         var1 = "test";
      }

      wcRequest$default(var0, var1, null, 2, null);
      return Unit.INSTANCE;
   }

   private static final Unit wcPaint$lambda$39(GxBluetoothPage var0) {
      var0.confirm("Clear All", "Remove every controller mapping?", "Clear All", new GxBluetoothPage$$ExternalSyntheticLambda5(var0));
      return Unit.INSTANCE;
   }

   private static final Unit wcPaint$lambda$39$lambda$38(GxBluetoothPage var0) {
      wcRequest$default(var0, "clear", null, 2, null);
      return Unit.INSTANCE;
   }

   private static final Unit wcPaint$lambda$41(GxBluetoothPage var0, boolean var1) {
      JSONObject var2 = new JSONObject().put("enabled", var1);
      Intrinsics.checkNotNullExpressionValue(var2, "put(...)");
      var0.wcRequest("offroad-disconnect", var2);
      return Unit.INSTANCE;
   }

   private static final Unit wcPaint$lambda$43(GxBluetoothPage var0, String var1, boolean var2) {
      JSONObject var3 = new JSONObject().put("device_id", var1).put("enabled", var2 ^ true);
      Intrinsics.checkNotNullExpressionValue(var3, "put(...)");
      var0.wcRequest("joystick", var3);
      return Unit.INSTANCE;
   }

   private static final Unit wcPaint$lambda$45(GxBluetoothPage var0, int var1) {
      String var2;
      if (var0.learningAt(var1)) {
         var2 = "cancel";
      } else {
         var2 = "learn";
      }

      JSONObject var3 = new JSONObject().put("slot", var1);
      Intrinsics.checkNotNullExpressionValue(var3, "put(...)");
      var0.wcRequest(var2, var3);
      return Unit.INSTANCE;
   }

   private static final Unit wcPaint$lambda$47(GxBluetoothPage var0) {
      var0.getGx().openPage("gx:favorites");
      return Unit.INSTANCE;
   }

   private static final Unit wcPaint$lambda$49(GxBluetoothPage var0, int var1) {
      String var2;
      if (var0.learningAt(var1)) {
         var2 = "cancel";
      } else {
         var2 = "learn";
      }

      JSONObject var3 = new JSONObject().put("slot", var1);
      Intrinsics.checkNotNullExpressionValue(var3, "put(...)");
      var0.wcRequest(var2, var3);
      return Unit.INSTANCE;
   }

   private static final Unit wcPaint$lambda$53(GxBluetoothPage var0, int var1) {
      var0.pickAction(var1);
      return Unit.INSTANCE;
   }

   private static final Unit wcPaint$lambda$58(GxBluetoothPage var0, double var1, int var3, String var4) {
      if (var0.wcDisabled()) {
         return Unit.INSTANCE;
      } else {
         GxPage var5 = var0;
         String var6 = var0.wcSpeedUnit;
         var6 = "Set speed (" + var6 + ")";
         String var8 = var0.fmtNum(var0.wcSpeedMin);
         String var7 = var0.fmtNum(var0.wcSpeedMax);
         GxPage.prompt$default(
            var5, var6, var8 + " – " + var7, var0.fmtNum(var1), 8194, null, new GxBluetoothPage$$ExternalSyntheticLambda26(var0, var3, var4), 16, null
         );
         return Unit.INSTANCE;
      }
   }

   private static final Unit wcPaint$lambda$58$lambda$57(GxBluetoothPage var0, int var1, String var2, String var3) {
      Intrinsics.checkNotNullParameter(var3, "t");
      Double var5 = StringsKt.toDoubleOrNull(StringsKt.trim(var3).toString());
      if (var5 != null && !Double.isNaN(var5) && !Double.isInfinite(var5)) {
         JSONObject var4 = new JSONObject().put("slot", var1).put("key", var2).put("value", var5);
         Intrinsics.checkNotNullExpressionValue(var4, "put(...)");
         var0.wcRequest("action", var4);
         return Unit.INSTANCE;
      } else {
         var0.toast("Enter a valid set speed.");
         return Unit.INSTANCE;
      }
   }

   private final void wcRefresh() {
      if (!this.wcInflight) {
         this.wcInflight = true;
         this.getJson("/api/wheel-controls/status", new GxBluetoothPage$$ExternalSyntheticLambda25(this));
      }
   }

   private static final Unit wcRefresh$lambda$32(GxBluetoothPage var0, String var1, JSONObject var2) {
      boolean var10 = false;
      var0.wcInflight = false;
      if (var1 == null && var2 != null) {
         label102: {
            var0.wcRaw = var2.toString();
            var0.wcAvailable = var2.optBoolean("available");
            var0.wcOffroad = var2.optBoolean("offroad");
            var0.wcLearning = var2.optBoolean("learning");
            JSONArray var14 = var2.optJSONArray("devices");
            if (var14 != null) {
               List var12 = var0.objects(var14);
               var15 = var12;
               if (var12 != null) {
                  break label102;
               }
            }

            var15 = CollectionsKt.emptyList();
         }

         label97: {
            var0.wcDevices = var15;
            JSONArray var16 = var2.optJSONArray("mappings");
            if (var16 != null) {
               List var35 = var0.objects(var16);
               var17 = var35;
               if (var35 != null) {
                  break label97;
               }
            }

            var17 = CollectionsKt.emptyList();
         }

         label92: {
            var0.wcMappings = var17;
            JSONArray var18 = var2.optJSONArray("slots");
            if (var18 != null) {
               List var36 = var0.objects(var18);
               var19 = var36;
               if (var36 != null) {
                  break label92;
               }
            }

            var19 = CollectionsKt.emptyList();
         }

         label87: {
            var0.wcSlots = var19;
            JSONArray var20 = var2.optJSONArray("controller_slots");
            if (var20 != null) {
               List var37 = var0.objects(var20);
               var21 = var37;
               if (var37 != null) {
                  break label87;
               }
            }

            var21 = CollectionsKt.emptyList();
         }

         label82: {
            var0.wcControllerSlots = var21;
            JSONArray var22 = var2.optJSONArray("controller_options");
            if (var22 != null) {
               List var38 = var0.objects(var22);
               var23 = var38;
               if (var38 != null) {
                  break label82;
               }
            }

            var23 = CollectionsKt.emptyList();
         }

         var0.wcOptions = var23;
         var0.wcDisconnectOffroad = var2.optBoolean("disconnect_controllers_offroad");
         Object var24 = var2.opt("joystick_device");
         boolean var11 = var24 instanceof String;
         Object var13 = null;
         if (var11) {
            var24 = (String)var24;
         } else {
            var24 = null;
         }

         String var39 = (String)var24;
         if (var24 == null) {
            var39 = "";
         }

         var0.wcJoystick = var39;
         var24 = var2.opt("learning_slot");
         if (var24 instanceof Number) {
            var24 = (Number)var24;
         } else {
            var24 = null;
         }

         Integer var40 = (Integer)var13;
         if (var24 != null) {
            var40 = var24.intValue();
         }

         var0.wcLearningSlot = var40;
         double var5 = var2.optDouble("remaining_seconds", 0.0);
         double var3 = var5;
         if (Double.isNaN(var5)) {
            var3 = 0.0;
         }

         var0.wcRemaining = var3;
         var0.wcTesting = var2.optBoolean("testing");
         var0.wcLastTested = var2.optJSONObject("last_tested");
         var24 = "mph";
         CharSequence var41 = var0.str(var2, "speed_unit", "mph");
         if (!StringsKt.isBlank(var41)) {
            var24 = var41;
         }

         var0.wcSpeedUnit = (String)var24;
         double var7 = 5.0;
         var5 = var2.optDouble("speed_minimum", 5.0);
         var3 = var7;
         if (!Double.isNaN(var5)) {
            boolean var9;
            if (var5 == 0.0) {
               var9 = true;
            } else {
               var9 = false;
            }

            if (var9) {
               var3 = var7;
            } else {
               var3 = var5;
            }
         }

         var0.wcSpeedMin = var3;
         var7 = 90.0;
         var5 = var2.optDouble("speed_maximum", 90.0);
         var3 = var7;
         if (!Double.isNaN(var5)) {
            boolean var34 = var10;
            if (var5 == 0.0) {
               var34 = true;
            }

            if (var34) {
               var3 = var7;
            } else {
               var3 = var5;
            }
         }

         var0.wcSpeedMax = var3;
         var0.wcError = "";
      } else {
         var0.wcAvailable = false;
         if (var1 == null) {
            var1 = "Wheel controls are unavailable";
         }

         var0.wcError = var1;
      }

      var0.wcLoaded = true;
      var0.wcPaint();
      return Unit.INSTANCE;
   }

   private final void wcRequest(String var1, JSONObject var2) {
      boolean var3;
      if (this.wcBusy.length() > 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (var3) {
         this.wcSig = "";
         this.wcPaint();
      } else {
         this.wcBusy = var1;
         this.wcPaint();
         GxPage.send$default(this, "POST", "/api/wheel-controls/" + var1, var2, 0L, new GxBluetoothPage$$ExternalSyntheticLambda28(this), 8, null);
      }
   }

   // $VF: synthetic method
   static void wcRequest$default(GxBluetoothPage var0, String var1, JSONObject var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = new JSONObject();
      }

      var0.wcRequest(var1, var2);
   }

   private static final Unit wcRequest$lambda$33(GxBluetoothPage var0, String var1, JSONObject var2) {
      var0.wcBusy = "";
      if (var1 != null) {
         var0.wcError = var1;
         var0.toast(var1);
      } else {
         var0.wcError = "";
      }

      var0.wcSig = "";
      var0.wcPaint();
      if (var1 == null) {
         var0.wcRefresh();
      }

      return Unit.INSTANCE;
   }

   @Override
   public void build() {
      GxPage.tabs$default(
         this,
         CollectionsKt.listOf(new String[]{"Bluetooth", "Controllers"}),
         0,
         new GxBluetoothPage$$ExternalSyntheticLambda16(this),
         new GxBluetoothPage$$ExternalSyntheticLambda17(this),
         2,
         null
      );
      this.every(2000L, new GxBluetoothPage$$ExternalSyntheticLambda18(this));
      this.every(1000L, new GxBluetoothPage$$ExternalSyntheticLambda19(this));
   }

   @Override
   public boolean getNeedsOffroad() {
      return this.needsOffroad;
   }
}
