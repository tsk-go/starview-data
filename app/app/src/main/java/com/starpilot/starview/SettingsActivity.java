package com.starpilot.starview;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.Voice;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.View.OnAttachStateChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts.OpenDocument;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsCompat.Type;
import java.io.File;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Deprecated;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.jvm.internal.Ref.BooleanRef;
import kotlin.jvm.internal.Ref.IntRef;
import kotlin.jvm.internal.Ref.ObjectRef;
import kotlin.random.Random;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.json.JSONObject;

public final class SettingsActivity extends AppCompatActivity implements GxHost {
   public static final SettingsActivity.Companion Companion = new SettingsActivity.Companion(null);
   public static final String EXTRA_HOST = "host";
   public static final String EXTRA_TAB = "tab";
   private static final HashMap<String, String> GALAXY_SUB_TITLES = new HashMap<>();
   private static final Map<String, String> GALAXY_TITLES;
   private static final Set<String> PLACED_PAGES = SetsKt.setOf(
      new String[]{
         "lane",
         "lane_change",
         "torque",
         "aol",
         "profiles",
         "conditional",
         "curve",
         "traffic",
         "aggressive",
         "standard",
         "relaxed",
         "slc",
         "vehicle",
         "wheel",
         "sounds",
         "sentry",
         "vasm",
         "data",
         "developer",
         "display",
         "appearance",
         "pip"
      }
   );
   private static final String STATE_AREA = "area";
   private static final String STATE_OPEN = "open";
   private static final String STATE_PAGE = "page";
   private GalaxyApi api;
   private int areaIndex;
   private String buildGroup;
   private FrameLayout contentHolder;
   private View current;
   private String currentPage;
   private TextView driveBtn;
   private final List<Pair<String, String>> driveModes;
   private final Handler handler;
   private FrameLayout headerSlot;
   private String host;
   private SettingsLauncher launcher;
   private DriveState.OffroadBar offroadBar;
   private Function1<? super Uri, Unit> onFilePicked;
   private Function1<? super String, Unit> onModelPicked;
   private final HashMap<String, View> panels;
   private final ActivityResultLauncher<String[]> pickAny;
   private final ActivityResultLauncher<String[]> pickModel;
   private FrameLayout root;
   private final Runnable ticker;
   private View togglesSearch;
   private CarUi ui;
   private final Runnable valuePoll;
   private String webReturn;

   // $VF: synthetic method
   public static void $r8$lambda$_5ulI6JKNdnF9tzIOrkchDJ7v6w/* $VF was: $r8$lambda$_5ulI6JKNdnF9tzIOrkchDJ7v6w*/(
      BooleanRef var0, Switch var1, SettingsActivity var2, CompoundButton var3, boolean var4
   ) {
      rtkFusionSection$lambda$247(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$_GtjsBd82kBLw8JN__uMGTkljgg/* $VF was: $r8$lambda$_GtjsBd82kBLw8JN__uMGTkljgg*/(
      VoiceStudio.Voice var0,
      IntRef var1,
      SettingsActivity var2,
      ObjectRef var3,
      List var4,
      TextView var5,
      BooleanRef var6,
      AtomicBoolean var7,
      TextView var8,
      ObjectRef var9
   ) {
      return tabletVoiceStudioPage$lambda$397$lambda$385(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9);
   }

   // $VF: synthetic method
   public static void $r8$lambda$_P_cpyZ0Kxx0aRegP94QlPr0Jc0/* $VF was: $r8$lambda$_P_cpyZ0Kxx0aRegP94QlPr0Jc0*/(
      File var0, SettingsActivity var1, DialogInterface var2, int var3
   ) {
      tabletBrakingPage$lambda$271$lambda$270(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$_T4L9FsqY84PWa_5j8iUXJdApKA/* $VF was: $r8$lambda$_T4L9FsqY84PWa_5j8iUXJdApKA*/(
      UiSettings var0, NewIdeas.Idea var1, SettingsActivity var2, boolean var3
   ) {
      return newIdeasPage$lambda$278(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static String $r8$lambda$_gQg45UPq5tWO1LvOqJpR_Jw1rM/* $VF was: $r8$lambda$_gQg45UPq5tWO1LvOqJpR_Jw1rM*/(
      SettingsActivity var0, VoiceStudio.Voice var1, AtomicBoolean var2, TextView var3
   ) {
      return tabletVoiceStudioPage$lambda$397$lambda$383$lambda$382(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static void $r8$lambda$_gTKKz4Fd6JdvczcQHzuJ7L8L80/* $VF was: $r8$lambda$_gTKKz4Fd6JdvczcQHzuJ7L8L80*/(View var0, SettingsActivity var1) {
      togglesPage$inset$lambda$121$lambda$120(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$_jQWz6LMSpm1RPtP7_WIrsi1PZY/* $VF was: $r8$lambda$_jQWz6LMSpm1RPtP7_WIrsi1PZY*/(
      SettingsActivity var0, DialogInterface var1, int var2
   ) {
      confirmRestart$lambda$64(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$0LhnbUz5sCJRhAkscbeg4X7Yz_E(UiSettings var0, boolean var1) {
      return tabletNavPage$lambda$255(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$0T5CAbR1eP6mgDUhbVREMbXEUY4(IntRef var0, VoiceStudio.Voice var1, SeekBar var2, TextView var3, SharedPreferences var4) {
      return tabletVoiceStudioPage$lambda$397$lambda$395(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$0nyJ59ogMZvcL_Q_HYOdq2DGIjY/* $VF was: $r8$lambda$0nyJ59ogMZvcL_Q_HYOdq2DGIjY*/(
      ObjectRef var0, String var1, SharedPreferences var2, ObjectRef var3, LinearLayout var4, SettingsActivity var5
   ) {
      return tabletVoiceStudioPage$fillFilter$lambda$371(var0, var1, var2, var3, var4, var5);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$1EBdOBmxvB_rctSm2Rd38JNOdB8(UiSettings var0, boolean var1) {
      return tabletBlinkerPage$lambda$319(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$1m0yWD7Oxc_2k2PumxXV2AEnolg(ObjectRef var0, SharedPreferences var1, float var2) {
      return tabletVoiceStudioPage$lambda$365(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$2B_0q8bHVniey4WL2nOxAEDz4V4/* $VF was: $r8$lambda$2B_0q8bHVniey4WL2nOxAEDz4V4*/(
      EditText var0, ObjectRef var1, IntRef var2, Function1 var3, GalaxyApi var4, SettingsActivity var5, FrameLayout var6
   ) {
      togglesPage$lambda$132(var0, var1, var2, var3, var4, var5, var6);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$2pIgbZtDCilxy7AVleUcgUvxLAM(SettingsActivity var0) {
      return tabletNavPage$lambda$267(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$2yTeEwX2xSFQo4pxC_SVhZbhlBA/* $VF was: $r8$lambda$2yTeEwX2xSFQo4pxC_SVhZbhlBA*/(float var0) {
      return tabletVoicePage$lambda$330(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$3IO1zZjh4ooZPEEGGgyhMAWrXAc(SettingsActivity var0) {
      techStreamPage$lambda$402$lambda$401(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$3Kd6VqjthUUKlKPu0RmsmktnVTY(SettingsActivity var0, String var1) {
      return catalogPage$lambda$92(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$3cHilTxNjswd1rRXenFKBhAh6yA(WebView var0, String var1, String var2) {
      techStreamPage$load$lambda$406$lambda$405(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$3lNW7wjFeybmcRz6FSkoQT23GIQ(ObjectRef var0, SettingsActivity var1, boolean var2, TextView var3, ObjectRef var4) {
      tabletVoiceStudioPage$loadCat$lambda$399(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$3pceCX0upVkwlsOulnbGzBo_N1w/* $VF was: $r8$lambda$3pceCX0upVkwlsOulnbGzBo_N1w*/(
      SettingsActivity var0, TextView var1, String var2
   ) {
      return tabletVoiceStudioPage$fillEngine$lambda$363$lambda$362$lambda$361$lambda$359(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$41rWTOnFg_N5yfh4k_V29i5DzYs/* $VF was: $r8$lambda$41rWTOnFg_N5yfh4k_V29i5DzYs*/(
      LinearLayout var0, SettingsActivity var1, String var2
   ) {
      tabletVoicePage$lambda$354(var0, var1, var2);
   }

   // $VF: synthetic method
   public static List $r8$lambda$46Uejya4DfW71VGi8efJH60OurY(SettingsActivity var0) {
      return showHome$lambda$31(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$4JDn3HCT56_ClL0GAEa_YVP7Cxo/* $VF was: $r8$lambda$4JDn3HCT56_ClL0GAEa_YVP7Cxo*/(SettingsActivity var0, Uri var1) {
      return tabletBackupPage$lambda$144$lambda$143(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$4Qjz_mCiSmEfB08abN8bWxrJPxs/* $VF was: $r8$lambda$4Qjz_mCiSmEfB08abN8bWxrJPxs*/(float var0) {
      return tabletVoicePage$lambda$324(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$4aAZTlmO9tro8h_a_r6Abob9bls/* $VF was: $r8$lambda$4aAZTlmO9tro8h_a_r6Abob9bls*/(SettingsActivity var0, LinearLayout var1) {
      return galaxyPage$lambda$15(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$4o44AAfW1w5rxPTTZnn_auwzqyM(
      Function1 var0, Float var1, SeekBar var2, float var3, float var4, TextView var5, Function1 var6, Function0 var7
   ) {
      return slider$lambda$274(var0, var1, var2, var3, var4, var5, var6, var7);
   }

   // $VF: synthetic method
   public static void $r8$lambda$5IblBtTtCM5u1xAzTidj1Sv6LVQ(ObjectRef var0, LinearLayout var1, SettingsActivity var2, TextView var3) {
      tabletVoiceStudioPage$fillEngine$lambda$363$lambda$362$lambda$361$lambda$360(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static String $r8$lambda$5QSOYHPVO3Yv7zQwSXwdaZfdakk(UiSettings var0) {
      return tabletThemePage$lambda$163(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$6BdoYXmCY83kPOmU09AuZkHCkpI(SettingsActivity var0, LinearLayout var1, String var2) {
      return tabletVoicePage$lambda$353(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$6DeaYCn7rnRMEe82cA55WOivEn0(Function0 var0, float var1, Function1 var2, float var3, float var4, TextView var5) {
      return stepperCard$lambda$160(var0, var1, var2, var3, var4, var5);
   }

   // $VF: synthetic method
   public static void $r8$lambda$6MSlFZha3jZYo5UHyD3SyUXn764(CatalogPanel var0, SettingsActivity var1) {
      catalogPage$lambda$94$lambda$93(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$6VqsFZz1pzNnozefHnY5EqNfhPo(
      VoiceStudio.Voice var0, SettingsActivity var1, AtomicBoolean var2, TextView var3, BooleanRef var4, TextView var5, ObjectRef var6
   ) {
      return tabletVoiceStudioPage$lambda$397$lambda$383(var0, var1, var2, var3, var4, var5, var6);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$6lZa4iSkRPreRi3rO8o_FS6XO4g(MapdMaps.State var0) {
      return tabletMapsPage$refresh$lambda$185(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$6vlsPlfQpxFi2PT57AHikW_QQ3M/* $VF was: $r8$lambda$6vlsPlfQpxFi2PT57AHikW_QQ3M*/(SettingsActivity var0, Uri var1) {
      pickModel$lambda$2(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$6xHyB9ygHDFg_l_IihJkjNJAPOs(UiSettings var0, boolean var1) {
      return tabletBlinkerPage$lambda$302(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$6z_aqWOJtvQ01bR4Jj2wHtyUZmE/* $VF was: $r8$lambda$6z_aqWOJtvQ01bR4Jj2wHtyUZmE*/(SettingsActivity var0, File var1) {
      return tabletBrakingPage$lambda$271(var0, var1);
   }

   // $VF: synthetic method
   public static Ntrip.Config $r8$lambda$7_toiDnvgm6STiLiZ7N9zJc8qzc/* $VF was: $r8$lambda$7_toiDnvgm6STiLiZ7N9zJc8qzc*/(Ntrip.Config var0, String var1) {
      return backupCasterSection$lambda$228(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$7KSEdN91_lWjaM1zPt1HCR5ULEY(SettingsActivity var0, TextView var1, ObjectRef var2, LinearLayout var3) {
      return tabletVoiceStudioPage$fillEngine$lambda$363(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static void $r8$lambda$7QsNW80bfzKkMo6w7KlvZLGcfnU(ObjectRef var0) {
      tabletVoiceStudioPage$loadCat$lambda$399$lambda$398(var0);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$7YrptNnrhM0es3CeHKNbhafPTW0(UiSettings var0) {
      return tabletBlinkerPage$lambda$318(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$7kpotOb5Kb5w_gYAJ963OZfKWyc(TextView var0, String var1) {
      tabletVoiceStudioPage$say$lambda$357(var0, var1);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$8JnWgT7Kr2bvoBZSAL0c0XewnOY(UiSettings var0) {
      return tabletBlinkerPage$lambda$320(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$8NtkSmxk_K1_Djj7DeQ_f0xU6dQ/* $VF was: $r8$lambda$8NtkSmxk_K1_Djj7DeQ_f0xU6dQ*/(SettingsActivity var0, String var1) {
      return languagePage$lambda$139$lambda$138(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$8Qi7cB0NFRfvW_LDX3Oc0SbORTQ/* $VF was: $r8$lambda$8Qi7cB0NFRfvW_LDX3Oc0SbORTQ*/(SettingsActivity var0) {
      return showHome$lambda$28(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$8cJpU1JhJ5F1lItaZ12PUjiVtaw(float var0) {
      return tabletVoicePage$lambda$333(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$8sUzoAougRIZn39mrLpJLlj9dCI(UiSettings var0, boolean var1) {
      return tabletBlinkerPage$lambda$321(var0, var1);
   }

   // $VF: synthetic method
   public static String $r8$lambda$93WumBefHiGjvLATeAMDx3wPvH8(SettingsActivity var0) {
      return tabletRtkPage$lambda$201(var0);
   }

   // $VF: synthetic method
   public static String $r8$lambda$9F7quAuZFEeOz4m0FDak3_pu9Kw(SettingsActivity var0) {
      return tabletRtkPage$lambda$199(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$9QtVE5i7RdAK12Oeb_N86v_KA5w(SettingsActivity var0, Uri var1, TextView var2, ObjectRef var3, LinearLayout var4) {
      tabletVoiceStudioPage$fillEngine$lambda$363$lambda$362$lambda$361(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$9Z5qVxA_tx4Z4VDNa9eElJN0TPc(TextView var0, SettingsActivity var1, List var2, String var3) {
      return tabletRtkPage$lambda$212$lambda$211(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static void $r8$lambda$9cY1uF0hBlUEjHTd_y5_cO2SeG8/* $VF was: $r8$lambda$9cY1uF0hBlUEjHTd_y5_cO2SeG8*/(
      IntRef var0, ObjectRef var1, HashMap var2, FrameLayout var3, SettingsActivity var4, GalaxyApi var5, ArrayList var6, String var7
   ) {
      subTabbedCatalog$lambda$105(var0, var1, var2, var3, var4, var5, var6, var7);
   }

   // $VF: synthetic method
   public static void $r8$lambda$9dFh9rYUtfdS8F1LcQP1neXfB8s(Function0 var0, View var1) {
      headerBar$lambda$49$lambda$48(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$9hUx4t4ZwlRuH4xAihO48YjKn_Q(SettingsActivity var0, String var1) {
      return languagePage$lambda$139(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$9rWWGVqFEUXLhljboyQJUHSotP8(
      Function2 var0, EditText var1, TextView var2, Function0 var3, SettingsActivity var4, DialogInterface var5, int var6
   ) {
      backupCasterSection$field$226$lambda$225$lambda$224(var0, var1, var2, var3, var4, var5, var6);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$A72OdmdfsLG14pevNG8n7DE1stw(SettingsActivity var0, List var1) {
      return tabletBrakingPage$lambda$269(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$AZWXdvSPvUnrKlKMu2WmINFzJz0(TextView var0, String var1, TextView var2, ObjectRef var3) {
      tabletVoiceStudioPage$job$lambda$377$lambda$376(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$B_m_hzTHYvSx_RJ2S_cL1OlwUFA/* $VF was: $r8$lambda$B_m_hzTHYvSx_RJ2S_cL1OlwUFA*/(
      SettingsActivity var0, LinearLayout var1, String var2, Uri var3
   ) {
      return tabletVoicePage$fill$lambda$348$lambda$347(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static void $r8$lambda$Bwff5U16YDZZ2_ocXLuzhpNGeVs/* $VF was: $r8$lambda$Bwff5U16YDZZ2_ocXLuzhpNGeVs*/(
      MapdMaps.State var0, DialogInterface var1, int var2
   ) {
      tabletMapsPage$refresh$lambda$187$lambda$186(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$CdX1DIXboAdVrhdH9qHbIfzf7_U(SettingsActivity var0, String var1, DialogInterface var2, int var3) {
      confirmDriveState$lambda$67(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Cm_Mn7hagohPBGPgXZrwdNP30sw/* $VF was: $r8$lambda$Cm_Mn7hagohPBGPgXZrwdNP30sw*/(MapdMaps.State var0) {
      return tabletMapsPage$refresh$lambda$184(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$CmoX_bJaFcwbtN01U07HS4QVX9Q(AudioManager var0, int var1, int var2, SettingsActivity var3, TextView var4) {
      return tabletVoicePage$lambda$336(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$D_5C2jmLdGGejiTwPOLB9ZjvC14/* $VF was: $r8$lambda$D_5C2jmLdGGejiTwPOLB9ZjvC14*/(UiSettings var0) {
      return tabletBlinkerPage$lambda$295(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$D8syXKC_pBHOp87GyU0tBPrGELc(Function0 var0, View var1) {
      headerBar$lambda$47$lambda$46(var0, var1);
   }

   // $VF: synthetic method
   public static String $r8$lambda$DpZXsHRohxMd22AceoVcklpT800(SettingsActivity var0) {
      return tabletRtkPage$lambda$203(var0);
   }

   // $VF: synthetic method
   public static String $r8$lambda$EKQfSuaDzNNv_8dtgQdizepY8nQ/* $VF was: $r8$lambda$EKQfSuaDzNNv_8dtgQdizepY8nQ*/(SettingsActivity var0) {
      return backupCasterSection$lambda$229(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$ELpopWHLDgY3lfZTXs4doNrcsrs(Switch var0) {
      return rtkFusionSection$lambda$247$lambda$246(var0);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$EOqZH4sMO01BWg6Yvs3Bt_PZxY4/* $VF was: $r8$lambda$EOqZH4sMO01BWg6Yvs3Bt_PZxY4*/(SettingsActivity var0) {
      return tabletNavPage$lambda$264(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$FARpgj2EdYUNmo9m5F6pq14UZp4(List var0) {
      return tabletMapsPage$refresh$lambda$182(var0);
   }

   // $VF: synthetic method
   public static String $r8$lambda$Fl1Q8HJfF2SDbPFwMZMC_QzdniA/* $VF was: $r8$lambda$Fl1Q8HJfF2SDbPFwMZMC_QzdniA*/(String var0) {
      return languagePage$lambda$137(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$FlMnfDnOUTRsBU1hjqLd50vAOMg(SettingsActivity var0, String var1) {
      pickModel$lambda$2$lambda$1$lambda$0(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$FrIhfd3FlqNnusnbCy8L_ARGqZg/* $VF was: $r8$lambda$FrIhfd3FlqNnusnbCy8L_ARGqZg*/(SettingsActivity var0) {
      newIdeasPage$lambda$286$lambda$285(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$FxnZ_8dKjvrmluwU0UB77d2JPkg(TextView var0, String var1, SettingsActivity var2, LaneModel var3) {
      tabletViewPage$lambda$173$lambda$172$lambda$171$lambda$170(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$G72ilRaxznnxyWKjqNsRdzFsYeM(AtomicBoolean var0) {
      return tabletVoiceStudioPage$lambda$355(var0);
   }

   // $VF: synthetic method
   public static float $r8$lambda$GLhusIy2dJ258212tHBvA076754(UiSettings var0) {
      return uiGroupPage$lambda$157(var0);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$GWa9HAUTpiuCjzHhF5M8O1LLStY(UiSettings var0, NewIdeas.Idea var1) {
      return newIdeasPage$lambda$277(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$HMkjf4hEMcXCHzdkR_Hv9Ok9BIM/* $VF was: $r8$lambda$HMkjf4hEMcXCHzdkR_Hv9Ok9BIM*/(
      SettingsActivity var0, Function2 var1, EditText var2, TextView var3, Function0 var4, DialogInterface var5, int var6
   ) {
      tabletRtkPage$field$lambda$198$lambda$197(var0, var1, var2, var3, var4, var5, var6);
   }

   // $VF: synthetic method
   public static void $r8$lambda$HfMKibHkRjz9upKJJooYVz2rZiA(SettingsActivity var0, Uri var1, LinearLayout var2, String var3) {
      tabletVoicePage$fill$lambda$348$lambda$347$lambda$346(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$I9_aCVpeUmrsH9XGHivCxaqntw0/* $VF was: $r8$lambda$I9_aCVpeUmrsH9XGHivCxaqntw0*/(UiSettings var0, float var1) {
      return tabletBlinkerPage$lambda$311(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$IFDnJA7RfSKN89b4zfP03IL60wQ(SettingsActivity var0) {
      return tabletBackupPage$lambda$146(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$IJy_YtA5LImh2ErwSocvhxAxEpY(SettingsActivity var0) {
      return newIdeasPage$lambda$289(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$IL_ZYPrwFqBMZobAjxogL73Zwos/* $VF was: $r8$lambda$IL_ZYPrwFqBMZobAjxogL73Zwos*/(
      UiSettings var0, String var1, SettingsActivity var2, boolean var3
   ) {
      return tabletNavPage$lambda$263(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static void $r8$lambda$IwSy8NdsF6xuc8t_v_BwPMhzVQA(SettingsActivity var0, LinearLayout var1, SimpleDateFormat var2) {
      tabletMapsPage$lambda$189$lambda$188(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$IxJxd8F6EJzZRjf7mmANv7Iga8k(UiSettings var0, boolean var1) {
      return tabletBlinkerPage$lambda$306(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$IxYAtLeUsSaOyXBneir4yxpGEPU() {
      return tabletBlinkerPage$lambda$312();
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Jh5cLjPIlOyPh6D_xlRmUs0ZTtM(VoiceStudio.Voice var0, SettingsActivity var1, TextView var2, long var3, long var5) {
      return tabletVoiceStudioPage$lambda$397$lambda$383$lambda$382$lambda$380(var0, var1, var2, var3, var5);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$KEgEeFL_DwCLDEWU85Bgh8jrp2U(SettingsActivity var0) {
      return tabletConnectionPage$lambda$191(var0);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$KMau_vlMWXaA7D66D_WKJWCHIOE/* $VF was: $r8$lambda$KMau_vlMWXaA7D66D_WKJWCHIOE*/(UiSettings var0) {
      return tabletBlinkerPage$lambda$301(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$KRIeehvlkK9TgM864WANasycOug(
      LinearLayout var0,
      SettingsActivity var1,
      ObjectRef var2,
      SharedPreferences var3,
      ObjectRef var4,
      AtomicBoolean var5,
      TextView var6,
      BooleanRef var7,
      TextView var8,
      ObjectRef var9,
      ObjectRef var10,
      List var11
   ) {
      return tabletVoiceStudioPage$lambda$397(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$KWOWZKRpiKs0_KUhefODIqgL8wE(SettingsActivity var0, String var1, String var2) {
      return buildPageUnsafe$lambda$79(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$KY0mxtLgfJow7IRk5DNpn6LsxNE(TextView var0, String var1) {
      return tabletViewPage$lambda$169$lambda$168(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$M5FFv2QTKJ1ENzuUUJQVNpohiQI() {
      return tabletVoiceStudioPage$lambda$369();
   }

   // $VF: synthetic method
   public static SettingIcons.Icon $r8$lambda$MQZ4XJlI646eBYKhfL1SiGl6XaU(SettingsActivity var0, String var1) {
      return onCreate$lambda$21$lambda$20(var0, var1);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$OFloG4tZFw_cGyRfhKY8FKm7hVU(UiSettings var0, UiSettings.Item var1) {
      return tabletViewPage$lambda$166(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$OlF219l3zcRxsiiHKUkF7OmQlKI(SettingsActivity var0) {
      return tabletBlinkerPage$lambda$317$lambda$314(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$OsaMuasGzNYNX3VwFvELf4v1k6Q() {
      return tabletVoicePage$lambda$334();
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$PHO4F0A4YSHlkipAGuYyFHJsqNk(SettingsActivity var0) {
      return newIdeasPage$lambda$280(var0);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$P_4K6J7LA1SCkHKwXU5YxggZ7LA(UiSettings var0, String var1) {
      return tabletNavPage$lambda$262(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Q4Pp8ZVF8HfXUZ_pTLJEiT_VSFE/* $VF was: $r8$lambda$Q4Pp8ZVF8HfXUZ_pTLJEiT_VSFE*/(SettingsActivity var0, boolean var1) {
      return tabletNavPage$lambda$265(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$QDDR_w_9sKZAGaHu0veZo1t9Vaw/* $VF was: $r8$lambda$QDDR_w_9sKZAGaHu0veZo1t9Vaw*/(
      SettingsActivity var0, LinearLayout var1, SimpleDateFormat var2
   ) {
      return tabletMapsPage$lambda$189(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$QnidDnK_Z4sz1h6cvYZSk5h6N5g/* $VF was: $r8$lambda$QnidDnK_Z4sz1h6cvYZSk5h6N5g*/(
      Function1 var0, List var1, ObjectRef var2, int var3
   ) {
      return choiceCard$lambda$151(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Qym1_L5I6ibF5_9hl5LG1e42Tvw() {
      return tabletBlinkerPage$lambda$317$lambda$315();
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$R_KtvTARkvJnJ4m4Oh_HvYlr3WM/* $VF was: $r8$lambda$R_KtvTARkvJnJ4m4Oh_HvYlr3WM*/(
      SettingsActivity var0, String var1, boolean var2, Function0 var3, String var4, boolean var5, Function2 var6, TextView var7
   ) {
      return tabletRtkPage$field$lambda$198(var0, var1, var2, var3, var4, var5, var6, var7);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$R6hDyoe9rO9lvIm52wGOL6ovhwc(UiSettings var0, float var1) {
      return uiGroupPage$lambda$158(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$RDzsNbrFvQdfp8HykTe3gOWutwI(EditText var0, TextView var1, SettingsActivity var2, DialogInterface var3, int var4) {
      backupCasterSection$lambda$240$lambda$239(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$REkKTucRWnNfGYiSXjTN_8WN4jw(SettingsActivity var0) {
      return tabletRtkPage$lambda$192(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$RWZi9bcnaIMf1eEKnj1Zho00ECM(SettingsActivity var0, MapdMaps.State var1) {
      return tabletMapsPage$refresh$lambda$187(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$SHoDrnK94_YesUQ36t4Du5q9YsA/* $VF was: $r8$lambda$SHoDrnK94_YesUQ36t4Du5q9YsA*/(
      Function0 var0, float var1, Function1 var2, float var3, float var4, TextView var5
   ) {
      return stepperCard$lambda$162(var0, var1, var2, var3, var4, var5);
   }

   // $VF: synthetic method
   public static String $r8$lambda$SQCV_xo4IEskpNNRZlLi6lnlhmQ(
      SettingsActivity var0, VoiceStudio.Voice var1, IntRef var2, ObjectRef var3, List var4, TextView var5
   ) {
      return tabletVoiceStudioPage$lambda$397$lambda$385$lambda$384(var0, var1, var2, var3, var4, var5);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$SeZtWAWXZWcKmNzIx8OSQwbttXk(SettingsActivity var0, AmyVoice.Pack var1, LinearLayout var2, String var3) {
      return tabletVoicePage$fill$lambda$343(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$TMh05Wrzk6TexR1zEIAxeyn7Seg() {
      return tabletVoicePage$lambda$328();
   }

   // $VF: synthetic method
   public static void $r8$lambda$TgaBPoKlYds3xFYNh5xH_dXof5E(SettingsActivity var0, String var1, WebView var2) {
      techStreamPage$load$lambda$406(var0, var1, var2);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$TyjIsajS8Q54PupiEhO2e_HemlE(UiSettings var0) {
      return tabletBlinkerPage$lambda$305(var0);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$ULCDV9ufQbsfM3gz9yN8uEjsAWE(UiSettings var0) {
      return tabletNavPage$lambda$258(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$UYCk8si8dC03nd47SNXuARyw55w(SettingsActivity var0, int var1) {
      newIdeasPage$lambda$286$lambda$285$lambda$284(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Ui9ZA4Rw7z7sZ1Z2vlnbnqqEMBo(SettingsActivity var0, boolean var1) {
      return tabletRtkPage$lambda$193(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$V1Xizz65LyuDf50Y3_4F19ZRW4I/* $VF was: $r8$lambda$V1Xizz65LyuDf50Y3_4F19ZRW4I*/(
      TextView var0, String var1, SettingsActivity var2
   ) {
      tabletViewPage$lambda$177$lambda$176$lambda$175$lambda$174(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$V5UtMTxqfauEQ20gMKb6OhHDxyc(SettingsActivity var0, boolean var1) {
      return backupCasterSection$lambda$220(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$V7iZj_wzx1ICJOJprnJiHiPMYuA/* $VF was: $r8$lambda$V7iZj_wzx1ICJOJprnJiHiPMYuA*/(
      IntRef var0, VoiceStudio.Voice var1, SeekBar var2, TextView var3, SharedPreferences var4
   ) {
      return tabletVoiceStudioPage$lambda$397$lambda$394(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static String $r8$lambda$Vf_nrgXOvRnQhIy8_Glyoeyaum8/* $VF was: $r8$lambda$Vf_nrgXOvRnQhIy8_Glyoeyaum8*/(float var0) {
      return tabletVoicePage$lambda$329(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Vknv03vpyt6_G6DL0sCthqaNNNE/* $VF was: $r8$lambda$Vknv03vpyt6_G6DL0sCthqaNNNE*/(UiSettings var0, float var1) {
      return tabletBlinkerPage$lambda$309(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$W90JQHyza2Haes24C2fJy_YvPK4(SettingsActivity var0, String var1) {
      onCreate$lambda$26$lambda$25(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$WkKDdXi6VCjJ3W40eDo_cj8j3Ck/* $VF was: $r8$lambda$WkKDdXi6VCjJ3W40eDo_cj8j3Ck*/(ObjectRef var0) {
      togglesPage$lambda$133(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$X3CZ9B22Et5QK3ck2ZFtE_F9oL8(SettingsActivity var0, TextView var1) {
      return tabletViewPage$lambda$177(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$XBsQpmzPmiX6hVYxoG4ICM4uETk() {
      return tabletVoiceStudioPage$lambda$366();
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$XCDLBhR_VSR5x0DAYkfXeZbkCl0/* $VF was: $r8$lambda$XCDLBhR_VSR5x0DAYkfXeZbkCl0*/(SettingsActivity var0) {
      return headerBar$lambda$54(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$XMjMdqSThISGxZz1MXVOP3YixAo(SettingsActivity var0, String var1, String var2) {
      return galaxyPage$lambda$14(var0, var1, var2);
   }

   // $VF: synthetic method
   public static String $r8$lambda$Y18WY3jsHxsXq4vkAuOrCBTDvpI(float var0) {
      return tabletVoiceStudioPage$lambda$364(var0);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$YTMhLR3guYG2PEwGAc7jHQKUD_w(UiSettings var0) {
      return tabletBlinkerPage$lambda$303(var0);
   }

   // $VF: synthetic method
   public static List $r8$lambda$ZVMa5EkaFJmREWhTlNIEhPQBxkw(SettingsActivity var0) {
      return showHome$lambda$29(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$ZWKGbq2NvtZmNBlg0bcfTdWOkrI(ArrayList var0, SettingsActivity var1, TextView var2, SettingsActivity.Rail var3, View var4) {
      showArea$lambda$75(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$_K5__rBzQLLZfysrhdh_xTpEu7s/* $VF was: $r8$lambda$_K5__rBzQLLZfysrhdh_xTpEu7s*/(
      SettingsActivity var0, VoiceStudio.Voice var1, ObjectRef var2, TextView var3
   ) {
      return tabletVoiceStudioPage$lambda$397$lambda$390(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Ntrip.Config $r8$lambda$_Nw8oJ_v7XM8Tjsy9ccQ27l_nr4(Ntrip.Config var0, String var1) {
      return tabletRtkPage$lambda$202(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$_Oa_FsxrFtWnZbSNjEFUBgkleRw(SettingsActivity var0, LinearLayout var1, String var2) {
      return tabletVoicePage$fill$lambda$348(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Ntrip.Config $r8$lambda$_S6HZEWa4eO_Gq9Bblskr6jJbs8/* $VF was: $r8$lambda$_S6HZEWa4eO_Gq9Bblskr6jJbs8*/(Ntrip.Config var0, String var1) {
      return tabletRtkPage$lambda$206(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$_TQ_GVsI8zkahVCA00CX19qcvfQ(RtkTools.Tool var0, SettingsActivity var1, DialogInterface var2, int var3) {
      rtkFusionSection$lambda$249$lambda$248(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$__x5V28hsHzqttT4_qxBg_tvzjU/* $VF was: $r8$lambda$__x5V28hsHzqttT4_qxBg_tvzjU*/(UiSettings var0) {
      return tabletNavPage$lambda$260(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$_iZNWq_EiJASClQjbYDQwJFQPKw/* $VF was: $r8$lambda$_iZNWq_EiJASClQjbYDQwJFQPKw*/(
      ObjectRef var0, SettingsActivity var1, TextView var2, ObjectRef var3
   ) {
      return tabletVoiceStudioPage$lambda$400(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static void $r8$lambda$_vsGI4DtVuSbymzgMRysym_pNc4(EditText var0, SettingsActivity var1, View var2) {
      togglesPage$lambda$113$lambda$112(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$_xarIE2_xkgjA5Jy5VG9C7TPe_c/* $VF was: $r8$lambda$_xarIE2_xkgjA5Jy5VG9C7TPe_c*/(
      WebView var0, SettingsActivity var1, String var2
   ) {
      techStreamPage$lambda$407(var0, var1, var2);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$b1x4Zy346_yJetQy0cX3b6qITLo(SettingsActivity var0) {
      return backupCasterSection$lambda$219(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$bHcYAjgKWOqohQThb_f_HVqThbQ/* $VF was: $r8$lambda$bHcYAjgKWOqohQThb_f_HVqThbQ*/(
      SettingsActivity var0, LaneModel var1, TextView var2
   ) {
      return tabletViewPage$lambda$173(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$bMO9hvKl6rGz6YegVIiqu8HPd3g(
      AmyVoice.Pack var0, LinearLayout var1, SettingsActivity var2, String var3, DialogInterface var4, int var5
   ) {
      tabletVoicePage$fill$lambda$343$lambda$342(var0, var1, var2, var3, var4, var5);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$bjIebzDBRgwdL49o1AQMMQlSG6g(SettingsActivity var0) {
      return tabletViewPage$lambda$178(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$bwY246txIRumg1M8f5Y3xfhBor4(UiSettings var0, boolean var1) {
      return tabletBlinkerPage$lambda$308(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$cAjwt1iwPwZ4WPZaEjoYOkxWrRM(SettingsActivity var0) {
      return confirmDriveState$lambda$67$lambda$66$lambda$65(var0);
   }

   // $VF: synthetic method
   public static String $r8$lambda$cQGZowPJpIVUOnd0T9FJhwmVA7o(SettingsActivity var0) {
      return backupCasterSection$lambda$227(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$cvNWbnBTx1ye8RFrw2YoZhf9aNY(SettingsActivity var0, RtkTools.Tool var1) {
      return rtkFusionSection$lambda$249(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$cz4In_Rof_L5EIALS3z_B5Utdmo(SettingsActivity var0, LinearLayout var1) {
      return galaxyPage$lambda$16(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$dEdSak4P2YcJH6bxz_LVlJO1tIY/* $VF was: $r8$lambda$dEdSak4P2YcJH6bxz_LVlJO1tIY*/(
      Function1 var0, CompoundButton var1, boolean var2
   ) {
      toggleCard$lambda$153(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$dGzoX2WOo4JWmeFlV783YnpdPTY(
      Function1 var0, List var1, HashMap var2, List var3, SettingsActivity var4, FrameLayout var5, int var6
   ) {
      return togglesPage$lambda$124(var0, var1, var2, var3, var4, var5, var6);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$dHCMmNK2y5tdiXaVSaKDaWkG6mg(SettingsActivity var0) {
      return buildPageUnsafe$lambda$84$lambda$83(var0);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$dHGrKBpJwnYTJQnpwG4f2RwTWi4(UiSettings var0) {
      return tabletNavPage$lambda$254(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$dTLwC1L5RBlVY0bMkvnN8bqOiqk(IntRef var0, SeekBar var1, TextView var2, SharedPreferences var3, VoiceStudio.Voice var4) {
      return tabletVoiceStudioPage$lambda$397$lambda$393(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$dV3Dddve9ai71pSxmwfk6loR6KM(UiSettings var0) {
      return tabletNavPage$lambda$256(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$ecpxCXaciIf_LiCxyhlaLkI8MoY(LinearLayout var0, SettingsActivity var1, String var2) {
      tabletVoicePage$lambda$353$lambda$352(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$etyotGJIpp5gDNYriS4RD7y00Qc(LaneModel var0, SettingsActivity var1, TextView var2, Uri var3) {
      return tabletViewPage$lambda$173$lambda$172(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static void $r8$lambda$fI27WklimTiA28PQcewJ2I3Hhoc(
      Function0 var0, BooleanRef var1, SettingsActivity var2, TextView var3, TextView var4, ObjectRef var5
   ) {
      tabletVoiceStudioPage$job$lambda$377(var0, var1, var2, var3, var4, var5);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$fNr3F8731lh6ezcsvQj90_6QWf0/* $VF was: $r8$lambda$fNr3F8731lh6ezcsvQj90_6QWf0*/(SettingsActivity var0, boolean var1) {
      return confirmRestart$lambda$64$lambda$63(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$ffs64np_sy8Cs7_pp_S5hgHnM2g/* $VF was: $r8$lambda$ffs64np_sy8Cs7_pp_S5hgHnM2g*/(
      SettingsActivity var0, List var1, TextView var2, DialogInterface var3, int var4
   ) {
      tabletRtkPage$lambda$212$lambda$211$lambda$210(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$fjfonyE9uwDnaEWCIWyPEfh98Uo(SettingsLauncher var0, SettingsIndex.Entry var1) {
      return showHome$lambda$32(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$fr43sMS3EL1EmBAF0IntTbjq2LI(SettingsActivity var0, String var1) {
      return showHome$lambda$33(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$ftAV95_Um_3ZHjNMgRh2dBFVCJ8() {
      return tabletVoicePage$lambda$325();
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$g3Wv7dk2DyKHSIGEsCVWXE1Xcdw(UiSettings var0) {
      return tabletBlinkerPage$lambda$307(var0);
   }

   // $VF: synthetic method
   public static Ntrip.Config $r8$lambda$gF_uUpyWaO8kz6v_tyg3gJjRGQs/* $VF was: $r8$lambda$gF_uUpyWaO8kz6v_tyg3gJjRGQs*/(Ntrip.Config var0, String var1) {
      return backupCasterSection$lambda$230(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$gPUmps5r2usmdm13Htt22DqSmxc(UiSettings var0, boolean var1) {
      return tabletBlinkerPage$lambda$298(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$gcBN0iJsVNwSsqVcByP2kPZnhYM(SettingsActivity var0) {
      return driveStateSwitch$lambda$59(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$gjDzebw_FSKowpZGfu7tgy9KxfA/* $VF was: $r8$lambda$gjDzebw_FSKowpZGfu7tgy9KxfA*/(SettingsActivity var0) {
      return newIdeasPage$lambda$281(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$gp_vnrES5GoqJ5ymwqkakyaMogY/* $VF was: $r8$lambda$gp_vnrES5GoqJ5ymwqkakyaMogY*/(UiSettings var0, String var1) {
      return tabletThemePage$lambda$164(var0, var1);
   }

   // $VF: synthetic method
   public static String $r8$lambda$hHMkxD6aZYiKp1NUNlHGu_oCT0Q/* $VF was: $r8$lambda$hHMkxD6aZYiKp1NUNlHGu_oCT0Q*/(float var0) {
      return tabletVoicePage$lambda$326(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$hge9NQnAH1Cl730pF89IU8OPjjw(Voice var0, LinearLayout var1, SettingsActivity var2, String var3) {
      return tabletVoicePage$fill$lambda$351(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static void $r8$lambda$hhErm5LUkspkRXDWcjmdoEOQ8bY(SettingsActivity var0, int var1) {
      newIdeasPage$lambda$289$lambda$288$lambda$287(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$huKkyrri_ggzEd9yF5LiOa6_kGQ/* $VF was: $r8$lambda$huKkyrri_ggzEd9yF5LiOa6_kGQ*/(
      SettingsActivity var0, String var1, LinearLayout var2, String var3
   ) {
      tabletVoicePage$fill$lambda$348$lambda$347$lambda$346$lambda$345(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Ntrip.Config $r8$lambda$i3kbtRR4j3wxxChBFsWqgQ3kSk4(Ntrip.Config var0, String var1) {
      return tabletRtkPage$lambda$204(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$iApjxT4Ikg6yrzJ0MC1oVdcVSBI(
      SettingsActivity var0, String var1, Function0 var2, String var3, boolean var4, Function2 var5, TextView var6
   ) {
      return backupCasterSection$field$226$lambda$225(var0, var1, var2, var3, var4, var5, var6);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$iLmNUE956OxKDcz23KrVKjLBuz0(Voice var0, String var1) {
      return tabletVoicePage$fill$lambda$350(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$iq7p3cAQgzfHCJGUeIwQI763ntM(SettingsActivity var0) {
      return newIdeasPage$lambda$286(var0);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$j7JxAKMsuD_EXiwyyxwbxvNTS_k(UiSettings var0) {
      return tabletBlinkerPage$lambda$297(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$j9MUYAqf12IVil3Fyh5yaQvg8Ng(
      VoiceStudio.Voice var0,
      IntRef var1,
      SettingsActivity var2,
      ObjectRef var3,
      AtomicBoolean var4,
      TextView var5,
      BooleanRef var6,
      TextView var7,
      ObjectRef var8
   ) {
      return tabletVoiceStudioPage$lambda$397$lambda$389(var0, var1, var2, var3, var4, var5, var6, var7, var8);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$jUUTkcfwfwDLsZl_KYzDBiucS0A/* $VF was: $r8$lambda$jUUTkcfwfwDLsZl_KYzDBiucS0A*/(TextView var0, SettingsActivity var1) {
      return backupCasterSection$lambda$236(var0, var1);
   }

   // $VF: synthetic method
   public static String $r8$lambda$jVrl5tPceV65hWfgXZRAZhXeVvg(SettingsActivity var0) {
      return tabletRtkPage$lambda$205(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$jr6sWDOF8Z1GUN1ZiNHNofnkfWM(UiSettings var0, boolean var1) {
      return tabletNavPage$lambda$259(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$kI1JpM_siwSbk9M3Nbv09TrG1ZE(SettingsActivity var0, TextView var1) {
      return tabletViewPage$lambda$169(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$kQfJsagNxBOVLNCsy4ItsGjxT7o(UiSettings var0, boolean var1) {
      return tabletBlinkerPage$lambda$296(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$kVJ3PnD9mUuFxNhH66NleBJDnXM(SettingsActivity var0, String var1) {
      return togglesPage$lambda$132$lambda$129(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$kXwyRX2m3WbUJXvqiDFMh1vtTYQ(SettingsActivity var0, Uri var1) {
      pickAny$lambda$3(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$lIDTJk9FEdIfODSooYD4VnMUkmo(UiSettings var0, UiSettings.Item var1, boolean var2) {
      return tabletViewPage$lambda$167(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$lRHUNZ4BURV22aZkUYUxJf6qmA8(SettingsActivity var0, String var1) {
      return subTabbedCatalog$select$lambda$102$lambda$101(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$lrlZSiV05gupxbkkAB9a5M3bCg4(IntRef var0, EditText var1, ObjectRef var2, int var3) {
      return togglesPage$lambda$117(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Ntrip.Config $r8$lambda$m6quxxzHo08mjsuGstp5HHVX6iY(Ntrip.Config var0, String var1) {
      return backupCasterSection$lambda$234(var0, var1);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$mJ4G0kIXHS0l3wCQ5OLJWnna38U(SettingsActivity var0) {
      return backupCasterSection$lambda$217(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$mRSIEA5ZPsIEUsOP9smJpxWOsGU(SettingsActivity var0) {
      return tabletNavPage$lambda$253(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$mtBAPqmGEP8YajPLSIyg9xBZGtU(AmyVoice.Pack var0, LinearLayout var1, SettingsActivity var2, String var3) {
      return tabletVoicePage$fill$lambda$341(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static void $r8$lambda$mtsSvnevyOqNDkro_ReGh_wBn2g/* $VF was: $r8$lambda$mtsSvnevyOqNDkro_ReGh_wBn2g*/(SettingsActivity var0, View var1) {
      togglesPage$lambda$109(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$n75sSHC8c4FGtjBNmDo0qUb8ed4(UiSettings var0, boolean var1) {
      return tabletBlinkerPage$lambda$304(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$nL0Mn5WZsn8IL5Ae89Z3ObftDm4(float var0) {
      return tabletVoicePage$lambda$327(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$nMZKKUI966PxxNNqGFcia8zhzJA(SettingsActivity var0, Uri var1) {
      pickModel$lambda$2$lambda$1(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$n_78n40tstJNoS4zWlIvh2Evi8Y(AudioManager var0, int var1, int var2, SettingsActivity var3, TextView var4) {
      return tabletVoicePage$lambda$335(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static void $r8$lambda$nrVX5ie5klLypmylYRNfE2knomI(SettingsActivity var0, View var1, String var2) {
      catalogPage$lambda$87(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$o0sjvJD15H4DikMhcF41b9l38Zs(
      IntRef var0, ObjectRef var1, HashMap var2, FrameLayout var3, SettingsActivity var4, GalaxyApi var5, ArrayList var6, String var7, int var8
   ) {
      return subTabbedCatalog$lambda$104(var0, var1, var2, var3, var4, var5, var6, var7, var8);
   }

   // $VF: synthetic method
   public static String $r8$lambda$oFIXXmz_pNjujURnPuo0yTe6BEI(float var0) {
      return tabletVoicePage$lambda$323(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$oFWS2AKMhwINi9Na2_P3clFkzAY/* $VF was: $r8$lambda$oFWS2AKMhwINi9Na2_P3clFkzAY*/(
      String var0, SettingsActivity var1, TextView var2, int var3, int var4
   ) {
      return tabletVoiceStudioPage$lambda$397$lambda$389$lambda$388$lambda$386(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$oLipsQYMmVesYtnf5OVkFeQW0fo(AmyVoice.Pack var0, LinearLayout var1, SettingsActivity var2, String var3) {
      return tabletVoicePage$fill$lambda$340(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$oMEcx2gILq7ijMXRwQoSnKyoJ_s(TextView var0, SettingsActivity var1, Uri var2) {
      return tabletViewPage$lambda$177$lambda$176(var0, var1, var2);
   }

   // $VF: synthetic method
   public static String $r8$lambda$ojOnFdCXgVoL3G_iOcipjhhcXxI(float var0) {
      return tabletVoiceStudioPage$lambda$367(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$oztM6idPI64_bchZoXFtv_RMm2M(
      IntRef var0, ObjectRef var1, HashMap var2, FrameLayout var3, SettingsActivity var4, GalaxyApi var5, ArrayList var6, String var7
   ) {
      subTabbedCatalog$lambda$106(var0, var1, var2, var3, var4, var5, var6, var7);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$p0ePPZZMOhxqqb7y253olwUbnII(SettingsActivity var0, TextView var1, ObjectRef var2, LinearLayout var3, Uri var4) {
      return tabletVoiceStudioPage$fillEngine$lambda$363$lambda$362(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$pEiXbw_n8fErfr9jRUQCHw29DPA(UiSettings var0, boolean var1) {
      return tabletBlinkerPage$lambda$300(var0, var1);
   }

   // $VF: synthetic method
   public static String $r8$lambda$pL_TlLkXb89srvISZNsyghfroSQ(float var0) {
      return tabletVoicePage$lambda$332(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$pPwc8KpuEp3v20UslrNkUpEUDpI(int var0, AudioManager var1, int var2, SettingsActivity var3, TextView var4) {
      return tabletVoicePage$lambda$337(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$pqqOh8HTI9ADB_vmM3wYBmA1wG0(SettingsActivity var0, TextView var1) {
      return tabletRtkPage$lambda$212(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$q67h5a3oVfp2fMYSOO1beUWMdBI(FrameLayout var0, SettingsActivity var1) {
      togglesPage$lambda$135(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$qAfuXI__2_qCmHgRCHmMIr97Dvc/* $VF was: $r8$lambda$qAfuXI__2_qCmHgRCHmMIr97Dvc*/(SettingsActivity var0) {
      return tabletVoicePage$lambda$322(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$qM1J_CDvJv9IwYoIo2tuN__JeSo/* $VF was: $r8$lambda$qM1J_CDvJv9IwYoIo2tuN__JeSo*/(SettingsActivity var0, Uri var1, TextView var2) {
      tabletViewPage$lambda$177$lambda$176$lambda$175(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$q_SV8nZHprTtsrpUiKLRuedbXAg(LaneModel var0, Uri var1, SettingsActivity var2, TextView var3) {
      tabletViewPage$lambda$173$lambda$172$lambda$171(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$r4ph8AtBhEMRT9ixka0SOYpWQuc(UiSettings var0, boolean var1) {
      return tabletNavPage$lambda$257(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$rYhnlI3bcItK4ha1AneF_e_lmXg(SettingsActivity var0, TextView var1) {
      return backupCasterSection$lambda$240(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$raJpfxcmIhhuweLhKXdHSMt6SXE() {
      return tabletMapsPage$refresh$lambda$179();
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$rhQEnfNW_xjNBhElm5HFg2_kpE4/* $VF was: $r8$lambda$rhQEnfNW_xjNBhElm5HFg2_kpE4*/(
      UiSettings var0, UiSettings.Item var1, boolean var2
   ) {
      return uiGroupPage$lambda$156(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$sEq9nsYh2RW2u_ydzYjG1iLS3Rw(UiSettings var0, boolean var1) {
      return tabletNavPage$lambda$261(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$snUprECSjO_hwSp2X5R3XJsiMNU(SettingsActivity var0) {
      return tabletBackupPage$lambda$142(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$tJ6mgEk0Hsinwo4_V5rZ7kY3TAw(SettingsActivity var0, TextView var1) {
      return tabletRtkPage$lambda$216(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$tavsVJyJXCHz134d9IitfvwK6hU(SettingsActivity.ComboPage var0, SettingsActivity var1) {
      togglesPage$lambda$132$lambda$131$lambda$130(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$tbOr8m80Ha1NsI1prwPCdSVUps4() {
      return tabletVoicePage$lambda$331();
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$uLTj7xbba_DSNpV7i8ZV1Qe_A_A() {
      return tabletBlinkerPage$lambda$310();
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$uQKqoo_DL0DYyvRNEXVoGQ36_UM(ObjectRef var0, SharedPreferences var1, float var2) {
      return tabletVoiceStudioPage$lambda$368(var0, var1, var2);
   }

   // $VF: synthetic method
   public static String $r8$lambda$uh4Xwc41qDpqGChFxbiQZcFCJl0(SettingsActivity var0) {
      return backupCasterSection$lambda$233(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$utqn9R0SHCyQdTE_OknoUrjeN_g/* $VF was: $r8$lambda$utqn9R0SHCyQdTE_OknoUrjeN_g*/(SettingsActivity var0) {
      return currentDriveState$lambda$60(var0);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$v0qpBhvI2yBlNeb8DW4URxB7MCc(UiSettings var0, UiSettings.Item var1) {
      return uiGroupPage$lambda$155(var0, var1);
   }

   // $VF: synthetic method
   public static WindowInsetsCompat $r8$lambda$vGl71vIfjZD7nNnWWltOTMUpsSs(View var0, WindowInsetsCompat var1) {
      return onCreate$lambda$23(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$vNIQKGjlPuTINu5_b8tOVlxgTFk/* $VF was: $r8$lambda$vNIQKGjlPuTINu5_b8tOVlxgTFk*/(SettingsActivity var0, int var1) {
      return homeAreas$lambda$35(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$vWMITYM98gB1lkGtjwOspNRnaYM(SettingsActivity var0, boolean var1) {
      return backupCasterSection$lambda$218(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$vajpF1DSsqS77UTgyut1_kYiQZ8(SettingsActivity var0, EditText var1, TextView var2, DialogInterface var3, int var4) {
      tabletRtkPage$lambda$216$lambda$215(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static void $r8$lambda$vpm3aj0ftEUrpzhjo6_5Xh1Rf0M(SettingsActivity var0) {
      newIdeasPage$lambda$289$lambda$288(var0);
   }

   // $VF: synthetic method
   public static String $r8$lambda$vqppP7pmvefRpFFgCBxKP9XktQY(SettingsActivity var0) {
      return backupCasterSection$lambda$231(var0);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$wwnzPjCpImOWNDaNuv09_YSmjok(UiSettings var0) {
      return tabletBlinkerPage$lambda$299(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$xEK72arQn5neuAO6Qj0e77nLh_o(SettingsActivity var0) {
      return showArea$lambda$69(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$xIbNjyXa7U3rQJM6GL249_XhT_Y/* $VF was: $r8$lambda$xIbNjyXa7U3rQJM6GL249_XhT_Y*/(SettingsActivity var0) {
      return tabletBackupPage$lambda$144(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$xL716F7vt_c7eVu1ewt_DKJMOT8(SettingsActivity var0, String var1, Function0 var2, JSONObject var3) {
      return rtkFusionSection$send$lambda$242(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$xMIwf6VI2PTxKRF_IOM1rK6Wf3g(SettingsActivity var0, String var1) {
      return buildPageUnsafe$lambda$81(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$xQ1GPjEfyMmVlbDFUVd1P6PiTCU(SettingsActivity var0, String var1) {
      return buildPageUnsafe$lambda$82(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$xUI97Z2EGeMjSgME0SRBJ1B1mjo(SettingsActivity var0) {
      return tabletVoicePage$fill$lambda$349(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$y4jjHS4aOjDc1KGvXr_aVw5__74/* $VF was: $r8$lambda$y4jjHS4aOjDc1KGvXr_aVw5__74*/(
      SettingsActivity var0, DialogInterface var1, int var2
   ) {
      driveStateSwitch$lambda$59$lambda$58(var0, var1, var2);
   }

   // $VF: synthetic method
   public static String $r8$lambda$yaxwjRNoaaa3Acd5528MHuWqiIw(
      SettingsActivity var0, VoiceStudio.Voice var1, IntRef var2, ObjectRef var3, String var4, AtomicBoolean var5, TextView var6
   ) {
      return tabletVoiceStudioPage$lambda$397$lambda$389$lambda$388(var0, var1, var2, var3, var4, var5, var6);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$zJq9q0e0y9cyZ9Txjs9Myr8jHdw(SettingsActivity var0, String var1) {
      return showHome$lambda$30(var0, var1);
   }

   // $VF: synthetic method
   public static Ntrip.Config $r8$lambda$zdnMmr8Fg9l_KHgbZExuvJOLkSY(Ntrip.Config var0, String var1) {
      return backupCasterSection$lambda$232(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$zgnosdxVjpxXm5YZj9e9RwFTym8(SettingsActivity var0, JSONObject var1) {
      return confirmDriveState$lambda$67$lambda$66(var0, var1);
   }

   // $VF: synthetic method
   public static Ntrip.Config $r8$lambda$zp8pmxwTY_RIVzvq1JCBJtsJvRA(Ntrip.Config var0, String var1) {
      return tabletRtkPage$lambda$200(var0, var1);
   }

   static {
      Pair var7 = TuplesKt.to("personalities", "Personalities");
      Pair var6 = TuplesKt.to("slc", "Speed limits");
      Pair var1 = TuplesKt.to("wheel", "Wheel controls");
      Pair var2 = TuplesKt.to("sounds", "Sounds & alerts");
      Pair var8 = TuplesKt.to("data", "Data uploads");
      Pair var3 = TuplesKt.to("developer", "Developer");
      Pair var4 = TuplesKt.to("display", "Screen");
      Pair var5 = TuplesKt.to("appearance", "Driving screen");
      Pair var0 = TuplesKt.to("pip", "Blind spot camera");
      GALAXY_TITLES = MapsKt.mapOf(new Pair[]{var7, var6, var1, var2, var8, var3, var4, var5, var0, TuplesKt.to("vehicle", "Vehicle")});
   }

   public SettingsActivity() {
      ActivityResultLauncher var1 = this.registerForActivityResult(new OpenDocument(), new SettingsActivity$$ExternalSyntheticLambda148(this));
      Intrinsics.checkNotNullExpressionValue(var1, "registerForActivityResult(...)");
      this.pickModel = var1;
      var1 = this.registerForActivityResult(new OpenDocument(), new SettingsActivity$$ExternalSyntheticLambda149(this));
      Intrinsics.checkNotNullExpressionValue(var1, "registerForActivityResult(...)");
      this.pickAny = var1;
      this.panels = new HashMap<>();
      this.handler = new Handler(Looper.getMainLooper());
      this.areaIndex = -1;
      this.driveModes = CollectionsKt.listOf(new Pair[]{TuplesKt.to("default", "Auto"), TuplesKt.to("onroad", "Onroad"), TuplesKt.to("offroad", "Offroad")});
      this.buildGroup = "tablet";
      this.ticker = new Runnable() {

         @Override
         public void run() {
            if (SettingsActivity.this.contentHolder == null && SettingsActivity.this.driveBtn != null) {
               SettingsActivity.this.syncDriveState();
            }

            View var2 = SettingsActivity.this.current;
            boolean var1x = var2 instanceof DevicePanel;
            Object var3 = null;
            DevicePanel var5;
            if (var1x) {
               var5 = (DevicePanel)var2;
            } else {
               var5 = null;
            }

            if (var5 != null) {
               var5.onTick();
            }

            var2 = SettingsActivity.this.current;
            CatalogPanel var7;
            if (var2 instanceof CatalogPanel) {
               var7 = (CatalogPanel)var2;
            } else {
               var7 = null;
            }

            if (var7 != null) {
               var7.onTick();
            }

            var2 = SettingsActivity.this.current;
            SettingsActivity.ComboPage var9;
            if (var2 instanceof SettingsActivity.ComboPage) {
               var9 = (SettingsActivity.ComboPage)var2;
            } else {
               var9 = null;
            }

            if (var9 != null) {
               var9.tick();
            }

            View var4 = SettingsActivity.this.current;
            GxPage var10 = (GxPage)var3;
            if (var4 instanceof GxPage) {
               var10 = (GxPage)var4;
            }

            if (var10 != null) {
               var10.tick();
            }

            DriveState.OffroadBar var11 = SettingsActivity.this.offroadBar;
            if (var11 != null) {
               var11.sync(SettingsActivity.this.pageNeedsOffroad(SettingsActivity.this.current));
            }

            SettingsActivity.this.handler.postDelayed(this, 700L);
         }
      };
      this.valuePoll = new Runnable() {

         @Override
         public void run() {
            if (SettingsActivity.this.current instanceof CatalogPanel || SettingsActivity.this.current instanceof SettingsActivity.ComboPage) {
               GalaxyApi var1 = SettingsActivity.this.api;
               if (var1 != null) {
                  GalaxyApi.refreshValues$default(var1, null, 1, null);
               }
            }

            SettingsActivity.this.handler.postDelayed(this, 4000L);
         }
      };
   }

   private final List<SettingsActivity.Area> areas() {
      int var1 = R.drawable.ic_car;
      SettingsActivity.Rail var14 = new SettingsActivity.Rail("Driving controls", R.drawable.ic_speed, "drivingmap");
      SettingsActivity.Rail var12 = new SettingsActivity.Rail("Steering", R.drawable.ic_steering, "galaxy:steering");
      SettingsActivity.Rail var15 = new SettingsActivity.Rail("Speed & following", R.drawable.ic_speed, "galaxy:speed");
      SettingsActivity.Rail var16 = new SettingsActivity.Rail("Personalities", R.drawable.ic_speed, "galaxy:personalities");
      SettingsActivity.Rail var9 = new SettingsActivity.Rail("Speed limits", R.drawable.ic_limit, "galaxy:slc");
      SettingsActivity.Rail var11 = new SettingsActivity.Rail("Vehicle", R.drawable.ic_car, "galaxy:vehicle");
      SettingsActivity.Rail var8 = new SettingsActivity.Rail("TechStream", R.drawable.ic_wheel_controls, "tablet:techstream");
      SettingsActivity.Rail var10 = new SettingsActivity.Rail("Doors & belts", R.drawable.ic_car, "doorbelt");
      SettingsActivity.Rail var7 = new SettingsActivity.Rail("Key fob", R.drawable.ic_car, "keyfob");
      SettingsActivity.Rail var6 = new SettingsActivity.Rail("Wheel controls", R.drawable.ic_wheel_controls, "galaxy:wheel");
      SettingsActivity.Rail var13 = new SettingsActivity.Rail("Sounds & alerts", R.drawable.ic_sound, "galaxy:sounds");
      SettingsActivity.Rail var5 = new SettingsActivity.Rail("Cameras & monitoring", R.drawable.ic_camera, "galaxy:cameras");
      Pair var30 = TuplesKt.to(
         "Driving", CollectionsKt.listOf(new SettingsActivity.Rail[]{var14, var12, var15, var16, var9, var11, var8, var10, var7, var6, var13, var5})
      );
      var8 = new SettingsActivity.Rail("Comma health", R.drawable.ic_info, "health");
      var10 = new SettingsActivity.Rail("Power management", R.drawable.ic_link, "hubpower");
      var15 = new SettingsActivity.Rail("Connections", R.drawable.ic_network, "connections");
      var9 = new SettingsActivity.Rail("Toggles", R.drawable.ic_gear, "native:toggles");
      var6 = new SettingsActivity.Rail("Device", R.drawable.ic_info, "native:device");
      var12 = new SettingsActivity.Rail("Network", R.drawable.ic_network, "native:network");
      var14 = new SettingsActivity.Rail("Software", R.drawable.ic_data, "native:software");
      var13 = new SettingsActivity.Rail("Data uploads", R.drawable.ic_data, "galaxy:data");
      var16 = new SettingsActivity.Rail("Developer", R.drawable.ic_dev, "galaxy:developer");
      var11 = new SettingsActivity.Rail("Developer (comma)", R.drawable.ic_terminal, "native:developer");
      var7 = new SettingsActivity.Rail("More from Galaxy", R.drawable.ic_star, "galaxy:more");
      Pair var41 = TuplesKt.to(
         "System", CollectionsKt.listOf(new SettingsActivity.Rail[]{var8, var10, var15, var9, var6, var12, var14, var13, var16, var11, var7})
      );
      SettingsActivity.Area var53 = new SettingsActivity.Area("Device", "Settings", var1, CollectionsKt.listOf(new Pair[]{var30, var41}));
      var1 = R.drawable.ic_display;
      var8 = new SettingsActivity.Rail("Screen", R.drawable.ic_sun, "galaxy:display");
      var6 = new SettingsActivity.Rail("Driving screen", R.drawable.ic_eye, "galaxy:appearance");
      var5 = new SettingsActivity.Rail("Quick Select", R.drawable.ic_star, "quickselect");
      var9 = new SettingsActivity.Rail("Blind spot camera", R.drawable.ic_camera, "galaxy:pip");
      SettingsActivity.Area var56 = new SettingsActivity.Area(
         "Device", "Display", var1, CollectionsKt.listOf(TuplesKt.to(null, CollectionsKt.listOf(new SettingsActivity.Rail[]{var8, var6, var5, var9})))
      );
      var1 = R.drawable.ic_tablet;
      var14 = new SettingsActivity.Rail("Connection", R.drawable.ic_link, "tablet:connection");
      SettingsActivity.Rail var17 = new SettingsActivity.Rail("Navigation map", R.drawable.ic_pin, "tablet:nav");
      SettingsActivity.Rail var23 = new SettingsActivity.Rail("Camera & view", R.drawable.ic_camera, "tablet:view");
      var15 = new SettingsActivity.Rail("Street maps", R.drawable.ic_pin, "tablet:maps");
      var12 = new SettingsActivity.Rail("GPS & sensors", R.drawable.ic_pin, "activity:sensors");
      var6 = new SettingsActivity.Rail("Lane pictures", R.drawable.ic_camera, "activity:lanepics");
      SettingsActivity.Rail var20 = new SettingsActivity.Rail("Lane road data", R.drawable.ic_pin, "roaddata");
      var10 = new SettingsActivity.Rail("Bookmarks", R.drawable.ic_pin, "bookmarks");
      SettingsActivity.Rail var19 = new SettingsActivity.Rail("RTK GPS", R.drawable.ic_pin, "tablet:rtk");
      SettingsActivity.Rail var24 = new SettingsActivity.Rail("Braking log", R.drawable.ic_speed, "tablet:braking");
      var16 = new SettingsActivity.Rail("Blinker & lane changes", R.drawable.ic_steering, "tablet:blinker");
      var5 = new SettingsActivity.Rail("New ideas", R.drawable.ic_star, "tablet:ideas");
      SettingsActivity.Rail var21 = new SettingsActivity.Rail("Voice alerts", R.drawable.ic_info, "tablet:voice");
      SettingsActivity.Rail var18 = new SettingsActivity.Rail("Voice studio", R.drawable.ic_info, "tablet:voicestudio");
      var9 = new SettingsActivity.Rail("Terminal", R.drawable.ic_terminal, "native:terminal");
      SettingsActivity.Rail var22 = new SettingsActivity.Rail("Files", R.drawable.ic_files, "activity:files");
      var13 = new SettingsActivity.Rail("Storage", R.drawable.ic_data, "tablet:storage");
      SettingsActivity.Rail var25 = new SettingsActivity.Rail("Backup & restore", R.drawable.ic_data, "tablet:backup");
      var11 = new SettingsActivity.Rail("About", R.drawable.ic_info, "tablet:about");
      Pair var33 = TuplesKt.to(
         null,
         CollectionsKt.listOf(
            new SettingsActivity.Rail[]{
               var14, var17, var23, var15, var12, var6, var20, var10, var19, var24, var16, var5, var21, var18, var9, var22, var13, var25, var11
            }
         )
      );
      var6 = new SettingsActivity.Rail("Last drive", R.drawable.ic_info, "journal:last");
      var12 = new SettingsActivity.Rail("Your driving", R.drawable.ic_info, "journal:trends");
      var11 = new SettingsActivity.Rail("Alert history", R.drawable.ic_info, "journal:alerts");
      var9 = new SettingsActivity.Rail("Takeover spots", R.drawable.ic_info, "journal:spots");
      var10 = new SettingsActivity.Rail("Driver profiles", R.drawable.ic_info, "profiles");
      Pair var45 = TuplesKt.to("Drives", CollectionsKt.listOf(new SettingsActivity.Rail[]{var6, var12, var11, var9, var10}));
      SettingsActivity.Area var61 = new SettingsActivity.Area("Tablet", "Settings", var1, CollectionsKt.listOf(new Pair[]{var33, var45}));
      int var2 = R.drawable.ic_view;
      Collection var65 = CollectionsKt.listOf(new SettingsActivity.Rail("Theme", R.drawable.ic_sun, "tablet:theme"));
      Iterable var46 = UiSettings.Companion.getITEMS();
      var var34 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var46, 10));
      Iterator var47 = var46.iterator();

      while (var47.hasNext()) {
         var34.add(((UiSettings.Item)var47.next()).getGroup());
      }

      List var35 = var34;
      Iterable var48 = CollectionsKt.distinct(var35);
      var var36 = new ArrayList();

      for (Object var74 : var48) {
         String var69 = (String)var74;
         boolean var28;
         if (!Intrinsics.areEqual(var69, "Widgets")
            && !Intrinsics.areEqual(var69, "Internal")
            && !Intrinsics.areEqual(var69, "Camera")
            && !Intrinsics.areEqual(var69, "Blinker")) {
            var28 = true;
         } else {
            var28 = false;
         }

         if (var28) {
            var36.add(var74);
         }
      }

      List var37 = var36;
      Iterable var50 = var37;
      boolean var4 = false;
      var var70 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var50, 10));
      boolean var3 = false;

      for (String var78 : var50) {
         label47: {
            switch (var78.hashCode()) {
               case -169275166:
                  if (var78.equals("Effects")) {
                     var1 = R.drawable.ic_fx;
                     break label47;
                  }
                  break;
               case 71895:
                  if (var78.equals("HUD")) {
                     var1 = R.drawable.ic_hud;
                     break label47;
                  }
                  break;
               case 2552640:
                  if (var78.equals("Road")) {
                     var1 = R.drawable.ic_road;
                     break label47;
                  }
                  break;
               case 2011082565:
                  if (var78.equals("Camera")) {
                     var1 = R.drawable.ic_camera;
                     break label47;
                  }
            }

            var1 = R.drawable.ic_eye;
         }

         var70.add(new SettingsActivity.Rail(var78, var1, "uigroup:" + var78));
      }

      List var38 = var70;
      SettingsActivity.Area var39 = new SettingsActivity.Area(
         "Tablet", "Display", var2, CollectionsKt.listOf(TuplesKt.to(null, CollectionsKt.plus(var65, var38)))
      );
      SettingsActivity.Area var51 = new SettingsActivity.Area(
         "Device", "Tools", R.drawable.ic_tools, CollectionsKt.listOf(TuplesKt.to(null, this.galaxyToolRails()))
      );
      return CollectionsKt.listOf(new SettingsActivity.Area[]{var53, var56, var61, var39, var51});
   }

   private final void backupCasterSection(LinearLayout var1) {
      CarUi var4 = this.ui;
      CarUi var3 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var3 = null;
      }

      View var5 = (View)var3.header("Backup caster");
      var4 = this.ui;
      var3 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var3 = null;
      }

      var1.addView(var5, (LayoutParams)CarUi.lp$default(var3, 0, 0, 3, null));
      this.toggleCard(
         var1,
         "Use a backup caster",
         "Only when the main caster gives no RTK for 3 minutes (e.g. outside NYSNet's area). The main one is tried again every 10 minutes.",
         new SettingsActivity$$ExternalSyntheticLambda57(this),
         new SettingsActivity$$ExternalSyntheticLambda62(this)
      );
      this.toggleCard(
         var1,
         "Use only the backup (testing)",
         "The main caster isn't used at all while this is on.",
         new SettingsActivity$$ExternalSyntheticLambda63(this),
         new SettingsActivity$$ExternalSyntheticLambda64(this)
      );
      backupCasterSection$field$226$default(
         this,
         var1,
         "Caster",
         new SettingsActivity$$ExternalSyntheticLambda65(this),
         "rtk2go.com",
         false,
         new SettingsActivity$$ExternalSyntheticLambda67(),
         32,
         null
      );
      backupCasterSection$field$226(
         this, var1, "Port", new SettingsActivity$$ExternalSyntheticLambda68(this), "2101", true, new SettingsActivity$$ExternalSyntheticLambda69()
      );
      backupCasterSection$field$226$default(
         this,
         var1,
         "Username",
         new SettingsActivity$$ExternalSyntheticLambda70(this),
         "RTK2go: your email address",
         false,
         new SettingsActivity$$ExternalSyntheticLambda71(),
         32,
         null
      );
      backupCasterSection$field$226$default(
         this,
         var1,
         "Password",
         new SettingsActivity$$ExternalSyntheticLambda58(this),
         "RTK2go: none",
         false,
         new SettingsActivity$$ExternalSyntheticLambda59(),
         32,
         null
      );
      var3 = this.ui;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var3 = null;
      }

      var var15 = backupCasterSection$b(this).getMount();
      boolean var2;
      if (var15.length() == 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (var2) {
         var15 = "Auto: the nearest base";
      }

      Pair var8 = CarUi.row$default(var3, "Mountpoint", var15, 0, null, null, 28, null);
      LinearLayout var16 = (LinearLayout)var8.component1();
      TextView var20 = (TextView)var8.component2();
      var3 = this.ui;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var3 = null;
      }

      var16.addView((View)CarUi.button$default(var3, "Auto", false, new SettingsActivity$$ExternalSyntheticLambda60(var20, this), 2, null));
      var3 = this.ui;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var3 = null;
      }

      var16.addView((View)CarUi.button$default(var3, "Type", false, new SettingsActivity$$ExternalSyntheticLambda61(this, var20), 2, null));
      var5 = (View)var16;
      var4 = this.ui;
      var3 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var3 = null;
      }

      var1.addView(var5, (LayoutParams)CarUi.lp$default(var3, 0, 0, 3, null));
      var4 = this.ui;
      var3 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var3 = null;
      }

      var5 = (View)var3.note(
         "Auto picks the nearest base within 25 miles from the caster's list (read at most every 30 minutes) and moves to a closer one when you're 15+ miles from it. Only one connection at a time. RTK2go: Caster rtk2go.com, Port 2101, Username your email, Password none."
      );
      var4 = this.ui;
      var3 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var3 = null;
      }

      var1.addView(var5, (LayoutParams)CarUi.lp$default(var3, 0, 0, 3, null));
   }

   private static final Ntrip.Config backupCasterSection$b(SettingsActivity var0) {
      return Ntrip.INSTANCE.backup((Context)var0);
   }

   private static final void backupCasterSection$field$226(
      SettingsActivity var0,
      LinearLayout var1,
      String var2,
      Function0<String> var3,
      String var4,
      boolean var5,
      Function2<? super Ntrip.Config, ? super String, Ntrip.Config> var6
   ) {
      CarUi var8 = var0.ui;
      if (var8 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var8 = null;
      }

      LinearLayout var9 = (CharSequence)var3.invoke();
      boolean var7;
      if (var9.length() == 0) {
         var7 = true;
      } else {
         var7 = false;
      }

      if (var7) {
         var9 = "not set";
      }

      Pair var14 = CarUi.row$default(var8, var2, (String)var9, 0, null, null, 28, null);
      var9 = (LinearLayout)var14.component1();
      TextView var10 = (TextView)var14.component2();
      var8 = var0.ui;
      if (var8 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var8 = null;
      }

      var9.addView(
         (View)CarUi.button$default(var8, "Change", false, new SettingsActivity$$ExternalSyntheticLambda108(var0, var2, var3, var4, var5, var6, var10), 2, null)
      );
      View var13 = (View)var9;
      CarUi var12 = var0.ui;
      CarUi var11 = var12;
      if (var12 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var11 = null;
      }

      var1.addView(var13, (LayoutParams)CarUi.lp$default(var11, 0, 0, 3, null));
   }

   // $VF: synthetic method
   static void backupCasterSection$field$226$default(
      SettingsActivity var0, LinearLayout var1, String var2, Function0 var3, String var4, boolean var5, Function2 var6, int var7, Object var8
   ) {
      if ((var7 & 32) != 0) {
         var5 = false;
      }

      backupCasterSection$field$226(var0, var1, var2, var3, var4, var5, var6);
   }

   private static final Unit backupCasterSection$field$226$lambda$225(
      SettingsActivity var0, String var1, Function0 var2, String var3, boolean var4, Function2 var5, TextView var6
   ) {
      EditText var11 = new EditText((Context)var0);
      var11.setText((CharSequence)var2.invoke());
      var11.setHint(var3);
      int var7;
      if (var4) {
         var7 = 2;
      } else {
         var7 = 524289;
      }

      var11.setInputType(var7);
      CarUi var10 = var0.ui;
      CarUi var12 = var10;
      if (var10 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var12 = null;
      }

      var7 = var12.dp(20);
      var10 = var0.ui;
      CarUi var13 = var10;
      if (var10 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var13 = null;
      }

      int var9 = var13.dp(16);
      var10 = var0.ui;
      CarUi var14 = var10;
      if (var10 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var14 = null;
      }

      int var8 = var14.dp(20);
      var10 = var0.ui;
      CarUi var15 = var10;
      if (var10 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var15 = null;
      }

      var11.setPadding(var7, var9, var8, var15.dp(16));
      new Builder((Context)var0)
         .setTitle(var1)
         .setView((View)var11)
         .setPositiveButton("Save", new SettingsActivity$$ExternalSyntheticLambda77(var5, var11, var6, var2, var0))
         .setNegativeButton("Cancel", null)
         .show();
      return Unit.INSTANCE;
   }

   private static final void backupCasterSection$field$226$lambda$225$lambda$224(
      Function2 var0, EditText var1, TextView var2, Function0 var3, SettingsActivity var4, DialogInterface var5, int var6
   ) {
      backupCasterSection$put(var4, (Ntrip.Config)var0.invoke(backupCasterSection$b(var4), var1.getText().toString()));
      if (var2 != null) {
         Object var7 = (CharSequence)var3.invoke();
         boolean var8;
         if (var7.length() == 0) {
            var8 = true;
         } else {
            var8 = false;
         }

         if (var8) {
            var7 = "not set";
         }

         var2.setText((CharSequence)var7);
      }
   }

   private static final boolean backupCasterSection$lambda$217(SettingsActivity var0) {
      return backupCasterSection$b(var0).getOn();
   }

   private static final Unit backupCasterSection$lambda$218(SettingsActivity var0, boolean var1) {
      Ntrip.Config var2 = backupCasterSection$b(var0);
      backupCasterSection$put(var0, new Ntrip.Config(var1, var2.getHost(), var2.getPort(), var2.getMount(), var2.getUser(), var2.getPass()));
      return Unit.INSTANCE;
   }

   private static final boolean backupCasterSection$lambda$219(SettingsActivity var0) {
      return Ntrip.INSTANCE.backupOnly((Context)var0);
   }

   private static final Unit backupCasterSection$lambda$220(SettingsActivity var0, boolean var1) {
      Ntrip.INSTANCE.setBackupOnly((Context)var0, var1);
      return Unit.INSTANCE;
   }

   private static final String backupCasterSection$lambda$227(SettingsActivity var0) {
      return backupCasterSection$b(var0).getHost();
   }

   private static final Ntrip.Config backupCasterSection$lambda$228(Ntrip.Config var0, String var1) {
      Intrinsics.checkNotNullParameter(var0, "c");
      Intrinsics.checkNotNullParameter(var1, "v");
      return new Ntrip.Config(var0.getOn(), var1, var0.getPort(), var0.getMount(), var0.getUser(), var0.getPass());
   }

   private static final String backupCasterSection$lambda$229(SettingsActivity var0) {
      return String.valueOf(backupCasterSection$b(var0).getPort());
   }

   private static final Ntrip.Config backupCasterSection$lambda$230(Ntrip.Config var0, String var1) {
      Intrinsics.checkNotNullParameter(var0, "c");
      Intrinsics.checkNotNullParameter(var1, "v");
      boolean var3 = var0.getOn();
      String var4 = var0.getHost();
      Integer var5 = StringsKt.toIntOrNull(var1);
      int var2;
      if (var5 != null) {
         var2 = var5;
      } else {
         var2 = var0.getPort();
      }

      return new Ntrip.Config(var3, var4, var2, var0.getMount(), var0.getUser(), var0.getPass());
   }

   private static final String backupCasterSection$lambda$231(SettingsActivity var0) {
      return backupCasterSection$b(var0).getUser();
   }

   private static final Ntrip.Config backupCasterSection$lambda$232(Ntrip.Config var0, String var1) {
      Intrinsics.checkNotNullParameter(var0, "c");
      Intrinsics.checkNotNullParameter(var1, "v");
      return new Ntrip.Config(var0.getOn(), var0.getHost(), var0.getPort(), var0.getMount(), var1, var0.getPass());
   }

   private static final String backupCasterSection$lambda$233(SettingsActivity var0) {
      return backupCasterSection$b(var0).getPass();
   }

   private static final Ntrip.Config backupCasterSection$lambda$234(Ntrip.Config var0, String var1) {
      Intrinsics.checkNotNullParameter(var0, "c");
      Intrinsics.checkNotNullParameter(var1, "v");
      return new Ntrip.Config(var0.getOn(), var0.getHost(), var0.getPort(), var0.getMount(), var0.getUser(), var1);
   }

   private static final Unit backupCasterSection$lambda$236(TextView var0, SettingsActivity var1) {
      Ntrip.Config var2 = backupCasterSection$b(var1);
      backupCasterSection$put(var1, new Ntrip.Config(var2.getOn(), var2.getHost(), var2.getPort(), "", var2.getUser(), var2.getPass()));
      if (var0 != null) {
         var0.setText("Auto: the nearest base");
      }

      return Unit.INSTANCE;
   }

   private static final Unit backupCasterSection$lambda$240(SettingsActivity var0, TextView var1) {
      EditText var7 = new EditText((Context)var0);
      var7.setText(backupCasterSection$b(var0).getMount());
      CarUi var6 = var0.ui;
      CarUi var5 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var5 = null;
      }

      int var2 = var5.dp(20);
      var6 = var0.ui;
      var5 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var5 = null;
      }

      int var3 = var5.dp(16);
      var6 = var0.ui;
      var5 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var5 = null;
      }

      int var4 = var5.dp(20);
      var6 = var0.ui;
      var5 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var5 = null;
      }

      var7.setPadding(var2, var3, var4, var5.dp(16));
      new Builder((Context)var0)
         .setTitle("Mountpoint (case matters)")
         .setView((View)var7)
         .setPositiveButton("Save", new SettingsActivity$$ExternalSyntheticLambda1(var7, var1, var0))
         .setNegativeButton("Cancel", null)
         .show();
      return Unit.INSTANCE;
   }

   private static final void backupCasterSection$lambda$240$lambda$239(EditText var0, TextView var1, SettingsActivity var2, DialogInterface var3, int var4) {
      Ntrip.Config var6 = backupCasterSection$b(var2);
      backupCasterSection$put(var2, new Ntrip.Config(var6.getOn(), var6.getHost(), var6.getPort(), var0.getText().toString(), var6.getUser(), var6.getPass()));
      if (var1 != null) {
         Object var5 = backupCasterSection$b(var2).getMount();
         boolean var7;
         if (var5.length() == 0) {
            var7 = true;
         } else {
            var7 = false;
         }

         if (var7) {
            var5 = "Auto: the nearest base";
         }

         var1.setText((CharSequence)var5);
      }
   }

   private static final void backupCasterSection$put(SettingsActivity var0, Ntrip.Config var1) {
      Ntrip.INSTANCE.saveBackup((Context)var0, var1);
   }

   private final View buildPage(String var1) {
      View var2;
      try {
         this.buildGroup = Companion.railIcon(var1).getGroup();
         var2 = this.buildPageUnsafe(var1);
      } catch (Exception var3) {
         Log.e("StarView/Settings", "page " + var1, var3);
         StackTraceElement[] var4 = var3.getStackTrace();
         Intrinsics.checkNotNullExpressionValue(var4, "getStackTrace(...)");
         var1 = CollectionsKt.joinToString$default(ArraysKt.take(var4, 6), "\n", null, null, 0, null, null, 62, null);
         return this.offline("This page failed to build:\n" + var3 + "\n\n" + var1);
      }

      return var2;
   }

   private final View buildPageUnsafe(String var1) {
      boolean var2 = Intrinsics.areEqual(var1, "drivingmap");
      Object var7 = null;
      Object var16 = null;
      Object var12 = null;
      Object var13 = null;
      Object var6 = null;
      Object var17 = null;
      Object var11 = null;
      Object var5 = null;
      Object var8 = null;
      Object var15 = null;
      Object var10 = null;
      Object var14 = null;
      Object var9 = null;
      Collection var4 = null;
      TextView var3 = null;
      View var19;
      if (var2) {
         var4 = (Context)this;
         CarUi var18 = this.ui;
         if (var18 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var18 = var3;
         }

         var19 = (View)(new DrivingMapView((Context)var4, var18, new SettingsActivity$$ExternalSyntheticLambda189(this)));
      } else if (Intrinsics.areEqual(var1, "toolshome")) {
         var3 = (Context)this;
         CarUi var20 = this.ui;
         if (var20 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var20 = (CarUi)var7;
         }

         var5 = GalaxyTools.INSTANCE.list((Context)this);
         var4 = new ArrayList();

         for (Object var58 : var5) {
            var5 = (GalaxyTools.Tool)var58;
            if (!GalaxyTools.INSTANCE.getPLACED_ELSEWHERE().contains(((GalaxyTools.Tool)var5).getPath())) {
               var4.add(var58);
            }
         }

         List var52 = (List)var4;
         var19 = (View)(new ToolsHomeView((Context)var3, var20, var52, new SettingsActivity$$ExternalSyntheticLambda190(this)));
      } else if (Intrinsics.areEqual(var1, "connections")) {
         var3 = (Context)this;
         CarUi var21 = this.ui;
         if (var21 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var21 = (CarUi)var16;
         }

         var19 = (View)(new ConnectionsView((Context)var3, var21, new SettingsActivity$$ExternalSyntheticLambda192(this)));
      } else if (Intrinsics.areEqual(var1, "health")) {
         var3 = (Context)this;
         CarUi var22 = this.ui;
         if (var22 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var22 = (CarUi)var12;
         }

         var19 = (View)(new CommaHealthView((Context)var3, var22));
      } else if (Intrinsics.areEqual(var1, "doorbelt")) {
         var3 = (Context)this;
         CarUi var23 = this.ui;
         if (var23 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var23 = (CarUi)var13;
         }

         var19 = (View)(new DoorsBeltsView((Context)var3, var23));
      } else if (Intrinsics.areEqual(var1, "keyfob")) {
         var3 = (Context)this;
         CarUi var24 = this.ui;
         if (var24 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var24 = (CarUi)var6;
         }

         var19 = (View)(new KeyFobView((Context)var3, var24));
      } else if (Intrinsics.areEqual(var1, "hubpower")) {
         var3 = (Context)this;
         CarUi var25 = this.ui;
         if (var25 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var25 = (CarUi)var17;
         }

         var19 = (View)(new HubPowerView((Context)var3, var25));
      } else if (Intrinsics.areEqual(var1, "roaddata")) {
         var3 = (Context)this;
         CarUi var26 = this.ui;
         if (var26 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var26 = (CarUi)var11;
         }

         var19 = (View)(new RoadDataView((Context)var3, var26));
      } else if (Intrinsics.areEqual(var1, "bookmarks")) {
         var3 = (Context)this;
         CarUi var27 = this.ui;
         if (var27 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var27 = (CarUi)var5;
         }

         var19 = (View)(new BookmarksView((Context)var3, var27));
      } else if (Intrinsics.areEqual(var1, "journal:last")) {
         var3 = (Context)this;
         CarUi var28 = this.ui;
         if (var28 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var28 = (CarUi)var8;
         }

         var19 = (View)(new LastDriveView((Context)var3, var28));
      } else if (Intrinsics.areEqual(var1, "journal:trends")) {
         var3 = (Context)this;
         CarUi var29 = this.ui;
         if (var29 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var29 = (CarUi)var15;
         }

         var19 = (View)(new DrivingTrendsView((Context)var3, var29));
      } else if (Intrinsics.areEqual(var1, "journal:alerts")) {
         var3 = (Context)this;
         CarUi var30 = this.ui;
         if (var30 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var30 = (CarUi)var10;
         }

         var19 = (View)(new AlertHistoryView((Context)var3, var30));
      } else if (Intrinsics.areEqual(var1, "journal:spots")) {
         var3 = (Context)this;
         CarUi var31 = this.ui;
         if (var31 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var31 = (CarUi)var14;
         }

         var19 = (View)(new TakeoverSpotsView((Context)var3, var31));
      } else if (Intrinsics.areEqual(var1, "profiles")) {
         var3 = (Context)this;
         CarUi var32 = this.ui;
         if (var32 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var32 = (CarUi)var9;
         }

         var19 = (View)(new DriverProfilesView((Context)var3, var32));
      } else if (StringsKt.startsWith$default(var1, "galaxy:", false, 2, null)) {
         var19 = this.galaxyPage(StringsKt.removePrefix(var1, "galaxy:"));
      } else if (StringsKt.startsWith$default(var1, "gweb:", false, 2, null)) {
         String var53 = this.host;
         if (var53 != null) {
            var5 = (Context)this;
            var3 = this.ui;
            if (var3 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("ui");
               var3 = null;
            }

            var19 = (View)(
               new GalaxyWeb(
                  (Context)var5,
                  (CarUi)var3,
                  var53,
                  StringsKt.substringBefore$default(StringsKt.removePrefix(var1, "gweb:"), "|", null, 2, null),
                  StringsKt.substringAfter$default(var1, "|", null, 2, null),
                  new SettingsActivity$$ExternalSyntheticLambda193(this)
               )
            );
         } else {
            var19 = this.offline("Not connected to the comma.");
         }
      } else if (Intrinsics.areEqual(var1, "quickselect")) {
         if (this.host != null) {
            var3 = (Context)this;
            CarUi var33 = this.ui;
            if (var33 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("ui");
               var33 = var4;
            }

            var19 = (View)(new GalaxyExtras.QuickSelect((Context)var3, var33));
         } else {
            var19 = this.offline("Not connected to the comma.");
         }
      } else if (StringsKt.startsWith$default(var1, "cat:", false, 2, null)) {
         var19 = catalogPage$default(this, StringsKt.removePrefix(var1, "cat:"), false, 2, null);
      } else if (StringsKt.startsWith$default(var1, "native:", false, 2, null)) {
         if (Intrinsics.areEqual(var1, "native:terminal")) {
            var19 = new View((Context)this);
         } else {
            var19 = (View)(new DevicePanel(this, StringsKt.removePrefix(var1, "native:")));
         }
      } else if (StringsKt.startsWith$default(var1, "gx:", false, 2, null)) {
         var19 = this.gxPage(StringsKt.removePrefix(var1, "gx:"));
      } else if (Intrinsics.areEqual(var1, "toggles:all")) {
         var19 = this.togglesPage();
      } else if (StringsKt.startsWith$default(var1, "uigroup:", false, 2, null)) {
         var19 = this.uiGroupPage(StringsKt.removePrefix(var1, "uigroup:"));
      } else if (Intrinsics.areEqual(var1, "tablet:theme")) {
         var19 = this.tabletThemePage();
      } else if (Intrinsics.areEqual(var1, "tablet:connection")) {
         var19 = this.tabletConnectionPage();
      } else if (Intrinsics.areEqual(var1, "tablet:view")) {
         var19 = this.tabletViewPage();
      } else if (Intrinsics.areEqual(var1, "tablet:maps")) {
         var19 = this.tabletMapsPage();
      } else if (Intrinsics.areEqual(var1, "tablet:about")) {
         var19 = this.tabletAboutPage();
      } else if (Intrinsics.areEqual(var1, "tablet:storage")) {
         var19 = this.tabletStoragePage();
      } else if (Intrinsics.areEqual(var1, "tablet:backup")) {
         var19 = this.tabletBackupPage();
      } else if (Intrinsics.areEqual(var1, "tablet:nav")) {
         var19 = this.tabletNavPage();
      } else if (Intrinsics.areEqual(var1, "tablet:braking")) {
         var19 = this.tabletBrakingPage();
      } else if (Intrinsics.areEqual(var1, "tablet:voice")) {
         var19 = this.tabletVoicePage();
      } else if (Intrinsics.areEqual(var1, "tablet:blinker")) {
         var19 = this.tabletBlinkerPage();
      } else if (Intrinsics.areEqual(var1, "tablet:ideas")) {
         var19 = this.newIdeasPage();
      } else if (Intrinsics.areEqual(var1, "tablet:voicestudio")) {
         var19 = this.tabletVoiceStudioPage();
      } else if (Intrinsics.areEqual(var1, "tablet:techstream")) {
         var19 = this.techStreamPage();
      } else if (Intrinsics.areEqual(var1, "tablet:rtk")) {
         var19 = this.tabletRtkPage();
      } else {
         var3 = new TextView((Context)this);
         var3.setText(var1);
         var19 = (View)var3;
      }

      return var19;
   }

   private static final Unit buildPageUnsafe$lambda$79(SettingsActivity var0, String var1, String var2) {
      Intrinsics.checkNotNullParameter(var1, "p");
      Intrinsics.checkNotNullParameter(var2, "t");
      var0.openSub(var1, var2);
      return Unit.INSTANCE;
   }

   private static final Unit buildPageUnsafe$lambda$81(SettingsActivity var0, String var1) {
      Intrinsics.checkNotNullParameter(var1, "it");
      var0.openToolTarget(var1);
      return Unit.INSTANCE;
   }

   private static final Unit buildPageUnsafe$lambda$82(SettingsActivity var0, String var1) {
      Intrinsics.checkNotNullParameter(var1, "it");
      var0.openToolTarget(var1);
      return Unit.INSTANCE;
   }

   private static final boolean buildPageUnsafe$lambda$84$lambda$83(SettingsActivity var0) {
      return var0.returnFromWeb();
   }

   private final View catalogPage(String var1, boolean var2) {
      GalaxyApi var8 = this.api;
      if (var8 == null) {
         return this.offline("Not connected to the comma. Connect first, then come back.");
      } else {
         String var9 = StringsKt.substringBefore$default(var1, ":", null, 2, null);
         String var7 = StringsKt.substringAfter(var1, ":", "");
         Iterator var10 = var8.getCatalog().iterator();

         Object var6;
         do {
            if (!var10.hasNext()) {
               var6 = null;
               break;
            }

            var6 = (View)var10.next();
         } while (!Intrinsics.areEqual(((GalaxyApi.Category)var6).getName(), var9));

         GalaxyApi.Category var31 = (GalaxyApi.Category)var6;
         if (var31 == null) {
            if (var8.getCatalog().isEmpty()) {
               var6 = "Loading the settings catalog from the comma…";
            } else {
               var6 = "Section \"" + var9 + "\" not found in this StarPilot version.";
            }

            var6 = this.offline((String)var6);
            this.handler.postDelayed(new SettingsActivity$$ExternalSyntheticLambda158(this, var6, var1), 1500L);
            return var6;
         } else {
            List var15 = var31.getParams();
            boolean var3;
            if (var7.length() > 0) {
               var3 = true;
            } else {
               var3 = false;
            }

            if (var3) {
               String var12 = var7.substring(1);
               Intrinsics.checkNotNullExpressionValue(var12, "substring(...)");
               HashSet var20 = new HashSet();
               ArrayDeque var11 = new ArrayDeque<>(CollectionsKt.listOf(var12));
               var20.add(var12);

               while (!var11.isEmpty()) {
                  String var14 = (String)var11.removeFirst();

                  for (GalaxyApi.Param var38 : var31.getParams()) {
                     if (Intrinsics.areEqual(var38.getParentKey(), var14) && var20.add(var38.getKey())) {
                        var11.add(var38.getKey());
                     }
                  }
               }

               if (StringsKt.startsWith$default(var7, "+", false, 2, null)) {
                  Iterable var26 = var31.getParams();
                  var var16 = new ArrayList();

                  for (Object var27 : var26) {
                     if (var20.contains(((GalaxyApi.Param)var27).getKey())) {
                        var16.add(var27);
                     }
                  }

                  var15 = var16;
               } else {
                  Iterable var33 = var31.getParams();
                  var var28 = new ArrayList();

                  for (Object var36 : var33) {
                     if (!var20.contains(((GalaxyApi.Param)var36).getKey())) {
                        var28.add(var36);
                     }
                  }

                  var15 = var28;
               }
            }

            Iterable var21 = var15;
            boolean var4 = false;
            var var35 = new ArrayList();

            for (Object var40 : var21) {
               label93: {
                  GalaxyApi.Param var39 = (GalaxyApi.Param)var40;
                  boolean var5;
                  if (var39.getParentKey() == null) {
                     var var29 = var15;
                     if (var29 instanceof Collection && ((Collection)var29).isEmpty()) {
                        var3 = false;
                     } else {
                        Iterator var30 = var29.iterator();

                        while (true) {
                           if (!var30.hasNext()) {
                              var3 = false;
                              break;
                           }

                           String var41 = ((GalaxyApi.Param)var30.next()).getParentKey();
                           if (Intrinsics.areEqual(var41, var39.getKey())) {
                              var3 = true;
                              break;
                           }
                        }
                     }

                     var5 = var4;
                     if (var3) {
                        var3 = true;
                        break label93;
                     }
                  } else {
                     var5 = var4;
                  }

                  var3 = false;
                  var4 = var5;
               }

               if (var3) {
                  var35.add(var40);
               }
            }

            List var22 = var35;
            if (var15.size() > 24 && var22.size() >= 2) {
               return this.subTabbedCatalog(var8, var15, var22, var9, var2);
            } else {
               CatalogPanel var17 = new CatalogPanel(this, var8, var15, var9, true, new SettingsActivity$$ExternalSyntheticLambda159(this));
               FrameLayout var23 = pageBox$default(this, (View)var17, var2, false, 4, null);
               var23.setTag(new SettingsActivity$$ExternalSyntheticLambda160(var17, this));
               return (View)var23;
            }
         }
      }
   }

   // $VF: synthetic method
   static View catalogPage$default(SettingsActivity var0, String var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return var0.catalogPage(var1, var2);
   }

   private static final void catalogPage$lambda$87(SettingsActivity var0, View var1, String var2) {
      if (var0.current == var1) {
         var0.panels.remove("cat:" + var2);
         var0.open("cat:" + var2);
      }
   }

   private static final Unit catalogPage$lambda$92(SettingsActivity var0, String var1) {
      Intrinsics.checkNotNullParameter(var1, "route");
      var0.openRoute(var1);
      return Unit.INSTANCE;
   }

   private static final void catalogPage$lambda$94$lambda$93(CatalogPanel var0, SettingsActivity var1) {
      var0.syncAll();
      var1.current = (View)var0;
   }

   private final void choiceCard(
      LinearLayout var1, String var2, String var3, List<Pair<String, String>> var4, Function0<String> var5, Function1<? super String, Unit> var6
   ) {
      LinearLayout var12 = new LinearLayout((Context)this);
      var12.setOrientation(1);
      CarUi var10 = this.ui;
      if (var10 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var10 = null;
      }

      var12.setBackground((Drawable)CarUi.rounded$default(var10, CarUi.Companion.getCARD(), 0, null, 6, null));
      CarUi var11 = this.ui;
      var10 = var11;
      if (var11 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var10 = null;
      }

      int var9 = var10.dp(20);
      var11 = this.ui;
      var10 = var11;
      if (var11 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var10 = null;
      }

      int var7 = var10.dp(14);
      var11 = this.ui;
      var10 = var11;
      if (var11 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var10 = null;
      }

      int var8 = var10.dp(18);
      var11 = this.ui;
      var10 = var11;
      if (var11 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var10 = null;
      }

      var12.setPadding(var9, var7, var8, var10.dp(14));
      var10 = this.ui;
      if (var10 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var10 = null;
      }

      LinearLayout var13 = (LinearLayout)CarUi.row$default(var10, var2, var3, 0, null, null, 28, null).component1();
      var13.setBackground(null);
      var13.setPadding(0, 0, 0, 0);
      var13.setMinimumHeight(0);
      var12.addView((View)var13);
      ObjectRef var33 = new ObjectRef();
      CarUi var19 = this.ui;
      CarUi var14 = var19;
      if (var19 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var14 = null;
      }

      Iterable var37 = var4;
      var var20 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var37, 10));
      Iterator var38 = var37.iterator();

      while (var38.hasNext()) {
         var20.add((String)((Pair)var38.next()).getSecond());
      }

      List var21 = var20;
      Pair var15 = var14.chips(var21, new SettingsActivity$$ExternalSyntheticLambda188(var6, var4, var33));
      LinearLayout var26 = (LinearLayout)var15.component1();
      var33.element = (T)((Function1)var15.component2());
      CarUi var22 = this.ui;
      CarUi var16 = var22;
      if (var22 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var16 = null;
      }

      var26.setPadding(0, var16.dp(10), 0, 0);
      var12.addView((View)var26);
      Function1 var17;
      if (var33.element == null) {
         Intrinsics.throwUninitializedPropertyAccessException("paint");
         var17 = null;
      } else {
         var17 = (Function1)var33.element;
      }

      var7 = 0;
      Iterator var23 = var4.iterator();

      while (true) {
         if (!var23.hasNext()) {
            var7 = -1;
            break;
         }

         if (Intrinsics.areEqual(((Pair)var23.next()).getFirst(), var5.invoke())) {
            break;
         }

         var7++;
      }

      var17.invoke(var7);
      View var25 = (View)var12;
      CarUi var24 = this.ui;
      CarUi var18 = var24;
      if (var24 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var18 = null;
      }

      var1.addView(var25, (LayoutParams)CarUi.lp$default(var18, 0, 0, 3, null));
   }

   private static final Unit choiceCard$lambda$151(Function1 var0, List var1, ObjectRef var2, int var3) {
      var0.invoke(((Pair)var1.get(var3)).getFirst());
      if (var2.element == null) {
         Intrinsics.throwUninitializedPropertyAccessException("paint");
         var0 = null;
      } else {
         var0 = (Function1)var2.element;
      }

      var0.invoke(var3);
      return Unit.INSTANCE;
   }

   private final View comboPage(List<? extends View> var1) {
      return (View)(new SettingsActivity.ComboPage(this, var1));
   }

   private final void confirmDriveState(String var1, String var2) {
      if (!Intrinsics.areEqual(var1, this.currentDriveState())) {
         String var3;
         if (Intrinsics.areEqual(var1, "onroad")) {
            var3 = "Force the comma ONROAD?\n\nopenpilot starts all driving processes even with the car off. Use for testing; set it back to Auto before driving normally.";
         } else if (Intrinsics.areEqual(var1, "offroad")) {
            var3 = "Force the comma OFFROAD?\n\nopenpilot stops all driving processes - it will NOT drive, even with the car on. Set it back to Auto before driving.";
         } else {
            var3 = "Back to Auto?\n\nThe comma goes onroad / offroad by itself with the car's ignition again.";
         }

         new Builder((Context)this)
            .setTitle("Drive state: " + var2)
            .setMessage(var3)
            .setPositiveButton(var2, new SettingsActivity$$ExternalSyntheticLambda183(this, var1))
            .setNegativeButton("Cancel", null)
            .show();
      }
   }

   private static final void confirmDriveState$lambda$67(SettingsActivity var0, String var1, DialogInterface var2, int var3) {
      DataClient var4 = Link.INSTANCE.getData();
      if (var4 != null && var4.getConnected()) {
         var4.request("drive_state", MapsKt.mapOf(TuplesKt.to("mode", var1)), new SettingsActivity$$ExternalSyntheticLambda182(var0));
      } else {
         Toast.makeText((Context)var0, "Not connected to the comma", 0).show();
      }
   }

   private static final Unit confirmDriveState$lambda$67$lambda$66(SettingsActivity var0, JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "ack");
      if (!var1.optBoolean("ok")) {
         Toast.makeText((Context)var0, var1.optString("error", "rejected"), 0).show();
      }

      DriveState.INSTANCE.mode(true, new SettingsActivity$$ExternalSyntheticLambda33(var0));
      return Unit.INSTANCE;
   }

   private static final Unit confirmDriveState$lambda$67$lambda$66$lambda$65(SettingsActivity var0) {
      var0.syncDriveState();
      return Unit.INSTANCE;
   }

   private final void confirmRestart() {
      Builder var2 = new Builder((Context)this).setTitle("Restart openpilot?");
      String var1;
      if (Intrinsics.areEqual(this.currentDriveState(), "onroad")) {
         var1 = "Onroad";
      } else {
         var1 = "Auto";
      }

      var2.setMessage(
            "The comma goes offroad, waits until openpilot has stopped (about 10-20 s), then goes back to "
               + var1
               + ".\n\nUse it parked or stopped: openpilot doesn't drive while it restarts. The big model reloads on the eGPU after it starts again."
         )
         .setPositiveButton("Restart", new SettingsActivity$$ExternalSyntheticLambda185(this))
         .setNegativeButton("Cancel", null)
         .show();
   }

   private static final void confirmRestart$lambda$64(SettingsActivity var0, DialogInterface var1, int var2) {
      DriveState.INSTANCE.restart(var0, new SettingsActivity$$ExternalSyntheticLambda120(var0));
   }

   private static final Unit confirmRestart$lambda$64$lambda$63(SettingsActivity var0, boolean var1) {
      var0.syncDriveState();
      return Unit.INSTANCE;
   }

   private final String currentDriveState() {
      return DriveState.mode$default(DriveState.INSTANCE, false, new SettingsActivity$$ExternalSyntheticLambda121(this), 1, null);
   }

   private static final Unit currentDriveState$lambda$60(SettingsActivity var0) {
      var0.syncDriveState();
      return Unit.INSTANCE;
   }

   private final View driveStateSwitch() {
      CarUi var1 = this.ui;
      Object var2 = null;
      if (var1 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var1 = null;
      }

      TextView var3 = CarUi.button$default(var1, "Auto", false, new SettingsActivity$$ExternalSyntheticLambda44(this), 2, null);
      var1 = this.ui;
      if (var1 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var1 = (CarUi)var2;
      }

      var3.setMinWidth(var1.dp(170));
      this.driveBtn = var3;
      this.syncDriveState();
      return (View)var3;
   }

   private static final Unit driveStateSwitch$lambda$59(SettingsActivity var0) {
      String var3 = var0.currentDriveState();
      Iterable var1 = var0.driveModes;
      var var4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var1, 10));

      for (Pair var7 : var1) {
         String var6 = (String)var7.component1();
         String var2 = (String)var7.component2();
         String var8 = var2;
         if (Intrinsics.areEqual(var6, var3)) {
            var8 = var2 + "   (current)";
         }

         var4.add(var8);
      }

      List var9 = var4;
      String[] var10 = CollectionsKt.plus(var9, "Restart openpilot (offroad, then back)").toArray(new String[0]);
      new Builder((Context)var0)
         .setTitle("Drive state")
         .setItems(var10, new SettingsActivity$$ExternalSyntheticLambda2(var0))
         .setNegativeButton("Cancel", null)
         .show();
      return Unit.INSTANCE;
   }

   private static final void driveStateSwitch$lambda$59$lambda$58(SettingsActivity var0, DialogInterface var1, int var2) {
      if (var2 == var0.driveModes.size()) {
         var0.confirmRestart();
      } else {
         Pair var3 = var0.driveModes.get(var2);
         var0.confirmDriveState((String)var3.component1(), (String)var3.component2());
      }
   }

   private final GalaxySettingsView.Root galaxyExtras(String var1) {
      GalaxySettingsView.Root var2 = this.galaxyRoot(var1);
      switch (var1.hashCode()) {
         case 110999:
            if (var1.equals("pip")) {
               return new GalaxySettingsView.Root(
                  var2.getTitle(),
                  var2.getPage(),
                  CollectionsKt.listOf(new GalaxySettingsView.Entry.Web("Camera crop", "Pick the part of the side camera picture to show", "/cameras/pip")),
                  null,
                  8,
                  null
               );
            }
            break;
         case 342069036:
            if (var1.equals("vehicle")) {
               return new GalaxySettingsView.Root(
                  var2.getTitle(),
                  var2.getPage(),
                  CollectionsKt.listOf(
                     new GalaxySettingsView.Entry.Web(
                        "Car selection & tools", "Pick the car (or Auto) for the next start, Toyota door and key tools", "/vehicle"
                     )
                  ),
                  var2.getNote()
               );
            }
            break;
         case 1796717668:
            if (var1.equals("appearance")) {
               return new GalaxySettingsView.Root(
                  var2.getTitle(),
                  var2.getPage(),
                  CollectionsKt.listOf(
                     new GalaxySettingsView.Entry.Web(
                        "Widgets, colors & layout", "Where each widget sits on the comma's screen, colors, path style, clock", "/theme_maker"
                     )
                  ),
                  null,
                  8,
                  null
               );
            }
      }

      return var2;
   }

   private final View galaxyPage(String var1) {
      String var3 = this.host;
      if (var3 == null) {
         return this.offline("Not connected to the comma. Connect first, then come back.");
      } else {
         Context var4 = (Context)this;
         CarUi var2 = this.ui;
         if (var2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var2 = null;
         }

         GalaxySettingsView.Root var5 = this.galaxyExtras(var1);
         SettingsActivity$$ExternalSyntheticLambda109 var6 = new SettingsActivity$$ExternalSyntheticLambda109(this);
         Object var7;
         if (Intrinsics.areEqual(var1, "sounds")) {
            var7 = new SettingsActivity$$ExternalSyntheticLambda112(this);
         } else if (Intrinsics.areEqual(var1, "developer")) {
            var7 = new SettingsActivity$$ExternalSyntheticLambda113(this);
         } else {
            var7 = null;
         }

         var7 = new GalaxySettingsView(var4, var2, var5, var6, (Function1)var7, null, 32, null);
         if (Link.INSTANCE.getHost() == null) {
            Link.INSTANCE.setHost(var3);
         }

         return (View)var7;
      }
   }

   private static final Unit galaxyPage$lambda$14(SettingsActivity var0, String var1, String var2) {
      Intrinsics.checkNotNullParameter(var1, "path");
      Intrinsics.checkNotNullParameter(var2, "title");
      var0.openGalaxyWeb(var1, var2);
      return Unit.INSTANCE;
   }

   private static final Unit galaxyPage$lambda$15(SettingsActivity var0, LinearLayout var1) {
      Intrinsics.checkNotNullParameter(var1, "col");
      GalaxyExtras var3 = GalaxyExtras.INSTANCE;
      Context var4 = (Context)var0;
      CarUi var2 = var0.ui;
      CarUi var5 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var5 = null;
      }

      var3.soundPacks(var4, var5, var1);
      return Unit.INSTANCE;
   }

   private static final Unit galaxyPage$lambda$16(SettingsActivity var0, LinearLayout var1) {
      Intrinsics.checkNotNullParameter(var1, "col");
      GalaxyExtras var3 = GalaxyExtras.INSTANCE;
      Context var4 = (Context)var0;
      CarUi var2 = var0.ui;
      CarUi var5 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var5 = null;
      }

      var3.cloudProvider(var4, var5, var1);
      return Unit.INSTANCE;
   }

   private final String galaxyRailFor(String var1) {
      var1 = StringsKt.substringBefore$default(var1, '/', null, 2, null);
      switch (var1.hashCode()) {
         case -1067310595:
            if (var1.equals("traffic")) {
               return "galaxy:personalities";
            }
            break;
         case -1002263574:
            if (var1.equals("profiles")) {
               return "galaxy:speed";
            }
            break;
         case -905947169:
            if (var1.equals("sentry")) {
               return "galaxy:cameras";
            }
            break;
         case -867966454:
            if (var1.equals("torque")) {
               return "galaxy:steering";
            }
            break;
         case 96766:
            if (var1.equals("aol")) {
               return "galaxy:steering";
            }
            break;
         case 113962:
            if (var1.equals("slc")) {
               return "galaxy:speed";
            }
            break;
         case 3314156:
            if (var1.equals("lane")) {
               return "galaxy:steering";
            }
            break;
         case 3612229:
            if (var1.equals("vasm")) {
               return "galaxy:cameras";
            }
            break;
         case 95027439:
            if (var1.equals("curve")) {
               return "galaxy:speed";
            }
            break;
         case 1090497327:
            if (var1.equals("relaxed")) {
               return "galaxy:personalities";
            }
            break;
         case 1147132932:
            if (var1.equals("aggressive")) {
               return "galaxy:personalities";
            }
            break;
         case 1208131206:
            if (var1.equals("conditional")) {
               return "galaxy:speed";
            }
            break;
         case 1312628413:
            if (var1.equals("standard")) {
               return "galaxy:personalities";
            }
            break;
         case 1641332067:
            if (var1.equals("lane_change")) {
               return "galaxy:steering";
            }
      }

      return "drivingmap";
   }

   private final GalaxySettingsView.Root galaxyRoot(String var1) {
      switch (var1.hashCode()) {
         case 3357525:
            if (var1.equals("more")) {
               List var7 = CollectionsKt.listOf(new GalaxySettingsView.Entry.Unplaced(PLACED_PAGES));
               return new GalaxySettingsView.Root(
                  "More from Galaxy",
                  null,
                  var7,
                  "Settings pages Galaxy has that aren't placed in a rail yet - new ones from StarPilot updates show up here.",
                  2,
                  null
               );
            }
            break;
         case 109641799:
            if (var1.equals("speed")) {
               return new GalaxySettingsView.Root(
                  "Speed & following",
                  null,
                  CollectionsKt.listOf(new GalaxySettingsView.Entry.Hub(CollectionsKt.listOf(new String[]{"profiles", "conditional", "curve", "slc"}))),
                  null,
                  10,
                  null
               );
            }
            break;
         case 342069036:
            if (var1.equals("vehicle")) {
               return new GalaxySettingsView.Root(
                  "Vehicle", "vehicle", null, "Choosing the car (or Auto) and the Toyota tools are under Car selection & tools below.", 4, null
               );
            }
            break;
         case 549364206:
            if (var1.equals("cameras")) {
               GalaxySettingsView.Entry.Page var4 = new GalaxySettingsView.Entry.Page(
                  "Sentry motion monitoring", "Watch for motion while parked: sensitivity and warning time", "sentry"
               );
               GalaxySettingsView.Entry.Web var2 = new GalaxySettingsView.Entry.Web(
                  "Sentry events & notifications", "What it caught, and where it sends alerts", "/cameras/events"
               );
               GalaxySettingsView.Entry.Page var3 = new GalaxySettingsView.Entry.Page(
                  "V-ASM spot monitoring", "Draw the side windows in the cabin camera, and visual blind-spot warning options", "vasm"
               );
               GalaxySettingsView.Entry.Web var6 = new GalaxySettingsView.Entry.Web("Live cameras", "Cabin and side camera previews", "/cameras");
               return new GalaxySettingsView.Root(
                  "Cameras & monitoring", null, CollectionsKt.listOf(new GalaxySettingsView.Entry[]{var4, var2, var3, var6}), null, 10, null
               );
            }
            break;
         case 590460812:
            if (var1.equals("personalities")) {
               return new GalaxySettingsView.Root(
                  "Personalities",
                  null,
                  CollectionsKt.listOf(new GalaxySettingsView.Entry.Hub(CollectionsKt.listOf(new String[]{"traffic", "aggressive", "standard", "relaxed"}))),
                  null,
                  10,
                  null
               );
            }
            break;
         case 1419257969:
            if (var1.equals("steering")) {
               return new GalaxySettingsView.Root(
                  "Steering",
                  null,
                  CollectionsKt.listOf(new GalaxySettingsView.Entry.Hub(CollectionsKt.listOf(new String[]{"lane", "lane_change", "torque", "aol"}))),
                  null,
                  10,
                  null
               );
            }
      }

      String var9 = GALAXY_TITLES.get(var1);
      String var8 = var9;
      if (var9 == null) {
         var9 = GALAXY_SUB_TITLES.get(var1);
         var8 = var9;
         if (var9 == null) {
            var8 = var1;
         }
      }

      return new GalaxySettingsView.Root(var8, var1, null, null, 12, null);
   }

   private final List<SettingsActivity.Rail> galaxyToolRails() {
      Iterable var3 = GalaxyTools.INSTANCE.list((Context)this);
      var var2 = new ArrayList();

      for (var var3_it : var3) {

         var3 = var3_it;
         GalaxyTools.Tool var5 = (GalaxyTools.Tool)var3;
         if (!GalaxyTools.INSTANCE.getPLACED_ELSEWHERE().contains(var5.getPath())) {
            var2.add(var3);
         }
      }

      List var13 = var2;
      Collection var14 = CollectionsKt.listOf(new SettingsActivity.Rail("All tools", R.drawable.ic_tools, "toolshome"));
      Iterable var10 = var13;
      var var6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var10, 10));

      for (GalaxyTools.Tool var9 : var10) {
         String var8 = var9.getName();
         int var1 = GalaxyTools.INSTANCE.icon(var9.getPath());
         String var7 = var9.getPath();
         String var16 = var9.getName();
         var6.add(new SettingsActivity.Rail(var8, var1, "gweb:" + var7 + "|" + var16));
      }

      List var11 = var6;
      return CollectionsKt.plus(var14, var11);
   }

   private final View gxPage(String var1) {
      switch (var1.hashCode()) {
         case -1785238953:
            if (var1.equals("favorites")) {
               return (View)(new GxFavoritesPage(this, this));
            }
            break;
         case -1253268720:
            if (var1.equals("galaxy")) {
               return (View)(new GxGalaxyPage(this, this));
            }
            break;
         case -1068799382:
            if (var1.equals("models")) {
               return (View)(new GxModelsPage(this, this));
            }
            break;
         case -887328209:
            if (var1.equals("system")) {
               return (View)(new GxSystemPage(this, this));
            }
            break;
         case -862552395:
            if (var1.equals("tuning")) {
               return (View)(new GxTuningPage(this, this));
            }
            break;
         case -619025980:
            if (var1.equals("modellab")) {
               return (View)(new GxModelLabPage(this, this));
            }
            break;
         case 3208415:
            if (var1.equals("home")) {
               return (View)(new GxHomePage(this, this));
            }
            break;
         case 3327407:
            if (var1.equals("logs")) {
               return (View)(new GxLogsPage(this, this));
            }
            break;
         case 110327241:
            if (var1.equals("theme")) {
               return (View)(new GxThemePage(this, this));
            }
            break;
         case 342069036:
            if (var1.equals("vehicle")) {
               return (View)(new GxVehiclePage(this, this));
            }
            break;
         case 549364206:
            if (var1.equals("cameras")) {
               return (View)(new GxCamerasPage(this, this));
            }
            break;
         case 590460812:
            if (var1.equals("personalities")) {
               return (View)(new GxPersonalitiesPage(this, this));
            }
            break;
         case 735527074:
            if (var1.equals("recordings")) {
               return (View)(new GxRecordingsPage(this, this));
            }
            break;
         case 1862666772:
            if (var1.equals("navigation")) {
               return (View)(new GxNavigationPage(this, this));
            }
            break;
         case 1968882350:
            if (var1.equals("bluetooth")) {
               return (View)(new GxBluetoothPage(this, this));
            }
      }

      return this.offline("Unknown page " + var1);
   }

   private final View headerBar(String var1, String var2, Function0<Unit> var3) {
      boolean var4;
      if (Intrinsics.areEqual(var1, "Settings") && var2 == null) {
         var4 = true;
      } else {
         var4 = false;
      }

      LinearLayout var10 = new LinearLayout((Context)this);
      var10.setOrientation(0);
      var10.setGravity(16);
      CarUi var9 = this.ui;
      CarUi var8 = var9;
      if (var9 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var8 = null;
      }

      var10.setPadding(0, 0, 0, var8.dp(14));
      ImageView var11 = new ImageView((Context)this);
      var11.setImageResource(R.drawable.ic_back);
      var11.setColorFilter(CarUi.Companion.getACCENT());
      var9 = this.ui;
      var8 = var9;
      if (var9 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var8 = null;
      }

      int var6 = var8.dp(8);
      var9 = this.ui;
      var8 = var9;
      if (var9 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var8 = null;
      }

      int var7 = var8.dp(8);
      var9 = this.ui;
      var8 = var9;
      if (var9 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var8 = null;
      }

      int var5 = var8.dp(8);
      var9 = this.ui;
      var8 = var9;
      if (var9 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var8 = null;
      }

      var11.setPadding(var6, var7, var5, var8.dp(8));
      var11.setOnClickListener(new SettingsActivity$$ExternalSyntheticLambda91(var3));
      View var59 = (View)var11;
      var9 = this.ui;
      var8 = var9;
      if (var9 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var8 = null;
      }

      var5 = var8.dp(56);
      var9 = this.ui;
      var8 = var9;
      if (var9 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var8 = null;
      }

      var10.addView(var59, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(var5, var8.dp(56))));
      TextView var58 = new TextView((Context)this);
      var58.setText(var1);
      var58.setTextColor(CarUi.Companion.getTEXT());
      var58.setTextSize(28.0F);
      var58.setTypeface(Typeface.DEFAULT_BOLD);
      var8 = this.ui;
      CarUi var12 = var8;
      if (var8 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var12 = null;
      }

      var58.setPadding(var12.dp(6), 0, 0, 0);
      var58.setOnClickListener(new SettingsActivity$$ExternalSyntheticLambda92(var3));
      var10.addView((View)var58);
      if (var2 != null) {
         TextView var34 = new TextView((Context)this);
         var34.setText(var2);
         var34.setTextColor(CarUi.Companion.getDIM());
         var34.setTextSize(18.0F);
         CarUi var26 = this.ui;
         CarUi var13 = var26;
         if (var26 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var13 = null;
         }

         var5 = var13.dp(16);
         CarUi var27 = this.ui;
         CarUi var14 = var27;
         if (var27 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var14 = null;
         }

         var34.setPadding(var5, var14.dp(6), 0, 0);
         var10.addView((View)var34);
      }

      if (var4) {
         var10.addView(new View((Context)this), (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, 1, 1.0F)));
      } else {
         View var35 = new View((Context)this);
         CarUi var28 = this.ui;
         CarUi var15 = var28;
         if (var28 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var15 = null;
         }

         byte var41;
         if (this.getResources().getConfiguration().screenWidthDp < 1150) {
            var41 = 40;
         } else {
            var41 = 120;
         }

         var10.addView(var35, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(var15.dp(var41), 1)));
         FrameLayout var16 = new FrameLayout((Context)this);
         this.headerSlot = var16;
         View var36 = (View)var16;
         android.widget.LinearLayout.LayoutParams var49 = new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F);
         CarUi var29 = this.ui;
         CarUi var17 = var29;
         if (var29 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var17 = null;
         }

         var49.rightMargin = var17.dp(14);
         Unit var18 = Unit.INSTANCE;
         var10.addView(var36, (LayoutParams)var49);
         DriveState.OffroadBar var30 = this.offroadBar;
         if (var30 != null) {
            ViewParent var19 = var30.getView().getParent();
            ViewGroup var20;
            if (var19 instanceof ViewGroup) {
               var20 = (ViewGroup)var19;
            } else {
               var20 = null;
            }

            if (var20 != null) {
               var20.removeView((View)var30.getView());
            }

            View var37 = (View)var30.getView();
            CarUi var31 = this.ui;
            CarUi var21 = var31;
            if (var31 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("ui");
               var21 = null;
            }

            android.widget.LinearLayout.LayoutParams var50 = new android.widget.LinearLayout.LayoutParams(-2, var21.dp(56));
            CarUi var32 = this.ui;
            CarUi var22 = var32;
            if (var32 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("ui");
               var22 = null;
            }

            var50.rightMargin = var22.dp(14);
            Unit var23 = Unit.INSTANCE;
            var10.addView(var37, (LayoutParams)var50);
         }
      }

      CarUi var24 = this.ui;
      if (var24 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var24 = null;
      }

      var10.addView((View)var24.button("Done", true, new SettingsActivity$$ExternalSyntheticLambda93(this)));
      if (!var4) {
         return (View)var10;
      } else {
         FrameLayout var25 = new FrameLayout((Context)this);
         var25.addView((View)var10, (LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -2)));
         View var51 = this.driveStateSwitch();
         android.widget.FrameLayout.LayoutParams var33 = new android.widget.FrameLayout.LayoutParams(-2, -2, 49);
         var33.topMargin = 0;
         Unit var38 = Unit.INSTANCE;
         var25.addView(var51, (LayoutParams)var33);
         return (View)var25;
      }
   }

   private static final void headerBar$lambda$47$lambda$46(Function0 var0, View var1) {
      var0.invoke();
   }

   private static final void headerBar$lambda$49$lambda$48(Function0 var0, View var1) {
      var0.invoke();
   }

   private static final Unit headerBar$lambda$54(SettingsActivity var0) {
      var0.finish();
      return Unit.INSTANCE;
   }

   private final void hideGalaxy(View var1) {
      boolean var2 = var1 instanceof GalaxySettingsView;
      Object var4 = null;
      GalaxySettingsView var3;
      if (var2) {
         var3 = (GalaxySettingsView)var1;
      } else {
         var3 = null;
      }

      if (var3 != null) {
         var3.hide();
      }

      GalaxyExtras.QuickSelect var5;
      if (var1 instanceof GalaxyExtras.QuickSelect) {
         var5 = (GalaxyExtras.QuickSelect)var1;
      } else {
         var5 = null;
      }

      if (var5 != null) {
         var5.hide();
      }

      CommaHealthView var6;
      if (var1 instanceof CommaHealthView) {
         var6 = (CommaHealthView)var1;
      } else {
         var6 = null;
      }

      if (var6 != null) {
         var6.stop();
      }

      DoorsBeltsView var7;
      if (var1 instanceof DoorsBeltsView) {
         var7 = (DoorsBeltsView)var1;
      } else {
         var7 = null;
      }

      if (var7 != null) {
         var7.stop();
      }

      KeyFobView var8;
      if (var1 instanceof KeyFobView) {
         var8 = (KeyFobView)var1;
      } else {
         var8 = null;
      }

      if (var8 != null) {
         var8.stop();
      }

      HubPowerView var9;
      if (var1 instanceof HubPowerView) {
         var9 = (HubPowerView)var1;
      } else {
         var9 = null;
      }

      if (var9 != null) {
         var9.stop();
      }

      BookmarksView var10 = (BookmarksView)var4;
      if (var1 instanceof BookmarksView) {
         var10 = (BookmarksView)var1;
      }

      if (var10 != null) {
         var10.stop();
      }

      if (var1 instanceof GalaxyWeb) {
         GalaxyWebPool.INSTANCE.park();
      }
   }

   private final View homeAreas() {
      GridLayout var8 = new GridLayout((Context)this);
      var8.setColumnCount(3);
      var8.setRowCount(2);
      var8.setUseDefaultMargins(false);
      List var5 = this.areas();
      SettingsActivity.Quad var9 = new SettingsActivity.Quad(0, 0, 0, "Driving behaviour, comma system");
      SettingsActivity.Quad var6 = new SettingsActivity.Quad(1, 0, 1, "What the comma's screen shows");
      SettingsActivity.Quad var7 = new SettingsActivity.Quad(4, 0, 2, "Galaxy's tools: models, recordings, logs…");
      DataClient var4 = Link.INSTANCE.getData();
      boolean var1;
      if (var4 != null && var4.getConnected()) {
         var1 = 1;
      } else {
         var1 = 0;
      }

      String var16;
      if (var1) {
         var16 = "Linked to the comma";
      } else {
         var16 = "Connection, camera, tools";
      }

      SettingsActivity.Quad var17 = new SettingsActivity.Quad(2, 1, 0, var16);

      for (var var6_it : CollectionsKt.listOf(new SettingsActivity.Quad[]{var9, var6, var7, var17, new SettingsActivity.Quad(3, 1, 1, "What this tablet draws")})) {

         var6 = var6_it;
         int var3 = var6.component1();
         var1 = var6.component2();
         int var2 = var6.component3();
         String var10 = var6.component4();
         SettingsActivity.Area var32 = (SettingsActivity.Area)var5.get(var3);
         CarUi var26 = this.ui;
         CarUi var20 = var26;
         if (var26 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var20 = null;
         }

         String var11 = var32.getTitle();
         String var27 = var32.getSub();
         View var33 = (View)var20.tile(var11 + " · " + var27, var10, var32.getIcon(), 64, new SettingsActivity$$ExternalSyntheticLambda11(this, var3));
         android.widget.GridLayout.LayoutParams var34 = new android.widget.GridLayout.LayoutParams(GridLayout.spec(var1, 1.0F), GridLayout.spec(var2, 1.0F));
         var34.width = 0;
         var34.height = 0;
         CarUi var28 = this.ui;
         CarUi var21 = var28;
         if (var28 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var21 = null;
         }

         var1 = var21.dp(10);
         CarUi var29 = this.ui;
         CarUi var22 = var29;
         if (var29 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var22 = null;
         }

         var3 = var22.dp(10);
         CarUi var30 = this.ui;
         CarUi var23 = var30;
         if (var30 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var23 = null;
         }

         var2 = var23.dp(10);
         CarUi var31 = this.ui;
         CarUi var24 = var31;
         if (var31 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var24 = null;
         }

         var34.setMargins(var1, var3, var2, var24.dp(10));
         Unit var25 = Unit.INSTANCE;
         var8.addView(var33, (LayoutParams)var34);
      }

      return (View)var8;
   }

   private static final Unit homeAreas$lambda$35(SettingsActivity var0, int var1) {
      var0.showArea(var1, null);
      return Unit.INSTANCE;
   }

   private final String lanModelText(LaneModel var1) {
      String var4;
      if (var1.getHasModel()) {
         String var2 = String.format("%.1f", Arrays.copyOf(new Object[]{var1.modelFile().length() / 1000000.0}, 1));
         Intrinsics.checkNotNullExpressionValue(var2, "format(...)");
         String var3 = new SimpleDateFormat("MMM d", Locale.US).format(new Date(var1.modelFile().lastModified()));
         var4 = "Installed (" + var2 + " MB, " + var3 + "): the lane card uses it on city streets (with the lane card on and the camera detector running)";
      } else {
         var4 = "Not installed: download lane_count_v1.lite from the chat on this tablet, then tap Import";
      }

      return var4;
   }

   private final View languagePage() {
      boolean var1;
      String var3;
      ScrollView var5;
      LinearLayout var6;
      List var7;
      String var10;
      label19: {
         Pair var2 = this.page();
         var5 = (ScrollView)var2.component1();
         var6 = (LinearLayout)var2.component2();
         var3 = "en";
         Pair var8 = TuplesKt.to("en", "English");
         var1 = false;
         var7 = CollectionsKt.listOf(
            new Pair[]{var8, TuplesKt.to("es", "Spanish"), TuplesKt.to("fr", "French"), TuplesKt.to("ko", "Korean"), TuplesKt.to("zh-CHS", "Chinese")}
         );
         GalaxyApi var9 = this.api;
         if (var9 != null) {
            String var4 = var9.string("LanguageSetting");
            var10 = var4;
            if (var4 != null) {
               break label19;
            }
         }

         var10 = "";
      }

      var10 = StringsKt.removePrefix(var10, "main_");
      if (var10.length() == 0) {
         var1 = true;
      }

      if (var1) {
         var10 = var3;
      }

      this.choiceCard(
         var6,
         "Language",
         "Select language. Galaxy uses English when no language is selected.",
         var7,
         new SettingsActivity$$ExternalSyntheticLambda131(var10),
         new SettingsActivity$$ExternalSyntheticLambda132(this)
      );
      return (View)var5;
   }

   private static final String languagePage$lambda$137(String var0) {
      return var0;
   }

   private static final Unit languagePage$lambda$139(SettingsActivity var0, String var1) {
      Intrinsics.checkNotNullParameter(var1, "code");
      GalaxyApi var2 = var0.api;
      if (var2 != null) {
         GalaxyApi.put$default(var2, "LanguageSetting", "main_" + var1, null, new SettingsActivity$$ExternalSyntheticLambda111(var0), 4, null);
      }

      return Unit.INSTANCE;
   }

   private static final Unit languagePage$lambda$139$lambda$138(SettingsActivity var0, String var1) {
      Context var2 = (Context)var0;
      if (var1 == null) {
         var1 = "Language updated.";
      }

      Toast.makeText(var2, var1, 0).show();
      return Unit.INSTANCE;
   }

   private final void livePoll(View var1, Runnable var2) {
      var1.addOnAttachStateChangeListener(new OnAttachStateChangeListener(this, var2) {
         final Runnable $poll;
         final SettingsActivity this$0;

         {
            this.this$0 = var1;
            this.$poll = var2x;
         }

         public void onViewAttachedToWindow(View var1) {
            Intrinsics.checkNotNullParameter(var1, "v");
            this.this$0.handler.removeCallbacks(this.$poll);
            this.this$0.handler.post(this.$poll);
         }

         public void onViewDetachedFromWindow(View var1) {
            Intrinsics.checkNotNullParameter(var1, "v");
            this.this$0.handler.removeCallbacks(this.$poll);
         }
      });
      if (var1.isAttachedToWindow()) {
         this.handler.post(var2);
      }
   }

   private final View newIdeasPage() {
      Pair var1 = this.page();
      ScrollView var4 = (ScrollView)var1.component1();
      LinearLayout var6 = (LinearLayout)var1.component2();
      UiSettings var5 = new UiSettings((Context)this);
      Boolean var11 = Ideas.INSTANCE.getCommaOk();
      String var12;
      if (Intrinsics.areEqual(var11, true)) {
         var12 = "Comma: ready (patch 39).";
      } else if (Intrinsics.areEqual(var11, false)) {
         var12 = "Comma: needs the update with patch 39 - until then only the warnings show.";
      } else {
         if (var11 != null) {
            throw new NoWhenBranchMatchedException();
         }

         var12 = "Comma: not checked yet (it is checked when an idea first has advice).";
      }

      CarUi var3 = this.ui;
      CarUi var2 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var2 = null;
      }

      View var35 = (View)var2.note(
         "Experiments, one switch each - all off until you turn them on. All the thinking runs on this tablet; the comma only gets a tiny \"advice\" twice a second, and only to drive gentler: a lower set speed (never under 10 mph, never higher), up to 0.6 s more following room, or up to 1 ft over inside the lane (only while Lane Centering is on). A blue chip at the top of the drive screen always says why. "
            + var12
      );
      var2 = this.ui;
      CarUi var13 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var13 = null;
      }

      var6.addView(var35, (LayoutParams)CarUi.lp$default(var13, 0, 0, 3, null));
      String var18 = "";

      for (NewIdeas.Idea var8 : NewIdeas.INSTANCE.getALL()) {
         String var14 = var18;
         if (!Intrinsics.areEqual(var8.getSection(), var18)) {
            String var19 = var8.getSection();
            var3 = this.ui;
            CarUi var15 = var3;
            if (var3 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("ui");
               var15 = null;
            }

            View var9 = (View)var15.header(var19);
            var3 = this.ui;
            CarUi var16 = var3;
            if (var3 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("ui");
               var16 = null;
            }

            var6.addView(var9, (LayoutParams)CarUi.lp$default(var16, 0, 0, 3, null));
            var14 = var19;
         }

         label100: {
            String var20 = NewIdeas.INSTANCE.getFIRST().get(var8.getId());
            if (var20 != null) {
               String var38 = "\n" + var20;
               var21 = var38;
               if (var38 != null) {
                  break label100;
               }
            }

            var21 = "";
         }

         String var10 = var8.getTitle();
         String var39 = var8.getDesc();
         String var54 = this.status(var8.getId());
         this.toggleCard(
            var6,
            var10,
            var39 + var21 + var54,
            new SettingsActivity$$ExternalSyntheticLambda76(var5, var8),
            new SettingsActivity$$ExternalSyntheticLambda78(var5, var8, this)
         );
         String var22 = var8.getId();
         if (Intrinsics.areEqual(var22, "teach")) {
            LinearLayout var49 = new LinearLayout((Context)this);
            var49.setOrientation(0);
            var3 = this.ui;
            var2 = var3;
            if (var3 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("ui");
               var2 = null;
            }

            String var41;
            if (Ideas.INSTANCE.getMemory().getTeaching() != null) {
               var41 = "Stop and keep";
            } else {
               var41 = "Learn a path from here";
            }

            var49.addView((View)var2.button(var41, true, new SettingsActivity$$ExternalSyntheticLambda79(this)));
            var2 = this.ui;
            if (var2 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("ui");
               var2 = null;
            }

            View var55 = (View)CarUi.button$default(var2, "Forget taught paths", false, new SettingsActivity$$ExternalSyntheticLambda80(this), 2, null);
            android.widget.LinearLayout.LayoutParams var57 = new android.widget.LinearLayout.LayoutParams(-2, -2);
            var3 = this.ui;
            var2 = var3;
            if (var3 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("ui");
               var2 = null;
            }

            var57.setMarginStart(var2.dp(10));
            Unit var26 = Unit.INSTANCE;
            var49.addView(var55, (LayoutParams)var57);
            View var50 = (View)var49;
            var3 = this.ui;
            var2 = var3;
            if (var3 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("ui");
               var2 = null;
            }

            var6.addView(var50, (LayoutParams)CarUi.lp$default(var2, 0, 0, 3, null));
         } else if (Intrinsics.areEqual(var22, "friends_fleet")) {
            LinearLayout var51 = new LinearLayout((Context)this);
            var51.setOrientation(0);
            var3 = this.ui;
            var2 = var3;
            if (var3 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("ui");
               var2 = null;
            }

            var51.addView((View)var2.button("Share mine now", true, new SettingsActivity$$ExternalSyntheticLambda81(this)));
            var2 = this.ui;
            if (var2 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("ui");
               var2 = null;
            }

            View var58 = (View)CarUi.button$default(var2, "Take friends' in", false, new SettingsActivity$$ExternalSyntheticLambda82(this), 2, null);
            android.widget.LinearLayout.LayoutParams var56 = new android.widget.LinearLayout.LayoutParams(-2, -2);
            var3 = this.ui;
            var2 = var3;
            if (var3 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("ui");
               var2 = null;
            }

            var56.setMarginStart(var2.dp(10));
            Unit var31 = Unit.INSTANCE;
            var51.addView(var58, (LayoutParams)var56);
            View var52 = (View)var51;
            var3 = this.ui;
            var2 = var3;
            if (var3 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("ui");
               var2 = null;
            }

            var6.addView(var52, (LayoutParams)CarUi.lp$default(var2, 0, 0, 3, null));
            var3 = this.ui;
            var2 = var3;
            if (var3 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("ui");
               var2 = null;
            }

            View var53 = (View)var2.note(
               "Sync the Download/StarView/fleet folder with your friends (Google Drive app, or any shared folder). Each tablet writes its own file there after every drive and reads the others' when a drive starts."
            );
            var3 = this.ui;
            var2 = var3;
            if (var3 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("ui");
               var2 = null;
            }

            var6.addView(var53, (LayoutParams)CarUi.lp$default(var2, 0, 0, 3, null));
         }

         var18 = var14;
      }

      return (View)var4;
   }

   private static final boolean newIdeasPage$lambda$277(UiSettings var0, NewIdeas.Idea var1) {
      return var0.get(NewIdeas.INSTANCE.key(var1.getId()));
   }

   private static final Unit newIdeasPage$lambda$278(UiSettings var0, NewIdeas.Idea var1, SettingsActivity var2, boolean var3) {
      var0.set(NewIdeas.INSTANCE.key(var1.getId()), var3);
      if (var3
         && (Intrinsics.areEqual(var1.getId(), "sirens") || Intrinsics.areEqual(var1.getId(), "horn"))
         && var2.checkSelfPermission("android.permission.RECORD_AUDIO") != 0) {
         var2.requestPermissions(new String[]{"android.permission.RECORD_AUDIO"}, 77);
      }

      return Unit.INSTANCE;
   }

   private static final Unit newIdeasPage$lambda$280(SettingsActivity var0) {
      if (Ideas.INSTANCE.getMemory().getTeaching() == null) {
         Ideas.INSTANCE.getMemory().startTeaching();
         var0.toast("Learning: drive the path, then tap Stop and keep");
      } else {
         IdeasMemory var2 = Ideas.INSTANCE.getMemory();
         int var1 = Ideas.INSTANCE.getMemory().taughtCount();
         var1 = var2.stopTeaching("path " + (var1 + 1));
         String var4;
         if (var1 > 0) {
            var4 = "Kept: " + var1 + " points";
         } else {
            var4 = "Too short (needs RTK fixed)";
         }

         var0.toast(var4);
      }

      var0.open("tablet:ideas");
      return Unit.INSTANCE;
   }

   private static final Unit newIdeasPage$lambda$281(SettingsActivity var0) {
      Ideas.INSTANCE.getMemory().forgetTaught();
      var0.toast("Forgotten");
      var0.open("tablet:ideas");
      return Unit.INSTANCE;
   }

   private static final Unit newIdeasPage$lambda$286(SettingsActivity var0) {
      new Thread(new SettingsActivity$$ExternalSyntheticLambda117(var0)).start();
      return Unit.INSTANCE;
   }

   private static final void newIdeasPage$lambda$286$lambda$285(SettingsActivity var0) {
      var0.runOnUiThread(new SettingsActivity$$ExternalSyntheticLambda211(var0, IdeaSpots.INSTANCE.exportFleet(NewIdeas.INSTANCE.deviceName((Context)var0))));
   }

   private static final void newIdeasPage$lambda$286$lambda$285$lambda$284(SettingsActivity var0, int var1) {
      String var2;
      if (var1 >= 0) {
         var2 = "Shared " + var1 + " places to Download/StarView/fleet";
      } else {
         var2 = "Couldn't write there";
      }

      var0.toast(var2);
   }

   private static final Unit newIdeasPage$lambda$289(SettingsActivity var0) {
      new Thread(new SettingsActivity$$ExternalSyntheticLambda245(var0)).start();
      return Unit.INSTANCE;
   }

   private static final void newIdeasPage$lambda$289$lambda$288(SettingsActivity var0) {
      var0.runOnUiThread(new SettingsActivity$$ExternalSyntheticLambda89(var0, IdeaSpots.INSTANCE.importFleet(NewIdeas.INSTANCE.deviceName((Context)var0))));
   }

   private static final void newIdeasPage$lambda$289$lambda$288$lambda$287(SettingsActivity var0, int var1) {
      var0.toast(var1 + " places from friends");
   }

   private final View offline(String var1) {
      TextView var7 = new TextView((Context)this);
      var7.setText(var1);
      var7.setTextColor(CarUi.Companion.getDIM());
      var7.setTextSize(20.0F);
      var7.setGravity(17);
      CarUi var6 = this.ui;
      Object var5 = null;
      CarUi var8 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var8 = null;
      }

      int var2 = var8.dp(40);
      var6 = this.ui;
      CarUi var9 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var9 = null;
      }

      int var4 = var9.dp(40);
      var6 = this.ui;
      CarUi var10 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var10 = null;
      }

      int var3 = var10.dp(40);
      CarUi var11 = this.ui;
      if (var11 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var11 = (CarUi)var5;
      }

      var7.setPadding(var2, var4, var3, var11.dp(40));
      return (View)var7;
   }

   private static final SettingIcons.Icon onCreate$lambda$21$lambda$20(SettingsActivity var0, String var1) {
      Intrinsics.checkNotNullParameter(var1, "t");
      return SettingIcons.INSTANCE.forLabel(var1, var0.buildGroup);
   }

   private static final WindowInsetsCompat onCreate$lambda$23(View var0, WindowInsetsCompat var1) {
      Intrinsics.checkNotNullParameter(var0, "v");
      Intrinsics.checkNotNullParameter(var1, "insets");
      var0.setPadding(0, 0, 0, var1.getInsets(Type.ime()).bottom);
      return var1;
   }

   private static final void onCreate$lambda$26$lambda$25(SettingsActivity var0, String var1) {
      if (!var0.isFinishing() && !var0.isDestroyed()) {
         GalaxyWebPool.INSTANCE.warm((Context)var0, var1);
      }
   }

   private final void open(String var1) {
      FrameLayout var4 = this.contentHolder;
      if (var4 != null) {
         if (Intrinsics.areEqual(var1, "activity:files")) {
            this.startActivity(new Intent((Context)this, FilesActivity.class));
         } else if (Intrinsics.areEqual(var1, "activity:sensors")) {
            this.startActivity(new Intent((Context)this, SensorsActivity.class));
         } else if (Intrinsics.areEqual(var1, "activity:lanepics")) {
            this.startActivity(new Intent((Context)this, LanePicturesActivity.class));
         } else if (Intrinsics.areEqual(var1, "action:navigation")) {
            this.startActivity(new Intent((Context)this, MainActivity.class).putExtra("open_nav", true).addFlags(603979776));
            this.finish();
         } else {
            Map var3 = this.panels;
            GxPage var2 = (GxPage)var3.get(var1);
            if (var2 == null) {
               var2 = this.buildPage(var1);
               var3.put(var1, var2);
            }

            View var5 = (View)var2;
            this.currentPage = var1;
            Object var16 = null;
            this.webReturn = null;
            if (Intrinsics.areEqual(var1, "toggles:all")) {
               var2 = this.togglesSearch;
            } else {
               var2 = null;
            }

            this.setHeaderTool((View)var2);
            if (this.current != var5) {
               var2 = this.current;
               if (var2 instanceof GxPage) {
                  var2 = (GxPage)var2;
               } else {
                  var2 = null;
               }

               if (var2 != null) {
                  var2.hide();
               }

               this.hideGalaxy(this.current);
               var4.removeAllViews();
               TermWeb.INSTANCE.detach();
               ViewParent var11 = var5.getParent();
               ViewGroup var12;
               if (var11 instanceof ViewGroup) {
                  var12 = (ViewGroup)var11;
               } else {
                  var12 = null;
               }

               if (var12 != null) {
                  var12.removeView(var5);
               }

               var4.addView(var5, (LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -1)));
               this.current = var5;
            }

            if (var5 instanceof CommaHealthView) {
               ((CommaHealthView)var5).start();
            } else if (var5 instanceof CardPage) {
               ((CardPage)var5).refresh();
            } else if (var5 instanceof DrivingMapView) {
               ((DrivingMapView)var5).refresh();
            } else if (var5 instanceof ToolsHomeView) {
               ((ToolsHomeView)var5).refresh();
            } else if (var5 instanceof ConnectionsView) {
               ((ConnectionsView)var5).refresh();
            } else if (var5 instanceof GalaxySettingsView) {
               ((GalaxySettingsView)var5).show();
            } else if (var5 instanceof GalaxyExtras.QuickSelect) {
               ((GalaxyExtras.QuickSelect)var5).show();
            } else if (var5 instanceof DevicePanel) {
               ((DevicePanel)var5).refresh(true);
            } else if (var5 instanceof CatalogPanel) {
               GalaxyApi var13 = this.api;
               if (var13 != null) {
                  GalaxyApi.refreshValues$default(var13, null, 1, null);
               }

               ((CatalogPanel)var5).syncAll();
            } else if (var5 instanceof SettingsActivity.ComboPage) {
               GalaxyApi var14 = this.api;
               if (var14 != null) {
                  GalaxyApi.refreshValues$default(var14, null, 1, null);
               }

               ((SettingsActivity.ComboPage)var5).refresh();
            } else if (var5 instanceof GxPage) {
               ((GxPage)var5).show();
            } else if (var5 instanceof GalaxyWeb) {
               ((GalaxyWeb)var5).show();
            } else {
               Object var17 = var5.getTag();
               Runnable var15 = (Runnable)var16;
               if (var17 instanceof Runnable) {
                  var15 = (Runnable)var17;
               }

               if (var15 != null) {
                  var15.run();
               }
            }

            if (Intrinsics.areEqual(var1, "native:terminal")) {
               var4.removeAllViews();
               var1 = this.host;
               if (var1 == null) {
                  return;
               }

               WebView var7 = TermWeb.INSTANCE.get((Context)this, var1);
               TermWeb.INSTANCE.detach();
               var4.addView((View)var7, (LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -1)));
               var7.requestFocus();
               this.current = (View)var7;
            }
         }
      }
   }

   private final void openRoute(String var1) {
      if (StringsKt.contains$default(var1, "favorites", false, 2, null)) {
         var1 = "gx:favorites";
      } else if (StringsKt.contains$default(var1, "longitudinal", false, 2, null) || StringsKt.contains$default(var1, "personalit", false, 2, null)) {
         var1 = "gx:personalities";
      } else if (StringsKt.contains$default(var1, "model", false, 2, null)) {
         var1 = "gx:models";
      } else if (!StringsKt.contains$default(var1, "navigation", false, 2, null) && !StringsKt.contains$default(var1, "maps", false, 2, null)) {
         var1 = "gx:home";
      } else {
         var1 = "gx:navigation";
      }

      this.openPage(var1);
   }

   private final void openSub(String var1, String var2) {
      byte var3 = SetsKt.setOf(new String[]{"display", "appearance", "pip", "favorites"}).contains(var1);
      Iterable var6 = this.areas().get(var3).getRails();
      var var5 = new ArrayList();
      Iterator var11 = var6.iterator();

      while (var11.hasNext()) {
         CollectionsKt.addAll(var5, (Iterable)((Pair)var11.next()).getSecond());
      }

      List var8 = var5;
      Iterator var7 = var8.iterator();

      do {
         boolean var4 = var7.hasNext();
         var12 = null;
         if (!var4) {
            var5 = null;
            break;
         }

         var5 = (ArrayList)var7.next();
      } while (!Intrinsics.areEqual(((SettingsActivity.Rail)var5).getPage(), "galaxy:" + var1));

      SettingsActivity.Rail var13 = (SettingsActivity.Rail)var5;
      String var10 = (String)var12;
      if (var13 != null) {
         var10 = var13.getPage();
      }

      if (var10 != null) {
         this.showArea(var3, var10);
      } else {
         if (this.areaIndex != var3) {
            this.showArea(var3, this.galaxyRailFor(var1));
         }

         GALAXY_SUB_TITLES.put(var1, var2);
         this.open("galaxy:" + var1);
      }
   }

   private final void openTarget(String var1) {
      if (StringsKt.startsWith$default(var1, "area:", false, 2, null)) {
         this.showArea(Integer.parseInt(StringsKt.removePrefix(var1, "area:")), null);
      } else if (StringsKt.startsWith$default(var1, "gsub:", false, 2, null)) {
         this.openSub(
            StringsKt.substringBefore$default(StringsKt.removePrefix(var1, "gsub:"), "|", null, 2, null),
            StringsKt.substringAfter$default(var1, "|", null, 2, null)
         );
      } else if (StringsKt.startsWith$default(var1, "tool:", false, 2, null)) {
         this.showArea(
            Integer.parseInt(StringsKt.substringBefore$default(StringsKt.removePrefix(var1, "tool:"), "|", null, 2, null)),
            StringsKt.substringAfter$default(var1, "|", null, 2, null)
         );
      } else {
         this.openPage(var1);
      }
   }

   private final void openToolTarget(String var1) {
      label86:
      if (!Intrinsics.areEqual(var1, "activity:files")) {
         if (StringsKt.startsWith$default(var1, "galaxy:", false, 2, null)) {
            var var4 = this.areas().get(0).getRails();
            boolean var9;
            if (var4 instanceof Collection && ((Collection)var4).isEmpty()) {
               var9 = true;
            } else {
               Iterator var12 = var4.iterator();

               while (true) {
                  if (!var12.hasNext()) {
                     var9 = true;
                     break;
                  }

                  Iterable var5 = (Iterable)((Pair)var12.next()).getSecond();
                  if (var5 instanceof Collection && ((Collection)var5).isEmpty()) {
                     var9 = false;
                  } else {
                     Iterator var14 = var5.iterator();

                     while (true) {
                        if (!var14.hasNext()) {
                           var9 = false;
                           break;
                        }

                        if (Intrinsics.areEqual(((SettingsActivity.Rail)var14.next()).getPage(), var1)) {
                           var9 = true;
                           break;
                        }
                     }
                  }

                  if (var9) {
                     var9 = false;
                     break;
                  }
               }
            }

            if (var9) {
               break label86;
            }
         }

         List var13 = this.areas();
         int var10 = 0;
         Iterator var15 = var13.iterator();

         while (true) {
            boolean var3 = false;
            if (!var15.hasNext()) {
               var10 = -1;
               break;
            }

            var var6 = ((SettingsActivity.Area)var15.next()).getRails();
            if (!(var6 instanceof Collection) || !((Collection)var6).isEmpty()) {
               Iterator var16 = var6.iterator();

               while (true) {
                  if (!var16.hasNext()) {
                     var3 = false;
                     break;
                  }

                  var var7 = (Iterable & List)((Pair)var16.next()).component2();
                  if (var7 instanceof Collection && ((Collection)var7).isEmpty()) {
                     var3 = false;
                  } else {
                     Iterator var17 = var7.iterator();

                     while (true) {
                        if (!var17.hasNext()) {
                           var3 = false;
                           break;
                        }

                        SettingsActivity.Rail var8 = (SettingsActivity.Rail)var17.next();
                        if (Intrinsics.areEqual(var8.getPage(), var1)) {
                           var3 = true;
                           break;
                        }
                     }
                  }

                  if (var3) {
                     var3 = true;
                     break;
                  }
               }
            }

            if (var3) {
               break;
            }

            var10++;
         }

         if (var10 >= 0) {
            this.showArea(var10, var1);
         } else {
            this.open(var1);
         }

         return;
      }

      this.open(var1);
   }

   private final Pair<ScrollView, LinearLayout> page() {
      LinearLayout var7 = new LinearLayout((Context)this);
      var7.setOrientation(1);
      CarUi var6 = this.ui;
      Object var5 = null;
      CarUi var4 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var4 = null;
      }

      int var3 = var4.dp(10);
      var6 = this.ui;
      var4 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var4 = null;
      }

      int var2 = var4.dp(4);
      var6 = this.ui;
      var4 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var4 = null;
      }

      int var1 = var4.dp(24);
      var4 = this.ui;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var4 = (CarUi)var5;
      }

      var7.setPadding(var3, var2, var1, var4.dp(60));
      ScrollView var11 = new ScrollView((Context)this);
      var11.addView((View)var7);
      return TuplesKt.to(var11, var7);
   }

   private final FrameLayout pageBox(View var1, boolean var2, boolean var3) {
      FrameLayout var9 = new FrameLayout((Context)this);
      Object var8 = null;
      CarUi var7 = this.ui;
      GradientDrawable var17;
      if (var3) {
         if (var7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var7 = null;
         }

         var17 = var7.corners(CarUi.Companion.getPAGE(), 0, 0, 12, 12);
      } else {
         if (var7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var7 = null;
         }

         int var5 = CarUi.Companion.getPAGE();
         byte var4;
         if (var2) {
            var4 = 12;
         } else {
            var4 = 14;
         }

         var17 = CarUi.rounded$default(var7, var5, var4, null, 4, null);
      }

      Drawable var18 = (Drawable)var17;
      var9.setBackground(var18);
      var9.setClipToOutline(true);
      var9.addView(var1, (LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -1)));
      if (var2 || var3) {
         int var15 = 0;
         android.widget.LinearLayout.LayoutParams var10 = new android.widget.LinearLayout.LayoutParams(-1, 0, 1.0F);
         var7 = this.ui;
         CarUi var11 = var7;
         if (var7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var11 = null;
         }

         int var16 = var11.dp(14);
         if (!var3) {
            var7 = this.ui;
            CarUi var12 = var7;
            if (var7 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("ui");
               var12 = null;
            }

            var15 = var12.dp(14);
         }

         var7 = this.ui;
         CarUi var13 = var7;
         if (var7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var13 = null;
         }

         int var6 = var13.dp(14);
         CarUi var14 = this.ui;
         if (var14 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var14 = (CarUi)var8;
         }

         var10.setMargins(var16, var15, var6, var14.dp(14));
         var9.setLayoutParams((LayoutParams)var10);
      }

      return var9;
   }

   // $VF: synthetic method
   static FrameLayout pageBox$default(SettingsActivity var0, View var1, boolean var2, boolean var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return var0.pageBox(var1, var2, var3);
   }

   private final boolean pageNeedsOffroad(View var1) {
      boolean var2;
      if (var1 instanceof CatalogPanel) {
         var2 = ((CatalogPanel)var1).getNeedsOffroad();
      } else if (var1 instanceof SettingsActivity.ComboPage) {
         var var3 = ((SettingsActivity.ComboPage)var1).getParts();
         if (var3 instanceof Collection && ((Collection)var3).isEmpty()) {
            var2 = false;
         } else {
            Iterator var4 = var3.iterator();

            while (true) {
               if (!var4.hasNext()) {
                  var2 = false;
                  break;
               }

               if (this.pageNeedsOffroad((View)var4.next())) {
                  var2 = true;
                  break;
               }
            }
         }
      } else if (var1 instanceof DevicePanel) {
         if (!Intrinsics.areEqual(((DevicePanel)var1).getKind(), "developer")
            && !Intrinsics.areEqual(((DevicePanel)var1).getKind(), "software")
            && !Intrinsics.areEqual(((DevicePanel)var1).getKind(), "device")) {
            var2 = false;
         } else {
            var2 = true;
         }
      } else if (var1 instanceof GxPage) {
         var2 = ((GxPage)var1).getNeedsOffroad();
      } else if (var1 instanceof GalaxySettingsView) {
         var2 = ((GalaxySettingsView)var1).getNeedsParked();
      } else if (var1 instanceof GalaxyWeb) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private static final void pickAny$lambda$3(SettingsActivity var0, Uri var1) {
      Function1 var2 = var0.onFilePicked;
      var0.onFilePicked = null;
      if (var2 != null) {
         var2.invoke(var1);
      }
   }

   private static final void pickModel$lambda$2(SettingsActivity var0, Uri var1) {
      if (var1 != null) {
         new Thread(new SettingsActivity$$ExternalSyntheticLambda5(var0, var1)).start();
      }
   }

   private static final void pickModel$lambda$2$lambda$1(SettingsActivity var0, Uri var1) {
      var0.runOnUiThread(new SettingsActivity$$ExternalSyntheticLambda181(var0, new LayoutSeg((Context)var0).importModel(var1)));
   }

   private static final void pickModel$lambda$2$lambda$1$lambda$0(SettingsActivity var0, String var1) {
      Function1 var2 = var0.onModelPicked;
      if (var2 != null) {
         if (var1 == null) {
            var1 = "Installed - the Scene view uses it from its next start";
         }

         var2.invoke(var1);
      }
   }

   private final boolean returnFromWeb() {
      String var1 = this.webReturn;
      if (var1 == null) {
         return false;
      } else {
         this.webReturn = null;
         this.open(var1);
         return true;
      }
   }

   private final void rtkFusionSection(View var1, LinearLayout var2) {
      CarUi var6 = this.ui;
      CarUi var5 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var5 = null;
      }

      View var7 = (View)var5.header("Sensor fusion");
      var6 = this.ui;
      var5 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var5 = null;
      }

      var2.addView(var7, (LayoutParams)CarUi.lp$default(var5, 0, 0, 3, null));
      var6 = this.ui;
      var5 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var5 = null;
      }

      var7 = (View)var5.note(
         "The ZED-F9R has its own gyro and accelerometer. Once it has learned how it sits in the car it carries the position through tunnels, garages and tall buildings, like a car's built-in navigation. The steps below go by themselves while you drive; keep the board taped flat and don't move it afterwards (if you do: Recalibrate mount)."
      );
      var6 = this.ui;
      var5 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var5 = null;
      }

      var2.addView(var7, (LayoutParams)CarUi.lp$default(var5, 0, 0, 3, null));
      var5 = this.ui;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var5 = null;
      }

      Pair var61 = CarUi.row$default(var5, "Firmware", "…", 0, null, null, 28, null);
      LinearLayout var29 = (LinearLayout)var61.component1();
      TextView var10 = (TextView)var61.component2();
      var7 = (View)var29;
      var6 = this.ui;
      var5 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var5 = null;
      }

      var2.addView(var7, (LayoutParams)CarUi.lp$default(var5, 0, 0, 3, null));
      var5 = this.ui;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var5 = null;
      }

      Pair var32 = CarUi.row$default(var5, "Calibration", "…", 0, null, null, 28, null);
      LinearLayout var87 = (LinearLayout)var32.component1();
      TextView var11 = (TextView)var32.component2();
      View var8 = (View)var87;
      var6 = this.ui;
      var5 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var5 = null;
      }

      var2.addView(var8, (LayoutParams)CarUi.lp$default(var5, 0, 0, 3, null));
      BarView var12 = rtkFusionSection$barIn(this, var87, 16);
      Iterable var64 = RangesKt.until(0, 4);
      boolean var3 = false;
      var var13 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var64, 10));
      Iterator var9 = var64.iterator();

      while (var9.hasNext()) {
         ((IntIterator)var9).nextInt();
         CarUi var88 = this.ui;
         if (var88 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var88 = null;
         }

         Pair var89 = CarUi.row$default(var88, "…", "…", 0, null, null, 28, null);
         LinearLayout var14 = (LinearLayout)var89.component1();
         TextView var15 = (TextView)var89.component2();
         View var16 = (View)var14;
         CarUi var103 = this.ui;
         CarUi var90 = var103;
         if (var103 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var90 = null;
         }

         var2.addView(var16, (LayoutParams)CarUi.lp$default(var90, 1, 0, 2, null));
         var13.add(new Triple<>(var14, var15, rtkFusionSection$barIn(this, var14, 8)));
      }

      List var110 = var13;
      var5 = this.ui;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var5 = null;
      }

      Pair var65 = CarUi.row$default(var5, "Sensors", "…", 0, null, null, 28, null);
      LinearLayout var35 = (LinearLayout)var65.component1();
      TextView var111 = (TextView)var65.component2();
      var7 = (View)var35;
      var6 = this.ui;
      var5 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var5 = null;
      }

      var2.addView(var7, (LayoutParams)CarUi.lp$default(var5, 0, 0, 3, null));
      var5 = this.ui;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var5 = null;
      }

      Pair var67 = CarUi.row$default(var5, "Mount angles", "…", 0, null, null, 28, null);
      LinearLayout var38 = (LinearLayout)var67.component1();
      TextView var112 = (TextView)var67.component2();
      var7 = (View)var38;
      var6 = this.ui;
      var5 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var5 = null;
      }

      var2.addView(var7, (LayoutParams)CarUi.lp$default(var5, 0, 0, 3, null));
      var6 = this.ui;
      var5 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var5 = null;
      }

      var7 = (View)var5.header("What the drive gave it so far");
      var6 = this.ui;
      var5 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var5 = null;
      }

      var2.addView(var7, (LayoutParams)CarUi.lp$default(var5, 0, 0, 3, null));
      var6 = this.ui;
      var5 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var5 = null;
      }

      var7 = (View)var5.note(
         "Counted by the comma from the receiver's own position and heading since the receiver connected (or since Recalibrate mount). u-blox asks for a stop with the engine on, then about 10 left and right turns of 90° or more at 20+ mph."
      );
      var6 = this.ui;
      var5 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var5 = null;
      }

      var2.addView(var7, (LayoutParams)CarUi.lp$default(var5, 0, 0, 3, null));
      Iterable var95 = RangesKt.until(0, 3);
      boolean var4 = false;
      var var113 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var95, 10));
      var3 = false;
      Iterator var73 = var95.iterator();

      while (var73.hasNext()) {
         ((IntIterator)var73).nextInt();
         CarUi var104 = this.ui;
         if (var104 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var104 = null;
         }

         Pair var105 = CarUi.row$default(var104, "…", "…", 0, null, null, 28, null);
         LinearLayout var17 = (LinearLayout)var105.component1();
         TextView var19 = (TextView)var105.component2();
         View var18 = (View)var17;
         CarUi var108 = this.ui;
         CarUi var106 = var108;
         if (var108 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var106 = null;
         }

         var2.addView(var18, (LayoutParams)CarUi.lp$default(var106, 1, 0, 2, null));
         var113.add(new Triple<>(var17, var19, rtkFusionSection$barIn(this, var17, 8)));
      }

      List var107 = var113;
      var5 = this.ui;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var5 = null;
      }

      Pair var74 = CarUi.row$default(var5, "Timeline", "nothing changed yet", 0, null, null, 28, null);
      LinearLayout var45 = (LinearLayout)var74.component1();
      TextView var109 = (TextView)var74.component2();
      var7 = (View)var45;
      var6 = this.ui;
      var5 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var5 = null;
      }

      var2.addView(var7, (LayoutParams)CarUi.lp$default(var5, 0, 0, 3, null));
      var5 = this.ui;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var5 = null;
      }

      Pair var48 = CarUi.row$default(
         var5,
         "Speed from the car",
         "Sends the car's speed to the receiver 10 times a second, the way a built-in navigation gets wheel speed. Steadier position and heading, and it keeps going when the satellites drop out.",
         0,
         null,
         null,
         28,
         null
      );
      LinearLayout var97 = (LinearLayout)var48.component1();
      TextView var114 = (TextView)var48.component2();
      var6 = this.ui;
      var5 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var5 = null;
      }

      Switch var116 = var5.bigSwitch();
      BooleanRef var115 = new BooleanRef();
      var116.setOnCheckedChangeListener(new SettingsActivity$$ExternalSyntheticLambda235(var115, var116, this));
      var97.addView((View)var116);
      var7 = (View)var97;
      var6 = this.ui;
      var5 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var5 = null;
      }

      var2.addView(var7, (LayoutParams)CarUi.lp$default(var5, 0, 0, 3, null));
      var6 = this.ui;
      var5 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var5 = null;
      }

      var7 = (View)var5.header("RTK tools");
      var6 = this.ui;
      var5 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var5 = null;
      }

      var2.addView(var7, (LayoutParams)CarUi.lp$default(var5, 0, 0, 3, null));
      var5 = this.ui;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var5 = null;
      }

      Pair var80 = CarUi.row$default(var5, "Last used", "nothing yet", 0, null, null, 28, null);
      LinearLayout var54 = (LinearLayout)var80.component1();
      TextView var117 = (TextView)var80.component2();
      var7 = (View)var54;
      var6 = this.ui;
      var5 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var5 = null;
      }

      var2.addView(var7, (LayoutParams)CarUi.lp$default(var5, 0, 0, 3, null));

      for (RtkTools.Tool var20 : RtkTools.INSTANCE.getTOOLS()) {
         var6 = this.ui;
         if (var6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var6 = null;
         }

         LinearLayout var21 = (LinearLayout)CarUi.row$default(var6, var20.getLabel(), var20.getWhat(), 0, null, null, 28, null).component1();
         var6 = this.ui;
         if (var6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var6 = null;
         }

         String var101;
         if (var20.getDanger()) {
            var101 = "Reset";
         } else {
            var101 = "Run";
         }

         var21.addView((View)CarUi.button$default(var6, var101, false, new SettingsActivity$$ExternalSyntheticLambda246(this, var20), 2, null));
         View var118 = (View)var21;
         CarUi var102 = this.ui;
         var6 = var102;
         if (var102 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var6 = null;
         }

         var2.addView(var118, (LayoutParams)CarUi.lp$default(var6, 0, 0, 3, null));
      }

      label155: {
         if (var114 != null) {
            CharSequence var22 = var114.getText();
            if (var22 != null) {
               String var57 = var22.toString();
               var23 = var57;
               if (var57 != null) {
                  break label155;
               }
            }
         }

         var23 = "";
      }

      this.livePoll(
         var1,
         new Runnable(var1, new BooleanRef(), var10, var11, var111, var110, var107, var12, var112, var109, var115, var116, var114, var23, var117, this) {
            final List<Triple<LinearLayout, TextView, BarView>> $needViews;
            final BarView $overallBar;
            final BooleanRef $painting;
            final TextView $rangd;
            final TextView $rfd;
            final TextView $rlastd;
            final TextView $rsend;
            final TextView $rspd;
            final TextView $rsumd;
            final TextView $rtld;
            final BooleanRef $seen;
            final String $speedDesc;
            final List<Triple<LinearLayout, TextView, BarView>> $stepViews;
            final View $sv;
            final Switch $sw;
            final SettingsActivity this$0;

            {
               this.$sv = var1;
               this.$seen = var2x;
               this.$rfd = var3x;
               this.$rsumd = var4x;
               this.$rsend = var5x;
               this.$stepViews = var6x;
               this.$needViews = var7x;
               this.$overallBar = var8x;
               this.$rangd = var9x;
               this.$rtld = var10x;
               this.$painting = var11x;
               this.$sw = var12x;
               this.$rspd = var13x;
               this.$speedDesc = var14;
               this.$rlastd = var15;
               this.this$0 = var16;
            }

            @Override
            public void run() {
               if (this.$sv.isAttachedToWindow()) {
                  this.$seen.element = true;
               } else if (this.$seen.element) {
                  return;
               }

               RtkStatus var10x = Ntrip.INSTANCE.getRtk();
               if (var10x == null) {
                  TextView var7x = this.$rfd;
                  if (var7x != null) {
                     var7x.setText("No RTK receiver reported by the comma");
                  }

                  var7x = this.$rsumd;
                  if (var7x != null) {
                     var7x.setText("–");
                  }

                  var7x = this.$rsend;
                  if (var7x != null) {
                     var7x.setText("–");
                  }

                  Iterator var26 = this.$stepViews.iterator();

                  while (var26.hasNext()) {
                     ((LinearLayout)((Triple)var26.next()).component1()).setVisibility(8);
                  }

                  Iterator var27 = this.$needViews.iterator();

                  while (var27.hasNext()) {
                     ((LinearLayout)((Triple)var27.next()).component1()).setVisibility(8);
                  }
               } else {
                  TextView var28 = this.$rfd;
                  if (var28 != null) {
                     var28.setText(RtkTools.INSTANCE.firmware(var10x));
                  }

                  float var1x = RtkTools.INSTANCE.overall(var10x);
                  TextView var8x = this.$rsumd;
                  if (var8x != null) {
                     int var2x = Math.round(100 * var1x);
                     String var29x = RtkTools.INSTANCE.summary(var10x);
                     var8x.setText(var2x + "%  ·  " + var29x);
                  }

                  this.$overallBar.setFraction(var1x);
                  var28 = this.$rsend;
                  if (var28 != null) {
                     var28.setText(RtkTools.INSTANCE.sensors(var10x.getSensors()));
                  }

                  var28 = this.$rangd;
                  if (var28 != null) {
                     var28.setText(RtkTools.INSTANCE.mountAngles(var10x));
                  }

                  List var11x = RtkTools.INSTANCE.needs(var10x);
                  Iterator var12x = this.$needViews.iterator();
                  int var16 = 0;

                  while (true) {
                     boolean var6x = var12x.hasNext();
                     String var49 = "✓  ";
                     if (!var6x) {
                        var8x = this.$rtld;
                        if (var8x != null) {
                           Object var37 = CollectionsKt.joinToString$default(
                              CollectionsKt.take(RtkTools.timeline$default(RtkTools.INSTANCE, var10x, System.currentTimeMillis(), null, 4, null), 12),
                              "\n",
                              null,
                              null,
                              0,
                              null,
                              null,
                              62,
                              null
                           );
                           boolean var18;
                           if (var37.length() == 0) {
                              var18 = true;
                           } else {
                              var18 = false;
                           }

                           if (var18) {
                              if (var10x.getCalibration() == null) {
                                 var37 = "needs the comma update with the calibration view (patch 32)";
                              } else {
                                 var37 = "nothing changed yet";
                              }
                           }

                           var8x.setText((CharSequence)var37);
                        }

                        Iterator var51 = RtkTools.INSTANCE.steps(var10x).iterator();
                        var16 = 0;

                        while (var51.hasNext()) {
                           int var23 = var16 + 1;
                           RtkTools.Step var58 = (RtkTools.Step)var51.next();
                           Triple var38x = this.$stepViews.get(var16);
                           LinearLayout var55 = (LinearLayout)var38x.component1();
                           TextView var60 = (TextView)var38x.component2();
                           ((BarView)var38x.component3()).setFraction(RtkTools.INSTANCE.stepProgress(var10x, var58.getTitle()));
                           var55.setVisibility(0);
                           View var39 = var55.getChildAt(var55.getChildCount() - 1);
                           LinearLayout var40;
                           if (var39 instanceof LinearLayout) {
                              var40 = (LinearLayout)var39;
                           } else {
                              var40 = null;
                           }

                           View var41;
                           if (var40 != null) {
                              var41 = var40.getChildAt(0);
                           } else {
                              var41 = null;
                           }

                           TextView var56;
                           if (var41 instanceof TextView) {
                              var56 = (TextView)var41;
                           } else {
                              var56 = null;
                           }

                           if (var56 != null) {
                              RtkTools.State var42 = var58.getState();
                              String var43;
                              switch (WhenMappings.$EnumSwitchMapping$0[var42.ordinal()]) {
                                 case 1:
                                    var43 = "✓  ";
                                    break;
                                 case 2:
                                    var43 = "●  ";
                                    break;
                                 case 3:
                                    var43 = "○  ";
                                    break;
                                 case 4:
                                    var43 = "–  ";
                                    break;
                                 default:
                                    throw new NoWhenBranchMatchedException();
                              }

                              String var63 = var58.getTitle();
                              var56.setText(var43 + var63);
                           }

                           if (var60 != null) {
                              var60.setText(var58.getWords());
                           }

                           var16 = var23;
                        }

                        this.$painting.element = true;
                        if (this.$sw.isEnabled()) {
                           this.$sw.setChecked(var10x.getSpeedFeedOn());
                        }

                        this.$painting.element = false;
                        TextView var59 = this.$rspd;
                        if (var59 != null) {
                           String var47;
                           if (var10x.getSpeedFeedOn()) {
                              String var61x = this.$speedDesc;
                              String var46;
                              if (!(var10x.getSpeedFeedHz() > 0.0)) {
                                 var46 = "waiting for the car's speed";
                              } else {
                                 long var4x;
                                 String var57;
                                 label178: {
                                    var4x = Math.round(var10x.getSpeedFeedHz());
                                    Integer var44 = var10x.getSpeedFeedDelayMs();
                                    var57 = "";
                                    if (var44 != null) {
                                       var16 = var44.intValue();
                                       String var52 = ", " + var16 + " ms behind the car";
                                       var45 = var52;
                                       if (var52 != null) {
                                          break label178;
                                       }
                                    }

                                    var45 = "";
                                 }

                                 RtkCalibration var64x = var10x.getCalibration();
                                 String var53 = var57;
                                 if (var64x != null) {
                                    Integer var65x = var64x.getSpeedBadPct();
                                    var53 = var57;
                                    if (var65x != null) {
                                       var16 = var65x.intValue();
                                       var53 = ", receiver accepted " + (100 - var16) + "% in the last minute";
                                       if (var53 == null) {
                                          var53 = var57;
                                       }
                                    }
                                 }

                                 var46 = var4x + " a second" + var45 + var53;
                              }

                              var47 = var61x + "\nNow: " + var46;
                           } else {
                              var47 = this.$speedDesc;
                           }

                           var59.setText(var47);
                        }

                        var8x = this.$rlastd;
                        if (var8x != null) {
                           Object var48x = RtkTools.INSTANCE.lastAction(var10x);
                           boolean var22;
                           if (var48x.length() == 0) {
                              var22 = true;
                           } else {
                              var22 = false;
                           }

                           if (var22) {
                              var48x = "nothing yet";
                           }

                           var8x.setText((CharSequence)var48x);
                        }
                        break;
                     }

                     int var3x = var16 + 1;
                     Triple var13x = (Triple)var12x.next();
                     LinearLayout var32x = (LinearLayout)var13x.component1();
                     TextView var9x = (TextView)var13x.component2();
                     BarView var62 = (BarView)var13x.component3();
                     RtkTools.Need var14 = CollectionsKt.getOrNull(var11x, var16);
                     byte var17;
                     if (var14 == null) {
                        var17 = 8;
                     } else {
                        var17 = 0;
                     }

                     var32x.setVisibility(var17);
                     if (var14 != null) {
                        View var33 = var32x.getChildAt(var32x.getChildCount() - 1);
                        LinearLayout var34;
                        if (var33 instanceof LinearLayout) {
                           var34 = (LinearLayout)var33;
                        } else {
                           var34 = null;
                        }

                        View var35x;
                        if (var34 != null) {
                           var35x = var34.getChildAt(0);
                        } else {
                           var35x = null;
                        }

                        if (var35x instanceof TextView) {
                           var28 = (TextView)var35x;
                        } else {
                           var28 = null;
                        }

                        if (var28 != null) {
                           if (!var14.getMet()) {
                              var49 = "●  ";
                           }

                           String var15 = var14.getTitle();
                           var28.setText(var49 + var15);
                        }

                        if (var9x != null) {
                           var9x.setText(var14.getWords());
                        }

                        var62.setFraction(var14.getProgress());
                     }

                     var16 = var3x;
                  }
               }

               this.this$0.handler.postDelayed(this, 1000L);
            }

            // $VF: synthetic class
            public final class WhenMappings {
               public static final int[] $EnumSwitchMapping$0;

               static {
                  int[] var0 = new int[RtkTools.State.values().length];

                  try {
                     var0[RtkTools.State.DONE.ordinal()] = 1;
                  } catch (NoSuchFieldError var5) {
                  }

                  try {
                     var0[RtkTools.State.NOW.ordinal()] = 2;
                  } catch (NoSuchFieldError var4) {
                  }

                  try {
                     var0[RtkTools.State.WAITING.ordinal()] = 3;
                  } catch (NoSuchFieldError var3) {
                  }

                  try {
                     var0[RtkTools.State.OFF.ordinal()] = 4;
                  } catch (NoSuchFieldError var2) {
                  }

                  $EnumSwitchMapping$0 = var0;
               }
            }
         }
      );
   }

   private static final BarView rtkFusionSection$barIn(SettingsActivity var0, LinearLayout var1, int var2) {
      BarView var5 = new BarView((Context)var0);
      View var10 = var1.getChildAt(var1.getChildCount() - 1);
      Intrinsics.checkNotNull(var10, "null cannot be cast to non-null type android.widget.LinearLayout");
      LinearLayout var6 = (LinearLayout)var10;
      View var7 = var5;
      CarUi var4 = var0.ui;
      Object var3 = null;
      CarUi var11 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var11 = null;
      }

      android.widget.LinearLayout.LayoutParams var12 = new android.widget.LinearLayout.LayoutParams(-1, var11.dp(var2));
      CarUi var8 = var0.ui;
      if (var8 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var8 = (CarUi)var3;
      }

      var12.topMargin = var8.dp(10);
      Unit var9 = Unit.INSTANCE;
      var6.addView(var7, (LayoutParams)var12);
      return var5;
   }

   private static final void rtkFusionSection$lambda$247(BooleanRef var0, Switch var1, SettingsActivity var2, CompoundButton var3, boolean var4) {
      if (!var0.element) {
         var1.setEnabled(false);
         String var5;
         if (var4) {
            var5 = "speed_on";
         } else {
            var5 = "speed_off";
         }

         rtkFusionSection$send(var2, var5, new SettingsActivity$$ExternalSyntheticLambda133(var1));
      }
   }

   private static final Unit rtkFusionSection$lambda$247$lambda$246(Switch var0) {
      var0.setEnabled(true);
      return Unit.INSTANCE;
   }

   private static final Unit rtkFusionSection$lambda$249(SettingsActivity var0, RtkTools.Tool var1) {
      Builder var3 = new Builder((Context)var0);
      String var2 = var1.getLabel();
      var3.setTitle(var2 + "?")
         .setMessage(var1.getWhat())
         .setPositiveButton(var1.getLabel(), new SettingsActivity$$ExternalSyntheticLambda187(var1, var0))
         .setNegativeButton("Cancel", null)
         .show();
      return Unit.INSTANCE;
   }

   private static final void rtkFusionSection$lambda$249$lambda$248(RtkTools.Tool var0, SettingsActivity var1, DialogInterface var2, int var3) {
      rtkFusionSection$send$default(var1, var0.getOp(), null, 4, null);
   }

   private static final void rtkFusionSection$send(SettingsActivity var0, String var1, Function0<Unit> var2) {
      DataClient var3 = Link.INSTANCE.getData();
      if (var3 != null && var3.getConnected()) {
         var3.request("rtk_action", MapsKt.mapOf(TuplesKt.to("op", var1)), new SettingsActivity$$ExternalSyntheticLambda118(var0, var1, var2));
      } else {
         Toast.makeText((Context)var0, "Not connected to the comma", 0).show();
         if (var2 != null) {
            var2.invoke();
         }
      }
   }

   // $VF: synthetic method
   static void rtkFusionSection$send$default(SettingsActivity var0, String var1, Function0 var2, int var3, Object var4) {
      if ((var3 & 4) != 0) {
         var2 = null;
      }

      rtkFusionSection$send(var0, var1, var2);
   }

   private static final Unit rtkFusionSection$send$lambda$242(SettingsActivity var0, String var1, Function0 var2, JSONObject var3) {
      Intrinsics.checkNotNullParameter(var3, "ack");
      if (!var3.optBoolean("ok")) {
         String var4 = var3.optString("error", "failed");
         String var5 = var4;
         if (Intrinsics.areEqual(var4, "unknown action")) {
            var5 = "This needs the comma update with the RTK tools (patch 31)";
         }

         new Builder((Context)var0).setTitle(RtkTools.INSTANCE.label(var1)).setMessage(var5).setPositiveButton("OK", null).show();
      }

      if (var2 != null) {
         var2.invoke();
      }

      return Unit.INSTANCE;
   }

   private final void setHeaderTool(View var1) {
      FrameLayout var3 = this.headerSlot;
      if (var3 != null) {
         if (var1 == null || var1.getParent() != var3) {
            var3.removeAllViews();
            if (var1 != null) {
               ViewParent var2 = var1.getParent();
               ViewGroup var4;
               if (var2 instanceof ViewGroup) {
                  var4 = (ViewGroup)var2;
               } else {
                  var4 = null;
               }

               if (var4 != null) {
                  var4.removeView(var1);
               }

               var3.addView(var1, (LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -2)));
            }
         }
      }
   }

   private final String shortSection(String var1) {
      if (StringsKt.startsWith$default(var1, "Lateral", false, 2, null)) {
         var1 = "Lateral (Steering)";
      } else {
         String var2 = "Longitudinal";
         if (StringsKt.startsWith$default(var1, "Longitudinal", false, 2, null)) {
            var1 = var2;
         } else if (StringsKt.startsWith$default(var1, "Vision Speed", false, 2, null)) {
            var1 = "Speed Limits";
         } else {
            var2 = "Visual";
            if (StringsKt.startsWith$default(var1, "Visual", false, 2, null)) {
               var1 = var2;
            } else {
               var2 = "Sounds";
               if (StringsKt.startsWith$default(var1, "Sounds", false, 2, null)) {
                  var1 = var2;
               } else {
                  var2 = "Wheel";
                  if (StringsKt.startsWith$default(var1, "Wheel", false, 2, null)) {
                     var1 = var2;
                  }
               }
            }
         }
      }

      return var1;
   }

   private final void showArea(int var1, String var2) {
      SettingsActivity.Area var7 = this.areas().get(var1);
      View var5 = this.current;
      GxPage var23;
      if (var5 instanceof GxPage) {
         var23 = (GxPage)var5;
      } else {
         var23 = null;
      }

      if (var23 != null) {
         var23.hide();
      }

      FrameLayout var6 = this.root;
      FrameLayout var24 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("root");
         var24 = null;
      }

      var24.removeAllViews();
      this.current = null;
      this.areaIndex = var1;
      LinearLayout var8 = new LinearLayout((Context)this);
      var8.setOrientation(1);
      CarUi var39 = this.ui;
      CarUi var25 = var39;
      if (var39 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var25 = null;
      }

      int var4 = var25.dp(24);
      CarUi var40 = this.ui;
      CarUi var26 = var40;
      if (var40 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var26 = null;
      }

      int var3 = var26.dp(16);
      CarUi var41 = this.ui;
      CarUi var27 = var41;
      if (var41 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var27 = null;
      }

      var1 = var27.dp(16);
      CarUi var42 = this.ui;
      CarUi var28 = var42;
      if (var42 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var28 = null;
      }

      var8.setPadding(var4, var3, var1, var28.dp(16));
      this.offroadBar = new DriveState.OffroadBar(this);
      var8.addView(this.headerBar(var7.getTitle(), var7.getSub(), new SettingsActivity$$ExternalSyntheticLambda209(this)));
      LinearLayout var11 = new LinearLayout((Context)this);
      var11.setOrientation(0);
      LinearLayout var10 = new LinearLayout((Context)this);
      var10.setOrientation(1);
      CarUi var43 = this.ui;
      CarUi var29 = var43;
      if (var43 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var29 = null;
      }

      var10.setPadding(0, 0, 0, var29.dp(30));
      ScrollView var30 = new ScrollView((Context)this);
      var30.addView((View)var10);
      var30.setVerticalScrollBarEnabled(false);
      View var9 = (View)var30;
      CarUi var44 = this.ui;
      CarUi var31 = var44;
      if (var44 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var31 = null;
      }

      short var17;
      if (this.getResources().getConfiguration().screenWidthDp < 1150) {
         var17 = 200;
      } else {
         var17 = 230;
      }

      var11.addView(var9, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(var31.dp(var17), -1)));
      var6 = new FrameLayout((Context)this);
      CarUi var32 = this.ui;
      if (var32 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var32 = null;
      }

      var6.setBackground((Drawable)var32.corners(CarUi.Companion.getFRAME(), 0, 14, 14, 14));
      CarUi var55 = this.ui;
      CarUi var33 = var55;
      if (var55 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var33 = null;
      }

      var17 = var33.dp(14);
      CarUi var56 = this.ui;
      CarUi var34 = var56;
      if (var56 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var34 = null;
      }

      var3 = var34.dp(14);
      CarUi var57 = this.ui;
      CarUi var35 = var57;
      if (var57 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var35 = null;
      }

      var4 = var35.dp(14);
      CarUi var58 = this.ui;
      CarUi var36 = var58;
      if (var58 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var36 = null;
      }

      var6.setPadding(var17, var3, var4, var36.dp(14));
      this.contentHolder = var6;
      var11.addView((View)var6, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -1, 1.0F)));
      var8.addView((View)var11, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, 0, 1.0F)));
      FrameLayout var59 = this.root;
      var6 = var59;
      if (var59 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("root");
         var6 = null;
      }

      var6.addView((View)var8);
      ArrayList var64 = new ArrayList();

      for (Pair var48 : var7.getRails()) {
         String var13 = (String)var48.component1();
         List var14 = (List)var48.component2();
         if (var13 != null) {
            CarUi var60 = this.ui;
            CarUi var49 = var60;
            if (var60 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("ui");
               var49 = null;
            }

            var10.addView((View)var49.header(var13));
         }

         for (SettingsActivity.Rail var66 : var14) {
            SettingIcons.Icon var15 = var66.getSv();
            CarUi var61 = this.ui;
            CarUi var50 = var61;
            if (var61 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("ui");
               var50 = null;
            }

            TextView var51 = var50.railItem(var66.getLabel(), var15.getName(), var15.getGroup());
            var51.setOnClickListener(new SettingsActivity$$ExternalSyntheticLambda210(var64, this, var51, var66));
            var64.add(TuplesKt.to(var51, var66));
            View var67 = (View)var51;
            CarUi var62 = this.ui;
            CarUi var52 = var62;
            if (var62 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("ui");
               var52 = null;
            }

            android.widget.LinearLayout.LayoutParams var68 = var52.lp(0, 8);
            CarUi var63 = this.ui;
            CarUi var53 = var63;
            if (var63 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("ui");
               var53 = null;
            }

            var68.rightMargin = var53.dp(18);
            Unit var54 = Unit.INSTANCE;
            var10.addView(var67, (LayoutParams)var68);
         }
      }

      Iterator var47 = var64.iterator();

      while (true) {
         if (var47.hasNext()) {
            var5 = (View)var47.next();
            if (!Intrinsics.areEqual(((SettingsActivity.Rail)((Pair)var5).getSecond()).getPage(), var2)) {
               continue;
            }

            var19 = var5;
            break;
         }

         var19 = null;
         break;
      }

      Pair var38 = (Pair)var19;
      Pair var20 = var38;
      if (var38 == null) {
         var20 = CollectionsKt.first(var64);
      }

      ((TextView)var20.getFirst()).performClick();
   }

   private static final Unit showArea$lambda$69(SettingsActivity var0) {
      var0.showHome();
      return Unit.INSTANCE;
   }

   private static final void showArea$lambda$75(ArrayList var0, SettingsActivity var1, TextView var2, SettingsActivity.Rail var3, View var4) {
      Iterator var6 = var0.iterator();
      Intrinsics.checkNotNullExpressionValue(var6, "iterator(...)");

      while (true) {
         boolean var5 = var6.hasNext();
         Object var10 = null;
         CarUi var8 = null;
         if (!var5) {
            CarUi var9 = var1.ui;
            if (var9 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("ui");
               var9 = (CarUi)var10;
            }

            var9.railSelect(var2, true);
            var1.open(var3.getPage());
            return;
         }

         var10 = var6.next();
         Intrinsics.checkNotNullExpressionValue(var10, "next(...)");
         TextView var7 = (TextView)((Pair)var10).component1();
         var10 = var1.ui;
         if (var10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
         } else {
            var8 = (CarUi)var10;
         }

         var8.railSelect(var7, false);
      }
   }

   private final void showHome() {
      View var5 = this.current;
      boolean var4 = var5 instanceof GxPage;
      Object var6 = null;
      GxPage var10;
      if (var4) {
         var10 = (GxPage)var5;
      } else {
         var10 = null;
      }

      if (var10 != null) {
         var10.hide();
      }

      FrameLayout var7 = this.root;
      FrameLayout var11 = var7;
      if (var7 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("root");
         var11 = null;
      }

      var11.removeAllViews();
      this.current = null;
      this.contentHolder = null;
      this.areaIndex = -1;
      this.offroadBar = null;
      LinearLayout var8 = new LinearLayout((Context)this);
      var8.setOrientation(1);
      CarUi var19 = this.ui;
      CarUi var12 = var19;
      if (var19 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var12 = null;
      }

      int var2 = var12.dp(30);
      CarUi var20 = this.ui;
      CarUi var13 = var20;
      if (var20 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var13 = null;
      }

      int var3 = var13.dp(16);
      CarUi var21 = this.ui;
      CarUi var14 = var21;
      if (var21 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var14 = null;
      }

      int var1 = var14.dp(30);
      CarUi var22 = this.ui;
      CarUi var15 = var22;
      if (var22 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var15 = null;
      }

      var8.setPadding(var2, var3, var1, var15.dp(16));
      var8.addView(this.headerBar("Settings", null, new SettingsActivity$$ExternalSyntheticLambda194(this)));
      Context var23 = (Context)this;
      CarUi var16 = this.ui;
      if (var16 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var16 = null;
      }

      SettingsLauncher var24 = new SettingsLauncher(
         var23, var16, this.homeAreas(), new SettingsActivity$$ExternalSyntheticLambda195(this), new SettingsActivity$$ExternalSyntheticLambda196(this)
      );
      this.launcher = var24;
      Context var9 = (Context)this;
      CarUi var17 = this.ui;
      if (var17 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var17 = null;
      }

      var8.addView(
         (View)(
            new SettingsHomeView(
               var9,
               var17,
               (View)var24,
               new SettingsActivity$$ExternalSyntheticLambda197(this),
               new SettingsActivity$$ExternalSyntheticLambda198(var24),
               new SettingsActivity$$ExternalSyntheticLambda199(this)
            )
         ),
         (LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, 0, 1.0F))
      );
      FrameLayout var18 = this.root;
      if (var18 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("root");
         var18 = (FrameLayout)var6;
      }

      var18.addView((View)var8);
   }

   private static final Unit showHome$lambda$28(SettingsActivity var0) {
      var0.finish();
      return Unit.INSTANCE;
   }

   private static final List showHome$lambda$29(SettingsActivity var0) {
      return var0.tabletEntries();
   }

   private static final Unit showHome$lambda$30(SettingsActivity var0, String var1) {
      Intrinsics.checkNotNullParameter(var1, "it");
      var0.openTarget(var1);
      return Unit.INSTANCE;
   }

   private static final List showHome$lambda$31(SettingsActivity var0) {
      return var0.tabletEntries();
   }

   private static final Unit showHome$lambda$32(SettingsLauncher var0, SettingsIndex.Entry var1) {
      Intrinsics.checkNotNullParameter(var1, "it");
      SettingsLauncher.addShortcut$default(var0, var1, 0, 2, null);
      return Unit.INSTANCE;
   }

   private static final Unit showHome$lambda$33(SettingsActivity var0, String var1) {
      Intrinsics.checkNotNullParameter(var1, "it");
      var0.openTarget(var1);
      return Unit.INSTANCE;
   }

   private final void slider(
      LinearLayout var1,
      String var2,
      float var3,
      float var4,
      float var5,
      float var6,
      Function1<? super Float, String> var7,
      Float var8,
      Function1<? super Float, Unit> var9,
      Function0<Unit> var10
   ) {
      int var11 = Math.round((var4 - var3) / var5);
      CarUi var12 = this.ui;
      Object var13 = null;
      if (var12 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var12 = null;
      }

      Pair var17 = CarUi.row$default(var12, var2, (String)var7.invoke(var6), 0, null, null, 28, null);
      LinearLayout var15 = (LinearLayout)var17.component1();
      TextView var16 = (TextView)var17.component2();
      SeekBar var14 = new SeekBar((Context)this);
      var14.setMax(var11);
      var14.setProgress(RangesKt.coerceIn(Math.round((var6 - var3) / var5), 0, var11));
      var12 = this.ui;
      CarUi var18 = var12;
      if (var12 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var18 = null;
      }

      var14.setMinimumHeight(var18.dp(48));
      var14.setThumbTintList(ColorStateList.valueOf(-1));
      var14.setProgressTintList(ColorStateList.valueOf(CarUi.Companion.getACCENT()));
      var14.setOnSeekBarChangeListener(new OnSeekBarChangeListener(var16, var7, var3, var5, var9, var14, var10) {
         final TextView $d;
         final Function1<Float, String> $fmt;
         final float $min;
         final Function0<Unit> $onRelease;
         final Function1<Float, Unit> $set;
         final float $step;
         final SeekBar $this_apply;

         {
            this.$d = var1;
            this.$fmt = var2x;
            this.$min = var3x;
            this.$step = var4x;
            this.$set = var5x;
            this.$this_apply = var6x;
            this.$onRelease = var7x;
         }

         public void onProgressChanged(SeekBar var1, int var2x, boolean var3x) {
            TextView var4x = this.$d;
            if (var4x != null) {
               var4x.setText(this.$fmt.invoke(SettingsActivity.slider$of(this.$min, this.$step, var2x)));
            }
         }

         public void onStartTrackingTouch(SeekBar var1) {
         }

         public void onStopTrackingTouch(SeekBar var1) {
            this.$set.invoke(SettingsActivity.slider$of(this.$min, this.$step, this.$this_apply.getProgress()));
            this.$onRelease.invoke();
         }
      });
      if (var8 != null) {
         CarUi var19 = this.ui;
         if (var19 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var19 = null;
         }

         var15.addView(
            (View)CarUi.button$default(
               var19, "Reset", false, new SettingsActivity$$ExternalSyntheticLambda72(var9, var8, var14, var3, var5, var16, var7, var10), 2, null
            )
         );
      }

      View var28 = (View)var15;
      CarUi var25 = this.ui;
      CarUi var20 = var25;
      if (var25 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var20 = null;
      }

      var1.addView(var28, (LayoutParams)CarUi.lp$default(var20, 0, 0, 3, null));
      View var29 = (View)var14;
      CarUi var26 = this.ui;
      CarUi var21 = var26;
      if (var26 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var21 = null;
      }

      android.widget.LinearLayout.LayoutParams var30 = new android.widget.LinearLayout.LayoutParams(-1, var21.dp(48));
      CarUi var27 = this.ui;
      CarUi var22 = var27;
      if (var27 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var22 = null;
      }

      var30.leftMargin = var22.dp(12);
      CarUi var23 = this.ui;
      if (var23 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var23 = (CarUi)var13;
      }

      var30.rightMargin = var23.dp(12);
      Unit var24 = Unit.INSTANCE;
      var1.addView(var29, (LayoutParams)var30);
   }

   // $VF: synthetic method
   static void slider$default(
      SettingsActivity var0,
      LinearLayout var1,
      String var2,
      float var3,
      float var4,
      float var5,
      float var6,
      Function1 var7,
      Float var8,
      Function1 var9,
      Function0 var10,
      int var11,
      Object var12
   ) {
      if ((var11 & 128) != 0) {
         var8 = null;
      }

      var0.slider(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10);
   }

   private static final Unit slider$lambda$274(Function1 var0, Float var1, SeekBar var2, float var3, float var4, TextView var5, Function1 var6, Function0 var7) {
      var0.invoke(var1);
      var2.setProgress(Math.round((var1 - var3) / var4));
      if (var5 != null) {
         var5.setText((CharSequence)var6.invoke(var1));
      }

      var7.invoke();
      return Unit.INSTANCE;
   }

   private static final float slider$of(float var0, float var1, int var2) {
      return var2 * var1 + var0;
   }

   private final String status(String var1) {
      String var7;
      label139: {
         label138: {
            label137: {
               int var5 = var1.hashCode();
               String var6 = "allowed";
               var7 = "";
               int var3 = 1;
               int var4 = 0;
               int var2 = 0;
               switch (var5) {
                  case -2038685998:
                     if (var1.equals("city_potholes")) {
                        var var14 = IdeaSpots.INSTANCE.all();
                        if (var14 instanceof Collection && ((Collection)var14).isEmpty()) {
                           var2 = var4;
                        } else {
                           Iterator var15 = var14.iterator();
                           var2 = 0;

                           while (var15.hasNext()) {
                              if (Intrinsics.areEqual(((IdeaSpots.Spot)var15.next()).getSrc(), "nyc311")) {
                                 var3 = var2 + 1;
                                 var2 = var3;
                                 if (var3 < 0) {
                                    CollectionsKt.throwCountOverflow();
                                    var2 = var3;
                                 }
                              }
                           }
                        }

                        var1 = "From 311: " + var2 + " open potholes loaded.";
                        break label139;
                     }
                     break;
                  case -1996341101:
                     if (var1.equals("nyc_cutins")) {
                        break label137;
                     }
                     break;
                  case -1889818619:
                     if (var1.equals("speed_bumps")) {
                        var2 = IdeaSpots.INSTANCE.count("bump");
                        var3 = IdeaSpots.INSTANCE.count("rough");
                        var4 = IdeaSpots.INSTANCE.count("map_bump");
                        var1 = "Knows " + (var2 + var3) + " it felt, " + var4 + " from the map.";
                        break label139;
                     }
                     break;
                  case -1664260572:
                     if (var1.equals("friends_fleet")) {
                        var var12 = IdeaSpots.INSTANCE.all();
                        if (!(var12 instanceof Collection) || !((Collection)var12).isEmpty()) {
                           var2 = 0;

                           for (IdeaSpots.Spot var31 : var12) {
                              if (!SetsKt.setOf(new String[]{"me", "osm", "nyc311"}).contains(var31.getSrc())) {
                                 var3 = var2 + 1;
                                 var2 = var3;
                                 if (var3 < 0) {
                                    CollectionsKt.throwCountOverflow();
                                    var2 = var3;
                                 }
                              }
                           }
                        }

                        var1 = "From friends: " + var2 + " places.";
                        break label139;
                     }
                     break;
                  case -1202757124:
                     if (var1.equals("hybrid")) {
                        break label138;
                     }
                     break;
                  case -902148466:
                     if (var1.equals("sirens")) {
                        if (this.checkSelfPermission("android.permission.RECORD_AUDIO") == 0) {
                           var1 = var6;
                        } else {
                           var1 = "not allowed yet";
                        }

                        var6 = SirenEar.INSTANCE.getStatus();
                        var1 = "Microphone: " + var1 + " - " + var6 + ".";
                        break label139;
                     }
                     break;
                  case -891990146:
                     if (var1.equals("streak")) {
                        var1 = Ideas.INSTANCE.getMore().streakText(new UiSettings((Context)this));
                        break label139;
                     }
                     break;
                  case -789415947:
                     if (var1.equals("green_countdown")) {
                        break label138;
                     }
                     break;
                  case 3208579:
                     if (var1.equals("horn")) {
                        if (this.checkSelfPermission("android.permission.RECORD_AUDIO") != 0) {
                           var6 = "not allowed yet";
                        }

                        var1 = "Microphone: " + var6 + ".";
                        break label139;
                     }
                     break;
                  case 11067959:
                     if (var1.equals("double_parked")) {
                        var2 = IdeaSpots.INSTANCE.count("dpark");
                        var1 = "Blocked spots seen: " + var2 + ".";
                        break label139;
                     }
                     break;
                  case 110233717:
                     if (var1.equals("teach")) {
                        var2 = Ideas.INSTANCE.getMemory().taughtCount();
                        if (Ideas.INSTANCE.getMemory().getTeaching() != null) {
                           var1 = " - learning now";
                        } else {
                           var1 = "";
                        }

                        var1 = "Taught paths: " + var2 + var1;
                        break label139;
                     }
                     break;
                  case 502068928:
                     if (var1.equals("spot_settings")) {
                        break label137;
                     }
                     break;
                  case 554390290:
                     if (var1.equals("carsick")) {
                        var1 = Ideas.INSTANCE.getMemory().lastComfort();
                        boolean var18;
                        if (var1.length() == 0) {
                           var18 = (boolean)var3;
                        } else {
                           var18 = false;
                        }

                        if (var18) {
                           var1 = "No score yet.";
                        }

                        var1 = var1;
                        break label139;
                     }
                     break;
                  case 631785340:
                     if (var1.equals("speed_cams")) {
                        var2 = IdeaSpots.INSTANCE.count("speed_cam");
                        var1 = "Cameras known: " + var2 + ".";
                        break label139;
                     }
                     break;
                  case 775364222:
                     if (var1.equals("potholes")) {
                        var2 = IdeaSpots.INSTANCE.count("pothole");
                        var1 = "Knows " + var2 + " potholes.";
                        break label139;
                     }
               }

               var1 = "";
               break label139;
            }

            int var27 = IdeaSpots.INSTANCE.count("cutin");
            int var23 = IdeaSpots.INSTANCE.count("takeover");
            var1 = "Cut-in spots: " + var27 + " - takeover spots: " + var23 + ".";
            break label139;
         }

         int var21 = Ideas.INSTANCE.getMemory().getLights().known();
         var1 = "Lights with a green seen: " + var21 + ".";
      }

      if (Intrinsics.areEqual(var1, "\n")) {
         var1 = var7;
      }

      return "\n" + var1;
   }

   private final void stepperCard(
      LinearLayout var1, String var2, String var3, float var4, float var5, float var6, Function0<Float> var7, Function1<? super Float, Unit> var8
   ) {
      CarUi var9 = this.ui;
      if (var9 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var9 = null;
      }

      LinearLayout var10 = (LinearLayout)CarUi.row$default(var9, var2, var3, 0, null, null, 28, null).component1();
      TextView var27 = new TextView((Context)this);
      var27.setTextSize(22.0F);
      var27.setTextColor(CarUi.Companion.getTEXT());
      var27.setTypeface(Typeface.DEFAULT_BOLD);
      CarUi var20 = this.ui;
      CarUi var13 = var20;
      if (var20 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var13 = null;
      }

      var27.setMinWidth(var13.dp(70));
      var27.setGravity(17);
      CarUi var21 = this.ui;
      CarUi var14 = var21;
      if (var21 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var14 = null;
      }

      var10.addView((View)var14.stepper("−", new SettingsActivity$$ExternalSyntheticLambda19(var7, var6, var8, var4, var5, var27)));
      View var11 = (View)var27;
      android.widget.LinearLayout.LayoutParams var12 = new android.widget.LinearLayout.LayoutParams(-2, -2);
      CarUi var22 = this.ui;
      CarUi var15 = var22;
      if (var22 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var15 = null;
      }

      var12.leftMargin = var15.dp(6);
      CarUi var23 = this.ui;
      CarUi var16 = var23;
      if (var23 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var16 = null;
      }

      var12.rightMargin = var16.dp(6);
      Unit var17 = Unit.INSTANCE;
      var10.addView(var11, (LayoutParams)var12);
      CarUi var24 = this.ui;
      CarUi var18 = var24;
      if (var24 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var18 = null;
      }

      var10.addView((View)var18.stepper("+", new SettingsActivity$$ExternalSyntheticLambda20(var7, var6, var8, var4, var5, var27)));
      stepperCard$paint(var27, var7);
      View var26 = (View)var10;
      CarUi var25 = this.ui;
      CarUi var19 = var25;
      if (var25 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var19 = null;
      }

      var1.addView(var26, (LayoutParams)CarUi.lp$default(var19, 0, 0, 3, null));
   }

   private static final Unit stepperCard$lambda$160(Function0 var0, float var1, Function1 var2, float var3, float var4, TextView var5) {
      stepperCard$write(var2, var3, var4, var1, var5, var0, ((Number)var0.invoke()).floatValue() - var1);
      return Unit.INSTANCE;
   }

   private static final Unit stepperCard$lambda$162(Function0 var0, float var1, Function1 var2, float var3, float var4, TextView var5) {
      stepperCard$write(var2, var3, var4, var1, var5, var0, ((Number)var0.invoke()).floatValue() + var1);
      return Unit.INSTANCE;
   }

   private static final void stepperCard$paint(TextView var0, Function0<Float> var1) {
      StringCompanionObject var2 = StringCompanionObject.INSTANCE;
      String var3 = String.format(Locale.US, "%.1f", Arrays.copyOf(new Object[]{var1.invoke()}, 1));
      Intrinsics.checkNotNullExpressionValue(var3, "format(...)");
      var0.setText(var3);
   }

   private static final void stepperCard$write(
      Function1<? super Float, Unit> var0, float var1, float var2, float var3, TextView var4, Function0<Float> var5, float var6
   ) {
      var0.invoke(Math.round(RangesKt.coerceIn(var6, var1, var2) / var3) * var3);
      stepperCard$paint(var4, var5);
   }

   private final View subTabbedCatalog(GalaxyApi var1, List<GalaxyApi.Param> var2, List<GalaxyApi.Param> var3, String var4, boolean var5) {
      LinearLayout var9 = new LinearLayout((Context)this);
      var9.setOrientation(1);
      Drawable var7;
      if (var5) {
         var7 = null;
      } else {
         CarUi var24 = this.ui;
         if (var24 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var24 = null;
         }

         var7 = (Drawable)CarUi.rounded$default(var24, CarUi.Companion.getSECTION(), 14, null, 4, null);
      }

      var9.setBackground(var7);
      FrameLayout var10 = new FrameLayout((Context)this);
      Iterable var25 = var2;
      var var11 = new ArrayList();

      for (Object var13 : var25) {
         boolean var23;
         label119: {
            GalaxyApi.Param var8 = (GalaxyApi.Param)var13;
            Iterable var27;
            if (var8.getParentKey() == null) {
               var var14 = var2;
               if (var14 instanceof Collection && ((Collection)var14).isEmpty()) {
                  var23 = true;
               } else {
                  Iterator var42 = var14.iterator();

                  while (true) {
                     if (!var42.hasNext()) {
                        var23 = true;
                        break;
                     }

                     String var15 = ((GalaxyApi.Param)var42.next()).getParentKey();
                     if (Intrinsics.areEqual(var15, var8.getKey())) {
                        var23 = false;
                        break;
                     }
                  }
               }

               var27 = var25;
               if (var23) {
                  var23 = true;
                  break label119;
               }
            } else {
               var27 = var25;
            }

            var23 = false;
            var25 = var27;
         }

         if (var23) {
            var11.add(var13);
         }
      }

      List var28 = var11;
      ArrayList var26 = new ArrayList();
      if (!var28.isEmpty() || StringsKt.startsWith$default(var4, "Longitudinal", false, 2, null)) {
         var26.add(TuplesKt.to("General", var28));
      }

      for (GalaxyApi.Param var35 : var3) {
         HashSet var29 = new HashSet();
         ArrayDeque var43 = new ArrayDeque<>(CollectionsKt.listOf(var35.getKey()));
         var29.add(var35.getKey());

         while (!var43.isEmpty()) {
            String var46 = (String)var43.removeFirst();

            for (GalaxyApi.Param var38 : var2) {
               if (Intrinsics.areEqual(var38.getParentKey(), var46) && var29.add(var38.getKey())) {
                  var43.add(var38.getKey());
               }
            }
         }

         String var33 = var35.getLabel();
         Iterable var39 = var2;
         var var36 = new ArrayList();

         for (Object var44 : var39) {
            if (var29.contains(((GalaxyApi.Param)var44).getKey())) {
               var36.add(var44);
            }
         }

         var28 = var36;
         var26.add(TuplesKt.to(var33, var28));
      }

      HashMap var31 = new HashMap();
      IntRef var37 = new IntRef();
      ObjectRef var34 = new ObjectRef();
      CarUi var16 = this.ui;
      if (var16 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var16 = null;
      }

      Iterable var20 = var26;
      var var41 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var20, 10));
      Iterator var45 = var20.iterator();

      while (var45.hasNext()) {
         var41.add((String)((Pair)var45.next()).getFirst());
      }

      var3 = var41;
      Pair var17 = var16.browserTabs(var3, 2, new SettingsActivity$$ExternalSyntheticLambda95(var37, var34, var31, var10, this, var1, var26, var4));
      View var22 = (View)var17.component1();
      var34.element = (T)((Function1)var17.component2());
      if (!var5) {
         CarUi var18 = this.ui;
         if (var18 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var18 = null;
         }

         var22.setBackground((Drawable)var18.corners(CarUi.Companion.getSECTION(), 14, 14, 0, 0));
      }

      var9.addView(var22, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, -2)));
      var9.addView((View)this.pageBox((View)var10, true, true));
      var9.post(new SettingsActivity$$ExternalSyntheticLambda96(var37, var34, var31, var10, this, var1, var26, var4));
      var9.setTag(new SettingsActivity$$ExternalSyntheticLambda97(var37, var34, var31, var10, this, var1, var26, var4));
      return (View)var9;
   }

   private static final Unit subTabbedCatalog$lambda$104(
      IntRef var0, ObjectRef var1, HashMap var2, FrameLayout var3, SettingsActivity var4, GalaxyApi var5, ArrayList var6, String var7, int var8
   ) {
      subTabbedCatalog$select(var0, var1, var2, var3, var4, var5, var6, var7, var8);
      return Unit.INSTANCE;
   }

   private static final void subTabbedCatalog$lambda$105(
      IntRef var0, ObjectRef var1, HashMap var2, FrameLayout var3, SettingsActivity var4, GalaxyApi var5, ArrayList var6, String var7
   ) {
      subTabbedCatalog$select(var0, var1, var2, var3, var4, var5, var6, var7, var0.element);
   }

   private static final void subTabbedCatalog$lambda$106(
      IntRef var0, ObjectRef var1, HashMap var2, FrameLayout var3, SettingsActivity var4, GalaxyApi var5, ArrayList var6, String var7
   ) {
      subTabbedCatalog$select(var0, var1, var2, var3, var4, var5, var6, var7, var0.element);
   }

   private static final void subTabbedCatalog$select(
      IntRef var0,
      ObjectRef<Function1<Integer, Unit>> var1,
      HashMap<Integer, CatalogPanel> var2,
      FrameLayout var3,
      SettingsActivity var4,
      GalaxyApi var5,
      ArrayList<Pair<String, List<GalaxyApi.Param>>> var6,
      String var7,
      int var8
   ) {
      var0.element = var8;
      Function1 var10 = var1.element;
      Object var9 = null;
      if (var10 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("paint");
         var10 = null;
      } else {
         var10 = (Function1)var1.element;
      }

      var10.invoke(var8);
      Map var14 = var2;
      Integer var16 = var8;
      var10 = (Function1)var14.get(var16);
      if (var10 == null) {
         var10 = new CatalogPanel(
            var4, var5, (List<GalaxyApi.Param>)((Pair)var6.get(var8)).getSecond(), var7, true, new SettingsActivity$$ExternalSyntheticLambda242(var4)
         );
         var14.put(var16, var10);
      }

      CatalogPanel var15 = (CatalogPanel)var10;
      if (var15.getParent() != var3) {
         var3.removeAllViews();
         ViewParent var17 = var15.getParent();
         ViewGroup var13 = (ViewGroup)var9;
         if (var17 instanceof ViewGroup) {
            var13 = (ViewGroup)var17;
         }

         if (var13 != null) {
            var13.removeView((View)var15);
         }

         var3.addView((View)var15);
      }

      var15.syncAll();
      var4.current = (View)var15;
   }

   private static final Unit subTabbedCatalog$select$lambda$102$lambda$101(SettingsActivity var0, String var1) {
      Intrinsics.checkNotNullParameter(var1, "r");
      var0.openRoute(var1);
      return Unit.INSTANCE;
   }

   private final void syncDriveState() {
      TextView var5 = this.driveBtn;
      if (var5 != null) {
         String var2 = this.currentDriveState();

         for (Object var3 : this.driveModes) {
            if (Intrinsics.areEqual(((Pair)var3).getFirst(), var2)) {
               var3 = ((String)((Pair)var3).getSecond()).toUpperCase(Locale.ROOT);
               Intrinsics.checkNotNullExpressionValue(var3, "toUpperCase(...)");
               var5.setText((CharSequence)var3);
               int var1;
               Integer var8;
               if (Intrinsics.areEqual(var2, "onroad")) {
                  var8 = Color.rgb(52, 190, 112);
                  var1 = -16777216;
               } else if (Intrinsics.areEqual(var2, "offroad")) {
                  var8 = Color.rgb(201, 34, 49);
                  var1 = -1;
               } else {
                  var8 = Color.rgb(38, 42, 52);
                  var1 = CarUi.Companion.getTEXT();
               }

               Pair var9 = TuplesKt.to(var8, var1);
               var1 = ((Number)var9.component1()).intValue();
               var5.setTextColor(((Number)var9.component2()).intValue());
               GradientDrawable var6 = new GradientDrawable();
               CarUi var14 = this.ui;
               var3 = null;
               CarUi var10 = var14;
               if (var14 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("ui");
                  var10 = null;
               }

               var6.setCornerRadius(var10.dp(12));
               var6.setColor(var1);
               CarUi var11 = this.ui;
               if (var11 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("ui");
                  var11 = (CarUi)var3;
               }

               var6.setStroke(var11.dp(1), Color.rgb(60, 66, 78));
               var5.setBackground((Drawable)var6);
               return;
            }
         }

         throw new NoSuchElementException("Collection contains no element matching the predicate.");
      }
   }

   private final View tabletAboutPage() {
      Pair var1 = this.page();
      ScrollView var3 = (ScrollView)var1.component1();
      LinearLayout var4 = (LinearLayout)var1.component2();

      try {
         var7 = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
      } catch (Exception var6) {
         var7 = "?";
      }

      CarUi var2 = this.ui;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var2 = null;
      }

      var var5 = (View & LinearLayout)CarUi.row$default(var2, "StarView", "Version " + var7, 0, null, null, 28, null).component1();
      var2 = this.ui;
      CarUi var8 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var8 = null;
      }

      var4.addView(var5, (LayoutParams)CarUi.lp$default(var8, 0, 0, 3, null));
      CarUi var9 = this.ui;
      if (var9 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var9 = null;
      }

      var5 = (View & LinearLayout)CarUi.row$default(
            var9,
            "Views",
            "Camera: the video with the openpilot overlay. Scene: a 3D drawing of the road. The button at the bottom left switches.",
            0,
            null,
            null,
            28,
            null
         )
         .component1();
      var2 = this.ui;
      CarUi var10 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var10 = null;
      }

      var4.addView(var5, (LayoutParams)CarUi.lp$default(var10, 0, 0, 3, null));
      return (View)var3;
   }

   private final View tabletBackupPage() {
      Pair var3 = this.page();
      ScrollView var5 = (ScrollView)var3.component1();
      LinearLayout var6 = (LinearLayout)var3.component2();
      CarUi var4 = this.ui;
      CarUi var10 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var10 = null;
      }

      View var7 = (View)var10.header("Backup");
      var4 = this.ui;
      CarUi var11 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var11 = null;
      }

      var6.addView(var7, (LayoutParams)CarUi.lp$default(var11, 0, 0, 3, null));
      var4 = this.ui;
      CarUi var12 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var12 = null;
      }

      var7 = (View)var12.note(
         "Saves every tablet setting - the display switches, the driving-screen layout, voice, maps, the lane card and the rest - to the tablet's Download/StarView folder. That folder stays when StarView is uninstalled, so a backup brings everything back after a reinstall. The comma pairing key is never saved: after a reinstall, pair again from the comma's QR code."
      );
      var4 = this.ui;
      CarUi var13 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var13 = null;
      }

      var6.addView(var7, (LayoutParams)CarUi.lp$default(var13, 0, 0, 3, null));
      long var1 = SettingsBackup.INSTANCE.lastAuto((Context)this);
      var4 = this.ui;
      CarUi var14 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var14 = null;
      }

      String var31;
      if (var1 > 0L) {
         var31 = new SimpleDateFormat("EEE MMM d, h:mm a", Locale.US).format(new Date(var1));
      } else {
         var31 = "not yet";
      }

      var7 = (View)var14.note(
         "Automatic backup: after you edit the driving screen and whenever StarView goes to the background, when something changed (starview-settings-auto.json, plus one copy a day, the last 7 kept). Last one: "
            + var31
            + "."
      );
      var4 = this.ui;
      CarUi var15 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var15 = null;
      }

      var6.addView(var7, (LayoutParams)CarUi.lp$default(var15, 0, 0, 3, null));
      LinearLayout var43 = new LinearLayout((Context)this);
      var43.setOrientation(0);
      var4 = this.ui;
      CarUi var16 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var16 = null;
      }

      var43.addView((View)var16.button("Back up now", true, new SettingsActivity$$ExternalSyntheticLambda50(this)));
      CarUi var17 = this.ui;
      if (var17 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var17 = null;
      }

      View var9 = (View)CarUi.button$default(var17, "Restore from a backup…", false, new SettingsActivity$$ExternalSyntheticLambda51(this), 2, null);
      android.widget.LinearLayout.LayoutParams var8 = new android.widget.LinearLayout.LayoutParams(-2, -2);
      var4 = this.ui;
      CarUi var18 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var18 = null;
      }

      var8.setMarginStart(var18.dp(10));
      Unit var19 = Unit.INSTANCE;
      var43.addView(var9, (LayoutParams)var8);
      var7 = (View)var43;
      var4 = this.ui;
      CarUi var20 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var20 = null;
      }

      var6.addView(var7, (LayoutParams)CarUi.lp$default(var20, 0, 0, 3, null));
      var4 = this.ui;
      CarUi var21 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var21 = null;
      }

      var7 = (View)var21.header("Lane tracker log");
      var4 = this.ui;
      CarUi var22 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var22 = null;
      }

      var6.addView(var7, (LayoutParams)CarUi.lp$default(var22, 0, 0, 3, null));
      var4 = this.ui;
      CarUi var23 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var23 = null;
      }

      var7 = (View)var23.note(
         "Every lane the card showed, why, and your ✓ / ✗ checks with the place. Saved as lane_log.txt next to the backups, so you can send it to tune the lane tracker."
      );
      var4 = this.ui;
      CarUi var24 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var24 = null;
      }

      var6.addView(var7, (LayoutParams)CarUi.lp$default(var24, 0, 0, 3, null));
      CarUi var25 = this.ui;
      if (var25 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var25 = null;
      }

      var7 = (View)CarUi.button$default(var25, "Save the lane log", false, new SettingsActivity$$ExternalSyntheticLambda52(this), 2, null);
      var4 = this.ui;
      CarUi var26 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var26 = null;
      }

      var6.addView(var7, (LayoutParams)CarUi.lp$default(var26, 0, 0, 3, null));
      return (View)var5;
   }

   private static final Unit tabletBackupPage$lambda$142(SettingsActivity var0) {
      String var4;
      try {
         String var2 = SettingsBackup.INSTANCE.manual((Context)var0);
         StringBuilder var5 = new StringBuilder();
         var4 = var5.append("Saved to ").append(var2).toString();
      } catch (Exception var3) {
         var4 = var3.getMessage();
         var4 = "Backup failed: " + var4;
      }

      new Builder((Context)var0).setMessage(var4).setPositiveButton("OK", null).show();
      return Unit.INSTANCE;
   }

   private static final Unit tabletBackupPage$lambda$144(SettingsActivity var0) {
      SettingsActivity$$ExternalSyntheticLambda73 var1 = new SettingsActivity$$ExternalSyntheticLambda73(var0);
      var0.pickFile(new String[]{"application/json", "text/plain", "application/octet-stream"}, var1);
      return Unit.INSTANCE;
   }

   private static final Unit tabletBackupPage$lambda$144$lambda$143(SettingsActivity var0, Uri var1) {
      if (var1 != null) {
         SettingsBackup.restoreFrom$default(SettingsBackup.INSTANCE, var0, var1, null, 4, null);
      }

      return Unit.INSTANCE;
   }

   private static final Unit tabletBackupPage$lambda$146(SettingsActivity var0) {
      String var5;
      try {
         SettingsBackup var6 = SettingsBackup.INSTANCE;
         Context var3 = (Context)var0;
         File var2 = new File(var0.getFilesDir(), "lane_log.txt");
         String var8 = var6.saveCopy(var3, var2, "lane_log.txt");
         StringBuilder var7 = new StringBuilder();
         var5 = var7.append("Saved to ").append(var8).toString();
      } catch (Exception var4) {
         var5 = var4.getMessage();
         var5 = "Couldn't save it: " + var5;
      }

      new Builder((Context)var0).setMessage(var5).setPositiveButton("OK", null).show();
      return Unit.INSTANCE;
   }

   private final View tabletBlinkerPage() {
      Pair var1 = this.page();
      ScrollView var3 = (ScrollView)var1.component1();
      LinearLayout var4 = (LinearLayout)var1.component2();
      UiSettings var5 = new UiSettings((Context)this);
      CarUi var2 = this.ui;
      CarUi var10 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var10 = null;
      }

      View var6 = (View)var10.note(
         "The tablet can turn the blinker on through the relay box: when you confirm a \"Change lanes?\" card, and (if switched on) before the route's turns. Every time it does, it is written in the list at the bottom with the reason."
      );
      var2 = this.ui;
      CarUi var11 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var11 = null;
      }

      var4.addView(var6, (LayoutParams)CarUi.lp$default(var11, 0, 0, 3, null));
      var2 = this.ui;
      CarUi var12 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var12 = null;
      }

      var6 = (View)var12.header("Lane changes");
      var2 = this.ui;
      CarUi var13 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var13 = null;
      }

      var4.addView(var6, (LayoutParams)CarUi.lp$default(var13, 0, 0, 3, null));
      this.toggleCard(
         var4,
         "\"Change lanes?\" card",
         "Asks when the driving model or the route wants a lane change. Nothing happens unless you confirm.",
         new SettingsActivity$$ExternalSyntheticLambda21(var5),
         new SettingsActivity$$ExternalSyntheticLambda34(var5)
      );
      this.toggleCard(
         var4,
         "Confirm with a steering nudge",
         "Besides tapping Change: a nudge of the wheel toward that side while the card asks - how hard is set under Steering nudge strength below.",
         new SettingsActivity$$ExternalSyntheticLambda39(var5),
         new SettingsActivity$$ExternalSyntheticLambda40(var5)
      );
      this.toggleCard(
         var4,
         "Take the route's exits",
         "With a destination: about 8 seconds before an exit or a fork (when you're in or next to its lane) it signals by itself and openpilot moves into the exit lane as soon as it sees it and the blind spot is clear. Cancel on the card skips that exit.",
         new SettingsActivity$$ExternalSyntheticLambda41(var5),
         new SettingsActivity$$ExternalSyntheticLambda42(var5)
      );
      this.toggleCard(
         var4,
         "Pass a slower car",
         "Above 30 mph, when the car ahead holds you 5 mph or more under your set speed for 5 seconds and there's a lane on your left: asks \"Change lanes left?\". Always waits for your nudge (or Change) - never automatic.",
         new SettingsActivity$$ExternalSyntheticLambda43(var5),
         new SettingsActivity$$ExternalSyntheticLambda45(var5)
      );
      this.toggleCard(
         var4,
         "Lane ends ahead",
         "The lane card counts down to a lane that ends (the road data's lane count drops ahead; the camera tells which side as you get close). On: when you're in the ending lane, about 8 s before it, asks \"Change lanes?\" toward the lane that goes on. Always waits for your nudge (or Change). Off: it only shows and logs it.",
         new SettingsActivity$$ExternalSyntheticLambda46(var5),
         new SettingsActivity$$ExternalSyntheticLambda47(var5)
      );
      this.toggleCard(
         var4,
         "Move back right after passing",
         "After you moved left (a pass, or any lane change to the left): asks \"Change lanes right?\" once the passed car is out of the right blind spot and the right lane ahead is clear - nobody within 6 seconds, or a car there keeping up with your set speed. Always waits for your nudge (or Change). No = stay left.",
         new SettingsActivity$$ExternalSyntheticLambda23(var5),
         new SettingsActivity$$ExternalSyntheticLambda24(var5)
      );
      this.toggleCard(
         var4,
         "Automatic (no confirming)",
         "The card counts down 3 seconds and signals by itself unless you tap No. Off = it always waits for you.",
         new SettingsActivity$$ExternalSyntheticLambda25(var5),
         new SettingsActivity$$ExternalSyntheticLambda26(var5)
      );
      var2 = this.ui;
      CarUi var14 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var14 = null;
      }

      var6 = (View)var14.header("Steering nudge strength");
      var2 = this.ui;
      CarUi var15 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var15 = null;
      }

      var4.addView(var6, (LayoutParams)CarUi.lp$default(var15, 0, 0, 3, null));
      var2 = this.ui;
      CarUi var16 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var16 = null;
      }

      var6 = (View)var16.note(
         "How hard you push the wheel toward a side for it to count as a nudge, as a percentage of the car's own nudge (100 % = stock). Lower = lighter push. The push has to last 0.15 s, so a bump in the road doesn't count."
      );
      var2 = this.ui;
      CarUi var17 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var17 = null;
      }

      var4.addView(var6, (LayoutParams)CarUi.lp$default(var17, 0, 0, 3, null));
      this.slider(
         var4,
         "To answer the \"Change lanes?\" card",
         30.0F,
         150.0F,
         10.0F,
         var5.getFloat("nudge_card_pct", 60.0F),
         <unrepresentable>.INSTANCE,
         60.0F,
         new SettingsActivity$$ExternalSyntheticLambda27(var5),
         new SettingsActivity$$ExternalSyntheticLambda28()
      );
      this.slider(
         var4,
         "To start a lane change with the blinker on",
         30.0F,
         150.0F,
         10.0F,
         var5.getFloat("nudge_lc_pct", 100.0F),
         <unrepresentable>.INSTANCE,
         100.0F,
         new SettingsActivity$$ExternalSyntheticLambda29(var5),
         new SettingsActivity$$ExternalSyntheticLambda30()
      );
      var2 = this.ui;
      CarUi var18 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var18 = null;
      }

      var6 = (View)var18.note(
         "The second one is used by the comma (it needs the StarView comma patch with the nudge strength; Toyota only - other cars keep their stock nudge)."
      );
      var2 = this.ui;
      CarUi var19 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var19 = null;
      }

      var4.addView(var6, (LayoutParams)CarUi.lp$default(var19, 0, 0, 3, null));
      var2 = this.ui;
      CarUi var20 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var20 = null;
      }

      var6 = (View)var20.header("Drive replay");
      var2 = this.ui;
      CarUi var21 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var21 = null;
      }

      var4.addView(var6, (LayoutParams)CarUi.lp$default(var21, 0, 0, 3, null));
      var2 = this.ui;
      CarUi var22 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var22 = null;
      }

      var6 = (View)var22.note(
         "Parked: the comma plays one of its recorded drives to this tablet as if you were driving it, so you can watch when the cards ask, signal and take exits. The real blinkers never flash during a replay, and it stops by itself if the car is put in gear. Controls appear at the top of the drive screen."
      );
      var2 = this.ui;
      CarUi var23 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var23 = null;
      }

      var4.addView(var6, (LayoutParams)CarUi.lp$default(var23, 0, 0, 3, null));
      SettingsActivity var76 = this;
      LinearLayout var7 = new LinearLayout((Context)var76);
      var7.setOrientation(0);
      var2 = var76.ui;
      CarUi var24 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var24 = null;
      }

      var7.addView((View)var24.button("Replay a drive…", true, new SettingsActivity$$ExternalSyntheticLambda31(var76)));
      CarUi var25 = var76.ui;
      if (var25 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var25 = null;
      }

      View var9 = (View)CarUi.button$default(var25, "Stop replay", false, new SettingsActivity$$ExternalSyntheticLambda32(), 2, null);
      android.widget.LinearLayout.LayoutParams var8 = new android.widget.LinearLayout.LayoutParams(-2, -2);
      var2 = var76.ui;
      CarUi var26 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var26 = null;
      }

      var8.setMarginStart(var26.dp(10));
      Unit var27 = Unit.INSTANCE;
      var7.addView(var9, (LayoutParams)var8);
      View var78 = (View)var7;
      var2 = var76.ui;
      CarUi var28 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var28 = null;
      }

      var4.addView(var78, (LayoutParams)CarUi.lp$default(var28, 0, 0, 3, null));
      var2 = this.ui;
      CarUi var29 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var29 = null;
      }

      var6 = (View)var29.header("Turns");
      var2 = this.ui;
      CarUi var30 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var30 = null;
      }

      var4.addView(var6, (LayoutParams)CarUi.lp$default(var30, 0, 0, 3, null));
      this.toggleCard(
         var4,
         "Automatic turn signals",
         "With a destination: signals before each left / right turn of the route - about 12 s ahead, after the last cross street, never under 100 ft - and holds it through red lights until the turn is done. Cancel on the blue card.",
         new SettingsActivity$$ExternalSyntheticLambda35(var5),
         new SettingsActivity$$ExternalSyntheticLambda36(var5)
      );
      Boolean var31 = BlinkerTurn.INSTANCE.getCommaOk();
      String var32;
      if (Intrinsics.areEqual(var31, true)) {
         var32 = " Comma: ready.";
      } else if (Intrinsics.areEqual(var31, false)) {
         var32 = " Comma: not updated yet.";
      } else {
         if (var31 != null) {
            throw new NoWhenBranchMatchedException();
         }

         var32 = "";
      }

      this.toggleCard(
         var4,
         "Check the street map for turns (no route)",
         "StarPilot's Turn Signal Guidance (Steering › Steering and Torque, its on/off) makes your blinker under 20 mph a turn. With this on, the tablet checks its street map first: a turn only when there's a street to turn into on that side within 250 ft (not a one-way coming at you, not a driveway or parking lot) - signalling to pull over isn't a turn. Where the tablet has no map, the comma decides by itself. The blinker card shows \"turn kept\" while there's a street. Needs the comma update from the Oct 23 sync."
            + var32,
         new SettingsActivity$$ExternalSyntheticLambda37(var5),
         new SettingsActivity$$ExternalSyntheticLambda38(var5)
      );
      Boolean var65 = LaneSuggestView.Companion.getLastCommaConfirms();
      CarUi var33 = this.ui;
      var2 = var33;
      if (var33 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var2 = null;
      }

      String var34;
      if (Intrinsics.areEqual(var65, true)) {
         var34 = "Comma: up to date - it knows a confirmed lane change needs no second nudge and a turn signal is never a lane change.";
      } else if (Intrinsics.areEqual(var65, false)) {
         var34 = "Comma: needs the StarPilot \"automatic blinker\" update - until then turn signals and automatic mode stay off, and openpilot may want a nudge after you confirm.";
      } else {
         if (var65 != null) {
            throw new NoWhenBranchMatchedException();
         }

         var34 = "Comma: not checked yet (it is checked when the tablet connects).";
      }

      View var66 = (View)var2.note(var34);
      var2 = this.ui;
      CarUi var35 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var35 = null;
      }

      var4.addView(var66, (LayoutParams)CarUi.lp$default(var35, 0, 0, 3, null));
      var2 = this.ui;
      CarUi var36 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var36 = null;
      }

      View var67 = (View)var36.header("What it did lately");
      var2 = this.ui;
      CarUi var37 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var37 = null;
      }

      var4.addView(var67, (LayoutParams)CarUi.lp$default(var37, 0, 0, 3, null));
      List var68 = BlinkLog.INSTANCE.recent(30);
      var2 = this.ui;
      CarUi var38 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var38 = null;
      }

      String var63;
      if (var68.isEmpty()) {
         var63 = "Nothing yet.";
      } else {
         var63 = CollectionsKt.joinToString$default(var68, "\n", null, null, 0, null, null, 62, null);
      }

      View var69 = (View)var38.note(var63);
      var2 = this.ui;
      CarUi var39 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var39 = null;
      }

      var4.addView(var69, (LayoutParams)CarUi.lp$default(var39, 0, 0, 3, null));
      return (View)var3;
   }

   private static final boolean tabletBlinkerPage$lambda$295(UiSettings var0) {
      return var0.get("lane_suggest");
   }

   private static final Unit tabletBlinkerPage$lambda$296(UiSettings var0, boolean var1) {
      var0.set("lane_suggest", var1);
      return Unit.INSTANCE;
   }

   private static final boolean tabletBlinkerPage$lambda$297(UiSettings var0) {
      return var0.get("lane_nudge_confirm");
   }

   private static final Unit tabletBlinkerPage$lambda$298(UiSettings var0, boolean var1) {
      var0.set("lane_nudge_confirm", var1);
      return Unit.INSTANCE;
   }

   private static final boolean tabletBlinkerPage$lambda$299(UiSettings var0) {
      return var0.get("exit_signals");
   }

   private static final Unit tabletBlinkerPage$lambda$300(UiSettings var0, boolean var1) {
      var0.set("exit_signals", var1);
      return Unit.INSTANCE;
   }

   private static final boolean tabletBlinkerPage$lambda$301(UiSettings var0) {
      return var0.get("lane_pass");
   }

   private static final Unit tabletBlinkerPage$lambda$302(UiSettings var0, boolean var1) {
      var0.set("lane_pass", var1);
      return Unit.INSTANCE;
   }

   private static final boolean tabletBlinkerPage$lambda$303(UiSettings var0) {
      return var0.get("lane_ends_ask");
   }

   private static final Unit tabletBlinkerPage$lambda$304(UiSettings var0, boolean var1) {
      var0.set("lane_ends_ask", var1);
      return Unit.INSTANCE;
   }

   private static final boolean tabletBlinkerPage$lambda$305(UiSettings var0) {
      return var0.get("lane_return");
   }

   private static final Unit tabletBlinkerPage$lambda$306(UiSettings var0, boolean var1) {
      var0.set("lane_return", var1);
      return Unit.INSTANCE;
   }

   private static final boolean tabletBlinkerPage$lambda$307(UiSettings var0) {
      return var0.get("lane_auto");
   }

   private static final Unit tabletBlinkerPage$lambda$308(UiSettings var0, boolean var1) {
      var0.set("lane_auto", var1);
      return Unit.INSTANCE;
   }

   private static final Unit tabletBlinkerPage$lambda$309(UiSettings var0, float var1) {
      var0.setFloat("nudge_card_pct", var1);
      return Unit.INSTANCE;
   }

   private static final Unit tabletBlinkerPage$lambda$310() {
      return Unit.INSTANCE;
   }

   private static final Unit tabletBlinkerPage$lambda$311(UiSettings var0, float var1) {
      var0.setFloat("nudge_lc_pct", var1);
      NudgeSync.INSTANCE.push(var1);
      return Unit.INSTANCE;
   }

   private static final Unit tabletBlinkerPage$lambda$312() {
      return Unit.INSTANCE;
   }

   private static final Unit tabletBlinkerPage$lambda$317$lambda$314(SettingsActivity var0) {
      if (!Replay.INSTANCE.getSupported()) {
         new Builder((Context)var0).setMessage("The comma needs StarView's drive replay (comma patch 7) first.").setPositiveButton("OK", null).show();
      } else {
         Replay.INSTANCE.pick(var0);
      }

      return Unit.INSTANCE;
   }

   private static final Unit tabletBlinkerPage$lambda$317$lambda$315() {
      Replay.control$default(Replay.INSTANCE, "stop", 0.0, null, 6, null);
      return Unit.INSTANCE;
   }

   private static final boolean tabletBlinkerPage$lambda$318(UiSettings var0) {
      return var0.get("turn_signals");
   }

   private static final Unit tabletBlinkerPage$lambda$319(UiSettings var0, boolean var1) {
      var0.set("turn_signals", var1);
      return Unit.INSTANCE;
   }

   private static final boolean tabletBlinkerPage$lambda$320(UiSettings var0) {
      return var0.get("blinker_turn");
   }

   private static final Unit tabletBlinkerPage$lambda$321(UiSettings var0, boolean var1) {
      var0.set("blinker_turn", var1);
      return Unit.INSTANCE;
   }

   private static final String tabletBlinkerPage$pctText(float var0) {
      int var1 = (int)var0;
      String var2;
      if (var0 < 100.0F) {
         var2 = " (lighter)";
      } else if (var0 > 100.0F) {
         var2 = " (firmer)";
      } else {
         var2 = " (stock)";
      }

      return var1 + " %" + var2;
   }

   private final View tabletBrakingPage() {
      Pair var4 = this.page();
      ScrollView var7 = (ScrollView)var4.component1();
      LinearLayout var8 = (LinearLayout)var4.component2();
      CarUi var5 = this.ui;
      CarUi var13 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var13 = null;
      }

      String var25 = String.format("%.2f", Arrays.copyOf(new Object[]{0.45F}, 1));
      Intrinsics.checkNotNullExpressionValue(var25, "format(...)");
      View var6 = (View)var13.note(
         "Each time openpilot braked by itself (at least "
            + var25
            + " m/s², you not on the brake or gas): when, the speed before and after, the hardest braking, and what the planner was braking for. The same reason shows live on the road in the \"Why it's slowing\" card (Tablet › Display › Widgets)."
      );
      var5 = this.ui;
      CarUi var14 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var14 = null;
      }

      var8.addView(var6, (LayoutParams)CarUi.lp$default(var14, 0, 0, 3, null));
      File var9 = new File(this.getFilesDir(), "braking_log.txt");
      List var17;
      if (var9.exists()) {
         Iterable var27 = FilesKt.readLines$default(var9, null, 1, null);
         var var15 = new ArrayList();

         for (Object var28 : var27) {
            if (!StringsKt.isBlank((String & CharSequence)var28)) {
               var15.add(var28);
            }
         }

         List var16 = var15;
         var17 = CollectionsKt.reversed(CollectionsKt.takeLast(var16, 300));
      } else {
         var17 = CollectionsKt.emptyList();
      }

      var5 = this.ui;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var5 = null;
      }

      String var40;
      if (var17.isEmpty()) {
         var40 = "Nothing logged yet.";
      } else {
         int var1 = var17.size();
         var40 = var1 + " braking episode(s), newest first";
      }

      LinearLayout var10 = (LinearLayout)CarUi.row$default(var5, "Braking log", var40, 0, null, null, 28, null).component1();
      var5 = this.ui;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var5 = null;
      }

      var10.addView((View)CarUi.button$default(var5, "Copy", false, new SettingsActivity$$ExternalSyntheticLambda207(this, var17), 2, null));
      View var11 = new View((Context)this);
      CarUi var41 = this.ui;
      var5 = var41;
      if (var41 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var5 = null;
      }

      var10.addView(var11, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(var5.dp(8), 1)));
      var5 = this.ui;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var5 = null;
      }

      var10.addView((View)CarUi.button$default(var5, "Clear", false, new SettingsActivity$$ExternalSyntheticLambda208(this, var9), 2, null));
      View var44 = (View)var10;
      CarUi var42 = this.ui;
      var5 = var42;
      if (var42 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var5 = null;
      }

      var8.addView(var44, (LayoutParams)CarUi.lp$default(var5, 0, 0, 3, null));

      for (String var18 : var17) {
         TextView var45 = new TextView((Context)this);
         var45.setText(StringsKt.replace$default(var18, " | ", "\n", false, 4, null));
         var45.setTextColor(CarUi.Companion.getTEXT());
         var45.setTextSize(15.0F);
         CarUi var19 = this.ui;
         if (var19 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var19 = null;
         }

         var45.setBackground((Drawable)CarUi.rounded$default(var19, CarUi.Companion.getCARD(), 10, null, 4, null));
         var5 = this.ui;
         CarUi var20 = var5;
         if (var5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var20 = null;
         }

         int var12 = var20.dp(16);
         var5 = this.ui;
         CarUi var21 = var5;
         if (var5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var21 = null;
         }

         int var2 = var21.dp(10);
         var5 = this.ui;
         CarUi var22 = var5;
         if (var5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var22 = null;
         }

         int var3 = var22.dp(16);
         var5 = this.ui;
         CarUi var23 = var5;
         if (var5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var23 = null;
         }

         var45.setPadding(var12, var2, var3, var23.dp(10));
         View var46 = (View)var45;
         var5 = this.ui;
         CarUi var24 = var5;
         if (var5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var24 = null;
         }

         var8.addView(var46, (LayoutParams)var24.lp(0, 6));
      }

      return (View)var7;
   }

   private static final Unit tabletBrakingPage$lambda$269(SettingsActivity var0, List var1) {
      Object var2 = var0.getSystemService("clipboard");
      Intrinsics.checkNotNull(var2, "null cannot be cast to non-null type android.content.ClipboardManager");
      ((ClipboardManager)var2)
         .setPrimaryClip(ClipData.newPlainText("StarView braking log", CollectionsKt.joinToString$default(var1, "\n", null, null, 0, null, null, 62, null)));
      Toast.makeText((Context)var0, "Copied", 0).show();
      return Unit.INSTANCE;
   }

   private static final Unit tabletBrakingPage$lambda$271(SettingsActivity var0, File var1) {
      new Builder((Context)var0)
         .setMessage("Clear the braking log?")
         .setPositiveButton("Clear", new SettingsActivity$$ExternalSyntheticLambda87(var1, var0))
         .setNegativeButton("Cancel", null)
         .show();
      return Unit.INSTANCE;
   }

   private static final void tabletBrakingPage$lambda$271$lambda$270(File var0, SettingsActivity var1, DialogInterface var2, int var3) {
      var0.delete();
      var1.panels.remove("tablet:braking");
      var1.open("tablet:braking");
   }

   private final View tabletConnectionPage() {
      Pair var2 = this.page();
      ScrollView var5 = (ScrollView)var2.component1();
      LinearLayout var6 = (LinearLayout)var2.component2();
      Telemetry var7 = Link.INSTANCE.getTelemetry();
      CarUi var8 = this.ui;
      if (var8 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var8 = null;
      }

      String var4 = this.host;
      String var3 = var4;
      if (var4 == null) {
         var3 = "not connected";
      }

      var var25 = (View & LinearLayout)CarUi.row$default(var8, "Comma", var3, 0, null, null, 28, null).component1();
      CarUi var19 = this.ui;
      CarUi var9 = var19;
      if (var19 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var9 = null;
      }

      var6.addView(var25, (LayoutParams)CarUi.lp$default(var9, 0, 0, 3, null));
      CarUi var20 = this.ui;
      if (var20 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var20 = null;
      }

      label58: {
         if (var7 != null) {
            String var10 = Telemetry.pStr$default(var7, "GalaxyDeviceName", null, 2, null);
            if (var10 != null) {
               var var11 = var10;
               boolean var1;
               if (var11.length() == 0) {
                  var1 = true;
               } else {
                  var1 = false;
               }

               if (var1) {
                  var11 = null;
               }

               var4 = var11;
               var12 = var4;
               if (var4 != null) {
                  break label58;
               }
            }
         }

         var12 = "-";
         if (var7 != null) {
            var12 = var7.pStr("DongleId", "-");
         }
      }

      var var27 = (View & LinearLayout)CarUi.row$default(var20, "Device", var12, 0, null, null, 28, null).component1();
      CarUi var21 = this.ui;
      CarUi var13 = var21;
      if (var21 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var13 = null;
      }

      var6.addView(var27, (LayoutParams)CarUi.lp$default(var13, 0, 0, 3, null));
      CarUi var14 = this.ui;
      if (var14 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var14 = null;
      }

      var var28 = (View & LinearLayout)CarUi.row$default(
            var14,
            "Link",
            "USB tether (usb0), Wi-Fi as fallback; moves back to USB by itself when the cable returns. The tablet finds the comma by broadcast on port 8091.",
            0,
            null,
            null,
            28,
            null
         )
         .component1();
      CarUi var22 = this.ui;
      CarUi var15 = var22;
      if (var22 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var15 = null;
      }

      var6.addView(var28, (LayoutParams)CarUi.lp$default(var15, 0, 0, 3, null));
      CarUi var16 = this.ui;
      if (var16 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var16 = null;
      }

      LinearLayout var23 = (LinearLayout)CarUi.row$default(var16, "Reconnect", "Drop the data link and search for the comma again.", 0, null, null, 28, null)
         .component1();
      CarUi var17 = this.ui;
      if (var17 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var17 = null;
      }

      var23.addView((View)CarUi.button$default(var17, "Reconnect", false, new SettingsActivity$$ExternalSyntheticLambda94(this), 2, null));
      View var29 = (View)var23;
      CarUi var24 = this.ui;
      CarUi var18 = var24;
      if (var24 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var18 = null;
      }

      var6.addView(var29, (LayoutParams)CarUi.lp$default(var18, 0, 0, 3, null));
      return (View)var5;
   }

   private static final Unit tabletConnectionPage$lambda$191(SettingsActivity var0) {
      DataClient var1 = Link.INSTANCE.getData();
      if (var1 != null) {
         var1.close();
      }

      var0.finish();
      return Unit.INSTANCE;
   }

   private final List<SettingsIndex.Entry> tabletEntries() {
      ArrayList var5 = new ArrayList();
      Iterator var4 = this.areas().iterator();
      int var1 = 0;

      while (true) {
         int var2 = var1;
         if (!var4.hasNext()) {
            for (UiSettings.Item var18 : UiSettings.Companion.getITEMS()) {
               if (!Intrinsics.areEqual(var18.getGroup(), "Internal")) {
                  String var14 = var18.getTitle();
                  String var16 = var18.getGroup();
                  var16 = "Tablet · Display · " + var16;
                  String var20 = var18.getGroup();
                  String var22 = "uigroup:" + var20;
                  SettingsActivity.Companion var21 = Companion;
                  String var19 = var18.getGroup();
                  var5.add(
                     new SettingsIndex.Entry(
                        var14,
                        "",
                        var16,
                        var22,
                        null,
                        null,
                        new SettingIcons.Icon(var21.railIcon("uigroup:" + var19).getName(), "tablet", false, 4, null),
                        48,
                        null
                     )
                  );
               }
            }

            return var5;
         }

         var1++;
         SettingsActivity.Area var6 = (SettingsActivity.Area)var4.next();
         Iterator var7 = var6.getRails().iterator();

         while (var7.hasNext()) {
            for (SettingsActivity.Rail var9 : (List)((Pair)var7.next()).component2()) {
               String var10 = var9.getLabel();
               String var11 = var6.getTitle();
               String var3 = var6.getSub();
               var11 = var11 + " · " + var3;
               var3 = var9.getPage();
               if (StringsKt.startsWith$default(var3, "gweb:", false, 2, null)) {
                  var3 = null;
               }

               if (var3 == null) {
                  var3 = var9.getPage();
                  var3 = "tool:" + var2 + "|" + var3;
               }

               var5.add(new SettingsIndex.Entry(var10, "", var11, var3, null, null, var9.getSv(), 48, null));
            }
         }
      }
   }

   private final View tabletMapsPage() {
      MapdMaps.INSTANCE.init((Context)this);
      Pair var2 = this.page();
      ScrollView var1 = (ScrollView)var2.component1();
      LinearLayout var4 = (LinearLayout)var2.component2();
      SimpleDateFormat var3 = new SimpleDateFormat("MMM d", Locale.US);
      MapdMaps.INSTANCE.setOnChange(new SettingsActivity$$ExternalSyntheticLambda122(this, var4, var3));
      tabletMapsPage$refresh(var4, this, var3);
      return (View)var1;
   }

   private static final Unit tabletMapsPage$lambda$189(SettingsActivity var0, LinearLayout var1, SimpleDateFormat var2) {
      var0.runOnUiThread(new SettingsActivity$$ExternalSyntheticLambda206(var0, var1, var2));
      return Unit.INSTANCE;
   }

   private static final void tabletMapsPage$lambda$189$lambda$188(SettingsActivity var0, LinearLayout var1, SimpleDateFormat var2) {
      if (!var0.isFinishing()) {
         tabletMapsPage$refresh(var1, var0, var2);
      }
   }

   private static final String tabletMapsPage$mb(long var0) {
      String var4;
      if (var0 >= 1000000000L) {
         StringCompanionObject var2 = StringCompanionObject.INSTANCE;
         var4 = String.format(Locale.US, "%.1f GB", Arrays.copyOf(new Object[]{var0 / 1.0E9}, 1));
         Intrinsics.checkNotNullExpressionValue(var4, "format(...)");
      } else {
         var0 /= 1000000;
         var4 = var0 + " MB";
      }

      return var4;
   }

   private static final void tabletMapsPage$refresh(LinearLayout var0, SettingsActivity var1, SimpleDateFormat var2) {
      var0.removeAllViews();
      CarUi var10 = var1.ui;
      CarUi var9 = var10;
      if (var10 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var9 = null;
      }

      View var11 = (View)var9.note(
         "Street maps for the Scene, downloaded to this tablet: the same OpenStreetMap files StarPilot's maps use (map-data.pfeifer.dev, rebuilt every few days), so the comma does nothing for it. Pick the states you drive in. Where nothing is downloaded, streets are fetched online as you drive."
      );
      var10 = var1.ui;
      var9 = var10;
      if (var10 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var9 = null;
      }

      var0.addView(var11, (LayoutParams)CarUi.lp$default(var9, 0, 0, 3, null));
      String var12 = MapdMaps.INSTANCE.getBusy();
      boolean var3;
      if (var12.length() > 0) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      if (var3) {
         var9 = var1.ui;
         if (var9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var9 = null;
         }

         LinearLayout var46 = (LinearLayout)CarUi.row$default(var9, "Downloading", var12, 0, null, null, 28, null).component1();
         var9 = var1.ui;
         if (var9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var9 = null;
         }

         var46.addView((View)CarUi.button$default(var9, "Cancel", false, new SettingsActivity$$ExternalSyntheticLambda200(), 2, null));
         var11 = (View)var46;
         var10 = var1.ui;
         var9 = var10;
         if (var10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var9 = null;
         }

         var0.addView(var11, (LayoutParams)CarUi.lp$default(var9, 0, 0, 3, null));
      }

      if (MapdMaps.INSTANCE.getLastError().length() > 0) {
         var3 = (boolean)1;
      } else {
         var3 = (boolean)0;
      }

      if (var3) {
         var10 = var1.ui;
         var9 = var10;
         if (var10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var9 = null;
         }

         String var49 = MapdMaps.INSTANCE.getLastError();
         var11 = (View)var9.note("Last problem: " + var49);
         var10 = var1.ui;
         var9 = var10;
         if (var10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var9 = null;
         }

         var0.addView(var11, (LayoutParams)CarUi.lp$default(var9, 0, 0, 3, null));
      }

      Set var14 = MapdMaps.INSTANCE.selected();
      Iterable var51 = MapdMaps.INSTANCE.getStates();
      var var28 = new ArrayList();

      for (Object var62 : var51) {
         if (var14.contains(((MapdMaps.State)var62).getCode())) {
            var28.add(var62);
         }
      }

      List var63 = var28;
      long var7 = var1.getFilesDir().getUsableSpace();
      var9 = var1.ui;
      if (var9 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var9 = null;
      }

      Iterator var53 = var63.iterator();
      long var5 = 0L;

      while (var53.hasNext()) {
         MapdMaps.State var13 = (MapdMaps.State)var53.next();
         var5 += MapdMaps.INSTANCE.stateBytes(var13);
      }

      String var64 = tabletMapsPage$mb(var5);
      String var54 = tabletMapsPage$mb(var7);
      LinearLayout var55 = (LinearLayout)CarUi.row$default(var9, "On this tablet", var64 + " of maps, " + var54 + " free", 0, null, null, 28, null)
         .component1();
      if (!var63.isEmpty()) {
         var9 = var1.ui;
         if (var9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var9 = null;
         }

         var55.addView((View)CarUi.button$default(var9, "Update all", false, new SettingsActivity$$ExternalSyntheticLambda201(var63), 2, null));
      }

      View var65 = (View)var55;
      var10 = var1.ui;
      var9 = var10;
      if (var10 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var9 = null;
      }

      var0.addView(var65, (LayoutParams)var9.lp(0, 14));
      Pair var32 = TuplesKt.to("Your states", var63);
      Iterable var66 = MapdMaps.INSTANCE.getStates();
      var var57 = new ArrayList();

      for (Object var15 : var66) {
         if (!var14.contains(((MapdMaps.State)var15).getCode())) {
            var57.add(var15);
         }
      }

      List var58 = var57;

      for (Pair var33 : CollectionsKt.listOf(new Pair[]{var32, TuplesKt.to("All states", var58)})) {
         String var74 = (String)var33.component1();
         List var16 = (List)var33.component2();
         if (!var16.isEmpty()) {
            CarUi var68 = var1.ui;
            var9 = var68;
            if (var68 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("ui");
               var9 = null;
            }

            var0.addView((View)var9.header(var74));

            for (MapdMaps.State var76 : var16) {
               Pair var35 = MapdMaps.INSTANCE.stateStatus(var76);
               var3 = ((Number)var35.component1()).intValue();
               var7 = ((Number)var35.component2()).longValue();
               int var4 = MapdMaps.INSTANCE.groupsOf(var76).size();
               String var37;
               switch (var3) {
                  case 1:
                     String var39 = tabletMapsPage$mb(MapdMaps.INSTANCE.stateBytes(var76));
                     var37 = "Partly downloaded - " + var39 + " (tap Download to finish)";
                     break;
                  case 2:
                     var64 = var2.format(new Date(var7));
                     String var38 = tabletMapsPage$mb(MapdMaps.INSTANCE.stateBytes(var76));
                     var37 = "Downloaded " + var64 + " - " + var38;
                     break;
                  default:
                     String var36;
                     if (var4 == 1) {
                        var36 = "";
                     } else {
                        var36 = "s";
                     }

                     var37 = var4 + " map area" + var36 + " to download";
               }

               CarUi var70 = var1.ui;
               if (var70 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("ui");
                  var70 = null;
               }

               LinearLayout var17 = (LinearLayout)CarUi.row$default(var70, var76.getName(), var37, 0, null, null, 28, null).component1();
               if (var3 == 2) {
                  var9 = var1.ui;
                  if (var9 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("ui");
                     var9 = null;
                  }

                  var17.addView((View)CarUi.button$default(var9, "Update", false, new SettingsActivity$$ExternalSyntheticLambda203(var76), 2, null));
               } else {
                  CarUi var71 = var1.ui;
                  var9 = var71;
                  if (var71 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("ui");
                     var9 = null;
                  }

                  var17.addView((View)var9.button("Download", var14.contains(var76.getCode()) ^ true, new SettingsActivity$$ExternalSyntheticLambda204(var76)));
               }

               if (var14.contains(var76.getCode()) || var3 > 0) {
                  View var18 = new View((Context)var1);
                  CarUi var72 = var1.ui;
                  var9 = var72;
                  if (var72 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("ui");
                     var9 = null;
                  }

                  var17.addView(var18, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(var9.dp(8), 1)));
                  var9 = var1.ui;
                  if (var9 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("ui");
                     var9 = null;
                  }

                  var17.addView((View)CarUi.button$default(var9, "Delete", false, new SettingsActivity$$ExternalSyntheticLambda205(var1, var76), 2, null));
               }

               View var77 = (View)var17;
               CarUi var73 = var1.ui;
               var9 = var73;
               if (var73 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("ui");
                  var9 = null;
               }

               var0.addView(var77, (LayoutParams)CarUi.lp$default(var9, 0, 0, 3, null));
            }
         }
      }
   }

   private static final Unit tabletMapsPage$refresh$lambda$179() {
      MapdMaps.INSTANCE.cancelAll();
      return Unit.INSTANCE;
   }

   private static final Unit tabletMapsPage$refresh$lambda$182(List var0) {
      for (MapdMaps.State var1 : var0) {
         MapdMaps.INSTANCE.download(var1, true);
      }

      return Unit.INSTANCE;
   }

   private static final Unit tabletMapsPage$refresh$lambda$184(MapdMaps.State var0) {
      MapdMaps.INSTANCE.download(var0, true);
      return Unit.INSTANCE;
   }

   private static final Unit tabletMapsPage$refresh$lambda$185(MapdMaps.State var0) {
      MapdMaps.download$default(MapdMaps.INSTANCE, var0, false, 2, null);
      return Unit.INSTANCE;
   }

   private static final Unit tabletMapsPage$refresh$lambda$187(SettingsActivity var0, MapdMaps.State var1) {
      Builder var3 = new Builder((Context)var0);
      String var2 = var1.getName();
      var3.setMessage("Delete the " + var2 + " street maps from this tablet?")
         .setPositiveButton("Delete", new SettingsActivity$$ExternalSyntheticLambda84(var1))
         .setNegativeButton("Cancel", null)
         .show();
      return Unit.INSTANCE;
   }

   private static final void tabletMapsPage$refresh$lambda$187$lambda$186(MapdMaps.State var0, DialogInterface var1, int var2) {
      MapdMaps.INSTANCE.delete(var0);
   }

   private final View tabletNavPage() {
      Pair var1 = this.page();
      ScrollView var3 = (ScrollView)var1.component1();
      LinearLayout var4 = (LinearLayout)var1.component2();
      UiSettings var5 = new UiSettings((Context)this);
      CarUi var2 = this.ui;
      CarUi var7 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var7 = null;
      }

      View var6 = (View)var7.note(
         "StarPilot's own navigation, on a map beside the road view: search places like Google Maps (as you type, or gas / coffee / food nearby or along the route), live traffic, incidents and closures from Mapbox (the comma's key), up to three routes to choose from, the turn-by-turn list, and route options (avoid tolls, highways, ferries, unpaved roads, carpool lanes…) under ⚙ on the map. Drag the bar between the map and the road view to make either bigger; drag it to the edge to close the map."
      );
      var2 = this.ui;
      CarUi var8 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var8 = null;
      }

      var4.addView(var6, (LayoutParams)CarUi.lp$default(var8, 0, 0, 3, null));
      var2 = this.ui;
      CarUi var9 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var9 = null;
      }

      var6 = (View)var9.button("Open the map", true, new SettingsActivity$$ExternalSyntheticLambda223(this));
      var2 = this.ui;
      CarUi var10 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var10 = null;
      }

      var4.addView(var6, (LayoutParams)CarUi.lp$default(var10, 0, 0, 3, null));
      var2 = this.ui;
      CarUi var11 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var11 = null;
      }

      var6 = (View)var11.header("Map");
      var2 = this.ui;
      CarUi var12 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var12 = null;
      }

      var4.addView(var6, (LayoutParams)CarUi.lp$default(var12, 0, 0, 3, null));
      this.toggleCard(
         var4,
         "Open the map when a route starts",
         "Also a destination set in Galaxy or on the comma.",
         new SettingsActivity$$ExternalSyntheticLambda229(var5),
         new SettingsActivity$$ExternalSyntheticLambda230(var5)
      );
      this.toggleCard(
         var4,
         "Live traffic on every road",
         "Off: plain street map (traffic is still drawn on your route).",
         new SettingsActivity$$ExternalSyntheticLambda231(var5),
         new SettingsActivity$$ExternalSyntheticLambda232(var5)
      );
      this.toggleCard(
         var4,
         "North up",
         "Off: the way you drive is up.",
         new SettingsActivity$$ExternalSyntheticLambda233(var5),
         new SettingsActivity$$ExternalSyntheticLambda234(var5)
      );
      this.toggleCard(
         var4, "Map on the right side", null, new SettingsActivity$$ExternalSyntheticLambda236(var5), new SettingsActivity$$ExternalSyntheticLambda237(var5)
      );
      var2 = this.ui;
      CarUi var13 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var13 = null;
      }

      var6 = (View)var13.header("Voice");
      var2 = this.ui;
      CarUi var14 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var14 = null;
      }

      var4.addView(var6, (LayoutParams)CarUi.lp$default(var14, 0, 0, 3, null));
      var2 = this.ui;
      CarUi var15 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var15 = null;
      }

      var6 = (View)var15.note(
         "Turn-by-turn: Mapbox's spoken directions, timed for the road, plus the alerts. Alerts only: a crash, closure or hazard about a mile ahead, and a faster route. The \ud83d\udd0a button on the map switches it too."
      );
      var2 = this.ui;
      CarUi var16 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var16 = null;
      }

      var4.addView(var6, (LayoutParams)CarUi.lp$default(var16, 0, 0, 3, null));

      for (Pair var17 : CollectionsKt.listOf(
         new Pair[]{TuplesKt.to("all", "Turn-by-turn directions and alerts"), TuplesKt.to("alerts", "Alerts only"), TuplesKt.to("off", "Off")}
      )) {
         String var36 = (String)var17.component1();
         this.toggleCard(
            var4,
            (String)var17.component2(),
            null,
            new SettingsActivity$$ExternalSyntheticLambda238(var5, var36),
            new SettingsActivity$$ExternalSyntheticLambda225(var5, var36, this)
         );
      }

      var2 = this.ui;
      CarUi var18 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var18 = null;
      }

      View var43 = (View)var18.header("GPS from the comma");
      var2 = this.ui;
      CarUi var19 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var19 = null;
      }

      var4.addView(var43, (LayoutParams)CarUi.lp$default(var19, 0, 0, 3, null));
      var2 = this.ui;
      CarUi var20 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var20 = null;
      }

      View var44 = (View)var20.note(
         "This tablet has no GPS of its own; the map always uses the comma's. StarView can also pass it to Android, so other apps see the car's real position. One-time setup: Developer options › Select mock location app › StarView."
      );
      var2 = this.ui;
      CarUi var21 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var21 = null;
      }

      var4.addView(var44, (LayoutParams)CarUi.lp$default(var21, 0, 0, 3, null));
      this.toggleCard(
         var4,
         "Share the comma's GPS with other apps",
         null,
         new SettingsActivity$$ExternalSyntheticLambda226(this),
         new SettingsActivity$$ExternalSyntheticLambda227(this)
      );
      CarUi var22 = this.ui;
      if (var22 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var22 = null;
      }

      var1 = CarUi.row$default(var22, "GPS", "-", 0, null, null, 28, null);
      LinearLayout var41 = (LinearLayout)var1.component1();
      TextView var45 = (TextView)var1.component2();
      CarUi var24 = this.ui;
      if (var24 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var24 = null;
      }

      var41.addView((View)CarUi.button$default(var24, "Developer options", false, new SettingsActivity$$ExternalSyntheticLambda228(this), 2, null));
      var6 = (View)var41;
      var2 = this.ui;
      CarUi var25 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var25 = null;
      }

      var4.addView(var6, (LayoutParams)CarUi.lp$default(var25, 0, 0, 3, null));
      Runnable var26 = new Runnable(var3, new BooleanRef(), var45, this) {
         final TextView $gpsVal;
         final BooleanRef $seen;
         final ScrollView $sv;
         final SettingsActivity this$0;

         {
            this.$sv = var1;
            this.$seen = var2x;
            this.$gpsVal = var3x;
            this.this$0 = var4x;
         }

         @Override
         public void run() {
            if (this.$sv.isAttachedToWindow()) {
               this.$seen.element = true;
            } else if (this.$seen.element) {
               return;
            }

            long var1x = (System.currentTimeMillis() - GpsBridge.INSTANCE.getLastFixMs()) / 1000;
            Boolean var3x = GpsBridge.INSTANCE.permitted();
            TextView var4x = this.$gpsVal;
            if (var4x != null) {
               CharSequence var8x;
               if (!GpsBridge.INSTANCE.enabled((Context)this.this$0)) {
                  var8x = "not shared (the map still has it)";
               } else if (Intrinsics.areEqual(var3x, false)) {
                  var8x = "NOT ALLOWED: StarView isn't the mock location app (Android reset it?). Developer options › Select mock location app › StarView";
               } else if (GpsBridge.INSTANCE.getLastFixMs() > 0L && var1x < 3L) {
                  String var9x = GpsBridge.INSTANCE.getStatus();
                  var1x = GpsBridge.INSTANCE.getFixes();
                  var8x = var9x + " (" + var1x + " fixes)";
               } else {
                  String var5x = GpsBridge.INSTANCE.getStatus();
                  String var7x;
                  if (GpsBridge.INSTANCE.getLastFixMs() > 0L) {
                     var7x = " (last fix " + var1x + "s ago)";
                  } else {
                     var7x = " (nothing from the comma yet)";
                  }

                  var8x = "allowed · " + var5x + var7x;
               }

               var4x.setText(var8x);
            }

            this.this$0.handler.postDelayed(this, 1000L);
         }
      };
      this.livePoll((View)var3, var26);
      return (View)var3;
   }

   private static final Unit tabletNavPage$lambda$253(SettingsActivity var0) {
      var0.startActivity(new Intent((Context)var0, MainActivity.class).putExtra("open_nav", true).addFlags(603979776));
      var0.finish();
      return Unit.INSTANCE;
   }

   private static final boolean tabletNavPage$lambda$254(UiSettings var0) {
      return var0.get("map_auto_open");
   }

   private static final Unit tabletNavPage$lambda$255(UiSettings var0, boolean var1) {
      var0.set("map_auto_open", var1);
      return Unit.INSTANCE;
   }

   private static final boolean tabletNavPage$lambda$256(UiSettings var0) {
      return var0.get("map_traffic");
   }

   private static final Unit tabletNavPage$lambda$257(UiSettings var0, boolean var1) {
      var0.set("map_traffic", var1);
      return Unit.INSTANCE;
   }

   private static final boolean tabletNavPage$lambda$258(UiSettings var0) {
      return var0.get("map_north_up");
   }

   private static final Unit tabletNavPage$lambda$259(UiSettings var0, boolean var1) {
      var0.set("map_north_up", var1);
      return Unit.INSTANCE;
   }

   private static final boolean tabletNavPage$lambda$260(UiSettings var0) {
      return Intrinsics.areEqual(var0.getStr("map_side", "left"), "right");
   }

   private static final Unit tabletNavPage$lambda$261(UiSettings var0, boolean var1) {
      String var2;
      if (var1) {
         var2 = "right";
      } else {
         var2 = "left";
      }

      var0.setStr("map_side", var2);
      return Unit.INSTANCE;
   }

   private static final boolean tabletNavPage$lambda$262(UiSettings var0, String var1) {
      return Intrinsics.areEqual(Nav.INSTANCE.voiceMode(var0), var1);
   }

   private static final Unit tabletNavPage$lambda$263(UiSettings var0, String var1, SettingsActivity var2, boolean var3) {
      if (var3) {
         var0.setStr("nav_voice", var1);
         var2.panels.remove("tablet:nav");
         var2.open("tablet:nav");
      }

      return Unit.INSTANCE;
   }

   private static final boolean tabletNavPage$lambda$264(SettingsActivity var0) {
      return GpsBridge.INSTANCE.enabled((Context)var0);
   }

   private static final Unit tabletNavPage$lambda$265(SettingsActivity var0, boolean var1) {
      GpsBridge.INSTANCE.setEnabled((Context)var0, var1);
      return Unit.INSTANCE;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private static final Unit tabletNavPage$lambda$267(SettingsActivity var0) {
      try {
         Result.Companion var4 = Result.Companion;
         Intent var5 = new Intent("android.settings.APPLICATION_DEVELOPMENT_SETTINGS");
         var0.startActivity(var5);
         KtCompat.resultOf(Unit.INSTANCE);
      } catch (Throwable var3) {
         Result.Companion var1 = Result.Companion;
         KtCompat.resultOf(ResultKt.createFailure(var3));
         return Unit.INSTANCE;
      }

      return Unit.INSTANCE;
   }

   private final View tabletRtkPage() {
      Pair var3 = this.page();
      ScrollView var5 = (ScrollView)var3.component1();
      LinearLayout var6 = (LinearLayout)var3.component2();
      CarUi var4 = this.ui;
      CarUi var11 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var11 = null;
      }

      View var7 = (View)var11.note(
         "A u-blox RTK receiver (SparkFun ZED-F9R) plugged into the comma's USB hub. Its position shows on the GPS card; with corrections from an NTRIP caster it gets to about an inch (\"RTK Fixed\"). The comma has no internet in the car, so this tablet fetches the corrections over its own connection and passes them to the receiver. NYSNet (New York State) is free: sign up at cors.dot.ny.gov, the third email has the caster, port and mountpoint. The password stays on this tablet (never in a settings backup)."
      );
      var4 = this.ui;
      CarUi var12 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var12 = null;
      }

      var6.addView(var7, (LayoutParams)CarUi.lp$default(var12, 0, 0, 3, null));
      var4 = this.ui;
      CarUi var13 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var13 = null;
      }

      var7 = (View)var13.header("Now");
      var4 = this.ui;
      CarUi var14 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var14 = null;
      }

      var6.addView(var7, (LayoutParams)CarUi.lp$default(var14, 0, 0, 3, null));
      CarUi var15 = this.ui;
      if (var15 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var15 = null;
      }

      Pair var38 = CarUi.row$default(var15, "Receiver", "…", 0, null, null, 28, null);
      LinearLayout var16 = (LinearLayout)var38.component1();
      TextView var52 = (TextView)var38.component2();
      View var8 = (View)var16;
      var4 = this.ui;
      CarUi var17 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var17 = null;
      }

      var6.addView(var8, (LayoutParams)CarUi.lp$default(var17, 0, 0, 3, null));
      CarUi var18 = this.ui;
      if (var18 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var18 = null;
      }

      Pair var40 = CarUi.row$default(var18, "Corrections", "…", 0, null, null, 28, null);
      LinearLayout var19 = (LinearLayout)var40.component1();
      TextView var57 = (TextView)var40.component2();
      View var9 = (View)var19;
      var4 = this.ui;
      CarUi var20 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var20 = null;
      }

      var6.addView(var9, (LayoutParams)CarUi.lp$default(var20, 0, 0, 3, null));
      CarUi var21 = this.ui;
      if (var21 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var21 = null;
      }

      Pair var42 = CarUi.row$default(var21, "Connection log", "…", 0, null, null, 28, null);
      LinearLayout var22 = (LinearLayout)var42.component1();
      TextView var58 = (TextView)var42.component2();
      View var10 = (View)var22;
      var4 = this.ui;
      CarUi var23 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var23 = null;
      }

      var6.addView(var10, (LayoutParams)CarUi.lp$default(var23, 0, 0, 3, null));
      Telemetry var24 = Link.INSTANCE.getTelemetry();
      boolean var2;
      if (var24 != null && var24.isMetric()) {
         var2 = true;
      } else {
         var2 = false;
      }

      Runnable var25 = new Runnable(var5, new BooleanRef(), var52, var2, var57, var58, this) {
         final boolean $metric;
         final TextView $rld;
         final TextView $rnd;
         final TextView $rsd;
         final BooleanRef $seen;
         final ScrollView $sv;
         final SettingsActivity this$0;

         {
            this.$sv = var1;
            this.$seen = var2x;
            this.$rsd = var3x;
            this.$metric = var4x;
            this.$rnd = var5x;
            this.$rld = var6x;
            this.this$0 = var7x;
         }

         @Override
         public void run() {
            boolean var4x = this.$sv.isAttachedToWindow();
            boolean var2x = true;
            if (var4x) {
               this.$seen.element = true;
            } else if (this.$seen.element) {
               return;
            }

            RtkStatus var10x = Ntrip.INSTANCE.getRtk();
            TextView var7x = this.$rsd;
            String var8x = "";
            if (var7x != null) {
               CharSequence var5x;
               if (var10x == null) {
                  var5x = "No RTK receiver reported by the comma (needs the comma update with rtk.py, and the receiver on the hub).";
               } else if (!var10x.getFresh()) {
                  var5x = var10x.getWords();
               } else {
                  String var9x = var10x.getWords();
                  String var11x = RtkStatus.Companion.accuracy(var10x.getHAccM(), this.$metric);
                  int var3x = var10x.getUsed();
                  boolean var1;
                  if (var10x.getFusion().length() > 0) {
                     var1 = true;
                  } else {
                     var1 = false;
                  }

                  String var16x;
                  if (var1) {
                     String var6x = var10x.getFusion();
                     String var15x = var10x.getMount();
                     var16x = "   sensors " + var6x + ", mount " + var15x;
                  } else {
                     var16x = "";
                  }

                  String var24x;
                  if (var10x.getHasFix()) {
                     var24x = String.format(Locale.US, "\n%.7f, %.7f", Arrays.copyOf(new Object[]{var10x.getLat(), var10x.getLon()}, 2));
                     Intrinsics.checkNotNullExpressionValue(var24x, "format(...)");
                  } else {
                     var24x = "";
                  }

                  var5x = var9x + "  ± " + var11x + "   " + var3x + " satellites" + var16x + var24x;
               }

               var7x.setText(var5x);
            }

            TextView var30 = this.$rnd;
            if (var30 != null) {
               boolean var13x;
               if (Ntrip.INSTANCE.getSource().length() > 0) {
                  var13x = true;
               } else {
                  var13x = false;
               }

               String var25x;
               if (var13x) {
                  String var17x = Ntrip.INSTANCE.getSource();
                  var25x = var17x + "\n";
               } else {
                  var25x = "";
               }

               String var12x = Ntrip.INSTANCE.getState();
               Object var29 = null;
               Integer var18x;
               if (var10x != null) {
                  var18x = var10x.getCorrectionAgeS();
               } else {
                  var18x = null;
               }

               String var27;
               if (var18x != null) {
                  var18x = var10x.getCorrectionAgeS();
                  var27 = "   (receiver using corrections ≤ " + var18x + " s old)";
               } else {
                  var27 = "";
               }

               Double var20x = (Double)var29;
               if (var10x != null) {
                  var20x = var10x.getRtcmAgeS();
               }

               String var22x;
               if (var20x != null) {
                  String var21x = String.format("%.0f", Arrays.copyOf(new Object[]{var10x.getRtcmAgeS()}, 1));
                  Intrinsics.checkNotNullExpressionValue(var21x, "format(...)");
                  var8x = NtripCore.INSTANCE.size(var10x.getRtcmBytes());
                  var22x = "\ncomma: last corrections " + var21x + " s ago, " + var8x + " in all";
               } else {
                  var22x = var8x;
                  if (var10x != null) {
                     var22x = "\ncomma: no corrections since it started";
                  }
               }

               var30.setText(var25x + var12x + var27 + var22x);
            }

            TextView var26 = this.$rld;
            if (var26 != null) {
               Object var23x = CollectionsKt.joinToString$default(Ntrip.INSTANCE.recent(25), "\n", null, null, 0, null, null, 62, null);
               boolean var14x;
               if (var23x.length() == 0) {
                  var14x = var2x;
               } else {
                  var14x = false;
               }

               if (var14x) {
                  var23x = "nothing yet";
               }

               var26.setText((CharSequence)var23x);
            }

            this.this$0.handler.postDelayed(this, 1000L);
         }
      };
      this.livePoll((View)var5, var25);
      this.rtkFusionSection((View)var5, var6);
      var4 = this.ui;
      CarUi var26 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var26 = null;
      }

      var7 = (View)var26.header("NTRIP caster");
      var4 = this.ui;
      CarUi var27 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var27 = null;
      }

      var6.addView(var7, (LayoutParams)CarUi.lp$default(var27, 0, 0, 3, null));
      this.toggleCard(
         var6,
         "Use RTK corrections",
         "Connect to the caster while the comma and the receiver are connected.",
         new SettingsActivity$$ExternalSyntheticLambda134(this),
         new SettingsActivity$$ExternalSyntheticLambda137(this)
      );
      tabletRtkPage$field$default(
         this,
         var6,
         "Caster",
         new SettingsActivity$$ExternalSyntheticLambda138(this),
         "e.g. the address in NYSNet's activation email",
         false,
         false,
         new SettingsActivity$$ExternalSyntheticLambda139(),
         96,
         null
      );
      tabletRtkPage$field$default(
         this,
         var6,
         "Port",
         new SettingsActivity$$ExternalSyntheticLambda140(this),
         "2101",
         false,
         true,
         new SettingsActivity$$ExternalSyntheticLambda141(),
         32,
         null
      );
      tabletRtkPage$field$default(
         this,
         var6,
         "Username",
         new SettingsActivity$$ExternalSyntheticLambda142(this),
         "your NTRIP username",
         false,
         false,
         new SettingsActivity$$ExternalSyntheticLambda143(),
         96,
         null
      );
      tabletRtkPage$field$default(
         this,
         var6,
         "Password",
         new SettingsActivity$$ExternalSyntheticLambda145(this),
         "your NTRIP password",
         true,
         false,
         new SettingsActivity$$ExternalSyntheticLambda146(),
         64,
         null
      );
      CarUi var28 = this.ui;
      if (var28 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var28 = null;
      }

      var var46 = tabletRtkPage$cfg(this).getMount();
      boolean var1;
      if (var46.length() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      if (var1) {
         var46 = "not set";
      }

      var3 = CarUi.row$default(var28, "Mountpoint", var46, 0, null, null, 28, null);
      LinearLayout var47 = (LinearLayout)var3.component1();
      TextView var54 = (TextView)var3.component2();
      CarUi var30 = this.ui;
      if (var30 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var30 = null;
      }

      var47.addView((View)CarUi.button$default(var30, "Pick", false, new SettingsActivity$$ExternalSyntheticLambda135(this, var54), 2, null));
      CarUi var31 = this.ui;
      if (var31 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var31 = null;
      }

      var47.addView((View)CarUi.button$default(var31, "Type", false, new SettingsActivity$$ExternalSyntheticLambda136(this, var54), 2, null));
      var7 = (View)var47;
      var4 = this.ui;
      CarUi var32 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var32 = null;
      }

      var6.addView(var7, (LayoutParams)CarUi.lp$default(var32, 0, 0, 3, null));
      var4 = this.ui;
      CarUi var33 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var33 = null;
      }

      var7 = (View)var33.note(
         "Pick: lists the caster's mountpoints, nearest first once the receiver has a fix. A network mountpoint (one that builds a station for your position) works anywhere in the network; a single station's works best within ~20 miles of it. Your position goes to the caster every 10 s, as NTRIP requires."
      );
      var4 = this.ui;
      CarUi var34 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var34 = null;
      }

      var6.addView(var7, (LayoutParams)CarUi.lp$default(var34, 0, 0, 3, null));
      this.backupCasterSection(var6);
      return (View)var5;
   }

   private static final Ntrip.Config tabletRtkPage$cfg(SettingsActivity var0) {
      return Ntrip.INSTANCE.config((Context)var0);
   }

   private static final void tabletRtkPage$field(
      SettingsActivity var0,
      LinearLayout var1,
      String var2,
      Function0<String> var3,
      String var4,
      boolean var5,
      boolean var6,
      Function2<? super Ntrip.Config, ? super String, Ntrip.Config> var7
   ) {
      CarUi var9 = var0.ui;
      if (var9 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var9 = null;
      }

      LinearLayout var10 = (CharSequence)var3.invoke();
      boolean var8;
      if (var10.length() == 0) {
         var8 = true;
      } else {
         var8 = false;
      }

      if (var8) {
         var10 = "not set";
      }

      Pair var15 = CarUi.row$default(var9, var2, (String)var10, 0, null, null, 28, null);
      var10 = (LinearLayout)var15.component1();
      TextView var11 = (TextView)var15.component2();
      var9 = var0.ui;
      if (var9 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var9 = null;
      }

      var10.addView(
         (View)CarUi.button$default(
            var9, "Change", false, new SettingsActivity$$ExternalSyntheticLambda244(var0, var2, var5, var3, var4, var6, var7, var11), 2, null
         )
      );
      View var14 = (View)var10;
      CarUi var13 = var0.ui;
      CarUi var12 = var13;
      if (var13 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var12 = null;
      }

      var1.addView(var14, (LayoutParams)CarUi.lp$default(var12, 0, 0, 3, null));
   }

   // $VF: synthetic method
   static void tabletRtkPage$field$default(
      SettingsActivity var0, LinearLayout var1, String var2, Function0 var3, String var4, boolean var5, boolean var6, Function2 var7, int var8, Object var9
   ) {
      if ((var8 & 32) != 0) {
         var5 = false;
      }

      if ((var8 & 64) != 0) {
         var6 = false;
      }

      tabletRtkPage$field(var0, var1, var2, var3, var4, var5, var6, var7);
   }

   private static final Unit tabletRtkPage$field$lambda$198(
      SettingsActivity var0, String var1, boolean var2, Function0 var3, String var4, boolean var5, Function2 var6, TextView var7
   ) {
      EditText var12 = new EditText((Context)var0);
      String var11;
      if (var2) {
         var11 = "";
      } else {
         var11 = (String)var3.invoke();
      }

      var12.setText(var11);
      var12.setHint(var4);
      int var8;
      if (var5) {
         var8 = 2;
      } else if (var2) {
         var8 = 129;
      } else {
         var8 = 524289;
      }

      var12.setInputType(var8);
      CarUi var18 = var0.ui;
      CarUi var13 = var18;
      if (var18 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var13 = null;
      }

      int var9 = var13.dp(20);
      CarUi var19 = var0.ui;
      CarUi var14 = var19;
      if (var19 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var14 = null;
      }

      var8 = var14.dp(16);
      CarUi var20 = var0.ui;
      CarUi var15 = var20;
      if (var20 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var15 = null;
      }

      int var10 = var15.dp(20);
      CarUi var21 = var0.ui;
      CarUi var16 = var21;
      if (var21 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var16 = null;
      }

      var12.setPadding(var9, var8, var10, var16.dp(16));
      new Builder((Context)var0)
         .setTitle(var1)
         .setView((View)var12)
         .setPositiveButton("Save", new SettingsActivity$$ExternalSyntheticLambda85(var0, var6, var12, var7, var3))
         .setNegativeButton("Cancel", null)
         .show();
      return Unit.INSTANCE;
   }

   private static final void tabletRtkPage$field$lambda$198$lambda$197(
      SettingsActivity var0, Function2 var1, EditText var2, TextView var3, Function0 var4, DialogInterface var5, int var6
   ) {
      Ntrip.INSTANCE.save((Context)var0, (Ntrip.Config)var1.invoke(tabletRtkPage$cfg(var0), var2.getText().toString()));
      if (var3 != null) {
         Object var7 = (CharSequence)var4.invoke();
         boolean var8;
         if (var7.length() == 0) {
            var8 = true;
         } else {
            var8 = false;
         }

         if (var8) {
            var7 = "not set";
         }

         var3.setText((CharSequence)var7);
      }
   }

   private static final boolean tabletRtkPage$lambda$192(SettingsActivity var0) {
      return tabletRtkPage$cfg(var0).getOn();
   }

   private static final Unit tabletRtkPage$lambda$193(SettingsActivity var0, boolean var1) {
      Ntrip.Config var2 = tabletRtkPage$cfg(var0);
      Ntrip.INSTANCE.save((Context)var0, new Ntrip.Config(var1, var2.getHost(), var2.getPort(), var2.getMount(), var2.getUser(), var2.getPass()));
      return Unit.INSTANCE;
   }

   private static final String tabletRtkPage$lambda$199(SettingsActivity var0) {
      return tabletRtkPage$cfg(var0).getHost();
   }

   private static final Ntrip.Config tabletRtkPage$lambda$200(Ntrip.Config var0, String var1) {
      Intrinsics.checkNotNullParameter(var0, "c");
      Intrinsics.checkNotNullParameter(var1, "v");
      return new Ntrip.Config(var0.getOn(), var1, var0.getPort(), var0.getMount(), var0.getUser(), var0.getPass());
   }

   private static final String tabletRtkPage$lambda$201(SettingsActivity var0) {
      return String.valueOf(tabletRtkPage$cfg(var0).getPort());
   }

   private static final Ntrip.Config tabletRtkPage$lambda$202(Ntrip.Config var0, String var1) {
      Intrinsics.checkNotNullParameter(var0, "c");
      Intrinsics.checkNotNullParameter(var1, "v");
      boolean var3 = var0.getOn();
      String var4 = var0.getHost();
      Integer var5 = StringsKt.toIntOrNull(var1);
      int var2;
      if (var5 != null) {
         var2 = var5;
      } else {
         var2 = var0.getPort();
      }

      return new Ntrip.Config(var3, var4, var2, var0.getMount(), var0.getUser(), var0.getPass());
   }

   private static final String tabletRtkPage$lambda$203(SettingsActivity var0) {
      return tabletRtkPage$cfg(var0).getUser();
   }

   private static final Ntrip.Config tabletRtkPage$lambda$204(Ntrip.Config var0, String var1) {
      Intrinsics.checkNotNullParameter(var0, "c");
      Intrinsics.checkNotNullParameter(var1, "v");
      return new Ntrip.Config(var0.getOn(), var0.getHost(), var0.getPort(), var0.getMount(), var1, var0.getPass());
   }

   private static final String tabletRtkPage$lambda$205(SettingsActivity var0) {
      boolean var1;
      if (tabletRtkPage$cfg(var0).getPass().length() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      String var2;
      if (var1) {
         var2 = "";
      } else {
         var2 = "••••••";
      }

      return var2;
   }

   private static final Ntrip.Config tabletRtkPage$lambda$206(Ntrip.Config var0, String var1) {
      Intrinsics.checkNotNullParameter(var0, "c");
      Intrinsics.checkNotNullParameter(var1, "v");
      return new Ntrip.Config(var0.getOn(), var0.getHost(), var0.getPort(), var0.getMount(), var0.getUser(), var1);
   }

   private static final Unit tabletRtkPage$lambda$212(SettingsActivity var0, TextView var1) {
      Ntrip.Config var2 = tabletRtkPage$cfg(var0);
      if (StringsKt.isBlank(var2.getHost())) {
         Toast.makeText((Context)var0, "Set the caster first", 0).show();
         return Unit.INSTANCE;
      } else {
         if (var1 != null) {
            var1.setText("loading the caster's list…");
         }

         Ntrip.INSTANCE
            .loadMounts(var2.getHost(), var2.getPort(), var2.getUser(), var2.getPass(), new SettingsActivity$$ExternalSyntheticLambda115(var1, var0));
         return Unit.INSTANCE;
      }
   }

   private static final Unit tabletRtkPage$lambda$212$lambda$211(TextView var0, SettingsActivity var1, List var2, String var3) {
      if (var0 != null) {
         Object var11 = tabletRtkPage$cfg(var1).getMount();
         boolean var6;
         if (var11.length() == 0) {
            var6 = true;
         } else {
            var6 = false;
         }

         if (var6) {
            var11 = "not set";
         }

         var0.setText((CharSequence)var11);
      }

      Collection var23 = var2;
      boolean var21;
      if (var23 != null && !var23.isEmpty()) {
         var21 = false;
      } else {
         var21 = true;
      }

      if (var21) {
         Builder var19 = new Builder((Context)var1);
         if (var3 == null) {
            var3 = "the caster listed none";
         }

         var19.setMessage("Couldn't get the mountpoints: " + var3).setPositiveButton("OK", null).show();
         return Unit.INSTANCE;
      } else {
         RtkStatus var24 = Ntrip.INSTANCE.getRtk();
         Iterable var12 = var2;
         boolean var8 = false;
         var var15 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var12, 10));
         boolean var7 = false;

         for (NtripCore.Mount var13 : var12) {
            double var4;
            if (var24 != null && var24.getHasFix()) {
               var4 = var13.milesFrom(var24.getLat(), var24.getLon());
            } else {
               var4 = Double.NaN;
            }

            String var17 = var13.getName();
            String var16 = var13.getPlace();
            String var18 = var13.getFormat();
            if (!Double.isInfinite(var4) && !Double.isNaN(var4)) {
               var21 = true;
            } else {
               var21 = false;
            }

            String var25;
            if (var21) {
               long var9 = Math.round(var4);
               var25 = "   " + var9 + " mi";
            } else {
               var25 = "";
            }

            var15.add(var17 + "   " + var16 + "   " + var18 + var25);
         }

         List var20 = var15;
         new Builder((Context)var1)
            .setTitle("Mountpoint (nearest first)")
            .setItems(var20.toArray(new String[0]), new SettingsActivity$$ExternalSyntheticLambda48(var1, var2, var0))
            .setNegativeButton("Cancel", null)
            .show();
         return Unit.INSTANCE;
      }
   }

   private static final void tabletRtkPage$lambda$212$lambda$211$lambda$210(SettingsActivity var0, List var1, TextView var2, DialogInterface var3, int var4) {
      Ntrip.Config var5 = tabletRtkPage$cfg(var0);
      Ntrip.INSTANCE
         .save(
            (Context)var0,
            new Ntrip.Config(var5.getOn(), var5.getHost(), var5.getPort(), ((NtripCore.Mount)var1.get(var4)).getName(), var5.getUser(), var5.getPass())
         );
      if (var2 != null) {
         var2.setText(((NtripCore.Mount)var1.get(var4)).getName());
      }
   }

   private static final Unit tabletRtkPage$lambda$216(SettingsActivity var0, TextView var1) {
      EditText var7 = new EditText((Context)var0);
      var7.setText(tabletRtkPage$cfg(var0).getMount());
      CarUi var6 = var0.ui;
      CarUi var5 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var5 = null;
      }

      int var3 = var5.dp(20);
      var6 = var0.ui;
      var5 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var5 = null;
      }

      int var4 = var5.dp(16);
      var6 = var0.ui;
      var5 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var5 = null;
      }

      int var2 = var5.dp(20);
      var6 = var0.ui;
      var5 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var5 = null;
      }

      var7.setPadding(var3, var4, var2, var5.dp(16));
      new Builder((Context)var0)
         .setTitle("Mountpoint")
         .setView((View)var7)
         .setPositiveButton("Save", new SettingsActivity$$ExternalSyntheticLambda49(var0, var7, var1))
         .setNegativeButton("Cancel", null)
         .show();
      return Unit.INSTANCE;
   }

   private static final void tabletRtkPage$lambda$216$lambda$215(SettingsActivity var0, EditText var1, TextView var2, DialogInterface var3, int var4) {
      Ntrip.Config var6 = tabletRtkPage$cfg(var0);
      Ntrip.INSTANCE
         .save((Context)var0, new Ntrip.Config(var6.getOn(), var6.getHost(), var6.getPort(), var1.getText().toString(), var6.getUser(), var6.getPass()));
      if (var2 != null) {
         Object var5 = tabletRtkPage$cfg(var0).getMount();
         boolean var7;
         if (var5.length() == 0) {
            var7 = true;
         } else {
            var7 = false;
         }

         if (var7) {
            var5 = "not set";
         }

         var2.setText((CharSequence)var5);
      }
   }

   private final View tabletStoragePage() {
      Pair var1 = this.page();
      ScrollView var3 = (ScrollView)var1.component1();
      LinearLayout var6 = (LinearLayout)var1.component2();
      LinearLayout var4 = new LinearLayout((Context)this);
      var4.setOrientation(1);
      View var5 = (View)var4;
      CarUi var2 = this.ui;
      CarUi var7 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var7 = null;
      }

      var6.addView(var5, (LayoutParams)CarUi.lp$default(var7, 0, 0, 3, null));
      BooleanRef var12 = new BooleanRef();
      CarUi var8 = this.ui;
      if (var8 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var8 = null;
      }

      var var13 = (View & LinearLayout)CarUi.row$default(var8, "Storage", "loading…", 0, null, null, 28, null).component1();
      var2 = this.ui;
      CarUi var9 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var9 = null;
      }

      var4.addView(var13, (LayoutParams)CarUi.lp$default(var9, 0, 0, 3, null));
      Runnable var10 = new Anon_tabletStoragePage_poll_1(var3, new BooleanRef(), this, var12, var4);
      this.livePoll((View)var3, var10);
      return (View)var3;
   }

   private static final void tabletStoragePage$render(LinearLayout var0, BooleanRef var1, SettingsActivity var2, JSONObject var3) {
      var0.removeAllViews();
      StorageInfo.Copying var6 = StorageInfo.INSTANCE.copying(var3);
      boolean var5;
      if (var6 != null) {
         var5 = true;
      } else {
         var5 = false;
      }

      var1.element = var5;

      for (Pair var10 : StorageInfo.INSTANCE.rows(var3)) {
         String var8 = (String)var10.component1();
         String var9 = (String)var10.component2();
         CarUi var16 = var2.ui;
         CarUi var11 = var16;
         if (var16 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var11 = null;
         }

         var var20 = (View & LinearLayout)CarUi.row$default(var11, var8, var9, 0, null, null, 28, null).component1();
         CarUi var17 = var2.ui;
         CarUi var12 = var17;
         if (var17 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var12 = null;
         }

         var0.addView(var20, (LayoutParams)CarUi.lp$default(var12, 0, 0, 3, null));
         if (Intrinsics.areEqual(var8, "Copying to the stick now")) {
            Integer var13;
            if (var6 != null) {
               var13 = var6.getPct();
            } else {
               var13 = null;
            }

            if (var13 != null) {
               ProgressBar var14 = new ProgressBar((Context)var2, null, 16842872);
               var14.setMax(100);
               var14.setProgress(var6.getPct());
               var14.setProgressTintList(ColorStateList.valueOf(Color.rgb(94, 229, 160)));
               View var15 = (View)var14;
               android.widget.LinearLayout.LayoutParams var18 = new android.widget.LinearLayout.LayoutParams(
                  -1, (int)(6 * var2.getResources().getDisplayMetrics().density)
               );
               int var4 = (int)(16 * var2.getResources().getDisplayMetrics().density);
               var18.setMargins(var4, 0, var4, var4);
               Unit var19 = Unit.INSTANCE;
               var0.addView(var15, (LayoutParams)var18);
            }
         }
      }
   }

   private final View tabletThemePage() {
      Pair var1 = this.page();
      ScrollView var3 = (ScrollView)var1.component1();
      LinearLayout var4 = (LinearLayout)var1.component2();
      UiSettings var5 = new UiSettings((Context)this);
      CarUi var2 = this.ui;
      CarUi var6 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var6 = null;
      }

      var4.addView(
         (View)var6.note(
            "Which widgets are on the main screen, where they sit and how big they are: long-press the road view while driving to open the layout editor. Layouts are saved per theme (day / night)."
         )
      );
      this.choiceCard(
         var4,
         "Theme",
         "Auto switches at sunset and sunrise from the GPS position.",
         CollectionsKt.listOf(new Pair[]{TuplesKt.to("auto", "Auto"), TuplesKt.to("day", "Day"), TuplesKt.to("night", "Night")}),
         new SettingsActivity$$ExternalSyntheticLambda54(var5),
         new SettingsActivity$$ExternalSyntheticLambda56(var5)
      );
      return (View)var3;
   }

   private static final String tabletThemePage$lambda$163(UiSettings var0) {
      return var0.getStr("theme_mode", "auto");
   }

   private static final Unit tabletThemePage$lambda$164(UiSettings var0, String var1) {
      Intrinsics.checkNotNullParameter(var1, "it");
      var0.setStr("theme_mode", var1);
      return Unit.INSTANCE;
   }

   private final View tabletViewPage() {
      Pair var3 = this.page();
      ScrollView var5 = (ScrollView)var3.component1();
      LinearLayout var6 = (LinearLayout)var3.component2();
      UiSettings var9 = new UiSettings((Context)this);
      Iterable var7 = UiSettings.Companion.getITEMS();
      var var4 = new ArrayList();

      for (Object var8 : var7) {
         if (Intrinsics.areEqual(((UiSettings.Item)var8).getGroup(), "Camera")) {
            var4.add(var8);
         }
      }

      for (UiSettings.Item var39 : var4) {
         this.toggleCard(
            var6,
            var39.getTitle(),
            null,
            new SettingsActivity$$ExternalSyntheticLambda100(var9, var39),
            new SettingsActivity$$ExternalSyntheticLambda101(var9, var39)
         );
      }

      LayoutSeg var27 = new LayoutSeg((Context)this);
      CarUi var10 = this.ui;
      if (var10 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var10 = null;
      }

      String var29;
      if (var27.getHasModel()) {
         String var28 = String.format("%.1f", Arrays.copyOf(new Object[]{var27.modelFile().length() / 1000000.0}, 1));
         Intrinsics.checkNotNullExpressionValue(var28, "format(...)");
         var29 = "Installed (" + var28 + " MB): the Scene shows sidewalks and grass";
      } else {
         var29 = "Not installed: the Scene shows the road only. Download layout-model.lite from the chat on this tablet, then tap Import";
      }

      var3 = CarUi.row$default(var10, "Scene sidewalk model", var29, 0, null, null, 28, null);
      LinearLayout var30 = (LinearLayout)var3.component1();
      TextView var40 = (TextView)var3.component2();
      CarUi var12 = this.ui;
      if (var12 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var12 = null;
      }

      var30.addView((View)CarUi.button$default(var12, "Import…", false, new SettingsActivity$$ExternalSyntheticLambda102(this, var40), 2, null));
      View var41 = (View)var30;
      CarUi var31 = this.ui;
      CarUi var13 = var31;
      if (var31 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var13 = null;
      }

      var6.addView(var41, (LayoutParams)CarUi.lp$default(var13, 0, 0, 3, null));
      LaneModel var32 = new LaneModel((Context)this);
      CarUi var14 = this.ui;
      if (var14 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var14 = null;
      }

      var3 = CarUi.row$default(var14, "Lane model", this.lanModelText(var32), 0, null, null, 28, null);
      LinearLayout var42 = (LinearLayout)var3.component1();
      TextView var47 = (TextView)var3.component2();
      CarUi var16 = this.ui;
      if (var16 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var16 = null;
      }

      var42.addView((View)CarUi.button$default(var16, "Import…", false, new SettingsActivity$$ExternalSyntheticLambda103(this, var32, var47), 2, null));
      View var43 = (View)var42;
      CarUi var33 = this.ui;
      CarUi var17 = var33;
      if (var33 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var17 = null;
      }

      var6.addView(var43, (LayoutParams)CarUi.lp$default(var17, 0, 0, 3, null));
      CarUi var18 = this.ui;
      if (var18 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var18 = null;
      }

      var3 = CarUi.row$default(var18, "Detector model", tabletViewPage$detText(this), 0, null, null, 28, null);
      LinearLayout var34 = (LinearLayout)var3.component1();
      TextView var44 = (TextView)var3.component2();
      CarUi var20 = this.ui;
      if (var20 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var20 = null;
      }

      var34.addView((View)CarUi.button$default(var20, "Import…", false, new SettingsActivity$$ExternalSyntheticLambda104(this, var44), 2, null));
      View var45 = (View)var34;
      CarUi var35 = this.ui;
      CarUi var21 = var35;
      if (var35 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var21 = null;
      }

      var6.addView(var45, (LayoutParams)CarUi.lp$default(var21, 0, 0, 3, null));
      DetMasks.INSTANCE.init((Context)this);
      CarUi var22 = this.ui;
      if (var22 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var22 = null;
      }

      int var2 = DetMasks.INSTANCE.get(false).size();
      int var1 = DetMasks.INSTANCE.get(true).size();
      LinearLayout var36 = (LinearLayout)CarUi.row$default(
            var22,
            "Detector masks",
            "Areas the object detector ignores: your hood, the dash, the mirror mount, reflections. Drawn on the camera picture while the car is on. Now: road camera "
               + var2
               + ", wide camera "
               + var1
               + " area(s).",
            0,
            null,
            null,
            28,
            null
         )
         .component1();
      CarUi var23 = this.ui;
      if (var23 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var23 = null;
      }

      var36.addView((View)CarUi.button$default(var23, "Edit…", false, new SettingsActivity$$ExternalSyntheticLambda105(this), 2, null));
      View var46 = (View)var36;
      CarUi var37 = this.ui;
      CarUi var24 = var37;
      if (var37 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var24 = null;
      }

      var6.addView(var46, (LayoutParams)CarUi.lp$default(var24, 0, 0, 3, null));
      return (View)var5;
   }

   private static final String tabletViewPage$detText(SettingsActivity var0) {
      String var1;
      if (ModelDownload.INSTANCE.installed((Context)var0)) {
         var1 = "Installed (18 MB): the camera finds cars, people, lights and signs";
      } else {
         var1 = "Not installed: download yolox_s_384x640_fp16.lite from the chat on this tablet, then tap Import";
      }

      return var1;
   }

   private static final boolean tabletViewPage$lambda$166(UiSettings var0, UiSettings.Item var1) {
      return var0.get(var1.getKey());
   }

   private static final Unit tabletViewPage$lambda$167(UiSettings var0, UiSettings.Item var1, boolean var2) {
      var0.set(var1.getKey(), var2);
      return Unit.INSTANCE;
   }

   private static final Unit tabletViewPage$lambda$169(SettingsActivity var0, TextView var1) {
      var0.onModelPicked = new SettingsActivity$$ExternalSyntheticLambda147(var1);
      var0.pickModel.launch(new String[]{"*/*"});
      return Unit.INSTANCE;
   }

   private static final Unit tabletViewPage$lambda$169$lambda$168(TextView var0, String var1) {
      Intrinsics.checkNotNullParameter(var1, "msg");
      if (var0 != null) {
         var0.setText(var1);
      }

      return Unit.INSTANCE;
   }

   private static final Unit tabletViewPage$lambda$173(SettingsActivity var0, LaneModel var1, TextView var2) {
      var0.onFilePicked = new SettingsActivity$$ExternalSyntheticLambda184(var1, var0, var2);
      var0.pickAny.launch(new String[]{"*/*"});
      return Unit.INSTANCE;
   }

   private static final Unit tabletViewPage$lambda$173$lambda$172(LaneModel var0, SettingsActivity var1, TextView var2, Uri var3) {
      if (var3 != null) {
         new Thread(new SettingsActivity$$ExternalSyntheticLambda90(var0, var3, var1, var2)).start();
      }

      return Unit.INSTANCE;
   }

   private static final void tabletViewPage$lambda$173$lambda$172$lambda$171(LaneModel var0, Uri var1, SettingsActivity var2, TextView var3) {
      var2.runOnUiThread(new SettingsActivity$$ExternalSyntheticLambda161(var3, var0.importModel(var1), var2, var0));
   }

   private static final void tabletViewPage$lambda$173$lambda$172$lambda$171$lambda$170(TextView var0, String var1, SettingsActivity var2, LaneModel var3) {
      if (var0 != null) {
         CharSequence var4;
         if (var1 != null) {
            var4 = var1;
         } else {
            var4 = var2.lanModelText(var3);
         }

         var0.setText(var4);
      }
   }

   private static final Unit tabletViewPage$lambda$177(SettingsActivity var0, TextView var1) {
      var0.onFilePicked = new SettingsActivity$$ExternalSyntheticLambda74(var1, var0);
      var0.pickAny.launch(new String[]{"*/*"});
      return Unit.INSTANCE;
   }

   private static final Unit tabletViewPage$lambda$177$lambda$176(TextView var0, SettingsActivity var1, Uri var2) {
      if (var2 != null) {
         if (var0 != null) {
            var0.setText("checking…");
         }

         new Thread(new SettingsActivity$$ExternalSyntheticLambda180(var1, var2, var0)).start();
      }

      return Unit.INSTANCE;
   }

   private static final void tabletViewPage$lambda$177$lambda$176$lambda$175(SettingsActivity var0, Uri var1, TextView var2) {
      var0.runOnUiThread(new SettingsActivity$$ExternalSyntheticLambda162(var2, ModelDownload.INSTANCE.importDetector((Context)var0, var1), var0));
   }

   private static final void tabletViewPage$lambda$177$lambda$176$lambda$175$lambda$174(TextView var0, String var1, SettingsActivity var2) {
      if (var0 != null) {
         CharSequence var3;
         if (var1 != null) {
            var3 = var1;
         } else {
            var3 = tabletViewPage$detText(var2);
         }

         var0.setText(var3);
      }
   }

   private static final Unit tabletViewPage$lambda$178(SettingsActivity var0) {
      var0.startActivity(new Intent((Context)var0, MainActivity.class).putExtra("edit_masks", true).addFlags(603979776));
      var0.finish();
      return Unit.INSTANCE;
   }

   private final View tabletVoicePage() {
      Pair var2 = this.page();
      ScrollView var4 = (ScrollView)var2.component1();
      LinearLayout var5 = (LinearLayout)var2.component2();
      CarUi var3 = this.ui;
      CarUi var10 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var10 = null;
      }

      View var6 = (View)var10.note(
         "The \"Change lanes?\" card says what it wants, so you don't have to look: \"Change lanes left?\", \"Exit on the right in half a mile. Two lanes to go. Change lanes right?\", then \"Signaling left.\", \"Changing lanes.\", \"Done.\" - or why it stopped (\"Car in the left blind spot. Cancelled.\"). It plays on the tablet's ALARM volume, set below - music and navigation volume don't affect it."
      );
      var3 = this.ui;
      CarUi var11 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var11 = null;
      }

      var5.addView(var6, (LayoutParams)CarUi.lp$default(var11, 0, 0, 3, null));
      CarUi var12 = this.ui;
      if (var12 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var12 = null;
      }

      String var32;
      if (Speech.INSTANCE.getEnabled()) {
         var32 = "On";
      } else {
         var32 = "Off - chimes only";
      }

      LinearLayout var45 = (LinearLayout)CarUi.row$default(var12, "Voice alerts", var32, 0, null, null, 28, null).component1();
      CarUi var13 = this.ui;
      if (var13 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var13 = null;
      }

      String var33;
      if (Speech.INSTANCE.getEnabled()) {
         var33 = "Turn off";
      } else {
         var33 = "Turn on";
      }

      var45.addView((View)CarUi.button$default(var13, var33, false, new SettingsActivity$$ExternalSyntheticLambda144(this), 2, null));
      var6 = (View)var45;
      var3 = this.ui;
      CarUi var14 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var14 = null;
      }

      var5.addView(var6, (LayoutParams)CarUi.lp$default(var14, 0, 0, 3, null));
      var3 = this.ui;
      CarUi var15 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var15 = null;
      }

      var6 = (View)var15.header("Sound");
      var3 = this.ui;
      CarUi var16 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var16 = null;
      }

      var5.addView(var6, (LayoutParams)CarUi.lp$default(var16, 0, 0, 3, null));
      this.slider(
         var5,
         "Talking speed",
         0.6F,
         1.8F,
         0.05F,
         Speech.INSTANCE.getSpeed(),
         new SettingsActivity$$ExternalSyntheticLambda171(),
         1.0F,
         new SettingsActivity$$ExternalSyntheticLambda172(),
         new SettingsActivity$$ExternalSyntheticLambda173()
      );
      this.slider(
         var5,
         "Pitch",
         -6.0F,
         6.0F,
         0.5F,
         Speech.INSTANCE.getPitch(),
         new SettingsActivity$$ExternalSyntheticLambda174(),
         0.0F,
         new SettingsActivity$$ExternalSyntheticLambda175(),
         new SettingsActivity$$ExternalSyntheticLambda176()
      );
      this.slider(
         var5,
         "Chime",
         0.0F,
         2.0F,
         0.05F,
         Speech.INSTANCE.getChime(),
         new SettingsActivity$$ExternalSyntheticLambda177(),
         1.0F,
         new SettingsActivity$$ExternalSyntheticLambda178(),
         new SettingsActivity$$ExternalSyntheticLambda179()
      );
      this.slider(
         var5,
         "Background music",
         0.0F,
         2.0F,
         0.05F,
         Speech.INSTANCE.getPad(),
         new SettingsActivity$$ExternalSyntheticLambda155(),
         1.0F,
         new SettingsActivity$$ExternalSyntheticLambda163(),
         new SettingsActivity$$ExternalSyntheticLambda164()
      );
      var3 = this.ui;
      CarUi var17 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var17 = null;
      }

      var6 = (View)var17.note("Speed, pitch, chime and music change instantly, for every voice. The tablet's own voices below only follow the speed.");
      var3 = this.ui;
      CarUi var18 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var18 = null;
      }

      var5.addView(var6, (LayoutParams)CarUi.lp$default(var18, 0, 0, 3, null));
      Pair var19 = (Pair)this.getSystemService("audio");
      Intrinsics.checkNotNull(var19, "null cannot be cast to non-null type android.media.AudioManager");
      AudioManager var49 = (AudioManager)var19;
      int var1 = var49.getStreamMaxVolume(4);
      var19 = this.ui;
      if (var19 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var19 = null;
      }

      var19 = CarUi.row$default((CarUi)var19, "Volume", "…", 0, null, null, 28, null);
      LinearLayout var7 = (LinearLayout)var19.component1();
      TextView var8 = (TextView)var19.component2();
      CarUi var22 = this.ui;
      if (var22 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var22 = null;
      }

      var7.addView((View)CarUi.button$default(var22, "−", false, new SettingsActivity$$ExternalSyntheticLambda165(var49, 4, var1, this, var8), 2, null));
      View var9 = new View((Context)this);
      var3 = this.ui;
      CarUi var23 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var23 = null;
      }

      var7.addView(var9, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(var23.dp(8), 1)));
      CarUi var24 = this.ui;
      if (var24 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var24 = null;
      }

      var7.addView((View)CarUi.button$default(var24, "+", false, new SettingsActivity$$ExternalSyntheticLambda166(var49, 4, var1, this, var8), 2, null));
      var9 = new View((Context)this);
      var3 = this.ui;
      CarUi var25 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var25 = null;
      }

      var7.addView(var9, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(var25.dp(8), 1)));
      CarUi var26 = this.ui;
      if (var26 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var26 = null;
      }

      var7.addView((View)CarUi.button$default(var26, "Max", false, new SettingsActivity$$ExternalSyntheticLambda167(var1, var49, 4, this, var8), 2, null));
      tabletVoicePage$showV(var49, 4, var8, var1);
      var6 = (View)var7;
      var3 = this.ui;
      CarUi var27 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var27 = null;
      }

      var5.addView(var6, (LayoutParams)CarUi.lp$default(var27, 0, 0, 3, null));
      var3 = this.ui;
      CarUi var28 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var28 = null;
      }

      var6 = (View)var28.header("Voice");
      var3 = this.ui;
      CarUi var29 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var29 = null;
      }

      var5.addView(var6, (LayoutParams)CarUi.lp$default(var29, 0, 0, 3, null));
      LinearLayout var52 = new LinearLayout((Context)this);
      var52.setOrientation(1);
      View var53 = (View)var52;
      var3 = this.ui;
      CarUi var30 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var30 = null;
      }

      var5.addView(var53, (LayoutParams)CarUi.lp$default(var30, 0, 0, 3, null));
      Speech.INSTANCE
         .init(
            (Context)this,
            new SettingsActivity$$ExternalSyntheticLambda168(this, var52, "Exit on the right in half a mile, two lanes to go. Change lanes right?")
         );
      tabletVoicePage$fill(var52, this, "Exit on the right in half a mile, two lanes to go. Change lanes right?");
      var4.setTag(new SettingsActivity$$ExternalSyntheticLambda170(var52, this, "Exit on the right in half a mile, two lanes to go. Change lanes right?"));
      return (View)var4;
   }

   private static final void tabletVoicePage$fill(LinearLayout var0, SettingsActivity var1, String var2) {
      var0.removeAllViews();
      List var8 = AmyVoice.INSTANCE.packs();
      AmyVoice.Pack var5;
      if (Speech.INSTANCE.getUseAmy()) {
         var5 = AmyVoice.INSTANCE.active();
      } else {
         var5 = null;
      }

      Iterator var10 = var8.iterator();

      while (true) {
         boolean var4 = var10.hasNext();
         String var9 = "In use";
         String var7 = "✓ ";
         if (!var4) {
            var var27 = var8;
            boolean var16;
            if (var27 instanceof Collection && ((Collection)var27).isEmpty()) {
               var16 = true;
            } else {
               Iterator var28 = var27.iterator();

               while (true) {
                  if (!var28.hasNext()) {
                     var16 = true;
                     break;
                  }

                  if (Intrinsics.areEqual(((AmyVoice.Pack)var28.next()).getId(), "amy")) {
                     var16 = false;
                     break;
                  }
               }
            }

            if (var16) {
               CarUi var29 = var1.ui;
               if (var29 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("ui");
                  var29 = null;
               }

               LinearLayout var50 = (LinearLayout)CarUi.row$default(
                     var29,
                     "Amy · recorded on the PC",
                     "Not installed - tap Install and pick StarView-Amy-voice.zip (from Drive or Downloads).",
                     0,
                     null,
                     null,
                     28,
                     null
                  )
                  .component1();
               CarUi var42 = var1.ui;
               CarUi var30 = var42;
               if (var42 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("ui");
                  var30 = null;
               }

               var50.addView((View)var30.button("Install…", var8.isEmpty(), new SettingsActivity$$ExternalSyntheticLambda126(var1, var0, var2)));
               View var51 = (View)var50;
               CarUi var43 = var1.ui;
               CarUi var31 = var43;
               if (var43 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("ui");
                  var31 = null;
               }

               var0.addView(var51, (LayoutParams)CarUi.lp$default(var31, 0, 0, 3, null));
            }

            CarUi var32 = var1.ui;
            if (var32 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("ui");
               var32 = null;
            }

            LinearLayout var44 = (LinearLayout)CarUi.row$default(
                  var32,
                  "Make a new voice",
                  "Voice studio: pick any of 38 English voices (some have hundreds of different speakers), adjust it, and the tablet records every phrase in it.",
                  0,
                  null,
                  null,
                  28,
                  null
               )
               .component1();
            CarUi var52 = var1.ui;
            CarUi var33 = var52;
            if (var52 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("ui");
               var33 = null;
            }

            var44.addView((View)var33.button("Voice studio ›", true, new SettingsActivity$$ExternalSyntheticLambda127(var1)));
            View var60 = (View)var44;
            CarUi var53 = var1.ui;
            CarUi var34 = var53;
            if (var53 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("ui");
               var34 = null;
            }

            var0.addView(var60, (LayoutParams)CarUi.lp$default(var34, 0, 0, 3, null));
            CarUi var54 = var1.ui;
            CarUi var35 = var54;
            if (var54 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("ui");
               var35 = null;
            }

            View var61 = (View)var35.note("The tablet's own voices (no chime):");
            CarUi var55 = var1.ui;
            CarUi var36 = var55;
            if (var55 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("ui");
               var36 = null;
            }

            var0.addView(var61, (LayoutParams)CarUi.lp$default(var36, 0, 0, 3, null));
            List var37 = Speech.INSTANCE.voices();
            if (var37.isEmpty()) {
               CarUi var19 = var1.ui;
               CarUi var14 = var19;
               if (var19 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("ui");
                  var14 = null;
               }

               String var20;
               if (Speech.INSTANCE.getReady()) {
                  var20 = "No English voices installed - add one in Android Settings › Text-to-speech.";
               } else {
                  var20 = "Loading the tablet's voices…";
               }

               View var21 = (View)var14.note(var20);
               CarUi var15 = var1.ui;
               CarUi var13 = var15;
               if (var15 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("ui");
                  var13 = null;
               }

               var0.addView(var21, (LayoutParams)CarUi.lp$default(var13, 0, 0, 3, null));
               return;
            } else {
               for (Voice var63 : var37) {
                  if (var5 == null && Intrinsics.areEqual(var63.getName(), Speech.INSTANCE.getVoiceName())) {
                     var4 = true;
                  } else {
                     var4 = false;
                  }

                  CarUi var45 = var1.ui;
                  if (var45 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("ui");
                     var45 = null;
                  }

                  if (var4) {
                     var9 = "✓ ";
                  } else {
                     var9 = "";
                  }

                  String var67 = Speech.INSTANCE.label(var63);
                  LinearLayout var68 = (LinearLayout)CarUi.row$default(var45, var9 + var67, var63.getName(), 0, null, null, 28, null).component1();
                  CarUi var46 = var1.ui;
                  if (var46 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("ui");
                     var46 = null;
                  }

                  var68.addView((View)CarUi.button$default(var46, "▶ Play", false, new SettingsActivity$$ExternalSyntheticLambda128(var63, var2), 2, null));
                  var68.addView(tabletVoicePage$gap(var1));
                  CarUi var57 = var1.ui;
                  CarUi var47 = var57;
                  if (var57 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("ui");
                     var47 = null;
                  }

                  if (var4) {
                     var9 = "In use";
                  } else {
                     var9 = "Use";
                  }

                  var68.addView((View)var47.button(var9, var4, new SettingsActivity$$ExternalSyntheticLambda129(var63, var0, var1, var2)));
                  View var64 = (View)var68;
                  CarUi var59 = var1.ui;
                  CarUi var48 = var59;
                  if (var59 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("ui");
                     var48 = null;
                  }

                  var0.addView(var64, (LayoutParams)CarUi.lp$default(var48, 0, 0, 3, null));
               }

               return;
            }
         }

         AmyVoice.Pack var11 = (AmyVoice.Pack)var10.next();
         String var6;
         if (var5 != null) {
            var6 = var5.getId();
         } else {
            var6 = null;
         }

         var4 = Intrinsics.areEqual(var6, var11.getId());
         CarUi var22 = var1.ui;
         if (var22 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var22 = null;
         }

         if (!var4) {
            var7 = "";
         }

         String var12 = var11.getLabel();
         var12 = var7 + var12;
         if (Intrinsics.areEqual(var11.getId(), "amy")) {
            var7 = "Recorded on the PC with Piper. ";
         } else {
            var7 = "Made on this tablet in Voice studio. ";
         }

         int var3 = AmyVoice.INSTANCE.recordings(var11);
         LinearLayout var66 = (LinearLayout)CarUi.row$default(var22, var12, var7 + var3 + " phrases, works offline.", 0, null, null, 28, null).component1();
         CarUi var23 = var1.ui;
         if (var23 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var23 = null;
         }

         var66.addView((View)CarUi.button$default(var23, "▶ Play", false, new SettingsActivity$$ExternalSyntheticLambda123(var11, var0, var1, var2), 2, null));
         var66.addView(tabletVoicePage$gap(var1));
         CarUi var39 = var1.ui;
         CarUi var24 = var39;
         if (var39 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var24 = null;
         }

         if (var4) {
            var7 = var9;
         } else {
            var7 = "Use";
         }

         var66.addView((View)var24.button(var7, var4, new SettingsActivity$$ExternalSyntheticLambda124(var11, var0, var1, var2)));
         var66.addView(tabletVoicePage$gap(var1));
         CarUi var25 = var1.ui;
         if (var25 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var25 = null;
         }

         var66.addView((View)CarUi.button$default(var25, "Delete", false, new SettingsActivity$$ExternalSyntheticLambda125(var1, var11, var0, var2), 2, null));
         View var49 = (View)var66;
         CarUi var41 = var1.ui;
         CarUi var26 = var41;
         if (var41 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var26 = null;
         }

         var0.addView(var49, (LayoutParams)CarUi.lp$default(var26, 0, 0, 3, null));
      }
   }

   private static final Unit tabletVoicePage$fill$lambda$340(AmyVoice.Pack var0, LinearLayout var1, SettingsActivity var2, String var3) {
      AmyVoice.INSTANCE.use(var0.getId());
      AmyVoice.INSTANCE.demo();
      tabletVoicePage$fill(var1, var2, var3);
      return Unit.INSTANCE;
   }

   private static final Unit tabletVoicePage$fill$lambda$341(AmyVoice.Pack var0, LinearLayout var1, SettingsActivity var2, String var3) {
      AmyVoice.INSTANCE.use(var0.getId());
      tabletVoicePage$sample();
      tabletVoicePage$fill(var1, var2, var3);
      return Unit.INSTANCE;
   }

   private static final Unit tabletVoicePage$fill$lambda$343(SettingsActivity var0, AmyVoice.Pack var1, LinearLayout var2, String var3) {
      Builder var5 = new Builder((Context)var0);
      String var4 = var1.getLabel();
      var5.setMessage("Delete the voice \"" + var4 + "\"?")
         .setPositiveButton("Delete", new SettingsActivity$$ExternalSyntheticLambda116(var1, var2, var0, var3))
         .setNegativeButton("Cancel", null)
         .show();
      return Unit.INSTANCE;
   }

   private static final void tabletVoicePage$fill$lambda$343$lambda$342(
      AmyVoice.Pack var0, LinearLayout var1, SettingsActivity var2, String var3, DialogInterface var4, int var5
   ) {
      AmyVoice.INSTANCE.stop();
      AmyVoice.INSTANCE.delete(var0.getId());
      tabletVoicePage$fill(var1, var2, var3);
   }

   private static final Unit tabletVoicePage$fill$lambda$348(SettingsActivity var0, LinearLayout var1, String var2) {
      SettingsActivity$$ExternalSyntheticLambda169 var3 = new SettingsActivity$$ExternalSyntheticLambda169(var0, var1, var2);
      var0.pickFile(new String[]{"application/zip", "application/x-zip-compressed", "application/octet-stream", "*/*"}, var3);
      return Unit.INSTANCE;
   }

   private static final Unit tabletVoicePage$fill$lambda$348$lambda$347(SettingsActivity var0, LinearLayout var1, String var2, Uri var3) {
      if (var3 == null) {
         return Unit.INSTANCE;
      } else {
         Toast.makeText((Context)var0, "Installing Amy's voice…", 0).show();
         new Thread(new SettingsActivity$$ExternalSyntheticLambda222(var0, var3, var1, var2)).start();
         return Unit.INSTANCE;
      }
   }

   private static final void tabletVoicePage$fill$lambda$348$lambda$347$lambda$346(SettingsActivity var0, Uri var1, LinearLayout var2, String var3) {
      var0.runOnUiThread(new SettingsActivity$$ExternalSyntheticLambda243(var0, AmyVoice.INSTANCE.install((Context)var0, var1), var2, var3));
   }

   private static final void tabletVoicePage$fill$lambda$348$lambda$347$lambda$346$lambda$345(
      SettingsActivity var0, String var1, LinearLayout var2, String var3
   ) {
      Context var5 = (Context)var0;
      String var4;
      if (var1 == null) {
         var4 = "Amy installed";
      } else {
         var4 = var1;
      }

      Toast.makeText(var5, var4, 1).show();
      if (var1 == null) {
         tabletVoicePage$sampleFull();
      }

      tabletVoicePage$fill(var2, var0, var3);
   }

   private static final Unit tabletVoicePage$fill$lambda$349(SettingsActivity var0) {
      var0.open("tablet:voicestudio");
      return Unit.INSTANCE;
   }

   private static final Unit tabletVoicePage$fill$lambda$350(Voice var0, String var1) {
      AmyVoice.INSTANCE.stop();
      Speech.INSTANCE.preview(var0, var1);
      return Unit.INSTANCE;
   }

   private static final Unit tabletVoicePage$fill$lambda$351(Voice var0, LinearLayout var1, SettingsActivity var2, String var3) {
      Speech.INSTANCE.setVoiceName(var0.getName());
      Speech.speak$default(Speech.INSTANCE, "Change lanes left?", false, 2, null);
      tabletVoicePage$fill(var1, var2, var3);
      return Unit.INSTANCE;
   }

   private static final View tabletVoicePage$gap(SettingsActivity var0) {
      View var2 = new View((Context)var0);
      CarUi var1 = var0.ui;
      CarUi var3 = var1;
      if (var1 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var3 = null;
      }

      var2.setLayoutParams((LayoutParams)(new android.widget.LinearLayout.LayoutParams(var3.dp(8), 1)));
      return var2;
   }

   private static final Unit tabletVoicePage$lambda$322(SettingsActivity var0) {
      Speech.INSTANCE.setEnabled(Speech.INSTANCE.getEnabled() ^ true);
      var0.panels.remove("tablet:voice");
      var0.open("tablet:voice");
      return Unit.INSTANCE;
   }

   private static final String tabletVoicePage$lambda$323(float var0) {
      String var2 = String.format("%.2fx", Arrays.copyOf(new Object[]{var0}, 1));
      Intrinsics.checkNotNullExpressionValue(var2, "format(...)");
      String var1;
      if (Math.abs(var0 - 1.0F) < 0.01F) {
         var1 = " - normal";
      } else if (var0 < 1.0F) {
         var1 = " - slower";
      } else {
         var1 = " - faster";
      }

      return var2 + var1;
   }

   private static final Unit tabletVoicePage$lambda$324(float var0) {
      Speech.INSTANCE.setSpeed(var0);
      return Unit.INSTANCE;
   }

   private static final Unit tabletVoicePage$lambda$325() {
      tabletVoicePage$sample();
      return Unit.INSTANCE;
   }

   private static final String tabletVoicePage$lambda$326(float var0) {
      String var1;
      if (Math.abs(var0) < 0.01F) {
         var1 = "as recorded";
      } else {
         if (var0 > 0.0F) {
            var1 = "+";
         } else {
            var1 = "";
         }

         String var3 = String.format("%.1f semitones", Arrays.copyOf(new Object[]{var0}, 1));
         Intrinsics.checkNotNullExpressionValue(var3, "format(...)");
         String var2;
         if (var0 > 0.0F) {
            var2 = " - higher";
         } else {
            var2 = " - lower";
         }

         var1 = var1 + var3 + var2;
      }

      return var1;
   }

   private static final Unit tabletVoicePage$lambda$327(float var0) {
      Speech.INSTANCE.setPitch(var0);
      return Unit.INSTANCE;
   }

   private static final Unit tabletVoicePage$lambda$328() {
      tabletVoicePage$sample();
      return Unit.INSTANCE;
   }

   private static final String tabletVoicePage$lambda$329(float var0) {
      String var2;
      if (var0 < 0.01F) {
         var2 = "off";
      } else {
         int var1 = Math.round(100 * var0);
         var2 = var1 + "%";
      }

      return var2;
   }

   private static final Unit tabletVoicePage$lambda$330(float var0) {
      Speech.INSTANCE.setChime(var0);
      return Unit.INSTANCE;
   }

   private static final Unit tabletVoicePage$lambda$331() {
      tabletVoicePage$sampleFull();
      return Unit.INSTANCE;
   }

   private static final String tabletVoicePage$lambda$332(float var0) {
      String var2;
      if (var0 < 0.01F) {
         var2 = "off";
      } else {
         int var1 = Math.round(100 * var0);
         var2 = var1 + "%";
      }

      return var2;
   }

   private static final Unit tabletVoicePage$lambda$333(float var0) {
      Speech.INSTANCE.setPad(var0);
      return Unit.INSTANCE;
   }

   private static final Unit tabletVoicePage$lambda$334() {
      tabletVoicePage$sampleFull();
      return Unit.INSTANCE;
   }

   private static final Unit tabletVoicePage$lambda$335(AudioManager var0, int var1, int var2, SettingsActivity var3, TextView var4) {
      tabletVoicePage$setV(var0, var1, var2, var3, var4, var0.getStreamVolume(var1) - 1);
      return Unit.INSTANCE;
   }

   private static final Unit tabletVoicePage$lambda$336(AudioManager var0, int var1, int var2, SettingsActivity var3, TextView var4) {
      tabletVoicePage$setV(var0, var1, var2, var3, var4, var0.getStreamVolume(var1) + 1);
      return Unit.INSTANCE;
   }

   private static final Unit tabletVoicePage$lambda$337(int var0, AudioManager var1, int var2, SettingsActivity var3, TextView var4) {
      tabletVoicePage$setV(var1, var2, var0, var3, var4, var0);
      return Unit.INSTANCE;
   }

   private static final Unit tabletVoicePage$lambda$353(SettingsActivity var0, LinearLayout var1, String var2) {
      var0.runOnUiThread(new SettingsActivity$$ExternalSyntheticLambda6(var1, var0, var2));
      return Unit.INSTANCE;
   }

   private static final void tabletVoicePage$lambda$353$lambda$352(LinearLayout var0, SettingsActivity var1, String var2) {
      tabletVoicePage$fill(var0, var1, var2);
   }

   private static final void tabletVoicePage$lambda$354(LinearLayout var0, SettingsActivity var1, String var2) {
      tabletVoicePage$fill(var0, var1, var2);
   }

   private static final void tabletVoicePage$sample() {
      if (Speech.INSTANCE.getUseAmy() && AmyVoice.INSTANCE.getInstalled()) {
         AmyVoice.INSTANCE.play(AmySynth.Style.SHORT, CollectionsKt.listOf("ask_left"));
      } else {
         Speech.speak$default(Speech.INSTANCE, "Change lanes left?", false, 2, null);
      }
   }

   private static final void tabletVoicePage$sampleFull() {
      if (Speech.INSTANCE.getUseAmy() && AmyVoice.INSTANCE.getInstalled()) {
         AmyVoice.INSTANCE.play(AmySynth.Style.ASK, AmyPrompts.INSTANCE.ask(1, "exit on the right in 0.6 mi - 2 lanes to go"));
      } else {
         Speech.speak$default(Speech.INSTANCE, "Change lanes right?", false, 2, null);
      }
   }

   private static final void tabletVoicePage$setV(AudioManager var0, int var1, int var2, SettingsActivity var3, TextView var4, int var5) {
      try {
         var0.setStreamVolume(var1, RangesKt.coerceIn(var5, 0, var2), 0);
      } catch (Exception var7) {
         Toast.makeText((Context)var3, "Can't change it here (Do Not Disturb?) - use the tablet's volume buttons", 1).show();
      }

      tabletVoicePage$showV(var0, var1, var4, var2);
      tabletVoicePage$sample();
   }

   private static final void tabletVoicePage$showV(AudioManager var0, int var1, TextView var2, int var3) {
      var1 = var0.getStreamVolume(var1);
      if (var2 != null) {
         String var4;
         if (var1 == 0) {
            var4 = " - muted, you won't hear the alerts";
         } else {
            var4 = "";
         }

         var2.setText(var1 + " of " + var3 + var4);
      }
   }

   private final View tabletVoiceStudioPage() {
      Pair var1 = this.page();
      ScrollView var3 = (ScrollView)var1.component1();
      LinearLayout var7 = (LinearLayout)var1.component2();
      SharedPreferences var6 = this.getSharedPreferences("speech", 0);
      ObjectRef var5 = new ObjectRef();
      var5.element = (T)(new VoiceStudio.Style(var6.getFloat("st_expr", 0.667F), var6.getFloat("st_rhythm", 0.8F), 1.08F));
      ObjectRef var4 = new ObjectRef();
      String var2 = "all";
      String var17 = var6.getString("st_filter", "all");
      if (var17 == null) {
         var17 = var2;
      }

      var4.element = (T)var17;
      BooleanRef var9 = new BooleanRef();
      AtomicBoolean var8 = new AtomicBoolean(false);
      CarUi var36 = this.ui;
      CarUi var18 = var36;
      if (var36 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var18 = null;
      }

      View var10 = (View)var18.note(
         "Pick a voice, download it, ▶ Try it, then \"Make my voice\": the tablet speaks all 60 lane-change phrases in it (about a minute) and the card uses it from then on - with your chime, music, speed and pitch. Voices with many speakers have a speaker slider: each number is a different person."
      );
      CarUi var37 = this.ui;
      CarUi var19 = var37;
      if (var37 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var19 = null;
      }

      var7.addView(var10, (LayoutParams)CarUi.lp$default(var19, 0, 0, 3, null));
      CarUi var20 = this.ui;
      if (var20 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var20 = null;
      }

      var1 = CarUi.row$default(var20, "Status", "…", 0, null, null, 28, null);
      LinearLayout var11 = (LinearLayout)var1.component1();
      TextView var51 = (TextView)var1.component2();
      CarUi var22 = this.ui;
      if (var22 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var22 = null;
      }

      TextView var14 = CarUi.button$default(var22, "Stop", false, new SettingsActivity$$ExternalSyntheticLambda212(var8), 2, null);
      var14.setVisibility(8);
      var11.addView((View)var14);
      View var12 = (View)var11;
      CarUi var38 = this.ui;
      CarUi var23 = var38;
      if (var38 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var23 = null;
      }

      var7.addView(var12, (LayoutParams)CarUi.lp$default(var23, 0, 0, 3, null));
      LinearLayout var52 = new LinearLayout((Context)this);
      var52.setOrientation(1);
      View var13 = (View)var52;
      CarUi var39 = this.ui;
      CarUi var24 = var39;
      if (var39 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var24 = null;
      }

      var7.addView(var13, (LayoutParams)CarUi.lp$default(var24, 0, 0, 3, null));
      ObjectRef var53 = new ObjectRef();
      CarUi var40 = this.ui;
      CarUi var25 = var40;
      if (var40 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var25 = null;
      }

      View var15 = (View)var25.header("How it speaks");
      CarUi var41 = this.ui;
      CarUi var26 = var41;
      if (var41 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var26 = null;
      }

      var7.addView(var15, (LayoutParams)CarUi.lp$default(var26, 0, 0, 3, null));
      this.slider(
         var7,
         "Expressive",
         0.1F,
         1.2F,
         0.05F,
         ((VoiceStudio.Style)var5.element).getExpressive(),
         new SettingsActivity$$ExternalSyntheticLambda214(),
         0.667F,
         new SettingsActivity$$ExternalSyntheticLambda215(var5, var6),
         new SettingsActivity$$ExternalSyntheticLambda216()
      );
      this.slider(
         var7,
         "Rhythm variation",
         0.1F,
         1.4F,
         0.05F,
         ((VoiceStudio.Style)var5.element).getRhythm(),
         new SettingsActivity$$ExternalSyntheticLambda217(),
         0.8F,
         new SettingsActivity$$ExternalSyntheticLambda218(var5, var6),
         new SettingsActivity$$ExternalSyntheticLambda219()
      );
      CarUi var42 = this.ui;
      CarUi var27 = var42;
      if (var42 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var27 = null;
      }

      var15 = (View)var27.note(
         "These two are baked in when the voice is made - ▶ Try after changing them. Speed, pitch, chime and music are on the Voice alerts page and change any time."
      );
      CarUi var43 = this.ui;
      CarUi var28 = var43;
      if (var43 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var28 = null;
      }

      var7.addView(var15, (LayoutParams)CarUi.lp$default(var28, 0, 0, 3, null));
      CarUi var44 = this.ui;
      CarUi var29 = var44;
      if (var44 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var29 = null;
      }

      var15 = (View)var29.header("Voices");
      CarUi var45 = this.ui;
      CarUi var30 = var45;
      if (var45 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var30 = null;
      }

      var7.addView(var15, (LayoutParams)CarUi.lp$default(var30, 0, 0, 3, null));
      LinearLayout var16 = new LinearLayout((Context)this);
      var16.setOrientation(1);
      var15 = (View)var16;
      CarUi var46 = this.ui;
      CarUi var31 = var46;
      if (var46 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var31 = null;
      }

      var7.addView(var15, (LayoutParams)CarUi.lp$default(var31, 0, 0, 3, null));
      tabletVoiceStudioPage$fillFilter(var16, this, var4, var6, var53);
      LinearLayout var57 = new LinearLayout((Context)this);
      var57.setOrientation(1);
      View var58 = (View)var57;
      CarUi var47 = this.ui;
      CarUi var32 = var47;
      if (var47 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var32 = null;
      }

      var7.addView(var58, (LayoutParams)CarUi.lp$default(var32, 0, 0, 3, null));
      ObjectRef var50 = new ObjectRef();
      var50.element = (T)CollectionsKt.emptyList();
      var53.element = (T)(new SettingsActivity$$ExternalSyntheticLambda220(
         var57,
         this,
         var50,
         var6,
         var4,
         var8,
         var51,
         var9,
         var14,
         var53,
         var5,
         CollectionsKt.listOf(new String[]{"Exit on the right in half a mile.", "Two lanes to go. Change lanes right?"})
      ));
      View var49 = new View((Context)this);
      CarUi var48 = this.ui;
      CarUi var33 = var48;
      if (var48 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var33 = null;
      }

      var11.addView(var49, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(var33.dp(8), 1)));
      CarUi var34 = this.ui;
      if (var34 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var34 = null;
      }

      var11.addView(
         (View)CarUi.button$default(var34, "Refresh list", false, new SettingsActivity$$ExternalSyntheticLambda221(var50, this, var51, var53), 2, null)
      );
      tabletVoiceStudioPage$fillEngine(var52, this, var51, var53);
      Function0 var35;
      if (var53.element == null) {
         Intrinsics.throwUninitializedPropertyAccessException("fillVoices");
         var35 = null;
      } else {
         var35 = (Function0)var53.element;
      }

      var35.invoke();
      tabletVoiceStudioPage$loadCat(var50, this, var51, var53, false);
      return (View)var3;
   }

   private static final void tabletVoiceStudioPage$fillEngine(LinearLayout var0, SettingsActivity var1, TextView var2, ObjectRef<Function0<Unit>> var3) {
      var0.removeAllViews();
      boolean var4 = VoiceStudio.INSTANCE.engineInstalled((Context)var1);
      CarUi var5 = var1.ui;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var5 = null;
      }

      String var6;
      if (var4) {
         var6 = "Installed (sherpa-onnx, runs on the tablet, offline)";
      } else {
         var6 = "Not installed - tap Install and pick StarView-Voice-engine.zip (about 18 MB, from Drive or Downloads). Needed once.";
      }

      LinearLayout var7 = (LinearLayout)CarUi.row$default(var5, "Voice engine", var6, 0, null, null, 28, null).component1();
      if (!var4) {
         CarUi var12 = var1.ui;
         var5 = var12;
         if (var12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var5 = null;
         }

         var7.addView((View)var5.button("Install…", true, new SettingsActivity$$ExternalSyntheticLambda119(var1, var2, var3, var0)));
      }

      View var10 = (View)var7;
      CarUi var9 = var1.ui;
      CarUi var8 = var9;
      if (var9 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var8 = null;
      }

      var0.addView(var10, (LayoutParams)CarUi.lp$default(var8, 0, 0, 3, null));
   }

   private static final Unit tabletVoiceStudioPage$fillEngine$lambda$363(SettingsActivity var0, TextView var1, ObjectRef var2, LinearLayout var3) {
      SettingsActivity$$ExternalSyntheticLambda22 var4 = new SettingsActivity$$ExternalSyntheticLambda22(var0, var1, var2, var3);
      var0.pickFile(new String[]{"application/zip", "application/x-zip-compressed", "application/octet-stream", "*/*"}, var4);
      return Unit.INSTANCE;
   }

   private static final Unit tabletVoiceStudioPage$fillEngine$lambda$363$lambda$362(
      SettingsActivity var0, TextView var1, ObjectRef var2, LinearLayout var3, Uri var4
   ) {
      if (var4 == null) {
         return Unit.INSTANCE;
      } else {
         tabletVoiceStudioPage$say(var0, var1, "Installing the voice engine…");
         new Thread(new SettingsActivity$$ExternalSyntheticLambda186(var0, var4, var1, var2, var3)).start();
         return Unit.INSTANCE;
      }
   }

   private static final void tabletVoiceStudioPage$fillEngine$lambda$363$lambda$362$lambda$361(
      SettingsActivity var0, Uri var1, TextView var2, ObjectRef var3, LinearLayout var4
   ) {
      String var5 = VoiceStudio.INSTANCE.installEngine((Context)var0, var1, new SettingsActivity$$ExternalSyntheticLambda55(var0, var2));
      if (var5 == null) {
         var5 = "Voice engine installed";
      }

      tabletVoiceStudioPage$say(var0, var2, var5);
      var0.runOnUiThread(new SettingsActivity$$ExternalSyntheticLambda66(var3, var4, var0, var2));
   }

   private static final Unit tabletVoiceStudioPage$fillEngine$lambda$363$lambda$362$lambda$361$lambda$359(SettingsActivity var0, TextView var1, String var2) {
      Intrinsics.checkNotNullParameter(var2, "it");
      tabletVoiceStudioPage$say(var0, var1, var2);
      return Unit.INSTANCE;
   }

   private static final void tabletVoiceStudioPage$fillEngine$lambda$363$lambda$362$lambda$361$lambda$360(
      ObjectRef var0, LinearLayout var1, SettingsActivity var2, TextView var3
   ) {
      tabletVoiceStudioPage$fillEngine(var1, var2, var3, var0);
      Function0 var4;
      if (var0.element == null) {
         Intrinsics.throwUninitializedPropertyAccessException("fillVoices");
         var4 = null;
      } else {
         var4 = (Function0)var0.element;
      }

      var4.invoke();
   }

   private static final void tabletVoiceStudioPage$fillFilter(
      LinearLayout var0, SettingsActivity var1, ObjectRef<String> var2, SharedPreferences var3, ObjectRef<Function0<Unit>> var4
   ) {
      var0.removeAllViews();
      CarUi var5 = var1.ui;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var5 = null;
      }

      LinearLayout var8 = (LinearLayout)CarUi.row$default(var5, "Show", "Filter the list", 0, null, null, 28, null).component1();

      for (Pair var12 : CollectionsKt.listOf(
         new Pair[]{
            TuplesKt.to("all", "All"),
            TuplesKt.to("US", "American"),
            TuplesKt.to("GB", "British"),
            TuplesKt.to("many", "Many speakers"),
            TuplesKt.to("have", "Downloaded")
         }
      )) {
         String var6 = (String)var12.component1();
         String var9 = (String)var12.component2();
         var5 = var1.ui;
         if (var5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var5 = null;
         }

         var8.addView(
            (View)var5.button(
               var9, Intrinsics.areEqual(var2.element, var6), new SettingsActivity$$ExternalSyntheticLambda241(var2, var6, var3, var4, var0, var1)
            )
         );
         View var16 = new View((Context)var1);
         CarUi var15 = var1.ui;
         var5 = var15;
         if (var15 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var5 = null;
         }

         var8.addView(var16, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(var5.dp(6), 1)));
      }

      View var11 = (View)var8;
      CarUi var10 = var1.ui;
      if (var10 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var10 = null;
      }

      var0.addView(var11, (LayoutParams)var10.lp(0, 0));
   }

   private static final Unit tabletVoiceStudioPage$fillFilter$lambda$371(
      ObjectRef var0, String var1, SharedPreferences var2, ObjectRef var3, LinearLayout var4, SettingsActivity var5
   ) {
      var0.element = (T)var1;
      var2.edit().putString("st_filter", var1).apply();
      tabletVoiceStudioPage$fillFilter(var4, var5, var0, var2, var3);
      Function0 var6;
      if (var3.element == null) {
         Intrinsics.throwUninitializedPropertyAccessException("fillVoices");
         var6 = null;
      } else {
         var6 = (Function0)var3.element;
      }

      var6.invoke();
      return Unit.INSTANCE;
   }

   private static final View tabletVoiceStudioPage$gap$374(SettingsActivity var0) {
      View var2 = new View((Context)var0);
      CarUi var1 = var0.ui;
      CarUi var3 = var1;
      if (var1 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var3 = null;
      }

      var2.setLayoutParams((LayoutParams)(new android.widget.LinearLayout.LayoutParams(var3.dp(8), 1)));
      return var2;
   }

   private static final void tabletVoiceStudioPage$job(
      BooleanRef var0,
      SettingsActivity var1,
      AtomicBoolean var2,
      TextView var3,
      TextView var4,
      ObjectRef<Function0<Unit>> var5,
      String var6,
      Function0<String> var7
   ) {
      if (var0.element) {
         Toast.makeText((Context)var1, "Wait for the current job (or Stop it)", 0).show();
      } else {
         var0.element = true;
         var2.set(false);
         var3.setVisibility(0);
         tabletVoiceStudioPage$say(var1, var4, var6);
         new Thread(new SettingsActivity$$ExternalSyntheticLambda4(var7, var0, var1, var3, var4, var5)).start();
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private static final void tabletVoiceStudioPage$job$lambda$377(
      Function0 var0, BooleanRef var1, SettingsActivity var2, TextView var3, TextView var4, ObjectRef var5
   ) {
      label24:
      try {
         var10 = (String)var0.invoke();
      } catch (Throwable var9) {
         String var6 = var9.getMessage();
         var10 = var6;
         if (var6 == null) {
            var10 = var9.toString();
         }
         break label24;
      }

      var1.element = false;
      var2.runOnUiThread(new SettingsActivity$$ExternalSyntheticLambda3(var3, var10, var4, var5));
   }

   private static final void tabletVoiceStudioPage$job$lambda$377$lambda$376(TextView var0, String var1, TextView var2, ObjectRef var3) {
      var0.setVisibility(8);
      if (var1 != null && var2 != null) {
         var2.setText(var1);
      }

      Function0 var4;
      if (var3.element == null) {
         Intrinsics.throwUninitializedPropertyAccessException("fillVoices");
         var4 = null;
      } else {
         var4 = (Function0)var3.element;
      }

      var4.invoke();
   }

   private static final Unit tabletVoiceStudioPage$lambda$355(AtomicBoolean var0) {
      var0.set(true);
      return Unit.INSTANCE;
   }

   private static final String tabletVoiceStudioPage$lambda$364(float var0) {
      String var2 = String.format("%.2f", Arrays.copyOf(new Object[]{var0}, 1));
      Intrinsics.checkNotNullExpressionValue(var2, "format(...)");
      String var1;
      if (var0 < 0.45F) {
         var1 = " - flat, calm";
      } else if (var0 > 0.85F) {
         var1 = " - lively";
      } else {
         var1 = "";
      }

      return var2 + var1;
   }

   private static final Unit tabletVoiceStudioPage$lambda$365(ObjectRef var0, SharedPreferences var1, float var2) {
      var0.element = (T)VoiceStudio.Style.copy$default((VoiceStudio.Style)var0.element, var2, 0.0F, 0.0F, 6, null);
      var1.edit().putFloat("st_expr", var2).apply();
      return Unit.INSTANCE;
   }

   private static final Unit tabletVoiceStudioPage$lambda$366() {
      return Unit.INSTANCE;
   }

   private static final String tabletVoiceStudioPage$lambda$367(float var0) {
      String var2 = String.format("%.2f", Arrays.copyOf(new Object[]{var0}, 1));
      Intrinsics.checkNotNullExpressionValue(var2, "format(...)");
      String var1;
      if (var0 < 0.45F) {
         var1 = " - even, steady";
      } else if (var0 > 1.0F) {
         var1 = " - loose";
      } else {
         var1 = "";
      }

      return var2 + var1;
   }

   private static final Unit tabletVoiceStudioPage$lambda$368(ObjectRef var0, SharedPreferences var1, float var2) {
      var0.element = (T)VoiceStudio.Style.copy$default((VoiceStudio.Style)var0.element, 0.0F, var2, 0.0F, 5, null);
      var1.edit().putFloat("st_rhythm", var2).apply();
      return Unit.INSTANCE;
   }

   private static final Unit tabletVoiceStudioPage$lambda$369() {
      return Unit.INSTANCE;
   }

   private static final Unit tabletVoiceStudioPage$lambda$397(
      LinearLayout var0,
      SettingsActivity var1,
      ObjectRef var2,
      SharedPreferences var3,
      ObjectRef var4,
      AtomicBoolean var5,
      TextView var6,
      BooleanRef var7,
      TextView var8,
      ObjectRef var9,
      ObjectRef var10,
      List var11
   ) {
      var0.removeAllViews();
      if (!VoiceStudio.INSTANCE.engineInstalled((Context)var1)) {
         CarUi var43 = var1.ui;
         CarUi var34 = var43;
         if (var43 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var34 = null;
         }

         View var44 = (View)var34.note("Install the voice engine first (above).");
         CarUi var35 = var1.ui;
         CarUi var25 = var35;
         if (var35 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var25 = null;
         }

         var0.addView(var44, (LayoutParams)CarUi.lp$default(var25, 0, 0, 3, null));
         return Unit.INSTANCE;
      } else if (((List)var2.element).isEmpty()) {
         CarUi var41 = var1.ui;
         CarUi var32 = var41;
         if (var41 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var32 = null;
         }

         View var42 = (View)var32.note("Loading the voice list from Hugging Face…");
         CarUi var33 = var1.ui;
         CarUi var24 = var33;
         if (var33 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            var24 = null;
         }

         var0.addView(var42, (LayoutParams)CarUi.lp$default(var24, 0, 0, 3, null));
         return Unit.INSTANCE;
      } else {
         Iterable var16 = (Iterable)var2.element;
         var var26 = new ArrayList();
         Iterator var17 = var16.iterator();

         while (true) {
            boolean var15 = var17.hasNext();
            byte var12 = 1;
            boolean var14 = true;
            if (!var15) {
               List var27 = var26;

               for (VoiceStudio.Voice var45 : var27) {
                  var14 = VoiceStudio.INSTANCE.downloaded((Context)var1, var45.getKey());
                  LinearLayout var20 = new LinearLayout((Context)var1);
                  var20.setOrientation(var12);
                  CarUi var56 = var1.ui;
                  if (var56 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("ui");
                     var56 = null;
                  }

                  String var75 = var45.getTitle();
                  String var82 = var45.getAccent();
                  String var21 = var45.getQuality();
                  var21 = var75 + " · " + var82 + " · " + var21;
                  int var13 = var45.getSpeakers();
                  var82 = "";
                  String var76;
                  if (var13 > var12) {
                     var13 = var45.getSpeakers();
                     var76 = var13 + " speakers · ";
                  } else {
                     var76 = "";
                  }

                  String var22 = tabletVoiceStudioPage$mb$375(var45.getBytes());
                  if (var14) {
                     var82 = " · downloaded";
                  }

                  Pair var57 = CarUi.row$default(var56, var21, var76 + var22 + var82, 0, null, null, 28, null);
                  LinearLayout var77 = (LinearLayout)var57.component1();
                  TextView var58 = (TextView)var57.component2();
                  IntRef var84 = new IntRef();
                  String var59 = var45.getKey();
                  var84.element = RangesKt.coerceIn(var3.getInt("st_sid_" + var59, 0), 0, Math.max(0, var45.getSpeakers() - var12));
                  if (!var14) {
                     CarUi var60 = var1.ui;
                     if (var60 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("ui");
                        var60 = null;
                     }

                     var21 = tabletVoiceStudioPage$mb$375(var45.getBytes());
                     var77.addView(
                        (View)CarUi.button$default(
                           var60,
                           "Download " + var21,
                           false,
                           new SettingsActivity$$ExternalSyntheticLambda150(var45, var1, var5, var6, var7, var8, var9),
                           2,
                           null
                        )
                     );
                  } else {
                     CarUi var61 = var1.ui;
                     if (var61 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("ui");
                        var61 = null;
                     }

                     var77.addView(
                        (View)CarUi.button$default(
                           var61,
                           "▶ Try",
                           false,
                           new SettingsActivity$$ExternalSyntheticLambda151(var45, var84, var1, var10, var11, var6, var7, var5, var8, var9),
                           2,
                           null
                        )
                     );
                     var77.addView(tabletVoiceStudioPage$gap$374(var1));
                     CarUi var62 = var1.ui;
                     if (var62 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("ui");
                        var62 = null;
                     }

                     var77.addView(
                        (View)var62.button(
                           "Make my voice",
                           (boolean)var12,
                           new SettingsActivity$$ExternalSyntheticLambda152(var45, var84, var1, var10, var5, var6, var7, var8, var9)
                        )
                     );
                     var77.addView(tabletVoiceStudioPage$gap$374(var1));
                     CarUi var63 = var1.ui;
                     if (var63 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("ui");
                        var63 = null;
                     }

                     var77.addView(
                        (View)CarUi.button$default(var63, "Delete", false, new SettingsActivity$$ExternalSyntheticLambda153(var1, var45, var9, var6), 2, null)
                     );
                  }

                  View var90 = (View)var77;
                  CarUi var78 = var1.ui;
                  CarUi var64 = var78;
                  if (var78 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("ui");
                     var64 = null;
                  }

                  var20.addView(var90, (LayoutParams)var64.lp(0, 0));
                  if (var14 && var45.getSpeakers() > var12) {
                     CarUi var65 = var1.ui;
                     if (var65 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("ui");
                        var65 = null;
                     }

                     Pair var66 = CarUi.row$default(var65, "Speaker", tabletVoiceStudioPage$lambda$397$who(var45, var84.element), 1, null, null, 24, null);
                     LinearLayout var91 = (LinearLayout)var66.component1();
                     TextView var92 = (TextView)var66.component2();
                     SeekBar var79 = new SeekBar((Context)var1);
                     var79.setMax(var45.getSpeakers() - var12);
                     var79.setProgress(var84.element);
                     var79.setThumbTintList(ColorStateList.valueOf(-1));
                     var79.setProgressTintList(ColorStateList.valueOf(CarUi.Companion.getACCENT()));
                     var79.setOnSeekBarChangeListener(new OnSeekBarChangeListener(var92, var45, var84, var79, var3) {
                        final TextView $d2;
                        final IntRef $sid;
                        final SharedPreferences $sp;
                        final SeekBar $this_apply;
                        final VoiceStudio.Voice $v;

                        {
                           this.$d2 = var1;
                           this.$v = var2x;
                           this.$sid = var3x;
                           this.$this_apply = var4x;
                           this.$sp = var5x;
                        }

                        public void onProgressChanged(SeekBar var1, int var2x, boolean var3x) {
                           TextView var4x = this.$d2;
                           if (var4x != null) {
                              var4x.setText(SettingsActivity.tabletVoiceStudioPage$lambda$397$who(this.$v, var2x));
                           }
                        }

                        public void onStartTrackingTouch(SeekBar var1) {
                        }

                        public void onStopTrackingTouch(SeekBar var1) {
                           this.$sid.element = this.$this_apply.getProgress();
                           Editor var3x = this.$sp.edit();
                           String var2x = this.$v.getKey();
                           var3x.putInt("st_sid_" + var2x, this.$sid.element).apply();
                        }
                     });
                     CarUi var67 = var1.ui;
                     if (var67 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("ui");
                        var67 = null;
                     }

                     var91.addView(
                        (View)CarUi.button$default(
                           var67, "◀", false, new SettingsActivity$$ExternalSyntheticLambda154(var84, var79, var92, var3, var45), 2, null
                        )
                     );
                     var91.addView(tabletVoiceStudioPage$gap$374(var1));
                     CarUi var68 = var1.ui;
                     if (var68 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("ui");
                        var68 = null;
                     }

                     var91.addView(
                        (View)CarUi.button$default(
                           var68, "▶", false, new SettingsActivity$$ExternalSyntheticLambda156(var84, var45, var79, var92, var3), 2, null
                        )
                     );
                     var91.addView(tabletVoiceStudioPage$gap$374(var1));
                     CarUi var69 = var1.ui;
                     if (var69 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("ui");
                        var69 = null;
                     }

                     var91.addView(
                        (View)CarUi.button$default(
                           var69, "\ud83c\udfb2", false, new SettingsActivity$$ExternalSyntheticLambda157(var84, var45, var79, var92, var3), 2, null
                        )
                     );
                     View var85 = (View)var91;
                     CarUi var70 = var1.ui;
                     CarUi var46 = var70;
                     if (var70 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("ui");
                        var46 = null;
                     }

                     var20.addView(var85, (LayoutParams)var46.lp(var12, 0));
                     View var80 = (View)var79;
                     CarUi var71 = var1.ui;
                     CarUi var47 = var71;
                     if (var71 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("ui");
                        var47 = null;
                     }

                     android.widget.LinearLayout.LayoutParams var86 = new android.widget.LinearLayout.LayoutParams(-1, var47.dp(44));
                     CarUi var72 = var1.ui;
                     CarUi var48 = var72;
                     if (var72 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("ui");
                        var48 = null;
                     }

                     var86.leftMargin = var48.dp(28);
                     CarUi var73 = var1.ui;
                     CarUi var49 = var73;
                     if (var73 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("ui");
                        var49 = null;
                     }

                     var86.rightMargin = var49.dp(12);
                     Unit var50 = Unit.INSTANCE;
                     var20.addView(var80, (LayoutParams)var86);
                  }

                  View var81 = (View)var20;
                  CarUi var74 = var1.ui;
                  CarUi var51 = var74;
                  if (var74 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("ui");
                     var51 = null;
                  }

                  var0.addView(var81, (LayoutParams)CarUi.lp$default(var51, 0, 0, 3, null));
               }

               if (var27.isEmpty()) {
                  CarUi var36 = var1.ui;
                  CarUi var28 = var36;
                  if (var36 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("ui");
                     var28 = null;
                  }

                  View var52 = (View)var28.note("Nothing here yet.");
                  CarUi var37 = var1.ui;
                  CarUi var29 = var37;
                  if (var37 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("ui");
                     var29 = null;
                  }

                  var0.addView(var52, (LayoutParams)CarUi.lp$default(var29, 0, 0, 3, null));
               }

               CarUi var38 = var1.ui;
               CarUi var30 = var38;
               if (var38 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("ui");
                  var30 = null;
               }

               String var39 = tabletVoiceStudioPage$mb$375(VoiceStudio.INSTANCE.downloadedBytes((Context)var1));
               View var40 = (View)var30.note(
                  "Downloaded voices use " + var39 + " on the tablet. Voices are Piper voices from rhasspy/piper-voices on Hugging Face."
               );
               CarUi var31 = var1.ui;
               CarUi var23 = var31;
               if (var31 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("ui");
                  var23 = null;
               }

               var0.addView(var40, (LayoutParams)CarUi.lp$default(var23, 0, 0, 3, null));
               return Unit.INSTANCE;
            }

            label167: {
               var55 = var17.next();
               VoiceStudio.Voice var19 = (VoiceStudio.Voice)var55;
               String var18 = (String)var4.element;
               switch (var18.hashCode()) {
                  case 2267:
                     if (!var18.equals("GB")) {
                        break label167;
                     }
                     break;
                  case 2718:
                     if (!var18.equals("US")) {
                        break label167;
                     }
                     break;
                  case 3195240:
                     if (var18.equals("have")) {
                        var14 = VoiceStudio.INSTANCE.downloaded((Context)var1, var19.getKey());
                     }
                     break label167;
                  case 3343967:
                     if (var18.equals("many") && var19.getSpeakers() <= 1) {
                        var14 = false;
                     }
                  default:
                     break label167;
               }

               var14 = Intrinsics.areEqual(var19.getRegion(), var4.element);
            }

            if (var14) {
               var26.add(var55);
            }
         }
      }
   }

   private static final Unit tabletVoiceStudioPage$lambda$397$lambda$383(
      VoiceStudio.Voice var0, SettingsActivity var1, AtomicBoolean var2, TextView var3, BooleanRef var4, TextView var5, ObjectRef var6
   ) {
      String var7 = var0.getTitle();
      tabletVoiceStudioPage$job(
         var4, var1, var2, var5, var3, var6, "Downloading " + var7 + "…", new SettingsActivity$$ExternalSyntheticLambda53(var1, var0, var2, var3)
      );
      return Unit.INSTANCE;
   }

   private static final String tabletVoiceStudioPage$lambda$397$lambda$383$lambda$382(
      SettingsActivity var0, VoiceStudio.Voice var1, AtomicBoolean var2, TextView var3
   ) {
      String var4 = VoiceStudio.INSTANCE.download((Context)var0, var1, var2, new SettingsActivity$$ExternalSyntheticLambda98(var1, var0, var3));
      String var6 = var4;
      if (var4 == null) {
         String var5 = var1.getTitle();
         tabletVoiceStudioPage$say(var0, var3, var5 + " downloaded - ▶ Try it");
         var6 = null;
      }

      return var6;
   }

   private static final Unit tabletVoiceStudioPage$lambda$397$lambda$383$lambda$382$lambda$380(
      VoiceStudio.Voice var0, SettingsActivity var1, TextView var2, long var3, long var5
   ) {
      String var9 = var0.getTitle();
      String var8 = tabletVoiceStudioPage$mb$375(var3);
      String var7 = tabletVoiceStudioPage$mb$375(var5);
      tabletVoiceStudioPage$say(var1, var2, "Downloading " + var9 + "… " + var8 + " of " + var7);
      return Unit.INSTANCE;
   }

   private static final Unit tabletVoiceStudioPage$lambda$397$lambda$385(
      VoiceStudio.Voice var0,
      IntRef var1,
      SettingsActivity var2,
      ObjectRef var3,
      List var4,
      TextView var5,
      BooleanRef var6,
      AtomicBoolean var7,
      TextView var8,
      ObjectRef var9
   ) {
      String var12 = var0.getTitle();
      String var11;
      if (var0.getSpeakers() > 1) {
         int var10 = var1.element;
         var11 = " #" + var10;
      } else {
         var11 = "";
      }

      tabletVoiceStudioPage$job(
         var6,
         var2,
         var7,
         var8,
         var5,
         var9,
         var12 + var11 + ": speaking…",
         new SettingsActivity$$ExternalSyntheticLambda130(var2, var0, var1, var3, var4, var5)
      );
      return Unit.INSTANCE;
   }

   private static final String tabletVoiceStudioPage$lambda$397$lambda$385$lambda$384(
      SettingsActivity var0, VoiceStudio.Voice var1, IntRef var2, ObjectRef var3, List var4, TextView var5
   ) {
      List var8 = VoiceStudio.INSTANCE.say((Context)var0, var1.getKey(), var2.element, (VoiceStudio.Style)var3.element, var4);
      AmyVoice.INSTANCE.playClips(AmySynth.Style.ASK, var8);
      String var9 = var1.getTitle();
      String var7;
      if (var1.getSpeakers() > 1) {
         int var6 = var2.element;
         var7 = " speaker " + var6;
      } else {
         var7 = "";
      }

      tabletVoiceStudioPage$say(var0, var5, var9 + var7 + " - like it? Make my voice");
      return null;
   }

   private static final Unit tabletVoiceStudioPage$lambda$397$lambda$389(
      VoiceStudio.Voice var0,
      IntRef var1,
      SettingsActivity var2,
      ObjectRef var3,
      AtomicBoolean var4,
      TextView var5,
      BooleanRef var6,
      TextView var7,
      ObjectRef var8
   ) {
      String var11 = var0.getTitle();
      String var10;
      if (var0.getSpeakers() > 1) {
         int var9 = var1.element;
         var10 = " #" + var9;
      } else {
         var10 = "";
      }

      String var12 = var0.getAccent();
      var10 = var11 + var10 + " · " + var12;
      tabletVoiceStudioPage$job(
         var6,
         var2,
         var4,
         var7,
         var5,
         var8,
         "Making \"" + var10 + "\"…",
         new SettingsActivity$$ExternalSyntheticLambda75(var2, var0, var1, var3, var10, var4, var5)
      );
      return Unit.INSTANCE;
   }

   private static final String tabletVoiceStudioPage$lambda$397$lambda$389$lambda$388(
      SettingsActivity var0, VoiceStudio.Voice var1, IntRef var2, ObjectRef var3, String var4, AtomicBoolean var5, TextView var6
   ) {
      String var8 = VoiceStudio.INSTANCE
         .make(
            (Context)var0, var1, var2.element, (VoiceStudio.Style)var3.element, var4, var5, new SettingsActivity$$ExternalSyntheticLambda86(var4, var0, var6)
         );
      String var7 = var8;
      if (var8 == null) {
         tabletVoiceStudioPage$say(var0, var6, "\"" + var4 + "\" is now the lane-change voice");
         AmyVoice.INSTANCE.demo();
         var7 = null;
      }

      return var7;
   }

   private static final Unit tabletVoiceStudioPage$lambda$397$lambda$389$lambda$388$lambda$386(
      String var0, SettingsActivity var1, TextView var2, int var3, int var4
   ) {
      tabletVoiceStudioPage$say(var1, var2, "Making \"" + var0 + "\"… " + var3 + " of " + var4 + " phrases");
      return Unit.INSTANCE;
   }

   private static final Unit tabletVoiceStudioPage$lambda$397$lambda$390(SettingsActivity var0, VoiceStudio.Voice var1, ObjectRef var2, TextView var3) {
      VoiceStudio.INSTANCE.freeEngine();
      VoiceStudio.INSTANCE.deleteModel((Context)var0, var1.getKey());
      String var5 = var1.getTitle();
      tabletVoiceStudioPage$say(var0, var3, var5 + ": download deleted (voices you made from it stay)");
      Function0 var4;
      if (var2.element == null) {
         Intrinsics.throwUninitializedPropertyAccessException("fillVoices");
         var4 = null;
      } else {
         var4 = (Function0)var2.element;
      }

      var4.invoke();
      return Unit.INSTANCE;
   }

   private static final Unit tabletVoiceStudioPage$lambda$397$lambda$393(
      IntRef var0, SeekBar var1, TextView var2, SharedPreferences var3, VoiceStudio.Voice var4
   ) {
      if (var0.element > 0) {
         var0.element--;
         var1.setProgress(var0.element);
         if (var2 != null) {
            var2.setText(tabletVoiceStudioPage$lambda$397$who(var4, var0.element));
         }

         Editor var5 = var3.edit();
         String var6 = var4.getKey();
         var5.putInt("st_sid_" + var6, var0.element).apply();
      }

      return Unit.INSTANCE;
   }

   private static final Unit tabletVoiceStudioPage$lambda$397$lambda$394(
      IntRef var0, VoiceStudio.Voice var1, SeekBar var2, TextView var3, SharedPreferences var4
   ) {
      if (var0.element < var1.getSpeakers() - 1) {
         var0.element++;
         var2.setProgress(var0.element);
         if (var3 != null) {
            var3.setText(tabletVoiceStudioPage$lambda$397$who(var1, var0.element));
         }

         Editor var6 = var4.edit();
         String var5 = var1.getKey();
         var6.putInt("st_sid_" + var5, var0.element).apply();
      }

      return Unit.INSTANCE;
   }

   private static final Unit tabletVoiceStudioPage$lambda$397$lambda$395(
      IntRef var0, VoiceStudio.Voice var1, SeekBar var2, TextView var3, SharedPreferences var4
   ) {
      var0.element = RangesKt.random(RangesKt.until(0, var1.getSpeakers()), Random.Default);
      var2.setProgress(var0.element);
      if (var3 != null) {
         var3.setText(tabletVoiceStudioPage$lambda$397$who(var1, var0.element));
      }

      Editor var6 = var4.edit();
      String var5 = var1.getKey();
      var6.putInt("st_sid_" + var5, var0.element).apply();
      return Unit.INSTANCE;
   }

   private static final String tabletVoiceStudioPage$lambda$397$who(VoiceStudio.Voice var0, int var1) {
      int var2 = var0.getSpeakers();
      String var4 = CollectionsKt.getOrNull(var0.getSpeakerNames(), var1);
      if (var4 != null) {
         String var3 = " (" + var4 + ")";
         if (var3 != null) {
            return "Speaker " + var1 + " of " + var2 + var3;
         }
      }

      String var5 = "";
      return "Speaker " + var1 + " of " + var2 + var5;
   }

   private static final Unit tabletVoiceStudioPage$lambda$400(ObjectRef var0, SettingsActivity var1, TextView var2, ObjectRef var3) {
      tabletVoiceStudioPage$loadCat(var0, var1, var2, var3, true);
      return Unit.INSTANCE;
   }

   private static final void tabletVoiceStudioPage$loadCat(
      ObjectRef<List<VoiceStudio.Voice>> var0, SettingsActivity var1, TextView var2, ObjectRef<Function0<Unit>> var3, boolean var4
   ) {
      new Thread(new SettingsActivity$$ExternalSyntheticLambda107(var0, var1, var4, var2, var3)).start();
   }

   private static final void tabletVoiceStudioPage$loadCat$lambda$399(ObjectRef var0, SettingsActivity var1, boolean var2, TextView var3, ObjectRef var4) {
      try {
         var0.element = (T)VoiceStudio.INSTANCE.catalog((Context)var1, var2);
         int var5 = ((List)var0.element).size();
         StringBuilder var8 = new StringBuilder();
         tabletVoiceStudioPage$say(var1, var3, var8.append(var5).append(" English voices").toString());
      } catch (Exception var6) {
         String var7 = var6.getMessage();
         tabletVoiceStudioPage$say(var1, var3, "Couldn't get the voice list (" + var7 + ") - is the tablet online?");
      }

      var1.runOnUiThread(new SettingsActivity$$ExternalSyntheticLambda15(var4));
   }

   private static final void tabletVoiceStudioPage$loadCat$lambda$399$lambda$398(ObjectRef var0) {
      Function0 var1;
      if (var0.element == null) {
         Intrinsics.throwUninitializedPropertyAccessException("fillVoices");
         var1 = null;
      } else {
         var1 = (Function0)var0.element;
      }

      var1.invoke();
   }

   private static final String tabletVoiceStudioPage$mb$375(long var0) {
      var0 = Math.round(var0 / 1048576.0);
      return var0 + " MB";
   }

   private static final void tabletVoiceStudioPage$say(SettingsActivity var0, TextView var1, String var2) {
      var0.runOnUiThread(new SettingsActivity$$ExternalSyntheticLambda0(var1, var2));
   }

   private static final void tabletVoiceStudioPage$say$lambda$357(TextView var0, String var1) {
      if (var0 != null) {
         var0.setText(var1);
      }
   }

   private final View techStreamPage() {
      String var2 = Link.INSTANCE.getHost();
      if (var2 == null) {
         View var5 = this.offline("Not connected to the comma. Connect first, then come back.");
         this.handler.post(new SettingsActivity$$ExternalSyntheticLambda239(this));
         return var5;
      } else {
         FrameLayout var3 = new FrameLayout((Context)this);
         var3.setBackgroundColor(Color.rgb(16, 16, 16));
         WebView var1 = new WebView((Context)this);
         var1.getSettings().setJavaScriptEnabled(true);
         var1.getSettings().setDomStorageEnabled(true);
         var1.getSettings().setSupportZoom(false);
         var1.setBackgroundColor(Color.rgb(16, 16, 16));
         var1.setWebViewClient(
            new WebViewClient() {
               public void onReceivedError(WebView var1, WebResourceRequest var2x, WebResourceError var3x) {
                  Intrinsics.checkNotNullParameter(var1, "v");
                  Intrinsics.checkNotNullParameter(var2x, "r");
                  Intrinsics.checkNotNullParameter(var3x, "e");
                  if (var2x.isForMainFrame()) {
                     String var4 = TextUtils.htmlEncode(var3x.getDescription().toString());
                     var1.loadData(
                        "<body style='background:#101010;color:#aaa;font:18px sans-serif;padding:40px'>Couldn't open TechStream on the comma ("
                           + var4
                           + "). Update StarPilot on the comma to the version with TechStream, then come back.</body>",
                        "text/html",
                        "utf-8"
                     );
                  }
               }
            }
         );
         var1.setWebChromeClient(new WebChromeClient());
         Context var4 = this.getApplicationContext();
         Intrinsics.checkNotNullExpressionValue(var4, "getApplicationContext(...)");
         var1.addJavascriptInterface(new TermWeb.Bridge(var4, var1), "StarView");
         var3.addView((View)var1, (LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -1)));
         techStreamPage$load(this, var2, var1);
         var3.setTag(new SettingsActivity$$ExternalSyntheticLambda240(var1, this, var2));
         return (View)var3;
      }
   }

   private static final void techStreamPage$lambda$402$lambda$401(SettingsActivity var0) {
      var0.panels.remove("tablet:techstream");
   }

   private static final void techStreamPage$lambda$407(WebView var0, SettingsActivity var1, String var2) {
      if (var0.getUrl() != null) {
         String var3 = var0.getUrl();
         Intrinsics.checkNotNull(var3);
         if (!StringsKt.startsWith$default(var3, "data:", false, 2, null)) {
            return;
         }
      }

      techStreamPage$load(var1, var2, var0);
   }

   private static final void techStreamPage$load(SettingsActivity var0, String var1, WebView var2) {
      new Thread(new SettingsActivity$$ExternalSyntheticLambda114(var0, var1, var2)).start();
   }

   private static final void techStreamPage$load$lambda$406(SettingsActivity var0, String var1, WebView var2) {
      Link.INSTANCE.loadKey((Context)var0);
      Link.ensureSession$default(Link.INSTANCE, var1, 0, 2, null);
      var0.runOnUiThread(new SettingsActivity$$ExternalSyntheticLambda83(var2, var1, Link.sessionFor$default(Link.INSTANCE, var1, 0, 2, null)));
   }

   private static final void techStreamPage$load$lambda$406$lambda$405(WebView var0, String var1, String var2) {
      boolean var3;
      if (var2.length() > 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (var3) {
         var2 = URLEncoder.encode(var2, "UTF-8");
         var2 = "?s=" + var2;
      } else {
         var2 = "";
      }

      var0.loadUrl("http://" + var1 + ":8090/techstream/" + var2);
   }

   private final void toast(String var1) {
      Toast.makeText((Context)this, var1, 0).show();
   }

   private final void toggleCard(LinearLayout var1, String var2, String var3, Function0<Boolean> var4, Function1<? super Boolean, Unit> var5) {
      CarUi var6 = this.ui;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var6 = null;
      }

      LinearLayout var13 = (LinearLayout)CarUi.row$default(var6, var2, var3, 0, null, null, 28, null).component1();
      CarUi var10 = this.ui;
      CarUi var7 = var10;
      if (var10 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var7 = null;
      }

      Switch var8 = var7.bigSwitch();
      var8.setChecked((Boolean)var4.invoke());
      var8.setOnCheckedChangeListener(new SettingsActivity$$ExternalSyntheticLambda110(var5));
      var13.addView((View)var8);
      View var12 = (View)var13;
      CarUi var11 = this.ui;
      CarUi var9 = var11;
      if (var11 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var9 = null;
      }

      var1.addView(var12, (LayoutParams)CarUi.lp$default(var9, 0, 0, 3, null));
   }

   private static final void toggleCard$lambda$153(Function1 var0, CompoundButton var1, boolean var2) {
      var0.invoke(var2);
   }

   private final View togglesPage() {
      GalaxyApi var6 = this.api;
      if (var6 == null) {
         return this.offline("Not connected to the comma. Connect first, then come back.");
      } else {
         Iterable var5 = var6.getCatalog();
         var var4 = new ArrayList();

         for (Object var7 : var5) {
            if (!((GalaxyApi.Category)var7).getParams().isEmpty()) {
               var4.add(var7);
            }
         }

         List var16 = var4;
         var5 = var16;
         var4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var5, 10));
         Iterator var36 = var5.iterator();

         while (var36.hasNext()) {
            var4.add(((GalaxyApi.Category)var36.next()).getName());
         }

         List var49 = var4;
         if (var49.isEmpty()) {
            View var33 = this.offline("Loading the settings catalog from the comma…");
            this.handler.postDelayed(new SettingsActivity$$ExternalSyntheticLambda7(this, var33), 1500L);
            return var33;
         } else {
            LinearLayout var8 = new LinearLayout((Context)this);
            var8.setOrientation(1);
            EditText var9 = new EditText((Context)this);
            var9.setHint("Search settings");
            var9.setTextSize(19.0F);
            var9.setSingleLine();
            var9.setTextColor(CarUi.Companion.getTEXT());
            var9.setHintTextColor(CarUi.Companion.getDIM());
            var9.setBackground(null);
            CarUi var37 = this.ui;
            CarUi var18 = var37;
            if (var37 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("ui");
               var18 = null;
            }

            int var2 = var18.dp(20);
            CarUi var38 = this.ui;
            CarUi var19 = var38;
            if (var38 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("ui");
               var19 = null;
            }

            int var3 = var19.dp(12);
            CarUi var39 = this.ui;
            CarUi var20 = var39;
            if (var39 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("ui");
               var20 = null;
            }

            int var1 = var20.dp(8);
            CarUi var40 = this.ui;
            CarUi var21 = var40;
            if (var40 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("ui");
               var21 = null;
            }

            var9.setPadding(var2, var3, var1, var21.dp(12));
            var9.setImeOptions(3);
            TextView var10 = new TextView((Context)this);
            var10.setText("✕");
            var10.setTextSize(17.0F);
            var10.setTextColor(CarUi.Companion.getBG());
            var10.setGravity(17);
            var10.setTypeface(Typeface.DEFAULT_BOLD);
            CarUi var22 = this.ui;
            if (var22 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("ui");
               var22 = null;
            }

            var10.setBackground((Drawable)CarUi.rounded$default(var22, CarUi.Companion.getDIM(), 15, null, 4, null));
            var10.setVisibility(8);
            var10.setOnClickListener(new SettingsActivity$$ExternalSyntheticLambda8(var9, this));
            LinearLayout var11 = new LinearLayout((Context)this);
            var11.setOrientation(0);
            var11.setGravity(16);
            CarUi var23 = this.ui;
            if (var23 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("ui");
               var23 = null;
            }

            var11.setBackground((Drawable)CarUi.rounded$default(var23, CarUi.Companion.getCARD(), 14, null, 4, null));
            var11.addView((View)var9, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
            View var12 = (View)var10;
            CarUi var41 = this.ui;
            CarUi var24 = var41;
            if (var41 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("ui");
               var24 = null;
            }

            var1 = var24.dp(30);
            CarUi var42 = this.ui;
            CarUi var25 = var42;
            if (var42 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("ui");
               var25 = null;
            }

            android.widget.LinearLayout.LayoutParams var13 = new android.widget.LinearLayout.LayoutParams(var1, var25.dp(30));
            CarUi var43 = this.ui;
            CarUi var26 = var43;
            if (var43 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("ui");
               var26 = null;
            }

            var13.rightMargin = var26.dp(14);
            Unit var27 = Unit.INSTANCE;
            var11.addView(var12, (LayoutParams)var13);
            this.togglesSearch = (View)var11;
            this.setHeaderTool((View)var11);
            var5 = var49;
            var4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var5, 10));
            Iterator var45 = var5.iterator();

            while (var45.hasNext()) {
               var4.add(this.shortSection((String)var45.next()));
            }

            List var29 = var4;
            List var14 = CollectionsKt.plus(var29, "Language");
            List var50 = CollectionsKt.plus(var49, "Language");
            ObjectRef var51 = new ObjectRef();
            IntRef var52 = new IntRef();
            CarUi var46 = this.ui;
            CarUi var30 = var46;
            if (var46 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("ui");
               var30 = null;
            }

            Pair var47 = var30.browserTabs(var14, 1, new SettingsActivity$$ExternalSyntheticLambda9(var52, var9, var51));
            View var31 = (View)var47.component1();
            Function1 var48 = (Function1)var47.component2();
            var8.addView(var31, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, -2)));
            FrameLayout var53 = new FrameLayout((Context)this);
            CarUi var32 = this.ui;
            if (var32 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("ui");
               var32 = null;
            }

            var53.setBackground((Drawable)var32.corners(CarUi.Companion.getSECTION(), 0, 0, 14, 14));
            var8.addView((View)var53, (LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, 0, 1.0F)));
            var51.element = (T)(new SettingsActivity$$ExternalSyntheticLambda10(var48, var50, new HashMap(), var49, this, var53));
            var9.addTextChangedListener(
               new TextWatcher(var10, this, new SettingsActivity$$ExternalSyntheticLambda12(var9, var51, var52, var48, var6, this, var53)) {
                  final TextView $clear;
                  final Runnable $runSearch;
                  final SettingsActivity this$0;

                  {
                     this.$clear = var1;
                     this.this$0 = var2x;
                     this.$runSearch = var3x;
                  }

                  public void afterTextChanged(Editable var1) {
                     TextView var4x = this.$clear;
                     CharSequence var5x = (CharSequence)var1;
                     byte var3x = 0;
                     boolean var2x;
                     if (var5x != null && var5x.length() != 0) {
                        var2x = false;
                     } else {
                        var2x = true;
                     }

                     if (var2x) {
                        var3x = 8;
                     }

                     var4x.setVisibility(var3x);
                     this.this$0.handler.removeCallbacks(this.$runSearch);
                     this.this$0.handler.postDelayed(this.$runSearch, 350L);
                  }

                  public void beforeTextChanged(CharSequence var1, int var2x, int var3x, int var4x) {
                  }

                  public void onTextChanged(CharSequence var1, int var2x, int var3x, int var4x) {
                  }
               }
            );
            var8.post(new SettingsActivity$$ExternalSyntheticLambda13(var51));
            var8.setTag(new SettingsActivity$$ExternalSyntheticLambda14(var53, this));
            return (View)var8;
         }
      }
   }

   private static final View togglesPage$inset(SettingsActivity var0, View var1) {
      if (!(var1 instanceof LinearLayout) || !(((LinearLayout)var1).getTag() instanceof Runnable) || ((LinearLayout)var1).getChildCount() != 2) {
         LinearLayout var4 = new LinearLayout((Context)var0);
         var4.setOrientation(1);
         var4.addView(var1);
         Object var3 = var1.getTag();
         Object var2 = var3;
         if (var3 == null) {
            var2 = new SettingsActivity$$ExternalSyntheticLambda106(var1, var0);
         }

         var4.setTag(var2);
         var1 = (View & LinearLayout)var4;
      }

      return var1;
   }

   private static final void togglesPage$inset$lambda$121$lambda$120(View var0, SettingsActivity var1) {
      CatalogPanel var2;
      if (var0 instanceof CatalogPanel) {
         var2 = (CatalogPanel & View)var0;
      } else {
         var2 = null;
      }

      if (var2 != null) {
         var2.syncAll();
         var1.current = (View)var2;
      }
   }

   private static final void togglesPage$lambda$109(SettingsActivity var0, View var1) {
      if (var0.current == var1) {
         var0.panels.remove("toggles:all");
         var0.open("toggles:all");
      }
   }

   private static final void togglesPage$lambda$113$lambda$112(EditText var0, SettingsActivity var1, View var2) {
      var0.setText("");
      Object var3 = var1.getSystemService("input_method");
      Intrinsics.checkNotNull(var3, "null cannot be cast to non-null type android.view.inputmethod.InputMethodManager");
      ((InputMethodManager)var3).hideSoftInputFromWindow(var0.getWindowToken(), 0);
      var0.clearFocus();
   }

   private static final Unit togglesPage$lambda$117(IntRef var0, EditText var1, ObjectRef var2, int var3) {
      var0.element = var3;
      var1.setText("");
      Function1 var4;
      if (var2.element == null) {
         Intrinsics.throwUninitializedPropertyAccessException("select");
         var4 = null;
      } else {
         var4 = (Function1)var2.element;
      }

      var4.invoke(var3);
      return Unit.INSTANCE;
   }

   private static final Unit togglesPage$lambda$124(Function1 var0, List var1, HashMap var2, List var3, SettingsActivity var4, FrameLayout var5, int var6) {
      var0.invoke(var6);
      String var7 = (String)var1.get(var6);
      Map var10 = var2;
      Object var8 = var10.get(var7);
      if (var8 == null) {
         if (Intrinsics.areEqual(var7, "Language") && !var3.contains(var7)) {
            var8 = togglesPage$inset(var4, (View)pageBox$default(var4, var4.languagePage(), true, false, 4, null));
         } else {
            View var9 = var4.catalogPage(var7, true);
            var8 = var9;
            if (var9 instanceof FrameLayout) {
               var8 = togglesPage$inset(var4, var9);
            }
         }

         var10.put(var7, var8);
      }

      togglesPage$showView(var5, var4, (View)var8);
      return Unit.INSTANCE;
   }

   private static final void togglesPage$lambda$132(
      EditText var0, ObjectRef var1, IntRef var2, Function1 var3, GalaxyApi var4, SettingsActivity var5, FrameLayout var6
   ) {
      String var9 = StringsKt.trim(var0.getText().toString()).toString().toLowerCase(Locale.ROOT);
      String var18 = "toLowerCase(...)";
      Intrinsics.checkNotNullExpressionValue(var9, "toLowerCase(...)");
      if (var9.length() < 2) {
         Function1 var20;
         if (var1.element == null) {
            Intrinsics.throwUninitializedPropertyAccessException("select");
            var20 = null;
         } else {
            var20 = (Function1)var1.element;
         }

         var20.invoke(var2.element);
      } else {
         var3.invoke(-1);
         ArrayList var10 = new ArrayList();

         for (GalaxyApi.Category var11 : var4.getCatalog()) {
            Iterable var24 = var11.getParams();
            var var13 = new ArrayList();

            for (Object var14 : var24) {
               GalaxyApi.Param var17 = (GalaxyApi.Param)var14;
               String var16 = var17.getLabel();
               String var15 = var17.getKey();
               String var8 = var17.getDescription();
               String var32 = var8;
               if (var8 == null) {
                  var32 = "";
               }

               var var33 = CollectionsKt.listOf(new String[]{var16, var15, var32, var17.getUnit()});
               boolean var7;
               if (var33 instanceof Collection && ((Collection)var33).isEmpty()) {
                  var7 = false;
               } else {
                  Iterator var38 = var33.iterator();

                  while (true) {
                     if (!var38.hasNext()) {
                        var7 = false;
                        break;
                     }

                     var15 = (String)var38.next();
                     var15 = var15.toLowerCase(Locale.ROOT);
                     Intrinsics.checkNotNullExpressionValue(var15, var18);
                     CharSequence var50 = var15;
                     CharSequence var51 = var9;
                     if (StringsKt.contains$default(var50, var51, false, 2, null)) {
                        var7 = true;
                        break;
                     }
                  }
               }

               if (var7) {
                  var13.add(var14);
               }
            }

            List var34 = var13;
            if (!var34.isEmpty()) {
               LinkedHashSet var43 = new LinkedHashSet();
               Iterable var25 = var11.getParams();
               Map var39 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(var25, 10)), 16));

               for (Object var44 : var25) {
                  var39.put(((GalaxyApi.Param)var44).getKey(), var44);
               }

               Iterator var45 = var34.iterator();

               while (var45.hasNext()) {
                  String var27 = ((GalaxyApi.Param)var45.next()).getKey();

                  while (var27 != null && var43.add(var27)) {
                     GalaxyApi.Param var28 = (GalaxyApi.Param)var39.get(var27);
                     if (var28 != null) {
                        var27 = var28.getParentKey();
                     } else {
                        var27 = null;
                     }
                  }
               }

               CarUi var40 = var5.ui;
               CarUi var29 = var40;
               if (var40 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("ui");
                  var29 = null;
               }

               String var41 = var11.getName();
               int var37 = var34.size();
               var10.add(var29.header(var41 + " (" + var37 + ")"));
               Activity var42 = var5;
               Iterable var30 = var11.getParams();
               var13 = new ArrayList();

               for (Object var35 : var30) {
                  if (var43.contains(((GalaxyApi.Param)var35).getKey())) {
                     var13.add(var35);
                  }
               }

               List var31 = var13;
               String var36 = var11.getName();
               var10.add(new CatalogPanel(var42, var4, var31, "Search · " + var36, true, new SettingsActivity$$ExternalSyntheticLambda88(var5)));
            }
         }

         if (var10.isEmpty()) {
            var10.add(var5.offline("No settings match \"" + var9 + "\"."));
         }

         SettingsActivity.ComboPage var22 = var5.new ComboPage(var5, var10);
         View var19 = togglesPage$inset(var5, (View)pageBox$default(var5, (View)var22, true, false, 4, null));
         var19.setTag(new SettingsActivity$$ExternalSyntheticLambda99(var22, var5));
         Unit var23 = Unit.INSTANCE;
         togglesPage$showView(var6, var5, var19);
      }
   }

   private static final Unit togglesPage$lambda$132$lambda$129(SettingsActivity var0, String var1) {
      Intrinsics.checkNotNullParameter(var1, "r");
      var0.openRoute(var1);
      return Unit.INSTANCE;
   }

   private static final void togglesPage$lambda$132$lambda$131$lambda$130(SettingsActivity.ComboPage var0, SettingsActivity var1) {
      var0.refresh();
      var1.current = (View)var0;
   }

   private static final void togglesPage$lambda$133(ObjectRef var0) {
      Function1 var1;
      if (var0.element == null) {
         Intrinsics.throwUninitializedPropertyAccessException("select");
         var1 = null;
      } else {
         var1 = (Function1)var0.element;
      }

      var1.invoke(0);
   }

   private static final void togglesPage$lambda$135(FrameLayout var0, SettingsActivity var1) {
      View var2 = var0.getChildAt(0);
      if (var2 != null) {
         togglesPage$showView(var0, var1, var2);
      }
   }

   private static final void togglesPage$showView(FrameLayout var0, SettingsActivity var1, View var2) {
      ViewParent var3 = var2.getParent();
      Object var4 = null;
      if (var3 != var0) {
         var0.removeAllViews();
         var3 = var2.getParent();
         ViewGroup var8;
         if (var3 instanceof ViewGroup) {
            var8 = (ViewGroup)var3;
         } else {
            var8 = null;
         }

         if (var8 != null) {
            var8.removeView(var2);
         }

         var0.addView(var2, (LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -1)));
      }

      if (var2 instanceof CatalogPanel) {
         ((CatalogPanel)var2).syncAll();
      } else {
         if (!(var2 instanceof SettingsActivity.ComboPage)) {
            Object var6 = var2.getTag();
            Runnable var5 = (Runnable)var4;
            if (var6 instanceof Runnable) {
               var5 = (Runnable)var6;
            }

            if (var5 != null) {
               var5.run();
            }

            return;
         }

         ((SettingsActivity.ComboPage)var2).refresh();
      }

      var1.current = var2;
   }

   private final View uiGroupPage(String var1) {
      Pair var2 = this.page();
      ScrollView var4 = (ScrollView)var2.component1();
      LinearLayout var5 = (LinearLayout)var2.component2();
      UiSettings var6 = new UiSettings((Context)this);
      CarUi var3 = this.ui;
      CarUi var10 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ui");
         var10 = null;
      }

      var5.addView((View)var10.note("These only change what the tablet draws. Driving behaviour and units come from the comma."));
      Iterable var13 = UiSettings.Companion.getITEMS();
      var var11 = new ArrayList();

      for (Object var14 : var13) {
         if (Intrinsics.areEqual(((UiSettings.Item)var14).getGroup(), var1)) {
            var11.add(var14);
         }
      }

      for (UiSettings.Item var12 : var11) {
         this.toggleCard(
            var5,
            var12.getTitle(),
            null,
            new SettingsActivity$$ExternalSyntheticLambda191(var6, var12),
            new SettingsActivity$$ExternalSyntheticLambda202(var6, var12)
         );
         if (Intrinsics.areEqual(var12.getKey(), "road_texture_flip")) {
            this.stepperCard(
               var5,
               "Road texture brightness",
               "Raise it if the road looks too dark (night doubles it automatically).",
               0.3F,
               3.0F,
               0.1F,
               new SettingsActivity$$ExternalSyntheticLambda213(var6),
               new SettingsActivity$$ExternalSyntheticLambda224(var6)
            );
         }
      }

      return (View)var4;
   }

   private static final boolean uiGroupPage$lambda$155(UiSettings var0, UiSettings.Item var1) {
      return var0.get(var1.getKey());
   }

   private static final Unit uiGroupPage$lambda$156(UiSettings var0, UiSettings.Item var1, boolean var2) {
      var0.set(var1.getKey(), var2);
      return Unit.INSTANCE;
   }

   private static final float uiGroupPage$lambda$157(UiSettings var0) {
      return var0.getFloat("road_texture_gain", 1.0F);
   }

   private static final Unit uiGroupPage$lambda$158(UiSettings var0, float var1) {
      var0.setFloat("road_texture_gain", var1);
      return Unit.INSTANCE;
   }

   @Override
   protected void attachBaseContext(Context var1) {
      Intrinsics.checkNotNullParameter(var1, "base");
      super.attachBaseContext(NarrowDensity.wrap$default(NarrowDensity.INSTANCE, var1, 0, 0.0F, 6, null));
   }

   @Override
   public GalaxyApi getGxApi() {
      return this.api;
   }

   @Override
   public String getGxHost() {
      return this.host;
   }

   @Deprecated(
      message = "Deprecated in Java"
   )
   @Override
   public void onBackPressed() {
      FrameLayout var4 = this.contentHolder;
      boolean var2 = true;
      if (var4 == null) {
         SettingsLauncher var9 = this.launcher;
         boolean var1;
         if (var9 != null && var9.getDrawerOpen()) {
            var1 = true;
         } else {
            var1 = false;
         }

         if (var1) {
            SettingsLauncher var13 = this.launcher;
            if (var13 != null) {
               var13.closeDrawer();
            }

            return;
         }
      }

      View var10 = this.current;
      boolean var3 = var10 instanceof GalaxySettingsView;
      Object var5 = null;
      GalaxySettingsView var11;
      if (var3) {
         var11 = (GalaxySettingsView)var10;
      } else {
         var11 = null;
      }

      boolean var7;
      if (var11 != null && var11.back()) {
         var7 = true;
      } else {
         var7 = false;
      }

      if (!var7) {
         View var6 = this.current;
         GalaxyWeb var12 = (GalaxyWeb)var5;
         if (var6 instanceof GalaxyWeb) {
            var12 = (GalaxyWeb)var6;
         }

         if (var12 != null && var12.back()) {
            var7 = var2;
         } else {
            var7 = false;
         }

         if (!var7) {
            if (this.contentHolder != null) {
               this.showHome();
            } else {
               super.onBackPressed();
            }
         }
      }
   }

   @Override
   protected void onCreate(Bundle var1) {
      Hpms var4 = Hpms.INSTANCE;
      Context var3 = this.getApplicationContext();
      Intrinsics.checkNotNullExpressionValue(var3, "getApplicationContext(...)");
      var4.init(var3);
      super.onCreate(var1);
      DriveJournal.INSTANCE.init((Context)this);
      CarUi var7 = new CarUi((Context)this, false, 2, null);
      var7.setAutoIcon(new SettingsActivity$$ExternalSyntheticLambda16(this));
      this.ui = var7;
      String var15 = this.getIntent().getStringExtra("host");
      String var8 = var15;
      if (var15 == null) {
         var8 = Link.INSTANCE.getHost();
      }

      this.host = var8;
      FrameLayout var9 = new FrameLayout((Context)this);
      var9.setBackgroundColor(CarUi.Companion.getBG());
      this.root = var9;
      FrameLayout var16 = this.root;
      FrameLayout var10 = var16;
      if (var16 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("root");
         var10 = null;
      }

      this.setContentView((View)var10);
      WindowCompat.setDecorFitsSystemWindows(this.getWindow(), false);
      FrameLayout var17 = this.root;
      FrameLayout var11 = var17;
      if (var17 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("root");
         var11 = null;
      }

      ViewCompat.setOnApplyWindowInsetsListener((View)var11, new SettingsActivity$$ExternalSyntheticLambda17());
      String var12 = this.host;
      if (var12 != null) {
         this.api = GalaxyApi.Companion.get((Context)this, var12);
         if (Link.INSTANCE.getHost() == null) {
            Link.INSTANCE.setHost(var12);
         }
      }

      String var13 = this.host;
      if (var13 != null) {
         this.handler.postDelayed(new SettingsActivity$$ExternalSyntheticLambda18(this, var13), 400L);
      }

      if (var1 != null) {
         int var2 = var1.getInt("area", -1);
         String var14 = var1.getString("page");
         if (var2 >= 0) {
            this.showArea(var2, var14);
         } else if (var14 != null && var1.getBoolean("open")) {
            this.showHome();
            this.openTarget(var14);
         } else {
            this.showHome();
         }
      } else {
         String var5 = this.getIntent().getStringExtra("tab");
         if (var5 != null) {
            switch (var5.hashCode()) {
               case -1335157162:
                  if (var5.equals("device")) {
                     this.showArea(0, null);
                     return;
                  }
                  break;
               case -1181248900:
                  if (var5.equals("terminal")) {
                     this.showArea(2, "native:terminal");
                     return;
                  }
                  break;
               case -1147621473:
                  if (var5.equals("toggles")) {
                     this.showArea(0, "native:toggles");
                     return;
                  }
                  break;
               case 110545371:
                  if (var5.equals("tools")) {
                     this.showArea(4, null);
                     return;
                  }
                  break;
               case 1319330215:
                  if (var5.equals("software")) {
                     this.showArea(0, "native:software");
                     return;
                  }
            }

            this.showHome();
            String var6 = this.getIntent().getStringExtra("tab");
            Intrinsics.checkNotNull(var6);
            this.openTarget(var6);
         } else {
            this.showHome();
         }
      }
   }

   @Override
   protected void onDestroy() {
      View var1 = this.current;
      GxPage var3;
      if (var1 instanceof GxPage) {
         var3 = (GxPage)var1;
      } else {
         var3 = null;
      }

      if (var3 != null) {
         var3.hide();
      }

      this.hideGalaxy(this.current);

      for (var var1_it : this.panels.values()) {

         var1 = var1_it;
         Intrinsics.checkNotNullExpressionValue(var1, "next(...)");
         var1 = var1;
         GalaxyWeb var6;
         if (var1 instanceof GalaxyWeb) {
            var6 = (GalaxyWeb)var1;
         } else {
            var6 = null;
         }

         if (var6 != null) {
            var6.destroy();
         }
      }

      TermWeb.INSTANCE.detach();
      GalaxyApi var7 = this.api;
      if (var7 != null) {
         var7.setOnChanged(null);
      }

      MapdMaps.INSTANCE.setOnChange(null);
      super.onDestroy();
   }

   @Override
   protected void onPause() {
      super.onPause();
      this.handler.removeCallbacks(this.ticker);
      this.handler.removeCallbacks(this.valuePoll);
      View var1 = this.current;
      GxPage var2;
      if (var1 instanceof GxPage) {
         var2 = (GxPage)var1;
      } else {
         var2 = null;
      }

      if (var2 != null) {
         var2.hide();
      }

      this.hideGalaxy(this.current);
   }

   @Override
   protected void onResume() {
      super.onResume();
      this.handler.post(this.ticker);
      this.handler.postDelayed(this.valuePoll, 4000L);
      View var2 = this.current;
      boolean var1 = var2 instanceof GxPage;
      Object var3 = null;
      GxPage var5;
      if (var1) {
         var5 = (GxPage)var2;
      } else {
         var5 = null;
      }

      if (var5 != null && !var5.getShown() && var5.getParent() != null) {
         var5.show();
      }

      var2 = this.current;
      GalaxyWeb var7;
      if (var2 instanceof GalaxyWeb) {
         var7 = (GalaxyWeb)var2;
      } else {
         var7 = null;
      }

      if (var7 != null) {
         var7.show();
      }

      var2 = this.current;
      CommaHealthView var9;
      if (var2 instanceof CommaHealthView) {
         var9 = (CommaHealthView)var2;
      } else {
         var9 = null;
      }

      if (var9 != null) {
         var9.start();
      }

      var2 = this.current;
      DoorsBeltsView var11;
      if (var2 instanceof DoorsBeltsView) {
         var11 = (DoorsBeltsView)var2;
      } else {
         var11 = null;
      }

      if (var11 != null) {
         var11.start();
      }

      var2 = this.current;
      KeyFobView var13;
      if (var2 instanceof KeyFobView) {
         var13 = (KeyFobView)var2;
      } else {
         var13 = null;
      }

      if (var13 != null) {
         var13.start();
      }

      var2 = this.current;
      HubPowerView var15;
      if (var2 instanceof HubPowerView) {
         var15 = (HubPowerView)var2;
      } else {
         var15 = null;
      }

      if (var15 != null) {
         var15.start();
      }

      var2 = this.current;
      GalaxySettingsView var17;
      if (var2 instanceof GalaxySettingsView) {
         var17 = (GalaxySettingsView)var2;
      } else {
         var17 = null;
      }

      if (var17 != null) {
         var17.show();
      }

      View var4 = this.current;
      GalaxyExtras.QuickSelect var18 = (GalaxyExtras.QuickSelect)var3;
      if (var4 instanceof GalaxyExtras.QuickSelect) {
         var18 = (GalaxyExtras.QuickSelect)var4;
      }

      if (var18 != null) {
         var18.show();
      }
   }

   @Override
   protected void onSaveInstanceState(Bundle var1) {
      Intrinsics.checkNotNullParameter(var1, "outState");
      super.onSaveInstanceState(var1);
      var1.putInt("area", this.areaIndex);
      String var3 = this.currentPage;
      if (var3 != null) {
         var1.putString("page", var3);
      }

      boolean var2;
      if (this.contentHolder != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      var1.putBoolean("open", var2);
   }

   public final void openGalaxyWeb(String var1, String var2) {
      Intrinsics.checkNotNullParameter(var1, "path");
      Intrinsics.checkNotNullParameter(var2, "title");
      String var3 = this.currentPage;
      this.open("gweb:" + var1 + "|" + var2);
      this.webReturn = var3;
   }

   @Override
   public void openPage(String var1) {
      Intrinsics.checkNotNullParameter(var1, "page");
      List var4 = this.areas();
      int var2 = 0;
      Iterator var5 = var4.iterator();

      while (true) {
         if (!var5.hasNext()) {
            var2 = -1;
            break;
         }

         var var6 = ((SettingsActivity.Area)var5.next()).getRails();
         boolean var9;
         if (var6 instanceof Collection && ((Collection)var6).isEmpty()) {
            var9 = false;
         } else {
            Iterator var10 = var6.iterator();

            while (true) {
               if (!var10.hasNext()) {
                  var9 = false;
                  break;
               }

               var var7 = (Iterable & List)((Pair)var10.next()).component2();
               if (var7 instanceof Collection && ((Collection)var7).isEmpty()) {
                  var9 = false;
               } else {
                  Iterator var11 = var7.iterator();

                  while (true) {
                     if (!var11.hasNext()) {
                        var9 = false;
                        break;
                     }

                     SettingsActivity.Rail var8 = (SettingsActivity.Rail)var11.next();
                     if (Intrinsics.areEqual(var8.getPage(), var1)) {
                        var9 = true;
                        break;
                     }
                  }
               }

               if (var9) {
                  var9 = true;
                  break;
               }
            }
         }

         if (var9) {
            break;
         }

         var2++;
      }

      if (var2 >= 0) {
         this.showArea(var2, var1);
      } else {
         this.open(var1);
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void pickFile(String[] var1, Function1<? super Uri, Unit> var2) {
      Intrinsics.checkNotNullParameter(var1, "mimes");
      Intrinsics.checkNotNullParameter(var2, "then");
      this.onFilePicked = var2;

      label26:
      try {
         Result.Companion var3 = Result.Companion;
         this.pickAny.launch(var1);
         var7 = KtCompat.resultOf(Unit.INSTANCE);
      } catch (Throwable var5) {
         Result.Companion var6 = Result.Companion;
         var7 = KtCompat.resultOf(ResultKt.createFailure(var5));
         break label26;
      }

      if (KtCompat.exceptionOrNull(var7) != null) {
         this.onFilePicked = null;
         var2.invoke(null);
      }
   }

   private static final class Area {
      private final int icon;
      private final List<Pair<String, List<SettingsActivity.Rail>>> rails;
      private final String sub;
      private final String title;

      public Area(String var1, String var2, int var3, List<? extends Pair<String, ? extends List<SettingsActivity.Rail>>> var4) {
         Intrinsics.checkNotNullParameter(var1, "title");
         Intrinsics.checkNotNullParameter(var2, "sub");
         Intrinsics.checkNotNullParameter(var4, "rails");
         super();
         this.title = var1;
         this.sub = var2;
         this.icon = var3;
         this.rails = var4;
      }

      public final int getIcon() {
         return this.icon;
      }

      public final List<Pair<String, List<SettingsActivity.Rail>>> getRails() {
         return this.rails;
      }

      public final String getSub() {
         return this.sub;
      }

      public final String getTitle() {
         return this.title;
      }
   }

   private final class ComboPage extends ScrollView {
      private final List<View> parts;
      final SettingsActivity this$0;

      public ComboPage(List<? extends View> var1, List var2) {
         Intrinsics.checkNotNullParameter(var2, "parts");
         this.this$0 = var1;
         super((Context)var1);
         this.parts = var2;
         LinearLayout var4 = new LinearLayout(this.getContext());
         var4.setOrientation(1);
         Iterator var3 = this.parts.iterator();

         while (var3.hasNext()) {
            var4.addView((View)var3.next(), (LayoutParams)(new android.widget.LinearLayout.LayoutParams(-1, -2)));
         }

         this.addView((View)var4);
      }

      public final List<View> getParts() {
         return this.parts;
      }

      public final void refresh() {
         for (View var5 : this.parts) {
            boolean var1 = var5 instanceof DevicePanel;
            Object var3 = null;
            DevicePanel var2;
            if (var1) {
               var2 = (DevicePanel)var5;
            } else {
               var2 = null;
            }

            if (var2 != null) {
               var2.refresh(true);
            }

            CatalogPanel var6 = (CatalogPanel)var3;
            if (var5 instanceof CatalogPanel) {
               var6 = (CatalogPanel)var5;
            }

            if (var6 != null) {
               var6.syncAll();
            }
         }
      }

      public final void tick() {
         for (View var5 : this.parts) {
            boolean var1 = var5 instanceof DevicePanel;
            Object var3 = null;
            DevicePanel var2;
            if (var1) {
               var2 = (DevicePanel)var5;
            } else {
               var2 = null;
            }

            if (var2 != null) {
               var2.onTick();
            }

            CatalogPanel var6 = (CatalogPanel)var3;
            if (var5 instanceof CatalogPanel) {
               var6 = (CatalogPanel)var5;
            }

            if (var6 != null) {
               var6.onTick();
            }
         }
      }
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      public final Set<String> getPLACED_PAGES() {
         return SettingsActivity.PLACED_PAGES;
      }

      public final SettingIcons.Icon railIcon(String var1) {
         Intrinsics.checkNotNullParameter(var1, "page");
         SettingIcons.Icon var5;
         if (Intrinsics.areEqual(var1, "drivingmap")) {
            var5 = new SettingIcons.Icon("gauge2", "speed", false, 4, null);
         } else if (Intrinsics.areEqual(var1, "toolshome")) {
            var5 = new SettingIcons.Icon("widgets", "device", false, 4, null);
         } else if (Intrinsics.areEqual(var1, "connections")) {
            var5 = new SettingIcons.Icon("link", "device", false, 4, null);
         } else if (Intrinsics.areEqual(var1, "health")) {
            var5 = new SettingIcons.Icon("plots", "device", false, 4, null);
         } else if (Intrinsics.areEqual(var1, "journal:last")) {
            var5 = new SettingIcons.Icon("car", "tablet", false, 4, null);
         } else if (Intrinsics.areEqual(var1, "journal:trends")) {
            var5 = new SettingIcons.Icon("plots", "tablet", false, 4, null);
         } else if (Intrinsics.areEqual(var1, "journal:alerts")) {
            var5 = new SettingIcons.Icon("bell", "tablet", false, 4, null);
         } else if (Intrinsics.areEqual(var1, "journal:spots")) {
            var5 = new SettingIcons.Icon("pin", "tablet", false, 4, null);
         } else if (Intrinsics.areEqual(var1, "profiles")) {
            var5 = new SettingIcons.Icon("standard", "person", false, 4, null);
         } else if (StringsKt.startsWith$default(var1, "galaxy:", false, 2, null)) {
            var5 = SettingIcons.INSTANCE.forPage(StringsKt.removePrefix(var1, "galaxy:"));
         } else if (Intrinsics.areEqual(var1, "quickselect")) {
            var5 = new SettingIcons.Icon("star", "display", false, 4, null);
         } else if (StringsKt.startsWith$default(var1, "gweb:", false, 2, null)) {
            var5 = new SettingIcons.Icon(
               this.toolIcon(StringsKt.substringBefore$default(StringsKt.removePrefix(var1, "gweb:"), "|", null, 2, null)), "device", false, 4, null
            );
         } else {
            boolean var2 = StringsKt.startsWith$default(var1, "uigroup:", false, 2, null);
            String var3 = "star";
            if (var2) {
               label200: {
                  label199: {
                     String var4 = StringsKt.removePrefix(var1, "uigroup:");
                     switch (var4.hashCode()) {
                        case -169275166:
                           var1 = var3;
                           if (var4.equals("Effects")) {
                              break label200;
                           }
                           break;
                        case 71895:
                           if (var4.equals("HUD")) {
                              var1 = "widgets";
                              break label200;
                           }
                           break;
                        case 77116:
                           if (var4.equals("Map")) {
                              break label199;
                           }
                           break;
                        case 2552640:
                           if (var4.equals("Road")) {
                              break label199;
                           }
                           break;
                        case 73186279:
                           if (var4.equals("Lanes")) {
                              var1 = "lane";
                              break label200;
                           }
                           break;
                        case 2011082565:
                           if (var4.equals("Camera")) {
                              var1 = "camera";
                              break label200;
                           }
                     }

                     var1 = "eye";
                     break label200;
                  }

                  var1 = "map";
               }

               var5 = new SettingIcons.Icon(var1, "tablet", false, 4, null);
            } else {
               label187: {
                  label186: {
                     switch (var1.hashCode()) {
                        case -1844774923:
                           if (var1.equals("tablet:blinker")) {
                              var3 = "blinker";
                              break label187;
                           }
                           break;
                        case -1680479212:
                           if (var1.equals("tablet:braking")) {
                              var3 = "decel";
                              break label187;
                           }
                           break;
                        case -1486773745:
                           if (var1.equals("tablet:nav")) {
                              break label186;
                           }
                           break;
                        case -1486769323:
                           if (var1.equals("tablet:rtk")) {
                              var3 = "satellite";
                              break label187;
                           }
                           break;
                        case -1417203926:
                           if (var1.equals("tablet:techstream")) {
                              var3 = "wrench";
                              break label187;
                           }
                           break;
                        case -1134666246:
                           if (var1.equals("keyfob")) {
                              var3 = "key";
                              break label187;
                           }
                           break;
                        case -1073114497:
                           if (var1.equals("native:terminal")) {
                              var3 = "terminal";
                              break label187;
                           }
                           break;
                        case -1023481817:
                           if (var1.equals("native:developer")) {
                              var3 = "console";
                              break label187;
                           }
                           break;
                        case -960904562:
                           if (var1.equals("activity:sensors")) {
                              var3 = "gps";
                              break label187;
                           }
                           break;
                        case -661461902:
                           if (var1.equals("tablet:connection")) {
                              var3 = "link";
                              break label187;
                           }
                           break;
                        case -581968182:
                           if (var1.equals("roaddata")) {
                              var3 = "lane";
                              break label187;
                           }
                           break;
                        case 101886580:
                           if (var1.equals("tablet:voicestudio")) {
                              var3 = "mic";
                              break label187;
                           }
                           break;
                        case 184405451:
                           if (var1.equals("native:network")) {
                              var3 = "wifi";
                              break label187;
                           }
                           break;
                        case 239912240:
                           if (var1.equals("hubpower")) {
                              var3 = "power";
                              break label187;
                           }
                           break;
                        case 273892121:
                           if (var1.equals("native:device")) {
                              var3 = "info";
                              break label187;
                           }
                           break;
                        case 592569703:
                           if (var1.equals("tablet:storage")) {
                              var3 = "data";
                              break label187;
                           }
                           break;
                        case 1154624299:
                           if (var1.equals("tablet:maps")) {
                              break label186;
                           }
                           break;
                        case 1154899769:
                           if (var1.equals("tablet:view")) {
                              var3 = "camera";
                              break label187;
                           }
                           break;
                        case 1166998092:
                           if (var1.equals("activity:files")) {
                              var3 = "folder";
                              break label187;
                           }
                           break;
                        case 1177077078:
                           if (var1.equals("tablet:backup")) {
                              var3 = "backup";
                              break label187;
                           }
                           break;
                        case 1202172345:
                           if (var1.equals("doorbelt")) {
                              var3 = "car";
                              break label187;
                           }
                           break;
                        case 1422561657:
                           if (!var1.equals("tablet:about")) {
                           }
                           break;
                        case 1427464618:
                           if (var1.equals("native:software")) {
                              var3 = "update";
                              break label187;
                           }
                           break;
                        case 1429999176:
                           if (var1.equals("tablet:ideas")) {
                              break label187;
                           }
                           break;
                        case 1440277429:
                           if (var1.equals("tablet:theme")) {
                              var3 = "palette";
                              break label187;
                           }
                           break;
                        case 1442336542:
                           if (var1.equals("tablet:voice")) {
                              var3 = "speaker";
                              break label187;
                           }
                           break;
                        case 1488266044:
                           if (var1.equals("native:toggles")) {
                              var3 = "toggle";
                              break label187;
                           }
                           break;
                        case 1694003232:
                           if (var1.equals("activity:lanepics")) {
                              var3 = "camera";
                              break label187;
                           }
                           break;
                        case 2037187069:
                           if (var1.equals("bookmarks")) {
                              var3 = "bookmark";
                              break label187;
                           }
                     }

                     var3 = "info";
                     break label187;
                  }

                  var3 = "map";
               }

               if ((!StringsKt.startsWith$default(var1, "tablet:", false, 2, null) || Intrinsics.areEqual(var1, "tablet:techstream"))
                  && !StringsKt.startsWith$default(var1, "activity:", false, 2, null)) {
                  var1 = "device";
               } else {
                  var1 = "tablet";
               }

               var5 = new SettingIcons.Icon(var3, var1, false, 4, null);
            }
         }

         return var5;
      }

      public final String toolIcon(String var1) {
         Intrinsics.checkNotNullParameter(var1, "path");
         if (Intrinsics.areEqual(var1, "/")) {
            var1 = "info";
         } else if (StringsKt.contains$default(var1, "recording", false, 2, null)) {
            var1 = "film";
         } else if (StringsKt.contains$default(var1, "bluetooth", false, 2, null)) {
            var1 = "bluetooth";
         } else if (StringsKt.contains$default(var1, "laboratory", false, 2, null)) {
            var1 = "flask";
         } else if (StringsKt.contains$default(var1, "model", false, 2, null)) {
            var1 = "chip";
         } else if (StringsKt.contains$default(var1, "navigation", false, 2, null)) {
            var1 = "map";
         } else if (StringsKt.contains$default(var1, "plots", false, 2, null) || StringsKt.contains$default(var1, "tuning", false, 2, null)) {
            var1 = "plots";
         } else if (StringsKt.contains$default(var1, "logs", false, 2, null)) {
            var1 = "console";
         } else if (StringsKt.contains$default(var1, "galaxy", false, 2, null)) {
            var1 = "star";
         } else if (StringsKt.contains$default(var1, "android", false, 2, null)) {
            var1 = "phone";
         } else {
            CharSequence var4 = var1;
            String var3 = "camera";
            if (StringsKt.contains$default(var4, "camera", false, 2, null)) {
               var1 = var3;
            } else if (StringsKt.contains$default(var1, "device-pref", false, 2, null)) {
               var1 = "shuffle";
            } else {
               boolean var2 = StringsKt.contains$default(var1, "system", false, 2, null);
               var3 = "wrench";
               if (var2) {
                  var1 = var3;
               } else if (StringsKt.contains$default(var1, "theme", false, 2, null)) {
                  var1 = "palette";
               } else if (StringsKt.contains$default(var1, "vehicle", false, 2, null)) {
                  var1 = "car";
               } else {
                  var1 = "wrench";
               }
            }
         }

         return var1;
      }
   }

   private static final class Quad {
      private final int c;
      private final int i;
      private final int r;
      private final String sub;

      public Quad(int var1, int var2, int var3, String var4) {
         Intrinsics.checkNotNullParameter(var4, "sub");
         super();
         this.i = var1;
         this.r = var2;
         this.c = var3;
         this.sub = var4;
      }

      // $VF: synthetic method
      public static SettingsActivity.Quad copy$default(SettingsActivity.Quad var0, int var1, int var2, int var3, String var4, int var5, Object var6) {
         if ((var5 & 1) != 0) {
            var1 = var0.i;
         }

         if ((var5 & 2) != 0) {
            var2 = var0.r;
         }

         if ((var5 & 4) != 0) {
            var3 = var0.c;
         }

         if ((var5 & 8) != 0) {
            var4 = var0.sub;
         }

         return var0.copy(var1, var2, var3, var4);
      }

      public final int component1() {
         return this.i;
      }

      public final int component2() {
         return this.r;
      }

      public final int component3() {
         return this.c;
      }

      public final String component4() {
         return this.sub;
      }

      public final SettingsActivity.Quad copy(int var1, int var2, int var3, String var4) {
         Intrinsics.checkNotNullParameter(var4, "sub");
         return new SettingsActivity.Quad(var1, var2, var3, var4);
      }

      @Override
      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else if (!(var1 instanceof SettingsActivity.Quad)) {
            return false;
         } else {
            var1 = var1;
            if (this.i != var1.i) {
               return false;
            } else if (this.r != var1.r) {
               return false;
            } else {
               return this.c != var1.c ? false : Intrinsics.areEqual(this.sub, var1.sub);
            }
         }
      }

      public final int getC() {
         return this.c;
      }

      public final int getI() {
         return this.i;
      }

      public final int getR() {
         return this.r;
      }

      public final String getSub() {
         return this.sub;
      }

      @Override
      public int hashCode() {
         return ((Integer.hashCode(this.i) * 31 + Integer.hashCode(this.r)) * 31 + Integer.hashCode(this.c)) * 31 + this.sub.hashCode();
      }

      @Override
      public String toString() {
         int var2 = this.i;
         int var3 = this.r;
         int var1 = this.c;
         String var4 = this.sub;
         return "Quad(i=" + var2 + ", r=" + var3 + ", c=" + var1 + ", sub=" + var4 + ")";
      }
   }

   private static final class Rail {
      private final int icon;
      private final String label;
      private final String page;

      public Rail(String var1, int var2, String var3) {
         Intrinsics.checkNotNullParameter(var1, "label");
         Intrinsics.checkNotNullParameter(var3, "page");
         super();
         this.label = var1;
         this.icon = var2;
         this.page = var3;
      }

      public final int getIcon() {
         return this.icon;
      }

      public final String getLabel() {
         return this.label;
      }

      public final String getPage() {
         return this.page;
      }

      public final SettingIcons.Icon getSv() {
         return SettingsActivity.Companion.railIcon(this.page);
      }
   }

   static final class Anon_tabletStoragePage_poll_1 implements Runnable {
      Anon_tabletStoragePage_poll_1(ScrollView var1, BooleanRef var2x, SettingsActivity var3x, BooleanRef var4x, LinearLayout var5x) {
         this.$sv = var1;
         this.$seen = var2x;
         this.this$0 = var3x;
         this.$copyingNow = var4x;
         this.$list = var5x;
      }

         final BooleanRef $copyingNow;
         final LinearLayout $list;
         final BooleanRef $seen;
         final ScrollView $sv;
         final SettingsActivity this$0;

         // $VF: synthetic method
         public static void $r8$lambda$6y_i4rnJMjHRetwEdZFrd_5SJ4c/* $VF was: $r8$lambda$6y_i4rnJMjHRetwEdZFrd_5SJ4c*/(
            ScrollView var0, BooleanRef var1, JSONObject var2, LinearLayout var3, BooleanRef var4, SettingsActivity var5
         ) {
            run$lambda$1$lambda$0(var0, var1, var2, var3, var4, var5);
         }

         // $VF: synthetic method
         public static void $r8$lambda$UzBPTDZP98Tiu8xfuOSSMYCqUN0(
            String var0, SettingsActivity var1, ScrollView var2, BooleanRef var3, LinearLayout var4, BooleanRef var5
         ) {
            run$lambda$1(var0, var1, var2, var3, var4, var5);
         }

         private static final void run$lambda$1(String var0, SettingsActivity var1x, ScrollView var2x, BooleanRef var3x, LinearLayout var4x, BooleanRef var5x) {
            JSONObject var6x = StorageInfo.fetch$default(StorageInfo.INSTANCE, var0, 0, 2, null);
            var1x.handler.post(new SettingsActivity$tabletStoragePage$poll$1$$ExternalSyntheticLambda1(var2x, var3x, var6x, var4x, var5x, var1x));
         }

         private static final void run$lambda$1$lambda$0(
            ScrollView var0, BooleanRef var1x, JSONObject var2x, LinearLayout var3x, BooleanRef var4x, SettingsActivity var5x
         ) {
            if (var0.isAttachedToWindow() || !var1x.element) {
               SettingsActivity.tabletStoragePage$render(var3x, var4x, var5x, var2x);
            }
         }

         @Override
         public void run() {
            if (this.$sv.isAttachedToWindow()) {
               this.$seen.element = true;
            } else if (this.$seen.element) {
               return;
            }

            String var4x = this.this$0.host;
            String var3x = var4x;
            if (var4x == null) {
               var3x = Link.INSTANCE.getHost();
            }

            long var1x = 3000L;
            if (var3x == null) {
               this.this$0.handler.postDelayed(this, 3000L);
            } else {
               new Thread(
                     new SettingsActivity$tabletStoragePage$poll$1$$ExternalSyntheticLambda0(
                        var3x, this.this$0, this.$sv, this.$seen, this.$list, this.$copyingNow
                     )
                  )
                  .start();
               Handler var6x = this.this$0.handler;
               Runnable var5x = this;
               if (!this.$copyingNow.element) {
                  var1x = 10000L;
               }

               var6x.postDelayed(var5x, var1x);
            }
         }
         }
}
