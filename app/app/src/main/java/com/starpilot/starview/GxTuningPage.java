package com.starpilot.starview;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import java.text.DateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.ranges.RangesKt;
import kotlin.sequences.SequencesKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

public final class GxTuningPage extends GxPage {
   private JSONObject activeTrial;
   private LinearLayout advancedBox;
   private TextView bAdvanced;
   private TextView bAnalyze;
   private TextView bKeep;
   private TextView bPause;
   private TextView bRefresh;
   private TextView bRevert;
   private TextView bSave;
   private TextView bSaveCurrent;
   private TextView bStop;
   private boolean busy;
   private final int cActual;
   private final int cD;
   private final int cDesired;
   private final int cF;
   private final int cI;
   private final int cP;
   private String carFp;
   private final ArrayList<GxTuningPage.ChartViews> chartViews;
   private final List<GxTuningPage.ChartCfg> charts;
   private LinearLayout comparisonBox;
   private final DateFormat dateFmt;
   private final LinkedHashSet<String> fbAccepted;
   private final LinkedHashSet<String> fbIgnored;
   private String fbNotes;
   private boolean flmBuilt;
   private String flmError;
   private boolean flmFetching;
   private boolean flmLoading;
   private LinearLayout flmNotices;
   private LinearLayout flmStatusBox;
   private boolean isOnroad;
   private boolean laneCentering;
   private String lastStatusReportId;
   private double lastTs;
   private LinearLayout latBox;
   private Pair<? extends TextView, ? extends TextView> latBtns;
   private boolean latBusy;
   private JSONObject latData;
   private boolean latFetching;
   private boolean latLoading;
   private final GxTuningPage.QualityCfg latQ;
   private JSONObject live;
   private String loadedReportId;
   private boolean loadingRoutes;
   private LinearLayout lonBox;
   private Pair<? extends TextView, ? extends TextView> lonBtns;
   private boolean lonBusy;
   private JSONObject lonData;
   private String lonError;
   private boolean lonFetching;
   private boolean lonLoading;
   private final GxTuningPage.QualityCfg lonQ;
   private boolean manBuilt;
   private final int maxRoutes;
   private final int maxSegments;
   private final boolean needsOffroad;
   private boolean plotsBuilt;
   private String plotsError;
   private boolean plotsFetching;
   private boolean plotsLoading;
   private boolean plotsPaused;
   private LinearLayout plotsStatus;
   private final Lazy prefs$delegate;
   private LinearLayout profilesBox;
   private JSONObject report;
   private LinearLayout reportBox;
   private boolean reportLoading;
   private JSONArray reports;
   private LinearLayout reportsBox;
   private final HashMap<String, LinearLayout> routeBoxes;
   private final HashMap<String, Switch> routeSwitches;
   private final ArrayList<JSONObject> routes;
   private LinearLayout routesBox;
   private LinearLayout routesHead;
   private final List<String> sampleKeys;
   private final ArrayList<GxTuningSample> samples;
   private JSONArray savedTunes;
   private final HashMap<String, Pair<String, String>> segmentRanges;
   private final LinkedHashSet<String> selectedRoutes;
   private boolean showAdvanced;
   private JSONObject statusData;
   private int tab;
   private LinearLayout tgBox;
   private boolean tgBuilt;
   private boolean tgBusy;
   private JSONObject tgData;
   private String tgError;
   private boolean tgFetching;
   private boolean tgLoading;
   private String tgSelected;
   private LinearLayout tunesBox;
   private JSONObject wsStatus;

   // $VF: synthetic method
   public static Unit $r8$lambda$_095B9lzlyzzRczjtJTuw6h__uc/* $VF was: $r8$lambda$_095B9lzlyzzRczjtJTuw6h__uc*/(GxTuningPage var0, int var1, LinearLayout var2) {
      return build$lambda$1(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Pair $r8$lambda$_J97tp_gRd3xFE6SMniqWsqX_vA/* $VF was: $r8$lambda$_J97tp_gRd3xFE6SMniqWsqX_vA*/(JSONObject var0, String var1) {
      return entries$lambda$7(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$_cfrJCD7Htw__Cw4PfHMh87Do50/* $VF was: $r8$lambda$_cfrJCD7Htw__Cw4PfHMh87Do50*/(GxTuningPage var0, List var1, int var2) {
      return paintTg$lambda$182$lambda$181(var0, var1, var2);
   }

   // $VF: synthetic method
   public static SharedPreferences $r8$lambda$0HOVRMoG_Yn8s3Md6LtNcngPHhk(Activity var0) {
      return prefs_delegate$lambda$136(var0);
   }

   // $VF: synthetic method
   public static CharSequence $r8$lambda$3cwkG0vcyVfcE8yUzaYoBQk0K6o(String var0) {
      return paintLon$lambda$207(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$43ybfNcywqGAo6dOIODbCkyy0IM(GxTuningPage var0, JSONObject var1) {
      return submitTune$lambda$55(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$5DfWssyEX_fj4tTPFgzY4ZjUeBk/* $VF was: $r8$lambda$5DfWssyEX_fj4tTPFgzY4ZjUeBk*/(GxTuningPage var0, String var1) {
      return renderReport$lambda$92(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$5dc54ls5CJAYcvY5Aer2WW_ExoE(GxTuningPage var0) {
      return buildLateral$lambda$34$lambda$33(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$6h5hTrDJgEJ6rNLavzDMeN1w9JE(GxTuningPage var0) {
      return buildLateral$lambda$35(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$6pao_rzuX9XkC1gFEhHUcw3zZdU(GxTuningPage var0, JSONObject var1) {
      return selectPath$lambda$107(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$75h2PI2noYknNIgriBS_hQHo6Pc/* $VF was: $r8$lambda$75h2PI2noYknNIgriBS_hQHo6Pc*/(GxTuningPage var0) {
      return buildManeuvers$lambda$187(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$7YPpwVKrOSVX25BCsyzfbmhv54k(boolean var0, GxTuningPage var1, String var2, String var3, JSONObject var4) {
      return manRun$lambda$197(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static CharSequence $r8$lambda$91V4j21Vri3NxCxLgtm65JpW6SM(String var0) {
      return paintLon$lambda$208(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$9VMlUYs_cGiKCYLk7QGG5dsRnbs(GxTuningPage var0, String var1) {
      return renderReport$lambda$101(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$A6_kgMps0Zv9W9j9aQt3yV_IiXg/* $VF was: $r8$lambda$A6_kgMps0Zv9W9j9aQt3yV_IiXg*/(GxTuningPage var0) {
      return saveFeedback$lambda$114$lambda$113(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$AUi3GlLdLaYyJLJ78YAa9CAmMPM(GxTuningPage var0) {
      return build$lambda$4(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Au_TbCggUu7vXH5BMHZxzrTOLPY/* $VF was: $r8$lambda$Au_TbCggUu7vXH5BMHZxzrTOLPY*/(GxTuningPage var0, String var1) {
      return saveCurrent$lambda$58(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$BqJ13_7_vtuZZD05cOhKA9m04ug(GxTuningPage var0) {
      return paintRoutesHead$lambda$126(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$CD4b_wdQRtQ5r2RdaqX5SoWvWOY/* $VF was: $r8$lambda$CD4b_wdQRtQ5r2RdaqX5SoWvWOY*/(
      GxTuningPage var0, String var1, JSONObject var2
   ) {
      return saveFeedback$lambda$114(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$H2x8INIAaSlJh7zo1G1rMHjbN0s(boolean var0, GxTuningPage var1, String var2, JSONObject var3) {
      return manLoad$lambda$193(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$H3oR1_tTmM5DM20PxBfHgmxqLhQ(GxTuningPage var0, String var1, String var2, String var3, JSONObject var4) {
      return tgSelectMode$lambda$170(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$JPCowy6uX9LdjfhptW3QrPKV8lg(GxTuningPage var0) {
      return paintRoutesHead$lambda$127(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$JRmEGTc8GjC_SouKKmBLinlUi9U/* $VF was: $r8$lambda$JRmEGTc8GjC_SouKKmBLinlUi9U*/(GxTuningPage var0) {
      return buildManeuvers$lambda$188(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$JUIPptTxqf5cWndjRa5uoTx6vGs(GxTuningPage var0, String var1) {
      return paintTg$lambda$184(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Jqlg1oe7MMVPTTAkCDu9ApSsKIM(GxTuningPage var0) {
      return paintRoutesHead$lambda$125(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$MS6YTIygHC8XvLXX8KhrszA0ZBE(GxTuningPage var0) {
      return buildLateral$lambda$31(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$N_kDruxdA1yvLQB0IfsJ7BqF1xQ/* $VF was: $r8$lambda$N_kDruxdA1yvLQB0IfsJ7BqF1xQ*/(GxTuningPage var0, int var1) {
      return build$lambda$0(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$O20lQ3YMpAbeDUAbx2_MBZ8qt0E(GxTuningPage var0) {
      return buildLateral$lambda$37(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$OdRMkgkhcxkri3ifjHW1Tnr5DUY(GxTuningPage var0, boolean var1, JSONObject var2) {
      return paintTunes$lambda$49(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$QnE3SjrrYOQbXsZxmsq5NB6VN0Y(GxTuningPage var0, String var1, JSONObject var2) {
      return loadReport$lambda$68(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$SIv8QCL5kZ0Cr7iCdPqwqtY9_rw(GxTuningPage var0, List var1) {
      return paintTg$lambda$182(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$SKFkJEhOR1bc_73bQAsTzF6LKBQ/* $VF was: $r8$lambda$SKFkJEhOR1bc_73bQAsTzF6LKBQ*/(GxTuningPage var0, String var1) {
      return renderRoute$lambda$132(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$XHbAm4XNDj6AEqzmak3_lz7OHaU(GxTuningPage var0, String var1, JSONObject var2) {
      return plotsLoad$lambda$148(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$YABdhQniDs_tWbKTevaZEY4nAOU(GxTuningPage var0) {
      return buildManeuvers$lambda$191(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$YOM3DCHnXIkHKC_yoQT47bhmno8(GxTuningPage var0) {
      return buildPlots$lambda$142(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Z7ufjAp_PAu6wgGqUOMX5NExnB8/* $VF was: $r8$lambda$Z7ufjAp_PAu6wgGqUOMX5NExnB8*/(
      GxTuningPage var0, Function0 var1, String var2, JSONObject var3
   ) {
      return flmRefresh$lambda$66(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$ZeVlQqiNsCqx5LuC5Nikk9y1jrs(GxTuningPage var0, String var1, boolean var2) {
      return renderRoute$lambda$130(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$aNW_9qkmWxE2z3E8Swc6NJf7epo/* $VF was: $r8$lambda$aNW_9qkmWxE2z3E8Swc6NJf7epo*/(GxTuningPage var0, JSONObject var1) {
      return paintTunes$lambda$49$lambda$48(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$ax7fNH5NyrPys_FhJW1HCtzHBx0(GxTuningPage var0) {
      return buildLateral$lambda$30(var0);
   }

   // $VF: synthetic method
   public static CharSequence $r8$lambda$fcQjdrU_ogl_dwDqZdg_qArpFkU/* $VF was: $r8$lambda$fcQjdrU_ogl_dwDqZdg_qArpFkU*/(String var0) {
      return loadRoutes$lambda$122$lambda$118$lambda$117(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$gvjtywXZCb9ePjV3lV8It_QAPng(GxTuningPage var0) {
      return buildPlots$lambda$138(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$hIkGwWtXjdiJrwjLMpxBv216qz8(GxTuningPage var0, JSONObject var1, String var2) {
      return submitTune$lambda$55$lambda$54(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$k4aFXRbq7tUWfgy2xeXY8IEk50g(GxTuningPage var0) {
      return buildLateral$lambda$36(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$kBxOVh5__vYT7_xypN3f6ybsx2U(GxTuningPage var0) {
      return paintRoutesHead$lambda$128(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$lUHR6vlPeK2YhFblND_Edqu88_8/* $VF was: $r8$lambda$lUHR6vlPeK2YhFblND_Edqu88_8*/(
      GxTuningPage var0, String var1, boolean var2, String var3, String var4, String var5
   ) {
      return editRange$lambda$135(var0, var1, var2, var3, var4, var5);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$m_MxRxtxOIi2lJG19sSyPplJQyc(GxTuningPage var0) {
      return buildPlots$lambda$140(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$n0zIdJzWQlyGmf63Yw6fqkxQ89U(GxTuningPage var0) {
      return renderReport$lambda$95(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$nMBVDQTUphIbg2k3PcUbl4FbOd8(GxTuningPage var0, String var1, JSONObject var2) {
      return deleteReport$lambda$109$lambda$108(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$nMQ_wBod1ZP10oESibodwgm7tNM(GxTuningPage var0) {
      return buildManeuvers$lambda$190(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$o0HLteyu7BBudEtftkhmp08s0dU(GxTuningPage var0, int var1, String var2, String var3) {
      return loadRoutes$lambda$122(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$ojyZBqTxVEOj6JC_51hr6h6VVbI/* $VF was: $r8$lambda$ojyZBqTxVEOj6JC_51hr6h6VVbI*/(
      GxTuningPage var0, Function1 var1, JSONObject var2
   ) {
      return runWith$lambda$61$lambda$60(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$pJBFHYlbEPpOlpPvGCiz7y6RIpE(GxTuningPage var0, String var1) {
      return renderRoute$lambda$134(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$prSAb39gNbh9_tEwOojQ_jZK6dw/* $VF was: $r8$lambda$prSAb39gNbh9_tEwOojQ_jZK6dw*/(GxTuningPage var0, JSONObject var1) {
      return paintTunes$lambda$51(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$qA_BK9lACxFW3pNlNrhbFtU4kyY(GxTuningPage var0) {
      return build$lambda$2(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$qAxMDKdGoiuiH4_2aKdJIEOHnkk/* $VF was: $r8$lambda$qAxMDKdGoiuiH4_2aKdJIEOHnkk*/(GxTuningPage var0, String var1) {
      return deleteReport$lambda$109(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$q_tloXYLKCVIQmjtlhx___bvYvY/* $VF was: $r8$lambda$q_tloXYLKCVIQmjtlhx___bvYvY*/(GxTuningPage var0) {
      return buildPlots$lambda$139(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$rGU3H8x0a15n6bhvOUhDbhOQyGY(GxTuningPage var0, JSONObject var1) {
      return paintTunes$lambda$46(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$rgR_D3cRkBd3uADZC0LJvq2T26k/* $VF was: $r8$lambda$rgR_D3cRkBd3uADZC0LJvq2T26k*/(GxTuningPage var0, JSONObject var1) {
      return renderReport$lambda$88(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$rmt21IKa6XrRXKO9nIE35PGqgts(GxTuningPage var0) {
      return buildLateral$lambda$32(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$rsTrR_E_cxFvPsq76OcHVSkTd9U/* $VF was: $r8$lambda$rsTrR_E_cxFvPsq76OcHVSkTd9U*/(GxTuningPage var0, String var1, View var2) {
      paintReports$lambda$72(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$s1EjFdjEIDXEQidgs4mjR4KlgI0(GxTuningPage var0, String var1, String var2) {
      return renameSavedTune$lambda$53(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$sDyOjmjH_M8oyH_0_CDFOunm_VA/* $VF was: $r8$lambda$sDyOjmjH_M8oyH_0_CDFOunm_VA*/(GxTuningPage var0) {
      return buildLateral$lambda$34(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$sMUhNEEEx7PhzvWiBHuSZa_2D7M(GxTuningPage var0, JSONObject var1) {
      return paintProfiles$lambda$104(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$sYsGsxcEwqO5lqPblALu8Zm4WKo(GxTuningPage var0, String var1, JSONObject var2) {
      return tgLoad$lambda$165(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$tI4XzUv3DVbE_H0XtSFvK1ZMHTE(GxTuningPage var0, JSONObject var1) {
      return analyze$lambda$115(var0, var1);
   }

   // $VF: synthetic method
   public static CharSequence $r8$lambda$uF6SvDW7nnfbG3YDvp8kFTOU6_o(GxTuningPage var0, JSONArray var1, int var2) {
      return curveStr$lambda$74(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$wM98WGFxLU5DH5_NzzFXGKOIgnM/* $VF was: $r8$lambda$wM98WGFxLU5DH5_NzzFXGKOIgnM*/(GxTuningPage var0, String var1) {
      return renderReport$lambda$102(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$wO73Pvsf8QJdfDsj4YUolonYMKo(GxTuningPage var0, JSONObject var1, String var2, Function0 var3, String var4, JSONObject var5) {
      return flmRefresh$lambda$66$lambda$65(var0, var1, var2, var3, var4, var5);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$wXss6tys_SfJLHwzyBEWSb6HUqc(GxTuningPage var0, boolean var1, JSONObject var2) {
      return paintTunes$lambda$45(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$wd1uaPKYwhveiAfdHO0HL73pZ10(GxTuningPage var0) {
      return build$lambda$5(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$wpDVghXEnPNQaZhxSO8PNWy_U8E/* $VF was: $r8$lambda$wpDVghXEnPNQaZhxSO8PNWy_U8E*/(
      GxTuningPage var0, Function1 var1, String var2, String var3, JSONObject var4
   ) {
      return runWith$lambda$61(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$wrIfOZVdETGZVnw_T9Ma1Lmx9Ho/* $VF was: $r8$lambda$wrIfOZVdETGZVnw_T9Ma1Lmx9Ho*/(GxTuningPage var0) {
      return build$lambda$3(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$x7IrKT_1UMFYLuyDWvAAhrD_WVg/* $VF was: $r8$lambda$x7IrKT_1UMFYLuyDWvAAhrD_WVg*/(GxTuningPage var0, String var1) {
      return paintReports$lambda$71(var0, var1);
   }

   public GxTuningPage(Activity var1, GxHost var2) {
      Intrinsics.checkNotNullParameter(var1, "act");
      Intrinsics.checkNotNullParameter(var2, "gx");
      super(var1, var2);
      this.needsOffroad = true;
      this.dateFmt = DateFormat.getDateTimeInstance(2, 3);
      this.maxRoutes = 250;
      this.maxSegments = 5;
      this.flmLoading = true;
      this.flmError = "";
      this.reports = new JSONArray();
      this.savedTunes = new JSONArray();
      this.carFp = "";
      this.wsStatus = new JSONObject();
      this.routes = new ArrayList<>();
      this.selectedRoutes = new LinkedHashSet<>();
      this.segmentRanges = new HashMap<>();
      this.loadedReportId = "";
      this.lastStatusReportId = "";
      this.fbAccepted = new LinkedHashSet<>();
      this.fbIgnored = new LinkedHashSet<>();
      this.fbNotes = "";
      this.routeBoxes = new HashMap<>();
      this.routeSwitches = new HashMap<>();
      this.cDesired = Color.parseColor("#7aa2f7");
      this.cActual = Color.parseColor("#9ece6a");
      this.cP = Color.parseColor("#5ec8c8");
      this.cI = Color.parseColor("#d4a060");
      this.cD = Color.parseColor("#e05577");
      this.cF = Color.parseColor("#bb9af7");
      GxTuningPage.ChartCfg var5 = new GxTuningPage.ChartCfg(
         "lateral",
         false,
         "Lateral Response",
         "m/s²",
         2,
         2,
         1.5,
         1.25,
         10.0,
         "lateral",
         "lateralSource",
         CollectionsKt.listOf(
            new Triple[]{new Triple<>("desiredLateralAccel", "Target", this.cDesired), new Triple<>("actualLateralAccel", "Measured", this.cActual)}
         )
      );
      GxTuningPage.ChartCfg var6 = new GxTuningPage.ChartCfg(
         "longitudinal",
         false,
         "Longitudinal Response",
         "m/s²",
         2,
         2,
         1.5,
         1.25,
         10.0,
         "longitudinal",
         "longitudinalSource",
         CollectionsKt.listOf(
            new Triple[]{new Triple<>("desiredLongitudinalAccel", "Target", this.cDesired), new Triple<>("actualLongitudinalAccel", "Measured", this.cActual)}
         )
      );
      GxTuningPage.ChartCfg var4 = new GxTuningPage.ChartCfg(
         "lateralTerms",
         true,
         "Lateral Controller Terms",
         "",
         3,
         3,
         0.15,
         1.35,
         1000.0,
         "lateralterms",
         "lateralTermsSource",
         CollectionsKt.listOf(
            new Triple[]{
               new Triple<>("lateralP", "P", this.cP),
               new Triple<>("lateralI", "I", this.cI),
               new Triple<>("lateralD", "D", this.cD),
               new Triple<>("lateralF", "F", this.cF)
            }
         )
      );
      GxTuningPage.ChartCfg var3 = new GxTuningPage.ChartCfg(
         "longitudinalTerms",
         true,
         "Longitudinal Accel Cmd Terms",
         "",
         3,
         3,
         0.15,
         1.35,
         1000.0,
         "longitudinalterms",
         "longitudinalTermsSource",
         CollectionsKt.listOf(
            new Triple[]{
               new Triple<>("longitudinalUpAccelCmd", "Up", this.cP),
               new Triple<>("longitudinalUiAccelCmd", "Ui", this.cI),
               new Triple<>("longitudinalUfAccelCmd", "Uf", this.cF)
            }
         )
      );
      this.charts = CollectionsKt.listOf(new GxTuningPage.ChartCfg[]{var5, var6, var4, var3});
      this.latQ = new GxTuningPage.QualityCfg("desiredLateralAccel", "actualLateralAccel", 0.5, 0.008, true, 0.5, 0.01, false, 0.0, 0.0, 0.15, 0.3, 0.5);
      this.lonQ = new GxTuningPage.QualityCfg(
         "desiredLongitudinalAccel", "actualLongitudinalAccel", 0.0, 0.05, true, 1.5, 0.04, true, 0.5, 0.9, 0.32, 0.52, 0.78
      );
      this.sampleKeys = CollectionsKt.listOf(
         new String[]{
            "speed",
            "desiredLateralAccel",
            "actualLateralAccel",
            "desiredLongitudinalAccel",
            "actualLongitudinalAccel",
            "lateralP",
            "lateralI",
            "lateralD",
            "lateralF",
            "longitudinalUpAccelCmd",
            "longitudinalUiAccelCmd",
            "longitudinalUfAccelCmd"
         }
      );
      this.samples = new ArrayList<>();
      this.plotsLoading = true;
      this.plotsError = "";
      this.prefs$delegate = LazyKt.lazy(new GxTuningPage$$ExternalSyntheticLambda12(var1));
      this.chartViews = new ArrayList<>();
      this.tgLoading = true;
      this.tgError = "";
      this.tgSelected = "";
      this.latLoading = true;
      this.lonLoading = true;
      this.lonError = "";
   }

   private final String activePathKey() {
      JSONObject var3 = this.report;
      if (var3 != null) {
         CharSequence var2 = this.s(var3, "selectedPathKey");
         Object var1 = var2;
         if (StringsKt.isBlank(var2)) {
            var1 = this.s(var3, "primaryPathKey");
         }

         String var5 = (String)var1;
         if (var5 != null) {
            return var5;
         }
      }

      return "";
   }

   private final void afterSelectionChange() {
      boolean var2;
      if (this.selectedSegmentCount() >= this.maxSegments) {
         var2 = true;
      } else {
         var2 = false;
      }

      for (Entry var6 : this.routeSwitches.entrySet()) {
         String var5 = (String)var6.getKey();
         Switch var7 = (Switch)var6.getValue();
         boolean var3;
         if (!this.selectedRoutes.contains(var5) && var2) {
            var3 = false;
         } else {
            var3 = true;
         }

         var7.setEnabled(var3);
         float var1;
         if (var3) {
            var1 = 1.0F;
         } else {
            var1 = 0.4F;
         }

         var7.setAlpha(var1);
      }

      this.paintRoutesHead();
      this.paintActions();
   }

   private final void analyze() {
      if (this.canAnalyze() && !this.busy) {
         JSONObject var4 = new JSONObject();
         Iterator var5 = this.selectedRoutes.iterator();
         Intrinsics.checkNotNullExpressionValue(var5, "iterator(...)");

         while (var5.hasNext()) {
            Pair var1 = (Pair)var5.next();
            Intrinsics.checkNotNullExpressionValue(var1, "next(...)");
            String var6 = (String)var1;
            Pair var2 = this.segmentRanges.get(var6);
            var1 = var2;
            if (var2 == null) {
               var1 = TuplesKt.to("", "");
            }

            String var3 = (String)var1.component1();
            String var11 = (String)var1.component2();
            if (!StringsKt.isBlank(var3) || !StringsKt.isBlank(var11)) {
               JSONObject var7 = new JSONObject();
               if (StringsKt.isBlank(var3)) {
                  var1 = (Pair)JSONObject.NULL;
               } else {
                  var1 = var3;
               }

               JSONObject var12 = var7.put("start", var1);
               if (StringsKt.isBlank(var11)) {
                  var1 = (Pair)JSONObject.NULL;
               } else {
                  var1 = var11;
               }

               var4.put(var6, var12.put("end", var1));
            }
         }

         this.runWith(
            "POST",
            "/api/flm/analyze",
            new JSONObject().put("routes", new JSONArray(CollectionsKt.toList(this.selectedRoutes))).put("segmentRanges", var4),
            "FLM analysis started.",
            new GxTuningPage$$ExternalSyntheticLambda5(this)
         );
      }
   }

   private static final Unit analyze$lambda$115(GxTuningPage var0, JSONObject var1) {
      if (var1 != null && !var0.selectedRoutes.isEmpty()) {
         var0.selectedRoutes.clear();
         var0.segmentRanges.clear();
         var0.renderRoutesAll();
      }

      return Unit.INSTANCE;
   }

   private final boolean angleControl() {
      JSONObject var1 = this.report;
      if (var1 != null) {
         var1 = var1.optJSONObject("car");
         if (var1 != null) {
            String var4 = this.s(var1, "controlPath");
            return Intrinsics.areEqual(var4, "angle");
         }
      }

      Object var3 = null;
      return Intrinsics.areEqual(var3, "angle");
   }

   private final void applyTrial(String var1) {
      JSONObject var2 = this.report;
      if (var2 != null) {
         String var3 = this.s(var2, "reportId");
         if (!StringsKt.isBlank(var3) && this.canApplyTrial()) {
            runWith$default(
               this, "POST", "/api/flm/trials/apply", new JSONObject().put("reportId", var3).put("profileId", var1), "Trial profile applied.", null, 16, null
            );
         }
      }
   }

   private final JSONArray arr(JSONObject var1, String var2) {
      if (var1 != null) {
         JSONArray var4 = var1.optJSONArray(var2);
         if (var4 != null) {
            return var4;
         }
      }

      return new JSONArray();
   }

   private final void autoOpenReport() {
      String var1 = this.s(this.status(), "reportId");
      if (!StringsKt.isBlank(var1) && !Intrinsics.areEqual(var1, this.lastStatusReportId)) {
         this.lastStatusReportId = var1;
         this.loadReport(var1);
      } else {
         label22: {
            JSONObject var3 = this.reports.optJSONObject(0);
            if (var3 != null) {
               String var2 = this.s(var3, "reportId");
               var1 = var2;
               if (var2 != null) {
                  break label22;
               }
            }

            var1 = "";
         }

         if (StringsKt.isBlank(this.loadedReportId) && !StringsKt.isBlank(var1)) {
            this.loadReport(var1);
         }
      }
   }

   private final TextView barBtn(LinearLayout var1, String var2, boolean var3, Function0<Unit> var4) {
      TextView var8 = this.getUi().button(var2, var3, var4);
      View var5 = (View)var8;
      LayoutParams var6 = new LayoutParams(-2, -2);
      var6.rightMargin = this.getUi().dp(10);
      Unit var7 = Unit.INSTANCE;
      var1.addView(var5, (android.view.ViewGroup.LayoutParams)var6);
      return var8;
   }

   // $VF: synthetic method
   static TextView barBtn$default(GxTuningPage var0, LinearLayout var1, String var2, boolean var3, Function0 var4, int var5, Object var6) {
      if ((var5 & 4) != 0) {
         var3 = false;
      }

      return var0.barBtn(var1, var2, var3, var4);
   }

   private final GxTuningPage.Bounds bounds(String var1) {
      Iterator var7 = this.routes.iterator();

      Object var5;
      while (true) {
         boolean var4 = var7.hasNext();
         var5 = null;
         if (var4) {
            Object var6 = var7.next();
            if (!Intrinsics.areEqual(this.s((JSONObject)var6, "name"), var1)) {
               continue;
            }

            var8 = var6;
            break;
         }

         var8 = null;
         break;
      }

      JSONObject var11 = (JSONObject)var8;
      Object var9;
      if (var11 != null) {
         var9 = var11.opt("segmentCount");
      } else {
         var9 = null;
      }

      int var3 = Math.max(0, (int)Math.round(num$default(this, var9, 0.0, 2, null)));
      var9 = var5;
      if (var11 != null) {
         var9 = var11.opt("firstSegmentNum");
      }

      int var2 = Math.max(0, (int)Math.round(num$default(this, var9, 0.0, 2, null)));
      return new GxTuningPage.Bounds(var2, Math.max(0, var3 - 1) + var2, var3);
   }

   private final LinearLayout btnBar(ViewGroup var1) {
      LinearLayout var2 = new LinearLayout((Context)this.getAct());
      var2.setOrientation(0);
      var2.setPadding(0, this.getUi().dp(6), 0, this.getUi().dp(6));
      HorizontalScrollView var3 = new HorizontalScrollView((Context)this.getAct());
      var3.setHorizontalScrollBarEnabled(false);
      var3.addView((View)var2);
      var1.addView((View)var3);
      return var2;
   }

   private static final Unit build$lambda$0(GxTuningPage var0, int var1) {
      int var2 = var0.tab;
      var0.tab = var1;
      if (var0.getShown() && var2 != var1) {
         var0.kick(var1);
      }

      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$1(GxTuningPage var0, int var1, LinearLayout var2) {
      Intrinsics.checkNotNullParameter(var2, "b");
      switch (var1) {
         case 0:
            var0.buildLateral(var2);
            break;
         case 1:
            var0.buildPlots(var2);
            break;
         case 2:
            var0.buildTesting(var2);
            break;
         default:
            var0.buildManeuvers(var2);
      }

      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$2(GxTuningPage var0) {
      if (var0.tab == 0) {
         flmRefresh$default(var0, true, null, 2, null);
      }

      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$3(GxTuningPage var0) {
      if (var0.tab == 1 && !var0.plotsPaused) {
         plotsLoad$default(var0, false, 1, null);
      }

      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$4(GxTuningPage var0) {
      if (var0.tab == 2 && !var0.tgBusy) {
         var0.tgLoad();
      }

      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$5(GxTuningPage var0) {
      if (var0.tab == 3) {
         var0.manLoad(true);
         var0.manLoad(false);
      }

      return Unit.INSTANCE;
   }

   private final void buildLateral(LinearLayout var1) {
      this.flmBuilt = true;
      LinearLayout var4 = this.card((ViewGroup)var1);
      headLine$default(this, (ViewGroup)var4, "Lateral Tuning (FLM)", 0, 4, null);
      GxPage.text$default(
         this,
         "Analyze one or more local routes, review deterministic lateral findings, apply a bounded trial, drive, then revert or refine.",
         (ViewGroup)var4,
         0.0F,
         0,
         false,
         28,
         null
      );
      this.flmNotices = this.box((ViewGroup)var4);
      LinearLayout var3 = this.btnBar((ViewGroup)var4);
      this.bAnalyze = this.barBtn(var3, "Analyze Selected", true, new GxTuningPage$$ExternalSyntheticLambda11(this));
      this.bStop = barBtn$default(this, var3, "Stop", false, new GxTuningPage$$ExternalSyntheticLambda22(this), 4, null);
      this.bRevert = barBtn$default(this, var3, "Revert Trial", false, new GxTuningPage$$ExternalSyntheticLambda33(this), 4, null);
      this.bKeep = barBtn$default(this, var3, "Keep as Baseline", false, new GxTuningPage$$ExternalSyntheticLambda44(this), 4, null);
      this.bSave = barBtn$default(this, var3, "Save Tune", false, new GxTuningPage$$ExternalSyntheticLambda55(this), 4, null);
      this.bRefresh = barBtn$default(this, var3, "Refresh", false, new GxTuningPage$$ExternalSyntheticLambda66(this), 4, null);
      this.sub("Workspace status", (ViewGroup)var4);
      this.flmStatusBox = this.box((ViewGroup)var4);
      this.header("Saved Tunes", (ViewGroup)var1);
      this.note(
         "Save a working FLM trial, switch between setups, then use Revert Trial to return to the exact manual settings from before FLM.", (ViewGroup)var1
      );
      this.tunesBox = this.box((ViewGroup)var1);
      this.header("Local Routes", (ViewGroup)var1);
      int var2 = this.maxSegments;
      this.note("Pick routes and segment ranges to analyze. A run is limited to " + var2 + " segments total.", (ViewGroup)var1);
      this.routesHead = this.box((ViewGroup)var1);
      this.routesBox = this.box((ViewGroup)var1);
      this.header("Reports", (ViewGroup)var1);
      this.reportsBox = this.box((ViewGroup)var1);
      this.reportBox = this.box((ViewGroup)var1);
      var1 = this.card((ViewGroup)var1);
      this.bSaveCurrent = headBtn$default(
         this,
         headLine$default(this, (ViewGroup)var1, "Trial Profiles", 0, 4, null),
         "Save Current",
         false,
         new GxTuningPage$$ExternalSyntheticLambda70(this),
         4,
         null
      );
      GxPage.text$default(
         this,
         "Apply one bounded profile at a time. Revert restores the exact advanced-lateral and FLM state from before the trial.",
         (ViewGroup)var1,
         0.0F,
         0,
         false,
         28,
         null
      );
      this.profilesBox = this.box((ViewGroup)var1);
      this.paintFlm();
      this.renderReport();
      this.renderRoutesAll();
      this.loadRoutes();
   }

   private static final Unit buildLateral$lambda$30(GxTuningPage var0) {
      var0.analyze();
      return Unit.INSTANCE;
   }

   private static final Unit buildLateral$lambda$31(GxTuningPage var0) {
      runWith$default(var0, "POST", "/api/flm/analyze/stop", null, "FLM analysis stopped.", null, 16, null);
      return Unit.INSTANCE;
   }

   private static final Unit buildLateral$lambda$32(GxTuningPage var0) {
      runWith$default(var0, "POST", "/api/flm/trials/revert", null, "Trial reverted.", null, 16, null);
      return Unit.INSTANCE;
   }

   private static final Unit buildLateral$lambda$34(GxTuningPage var0) {
      var0.confirm(
         "Keep Current as Baseline",
         "Keep the currently applied tuning values and end this trial? This does not restore the previous tune.",
         "Yes, Keep",
         new GxTuningPage$$ExternalSyntheticLambda0(var0)
      );
      return Unit.INSTANCE;
   }

   private static final Unit buildLateral$lambda$34$lambda$33(GxTuningPage var0) {
      runWith$default(var0, "POST", "/api/flm/trials/accept", null, "Current tune kept as baseline.", null, 16, null);
      return Unit.INSTANCE;
   }

   private static final Unit buildLateral$lambda$35(GxTuningPage var0) {
      var0.saveCurrent();
      return Unit.INSTANCE;
   }

   private static final Unit buildLateral$lambda$36(GxTuningPage var0) {
      flmRefresh$default(var0, false, null, 3, null);
      return Unit.INSTANCE;
   }

   private static final Unit buildLateral$lambda$37(GxTuningPage var0) {
      var0.saveCurrent();
      return Unit.INSTANCE;
   }

   private final void buildManeuvers(LinearLayout var1) {
      this.manBuilt = true;
      this.header("Lateral Maneuvers", (ViewGroup)var1);
      LinearLayout var3 = this.card((ViewGroup)var1);
      GxPage.text$default(this, "Arm the lateral maneuver suite from your phone and monitor progress live.", (ViewGroup)var3, 0.0F, 0, false, 28, null);
      LinearLayout var4 = this.btnBar((ViewGroup)var3);
      TextView var2 = this.barBtn(var4, "Start / Arm", true, new GxTuningPage$$ExternalSyntheticLambda34(this));
      TextView var5 = barBtn$default(this, var4, "Stop", false, new GxTuningPage$$ExternalSyntheticLambda35(this), 4, null);
      var5.setTextColor(Color.rgb(240, 110, 130));
      Unit var10 = Unit.INSTANCE;
      this.latBtns = TuplesKt.to(var2, var5);
      this.latBox = this.box((ViewGroup)var3);
      this.header("Long Maneuvers", (ViewGroup)var1);
      LinearLayout var7 = this.card((ViewGroup)var1);
      GxPage.text$default(this, "Run the longitudinal maneuver suite from your phone and monitor progress live.", (ViewGroup)var7, 0.0F, 0, false, 28, null);
      var3 = this.btnBar((ViewGroup)var7);
      TextView var6 = this.barBtn(var3, "Start / Arm", true, new GxTuningPage$$ExternalSyntheticLambda36(this));
      TextView var9 = barBtn$default(this, var3, "Stop", false, new GxTuningPage$$ExternalSyntheticLambda37(this), 4, null);
      var9.setTextColor(Color.rgb(240, 110, 130));
      Unit var11 = Unit.INSTANCE;
      this.lonBtns = TuplesKt.to(var6, var9);
      this.lonBox = this.box((ViewGroup)var7);
      this.paintLat();
      this.paintLon();
   }

   private static final Unit buildManeuvers$lambda$187(GxTuningPage var0) {
      var0.manRun(true, "start");
      return Unit.INSTANCE;
   }

   private static final Unit buildManeuvers$lambda$188(GxTuningPage var0) {
      var0.manRun(true, "stop");
      return Unit.INSTANCE;
   }

   private static final Unit buildManeuvers$lambda$190(GxTuningPage var0) {
      var0.manRun(false, "start");
      return Unit.INSTANCE;
   }

   private static final Unit buildManeuvers$lambda$191(GxTuningPage var0) {
      var0.manRun(false, "stop");
      return Unit.INSTANCE;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private final void buildPlots(LinearLayout var1) {
      this.plotsBuilt = true;

      Object var10;
      label97:
      try {
         var10 = Result.Companion;
         var10 = (Collection)KtCompat.resultOf(this.getPrefs().getBoolean("plotsShowAdvancedTerms", false));
      } catch (Throwable var6) {
         var10 = Result.Companion;
         var10 = (Collection)KtCompat.resultOf(ResultKt.createFailure(var6));
         break label97;
      }

      Object var3 = var10;
      if (KtCompat.isFailure(var10)) {
         var3 = false;
      }

      this.showAdvanced = (Boolean)var3;
      var10 = this.card((ViewGroup)var1);
      headLine$default(this, (ViewGroup)var10, "Live tuning status", 0, 4, null);
      GxPage.text$default(
         this,
         "Live comparison view for tuning diagnostics. These scores are a quick health check, not a final verdict. Short spikes from bumps, lane changes, traffic transitions, and manual inputs can temporarily lower a score.",
         (ViewGroup)var10,
         0.0F,
         0,
         false,
         28,
         null
      );
      this.plotsStatus = this.box((ViewGroup)var10);
      GxPage.text$default(
         this,
         "Match rating uses a 30-second rolling window. Strong steering or accel moments are preferred, but gentler windows can still earn a rating. Longitudinal also checks how much of the window stays above error limits.",
         (ViewGroup)var10,
         14.0F,
         0,
         false,
         24,
         null
      );
      var10 = this.btnBar((ViewGroup)var10);
      this.bPause = barBtn$default(this, (LinearLayout)var10, "Pause Live", false, new GxTuningPage$$ExternalSyntheticLambda20(this), 4, null);
      barBtn$default(this, (LinearLayout)var10, "Clear History", false, new GxTuningPage$$ExternalSyntheticLambda21(this), 4, null);
      barBtn$default(this, (LinearLayout)var10, "Refresh", false, new GxTuningPage$$ExternalSyntheticLambda23(this), 4, null);
      var3 = this.charts;
      var10 = new ArrayList();

      for (var var3_it : var3) {

         var3 = var3_it;
         if (!((GxTuningPage.ChartCfg)var3).getAdvanced()) {
            var10.add(var3);
         }
      }

      List var15 = (List)var10;
      Iterator var16 = var15.iterator();

      while (var16.hasNext()) {
         this.chartCard(var1, (GxTuningPage.ChartCfg)var16.next());
      }

      this.bAdvanced = this.getUi().button("", false, new GxTuningPage$$ExternalSyntheticLambda24(this));
      var3 = this.bAdvanced;
      TextView var17 = (TextView)var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("bAdvanced");
         var17 = null;
      }

      View var28 = (View)var17;
      LayoutParams var18 = new LayoutParams(-2, -2);
      var18.topMargin = this.getUi().dp(8);
      var18.bottomMargin = this.getUi().dp(10);
      var3 = Unit.INSTANCE;
      var1.addView(var28, (android.view.ViewGroup.LayoutParams)var18);
      this.advancedBox = this.box((ViewGroup)var1);
      Iterable var19 = this.charts;
      var var7 = new ArrayList();

      for (Object var26 : var19) {
         if (((GxTuningPage.ChartCfg)var26).getAdvanced()) {
            var7.add(var26);
         }
      }

      for (GxTuningPage.ChartCfg var29 : var7) {
         LinearLayout var21 = this.advancedBox;
         var1 = var21;
         if (var21 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("advancedBox");
            var1 = null;
         }

         this.chartCard(var1, var29);
      }

      this.paintPlots();
   }

   private static final Unit buildPlots$lambda$138(GxTuningPage var0) {
      var0.togglePaused();
      return Unit.INSTANCE;
   }

   private static final Unit buildPlots$lambda$139(GxTuningPage var0) {
      var0.samples.clear();
      var0.lastTs = 0.0;
      var0.paintPlots();
      var0.toast("Plot history cleared.");
      return Unit.INSTANCE;
   }

   private static final Unit buildPlots$lambda$140(GxTuningPage var0) {
      var0.plotsLoading = true;
      var0.plotsError = "";
      var0.paintPlots();
      var0.plotsLoad(true);
      return Unit.INSTANCE;
   }

   private static final Unit buildPlots$lambda$142(GxTuningPage var0) {
      var0.toggleAdvanced();
      return Unit.INSTANCE;
   }

   private final void buildTesting(LinearLayout var1) {
      this.tgBuilt = true;
      this.header("A/B Tuning Sandbox", (ViewGroup)var1);
      this.note("A/B Tuning Sandbox. If you don't know what this is, you probably shouldn't be here ;)", (ViewGroup)var1);
      this.tgBox = this.box((ViewGroup)var1);
      this.paintTg();
   }

   private final void bullets(ViewGroup var1, List<String> var2, boolean var3) {
      Iterator var8 = var2.iterator();
      int var4 = 0;

      while (true) {
         int var5 = var4;
         if (!var8.hasNext()) {
            return;
         }

         var4++;
         String var7 = (String)var8.next();
         GxPage var9 = this;
         StringBuilder var6;
         String var10;
         if (var3) {
            var6 = new StringBuilder().append(var5 + 1);
            var10 = ". ";
         } else {
            var6 = new StringBuilder();
            var10 = "•  ";
         }

         GxPage.text$default(var9, var6.append(var10).append(var7).toString(), var1, 16.0F, CarUi.Companion.getDIM(), false, 16, null)
            .setPadding(this.getUi().dp(6), this.getUi().dp(3), 0, this.getUi().dp(3));
      }
   }

   // $VF: synthetic method
   static void bullets$default(GxTuningPage var0, ViewGroup var1, List var2, boolean var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = false;
      }

      var0.bullets(var1, var2, var3);
   }

   private final boolean canAnalyze() {
      int var1 = this.selectedSegmentCount();
      boolean var2;
      if (!this.selectedRoutes.isEmpty() && var1 > 0 && var1 <= this.maxSegments && !this.isOnroad && !this.laneCentering) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private final boolean canApplyTrial() {
      boolean var1;
      if (!this.busy && !this.rollbackUnavailable()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private final void chartCard(LinearLayout var1, GxTuningPage.ChartCfg var2) {
      var1 = this.card((ViewGroup)var1);
      headLine$default(this, (ViewGroup)var1, var2.getTitle(), 0, 4, null);
      TextView var3 = GxPage.text$default(this, "", (ViewGroup)var1, 14.0F, 0, false, 24, null);
      LinearLayout var4 = new LinearLayout((Context)this.getAct());
      var4.setOrientation(0);
      var4.setPadding(0, this.getUi().dp(4), 0, this.getUi().dp(8));
      var1.addView((View)var4);
      Context var5 = (Context)this.getAct();
      Iterable var7 = var2.getSeries();
      var var6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var7, 10));

      for (Triple var8 : var7) {
         var6.add(TuplesKt.to(var8.getFirst(), var8.getThird()));
      }

      List var11 = var6;
      GxTuningChartView var12 = new GxTuningChartView(
         var5, var11, var2.getUnit(), var2.getRangeDigits(), var2.getRangeFloor(), var2.getRangeMult(), var2.getRoundDiv(), this.samples
      );
      var1.addView(var12, (android.view.ViewGroup.LayoutParams)(new LayoutParams(-1, this.getUi().dp(220))));
      TextView var10 = this.empty("Waiting for enough live samples...");
      var1.addView((View)var10);
      this.chartViews.add(new GxTuningPage.ChartViews(var2, var3, var4, var12, var10));
   }

   private final List<GxTuningPage.CmpRow> comparisonRows() {
      JSONObject var2 = this.report;
      if (var2 == null) {
         return CollectionsKt.emptyList();
      } else {
         JSONObject var6 = this.obj(var2, "stockParams");
         JSONObject var4 = this.obj(var2, "currentParams");
         JSONObject var3 = this.obj(this.activeTrial, "appliedGenericParams");
         boolean var1 = this.angleControl();
         List var12;
         if (var1) {
            var12 = CollectionsKt.listOf(
               new Pair[]{
                  TuplesKt.to("Auto steer delay", "UseAutoSteerDelay"), TuplesKt.to("Steer delay", "SteerDelay"), TuplesKt.to("Steer ratio", "SteerRatio")
               }
            );
         } else {
            var12 = CollectionsKt.listOf(
               new Pair[]{
                  TuplesKt.to("Lat accel", "SteerLatAccel"),
                  TuplesKt.to("Friction", "SteerFriction"),
                  TuplesKt.to("Auto steer delay", "UseAutoSteerDelay"),
                  TuplesKt.to("Steer delay", "SteerDelay"),
                  TuplesKt.to("Steer ratio", "SteerRatio"),
                  TuplesKt.to("KP", "SteerKP")
               }
            );
         }

         ArrayList var5 = new ArrayList();

         for (Pair var13 : var12) {
            String var7 = (String)var13.component1();
            String var10 = (String)var13.component2();
            Object var9 = var6.opt(var10);
            if (var3.has(var10)) {
               var2 = (JSONObject)var3.opt(var10);
            } else {
               var2 = (JSONObject)var4.opt(var10);
            }

            var5.add(new GxTuningPage.CmpRow(var10, var7, false, var9, var2));
         }

         if (var1) {
            return var5;
         } else {
            JSONObject var28 = this.obj(this.activeTrial, "appliedFrictionThresholds");

            for (Pair var15 : this.entries(this.obj(var6, "FLMBaseFrictionThresholds"))) {
               String var30 = (String)var15.component1();
               var2 = (JSONObject)var15.component2();
               var1 = var2 instanceof JSONObject;
               JSONArray var20 = null;
               if (var1) {
                  var2 = var2;
               } else {
                  var2 = null;
               }

               label60: {
                  if (var2 != null) {
                     JSONArray var22 = var2.optJSONArray("values");
                     var18 = var22;
                     if (var22 != null) {
                        break label60;
                     }
                  }

                  var18 = new JSONArray();
               }

               var4 = var28.optJSONObject(var30);
               if (var4 != null) {
                  var20 = var4.optJSONArray("values");
               }

               String var24 = "friction-" + var30;
               var30 = var30 + " friction threshold";
               if (var20 == null) {
                  var20 = var18;
               }

               var5.add(new GxTuningPage.CmpRow(var24, var30, true, var18, var20));
            }

            var3 = this.obj(var6, "FLMVehicleKnobs");

            for (Pair var26 : this.entries(this.obj(this.activeTrial, "appliedVehicleKnobs"))) {
               String var19 = (String)var26.component1();
               Object var27 = var26.component2();
               if (var3.has(var19)) {
                  var5.add(
                     new GxTuningPage.CmpRow(
                        var19,
                        CollectionsKt.joinToString$default(
                           CollectionsKt.drop(StringsKt.split$default(var19, new String[]{"."}, false, 0, 6, null), 1),
                           ".",
                           null,
                           null,
                           0,
                           null,
                           null,
                           62,
                           null
                        ),
                        false,
                        var3.opt(var19),
                        var27
                     )
                  );
               }
            }

            return var5;
         }
      }
   }

   private final GxTuningPage.Quality computeQuality(GxTuningPage.QualityCfg var1) {
      if (this.samples.size() < 2) {
         return new GxTuningPage.Quality("na", null, "Waiting for data");
      } else {
         int var15 = 8;
         double var4 = CollectionsKt.last(this.samples).getTs();
         double var2;
         if (var4 > 0.0) {
            var2 = var4 - 30.0;
         } else {
            var2 = 0.0;
         }

         Iterable var23 = this.samples;
         var var22 = new ArrayList();
         Iterator var71 = var23.iterator();

         while (true) {
            boolean var21 = var71.hasNext();
            int var14 = 1;
            if (!var21) {
               List var72 = var22;
               Iterable var63 = var72;
               var var25 = new ArrayList();

               for (Object var27 : var63) {
                  GxTuningSample var26 = (GxTuningSample)var27;
                  Double var28 = var26.getV().get("speed");
                  double var6;
                  if (var28 != null) {
                     var6 = var28;
                  } else {
                     var6 = 0.0;
                  }

                  var6 = Math.abs(var6);
                  if ((var1.getMinSpeed() <= 0.0 || var6 >= var1.getMinSpeed())
                     && (var1.getMinDemand() <= 0.0 || computeQuality$mag(var1, var26) >= var1.getMinDemand())) {
                     var14 = (boolean)1;
                  } else {
                     var14 = (boolean)0;
                  }

                  if (var14) {
                     var25.add(var27);
                  }
               }

               List var64 = var25;
               var14 = (boolean)0;
               if (var1.getAllowFallback() && var64.size() < 8 && var72.size() >= 8) {
                  Iterator var76 = var72.iterator();
                  var4 = 0.0;

                  while (var76.hasNext()) {
                     GxTuningSample var81 = (GxTuningSample)var76.next();
                     Double var82 = var81.getV().get("speed");
                     double var44;
                     if (var82 != null) {
                        var44 = var82;
                     } else {
                        var44 = 0.0;
                     }

                     var4 += Math.abs(var44);
                  }

                  List var77 = var64;
                  double var45 = var4 / var72.size();
                  Iterator var65 = var72.iterator();
                  if (!var65.hasNext()) {
                     throw new NoSuchElementException();
                  }

                  var4 = computeQuality$mag(var1, (GxTuningSample)var65.next());

                  while (var65.hasNext()) {
                     var4 = Math.max(var4, computeQuality$mag(var1, (GxTuningSample)var65.next()));
                  }

                  var64 = var64;
                  if (var45 >= Math.max(0.0, var1.getFallbackMinSpeed())) {
                     var64 = var77;
                     if (var4 >= Math.max(0.0, var1.getFallbackMinPeak())) {
                        var64 = var72;
                        var14 = (boolean)1;
                     }
                  }
               }

               if (var64.size() < 8) {
                  var15 = var64.size();
                  String var31;
                  if (var1.getAllowFallback()) {
                     var31 = "eligible";
                  } else {
                     var31 = "signal";
                  }

                  var14 = var72.size();
                  return new GxTuningPage.Quality("na", null, "Need " + 8 + " samples (" + var15 + " " + var31 + " / " + var14 + " total)");
               } else {
                  Iterable var78 = var64;
                  var var86 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var78, 10));

                  for (GxTuningSample var89 : var78) {
                     Double var87 = var89.getV().get(var1.getDesiredKey());
                     if (var87 != null) {
                        var2 = var87;
                     } else {
                        var2 = 0.0;
                     }

                     var87 = var89.getV().get(var1.getActualKey());
                     if (var87 != null) {
                        var4 = var87;
                     } else {
                        var4 = 0.0;
                     }

                     var86.add(Math.abs(var2 - var4));
                  }

                  List var73 = var86;
                  List var79 = CollectionsKt.sorted(var73);
                  if (var79.isEmpty()) {
                     return new GxTuningPage.Quality("na", null, "No eligible samples");
                  } else {
                     double var46 = this.percentile(var79, 0.5) * 0.7 + this.percentile(var79, 0.9) * 0.3;
                     int var16 = var64.size();
                     var15 = (int)30.0;
                     String var66;
                     if (var14) {
                        var66 = ", low-demand fallback";
                     } else {
                        var66 = "";
                     }

                     String var74 = var16 + " samples / " + var15 + "s" + var66;
                     if (var1.getPersistence()) {
                        boolean var49;
                        if (var1.getWarnError() == 0.0) {
                           var49 = 1;
                        } else {
                           var49 = 0;
                        }

                        var4 = 0.35;
                        if (!var49) {
                           var2 = var1.getWarnError();
                        } else {
                           var2 = 0.35;
                        }

                        if (var1.getGood() == 0.0) {
                           var49 = (boolean)1;
                        } else {
                           var49 = (boolean)0;
                        }

                        if (!var49) {
                           var4 = var1.getGood();
                        }

                        double var8 = Math.max(var2, var4);
                        if (var1.getSevereError() == 0.0) {
                           var49 = (boolean)1;
                        } else {
                           var49 = (boolean)0;
                        }

                        if (!var49) {
                           var2 = var1.getSevereError();
                        } else {
                           var2 = 0.7;
                        }

                        if (var1.getFair() == 0.0) {
                           var49 = (boolean)1;
                        } else {
                           var49 = (boolean)0;
                        }

                        if (!var49) {
                           var4 = var1.getFair();
                        } else {
                           var4 = 0.55;
                        }

                        var2 = Math.max(var2, var4);
                        var var67 = var79;
                        if (var67 instanceof Collection && ((Collection)var67).isEmpty()) {
                           var49 = 0;
                        } else {
                           var49 = 0;
                           Iterator var68 = var67.iterator();

                           while (var68.hasNext()) {
                              boolean var58;
                              if (((Number)var68.next()).doubleValue() > var8) {
                                 var58 = 1;
                              } else {
                                 var58 = 0;
                              }

                              if (var58) {
                                 var58 = var49 + 1;
                                 var49 = var58;
                                 if (var58 < 0) {
                                    CollectionsKt.throwCountOverflow();
                                    var49 = var58;
                                 }
                              }
                           }
                        }

                        var4 = var49;
                        var49 = var79.size();
                        String var69 = "poor";
                        var4 /= var49;
                        var var84 = var79;
                        if (var84 instanceof Collection && ((Collection)var84).isEmpty()) {
                           var49 = 0;
                        } else {
                           var49 = 0;
                           Iterator var85 = var84.iterator();

                           while (var85.hasNext()) {
                              boolean var60;
                              if (((Number)var85.next()).doubleValue() > var2) {
                                 var60 = 1;
                              } else {
                                 var60 = 0;
                              }

                              if (var60) {
                                 var60 = var49 + 1;
                                 var49 = var60;
                                 if (var60 < 0) {
                                    CollectionsKt.throwCountOverflow();
                                    var49 = var60;
                                 }
                              }
                           }
                        }

                        double var10 = (double)var49 / var79.size();
                        String var30;
                        if (var46 <= var1.getGreat() && var4 <= 0.18 && var10 <= 0.05) {
                           var30 = "great";
                        } else if (var46 <= var1.getGood() && var4 <= 0.34 && var10 <= 0.12) {
                           var30 = "good";
                        } else if (var46 <= var1.getFair() && var4 <= 0.55 && var10 <= 0.24) {
                           var30 = "fair";
                        } else {
                           var30 = var69;
                        }

                        double var12 = 100;
                        long var19 = Math.round(var4 * var12);
                        String var70 = this.f(var8, 2);
                        long var17 = Math.round(var12 * var10);
                        String var80 = this.f(var2, 2);
                        return new GxTuningPage.Quality(var30, var46, var74 + ", " + var19 + "% > " + var70 + " and " + var17 + "% > " + var80);
                     } else {
                        String var29;
                        if (var46 <= var1.getGreat()) {
                           var29 = "great";
                        } else if (var46 <= var1.getGood()) {
                           var29 = "good";
                        } else if (var46 <= var1.getFair()) {
                           var29 = "fair";
                        } else {
                           var29 = "poor";
                        }

                        return new GxTuningPage.Quality(var29, var46, var74);
                     }
                  }
               }
            }

            Object var24 = var71.next();
            if (!(((GxTuningSample)var24).getTs() >= var2)) {
               var14 = 0;
            }

            if (var14) {
               var22.add(var24);
            }
         }
      }
   }

   private static final double computeQuality$mag(GxTuningPage.QualityCfg var0, GxTuningSample var1) {
      Double var8 = var1.getV().get(var0.getDesiredKey());
      double var4 = 0.0;
      double var2;
      if (var8 != null) {
         var2 = var8;
      } else {
         var2 = 0.0;
      }

      double var6 = Math.abs(var2);
      Double var9 = var1.getV().get(var0.getActualKey());
      var2 = var4;
      if (var9 != null) {
         var2 = var9;
      }

      return Math.max(var6, Math.abs(var2));
   }

   private final String curveStr(JSONArray var1) {
      int var2;
      if (var1 != null) {
         var2 = var1.length();
      } else {
         var2 = 0;
      }

      String var3 = CollectionsKt.joinToString$default(
         RangesKt.until(0, var2), ", ", null, null, 0, null, new GxTuningPage$$ExternalSyntheticLambda54(this, var1), 30, null
      );
      return "[" + var3 + "]";
   }

   private static final CharSequence curveStr$lambda$74(GxTuningPage var0, JSONArray var1, int var2) {
      Intrinsics.checkNotNull(var1);
      return var0.fmtNum3(var1.opt(var2));
   }

   private final String defaultMode(JSONObject var1) {
      Set var2 = this.variantLabels(var1).keySet();
      Intrinsics.checkNotNullExpressionValue(var2, "<get-keys>(...)");
      String var3 = "A";
      if (!var2.contains("A")) {
         String var4 = CollectionsKt.firstOrNull(var2);
         if (var4 != null) {
            var3 = var4;
         }
      }

      return var3;
   }

   private final void deleteReport(JSONObject var1) {
      String var2 = this.s(var1, "reportId");
      if (!StringsKt.isBlank(var2)) {
         this.confirm(
            "Delete Report",
            "Delete this saved tuning report and its generated trial data?",
            "Yes, Delete",
            new GxTuningPage$$ExternalSyntheticLambda14(this, var2)
         );
      }
   }

   private static final Unit deleteReport$lambda$109(GxTuningPage var0, String var1) {
      String var2 = var0.enc(var1);
      var0.runWith("DELETE", "/api/flm/report/" + var2, null, "Report deleted.", new GxTuningPage$$ExternalSyntheticLambda4(var0, var1));
      return Unit.INSTANCE;
   }

   private static final Unit deleteReport$lambda$109$lambda$108(GxTuningPage var0, String var1, JSONObject var2) {
      if (var2 != null && Intrinsics.areEqual(var0.loadedReportId, var1)) {
         var0.report = null;
         var0.loadedReportId = "";
         var0.renderReport();
         var0.paintFlm();
         var0.autoOpenReport();
      }

      return Unit.INSTANCE;
   }

   private final void editRange(String var1, boolean var2) {
      Pair var4 = this.segmentRanges.get(var1);
      Pair var3 = var4;
      if (var4 == null) {
         var3 = TuplesKt.to("", "");
      }

      String var6 = (String)var3.component1();
      String var7 = (String)var3.component2();
      String var8;
      if (var2) {
         var8 = "First segment";
      } else {
         var8 = "Last segment";
      }

      String var9;
      if (var2) {
         var9 = "First";
      } else {
         var9 = "Last";
      }

      String var5;
      if (var2) {
         var5 = var6;
      } else {
         var5 = var7;
      }

      this.prompt(var8, var9, var5, 2, "Leave blank to use the whole route.", new GxTuningPage$$ExternalSyntheticLambda57(this, var1, var2, var7, var6));
   }

   private static final Unit editRange$lambda$135(GxTuningPage var0, String var1, boolean var2, String var3, String var4, String var5) {
      Intrinsics.checkNotNullParameter(var5, "raw");
      CharSequence var8 = var5;
      String var6 = new Regex("[^\\d]").replace(var8, "");
      Map var9 = var0.segmentRanges;
      Pair var7;
      if (var2) {
         var7 = TuplesKt.to(var6, var3);
      } else {
         var7 = TuplesKt.to(var4, var6);
      }

      var9.put(var1, var7);
      var0.renderRoute(var1);
      var0.afterSelectionChange();
      return Unit.INSTANCE;
   }

   private final void en(TextView var1, boolean var2) {
      var1.setEnabled(var2);
      float var3;
      if (var2) {
         var3 = 1.0F;
      } else {
         var3 = 0.4F;
      }

      var1.setAlpha(var3);
   }

   private final List<Pair<String, Object>> entries(JSONObject var1) {
      Iterator var2 = var1.keys();
      Intrinsics.checkNotNullExpressionValue(var2, "keys(...)");
      return SequencesKt.toList(SequencesKt.map(SequencesKt.asSequence(var2), new GxTuningPage$$ExternalSyntheticLambda65(var1)));
   }

   private static final Pair entries$lambda$7(JSONObject var0, String var1) {
      return TuplesKt.to(var1, var0.opt(var1));
   }

   private final String f(double var1, int var3) {
      StringCompanionObject var4 = StringCompanionObject.INSTANCE;
      String var5 = String.format(Locale.US, "%." + var3 + "f", Arrays.copyOf(new Object[]{var1}, 1));
      Intrinsics.checkNotNullExpressionValue(var5, "format(...)");
      return var5;
   }

   private final void flmRefresh(boolean var1, Function0<Unit> var2) {
      if (!var1 || !this.flmFetching && !this.busy) {
         this.flmFetching = true;
         this.getJson("/api/flm/workspace", new GxTuningPage$$ExternalSyntheticLambda56(this, var2));
      }
   }

   // $VF: synthetic method
   static void flmRefresh$default(GxTuningPage var0, boolean var1, Function0 var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = false;
      }

      if ((var3 & 2) != 0) {
         var2 = null;
      }

      var0.flmRefresh(var1, var2);
   }

   private static final Unit flmRefresh$lambda$66(GxTuningPage var0, Function0 var1, String var2, JSONObject var3) {
      if (var3 != null) {
         var0.reports = var0.arr(var3, "reports");
         var0.savedTunes = var0.arr(var3, "savedTunes");
         var0.carFp = var0.s(var3, "currentCarFingerprint");
         var0.activeTrial = var3.optJSONObject("activeTrial");
         JSONObject var5 = var3.optJSONObject("status");
         JSONObject var4 = var5;
         if (var5 == null) {
            var4 = new JSONObject();
         }

         var0.wsStatus = var4;
      }

      var0.getJson("/api/flm/status", new GxTuningPage$$ExternalSyntheticLambda58(var0, var3, var2, var1));
      return Unit.INSTANCE;
   }

   private static final Unit flmRefresh$lambda$66$lambda$65(GxTuningPage var0, JSONObject var1, String var2, Function0 var3, String var4, JSONObject var5) {
      var0.flmFetching = false;
      if (var5 != null) {
         var0.isOnroad = var0.truthy(var5.opt("isOnroad"));
         var0.laneCentering = var0.truthy(var5.opt("laneCentering"));
         var0.statusData = var5.optJSONObject("status");
         JSONArray var6 = var5.optJSONArray("savedTunes");
         if (var6 != null && var6.length() > 0) {
            var0.savedTunes = var6;
         }

         JSONObject var8 = var5.optJSONObject("activeTrial");
         if (var8 != null) {
            var0.activeTrial = var8;
         }

         var6 = var5.optJSONArray("reports");
         if (var6 != null && var6.length() > 0) {
            var0.reports = var6;
         }
      }

      String var7;
      if (var1 != null || var5 != null) {
         var7 = "";
      } else if (var2 == null) {
         if (var4 == null) {
            var7 = "Failed to load tuning workspace.";
         } else {
            var7 = var4;
         }
      } else {
         var7 = var2;
      }

      var0.flmError = var7;
      var0.flmLoading = false;
      var0.paintFlm();
      var0.autoOpenReport();
      if (var3 != null) {
         var3.invoke();
      }

      return Unit.INSTANCE;
   }

   private final String fmtAge(Object var1) {
      double var2 = this.num(var1, Double.NaN);
      if (!Double.isNaN(var2) && !(var2 <= 0.0)) {
         long var4 = Math.max(0L, Math.round(System.currentTimeMillis() / 1000.0 - var2));
         if (var4 < 5L) {
            var1 = "just now";
         } else {
            String var6;
            StringBuilder var8;
            if (var4 < 60L) {
               var8 = new StringBuilder().append(var4);
               var6 = "s ago";
            } else if (var4 < 3600L) {
               var4 = Math.round(var4 / 60.0);
               var8 = new StringBuilder().append(var4);
               var6 = "m ago";
            } else {
               var4 = Math.round(var4 / 3600.0);
               var8 = new StringBuilder().append(var4);
               var6 = "h ago";
            }

            var1 = var8.append(var6).toString();
         }

         return var1;
      } else {
         return "unknown";
      }
   }

   private final String fmtAgeSeconds(Object var1) {
      double var4 = this.num(var1, Double.NaN);
      if (!Double.isNaN(var4) && !(var4 < 0.0)) {
         if (var4 < 1.0) {
            var1 = "just now";
         } else {
            String var8;
            StringBuilder var10;
            if (var4 < 60.0) {
               long var6 = Math.round(var4);
               var10 = new StringBuilder().append(var6);
               var8 = "s ago";
            } else {
               double var2 = 60;
               if (var4 / var2 < 60.0) {
                  long var11 = Math.round(var4 / var2);
                  var10 = new StringBuilder().append(var11);
                  var8 = "m ago";
               } else {
                  long var12 = Math.round(var4 / 3600);
                  var10 = new StringBuilder().append(var12);
                  var8 = "h ago";
               }
            }

            var1 = var10.append(var8).toString();
         }

         return var1;
      } else {
         return "unknown";
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private final String fmtDate(Object var1) {
      if (var1 != null && !Intrinsics.areEqual(var1, JSONObject.NULL) && !StringsKt.isBlank(var1.toString())) {
         Double var6;
         if (var1 instanceof Number) {
            var6 = ((Number)var1).doubleValue();
         } else {
            var6 = StringsKt.toDoubleOrNull(StringsKt.trim(var1.toString()).toString());
         }

         if (var6 != null) {
            double var2 = var6;
            boolean var4;
            if (!Double.isInfinite(var2) && !Double.isNaN(var2)) {
               var4 = true;
            } else {
               var4 = false;
            }

            if (var4) {
               if (var6 <= 0.0) {
                  return "unknown";
               }

               var1 = this.dateFmt;
               if (var6 > 1.0E11) {
                  var2 = var6;
               } else {
                  var2 = var6 * 1000;
               }

               var1 = var1.format(new Date((long)var2));
               Intrinsics.checkNotNullExpressionValue(var1, "format(...)");
               return var1;
            }
         }

         String var7 = var1.toString();

         label213:
         try {
            var1 = Result.Companion;
            var1 = this;
            var1 = KtCompat.resultOf(OffsetDateTime.parse(var7).toInstant().toEpochMilli());
         } catch (Throwable var20) {
            var1 = Result.Companion;
            var1 = KtCompat.resultOf(ResultKt.createFailure(var20));
            break label213;
         }

         boolean var5 = KtCompat.isFailure(var1);
         Object var8 = null;
         if (var5) {
            var1 = null;
         }

         Long var38 = (Long)var1;
         Long var25 = var38;
         if (var38 == null) {
            label207:
            try {
               var1 = Result.Companion;
               var1 = this;
               var1 = KtCompat.resultOf(Instant.parse(var7).toEpochMilli());
            } catch (Throwable var19) {
               var1 = Result.Companion;
               var1 = KtCompat.resultOf(ResultKt.createFailure(var19));
               break label207;
            }

            Object var39 = var1;
            if (KtCompat.isFailure(var1)) {
               var39 = null;
            }

            var39 = (Long)var39;
            var25 = (Long)var39;
            if (var39 == null) {
               label202:
               try {
                  Result.Companion var32 = Result.Companion;
                  var1 = this;
                  var1 = KtCompat.resultOf(LocalDateTime.parse(var7).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
               } catch (Throwable var18) {
                  Result.Companion var30 = Result.Companion;
                  var1 = KtCompat.resultOf(ResultKt.createFailure(var18));
                  break label202;
               }

               if (KtCompat.isFailure(var1)) {
                  var1 = var8;
               }

               var25 = (Long)var1;
            }
         }

         String var34;
         if (var25 != null) {
            var34 = this.dateFmt.format(new Date(var25));
            Intrinsics.checkNotNullExpressionValue(var34, "format(...)");
         } else {
            var34 = var7;
         }

         return var34;
      } else {
         return "unknown";
      }
   }

   private final String fmtLen(JSONObject var1) {
      long var4 = Math.max(0L, Math.round(num$default(this, var1.opt("segmentCount"), 0.0, 2, null)));
      if (var4 == 0L) {
         return "Length unavailable";
      } else {
         long var2 = Math.round(num$default(this, var1.opt("approxDurationSeconds"), 0.0, 2, null) / 60.0);
         if (var2 == 0L) {
            var2 = var4;
         }

         var2 = Math.max(1L, var2);
         String var10;
         StringBuilder var11;
         if (var2 >= 60L) {
            long var8 = 60;
            long var6 = var2 / var8;
            var11 = new StringBuilder().append(var6).append("h ").append(var2 % var8);
            var10 = "m";
         } else {
            var11 = new StringBuilder().append("~").append(var2);
            var10 = " min";
         }

         var10 = var11.append(var10).toString();
         String var12;
         if (var4 == 1L) {
            var12 = "";
         } else {
            var12 = "s";
         }

         return var4 + " segment" + var12 + " (" + var10 + ")";
      }
   }

   private final String fmtNum3(Object var1) {
      double var2 = this.num(var1, Double.NaN);
      if (Double.isNaN(var2)) {
         var1 = "NaN";
      } else {
         var1 = this.f(var2, 3);
      }

      return var1;
   }

   private final String fmtVal(Object var1) {
      String var5;
      if (var1 instanceof JSONArray) {
         var5 = this.curveStr((JSONArray)var1);
      } else if (var1 instanceof Boolean) {
         if ((Boolean)var1) {
            var5 = "On";
         } else {
            var5 = "Off";
         }
      } else if (var1 == null || Intrinsics.areEqual(var1, JSONObject.NULL)) {
         var5 = "-";
      } else if (var1 instanceof Number) {
         double var2 = ((Number)var1).doubleValue();
         boolean var4;
         if (!Double.isInfinite(var2) && !Double.isNaN(var2)) {
            var4 = true;
         } else {
            var4 = false;
         }

         if (var4) {
            var5 = this.f(((Number)var1).doubleValue(), 3);
         } else {
            var5 = ((Number)var1).toString();
         }
      } else if (var1 instanceof String) {
         Double var7;
         if (StringsKt.isBlank((CharSequence)var1)) {
            var7 = 0.0;
         } else {
            var7 = StringsKt.toDoubleOrNull(StringsKt.trim((String & CharSequence)var1).toString());
         }

         if (var7 != null) {
            String var6 = this.f(var7.doubleValue(), 3);
            if (var6 != null) {
               return var6;
            }
         }

         var5 = (String)var1;
      } else {
         var5 = var1.toString();
      }

      return var5;
   }

   private final String formatAge(double var1) {
      var1 = Math.max(0.0, var1);
      String var5;
      StringBuilder var6;
      if (var1 < 1.0) {
         long var3 = Math.round(1000 * var1);
         var6 = new StringBuilder().append(var3);
         var5 = " ms";
      } else {
         var5 = this.f(var1, 1);
         var6 = new StringBuilder().append(var5);
         var5 = " s";
      }

      return var6.append(var5).toString();
   }

   private final SharedPreferences getPrefs() {
      return (SharedPreferences)this.prefs$delegate.getValue();
   }

   private final TextView headBtn(LinearLayout var1, String var2, boolean var3, Function0<Unit> var4) {
      TextView var6 = this.getUi().button(var2, var3, var4);
      View var8 = (View)var6;
      LayoutParams var5 = new LayoutParams(-2, -2);
      var5.leftMargin = this.getUi().dp(8);
      Unit var7 = Unit.INSTANCE;
      var1.addView(var8, (android.view.ViewGroup.LayoutParams)var5);
      return var6;
   }

   // $VF: synthetic method
   static TextView headBtn$default(GxTuningPage var0, LinearLayout var1, String var2, boolean var3, Function0 var4, int var5, Object var6) {
      if ((var5 & 4) != 0) {
         var3 = false;
      }

      return var0.headBtn(var1, var2, var3, var4);
   }

   private final LinearLayout headLine(ViewGroup var1, String var2, int var3) {
      LinearLayout var5 = new LinearLayout((Context)this.getAct());
      var5.setOrientation(0);
      var5.setGravity(16);
      var5.setPadding(0, 0, 0, this.getUi().dp(6));
      TextView var4 = new TextView((Context)this.getAct());
      var4.setText(var2);
      var4.setTextSize(21.0F);
      var4.setTextColor(var3);
      var4.setTypeface(Typeface.DEFAULT_BOLD);
      var5.addView((View)var4, (android.view.ViewGroup.LayoutParams)(new LayoutParams(0, -2, 1.0F)));
      var1.addView((View)var5);
      return var5;
   }

   // $VF: synthetic method
   static LinearLayout headLine$default(GxTuningPage var0, ViewGroup var1, String var2, int var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = CarUi.Companion.getTEXT();
      }

      return var0.headLine(var1, var2, var3);
   }

   private final void history(ViewGroup var1, JSONObject var2, String var3) {
      List var4 = this.strings(this.arr(var2, "history"));
      if (!var4.isEmpty() || var3 != null) {
         this.sub("Progress chain", var1);
         if (var4.isEmpty()) {
            GxPage var5 = this;
            if (var3 == null) {
               var3 = "";
            }

            GxPage.text$default(var5, var3, var1, 0.0F, 0, false, 28, null);
         } else {
            this.bullets((ViewGroup)this.inner(var1), CollectionsKt.reversed(var4), true);
         }
      }
   }

   private final LinearLayout inner(ViewGroup var1) {
      LinearLayout var2 = new LinearLayout((Context)this.getAct());
      var2.setOrientation(1);
      var2.setBackground((Drawable)this.getUi().rounded(CarUi.Companion.getCARD_ON(), 12, Color.rgb(50, 55, 66)));
      var2.setPadding(this.getUi().dp(16), this.getUi().dp(12), this.getUi().dp(16), this.getUi().dp(12));
      View var4 = (View)var2;
      LayoutParams var3 = new LayoutParams(-1, -2);
      var3.topMargin = this.getUi().dp(6);
      var3.bottomMargin = this.getUi().dp(6);
      Unit var5 = Unit.INSTANCE;
      var1.addView(var4, (android.view.ViewGroup.LayoutParams)var3);
      return var2;
   }

   private final boolean isUnused(JSONObject var1) {
      String var3 = StringsKt.trim(this.s(var1, "name")).toString().toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var3, "toLowerCase(...)");
      if (!Intrinsics.areEqual(var3, "unused")) {
         boolean var2 = false;
         if (!StringsKt.startsWith$default(var3, "unused ", false, 2, null)) {
            return var2;
         }
      }

      return true;
   }

   private final void kick(int var1) {
      switch (var1) {
         case 0:
            flmRefresh$default(this, false, null, 3, null);
            break;
         case 1:
            if (!this.plotsPaused) {
               plotsLoad$default(this, false, 1, null);
            }
            break;
         case 2:
            this.tgLoad();
            break;
         default:
            this.manLoad(true);
            this.manLoad(false);
      }
   }

   private final TextView line(ViewGroup var1, String var2, String var3, int var4) {
      LinearLayout var5 = new LinearLayout((Context)this.getAct());
      var5.setOrientation(0);
      var5.setGravity(16);
      var5.setPadding(0, this.getUi().dp(5), 0, this.getUi().dp(5));
      TextView var6 = new TextView((Context)this.getAct());
      var6.setText(var2);
      var6.setTextSize(17.0F);
      var6.setTextColor(CarUi.Companion.getDIM());
      var5.addView((View)var6, (android.view.ViewGroup.LayoutParams)(new LayoutParams(0, -2, 1.0F)));
      TextView var7 = new TextView((Context)this.getAct());
      var7.setText(var3);
      var7.setTextSize(17.0F);
      var7.setTextColor(var4);
      var7.setTypeface(Typeface.DEFAULT_BOLD);
      var7.setGravity(8388613);
      var5.addView((View)var7, (android.view.ViewGroup.LayoutParams)(new LayoutParams(0, -2, 1.0F)));
      var1.addView((View)var5);
      return var7;
   }

   // $VF: synthetic method
   static TextView line$default(GxTuningPage var0, ViewGroup var1, String var2, String var3, int var4, int var5, Object var6) {
      if ((var5 & 8) != 0) {
         var4 = CarUi.Companion.getTEXT();
      }

      return var0.line(var1, var2, var3, var4);
   }

   private final void loadReport(String var1) {
      if (!StringsKt.isBlank(var1) && (!Intrinsics.areEqual(var1, this.loadedReportId) || this.report == null)) {
         this.loadedReportId = var1;
         this.reportLoading = true;
         this.renderReport();
         this.paintReports();
         this.paintProfiles();
         var1 = this.enc(var1);
         this.getJson("/api/flm/report/" + var1, new GxTuningPage$$ExternalSyntheticLambda19(this));
      }
   }

   private static final Unit loadReport$lambda$68(GxTuningPage var0, String var1, JSONObject var2) {
      var0.reportLoading = false;
      if (var2 != null) {
         var0.report = var2;
         var0.syncFeedback(var2);
         var0.flmError = "";
      } else {
         var0.report = null;
         if (var1 == null) {
            var1 = "Failed to load report.";
         }

         var0.toast(var1);
      }

      var0.renderReport();
      var0.paintFlm();
      return Unit.INSTANCE;
   }

   private final void loadRoutes() {
      if (!this.loadingRoutes) {
         this.loadingRoutes = true;
         this.routes.clear();
         this.renderRoutesAll();
         this.call("GET", "/api/routes", null, 300L, new GxTuningPage$$ExternalSyntheticLambda25(this));
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private static final Unit loadRoutes$lambda$122(GxTuningPage var0, int var1, String var2, String var3) {
      Intrinsics.checkNotNullParameter(var2, "text");
      var0.loadingRoutes = false;
      HashSet var6 = new HashSet();
      CharSequence var13 = var2;

      for (var var2_it : new Regex("\\r?\\n\\r?\\n").split(var13, 0)) {

         var2 = var2_it;
         Iterable var15 = new Regex("\\r?\\n").split(var2, 0);
         var var9 = new ArrayList();
         Iterator var8 = var15.iterator();

         while (true) {
            boolean var4 = var8.hasNext();
            Object var5 = null;
            if (!var4) {
               List var31 = var9;
               if (!var31.isEmpty()) {
                  label120:
                  try {
                     Result.Companion var17 = Result.Companion;
                     Iterable var10 = var31;
                     CharSequence var35 = "\n";
                     GxTuningPage$$ExternalSyntheticLambda68 var33 = new GxTuningPage$$ExternalSyntheticLambda68();
                     JSONObject var18 = new JSONObject(CollectionsKt.joinToString$default(var10, var35, null, null, 0, null, var33, 30, null));
                     var16 = KtCompat.resultOf(var18);
                  } catch (Throwable var12) {
                     Result.Companion var32 = Result.Companion;
                     var16 = KtCompat.resultOf(ResultKt.createFailure(var12));
                     break label120;
                  }

                  if (KtCompat.isFailure(var16)) {
                     var16 = var5;
                  }

                  JSONObject var19 = (JSONObject)var16;
                  if (var19 != null) {
                     for (JSONObject var28 : var0.objects(var0.arr(var19, "routes"))) {
                        var2 = var0.s(var28, "name");
                        if (!StringsKt.isBlank(var2) && var6.add(var2) && var0.routes.size() < var0.maxRoutes) {
                           var0.routes.add(var28);
                        }
                     }
                  }
               }
               break;
            }

            var5 = var8.next();
            if (StringsKt.startsWith$default((String)var5, "data:", false, 2, null)) {
               var9.add(var5);
            }
         }
      }

      List var21 = var0.routes;
      if (var21.size() > 1) {
         CollectionsKt.sortWith(var21, new Comparator(var0) {
            final GxTuningPage this$0;

            {
               this.this$0 = var1;
            }

            @Override
            public final int compare(T var1, T var2x) {
               var2x = var2x;
               Comparable var5x = this.this$0.s(var2x, "startedAt");
               var1 = var1;
               return ComparisonsKt.compareValues(var5x, this.this$0.s(var1, "startedAt"));
            }
         });
      }

      if (var3 != null) {
         CharSequence var24 = var3;
         Object var22 = var24;
         if (StringsKt.isBlank(var24)) {
            var22 = "Failed to load routes.";
         }

         var0.toast((String)var22);
      }

      LinkedHashSet var23 = var0.selectedRoutes;
      Iterable var29 = var0.routes;
      var var25 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var29, 10));
      Iterator var30 = var29.iterator();

      while (var30.hasNext()) {
         var25.add(var0.s((JSONObject)var30.next(), "name"));
      }

      List var26 = var25;
      var23.retainAll(CollectionsKt.toSet(var26));
      var0.renderRoutesAll();
      return Unit.INSTANCE;
   }

   private static final CharSequence loadRoutes$lambda$122$lambda$118$lambda$117(String var0) {
      Intrinsics.checkNotNullParameter(var0, "it");
      var0 = var0.substring(5);
      Intrinsics.checkNotNullExpressionValue(var0, "substring(...)");
      return StringsKt.trimStart(var0).toString();
   }

   private final void manLoad(boolean var1) {
      if (this.manBuilt) {
         if (var1) {
            if (this.latFetching) {
               return;
            }

            this.latFetching = true;
         } else {
            if (this.lonFetching) {
               return;
            }

            this.lonFetching = true;
         }

         String var2;
         if (var1) {
            var2 = "/api/lateral_maneuvers/status";
         } else {
            var2 = "/api/longitudinal_maneuvers/status";
         }

         this.getJson(var2, new GxTuningPage$$ExternalSyntheticLambda8(var1, this));
      }
   }

   private static final Unit manLoad$lambda$193(boolean var0, GxTuningPage var1, String var2, JSONObject var3) {
      if (var0) {
         var1.latFetching = false;
         var1.latLoading = false;
         if (var3 != null) {
            var1.latData = var3;
         }

         var1.paintLat();
      } else {
         var1.lonFetching = false;
         var1.lonLoading = false;
         if (var3 != null) {
            var1.lonData = var3;
            var2 = "";
         } else if (var2 == null) {
            var2 = "Failed to load maneuver status";
         }

         var1.lonError = var2;
         var1.paintLon();
      }

      return Unit.INSTANCE;
   }

   private final void manRun(boolean var1, String var2) {
      boolean var3;
      if (var1) {
         var3 = this.latBusy;
      } else {
         var3 = this.lonBusy;
      }

      if (!var3) {
         if (var1) {
            this.latBusy = true;
         } else {
            this.lonBusy = true;
         }

         this.paintLat();
         this.paintLon();
         GxPage var5 = this;
         String var4;
         if (var1) {
            var4 = "lateral";
         } else {
            var4 = "longitudinal";
         }

         GxPage.send$default(
            var5, "POST", "/api/" + var4 + "_maneuvers/" + var2, null, 0L, new GxTuningPage$$ExternalSyntheticLambda67(var1, this, var2), 12, null
         );
      }
   }

   private static final Unit manRun$lambda$197(boolean var0, GxTuningPage var1, String var2, String var3, JSONObject var4) {
      if (var0) {
         var1.latBusy = false;
      } else {
         var1.lonBusy = false;
      }

      if (var3 != null) {
         CharSequence var5 = var3;
         Object var12 = var5;
         if (StringsKt.isBlank(var5)) {
            var12 = "Action failed.";
         }

         var1.toast((String)var12);
         if (!var0) {
            var12 = var3;
            Object var9 = var12;
            if (StringsKt.isBlank((CharSequence)var12)) {
               var9 = "Failed to " + var2 + " maneuvers";
            }

            var1.lonError = (String)var9;
         }
      } else {
         if (var4 != null) {
            if (var0) {
               var1.latData = var4;
            } else {
               var1.lonData = var4;
               var1.lonError = "";
            }
         }

         label40: {
            if (var4 != null) {
               var2 = var4.optString("message");
               if (var2 != null) {
                  CharSequence var10 = var2;
                  CharSequence var7 = var10;
                  if (StringsKt.isBlank(var10)) {
                     var7 = null;
                  }

                  var3 = (String)var7;
                  var2 = var3;
                  if (var3 != null) {
                     break label40;
                  }
               }
            }

            var2 = "Action complete.";
         }

         var1.toast(var2);
         var1.manLoad(var0 ^ true);
      }

      var1.paintLat();
      var1.paintLon();
      return Unit.INSTANCE;
   }

   private final String modeLabel(JSONObject var1, String var2) {
      String var3 = StringsKt.trim(var2).toString().toUpperCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var3, "toUpperCase(...)");
      var2 = this.variantLabels(var1).get(var3);
      String var4 = var2;
      if (var2 == null) {
         CharSequence var7 = var3;
         Object var5 = var7;
         if (StringsKt.isBlank(var7)) {
            var5 = "A";
         }

         var4 = (String)var5;
      }

      return var4;
   }

   private final TextView mono(ViewGroup var1, String var2) {
      TextView var3 = GxPage.text$default(this, var2, var1, 14.0F, Color.rgb(210, 220, 230), false, 16, null);
      var3.setTypeface(Typeface.MONOSPACE);
      return var3;
   }

   private final void notice(ViewGroup var1, String var2, int var3, String var4) {
      LinearLayout var5 = new LinearLayout((Context)this.getAct());
      var5.setOrientation(1);
      var5.setBackground((Drawable)this.getUi().rounded(Color.argb(40, Color.red(var3), Color.green(var3), Color.blue(var3)), 12, var3));
      var5.setPadding(this.getUi().dp(16), this.getUi().dp(10), this.getUi().dp(16), this.getUi().dp(10));
      SpannableStringBuilder var6 = new SpannableStringBuilder();
      if (var4 != null) {
         var6.append(var4).append(" ");
         var6.setSpan(new StyleSpan(1), 0, var4.length(), 33);
      }

      var6.append(var2);
      TextView var7 = new TextView((Context)this.getAct());
      var7.setText((CharSequence)var6);
      var7.setTextSize(15.0F);
      var7.setTextColor(CarUi.Companion.getTEXT());
      var5.addView((View)var7);
      View var8 = (View)var5;
      LayoutParams var10 = new LayoutParams(-1, -2);
      var10.topMargin = this.getUi().dp(4);
      var10.bottomMargin = this.getUi().dp(8);
      Unit var9 = Unit.INSTANCE;
      var1.addView(var8, (android.view.ViewGroup.LayoutParams)var10);
   }

   // $VF: synthetic method
   static void notice$default(GxTuningPage var0, ViewGroup var1, String var2, int var3, String var4, int var5, Object var6) {
      if ((var5 & 4) != 0) {
         var3 = CarUi.Companion.getWARN();
      }

      if ((var5 & 8) != 0) {
         var4 = null;
      }

      var0.notice(var1, var2, var3, var4);
   }

   private final double num(Object var1, double var2) {
      if (var1 != null && !Intrinsics.areEqual(var1, JSONObject.NULL)) {
         double var4;
         if (var1 instanceof Number) {
            var4 = var1.doubleValue();
         } else {
            label45: {
               if (var1 instanceof Boolean) {
                  if ((Boolean)var1) {
                     var4 = 1.0;
                     break label45;
                  }
               } else {
                  if (!(var1 instanceof String)) {
                     return var2;
                  }

                  if (!StringsKt.isBlank((CharSequence)var1)) {
                     var1 = StringsKt.toDoubleOrNull(StringsKt.trim((String & CharSequence)var1).toString());
                     if (var1 == null) {
                        return var2;
                     }

                     var4 = var1;
                     break label45;
                  }
               }

               var4 = 0.0;
            }
         }

         boolean var6;
         if (!Double.isInfinite(var4) && !Double.isNaN(var4)) {
            var6 = true;
         } else {
            var6 = false;
         }

         if (var6) {
            var2 = var4;
         }

         return var2;
      } else {
         return var2;
      }
   }

   // $VF: synthetic method
   static double num$default(GxTuningPage var0, Object var1, double var2, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 0.0;
      }

      return var0.num(var1, var2);
   }

   private final JSONObject obj(JSONObject var1, String var2) {
      if (var1 != null) {
         JSONObject var4 = var1.optJSONObject(var2);
         if (var4 != null) {
            return var4;
         }
      }

      return new JSONObject();
   }

   private final void paintActions() {
      if (this.flmBuilt) {
         JSONObject var7 = this.status();
         TextView var6 = this.bAnalyze;
         Object var5 = null;
         TextView var4 = var6;
         if (var6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bAnalyze");
            var4 = null;
         }

         boolean var2 = this.busy;
         boolean var3 = false;
         if (!var2 && this.canAnalyze()) {
            var2 = true;
         } else {
            var2 = false;
         }

         this.en(var4, var2);
         var6 = this.bStop;
         var4 = var6;
         if (var6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bStop");
            var4 = null;
         }

         if (!this.busy && this.truthy(var7.opt("running"))) {
            var2 = true;
         } else {
            var2 = false;
         }

         this.en(var4, var2);
         var6 = this.bRevert;
         var4 = var6;
         if (var6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bRevert");
            var4 = null;
         }

         if (!this.busy && this.activeTrial != null && !this.rollbackUnavailable()) {
            var2 = true;
         } else {
            var2 = false;
         }

         this.en(var4, var2);
         var6 = this.bKeep;
         var4 = var6;
         if (var6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bKeep");
            var4 = null;
         }

         byte var1;
         if (this.rollbackUnavailable()) {
            var1 = 0;
         } else {
            var1 = 8;
         }

         var4.setVisibility(var1);
         var6 = this.bKeep;
         var4 = var6;
         if (var6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bKeep");
            var4 = null;
         }

         this.en(var4, this.busy ^ true);
         var6 = this.bSave;
         var4 = var6;
         if (var6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bSave");
            var4 = null;
         }

         if (!this.busy && this.activeTrial != null) {
            var2 = true;
         } else {
            var2 = false;
         }

         this.en(var4, var2);
         var6 = this.bRefresh;
         var4 = var6;
         if (var6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bRefresh");
            var4 = null;
         }

         this.en(var4, this.busy ^ true);
         var4 = this.bSaveCurrent;
         if (var4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bSaveCurrent");
            var4 = (TextView)var5;
         }

         var2 = var3;
         if (!this.busy) {
            var2 = var3;
            if (this.activeTrial != null) {
               var2 = true;
            }
         }

         this.en(var4, var2);
      }
   }

   private final void paintComparison() {
      LinearLayout var4 = this.comparisonBox;
      if (var4 != null) {
         var4.removeAllViews();
         List var3 = this.comparisonRows();
         if (!var3.isEmpty()) {
            String var2;
            if (this.angleControl()) {
               var2 = "Applicable Angle Settings";
            } else {
               var2 = "Stock vs Current FLM";
            }

            this.sub(var2, (ViewGroup)var4);
            if (!this.angleControl()) {
               GxPage.text$default(this, "Active trial values are shown in the current column when one is applied.", (ViewGroup)var4, 0.0F, 0, false, 28, null);
            }

            for (GxTuningPage.CmpRow var8 : var3) {
               LinearLayout var6 = new LinearLayout((Context)this.getAct());
               var6.setOrientation(0);
               var6.setGravity(16);
               var6.setPadding(0, this.getUi().dp(6), 0, this.getUi().dp(6));
               TextView var9 = new TextView((Context)this.getAct());
               var9.setText(var8.getLabel());
               var9.setTextSize(17.0F);
               var9.setTextColor(CarUi.Companion.getDIM());
               var6.addView((View)var9, (android.view.ViewGroup.LayoutParams)(new LayoutParams(0, -2, 1.0F)));
               LinearLayout var7 = new LinearLayout((Context)this.getAct());
               var7.setOrientation(1);
               var7.setGravity(8388613);
               boolean var1 = var8.getCurve();
               JSONArray var14 = null;
               if (var1) {
                  var2 = (String)var8.getStock();
                  JSONArray var11;
                  if (var2 instanceof JSONArray) {
                     var11 = (JSONArray)var2;
                  } else {
                     var11 = null;
                  }

                  var2 = this.curveStr(var11);
               } else {
                  var2 = this.fmtVal(var8.getStock());
               }

               String var15;
               if (var8.getCurve()) {
                  Object var16 = var8.getCurrent();
                  if (var16 instanceof JSONArray) {
                     var14 = (JSONArray)var16;
                  }

                  var15 = this.curveStr(var14);
               } else {
                  var15 = this.fmtVal(var8.getCurrent());
               }

               TextView var17 = new TextView((Context)this.getAct());
               var17.setText("stock " + var2);
               var17.setTextSize(14.0F);
               var17.setTextColor(CarUi.Companion.getDIM());
               var17.setGravity(8388613);
               var7.addView((View)var17);
               TextView var13 = new TextView((Context)this.getAct());
               var13.setText(var15);
               var13.setTextSize(17.0F);
               var13.setTextColor(CarUi.Companion.getACCENT());
               var13.setTypeface(Typeface.DEFAULT_BOLD);
               var13.setGravity(8388613);
               var7.addView((View)var13);
               var6.addView((View)var7, (android.view.ViewGroup.LayoutParams)(new LayoutParams(0, -2, 1.4F)));
               var4.addView((View)var6);
            }
         }
      }
   }

   private final void paintFlm() {
      if (this.flmBuilt) {
         this.paintNotices();
         this.paintActions();
         this.paintStatus();
         this.paintTunes();
         this.paintReports();
         this.paintComparison();
         this.paintProfiles();
      }
   }

   private final void paintLat() {
      if (this.manBuilt) {
         Pair var9 = this.latBtns;
         Object var7 = null;
         Object var8 = null;
         Pair var6 = var9;
         if (var9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("latBtns");
            var6 = null;
         }

         this.en((TextView)var6.getFirst(), this.latBusy ^ true);
         var9 = this.latBtns;
         var6 = var9;
         if (var9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("latBtns");
            var6 = null;
         }

         this.en((TextView)var6.getSecond(), this.latBusy ^ true);
         LinearLayout var60 = this.latBox;
         LinearLayout var15 = var60;
         if (var60 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("latBox");
            var15 = null;
         }

         var15.removeAllViews();
         if (this.latLoading) {
            var7 = this;
            LinearLayout var41 = this.latBox;
            if (var41 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("latBox");
               var41 = (LinearLayout)var8;
            }

            GxPage.text$default((GxPage)var7, "Loading status...", (ViewGroup)var41, 0.0F, 0, false, 28, null);
         } else {
            JSONObject var10 = this.latData;
            if (var10 != null) {
               var8 = this.latBox;
               LinearLayout var16 = (LinearLayout)var8;
               if (var8 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("latBox");
                  var16 = null;
               }

               var8 = (ViewGroup)var16;
               String var17;
               if (this.truthy(var10.opt("modeEnabled"))) {
                  var17 = "Yes";
               } else {
                  var17 = "No";
               }

               line$default(this, (ViewGroup)var8, "Mode Enabled", var17, 0, 8, null);
               var8 = this.latBox;
               LinearLayout var18 = (LinearLayout)var8;
               if (var8 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("latBox");
                  var18 = null;
               }

               ViewGroup var61 = (ViewGroup)var18;
               var8 = this.s(var10, "state");
               var6 = (Pair)var8;
               if (StringsKt.isBlank((CharSequence)var8)) {
                  var6 = "idle";
               }

               line$default(this, var61, "State", (String)var6, 0, 8, null);
               var8 = this.latBox;
               LinearLayout var20 = (LinearLayout)var8;
               if (var8 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("latBox");
                  var20 = null;
               }

               var8 = (ViewGroup)var20;
               String var21;
               if (this.truthy(var10.opt("isOnroad"))) {
                  var21 = "Yes";
               } else {
                  var21 = "No";
               }

               line$default(this, (ViewGroup)var8, "Onroad", var21, 0, 8, null);
               var8 = this.latBox;
               LinearLayout var22 = (LinearLayout)var8;
               if (var8 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("latBox");
                  var22 = null;
               }

               var8 = (ViewGroup)var22;
               String var23;
               if (this.truthy(var10.opt("isEngaged"))) {
                  var23 = "Yes";
               } else {
                  var23 = "No";
               }

               line$default(this, (ViewGroup)var8, "Engaged", var23, 0, 8, null);
               var8 = this.latBox;
               LinearLayout var24 = (LinearLayout)var8;
               if (var8 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("latBox");
                  var24 = null;
               }

               ViewGroup var62 = (ViewGroup)var24;
               var var25 = this.s(var10, "phase");
               boolean var5 = StringsKt.isBlank(var25);
               var8 = "n/a";
               if (var5) {
                  var25 = "n/a";
               }

               line$default(this, var62, "Phase", var25, 0, 8, null);
               LinearLayout var63 = this.latBox;
               LinearLayout var26 = var63;
               if (var63 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("latBox");
                  var26 = null;
               }

               ViewGroup var27 = (ViewGroup)var26;
               long var1 = (long)num$default(this, var10.opt("stepIndex"), 0.0, 2, null);
               long var3 = (long)num$default(this, var10.opt("stepTotal"), 0.0, 2, null);
               line$default(this, var27, "Step", var1 + " / " + var3, 0, 8, null);
               LinearLayout var64 = this.latBox;
               LinearLayout var28 = var64;
               if (var64 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("latBox");
                  var28 = null;
               }

               ViewGroup var29 = (ViewGroup)var28;
               var1 = (long)num$default(this, var10.opt("runIndex"), 0.0, 2, null);
               var3 = (long)num$default(this, var10.opt("runTotal"), 0.0, 2, null);
               line$default(this, var29, "Run", var1 + " / " + var3, 0, 8, null);
               LinearLayout var65 = this.latBox;
               LinearLayout var30 = var65;
               if (var65 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("latBox");
                  var30 = null;
               }

               line$default(this, (ViewGroup)var30, "Updated", this.fmtAgeSeconds(var10.opt("updatedAgeSec")), 0, 8, null);
               LinearLayout var66 = this.latBox;
               LinearLayout var31 = var66;
               if (var66 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("latBox");
                  var31 = null;
               }

               this.sub("Current maneuver", (ViewGroup)var31);
               var var32 = this.s(var10, "maneuver");
               if (StringsKt.isBlank(var32)) {
                  var32 = (String)var8;
               }

               String var67 = var32;
               var8 = this.latBox;
               LinearLayout var33 = (LinearLayout)var8;
               if (var8 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("latBox");
                  var33 = null;
               }

               this.text(var67, (ViewGroup)var33, 18.0F, CarUi.Companion.getTEXT(), true);
               var8 = this.s(var10, "uiText1");
               String var34 = this.s(var10, "uiText2");
               String var35;
               if (!StringsKt.isBlank(var34)) {
                  var35 = " | " + var34;
               } else {
                  var35 = "";
               }

               String var68 = var8 + var35;
               if (!StringsKt.isBlank(var68)) {
                  GxPage var11 = this;
                  var8 = this.latBox;
                  LinearLayout var36 = (LinearLayout)var8;
                  if (var8 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("latBox");
                     var36 = null;
                  }

                  GxPage.text$default(var11, var68, (ViewGroup)var36, 0.0F, 0, false, 28, null);
               }

               var8 = this.latBox;
               LinearLayout var37 = (LinearLayout)var8;
               if (var8 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("latBox");
                  var37 = null;
               }

               this.sub("Quick guide", (ViewGroup)var37);
               var8 = this.latBox;
               LinearLayout var38 = (LinearLayout)var8;
               if (var8 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("latBox");
                  var38 = null;
               }

               ViewGroup var39 = (ViewGroup)var38;
               var8 = CollectionsKt.listOf(
                  new String[]{
                     "Find a large, empty, straight, flat road or lot with no traffic.",
                     "Press Start / Arm here, then engage openpilot and set the requested speed manually.",
                     "Keep full supervision and be ready to steer or disengage immediately at all times.",
                     "Do not touch the wheel during a run. Steering input or speed drift can reset the current maneuver.",
                     "When the status says complete, collect logs and generate the HTML report if needed."
                  }
               );
               this.bullets(var39, (List<String>)var8, true);
               LinearLayout var40 = this.latBox;
               if (var40 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("latBox");
                  var40 = (LinearLayout)var7;
               }

               this.history((ViewGroup)var40, var10, "No steps logged yet.");
            }
         }
      }
   }

   private final void paintLon() {
      if (this.manBuilt) {
         Pair var7 = this.lonBtns;
         Pair var6 = var7;
         if (var7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("lonBtns");
            var6 = null;
         }

         this.en((TextView)var6.getFirst(), this.lonBusy ^ true);
         var7 = this.lonBtns;
         var6 = var7;
         if (var7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("lonBtns");
            var6 = null;
         }

         this.en((TextView)var6.getSecond(), this.lonBusy ^ true);
         LinearLayout var26 = this.lonBox;
         LinearLayout var15 = var26;
         if (var26 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("lonBox");
            var15 = null;
         }

         var15.removeAllViews();
         if (!StringsKt.isBlank(this.lonError)) {
            LinearLayout var27 = this.lonBox;
            LinearLayout var16 = var27;
            if (var27 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("lonBox");
               var16 = null;
            }

            notice$default(this, (ViewGroup)var16, this.lonError, 0, null, 12, null);
         }

         if (this.lonLoading) {
            GxPage var67 = this;
            LinearLayout var24 = this.lonBox;
            if (var24 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("lonBox");
               var24 = null;
            }

            GxPage.text$default(var67, "Loading status...", (ViewGroup)var24, 0.0F, 0, false, 28, null);
         } else {
            JSONObject var10 = this.lonData;
            if (var10 != null) {
               JSONObject var28 = var10.optJSONObject("support");
               JSONObject var17 = var28;
               if (var28 == null) {
                  var17 = new JSONObject();
               }

               LinearLayout var8 = this.lonBox;
               LinearLayout var29 = var8;
               if (var8 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                  var29 = null;
               }

               this.sub("Live status", (ViewGroup)var29);
               var8 = this.lonBox;
               LinearLayout var30 = var8;
               if (var8 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                  var30 = null;
               }

               ViewGroup var69 = (ViewGroup)var30;
               String var31;
               if (this.truthy(var10.opt("modeEnabled"))) {
                  var31 = "Yes";
               } else {
                  var31 = "No";
               }

               line$default(this, var69, "Mode Enabled", var31, 0, 8, null);
               var8 = this.lonBox;
               LinearLayout var32 = var8;
               if (var8 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                  var32 = null;
               }

               ViewGroup var9 = (ViewGroup)var32;
               CharSequence var71 = this.s(var10, "state");
               var7 = var71;
               if (StringsKt.isBlank(var71)) {
                  var7 = "idle";
               }

               line$default(this, var9, "State", (String)var7, 0, 8, null);
               var8 = this.lonBox;
               LinearLayout var34 = var8;
               if (var8 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                  var34 = null;
               }

               ViewGroup var73 = (ViewGroup)var34;
               String var35;
               if (this.truthy(var10.opt("isOnroad"))) {
                  var35 = "Yes";
               } else {
                  var35 = "No";
               }

               line$default(this, var73, "Onroad", var35, 0, 8, null);
               var8 = this.lonBox;
               LinearLayout var36 = var8;
               if (var8 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                  var36 = null;
               }

               ViewGroup var75 = (ViewGroup)var36;
               String var37;
               if (this.truthy(var10.opt("isEngaged"))) {
                  var37 = "Yes";
               } else {
                  var37 = "No";
               }

               line$default(this, var75, "Engaged", var37, 0, 8, null);
               var8 = this.lonBox;
               LinearLayout var38 = var8;
               if (var8 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                  var38 = null;
               }

               var9 = (ViewGroup)var38;
               var var39 = this.s(var10, "phase");
               boolean var5 = StringsKt.isBlank(var39);
               String var77 = "n/a";
               if (var5) {
                  var39 = "n/a";
               }

               line$default(this, var9, "Phase", var39, 0, 8, null);
               LinearLayout var92 = this.lonBox;
               LinearLayout var40 = var92;
               if (var92 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                  var40 = null;
               }

               ViewGroup var11 = (ViewGroup)var40;
               CharSequence var93 = this.s(var10, "paddleMode");
               var7 = var93;
               if (StringsKt.isBlank(var93)) {
                  var7 = "auto";
               }

               line$default(this, var11, "Paddle Mode", (String)var7, 0, 8, null);
               LinearLayout var94 = this.lonBox;
               LinearLayout var42 = var94;
               if (var94 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                  var42 = null;
               }

               ViewGroup var43 = (ViewGroup)var42;
               long var3 = (long)num$default(this, var10.opt("stepIndex"), 0.0, 2, null);
               long var1 = (long)num$default(this, var10.opt("stepTotal"), 0.0, 2, null);
               line$default(this, var43, "Step", var3 + " / " + var1, 0, 8, null);
               LinearLayout var95 = this.lonBox;
               LinearLayout var44 = var95;
               if (var95 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                  var44 = null;
               }

               ViewGroup var45 = (ViewGroup)var44;
               var3 = (long)num$default(this, var10.opt("runIndex"), 0.0, 2, null);
               var1 = (long)num$default(this, var10.opt("runTotal"), 0.0, 2, null);
               line$default(this, var45, "Run", var3 + " / " + var1, 0, 8, null);
               LinearLayout var96 = this.lonBox;
               LinearLayout var46 = var96;
               if (var96 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                  var46 = null;
               }

               line$default(this, (ViewGroup)var46, "Updated", this.fmtAgeSeconds(var10.opt("updatedAgeSec")), 0, 8, null);
               LinearLayout var97 = this.lonBox;
               LinearLayout var47 = var97;
               if (var97 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                  var47 = null;
               }

               this.sub("Current maneuver", (ViewGroup)var47);
               var var48 = this.s(var10, "maneuver");
               if (StringsKt.isBlank(var48)) {
                  var48 = var77;
               }

               String var98 = var48;
               var8 = this.lonBox;
               LinearLayout var49 = var8;
               if (var8 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                  var49 = null;
               }

               this.text(var98, (ViewGroup)var49, 18.0F, CarUi.Companion.getTEXT(), true);
               String var79 = this.s(var10, "uiText1");
               String var50 = this.s(var10, "uiText2");
               String var51;
               if (!StringsKt.isBlank(var50)) {
                  var51 = " | " + var50;
               } else {
                  var51 = "";
               }

               String var99 = var79 + var51;
               if (!StringsKt.isBlank(var99)) {
                  GxPage var100 = this;
                  var8 = this.lonBox;
                  LinearLayout var52 = var8;
                  if (var8 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                     var52 = null;
                  }

                  GxPage.text$default(var100, var99, (ViewGroup)var52, 0.0F, 0, false, 28, null);
               }

               var8 = this.lonBox;
               LinearLayout var53 = var8;
               if (var8 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                  var53 = null;
               }

               this.sub("Longitudinal support", (ViewGroup)var53);
               var8 = this.lonBox;
               LinearLayout var54 = var8;
               if (var8 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                  var54 = null;
               }

               line$default(this, (ViewGroup)var54, "openpilot Longitudinal", paintLon$yes(this, var17, "openpilotLongitudinalControl"), 0, 8, null);
               var8 = this.lonBox;
               LinearLayout var55 = var8;
               if (var8 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                  var55 = null;
               }

               line$default(this, (ViewGroup)var55, "Full Stop + Go", paintLon$yes(this, var17, "fullStopAndGo"), 0, 8, null);
               var8 = this.lonBox;
               LinearLayout var56 = var8;
               if (var8 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                  var56 = null;
               }

               line$default(this, (ViewGroup)var56, "Auto Resume from Stop", paintLon$yes(this, var17, "autoResumeFromStop"), 0, 8, null);
               var8 = this.lonBox;
               LinearLayout var57 = var8;
               if (var8 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                  var57 = null;
               }

               line$default(this, (ViewGroup)var57, "Requires Resume Assist", paintLon$yes(this, var17, "requiresResumeAssist"), 0, 8, null);
               var8 = this.lonBox;
               LinearLayout var58 = var8;
               if (var8 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                  var58 = null;
               }

               line$default(this, (ViewGroup)var58, "Expected to Reach Zero", paintLon$yes(this, var17, "expectedToReachZero"), 0, 8, null);
               var8 = this.lonBox;
               LinearLayout var59 = var8;
               if (var8 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                  var59 = null;
               }

               line$default(this, (ViewGroup)var59, "Minimum Enable Speed", paintLon$number(this, var17, "minEnableSpeed", "m/s"), 0, 8, null);
               var8 = this.lonBox;
               LinearLayout var60 = var8;
               if (var8 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                  var60 = null;
               }

               line$default(this, (ViewGroup)var60, "Stop Acceleration", paintLon$number(this, var17, "stopAccel", "m/s²"), 0, 8, null);
               List var89 = this.strings(this.arr(var10, "caveats"));
               if (!var89.isEmpty()) {
                  LinearLayout var61 = this.lonBox;
                  LinearLayout var18 = var61;
                  if (var61 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                     var18 = null;
                  }

                  notice$default(
                     this,
                     (ViewGroup)var18,
                     CollectionsKt.joinToString$default(var89, "\n", null, null, 0, null, new GxTuningPage$$ExternalSyntheticLambda43(), 30, null),
                     0,
                     "Platform caveats\n",
                     4,
                     null
                  );
               }

               List var90 = this.strings(this.arr(var10, "skippedManeuvers"));
               if (!var90.isEmpty()) {
                  LinearLayout var62 = this.lonBox;
                  LinearLayout var19 = var62;
                  if (var62 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                     var19 = null;
                  }

                  this.notice(
                     (ViewGroup)var19,
                     CollectionsKt.joinToString$default(var90, "\n", null, null, 0, null, new GxTuningPage$$ExternalSyntheticLambda45(), 30, null),
                     Color.rgb(94, 200, 200),
                     "Expected skips\n"
                  );
               }

               LinearLayout var63 = this.lonBox;
               LinearLayout var20 = var63;
               if (var63 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                  var20 = null;
               }

               this.sub("Quick guide", (ViewGroup)var20);
               LinearLayout var64 = this.lonBox;
               LinearLayout var21 = var64;
               if (var64 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                  var21 = null;
               }

               ViewGroup var65 = (ViewGroup)var21;
               List var22 = CollectionsKt.listOf(
                  new String[]{
                     "Find a large, empty, straight road or lot with no traffic.",
                     "Press Start / Arm, then engage openpilot with SET.",
                     "Keep full supervision and be ready to disengage at all times.",
                     "Review the platform caveats first. Low-speed maneuvers can be skipped automatically on cars that do not fully stop.",
                     "When the status says complete, collect the logs and generate your HTML report."
                  }
               );
               this.bullets(var65, var22, true);
               LinearLayout var66 = this.lonBox;
               LinearLayout var23 = var66;
               if (var66 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                  var23 = null;
               }

               this.history((ViewGroup)var23, var10, null);
            }
         }
      }
   }

   private static final CharSequence paintLon$lambda$207(String var0) {
      Intrinsics.checkNotNullParameter(var0, "it");
      return "•  " + var0;
   }

   private static final CharSequence paintLon$lambda$208(String var0) {
      Intrinsics.checkNotNullParameter(var0, "it");
      return "•  " + var0;
   }

   private static final String paintLon$number(GxTuningPage var0, JSONObject var1, String var2, String var3) {
      double var4 = var0.num(var1.opt(var2), Double.NaN);
      String var6;
      if (Double.isNaN(var4)) {
         var6 = "n/a";
      } else {
         String var7 = var0.f(var4, 2);
         var6 = var7 + " " + var3;
      }

      return var6;
   }

   private static final String paintLon$yes(GxTuningPage var0, JSONObject var1, String var2) {
      String var3;
      if (var0.truthy(var1.opt(var2))) {
         var3 = "Yes";
      } else {
         var3 = "No";
      }

      return var3;
   }

   private final void paintNotices() {
      LinearLayout var3 = this.flmNotices;
      Object var2 = null;
      LinearLayout var1 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("flmNotices");
         var1 = null;
      }

      var1.removeAllViews();
      if (this.laneCentering) {
         var3 = this.flmNotices;
         var1 = var3;
         if (var3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("flmNotices");
            var1 = null;
         }

         notice$default(
            this,
            (ViewGroup)var1,
            "Turn Lane Centering off. FLM must analyze the model's unmodified lateral request; routes recorded with Lane Centering enabled are excluded.",
            0,
            "Before using FLM:",
            4,
            null
         );
      }

      if (this.isOnroad) {
         var3 = this.flmNotices;
         var1 = var3;
         if (var3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("flmNotices");
            var1 = null;
         }

         notice$default(this, (ViewGroup)var1, "FLM analysis is offroad-only. Stop the car and go offroad before starting a run.", 0, null, 12, null);
      }

      if (this.rollbackUnavailable()) {
         var3 = this.flmNotices;
         var1 = var3;
         if (var3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("flmNotices");
            var1 = null;
         }

         notice$default(
            this,
            (ViewGroup)var1,
            "The original rollback data is unavailable. Keep the current tune as the new baseline before applying another trial.",
            0,
            null,
            12,
            null
         );
      }

      if (!StringsKt.isBlank(this.flmError)) {
         var3 = this.flmNotices;
         var1 = var3;
         if (var3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("flmNotices");
            var1 = null;
         }

         notice$default(this, (ViewGroup)var1, this.flmError, Color.rgb(224, 85, 119), null, 8, null);
      }

      if (this.flmLoading) {
         GxPage var13 = this;
         var1 = this.flmNotices;
         if (var1 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("flmNotices");
            var1 = (LinearLayout)var2;
         }

         GxPage.text$default(var13, "Loading tuning workspace...", (ViewGroup)var1, 0.0F, 0, false, 28, null);
      }
   }

   private final void paintPlots() {
      if (this.plotsBuilt) {
         JSONObject var6 = this.live;
         JSONObject var7 = var6;
         if (var6 == null) {
            var7 = new JSONObject();
         }

         LinearLayout var8 = this.plotsStatus;
         GxTuningPage.ChartViews var9 = null;
         LinearLayout var19 = var8;
         if (var8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("plotsStatus");
            var19 = null;
         }

         var19.removeAllViews();
         if (this.plotsLoading) {
            GxPage var10 = this;
            var8 = this.plotsStatus;
            LinearLayout var20 = var8;
            if (var8 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("plotsStatus");
               var20 = null;
            }

            GxPage.text$default(var10, "Loading live data...", (ViewGroup)var20, 0.0F, 0, false, 28, null);
         }

         if (!StringsKt.isBlank(this.plotsError)) {
            var8 = this.plotsStatus;
            LinearLayout var21 = var8;
            if (var8 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("plotsStatus");
               var21 = null;
            }

            notice$default(this, (ViewGroup)var21, this.plotsError, 0, "Error:", 4, null);
         }

         if (!this.plotsLoading || this.live != null) {
            var8 = this.plotsStatus;
            LinearLayout var22 = var8;
            if (var8 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("plotsStatus");
               var22 = null;
            }

            ViewGroup var45 = (ViewGroup)var22;
            String var23;
            if (this.truthy(var7.opt("isOnroad"))) {
               var23 = "Yes";
            } else {
               var23 = "No";
            }

            line$default(this, var45, "Onroad", var23, 0, 8, null);
            var8 = this.plotsStatus;
            LinearLayout var24 = var8;
            if (var8 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("plotsStatus");
               var24 = null;
            }

            line$default(this, (ViewGroup)var24, "Sample Age", this.formatAge(num$default(this, var7.opt("sampleAgeSeconds"), 0.0, 2, null)), 0, 8, null);
            var8 = this.plotsStatus;
            LinearLayout var25 = var8;
            if (var8 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("plotsStatus");
               var25 = null;
            }

            ViewGroup var26 = (ViewGroup)var25;
            String var48 = this.f(num$default(this, var7.opt("speed"), 0.0, 2, null), 2);
            line$default(this, var26, "Vehicle Speed", var48 + " m/s", 0, 8, null);
            var8 = this.plotsStatus;
            LinearLayout var27 = var8;
            if (var8 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("plotsStatus");
               var27 = null;
            }

            line$default(this, (ViewGroup)var27, "Samples", String.valueOf(this.samples.size()), 0, 8, null);
            if (this.truthy(var7.opt("bootStabilizing"))) {
               var8 = this.plotsStatus;
               LinearLayout var28 = var8;
               if (var8 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("plotsStatus");
                  var28 = null;
               }

               notice$default(this, (ViewGroup)var28, "Plots are warming up after startup.", 0, "Boot stabilizing", 4, null);
            }

            if (!StringsKt.isBlank(this.s(var7, "lastError"))) {
               var8 = this.plotsStatus;
               LinearLayout var29 = var8;
               if (var8 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("plotsStatus");
                  var29 = null;
               }

               notice$default(this, (ViewGroup)var29, this.s(var7, "lastError"), 0, "Source Error:", 4, null);
            }

            for (Pair var30 : CollectionsKt.listOf(
               new Pair[]{
                  TuplesKt.to("Your lateral tuning is", this.computeQuality(this.latQ)),
                  TuplesKt.to("Your longitudinal tuning is", this.computeQuality(this.lonQ))
               }
            )) {
               String var12 = (String)var30.component1();
               GxTuningPage.Quality var64 = (GxTuningPage.Quality)var30.component2();
               String var31;
               if (Intrinsics.areEqual(var64.getLabel(), "na")) {
                  var31 = "N/A";
               } else {
                  String var52 = var64.getLabel();
                  boolean var4;
                  if (var52.length() > 0) {
                     var4 = true;
                  } else {
                     var4 = false;
                  }

                  var31 = var52;
                  if (var4) {
                     StringBuilder var32 = new StringBuilder();
                     char var1 = var52.charAt(0);
                     String var13 = String.valueOf(var1);
                     Intrinsics.checkNotNull(var13, "null cannot be cast to non-null type java.lang.String");
                     var13 = var13.toUpperCase(Locale.ROOT);
                     Intrinsics.checkNotNullExpressionValue(var13, "toUpperCase(...)");
                     StringBuilder var33 = var32.append(var13);
                     String var53 = var52.substring(1);
                     Intrinsics.checkNotNullExpressionValue(var53, "substring(...)");
                     var31 = var33.append(var53).toString();
                  }
               }

               SpannableStringBuilder var73 = new SpannableStringBuilder(var12 + " ");
               int var14 = var73.length();
               var73.append(var31);
               var73.setSpan(new ForegroundColorSpan(this.toneColor(var64.getLabel())), var14, var73.length(), 33);
               var8 = this.plotsStatus;
               LinearLayout var34 = var8;
               if (var8 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("plotsStatus");
                  var34 = null;
               }

               TextView var35 = this.text("", (ViewGroup)var34, 18.0F, CarUi.Companion.getTEXT(), true);
               var35.setText((CharSequence)var73);
               var35.setPadding(0, this.getUi().dp(10), 0, 0);
               GxPage var74 = this;
               String var36;
               if (var64.getValue() == null) {
                  var36 = var64.getDetail();
               } else {
                  String var37 = this.f(var64.getValue(), 2);
                  String var55 = var64.getDetail();
                  var36 = var37 + " m/s² error (" + var55 + ")";
               }

               LinearLayout var65 = this.plotsStatus;
               var8 = var65;
               if (var65 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("plotsStatus");
                  var8 = null;
               }

               GxPage.text$default(var74, var36, (ViewGroup)var8, 14.0F, 0, false, 24, null);
            }
         }

         TextView var57 = this.bPause;
         TextView var38 = var57;
         if (var57 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bPause");
            var38 = null;
         }

         String var58;
         if (this.plotsPaused) {
            var58 = "Resume Live";
         } else {
            var58 = "Pause Live";
         }

         var38.setText(var58);
         TextView var59 = this.bAdvanced;
         TextView var39 = var59;
         if (var59 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bAdvanced");
            var39 = null;
         }

         String var60;
         if (this.showAdvanced) {
            var60 = "Hide Advanced Controller Terms";
         } else {
            var60 = "Show Advanced Controller Terms";
         }

         var39.setText(var60);
         LinearLayout var40 = this.advancedBox;
         if (var40 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("advancedBox");
            var40 = var9;
         }

         byte var15;
         if (this.showAdvanced) {
            var15 = 0;
         } else {
            var15 = 8;
         }

         var40.setVisibility(var15);
         boolean var16;
         if (this.samples.size() <= 1) {
            var16 = true;
         } else {
            var16 = false;
         }

         GxTuningSample var41 = CollectionsKt.lastOrNull(this.samples);
         Iterator var61 = this.chartViews.iterator();
         Intrinsics.checkNotNullExpressionValue(var61, "iterator(...)");

         while (var61.hasNext()) {
            var9 = var61.next();
            Intrinsics.checkNotNullExpressionValue(var9, "next(...)");
            var9 = (GxTuningPage.ChartViews)var9;
            TextView var70 = var9.getSource();
            String var66 = this.sourceLabel(var9.getCfg().getSourceKind(), this.s(var7, var9.getCfg().getSourceKey()));
            var70.setText("Source: " + var66);
            var9.getLegend().removeAllViews();

            for (Triple var78 : var9.getCfg().getSeries()) {
               double var2;
               int var5;
               label158: {
                  String var75 = (String)var78.component1();
                  var71 = (String)var78.component2();
                  var5 = ((Number)var78.component3()).intValue();
                  if (var41 != null) {
                     Map var79 = var41.getV();
                     if (var79 != null) {
                        Double var80 = (Double)var79.get(var75);
                        if (var80 != null) {
                           var2 = var80;
                           break label158;
                        }
                     }
                  }

                  var2 = num$default(this, var7.opt(var75), 0.0, 2, null);
               }

               SpannableStringBuilder var76 = new SpannableStringBuilder("▬ ");
               var76.setSpan(new ForegroundColorSpan(var5), 0, 1, 33);
               String var81 = this.f(var2, var9.getCfg().getLegendDigits());
               var76.append(var71 + ": " + var81);
               LinearLayout var72 = var9.getLegend();
               TextView var82 = new TextView((Context)this.getAct());
               var82.setText((CharSequence)var76);
               var82.setTextSize(16.0F);
               var82.setTextColor(CarUi.Companion.getTEXT());
               var82.setPadding(0, 0, this.getUi().dp(20), 0);
               var72.addView((View)var82);
            }

            GxTuningChartView var68 = var9.getChart();
            byte var17;
            if (var16) {
               var17 = 8;
            } else {
               var17 = 0;
            }

            var68.setVisibility(var17);
            TextView var69 = var9.getWaiting();
            if (var16) {
               var17 = 0;
            } else {
               var17 = 8;
            }

            var69.setVisibility(var17);
            if (!var16) {
               var9.getChart().invalidate();
            }
         }
      }
   }

   private final void paintProfiles() {
      if (this.flmBuilt) {
         LinearLayout var2 = this.profilesBox;
         LinearLayout var1 = var2;
         if (var2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("profilesBox");
            var1 = null;
         }

         var1.removeAllViews();
         JSONObject var11 = this.report;
         if (this.reportLoading) {
            GxPage var30 = this;
            var1 = this.profilesBox;
            if (var1 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("profilesBox");
               var1 = null;
            }

            GxPage.text$default(var30, "Loading report profiles...", (ViewGroup)var1, 0.0F, 0, false, 28, null);
         } else if (var11 == null) {
            var1 = this.profilesBox;
            if (var1 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("profilesBox");
               var1 = null;
            }

            var1.addView((View)this.empty("Open a report to view and apply its trial profiles."));
         } else {
            List var12 = this.profiles();
            if (var12.isEmpty()) {
               var1 = this.profilesBox;
               if (var1 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("profilesBox");
                  var1 = null;
               }

               var1.addView((View)this.empty("No trial profiles for this report."));
            } else {
               for (JSONObject var5 : var12) {
                  var2 = this.profilesBox;
                  var1 = var2;
                  if (var2 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("profilesBox");
                     var1 = null;
                  }

                  LinearLayout var4 = this.inner((ViewGroup)var1);
                  var1 = headLine$default(this, (ViewGroup)var4, this.s(var5, "label"), 0, 4, null);
                  this.en(this.headBtn(var1, "Apply Trial", true, new GxTuningPage$$ExternalSyntheticLambda7(this, var5)), this.canApplyTrial());
                  if (!StringsKt.isBlank(this.s(var5, "description"))) {
                     GxPage.text$default(this, this.s(var5, "description"), (ViewGroup)var4, 16.0F, 0, false, 24, null);
                  }

                  Iterable var6 = this.entries(this.obj(var5, "genericParams"));
                  var var26 = new ArrayList();

                  for (Object var7 : var6) {
                     if (!Intrinsics.areEqual(((Pair)var7).getFirst(), "AdvancedLateralTune")) {
                        var26.add(var7);
                     }
                  }

                  List var27 = var26;
                  JSONObject var15 = this.obj(var5, "flmOverrides");
                  List var35 = this.entries(this.obj(var15, "baseFrictionThresholds"));
                  List var31 = this.entries(this.obj(var15, "vehicleKnobs"));
                  if (var27.isEmpty() && var35.isEmpty() && var31.isEmpty()) {
                     GxPage.text$default(this, "No parameter overrides in this profile.", (ViewGroup)var4, 0.0F, 0, false, 28, null);
                  } else {
                     for (Pair var9 : var27) {
                        String var8 = (String)var9.component1();
                        Object var10 = var9.component2();
                        ViewGroup var41 = (ViewGroup)var4;
                        var10 = this.fmtVal(var10);
                        this.mono(var41, var8 + ": " + var10);
                     }

                     for (Pair var16 : var35) {
                        String var28 = (String)var16.component1();
                        JSONObject var17 = (JSONObject)var16.component2();
                        ViewGroup var40 = (ViewGroup)var4;
                        if (var17 instanceof JSONObject) {
                           var17 = var17;
                        } else {
                           var17 = null;
                        }

                        JSONArray var19;
                        if (var17 != null) {
                           var19 = var17.optJSONArray("values");
                        } else {
                           var19 = null;
                        }

                        String var20 = this.curveStr(var19);
                        this.mono(var40, var28 + ": " + var20);
                     }

                     for (Pair var32 : var31) {
                        String var21 = (String)var32.component1();
                        var6 = (Iterable)var32.component2();
                        ViewGroup var33 = (ViewGroup)var4;
                        String var38 = this.fmtVal(var6);
                        this.mono(var33, var21 + ": " + var38);
                     }
                  }
               }
            }
         }
      }
   }

   private static final Unit paintProfiles$lambda$104(GxTuningPage var0, JSONObject var1) {
      var0.applyTrial(var0.s(var1, "id"));
      return Unit.INSTANCE;
   }

   private final void paintReports() {
      LinearLayout var4 = this.reportsBox;
      GxPage.Row var3 = null;
      LinearLayout var2 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("reportsBox");
         var2 = null;
      }

      var2.removeAllViews();
      List var9 = this.objects(this.reports);
      if (!this.flmLoading && !var9.isEmpty()) {
         for (var var3_it : var9) {
            var3 = var3_it;
            String var5 = this.s((JSONObject)var3, "reportId");
            boolean var1 = Intrinsics.areEqual(this.loadedReportId, var5);
            String var6 = this.fmtDate(var3.opt("createdAt"));
            String var11 = this.s((JSONObject)var3, "controlPath");
            String var12;
            if (!StringsKt.isBlank(var11)) {
               var12 = " / " + var11;
            } else {
               var12 = "";
            }

            var6 = var6 + var12;
            GxPage var7 = this;
            var3 = this.s((JSONObject)var3, "carFingerprint");
            Object var13 = var3;
            if (StringsKt.isBlank((CharSequence)var3)) {
               var13 = "Unknown car";
            }

            String var8 = (String)var13;
            var3 = this.reportsBox;
            var13 = var3;
            if (var3 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("reportsBox");
               var13 = null;
            }

            var3 = GxPage.row$default(var7, var8, var6, (ViewGroup)var13, 0, 8, null);
            if (var1) {
               var13 = "Open ✓";
            } else {
               var13 = "Open";
            }

            var3.button((String)var13, var1, new GxTuningPage$$ExternalSyntheticLambda9(this, var5));
            var3.getView().setOnClickListener(new GxTuningPage$$ExternalSyntheticLambda10(this, var5));
            if (var1) {
               var3.getView().setBackground((Drawable)this.getUi().rounded(CarUi.Companion.getCARD_ON(), 14, CarUi.Companion.getACCENT()));
            }
         }
      } else {
         var2 = this.reportsBox;
         if (var2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("reportsBox");
            var2 = var3;
         }

         var2.addView((View)this.empty("No reports yet. Analyze routes to generate findings."));
      }
   }

   private static final Unit paintReports$lambda$71(GxTuningPage var0, String var1) {
      var0.loadReport(var1);
      return Unit.INSTANCE;
   }

   private static final void paintReports$lambda$72(GxTuningPage var0, String var1, View var2) {
      var0.loadReport(var1);
   }

   private final void paintRoutesHead() {
      if (this.flmBuilt) {
         LinearLayout var7 = this.routesHead;
         Object var5 = null;
         Object var4 = null;
         Object var6 = null;
         LinearLayout var3 = var7;
         if (var7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("routesHead");
            var3 = null;
         }

         var3.removeAllViews();
         if (this.loadingRoutes) {
            var3 = this.routesHead;
            if (var3 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("routesHead");
               var3 = (LinearLayout)var6;
            }

            var3.addView((View)this.empty("Loading local routes..."));
         } else if (this.routes.isEmpty()) {
            var4 = this.routesHead;
            var3 = (LinearLayout)var4;
            if (var4 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("routesHead");
               var3 = null;
            }

            var3.addView((View)this.empty("No local routes found."));
            var4 = this;
            var6 = TuplesKt.to("Reload routes", new GxTuningPage$$ExternalSyntheticLambda71(this));
            var3 = this.routesHead;
            if (var3 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("routesHead");
               var3 = (LinearLayout)var5;
            }

            ViewGroup var16 = (ViewGroup)var3;
            GxPage.buttons$default((GxPage)var4, new Pair[]{(Pair)var6}, var16, false, 4, null);
         } else {
            int var2 = this.selectedSegmentCount();
            GxPage var25 = this;
            int var1 = this.maxSegments;
            var6 = var2 + "/" + var1 + " segments selected";
            var5 = this.routesHead;
            var3 = (LinearLayout)var5;
            if (var5 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("routesHead");
               var3 = null;
            }

            GxPage.Row var11 = GxPage.row$default(var25, (String)var6, null, (ViewGroup)var3, 0, 8, null);
            if (var2 > this.maxSegments) {
               var11.getTitleView().setTextColor(Color.rgb(240, 110, 130));
            }

            var6 = this;
            var1 = this.maxSegments;
            Pair var26 = TuplesKt.to("Select first " + var1, new GxTuningPage$$ExternalSyntheticLambda72(this));
            Pair var8 = TuplesKt.to("Clear", new GxTuningPage$$ExternalSyntheticLambda1(this));
            var5 = TuplesKt.to("Reload routes", new GxTuningPage$$ExternalSyntheticLambda2(this));
            var3 = this.routesHead;
            if (var3 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("routesHead");
               var3 = (LinearLayout)var4;
            }

            ViewGroup var13 = (ViewGroup)var3;
            GxPage.buttons$default((GxPage)var6, new Pair[]{var26, var8, (Pair)var5}, var13, false, 4, null);
         }
      }
   }

   private static final Unit paintRoutesHead$lambda$125(GxTuningPage var0) {
      var0.loadRoutes();
      return Unit.INSTANCE;
   }

   private static final Unit paintRoutesHead$lambda$126(GxTuningPage var0) {
      var0.selectFirstSegments();
      return Unit.INSTANCE;
   }

   private static final Unit paintRoutesHead$lambda$127(GxTuningPage var0) {
      var0.selectedRoutes.clear();
      var0.segmentRanges.clear();
      var0.renderRoutesAll();
      return Unit.INSTANCE;
   }

   private static final Unit paintRoutesHead$lambda$128(GxTuningPage var0) {
      var0.loadRoutes();
      return Unit.INSTANCE;
   }

   private final void paintStatus() {
      LinearLayout var7 = this.flmStatusBox;
      Object var6 = null;
      LinearLayout var5 = var7;
      if (var7 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("flmStatusBox");
         var5 = null;
      }

      var5.removeAllViews();
      JSONObject var8 = this.status();
      var7 = this.flmStatusBox;
      var5 = var7;
      if (var7 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("flmStatusBox");
         var5 = null;
      }

      ViewGroup var9 = (ViewGroup)var5;
      CharSequence var28 = this.s(var8, "state");
      Object var12 = var28;
      if (StringsKt.isBlank(var28)) {
         var12 = "idle";
      }

      line$default(this, var9, "State", (String)var12, 0, 8, null);
      var7 = this.flmStatusBox;
      var12 = var7;
      if (var7 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("flmStatusBox");
         var12 = null;
      }

      ViewGroup var30 = (ViewGroup)var12;
      if (this.truthy(var8.opt("running"))) {
         var12 = "Yes";
      } else {
         var12 = "No";
      }

      line$default(this, var30, "Running", (String)var12, 0, 8, null);
      var7 = this.flmStatusBox;
      var12 = var7;
      if (var7 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("flmStatusBox");
         var12 = null;
      }

      ViewGroup var32 = (ViewGroup)var12;
      if (this.isOnroad) {
         var12 = "Yes";
      } else {
         var12 = "No";
      }

      line$default(this, var32, "Onroad", (String)var12, 0, 8, null);
      var7 = this.flmStatusBox;
      var12 = var7;
      if (var7 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("flmStatusBox");
         var12 = null;
      }

      ViewGroup var34 = (ViewGroup)var12;
      if (this.laneCentering) {
         var12 = "On";
      } else {
         var12 = "Off";
      }

      line$default(this, var34, "Lane Centering", (String)var12, 0, 8, null);
      var7 = this.flmStatusBox;
      var12 = var7;
      if (var7 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("flmStatusBox");
         var12 = null;
      }

      line$default(this, (ViewGroup)var12, "Updated", this.fmtAge(var8.opt("updatedAt")), 0, 8, null);
      if (this.truthy(var8.opt("currentSegment"))) {
         var7 = this.flmStatusBox;
         var12 = var7;
         if (var7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("flmStatusBox");
            var12 = null;
         }

         line$default(this, (ViewGroup)var12, "Current segment", this.s(var8, "currentSegment"), 0, 8, null);
      }

      if (this.truthy(var8.opt("lastSkippedSegment"))) {
         var7 = this.flmStatusBox;
         var12 = var7;
         if (var7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("flmStatusBox");
            var12 = null;
         }

         line$default(this, (ViewGroup)var12, "Skipped", this.s(var8, "lastSkippedSegment"), 0, 8, null);
      }

      JSONObject var10 = this.activeTrial;
      if (var10 != null) {
         var7 = this.flmStatusBox;
         var12 = var7;
         if (var7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("flmStatusBox");
            var12 = null;
         }

         var9 = (ViewGroup)var12;
         CharSequence var39 = this.s(var10, "profileLabel");
         var12 = var39;
         if (StringsKt.isBlank(var39)) {
            var12 = this.s(var10, "profileId");
         }

         CharSequence var40 = (CharSequence)var12;
         var12 = var40;
         if (StringsKt.isBlank(var40)) {
            var12 = "Active";
         }

         this.line(var9, "Active trial", (String)var12, CarUi.Companion.getACCENT());
      }

      if (this.truthy(var8.opt("running")) && this.truthy(var8.opt("total"))) {
         var12 = this.flmStatusBox;
         if (var12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("flmStatusBox");
            var12 = var6;
         }

         var12 = (ViewGroup)var12;
         long var1 = (long)num$default(this, var8.opt("progress"), 0.0, 2, null);
         long var3 = (long)num$default(this, var8.opt("total"), 0.0, 2, null);
         line$default(this, (ViewGroup)var12, "Progress", var1 + " / " + var3, 0, 8, null);
      }
   }

   private final void paintTg() {
      if (this.tgBuilt) {
         LinearLayout var4 = this.tgBox;
         LinearLayout var3 = var4;
         if (var4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tgBox");
            var3 = null;
         }

         var3.removeAllViews();
         JSONObject var8 = this.tgData;
         if (this.tgLoading) {
            var3 = this.tgBox;
            if (var3 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("tgBox");
               var3 = null;
            }

            var3.addView((View)this.empty("Loading testing ground state..."));
         } else if (var8 == null) {
            if (!StringsKt.isBlank(this.tgError)) {
               var3 = this.tgBox;
               if (var3 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("tgBox");
                  var3 = null;
               }

               notice$default(this, (ViewGroup)var3, this.tgError, 0, null, 12, null);
            }
         } else {
            if (!StringsKt.isBlank(this.tgError)) {
               var4 = this.tgBox;
               var3 = var4;
               if (var4 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("tgBox");
                  var3 = null;
               }

               notice$default(this, (ViewGroup)var3, this.tgError, 0, null, 12, null);
            }

            List var9 = this.tgSelectable();
            if (var9.isEmpty()) {
               if (StringsKt.isBlank(this.tgError)) {
                  var3 = this.tgBox;
                  if (var3 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("tgBox");
                     var3 = null;
                  }

                  var3.addView((View)this.empty("No active test slots."));
               }
            } else {
               List var44 = this.tgSlots();
               JSONObject var5;
               if (StringsKt.isBlank(this.tgSelected)) {
                  var5 = null;
               } else {
                  Iterator var47 = var44.iterator();

                  do {
                     if (!var47.hasNext()) {
                        var16 = null;
                        break;
                     }

                     var16 = var47.next();
                  } while (!Intrinsics.areEqual(this.slotId((JSONObject)var16), StringsKt.trim(this.tgSelected).toString()));

                  var5 = (JSONObject)var16;
               }

               JSONObject var45;
               if (StringsKt.isBlank(this.tgActiveId())) {
                  var45 = null;
               } else {
                  Iterator var46 = var44.iterator();

                  do {
                     if (!var46.hasNext()) {
                        var17 = null;
                        break;
                     }

                     var17 = var46.next();
                  } while (!Intrinsics.areEqual(this.slotId((JSONObject)var17), this.tgActiveId()));

                  var45 = (JSONObject)var17;
               }

               LinearLayout var6 = this.tgBox;
               var3 = var6;
               if (var6 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("tgBox");
                  var3 = null;
               }

               LinearLayout var7;
               ViewGroup var10;
               label201: {
                  var7 = this.card((ViewGroup)var3);
                  headLine$default(this, (ViewGroup)var7, "Status", 0, 4, null);
                  var10 = (ViewGroup)var7;
                  if (var5 != null) {
                     String var19 = this.s(var5, "name");
                     if (var19 != null) {
                        CharSequence var54 = var19;
                        CharSequence var20 = var54;
                        if (StringsKt.isBlank(var54)) {
                           var20 = null;
                        }

                        String var55 = (String)var20;
                        var21 = var55;
                        if (var55 != null) {
                           break label201;
                        }
                     }
                  }

                  var21 = "Unknown";
               }

               line$default(this, var10, "Selected Slot", var21, 0, 8, null);
               String var22 = "Not active";
               if (var5 != null && Intrinsics.areEqual(this.tgActiveId(), StringsKt.trim(this.tgSelected).toString())) {
                  CharSequence var56 = StringsKt.trim(this.s(var8, "activeVariantLabel")).toString();
                  Object var23 = var56;
                  if (StringsKt.isBlank(var56)) {
                     CharSequence var57 = this.tgActiveVariant();
                     var23 = var57;
                     if (StringsKt.isBlank(var57)) {
                        var23 = this.defaultMode(var5);
                     }

                     var23 = this.modeLabel(var5, (String)var23);
                  }

                  var22 = (String)var23;
               }

               line$default(this, (ViewGroup)var7, "Selected Mode", var22, 0, 8, null);
               var10 = (ViewGroup)var7;
               String var58;
               if (this.truthy(var8.opt("isOnroad"))) {
                  var58 = "Yes";
               } else {
                  var58 = "No";
               }

               line$default(this, var10, "Onroad", var58, 0, 8, null);
               line$default(this, (ViewGroup)var7, "Selectable Slots", String.valueOf(var9.size()), 0, 8, null);
               int var1 = var9.size();
               String var80 = "Current Test Slots (" + var1 + ")";
               var7 = this.tgBox;
               var6 = var7;
               if (var7 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("tgBox");
                  var6 = null;
               }

               this.header(var80, (ViewGroup)var6);
               var7 = this.tgBox;
               var6 = var7;
               if (var7 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("tgBox");
                  var6 = null;
               }

               var7 = this.card((ViewGroup)var6);

               for (JSONObject var12 : var9) {
                  boolean var2 = Intrinsics.areEqual(this.slotId(var12), this.tgActiveId());
                  TextView var81 = new TextView((Context)this.getAct());
                  String var11 = this.slotId(var12);
                  String var87 = this.s(var12, "name");
                  var81.setText(var11 + ". " + var87);
                  var81.setTextSize(18.0F);
                  CarUi.Companion var84 = CarUi.Companion;
                  if (var2) {
                     var1 = var84.getACCENT();
                  } else {
                     var1 = var84.getTEXT();
                  }

                  var81.setTextColor(var1);
                  if (var2) {
                     var81.setTypeface(Typeface.DEFAULT_BOLD);
                  }

                  var81.setPadding(this.getUi().dp(12), this.getUi().dp(10), this.getUi().dp(12), this.getUi().dp(10));
                  if (var2) {
                     var81.setBackground((Drawable)this.getUi().rounded(CarUi.Companion.getCARD_ON(), 10, CarUi.Companion.getACCENT()));
                  }

                  View var85 = (View)var81;
                  LayoutParams var82 = new LayoutParams(-1, -2);
                  var82.bottomMargin = this.getUi().dp(4);
                  Unit var88 = Unit.INSTANCE;
                  var7.addView(var85, (android.view.ViewGroup.LayoutParams)var82);
               }

               GxPage var73 = this;
               var6 = this.tgBox;
               var3 = var6;
               if (var6 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("tgBox");
                  var3 = null;
               }

               label187: {
                  var74 = GxPage.row$default(var73, "View Slot", null, (ViewGroup)var3, 0, 8, null);
                  if (var5 != null) {
                     String var26 = this.slotId(var5);
                     String var63 = this.s(var5, "name");
                     String var64 = var26 + ". " + var63;
                     var27 = var64;
                     if (var64 != null) {
                        break label187;
                     }
                  }

                  var27 = "Choose…";
               }

               this.en(GxPage.Row.button$default(var74, var27, false, new GxTuningPage$$ExternalSyntheticLambda40(this, var9), 2, null), this.tgBusy ^ true);
               var6 = this.tgBox;
               var3 = var6;
               if (var6 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("tgBox");
                  var3 = null;
               }

               this.note(
                  "Only one Testing Ground can be active at a time. Switching slots only changes what you're viewing; the active test stays enabled until you explicitly choose another mode.",
                  (ViewGroup)var3
               );
               if (var5 != null) {
                  var6 = this.tgBox;
                  var3 = var6;
                  if (var6 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("tgBox");
                     var3 = null;
                  }

                  var6 = this.card((ViewGroup)var3);
                  var7 = headLine$default(this, (ViewGroup)var6, this.s(var5, "name"), 0, 4, null);
                  if (Intrinsics.areEqual(this.slotId(var5), this.tgActiveId())) {
                     TextView var30 = new TextView((Context)this.getAct());
                     var30.setText("Active");
                     var30.setTextSize(15.0F);
                     var30.setTextColor(CarUi.Companion.getBG());
                     var30.setTypeface(Typeface.DEFAULT_BOLD);
                     var30.setBackground((Drawable)CarUi.rounded$default(this.getUi(), CarUi.Companion.getACCENT(), 10, null, 4, null));
                     var30.setPadding(this.getUi().dp(12), this.getUi().dp(4), this.getUi().dp(12), this.getUi().dp(4));
                     var7.addView((View)var30);
                  }

                  if (!StringsKt.isBlank(this.s(var5, "description"))) {
                     GxPage.text$default(this, this.s(var5, "description"), (ViewGroup)var6, 16.0F, 0, false, 24, null);
                  }

                  LinearLayout var78 = this.btnBar((ViewGroup)var6);

                  for (Object var31 : this.variantLabels(var5).keySet()) {
                     Intrinsics.checkNotNullExpressionValue(var31, "next(...)");
                     String var83 = (String)var31;
                     boolean var14;
                     if (Intrinsics.areEqual(this.tgActiveId(), StringsKt.trim(this.tgSelected).toString())
                        && Intrinsics.areEqual(this.tgActiveVariant(), var83)) {
                        var14 = true;
                     } else {
                        var14 = false;
                     }

                     String var32;
                     if (var14) {
                        var32 = "✓ ";
                     } else {
                        var32 = "";
                     }

                     String var86 = this.modeLabel(var5, var83);
                     this.en(
                        this.barBtn(var78, var32 + var83 + " · " + var86, var14, new GxTuningPage$$ExternalSyntheticLambda41(this, var83)), this.tgBusy ^ true
                     );
                  }

                  GxPage var33 = this;
                  String var48 = this.s(var5, "name");
                  GxPage.text$default(var33, "Selecting a mode sets " + var48 + " as the active Testing Ground.", (ViewGroup)var6, 0.0F, 0, false, 28, null);
               }

               LinearLayout var49 = this.tgBox;
               var3 = var49;
               if (var49 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("tgBox");
                  var3 = null;
               }

               this.header("Currently Active", (ViewGroup)var3);
               if (var45 != null) {
                  GxPage var68 = this;
                  String var35 = this.s(var45, "id");
                  String var50 = this.s(var45, "name");
                  String var77 = var35 + ". " + var50;
                  LinearLayout var51 = this.tgBox;
                  var3 = var51;
                  if (var51 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("tgBox");
                     var3 = null;
                  }

                  GxPage.Row var69 = GxPage.row$default(var68, var77, null, (ViewGroup)var3, 0, 8, null);
                  var69.getView().setBackground((Drawable)this.getUi().rounded(CarUi.Companion.getCARD_ON(), 14, CarUi.Companion.getACCENT()));
                  CharSequence var52 = StringsKt.trim(this.s(var8, "activeVariantLabel")).toString();
                  Object var37 = var52;
                  if (StringsKt.isBlank(var52)) {
                     CharSequence var53 = this.tgActiveVariant();
                     var37 = var53;
                     if (StringsKt.isBlank(var53)) {
                        var37 = "A";
                     }

                     var37 = this.modeLabel(var45, (String)var37);
                  }

                  var37 = (String)var37;
                  var69.value("Mode " + var37).setTextColor(CarUi.Companion.getACCENT());
               }
            }
         }
      }
   }

   private static final Unit paintTg$lambda$182(GxTuningPage var0, List var1) {
      if (var0.tgBusy) {
         return Unit.INSTANCE;
      } else {
         Iterable var3 = var1;
         var var2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var3, 10));

         for (JSONObject var5 : var3) {
            String var4 = var0.slotId(var5);
            String var8 = var0.s(var5, "name");
            var2.add(var4 + ". " + var8);
         }

         List var6 = var2;
         var0.pick("View Slot", var6, new GxTuningPage$$ExternalSyntheticLambda3(var0, var1));
         return Unit.INSTANCE;
      }
   }

   private static final Unit paintTg$lambda$182$lambda$181(GxTuningPage var0, List var1, int var2) {
      var0.tgSelected = var0.slotId((JSONObject)var1.get(var2));
      var0.paintTg();
      return Unit.INSTANCE;
   }

   private static final Unit paintTg$lambda$184(GxTuningPage var0, String var1) {
      var0.tgSelectMode(var1);
      return Unit.INSTANCE;
   }

   private final void paintTunes() {
      LinearLayout var6 = this.tunesBox;
      CarUi.Companion var5 = null;
      LinearLayout var4 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("tunesBox");
         var4 = null;
      }

      var4.removeAllViews();
      List var13 = this.objects(this.savedTunes);
      if (var13.isEmpty()) {
         var4 = this.tunesBox;
         if (var4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tunesBox");
            var4 = var5;
         }

         var4.addView((View)this.empty("No saved tunes yet. Apply a trial, then save it here."));
      } else {
         for (JSONObject var8 : var13) {
            boolean var3 = this.truthy(var8.opt("active"));
            var5 = this.tunesBox;
            var4 = (LinearLayout)var5;
            if (var5 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("tunesBox");
               var4 = null;
            }

            LinearLayout var9 = this.card((ViewGroup)var4);
            var5 = this.s(var8, "name");
            TextView var15 = (TextView)var5;
            if (StringsKt.isBlank((CharSequence)var5)) {
               var15 = "Saved Tune";
            }

            String var31 = "";
            if (var3) {
               var5 = " (Active)";
            } else {
               var5 = "";
            }

            String var10 = var15 + var5;
            var15 = (ViewGroup)var9;
            var5 = CarUi.Companion;
            int var1;
            if (var3) {
               var1 = var5.getACCENT();
            } else {
               var1 = var5.getTEXT();
            }

            this.text(var10, (ViewGroup)var15, 21.0F, var1, true);
            GxPage var33 = this;
            CharSequence var27 = this.s(var8, "carFingerprint");
            var15 = var27;
            if (StringsKt.isBlank(var27)) {
               var15 = "Unknown car";
            }

            String var11 = this.s(var8, "pathLabel");
            var5 = var31;
            if (!StringsKt.isBlank(var11)) {
               var5 = " / " + var11;
            }

            GxPage.text$default(var33, var15 + var5, (ViewGroup)var9, 0.0F, 0, false, 28, null);
            GxPage var32 = this;
            var15 = this.s(var8, "genericParamCount");
            String var29 = this.s(var8, "frictionCurveCount");
            var10 = this.s(var8, "vehicleKnobCount");
            GxPage.text$default(var32, var15 + " generic, " + var29 + " friction curve, " + var10 + " knobs", (ViewGroup)var9, 0.0F, 0, false, 28, null);
            GxPage.text$default(this, this.fmtAge(var8.opt("updatedAt")), (ViewGroup)var9, 0.0F, 0, false, 28, null);
            LinearLayout var30 = this.btnBar((ViewGroup)var9);
            if (var3) {
               var15 = "Active";
            } else {
               var15 = "Apply";
            }

            var15 = this.barBtn(var30, (String)var15, var3 ^ true, new GxTuningPage$$ExternalSyntheticLambda27(this, var3, var8));
            boolean var2;
            if (!this.busy && !var3) {
               var2 = true;
            } else {
               var2 = false;
            }

            this.en((TextView)var15, var2);
            this.en(barBtn$default(this, var30, "Rename", false, new GxTuningPage$$ExternalSyntheticLambda28(this, var8), 4, null), this.busy ^ true);
            var15 = barBtn$default(this, var30, "Delete", false, new GxTuningPage$$ExternalSyntheticLambda29(this, var3, var8), 4, null);
            var15.setTextColor(Color.rgb(240, 110, 130));
            if (!this.busy && !var3) {
               var2 = true;
            } else {
               var2 = false;
            }

            this.en(var15, var2);
            this.en(barBtn$default(this, var30, "Firestar", false, new GxTuningPage$$ExternalSyntheticLambda30(this, var8), 4, null), this.busy ^ true);
         }
      }
   }

   private static final Unit paintTunes$lambda$45(GxTuningPage var0, boolean var1, JSONObject var2) {
      if (!var0.busy && !var1) {
         String var3 = var0.enc(var0.s(var2, "tuneId"));
         runWith$default(var0, "POST", "/api/flm/saved-tunes/" + var3 + "/apply", null, "Saved tune applied.", null, 16, null);
      }

      return Unit.INSTANCE;
   }

   private static final Unit paintTunes$lambda$46(GxTuningPage var0, JSONObject var1) {
      var0.renameSavedTune(var1);
      return Unit.INSTANCE;
   }

   private static final Unit paintTunes$lambda$49(GxTuningPage var0, boolean var1, JSONObject var2) {
      if (!var0.busy && !var1) {
         CharSequence var4 = var0.s(var2, "name");
         Object var3 = var4;
         if (StringsKt.isBlank(var4)) {
            var3 = "Saved Tune";
         }

         var0.confirm("Delete Saved Tune", "Delete saved tune \"" + var3 + "\"?", "Yes, Delete", new GxTuningPage$$ExternalSyntheticLambda13(var0, var2));
         return Unit.INSTANCE;
      } else {
         return Unit.INSTANCE;
      }
   }

   private static final Unit paintTunes$lambda$49$lambda$48(GxTuningPage var0, JSONObject var1) {
      String var2 = var0.enc(var0.s(var1, "tuneId"));
      runWith$default(var0, "DELETE", "/api/flm/saved-tunes/" + var2, null, "Saved tune deleted.", null, 16, null);
      return Unit.INSTANCE;
   }

   private static final Unit paintTunes$lambda$51(GxTuningPage var0, JSONObject var1) {
      var0.submitTune(var1);
      return Unit.INSTANCE;
   }

   private final void para(ViewGroup var1, String var2, String var3) {
      SpannableStringBuilder var5 = new SpannableStringBuilder(var2).append(" ").append(var3);
      var5.setSpan(new StyleSpan(1), 0, var2.length(), 33);
      TextView var4 = GxPage.text$default(this, "", var1, 16.0F, CarUi.Companion.getTEXT(), false, 16, null);
      var4.setText((CharSequence)var5);
      var4.setPadding(0, this.getUi().dp(3), 0, this.getUi().dp(3));
   }

   private final List<JSONObject> pathList() {
      JSONObject var3 = this.report;
      if (var3 == null) {
         return CollectionsKt.emptyList();
      } else {
         JSONArray var1 = var3.optJSONArray("paths");
         if (var1 != null && var1.length() > 0) {
            return this.objects(var1);
         } else {
            JSONObject var4 = new JSONObject();
            CharSequence var2 = this.s(var3, "primaryPathKey");
            var1 = var2;
            if (StringsKt.isBlank(var2)) {
               var1 = "cleanup_pass";
            }

            return CollectionsKt.listOf(
               var4.put("key", var1)
                  .put("title", "Recommendations")
                  .put("description", "")
                  .put("whySelected", "")
                  .put("isPrimary", true)
                  .put("suggestions", this.arr(var3, "suggestions"))
                  .put("profiles", this.arr(var3, "profiles"))
            );
         }
      }
   }

   private final double percentile(List<Double> var1, double var2) {
      if (var1.isEmpty()) {
         return 0.0;
      } else {
         var2 = (var1.size() - 1) * RangesKt.coerceIn(var2, 0.0, 1.0);
         int var5 = (int)Math.floor(var2);
         int var4 = (int)Math.ceil(var2);
         if (var5 == var4) {
            return ((Number)var1.get(var5)).doubleValue();
         } else {
            var2 -= var5;
            return ((Number)var1.get(var5)).doubleValue() * (1 - var2) + ((Number)var1.get(var4)).doubleValue() * var2;
         }
      }
   }

   private final void plotsLoad(boolean var1) {
      if (this.plotsBuilt && (!this.plotsFetching || var1)) {
         this.plotsFetching = true;
         this.getJson("/api/plots/live", new GxTuningPage$$ExternalSyntheticLambda46(this));
      }
   }

   // $VF: synthetic method
   static void plotsLoad$default(GxTuningPage var0, boolean var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = false;
      }

      var0.plotsLoad(var1);
   }

   private static final Unit plotsLoad$lambda$148(GxTuningPage var0, String var1, JSONObject var2) {
      var0.plotsFetching = false;
      if (var2 != null) {
         var0.live = var2;
         var0.plotsError = "";
         if (!var0.truthy(var2.opt("stale"))) {
            var0.pushSample(var2);
         }
      } else {
         if (var1 == null) {
            var1 = "Failed to load live plot data";
         }

         var0.plotsError = var1;
      }

      var0.plotsLoading = false;
      var0.paintPlots();
      return Unit.INSTANCE;
   }

   private static final SharedPreferences prefs_delegate$lambda$136(Activity var0) {
      return var0.getSharedPreferences("gx_tuning", 0);
   }

   private final JSONObject primaryPath() {
      List var5 = this.pathList();
      String var4 = this.activePathKey();
      Iterator var6 = var5.iterator();

      Object var2;
      Object var3;
      do {
         boolean var1 = var6.hasNext();
         var3 = null;
         if (!var1) {
            var2 = null;
            break;
         }

         var2 = (JSONObject)var6.next();
      } while (!Intrinsics.areEqual(this.s(var2, "key"), var4));

      JSONObject var10 = var2;
      var2 = var10;
      if (var10 == null) {
         Iterator var11 = var5.iterator();

         do {
            if (!var11.hasNext()) {
               var2 = (JSONObject)var3;
               break;
            }

            var2 = (JSONObject)var11.next();
         } while (!this.truthy(var2.opt("isPrimary")));

         var3 = var2;
         var2 = (JSONObject)var3;
         if (var3 == null) {
            var2 = CollectionsKt.firstOrNull(var5);
         }
      }

      return var2;
   }

   private final List<JSONObject> profiles() {
      List var1 = this.objects(this.arr(this.primaryPath(), "profiles"));
      if (var1.isEmpty()) {
         var1 = this.objects(this.arr(this.report, "profiles"));
      }

      return var1;
   }

   private final void pushSample(JSONObject var1) {
      double var2 = num$default(this, var1.opt("timestamp"), 0.0, 2, null);
      if (!(var2 <= 0.0)) {
         boolean var4;
         if (var2 == this.lastTs) {
            var4 = true;
         } else {
            var4 = false;
         }

         if (!var4) {
            this.lastTs = var2;
            HashMap var7 = new HashMap();

            for (String var5 : this.sampleKeys) {
               var7.put(var5, num$default(this, var1.opt(var5), 0.0, 2, null));
            }

            this.samples.add(new GxTuningSample(var2, var7));
            if (this.samples.size() > 240) {
               this.samples.subList(0, this.samples.size() - 240).clear();
            }

            return;
         }
      }
   }

   private final void renameSavedTune(JSONObject var1) {
      String var4 = this.s(var1, "tuneId");
      if (!StringsKt.isBlank(var4) && !this.busy) {
         GxPage var5 = this;
         String var3 = this.s(var1, "name");
         CharSequence var2 = this.s(var1, "name");
         Object var6 = var2;
         if (StringsKt.isBlank(var2)) {
            var6 = "Saved Tune";
         }

         GxPage.prompt$default(
            var5,
            "Rename Saved Tune",
            "Name this tune...",
            var3,
            0,
            "Choose a new name for “" + var6 + "”.",
            new GxTuningPage$$ExternalSyntheticLambda15(this, var4),
            8,
            null
         );
      }
   }

   private static final Unit renameSavedTune$lambda$53(GxTuningPage var0, String var1, String var2) {
      Intrinsics.checkNotNullParameter(var2, "name");
      var1 = var0.enc(var1);
      runWith$default(var0, "PATCH", "/api/flm/saved-tunes/" + var1, new JSONObject().put("name", var2), "Tune renamed.", null, 16, null);
      return Unit.INSTANCE;
   }

   private final void renderReport() {
      if (this.flmBuilt) {
         LinearLayout var7 = this.reportBox;
         LinearLayout var6 = var7;
         if (var7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("reportBox");
            var6 = null;
         }

         var6.removeAllViews();
         this.comparisonBox = null;
         JSONObject var8 = this.report;
         if (var8 != null && !this.reportLoading) {
            var7 = this.reportBox;
            var6 = var7;
            if (var7 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("reportBox");
               var6 = null;
            }

            this.header("Report Summary", (ViewGroup)var6);
            var7 = this.reportBox;
            var6 = var7;
            if (var7 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("reportBox");
               var6 = null;
            }

            LinearLayout var9;
            ViewGroup var10;
            label191: {
               var9 = this.card((ViewGroup)var6);
               var10 = (ViewGroup)var9;
               JSONObject var18 = var8.optJSONObject("car");
               if (var18 != null) {
                  String var19 = this.s(var18, "carFingerprint");
                  if (var19 != null) {
                     CharSequence var42 = var19;
                     CharSequence var20 = var42;
                     if (StringsKt.isBlank(var42)) {
                        var20 = null;
                     }

                     String var43 = (String)var20;
                     var21 = var43;
                     if (var43 != null) {
                        break label191;
                     }
                  }
               }

               var21 = "Report";
            }

            headBtn$default(
                  this,
                  headLine$default(this, var10, var21, 0, 4, null),
                  "Delete report",
                  false,
                  new GxTuningPage$$ExternalSyntheticLambda47(this, var8),
                  4,
                  null
               )
               .setTextColor(Color.rgb(240, 110, 130));
            JSONObject var11 = this.obj(var8, "car");
            JSONObject var69 = this.obj(var8, "summary");
            ViewGroup var12 = (ViewGroup)var9;
            CharSequence var44 = this.s(var11, "carFingerprint");
            EditText var22 = var44;
            if (StringsKt.isBlank(var44)) {
               var22 = "Unknown";
            }

            line$default(this, var12, "Car", (String)var22, 0, 8, null);
            var12 = (ViewGroup)var9;
            CharSequence var45 = this.s(var11, "controlPath");
            var22 = var45;
            if (StringsKt.isBlank(var45)) {
               var22 = "unknown";
            }

            line$default(this, var12, "Control path", (String)var22, 0, 8, null);
            ViewGroup var46 = (ViewGroup)var9;
            double var1 = this.num(var8.opt("createdAt"), Double.NaN);
            boolean var3;
            if (!Double.isInfinite(var1) && !Double.isNaN(var1)) {
               var3 = 1;
            } else {
               var3 = 0;
            }

            if (var3 && var1 > 1.0E9) {
               var22 = this.fmtDate(var1);
            } else {
               var22 = "";
            }

            line$default(this, var46, "Created", (String)var22, 0, 8, null);
            line$default(this, (ViewGroup)var9, "Samples", String.valueOf((long)num$default(this, var69.opt("sampleCount"), 0.0, 2, null)), 0, 8, null);
            line$default(
               this, (ViewGroup)var9, "Processed segments", String.valueOf((long)num$default(this, var69.opt("processedSegments"), 0.0, 2, null)), 0, 8, null
            );
            if (this.truthy(var69.opt("usedQlogFallback"))) {
               line$default(this, (ViewGroup)var9, "qlog fallback", "Yes", 0, 8, null);
            }

            this.comparisonBox = this.box((ViewGroup)var9);
            this.paintComparison();
            var22 = this.pathList();

            for (JSONObject var70 : var22) {
               this.sub(this.s(var70, "title"), (ViewGroup)var9);
               if (!StringsKt.isBlank(this.s(var70, "description"))) {
                  GxPage.text$default(this, this.s(var70, "description"), (ViewGroup)var9, 16.0F, 0, false, 24, null);
               }

               if (!StringsKt.isBlank(this.s(var70, "whySelected"))) {
                  this.para((ViewGroup)var9, "Why this path:", this.s(var70, "whySelected"));
               }

               String var73 = this.s(var70, "key");
               if (Intrinsics.areEqual(var73, this.activePathKey())) {
                  this.text("Active path", (ViewGroup)var9, 15.0F, CarUi.Companion.getACCENT(), true);
               } else if (var22.size() > 1) {
                  GxPage var71 = this;
                  Pair var78 = TuplesKt.to("Use this path", new GxTuningPage$$ExternalSyntheticLambda48(this, var73));
                  ViewGroup var74 = (ViewGroup)var9;
                  GxPage.buttons$default(var71, new Pair[]{var78}, var74, false, 4, null);
               }
            }

            var22 = this.strings(this.arr(var8, "addTheseParametersAndStartHere"));
            if (!((Collection)var22).isEmpty()) {
               this.sub("Add these and start here", (ViewGroup)var9);
               bullets$default(this, (ViewGroup)var9, (List)var22, false, 4, null);
            }

            var22 = this.strings(this.arr(var8, "warnings"));
            if (!((Collection)var22).isEmpty()) {
               this.sub("Warnings", (ViewGroup)var9);
               bullets$default(this, (ViewGroup)var9, (List)var22, false, 4, null);
            }

            var7 = this.reportBox;
            var22 = var7;
            if (var7 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("reportBox");
               var22 = null;
            }

            this.header("Findings & feedback", (ViewGroup)var22);
            var7 = this.reportBox;
            var22 = var7;
            if (var7 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("reportBox");
               var22 = null;
            }

            var7 = this.card((ViewGroup)var22);
            GxPage.text$default(this, "Finding decisions save immediately and regenerate trial profiles.", (ViewGroup)var7, 0.0F, 0, false, 28, null);
            var22 = new EditText((Context)this.getAct());
            var22.setText(this.fbNotes);
            var22.setHint("Optional tuning notes");
            var22.setTextSize(18.0F);
            var22.setTextColor(CarUi.Companion.getTEXT());
            var22.setHintTextColor(CarUi.Companion.getDIM());
            var22.setInputType(147457);
            var22.setMinLines(2);
            var22.setGravity(8388659);
            var22.setBackground((Drawable)this.getUi().rounded(Color.rgb(6, 8, 11), 12, Color.rgb(50, 55, 66)));
            var22.setPadding(this.getUi().dp(14), this.getUi().dp(10), this.getUi().dp(14), this.getUi().dp(10));
            var22.addTextChangedListener(new TextWatcher() {

               public void afterTextChanged(Editable var1) {
                  GxTuningPage var3x;
                  label11: {
                     var3x = GxTuningPage.this;
                     if (var1 != null) {
                        String var2 = var1.toString();
                        var4 = var2;
                        if (var2 != null) {
                           break label11;
                        }
                     }

                     var4 = "";
                  }

                  var3x.fbNotes = var4;
               }

               public void beforeTextChanged(CharSequence var1, int var2, int var3x, int var4) {
               }

               public void onTextChanged(CharSequence var1, int var2, int var3x, int var4) {
               }
            });
            View var51 = (View)var22;
            LayoutParams var31 = new LayoutParams(-1, -2);
            var31.topMargin = this.getUi().dp(8);
            Unit var62 = Unit.INSTANCE;
            var7.addView(var51, (android.view.ViewGroup.LayoutParams)var31);
            GxPage var52 = this;
            Pair var32 = TuplesKt.to("Save Notes", new GxTuningPage$$ExternalSyntheticLambda49(this));
            ViewGroup var63 = (ViewGroup)var7;
            GxPage.buttons$default(var52, new Pair[]{var32}, var63, false, 4, null);
            List var33 = this.suggestions();
            if (var33.isEmpty()) {
               var7.addView((View)this.empty("No findings for the active path."));
            }

            for (JSONObject var79 : var33) {
               LinearLayout var75 = this.inner((ViewGroup)var7);
               CharSequence var53 = this.s(var79, "bucket");
               var22 = var53;
               if (StringsKt.isBlank(var53)) {
                  var22 = "finding";
               }

               this.text(StringsKt.replace$default((String)var22, "_", " ", false, 4, null), (ViewGroup)var75, 18.0F, CarUi.Companion.getTEXT(), true);
               JSONObject var14 = var79.optJSONObject("evidence");
               if (var14 != null && (this.truthy(var14.opt("speedBand")) || this.truthy(var14.opt("directionBias")) || this.truthy(var14.opt("eventCount")))) {
                  GxPage var13 = this;
                  CharSequence var54 = this.s(var14, "speedBand");
                  var22 = var54;
                  if (StringsKt.isBlank(var54)) {
                     var22 = "mixed";
                  }

                  CharSequence var64 = this.s(var14, "directionBias");
                  Object var55 = var64;
                  if (StringsKt.isBlank(var64)) {
                     var55 = "center";
                  }

                  long var4 = (long)num$default(this, var14.opt("eventCount"), 0.0, 2, null);
                  GxPage.text$default(var13, var22 + " | " + var55 + " | " + var4 + " event(s)", (ViewGroup)var75, 14.0F, 0, false, 24, null);
               }

               if (!StringsKt.isBlank(this.s(var79, "observedBehavior"))) {
                  this.para((ViewGroup)var75, "Observed:", this.s(var79, "observedBehavior"));
               }

               if (!StringsKt.isBlank(this.s(var79, "likelyInterpretation"))) {
                  this.para((ViewGroup)var75, "Interpretation:", this.s(var79, "likelyInterpretation"));
               }

               if (!StringsKt.isBlank(this.s(var79, "primaryAdjustment"))) {
                  this.para((ViewGroup)var75, "Primary adjustment:", this.s(var79, "primaryAdjustment"));
               }

               if (!StringsKt.isBlank(this.s(var79, "whatNotToTouchYet"))) {
                  this.para((ViewGroup)var75, "What not to touch yet:", this.s(var79, "whatNotToTouchYet"));
               }

               if (!StringsKt.isBlank(this.s(var79, "ifThatWasWrong"))) {
                  this.para((ViewGroup)var75, "Next thing to try:", this.s(var79, "ifThatWasWrong"));
               }

               JSONObject var65 = var79.optJSONObject("currentVsSuggested");
               if (var65 != null) {
                  ViewGroup var36 = (ViewGroup)var75;
                  var3 = CarUi.Companion.getTEXT();
                  this.text("Current vs suggested:", var36, 15.0F, var3, true).setPadding(0, this.getUi().dp(8), 0, 0);
                  if (Intrinsics.areEqual(this.s(var65, "type"), "friction_curve")) {
                     ViewGroup var56 = (ViewGroup)var75;
                     String var37 = this.s(var65, "family");
                     String var80 = this.curveStr(var65.optJSONArray("current"));
                     String var66 = this.curveStr(var65.optJSONArray("suggested"));
                     this.mono(var56, var37 + " " + var80 + " → " + var66);
                  } else {
                     ViewGroup var81 = (ViewGroup)var75;
                     CharSequence var57 = this.s(var65, "paramKey");
                     var22 = var57;
                     if (StringsKt.isBlank(var57)) {
                        var22 = this.s(var65, "symbol");
                     }

                     String var58 = this.fmtVal(var65.opt("current"));
                     String var67 = this.fmtVal(var65.opt("suggested"));
                     this.mono(var81, var22 + " " + var58 + " → " + var67);
                  }
               }

               String var68 = this.s(var79, "dimensionId");
               String var39;
               if (this.fbAccepted.contains(var68)) {
                  var39 = "accepted";
               } else if (this.fbIgnored.contains(var68)) {
                  var39 = "ignored";
               } else {
                  var39 = "unset";
               }

               LinearLayout var76 = this.btnBar((ViewGroup)var75);
               String var59;
               if (Intrinsics.areEqual(var39, "accepted")) {
                  var59 = "Matched";
               } else {
                  var59 = "Matches Experience";
               }

               this.barBtn(var76, var59, Intrinsics.areEqual(var39, "accepted"), new GxTuningPage$$ExternalSyntheticLambda50(this, var68));
               String var60;
               if (Intrinsics.areEqual(var39, "ignored")) {
                  var60 = "Ignored";
               } else {
                  var60 = "Ignore";
               }

               TextView var61 = barBtn$default(this, var76, var60, false, new GxTuningPage$$ExternalSyntheticLambda51(this, var68), 4, null);
               if (Intrinsics.areEqual(var39, "ignored")) {
                  var61.setTextColor(Color.rgb(240, 110, 130));
               }
            }
         }
      }
   }

   private static final Unit renderReport$lambda$101(GxTuningPage var0, String var1) {
      var0.toggleFeedback(var1, "accepted");
      return Unit.INSTANCE;
   }

   private static final Unit renderReport$lambda$102(GxTuningPage var0, String var1) {
      var0.toggleFeedback(var1, "ignored");
      return Unit.INSTANCE;
   }

   private static final Unit renderReport$lambda$88(GxTuningPage var0, JSONObject var1) {
      var0.deleteReport(var1);
      return Unit.INSTANCE;
   }

   private static final Unit renderReport$lambda$92(GxTuningPage var0, String var1) {
      var0.selectPath(var1);
      return Unit.INSTANCE;
   }

   private static final Unit renderReport$lambda$95(GxTuningPage var0) {
      var0.saveFeedback();
      return Unit.INSTANCE;
   }

   private final void renderRoute(String var1) {
      LinearLayout var9 = this.routeBoxes.get(var1);
      if (var9 != null) {
         Iterator var8 = this.routes.iterator();

         Object var7;
         do {
            if (!var8.hasNext()) {
               var7 = null;
               break;
            }

            var7 = (GxPage.Row)var8.next();
         } while (!Intrinsics.areEqual(this.s((JSONObject)var7, "name"), var1));

         JSONObject var10 = (JSONObject)var7;
         if (var10 != null) {
            var9.removeAllViews();
            boolean var6 = this.selectedRoutes.contains(var1);
            GxPage var24 = this;
            var7 = this.fmtDate(var10.opt("timestamp"));
            String var28 = this.fmtLen(var10);
            int var3 = this.routeSelectedCount(var1);
            var7 = GxPage.row$default(var24, (String)var7, var1 + "\n" + var28 + " · " + var3 + " selected", (ViewGroup)var9, 0, 8, null);
            if (var6) {
               var7.getView().setBackground((Drawable)this.getUi().rounded(CarUi.Companion.getCARD_ON(), 14, CarUi.Companion.getACCENT()));
            }

            Switch var19 = var7.switch_(var6, new GxTuningPage$$ExternalSyntheticLambda16(this, var1));
            this.routeSwitches.put(var1, var19);
            int var4 = this.selectedSegmentCount();
            var3 = this.maxSegments;
            boolean var5 = false;
            boolean var14;
            if (var4 >= var3) {
               var14 = 1;
            } else {
               var14 = 0;
            }

            if (var6 || !var14) {
               var5 = true;
            }

            var19.setEnabled(var5);
            float var2;
            if (var19.isEnabled()) {
               var2 = 1.0F;
            } else {
               var2 = 0.4F;
            }

            var19.setAlpha(var2);
            if (var6) {
               GxTuningPage.Bounds var29 = this.bounds(var1);
               Pair var25 = this.segmentRanges.get(var1);
               Pair var20 = var25;
               if (var25 == null) {
                  var20 = TuplesKt.to("", "");
               }

               String var12 = (String)var20.component1();
               String var11 = (String)var20.component2();
               var4 = var29.getFirst();
               var14 = var29.getLast();
               StringBuilder var21 = new StringBuilder().append("Segments ").append(var4);
               String var26 = "–";
               GxPage.Row var30 = this.row("Segments", var21.append("–").append(var14).append(". Blank uses the whole route.").toString(), (ViewGroup)var9, 1);
               CharSequence var27 = var12;
               var7 = var27;
               if (StringsKt.isBlank(var27)) {
                  var7 = "–";
               }

               GxPage.Row.button$default(var30, "First: " + var7, false, new GxTuningPage$$ExternalSyntheticLambda17(this, var1), 2, null);
               var7 = var11;
               if (StringsKt.isBlank(var7)) {
                  var7 = var26;
               }

               GxPage.Row.button$default(var30, "Last: " + var7, false, new GxTuningPage$$ExternalSyntheticLambda18(this, var1), 2, null);
            }
         }
      }
   }

   private static final Unit renderRoute$lambda$130(GxTuningPage var0, String var1, boolean var2) {
      var0.toggleRoute(var1);
      return Unit.INSTANCE;
   }

   private static final Unit renderRoute$lambda$132(GxTuningPage var0, String var1) {
      var0.editRange(var1, true);
      return Unit.INSTANCE;
   }

   private static final Unit renderRoute$lambda$134(GxTuningPage var0, String var1) {
      var0.editRange(var1, false);
      return Unit.INSTANCE;
   }

   private final void renderRoutesAll() {
      if (this.flmBuilt) {
         LinearLayout var2 = this.routesBox;
         LinearLayout var1 = var2;
         if (var2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("routesBox");
            var1 = null;
         }

         var1.removeAllViews();
         this.routeBoxes.clear();
         this.routeSwitches.clear();
         if (!this.loadingRoutes) {
            Iterator var3 = this.routes.iterator();
            Intrinsics.checkNotNullExpressionValue(var3, "iterator(...)");

            while (var3.hasNext()) {
               Object var6 = var3.next();
               Intrinsics.checkNotNullExpressionValue(var6, "next(...)");
               String var5 = this.s((JSONObject)var6, "name");
               Map var4 = this.routeBoxes;
               var2 = this.routesBox;
               var6 = var2;
               if (var2 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("routesBox");
                  var6 = null;
               }

               var4.put(var5, this.box((ViewGroup)var6));
               this.renderRoute(var5);
            }
         }

         this.afterSelectionChange();
      }
   }

   private final boolean rollbackUnavailable() {
      JSONObject var2 = this.activeTrial;
      boolean var1 = false;
      if (var2 != null) {
         var1 = Intrinsics.areEqual(var2.opt("rollbackAvailable"), false);
      }

      return var1;
   }

   private final int routeSelectedCount(String var1) {
      GxTuningPage.Bounds var12 = this.bounds(var1);
      int var9 = var12.getCount();
      int var8 = 0;
      if (var9 == 0) {
         return 0;
      } else {
         Pair var11 = this.segmentRanges.get(var1);
         Pair var13 = var11;
         if (var11 == null) {
            var13 = TuplesKt.to("", "");
         }

         String var19 = (String)var13.component1();
         var1 = (String)var13.component2();
         double var6 = var12.getLast();
         double var4 = var12.getFirst();
         double var2;
         if (StringsKt.isBlank(var19)) {
            var2 = var12.getFirst();
         } else {
            var2 = this.num(var19, var12.getFirst());
         }

         var9 = (int)Math.min(var6, Math.max(var4, var2));
         var4 = var12.getFirst();
         var6 = var12.getLast();
         if (StringsKt.isBlank(var1)) {
            var2 = var12.getLast();
         } else {
            var2 = this.num(var1, var12.getLast());
         }

         int var10 = (int)Math.max(var4, Math.min(var6, var2));
         if (var10 >= var9) {
            var8 = var10 - var9 + 1;
         }

         return var8;
      }
   }

   private final void runWith(String var1, String var2, JSONObject var3, String var4, Function1<? super JSONObject, Unit> var5) {
      if (!this.busy) {
         this.busy = true;
         this.flmError = "";
         this.paintActions();
         this.send(var1, var2, var3, 60L, new GxTuningPage$$ExternalSyntheticLambda31(this, var5, var4));
      }
   }

   // $VF: synthetic method
   static void runWith$default(GxTuningPage var0, String var1, String var2, JSONObject var3, String var4, Function1 var5, int var6, Object var7) {
      if ((var6 & 16) != 0) {
         var5 = null;
      }

      var0.runWith(var1, var2, var3, var4, var5);
   }

   private static final Unit runWith$lambda$61(GxTuningPage var0, Function1 var1, String var2, String var3, JSONObject var4) {
      if (var3 != null) {
         var0.busy = false;
         var0.flmError = var3;
         var0.toast(var3);
         var0.paintFlm();
         if (var1 != null) {
            var1.invoke(null);
         }
      } else {
         label22: {
            if (var4 != null) {
               var3 = var4.optString("message");
               if (var3 != null) {
                  CharSequence var5 = var3;
                  CharSequence var7 = var5;
                  if (StringsKt.isBlank(var5)) {
                     var7 = null;
                  }

                  String var9 = (String)var7;
                  var3 = var9;
                  if (var9 != null) {
                     break label22;
                  }
               }
            }

            var3 = var2;
         }

         var0.toast(var3);
         flmRefresh$default(var0, false, new GxTuningPage$$ExternalSyntheticLambda42(var0, var1, var4), 1, null);
      }

      return Unit.INSTANCE;
   }

   private static final Unit runWith$lambda$61$lambda$60(GxTuningPage var0, Function1 var1, JSONObject var2) {
      var0.busy = false;
      var0.paintFlm();
      if (var1 != null) {
         if (var2 == null) {
            var2 = new JSONObject();
         }

         var1.invoke(var2);
      }

      return Unit.INSTANCE;
   }

   private final String s(JSONObject var1, String var2) {
      String var4 = "";
      String var3 = var4;
      if (var1 != null) {
         label20: {
            if (var1.has(var2) && !var1.isNull(var2)) {
               Object var5 = var1.opt(var2);
               if (var5 != null) {
                  var2 = var5.toString();
                  var6 = var2;
                  if (var2 != null) {
                     break label20;
                  }
               }
            }

            var6 = "";
         }

         if (var6 == null) {
            var3 = var4;
         } else {
            var3 = var6;
         }
      }

      return var3;
   }

   private final void saveCurrent() {
      JSONObject var1 = this.activeTrial;
      if (var1 != null) {
         if (!this.busy) {
            CharSequence var2 = this.s(var1, "profileLabel");
            var1 = var2;
            if (StringsKt.isBlank(var2)) {
               var1 = this.carFp;
            }

            var2 = (CharSequence)var1;
            var1 = var2;
            if (StringsKt.isBlank(var2)) {
               var1 = "Saved Tune";
            }

            String var5 = (String)var1;
            GxPage.prompt$default(this, "Save Current Tune", "Name this tune...", var5, 0, null, new GxTuningPage$$ExternalSyntheticLambda39(this), 24, null);
         }
      }
   }

   private static final Unit saveCurrent$lambda$58(GxTuningPage var0, String var1) {
      Intrinsics.checkNotNullParameter(var1, "raw");
      var1 = StringsKt.trim(var1).toString();
      boolean var2;
      if (var1.length() == 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (var2) {
         var0.toast("A name is required.");
         return Unit.INSTANCE;
      } else {
         runWith$default(var0, "POST", "/api/flm/saved-tunes", new JSONObject().put("name", var1), "Tune saved.", null, 16, null);
         return Unit.INSTANCE;
      }
   }

   private final void saveFeedback() {
      JSONObject var1 = this.report;
      if (var1 != null) {
         String var2 = this.s(var1, "reportId");
         if (!StringsKt.isBlank(var2) && !this.busy) {
            this.busy = true;
            this.paintActions();
            this.send(
               "POST",
               "/api/flm/feedback",
               new JSONObject()
                  .put("reportId", var2)
                  .put("acceptedDimensions", new JSONArray(CollectionsKt.toList(this.fbAccepted)))
                  .put("ignoredDimensions", new JSONArray(CollectionsKt.toList(this.fbIgnored)))
                  .put("notes", this.fbNotes),
               60L,
               new GxTuningPage$$ExternalSyntheticLambda52(this)
            );
         }
      }
   }

   private static final Unit saveFeedback$lambda$114(GxTuningPage var0, String var1, JSONObject var2) {
      if (var1 != null) {
         var0.busy = false;
         CharSequence var10 = var1;
         Object var7 = var10;
         if (StringsKt.isBlank(var10)) {
            var7 = "Failed to save feedback.";
         }

         var0.toast((String)var7);
         var0.paintFlm();
         return Unit.INSTANCE;
      } else {
         if (var2 != null) {
            JSONObject var3 = var2.optJSONObject("report");
            if (var3 != null) {
               var0.report = var3;
               var0.syncFeedback(var3);
               var0.renderReport();
            }
         }

         label28: {
            if (var2 != null) {
               var1 = var2.optString("message");
               if (var1 != null) {
                  CharSequence var8 = var1;
                  CharSequence var5 = var8;
                  if (StringsKt.isBlank(var8)) {
                     var5 = null;
                  }

                  String var9 = (String)var5;
                  var1 = var9;
                  if (var9 != null) {
                     break label28;
                  }
               }
            }

            var1 = "Feedback saved.";
         }

         var0.toast(var1);
         flmRefresh$default(var0, false, new GxTuningPage$$ExternalSyntheticLambda69(var0), 1, null);
         return Unit.INSTANCE;
      }
   }

   private static final Unit saveFeedback$lambda$114$lambda$113(GxTuningPage var0) {
      var0.busy = false;
      var0.paintFlm();
      return Unit.INSTANCE;
   }

   private final void selectFirstSegments() {
      this.selectedRoutes.clear();
      this.segmentRanges.clear();
      int var1 = this.maxSegments;
      Iterator var3 = this.routes.iterator();
      Intrinsics.checkNotNullExpressionValue(var3, "iterator(...)");

      while (var3.hasNext()) {
         Object var4 = var3.next();
         Intrinsics.checkNotNullExpressionValue(var4, "next(...)");
         var4 = (JSONObject)var4;
         if (var1 <= 0) {
            break;
         }

         String var5 = this.s((JSONObject)var4, "name");
         var4 = this.bounds(var5);
         if (((GxTuningPage.Bounds)var4).getCount() != 0) {
            int var2 = Math.min(((GxTuningPage.Bounds)var4).getCount(), var1);
            this.selectedRoutes.add(var5);
            if (var2 < ((GxTuningPage.Bounds)var4).getCount()) {
               this.segmentRanges
                  .put(
                     var5,
                     TuplesKt.to(String.valueOf(((GxTuningPage.Bounds)var4).getFirst()), String.valueOf(((GxTuningPage.Bounds)var4).getFirst() + var2 - 1))
                  );
            }

            var1 -= var2;
         }
      }

      this.renderRoutesAll();
   }

   private final void selectPath(String var1) {
      JSONObject var2 = this.report;
      if (var2 != null) {
         String var3 = this.s(var2, "reportId");
         if (!StringsKt.isBlank(var3) && !Intrinsics.areEqual(var1, this.activePathKey())) {
            String var4 = this.enc(var3);
            this.runWith(
               "POST",
               "/api/flm/report/" + var4 + "/path",
               new JSONObject().put("pathKey", var1),
               "Path selected.",
               new GxTuningPage$$ExternalSyntheticLambda38(this)
            );
         }
      }
   }

   private static final Unit selectPath$lambda$107(GxTuningPage var0, JSONObject var1) {
      if (var1 != null) {
         var1 = var1.optJSONObject("report");
         if (var1 != null) {
            var0.report = var1;
            var0.syncFeedback(var1);
            var0.renderReport();
            var0.paintProfiles();
         }
      }

      return Unit.INSTANCE;
   }

   private final int selectedSegmentCount() {
      Iterator var2 = this.selectedRoutes.iterator();
      int var1 = 0;

      while (var2.hasNext()) {
         String var3 = (String)var2.next();
         var1 += this.routeSelectedCount(var3);
      }

      return var1;
   }

   private final String slotId(JSONObject var1) {
      if (var1 != null) {
         String var2 = StringsKt.trim(this.s(var1, "id")).toString();
         if (var2 != null) {
            return var2;
         }
      }

      return "";
   }

   private final String sourceLabel(String var1, String var2) {
      var1 = var1.toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var1, "toLowerCase(...)");
      var2 = var2.toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var2, "toLowerCase(...)");
      if (Intrinsics.areEqual(var1, "lateral")) {
         if (Intrinsics.areEqual(var2, "torquestate")) {
            return "Steering controller output";
         }

         if (Intrinsics.areEqual(var2, "curvature")) {
            return "Path model estimate";
         }
      }

      if (Intrinsics.areEqual(var1, "longitudinal")) {
         if (StringsKt.contains$default(var2, "atarget", false, 2, null)) {
            return "Planner target acceleration + measured acceleration";
         }

         if (StringsKt.contains$default(var2, "pid sum", false, 2, null)) {
            return "PID term sum + measured acceleration";
         }

         if (StringsKt.contains$default(var2, "caroutput", false, 2, null)) {
            return "Final accel command + measured acceleration";
         }

         if (StringsKt.contains$default(var2, "livelocationkalman", false, 2, null)) {
            return "Control output + calibrated acceleration";
         }

         if (Intrinsics.areEqual(var2, "controlsstate")) {
            return "Planner target output";
         }
      }

      if (Intrinsics.areEqual(var1, "lateralterms")) {
         if (Intrinsics.areEqual(var2, "torquestate")) {
            return "Steering torque controller";
         }

         if (Intrinsics.areEqual(var2, "pidstate")) {
            return "Steering angle PID controller";
         }
      }

      return Intrinsics.areEqual(var1, "longitudinalterms") && Intrinsics.areEqual(var2, "controlsstate")
         ? "Longitudinal controller terms"
         : "Live control signal";
   }

   private final JSONObject status() {
      JSONObject var2 = new JSONObject(this.wsStatus.toString());
      JSONObject var4 = this.statusData;
      if (var4 != null) {
         Iterator var3 = var4.keys();
         Intrinsics.checkNotNullExpressionValue(var3, "keys(...)");

         while (var3.hasNext()) {
            String var1 = (String)var3.next();
            var2.put(var1, var4.opt(var1));
         }
      }

      return var2;
   }

   private final TextView sub(String var1, ViewGroup var2) {
      TextView var3 = this.text(var1, var2, 18.0F, CarUi.Companion.getTEXT(), true);
      var3.setPadding(0, this.getUi().dp(14), 0, this.getUi().dp(4));
      return var3;
   }

   private final void submitTune(JSONObject var1) {
      this.confirm(
         "Send to Firestar",
         "Think this FLM tune is genuinely good and worth sharing? Only the tune values, car identity, and your Discord username are sent; routes and driving logs are not included.",
         "Continue",
         new GxTuningPage$$ExternalSyntheticLambda32(this, var1)
      );
   }

   private static final Unit submitTune$lambda$55(GxTuningPage var0, JSONObject var1) {
      GxPage.prompt$default(
         var0, "Send to Firestar", "Enter your Discord username...", null, 0, null, new GxTuningPage$$ExternalSyntheticLambda53(var0, var1), 28, null
      );
      return Unit.INSTANCE;
   }

   private static final Unit submitTune$lambda$55$lambda$54(GxTuningPage var0, JSONObject var1, String var2) {
      Intrinsics.checkNotNullParameter(var2, "raw");
      var2 = StringsKt.trim(var2).toString();
      boolean var3;
      if (var2.length() == 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (var3) {
         var0.toast("A Discord username is required.");
         return Unit.INSTANCE;
      } else {
         String var4 = var0.enc(var0.s(var1, "tuneId"));
         runWith$default(
            var0,
            "POST",
            "/api/flm/saved-tunes/" + var4 + "/submit",
            new JSONObject().put("discordUsername", var2),
            "Tune submitted to Firestar.",
            null,
            16,
            null
         );
         return Unit.INSTANCE;
      }
   }

   private final List<JSONObject> suggestions() {
      JSONObject var1 = this.primaryPath();
      JSONArray var2;
      if (var1 != null) {
         var2 = this.arr(var1, "suggestions");
      } else {
         var2 = this.arr(this.report, "suggestions");
      }

      return this.objects(var2);
   }

   private final void syncFeedback(JSONObject var1) {
      var1 = this.obj(var1, "feedback");
      this.fbAccepted.clear();
      this.fbAccepted.addAll(this.strings(this.arr(var1, "acceptedDimensions")));
      this.fbIgnored.clear();
      this.fbIgnored.addAll(this.strings(this.arr(var1, "ignoredDimensions")));
      Object var4 = var1.opt("notes");
      if (var4 instanceof String) {
         var4 = (String)var4;
      } else {
         var4 = null;
      }

      String var2 = (String)var4;
      if (var4 == null) {
         var2 = "";
      }

      this.fbNotes = var2;
   }

   private final String tgActiveId() {
      return StringsKt.trim(this.s(this.tgData, "activeSlot")).toString();
   }

   private final String tgActiveVariant() {
      String var1 = StringsKt.trim(this.s(this.tgData, "activeVariant")).toString().toUpperCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var1, "toUpperCase(...)");
      return var1;
   }

   private final void tgApplyPayload(JSONObject var1) {
      this.tgData = var1;
      this.tgError = "";
      List var5 = this.objects(this.arr(var1, "selectableSlots"));
      var var6 = var5;
      boolean var4 = var6 instanceof Collection;
      boolean var3 = false;
      boolean var2;
      if (var4 && ((Collection)var6).isEmpty()) {
         var2 = true;
      } else {
         Iterator var9 = var6.iterator();

         while (true) {
            if (!var9.hasNext()) {
               var2 = true;
               break;
            }

            if (Intrinsics.areEqual(this.slotId((JSONObject)var9.next()), StringsKt.trim(this.tgSelected).toString())) {
               var2 = false;
               break;
            }
         }
      }

      if (var2) {
         String var7 = StringsKt.trim(this.s(var1, "activeSlot")).toString();
         var6 = var5;
         if (var6 instanceof Collection && ((Collection)var6).isEmpty()) {
            var2 = var3;
         } else {
            Iterator var11 = var6.iterator();

            while (true) {
               if (!var11.hasNext()) {
                  var2 = var3;
                  break;
               }

               if (Intrinsics.areEqual(this.slotId((JSONObject)var11.next()), var7)) {
                  var2 = true;
                  break;
               }
            }
         }

         if (!var2) {
            var7 = this.slotId(CollectionsKt.firstOrNull(var5));
         }

         this.tgSelected = var7;
      }
   }

   private final void tgLoad() {
      if (this.tgBuilt && !this.tgFetching) {
         this.tgFetching = true;
         this.getJson("/api/testing_grounds", new GxTuningPage$$ExternalSyntheticLambda6(this));
      }
   }

   private static final Unit tgLoad$lambda$165(GxTuningPage var0, String var1, JSONObject var2) {
      var0.tgFetching = false;
      var0.tgLoading = false;
      if (var2 != null) {
         var0.tgApplyPayload(var2);
      } else {
         if (var1 == null) {
            var1 = "Failed to load testing grounds";
         }

         var0.tgError = var1;
      }

      var0.paintTg();
      return Unit.INSTANCE;
   }

   private final void tgSelectMode(String var1) {
      if (!this.tgBusy) {
         Iterator var3 = this.tgSlots().iterator();

         Object var2;
         do {
            if (!var3.hasNext()) {
               var2 = null;
               break;
            }

            var2 = var3.next();
         } while (!Intrinsics.areEqual(this.slotId((JSONObject)var2), StringsKt.trim(this.tgSelected).toString()));

         var2 = (JSONObject)var2;
         if (var2 == null) {
            this.toast("Select a testing ground first.");
         } else {
            var2 = this.slotId((JSONObject)var2);
            var1 = StringsKt.trim(var1).toString().toUpperCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(var1, "toUpperCase(...)");
            if (!StringsKt.isBlank((CharSequence)var2) && !StringsKt.isBlank(var1)) {
               this.tgBusy = true;
               this.paintTg();
               GxPage.send$default(
                  this,
                  "POST",
                  "/api/testing_grounds/select",
                  new JSONObject().put("slotId", var2).put("variant", var1),
                  0L,
                  new GxTuningPage$$ExternalSyntheticLambda26(this, (String)var2, var1),
                  8,
                  null
               );
            }
         }
      }
   }

   private static final Unit tgSelectMode$lambda$170(GxTuningPage var0, String var1, String var2, String var3, JSONObject var4) {
      var0.tgBusy = false;
      if (var3 == null && var4 != null) {
         var0.tgApplyPayload(var4);
         var0.tgSelected = var1;
         var0.tgError = "";
         Iterator var5 = var0.objects(var0.arr(var4, "slots")).iterator();

         do {
            if (!var5.hasNext()) {
               var8 = null;
               break;
            }

            var8 = var5.next();
         } while (!Intrinsics.areEqual(var0.slotId((JSONObject)var8), var1));

         JSONObject var6 = (JSONObject)var8;
         CharSequence var11 = StringsKt.trim(var0.s(var4, "activeVariantLabel")).toString();
         Object var9 = var11;
         if (StringsKt.isBlank(var11)) {
            var9 = var0.modeLabel(var6, var2);
         }

         String var12 = (String)var9;
         var9 = var0.s(var4, "message");
         Object var7 = var9;
         if (StringsKt.isBlank((CharSequence)var9)) {
            var7 = "Testing Ground " + var1 + " set to " + var12 + ".";
         }

         var0.toast((String)var7);
      } else {
         if (var3 == null) {
            var3 = "Failed to update testing ground mode";
         }

         var0.tgError = var3;
         var0.toast(var0.tgError);
      }

      var0.paintTg();
      return Unit.INSTANCE;
   }

   private final List<JSONObject> tgSelectable() {
      List var1 = this.objects(this.arr(this.tgData, "selectableSlots"));
      if (var1.isEmpty()) {
         Iterable var2 = this.tgSlots();
         var var4 = new ArrayList();

         for (Object var3 : var2) {
            if (!this.isUnused((JSONObject)var3)) {
               var4.add(var3);
            }
         }

         var1 = var4;
      }

      return var1;
   }

   private final List<JSONObject> tgSlots() {
      return this.objects(this.arr(this.tgData, "slots"));
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private final void toggleAdvanced() {
      this.showAdvanced ^= true;

      label20:
      try {
         Result.Companion var1 = Result.Companion;
         GxTuningPage var5 = this;
         var5.getPrefs().edit().putBoolean("plotsShowAdvancedTerms", var5.showAdvanced).apply();
         KtCompat.resultOf(Unit.INSTANCE);
      } catch (Throwable var4) {
         Result.Companion var2 = Result.Companion;
         KtCompat.resultOf(ResultKt.createFailure(var4));
         break label20;
      }

      this.paintPlots();
   }

   private final void toggleFeedback(String var1, String var2) {
      boolean var3 = this.fbAccepted.contains(var1);
      String var5 = "unset";
      String var4;
      if (var3) {
         var4 = "accepted";
      } else if (this.fbIgnored.contains(var1)) {
         var4 = "ignored";
      } else {
         var4 = "unset";
      }

      if (Intrinsics.areEqual(var4, var2)) {
         var2 = var5;
      }

      this.fbAccepted.remove(var1);
      this.fbIgnored.remove(var1);
      if (Intrinsics.areEqual(var2, "accepted")) {
         this.fbAccepted.add(var1);
      }

      if (Intrinsics.areEqual(var2, "ignored")) {
         this.fbIgnored.add(var1);
      }

      this.renderReport();
      this.saveFeedback();
   }

   private final void togglePaused() {
      this.plotsPaused ^= true;
      this.paintPlots();
      if (!this.plotsPaused) {
         this.plotsLoad(true);
      }
   }

   private final void toggleRoute(String var1) {
      if (this.selectedRoutes.contains(var1)) {
         this.selectedRoutes.remove(var1);
         this.segmentRanges.remove(var1);
      } else {
         int var2 = this.maxSegments - this.selectedSegmentCount();
         if (var2 <= 0) {
            var2 = this.maxSegments;
            this.toast("FLM is limited to " + var2 + " segments at a time.");
            this.renderRoute(var1);
            return;
         }

         GxTuningPage.Bounds var3 = this.bounds(var1);
         if (var3.getCount() == 0) {
            this.renderRoute(var1);
            return;
         }

         this.selectedRoutes.add(var1);
         if (var3.getCount() > var2) {
            this.segmentRanges.put(var1, TuplesKt.to(String.valueOf(var3.getFirst()), String.valueOf(var3.getFirst() + var2 - 1)));
         }
      }

      this.renderRoute(var1);
      this.afterSelectionChange();
   }

   private final int toneColor(String var1) {
      switch (var1.hashCode()) {
         case 3135268:
            if (var1.equals("fair")) {
               var1 = "#d4a060";
               return Color.parseColor(var1);
            }
            break;
         case 3178685:
            if (var1.equals("good")) {
               var1 = "#5ec8c8";
               return Color.parseColor(var1);
            }
            break;
         case 3446818:
            if (var1.equals("poor")) {
               var1 = "#e05577";
               return Color.parseColor(var1);
            }
            break;
         case 98619021:
            if (var1.equals("great")) {
               var1 = "#6cc56e";
               return Color.parseColor(var1);
            }
      }

      return CarUi.Companion.getDIM();
   }

   private final boolean truthy(Object var1) {
      boolean var4 = false;
      boolean var3 = var4;
      if (var1 != null) {
         if (Intrinsics.areEqual(var1, JSONObject.NULL)) {
            var3 = var4;
         } else if (var1 instanceof Boolean) {
            var3 = (Boolean)var1;
         } else {
            if (var1 instanceof Number) {
               boolean var2;
               if (((Number)var1).doubleValue() == 0.0) {
                  var2 = true;
               } else {
                  var2 = false;
               }

               if (var2) {
                  return var4;
               }
            } else if (var1 instanceof String && ((CharSequence)var1).length() <= 0) {
               return var4;
            }

            var3 = true;
         }
      }

      return var3;
   }

   private final TreeMap<String, String> variantLabels(JSONObject var1) {
      TreeMap var4 = new TreeMap();
      if (var1 == null) {
         var4.put("A", "A");
         return var4;
      } else {
         JSONObject var7 = var1.optJSONObject("variantLabels");
         boolean var3 = true;
         if (var7 != null) {
            Iterator var6 = var7.keys();
            Intrinsics.checkNotNullExpressionValue(var6, "keys(...)");

            while (var6.hasNext()) {
               String var8 = (String)var6.next();
               Intrinsics.checkNotNull(var8);
               String var5 = StringsKt.trim(var8).toString().toUpperCase(Locale.ROOT);
               Intrinsics.checkNotNullExpressionValue(var5, "toUpperCase(...)");
               var8 = StringsKt.trim(this.s(var7, var8)).toString();
               if (var5.length() == 1) {
                  char var2 = var5.charAt(0);
                  boolean var10;
                  if ('A' <= var2 && var2 < '[') {
                     var10 = true;
                  } else {
                     var10 = false;
                  }

                  if (var10) {
                     if (var8.length() > 0) {
                        var10 = true;
                     } else {
                        var10 = false;
                     }

                     if (var10) {
                        var4.put(var5, var8);
                     }
                  }
               }
            }
         }

         String var14 = StringsKt.trim(this.s(var1, "aLabel")).toString();
         String var9 = StringsKt.trim(this.s(var1, "bLabel")).toString();
         boolean var12;
         if (var14.length() > 0) {
            var12 = true;
         } else {
            var12 = false;
         }

         if (var12 && var4.get("A") == null) {
            var4.put("A", var14);
         }

         if (var9.length() > 0) {
            var12 = var3;
         } else {
            var12 = false;
         }

         if (var12 && var4.get("B") == null) {
            var4.put("B", var9);
         }

         if (var4.get("A") == null) {
            var4.put("A", "A");
         }

         return var4;
      }
   }

   @Override
   public void build() {
      GxPage.header$default(this, "Tuning, Plots & Testing", null, 2, null);
      GxPage.tabs$default(
         this,
         CollectionsKt.listOf(new String[]{"Lateral Tuning", "Plots", "Testing Ground", "Maneuvers"}),
         0,
         new GxTuningPage$$ExternalSyntheticLambda59(this),
         new GxTuningPage$$ExternalSyntheticLambda60(this),
         2,
         null
      );
      this.every(4000L, new GxTuningPage$$ExternalSyntheticLambda61(this));
      this.every(1000L, new GxTuningPage$$ExternalSyntheticLambda62(this));
      this.every(3000L, new GxTuningPage$$ExternalSyntheticLambda63(this));
      this.every(3000L, new GxTuningPage$$ExternalSyntheticLambda64(this));
   }

   @Override
   public boolean getNeedsOffroad() {
      return this.needsOffroad;
   }

   @Override
   public void onShow() {
      if (this.flmBuilt && this.routes.isEmpty() && !this.loadingRoutes) {
         this.loadRoutes();
      }
   }

   private static final class Bounds {
      private final int count;
      private final int first;
      private final int last;

      public Bounds(int var1, int var2, int var3) {
         this.first = var1;
         this.last = var2;
         this.count = var3;
      }

      // $VF: synthetic method
      public static GxTuningPage.Bounds copy$default(GxTuningPage.Bounds var0, int var1, int var2, int var3, int var4, Object var5) {
         if ((var4 & 1) != 0) {
            var1 = var0.first;
         }

         if ((var4 & 2) != 0) {
            var2 = var0.last;
         }

         if ((var4 & 4) != 0) {
            var3 = var0.count;
         }

         return var0.copy(var1, var2, var3);
      }

      public final int component1() {
         return this.first;
      }

      public final int component2() {
         return this.last;
      }

      public final int component3() {
         return this.count;
      }

      public final GxTuningPage.Bounds copy(int var1, int var2, int var3) {
         return new GxTuningPage.Bounds(var1, var2, var3);
      }

      @Override
      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof GxTuningPage.Bounds)) {
            return false;
         } else {
            var1 = var1;
            if (this.first != var1.first) {
               return false;
            } else {
               return this.last != var1.last ? false : this.count == var1.count;
            }
         }
      }

      public final int getCount() {
         return this.count;
      }

      public final int getFirst() {
         return this.first;
      }

      public final int getLast() {
         return this.last;
      }

      @Override
      public int hashCode() {
         return (Integer.hashCode(this.first) * 31 + Integer.hashCode(this.last)) * 31 + Integer.hashCode(this.count);
      }

      @Override
      public String toString() {
         int var1 = this.first;
         int var3 = this.last;
         int var2 = this.count;
         return "Bounds(first=" + var1 + ", last=" + var3 + ", count=" + var2 + ")";
      }
   }

   private static final class ChartCfg {
      private final boolean advanced;
      private final String id;
      private final int legendDigits;
      private final int rangeDigits;
      private final double rangeFloor;
      private final double rangeMult;
      private final double roundDiv;
      private final List<Triple<String, String, Integer>> series;
      private final String sourceKey;
      private final String sourceKind;
      private final String title;
      private final String unit;

      public ChartCfg(
         String var1,
         boolean var2,
         String var3,
         String var4,
         int var5,
         int var6,
         double var7,
         double var9,
         double var11,
         String var13,
         String var14,
         List<Triple<String, String, Integer>> var15
      ) {
         Intrinsics.checkNotNullParameter(var1, "id");
         Intrinsics.checkNotNullParameter(var3, "title");
         Intrinsics.checkNotNullParameter(var4, "unit");
         Intrinsics.checkNotNullParameter(var13, "sourceKind");
         Intrinsics.checkNotNullParameter(var14, "sourceKey");
         Intrinsics.checkNotNullParameter(var15, "series");
         super();
         this.id = var1;
         this.advanced = var2;
         this.title = var3;
         this.unit = var4;
         this.legendDigits = var5;
         this.rangeDigits = var6;
         this.rangeFloor = var7;
         this.rangeMult = var9;
         this.roundDiv = var11;
         this.sourceKind = var13;
         this.sourceKey = var14;
         this.series = var15;
      }

      public final boolean getAdvanced() {
         return this.advanced;
      }

      public final String getId() {
         return this.id;
      }

      public final int getLegendDigits() {
         return this.legendDigits;
      }

      public final int getRangeDigits() {
         return this.rangeDigits;
      }

      public final double getRangeFloor() {
         return this.rangeFloor;
      }

      public final double getRangeMult() {
         return this.rangeMult;
      }

      public final double getRoundDiv() {
         return this.roundDiv;
      }

      public final List<Triple<String, String, Integer>> getSeries() {
         return this.series;
      }

      public final String getSourceKey() {
         return this.sourceKey;
      }

      public final String getSourceKind() {
         return this.sourceKind;
      }

      public final String getTitle() {
         return this.title;
      }

      public final String getUnit() {
         return this.unit;
      }
   }

   private static final class ChartViews {
      private final GxTuningPage.ChartCfg cfg;
      private final GxTuningChartView chart;
      private final LinearLayout legend;
      private final TextView source;
      private final TextView waiting;

      public ChartViews(GxTuningPage.ChartCfg var1, TextView var2, LinearLayout var3, GxTuningChartView var4, TextView var5) {
         Intrinsics.checkNotNullParameter(var1, "cfg");
         Intrinsics.checkNotNullParameter(var2, "source");
         Intrinsics.checkNotNullParameter(var3, "legend");
         Intrinsics.checkNotNullParameter(var4, "chart");
         Intrinsics.checkNotNullParameter(var5, "waiting");
         super();
         this.cfg = var1;
         this.source = var2;
         this.legend = var3;
         this.chart = var4;
         this.waiting = var5;
      }

      public final GxTuningPage.ChartCfg getCfg() {
         return this.cfg;
      }

      public final GxTuningChartView getChart() {
         return this.chart;
      }

      public final LinearLayout getLegend() {
         return this.legend;
      }

      public final TextView getSource() {
         return this.source;
      }

      public final TextView getWaiting() {
         return this.waiting;
      }
   }

   private static final class CmpRow {
      private final Object current;
      private final boolean curve;
      private final String key;
      private final String label;
      private final Object stock;

      public CmpRow(String var1, String var2, boolean var3, Object var4, Object var5) {
         Intrinsics.checkNotNullParameter(var1, "key");
         Intrinsics.checkNotNullParameter(var2, "label");
         super();
         this.key = var1;
         this.label = var2;
         this.curve = var3;
         this.stock = var4;
         this.current = var5;
      }

      // $VF: synthetic method
      public static GxTuningPage.CmpRow copy$default(
         GxTuningPage.CmpRow var0, String var1, String var2, boolean var3, Object var4, Object var5, int var6, Object var7
      ) {
         if ((var6 & 1) != 0) {
            var1 = var0.key;
         }

         if ((var6 & 2) != 0) {
            var2 = var0.label;
         }

         if ((var6 & 4) != 0) {
            var3 = var0.curve;
         }

         if ((var6 & 8) != 0) {
            var4 = var0.stock;
         }

         if ((var6 & 16) != 0) {
            var5 = var0.current;
         }

         return var0.copy(var1, var2, var3, var4, var5);
      }

      public final String component1() {
         return this.key;
      }

      public final String component2() {
         return this.label;
      }

      public final boolean component3() {
         return this.curve;
      }

      public final Object component4() {
         return this.stock;
      }

      public final Object component5() {
         return this.current;
      }

      public final GxTuningPage.CmpRow copy(String var1, String var2, boolean var3, Object var4, Object var5) {
         Intrinsics.checkNotNullParameter(var1, "key");
         Intrinsics.checkNotNullParameter(var2, "label");
         return new GxTuningPage.CmpRow(var1, var2, var3, var4, var5);
      }

      @Override
      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof GxTuningPage.CmpRow)) {
            return false;
         } else {
            var1 = var1;
            if (!Intrinsics.areEqual(this.key, var1.key)) {
               return false;
            } else if (!Intrinsics.areEqual(this.label, var1.label)) {
               return false;
            } else if (this.curve != var1.curve) {
               return false;
            } else {
               return !Intrinsics.areEqual(this.stock, var1.stock) ? false : Intrinsics.areEqual(this.current, var1.current);
            }
         }
      }

      public final Object getCurrent() {
         return this.current;
      }

      public final boolean getCurve() {
         return this.curve;
      }

      public final String getKey() {
         return this.key;
      }

      public final String getLabel() {
         return this.label;
      }

      public final Object getStock() {
         return this.stock;
      }

      @Override
      public int hashCode() {
         int var3 = this.key.hashCode();
         int var5 = this.label.hashCode();
         int var4 = Boolean.hashCode(this.curve);
         Object var6 = this.stock;
         int var2 = 0;
         int var1;
         if (var6 == null) {
            var1 = 0;
         } else {
            var1 = this.stock.hashCode();
         }

         if (this.current != null) {
            var2 = this.current.hashCode();
         }

         return (((var3 * 31 + var5) * 31 + var4) * 31 + var1) * 31 + var2;
      }

      @Override
      public String toString() {
         String var3 = this.key;
         String var5 = this.label;
         boolean var1 = this.curve;
         Object var4 = this.stock;
         Object var2 = this.current;
         return "CmpRow(key=" + var3 + ", label=" + var5 + ", curve=" + var1 + ", stock=" + var4 + ", current=" + var2 + ")";
      }
   }

   private static final class Quality {
      private final String detail;
      private final String label;
      private final Double value;

      public Quality(String var1, Double var2, String var3) {
         Intrinsics.checkNotNullParameter(var1, "label");
         Intrinsics.checkNotNullParameter(var3, "detail");
         super();
         this.label = var1;
         this.value = var2;
         this.detail = var3;
      }

      public final String getDetail() {
         return this.detail;
      }

      public final String getLabel() {
         return this.label;
      }

      public final Double getValue() {
         return this.value;
      }
   }

   private static final class QualityCfg {
      private final String actualKey;
      private final boolean allowFallback;
      private final String desiredKey;
      private final double fair;
      private final double fallbackMinPeak;
      private final double fallbackMinSpeed;
      private final double good;
      private final double great;
      private final double minDemand;
      private final double minSpeed;
      private final boolean persistence;
      private final double severeError;
      private final double warnError;

      public QualityCfg(
         String var1,
         String var2,
         double var3,
         double var5,
         boolean var7,
         double var8,
         double var10,
         boolean var12,
         double var13,
         double var15,
         double var17,
         double var19,
         double var21
      ) {
         Intrinsics.checkNotNullParameter(var1, "desiredKey");
         Intrinsics.checkNotNullParameter(var2, "actualKey");
         super();
         this.desiredKey = var1;
         this.actualKey = var2;
         this.minSpeed = var3;
         this.minDemand = var5;
         this.allowFallback = var7;
         this.fallbackMinSpeed = var8;
         this.fallbackMinPeak = var10;
         this.persistence = var12;
         this.warnError = var13;
         this.severeError = var15;
         this.great = var17;
         this.good = var19;
         this.fair = var21;
      }

      public final String getActualKey() {
         return this.actualKey;
      }

      public final boolean getAllowFallback() {
         return this.allowFallback;
      }

      public final String getDesiredKey() {
         return this.desiredKey;
      }

      public final double getFair() {
         return this.fair;
      }

      public final double getFallbackMinPeak() {
         return this.fallbackMinPeak;
      }

      public final double getFallbackMinSpeed() {
         return this.fallbackMinSpeed;
      }

      public final double getGood() {
         return this.good;
      }

      public final double getGreat() {
         return this.great;
      }

      public final double getMinDemand() {
         return this.minDemand;
      }

      public final double getMinSpeed() {
         return this.minSpeed;
      }

      public final boolean getPersistence() {
         return this.persistence;
      }

      public final double getSevereError() {
         return this.severeError;
      }

      public final double getWarnError() {
         return this.warnError;
      }
   }
}
