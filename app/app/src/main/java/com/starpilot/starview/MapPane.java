package com.starpilot.starview;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.location.Geocoder;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.text.style.ForegroundColorSpan;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.json.JSONObject;

public final class MapPane extends FrameLayout {
   private final MapPane.Banner banner;
   private final LinearLayout bottomBar;
   private final LinearLayout btnRow;
   private final TextView closeBtn;
   private final float d;
   private String dismissedFaster;
   private final TextView etaBig;
   private final TextView etaSmall;
   private final TextView faster;
   private final Runnable hideInfo;
   private final MapPane.Host host;
   private final TextView info;
   private String lastButtons;
   private String lastDest;
   private double[] lastKnownLL;
   private String lastKnownRaw;
   private int lastVersion;
   private boolean lookFresh;
   private final MapPane.MapCanvas map;
   private final TextView northBtn;
   private final TextView note;
   private final LinearLayout pinRow;
   private final HorizontalScrollView pinScroll;
   private Nav.Line prefetchedFor;
   private String previewShown;
   private final TextView recenter;
   private final FrameLayout sheet;
   private String sheetKind;
   private int sheetVersion;
   private boolean showRoutesWhenReady;
   private final LinearLayout side;
   private int stepScroll;
   private int tickN;
   private final MapTiles tiles;
   private final TextView tiltBtn;
   private final TextView voiceBtn;

   // $VF: synthetic method
   public static Unit $r8$lambda$_6_i_UDY1BC1CUumWqJ4ywXnmXo/* $VF was: $r8$lambda$_6_i_UDY1BC1CUumWqJ4ywXnmXo*/(MapPane var0) {
      return refreshButtons$lambda$48(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$_O7rtNshMo8CTmgwast2sKC2t5c/* $VF was: $r8$lambda$_O7rtNshMo8CTmgwast2sKC2t5c*/(Switch var0, View var1) {
      toggleRow$lambda$151(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$_Ob1t3aa_PF5cxv9J2quV0mzIfw/* $VF was: $r8$lambda$_Ob1t3aa_PF5cxv9J2quV0mzIfw*/(MapPane var0) {
      return tiles$lambda$1(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$08WU92oV7OXNFW2Q4lyt9b09cpc(MapPane var0) {
      return _init_$lambda$17(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$0rqokbFmE_4LOT44yXczzSZf1SM(MapPane var0, int var1, JSONObject var2, String var3) {
      return saveDestination$lambda$60$lambda$57(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$0uyfoGhURVeR2xAsoYoNaQGT5DU(MapPane var0, boolean var1) {
      return fillOptions$lambda$129(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$1IPpMRu6nLqtYZnnm_y68vri6CU/* $VF was: $r8$lambda$1IPpMRu6nLqtYZnnm_y68vri6CU*/(MapPane var0, boolean var1) {
      return fillOptions$lambda$126(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$3AGJVb2pHZAStoDdmkFYsiAdplY(MapPane var0) {
      return _init_$lambda$18(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$6KEsm5quxKt778TcqUNYSthi23s(JSONObject var0) {
      return tick$lambda$29$lambda$28(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$6TEzuliI38GA_fOE5od5HIEmeqQ/* $VF was: $r8$lambda$6TEzuliI38GA_fOE5od5HIEmeqQ*/(
      int var0, Function1 var1, MapPane var2, String var3
   ) {
      return saveDestination$lambda$60$lambda$59(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$6ycLDUOFNGJDezop2v1gUXWvcmw(MapPane var0, String var1) {
      return fillOptions$lambda$122$lambda$121$lambda$120(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$79Sp2l62EvGC_OCM1oWKe_JPmqI(MapPane var0, boolean var1) {
      return fillOptions$lambda$138(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$7LJjVJ0wS_hmo8NoiTNH_B7Bg80/* $VF was: $r8$lambda$7LJjVJ0wS_hmo8NoiTNH_B7Bg80*/(MapPane var0) {
      onNav$lambda$37(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$7zofi3OTx_vON_HZQsrR9OkbVQU/* $VF was: $r8$lambda$7zofi3OTx_vON_HZQsrR9OkbVQU*/(
      Function1 var0, CompoundButton var1, boolean var2
   ) {
      toggleRow$lambda$150$lambda$149(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$8CBQ8cq9dpU1cHauHnV_h48Rzzk(TextView var0, MapPane var1, String var2, JSONObject var3, View var4) {
      refreshButtons$lambda$51$lambda$50(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$8J87_2qypPKQmNiTNRd_0HikpoQ(MapPane var0) {
      return fillSheet$lambda$79(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$9jZnjoMbyVgJEMZATLB_c0gAqZo(MapPane var0) {
      return refreshButtons$lambda$46(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$DKVYS9a_9kEPAinJxI_aTjENLeE(MapPane var0, boolean var1) {
      return refreshButtons$lambda$45(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$ElxG3BSwXV98GIUC4aCpqwUOnvs(MapPane var0) {
      return _init_$lambda$14(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$F4PL95OLOuTLvfq54Bu_xFu2umQ(MapPane var0, String var1, View var2) {
      fillOptions$lambda$133$lambda$132(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$F4rlRgHUPNMFajxQMkOThslN32E(MapPane var0, Nav.Step var1, View var2) {
      fillSteps$lambda$114(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$G9lDclssPyUcThR2aGCu5XTUCMs(MapPane var0) {
      return fillSheet$lambda$82(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$II0P2cvq4j9eoZXPq8xKjMnPlhA(MapPane var0) {
      return fillSheet$lambda$80(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$K_KvmsvOh9G6oEDEkBOOusLlYRs/* $VF was: $r8$lambda$K_KvmsvOh9G6oEDEkBOOusLlYRs*/(MapPane var0) {
      return refreshButtons$lambda$42(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Pc3BQiuQll3tZNIF2pmGQjDdW4A(MapPane var0) {
      return _init_$lambda$12(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$RYJHfn7PeTF7EgvdNNl3yg0yxyg(MapPane var0) {
      fillOptions$lambda$138$lambda$137(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$T3QbF3_R4SDXDhxFIRm0ZhCy0pk/* $VF was: $r8$lambda$T3QbF3_R4SDXDhxFIRm0ZhCy0pk*/(MapPane var0) {
      return refreshButtons$lambda$43(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$UKpb63YyprUjR9UwVU1ps4B2baQ(MapPane var0, View var1) {
      fillSheet$lambda$68$lambda$67(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$UfoaXso871KrHnwuOzNqb9CeyH4(MapPane var0, String var1) {
      return goWithLooked$lambda$34(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$UsulQsBngUHXPv_pkzp6JjBkr68/* $VF was: $r8$lambda$UsulQsBngUHXPv_pkzp6JjBkr68*/(MapPane var0, View var1) {
      fillSheet$lambda$74$lambda$73(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$WUc7KlJ8hyb5AQPJQKewnRSZucs(MapPane var0, int var1, View var2) {
      fillOptions$lambda$122$lambda$121(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$XwkvxZacRhIJWhyC4VYg9ajtF4c(MapPane var0, View var1) {
      fillSheet$lambda$77$lambda$76(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$YqrQZ4U5ecrAoUsufLRVP6EhKKI(Function0 var0, View var1) {
      refreshButtons$b$lambda$40(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$ZQ1VPY35V0Pc_UlnRV6sah0wk4k/* $VF was: $r8$lambda$ZQ1VPY35V0Pc_UlnRV6sah0wk4k*/(MapPane var0) {
      return _init_$lambda$13(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$dk_5iqc9QY0qjBfkyJyq6cGsaWs/* $VF was: $r8$lambda$dk_5iqc9QY0qjBfkyJyq6cGsaWs*/(MapPane var0) {
      return fillSheet$lambda$81(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$gqabWpiCCzn3KjEID15jqoOQOaY(MapPane var0, String var1) {
      return fillOptions$lambda$117$lambda$116(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$kWhSUGhDzNvpMJ7RGW0cww_1AVM(MapPane var0, Nav.Route var1, View var2) {
      fillRoutes$lambda$100(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$kzk4WH_n7_HSwrdU8FhX6DaVDKs/* $VF was: $r8$lambda$kzk4WH_n7_HSwrdU8FhX6DaVDKs*/(MapPane var0) {
      tiles$lambda$1$lambda$0(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$mmcEzRp98eEMghEbspfa5CCRi6I(MapPane var0) {
      return refreshButtons$lambda$44(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$mxErwZpXFKYbhgHj6UN8aS9_QK4/* $VF was: $r8$lambda$mxErwZpXFKYbhgHj6UN8aS9_QK4*/(Function0 var0, View var1) {
      fillSheet$lambda$84$lambda$83(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$n1nlPch6DkZbbthI3pUcMZwjB_M(MapPane var0) {
      hideInfo$lambda$63(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$oiMuiWSGBWfNwGPpjjnBCW1NUhA(Function0 var0, View var1) {
      sideBtn$lambda$11$lambda$9(var0, var1);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$pjv7gC8Zi5vbAU9RoKn55upKa_E(MapPane var0, String var1, Nav.Route var2, View var3, MotionEvent var4) {
      return refreshBars$lambda$54(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static void $r8$lambda$rQu7OebEE7FXUALxraUbX9Vk5_U/* $VF was: $r8$lambda$rQu7OebEE7FXUALxraUbX9Vk5_U*/(MapPane var0, DialogInterface var1, int var2) {
      confirmEnd$lambda$62(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$srwNnBAUl11Gp445oLLIj6pzrSM(MapPane var0) {
      return refreshButtons$lambda$47(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$swYaidbLOz3cAyRdQCkJ72iLmB0(JSONObject var0, MapPane var1, String var2, String var3, DialogInterface var4, int var5) {
      saveDestination$lambda$60(var0, var1, var2, var3, var4, var5);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$t6KJYnZKiGGqxtMlbX5D7c_Tb1U(MapPane var0) {
      return _init_$lambda$16(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$ul9_3TnU1cV4BgGCKIE2G_BK_tQ/* $VF was: $r8$lambda$ul9_3TnU1cV4BgGCKIE2G_BK_tQ*/(MapPane var0, String var1) {
      return goWithLooked$lambda$33(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$w0daMP8XwIwdl4mc9W6E9LbG_ZI/* $VF was: $r8$lambda$w0daMP8XwIwdl4mc9W6E9LbG_ZI*/(MapPane var0) {
      return _init_$lambda$15(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$wSwHQhLNqXEI0sHGovwwc_ZUmKE(MapPane var0, boolean var1) {
      return fillOptions$lambda$128(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$xBqg74ppensNykRSMHLM6_1V8UY(MapPane var0, String var1) {
      return confirmEnd$lambda$62$lambda$61(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$xtiptgBDoUBufZEAXL3TYC12iIM(MapPane var0, String var1) {
      return fillOptions$lambda$126$lambda$125(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$yK_DGhQXIcAJJ1Lr6N5KcPhGJ5w/* $VF was: $r8$lambda$yK_DGhQXIcAJJ1Lr6N5KcPhGJ5w*/(ScrollView var0, MapPane var1, int var2) {
      fillSheet$lambda$87(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$z3MerjUl23yvI__TLbQdkCG6p4E/* $VF was: $r8$lambda$z3MerjUl23yvI__TLbQdkCG6p4E*/(MapPane var0, String var1, boolean var2) {
      return fillOptions$lambda$117(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$zylewyqes295PcHhtwCJ_7Q1SAw(MapPane var0) {
      return refreshButtons$lambda$49(var0);
   }

   public MapPane(Context var1, MapPane.Host var2) {
      Intrinsics.checkNotNullParameter(var1, "ctx");
      Intrinsics.checkNotNullParameter(var2, "host");
      super(var1);
      this.host = var2;
      this.d = this.getResources().getDisplayMetrics().density;
      this.tiles = new MapTiles(var1, new MapPane$$ExternalSyntheticLambda9(this));
      this.map = new MapPane.MapCanvas(this, var1);
      this.banner = new MapPane.Banner(this, var1);
      this.faster = this.chip();
      this.note = new TextView(var1);
      this.info = new TextView(var1);
      this.bottomBar = new LinearLayout(var1);
      this.etaBig = new TextView(var1);
      this.etaSmall = new TextView(var1);
      this.btnRow = new LinearLayout(var1);
      LinearLayout var9 = new LinearLayout(var1);
      var9.setOrientation(0);
      this.pinRow = var9;
      this.pinScroll = new HorizontalScrollView(var1);
      this.side = new LinearLayout(var1);
      this.sheet = new FrameLayout(var1);
      this.sheetKind = "";
      this.sheetVersion = -1;
      this.lastVersion = -1;
      this.dismissedFaster = "";
      this.lastDest = "";
      this.setClipChildren(true);
      this.setBackgroundColor(Color.rgb(27, 30, 36));
      this.addView(this.map, (LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -1)));
      View var3 = this.banner;
      android.widget.FrameLayout.LayoutParams var4 = new android.widget.FrameLayout.LayoutParams(-1, this.dpi(92), 48);
      var4.setMargins(this.dpi(10), this.dpi(10), this.dpi(10), 0);
      Unit var10 = Unit.INSTANCE;
      this.addView(var3, (LayoutParams)var4);
      this.banner.setVisibility(8);
      var3 = (View)this.faster;
      var4 = new android.widget.FrameLayout.LayoutParams(-2, -2, 49);
      var4.topMargin = this.dpi(110);
      Unit var11 = Unit.INSTANCE;
      this.addView(var3, (LayoutParams)var4);
      this.faster.setVisibility(8);
      TextView var12 = this.info;
      var12.setTextColor(-1);
      var12.setTextSize(14.0F);
      var12.setPadding(this.dpi(14), this.dpi(10), this.dpi(14), this.dpi(10));
      var12.setBackground((Drawable)card$default(this, -434101200, 14.0F, 0, false, 12, null));
      var12.setVisibility(8);
      var12.setMaxLines(4);
      View var13 = (View)this.info;
      android.widget.FrameLayout.LayoutParams var24 = new android.widget.FrameLayout.LayoutParams(-2, -2, 49);
      var24.topMargin = this.dpi(160);
      var24.setMarginStart(this.dpi(16));
      var24.setMarginEnd(this.dpi(16));
      Unit var32 = Unit.INSTANCE;
      this.addView(var13, (LayoutParams)var24);
      TextView var14 = this.note;
      var14.setTextColor(-1);
      var14.setTextSize(16.0F);
      var14.setGravity(17);
      var14.setPadding(this.dpi(18), this.dpi(14), this.dpi(18), this.dpi(14));
      var14.setBackground((Drawable)card$default(this, -434101200, 16.0F, 0, false, 12, null));
      var14.setVisibility(8);
      var3 = (View)this.note;
      var4 = new android.widget.FrameLayout.LayoutParams(-2, -2, 17);
      var4.setMarginStart(this.dpi(20));
      var4.setMarginEnd(this.dpi(20));
      Unit var15 = Unit.INSTANCE;
      this.addView(var3, (LayoutParams)var4);
      this.side.setOrientation(1);
      this.recenter = _init_$sideBtn(this, "◎", new MapPane$$ExternalSyntheticLambda10(this));
      _init_$sideBtn(this, "+", new MapPane$$ExternalSyntheticLambda12(this));
      _init_$sideBtn(this, "−", new MapPane$$ExternalSyntheticLambda13(this));
      this.northBtn = _init_$sideBtn(this, "▲", new MapPane$$ExternalSyntheticLambda14(this));
      this.tiltBtn = _init_$sideBtn(this, "3D", new MapPane$$ExternalSyntheticLambda15(this));
      this.voiceBtn = _init_$sideBtn(this, "\ud83d\udd0a", new MapPane$$ExternalSyntheticLambda16(this));
      this.closeBtn = _init_$sideBtn(this, "‹", new MapPane$$ExternalSyntheticLambda17(this));
      View var34 = (View)this.side;
      android.widget.FrameLayout.LayoutParams var16 = new android.widget.FrameLayout.LayoutParams(-2, -2, 8388629);
      var16.setMarginEnd(this.dpi(10));
      Unit var26 = Unit.INSTANCE;
      this.addView(var34, (LayoutParams)var16);
      this.bottomBar.setOrientation(1);
      this.bottomBar.setBackground((Drawable)card$default(this, -350610141, 18.0F, 0, false, 12, null));
      this.bottomBar.setPadding(this.dpi(14), this.dpi(10), this.dpi(14), this.dpi(10));
      LinearLayout var5 = new LinearLayout(var1);
      var5.setOrientation(0);
      var5.setGravity(16);
      TextView var17 = this.etaBig;
      var17.setTextColor(-1);
      var17.setTextSize(24.0F);
      var17.setTypeface(Typeface.DEFAULT_BOLD);
      var17.setMaxLines(1);
      var17.setEllipsize(TruncateAt.END);
      TextView var18 = this.etaSmall;
      var18.setTextColor(Color.argb(200, 255, 255, 255));
      var18.setTextSize(15.0F);
      var18.setMaxLines(2);
      var18.setEllipsize(TruncateAt.END);
      var5.addView((View)this.etaBig, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(-2, -2)));
      View var19 = (View)this.etaSmall;
      android.widget.LinearLayout.LayoutParams var35 = new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F);
      var35.setMarginStart(this.dpi(12));
      Unit var27 = Unit.INSTANCE;
      var5.addView(var19, (LayoutParams)var35);
      this.bottomBar.addView((View)var5);
      this.pinScroll.addView((View)this.pinRow);
      this.pinScroll.setHorizontalScrollBarEnabled(false);
      LinearLayout var6 = this.bottomBar;
      var3 = (View)this.pinScroll;
      android.widget.LinearLayout.LayoutParams var36 = new android.widget.LinearLayout.LayoutParams(-1, -2);
      var36.topMargin = this.dpi(6);
      Unit var20 = Unit.INSTANCE;
      var6.addView(var3, (LayoutParams)var36);
      this.btnRow.setOrientation(0);
      LinearLayout var29 = this.bottomBar;
      View var21 = (View)this.btnRow;
      android.widget.LinearLayout.LayoutParams var7 = new android.widget.LinearLayout.LayoutParams(-1, this.dpi(46));
      var7.topMargin = this.dpi(8);
      Unit var37 = Unit.INSTANCE;
      var29.addView(var21, (LayoutParams)var7);
      View var8 = (View)this.bottomBar;
      android.widget.FrameLayout.LayoutParams var30 = new android.widget.FrameLayout.LayoutParams(-1, -2, 80);
      var30.setMargins(this.dpi(10), 0, this.dpi(10), this.dpi(10));
      Unit var22 = Unit.INSTANCE;
      this.addView(var8, (LayoutParams)var30);
      this.sheet.setVisibility(8);
      this.sheet.setElevation(this.dp(8.0F));
      this.addView((View)this.sheet, (LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -2, 80)));
      this.refreshButtons();
      this.lastKnownRaw = "";
      this.lastKnownLL = new double[]{Double.NaN, Double.NaN};
      this.previewShown = "";
      this.lastButtons = "";
      this.hideInfo = new MapPane$$ExternalSyntheticLambda18(this);
   }

   private static final Unit _init_$lambda$12(MapPane var0) {
      var0.map.follow();
      return Unit.INSTANCE;
   }

   private static final Unit _init_$lambda$13(MapPane var0) {
      var0.map.zoomBy(1.0);
      return Unit.INSTANCE;
   }

   private static final Unit _init_$lambda$14(MapPane var0) {
      var0.map.zoomBy(-1.0);
      return Unit.INSTANCE;
   }

   private static final Unit _init_$lambda$15(MapPane var0) {
      var0.map.compass();
      var0.refreshButtons();
      return Unit.INSTANCE;
   }

   private static final Unit _init_$lambda$16(MapPane var0) {
      UiSettings var3 = var0.getS();
      UiSettings var2 = var0.getS();
      float var1 = 0.0F;
      if (!(var2.getFloat("map_tilt", 0.0F) > 5.0F)) {
         var1 = 45.0F;
      }

      var3.setFloat("map_tilt", var1);
      var0.map.invalidate();
      var0.refreshButtons();
      return Unit.INSTANCE;
   }

   private static final Unit _init_$lambda$17(MapPane var0) {
      String var1 = Nav.INSTANCE.voiceMode(var0.getS());
      if (Intrinsics.areEqual(var1, "all")) {
         var1 = "alerts";
      } else if (Intrinsics.areEqual(var1, "alerts")) {
         var1 = "off";
      } else {
         var1 = "all";
      }

      var0.getS().setStr("nav_voice", var1);
      var0.refreshButtons();
      String var2;
      if (Intrinsics.areEqual(var1, "all")) {
         var2 = "Voice: turn-by-turn directions and alerts";
      } else if (Intrinsics.areEqual(var1, "alerts")) {
         var2 = "Voice: alerts only (incidents, closures, faster routes)";
      } else {
         var2 = "Voice off";
      }

      var0.showInfo(var2);
      if (Intrinsics.areEqual(var1, "all")) {
         Speech.INSTANCE.speak("Voice directions on", true);
      }

      return Unit.INSTANCE;
   }

   private static final Unit _init_$lambda$18(MapPane var0) {
      var0.host.closeMap();
      return Unit.INSTANCE;
   }

   private static final TextView _init_$sideBtn(MapPane var0, String var1, Function0<Unit> var2) {
      TextView var6 = var0.round(var1, 44);
      var6.setOnClickListener(new MapPane$$ExternalSyntheticLambda6(var2));
      LinearLayout var7 = var0.side;
      View var4 = (View)var6;
      android.widget.LinearLayout.LayoutParams var3 = new android.widget.LinearLayout.LayoutParams(var0.dpi(48), var0.dpi(48));
      var3.bottomMargin = var0.dpi(10);
      Unit var5 = Unit.INSTANCE;
      var7.addView(var4, (LayoutParams)var3);
      return var6;
   }

   private final GradientDrawable card(int var1, float var2, int var3, boolean var4) {
      GradientDrawable var6 = new GradientDrawable();
      var6.setColor(var1);
      float var5 = this.dp(var2);
      if (var4) {
         var6.setCornerRadii(new float[]{var5, this.dp(var2), this.dp(var2), this.dp(var2), 0.0F, 0.0F, 0.0F, 0.0F});
      } else {
         var6.setCornerRadius(var5);
      }

      if (var3 != 0) {
         var6.setStroke(this.dpi(2), var3);
      }

      return var6;
   }

   // $VF: synthetic method
   static GradientDrawable card$default(MapPane var0, int var1, float var2, int var3, boolean var4, int var5, Object var6) {
      if ((var5 & 4) != 0) {
         var3 = 0;
      }

      if ((var5 & 8) != 0) {
         var4 = false;
      }

      return var0.card(var1, var2, var3, var4);
   }

   private final TextView chip() {
      TextView var1 = new TextView(this.getContext());
      var1.setTextSize(15.0F);
      var1.setTypeface(Typeface.DEFAULT_BOLD);
      var1.setTextColor(-1);
      var1.setPadding(this.dpi(16), this.dpi(10), this.dpi(16), this.dpi(10));
      var1.setBackground((Drawable)card$default(this, -265381033, 22.0F, 0, false, 12, null));
      return var1;
   }

   private final void closeSheet() {
      String var1 = this.sheetKind;
      this.sheetKind = "";
      this.sheet.setVisibility(8);
      this.sheet.removeAllViews();
      this.refreshBars();
      if (Nav.INSTANCE.getInPreview()) {
         Nav.INSTANCE.cancelPreview();
      }

      if (Nav.INSTANCE.getLook() >= 0) {
         Nav.INSTANCE.setLook(-1);
         this.map.linesChanged();
      }

      if (Intrinsics.areEqual(var1, "routes") || Intrinsics.areEqual(var1, "look") || this.map.getPreviewing()) {
         this.map.follow();
      }
   }

   private final void confirmEnd() {
      Builder var3;
      String var4;
      label11: {
         var3 = new Builder(this.getContext());
         JSONObject var1 = Nav.INSTANCE.getDestination();
         if (var1 != null) {
            String var2 = var1.optString("name");
            var4 = var2;
            if (var2 != null) {
               break label11;
            }
         }

         var4 = "the destination";
      }

      var3.setMessage("End the route to " + var4 + "?")
         .setPositiveButton("End", new MapPane$$ExternalSyntheticLambda8(this))
         .setNegativeButton("Keep", null)
         .show();
   }

   private static final void confirmEnd$lambda$62(MapPane var0, DialogInterface var1, int var2) {
      Nav.INSTANCE.cancel(var0.host.getData(), new MapPane$$ExternalSyntheticLambda44(var0));
   }

   private static final Unit confirmEnd$lambda$62$lambda$61(MapPane var0, String var1) {
      if (var1 != null) {
         var0.toast(var1);
      }

      return Unit.INSTANCE;
   }

   private final float dp(float var1) {
      return this.d * var1;
   }

   private final int dpi(int var1) {
      return MathKt.roundToInt(var1 * this.d);
   }

   private final double drivenAlong() {
      double var1;
      if (!Nav.INSTANCE.getInPreview() && Nav.INSTANCE.getShownSelected() == Nav.INSTANCE.getSelected()) {
         var1 = Nav.INSTANCE.getAlong();
      } else {
         var1 = Double.NaN;
      }

      return var1;
   }

   private final void fillLook(LinearLayout var1) {
      Nav.Route var11 = Nav.INSTANCE.getShownRoute();
      Nav.Line var9 = Nav.INSTANCE.getShownLine();
      if (var11 != null && var9 != null) {
         Iterator var8 = Nav.INSTANCE.getShownRoutes().iterator();
         if (!var8.hasNext()) {
            throw new NoSuchElementException();
         } else {
            double var2 = ((Nav.Route)var8.next()).getDurationS();

            while (var8.hasNext()) {
               var2 = Math.min(var2, ((Nav.Route)var8.next()).getDurationS());
            }

            boolean var7;
            ArrayList var10;
            label62: {
               var10 = new ArrayList();
               String var15 = Nav.INSTANCE.etaText(var11.getDurationS());
               var10.add("arrive " + var15);
               double var4 = var11.getDurationS();
               var7 = true;
               String var17;
               if (var4 - var2 >= 60.0) {
                  String var16 = Nav.INSTANCE.durationText(var11.getDurationS() - var2);
                  var17 = var16 + " slower";
               } else {
                  if (Nav.INSTANCE.getShownRoutes().size() <= 1) {
                     break label62;
                  }

                  var17 = "fastest";
               }

               var10.add(var17);
            }

            var2 = var11.getTypicalS();
            boolean var6;
            if (!Double.isInfinite(var2) && !Double.isNaN(var2)) {
               var6 = 1;
            } else {
               var6 = 0;
            }

            if (var6 && var11.getDurationS() - var11.getTypicalS() >= 120.0) {
               String var18 = Nav.INSTANCE.durationText(var11.getDurationS() - var11.getTypicalS());
               var10.add("traffic +" + var18);
            }

            if (var11.getClasses().contains("toll")) {
               var10.add("tolls");
            }

            if (var11.getIncidents() > 0) {
               var6 = var11.getIncidents();
               String var19;
               if (var11.getIncidents() > 1) {
                  var19 = "s";
               } else {
                  var19 = "";
               }

               var10.add(var6 + " incident" + var19);
            }

            boolean var14;
            if (var11.getVia().length() > 0) {
               var14 = var7;
            } else {
               var14 = false;
            }

            if (var14) {
               String var20 = var11.getVia();
               var10.add("via " + var20);
            }

            TextView var21 = label$default(
               this, CollectionsKt.joinToString$default(var10, "  ·  ", null, null, 0, null, null, 62, null), 13.0F, false, true, 4, null
            );
            var21.setPadding(0, 0, 0, this.dpi(8));
            var1.addView((View)var21);
            this.fillSteps(var1, var9, false);
         }
      } else {
         var1.addView((View)label$default(this, "No directions for this route.", 15.0F, false, false, 12, null));
      }
   }

   private final void fillOptions(LinearLayout var1) {
      JSONObject var5 = Nav.INSTANCE.getOptions();
      var1.addView((View)this.label("Avoid", 14.0F, true, true));
      Pair var6 = TuplesKt.to("avoidTolls", "Tolls");
      Pair var8 = TuplesKt.to("avoidHighways", "Highways");
      Pair var11 = TuplesKt.to("avoidFerries", "Ferries");
      Pair var7 = TuplesKt.to("avoidUnpaved", "Unpaved roads");
      Pair var10 = TuplesKt.to("avoidCashTolls", "Cash-only tolls (fine with E-ZPass)");
      Pair var9 = TuplesKt.to("avoidTunnels", "Tunnels");

      for (var var7_it : CollectionsKt.listOf(new Pair[]{var6, var8, var11, var7, var10, var9, TuplesKt.to("avoidBorders", "Border crossings")})) {

         var7 = var7_it;
         String var40 = (String)var7.component1();
         var1.addView(this.toggleRow((String)var7.component2(), var5.optBoolean(var40), new MapPane$$ExternalSyntheticLambda36(this, var40)));
      }

      TextView var26 = this.label("Carpool (HOV) lanes", 14.0F, true, true);
      var26.setPadding(0, this.dpi(12), 0, 0);
      var1.addView((View)var26);
      var1.addView((View)label$default(this, "Left out of routes unless you have the people for them.", 13.0F, false, true, 4, null));
      LinearLayout var27 = new LinearLayout(this.getContext());
      var27.setOrientation(0);

      for (Pair var41 : CollectionsKt.listOf(new Pair[]{TuplesKt.to(0, "Off"), TuplesKt.to(2, "2+ people"), TuplesKt.to(3, "3+ people")})) {
         int var2 = ((Number)var41.component1()).intValue();
         String var42 = (String)var41.component2();
         boolean var4;
         if (var5.optInt("carpool", 0) == var2) {
            var4 = true;
         } else {
            var4 = false;
         }

         TextView var43 = this.pill(var42, var4);
         var43.setOnClickListener(new MapPane$$ExternalSyntheticLambda37(this, var2));
         View var44 = (View)var43;
         android.widget.LinearLayout.LayoutParams var49 = new android.widget.LinearLayout.LayoutParams(0, this.dpi(42), 1.0F);
         var49.setMarginEnd(this.dpi(6));
         Unit var51 = Unit.INSTANCE;
         var27.addView(var44, (LayoutParams)var49);
      }

      View var45 = (View)var27;
      android.widget.LinearLayout.LayoutParams var28 = new android.widget.LinearLayout.LayoutParams(-1, -2);
      var28.topMargin = this.dpi(6);
      Unit var36 = Unit.INSTANCE;
      var1.addView(var45, (LayoutParams)var28);
      var1.addView(
         this.toggleRow(
            "Express toll lanes (HOT): free with the people, a toll without", var5.optBoolean("expressLanes"), new MapPane$$ExternalSyntheticLambda38(this)
         )
      );
      TextView var15 = label$default(this, "Changes ask the comma for new routes. Mapbox avoids these when it can.", 12.0F, false, true, 4, null);
      var15.setPadding(0, this.dpi(4), 0, this.dpi(8));
      var1.addView((View)var15);
      var1.addView((View)this.label("Map", 14.0F, true, true));
      var1.addView(this.toggleRow("Live traffic on every road", this.getS().get("map_traffic"), new MapPane$$ExternalSyntheticLambda39(this)));
      var1.addView(this.toggleRow("North up (off: the way you drive is up)", this.getS().get("map_north_up"), new MapPane$$ExternalSyntheticLambda40(this)));
      TextView var16 = this.label("Voice", 14.0F, true, true);
      var16.setPadding(0, this.dpi(10), 0, 0);
      var1.addView((View)var16);
      LinearLayout var17 = new LinearLayout(this.getContext());
      var17.setOrientation(0);

      for (Pair var46 : CollectionsKt.listOf(
         new Pair[]{TuplesKt.to("all", "\ud83d\udd0a Turn-by-turn"), TuplesKt.to("alerts", "⚠ Alerts only"), TuplesKt.to("off", "\ud83d\udd07 Off")}
      )) {
         String var37 = (String)var46.component1();
         TextView var47 = this.pill((String)var46.component2(), Intrinsics.areEqual(Nav.INSTANCE.voiceMode(this.getS()), var37));
         var47.setTextSize(13.0F);
         var47.setOnClickListener(new MapPane$$ExternalSyntheticLambda41(this, var37));
         View var50 = (View)var47;
         android.widget.LinearLayout.LayoutParams var38 = new android.widget.LinearLayout.LayoutParams(0, this.dpi(42), 1.0F);
         var38.setMarginEnd(this.dpi(6));
         Unit var48 = Unit.INSTANCE;
         var17.addView(var50, (LayoutParams)var38);
      }

      View var18 = (View)var17;
      android.widget.LinearLayout.LayoutParams var30 = new android.widget.LinearLayout.LayoutParams(-1, -2);
      var30.topMargin = this.dpi(6);
      Unit var39 = Unit.INSTANCE;
      var1.addView(var18, (LayoutParams)var30);
      TextView var19 = label$default(this, "Alerts: crashes, closures and hazards ahead, and a faster route.", 12.0F, false, true, 4, null);
      var19.setPadding(0, this.dpi(4), 0, this.dpi(6));
      var1.addView((View)var19);
      var1.addView(
         this.toggleRow(
            "Map on the right side", Intrinsics.areEqual(this.getS().getStr("map_side", "left"), "right"), new MapPane$$ExternalSyntheticLambda42(this)
         )
      );
      JSONObject var31 = Nav.INSTANCE.getUsage();
      var5 = var31.optJSONObject("staticTiles");
      JSONObject var32 = var31.optJSONObject("directions");
      if (var5 != null || var32 != null) {
         String var21;
         if (var5 != null) {
            int var3 = var5.optInt("used");
            int var12 = var5.optInt("free");
            var21 = var3 + " of " + var12 + " free map views";
         } else {
            var21 = null;
         }

         String var33;
         if (var32 != null) {
            int var13 = var32.optInt("used");
            int var14 = var32.optInt("free");
            var33 = var13 + " of " + var14 + " free routes";
         } else {
            var33 = null;
         }

         String var22 = CollectionsKt.joinToString$default(CollectionsKt.listOfNotNull(new String[]{var21, var33}), ", ", null, null, 0, null, null, 62, null);
         String var23 = "This month on the comma's Mapbox key: " + var22 + ".";
         TextView var24 = label$default(this, var23, 12.0F, false, true, 4, null);
         var24.setPadding(0, this.dpi(10), 0, 0);
         var1.addView((View)var24);
      }
   }

   private static final Unit fillOptions$lambda$117(MapPane var0, String var1, boolean var2) {
      Nav var3 = Nav.INSTANCE;
      DataClient var4 = var0.host.getData();
      JSONObject var5 = new JSONObject().put(var1, var2);
      Intrinsics.checkNotNullExpressionValue(var5, "put(...)");
      var3.setOptions(var4, var5, new MapPane$$ExternalSyntheticLambda25(var0));
      return Unit.INSTANCE;
   }

   private static final Unit fillOptions$lambda$117$lambda$116(MapPane var0, String var1) {
      if (var1 != null) {
         var0.toast(var1);
      }

      return Unit.INSTANCE;
   }

   private static final void fillOptions$lambda$122$lambda$121(MapPane var0, int var1, View var2) {
      Nav var5 = Nav.INSTANCE;
      DataClient var4 = var0.host.getData();
      JSONObject var3 = new JSONObject().put("carpool", var1);
      Intrinsics.checkNotNullExpressionValue(var3, "put(...)");
      var5.setOptions(var4, var3, new MapPane$$ExternalSyntheticLambda49(var0));
   }

   private static final Unit fillOptions$lambda$122$lambda$121$lambda$120(MapPane var0, String var1) {
      if (var1 != null) {
         var0.toast(var1);
      }

      return Unit.INSTANCE;
   }

   private static final Unit fillOptions$lambda$126(MapPane var0, boolean var1) {
      Nav var2 = Nav.INSTANCE;
      DataClient var4 = var0.host.getData();
      JSONObject var3 = new JSONObject().put("expressLanes", var1);
      Intrinsics.checkNotNullExpressionValue(var3, "put(...)");
      var2.setOptions(var4, var3, new MapPane$$ExternalSyntheticLambda45(var0));
      return Unit.INSTANCE;
   }

   private static final Unit fillOptions$lambda$126$lambda$125(MapPane var0, String var1) {
      if (var1 != null) {
         var0.toast(var1);
      }

      return Unit.INSTANCE;
   }

   private static final Unit fillOptions$lambda$128(MapPane var0, boolean var1) {
      var0.getS().set("map_traffic", var1);
      var0.tiles.clearMemory();
      var0.map.invalidate();
      return Unit.INSTANCE;
   }

   private static final Unit fillOptions$lambda$129(MapPane var0, boolean var1) {
      var0.getS().set("map_north_up", var1);
      var0.refreshButtons();
      var0.map.invalidate();
      return Unit.INSTANCE;
   }

   private static final void fillOptions$lambda$133$lambda$132(MapPane var0, String var1, View var2) {
      var0.getS().setStr("nav_voice", var1);
      var0.refreshButtons();
      var0.fillSheet();
   }

   private static final Unit fillOptions$lambda$138(MapPane var0, boolean var1) {
      UiSettings var3 = var0.getS();
      String var2;
      if (var1) {
         var2 = "right";
      } else {
         var2 = "left";
      }

      var3.setStr("map_side", var2);
      var0.host.closeMap();
      var0.post(new MapPane$$ExternalSyntheticLambda21(var0));
      return Unit.INSTANCE;
   }

   private static final void fillOptions$lambda$138$lambda$137(MapPane var0) {
      Context var1 = var0.getContext();
      MainActivity var2;
      if (var1 instanceof MainActivity) {
         var2 = (MainActivity)var1;
      } else {
         var2 = null;
      }

      if (var2 != null) {
         var2.openMap();
      }
   }

   private final void fillRoutes(LinearLayout var1) {
      boolean var10 = this.getT().isMetric();
      List var13 = Nav.INSTANCE.getShownRoutes();
      boolean var8 = var13.isEmpty();
      String var12 = "";
      if (var8) {
         String var53;
         label168: {
            if (Nav.INSTANCE.getInPreview()) {
               boolean var29;
               if (Nav.INSTANCE.getPreviewError().length() > 0) {
                  var29 = true;
               } else {
                  var29 = false;
               }

               if (var29) {
                  var53 = Nav.INSTANCE.getPreviewError();
                  var53 = "No routes: " + var53;
                  break label168;
               }
            }

            if (Nav.INSTANCE.getInPreview()) {
               JSONObject var50 = Nav.INSTANCE.getPreviewing();
               if (var50 != null) {
                  var53 = var50.optString("name");
               } else {
                  var53 = null;
               }

               if (var53 == null) {
                  var53 = var12;
               }

               var53 = var53;
               boolean var30;
               if (var53.length() == 0) {
                  var30 = true;
               } else {
                  var30 = false;
               }

               if (var30) {
                  var53 = "there";
               }

               var53 = "Finding routes to " + var53 + "…";
            } else if (Intrinsics.areEqual(Nav.INSTANCE.getStatus(), "routeUnavailable")) {
               var53 = "No route found. The comma tries again in 30 s.";
            } else {
               var53 = "Finding routes…";
            }
         }

         var1.addView((View)label$default(this, var53, 15.0F, false, false, 12, null));
      } else {
         if (Nav.INSTANCE.getInPreview()) {
            TextView var11 = label$default(this, "Tap a route to see its directions. Nothing is sent to the comma until Go.", 13.0F, false, true, 4, null);
            var11.setPadding(0, 0, 0, this.dpi(8));
            var1.addView((View)var11);
         }

         Iterator var35 = var13.iterator();
         if (!var35.hasNext()) {
            throw new NoSuchElementException();
         } else {
            double var2 = ((Nav.Route)var35.next()).getDurationS();

            while (var35.hasNext()) {
               var2 = Math.min(var2, ((Nav.Route)var35.next()).getDurationS());
            }

            for (Nav.Route var16 : var13) {
               if (var16.getIndex() == Nav.INSTANCE.getShownSelected()) {
                  var8 = true;
               } else {
                  var8 = false;
               }

               LinearLayout var15 = new LinearLayout(this.getContext());
               var15.setOrientation(0);
               var15.setPadding(this.dpi(12), this.dpi(10), this.dpi(12), this.dpi(10));
               int var6;
               if (var8) {
                  var6 = 858751213;
               } else {
                  var6 = 352321535;
               }

               int var7;
               if (var8) {
                  var7 = -13664019;
               } else {
                  var7 = 0;
               }

               var15.setBackground((Drawable)card$default(this, var6, 14.0F, var7, false, 8, null));
               View var55 = new View(this.getContext());
               var55.setBackground((Drawable)card$default(this, this.routeColor(var8), 6.0F, 0, false, 12, null));
               android.widget.LinearLayout.LayoutParams var36 = new android.widget.LinearLayout.LayoutParams(this.dpi(8), -1);
               var36.setMarginEnd(this.dpi(12));
               Unit var17 = Unit.INSTANCE;
               var15.addView(var55, (LayoutParams)var36);
               LinearLayout var75 = new LinearLayout(this.getContext());
               var75.setOrientation(1);
               LinearLayout var56 = new LinearLayout(this.getContext());
               var56.setOrientation(0);
               var56.setGravity(80);
               var56.addView((View)label$default(this, Nav.INSTANCE.durationText(var16.getDurationS()), 22.0F, true, false, 8, null));
               String var37 = Nav.distanceText$default(Nav.INSTANCE, var16.getDistanceM(), var10, false, 4, null);
               String var18 = Nav.INSTANCE.etaText(var16.getDurationS());
               var56.addView((View)label$default(this, "   " + var37 + "  ·  arrive " + var18, 14.0F, false, true, 4, null));
               var75.addView((View)var56);
               ArrayList var57 = new ArrayList();
               if (var16.getDurationS() - var2 >= 60.0) {
                  String var38 = Nav.INSTANCE.durationText(var16.getDurationS() - var2);
                  var57.add(TuplesKt.to(var38 + " slower", Color.argb(190, 255, 255, 255)));
               } else if (var13.size() > 1) {
                  var57.add(TuplesKt.to("Fastest", Color.rgb(108, 214, 140)));
               }

               double var4 = var16.getTypicalS();
               boolean var23;
               if (!Double.isInfinite(var4) && !Double.isNaN(var4)) {
                  var23 = 1;
               } else {
                  var23 = 0;
               }

               if (var23) {
                  var4 = var16.getDurationS() - var16.getTypicalS();
                  String var40;
                  if (var4 >= 120.0) {
                     String var39 = Nav.INSTANCE.durationText(var4);
                     var40 = "heavy traffic, +" + var39;
                  } else {
                     var40 = "usual traffic";
                  }

                  if (var4 >= 300.0) {
                     var23 = Color.rgb(255, 122, 112);
                  } else if (var4 >= 120.0) {
                     var23 = Color.rgb(255, 176, 64);
                  } else {
                     var23 = Color.rgb(108, 214, 140);
                  }

                  var57.add(TuplesKt.to(var40, var23));
               }

               if (var16.getClasses().contains("toll")) {
                  var57.add(TuplesKt.to("Tolls", Color.rgb(255, 200, 87)));
               }

               if (var16.getClasses().contains("motorway")) {
                  var57.add(TuplesKt.to("Highways", Color.argb(190, 255, 255, 255)));
               }

               if (var16.getClasses().contains("ferry")) {
                  var57.add(TuplesKt.to("Ferry", Color.rgb(127, 196, 255)));
               }

               if (var16.getClasses().contains("restricted")) {
                  var57.add(TuplesKt.to("Restricted roads", Color.rgb(255, 176, 64)));
               }

               if (var16.getIncidents() > 0) {
                  var23 = var16.getIncidents();
                  String var41;
                  if (var16.getIncidents() > 1) {
                     var41 = "s";
                  } else {
                     var41 = "";
                  }

                  var57.add(TuplesKt.to(var23 + " incident" + var41, Color.rgb(255, 122, 112)));
               }

               if (var16.getClosed()) {
                  var57.add(TuplesKt.to("Closure on route", Color.rgb(255, 85, 85)));
               }

               if (!Nav.INSTANCE.getInPreview() && var16.getIndex() == Nav.INSTANCE.getSelected()) {
                  var57.add(0, TuplesKt.to("Driving now", Color.rgb(127, 196, 255)));
               }

               LinearLayout var20 = new LinearLayout(this.getContext());
               var20.setOrientation(0);
               TextView var77 = new TextView(this.getContext());
               boolean var9 = false;
               var77.setTextSize(13.0F);
               var77.setMaxLines(2);
               SpannableStringBuilder var19 = new SpannableStringBuilder();
               var8 = false;
               var35 = var57.iterator();
               var23 = 0;

               while (true) {
                  var7 = var23;
                  if (!var35.hasNext()) {
                     var77.setText((CharSequence)var19);
                     var20.addView((View)var77);
                     var75.addView((View)var20);
                     boolean var27;
                     if (var16.getVia().length() > 0) {
                        var27 = true;
                     } else {
                        var27 = false;
                     }

                     if (var27) {
                        String var43 = var16.getVia();
                        TextView var44 = label$default(this, "via " + var43, 13.0F, false, true, 4, null);
                        var44.setMaxLines(1);
                        var44.setEllipsize(TruncateAt.END);
                        var75.addView((View)var44);
                     }

                     var15.addView((View)var75, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
                     var15.setOnClickListener(new MapPane$$ExternalSyntheticLambda46(this, var16));
                     View var58 = (View)var15;
                     android.widget.LinearLayout.LayoutParams var45 = new android.widget.LinearLayout.LayoutParams(-1, -2);
                     var45.bottomMargin = this.dpi(8);
                     Unit var72 = Unit.INSTANCE;
                     var1.addView(var58, (LayoutParams)var45);
                     break;
                  }

                  var23++;
                  Pair var21 = (Pair)var35.next();
                  if (var7 > 0) {
                     var19.append("  ·  ");
                  }

                  var7 = var19.length();
                  var19.append((CharSequence)var21.getFirst());
                  var19.setSpan(new ForegroundColorSpan(((Number)var21.getSecond()).intValue()), var7, var19.length(), 0);
               }
            }

            JSONObject var46 = Nav.INSTANCE.getOptions();
            Pair var74 = TuplesKt.to("avoidTolls", "tolls");
            Pair var76 = TuplesKt.to("avoidHighways", "highways");
            Pair var59 = TuplesKt.to("avoidFerries", "ferries");
            Pair var73 = TuplesKt.to("avoidUnpaved", "unpaved roads");
            Pair var65 = TuplesKt.to("avoidCashTolls", "cash-only tolls");
            Pair var70 = TuplesKt.to("avoidTunnels", "tunnels");
            Iterable var66 = CollectionsKt.listOf(new Pair[]{var74, var76, var59, var73, var65, var70, TuplesKt.to("avoidBorders", "borders")});
            var var60 = new ArrayList();

            for (Object var71 : var66) {
               if (var46.optBoolean((String)((Pair)var71).getFirst())) {
                  var60.add(var71);
               }
            }

            List var61 = var60;
            Iterable var68 = var61;
            var var62 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var68, 10));
            Iterator var69 = var68.iterator();

            while (var69.hasNext()) {
               var62.add((String)((Pair)var69.next()).getSecond());
            }

            List var63 = var62;
            int var28 = var46.optInt("carpool", 0);
            String var47;
            if (var63.isEmpty()) {
               var47 = "Avoiding nothing";
            } else {
               String var48 = CollectionsKt.joinToString$default(var63, ", ", null, null, 0, null, null, 62, null);
               var47 = "Avoiding " + var48;
            }

            if (var28 > 0) {
               var12 = " · carpool lanes (" + var28 + "+)";
            } else {
               var12 = " · no carpool lanes";
            }

            String var49 = var47 + var12;
            var1.addView((View)label$default(this, var49, 13.0F, false, true, 4, null));
         }
      }
   }

   private static final void fillRoutes$lambda$100(MapPane var0, Nav.Route var1, View var2) {
      var0.lookAt(var1.getIndex());
   }

   private final void fillSheet() {
      this.sheetVersion = Nav.INSTANCE.getVersion();
      View var4 = this.sheet.getChildAt(0);
      LinearLayout var13;
      if (var4 instanceof LinearLayout) {
         var13 = (LinearLayout)var4;
      } else {
         var13 = null;
      }

      int var1;
      label160: {
         if (var13 != null) {
            var4 = var13.getChildAt(1);
            ScrollView var15;
            if (var4 instanceof ScrollView) {
               var15 = (ScrollView)var4;
            } else {
               var15 = null;
            }

            Integer var16;
            if (var15 != null) {
               var16 = var15.getScrollY();
            } else {
               var16 = null;
            }

            if (var16 != null) {
               var1 = var16;
               break label160;
            }
         }

         var1 = 0;
      }

      this.sheet.removeAllViews();
      LinearLayout var6 = new LinearLayout(this.getContext());
      var6.setOrientation(1);
      var6.setBackground((Drawable)card$default(this, -233169629, 20.0F, 0, true, 4, null));
      var6.setPadding(this.dpi(14), this.dpi(10), this.dpi(14), this.dpi(12));
      LinearLayout var7 = new LinearLayout(this.getContext());
      var7.setOrientation(0);
      var7.setGravity(16);
      boolean var3 = Nav.INSTANCE.getInPreview();
      if (Intrinsics.areEqual(this.sheetKind, "look")) {
         TextView var17 = pill$default(this, "‹", false, 2, null);
         var17.setTextSize(20.0F);
         var17.setOnClickListener(new MapPane$$ExternalSyntheticLambda26(this));
         var4 = (View)var17;
         android.widget.LinearLayout.LayoutParams var8 = new android.widget.LinearLayout.LayoutParams(this.dpi(48), this.dpi(40));
         var8.setMarginEnd(this.dpi(10));
         Unit var5 = Unit.INSTANCE;
         var7.addView(var4, (LayoutParams)var8);
      }

      label153: {
         String var19 = this.sheetKind;
         switch (var19.hashCode()) {
            case -925132982:
               if (var19.equals("routes")) {
                  if (var3) {
                     JSONObject var22 = Nav.INSTANCE.getPreviewing();
                     String var23;
                     if (var22 != null) {
                        var23 = var22.optString("name");
                     } else {
                        var23 = null;
                     }

                     String var36 = var23;
                     if (var23 == null) {
                        var36 = "";
                     }

                     var4 = var36;
                     boolean var2;
                     if (var4.length() == 0) {
                        var2 = true;
                     } else {
                        var2 = false;
                     }

                     if (var2) {
                        var4 = "there";
                     }

                     var20 = "Routes to " + var4;
                  } else {
                     var20 = "Routes";
                  }
                  break label153;
               }
               break;
            case 3327647:
               if (var19.equals("look")) {
                  Nav.Route var34 = Nav.INSTANCE.getShownRoute();
                  if (var34 != null) {
                     String var21 = Nav.INSTANCE.durationText(var34.getDurationS());
                     String var35 = Nav.distanceText$default(Nav.INSTANCE, var34.getDistanceM(), this.getT().isMetric(), false, 4, null);
                     var20 = var21 + " · " + var35;
                     if (var20 != null) {
                        break label153;
                     }
                  }

                  var20 = "Directions";
                  break label153;
               }
               break;
            case 109761319:
               if (var19.equals("steps")) {
                  var20 = "Directions";
                  break label153;
               }
         }

         var20 = "Route options";
      }

      TextView var25 = label$default(this, var20, 20.0F, true, false, 8, null);
      var25.setMaxLines(1);
      var25.setEllipsize(TruncateAt.END);
      var7.addView((View)var25, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
      if (Intrinsics.areEqual(this.sheetKind, "routes")) {
         TextView var26 = pill$default(this, "Avoid…", false, 2, null);
         var26.setOnClickListener(new MapPane$$ExternalSyntheticLambda27(this));
         var4 = (View)var26;
         android.widget.LinearLayout.LayoutParams var44 = new android.widget.LinearLayout.LayoutParams(-2, this.dpi(40));
         var44.setMarginEnd(this.dpi(8));
         Unit var37 = Unit.INSTANCE;
         var7.addView(var4, (LayoutParams)var44);
      }

      if (var3 && (Intrinsics.areEqual(this.sheetKind, "routes") || Intrinsics.areEqual(this.sheetKind, "look"))) {
         TextView var28 = pill$default(this, "Cancel", false, 2, null);
         var28.setOnClickListener(new MapPane$$ExternalSyntheticLambda28(this));
         var4 = (View)var28;
         android.widget.LinearLayout.LayoutParams var38 = new android.widget.LinearLayout.LayoutParams(-2, this.dpi(40));
         var38.setMarginEnd(this.dpi(8));
         Unit var45 = Unit.INSTANCE;
         var7.addView(var4, (LayoutParams)var38);
      }

      boolean var10;
      if (Intrinsics.areEqual(this.sheetKind, "look") && !var3 && Nav.INSTANCE.getLook() >= 0 && Nav.INSTANCE.getLook() != Nav.INSTANCE.getSelected()) {
         var10 = 1;
      } else {
         var10 = 0;
      }

      Pair var30;
      if (!var3 || !Intrinsics.areEqual(this.sheetKind, "routes") && !Intrinsics.areEqual(this.sheetKind, "look")) {
         if (var10) {
            var30 = TuplesKt.to("Use this route", new MapPane$$ExternalSyntheticLambda30(this));
         } else if (var3 && Intrinsics.areEqual(this.sheetKind, "options")) {
            var30 = TuplesKt.to("Done", new MapPane$$ExternalSyntheticLambda31(this));
         } else {
            var30 = TuplesKt.to("Done", new MapPane$$ExternalSyntheticLambda32(this));
         }
      } else {
         var30 = TuplesKt.to("Go", new MapPane$$ExternalSyntheticLambda29(this));
      }

      String var39 = (String)var30.component1();
      Function0 var31 = (Function0)var30.component2();
      TextView var40 = this.pill(var39, true);
      var40.setOnClickListener(new MapPane$$ExternalSyntheticLambda34(var31));
      var4 = (View)var40;
      if (var10) {
         var10 = -2;
      } else {
         var10 = this.dpi(84);
      }

      label112: {
         var7.addView(var4, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(var10, this.dpi(40))));
         var6.addView((View)var7);
         var41 = new ScrollView(this.getContext());
         LinearLayout var33 = new LinearLayout(this.getContext());
         var33.setOrientation(1);
         var41.addView((View)var33);
         View var42 = (View)var41;
         android.widget.LinearLayout.LayoutParams var9 = new android.widget.LinearLayout.LayoutParams(-1, -2);
         var9.topMargin = this.dpi(8);
         Unit var46 = Unit.INSTANCE;
         var6.addView(var42, (LayoutParams)var9);
         String var43 = this.sheetKind;
         switch (var43.hashCode()) {
            case -925132982:
               if (var43.equals("routes")) {
                  this.fillRoutes(var33);
                  break label112;
               }
               break;
            case 3327647:
               if (var43.equals("look")) {
                  this.fillLook(var33);
                  break label112;
               }
               break;
            case 109761319:
               if (var43.equals("steps")) {
                  this.fillSteps(var33);
                  break label112;
               }
         }

         this.fillOptions(var33);
      }

      var10 = RangesKt.coerceAtLeast(MathKt.roundToInt(this.getHeight() * 0.62F), this.dpi(260));
      this.sheet.addView((View)var6, (LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -2)));
      var6.measure(MeasureSpec.makeMeasureSpec(RangesKt.coerceAtLeast(this.getWidth(), 1), 1073741824), MeasureSpec.makeMeasureSpec(0, 0));
      if (var6.getMeasuredHeight() > var10) {
         var6.setLayoutParams((LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, var10)));
      }

      var41.post(new MapPane$$ExternalSyntheticLambda35(var41, this, var1));
      this.lookFresh = false;
   }

   private static final void fillSheet$lambda$68$lambda$67(MapPane var0, View var1) {
      var0.openSheet("routes");
   }

   private static final void fillSheet$lambda$74$lambda$73(MapPane var0, View var1) {
      var0.openSheet("options");
   }

   private static final void fillSheet$lambda$77$lambda$76(MapPane var0, View var1) {
      var0.closeSheet();
   }

   private static final Unit fillSheet$lambda$79(MapPane var0) {
      var0.goWithLooked();
      return Unit.INSTANCE;
   }

   private static final Unit fillSheet$lambda$80(MapPane var0) {
      var0.goWithLooked();
      return Unit.INSTANCE;
   }

   private static final Unit fillSheet$lambda$81(MapPane var0) {
      var0.openSheet("routes");
      return Unit.INSTANCE;
   }

   private static final Unit fillSheet$lambda$82(MapPane var0) {
      var0.closeSheet();
      return Unit.INSTANCE;
   }

   private static final void fillSheet$lambda$84$lambda$83(Function0 var0, View var1) {
      var0.invoke();
   }

   private static final void fillSheet$lambda$87(ScrollView var0, MapPane var1, int var2) {
      if (Intrinsics.areEqual(var1.sheetKind, "steps") && var2 == 0) {
         var2 = var1.stepScroll;
      } else if (Intrinsics.areEqual(var1.sheetKind, "look") && var1.lookFresh) {
         var2 = 0;
      }

      var0.scrollTo(0, var2);
   }

   private final void fillSteps(LinearLayout var1) {
      Nav.Line var2 = Nav.INSTANCE.getLine();
      if (var2 != null && !var2.getSteps().isEmpty()) {
         this.fillSteps(var1, var2, true);
      } else {
         var1.addView((View)label$default(this, "No directions yet.", 15.0F, false, false, 12, null));
      }
   }

   private final void fillSteps(LinearLayout var1, Nav.Line var2, boolean var3) {
      if (var2.getSteps().isEmpty()) {
         var1.addView((View)label$default(this, "No directions yet.", 15.0F, false, false, 12, null));
      } else {
         int var7;
         if (var3) {
            var7 = Nav.INSTANCE.currentStep(var2);
         } else {
            var7 = -1;
         }

         double var4;
         if (var3) {
            var4 = Nav.INSTANCE.getAlong();
         } else {
            var4 = Double.NaN;
         }

         boolean var9 = this.getT().isMetric();
         int var6 = 0;
         this.stepScroll = 0;
         Iterator var13 = var2.getSteps().iterator();

         for (int var8 = 0; var13.hasNext(); var8++) {
            Nav.Step var15 = (Nav.Step)var13.next();
            if (var7 < 0 || var8 >= var7) {
               LinearLayout var14 = new LinearLayout(this.getContext());
               var14.setOrientation(var6);
               var14.setGravity(16);
               var14.setPadding(this.dpi(10), this.dpi(10), this.dpi(10), this.dpi(10));
               if (var8 == var7 + 1 || var7 < 0 && var8 == 0) {
                  var6 = 858751213;
               } else {
                  var6 = 285212671;
               }

               var14.setBackground((Drawable)card$default(this, var6, 12.0F, 0, false, 12, null));
               ImageView var17 = new ImageView(this.getContext());
               NavIcons var10 = NavIcons.INSTANCE;
               Context var11 = var17.getContext();
               Intrinsics.checkNotNullExpressionValue(var11, "getContext(...)");
               Bitmap var33 = var10.icon(var11, var15.getType(), var15.getModifier());
               if (var33 != null) {
                  var17.setImageBitmap(var33);
               }

               View var34 = (View)var17;
               android.widget.LinearLayout.LayoutParams var18 = new android.widget.LinearLayout.LayoutParams(this.dpi(36), this.dpi(36));
               var18.setMarginEnd(this.dpi(12));
               Unit var42 = Unit.INSTANCE;
               var14.addView(var34, (LayoutParams)var18);
               LinearLayout var16 = new LinearLayout(this.getContext());
               var16.setOrientation(1);
               var var19 = var15.getText();
               boolean var29;
               if (var19.length() == 0) {
                  var29 = true;
               } else {
                  var29 = false;
               }

               if (var29) {
                  var19 = var15.getRoad();
               }

               String var20 = var19;
               if (var8 == var7 + 1) {
                  var3 = true;
               } else {
                  var3 = false;
               }

               var16.addView((View)label$default(this, var20, 16.0F, var3, false, 8, null));
               if (!Double.isInfinite(var4) && !Double.isNaN(var4)) {
                  var29 = true;
               } else {
                  var29 = false;
               }

               String var12;
               label144: {
                  var12 = "";
                  String var21;
                  String var35;
                  StringBuilder var43;
                  if (var29 && var15.getStartM() > var4) {
                     var35 = Nav.distanceText$default(Nav.INSTANCE, var15.getStartM() - var4, var9, false, 4, null);
                     var43 = new StringBuilder();
                     var21 = "in ";
                  } else {
                     if (!(var15.getDistanceM() > 0.0)) {
                        var22 = "";
                        break label144;
                     }

                     var35 = Nav.distanceText$default(Nav.INSTANCE, var15.getDistanceM(), var9, false, 4, null);
                     var43 = new StringBuilder();
                     var21 = "then ";
                  }

                  var22 = var43.append(var21).append(var35).toString();
               }

               String var44 = var15.getRef();
               String var36;
               if (StringsKt.contains$default(var15.getText(), var15.getRoad(), false, 2, null)) {
                  var36 = var12;
               } else {
                  var36 = var15.getRoad();
               }

               Iterable var45 = CollectionsKt.listOf(new String[]{var44, var36});
               var var37 = new ArrayList();

               for (Object var49 : var45) {
                  if (((String)var49).length() > 0) {
                     var29 = true;
                  } else {
                     var29 = false;
                  }

                  if (var29) {
                     var37.add(var49);
                  }
               }

               List var38 = var37;
               Iterable var39 = CollectionsKt.listOf(new String[]{var22, CollectionsKt.joinToString$default(var38, " · ", null, null, 0, null, null, 62, null)});
               var var23 = new ArrayList();

               for (Object var47 : var39) {
                  if (((String)var47).length() > 0) {
                     var29 = true;
                  } else {
                     var29 = false;
                  }

                  if (var29) {
                     var23.add(var47);
                  }
               }

               List var24 = var23;
               String var25 = CollectionsKt.joinToString$default(var24, "   ", null, null, 0, null, null, 62, null);
               var6 = 0;
               var16.addView((View)label$default(this, var25, 13.0F, false, true, 4, null));
               var14.addView((View)var16, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
               var14.setOnClickListener(new MapPane$$ExternalSyntheticLambda22(this, var15));
               View var41 = (View)var14;
               android.widget.LinearLayout.LayoutParams var48 = new android.widget.LinearLayout.LayoutParams(-1, -2);
               var48.bottomMargin = this.dpi(6);
               Unit var26 = Unit.INSTANCE;
               var1.addView(var41, (LayoutParams)var48);
            }
         }
      }
   }

   private static final void fillSteps$lambda$114(MapPane var0, Nav.Step var1, View var2) {
      var0.map.preview(var1.getLat(), var1.getLon());
      var0.showInfo(var1.getText());
   }

   private final UiSettings getS() {
      return this.host.getSettings();
   }

   private final Telemetry getT() {
      return this.host.getTelemetry();
   }

   private final void goWithLooked() {
      if (Nav.INSTANCE.getInPreview()) {
         if (Nav.INSTANCE.getPreview() == null) {
            var var2 = Nav.INSTANCE.getPreviewError();
            boolean var3;
            if (var2.length() == 0) {
               var3 = true;
            } else {
               var3 = false;
            }

            if (var3) {
               var2 = "Still finding routes…";
            }

            this.toast(var2);
         } else {
            Nav.INSTANCE.go(this.host.getData(), new MapPane$$ExternalSyntheticLambda23(this));
         }
      } else {
         int var1 = Nav.INSTANCE.getLook();
         Nav.INSTANCE.setLook(-1);
         if (var1 >= 0 && var1 != Nav.INSTANCE.getSelected()) {
            Nav.INSTANCE.choose(this.host.getData(), var1, new MapPane$$ExternalSyntheticLambda24(this));
         }

         this.closeSheet();
      }
   }

   private static final Unit goWithLooked$lambda$33(MapPane var0, String var1) {
      if (var1 != null) {
         var0.toast(var1);
      } else {
         var0.sheetKind = "";
         var0.sheet.setVisibility(8);
         var0.sheet.removeAllViews();
         var0.map.follow();
         var0.refreshBars();
      }

      return Unit.INSTANCE;
   }

   private static final Unit goWithLooked$lambda$34(MapPane var0, String var1) {
      if (var1 != null) {
         var0.toast(var1);
      }

      return Unit.INSTANCE;
   }

   private static final void hideInfo$lambda$63(MapPane var0) {
      var0.info.setVisibility(8);
   }

   private final TextView label(String var1, float var2, boolean var3, boolean var4) {
      TextView var6 = new TextView(this.getContext());
      var6.setText(var1);
      var6.setTextSize(var2);
      int var5;
      if (var4) {
         var5 = Color.argb(170, 255, 255, 255);
      } else {
         var5 = -1;
      }

      var6.setTextColor(var5);
      if (var3) {
         var6.setTypeface(Typeface.DEFAULT_BOLD);
      }

      return var6;
   }

   // $VF: synthetic method
   static TextView label$default(MapPane var0, String var1, float var2, boolean var3, boolean var4, int var5, Object var6) {
      if ((var5 & 4) != 0) {
         var3 = false;
      }

      if ((var5 & 8) != 0) {
         var4 = false;
      }

      return var0.label(var1, var2, var3, var4);
   }

   private final double[] lastKnown() {
      String var1 = Telemetry.pStr$default(this.getT(), "LastGPSPosition", null, 2, null);
      if (!Intrinsics.areEqual(var1, this.lastKnownRaw)) {
         this.lastKnownRaw = var1;

         try {
            JSONObject var2 = new JSONObject(var1);
            var4 = new double[]{var2.optDouble("latitude", Double.NaN), var2.optDouble("longitude", Double.NaN)};
         } catch (Exception var3) {
            var4 = new double[]{Double.NaN, Double.NaN};
         }

         this.lastKnownLL = var4;
      }

      return this.lastKnownLL;
   }

   private final void lookAt(int var1) {
      Nav var2 = Nav.INSTANCE;
      if (!Nav.INSTANCE.getInPreview() && var1 == Nav.INSTANCE.getSelected()) {
         var1 = -1;
      }

      var2.setLook(var1);
      this.map.linesChanged();
      this.map.invalidate();
      this.openSheet("look");
   }

   private final void onNav() {
      String var9;
      String var14;
      label96: {
         JSONObject var8 = Nav.INSTANCE.getDestination();
         var9 = "";
         if (var8 != null) {
            String var10 = var8.optString("name");
            double var3 = var8.optDouble("latitude");
            double var1 = var8.optDouble("longitude");
            var10 = var10 + "|" + var3 + "|" + var1;
            var14 = var10;
            if (var10 != null) {
               break label96;
            }
         }

         var14 = "";
      }

      boolean var7 = Intrinsics.areEqual(var14, this.lastDest);
      boolean var6 = true;
      if (!var7) {
         this.lastDest = var14;
         this.dismissedFaster = "";
         boolean var5;
         if (var14.length() == 0) {
            var5 = true;
         } else {
            var5 = false;
         }

         if (var5) {
            if (Intrinsics.areEqual(this.sheetKind, "routes") || Intrinsics.areEqual(this.sheetKind, "steps")) {
               this.closeSheet();
            }

            this.map.follow();
         }
      }

      if (this.showRoutesWhenReady && !Nav.INSTANCE.getRoutes().isEmpty()) {
         var var15 = Nav.INSTANCE.getLines();
         boolean var12;
         if (var15 instanceof Collection && ((Collection)var15).isEmpty()) {
            var12 = false;
         } else {
            label102: {
               Iterator var16 = var15.iterator();

               while (var16.hasNext()) {
                  if ((Nav.Line)var16.next() != null) {
                     var12 = true;
                  } else {
                     var12 = false;
                  }

                  if (var12) {
                     var12 = true;
                     break label102;
                  }
               }

               var12 = false;
            }
         }

         if (var12) {
            this.showRoutesWhenReady = false;
            this.openRoutes();
         }
      }

      Nav.Preview var19 = Nav.INSTANCE.getPreview();
      var14 = var9;
      if (var19 != null) {
         var14 = var19.getId();
         if (var14 == null) {
            var14 = var9;
         }
      }

      if (Nav.INSTANCE.getInPreview() && !Intrinsics.areEqual(var14, this.previewShown)) {
         this.previewShown = var14;
         if (!Intrinsics.areEqual(this.sheetKind, "routes") && !Intrinsics.areEqual(this.sheetKind, "look") && !Intrinsics.areEqual(this.sheetKind, "options")) {
            this.openSheet("routes");
         }

         boolean var13;
         if (var14.length() > 0) {
            var13 = var6;
         } else {
            var13 = false;
         }

         if (var13) {
            this.post(new MapPane$$ExternalSyntheticLambda7(this));
         }
      }

      this.map.linesChanged();
      if (this.sheet.getVisibility() == 0 && this.sheetVersion != Nav.INSTANCE.getVersion()) {
         this.fillSheet();
      }

      this.refreshButtons();
      this.refreshBars();
      this.map.invalidate();
   }

   private static final void onNav$lambda$37(MapPane var0) {
      var0.map.linesChanged();
      var0.map.overview();
   }

   private final void openSheet(String var1) {
      boolean var2;
      if (Intrinsics.areEqual(var1, "look") && !Intrinsics.areEqual(this.sheetKind, "look")) {
         var2 = true;
      } else {
         var2 = false;
      }

      this.lookFresh = var2;
      this.sheetKind = var1;
      this.sheet.setVisibility(0);
      this.fillSheet();
      this.refreshBars();
   }

   private final TextView pill(String var1, boolean var2) {
      TextView var4 = new TextView(this.getContext());
      var4.setText(var1);
      var4.setTextSize(15.0F);
      var4.setTypeface(Typeface.DEFAULT_BOLD);
      var4.setGravity(17);
      var4.setTextColor(-1);
      var4.setMaxLines(1);
      int var3;
      if (var2) {
         var3 = -13664019;
      } else {
         var3 = 654311423;
      }

      var4.setBackground((Drawable)card$default(this, var3, 12.0F, 0, false, 12, null));
      var4.setPadding(this.dpi(10), 0, this.dpi(10), 0);
      return var4;
   }

   // $VF: synthetic method
   static TextView pill$default(MapPane var0, String var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return var0.pill(var1, var2);
   }

   private final void prefetchAhead() {
      Nav.Line var18 = Nav.INSTANCE.getLine();
      if (var18 != null) {
         Double var16 = Nav.INSTANCE.getAlong();
         double var1 = var16.doubleValue();
         boolean var9;
         if (!Double.isInfinite(var1) && !Double.isNaN(var1)) {
            var9 = 1;
         } else {
            var9 = 0;
         }

         if (!var9) {
            var16 = null;
         }

         if (var16 != null) {
            var1 = var16;
         } else {
            var1 = 0.0;
         }

         LinkedHashSet var19 = new LinkedHashSet();
         int[] var17 = new int[]{16, 15, 13};
         int var10 = 0;

         for (Nav.Line var24 = var18; var10 < 3; var10++) {
            int var14 = var17[var10];
            int var15 = 1 << var14;
            double var3 = var1;
            double var5 = Math.min(15000.0 + var1, var24.getLength());

            while (var3 <= var5) {
               int var11 = var24.indexAt(var3);
               double var7 = var24.getMx()[var11];
               var9 = (int)(var15 * var7);
               int var12 = (int)(var24.getMy()[var11] * var15);

               for (int var23 = -1; var23 < 2; var23++) {
                  for (int var13 = -1; var13 < 2; var13++) {
                     var19.add(new Triple<>(var14, ((var9 + var23) % var15 + var15) % var15, RangesKt.coerceIn(var12 + var13, 0, var15 - 1)));
                  }
               }

               if (var14 >= 16) {
                  var7 = 200.0;
               } else {
                  var7 = 400.0;
               }

               var3 += var7;
            }
         }

         this.tiles.prefetch(this.map.style(), CollectionsKt.take(var19, 450));
      }
   }

   private final void refreshBars() {
      boolean var7 = this.getT().isMetric();
      boolean var8 = Nav.INSTANCE.getLoaded();
      String var11 = "";
      String var9;
      if (!var8) {
         if (this.host.getData().getConnected()) {
            var9 = "";
         } else {
            var9 = "Not connected to the comma";
         }
      } else if (!Nav.INSTANCE.getSupported()) {
         var9 = "The comma needs the StarView navigation update (patch 9) for the map.";
      } else if (!Nav.INSTANCE.getHasKey()) {
         var9 = "Add your Mapbox key on the comma first:\nGalaxy › Navigation › App Keys.";
      } else if (!Nav.INSTANCE.getEnabled() && Nav.INSTANCE.getActive()) {
         var9 = "Navigation is off on the comma (Galaxy › Navigation).";
      } else {
         var9 = "";
      }

      this.note.setText(var9);
      TextView var10 = this.note;
      boolean var5;
      if (var9.length() == 0) {
         var5 = 1;
      } else {
         var5 = 0;
      }

      if (var5) {
         var5 = (byte)8;
      } else {
         var5 = (byte)0;
      }

      Object var12;
      var10.setVisibility(var5);
      var8 = Nav.INSTANCE.getActive();
      var12 = null;
      label214:
      if (var8) {
         double var1;
         label212: {
            var30 = Nav.INSTANCE.remaining();
            String var49 = Nav.INSTANCE.getStatus();
            var5 = var49.hashCode();
            var1 = 0.0;
            switch (var5) {
               case -2094020847:
                  if (var49.equals("waitingForLocation")) {
                     break label212;
                  }
                  break;
               case -734206867:
                  if (var49.equals("arrived")) {
                     this.etaBig.setText("Arrived");
                     TextView var13 = this.etaSmall;
                     JSONObject var31 = Nav.INSTANCE.getDestination();
                     if (var31 != null) {
                        var9 = var31.optString("name");
                     } else {
                        var9 = null;
                     }

                     String var50 = var9;
                     if (var9 == null) {
                        var50 = "";
                     }

                     var13.setText(var50);
                     break label214;
                  }
                  break;
               case -72763833:
                  if (var49.equals("routeUnavailable")) {
                     this.etaBig.setText("No route");
                     this.etaSmall.setText("The comma tries again in 30 s");
                     break label214;
                  }
                  break;
               case 1385652422:
                  if (var49.equals("routing")) {
                     break label212;
                  }
            }

            if (var30 != null) {
               this.etaBig.setText(Nav.INSTANCE.etaText(((Number)var30.getFirst()).doubleValue()));
               Nav.Route var51 = Nav.INSTANCE.getRoute();
               double var3 = var1;
               if (var51 != null) {
                  var3 = var51.getTypicalS();
                  boolean var19;
                  if (!Double.isInfinite(var3) && !Double.isNaN(var3)) {
                     var19 = true;
                  } else {
                     var19 = false;
                  }

                  var3 = var1;
                  if (var19) {
                     var3 = var51.getDurationS() - var51.getTypicalS();
                  }
               }

               TextView var14 = this.etaSmall;
               String var64 = Nav.INSTANCE.durationText(((Number)var30.getFirst()).doubleValue());
               String var15 = Nav.distanceText$default(Nav.INSTANCE, ((Number)var30.getSecond()).doubleValue(), var7, false, 4, null);
               if (var3 >= 120.0) {
                  var9 = Nav.INSTANCE.durationText(var3);
                  var9 = "  ·  +" + var9 + " traffic";
               } else {
                  var9 = "";
               }

               String var52;
               if (Nav.INSTANCE.getOffRoute()) {
                  var52 = "  ·  off the route";
               } else {
                  var52 = "";
               }

               var14.setText(var64 + " · " + var15 + var9 + var52);
               TextView var35 = this.etaSmall;
               if (var3 >= 300.0) {
                  var5 = Color.rgb(255, 138, 128);
               } else {
                  var5 = Color.argb(200, 255, 255, 255);
               }

               var35.setTextColor(var5);
            } else {
               this.etaBig.setText("—");
               TextView var65 = this.etaSmall;
               JSONObject var36 = Nav.INSTANCE.getDestination();
               if (var36 != null) {
                  var9 = var36.optString("name");
               } else {
                  var9 = null;
               }

               String var53 = var9;
               if (var9 == null) {
                  var53 = "";
               }

               var65.setText(var53);
            }
            break label214;
         }

         var10 = this.etaBig;
         if (Nav.INSTANCE.getRoutes().isEmpty()) {
            var9 = "Finding routes…";
         } else {
            Nav var66 = Nav.INSTANCE;
            if (var30 != null) {
               var1 = ((Number)var30.getFirst()).doubleValue();
            }

            var9 = var66.etaText(var1);
         }

         CharSequence var39 = var9;
         var10.setText(var39);
         TextView var67 = this.etaSmall;
         JSONObject var40 = Nav.INSTANCE.getDestination();
         if (var40 != null) {
            var9 = var40.optString("name");
         } else {
            var9 = null;
         }

         String var55 = var9;
         if (var9 == null) {
            var55 = "";
         }

         var67.setText(var55);
      } else {
         this.etaBig.setText("");
         var10 = this.etaSmall;
         CharSequence var42;
         if (Nav.INSTANCE.getLoaded() && Nav.INSTANCE.getSupported() && Nav.INSTANCE.getHasKey()) {
            var42 = "No destination";
         } else {
            var42 = "";
         }

         var10.setText(var42);
      }

      int var6 = Nav.INSTANCE.faster();
      Iterator var57 = Nav.INSTANCE.getRoutes().iterator();

      do {
         if (!var57.hasNext()) {
            var9 = (String)var12;
            break;
         }

         var9 = (String)var57.next();
         if (((Nav.Route)var9).getIndex() == var6) {
            var21 = true;
         } else {
            var21 = false;
         }
      } while (!var21);

      Nav.Route var58 = (Nav.Route)var9;
      var12 = Nav.INSTANCE.getRoute();
      var9 = var11;
      if (var58 != null) {
         var9 = var11;
         if (var12 != null) {
            var5 = var58.getIndex();
            var6 = MathKt.roundToInt(var58.getDurationS() / 60);
            var9 = var5 + "|" + var6;
         }
      }

      if (var58 != null
         && var12 != null
         && !Intrinsics.areEqual(var9, this.dismissedFaster)
         && Intrinsics.areEqual(Nav.INSTANCE.getStatus(), "guiding")
         && !Intrinsics.areEqual(this.sheetKind, "routes")) {
         TextView var61 = this.faster;
         var12 = Nav.INSTANCE.durationText(((Nav.Route)var12).getDurationS() - var58.getDurationS());
         var61.setText("Faster route · saves " + var12 + "   TAKE IT    ✕");
         this.faster.setVisibility(0);
         this.faster.setOnTouchListener(new MapPane$$ExternalSyntheticLambda33(this, var9, var58));
      } else {
         this.faster.setVisibility(8);
      }

      TextView var45 = this.recenter;
      byte var23;
      if (this.map.getFollowing()) {
         var23 = 8;
      } else {
         var23 = 0;
      }

      var45.setVisibility(var23);
      LinearLayout var46 = this.bottomBar;
      if (this.sheet.getVisibility() == 0) {
         var23 = (byte)8;
      } else {
         var23 = (byte)0;
      }

      var46.setVisibility(var23);
      MapPane.Banner var47 = this.banner;
      if (!Nav.INSTANCE.getActive()
         || this.sheet.getVisibility() == 0
         || this.getT().navState() == null && !SetsKt.setOf(new String[]{"routing", "routeUnavailable", "arrived"}).contains(Nav.INSTANCE.getStatus())) {
         var23 = (byte)8;
      } else {
         var23 = (byte)0;
      }

      var47.setVisibility(var23);
      if (this.getT().navState() != null && Nav.INSTANCE.nextLanes() != null) {
         var23 = (short)150;
      } else {
         var23 = (short)92;
      }

      var23 = this.dpi(var23);
      if (this.banner.getLayoutParams().height != var23) {
         MapPane.Banner var48 = this.banner;
         LayoutParams var59 = this.banner.getLayoutParams();
         Intrinsics.checkNotNull(var59, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
         var var60 = (android.widget.FrameLayout.LayoutParams & LayoutParams)var59;
         var60.height = var23;
         var48.setLayoutParams((LayoutParams)var60);
      }
   }

   private static final boolean refreshBars$lambda$54(MapPane var0, String var1, Nav.Route var2, View var3, MotionEvent var4) {
      if (var4.getAction() == 1) {
         if (var4.getX() > var3.getWidth() - var0.dp(44.0F)) {
            var0.dismissedFaster = var1;
         } else {
            Nav.choose$default(Nav.INSTANCE, var0.host.getData(), var2.getIndex(), null, 4, null);
         }

         var0.faster.setVisibility(8);
      }

      return true;
   }

   private final void refreshButtons() {
      boolean var5 = Nav.INSTANCE.getActive();
      JSONObject var9 = Nav.INSTANCE.getDestination();
      byte var2 = 0;
      JSONObject var7;
      if (var9 != null) {
         JSONObject var8 = Favorites.INSTANCE.find(var9.optDouble("latitude"), var9.optDouble("longitude"));
         var7 = var8;
         if (var8 == null) {
            Iterator var10 = Favorites.INSTANCE.getList().iterator();

            boolean var1;
            do {
               if (!var10.hasNext()) {
                  var7 = null;
                  break;
               }

               var7 = (JSONObject)var10.next();
               var8 = var7;
               if (Intrinsics.areEqual(var8.optString("name"), var9.optString("name"))
                  && Math.abs(var8.optDouble("latitude") - var9.optDouble("latitude")) < 0.003
                  && Math.abs(var8.optDouble("longitude") - var9.optDouble("longitude")) < 0.003) {
                  var1 = true;
               } else {
                  var1 = false;
               }
            } while (!var1);

            var7 = var7;
         }
      } else {
         var7 = null;
      }

      boolean var4;
      if (var7 != null) {
         var4 = true;
      } else {
         var4 = false;
      }

      int var13 = Nav.INSTANCE.getRoutes().size();
      boolean var6 = this.getS().get("map_north_up");
      String var16 = Nav.INSTANCE.voiceMode(this.getS());
      int var3 = Favorites.INSTANCE.getVersion();
      String var26 = this.getS().getStr("fav_pins", "");
      var26 = var5 + "|" + var13 + "|" + var6 + "|" + var16 + "|" + var4 + "|" + var3 + "|" + var26;
      TextView var30 = this.northBtn;
      String var17;
      if (this.map.getRotatedByHand()) {
         var17 = "⊕";
      } else if (this.getS().get("map_north_up")) {
         var17 = "N";
      } else {
         var17 = "▲";
      }

      CharSequence var18 = var17;
      var30.setText(var18);
      TextView var31 = this.tiltBtn;
      String var19;
      if (this.getS().getFloat("map_tilt", 0.0F) > 5.0F) {
         var19 = "2D";
      } else {
         var19 = "3D";
      }

      var31.setText(var19);
      TextView var32 = this.closeBtn;
      String var20;
      if (Intrinsics.areEqual(this.getS().getStr("map_side", "left"), "right")) {
         var20 = "›";
      } else {
         var20 = "‹";
      }

      var32.setText(var20);
      TextView var33 = this.voiceBtn;
      String var21 = Nav.INSTANCE.voiceMode(this.getS());
      String var22;
      if (Intrinsics.areEqual(var21, "all")) {
         var22 = "\ud83d\udd0a";
      } else if (Intrinsics.areEqual(var21, "alerts")) {
         var22 = "⚠";
      } else {
         var22 = "\ud83d\udd07";
      }

      var33.setText(var22);
      if (!Intrinsics.areEqual(var26, this.lastButtons)) {
         this.lastButtons = var26;
         this.btnRow.removeAllViews();
         if (var5) {
            if (Nav.INSTANCE.getRoutes().size() > 1) {
               refreshButtons$b$default(this, "Routes", false, 0.0F, new MapPane$$ExternalSyntheticLambda51(this), 12, null);
            }

            refreshButtons$b$default(this, "Steps", false, 0.0F, new MapPane$$ExternalSyntheticLambda52(this), 12, null);
            refreshButtons$b$default(this, "⌕", false, 0.6F, new MapPane$$ExternalSyntheticLambda53(this), 4, null);
            String var23;
            if (var4) {
               var23 = "★";
            } else {
               var23 = "☆";
            }

            refreshButtons$b$default(this, var23, false, 0.5F, new MapPane$$ExternalSyntheticLambda54(this, var4), 4, null);
            refreshButtons$b$default(this, "⚙", false, 0.6F, new MapPane$$ExternalSyntheticLambda1(this), 4, null);
            refreshButtons$b$default(this, "End", false, 0.8F, new MapPane$$ExternalSyntheticLambda2(this), 4, null);
         } else {
            refreshButtons$b(this, "⌕  Where to?", true, 3.0F, new MapPane$$ExternalSyntheticLambda3(this));
            refreshButtons$b$default(this, "⚙", false, 0.8F, new MapPane$$ExternalSyntheticLambda4(this), 4, null);
         }

         this.pinRow.removeAllViews();
         List var24;
         if (var5) {
            var24 = CollectionsKt.emptyList();
         } else {
            var24 = Favorites.INSTANCE.pins(this.getS());
         }

         for (JSONObject var36 : var24) {
            Pair var34 = Favorites.INSTANCE.pinTitle(var36);
            String var12 = (String)var34.component1();
            String var11 = (String)var34.component2();
            LinearLayout var35 = this.pinRow;
            TextView var39 = pill$default(this, var12 + "  " + var11, false, 2, null);
            var39.setPadding(this.dpi(14), 0, this.dpi(14), 0);
            var39.setOnClickListener(new MapPane$$ExternalSyntheticLambda5(var39, this, var11, var36));
            View var40 = (View)var39;
            android.widget.LinearLayout.LayoutParams var38 = new android.widget.LinearLayout.LayoutParams(-2, this.dpi(42));
            var38.setMarginEnd(this.dpi(8));
            Unit var37 = Unit.INSTANCE;
            var35.addView(var40, (LayoutParams)var38);
         }

         HorizontalScrollView var29 = this.pinScroll;
         byte var14 = var2;
         if (var24.isEmpty()) {
            var14 = 8;
         }

         var29.setVisibility(var14);
      }
   }

   private static final void refreshButtons$b(MapPane var0, String var1, boolean var2, float var3, Function0<Unit> var4) {
      TextView var5 = var0.pill(var1, var2);
      var5.setOnClickListener(new MapPane$$ExternalSyntheticLambda48(var4));
      LinearLayout var7 = var0.btnRow;
      View var9 = (View)var5;
      android.widget.LinearLayout.LayoutParams var8 = new android.widget.LinearLayout.LayoutParams(0, -1, var3);
      var8.setMarginEnd(var0.dpi(6));
      Unit var6 = Unit.INSTANCE;
      var7.addView(var9, (LayoutParams)var8);
   }

   // $VF: synthetic method
   static void refreshButtons$b$default(MapPane var0, String var1, boolean var2, float var3, Function0 var4, int var5, Object var6) {
      if ((var5 & 4) != 0) {
         var2 = false;
      }

      if ((var5 & 8) != 0) {
         var3 = 1.0F;
      }

      refreshButtons$b(var0, var1, var2, var3, var4);
   }

   private static final void refreshButtons$b$lambda$40(Function0 var0, View var1) {
      var0.invoke();
   }

   private static final Unit refreshButtons$lambda$42(MapPane var0) {
      var0.openRoutes();
      return Unit.INSTANCE;
   }

   private static final Unit refreshButtons$lambda$43(MapPane var0) {
      var0.openSheet("steps");
      return Unit.INSTANCE;
   }

   private static final Unit refreshButtons$lambda$44(MapPane var0) {
      var0.host.openSearch();
      return Unit.INSTANCE;
   }

   private static final Unit refreshButtons$lambda$45(MapPane var0, boolean var1) {
      var0.saveDestination(var1);
      return Unit.INSTANCE;
   }

   private static final Unit refreshButtons$lambda$46(MapPane var0) {
      var0.openSheet("options");
      return Unit.INSTANCE;
   }

   private static final Unit refreshButtons$lambda$47(MapPane var0) {
      var0.confirmEnd();
      return Unit.INSTANCE;
   }

   private static final Unit refreshButtons$lambda$48(MapPane var0) {
      var0.host.openSearch();
      return Unit.INSTANCE;
   }

   private static final Unit refreshButtons$lambda$49(MapPane var0) {
      var0.openSheet("options");
      return Unit.INSTANCE;
   }

   private static final void refreshButtons$lambda$51$lambda$50(TextView var0, MapPane var1, String var2, JSONObject var3, View var4) {
      var0.setVisibility(8);
      MapPane.Host var6 = var1.host;
      String var5 = var3.optString("address");
      Intrinsics.checkNotNullExpressionValue(var5, "optString(...)");
      var6.navigateTo(var2, var5, var3.optDouble("latitude"), var3.optDouble("longitude"));
   }

   private final TextView round(String var1, int var2) {
      TextView var3 = new TextView(this.getContext());
      var3.setText(var1);
      var3.setTextSize(20.0F);
      var3.setTypeface(Typeface.DEFAULT_BOLD);
      var3.setGravity(17);
      var3.setTextColor(-1);
      GradientDrawable var4 = new GradientDrawable();
      var4.setShape(1);
      var4.setColor(-434496221);
      var4.setStroke(this.dpi(1), 1090519039);
      var3.setBackground((Drawable)var4);
      var3.setMinimumWidth(this.dpi(var2));
      var3.setMinimumHeight(this.dpi(var2));
      return var3;
   }

   private final int routeColor(boolean var1) {
      int var2;
      if (var1) {
         var2 = -13664019;
      } else {
         var2 = -7362876;
      }

      return var2;
   }

   private final double[] rtkNow() {
      return RtkPosition.INSTANCE.now(System.currentTimeMillis(), this.getT().getVEgo());
   }

   private final void saveDestination(boolean var1) {
      JSONObject var3 = Nav.INSTANCE.getDestination();
      if (var3 != null) {
         String var2 = var3.optString("name");
         String var4 = var3.optString("address");
         if (var1) {
            this.showInfo(var2 + " is saved. Hold it in the search screen for Home / Work or the main screen.");
         } else {
            new Builder(this.getContext())
               .setTitle(var2)
               .setItems(
                  new String[]{"☆  Save", "\ud83c\udfe0  Save as Home", "\ud83d\udcbc  Save as Work", "\ud83d\udccc  Save and show on the main screen"},
                  new MapPane$$ExternalSyntheticLambda50(var3, this, var2, var4)
               )
               .setNegativeButton("Cancel", null)
               .show();
         }
      }
   }

   private static final void saveDestination$lambda$60(JSONObject var0, MapPane var1, String var2, String var3, DialogInterface var4, int var5) {
      String var11;
      switch (var5) {
         case 1:
            var11 = "home";
            break;
         case 2:
            var11 = "work";
            break;
         default:
            var11 = null;
      }

      MapPane$$ExternalSyntheticLambda19 var6 = new MapPane$$ExternalSyntheticLambda19(var1, var5, var0);
      if (var0.optBoolean("temporary")) {
         Favorites var9 = Favorites.INSTANCE;
         Context var8 = var1.getContext();
         Intrinsics.checkNotNullExpressionValue(var8, "getContext(...)");
         DataClient var7 = var1.host.getData();
         Intrinsics.checkNotNull(var2);
         Intrinsics.checkNotNull(var3);
         var9.saveFound(var8, var7, var2, var3, var11, new MapPane$$ExternalSyntheticLambda20(var5, var6, var1));
      } else {
         Favorites var12 = Favorites.INSTANCE;
         DataClient var10 = var1.host.getData();
         Intrinsics.checkNotNull(var2);
         Intrinsics.checkNotNull(var3);
         var12.save(var10, var2, var3, var0.optDouble("latitude"), var0.optDouble("longitude"), var11, var6);
      }
   }

   private static final Unit saveDestination$lambda$60$lambda$57(MapPane var0, int var1, JSONObject var2, String var3) {
      if (var3 != null) {
         var0.toast(var3);
      } else {
         if (var1 == 3) {
            var2 = Favorites.INSTANCE.find(var2.optDouble("latitude"), var2.optDouble("longitude"));
            if (var2 != null) {
               Favorites.INSTANCE.setPinned(var0.getS(), var2, true);
            }
         }

         var0.showInfo("Saved");
         var0.lastButtons = "";
         var0.refreshButtons();
      }

      return Unit.INSTANCE;
   }

   private static final Unit saveDestination$lambda$60$lambda$59(int var0, Function1 var1, MapPane var2, String var3) {
      if (var3 == null && var0 == 3) {
         JSONObject var4 = CollectionsKt.lastOrNull(Favorites.INSTANCE.getList());
         if (var4 != null) {
            Favorites.INSTANCE.setPinned(var2.getS(), var4, true);
         }
      }

      var1.invoke(var3);
      return Unit.INSTANCE;
   }

   private static final void sideBtn$lambda$11$lambda$9(Function0 var0, View var1) {
      var0.invoke();
   }

   private static final Unit tick$lambda$29$lambda$28(JSONObject var0) {
      Intrinsics.checkNotNullParameter(var0, "it");
      return Unit.INSTANCE;
   }

   private static final Unit tiles$lambda$1(MapPane var0) {
      var0.post(new MapPane$$ExternalSyntheticLambda47(var0));
      return Unit.INSTANCE;
   }

   private static final void tiles$lambda$1$lambda$0(MapPane var0) {
      var0.map.invalidate();
   }

   private final void toast(String var1) {
      Toast.makeText(this.getContext(), var1, 1).show();
   }

   private final View toggleRow(String var1, boolean var2, Function1<? super Boolean, Unit> var3) {
      LinearLayout var4 = new LinearLayout(this.getContext());
      var4.setOrientation(0);
      var4.setGravity(16);
      var4.setPadding(0, this.dpi(6), 0, this.dpi(6));
      var4.addView((View)label$default(this, var1, 15.0F, false, false, 12, null), (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
      Switch var5 = new Switch(this.getContext());
      var5.setChecked(var2);
      var5.setOnCheckedChangeListener(new MapPane$$ExternalSyntheticLambda0(var3));
      var4.addView((View)var5);
      var4.setOnClickListener(new MapPane$$ExternalSyntheticLambda11(var5));
      return (View)var4;
   }

   private static final void toggleRow$lambda$150$lambda$149(Function1 var0, CompoundButton var1, boolean var2) {
      var0.invoke(var2);
   }

   private static final void toggleRow$lambda$151(Switch var0, View var1) {
      var0.toggle();
   }

   public final float carBearing() {
      double[] var6 = this.rtkNow();
      boolean var5 = true;
      if (var6 != null) {
         double var1 = RtkPosition.INSTANCE.getHeading();
         boolean var4;
         if (!Double.isInfinite(var1) && !Double.isNaN(var1)) {
            var4 = true;
         } else {
            var4 = false;
         }

         if (var4) {
            float var9 = (float)RtkPosition.INSTANCE.getHeading();
            return var9;
         }
      }

      float var3 = GpsBridge.INSTANCE.getOutBearing();
      boolean var8;
      if (!Float.isInfinite(var3) && !Float.isNaN(var3)) {
         var8 = var5;
      } else {
         var8 = false;
      }

      if (var8 && System.currentTimeMillis() - GpsBridge.INSTANCE.getLastLiveMs() < 30000L) {
         var3 = GpsBridge.INSTANCE.getOutBearing();
      } else {
         var3 = this.getT().getBearingDeg();
      }

      return var3;
   }

   public final double carLat() {
      double[] var6 = this.rtkNow();
      double var1;
      if (var6 != null) {
         var1 = var6[0];
      } else {
         var1 = GpsBridge.INSTANCE.getOutLat();
         boolean var5 = Double.isInfinite(var1);
         boolean var4 = true;
         boolean var3;
         if (!var5 && !Double.isNaN(var1)) {
            var3 = true;
         } else {
            var3 = false;
         }

         if (var3 && System.currentTimeMillis() - GpsBridge.INSTANCE.getLastLiveMs() < 30000L) {
            var1 = GpsBridge.INSTANCE.getOutLat();
         } else {
            var1 = this.getT().getGpsLat();
            if (!Double.isInfinite(var1) && !Double.isNaN(var1)) {
               var3 = var4;
            } else {
               var3 = false;
            }

            if (var3) {
               var1 = this.getT().getGpsLat();
            } else {
               var1 = this.lastKnown()[0];
            }
         }
      }

      return var1;
   }

   public final double carLon() {
      double[] var6 = this.rtkNow();
      double var1;
      if (var6 != null) {
         var1 = var6[1];
      } else {
         var1 = GpsBridge.INSTANCE.getOutLon();
         boolean var5 = Double.isInfinite(var1);
         boolean var4 = false;
         boolean var3;
         if (!var5 && !Double.isNaN(var1)) {
            var3 = true;
         } else {
            var3 = false;
         }

         if (var3 && System.currentTimeMillis() - GpsBridge.INSTANCE.getLastLiveMs() < 30000L) {
            var1 = GpsBridge.INSTANCE.getOutLon();
         } else {
            var1 = this.getT().getGpsLon();
            var3 = var4;
            if (!Double.isInfinite(var1)) {
               var3 = var4;
               if (!Double.isNaN(var1)) {
                  var3 = true;
               }
            }

            if (var3) {
               var1 = this.getT().getGpsLon();
            } else {
               var1 = this.lastKnown()[1];
            }
         }
      }

      return var1;
   }

   public final void expectRoutes() {
      this.showRoutesWhenReady = true;
      Nav.INSTANCE.refresh(this.host.getData());
   }

   public final MapTiles getTiles() {
      return this.tiles;
   }

   public final Map<String, Object> liveOrigin() {
      double[] var6 = this.rtkNow();
      boolean var5 = true;
      boolean var4 = true;
      if (var6 != null) {
         HashMap var11 = MapsKt.hashMapOf(new Pair[]{TuplesKt.to("latitude", var6[0]), TuplesKt.to("longitude", var6[1])});
         double var7 = RtkPosition.INSTANCE.getHeading();
         if (Double.isInfinite(var7) || Double.isNaN(var7)) {
            var4 = false;
         }

         if (var4) {
            var11.put("bearing", RtkPosition.INSTANCE.getHeading());
         }

         return var11;
      } else {
         double var1 = GpsBridge.INSTANCE.getOutLat();
         if (!Double.isInfinite(var1) && !Double.isNaN(var1)) {
            var4 = true;
         } else {
            var4 = false;
         }

         if (var4 && System.currentTimeMillis() - GpsBridge.INSTANCE.getLastLiveMs() <= 30000L) {
            HashMap var10 = MapsKt.hashMapOf(
               new Pair[]{TuplesKt.to("latitude", GpsBridge.INSTANCE.getOutLat()), TuplesKt.to("longitude", GpsBridge.INSTANCE.getOutLon())}
            );
            float var3 = GpsBridge.INSTANCE.getOutBearing();
            if (!Float.isInfinite(var3) && !Float.isNaN(var3)) {
               var4 = var5;
            } else {
               var4 = false;
            }

            if (var4) {
               var10.put("bearing", (double)GpsBridge.INSTANCE.getOutBearing());
            }

            return var10;
         } else {
            return null;
         }
      }
   }

   public final void openRoutes() {
      this.openSheet("routes");
      this.map.overview();
   }

   public final void showInfo(String var1) {
      Intrinsics.checkNotNullParameter(var1, "text");
      this.info.setText(var1);
      this.info.setVisibility(0);
      this.removeCallbacks(this.hideInfo);
      this.postDelayed(this.hideInfo, 6000L);
   }

   public final void showPreview() {
      this.previewShown = "";
      this.openSheet("routes");
   }

   public final void tick(long var1) {
      this.tickN++;
      this.tiles.setHost(Link.INSTANCE.getHost());
      this.tiles.setDirectKey(Nav.INSTANCE.getTileKey());
      Nav.poll$default(Nav.INSTANCE, this.host.getData(), true, var1, false, 8, null);
      if (this.tickN % 600 == 300) {
         int var7 = this.tiles.takeDirectCount();
         if (var7 > 0) {
            this.host.getData().request("nav_tiles", MapsKt.mapOf(TuplesKt.to("count", var7)), new MapPane$$ExternalSyntheticLambda43());
         }
      }

      if (Nav.INSTANCE.getActive() && Nav.INSTANCE.getLine() != null && (this.tickN % 3600 == 0 || Nav.INSTANCE.getLine() != this.prefetchedFor)) {
         this.prefetchedFor = Nav.INSTANCE.getLine();
         this.prefetchAhead();
      }

      double var5 = this.carLat();
      double var3 = this.carLon();
      Nav.INSTANCE.track(var5, var3, this.getT().getVEgo(), this.getT().isMetric(), this.getT().getStarted(), Nav.INSTANCE.voiceMode(this.getS()));
      this.map.onTick(var1);
      if (Nav.INSTANCE.getVersion() != this.lastVersion) {
         this.lastVersion = Nav.INSTANCE.getVersion();
         this.onNav();
      }

      if (this.tickN % 5 == 0) {
         this.refreshBars();
      }

      if (Intrinsics.areEqual(this.sheetKind, "steps") && this.tickN % 100 == 0) {
         this.fillSheet();
      }

      this.banner.invalidate();
   }

   private final class Banner extends View {
      private final Paint p;
      final MapPane this$0;
      private final Paint tp;

      public Banner(Context var1, Context var2) {
         Intrinsics.checkNotNullParameter(var2, "ctx");
         this.this$0 = var1;
         super(var2);
         this.p = new Paint(3);
         Paint var3 = new Paint(1);
         var3.setColor(-1);
         this.tp = var3;
      }

      private final String laneModifier(String var1) {
         switch (var1.hashCode()) {
            case -2016367553:
               if (var1.equals("slight right")) {
                  return "slightRight";
               }
               break;
            case -1531469187:
               if (var1.equals("sharp left")) {
                  return "sharpLeft";
               }
               break;
            case -757963388:
               if (var1.equals("slight left")) {
                  return "slightLeft";
               }
               break;
            case -225243546:
               if (var1.equals("sharp right")) {
                  return "sharpRight";
               }
               break;
            case 3317767:
               String var5 = "left";
               if (var1.equals("left")) {
                  return var5;
               }
               break;
            case 108511772:
               String var4 = "right";
               if (var1.equals("right")) {
                  return var4;
               }
               break;
            case 111623794:
               String var2 = "uturn";
               if (var1.equals("uturn")) {
                  return var2;
               }
         }

         return "straight";
      }

      protected void onDraw(Canvas var1) {
         Intrinsics.checkNotNullParameter(var1, "c");
         float var2 = this.getWidth();
         float var7 = this.getHeight();
         this.p.setColor(-266899898);
         var1.drawRoundRect(new RectF(0.0F, 0.0F, var2, var7), this.this$0.dp(16.0F), this.this$0.dp(16.0F), this.p);
         JSONObject var10 = this.this$0.getT().navState();
         if (var10 == null) {
            this.tp.setTextSize(this.this$0.dp(22.0F));
            this.tp.setTypeface(Typeface.DEFAULT_BOLD);
            String var26 = Nav.INSTANCE.getStatus();
            String var27;
            if (Intrinsics.areEqual(var26, "arrived")) {
               var27 = "You have arrived";
            } else if (Intrinsics.areEqual(var26, "routeUnavailable")) {
               var27 = "No route right now";
            } else {
               var27 = "Finding the route…";
            }

            var1.drawText(var27, this.this$0.dp(18.0F), var7 / 2 + this.this$0.dp(8.0F), this.tp);
         } else {
            float var6 = Math.min(var7, this.this$0.dp(92.0F));
            float var4 = this.this$0.dp(12.0F);
            float var3 = 2;
            float var5 = var6 - var3 * var4;
            NavIcons var11 = NavIcons.INSTANCE;
            Context var12 = this.getContext();
            Intrinsics.checkNotNullExpressionValue(var12, "getContext(...)");
            String var13 = var10.optString("maneuverType");
            Intrinsics.checkNotNullExpressionValue(var13, "optString(...)");
            String var14 = var10.optString("maneuverModifier");
            Intrinsics.checkNotNullExpressionValue(var14, "optString(...)");
            Bitmap var28 = var11.icon(var12, var13, var14);
            if (var28 != null) {
               var1.drawBitmap(var28, null, new RectF(var4, var4, var4 + var5, var4 + var5), this.p);
            }

            var5 = var4 + var5 + this.this$0.dp(14.0F);
            this.tp.setTypeface(Typeface.DEFAULT_BOLD);
            this.tp.setTextSize(this.this$0.dp(26.0F));
            var1.drawText(
               Nav.distanceText$default(Nav.INSTANCE, var10.optDouble("maneuverDistance", 0.0), this.this$0.getT().isMetric(), false, 4, null),
               var5,
               this.this$0.dp(26.0F) + var4,
               this.tp
            );
            this.tp.setTypeface(Typeface.DEFAULT);
            this.tp.setTextSize(this.this$0.dp(19.0F));
            var1.drawText(
               TextUtils.ellipsize(var10.optString("maneuverPrimaryText"), new TextPaint(this.tp), var2 - var5 - var4, TruncateAt.END).toString(),
               var5,
               var6 - var4 - this.this$0.dp(6.0F),
               this.tp
            );
            Pair var21 = Nav.INSTANCE.nextLanes();
            if (var21 != null) {
               Nav.Step var22 = (Nav.Step)var21.getFirst();
               if (var22 != null) {
                  List var23 = var22.getLanes();
                  if (var23 != null) {
                     if (var7 - var6 < this.this$0.dp(40.0F)) {
                        return;
                     }

                     this.p.setColor(855638016);
                     var1.drawRect(this.this$0.dp(6.0F), var6, var2 - this.this$0.dp(6.0F), var7 - this.this$0.dp(6.0F), this.p);
                     var4 = Math.min(this.this$0.dp(46.0F), (var2 - var3 * var4) / var23.size());
                     var5 = Math.min(var4 - this.this$0.dp(6.0F), var7 - var6 - this.this$0.dp(14.0F));
                     var2 = (var2 - var23.size() * var4) / var3;
                     var6 += (var7 - var6) / var3;
                     Iterator var32 = var23.iterator();

                     for (int var8 = 0; var32.hasNext(); var8++) {
                        label59: {
                           boolean var9 = true;
                           var33 = (Nav.Lane)var32.next();
                           if (var33.getOk()) {
                              if (var33.getUse().length() <= 0) {
                                 var9 = false;
                              }

                              if (var9) {
                                 var24 = var33.getUse();
                                 break label59;
                              }
                           }

                           String var29 = CollectionsKt.firstOrNull(var33.getArrows());
                           var24 = var29;
                           if (var29 == null) {
                              var24 = "straight";
                           }
                        }

                        Paint var30 = this.p;
                        short var20;
                        if (var33.getOk()) {
                           var20 = 255;
                        } else {
                           var20 = 70;
                        }

                        var30.setAlpha(var20);
                        var11 = NavIcons.INSTANCE;
                        Context var34 = this.getContext();
                        Intrinsics.checkNotNullExpressionValue(var34, "getContext(...)");
                        Bitmap var25 = var11.icon(var34, "turn", this.laneModifier(var24));
                        if (var25 != null) {
                           var1.drawBitmap(
                              var25, null, new RectF(var2 + (var4 - var5) / var3, var6 - var5 / var3, var2 + (var4 + var5) / var3, var6 + var5 / var3), this.p
                           );
                        }

                        this.p.setAlpha(255);
                        if (var8 > 0) {
                           this.p.setColor(1442840575);
                           var1.drawRect(var2 - this.this$0.dp(0.5F), var6 - var5 / var3, var2 + this.this$0.dp(0.5F), var6 + var5 / var3, this.p);
                        }

                        var2 += var4;
                     }

                     return;
                  }
               }
            }
         }
      }
   }

   public interface Host {
      void closeMap();

      DataClient getData();

      boolean getNight();

      UiSettings getSettings();

      Telemetry getTelemetry();

      void navigateTo(String var1, String var2, double var3, double var5);

      void openSearch();
   }

   private final class MapCanvas extends View {
      private float bearing;
      private double cx;
      private double cy;
      private final Paint fillP;
      private boolean following;
      private final GestureDetector gestures;
      private final Matrix inv;
      private final double k;
      private int[] labelAt;
      private long lastTouchMs;
      private final Paint lineP;
      private final Matrix m;
      private boolean overviewOn;
      private final Path path;
      private boolean previewing;
      private final float[] pts;
      private final ScaleGestureDetector scale;
      private final Paint textP;
      final MapPane this$0;
      private final Paint tileP;
      private final Matrix tilt;
      private double twoAngle;
      private float twoMidY;
      private int twoMode;
      private float twoSpan;
      private double twoStartAngle;
      private float twoStartMidY;
      private float twoStartSpan;
      private boolean userRotated;
      private double zoom;
      private double zoomOffset;

      // $VF: synthetic method
      public static void $r8$lambda$DQboxdqons_aXSeAala_D80IieM/* $VF was: $r8$lambda$DQboxdqons_aXSeAala_D80IieM*/(
         String var0, MapPane.MapCanvas var1, double var2, double var4, MapPane var6
      ) {
         dropPin$lambda$19$lambda$18(var0, var1, var2, var4, var6);
      }

      // $VF: synthetic method
      public static void $r8$lambda$QbLiUw8YOHmahEDtOzB37NWo4z0(
         MapPane var0, String var1, String var2, double var3, double var5, DialogInterface var7, int var8
      ) {
         dropPin$lambda$19$lambda$18$lambda$17(var0, var1, var2, var3, var5, var7, var8);
      }

      // $VF: synthetic method
      public static void $r8$lambda$mBRjoIXYaE5lP25IAGr6a48lzeM(MapPane.MapCanvas var0, double var1, double var3, MapPane var5) {
         dropPin$lambda$19(var0, var1, var3, var5);
      }

      public MapCanvas(Context var1, Context var2) {
         Intrinsics.checkNotNullParameter(var2, "ctx");
         this.this$0 = var1;
         super(var2);
         this.cx = Double.NaN;
         this.cy = Double.NaN;
         this.zoom = 15.5;
         this.following = true;
         this.k = RangesKt.coerceIn(this.this$0.d / 1.33F, 1.0F, 1.6F);
         this.tileP = new Paint(2);
         Paint var3 = new Paint(1);
         var3.setStyle(Style.STROKE);
         var3.setStrokeCap(Cap.ROUND);
         var3.setStrokeJoin(Join.ROUND);
         this.lineP = var3;
         this.fillP = new Paint(1);
         Paint var4 = new Paint(1);
         var4.setColor(-1);
         var4.setTypeface(Typeface.DEFAULT_BOLD);
         var4.setTextAlign(Align.CENTER);
         this.textP = var4;
         this.path = new Path();
         this.m = new Matrix();
         this.inv = new Matrix();
         this.pts = new float[8];
         this.labelAt = new int[0];
         this.tilt = new Matrix();
         this.scale = new ScaleGestureDetector(var2, (OnScaleGestureListener)(new SimpleOnScaleGestureListener() {

            public boolean onScale(ScaleGestureDetector var1) {
               Intrinsics.checkNotNullParameter(var1, "det");
               if (MapPane.MapCanvas.this.twoMode != 2 && MapPane.MapCanvas.this.twoMode != 3) {
                  double var2x = Math.log(var1.getScaleFactor()) / Math.log(2.0);
                  if (MapPane.MapCanvas.this.getFollowing()) {
                     MapPane.MapCanvas.this.zoomOffset = RangesKt.coerceIn(MapPane.MapCanvas.this.zoomOffset + var2x, -6.0, 3.0);
                  } else {
                     MapPane.MapCanvas.this.setZoom(RangesKt.coerceIn(MapPane.MapCanvas.this.getZoom() + var2x, 3.0, 18.4));
                  }

                  MapPane.MapCanvas.this.lastTouchMs = System.currentTimeMillis();
                  MapPane.MapCanvas.this.invalidate();
                  return true;
               } else {
                  return true;
               }
            }
         }));
         this.gestures = new GestureDetector(var2, (OnGestureListener)(new SimpleOnGestureListener() {

            public boolean onDoubleTap(MotionEvent var1) {
               Intrinsics.checkNotNullParameter(var1, "e");
               MapPane.MapCanvas.this.zoomBy(1.0);
               return true;
            }

            public boolean onDown(MotionEvent var1) {
               Intrinsics.checkNotNullParameter(var1, "e");
               return true;
            }

            public void onLongPress(MotionEvent var1) {
               Intrinsics.checkNotNullParameter(var1, "e");
               MapPane.MapCanvas.this.dropPin(var1.getX(), var1.getY());
            }

            public boolean onScroll(MotionEvent var1, MotionEvent var2x, float var3x, float var4x) {
               Intrinsics.checkNotNullParameter(var2x, "e2");
               if (MapPane.MapCanvas.this.scale.isInProgress()) {
                  return true;
               } else if (var2x.getPointerCount() > 1) {
                  return true;
               } else {
                  MapPane.MapCanvas.this.unfollow();
                  MapPane.MapCanvas.this.overviewOn = false;
                  MapPane.MapCanvas.this.setMatrix();
                  double[] var8 = MapPane.MapCanvas.this.toMerc(var2x.getX() + var3x, var2x.getY() + var4x);
                  double[] var9 = MapPane.MapCanvas.this.toMerc(var2x.getX(), var2x.getY());
                  double var5 = var8[0];
                  boolean var7;
                  if (!Double.isInfinite(var5) && !Double.isNaN(var5)) {
                     var7 = true;
                  } else {
                     var7 = false;
                  }

                  if (var7) {
                     var5 = var9[0];
                     if (!Double.isInfinite(var5) && !Double.isNaN(var5)) {
                        var7 = true;
                     } else {
                        var7 = false;
                     }

                     if (var7) {
                        MapPane.MapCanvas.this.setCx(MapPane.MapCanvas.this.getCx() + (var8[0] - var9[0]));
                        MapPane.MapCanvas.this.setCy(MapPane.MapCanvas.this.getCy() + (var8[1] - var9[1]));
                     }
                  }

                  MapPane.MapCanvas.this.lastTouchMs = System.currentTimeMillis();
                  MapPane.MapCanvas.this.invalidate();
                  return true;
               }
            }

            public boolean onSingleTapConfirmed(MotionEvent var1) {
               Intrinsics.checkNotNullParameter(var1, "e");
               MapPane.MapCanvas.this.tap(var1.getX(), var1.getY());
               return true;
            }
         }));
      }

      private final void bubble(Canvas var1, float var2, float var3, String var4, int var5) {
         this.textP.setTextSize(this.this$0.dp(14.0F));
         float var6 = this.textP.measureText(var4);
         float var7 = 2;
         RectF var8 = new RectF(
            var2 - var6 / var7 - this.this$0.dp(9.0F), var3 - this.this$0.dp(30.0F), var6 / var7 + var2 + this.this$0.dp(9.0F), var3 - this.this$0.dp(6.0F)
         );
         this.fillP.setStyle(Style.FILL);
         this.fillP.setColor(var5);
         var1.drawRoundRect(var8, this.this$0.dp(8.0F), this.this$0.dp(8.0F), this.fillP);
         var1.drawText(var4, var2, var3 - this.this$0.dp(13.0F), this.textP);
      }

      private final void car(Canvas var1, float var2, float var3, float var4) {
         var1.save();
         var1.translate(var2, var3);
         var1.rotate(var4);
         var2 = this.this$0.dp(15.0F);
         Path var5 = new Path();
         var5.moveTo(0.0F, -var2);
         var5.lineTo(var2 * 0.8F, var2 * 0.85F);
         var5.lineTo(0.0F, 0.45F * var2);
         var5.lineTo(-var2 * 0.8F, 0.85F * var2);
         var5.close();
         this.fillP.setStyle(Style.FILL);
         this.fillP.setColor(1429176557);
         var1.drawCircle(0.0F, 0.0F, 1.6F * var2, this.fillP);
         this.fillP.setColor(-1);
         this.fillP.setStyle(Style.STROKE);
         this.fillP.setStrokeWidth(this.this$0.dp(5.0F));
         this.fillP.setStrokeJoin(Join.ROUND);
         var1.drawPath(var5, this.fillP);
         this.fillP.setStyle(Style.FILL);
         this.fillP.setColor(-13664019);
         var1.drawPath(var5, this.fillP);
         var1.restore();
      }

      private final void dropPin(float var1, float var2) {
         this.setMatrix();
         double[] var3 = this.toMerc(var1, var2);
         new Thread(new MapPane$MapCanvas$$ExternalSyntheticLambda2(this, Nav.INSTANCE.latOf(var3[1]), Nav.INSTANCE.lonOf(var3[0]), this.this$0)).start();
      }

      // $VF: Duplicated exception handlers to handle obfuscated exceptions
      private static final void dropPin$lambda$19(MapPane.MapCanvas var0, double var1, double var3, MapPane var5) {
         Object var7 = null;

         String var6;
         label27: {
            List var8;
            try {
               Geocoder var12 = new Geocoder(var0.getContext());
               var8 = var12.getFromLocation(var1, var3, 1);
            } catch (Exception var11) {
               var6 = null;
               break label27;
            }

            var6 = (String)var7;
            label24:
            if (var8 != null) {
               try {
                  var13 = CollectionsKt.firstOrNull(var8);
               } catch (Exception var10) {
                  var6 = null;
                  break label24;
               }

               var6 = (String)var7;
               if (var13 != null) {
                  try {
                     var6 = var13.getAddressLine(0);
                  } catch (Exception var9) {
                     var6 = null;
                  }
               }
            }
         }

         var0.post(new MapPane$MapCanvas$$ExternalSyntheticLambda0(var6, var0, var1, var3, var5));
      }

      private static final void dropPin$lambda$19$lambda$18(String var0, MapPane.MapCanvas var1, double var2, double var4, MapPane var6) {
         String var12;
         label27: {
            if (var0 != null) {
               var12 = StringsKt.substringBefore$default(var0, ",", null, 2, null);
               if (var12 != null) {
                  var12 = var12;
                  boolean var7;
                  if (var12.length() == 0) {
                     var7 = true;
                  } else {
                     var7 = false;
                  }

                  if (var7) {
                     var12 = null;
                  }

                  String var9 = var12;
                  var12 = var9;
                  if (var9 != null) {
                     break label27;
                  }
               }
            }

            var12 = "Dropped pin";
         }

         Builder var13 = new Builder(var1.getContext()).setTitle("Drive here?");
         String var10;
         if (var0 == null) {
            var10 = String.format("%.5f, %.5f", Arrays.copyOf(new Object[]{var2, var4}, 2));
            Intrinsics.checkNotNullExpressionValue(var10, "format(...)");
         } else {
            var10 = var0;
         }

         var13.setMessage(var10)
            .setPositiveButton("Go", new MapPane$MapCanvas$$ExternalSyntheticLambda1(var6, var12, var0, var2, var4))
            .setNegativeButton("Cancel", null)
            .show();
      }

      private static final void dropPin$lambda$19$lambda$18$lambda$17(
         MapPane var0, String var1, String var2, double var3, double var5, DialogInterface var7, int var8
      ) {
         MapPane.Host var10 = var0.host;
         String var9;
         if (var2 == null) {
            var9 = "";
         } else {
            var9 = var2;
         }

         var10.navigateTo(var1, var9, var3, var5);
      }

      private final float focusX() {
         return this.getWidth() / 2.0F;
      }

      private final float focusY() {
         float var1;
         float var2;
         if (this.following && !this.this$0.getS().get("map_north_up")) {
            var2 = this.getHeight();
            if (this.pitch() > 5.0F) {
               var1 = 0.72F;
            } else {
               var1 = 0.66F;
            }
         } else {
            var2 = this.getHeight();
            var1 = 0.5F;
         }

         return var2 * var1;
      }

      private final void incident(Canvas var1, float var2, float var3, Nav.Incident var4) {
         float var5 = this.this$0.dp(14.0F);
         this.fillP.setStyle(Style.FILL);
         Paint var7 = this.fillP;
         int var6;
         if (var4.getClosed() || Intrinsics.areEqual(var4.getType(), "road_closure")) {
            var6 = -15000805;
         } else if (Intrinsics.areEqual(var4.getType(), "accident")) {
            var6 = -1755077;
         } else if (Intrinsics.areEqual(var4.getType(), "construction")) {
            var6 = -1012736;
         } else {
            var6 = -678365;
         }

         var7.setColor(var6);
         var1.drawCircle(var2, var3, var5, this.fillP);
         this.fillP.setStyle(Style.STROKE);
         this.fillP.setStrokeWidth(this.this$0.dp(2.0F));
         this.fillP.setColor(-1);
         var1.drawCircle(var2, var3, var5, this.fillP);
         this.textP.setTextSize(this.this$0.dp(15.0F));
         String var8;
         if (var4.getClosed() || Intrinsics.areEqual(var4.getType(), "road_closure")) {
            var8 = "⛔";
         } else if (Intrinsics.areEqual(var4.getType(), "construction")) {
            var8 = "\ud83d\udea7";
         } else if (Intrinsics.areEqual(var4.getType(), "accident")) {
            var8 = "!";
         } else if (Intrinsics.areEqual(var4.getType(), "weather")) {
            var8 = "☂";
         } else {
            var8 = "⚠";
         }

         var1.drawText(var8, var2, this.this$0.dp(5.5F) + var3, this.textP);
      }

      private final void marker(Canvas var1, double var2, double var4, double var6, Function2<? super Float, ? super Float, Unit> var8) {
         boolean var9;
         if (!Double.isInfinite(var2) && !Double.isNaN(var2)) {
            var9 = true;
         } else {
            var9 = false;
         }

         if (var9) {
            if (!Double.isInfinite(var4) && !Double.isNaN(var4)) {
               var9 = true;
            } else {
               var9 = false;
            }

            if (var9) {
               float[] var10 = this.screen(Nav.INSTANCE.mercX(var4), Nav.INSTANCE.mercY(var2), var6);
               if (!(var10[0] < -this.this$0.dp(40.0F))
                  && !(var10[1] < -this.this$0.dp(40.0F))
                  && !(var10[0] > this.getWidth() + this.this$0.dp(40.0F))
                  && !(var10[1] > this.getHeight() + this.this$0.dp(40.0F))) {
                  var8.invoke(var10[0], var10[1]);
                  return;
               }

               return;
            }
         }
      }

      private final void pin(Canvas var1, float var2, float var3) {
         float var4 = this.this$0.dp(11.0F);
         this.fillP.setStyle(Style.FILL);
         this.fillP.setColor(-1755077);
         var1.drawCircle(var2, var3 - var4 * 2.0F, var4, this.fillP);
         Path var5 = new Path();
         var5.moveTo(var2 - var4 * 0.75F, var3 - var4 * 1.4F);
         var5.lineTo(var2, var3);
         var5.lineTo(0.75F * var4 + var2, var3 - 1.4F * var4);
         var5.close();
         var1.drawPath(var5, this.fillP);
         this.fillP.setColor(-1);
         var1.drawCircle(var2, var3 - 2.0F * var4, 0.4F * var4, this.fillP);
      }

      private final float[] screen(double var1, double var3, double var5) {
         float var8 = (float)((var1 - this.cx) * var5);
         float var7 = (float)((var3 - this.cy) * var5);
         float[] var9 = new float[]{var8, var7};
         this.m.mapPoints(var9);
         return var9;
      }

      private final void setMatrix() {
         this.m.reset();
         this.m.postRotate(-this.bearing);
         double var1 = Math.toRadians(this.pitch());
         if (var1 > 0.01) {
            float var3 = RangesKt.coerceAtLeast(Math.max(this.getHeight(), this.getWidth()), 1);
            this.tilt.setValues(new float[]{1.0F, 0.0F, 0.0F, 0.0F, (float)Math.cos(var1), 0.0F, 0.0F, (float)(-Math.sin(var1) / (var3 * 1.6F)), 1.0F});
            this.m.postConcat(this.tilt);
         }

         this.m.postTranslate(this.focusX(), this.focusY());
         this.m.invert(this.inv);
      }

      private final void stroke(Canvas var1, Nav.Line var2, int var3, int var4, float var5, int var6, double var7) {
         if (var4 > var3) {
            this.path.reset();
            float var10 = 0.0F;
            float var12 = 0.0F;
            boolean var15 = false;
            if (var3 <= var4) {
               while (true) {
                  float var13 = (float)((var2.getMx()[var3] - this.cx) * var7);
                  float var11 = (float)((var2.getMy()[var3] - this.cy) * var7);
                  float var9;
                  boolean var14;
                  if (!var15) {
                     this.path.moveTo(var13, var11);
                     var10 = var13;
                     var9 = var11;
                     var14 = true;
                  } else {
                     label37: {
                        if (var3 != var4 && Math.abs(var13 - var10) < 1.5F) {
                           var9 = var12;
                           var14 = var15;
                           if (Math.abs(var11 - var12) < 1.5F) {
                              break label37;
                           }
                        }

                        this.path.lineTo(var13, var11);
                        var10 = var13;
                        var9 = var11;
                        var14 = var15;
                     }
                  }

                  if (var3 == var4) {
                     break;
                  }

                  var3++;
                  var12 = var9;
                  var15 = var14;
               }
            }

            this.lineP.setStrokeWidth(var5);
            this.lineP.setColor(var6);
            var1.drawPath(this.path, this.lineP);
         }
      }

      private final void tap(float var1, float var2) {
         double var3 = this.world();
         this.setMatrix();
         Nav.Line var23 = Nav.INSTANCE.getShownLine();
         int var16 = 0;
         int var15 = 1;
         if (var23 != null) {
            MapPane var26 = this.this$0;

            for (Nav.Incident var27 : var23.getIncidents()) {
               float[] var41 = this.screen(Nav.INSTANCE.mercX(var27.getLon()), Nav.INSTANCE.mercY(var27.getLat()), var3);
               if ((float)Math.hypot(var41[0] - var1, var41[1] - var2) < var26.dp(26.0F)) {
                  String var28 = Nav.INSTANCE.incidentWords(var27);
                  boolean var32;
                  if (var27.getImpact().length() > 0) {
                     var32 = true;
                  } else {
                     var32 = false;
                  }

                  String var25 = "";
                  String var42;
                  if (var32 && !Intrinsics.areEqual(var27.getImpact(), "unknown")) {
                     String var43 = var27.getImpact();
                     var42 = " · " + var43;
                  } else {
                     var42 = "";
                  }

                  var3 = var26.drivenAlong();
                  if (!Double.isInfinite(var3) && !Double.isNaN(var3)) {
                     var32 = true;
                  } else {
                     var32 = false;
                  }

                  String var45;
                  if (var32 && var27.getStartM() > var26.drivenAlong()) {
                     var45 = Nav.distanceText$default(Nav.INSTANCE, var27.getStartM() - var26.drivenAlong(), var26.getT().isMetric(), false, 4, null);
                     var45 = " · in " + var45;
                  } else {
                     var45 = "";
                  }

                  var32 = (boolean)var16;
                  if (var27.getDescription().length() > 0) {
                     var32 = true;
                  }

                  if (var32) {
                     var25 = var27.getDescription();
                     var25 = "\n" + var25;
                  }

                  var26.showInfo(var28 + var42 + var45 + var25);
                  return;
               }
            }
         }

         double var5 = this.this$0.dp(26.0F);
         Iterator var50 = Nav.INSTANCE.getShownLines().iterator();
         int var17 = -1;
         int var18 = 0;
         var16 = (byte)var15;
         var15 = var18;

         while (var50.hasNext()) {
            var18 = var15 + 1;
            Nav.Line var49 = (Nav.Line)var50.next();
            if (var49 != null && var15 != Nav.INSTANCE.getShownSelected() && var49.getSize() >= 2) {
               int var22 = Math.max(var16, var49.getSize() / 2000);
               float[] var44 = this.screen(var49.getMx()[0], var49.getMy()[0], var3);
               double var7 = var5;
               int var19 = var17;
               byte var21 = var16;
               var17 = var22;

               while (true) {
                  int var20 = Math.min(var17, var49.getSize() - var21);
                  float[] var47 = this.screen(var49.getMx()[var20], var49.getMy()[var20], var3);
                  double var9 = var47[0] - var44[0];
                  double var11 = var47[var21] - var44[var21];
                  double var13 = var9 * var9 + var11 * var11;
                  var5 = 0.0;
                  if (var13 > 0.0) {
                     var5 = RangesKt.coerceIn(((var1 - var44[0]) * var9 + (var2 - var44[var21]) * var11) / var13, 0.0, 1.0);
                  }

                  var9 = Math.hypot(var44[0] + var5 * var9 - var1, var44[1] + var5 * var11 - var2);
                  var16 = var19;
                  var5 = var7;
                  if (var9 < var7) {
                     var5 = var9;
                     var16 = var15;
                  }

                  if (var20 == var49.getSize() - 1) {
                     byte var40 = 1;
                     var17 = var16;
                     var15 = var18;
                     var16 = var40;
                     break;
                  }

                  var44 = var47;
                  var17 += var22;
                  var21 = 1;
                  var19 = var16;
                  var7 = var5;
               }
            } else {
               var15 = var18;
            }
         }

         if (var17 >= 0) {
            this.this$0.lookAt(var17);
         }
      }

      private final double[] toMerc(float var1, float var2) {
         float[] var5 = new float[]{var1, var2};
         this.inv.mapPoints(var5);
         double var3 = this.world();
         return new double[]{this.cx + var5[0] / var3, this.cy + var5[1] / var3};
      }

      private final boolean twoFinger(MotionEvent var1) {
         if (var1.getPointerCount() < 2) {
            if (var1.getActionMasked() == 1 || var1.getActionMasked() == 3) {
               this.twoMode = 0;
            }

            return false;
         } else {
            float var15 = var1.getX(0);
            float var13 = var1.getY(0);
            float var16 = var1.getX(1);
            float var14 = var1.getY(1);
            double var2 = Math.toDegrees(Math.atan2(var14 - var13, var16 - var15));
            float var12 = (var13 + var14) / 2;
            var15 = (float)Math.hypot(var16 - var15, var14 - var13);
            switch (var1.getActionMasked()) {
               case 2:
                  if (this.twoMode == 0) {
                     double var4 = Math.abs((var2 - this.twoStartAngle + 540) % 360 - 180);
                     var16 = Math.abs(var12 - this.twoStartMidY);
                     float var17 = Math.abs(var15 - this.twoStartSpan);
                     byte var18;
                     if (var4 > 10.0) {
                        var18 = 2;
                     } else if (var16 > this.this$0.dp(22.0F) && var17 < this.this$0.dp(30.0F) && Math.abs(var14 - var13) < 0.8F * var15) {
                        var18 = 3;
                     } else if (var17 > this.this$0.dp(24.0F)) {
                        var18 = 1;
                     } else {
                        var18 = 0;
                     }

                     this.twoMode = var18;
                  }

                  switch (this.twoMode) {
                     case 2:
                        double var21 = this.twoAngle;
                        double var8 = 540;
                        double var10 = 360;
                        double var6 = 180;
                        if (this.following) {
                           this.unfollow();
                        }

                        this.userRotated = true;
                        this.overviewOn = false;
                        this.bearing = ((this.bearing - (float)((var2 - var21 + var8) % var10 - var6)) % 360.0F + 360.0F) % 360.0F;
                        this.invalidate();
                        this.this$0.refreshButtons();
                        break;
                     case 3:
                        var13 = RangesKt.coerceIn(this.this$0.getS().getFloat("map_tilt", 0.0F) + (this.twoMidY - var12) * 0.18F, 0.0F, 60.0F);
                        this.this$0.getS().setFloat("map_tilt", var13);
                        this.invalidate();
                        this.this$0.refreshButtons();
                  }

                  this.twoAngle = var2;
                  this.twoMidY = var12;
                  this.twoSpan = var15;
                  this.lastTouchMs = System.currentTimeMillis();
                  break;
               case 5:
                  this.twoMode = 0;
                  this.twoAngle = var2;
                  this.twoMidY = var12;
                  this.twoSpan = var15;
                  this.twoStartAngle = var2;
                  this.twoStartMidY = var12;
                  this.twoStartSpan = var15;
            }

            boolean var20 = true;
            boolean var19 = var20;
            if (this.twoMode != 2) {
               if (this.twoMode == 3) {
                  var19 = var20;
               } else {
                  var19 = false;
               }
            }

            return var19;
         }
      }

      private final void unfollow() {
         if (this.following) {
            if (!Double.isNaN(this.cx) && this.getWidth() > 0) {
               this.setMatrix();
               double[] var1 = this.toMerc(this.getWidth() / 2.0F, this.getHeight() * 0.5F);
               this.cx = var1[0];
               this.cy = var1[1];
            }

            this.following = false;
            this.this$0.refreshBars();
         }
      }

      private final double world() {
         return Math.pow(2.0, this.zoom) * 512.0 * this.k;
      }

      public final void compass() {
         if (this.userRotated && !this.following) {
            this.userRotated = false;
            this.bearing = 0.0F;
         } else {
            this.this$0.getS().set("map_north_up", this.this$0.getS().get("map_north_up") ^ true);
         }

         this.invalidate();
      }

      public final void follow() {
         this.following = true;
         this.previewing = false;
         this.overviewOn = false;
         this.userRotated = false;
         this.zoomOffset = 0.0;
         this.invalidate();
         this.this$0.refreshBars();
      }

      public final float getBearing() {
         return this.bearing;
      }

      public final double getCx() {
         return this.cx;
      }

      public final double getCy() {
         return this.cy;
      }

      public final boolean getFollowing() {
         return this.following;
      }

      public final boolean getPreviewing() {
         return this.previewing;
      }

      public final boolean getRotatedByHand() {
         return this.userRotated;
      }

      public final double getZoom() {
         return this.zoom;
      }

      public final void linesChanged() {
         Nav.Line var17 = Nav.INSTANCE.getShownLine();
         int var11 = Nav.INSTANCE.getShownLines().size();
         int[] var16 = new int[var11];
         int var10 = 0;

         for (int var9 = 0; var9 < var11; var9++) {
            var16[var9] = -1;
         }

         this.labelAt = var16;
         Iterator var24 = Nav.INSTANCE.getShownLines().iterator();
         int var20 = var10;

         while (var24.hasNext()) {
            int var14 = var20 + 1;
            Nav.Line var18 = (Nav.Line)var24.next();
            if (var18 != null && var17 != null && var20 != Nav.INSTANCE.getShownSelected() && var18.getSize() >= 4) {
               var10 = -1;
               double var3 = -1.0;
               int var12 = Math.max(1, var18.getSize() / 60);
               int var15 = Math.max(1, var17.getSize() / 400);
               var20 = var12;

               while (var20 < var18.getSize() - var12) {
                  double var1 = Double.MAX_VALUE;

                  for (int var23 = 0; var23 < var17.getSize(); var23 += var15) {
                     double var7 = var18.getMx()[var20] - var17.getMx()[var23];
                     double var5 = var18.getMy()[var20] - var17.getMy()[var23];
                     var1 = Math.min(var1, var7 * var7 + var5 * var5);
                  }

                  double var19 = var3;
                  if (var1 > var3) {
                     var10 = var20;
                     var19 = var1;
                  }

                  var20 += var12;
                  var3 = var19;
               }

               this.labelAt[var20] = var10;
            }

            var20 = var14;
         }
      }

      protected void onDraw(Canvas var1) {
         Intrinsics.checkNotNullParameter(var1, "c");
         short var14;
         short var15;
         short var16;
         if (this.this$0.host.getNight()) {
            var14 = 30;
            var15 = 36;
            var16 = 27;
         } else {
            var14 = 238;
            var15 = 241;
            var16 = 236;
         }

         var1.drawColor(Color.rgb(var16, var14, var15));
         if (!Double.isNaN(this.cx)) {
            this.setMatrix();
            double var8 = this.world();
            String var24 = this.style();
            this.pts[0] = 0.0F;
            this.pts[1] = 0.0F;
            this.pts[2] = this.getWidth();
            this.pts[3] = 0.0F;
            this.pts[4] = 0.0F;
            this.pts[5] = this.getHeight();
            this.pts[6] = this.getWidth();
            this.pts[7] = this.getHeight();
            this.inv.mapPoints(this.pts);
            float var12 = Float.MAX_VALUE;
            var14 = 0;
            float var13 = -Float.MAX_VALUE;
            float var11 = Float.MAX_VALUE;

            float var10;
            for (var10 = -Float.MAX_VALUE; var14 < 4; var14++) {
               var12 = Math.min(var12, this.pts[var14 * 2]);
               var13 = Math.max(var13, this.pts[var14 * 2]);
               var11 = Math.min(var11, this.pts[var14 * 2 + 1]);
               var10 = Math.max(var10, this.pts[var14 * 2 + 1]);
            }

            var1.save();
            var1.concat(this.m);
            var15 = (int)Math.floor(this.zoom + 0.4);
            byte var68;
            if (this.following) {
               var68 = 16;
            } else {
               var68 = 18;
            }

            var15 = RangesKt.coerceIn(var15, 0, var68);
            var68 = var15;
            if (this.pitch() > 25.0F) {
               var68 = RangesKt.coerceAtLeast(var15 - 1, 0);
            }

            for (var15 = var68; var15 > 0; var15--) {
               double var4 = (var13 - var12) / (var8 / (1 << var15));
               double var2 = 2;
               if (!((var4 + var2) * ((var10 - var11) / (var8 / (1 << var15)) + var2) > 40.0)) {
                  break;
               }
            }

            int var21 = 1 << var15;
            double var6 = var8 / var21;
            var68 = (int)Math.floor((this.cx + var12 / var8) * var21);
            int var19 = (int)Math.floor((this.cx + var13 / var8) * var21);
            double var35 = this.cy;
            var16 = RangesKt.coerceAtLeast((int)Math.floor((var35 + var11 / var8) * var21), 0);
            var35 = this.cy;
            int var20 = RangesKt.coerceAtMost((int)Math.floor((var35 + var10 / var8) * var21), var21 - 1);
            HashSet var26 = new HashSet();
            int var17 = var16;
            if (var16 <= var20) {
               while (true) {
                  int var18 = var68;
                  if (var68 <= var19) {
                     while (true) {
                        MapTiles.Companion var25 = MapTiles.Companion;
                        var26.add(var25.key(var24, var15, (var18 % var21 + var21) % var21, var17));
                        var12 = var10;
                        var10 = var11;
                        if (var18 == var19) {
                           break;
                        }

                        var18++;
                        var10 = var10;
                     }
                  } else {
                     var12 = var10;
                     var10 = var11;
                  }

                  if (var17 == var20) {
                     break;
                  }

                  var17++;
                  var11 = var10;
                  var10 = var12;
               }
            }

            this.this$0.getTiles().setWanted(var26);
            RectF var143 = new RectF();
            Rect var28 = new Rect();
            var35 = this.cx;
            double var46 = var21 * var35;
            var35 = this.cy;
            var35 = var21 * var35;
            ArrayList var27 = new ArrayList();
            String var137;
            if (var16 <= var20) {
               int var100 = var16;

               while (true) {
                  var15 = var68;
                  if (var68 <= var19) {
                     while (true) {
                        var137 = var24;
                        var27.add(TuplesKt.to(var68, var100));
                        var24 = var24;
                        if (var68 == var19) {
                           break;
                        }

                        var68++;
                        var24 = var137;
                     }
                  }

                  var137 = var24;
                  if (var100 == var20) {
                     break;
                  }

                  var100++;
                  var68 = var15;
               }
            } else {
               var137 = var24;
            }

            List var117 = var27;
            if (var117.size() > 1) {
               CollectionsKt.sortWith(var117, new Comparator(var46, var35) {
                  final double $fx$inlined;
                  final double $fy$inlined;

                  {
                     this.$fx$inlined = var1;
                     this.$fy$inlined = var3;
                  }

                  @Override
                  public final int compare(T var1, T var2) {
                     var2 = var2;
                     int var7 = ((Number)var2.component1()).intValue();
                     int var8x = ((Number)var2.component2()).intValue();
                     double var3 = var7 + 0.5 - this.$fx$inlined;
                     double var5 = var8x + 0.5 - this.$fy$inlined;
                     Comparable var11x = var3 * var3 + var5 * var5;
                     var1 = var1;
                     var8x = ((Number)var1.component1()).intValue();
                     var7 = ((Number)var1.component2()).intValue();
                     var5 = var8x + 0.5 - this.$fx$inlined;
                     var3 = var7 + 0.5 - this.$fy$inlined;
                     return ComparisonsKt.compareValues(var11x, var5 * var5 + var3 * var3);
                  }
               });
            }

            Iterator var29 = var27.iterator();
            Intrinsics.checkNotNullExpressionValue(var29, "iterator(...)");
            var24 = var137;
            RectF var138 = var143;
            var68 = var15;
            Iterator var148 = var29;

            while (var148.hasNext()) {
               var29 = (Iterator)var148.next();
               Intrinsics.checkNotNullExpressionValue(var29, "next(...)");
               Pair var154 = (Pair)var29;
               var17 = ((Number)var154.component1()).intValue();
               var20 = ((Number)var154.component2()).intValue();
               int var101 = (var17 % var21 + var21) % var21;
               var12 = (float)(((double)var17 / var21 - this.cx) * var8);
               var10 = (float)(((double)var20 / var21 - this.cy) * var8);
               var138.set(var12, var10, (float)var6 + var12 + 0.6F, (float)var6 + var10 + 0.6F);
               MapTiles var155 = this.this$0.getTiles();
               Bitmap var156 = var155.get(var24, var68, var101, var20);
               if (var156 != null) {
                  var1.drawBitmap(var156, null, var138, this.tileP);
               } else {
                  for (int var95 = 1; var95 < 5; var95++) {
                     int var22 = var68 - var95;
                     if (var22 < 0) {
                        break;
                     }

                     Bitmap var30 = this.this$0.getTiles().peek(var24, var22, var101 >> var95, var20 >> var95);
                     if (var30 != null) {
                        var22 = 512 >> var95;
                        var28.set((var101 & (1 << var95) - 1) * var22, (var20 & (1 << var95) - 1) * var22, 0, 0);
                        var28.right = var28.left + var22;
                        var28.bottom = var28.top + var22;
                        var1.drawBitmap(var30, var28, var138, this.tileP);
                        break;
                     }
                  }
               }
            }

            Canvas var149 = var1;
            int var116 = Nav.INSTANCE.getShownSelected();
            Iterator var157 = Nav.INSTANCE.getShownLines().iterator();
            var17 = 0;

            for (Iterator var144 = var157; var144.hasNext(); var17++) {
               Nav.Line var158 = (Nav.Line)var144.next();
               if (var158 != null && var17 != var116) {
                  var20 = var158.getSize();
                  var10 = this.this$0.dp(10.0F);
                  int var102;
                  if (this.this$0.host.getNight()) {
                     var102 = -13945790;
                  } else {
                     var102 = -10521711;
                  }

                  this.stroke(var1, var158, 0, var20 - 1, var10, var102, var8);
                  var20 = var158.getSize();
                  var10 = this.this$0.dp(6.0F);
                  if (this.this$0.host.getNight()) {
                     var102 = -10587504;
                  } else {
                     var102 = -6441773;
                  }

                  this.stroke(var1, var158, 0, var20 - 1, var10, var102, var8);
               }
            }

            Nav.Line var119 = CollectionsKt.getOrNull(Nav.INSTANCE.getShownLines(), var116);
            if (var119 != null) {
               MapPane var139 = this.this$0;
               var35 = var139.drivenAlong();
               boolean var72;
               if (!Double.isInfinite(var35) && !Double.isNaN(var35)) {
                  var72 = 1;
               } else {
                  var72 = 0;
               }

               if (var72) {
                  var72 = var119.indexAt(var35);
               } else {
                  var72 = 0;
               }

               if (var72 > 0) {
                  this.stroke(var1, var119, 0, var72, var139.dp(7.0F), -7432802, var8);
               }

               var21 = 1090519040;
               this.stroke(var1, var119, var72, var119.getSize() - 1, var139.dp(12.0F), -16040559, var8);
               this.stroke(var1, var119, var72, var119.getSize() - 1, var139.dp(8.0F), -13664019, var8);
               var20 = var119.getTrafficLevel().length;
               int var104 = 0;
               var15 = var72;

               for (int var106 = var20; var104 < var106; var104++) {
                  var20 = var119.getTrafficLevel()[var104];
                  if (var20 >= 2) {
                     int var23 = RangesKt.coerceAtLeast(Math.max(var119.indexAt(var119.getTrafficFrom()[var104]) - 1, var15), 0);
                     var21 = var119.indexAt(var119.getTrafficTo()[var104]);
                     if (var21 > var23) {
                        if (var20 == 5) {
                           this.lineP.setPathEffect((PathEffect)(new DashPathEffect(new float[]{var139.dp(6.0F), var139.dp(5.0F)}, 0.0F)));
                        }

                        var10 = var139.dp(8.0F);
                        switch (var20) {
                           case 2:
                              var72 = -678365;
                              break;
                           case 3:
                              var72 = -1554886;
                              break;
                           case 4:
                              var72 = -7464165;
                              break;
                           default:
                              var72 = -15658735;
                        }

                        var20 = 1090519040;
                        this.stroke(var1, var119, var23, var21, var10, var72, var8);
                        this.lineP.setPathEffect(null);
                     }
                  }
               }

               Unit var120 = Unit.INSTANCE;
               Unit var121 = Unit.INSTANCE;
            }

            byte var89 = 2;
            var1.restore();
            JSONObject var122 = Nav.INSTANCE.getShownDestination();
            if (var122 != null) {
               var35 = var122.optDouble("latitude");
               var46 = var122.optDouble("longitude");
               boolean var75;
               if (!Double.isInfinite(var35) && !Double.isNaN(var35)) {
                  var75 = true;
               } else {
                  var75 = false;
               }

               if (var75) {
                  if (!Double.isInfinite(var46) && !Double.isNaN(var46)) {
                     var75 = true;
                  } else {
                     var75 = false;
                  }

                  if (var75) {
                     float[] var123 = this.screen(Nav.INSTANCE.mercX(var46), Nav.INSTANCE.mercY(var35), var8);
                     if (!(var123[0] < -this.this$0.dp(40.0F))
                        && !(var123[1] < -this.this$0.dp(40.0F))
                        && !(var123[0] > this.getWidth() + this.this$0.dp(40.0F))
                        && !(var123[1] > this.getHeight() + this.this$0.dp(40.0F))) {
                        this.pin(var1, var123[0], var123[1]);
                     }
                  }
               }

               Unit var124 = Unit.INSTANCE;
               Unit var125 = Unit.INSTANCE;
            }

            Nav.Line var126 = Nav.INSTANCE.getShownLine();
            if (var126 != null) {
               for (Nav.Incident var145 : var126.getIncidents()) {
                  var35 = this.this$0.drivenAlong();
                  boolean var77;
                  if (!Double.isInfinite(var35) && !Double.isNaN(var35)) {
                     var77 = true;
                  } else {
                     var77 = false;
                  }

                  if (!var77 || !(var145.getEndM() < this.this$0.drivenAlong())) {
                     var35 = var145.getLat();
                     var46 = var145.getLon();
                     if (!Double.isInfinite(var35) && !Double.isNaN(var35)) {
                        var77 = true;
                     } else {
                        var77 = false;
                     }

                     if (var77) {
                        if (!Double.isInfinite(var46) && !Double.isNaN(var46)) {
                           var77 = true;
                        } else {
                           var77 = false;
                        }

                        if (var77) {
                           float[] var127 = this.screen(Nav.INSTANCE.mercX(var46), Nav.INSTANCE.mercY(var35), var8);
                           if (!(var127[0] < -this.this$0.dp(40.0F))
                              && !(var127[1] < -this.this$0.dp(40.0F))
                              && !(var127[0] > this.getWidth() + this.this$0.dp(40.0F))
                              && !(var127[1] > this.getHeight() + this.this$0.dp(40.0F))) {
                              this.incident(var149, var127[0], var127[1], var145);
                           }
                        }
                     }
                  }
               }
            }

            Iterator var141 = Nav.INSTANCE.getShownLines().iterator();
            int var97 = 0;

            for (int var90 = var97; var141.hasNext(); var90++) {
               Nav.Line var146 = (Nav.Line)var141.next();
               Integer var128 = ArraysKt.getOrNull(this.labelAt, var90);
               if (var128 != null) {
                  var97 = var128;
               } else {
                  var97 = -1;
               }

               if (var146 != null && var90 != var116 && var97 >= 0 && var97 < var146.getSize()) {
                  Iterator var150 = Nav.INSTANCE.getShownRoutes().iterator();

                  label292: {
                     while (var150.hasNext()) {
                        var24 = (String)var150.next();
                        boolean var105;
                        if (((Nav.Route)var24).getIndex() == var90) {
                           var105 = true;
                        } else {
                           var105 = false;
                        }

                        if (var105) {
                           break label292;
                        }
                     }

                     var24 = null;
                  }

                  Nav.Route var130 = (Nav.Route)var24;
                  if (var130 != null) {
                     Nav.Route var151 = Nav.INSTANCE.getShownRoute();
                     if (var151 != null) {
                        var35 = var130.getDurationS() - var151.getDurationS();
                     } else {
                        var35 = 0.0;
                     }

                     if (Math.abs(var35) < 60.0) {
                        var24 = "same time";
                     } else {
                        if (var35 > 0.0) {
                           var24 = "+";
                        } else {
                           var24 = "−";
                        }

                        String var152 = Nav.INSTANCE.durationText(Math.abs(var35));
                        var24 = var24 + var152;
                     }

                     float[] var147 = this.screen(var146.getMx()[var97], var146.getMy()[var97], var8);
                     var11 = var147[0];
                     var10 = var147[1];
                     if (var35 < -60.0) {
                        var97 = -13722793;
                     } else {
                        var97 = -12958894;
                     }

                     this.bubble(var1, var11, var10, var24, var97);
                  }
               }
            }

            if (this.overviewOn) {
               Nav.Route var142 = Nav.INSTANCE.getShownRoute();
               if (var142 != null) {
                  Nav.Line var133 = Nav.INSTANCE.getShownLine();
                  if (var133 != null) {
                     boolean var91;
                     if (var133.getSize() > 0) {
                        var91 = true;
                     } else {
                        var91 = false;
                     }

                     if (!var91) {
                        var133 = null;
                     }

                     if (var133 != null) {
                        float[] var134 = this.screen(var133.getMx()[var133.getSize() / var89], var133.getMy()[var133.getSize() / var89], var8);
                        this.bubble(var1, var134[0], var134[1], Nav.INSTANCE.durationText(var142.getDurationS()), -13664019);
                        Unit var31 = Unit.INSTANCE;
                        Unit var32 = Unit.INSTANCE;
                     }
                  }
               }
            }

            var35 = this.this$0.carLat();
            var46 = this.this$0.carLon();
            boolean var81;
            if (!Double.isInfinite(var35) && !Double.isNaN(var35)) {
               var81 = 1;
            } else {
               var81 = 0;
            }

            if (var81) {
               float[] var135 = this.screen(Nav.INSTANCE.mercX(var46), Nav.INSTANCE.mercY(var35), var8);
               Float var33 = this.this$0.carBearing();
               var10 = var33.floatValue();
               if (!Float.isInfinite(var10) && !Float.isNaN(var10)) {
                  var81 = (boolean)1;
               } else {
                  var81 = (boolean)0;
               }

               if (!var81) {
                  var33 = null;
               }

               if (var33 != null) {
                  var10 = var33;
               } else {
                  var10 = 0.0F;
               }

               this.car(var149, var135[0], var135[1], var10 - this.bearing);
            }

            this.textP.setTextAlign(Align.LEFT);
            this.textP.setTextSize(this.this$0.dp(10.0F));
            Paint var34 = this.textP;
            if (this.this$0.host.getNight()) {
               var81 = Color.argb(150, 255, 255, 255);
            } else {
               var81 = Color.argb(150, 0, 0, 0);
            }

            var34.setColor(var81);
            var12 = this.this$0.dp(8.0F);
            var13 = this.getHeight();
            var11 = this.this$0.dp(4.0F);
            if (this.this$0.bottomBar.getVisibility() == 0) {
               var10 = this.this$0.bottomBar.getHeight();
               var10 = this.this$0.dp(10.0F) + var10;
            } else {
               var10 = 0.0F;
            }

            var149.drawText("© Mapbox © OpenStreetMap", var12, var13 - var11 - var10, this.textP);
            this.textP.setTextAlign(Align.CENTER);
            this.textP.setColor(-1);
         }
      }

      public final void onTick(long var1) {
         double var5 = this.this$0.carLat();
         double var3 = this.this$0.carLon();
         if (!this.following && this.this$0.tickN % 3 == 0) {
            this.invalidate();
         }

         if (Double.isNaN(this.cx)) {
            boolean var11;
            if (!Double.isInfinite(var5) && !Double.isNaN(var5)) {
               var11 = true;
            } else {
               var11 = false;
            }

            if (var11) {
               this.cx = Nav.INSTANCE.mercX(var3);
               this.cy = Nav.INSTANCE.mercY(var5);
            }
         }

         if (!this.following && !this.overviewOn && var1 - this.lastTouchMs > 12000L && this.this$0.getT().getVEgo() > 3.0F) {
            this.follow();
         }

         if (this.following) {
            boolean var21;
            if (!Double.isInfinite(var5) && !Double.isNaN(var5)) {
               var21 = true;
            } else {
               var21 = false;
            }

            if (var21) {
               this.cx = Nav.INSTANCE.mercX(var3);
               this.cy = Nav.INSTANCE.mercY(var5);
               double var7 = this.this$0.getT().getVEgo();
               var3 = 16.6;
               if (!(var7 < 5.0)) {
                  if (var7 < 13.0) {
                     var3 = 16.6 - (var7 - 5) / 8 * 0.8;
                  } else {
                     if (var7 < 27.0) {
                        var3 = (var7 - 13) / 14 * 0.8;
                        var5 = 15.8;
                     } else {
                        var3 = RangesKt.coerceAtMost((var7 - 27) / 8 * 0.4, 0.4);
                        var5 = 15.0;
                     }

                     var3 = var5 - var3;
                  }
               }

               JSONObject var12 = this.this$0.getT().navState();
               var3 = var3;
               if (var12 != null) {
                  var3 = var3;
                  if (var7 < 20.0) {
                     var3 = var3;
                     if (var12.optDouble("maneuverDistance", 9.0E9) < 250.0) {
                        var3 += 0.5;
                     }
                  }
               }

               this.zoom = this.zoom + (RangesKt.coerceIn(var3 + this.zoomOffset, 3.0, 18.4) - this.zoom) * 0.08;
               float var19;
               if (this.this$0.getS().get("map_north_up")) {
                  var19 = 0.0F;
               } else {
                  Float var25 = this.this$0.carBearing();
                  MapPane var13 = this.this$0;
                  var19 = var25.floatValue();
                  if (!Float.isInfinite(var19) && !Float.isNaN(var19)) {
                     var21 = true;
                  } else {
                     var21 = false;
                  }

                  if (var21 && var13.getT().getVEgo() > 1.0F) {
                     var21 = true;
                  } else {
                     var21 = false;
                  }

                  if (!var21) {
                     var25 = null;
                  }

                  if (var25 != null) {
                     var19 = var25;
                  } else {
                     var19 = this.bearing;
                  }
               }

               float var10 = (var19 - this.bearing + 540.0F) % 360.0F - 180.0F;
               var19 = var10;
               if (Math.abs(var10) < 0.3F) {
                  var19 = 0.0F;
               }

               this.bearing = (this.bearing + 0.2F * var19 + 360.0F) % 360.0F;
               this.invalidate();
               return;
            }
         }

         if (!this.overviewOn && !this.userRotated) {
            boolean var22;
            if (this.bearing == 0.0F) {
               var22 = true;
            } else {
               var22 = false;
            }

            if (!var22 && this.this$0.getS().get("map_north_up")) {
               this.bearing = 0.0F;
               this.invalidate();
            }
         }
      }

      public boolean onTouchEvent(MotionEvent var1) {
         Intrinsics.checkNotNullParameter(var1, "e");
         this.twoFinger(var1);
         this.scale.onTouchEvent(var1);
         this.gestures.onTouchEvent(var1);
         return true;
      }

      public final void overview() {
         List var19 = CollectionsKt.filterNotNull(Nav.INSTANCE.getShownLines());
         if (!var19.isEmpty() && this.getWidth() != 0) {
            double var15 = 1.0;
            double var5 = 0.0;
            double var3 = 1.0;
            double var1 = 0.0;

            for (Nav.Line var26 : var19) {
               int var17 = 0;
               int var18 = var26.getSize();
               double var11 = var1;
               double var13 = var3;
               double var7 = var5;
               double var9 = var15;

               while (true) {
                  var15 = var9;
                  var5 = var7;
                  var3 = var13;
                  var1 = var11;
                  if (var17 >= var18) {
                     break;
                  }

                  var9 = Math.min(var9, var26.getMx()[var17]);
                  var7 = Math.max(var7, var26.getMx()[var17]);
                  var13 = Math.min(var13, var26.getMy()[var17]);
                  var11 = Math.max(var11, var26.getMy()[var17]);
                  var17++;
               }
            }

            this.following = false;
            this.previewing = false;
            this.overviewOn = true;
            this.bearing = 0.0F;
            double var22 = 2;
            this.cx = (var15 + var5) / var22;
            this.cy = (var3 + var1) / var22;
            double var24 = this.getHeight();
            var22 = this.getWidth();
            var5 = Math.max(var5 - var15, 1.0E-9);
            double var25 = 512;
            this.zoom = RangesKt.coerceIn(
               Math.min(
                  Math.log(var22 * 0.8 / (var5 * var25 * this.k)) / Math.log(2.0),
                  Math.log(var24 * 0.36 / (Math.max(var1 - var3, 1.0E-9) * var25 * this.k)) / Math.log(2.0)
               ),
               3.0,
               17.0
            );
            this.cy = this.cy + this.getHeight() * 0.22 / this.world();
            this.lastTouchMs = System.currentTimeMillis() + 3600000;
            this.invalidate();
         }
      }

      public final float pitch() {
         boolean var2 = this.overviewOn;
         float var1 = 0.0F;
         if (!var2) {
            var1 = RangesKt.coerceIn(this.this$0.getS().getFloat("map_tilt", 0.0F), 0.0F, 60.0F);
         }

         return var1;
      }

      public final void preview(double var1, double var3) {
         this.following = false;
         this.previewing = true;
         this.overviewOn = false;
         this.cx = Nav.INSTANCE.mercX(var3);
         this.cy = Nav.INSTANCE.mercY(var1);
         this.zoom = 17.0;
         this.bearing = 0.0F;
         this.lastTouchMs = System.currentTimeMillis() + 20000;
         this.invalidate();
         this.this$0.refreshBars();
      }

      public final void setBearing(float var1) {
         this.bearing = var1;
      }

      public final void setCx(double var1) {
         this.cx = var1;
      }

      public final void setCy(double var1) {
         this.cy = var1;
      }

      public final void setZoom(double var1) {
         this.zoom = var1;
      }

      public final String style() {
         String var1;
         if (this.this$0.getS().get("map_traffic")) {
            if (this.this$0.host.getNight()) {
               var1 = "trafficDark";
            } else {
               var1 = "trafficLight";
            }
         } else if (this.this$0.host.getNight()) {
            var1 = "dark";
         } else {
            var1 = "light";
         }

         return var1;
      }

      public final void zoomBy(double var1) {
         if (this.following) {
            this.zoomOffset = RangesKt.coerceIn(this.zoomOffset + var1, -6.0, 3.0);
         } else {
            this.zoom = RangesKt.coerceIn(this.zoom + var1, 3.0, 18.4);
         }

         this.lastTouchMs = System.currentTimeMillis();
         this.invalidate();
      }
   }
}
