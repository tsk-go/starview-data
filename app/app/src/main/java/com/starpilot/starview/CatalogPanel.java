package com.starpilot.starview;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Pair;
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
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.jvm.internal.Ref.IntRef;
import kotlin.jvm.internal.Ref.ObjectRef;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.json.JSONObject;

public final class CatalogPanel extends ScrollView {
   public static final CatalogPanel.Companion Companion = new CatalogPanel.Companion(null);
   private static final List<String> GM = CollectionsKt.listOf(new String[]{"Buick", "Cadillac", "Chevrolet", "GMC", "Holden"});
   private static final Set<String> HIDDEN = SetsKt.setOf(
      new String[]{
         "AccelerationProfile",
         "AggressiveFollow",
         "AggressiveFollowHigh",
         "CustomAccelProfile",
         "CustomAccelProfile0MPH",
         "CustomAccelProfile11MPH",
         "CustomAccelProfile22MPH",
         "CustomAccelProfile34MPH",
         "CustomAccelProfile45MPH",
         "CustomAccelProfile56MPH",
         "CustomAccelProfile89MPH",
         "DecelerationProfile",
         "EVTuning",
         "HumanAcceleration",
         "RelaxedFollow",
         "RelaxedFollowHigh",
         "StandardFollow",
         "StandardFollowHigh",
         "TrafficFollow",
         "TruckTuning",
         "TrafficPersonalityProfile",
         "AggressivePersonalityProfile",
         "StandardPersonalityProfile",
         "RelaxedPersonalityProfile",
         "AlphaLongitudinalEnabled",
         "ForceOffroad",
         "GsmMetered",
         "IsRHD"
      }
   );
   private static final List<String> HKG = CollectionsKt.listOf(new String[]{"Genesis", "Hyundai", "Kia"});
   private static final List<Pair<String, List<String>>> PREFIX_MAKES;
   private static final Set<String> RADAR_REQUIRED = SetsKt.setOf(new String[]{"HumanLaneChanges", "RadarTakeoffs"});
   private static final List<String> SPEED_RANGES_IMP = CollectionsKt.listOf(new String[]{"0–24", "25–34", "35–44", "45–54", "55–64", "65–74", "75–99"});
   private static final List<String> SPEED_RANGES_MET = CollectionsKt.listOf(new String[]{"0–29", "30–49", "50–59", "60–79", "80–99", "100–119", "120–140"});
   private static final Map<String, List<String>> VEHICLE_MAKES;
   private final Activity act;
   private boolean advanced;
   private final GalaxyApi api;
   private final Map<String, GalaxyApi.Param> byKey;
   private final Map<String, List<GalaxyApi.Param>> children;
   private final LinearLayout col;
   private View emptyNote;
   private String lastSig;
   private final boolean needsOffroad;
   private final Function1<String, Unit> openWeb;
   private final HashMap<String, List<Pair<String, String>>> optionCache;
   private final List<GalaxyApi.Param> params;
   private final ArrayList<CatalogPanel.Row> rows;
   private final String sectionName;
   private boolean syncing;
   private final CarUi ui;

   // $VF: synthetic method
   public static void $r8$lambda$0MMPbl99rxsp9cOWduBh7gEvTfs(Function1 var0, DialogInterface var1, int var2) {
      addDropdown$rebuild$lambda$55$lambda$54(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$1Fxcxk0lNUIoBrG4b39P_WrnaVM(LinearLayout var0, CatalogPanel var1, GalaxyApi.Param var2, Switch var3, TextView var4) {
      return addToggle$lambda$32(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$4DTje3wlSdM4RAPkCrC8gezdc_A(CatalogPanel var0, String var1) {
      return addToggle$lambda$31$lambda$30(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$79vPK_XDOGpPWPyLs_7_WM5eKqE/* $VF was: $r8$lambda$79vPK_XDOGpPWPyLs_7_WM5eKqE*/(
      TextView var0, ObjectRef var1, CatalogPanel var2, GalaxyApi.Param var3, int var4
   ) {
      return addDropdown$rebuild$lambda$59(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$7Uc4FgoKUItH0UUUQYF9nf5V6do(CatalogPanel var0, GalaxyApi.Param var1, ObjectRef var2, int var3) {
      return addDropdown$rebuild$lambda$51(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$99H8a0l9EpbVmoVyZgge4WW92FE(CatalogPanel var0, GalaxyApi.Param var1, TextView var2, TextView var3) {
      return addRowUnsafe$lambda$27(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$BXSUTAgbunMhr_YfTcpm_4Pcllw/* $VF was: $r8$lambda$BXSUTAgbunMhr_YfTcpm_4Pcllw*/(CatalogPanel var0, String var1) {
      return addDropdown$rebuild$lambda$51$lambda$50(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$BXaoPJVVkI5MAXfHUtzp6fGvJHE(Function1 var0, CatalogPanel var1) {
      return buildLongitudinalMode$lambda$76(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$CzVZLDW_VK755UEHG1cjRlE1_xU(CatalogPanel var0, GalaxyApi.Param var1, View var2) {
      addNumeric$lambda$41(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Dti30MGpJrqtHlB24fMDiHLquts(CatalogPanel var0, GalaxyApi.Param var1, ObjectRef var2, Function1 var3) {
      return addDropdown$rebuild$lambda$55(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static void $r8$lambda$EEzRqNl1RygajSt98S3EgasK0QQ(EditText var0, CatalogPanel var1, GalaxyApi.Param var2, DialogInterface var3, int var4) {
      addNumeric$lambda$41$lambda$40(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Epq3vTs5PEuXn8BBucdKd0GZFeA(
      CatalogPanel var0, String var1, ObjectRef var2, LinearLayout var3, ObjectRef var4, GalaxyApi.Param var5, List var6
   ) {
      return addDropdown$lambda$64$lambda$62$lambda$61(var0, var1, var2, var3, var4, var5, var6);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$I6ngHiGxDMVEWkv1aUii0ISBBfI(GalaxyApi.Param var0, CatalogPanel var1) {
      return addRowUnsafe$lambda$25(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$J8URGjspLqnl9dcFvx_3wecKKiE/* $VF was: $r8$lambda$J8URGjspLqnl9dcFvx_3wecKKiE*/(CatalogPanel var0, String var1) {
      return addColor$lambda$70$lambda$69$lambda$68(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$JX26WigUEvIVvWiUphB6QS2y8mA(CatalogPanel var0, String var1) {
      return addLink$lambda$16(var0, var1);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$N1YN6E_B4nTphhQstFGyHle7jGU/* $VF was: $r8$lambda$N1YN6E_B4nTphhQstFGyHle7jGU*/(
      CatalogPanel var0, GalaxyApi.Param var1, View var2
   ) {
      return addNumeric$lambda$42(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$O9FAY4dXewmxSyuD43nVEht_NEQ/* $VF was: $r8$lambda$O9FAY4dXewmxSyuD43nVEht_NEQ*/(CatalogPanel var0) {
      return addRowUnsafe$lambda$18(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$P3SLLzuugugx4PyISNxQ8lojH44(CatalogPanel var0, String var1) {
      return buildLongitudinalMode$lambda$75$lambda$74(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$RAuMy5nN8TkdFtS4MeBjPW6wSd0(CatalogPanel var0, CompoundButton var1, boolean var2) {
      build$lambda$15(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$RaQ256QhAta4SbtxPyyfRnGd_dY/* $VF was: $r8$lambda$RaQ256QhAta4SbtxPyyfRnGd_dY*/(
      CatalogPanel var0, String var1, JSONObject var2
   ) {
      return addRowUnsafe$lambda$25$lambda$23$lambda$22(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$V5rskPcym0LBf49r86E4zPasAxo(CatalogPanel var0, int var1) {
      return buildLongitudinalMode$lambda$75(var0, var1);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$VOJpUmewnxypK_F0QZgtzkJD_tA/* $VF was: $r8$lambda$VOJpUmewnxypK_F0QZgtzkJD_tA*/(
      CatalogPanel var0, GalaxyApi.Param var1, View var2
   ) {
      return addNumeric$lambda$43(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$Whrsw9C_LHzBlYBNDDi7YRv_zxc/* $VF was: $r8$lambda$Whrsw9C_LHzBlYBNDDi7YRv_zxc*/(Function0 var0, DialogInterface var1, int var2) {
      addRowUnsafe$lambda$25$lambda$24(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$XdowQr6wy2_PVaVzL3nNzEVQNrs/* $VF was: $r8$lambda$XdowQr6wy2_PVaVzL3nNzEVQNrs*/(CatalogPanel var0, String var1) {
      return build$lambda$15$lambda$14(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$XpHZd4YS825lufmH006Ih3WNsBM(CatalogPanel var0, GalaxyApi.Param var1) {
      return addRowUnsafe$lambda$25$lambda$23(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$ZEt7HF4GaK0X1wD3M9q7V8ZsMKM(LinearLayout var0, CatalogPanel var1, GalaxyApi.Param var2) {
      return addLink$lambda$17(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$ZX3JZCfZTwLbemCRkMhCWCyHj2A(LinearLayout var0, CatalogPanel var1, GalaxyApi.Param var2) {
      return addRowUnsafe$lambda$19(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$l6JKS2moaTnQIihaaY2h85ucv54(CatalogPanel var0, String var1) {
      return addNumeric$write$lambda$34(var0, var1);
   }

   // $VF: synthetic method
   public static CharSequence $r8$lambda$o4lACCtUaEaEW3DCRksH8l6b_CQ(CatalogPanel var0, MatchResult var1) {
      return addDropdown$lambda$64$lambda$62$lambda$60(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$oTCMMmkpLIOSkKcrtW_M3Wr8NUk/* $VF was: $r8$lambda$oTCMMmkpLIOSkKcrtW_M3Wr8NUk*/(
      CatalogPanel var0, boolean var1, JSONObject var2
   ) {
      return addToggle$lambda$31$lambda$29(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$pSAxEZVyIy2A8cdoZTbvGeMmgck(
      LinearLayout var0, CatalogPanel var1, GalaxyApi.Param var2, ObjectRef var3, ArrayList var4, List var5, TextView var6
   ) {
      return addColor$lambda$72(var0, var1, var2, var3, var4, var5, var6);
   }

   // $VF: synthetic method
   public static void $r8$lambda$pTHfmfI79AeqSJ96XVtSXl3tmkU(CatalogPanel var0, GalaxyApi.Param var1, Switch var2, CompoundButton var3, boolean var4) {
      addToggle$lambda$31(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$pmEcB_p_yUFJTbO0vVDeRC7FyRA/* $VF was: $r8$lambda$pmEcB_p_yUFJTbO0vVDeRC7FyRA*/(
      LinearLayout var0, CatalogPanel var1, GalaxyApi.Param var2, TextView var3
   ) {
      return addRowUnsafe$lambda$20(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static void $r8$lambda$qGDBbK5sWhAEuXmkE93Qrq2pXC0(CatalogPanel var0, GalaxyApi.Param var1, String var2, View var3) {
      addColor$lambda$70$lambda$69(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$qa2z02pRURBnVsyarT4ZTu8_imI/* $VF was: $r8$lambda$qa2z02pRURBnVsyarT4ZTu8_imI*/(CatalogPanel var0, GalaxyApi.Param var1) {
      return addNumeric$lambda$37(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$uW_B_YD2NT31cYOwZLRtrLzhiA4(
      LinearLayout var0,
      CatalogPanel var1,
      GalaxyApi.Param var2,
      ObjectRef var3,
      IntRef var4,
      ObjectRef var5,
      ObjectRef var6,
      TextView var7,
      ObjectRef var8,
      LinearLayout var9
   ) {
      return addDropdown$lambda$64(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$upo5M_3oDBGNLltkBjpPwayd6JM(LinearLayout var0, CatalogPanel var1, GalaxyApi.Param var2, TextView var3) {
      return addRowUnsafe$lambda$26(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$yP_SkuwTRWxBLg8kJXD8rn_HUMw/* $VF was: $r8$lambda$yP_SkuwTRWxBLg8kJXD8rn_HUMw*/(CatalogPanel var0, GalaxyApi.Param var1) {
      return addNumeric$lambda$35(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$zwMIjf_GZQAuBVXII36a_7b3DK0/* $VF was: $r8$lambda$zwMIjf_GZQAuBVXII36a_7b3DK0*/(
      LinearLayout var0, CatalogPanel var1, GalaxyApi.Param var2, TextView var3, TextView var4
   ) {
      return addNumeric$lambda$47(var0, var1, var2, var3, var4);
   }

   static {
      Pair var26 = TuplesKt.to("RivianAngleControl", CollectionsKt.listOf("Rivian"));
      Pair var12 = TuplesKt.to("TeslaCoopSteering", CollectionsKt.listOf("Tesla"));
      Pair var21 = TuplesKt.to("NAPRadarEnabled", CollectionsKt.listOf("Tesla"));
      Pair var14 = TuplesKt.to("NAPRadarBehindNosecone", CollectionsKt.listOf("Tesla"));
      Pair var15 = TuplesKt.to("NAPRadarOffset", CollectionsKt.listOf("Tesla"));
      Pair var2 = TuplesKt.to("NAPPedalEnabled", CollectionsKt.listOf("Tesla"));
      Pair var10 = TuplesKt.to("NAPPedalCanBus", CollectionsKt.listOf("Tesla"));
      Pair var28 = TuplesKt.to("NAPAdaptiveAccel", CollectionsKt.listOf("Tesla"));
      Pair var22 = TuplesKt.to("NAPPedalCalibDone", CollectionsKt.listOf("Tesla"));
      Pair var3 = TuplesKt.to("NAPPedalCalibFactor", CollectionsKt.listOf("Tesla"));
      Pair var4 = TuplesKt.to("NAPPedalCalibZero", CollectionsKt.listOf("Tesla"));
      Pair var9 = TuplesKt.to("GMPedalLongitudinal", GM);
      Pair var5 = TuplesKt.to("GMDashSpoofOffsets", GM);
      Pair var11 = TuplesKt.to("IgnoreIgnitionLine", GM);
      Pair var27 = TuplesKt.to("LongPitch", GM);
      Pair var24 = TuplesKt.to("RemoteStartBootsComma", GM);
      Pair var17 = TuplesKt.to("HKGRemoteStartBootsComma", HKG);
      Pair var13 = TuplesKt.to("VoltSNG", CollectionsKt.listOf(new String[]{"Chevrolet", "Holden"}));
      Pair var23 = TuplesKt.to("GMAutoHold", CollectionsKt.listOf(new String[]{"Buick", "Chevrolet", "Holden"}));
      Pair var0 = TuplesKt.to("VoltOnePedalMode", CollectionsKt.listOf(new String[]{"Chevrolet", "Holden"}));
      Pair var1 = TuplesKt.to("RemapCancelToDistance", CollectionsKt.listOf(new String[]{"Chevrolet", "Holden"}));
      Pair var19 = TuplesKt.to("JeepBrakeHold", CollectionsKt.listOf("Jeep"));
      Pair var6 = TuplesKt.to("SubaruSNG", CollectionsKt.listOf("Subaru"));
      Pair var18 = TuplesKt.to("SubaruSNGManualParkingBrake", CollectionsKt.listOf("Subaru"));
      Pair var25 = TuplesKt.to("SubaruStopStartOff", CollectionsKt.listOf("Subaru"));
      Pair var16 = TuplesKt.to("SubaruAvhStartup", CollectionsKt.listOf("Subaru"));
      Pair var7 = TuplesKt.to("SubaruRedneckCruise", CollectionsKt.listOf("Subaru"));
      Pair var8 = TuplesKt.to("SNGHack", CollectionsKt.listOf(new String[]{"Lexus", "Toyota"}));
      Pair var20 = TuplesKt.to("ToyotaAutoHold", CollectionsKt.listOf(new String[]{"Lexus", "Toyota"}));
      VEHICLE_MAKES = MapsKt.mapOf(
         new Pair[]{
            var26,
            var12,
            var21,
            var14,
            var15,
            var2,
            var10,
            var28,
            var22,
            var3,
            var4,
            var9,
            var5,
            var11,
            var27,
            var24,
            var17,
            var13,
            var23,
            var0,
            var1,
            var19,
            var6,
            var18,
            var25,
            var16,
            var7,
            var8,
            var20
         }
      );
      var8 = TuplesKt.to("Ford", CollectionsKt.listOf(new String[]{"Ford", "Lincoln"}));
      var13 = TuplesKt.to("Toyota", CollectionsKt.listOf(new String[]{"Toyota", "Lexus"}));
      var6 = TuplesKt.to("Lexus", CollectionsKt.listOf(new String[]{"Toyota", "Lexus"}));
      var0 = TuplesKt.to("Tesla", CollectionsKt.listOf("Tesla"));
      var11 = TuplesKt.to("Subaru", CollectionsKt.listOf("Subaru"));
      var2 = TuplesKt.to("Hyundai", CollectionsKt.listOf(new String[]{"Hyundai", "Kia", "Genesis"}));
      var3 = TuplesKt.to("HKG", CollectionsKt.listOf(new String[]{"Hyundai", "Kia", "Genesis"}));
      var4 = TuplesKt.to("GM", CollectionsKt.listOf(new String[]{"Buick", "Cadillac", "Chevrolet", "GMC", "Holden"}));
      var5 = TuplesKt.to("Honda", CollectionsKt.listOf(new String[]{"Honda", "Acura"}));
      var12 = TuplesKt.to("Rivian", CollectionsKt.listOf("Rivian"));
      var9 = TuplesKt.to("Nissan", CollectionsKt.listOf("Nissan"));
      var7 = TuplesKt.to("Mazda", CollectionsKt.listOf("Mazda"));
      var10 = TuplesKt.to("VW", CollectionsKt.listOf(new String[]{"Volkswagen", "Audi", "Skoda", "Seat"}));
      var1 = TuplesKt.to("Chrysler", CollectionsKt.listOf(new String[]{"Chrysler", "Jeep", "Ram", "Dodge"}));
      PREFIX_MAKES = CollectionsKt.listOf(
         new Pair[]{var8, var13, var6, var0, var11, var2, var3, var4, var5, var12, var9, var7, var10, var1, TuplesKt.to("Jeep", CollectionsKt.listOf("Jeep"))}
      );
   }

   public CatalogPanel(Activity var1, GalaxyApi var2, List<GalaxyApi.Param> var3, String var4, boolean var5, Function1<? super String, Unit> var6) {
      Intrinsics.checkNotNullParameter(var1, "act");
      Intrinsics.checkNotNullParameter(var2, "api");
      Intrinsics.checkNotNullParameter(var3, "params");
      Intrinsics.checkNotNullParameter(var4, "sectionName");
      Intrinsics.checkNotNullParameter(var6, "openWeb");
      super((Context)var1);
      this.act = var1;
      this.api = var2;
      this.params = var3;
      this.sectionName = var4;
      this.openWeb = var6;
      this.ui = new CarUi((Context)this.act, var5);
      LinearLayout var13 = new LinearLayout((Context)this.act);
      var13.setOrientation(1);
      CarUi var7 = this.ui;
      if (var5) {
         var13.setPadding(0, 0, 0, var7.dp(40));
      } else {
         var13.setPadding(var7.dp(10), this.ui.dp(4), this.ui.dp(24), this.ui.dp(60));
      }

      this.col = var13;
      Iterable var14 = this.params;
      Map var8 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(var14, 10)), 16));

      for (Object var17 : var14) {
         var8.put(((GalaxyApi.Param)var17).getKey(), var17);
      }

      this.byKey = var8;
      Iterable var9 = this.params;
      Map var16 = new LinkedHashMap();

      for (Object var19 : var9) {
         String var21 = ((GalaxyApi.Param)var19).getParentKey();
         Object var10 = var16.get(var21);
         if (var10 == null) {
            var10 = new ArrayList();
            var16.put(var21, var10);
         }

         ((List)var10).add(var19);
      }

      this.children = var16;
      this.rows = new ArrayList<>();
      this.optionCache = new HashMap<>();
      var var11 = this.params;
      if (var11 instanceof Collection && ((Collection)var11).isEmpty()) {
         var5 = false;
      } else {
         Iterator var12 = var11.iterator();

         while (true) {
            if (!var12.hasNext()) {
               var5 = false;
               break;
            }

            if (((GalaxyApi.Param)var12.next()).getO().optBoolean("requires_offroad")) {
               var5 = true;
               break;
            }
         }
      }

      this.needsOffroad = var5;
      this.addView((View)this.col);
      this.advanced = this.api.bool("GalaxyDeveloperMode");
      this.build();
      this.lastSig = "";
   }

   // $VF: synthetic method
   public CatalogPanel(Activity var1, GalaxyApi var2, List var3, String var4, boolean var5, Function1 var6, int var7, DefaultConstructorMarker var8) {
      if ((var7 & 16) != 0) {
         var5 = false;
      }

      this(var1, var2, var3, var4, var5, var6);
   }

   private final void addColor(GalaxyApi.Param var1, int var2) {
      String var6 = "Stock";
      String var8 = "";
      List var11 = CollectionsKt.listOf(
         new Pair[]{
            TuplesKt.to("Stock", ""),
            TuplesKt.to("White", "#FFFFFF"),
            TuplesKt.to("Green", "#00FF00"),
            TuplesKt.to("Blue", "#1E90FF"),
            TuplesKt.to("Red", "#FF3B30"),
            TuplesKt.to("Yellow", "#FFD60A"),
            TuplesKt.to("Orange", "#FF8C00"),
            TuplesKt.to("Purple", "#A24BFF"),
            TuplesKt.to("Cyan", "#00E5FF"),
            TuplesKt.to("Pink", "#FF4FA3")
         }
      );
      LinearLayout var10 = new LinearLayout((Context)this.act);
      var10.setOrientation(1);
      var10.setBackground(this.ui.rowBg());
      var10.setPadding(this.ui.dp(20), this.ui.dp(14), this.ui.dp(18), this.ui.dp(14));
      Pair var4 = CarUi.row$default(this.ui, var1.getLabel(), var1.getDescription(), 0, null, null, 28, null);
      LinearLayout var5 = (LinearLayout)var4.component1();
      TextView var19 = (TextView)var4.component2();
      var5.setBackground(null);
      var5.setPadding(0, 0, 0, 0);
      var5.setMinimumHeight(0);
      var10.addView((View)var5);
      LinearLayout var13 = new LinearLayout((Context)this.act);
      var13.setOrientation(0);
      var13.setPadding(0, this.ui.dp(12), 0, 0);
      ArrayList var12 = new ArrayList();

      for (Pair var9 : var11) {
         String var14 = (String)var9.component1();
         String var16 = (String)var9.component2();
         TextView var15 = new TextView((Context)this.act);
         boolean var3;
         if (var16.length() == 0) {
            var3 = 1;
         } else {
            var3 = 0;
         }

         CharSequence var21;
         if (var3) {
            var21 = var6;
         } else {
            var21 = var8;
         }

         var15.setText(var21);
         var15.setTextSize(13.0F);
         var15.setTextColor(CarUi.Companion.getTEXT());
         var15.setGravity(17);
         CarUi var22 = this.ui;
         if (var16.length() == 0) {
            var3 = (boolean)1;
         } else {
            var3 = (boolean)0;
         }

         if (var3) {
            var3 = CarUi.Companion.getCARD_ON();
         } else {
            var3 = Color.parseColor(var16);
         }

         var15.setBackground((Drawable)var22.rounded(var3, 26, Color.rgb(60, 66, 78)));
         var15.setOnClickListener(new CatalogPanel$$ExternalSyntheticLambda10(this, var1, var16));
         var15.setContentDescription(var14);
         var12.add(var15);
         View var23 = (View)var15;
         LayoutParams var24 = new LayoutParams(this.ui.dp(52), this.ui.dp(52));
         var24.rightMargin = this.ui.dp(10);
         Unit var25 = Unit.INSTANCE;
         var13.addView(var23, (android.view.ViewGroup.LayoutParams)var24);
      }

      var10.addView((View)var13);
      this.col.addView((View)var10, (android.view.ViewGroup.LayoutParams)CarUi.lp$default(this.ui, var2, 0, 2, null));
      ObjectRef var20 = new ObjectRef();
      this.rows.add(new CatalogPanel.Row(var1, (View)var10, var2, new CatalogPanel$$ExternalSyntheticLambda12(var10, this, var1, var20, var12, var11, var19)));
   }

   private static final void addColor$lambda$70$lambda$69(CatalogPanel var0, GalaxyApi.Param var1, String var2, View var3) {
      String var8 = var0.lockReason(var1);
      int var4 = var8.length();
      boolean var5 = true;
      boolean var10;
      if (var4 > 0) {
         var10 = true;
      } else {
         var10 = false;
      }

      if (var10) {
         var0.toast(var8);
      } else {
         GalaxyApi var9 = var0.api;
         String var6 = var1.getKey();
         Object var7 = var2;
         if (var7.length() == 0) {
            var10 = var5;
         } else {
            var10 = false;
         }

         if (var10) {
            var7 = "Stock";
         }

         GalaxyApi.put$default(var9, var6, var7, null, new CatalogPanel$$ExternalSyntheticLambda33(var0), 4, null);
         var0.syncAll();
      }
   }

   private static final Unit addColor$lambda$70$lambda$69$lambda$68(CatalogPanel var0, String var1) {
      if (var1 != null) {
         var0.toast(var1);
      }

      return Unit.INSTANCE;
   }

   private static final Unit addColor$lambda$72(
      LinearLayout var0, CatalogPanel var1, GalaxyApi.Param var2, ObjectRef var3, ArrayList var4, List var5, TextView var6
   ) {
      CatalogPanelKt.access$vis((View)var0, var1.visible(var2));
      String var13 = StringsKt.trim(var1.api.string(var2.getKey())).toString().toUpperCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var13, "toUpperCase(...)");
      boolean var8;
      if (var13.length() == 0) {
         var8 = 1;
      } else {
         var8 = 0;
      }

      boolean var11;
      if (!var8 && !Intrinsics.areEqual(var13, "STOCK")) {
         var11 = false;
      } else {
         var11 = true;
      }

      if (!Intrinsics.areEqual(var13, var3.element)) {
         var3.element = (T)var13;
         Iterator var17 = var4.iterator();
         var8 = 0;

         while (var17.hasNext()) {
            int var10 = var8 + 1;
            View var14 = (View)var17.next();
            String var16 = (String)((Pair)var5.get(var8)).getSecond();
            boolean var19;
            if (var16.length() == 0) {
               var19 = 1;
            } else {
               var19 = 0;
            }

            boolean var12;
            if (var19) {
               var12 = var11;
            } else {
               String var15 = var16.toUpperCase(Locale.ROOT);
               Intrinsics.checkNotNullExpressionValue(var15, "toUpperCase(...)");
               var12 = Intrinsics.areEqual(var15, var13);
            }

            CarUi var22 = var1.ui;
            if (var16.length() == 0) {
               var19 = (boolean)1;
            } else {
               var19 = (boolean)0;
            }

            if (var19) {
               var19 = CarUi.Companion.getCARD_ON();
            } else {
               var19 = Color.parseColor(var16);
            }

            int var9;
            if (var12) {
               var9 = CarUi.Companion.getACCENT();
            } else {
               var9 = Color.rgb(60, 66, 78);
            }

            var14.setBackground((Drawable)var22.rounded(var19, 26, var9));
            float var7;
            if (var12) {
               var7 = 1.15F;
            } else {
               var7 = 1.0F;
            }

            var14.setScaleX(var7);
            var14.setScaleY(var14.getScaleX());
            var8 = var10;
         }
      }

      var1.lock((View)var0, var6, var2);
      return Unit.INSTANCE;
   }

   private final void addDropdown(GalaxyApi.Param var1, int var2) {
      LinearLayout var3 = new LinearLayout((Context)this.act);
      var3.setOrientation(1);
      var3.setBackground(this.ui.rowBg());
      var3.setPadding(this.ui.dp(20), this.ui.dp(14), this.ui.dp(18), this.ui.dp(14));
      Pair var4 = CarUi.row$default(this.ui, var1.getLabel(), var1.getDescription(), 0, null, null, 28, null);
      LinearLayout var5 = (LinearLayout)var4.component1();
      TextView var11 = (TextView)var4.component2();
      var5.setBackground(null);
      var5.setPadding(0, 0, 0, 0);
      var5.setMinimumHeight(0);
      var3.addView((View)var5);
      LinearLayout var8 = new LinearLayout((Context)this.act);
      var8.setOrientation(1);
      var8.setPadding(0, this.ui.dp(10), 0, 0);
      var3.addView((View)var8);
      ObjectRef var12 = new ObjectRef();
      var12.element = (T)var1.getOptions();
      ObjectRef var6 = new ObjectRef();
      ObjectRef var10 = new ObjectRef();
      var10.element = (T)"";
      IntRef var7 = new IntRef();
      var7.element = -2;
      ObjectRef var9 = new ObjectRef();
      addDropdown$rebuild(var8, var6, var12, this, var1);
      this.col.addView((View)var3, (android.view.ViewGroup.LayoutParams)CarUi.lp$default(this.ui, var2, 0, 2, null));
      this.rows
         .add(
            new CatalogPanel.Row(
               var1, (View)var3, var2, new CatalogPanel$$ExternalSyntheticLambda22(var3, this, var1, var12, var7, var6, var9, var11, var10, var8)
            )
         );
   }

   private static final Unit addDropdown$lambda$64(
      LinearLayout var0,
      CatalogPanel var1,
      GalaxyApi.Param var2,
      ObjectRef var3,
      IntRef var4,
      ObjectRef var5,
      ObjectRef var6,
      TextView var7,
      ObjectRef var8,
      LinearLayout var9
   ) {
      CatalogPanelKt.access$vis((View)var0, var1.visible(var2));
      String var12 = var2.str("options_endpoint");
      if (var12 != null) {
         CharSequence var19 = var12;
         var12 = new Regex("\\{(\\w+)}").replace(var19, new CatalogPanel$$ExternalSyntheticLambda26(var1));
         if (!Intrinsics.areEqual(var12, var8.element)) {
            var8.element = (T)var12;
            List var16 = var1.optionCache.get(var12);
            if (var16 != null) {
               var3.element = (T)var16;
               addDropdown$rebuild(var9, var5, var3, var1, var2);
            } else {
               var1.api.options(var12, new CatalogPanel$$ExternalSyntheticLambda27(var1, var12, var3, var9, var5, var2));
            }
         }
      }

      String var17 = var1.api.string(var2.getKey());
      List var13 = (List)var3.element;
      int var10 = 0;
      Iterator var14 = var13.iterator();

      while (true) {
         if (!var14.hasNext()) {
            var10 = -1;
            break;
         }

         Pair var18 = (Pair)var14.next();
         boolean var11;
         if (Intrinsics.areEqual(var18.getFirst(), var17)
            || StringsKt.toDoubleOrNull((String)var18.getFirst()) != null
               && StringsKt.toDoubleOrNull(var17) != null
               && Math.abs(Double.parseDouble((String)var18.getFirst()) - Double.parseDouble(var17)) < 1.0E-9) {
            var11 = true;
         } else {
            var11 = false;
         }

         if (var11) {
            break;
         }

         var10++;
      }

      if (var10 != var4.element || var5.element != var6.element) {
         var4.element = var10;
         var6.element = var5.element;
         Function1 var15 = (Function1)var5.element;
         if (var15 != null) {
            var15.invoke(var10);
         }
      }

      var1.lock((View)var0, var7, var2);
      return Unit.INSTANCE;
   }

   private static final CharSequence addDropdown$lambda$64$lambda$62$lambda$60(CatalogPanel var0, MatchResult var1) {
      Intrinsics.checkNotNullParameter(var1, "m");
      String var2 = URLEncoder.encode(var0.api.string(var1.getGroupValues().get(1)), "UTF-8");
      Intrinsics.checkNotNullExpressionValue(var2, "encode(...)");
      return var2;
   }

   private static final Unit addDropdown$lambda$64$lambda$62$lambda$61(
      CatalogPanel var0, String var1, ObjectRef var2, LinearLayout var3, ObjectRef var4, GalaxyApi.Param var5, List var6
   ) {
      Intrinsics.checkNotNullParameter(var6, "o");
      var0.optionCache.put(var1, var6);
      var2.element = (T)var6;
      addDropdown$rebuild(var3, var4, var2, var0, var5);
      var0.syncAll();
      return Unit.INSTANCE;
   }

   private static final void addDropdown$rebuild(
      LinearLayout var0, ObjectRef<Function1<Integer, Unit>> var1, ObjectRef<List<Pair<String, String>>> var2, CatalogPanel var3, GalaxyApi.Param var4
   ) {
      var0.removeAllViews();
      var1.element = null;
      if (((List)var2.element).isEmpty()) {
         var0.addView((View)var3.ui.note("No options available."));
      } else {
         CatalogPanel$$ExternalSyntheticLambda28 var5 = new CatalogPanel$$ExternalSyntheticLambda28(var3, var4, var2);
         if (((List)var2.element).size() <= 4) {
            CarUi var11 = var3.ui;
            Iterable var14 = (Iterable)var2.element;
            var var8 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var14, 10));
            Iterator var15 = var14.iterator();

            while (var15.hasNext()) {
               var8.add((String)((Pair)var15.next()).getSecond());
            }

            List var9 = var8;
            Pair var12 = var11.chips(var9, var5);
            LinearLayout var10 = (LinearLayout)var12.component1();
            Function1 var13 = (Function1)var12.component2();
            var0.addView((View)var10);
            var1.element = (T)var13;
         } else {
            TextView var16 = CarUi.button$default(var3.ui, "Choose…", false, new CatalogPanel$$ExternalSyntheticLambda29(var3, var4, var2, var5), 2, null);
            TextView var7 = var3.value();
            var7.setGravity(8388611);
            var7.setTextSize(20.0F);
            LinearLayout var6 = new LinearLayout((Context)var3.act);
            var6.setOrientation(0);
            var6.setGravity(16);
            var6.addView((View)var7, (android.view.ViewGroup.LayoutParams)(new LayoutParams(0, -2, 1.0F)));
            var6.addView((View)var16);
            var0.addView((View)var6);
            var1.element = (T)(new CatalogPanel$$ExternalSyntheticLambda30(var7, var2, var3, var4));
         }
      }
   }

   private static final Unit addDropdown$rebuild$lambda$51(CatalogPanel var0, GalaxyApi.Param var1, ObjectRef var2, int var3) {
      String var5 = var0.lockReason(var1);
      boolean var4;
      if (var5.length() > 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      if (var4) {
         var0.toast(var5);
      } else {
         String var6;
         GalaxyApi var7;
         label19: {
            var5 = (String)((Pair)((List)var2.element).get(var3)).getFirst();
            var7 = var0.api;
            var6 = var1.getKey();
            if (Intrinsics.areEqual(var1.getDataType(), "int")) {
               Double var8 = StringsKt.toDoubleOrNull(var5);
               if (var8 != null) {
                  var9 = MathKt.roundToInt(var8);
                  break label19;
               }
            }

            var9 = var5;
         }

         var7.put(
            var6,
            var9,
            MapsKt.mapOf(TuplesKt.to("label", ((Pair)((List)var2.element).get(var3)).getSecond())),
            new CatalogPanel$$ExternalSyntheticLambda18(var0)
         );
         var0.syncAll();
      }

      return Unit.INSTANCE;
   }

   private static final Unit addDropdown$rebuild$lambda$51$lambda$50(CatalogPanel var0, String var1) {
      if (var1 != null) {
         var0.toast(var1);
      }

      return Unit.INSTANCE;
   }

   private static final Unit addDropdown$rebuild$lambda$55(CatalogPanel var0, GalaxyApi.Param var1, ObjectRef var2, Function1 var3) {
      Builder var4 = new Builder((Context)var0.act).setTitle(var1.getLabel());
      Iterable var8 = (Iterable)var2.element;
      var var5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var8, 10));
      Iterator var9 = var8.iterator();

      while (var9.hasNext()) {
         var5.add((String)((Pair)var9.next()).getSecond());
      }

      List var6 = var5;
      Collection var7 = var6;
      var4.setItems(var7.toArray(new String[0]), new CatalogPanel$$ExternalSyntheticLambda31(var3)).show();
      return Unit.INSTANCE;
   }

   private static final void addDropdown$rebuild$lambda$55$lambda$54(Function1 var0, DialogInterface var1, int var2) {
      var0.invoke(var2);
   }

   private static final Unit addDropdown$rebuild$lambda$59(TextView var0, ObjectRef var1, CatalogPanel var2, GalaxyApi.Param var3, int var4) {
      label17: {
         Pair var6 = CollectionsKt.getOrNull((List<? extends Pair>)var1.element, var4);
         if (var6 != null) {
            String var5 = (String)var6.getSecond();
            var7 = var5;
            if (var5 != null) {
               break label17;
            }
         }

         CharSequence var9 = var2.api.string(var3.getKey());
         Object var8 = var9;
         if (StringsKt.isBlank(var9)) {
            var8 = "(not set)";
         }

         var7 = (String)var8;
      }

      CatalogPanelKt.access$txt(var0, var7);
      return Unit.INSTANCE;
   }

   private final void addLink(String var1, String var2, GalaxyApi.Param var3, String var4) {
      LinearLayout var7 = (LinearLayout)CarUi.row$default(this.ui, var1, var2, 1, null, null, 24, null).component1();
      var7.addView((View)CarUi.button$default(this.ui, "Open", false, new CatalogPanel$$ExternalSyntheticLambda13(this, var4), 2, null));
      LinearLayout var6 = this.col;
      View var8 = (View)var7;
      CarUi var9 = this.ui;
      int var5;
      if (var3 != null) {
         var5 = this.depthOf(var3) + 1;
      } else {
         var5 = 0;
      }

      var6.addView(var8, (android.view.ViewGroup.LayoutParams)CarUi.lp$default(var9, var5, 0, 2, null));
      if (var3 != null) {
         this.rows.add(new CatalogPanel.Row(var3, (View)var7, 0, new CatalogPanel$$ExternalSyntheticLambda14(var7, this, var3)));
      }
   }

   private static final Unit addLink$lambda$16(CatalogPanel var0, String var1) {
      var0.openWeb.invoke(var1);
      return Unit.INSTANCE;
   }

   private static final Unit addLink$lambda$17(LinearLayout var0, CatalogPanel var1, GalaxyApi.Param var2) {
      byte var3;
      if (var1.visible(var2) && var1.api.bool(var2.getKey())) {
         var3 = 0;
      } else {
         var3 = 8;
      }

      var0.setVisibility(var3);
      return Unit.INSTANCE;
   }

   private final void addNumeric(GalaxyApi.Param var1, int var2) {
      CatalogPanel.Num var3 = this.numeric(var1);
      Pair var4 = CarUi.row$default(this.ui, var3.getLabel(), var3.getDesc(), var2, null, null, 24, null);
      LinearLayout var10 = (LinearLayout)var4.component1();
      TextView var6 = (TextView)var4.component2();
      TextView var5 = this.value();
      LinearLayout var11 = new LinearLayout((Context)this.act);
      var11.setOrientation(0);
      var11.setGravity(16);
      var11.addView((View)this.ui.stepper("−", new CatalogPanel$$ExternalSyntheticLambda19(this, var1)));
      View var9 = (View)var5;
      LayoutParams var8 = new LayoutParams(-2, -2);
      var8.leftMargin = this.ui.dp(6);
      var8.rightMargin = this.ui.dp(6);
      Unit var7 = Unit.INSTANCE;
      var11.addView(var9, (android.view.ViewGroup.LayoutParams)var8);
      var11.addView((View)this.ui.stepper("+", new CatalogPanel$$ExternalSyntheticLambda20(this, var1)));
      var5.setOnClickListener(new CatalogPanel$$ExternalSyntheticLambda21(this, var1));
      View var12 = var11.getChildAt(0);
      Intrinsics.checkNotNull(var12, "null cannot be cast to non-null type android.widget.TextView");
      ((TextView)var12).setOnLongClickListener(new CatalogPanel$$ExternalSyntheticLambda23(this, var1));
      View var13 = var11.getChildAt(2);
      Intrinsics.checkNotNull(var13, "null cannot be cast to non-null type android.widget.TextView");
      ((TextView)var13).setOnLongClickListener(new CatalogPanel$$ExternalSyntheticLambda24(this, var1));
      var10.addView((View)var11);
      this.col.addView((View)var10, (android.view.ViewGroup.LayoutParams)CarUi.lp$default(this.ui, var2, 0, 2, null));
      this.rows.add(new CatalogPanel.Row(var1, (View)var10, var2, new CatalogPanel$$ExternalSyntheticLambda25(var10, this, var1, var5, var6)));
   }

   private static final double addNumeric$cur(CatalogPanel var0, GalaxyApi.Param var1) {
      Double var4 = var0.api.double_(var1.getKey());
      double var2;
      if (var4 != null) {
         var2 = var4;
      } else {
         var2 = var1.num("default", var0.numeric(var1).getMin());
      }

      return var2;
   }

   private static final boolean addNumeric$guard(CatalogPanel var0, GalaxyApi.Param var1) {
      String var3 = var0.lockReason(var1);
      boolean var2;
      if (var3.length() > 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (var2) {
         var0.toast(var3);
         return false;
      } else {
         return true;
      }
   }

   private static final Unit addNumeric$lambda$35(CatalogPanel var0, GalaxyApi.Param var1) {
      if (addNumeric$guard(var0, var1)) {
         addNumeric$write(var0, var1, addNumeric$cur(var0, var1) - var0.numeric(var1).getStep());
      }

      return Unit.INSTANCE;
   }

   private static final Unit addNumeric$lambda$37(CatalogPanel var0, GalaxyApi.Param var1) {
      if (addNumeric$guard(var0, var1)) {
         addNumeric$write(var0, var1, addNumeric$cur(var0, var1) + var0.numeric(var1).getStep());
      }

      return Unit.INSTANCE;
   }

   private static final void addNumeric$lambda$41(CatalogPanel var0, GalaxyApi.Param var1, View var2) {
      if (addNumeric$guard(var0, var1)) {
         CatalogPanel.Num var6 = var0.numeric(var1);
         EditText var5 = new EditText((Context)var0.act);
         var5.setInputType(12290);
         var5.setText(
            var0.fmt(addNumeric$cur(var0, var1), new CatalogPanel.Num(var6.getMin(), var6.getMax(), var6.getStep(), var6.getPrecision(), "", "", null))
         );
         var5.setTextSize(24.0F);
         Builder var7 = new Builder((Context)var0.act);
         String var3 = var6.getLabel();
         String var4 = var0.fmt(var6.getMin(), var6);
         String var8 = var0.fmt(var6.getMax(), var6);
         var7.setTitle(var3 + " (" + var4 + " – " + var8 + ")")
            .setView((View)var5)
            .setPositiveButton("Set", new CatalogPanel$$ExternalSyntheticLambda15(var5, var0, var1))
            .setNegativeButton("Cancel", null)
            .show();
      }
   }

   private static final void addNumeric$lambda$41$lambda$40(EditText var0, CatalogPanel var1, GalaxyApi.Param var2, DialogInterface var3, int var4) {
      Double var5 = StringsKt.toDoubleOrNull(var0.getText().toString());
      if (var5 != null) {
         addNumeric$write(var1, var2, var5.doubleValue());
      }
   }

   private static final boolean addNumeric$lambda$42(CatalogPanel var0, GalaxyApi.Param var1, View var2) {
      if (addNumeric$guard(var0, var1)) {
         addNumeric$write(var0, var1, addNumeric$cur(var0, var1) - var0.numeric(var1).getStep() * 10);
      }

      return true;
   }

   private static final boolean addNumeric$lambda$43(CatalogPanel var0, GalaxyApi.Param var1, View var2) {
      if (addNumeric$guard(var0, var1)) {
         addNumeric$write(var0, var1, addNumeric$cur(var0, var1) + var0.numeric(var1).getStep() * 10);
      }

      return true;
   }

   private static final Unit addNumeric$lambda$47(LinearLayout var0, CatalogPanel var1, GalaxyApi.Param var2, TextView var3, TextView var4) {
      CatalogPanelKt.access$vis((View)var0, var1.visible(var2));
      CatalogPanel.Num var6 = var1.numeric(var2);
      CatalogPanelKt.access$txt(var3, var1.fmt(addNumeric$cur(var1, var2), var6));
      Iterable var5 = RangesKt.until(0, var0.getChildCount());
      var var8 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var5, 10));
      Iterator var13 = var5.iterator();

      while (var13.hasNext()) {
         var8.add(var0.getChildAt(((IntIterator)var13).nextInt()));
      }

      List var9 = var8;

      for (Object var14 : var9) {
         if ((View)var14 instanceof LinearLayout) {
            Intrinsics.checkNotNull(var14, "null cannot be cast to non-null type android.widget.LinearLayout");
            View var7 = ((LinearLayout)var14).getChildAt(0);
            if (var7 instanceof TextView) {
               var3 = (TextView)var7;
            } else {
               var3 = null;
            }

            TextView var15 = var3;
            if (var3 == null) {
               Intrinsics.checkNotNull(var7, "null cannot be cast to non-null type android.widget.LinearLayout");
               View var12 = ((LinearLayout)var7).getChildAt(0);
               Intrinsics.checkNotNull(var12, "null cannot be cast to non-null type android.widget.TextView");
               var15 = (TextView)var12;
            }

            CatalogPanelKt.access$txt(var15, var6.getLabel());
            var1.lock((View)var0, var4, var2);
            return Unit.INSTANCE;
         }
      }

      throw new NoSuchElementException("Collection contains no element matching the predicate.");
   }

   private static final void addNumeric$write(CatalogPanel var0, GalaxyApi.Param var1, double var2) {
      CatalogPanel.Num var5 = var0.numeric(var1);
      var2 = Math.min(var5.getMax(), Math.max(var5.getMin(), var2));
      if (var5.getStep() > 0.0) {
         var2 = Math.round(var2 / var5.getStep()) * var5.getStep();
      }

      Object var8;
      if (Intrinsics.areEqual(var1.getDataType(), "int")) {
         var8 = MathKt.roundToInt(var2);
      } else {
         StringCompanionObject var6 = StringCompanionObject.INSTANCE;
         Locale var10 = Locale.US;
         int var4 = Math.max(var5.getPrecision(), 0);
         var8 = String.format(var10, "%." + var4 + "f", Arrays.copyOf(new Object[]{var2}, 1));
         Intrinsics.checkNotNullExpressionValue(var8, "format(...)");
         var8 = Double.parseDouble((String)var8);
      }

      GalaxyApi.put$default(var0.api, var1.getKey(), var8, null, new CatalogPanel$$ExternalSyntheticLambda32(var0), 4, null);
   }

   private static final Unit addNumeric$write$lambda$34(CatalogPanel var0, String var1) {
      if (var1 != null) {
         var0.toast(var1);
      }

      return Unit.INSTANCE;
   }

   private final void addRow(GalaxyApi.Param var1) {
      try {
         this.addRowUnsafe(var1);
      } catch (Exception var4) {
         String var2 = var1.getKey();
         Log.e("StarView/Catalog", "row " + var2, var4);
         LinearLayout var5 = (LinearLayout)CarUi.row$default(this.ui, var1.getLabel(), "Could not render this setting: " + var4, 0, null, null, 28, null)
            .component1();
         this.col.addView((View)var5, (android.view.ViewGroup.LayoutParams)CarUi.lp$default(this.ui, this.depthOf(var1), 0, 2, null));
      }
   }

   private final void addRowUnsafe(GalaxyApi.Param var1) {
      int var3 = this.depthOf(var1);
      String var5 = var1.getUiType();
      int var2 = var5.hashCode();
      TextView var4 = null;
      switch (var2) {
         case -2000413939:
            if (var5.equals("numeric")) {
               this.addNumeric(var1, var3);
            }
            break;
         case -1785238953:
            if (var5.equals("favorites")) {
               LinearLayout var12 = (LinearLayout)CarUi.row$default(this.ui, var1.getLabel(), var1.getDescription(), 0, null, null, 28, null).component1();
               var12.addView((View)CarUi.button$default(this.ui, "Open", false, new CatalogPanel$$ExternalSyntheticLambda35(this), 2, null));
               this.col.addView((View)var12, (android.view.ViewGroup.LayoutParams)CarUi.lp$default(this.ui, var3, 0, 2, null));
               this.rows.add(new CatalogPanel.Row(var1, (View)var12, var3, new CatalogPanel$$ExternalSyntheticLambda36(var12, this, var1)));
            }
            break;
         case -1422950858:
            if (var5.equals("action")) {
               Pair var10 = CarUi.row$default(this.ui, var1.getLabel(), var1.getDescription(), var3, null, null, 24, null);
               LinearLayout var6 = (LinearLayout)var10.component1();
               TextView var8 = (TextView)var10.component2();
               CarUi var7 = this.ui;
               var5 = var1.str("action_label");
               String var11 = var5;
               if (var5 == null) {
                  var11 = "Run";
               }

               var6.addView((View)CarUi.button$default(var7, var11, false, new CatalogPanel$$ExternalSyntheticLambda38(var1, this), 2, null));
               this.col.addView((View)var6, (android.view.ViewGroup.LayoutParams)CarUi.lp$default(this.ui, var3, 0, 2, null));
               this.rows.add(new CatalogPanel.Row(var1, (View)var6, var3, new CatalogPanel$$ExternalSyntheticLambda1(var6, this, var1, var8)));
            }
            break;
         case -868304044:
            if (var5.equals("toggle")) {
               this.addToggle(var1, var3);
            }
            break;
         case -432061423:
            if (var5.equals("dropdown")) {
               this.addDropdown(var1, var3);
            }
            break;
         case 94842723:
            if (var5.equals("color")) {
               this.addColor(var1, var3);
            }
            break;
         case 98629247:
            if (var5.equals("group")) {
               TextView var14 = this.ui.header(var1.getLabel());
               this.col.addView((View)var14, (android.view.ViewGroup.LayoutParams)this.ui.lp(var3, 0));
               if (var1.getDescription() != null) {
                  var4 = this.ui.note(var1.getDescription());
               }

               if (var4 != null) {
                  this.col.addView((View)var4, (android.view.ViewGroup.LayoutParams)this.ui.lp(var3, 4));
               }

               this.rows.add(new CatalogPanel.Row(var1, (View)var14, var3, new CatalogPanel$$ExternalSyntheticLambda2(this, var1, var14, var4)));
            }
            break;
         case 1080419832:
            if (var5.equals("readout")) {
               LinearLayout var13 = (LinearLayout)CarUi.row$default(this.ui, var1.getLabel(), var1.getDescription(), var3, null, null, 24, null).component1();
               var4 = this.value();
               var13.addView((View)var4);
               this.col.addView((View)var13, (android.view.ViewGroup.LayoutParams)CarUi.lp$default(this.ui, var3, 0, 2, null));
               this.rows.add(new CatalogPanel.Row(var1, (View)var13, var3, new CatalogPanel$$ExternalSyntheticLambda37(var13, this, var1, var4)));
            }
      }
   }

   private static final Unit addRowUnsafe$lambda$18(CatalogPanel var0) {
      var0.openWeb.invoke("#/settings/favorites");
      return Unit.INSTANCE;
   }

   private static final Unit addRowUnsafe$lambda$19(LinearLayout var0, CatalogPanel var1, GalaxyApi.Param var2) {
      CatalogPanelKt.access$vis((View)var0, var1.visible(var2));
      return Unit.INSTANCE;
   }

   private static final Unit addRowUnsafe$lambda$20(LinearLayout var0, CatalogPanel var1, GalaxyApi.Param var2, TextView var3) {
      CatalogPanelKt.access$vis((View)var0, var1.visible(var2));
      Double var5 = var1.api.double_(var2.getKey());
      String var6;
      if (var5 == null) {
         var6 = "--";
      } else {
         StringCompanionObject var8 = StringCompanionObject.INSTANCE;
         Locale var9 = Locale.US;
         int var4 = (int)var2.num("precision", 2.0);
         String var7 = String.format(var9, "%." + var4 + "f", Arrays.copyOf(new Object[]{var5}, 1));
         Intrinsics.checkNotNullExpressionValue(var7, "format(...)");
         String var10 = var2.getUnit();
         var6 = var7 + var10;
      }

      var3.setText(var6);
      return Unit.INSTANCE;
   }

   private static final Unit addRowUnsafe$lambda$25(GalaxyApi.Param var0, CatalogPanel var1) {
      CatalogPanel$$ExternalSyntheticLambda4 var2 = new CatalogPanel$$ExternalSyntheticLambda4(var1, var0);
      String var3 = var0.str("confirm_message");
      if (var3 != null) {
         new Builder((Context)var1.act)
            .setMessage(var3)
            .setPositiveButton("Yes", new CatalogPanel$$ExternalSyntheticLambda5(var2))
            .setNegativeButton("Cancel", null)
            .show();
      } else {
         var2.invoke();
      }

      return Unit.INSTANCE;
   }

   private static final Unit addRowUnsafe$lambda$25$lambda$23(CatalogPanel var0, GalaxyApi.Param var1) {
      GalaxyApi var3 = var0.api;
      String var2 = var1.str("action_endpoint");
      String var4 = var2;
      if (var2 == null) {
         var4 = "";
      }

      var3.post(var4, null, new CatalogPanel$$ExternalSyntheticLambda9(var0));
      return Unit.INSTANCE;
   }

   private static final Unit addRowUnsafe$lambda$25$lambda$23$lambda$22(CatalogPanel var0, String var1, JSONObject var2) {
      if (var1 == null) {
         label20: {
            if (var2 != null) {
               var1 = var2.optString("message");
               if (var1 != null) {
                  CharSequence var6 = var1;
                  CharSequence var4 = var6;
                  if (StringsKt.isBlank(var6)) {
                     var4 = null;
                  }

                  var5 = (String)var4;
                  break label20;
               }
            }

            var5 = null;
         }

         var1 = var5;
         if (var5 == null) {
            var1 = "Done";
         }
      }

      var0.toast(var1);
      GalaxyApi.refreshValues$default(var0.api, null, 1, null);
      return Unit.INSTANCE;
   }

   private static final void addRowUnsafe$lambda$25$lambda$24(Function0 var0, DialogInterface var1, int var2) {
      var0.invoke();
   }

   private static final Unit addRowUnsafe$lambda$26(LinearLayout var0, CatalogPanel var1, GalaxyApi.Param var2, TextView var3) {
      CatalogPanelKt.access$vis((View)var0, var1.visible(var2));
      var1.lock((View)var0, var3, var2);
      return Unit.INSTANCE;
   }

   private static final Unit addRowUnsafe$lambda$27(CatalogPanel var0, GalaxyApi.Param var1, TextView var2, TextView var3) {
      byte var4;
      if (var0.visible(var1)) {
         var4 = 0;
      } else {
         var4 = 8;
      }

      var2.setVisibility(var4);
      if (var3 != null) {
         var3.setVisibility(var4);
      }

      return Unit.INSTANCE;
   }

   private final void addToggle(GalaxyApi.Param var1, int var2) {
      Pair var4 = CarUi.row$default(this.ui, var1.getLabel(), var1.getDescription(), var2, null, null, 24, null);
      LinearLayout var3 = (LinearLayout)var4.component1();
      TextView var6 = (TextView)var4.component2();
      Switch var5 = this.ui.bigSwitch();
      var5.setOnCheckedChangeListener(new CatalogPanel$$ExternalSyntheticLambda16(this, var1, var5));
      var3.addView((View)var5);
      this.col.addView((View)var3, (android.view.ViewGroup.LayoutParams)CarUi.lp$default(this.ui, var2, 0, 2, null));
      this.rows.add(new CatalogPanel.Row(var1, (View)var3, var2, new CatalogPanel$$ExternalSyntheticLambda17(var3, this, var1, var5, var6)));
   }

   private static final void addToggle$lambda$31(CatalogPanel var0, GalaxyApi.Param var1, Switch var2, CompoundButton var3, boolean var4) {
      if (!var0.syncing) {
         String var7 = var0.lockReason(var1);
         boolean var5;
         if (var7.length() > 0) {
            var5 = true;
         } else {
            var5 = false;
         }

         if (var5) {
            var0.toast(var7);
            var0.syncing = true;
            var2.setChecked(var4 ^ true);
            var0.syncing = false;
         } else if (Intrinsics.areEqual(var1.getKey(), "ForceOffroad")) {
            DataClient var8 = Link.INSTANCE.getData();
            if (var8 != null && var8.getConnected()) {
               String var6;
               if (var4) {
                  var6 = "offroad";
               } else {
                  var6 = "default";
               }

               var8.request("drive_state", MapsKt.mapOf(TuplesKt.to("mode", var6)), new CatalogPanel$$ExternalSyntheticLambda6(var0, var4));
            } else {
               var0.toast("Not connected to the comma");
               var0.syncing = true;
               var2.setChecked(var4 ^ true);
               var0.syncing = false;
            }
         } else {
            GalaxyApi.put$default(var0.api, var1.getKey(), var4, null, new CatalogPanel$$ExternalSyntheticLambda7(var0), 4, null);
            var0.syncAll();
         }
      }
   }

   private static final Unit addToggle$lambda$31$lambda$29(CatalogPanel var0, boolean var1, JSONObject var2) {
      Intrinsics.checkNotNullParameter(var2, "ack");
      if (var2.optBoolean("ok")) {
         var0.api.getValues().put("ForceOffroad", var1);
         var0.api.getValues().put("ForceOnroad", false);
      } else {
         String var3 = var2.optString("error", "rejected");
         Intrinsics.checkNotNullExpressionValue(var3, "optString(...)");
         var0.toast(var3);
         GalaxyApi.refreshValues$default(var0.api, null, 1, null);
      }

      var0.syncAll();
      return Unit.INSTANCE;
   }

   private static final Unit addToggle$lambda$31$lambda$30(CatalogPanel var0, String var1) {
      if (var1 != null) {
         var0.toast(var1);
      }

      return Unit.INSTANCE;
   }

   private static final Unit addToggle$lambda$32(LinearLayout var0, CatalogPanel var1, GalaxyApi.Param var2, Switch var3, TextView var4) {
      CatalogPanelKt.access$vis((View)var0, var1.visible(var2));
      var3.setChecked(var1.api.bool(var2.getKey()));
      var1.lock((View)var0, var4, var2);
      return Unit.INSTANCE;
   }

   private final void build() {
      this.col.removeAllViews();
      this.rows.clear();
      if (!Intrinsics.areEqual(this.sectionName, "Favorites")) {
         var var2 = this.params;
         boolean var1;
         if (var2 instanceof Collection && ((Collection)var2).isEmpty()) {
            var1 = 0;
         } else {
            Iterator var6 = var2.iterator();

            while (true) {
               if (!var6.hasNext()) {
                  var1 = 0;
                  break;
               }

               if (Intrinsics.areEqual(((GalaxyApi.Param)var6.next()).getTier(), "advanced")) {
                  var1 = 1;
                  break;
               }
            }
         }

         if (var1) {
            LinearLayout var3 = (LinearLayout)CarUi.row$default(
                  this.ui, "Show advanced settings", "Reveals the fine-tuning options in every section (Galaxy Developer Mode).", 0, null, null, 28, null
               )
               .component1();
            Switch var7 = this.ui.bigSwitch();
            var7.setChecked(this.advanced);
            var7.setOnCheckedChangeListener(new CatalogPanel$$ExternalSyntheticLambda8(this));
            var3.addView((View)var7);
            LinearLayout var8 = this.col;
            View var4 = (View)var3;
            CarUi var9 = this.ui;
            if (this.ui.getFlat()) {
               var1 = 0;
            } else {
               var1 = 14;
            }

            var8.addView(var4, (android.view.ViewGroup.LayoutParams)var9.lp(0, var1));
         }
      }

      if (StringsKt.startsWith$default(this.sectionName, "Longitudinal", false, 2, null)) {
         this.buildLongitudinalMode();
      }

      build$tree(this, null);
      this.emptyNote = (View)this.ui.note("Nothing to show here for your car at this level. Turn on \"Show advanced settings\" for the fine-tuning options.");
      this.col.addView(this.emptyNote, (android.view.ViewGroup.LayoutParams)CarUi.lp$default(this.ui, 0, 0, 3, null));
      this.syncAll();
   }

   private static final void build$lambda$15(CatalogPanel var0, CompoundButton var1, boolean var2) {
      if (!var0.syncing) {
         var0.advanced = var2;
         GalaxyApi.put$default(var0.api, "GalaxyDeveloperMode", var2, null, new CatalogPanel$$ExternalSyntheticLambda34(var0), 4, null);
         var0.build();
         var0.syncAll();
      }
   }

   private static final Unit build$lambda$15$lambda$14(CatalogPanel var0, String var1) {
      if (var1 != null) {
         var0.toast(var1);
      }

      return Unit.INSTANCE;
   }

   private static final void build$tree(CatalogPanel var0, String var1) {
      List var2 = var0.children.get(var1);
      List var3 = var2;
      if (var2 == null) {
         var3 = CollectionsKt.emptyList();
      }

      for (GalaxyApi.Param var5 : var3) {
         var0.addRow(var5);
         if (Intrinsics.areEqual(var5.getKey(), "CustomPersonalities")) {
            var0.addLink(
               "Driving personalities editor", "Braking, acceleration and following distance per profile.", var5, "#/settings/longitudinal-speed-following"
            );
         } else {
            build$tree(var0, var5.getKey());
         }
      }
   }

   private final void buildLongitudinalMode() {
      LinearLayout var1 = new LinearLayout((Context)this.act);
      var1.setOrientation(1);
      var1.setBackground(this.ui.rowBg());
      var1.setPadding(this.ui.dp(20), this.ui.dp(14), this.ui.dp(18), this.ui.dp(14));
      LinearLayout var2 = (LinearLayout)CarUi.row$default(
            this.ui,
            "Longitudinal control mode",
            "Chill follows traffic conservatively. Experimental lets the driving model handle stops and turns. The conditional modes switch automatically.",
            0,
            null,
            null,
            28,
            null
         )
         .component1();
      var2.setBackground(null);
      var2.setPadding(0, 0, 0, 0);
      var2.setMinimumHeight(0);
      var1.addView((View)var2);
      List var4 = CollectionsKt.listOf(new String[]{"Chill", "Experimental", "Conditional Experimental", "Conditional Chill"});
      Pair var5 = this.ui.chips(var4, new CatalogPanel$$ExternalSyntheticLambda0(this));
      LinearLayout var3 = (LinearLayout)var5.component1();
      Function1 var6 = (Function1)var5.component2();
      var3.setPadding(0, this.ui.dp(10), 0, 0);
      var1.addView((View)var3);
      this.col.addView((View)var1, (android.view.ViewGroup.LayoutParams)this.ui.lp(0, 14));
      JSONObject var7 = new JSONObject().put("key", "__mode");
      Intrinsics.checkNotNullExpressionValue(var7, "put(...)");
      GalaxyApi.Param var8 = new GalaxyApi.Param(var7);
      this.rows.add(new CatalogPanel.Row(var8, (View)var1, 0, new CatalogPanel$$ExternalSyntheticLambda11(var6, this)));
   }

   private static final Unit buildLongitudinalMode$lambda$75(CatalogPanel var0, int var1) {
      Boolean var4;
      Boolean var5;
      Pair var7;
      label17: {
         String var3 = "ExperimentalMode";
         var4 = false;
         var5 = true;
         switch (var1) {
            case 0:
               var7 = TuplesKt.to("ExperimentalMode", var4);
               break label17;
            case 1:
               break;
            case 2:
               var3 = "ConditionalExperimental";
               break;
            default:
               var3 = "ConditionalChill";
         }

         var7 = TuplesKt.to(var3, var5);
      }

      String var6 = (String)var7.component1();
      boolean var2 = (Boolean)var7.component2();
      if (var1 <= 1) {
         GalaxyApi.put$default(var0.api, "ConditionalExperimental", var4, null, null, 12, null);
         GalaxyApi.put$default(var0.api, "ConditionalChill", var4, null, null, 12, null);
      }

      var0.api.put(var6, var2, MapsKt.mapOf(TuplesKt.to("acknowledged", var5)), new CatalogPanel$$ExternalSyntheticLambda3(var0));
      return Unit.INSTANCE;
   }

   private static final Unit buildLongitudinalMode$lambda$75$lambda$74(CatalogPanel var0, String var1) {
      if (var1 != null) {
         var0.toast(var1);
      }

      GalaxyApi.refreshValues$default(var0.api, null, 1, null);
      return Unit.INSTANCE;
   }

   private static final Unit buildLongitudinalMode$lambda$76(Function1 var0, CatalogPanel var1) {
      byte var2;
      if (var1.api.bool("ConditionalExperimental")) {
         var2 = 2;
      } else if (var1.api.bool("ConditionalChill")) {
         var2 = 3;
      } else if (var1.api.bool("ExperimentalMode")) {
         var2 = 1;
      } else {
         var2 = 0;
      }

      var0.invoke(Integer.valueOf(var2));
      return Unit.INSTANCE;
   }

   private final int depthOf(GalaxyApi.Param var1) {
      int var2 = 0;
      String var3 = var1.getParentKey();

      while (var3 != null) {
         var2++;
         var1 = this.byKey.get(var3);
         if (var1 != null) {
            var3 = var1.getParentKey();
         } else {
            var3 = null;
         }
      }

      return var2;
   }

   private final String fmt(double var1, CatalogPanel.Num var3) {
      String var5;
      if (var3.getPrecision() <= 0) {
         var5 = String.valueOf(MathKt.roundToInt(var1));
      } else {
         StringCompanionObject var7 = StringCompanionObject.INSTANCE;
         Locale var8 = Locale.US;
         int var4 = var3.getPrecision();
         var5 = String.format(var8, "%." + var4 + "f", Arrays.copyOf(new Object[]{var1}, 1));
         Intrinsics.checkNotNullExpressionValue(var5, "format(...)");
      }

      String var6 = var3.getUnit();
      return var5 + var6;
   }

   private final void lock(View var1, TextView var2, GalaxyApi.Param var3) {
      String var8 = this.lockReason(var3);
      int var5 = var8.length();
      boolean var6 = false;
      boolean var13;
      if (var5 == 0) {
         var13 = true;
      } else {
         var13 = false;
      }

      float var4;
      if (var13) {
         var4 = 1.0F;
      } else {
         var4 = 0.5F;
      }

      if (var1.getAlpha() == var4) {
         var13 = true;
      } else {
         var13 = false;
      }

      if (!var13) {
         var1.setAlpha(var4);
      }

      if (var2 != null) {
         String var7 = this.numeric(var3).getDesc();
         String var9 = var7;
         if (var7 == null) {
            String var12 = var3.getDescription();
            var9 = var12;
            if (var12 == null) {
               var9 = "";
            }
         }

         var13 = var6;
         if (var8.length() == 0) {
            var13 = true;
         }

         if (!var13) {
            var9 = var9 + "\n⚠ " + var8;
         }

         CatalogPanelKt.access$txt(var2, var9);
         var1 = (View)var2;
         CharSequence var11 = var2.getText();
         Intrinsics.checkNotNullExpressionValue(var11, "getText(...)");
         CatalogPanelKt.access$vis(var1, true ^ StringsKt.isBlank(var11));
      }
   }

   private final String lockReason(GalaxyApi.Param var1) {
      boolean var4 = Intrinsics.areEqual(var1.getKey(), "ForceOffroad");
      String var5 = "";
      if (var4) {
         Telemetry var8 = Link.INSTANCE.getTelemetry();
         boolean var3 = false;
         boolean var2 = var3;
         if (var8 != null) {
            var2 = var3;
            if (var8.getEnabled()) {
               var2 = true;
            }
         }

         String var9 = var5;
         if (var2) {
            var9 = "Disengage first.";
         }

         return var9;
      } else if (var1.getO().optBoolean("requires_offroad") && this.api.bool("IsOnroad")) {
         return "Only while offroad - use the Offroad switch at the top.";
      } else if (var1.getO().optBoolean("requires_parked") && !this.api.bool("VehicleParked")) {
         return "Only while the car is in Park.";
      } else {
         var5 = var1.str("disabled_when_key_true");
         if (var5 != null && this.api.bool(var5)) {
            var5 = var1.str("disabled_reason");
            String var7 = var5;
            if (var5 == null) {
               var7 = "Disabled by another setting.";
            }

            return var7;
         } else {
            var5 = var1.str("requires_nonempty_key");
            if (var5 != null) {
               var5 = this.api.string(var5);
               if (StringsKt.isBlank(var5) || Intrinsics.areEqual(var5, "{}")) {
                  var5 = var1.str("disabled_reason");
                  String var6 = var5;
                  if (var5 == null) {
                     var6 = "Required configuration missing.";
                  }

                  return var6;
               }
            }

            return "";
         }
      }
   }

   private final CatalogPanel.Num numeric(GalaxyApi.Param var1) {
      boolean var16 = this.api.getMetric();
      String var19;
      if (var16) {
         var19 = "metric";
      } else {
         var19 = "imperial";
      }

      boolean var17 = Intrinsics.areEqual(var1.getDataType(), "float");
      double var6 = 0.0;
      double var12 = var1.num("min", 0.0);
      double var8 = var1.num("max", 100.0);
      double var2;
      if (var17) {
         var2 = 0.01;
      } else {
         var2 = 1.0;
      }

      double var4 = var1.num("step", var2);
      var2 = var6;
      if (var17) {
         var2 = 2.0;
      }

      int var14 = (int)var1.num("precision", var2);
      String var20 = var1.getUnit();
      String var22 = var1.getLabel();
      String var23 = var1.getDescription();
      int var15;
      String var21;
      if (Intrinsics.areEqual(var1.getUnitType(), "vehicle_speed")) {
         String var18;
         if (var16) {
            var18 = " km/h";
         } else {
            var18 = " mph";
         }

         var2 = var12;
         if (var1.has(var19 + "_min")) {
            var2 = var1.num(var19 + "_min", var12);
         }

         var6 = var8;
         if (var1.has(var19 + "_max")) {
            var6 = var1.num(var19 + "_max", var8);
         }

         double var10 = var4;
         if (var1.has(var19 + "_step")) {
            var10 = var1.num(var19 + "_step", var4);
         }

         if (var1.has(var19 + "_precision")) {
            var14 = (int)var1.num(var19 + "_precision", var14);
         }

         var15 = var14;
         var20 = var18;
         var19 = var22;
         var12 = var2;
         var8 = var6;
         var4 = var10;
         var21 = var23;
         if (var1.has("unit_range_index")) {
            var15 = (int)var1.num("unit_range_index", -1.0);
            List var30;
            if (var16) {
               var30 = SPEED_RANGES_MET;
            } else {
               var30 = SPEED_RANGES_IMP;
            }

            String var24 = CollectionsKt.getOrNull(var30, var15);
            var15 = var14;
            var20 = var18;
            var19 = var22;
            var12 = var2;
            var8 = var6;
            var4 = var10;
            var21 = var23;
            if (var24 != null) {
               var19 = "Speed Offset (" + var24 + var18 + ")";
               var21 = "How much to offset posted speed limits between " + var24 + var18 + ".";
               var15 = var14;
               var20 = var18;
               var12 = var2;
               var8 = var6;
               var4 = var10;
            }
         }
      } else {
         var21 = var23;
         var19 = var22;
         var15 = var14;
      }

      if (Intrinsics.areEqual(var1.getKey(), "ScreenBrightness") || Intrinsics.areEqual(var1.getKey(), "ScreenBrightnessOnroad")) {
         var12 = 1.0;
         var8 = 101.0;
         var4 = 1.0;
      }

      return new CatalogPanel.Num(var12, var8, var4, var15, var20, var19, var21);
   }

   private final boolean tierVisible(GalaxyApi.Param var1) {
      boolean var2;
      if (!this.advanced && !Intrinsics.areEqual(this.sectionName, "Favorites") && !Intrinsics.areEqual(var1.getTier(), "simple")) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   private final void toast(String var1) {
      Toast.makeText((Context)this.act, var1, 1).show();
   }

   private final TextView value() {
      TextView var1 = new TextView((Context)this.act);
      var1.setTextColor(CarUi.Companion.getACCENT());
      var1.setTextSize(22.0F);
      var1.setTypeface(Typeface.DEFAULT_BOLD);
      var1.setGravity(17);
      var1.setMinWidth(this.ui.dp(120));
      return var1;
   }

   private final boolean visible(GalaxyApi.Param var1) {
      if (HIDDEN.contains(var1.getKey())) {
         return false;
      } else {
         String var6 = StringsKt.trim(this.api.string("CarMake")).toString().toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var6, "toLowerCase(...)");
         Collection var5 = var1.strList("vehicle_makes");
         Pair var4 = var5;
         if (var5.isEmpty()) {
            List var24 = VEHICLE_MAKES.get(var1.getKey());
            var4 = var24;
            if (var24 == null) {
               var4 = CollectionsKt.emptyList();
            }
         }

         List var25 = (List)var4;
         var4 = var25;
         label166:
         if (var25.isEmpty()) {
            Iterator var26 = PREFIX_MAKES.iterator();

            boolean var2;
            do {
               boolean var3 = var26.hasNext();
               var4 = null;
               if (!var3) {
                  break;
               }

               var4 = var26.next();
               Pair var7 = (Pair)var4;
               if (StringsKt.startsWith$default(var1.getKey(), (String)var7.getFirst(), false, 2, null)
                  && var1.getKey().length() > ((String)var7.getFirst()).length()
                  && Character.isUpperCase(var1.getKey().charAt(((String)var7.getFirst()).length()))) {
                  var2 = true;
               } else {
                  var2 = false;
               }
            } while (!var2);

            var4 = (Pair)var4;
            if (var4 != null) {
               List var27 = (List)var4.getSecond();
               var4 = var27;
               if (var27 != null) {
                  break label166;
               }
            }

            var4 = CollectionsKt.emptyList();
         }

         if (!((Collection)var4).isEmpty()) {
            boolean var10;
            if (var6.length() > 0) {
               var10 = true;
            } else {
               var10 = false;
            }

            if (var10) {
               var var17 = (Iterable & List)var4;
               if (var17 instanceof Collection && ((Collection)var17).isEmpty()) {
                  var10 = true;
               } else {
                  Iterator var28 = var17.iterator();

                  while (true) {
                     if (!var28.hasNext()) {
                        var10 = true;
                        break;
                     }

                     String var18 = ((String)var28.next()).toLowerCase(Locale.ROOT);
                     Intrinsics.checkNotNullExpressionValue(var18, "toLowerCase(...)");
                     if (Intrinsics.areEqual(var18, var6)) {
                        var10 = false;
                        break;
                     }
                  }
               }

               if (var10) {
                  return false;
               }
            }
         }

         var var19 = var1.strList("excluded_vehicle_makes");
         boolean var12;
         if (var19 instanceof Collection && ((Collection)var19).isEmpty()) {
            var12 = false;
         } else {
            Iterator var20 = var19.iterator();

            while (true) {
               if (!var20.hasNext()) {
                  var12 = false;
                  break;
               }

               String var29 = ((String)var20.next()).toLowerCase(Locale.ROOT);
               Intrinsics.checkNotNullExpressionValue(var29, "toLowerCase(...)");
               if (Intrinsics.areEqual(var29, var6)) {
                  var12 = true;
                  break;
               }
            }
         }

         if (var12) {
            return false;
         } else {
            String var21 = var1.str("visible_when_key");
            if (var21 != null) {
               String var22 = this.api.string(var21);
               var var30 = var1.strList("visible_when_values");
               if (var30 instanceof Collection && ((Collection)var30).isEmpty()) {
                  var12 = true;
               } else {
                  Iterator var31 = var30.iterator();

                  while (true) {
                     if (!var31.hasNext()) {
                        var12 = true;
                        break;
                     }

                     if (Intrinsics.areEqual((String)var31.next(), var22)) {
                        var12 = false;
                        break;
                     }
                  }
               }

               if (var12) {
                  return false;
               }
            }

            String var23 = var1.str("requires_capability");
            if (var23 != null && !this.api.bool(var23)) {
               return false;
            } else if (RADAR_REQUIRED.contains(var1.getKey()) && !this.api.bool("HasRadar")) {
               return false;
            } else if (Intrinsics.areEqual(var1.getKey(), "AlphaLongitudinalEnabled") && !this.api.bool("AlphaLongitudinalAvailable")) {
               return false;
            } else if (!this.tierVisible(var1)) {
               return false;
            } else {
               String var8 = var1.getParentKey();

               while (var8 != null) {
                  var1 = this.byKey.get(var8);
                  if (var1 == null) {
                     break;
                  }

                  if (!this.visible(var1)) {
                     return false;
                  }

                  if (!Intrinsics.areEqual(var1.getUiType(), "group") && !this.api.bool(var1.getKey())) {
                     return false;
                  }

                  var8 = var1.getParentKey();
               }

               return true;
            }
         }
      }
   }

   public final boolean getNeedsOffroad() {
      return this.needsOffroad;
   }

   public final void onTick() {
      if (this.api.bool("GalaxyDeveloperMode") != this.advanced) {
         this.advanced ^= true;
         this.build();
      } else {
         int var1 = this.api.getVersion();
         Telemetry var2 = Link.INSTANCE.getTelemetry();
         Boolean var4;
         if (var2 != null) {
            var4 = var2.getEnabled();
         } else {
            var4 = null;
         }

         Boolean var3 = DriveState.INSTANCE.onroad();
         if (!Intrinsics.areEqual(var1 + "|" + var4 + "|" + var3, this.lastSig)) {
            this.syncAll();
         }
      }
   }

   public final void syncAll() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 000: aload 0
      // 001: getfield com/starpilot/starview/CatalogPanel.api Lcom/starpilot/starview/GalaxyApi;
      // 004: invokevirtual com/starpilot/starview/GalaxyApi.getVersion ()I
      // 007: istore 1
      // 008: getstatic com/starpilot/starview/Link.INSTANCE Lcom/starpilot/starview/Link;
      // 00b: invokevirtual com/starpilot/starview/Link.getTelemetry ()Lcom/starpilot/starview/Telemetry;
      // 00e: astore 4
      // 010: aload 4
      // 012: ifnull 022
      // 015: aload 4
      // 017: invokevirtual com/starpilot/starview/Telemetry.getEnabled ()Z
      // 01a: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 01d: astore 4
      // 01f: goto 025
      // 022: aconst_null
      // 023: astore 4
      // 025: getstatic com/starpilot/starview/DriveState.INSTANCE Lcom/starpilot/starview/DriveState;
      // 028: invokevirtual com/starpilot/starview/DriveState.onroad ()Ljava/lang/Boolean;
      // 02b: astore 5
      // 02d: aload 0
      // 02e: new java/lang/StringBuilder
      // 031: dup
      // 032: invokespecial java/lang/StringBuilder.<init> ()V
      // 035: iload 1
      // 036: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 039: ldc_w "|"
      // 03c: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 03f: aload 4
      // 041: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 044: ldc_w "|"
      // 047: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 04a: aload 5
      // 04c: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 04f: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 052: putfield com/starpilot/starview/CatalogPanel.lastSig Ljava/lang/String;
      // 055: bipush 1
      // 056: istore 3
      // 057: aload 0
      // 058: bipush 1
      // 059: putfield com/starpilot/starview/CatalogPanel.syncing Z
      // 05c: bipush 0
      // 05d: istore 2
      // 05e: aload 0
      // 05f: getfield com/starpilot/starview/CatalogPanel.rows Ljava/util/ArrayList;
      // 062: invokevirtual java/util/ArrayList.iterator ()Ljava/util/Iterator;
      // 065: astore 4
      // 067: aload 4
      // 069: ldc_w "iterator(...)"
      // 06c: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 06f: aload 4
      // 071: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 076: ifeq 0d4
      // 079: aload 4
      // 07b: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 080: astore 5
      // 082: aload 5
      // 084: ldc_w "next(...)"
      // 087: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
      // 08a: aload 5
      // 08c: checkcast com/starpilot/starview/CatalogPanel$Row
      // 08f: astore 6
      // 091: aload 6
      // 093: invokevirtual com/starpilot/starview/CatalogPanel$Row.getSync ()Lkotlin/jvm/functions/Function0;
      // 096: invokeinterface kotlin/jvm/functions/Function0.invoke ()Ljava/lang/Object; 1
      // 09b: pop
      // 09c: goto 06f
      // 09f: astore 5
      // 0a1: aload 6
      // 0a3: invokevirtual com/starpilot/starview/CatalogPanel$Row.getP ()Lcom/starpilot/starview/GalaxyApi$Param;
      // 0a6: invokevirtual com/starpilot/starview/GalaxyApi$Param.getKey ()Ljava/lang/String;
      // 0a9: astore 7
      // 0ab: new java/lang/StringBuilder
      // 0ae: astore 6
      // 0b0: aload 6
      // 0b2: invokespecial java/lang/StringBuilder.<init> ()V
      // 0b5: ldc_w "StarView/Catalog"
      // 0b8: aload 6
      // 0ba: ldc_w "sync "
      // 0bd: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0c0: aload 7
      // 0c2: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0c5: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 0c8: aload 5
      // 0ca: checkcast java/lang/Throwable
      // 0cd: invokestatic android/util/Log.e (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      // 0d0: pop
      // 0d1: goto 06f
      // 0d4: aload 0
      // 0d5: bipush 0
      // 0d6: putfield com/starpilot/starview/CatalogPanel.syncing Z
      // 0d9: aload 0
      // 0da: getfield com/starpilot/starview/CatalogPanel.emptyNote Landroid/view/View;
      // 0dd: astore 4
      // 0df: aload 4
      // 0e1: ifnull 161
      // 0e4: aload 0
      // 0e5: getfield com/starpilot/starview/CatalogPanel.rows Ljava/util/ArrayList;
      // 0e8: checkcast java/lang/Iterable
      // 0eb: astore 5
      // 0ed: aload 5
      // 0ef: instanceof java/util/Collection
      // 0f2: ifeq 107
      // 0f5: aload 5
      // 0f7: checkcast java/util/Collection
      // 0fa: invokeinterface java/util/Collection.isEmpty ()Z 1
      // 0ff: ifeq 107
      // 102: bipush 0
      // 103: istore 1
      // 104: goto 154
      // 107: aload 5
      // 109: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 10e: astore 5
      // 110: aload 5
      // 112: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 117: ifeq 152
      // 11a: aload 5
      // 11c: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 121: checkcast com/starpilot/starview/CatalogPanel$Row
      // 124: astore 6
      // 126: aload 6
      // 128: invokevirtual com/starpilot/starview/CatalogPanel$Row.getP ()Lcom/starpilot/starview/GalaxyApi$Param;
      // 12b: invokevirtual com/starpilot/starview/GalaxyApi$Param.getKey ()Ljava/lang/String;
      // 12e: ldc_w "__mode"
      // 131: invokestatic kotlin/jvm/internal/Intrinsics.areEqual (Ljava/lang/Object;Ljava/lang/Object;)Z
      // 134: ifne 147
      // 137: aload 6
      // 139: invokevirtual com/starpilot/starview/CatalogPanel$Row.getView ()Landroid/view/View;
      // 13c: invokevirtual android/view/View.getVisibility ()I
      // 13f: ifne 147
      // 142: bipush 1
      // 143: istore 1
      // 144: goto 149
      // 147: bipush 0
      // 148: istore 1
      // 149: iload 1
      // 14a: ifeq 110
      // 14d: iload 3
      // 14e: istore 1
      // 14f: goto 154
      // 152: bipush 0
      // 153: istore 1
      // 154: iload 1
      // 155: ifeq 15b
      // 158: bipush 8
      // 15a: istore 2
      // 15b: aload 4
      // 15d: iload 2
      // 15e: invokevirtual android/view/View.setVisibility (I)V
      // 161: return
      // 162: astore 4
      // 164: aload 0
      // 165: bipush 0
      // 166: putfield com/starpilot/starview/CatalogPanel.syncing Z
      // 169: aload 4
      // 16b: athrow
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }
   }

   private static final class Num {
      private final String desc;
      private final String label;
      private final double max;
      private final double min;
      private final int precision;
      private final double step;
      private final String unit;

      public Num(double var1, double var3, double var5, int var7, String var8, String var9, String var10) {
         Intrinsics.checkNotNullParameter(var8, "unit");
         Intrinsics.checkNotNullParameter(var9, "label");
         super();
         this.min = var1;
         this.max = var3;
         this.step = var5;
         this.precision = var7;
         this.unit = var8;
         this.label = var9;
         this.desc = var10;
      }

      public final String getDesc() {
         return this.desc;
      }

      public final String getLabel() {
         return this.label;
      }

      public final double getMax() {
         return this.max;
      }

      public final double getMin() {
         return this.min;
      }

      public final int getPrecision() {
         return this.precision;
      }

      public final double getStep() {
         return this.step;
      }

      public final String getUnit() {
         return this.unit;
      }
   }

   private static final class Row {
      private final int depth;
      private final GalaxyApi.Param p;
      private final Function0<Unit> sync;
      private final View view;

      public Row(GalaxyApi.Param var1, View var2, int var3, Function0<Unit> var4) {
         Intrinsics.checkNotNullParameter(var1, "p");
         Intrinsics.checkNotNullParameter(var2, "view");
         Intrinsics.checkNotNullParameter(var4, "sync");
         super();
         this.p = var1;
         this.view = var2;
         this.depth = var3;
         this.sync = var4;
      }

      public final int getDepth() {
         return this.depth;
      }

      public final GalaxyApi.Param getP() {
         return this.p;
      }

      public final Function0<Unit> getSync() {
         return this.sync;
      }

      public final View getView() {
         return this.view;
      }
   }
}
