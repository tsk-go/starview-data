package com.starpilot.starview;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.SystemClock;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.TextUtils.TruncateAt;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.jvm.internal.Ref.ObjectRef;
import kotlin.ranges.RangesKt;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

public final class GxLogsPage extends GxPage {
   private String errContent;
   private TextView errDelAll;
   private List<GxLogsPage.ErrFile> errFiles;
   private String errFilter;
   private LinearLayout errList;
   private boolean errLoading;
   private String errSelected;
   private LinearLayout errViewCard;
   private TextView errViewLog;
   private TextView errViewTitle;
   private LinearLayout monBody;
   private LinearLayout monCoresBox;
   private TextView monCoresBtn;
   private boolean monCoresOpen;
   private TextView monCountNote;
   private boolean monDesc;
   private TextView monErrNote;
   private String monError;
   private List<Double> monHistory;
   private boolean monLoading;
   private TextView monPauseBtn;
   private boolean monPaused;
   private List<GxLogsProc> monProcs;
   private String monQuery;
   private long monReceivedAt;
   private String monScope;
   private long monSensorNow;
   private JSONObject monSnap;
   private String monSort;
   private LinearLayout monSortBar;
   private final List<Pair<String, String>> monSortCols;
   private GxPage.Row monStatus;
   private LinearLayout monTable;
   private final HashMap<String, GxLogsPage.MonTile> monTiles;
   private TextView monWait;
   private final float[] monWeights;
   private Function1<? super Integer, Unit> selectTab;
   private int tab;
   private boolean tmuxBusy;
   private List<Pair<String, Double>> tmuxFiles;
   private String tmuxLatest;
   private LinearLayout tmuxList;
   private boolean tmuxListLoading;
   private TextView tmuxLog;
   private TextView tmuxPauseBtn;
   private boolean tmuxPaused;
   private TextView tmuxTransport;
   private LinearLayout tsBody;
   private String tsBusySection;
   private TextView tsChangedText;
   private TextView tsErrNote;
   private String tsError;
   private boolean tsLoading;
   private boolean tsOnlyChanged;
   private boolean tsOnroad;
   private TextView tsOnroadText;
   private TextView tsRefreshBtn;
   private boolean tsRefreshing;
   private String tsSearch;
   private final Runnable tsSearchRun;
   private List<? extends JSONObject> tsSections;
   private List<? extends JSONObject> tsSnapshot;
   private JSONObject tsVehicle;

   // $VF: synthetic method
   public static Unit $r8$lambda$_1XGm_7oMLiP6rsf7MCwRK_Fpsw/* $VF was: $r8$lambda$_1XGm_7oMLiP6rsf7MCwRK_Fpsw*/(GxLogsPage var0, GxLogsPage.ErrFile var1) {
      return errRender$lambda$30(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$_d9OvWE9PlTJaTTPCvoR440QGdM/* $VF was: $r8$lambda$_d9OvWE9PlTJaTTPCvoR440QGdM*/(GxLogsPage var0) {
      tsSearchRun$lambda$98(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$0EH859_8GONFdeeLZz_8V7g2WjU/* $VF was: $r8$lambda$0EH859_8GONFdeeLZz_8V7g2WjU*/(GxLogsPage var0) {
      return buildTmux$lambda$39(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$1WjuxbxKs_724n_5UIbSmLkcxB0(GxLogsPage var0) {
      return buildMonitor$lambda$66(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$3POJElo1IGxKM0XyZiq_WCTiOaA(GxLogsPage var0, String var1, JSONObject var2) {
      return errDeleteAll$lambda$38$lambda$37(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$3Zr4VW5tMGjU64N_qfzCU0QFCTE/* $VF was: $r8$lambda$3Zr4VW5tMGjU64N_qfzCU0QFCTE*/(GxLogsPage var0, int var1, LinearLayout var2) {
      return build$lambda$1(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$4JlaJzb_h5ysYKf7_abKvSJY2IQ(String var0, GxLogsPage var1, String var2) {
      return tmuxRename$lambda$57(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$6Rh8QoKL3ms8RsPvzsetk6ciLJs(GxLogsPage var0) {
      return build$lambda$3(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$8SQt7mjKwzC23z5GHF9E_O1CDfI/* $VF was: $r8$lambda$8SQt7mjKwzC23z5GHF9E_O1CDfI*/(GxLogsPage var0) {
      return buildTmux$lambda$41(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$8tdtkLwAEbU9UubOQke_GpHeb6g(GxLogsPage var0) {
      return buildMonitor$lambda$58(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$AQA7qm3trrIotwPw7A42Q91NFoQ(GxLogsPage var0, String var1, String var2) {
      return tsResetSection$lambda$128(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$CKHdVLMDZHRp478idvTNnpDbK5o(GxLogsPage var0, Pair var1) {
      return buildMonitor$lambda$71$lambda$70(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$CTAD4NiulJbJSRjuT3zgXsHyoDU(GxLogsPage var0, GxLogsPage.ErrFile var1, View var2) {
      errRender$lambda$29(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$CZGeLvG8R0hFPMNAJxRwh_rMG9A/* $VF was: $r8$lambda$CZGeLvG8R0hFPMNAJxRwh_rMG9A*/(GxLogsPage var0) {
      errView$lambda$33$lambda$32(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$D5tllTZj5308KUpwlU8zSmBciTU(GxLogsPage var0, String var1) {
      return buildMonitor$lambda$68(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$HfgtsH2JZ3N_XYCEyo7DKY8Yykg/* $VF was: $r8$lambda$HfgtsH2JZ3N_XYCEyo7DKY8Yykg*/(GxLogsPage var0, String var1, JSONObject var2) {
      return tmuxSnapshot$lambda$43(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$I9i4CnlVtivJEVN3Q5XNXfwtLzs(GxLogsPage var0, String var1, String var2, JSONObject var3) {
      return tmuxDelete$lambda$53$lambda$52(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static int $r8$lambda$Kz5JGyS1iaRR8sxIjtbRA4iWop4(GxLogsPage var0, GxLogsProc var1, GxLogsProc var2) {
      return monPaintTable$lambda$91(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$MhAgKKNqbcV_9j9JczH6ZqWXKcU(GxLogsPage var0, long var1, String var3, Object var4) {
      return monRefresh$lambda$75(var0, var1, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$PFR2yUEENAdRLXxn4l5p4R4t76A(GxLogsPage var0) {
      return buildErrors$lambda$18(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Prz6TLOeprTBxpUfJCz7_Ngb0jQ/* $VF was: $r8$lambda$Prz6TLOeprTBxpUfJCz7_Ngb0jQ*/(GxLogsPage var0) {
      return buildDiscord$lambda$97(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$QKjqYS06IYW1J1JPJVtmPoBIAVM(GxLogsPage var0) {
      return build$lambda$2(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$QP55YP8NuXppl017eETDE3F62os(GxLogsPage var0, GxLogsPage.ErrFile var1) {
      return errDelete$lambda$36(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$R6_9AT_OV2Y8P9ehc4GQOrCfdYI/* $VF was: $r8$lambda$R6_9AT_OV2Y8P9ehc4GQOrCfdYI*/(GxLogsPage var0, GxLogsPage.ErrFile var1) {
      return errRender$lambda$31(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$RvyDn37KgP4eYB8MpGpLzPKMj_c/* $VF was: $r8$lambda$RvyDn37KgP4eYB8MpGpLzPKMj_c*/(
      GxLogsPage var0, String var1, String var2, JSONObject var3
   ) {
      return tsResetSection$lambda$128$lambda$127(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static int $r8$lambda$TxmnCGr0oWwvDzjVYMOTRu49UMg(Function2 var0, Object var1, Object var2) {
      return monPaintTable$lambda$92(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$UPXhLU5Uz82c_L_edFr7n9fvoYk/* $VF was: $r8$lambda$UPXhLU5Uz82c_L_edFr7n9fvoYk*/(
      GxLogsPage var0, GxLogsPage.ErrFile var1, String var2, JSONObject var3
   ) {
      return errDelete$lambda$36$lambda$35(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$WBoEwLpdgwzmjkEBTjN_LgwxmNE/* $VF was: $r8$lambda$WBoEwLpdgwzmjkEBTjN_LgwxmNE*/(
      GxLogsPage var0, GxLogsPage.ErrFile var1, String var2, String var3
   ) {
      return errView$lambda$33(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$XjI2OPuKOuuLbFcUQMQrw9Mr5Mc(GxLogsPage var0) {
      return tmuxDeleteAll$lambda$55(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Xz40ABV8lJuaPe9Z1H_yBzBQll4(GxLogsPage var0, String var1) {
      return tmuxRenderList$lambda$49(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$YxX7NWjeTrq_vZqmRG9SGAwKhNQ(GxLogsPage var0, String var1) {
      return tmuxDelete$lambda$53(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$ZSxCh2CzUsZRe1JF3d3L3XzK49Y(GxLogsPage var0, String var1, JSONObject var2) {
      return tmuxCapture$lambda$50(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$_keDAFm57hWFsJPtjVRnu_rPE7g/* $VF was: $r8$lambda$_keDAFm57hWFsJPtjVRnu_rPE7g*/(GxLogsPage var0) {
      return buildTroubleshoot$lambda$99(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$_o84ceN2gIQSYrPqcFfCz4W_wls(Function2 var0, ObjectRef var1, ObjectRef var2) {
      getJsonAccept$lambda$10$lambda$9(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$ao20TbPw3bisIXrz2gMtMU8I_vU(GxLogsPage var0) {
      return buildTroubleshoot$lambda$100(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$eXfak8cXLrNr1Ow86JXNtxS8lig(GxLogsPage var0, String var1, JSONArray var2) {
      return loadTmuxFiles$lambda$46(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$ejVFWVkPrxECBBNyohYW3MM8hPM(GxLogsPage var0, List var1, int var2) {
      return buildMonitor$lambda$69(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$f6OwPKGiwtxwn6TvjVIFBrKgURU(GxLogsPage var0) {
      return buildErrors$lambda$22(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$f8rTPYCmi7t0T7aCy3V76y_m5Tg(GxLogsPage var0, String var1) {
      return tmuxRenderList$lambda$48(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$gf09MhDeFQnAuQyFc7ahv7cJ21k(GxLogsPage var0, boolean var1, String var2, JSONObject var3) {
      return tsLoad$lambda$105(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static void $r8$lambda$iUmmiLwsDCc0Zqn3a332XFiSG9g(String var0, String var1, GxLogsPage var2, Function2 var3) {
      getJsonAccept$lambda$10(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$j5_hZ0YD_WUzqTOAcYaLrV3QRYM/* $VF was: $r8$lambda$j5_hZ0YD_WUzqTOAcYaLrV3QRYM*/(GxLogsPage var0, String var1) {
      return buildTroubleshoot$lambda$103(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$jJTHZjS_n47H2nYspGxDEQglhg4/* $VF was: $r8$lambda$jJTHZjS_n47H2nYspGxDEQglhg4*/(GxLogsPage var0, String var1, JSONObject var2) {
      return tmuxDeleteAll$lambda$55$lambda$54(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$jfLfa87v1FJQhT261Iw3wB_XkDE(GxLogsPage var0, String var1, Object var2) {
      return loadErrors$lambda$27(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$kB6Z5ODkQeY5_pUDd9y2_d8Tl0g(GxLogsPage var0, JSONObject var1) {
      return tsRender$lambda$115(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$kmFmv1bK9VCGvzgYEGIbFrxwltI(GxLogsPage var0, int var1) {
      return build$lambda$0(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$lnM6_MPSKjdLVjE_SMgrJSNZkDU/* $VF was: $r8$lambda$lnM6_MPSKjdLVjE_SMgrJSNZkDU*/(GxLogsPage var0, String var1) {
      return tmuxRenderList$lambda$47(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$mkdYTsC7FuYJtR7uzISDhd2wh5A(GxLogsPage var0, String var1, ObjectRef var2, String var3, JSONObject var4) {
      return tmuxRename$lambda$57$lambda$56(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$pQiOfnZytFjYzQ1e6tORZo8oVhg(GxLogsPage var0) {
      return buildTmux$lambda$40(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$sqYmqLwTr0cGYfzshTh9lse0ics(GxLogsPage var0, boolean var1) {
      return buildTroubleshoot$lambda$101(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$t9N2lMJios0GcACnZFIk6cyyp6U(GxLogsPage var0, String var1) {
      return buildErrors$lambda$17(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$tSfcxs355vw9F0dE3LZQmrNl9hQ(GxLogsPage var0) {
      return errDeleteAll$lambda$38(var0);
   }

   public GxLogsPage(Activity var1, GxHost var2) {
      Intrinsics.checkNotNullParameter(var1, "act");
      Intrinsics.checkNotNullParameter(var2, "gx");
      super(var1, var2);
      this.errFiles = CollectionsKt.emptyList();
      this.errLoading = true;
      this.errFilter = "";
      this.errSelected = "";
      this.errContent = "";
      this.tmuxLatest = "";
      this.tmuxFiles = CollectionsKt.emptyList();
      this.tmuxListLoading = true;
      this.monError = "";
      this.monQuery = "";
      this.monScope = "comma";
      this.monSort = "cpu";
      this.monDesc = true;
      this.monHistory = CollectionsKt.emptyList();
      this.monProcs = CollectionsKt.emptyList();
      this.monTiles = new HashMap<>();
      this.monSortCols = CollectionsKt.listOf(
         new Pair[]{
            TuplesKt.to("name", "Process"),
            TuplesKt.to("pid", "PID"),
            TuplesKt.to("cpu", "CPU"),
            TuplesKt.to("memoryMiB", "Memory"),
            TuplesKt.to("user", "User"),
            TuplesKt.to("state", "Status")
         }
      );
      this.monWeights = new float[]{3.4F, 0.9F, 0.9F, 1.2F, 1.1F, 1.1F};
      this.tsLoading = true;
      this.tsBusySection = "";
      this.tsError = "";
      this.tsSearch = "";
      this.tsVehicle = new JSONObject();
      this.tsSnapshot = CollectionsKt.emptyList();
      this.tsSections = CollectionsKt.emptyList();
      this.tsSearchRun = new GxLogsPage$$ExternalSyntheticLambda5(this);
   }

   private static final Unit build$lambda$0(GxLogsPage var0, int var1) {
      var0.tab = var1;
      if (var0.getShown()) {
         var0.tabShown(var1);
      }

      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$1(GxLogsPage var0, int var1, LinearLayout var2) {
      Intrinsics.checkNotNullParameter(var2, "box");
      switch (var1) {
         case 0:
            var0.buildTroubleshoot(var2);
            break;
         case 1:
            var0.buildErrors(var2);
            break;
         case 2:
            var0.buildTmux(var2);
            break;
         case 3:
            var0.buildMonitor(var2);
            break;
         default:
            var0.buildDiscord(var2);
      }

      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$2(GxLogsPage var0) {
      if (var0.tab == 2) {
         var0.tmuxSnapshot();
      }

      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$3(GxLogsPage var0) {
      if (var0.tab == 3) {
         var0.monRefresh();
      }

      return Unit.INSTANCE;
   }

   private final void buildDiscord(LinearLayout var1) {
      LinearLayout var2 = this.card((ViewGroup)var1);
      this.titleBar("Get Help via Discord", (ViewGroup)var2);
      GxPage.text$default(
         this, "StarPilot has a vibrant, welcoming community discord. Stop by to chat or ask questions!", (ViewGroup)var2, 17.0F, 0, false, 24, null
      );
      GxPage.text$default(this, "firestar.link/discord", (ViewGroup)var2, 15.0F, CarUi.Companion.getACCENT(), false, 16, null);
      Pair var3 = TuplesKt.to("Get Help via Discord", new GxLogsPage$$ExternalSyntheticLambda45(this));
      ViewGroup var4 = (ViewGroup)var2;
      this.buttons(new Pair[]{var3}, var4, true);
   }

   private static final Unit buildDiscord$lambda$97(GxLogsPage var0) {
      try {
         Activity var1 = var0.getAct();
         Intent var2 = new Intent("android.intent.action.VIEW", Uri.parse("https://firestar.link/discord"));
         var1.startActivity(var2);
      } catch (Exception var3) {
         var0.toast("No browser found. Visit https://firestar.link/discord");
      }

      return Unit.INSTANCE;
   }

   private final void buildErrors(LinearLayout var1) {
      LinearLayout var5 = new LinearLayout((Context)this.getAct());
      var5.setOrientation(0);
      var5.setGravity(16);
      var1.addView((View)var5, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, -2)));
      LinearLayout var2 = new LinearLayout((Context)this.getAct());
      var2.setOrientation(1);
      var5.addView((View)var2, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
      this.searchField("Search logs...", (ViewGroup)var2, new GxLogsPage$$ExternalSyntheticLambda20(this));
      TextView var11 = CarUi.button$default(this.getUi(), "Delete All", false, new GxLogsPage$$ExternalSyntheticLambda21(this), 2, null);
      var11.setTextColor(GxLogsKt.access$getGXLOGS_RED$p());
      var11.setVisibility(8);
      this.errDelAll = var11;
      TextView var4 = this.errDelAll;
      Object var3 = null;
      TextView var12 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("errDelAll");
         var12 = null;
      }

      View var16 = (View)var12;
      android.widget.LinearLayout.LayoutParams var6 = new android.widget.LinearLayout.LayoutParams(-2, -2);
      var6.leftMargin = this.getUi().dp(10);
      var6.bottomMargin = this.getUi().dp(8);
      Unit var13 = Unit.INSTANCE;
      var5.addView(var16, (LayoutParams)var6);
      this.errList = this.box((ViewGroup)var1);
      var1 = this.card((ViewGroup)var1);
      var1.setVisibility(8);
      this.errViewCard = var1;
      var2 = this.errViewCard;
      var1 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("errViewCard");
         var1 = null;
      }

      var1 = this.titleBar("", (ViewGroup)var1);
      View var15 = var1.getChildAt(0);
      Intrinsics.checkNotNull(var15, "null cannot be cast to non-null type android.widget.TextView");
      this.errViewTitle = (TextView)var15;
      var1.addView((View)CarUi.button$default(this.getUi(), "Copy", false, new GxLogsPage$$ExternalSyntheticLambda23(this), 2, null));
      var1 = this.errViewCard;
      if (var1 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("errViewCard");
         var1 = (LinearLayout)var3;
      }

      this.errViewLog = this.logBox(520, (ViewGroup)var1);
      this.errRender();
   }

   private static final Unit buildErrors$lambda$17(GxLogsPage var0, String var1) {
      Intrinsics.checkNotNullParameter(var1, "q");
      var0.errFilter = var1;
      var0.errRender();
      return Unit.INSTANCE;
   }

   private static final Unit buildErrors$lambda$18(GxLogsPage var0) {
      var0.errDeleteAll();
      return Unit.INSTANCE;
   }

   private static final Unit buildErrors$lambda$22(GxLogsPage var0) {
      var0.copyText(var0.errContent, "Copied to clipboard!");
      return Unit.INSTANCE;
   }

   private final void buildMonitor(LinearLayout var1) {
      this.monStatus = GxPage.row$default(this, "System Monitor", "Live updates · Captured —", (ViewGroup)var1, 0, 8, null);
      GxPage.Row var4 = this.monStatus;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("monStatus");
         var4 = null;
      }

      this.monPauseBtn = GxPage.Row.button$default(var4, "Pause", false, new GxLogsPage$$ExternalSyntheticLambda22(this), 2, null);
      TextView var35 = this.note("", (ViewGroup)var1);
      var35.setTextColor(GxLogsKt.access$getGXLOGS_RED$p());
      var35.setVisibility(8);
      this.monErrNote = var35;
      this.monWait = this.empty("Reading system activity…");
      TextView var5 = this.monWait;
      TextView var36 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("monWait");
         var36 = null;
      }

      var1.addView((View)var36);
      var1 = this.box((ViewGroup)var1);
      var1.setVisibility(8);
      this.monBody = var1;
      Pair var49 = TuplesKt.to("cpu", "CPU");
      Pair var16 = TuplesKt.to("mem", "Memory");
      Pair var9 = TuplesKt.to("procs", "Processes");
      Pair var6 = TuplesKt.to("storage", "Storage");
      Pair var8 = TuplesKt.to("cpuTemp", "Onboard CPU temperature");
      Pair var10 = TuplesKt.to("gpuTemp", "Onboard GPU temperature");
      Pair var7 = TuplesKt.to("hotspot", "eGPU hotspot temperature");
      Pair var11 = TuplesKt.to("edge", "eGPU temperature");
      String var37 = "vram";
      Iterator var65 = CollectionsKt.listOf(new Pair[]{var49, var16, var9, var6, var8, var10, var7, var11, TuplesKt.to("vram", "eGPU VRAM")}).iterator();
      var1 = null;

      for (int var2 = 0; var65.hasNext(); var2++) {
         var8 = (Pair)var65.next();
         if (var2 % 3 == 0) {
            LinearLayout var50 = new LinearLayout((Context)this.getAct());
            var50.setOrientation(0);
            LinearLayout var57 = this.monBody;
            var1 = var57;
            if (var57 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("monBody");
               var1 = null;
            }

            var1.addView((View)var50, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, -2)));
            var1 = var50;
         }

         LinearLayout var68 = new LinearLayout((Context)this.getAct());
         var68.setOrientation(1);
         var68.setBackground((Drawable)CarUi.rounded$default(this.getUi(), CarUi.Companion.getCARD(), 0, null, 6, null));
         var68.setPadding(this.getUi().dp(18), this.getUi().dp(14), this.getUi().dp(18), this.getUi().dp(14));
         var68.setMinimumHeight(this.getUi().dp(130));
         Intrinsics.checkNotNull(var1);
         View var51 = (View)var68;
         android.widget.LinearLayout.LayoutParams var58 = new android.widget.LinearLayout.LayoutParams(0, -1, 1.0F);
         int var3;
         if (var2 % 3 < 2) {
            var3 = this.getUi().dp(8);
         } else {
            var3 = 0;
         }

         var58.rightMargin = var3;
         var58.bottomMargin = this.getUi().dp(8);
         Unit var69 = Unit.INSTANCE;
         var1.addView(var51, (LayoutParams)var58);
         GxPage.text$default(this, (String)var8.getSecond(), (ViewGroup)var68, 0.0F, 0, false, 28, null);
         TextView var71 = this.text("—", (ViewGroup)var68, 28.0F, CarUi.Companion.getTEXT(), true);
         TextView var70 = GxPage.text$default(this, "", (ViewGroup)var68, 14.0F, 0, false, 24, null);
         ProgressBar var52;
         if (!Intrinsics.areEqual(var8.getFirst(), "mem") && !Intrinsics.areEqual(var8.getFirst(), var37)) {
            var52 = null;
         } else {
            var52 = this.progress((ViewGroup)var68);
         }

         GxLogsSpark var59;
         if (Intrinsics.areEqual(var8.getFirst(), "cpu")) {
            var59 = new GxLogsSpark((Context)this.getAct());
            View var12 = var59;
            android.widget.LinearLayout.LayoutParams var13 = new android.widget.LinearLayout.LayoutParams(-1, this.getUi().dp(40));
            var13.topMargin = this.getUi().dp(4);
            Unit var14 = Unit.INSTANCE;
            var68.addView(var12, (LayoutParams)var13);
         } else {
            var59 = null;
         }

         this.monTiles.put((String)var8.getFirst(), new GxLogsPage.MonTile(var68, var71, var70, var52, var59));
      }

      LinearLayout var38 = this.monBody;
      var1 = var38;
      if (var38 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("monBody");
         var1 = null;
      }

      LinearLayout var60 = this.card((ViewGroup)var1);
      LinearLayout var53 = this.titleBar("CPU cores", (ViewGroup)var60);
      this.monCoresBtn = CarUi.button$default(this.getUi(), "Show", false, new GxLogsPage$$ExternalSyntheticLambda33(this), 2, null);
      TextView var39 = this.monCoresBtn;
      TextView var20 = var39;
      if (var39 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("monCoresBtn");
         var20 = null;
      }

      var53.addView((View)var20);
      var1 = this.box((ViewGroup)var60);
      var1.setVisibility(8);
      this.monCoresBox = var1;
      LinearLayout var40 = this.monBody;
      var1 = var40;
      if (var40 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("monBody");
         var1 = null;
      }

      this.searchField("Search feature, process, PID or user…", (ViewGroup)var1, new GxLogsPage$$ExternalSyntheticLambda44(this));
      List var61 = CollectionsKt.listOf(new String[]{"comma", "users", "all"});
      List var54 = CollectionsKt.listOf(new String[]{"Comma processes", "Apps and services", "All processes"});
      LinearLayout var41 = this.monBody;
      var1 = var41;
      if (var41 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("monBody");
         var1 = null;
      }

      this.choice("Process group", null, var54, 0, (ViewGroup)var1, new GxLogsPage$$ExternalSyntheticLambda47(this, var61));
      LinearLayout var42 = this.monBody;
      var1 = var42;
      if (var42 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("monBody");
         var1 = null;
      }

      this.monCountNote = this.note("", (ViewGroup)var1);
      LinearLayout var43 = this.monBody;
      var1 = var43;
      if (var43 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("monBody");
         var1 = null;
      }

      this.header("Sort by", (ViewGroup)var1);
      GxPage var55 = this;
      Iterable var44 = this.monSortCols;
      var var26 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var44, 10));

      for (Pair var45 : var44) {
         var26.add(TuplesKt.to(var45.getSecond(), new GxLogsPage$$ExternalSyntheticLambda48(this, var45)));
      }

      List var27 = var26;
      Collection var28 = var27;
      Pair[] var29 = var28.toArray(new Pair[0]);
      Pair[] var63 = Arrays.copyOf(var29, var29.length);
      LinearLayout var46 = this.monBody;
      var1 = var46;
      if (var46 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("monBody");
         var1 = null;
      }

      this.monSortBar = GxPage.buttons$default(var55, var63, (ViewGroup)var1, false, 4, null);
      LinearLayout var47 = this.monBody;
      var1 = var47;
      if (var47 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("monBody");
         var1 = null;
      }

      LinearLayout var56 = this.card((ViewGroup)var1);
      var1 = new LinearLayout((Context)this.getAct());
      var1.setOrientation(0);
      var1.setPadding(0, this.getUi().dp(4), 0, this.getUi().dp(8));
      Iterator var66 = this.monSortCols.iterator();
      int var33 = 0;

      while (true) {
         int var34 = var33;
         if (!var66.hasNext()) {
            var56.addView((View)var1);
            this.monTable = this.box((ViewGroup)var56);
            this.monPaintSortBar();
            return;
         }

         var33++;
         var6 = (Pair)var66.next();
         TextView var48 = new TextView((Context)this.getAct());
         var48.setText((CharSequence)var6.getSecond());
         var48.setTextSize(15.0F);
         var48.setTextColor(CarUi.Companion.getDIM());
         var48.setTypeface(Typeface.DEFAULT_BOLD);
         var1.addView((View)var48, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, this.monWeights[var34])));
      }
   }

   private static final Unit buildMonitor$lambda$58(GxLogsPage var0) {
      var0.monTogglePause();
      return Unit.INSTANCE;
   }

   private static final Unit buildMonitor$lambda$66(GxLogsPage var0) {
      var0.monCoresOpen ^= true;
      TextView var3 = var0.monCoresBtn;
      Object var4 = null;
      TextView var2 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("monCoresBtn");
         var2 = null;
      }

      String var6;
      if (var0.monCoresOpen) {
         var6 = "Hide";
      } else {
         var6 = "Show";
      }

      var2.setText(var6);
      LinearLayout var5 = var0.monCoresBox;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("monCoresBox");
         var5 = (LinearLayout)var4;
      }

      byte var1;
      if (var0.monCoresOpen) {
         var1 = 0;
      } else {
         var1 = 8;
      }

      var5.setVisibility(var1);
      var0.monPaintCores();
      return Unit.INSTANCE;
   }

   private static final Unit buildMonitor$lambda$68(GxLogsPage var0, String var1) {
      Intrinsics.checkNotNullParameter(var1, "q");
      var0.monQuery = var1;
      var0.monPaintTable();
      return Unit.INSTANCE;
   }

   private static final Unit buildMonitor$lambda$69(GxLogsPage var0, List var1, int var2) {
      var0.monScope = (String)var1.get(var2);
      var0.monPaintTable();
      return Unit.INSTANCE;
   }

   private static final Unit buildMonitor$lambda$71$lambda$70(GxLogsPage var0, Pair var1) {
      var0.monSortBy((String)var1.getFirst());
      return Unit.INSTANCE;
   }

   private final void buildTmux(LinearLayout var1) {
      LinearLayout var4 = this.card((ViewGroup)var1);
      LinearLayout var2 = this.titleBar("Tmux Live Log", (ViewGroup)var4);
      this.tmuxTransport = this.chip("polling", CarUi.Companion.getDIM(), (ViewGroup)var2);
      GxPage.text$default(this, "Newest lines are at the top.", (ViewGroup)var4, 0.0F, 0, false, 28, null);
      this.tmuxLog = this.logBox(520, (ViewGroup)var4);
      TextView var3 = this.tmuxLog;
      TextView var8 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("tmuxLog");
         var8 = null;
      }

      var8.setText("(waiting for log output…)");
      GxPage var6 = this;
      Pair var5 = TuplesKt.to("Pause", new GxLogsPage$$ExternalSyntheticLambda41(this));
      Pair var11 = TuplesKt.to("Capture Log", new GxLogsPage$$ExternalSyntheticLambda42(this));
      Pair var9 = TuplesKt.to("Delete All", new GxLogsPage$$ExternalSyntheticLambda43(this));
      ViewGroup var12 = (ViewGroup)var4;
      View var10 = GxPage.buttons$default(var6, new Pair[]{var5, var11, var9}, var12, false, 4, null).getChildAt(0);
      Intrinsics.checkNotNull(var10, "null cannot be cast to non-null type android.widget.TextView");
      this.tmuxPauseBtn = (TextView)var10;
      var1 = this.card((ViewGroup)var1);
      this.titleBar("Saved Session Logs", (ViewGroup)var1);
      this.tmuxList = this.box((ViewGroup)var1);
      this.tmuxRenderList();
   }

   private static final Unit buildTmux$lambda$39(GxLogsPage var0) {
      var0.tmuxTogglePause();
      return Unit.INSTANCE;
   }

   private static final Unit buildTmux$lambda$40(GxLogsPage var0) {
      var0.tmuxCapture();
      return Unit.INSTANCE;
   }

   private static final Unit buildTmux$lambda$41(GxLogsPage var0) {
      var0.tmuxDeleteAll();
      return Unit.INSTANCE;
   }

   private final void buildTroubleshoot(LinearLayout var1) {
      LinearLayout var2 = this.card((ViewGroup)var1);
      GxPage.text$default(this, "Quick diagnostics snapshot for weird behavior reports and copy-ready debug logs.", (ViewGroup)var2, 17.0F, 0, false, 24, null);
      GxPage var3 = this;
      Pair var5 = TuplesKt.to("Refresh", new GxLogsPage$$ExternalSyntheticLambda14(this));
      Pair var6 = TuplesKt.to("Copy to Clipboard", new GxLogsPage$$ExternalSyntheticLambda15(this));
      ViewGroup var4 = (ViewGroup)var2;
      View var7 = GxPage.buttons$default(var3, new Pair[]{var5, var6}, var4, false, 4, null).getChildAt(0);
      Intrinsics.checkNotNull(var7, "null cannot be cast to non-null type android.widget.TextView");
      this.tsRefreshBtn = (TextView)var7;
      this.toggle("Only non-default values", null, false, (ViewGroup)var2, new GxLogsPage$$ExternalSyntheticLambda16(this))
         .getFirst()
         .getView()
         .setBackground((Drawable)CarUi.rounded$default(this.getUi(), CarUi.Companion.getCARD_ON(), 14, null, 4, null));
      LinearLayout var8 = new LinearLayout((Context)this.getAct());
      var8.setOrientation(1);
      var8.setPadding(0, this.getUi().dp(8), 0, 0);
      var2.addView((View)var8);
      this.searchField("Search settings or categories...", (ViewGroup)var8, new GxLogsPage$$ExternalSyntheticLambda17(this));
      TextView var9 = GxPage.text$default(this, "", (ViewGroup)var2, 15.0F, GxLogsKt.access$getGXLOGS_RED$p(), false, 16, null);
      var9.setVisibility(8);
      this.tsErrNote = var9;
      this.tsOnroadText = GxPage.text$default(this, "Onroad: No", (ViewGroup)var2, 0.0F, 0, false, 28, null);
      this.tsChangedText = GxPage.text$default(this, "Changed Settings: 0", (ViewGroup)var2, 0.0F, 0, false, 28, null);
      this.tsBody = this.box((ViewGroup)var1);
      this.tsRender();
   }

   private static final Unit buildTroubleshoot$lambda$100(GxLogsPage var0) {
      var0.tsCopyReport();
      return Unit.INSTANCE;
   }

   private static final Unit buildTroubleshoot$lambda$101(GxLogsPage var0, boolean var1) {
      var0.tsOnlyChanged = var1;
      var0.tsRender();
      return Unit.INSTANCE;
   }

   private static final Unit buildTroubleshoot$lambda$103(GxLogsPage var0, String var1) {
      Intrinsics.checkNotNullParameter(var1, "q");
      var0.tsSearch = var1;
      var0.getMain().removeCallbacks(var0.tsSearchRun);
      var0.getMain().postDelayed(var0.tsSearchRun, 250L);
      return Unit.INSTANCE;
   }

   private static final Unit buildTroubleshoot$lambda$99(GxLogsPage var0) {
      var0.tsLoad(true);
      return Unit.INSTANCE;
   }

   private final TextView chip(String var1, int var2, ViewGroup var3) {
      TextView var4 = new TextView((Context)this.getAct());
      var4.setText(var1);
      var4.setTextSize(14.0F);
      var4.setTypeface(Typeface.DEFAULT_BOLD);
      var4.setTextColor(CarUi.Companion.getBG());
      var4.setBackground((Drawable)CarUi.rounded$default(this.getUi(), var2, 10, null, 4, null));
      var4.setPadding(this.getUi().dp(10), this.getUi().dp(4), this.getUi().dp(10), this.getUi().dp(4));
      View var7 = (View)var4;
      android.widget.LinearLayout.LayoutParams var5 = new android.widget.LinearLayout.LayoutParams(-2, -2);
      var5.leftMargin = this.getUi().dp(10);
      Unit var6 = Unit.INSTANCE;
      var3.addView(var7, (LayoutParams)var5);
      return var4;
   }

   private final void copyText(String var1, String var2) {
      try {
         Object var3 = this.getAct().getSystemService("clipboard");
         Intrinsics.checkNotNull(var3, "null cannot be cast to non-null type android.content.ClipboardManager");
         ((ClipboardManager)var3).setPrimaryClip(ClipData.newPlainText("StarView", var1));
         this.toast(var2);
      } catch (Exception var4) {
         var1 = var4.getMessage();
         this.toast("Failed to copy: " + var1);
      }
   }

   private final void errDelete(GxLogsPage.ErrFile var1) {
      String var2 = var1.getFilename();
      this.confirm("Delete log?", "Delete " + var2 + "?", "Delete", new GxLogsPage$$ExternalSyntheticLambda49(this, var1));
   }

   private static final Unit errDelete$lambda$36(GxLogsPage var0, GxLogsPage.ErrFile var1) {
      GxPage var2 = var0;
      String var3 = var0.enc(var1.getFilename());
      GxPage.send$default(var2, "DELETE", "/api/error_logs/" + var3, null, 0L, new GxLogsPage$$ExternalSyntheticLambda31(var0, var1), 12, null);
      return Unit.INSTANCE;
   }

   private static final Unit errDelete$lambda$36$lambda$35(GxLogsPage var0, GxLogsPage.ErrFile var1, String var2, JSONObject var3) {
      if (var2 != null) {
         var0.toast(var2);
         return Unit.INSTANCE;
      } else {
         Iterable var7 = var0.errFiles;
         var var5 = new ArrayList();

         for (Object var4 : var7) {
            if (!Intrinsics.areEqual(((GxLogsPage.ErrFile)var4).getFilename(), var1.getFilename())) {
               var5.add(var4);
            }
         }

         List var6 = var5;
         var0.errFiles = var6;
         if (Intrinsics.areEqual(var0.errSelected, var1.getFilename())) {
            var0.errSelected = "";
         }

         var0.errRender();
         var0.toast("Log deleted!");
         return Unit.INSTANCE;
      }
   }

   private final void errDeleteAll() {
      this.confirm("Delete all error logs?", "This cannot be undone.", "Delete All", new GxLogsPage$$ExternalSyntheticLambda6(this));
   }

   private static final Unit errDeleteAll$lambda$38(GxLogsPage var0) {
      GxPage.send$default(var0, "DELETE", "/api/error_logs/delete_all", null, 0L, new GxLogsPage$$ExternalSyntheticLambda28(var0), 12, null);
      return Unit.INSTANCE;
   }

   private static final Unit errDeleteAll$lambda$38$lambda$37(GxLogsPage var0, String var1, JSONObject var2) {
      if (var1 != null) {
         var0.toast("Delete all failed.");
         return Unit.INSTANCE;
      } else {
         var0.errFiles = CollectionsKt.emptyList();
         var0.errSelected = "";
         var0.errRender();
         var0.toast("All error logs deleted!");
         return Unit.INSTANCE;
      }
   }

   private final void errRender() {
      if (this.errList != null) {
         LinearLayout var6 = this.errList;
         Object var5 = null;
         LinearLayout var4 = var6;
         if (var6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("errList");
            var4 = null;
         }

         var4.removeAllViews();
         TextView var22 = this.errDelAll;
         TextView var14 = var22;
         if (var22 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("errDelAll");
            var14 = null;
         }

         boolean var3 = this.errFiles.isEmpty();
         boolean var2 = false;
         byte var1;
         if (!var3) {
            var1 = 0;
         } else {
            var1 = 8;
         }

         var14.setVisibility(var1);
         String var15 = this.errFilter.toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var15, "toLowerCase(...)");
         boolean var12;
         if (var15.length() == 0) {
            var12 = true;
         } else {
            var12 = false;
         }

         List var16;
         if (var12) {
            var16 = this.errFiles;
         } else {
            Iterable var7 = this.errFiles;
            var var23 = new ArrayList();

            for (Object var8 : var7) {
               String var9 = ((GxLogsPage.ErrFile)var8).getFilename().toLowerCase(Locale.ROOT);
               Intrinsics.checkNotNullExpressionValue(var9, "toLowerCase(...)");
               if (StringsKt.contains$default(var9, var15, false, 2, null)) {
                  var23.add(var8);
               }
            }

            var16 = var23;
         }

         if (this.errLoading) {
            var6 = this.errList;
            var4 = var6;
            if (var6 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("errList");
               var4 = null;
            }

            var4.addView((View)this.empty("Loading..."));
         } else if (var16.isEmpty()) {
            var6 = this.errList;
            var4 = var6;
            if (var6 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("errList");
               var4 = null;
            }

            var4.addView((View)this.empty("No error logs!"));
         } else {
            for (GxLogsPage.ErrFile var28 : var16) {
               GxPage var11 = this;
               String var10 = var28.getDate();
               String var30 = var28.getFilename();
               var6 = this.errList;
               var4 = var6;
               if (var6 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("errList");
                  var4 = null;
               }

               GxPage.Row var20 = GxPage.row$default(var11, var10, var30, (ViewGroup)var4, 0, 8, null);
               if (Intrinsics.areEqual(var28.getFilename(), this.errSelected)) {
                  var20.getView().setBackground((Drawable)this.getUi().rounded(CarUi.Companion.getCARD_ON(), 14, CarUi.Companion.getACCENT()));
               }

               var20.getView().setOnClickListener(new GxLogsPage$$ExternalSyntheticLambda8(this, var28));
               GxPage.Row.button$default(var20, "Download", false, new GxLogsPage$$ExternalSyntheticLambda9(this, var28), 2, null);
               GxPage.Row.button$default(var20, "Delete", false, new GxLogsPage$$ExternalSyntheticLambda10(this, var28), 2, null)
                  .setTextColor(GxLogsKt.access$getGXLOGS_RED$p());
            }
         }

         var12 = var2;
         if (this.errSelected.length() == 0) {
            var12 = true;
         }

         if (var12) {
            var4 = this.errViewCard;
            if (var4 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("errViewCard");
               var4 = (LinearLayout)var5;
            }

            var4.setVisibility(8);
         }
      }
   }

   private static final void errRender$lambda$29(GxLogsPage var0, GxLogsPage.ErrFile var1, View var2) {
      var0.errView(var1);
   }

   private static final Unit errRender$lambda$30(GxLogsPage var0, GxLogsPage.ErrFile var1) {
      String var2 = var0.enc(var1.getFilename());
      var0.download("/api/error_logs/" + var2, var1.getFilename(), "text/plain");
      return Unit.INSTANCE;
   }

   private static final Unit errRender$lambda$31(GxLogsPage var0, GxLogsPage.ErrFile var1) {
      var0.errDelete(var1);
      return Unit.INSTANCE;
   }

   private final void errView(GxLogsPage.ErrFile var1) {
      this.errSelected = var1.getFilename();
      this.errRender();
      LinearLayout var4 = this.errViewCard;
      Object var3 = null;
      LinearLayout var2 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("errViewCard");
         var2 = null;
      }

      var2.setVisibility(0);
      TextView var8 = this.errViewTitle;
      TextView var5 = var8;
      if (var8 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("errViewTitle");
         var5 = null;
      }

      var5.setText(var1.getFilename());
      TextView var6 = this.errViewLog;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("errViewLog");
         var6 = (TextView)var3;
      }

      var6.setText("Loading...");
      String var7 = this.enc(var1.getFilename());
      this.getText("/api/error_logs/" + var7, new GxLogsPage$$ExternalSyntheticLambda35(this, var1));
   }

   private static final Unit errView$lambda$33(GxLogsPage var0, GxLogsPage.ErrFile var1, String var2, String var3) {
      Intrinsics.checkNotNullParameter(var3, "t");
      if (!Intrinsics.areEqual(var0.errSelected, var1.getFilename())) {
         return Unit.INSTANCE;
      } else {
         String var4;
         if (var2 != null) {
            var4 = "Could not load this log.";
         } else {
            var4 = var3;
         }

         var0.errContent = var4;
         TextView var8 = var0.errViewLog;
         Object var7 = null;
         TextView var5 = var8;
         if (var8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("errViewLog");
            var5 = null;
         }

         var5.setText(var0.errContent);
         TextView var6 = var0.errViewLog;
         if (var6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("errViewLog");
            var6 = (TextView)var7;
         }

         var0.logToEnd(var6);
         var0.getMain().post(new GxLogsPage$$ExternalSyntheticLambda46(var0));
         return Unit.INSTANCE;
      }
   }

   private static final void errView$lambda$33$lambda$32(GxLogsPage var0) {
      int var1 = 0;
      LinearLayout var3 = var0.errViewCard;
      LinearLayout var2 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("errViewCard");
         var2 = null;
      }

      View var4 = (View)var2;

      while (var4 != null && var4 != var0.getCol()) {
         var1 += var4.getTop();
         ViewParent var5 = var4.getParent();
         if (var5 instanceof View) {
            var4 = (View)var5;
         } else {
            var4 = null;
         }
      }

      var0.getScroll().smoothScrollTo(0, var1);
   }

   private final void getJsonAccept(String var1, Function2<? super String, Object, Unit> var2) {
      String var3 = this.getHost();
      if (var3 == null) {
         GxLogsPage var4 = this;
         var2.invoke("Not connected", null);
      } else {
         new Thread(new GxLogsPage$$ExternalSyntheticLambda12(var3, var1, this, var2)).start();
      }
   }

   private static final void getJsonAccept$lambda$10(String param0, String param1, GxLogsPage param2, Function2 param3) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:385)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:551)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 000: new kotlin/jvm/internal/Ref$ObjectRef
      // 003: dup
      // 004: invokespecial kotlin/jvm/internal/Ref$ObjectRef.<init> ()V
      // 007: astore 5
      // 009: new kotlin/jvm/internal/Ref$ObjectRef
      // 00c: dup
      // 00d: invokespecial kotlin/jvm/internal/Ref$ObjectRef.<init> ()V
      // 010: astore 6
      // 012: new okhttp3/Request$Builder
      // 015: astore 8
      // 017: aload 8
      // 019: invokespecial okhttp3/Request$Builder.<init> ()V
      // 01c: new java/lang/StringBuilder
      // 01f: astore 7
      // 021: aload 7
      // 023: invokespecial java/lang/StringBuilder.<init> ()V
      // 026: aload 8
      // 028: aload 7
      // 02a: ldc_w "http://"
      // 02d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 030: aload 0
      // 031: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 034: ldc_w ":8082"
      // 037: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 03a: aload 1
      // 03b: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 03e: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 041: invokevirtual okhttp3/Request$Builder.url (Ljava/lang/String;)Lokhttp3/Request$Builder;
      // 044: ldc_w "Accept"
      // 047: ldc_w "application/json"
      // 04a: invokevirtual okhttp3/Request$Builder.header (Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;
      // 04d: invokevirtual okhttp3/Request$Builder.build ()Lokhttp3/Request;
      // 050: astore 0
      // 051: getstatic com/starpilot/starview/GxPage.Companion Lcom/starpilot/starview/GxPage$Companion;
      // 054: ldc2_w 20
      // 057: invokevirtual com/starpilot/starview/GxPage$Companion.client (J)Lokhttp3/OkHttpClient;
      // 05a: aload 0
      // 05b: invokevirtual okhttp3/OkHttpClient.newCall (Lokhttp3/Request;)Lokhttp3/Call;
      // 05e: invokeinterface okhttp3/Call.execute ()Lokhttp3/Response; 1
      // 063: checkcast java/io/Closeable
      // 066: astore 7
      // 068: aload 7
      // 06a: checkcast okhttp3/Response
      // 06d: astore 8
      // 06f: aload 8
      // 071: invokevirtual okhttp3/Response.body ()Lokhttp3/ResponseBody;
      // 074: astore 0
      // 075: aload 0
      // 076: ifnull 084
      // 079: aload 0
      // 07a: invokevirtual okhttp3/ResponseBody.string ()Ljava/lang/String;
      // 07d: astore 1
      // 07e: aload 1
      // 07f: astore 0
      // 080: aload 1
      // 081: ifnonnull 088
      // 084: ldc_w ""
      // 087: astore 0
      // 088: aload 8
      // 08a: invokevirtual okhttp3/Response.isSuccessful ()Z
      // 08d: ifne 0b6
      // 090: aload 8
      // 092: invokevirtual okhttp3/Response.code ()I
      // 095: istore 4
      // 097: new java/lang/StringBuilder
      // 09a: astore 0
      // 09b: aload 0
      // 09c: invokespecial java/lang/StringBuilder.<init> ()V
      // 09f: aload 5
      // 0a1: aload 0
      // 0a2: ldc_w "HTTP "
      // 0a5: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0a8: iload 4
      // 0aa: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 0ad: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 0b0: putfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 0b3: goto 0ec
      // 0b6: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 0b9: astore 1
      // 0ba: new org/json/JSONTokener
      // 0bd: astore 1
      // 0be: aload 1
      // 0bf: aload 0
      // 0c0: invokespecial org/json/JSONTokener.<init> (Ljava/lang/String;)V
      // 0c3: aload 1
      // 0c4: invokevirtual org/json/JSONTokener.nextValue ()Ljava/lang/Object;
      // 0c7: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 0ca: astore 0
      // 0cb: goto 0db
      // 0ce: astore 0
      // 0cf: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 0d2: astore 1
      // 0d3: aload 0
      // 0d4: invokestatic kotlin/ResultKt.createFailure (Ljava/lang/Throwable;)Ljava/lang/Object;
      // 0d7: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 0da: astore 0
      // 0db: aload 0
      // 0dc: astore 1
      // 0dd: aload 0
      // 0de: invokestatic kotlin/Result.isFailure-impl (Ljava/lang/Object;)Z
      // 0e1: ifeq 0e6
      // 0e4: aconst_null
      // 0e5: astore 1
      // 0e6: aload 6
      // 0e8: aload 1
      // 0e9: putfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 0ec: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 0ef: astore 0
      // 0f0: aload 7
      // 0f2: aconst_null
      // 0f3: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 0f6: goto 11f
      // 0f9: astore 1
      // 0fa: aload 1
      // 0fb: athrow
      // 0fc: astore 0
      // 0fd: aload 7
      // 0ff: aload 1
      // 100: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 103: aload 0
      // 104: athrow
      // 105: astore 7
      // 107: aload 7
      // 109: invokevirtual java/lang/Exception.getMessage ()Ljava/lang/String;
      // 10c: astore 1
      // 10d: aload 1
      // 10e: astore 0
      // 10f: aload 1
      // 110: ifnonnull 119
      // 113: aload 7
      // 115: invokevirtual java/lang/Exception.toString ()Ljava/lang/String;
      // 118: astore 0
      // 119: aload 5
      // 11b: aload 0
      // 11c: putfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 11f: aload 2
      // 120: invokevirtual com/starpilot/starview/GxLogsPage.getMain ()Landroid/os/Handler;
      // 123: new com/starpilot/starview/GxLogsPage$$ExternalSyntheticLambda7
      // 126: dup
      // 127: aload 3
      // 128: aload 5
      // 12a: aload 6
      // 12c: invokespecial com/starpilot/starview/GxLogsPage$$ExternalSyntheticLambda7.<init> (Lkotlin/jvm/functions/Function2;Lkotlin/jvm/internal/Ref$ObjectRef;Lkotlin/jvm/internal/Ref$ObjectRef;)V
      // 12f: invokevirtual android/os/Handler.post (Ljava/lang/Runnable;)Z
      // 132: pop
      // 133: return
   }

   private static final void getJsonAccept$lambda$10$lambda$9(Function2 var0, ObjectRef var1, ObjectRef var2) {
      var0.invoke(var1.element, var2.element);
   }

   private final String jsNum(double var1) {
      boolean var3;
      if (var1 == Math.floor(var1)) {
         var3 = true;
      } else {
         var3 = false;
      }

      String var4;
      if (var3 && !Double.isInfinite(var1) && Math.abs(var1) < 1.0E15) {
         var4 = String.valueOf((long)var1);
      } else {
         var4 = String.valueOf(var1);
      }

      return var4;
   }

   private final void loadErrors() {
      this.getJsonAccept("/api/error_logs", new GxLogsPage$$ExternalSyntheticLambda18(this));
   }

   private static final Unit loadErrors$lambda$27(GxLogsPage var0, String var1, Object var2) {
      var0.errLoading = false;
      if (var1 == null && var2 instanceof JSONArray) {
         var2 = var0.strings((JSONArray)var2);
         var var4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var2, 10));

         for (String var3 : var2) {
            var4.add(new GxLogsPage.ErrFile(var3, var0.parseLogDate(var3)));
         }

         List var5 = var4;
         var0.errFiles = var5;
      } else {
         var0.toast("Failed to load error logs.");
      }

      var0.errRender();
      return Unit.INSTANCE;
   }

   private final void loadTmuxFiles() {
      this.getArray("/api/tmux_log/list", new GxLogsPage$$ExternalSyntheticLambda19(this));
   }

   private static final Unit loadTmuxFiles$lambda$46(GxLogsPage var0, String var1, JSONArray var2) {
      List var4;
      if (var1 == null && var2 != null) {
         Iterable var6 = var0.objects(var2);
         var var5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var6, 10));

         for (JSONObject var7 : var6) {
            var5.add(TuplesKt.to(GxPage.str$default(var0, var7, "filename", null, 2, null), var7.optDouble("timestamp", 0.0)));
         }

         var4 = var5;
      } else {
         var4 = CollectionsKt.emptyList();
      }

      var0.tmuxFiles = var4;
      var0.tmuxListLoading = false;
      var0.tmuxRenderList();
      return Unit.INSTANCE;
   }

   private final int monCompare(GxLogsProc var1, GxLogsProc var2) {
      Object var4 = monCompare$key(this, var1);
      Object var5 = monCompare$key(this, var2);
      int var3 = 1;
      if (var4 == null) {
         if (var5 == null) {
            var3 = var1.getPid() - var2.getPid();
         }

         return var3;
      } else if (var5 == null) {
         return -1;
      } else {
         if (var4 instanceof Double && var5 instanceof Double) {
            var3 = Double.compare(((Number)var4).doubleValue(), ((Number)var5).doubleValue());
         } else {
            var3 = StringsKt.compareTo(var4.toString(), var5.toString(), true);
         }

         if (this.monDesc) {
            var3 = -var3;
         }

         if (var3 == 0) {
            var3 = var1.getPid() - var2.getPid();
         }

         return var3;
      }
   }

   private static final Object monCompare$key(GxLogsPage var0, GxLogsProc var1) {
      String var2 = var0.monSort;
      switch (var2.hashCode()) {
         case 98728:
            if (var2.equals("cpu")) {
               return var1.getCpu();
            }
            break;
         case 110987:
            if (var2.equals("pid")) {
               return (double)var1.getPid();
            }
            break;
         case 3373707:
            if (var2.equals("name")) {
               return var1.getName();
            }
            break;
         case 3599307:
            if (var2.equals("user")) {
               return var1.getUser();
            }
            break;
         case 1726596453:
            if (var2.equals("memoryMiB")) {
               return var1.getMem();
            }
      }

      return var1.getState();
   }

   private final String monFeature(String var1, boolean var2) {
      String var3 = "";
      if (var2) {
         return "";
      } else {
         var1 = StringsKt.removePrefix(
            StringsKt.replace$default(
               StringsKt.removeSuffix(StringsKt.removePrefix(StringsKt.removePrefix(var1, "/data/openpilot/"), "./"), ".py"), "/", ".", false, 4, null
            ),
            "openpilot."
         );
         var1 = (String)GxLogsKt.access$getGXLOGS_FEATURES$p().get(var1);
         if (var1 == null) {
            var1 = var3;
         }

         return var1;
      }
   }

   private final String monNumber(Double var1, String var2) {
      String var4;
      if (var1 == null) {
         var4 = "—";
      } else {
         StringCompanionObject var3 = StringCompanionObject.INSTANCE;
         String var5 = String.format(Locale.US, "%.1f", Arrays.copyOf(new Object[]{var1}, 1));
         Intrinsics.checkNotNullExpressionValue(var5, "format(...)");
         var4 = var5 + var2;
      }

      return var4;
   }

   // $VF: synthetic method
   static String monNumber$default(GxLogsPage var0, Double var1, String var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = "";
      }

      return var0.monNumber(var1, var2);
   }

   private final void monPaintAll() {
      this.monPaintStatus();
      this.monPaintVitals();
      this.monPaintCores();
      this.monPaintTable();
   }

   private final void monPaintCores() {
      if (this.monCoresOpen) {
         List var12;
         label64: {
            JSONObject var5 = this.monSnap;
            if (var5 != null) {
               JSONArray var11 = var5.optJSONArray("cores");
               if (var11 != null) {
                  List var6 = this.objects(var11);
                  var12 = var6;
                  if (var6 != null) {
                     break label64;
                  }
               }
            }

            var12 = CollectionsKt.emptyList();
         }

         while (true) {
            LinearLayout var7 = this.monCoresBox;
            LinearLayout var21 = var7;
            if (var7 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("monCoresBox");
               var21 = null;
            }

            if (var21.getChildCount() <= var12.size()) {
               Iterator var25 = var12.iterator();
               int var3 = 0;

               while (var25.hasNext()) {
                  int var4 = var3 + 1;
                  JSONObject var10 = (JSONObject)var25.next();
                  LinearLayout var8 = this.monCoresBox;
                  LinearLayout var13 = var8;
                  if (var8 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("monCoresBox");
                     var13 = null;
                  }

                  View var14 = var13.getChildAt(var3);
                  LinearLayout var15;
                  if (var14 instanceof LinearLayout) {
                     var15 = (LinearLayout)var14;
                  } else {
                     var15 = null;
                  }

                  var8 = var15;
                  if (var15 == null) {
                     var8 = new LinearLayout((Context)this.getAct());
                     var8.setOrientation(0);
                     var8.setGravity(16);
                     var8.setPadding(0, this.getUi().dp(4), 0, this.getUi().dp(4));
                     TextView var16 = new TextView((Context)this.getAct());
                     var16.setTextSize(16.0F);
                     var16.setTextColor(CarUi.Companion.getTEXT());
                     var8.addView((View)var16, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(this.getUi().dp(90), -2)));
                     TextView var17 = new TextView((Context)this.getAct());
                     var17.setTextSize(16.0F);
                     var17.setTextColor(CarUi.Companion.getTEXT());
                     var17.setTypeface(Typeface.DEFAULT_BOLD);
                     var8.addView((View)var17, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(this.getUi().dp(90), -2)));
                     ProgressBar var18 = new ProgressBar((Context)this.getAct(), null, 16842872);
                     var18.setMax(100);
                     var18.setProgressTintList(ColorStateList.valueOf(CarUi.Companion.getACCENT()));
                     var8.addView((View)var18, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, this.getUi().dp(14), 1.0F)));
                     LinearLayout var9 = this.monCoresBox;
                     LinearLayout var19 = var9;
                     if (var9 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("monCoresBox");
                        var19 = null;
                     }

                     var19.addView((View)var8);
                  }

                  Double var20 = this.optNum(var10, "percent");
                  View var29 = var8.getChildAt(0);
                  Intrinsics.checkNotNull(var29, "null cannot be cast to non-null type android.widget.TextView");
                  TextView var30 = (TextView)var29;
                  String var32 = GxPage.str$default(this, var10, "name", null, 2, null).toUpperCase(Locale.ROOT);
                  Intrinsics.checkNotNullExpressionValue(var32, "toUpperCase(...)");
                  var30.setText(var32);
                  var29 = var8.getChildAt(1);
                  Intrinsics.checkNotNull(var29, "null cannot be cast to non-null type android.widget.TextView");
                  ((TextView)var29).setText(this.monNumber(var20, "%"));
                  View var27 = var8.getChildAt(2);
                  Intrinsics.checkNotNull(var27, "null cannot be cast to non-null type android.widget.ProgressBar");
                  ProgressBar var28 = (ProgressBar)var27;
                  double var1;
                  if (var20 != null) {
                     var1 = var20;
                  } else {
                     var1 = 0.0;
                  }

                  var28.setProgress(RangesKt.coerceIn((int)var1, 0, 100));
                  var3 = var4;
               }

               return;
            }

            var7 = this.monCoresBox;
            var21 = var7;
            if (var7 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("monCoresBox");
               var21 = null;
            }

            var7 = this.monCoresBox;
            if (var7 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("monCoresBox");
               var7 = null;
            }

            var21.removeViewAt(var7.getChildCount() - 1);
         }
      }
   }

   private final void monPaintSortBar() {
      Iterator var7 = this.monSortCols.iterator();
      int var1 = 0;

      while (var7.hasNext()) {
         int var2 = var1 + 1;
         Pair var8 = (Pair)var7.next();
         LinearLayout var6 = this.monSortBar;
         TextView var5 = null;
         LinearLayout var4 = var6;
         if (var6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("monSortBar");
            var4 = null;
         }

         View var10 = var4.getChildAt(var1);
         if (var10 instanceof TextView) {
            var5 = (TextView)var10;
         }

         if (var5 == null) {
            var1 = var2;
         } else {
            boolean var3 = Intrinsics.areEqual(this.monSort, var8.getFirst());
            var6 = (LinearLayout)var8.getSecond();
            Object var11;
            if (var3) {
               if (this.monDesc) {
                  var11 = " ↓";
               } else {
                  var11 = " ↑";
               }
            } else {
               var11 = "";
            }

            var5.setText(var6 + var11);
            var11 = CarUi.Companion;
            if (var3) {
               var1 = var11.getACCENT();
            } else {
               var1 = var11.getTEXT();
            }

            var5.setTextColor(var1);
            var1 = var2;
         }
      }
   }

   private final void monPaintStatus() {
      JSONObject var11 = this.monSnap;
      String var8;
      if (var11 == null) {
         var8 = "—";
      } else {
         DateFormat var7 = DateFormat.getTimeInstance(2);
         Double var24 = this.optNum(var11, "sampledAt");
         double var1;
         if (var24 != null) {
            var1 = var24;
         } else {
            var1 = 0.0;
         }

         var8 = var7.format(new Date((long)(var1 * 1000)));
      }

      GxPage.Row var17 = this.monStatus;
      Object var10 = null;
      GxPage.Row var9 = var17;
      if (var17 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("monStatus");
         var9 = null;
      }

      boolean var6 = this.monPaused;
      boolean var5 = true;
      byte var4 = 0;
      String var18;
      if (var6) {
         var18 = "Paused";
      } else {
         boolean var3;
         if (this.monError.length() > 0) {
            var3 = true;
         } else {
            var3 = false;
         }

         if (var3) {
            var18 = "Connection interrupted";
         } else {
            var18 = "Live updates";
         }
      }

      var9.setDesc(var18 + " · Captured " + var8);
      TextView var25 = this.monErrNote;
      TextView var19 = var25;
      if (var25 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("monErrNote");
         var19 = null;
      }

      var19.setText(this.monError);
      TextView var26 = this.monErrNote;
      TextView var20 = var26;
      if (var26 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("monErrNote");
         var20 = null;
      }

      boolean var12;
      if (this.monError.length() > 0) {
         var12 = 1;
      } else {
         var12 = 0;
      }

      if (var12) {
         var12 = 0;
      } else {
         var12 = 8;
      }

      var20.setVisibility(var12);
      TextView var27 = this.monWait;
      TextView var21 = var27;
      if (var27 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("monWait");
         var21 = null;
      }

      if (var11 == null) {
         var12 = 0;
      } else {
         var12 = 8;
      }

      var21.setVisibility(var12);
      TextView var28 = this.monWait;
      TextView var22 = var28;
      if (var28 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("monWait");
         var22 = null;
      }

      boolean var15;
      if (this.monError.length() > 0) {
         var15 = var5;
      } else {
         var15 = 0;
      }

      if (var15) {
         var8 = "Waiting for the device…";
      } else {
         var8 = "Reading system activity…";
      }

      var22.setText(var8);
      LinearLayout var23 = this.monBody;
      if (var23 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("monBody");
         var23 = (LinearLayout)var10;
      }

      var15 = var4;
      if (var11 == null) {
         var15 = 8;
      }

      var23.setVisibility(var15);
   }

   private final void monPaintTable() {
      if (this.monTable != null) {
         String var8 = StringsKt.trim(this.monQuery).toString().toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var8, "toLowerCase(...)");
         Iterable var6 = this.monProcs;
         boolean var5 = false;
         var var7 = new ArrayList();

         for (Object var10 : var6) {
            int var16;
            label167: {
               label180: {
                  GxLogsProc var14 = (GxLogsProc)var10;
                  if (!Intrinsics.areEqual(this.monScope, "all")) {
                     boolean var4;
                     if (Intrinsics.areEqual(this.monScope, "comma")) {
                        var4 = Intrinsics.areEqual(var14.getUser(), "comma");
                     } else if (!var14.getKernel()) {
                        var4 = true;
                     } else {
                        var4 = false;
                     }

                     if (!var4) {
                        break label180;
                     }
                  }

                  if (var8.length() == 0) {
                     var16 = (boolean)1;
                  } else {
                     var16 = (boolean)0;
                  }

                  if (!var16) {
                     String var13 = var14.getName();
                     String var12 = var14.getFeature();
                     var16 = var14.getPid();
                     String var11 = var14.getUser();
                     String var72 = this.monState(var14.getState());
                     var11 = (var13 + " " + var12 + " " + var16 + " " + var11 + " " + var72).toLowerCase(Locale.ROOT);
                     Intrinsics.checkNotNullExpressionValue(var11, "toLowerCase(...)");
                     if (!StringsKt.contains$default(var11, var8, false, 2, null)) {
                        break label180;
                     }
                  }

                  var16 = 1;
                  break label167;
               }

               var16 = 0;
            }

            if (var16) {
               var7.add(var10);
            }
         }

         List var23 = var7;
         List var46 = CollectionsKt.sortedWith(var23, new GxLogsPage$$ExternalSyntheticLambda38(new GxLogsPage$$ExternalSyntheticLambda37(this)));
         TextView var32 = this.monCountNote;
         TextView var24 = var32;
         if (var32 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("monCountNote");
            var24 = null;
         }

         int var17 = var46.size();
         String var33;
         if (this.optNum(this.monSnap, "cpuPercent") == null) {
            var33 = "Collecting the first CPU sample…";
         } else {
            var33 = "";
         }

         var24.setText(var17 + " processes shown. " + var33);
         String var53 = "empty";
         if (var46.isEmpty()) {
            LinearLayout var42 = this.monTable;
            LinearLayout var30 = var42;
            if (var42 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("monTable");
               var30 = null;
            }

            var30.removeAllViews();
            LinearLayout var31 = this.monTable;
            if (var31 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("monTable");
               var31 = null;
            }

            TextView var43 = this.empty("No matching processes.");
            var43.setTag("empty");
            var31.addView((View)var43);
            return;
         } else {
            LinearLayout var34 = this.monTable;
            LinearLayout var25 = var34;
            if (var34 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("monTable");
               var25 = null;
            }

            if (var25.getChildCount() > 0) {
               LinearLayout var35 = this.monTable;
               LinearLayout var26 = var35;
               if (var35 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("monTable");
                  var26 = null;
               }

               if (Intrinsics.areEqual(var26.getChildAt(0).getTag(), "empty")) {
                  LinearLayout var36 = this.monTable;
                  LinearLayout var27 = var36;
                  if (var36 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("monTable");
                     var27 = null;
                  }

                  var27.removeAllViews();
               }
            }

            while (true) {
               LinearLayout var37 = this.monTable;
               LinearLayout var28 = var37;
               if (var37 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("monTable");
                  var28 = null;
               }

               if (var28.getChildCount() <= var46.size()) {
                  Iterator var70 = var46.iterator();
                  var17 = 0;
                  String var40 = var53;

                  while (var70.hasNext()) {
                     int var2 = var17 + 1;
                     GxLogsProc var71 = (GxLogsProc)var70.next();
                     LinearLayout var54 = this.monTable;
                     LinearLayout var47 = var54;
                     if (var54 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("monTable");
                        var47 = null;
                     }

                     View var48 = var47.getChildAt(var17);
                     LinearLayout var49;
                     if (var48 instanceof LinearLayout) {
                        var49 = (LinearLayout & View)var48;
                     } else {
                        var49 = null;
                     }

                     String var41;
                     if (var49 != null) {
                        var41 = var8;
                        var8 = var40;
                     } else {
                        var49 = new LinearLayout((Context)this.getAct());
                        var49.setOrientation(0);
                        var49.setGravity(16);
                        var49.setPadding(0, this.getUi().dp(6), 0, this.getUi().dp(6));
                        var17 = 0;

                        for (int var3 = this.monWeights.length; var17 < var3; var17++) {
                           TextView var55 = new TextView((Context)this.getAct());
                           var55.setTextSize(15.0F);
                           var55.setTextColor(CarUi.Companion.getTEXT());
                           if (var17 == 0) {
                              var55.setMaxLines(2);
                              var55.setEllipsize(TruncateAt.END);
                           } else {
                              var55.setSingleLine(true);
                           }

                           var55.setPadding(0, 0, this.getUi().dp(6), 0);
                           var49.addView((View)var55, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, this.monWeights[var17])));
                        }

                        LinearLayout var66 = this.monTable;
                        LinearLayout var44 = var66;
                        if (var66 == null) {
                           Intrinsics.throwUninitializedPropertyAccessException("monTable");
                           var44 = null;
                        }

                        var44.addView((View)var49);
                        var8 = var40;
                        var41 = var8;
                     }

                     SpannableStringBuilder var58 = new SpannableStringBuilder(var71.getName());
                     boolean var20;
                     if (var71.getFeature().length() > 0) {
                        var20 = 1;
                     } else {
                        var20 = 0;
                     }

                     if (var20) {
                        var20 = var58.length();
                        String var67 = var71.getFeature();
                        var58.append(" (" + var67 + ")");
                        var58.setSpan(new ForegroundColorSpan(CarUi.Companion.getACCENT()), var20, var58.length(), 33);
                     }

                     View var68 = var49.getChildAt(0);
                     Intrinsics.checkNotNull(var68, "null cannot be cast to non-null type android.widget.TextView");
                     ((TextView)var68).setText((CharSequence)var58);
                     View var59 = var49.getChildAt(1);
                     Intrinsics.checkNotNull(var59, "null cannot be cast to non-null type android.widget.TextView");
                     ((TextView)var59).setText(String.valueOf(var71.getPid()));
                     View var60 = var49.getChildAt(2);
                     Intrinsics.checkNotNull(var60, "null cannot be cast to non-null type android.widget.TextView");
                     ((TextView)var60).setText(this.monNumber(var71.getCpu(), "%"));
                     View var61 = var49.getChildAt(3);
                     Intrinsics.checkNotNull(var61, "null cannot be cast to non-null type android.widget.TextView");
                     TextView var62 = (TextView)var61;
                     String var69 = monNumber$default(this, var71.getMem(), null, 2, null);
                     var62.setText(var69 + " MiB");
                     View var63 = var49.getChildAt(4);
                     Intrinsics.checkNotNull(var63, "null cannot be cast to non-null type android.widget.TextView");
                     ((TextView)var63).setText(var71.getUser());
                     View var50 = var49.getChildAt(5);
                     Intrinsics.checkNotNull(var50, "null cannot be cast to non-null type android.widget.TextView");
                     TextView var51 = (TextView)var50;
                     var51.setText(this.monState(var71.getState()));
                     if (Intrinsics.areEqual(var71.getState(), "R")) {
                        var20 = CarUi.Companion.getACCENT();
                     } else if (!Intrinsics.areEqual(var71.getState(), "Z") && !Intrinsics.areEqual(var71.getState(), "D")) {
                        var20 = CarUi.Companion.getDIM();
                     } else {
                        var20 = CarUi.Companion.getWARN();
                     }

                     var51.setTextColor(var20);
                     var8 = var41;
                     var40 = var8;
                     var17 = var2;
                  }

                  return;
               }

               LinearLayout var38 = this.monTable;
               LinearLayout var29 = var38;
               if (var38 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("monTable");
                  var29 = null;
               }

               LinearLayout var65 = this.monTable;
               LinearLayout var39 = var65;
               if (var65 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("monTable");
                  var39 = null;
               }

               var29.removeViewAt(var39.getChildCount() - 1);
            }
         }
      }
   }

   private static final int monPaintTable$lambda$91(GxLogsPage var0, GxLogsProc var1, GxLogsProc var2) {
      Intrinsics.checkNotNull(var1);
      Intrinsics.checkNotNull(var2);
      return var0.monCompare(var1, var2);
   }

   private static final int monPaintTable$lambda$92(Function2 var0, Object var1, Object var2) {
      return ((Number)var0.invoke(var1, var2)).intValue();
   }

   private final void monPaintVitals() {
      JSONObject var12 = this.monSnap;
      if (var12 != null) {
         if (this.monStatus != null) {
            JSONArray var10 = var12.optJSONArray("cores");
            int var3;
            if (var10 != null) {
               var3 = var10.length();
            } else {
               var3 = 0;
            }

            GxLogsPage.MonTile var23 = monPaintVitals$tile(this, "cpu");
            var23.getValue().setText(this.monNumber(this.optNum(var12, "cpuPercent"), "%"));
            var23.getSmall().setText(var3 + " cores · overall usage");
            GxLogsSpark var24 = var23.getSpark();
            if (var24 != null) {
               var24.setValues(this.monHistory);
            }

            JSONObject var16 = var12.optJSONObject("memory");
            GxLogsPage.MonTile var13 = monPaintVitals$tile(this, "mem");
            Double var11 = this.optNum(var16, "percent");
            var13.getValue().setText(this.monNumber(var11, "%"));
            TextView var14 = var13.getSmall();
            Double var25 = this.optNum(var16, "usedMiB");
            Double var26;
            if (var25 != null) {
               var26 = var25 / 1024;
            } else {
               var26 = null;
            }

            String var15 = monNumber$default(this, var26, null, 2, null);
            Double var27 = this.optNum(var16, "totalMiB");
            Double var28;
            if (var27 != null) {
               var28 = var27 / 1024;
            } else {
               var28 = null;
            }

            String var29 = monNumber$default(this, var28, null, 2, null);
            var14.setText(var15 + " / " + var29 + " GiB");
            ProgressBar var30 = var13.getBar();
            if (var30 != null) {
               double var1;
               if (var11 != null) {
                  var1 = var11;
               } else {
                  var1 = 0.0;
               }

               this.setProgress(var30, var1);
            }

            Double var31 = this.optNum(var12, "uptimeSeconds");
            double var17;
            if (var31 != null) {
               var17 = var31;
            } else {
               var17 = 0.0;
            }

            GxLogsPage.MonTile var46 = monPaintVitals$tile(this, "procs");
            TextView var32 = var46.getValue();
            Double var53 = this.optNum(var12, "processCount");
            long var4;
            if (var53 != null) {
               var4 = (long)var53.doubleValue();
            } else {
               var4 = 0L;
            }

            label106: {
               var32.setText(String.valueOf(var4));
               TextView var33 = var46.getSmall();
               long var8 = (long)(var17 / 3600);
               var4 = (long)(var17 / 60);
               long var6 = 60;
               var33.setText("Uptime " + var8 + "h " + var4 % var6 + "m");
               var54 = var12.optJSONObject("storage");
               var56 = monPaintVitals$tile(this, "storage");
               var60 = var56.getValue();
               Double var34 = this.optNum(var54, "usedGiB");
               if (var34 != null) {
                  var17 = var34.doubleValue();
                  String var47 = this.jsNum(var17);
                  var35 = var47;
                  if (var47 != null) {
                     break label106;
                  }
               }

               var35 = "—";
            }

            label101: {
               var60.setText(var35 + " GiB");
               var14 = var56.getSmall();
               Double var36 = this.optNum(var54, "totalGiB");
               if (var36 != null) {
                  var17 = var36.doubleValue();
                  String var48 = this.jsNum(var17);
                  var37 = var48;
                  if (var48 != null) {
                     break label101;
                  }
               }

               var37 = "—";
            }

            var14.setText(var37 + " GiB total");
            monPaintVitals$tile(this, "cpuTemp").getValue().setText(this.monNumber(this.monSensor("cpuTempC", "onboardMaxAgeMs"), " °C"));
            monPaintVitals$tile(this, "gpuTemp").getValue().setText(this.monNumber(this.monSensor("gpuTempC", "onboardMaxAgeMs"), " °C"));
            monPaintVitals$tile(this, "hotspot").getValue().setText(this.monNumber(this.monSensor("hotspotTempC", "maxAgeMs"), " °C"));
            JSONObject var38 = var12.optJSONObject("vitals");
            GxLogsPage.MonTile var49 = monPaintVitals$tile(this, "edge");
            LinearLayout var51 = var49.getRoot();
            byte var20;
            if (var38 != null && var38.has("gpuEdgeTempC") && !var38.isNull("gpuEdgeTempC")) {
               var20 = 0;
            } else {
               var20 = 4;
            }

            var51.setVisibility(var20);
            var49.getValue().setText(this.monNumber(this.monSensor("gpuEdgeTempC", "maxAgeMs"), " °C"));
            Double var52 = this.monSensor("memoryUsedBytes", "memoryMaxAgeMs");
            var11 = this.monSensor("memoryTotalBytes", "memoryMaxAgeMs");
            var13 = monPaintVitals$tile(this, "vram");
            var14 = var13.getValue();
            CharSequence var39;
            if (var52 == null) {
               var39 = "—";
            } else {
               String var40 = monNumber$default(this, var52 / 1.0737418E9F, null, 2, null);
               var39 = var40 + " GiB";
            }

            var14.setText(var39);
            var14 = var13.getSmall();
            CharSequence var41;
            if (var11 == null) {
               var41 = "";
            } else {
               var15 = monNumber$default(this, var11 / 1.0737418E9F, null, 2, null);
               Double var42;
               if (var52 == null) {
                  var42 = null;
               } else {
                  var42 = 100 * var52 / var11;
               }

               String var43 = this.monNumber(var42, "%");
               var41 = var15 + " GiB total · " + var43;
            }

            var14.setText(var41);
            ProgressBar var44 = var13.getBar();
            if (var44 != null) {
               if (var11 != null && var11 > 0.0 && var52 != null) {
                  var20 = 0;
               } else {
                  var20 = 8;
               }

               var44.setVisibility(var20);
            }

            if (var11 != null && var11 > 0.0 && var52 != null) {
               ProgressBar var45 = var13.getBar();
               if (var45 != null) {
                  this.setProgress(var45, 100 * var52 / var11);
               }
            }
         }
      }
   }

   private static final GxLogsPage.MonTile monPaintVitals$tile(GxLogsPage var0, String var1) {
      Object var2 = var0.monTiles.get(var1);
      Intrinsics.checkNotNull(var2);
      return (GxLogsPage.MonTile)var2;
   }

   private final void monRefresh() {
      if (!this.monLoading && !this.monPaused && this.monStatus != null) {
         this.monLoading = true;
         long var1 = SystemClock.elapsedRealtime();
         GxPage.json$default(this, "GET", "/api/system/monitor", null, 8L, new GxLogsPage$$ExternalSyntheticLambda30(this, var1), 4, null);
      }
   }

   private static final Unit monRefresh$lambda$75(GxLogsPage var0, long var1, String var3, Object var4) {
      var0.monLoading = false;
      if (var4 instanceof JSONObject) {
         var4 = var4;
      } else {
         var4 = null;
      }

      if (var3 == null && var4 != null && (!var4.has("error") || var4.has("processes"))) {
         Double var10 = var0.optNum(var4, "cpuPercent");
         if (!Intrinsics.areEqual(var0.optNum(var4, "sampledAt"), var0.optNum(var0.monSnap, "sampledAt")) && var10 != null) {
            var0.monHistory = CollectionsKt.takeLast(CollectionsKt.plus(var0.monHistory, var10), 30);
         }

         label37: {
            var0.monSnap = var4;
            var0.monError = "";
            var0.monReceivedAt = var1;
            var0.monSensorNow = SystemClock.elapsedRealtime();
            JSONArray var6 = var4.optJSONArray("processes");
            if (var6 != null) {
               List var13 = var0.objects(var6);
               if (var13 != null) {
                  Iterable var7 = var13;
                  var var14 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var7, 10));

                  for (JSONObject var9 : var7) {
                     String var8 = GxPage.str$default(var0, var9, "name", null, 2, null);
                     boolean var5 = var9.optBoolean("kernel", false);
                     var14.add(
                        new GxLogsProc(
                           var9.optInt("pid"),
                           var8,
                           GxPage.str$default(var0, var9, "user", null, 2, null),
                           var5,
                           GxPage.str$default(var0, var9, "state", null, 2, null),
                           var0.optNum(var9, "cpu"),
                           var0.optNum(var9, "memoryMiB"),
                           var0.monFeature(var8, var5)
                        )
                     );
                  }

                  var11 = var14;
                  break label37;
               }
            }

            var11 = CollectionsKt.emptyList();
         }

         var0.monProcs = var11;
      } else {
         var0.monError = "Cannot refresh system monitor. Showing the last captured values.";
      }

      var0.monPaintAll();
      return Unit.INSTANCE;
   }

   private final Double monSensor(String var1, String var2) {
      JSONObject var6 = this.monSnap;
      if (var6 != null) {
         var6 = var6.optJSONObject("vitals");
         if (var6 != null) {
            Double var7 = this.optNum(var6, var2);
            if (var7 != null) {
               double var3 = var7;
               boolean var5;
               if (this.monError.length() > 0) {
                  var5 = true;
               } else {
                  var5 = false;
               }

               if (!var5 && !(var3 <= 0.0) && !(this.monSensorNow - this.monReceivedAt >= var3)) {
                  return this.optNum(var6, var1);
               }

               return null;
            }

            return null;
         }
      }

      return null;
   }

   private final void monSortBy(String var1) {
      boolean var2 = Intrinsics.areEqual(this.monSort, var1);
      boolean var3 = true;
      if (var2) {
         this.monDesc ^= true;
      } else {
         this.monSort = var1;
         var2 = var3;
         if (!Intrinsics.areEqual(var1, "cpu")) {
            if (Intrinsics.areEqual(var1, "memoryMiB")) {
               var2 = var3;
            } else {
               var2 = false;
            }
         }

         this.monDesc = var2;
      }

      this.monPaintSortBar();
      this.monPaintTable();
   }

   private final String monState(String var1) {
      String var3 = (String)MapsKt.mapOf(
            new Pair[]{
               TuplesKt.to("R", "Running"),
               TuplesKt.to("S", "Sleeping"),
               TuplesKt.to("D", "Waiting"),
               TuplesKt.to("T", "Stopped"),
               TuplesKt.to("t", "Tracing"),
               TuplesKt.to("Z", "Zombie"),
               TuplesKt.to("I", "Idle")
            }
         )
         .get(var1);
      String var2 = var3;
      if (var3 == null) {
         var2 = var1;
      }

      return var2;
   }

   private final void monTogglePause() {
      this.monPaused ^= true;
      TextView var2 = this.monPauseBtn;
      TextView var1 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("monPauseBtn");
         var1 = null;
      }

      String var3;
      if (this.monPaused) {
         var3 = "Resume";
      } else {
         var3 = "Pause";
      }

      var1.setText(var3);
      if (!this.monPaused) {
         this.monSensorNow = SystemClock.elapsedRealtime();
         this.monRefresh();
      }

      this.monPaintStatus();
      this.monPaintVitals();
   }

   private final Double optNum(JSONObject var1, String var2) {
      Object var5 = null;
      if (var1 != null && var1.has(var2) && !var1.isNull(var2)) {
         double var3 = var1.optDouble(var2);
         Double var6;
         if (Double.isNaN(var3)) {
            var6 = (Double)var5;
         } else {
            var6 = var3;
         }

         return var6;
      } else {
         return null;
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private final String parseLogDate(String var1) {
      MatchResult var66 = Regex.find$default(new Regex("(\\d{4})-(\\d{2})-(\\d{2})[T_]?(\\d{2})-?(\\d{2})-?(\\d{2})?"), var1, 0, 2, null);
      Date var67;
      if (var66 == null) {
         var67 = new Date();
      } else {
         label279: {
            int var5;
            int var6;
            int var7;
            Integer var9;
            List var79;
            try {
               Result.Companion var8 = Result.Companion;
               GxLogsPage var78 = this;
               var79 = var66.getGroupValues();
               var70 = Calendar.getInstance();
               var70.setLenient(true);
               var6 = Integer.parseInt((String)var79.get(1));
               var5 = Integer.parseInt((String)var79.get(2));
               var7 = Integer.parseInt((String)var79.get(3));
               var9 = StringsKt.toIntOrNull((String)var79.get(4));
            } catch (Throwable var65) {
               Result.Companion var68 = Result.Companion;
               var69 = KtCompat.resultOf(ResultKt.createFailure(var65));
               break label279;
            }

            int var2;
            if (var9 != null) {
               try {
                  var2 = var9;
               } catch (Throwable var64) {
                  Result.Companion var71 = Result.Companion;
                  var69 = KtCompat.resultOf(ResultKt.createFailure(var64));
                  break label279;
               }
            } else {
               var2 = 0;
            }

            try {
               var9 = StringsKt.toIntOrNull((String)var79.get(5));
            } catch (Throwable var63) {
               Result.Companion var72 = Result.Companion;
               var69 = KtCompat.resultOf(ResultKt.createFailure(var63));
               break label279;
            }

            int var3;
            if (var9 != null) {
               try {
                  var3 = var9;
               } catch (Throwable var62) {
                  Result.Companion var73 = Result.Companion;
                  var69 = KtCompat.resultOf(ResultKt.createFailure(var62));
                  break label279;
               }
            } else {
               var3 = 0;
            }

            try {
               var80 = StringsKt.toIntOrNull((String)var79.get(6));
            } catch (Throwable var61) {
               Result.Companion var74 = Result.Companion;
               var69 = KtCompat.resultOf(ResultKt.createFailure(var61));
               break label279;
            }

            int var4;
            if (var80 != null) {
               try {
                  var4 = var80;
               } catch (Throwable var60) {
                  Result.Companion var75 = Result.Companion;
                  var69 = KtCompat.resultOf(ResultKt.createFailure(var60));
                  break label279;
               }
            } else {
               var4 = 0;
            }

            label256:
            try {
               var70.set(var6, var5 - 1, var7, var2, var3, var4);
               var70.set(14, 0);
               var69 = KtCompat.resultOf(var70.getTime());
            } catch (Throwable var59) {
               Result.Companion var76 = Result.Companion;
               var69 = KtCompat.resultOf(ResultKt.createFailure(var59));
               break label256;
            }
         }

         if (KtCompat.exceptionOrNull(var69) != null) {
            var69 = new Date();
         }

         var67 = (Date)var69;
      }

      var1 = DateFormat.getDateTimeInstance(2, 2).format(var67);
      Intrinsics.checkNotNullExpressionValue(var1, "format(...)");
      return var1;
   }

   private final EditText searchField(String var1, ViewGroup var2, Function1<? super String, Unit> var3) {
      EditText var4 = new EditText((Context)this.getAct());
      var4.setHint(var1);
      var4.setTextSize(19.0F);
      var4.setTextColor(CarUi.Companion.getTEXT());
      var4.setHintTextColor(CarUi.Companion.getDIM());
      var4.setSingleLine(true);
      var4.setInputType(1);
      var4.setBackground((Drawable)this.getUi().rounded(CarUi.Companion.getCARD(), 12, Color.rgb(50, 55, 66)));
      var4.setPadding(this.getUi().dp(18), this.getUi().dp(12), this.getUi().dp(18), this.getUi().dp(12));
      var4.setMinHeight(this.getUi().dp(58));
      var4.addTextChangedListener(new TextWatcher(var3) {
         final Function1<String, Unit> $onChange;

         {
            this.$onChange = var1;
         }

         public void afterTextChanged(Editable var1) {
            Function1 var3x;
            label11: {
               var3x = this.$onChange;
               if (var1 != null) {
                  String var2x = var1.toString();
                  var4 = var2x;
                  if (var2x != null) {
                     break label11;
                  }
               }

               var4 = "";
            }

            var3x.invoke(var4);
         }

         public void beforeTextChanged(CharSequence var1, int var2x, int var3x, int var4x) {
         }

         public void onTextChanged(CharSequence var1, int var2x, int var3x, int var4x) {
         }
      });
      View var6 = (View)var4;
      android.widget.LinearLayout.LayoutParams var7 = new android.widget.LinearLayout.LayoutParams(-1, -2);
      var7.bottomMargin = this.getUi().dp(8);
      Unit var5 = Unit.INSTANCE;
      var2.addView(var6, (LayoutParams)var7);
      return var4;
   }

   private final void tabShown(int var1) {
      switch (var1) {
         case 0:
            this.tsLoad(false);
            break;
         case 1:
            this.loadErrors();
            break;
         case 2:
            this.loadTmuxFiles();
            this.tmuxSnapshot();
      }
   }

   private final LinearLayout titleBar(String var1, ViewGroup var2) {
      LinearLayout var4 = new LinearLayout((Context)this.getAct());
      var4.setOrientation(0);
      var4.setGravity(16);
      var4.setPadding(0, 0, 0, this.getUi().dp(6));
      TextView var3 = new TextView((Context)this.getAct());
      var3.setText(var1);
      var3.setTextSize(21.0F);
      var3.setTextColor(CarUi.Companion.getTEXT());
      var3.setTypeface(Typeface.DEFAULT_BOLD);
      var4.addView((View)var3, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
      var2.addView((View)var4);
      return var4;
   }

   private final void tmuxCapture() {
      GxPage.send$default(this, "POST", "/api/tmux_log/capture", null, 0L, new GxLogsPage$$ExternalSyntheticLambda0(this), 12, null);
   }

   private static final Unit tmuxCapture$lambda$50(GxLogsPage var0, String var1, JSONObject var2) {
      if (var1 == null) {
         var1 = "Current session captured!";
      } else {
         var1 = "Capture failed.";
      }

      var0.toast(var1);
      var0.tmuxListLoading = true;
      var0.tmuxRenderList();
      var0.loadTmuxFiles();
      return Unit.INSTANCE;
   }

   private final void tmuxDelete(String var1) {
      this.confirm("Delete log?", "Delete " + var1 + "?", "Delete", new GxLogsPage$$ExternalSyntheticLambda13(this, var1));
   }

   private static final Unit tmuxDelete$lambda$53(GxLogsPage var0, String var1) {
      GxPage var3 = var0;
      String var2 = var0.enc(var1);
      GxPage.send$default(var3, "DELETE", "/api/tmux_log/delete/" + var2, null, 0L, new GxLogsPage$$ExternalSyntheticLambda29(var0, var1), 12, null);
      return Unit.INSTANCE;
   }

   private static final Unit tmuxDelete$lambda$53$lambda$52(GxLogsPage var0, String var1, String var2, JSONObject var3) {
      if (var2 == null) {
         var2 = "Deleted!";
      } else {
         var2 = "Delete failed.";
      }

      var0.toast(var2);
      Iterable var8 = var0.tmuxFiles;
      var var7 = new ArrayList();

      for (Object var4 : var8) {
         if (!Intrinsics.areEqual(((Pair)var4).getFirst(), var1)) {
            var7.add(var4);
         }
      }

      List var5 = var7;
      var0.tmuxFiles = var5;
      var0.tmuxRenderList();
      return Unit.INSTANCE;
   }

   private final void tmuxDeleteAll() {
      this.confirm("Delete all logs?", "This cannot be undone.", "Delete All", new GxLogsPage$$ExternalSyntheticLambda36(this));
   }

   private static final Unit tmuxDeleteAll$lambda$55(GxLogsPage var0) {
      GxPage.send$default(var0, "DELETE", "/api/tmux_log/delete_all", null, 0L, new GxLogsPage$$ExternalSyntheticLambda50(var0), 12, null);
      return Unit.INSTANCE;
   }

   private static final Unit tmuxDeleteAll$lambda$55$lambda$54(GxLogsPage var0, String var1, JSONObject var2) {
      if (var1 == null) {
         var1 = "All logs deleted!";
      } else {
         var1 = "Delete failed.";
      }

      var0.toast(var1);
      var0.tmuxFiles = CollectionsKt.emptyList();
      var0.tmuxRenderList();
      return Unit.INSTANCE;
   }

   private final void tmuxRename(String var1) {
      GxPage.prompt$default(this, "Rename log", "New file name", var1, 0, null, new GxLogsPage$$ExternalSyntheticLambda39(var1, this), 24, null);
   }

   private static final Unit tmuxRename$lambda$57(String var0, GxLogsPage var1, String var2) {
      Intrinsics.checkNotNullParameter(var2, "input");
      ObjectRef var4 = new ObjectRef();
      var4.element = (T)StringsKt.trim(var2).toString();
      boolean var3;
      if (((CharSequence)var4.element).length() == 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (!var3 && !Intrinsics.areEqual(var4.element, var0)) {
         if (!StringsKt.endsWith$default((String)var4.element, ".json", false, 2, null)) {
            Object var7 = var4.element;
            var4.element = (T)(var7 + ".json");
         }

         if (Intrinsics.areEqual(var4.element, var0)) {
            return Unit.INSTANCE;
         } else {
            GxPage var6 = var1;
            var2 = var1.enc(var0);
            String var5 = var1.enc((String)var4.element);
            GxPage.send$default(
               var6, "PUT", "/api/tmux_log/rename/" + var2 + "/" + var5, null, 0L, new GxLogsPage$$ExternalSyntheticLambda34(var1, var0, var4), 12, null
            );
            return Unit.INSTANCE;
         }
      } else {
         return Unit.INSTANCE;
      }
   }

   private static final Unit tmuxRename$lambda$57$lambda$56(GxLogsPage var0, String var1, ObjectRef var2, String var3, JSONObject var4) {
      StringBuilder var5;
      if (var3 != null) {
         var5 = new StringBuilder().append("Rename failed: ").append(var3);
      } else {
         Object var6 = var2.element;
         var5 = new StringBuilder().append(var1).append(" renamed to ").append(var6).append("!");
      }

      var0.toast(var5.toString());
      var0.loadTmuxFiles();
      return Unit.INSTANCE;
   }

   private final void tmuxRenderList() {
      if (this.tmuxList != null) {
         LinearLayout var6 = this.tmuxList;
         Object var4 = null;
         Object var5 = null;
         LinearLayout var3 = var6;
         if (var6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tmuxList");
            var3 = null;
         }

         var3.removeAllViews();
         if (this.tmuxListLoading) {
            var3 = this.tmuxList;
            if (var3 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("tmuxList");
               var3 = (LinearLayout)var5;
            }

            var3.addView((View)this.empty("Loading..."));
         } else if (this.tmuxFiles.isEmpty()) {
            var3 = this.tmuxList;
            if (var3 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("tmuxList");
               var3 = (LinearLayout)var4;
            }

            var3.addView((View)this.empty("No tmux logs found."));
         } else {
            for (Pair var11 : this.tmuxFiles) {
               var5 = (String)var11.component1();
               double var1 = ((Number)var11.component2()).doubleValue();
               GxPage var7 = this;
               String var8 = DateFormat.getDateTimeInstance(2, 2).format(new Date((long)(1000 * var1)));
               var4 = this.tmuxList;
               var3 = (LinearLayout)var4;
               if (var4 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("tmuxList");
                  var3 = null;
               }

               GxPage.Row var13 = GxPage.row$default(var7, (String)var5, var8, (ViewGroup)var3, 0, 8, null);
               var13.getView().setBackground((Drawable)CarUi.rounded$default(this.getUi(), CarUi.Companion.getCARD_ON(), 14, null, 4, null));
               GxPage.Row.button$default(var13, "Rename", false, new GxLogsPage$$ExternalSyntheticLambda25(this, (String)var5), 2, null);
               GxPage.Row.button$default(var13, "Download", false, new GxLogsPage$$ExternalSyntheticLambda26(this, (String)var5), 2, null);
               GxPage.Row.button$default(var13, "Delete", false, new GxLogsPage$$ExternalSyntheticLambda27(this, (String)var5), 2, null)
                  .setTextColor(GxLogsKt.access$getGXLOGS_RED$p());
            }
         }
      }
   }

   private static final Unit tmuxRenderList$lambda$47(GxLogsPage var0, String var1) {
      var0.tmuxRename(var1);
      return Unit.INSTANCE;
   }

   private static final Unit tmuxRenderList$lambda$48(GxLogsPage var0, String var1) {
      String var2 = var0.enc(var1);
      var0.download("/api/tmux_log/download/" + var2, var1, "application/json");
      return Unit.INSTANCE;
   }

   private static final Unit tmuxRenderList$lambda$49(GxLogsPage var0, String var1) {
      var0.tmuxDelete(var1);
      return Unit.INSTANCE;
   }

   private final void tmuxSnapshot() {
      if (!this.tmuxBusy) {
         this.tmuxBusy = true;
         this.getJson("/api/tmux_log/snapshot", new GxLogsPage$$ExternalSyntheticLambda32(this));
      }
   }

   private static final Unit tmuxSnapshot$lambda$43(GxLogsPage var0, String var1, JSONObject var2) {
      boolean var3 = false;
      var0.tmuxBusy = false;
      if (var1 == null && var2 != null) {
         var0.tmuxLatest = GxPage.str$default(var0, var2, "data", null, 2, null);
         if (!var0.tmuxPaused && var0.tmuxLog != null) {
            TextView var6 = var0.tmuxLog;
            TextView var5 = var6;
            if (var6 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("tmuxLog");
               var5 = null;
            }

            Object var4 = var0.tmuxLatest;
            if (var4.length() == 0) {
               var3 = true;
            }

            if (var3) {
               var4 = "(waiting for log output…)";
            }

            var5.setText((CharSequence)var4);
         }

         return Unit.INSTANCE;
      } else {
         return Unit.INSTANCE;
      }
   }

   private final void tmuxTogglePause() {
      boolean var2 = this.tmuxPaused;
      boolean var1 = true;
      this.tmuxPaused = var2 ^ true;
      TextView var4 = this.tmuxPauseBtn;
      Object var5 = null;
      TextView var3 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("tmuxPauseBtn");
         var3 = null;
      }

      String var8;
      if (this.tmuxPaused) {
         var8 = "Resume";
      } else {
         var8 = "Pause";
      }

      var3.setText(var8);
      var4 = this.tmuxTransport;
      var3 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("tmuxTransport");
         var3 = null;
      }

      String var10;
      if (this.tmuxPaused) {
         var10 = "paused";
      } else {
         var10 = "polling";
      }

      var3.setText(var10);
      if (!this.tmuxPaused) {
         var3 = this.tmuxLog;
         if (var3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tmuxLog");
            var3 = (TextView)var5;
         }

         var4 = this.tmuxLatest;
         if (var4.length() != 0) {
            var1 = false;
         }

         if (var1) {
            var4 = "(waiting for log output…)";
         }

         var3.setText((CharSequence)var4);
      }
   }

   private final boolean tsChanged(JSONObject var1) {
      return this.tsMatch(this.tsVal(var1, "value"), this.tsVal(var1, "defaultValue")) ^ true;
   }

   private final void tsCopyReport() {
      SimpleDateFormat var3 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
      var3.setTimeZone(TimeZone.getTimeZone("UTC"));
      String var4 = var3.format(new Date());
      ArrayList var7 = new ArrayList();
      var7.add("StarPilot Troubleshoot Report");
      var7.add("Generated: " + var4);
      boolean var2 = this.tsOnroad;
      String var5 = "Yes";
      String var13;
      if (var2) {
         var13 = "Yes";
      } else {
         var13 = "No";
      }

      var7.add("Onroad: " + var13);
      String var14;
      if (this.tsOnlyChanged) {
         var14 = var5;
      } else {
         var14 = "No";
      }

      var7.add("Only non-default values: " + var14);
      var5 = GxPage.str$default(this, this.tsVehicle, "summary", null, 2, null);
      boolean var1;
      if (var5.length() > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      if (var1) {
         label74: {
            var7.add("");
            var7.add("Vehicle Fault Status");
            var7.add("Summary: " + var5);
            JSONArray var15 = this.tsVehicle.optJSONArray("items");
            if (var15 != null) {
               List var6 = this.objects(var15);
               var16 = var6;
               if (var6 != null) {
                  break label74;
               }
            }

            var16 = CollectionsKt.emptyList();
         }

         for (JSONObject var8 : var16) {
            String var17 = GxPage.str$default(this, var8, "label", null, 2, null);
            String var26 = this.tsFormat(this.tsVal(var8, "value"));
            var7.add("- " + var17 + ": " + var26);
         }
      }

      var7.add("");
      var7.add("Snapshot");

      for (JSONObject var27 : this.tsSnapshot) {
         String var24 = GxPage.str$default(this, var27, "label", null, 2, null);
         String var28 = this.tsFormat(this.tsVal(var27, "value"));
         var7.add("- " + var24 + ": " + var28);
      }

      for (JSONObject var29 : this.tsSections) {
         List var20 = this.tsVisible(var29);
         if (!var20.isEmpty()) {
            var7.add("");
            var7.add(GxPage.str$default(this, var29, "title", null, 2, null));

            for (JSONObject var11 : var20) {
               var5 = this.tsFormatLearned(this.tsVal(var11, "learnedValue"));
               String var10 = GxPage.str$default(this, var11, "label", null, 2, null);
               String var9 = this.tsFormat(this.tsVal(var11, "value"));
               String var31 = this.tsFormat(this.tsVal(var11, "defaultValue"));
               if (var5.length() > 0) {
                  var1 = true;
               } else {
                  var1 = false;
               }

               if (var1) {
                  var5 = ", learned: " + var5;
               } else {
                  var5 = "";
               }

               var7.add("- " + var10 + ": " + var9 + " (default: " + var31 + var5 + ")");
            }
         }
      }

      this.copyText(CollectionsKt.joinToString$default(var7, "\n", null, null, 0, null, null, 62, null), "Troubleshoot report copied to clipboard.");
   }

   private final int tsCountChanged() {
      Iterator var4 = this.tsSections.iterator();
      int var2 = 0;

      while (var4.hasNext()) {
         JSONObject var5 = (JSONObject)var4.next();
         var var6 = this.tsItems(var5);
         int var1;
         if (var6 instanceof Collection && ((Collection)var6).isEmpty()) {
            var1 = 0;
         } else {
            var1 = 0;
            Iterator var7 = var6.iterator();

            while (var7.hasNext()) {
               if (this.tsChanged((JSONObject)var7.next())) {
                  int var3 = var1 + 1;
                  var1 = var3;
                  if (var3 < 0) {
                     CollectionsKt.throwCountOverflow();
                     var1 = var3;
                  }
               }
            }
         }

         var2 += var1;
      }

      return var2;
   }

   private final String tsFormat(Object var1) {
      if (var1 == null || Intrinsics.areEqual(var1, JSONObject.NULL)) {
         var1 = "n/a";
      } else if (var1 instanceof Boolean) {
         if ((Boolean)var1) {
            var1 = "On";
         } else {
            var1 = "Off";
         }
      } else if (!(var1 instanceof Integer) && !(var1 instanceof Long)) {
         boolean var6 = var1 instanceof Number;
         boolean var4 = true;
         boolean var5 = true;
         if (var6) {
            double var2 = ((Number)var1).doubleValue();
            if (!Double.isNaN(var2) && !Double.isInfinite(var2)) {
               if (var2 == Math.floor(var2)) {
                  var4 = var5;
               } else {
                  var4 = false;
               }

               if (var4) {
                  var1 = this.jsNum(var2);
               } else {
                  var1 = new BigDecimal(var2).setScale(4, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
                  Intrinsics.checkNotNullExpressionValue(var1, "toPlainString(...)");
               }
            } else {
               var1 = String.valueOf(var2);
            }
         } else if (!(var1 instanceof JSONObject) && !(var1 instanceof JSONArray)) {
            var1 = StringsKt.trim(var1.toString()).toString();
            if (var1.length() != 0) {
               var4 = false;
            }

            if (var4) {
               var1 = "(empty)";
            }

            var1 = var1;
         } else {
            var1 = var1.toString();
         }
      } else {
         var1 = var1.toString();
      }

      return var1;
   }

   private final String tsFormatLearned(Object var1) {
      String var4 = "";
      String var3 = var4;
      if (var1 != null) {
         if (Intrinsics.areEqual(var1, JSONObject.NULL)) {
            var3 = var4;
         } else {
            boolean var2;
            if (StringsKt.trim(var1.toString()).toString().length() == 0) {
               var2 = true;
            } else {
               var2 = false;
            }

            if (var2) {
               var3 = var4;
            } else {
               var3 = this.tsFormat(var1);
            }
         }
      }

      return var3;
   }

   private final List<JSONObject> tsItems(JSONObject var1) {
      JSONArray var3 = var1.optJSONArray("items");
      if (var3 != null) {
         List var2 = this.objects(var3);
         if (var2 != null) {
            return var2;
         }
      }

      return CollectionsKt.emptyList();
   }

   private final String tsJsStr(Object var1) {
      if (var1 == null || Intrinsics.areEqual(var1, JSONObject.NULL)) {
         var1 = "";
      } else if (var1 instanceof Number) {
         var1 = this.jsNum(((Number)var1).doubleValue());
      } else {
         var1 = var1.toString();
      }

      return var1;
   }

   private final void tsLoad(boolean var1) {
      if (!this.tsRefreshing) {
         this.tsRefreshing = true;
         if (this.tsSections.isEmpty()) {
            this.tsLoading = true;
         }

         this.tsError = "";
         this.tsPaintHead();
         if (this.tsLoading) {
            this.tsRender();
         }

         this.getJson("/api/troubleshoot", new GxLogsPage$$ExternalSyntheticLambda51(this, var1));
      }
   }

   private static final Unit tsLoad$lambda$105(GxLogsPage var0, boolean var1, String var2, JSONObject var3) {
      var0.tsRefreshing = false;
      var0.tsLoading = false;
      if (var2 == null && var3 != null) {
         label32: {
            JSONArray var5 = var3.optJSONArray("snapshot");
            if (var5 != null) {
               List var4 = var0.objects(var5);
               var6 = var4;
               if (var4 != null) {
                  break label32;
               }
            }

            var6 = CollectionsKt.emptyList();
         }

         label27: {
            var0.tsSnapshot = var6;
            JSONArray var7 = var3.optJSONArray("sections");
            if (var7 != null) {
               List var10 = var0.objects(var7);
               var8 = var10;
               if (var10 != null) {
                  break label27;
               }
            }

            var8 = CollectionsKt.emptyList();
         }

         var0.tsSections = var8;
         JSONObject var11 = var3.optJSONObject("vehicleStatus");
         JSONObject var9 = var11;
         if (var11 == null) {
            var9 = new JSONObject();
         }

         var0.tsVehicle = var9;
         var0.tsOnroad = var3.optBoolean("isOnroad", false);
         if (var1) {
            var0.toast("Troubleshoot data refreshed.");
         }
      } else {
         var0.tsError = "Failed to load troubleshoot data";
         if (var1) {
            var0.toast(var0.tsError);
         }
      }

      var0.tsRender();
      return Unit.INSTANCE;
   }

   private final boolean tsMatch(Object var1, Object var2) {
      boolean var6 = Intrinsics.areEqual(var1, JSONObject.NULL);
      Double var9 = null;
      if (var6) {
         var1 = null;
      }

      if (Intrinsics.areEqual(var2, JSONObject.NULL)) {
         var2 = var9;
      }

      boolean var8 = Intrinsics.areEqual(var1, var2);
      boolean var7 = true;
      var6 = true;
      if (var8) {
         return true;
      } else if (!(var1 instanceof JSONObject) && !(var1 instanceof JSONArray) || !(var2 instanceof JSONObject) && !(var2 instanceof JSONArray)) {
         if (var1 == null && var2 == null) {
            return true;
         } else if (var1 instanceof Number && var2 instanceof Number) {
            if (!(Math.abs(((Number)var1).doubleValue() - var2.doubleValue()) < 1.0E-9)) {
               var6 = false;
            }

            return var6;
         } else {
            var1 = StringsKt.trim(this.tsJsStr(var1)).toString();
            String var10 = StringsKt.trim(this.tsJsStr(var2)).toString();
            boolean var5;
            if (var1.length() == 0) {
               var5 = true;
            } else {
               var5 = false;
            }

            if (var5) {
               if (var10.length() == 0) {
                  var5 = true;
               } else {
                  var5 = false;
               }

               if (var5) {
                  return true;
               }
            }

            var2 = StringsKt.toDoubleOrNull(var1);
            var9 = StringsKt.toDoubleOrNull(var10);
            if (var1.length() > 0) {
               var5 = true;
            } else {
               var5 = false;
            }

            if (var5) {
               if (var10.length() > 0) {
                  var5 = true;
               } else {
                  var5 = false;
               }

               if (var5 && var2 != null && var9 != null) {
                  double var3 = var2;
                  if (!Double.isInfinite(var3) && !Double.isNaN(var3)) {
                     var5 = true;
                  } else {
                     var5 = false;
                  }

                  if (var5) {
                     var3 = var9;
                     if (!Double.isInfinite(var3) && !Double.isNaN(var3)) {
                        var5 = true;
                     } else {
                        var5 = false;
                     }

                     if (var5) {
                        if (Math.abs(var2 - var9) < 1.0E-9) {
                           var6 = var7;
                        } else {
                           var6 = false;
                        }

                        return var6;
                     }
                  }
               }
            }

            return Intrinsics.areEqual(var1, var10);
         }
      } else {
         return Intrinsics.areEqual(var1.toString(), var2.toString());
      }
   }

   private final void tsPaintHead() {
      if (this.tsRefreshBtn != null) {
         TextView var4 = this.tsRefreshBtn;
         Object var5 = null;
         TextView var3 = var4;
         if (var4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tsRefreshBtn");
            var3 = null;
         }

         String var13;
         if (this.tsRefreshing) {
            var13 = "Refreshing...";
         } else {
            var13 = "Refresh";
         }

         var3.setText(var13);
         var4 = this.tsErrNote;
         var3 = var4;
         if (var4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tsErrNote");
            var3 = null;
         }

         var3.setText(this.tsError);
         var4 = this.tsErrNote;
         var3 = var4;
         if (var4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tsErrNote");
            var3 = null;
         }

         int var1 = this.tsError.length();
         byte var2 = 0;
         boolean var6;
         if (var1 > 0) {
            var6 = 1;
         } else {
            var6 = 0;
         }

         if (var6) {
            var6 = var2;
         } else {
            var6 = (byte)8;
         }

         var3.setVisibility(var6);
         var4 = this.tsOnroadText;
         var3 = var4;
         if (var4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tsOnroadText");
            var3 = null;
         }

         String var17;
         if (this.tsOnroad) {
            var17 = "Yes";
         } else {
            var17 = "No";
         }

         var3.setText("Onroad: " + var17);
         var3 = this.tsChangedText;
         if (var3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tsChangedText");
            var3 = (TextView)var5;
         }

         var6 = this.tsCountChanged();
         var3.setText("Changed Settings: " + var6);
      }
   }

   private final void tsPair(String var1, String var2, ViewGroup var3, int var4) {
      LinearLayout var5 = new LinearLayout((Context)this.getAct());
      var5.setOrientation(0);
      var5.setGravity(16);
      var5.setBackground((Drawable)CarUi.rounded$default(this.getUi(), CarUi.Companion.getCARD_ON(), 10, null, 4, null));
      var5.setPadding(this.getUi().dp(14), this.getUi().dp(10), this.getUi().dp(14), this.getUi().dp(10));
      TextView var6 = new TextView((Context)this.getAct());
      var6.setText(var1);
      var6.setTextSize(16.0F);
      var6.setTextColor(CarUi.Companion.getDIM());
      var5.addView((View)var6, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
      TextView var7 = new TextView((Context)this.getAct());
      var7.setText(var2);
      var7.setTextSize(17.0F);
      var7.setTextColor(var4);
      var7.setTypeface(Typeface.DEFAULT_BOLD);
      var7.setGravity(8388613);
      var7.setPadding(this.getUi().dp(12), 0, 0, 0);
      var5.addView((View)var7, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.3F)));
      View var8 = (View)var5;
      android.widget.LinearLayout.LayoutParams var9 = new android.widget.LinearLayout.LayoutParams(-1, -2);
      var9.bottomMargin = this.getUi().dp(6);
      Unit var10 = Unit.INSTANCE;
      var3.addView(var8, (LayoutParams)var9);
   }

   // $VF: synthetic method
   static void tsPair$default(GxLogsPage var0, String var1, String var2, ViewGroup var3, int var4, int var5, Object var6) {
      if ((var5 & 8) != 0) {
         var4 = CarUi.Companion.getTEXT();
      }

      var0.tsPair(var1, var2, var3, var4);
   }

   private final void tsRender() {
      if (this.tsBody != null) {
         this.tsPaintHead();
         LinearLayout var6 = this.tsBody;
         LinearLayout var5 = var6;
         if (var6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tsBody");
            var5 = null;
         }

         var5.removeAllViews();
         if (this.tsLoading) {
            var5 = this.tsBody;
            if (var5 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("tsBody");
               var5 = null;
            }

            var5.addView((View)this.empty("Loading troubleshoot data..."));
         } else {
            var6 = this.tsBody;
            var5 = var6;
            if (var6 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("tsBody");
               var5 = null;
            }

            var6 = this.card((ViewGroup)var5);
            LinearLayout var11 = this.titleBar("Vehicle Fault Status", (ViewGroup)var6);
            boolean var3 = this.tsVehicle.optBoolean("available", false);
            String var26;
            if (var3) {
               var26 = "Live";
            } else {
               var26 = "Unavailable";
            }

            int var1;
            if (var3) {
               var1 = CarUi.Companion.getACCENT();
            } else {
               String var7 = GxPage.str$default(this, this.tsVehicle, "summarySeverity", null, 2, null).toLowerCase(Locale.ROOT);
               Intrinsics.checkNotNullExpressionValue(var7, "toLowerCase(...)");
               if (Intrinsics.areEqual(var7, "error")) {
                  var1 = GxLogsKt.access$getGXLOGS_RED$p();
               } else {
                  var1 = CarUi.Companion.getDIM();
               }
            }

            this.chip(var26, var1, (ViewGroup)var11);
            GxPage var41 = this;
            var var27 = GxPage.str$default(this, this.tsVehicle, "summary", null, 2, null);
            var1 = var27.length();
            boolean var2 = true;
            boolean var20;
            if (var1 == 0) {
               var20 = 1;
            } else {
               var20 = 0;
            }

            if (var20) {
               var27 = "Vehicle fault status unavailable.";
            }

            label136: {
               GxPage.text$default(var41, var27, (ViewGroup)var6, 17.0F, CarUi.Companion.getTEXT(), false, 16, null);
               JSONArray var28 = this.tsVehicle.optJSONArray("items");
               if (var28 != null) {
                  List var42 = this.objects(var28);
                  var29 = var42;
                  if (var42 != null) {
                     break label136;
                  }
               }

               var29 = CollectionsKt.emptyList();
            }

            for (JSONObject var30 : var29) {
               this.tsPair(
                  GxPage.str$default(this, var30, "label", null, 2, null),
                  this.tsFormat(this.tsVal(var30, "value")),
                  (ViewGroup)var6,
                  this.tsSevColor(this.str(var30, "severity", "neutral"))
               );
            }

            LinearLayout var44 = this.tsBody;
            var5 = var44;
            if (var44 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("tsBody");
               var5 = null;
            }

            LinearLayout var45 = this.card((ViewGroup)var5);
            this.titleBar("Snapshot", (ViewGroup)var45);
            if (this.tsSnapshot.isEmpty()) {
               GxPage.text$default(this, "No snapshot data.", (ViewGroup)var45, 0.0F, 0, false, 28, null);
            }

            for (JSONObject var8 : this.tsSnapshot) {
               tsPair$default(
                  this, GxPage.str$default(this, var8, "label", null, 2, null), this.tsFormat(this.tsVal(var8, "value")), (ViewGroup)var45, 0, 8, null
               );
            }

            for (JSONObject var10 : this.tsSections) {
               List var9 = this.tsVisible(var10);
               if (!var9.isEmpty()) {
                  LinearLayout var46 = this.tsBody;
                  var6 = var46;
                  if (var46 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("tsBody");
                     var6 = null;
                  }

                  LinearLayout var14 = this.card((ViewGroup)var6);
                  LinearLayout var48 = this.titleBar(GxPage.str$default(this, var10, "title", null, 2, null), (ViewGroup)var14);
                  String var47 = GxPage.str$default(this, var10, "id", null, 2, null);
                  if (var10.optBoolean("resettable", false)) {
                     CarUi var13 = this.getUi();
                     String var38;
                     if (Intrinsics.areEqual(this.tsBusySection, var47)) {
                        var38 = "Resetting...";
                     } else {
                        var38 = "Reset to Default";
                     }

                     TextView var39 = CarUi.button$default(var13, var38, false, new GxLogsPage$$ExternalSyntheticLambda40(this, var10), 2, null);
                     var39.setTextColor(GxLogsKt.access$getGXLOGS_RED$p());
                     var48.addView((View)var39);
                  }

                  var3 = var10.optBoolean("hasLearnedColumn", false);

                  for (JSONObject var16 : var9) {
                     boolean var4 = this.tsChanged(var16);
                     LinearLayout var15 = new LinearLayout((Context)this.getAct());
                     var15.setOrientation(0);
                     var15.setGravity(16);
                     CarUi var17 = this.getUi();
                     var20 = CarUi.Companion.getCARD_ON();
                     Integer var49;
                     if (var4) {
                        var49 = CarUi.Companion.getWARN();
                     } else {
                        var49 = null;
                     }

                     var15.setBackground((Drawable)var17.rounded(var20, 10, var49));
                     var15.setPadding(this.getUi().dp(14), this.getUi().dp(10), this.getUi().dp(14), this.getUi().dp(10));
                     LinearLayout var50 = new LinearLayout((Context)this.getAct());
                     var50.setOrientation(0);
                     var50.setGravity(16);
                     TextView var56 = new TextView((Context)this.getAct());
                     var56.setText(GxPage.str$default(this, var16, "label", null, 2, null));
                     var56.setTextSize(16.0F);
                     var56.setTextColor(CarUi.Companion.getTEXT());
                     var50.addView((View)var56, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
                     if (var4) {
                        this.chip("Changed", CarUi.Companion.getWARN(), (ViewGroup)var50);
                     }

                     var15.addView((View)var50, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
                     LinearLayout var51 = new LinearLayout((Context)this.getAct());
                     var51.setOrientation(1);
                     var51.setPadding(this.getUi().dp(16), 0, 0, 0);
                     TextView var18 = new TextView((Context)this.getAct());
                     SpannableStringBuilder var57 = new SpannableStringBuilder("current ");
                     var57.setSpan(new ForegroundColorSpan(CarUi.Companion.getDIM()), 0, var57.length(), 33);
                     var20 = var57.length();
                     var57.append(this.tsFormat(this.tsVal(var16, "value")));
                     var57.setSpan(new StyleSpan(1), var20, var57.length(), 33);
                     var18.setText((CharSequence)var57);
                     var18.setTextSize(16.0F);
                     var18.setTextColor(CarUi.Companion.getTEXT());
                     var51.addView((View)var18);
                     TextView var58 = new TextView((Context)this.getAct());
                     String var60 = this.tsFormat(this.tsVal(var16, "defaultValue"));
                     var58.setText("default " + var60);
                     var58.setTextSize(15.0F);
                     var58.setTextColor(CarUi.Companion.getDIM());
                     var51.addView((View)var58);
                     String var59 = this.tsFormatLearned(this.tsVal(var16, "learnedValue"));
                     if (var3) {
                        boolean var23;
                        if (var59.length() > 0) {
                           var23 = true;
                        } else {
                           var23 = false;
                        }

                        if (var23) {
                           TextView var54 = new TextView((Context)this.getAct());
                           var54.setText("learned " + var59);
                           var54.setTextSize(15.0F);
                           var54.setTextColor(CarUi.Companion.getDIM());
                           var51.addView((View)var54);
                        }
                     }

                     var15.addView((View)var51, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
                     View var55 = (View)var15;
                     android.widget.LinearLayout.LayoutParams var53 = new android.widget.LinearLayout.LayoutParams(-1, -2);
                     var53.bottomMargin = this.getUi().dp(6);
                     Unit var52 = Unit.INSTANCE;
                     var14.addView(var55, (LayoutParams)var53);
                  }
               }
            }

            if (this.tsOnlyChanged && this.tsCountChanged() == 0) {
               var5 = this.tsBody;
               if (var5 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("tsBody");
                  var5 = null;
               }

               var5.addView((View)this.empty("No settings are currently different from their defaults."));
            }
         }
      }
   }

   private static final Unit tsRender$lambda$115(GxLogsPage var0, JSONObject var1) {
      var0.tsResetSection(var1);
      return Unit.INSTANCE;
   }

   private final void tsResetSection(JSONObject var1) {
      String var4 = GxPage.str$default(this, var1, "id", null, 2, null);
      int var2 = var4.length();
      boolean var3 = true;
      boolean var7;
      if (var2 == 0) {
         var7 = true;
      } else {
         var7 = false;
      }

      if (!var7) {
         if (this.tsBusySection.length() > 0) {
            var7 = true;
         } else {
            var7 = false;
         }

         if (!var7) {
            var var5 = GxPage.str$default(this, var1, "title", null, 2, null);
            if (var5.length() == 0) {
               var7 = var3;
            } else {
               var7 = false;
            }

            if (var7) {
               var5 = "this section";
            }

            String var6 = var5;
            this.confirm("Reset to defaults?", "Reset " + var6 + " to defaults?", "Reset", new GxLogsPage$$ExternalSyntheticLambda24(this, var4, var6));
            return;
         }
      }
   }

   private static final Unit tsResetSection$lambda$128(GxLogsPage var0, String var1, String var2) {
      var0.tsBusySection = var1;
      var0.tsRender();
      GxPage.send$default(
         var0, "POST", "/api/troubleshoot/reset", new JSONObject().put("sectionId", var1), 0L, new GxLogsPage$$ExternalSyntheticLambda11(var0, var2), 8, null
      );
      return Unit.INSTANCE;
   }

   private static final Unit tsResetSection$lambda$128$lambda$127(GxLogsPage var0, String var1, String var2, JSONObject var3) {
      var0.tsBusySection = "";
      if (var2 != null) {
         CharSequence var9 = var2;
         Object var5 = var9;
         if (StringsKt.isBlank(var9)) {
            var5 = "Failed to reset section.";
         }

         var0.toast((String)var5);
         var0.tsRender();
         return Unit.INSTANCE;
      } else {
         label25: {
            if (var3 != null) {
               var2 = GxPage.str$default(var0, var3, "message", null, 2, null);
               if (var2 != null) {
                  CharSequence var10 = var2;
                  CharSequence var7 = var10;
                  if (StringsKt.isBlank(var10)) {
                     var7 = null;
                  }

                  var2 = (String)var7;
                  if (var2 != null) {
                     var1 = var2;
                     break label25;
                  }
               }
            }

            var1 = var1 + " reset.";
         }

         var0.toast(var1);
         var0.tsLoad(false);
         return Unit.INSTANCE;
      }
   }

   private static final void tsSearchRun$lambda$98(GxLogsPage var0) {
      var0.tsRender();
   }

   private final int tsSevColor(String var1) {
      var1 = var1.toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var1, "toLowerCase(...)");
      switch (var1.hashCode()) {
         case 3548:
            if (var1.equals("ok")) {
               return CarUi.Companion.getACCENT();
            }
            break;
         case 3322092:
            if (var1.equals("live")) {
               return CarUi.Companion.getACCENT();
            }
            break;
         case 3641990:
            if (var1.equals("warn")) {
               return CarUi.Companion.getWARN();
            }
            break;
         case 96784904:
            if (var1.equals("error")) {
               return GxLogsKt.access$getGXLOGS_RED$p();
            }
            break;
         case 1124446108:
            if (var1.equals("warning")) {
               return CarUi.Companion.getWARN();
            }
            break;
         case 1952151455:
            if (var1.equals("critical")) {
               return GxLogsKt.access$getGXLOGS_RED$p();
            }
      }

      return CarUi.Companion.getDIM();
   }

   private final Object tsVal(JSONObject var1, String var2) {
      Object var3 = var1.opt(var2);
      if (var3 == null || Intrinsics.areEqual(var3, JSONObject.NULL)) {
         var3 = null;
      }

      return var3;
   }

   private final List<JSONObject> tsVisible(JSONObject var1) {
      String var5 = StringsKt.trim(this.tsSearch).toString().toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var5, "toLowerCase(...)");
      Iterable var4 = this.tsItems(var1);
      var var7 = new ArrayList();

      for (Object var8 : var4) {
         boolean var2;
         JSONObject var12 = (JSONObject)var8;
         boolean var3 = this.tsOnlyChanged;
         var2 = false;
         label32:
         if (!var3 || this.tsChanged(var12)) {
            if (var5.length() == 0) {
               var2 = true;
            } else {
               var2 = false;
            }

            if (!var2) {
               String var10 = GxPage.str$default(this, var1, "title", null, 2, null);
               String var11 = GxPage.str$default(this, var12, "key", null, 2, null);
               String var9 = GxPage.str$default(this, var12, "label", null, 2, null);
               String var17 = this.tsFormat(this.tsVal(var12, "value"));
               var9 = (var10 + " " + var11 + " " + var9 + " " + var17).toLowerCase(Locale.ROOT);
               Intrinsics.checkNotNullExpressionValue(var9, "toLowerCase(...)");
               CharSequence var15 = var9;
               CharSequence var16 = var5;
               var2 = false;
               if (!StringsKt.contains$default(var15, var16, false, 2, null)) {
                  break label32;
               }
            }

            var2 = true;
         }

         if (var2) {
            var7.add(var8);
         }
      }

      return var7;
   }

   @Override
   public void build() {
      GxPage.header$default(this, "Logs & Diagnostics", null, 2, null);
      this.selectTab = this.tabs(
         CollectionsKt.listOf(new String[]{"Troubleshoot", "Error Logs", "Tmux Live Log", "System Monitor", "Get Help via Discord"}),
         this.tab,
         new GxLogsPage$$ExternalSyntheticLambda1(this),
         new GxLogsPage$$ExternalSyntheticLambda2(this)
      );
      this.every(2000L, new GxLogsPage$$ExternalSyntheticLambda3(this));
      this.every(1000L, new GxLogsPage$$ExternalSyntheticLambda4(this));
   }

   @Override
   public void onShow() {
      this.tabShown(this.tab);
   }

   @Override
   public void onTick() {
      if (this.tab == 3 && !this.monPaused && this.monSnap != null) {
         this.monSensorNow = SystemClock.elapsedRealtime();
         this.monPaintVitals();
      }
   }

   private static final class ErrFile {
      private final String date;
      private final String filename;

      public ErrFile(String var1, String var2) {
         Intrinsics.checkNotNullParameter(var1, "filename");
         Intrinsics.checkNotNullParameter(var2, "date");
         super();
         this.filename = var1;
         this.date = var2;
      }

      public final String getDate() {
         return this.date;
      }

      public final String getFilename() {
         return this.filename;
      }
   }

   private static final class MonTile {
      private final ProgressBar bar;
      private final LinearLayout root;
      private final TextView small;
      private final GxLogsSpark spark;
      private final TextView value;

      public MonTile(LinearLayout var1, TextView var2, TextView var3, ProgressBar var4, GxLogsSpark var5) {
         Intrinsics.checkNotNullParameter(var1, "root");
         Intrinsics.checkNotNullParameter(var2, "value");
         Intrinsics.checkNotNullParameter(var3, "small");
         super();
         this.root = var1;
         this.value = var2;
         this.small = var3;
         this.bar = var4;
         this.spark = var5;
      }

      public final ProgressBar getBar() {
         return this.bar;
      }

      public final LinearLayout getRoot() {
         return this.root;
      }

      public final TextView getSmall() {
         return this.small;
      }

      public final GxLogsSpark getSpark() {
         return this.spark;
      }

      public final TextView getValue() {
         return this.value;
      }
   }
}
