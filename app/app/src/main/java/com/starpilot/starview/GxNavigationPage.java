package com.starpilot.starview;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import java.text.DateFormat;
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
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SpreadBuilder;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.jvm.internal.Ref.ObjectRef;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.RegexOption;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

public final class GxNavigationPage extends GxPage {
   private String appUrl;
   private boolean canProcessNow;
   private LinearLayout catalogBox;
   private String cookieName;
   private int curTab;
   private LinearLayout destBox;
   private JSONObject destination;
   private final HashSet<String> expandedGroups;
   private LinearLayout favBox;
   private List<? extends JSONObject> favorites;
   private String galaxyToken;
   private final HashMap<String, Pair<GxPage.Row, JSONObject>> groupRows;
   private boolean isMetric;
   private LinearLayout keyBox;
   private final List<GxNavigationPage.KeyMeta> keyMeta;
   private LinearLayout keysBox;
   private String keysError;
   private boolean keysLoading;
   private String language;
   private Pair<Double, Double> lastPosition;
   private boolean loadingRoute;
   private String mapboxPublic;
   private String mapboxSecret;
   private boolean mapsActionBusy;
   private LinearLayout mapsBar;
   private String mapsError;
   private TextView mapsErrorNote;
   private boolean mapsLoadingCatalog;
   private boolean mapsLoadingStatus;
   private JSONObject mapsProgress;
   private boolean mapsSavingSchedule;
   private boolean mapsSavingSelection;
   private String mapsSearch;
   private List<? extends JSONObject> mapsSections;
   private JSONObject mapsStatus;
   private boolean mapsStatusInFlight;
   private TextView nOnroad;
   private TextView nSchDirty;
   private TextView nSelDirty;
   private String navError;
   private boolean navLoading;
   private boolean navigationStarted;
   private final boolean needsOffroad;
   private TextView pEstimate;
   private TextView pEta;
   private TextView pFiles;
   private TextView pRegion;
   private TextView pStored;
   private Function1<? super Integer, Unit> paintSchedule;
   private boolean paired;
   private ProgressBar progBar;
   private LinearLayout progCard;
   private TextView progTitle;
   private EditText queryEdit;
   private boolean quietEdit;
   private LinearLayout recentBox;
   private List<? extends JSONObject> recentDestinations;
   private final HashMap<String, Switch> regionSwitches;
   private Triple<Double, Double, String> routeSummary;
   private List<? extends JSONObject> routes;
   private LinearLayout sBar;
   private TextView sDebug;
   private GxPage.Row sDebugRow;
   private TextView sLastEvent;
   private TextView sReason;
   private TextView sStatus;
   private TextView sVision;
   private final HashMap<String, String> savedKeys;
   private TextView schedNote;
   private LinearLayout scheduleBox;
   private String scheduleDraft;
   private List<Pair<String, String>> scheduleOptions;
   private String scheduleSaved;
   private LinearLayout searchCard;
   private final Runnable searchRunner;
   private int searchSeq;
   private boolean searching;
   private TextView selRegionsText;
   private Function1<? super Integer, Unit> selectTab;
   private final LinkedHashSet<String> selectedDraft;
   private String selectedRouteId;
   private Set<String> selectedSaved;
   private final String sessionToken;
   private boolean speedLoading;
   private JSONObject speedObj;
   private boolean speedProcessing;
   private String speedReason;
   private String speedStatus;
   private boolean speedSubmitting;
   private TextView statusNote;
   private LinearLayout suggestBox;
   private List<? extends JSONObject> suggestions;
   private final HashMap<String, String> tokenLabels;
   private boolean tokenVisible;
   private TextView vDownloader;
   private TextView vEstimate;
   private TextView vLastUpdate;
   private TextView vSaved;
   private TextView vStorage;

   // $VF: synthetic method
   public static Unit $r8$lambda$0tJFnqF9_STCoJec3qP3VXoARlQ/* $VF was: $r8$lambda$0tJFnqF9_STCoJec3qP3VXoARlQ*/(GxNavigationPage var0, JSONObject var1) {
      return renderRecents$lambda$100(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$1Tt5_IrJw3tB78cvEDlJAQDDz0A(GxNavigationPage var0, GxNavigationPage.KeyMeta var1) {
      return keyRow$lambda$162(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$1WYm5_CvSWrc2bu_5ODgT7BdJQo/* $VF was: $r8$lambda$1WYm5_CvSWrc2bu_5ODgT7BdJQo*/(JSONObject var0, GxNavigationPage var1) {
      return renderFavorites$lambda$89$lambda$88(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$1fLF40fue0b9GIqIXX7qlnW_2tA(GxNavigationPage.KeyMeta var0, GxNavigationPage var1, String var2) {
      return editKey$lambda$166(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$23gWRfRynTawC4kUJlRsmhKujAY(GxNavigationPage var0) {
      return renderDest$lambda$75(var0);
   }

   // $VF: synthetic method
   public static CharSequence $r8$lambda$27XxcG1X3iYzhXiw8Rl1pLrJkX0(GxNavigationPage var0, Pair var1) {
      return qs$lambda$21(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$2IZeaKFbbwS6YEpYGRG5Zh_F8PE(GxNavigationPage var0) {
      return buildDest$lambda$27(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$2xNkgSg4nuFjP2jw_mqY5tehppI(GxNavigationPage var0, GxNavigationPage.KeyMeta var1) {
      return keyRow$lambda$162$lambda$161(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$61lxBbC7c7O2kMrnXnnfgfg70H4(GxNavigationPage var0, int var1) {
      return buildSchedule$lambda$114(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$6ExDU2_tP5mjhaMLwT8WfsiZpiM/* $VF was: $r8$lambda$6ExDU2_tP5mjhaMLwT8WfsiZpiM*/(
      GxNavigationPage var0, String var1, JSONObject var2
   ) {
      return chooseSuggestion$lambda$51(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$6o6BAdu9sHNsZuKilXkigf60nKY(GxNavigationPage var0, String var1, JSONObject var2) {
      return loadNav$lambda$32(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$7Lv6_qXfiaTf0C97aCNNsI76x1E(GxNavigationPage var0) {
      return renderDest$lambda$76(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$7u0DogkeLWKAtLXQU6LFU61Xyj4(GxNavigationPage var0) {
      return buildDest$lambda$28(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$9p3LgghAOO_WjKZkqAnMxKf3ByY(GxNavigationPage var0) {
      return removeMaps$lambda$146(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$9u6Nw_ht0lv_Msqr5dnSkBNcNgM/* $VF was: $r8$lambda$9u6Nw_ht0lv_Msqr5dnSkBNcNgM*/(List var0, GxNavigationPage var1) {
      return renderCatalog$lambda$137(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$A1fi6DsFKw7TSHVOXPHumqb6U2U(Function0 var0, View var1) {
      clickable$lambda$13(var0, var1);
   }

   // $VF: synthetic method
   public static CharSequence $r8$lambda$BGQKJGHNZwZkZ6XwmTx_Fam0H4U/* $VF was: $r8$lambda$BGQKJGHNZwZkZ6XwmTx_Fam0H4U*/(MatchResult var0) {
      return keyRow$lambda$156(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$BT_9Ir48UZJxAs2Drm2x71gKneQ(GxNavigationPage var0) {
      return buildSchedule$lambda$115(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$DJ5_tKgPWoUouBfZHYdYbQUsCfU(GxNavigationPage var0, String var1, JSONObject var2) {
      return renameFavorite$lambda$91$lambda$90(var0, var1, var2);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$DdJSZN0YqcWshzFtc6sBeVUbw34(GxNavigationPage var0, TextView var1, int var2, KeyEvent var3) {
      return buildDest$lambda$24$lambda$23(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$DfFgKjstQn5_XXvs_mVZujF2e3U/* $VF was: $r8$lambda$DfFgKjstQn5_XXvs_mVZujF2e3U*/(
      GxNavigationPage var0, String var1, JSONObject var2
   ) {
      return loadSpeed$lambda$172(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$E7StRAnnwQWORGs8aSPK2S_OE8E/* $VF was: $r8$lambda$E7StRAnnwQWORGs8aSPK2S_OE8E*/(GxNavigationPage var0) {
      return buildMaps$lambda$105(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$G8QeClsbrzfw_4_79fiq0jQ60cg/* $VF was: $r8$lambda$G8QeClsbrzfw_4_79fiq0jQ60cg*/(
      GxNavigationPage var0, Function2 var1, String var2, JSONObject var3, String var4, String var5, JSONObject var6
   ) {
      return resolvePlace$geocode$lambda$49(var0, var1, var2, var3, var4, var5, var6);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$HwC8NKCIZqN6g_jXaxpJKEhAHDY/* $VF was: $r8$lambda$HwC8NKCIZqN6g_jXaxpJKEhAHDY*/(
      GxNavigationPage var0, String var1, JSONObject var2
   ) {
      return saveSelection$lambda$141(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$JlyR0SMxxFwrJKOESpiIEha1Xi0(GxNavigationPage var0, JSONObject var1, String var2, JSONObject var3) {
      return loadNav$lambda$32$lambda$31(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$K9spdNlFyVj5zCjRgGTeGZkilEo(GxNavigationPage var0) {
      return buildSpeed$lambda$168(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$KQXzwrhFZoWZ2HFKQ8ISecKG7LY(Function2 var0, ObjectRef var1, ObjectRef var2) {
      extGet$lambda$19$lambda$18(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$OMtIpf7EJzU7fMEW0ANgRqqwci0(GxNavigationPage var0) {
      return renderKeys$lambda$153(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Omg3Me_qcfZHqd6M_JKSweGIfIA/* $VF was: $r8$lambda$Omg3Me_qcfZHqd6M_JKSweGIfIA*/(
      GxNavigationPage var0, String var1, JSONObject var2
   ) {
      return loadCatalog$lambda$111(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$QBRtkhLNFSVNQ_31aVJNVyjKwVE(GxNavigationPage var0, String var1, JSONObject var2) {
      return toggleFavorite$lambda$60(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$QPdooNi1Iv_O3etvejZGY3_ckwY(String var0, GxNavigationPage var1, Function2 var2) {
      extGet$lambda$19(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$QTSBsS0zqMsZf5z7tJQe5HErMw0(GxNavigationPage var0, String var1, JSONObject var2) {
      return loadKeys$lambda$150$lambda$149(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$QhoxuF_NGfXjphNI_5EIAoZPN20/* $VF was: $r8$lambda$QhoxuF_NGfXjphNI_5EIAoZPN20*/(GxNavigationPage var0) {
      return renderKeys$lambda$154(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$QoXu6Gnt34unqIBkDR_X7IoYkYU/* $VF was: $r8$lambda$QoXu6Gnt34unqIBkDR_X7IoYkYU*/(
      JSONObject var0, boolean var1, boolean var2, GxNavigationPage var3, int var4
   ) {
      return homeWork$lambda$93(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$R7_ZsMYfDzeT2KPqcdfjcOPgWzE/* $VF was: $r8$lambda$R7_ZsMYfDzeT2KPqcdfjcOPgWzE*/(
      GxNavigationPage var0, String var1, boolean var2
   ) {
      return renderCatalog$lambda$139(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$RrjTSFt1W_Irtjqoko70OXSND0Q(GxNavigationPage var0, String var1, JSONObject var2) {
      return toggleFavorite$lambda$63(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$S8y8MEOfogq0kFAC0Pr6GcwRsTE(Function0 var0) {
      return renderCatalog$lambda$134(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$SfvpUmoYMhx3y3FEJTkKvUJaPzw(String var0, JSONObject var1, GxNavigationPage var2, String var3) {
      return renameFavorite$lambda$91(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Te6_gCwNds3R2eSs8JoT_4yedGU/* $VF was: $r8$lambda$Te6_gCwNds3R2eSs8JoT_4yedGU*/(GxNavigationPage var0, int var1) {
      return build$lambda$0(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$VfkEFeBpuu5itMvvTPFaDasoyxQ(GxNavigationPage var0) {
      return buildDest$lambda$26(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Vx0_elBOjO6EhOzXnen5fy9wjJw/* $VF was: $r8$lambda$Vx0_elBOjO6EhOzXnen5fy9wjJw*/(
      GxNavigationPage var0, String var1, JSONObject var2
   ) {
      return renderFavorites$lambda$89$lambda$88$lambda$87(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$W_C9tGhRcwRXEnQBo2RgSAatnXI(GxNavigationPage var0, String var1, String var2, JSONObject var3) {
      return mapsOp$lambda$145(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$WzYgEeyqQlRlnROQA_E9_XXfMs8(GxNavigationPage var0) {
      return renderKeys$lambda$152(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$XA2MgEh4wN_L3HWbCYImfGqpHoo/* $VF was: $r8$lambda$XA2MgEh4wN_L3HWbCYImfGqpHoo*/(
      GxNavigationPage var0, JSONObject var1, String var2, String var3, JSONObject var4
   ) {
      return setDestination$lambda$55$lambda$54(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$YMKOwcWGftRWFsW4E7hwJn4GWYA(GxNavigationPage var0, String var1, JSONObject var2) {
      return processSpeed$lambda$180(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Ya23yZ2jBS9GAkxXl6XNkxFm55M(GxNavigationPage var0, String var1, JSONObject var2) {
      return loadKeys$lambda$150(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$ZAvbndPpQ_mTFEXrSWbUajZsEGk(GxNavigationPage var0) {
      return buildSpeed$lambda$170(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$_5MCZn0npv1G7zNi7xWNPuXuYOY(GxNavigationPage var0, JSONObject var1) {
      return renderFavorites$lambda$84(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$_bcmlnjEKHRItBEgn3zxTpfHocs(GxNavigationPage var0, String var1, JSONObject var2) {
      return reloadFavorites$lambda$59(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$cIpwgGc8MLAHn0pecllIkGnFV9A(GxNavigationPage var0) {
      return buildMaps$lambda$107(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$cO6VtjPQZ34gqJsR3le5QtVIosA(GxNavigationPage var0, String var1, JSONObject var2) {
      return refreshMapsStatus$lambda$116(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$eSozxjRVsujs5Schg08OpkDD6EI(GxNavigationPage var0, String var1, JSONObject var2) {
      return setDestination$lambda$55(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$fkJn6FA_X9LzCDlt7hB540_WzDE(GxNavigationPage var0, int var1) {
      return renderDest$lambda$79(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$fyMAPvQV_xNeAnh4tI2Mx4nEv58/* $VF was: $r8$lambda$fyMAPvQV_xNeAnh4tI2Mx4nEv58*/(GxNavigationPage var0) {
      return build$lambda$2(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$hP7jrfp4Lsn6jQBcUUNo6uQ_CXo(GxNavigationPage var0) {
      return buildMaps$lambda$104(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$haeR37YEQSZxf_Lv19ZPi1suJ1g/* $VF was: $r8$lambda$haeR37YEQSZxf_Lv19ZPi1suJ1g*/(GxNavigationPage var0) {
      return buildSpeed$lambda$169(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$iDQOiBbt5ytZBAZLM1fTCEJsM3Y(GxNavigationPage var0, JSONObject var1) {
      return renderSuggestions$lambda$43(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$iDxtlhB7_YKABUDJjIPeBI1pi9M/* $VF was: $r8$lambda$iDxtlhB7_YKABUDJjIPeBI1pi9M*/(GxNavigationPage var0, JSONObject var1) {
      return renderFavorites$lambda$85(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$iMS1baoR7kydDg4Ym3B3aRSOTvs(GxNavigationPage var0, int var1, LinearLayout var2) {
      return build$lambda$1(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$iNabwMd4gQKqCd8OhwREnUNvagg(GxNavigationPage var0, String var1, JSONObject var2) {
      return cancelNavigation$lambda$57(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$ijG_S_QnMrhQ3T3eC6a03Xa8oQg(GxNavigationPage var0) {
      return renderKeyNotice$lambda$33(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$k4aMAm6umlSpbNHqSXJrpJ8wF_A(GxNavigationPage var0, GxNavigationPage.KeyMeta var1, String var2, JSONObject var3) {
      return keyRow$lambda$162$lambda$161$lambda$160(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$kXxdTy393VM6y8NBMMsZVz1rpBM(GxNavigationPage var0) {
      return renderKeyNotice$lambda$34(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$lM6oeGZayMa3A_OBSmPdKDbZ5Ag/* $VF was: $r8$lambda$lM6oeGZayMa3A_OBSmPdKDbZ5Ag*/(
      GxNavigationPage var0, String var1, JSONObject var2
   ) {
      return saveSchedule$lambda$143(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$lrTSK49v7xd4t_ykdhnos_DX7J0/* $VF was: $r8$lambda$lrTSK49v7xd4t_ykdhnos_DX7J0*/(GxNavigationPage var0) {
      return buildMaps$lambda$103(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$m3WC9N1PVE7OaarpxlzYxCPUxLI(GxNavigationPage var0) {
      return renderDest$lambda$74(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$qQ7LTha7tAf9TZ6l5_cGR5HI6IY/* $VF was: $r8$lambda$qQ7LTha7tAf9TZ6l5_cGR5HI6IY*/(
      GxNavigationPage var0, GxNavigationPage.KeyMeta var1, String var2, String var3, JSONObject var4
   ) {
      return editKey$lambda$166$lambda$165(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static void $r8$lambda$r5Bc_EhTydq9oSJseexKzm7dgWU/* $VF was: $r8$lambda$r5Bc_EhTydq9oSJseexKzm7dgWU*/(GxNavigationPage var0) {
      searchRunner$lambda$22(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$rfB_5GZjbheIzWGGg9J1t5WIGfA/* $VF was: $r8$lambda$rfB_5GZjbheIzWGGg9J1t5WIGfA*/(
      GxNavigationPage var0, JSONObject var1, String var2, Pair var3, String var4, JSONObject var5
   ) {
      return previewDestination$lambda$71(var0, var1, var2, var3, var4, var5);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$sBQTBqKT1NuOFm30IVfkUFI56tw(int var0, GxNavigationPage var1, String var2, JSONObject var3) {
      return search$lambda$38(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$sGfE8bLshtkOjgq1EoEfzGb4J6s(GxNavigationPage var0, String var1, JSONObject var2) {
      return setDestination$lambda$55$lambda$54$lambda$53(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$t6LK8tNumvSyzV6kIuIeE0Lar_o(GxNavigationPage var0, String var1) {
      return renderCatalog$lambda$132(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$tIwjqE44tfRM2gSe3rRBVxokHEA(GxNavigationPage var0, JSONObject var1) {
      return renderSuggestions$lambda$44(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$u0rv37MQqY1S8s_3GHyhoyNYnEw/* $VF was: $r8$lambda$u0rv37MQqY1S8s_3GHyhoyNYnEw*/(
      GxNavigationPage var0, int var1, String var2, String var3, JSONObject var4
   ) {
      return homeWork$lambda$93$lambda$92(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$uITR5LDAzHTT55SHeKA5rVUpsdA(GxNavigationPage var0, JSONObject var1) {
      return renderFavorites$lambda$86(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$v91ap3OstY_jXHTcWAwvGiC3hIs/* $VF was: $r8$lambda$v91ap3OstY_jXHTcWAwvGiC3hIs*/(GxNavigationPage var0) {
      return build$lambda$3(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$v_v3Smb69iiiwnMlHCsOrVBWq6w(List var0, GxNavigationPage var1) {
      return renderCatalog$lambda$136(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$wHc9bifMzEE0Q6HaAm7nrpBJ0Vg(GxNavigationPage var0, GxNavigationPage.KeyMeta var1) {
      return keyRow$lambda$157(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$xPs26eeMVf_2bH3OekXx4ggoWgw(GxNavigationPage var0, JSONObject var1) {
      return renderRecents$lambda$99(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$y6wF81vpYYAy55dMbkGOykzxG9A(Function0 var0) {
      return renderCatalog$lambda$133(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$yLnbl3OLF8tthuqYjd3WmN37STU(GxNavigationPage var0) {
      return buildMaps$lambda$106(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$yUHFAtQsOxN0_KijDAZ7P_DUaIQ/* $VF was: $r8$lambda$yUHFAtQsOxN0_KijDAZ7P_DUaIQ*/(GxNavigationPage var0, JSONObject var1) {
      return renderFavorites$lambda$89(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$zmoaQq0EidXxfmZZ7QbmE9ZNYek(
      GxNavigationPage var0, Function2 var1, String var2, JSONObject var3, String var4, String var5, JSONObject var6
   ) {
      return resolvePlace$lambda$50(var0, var1, var2, var3, var4, var5, var6);
   }

   public GxNavigationPage(Activity var1, GxHost var2) {
      Intrinsics.checkNotNullParameter(var1, "act");
      Intrinsics.checkNotNullParameter(var2, "gx");
      super(var1, var2);
      this.needsOffroad = true;
      this.navLoading = true;
      this.navError = "";
      this.mapboxPublic = "";
      this.mapboxSecret = "";
      this.language = "";
      this.favorites = CollectionsKt.emptyList();
      this.recentDestinations = CollectionsKt.emptyList();
      this.routes = CollectionsKt.emptyList();
      this.selectedRouteId = "main";
      this.suggestions = CollectionsKt.emptyList();
      String var5 = UUID.randomUUID().toString();
      Intrinsics.checkNotNullExpressionValue(var5, "toString(...)");
      this.sessionToken = var5;
      this.searchRunner = new GxNavigationPage$$ExternalSyntheticLambda55(this);
      this.mapsLoadingCatalog = true;
      this.mapsLoadingStatus = true;
      this.mapsError = "";
      this.mapsSearch = "";
      this.mapsSections = CollectionsKt.emptyList();
      this.scheduleOptions = CollectionsKt.emptyList();
      this.mapsStatus = new JSONObject();
      this.mapsProgress = new JSONObject();
      this.selectedSaved = SetsKt.emptySet();
      this.selectedDraft = new LinkedHashSet<>();
      this.scheduleSaved = "2";
      this.scheduleDraft = "2";
      this.tokenLabels = new HashMap<>();
      this.expandedGroups = new HashSet<>();
      this.regionSwitches = new HashMap<>();
      this.groupRows = new HashMap<>();
      GxNavigationPage.KeyMeta var6 = new GxNavigationPage.KeyMeta("amap1", "amap1", "", 39, "AMap / Gaode 1", "amap1Key");
      GxNavigationPage.KeyMeta var3 = new GxNavigationPage.KeyMeta("amap2", "amap2", "", 39, "AMap / Gaode 2", "amap2Key");
      GxNavigationPage.KeyMeta var7 = new GxNavigationPage.KeyMeta("public", "public", "pk.", 80, "Public Mapbox", "mapboxPublic");
      GxNavigationPage.KeyMeta var4 = new GxNavigationPage.KeyMeta("secret", "secret", "sk.", 80, "Secret Mapbox", "mapboxSecret");
      this.keyMeta = CollectionsKt.listOf(new GxNavigationPage.KeyMeta[]{var6, var3, var7, var4});
      this.savedKeys = new HashMap<>();
      this.keysLoading = true;
      this.keysError = "";
      this.appUrl = "https://play.google.com/store/apps/details?id=com.embaucha.galaxynav&hl=en-US&ah=9FldHJ99kxL8oNbSlO5F4sQqwC4";
      this.cookieName = "galaxy_session";
      this.galaxyToken = "";
      this.speedLoading = true;
      this.speedReason = "";
      this.speedStatus = "Idle";
      this.speedObj = new JSONObject();
   }

   private static final Unit build$lambda$0(GxNavigationPage var0, int var1) {
      var0.curTab = var1;
      switch (var1) {
         case 0:
            var0.loadNav();
            break;
         case 1:
            var0.refreshMapsStatus();
            break;
         case 2:
            var0.loadKeys();
            break;
         case 3:
            var0.loadSpeed();
      }

      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$1(GxNavigationPage var0, int var1, LinearLayout var2) {
      Intrinsics.checkNotNullParameter(var2, "box");
      switch (var1) {
         case 0:
            var0.buildDest(var2);
            break;
         case 1:
            var0.buildMaps(var2);
            break;
         case 2:
            var0.buildKeys(var2);
            break;
         case 3:
            var0.buildSpeed(var2);
      }

      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$2(GxNavigationPage var0) {
      if (var0.curTab == 1) {
         var0.refreshMapsStatus();
      }

      return Unit.INSTANCE;
   }

   private static final Unit build$lambda$3(GxNavigationPage var0) {
      if (var0.curTab == 3) {
         var0.loadSpeed();
      }

      return Unit.INSTANCE;
   }

   private final void buildDest(LinearLayout var1) {
      this.keyBox = this.box((ViewGroup)var1);
      this.searchCard = this.card((ViewGroup)var1);
      GxPage var5 = this;
      LinearLayout var3 = this.searchCard;
      Object var4 = null;
      LinearLayout var2 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("searchCard");
         var2 = null;
      }

      GxPage.text$default(var5, "Search for a destination", (ViewGroup)var2, 21.0F, CarUi.Companion.getTEXT(), false, 16, null);
      EditText var7 = this.field("Search here");
      var7.setImeOptions(3);
      var7.setInputType(524289);
      var7.setOnEditorActionListener(new GxNavigationPage$$ExternalSyntheticLambda54(this));
      var7.addTextChangedListener(new TextWatcher() {

         public void afterTextChanged(Editable var1) {
            if (!GxNavigationPage.this.quietEdit) {
               GxNavigationPage.this.onInput();
            }
         }

         public void beforeTextChanged(CharSequence var1, int var2x, int var3x, int var4x) {
         }

         public void onTextChanged(CharSequence var1, int var2x, int var3x, int var4x) {
         }
      });
      this.queryEdit = var7;
      var3 = this.searchCard;
      var2 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("searchCard");
         var2 = null;
      }

      EditText var15 = this.queryEdit;
      EditText var12 = var15;
      if (var15 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("queryEdit");
         var12 = null;
      }

      View var13 = (View)var12;
      LayoutParams var16 = new LayoutParams(-1, -2);
      var16.topMargin = this.getUi().dp(10);
      var16.bottomMargin = this.getUi().dp(8);
      Unit var6 = Unit.INSTANCE;
      var2.addView(var13, (android.view.ViewGroup.LayoutParams)var16);
      Pair var14 = TuplesKt.to("Set Destination", new GxNavigationPage$$ExternalSyntheticLambda56(this));
      Pair var18 = TuplesKt.to("Search", new GxNavigationPage$$ExternalSyntheticLambda57(this));
      Pair var17 = TuplesKt.to("Clear", new GxNavigationPage$$ExternalSyntheticLambda58(this));
      var2 = this.searchCard;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("searchCard");
         var2 = (LinearLayout)var4;
      }

      ViewGroup var10 = (ViewGroup)var2;
      this.buttons(new Pair[]{var14, var18, var17}, var10, true);
      this.statusNote = this.note("", (ViewGroup)var1);
      this.suggestBox = this.box((ViewGroup)var1);
      this.destBox = this.box((ViewGroup)var1);
      this.favBox = this.box((ViewGroup)var1);
      this.recentBox = this.box((ViewGroup)var1);
      this.renderDestAll();
   }

   private static final boolean buildDest$lambda$24$lambda$23(GxNavigationPage var0, TextView var1, int var2, KeyEvent var3) {
      boolean var4;
      switch (var2) {
         case 3:
         case 6:
            var0.setDestination(null);
            var4 = true;
            break;
         default:
            var4 = false;
      }

      return var4;
   }

   private static final Unit buildDest$lambda$26(GxNavigationPage var0) {
      var0.setDestination(null);
      return Unit.INSTANCE;
   }

   private static final Unit buildDest$lambda$27(GxNavigationPage var0) {
      var0.getMain().removeCallbacks(var0.searchRunner);
      String var1 = StringsKt.trim(var0.getQuery()).toString();
      if (var1.length() >= 3) {
         var0.search(var1);
      } else {
         var0.toast("Type at least 3 letters.");
      }

      return Unit.INSTANCE;
   }

   private static final Unit buildDest$lambda$28(GxNavigationPage var0) {
      var0.setQuery("");
      var0.onInput();
      return Unit.INSTANCE;
   }

   private final void buildKeys(LinearLayout var1) {
      this.keysBox = this.box((ViewGroup)var1);
      this.renderKeys();
   }

   private final void buildMaps(LinearLayout var1) {
      TextView var2 = this.note("", (ViewGroup)var1);
      var2.setTextColor(CarUi.Companion.getWARN());
      var2.setVisibility(8);
      this.mapsErrorNote = var2;
      this.header("Offline Maps", (ViewGroup)var1);
      this.note("Select regions, start downloads, and manage offline maps entirely from Galaxy.", (ViewGroup)var1);
      this.vDownloader = this.statRow("Downloader", var1);
      this.vSaved = this.statRow("Saved Regions", var1);
      this.vEstimate = this.statRow("Additional Storage", var1);
      this.vLastUpdate = this.statRow("Last Updated", var1);
      this.vStorage = this.statRow("Storage Used", var1);
      this.nOnroad = this.noteIn("Map downloads and removal are blocked while driving.", var1);
      this.nSelDirty = this.noteIn("You have unsaved region changes. Downloading now will use the current selection.", var1);
      this.nSchDirty = this.noteIn("You have an unsaved schedule change. Downloading now will also apply it.", var1);
      Pair var5 = TuplesKt.to("Download Maps", new GxNavigationPage$$ExternalSyntheticLambda25(this));
      Pair var7 = TuplesKt.to("Remove Maps", new GxNavigationPage$$ExternalSyntheticLambda26(this));
      Pair var3 = TuplesKt.to("Save Selection", new GxNavigationPage$$ExternalSyntheticLambda27(this));
      Pair var8 = TuplesKt.to("Reset", new GxNavigationPage$$ExternalSyntheticLambda28(this));
      Pair var4 = TuplesKt.to("Clear All", new GxNavigationPage$$ExternalSyntheticLambda29(this));
      ViewGroup var6 = (ViewGroup)var1;
      this.mapsBar = this.buttons(new Pair[]{var5, var7, var3, var8, var4}, var6, true);
      LinearLayout var25 = this.mapsBar;
      var3 = null;
      LinearLayout var9 = var25;
      if (var25 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("mapsBar");
         var9 = null;
      }

      View var10 = var9.getChildAt(1);
      if (var10 instanceof TextView) {
         var2 = (TextView)var10;
      } else {
         var2 = null;
      }

      if (var2 != null) {
         var2.setTextColor(CarUi.Companion.getWARN());
      }

      LinearLayout var12 = this.card((ViewGroup)var1);
      var12.setVisibility(8);
      this.progCard = var12;
      LinearLayout var26 = this.progCard;
      LinearLayout var13 = var26;
      if (var26 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("progCard");
         var13 = null;
      }

      this.progTitle = this.text("Download Progress", (ViewGroup)var13, 21.0F, CarUi.Companion.getTEXT(), true);
      LinearLayout var27 = this.progCard;
      LinearLayout var14 = var27;
      if (var27 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("progCard");
         var14 = null;
      }

      this.progBar = this.progress((ViewGroup)var14);
      GxPage var34 = this;
      LinearLayout var28 = this.progCard;
      LinearLayout var15 = var28;
      if (var28 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("progCard");
         var15 = null;
      }

      this.pEstimate = GxPage.text$default(var34, "", (ViewGroup)var15, 0.0F, 0, false, 28, null);
      GxPage var35 = this;
      LinearLayout var29 = this.progCard;
      LinearLayout var16 = var29;
      if (var29 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("progCard");
         var16 = null;
      }

      this.pStored = GxPage.text$default(var35, "", (ViewGroup)var16, 0.0F, 0, false, 28, null);
      GxPage var36 = this;
      LinearLayout var30 = this.progCard;
      LinearLayout var17 = var30;
      if (var30 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("progCard");
         var17 = null;
      }

      this.pFiles = GxPage.text$default(var36, "", (ViewGroup)var17, 0.0F, 0, false, 28, null);
      GxPage var37 = this;
      LinearLayout var31 = this.progCard;
      LinearLayout var18 = var31;
      if (var31 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("progCard");
         var18 = null;
      }

      this.pEta = GxPage.text$default(var37, "", (ViewGroup)var18, 0.0F, 0, false, 28, null);
      GxPage var32 = this;
      LinearLayout var19 = this.progCard;
      if (var19 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("progCard");
         var19 = var3;
      }

      this.pRegion = GxPage.text$default(var32, "", (ViewGroup)var19, 0.0F, 0, false, 28, null);
      this.header("Regions & Schedule", (ViewGroup)var1);
      LinearLayout var20 = this.card((ViewGroup)var1);
      GxPage.text$default(this, "Search", (ViewGroup)var20, 21.0F, CarUi.Companion.getTEXT(), false, 16, null);
      EditText var23 = this.field("Filter by name or code");
      var23.addTextChangedListener(new TextWatcher() {

         public void afterTextChanged(Editable var1) {
            GxNavigationPage var3x = GxNavigationPage.this;
            String var4x;
            if (var1 != null) {
               var4x = var1.toString();
            } else {
               var4x = null;
            }

            String var2x = var4x;
            if (var4x == null) {
               var2x = "";
            }

            var3x.mapsSearch = var2x;
            GxNavigationPage.this.renderCatalog();
         }

         public void beforeTextChanged(CharSequence var1, int var2x, int var3x, int var4x) {
         }

         public void onTextChanged(CharSequence var1, int var2x, int var3x, int var4x) {
         }
      });
      View var24 = (View)var23;
      LayoutParams var38 = new LayoutParams(-1, -2);
      var38.topMargin = this.getUi().dp(8);
      var38.bottomMargin = this.getUi().dp(6);
      Unit var33 = Unit.INSTANCE;
      var20.addView(var24, (android.view.ViewGroup.LayoutParams)var38);
      this.scheduleBox = this.box((ViewGroup)var1);
      LinearLayout var21 = this.card((ViewGroup)var1);
      GxPage.text$default(this, "Selected Regions", (ViewGroup)var21, 21.0F, CarUi.Companion.getTEXT(), false, 16, null);
      this.selRegionsText = GxPage.text$default(this, "No regions selected.", (ViewGroup)var21, 0.0F, 0, false, 28, null);
      this.schedNote = this.note("", (ViewGroup)var1);
      this.catalogBox = this.box((ViewGroup)var1);
      this.loadCatalog();
   }

   private static final Unit buildMaps$lambda$103(GxNavigationPage var0) {
      if (var0.isDownloading()) {
         var0.cancelDownload();
      } else {
         var0.startDownload();
      }

      return Unit.INSTANCE;
   }

   private static final Unit buildMaps$lambda$104(GxNavigationPage var0) {
      var0.removeMaps();
      return Unit.INSTANCE;
   }

   private static final Unit buildMaps$lambda$105(GxNavigationPage var0) {
      var0.saveSelection();
      return Unit.INSTANCE;
   }

   private static final Unit buildMaps$lambda$106(GxNavigationPage var0) {
      var0.selectedDraft.clear();
      var0.selectedDraft.addAll(var0.selectedSaved);
      var0.scheduleDraft = var0.scheduleSaved;
      var0.paintMaps();
      var0.renderCatalog();
      return Unit.INSTANCE;
   }

   private static final Unit buildMaps$lambda$107(GxNavigationPage var0) {
      var0.selectedDraft.clear();
      var0.paintMaps();
      return Unit.INSTANCE;
   }

   private final void buildSchedule() {
      LinearLayout var4 = this.scheduleBox;
      Object var3 = null;
      LinearLayout var2 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("scheduleBox");
         var2 = null;
      }

      var2.removeAllViews();
      this.paintSchedule = null;
      if (!this.scheduleOptions.isEmpty()) {
         Iterable var12 = this.scheduleOptions;
         var var6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var12, 10));
         Iterator var13 = var12.iterator();

         while (var13.hasNext()) {
            var6.add((String)((Pair)var13.next()).getSecond());
         }

         List var5 = var6;
         List var7 = this.scheduleOptions;
         int var1 = 0;
         Iterator var8 = var7.iterator();

         while (true) {
            if (!var8.hasNext()) {
               var1 = -1;
               break;
            }

            if (Intrinsics.areEqual(((Pair)var8.next()).getFirst(), this.scheduleDraft)) {
               break;
            }

            var1++;
         }

         var4 = this.scheduleBox;
         var2 = var4;
         if (var4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scheduleBox");
            var2 = null;
         }

         this.paintSchedule = this.choice(
            "Auto Update",
            "How often the comma refreshes the downloaded maps.",
            var5,
            var1,
            (ViewGroup)var2,
            new GxNavigationPage$$ExternalSyntheticLambda47(this)
         );
         GxPage var15 = this;
         Pair var16 = TuplesKt.to("Apply", new GxNavigationPage$$ExternalSyntheticLambda48(this));
         var2 = this.scheduleBox;
         if (var2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scheduleBox");
            var2 = (LinearLayout)var3;
         }

         ViewGroup var11 = (ViewGroup)var2;
         GxPage.buttons$default(var15, new Pair[]{var16}, var11, false, 4, null);
      }
   }

   private static final Unit buildSchedule$lambda$114(GxNavigationPage var0, int var1) {
      var0.scheduleDraft = var0.scheduleOptions.get(var1).getFirst();
      var0.paintMaps();
      return Unit.INSTANCE;
   }

   private static final Unit buildSchedule$lambda$115(GxNavigationPage var0) {
      var0.saveSchedule();
      return Unit.INSTANCE;
   }

   private final void buildSpeed(LinearLayout var1) {
      this.header("Download Speed Limits", (ViewGroup)var1);
      this.note("Enable \"Speed Limit Filler\" on the device, drive to collect data, then process and download it here when parked.", (ViewGroup)var1);
      this.sStatus = this.statRow("Processor Status", var1);
      this.sVision = this.statRow("Vision Detector", var1);
      this.sDebugRow = GxPage.row$default(this, "Vision Debug", null, (ViewGroup)var1, 0, 8, null);
      GxPage.Row var3 = this.sDebugRow;
      GxPage.Row var2 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sDebugRow");
         var2 = null;
      }

      this.sDebug = var2.value("");
      this.sLastEvent = this.note("", (ViewGroup)var1);
      TextView var6 = this.note("", (ViewGroup)var1);
      var6.setTextColor(CarUi.Companion.getWARN());
      this.sReason = var6;
      Pair var4 = TuplesKt.to("Download", new GxNavigationPage$$ExternalSyntheticLambda50(this));
      Pair var7 = TuplesKt.to("Process Now", new GxNavigationPage$$ExternalSyntheticLambda51(this));
      Pair var8 = TuplesKt.to("Submit speed limits here", new GxNavigationPage$$ExternalSyntheticLambda52(this));
      ViewGroup var5 = (ViewGroup)var1;
      this.sBar = this.buttons(new Pair[]{var4, var7, var8}, var5, true);
      this.paintSpeed();
   }

   private static final Unit buildSpeed$lambda$168(GxNavigationPage var0) {
      var0.download("/api/speed_limits", "speed_limits.json", "application/json");
      return Unit.INSTANCE;
   }

   private static final Unit buildSpeed$lambda$169(GxNavigationPage var0) {
      var0.processSpeed();
      return Unit.INSTANCE;
   }

   private static final Unit buildSpeed$lambda$170(GxNavigationPage var0) {
      var0.openUrl("https://nerf.077769.xyz/");
      return Unit.INSTANCE;
   }

   private final void cancelDownload() {
      if (!this.mapsActionBusy && this.isDownloading()) {
         this.mapsOp("cancel", new JSONObject(), "Map download cancellation requested.");
      }
   }

   private final void cancelNavigation() {
      GxPage.send$default(this, "DELETE", "/api/navigation", null, 0L, new GxNavigationPage$$ExternalSyntheticLambda38(this), 12, null);
   }

   private static final Unit cancelNavigation$lambda$57(GxNavigationPage var0, String var1, JSONObject var2) {
      if (var1 != null) {
         CharSequence var4 = var1;
         Object var3 = var4;
         if (StringsKt.isBlank(var4)) {
            var3 = "Could not cancel navigation.";
         }

         var0.toast((String)var3);
         return Unit.INSTANCE;
      } else {
         var0.navigationStarted = false;
         var0.destination = null;
         var0.routeSummary = null;
         var0.routes = CollectionsKt.emptyList();
         var0.selectedRouteId = "main";
         var0.suggestions = CollectionsKt.emptyList();
         var0.setQuery("");
         var0.toast("Navigation cancelled.");
         var0.renderSuggestions();
         var0.renderDest();
         return Unit.INSTANCE;
      }
   }

   private final void chooseSuggestion(JSONObject var1) {
      this.searching = true;
      this.renderStatus();
      this.resolvePlace(var1, new GxNavigationPage$$ExternalSyntheticLambda75(this));
   }

   private static final Unit chooseSuggestion$lambda$51(GxNavigationPage var0, String var1, JSONObject var2) {
      var0.searching = false;
      if (var1 == null && var2 != null) {
         var0.selectedRouteId = "main";
         var0.routes = CollectionsKt.emptyList();
         var0.routeSummary = null;
         var0.navigationStarted = false;
         var0.destination = var2;
         var0.setQuery(GxPage.str$default(var0, var2, "name", null, 2, null));
         var0.suggestions = CollectionsKt.emptyList();
         var0.navError = "";
         var0.renderStatus();
         var0.renderSuggestions();
         var0.renderDest();
         var0.previewDestination(var2, null);
         return Unit.INSTANCE;
      } else {
         if (var1 == null) {
            var1 = "Could not determine that location.";
         }

         var0.navError = var1;
         var0.toast(var0.navError);
         var0.renderStatus();
         return Unit.INSTANCE;
      }
   }

   private final void clickable(GxPage.Row var1, Function0<Unit> var2) {
      var1.getView().setClickable(true);
      var1.getView().setOnClickListener(new GxNavigationPage$$ExternalSyntheticLambda49(var2));
   }

   private static final void clickable$lambda$13(Function0 var0, View var1) {
      var0.invoke();
   }

   private final Pair<Double, Double> coordsArr(JSONArray var1) {
      if (var1 != null && var1.length() >= 2) {
         Double var4 = this.numOf(var1.opt(0));
         if (var4 != null) {
            double var2 = var4;
            Double var5 = this.numOf(var1.opt(1));
            if (var5 != null) {
               return TuplesKt.to(var5, var2);
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private final Pair<Double, Double> coordsOf(JSONObject var1) {
      if (var1 == null) {
         return null;
      } else {
         JSONObject var4 = var1.optJSONObject("geometry");
         JSONArray var6;
         if (var4 != null) {
            var6 = var4.optJSONArray("coordinates");
         } else {
            var6 = null;
         }

         Pair var7 = this.coordsArr(var6);
         if (var7 != null) {
            return var7;
         } else {
            Double var8 = this.numOf(var1.opt("latitude"));
            if (var8 != null) {
               double var2 = var8;
               Double var5 = this.numOf(var1.opt("longitude"));
               if (var5 != null) {
                  return TuplesKt.to(var2, var5);
               }
            }

            return null;
         }
      }
   }

   private final void editKey(GxNavigationPage.KeyMeta var1) {
      GxPage var2 = this;
      String var3 = var1.getTitle();
      String var4 = var3 + " key";
      var3 = var1.getPrefix();
      GxPage.prompt$default(var2, var4, var3 + "xxxxxx...", "", 524433, null, new GxNavigationPage$$ExternalSyntheticLambda31(var1, this), 16, null);
   }

   private static final Unit editKey$lambda$166(GxNavigationPage.KeyMeta var0, GxNavigationPage var1, String var2) {
      Intrinsics.checkNotNullParameter(var2, "v");
      var2 = StringsKt.trim(var2).toString();
      boolean var3;
      if (var2.length() == 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (var3) {
         return Unit.INSTANCE;
      } else {
         if (!StringsKt.startsWith$default(var2, var0.getPrefix(), false, 2, null)) {
            String var4 = var0.getPrefix();
            var2 = var4 + var2;
         }

         if (var2.length() < var0.getMin()) {
            String var5 = var0.getTitle();
            var1.toast(var5 + " key is invalid or too short...");
            return Unit.INSTANCE;
         } else {
            GxPage.send$default(
               var1,
               "POST",
               "/api/navigation_key",
               new JSONObject().put(var0.getBody(), var2),
               0L,
               new GxNavigationPage$$ExternalSyntheticLambda66(var1, var0, var2),
               8,
               null
            );
            return Unit.INSTANCE;
         }
      }
   }

   private static final Unit editKey$lambda$166$lambda$165(GxNavigationPage var0, GxNavigationPage.KeyMeta var1, String var2, String var3, JSONObject var4) {
      if (var3 != null) {
         CharSequence var6 = var3;
         Object var5 = var6;
         if (StringsKt.isBlank(var6)) {
            var5 = "Save failed...";
         }

         var0.toast((String)var5);
         return Unit.INSTANCE;
      } else {
         label31: {
            if (var4 != null) {
               var3 = var4.optString("message");
               if (var3 != null) {
                  CharSequence var10 = var3;
                  CharSequence var8 = var10;
                  if (StringsKt.isBlank(var10)) {
                     var8 = null;
                  }

                  String var11 = (String)var8;
                  var3 = var11;
                  if (var11 != null) {
                     break label31;
                  }
               }
            }

            var3 = "Saved!";
         }

         var0.toast(var3);
         var0.savedKeys.put(var1.getKind(), var2);
         if (Intrinsics.areEqual(var1.getKind(), "public")) {
            var0.mapboxPublic = var2;
         } else if (Intrinsics.areEqual(var1.getKind(), "secret")) {
            var0.mapboxSecret = var2;
         }

         var0.renderKeys();
         var0.renderNavAfterKeys();
         return Unit.INSTANCE;
      }
   }

   private final void extGet(String var1, Function2<? super String, ? super JSONObject, Unit> var2) {
      new Thread(new GxNavigationPage$$ExternalSyntheticLambda76(var1, this, var2)).start();
   }

   private static final void extGet$lambda$19(String param0, GxNavigationPage param1, Function2 param2) {
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
      // 012: getstatic com/starpilot/starview/GxPage.Companion Lcom/starpilot/starview/GxPage$Companion;
      // 015: ldc2_w 20
      // 018: invokevirtual com/starpilot/starview/GxPage$Companion.client (J)Lokhttp3/OkHttpClient;
      // 01b: astore 4
      // 01d: new okhttp3/Request$Builder
      // 020: astore 7
      // 022: aload 7
      // 024: invokespecial okhttp3/Request$Builder.<init> ()V
      // 027: aload 4
      // 029: aload 7
      // 02b: aload 0
      // 02c: invokevirtual okhttp3/Request$Builder.url (Ljava/lang/String;)Lokhttp3/Request$Builder;
      // 02f: invokevirtual okhttp3/Request$Builder.build ()Lokhttp3/Request;
      // 032: invokevirtual okhttp3/OkHttpClient.newCall (Lokhttp3/Request;)Lokhttp3/Call;
      // 035: invokeinterface okhttp3/Call.execute ()Lokhttp3/Response; 1
      // 03a: checkcast java/io/Closeable
      // 03d: astore 7
      // 03f: aload 7
      // 041: checkcast okhttp3/Response
      // 044: astore 8
      // 046: aload 8
      // 048: invokevirtual okhttp3/Response.body ()Lokhttp3/ResponseBody;
      // 04b: astore 0
      // 04c: aload 0
      // 04d: ifnull 05e
      // 050: aload 0
      // 051: invokevirtual okhttp3/ResponseBody.string ()Ljava/lang/String;
      // 054: astore 4
      // 056: aload 4
      // 058: astore 0
      // 059: aload 4
      // 05b: ifnonnull 062
      // 05e: ldc_w ""
      // 061: astore 0
      // 062: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 065: astore 4
      // 067: new org/json/JSONObject
      // 06a: astore 4
      // 06c: aload 4
      // 06e: aload 0
      // 06f: invokespecial org/json/JSONObject.<init> (Ljava/lang/String;)V
      // 072: aload 4
      // 074: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 077: astore 0
      // 078: goto 089
      // 07b: astore 0
      // 07c: getstatic kotlin/Result.Companion Lkotlin/Result$Companion;
      // 07f: astore 4
      // 081: aload 0
      // 082: invokestatic kotlin/ResultKt.createFailure (Ljava/lang/Throwable;)Ljava/lang/Object;
      // 085: invokestatic kotlin/Result.constructor-impl (Ljava/lang/Object;)Ljava/lang/Object;
      // 088: astore 0
      // 089: aload 0
      // 08a: astore 4
      // 08c: aload 0
      // 08d: invokestatic kotlin/Result.isFailure-impl (Ljava/lang/Object;)Z
      // 090: ifeq 096
      // 093: aconst_null
      // 094: astore 4
      // 096: aload 6
      // 098: aload 4
      // 09a: putfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 09d: aload 8
      // 09f: invokevirtual okhttp3/Response.isSuccessful ()Z
      // 0a2: ifne 142
      // 0a5: aload 6
      // 0a7: getfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 0aa: checkcast org/json/JSONObject
      // 0ad: astore 0
      // 0ae: aload 0
      // 0af: ifnull 0df
      // 0b2: aload 0
      // 0b3: ldc_w "message"
      // 0b6: invokevirtual org/json/JSONObject.optString (Ljava/lang/String;)Ljava/lang/String;
      // 0b9: astore 0
      // 0ba: aload 0
      // 0bb: ifnull 0df
      // 0be: aload 0
      // 0bf: checkcast java/lang/CharSequence
      // 0c2: astore 4
      // 0c4: aload 4
      // 0c6: astore 0
      // 0c7: aload 4
      // 0c9: invokestatic kotlin/text/StringsKt.isBlank (Ljava/lang/CharSequence;)Z
      // 0cc: ifeq 0d1
      // 0cf: aconst_null
      // 0d0: astore 0
      // 0d1: aload 0
      // 0d2: checkcast java/lang/String
      // 0d5: astore 4
      // 0d7: aload 4
      // 0d9: astore 0
      // 0da: aload 4
      // 0dc: ifnonnull 13c
      // 0df: aload 6
      // 0e1: getfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 0e4: checkcast org/json/JSONObject
      // 0e7: astore 0
      // 0e8: aload 0
      // 0e9: ifnull 114
      // 0ec: aload 0
      // 0ed: ldc_w "error"
      // 0f0: invokevirtual org/json/JSONObject.optString (Ljava/lang/String;)Ljava/lang/String;
      // 0f3: astore 0
      // 0f4: aload 0
      // 0f5: ifnull 114
      // 0f8: aload 0
      // 0f9: checkcast java/lang/CharSequence
      // 0fc: astore 4
      // 0fe: aload 4
      // 100: astore 0
      // 101: aload 4
      // 103: invokestatic kotlin/text/StringsKt.isBlank (Ljava/lang/CharSequence;)Z
      // 106: ifeq 10b
      // 109: aconst_null
      // 10a: astore 0
      // 10b: aload 0
      // 10c: checkcast java/lang/String
      // 10f: astore 4
      // 111: goto 117
      // 114: aconst_null
      // 115: astore 4
      // 117: aload 4
      // 119: astore 0
      // 11a: aload 4
      // 11c: ifnonnull 13c
      // 11f: aload 8
      // 121: invokevirtual okhttp3/Response.code ()I
      // 124: istore 3
      // 125: new java/lang/StringBuilder
      // 128: astore 0
      // 129: aload 0
      // 12a: invokespecial java/lang/StringBuilder.<init> ()V
      // 12d: aload 0
      // 12e: ldc_w "HTTP "
      // 131: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 134: iload 3
      // 135: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 138: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 13b: astore 0
      // 13c: aload 5
      // 13e: aload 0
      // 13f: putfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 142: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 145: astore 0
      // 146: aload 7
      // 148: aconst_null
      // 149: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 14c: goto 17b
      // 14f: astore 4
      // 151: aload 4
      // 153: athrow
      // 154: astore 0
      // 155: aload 7
      // 157: aload 4
      // 159: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 15c: aload 0
      // 15d: athrow
      // 15e: astore 7
      // 160: aload 7
      // 162: invokevirtual java/lang/Exception.getMessage ()Ljava/lang/String;
      // 165: astore 4
      // 167: aload 4
      // 169: astore 0
      // 16a: aload 4
      // 16c: ifnonnull 175
      // 16f: aload 7
      // 171: invokevirtual java/lang/Exception.toString ()Ljava/lang/String;
      // 174: astore 0
      // 175: aload 5
      // 177: aload 0
      // 178: putfield kotlin/jvm/internal/Ref$ObjectRef.element Ljava/lang/Object;
      // 17b: aload 1
      // 17c: invokevirtual com/starpilot/starview/GxNavigationPage.getMain ()Landroid/os/Handler;
      // 17f: new com/starpilot/starview/GxNavigationPage$$ExternalSyntheticLambda33
      // 182: dup
      // 183: aload 2
      // 184: aload 5
      // 186: aload 6
      // 188: invokespecial com/starpilot/starview/GxNavigationPage$$ExternalSyntheticLambda33.<init> (Lkotlin/jvm/functions/Function2;Lkotlin/jvm/internal/Ref$ObjectRef;Lkotlin/jvm/internal/Ref$ObjectRef;)V
      // 18b: invokevirtual android/os/Handler.post (Ljava/lang/Runnable;)Z
      // 18e: pop
      // 18f: return
   }

   private static final void extGet$lambda$19$lambda$18(Function2 var0, ObjectRef var1, ObjectRef var2) {
      var0.invoke(var1.element, var2.element);
   }

   private final JSONObject favoriteOf(JSONObject var1) {
      Pair var3 = this.coordsOf(var1);
      Object var2 = null;
      if (var3 == null) {
         return null;
      } else {
         Iterator var4 = this.favorites.iterator();

         do {
            if (!var4.hasNext()) {
               var5 = var2;
               break;
            }

            var5 = var4.next();
         } while (!this.sameSpot(this.coordsOf((JSONObject)var5), var3));

         return (JSONObject)var5;
      }
   }

   private final EditText field(String var1) {
      EditText var2 = new EditText((Context)this.getAct());
      var2.setHint(var1);
      var2.setTextSize(20.0F);
      var2.setTextColor(CarUi.Companion.getTEXT());
      var2.setHintTextColor(CarUi.Companion.getDIM());
      var2.setSingleLine(true);
      var2.setBackground((Drawable)this.getUi().rounded(CarUi.Companion.getCARD_ON(), 12, Color.rgb(60, 66, 78)));
      var2.setPadding(this.getUi().dp(16), this.getUi().dp(12), this.getUi().dp(16), this.getUi().dp(12));
      var2.setMinHeight(this.getUi().dp(56));
      return var2;
   }

   private final String fmtDistance(double var1) {
      String var8;
      if (this.isMetric) {
         if (var1 >= 1000.0) {
            StringCompanionObject var5 = StringCompanionObject.INSTANCE;
            var8 = String.format(Locale.US, "%.1f km", Arrays.copyOf(new Object[]{var1 / 1000}, 1));
            Intrinsics.checkNotNullExpressionValue(var8, "format(...)");
         } else {
            long var3 = Math.round(var1);
            var8 = var3 + " m";
         }
      } else {
         var1 /= 1609.34;
         if (var1 >= 0.1) {
            StringCompanionObject var9 = StringCompanionObject.INSTANCE;
            var8 = String.format(Locale.US, "%.1f mi", Arrays.copyOf(new Object[]{var1}, 1));
            Intrinsics.checkNotNullExpressionValue(var8, "format(...)");
         } else {
            long var7 = Math.round(5280 * var1);
            var8 = var7 + " ft";
         }
      }

      return var8;
   }

   private final String fmtDuration(double var1) {
      double var3 = 3600;
      int var6 = (int)(var1 / var3);
      int var5 = (int)(var1 % var3 / 60);
      StringBuilder var7 = null;
      if (var6 > 0) {
         var7 = new StringBuilder();
         var7 = var7.append(var6).append("h ");
      } else {
         var7 = new StringBuilder();
      }

      return var7.append(var5).append(" min").toString();
   }

   private final String fmtEta(double var1) {
      String var3 = DateFormat.getTimeInstance(3).format(new Date(System.currentTimeMillis() + (long)(1000 * var1)));
      Intrinsics.checkNotNullExpressionValue(var3, "format(...)");
      return var3;
   }

   private final boolean getHasMapbox() {
      boolean var1;
      if (this.mapboxPublic.length() > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private final boolean getHasRoutingKey() {
      boolean var1;
      if (this.mapboxSecret.length() > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private final String getQuery() {
      String var3;
      if (this.queryEdit != null) {
         EditText var2 = this.queryEdit;
         EditText var1 = var2;
         if (var2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("queryEdit");
            var1 = null;
         }

         var3 = var1.getText().toString();
      } else {
         var3 = "";
      }

      return var3;
   }

   private final boolean getScheduleDirty() {
      return Intrinsics.areEqual(this.scheduleDraft, this.scheduleSaved) ^ true;
   }

   private final boolean getSelectionDirty() {
      return Intrinsics.areEqual(CollectionsKt.toSet(this.selectedDraft), this.selectedSaved) ^ true;
   }

   private final int groupSelectedCount(JSONObject var1) {
      label40: {
         JSONArray var6 = var1.optJSONArray("regions");
         if (var6 != null) {
            List var4 = this.objects(var6);
            var7 = var4;
            if (var4 != null) {
               break label40;
            }
         }

         var7 = CollectionsKt.emptyList();
      }

      Iterable var11 = var7;
      var var8 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var11, 10));

      for (JSONObject var5 : var11) {
         var8.add(GxPage.str$default(this, var5, "token", null, 2, null));
      }

      List var9 = var8;
      Set var10 = CollectionsKt.toSet(var9);
      var var13 = this.selectedDraft;
      int var2;
      if (var13 instanceof Collection && var13.isEmpty()) {
         var2 = 0;
      } else {
         var2 = 0;
         Iterator var14 = var13.iterator();

         while (var14.hasNext()) {
            if (var10.contains((String)var14.next())) {
               int var3 = var2 + 1;
               var2 = var3;
               if (var3 < 0) {
                  CollectionsKt.throwCountOverflow();
                  var2 = var3;
               }
            }
         }
      }

      return var2;
   }

   private final String gxBytes(double var1) {
      if (var1 <= 0.0) {
         return "0 MB";
      } else {
         List var5 = CollectionsKt.listOf(new String[]{"B", "KB", "MB", "GB", "TB"});
         int var3 = Math.min((int)(Math.log(var1) / Math.log(1024.0)), var5.size() - 1);
         var1 /= Math.pow(1024.0, var3);
         String var7;
         if (!(var1 >= 10.0) && var3 != 0) {
            StringCompanionObject var8 = StringCompanionObject.INSTANCE;
            var7 = String.format(Locale.US, "%.2f", Arrays.copyOf(new Object[]{var1}, 1));
         } else {
            StringCompanionObject var4 = StringCompanionObject.INSTANCE;
            var7 = String.format(Locale.US, "%.0f", Arrays.copyOf(new Object[]{var1}, 1));
         }

         Intrinsics.checkNotNullExpressionValue(var7, "format(...)");
         Object var9 = var5.get(var3);
         return var7 + " " + var9;
      }
   }

   private final String gxDuration(double var1) {
      long var7 = Math.max(0L, Math.round(var1));
      if (var7 < 60L) {
         return var7 + "s";
      } else {
         long var5 = 60;
         long var3 = var7 / var5;
         if (var3 < 60L) {
            return var3 + "m " + var7 % var5 + "s";
         } else {
            var7 = var3 / var5;
            return var7 + "h " + var3 % var5 + "m";
         }
      }
   }

   private final void homeWork(JSONObject var1) {
      boolean var2 = var1.optBoolean("is_home");
      boolean var3 = var1.optBoolean("is_work");
      String var6 = GxPage.str$default(this, var1, "name", null, 2, null);
      String var4;
      if (var2) {
         var4 = "Remove Home";
      } else {
         var4 = "Set as Home";
      }

      String var5;
      if (var3) {
         var5 = "Remove Work";
      } else {
         var5 = "Set as Work";
      }

      this.pick(var6, CollectionsKt.listOf(new String[]{var4, var5}), new GxNavigationPage$$ExternalSyntheticLambda61(var1, var2, var3, this));
   }

   private static final Unit homeWork$lambda$93(JSONObject var0, boolean var1, boolean var2, GxNavigationPage var3, int var4) {
      JSONObject var7 = new JSONObject();
      JSONObject var6 = (JSONObject)var0.opt("routeId");
      JSONObject var5 = var6;
      if (var6 == null) {
         var5 = (JSONObject)JSONObject.NULL;
      }

      var6 = var7.put("routeId", var5);
      var5 = var0.opt("id");
      Object var8 = var5;
      if (var5 == null) {
         var8 = JSONObject.NULL;
      }

      var5 = var6.put("id", var8);
      if (var4 == 0) {
         if (var1) {
            var5.put("is_home", false);
            var8 = "Home location removed!";
         } else {
            var5.put("is_home", true);
            if (var2) {
               var5.put("is_work", false);
            }

            var8 = "Home location set!";
         }
      } else if (var2) {
         var5.put("is_work", false);
         var8 = "Work location removed!";
      } else {
         var5.put("is_work", true);
         if (var1) {
            var5.put("is_home", false);
         }

         var8 = "Work location set!";
      }

      GxPage.send$default(
         var3, "POST", "/api/navigation/favorite/rename", var5, 0L, new GxNavigationPage$$ExternalSyntheticLambda40(var3, var4, (String)var8), 8, null
      );
      return Unit.INSTANCE;
   }

   private static final Unit homeWork$lambda$93$lambda$92(GxNavigationPage var0, int var1, String var2, String var3, JSONObject var4) {
      if (var3 != null) {
         if (var1 == 0) {
            var2 = "home";
         } else {
            var2 = "work";
         }

         var0.toast("Failed to update " + var2 + " location...");
      } else {
         var0.toast(var2);
         var0.reloadFavorites();
      }

      return Unit.INSTANCE;
   }

   private final boolean isDownloading() {
      return this.mapsStatus.optBoolean("downloading");
   }

   private final boolean isOnroad() {
      return this.mapsStatus.optBoolean("isOnroad");
   }

   private final void keyRow(GxNavigationPage.KeyMeta var1) {
      String var7 = this.savedKeys.get(var1.getKind());
      String var6 = var7;
      if (var7 == null) {
         var6 = "";
      }

      String var8 = var1.getKind();
      int var3 = var8.length();
      boolean var4 = true;
      boolean var12;
      if (var3 > 0) {
         var12 = true;
      } else {
         var12 = false;
      }

      var7 = var8;
      if (var12) {
         StringBuilder var18 = new StringBuilder();
         char var2 = var8.charAt(0);
         String var9 = String.valueOf(var2);
         Intrinsics.checkNotNull(var9, "null cannot be cast to non-null type java.lang.String");
         var9 = var9.toUpperCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var9, "toUpperCase(...)");
         StringBuilder var19 = var18.append(var9);
         var8 = var8.substring(1);
         Intrinsics.checkNotNullExpressionValue(var8, "substring(...)");
         var7 = var19.append(var8).toString();
      }

      CharSequence var20 = var7;
      var7 = new Regex("[0-9]").replace(var20, new GxNavigationPage$$ExternalSyntheticLambda12());
      var8 = var7 + " Key";
      GxPage var10 = this;
      var7 = var1.getTitle();
      String var11 = var7 + "  (" + var8 + ")";
      if (var6.length() == 0) {
         var12 = true;
      } else {
         var12 = false;
      }

      if (var12) {
         var7 = "Not set";
      } else {
         var7 = this.maskKey(var6);
      }

      LinearLayout var31 = this.keysBox;
      LinearLayout var28 = var31;
      if (var31 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("keysBox");
         var28 = null;
      }

      GxPage.Row var29 = GxPage.row$default(var10, var11, var7, (ViewGroup)var28, 0, 8, null);
      TextView var32 = var29.getDesc();
      if (var6.length() == 0) {
         var12 = true;
      } else {
         var12 = false;
      }

      Typeface var24;
      if (var12) {
         var24 = Typeface.DEFAULT;
      } else {
         var24 = Typeface.MONOSPACE;
      }

      var32.setTypeface(var24);
      if (var6.length() == 0) {
         var12 = true;
      } else {
         var12 = false;
      }

      if (var12) {
         var7 = "Set";
      } else {
         var7 = "Change";
      }

      boolean var5;
      if (var6.length() == 0) {
         var5 = true;
      } else {
         var5 = false;
      }

      var29.button(var7, var5, new GxNavigationPage$$ExternalSyntheticLambda13(this, var1));
      if (var6.length() > 0) {
         var12 = var4;
      } else {
         var12 = false;
      }

      if (var12) {
         GxPage.Row.button$default(var29, "Delete", false, new GxNavigationPage$$ExternalSyntheticLambda14(this, var1), 2, null);
      }
   }

   private static final CharSequence keyRow$lambda$156(MatchResult var0) {
      Intrinsics.checkNotNullParameter(var0, "it");
      String var1 = var0.getValue();
      return " " + var1;
   }

   private static final Unit keyRow$lambda$157(GxNavigationPage var0, GxNavigationPage.KeyMeta var1) {
      var0.editKey(var1);
      return Unit.INSTANCE;
   }

   private static final Unit keyRow$lambda$162(GxNavigationPage var0, GxNavigationPage.KeyMeta var1) {
      String var2 = var1.getTitle();
      var0.confirm(
         "Confirm Delete", "Are you sure you want to delete your " + var2 + " key?", "Yes, Delete", new GxNavigationPage$$ExternalSyntheticLambda0(var0, var1)
      );
      return Unit.INSTANCE;
   }

   private static final Unit keyRow$lambda$162$lambda$161(GxNavigationPage var0, GxNavigationPage.KeyMeta var1) {
      GxPage var3 = var0;
      String var2 = var0.enc(var1.getKind());
      GxPage.send$default(var3, "DELETE", "/api/navigation_key?type=" + var2, null, 0L, new GxNavigationPage$$ExternalSyntheticLambda24(var0, var1), 12, null);
      return Unit.INSTANCE;
   }

   private static final Unit keyRow$lambda$162$lambda$161$lambda$160(GxNavigationPage var0, GxNavigationPage.KeyMeta var1, String var2, JSONObject var3) {
      if (var2 != null) {
         CharSequence var8 = var2;
         Object var4 = var8;
         if (StringsKt.isBlank(var8)) {
            var4 = "Delete failed...";
         }

         var0.toast((String)var4);
         return Unit.INSTANCE;
      } else {
         label31: {
            if (var3 != null) {
               var2 = var3.optString("message");
               if (var2 != null) {
                  CharSequence var9 = var2;
                  CharSequence var6 = var9;
                  if (StringsKt.isBlank(var9)) {
                     var6 = null;
                  }

                  String var10 = (String)var6;
                  var2 = var10;
                  if (var10 != null) {
                     break label31;
                  }
               }
            }

            var2 = "Deleted!";
         }

         var0.toast(var2);
         var0.savedKeys.put(var1.getKind(), "");
         if (Intrinsics.areEqual(var1.getKind(), "public")) {
            var0.mapboxPublic = "";
         } else if (Intrinsics.areEqual(var1.getKind(), "secret")) {
            var0.mapboxSecret = "";
         }

         var0.renderKeys();
         var0.renderNavAfterKeys();
         return Unit.INSTANCE;
      }
   }

   private final String labelFor(JSONObject var1) {
      Iterable var4 = CollectionsKt.listOf(new String[]{"full_address", "place_name", "name", "address"});
      var var7 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var4, 10));
      Iterator var8 = var4.iterator();

      while (true) {
         boolean var3 = var8.hasNext();
         String var5 = "";
         Object var13 = null;
         Iterator var6 = null;
         if (!var3) {
            List var10 = var7;
            var6 = var10.iterator();

            boolean var2;
            do {
               if (!var6.hasNext()) {
                  var11 = var13;
                  break;
               }

               var11 = var6.next();
               if (((String)var11).length() > 0) {
                  var2 = true;
               } else {
                  var2 = false;
               }
            } while (!var2);

            String var12 = (String)var11;
            if (var12 == null) {
               var12 = var5;
            }

            return var12;
         }

         String var9 = (String)var8.next();
         var13 = var6;
         if (var1 != null) {
            var9 = GxPage.str$default(this, var1, var9, null, 2, null);
            var13 = var6;
            if (var9 != null) {
               var13 = StringsKt.trim(var9).toString();
            }
         }

         if (var13 == null) {
            var13 = var5;
         }

         var7.add(var13);
      }
   }

   private final void loadCatalog() {
      this.getJson("/api/maps/catalog", new GxNavigationPage$$ExternalSyntheticLambda44(this));
   }

   private static final Unit loadCatalog$lambda$111(GxNavigationPage var0, String var1, JSONObject var2) {
      var0.mapsLoadingCatalog = false;
      if (var1 == null && var2 != null) {
         label75: {
            JSONArray var5 = var2.optJSONArray("sections");
            if (var5 != null) {
               List var3 = var0.objects(var5);
               var6 = var3;
               if (var3 != null) {
                  break label75;
               }
            }

            var6 = CollectionsKt.emptyList();
         }

         label70: {
            var0.mapsSections = var6;
            JSONArray var7 = var2.optJSONArray("scheduleOptions");
            if (var7 != null) {
               List var8 = var0.objects(var7);
               if (var8 != null) {
                  Iterable var16 = var8;
                  var var10 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var16, 10));

                  for (JSONObject var17 : var16) {
                     var10.add(TuplesKt.to(var17.opt("value").toString(), GxPage.str$default(var0, var17, "label", null, 2, null)));
                  }

                  var9 = var10;
                  break label70;
               }
            }

            var9 = CollectionsKt.emptyList();
         }

         var0.scheduleOptions = var9;
         var0.tokenLabels.clear();
         Iterator var22 = var0.mapsSections.iterator();

         while (var22.hasNext()) {
            label41: {
               JSONArray var11 = ((JSONObject)var22.next()).optJSONArray("groups");
               if (var11 != null) {
                  List var18 = var0.objects(var11);
                  var12 = var18;
                  if (var18 != null) {
                     break label41;
                  }
               }

               var12 = CollectionsKt.emptyList();
            }

            Iterator var4 = var12.iterator();

            while (var4.hasNext()) {
               label49: {
                  JSONArray var13 = ((JSONObject)var4.next()).optJSONArray("regions");
                  if (var13 != null) {
                     List var19 = var0.objects(var13);
                     var14 = var19;
                     if (var19 != null) {
                        break label49;
                     }
                  }

                  var14 = CollectionsKt.emptyList();
               }

               for (JSONObject var15 : var14) {
                  var0.tokenLabels.put(GxPage.str$default(var0, var15, "token", null, 2, null), GxPage.str$default(var0, var15, "label", null, 2, null));
               }
            }
         }
      } else {
         if (var1 == null) {
            var1 = "Failed to load the map catalog.";
         }

         var0.mapsError = var1;
      }

      var0.buildSchedule();
      var0.renderCatalog();
      var0.paintMaps();
      return Unit.INSTANCE;
   }

   private final void loadKeys() {
      if (this.keysBox != null) {
         this.getJson("/api/navigation", new GxNavigationPage$$ExternalSyntheticLambda8(this));
      }
   }

   private static final Unit loadKeys$lambda$150(GxNavigationPage var0, String var1, JSONObject var2) {
      if (var1 == null && var2 != null) {
         var0.keysError = "";

         for (GxNavigationPage.KeyMeta var4 : var0.keyMeta) {
            var0.savedKeys.put(var4.getKind(), GxPage.str$default(var0, var2, var4.getNavField(), null, 2, null));
         }

         var0.mapboxPublic = StringsKt.trim(GxPage.str$default(var0, var2, "mapboxPublic", null, 2, null)).toString();
         var0.mapboxSecret = StringsKt.trim(GxPage.str$default(var0, var2, "mapboxSecret", null, 2, null)).toString();
      } else {
         if (var1 == null) {
            var1 = "Failed to load keys...";
         }

         var0.keysError = var1;
      }

      var0.getJson("/api/galaxy/session", new GxNavigationPage$$ExternalSyntheticLambda2(var0));
      return Unit.INSTANCE;
   }

   private static final Unit loadKeys$lambda$150$lambda$149(GxNavigationPage var0, String var1, JSONObject var2) {
      if (var1 == null && var2 != null) {
         var1 = GxPage.str$default(var0, var2, "appUrl", null, 2, null);
         int var3 = var1.length();
         boolean var4 = true;
         boolean var7;
         if (var3 == 0) {
            var7 = true;
         } else {
            var7 = false;
         }

         if (var7) {
            var1 = var0.appUrl;
         }

         var0.appUrl = var1;
         var1 = GxPage.str$default(var0, var2, "cookieName", null, 2, null);
         if (var1.length() == 0) {
            var7 = var4;
         } else {
            var7 = false;
         }

         if (var7) {
            var1 = "galaxy_session";
         }

         var0.cookieName = var1;
         var0.paired = var2.optBoolean("paired");
         var0.galaxyToken = GxPage.str$default(var0, var2, "sessionToken", null, 2, null);
         var0.tokenVisible = false;
      } else {
         if (var1 == null) {
            var1 = "Failed to load Galaxy session...";
         }

         var0.toast(var1);
      }

      var0.keysLoading = false;
      var0.renderKeys();
      return Unit.INSTANCE;
   }

   private final void loadNav() {
      if (this.keyBox != null) {
         this.getJson("/api/navigation", new GxNavigationPage$$ExternalSyntheticLambda9(this));
      }
   }

   private static final Unit loadNav$lambda$32(GxNavigationPage var0, String var1, JSONObject var2) {
      if (var1 == null && var2 != null) {
         var0.getJson("/api/navigation/favorite", new GxNavigationPage$$ExternalSyntheticLambda30(var0, var2));
         return Unit.INSTANCE;
      } else {
         if (var1 == null) {
            var1 = "Failed to load navigation.";
         }

         var0.navError = var1;
         var0.navLoading = false;
         var0.renderDestAll();
         return Unit.INSTANCE;
      }
   }

   private static final Unit loadNav$lambda$32$lambda$31(GxNavigationPage var0, JSONObject var1, String var2, JSONObject var3) {
      label54: {
         var0.mapboxPublic = StringsKt.trim(GxPage.str$default(var0, var1, "mapboxPublic", null, 2, null)).toString();
         var0.mapboxSecret = StringsKt.trim(GxPage.str$default(var0, var1, "mapboxSecret", null, 2, null)).toString();
         var0.language = StringsKt.trim(GxPage.str$default(var0, var1, "language", null, 2, null)).toString();
         var0.isMetric = var1.optBoolean("isMetric");
         var0.lastPosition = var0.coordsOf(var1.optJSONObject("lastPosition"));
         if (var3 != null) {
            JSONArray var11 = var3.optJSONArray("favorites");
            if (var11 != null) {
               List var15 = var0.objects(var11);
               var12 = var15;
               if (var15 != null) {
                  break label54;
               }
            }
         }

         var12 = CollectionsKt.emptyList();
      }

      var0.favorites = var12;
      var0.recentDestinations = var0.parseArr(var1.opt("previousDestinations"));
      JSONObject var5 = var0.parseObj(var1.opt("destination"));
      Pair var16 = var0.coordsOf(var5);
      String var7 = "main";
      if (var16 != null && var5 != null) {
         String var6 = var0.primaryOf(var5);
         var2 = var0.str(var5, "routeId", "main");
         if (new Regex("^(?:main|alt-[1-9]\\d*)$").matches(var2)) {
            var7 = var2;
         }

         var0.selectedRouteId = var7;
         JSONObject var14 = new JSONObject(var5.toString())
            .put("latitude", ((Number)var16.getFirst()).doubleValue())
            .put("longitude", ((Number)var16.getSecond()).doubleValue());
         Object var8 = var6;
         boolean var4;
         if (var8.length() == 0) {
            var4 = true;
         } else {
            var4 = false;
         }

         if (var4) {
            var8 = var0.labelFor(var5);
            if (var8.length() == 0) {
               var4 = true;
            } else {
               var4 = false;
            }

            if (var4) {
               var8 = "Current destination";
            }

            var8 = (String)var8;
         }

         var14.put("name", var8);
         if (!var0.navigationStarted || var0.destination == null || !var0.sameSpot(var0.coordsOf(var0.destination), var16)) {
            var0.destination = var14;
            var0.navigationStarted = true;
            var8 = var0;
            Intrinsics.checkNotNull(var14);
            var0.setQuery(GxPage.str$default((GxPage)var8, var14, "name", null, 2, null));
            var0.suggestions = CollectionsKt.emptyList();
            var0.previewDestination(var14, null);
         }
      } else if (var0.navigationStarted) {
         var0.navigationStarted = false;
         var0.destination = null;
         var0.routes = CollectionsKt.emptyList();
         var0.routeSummary = null;
         var0.selectedRouteId = "main";
      }

      var0.navError = "";
      var0.navLoading = false;
      var0.renderDestAll();
      return Unit.INSTANCE;
   }

   private final void loadSpeed() {
      if (this.sStatus != null) {
         this.getJson("/api/speed_limits/status", new GxNavigationPage$$ExternalSyntheticLambda73(this));
      }
   }

   private static final Unit loadSpeed$lambda$172(GxNavigationPage var0, String var1, JSONObject var2) {
      boolean var3 = false;
      var0.speedLoading = false;
      if (var1 == null && var2 != null) {
         var0.canProcessNow = var2.optBoolean("canProcessNow");
         var0.speedProcessing = var2.optBoolean("processing");
         var0.speedReason = GxPage.str$default(var0, var2, "reason", null, 2, null);
         var1 = GxPage.str$default(var0, var2, "status", null, 2, null);
         if (var1.length() == 0) {
            var3 = true;
         }

         if (var3) {
            var1 = "Idle";
         }

         var0.speedStatus = var1;
         var0.speedObj = var2;
      } else {
         var0.speedStatus = "Unavailable";
         var0.speedObj = new JSONObject().put("visionStatus", "Unavailable");
         var0.canProcessNow = false;
         var0.speedProcessing = false;
      }

      var0.paintSpeed();
      return Unit.INSTANCE;
   }

   private final void mapsOp(String var1, JSONObject var2, String var3) {
      this.mapsActionBusy = true;
      this.paintMaps();
      GxPage.send$default(this, "POST", "/api/maps/" + var1, var2, 0L, new GxNavigationPage$$ExternalSyntheticLambda5(this, var3), 8, null);
   }

   private static final Unit mapsOp$lambda$145(GxNavigationPage var0, String var1, String var2, JSONObject var3) {
      var0.mapsActionBusy = false;
      String var9;
      if (var2 == null) {
         Object var5 = null;
         Object var4 = null;
         var2 = (String)var5;
         if (var3 != null) {
            String var8 = var3.optString("message");
            var2 = (String)var5;
            if (var8 != null) {
               var2 = var8;
               if (StringsKt.isBlank(var2)) {
                  var2 = (String)var4;
               }

               var2 = var2;
            }
         }

         var9 = var2;
         if (var2 == null) {
            var9 = var1;
         }
      } else {
         var9 = var2;
      }

      var0.toast(var9);
      var0.paintMaps();
      var0.refreshMapsStatus();
      return Unit.INSTANCE;
   }

   private final String maskKey(String var1) {
      boolean var2;
      if (var1.length() == 0) {
         var2 = 1;
      } else {
         var2 = 0;
      }

      String var5 = "";
      if (var2) {
         return "";
      } else {
         Iterator var6 = CollectionsKt.listOf(new String[]{"pk.", "sk."}).iterator();

         Object var4;
         do {
            boolean var3 = var6.hasNext();
            var4 = null;
            if (!var3) {
               break;
            }

            var4 = (String)var6.next();
         } while (!StringsKt.startsWith$default(var1, var4, false, 2, null));

         String var10 = var4;
         var4 = var10;
         if (var10 == null) {
            var4 = "";
         }

         var2 = var1.length() - var4.length();
         String var11 = StringsKt.repeat("x", Math.min(var2, 24));
         var1 = var5;
         if (var2 > 24) {
            var1 = "… (" + var2 + " characters)";
         }

         return var4 + var11 + var1;
      }
   }

   private final TextView noteIn(String var1, LinearLayout var2) {
      TextView var3 = GxPage.text$default(this, var1, (ViewGroup)var2, 15.0F, CarUi.Companion.getWARN(), false, 16, null);
      var3.setVisibility(8);
      return var3;
   }

   private final Double numOf(Object var1) {
      boolean var6 = var1 instanceof Number;
      boolean var5 = true;
      boolean var4 = true;
      Object var7 = null;
      if (var6) {
         Double var8 = var1.doubleValue();
         double var2 = var8.doubleValue();
         if (Double.isInfinite(var2) || Double.isNaN(var2)) {
            var4 = false;
         }

         var1 = (Double)var7;
         if (var4) {
            var1 = var8;
         }
      } else if (var1 instanceof String) {
         Double var12 = StringsKt.toDoubleOrNull(StringsKt.trim((String & CharSequence)var1).toString());
         var1 = (Double)var7;
         if (var12 != null) {
            double var10 = var12.doubleValue();
            if (!Double.isInfinite(var10) && !Double.isNaN(var10)) {
               var4 = var5;
            } else {
               var4 = false;
            }

            var1 = (Double)var7;
            if (var4) {
               var1 = var12;
            }
         }
      } else {
         var1 = (Double)var7;
      }

      return var1;
   }

   private final void onInput() {
      this.destination = null;
      this.routeSummary = null;
      this.routes = CollectionsKt.emptyList();
      this.selectedRouteId = "main";
      this.navigationStarted = false;
      this.searchSeq++;
      this.searching = false;
      this.navError = "";
      this.getMain().removeCallbacks(this.searchRunner);
      if (StringsKt.trim(this.getQuery()).toString().length() >= 3 && this.getHasMapbox()) {
         this.getMain().postDelayed(this.searchRunner, 350L);
      } else {
         this.suggestions = CollectionsKt.emptyList();
      }

      this.renderStatus();
      this.renderSuggestions();
      this.renderDest();
   }

   private final void openUrl(String var1) {
      try {
         Activity var2 = this.getAct();
         Intent var3 = new Intent("android.intent.action.VIEW", Uri.parse(var1));
         var2.startActivity(var3);
      } catch (Exception var4) {
         this.toast("No app can open " + var1);
      }
   }

   private final void paintMaps() {
      if (this.vDownloader != null) {
         TextView var12 = this.mapsErrorNote;
         TextView var11 = var12;
         if (var12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mapsErrorNote");
            var11 = null;
         }

         var11.setText(this.mapsError);
         var12 = this.mapsErrorNote;
         var11 = var12;
         if (var12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mapsErrorNote");
            var11 = null;
         }

         boolean var5;
         if (this.mapsError.length() == 0) {
            var5 = 1;
         } else {
            var5 = 0;
         }

         if (var5) {
            var5 = 8;
         } else {
            var5 = 0;
         }

         var11.setVisibility(var5);
         JSONObject var14 = this.mapsStatus;
         JSONObject var13 = this.mapsProgress;
         var11 = this.vDownloader;
         var12 = var11;
         if (var11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vDownloader");
            var12 = null;
         }

         CharSequence var48;
         if (this.mapsLoadingStatus) {
            var48 = "Checking...";
         } else {
            String var49;
            if (this.isDownloading()) {
               if (var14.optBoolean("cancelling")) {
                  var49 = "Cancelling";
               } else {
                  var49 = "Downloading";
               }
            } else {
               var49 = "Idle";
            }

            var48 = var49;
         }

         var12.setText(var48);
         var12 = this.vSaved;
         var11 = var12;
         if (var12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vSaved");
            var11 = null;
         }

         CharSequence var110;
         if (this.mapsLoadingStatus) {
            var110 = "Checking...";
         } else {
            var110 = String.valueOf(var14.optInt("selectedCount", 0));
         }

         var11.setText(var110);
         Double var51 = var13.optDouble("estimatedDownloadBytes", 0.0);
         double var1 = var51.doubleValue();
         boolean var22;
         if (!Double.isInfinite(var1) && !Double.isNaN(var1)) {
            var22 = 1;
         } else {
            var22 = 0;
         }

         if (!var22) {
            var51 = null;
         }

         if (var51 != null) {
            var1 = var51;
         } else {
            var1 = 0.0;
         }

         var11 = this.vEstimate;
         var12 = var11;
         if (var11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vEstimate");
            var12 = null;
         }

         String var53;
         if (!this.getSelectionDirty() && var1 > 0.0) {
            String var54 = this.gxBytes(var1);
            var53 = "~" + var54 + " additional";
         } else if (!this.selectedDraft.isEmpty()) {
            var53 = "Not yet available";
         } else {
            var53 = "Select regions";
         }

         CharSequence var55 = var53;
         var12.setText(var55);
         var11 = this.vLastUpdate;
         if (var11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vLastUpdate");
            var11 = null;
         }

         var12 = GxPage.str$default(this, var14, "lastUpdate", null, 2, null);
         if (var12.length() == 0) {
            var22 = (boolean)1;
         } else {
            var22 = (boolean)0;
         }

         if (var22) {
            var12 = "Never";
         }

         var11.setText((CharSequence)var12);
         var12 = this.vStorage;
         var11 = var12;
         if (var12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vStorage");
            var11 = null;
         }

         String var114;
         if (var14.optBoolean("storageKnown")) {
            var114 = this.gxBytes(var14.optDouble("storageBytes", 0.0));
         } else {
            var114 = "Calculating…";
         }

         var11.setText(var114);
         var12 = this.nOnroad;
         var11 = var12;
         if (var12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("nOnroad");
            var11 = null;
         }

         if (this.isOnroad()) {
            var22 = 0;
         } else {
            var22 = 8;
         }

         var11.setVisibility(var22);
         var12 = this.nSelDirty;
         var11 = var12;
         if (var12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("nSelDirty");
            var11 = null;
         }

         if (this.getSelectionDirty()) {
            var22 = 0;
         } else {
            var22 = 8;
         }

         var11.setVisibility(var22);
         var12 = this.nSchDirty;
         var11 = var12;
         if (var12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("nSchDirty");
            var11 = null;
         }

         if (this.getScheduleDirty()) {
            var22 = 0;
         } else {
            var22 = 8;
         }

         var11.setVisibility(var22);
         boolean var27;
         if (!this.mapsLoadingStatus && !this.mapsActionBusy && !this.isOnroad() && !this.isDownloading() && !this.selectedDraft.isEmpty()) {
            var27 = 1;
         } else {
            var27 = 0;
         }

         boolean var6;
         if (!this.mapsLoadingStatus && !this.mapsActionBusy && this.isDownloading()) {
            var6 = 1;
         } else {
            var6 = 0;
         }

         boolean var7;
         if (!this.mapsLoadingStatus && !this.mapsActionBusy && !this.isDownloading() && !this.isOnroad() && var14.optBoolean("mapsPresent")) {
            var7 = true;
         } else {
            var7 = false;
         }

         LinearLayout var118 = this.mapsBar;
         LinearLayout var61 = var118;
         if (var118 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mapsBar");
            var61 = null;
         }

         View var62 = var61.getChildAt(0);
         if (var62 instanceof TextView) {
            var12 = (TextView)var62;
         } else {
            var12 = null;
         }

         if (var12 != null) {
            String var63;
            if (this.isDownloading()) {
               if (var14.optBoolean("cancelling")) {
                  var63 = "Cancelling...";
               } else {
                  var63 = "Cancel Download";
               }
            } else {
               var63 = "Download Maps";
            }

            var12.setText(var63);
            boolean var8;
            if (!var27 && !var6) {
               var8 = false;
            } else {
               var8 = true;
            }

            this.setOn(var12, var8);
            Unit var64 = Unit.INSTANCE;
            Unit var65 = Unit.INSTANCE;
         }

         LinearLayout var120 = this.mapsBar;
         LinearLayout var66 = var120;
         if (var120 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mapsBar");
            var66 = null;
         }

         View var67 = var66.getChildAt(1);
         if (var67 instanceof TextView) {
            var11 = (TextView)var67;
         } else {
            var11 = null;
         }

         this.setOn(var11, var7);
         LinearLayout var121 = this.mapsBar;
         LinearLayout var69 = var121;
         if (var121 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mapsBar");
            var69 = null;
         }

         View var70 = var69.getChildAt(2);
         if (var70 instanceof TextView) {
            var11 = (TextView)var70;
         } else {
            var11 = null;
         }

         if (var11 != null) {
            String var122;
            if (this.mapsSavingSelection) {
               var122 = "Saving...";
            } else {
               var122 = "Save Selection";
            }

            var11.setText(var122);
            if (!this.mapsSavingSelection && !this.mapsLoadingCatalog && this.getSelectionDirty()) {
               var7 = true;
            } else {
               var7 = false;
            }

            this.setOn(var11, var7);
            Unit var72 = Unit.INSTANCE;
            Unit var73 = Unit.INSTANCE;
         }

         LinearLayout var123 = this.mapsBar;
         LinearLayout var74 = var123;
         if (var123 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mapsBar");
            var74 = null;
         }

         View var75 = var74.getChildAt(3);
         if (var75 instanceof TextView) {
            var11 = (TextView)var75;
         } else {
            var11 = null;
         }

         if (!this.getSelectionDirty() && !this.getScheduleDirty()) {
            var7 = false;
         } else {
            var7 = true;
         }

         this.setOn(var11, var7);
         LinearLayout var124 = this.mapsBar;
         LinearLayout var77 = var124;
         if (var124 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mapsBar");
            var77 = null;
         }

         View var78 = var77.getChildAt(4);
         if (var78 instanceof TextView) {
            var11 = (TextView)var78;
         } else {
            var11 = null;
         }

         this.setOn(var11, this.selectedDraft.isEmpty() ^ true);
         boolean var45 = var13.optBoolean("completed");
         var7 = var13.optBoolean("cancelled");
         if (this.mapsLoadingStatus || !this.isDownloading() && !var45 && !var7 && !(var1 > 0.0)) {
            var27 = (boolean)0;
         } else {
            var27 = (boolean)1;
         }

         LinearLayout var125 = this.progCard;
         LinearLayout var80 = var125;
         if (var125 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("progCard");
            var80 = null;
         }

         if (var27) {
            var6 = (byte)0;
         } else {
            var6 = (byte)8;
         }

         var80.setVisibility(var6);
         if (var27) {
            Double var81 = var13.optDouble("percent", 0.0);
            double var3 = var81.doubleValue();
            if (!Double.isInfinite(var3) && !Double.isNaN(var3)) {
               var27 = (boolean)1;
            } else {
               var27 = (boolean)0;
            }

            if (!var27) {
               var81 = null;
            }

            if (var81 != null) {
               var3 = var81;
            } else {
               var3 = 0.0;
            }

            var11 = this.progTitle;
            var12 = var11;
            if (var11 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("progTitle");
               var12 = null;
            }

            String var83;
            if (this.isDownloading()) {
               var83 = "Download Progress";
            } else if (var45) {
               var83 = "Last Download";
            } else if (var7) {
               var83 = "Download Cancelled";
            } else {
               var83 = "Download Estimate";
            }

            long var9 = Math.round(var3);
            var12.setText(var83 + "  ·  " + var9 + "%");
            ProgressBar var127 = this.progBar;
            ProgressBar var84 = var127;
            if (var127 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("progBar");
               var84 = null;
            }

            this.setProgress(var84, var3);
            var12 = this.pEstimate;
            var11 = var12;
            if (var12 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("pEstimate");
               var11 = null;
            }

            String var130;
            if (var1 > 0.0) {
               String var129 = this.gxBytes(var1);
               var130 = "~" + var129 + " additional storage";
            } else {
               var130 = "Storage estimate unavailable";
            }

            var11.setText("Estimate: " + var130);
            var1 = var13.optDouble("downloadedBytes", 0.0);
            var12 = this.pStored;
            var11 = var12;
            if (var12 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("pStored");
               var11 = null;
            }

            String var133;
            if (var1 > 0.0) {
               String var132 = this.gxBytes(var1);
               var133 = var132 + " added storage";
            } else {
               var133 = "Storage reconciles after completion";
            }

            var11.setText("Stored: " + var133);
            var6 = var13.optInt("totalFiles", 0);
            var12 = this.pFiles;
            var11 = var12;
            if (var12 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("pFiles");
               var11 = null;
            }

            String var135;
            if (var6 > 0) {
               var27 = var13.optInt("downloadedFiles", 0);
               var135 = var27 + " / " + var6 + " files";
            } else {
               var135 = "Waiting for map service...";
            }

            var11.setText("Files: " + var135);
            var1 = var13.optDouble("etaSeconds", 0.0);
            var12 = this.pEta;
            var11 = var12;
            if (var12 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("pEta");
               var11 = null;
            }

            String var137;
            if (this.isDownloading() && var1 > 0.0) {
               String var138 = this.gxDuration(var1);
               var137 = "About " + var138 + " remaining";
            } else {
               var137 = "ETA unavailable until files start arriving";
            }

            var11.setText("ETA: " + var137);
            String var156 = GxPage.str$default(this, var13, "primaryLocation", null, 2, null);
            var12 = this.pRegion;
            var11 = var12;
            if (var12 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("pRegion");
               var11 = null;
            }

            var11.setText("Current region: " + var156);
            var12 = this.pRegion;
            var11 = var12;
            if (var12 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("pRegion");
               var11 = null;
            }

            boolean var31;
            if (var156.length() == 0) {
               var31 = 1;
            } else {
               var31 = 0;
            }

            if (var31) {
               var31 = 8;
            } else {
               var31 = 0;
            }

            var11.setVisibility(var31);
         }

         Function1 var91 = this.paintSchedule;
         if (var91 != null) {
            List var141 = this.scheduleOptions;
            var27 = 0;
            Iterator var142 = var141.iterator();

            while (true) {
               if (!var142.hasNext()) {
                  var27 = -1;
                  break;
               }

               if (Intrinsics.areEqual(((Pair)var142.next()).getFirst(), this.scheduleDraft)) {
                  break;
               }

               var27++;
            }

            var91.invoke(var27);
            Unit var92 = Unit.INSTANCE;
         }

         LinearLayout var143 = this.scheduleBox;
         LinearLayout var93 = var143;
         if (var143 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scheduleBox");
            var93 = null;
         }

         View var94 = var93.getChildAt(1);
         HorizontalScrollView var95;
         if (var94 instanceof HorizontalScrollView) {
            var95 = (HorizontalScrollView)var94;
         } else {
            var95 = null;
         }

         if (var95 != null) {
            View var96 = var95.getChildAt(0);
            LinearLayout var97;
            if (var96 instanceof LinearLayout) {
               var97 = (LinearLayout)var96;
            } else {
               var97 = null;
            }

            View var98;
            if (var97 != null) {
               var98 = var97.getChildAt(0);
            } else {
               var98 = null;
            }

            if (var98 instanceof TextView) {
               var11 = (TextView)var98;
            } else {
               var11 = null;
            }

            if (var11 != null) {
               String var144;
               if (this.mapsSavingSchedule) {
                  var144 = "Applying...";
               } else {
                  var144 = "Apply";
               }

               var11.setText(var144);
               if (!this.mapsSavingSchedule && this.getScheduleDirty()) {
                  var7 = true;
               } else {
                  var7 = false;
               }

               this.setOn(var11, var7);
               Unit var100 = Unit.INSTANCE;
               Unit var101 = Unit.INSTANCE;
            }
         }

         var12 = this.selRegionsText;
         var11 = var12;
         if (var12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("selRegionsText");
            var11 = null;
         }

         CharSequence var146;
         if (this.selectedDraft.isEmpty()) {
            var146 = "No regions selected.";
         } else {
            Iterable var147 = this.selectedDraft;
            var var15 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var147, 10));

            for (String var157 : var147) {
               String var148 = this.tokenLabels.get(var157);
               if (var148 == null) {
                  var148 = var157;
               }

               var15.add(var148);
            }

            List var149 = var15;
            Iterable var150 = var149;
            var146 = CollectionsKt.joinToString$default(CollectionsKt.sortedWith(var150, new Comparator() {
               @Override
               public final int compare(T var1, T var2) {
                  var1 = var1.toLowerCase(Locale.ROOT);
                  Intrinsics.checkNotNullExpressionValue(var1, "toLowerCase(...)");
                  Comparable var4 = var1;
                  var2 = var2.toLowerCase(Locale.ROOT);
                  Intrinsics.checkNotNullExpressionValue(var2, "toLowerCase(...)");
                  return ComparisonsKt.compareValues(var4, var2);
               }
            }), ", ", null, null, 0, null, null, 62, null);
         }

         var11.setText(var146);
         var12 = this.selRegionsText;
         var11 = var12;
         if (var12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("selRegionsText");
            var11 = null;
         }

         if (this.selectedDraft.isEmpty()) {
            var27 = CarUi.Companion.getDIM();
         } else {
            var27 = CarUi.Companion.getTEXT();
         }

         var11.setTextColor(var27);
         var12 = this.schedNote;
         var11 = var12;
         if (var12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("schedNote");
            var11 = null;
         }

         var12 = GxPage.str$default(this, var14, "scheduleLabel", null, 2, null);
         boolean var35;
         if (var12.length() == 0) {
            var35 = 1;
         } else {
            var35 = 0;
         }

         if (var35) {
            var12 = "Monthly";
         }

         var35 = this.selectedDraft.size();
         var11.setText("Saved schedule: " + var12 + " · Draft regions: " + var35);

         for (Entry var158 : this.regionSwitches.entrySet()) {
            String var154 = (String)var158.getKey();
            Switch var159 = (Switch)var158.getValue();
            var7 = this.selectedDraft.contains(var154);
            if (var159.isChecked() != var7) {
               var159.setChecked(var7);
            }
         }

         Iterator var160 = this.groupRows.entrySet().iterator();

         while (var160.hasNext()) {
            Pair var106 = (Pair)((Entry)var160.next()).getValue();
            GxPage.Row var155 = (GxPage.Row)var106.getFirst();
            var35 = this.groupSelectedCount((JSONObject)var106.getSecond());
            var155.setDesc(var35 + " selected");
         }
      }
   }

   private final void paintSpeed() {
      JSONObject var14 = this.speedObj;
      boolean var7 = var14.optBoolean("visionEnabled");
      TextView var11 = this.sStatus;
      TextView var10 = var11;
      if (var11 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sStatus");
         var10 = null;
      }

      CharSequence var45;
      if (this.speedLoading) {
         var45 = "Checking...";
      } else {
         var45 = this.speedStatus;
      }

      var10.setText(var45);
      var11 = this.sVision;
      if (var11 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sVision");
         var11 = null;
      }

      CharSequence var30;
      if (this.speedLoading) {
         var30 = "Checking...";
      } else {
         var var31 = GxPage.str$default(this, var14, "visionStatus", null, 2, null);
         boolean var3;
         if (var31.length() == 0) {
            var3 = true;
         } else {
            var3 = false;
         }

         if (var3) {
            if (var7) {
               var31 = "Idle";
            } else {
               var31 = "Disabled";
            }
         }

         String var12 = var31;
         String var13 = GxPage.str$default(this, var14, "visionStream", null, 2, null);
         if (var13.length() > 0) {
            var3 = true;
         } else {
            var3 = false;
         }

         String var32 = var12;
         if (var3) {
            var32 = var12 + " on " + var13;
         }

         Double var56 = var14.optDouble("visionDisplaySpeed", 0.0);
         double var1 = var56.doubleValue();
         if (!Double.isInfinite(var1) && !Double.isNaN(var1)) {
            var3 = true;
         } else {
            var3 = false;
         }

         if (!var3) {
            var56 = null;
         }

         if (var56 != null) {
            var1 = var56;
         } else {
            var1 = 0.0;
         }

         if (var1 > 0.0) {
            if (var1 % 1.0 == 0.0) {
               var3 = true;
            } else {
               var3 = false;
            }

            if (var3) {
               var12 = String.valueOf((long)var1);
            } else {
               var12 = String.valueOf(var1);
            }

            var13 = GxPage.str$default(this, var14, "visionSpeedUnit", null, 2, null);
            if (var13.length() == 0) {
               var3 = true;
            } else {
               var3 = false;
            }

            if (var3) {
               var13 = "mph";
            }

            var12 = var32 + " (" + var12 + " " + var13;
            Double var33 = var14.optDouble("visionConfidence", 0.0);
            var1 = var33.doubleValue();
            if (!Double.isInfinite(var1) && !Double.isNaN(var1)) {
               var3 = true;
            } else {
               var3 = false;
            }

            if (!var3) {
               var33 = null;
            }

            if (var33 != null) {
               var1 = var33;
            } else {
               var1 = 0.0;
            }

            String var34 = var12;
            if (var1 > 0.0) {
               long var8 = Math.round(100 * var1);
               var34 = var12 + ", " + var8 + "%";
            }

            var32 = var34 + ")";
         }

         var30 = var32;
      }

      var11.setText(var30);
      var11 = this.sVision;
      var10 = var11;
      if (var11 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sVision");
         var10 = null;
      }

      var10.setMaxWidth(this.getUi().dp(520));
      GxPage.Row var48 = this.sDebugRow;
      GxPage.Row var36 = var48;
      if (var48 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sDebugRow");
         var36 = null;
      }

      boolean var6;
      if (!this.speedLoading && var7) {
         var6 = true;
      } else {
         var6 = false;
      }

      var36.setVisible(var6);
      int var4 = var14.optInt("visionBookmarkCount", 0);
      var10 = this.sDebug;
      if (var10 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sDebug");
         var10 = null;
      }

      var11 = GxPage.str$default(this, var14, "visionDebugSession", null, 2, null);
      boolean var23;
      if (var11.length() == 0) {
         var23 = 1;
      } else {
         var23 = 0;
      }

      if (var23) {
         var11 = "No active session";
      }

      String var62 = "";
      Object var59 = var62;
      if (var4 > 0) {
         if (var4 != 1) {
            var62 = "s";
         }

         var59 = ", " + var4 + " bookmark" + var62;
      }

      var10.setText(var11 + var59);
      var59 = GxPage.str$default(this, var14, "visionLastEvent", null, 2, null);
      var11 = this.sLastEvent;
      var10 = var11;
      if (var11 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sLastEvent");
         var10 = null;
      }

      var10.setText((CharSequence)var59);
      var11 = this.sLastEvent;
      var10 = var11;
      if (var11 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sLastEvent");
         var10 = null;
      }

      byte var5;
      label183: {
         var6 = this.speedLoading;
         var5 = 8;
         if (!var6 && var7) {
            boolean var24;
            if (((CharSequence)var59).length() > 0) {
               var24 = true;
            } else {
               var24 = false;
            }

            if (var24) {
               var23 = 0;
               break label183;
            }
         }

         var23 = 8;
      }

      var10.setVisibility(var23);
      var11 = this.sReason;
      var10 = var11;
      if (var11 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sReason");
         var10 = null;
      }

      var10.setText(this.speedReason);
      var11 = this.sReason;
      var10 = var11;
      if (var11 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sReason");
         var10 = null;
      }

      byte var27 = var5;
      if (!this.speedLoading) {
         boolean var26;
         if (this.speedReason.length() > 0) {
            var26 = true;
         } else {
            var26 = false;
         }

         var27 = var5;
         if (var26) {
            var27 = var5;
            if (!Intrinsics.areEqual(this.speedReason, this.speedStatus)) {
               var27 = 0;
            }
         }
      }

      var10.setVisibility(var27);
      LinearLayout var54 = this.sBar;
      LinearLayout var42 = var54;
      if (var54 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sBar");
         var42 = null;
      }

      View var43 = var42.getChildAt(1);
      if (var43 instanceof TextView) {
         var10 = (TextView)var43;
      } else {
         var10 = null;
      }

      if (var10 != null) {
         String var55;
         if (!this.speedProcessing && !this.speedSubmitting) {
            var55 = "Process Now";
         } else {
            var55 = "Processing...";
         }

         var10.setText(var55);
         if (!this.speedSubmitting && !this.speedProcessing && this.canProcessNow) {
            var6 = true;
         } else {
            var6 = false;
         }

         this.setOn(var10, var6);
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private final List<JSONObject> parseArr(Object var1) {
      if (var1 instanceof JSONArray) {
         var1 = this.objects((JSONArray)var1);
      } else if (var1 instanceof String) {
         label32:
         try {
            Result.Companion var2 = Result.Companion;
            GxNavigationPage var3 = this;
            JSONArray var9 = new JSONArray((String)var1);
            var1 = KtCompat.resultOf(var3.objects(var9));
         } catch (Throwable var5) {
            var1 = Result.Companion;
            var1 = KtCompat.resultOf(ResultKt.createFailure(var5));
            break label32;
         }

         List var11 = CollectionsKt.emptyList();
         Object var10 = var1;
         if (KtCompat.isFailure(var1)) {
            var10 = var11;
         }

         var1 = (List)var10;
      } else {
         var1 = CollectionsKt.emptyList();
      }

      return var1;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private final JSONObject parseObj(Object var1) {
      if (var1 instanceof JSONObject) {
         var1 = (JSONObject)var1;
      } else {
         boolean var2 = var1 instanceof String;
         Object var3 = null;
         if (var2) {
            if (StringsKt.isBlank((CharSequence)var1)) {
               var1 = null;
            } else {
               label35:
               try {
                  Result.Companion var9 = Result.Companion;
                  GxNavigationPage var10 = this;
                  JSONObject var11 = new JSONObject((String)var1);
                  var1 = KtCompat.resultOf(var11);
               } catch (Throwable var6) {
                  Result.Companion var4 = Result.Companion;
                  var1 = KtCompat.resultOf(ResultKt.createFailure(var6));
                  break label35;
               }

               if (KtCompat.isFailure(var1)) {
                  var1 = var3;
               }

               var1 = (JSONObject)var1;
            }
         } else {
            var1 = null;
         }
      }

      return var1;
   }

   private final void previewDestination(JSONObject var1, String var2) {
      Pair var6 = this.lastPosition;
      Pair var4 = this.coordsOf(var1);
      boolean var3 = true;
      if (var6 != null && var4 != null && this.getHasMapbox()) {
         Object var5 = var6.getSecond();
         Object var7 = var6.getFirst();
         Object var9 = var4.getSecond();
         Object var12 = var4.getFirst();
         String var8 = this.qs(
            TuplesKt.to("geometries", "geojson"),
            TuplesKt.to("overview", "false"),
            TuplesKt.to("alternatives", "true"),
            TuplesKt.to("access_token", this.mapboxPublic)
         );
         var5 = "https://api.mapbox.com/directions/v5/mapbox/driving-traffic/" + var5 + "," + var7 + ";" + var9 + "," + var12 + "?" + var8;
         this.extGet((String)var5, new GxNavigationPage$$ExternalSyntheticLambda46(this, var1, var2, var4));
      } else {
         this.routeSummary = null;
         this.routes = CollectionsKt.emptyList();
         var var10 = this.selectedRouteId;
         if (var10.length() != 0) {
            var3 = false;
         }

         if (var3) {
            var10 = "main";
         }

         this.selectedRouteId = var10;
         this.renderDest();
      }
   }

   private static final Unit previewDestination$lambda$71(GxNavigationPage var0, JSONObject var1, String var2, Pair var3, String var4, JSONObject var5) {
      JSONObject var18 = var0.destination;
      boolean var8 = true;
      byte var7 = 0;
      if (var18 != var1) {
         JSONObject var19 = var0.destination;
         boolean var6;
         if (var19 != null && var0.sameSpot(var0.coordsOf(var19), var3)) {
            var6 = true;
         } else {
            var6 = false;
         }

         if (!var6) {
            return Unit.INSTANCE;
         }
      }

      label65: {
         if (var5 != null) {
            JSONArray var16 = var5.optJSONArray("routes");
            if (var16 != null) {
               List var20 = var0.objects(var16);
               var17 = var20;
               if (var20 != null) {
                  break label65;
               }
            }
         }

         var17 = CollectionsKt.emptyList();
      }

      boolean var9 = var17.isEmpty();
      String var22 = "main";
      Object var21 = null;
      if (!var9) {
         String var12;
         if (var2 == null) {
            var var10 = GxPage.str$default(var0, var1, "routeId", null, 2, null);
            boolean var24;
            if (var10.length() == 0) {
               var24 = true;
            } else {
               var24 = false;
            }

            if (var24) {
               var var11 = var0.selectedRouteId;
               if (var11.length() == 0) {
                  var24 = var8;
               } else {
                  var24 = false;
               }

               if (var24) {
                  var11 = var22;
               }

               var10 = var11;
            }

            var12 = var10;
         } else {
            var12 = var2;
         }

         Iterator var23 = CollectionsKt.getIndices(var17).iterator();

         while (true) {
            if (!var23.hasNext()) {
               var13 = var21;
               break;
            }

            Object var15 = var23.next();
            if (Intrinsics.areEqual(var0.routeIdOf(((Number)var15).intValue()), var12)) {
               var13 = var15;
               break;
            }
         }

         Integer var14 = (Integer)var13;
         int var26 = var7;
         if (var14 != null) {
            var26 = var14;
         }

         var0.routes = var17;
         var0.selectRoute(var26);
      } else {
         var0.routeSummary = null;
         var0.routes = CollectionsKt.emptyList();
         var0.selectedRouteId = "main";
      }

      var0.renderDest();
      return Unit.INSTANCE;
   }

   private final String primaryOf(JSONObject var1) {
      String var9;
      label33: {
         Object var4 = null;
         if (var1 != null) {
            var9 = GxPage.str$default(this, var1, "name", null, 2, null);
            if (var9 != null) {
               var9 = StringsKt.trim(var9).toString();
               if (var9 != null) {
                  var9 = var9;
                  boolean var2;
                  if (var9.length() == 0) {
                     var2 = true;
                  } else {
                     var2 = false;
                  }

                  if (var2) {
                     var9 = null;
                  }

                  var9 = var9;
                  if (var9 != null) {
                     break label33;
                  }
               }
            }
         }

         var9 = (String)var4;
         if (var1 != null) {
            String var5 = GxPage.str$default(this, var1, "text", null, 2, null);
            var9 = (String)var4;
            if (var5 != null) {
               var9 = StringsKt.trim(var5).toString();
            }
         }
      }

      String var6 = var9;
      if (var9 == null) {
         var6 = "";
      }

      return var6;
   }

   private final void processSpeed() {
      if (!this.speedSubmitting && !this.speedProcessing && this.canProcessNow) {
         this.speedSubmitting = true;
         this.paintSpeed();
         GxPage.send$default(this, "POST", "/api/speed_limits/process", null, 0L, new GxNavigationPage$$ExternalSyntheticLambda6(this), 12, null);
      }
   }

   private static final Unit processSpeed$lambda$180(GxNavigationPage var0, String var1, JSONObject var2) {
      var0.speedSubmitting = false;
      String var8;
      if (var1 == null) {
         Object var4 = null;
         Object var3 = null;
         var1 = (String)var4;
         if (var2 != null) {
            String var7 = var2.optString("message");
            var1 = (String)var4;
            if (var7 != null) {
               var1 = var7;
               if (StringsKt.isBlank(var1)) {
                  var1 = (String)var3;
               }

               var1 = var1;
            }
         }

         var8 = var1;
         if (var1 == null) {
            var8 = "Speed limit processing started.";
         }
      } else {
         var8 = var1;
      }

      var0.toast(var8);
      var0.loadSpeed();
      return Unit.INSTANCE;
   }

   private final String qs(Pair<String, String>... var1) {
      var var6 = new ArrayList();

      for (Pair var5 : var1) {
         CharSequence var7 = (CharSequence)var5.getSecond();
         boolean var3;
         if (var7 != null && var7.length() != 0) {
            var3 = false;
         } else {
            var3 = true;
         }

         if (!var3) {
            var6.add(var5);
         }
      }

      List var8 = var6;
      return CollectionsKt.joinToString$default(var8, "&", null, null, 0, null, new GxNavigationPage$$ExternalSyntheticLambda80(this), 30, null);
   }

   private static final CharSequence qs$lambda$21(GxNavigationPage var0, Pair var1) {
      Intrinsics.checkNotNullParameter(var1, "it");
      String var2 = var0.enc((String)var1.getFirst());
      Object var4 = var1.getSecond();
      Intrinsics.checkNotNull(var4);
      String var3 = var0.enc((String)var4);
      return var2 + "=" + var3;
   }

   private final void refreshMapsStatus() {
      if (this.vDownloader != null && !this.mapsStatusInFlight) {
         this.mapsStatusInFlight = true;
         this.getJson("/api/maps/status", new GxNavigationPage$$ExternalSyntheticLambda10(this));
      }
   }

   private static final Unit refreshMapsStatus$lambda$116(GxNavigationPage var0, String var1, JSONObject var2) {
      var0.mapsStatusInFlight = false;
      var0.mapsLoadingStatus = false;
      if (var1 == null && var2 != null) {
         boolean var3 = var0.getSelectionDirty();
         boolean var4 = var0.getScheduleDirty();
         var0.mapsStatus = var2;
         JSONObject var5 = var2.optJSONObject("downloadProgress");
         JSONObject var6 = var5;
         if (var5 == null) {
            var6 = new JSONObject();
         }

         label36: {
            var0.mapsProgress = var6;
            JSONArray var7 = var2.optJSONArray("selectedLocations");
            if (var7 != null) {
               List var9 = var0.strings(var7);
               var8 = var9;
               if (var9 != null) {
                  break label36;
               }
            }

            var8 = CollectionsKt.emptyList();
         }

         var0.selectedSaved = CollectionsKt.toSet(CollectionsKt.toSortedSet(var8, StringsKt.getCASE_INSENSITIVE_ORDER(StringCompanionObject.INSTANCE)));
         if (!var3) {
            var0.selectedDraft.clear();
            var0.selectedDraft.addAll(var0.selectedSaved);
         }

         if (var2.has("scheduleValue") && !var2.isNull("scheduleValue")) {
            var0.scheduleSaved = var2.opt("scheduleValue").toString();
         }

         if (!var4) {
            var0.scheduleDraft = var0.scheduleSaved;
         }

         var0.mapsError = "";
         var0.paintMaps();
         return Unit.INSTANCE;
      } else {
         if (var1 == null) {
            var1 = "Failed to load map status.";
         }

         var0.mapsError = var1;
         var0.paintMaps();
         return Unit.INSTANCE;
      }
   }

   private final void reloadFavorites() {
      this.getJson("/api/navigation/favorite", new GxNavigationPage$$ExternalSyntheticLambda32(this));
   }

   private static final Unit reloadFavorites$lambda$59(GxNavigationPage var0, String var1, JSONObject var2) {
      label13: {
         if (var2 != null) {
            JSONArray var3 = var2.optJSONArray("favorites");
            if (var3 != null) {
               List var5 = var0.objects(var3);
               var4 = var5;
               if (var5 != null) {
                  break label13;
               }
            }
         }

         var4 = var0.favorites;
      }

      var0.favorites = var4;
      var0.renderDest();
      var0.renderFavorites();
      var0.renderRecents();
      return Unit.INSTANCE;
   }

   private final void removeMaps() {
      if (!this.mapsActionBusy && !this.isDownloading() && !this.isOnroad() && this.mapsStatus.optBoolean("mapsPresent")) {
         this.confirm(
            "Remove Maps", "Are you sure you want to remove all downloaded offline maps?", "Yes, Remove", new GxNavigationPage$$ExternalSyntheticLambda11(this)
         );
      }
   }

   private static final Unit removeMaps$lambda$146(GxNavigationPage var0) {
      var0.mapsOp("remove", new JSONObject(), "Maps removed.");
      return Unit.INSTANCE;
   }

   private final void renameFavorite(JSONObject var1) {
      String var2 = GxPage.str$default(this, var1, "name", null, 2, null);
      GxPage.prompt$default(
         this, "Rename Favorite", "Name", var2, 0, "Rename " + var2 + " to:", new GxNavigationPage$$ExternalSyntheticLambda4(var2, var1, this), 8, null
      );
   }

   private static final Unit renameFavorite$lambda$91(String var0, JSONObject var1, GxNavigationPage var2, String var3) {
      Intrinsics.checkNotNullParameter(var3, "v");
      String var5 = StringsKt.trim(var3).toString();
      boolean var4;
      if (var5.length() == 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      if (!var4 && !Intrinsics.areEqual(var5, var0)) {
         JSONObject var6 = new JSONObject();
         Object var11 = var1.opt("id");
         Object var7 = var11;
         if (var11 == null) {
            var7 = JSONObject.NULL;
         }

         var11 = var6.put("id", var7);
         Object var10 = var1.opt("routeId");
         var7 = var10;
         if (var10 == null) {
            var7 = JSONObject.NULL;
         }

         var7 = var11.put("routeId", var7).put("name", var5);
         GxPage.send$default(
            var2, "POST", "/api/navigation/favorite/rename", (JSONObject)var7, 0L, new GxNavigationPage$$ExternalSyntheticLambda3(var2), 8, null
         );
         return Unit.INSTANCE;
      } else {
         return Unit.INSTANCE;
      }
   }

   private static final Unit renameFavorite$lambda$91$lambda$90(GxNavigationPage var0, String var1, JSONObject var2) {
      if (var1 != null) {
         var0.toast("Failed to rename favorite...");
      } else {
         var0.toast("Favorite renamed!");
         var0.reloadFavorites();
      }

      return Unit.INSTANCE;
   }

   private final void renderCatalog() {
      if (this.catalogBox != null) {
         LinearLayout var6 = this.catalogBox;
         LinearLayout var5 = var6;
         if (var6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("catalogBox");
            var5 = null;
         }

         var5.removeAllViews();
         this.regionSwitches.clear();
         this.groupRows.clear();
         if (this.mapsLoadingCatalog) {
            var5 = this.catalogBox;
            if (var5 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("catalogBox");
               var5 = null;
            }

            var5.addView((View)this.empty("Loading map catalog..."));
         } else {
            String var13 = StringsKt.trim(this.mapsSearch).toString().toLowerCase(Locale.ROOT);
            String var29 = "toLowerCase(...)";
            Intrinsics.checkNotNullExpressionValue(var13, "toLowerCase(...)");
            int var1 = 0;

            for (JSONObject var12 : this.mapsSections) {
               List var34;
               label122: {
                  JSONArray var7 = var12.optJSONArray("groups");
                  if (var7 != null) {
                     List var8 = this.objects(var7);
                     var34 = var8;
                     if (var8 != null) {
                        break label122;
                     }
                  }

                  var34 = CollectionsKt.emptyList();
               }

               Iterable var10 = var34;
               boolean var4 = false;
               var var15 = new ArrayList();
               Iterable var9 = var10;
               boolean var3 = false;

               for (JSONObject var16 : var10) {
                  List var39;
                  label130: {
                     JSONArray var38 = var16.optJSONArray("regions");
                     if (var38 != null) {
                        List var11 = this.objects(var38);
                        var39 = var11;
                        if (var11 != null) {
                           break label130;
                        }
                     }

                     var39 = CollectionsKt.emptyList();
                  }

                  Iterable var58 = var39;
                  var var18 = new ArrayList();
                  Iterator var17 = var58.iterator();
                  String var40 = var29;

                  while (var17.hasNext()) {
                     var9 = (Iterable)var17.next();
                     JSONObject var21 = (JSONObject)var9;
                     boolean var2;
                     if (var13.length() == 0) {
                        var2 = true;
                     } else {
                        var2 = false;
                     }

                     label140: {
                        if (!var2) {
                           String var19 = GxPage.str$default(this, var21, "label", null, 2, null);
                           String var20 = GxPage.str$default(this, var21, "code", null, 2, null);
                           String var80 = GxPage.str$default(this, var21, "token", null, 2, null);
                           var19 = (var19 + " " + var20 + " " + var80).toLowerCase(Locale.ROOT);
                           Intrinsics.checkNotNullExpressionValue(var19, var40);
                           if (!StringsKt.contains$default(var19, var13, false, 2, null)) {
                              var2 = false;
                              break label140;
                           }
                        }

                        var2 = true;
                     }

                     if (var2) {
                        var18.add(var9);
                     }
                  }

                  List var51 = var18;
                  Pair var52;
                  if (var51.isEmpty()) {
                     var52 = null;
                  } else {
                     var52 = TuplesKt.to(var16, var51);
                  }

                  if (var52 != null) {
                     var15.add(var52);
                  }

                  var9 = var9;
                  var29 = var40;
               }

               List var53 = var15;
               if (!var53.isEmpty()) {
                  String var35 = GxPage.str$default(this, var12, "title", null, 2, null);
                  var1 = var53.size();
                  String var55 = var35 + "  (" + var1 + ")";
                  LinearLayout var41 = this.catalogBox;
                  LinearLayout var36 = var41;
                  if (var41 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("catalogBox");
                     var36 = null;
                  }

                  this.header(var55, (ViewGroup)var36);
                  Iterator var70 = var53.iterator();
                  JSONObject var37 = var12;

                  while (var70.hasNext()) {
                     Pair var42 = (Pair)var70.next();
                     JSONObject var54 = (JSONObject)var42.component1();
                     List var73 = (List)var42.component2();
                     String var43 = GxPage.str$default(this, var37, "key", null, 2, null);
                     String var56 = GxPage.str$default(this, var54, "key", null, 2, null);
                     String var57 = var43 + ":" + var56;
                     boolean var23;
                     if (var13.length() > 0) {
                        var23 = true;
                     } else {
                        var23 = false;
                     }

                     if (!var23 && !this.expandedGroups.contains(var57)) {
                        var23 = false;
                     } else {
                        var23 = true;
                     }

                     GxPage var75 = this;
                     String var71 = GxPage.str$default(this, var54, "title", null, 2, null);
                     int var27 = this.groupSelectedCount(var54);
                     String var64 = var27 + " selected";
                     LinearLayout var59 = this.catalogBox;
                     var41 = var59;
                     if (var59 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("catalogBox");
                        var41 = null;
                     }

                     GxPage.Row var60 = GxPage.row$default(var75, var71, var64, (ViewGroup)var41, 0, 8, null);
                     this.groupRows.put(var57, TuplesKt.to(var60, var54));
                     GxNavigationPage$$ExternalSyntheticLambda17 var65 = new GxNavigationPage$$ExternalSyntheticLambda17(this, var57);
                     boolean var28;
                     if (var13.length() == 0) {
                        var28 = true;
                     } else {
                        var28 = false;
                     }

                     if (var28) {
                        this.clickable(var60, new GxNavigationPage$$ExternalSyntheticLambda18(var65));
                        String var45;
                        if (var23) {
                           var45 = "Hide";
                        } else {
                           var45 = "Show";
                        }

                        GxPage.Row.button$default(var60, var45, false, new GxNavigationPage$$ExternalSyntheticLambda19(var65), 2, null);
                     }

                     if (var23) {
                        LinearLayout var72 = new LinearLayout((Context)this.getAct());
                        var72.setOrientation(1);
                        var72.setPadding(this.getUi().dp(28), 0, 0, this.getUi().dp(8));
                        LinearLayout var66 = this.catalogBox;
                        var41 = var66;
                        if (var66 == null) {
                           Intrinsics.throwUninitializedPropertyAccessException("catalogBox");
                           var41 = null;
                        }

                        label174: {
                           var41.addView((View)var72, (android.view.ViewGroup.LayoutParams)(new LayoutParams(-1, -2)));
                           JSONArray var47 = var54.optJSONArray("regions");
                           if (var47 != null) {
                              List var67 = this.objects(var47);
                              var48 = var67;
                              if (var67 != null) {
                                 break label174;
                              }
                           }

                           var48 = CollectionsKt.emptyList();
                        }

                        var75 = this;
                        Pair var68 = TuplesKt.to("Select All", new GxNavigationPage$$ExternalSyntheticLambda20(var48, this));
                        Pair var78 = TuplesKt.to("Clear", new GxNavigationPage$$ExternalSyntheticLambda21(var48, this));
                        ViewGroup var49 = (ViewGroup)var72;
                        GxPage.buttons$default(var75, new Pair[]{var68, var78}, var49, false, 4, null);

                        for (JSONObject var61 : var73) {
                           String var74 = GxPage.str$default(this, var61, "token", null, 2, null);
                           String var77 = GxPage.str$default(this, var61, "label", null, 2, null);
                           var var62 = GxPage.str$default(this, var61, "code", null, 2, null);
                           if (var62.length() == 0) {
                              var23 = true;
                           } else {
                              var23 = false;
                           }

                           if (var23) {
                              var62 = null;
                           }

                           Switch var63 = this.toggle(
                                 var77,
                                 var62,
                                 this.selectedDraft.contains(var74),
                                 (ViewGroup)var72,
                                 new GxNavigationPage$$ExternalSyntheticLambda23(this, var74)
                              )
                              .component2();
                           this.regionSwitches.put(var74, var63);
                        }
                     }
                  }

                  var1 = 1;
               }
            }

            if (!var1) {
               var5 = this.catalogBox;
               if (var5 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("catalogBox");
                  var5 = null;
               }

               var5.addView((View)this.empty("No regions match the current search."));
            }
         }
      }
   }

   private static final Unit renderCatalog$lambda$132(GxNavigationPage var0, String var1) {
      if (var0.expandedGroups.contains(var1)) {
         var0.expandedGroups.remove(var1);
      } else {
         var0.expandedGroups.add(var1);
      }

      var0.renderCatalog();
      return Unit.INSTANCE;
   }

   private static final Unit renderCatalog$lambda$133(Function0 var0) {
      var0.invoke();
      return Unit.INSTANCE;
   }

   private static final Unit renderCatalog$lambda$134(Function0 var0) {
      var0.invoke();
      return Unit.INSTANCE;
   }

   private static final Unit renderCatalog$lambda$136(List var0, GxNavigationPage var1) {
      for (JSONObject var3 : var0) {
         var1.selectedDraft.add(GxPage.str$default(var1, var3, "token", null, 2, null));
      }

      var1.paintMaps();
      return Unit.INSTANCE;
   }

   private static final Unit renderCatalog$lambda$137(List var0, GxNavigationPage var1) {
      for (JSONObject var3 : var0) {
         var1.selectedDraft.remove(GxPage.str$default(var1, var3, "token", null, 2, null));
      }

      var1.paintMaps();
      return Unit.INSTANCE;
   }

   private static final Unit renderCatalog$lambda$139(GxNavigationPage var0, String var1, boolean var2) {
      LinkedHashSet var3 = var0.selectedDraft;
      if (var2) {
         var3.add(var1);
      } else {
         var3.remove(var1);
      }

      var0.paintMaps();
      return Unit.INSTANCE;
   }

   private final void renderDest() {
      if (this.destBox != null) {
         LinearLayout var5 = this.destBox;
         LinearLayout var4 = var5;
         if (var5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("destBox");
            var4 = null;
         }

         var4.removeAllViews();
         JSONObject var7 = this.destination;
         if (var7 != null) {
            var5 = this.destBox;
            var4 = var5;
            if (var5 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("destBox");
               var4 = null;
            }

            ViewGroup var17 = (ViewGroup)var4;
            String var36 = "Destination";
            this.header("Destination", var17);
            LinearLayout var6 = this.destBox;
            var4 = var6;
            if (var6 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("destBox");
               var4 = null;
            }

            var6 = this.card((ViewGroup)var4);
            var var19 = GxPage.str$default(this, var7, "name", null, 2, null);
            boolean var1;
            if (var19.length() == 0) {
               var1 = 1;
            } else {
               var1 = 0;
            }

            if (var1) {
               var var20 = this.getQuery();
               if (var20.length() == 0) {
                  var1 = (boolean)1;
               } else {
                  var1 = (boolean)0;
               }

               if (var1) {
                  var20 = var36;
               }

               var19 = var20;
            }

            this.text(var19, (ViewGroup)var6, 24.0F, CarUi.Companion.getTEXT(), true);
            String var21 = GxPage.str$default(this, var7, "place_name", null, 2, null);
            if (var21.length() > 0) {
               var1 = (boolean)1;
            } else {
               var1 = (boolean)0;
            }

            if (var1 && !StringsKt.equals(var21, GxPage.str$default(this, var7, "name", null, 2, null), true)) {
               GxPage.text$default(this, var21, (ViewGroup)var6, 0.0F, 0, false, 28, null);
            }

            Triple var8 = this.routeSummary;
            if (var8 != null) {
               String var37 = this.fmtDistance(((Number)var8.getFirst()).doubleValue());
               String var22 = this.fmtDuration(((Number)var8.getSecond()).doubleValue());
               String var47 = this.fmtEta(((Number)var8.getSecond()).doubleValue());
               this.text("Distance  " + var37 + "     Duration  " + var22 + "     ETA  " + var47, (ViewGroup)var6, 19.0F, CarUi.Companion.getACCENT(), true);
            } else if (this.lastPosition == null) {
               GxPage.text$default(this, "The car's position is unknown, so no route preview is shown.", (ViewGroup)var6, 0.0F, 0, false, 28, null);
            }

            if (this.favoriteOf(var7) != null) {
               var1 = (boolean)1;
            } else {
               var1 = (boolean)0;
            }

            ArrayList var38 = new ArrayList();
            if (this.navigationStarted) {
               var38.add(TuplesKt.to("Cancel Navigation", new GxNavigationPage$$ExternalSyntheticLambda63(this)));
            } else {
               String var23;
               if (this.loadingRoute) {
                  var23 = "Calculating...";
               } else {
                  var23 = "Start Navigation";
               }

               var38.add(TuplesKt.to(var23, new GxNavigationPage$$ExternalSyntheticLambda64(this)));
            }

            String var24;
            if (var1) {
               var24 = "Remove from favorites";
            } else {
               var24 = "Add to favorites";
            }

            var38.add(TuplesKt.to(var24, new GxNavigationPage$$ExternalSyntheticLambda65(this)));
            Pair[] var25 = var38.toArray(new Pair[0]);
            var4 = this.buttons(Arrays.copyOf(var25, var25.length), (ViewGroup)var6, this.navigationStarted ^ true);
            if (this.navigationStarted) {
               View var27 = var4.getChildAt(0);
               TextView var28;
               if (var27 instanceof TextView) {
                  var28 = (TextView)var27;
               } else {
                  var28 = null;
               }

               if (var28 != null) {
                  var28.setTextColor(CarUi.Companion.getWARN());
               }
            } else {
               View var29 = var4.getChildAt(0);
               TextView var30;
               if (var29 instanceof TextView) {
                  var30 = (TextView)var29;
               } else {
                  var30 = null;
               }

               boolean var2;
               if (!this.loadingRoute && this.getHasRoutingKey()) {
                  var2 = true;
               } else {
                  var2 = false;
               }

               this.setOn(var30, var2);
            }

            if (this.routes.size() > 1) {
               Iterable var39 = this.routes;
               boolean var15 = false;
               var var45 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var39, 10));
               boolean var3 = false;
               var1 = 0;

               for (Object var48 : var39) {
                  if (var1 < 0) {
                     CollectionsKt.throwIndexOverflow();
                  }

                  JSONObject var9 = (JSONObject)var48;
                  String var49 = this.fmtDistance(var9.optDouble("distance", 0.0));
                  String var50 = this.fmtDuration(var9.optDouble("duration", 0.0));
                  var45.add("Route " + (var1 + 1) + "\n" + var49 + " · " + var50);
                  var1++;
               }

               List var44 = var45;
               Iterable var31 = CollectionsKt.getIndices(this.routes);
               Iterator var46 = var31.iterator();

               while (true) {
                  if (!var46.hasNext()) {
                     var32 = null;
                     break;
                  }

                  var5 = (LinearLayout)var46.next();
                  if (Intrinsics.areEqual(this.routeIdOf(((Number)var5).intValue()), this.selectedRouteId)) {
                     var32 = var5;
                     break;
                  }
               }

               Integer var33 = (Integer)var32;
               if (var33 != null) {
                  var1 = var33;
               } else {
                  var1 = 0;
               }

               String var34;
               if (this.navigationStarted) {
                  var34 = "Picking another route here only changes the preview; tap Start Navigation again to send it.";
               } else {
                  var34 = null;
               }

               var5 = this.destBox;
               if (var5 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("destBox");
                  var5 = null;
               }

               this.choice("Choose a route", var34, var44, var1, (ViewGroup)var5, new GxNavigationPage$$ExternalSyntheticLambda67(this));
            }
         }
      }
   }

   private static final Unit renderDest$lambda$74(GxNavigationPage var0) {
      var0.cancelNavigation();
      return Unit.INSTANCE;
   }

   private static final Unit renderDest$lambda$75(GxNavigationPage var0) {
      var0.setDestination(var0.destination);
      return Unit.INSTANCE;
   }

   private static final Unit renderDest$lambda$76(GxNavigationPage var0) {
      var0.toggleFavorite();
      return Unit.INSTANCE;
   }

   private static final Unit renderDest$lambda$79(GxNavigationPage var0, int var1) {
      var0.selectRoute(var1);
      if (var0.navigationStarted) {
         var0.navigationStarted = false;
      }

      var0.renderDest();
      return Unit.INSTANCE;
   }

   private final void renderDestAll() {
      this.renderKeyNotice();
      this.renderStatus();
      this.renderSuggestions();
      this.renderDest();
      this.renderFavorites();
      this.renderRecents();
   }

   private final void renderFavorites() {
      if (this.favBox != null) {
         LinearLayout var4 = this.favBox;
         LinearLayout var3 = var4;
         if (var4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("favBox");
            var3 = null;
         }

         var3.removeAllViews();
         if (!this.navLoading && !this.favorites.isEmpty()) {
            var4 = this.favBox;
            var3 = var4;
            if (var4 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("favBox");
               var3 = null;
            }

            this.header("Favorite destinations", (ViewGroup)var3);

            for (JSONObject var5 : CollectionsKt.sortedWith(this.favorites, new Comparator() {

               @Override
               public final int compare(T var1, T var2) {
                  var1 = var1;
                  String var4x = GxPage.str$default(GxNavigationPage.this, var1, "name", null, 2, null).toLowerCase(Locale.ROOT);
                  Intrinsics.checkNotNullExpressionValue(var4x, "toLowerCase(...)");
                  Comparable var5x = var4x;
                  var2 = var2;
                  String var7 = GxPage.str$default(GxNavigationPage.this, var2, "name", null, 2, null).toLowerCase(Locale.ROOT);
                  Intrinsics.checkNotNullExpressionValue(var7, "toLowerCase(...)");
                  return ComparisonsKt.compareValues(var5x, var7);
               }
            })) {
               Object var14;
               if (var5.optBoolean("is_home")) {
                  var14 = "Home: ";
               } else if (var5.optBoolean("is_work")) {
                  var14 = "Work: ";
               } else {
                  var14 = "";
               }

               GxPage var7 = this;
               String var19 = GxPage.str$default(this, var5, "name", null, 2, null);
               int var1 = var19.length();
               boolean var2 = false;
               boolean var10;
               if (var1 == 0) {
                  var10 = true;
               } else {
                  var10 = false;
               }

               if (var10) {
                  var19 = GxPage.str$default(this, var5, "place_name", null, 2, null);
                  if (var19.length() == 0) {
                     var10 = true;
                  } else {
                     var10 = false;
                  }

                  if (var10) {
                     var19 = "Favorite destination";
                  }

                  var19 = var19;
               }

               String var8 = var14 + var19;
               var14 = this.secondaryLabel(var5);
               var10 = var2;
               if (var14.length() == 0) {
                  var10 = true;
               }

               if (var10) {
                  var14 = null;
               }

               String var9 = (String)var14;
               LinearLayout var21 = this.favBox;
               var14 = var21;
               if (var21 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("favBox");
                  var14 = null;
               }

               var14 = GxPage.row$default(var7, var8, var9, (ViewGroup)var14, 0, 8, null);
               var14.button("Go", true, new GxNavigationPage$$ExternalSyntheticLambda41(this, var5));
               GxPage.Row.button$default(var14, "Rename", false, new GxNavigationPage$$ExternalSyntheticLambda42(this, var5), 2, null);
               GxPage.Row.button$default(var14, "Home / Work", false, new GxNavigationPage$$ExternalSyntheticLambda43(this, var5), 2, null);
               GxPage.Row.button$default(var14, "Delete", false, new GxNavigationPage$$ExternalSyntheticLambda45(this, var5), 2, null);
            }
         }
      }
   }

   private static final Unit renderFavorites$lambda$84(GxNavigationPage var0, JSONObject var1) {
      var0.chooseSuggestion(var1);
      return Unit.INSTANCE;
   }

   private static final Unit renderFavorites$lambda$85(GxNavigationPage var0, JSONObject var1) {
      var0.renameFavorite(var1);
      return Unit.INSTANCE;
   }

   private static final Unit renderFavorites$lambda$86(GxNavigationPage var0, JSONObject var1) {
      var0.homeWork(var1);
      return Unit.INSTANCE;
   }

   private static final Unit renderFavorites$lambda$89(GxNavigationPage var0, JSONObject var1) {
      String var2 = GxPage.str$default(var0, var1, "name", null, 2, null);
      var0.confirm(
         "Remove Favorite",
         "Are you sure you want to remove " + var2 + " from your favorites?",
         "Remove",
         new GxNavigationPage$$ExternalSyntheticLambda62(var1, var0)
      );
      return Unit.INSTANCE;
   }

   private static final Unit renderFavorites$lambda$89$lambda$88(JSONObject var0, GxNavigationPage var1) {
      JSONObject var4 = new JSONObject();
      JSONObject var3 = (JSONObject)var0.opt("id");
      Object var2 = var3;
      if (var3 == null) {
         var2 = JSONObject.NULL;
      }

      var4 = var4.put("id", var2);
      var3 = var0.opt("name");
      var2 = var3;
      if (var3 == null) {
         var2 = JSONObject.NULL;
      }

      var4 = var4.put("name", var2);
      var3 = var0.opt("latitude");
      var2 = var3;
      if (var3 == null) {
         var2 = JSONObject.NULL;
      }

      var4 = var4.put("latitude", var2);
      var3 = var0.opt("longitude");
      var2 = var3;
      if (var3 == null) {
         var2 = JSONObject.NULL;
      }

      var3 = var4.put("longitude", var2);
      var2 = var0.opt("routeId");
      Object var5 = var2;
      if (var2 == null) {
         var5 = JSONObject.NULL;
      }

      var5 = var3.put("routeId", var5);
      GxPage.send$default(var1, "DELETE", "/api/navigation/favorite", (JSONObject)var5, 0L, new GxNavigationPage$$ExternalSyntheticLambda78(var1), 8, null);
      return Unit.INSTANCE;
   }

   private static final Unit renderFavorites$lambda$89$lambda$88$lambda$87(GxNavigationPage var0, String var1, JSONObject var2) {
      if (var1 != null) {
         var0.toast("Failed to remove favorite...");
      } else {
         var0.toast("Favorite removed!");
         var0.reloadFavorites();
      }

      return Unit.INSTANCE;
   }

   private final void renderKeyNotice() {
      LinearLayout var7 = this.keyBox;
      Object var4 = null;
      Object var6 = null;
      Object var5 = null;
      LinearLayout var3 = var7;
      if (var7 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("keyBox");
         var3 = null;
      }

      var3.removeAllViews();
      boolean var2 = this.navLoading;
      byte var1 = 8;
      if (var2) {
         var4 = this.keyBox;
         var3 = (LinearLayout)var4;
         if (var4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("keyBox");
            var3 = null;
         }

         var3.addView((View)this.empty("Loading navigation..."));
         var3 = this.searchCard;
         if (var3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("searchCard");
            var3 = (LinearLayout)var5;
         }

         var3.setVisibility(8);
      } else {
         var5 = this.searchCard;
         var3 = (LinearLayout)var5;
         if (var5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("searchCard");
            var3 = null;
         }

         if (this.getHasMapbox()) {
            var1 = 0;
         }

         var3.setVisibility(var1);
         if (!this.getHasMapbox()) {
            var3 = this.keyBox;
            if (var3 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("keyBox");
               var3 = (LinearLayout)var4;
            }

            var4 = this.card((ViewGroup)var3);
            GxPage.text$default(this, "Map unavailable until a Mapbox key is configured.", (ViewGroup)var4, 21.0F, CarUi.Companion.getTEXT(), false, 16, null);
            GxPage.text$default(this, "Add a Mapbox public key in App Keys to search destinations and show the map.", (ViewGroup)var4, 0.0F, 0, false, 28, null);
            Pair var10 = TuplesKt.to("Open App Keys", new GxNavigationPage$$ExternalSyntheticLambda59(this));
            var4 = (ViewGroup)var4;
            this.buttons(new Pair[]{var10}, (ViewGroup)var4, true);
         } else if (!this.getHasRoutingKey()) {
            var3 = this.keyBox;
            if (var3 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("keyBox");
               var3 = (LinearLayout)var6;
            }

            var5 = this.card((ViewGroup)var3);
            this.text("Secret Mapbox key needed", (ViewGroup)var5, 21.0F, CarUi.Companion.getWARN(), true);
            GxPage.text$default(
               this,
               "The map and destination search only use your public Mapbox key. Add a secret Mapbox key in App Keys before starting navigation so the comma can calculate the on-device route and provide turn desires.",
               (ViewGroup)var5,
               0.0F,
               0,
               false,
               28,
               null
            );
            GxPage var12 = this;
            var4 = TuplesKt.to("Open App Keys", new GxNavigationPage$$ExternalSyntheticLambda60(this));
            var5 = (ViewGroup)var5;
            GxPage.buttons$default(var12, new Pair[]{(Pair)var4}, (ViewGroup)var5, false, 4, null);
         }
      }
   }

   private static final Unit renderKeyNotice$lambda$33(GxNavigationPage var0) {
      Function1 var1 = var0.selectTab;
      Function1 var2 = var1;
      if (var1 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("selectTab");
         var2 = null;
      }

      var2.invoke(2);
      return Unit.INSTANCE;
   }

   private static final Unit renderKeyNotice$lambda$34(GxNavigationPage var0) {
      Function1 var1 = var0.selectTab;
      Function1 var2 = var1;
      if (var1 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("selectTab");
         var2 = null;
      }

      var2.invoke(2);
      return Unit.INSTANCE;
   }

   private final void renderKeys() {
      LinearLayout var6 = this.keysBox;
      LinearLayout var4 = null;
      Object var5 = null;
      LinearLayout var3 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("keysBox");
         var3 = null;
      }

      var3.removeAllViews();
      if (this.keysLoading) {
         var3 = this.keysBox;
         if (var3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("keysBox");
            var3 = (LinearLayout)var5;
         }

         var3.addView((View)this.empty("Loading keys..."));
      } else {
         int var1 = this.keysError.length();
         boolean var2 = true;
         boolean var7;
         if (var1 > 0) {
            var7 = true;
         } else {
            var7 = false;
         }

         if (var7) {
            String var33 = this.keysError;
            var5 = this.keysBox;
            var3 = (LinearLayout)var5;
            if (var5 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("keysBox");
               var3 = null;
            }

            this.note(var33, (ViewGroup)var3).setTextColor(CarUi.Companion.getWARN());
         }

         var5 = this.keysBox;
         var3 = (LinearLayout)var5;
         if (var5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("keysBox");
            var3 = null;
         }

         this.header("AMap / Gaode Keys", (ViewGroup)var3);
         var5 = this.keysBox;
         var3 = (LinearLayout)var5;
         if (var5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("keysBox");
            var3 = null;
         }

         this.note("AMap is the Gaode provider, not Google Maps.", (ViewGroup)var3);
         Iterator var13 = CollectionsKt.take(this.keyMeta, 2).iterator();

         while (var13.hasNext()) {
            this.keyRow((GxNavigationPage.KeyMeta)var13.next());
         }

         var5 = this.keysBox;
         var3 = (LinearLayout)var5;
         if (var5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("keysBox");
            var3 = null;
         }

         this.header("Mapbox Keys", (ViewGroup)var3);
         var5 = this.keysBox;
         var3 = (LinearLayout)var5;
         if (var5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("keysBox");
            var3 = null;
         }

         this.note("Public and secret Mapbox tokens used by the navigation app.", (ViewGroup)var3);
         Iterator var16 = CollectionsKt.drop(this.keyMeta, 2).iterator();

         while (var16.hasNext()) {
            this.keyRow((GxNavigationPage.KeyMeta)var16.next());
         }

         var5 = this.keysBox;
         var3 = (LinearLayout)var5;
         if (var5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("keysBox");
            var3 = null;
         }

         this.header("App Keys", (ViewGroup)var3);
         GxPage var34 = this;
         var5 = this.keysBox;
         var3 = (LinearLayout)var5;
         if (var5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("keysBox");
            var3 = null;
         }

         GxPage.Row.button$default(
            GxPage.row$default(var34, "Install The App", "The Galaxy navigation app on Google Play.", (ViewGroup)var3, 0, 8, null),
            "Open Play Store",
            false,
            new GxNavigationPage$$ExternalSyntheticLambda81(this),
            2,
            null
         );
         GxPage var35 = this;
         var5 = this.keysBox;
         var3 = (LinearLayout)var5;
         if (var5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("keysBox");
            var3 = null;
         }

         GxPage.row$default(var35, "Cookie Name", null, (ViewGroup)var3, 0, 8, null).value(this.cookieName);
         GxPage var36 = this;
         if (this.galaxyToken.length() == 0) {
            var7 = true;
         } else {
            var7 = false;
         }

         String var20;
         if (var7) {
            if (this.paired) {
               var20 = "Session token unavailable...";
            } else {
               var20 = "Pair Galaxy to create a session token...";
            }
         } else if (this.tokenVisible) {
            var20 = this.galaxyToken;
         } else {
            var20 = StringsKt.repeat("•", Math.min(this.galaxyToken.length(), 24));
         }

         var5 = this.keysBox;
         if (var5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("keysBox");
         } else {
            var4 = (LinearLayout)var5;
         }

         GxPage.Row var23 = GxPage.row$default(var36, "Session Token", var20, (ViewGroup)var4, 0, 8, null);
         if (this.galaxyToken.length() > 0) {
            var7 = var2;
         } else {
            var7 = false;
         }

         if (var7) {
            var23.getDesc().setTextIsSelectable(this.tokenVisible);
            String var21;
            if (this.tokenVisible) {
               var21 = "Hide";
            } else {
               var21 = "Show";
            }

            GxPage.Row.button$default(var23, var21, false, new GxNavigationPage$$ExternalSyntheticLambda82(this), 2, null);
            GxPage.Row.button$default(var23, "Copy", false, new GxNavigationPage$$ExternalSyntheticLambda1(this), 2, null);
         }
      }
   }

   private static final Unit renderKeys$lambda$152(GxNavigationPage var0) {
      var0.openUrl(var0.appUrl);
      return Unit.INSTANCE;
   }

   private static final Unit renderKeys$lambda$153(GxNavigationPage var0) {
      var0.tokenVisible ^= true;
      var0.renderKeys();
      return Unit.INSTANCE;
   }

   private static final Unit renderKeys$lambda$154(GxNavigationPage var0) {
      try {
         Object var1 = var0.getAct().getSystemService("clipboard");
         Intrinsics.checkNotNull(var1, "null cannot be cast to non-null type android.content.ClipboardManager");
         ((ClipboardManager)var1).setPrimaryClip(ClipData.newPlainText("Galaxy session token", var0.galaxyToken));
         var0.toast("Session token copied!");
      } catch (Exception var2) {
         var0.toast("Copy failed...");
      }

      return Unit.INSTANCE;
   }

   private final void renderNavAfterKeys() {
      if (this.keyBox != null && !this.navLoading) {
         this.renderKeyNotice();
         this.renderDest();
      }
   }

   private final void renderRecents() {
      if (this.recentBox != null) {
         LinearLayout var3 = this.recentBox;
         LinearLayout var2 = var3;
         if (var3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recentBox");
            var2 = null;
         }

         var2.removeAllViews();
         if (!this.navLoading) {
            HashSet var4 = new HashSet();

            for (JSONObject var5 : this.favorites) {
               Pair var15 = this.coordsOf(var5);
               String var17;
               if (var15 != null) {
                  var5 = (JSONObject)var15.getFirst();
                  Object var16 = var15.getSecond();
                  var17 = var5 + ":" + var16;
               } else {
                  var17 = this.labelFor(var5).toLowerCase(Locale.ROOT);
                  Intrinsics.checkNotNullExpressionValue(var17, "toLowerCase(...)");
               }

               var4.add(var17);
            }

            Iterable var29 = this.recentDestinations;
            var var7 = new ArrayList();

            for (Object var8 : var29) {
               JSONObject var18 = (JSONObject)var8;
               Pair var9 = this.coordsOf(var18);
               String var20;
               if (var9 != null) {
                  Object var35 = var9.getFirst();
                  Object var19 = var9.getSecond();
                  var20 = var35 + ":" + var19;
               } else {
                  var20 = this.labelFor(var18).toLowerCase(Locale.ROOT);
                  Intrinsics.checkNotNullExpressionValue(var20, "toLowerCase(...)");
               }

               boolean var1;
               if (var20.length() > 0) {
                  var1 = true;
               } else {
                  var1 = false;
               }

               label116: {
                  if (var1 && var4.add(var20)) {
                     var var36 = this.favorites;
                     if (var36 instanceof Collection && ((Collection)var36).isEmpty()) {
                        var1 = true;
                     } else {
                        Iterator var37 = var36.iterator();

                        while (true) {
                           if (!var37.hasNext()) {
                              var1 = true;
                              break;
                           }

                           if (this.sameSpot(this.coordsOf((JSONObject)var37.next()), var9)) {
                              var1 = false;
                              break;
                           }
                        }
                     }

                     if (var1) {
                        var1 = true;
                        break label116;
                     }
                  }

                  var1 = false;
               }

               if (var1) {
                  var7.add(var8);
               }
            }

            List var21 = var7;
            List var32 = CollectionsKt.take(var21, 10);
            if (!var32.isEmpty()) {
               var3 = this.recentBox;
               var2 = var3;
               if (var3 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("recentBox");
                  var2 = null;
               }

               this.header("Recent destinations", (ViewGroup)var2);

               for (JSONObject var33 : var32) {
                  GxPage var39 = this;
                  var var23 = GxPage.str$default(this, var33, "name", null, 2, null);
                  boolean var12;
                  if (var23.length() == 0) {
                     var12 = true;
                  } else {
                     var12 = false;
                  }

                  if (var12) {
                     var var24 = GxPage.str$default(this, var33, "place_name", null, 2, null);
                     if (var24.length() == 0) {
                        var12 = true;
                     } else {
                        var12 = false;
                     }

                     if (var12) {
                        var24 = "Destination";
                     }

                     var23 = var24;
                  }

                  String var40 = var23;
                  var var25 = this.secondaryLabel(var33);
                  if (var25.length() == 0) {
                     var12 = true;
                  } else {
                     var12 = false;
                  }

                  if (var12) {
                     var25 = null;
                  }

                  String var41 = var25;
                  var3 = this.recentBox;
                  var2 = var3;
                  if (var3 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("recentBox");
                     var2 = null;
                  }

                  GxPage.Row var27 = GxPage.row$default(var39, var40, var41, (ViewGroup)var2, 0, 8, null);
                  this.clickable(var27, new GxNavigationPage$$ExternalSyntheticLambda15(this, var33));
                  GxPage.Row.button$default(var27, "Go", false, new GxNavigationPage$$ExternalSyntheticLambda16(this, var33), 2, null);
               }
            }
         }
      }
   }

   private static final Unit renderRecents$lambda$100(GxNavigationPage var0, JSONObject var1) {
      var0.chooseSuggestion(var1);
      return Unit.INSTANCE;
   }

   private static final Unit renderRecents$lambda$99(GxNavigationPage var0, JSONObject var1) {
      var0.chooseSuggestion(var1);
      return Unit.INSTANCE;
   }

   private final void renderStatus() {
      boolean var3 = this.searching;
      boolean var2 = true;
      String var4;
      if (var3) {
         var4 = "Searching...";
      } else {
         boolean var1;
         if (this.navError.length() > 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         if (var1) {
            var4 = this.navError;
         } else {
            var4 = "";
         }
      }

      TextView var7 = this.statusNote;
      Object var6 = null;
      TextView var5 = var7;
      if (var7 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("statusNote");
         var5 = null;
      }

      var5.setText(var4);
      var7 = this.statusNote;
      var5 = var7;
      if (var7 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("statusNote");
         var5 = null;
      }

      boolean var8;
      if (var4.length() == 0) {
         var8 = 1;
      } else {
         var8 = 0;
      }

      if (var8) {
         var8 = (byte)8;
      } else {
         var8 = (byte)0;
      }

      var5.setVisibility(var8);
      TextView var12 = this.statusNote;
      if (var12 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("statusNote");
         var12 = (TextView)var6;
      }

      label48: {
         if (!this.searching) {
            boolean var10;
            if (this.navError.length() > 0) {
               var10 = var2;
            } else {
               var10 = false;
            }

            if (var10) {
               var8 = CarUi.Companion.getWARN();
               break label48;
            }
         }

         var8 = CarUi.Companion.getDIM();
      }

      var12.setTextColor(var8);
   }

   private final void renderSuggestions() {
      LinearLayout var4 = this.suggestBox;
      LinearLayout var3 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("suggestBox");
         var3 = null;
      }

      var3.removeAllViews();
      if (!this.suggestions.isEmpty()) {
         var4 = this.suggestBox;
         var3 = var4;
         if (var4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("suggestBox");
            var3 = null;
         }

         this.header("Results", (ViewGroup)var3);

         for (JSONObject var5 : this.suggestions) {
            var var15 = this.primaryOf(var5);
            int var1 = var15.length();
            boolean var2 = true;
            boolean var10;
            if (var1 == 0) {
               var10 = true;
            } else {
               var10 = false;
            }

            if (var10) {
               var var16 = GxPage.str$default(this, var5, "place_name", null, 2, null);
               if (var16.length() == 0) {
                  var10 = true;
               } else {
                  var10 = false;
               }

               if (var10) {
                  var16 = "Unnamed location";
               }

               var15 = var16;
            }

            String var8 = var15;
            GxPage var7 = this;
            var var17 = this.secondaryLabel(var5);
            if (var17.length() == 0) {
               var10 = true;
            } else {
               var10 = false;
            }

            if (var10) {
               var var18 = GxPage.str$default(this, var5, "place_formatted", null, 2, null);
               if (var18.length() == 0) {
                  var10 = var2;
               } else {
                  var10 = false;
               }

               if (var10) {
                  var18 = null;
               }

               var17 = var18;
            }

            String var9 = var17;
            var4 = this.suggestBox;
            var3 = var4;
            if (var4 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("suggestBox");
               var3 = null;
            }

            GxPage.Row var20 = GxPage.row$default(var7, var8, var9, (ViewGroup)var3, 0, 8, null);
            this.clickable(var20, new GxNavigationPage$$ExternalSyntheticLambda34(this, var5));
            GxPage.Row.button$default(var20, "Select", false, new GxNavigationPage$$ExternalSyntheticLambda35(this, var5), 2, null);
         }
      }
   }

   private static final Unit renderSuggestions$lambda$43(GxNavigationPage var0, JSONObject var1) {
      var0.chooseSuggestion(var1);
      return Unit.INSTANCE;
   }

   private static final Unit renderSuggestions$lambda$44(GxNavigationPage var0, JSONObject var1) {
      var0.chooseSuggestion(var1);
      return Unit.INSTANCE;
   }

   private final void resolvePlace(JSONObject var1, Function2<? super String, ? super JSONObject, Unit> var2) {
      String var5 = this.primaryOf(var1);
      var var4 = var5;
      boolean var3;
      if (var4.length() == 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (var3) {
         var4 = this.labelFor(var1);
         if (var4.length() == 0) {
            var3 = true;
         } else {
            var3 = false;
         }

         if (var3) {
            var4 = StringsKt.trim(this.getQuery()).toString();
         }

         var4 = var4;
      }

      var4 = var4;
      Pair var6 = this.coordsOf(var1);
      if (var6 != null) {
         resolvePlace$done(var2, var5, this, var1, var4, var6);
      } else {
         String var12 = GxPage.str$default(this, var1, "mapbox_id", null, 2, null);
         if (var12.length() > 0) {
            var3 = true;
         } else {
            var3 = false;
         }

         if (var3 && this.getHasMapbox()) {
            String var13 = this.enc(var12);
            String var7 = this.qs(TuplesKt.to("access_token", this.mapboxPublic), TuplesKt.to("session_token", this.sessionToken));
            this.extGet(
               "https://api.mapbox.com/search/searchbox/v1/retrieve/" + var13 + "?" + var7,
               new GxNavigationPage$$ExternalSyntheticLambda53(this, var2, var5, var1, var4)
            );
         } else {
            resolvePlace$geocode(this, var4, var2, var5, var1);
         }
      }
   }

   private static final void resolvePlace$done(
      Function2<? super String, ? super JSONObject, Unit> var0, String var1, GxNavigationPage var2, JSONObject var3, String var4, Pair<Double, Double> var5
   ) {
      if (var5 == null) {
         var0.invoke("Could not determine that location.", null);
      } else {
         JSONObject var10 = new JSONObject()
            .put("latitude", ((Number)var5.getFirst()).doubleValue())
            .put("longitude", ((Number)var5.getSecond()).doubleValue());
         Object var8 = var1;
         int var6 = var8.length();
         boolean var7 = true;
         boolean var12;
         if (var6 == 0) {
            var12 = true;
         } else {
            var12 = false;
         }

         if (var12) {
            var8 = var4;
         }

         JSONObject var11 = var10.put("name", var8);
         var8 = var2.labelFor(var3);
         if (var8.length() == 0) {
            var12 = var7;
         } else {
            var12 = false;
         }

         if (var12) {
            var8 = var4;
         }

         var0.invoke(null, var11.put("place_name", var8));
      }
   }

   private static final void resolvePlace$geocode(
      GxNavigationPage var0, String var1, Function2<? super String, ? super JSONObject, Unit> var2, String var3, JSONObject var4
   ) {
      if (var0.getHasMapbox()) {
         boolean var5;
         if (var1.length() == 0) {
            var5 = true;
         } else {
            var5 = false;
         }

         if (!var5) {
            SpreadBuilder var6 = new SpreadBuilder(3);
            var6.add(TuplesKt.to("access_token", var0.mapboxPublic));
            var6.add(TuplesKt.to("q", var1));
            var6.addSpread(var0.searchContext(var1).toArray(new Pair[0]));
            String var7 = var0.qs((Pair<String, String>[])var6.toArray(new Pair[var6.size()]));
            var0.extGet(
               "https://api.mapbox.com/search/geocode/v6/forward?" + var7, new GxNavigationPage$$ExternalSyntheticLambda79(var0, var2, var3, var4, var1)
            );
            return;
         }
      }

      resolvePlace$done(var2, var3, var0, var4, var1, null);
   }

   private static final Unit resolvePlace$geocode$lambda$49(
      GxNavigationPage var0, Function2 var1, String var2, JSONObject var3, String var4, String var5, JSONObject var6
   ) {
      label16: {
         if (var6 != null) {
            JSONArray var7 = var6.optJSONArray("features");
            if (var7 != null) {
               JSONObject var8 = var7.optJSONObject(0);
               if (var8 != null) {
                  JSONObject var9 = var8.optJSONObject("geometry");
                  if (var9 != null) {
                     var10 = var9.optJSONArray("coordinates");
                     break label16;
                  }
               }
            }
         }

         var10 = null;
      }

      resolvePlace$done(var1, var2, var0, var3, var4, var0.coordsArr(var10));
      return Unit.INSTANCE;
   }

   private static final Unit resolvePlace$lambda$50(
      GxNavigationPage var0, Function2 var1, String var2, JSONObject var3, String var4, String var5, JSONObject var6
   ) {
      label21: {
         if (var6 != null) {
            JSONArray var7 = var6.optJSONArray("features");
            if (var7 != null) {
               JSONObject var8 = var7.optJSONObject(0);
               if (var8 != null) {
                  JSONObject var9 = var8.optJSONObject("geometry");
                  if (var9 != null) {
                     var10 = var9.optJSONArray("coordinates");
                     break label21;
                  }
               }
            }
         }

         var10 = null;
      }

      Pair var11 = var0.coordsArr(var10);
      if (var11 != null) {
         resolvePlace$done(var1, var2, var0, var3, var4, var11);
      } else {
         resolvePlace$geocode(var0, var4, var1, var2, var3);
      }

      return Unit.INSTANCE;
   }

   private final void resolveQuery(Function2<? super String, ? super JSONObject, Unit> var1) {
      String var3 = StringsKt.trim(this.getQuery()).toString();
      boolean var2;
      if (var3.length() == 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (var2) {
         var1.invoke("Enter a destination first.", null);
      } else {
         JSONObject var4 = this.destination;
         if (var4 != null && Intrinsics.areEqual(GxPage.str$default(this, var4, "name", null, 2, null), var3)) {
            var1.invoke(null, var4);
         } else {
            JSONObject var5 = new JSONObject().put("name", var3);
            Intrinsics.checkNotNullExpressionValue(var5, "put(...)");
            this.resolvePlace(var5, var1);
         }
      }
   }

   private final String routeIdOf(int var1) {
      String var2;
      if (var1 == 0) {
         var2 = "main";
      } else {
         var2 = "alt-" + var1;
      }

      return var2;
   }

   private final boolean sameSpot(Pair<Double, Double> var1, Pair<Double, Double> var2) {
      boolean var3;
      if (var1 != null
         && var2 != null
         && Math.abs(((Number)var1.getFirst()).doubleValue() - ((Number)var2.getFirst()).doubleValue()) < 1.0E-5
         && Math.abs(((Number)var1.getSecond()).doubleValue() - ((Number)var2.getSecond()).doubleValue()) < 1.0E-5) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   private final void saveSchedule() {
      if (!this.mapsSavingSchedule && this.getScheduleDirty()) {
         this.mapsSavingSchedule = true;
         this.paintMaps();
         GxPage.send$default(
            this,
            "POST",
            "/api/maps/schedule",
            new JSONObject().put("schedule", this.scheduleDraft),
            0L,
            new GxNavigationPage$$ExternalSyntheticLambda72(this),
            8,
            null
         );
      }
   }

   private static final Unit saveSchedule$lambda$143(GxNavigationPage var0, String var1, JSONObject var2) {
      var0.mapsSavingSchedule = false;
      String var8;
      if (var1 == null) {
         Object var5 = null;
         Object var4 = null;
         String var3 = (String)var5;
         if (var2 != null) {
            String var6 = var2.optString("message");
            var3 = (String)var5;
            if (var6 != null) {
               var var7 = var6;
               if (StringsKt.isBlank(var7)) {
                  var7 = (String)var4;
               }

               var3 = var7;
            }
         }

         var8 = var3;
         if (var3 == null) {
            var8 = "Map schedule updated.";
         }
      } else {
         var8 = var1;
      }

      var0.toast(var8);
      if (var1 == null) {
         var0.scheduleSaved = var0.scheduleDraft;
      }

      var0.paintMaps();
      var0.refreshMapsStatus();
      return Unit.INSTANCE;
   }

   private final void saveSelection() {
      if (!this.mapsSavingSelection && this.getSelectionDirty()) {
         this.mapsSavingSelection = true;
         this.paintMaps();
         GxPage.send$default(
            this,
            "POST",
            "/api/maps/selection",
            new JSONObject().put("selectedLocations", new JSONArray(CollectionsKt.toList(this.selectedDraft))),
            0L,
            new GxNavigationPage$$ExternalSyntheticLambda22(this),
            8,
            null
         );
      }
   }

   private static final Unit saveSelection$lambda$141(GxNavigationPage var0, String var1, JSONObject var2) {
      var0.mapsSavingSelection = false;
      String var8;
      if (var1 == null) {
         Object var5 = null;
         Object var4 = null;
         String var3 = (String)var5;
         if (var2 != null) {
            String var6 = var2.optString("message");
            var3 = (String)var5;
            if (var6 != null) {
               var var7 = var6;
               if (StringsKt.isBlank(var7)) {
                  var7 = (String)var4;
               }

               var3 = var7;
            }
         }

         var8 = var3;
         if (var3 == null) {
            var8 = "Map selection saved.";
         }
      } else {
         var8 = var1;
      }

      var0.toast(var8);
      if (var1 == null) {
         var0.selectedSaved = CollectionsKt.toSet(var0.selectedDraft);
      }

      var0.paintMaps();
      var0.refreshMapsStatus();
      return Unit.INSTANCE;
   }

   private final void search(String var1) {
      if (var1.length() >= 3 && this.getHasMapbox()) {
         this.searchSeq++;
         int var2 = this.searchSeq;
         this.searching = true;
         this.renderStatus();
         SpreadBuilder var3 = new SpreadBuilder(5);
         var3.add(TuplesKt.to("access_token", this.mapboxPublic));
         var3.add(TuplesKt.to("session_token", this.sessionToken));
         var3.add(TuplesKt.to("q", var1));
         var3.add(TuplesKt.to("limit", "4"));
         var3.addSpread(this.searchContext(var1).toArray(new Pair[0]));
         var1 = this.qs((Pair<String, String>[])var3.toArray(new Pair[var3.size()]));
         this.extGet("https://api.mapbox.com/search/searchbox/v1/suggest?" + var1, new GxNavigationPage$$ExternalSyntheticLambda74(var2, this));
      }
   }

   private static final Unit search$lambda$38(int var0, GxNavigationPage var1, String var2, JSONObject var3) {
      if (var0 != var1.searchSeq) {
         return Unit.INSTANCE;
      } else {
         var1.searching = false;
         if (var2 != null) {
            CharSequence var7 = var2;
            Object var4 = var7;
            if (StringsKt.isBlank(var7)) {
               var4 = "Destination search failed.";
            }

            var1.navError = (String)var4;
         } else {
            label23: {
               if (var3 != null) {
                  JSONArray var5 = var3.optJSONArray("suggestions");
                  if (var5 != null) {
                     List var8 = var1.objects(var5);
                     var6 = var8;
                     if (var8 != null) {
                        break label23;
                     }
                  }
               }

               var6 = CollectionsKt.emptyList();
            }

            var1.suggestions = var6;
         }

         var1.renderStatus();
         var1.renderSuggestions();
         return Unit.INSTANCE;
      }
   }

   private final List<Pair<String, String>> searchContext(String var1) {
      CharSequence var13 = var1;
      boolean var10 = false;
      int var6 = 0;

      while (true) {
         if (var6 >= var13.length()) {
            var15 = false;
            break;
         }

         char var7 = var13.charAt(var6);
         boolean var16;
         if (12352 <= var7 && var7 < 12544) {
            var16 = true;
         } else {
            var16 = false;
         }

         if (var16) {
            var15 = true;
            break;
         }

         var6++;
      }

      boolean var17;
      if (!new Regex("^(?:main_)?ja(?:[-_]|$)", RegexOption.IGNORE_CASE).containsMatchIn(this.language)
         && !Intrinsics.areEqual(Locale.getDefault().getLanguage(), "ja")) {
         var17 = false;
      } else {
         var17 = true;
      }

      Pair var12 = this.lastPosition;
      boolean var9 = var10;
      if (var12 != null) {
         double var4 = ((Number)var12.component1()).doubleValue();
         double var2 = ((Number)var12.component2()).doubleValue();
         boolean var8;
         if (30.0 <= var4 && var4 <= 45.8) {
            var8 = true;
         } else {
            var8 = false;
         }

         label110: {
            label146: {
               if (var8) {
                  if (129.2 <= var2 && var2 <= 146.0) {
                     var8 = true;
                  } else {
                     var8 = false;
                  }

                  if (var8) {
                     break label146;
                  }
               }

               if (var4 >= 24.0 && var4 < 30.0) {
                  if (122.8 <= var2 && var2 <= 131.5) {
                     var8 = true;
                  } else {
                     var8 = false;
                  }

                  if (var8) {
                     break label146;
                  }
               }

               if (var4 >= 20.0 && var4 < 30.0) {
                  if (136.0 <= var2 && var2 <= 154.0) {
                     var8 = true;
                  } else {
                     var8 = false;
                  }

                  if (var8) {
                     break label146;
                  }
               }

               var8 = false;
               break label110;
            }

            var8 = true;
         }

         var9 = var10;
         if (var8) {
            var9 = true;
         }
      }

      ArrayList var11 = new ArrayList();
      if (!var9 && (!var15 && !var17 || var12 != null)) {
         if (var15 || var17) {
            var11.add(TuplesKt.to("language", "ja"));
         }
      } else {
         var11.add(TuplesKt.to("language", "ja"));
         var11.add(TuplesKt.to("country", "jp"));
      }

      if (var12 != null) {
         Object var14 = var12.getSecond();
         Object var22 = var12.getFirst();
         var11.add(TuplesKt.to("proximity", var14 + "," + var22));
      }

      return var11;
   }

   private static final void searchRunner$lambda$22(GxNavigationPage var0) {
      var0.search(StringsKt.trim(var0.getQuery()).toString());
   }

   private final String secondaryLabel(JSONObject var1) {
      String var3 = this.primaryOf(var1);
      String var4 = this.labelFor(var1);
      boolean var2;
      if (var4.length() > 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (!var2 || StringsKt.equals(var4, var3, true)) {
         var4 = "";
      }

      return var4;
   }

   private final void selectRoute(int var1) {
      JSONObject var12 = CollectionsKt.getOrNull(this.routes, var1);
      if (var12 != null) {
         String var13 = this.routeIdOf(var1);
         this.selectedRouteId = var13;
         JSONObject var10 = this.destination;
         if (var10 != null) {
            var10.put("routeId", var13);
         }

         double var4 = 0.0;
         Double var17 = var12.optDouble("distance", 0.0);
         double var2 = var17.doubleValue();
         boolean var9 = Double.isInfinite(var2);
         boolean var8 = true;
         boolean var14;
         if (!var9 && !Double.isNaN(var2)) {
            var14 = true;
         } else {
            var14 = false;
         }

         Object var11 = null;
         if (!var14) {
            var17 = null;
         }

         if (var17 != null) {
            var2 = var17;
         } else {
            var2 = 0.0;
         }

         Double var19 = var12.optDouble("duration", 0.0);
         double var6 = var19.doubleValue();
         if (!Double.isInfinite(var6) && !Double.isNaN(var6)) {
            var14 = var8;
         } else {
            var14 = false;
         }

         Double var18 = (Double)var11;
         if (var14) {
            var18 = var19;
         }

         if (var18 != null) {
            var4 = var18;
         }

         this.routeSummary = new Triple<>(var2, var4, var13);
      }
   }

   private final void setDestination(JSONObject var1) {
      if (!this.getHasMapbox()) {
         this.toast("Add a Mapbox public key in App Keys first.");
      } else if (!this.getHasRoutingKey()) {
         this.alert(
            "Secret Mapbox key needed",
            "Add a Mapbox secret key in App Keys first. It is required for the comma to calculate the on-device route and provide navigation turn desires."
         );
      } else if (!this.loadingRoute) {
         this.loadingRoute = true;
         this.renderDest();
         GxNavigationPage$$ExternalSyntheticLambda39 var2 = new GxNavigationPage$$ExternalSyntheticLambda39(this);
         if (var1 != null) {
            var2.invoke(null, var1);
         } else {
            this.resolveQuery(var2);
         }
      }
   }

   private static final Unit setDestination$lambda$55(GxNavigationPage var0, String var1, JSONObject var2) {
      boolean var3 = false;
      if (var1 == null && var2 != null) {
         var1 = var0.selectedRouteId;
         if (var1.length() == 0) {
            var3 = true;
         }

         label24:
         if (var3) {
            Triple var6 = var0.routeSummary;
            if (var6 != null) {
               String var4 = (String)var6.getThird();
               var1 = var4;
               if (var4 != null) {
                  break label24;
               }
            }

            var1 = "main";
         }

         var1 = var1;
         var2 = new JSONObject(var2.toString()).put("routeId", var1);
         var0.destination = var2;
         GxPage.send$default(var0, "POST", "/api/navigation", var2, 0L, new GxNavigationPage$$ExternalSyntheticLambda77(var0, var2, var1), 8, null);
         return Unit.INSTANCE;
      } else {
         var0.loadingRoute = false;
         if (var1 == null) {
            var1 = "Enter a destination first.";
         }

         var0.navError = var1;
         var0.toast(var0.navError);
         var0.renderStatus();
         var0.renderDest();
         return Unit.INSTANCE;
      }
   }

   private static final Unit setDestination$lambda$55$lambda$54(GxNavigationPage var0, JSONObject var1, String var2, String var3, JSONObject var4) {
      var0.loadingRoute = false;
      if (var3 != null) {
         var0.navError = var3;
         var0.toast(var3);
         var0.renderStatus();
         var0.renderDest();
         return Unit.INSTANCE;
      } else {
         var0.navigationStarted = true;
         var0.navError = "";
         GxPage var5 = var0;
         Intrinsics.checkNotNull(var1);
         var0.setQuery(GxPage.str$default(var5, var1, "name", null, 2, null));
         var0.suggestions = CollectionsKt.emptyList();
         var0.toast("Destination set.");
         var0.renderStatus();
         var0.renderSuggestions();
         var0.renderDest();
         var0.previewDestination(var1, var2);
         var0.getJson("/api/navigation", new GxNavigationPage$$ExternalSyntheticLambda7(var0));
         return Unit.INSTANCE;
      }
   }

   private static final Unit setDestination$lambda$55$lambda$54$lambda$53(GxNavigationPage var0, String var1, JSONObject var2) {
      if (var2 != null) {
         var0.recentDestinations = var0.parseArr(var2.opt("previousDestinations"));
         var0.renderRecents();
      }

      return Unit.INSTANCE;
   }

   private final void setOn(TextView var1, boolean var2) {
      if (var1 != null) {
         var1.setEnabled(var2);
         float var3;
         if (var2) {
            var3 = 1.0F;
         } else {
            var3 = 0.4F;
         }

         var1.setAlpha(var3);
      }
   }

   private final void setQuery(String var1) {
      this.quietEdit = true;
      EditText var4 = this.queryEdit;
      Object var3 = null;
      EditText var2 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("queryEdit");
         var2 = null;
      }

      var2.setText(var1);
      var2 = this.queryEdit;
      EditText var5 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("queryEdit");
         var5 = null;
      }

      var2 = this.queryEdit;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("queryEdit");
         var2 = (EditText)var3;
      }

      var5.setSelection(var2.getText().length());
      this.quietEdit = false;
   }

   private final void startDownload() {
      if (!this.mapsActionBusy && !this.isDownloading() && !this.isOnroad() && !this.selectedDraft.isEmpty()) {
         JSONObject var1 = new JSONObject()
            .put("selectedLocations", new JSONArray(CollectionsKt.toList(this.selectedDraft)))
            .put("schedule", this.scheduleDraft);
         Intrinsics.checkNotNullExpressionValue(var1, "put(...)");
         this.mapsOp("download", var1, "Map download started.");
      }
   }

   private final TextView statRow(String var1, LinearLayout var2) {
      return GxPage.row$default(this, var1, null, (ViewGroup)var2, 0, 8, null).value("…");
   }

   private final void toggleFavorite() {
      JSONObject var3 = this.destination;
      if (var3 != null) {
         Pair var4 = this.coordsOf(var3);
         if (var4 != null) {
            JSONObject var5 = this.favoriteOf(var3);
            if (var5 != null) {
               GxPage.send$default(this, "DELETE", "/api/navigation/favorite", var5, 0L, new GxNavigationPage$$ExternalSyntheticLambda36(this), 8, null);
            } else {
               var5 = new JSONObject();
               String var8 = GxPage.str$default(this, var3, "name", null, 2, null);
               int var1 = var8.length();
               boolean var2 = true;
               boolean var6;
               if (var1 == 0) {
                  var6 = true;
               } else {
                  var6 = false;
               }

               if (var6) {
                  var8 = this.getQuery();
                  if (var8.length() == 0) {
                     var6 = var2;
                  } else {
                     var6 = false;
                  }

                  if (var6) {
                     var8 = "Favorite destination";
                  }

                  var8 = var8;
               }

               label36: {
                  var5 = var5.put("name", var8)
                     .put("longitude", ((Number)var4.getSecond()).doubleValue())
                     .put("latitude", ((Number)var4.getFirst()).doubleValue());
                  Triple var10 = this.routeSummary;
                  if (var10 != null) {
                     String var13 = (String)var10.getThird();
                     var11 = var13;
                     if (var13 != null) {
                        break label36;
                     }
                  }

                  var11 = JSONObject.NULL;
               }

               JSONObject var12 = var5.put("routeId", var11);
               GxPage.send$default(this, "POST", "/api/navigation/favorite", var12, 0L, new GxNavigationPage$$ExternalSyntheticLambda37(this), 8, null);
            }
         }
      }
   }

   private static final Unit toggleFavorite$lambda$60(GxNavigationPage var0, String var1, JSONObject var2) {
      if (var1 != null) {
         var0.toast(var1);
      } else {
         var0.toast("Removed from favorites.");
         var0.reloadFavorites();
      }

      return Unit.INSTANCE;
   }

   private static final Unit toggleFavorite$lambda$63(GxNavigationPage var0, String var1, JSONObject var2) {
      if (var1 != null) {
         var0.toast(var1);
      } else {
         var0.toast("Added to favorites.");
         var0.reloadFavorites();
      }

      return Unit.INSTANCE;
   }

   @Override
   public void build() {
      GxPage.header$default(this, "Navigation & Maps", null, 2, null);
      this.selectTab = this.tabs(
         CollectionsKt.listOf(new String[]{"Destination", "Maps", "App Keys", "Speed Limits"}),
         0,
         new GxNavigationPage$$ExternalSyntheticLambda68(this),
         new GxNavigationPage$$ExternalSyntheticLambda69(this)
      );
      this.every(2500L, new GxNavigationPage$$ExternalSyntheticLambda70(this));
      this.every(3000L, new GxNavigationPage$$ExternalSyntheticLambda71(this));
   }

   @Override
   public boolean getNeedsOffroad() {
      return this.needsOffroad;
   }

   @Override
   public void onHide() {
      this.getMain().removeCallbacks(this.searchRunner);
   }

   @Override
   public void onShow() {
      if (this.curTab == 0) {
         this.loadNav();
      } else if (this.curTab == 2) {
         this.loadKeys();
      }
   }

   private static final class KeyMeta {
      private final String body;
      private final String kind;
      private final int min;
      private final String navField;
      private final String prefix;
      private final String title;

      public KeyMeta(String var1, String var2, String var3, int var4, String var5, String var6) {
         Intrinsics.checkNotNullParameter(var1, "kind");
         Intrinsics.checkNotNullParameter(var2, "body");
         Intrinsics.checkNotNullParameter(var3, "prefix");
         Intrinsics.checkNotNullParameter(var5, "title");
         Intrinsics.checkNotNullParameter(var6, "navField");
         super();
         this.kind = var1;
         this.body = var2;
         this.prefix = var3;
         this.min = var4;
         this.title = var5;
         this.navField = var6;
      }

      // $VF: synthetic method
      public static GxNavigationPage.KeyMeta copy$default(
         GxNavigationPage.KeyMeta var0, String var1, String var2, String var3, int var4, String var5, String var6, int var7, Object var8
      ) {
         if ((var7 & 1) != 0) {
            var1 = var0.kind;
         }

         if ((var7 & 2) != 0) {
            var2 = var0.body;
         }

         if ((var7 & 4) != 0) {
            var3 = var0.prefix;
         }

         if ((var7 & 8) != 0) {
            var4 = var0.min;
         }

         if ((var7 & 16) != 0) {
            var5 = var0.title;
         }

         if ((var7 & 32) != 0) {
            var6 = var0.navField;
         }

         return var0.copy(var1, var2, var3, var4, var5, var6);
      }

      public final String component1() {
         return this.kind;
      }

      public final String component2() {
         return this.body;
      }

      public final String component3() {
         return this.prefix;
      }

      public final int component4() {
         return this.min;
      }

      public final String component5() {
         return this.title;
      }

      public final String component6() {
         return this.navField;
      }

      public final GxNavigationPage.KeyMeta copy(String var1, String var2, String var3, int var4, String var5, String var6) {
         Intrinsics.checkNotNullParameter(var1, "kind");
         Intrinsics.checkNotNullParameter(var2, "body");
         Intrinsics.checkNotNullParameter(var3, "prefix");
         Intrinsics.checkNotNullParameter(var5, "title");
         Intrinsics.checkNotNullParameter(var6, "navField");
         return new GxNavigationPage.KeyMeta(var1, var2, var3, var4, var5, var6);
      }

      @Override
      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof GxNavigationPage.KeyMeta)) {
            return false;
         } else {
            var1 = var1;
            if (!Intrinsics.areEqual(this.kind, var1.kind)) {
               return false;
            } else if (!Intrinsics.areEqual(this.body, var1.body)) {
               return false;
            } else if (!Intrinsics.areEqual(this.prefix, var1.prefix)) {
               return false;
            } else if (this.min != var1.min) {
               return false;
            } else {
               return !Intrinsics.areEqual(this.title, var1.title) ? false : Intrinsics.areEqual(this.navField, var1.navField);
            }
         }
      }

      public final String getBody() {
         return this.body;
      }

      public final String getKind() {
         return this.kind;
      }

      public final int getMin() {
         return this.min;
      }

      public final String getNavField() {
         return this.navField;
      }

      public final String getPrefix() {
         return this.prefix;
      }

      public final String getTitle() {
         return this.title;
      }

      @Override
      public int hashCode() {
         return (
                  (((this.kind.hashCode() * 31 + this.body.hashCode()) * 31 + this.prefix.hashCode()) * 31 + Integer.hashCode(this.min)) * 31
                     + this.title.hashCode()
               )
               * 31
            + this.navField.hashCode();
      }

      @Override
      public String toString() {
         String var6 = this.kind;
         String var3 = this.body;
         String var5 = this.prefix;
         int var1 = this.min;
         String var2 = this.title;
         String var4 = this.navField;
         return "KeyMeta(kind=" + var6 + ", body=" + var3 + ", prefix=" + var5 + ", min=" + var1 + ", title=" + var2 + ", navField=" + var4 + ")";
      }
   }
}
