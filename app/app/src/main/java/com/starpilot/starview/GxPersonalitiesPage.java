package com.starpilot.starview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewGroup.LayoutParams;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.SortedMap;
import java.util.TreeSet;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.ranges.RangesKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

public final class GxPersonalitiesPage extends GxPage {
   private final HashSet<String> advancedCustom;
   private final HashMap<String, String> advancedErrors;
   private final HashSet<String> advancedOpen;
   private final HashMap<String, Double> advancedPendingValue;
   private final HashMap<String, Runnable> advancedTimers;
   private boolean busy;
   private final List<Pair<String, String>> categories;
   private LinearLayout content;
   private boolean contextPending;
   private final HashMap<String, String> curveErrors;
   private boolean curvePending;
   private JSONObject data;
   private final HashMap<String, List<Double>> drafts;
   private GxPersonalitiesPage.Drag drag;
   private String error;
   private final List<String> jerkSuffixes;
   private String lastSig;
   private boolean loadPending;
   private Map<String, GalaxyApi.Param> meta;
   private final boolean needsOffroad;
   private String notice;
   private Boolean offroad;
   private Boolean onroad;
   private final HashMap<String, List<TextView>> pointViews;
   private final Map<String, String> profileKeys;
   private final List<String> profilesIds;
   private boolean ready;
   private boolean recovery;
   private final HashMap<String, Object> vals;

   // $VF: synthetic method
   public static Unit $r8$lambda$1_fNXL7LFoULZy85UII4oih_Nkk/* $VF was: $r8$lambda$1_fNXL7LFoULZy85UII4oih_Nkk*/(GxPersonalitiesPage var0) {
      return build$lambda$0(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$1TIRLXAHd8bTo2x3mfbTJ6wEg7o(GxPersonalitiesPage var0, boolean var1, String var2, String var3, List var4, boolean var5) {
      return saveCurve$lambda$43(var0, var1, var2, var3, var4, var5);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$4oP3EfTu_55m0hhfgex9uMH3268(GxPersonalitiesPage var0, boolean var1) {
      return nudgeAdvanced$lambda$53$lambda$52(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$6FBx8fHxnZOt15NUhvlKRHnDpYk(GxPersonalitiesPage var0, boolean var1) {
      return toggle$lambda$34(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$7_Si5I8wXa4FGII1VYQclnVuch8/* $VF was: $r8$lambda$7_Si5I8wXa4FGII1VYQclnVuch8*/(boolean var0) {
      return write$lambda$27(var0);
   }

   // $VF: synthetic method
   public static CharSequence $r8$lambda$7sJS698s3pSGRG1Za2I0EqwKeVw(GxPersonalitiesPage var0, String var1) {
      return signature$lambda$55(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$8SQO4BCGTYMCnLWknbFrNsB982o(
      GxPersonalitiesPage var0, double var1, double var3, String var5, String var6, int var7, String var8
   ) {
      return editPoint$lambda$45(var0, var1, var3, var5, var6, var7, var8);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$9NbUv5T5UcYGf4Pj50dRPwSHOS8(GxPersonalitiesPage var0, String var1, JSONObject var2, boolean var3) {
      return load$lambda$23$lambda$22(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Az55lA85121hV1WEvoeNYzWXpsk(GxPersonalitiesPage var0, GalaxyApi.Param var1, Object var2, Function2 var3) {
      return setAdvanced$lambda$49$lambda$47(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$B7efZfqvEIrilrFGyyF4NSYXl9A(GxPersonalitiesPage var0, String var1, String var2) {
      return renderProfile$lambda$76$lambda$75(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$CYgHAq_N12dA1EwJaOZ4Dxtt7xI/* $VF was: $r8$lambda$CYgHAq_N12dA1EwJaOZ4Dxtt7xI*/(
      GxPersonalitiesPage var0, HashMap var1, boolean var2, Function1 var3, String var4, JSONObject var5
   ) {
      return fetchContext$lambda$18$lambda$17(var0, var1, var2, var3, var4, var5);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$DPG19_suf8IWAAzF53zy58uAgZ0/* $VF was: $r8$lambda$DPG19_suf8IWAAzF53zy58uAgZ0*/(
      GxPersonalitiesPage var0, HashMap var1, String var2, JSONObject var3, boolean var4, Function1 var5, String var6, String var7
   ) {
      return fetchContext$lambda$18$lambda$17$lambda$16(var0, var1, var2, var3, var4, var5, var6, var7);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$ErF3DvKPihCwHOZnsNhBDrKsWok(GxPersonalitiesPage var0, String var1, String var2, String var3, Function2 var4) {
      return choosePreset$lambda$38(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$F1mffr62rYuUHvP77bUgOOLLY6o(GxPersonalitiesPage var0, String var1, String var2, String var3, boolean var4) {
      return choosePreset$lambda$39(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$GP2VdYb5dAiOcugtptK4s0Pvbx4(GxPersonalitiesPage var0, Boolean var1, boolean var2) {
      return refreshContext$lambda$24(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$GVW2AdwCzYuQ3iya4XcOzzmDEIo(GxPersonalitiesPage var0, String var1, JSONObject var2) {
      return load$lambda$23(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$GwfUJDY7P_CJFAJWOj8dMar9OUU/* $VF was: $r8$lambda$GwfUJDY7P_CJFAJWOj8dMar9OUU*/(
      boolean var0, GxPersonalitiesPage var1, String var2
   ) {
      return renderProfile$lambda$64(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$L3c0GF1_LaUKl_fpa_0Mb7JeHVY/* $VF was: $r8$lambda$L3c0GF1_LaUKl_fpa_0Mb7JeHVY*/(
      String var0, JSONObject var1, GxPersonalitiesPage var2, Function1 var3, boolean var4
   ) {
      return write$lambda$31$lambda$30$lambda$29$lambda$28(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$LCS2rBULuY_ZY96_DWCLMpfB6rU/* $VF was: $r8$lambda$LCS2rBULuY_ZY96_DWCLMpfB6rU*/(
      Function1 var0, GalaxyApi var1, GxPersonalitiesPage var2, boolean var3, boolean var4
   ) {
      return fetchContext$lambda$18(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$LyTHuZGx0gF4YkkxP5k7esuwlRw(GxPersonalitiesPage var0, GalaxyApi.Param var1, TextView var2) {
      return renderProfile$lambda$81(var0, var1, var2);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$OaSjzhv5De7iTW7aup98ukqRKxQ(GxPersonalitiesPage var0, boolean var1) {
      return saveCurve$lambda$43$lambda$41(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Oh_EdEW0p0gHWD2vH1wN736F6rA/* $VF was: $r8$lambda$Oh_EdEW0p0gHWD2vH1wN736F6rA*/(GxPersonalitiesPage var0, Function2 var1) {
      return migrate$lambda$35(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Rg9_sB1fNwPsPrdO56Zj1l0_IkE/* $VF was: $r8$lambda$Rg9_sB1fNwPsPrdO56Zj1l0_IkE*/(
      GxPersonalitiesPage var0, String var1, String var2, boolean var3
   ) {
      return saveCurve$lambda$43$lambda$42(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$SWJikDbwOFcC41h7GQ__pHnpmLI/* $VF was: $r8$lambda$SWJikDbwOFcC41h7GQ__pHnpmLI*/(GxPersonalitiesPage var0, boolean var1) {
      return render$lambda$56(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$TAFKsClroz6M1Tp0YpiEzAPZVH8(GxPersonalitiesPage var0, String var1, String var2, List var3, int var4) {
      return renderProfile$lambda$63(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$XMrLSCxjF_Fw7DZnJrj4Ud7YTZc(GxPersonalitiesPage var0, boolean var1, Function0 var2, Function1 var3, Function1 var4) {
      return write$lambda$31(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$ZOGx2EIm3lrzrMQcc08cHN4SkKw(GxPersonalitiesPage var0, String var1) {
      return toggle$lambda$33(var0, var1);
   }

   // $VF: synthetic method
   public static CharSequence $r8$lambda$ZfrMaz0DsX2ePYNxCavIqNSh68M(String var0) {
      return label$lambda$1(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$_LBL0iHmmrHK_bpBoTMuxjGDcoA/* $VF was: $r8$lambda$_LBL0iHmmrHK_bpBoTMuxjGDcoA*/(
      GxPersonalitiesPage var0, boolean var1, Function1 var2, String var3, GalaxyApi.Param var4
   ) {
      return setAdvanced$lambda$49(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$bpgkx_3sGCFLX0jBiahf3S3Lt2c/* $VF was: $r8$lambda$bpgkx_3sGCFLX0jBiahf3S3Lt2c*/(
      GxPersonalitiesPage var0, GalaxyApi.Param var1, boolean var2
   ) {
      return advancedPreset$lambda$50(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$catUoUv81SLVia3ZSwLQI8UxRMU(GxPersonalitiesPage var0, String var1, boolean var2, Function2 var3) {
      return toggle$lambda$32(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static void $r8$lambda$dUEDkW9NhgnBZaXFNMwk9iXuA7g(GxPersonalitiesPage var0, GalaxyApi.Param var1) {
      nudgeAdvanced$lambda$53(var0, var1);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$e8HxTgAFnmIYpJk_J2TXMVJ1fMk(GxPersonalitiesPage var0) {
      return migrate$lambda$36(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$fLDIvxUW72ABiAIwGb8uXHFUd_M/* $VF was: $r8$lambda$fLDIvxUW72ABiAIwGb8uXHFUd_M*/(GxPersonalitiesPage var0, boolean var1) {
      return renderProfile$lambda$85$lambda$84$lambda$83(var0, var1);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$gdxpFWaaRn2my_yksUltTKHl3S8(GxPersonalitiesPage var0, GalaxyApi.Param var1, boolean var2) {
      return setAdvanced$lambda$49$lambda$48(var0, var1, var2);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$h5_s_2k2AgdSxKHLaJL7I3UU0sg(GxPersonalitiesPage var0) {
      return write$lambda$26(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$hVtAsBFkc8iDWr0KoXBJ1xseiww(GxPersonalitiesPage var0, Function1 var1, String var2, JSONObject var3) {
      return write$lambda$31$lambda$30$lambda$29(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$hgcEkUH1Pcsce9v8sHYNE_PAC_4/* $VF was: $r8$lambda$hgcEkUH1Pcsce9v8sHYNE_PAC_4*/(boolean var0) {
      return setAdvanced$lambda$46(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$hmP1D53pwuAhBh_12hnJDWLUsRU/* $VF was: $r8$lambda$hmP1D53pwuAhBh_12hnJDWLUsRU*/(
      GxPersonalitiesPage var0, String var1, String var2, String var3
   ) {
      return renderProfile$lambda$76(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$iLVBE97ORGeca6C2BvhWd5QsA8I(GxPersonalitiesPage var0) {
      return render$lambda$57(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$kLyT1wsnrLIr_shhWC5fELzKfaU(GxPersonalitiesPage var0) {
      return render$lambda$58(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$m6kFkSSq_CjPaPQirYcFpUAnn3I(GxPersonalitiesPage var0, GalaxyApi.Param var1, List var2, int var3) {
      return renderProfile$lambda$88(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$nBV6QuP_OumhXGMbDxxD6S5Mwi4(GxPersonalitiesPage var0, GalaxyApi.Param var1, double var2, double var4) {
      return renderProfile$lambda$85(var0, var1, var2, var4);
   }

   // $VF: synthetic method
   public static void $r8$lambda$nXNwC2izik9caluVCCVJzVFWVNo(GxPersonalitiesPage var0, String var1, CompoundButton var2, boolean var3) {
      renderProfile$lambda$61(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$nkTLHWkcPXDYpD__CW04eD_G5Ns(GxPersonalitiesPage var0, GalaxyApi.Param var1, String var2) {
      return renderProfile$lambda$85$lambda$84(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$ozf5AJNRjrkq9siiqW1G4J_reBI/* $VF was: $r8$lambda$ozf5AJNRjrkq9siiqW1G4J_reBI*/(
      GxPersonalitiesPage var0, Function1 var1, String var2, JSONObject var3
   ) {
      return write$lambda$31$lambda$30(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static void $r8$lambda$pcWtkJZXfMyqnfBTCybJu66t_7o(GxPersonalitiesPage var0, Function0 var1) {
      whenContextIdle$lambda$25(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$q_PxzVjlIFaLgKUgJHclmKehOcc(GxPersonalitiesPage var0, GalaxyApi.Param var1, TextView var2) {
      return renderProfile$lambda$82(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$sY2wlLPxd7JoWD4gOXpESSXiOxY(String var0, String var1, List var2, boolean var3, GxPersonalitiesPage var4, Function2 var5) {
      return saveCurve$lambda$43$lambda$40(var0, var1, var2, var3, var4, var5);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$uqi4C_Yv_zrRgRkodxmaBSJB4mY(GxPersonalitiesPage var0, boolean var1) {
      return migrate$lambda$37(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$yjEmR_Opaa2HLh1P2ve3xoresN4/* $VF was: $r8$lambda$yjEmR_Opaa2HLh1P2ve3xoresN4*/(
      GxPersonalitiesPage var0, String var1, String var2, int var3
   ) {
      return renderProfile$lambda$71(var0, var1, var2, var3);
   }

   public GxPersonalitiesPage(Activity var1, GxHost var2) {
      Intrinsics.checkNotNullParameter(var1, "act");
      Intrinsics.checkNotNullParameter(var2, "gx");
      super(var1, var2);
      this.needsOffroad = true;
      this.profilesIds = CollectionsKt.listOf(new String[]{"traffic", "aggressive", "standard", "relaxed"});
      this.categories = CollectionsKt.listOf(
         new Pair[]{TuplesKt.to("acceleration", "Acceleration"), TuplesKt.to("braking", "Braking"), TuplesKt.to("following", "Following")}
      );
      this.profileKeys = MapsKt.mapOf(
         new Pair[]{
            TuplesKt.to("traffic", "TrafficPersonalityProfile"),
            TuplesKt.to("aggressive", "AggressivePersonalityProfile"),
            TuplesKt.to("standard", "StandardPersonalityProfile"),
            TuplesKt.to("relaxed", "RelaxedPersonalityProfile")
         }
      );
      this.jerkSuffixes = CollectionsKt.listOf(new String[]{"Acceleration", "Deceleration", "Danger", "SpeedDecrease", "Speed"});
      this.vals = new HashMap<>();
      this.meta = MapsKt.emptyMap();
      this.error = "";
      this.notice = "";
      this.advancedOpen = new HashSet<>();
      this.drafts = new HashMap<>();
      this.curveErrors = new HashMap<>();
      this.advancedErrors = new HashMap<>();
      this.advancedCustom = new HashSet<>();
      this.advancedPendingValue = new HashMap<>();
      this.advancedTimers = new HashMap<>();
      this.lastSig = "";
      this.pointViews = new HashMap<>();
   }

   private final void acceptData(JSONObject var1) {
      JSONObject var8 = this.validate(var1);
      JSONObject var2 = this.data;

      for (String var5 : this.profilesIds) {
         Iterator var6 = this.categories.iterator();

         while (var6.hasNext()) {
            String var3;
            String var4;
            label34: {
               var4 = (String)((Pair)var6.next()).component1();
               var3 = var5 + var4;
               if (var2 != null) {
                  var1 = var2.optJSONObject("profiles");
                  if (var1 != null) {
                     var1 = var1.optJSONObject(var5);
                     if (var1 != null) {
                        var1 = var1.optJSONObject(var4);
                        if (var1 != null) {
                           var13 = var1.toString();
                           break label34;
                        }
                     }
                  }
               }

               var13 = null;
            }

            String var9 = var8.getJSONObject("profiles").getJSONObject(var5).getJSONObject(var4).toString();
            Intrinsics.checkNotNullExpressionValue(var9, "toString(...)");
            if (this.drafts.containsKey(var3) && !Intrinsics.areEqual(var13, var9)) {
               this.discard(var5, var4);
               if (!this.busy && !this.curvePending) {
                  this.drag = null;
                  this.notice = "Saved profiles changed. The affected preview was cancelled.";
               }
            }
         }
      }

      this.data = var8;
   }

   private final String advancedMode(String var1) {
      if (this.advancedCustom.contains(var1)) {
         return "custom";
      } else {
         double var2 = GxHomeKt.access$gxHomeNum(this.vals.get(var1));
         boolean var5 = true;
         boolean var4;
         if (var2 == 100.0) {
            var4 = true;
         } else {
            var4 = false;
         }

         if (var4) {
            return "standard";
         } else {
            if (!StringsKt.endsWith$default(var1, "JerkDanger", false, 2, null)) {
               if (var2 == 50.0) {
                  var4 = var5;
               } else {
                  var4 = false;
               }

               if (var4) {
                  return "chill";
               }
            }

            return "custom";
         }
      }
   }

   private final List<GalaxyApi.Param> advancedParams(String var1) {
      Iterable var6 = this.meta.values();
      var var5 = new ArrayList();

      for (Object var7 : var6) {
         GalaxyApi.Param var8 = (GalaxyApi.Param)var7;
         boolean var4 = Intrinsics.areEqual(var8.getParentKey(), this.key(var1));
         boolean var3 = false;
         boolean var2 = var3;
         if (var4) {
            var2 = var3;
            if (StringsKt.contains$default(var8.getKey(), "Jerk", false, 2, null)) {
               var2 = true;
            }
         }

         if (var2) {
            var5.add(var7);
         }
      }

      return var5;
   }

   private final void advancedPreset(GalaxyApi.Param var1, String var2) {
      if (!this.paramLocked(var1.getKey())) {
         if (Intrinsics.areEqual(var2, "custom")) {
            this.advancedCustom.add(var1.getKey());
            this.forceRender();
         } else {
            if (Intrinsics.areEqual(var2, "chill")) {
               var2 = "50";
            } else {
               var2 = "100";
            }

            this.setAdvanced(var1, var2, new GxPersonalitiesPage$$ExternalSyntheticLambda42(this, var1));
         }
      }
   }

   private static final Unit advancedPreset$lambda$50(GxPersonalitiesPage var0, GalaxyApi.Param var1, boolean var2) {
      if (var2) {
         var0.advancedCustom.remove(var1.getKey());
      }

      var0.forceRender();
      return Unit.INSTANCE;
   }

   private final Pair<Double, Double> bounds(String var1) {
      JSONObject var2 = this.data;
      Intrinsics.checkNotNull(var2);
      List var3 = this.nums(var2.getJSONObject("bounds").getJSONArray(var1));
      return TuplesKt.to((Double)var3.get(0), (Double)var3.get(1));
   }

   private static final Unit build$lambda$0(GxPersonalitiesPage var0) {
      if (var0.ready) {
         var0.refreshContext();
      } else {
         var0.load();
      }

      return Unit.INSTANCE;
   }

   private final void choosePreset(String var1, String var2, String var3) {
      if (!Intrinsics.areEqual(this.preset(var1, var2), var3)) {
         write$default(
            this,
            new GxPersonalitiesPage$$ExternalSyntheticLambda1(this, var1, var2, var3),
            null,
            new GxPersonalitiesPage$$ExternalSyntheticLambda2(this, var1, var2, var3),
            2,
            null
         );
      }
   }

   private static final Unit choosePreset$lambda$38(GxPersonalitiesPage var0, String var1, String var2, String var3, Function2 var4) {
      Intrinsics.checkNotNullParameter(var4, "cb");
      GxPage.send$default(
         var0,
         "PUT",
         "/api/personality_profiles",
         new JSONObject().put("profile", var1).put("category", var2).put("preset", var3).put("curve", new JSONArray()).put("expected", var0.config(var1, var2)),
         0L,
         var4,
         8,
         null
      );
      return Unit.INSTANCE;
   }

   private static final Unit choosePreset$lambda$39(GxPersonalitiesPage var0, String var1, String var2, String var3, boolean var4) {
      if (var4) {
         var0.discard(var1, var2);
         var0.notice = "";
         if (Intrinsics.areEqual(var3, "custom")) {
            var0.advancedOpen.add(var1);
         }
      }

      var0.forceRender();
      return Unit.INSTANCE;
   }

   private final JSONObject config(String var1, String var2) {
      JSONObject var3 = this.data;
      Intrinsics.checkNotNull(var3);
      JSONObject var4 = var3.getJSONObject("profiles").getJSONObject(var1).getJSONObject(var2);
      Intrinsics.checkNotNullExpressionValue(var4, "getJSONObject(...)");
      return var4;
   }

   private final void discard(String var1, String var2) {
      this.drafts.remove(var1 + var2);
      this.curveErrors.remove(var1 + var2);
   }

   private final List<Double> draft(String var1, String var2) {
      List var4 = this.drafts.get(var1 + var2);
      List var3 = var4;
      if (var4 == null) {
         var3 = this.nums(this.config(var1, var2).optJSONArray("curve"));
      }

      return var3;
   }

   private final void editPoint(String var1, String var2, int var3) {
      if (!this.getEditingLocked()) {
         List var9 = this.draft(var1, var2);
         Pair var8 = this.bounds(var2);
         double var6 = ((Number)var8.component1()).doubleValue();
         double var4 = ((Number)var8.component2()).doubleValue();
         GxPage var10 = this;
         String var14 = this.label(var1);

         for (Object var11 : this.categories) {
            if (Intrinsics.areEqual(((Pair)var11).getFirst(), var2)) {
               var11 = ((Pair)var11).getSecond();
               String var18 = this.speed(this.speeds(var2).get(var3).doubleValue());
               String var13 = this.speedUnit();
               var11 = var14 + " " + var11 + " at " + var18 + " " + var13;
               String var15;
               if (Intrinsics.areEqual(var2, "following")) {
                  var15 = "seconds";
               } else {
                  var15 = "m/s²";
               }

               GxPage.prompt$default(
                  var10,
                  (String)var11,
                  var15,
                  GxHomeKt.access$gxHomeFmt(((Number)var9.get(var3)).doubleValue()),
                  8194,
                  null,
                  new GxPersonalitiesPage$$ExternalSyntheticLambda13(this, var6, var4, var1, var2, var3),
                  16,
                  null
               );
               return;
            }
         }

         throw new NoSuchElementException("Collection contains no element matching the predicate.");
      }
   }

   private static final Unit editPoint$lambda$45(GxPersonalitiesPage var0, double var1, double var3, String var5, String var6, int var7, String var8) {
      Intrinsics.checkNotNullParameter(var8, "raw");
      if (var0.getEditingLocked()) {
         return Unit.INSTANCE;
      } else {
         Double var12 = StringsKt.toDoubleOrNull(StringsKt.trim(var8).toString());
         if (!StringsKt.isBlank(var8) && var12 != null) {
            double var9 = var12;
            boolean var11;
            if (!Double.isInfinite(var9) && !Double.isNaN(var9)) {
               var11 = true;
            } else {
               var11 = false;
            }

            if (var11 && !(var12 < var1) && !(var12 > var3) && !(Math.abs(var12 / 0.05 - Math.round(var12 / 0.05)) > 1.0E-7)) {
               List var16 = CollectionsKt.toMutableList(var0.draft(var5, var6));
               var16.set(var7, var12);
               var0.drafts.put(var5 + var6, var16);
               var0.curveErrors.remove(var5 + var6);
               saveCurve$default(var0, var5, var6, false, 4, null);
               return Unit.INSTANCE;
            }
         }

         Map var15 = var0.curveErrors;
         var5 = var5 + var6;
         var6 = GxHomeKt.access$gxHomeFmt(var1);
         String var17 = GxHomeKt.access$gxHomeFmt(var3);
         var15.put(var5, "Edited points must be between " + var6 + " and " + var17 + ", in 0.05 increments.");
         var0.forceRender();
         return Unit.INSTANCE;
      }
   }

   private final boolean enabled(Object var1) {
      boolean var3 = true;
      boolean var2 = var3;
      if (!Intrinsics.areEqual(var1, true)) {
         var2 = var3;
         if (!Intrinsics.areEqual(var1, 1)) {
            var2 = var3;
            if (!Intrinsics.areEqual(var1, 1L)) {
               var2 = var3;
               if (!Intrinsics.areEqual(var1, "1")) {
                  var2 = var3;
                  if (!Intrinsics.areEqual(var1, "True")) {
                     if (Intrinsics.areEqual(var1, "true")) {
                        var2 = var3;
                     } else {
                        var2 = false;
                     }
                  }
               }
            }
         }
      }

      return var2;
   }

   private final void fetchContext(boolean var1, Function1<? super Boolean, Unit> var2) {
      GalaxyApi var3 = this.getApi();
      if (var3 == null) {
         GxPersonalitiesPage var4 = this;
         var2.invoke(false);
      } else {
         var3.refreshValues(new GxPersonalitiesPage$$ExternalSyntheticLambda36(var2, var3, this, var1));
      }
   }

   private static final Unit fetchContext$lambda$18(Function1 var0, GalaxyApi var1, GxPersonalitiesPage var2, boolean var3, boolean var4) {
      if (!var4) {
         var0.invoke(false);
         return Unit.INSTANCE;
      } else {
         var2.getJson("/api/device/status", new GxPersonalitiesPage$$ExternalSyntheticLambda9(var2, new HashMap<>(var1.getValues()), var3, var0));
         return Unit.INSTANCE;
      }
   }

   private static final Unit fetchContext$lambda$18$lambda$17(
      GxPersonalitiesPage var0, HashMap var1, boolean var2, Function1 var3, String var4, JSONObject var5
   ) {
      var0.getText("/api/params?key=IsOffroad", new GxPersonalitiesPage$$ExternalSyntheticLambda3(var0, var1, var4, var5, var2, var3));
      return Unit.INSTANCE;
   }

   private static final Unit fetchContext$lambda$18$lambda$17$lambda$16(
      GxPersonalitiesPage var0, HashMap var1, String var2, JSONObject var3, boolean var4, Function1 var5, String var6, String var7
   ) {
      Intrinsics.checkNotNullParameter(var7, "t");
      Boolean var11 = var0.roadFlag(var1.get("IsOnroad"));
      Object var10 = null;
      Boolean var9 = var11;
      if (var11 == null) {
         if (var2 == null && var3 != null) {
            var9 = Intrinsics.areEqual(var3.optString("status"), "Driving");
         } else {
            var9 = null;
         }
      }

      Boolean var13;
      if (var1.containsKey("IsOffroad")) {
         var13 = var0.roadFlag(var1.get("IsOffroad"));
      } else {
         var13 = null;
      }

      Boolean var12 = var13;
      if (var13 == null) {
         Boolean var14;
         if (var6 == null) {
            var14 = var0.roadFlag(StringsKt.trim(var7).toString());
         } else {
            var14 = null;
         }

         var12 = var14;
         if (var14 == null) {
            var12 = (Boolean)var10;
            if (var9 != null) {
               boolean var8 = var9;
               var12 = var8 ^ true;
            }
         }
      }

      if (var4) {
         var0.vals.clear();
         var0.vals.putAll(var1);
         var0.vals.put("IsOnroad", var9);
         var0.vals.put("IsOffroad", var12);
         var0.onroad = var9;
         var0.offroad = var12;
      }

      var5.invoke(true);
      return Unit.INSTANCE;
   }

   private final void forceRender() {
      this.lastSig = "";
      this.render();
   }

   private final boolean getEditingLocked() {
      boolean var1;
      if (!this.getLocked() && !this.curvePending && !this.getMigrationRequired()) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   private final boolean getLocked() {
      boolean var1;
      if (this.ready && !this.busy && this.getRoadStateKnown()) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   private final boolean getMigrationRequired() {
      JSONObject var3 = this.data;
      boolean var2 = false;
      boolean var1 = var2;
      if (var3 != null) {
         var1 = var2;
         if (var3.optBoolean("migration_required", false)) {
            var1 = true;
         }
      }

      return var1;
   }

   private final boolean getRoadStateKnown() {
      boolean var1;
      if (this.onroad != null && this.offroad != null && !Intrinsics.areEqual(this.onroad, this.offroad)) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private final double graphMax(String var1, String var2) {
      GxPersonalitiesPage.Drag var5 = this.drag;
      if (var5 != null && Intrinsics.areEqual(var5.getProfile(), var1) && Intrinsics.areEqual(var5.getCategory(), var2)) {
         return var5.getMax();
      } else {
         Double var6 = CollectionsKt.maxOrNull(
            CollectionsKt.plus(CollectionsKt.plus(CollectionsKt.listOf(this.bounds(var2).getSecond()), this.draft(var1, var2)), this.reference(var1, var2))
         );
         double var3;
         if (var6 != null) {
            var3 = var6;
         } else {
            var3 = 1.0;
         }

         return var3;
      }
   }

   private final double graphMin(String var1, String var2) {
      GxPersonalitiesPage.Drag var5 = this.drag;
      if (var5 != null && Intrinsics.areEqual(var5.getProfile(), var1) && Intrinsics.areEqual(var5.getCategory(), var2)) {
         return var5.getMin();
      } else {
         Double var6 = CollectionsKt.minOrNull(
            CollectionsKt.plus(CollectionsKt.plus(CollectionsKt.listOf(this.bounds(var2).getFirst()), this.draft(var1, var2)), this.reference(var1, var2))
         );
         double var3;
         if (var6 != null) {
            var3 = var6;
         } else {
            var3 = 0.0;
         }

         return var3;
      }
   }

   private final boolean isOnroad() {
      return Intrinsics.areEqual(this.onroad, true);
   }

   private final String key(String var1) {
      String var2 = this.profileKeys.get(var1);
      var1 = var2;
      if (var2 == null) {
         var1 = "";
      }

      return var1;
   }

   private final String label(String var1) {
      return StringsKt.replace$default(
         CollectionsKt.joinToString$default(
            StringsKt.split$default(var1, new String[]{"_"}, false, 0, 6, null),
            " ",
            null,
            null,
            0,
            null,
            new GxPersonalitiesPage$$ExternalSyntheticLambda24(),
            30,
            null
         ),
         " +",
         "+",
         false,
         4,
         null
      );
   }

   private static final CharSequence label$lambda$1(String var0) {
      Intrinsics.checkNotNullParameter(var0, "s");
      switch (var0.hashCode()) {
         case -1106578487:
            if (var0.equals("legacy")) {
               String var8 = "Previous";
               return var8;
            }
            break;
         case 0:
            String var6 = "";
            if (var0.equals("")) {
               return var6;
            }
            break;
         case 3444122:
            if (var0.equals("plus")) {
               String var1 = "+";
               return var1;
            }
      }

      String var2 = var0.substring(0, 1);
      Intrinsics.checkNotNullExpressionValue(var2, "substring(...)");
      Locale var4 = Locale.ROOT;
      Intrinsics.checkNotNullExpressionValue(var4, "ROOT");
      String var5 = var2.toUpperCase(var4);
      Intrinsics.checkNotNullExpressionValue(var5, "toUpperCase(...)");
      var0 = var0.substring(1);
      Intrinsics.checkNotNullExpressionValue(var0, "substring(...)");
      String var7 = var5 + var0;
      return var7;
   }

   private final void load() {
      if (!this.busy && !this.loadPending && !this.contextPending) {
         this.loadPending = true;
         this.ready = false;
         this.getJson("/api/personality_profiles", new GxPersonalitiesPage$$ExternalSyntheticLambda27(this));
      }
   }

   private static final Unit load$lambda$23(GxPersonalitiesPage var0, String var1, JSONObject var2) {
      var0.fetchContext(true, new GxPersonalitiesPage$$ExternalSyntheticLambda7(var0, var1, var2));
      return Unit.INSTANCE;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private static final Unit load$lambda$23$lambda$22(GxPersonalitiesPage var0, String var1, JSONObject var2, boolean var3) {
      label230: {
         label240: {
            Exception var5;
            var0.loadPending = false;
            label227:
            if (var1 == null && var2 != null) {
               try {
                  var75 = var0.getApi();
               } catch (Exception var35) {
                  var5 = var35;
                  String var48 = var35.getMessage();
                  var1 = var48;
                  if (var48 != null) {
                     break label240;
                  }
                  break label227;
               }

               label234: {
                  if (var3 && var75 != null) {
                     try {
                        if (!var75.getCatalog().isEmpty()) {
                           break label234;
                        }
                     } catch (Exception var34) {
                        var5 = var34;
                        String var49 = var34.getMessage();
                        var1 = var49;
                        if (var49 != null) {
                           break label240;
                        }
                        break label227;
                     }
                  }

                  if (var75 != null) {
                     try {
                        var75.loadCatalog();
                     } catch (Exception var33) {
                        var5 = var33;
                        String var50 = var33.getMessage();
                        var1 = var50;
                        if (var50 != null) {
                           break label240;
                        }
                        break label227;
                     }
                  }

                  try {
                     IllegalStateException var39 = new IllegalStateException("Settings metadata is unavailable. Retrying automatically…");
                     throw var39;
                  } catch (Exception var32) {
                     var5 = var32;
                     String var51 = var32.getMessage();
                     var1 = var51;
                     if (var51 != null) {
                        break label240;
                     }
                     break label227;
                  }
               }

               try {
                  var0.acceptData(var2);
                  var40 = new LinkedHashMap();
                  var76 = var75.getCatalog().iterator();
               } catch (Exception var29) {
                  var5 = var29;
                  String var52 = var29.getMessage();
                  var1 = var52;
                  if (var52 != null) {
                     break label240;
                  }
                  break label227;
               }

               while (true) {
                  try {
                     if (!var76.hasNext()) {
                        break;
                     }

                     var54 = ((GalaxyApi.Category)var76.next()).getParams().iterator();
                  } catch (Exception var31) {
                     var5 = var31;
                     String var53 = var31.getMessage();
                     var1 = var53;
                     if (var53 != null) {
                        break label240;
                     }
                     break label227;
                  }

                  while (true) {
                     try {
                        if (!var54.hasNext()) {
                           break;
                        }

                        GalaxyApi.Param var6 = (GalaxyApi.Param)var54.next();
                        var40.put(var6.getKey(), var6);
                     } catch (Exception var30) {
                        var5 = var30;
                        String var55 = var30.getMessage();
                        var1 = var55;
                        if (var55 != null) {
                           break label240;
                        }
                        break label227;
                     }
                  }
               }

               ArrayList var7;
               Iterator var8;
               Collection var79;
               try {
                  var0.meta = var40;
                  var79 = CollectionsKt.listOf("CustomPersonalities");
                  Iterable var57 = var0.profilesIds;
                  ArrayList var77 = new ArrayList();
                  var7 = var77;
                  var8 = var57.iterator();
               } catch (Exception var26) {
                  var5 = var26;
                  String var56 = var26.getMessage();
                  var1 = var56;
                  if (var56 != null) {
                     break label240;
                  }
                  break label227;
               }

               while (true) {
                  Collection var9;
                  String var10;
                  ArrayList var11;
                  try {
                     if (!var8.hasNext()) {
                        break;
                     }

                     var10 = (String)var8.next();
                     var9 = CollectionsKt.listOf(var0.key(var10));
                     var78 = var0.jerkSuffixes;
                     ArrayList var59 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var78, 10));
                     var11 = var59;
                  } catch (Exception var27) {
                     var5 = var27;
                     String var58 = var27.getMessage();
                     var1 = var58;
                     if (var58 != null) {
                        break label240;
                     }
                     break label227;
                  }

                  Iterable var60 = var78;

                  Iterator var12;
                  try {
                     var12 = var60.iterator();
                  } catch (Exception var25) {
                     var5 = var25;
                     String var61 = var25.getMessage();
                     var1 = var61;
                     if (var61 != null) {
                        break label240;
                     }
                     break label227;
                  }

                  while (true) {
                     try {
                        if (var12.hasNext()) {
                           String var13 = (String)var12.next();
                           String var14 = var0.label(var10);
                           StringBuilder var15 = new StringBuilder();
                           var11.add(var15.append(var14).append("Jerk").append(var13).toString());
                           continue;
                        }
                     } catch (Exception var28) {
                        var5 = var28;
                        String var62 = var28.getMessage();
                        var1 = var62;
                        if (var62 != null) {
                           break label240;
                        }
                        break label227;
                     }

                     try {
                        var64 = var11;
                     } catch (Exception var24) {
                        var5 = var24;
                        String var63 = var24.getMessage();
                        var1 = var63;
                        if (var63 != null) {
                           break label240;
                        }
                        break label227;
                     }

                     try {
                        var66 = CollectionsKt.plus(var9, var64);
                     } catch (Exception var23) {
                        var5 = var23;
                        String var65 = var23.getMessage();
                        var1 = var65;
                        if (var65 != null) {
                           break label240;
                        }
                        break label227;
                     }

                     try {
                        CollectionsKt.addAll(var7, var66);
                        break;
                     } catch (Exception var22) {
                        var5 = var22;
                        String var67 = var22.getMessage();
                        var1 = var67;
                        if (var67 != null) {
                           break label240;
                        }
                        break label227;
                     }
                  }
               }

               try {
                  var41 = var7;
               } catch (Exception var21) {
                  var5 = var21;
                  String var68 = var21.getMessage();
                  var1 = var68;
                  if (var68 != null) {
                     break label240;
                  }
                  break label227;
               }

               boolean var4;
               label154: {
                  label237: {
                     try {
                        var42 = CollectionsKt.plus(var79, var41);
                        if (var42 instanceof Collection && ((Collection)var42).isEmpty()) {
                           break label237;
                        }
                     } catch (Exception var20) {
                        var5 = var20;
                        String var69 = var20.getMessage();
                        var1 = var69;
                        if (var69 != null) {
                           break label240;
                        }
                        break label227;
                     }

                     try {
                        var43 = var42.iterator();
                     } catch (Exception var18) {
                        var5 = var18;
                        String var70 = var18.getMessage();
                        var1 = var70;
                        if (var70 != null) {
                           break label240;
                        }
                        break label227;
                     }

                     label143: {
                        try {
                           while (var43.hasNext()) {
                              String var72 = (String)var43.next();
                              if (!var0.meta.containsKey(var72)) {
                                 break label143;
                              }
                           }
                        } catch (Exception var19) {
                           var5 = var19;
                           String var71 = var19.getMessage();
                           var1 = var71;
                           if (var71 != null) {
                              break label240;
                           }
                           break label227;
                        }

                        var4 = false;
                        break label154;
                     }

                     var4 = true;
                     break label154;
                  }

                  var4 = false;
               }

               if (!var4) {
                  try {
                     var0.ready = true;
                     var0.error = "";
                     if (var0.recovery) {
                        var0.notice = "Save could not be confirmed. Showing verified saved state; review it before editing again.";
                        var0.recovery = false;
                     }
                     break label230;
                  } catch (Exception var16) {
                     var5 = var16;
                     String var73 = var16.getMessage();
                     var1 = var73;
                     if (var73 != null) {
                        break label240;
                     }
                  }
               } else {
                  try {
                     IllegalStateException var44 = new IllegalStateException("Personality metadata is incomplete. Retrying automatically…");
                     throw var44;
                  } catch (Exception var17) {
                     var5 = var17;
                     String var74 = var17.getMessage();
                     var1 = var74;
                     if (var74 != null) {
                        break label240;
                     }
                  }
               }
            } else {
               label239: {
                  try {
                  } catch (Exception var37) {
                     var5 = var37;
                     String var45 = var37.getMessage();
                     var1 = var45;
                     if (var45 != null) {
                        break label240;
                     }
                     break label239;
                  }

                  if (var1 == null) {
                     var1 = "bad reply";
                  }

                  try {
                     var46 = new IllegalStateException(var1);
                     throw var46;
                  } catch (Exception var36) {
                     var5 = var36;
                     String var47 = var36.getMessage();
                     var1 = var47;
                     if (var47 != null) {
                        break label240;
                     }
                  }
               }
            }

            var1 = var5.toString();
         }

         var0.error = var1;
      }

      var0.render();
      return Unit.INSTANCE;
   }

   private final boolean metric() {
      return this.enabled(this.vals.get("IsMetric"));
   }

   private final void migrate() {
      this.write(
         new GxPersonalitiesPage$$ExternalSyntheticLambda20(this),
         new GxPersonalitiesPage$$ExternalSyntheticLambda21(this),
         new GxPersonalitiesPage$$ExternalSyntheticLambda23(this)
      );
   }

   private static final Unit migrate$lambda$35(GxPersonalitiesPage var0, Function2 var1) {
      Intrinsics.checkNotNullParameter(var1, "cb");
      GxPage.send$default(var0, "POST", "/api/personality_profiles/migrate", null, 0L, var1, 8, null);
      return Unit.INSTANCE;
   }

   private static final boolean migrate$lambda$36(GxPersonalitiesPage var0) {
      boolean var1;
      if (!var0.getLocked() && !var0.isOnroad()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private static final Unit migrate$lambda$37(GxPersonalitiesPage var0, boolean var1) {
      var0.forceRender();
      return Unit.INSTANCE;
   }

   private final void nudgeAdvanced(GalaxyApi.Param var1, int var2, TextView var3) {
      if (!this.paramLocked(var1.getKey())) {
         Triple var12 = this.paramBounds(var1);
         double var8 = ((Number)var12.component1()).doubleValue();
         double var10 = ((Number)var12.component2()).doubleValue();
         double var6 = ((Number)var12.component3()).doubleValue();
         Double var16 = this.advancedPendingValue.get(var1.getKey());
         double var4;
         if (var16 != null) {
            var4 = var16;
         } else {
            var4 = GxHomeKt.access$gxHomeNum(this.vals.get(var1.getKey()));
         }

         var4 = RangesKt.coerceIn(var2 * var6 + var4, var8, var10);
         this.advancedPendingValue.put(var1.getKey(), var4);
         String var17 = GxHomeKt.access$gxHomeFmt(var4);
         var3.setText(var17 + "%");
         Runnable var13 = this.advancedTimers.remove(var1.getKey());
         if (var13 != null) {
            this.getMain().removeCallbacks(var13);
         }

         GxPersonalitiesPage$$ExternalSyntheticLambda38 var14 = new GxPersonalitiesPage$$ExternalSyntheticLambda38(this, var1);
         this.advancedTimers.put(var1.getKey(), var14);
         this.getMain().postDelayed(var14, 900L);
      }
   }

   private static final void nudgeAdvanced$lambda$53(GxPersonalitiesPage var0, GalaxyApi.Param var1) {
      var0.advancedTimers.remove(var1.getKey());
      Double var2 = var0.advancedPendingValue.remove(var1.getKey());
      if (var2 != null) {
         var0.setAdvanced(var1, GxHomeKt.access$gxHomeFmt(var2), new GxPersonalitiesPage$$ExternalSyntheticLambda28(var0));
      }
   }

   private static final Unit nudgeAdvanced$lambda$53$lambda$52(GxPersonalitiesPage var0, boolean var1) {
      var0.forceRender();
      return Unit.INSTANCE;
   }

   private final List<Double> nums(JSONArray var1) {
      List var4;
      if (var1 == null) {
         var4 = CollectionsKt.emptyList();
      } else {
         Iterable var3 = RangesKt.until(0, var1.length());
         var var2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var3, 10));
         Iterator var5 = var3.iterator();

         while (var5.hasNext()) {
            var2.add(GxHomeKt.access$gxHomeNum(var1.opt(((IntIterator)var5).nextInt())));
         }

         var4 = var2;
      }

      return var4;
   }

   private final List<String> options(String var1, String var2) {
      JSONObject var3 = this.data;
      Intrinsics.checkNotNull(var3);
      JSONArray var10 = var3.getJSONObject("options").getJSONArray(var1);
      Intrinsics.checkNotNullExpressionValue(var10, "getJSONArray(...)");
      List var4 = this.strings(var10);
      List var11;
      if (Intrinsics.areEqual(var1, "following")) {
         var11 = CollectionsKt.listOf(new String[]{"close", "medium", "far", "traffic", "custom"});
      } else {
         var11 = CollectionsKt.listOf(new String[]{"eco", "standard", "sport", "sport_plus", "custom"});
      }

      Iterable var5 = var11;
      var var12 = new ArrayList();

      for (Object var6 : var5) {
         if (var4.contains((String)var6)) {
            var12.add(var6);
         }
      }

      label34: {
         List var13 = var12;
         var14 = CollectionsKt.toMutableList(var13);
         JSONObject var16 = this.data;
         Intrinsics.checkNotNull(var16);
         JSONObject var17 = var16.optJSONObject("profiles");
         if (var17 != null) {
            JSONObject var7 = var17.optJSONObject(var2);
            if (var7 != null) {
               JSONObject var8 = var7.optJSONObject(var1);
               if (var8 != null) {
                  var2 = var8.optString("preset");
                  break label34;
               }
            }
         }

         var2 = null;
      }

      if (Intrinsics.areEqual(var1, "following") && var2 != null && StringsKt.startsWith$default(var2, "legacy_", false, 2, null) && var4.contains(var2)) {
         var14.add(0, var2);
      }

      return var14;
   }

   private final Triple<Double, Double, Double> paramBounds(GalaxyApi.Param var1) {
      boolean var8 = new Regex("^(Traffic|Aggressive|Standard|Relaxed)Jerk(Acceleration|Deceleration|Danger|SpeedDecrease|Speed)$").matches(var1.getKey());
      double var2 = 1.0;
      if (var8) {
         return new Triple<>(25.0, 200.0, 1.0);
      } else {
         var8 = Intrinsics.areEqual(var1.getDataType(), "float");
         double var6 = var1.num("min", 0.0);
         double var4 = var1.num("max", 100.0);
         if (var8) {
            var2 = 0.01;
         }

         return new Triple<>(var6, var4, var1.num("step", var2));
      }
   }

   private final boolean paramLocked(String var1) {
      GalaxyApi.Param var4 = this.meta.get(var1);
      if (var4 == null) {
         return true;
      } else if (this.getEditingLocked()) {
         return true;
      } else if (GxHomeKt.access$gxHomeTruthy(var4.getO().opt("requires_parked")) && !GxHomeKt.access$gxHomeTruthy(this.vals.get("VehicleParked"))) {
         return true;
      } else {
         var1 = var4.str("requires_capability");
         Object var3 = null;
         if (var1 != null) {
            boolean var2;
            if (var1.length() > 0) {
               var2 = true;
            } else {
               var2 = false;
            }

            if (!var2) {
               var1 = null;
            }

            if (var1 != null && !GxHomeKt.access$gxHomeTruthy(this.vals.get(var1))) {
               return true;
            }
         }

         var1 = var4.str("disabled_when_key_true");
         if (var1 != null) {
            boolean var9;
            if (var1.length() > 0) {
               var9 = true;
            } else {
               var9 = false;
            }

            if (!var9) {
               var1 = null;
            }

            if (var1 != null && GxHomeKt.access$gxHomeTruthy(this.vals.get(var1))) {
               return true;
            }
         }

         String var11 = var4.str("requires_nonempty_key");
         if (var11 != null) {
            boolean var10;
            if (var11.length() > 0) {
               var10 = true;
            } else {
               var10 = false;
            }

            var1 = (String)var3;
            if (var10) {
               var1 = var11;
            }

            if (var1 != null) {
               Object var8 = this.vals.get(var1);
               if (!GxHomeKt.access$gxHomeTruthy(var8) || Intrinsics.areEqual(var8, "{}")) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   private final String preset(String var1, String var2) {
      var1 = this.config(var1, var2).optString("preset");
      Intrinsics.checkNotNullExpressionValue(var1, "optString(...)");
      return var1;
   }

   private final JSONObject putParamBody(String var1, Object var2) {
      JSONObject var3 = new JSONObject().put("key", var1).put("value", var2);
      Intrinsics.checkNotNullExpressionValue(var3, "put(...)");
      return var3;
   }

   private final List<Double> reference(String var1, String var2) {
      JSONObject var3 = this.data;
      if (var3 != null) {
         var3 = var3.optJSONObject("reference_curves");
         if (var3 != null) {
            JSONObject var4 = var3.optJSONObject(var1);
            if (var4 != null) {
               JSONArray var7 = var4.optJSONArray(var2);
               return this.nums(var7);
            }
         }
      }

      Object var5 = null;
      return this.nums((JSONArray)var5);
   }

   private final void refreshContext() {
      if (!this.busy && this.ready && !this.contextPending) {
         this.contextPending = true;
         Boolean var1 = this.roadFlag(this.vals.get("IsOnroad"));
         this.fetchContext(true ^ this.busy, new GxPersonalitiesPage$$ExternalSyntheticLambda12(this, var1));
      }
   }

   private static final Unit refreshContext$lambda$24(GxPersonalitiesPage var0, Boolean var1, boolean var2) {
      var0.contextPending = false;
      if (!var2) {
         var0.ready = false;
         var0.error = "Connection lost. Reconnecting…";
         var0.render();
         return Unit.INSTANCE;
      } else {
         Boolean var3 = var0.roadFlag(var0.vals.get("IsOnroad"));
         if (!var0.getRoadStateKnown() || var1 != null && !Intrinsics.areEqual(var3, var1)) {
            var0.drag = null;
            var0.drafts.clear();
         }

         var0.render();
         return Unit.INSTANCE;
      }
   }

   private final void render() {
      if (this.drag == null && this.advancedTimers.isEmpty()) {
         String var4 = this.signature();
         if (!Intrinsics.areEqual(var4, this.lastSig)) {
            this.lastSig = var4;
            LinearLayout var7 = this.content;
            Object var5 = null;
            Object var6 = null;
            LinearLayout var13 = var7;
            if (var7 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("content");
               var13 = null;
            }

            var13.removeAllViews();
            this.pointViews.clear();
            GxPage var8 = this;
            var7 = this.content;
            LinearLayout var14 = var7;
            if (var7 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("content");
               var14 = null;
            }

            GxPage.Row var15 = GxPage.row$default(
               var8, "Driving Personalities", "Acceleration, braking and following for each driving style.", (ViewGroup)var14, 0, 8, null
            );
            var15.getTitleView().setTypeface(Typeface.DEFAULT_BOLD);
            Switch var16 = var15.switch_(this.enabled(this.vals.get("CustomPersonalities")), new GxPersonalitiesPage$$ExternalSyntheticLambda39(this));
            boolean var3 = this.paramLocked("CustomPersonalities");
            boolean var2 = true;
            var16.setEnabled(var3 ^ true);
            boolean var1;
            if (this.error.length() > 0) {
               var1 = true;
            } else {
               var1 = false;
            }

            if (var1) {
               String var40 = this.error;
               var7 = this.content;
               LinearLayout var17 = var7;
               if (var7 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("content");
                  var17 = null;
               }

               this.note(var40, (ViewGroup)var17).setTextColor(CarUi.Companion.getWARN());
            }

            if (this.error.length() > 0) {
               var1 = true;
            } else {
               var1 = false;
            }

            if (var1 && !this.ready) {
               GxPage var9 = this;
               Pair var41 = TuplesKt.to("Retry loading", new GxPersonalitiesPage$$ExternalSyntheticLambda40(this));
               var7 = this.content;
               LinearLayout var18 = var7;
               if (var7 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("content");
                  var18 = null;
               }

               ViewGroup var19 = (ViewGroup)var18;
               LinearLayout var20 = GxPage.buttons$default(var9, new Pair[]{var41}, var19, false, 4, null);
               if (this.busy || this.loadPending || this.contextPending) {
                  var20.setAlpha(0.45F);
               }
            }

            if (this.notice.length() > 0) {
               var1 = true;
            } else {
               var1 = false;
            }

            if (var1) {
               String var42 = this.notice;
               var7 = this.content;
               LinearLayout var21 = var7;
               if (var7 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("content");
                  var21 = null;
               }

               this.note(var42, (ViewGroup)var21).setTextColor(CarUi.Companion.getACCENT());
            }

            if (this.busy) {
               var7 = this.content;
               LinearLayout var22 = var7;
               if (var7 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("content");
                  var22 = null;
               }

               this.note("Saving…", (ViewGroup)var22).setTextColor(CarUi.Companion.getACCENT());
            }

            if (this.data == null) {
               if (this.error.length() == 0) {
                  var1 = var2;
               } else {
                  var1 = false;
               }

               if (var1) {
                  LinearLayout var30 = this.content;
                  if (var30 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("content");
                     var30 = (LinearLayout)var6;
                  }

                  var30.addView((View)this.empty("Loading profiles…"));
               }
            } else {
               if (!this.getRoadStateKnown()) {
                  var6 = this.content;
                  LinearLayout var23 = (LinearLayout)var6;
                  if (var6 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("content");
                     var23 = null;
                  }

                  this.note("Driving state is not confirmed. Editing is temporarily disabled.", (ViewGroup)var23);
               } else if (this.isOnroad()) {
                  var6 = this.content;
                  LinearLayout var24 = (LinearLayout)var6;
                  if (var6 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("content");
                     var24 = null;
                  }

                  this.note(
                        "Changes to the active profile can take effect immediately and alter acceleration, braking, or following behavior. Make adjustments only when it is safe, and stay ready to take control.",
                        (ViewGroup)var24
                     )
                     .setTextColor(CarUi.Companion.getWARN());
               }

               if (this.getMigrationRequired()) {
                  GxPage var39 = this;
                  var6 = this.content;
                  LinearLayout var25 = (LinearLayout)var6;
                  if (var6 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("content");
                     var25 = null;
                  }

                  GxPage.Row var26 = GxPage.row$default(var39, "Stored profiles need migration before editing.", null, (ViewGroup)var25, 0, 8, null);
                  var26.getTitleView().setTextColor(CarUi.Companion.getWARN());
                  TextView var27 = var26.button("Migrate profiles", true, new GxPersonalitiesPage$$ExternalSyntheticLambda41(this));
                  if (this.getLocked() || this.isOnroad()) {
                     var27.setAlpha(0.45F);
                  }
               }

               if (!this.enabled(this.vals.get("CustomPersonalities"))) {
                  LinearLayout var28 = this.content;
                  if (var28 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("content");
                     var28 = (LinearLayout)var5;
                  }

                  this.note("Enable to configure profiles. Existing defaults remain active while off.", (ViewGroup)var28);
               }

               Iterator var29 = this.profilesIds.iterator();

               while (var29.hasNext()) {
                  this.renderProfile((String)var29.next());
               }
            }
         }
      }
   }

   private static final Unit render$lambda$56(GxPersonalitiesPage var0, boolean var1) {
      var0.toggle("CustomPersonalities", var1);
      return Unit.INSTANCE;
   }

   private static final Unit render$lambda$57(GxPersonalitiesPage var0) {
      if (!var0.busy && !var0.loadPending && !var0.contextPending) {
         var0.load();
      }

      return Unit.INSTANCE;
   }

   private static final Unit render$lambda$58(GxPersonalitiesPage var0) {
      if (!var0.getLocked() && !var0.isOnroad()) {
         var0.migrate();
      }

      return Unit.INSTANCE;
   }

   private final void renderProfile(String var1) {
      String var17 = this.key(var1);
      String var14;
      if (Intrinsics.areEqual(var1, "traffic")) {
         var14 = "Traffic Mode";
      } else {
         var14 = this.label(var1);
      }

      LinearLayout var16 = this.content;
      LinearLayout var15 = var16;
      if (var16 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("content");
         var15 = null;
      }

      this.header(var14, (ViewGroup)var15);
      var15 = this.content;
      LinearLayout var40 = var15;
      if (var15 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("content");
         var40 = null;
      }

      LinearLayout var21 = this.card((ViewGroup)var40);
      var15 = new LinearLayout((Context)this.getAct());
      var15.setOrientation(0);
      var15.setGravity(16);
      TextView var53 = new TextView((Context)this.getAct());
      CharSequence var41;
      if (Intrinsics.areEqual(var1, "traffic")) {
         var41 = "Traffic Mode";
      } else {
         var41 = this.label(var1);
      }

      var53.setText(var41);
      var53.setTextSize(22.0F);
      var53.setTextColor(CarUi.Companion.getTEXT());
      var53.setTypeface(Typeface.DEFAULT_BOLD);
      var15.addView((View)var53, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
      Switch var42 = this.getUi().bigSwitch();
      var42.setChecked(this.enabled(this.vals.get(var17)));
      var42.setEnabled(this.paramLocked(var17) ^ true);
      var42.setOnCheckedChangeListener(new GxPersonalitiesPage$$ExternalSyntheticLambda0(this, var17));
      var15.addView((View)var42);
      var21.addView((View)var15);
      if (!this.enabled(this.vals.get(var17))) {
         GxPage.text$default(this, "Turn on to configure this profile.", (ViewGroup)var21, 0.0F, 0, false, 28, null);
      } else {
         for (Pair var19 : this.categories) {
            String var18 = (String)var19.component1();
            this.text((String)var19.component2(), (ViewGroup)var21, 18.0F, CarUi.Companion.getTEXT(), true)
               .setPadding(0, this.getUi().dp(12), 0, this.getUi().dp(4));
            List var85 = this.options(var18, var1);
            CarUi var20 = this.getUi();
            Iterable var23 = var85;
            var var22 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var23, 10));
            Iterator var109 = var23.iterator();

            while (var109.hasNext()) {
               var22.add(this.label((String)var109.next()));
            }

            List var105 = var22;
            Pair var106 = var20.chips(var105, new GxPersonalitiesPage$$ExternalSyntheticLambda11(this, var1, var18, var85));
            LinearLayout var96 = (LinearLayout)var106.component1();
            ((Function1)var106.component2()).invoke(var85.indexOf(this.preset(var1, var18)));
            if (this.getEditingLocked()) {
               var96.setAlpha(0.5F);
            }

            var21.addView((View)var96);
            if (Intrinsics.areEqual(this.preset(var1, var18), "dom_default")) {
               GxPage.text$default(this, "Using existing Dom default.", (ViewGroup)var21, 0.0F, 0, false, 28, null);
            }

            if (Intrinsics.areEqual(var18, "following") && StringsKt.startsWith$default(this.preset(var1, var18), "legacy_", false, 2, null)) {
               GxPage.text$default(
                  this, "Previous fixed following distance retained. Select a preset to use its current curve.", (ViewGroup)var21, 0.0F, 0, false, 28, null
               );
            }
         }

         String var55 = "following";
         boolean var12 = this.advancedOpen.contains(var1);
         CarUi var68 = this.getUi();
         if (var12) {
            var14 = "Advanced  ▲";
         } else {
            var14 = "Advanced  ▼";
         }

         View var69 = (View)CarUi.button$default(var68, var14, false, new GxPersonalitiesPage$$ExternalSyntheticLambda22(var12, this, var1), 2, null);
         android.widget.LinearLayout.LayoutParams var86 = new android.widget.LinearLayout.LayoutParams(-2, -2);
         var86.topMargin = this.getUi().dp(14);
         Unit var44 = Unit.INSTANCE;
         var21.addView(var69, (LayoutParams)var86);
         if (var12) {
            Iterator var107 = this.categories.iterator();

            while (true) {
               boolean var13 = var107.hasNext();
               var17 = "custom";
               if (!var13) {
                  float var8 = 0.5F;
                  GxPage.text$default(
                        this,
                        "Custom values are untested and may not be supported by the developer.",
                        (ViewGroup)var21,
                        0.0F,
                        CarUi.Companion.getWARN(),
                        false,
                        20,
                        null
                     )
                     .setPadding(0, this.getUi().dp(14), 0, this.getUi().dp(4));
                  Iterator var52 = this.advancedParams(var1).iterator();
                  var1 = var17;

                  while (var52.hasNext()) {
                     GalaxyApi.Param var66 = (GalaxyApi.Param)var52.next();
                     var12 = this.paramLocked(var66.getKey());
                     this.text(var66.getLabel(), (ViewGroup)var21, 18.0F, CarUi.Companion.getTEXT(), true).setPadding(0, this.getUi().dp(12), 0, 0);
                     var14 = var66.getDescription();
                     if (var14 != null) {
                        GxPage.text$default(this, var14, (ViewGroup)var21, 0.0F, 0, false, 28, null);
                     }

                     String var56 = this.advancedMode(var66.getKey());
                     if (Intrinsics.areEqual(var56, var1)) {
                        Triple var46 = this.paramBounds(var66);
                        double var6 = ((Number)var46.component1()).doubleValue();
                        double var33 = ((Number)var46.component2()).doubleValue();
                        double var32 = ((Number)var46.component3()).doubleValue();
                        LinearLayout var47 = new LinearLayout((Context)this.getAct());
                        var47.setOrientation(0);
                        var47.setGravity(16);
                        var47.setPadding(0, this.getUi().dp(6), 0, this.getUi().dp(6));
                        TextView var108 = new TextView((Context)this.getAct());
                        String var80 = GxHomeKt.access$gxHomeFmt(var6);
                        String var92 = GxHomeKt.access$gxHomeFmt(var33);
                        String var102 = GxHomeKt.access$gxHomeFmt(var32);
                        var108.setText(var80 + "–" + var92 + "% · step " + var102);
                        var108.setTextSize(15.0F);
                        var108.setTextColor(CarUi.Companion.getDIM());
                        var47.addView((View)var108, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
                        TextView var93 = new TextView((Context)this.getAct());
                        String var81 = GxHomeKt.access$gxHomeFmt(GxHomeKt.access$gxHomeNum(this.vals.get(var66.getKey())));
                        var93.setText(var81 + "%");
                        var93.setTextSize(22.0F);
                        var93.setTextColor(CarUi.Companion.getTEXT());
                        var93.setTypeface(Typeface.DEFAULT_BOLD);
                        var93.setMinWidth(this.getUi().dp(96));
                        var93.setGravity(17);
                        var47.addView((View)this.getUi().stepper("−", new GxPersonalitiesPage$$ExternalSyntheticLambda46(this, var66, var93)));
                        var47.addView((View)var93);
                        var47.addView((View)this.getUi().stepper("+", new GxPersonalitiesPage$$ExternalSyntheticLambda47(this, var66, var93)));
                        var47.addView(new View((Context)this.getAct()), (LayoutParams)(new android.widget.LinearLayout.LayoutParams(this.getUi().dp(10), 1)));
                        var68 = this.getUi();
                        var8 = 0.5F;
                        var47.addView(
                           (View)CarUi.button$default(
                              var68, "Edit", false, new GxPersonalitiesPage$$ExternalSyntheticLambda48(this, var66, var6, var33), 2, null
                           )
                        );
                        if (var12) {
                           var47.setAlpha(0.5F);
                        }

                        var21.addView((View)var47);
                     }

                     var14 = this.advancedErrors.get(var66.getKey());
                     if (var14 != null) {
                        GxPage.text$default(this, var14, (ViewGroup)var21, 0.0F, CarUi.Companion.getWARN(), false, 20, null);
                     }

                     List var49;
                     if (StringsKt.endsWith$default(var66.getKey(), "JerkDanger", false, 2, null)) {
                        var49 = CollectionsKt.listOf(new String[]{"standard", var1});
                     } else {
                        var49 = CollectionsKt.listOf(new String[]{"chill", "standard", var1});
                     }

                     var68 = this.getUi();
                     Iterable var103 = var49;
                     var var94 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var103, 10));
                     Iterator var104 = var103.iterator();

                     while (var104.hasNext()) {
                        var94.add(this.label((String)var104.next()));
                     }

                     List var95 = var94;
                     Pair var67 = var68.chips(var95, new GxPersonalitiesPage$$ExternalSyntheticLambda49(this, var66, var49));
                     LinearLayout var84 = (LinearLayout)var67.component1();
                     ((Function1)var67.component2()).invoke(var49.indexOf(var56));
                     if (var12) {
                        var84.setAlpha(var8);
                     }

                     var84.setPadding(0, this.getUi().dp(6), 0, this.getUi().dp(4));
                     var21.addView((View)var84);
                  }

                  return;
               }

               Pair var58 = (Pair)var107.next();
               String var110 = (String)var58.component1();
               String var24 = (String)var58.component2();
               if (Intrinsics.areEqual(this.preset(var1, var110), "custom")) {
                  String var25 = var1 + var110;
                  Locale var59 = Locale.ROOT;
                  Intrinsics.checkNotNullExpressionValue(var59, "ROOT");
                  var17 = var24.toLowerCase(var59);
                  Intrinsics.checkNotNullExpressionValue(var17, "toLowerCase(...)");
                  this.text("Custom " + var17 + " graph", (ViewGroup)var21, 18.0F, CarUi.Companion.getTEXT(), true).setPadding(0, this.getUi().dp(16), 0, 0);
                  GxPage var70 = this;
                  if (Intrinsics.areEqual(var110, var55)) {
                     var17 = "Seconds";
                  } else {
                     var17 = "m/s²";
                  }

                  String var87 = this.speedUnit();
                  GxPage.text$default(
                     var70,
                     var17 + " · " + var87 + ". Dashed: Dom default. Drag a point up or down, or tap a value below to type it.",
                     (ViewGroup)var21,
                     0.0F,
                     0,
                     false,
                     28,
                     null
                  );
                  View var71 = new GxPersonalitiesPage.Graph(this, var1, var110);
                  var86 = new android.widget.LinearLayout.LayoutParams(-1, this.getUi().dp(300));
                  var86.topMargin = this.getUi().dp(6);
                  var86.bottomMargin = this.getUi().dp(6);
                  Unit var62 = Unit.INSTANCE;
                  var21.addView(var71, (LayoutParams)var86);
                  double var2 = this.bounds(var110).getSecond().doubleValue();
                  List var63 = this.draft(var1, var110);
                  var var72 = var63;
                  boolean var34;
                  if (var72 instanceof Collection && ((Collection)var72).isEmpty()) {
                     var34 = 0;
                  } else {
                     label221: {
                        Iterator var73 = var72.iterator();

                        while (var73.hasNext()) {
                           if (((Number)var73.next()).doubleValue() > var2) {
                              var34 = (boolean)1;
                           } else {
                              var34 = (boolean)0;
                           }

                           if (var34) {
                              var34 = 1;
                              break label221;
                           }
                        }

                        var34 = 0;
                     }
                  }

                  if (var34) {
                     GxPage var74 = this;
                     String var89 = GxHomeKt.access$gxHomeFmt(var2);
                     GxPage.text$default(
                        var74,
                        "Saved values above " + var89 + " are shown at their original scale. Only edited points use the current authoring bounds.",
                        (ViewGroup)var21,
                        0.0F,
                        0,
                        false,
                        28,
                        null
                     );
                  }

                  String var75 = this.curveErrors.get(var25);
                  if (var75 != null) {
                     GxPage.text$default(this, var75, (ViewGroup)var21, 0.0F, CarUi.Companion.getWARN(), false, 20, null);
                  }

                  List var76 = this.speeds(var110);
                  ArrayList var26 = new ArrayList();
                  LinearLayout var90 = null;
                  Iterator var27 = var63.iterator();
                  var34 = 0;

                  while (var27.hasNext()) {
                     int var11 = var34 + 1;
                     double var4 = ((Number)var27.next()).doubleValue();
                     if (var34 % 5 == 0) {
                        var90 = new LinearLayout((Context)this.getAct());
                        var90.setOrientation(0);
                        var90.setPadding(0, this.getUi().dp(4), 0, this.getUi().dp(4));
                        var21.addView((View)var90);
                     }

                     CarUi var28 = this.getUi();
                     boolean var36;
                     if (var34 >= 0 && var34 < var76.size()) {
                        var36 = true;
                     } else {
                        var36 = false;
                     }

                     Object var97;
                     if (var36) {
                        var97 = (TextView)var76.get(var34);
                     } else {
                        var97 = 0.0;
                     }

                     var97 = this.speed(((Number)var97).doubleValue());
                     String var30 = this.speedUnit();
                     String var29 = GxHomeKt.access$gxHomeFmt(var4);
                     var97 = CarUi.button$default(
                        var28,
                        var97 + " " + var30 + "\n" + var29,
                        false,
                        new GxPersonalitiesPage$$ExternalSyntheticLambda33(this, var1, var110, var34),
                        2,
                        null
                     );
                     var97.setTextSize(16.0F);
                     var97.setMinWidth(0);
                     if (this.getEditingLocked()) {
                        var97.setAlpha(0.5F);
                     }

                     var26.add(var97);
                     Intrinsics.checkNotNull(var90);
                     View var113 = (View)var97;
                     android.widget.LinearLayout.LayoutParams var112 = new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F);
                     var112.rightMargin = this.getUi().dp(6);
                     Unit var100 = Unit.INSTANCE;
                     var90.addView(var113, (LayoutParams)var112);
                     var34 = var11;
                  }

                  if (var63.size() % 5 != 0) {
                     int var38 = 5 - var63.size() % 5;

                     for (int var37 = 0; var37 < var38; var37++) {
                        if (var90 != null) {
                           View var111 = new View((Context)this.getAct());
                           android.widget.LinearLayout.LayoutParams var77 = new android.widget.LinearLayout.LayoutParams(0, 1, 1.0F);
                           var77.rightMargin = this.getUi().dp(6);
                           Unit var101 = Unit.INSTANCE;
                           var90.addView(var111, (LayoutParams)var77);
                           Unit var78 = Unit.INSTANCE;
                        }
                     }
                  }

                  this.pointViews.put(var25, var26);
                  GxPage var64 = this;
                  Pair var91 = TuplesKt.to("Reset to default", new GxPersonalitiesPage$$ExternalSyntheticLambda44(this, var1, var24, var110));
                  ViewGroup var79 = (ViewGroup)var21;
                  LinearLayout var65 = GxPage.buttons$default(var64, new Pair[]{var91}, var79, false, 4, null);
                  if (this.getEditingLocked()) {
                     var65.setAlpha(0.5F);
                  }
               }
            }
         }
      }
   }

   private static final void renderProfile$lambda$61(GxPersonalitiesPage var0, String var1, CompoundButton var2, boolean var3) {
      if (var2.isPressed()) {
         var0.toggle(var1, var3);
      }
   }

   private static final Unit renderProfile$lambda$63(GxPersonalitiesPage var0, String var1, String var2, List var3, int var4) {
      if (!var0.getEditingLocked()) {
         var0.choosePreset(var1, var2, (String)var3.get(var4));
      }

      return Unit.INSTANCE;
   }

   private static final Unit renderProfile$lambda$64(boolean var0, GxPersonalitiesPage var1, String var2) {
      HashSet var3 = var1.advancedOpen;
      if (var0) {
         var3.remove(var2);
      } else {
         var3.add(var2);
      }

      var1.forceRender();
      return Unit.INSTANCE;
   }

   private static final Unit renderProfile$lambda$71(GxPersonalitiesPage var0, String var1, String var2, int var3) {
      var0.editPoint(var1, var2, var3);
      return Unit.INSTANCE;
   }

   private static final Unit renderProfile$lambda$76(GxPersonalitiesPage var0, String var1, String var2, String var3) {
      if (!var0.getEditingLocked()) {
         String var4 = var0.label(var1);
         Locale var5 = Locale.ROOT;
         Intrinsics.checkNotNullExpressionValue(var5, "ROOT");
         var2 = var2.toLowerCase(var5);
         Intrinsics.checkNotNullExpressionValue(var2, "toLowerCase(...)");
         var0.confirm(
            "Reset to default?",
            var4 + " " + var2 + " goes back to the Dom default curve.",
            "Reset",
            new GxPersonalitiesPage$$ExternalSyntheticLambda6(var0, var1, var3)
         );
      }

      return Unit.INSTANCE;
   }

   private static final Unit renderProfile$lambda$76$lambda$75(GxPersonalitiesPage var0, String var1, String var2) {
      var0.saveCurve(var1, var2, true);
      return Unit.INSTANCE;
   }

   private static final Unit renderProfile$lambda$81(GxPersonalitiesPage var0, GalaxyApi.Param var1, TextView var2) {
      var0.nudgeAdvanced(var1, -5, var2);
      return Unit.INSTANCE;
   }

   private static final Unit renderProfile$lambda$82(GxPersonalitiesPage var0, GalaxyApi.Param var1, TextView var2) {
      var0.nudgeAdvanced(var1, 5, var2);
      return Unit.INSTANCE;
   }

   private static final Unit renderProfile$lambda$85(GxPersonalitiesPage var0, GalaxyApi.Param var1, double var2, double var4) {
      if (!var0.paramLocked(var1.getKey())) {
         GxPage var8 = var0;
         String var7 = var1.getLabel();
         String var6 = GxHomeKt.access$gxHomeFmt(var2);
         String var9 = GxHomeKt.access$gxHomeFmt(var4);
         GxPage.prompt$default(
            var8,
            var7,
            var6 + "–" + var9,
            GxHomeKt.access$gxHomeFmt(GxHomeKt.access$gxHomeNum(var0.vals.get(var1.getKey()))),
            8194,
            null,
            new GxPersonalitiesPage$$ExternalSyntheticLambda8(var0, var1),
            16,
            null
         );
      }

      return Unit.INSTANCE;
   }

   private static final Unit renderProfile$lambda$85$lambda$84(GxPersonalitiesPage var0, GalaxyApi.Param var1, String var2) {
      Intrinsics.checkNotNullParameter(var2, "raw");
      var0.setAdvanced(var1, var2, new GxPersonalitiesPage$$ExternalSyntheticLambda15(var0));
      return Unit.INSTANCE;
   }

   private static final Unit renderProfile$lambda$85$lambda$84$lambda$83(GxPersonalitiesPage var0, boolean var1) {
      var0.forceRender();
      return Unit.INSTANCE;
   }

   private static final Unit renderProfile$lambda$88(GxPersonalitiesPage var0, GalaxyApi.Param var1, List var2, int var3) {
      var0.advancedPreset(var1, (String)var2.get(var3));
      return Unit.INSTANCE;
   }

   private final Boolean roadFlag(Object var1) {
      Boolean var3 = true;
      Boolean var2 = var3;
      if (!Intrinsics.areEqual(var1, var3)) {
         var2 = var3;
         if (!Intrinsics.areEqual(var1, 1)) {
            var2 = var3;
            if (!Intrinsics.areEqual(var1, 1L)) {
               var2 = var3;
               if (!Intrinsics.areEqual(var1, Double.valueOf(1.0))) {
                  var2 = var3;
                  if (!Intrinsics.areEqual(var1, "1")) {
                     var2 = var3;
                     if (!Intrinsics.areEqual(var1, "true")) {
                        if (Intrinsics.areEqual(var1, "True")) {
                           var2 = var3;
                        } else if (!Intrinsics.areEqual(var1, false)
                           && !Intrinsics.areEqual(var1, 0)
                           && !Intrinsics.areEqual(var1, 0L)
                           && !Intrinsics.areEqual(var1, Double.valueOf(0.0))
                           && !Intrinsics.areEqual(var1, "")
                           && !Intrinsics.areEqual(var1, "0")
                           && !Intrinsics.areEqual(var1, "false")
                           && !Intrinsics.areEqual(var1, "False")) {
                           var2 = null;
                        } else {
                           var2 = false;
                        }
                     }
                  }
               }
            }
         }
      }

      return var2;
   }

   private final void saveCurve(String var1, String var2, boolean var3) {
      if (!this.getEditingLocked() && this.getShown()) {
         boolean var4 = this.isOnroad();
         List var5;
         if (var3) {
            var5 = CollectionsKt.emptyList();
         } else {
            var5 = CollectionsKt.toList(this.draft(var1, var2));
         }

         this.curvePending = true;
         this.whenContextIdle(new GxPersonalitiesPage$$ExternalSyntheticLambda5(this, var4, var1, var2, var5, var3));
      }
   }

   // $VF: synthetic method
   static void saveCurve$default(GxPersonalitiesPage var0, String var1, String var2, boolean var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = false;
      }

      var0.saveCurve(var1, var2, var3);
   }

   private static final Unit saveCurve$lambda$43(GxPersonalitiesPage var0, boolean var1, String var2, String var3, List var4, boolean var5) {
      if (var0.getRoadStateKnown() && var0.isOnroad() == var1) {
         var0.write(
            new GxPersonalitiesPage$$ExternalSyntheticLambda29(var2, var3, var4, var5, var0),
            new GxPersonalitiesPage$$ExternalSyntheticLambda30(var0, var1),
            new GxPersonalitiesPage$$ExternalSyntheticLambda31(var0, var2, var3)
         );
         return Unit.INSTANCE;
      } else {
         var0.discard(var2, var3);
         var0.curvePending = false;
         var0.forceRender();
         return Unit.INSTANCE;
      }
   }

   private static final Unit saveCurve$lambda$43$lambda$40(String var0, String var1, List var2, boolean var3, GxPersonalitiesPage var4, Function2 var5) {
      Intrinsics.checkNotNullParameter(var5, "cb");
      JSONObject var6 = new JSONObject().put("profile", var0).put("category", var1).put("preset", "custom").put("curve", new JSONArray(var2));
      if (var3) {
         var6.put("reset", true);
      }

      var6.put("expected", var4.config(var0, var1));
      GxPage.send$default(var4, "PUT", "/api/personality_profiles", var6, 0L, var5, 8, null);
      return Unit.INSTANCE;
   }

   private static final boolean saveCurve$lambda$43$lambda$41(GxPersonalitiesPage var0, boolean var1) {
      if (!var0.getLocked() && var0.isOnroad() == var1 && !var0.getMigrationRequired()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private static final Unit saveCurve$lambda$43$lambda$42(GxPersonalitiesPage var0, String var1, String var2, boolean var3) {
      if (var3) {
         var0.notice = "";
      }

      var0.discard(var1, var2);
      var0.curvePending = false;
      var0.forceRender();
      return Unit.INSTANCE;
   }

   private final void setAdvanced(GalaxyApi.Param var1, String var2, Function1<? super Boolean, Unit> var3) {
      if (!this.getRoadStateKnown()) {
         var3.invoke(false);
      } else {
         this.whenContextIdle(new GxPersonalitiesPage$$ExternalSyntheticLambda45(this, this.isOnroad(), var3, var2, var1));
      }
   }

   // $VF: synthetic method
   static void setAdvanced$default(GxPersonalitiesPage var0, GalaxyApi.Param var1, String var2, Function1 var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = new GxPersonalitiesPage$$ExternalSyntheticLambda4();
      }

      var0.setAdvanced(var1, var2, (Function1<? super Boolean, Unit>)var3);
   }

   private static final Unit setAdvanced$lambda$46(boolean var0) {
      return Unit.INSTANCE;
   }

   private static final Unit setAdvanced$lambda$49(GxPersonalitiesPage var0, boolean var1, Function1 var2, String var3, GalaxyApi.Param var4) {
      boolean var15 = var0.getRoadStateKnown();
      boolean var14 = false;
      Boolean var17 = false;
      if (var15 && var0.isOnroad() == var1) {
         Double var16 = StringsKt.toDoubleOrNull(StringsKt.trim(var3).toString());
         Triple var18 = var0.paramBounds(var4);
         double var5 = ((Number)var18.component1()).doubleValue();
         double var7 = ((Number)var18.component2()).doubleValue();
         double var11 = ((Number)var18.component3()).doubleValue();
         if (!StringsKt.isBlank(var3) && var16 != null) {
            double var9 = var16;
            boolean var13 = var14;
            if (!Double.isInfinite(var9)) {
               var13 = var14;
               if (!Double.isNaN(var9)) {
                  var13 = true;
               }
            }

            if (var13 && !(var16 < var5) && !(var16 > var7) && !(Math.abs((var16 - var5) / var11 - Math.round((var16 - var5) / var11)) > 1.0E-7)) {
               var0.advancedErrors.remove(var4.getKey());
               Object var21;
               if (Intrinsics.areEqual(var16, Math.floor(var16))) {
                  var21 = (long)var16.doubleValue();
               } else {
                  var21 = var16;
               }

               var0.write(
                  new GxPersonalitiesPage$$ExternalSyntheticLambda25(var0, var4, var21),
                  new GxPersonalitiesPage$$ExternalSyntheticLambda26(var0, var4, var1),
                  var2
               );
               return Unit.INSTANCE;
            }
         }

         Map var20 = var0.advancedErrors;
         String var24 = var4.getKey();
         String var19 = GxHomeKt.access$gxHomeFmt(var5);
         String var22 = GxHomeKt.access$gxHomeFmt(var7);
         String var23 = GxHomeKt.access$gxHomeFmt(var11);
         var20.put(var24, "Enter " + var19 + "–" + var22 + "% in increments of " + var23 + ".");
         var0.forceRender();
         var2.invoke(var17);
         return Unit.INSTANCE;
      } else {
         var2.invoke(var17);
         return Unit.INSTANCE;
      }
   }

   private static final Unit setAdvanced$lambda$49$lambda$47(GxPersonalitiesPage var0, GalaxyApi.Param var1, Object var2, Function2 var3) {
      Intrinsics.checkNotNullParameter(var3, "cb");
      GxPage.send$default(var0, "PUT", "/api/params", var0.putParamBody(var1.getKey(), var2), 0L, var3, 8, null);
      return Unit.INSTANCE;
   }

   private static final boolean setAdvanced$lambda$49$lambda$48(GxPersonalitiesPage var0, GalaxyApi.Param var1, boolean var2) {
      if (!var0.paramLocked(var1.getKey()) && var0.isOnroad() == var2) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private final String signature() {
      boolean var2 = false;
      TreeSet var8 = SetsKt.sortedSetOf(new String[]{"CustomPersonalities", "IsMetric", "VehicleParked", "IsOnroad", "IsOffroad"});
      var8.addAll(this.profileKeys.values());
      Iterator var9 = this.meta.values().iterator();

      while (true) {
         boolean var1 = var9.hasNext();
         String var7 = null;
         if (!var1) {
            JSONObject var19 = this.data;
            if (var19 != null) {
               var7 = var19.toString();
            }

            String var15 = CollectionsKt.joinToString$default(
               var8, null, null, null, 0, null, new GxPersonalitiesPage$$ExternalSyntheticLambda32(this), 31, null
            );
            boolean var6 = this.busy;
            boolean var3 = this.ready;
            String var21 = this.error;
            String var22 = this.notice;
            boolean var5 = this.curvePending;
            boolean var4 = this.loadPending;
            var1 = var2;
            if (this.contextPending) {
               var1 = var2;
               if (!this.ready) {
                  var1 = true;
               }
            }

            List var13 = CollectionsKt.sorted(this.advancedOpen);
            SortedMap var12 = MapsKt.toSortedMap(this.drafts);
            SortedMap var20 = MapsKt.toSortedMap(this.curveErrors);
            SortedMap var14 = MapsKt.toSortedMap(this.advancedErrors);
            List var18 = CollectionsKt.sorted(this.advancedCustom);
            return CollectionsKt.joinToString$default(
               CollectionsKt.listOf(new Object[]{var7, var15, var6, var3, var21, var22, var5, var4, var1, var13, var12, var20, var14, var18, this.meta.size()}),
               "|",
               null,
               null,
               0,
               null,
               null,
               62,
               null
            );
         }

         GalaxyApi.Param var11 = (GalaxyApi.Param)var9.next();
         if (StringsKt.contains$default(var11.getKey(), "Jerk", false, 2, null) && CollectionsKt.contains(this.profileKeys.values(), var11.getParentKey())) {
            var8.add(var11.getKey());
            Iterator var17 = CollectionsKt.listOf(new String[]{"requires_capability", "disabled_when_key_true", "requires_nonempty_key"}).iterator();

            while (var17.hasNext()) {
               String var10 = var11.str((String)var17.next());
               if (var10 != null) {
                  var8.add(var10);
               }
            }
         }
      }
   }

   private static final CharSequence signature$lambda$55(GxPersonalitiesPage var0, String var1) {
      Intrinsics.checkNotNullParameter(var1, "it");
      Object var2 = var0.vals.get(var1);
      return var1 + "=" + var2;
   }

   private final String speed(double var1) {
      boolean var5 = Double.isInfinite(var1);
      boolean var4 = false;
      boolean var3;
      if (!var5 && !Double.isNaN(var1)) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (!var3) {
         return "—";
      } else if (!this.metric()) {
         var3 = var4;
         if (var1 == Math.floor(var1)) {
            var3 = true;
         }

         String var9;
         if (var3) {
            var9 = String.valueOf((long)var1);
         } else {
            StringCompanionObject var10 = StringCompanionObject.INSTANCE;
            var9 = String.format(Locale.US, "%.1f", Arrays.copyOf(new Object[]{var1}, 1));
            Intrinsics.checkNotNullExpressionValue(var9, "format(...)");
            var9 = StringsKt.removeSuffix(var9, ".0");
         }

         return var9;
      } else {
         StringCompanionObject var6 = StringCompanionObject.INSTANCE;
         String var8 = String.format(Locale.US, "%.1f", Arrays.copyOf(new Object[]{1.609344 * var1}, 1));
         Intrinsics.checkNotNullExpressionValue(var8, "format(...)");
         return StringsKt.removeSuffix(var8, ".0");
      }
   }

   private final String speedUnit() {
      String var1;
      if (this.metric()) {
         var1 = "km/h";
      } else {
         var1 = "mph";
      }

      return var1;
   }

   private final List<Double> speeds(String var1) {
      JSONObject var2 = this.data;
      Intrinsics.checkNotNull(var2);
      return this.nums(var2.getJSONObject("speed_breakpoints_mph").getJSONArray(var1));
   }

   private final void toggle(String var1, boolean var2) {
      this.write(
         new GxPersonalitiesPage$$ExternalSyntheticLambda17(this, var1, var2),
         new GxPersonalitiesPage$$ExternalSyntheticLambda18(this, var1),
         new GxPersonalitiesPage$$ExternalSyntheticLambda19(this)
      );
   }

   private static final Unit toggle$lambda$32(GxPersonalitiesPage var0, String var1, boolean var2, Function2 var3) {
      Intrinsics.checkNotNullParameter(var3, "cb");
      GxPage.send$default(var0, "PUT", "/api/params", var0.putParamBody(var1, var2), 0L, var3, 8, null);
      return Unit.INSTANCE;
   }

   private static final boolean toggle$lambda$33(GxPersonalitiesPage var0, String var1) {
      return var0.paramLocked(var1) ^ true;
   }

   private static final Unit toggle$lambda$34(GxPersonalitiesPage var0, boolean var1) {
      var0.forceRender();
      return Unit.INSTANCE;
   }

   private final JSONObject validate(JSONObject var1) {
      for (String var12 : this.profilesIds) {
         Iterator var13 = this.categories.iterator();

         while (var13.hasNext()) {
            JSONArray var11;
            String var14;
            JSONObject var21;
            label84: {
               var14 = (String)((Pair)var13.next()).component1();
               var21 = var1.optJSONObject("profiles");
               var11 = null;
               if (var21 != null) {
                  var21 = var21.optJSONObject(var12);
                  if (var21 != null) {
                     var21 = var21.optJSONObject(var14);
                     break label84;
                  }
               }

               var21 = null;
            }

            JSONArray var7;
            if (var21 != null) {
               var7 = var21.optJSONArray("curve");
            } else {
               var7 = null;
            }

            JSONObject var8 = var1.optJSONObject("speed_breakpoints_mph");
            JSONArray var22;
            if (var8 != null) {
               var22 = var8.optJSONArray(var14);
            } else {
               var22 = null;
            }

            JSONObject var9 = var1.optJSONObject("bounds");
            JSONArray var23;
            if (var9 != null) {
               var23 = var9.optJSONArray(var14);
            } else {
               var23 = null;
            }

            JSONArray var25;
            label90: {
               JSONObject var10 = var1.optJSONObject("reference_curves");
               if (var10 != null) {
                  JSONObject var24 = var10.optJSONObject(var12);
                  if (var24 != null) {
                     var25 = var24.optJSONArray(var14);
                     break label90;
                  }
               }

               var25 = null;
            }

            JSONObject var15 = var1.optJSONObject("options");
            if (var15 != null) {
               var11 = var15.optJSONArray(var14);
            }

            int var19;
            label135: {
               List var26 = this.nums(var22);
               if (var21 != null && var7 != null && var22 != null && var22.length() != 0 && validate$finite(var22)) {
                  Iterable var27 = CollectionsKt.withIndex(var26);
                  if (var27 instanceof Collection && ((Collection)var27).isEmpty()) {
                     var19 = (boolean)0;
                  } else {
                     Iterator var28 = var27.iterator();

                     while (true) {
                        if (!var28.hasNext()) {
                           var19 = (boolean)0;
                           break;
                        }

                        IndexedValue var16 = (IndexedValue)var28.next();
                        var19 = var16.component1();
                        double var2 = ((Number)var16.component2()).doubleValue();
                        boolean var17;
                        if (var2 < 0.0 || var19 > 0 && var2 <= ((Number)var26.get(var19 - 1)).doubleValue()) {
                           var17 = true;
                        } else {
                           var17 = false;
                        }

                        if (var17) {
                           var19 = (boolean)1;
                           break;
                        }
                     }
                  }

                  if (!var19
                     && !(CollectionsKt.<Number>last(var26).doubleValue() <= 0.0)
                     && var23 != null
                     && var23.length() == 2
                     && validate$finite(var23)
                     && !(this.nums(var23).get(0).doubleValue() >= this.nums(var23).get(1).doubleValue())
                     && var25 != null
                     && var25.length() == var22.length()
                     && validate$finite(var25)
                     && var11 != null
                     && this.strings(var11).contains(var21.optString("preset"))
                     && (!Intrinsics.areEqual(var21.optString("preset"), "custom") || var7.length() == var22.length())
                     && validate$finite(var7)) {
                     var19 = 0;
                     break label135;
                  }
               }

               var19 = 1;
            }

            if (var19) {
               throw new IllegalStateException("Profile data is unavailable or malformed. Retrying automatically…");
            }
         }
      }

      return var1;
   }

   private static final boolean validate$finite(JSONArray var0) {
      boolean var5 = false;
      boolean var4 = var5;
      if (var0 != null) {
         Iterable var6 = RangesKt.until(0, var0.length());
         boolean var9;
         if (var6 instanceof Collection && ((Collection)var6).isEmpty()) {
            var9 = true;
         } else {
            Iterator var10 = var6.iterator();

            while (true) {
               if (!var10.hasNext()) {
                  var9 = true;
                  break;
               }

               label40: {
                  Object var7 = var0.opt(((IntIterator)var10).nextInt());
                  if (var7 instanceof Number) {
                     double var1 = ((Number)var7).doubleValue();
                     if (!Double.isInfinite(var1) && !Double.isNaN(var1)) {
                        var9 = true;
                     } else {
                        var9 = false;
                     }

                     if (var9) {
                        var9 = true;
                        break label40;
                     }
                  }

                  var9 = false;
               }

               if (!var9) {
                  var9 = false;
                  break;
               }
            }
         }

         var4 = var5;
         if (var9) {
            var4 = true;
         }
      }

      return var4;
   }

   private final void whenContextIdle(Function0<Unit> var1) {
      if (!this.getAct().isFinishing() && !this.getAct().isDestroyed()) {
         if (this.contextPending) {
            this.getMain().postDelayed(new GxPersonalitiesPage$$ExternalSyntheticLambda50(this, var1), 150L);
         } else {
            var1.invoke();
         }
      }
   }

   private static final void whenContextIdle$lambda$25(GxPersonalitiesPage var0, Function0 var1) {
      var0.whenContextIdle(var1);
   }

   private final void write(
      Function1<? super Function2<? super String, ? super JSONObject, Unit>, Unit> var1, Function0<Boolean> var2, Function1<? super Boolean, Unit> var3
   ) {
      if (!this.getRoadStateKnown()) {
         var3.invoke(false);
      } else {
         this.whenContextIdle(new GxPersonalitiesPage$$ExternalSyntheticLambda16(this, this.isOnroad(), var2, var3, var1));
      }
   }

   // $VF: synthetic method
   static void write$default(GxPersonalitiesPage var0, Function1 var1, Function0 var2, Function1 var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = new GxPersonalitiesPage$$ExternalSyntheticLambda34(var0);
      }

      if ((var4 & 4) != 0) {
         var3 = new GxPersonalitiesPage$$ExternalSyntheticLambda35();
      }

      var0.write(var1, (Function0<Boolean>)var2, (Function1<? super Boolean, Unit>)var3);
   }

   private static final boolean write$lambda$26(GxPersonalitiesPage var0) {
      return var0.getEditingLocked() ^ true;
   }

   private static final Unit write$lambda$27(boolean var0) {
      return Unit.INSTANCE;
   }

   private static final Unit write$lambda$31(GxPersonalitiesPage var0, boolean var1, Function0 var2, Function1 var3, Function1 var4) {
      if (var0.getRoadStateKnown() && var0.isOnroad() == var1 && (Boolean)var2.invoke()) {
         var0.busy = true;
         var0.error = "";
         var0.notice = "";
         var0.render();
         var4.invoke(new GxPersonalitiesPage$$ExternalSyntheticLambda10(var0, var3));
         return Unit.INSTANCE;
      } else {
         var3.invoke(false);
         return Unit.INSTANCE;
      }
   }

   private static final void write$lambda$31$fail(GxPersonalitiesPage var0, Function1<? super Boolean, Unit> var1, String var2) {
      var0.busy = false;
      var0.error = var2 + " Rechecking saved state…";
      var0.ready = false;
      var0.recovery = true;
      var0.drafts.clear();
      var0.render();
      var1.invoke(false);
      var0.load();
   }

   private static final Unit write$lambda$31$lambda$30(GxPersonalitiesPage var0, Function1 var1, String var2, JSONObject var3) {
      if (var2 != null) {
         write$lambda$31$fail(var0, var1, var2);
         return Unit.INSTANCE;
      } else {
         var0.getJson("/api/personality_profiles", new GxPersonalitiesPage$$ExternalSyntheticLambda37(var0, var1));
         return Unit.INSTANCE;
      }
   }

   private static final Unit write$lambda$31$lambda$30$lambda$29(GxPersonalitiesPage var0, Function1 var1, String var2, JSONObject var3) {
      var0.fetchContext(true, new GxPersonalitiesPage$$ExternalSyntheticLambda43(var2, var3, var0, var1));
      return Unit.INSTANCE;
   }

   private static final Unit write$lambda$31$lambda$30$lambda$29$lambda$28(String var0, JSONObject var1, GxPersonalitiesPage var2, Function1 var3, boolean var4) {
      if (var0 == null && var1 != null) {
         if (!var4) {
            write$lambda$31$fail(var2, var3, "Connection lost.");
            return Unit.INSTANCE;
         } else {
            try {
               var2.acceptData(var1);
            } catch (Exception var6) {
               String var8 = var6.getMessage();
               var0 = var8;
               if (var8 == null) {
                  var0 = var6.toString();
               }

               write$lambda$31$fail(var2, var3, var0);
               return Unit.INSTANCE;
            }

            var2.busy = false;
            var2.toast("Driving personalities saved.");
            var2.render();
            var3.invoke(true);
            return Unit.INSTANCE;
         }
      } else {
         if (var0 == null) {
            var0 = "bad reply";
         }

         write$lambda$31$fail(var2, var3, var0);
         return Unit.INSTANCE;
      }
   }

   @Override
   public void build() {
      this.content = GxPage.box$default(this, null, 1, null);
      this.every(4000L, new GxPersonalitiesPage$$ExternalSyntheticLambda14(this));
   }

   @Override
   public boolean getNeedsOffroad() {
      return this.needsOffroad;
   }

   @Override
   public void onHide() {
      this.drag = null;
      this.drafts.clear();

      for (Object var2 : this.advancedTimers.values()) {
         Intrinsics.checkNotNullExpressionValue(var2, "next(...)");
         var2 = (Runnable)var2;
         this.getMain().removeCallbacks((Runnable)var2);
      }

      this.advancedTimers.clear();
      this.advancedPendingValue.clear();
   }

   @Override
   public void onShow() {
      this.forceRender();
      this.load();
   }

   private static final class Drag {
      private final String category;
      private final int index;
      private final double max;
      private final double min;
      private final List<Double> previous;
      private final String profile;

      public Drag(String var1, String var2, int var3, double var4, double var6, List<Double> var8) {
         Intrinsics.checkNotNullParameter(var1, "profile");
         Intrinsics.checkNotNullParameter(var2, "category");
         super();
         this.profile = var1;
         this.category = var2;
         this.index = var3;
         this.min = var4;
         this.max = var6;
         this.previous = var8;
      }

      public final String getCategory() {
         return this.category;
      }

      public final int getIndex() {
         return this.index;
      }

      public final double getMax() {
         return this.max;
      }

      public final double getMin() {
         return this.min;
      }

      public final List<Double> getPrevious() {
         return this.previous;
      }

      public final String getProfile() {
         return this.profile;
      }
   }

   private final class Graph extends View {
      private final String category;
      private final Paint dot;
      private final Paint dotRing;
      private final Paint grid;
      private final String k;
      private final Paint lineP;
      private float ox;
      private float oy;
      private final String profile;
      private final Paint refP;
      private float s;
      final GxPersonalitiesPage this$0;
      private final Paint txt;

      public Graph(String var1, String var2, String var3) {
         Intrinsics.checkNotNullParameter(var2, "profile");
         Intrinsics.checkNotNullParameter(var3, "category");
         this.this$0 = var1;
         super((Context)var1.getAct());
         this.profile = var2;
         this.category = var3;
         var2 = this.profile;
         String var4 = this.category;
         this.k = var2 + var4;
         Paint var5 = new Paint(1);
         GxPersonalitiesPage var12 = this.this$0;
         var5.setColor(Color.argb(60, 255, 255, 255));
         var5.setStrokeWidth(var12.getUi().dp(1));
         this.grid = var5;
         Paint var6 = new Paint(1);
         var6.setColor(CarUi.Companion.getDIM());
         this.txt = var6;
         Paint var13 = new Paint(1);
         var1 = this.this$0;
         var13.setColor(Color.argb(140, 255, 255, 255));
         var13.setStyle(Style.STROKE);
         var13.setStrokeWidth(var1.getUi().dp(2));
         var13.setPathEffect((PathEffect)(new DashPathEffect(new float[]{var1.getUi().dp(6), var1.getUi().dp(6)}, 0.0F)));
         this.refP = var13;
         Paint var14 = new Paint(1);
         var1 = this.this$0;
         var14.setColor(CarUi.Companion.getACCENT());
         var14.setStyle(Style.STROKE);
         var14.setStrokeWidth(var1.getUi().dp(3));
         this.lineP = var14;
         Paint var9 = new Paint(1);
         var9.setColor(CarUi.Companion.getACCENT());
         this.dot = var9;
         Paint var15 = new Paint(1);
         var1 = this.this$0;
         var15.setColor(-1);
         var15.setStyle(Style.STROKE);
         var15.setStrokeWidth(var1.getUi().dp(1));
         this.dotRing = var15;
         this.s = 1.0F;
         this.setBackground((Drawable)CarUi.rounded$default(this.this$0.getUi(), Color.rgb(16, 18, 24), 12, null, 4, null));
      }

      private final float X(double var1) {
         return this.ox + (float)((30 + var1) * this.s);
      }

      private final float Y(double var1) {
         return this.oy + (float)((12 + var1) * this.s);
      }

      private final void move(MotionEvent var1) {
         GxPersonalitiesPage.Drag var11 = this.this$0.drag;
         if (var11 != null) {
            float var6 = (var1.getY() - this.oy) / this.s;
            float var7 = 12;
            Pair var13 = this.this$0.bounds(this.category);
            double var4 = ((Number)var13.component1()).doubleValue();
            double var2 = ((Number)var13.component2()).doubleValue();
            var2 = RangesKt.coerceIn(Math.round((var11.getMin() + (90 - (var6 - var7)) / 80 * (var11.getMax() - var11.getMin())) * 20) / 20.0, var4, var2);
            List var14 = CollectionsKt.toMutableList(this.this$0.draft(this.profile, this.category));
            int var9 = var14.size();
            int var8 = var11.getIndex();
            boolean var20;
            if (var8 >= 0 && var8 < var9) {
               var20 = true;
            } else {
               var20 = false;
            }

            if (var20) {
               var14.set(var11.getIndex(), var2);
               this.this$0.drafts.put(this.k, var14);
               this.this$0.curveErrors.remove(this.k);
               List var15 = this.this$0.pointViews.get(this.k);
               if (var15 != null) {
                  TextView var10 = CollectionsKt.getOrNull(var15, var11.getIndex());
                  if (var10 != null) {
                     GxPersonalitiesPage var12 = this.this$0;
                     List var16 = var12.speeds(this.category);
                     var9 = var11.getIndex();
                     if (var9 >= 0 && var9 < var16.size()) {
                        var20 = true;
                     } else {
                        var20 = false;
                     }

                     Object var17;
                     if (var20) {
                        var17 = var16.get(var9);
                     } else {
                        var17 = 0.0;
                     }

                     var17 = var12.speed(((Number)var17).doubleValue());
                     String var23 = var12.speedUnit();
                     String var24 = GxHomeKt.access$gxHomeFmt(var2);
                     var10.setText(var17 + " " + var23 + "\n" + var24);
                  }
               }

               this.invalidate();
            }
         }
      }

      private static final void onDraw$poly(Canvas var0, GxPersonalitiesPage.Graph var1, List<Pair<Double, Double>> var2, Paint var3) {
         if (!var2.isEmpty()) {
            Path var11 = new Path();
            Iterable var13 = var2;
            int var10 = 0;

            for (Object var12 : var13) {
               if (var10 < 0) {
                  CollectionsKt.throwIndexOverflow();
               }

               var12 = (Pair)var12;
               double var4 = ((Number)var12.component1()).doubleValue();
               double var6 = ((Number)var12.component2()).doubleValue();
               float var8 = var1.X(var4);
               float var9 = var1.Y(var6);
               if (var10 == 0) {
                  var11.moveTo(var8, var9);
               } else {
                  var11.lineTo(var8, var9);
               }

               var10++;
            }

            var0.drawPath(var11, var3);
         }
      }

      private final List<Pair<Double, Double>> points(List<Double> var1) {
         List var16 = this.this$0.speeds(this.category);
         double var8 = this.this$0.graphMax(this.profile, this.category);
         double var6 = this.this$0.graphMin(this.profile, this.category);
         Double var17 = CollectionsKt.lastOrNull(var16);
         double var4 = 1.0;
         double var2;
         if (var17 != null) {
            var2 = var17;
         } else {
            var2 = 1.0;
         }

         boolean var14;
         if (var8 - var6 == 0.0) {
            var14 = 1;
         } else {
            var14 = 0;
         }

         if (!var14) {
            var4 = var8 - var6;
         }

         Iterable var19 = var1;
         var var24 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var19, 10));
         var14 = 0;
         Iterator var18 = var19.iterator();

         for (List var20 = var16; var18.hasNext(); var14++) {
            var16 = (List)var18.next();
            if (var14 < 0) {
               CollectionsKt.throwIndexOverflow();
            }

            double var12 = ((Number)var16).doubleValue();
            double var10 = 10;
            boolean var15;
            if (var14 >= 0 && var14 < var20.size()) {
               var15 = true;
            } else {
               var15 = false;
            }

            if (var15) {
               var16 = (List)var20.get(var14);
            } else {
               var16 = 0.0;
            }

            var24.add(TuplesKt.to(var10 + ((Number)var16).doubleValue() / var2 * 280, 90 - (var12 - var6) / var4 * 80));
         }

         return var24;
      }

      private final void restore() {
         GxPersonalitiesPage.Drag var1 = this.this$0.drag;
         if (var1 != null) {
            if (var1.getPrevious() != null) {
               this.this$0.drafts.put(this.k, var1.getPrevious());
            } else {
               this.this$0.drafts.remove(this.k);
            }

            this.this$0.drag = null;
            this.invalidate();
            this.this$0.forceRender();
         }
      }

      public final String getCategory() {
         return this.category;
      }

      public final String getProfile() {
         return this.profile;
      }

      protected void onDraw(Canvas var1) {
         Intrinsics.checkNotNullParameter(var1, "cv");
         if (this.this$0.data != null) {
            this.s = Math.min(this.getWidth() / 340.0F, this.getHeight() / 140.0F);
            this.ox = (this.getWidth() - 340 * this.s) / 2.0F;
            this.oy = (this.getHeight() - 140 * this.s) / 2.0F;
            this.txt.setTextSize(10 * this.s);
            double var2 = this.this$0.graphMin(this.profile, this.category);
            double var6 = this.this$0.graphMax(this.profile, this.category);
            this.txt.setTextAlign(Align.LEFT);
            String var11;
            if (Intrinsics.areEqual(this.category, "following")) {
               var11 = "Seconds";
            } else {
               var11 = "m/s²";
            }

            var1.drawText(var11, this.X(10.0), this.Y(-3.0), this.txt);

            for (int var10 = 0; var10 < 5; var10++) {
               var1.drawLine(this.X(10.0), this.Y(90.0 - var10 * 20), this.X(290.0), this.Y(90.0 - var10 * 20), this.grid);
               this.txt.setTextAlign(Align.RIGHT);
               StringCompanionObject var16 = StringCompanionObject.INSTANCE;
               Locale var17 = Locale.US;
               double var4 = var10;
               double var8 = 4;
               var11 = String.format(var17, "%.2f", Arrays.copyOf(new Object[]{(var6 - var2) * var4 / var8 + var2}, 1));
               Intrinsics.checkNotNullExpressionValue(var11, "format(...)");
               var1.drawText(GxHomeKt.access$gxHomeFmt(Double.parseDouble(var11)), this.X(5.0), this.Y(93.0 - var10 * 20), this.txt);
               var1.drawLine(this.X(var10 * 70 + 10.0), this.Y(10.0), this.X(var10 * 70 + 10.0), this.Y(94.0), this.grid);
               this.txt.setTextAlign(Align.CENTER);
               GxPersonalitiesPage var19 = this.this$0;
               Double var12 = CollectionsKt.lastOrNull(this.this$0.speeds(this.category));
               if (var12 != null) {
                  var4 = var12;
               } else {
                  var4 = 0.0;
               }

               var1.drawText(var19.speed(var4 * var10 / var8), this.X(var10 * 70 + 10.0), this.Y(105.0), this.txt);
            }

            var11 = this.this$0.speedUnit();
            var1.drawText("Speed (" + var11 + ")", this.X(150.0), this.Y(121.0), this.txt);
            onDraw$poly(var1, this, this.points(this.this$0.reference(this.profile, this.category)), this.refP);
            List var21 = this.points(this.this$0.draft(this.profile, this.category));
            onDraw$poly(var1, this, var21, this.lineP);

            for (Pair var23 : var21) {
               var2 = ((Number)var23.component1()).doubleValue();
               double var15 = ((Number)var23.component2()).doubleValue();
               var1.drawCircle(this.X(var2), this.Y(var15), this.s * 4.5F, this.dot);
               var1.drawCircle(this.X(var2), this.Y(var15), this.s * 4.5F, this.dotRing);
            }
         }
      }

      public boolean onTouchEvent(MotionEvent var1) {
         Intrinsics.checkNotNullParameter(var1, "e");
         switch (var1.getActionMasked()) {
            case 0:
               if (!this.this$0.getEditingLocked() && this.this$0.drag == null && this.this$0.data != null) {
                  float var8 = (var1.getX() - this.ox) / this.s - 30;
                  List var11 = this.points(this.this$0.draft(this.profile, this.category));
                  if (var11.isEmpty()) {
                     return false;
                  } else {
                     Iterator var13 = CollectionsKt.getIndices(var11).iterator();
                     Object var10;
                     if (!var13.hasNext()) {
                        var10 = null;
                     } else {
                        var10 = (Integer)var13.next();
                        if (var13.hasNext()) {
                           double var2 = Math.abs(((Number)((Pair)var11.get(var10.intValue())).getFirst()).doubleValue() - var8);

                           while (true) {
                              Object var12 = var13.next();
                              double var6 = Math.abs(((Number)((Pair)var11.get(((Number)var12).intValue())).getFirst()).doubleValue() - var8);
                              double var4 = var2;
                              if (Double.compare(var2, var6) > 0) {
                                 var10 = (Integer)var12;
                                 var4 = var6;
                              }

                              if (!var13.hasNext()) {
                                 break;
                              }

                              var2 = var4;
                           }
                        }
                     }

                     var10 = var10;
                     int var9;
                     if (var10 != null) {
                        var9 = var10;
                     } else {
                        var9 = 0;
                     }

                     GxPersonalitiesPage var23 = this.this$0;
                     String var24 = this.profile;
                     String var22 = this.category;
                     double var17 = this.this$0.graphMin(this.profile, this.category);
                     double var16 = this.this$0.graphMax(this.profile, this.category);
                     List var19 = this.this$0.drafts.get(this.k);
                     List var20;
                     if (var19 != null) {
                        var20 = CollectionsKt.toMutableList(var19);
                     } else {
                        var20 = null;
                     }

                     var23.drag = new GxPersonalitiesPage.Drag(var24, var22, var9, var17, var16, var20);
                     ViewParent var21 = this.getParent();
                     if (var21 != null) {
                        var21.requestDisallowInterceptTouchEvent(true);
                     }

                     this.move(var1);
                     return true;
                  }
               } else {
                  return false;
               }
            case 1:
               if (this.this$0.drag == null) {
                  return false;
               } else {
                  ViewParent var15 = this.getParent();
                  if (var15 != null) {
                     var15.requestDisallowInterceptTouchEvent(false);
                  }

                  if (this.this$0.getEditingLocked()) {
                     this.restore();
                     return true;
                  }

                  this.this$0.drag = null;
                  GxPersonalitiesPage.saveCurve$default(this.this$0, this.profile, this.category, false, 4, null);
                  this.this$0.forceRender();
                  return true;
               }
            case 2:
               if (this.this$0.drag == null) {
                  return false;
               } else {
                  if (this.this$0.getEditingLocked()) {
                     this.restore();
                     return true;
                  }

                  this.move(var1);
                  return true;
               }
            case 3:
               ViewParent var14 = this.getParent();
               if (var14 != null) {
                  var14.requestDisallowInterceptTouchEvent(false);
               }

               this.restore();
               return true;
            default:
               return false;
         }
      }
   }
}
