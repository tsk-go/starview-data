package com.starpilot.starview;

import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.SurfaceHolder.Callback;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts.OpenDocument;
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.core.view.WindowInsetsCompat.Type;
import java.io.File;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.Grouping;
import kotlin.collections.GroupingKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref.BooleanRef;
import kotlin.jvm.internal.Ref.FloatRef;
import kotlin.jvm.internal.Ref.IntRef;
import kotlin.jvm.internal.Ref.LongRef;
import kotlin.jvm.internal.Ref.ObjectRef;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import okhttp3.OkHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

public final class MainActivity extends AppCompatActivity {
   public static final MainActivity.Companion Companion = new MainActivity.Companion(null);
   public static final String EXTRA_EDIT_MASKS = "edit_masks";
   public static final String EXTRA_OPEN_NAV = "open_nav";
   private static final String TAG = "StarView/Main";
   private TextView alert1;
   private TextView alert2;
   private float alertAlpha;
   private LinearLayout alertBox;
   private View alertTint;
   private boolean askedNotify;
   private boolean backupArmed;
   private BlinkerCardView blinkCard;
   private ImageView bookmarkBtn;
   private BookmarkCard bookmarkCard;
   private final CarMemory carMemory;
   private TextView clockTv;
   private TextView compass;
   private LinearLayout connectBar;
   private TextView curveLabel;
   private DataClient data;
   private final ActivityResultLauncher<Intent> destLauncher;
   private VideoClient detRoad;
   private VideoClient detWide;
   private Detector detectorRef;
   private TextView devMetrics;
   private DevSidebarView devSidebar;
   private View divider;
   private DmView dm;
   private ImageView egpu;
   private boolean egpuFallback;
   private boolean egpuSeen;
   private boolean egpuWasEngaged;
   private LinearLayout favBar;
   private long favHiddenUntil;
   private String favKey;
   private final Lazy floating$delegate;
   private int frameH;
   private int frameW;
   private AetherGaugeView gauge;
   private GpsCardView gpsCard;
   private final Lazy gpuBanner$delegate;
   private long gpuLoadSince;
   private final Paint greyPaint;
   private boolean homeShown;
   private String host;
   private EditText hostEdit;
   private HudLayout hud;
   private HudEditor hudEditor;
   private Boolean hudGrey;
   private final Map<String, String> hudSettingKeys;
   private TextView ideasChip;
   private ImageView ideasDay;
   private long ideasLabelMs;
   private boolean ideasWasStarted;
   private LinearLayout ideasWhy;
   private long ideasWhyShownFor;
   private final LaneAhead laneAhead;
   private LaneCardView laneCard;
   private final LaneFeed laneFeed;
   private LaneMapLog laneMapLog;
   private final LaneMarks laneMarks;
   private final Lazy laneModel$delegate;
   private final Lazy laneShots$delegate;
   private LaneSuggestView laneSuggest;
   private final LaneBrain laneTracker;
   private boolean laneWasStarted;
   private String lastCrash;
   private String lastHomeKey;
   private boolean lastHudEditing;
   private long lastModelFrame;
   private String lastNavDest;
   private String lastStatus;
   private boolean lastThemeApplied;
   private long lastThemeCheck;
   private float lastWs;
   private LayoutSeg layoutSeg;
   private String linkIf;
   private TextView mapBtn;
   private MapPane mapPane;
   private MaskEditor maskEditor;
   private LinearLayout maxCard;
   private TextView maxLabel;
   private ImageView micBtn;
   private TextView modeText;
   private NavCardView navCard;
   private boolean night;
   private NudgeHintView nudgeHint;
   private OffroadHome offroadHome;
   private long onroadSinceNs;
   private FrameLayout outer;
   private OverlayView overlay;
   private MiciDistanceView personality;
   private final ActivityResultLauncher<String[]> pickBackup;
   private SideView pipLeft;
   private SideView pipRight;
   private int port;
   private TextView quickBtn;
   private QuickPanel quickPanel;
   private boolean reconnectPending;
   private ReplayBar replayBar;
   private boolean replayWas;
   private TextView roadName;
   private FrameLayout root;
   private boolean saverLinked;
   private TextView saverPill;
   private SceneView scene;
   private TextView sceneBtn;
   private boolean sceneOn;
   private boolean searching;
   private TextView setSpeedTv;
   private UiSettings settings;
   private volatile SharedPlace.Place sharedPending;
   private LinearLayout sidebar;
   private SlcView slc;
   private SlcActionsView slcActions;
   private SlcSourcesView slcSources;
   private SlowReasonView slowCard;
   private TextView speedTv;
   private TextView speedUnit;
   private final Lazy stallBanner$delegate;
   private long standstillSinceNs;
   private TextView statsTv;
   private boolean statsVisible;
   private TextView statusTv;
   private final Lazy storageHttp$delegate;
   private boolean surfaceReady;
   private Function0<Unit> tabletModePusher;
   private final Telemetry telemetry;
   private int tick;
   private final GradientDrawable tintDrawable;
   private final Handler ui = new Handler(Looper.getMainLooper());
   private final Anon_uiTick_1 uiTick;
   private boolean usbProbe;
   private VideoClient video;
   private boolean videoConnected;
   private SurfaceView videoView;
   private volatile boolean visible;
   private final ActivityResultLauncher<Intent> voiceLauncher;
   private WeatherView weather;
   private WheelButton wheel;
   private TextView whereBtn;

   // $VF: synthetic method
   public static void $r8$lambda$_ez0Fn305PwMoODb0VIX_pHeYIk/* $VF was: $r8$lambda$_ez0Fn305PwMoODb0VIX_pHeYIk*/(
      MainActivity var0, DialogInterface var1, int var2
   ) {
      onCreate$lambda$31$lambda$29(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$07_Ng7dk2VA44LEeCeaPnZlQd8c(MainActivity var0) {
      onCreate$lambda$33(var0);
   }

   // $VF: synthetic method
   public static Pair $r8$lambda$0HrbW9_pIwKTxZWW8cnFu0qT1bk/* $VF was: $r8$lambda$0HrbW9_pIwKTxZWW8cnFu0qT1bk*/(MainActivity var0) {
      return onCreate$lambda$50(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$0e_5A8bpmE7Zk3YrWe7yEZdhh0w/* $VF was: $r8$lambda$0e_5A8bpmE7Zk3YrWe7yEZdhh0w*/(MainActivity var0, View var1) {
      onCreate$lambda$40(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$0jWgCSsGbzS1Sl7KIXT9g29A6kE(MainActivity var0, String var1) {
      return onCreate$lambda$69(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$167d4oFI8leIUjqtRGOJnFF0RpM(MainActivity var0) {
      onCreate$lambda$37$lambda$36(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$1tiq5wJncDLspRdSB8JcqFERoBU(MainActivity var0) {
      discover$lambda$153$lambda$152$lambda$151(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$1ykBJ1xQaE_riuUhcXSDK321USw(MainActivity var0, int var1, int var2) {
      onCreate$lambda$71$lambda$70(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$220cfs_f4TIQ1EppCjDu01nJZPQ/* $VF was: $r8$lambda$220cfs_f4TIQ1EppCjDu01nJZPQ*/(MainActivity var0) {
      return startMaskEdit$lambda$22(var0);
   }

   // $VF: synthetic method
   public static List $r8$lambda$2P52AZXhcShj_uaYQNH1VeCLfM0/* $VF was: $r8$lambda$2P52AZXhcShj_uaYQNH1VeCLfM0*/(MainActivity var0) {
      return onCreate$lambda$47(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$2hulvJhs30kZGdZVMECMe6STuns(MainActivity var0, String var1, JSONObject var2, View var3) {
      syncFavBar$lambda$185$lambda$184(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static void $r8$lambda$2pbZxw19DBd7_WrO0XYA17PP_As/* $VF was: $r8$lambda$2pbZxw19DBd7_WrO0XYA17PP_As*/(MainActivity var0, String var1, View var2) {
      wireControls$lambda$146(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$3BUP8ZuPVwioS7botlmrM2qjbWw(MainActivity var0) {
      ideasTick$lambda$191(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$4ET8Y3rU3ThDbBUod7_19T8uCbg(MainActivity var0) {
      discover$lambda$153$lambda$152$lambda$149(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$6ZJ_83sdYu8kOolfKGefMSaUZ5k/* $VF was: $r8$lambda$6ZJ_83sdYu8kOolfKGefMSaUZ5k*/(MainActivity var0, View var1) {
      onCreate$lambda$67(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$6f8SJn_wpqgmxZ_lYGPA_EmqXxA/* $VF was: $r8$lambda$6f8SJn_wpqgmxZ_lYGPA_EmqXxA*/(
      MainActivity var0, DialogInterface var1, int var2
   ) {
      onCreate$lambda$40$lambda$38(var0, var1, var2);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$7JJw4YS23IZbDKY_cwM5Yy2ac6s/* $VF was: $r8$lambda$7JJw4YS23IZbDKY_cwM5Yy2ac6s*/(
      MainActivity var0, FloatRef var1, FloatRef var2, BooleanRef var3, Runnable var4, float var5, View var6, MotionEvent var7
   ) {
      return wireControls$lambda$139$lambda$138(var0, var1, var2, var3, var4, var5, var6, var7);
   }

   // $VF: synthetic method
   public static void $r8$lambda$7ZEtzmwst98CbSAxQkmqn0dj698(MainActivity var0, Discovery.Found var1) {
      discover$lambda$153$lambda$152(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$8CHGLAcsqHR3gIhEYQhxpD_4gUA/* $VF was: $r8$lambda$8CHGLAcsqHR3gIhEYQhxpD_4gUA*/(
      MainActivity var0, DialogInterface var1, int var2
   ) {
      onCreate$lambda$31$lambda$28(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$8VIuviw5zmR5t4WYojWMwkQyIcc(MainActivity var0, View var1) {
      onCreate$lambda$92$lambda$91(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$B8Vu3uQICaDcwtJ0yrwPePoRj9s(MainActivity var0) {
      return discover$lambda$153$lambda$152$lambda$148(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$BfZSGUjhHqw9RasPPTgJgovh7_E/* $VF was: $r8$lambda$BfZSGUjhHqw9RasPPTgJgovh7_E*/(MainActivity var0, ObjectRef var1) {
      return onCreate$lambda$62(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$BpIw6V9RxqcyLEAnQ2Afis4QCUk(MainActivity var0, JSONObject var1) {
      fetchStorage$lambda$165$lambda$164$lambda$163(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$C3RvtHg3_TpC3OkhYqHKevLkRJs/* $VF was: $r8$lambda$C3RvtHg3_TpC3OkhYqHKevLkRJs*/(MainActivity var0, Image var1) {
      return onCreate$lambda$81$lambda$80(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$C4gx5IRqiqqvVYKEF0egt7JOamY(MainActivity var0, View var1) {
      onCreate$lambda$60(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$C7DXQu_5Pme_jx0HUYafyDG_uJs/* $VF was: $r8$lambda$C7DXQu_5Pme_jx0HUYafyDG_uJs*/(MainActivity var0, SlowReason.Event var1) {
      return onCreate$lambda$44(var0, var1);
   }

   // $VF: synthetic method
   public static LaneModel $r8$lambda$DCK8stkjFzFBN0sA8dyl_TwdYRs/* $VF was: $r8$lambda$DCK8stkjFzFBN0sA8dyl_TwdYRs*/(MainActivity var0) {
      return laneModel_delegate$lambda$10(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$Df2e9zhoNRLcYRoRhogA53Him7Q(MainActivity var0) {
      scheduleReconnect$lambda$161(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$DnVNUVsfgSFQCJHJaTO_yPkZaAw/* $VF was: $r8$lambda$DnVNUVsfgSFQCJHJaTO_yPkZaAw*/(MainActivity var0, View var1) {
      wireControls$lambda$143(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$DzNDdBy5aodWRSLBdJ41_o_OQeg/* $VF was: $r8$lambda$DzNDdBy5aodWRSLBdJ41_o_OQeg*/(MainActivity var0) {
      ideasTick$lambda$190(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$EeNFnBeYCUMFXlPF_HhOU6NDfDY/* $VF was: $r8$lambda$EeNFnBeYCUMFXlPF_HhOU6NDfDY*/(MainActivity var0, File var1) {
      return onCreate$lambda$58(var0, var1);
   }

   // $VF: synthetic method
   public static Triple $r8$lambda$Ek2A2lTBfKFvfgADimmPMXB9bsU(MainActivity var0) {
      return onCreate$lambda$45(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Fz3hRxCne3okh_l7vxsh4B51xFg/* $VF was: $r8$lambda$Fz3hRxCne3okh_l7vxsh4B51xFg*/(MainActivity var0, JSONObject var1) {
      return setDestinationNow$lambda$181(var0, var1);
   }

   // $VF: synthetic method
   public static String $r8$lambda$G5OumP5KtqPxrDHQ4y84KWpBYJs(MainActivity var0) {
      return onCreate$lambda$55(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$G6lsFDhoELziNtJPrTmlJPUxp6k(boolean var0, boolean var1, MainActivity var2, JSONObject var3) {
      return sendSaver$lambda$187(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static OkHttpClient $r8$lambda$HGVpTPYyUbW_JdLN8YQ5xcbbF2w() {
      return storageHttp_delegate$lambda$162();
   }

   // $VF: synthetic method
   public static void $r8$lambda$HcjElX10KErT57iPxmaCUS1GG_E(MainActivity var0) {
      maskIntent$lambda$20(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$HjJrOx7oBMqyAXlv8YgkQSjw8VU(MainActivity var0, ObjectRef var1, String var2, boolean var3) {
      return onCreate$lambda$66(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static TextView $r8$lambda$I4HlSLa5PJEHB8jmGjMOkiPcn_Q(MainActivity var0) {
      return gpuBanner_delegate$lambda$8(var0);
   }

   // $VF: synthetic method
   public static SceneView $r8$lambda$INH_joGavraeZvTsq9U28KGSWv8(MainActivity var0) {
      return onCreate$lambda$49(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$JOanpD1JixHewDaWP1bmxUw6sgU(MainActivity var0) {
      return syncScene$lambda$168$lambda$167(var0);
   }

   // $VF: synthetic method
   public static Pair $r8$lambda$K0k1lBJ9k_lUbtGFVaWiaF1Lz5k(MainActivity var0) {
      return onCreate$lambda$56(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$KYyCdF9GpsHEojBG_cZ_5RxDC3c(MainActivity var0, String var1, boolean var2) {
      return onCreate$lambda$57(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$KbKV4gEyeFewmT7vvcM_1LJKWZA/* $VF was: $r8$lambda$KbKV4gEyeFewmT7vvcM_1LJKWZA*/(JSONObject var0) {
      return onCreate$pushTabletMode$lambda$61(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$Lg4YmEk8MMXszNR3vBDBeTI21xM(MainActivity var0, String var1, View var2) {
      onCreate$lambda$127$lambda$125$lambda$124(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$MWK0_mFYiSSaySVSyJu4MVg_SCE/* $VF was: $r8$lambda$MWK0_mFYiSSaySVSyJu4MVg_SCE*/(MainActivity var0, JSONObject var1) {
      return wireControls$lambda$146$lambda$145(var0, var1);
   }

   // $VF: synthetic method
   public static List $r8$lambda$MduX3OkqTORRf1kthJIdyO4X8qg(MainActivity var0) {
      return floating_delegate$lambda$172(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$Mh25gyY_YxKvq_vF9HpJY_p86pE/* $VF was: $r8$lambda$Mh25gyY_YxKvq_vF9HpJY_p86pE*/(MainActivity var0, View var1) {
      wireControls$lambda$144(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$MvtV5nwTeAA10EYgTjbmDgOf1Wg(MainActivity var0, String var1) {
      onCreate$lambda$69$lambda$68(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$NX_429s_ZStQ48BP9ulGgY240f0/* $VF was: $r8$lambda$NX_429s_ZStQ48BP9ulGgY240f0*/(
      MainActivity var0, String var1, Link.Auth var2, int var3
   ) {
      connectTo$lambda$157$lambda$156(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$PYyZohmMAP5TogpHQATte9dolew(MainActivity var0, IntRef var1, LongRef var2, View var3, MotionEvent var4) {
      return setupMapFrame$lambda$215(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static void $r8$lambda$Q4uJ2lObbR_1LiqWI6NPto4Do5A(MainActivity var0, View var1) {
      onCreate$lambda$104$lambda$103(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$QMk0HSTKU6nAg87xBD4Ng3TkT7U(String var0, MainActivity var1, JSONObject var2) {
      return doorCommand$lambda$212(var0, var1, var2);
   }

   // $VF: synthetic method
   public static LaneBrain.Road $r8$lambda$QUWjt_SrCwumNYzSli3q4ssV9jI(MainActivity var0) {
      return onCreate$lambda$51(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Qp8tFXaZhhpfo_X5ouZf5LhGwwA/* $VF was: $r8$lambda$Qp8tFXaZhhpfo_X5ouZf5LhGwwA*/(MainActivity var0) {
      return startHudEdit$lambda$225(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$S5ADl8gDHgHzxsyfobpZnWwPU5c(MainActivity var0, View var1) {
      onCreate$lambda$111$lambda$110(var0, var1);
   }

   // $VF: synthetic method
   public static String $r8$lambda$SdIBudNho6RlmBuNQP9l3mmveOA(MainActivity var0) {
      return onCreate$lambda$54(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$Sl3I1D7Uh8g6DBmm_Po3sS3nL98(
      MainActivity var0, View var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9
   ) {
      wireControls$lambda$140(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9);
   }

   // $VF: synthetic method
   public static void $r8$lambda$T5ecypjInvCygepbZ9TpoKMuXaA(
      MainActivity var0, View var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9
   ) {
      layoutHud$lambda$224(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9);
   }

   // $VF: synthetic method
   public static void $r8$lambda$UOVnysVC7nLSlE0NuIxzZVFYN_8/* $VF was: $r8$lambda$UOVnysVC7nLSlE0NuIxzZVFYN_8*/(MainActivity var0, ActivityResult var1) {
      destLauncher$lambda$12(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$VNFhSoRaas_DQNx5kuh02Wgk7tc(MainActivity var0, DialogInterface var1) {
      onCreate$lambda$31$lambda$30(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$Ve8G98ph1d6aKriZ7UdcYdR6PqM(MainActivity var0) {
      discover$lambda$153(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$Vu7xwS8UUXDbNLdoAZqOQmNFOBs(String var0) {
      return onCreate$lambda$78(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$WgzZx8DnHhwkyEyNQgUUc2K__Uw/* $VF was: $r8$lambda$WgzZx8DnHhwkyEyNQgUUc2K__Uw*/(
      File var0, String var1, UncaughtExceptionHandler var2, Thread var3, Throwable var4
   ) {
      installCrashLog$lambda$13(var0, var1, var2, var3, var4);
   }

   // $VF: synthetic method
   public static void $r8$lambda$YFL2i89c9j3JV_WYzJTw0Ds86Is(String var0, String var1, MainActivity var2) {
      shareIntent$lambda$18(var0, var1, var2);
   }

   // $VF: synthetic method
   public static CharSequence $r8$lambda$YlA_RWKQNEtA5DE0d7PtEtiRv_U/* $VF was: $r8$lambda$YlA_RWKQNEtA5DE0d7PtEtiRv_U*/(JSONObject var0) {
      return syncFavBar$lambda$182(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$ZWo7gOHSWL96Az_63AmdqEpSOnQ/* $VF was: $r8$lambda$ZWo7gOHSWL96Az_63AmdqEpSOnQ*/(MainActivity var0, String var1) {
      fetchStorage$lambda$165(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$_wlGrL9DdIPPQiY_ND_t_eSS5Zk/* $VF was: $r8$lambda$_wlGrL9DdIPPQiY_ND_t_eSS5Zk*/(
      MainActivity var0, SharedPreferences var1, View var2
   ) {
      onCreate$lambda$59(var0, var1, var2);
   }

   // $VF: synthetic method
   public static TextView $r8$lambda$a1cxIVHfZ6h9NoJHOSpEMxEtfE4(MainActivity var0) {
      return stallBanner_delegate$lambda$4(var0);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$bhOlf2Bs_kT_yXchkxnmrwWwrSQ(LongRef var0, MainActivity var1, View var2, MotionEvent var3) {
      return wireControls$lambda$136(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$bn7XqEtypsYwLIi1SAKcHzEpiCw(MainActivity var0, String var1) {
      return voiceNavigate$lambda$208(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$cD3ejtcBMj2xvGCx2w_Dzex2px0/* $VF was: $r8$lambda$cD3ejtcBMj2xvGCx2w_Dzex2px0*/(MainActivity var0) {
      return startHudEdit$lambda$226(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$cY3S9THfVX_hWgEgbNoaOV3_aQw/* $VF was: $r8$lambda$cY3S9THfVX_hWgEgbNoaOV3_aQw*/(MainActivity var0, View var1) {
      wireControls$lambda$141(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$cYDMgLI09Wlh57C7CNdozyTG3hU(boolean var0) {
      return startMaskEdit$lambda$21(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$cYJHebfIe6AluJLfo_PHK4axt58(MainActivity var0) {
      onCreate$lambda$73$lambda$72(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$cor20Md8kwtj8csCe5ThSY8T_Ag(MainActivity var0) {
      onNewIntent$lambda$14(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$dD8PWtLWShacS93JDVyPO7NEnKE(MainActivity var0, float var1, float var2) {
      return syncScene$lambda$168$lambda$166(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$dJbjqFzSOAO_XB1eVx_WOHhL_Qc/* $VF was: $r8$lambda$dJbjqFzSOAO_XB1eVx_WOHhL_Qc*/(
      MainActivity var0, String var1, JSONObject var2
   ) {
      return navigateTo$lambda$177$lambda$176(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$e9QKJ9P6hfgXHajSRWqYJMqm6qo(Map var0, Function0 var1, String var2) {
      return previewPlace$lambda$178(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$eykZKE3PxBcJjbfkPd8dSaS_fa4/* $VF was: $r8$lambda$eykZKE3PxBcJjbfkPd8dSaS_fa4*/(String var0, int var1, MainActivity var2) {
      connectTo$lambda$157(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$fMgz_2wXxTc_MaA0kMn7QWxvvWg/* $VF was: $r8$lambda$fMgz_2wXxTc_MaA0kMn7QWxvvWg*/(MainActivity var0, ObjectRef var1) {
      shareIntent$lambda$18$lambda$17(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$fQg5MPs29j0IrKfRRwID6bvSQL8(MainActivity var0, String var1, String var2, double var3, double var5) {
      return driveTo$lambda$179(var0, var1, var2, var3, var5);
   }

   // $VF: synthetic method
   public static List $r8$lambda$fZAHCLgtue0eX7p4Ef_k27RovC8/* $VF was: $r8$lambda$fZAHCLgtue0eX7p4Ef_k27RovC8*/(MainActivity var0) {
      return onCreate$lambda$52(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$gED8elugZpn_BuO8BUSQvAm7rIA(MainActivity var0, JSONObject var1) {
      return onCreate$lambda$43(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$gI6wUg41v1D7Zop5kTH9wfosnOg(MainActivity var0) {
      return onCreate$lambda$107(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$gXtB0R_AZPgIQhZVqT9sqnf4eR0(MainActivity var0) {
      return onCreate$lambda$66$lambda$65$lambda$64(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$h_9KblU7VF2w2G7q_7K09b8urqg(MainActivity var0, Discovery.Found var1) {
      checkUsbReturn$lambda$160$lambda$159(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$hrauTO4Kmcmv0Uzfp2N2IJQmL_4(String var0, MainActivity var1, boolean var2, ObjectRef var3) {
      onCreate$lambda$66$lambda$65(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static LaneShots $r8$lambda$iltEZ4xRH8JMTsH8xJd5dqdTrbo(MainActivity var0) {
      return laneShots_delegate$lambda$9(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$jV1RsmF_rOXDZtwhTAPT4Stj8SY(String var0) {
      return onCreate$lambda$74(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$jiu9wzfnpt8nwYHv7CMnrcRjO5M(MainActivity var0, String var1, String var2, String var3) {
      return navigateTo$lambda$177(var0, var1, var2, var3);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$kL3eR_AvQOov3wGU_ac0Bh_j_Wc/* $VF was: $r8$lambda$kL3eR_AvQOov3wGU_ac0Bh_j_Wc*/(MainActivity var0, View var1) {
      return wireControls$lambda$147(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$kWRAu5Bz_jR34VJIQWi0_nOEE4Q/* $VF was: $r8$lambda$kWRAu5Bz_jR34VJIQWi0_nOEE4Q*/(MainActivity var0) {
      setupMapFrame$lambda$217(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$lNT1DEP0V4pKL2kTdpBSJYE73Ws(MainActivity var0, View var1) {
      onCreate$lambda$96$lambda$95(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$lPn6ukxHp_GO0bpdyd6mnJyWJyE(MainActivity var0) {
      return pickBackup$lambda$24$lambda$23(var0);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$lZ2Sd1D_BmYmiSMszUWOH53nPqU(MainActivity var0, View var1, MotionEvent var2) {
      return wireControls$lambda$134(var0, var1, var2);
   }

   // $VF: synthetic method
   public static void $r8$lambda$lb53moPhP7A1hQ7pSBtRnMKf3vA(MainActivity var0) {
      onCreate$lambda$31(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$nJsnVA5W6IY0mTtp90p0Hr9uk90(MainActivity var0, Uri var1) {
      pickBackup$lambda$24(var0, var1);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$nbm8Vo5ZQcXQsfo_T9eS5V_U5dI/* $VF was: $r8$lambda$nbm8Vo5ZQcXQsfo_T9eS5V_U5dI*/(MainActivity var0) {
      return onCreate$lambda$106(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$obY_j3AFHKMeWUA8iRUvZr9gJM0(MainActivity var0, ActivityResult var1) {
      voiceLauncher$lambda$192(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$pn5qc3DyNfcvahEntD1cwVgp0Io(MainActivity var0, LaneBrain.Event var1) {
      return onCreate$lambda$46(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$q_fOlMlNtUX2uozFZS1X8SZ95vU/* $VF was: $r8$lambda$q_fOlMlNtUX2uozFZS1X8SZ95vU*/(MainActivity var0, View var1) {
      onCreate$lambda$84$lambda$83(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$qpKS6vHV_2GibsyNfLqEaCSIahg(MainActivity var0, int var1, JSONObject var2) {
      return wireControls$lambda$133$lambda$132(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$qsN8zJUTIxq5r1emuNcQODmc8aI(MainActivity var0) {
      return connectTo$lambda$157$lambda$156$lambda$155(var0);
   }

   // $VF: synthetic method
   public static boolean $r8$lambda$qu519tNdLppvcShSTJzxQCnUqEQ(MainActivity var0, View var1, MotionEvent var2) {
      return wireControls$lambda$133(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$rSeeLrt1_rEEvQ9rtTmLMiwD0fw(MainActivity var0) {
      return onCreate$lambda$73(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$rjkY1Jz4A79ePIVHZNTq5EHQhDM(int var0, int var1) {
      return onCreate$lambda$79(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$tUo4y352YWqt4Xyd_Sw_T6S0_4o/* $VF was: $r8$lambda$tUo4y352YWqt4Xyd_Sw_T6S0_4o*/(MainActivity var0, View var1) {
      wireControls$lambda$135(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$ufF2DMzhEZYw2iclsByALsA9Wp8(int var0, int var1) {
      return onCreate$lambda$75(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$v1UctNv_jq0_aFSKtOWYwI_Srh0/* $VF was: $r8$lambda$v1UctNv_jq0_aFSKtOWYwI_Srh0*/(MainActivity var0, View var1) {
      wireControls$lambda$142(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$xahwK0JcxoKqaleY5sg0Hq__Hws/* $VF was: $r8$lambda$xahwK0JcxoKqaleY5sg0Hq__Hws*/(MainActivity var0, View var1) {
      onCreate$lambda$100$lambda$99(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$yAzHmyDU3pOPubB5og6QknmWSzY(MainActivity var0, View var1) {
      onCreate$lambda$88$lambda$87(var0, var1);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$yGLk9vauE4QcL7RuwhEJmAiP23o(MainActivity var0, int var1, int var2) {
      return onCreate$lambda$71(var0, var1, var2);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$yfn4fokP6oFUwObagvvcJM02jPY(MainActivity var0) {
      return onCreate$lambda$37(var0);
   }

   // $VF: synthetic method
   public static void $r8$lambda$ys_E9pyTbUOiIfRcHZSCUioxnlk(MainActivity var0) {
      checkUsbReturn$lambda$160(var0);
   }

   // $VF: synthetic method
   public static Unit $r8$lambda$zIKjDlOdQhb_HyU39cQMxx2z1xQ(MainActivity var0, Image var1) {
      return onCreate$lambda$77$lambda$76(var0, var1);
   }

   // $VF: synthetic method
   public static void $r8$lambda$zRciZgju71n5sXugF_ufGRm5OAk/* $VF was: $r8$lambda$zRciZgju71n5sXugF_ufGRm5OAk*/(
      MainActivity var0, View var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9
   ) {
      setupMapFrame$lambda$216(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9);
   }

   // $VF: synthetic method
   public static void $r8$lambda$zln95vbsLBAXEBiyYDmOVzkwu7k(BooleanRef var0, MainActivity var1) {
      wireControls$lambda$139$lambda$137(var0, var1);
   }

   public MainActivity() {
      this.telemetry = new Telemetry();
      this.laneMarks = new LaneMarks();
      this.stallBanner$delegate = LazyKt.lazy(new MainActivity$$ExternalSyntheticLambda112(this));
      this.gpuBanner$delegate = LazyKt.lazy(new MainActivity$$ExternalSyntheticLambda113(this));
      this.carMemory = new CarMemory();
      this.laneShots$delegate = LazyKt.lazy(new MainActivity$$ExternalSyntheticLambda114(this));
      this.laneModel$delegate = LazyKt.lazy(new MainActivity$$ExternalSyntheticLambda115(this));
      this.laneTracker = new LaneBrain();
      this.laneFeed = new LaneFeed();
      this.laneAhead = new LaneAhead();
      this.tintDrawable = new GradientDrawable();
      this.port = 8090;
      this.linkIf = "";
      this.lastStatus = "";
      this.lastModelFrame = -1L;
      ActivityResultLauncher var1 = this.registerForActivityResult(new StartActivityForResult(), new MainActivity$$ExternalSyntheticLambda116(this));
      Intrinsics.checkNotNullExpressionValue(var1, "registerForActivityResult(...)");
      this.destLauncher = var1;
      this.lastCrash = "";
      var1 = this.registerForActivityResult(new OpenDocument(), new MainActivity$$ExternalSyntheticLambda1(this));
      Intrinsics.checkNotNullExpressionValue(var1, "registerForActivityResult(...)");
      this.pickBackup = var1;
      this.backupArmed = true;
      this.visible = true;
      this.storageHttp$delegate = LazyKt.lazy(new MainActivity$$ExternalSyntheticLambda2());
      this.lastWs = -1.0F;
      this.floating$delegate = LazyKt.lazy(new MainActivity$$ExternalSyntheticLambda3(this));
      this.favKey = "";
      var1 = this.registerForActivityResult(new StartActivityForResult(), new MainActivity$$ExternalSyntheticLambda4(this));
      Intrinsics.checkNotNullExpressionValue(var1, "registerForActivityResult(...)");
      this.voiceLauncher = var1;
      this.lastNavDest = "";
      this.lastHomeKey = "";
      this.uiTick = new Anon_uiTick_1(this);
      Pair var17 = TuplesKt.to("speedBox", "show_speed");
      Pair var19 = TuplesKt.to("maxCard", "show_max");
      Pair var12 = TuplesKt.to("slc", "speed_limit");
      Pair var13 = TuplesKt.to("wheel", "wheel_button");
      Pair var9 = TuplesKt.to("egpu", "egpu_status");
      Pair var16 = TuplesKt.to("personality", "personality_button");
      Pair var2 = TuplesKt.to("dm", "dm_icon");
      Pair var18 = TuplesKt.to("roadName", "road_name");
      Pair var6 = TuplesKt.to("gauge", "aether_gauge");
      Pair var25 = TuplesKt.to("navCard", "nav_card");
      Pair var20 = TuplesKt.to("slowCard", "slow_card");
      Pair var15 = TuplesKt.to("laneCard", "lane_card");
      Pair var21 = TuplesKt.to("blinkCard", "blink_card");
      Pair var4 = TuplesKt.to("gpsCard", "gps_card");
      Pair var8 = TuplesKt.to("nudgeHint", "nudge_hint");
      Pair var10 = TuplesKt.to("weather", "weather");
      Pair var11 = TuplesKt.to("compass", "compass");
      Pair var3 = TuplesKt.to("devMetrics", "dev_metrics");
      Pair var14 = TuplesKt.to("clock", "clock");
      Pair var7 = TuplesKt.to("modeText", "mode_text");
      Pair var5 = TuplesKt.to("devSidebar", "dev_sidebar");
      Pair var22 = TuplesKt.to("pipLeft", "side_views");
      this.hudSettingKeys = MapsKt.mapOf(
         new Pair[]{
            var17,
            var19,
            var12,
            var13,
            var9,
            var16,
            var2,
            var18,
            var6,
            var25,
            var20,
            var15,
            var21,
            var4,
            var8,
            var10,
            var11,
            var3,
            var14,
            var7,
            var5,
            var22,
            TuplesKt.to("pipRight", "side_views")
         }
      );
      Paint var27 = new Paint();
      ColorMatrix var26 = new ColorMatrix();
      var26.setSaturation(0.0F);
      var27.setColorFilter((ColorFilter)(new ColorMatrixColorFilter(var26)));
      this.greyPaint = var27;
   }

   private final void applyHud() {
      if (this.hud != null) {
         this.layoutHud();
         this.applyHudGrey();
         boolean var2;
         if (this.hudEditor != null) {
            var2 = true;
         } else {
            var2 = false;
         }

         boolean var1;
         if (this.lastHudEditing && !var2) {
            var1 = true;
         } else {
            var1 = false;
         }

         this.lastHudEditing = var2;
         HudLayout var5 = this.hud;
         HudLayout var4 = var5;
         if (var5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("hud");
            var4 = null;
         }

         for (HudLayout.Item var8 : var4.getItems()) {
            FrameLayout var13 = this.root;
            FrameLayout var9 = var13;
            if (var13 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("root");
               var9 = null;
            }

            View var7 = var9.findViewById(var8.getId());
            if (var7 != null) {
               var5 = this.hud;
               var4 = var5;
               if (var5 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("hud");
                  var4 = null;
               }

               boolean var3 = var4.enabled(var8.getKey());
               if (var2) {
                  String var17 = this.hudSettingKeys.get(var8.getKey());
                  if (var17 != null && var3) {
                     UiSettings var15 = this.settings;
                     UiSettings var11 = var15;
                     if (var15 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("settings");
                        var11 = null;
                     }

                     if (!var11.get(var17)) {
                        UiSettings var16 = this.settings;
                        UiSettings var12 = var16;
                        if (var16 == null) {
                           Intrinsics.throwUninitializedPropertyAccessException("settings");
                           var12 = null;
                        }

                        var12.set(var17, true);
                     }
                  }

                  var7.setVisibility(0);
                  var7.setAlpha(1.0F);
               } else {
                  if (var1) {
                     var7.setAlpha(1.0F);
                  }

                  if (!var3) {
                     var7.setVisibility(8);
                  }
               }
            }
         }
      }
   }

   private final void applyHudGrey() {
      UiSettings var3 = this.settings;
      UiSettings var2 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("settings");
         var2 = null;
      }

      boolean var1 = var2.get("hud_grey");
      if (!Intrinsics.areEqual(var1, this.hudGrey)) {
         this.hudGrey = var1;
         HudLayout var12 = this.hud;
         HudLayout var8 = var12;
         if (var12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("hud");
            var8 = null;
         }

         Iterable var9 = var8.getItems();
         var var6 = new ArrayList();

         for (HudLayout.Item var7 : var9) {
            FrameLayout var4 = this.root;
            FrameLayout var13 = var4;
            if (var4 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("root");
               var13 = null;
            }

            View var14 = var13.findViewById(var7.getId());
            if (var14 != null) {
               var6.add(var14);
            }
         }

         List var10 = var6;

         for (View var11 : CollectionsKt.plus(
            var10, CollectionsKt.listOfNotNull(new View[]{this.findViewById(R.id.alertBox), this.findViewById(R.id.alertTint)})
         )) {
            if (var1) {
               var11.setLayerType(2, this.greyPaint);
            } else {
               var11.setLayerType(0, null);
            }
         }
      }
   }

   private final void applyRootBg() {
      FrameLayout var5 = this.root;
      FrameLayout var4 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("root");
         var4 = null;
      }

      int var1;
      if (!this.sceneOn) {
         var1 = -16777216;
      } else {
         short var2;
         short var3;
         short var6;
         if (this.night) {
            var3 = 13;
            var6 = 18;
            var2 = 11;
         } else {
            var3 = 245;
            var6 = 247;
            var2 = 244;
         }

         var1 = Color.rgb(var2, var3, var6);
      }

      var4.setBackgroundColor(var1);
   }

   private final void applySaver() {
      if (this.saverPill != null) {
         TextView var3 = this.saverPill;
         TextView var2 = var3;
         if (var3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("saverPill");
            var2 = null;
         }

         byte var1;
         if (this.getSaver()) {
            var1 = 0;
         } else {
            var1 = 8;
         }

         var2.setVisibility(var1);
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private final void bookmarkNow() {
      long var11;
      Telemetry var18;
      JSONObject var19;
      double[] var131;
      label724: {
         try {
            var18 = this.telemetry;
            var11 = System.currentTimeMillis();
            var19 = new JSONObject();
            if (RtkPosition.INSTANCE.fast(var11)) {
               var131 = RtkPosition.INSTANCE.now(var11, var18.getVEgo());
               break label724;
            }
         } catch (Exception var105) {
            Context var15 = (Context)this;
            String var16 = var105.getMessage();
            Toast.makeText(var15, "Couldn't bookmark: " + var16, 1).show();
            return;
         }

         var131 = null;
      }

      double var1;
      if (var131 != null) {
         var1 = var131[0];
      } else {
         try {
            var1 = var18.getGpsLat();
         } catch (Exception var104) {
            Context var132 = (Context)this;
            String var264 = var104.getMessage();
            Toast.makeText(var132, "Couldn't bookmark: " + var264, 1).show();
            return;
         }
      }

      JSONObject var266;
      try {
         var266 = var19.put("lat", var1);
      } catch (Exception var103) {
         Context var133 = (Context)this;
         String var265 = var103.getMessage();
         Toast.makeText(var133, "Couldn't bookmark: " + var265, 1).show();
         return;
      }

      if (var131 != null) {
         var1 = var131[1];
      } else {
         try {
            var1 = var18.getGpsLon();
         } catch (Exception var102) {
            Context var134 = (Context)this;
            String var267 = var102.getMessage();
            Toast.makeText(var134, "Couldn't bookmark: " + var267, 1).show();
            return;
         }
      }

      try {
         var266.put("lon", var1);
      } catch (Exception var101) {
         Context var135 = (Context)this;
         String var268 = var101.getMessage();
         Toast.makeText(var135, "Couldn't bookmark: " + var268, 1).show();
         return;
      }

      String var138;
      if (var131 != null) {
         try {
            String var137 = RtkPosition.INSTANCE.getKind();
            var1 = bookmarkNow$r1(RtkPosition.INSTANCE.getAccM() * 39.37);
            StringBuilder var270 = new StringBuilder();
            var138 = var270.append("RTK ").append(var137).append(" ±").append(var1).append(" in").toString();
         } catch (Exception var100) {
            Context var136 = (Context)this;
            String var269 = var100.getMessage();
            Toast.makeText(var136, "Couldn't bookmark: " + var269, 1).show();
            return;
         }
      } else {
         var138 = "comma GPS";
      }

      try {
         var19.put("position", var138);
         int var6 = Math.round(var18.getVEgo() * 2.23694F);
         StringBuilder var140 = new StringBuilder();
         var19.put("speed", var140.append(var6).append(" mph").toString()).put("speed_ms", var18.getVEgo());
      } catch (Exception var99) {
         Context var139 = (Context)this;
         String var271 = var99.getMessage();
         Toast.makeText(var139, "Couldn't bookmark: " + var271, 1).show();
         return;
      }

      try {
         var141 = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
      } catch (Exception var27) {
         var141 = "";
      }

      LaneBrain.Road var17;
      LaneBrain var20;
      try {
         var19.put("app", var141);
         var20 = this.laneTracker;
         var17 = var20.getRoad();
      } catch (Exception var98) {
         Context var142 = (Context)this;
         String var272 = var98.getMessage();
         Toast.makeText(var142, "Couldn't bookmark: " + var272, 1).show();
         return;
      }

      label690: {
         if (var17 != null) {
            try {
               var144 = var17.getName();
            } catch (Exception var96) {
               Context var143 = (Context)this;
               String var273 = var96.getMessage();
               Toast.makeText(var143, "Couldn't bookmark: " + var273, 1).show();
               return;
            }

            if (var144 != null) {
               boolean var111;
               label685: {
                  label684: {
                     try {
                        var146 = var144;
                        if (var146.length() == 0) {
                           break label684;
                        }
                     } catch (Exception var97) {
                        Context var145 = (Context)this;
                        String var274 = var97.getMessage();
                        Toast.makeText(var145, "Couldn't bookmark: " + var274, 1).show();
                        return;
                     }

                     var111 = false;
                     break label685;
                  }

                  var111 = true;
               }

               if (var111) {
                  try {
                     var146 = var17.getKind();
                  } catch (Exception var95) {
                     Context var147 = (Context)this;
                     String var275 = var95.getMessage();
                     Toast.makeText(var147, "Couldn't bookmark: " + var275, 1).show();
                     return;
                  }
               }

               try {
                  var277 = var146;
               } catch (Exception var94) {
                  Context var148 = (Context)this;
                  String var276 = var94.getMessage();
                  Toast.makeText(var148, "Couldn't bookmark: " + var276, 1).show();
                  return;
               }

               var149 = var277;
               if (var277 != null) {
                  break label690;
               }
            }
         }

         try {
            var149 = var18.getRoadName();
         } catch (Exception var93) {
            Context var150 = (Context)this;
            String var278 = var93.getMessage();
            Toast.makeText(var150, "Couldn't bookmark: " + var278, 1).show();
            return;
         }
      }

      int var7;
      JSONObject var21;
      try {
         var19.put("place", var149);
         var21 = new JSONObject();
         Pair var152 = var20.laneFromLeft();
         int var112 = ((Number)var152.component1()).intValue();
         var1 = ((Number)var152.component2()).doubleValue();
         Pair var153 = var20.laneCount();
         var7 = ((Number)var153.component1()).intValue();
         var280 = (String)var153.component2();
         var154 = var21.put("label", var20.label()).put("lane_from_left", var112);
      } catch (Exception var92) {
         Context var151 = (Context)this;
         String var279 = var92.getMessage();
         Toast.makeText(var151, "Couldn't bookmark: " + var279, 1).show();
         return;
      }

      double var3 = 100;

      try {
         var154.put("sure", bookmarkNow$r1(var1 * var3)).put("lanes", var7).put("lanes_from", var280);
         var131 = var20.marg(-1);
         ArrayList var282 = new ArrayList(var131.length);
         var283 = var282;
         var7 = var131.length;
      } catch (Exception var91) {
         Context var155 = (Context)this;
         String var281 = var91.getMessage();
         Toast.makeText(var155, "Couldn't bookmark: " + var281, 1).show();
         return;
      }

      for (int var113 = 0; var113 < var7; var113++) {
         try {
            var283.add(bookmarkNow$r1(var131[var113] * var3));
         } catch (Exception var90) {
            Context var157 = (Context)this;
            String var284 = var90.getMessage();
            Toast.makeText(var157, "Couldn't bookmark: " + var284, 1).show();
            return;
         }
      }

      try {
         var159 = var283;
      } catch (Exception var89) {
         Context var158 = (Context)this;
         String var285 = var89.getMessage();
         Toast.makeText(var158, "Couldn't bookmark: " + var285, 1).show();
         return;
      }

      ArrayList var395;
      try {
         Collection var287 = var159;
         JSONArray var161 = new JSONArray(var287);
         var162 = var21.put("lanes_left_probabilities", var161);
         var288 = var20.marg(1);
         var395 = new ArrayList(var288.length);
         var395 = var395;
         var7 = var288.length;
      } catch (Exception var88) {
         Context var160 = (Context)this;
         String var286 = var88.getMessage();
         Toast.makeText(var160, "Couldn't bookmark: " + var286, 1).show();
         return;
      }

      for (int var114 = 0; var114 < var7; var114++) {
         try {
            var395.add(bookmarkNow$r1(var288[var114] * var3));
         } catch (Exception var87) {
            Context var163 = (Context)this;
            String var289 = var87.getMessage();
            Toast.makeText(var163, "Couldn't bookmark: " + var289, 1).show();
            return;
         }
      }

      try {
         var291 = var395;
      } catch (Exception var86) {
         Context var164 = (Context)this;
         String var290 = var86.getMessage();
         Toast.makeText(var164, "Couldn't bookmark: " + var290, 1).show();
         return;
      }

      try {
         Collection var396 = var291;
         JSONArray var293 = new JSONArray(var396);
         var162.put("lanes_right_probabilities", var293);
         var294 = var20.getWhy();
      } catch (Exception var85) {
         Context var165 = (Context)this;
         String var292 = var85.getMessage();
         Toast.makeText(var165, "Couldn't bookmark: " + var292, 1).show();
         return;
      }

      boolean var9 = false;

      ArrayList var23;
      try {
         ArrayList var167 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var294, 10));
         var23 = var167;
      } catch (Exception var84) {
         Context var166 = (Context)this;
         String var295 = var84.getMessage();
         Toast.makeText(var166, "Couldn't bookmark: " + var295, 1).show();
         return;
      }

      Iterable var168 = var294;
      boolean var8 = false;

      try {
         var397 = var168.iterator();
      } catch (Exception var83) {
         Context var169 = (Context)this;
         String var296 = var83.getMessage();
         Toast.makeText(var169, "Couldn't bookmark: " + var296, 1).show();
         return;
      }

      while (true) {
         boolean var10;
         try {
            var10 = var397.hasNext();
         } catch (Exception var81) {
            Context var170 = (Context)this;
            String var297 = var81.getMessage();
            Toast.makeText(var170, "Couldn't bookmark: " + var297, 1).show();
            break;
         }

         if (!var10) {
            try {
               var173 = var23;
            } catch (Exception var80) {
               Context var172 = (Context)this;
               String var299 = var80.getMessage();
               Toast.makeText(var172, "Couldn't bookmark: " + var299, 1).show();
               break;
            }

            try {
               Collection var175 = var173;
               JSONArray var301 = new JSONArray(var175);
               var21.put("why", var301);
               LaneBrain.RoadModel var176 = var20.getModel();
               var266 = new JSONObject();
               var21.put(
                  "road_model",
                  var266.put("lanes", var176.getLanes())
                     .put("from", var176.getSrc())
                     .put("conf", var176.getConf())
                     .put("extra_left", var176.getExtraL())
                     .put("extra_right", var176.getExtraR())
                     .put("kind_left", var176.getKindL())
                     .put("kind_right", var176.getKindR())
                     .put("tunnel", var176.getTunnel())
                     .put("bridge", var176.getBridge())
               );
               var303 = var20.getInfo();
            } catch (Exception var79) {
               Context var174 = (Context)this;
               String var300 = var79.getMessage();
               Toast.makeText(var174, "Couldn't bookmark: " + var300, 1).show();
               break;
            }

            if (var303 != null) {
               try {
                  JSONObject var178 = new JSONObject();
                  var21.put(
                     "road_data",
                     var178.put("hpms_lanes", var303.getHpmsLanes())
                        .put("hpms_name", var303.getHpmsName())
                        .put("hpms_ramp", var303.getHpmsRamp())
                        .put("hpms_sure", var303.getHpmsSure())
                        .put("extra_why", var303.getExtraWhy())
                        .put("route_straight_lanes", var303.getNavStraight())
                  );
               } catch (Exception var78) {
                  Context var177 = (Context)this;
                  String var304 = var78.getMessage();
                  Toast.makeText(var177, "Couldn't bookmark: " + var304, 1).show();
                  break;
               }
            }

            if (var17 != null) {
               try {
                  JSONObject var180 = new JSONObject();
                  var21.put(
                     "map_street",
                     var180.put("id", var17.getId())
                        .put("name", var17.getName())
                        .put("kind", var17.getKind())
                        .put("oneway", var17.getOneway())
                        .put("highway", var17.getHighway())
                        .put("lanes_tag", var17.getLanesOurWay())
                  );
               } catch (Exception var77) {
                  Context var179 = (Context)this;
                  String var305 = var77.getMessage();
                  Toast.makeText(var179, "Couldn't bookmark: " + var305, 1).show();
                  break;
               }
            }

            try {
               Iterable var182 = var20.getEvents();
               ArrayList var307 = new ArrayList();
               var308 = var307;
               var398 = var182.iterator();
            } catch (Exception var76) {
               Context var181 = (Context)this;
               String var306 = var76.getMessage();
               Toast.makeText(var181, "Couldn't bookmark: " + var306, 1).show();
               break;
            }

            while (true) {
               boolean var115;
               label595: {
                  label594: {
                     try {
                        if (!var398.hasNext()) {
                           break;
                        }

                        var375 = var398.next();
                        if (var11 - ((LaneBrain.Event)var375).getAtMs() < 120000L) {
                           break label594;
                        }
                     } catch (Exception var75) {
                        Context var183 = (Context)this;
                        String var309 = var75.getMessage();
                        Toast.makeText(var183, "Couldn't bookmark: " + var309, 1).show();
                        return;
                     }

                     var115 = false;
                     break label595;
                  }

                  var115 = true;
               }

               if (var115) {
                  try {
                     var308.add(var375);
                  } catch (Exception var74) {
                     Context var184 = (Context)this;
                     String var310 = var74.getMessage();
                     Toast.makeText(var184, "Couldn't bookmark: " + var310, 1).show();
                     return;
                  }
               }
            }

            try {
               var186 = var308;
            } catch (Exception var73) {
               Context var185 = (Context)this;
               String var311 = var73.getMessage();
               Toast.makeText(var185, "Couldn't bookmark: " + var311, 1).show();
               break;
            }

            try {
               var313 = var186;
            } catch (Exception var72) {
               Context var187 = (Context)this;
               String var312 = var72.getMessage();
               Toast.makeText(var187, "Couldn't bookmark: " + var312, 1).show();
               break;
            }

            var9 = false;

            try {
               ArrayList var189 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var313, 10));
               var376 = var189;
            } catch (Exception var71) {
               Context var188 = (Context)this;
               String var314 = var71.getMessage();
               Toast.makeText(var188, "Couldn't bookmark: " + var314, 1).show();
               break;
            }

            Iterable var190 = var313;
            var8 = false;

            try {
               var399 = var190.iterator();
            } catch (Exception var69) {
               Context var191 = (Context)this;
               String var315 = var69.getMessage();
               Toast.makeText(var191, "Couldn't bookmark: " + var315, 1).show();
               break;
            }

            while (true) {
               try {
                  if (!var399.hasNext()) {
                     break;
                  }

                  LaneBrain.Event var402 = (LaneBrain.Event)var399.next();
                  long var130 = (var11 - var402.getAtMs()) / 1000;
                  String var403 = var402.getText();
                  StringBuilder var406 = new StringBuilder();
                  var376.add(var406.append(var130).append("s ago: ").append(var403).toString());
               } catch (Exception var70) {
                  Context var192 = (Context)this;
                  String var316 = var70.getMessage();
                  Toast.makeText(var192, "Couldn't bookmark: " + var316, 1).show();
                  return;
               }
            }

            try {
               var194 = var376;
            } catch (Exception var68) {
               Context var193 = (Context)this;
               String var317 = var68.getMessage();
               Toast.makeText(var193, "Couldn't bookmark: " + var317, 1).show();
               break;
            }

            try {
               Collection var196 = var194;
               JSONArray var319 = new JSONArray(var196);
               var21.put("events_last_2_min", var319);
               var21.put("ahead", this.laneAhead.text(var20.getOdo(), false))
                  .put("ahead_stage", this.laneAhead.getStage().name())
                  .put("drive", var20.summary());
               var19.put("lane", var21);
               var197 = var18.navState();
            } catch (Exception var67) {
               Context var195 = (Context)this;
               String var318 = var67.getMessage();
               Toast.makeText(var195, "Couldn't bookmark: " + var318, 1).show();
               break;
            }

            if (var197 != null) {
               try {
                  var19.put("route", var197);
               } catch (Exception var66) {
                  Context var198 = (Context)this;
                  String var320 = var66.getMessage();
                  Toast.makeText(var198, "Couldn't bookmark: " + var320, 1).show();
                  break;
               }
            }

            try {
               var322 = Nav.INSTANCE.nextLanes();
            } catch (Exception var65) {
               Context var199 = (Context)this;
               String var321 = var65.getMessage();
               Toast.makeText(var199, "Couldn't bookmark: " + var321, 1).show();
               break;
            }

            if (var322 != null) {
               try {
                  Nav.Step var201 = (Nav.Step)var322.component1();
                  var1 = ((Number)var322.component2()).doubleValue();
                  var266 = new JSONObject();
                  var377 = var266.put("at_m", Math.round(var1))
                     .put("type", var201.getType())
                     .put("modifier", var201.getModifier())
                     .put("text", var201.getText());
                  Iterable var325 = var201.getLanes();
                  ArrayList var388 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var325, 10));
                  var391 = var388;
                  var389 = var325.iterator();
               } catch (Exception var63) {
                  Context var200 = (Context)this;
                  String var323 = var63.getMessage();
                  Toast.makeText(var200, "Couldn't bookmark: " + var323, 1).show();
                  break;
               }

               while (true) {
                  try {
                     if (!var389.hasNext()) {
                        break;
                     }

                     Nav.Lane var404 = (Nav.Lane)var389.next();
                     JSONObject var400 = new JSONObject();
                     JSONArray var407 = new JSONArray(var404.getArrows());
                     var391.add(var400.put("arrows", var407).put("ok", var404.getOk()).put("use", var404.getUse()));
                  } catch (Exception var64) {
                     Context var202 = (Context)this;
                     String var326 = var64.getMessage();
                     Toast.makeText(var202, "Couldn't bookmark: " + var326, 1).show();
                     return;
                  }
               }

               try {
                  var204 = var391;
               } catch (Exception var62) {
                  Context var203 = (Context)this;
                  String var327 = var62.getMessage();
                  Toast.makeText(var203, "Couldn't bookmark: " + var327, 1).show();
                  break;
               }

               try {
                  Collection var206 = var204;
                  JSONArray var329 = new JSONArray(var206);
                  var19.put("route_next_lanes", var377.put("lanes", var329));
               } catch (Exception var61) {
                  Context var205 = (Context)this;
                  String var328 = var61.getMessage();
                  Toast.makeText(var205, "Couldn't bookmark: " + var328, 1).show();
                  break;
               }
            }

            try {
               var390 = var18.getModel();
               JSONObject var208 = new JSONObject();
               var266 = var208.put("enabled", var18.getEnabled())
                  .put("active", var18.getActive())
                  .put("state", var18.getOpState())
                  .put("experimental", var18.getExperimentalMode());
               Iterable var209 = CollectionsKt.listOf(new String[]{var18.getAlertText1(), var18.getAlertText2()});
               ArrayList var378 = new ArrayList();
               var379 = var378;
               var392 = var209.iterator();
            } catch (Exception var59) {
               Context var207 = (Context)this;
               String var330 = var59.getMessage();
               Toast.makeText(var207, "Couldn't bookmark: " + var330, 1).show();
               break;
            }

            while (true) {
               boolean var116;
               label529: {
                  label528: {
                     try {
                        if (!var392.hasNext()) {
                           break;
                        }

                        var401 = var392.next();
                        if (((String)var401).length() > 0) {
                           break label528;
                        }
                     } catch (Exception var60) {
                        Context var210 = (Context)this;
                        String var332 = var60.getMessage();
                        Toast.makeText(var210, "Couldn't bookmark: " + var332, 1).show();
                        return;
                     }

                     var116 = false;
                     break label529;
                  }

                  var116 = true;
               }

               if (var116) {
                  try {
                     var379.add(var401);
                  } catch (Exception var58) {
                     Context var211 = (Context)this;
                     String var333 = var58.getMessage();
                     Toast.makeText(var211, "Couldn't bookmark: " + var333, 1).show();
                     return;
                  }
               }
            }

            try {
               var213 = var379;
            } catch (Exception var57) {
               Context var212 = (Context)this;
               String var334 = var57.getMessage();
               Toast.makeText(var212, "Couldn't bookmark: " + var334, 1).show();
               break;
            }

            float var5;
            label511: {
               try {
                  var266 = var266.put("alert", CollectionsKt.joinToString$default(var213, " / ", null, null, 0, null, null, 62, null));
                  if (var18.getVCruiseCluster() > 0.0F) {
                     var5 = var18.getVCruiseCluster();
                     break label511;
                  }
               } catch (Exception var56) {
                  Context var214 = (Context)this;
                  String var335 = var56.getMessage();
                  Toast.makeText(var214, "Couldn't bookmark: " + var335, 1).show();
                  break;
               }

               try {
                  var5 = var18.getVCruise();
               } catch (Exception var55) {
                  Context var215 = (Context)this;
                  String var337 = var55.getMessage();
                  Toast.makeText(var215, "Couldn't bookmark: " + var337, 1).show();
                  break;
               }
            }

            try {
               int var117 = Math.round(var5 * 0.621371F);
               StringBuilder var217 = new StringBuilder();
               var380 = var266.put("set_speed", var217.append(var117).append(" mph").toString());
               var8 = var18.getLeadOne().getStatus();
            } catch (Exception var54) {
               Context var216 = (Context)this;
               String var338 = var54.getMessage();
               Toast.makeText(var216, "Couldn't bookmark: " + var338, 1).show();
               break;
            }

            String var339 = "none";
            String var220;
            if (var8) {
               try {
                  var7 = Math.round(var18.getLeadOne().getDRel() * 3.28084F);
                  int var118 = Math.round(var18.getLeadOne().getVLead() * 2.23694F);
                  StringBuilder var219 = new StringBuilder();
                  var220 = var219.append(var7).append(" ft, ").append(var118).append(" mph").toString();
               } catch (Exception var53) {
                  Context var218 = (Context)this;
                  String var340 = var53.getMessage();
                  Toast.makeText(var218, "Couldn't bookmark: " + var340, 1).show();
                  break;
               }
            } else {
               var220 = "none";
            }

            try {
               var21 = var380.put("lead", var220);
               var8 = var18.getLeftBlinker();
            } catch (Exception var52) {
               Context var221 = (Context)this;
               String var341 = var52.getMessage();
               Toast.makeText(var221, "Couldn't bookmark: " + var341, 1).show();
               break;
            }

            label490: {
               label734: {
                  var381 = "both";
                  if (var8) {
                     try {
                        if (var18.getRightBlinker()) {
                           break label734;
                        }
                     } catch (Exception var51) {
                        Context var222 = (Context)this;
                        String var342 = var51.getMessage();
                        Toast.makeText(var222, "Couldn't bookmark: " + var342, 1).show();
                        break;
                     }
                  }

                  label735: {
                     try {
                        if (var18.getLeftBlinker()) {
                           break label735;
                        }
                     } catch (Exception var50) {
                        Context var223 = (Context)this;
                        String var343 = var50.getMessage();
                        Toast.makeText(var223, "Couldn't bookmark: " + var343, 1).show();
                        break;
                     }

                     label476: {
                        try {
                           if (var18.getRightBlinker()) {
                              break label476;
                           }
                        } catch (Exception var49) {
                           Context var224 = (Context)this;
                           String var344 = var49.getMessage();
                           Toast.makeText(var224, "Couldn't bookmark: " + var344, 1).show();
                           break;
                        }

                        var225 = "off";
                        break label490;
                     }

                     var225 = "right";
                     break label490;
                  }

                  var225 = "left";
                  break label490;
               }

               var225 = "both";
            }

            label469: {
               label736: {
                  try {
                     var21 = var21.put("blinker", var225);
                     if (var18.getLeftBlindspot() && var18.getRightBlindspot()) {
                        break label736;
                     }
                  } catch (Exception var48) {
                     Context var226 = (Context)this;
                     String var345 = var48.getMessage();
                     Toast.makeText(var226, "Couldn't bookmark: " + var345, 1).show();
                     break;
                  }

                  label737: {
                     try {
                        if (var18.getLeftBlindspot()) {
                           break label737;
                        }
                     } catch (Exception var47) {
                        Context var227 = (Context)this;
                        String var346 = var47.getMessage();
                        Toast.makeText(var227, "Couldn't bookmark: " + var346, 1).show();
                        break;
                     }

                     label453: {
                        try {
                           if (var18.getRightBlindspot()) {
                              break label453;
                           }
                        } catch (Exception var46) {
                           Context var228 = (Context)this;
                           String var347 = var46.getMessage();
                           Toast.makeText(var228, "Couldn't bookmark: " + var347, 1).show();
                           break;
                        }

                        var229 = "none";
                        break label469;
                     }

                     var229 = "right";
                     break label469;
                  }

                  var229 = "left";
                  break label469;
               }

               var229 = var381;
            }

            try {
               var382 = var21.put("blind_spot", var229);
            } catch (Exception var45) {
               Context var230 = (Context)this;
               String var348 = var45.getMessage();
               Toast.makeText(var230, "Couldn't bookmark: " + var348, 1).show();
               break;
            }

            boolean var119;
            label443: {
               label442: {
                  if (var390 != null) {
                     try {
                        if (var390.getLaneChanging()) {
                           break label442;
                        }
                     } catch (Exception var44) {
                        Context var231 = (Context)this;
                        String var349 = var44.getMessage();
                        Toast.makeText(var231, "Couldn't bookmark: " + var349, 1).show();
                        break;
                     }
                  }

                  var119 = false;
                  break label443;
               }

               var119 = true;
            }

            var232 = var339;
            label434:
            if (var119) {
               label432: {
                  try {
                     if (var390.getLaneChangeDir() < 0) {
                        break label432;
                     }
                  } catch (Exception var43) {
                     Context var233 = (Context)this;
                     String var350 = var43.getMessage();
                     Toast.makeText(var233, "Couldn't bookmark: " + var350, 1).show();
                     break;
                  }

                  var232 = "right";
                  break label434;
               }

               var232 = "left";
            }

            try {
               var19.put(
                  "openpilot",
                  var382.put("lane_change", var232)
                     .put("gas", var18.getGasPressed())
                     .put("brake", var18.getBrakePressed())
                     .put("steering_pressed", var18.getSteeringPressed())
                     .put("steering_angle", bookmarkNow$r1(var18.getSteeringAngleDeg()))
               );
               var352 = this.laneSuggest;
            } catch (Exception var42) {
               Context var234 = (Context)this;
               String var351 = var42.getMessage();
               Toast.makeText(var234, "Couldn't bookmark: " + var351, 1).show();
               break;
            }

            LaneSuggestView var235 = var352;
            if (var352 == null) {
               try {
                  Intrinsics.throwUninitializedPropertyAccessException("laneSuggest");
               } catch (Exception var41) {
                  Context var236 = (Context)this;
                  String var353 = var41.getMessage();
                  Toast.makeText(var236, "Couldn't bookmark: " + var353, 1).show();
                  break;
               }

               var235 = null;
            }

            label417: {
               label738: {
                  try {
                     var355 = var235.getLogic();
                     JSONObject var238 = new JSONObject();
                     var383 = var238.put("state", var355.getState().name()).put("kind", var355.getKind());
                     if (var355.getDir() < 0) {
                        break label738;
                     }
                  } catch (Exception var40) {
                     Context var237 = (Context)this;
                     String var354 = var40.getMessage();
                     Toast.makeText(var237, "Couldn't bookmark: " + var354, 1).show();
                     break;
                  }

                  label410: {
                     try {
                        if (var355.getDir() > 0) {
                           break label410;
                        }
                     } catch (Exception var39) {
                        Context var239 = (Context)this;
                        String var356 = var39.getMessage();
                        Toast.makeText(var239, "Couldn't bookmark: " + var356, 1).show();
                        break;
                     }

                     var240 = "";
                     break label417;
                  }

                  var240 = "right";
                  break label417;
               }

               var240 = "left";
            }

            try {
               var19.put("lane_question", var383.put("side", var240).put("why", var355.getWhy()));
               var358 = this.laneSuggest;
            } catch (Exception var38) {
               Context var241 = (Context)this;
               String var357 = var38.getMessage();
               Toast.makeText(var241, "Couldn't bookmark: " + var357, 1).show();
               break;
            }

            LaneSuggestView var242 = var358;
            if (var358 == null) {
               try {
                  Intrinsics.throwUninitializedPropertyAccessException("laneSuggest");
               } catch (Exception var37) {
                  Context var243 = (Context)this;
                  String var359 = var37.getMessage();
                  Toast.makeText(var243, "Couldn't bookmark: " + var359, 1).show();
                  break;
               }

               var242 = null;
            }

            label395: {
               label739: {
                  try {
                     var361 = var242.getTurn();
                     JSONObject var245 = new JSONObject();
                     var384 = var245.put("state", var361.getState().name());
                     if (var361.getDir() < 0) {
                        break label739;
                     }
                  } catch (Exception var36) {
                     Context var244 = (Context)this;
                     String var360 = var36.getMessage();
                     Toast.makeText(var244, "Couldn't bookmark: " + var360, 1).show();
                     break;
                  }

                  label388: {
                     try {
                        if (var361.getDir() > 0) {
                           break label388;
                        }
                     } catch (Exception var35) {
                        Context var246 = (Context)this;
                        String var362 = var35.getMessage();
                        Toast.makeText(var246, "Couldn't bookmark: " + var362, 1).show();
                        break;
                     }

                     var247 = "";
                     break label395;
                  }

                  var247 = "right";
                  break label395;
               }

               var247 = "left";
            }

            label381: {
               label380: {
                  try {
                     var249 = var384.put("side", var247).put("street", var361.getStreet());
                     var1 = var361.getDistM();
                     if (!Double.isInfinite(var1) && !Double.isNaN(var1)) {
                        break label380;
                     }
                  } catch (Exception var34) {
                     Context var248 = (Context)this;
                     String var363 = var34.getMessage();
                     Toast.makeText(var248, "Couldn't bookmark: " + var363, 1).show();
                     break;
                  }

                  var119 = false;
                  break label381;
               }

               var119 = true;
            }

            if (var119) {
               try {
                  var11 = Math.round(var361.getDistM() * 3.28084);
               } catch (Exception var33) {
                  Context var250 = (Context)this;
                  String var364 = var33.getMessage();
                  Toast.makeText(var250, "Couldn't bookmark: " + var364, 1).show();
                  break;
               }
            } else {
               var11 = -1L;
            }

            label365: {
               label364: {
                  try {
                     var252 = var249.put("ft_to_turn", var11);
                     if (var361.getHoldSide() != 0) {
                        break label364;
                     }
                  } catch (Exception var32) {
                     Context var251 = (Context)this;
                     String var365 = var32.getMessage();
                     Toast.makeText(var251, "Couldn't bookmark: " + var365, 1).show();
                     break;
                  }

                  var8 = false;
                  break label365;
               }

               var8 = true;
            }

            try {
               var19.put("turn_signal", var252.put("holding", var8).put("not_a_turn_lane", var361.getLaneHold()).put("last", var361.getResult()));
               JSONObject var254 = new JSONObject();
               var19.put(
                  "blinker_settings",
                  var254.put("change_lanes_card", var18.s("lane_suggest"))
                     .put("automatic_lane_changes", var18.s("lane_auto"))
                     .put("exit_signals", var18.s("exit_signals"))
                     .put("turn_signals", var18.s("turn_signals"))
                     .put("lane_ends_question", var18.s("lane_ends_ask"))
               );
               JSONArray var255 = new JSONArray(BlinkLog.INSTANCE.recent(15));
               var19.put("blinker_log", var255);
               Bookmarks var256 = Bookmarks.INSTANCE;
               File var367 = new File(this.getFilesDir(), "lane_log.txt");
               var19.put("lane_log_tail", var256.tail(var367, 80));
               var385 = Bookmarks.INSTANCE.create((Context)this, var19);
               LaneShots var257 = this.getLaneShots();
               String var368 = LaneShots.Companion.caption(this.laneTracker, "[bookmark]", var18.getVEgo() * 2.23694F);
               LaneShots.Why var386 = LaneShots.Why.USER;
               File var387 = new File(var385.getDir(), "picture.jpg");
               var257.request(var368, var386, var387);
               var369 = this.bookmarkCard;
            } catch (Exception var31) {
               Context var253 = (Context)this;
               String var366 = var31.getMessage();
               Toast.makeText(var253, "Couldn't bookmark: " + var366, 1).show();
               break;
            }

            BookmarkCard var258 = var369;
            if (var369 == null) {
               try {
                  Intrinsics.throwUninitializedPropertyAccessException("bookmarkCard");
               } catch (Exception var30) {
                  Context var259 = (Context)this;
                  String var370 = var30.getMessage();
                  Toast.makeText(var259, "Couldn't bookmark: " + var370, 1).show();
                  break;
               }

               var258 = null;
            }

            try {
               var258.show(var385);
               var372 = this.bookmarkBtn;
            } catch (Exception var29) {
               Context var260 = (Context)this;
               String var371 = var29.getMessage();
               Toast.makeText(var260, "Couldn't bookmark: " + var371, 1).show();
               break;
            }

            ImageView var261 = var372;
            if (var372 == null) {
               try {
                  Intrinsics.throwUninitializedPropertyAccessException("bookmarkBtn");
               } catch (Exception var28) {
                  Context var262 = (Context)this;
                  String var373 = var28.getMessage();
                  Toast.makeText(var262, "Couldn't bookmark: " + var373, 1).show();
                  break;
               }

               var261 = null;
            }

            try {
               var261.performHapticFeedback(0);
            } catch (Exception var26) {
               Context var263 = (Context)this;
               String var374 = var26.getMessage();
               Toast.makeText(var263, "Couldn't bookmark: " + var374, 1).show();
            }
            break;
         }

         try {
            LaneBrain.Event var24 = (LaneBrain.Event)var397.next();
            long var13 = (var11 - var24.getAtMs()) / 1000;
            String var25 = var24.getText();
            StringBuilder var405 = new StringBuilder();
            var23.add(var405.append(var13).append("s ago: ").append(var25).toString());
         } catch (Exception var82) {
            Context var171 = (Context)this;
            String var298 = var82.getMessage();
            Toast.makeText(var171, "Couldn't bookmark: " + var298, 1).show();
            break;
         }
      }
   }

   private static final double bookmarkNow$r1(double var0) {
      return Math.round(10 * var0) / 10.0;
   }

   private final void checkUsbReturn() {
      String var1 = this.host;
      if (var1 != null) {
         if (!this.usbProbe && !this.searching && !Discovery.INSTANCE.isUsb(Discovery.INSTANCE.interfaceFor(var1))) {
            if (Discovery.INSTANCE.usbInterfaceUp()) {
               this.usbProbe = true;
               new Thread(new MainActivity$$ExternalSyntheticLambda24(this)).start();
            }
         }
      }
   }

   private static final void checkUsbReturn$lambda$160(MainActivity var0) {
      Discovery.Found var1;
      try {
         var1 = Discovery.INSTANCE.find(1500);
      } catch (Exception var2) {
         var1 = null;
      }

      var0.ui.post(new MainActivity$$ExternalSyntheticLambda109(var0, var1));
   }

   private static final void checkUsbReturn$lambda$160$lambda$159(MainActivity var0, Discovery.Found var1) {
      boolean var2 = false;
      var0.usbProbe = false;
      if (var1 != null
         && !Intrinsics.areEqual(var1.getHost(), var0.host)
         && Discovery.INSTANCE.isUsb(Discovery.INSTANCE.interfaceFor(var1.getHost()))
         && (!Link.INSTANCE.getPaired() || var1.getVerified())) {
         String var3 = var0.host;
         String var4 = var1.getHost();
         Log.i("StarView/Main", "USB is back: moving from " + var3 + " to " + var4);
         var0.stopVideo();
         var0.syncDetector(false);
         var0.getSharedPreferences("starview", 0).edit().putString("host", var1.getHost()).apply();
         EditText var8 = var0.hostEdit;
         EditText var5 = var8;
         if (var8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("hostEdit");
            var5 = null;
         }

         var5.setText(var1.getHost());
         var3 = Discovery.INSTANCE.interfaceFor(var1.getHost());
         if (var3.length() == 0) {
            var2 = true;
         }

         if (var2) {
            var3 = "?";
         }

         var0.linkIf = var3;
         var4 = var1.getHost();
         var3 = var0.linkIf;
         var0.setStatus("back on USB: " + var4 + " via " + var3);
         var0.connectTo(var1.getHost(), var1.getPort());
      }
   }

   private final void connectTo(String var1, int var2) {
      this.host = var1;
      this.port = var2;
      new Thread(new MainActivity$$ExternalSyntheticLambda17(var1, var2, this)).start();
   }

   private static final void connectTo$lambda$157(String var0, int var1, MainActivity var2) {
      Link.Auth var3 = Link.INSTANCE.ensureSession(var0, var1);
      var2.ui.post(new MainActivity$$ExternalSyntheticLambda32(var2, var0, var3, var1));
   }

   private static final void connectTo$lambda$157$lambda$156(MainActivity var0, String var1, Link.Auth var2, int var3) {
      if (Intrinsics.areEqual(var0.host, var1)) {
         if (var2 == Link.Auth.NOT_OUR_COMMA) {
            var0.setStatus(var1 + " didn't prove it's your paired comma: not connecting. If the comma was unpaired, scan its QR code again");
            var0.scheduleReconnect();
            PairScanner.INSTANCE.promptIfNeeded(var0, new MainActivity$$ExternalSyntheticLambda18(var0));
         } else {
            Link.INSTANCE.setHost(var1);
            DataClient var5 = var0.data;
            DataClient var4 = var5;
            if (var5 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("data");
               var4 = null;
            }

            var4.connect(var1, var3);
         }
      }
   }

   private static final Unit connectTo$lambda$157$lambda$156$lambda$155(MainActivity var0) {
      String var1 = var0.host;
      if (var1 != null) {
         var0.connectTo(var1, var0.port);
      }

      return Unit.INSTANCE;
   }

   private static final void destLauncher$lambda$12(MainActivity var0, ActivityResult var1) {
      if (var1.getResultCode() == -1) {
         Intent var2 = var1.getData();
         if (var2 != null) {
            var0.navigateTo(var2);
         }
      }
   }

   private final void discover() {
      if (!this.searching) {
         this.searching = true;
         this.setStatus("searching for comma…");
         new Thread(new MainActivity$$ExternalSyntheticLambda105(this)).start();
      }
   }

   private static final void discover$lambda$153(MainActivity var0) {
      Discovery.Found var1 = Discovery.INSTANCE.find(2000);
      var0.ui.post(new MainActivity$$ExternalSyntheticLambda23(var0, var1));
   }

   private static final void discover$lambda$153$lambda$152(MainActivity var0, Discovery.Found var1) {
      boolean var3 = false;
      var0.searching = false;
      boolean var2 = true;
      if (var1 != null && Link.INSTANCE.getPaired() && !var1.getVerified()) {
         Link.INSTANCE.setPairingRequired(true);
         String var12 = var1.getHost();
         var0.setStatus(var12 + " doesn't have this tablet's pairing: not connecting. On the comma: Settings › starview › show qr, then scan it");
         PairScanner.INSTANCE.promptIfNeeded(var0, new MainActivity$$ExternalSyntheticLambda6(var0));
         var0.ui.postDelayed(new MainActivity$$ExternalSyntheticLambda7(var0), 5000L);
      } else {
         EditText var4 = null;
         Object var5 = null;
         if (var1 != null) {
            var4 = var0.hostEdit;
            if (var4 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("hostEdit");
               var4 = (EditText)var5;
            }

            var4.setText(var1.getHost());
            var0.getSharedPreferences("starview", 0).edit().putString("host", var1.getHost()).apply();
            var var15 = Discovery.INSTANCE.interfaceFor(var1.getHost());
            var2 = var3;
            if (var15.length() == 0) {
               var2 = true;
            }

            if (var2) {
               var15 = "?";
            }

            var0.linkIf = var15;
            var5 = var1.getDongleId();
            String var7 = var1.getBranch();
            String var16 = var1.getCommit();
            String var6 = var1.getHost();
            String var8 = var0.linkIf;
            var0.setStatus("found " + var5 + " (" + var7 + " " + var16 + ") at " + var6 + " via " + var8);
            var0.connectTo(var1.getHost(), var1.getPort());
         } else {
            var5 = var0.hostEdit;
            EditText var9 = (EditText)var5;
            if (var5 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("hostEdit");
               var9 = null;
            }

            String var10 = StringsKt.trim(var9.getText().toString()).toString();
            if (var10.length() <= 0) {
               var2 = false;
            }

            if (var2) {
               var0.setStatus("no reply to discovery; trying " + var10);
               var0.connectTo(var10, 8090);
            } else {
               var0.setStatus("comma not found. Is USB tethering on?");
               LinearLayout var11 = var0.connectBar;
               if (var11 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("connectBar");
                  var11 = var4;
               }

               var11.setVisibility(0);
               var0.ui.postDelayed(new MainActivity$$ExternalSyntheticLambda8(var0), 5000L);
            }
         }
      }
   }

   private static final Unit discover$lambda$153$lambda$152$lambda$148(MainActivity var0) {
      var0.discover();
      return Unit.INSTANCE;
   }

   private static final void discover$lambda$153$lambda$152$lambda$149(MainActivity var0) {
      var0.discover();
   }

   private static final void discover$lambda$153$lambda$152$lambda$151(MainActivity var0) {
      var0.discover();
   }

   private final void doorCommand(String var1) {
      DataClient var4 = this.data;
      Object var3 = null;
      DataClient var2 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("data");
         var2 = null;
      }

      if (!var2.getConnected()) {
         Toast.makeText((Context)this, "Not connected to the comma", 0).show();
      } else {
         var2 = this.data;
         if (var2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("data");
            var2 = (DataClient)var3;
         }

         var2.request("door", MapsKt.mapOf(TuplesKt.to("cmd", var1)), new MainActivity$$ExternalSyntheticLambda31(var1, this));
      }
   }

   private static final Unit doorCommand$lambda$212(String var0, MainActivity var1, JSONObject var2) {
      Intrinsics.checkNotNullParameter(var2, "ack");
      if (var2.optBoolean("ok")) {
         if (Intrinsics.areEqual(var0, "lock")) {
            var0 = "Doors locked";
         } else {
            var0 = "Doors unlocked";
         }
      } else {
         var0 = var2.optString("error", "refused");
         var0 = "Doors: " + var0;
      }

      Toast.makeText((Context)var1, var0, 0).show();
      return Unit.INSTANCE;
   }

   private final int dp(int var1) {
      return MathKt.roundToInt(var1 * this.getResources().getDisplayMetrics().density);
   }

   private static final Unit driveTo$lambda$179(MainActivity var0, String var1, String var2, double var3, double var5) {
      var0.setDestinationNow(var1, var2, var3, var5);
      return Unit.INSTANCE;
   }

   private final void fetchStorage() {
      String var1 = this.host;
      if (var1 != null) {
         new Thread(new MainActivity$$ExternalSyntheticLambda73(this, var1)).start();
      }
   }

   private static final void fetchStorage$lambda$165(MainActivity param0, String param1) {
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
      // 00: aload 0
      // 01: invokespecial com/starpilot/starview/MainActivity.getStorageHttp ()Lokhttp3/OkHttpClient;
      // 04: astore 4
      // 06: new okhttp3/Request$Builder
      // 09: astore 5
      // 0b: aload 5
      // 0d: invokespecial okhttp3/Request$Builder.<init> ()V
      // 10: aload 0
      // 11: getfield com/starpilot/starview/MainActivity.port I
      // 14: istore 2
      // 15: new java/lang/StringBuilder
      // 18: astore 3
      // 19: aload 3
      // 1a: invokespecial java/lang/StringBuilder.<init> ()V
      // 1d: aload 4
      // 1f: aload 5
      // 21: aload 3
      // 22: ldc_w "http://"
      // 25: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 28: aload 1
      // 29: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 2c: ldc_w ":"
      // 2f: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 32: iload 2
      // 33: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 36: ldc_w "/storage"
      // 39: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 3c: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 3f: invokevirtual okhttp3/Request$Builder.url (Ljava/lang/String;)Lokhttp3/Request$Builder;
      // 42: invokevirtual okhttp3/Request$Builder.build ()Lokhttp3/Request;
      // 45: invokevirtual okhttp3/OkHttpClient.newCall (Lokhttp3/Request;)Lokhttp3/Call;
      // 48: invokeinterface okhttp3/Call.execute ()Lokhttp3/Response; 1
      // 4d: checkcast java/io/Closeable
      // 50: astore 1
      // 51: aload 1
      // 52: checkcast okhttp3/Response
      // 55: astore 4
      // 57: aload 4
      // 59: invokevirtual okhttp3/Response.isSuccessful ()Z
      // 5c: ifeq 92
      // 5f: new org/json/JSONObject
      // 62: astore 3
      // 63: aload 4
      // 65: invokevirtual okhttp3/Response.body ()Lokhttp3/ResponseBody;
      // 68: astore 4
      // 6a: aload 4
      // 6c: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
      // 6f: aload 3
      // 70: aload 4
      // 72: invokevirtual okhttp3/ResponseBody.string ()Ljava/lang/String;
      // 75: invokespecial org/json/JSONObject.<init> (Ljava/lang/String;)V
      // 78: aload 0
      // 79: getfield com/starpilot/starview/MainActivity.ui Landroid/os/Handler;
      // 7c: astore 4
      // 7e: new com/starpilot/starview/MainActivity$$ExternalSyntheticLambda110
      // 81: astore 5
      // 83: aload 5
      // 85: aload 0
      // 86: aload 3
      // 87: invokespecial com/starpilot/starview/MainActivity$$ExternalSyntheticLambda110.<init> (Lcom/starpilot/starview/MainActivity;Lorg/json/JSONObject;)V
      // 8a: aload 4
      // 8c: aload 5
      // 8e: invokevirtual android/os/Handler.post (Ljava/lang/Runnable;)Z
      // 91: pop
      // 92: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 95: astore 0
      // 96: aload 1
      // 97: aconst_null
      // 98: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // 9b: goto aa
      // 9e: astore 3
      // 9f: aload 3
      // a0: athrow
      // a1: astore 0
      // a2: aload 1
      // a3: aload 3
      // a4: invokestatic kotlin/io/CloseableKt.closeFinally (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      // a7: aload 0
      // a8: athrow
      // a9: astore 0
      // aa: return
   }

   private static final void fetchStorage$lambda$165$lambda$164$lambda$163(MainActivity var0, JSONObject var1) {
      OffroadHome var2 = var0.offroadHome;
      OffroadHome var3 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("offroadHome");
         var3 = null;
      }

      var3.setStorage(var1);
   }

   private static final List floating_delegate$lambda$172(MainActivity var0) {
      TextView var2 = var0.statsTv;
      LinearLayout var6 = null;
      TextView var1 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("statsTv");
         var1 = null;
      }

      TextView var3 = var0.statusTv;
      var2 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("statusTv");
         var2 = null;
      }

      TextView var4 = var0.sceneBtn;
      var3 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sceneBtn");
         var3 = null;
      }

      TextView var5 = var0.whereBtn;
      var4 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("whereBtn");
         var4 = null;
      }

      TextView var7 = var0.mapBtn;
      var5 = var7;
      if (var7 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("mapBtn");
         var5 = null;
      }

      LinearLayout var12 = var0.favBar;
      if (var12 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("favBar");
      } else {
         var6 = var12;
      }

      return CollectionsKt.listOf(new View[]{var1, var2, var3, var4, var5, var6, var0.findViewById(R.id.alertBox), var0.findViewById(R.id.connectBar)});
   }

   private final Detector getDetector() {
      Detector var2 = this.detectorRef;
      Detector var1 = var2;
      if (var2 == null) {
         Context var4 = this.getApplicationContext();
         Intrinsics.checkNotNullExpressionValue(var4, "getApplicationContext(...)");
         var2 = new Detector(var4);
         this.detectorRef = var2;
         OverlayView var3 = this.overlay;
         OverlayView var5 = var3;
         if (var3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("overlay");
            var5 = null;
         }

         var5.setDetector(var2);
         var2.setTelemetry(this.telemetry);
         var1 = var2;
      }

      return var1;
   }

   private final List<View> getFloating() {
      return (List<View>)this.floating$delegate.getValue();
   }

   private final TextView getGpuBanner() {
      return (TextView)this.gpuBanner$delegate.getValue();
   }

   private final LaneModel getLaneModel() {
      return (LaneModel)this.laneModel$delegate.getValue();
   }

   private final LaneShots getLaneShots() {
      return (LaneShots)this.laneShots$delegate.getValue();
   }

   private final boolean getMapOpen() {
      MapPane var3 = this.mapPane;
      boolean var2 = false;
      boolean var1 = var2;
      if (var3 != null) {
         var1 = var2;
         if (var3.getVisibility() == 0) {
            var1 = true;
         }
      }

      return var1;
   }

   private final boolean getMapRight() {
      UiSettings var2 = this.settings;
      UiSettings var1 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("settings");
         var1 = null;
      }

      return Intrinsics.areEqual(var1.getStr("map_side", "left"), "right");
   }

   private final boolean getSaver() {
      UiSettings var2 = this.settings;
      UiSettings var1 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("settings");
         var1 = null;
      }

      return var1.get("battery_saver");
   }

   private final TextView getStallBanner() {
      return (TextView)this.stallBanner$delegate.getValue();
   }

   private final OkHttpClient getStorageHttp() {
      return (OkHttpClient)this.storageHttp$delegate.getValue();
   }

   private static final TextView gpuBanner_delegate$lambda$8(MainActivity var0) {
      TextView var2 = new TextView((Context)var0);
      var2.setTextColor(-1);
      var2.setTextSize(16.0F);
      var2.setGravity(17);
      var2.setPadding(32, 16, 32, 16);
      var2.setVisibility(8);
      GradientDrawable var1 = new GradientDrawable();
      var1.setCornerRadius(28.0F);
      var1.setColor(Color.argb(220, 48, 52, 60));
      var2.setBackground((Drawable)var1);
      var2.setElevation(20.0F);
      var2.setText("Big model loading on the GPU - camera video back when it's ready");
      FrameLayout var6 = var0.root;
      FrameLayout var5 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("root");
         var5 = null;
      }

      View var4 = (View)var2;
      android.widget.FrameLayout.LayoutParams var3 = new android.widget.FrameLayout.LayoutParams(-2, -2, 49);
      var3.topMargin = 170;
      Unit var7 = Unit.INSTANCE;
      var5.addView(var4, (LayoutParams)var3);
      return var2;
   }

   private final void ideasTick(Telemetry var1) {
      long var8 = System.currentTimeMillis();
      Ideas var13 = Ideas.INSTANCE;
      UiSettings var12 = this.settings;
      if (var12 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("settings");
         var12 = null;
      }

      var13.tick(var1, var12, this.detectorRef, var8);
      String var14 = Ideas.INSTANCE.getChip();
      if (var14 != null && var1.getStarted()) {
         TextView var41 = this.ideasChip;
         TextView var20 = var41;
         if (var41 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ideasChip");
            var20 = null;
         }

         if (!Intrinsics.areEqual(var20.getText(), var14)) {
            TextView var42 = this.ideasChip;
            TextView var21 = var42;
            if (var42 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("ideasChip");
               var21 = null;
            }

            var21.setText(var14);
         }

         TextView var43 = this.ideasChip;
         TextView var22 = var43;
         if (var43 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ideasChip");
            var22 = null;
         }

         var22.setVisibility(0);
      } else {
         TextView var40 = this.ideasChip;
         TextView var19 = var40;
         if (var40 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ideasChip");
            var19 = null;
         }

         var19.setVisibility(8);
      }

      long var10 = Ideas.INSTANCE.road().getAskWhy();
      if (var10 != 0L && var10 != this.ideasWhyShownFor) {
         this.ideasWhyShownFor = var10;
         LinearLayout var44 = this.ideasWhy;
         LinearLayout var23 = var44;
         if (var44 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ideasWhy");
            var23 = null;
         }

         var23.setVisibility(0);
      }

      LinearLayout var45 = this.ideasWhy;
      LinearLayout var24 = var45;
      if (var45 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ideasWhy");
         var24 = null;
      }

      if (var24.getVisibility() == 0 && var8 - this.ideasWhyShownFor > 12000L) {
         LinearLayout var46 = this.ideasWhy;
         LinearLayout var25 = var46;
         if (var46 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ideasWhy");
            var25 = null;
         }

         var25.setVisibility(8);
         Ideas.INSTANCE.road().answerWhy((Context)this, null);
      }

      label193: {
         DayView var47 = Ideas.INSTANCE.getMemory().getDayView();
         var26 = var47.getShowing();
         if (var26 != null) {
            boolean var4;
            if (var47.getWanted() && var1.getStarted() && var8 - var47.getShowingAtMs() < 3000L && !this.sceneOn) {
               var4 = true;
            } else {
               var4 = false;
            }

            if (var4) {
               break label193;
            }
         }

         var26 = null;
      }

      if (var26 != null) {
         ImageView var58 = this.ideasDay;
         ImageView var48 = var58;
         if (var58 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ideasDay");
            var48 = null;
         }

         var48.setImageBitmap(var26);
         ImageView var49 = this.ideasDay;
         ImageView var27 = var49;
         if (var49 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ideasDay");
            var27 = null;
         }

         var27.setVisibility(0);
      } else {
         ImageView var50 = this.ideasDay;
         ImageView var28 = var50;
         if (var50 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ideasDay");
            var28 = null;
         }

         var28.setVisibility(8);
      }

      if (var1.getStarted() && var1.getVEgo() > 3.0F && var8 - this.ideasLabelMs > 20000L) {
         Pair var29 = this.laneTracker.laneFromLeft();
         int var15 = ((Number)var29.component1()).intValue();
         double var2 = ((Number)var29.component2()).doubleValue();
         int var5 = this.laneTracker.laneCount().component1().intValue();
         NewIdeas var59 = NewIdeas.INSTANCE;
         UiSettings var51 = this.settings;
         var12 = var51;
         if (var51 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("settings");
            var12 = null;
         }

         if (var59.on(var12, "self_labels") && Intrinsics.areEqual(RtkPosition.INSTANCE.getKind(), "fixed") && var2 >= 0.9 && var15 > 0 && var5 > 0) {
            this.ideasLabelMs = var8;
            LaneShots var36 = this.getLaneShots();
            String var54 = LaneShots.Companion.caption(this.laneTracker, "self-labelled (RTK fixed)", var1.getVEgo() * 2.23694F);
            LaneShots.request$default(var36, "[auto] " + var54, LaneShots.Why.EVENT, null, 4, null);
         } else {
            NewIdeas var60 = NewIdeas.INSTANCE;
            UiSettings var52 = this.settings;
            var12 = var52;
            if (var52 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("settings");
               var12 = null;
            }

            if (var60.on(var12, "model_disagree")) {
               label166: {
                  Detector var32 = this.detectorRef;
                  if (var32 != null) {
                     LaneModel var33 = var32.getLaneModel();
                     if (var33 != null) {
                        var34 = var33.getAnswer();
                        if (var34 != null) {
                           boolean var16;
                           if (var8 - var34.getAtMs() < 3000L && var34.getQL() > 0.85F && var34.getQR() > 0.85F) {
                              var16 = true;
                           } else {
                              var16 = false;
                           }

                           if (var16) {
                              break label166;
                           }
                        }
                     }
                  }

                  var34 = null;
               }

               if (var34 != null
                  && var2 >= 0.6
                  && var5 > 0
                  && (
                     var34.getLeft() != RangesKt.coerceAtMost(this.laneTracker.lanesLeft(), 3)
                        || var34.getRight() != RangesKt.coerceAtMost(this.laneTracker.lanesRight(), 3)
                  )) {
                  this.ideasLabelMs = var8;
                  LaneShots var53 = this.getLaneShots();
                  int var7 = var34.getLeft();
                  var5 = var34.getRight();
                  int var6 = this.laneTracker.lanesLeft();
                  var15 = this.laneTracker.lanesRight();
                  String var35 = LaneShots.Companion.caption(this.laneTracker, "model and card disagree", var1.getVEgo() * 2.23694F);
                  LaneShots.request$default(
                     var53, "[disagree] model " + var7 + "/" + var5 + " vs card " + var6 + "/" + var15 + " - " + var35, LaneShots.Why.EVENT, null, 4, null
                  );
               }
            }
         }
      }

      if (var1.getStarted() && !this.ideasWasStarted) {
         NewIdeas var61 = NewIdeas.INSTANCE;
         UiSettings var55 = this.settings;
         var12 = var55;
         if (var55 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("settings");
            var12 = null;
         }

         if (var61.on(var12, "friends_fleet")) {
            new Thread(new MainActivity$$ExternalSyntheticLambda33(this)).start();
         }

         NewIdeas var62 = NewIdeas.INSTANCE;
         UiSettings var56 = this.settings;
         var12 = var56;
         if (var56 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("settings");
            var12 = null;
         }

         if (var62.on(var12, "night_school")) {
            NewIdeas.INSTANCE.importNightModel((Context)this);
         }
      }

      if (!var1.getStarted() && this.ideasWasStarted) {
         Ideas.INSTANCE.endOfDrive();
         NewIdeas var63 = NewIdeas.INSTANCE;
         UiSettings var57 = this.settings;
         var12 = var57;
         if (var57 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("settings");
            var12 = null;
         }

         if (var63.on(var12, "friends_fleet")) {
            new Thread(new MainActivity$$ExternalSyntheticLambda34(this)).start();
         }
      }

      this.ideasWasStarted = var1.getStarted();
   }

   private static final void ideasTick$lambda$190(MainActivity var0) {
      IdeaSpots.INSTANCE.importFleet(NewIdeas.INSTANCE.deviceName((Context)var0));
   }

   private static final void ideasTick$lambda$191(MainActivity var0) {
      IdeaSpots.INSTANCE.exportFleet(NewIdeas.INSTANCE.deviceName((Context)var0));
   }

   private final void installCrashLog() {
      File var2 = new File(this.getFilesDir(), "last_crash.txt");
      if (var2.exists()) {
         String var1;
         try {
            var1 = StringsKt.take(FilesKt.readText$default(var2, null, 1, null), 600);
         } catch (Exception var4) {
            var1 = "";
         }

         this.lastCrash = var1;
         var2.delete();
      }

      String var5;
      try {
         var5 = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
      } catch (Exception var3) {
         var5 = "?";
      }

      Thread.setDefaultUncaughtExceptionHandler(new MainActivity$$ExternalSyntheticLambda28(var2, var5, Thread.getDefaultUncaughtExceptionHandler()));
   }

   private static final void installCrashLog$lambda$13(File var0, String var1, UncaughtExceptionHandler var2, Thread var3, Throwable var4) {
      label24:
      try {
         Date var8 = new Date();
         String var7 = var3.getName();
         String var6 = Log.getStackTraceString(var4);
         StringBuilder var5 = new StringBuilder();
         FilesKt.writeText$default(
            var0, var5.append(var8).append(" ").append(var1).append(" [").append(var7).append("]\n").append(var6).toString(), null, 2, null
         );
      } finally {
         break label24;
      }

      if (var2 != null) {
         var2.uncaughtException(var3, var4);
      }
   }

   private static final LaneModel laneModel_delegate$lambda$10(MainActivity var0) {
      Context var1 = var0.getApplicationContext();
      Intrinsics.checkNotNullExpressionValue(var1, "getApplicationContext(...)");
      return new LaneModel(var1);
   }

   private static final LaneShots laneShots_delegate$lambda$9(MainActivity var0) {
      Context var1 = var0.getApplicationContext();
      Intrinsics.checkNotNullExpressionValue(var1, "getApplicationContext(...)");
      return new LaneShots(var1);
   }

   private final void layoutHome() {
      FrameLayout var7 = this.root;
      Object var6 = null;
      FrameLayout var5 = var7;
      if (var7 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("root");
         var5 = null;
      }

      int var4 = var5.getWidth();
      var7 = this.root;
      var5 = var7;
      if (var7 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("root");
         var5 = null;
      }

      int var2 = var5.getHeight();
      if (var4 != 0 && var2 != 0) {
         float var1 = this.getResources().getDisplayMetrics().density;
         var1 = RangesKt.coerceIn(var4 / var1 / 1150.0F, 0.62F, 1.0F);
         String var11 = var4 + " x " + var2 + " x " + var1;
         if (!Intrinsics.areEqual(var11, this.lastHomeKey)) {
            this.lastHomeKey = var11;
            int var3 = this.dp(240);
            LinearLayout var27 = this.sidebar;
            LinearLayout var12 = var27;
            if (var27 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("sidebar");
               var12 = null;
            }

            LayoutParams var13 = var12.getLayoutParams();
            Intrinsics.checkNotNull(var13, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
            var var8 = (android.widget.FrameLayout.LayoutParams & LayoutParams)var13;
            var8.width = var3;
            var8.height = (int)(var2 / var1);
            LinearLayout var28 = this.sidebar;
            LinearLayout var14 = var28;
            if (var28 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("sidebar");
               var14 = null;
            }

            var14.setLayoutParams((LayoutParams)var8);
            LinearLayout var29 = this.sidebar;
            LinearLayout var15 = var29;
            if (var29 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("sidebar");
               var15 = null;
            }

            var15.setPivotX(0.0F);
            LinearLayout var30 = this.sidebar;
            LinearLayout var16 = var30;
            if (var30 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("sidebar");
               var16 = null;
            }

            var16.setPivotY(0.0F);
            LinearLayout var31 = this.sidebar;
            LinearLayout var17 = var31;
            if (var31 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("sidebar");
               var17 = null;
            }

            var17.setScaleX(var1);
            LinearLayout var32 = this.sidebar;
            LinearLayout var18 = var32;
            if (var32 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("sidebar");
               var18 = null;
            }

            var18.setScaleY(var1);
            OffroadHome var33 = this.offroadHome;
            OffroadHome var19 = var33;
            if (var33 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("offroadHome");
               var19 = null;
            }

            LayoutParams var20 = var19.getLayoutParams();
            Intrinsics.checkNotNull(var20, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
            var8 = (android.widget.FrameLayout.LayoutParams & LayoutParams)var20;
            var8.width = (int)((var4 - var3 * var1) / var1);
            var8.height = (int)(var2 / var1);
            var8.setMarginStart((int)(var3 * var1));
            OffroadHome var34 = this.offroadHome;
            OffroadHome var21 = var34;
            if (var34 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("offroadHome");
               var21 = null;
            }

            var21.setLayoutParams((LayoutParams)var8);
            OffroadHome var35 = this.offroadHome;
            OffroadHome var22 = var35;
            if (var35 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("offroadHome");
               var22 = null;
            }

            var22.setPivotX(0.0F);
            OffroadHome var36 = this.offroadHome;
            OffroadHome var23 = var36;
            if (var36 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("offroadHome");
               var23 = null;
            }

            var23.setPivotY(0.0F);
            OffroadHome var37 = this.offroadHome;
            OffroadHome var24 = var37;
            if (var37 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("offroadHome");
               var24 = null;
            }

            var24.setScaleX(var1);
            OffroadHome var25 = this.offroadHome;
            if (var25 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("offroadHome");
               var25 = (OffroadHome)var6;
            }

            var25.setScaleY(var1);
         }
      }
   }

   private final void layoutHud() {
      float var1 = this.windowScale();
      HudLayout var5 = this.hud;
      FrameLayout var4 = null;
      HudLayout var3 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("hud");
         var3 = null;
      }

      var3.setWindowScale(var1);
      var5 = this.hud;
      var3 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("hud");
         var3 = null;
      }

      FrameLayout var11 = this.root;
      if (var11 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("root");
      } else {
         var4 = var11;
      }

      var3.apply(var4);
      this.layoutHome();
      if (this.lastWs < 0.0F) {
         Iterator var7 = this.getFloating().iterator();

         while (var7.hasNext()) {
            ((View)var7.next()).addOnLayoutChangeListener(new MainActivity$$ExternalSyntheticLambda16(this));
         }
      }

      boolean var2;
      if (var1 == this.lastWs) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (!var2) {
         this.lastWs = var1;

         for (View var9 : this.getFloating()) {
            var9.setScaleX(var1);
            var9.setScaleY(var1);
            this.pivotToAnchor(var9);
         }
      }
   }

   private static final void layoutHud$lambda$224(MainActivity var0, View var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
      Intrinsics.checkNotNull(var1);
      var0.pivotToAnchor(var1);
   }

   private final void layoutMap(int var1) {
      MapPane var5 = this.mapPane;
      if (var5 != null) {
         int var2;
         if (this.getMapRight()) {
            var2 = 8388613;
         } else {
            var2 = 8388611;
         }

         var5.setLayoutParams((LayoutParams)(new android.widget.FrameLayout.LayoutParams(var1, -1, var2)));
         FrameLayout var7 = this.root;
         Object var6 = null;
         FrameLayout var10 = var7;
         if (var7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            var10 = null;
         }

         LayoutParams var11 = var10.getLayoutParams();
         Intrinsics.checkNotNull(var11, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
         var var13 = (android.widget.FrameLayout.LayoutParams & LayoutParams)var11;
         boolean var4 = this.getMapRight();
         byte var3 = 0;
         if (var4) {
            var2 = 0;
         } else {
            var2 = var1;
         }

         var13.setMarginStart(var2);
         var2 = var3;
         if (this.getMapRight()) {
            var2 = var1;
         }

         var13.setMarginEnd(var2);
         FrameLayout var12 = this.root;
         if (var12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            var12 = (FrameLayout)var6;
         }

         var12.setLayoutParams((LayoutParams)var13);
         this.placeDivider(var1);
      }
   }

   private final void listenVoice() {
      try {
         ActivityResultLauncher var1 = this.voiceLauncher;
         Intent var2 = new Intent("android.speech.action.RECOGNIZE_SPEECH");
         var1.launch(
            var2.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form")
               .putExtra("android.speech.extra.PROMPT", "Say a quick setting, or \"take me to…\"")
         );
      } catch (Exception var3) {
         Toast.makeText((Context)this, "No speech recognizer on this tablet", 1).show();
      }
   }

   private final float locationOnScreenX(View var1) {
      int[] var2 = new int[2];
      var1.getLocationOnScreen(var2);
      return var2[0];
   }

   private final int mapWidth() {
      FrameLayout var5 = this.outer;
      Object var4 = null;
      FrameLayout var3 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("outer");
         var3 = null;
      }

      Integer var7 = var3.getWidth();
      boolean var2;
      if (var7.intValue() > 0) {
         var2 = 1;
      } else {
         var2 = 0;
      }

      if (!var2) {
         var7 = null;
      }

      if (var7 != null) {
         var2 = var7;
      } else {
         var2 = this.getResources().getDisplayMetrics().widthPixels;
      }

      float var1 = var2;
      UiSettings var8 = this.settings;
      if (var8 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("settings");
         var8 = (UiSettings)var4;
      }

      return RangesKt.coerceIn(
         MathKt.roundToInt(var1 * var8.getFloat("map_frac", 0.4F)), RangesKt.coerceAtMost(this.dp(260), var2 / 2), MathKt.roundToInt(var2 * 0.72F)
      );
   }

   private final void maskIntent(Intent var1) {
      boolean var3 = false;
      boolean var2 = var3;
      if (var1 != null) {
         var2 = var3;
         if (var1.getBooleanExtra("edit_masks", false)) {
            var2 = true;
         }
      }

      if (var2) {
         var1.removeExtra("edit_masks");
         this.getWindow().getDecorView().postDelayed(new MainActivity$$ExternalSyntheticLambda19(this), 300L);
      }
   }

   private static final void maskIntent$lambda$20(MainActivity var0) {
      var0.startMaskEdit();
   }

   private final void navTick(long var1) {
      MapPane var9 = this.mapPane;
      UiSettings var10 = null;
      if (var9 != null && this.getMapOpen()) {
         var9.tick(var1);
      } else {
         Nav var11 = Nav.INSTANCE;
         DataClient var15 = this.data;
         if (var15 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("data");
            var15 = null;
         }

         Nav.poll$default(var11, var15, false, var1, false, 8, null);
      }

      MapboxRelay var12 = MapboxRelay.INSTANCE;
      Context var20 = (Context)this;
      DataClient var16 = this.data;
      if (var16 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("data");
         var16 = null;
      }

      var12.tick(var20, var16, this.host, var1);
      if (Favorites.INSTANCE.getList().isEmpty() && this.tick % 200 == 0 || this.tick % 2400 == 0) {
         Favorites var24 = Favorites.INSTANCE;
         DataClient var21 = this.data;
         DataClient var17 = var21;
         if (var21 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("data");
            var17 = null;
         }

         Favorites.load$default(var24, var17, null, 2, null);
      }

      label61: {
         JSONObject var18 = Nav.INSTANCE.getDestination();
         if (var18 != null) {
            double var5 = var18.optDouble("latitude");
            double var3 = var18.optDouble("longitude");
            String var22 = var5 + "," + var3;
            var19 = var22;
            if (var22 != null) {
               break label61;
            }
         }

         var19 = "";
      }

      if (!Intrinsics.areEqual(var19, this.lastNavDest)) {
         int var7 = var19.length();
         boolean var8 = true;
         boolean var13;
         if (var7 > 0) {
            var13 = true;
         } else {
            var13 = false;
         }

         if (var13) {
            if (this.lastNavDest.length() > 0) {
               var13 = var8;
            } else {
               var13 = false;
            }

            if (!var13 && Nav.INSTANCE.getLoaded() && !this.getMapOpen()) {
               UiSettings var23 = this.settings;
               if (var23 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("settings");
               } else {
                  var10 = var23;
               }

               if (var10.get("map_auto_open")) {
                  this.openMap();
               }
            }
         }

         this.lastNavDest = var19;
      }
   }

   private final void navigateTo(Intent var1) {
      String var9 = var1.getStringExtra("name");
      String var11 = "";
      String var8 = var9;
      if (var9 == null) {
         var8 = "";
      }

      String var10 = var1.getStringExtra("address");
      var9 = var10;
      if (var10 == null) {
         var9 = "";
      }

      double var2 = var1.getDoubleExtra("latitude", Double.NaN);
      double var4 = var1.getDoubleExtra("longitude", Double.NaN);
      String var12 = var1.getStringExtra("mapbox_id");
      var10 = var12;
      if (var12 == null) {
         var10 = "";
      }

      int var6 = var10.length();
      boolean var7 = true;
      boolean var14;
      if (var6 > 0) {
         var14 = true;
      } else {
         var14 = false;
      }

      if (var14) {
         String var13 = var1.getStringExtra("search_id");
         if (var13 == null) {
            var13 = var11;
         }

         Map var20 = MapsKt.mapOf(new Pair[]{TuplesKt.to("placeId", var10), TuplesKt.to("searchId", var13)});
         JSONObject var18 = new JSONObject().put("name", var8).put("address", var9);
         Intrinsics.checkNotNullExpressionValue(var18, "put(...)");
         this.previewPlace(var20, var18, new MainActivity$$ExternalSyntheticLambda5(this, var10, var13, var8));
      } else {
         if (!Double.isInfinite(var2) && !Double.isNaN(var2)) {
            var14 = true;
         } else {
            var14 = false;
         }

         if (var14) {
            if (!Double.isInfinite(var4) && !Double.isNaN(var4)) {
               var14 = var7;
            } else {
               var14 = false;
            }

            if (var14) {
               this.driveTo(var8, var9, var2, var4);
               return;
            }
         }
      }
   }

   private static final Unit navigateTo$lambda$177(MainActivity var0, String var1, String var2, String var3) {
      DataClient var5 = var0.data;
      DataClient var4 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("data");
         var4 = null;
      }

      var4.request(
         "nav_pick", MapsKt.mapOf(new Pair[]{TuplesKt.to("id", var1), TuplesKt.to("searchId", var2)}), new MainActivity$$ExternalSyntheticLambda100(var0, var3)
      );
      return Unit.INSTANCE;
   }

   private static final Unit navigateTo$lambda$177$lambda$176(MainActivity var0, String var1, JSONObject var2) {
      Intrinsics.checkNotNullParameter(var2, "ack");
      if (!var2.optBoolean("ok")) {
         Context var9 = (Context)var0;
         Object var6 = var2.optString("error");
         boolean var3;
         if (var6.length() == 0) {
            var3 = true;
         } else {
            var3 = false;
         }

         if (var3) {
            var6 = "no answer";
         }

         Toast.makeText(var9, "StarPilot didn't take " + var1 + ": " + var6, 1).show();
         return Unit.INSTANCE;
      } else {
         Nav var4 = Nav.INSTANCE;
         DataClient var8 = var0.data;
         DataClient var7 = var8;
         if (var8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("data");
            var7 = null;
         }

         var4.newDestination(var7);
         var0.openMap();
         MapPane var5 = var0.mapPane;
         if (var5 != null) {
            var5.expectRoutes();
         }

         return Unit.INSTANCE;
      }
   }

   private static final void onCreate$lambda$100$lambda$99(MainActivity var0, View var1) {
      var0.listenVoice();
   }

   private static final void onCreate$lambda$104$lambda$103(MainActivity var0, View var1) {
      var0.bookmarkNow();
   }

   private static final boolean onCreate$lambda$106(MainActivity var0) {
      boolean var1;
      if (var0.telemetry.getVEgo() < 0.5F) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private static final Unit onCreate$lambda$107(MainActivity var0) {
      var0.requestPermissions(new String[]{"android.permission.RECORD_AUDIO"}, 84);
      Toast.makeText((Context)var0, "Allow the microphone, then hold \ud83c\udfa4 again", 1).show();
      return Unit.INSTANCE;
   }

   private static final void onCreate$lambda$111$lambda$110(MainActivity var0, View var1) {
      var0.setSaver(false);
   }

   private static final void onCreate$lambda$127$lambda$125$lambda$124(MainActivity var0, String var1, View var2) {
      Ideas.INSTANCE.road().answerWhy((Context)var0, var1);
      LinearLayout var4 = var0.ideasWhy;
      LinearLayout var3 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ideasWhy");
         var3 = null;
      }

      var3.setVisibility(8);
   }

   private static final void onCreate$lambda$31(MainActivity var0) {
      if (!var0.isFinishing()) {
         new Builder((Context)var0)
            .setTitle("Restore your settings?")
            .setMessage("StarView looks newly installed. If you made a backup (Download/StarView), pick it to bring back your layout and settings.")
            .setPositiveButton("Choose backup…", new MainActivity$$ExternalSyntheticLambda10(var0))
            .setNegativeButton("Not now", new MainActivity$$ExternalSyntheticLambda12(var0))
            .setOnCancelListener(new MainActivity$$ExternalSyntheticLambda13(var0))
            .show();
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private static final void onCreate$lambda$31$lambda$28(MainActivity var0, DialogInterface var1, int var2) {
      label24:
      try {
         Result.Companion var8 = Result.Companion;
         var0.pickBackup.launch(new String[]{"application/json", "text/plain", "application/octet-stream"});
         var7 = KtCompat.resultOf(Unit.INSTANCE);
      } catch (Throwable var5) {
         Result.Companion var6 = Result.Companion;
         var7 = KtCompat.resultOf(ResultKt.createFailure(var5));
         break label24;
      }

      if (KtCompat.exceptionOrNull(var7) != null) {
         var0.backupArmed = true;
      }
   }

   private static final void onCreate$lambda$31$lambda$29(MainActivity var0, DialogInterface var1, int var2) {
      var0.backupArmed = true;
   }

   private static final void onCreate$lambda$31$lambda$30(MainActivity var0, DialogInterface var1) {
      var0.backupArmed = true;
   }

   private static final void onCreate$lambda$33(MainActivity var0) {
      try {
         File var1 = new File(var0.getFilesDir(), "models");
         FilesKt.deleteRecursively(var1);
      } catch (Exception var2) {
      }
   }

   private static final Unit onCreate$lambda$37(MainActivity var0) {
      var0.ui.post(new MainActivity$$ExternalSyntheticLambda11(var0));
      return Unit.INSTANCE;
   }

   private static final void onCreate$lambda$37$lambda$36(MainActivity var0) {
      SideView var3 = var0.pipLeft;
      Object var2 = null;
      SideView var1 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("pipLeft");
         var1 = null;
      }

      var1.invalidate();
      SideView var4 = var0.pipRight;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("pipRight");
         var4 = (SideView)var2;
      }

      var4.invalidate();
   }

   private static final void onCreate$lambda$40(MainActivity var0, View var1) {
      String var8 = Telemetry.pStr$default(var0.telemetry, "ChestnutState", null, 2, null);
      String var7 = Telemetry.Companion.chestnutWhy(Telemetry.pStr$default(var0.telemetry, "ChestnutWhy", null, 2, null));
      boolean var5 = Intrinsics.areEqual(var8, "failed");
      String var12 = "";
      boolean var2 = false;
      boolean var4 = false;
      boolean var3 = false;
      if (!var5 && !var0.egpuFallback) {
         Context var15;
         label57: {
            var15 = (Context)var0;
            switch (var8.hashCode()) {
               case -1422950650:
                  if (var8.equals("active")) {
                     var9 = "Driving on the big model (eGPU)";
                     break label57;
                  }
                  break;
               case -1281977283:
                  if (var8.equals("failed")) {
                     var2 = var3;
                     if (var7.length() > 0) {
                        var2 = true;
                     }

                     if (var2) {
                        var12 = ": " + var7;
                     }

                     var9 = "Big model (eGPU) failed - driving on the small model" + var12;
                     break label57;
                  }
                  break;
               case 336650556:
                  if (var8.equals("loading")) {
                     var9 = "Big model (eGPU) loading";
                     break label57;
                  }
            }

            Object var10 = var8;
            if (var10.length() == 0) {
               var2 = true;
            }

            if (var2) {
               var10 = "unknown";
            }

            var9 = "eGPU: " + var10;
         }

         CharSequence var11 = var9;
         Toast.makeText(var15, var11, 1).show();
      } else {
         Builder var6 = new Builder((Context)var0).setTitle("Big model (eGPU) failed");
         var2 = var4;
         if (var7.length() > 0) {
            var2 = true;
         }

         if (var2) {
            var12 = " (" + var7 + ")";
         }

         var6.setMessage(
               "Driving on the small model"
                  + var12
                  + ".\n\nRestart openpilot to reload it? The comma goes offroad and comes back by itself (about 20 s). Do it parked or stopped."
            )
            .setPositiveButton("Restart", new MainActivity$$ExternalSyntheticLambda26(var0))
            .setNegativeButton("Not now", null)
            .show();
      }
   }

   private static final void onCreate$lambda$40$lambda$38(MainActivity var0, DialogInterface var1, int var2) {
      DriveState.restart$default(DriveState.INSTANCE, var0, null, 2, null);
   }

   private static final Unit onCreate$lambda$43(MainActivity var0, JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "req");
      DataClient var2 = var0.data;
      DataClient var3 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("data");
         var3 = null;
      }

      var3.control(var1);
      return Unit.INSTANCE;
   }

   private static final Unit onCreate$lambda$44(MainActivity var0, SlowReason.Event var1) {
      Intrinsics.checkNotNullParameter(var1, "e");
      SlowReasonView.Companion var2 = SlowReasonView.Companion;
      Context var3 = var0.getApplicationContext();
      Intrinsics.checkNotNullExpressionValue(var3, "getApplicationContext(...)");
      var2.log(var3, var1);
      return Unit.INSTANCE;
   }

   private static final Triple onCreate$lambda$45(MainActivity var0) {
      return new Triple<>(var0.telemetry.getGpsLat(), var0.telemetry.getGpsLon(), var0.telemetry.getVEgo() * 2.23694F);
   }

   private static final Unit onCreate$lambda$46(MainActivity var0, LaneBrain.Event var1) {
      Intrinsics.checkNotNullParameter(var1, "e");
      LaneCardView.Companion var2 = LaneCardView.Companion;
      Context var3 = var0.getApplicationContext();
      Intrinsics.checkNotNullExpressionValue(var3, "getApplicationContext(...)");
      var2.log(var3, var0.laneTracker, var1, var0.telemetry.getGpsLat(), var0.telemetry.getGpsLon(), var0.telemetry.getVEgo() * 2.23694F);
      LaneCardView var5 = var0.laneCard;
      LaneCardView var4 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("laneCard");
         var4 = null;
      }

      if (var4.getVisibility() == 0) {
         LaneShots.request$default(
            var0.getLaneShots(),
            LaneShots.Companion.caption(var0.laneTracker, var1.getText(), var0.telemetry.getVEgo() * 2.23694F),
            LaneShots.Why.EVENT,
            null,
            4,
            null
         );
      }

      return Unit.INSTANCE;
   }

   private static final List onCreate$lambda$47(MainActivity var0) {
      return var0.laneFeed.getRamps();
   }

   private static final SceneView onCreate$lambda$49(MainActivity var0) {
      SceneView var2 = var0.scene;
      if (var2 != null) {
         boolean var1;
         if (var0.sceneOn && var2.getVisibility() == 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         if (var1) {
            return var2;
         }
      }

      return null;
   }

   private static final Pair onCreate$lambda$50(MainActivity var0) {
      return TuplesKt.to(var0.laneTracker.laneCount().getFirst(), var0.laneTracker.laneFromLeft().getFirst());
   }

   private static final LaneBrain.Road onCreate$lambda$51(MainActivity var0) {
      return var0.laneTracker.getRoad();
   }

   private static final List onCreate$lambda$52(MainActivity var0) {
      return var0.laneFeed.getRamps();
   }

   private static final String onCreate$lambda$54(MainActivity var0) {
      Detector var3 = var0.detectorRef;
      if (var3 != null) {
         LaneModel var4 = var3.getLaneModel();
         if (var4 != null) {
            LaneModel.Answer var5 = var4.getAnswer();
            if (var5 != null) {
               boolean var1;
               if (System.currentTimeMillis() - var5.getAtMs() < 5000L) {
                  var1 = true;
               } else {
                  var1 = false;
               }

               if (!var1) {
                  var5 = null;
               }

               if (var5 != null) {
                  String var2 = var5.code();
                  if (var2 != null) {
                     return var2;
                  }
               }
            }
         }
      }

      return "";
   }

   private static final String onCreate$lambda$55(MainActivity var0) {
      Pair var5 = var0.laneTracker.laneFromLeft();
      int var3 = ((Number)var5.component1()).intValue();
      double var1 = ((Number)var5.component2()).doubleValue();
      int var4 = var0.laneTracker.laneCount().component1().intValue();
      String var6 = String.format(Locale.US, "%.2f", Arrays.copyOf(new Object[]{var1}, 1));
      Intrinsics.checkNotNullExpressionValue(var6, "format(...)");
      return var3 + "," + var4 + "," + var6;
   }

   private static final Pair onCreate$lambda$56(MainActivity var0) {
      String var2 = var0.laneAhead.text(var0.laneTracker.getOdo(), var0.telemetry.isMetric());
      boolean var1;
      if (var0.laneAhead.getStage() == LaneAhead.Stage.URGENT) {
         var1 = true;
      } else {
         var1 = false;
      }

      return TuplesKt.to(var2, var1);
   }

   private static final Unit onCreate$lambda$57(MainActivity var0, String var1, boolean var2) {
      Intrinsics.checkNotNullParameter(var1, "text");
      LaneCardView.Companion var4 = LaneCardView.Companion;
      Context var3 = var0.getApplicationContext();
      Intrinsics.checkNotNullExpressionValue(var3, "getApplicationContext(...)");
      var4.log(
         var3,
         var0.laneTracker,
         new LaneBrain.Event(System.currentTimeMillis(), "[lane ends] " + var1),
         var0.telemetry.getGpsLat(),
         var0.telemetry.getGpsLon(),
         var0.telemetry.getVEgo() * 2.23694F
      );
      if (var2) {
         LaneShots.request$default(
            var0.getLaneShots(),
            LaneShots.Companion.caption(var0.laneTracker, "[lane ends] " + var1, var0.telemetry.getVEgo() * 2.23694F),
            LaneShots.Why.EVENT,
            null,
            4,
            null
         );
      }

      return Unit.INSTANCE;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private static final Unit onCreate$lambda$58(MainActivity var0, File var1) {
      JSONObject var2;
      Iterator var3;
      try {
         var2 = new JSONObject();
         var3 = var0.laneTracker.getMyCounts().entrySet().iterator();
      } catch (Exception var5) {
         return Unit.INSTANCE;
      }

      while (true) {
         try {
            if (var3.hasNext()) {
               Entry var8 = (Entry)var3.next();
               var2.put(String.valueOf(((Number)var8.getKey()).longValue()), ((Number)var8.getValue()).intValue());
               continue;
            }
         } catch (Exception var6) {
            break;
         }

         try {
            String var7 = var2.toString();
            Intrinsics.checkNotNullExpressionValue(var7, "toString(...)");
            FilesKt.writeText$default(var1, var7, null, 2, null);
         } catch (Exception var4) {
         }
         break;
      }

      return Unit.INSTANCE;
   }

   private static final void onCreate$lambda$59(MainActivity var0, SharedPreferences var1, View var2) {
      EditText var4 = var0.hostEdit;
      EditText var5 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("hostEdit");
         var5 = null;
      }

      String var6 = StringsKt.trim(var5.getText().toString()).toString();
      boolean var3;
      if (var6.length() > 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (var3) {
         var1.edit().putString("host", var6).apply();
         var0.connectTo(var6, 8090);
      }
   }

   private static final void onCreate$lambda$60(MainActivity var0, View var1) {
      var0.discover();
   }

   private static final Unit onCreate$lambda$62(MainActivity var0, ObjectRef var1) {
      onCreate$pushTabletMode(var0, var1);
      return Unit.INSTANCE;
   }

   private static final Unit onCreate$lambda$66(MainActivity var0, ObjectRef var1, String var2, boolean var3) {
      Intrinsics.checkNotNullParameter(var2, "s");
      var0.ui.post(new MainActivity$$ExternalSyntheticLambda98(var2, var0, var3, var1));
      return Unit.INSTANCE;
   }

   private static final void onCreate$lambda$66$lambda$65(String var0, MainActivity var1, boolean var2, ObjectRef var3) {
      if (StringsKt.startsWith$default(var0, "ack:", false, 2, null)) {
         Toast.makeText((Context)var1, StringsKt.removePrefix(var0, "ack: "), 0).show();
      } else {
         var1.setStatus(var0);
         if (!var2) {
            var3.element = null;
            var1.scheduleReconnect();
            PairScanner.INSTANCE.promptIfNeeded(var1, new MainActivity$$ExternalSyntheticLambda30(var1));
         } else if (StringsKt.startsWith$default(var0, "data: hello", false, 2, null)) {
            onCreate$pushTabletMode(var1, var3);
         }
      }
   }

   private static final Unit onCreate$lambda$66$lambda$65$lambda$64(MainActivity var0) {
      String var1 = var0.host;
      if (var1 != null) {
         var0.connectTo(var1, var0.port);
      }

      return Unit.INSTANCE;
   }

   private static final void onCreate$lambda$67(MainActivity var0, View var1) {
      var0.startActivity(new Intent((Context)var0, SettingsActivity.class).putExtra("host", var0.host));
   }

   private static final Unit onCreate$lambda$69(MainActivity var0, String var1) {
      Intrinsics.checkNotNullParameter(var1, "s");
      var0.ui.post(new MainActivity$$ExternalSyntheticLambda107(var0, var1));
      return Unit.INSTANCE;
   }

   private static final void onCreate$lambda$69$lambda$68(MainActivity var0, String var1) {
      var0.setStatus(var1);
   }

   private static final Unit onCreate$lambda$71(MainActivity var0, int var1, int var2) {
      var0.ui.post(new MainActivity$$ExternalSyntheticLambda20(var0, var1, var2));
      return Unit.INSTANCE;
   }

   private static final void onCreate$lambda$71$lambda$70(MainActivity var0, int var1, int var2) {
      var0.frameW = var1;
      var0.frameH = var2;
      OverlayView var4 = var0.overlay;
      OverlayView var3 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("overlay");
         var3 = null;
      }

      var3.setFrameSize(var1, var2);
      var0.placeVideo();
   }

   private static final Unit onCreate$lambda$73(MainActivity var0) {
      var0.ui.post(new MainActivity$$ExternalSyntheticLambda111(var0));
      return Unit.INSTANCE;
   }

   private static final void onCreate$lambda$73$lambda$72(MainActivity var0) {
      var0.placeVideo();
   }

   private static final Unit onCreate$lambda$74(String var0) {
      Intrinsics.checkNotNullParameter(var0, "s");
      Log.i("StarView/Main", "detector road video " + var0);
      return Unit.INSTANCE;
   }

   private static final Unit onCreate$lambda$75(int var0, int var1) {
      return Unit.INSTANCE;
   }

   private static final Unit onCreate$lambda$77$lambda$76(MainActivity var0, Image var1) {
      Intrinsics.checkNotNullParameter(var1, "img");
      var0.getDetector().offer(var1, false);
      return Unit.INSTANCE;
   }

   private static final Unit onCreate$lambda$78(String var0) {
      Intrinsics.checkNotNullParameter(var0, "s");
      Log.i("StarView/Main", "detector wide video " + var0);
      return Unit.INSTANCE;
   }

   private static final Unit onCreate$lambda$79(int var0, int var1) {
      return Unit.INSTANCE;
   }

   private static final Unit onCreate$lambda$81$lambda$80(MainActivity var0, Image var1) {
      Intrinsics.checkNotNullParameter(var1, "img");
      var0.getDetector().offer(var1, true);
      return Unit.INSTANCE;
   }

   private static final void onCreate$lambda$84$lambda$83(MainActivity var0, View var1) {
      UiSettings var3 = var0.settings;
      UiSettings var2 = null;
      UiSettings var4 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("settings");
         var4 = null;
      }

      var3 = var0.settings;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("settings");
      } else {
         var2 = var3;
      }

      var4.set("scene_view", var2.get("scene_view") ^ true);
      var0.uiTickNow();
   }

   private static final void onCreate$lambda$88$lambda$87(MainActivity var0, View var1) {
      var0.openDestinations();
   }

   private static final void onCreate$lambda$92$lambda$91(MainActivity var0, View var1) {
      var0.openMap();
   }

   private static final void onCreate$lambda$96$lambda$95(MainActivity var0, View var1) {
      var0.openQuick();
   }

   private static final void onCreate$pushTabletMode(MainActivity var0, ObjectRef<Boolean> var1) {
      UiSettings var5 = var0.settings;
      Object var4 = null;
      UiSettings var3 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("settings");
         var3 = null;
      }

      boolean var2 = var3.get("comma_screen_off");
      if (!Intrinsics.areEqual(var2, var1.element)) {
         DataClient var8 = var0.data;
         DataClient var7 = var8;
         if (var8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("data");
            var7 = null;
         }

         if (var7.getConnected()) {
            var1.element = (T)var2;
            DataClient var6 = var0.data;
            if (var6 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("data");
               var6 = (DataClient)var4;
            }

            var6.request("tablet_mode", MapsKt.mapOf(TuplesKt.to("on", var2)), new MainActivity$$ExternalSyntheticLambda96());
         }
      }
   }

   private static final Unit onCreate$pushTabletMode$lambda$61(JSONObject var0) {
      Intrinsics.checkNotNullParameter(var0, "it");
      return Unit.INSTANCE;
   }

   private static final void onNewIntent$lambda$14(MainActivity var0) {
      var0.openMap();
   }

   private final void openDestinations() {
      Intent var8 = new Intent((Context)this, DestinationActivity.class);
      double var1 = this.telemetry.getGpsLat();
      boolean var7 = Double.isInfinite(var1);
      boolean var6 = true;
      boolean var5;
      if (!var7 && !Double.isNaN(var1)) {
         var5 = true;
      } else {
         var5 = false;
      }

      if (var5) {
         var8.putExtra("lat", this.telemetry.getGpsLat()).putExtra("lon", this.telemetry.getGpsLon());
      } else {
         MapPane var9 = this.mapPane;
         if (var9 != null) {
            var1 = var9.carLat();
            double var3 = var9.carLon();
            if (!Double.isInfinite(var1) && !Double.isNaN(var1)) {
               var5 = var6;
            } else {
               var5 = false;
            }

            if (var5) {
               var8.putExtra("lat", var1).putExtra("lon", var3);
            }
         }
      }

      if (Nav.INSTANCE.getActive()) {
         String var12 = Nav.INSTANCE.routeAhead();
         if (var12 != null) {
            var8.putExtra("route", var12);
         }
      }

      this.destLauncher.launch(var8);
   }

   private final void openQuick() {
      if (this.quickPanel == null) {
         QuickPanel var3 = new QuickPanel(
            (Context)this,
            new QuickPanel.Actions() {

               @Override
               public void close() {
                  QuickPanel var3x = MainActivity.this.quickPanel;
                  if (var3x != null) {
                     FrameLayout var2 = MainActivity.this.root;
                     FrameLayout var1 = var2;
                     if (var2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("root");
                        var1 = null;
                     }

                     var1.removeView((View)var3x);
                  }

                  MainActivity.this.quickPanel = null;
               }

               @Override
               public void door(String var1) {
                  Intrinsics.checkNotNullParameter(var1, "cmd");
                  MainActivity.this.doorCommand(var1);
               }

               @Override
               public boolean experimental() {
                  return MainActivity.this.telemetry.pBool("ExperimentalMode", false);
               }

               @Override
               public boolean focus() {
                  return HudPresets.INSTANCE.focusOn((Context)MainActivity.this);
               }

               @Override
               public void listen() {
                  MainActivity.this.listenVoice();
               }

               @Override
               public boolean mapOpen() {
                  return MainActivity.this.getMapOpen();
               }

               @Override
               public int personality() {
                  return MainActivity.this.telemetry.pInt("LongitudinalPersonality", 1);
               }

               @Override
               public boolean saver() {
                  return MainActivity.this.getSaver();
               }

               @Override
               public boolean sceneOn() {
                  UiSettings var2 = MainActivity.this.settings;
                  UiSettings var1 = var2;
                  if (var2 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("settings");
                     var1 = null;
                  }

                  return var1.get("scene_view");
               }

               @Override
               public void setPersonality(int var1) {
                  DataClient var3x = MainActivity.this.data;
                  DataClient var2 = var3x;
                  if (var3x == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("data");
                     var2 = null;
                  }

                  var2.control(
                     new JSONObject(
                        MapsKt.mapOf(new Pair[]{TuplesKt.to("action", "param"), TuplesKt.to("key", "LongitudinalPersonality"), TuplesKt.to("value", var1)})
                     )
                  );
               }

               @Override
               public void toggleExperimental() {
                  DataClient var2 = MainActivity.this.data;
                  DataClient var1 = var2;
                  if (var2 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("data");
                     var1 = null;
                  }

                  var1.control(new JSONObject(MapsKt.mapOf(TuplesKt.to("action", "exp_toggle"))));
               }

               @Override
               public void toggleFocus() {
                  HudPresets var3x = HudPresets.INSTANCE;
                  Context var4 = (Context)MainActivity.this;
                  HudLayout var2 = MainActivity.this.hud;
                  HudLayout var1 = var2;
                  if (var2 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("hud");
                     var1 = null;
                  }

                  var3x.toggleFocus(var4, var1);
                  MainActivity.this.layoutHud();
                  MainActivity.this.applyHud();
               }

               @Override
               public void toggleMap() {
                  if (MainActivity.this.getMapOpen()) {
                     MainActivity.this.closeMap();
                  } else {
                     MainActivity.this.openMap();
                  }
               }

               @Override
               public void toggleSaver() {
                  MainActivity.this.setSaver(MainActivity.this.getSaver() ^ true);
               }

               @Override
               public void toggleScene() {
                  UiSettings var3x = MainActivity.this.settings;
                  UiSettings var2 = null;
                  UiSettings var1 = var3x;
                  if (var3x == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("settings");
                     var1 = null;
                  }

                  var3x = MainActivity.this.settings;
                  if (var3x == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("settings");
                  } else {
                     var2 = var3x;
                  }

                  var1.set("scene_view", var2.get("scene_view") ^ true);
                  MainActivity.this.uiTickNow();
               }
            }
         );
         this.quickPanel = var3;
         FrameLayout var2 = this.root;
         FrameLayout var1 = var2;
         if (var2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            var1 = null;
         }

         var1.addView((View)var3, (LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -1)));
      }
   }

   private static final void pickBackup$lambda$24(MainActivity var0, Uri var1) {
      if (var1 != null) {
         SettingsBackup.INSTANCE.restoreFrom(var0, var1, new MainActivity$$ExternalSyntheticLambda27(var0));
      } else {
         var0.backupArmed = true;
      }
   }

   private static final Unit pickBackup$lambda$24$lambda$23(MainActivity var0) {
      var0.backupArmed = true;
      return Unit.INSTANCE;
   }

   private final void pivotToAnchor(View var1) {
      LayoutParams var5 = var1.getLayoutParams();
      android.widget.FrameLayout.LayoutParams var7;
      if (var5 instanceof android.widget.FrameLayout.LayoutParams) {
         var7 = (android.widget.FrameLayout.LayoutParams)var5;
      } else {
         var7 = null;
      }

      if (var7 != null) {
         int var4 = var7.gravity;
         float var3 = 0.0F;
         float var2;
         switch (var4 & 7) {
            case 1:
               var2 = var1.getWidth() / 2.0F;
               break;
            case 5:
            case 8388613:
               var2 = var1.getWidth();
               break;
            default:
               var2 = 0.0F;
         }

         var1.setPivotX(var2);
         switch (var4 & 112) {
            case 16:
               var2 = var1.getHeight() / 2.0F;
               break;
            case 80:
               var2 = var1.getHeight();
               break;
            default:
               var2 = var3;
         }

         var1.setPivotY(var2);
      }
   }

   private final void placeDivider(int var1) {
      FrameLayout var5 = this.outer;
      Object var4 = null;
      FrameLayout var3 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("outer");
         var3 = null;
      }

      int var2 = var3.getWidth();
      View var6 = this.divider;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("divider");
         var6 = (View)var4;
      }

      if (this.getMapRight()) {
         var1 = var2 - var1;
      }

      var6.setTranslationX((float)var1 - this.dp(14));
   }

   private final void placeVideo() {
      OverlayView var3 = this.overlay;
      Object var2 = null;
      OverlayView var1 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("overlay");
         var1 = null;
      }

      Projection var4 = var1.getProj();
      if (this.frameW != 0 && var4.getValid()) {
         SurfaceView var11 = this.videoView;
         SurfaceView var6 = var11;
         if (var11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("videoView");
            var6 = null;
         }

         LayoutParams var7 = var6.getLayoutParams();
         Intrinsics.checkNotNull(var7, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
         var var5 = (android.widget.FrameLayout.LayoutParams & LayoutParams)var7;
         var5.gravity = 8388659;
         var5.width = MathKt.roundToInt(this.frameW * var4.getVideoScale());
         var5.height = MathKt.roundToInt(this.frameH * var4.getVideoScale());
         SurfaceView var12 = this.videoView;
         SurfaceView var8 = var12;
         if (var12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("videoView");
            var8 = null;
         }

         var8.setLayoutParams((LayoutParams)var5);
         SurfaceView var13 = this.videoView;
         SurfaceView var9 = var13;
         if (var13 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("videoView");
            var9 = null;
         }

         var9.setTranslationX((float)var4.getVideoTx());
         SurfaceView var10 = this.videoView;
         if (var10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("videoView");
            var10 = (SurfaceView)var2;
         }

         var10.setTranslationY((float)var4.getVideoTy());
      }
   }

   private final void previewPlace(Map<String, ? extends Object> var1, JSONObject var2, Function0<Unit> var3) {
      this.openMap();
      Nav var7 = Nav.INSTANCE;
      DataClient var6 = this.data;
      Map var5 = null;
      DataClient var4 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("data");
         var4 = null;
      }

      MapPane var9 = this.mapPane;
      if (var9 != null) {
         var5 = var9.liveOrigin();
      }

      var7.startPreview(var4, var1, var2, var5, new MainActivity$$ExternalSyntheticLambda25(var1, var3));
      MapPane var8 = this.mapPane;
      if (var8 != null) {
         var8.showPreview();
      }
   }

   private static final Unit previewPlace$lambda$178(Map var0, Function0 var1, String var2) {
      if (var2 != null
         && (
            StringsKt.contains$default(var2, "unknown action", false, 2, null)
               || StringsKt.contains$default(var2, "Choose a place from search results", false, 2, null) && !var0.containsKey("placeId")
         )) {
         Nav.INSTANCE.cancelPreview();
         var1.invoke();
      }

      return Unit.INSTANCE;
   }

   private final void runVoice(String var1) {
      VoiceCommands.Cmd var11 = VoiceCommands.INSTANCE.parse(var1);
      boolean var4 = this.telemetry.pBool("ExperimentalMode", false);
      int var3 = this.telemetry.pInt("LongitudinalPersonality", 1);
      VoiceCommands.Kind var5 = var11.getKind();
      int var2 = MainActivity.WhenMappings.$EnumSwitchMapping$0[var5.ordinal()];
      Object var9 = null;
      HudPresets var19 = null;
      Object var6 = null;
      Object var10 = null;
      Object var8 = null;
      HudPresets var7 = null;
      switch (var2) {
         case 1:
            if (!var4) {
               DataClient var18 = this.data;
               if (var18 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("data");
                  var18 = (DataClient)var8;
               }

               var18.control(new JSONObject(MapsKt.mapOf(TuplesKt.to("action", "exp_toggle"))));
            }

            var1 = "Experimental on";
            break;
         case 2:
            if (var4) {
               DataClient var17 = this.data;
               if (var17 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("data");
                  var17 = (DataClient)var10;
               }

               var17.control(new JSONObject(MapsKt.mapOf(TuplesKt.to("action", "exp_toggle"))));
            }

            var1 = "Chill mode";
            break;
         case 3:
            if (!this.getMapOpen()) {
               this.openMap();
            }

            var1 = "Map open";
            break;
         case 4:
            if (this.getMapOpen()) {
               this.closeMap();
            }

            var1 = "Map closed";
            break;
         case 5:
            if (!HudPresets.INSTANCE.focusOn((Context)this)) {
               var19 = HudPresets.INSTANCE;
               var7 = (Context)this;
               HudLayout var16 = this.hud;
               if (var16 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("hud");
                  var16 = (HudLayout)var6;
               }

               var19.toggleFocus((Context)var7, var16);
               this.layoutHud();
               this.applyHud();
            }

            var1 = "Highway focus on";
            break;
         case 6:
            if (HudPresets.INSTANCE.focusOn((Context)this)) {
               var7 = HudPresets.INSTANCE;
               var6 = (Context)this;
               HudLayout var15 = this.hud;
               if (var15 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("hud");
                  var15 = var19;
               }

               var7.toggleFocus((Context)var6, var15);
               this.layoutHud();
               this.applyHud();
            }

            var1 = "All widgets back";
            break;
         case 7:
            Speech.INSTANCE.setEnabled(true);
            var1 = "Voice on";
            break;
         case 8:
            Speech.INSTANCE.setEnabled(false);
            var1 = "Chimes only";
            break;
         case 9:
            this.setSaver(true);
            var1 = "Battery saver on";
            break;
         case 10:
            this.setSaver(false);
            var1 = "Battery saver off";
            break;
         case 11:
            KeepDrive.INSTANCE.keepNewest((Context)this);
            var1 = "Keeping this drive";
            break;
         case 12:
            runVoice$setP(this, Integer.parseInt(var11.getArg()));
            var1 = CollectionsKt.listOf(new String[]{"Aggressive", "Standard", "Relaxed"}).get(Integer.parseInt(var11.getArg()));
            break;
         case 13:
            runVoice$setP(this, var3 + 1);
            var1 = "More room";
            break;
         case 14:
            runVoice$setP(this, var3 - 1);
            var1 = "Following closer";
            break;
         case 15:
            UiSettings var14 = this.settings;
            if (var14 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("settings");
               var14 = (UiSettings)var9;
            }

            var14.set("scene_view", true);
            this.uiTickNow();
            var1 = "Scene view";
            break;
         case 16:
            UiSettings var13 = this.settings;
            if (var13 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("settings");
               var13 = var7;
            }

            var13.set("scene_view", false);
            this.uiTickNow();
            var1 = "Camera view";
            break;
         case 17:
            var1 = this.voiceNavigate(var11.getArg(), true);
            break;
         case 18:
            var1 = this.voiceNudge(var11.getArg());
            break;
         case 19:
            this.bookmarkNow();
            var1 = "Bookmarked";
            break;
         case 20:
            var1 = "Didn't catch that: \"" + var1 + "\"";
            break;
         default:
            throw new NoWhenBranchMatchedException();
      }

      QuickPanel var21 = this.quickPanel;
      if (var21 != null) {
         var21.paint();
      }

      Toast.makeText((Context)this, var1, 0).show();
   }

   private static final void runVoice$setP(MainActivity var0, int var1) {
      DataClient var2 = var0.data;
      DataClient var3 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("data");
         var3 = null;
      }

      var3.control(
         new JSONObject(
            MapsKt.mapOf(
               new Pair[]{TuplesKt.to("action", "param"), TuplesKt.to("key", "LongitudinalPersonality"), TuplesKt.to("value", RangesKt.coerceIn(var1, 0, 2))}
            )
         )
      );
   }

   private final void saverTick(boolean var1) {
      if (var1 && !this.saverLinked && this.getSaver()) {
         this.sendSaver(true, false, false);
      }

      this.saverLinked = var1;
   }

   private final void scheduleReconnect() {
      if (!this.reconnectPending) {
         this.reconnectPending = true;
         this.stopVideo();
         this.ui.postDelayed(new MainActivity$$ExternalSyntheticLambda62(this), 2000L);
      }
   }

   private static final void scheduleReconnect$lambda$161(MainActivity var0) {
      var0.reconnectPending = false;
      var0.discover();
   }

   private final void sendSaver(boolean var1, boolean var2, boolean var3) {
      DataClient var5 = this.data;
      DataClient var4 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("data");
         var4 = null;
      }

      var4.request(
         "tablet_saver",
         MapsKt.mapOf(new Pair[]{TuplesKt.to("on", var1), TuplesKt.to("force", var2)}),
         new MainActivity$$ExternalSyntheticLambda95(var1, var3, this)
      );
   }

   private static final Unit sendSaver$lambda$187(boolean var0, boolean var1, MainActivity var2, JSONObject var3) {
      Intrinsics.checkNotNullParameter(var3, "r");
      String var7;
      if (!var3.optBoolean("ok")) {
         var7 = var3.optString("error", "no answer");
         var7 = "Battery saver: " + var7;
      } else if (var3.optBoolean("already")) {
         var7 = null;
      } else if (var0) {
         int var4 = var3.optInt("stopped", var3.optInt("quieted"));
         var7 = "Battery saver on: " + var4 + " other apps stopped and kept asleep; StarView untouched";
      } else {
         int var6 = var3.optInt("restored");
         var7 = "Battery saver off: " + var6 + " apps and the scanning settings back as they were";
      }

      if (var7 != null && (var1 || !var3.optBoolean("ok"))) {
         Toast.makeText((Context)var2, var7, 1).show();
      }

      return Unit.INSTANCE;
   }

   private final void setDestinationNow(String var1, String var2, double var3, double var5) {
      DataClient var8 = this.data;
      DataClient var7 = var8;
      if (var8 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("data");
         var7 = null;
      }

      var7.request(
         "nav_set",
         MapsKt.mapOf(new Pair[]{TuplesKt.to("name", var1), TuplesKt.to("address", var2), TuplesKt.to("latitude", var3), TuplesKt.to("longitude", var5)}),
         new MainActivity$$ExternalSyntheticLambda104(this)
      );
   }

   private static final Unit setDestinationNow$lambda$181(MainActivity var0, JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "ack");
      if (var1.optBoolean("ok")) {
         Nav var4 = Nav.INSTANCE;
         DataClient var3 = var0.data;
         DataClient var9 = var3;
         if (var3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("data");
            var9 = null;
         }

         var4.newDestination(var9);
         var0.openMap();
         MapPane var5 = var0.mapPane;
         if (var5 != null) {
            var5.expectRoutes();
         }

         return Unit.INSTANCE;
      } else {
         String var6 = var1.optString("error");
         Intrinsics.checkNotNull(var6);
         String var7;
         if (!StringsKt.contains(var6, "unknown", true) && !StringsKt.contains(var6, "action", true)) {
            Object var8 = var6;
            boolean var2;
            if (var8.length() == 0) {
               var2 = true;
            } else {
               var2 = false;
            }

            if (var2) {
               var8 = "no answer";
            }

            var7 = "StarPilot didn't take the destination: " + var8;
         } else {
            var7 = "The comma doesn't take destinations from the tablet yet (StarView patch 9)";
         }

         Toast.makeText((Context)var0, var7, 1).show();
         return Unit.INSTANCE;
      }
   }

   private final void setSaver(boolean var1) {
      UiSettings var3 = this.settings;
      UiSettings var2 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("settings");
         var2 = null;
      }

      var2.set("battery_saver", var1);
      this.applySaver();
      QuickPanel var4 = this.quickPanel;
      if (var4 != null) {
         var4.paint();
      }

      Context var6 = (Context)this;
      String var5;
      if (var1) {
         var5 = "Battery saver: quieting the tablet…";
      } else {
         var5 = "Battery saver off: putting the tablet back…";
      }

      Toast.makeText(var6, var5, 0).show();
      this.sendSaver(var1, true, true);
   }

   private final void setStatus(String var1) {
      if (!Intrinsics.areEqual(var1, this.lastStatus)) {
         this.lastStatus = var1;
         TextView var3 = this.statusTv;
         TextView var2 = var3;
         if (var3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("statusTv");
            var2 = null;
         }

         var2.setText(var1);
         Log.i("StarView/Main", var1);
      }
   }

   private final void setupMapFrame() {
      ViewGroup var5 = this.findViewById(16908290);
      FrameLayout var2 = this.root;
      Object var3 = null;
      FrameLayout var1 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("root");
         var1 = null;
      }

      var5.removeView((View)var1);
      var1 = new FrameLayout((Context)this);
      var1.setBackgroundColor(-16777216);
      this.outer = var1;
      var2 = this.outer;
      var1 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("outer");
         var1 = null;
      }

      FrameLayout var4 = this.root;
      var2 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("root");
         var2 = null;
      }

      var1.addView((View)var2, (LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -1)));
      var2 = this.outer;
      var1 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("outer");
         var1 = null;
      }

      var5.addView((View)var1, (LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -1)));
      View var9 = new View(this) {
         private final Paint p;
         final MainActivity this$0;

         {
            this.this$0 = var1;
            this.p = new Paint(1);
         }

         protected void onDraw(Canvas var1) {
            Intrinsics.checkNotNullParameter(var1, "c");
            float var8 = this.getWidth();
            float var4x = this.getHeight();
            this.p.setColor(Color.rgb(16, 18, 22));
            float var3x = 2;
            var1.drawRect(var8 / var3x - this.this$0.dp(3), 0.0F, var8 / var3x + this.this$0.dp(3), var4x, this.p);
            this.p.setColor(Color.argb(220, 210, 215, 225));
            float var5x = var8 / var3x;
            float var2x = this.this$0.dp(3);
            float var6 = var4x / var3x;
            float var7 = this.this$0.dp(36);
            var8 /= var3x;
            var1.drawRoundRect(
               var5x - var2x, var6 - var7, this.this$0.dp(3) + var8, var4x / var3x + this.this$0.dp(36), this.this$0.dp(3), this.this$0.dp(3), this.p
            );
         }
      };
      var9.setVisibility(8);
      var9.setElevation(30.0F);
      this.divider = var9;
      var2 = this.outer;
      var1 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("outer");
         var1 = null;
      }

      View var23 = this.divider;
      View var19 = var23;
      if (var23 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("divider");
         var19 = null;
      }

      var1.addView(var19, (LayoutParams)(new android.widget.FrameLayout.LayoutParams(this.dp(28), -1)));
      IntRef var24 = new IntRef();
      LongRef var25 = new LongRef();
      View var20 = this.divider;
      View var11 = var20;
      if (var20 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("divider");
         var11 = null;
      }

      var11.setOnTouchListener(new MainActivity$$ExternalSyntheticLambda29(this, var24, var25));
      var2 = this.outer;
      var1 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("outer");
         var1 = null;
      }

      var1.addOnLayoutChangeListener(new MainActivity$$ExternalSyntheticLambda40(this));
      UiSettings var22 = this.settings;
      UiSettings var13 = var22;
      if (var22 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("settings");
         var13 = null;
      }

      if (var13.get("map_open")) {
         var1 = this.outer;
         if (var1 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("outer");
            var1 = (FrameLayout)var3;
         }

         var1.post(new MainActivity$$ExternalSyntheticLambda51(this));
      }
   }

   private static final boolean setupMapFrame$lambda$215(MainActivity var0, IntRef var1, LongRef var2, View var3, MotionEvent var4) {
      FrameLayout var11 = var0.outer;
      Object var10 = null;
      FrameLayout var13 = var11;
      if (var11 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("outer");
         var13 = null;
      }

      int var6 = var13.getWidth();
      float var5 = var4.getRawX();
      var11 = var0.outer;
      FrameLayout var14 = var11;
      if (var11 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("outer");
         var14 = null;
      }

      var5 -= var0.locationOnScreenX((View)var14);
      if (var0.getMapRight()) {
         var5 = var6 - var5;
      }

      int var7 = MathKt.roundToInt(var5);
      switch (var4.getActionMasked()) {
         case 0:
            var1.element = var0.mapWidth();
            break;
         case 1:
         case 3:
            if (var1.element < var6 * 0.17F) {
               var0.closeMap();
            } else {
               UiSettings var12 = var0.settings;
               if (var12 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("settings");
                  var12 = (UiSettings)var10;
               }

               var12.setFloat("map_frac", (float)var1.element / var6);
               var0.layoutMap(var0.mapWidth());
            }
            break;
         case 2:
            var1.element = RangesKt.coerceIn(var7, 0, MathKt.roundToInt(var6 * 0.72F));
            long var8 = System.currentTimeMillis();
            if (var8 - var2.element > 70L) {
               var2.element = var8;
               var0.layoutMap(RangesKt.coerceAtLeast(var1.element, var0.dp(220)));
            }

            var0.placeDivider(var1.element);
      }

      return true;
   }

   private static final void setupMapFrame$lambda$216(
      MainActivity var0, View var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9
   ) {
      if (var4 - var2 != var8 - var6 && var0.getMapOpen()) {
         var0.layoutMap(var0.mapWidth());
      }
   }

   private static final void setupMapFrame$lambda$217(MainActivity var0) {
      var0.openMap();
   }

   private final void shareIntent(Intent var1) {
      if (var1 != null) {
         String var5;
         label31: {
            var5 = var1.getAction();
            if (var5 != null) {
               switch (var5.hashCode()) {
                  case -1173264947:
                     if (var5.equals("android.intent.action.SEND")) {
                        var5 = var1.getStringExtra("android.intent.extra.TEXT");
                        break label31;
                     }
                     break;
                  case -1173171990:
                     if (var5.equals("android.intent.action.VIEW")) {
                        var5 = var1.getDataString();
                        break label31;
                     }
               }
            }

            var5 = null;
         }

         if (var5 != null) {
            var1.setAction(null);
            String var3 = var1.getStringExtra("android.intent.extra.SUBJECT");
            String var4 = var3;
            if (var3 == null) {
               var4 = "";
            }

            new Thread(new MainActivity$$ExternalSyntheticLambda99(var5, var4, this)).start();
         }
      }
   }

   private static final void shareIntent$lambda$18(String var0, String var1, MainActivity var2) {
      String var5 = SharedPlace.INSTANCE.link(var0);
      String var4 = var5;
      if (var5 == null) {
         if (StringsKt.startsWith$default(var0, "geo:", false, 2, null)) {
            var4 = var0;
         } else {
            var4 = null;
         }
      }

      ObjectRef var9 = new ObjectRef();
      SharedPlace var6 = SharedPlace.INSTANCE;
      boolean var3;
      if (var1.length() > 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (var3 && !StringsKt.contains$default(var0, var1, false, 2, null)) {
         var1 = var1 + "\n" + var0;
      } else {
         var1 = var0;
      }

      var9.element = (T)SharedPlace.parse$default(var6, var1, null, 2, null);
      if (!((SharedPlace.Place)var9.element).getHasCoords() && var4 != null && SharedPlace.INSTANCE.isShort(var4)) {
         var1 = SharedPlace.INSTANCE.follow(var4);
         if (var1 != null) {
            var9.element = (T)SharedPlace.INSTANCE.parse(var0, var1);
         }
      }

      var2.runOnUiThread(new MainActivity$$ExternalSyntheticLambda97(var2, var9));
   }

   private static final void shareIntent$lambda$18$lambda$17(MainActivity var0, ObjectRef var1) {
      var0.sharedPending = (SharedPlace.Place)var1.element;
      var0.useShared();
   }

   private static final TextView stallBanner_delegate$lambda$4(MainActivity var0) {
      TextView var2 = new TextView((Context)var0);
      var2.setTextColor(-1);
      var2.setTextSize(17.0F);
      var2.setGravity(17);
      var2.setPadding(36, 20, 36, 20);
      var2.setVisibility(8);
      GradientDrawable var1 = new GradientDrawable();
      var1.setCornerRadius(28.0F);
      var1.setColor(Color.rgb(179, 38, 30));
      var2.setBackground((Drawable)var1);
      var2.setElevation(20.0F);
      FrameLayout var6 = var0.root;
      FrameLayout var5 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("root");
         var5 = null;
      }

      View var7 = (View)var2;
      android.widget.FrameLayout.LayoutParams var3 = new android.widget.FrameLayout.LayoutParams(-2, -2, 49);
      var3.topMargin = 170;
      Unit var4 = Unit.INSTANCE;
      var5.addView(var7, (LayoutParams)var3);
      return var2;
   }

   private final void startHudEdit() {
      if (this.hudEditor == null) {
         LinearLayout var2 = this.sidebar;
         Object var3 = null;
         LinearLayout var1 = var2;
         if (var2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sidebar");
            var1 = null;
         }

         var1.setVisibility(8);
         Context var4 = (Context)this;
         FrameLayout var5 = this.root;
         if (var5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            var5 = null;
         }

         HudLayout var7 = this.hud;
         if (var7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("hud");
            var7 = null;
         }

         HudEditor var8 = new HudEditor(
            var4, var5, var7, new MainActivity$$ExternalSyntheticLambda106(this), new MainActivity$$ExternalSyntheticLambda117(this)
         );
         this.hudEditor = var8;
         FrameLayout var6 = this.root;
         if (var6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            var6 = (FrameLayout)var3;
         }

         var6.addView((View)var8, (LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -1)));
         this.applyHud();
      }
   }

   private static final Unit startHudEdit$lambda$225(MainActivity var0) {
      var0.applyHud();
      return Unit.INSTANCE;
   }

   private static final Unit startHudEdit$lambda$226(MainActivity var0) {
      var0.stopHudEdit();
      return Unit.INSTANCE;
   }

   private final void startMaskEdit() {
      if (this.maskEditor == null) {
         LinearLayout var3 = this.sidebar;
         Object var2 = null;
         LinearLayout var1 = var3;
         if (var3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sidebar");
            var1 = null;
         }

         var1.setVisibility(8);
         Context var4 = (Context)this;
         Detector var5 = this.getDetector();
         UiSettings var9 = this.settings;
         UiSettings var6 = var9;
         if (var9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("settings");
            var6 = null;
         }

         MaskEditor var11 = new MaskEditor(
            var4, var5, var6.get("wide_camera"), new MainActivity$$ExternalSyntheticLambda21(), new MainActivity$$ExternalSyntheticLambda22(this)
         );
         this.maskEditor = var11;
         var11.setElevation(40.0F);
         var11.getBar().setElevation(41.0F);
         FrameLayout var10 = this.root;
         FrameLayout var7 = var10;
         if (var10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            var7 = null;
         }

         var7.addView(var11, (LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -1)));
         FrameLayout var8 = this.root;
         if (var8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            var8 = (FrameLayout)var2;
         }

         var8.addView((View)var11.getBar(), (LayoutParams)(new android.widget.FrameLayout.LayoutParams(-2, -2, 49)));
      }
   }

   private static final Unit startMaskEdit$lambda$21(boolean var0) {
      return Unit.INSTANCE;
   }

   private static final Unit startMaskEdit$lambda$22(MainActivity var0) {
      var0.stopMaskEdit();
      return Unit.INSTANCE;
   }

   private final void startVideo() {
      String var6 = this.host;
      if (var6 != null) {
         if (this.surfaceReady && !this.videoConnected) {
            String var2;
            if (this.telemetry.s("wide_camera")) {
               var2 = "wide";
            } else {
               var2 = "road";
            }

            OverlayView var5 = this.overlay;
            SurfaceView var4 = null;
            OverlayView var3 = var5;
            if (var5 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("overlay");
               var3 = null;
            }

            var3.getProj().setWide(Intrinsics.areEqual(var2, "wide"));
            VideoClient var8 = this.video;
            VideoClient var7 = var8;
            if (var8 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("video");
               var7 = null;
            }

            int var1 = this.port;
            SurfaceView var9 = this.videoView;
            if (var9 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("videoView");
            } else {
               var4 = var9;
            }

            var7.connect(var6, var1, var2, var4.getHolder().getSurface());
            this.videoConnected = true;
         }
      }
   }

   private final void stopHudEdit() {
      HudEditor var3 = this.hudEditor;
      if (var3 != null) {
         FrameLayout var2 = this.root;
         FrameLayout var1 = var2;
         if (var2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            var1 = null;
         }

         var1.removeView((View)var3);
         this.hudEditor = null;
         if (this.backupArmed) {
            SettingsBackup.INSTANCE.auto((Context)this);
         }

         this.applyHud();
      }
   }

   private final void stopMaskEdit() {
      MaskEditor var3 = this.maskEditor;
      if (var3 != null) {
         FrameLayout var2 = this.root;
         FrameLayout var1 = var2;
         if (var2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            var1 = null;
         }

         var1.removeView(var3);
         var2 = this.root;
         var1 = var2;
         if (var2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            var1 = null;
         }

         var1.removeView((View)var3.getBar());
         this.maskEditor = null;
      }
   }

   private final void stopVideo() {
      if (this.videoConnected) {
         VideoClient var2 = this.video;
         VideoClient var1 = var2;
         if (var2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("video");
            var1 = null;
         }

         var1.close();
         this.videoConnected = false;
      }
   }

   private static final OkHttpClient storageHttp_delegate$lambda$162() {
      return new okhttp3.OkHttpClient.Builder()
         .addInterceptor(Link.INSTANCE.getAuthInterceptor())
         .connectTimeout(3L, TimeUnit.SECONDS)
         .readTimeout(5L, TimeUnit.SECONDS)
         .build();
   }

   private final void syncDetector(boolean var1) {
      if (var1 || this.detectorRef != null) {
         boolean var4;
         MaskEditor var5;
         String var8;
         boolean var10;
         label134: {
            var8 = this.host;
            var4 = this.telemetry.s("wide_camera");
            this.getDetector().setShownWide(var4);
            var5 = this.maskEditor;
            if (var1 && var8 != null) {
               if (var5 != null ? !var5.getWide() : this.sceneOn || !var4) {
                  var10 = true;
               } else {
                  var10 = false;
               }

               if (var10) {
                  var10 = true;
                  break label134;
               }
            }

            var10 = false;
         }

         boolean var3;
         label119: {
            if (var1 && var8 != null) {
               if (var5 != null) {
                  var1 = var5.getWide();
               } else if (!this.sceneOn && !var4) {
                  var1 = false;
               } else {
                  var1 = true;
               }

               if (var1) {
                  var3 = true;
                  break label119;
               }
            }

            var3 = false;
         }

         Object var6 = null;
         if (var10) {
            VideoClient var7 = this.detRoad;
            VideoClient var11 = var7;
            if (var7 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("detRoad");
               var11 = null;
            }

            if (!var11.isRunning()) {
               var7 = this.detRoad;
               VideoClient var12 = var7;
               if (var7 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("detRoad");
                  var12 = null;
               }

               Intrinsics.checkNotNull(var8);
               var12.connect(var8, this.port, "road", null);
            }
         }

         if (!var10) {
            VideoClient var20 = this.detRoad;
            VideoClient var13 = var20;
            if (var20 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("detRoad");
               var13 = null;
            }

            if (var13.isRunning()) {
               var20 = this.detRoad;
               VideoClient var14 = var20;
               if (var20 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("detRoad");
                  var14 = null;
               }

               var14.close();
               this.getDetector().clearSource(false);
            }
         }

         if (var3) {
            VideoClient var22 = this.detWide;
            VideoClient var15 = var22;
            if (var22 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("detWide");
               var15 = null;
            }

            if (!var15.isRunning()) {
               var22 = this.detWide;
               VideoClient var16 = var22;
               if (var22 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("detWide");
                  var16 = null;
               }

               Intrinsics.checkNotNull(var8);
               var16.connect(var8, this.port, "wide", null);
            }
         }

         if (!var3) {
            VideoClient var24 = this.detWide;
            VideoClient var17 = var24;
            if (var24 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("detWide");
               var17 = null;
            }

            if (var17.isRunning()) {
               VideoClient var18 = this.detWide;
               if (var18 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("detWide");
                  var18 = (VideoClient)var6;
               }

               var18.close();
               this.getDetector().clearSource(true);
            }
         }

         if ((var10 || var3) && !this.getDetector().isRunning()) {
            this.getDetector().start();
         }

         if (!var10 && !var3 && this.getDetector().isRunning()) {
            this.getDetector().stop();
         }
      }
   }

   private final void syncFavBar(boolean var1) {
      Favorites var6 = Favorites.INSTANCE;
      UiSettings var5 = this.settings;
      UiSettings var4 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("settings");
         var4 = null;
      }

      List var19 = var6.pins(var4);
      int var3 = 0;
      boolean var2;
      if (var1 && !var19.isEmpty() && System.currentTimeMillis() > this.favHiddenUntil) {
         var2 = true;
      } else {
         var2 = false;
      }

      LinearLayout var17 = this.favBar;
      LinearLayout var13 = var17;
      if (var17 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("favBar");
         var13 = null;
      }

      if (!var2) {
         var3 = 8;
      }

      var13.setVisibility(var3);
      if (var2) {
         String var14 = CollectionsKt.joinToString$default(var19, "|", null, null, 0, null, new MainActivity$$ExternalSyntheticLambda14(), 30, null);
         var3 = Favorites.INSTANCE.getVersion();
         String var18 = var14 + var3;
         if (!Intrinsics.areEqual(var18, this.favKey)) {
            this.favKey = var18;
            LinearLayout var7 = this.favBar;
            LinearLayout var15 = var7;
            if (var7 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("favBar");
               var15 = null;
            }

            var15.removeAllViews();

            for (JSONObject var9 : CollectionsKt.take(var19, 6)) {
               Pair var20 = Favorites.INSTANCE.pinTitle(var9);
               String var11 = (String)var20.component1();
               String var10 = (String)var20.component2();
               LinearLayout var8 = this.favBar;
               var7 = var8;
               if (var8 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("favBar");
                  var7 = null;
               }

               TextView var22 = new TextView((Context)this);
               var22.setText(var11 + "  " + var10);
               var22.setTextSize(15.0F);
               var22.setTypeface(null, 1);
               var22.setTextColor(-1);
               var22.setMaxLines(1);
               var22.setPadding(this.dp(16), this.dp(10), this.dp(16), this.dp(10));
               GradientDrawable var26 = new GradientDrawable();
               var26.setCornerRadius(this.dp(22));
               var26.setColor(Color.argb(170, 20, 22, 28));
               var26.setStroke(this.dp(1), Color.argb(110, 255, 200, 87));
               var22.setBackground((Drawable)var26);
               var22.setOnClickListener(new MainActivity$$ExternalSyntheticLambda15(this, var10, var9));
               View var24 = (View)var22;
               android.widget.LinearLayout.LayoutParams var23 = new android.widget.LinearLayout.LayoutParams(-2, -2);
               var23.setMarginEnd(this.dp(10));
               Unit var25 = Unit.INSTANCE;
               var7.addView(var24, (LayoutParams)var23);
            }
         }
      }
   }

   private static final CharSequence syncFavBar$lambda$182(JSONObject var0) {
      Intrinsics.checkNotNullParameter(var0, "it");
      Pair var1 = Favorites.INSTANCE.pinTitle(var0);
      String var2 = (String)var1.component1();
      String var3 = (String)var1.component2();
      return var2 + var3;
   }

   private static final void syncFavBar$lambda$185$lambda$184(MainActivity var0, String var1, JSONObject var2, View var3) {
      var0.favHiddenUntil = System.currentTimeMillis() + 10000;
      LinearLayout var4 = var0.favBar;
      LinearLayout var5 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("favBar");
         var5 = null;
      }

      var5.setVisibility(8);
      String var6 = var2.optString("address");
      Intrinsics.checkNotNullExpressionValue(var6, "optString(...)");
      var0.driveTo(var1, var6, var2.optDouble("latitude"), var2.optDouble("longitude"));
   }

   private final void syncScene(boolean var1) {
      boolean var3;
      byte var4;
      boolean var5;
      Object var10;
      label264: {
         var3 = 1;
         var4 = 0;
         var10 = null;
         if (var1) {
            UiSettings var8 = this.settings;
            UiSettings var7 = var8;
            if (var8 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("settings");
               var7 = null;
            }

            if (var7.get("scene_view")) {
               var5 = true;
               break label264;
            }
         }

         var5 = false;
      }

      if (var5 && this.scene == null) {
         SceneView var12 = new SceneView((Context)this, null, 2, null);
         var12.setT(this.telemetry);
         UiSettings var53 = this.settings;
         UiSettings var22 = var53;
         if (var53 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("settings");
            var22 = null;
         }

         var12.setDistance(var22.getFloat("scene_distance4", 26.0F));
         var53 = this.settings;
         var22 = var53;
         if (var53 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("settings");
            var22 = null;
         }

         var12.setTiltDeg(var22.getFloat("scene_tilt4", 17.0F));
         var12.setOnViewChanged(new MainActivity$$ExternalSyntheticLambda101(this));
         var12.setOnLongPress(new MainActivity$$ExternalSyntheticLambda102(this));
         var12.setDetector(this.getDetector());
         FrameLayout var55 = this.root;
         FrameLayout var24 = var55;
         if (var55 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            var24 = null;
         }

         View var13 = (View)var12;
         FrameLayout var9 = this.root;
         FrameLayout var56 = var9;
         if (var9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            var56 = null;
         }

         OverlayView var11 = this.overlay;
         OverlayView var76 = var11;
         if (var11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("overlay");
            var76 = null;
         }

         var24.addView(var13, var56.indexOfChild(var76), (LayoutParams)(new android.widget.FrameLayout.LayoutParams(-1, -1)));
         this.scene = var12;
      }

      SceneView var25 = this.scene;
      if (var25 != null) {
         byte var2;
         if (var5) {
            var2 = 0;
         } else {
            var2 = 8;
         }

         if (var25.getVisibility() != var2) {
            var25.setVisibility(var2);
         }
      }

      if (var5 != this.sceneOn) {
         this.sceneOn = var5;
         OverlayView var57 = this.overlay;
         OverlayView var26 = var57;
         if (var57 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("overlay");
            var26 = null;
         }

         var26.setSceneMode(var5);
         var57 = this.overlay;
         OverlayView var27 = var57;
         if (var57 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("overlay");
            var27 = null;
         }

         boolean var6;
         if (!var5) {
            var6 = true;
         } else {
            var6 = false;
         }

         var27.setLongClickable(var6);
         var57 = this.overlay;
         OverlayView var28 = var57;
         if (var57 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("overlay");
            var28 = null;
         }

         if (!var5) {
            var6 = true;
         } else {
            var6 = false;
         }

         var28.setClickable(var6);
         SurfaceView var60 = this.videoView;
         SurfaceView var29 = var60;
         if (var60 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("videoView");
            var29 = null;
         }

         byte var14;
         if (var5) {
            var14 = 4;
         } else {
            var14 = 0;
         }

         var29.setVisibility(var14);
         this.applyRootBg();
         var57 = this.overlay;
         OverlayView var30 = var57;
         if (var57 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("overlay");
            var30 = null;
         }

         var30.invalidate();
      }

      SceneView var77 = this.scene;
      if (var77 != null) {
         var77.setNight(this.night);
         var77.setAlwaysDark(false);
         var77.setUiStatus(UiStatus.Companion.from(this.telemetry));
         UiSettings var62 = this.settings;
         UiSettings var31 = var62;
         if (var62 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("settings");
            var31 = null;
         }

         var77.setShowTraffic(var31.get("scene_cars"));
      }

      boolean var15;
      label252: {
         if (this.sceneOn) {
            LayoutSeg var63 = this.layoutSeg;
            LayoutSeg var32 = var63;
            if (var63 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("layoutSeg");
               var32 = null;
            }

            if (var32.getHasModel()) {
               var15 = 1;
               break label252;
            }
         }

         var15 = 0;
      }

      if (var15) {
         LayoutSeg var64 = this.layoutSeg;
         LayoutSeg var33 = var64;
         if (var64 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutSeg");
            var33 = null;
         }

         var33.start();
         var64 = this.layoutSeg;
         LayoutSeg var34 = var64;
         if (var64 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutSeg");
            var34 = null;
         }

         var34.setEnabled(true);
         Detector var78 = this.getDetector();
         var64 = this.layoutSeg;
         LayoutSeg var35 = var64;
         if (var64 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutSeg");
            var35 = null;
         }

         var78.setLayout(var35);
         var77 = this.scene;
         if (var77 != null) {
            var64 = this.layoutSeg;
            LayoutSeg var36 = var64;
            if (var64 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("layoutSeg");
               var36 = null;
            }

            var77.setLayout(var36);
         }
      } else {
         Detector var37 = this.detectorRef;
         LayoutSeg var38;
         if (var37 != null) {
            var38 = var37.getLayout();
         } else {
            var38 = null;
         }

         if (var38 != null) {
            Detector var39 = this.detectorRef;
            if (var39 != null) {
               var39.setLayout(null);
            }

            LayoutSeg var68 = this.layoutSeg;
            LayoutSeg var40 = var68;
            if (var68 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("layoutSeg");
               var40 = null;
            }

            var40.stop();
         }
      }

      label245: {
         LaneMarks var41;
         Detector var69;
         if (this.sceneOn) {
            var69 = this.getDetector();
            var41 = this.laneMarks;
         } else {
            var69 = this.detectorRef;
            if (var69 == null) {
               break label245;
            }

            UiSettings var80 = this.settings;
            UiSettings var42 = var80;
            if (var80 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("settings");
               var42 = null;
            }

            if (var42.get("lane_card")) {
               var41 = this.laneMarks;
            } else {
               var41 = null;
            }
         }

         var69.setLaneMarks(var41);
      }

      var25 = this.scene;
      if (var25 != null) {
         var25.setLaneMarks(this.laneMarks);
      }

      var25 = this.scene;
      if (var25 != null) {
         var25.setLaneMemory(this.laneTracker.getMemory());
      }

      var25 = this.scene;
      if (var25 != null) {
         var25.setCarMemory(this.carMemory);
      }

      Detector var81 = this.detectorRef;
      if (var81 != null) {
         UiSettings var70 = this.settings;
         UiSettings var46 = var70;
         if (var70 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("settings");
            var46 = null;
         }

         LaneModel var47;
         if (var46.get("lane_card") && this.getLaneModel().getHasModel()) {
            var47 = this.getLaneModel();
            var47.setEnabled(true);
         } else {
            var47 = null;
         }

         var81.setLaneModel(var47);
      }

      if (var1 && this.hudEditor == null) {
         var15 = (boolean)var3;
      } else {
         var15 = (boolean)0;
      }

      TextView var71 = this.sceneBtn;
      TextView var48 = var71;
      if (var71 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sceneBtn");
         var48 = null;
      }

      if (var15) {
         var3 = 0;
      } else {
         var3 = 8;
      }

      var48.setVisibility(var3);
      if (this.quickBtn != null) {
         var71 = this.quickBtn;
         TextView var49 = var71;
         if (var71 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("quickBtn");
            var49 = null;
         }

         if (var15) {
            var3 = 0;
         } else {
            var3 = 8;
         }

         var49.setVisibility(var3);
      }

      if (this.micBtn != null) {
         ImageView var73 = this.micBtn;
         ImageView var50 = var73;
         if (var73 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("micBtn");
            var50 = null;
         }

         if (var15) {
            var3 = 0;
         } else {
            var3 = 8;
         }

         var50.setVisibility(var3);
      }

      if (this.bookmarkBtn != null) {
         ImageView var74 = this.bookmarkBtn;
         ImageView var51 = var74;
         if (var74 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bookmarkBtn");
            var51 = null;
         }

         if (var15) {
            var15 = var4;
         } else {
            var15 = 8;
         }

         var51.setVisibility(var15);
      }

      TextView var52 = this.sceneBtn;
      if (var52 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sceneBtn");
         var52 = (TextView)var10;
      }

      String var75;
      if (var5) {
         var75 = "CAMERA";
      } else {
         var75 = "SCENE";
      }

      var52.setText(var75);
   }

   private static final Unit syncScene$lambda$168$lambda$166(MainActivity var0, float var1, float var2) {
      UiSettings var5 = var0.settings;
      Object var4 = null;
      UiSettings var3 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("settings");
         var3 = null;
      }

      var3.setFloat("scene_distance4", var1);
      UiSettings var6 = var0.settings;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("settings");
         var6 = (UiSettings)var4;
      }

      var6.setFloat("scene_tilt4", var2);
      return Unit.INSTANCE;
   }

   private static final Unit syncScene$lambda$168$lambda$167(MainActivity var0) {
      var0.startHudEdit();
      return Unit.INSTANCE;
   }

   private final void uiTickNow() {
      this.ui.removeCallbacks(this.uiTick);
      this.ui.post(this.uiTick);
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private final void updateTheme() {
      UiSettings var14 = this.settings;
      UiSettings var13 = var14;
      if (var14 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("settings");
         var13 = null;
      }

      String var15 = var13.getStr("theme_mode", "auto");
      Telemetry var24 = this.telemetry;
      String var34 = "day";
      boolean var12;
      if (Intrinsics.areEqual(var15, "day")) {
         var12 = false;
      } else if (Intrinsics.areEqual(var15, "night")) {
         var12 = true;
      } else {
         double var1;
         double var5;
         var1 = var24.getGpsLat();
         var5 = var24.getGpsLon();
         label154:
         if (Double.isNaN(var1)) {
            try {
               var44 = new JSONObject(Telemetry.pStr$default(var24, "LastGPSPosition", null, 2, null));
            } catch (Exception var19) {
               break label154;
            }

            double var3;
            try {
               var3 = var44.optDouble("latitude", Double.NaN);
            } catch (Exception var18) {
               break label154;
            }

            var1 = var3;

            double var7;
            try {
               var7 = var44.optDouble("longitude", Double.NaN);
            } catch (Exception var17) {
               break label154;
            }

            var5 = var7;
            var1 = var3;
         }

         if (!Double.isNaN(var1) && !Double.isNaN(var5)) {
            var12 = Theme.isNight$default(Theme.INSTANCE, var1, var5, 0L, 4, null);
         } else {
            int var10 = Calendar.getInstance().get(11);
            if (var10 >= 6 && var10 < 19) {
               var12 = false;
            } else {
               var12 = true;
            }
         }
      }

      if (var12 != this.night || !this.lastThemeApplied) {
         this.night = var12;
         this.lastThemeApplied = true;
         if (this.hud != null) {
            HudLayout var45 = this.hud;
            HudLayout var25 = var45;
            if (var45 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("hud");
               var25 = null;
            }

            if (var12) {
               var34 = "night";
            }

            var25.load(var34);
            this.layoutHud();
         }

         int var23;
         if (var12) {
            var23 = -1;
         } else {
            var23 = Color.rgb(29, 29, 31);
         }

         int var11;
         if (var12) {
            var11 = Color.argb(200, 255, 255, 255);
         } else {
            var11 = Color.rgb(110, 110, 115);
         }

         TextView var35 = this.speedTv;
         TextView var26 = var35;
         if (var35 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("speedTv");
            var26 = null;
         }

         TextView var46 = this.roadName;
         TextView var36 = var46;
         if (var46 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("roadName");
            var36 = null;
         }

         TextView var16 = this.compass;
         TextView var47 = var16;
         if (var16 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("compass");
            var47 = null;
         }

         for (TextView var27 : CollectionsKt.listOf(new TextView[]{var26, var36, var47})) {
            var27.setTextColor(var23);
            float var9;
            if (var12) {
               var9 = 12.0F;
            } else {
               var9 = 0.0F;
            }

            var27.setShadowLayer(var9, 0.0F, 0.0F, -16777216);
         }

         if (this.clockTv != null) {
            TextView var38 = this.clockTv;
            TextView var28 = var38;
            if (var38 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("clockTv");
               var28 = null;
            }

            TextView var48 = this.modeText;
            TextView var39 = var48;
            if (var48 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("modeText");
               var39 = null;
            }

            for (TextView var29 : CollectionsKt.listOf(new TextView[]{var28, var39})) {
               var29.setTextColor(var23);
               float var20;
               if (var12) {
                  var20 = 2.0F;
               } else {
                  var20 = 0.0F;
               }

               var29.setShadowLayer(var20, 2.0F, 2.0F, Color.argb(170, 0, 0, 0));
            }
         }

         TextView var41 = this.speedUnit;
         TextView var30 = var41;
         if (var41 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("speedUnit");
            var30 = null;
         }

         var30.setTextColor(var11);
         TextView var42 = this.speedUnit;
         TextView var31 = var42;
         if (var42 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("speedUnit");
            var31 = null;
         }

         float var21;
         if (var12) {
            var21 = 8.0F;
         } else {
            var21 = 0.0F;
         }

         var31.setShadowLayer(var21, 0.0F, 0.0F, -16777216);
         TextView var43 = this.devMetrics;
         TextView var32 = var43;
         if (var43 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("devMetrics");
            var32 = null;
         }

         var32.setTextColor(var11);
         TextView var33 = this.devMetrics;
         if (var33 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("devMetrics");
            var33 = null;
         }

         if (var12) {
            var21 = 6.0F;
         } else {
            var21 = 0.0F;
         }

         var33.setShadowLayer(var21, 0.0F, 0.0F, -16777216);
         this.applyRootBg();
      }
   }

   private final void useShared() {
      SharedPlace.Place var8 = this.sharedPending;
      if (var8 != null) {
         DataClient var7 = this.data;
         DataClient var6 = var7;
         if (var7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("data");
            var6 = null;
         }

         boolean var5 = var6.getConnected();
         boolean var4 = true;
         if (!var5) {
            Context var11 = (Context)this;
            String var13 = var8.getName();
            Toast.makeText(var11, var13 + ": the route shows once the comma is connected", 1).show();
         } else {
            this.sharedPending = null;
            if (var8.getHasCoords()) {
               this.driveTo(var8.getName(), var8.getAddress(), var8.getLat(), var8.getLon());
            } else {
               boolean var3;
               if (var8.getQuery().length() > 0) {
                  var3 = true;
               } else {
                  var3 = false;
               }

               if (var3) {
                  ActivityResultLauncher var10 = this.destLauncher;
                  Intent var12 = new Intent((Context)this, DestinationActivity.class).putExtra("query", var8.getQuery());
                  double var1 = this.telemetry.getGpsLat();
                  if (!Double.isInfinite(var1) && !Double.isNaN(var1)) {
                     var3 = var4;
                  } else {
                     var3 = false;
                  }

                  if (var3) {
                     var12.putExtra("lat", this.telemetry.getGpsLat()).putExtra("lon", this.telemetry.getGpsLon());
                  }

                  var10.launch(var12);
               } else {
                  Toast.makeText((Context)this, "Couldn't find a place in what was shared", 1).show();
               }
            }
         }
      }
   }

   private static final void voiceLauncher$lambda$192(MainActivity var0, ActivityResult var1) {
      Intent var2 = var1.getData();
      if (var2 != null) {
         ArrayList var3 = var2.getStringArrayListExtra("android.speech.extra.RESULTS");
         if (var3 != null) {
            String var4 = CollectionsKt.firstOrNull(var3);
            if (var4 != null) {
               var0.runVoice(var4);
               return;
            }
         }
      }
   }

   private final String voiceNavigate(String var1, boolean var2) {
      String var9 = StringsKt.trim(var1).toString().toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var9, "toLowerCase(...)");
      Iterator var11 = Favorites.INSTANCE.getList().iterator();

      Object var8;
      Object var10;
      do {
         boolean var7 = var11.hasNext();
         var10 = null;
         if (!var7) {
            var8 = null;
            break;
         }

         var8 = (String)var11.next();
      } while (!Intrinsics.areEqual(((JSONObject)var8).optString("label"), var9));

      JSONObject var39 = (JSONObject)var8;
      var8 = var39;
      if (var39 == null) {
         Iterator var12 = Favorites.INSTANCE.getList().iterator();

         do {
            if (!var12.hasNext()) {
               var8 = null;
               break;
            }

            var8 = var12.next();
            var40 = (JSONObject)var8;
         } while (!StringsKt.equals(Favorites.INSTANCE.title(var40), var9, true));

         JSONObject var41 = (JSONObject)var8;
         var8 = var41;
         if (var41 == null) {
            if (var9.length() >= 4) {
               var11 = Favorites.INSTANCE.getList().iterator();

               do {
                  if (!var11.hasNext()) {
                     var8 = null;
                     break;
                  }

                  var8 = var11.next();
                  JSONObject var44 = (JSONObject)var8;
                  String var45 = Favorites.INSTANCE.title(var44);
                  Intrinsics.checkNotNullExpressionValue(var45, "title(...)");
                  var46 = var45.toLowerCase(Locale.ROOT);
                  Intrinsics.checkNotNullExpressionValue(var46, "toLowerCase(...)");
               } while (!StringsKt.contains$default(var46, var9, false, 2, null));

               var8 = (JSONObject)var8;
            } else {
               var8 = null;
            }
         }
      }

      if (var8 != null) {
         Intent var17 = new Intent()
            .putExtra("name", Favorites.INSTANCE.title((JSONObject)var8))
            .putExtra("address", var8.optString("address"))
            .putExtra("latitude", var8.optDouble("latitude"))
            .putExtra("longitude", var8.optDouble("longitude"));
         Intrinsics.checkNotNullExpressionValue(var17, "putExtra(...)");
         this.navigateTo(var17);
         var1 = Favorites.INSTANCE.title((JSONObject)var8);
         return "Taking you to " + var1;
      } else {
         if (var2 && Favorites.INSTANCE.getList().isEmpty()) {
            DataClient var43 = this.data;
            var8 = var43;
            if (var43 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("data");
               var8 = null;
            }

            if (((DataClient)var8).getConnected()) {
               Favorites var35 = Favorites.INSTANCE;
               var8 = this.data;
               if (var8 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("data");
                  var8 = var10;
               }

               var35.load((DataClient)var8, new MainActivity$$ExternalSyntheticLambda0(this, var1));
               return "Looking up your saved places…";
            }
         }

         var10 = this.destLauncher;
         var8 = new Intent((Context)this, DestinationActivity.class).putExtra("query", var1);
         double var4 = this.telemetry.getGpsLat();
         boolean var6;
         if (!Double.isInfinite(var4) && !Double.isNaN(var4)) {
            var6 = true;
         } else {
            var6 = false;
         }

         if (var6) {
            var8.putExtra("lat", this.telemetry.getGpsLat()).putExtra("lon", this.telemetry.getGpsLon());
         }

         var10.launch(var8);
         StringBuilder var16;
         if (!Intrinsics.areEqual(var9, "home") && !Intrinsics.areEqual(var9, "work")) {
            var16 = new StringBuilder().append("Searching ").append(var1);
         } else {
            if (var9.length() > 0) {
               var6 = true;
            } else {
               var6 = false;
            }

            if (var6) {
               StringBuilder var13 = new StringBuilder();
               char var3 = var9.charAt(0);
               var8 = String.valueOf(var3);
               Intrinsics.checkNotNull(var8, "null cannot be cast to non-null type java.lang.String");
               var8 = var8.toUpperCase(Locale.ROOT);
               Intrinsics.checkNotNullExpressionValue(var8, "toUpperCase(...)");
               StringBuilder var14 = var13.append(var8);
               var8 = var9.substring(1);
               Intrinsics.checkNotNullExpressionValue(var8, "substring(...)");
               var1 = var14.append(var8).toString();
            } else {
               var1 = var9;
            }

            if (var9.length() > 0) {
               var6 = true;
            } else {
               var6 = false;
            }

            if (var6) {
               StringBuilder var30 = new StringBuilder();
               char var19 = var9.charAt(0);
               String var37 = String.valueOf(var19);
               Intrinsics.checkNotNull(var37, "null cannot be cast to non-null type java.lang.String");
               String var38 = var37.toUpperCase(Locale.ROOT);
               Intrinsics.checkNotNullExpressionValue(var38, "toUpperCase(...)");
               StringBuilder var31 = var30.append(var38);
               var9 = var9.substring(1);
               Intrinsics.checkNotNullExpressionValue(var9, "substring(...)");
               var8 = var31.append(var9).toString();
            } else {
               var8 = var9;
            }

            var16 = new StringBuilder().append("No ").append(var1).append(" saved yet: pick it, then ★ › Make it ").append(var8);
         }

         return var16.toString();
      }
   }

   private static final Unit voiceNavigate$lambda$208(MainActivity var0, String var1) {
      var1 = var0.voiceNavigate(var1, false);
      Toast.makeText((Context)var0, var1, 0).show();
      return Unit.INSTANCE;
   }

   private final String voiceNudge(String var1) {
      NewIdeas var7 = NewIdeas.INSTANCE;
      UiSettings var6 = this.settings;
      UiSettings var5 = var6;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("settings");
         var5 = null;
      }

      if (!var7.on(var5, "voice_nudges")) {
         return "Voice nudges are off (Settings > New ideas)";
      } else {
         IdeasMemory var11 = Ideas.INSTANCE.getMemory();
         long var3 = System.currentTimeMillis();
         int var2 = var1.hashCode();
         String var10 = "right";
         switch (var2) {
            case -1364013995:
               if (var1.equals("center")) {
                  var11.setNudgeOffset(0.0);
                  var11.setNudgeUntil(0L);
                  return "Back to the middle";
               }

               return "Didn't catch that";
            case -124477815:
               if (!var1.equals("turn_left")) {
                  return "Didn't catch that";
               }
               break;
            case 3317767:
               if (var1.equals("left")) {
                  var11.setNudgeOffset(-0.3);
                  var11.setNudgeUntil(90000L + var3);
                  return "Moving over left a bit";
               }

               return "Didn't catch that";
            case 3506395:
               if (var1.equals("room")) {
                  var11.setRoomUntil(180000L + var3);
                  return "More room for 3 minutes";
               }

               return "Didn't catch that";
            case 108511772:
               if (var1.equals("right")) {
                  var11.setNudgeOffset(0.3);
                  var11.setNudgeUntil(90000L + var3);
                  return "Moving over right a bit";
               }

               return "Didn't catch that";
            case 441816026:
               if (!var1.equals("turn_right")) {
                  return "Didn't catch that";
               }
               break;
            default:
               return "Didn't catch that";
         }

         IdeasMemory var12 = Ideas.INSTANCE.getMemory();
         byte var9;
         if (Intrinsics.areEqual(var1, "turn_left")) {
            var9 = -1;
         } else {
            var9 = 1;
         }

         var12.voiceTurn(var9);
         if (Intrinsics.areEqual(var1, "turn_left")) {
            var10 = "left";
         }

         return "Signaling for the next " + var10;
      }
   }

   private final float windowScale() {
      FrameLayout var8 = this.root;
      Object var7 = null;
      FrameLayout var6 = var8;
      if (var8 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("root");
         var6 = null;
      }

      int var5 = var6.getWidth();
      var8 = this.root;
      var6 = var8;
      if (var8 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("root");
         var6 = null;
      }

      int var4 = var6.getHeight();
      if (var5 != 0 && var4 != 0) {
         Rect var12;
         if (VERSION.SDK_INT >= 30) {
            var12 = this.getWindowManager().getMaximumWindowMetrics().getBounds();
         } else {
            var12 = new Rect(0, 0, this.getResources().getDisplayMetrics().widthPixels, this.getResources().getDisplayMetrics().heightPixels);
         }

         Intrinsics.checkNotNull(var12);
         float var3 = Math.max(var12.width(), var12.height());
         float var2 = Math.min(var12.width(), var12.height());
         if (!(var3 <= 0.0F) && !(var2 <= 0.0F)) {
            HudLayout var16 = this.hud;
            HudLayout var13 = var16;
            if (var16 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("hud");
               var13 = null;
            }

            var13.setDisplayLong(var3);
            HudLayout var14 = this.hud;
            if (var14 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("hud");
               var14 = (HudLayout)var7;
            }

            var14.setDisplayShort(var2);
            float var1 = var5;
            if (var5 >= var4) {
               var1 /= var3;
               var2 = var4 / var2;
            } else {
               var1 /= var2;
               var2 = var4 / var3;
            }

            return RangesKt.coerceIn((float)Math.pow(RangesKt.coerceIn(Math.min(var1, var2), 0.2F, 1.0F), 0.8), 0.5F, 1.0F);
         } else {
            return 1.0F;
         }
      } else {
         return 1.0F;
      }
   }

   private final void wireControls() {
      MiciDistanceView var4 = this.personality;
      Object var5 = null;
      MiciDistanceView var3 = var4;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("personality");
         var3 = null;
      }

      var3.setOnTouchListener(new MainActivity$$ExternalSyntheticLambda81(this));
      WheelButton var19 = this.wheel;
      WheelButton var10 = var19;
      if (var19 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("wheel");
         var10 = null;
      }

      var10.setOnTouchListener(new MainActivity$$ExternalSyntheticLambda86(this));
      SlcView var20 = this.slc;
      SlcView var11 = var20;
      if (var20 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("slc");
         var11 = null;
      }

      var11.setOnClickListener(new MainActivity$$ExternalSyntheticLambda87(this));
      LongRef var6 = new LongRef();
      NavCardView var21 = this.navCard;
      NavCardView var12 = var21;
      if (var21 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("navCard");
         var12 = null;
      }

      var12.setOnTouchListener(new MainActivity$$ExternalSyntheticLambda88(var6, this));
      MainActivity var9 = this;
      FloatRef var30 = new FloatRef();
      FloatRef var22 = new FloatRef();
      BooleanRef var8 = new BooleanRef();
      float var1 = ViewConfiguration.get((Context)var9).getScaledTouchSlop();
      MainActivity$$ExternalSyntheticLambda89 var7 = new MainActivity$$ExternalSyntheticLambda89(var8, var9);
      OverlayView var13 = var9.overlay;
      if (var13 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("overlay");
         var13 = null;
      }

      var13.setOnTouchListener(new MainActivity$$ExternalSyntheticLambda90(var9, var30, var22, var8, var7, var1 * 2.0F));
      this.hud = new HudLayout((Context)this);
      HudLayout var23 = this.hud;
      HudLayout var14 = var23;
      if (var23 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("hud");
         var14 = null;
      }

      FrameLayout var31 = this.root;
      FrameLayout var24 = var31;
      if (var31 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("root");
         var24 = null;
      }

      var14.adopt(var24);
      HudLayout var25 = this.hud;
      HudLayout var15 = var25;
      if (var25 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("hud");
         var15 = null;
      }

      String var26;
      if (this.night) {
         var26 = "night";
      } else {
         var26 = "day";
      }

      var15.load(var26);
      this.maskIntent(this.getIntent());
      this.shareIntent(this.getIntent());
      FrameLayout var27 = this.root;
      FrameLayout var16 = var27;
      if (var27 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("root");
         var16 = null;
      }

      var16.addOnLayoutChangeListener(new MainActivity$$ExternalSyntheticLambda91(this));
      ((Button)this.findViewById(R.id.sbSettings)).setOnClickListener(new MainActivity$$ExternalSyntheticLambda92(this));
      ((Button)this.findViewById(R.id.sbTerminal)).setOnClickListener(new MainActivity$$ExternalSyntheticLambda93(this));
      ((Button)this.findViewById(R.id.sbFiles)).setOnClickListener(new MainActivity$$ExternalSyntheticLambda94(this));
      ((Button)this.findViewById(R.id.sbDisplay)).setOnClickListener(new MainActivity$$ExternalSyntheticLambda82(this));

      for (Pair var28 : CollectionsKt.listOf(
         new Pair[]{TuplesKt.to(R.id.dsDefault, "default"), TuplesKt.to(R.id.dsOnroad, "onroad"), TuplesKt.to(R.id.dsOffroad, "offroad")}
      )) {
         int var2 = ((Number)var28.component1()).intValue();
         String var29 = (String)var28.component2();
         ((Button)this.findViewById(var2)).setOnClickListener(new MainActivity$$ExternalSyntheticLambda83(this, var29));
      }

      TextView var18 = this.speedTv;
      if (var18 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("speedTv");
         var18 = (TextView)var5;
      }

      var18.setOnLongClickListener(new MainActivity$$ExternalSyntheticLambda85(this));
   }

   private static final boolean wireControls$lambda$133(MainActivity var0, View var1, MotionEvent var2) {
      int var3 = var2.getActionMasked();
      Object var4 = null;
      Object var5 = null;
      MiciDistanceView var10 = null;
      switch (var3) {
         case 0:
            MiciDistanceView var7 = var0.personality;
            if (var7 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("personality");
               var7 = (MiciDistanceView)var5;
            }

            var7.setDown(true);
            var1.invalidate();
            break;
         case 1:
            var5 = var0.personality;
            var10 = (MiciDistanceView)var5;
            if (var5 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("personality");
               var10 = null;
            }

            var10.setDown(false);
            var1.invalidate();
            var3 = Personality.INSTANCE.next(var0.telemetry.getPersonality());
            DataClient var12 = var0.data;
            DataClient var8 = var12;
            if (var12 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("data");
               var8 = null;
            }

            if (!var8.getConnected()) {
               Toast.makeText((Context)var0, "Not connected to the comma", 0).show();
            } else {
               DataClient var9 = var0.data;
               if (var9 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("data");
                  var9 = (DataClient)var4;
               }

               var9.request(
                  "param",
                  MapsKt.mapOf(new Pair[]{TuplesKt.to("key", "LongitudinalPersonality"), TuplesKt.to("value", var3)}),
                  new MainActivity$$ExternalSyntheticLambda84(var0, var3)
               );
            }
         case 2:
         default:
            break;
         case 3:
            MiciDistanceView var6 = var0.personality;
            if (var6 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("personality");
               var6 = var10;
            }

            var6.setDown(false);
            var1.invalidate();
      }

      return true;
   }

   private static final Unit wireControls$lambda$133$lambda$132(MainActivity var0, int var1, JSONObject var2) {
      Intrinsics.checkNotNullParameter(var2, "ack");
      if (var2.optBoolean("ok")) {
         var0.telemetry.setPersonality(Personality.INSTANCE.getNAMES()[var1]);
         MiciDistanceView var3 = var0.personality;
         MiciDistanceView var5 = var3;
         if (var3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("personality");
            var5 = null;
         }

         var5.invalidate();
         Context var6 = (Context)var0;
         String var4 = Personality.INSTANCE.getLABELS()[var1];
         Toast.makeText(var6, "Following distance: " + var4, 0).show();
      } else {
         Toast.makeText((Context)var0, var2.optString("error", "The comma refused"), 0).show();
      }

      return Unit.INSTANCE;
   }

   private static final boolean wireControls$lambda$134(MainActivity var0, View var1, MotionEvent var2) {
      int var3 = var2.getActionMasked();
      Object var4 = null;
      WheelButton var9 = null;
      Object var5 = null;
      switch (var3) {
         case 0:
            WheelButton var8 = var0.wheel;
            if (var8 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("wheel");
               var8 = var9;
            }

            var8.setDown(true);
            var1.invalidate();
            break;
         case 1:
            var5 = var0.wheel;
            var9 = (WheelButton)var5;
            if (var5 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("wheel");
               var9 = null;
            }

            var9.setDown(false);
            var1.invalidate();
            DataClient var7 = var0.data;
            if (var7 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("data");
               var7 = (DataClient)var4;
            }

            var7.control(new JSONObject(MapsKt.mapOf(TuplesKt.to("action", "exp_toggle"))));
         case 2:
         default:
            break;
         case 3:
            WheelButton var6 = var0.wheel;
            if (var6 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("wheel");
               var6 = (WheelButton)var5;
            }

            var6.setDown(false);
            var1.invalidate();
      }

      return true;
   }

   private static final void wireControls$lambda$135(MainActivity var0, View var1) {
      Telemetry var6 = var0.telemetry;
      SlcActionsView var5 = var0.slcActions;
      Object var2 = null;
      Object var3 = null;
      Object var4 = null;
      SlcActionsView var10 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("slcActions");
         var10 = null;
      }

      if (!var10.openPrompt()) {
         if (Telemetry.slcLive$default(var6, 0L, 1, null) != null) {
            if (var6.getSp7()) {
               var6.getUi().set("slc_sources_sp7", true ^ var6.s("slc_sources_sp7"));
            } else {
               DataClient var9 = var0.data;
               if (var9 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("data");
                  var9 = (DataClient)var4;
               }

               var9.control(
                  new JSONObject(
                     MapsKt.mapOf(
                        new Pair[]{
                           TuplesKt.to("action", "param"),
                           TuplesKt.to("key", "SpeedLimitSources"),
                           TuplesKt.to("value", true ^ var6.pBool("SpeedLimitSources", false))
                        }
                     )
                  )
               );
            }
         } else {
            if (var6.getSlcChanged() && var6.getSlcUnconfirmed() > 0.0F) {
               DataClient var8 = var0.data;
               if (var8 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("data");
                  var8 = (DataClient)var2;
               }

               var8.control(
                  new JSONObject(
                     MapsKt.mapOf(
                        new Pair[]{
                           TuplesKt.to("action", "param"), TuplesKt.to("key", "SpeedLimitAccepted"), TuplesKt.to("value", true), TuplesKt.to("memory", true)
                        }
                     )
                  )
               );
            } else if (var6.getSp7()) {
               var6.getUi().set("slc_sources_sp7", true ^ var6.s("slc_sources_sp7"));
            } else {
               DataClient var7 = var0.data;
               if (var7 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("data");
                  var7 = (DataClient)var3;
               }

               var7.control(
                  new JSONObject(
                     MapsKt.mapOf(
                        new Pair[]{
                           TuplesKt.to("action", "param"),
                           TuplesKt.to("key", "SpeedLimitSources"),
                           TuplesKt.to("value", true ^ var6.pBool("SpeedLimitSources", false))
                        }
                     )
                  )
               );
            }
         }
      }
   }

   private static final boolean wireControls$lambda$136(LongRef var0, MainActivity var1, View var2, MotionEvent var3) {
      switch (var3.getActionMasked()) {
         case 0:
            var0.element = System.nanoTime();
            break;
         case 1:
            Telemetry var4 = var1.telemetry;
            Object var7 = null;
            Object var8 = null;
            if (Telemetry.navCard$default(var4, 0L, 1, null) != null) {
               if (System.nanoTime() - var0.element >= 650000000L) {
                  DataClient var5 = var1.data;
                  if (var5 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("data");
                     var5 = (DataClient)var8;
                  }

                  var5.control(new JSONObject(MapsKt.mapOf(TuplesKt.to("action", "nav_cancel"))));
               } else {
                  DataClient var6 = var1.data;
                  if (var6 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("data");
                     var6 = (DataClient)var7;
                  }

                  var6.control(
                     new JSONObject(
                        MapsKt.mapOf(
                           new Pair[]{
                              TuplesKt.to("action", "param"),
                              TuplesKt.to("key", "NavInstructionCollapsed"),
                              TuplesKt.to("value", var1.telemetry.mBool("NavInstructionCollapsed", false) ^ true),
                              TuplesKt.to("memory", true)
                           }
                        )
                     )
                  );
               }
            }
      }

      return true;
   }

   private static final void wireControls$lambda$139$lambda$137(BooleanRef var0, MainActivity var1) {
      if (var0.element) {
         var0.element = false;
         var1.startHudEdit();
      }
   }

   private static final boolean wireControls$lambda$139$lambda$138(
      MainActivity var0, FloatRef var1, FloatRef var2, BooleanRef var3, Runnable var4, float var5, View var6, MotionEvent var7
   ) {
      if (var0.sceneOn) {
         return false;
      } else {
         switch (var7.getActionMasked()) {
            case 0:
               var1.element = var7.getX();
               var2.element = var7.getY();
               EdgeGuard var8 = EdgeGuard.INSTANCE;
               Intrinsics.checkNotNull(var6);
               var3.element = var8.inSystemGestureZone(var6, var7.getX(), var7.getY()) ^ true;
               var0.ui.removeCallbacks(var4);
               if (var3.element) {
                  var0.ui.postDelayed(var4, 800L);
               }
               break;
            case 1:
            default:
               var3.element = false;
               var0.ui.removeCallbacks(var4);
               break;
            case 2:
               if (var3.element && (Math.abs(var7.getX() - var1.element) > var5 || Math.abs(var7.getY() - var2.element) > var5)) {
                  var3.element = false;
                  var0.ui.removeCallbacks(var4);
               }
         }

         return true;
      }
   }

   private static final void wireControls$lambda$140(
      MainActivity var0, View var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9
   ) {
      var0.layoutHud();
   }

   private static final void wireControls$lambda$141(MainActivity var0, View var1) {
      LinearLayout var2 = var0.sidebar;
      LinearLayout var3 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sidebar");
         var3 = null;
      }

      var3.setVisibility(8);
      var0.startActivity(new Intent((Context)var0, SettingsActivity.class).putExtra("host", var0.host).putExtra("tab", "device"));
   }

   private static final void wireControls$lambda$142(MainActivity var0, View var1) {
      LinearLayout var2 = var0.sidebar;
      LinearLayout var3 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sidebar");
         var3 = null;
      }

      var3.setVisibility(8);
      var0.startActivity(new Intent((Context)var0, SettingsActivity.class).putExtra("host", var0.host).putExtra("tab", "terminal"));
   }

   private static final void wireControls$lambda$143(MainActivity var0, View var1) {
      LinearLayout var2 = var0.sidebar;
      LinearLayout var3 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sidebar");
         var3 = null;
      }

      var3.setVisibility(8);
      var0.startActivity(new Intent((Context)var0, FilesActivity.class));
   }

   private static final void wireControls$lambda$144(MainActivity var0, View var1) {
      LinearLayout var2 = var0.sidebar;
      LinearLayout var3 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sidebar");
         var3 = null;
      }

      var3.setVisibility(8);
      var0.startActivity(new Intent((Context)var0, SettingsActivity.class).putExtra("host", var0.host));
   }

   private static final void wireControls$lambda$146(MainActivity var0, String var1, View var2) {
      DataClient var3 = var0.data;
      DataClient var4 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("data");
         var4 = null;
      }

      var4.request("drive_state", MapsKt.mapOf(TuplesKt.to("mode", var1)), new MainActivity$$ExternalSyntheticLambda9(var0));
   }

   private static final Unit wireControls$lambda$146$lambda$145(MainActivity var0, JSONObject var1) {
      Intrinsics.checkNotNullParameter(var1, "ack");
      if (!var1.optBoolean("ok")) {
         Toast.makeText((Context)var0, var1.optString("error", "rejected"), 0).show();
      }

      return Unit.INSTANCE;
   }

   private static final boolean wireControls$lambda$147(MainActivity var0, View var1) {
      var0.statsVisible ^= true;
      UiSettings var2 = var0.settings;
      UiSettings var3 = var2;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("settings");
         var3 = null;
      }

      var3.set("stats", var0.statsVisible);
      return true;
   }

   public final void closeMap() {
      MapPane var4 = this.mapPane;
      if (var4 != null) {
         var4.setVisibility(8);
         View var3 = this.divider;
         Object var2 = null;
         View var1 = var3;
         if (var3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("divider");
            var1 = null;
         }

         var1.setVisibility(8);
         UiSettings var9 = this.settings;
         UiSettings var5 = var9;
         if (var9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("settings");
            var5 = null;
         }

         var5.set("map_open", false);
         var4.getTiles().clearMemory();
         FrameLayout var10 = this.root;
         FrameLayout var6 = var10;
         if (var10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            var6 = null;
         }

         LayoutParams var7 = var6.getLayoutParams();
         Intrinsics.checkNotNull(var7, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
         var var11 = (android.widget.FrameLayout.LayoutParams & LayoutParams)var7;
         var11.setMarginStart(0);
         var11.setMarginEnd(0);
         FrameLayout var8 = this.root;
         if (var8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            var8 = (FrameLayout)var2;
         }

         var8.setLayoutParams((LayoutParams)var11);
      }
   }

   public final void driveTo(String var1, String var2, double var3, double var5) {
      Intrinsics.checkNotNullParameter(var1, "name");
      Intrinsics.checkNotNullParameter(var2, "address");
      Map var7 = MapsKt.mapOf(
         new Pair[]{TuplesKt.to("name", var1), TuplesKt.to("address", var2), TuplesKt.to("latitude", var3), TuplesKt.to("longitude", var5)}
      );
      JSONObject var8 = new JSONObject().put("name", var1).put("address", var2).put("latitude", var3).put("longitude", var5);
      Intrinsics.checkNotNullExpressionValue(var8, "put(...)");
      this.previewPlace(var7, var8, new MainActivity$$ExternalSyntheticLambda108(this, var1, var2, var3, var5));
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   @Override
   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      boolean var2;
      if (var1 == null && SettingsBackup.INSTANCE.looksFresh((Context)this)) {
         var2 = 1;
      } else {
         var2 = 0;
      }

      boolean var4;
      if (!var2) {
         var4 = true;
      } else {
         var4 = false;
      }

      this.backupArmed = var4;
      ModelDownload.INSTANCE.detector((Context)this);
      GpsBridge.INSTANCE.init((Context)this);
      Odometry.INSTANCE.init((Context)this);
      ContextCompat.registerReceiver((Context)this, new BroadcastReceiver() {

         public void onReceive(Context var1, Intent var2x) {
            Intrinsics.checkNotNullParameter(var1, "c");
            Intrinsics.checkNotNullParameter(var2x, "i");
            LaneSuggestView var3 = MainActivity.this.laneSuggest;
            LaneSuggestView var5 = var3;
            if (var3 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("laneSuggest");
               var5 = null;
            }

            String var4x = var2x.getStringExtra("side");
            String var7 = var4x;
            if (var4x == null) {
               var7 = "left";
            }

            var4x = var2x.getStringExtra("why");
            String var6 = var4x;
            if (var4x == null) {
               var6 = "";
            }

            var5.testAsk(var7, var6);
         }
      }, new IntentFilter("com.starpilot.starview.TEST_LANE_SUGGEST"), 2);
      ContextCompat.registerReceiver((Context)this, new BroadcastReceiver() {
         // $VF: Duplicated exception handlers to handle obfuscated exceptions
         public void onReceive(Context var1, Intent var2) {
            Intrinsics.checkNotNullParameter(var1, "c");
            Intrinsics.checkNotNullParameter(var2, "i");
            String var4 = var2.getStringExtra("keys");
            if (var2.getStringExtra("demo") != null) {
               AmyVoice.INSTANCE.demo();
            } else if (var4 != null) {
               AmyVoice var3 = AmyVoice.INSTANCE;

               label44: {
                  try {
                     var11 = var2.getStringExtra("style");
                  } catch (Exception var6) {
                     var7 = AmySynth.Style.SHORT;
                     break label44;
                  }

                  String var8 = var11;
                  if (var11 == null) {
                     var8 = "short";
                  }

                  try {
                     String var9 = var8.toUpperCase(Locale.ROOT);
                     Intrinsics.checkNotNullExpressionValue(var9, "toUpperCase(...)");
                     var7 = AmySynth.Style.valueOf(var9);
                  } catch (Exception var5) {
                     var7 = AmySynth.Style.SHORT;
                  }
               }

               Iterable var14 = StringsKt.split$default(var4, new String[]{","}, false, 0, 6, null);
               var var12 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var14, 10));
               Iterator var15 = var14.iterator();

               while (var15.hasNext()) {
                  var12.add(StringsKt.trim((String & CharSequence)var15.next()).toString());
               }

               List var13 = var12;
               var3.play(var7, var13);
            } else {
               String var10 = var2.getStringExtra("text");
               if (var10 != null) {
                  Speech.INSTANCE.speak(var10, false);
               }
            }
         }
      }, new IntentFilter("com.starpilot.starview.TEST_SPEAK"), 2);
      Speech.init$default(Speech.INSTANCE, (Context)this, null, 2, null);
      OsmRoads.INSTANCE.init((Context)this);
      BlinkLog.INSTANCE.init((Context)this);
      MapdMaps.INSTANCE.init((Context)this);
      this.installCrashLog();
      boolean var3;
      if (this.lastCrash.length() > 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (var3) {
         Toast.makeText((Context)this, "StarView closed unexpectedly last time: the details are on the diagnostics panel", 1).show();
      }

      this.setContentView(R.layout.activity_main);
      this.getWindow().addFlags(128);
      WindowCompat.setDecorFitsSystemWindows(this.getWindow(), false);
      WindowInsetsControllerCompat var13 = new WindowInsetsControllerCompat(this.getWindow(), this.getWindow().getDecorView());
      var13.hide(Type.systemBars());
      var13.setSystemBarsBehavior(2);
      this.settings = new UiSettings((Context)this);
      Telemetry var6 = this.telemetry;
      UiSettings var5 = this.settings;
      UiSettings var14 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("settings");
         var14 = null;
      }

      var6.setUi(var14);
      var5 = this.settings;
      UiSettings var15 = var5;
      if (var5 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("settings");
         var15 = null;
      }

      this.statsVisible = var15.get("stats");
      if (var2) {
         View var16 = this.getWindow().getDecorView();
         MainActivity$$ExternalSyntheticLambda35 var100 = new MainActivity$$ExternalSyntheticLambda35(this);
         var16.postDelayed(var100, 1500L);
      }

      SharedPreferences var212 = this.getSharedPreferences("starview_migr", 0);
      if (!var212.getBoolean("m185", false)) {
         var5 = this.settings;
         UiSettings var17 = var5;
         if (var5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("settings");
            var17 = null;
         }

         var17.set("road_texture", false);
         var212.edit().putBoolean("m185", true).apply();
      }

      Unit var18 = Unit.INSTANCE;
      this.root = this.findViewById(R.id.root);
      this.videoView = this.findViewById(R.id.video);
      this.overlay = this.findViewById(R.id.overlay);
      new Thread(new MainActivity$$ExternalSyntheticLambda47(this)).start();
      this.speedTv = this.findViewById(R.id.speed);
      this.speedUnit = this.findViewById(R.id.speedUnit);
      this.maxCard = this.findViewById(R.id.maxCard);
      this.maxLabel = this.findViewById(R.id.maxLabel);
      this.setSpeedTv = this.findViewById(R.id.setSpeed);
      this.slc = this.findViewById(R.id.slc);
      this.wheel = this.findViewById(R.id.wheel);
      this.dm = this.findViewById(R.id.dm);
      this.personality = this.findViewById(R.id.personality);
      this.slcActions = this.findViewById(R.id.slcActions);
      this.curveLabel = this.findViewById(R.id.curveLabel);
      this.clockTv = this.findViewById(R.id.clockTv);
      this.modeText = this.findViewById(R.id.modeText);
      this.devSidebar = this.findViewById(R.id.devSidebar);
      SideView var19 = this.findViewById(R.id.pipLeft);
      var19.setSide("left");
      Unit var102 = Unit.INSTANCE;
      this.pipLeft = var19;
      SideView var20 = this.findViewById(R.id.pipRight);
      var20.setSide("right");
      Unit var103 = Unit.INSTANCE;
      this.pipRight = var20;
      SideCam.INSTANCE.setOnFrame(new MainActivity$$ExternalSyntheticLambda59(this));
      this.egpu = this.findViewById(R.id.egpu);
      ImageView var104 = this.egpu;
      ImageView var21 = var104;
      if (var104 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("egpu");
         var21 = null;
      }

      var21.setOnClickListener(new MainActivity$$ExternalSyntheticLambda71(this));
      this.roadName = this.findViewById(R.id.roadName);
      this.statsTv = this.findViewById(R.id.stats);
      this.statusTv = this.findViewById(R.id.status);
      this.alertBox = this.findViewById(R.id.alertBox);
      this.alert1 = this.findViewById(R.id.alert1);
      this.alert2 = this.findViewById(R.id.alert2);
      this.alertTint = this.findViewById(R.id.alertTint);
      View var105 = this.alertTint;
      View var22 = var105;
      if (var105 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("alertTint");
         var22 = null;
      }

      var22.setBackground((Drawable)this.tintDrawable);
      this.connectBar = this.findViewById(R.id.connectBar);
      this.hostEdit = this.findViewById(R.id.hostEdit);
      this.gauge = this.findViewById(R.id.gauge);
      this.navCard = this.findViewById(R.id.navCard);
      this.slowCard = this.findViewById(R.id.slowCard);
      this.laneCard = this.findViewById(R.id.laneCard);
      this.blinkCard = this.findViewById(R.id.blinkCard);
      this.gpsCard = this.findViewById(R.id.gpsCard);
      this.laneSuggest = this.findViewById(R.id.laneSuggest);
      LaneSuggestView var106 = this.laneSuggest;
      LaneSuggestView var23 = var106;
      if (var106 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("laneSuggest");
         var23 = null;
      }

      var23.setTranslationZ(this.dp(40));
      this.nudgeHint = this.findViewById(R.id.nudgeHint);
      this.weather = this.findViewById(R.id.weather);
      this.slcSources = this.findViewById(R.id.slcSources);
      this.compass = this.findViewById(R.id.compass);
      this.devMetrics = this.findViewById(R.id.devMetrics);
      this.sidebar = this.findViewById(R.id.sidebar);
      OffroadHome var24 = new OffroadHome((Context)this, new OffroadHome.Actions() {

         @Override
         public void control(JSONObject var1) {
            Intrinsics.checkNotNullParameter(var1, "req");
            DataClient var3x = MainActivity.this.data;
            DataClient var2x = var3x;
            if (var3x == null) {
               Intrinsics.throwUninitializedPropertyAccessException("data");
               var2x = null;
            }

            var2x.control(var1);
         }

         @Override
         public void openFiles() {
            MainActivity.this.startActivity(new Intent((Context)MainActivity.this, FilesActivity.class));
         }

         @Override
         public void openSettings(String var1) {
            MainActivity var2x = MainActivity.this;
            Intent var3x = new Intent((Context)MainActivity.this, SettingsActivity.class).putExtra("host", MainActivity.this.host);
            if (var1 == null) {
               var1 = "device";
            }

            var2x.startActivity(var3x.putExtra("tab", var1));
         }

         @Override
         public void openTerminal() {
            MainActivity.this.startActivity(new Intent((Context)MainActivity.this, SettingsActivity.class).putExtra("host", MainActivity.this.host).putExtra("tab", "terminal"));
         }
      });
      var24.setVisibility(8);
      Unit var107 = Unit.INSTANCE;
      this.offroadHome = var24;
      FrameLayout var108 = this.root;
      FrameLayout var25 = var108;
      if (var108 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("root");
         var25 = null;
      }

      OffroadHome var213 = this.offroadHome;
      OffroadHome var109 = var213;
      if (var213 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("offroadHome");
         var109 = null;
      }

      View var8 = (View)var109;
      FrameLayout var214 = this.root;
      FrameLayout var110 = var214;
      if (var214 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("root");
         var110 = null;
      }

      LinearLayout var7 = this.sidebar;
      LinearLayout var215 = var7;
      if (var7 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sidebar");
         var215 = null;
      }

      var2 = var110.indexOfChild((View)var215);
      android.widget.FrameLayout.LayoutParams var216 = new android.widget.FrameLayout.LayoutParams(-1, -1);
      var216.setMarginStart(this.dp(240));
      Unit var111 = Unit.INSTANCE;
      var25.addView(var8, var2, (LayoutParams)var216);
      OverlayView var112 = this.overlay;
      OverlayView var26 = var112;
      if (var112 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("overlay");
         var26 = null;
      }

      var26.setT(this.telemetry);
      SlcView var113 = this.slc;
      SlcView var27 = var113;
      if (var113 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("slc");
         var27 = null;
      }

      var27.setT(this.telemetry);
      WheelButton var114 = this.wheel;
      WheelButton var28 = var114;
      if (var114 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("wheel");
         var28 = null;
      }

      var28.setT(this.telemetry);
      DmView var115 = this.dm;
      DmView var29 = var115;
      if (var115 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("dm");
         var29 = null;
      }

      var29.setT(this.telemetry);
      MiciDistanceView var116 = this.personality;
      MiciDistanceView var30 = var116;
      if (var116 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("personality");
         var30 = null;
      }

      var30.setT(this.telemetry);
      SlcActionsView var117 = this.slcActions;
      SlcActionsView var31 = var117;
      if (var117 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("slcActions");
         var31 = null;
      }

      var31.setT(this.telemetry);
      SlcActionsView var118 = this.slcActions;
      SlcActionsView var32 = var118;
      if (var118 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("slcActions");
         var32 = null;
      }

      var32.setSend(new MainActivity$$ExternalSyntheticLambda75(this));
      DevSidebarView var119 = this.devSidebar;
      DevSidebarView var33 = var119;
      if (var119 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("devSidebar");
         var33 = null;
      }

      var33.setT(this.telemetry);
      SideView var120 = this.pipLeft;
      SideView var34 = var120;
      if (var120 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("pipLeft");
         var34 = null;
      }

      var34.setT(this.telemetry);
      SideView var121 = this.pipRight;
      SideView var35 = var121;
      if (var121 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("pipRight");
         var35 = null;
      }

      var35.setT(this.telemetry);
      AetherGaugeView var122 = this.gauge;
      AetherGaugeView var36 = var122;
      if (var122 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("gauge");
         var36 = null;
      }

      var36.setT(this.telemetry);
      NavCardView var123 = this.navCard;
      NavCardView var37 = var123;
      if (var123 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("navCard");
         var37 = null;
      }

      var37.setT(this.telemetry);
      SlowReasonView var124 = this.slowCard;
      SlowReasonView var38 = var124;
      if (var124 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("slowCard");
         var38 = null;
      }

      var38.setT(this.telemetry);
      this.telemetry.getSlow().setOnEvent(new MainActivity$$ExternalSyntheticLambda76(this));
      BlinkerCardView var125 = this.blinkCard;
      BlinkerCardView var39 = var125;
      if (var125 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("blinkCard");
         var39 = null;
      }

      var39.setT(this.telemetry);
      GpsCardView var126 = this.gpsCard;
      GpsCardView var40 = var126;
      if (var126 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("gpsCard");
         var40 = null;
      }

      var40.setT(this.telemetry);
      LaneCardView var127 = this.laneCard;
      LaneCardView var41 = var127;
      if (var127 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("laneCard");
         var41 = null;
      }

      var41.setTracker(this.laneTracker);
      LaneCardView var128 = this.laneCard;
      LaneCardView var42 = var128;
      if (var128 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("laneCard");
         var42 = null;
      }

      var42.setWhere(new MainActivity$$ExternalSyntheticLambda77(this));
      this.laneTracker.setOnEvent(new MainActivity$$ExternalSyntheticLambda78(this));
      WeatherView var129 = this.weather;
      WeatherView var43 = var129;
      if (var129 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("weather");
         var43 = null;
      }

      var43.setT(this.telemetry);
      SlcSourcesView var130 = this.slcSources;
      SlcSourcesView var44 = var130;
      if (var130 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("slcSources");
         var44 = null;
      }

      var44.setT(this.telemetry);
      LaneCardView var131 = this.laneCard;
      LaneCardView var45 = var131;
      if (var131 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("laneCard");
         var45 = null;
      }

      var45.setShots(this.getLaneShots());
      LaneCardView var132 = this.laneCard;
      LaneCardView var46 = var132;
      if (var132 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("laneCard");
         var46 = null;
      }

      var46.setRamps(new MainActivity$$ExternalSyntheticLambda79(this));
      this.getLaneShots().setScene(new MainActivity$$ExternalSyntheticLambda80(this));
      this.getLaneShots().setLanes(new MainActivity$$ExternalSyntheticLambda36(this));
      this.getLaneShots().setRoad(new MainActivity$$ExternalSyntheticLambda37(this));
      this.getLaneShots().setRamps(new MainActivity$$ExternalSyntheticLambda38(this));
      this.getLaneShots().setModelSays(new MainActivity$$ExternalSyntheticLambda39(this));
      this.getLaneShots().setAnswer(new MainActivity$$ExternalSyntheticLambda41(this));
      LaneBrain.Live.INSTANCE.setBrain(this.laneTracker);
      Hpms var133 = Hpms.INSTANCE;
      Context var47 = this.getApplicationContext();
      Intrinsics.checkNotNullExpressionValue(var47, "getApplicationContext(...)");
      var133.init(var47);
      Context var48 = this.getApplicationContext();
      Intrinsics.checkNotNullExpressionValue(var48, "getApplicationContext(...)");
      this.laneMapLog = new LaneMapLog(var48);
      LaneCardView var134 = this.laneCard;
      LaneCardView var49 = var134;
      if (var134 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("laneCard");
         var49 = null;
      }

      var49.setAhead(new MainActivity$$ExternalSyntheticLambda42(this));
      this.laneAhead.setOnEvent(new MainActivity$$ExternalSyntheticLambda43(this));
      File var263 = new File(this.getFilesDir(), "lane_counts.json");

      label330: {
         try {
            var217 = new JSONObject(FilesKt.readText$default(var263, null, 1, null));
            var50 = var217.keys();
            Intrinsics.checkNotNullExpressionValue(var50, "keys(...)");
         } catch (Exception var12) {
            break label330;
         }

         while (true) {
            try {
               if (!var50.hasNext()) {
                  break;
               }

               String var135 = (String)var50.next();
               Map var268 = this.laneTracker.getMyCounts();
               Intrinsics.checkNotNull(var135);
               var268.put(Long.parseLong(var135), var217.getInt(var135));
            } catch (Exception var11) {
               break;
            }
         }
      }

      this.laneTracker.setOnCountsChanged(new MainActivity$$ExternalSyntheticLambda44(this, var263));
      Link.INSTANCE.loadKey((Context)this);
      DriveJournal.INSTANCE.init((Context)this);
      Ideas.INSTANCE.init((Context)this);
      MapBumps.INSTANCE.init((Context)this);
      MapCams.INSTANCE.init((Context)this);
      DriveTrack.INSTANCE.init((Context)this);
      SharedPreferences var218 = this.getSharedPreferences("starview", 0);
      EditText var136 = this.hostEdit;
      EditText var51 = var136;
      if (var136 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("hostEdit");
         var51 = null;
      }

      var51.setText(var218.getString("host", ""));
      ((Button)this.findViewById(R.id.connectBtn)).setOnClickListener(new MainActivity$$ExternalSyntheticLambda45(this, var218));
      ((Button)this.findViewById(R.id.findBtn)).setOnClickListener(new MainActivity$$ExternalSyntheticLambda46(this));
      ObjectRef var52 = new ObjectRef();
      this.tabletModePusher = new MainActivity$$ExternalSyntheticLambda48(this, var52);
      this.data = new DataClient(this.telemetry, new MainActivity$$ExternalSyntheticLambda49(this, var52));
      Link.INSTANCE.setTelemetry(this.telemetry);
      Link var219 = Link.INSTANCE;
      DataClient var137 = this.data;
      DataClient var53 = var137;
      if (var137 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("data");
         var53 = null;
      }

      var219.setData(var53);
      this.<View>findViewById(R.id.gear).setOnClickListener(new MainActivity$$ExternalSyntheticLambda50(this));
      this.video = new VideoClient(new MainActivity$$ExternalSyntheticLambda52(this), new MainActivity$$ExternalSyntheticLambda53(this));
      OverlayView var138 = this.overlay;
      OverlayView var54 = var138;
      if (var138 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("overlay");
         var54 = null;
      }

      var54.setOnResized(new MainActivity$$ExternalSyntheticLambda54(this));
      Context var55 = this.getApplicationContext();
      Intrinsics.checkNotNullExpressionValue(var55, "getApplicationContext(...)");
      this.layoutSeg = new LayoutSeg(var55);
      VideoClient var56 = new VideoClient(new MainActivity$$ExternalSyntheticLambda55(), new MainActivity$$ExternalSyntheticLambda56());
      var56.setOnImage(new MainActivity$$ExternalSyntheticLambda57(this));
      Unit var139 = Unit.INSTANCE;
      this.detRoad = var56;
      VideoClient var57 = new VideoClient(new MainActivity$$ExternalSyntheticLambda58(), new MainActivity$$ExternalSyntheticLambda60());
      var57.setOnImage(new MainActivity$$ExternalSyntheticLambda61(this));
      Unit var140 = Unit.INSTANCE;
      this.detWide = var57;
      SurfaceView var141 = this.videoView;
      SurfaceView var58 = var141;
      if (var141 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("videoView");
         var58 = null;
      }

      var58.getHolder().addCallback(new Callback() {

         public void surfaceChanged(SurfaceHolder var1, int var2x, int var3x, int var4x) {
            Intrinsics.checkNotNullParameter(var1, "holder");
         }

         public void surfaceCreated(SurfaceHolder var1) {
            Intrinsics.checkNotNullParameter(var1, "holder");
            MainActivity.this.surfaceReady = true;
         }

         public void surfaceDestroyed(SurfaceHolder var1) {
            Intrinsics.checkNotNullParameter(var1, "holder");
            MainActivity.this.surfaceReady = false;
            MainActivity.this.stopVideo();
         }
      });
      this.wireControls();
      TextView var59 = new TextView((Context)this);
      var59.setTextSize(15.0F);
      var59.setTypeface(null, 1);
      var59.setTextColor(-1);
      var59.setGravity(17);
      var59.setPadding(this.dp(18), this.dp(10), this.dp(18), this.dp(10));
      var59.setVisibility(8);
      GradientDrawable var142 = new GradientDrawable();
      var142.setCornerRadius(this.dp(22));
      var142.setColor(Color.argb(150, 20, 22, 28));
      var142.setStroke(this.dp(1), Color.argb(90, 255, 255, 255));
      var59.setBackground((Drawable)var142);
      var59.setOnClickListener(new MainActivity$$ExternalSyntheticLambda63(this));
      Unit var143 = Unit.INSTANCE;
      this.sceneBtn = var59;
      FrameLayout var144 = this.root;
      FrameLayout var60 = var144;
      if (var144 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("root");
         var60 = null;
      }

      TextView var220 = this.sceneBtn;
      TextView var145 = var220;
      if (var220 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sceneBtn");
         var145 = null;
      }

      View var9 = (View)var145;
      FrameLayout var221 = this.root;
      FrameLayout var146 = var221;
      if (var221 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("root");
         var146 = null;
      }

      LinearLayout var269 = this.sidebar;
      LinearLayout var222 = var269;
      if (var269 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sidebar");
         var222 = null;
      }

      var2 = var146.indexOfChild((View)var222);
      android.widget.FrameLayout.LayoutParams var223 = new android.widget.FrameLayout.LayoutParams(-2, -2, 8388691);
      var223.setMarginStart(this.dp(24));
      var223.bottomMargin = this.dp(24);
      Unit var147 = Unit.INSTANCE;
      var60.addView(var9, var2, (LayoutParams)var223);
      TextView var61 = new TextView((Context)this);
      var61.setText("⌕  Where to?");
      var61.setTextSize(15.0F);
      var61.setTypeface(null, 1);
      var61.setTextColor(-1);
      var61.setGravity(17);
      var61.setPadding(this.dp(18), this.dp(10), this.dp(18), this.dp(10));
      var61.setVisibility(8);
      GradientDrawable var148 = new GradientDrawable();
      var148.setCornerRadius(this.dp(22));
      var148.setColor(Color.argb(150, 20, 22, 28));
      var148.setStroke(this.dp(1), Color.argb(90, 255, 255, 255));
      var61.setBackground((Drawable)var148);
      var61.setOnClickListener(new MainActivity$$ExternalSyntheticLambda64(this));
      Unit var149 = Unit.INSTANCE;
      this.whereBtn = var61;
      FrameLayout var150 = this.root;
      FrameLayout var62 = var150;
      if (var150 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("root");
         var62 = null;
      }

      TextView var224 = this.whereBtn;
      TextView var151 = var224;
      if (var224 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("whereBtn");
         var151 = null;
      }

      var9 = (View)var151;
      FrameLayout var225 = this.root;
      FrameLayout var152 = var225;
      if (var225 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("root");
         var152 = null;
      }

      LinearLayout var270 = this.sidebar;
      LinearLayout var226 = var270;
      if (var270 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sidebar");
         var226 = null;
      }

      var2 = var152.indexOfChild((View)var226);
      android.widget.FrameLayout.LayoutParams var227 = new android.widget.FrameLayout.LayoutParams(-2, -2, 8388691);
      var227.setMarginStart(this.dp(160));
      var227.bottomMargin = this.dp(24);
      Unit var153 = Unit.INSTANCE;
      var62.addView(var9, var2, (LayoutParams)var227);
      TextView var63 = new TextView((Context)this);
      var63.setText("MAP");
      var63.setTextSize(15.0F);
      var63.setTypeface(null, 1);
      var63.setTextColor(-1);
      var63.setGravity(17);
      var63.setPadding(this.dp(18), this.dp(10), this.dp(18), this.dp(10));
      var63.setVisibility(8);
      GradientDrawable var154 = new GradientDrawable();
      var154.setCornerRadius(this.dp(22));
      var154.setColor(Color.argb(150, 20, 22, 28));
      var154.setStroke(this.dp(1), Color.argb(90, 255, 255, 255));
      var63.setBackground((Drawable)var154);
      var63.setOnClickListener(new MainActivity$$ExternalSyntheticLambda65(this));
      Unit var155 = Unit.INSTANCE;
      this.mapBtn = var63;
      FrameLayout var156 = this.root;
      FrameLayout var64 = var156;
      if (var156 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("root");
         var64 = null;
      }

      TextView var228 = this.mapBtn;
      TextView var157 = var228;
      if (var228 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("mapBtn");
         var157 = null;
      }

      var9 = (View)var157;
      FrameLayout var229 = this.root;
      FrameLayout var158 = var229;
      if (var229 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("root");
         var158 = null;
      }

      LinearLayout var271 = this.sidebar;
      LinearLayout var230 = var271;
      if (var271 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sidebar");
         var230 = null;
      }

      var2 = var158.indexOfChild((View)var230);
      android.widget.FrameLayout.LayoutParams var231 = new android.widget.FrameLayout.LayoutParams(-2, -2, 8388691);
      var231.setMarginStart(this.dp(330));
      var231.bottomMargin = this.dp(24);
      Unit var159 = Unit.INSTANCE;
      var64.addView(var9, var2, (LayoutParams)var231);
      TextView var65 = new TextView((Context)this);
      var65.setText("☰  QUICK");
      var65.setTextSize(15.0F);
      var65.setTypeface(null, 1);
      var65.setTextColor(-1);
      var65.setGravity(17);
      var65.setPadding(this.dp(18), this.dp(10), this.dp(18), this.dp(10));
      var65.setVisibility(8);
      GradientDrawable var160 = new GradientDrawable();
      var160.setCornerRadius(this.dp(22));
      var160.setColor(Color.argb(150, 20, 22, 28));
      var160.setStroke(this.dp(1), Color.argb(90, 255, 255, 255));
      var65.setBackground((Drawable)var160);
      var65.setOnClickListener(new MainActivity$$ExternalSyntheticLambda66(this));
      Unit var161 = Unit.INSTANCE;
      this.quickBtn = var65;
      FrameLayout var162 = this.root;
      FrameLayout var66 = var162;
      if (var162 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("root");
         var66 = null;
      }

      TextView var232 = this.quickBtn;
      TextView var163 = var232;
      if (var232 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("quickBtn");
         var163 = null;
      }

      var9 = (View)var163;
      FrameLayout var233 = this.root;
      FrameLayout var164 = var233;
      if (var233 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("root");
         var164 = null;
      }

      LinearLayout var272 = this.sidebar;
      LinearLayout var234 = var272;
      if (var272 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sidebar");
         var234 = null;
      }

      var2 = var164.indexOfChild((View)var234);
      android.widget.FrameLayout.LayoutParams var235 = new android.widget.FrameLayout.LayoutParams(-2, -2, 8388691);
      var235.setMarginStart(this.dp(440));
      var235.bottomMargin = this.dp(24);
      Unit var165 = Unit.INSTANCE;
      var66.addView(var9, var2, (LayoutParams)var235);
      ImageView var67 = new ImageView((Context)this);
      var67.setImageDrawable(SvIcon.INSTANCE.drawable("mic", -1, this.dp(26)));
      var67.setScaleType(ScaleType.CENTER);
      var67.setContentDescription("Say it");
      var67.setVisibility(8);
      GradientDrawable var166 = new GradientDrawable();
      var166.setShape(1);
      var166.setColor(Color.argb(150, 20, 22, 28));
      var166.setStroke(this.dp(1), Color.argb(90, 255, 255, 255));
      var67.setBackground((Drawable)var166);
      var67.setOnClickListener(new MainActivity$$ExternalSyntheticLambda67(this));
      Unit var167 = Unit.INSTANCE;
      this.micBtn = var67;
      FrameLayout var168 = this.root;
      FrameLayout var68 = var168;
      if (var168 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("root");
         var68 = null;
      }

      ImageView var236 = this.micBtn;
      ImageView var169 = var236;
      if (var236 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("micBtn");
         var169 = null;
      }

      var9 = (View)var169;
      FrameLayout var237 = this.root;
      FrameLayout var170 = var237;
      if (var237 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("root");
         var170 = null;
      }

      LinearLayout var273 = this.sidebar;
      LinearLayout var238 = var273;
      if (var273 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sidebar");
         var238 = null;
      }

      var2 = var170.indexOfChild((View)var238);
      android.widget.FrameLayout.LayoutParams var171 = new android.widget.FrameLayout.LayoutParams(this.dp(48), this.dp(48), 8388691);
      var171.setMarginStart(this.dp(380));
      var171.bottomMargin = this.dp(24);
      Unit var239 = Unit.INSTANCE;
      var68.addView(var9, var2, (LayoutParams)var171);
      ImageView var69 = new ImageView((Context)this);
      var69.setImageDrawable(SvIcon.INSTANCE.drawable("bookmark", Color.rgb(255, 196, 0), this.dp(26)));
      var69.setScaleType(ScaleType.CENTER);
      var69.setContentDescription("Bookmark this moment");
      var69.setVisibility(8);
      GradientDrawable var172 = new GradientDrawable();
      var172.setShape(1);
      var172.setColor(Color.argb(150, 20, 22, 28));
      var172.setStroke(this.dp(1), Color.argb(90, 255, 255, 255));
      var69.setBackground((Drawable)var172);
      var69.setOnClickListener(new MainActivity$$ExternalSyntheticLambda68(this));
      Unit var173 = Unit.INSTANCE;
      this.bookmarkBtn = var69;
      FrameLayout var174 = this.root;
      FrameLayout var70 = var174;
      if (var174 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("root");
         var70 = null;
      }

      ImageView var240 = this.bookmarkBtn;
      ImageView var175 = var240;
      if (var240 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("bookmarkBtn");
         var175 = null;
      }

      var9 = (View)var175;
      FrameLayout var241 = this.root;
      FrameLayout var176 = var241;
      if (var241 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("root");
         var176 = null;
      }

      LinearLayout var274 = this.sidebar;
      LinearLayout var242 = var274;
      if (var274 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sidebar");
         var242 = null;
      }

      var2 = var176.indexOfChild((View)var242);
      android.widget.FrameLayout.LayoutParams var243 = new android.widget.FrameLayout.LayoutParams(this.dp(48), this.dp(48), 8388691);
      var243.setMarginStart(this.dp(320));
      var243.bottomMargin = this.dp(24);
      Unit var177 = Unit.INSTANCE;
      var70.addView(var9, var2, (LayoutParams)var243);
      this.bookmarkCard = new BookmarkCard((Context)this, new MainActivity$$ExternalSyntheticLambda69(this), new MainActivity$$ExternalSyntheticLambda70(this));
      BookmarkCard var178 = this.bookmarkCard;
      BookmarkCard var71 = var178;
      if (var178 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("bookmarkCard");
         var71 = null;
      }

      var71.setTranslationZ(this.dp(42));
      FrameLayout var179 = this.root;
      FrameLayout var72 = var179;
      if (var179 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("root");
         var72 = null;
      }

      BookmarkCard var244 = this.bookmarkCard;
      BookmarkCard var180 = var244;
      if (var244 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("bookmarkCard");
         var180 = null;
      }

      View var181 = (View)var180;
      android.widget.FrameLayout.LayoutParams var275 = new android.widget.FrameLayout.LayoutParams(-2, -2, 8388691);
      var275.setMarginStart(this.dp(24));
      var275.bottomMargin = this.dp(86);
      Unit var245 = Unit.INSTANCE;
      var72.addView(var181, (LayoutParams)var275);
      TextView var73 = new TextView((Context)this);
      var73.setText("SAVER ON");
      var73.setTextSize(15.0F);
      var73.setTypeface(null, 1);
      var73.setTextColor(Color.rgb(111, 227, 161));
      var73.setGravity(17);
      var73.setPadding(this.dp(18), this.dp(10), this.dp(18), this.dp(10));
      var73.setVisibility(8);
      GradientDrawable var182 = new GradientDrawable();
      var182.setCornerRadius(this.dp(22));
      var182.setColor(Color.argb(170, 12, 34, 22));
      var182.setStroke(this.dp(1), Color.rgb(43, 107, 75));
      var73.setBackground((Drawable)var182);
      var73.setOnClickListener(new MainActivity$$ExternalSyntheticLambda72(this));
      Unit var183 = Unit.INSTANCE;
      this.saverPill = var73;
      FrameLayout var184 = this.root;
      FrameLayout var74 = var184;
      if (var184 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("root");
         var74 = null;
      }

      TextView var246 = this.saverPill;
      TextView var185 = var246;
      if (var246 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("saverPill");
         var185 = null;
      }

      var9 = (View)var185;
      FrameLayout var247 = this.root;
      FrameLayout var186 = var247;
      if (var247 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("root");
         var186 = null;
      }

      LinearLayout var276 = this.sidebar;
      LinearLayout var248 = var276;
      if (var276 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sidebar");
         var248 = null;
      }

      var2 = var186.indexOfChild((View)var248);
      android.widget.FrameLayout.LayoutParams var249 = new android.widget.FrameLayout.LayoutParams(-2, -2, 8388691);
      var249.setMarginStart(this.dp(570));
      var249.bottomMargin = this.dp(24);
      Unit var187 = Unit.INSTANCE;
      var74.addView(var9, var2, (LayoutParams)var249);
      this.applySaver();
      LinearLayout var75 = new LinearLayout((Context)this);
      var75.setOrientation(0);
      var75.setVisibility(8);
      Unit var188 = Unit.INSTANCE;
      this.favBar = var75;
      FrameLayout var189 = this.root;
      FrameLayout var76 = var189;
      if (var189 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("root");
         var76 = null;
      }

      LinearLayout var250 = this.favBar;
      LinearLayout var190 = var250;
      if (var250 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("favBar");
         var190 = null;
      }

      var9 = (View)var190;
      FrameLayout var251 = this.root;
      FrameLayout var191 = var251;
      if (var251 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("root");
         var191 = null;
      }

      LinearLayout var277 = this.sidebar;
      LinearLayout var252 = var277;
      if (var277 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sidebar");
         var252 = null;
      }

      var2 = var191.indexOfChild((View)var252);
      android.widget.FrameLayout.LayoutParams var192 = new android.widget.FrameLayout.LayoutParams(-2, -2, 8388691);
      var192.setMarginStart(this.dp(24));
      var192.bottomMargin = this.dp(84);
      Unit var253 = Unit.INSTANCE;
      var76.addView(var9, var2, (LayoutParams)var192);
      this.setupMapFrame();
      ReplayBar var193 = new ReplayBar((Context)this, null, 2, null);
      var193.setVisibility(8);
      var193.setTranslationZ(this.dp(60));
      Unit var77 = Unit.INSTANCE;
      this.replayBar = var193;
      FrameLayout var194 = this.root;
      FrameLayout var78 = var194;
      if (var194 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("root");
         var78 = null;
      }

      ReplayBar var254 = this.replayBar;
      ReplayBar var195 = var254;
      if (var254 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("replayBar");
         var195 = null;
      }

      View var255 = var195;
      android.widget.FrameLayout.LayoutParams var278 = new android.widget.FrameLayout.LayoutParams(-2, -2, 49);
      var278.topMargin = this.dp(12);
      Unit var196 = Unit.INSTANCE;
      var78.addView(var255, (LayoutParams)var278);
      TextView var79 = new TextView((Context)this);
      var79.setTextColor(-1);
      var79.setTextSize(17.0F);
      var79.setTypeface(null, 1);
      var79.setVisibility(8);
      var79.setPadding(this.dp(16), this.dp(8), this.dp(16), this.dp(8));
      var79.setTranslationZ(this.dp(58));
      GradientDrawable var197 = new GradientDrawable();
      var197.setCornerRadius(this.dp(20));
      var197.setColor(Color.argb(200, 20, 60, 140));
      var197.setStroke(this.dp(1), Color.argb(140, 120, 180, 255));
      var79.setBackground((Drawable)var197);
      Unit var198 = Unit.INSTANCE;
      this.ideasChip = var79;
      FrameLayout var199 = this.root;
      FrameLayout var80 = var199;
      if (var199 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("root");
         var80 = null;
      }

      TextView var256 = this.ideasChip;
      TextView var200 = var256;
      if (var256 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ideasChip");
         var200 = null;
      }

      View var257 = (View)var200;
      android.widget.FrameLayout.LayoutParams var279 = new android.widget.FrameLayout.LayoutParams(-2, -2, 49);
      var279.topMargin = this.dp(70);
      Unit var201 = Unit.INSTANCE;
      var80.addView(var257, (LayoutParams)var279);
      LinearLayout var258 = new LinearLayout((Context)this);
      var4 = false;
      var258.setOrientation(1);
      var258.setVisibility(8);
      var258.setTranslationZ(this.dp(62));
      var258.setPadding(this.dp(16), this.dp(12), this.dp(16), this.dp(12));
      GradientDrawable var81 = new GradientDrawable();
      var81.setCornerRadius(this.dp(18));
      var81.setColor(Color.argb(235, 24, 26, 32));
      var258.setBackground((Drawable)var81);
      TextView var82 = new TextView((Context)this);
      var82.setText("Why did you take over?");
      var82.setTextColor(-1);
      var82.setTextSize(18.0F);
      var82.setTypeface(null, 1);
      var258.addView((View)var82);
      LinearLayout var280 = new LinearLayout((Context)this);
      var280.setOrientation(0);

      for (String var264 : CollectionsKt.listOf(new String[]{"Too close", "Swerved", "Wrong lane", "Braked late", "Just because"})) {
         TextView var10 = new TextView((Context)this);
         var10.setText(var264);
         var10.setTextColor(-1);
         var10.setTextSize(16.0F);
         var10.setPadding(this.dp(14), this.dp(12), this.dp(14), this.dp(12));
         GradientDrawable var288 = new GradientDrawable();
         var288.setCornerRadius(this.dp(12));
         var288.setColor(Color.rgb(48, 54, 66));
         var10.setBackground((Drawable)var288);
         var10.setOnClickListener(new MainActivity$$ExternalSyntheticLambda74(this, var264));
         View var265 = (View)var10;
         android.widget.LinearLayout.LayoutParams var289 = new android.widget.LinearLayout.LayoutParams(-2, -2);
         var289.setMarginEnd(this.dp(8));
         var289.topMargin = this.dp(10);
         Unit var290 = Unit.INSTANCE;
         var280.addView(var265, (LayoutParams)var289);
      }

      var258.addView((View)var280);
      Unit var83 = Unit.INSTANCE;
      this.ideasWhy = var258;
      FrameLayout var203 = this.root;
      FrameLayout var84 = var203;
      if (var203 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("root");
         var84 = null;
      }

      LinearLayout var259 = this.ideasWhy;
      LinearLayout var204 = var259;
      if (var259 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ideasWhy");
         var204 = null;
      }

      View var260 = (View)var204;
      android.widget.FrameLayout.LayoutParams var205 = new android.widget.FrameLayout.LayoutParams(-2, -2, 49);
      var205.topMargin = this.dp(120);
      Unit var266 = Unit.INSTANCE;
      var84.addView(var260, (LayoutParams)var205);
      ImageView var85 = new ImageView((Context)this);
      var85.setVisibility(8);
      var85.setTranslationZ(this.dp(40));
      var85.setScaleType(ScaleType.FIT_CENTER);
      GradientDrawable var206 = new GradientDrawable();
      var206.setCornerRadius(this.dp(10));
      var206.setStroke(this.dp(2), Color.argb(200, 255, 255, 255));
      var85.setBackground((Drawable)var206);
      Unit var207 = Unit.INSTANCE;
      this.ideasDay = var85;
      FrameLayout var208 = this.root;
      FrameLayout var86 = var208;
      if (var208 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("root");
         var86 = null;
      }

      ImageView var261 = this.ideasDay;
      ImageView var209 = var261;
      if (var261 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("ideasDay");
         var209 = null;
      }

      View var262 = (View)var209;
      android.widget.FrameLayout.LayoutParams var267 = new android.widget.FrameLayout.LayoutParams(this.dp(320), this.dp(160), 8388693);
      var267.setMarginEnd(this.dp(24));
      var267.bottomMargin = this.dp(100);
      Unit var210 = Unit.INSTANCE;
      var86.addView(var262, (LayoutParams)var267);
      OverlayView var211 = this.overlay;
      OverlayView var87 = var211;
      if (var211 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("overlay");
         var87 = null;
      }

      var87.setVisibility(0);
      SurfaceView var88 = this.videoView;
      if (var88 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("videoView");
         var88 = null;
      }

      var88.setVisibility(0);
      this.updateTheme();
      this.ui.post(this.uiTick);
      this.discover();
   }

   @Override
   protected void onDestroy() {
      super.onDestroy();
      SideCam.INSTANCE.close();
      if (this.isFinishing()) {
         LinkService.Companion.stop((Context)this);
      }

      Handler var1 = this.ui;
      Object var2 = null;
      var1.removeCallbacksAndMessages(null);
      this.stopVideo();
      this.syncDetector(false);
      Detector var4 = this.detectorRef;
      if (var4 != null) {
         var4.shutdown();
      }

      this.detectorRef = null;
      LayoutSeg var3 = this.layoutSeg;
      LayoutSeg var5 = var3;
      if (var3 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("layoutSeg");
         var5 = null;
      }

      var5.shutdown();
      DataClient var6 = this.data;
      if (var6 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("data");
         var6 = (DataClient)var2;
      }

      var6.close();
   }

   @Override
   protected void onNewIntent(Intent var1) {
      Intrinsics.checkNotNullParameter(var1, "intent");
      super.onNewIntent(var1);
      this.setIntent(var1);
      if (var1.getBooleanExtra("open_nav", false)) {
         var1.removeExtra("open_nav");
         this.getWindow().getDecorView().postDelayed(new MainActivity$$ExternalSyntheticLambda103(this), 300L);
      }

      this.maskIntent(var1);
      this.shareIntent(var1);
   }

   @Override
   protected void onStart() {
      super.onStart();
      this.visible = true;
      LinkService.Companion.start((Context)this);
      if (!this.askedNotify && VERSION.SDK_INT >= 33 && this.checkSelfPermission("android.permission.POST_NOTIFICATIONS") != 0) {
         this.askedNotify = true;
         this.requestPermissions(new String[]{"android.permission.POST_NOTIFICATIONS"}, 71);
      }
   }

   @Override
   protected void onStop() {
      super.onStop();
      this.visible = false;
      if (this.backupArmed) {
         SettingsBackup.INSTANCE.auto((Context)this);
      }
   }

   public final void openMap() {
      if (this.outer != null) {
         MapPane var2 = this.mapPane;
         Object var4 = null;
         MapPane var1 = var2;
         if (var2 == null) {
            MapPane var5 = new MapPane((Context)this, new MapPane.Host() {

               @Override
               public void closeMap() {
                  MainActivity.this.closeMap();
               }

               @Override
               public DataClient getData() {
                  DataClient var2x = MainActivity.this.data;
                  DataClient var1x = var2x;
                  if (var2x == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("data");
                     var1x = null;
                  }

                  return var1x;
               }

               @Override
               public boolean getNight() {
                  return MainActivity.this.night;
               }

               @Override
               public UiSettings getSettings() {
                  UiSettings var2x = MainActivity.this.settings;
                  UiSettings var1x = var2x;
                  if (var2x == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("settings");
                     var1x = null;
                  }

                  return var1x;
               }

               @Override
               public Telemetry getTelemetry() {
                  return MainActivity.this.telemetry;
               }

               @Override
               public void navigateTo(String var1, String var2x, double var3, double var5x) {
                  Intrinsics.checkNotNullParameter(var1, "name");
                  Intrinsics.checkNotNullParameter(var2x, "address");
                  MainActivity.this.driveTo(var1, var2x, var3, var5x);
               }

               @Override
               public void openSearch() {
                  MainActivity.this.openDestinations();
               }
            });
            this.mapPane = var5;
            FrameLayout var12 = this.outer;
            FrameLayout var8 = var12;
            if (var12 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("outer");
               var8 = null;
            }

            View var7 = (View)var5;
            FrameLayout var3 = this.outer;
            FrameLayout var13 = var3;
            if (var3 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("outer");
               var13 = null;
            }

            View var6 = this.divider;
            View var17 = var6;
            if (var6 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("divider");
               var17 = null;
            }

            var8.addView(var7, var13.indexOfChild(var17), (LayoutParams)(new android.widget.FrameLayout.LayoutParams(this.mapWidth(), -1)));
            var1 = var5;
         }

         var1.setVisibility(0);
         View var14 = this.divider;
         View var9 = var14;
         if (var14 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("divider");
            var9 = null;
         }

         var9.setVisibility(0);
         UiSettings var15 = this.settings;
         UiSettings var10 = var15;
         if (var15 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("settings");
            var10 = null;
         }

         var10.set("map_open", true);
         this.layoutMap(this.mapWidth());
         Nav var16 = Nav.INSTANCE;
         DataClient var11 = this.data;
         if (var11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("data");
            var11 = (DataClient)var4;
         }

         var16.refresh(var11);
      }
   }

   public static final class Companion {
      private Companion() {
      }

      // $VF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }
   }

   // $VF: synthetic class
   public final class WhenMappings {
      public static final int[] $EnumSwitchMapping$0;

      static {
         int[] var0 = new int[VoiceCommands.Kind.values().length];

         try {
            var0[VoiceCommands.Kind.EXP_ON.ordinal()] = 1;
         } catch (NoSuchFieldError var21) {
         }

         try {
            var0[VoiceCommands.Kind.EXP_OFF.ordinal()] = 2;
         } catch (NoSuchFieldError var20) {
         }

         try {
            var0[VoiceCommands.Kind.MAP_OPEN.ordinal()] = 3;
         } catch (NoSuchFieldError var19) {
         }

         try {
            var0[VoiceCommands.Kind.MAP_CLOSE.ordinal()] = 4;
         } catch (NoSuchFieldError var18) {
         }

         try {
            var0[VoiceCommands.Kind.FOCUS_ON.ordinal()] = 5;
         } catch (NoSuchFieldError var17) {
         }

         try {
            var0[VoiceCommands.Kind.FOCUS_OFF.ordinal()] = 6;
         } catch (NoSuchFieldError var16) {
         }

         try {
            var0[VoiceCommands.Kind.VOICE_ON.ordinal()] = 7;
         } catch (NoSuchFieldError var15) {
         }

         try {
            var0[VoiceCommands.Kind.VOICE_OFF.ordinal()] = 8;
         } catch (NoSuchFieldError var14) {
         }

         try {
            var0[VoiceCommands.Kind.SAVER_ON.ordinal()] = 9;
         } catch (NoSuchFieldError var13) {
         }

         try {
            var0[VoiceCommands.Kind.SAVER_OFF.ordinal()] = 10;
         } catch (NoSuchFieldError var12) {
         }

         try {
            var0[VoiceCommands.Kind.KEEP.ordinal()] = 11;
         } catch (NoSuchFieldError var11) {
         }

         try {
            var0[VoiceCommands.Kind.FOLLOW.ordinal()] = 12;
         } catch (NoSuchFieldError var10) {
         }

         try {
            var0[VoiceCommands.Kind.FARTHER.ordinal()] = 13;
         } catch (NoSuchFieldError var9) {
         }

         try {
            var0[VoiceCommands.Kind.CLOSER.ordinal()] = 14;
         } catch (NoSuchFieldError var8) {
         }

         try {
            var0[VoiceCommands.Kind.SCENE.ordinal()] = 15;
         } catch (NoSuchFieldError var7) {
         }

         try {
            var0[VoiceCommands.Kind.CAMERA.ordinal()] = 16;
         } catch (NoSuchFieldError var6) {
         }

         try {
            var0[VoiceCommands.Kind.NAVIGATE.ordinal()] = 17;
         } catch (NoSuchFieldError var5) {
         }

         try {
            var0[VoiceCommands.Kind.NUDGE.ordinal()] = 18;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[VoiceCommands.Kind.BOOKMARK.ordinal()] = 19;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[VoiceCommands.Kind.UNKNOWN.ordinal()] = 20;
         } catch (NoSuchFieldError var2) {
         }

         $EnumSwitchMapping$0 = var0;
      }
   }

   static final class Anon_uiTick_1 implements Runnable {
      Anon_uiTick_1(MainActivity var1) {
         this.this$0 = var1;
      }

         final MainActivity this$0;

         // $VF: synthetic method
         public static String $r8$lambda$AD3oUc_uKHaDRp6UW3RvV63Drk8/* $VF was: $r8$lambda$AD3oUc_uKHaDRp6UW3RvV63Drk8*/(MainActivity var0, Telemetry var1) {
            return run$lambda$5(var0, var1);
         }

         // $VF: synthetic method
         public static CharSequence $r8$lambda$sJ6A_DP2ytDmO5tvSWBQ4UDjo0k(Entry var0) {
            return run$lambda$27$lambda$24(var0);
         }

         private static final CharSequence run$lambda$27$lambda$24(Entry var0) {
            Intrinsics.checkNotNullParameter(var0, "it");
            Object var1x = var0.getValue();
            Object var2 = var0.getKey();
            return var1x + " " + var2;
         }

         private static final String run$lambda$5(MainActivity var0, Telemetry var1x) {
            return LaneShots.Companion.caption(var0.laneTracker, "every 30 s", var1x.getVEgo() * 2.23694F);
         }

         // $VF: Duplicated exception handlers to handle obfuscated exceptions
         @Override
         public void run() {
            int var19 = this.this$0.tick;
            this.this$0.tick = var19 + 1;
            Telemetry var58 = this.this$0.telemetry;
            DataClient var54 = this.this$0.data;
            DataClient var53 = var54;
            if (var54 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("data");
               var53 = null;
            }

            boolean var29 = var53.getConnected();
            this.this$0.saverTick(var29);
            LinearLayout var461 = this.this$0.connectBar;
            LinearLayout var242 = var461;
            if (var461 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("connectBar");
               var242 = null;
            }

            byte var107;
            if (var29) {
               var107 = 8;
            } else {
               var107 = 0;
            }

            var242.setVisibility(var107);
            Number var243 = (Number)var58.getInfo().get("encStall");
            if (var243 instanceof Number) {
               var243 = var243;
            } else {
               var243 = null;
            }

            if (var243 != null) {
               var107 = var243.intValue();
            } else {
               var107 = 0;
            }

            if (var29 && var107 > 0) {
               this.this$0
                  .getStallBanner()
                  .setText("Comma video encoder stopped " + var107 + " s ago: no drive recording, no camera video.\nReboot the comma when parked.");
               this.this$0.getStallBanner().setVisibility(0);
            } else if (this.this$0.getStallBanner().getVisibility() != 8) {
               this.this$0.getStallBanner().setVisibility(8);
            }

            boolean var109;
            if (var29 && var58.getStarted() && var58.pBool("GpuQuiet", false)) {
               var109 = 1;
            } else {
               var109 = 0;
            }

            boolean var20;
            if (!var29 || !var58.getStarted() || !var109 && !Intrinsics.areEqual(Telemetry.pStr$default(var58, "ChestnutState", null, 2, null), "loading")) {
               var20 = 0;
            } else {
               var20 = 1;
            }

            if (var20) {
               long var51 = System.currentTimeMillis();
               if (this.this$0.gpuLoadSince == 0L) {
                  this.this$0.gpuLoadSince = var51;
               }

               long var49 = (var51 - this.this$0.gpuLoadSince) / 1000;
               TextView var462 = this.this$0.getGpuBanner();
               String var245;
               if (var109) {
                  var245 = "\ncamera video back when it's ready";
               } else {
                  var245 = "";
               }

               var462.setText("⏳  Big model still loading… " + var49 + " s" + var245);
               this.this$0.getGpuBanner().setAlpha((float)((Math.cos(var51 / 1000.0 * 4.0) * 0.5 + 0.5) * 0.55 + 0.45));
               this.this$0.getGpuBanner().setVisibility(0);
            } else {
               this.this$0.gpuLoadSince = 0L;
               if (this.this$0.getGpuBanner().getVisibility() != 8) {
                  this.this$0.getGpuBanner().setVisibility(8);
               }
            }

            if (this.this$0.tick % 20 == 0) {
               Function0 var246 = this.this$0.tabletModePusher;
               if (var246 != null) {
                  var246.invoke();
                  Unit var247 = Unit.INSTANCE;
               }
            }

            if (this.this$0.tick % 100 == 50) {
               this.this$0.checkUsbReturn();
            }

            if (var29) {
               this.this$0.navTick(System.currentTimeMillis());
            }

            MainActivity var55 = this.this$0;
            UiSettings var463 = this.this$0.settings;
            UiSettings var248 = var463;
            if (var463 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("settings");
               var248 = null;
            }

            var55.statsVisible = var248.get("stats");
            TextView var464 = this.this$0.statsTv;
            TextView var249 = var464;
            if (var464 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("statsTv");
               var249 = null;
            }

            if (this.this$0.statsVisible) {
               var109 = 0;
            } else {
               var109 = 8;
            }

            boolean var28;
            label2294: {
               var249.setVisibility(var109);
               if (var29 && !var58.getStarted()) {
                  UiSettings var465 = this.this$0.settings;
                  UiSettings var250 = var465;
                  if (var465 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("settings");
                     var250 = null;
                  }

                  if (var250.get("offroad_home") && this.this$0.hudEditor == null) {
                     var28 = true;
                     break label2294;
                  }
               }

               var28 = false;
            }

            if (var28 != this.this$0.homeShown) {
               this.this$0.homeShown = var28;
               OffroadHome var466 = this.this$0.offroadHome;
               OffroadHome var251 = var466;
               if (var466 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("offroadHome");
                  var251 = null;
               }

               if (var28) {
                  var109 = 0;
               } else {
                  var109 = 8;
               }

               var251.setVisibility(var109);
               LinearLayout var467 = this.this$0.sidebar;
               LinearLayout var252 = var467;
               if (var467 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("sidebar");
                  var252 = null;
               }

               if (var28) {
                  var109 = 0;
               } else {
                  var109 = 8;
               }

               var252.setVisibility(var109);
            }

            if (var28) {
               OffroadHome var468 = this.this$0.offroadHome;
               OffroadHome var253 = var468;
               if (var468 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("offroadHome");
                  var253 = null;
               }

               OffroadHome.refresh$default(var253, var58, false, 2, null);
            }

            if (var28 && this.this$0.tick % 600 == 30) {
               KeepDrive.INSTANCE.applyPending((Context)this.this$0);
            }

            StorageInfo var632 = StorageInfo.INSTANCE;
            OffroadHome var469 = this.this$0.offroadHome;
            OffroadHome var254 = var469;
            if (var469 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("offroadHome");
               var254 = null;
            }

            boolean var113;
            if (var632.copying(var254.getStorage()) != null) {
               var113 = 1;
            } else {
               var113 = 0;
            }

            label2279:
            if (var28) {
               if (this.this$0.tick % 300 != 5) {
                  OffroadHome var470 = this.this$0.offroadHome;
                  OffroadHome var255 = var470;
                  if (var470 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("offroadHome");
                     var255 = null;
                  }

                  if (var255.getStorage() != null && !var113 || this.this$0.tick % 40 != 5) {
                     break label2279;
                  }
               }

               this.this$0.fetchStorage();
            }

            TextView var471 = this.this$0.statusTv;
            TextView var256 = var471;
            if (var471 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("statusTv");
               var256 = null;
            }

            if ((!var29 || !var58.getStarted()) && !var28) {
               var113 = 0;
            } else {
               var113 = 8;
            }

            var256.setVisibility(var113);
            MainActivity var257 = this.this$0;
            if (var29 && var58.getStarted()) {
               var28 = true;
            } else {
               var28 = false;
            }

            var257.syncScene(var28);
            boolean var115;
            if (var29 && var58.getStarted() && !this.this$0.sceneOn && this.this$0.visible) {
               var115 = 1;
            } else {
               var115 = 0;
            }

            if (var115 && !this.this$0.videoConnected) {
               this.this$0.startVideo();
            }

            if (!var115 && this.this$0.videoConnected) {
               this.this$0.stopVideo();
            }

            label2245: {
               label2244: {
                  var55 = this.this$0;
                  if (this.this$0.visible && var29 && var58.getStarted()) {
                     UiSettings var472 = this.this$0.settings;
                     UiSettings var258 = var472;
                     if (var472 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("settings");
                        var258 = null;
                     }

                     if (var258.get("detector_test") || this.this$0.sceneOn || this.this$0.maskEditor != null) {
                        break label2244;
                     }

                     NewIdeas var56 = NewIdeas.INSTANCE;
                     UiSettings var473 = this.this$0.settings;
                     UiSettings var259 = var473;
                     if (var473 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("settings");
                        var259 = null;
                     }

                     if (var56.needsCamera(var259)) {
                        break label2244;
                     }
                  }

                  var28 = false;
                  break label2245;
               }

               var28 = true;
            }

            var55.syncDetector(var28);
            OsmRoads var634 = OsmRoads.INSTANCE;
            UiSettings var474 = this.this$0.settings;
            UiSettings var260 = var474;
            if (var474 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("settings");
               var260 = null;
            }

            var634.setEnabled(var260.get("map_roads"));
            OverlayView var475 = this.this$0.overlay;
            OverlayView var261 = var475;
            if (var475 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("overlay");
               var261 = null;
            }

            if (var261.updateCalibration()) {
               this.this$0.placeVideo();
            }

            OverlayView var476 = this.this$0.overlay;
            OverlayView var262 = var476;
            if (var476 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("overlay");
               var262 = null;
            }

            var262.setUiStatus(UiStatus.Companion.from(var58));
            if (System.currentTimeMillis() - this.this$0.lastThemeCheck > 15000L) {
               this.this$0.lastThemeCheck = System.currentTimeMillis();
               this.this$0.updateTheme();
            }

            var28 = var58.isMetric();
            float var9;
            if (var28) {
               var9 = 3.6F;
            } else {
               var9 = 2.23694F;
            }

            float var10;
            if (!var58.tBool("use_wheel_speed", false) && !(var58.getVEgoCluster() <= 0.0F)) {
               var10 = var58.getVEgoCluster();
            } else {
               var10 = var58.getVEgo();
            }

            TextView var477 = this.this$0.speedTv;
            TextView var263 = var477;
            if (var477 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("speedTv");
               var263 = null;
            }

            var263.setText(String.valueOf(RangesKt.coerceAtLeast(MathKt.roundToInt(var10 * var9), 0)));
            TextView var478 = this.this$0.speedUnit;
            TextView var264 = var478;
            if (var478 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("speedUnit");
               var264 = null;
            }

            String var479;
            if (var28) {
               var479 = "km/h";
            } else {
               var479 = "mph";
            }

            var264.setText(var479);
            if (var58.s("show_speed") && var58.getStarted()) {
               var115 = (boolean)0;
            } else {
               var115 = (boolean)1;
            }

            TextView var480 = this.this$0.speedTv;
            TextView var265 = var480;
            if (var480 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("speedTv");
               var265 = null;
            }

            if (var115) {
               var115 = 4;
            } else {
               var115 = 0;
            }

            var265.setVisibility(var115);
            TextView var481 = this.this$0.speedUnit;
            TextView var266 = var481;
            if (var481 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("speedUnit");
               var266 = null;
            }

            TextView var635 = this.this$0.speedTv;
            TextView var482 = var635;
            if (var635 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("speedTv");
               var482 = null;
            }

            var266.setVisibility(var482.getVisibility());
            if (var58.getVCruiseCluster() > 0.0F) {
               var9 = var58.getVCruiseCluster();
            } else {
               var9 = var58.getVCruise();
            }

            boolean var118;
            if (var58.getCruiseEnabled() && var9 > 0.0F && var9 < 200.0F) {
               var118 = 1;
            } else {
               var118 = 0;
            }

            float var11 = var58.tFloat("set_speed_offset", 0.0F);
            TextView var483 = this.this$0.setSpeedTv;
            TextView var267 = var483;
            if (var483 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("setSpeedTv");
               var267 = null;
            }

            String var484;
            if (var118) {
               if (var28) {
                  var10 = 1.0F;
               } else {
                  var10 = 0.621371F;
               }

               var484 = String.valueOf(MathKt.roundToInt((var9 + var11) * var10));
            } else {
               var484 = "–";
            }

            var267.setText(var484);
            TextView var485 = this.this$0.setSpeedTv;
            TextView var268 = var485;
            if (var485 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("setSpeedTv");
               var268 = null;
            }

            if (var118) {
               var20 = -1;
            } else {
               var20 = Color.rgb(114, 114, 114);
            }

            var268.setTextColor(var20);
            TextView var486 = this.this$0.maxLabel;
            TextView var269 = var486;
            if (var486 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("maxLabel");
               var269 = null;
            }

            if (!var118) {
               var118 = Color.rgb(166, 166, 166);
            } else if (var58.getEnabled() && var58.getActive()) {
               var118 = Color.rgb(128, 216, 166);
            } else {
               var118 = Color.rgb(145, 155, 149);
            }

            var269.setTextColor(var118);
            LinearLayout var487 = this.this$0.maxCard;
            LinearLayout var270 = var487;
            if (var487 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("maxCard");
               var270 = null;
            }

            byte var120;
            if (var58.s("show_max") && var58.getStarted()) {
               var120 = 0;
            } else {
               var120 = 4;
            }

            var270.setVisibility(var120);
            SlcView var488 = this.this$0.slc;
            SlcView var271 = var488;
            if (var488 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("slc");
               var271 = null;
            }

            if (var58.getStarted() && var58.s("speed_limit")) {
               var120 = (byte)0;
            } else {
               var120 = (byte)4;
            }

            var271.setVisibility(var120);
            WheelButton var489 = this.this$0.wheel;
            WheelButton var272 = var489;
            if (var489 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("wheel");
               var272 = null;
            }

            if (var58.s("wheel_button") && var58.getStarted()) {
               var120 = (byte)0;
            } else {
               var120 = (byte)4;
            }

            var272.setVisibility(var120);
            MiciDistanceView var490 = this.this$0.personality;
            MiciDistanceView var273 = var490;
            if (var490 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("personality");
               var273 = null;
            }

            if (var58.getStarted() && var58.s("personality_button")) {
               var120 = (byte)0;
            } else {
               var120 = (byte)8;
            }

            var273.setVisibility(var120);
            SlcActionsView var491 = this.this$0.slcActions;
            SlcActionsView var274 = var491;
            if (var491 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("slcActions");
               var274 = null;
            }

            label2156: {
               if (var58.getStarted() && var58.s("speed_limit")) {
                  SlcActionsView var636 = this.this$0.slcActions;
                  SlcActionsView var492 = var636;
                  if (var636 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("slcActions");
                     var492 = null;
                  }

                  if (!SlcActionsView.controls$default(var492, 0L, 1, null).isEmpty()) {
                     var120 = (byte)0;
                     break label2156;
                  }
               }

               var120 = (byte)8;
            }

            var274.setVisibility(var120);
            Pair var275;
            if (var58.getStarted() && var58.s("csc_glow")) {
               var275 = DrivingMode.curveLabel$default(DrivingMode.INSTANCE, var58, 0L, 2, null);
            } else {
               var275 = null;
            }

            TextView var637 = this.this$0.curveLabel;
            TextView var493 = var637;
            if (var637 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("curveLabel");
               var493 = null;
            }

            label2143: {
               if (var275 != null) {
                  String var638 = (String)var275.getFirst();
                  if (var638 != null) {
                     var639 = var638;
                     break label2143;
                  }
               }

               var639 = "";
            }

            var493.setText(var639);
            if (var275 != null) {
               TextView var640 = this.this$0.curveLabel;
               TextView var494 = var640;
               if (var640 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("curveLabel");
                  var494 = null;
               }

               var494.setTextColor(((Number)var275.getSecond()).intValue());
               Unit var495 = Unit.INSTANCE;
               Unit var496 = Unit.INSTANCE;
            }

            TextView var641 = this.this$0.curveLabel;
            TextView var497 = var641;
            if (var641 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("curveLabel");
               var497 = null;
            }

            if (var275 != null) {
               var120 = (byte)0;
            } else {
               var120 = (byte)8;
            }

            var497.setVisibility(var120);
            var28 = Intrinsics.areEqual(var58.getAlertSize(), "none");
            TextView var498 = this.this$0.clockTv;
            TextView var276 = var498;
            if (var498 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("clockTv");
               var276 = null;
            }

            if (var58.getStarted() && var58.s("clock") && var28) {
               var120 = (byte)0;
            } else {
               var120 = (byte)8;
            }

            var276.setVisibility(var120);
            TextView var499 = this.this$0.clockTv;
            TextView var277 = var499;
            if (var499 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("clockTv");
               var277 = null;
            }

            if (var277.getVisibility() == 0) {
               TextView var500 = this.this$0.clockTv;
               TextView var278 = var500;
               if (var500 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("clockTv");
                  var278 = null;
               }

               var278.setText(DrivingMode.clock$default(DrivingMode.INSTANCE, null, var58.s("clock_24h"), 1, null));
            }

            TextView var501 = this.this$0.modeText;
            TextView var279 = var501;
            if (var501 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("modeText");
               var279 = null;
            }

            if (var58.getStarted() && var58.s("mode_text") && var28) {
               var120 = (byte)0;
            } else {
               var120 = (byte)8;
            }

            var279.setVisibility(var120);
            TextView var502 = this.this$0.modeText;
            TextView var280 = var502;
            if (var502 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("modeText");
               var280 = null;
            }

            if (var280.getVisibility() == 0) {
               TextView var503 = this.this$0.modeText;
               TextView var281 = var503;
               if (var503 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("modeText");
                  var281 = null;
               }

               var281.setText(DrivingMode.INSTANCE.describe(var58));
            }

            DevSidebarView var504 = this.this$0.devSidebar;
            DevSidebarView var282 = var504;
            if (var504 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("devSidebar");
               var282 = null;
            }

            if (var58.getStarted() && var58.s("dev_sidebar")) {
               var120 = (byte)0;
            } else {
               var120 = (byte)8;
            }

            var282.setVisibility(var120);
            SideCam var505 = SideCam.INSTANCE;
            String var283 = this.this$0.host;
            var120 = this.this$0.port;
            var11 = 0.0F;
            SideCam.tick$default(var505, var58, var283, var120, 0L, 8, null);
            List var642 = SideCam.INSTANCE.wanted(var58);
            SideView var506 = this.this$0.pipLeft;
            SideView var284 = var506;
            if (var506 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("pipLeft");
               var284 = null;
            }

            byte var130;
            if (var642.contains("left")) {
               var130 = 0;
            } else {
               var130 = 8;
            }

            var284.setVisibility(var130);
            SideView var507 = this.this$0.pipRight;
            SideView var285 = var507;
            if (var507 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("pipRight");
               var285 = null;
            }

            if (var642.contains("right")) {
               var130 = 0;
            } else {
               var130 = 8;
            }

            var285.setVisibility(var130);
            String var643 = Telemetry.pStr$default(var58, "ChestnutState", null, 2, null);
            boolean var132;
            if (var643.length() > 0) {
               var132 = 1;
            } else {
               var132 = 0;
            }

            if (var132) {
               label2061: {
                  if (var58.getStarted() && var58.s("egpu_status")) {
                     String[] var286 = new String[]{"active", "loading", "failed"};
                     if (SetsKt.setOf(var286).contains(var643)) {
                        var132 = (boolean)1;
                        break label2061;
                     }
                  }

                  var132 = (boolean)0;
               }

               ImageView var508 = this.this$0.egpu;
               ImageView var287 = var508;
               if (var508 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("egpu");
                  var287 = null;
               }

               byte var187;
               if (var132) {
                  var187 = 0;
               } else {
                  var187 = 8;
               }

               var287.setVisibility(var187);
               if (var132) {
                  if (Intrinsics.areEqual(var643, "loading")) {
                     ImageView var509 = this.this$0.egpu;
                     ImageView var288 = var509;
                     if (var509 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("egpu");
                        var288 = null;
                     }

                     var288.setImageResource(R.drawable.egpu_loading);
                     ImageView var510 = this.this$0.egpu;
                     ImageView var289 = var510;
                     if (var510 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("egpu");
                        var289 = null;
                     }

                     var289.setAlpha((float)((0.5 - Math.cos(System.nanoTime() / 1.0E9 * 6.0) * 0.5) * 0.65 + 0.35));
                  } else if (Intrinsics.areEqual(var643, "failed")) {
                     ImageView var511 = this.this$0.egpu;
                     ImageView var290 = var511;
                     if (var511 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("egpu");
                        var290 = null;
                     }

                     var290.setImageResource(R.drawable.egpu_orange);
                     ImageView var512 = this.this$0.egpu;
                     ImageView var291 = var512;
                     if (var512 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("egpu");
                        var291 = null;
                     }

                     var291.setAlpha(1.0F);
                  } else {
                     ImageView var513 = this.this$0.egpu;
                     ImageView var292 = var513;
                     if (var513 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("egpu");
                        var292 = null;
                     }

                     var292.setImageResource(R.drawable.egpu_green);
                     ImageView var514 = this.this$0.egpu;
                     ImageView var293 = var514;
                     if (var514 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("egpu");
                        var293 = null;
                     }

                     var293.setAlpha(1.0F);
                  }
               }
            } else {
               var55 = this.this$0;
               var var294 = (Anon_uiTick_1)this;
               if (var58.getChestnutPresent() || var58.getStarted() && var55.egpuSeen) {
                  var28 = true;
               } else {
                  var28 = false;
               }

               var55.egpuSeen = var28;
               if (var58.getStarted() && var58.s("egpu_status") && var28 && var58.pBool("UsbGpuCompiled", false)) {
                  var132 = (boolean)1;
               } else {
                  var132 = (boolean)0;
               }

               if (!var132) {
                  ImageView var304 = var55.egpu;
                  if (var304 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("egpu");
                     var304 = null;
                  }

                  var304.setVisibility(8);
                  var55.egpuFallback = false;
               } else {
                  var29 = var58.pBool("UsbGpuLoading", false);
                  var28 = var58.pBool("UsbGpuActive", false);
                  if (var58.getModelSeen() && System.nanoTime() - var58.getLastModelRxNs() < 1000000000L) {
                     var132 = (boolean)1;
                  } else {
                     var132 = (boolean)0;
                  }

                  if (!var28 || var58.getModelSeen() && !var132) {
                     var132 = (boolean)1;
                  } else {
                     var132 = (boolean)0;
                  }

                  if (var58.getEnabled() && !var55.egpuWasEngaged && !var29 && !var28 && var58.getModelSeen()) {
                     var55.egpuFallback = true;
                  }

                  var55.egpuWasEngaged = var58.getEnabled();
                  if (var55.egpuFallback && var132) {
                     var28 = true;
                  } else {
                     var28 = false;
                  }

                  var55.egpuFallback = var28;
                  ImageView var515 = var55.egpu;
                  ImageView var295 = var515;
                  if (var515 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("egpu");
                     var295 = null;
                  }

                  var295.setVisibility(0);
                  if (var29) {
                     ImageView var516 = var55.egpu;
                     ImageView var296 = var516;
                     if (var516 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("egpu");
                        var296 = null;
                     }

                     var296.setImageResource(R.drawable.egpu_loading);
                     ImageView var517 = var55.egpu;
                     ImageView var297 = var517;
                     if (var517 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("egpu");
                        var297 = null;
                     }

                     var297.setAlpha((float)((0.5 - Math.cos(System.nanoTime() / 1.0E9 * 6.0) * 0.5) * 0.65 + 0.35));
                  } else if (var55.egpuFallback) {
                     ImageView var518 = var55.egpu;
                     ImageView var298 = var518;
                     if (var518 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("egpu");
                        var298 = null;
                     }

                     var298.setImageResource(R.drawable.egpu_crossed);
                     ImageView var519 = var55.egpu;
                     ImageView var299 = var519;
                     if (var519 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("egpu");
                        var299 = null;
                     }

                     var299.setAlpha(0.65F);
                  } else if (var132) {
                     ImageView var520 = var55.egpu;
                     ImageView var300 = var520;
                     if (var520 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("egpu");
                        var300 = null;
                     }

                     var300.setImageResource(R.drawable.egpu_orange);
                     ImageView var521 = var55.egpu;
                     ImageView var301 = var521;
                     if (var521 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("egpu");
                        var301 = null;
                     }

                     var301.setAlpha(1.0F);
                  } else {
                     ImageView var522 = var55.egpu;
                     ImageView var302 = var522;
                     if (var522 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("egpu");
                        var302 = null;
                     }

                     var302.setImageResource(R.drawable.egpu_green);
                     ImageView var523 = var55.egpu;
                     ImageView var303 = var523;
                     if (var523 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("egpu");
                        var303 = null;
                     }

                     var303.setAlpha(1.0F);
                  }
               }

               Unit var305 = Unit.INSTANCE;
            }

            TextView var524 = this.this$0.roadName;
            TextView var306 = var524;
            if (var524 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("roadName");
               var306 = null;
            }

            CharSequence var525;
            if (var58.getStarted() && var58.s("road_name")) {
               var525 = var58.getRoadName();
            } else {
               var525 = "";
            }

            var306.setText(var525);
            AetherGaugeView var526 = this.this$0.gauge;
            AetherGaugeView var307 = var526;
            if (var526 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("gauge");
               var307 = null;
            }

            if (var58.getStarted() && var58.s("aether_gauge")) {
               var132 = 0;
            } else {
               var132 = 8;
            }

            var307.setVisibility(var132);
            SlcSourcesView var527 = this.this$0.slcSources;
            SlcSourcesView var308 = var527;
            if (var527 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("slcSources");
               var308 = null;
            }

            label2042: {
               if (var58.getStarted() && var58.s("speed_limit")) {
                  if (var58.getSp7()) {
                     var28 = var58.s("slc_sources_sp7");
                  } else {
                     var28 = var58.pBool("SpeedLimitSources", false);
                  }

                  if (var28) {
                     var132 = 0;
                     break label2042;
                  }
               }

               var132 = 8;
            }

            var308.setVisibility(var132);
            Pair var645 = Telemetry.navCard$default(var58, 0L, 1, null);
            NavCardView var528 = this.this$0.navCard;
            NavCardView var309 = var528;
            if (var528 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("navCard");
               var309 = null;
            }

            if (var58.getStarted() && var58.s("nav_card") && var645 != null && !this.this$0.getMapOpen()) {
               var132 = 0;
            } else {
               var132 = 8;
            }

            var309.setVisibility(var132);
            NavCardView var529 = this.this$0.navCard;
            NavCardView var310 = var529;
            if (var529 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("navCard");
               var310 = null;
            }

            boolean var140;
            if (var645 != null && (Boolean)var645.getSecond()) {
               var140 = 1;
            } else {
               var140 = 0;
            }

            if (var140) {
               var9 = 0.55F;
            } else {
               var9 = 1.0F;
            }

            var310.setAlpha(var9);
            SlowReasonView var530 = this.this$0.slowCard;
            SlowReasonView var311 = var530;
            if (var530 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("slowCard");
               var311 = null;
            }

            label2020: {
               if (var58.getStarted() && var58.s("slow_card")) {
                  SlowReasonView var646 = this.this$0.slowCard;
                  SlowReasonView var531 = var646;
                  if (var646 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("slowCard");
                     var531 = null;
                  }

                  if (var531.current() != null) {
                     var140 = (byte)0;
                     break label2020;
                  }
               }

               var140 = (byte)8;
            }

            var311.setVisibility(var140);
            if (var58.getStarted()) {
               LaneBrain.Frame var532 = this.this$0.laneFeed.frame(var58, this.this$0.laneMarks, System.currentTimeMillis());
               if (var532 != null) {
                  var55 = this.this$0;
                  var55.laneTracker.update(var532);
                  long var234 = var532.getNowMs();

                  label2009: {
                     try {
                        var312 = var55.laneMapLog;
                     } catch (Exception var71) {
                        break label2009;
                     }

                     if (var312 != null) {
                        try {
                           var312.frame(var234, var58.getVEgo(), var532, var55.laneTracker, var55.laneTracker.turning(var234));
                           Unit var313 = Unit.INSTANCE;
                        } catch (Exception var70) {
                        }
                     }
                  }

                  double[] var314;
                  if (RtkPosition.INSTANCE.fast(var234)) {
                     var314 = RtkPosition.INSTANCE.now(var234, var58.getVEgo());
                  } else {
                     var314 = null;
                  }

                  OsmRoads.Fix var692 = OsmRoads.INSTANCE.getLastFix();
                  double var1x;
                  if (var314 != null) {
                     var1x = var314[0];
                  } else if (var692 != null) {
                     var1x = var692.getLat();
                  } else {
                     var1x = Double.NaN;
                  }

                  double var3;
                  if (var314 != null) {
                     var3 = var314[1];
                  } else if (var692 != null) {
                     var3 = var692.getLon();
                  } else {
                     var3 = Double.NaN;
                  }

                  double var85;
                  label2004: {
                     if (var314 != null) {
                        var85 = RtkPosition.INSTANCE.getHeading();
                        boolean var142;
                        if (!Double.isInfinite(var85) && !Double.isNaN(var85)) {
                           var142 = true;
                        } else {
                           var142 = false;
                        }

                        if (var142) {
                           var85 = RtkPosition.INSTANCE.getHeading();
                           break label2004;
                        }
                     }

                     if (var692 != null) {
                        var85 = var692.getBearingDeg();
                     } else {
                        var85 = Double.NaN;
                     }
                  }

                  try {
                     var55.laneAhead
                        .update(
                           var234, var55.laneTracker.getOdo(), var1x, var3, (Math.PI / 2) - Math.toRadians(var85), var58.getVEgo(), var55.laneTracker, var532
                        );
                  } catch (Exception var69) {
                  }

                  Unit var315 = Unit.INSTANCE;
                  Unit var316 = Unit.INSTANCE;
               }
            } else if (this.this$0.laneWasStarted) {
               this.this$0.laneTracker.endDrive();
               this.this$0.laneAhead.reset();
               LaneMapLog var317 = this.this$0.laneMapLog;
               if (var317 != null) {
                  var317.close();
                  Unit var318 = Unit.INSTANCE;
               }
            }

            if (var58.getStarted()) {
               Detector var319 = this.this$0.detectorRef;
               if (var319 != null) {
                  LaneModel var320 = var319.getLaneModel();
                  if (var320 != null) {
                     LaneModel.Answer var533 = var320.getAnswer();
                     if (var533 != null) {
                        MainActivity var321 = this.this$0;
                        if (System.currentTimeMillis() - var533.getAtMs() < 5000L) {
                           LaneBrain.modelSaw$default(var321.laneTracker, var533.getPL(), var533.getPR(), var533.getAtMs(), 0L, 8, null);
                        }

                        Unit var322 = Unit.INSTANCE;
                        Unit var323 = Unit.INSTANCE;
                     }
                  }
               }
            }

            SceneView var534 = this.this$0.scene;
            if (var534 != null) {
               var55 = this.this$0;
               LaneBrain.Road var324 = var55.laneTracker.getRoad();
               Pair var693 = var55.laneTracker.laneFromLeft();
               var140 = ((Number)var693.component1()).intValue();
               double var72 = ((Number)var693.component2()).doubleValue();
               var20 = var55.laneTracker.laneCount().component1().intValue();
               SceneView.LaneHint var325;
               if (var58.getStarted() && var324 != null && var140 > 0) {
                  var325 = new SceneView.LaneHint(var324.getId(), var140 - 1, var20, var72, Double.NaN, System.nanoTime());
               } else {
                  var325 = null;
               }

               var534.setLaneHint(var325);
               Unit var326 = Unit.INSTANCE;
               Unit var327 = Unit.INSTANCE;
            }

            if (var58.getStarted() && !this.this$0.laneWasStarted) {
               this.this$0.getLaneShots().newDrive();
            }

            this.this$0.laneWasStarted = var58.getStarted();
            LaneCardView var535 = this.this$0.laneCard;
            LaneCardView var328 = var535;
            if (var535 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("laneCard");
               var328 = null;
            }

            byte var144;
            if (var58.getStarted() && var58.s("lane_card")) {
               var144 = 0;
            } else {
               var144 = 8;
            }

            var328.setVisibility(var144);
            this.this$0.getLaneShots().setDetector(this.this$0.detectorRef);
            LaneCardView var536 = this.this$0.laneCard;
            LaneCardView var329 = var536;
            if (var536 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("laneCard");
               var329 = null;
            }

            if (var329.getVisibility() == 0 && var58.getVEgo() > 3.0F) {
               this.this$0.getLaneShots().tick(new MainActivity$uiTick$1$$ExternalSyntheticLambda0(this.this$0, var58));
            }

            BlinkerCardView var537 = this.this$0.blinkCard;
            BlinkerCardView var330 = var537;
            if (var537 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("blinkCard");
               var330 = null;
            }

            DataClient var649 = this.this$0.data;
            var54 = var649;
            if (var649 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("data");
               var54 = null;
            }

            if (var54.getConnected() && var58.s("blink_card")) {
               var144 = (byte)0;
            } else {
               var144 = (byte)8;
            }

            var330.setVisibility(var144);
            GpsCardView var539 = this.this$0.gpsCard;
            GpsCardView var331 = var539;
            if (var539 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("gpsCard");
               var331 = null;
            }

            DataClient var650 = this.this$0.data;
            var54 = var650;
            if (var650 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("data");
               var54 = null;
            }

            if (var54.getConnected() && var58.s("gps_card")) {
               var144 = (byte)0;
            } else {
               var144 = (byte)8;
            }

            var331.setVisibility(var144);
            boolean var31 = var58.s("lane_suggest");
            boolean var35 = var58.s("turn_signals");
            if (var58.s("car_memory")) {
               this.this$0.carMemory.update(var58, System.currentTimeMillis(), var58.getLeftBlindspot(), var58.getRightBlindspot());
            }

            if (var58.getLeftBlindspot() || var58.s("car_memory") && this.this$0.carMemory.beside(-1)) {
               var28 = true;
            } else {
               var28 = false;
            }

            if (var58.getRightBlindspot() || var58.s("car_memory") && this.this$0.carMemory.beside(1)) {
               var29 = true;
            } else {
               var29 = false;
            }

            if (Replay.INSTANCE.getActive() != this.this$0.replayWas) {
               this.this$0.replayWas = Replay.INSTANCE.getActive();
               LaneSuggestView var541 = this.this$0.laneSuggest;
               LaneSuggestView var332 = var541;
               if (var541 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("laneSuggest");
                  var332 = null;
               }

               var332.stop();
            }

            label2348: {
               label2349: {
                  if (!var58.getStarted()) {
                     LaneSuggestView var542 = this.this$0.laneSuggest;
                     LaneSuggestView var333 = var542;
                     if (var542 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("laneSuggest");
                        var333 = null;
                     }

                     if (!var333.getLogic().getTest()) {
                        LaneSuggestView var543 = this.this$0.laneSuggest;
                        LaneSuggestView var334 = var543;
                        if (var543 == null) {
                           Intrinsics.throwUninitializedPropertyAccessException("laneSuggest");
                           var334 = null;
                        }

                        if (var334.getLogic().getState() == LaneSuggest.State.IDLE) {
                           break label2349;
                        }
                     }
                  }

                  var54 = this.this$0.data;
                  DataClient var335 = var54;
                  if (var54 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("data");
                     var335 = null;
                  }

                  if (var335.getConnected() && (var31 || var35)) {
                     ModelSnapshot var651 = var58.getModel();
                     long var235 = System.currentTimeMillis();
                     JSONObject var59 = var58.navState();
                     var10 = var58.tFloat("minimum_lane_change_speed", 8.9F);
                     LaneSuggestView var545 = this.this$0.laneSuggest;
                     LaneSuggestView var336 = var545;
                     if (var545 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("laneSuggest");
                        var336 = null;
                     }

                     var336.setMetric(var58.isMetric());
                     LaneSuggestView var546 = this.this$0.laneSuggest;
                     LaneSuggestView var337 = var546;
                     if (var546 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("laneSuggest");
                        var337 = null;
                     }

                     boolean var30;
                     if (var337.getTurn().getState() == TurnSignal.State.SIGNALING) {
                        var30 = true;
                     } else {
                        var30 = false;
                     }

                     label1911: {
                        if (var59 != null) {
                           Double var338 = var59.optDouble("maneuverDistance", Double.NaN);
                           double var73 = var338.doubleValue();
                           boolean var147;
                           if (!Double.isInfinite(var73) && !Double.isNaN(var73)) {
                              var147 = true;
                           } else {
                              var147 = false;
                           }

                           if (!var147) {
                              var338 = null;
                           }

                           if (var338 != null) {
                              label1901: {
                                 var73 = var338.doubleValue();
                                 Pair var339 = Nav.INSTANCE.nextLanes();
                                 if (var339 != null) {
                                    if (Math.abs(((Number)var339.getSecond()).doubleValue() - var73) < 200.0) {
                                       var147 = true;
                                    } else {
                                       var147 = false;
                                    }

                                    if (!var147) {
                                       var339 = null;
                                    }

                                    if (var339 != null) {
                                       Nav.Step var340 = (Nav.Step)var339.getFirst();
                                       if (var340 != null) {
                                          var341 = var340.getLanes();
                                          break label1901;
                                       }
                                    }
                                 }

                                 var341 = null;
                              }

                              var547 = var341;
                              if (var341 != null) {
                                 break label1911;
                              }
                           }
                        }

                        var547 = CollectionsKt.emptyList();
                     }

                     LaneSuggest.In var344;
                     if (!var31) {
                        var344 = null;
                     } else {
                        Pair var342 = LaneSuggest.Companion.routeNeed(var59, this.this$0.laneTracker, var58.isMetric(), var547);
                        int var26 = ((Number)var342.component1()).intValue();
                        String var60 = (String)var342.component2();
                        LaneSuggest.Companion.Exit var61 = LaneSuggest.Companion.exitNeed(var59, var58.isMetric());
                        if (var58.getEnabled() && var58.getActive()) {
                           var31 = true;
                        } else {
                           var31 = false;
                        }

                        float var12 = var58.getVEgo();
                        float[] var343;
                        if (var651 != null) {
                           var343 = var651.getDesirePred();
                        } else {
                           var343 = null;
                        }

                        boolean var32;
                        if (var651 != null) {
                           var32 = var651.getLaneChanging();
                        } else {
                           var32 = false;
                        }

                        if (var651 != null) {
                           var144 = var651.getLaneChangeDir();
                        } else {
                           var144 = 0;
                        }

                        boolean var44 = var58.getLeftBlinker();
                        boolean var43 = var58.getRightBlinker();
                        boolean var38 = var58.getBrakePressed();
                        boolean var37 = var58.getSteeringPressed();
                        float var13 = var58.getSteeringTorque();
                        boolean var36 = var58.s("lane_nudge_confirm");
                        boolean var42 = var58.s("lane_auto");
                        LaneSuggestView var652 = this.this$0.laneSuggest;
                        LaneSuggestView var548 = var652;
                        if (var652 == null) {
                           Intrinsics.throwUninitializedPropertyAccessException("laneSuggest");
                           var548 = null;
                        }

                        boolean var45 = Intrinsics.areEqual(var548.getCommaConfirms(), true);
                        boolean var40 = var58.s("lane_pass");
                        if (var58.getVCruiseCluster() > 0.0F) {
                           var9 = var58.getVCruiseCluster();
                        } else {
                           var9 = var58.getVCruise();
                        }

                        label1881: {
                           if (var58.getCruiseEnabled()) {
                              boolean var189;
                              if (1.0F <= var9 && var9 <= 200.0F) {
                                 var189 = true;
                              } else {
                                 var189 = false;
                              }

                              if (var189) {
                                 var9 /= 3.6F;
                                 break label1881;
                              }
                           }

                           var9 = 0.0F;
                        }

                        boolean var46 = var58.getLeadOne().getStatus();
                        float var14 = var58.getLeadOne().getDRel();
                        float var18 = var58.getLeadOne().getVLead();
                        LaneBrain var549 = this.this$0.laneTracker;
                        int var23 = -1;
                        Pair var550 = var549.sideLanes(-1);
                        var20 = ((Number)var550.component1()).intValue();
                        double var75 = ((Number)var550.component2()).doubleValue();
                        if (var20 < 0 || !(var75 >= 0.5)) {
                           var20 = -1;
                        }

                        int var27 = LaneSuggest.Companion.routeSoon(var59);
                        boolean var41 = var58.isMetric();
                        boolean var39 = var58.s("exit_signals");
                        int var21;
                        if (var61 != null) {
                           var21 = var61.getDir();
                        } else {
                           var21 = 0;
                        }

                        if (var61 != null) {
                           var75 = var61.getDist();
                        } else {
                           var75 = Double.NaN;
                        }

                        label1865: {
                           if (var61 != null) {
                              var551 = var61.getWhy();
                              if (var551 != null) {
                                 break label1865;
                              }
                           }

                           var551 = "";
                        }

                        label1860: {
                           if (var61 != null) {
                              var653 = var61.getKey();
                              if (var653 != null) {
                                 break label1860;
                              }
                           }

                           var653 = "";
                        }

                        LaneBrain var694 = this.this$0.laneTracker;
                        int var22;
                        if (var61 != null) {
                           var22 = var61.getDir();
                        } else {
                           var22 = 0;
                        }

                        byte var202;
                        if (var22 < 0) {
                           var202 = var23;
                        } else {
                           var202 = 1;
                        }

                        Pair var695 = var694.sideLanes(var202);
                        var202 = ((Number)var695.component1()).intValue();
                        double var80 = ((Number)var695.component2()).doubleValue();
                        if (var202 < 0 || !(var80 >= 0.5)) {
                           var202 = -1;
                        }

                        boolean var33;
                        label1849: {
                           if (var61 != null) {
                              LaneSuggest.Companion var62;
                              LaneBrain var63;
                              label1846: {
                                 var62 = LaneSuggest.Companion;
                                 var63 = this.this$0.laneTracker;
                                 Pair var696 = Nav.INSTANCE.nextLanes();
                                 if (var696 != null) {
                                    var80 = ((Number)var696.getSecond()).doubleValue();
                                    boolean var205;
                                    if (0.0 <= var80 && var80 <= 800.0) {
                                       var205 = true;
                                    } else {
                                       var205 = false;
                                    }

                                    if (!var205) {
                                       var696 = null;
                                    }

                                    if (var696 != null) {
                                       Nav.Step var697 = (Nav.Step)var696.getFirst();
                                       if (var697 != null) {
                                          List var57 = var697.getLanes();
                                          var698 = var57;
                                          if (var57 != null) {
                                             break label1846;
                                          }
                                       }
                                    }
                                 }

                                 var698 = CollectionsKt.emptyList();
                              }

                              if (var62.inLaneFor(var61, var63, var698)) {
                                 var33 = true;
                                 break label1849;
                              }
                           }

                           var33 = false;
                        }

                        if (var58.s("lane_ends_ask")) {
                           var23 = this.this$0.laneAhead.need(this.this$0.laneTracker).getFirst().intValue();
                        } else {
                           var23 = 0;
                        }

                        String var719 = this.this$0.laneAhead.need(this.this$0.laneTracker).getSecond();
                        UiSettings var713 = this.this$0.settings;
                        UiSettings var699 = var713;
                        if (var713 == null) {
                           Intrinsics.throwUninitializedPropertyAccessException("settings");
                           var699 = null;
                        }

                        float var16 = var699.getFloat("nudge_card_pct", 60.0F) * 100.0F / 100.0F;
                        boolean var47 = var58.s("lane_return");
                        Pair var700 = this.this$0.laneTracker.sideLanes(1);
                        int var24 = ((Number)var700.component1()).intValue();
                        var80 = ((Number)var700.component2()).doubleValue();
                        if (var24 < 0 || !(var80 >= 0.5)) {
                           var24 = -1;
                        }

                        boolean var48 = var58.getLeadRight().getStatus();
                        float var17 = var58.getLeadRight().getDRel();
                        float var15 = var58.getLeadRight().getVLead();
                        int var25 = this.this$0.laneTracker.laneCount().getFirst().intValue();
                        if (var25 <= 0) {
                           var25 = -1;
                        }

                        LaneBrain.Road var701 = this.this$0.laneTracker.getRoad();
                        boolean var34;
                        if (var701 != null && var701.getHighway()) {
                           var34 = true;
                        } else {
                           var34 = false;
                        }

                        var344 = new LaneSuggest.In(
                           var235,
                           var31,
                           var12,
                           var10,
                           var343,
                           var32,
                           var144,
                           var44,
                           var43,
                           var28,
                           var29,
                           var38,
                           var26,
                           var60,
                           var37,
                           var13,
                           var36,
                           var42,
                           var45,
                           var30,
                           var40,
                           var9,
                           var46,
                           var14,
                           var18,
                           var20,
                           var27,
                           var41,
                           var39,
                           var21,
                           var75,
                           var551,
                           var653,
                           var202,
                           var33,
                           var23,
                           var719,
                           var16,
                           var47,
                           var24,
                           var48,
                           var17,
                           var15,
                           var25,
                           var34
                        );
                     }

                     var9 = var58.getPoseYawZ();
                     boolean var150;
                     if (!Float.isInfinite(var9) && !Float.isNaN(var9)) {
                        var150 = true;
                     } else {
                        var150 = false;
                     }

                     if (var150 && System.nanoTime() - var58.getPoseAtNs() < 1000000000L) {
                        var9 = Odometry.INSTANCE.getGyroSign() * var58.getPoseYawZ();
                     } else {
                        var9 = var58.getYawRate();
                     }

                     TurnSignal.In var552;
                     if (var35 && var58.getStarted()) {
                        float var106 = var58.getVEgo();
                        var30 = var58.getLeftBlinker();
                        var31 = var58.getRightBlinker();
                        var29 = var58.tBool("use_turn_desires", false);
                        LaneSuggestView var654 = this.this$0.laneSuggest;
                        LaneSuggestView var553 = var654;
                        if (var654 == null) {
                           Intrinsics.throwUninitializedPropertyAccessException("laneSuggest");
                           var553 = null;
                        }

                        double var77 = var553.crossGapFor(var59, var235);
                        LaneSuggestView var655 = this.this$0.laneSuggest;
                        LaneSuggestView var554 = var655;
                        if (var655 == null) {
                           Intrinsics.throwUninitializedPropertyAccessException("laneSuggest");
                           var554 = null;
                        }

                        if (var554.getLogic().getState() != LaneSuggest.State.IDLE) {
                           var28 = true;
                        } else {
                           var28 = false;
                        }

                        var552 = new TurnSignal.In(
                           var235,
                           var59,
                           var106,
                           var9,
                           var30,
                           var31,
                           var10,
                           var29,
                           var77,
                           var28,
                           Intrinsics.areEqual(var58.getGear(), "park"),
                           var58.getSteeringAngleDeg(),
                           LaneSuggest.Companion.notTurnLane(var59, this.this$0.laneTracker, Nav.INSTANCE.nextLanes())
                        );
                     } else {
                        var552 = null;
                     }

                     LaneSuggestView var702 = this.this$0.laneSuggest;
                     LaneSuggestView var656 = var702;
                     if (var702 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("laneSuggest");
                        var656 = null;
                     }

                     var656.tick(var344, var552);
                     break label2348;
                  }
               }

               var28 = false;
               LaneSuggestView var555 = this.this$0.laneSuggest;
               LaneSuggestView var345 = var555;
               if (var555 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("laneSuggest");
                  var345 = null;
               }

               if (var345.getVisibility() == 8) {
                  LaneSuggestView var556 = this.this$0.laneSuggest;
                  LaneSuggestView var346 = var556;
                  if (var556 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("laneSuggest");
                     var346 = null;
                  }

                  if (var346.getTurn().getHoldSide() == 0) {
                     break label2348;
                  }
               }

               LaneSuggestView var557 = this.this$0.laneSuggest;
               LaneSuggestView var347 = var557;
               if (var557 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("laneSuggest");
                  var347 = null;
               }

               var347.stop();
            }

            ReplayBar var558 = this.this$0.replayBar;
            ReplayBar var348 = var558;
            if (var558 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("replayBar");
               var348 = null;
            }

            DataClient var657 = this.this$0.data;
            var54 = var657;
            if (var657 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("data");
               var54 = null;
            }

            byte var151;
            if (var54.getConnected() && Replay.INSTANCE.getActive()) {
               var151 = 0;
            } else {
               var151 = 8;
            }

            var348.setVisibility(var151);
            TextView var560 = this.this$0.whereBtn;
            TextView var349 = var560;
            if (var560 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("whereBtn");
               var349 = null;
            }

            DataClient var658 = this.this$0.data;
            var54 = var658;
            if (var658 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("data");
               var54 = null;
            }

            if (var54.getConnected()
               && var58.navState() == null
               && !this.this$0.getMapOpen()
               && this.this$0.hudEditor == null
               && this.this$0.maskEditor == null) {
               var151 = (byte)0;
            } else {
               var151 = (byte)8;
            }

            var349.setVisibility(var151);
            TextView var562 = this.this$0.mapBtn;
            TextView var350 = var562;
            if (var562 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("mapBtn");
               var350 = null;
            }

            DataClient var659 = this.this$0.data;
            var54 = var659;
            if (var659 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("data");
               var54 = null;
            }

            if (!var54.getConnected()
               || this.this$0.getMapOpen()
               || this.this$0.hudEditor != null
               || this.this$0.maskEditor != null
               || !var58.getStarted() && this.this$0.homeShown) {
               var151 = (byte)8;
            } else {
               var151 = (byte)0;
            }

            var350.setVisibility(var151);
            if (this.this$0.tick % 40 == 0) {
               NudgeSync var660 = NudgeSync.INSTANCE;
               UiSettings var564 = this.this$0.settings;
               UiSettings var351 = var564;
               if (var564 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("settings");
                  var351 = null;
               }

               var660.tick(var351.getFloat("nudge_lc_pct", 100.0F));
            }

            BlinkerTurn var565 = BlinkerTurn.INSTANCE;
            UiSettings var352 = this.this$0.settings;
            if (var352 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("settings");
               var352 = null;
            }

            BlinkerTurn.tick$default(var565, var58, var352, 0L, 4, null);
            this.this$0.ideasTick(var58);
            DriveTrack.tick$default(DriveTrack.INSTANCE, var58, 0L, 2, null);
            if (this.this$0.sharedPending != null) {
               var54 = this.this$0.data;
               DataClient var353 = var54;
               if (var54 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("data");
                  var353 = null;
               }

               if (var353.getConnected() && this.this$0.tick % 20 == 0) {
                  this.this$0.useShared();
               }
            }

            if (this.this$0.tick % 20 == 0) {
               Ntrip.INSTANCE.tick((Context)this.this$0);
            }

            if (this.this$0.tick % 4 == 0) {
               var55 = this.this$0;
               var54 = this.this$0.data;
               DataClient var354 = var54;
               if (var54 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("data");
                  var354 = null;
               }

               if (!var354.getConnected()
                  || Nav.INSTANCE.getActive()
                  || var58.navState() != null
                  || this.this$0.hudEditor != null
                  || this.this$0.maskEditor != null
                  || !var58.getStarted() && this.this$0.homeShown) {
                  var28 = false;
               } else {
                  var28 = true;
               }

               var55.syncFavBar(var28);
            }

            TextView var568 = this.this$0.mapBtn;
            TextView var355 = var568;
            if (var568 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("mapBtn");
               var355 = null;
            }

            LayoutParams var356 = var355.getLayoutParams();
            Intrinsics.checkNotNull(var356, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
            var var662 = (android.widget.FrameLayout.LayoutParams & LayoutParams)var356;
            MainActivity var703 = this.this$0;
            TextView var569 = var703.whereBtn;
            TextView var357 = var569;
            if (var569 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("whereBtn");
               var357 = null;
            }

            if (var357.getVisibility() == 0) {
               var151 = (short)330;
            } else {
               var151 = (short)160;
            }

            var151 = var703.dp(var151);
            if (var662.getMarginStart() != var151) {
               var662.setMarginStart(var151);
               TextView var570 = var703.mapBtn;
               TextView var358 = var570;
               if (var570 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("mapBtn");
                  var358 = null;
               }

               var358.setLayoutParams((LayoutParams)var662);
            }

            Unit var359 = Unit.INSTANCE;
            var703 = this.this$0;
            var var360 = (Anon_uiTick_1)this;
            if (var58.getParams().get("NavLanePositioningAllowed") != null) {
               var28 = var58.pBool("NavLanePositioningAllowed", true);
            } else {
               var28 = true;
            }

            NudgeLogic.Result var361;
            if (var58.getStarted() && var58.getActive() && var28 && var58.s("nudge_hint")) {
               Double var362 = StringsKt.toDoubleOrNull(Telemetry.pStr$default(var58, "LaneDetectionWidth", null, 2, null));
               double var78;
               if (var362 != null) {
                  var78 = var362;
               } else {
                  var78 = 0.0;
               }

               NudgeLogic var363 = NudgeLogic.INSTANCE;
               JSONObject var571 = var58.navState();
               double var83 = var58.getVEgo();
               var31 = var58.getLeftBlinker();
               var29 = var58.getRightBlinker();
               boolean var230 = var58.getLeftBlindspot();
               var28 = var58.getRightBlindspot();
               double var7 = var58.getLaneWidthLeft();
               double var86 = var58.getLaneWidthRight();
               if (!var58.isMetric()) {
                  var78 = 0.3048 * var78;
               }

               var361 = var363.evaluate(var571, var83, var31, var29, var230, var28, var7, var86, var78);
            } else {
               var361 = new NudgeLogic.Result(NudgeLogic.State.NONE, false, 0.0, 0.0, null, 30, null);
            }

            NudgeHintView var663 = var703.nudgeHint;
            NudgeHintView var572 = var663;
            if (var663 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("nudgeHint");
               var572 = null;
            }

            var572.setResult(var361);
            NudgeHintView var664 = var703.nudgeHint;
            NudgeHintView var573 = var664;
            if (var664 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("nudgeHint");
               var573 = null;
            }

            var573.setMetric(var58.isMetric());
            NudgeHintView var665 = var703.nudgeHint;
            NudgeHintView var574 = var665;
            if (var665 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("nudgeHint");
               var574 = null;
            }

            byte var156;
            if (var361.getState() == NudgeLogic.State.NONE) {
               var156 = 8;
            } else {
               var156 = 0;
            }

            var574.setVisibility(var156);
            Unit var364 = Unit.INSTANCE;
            WeatherView var575 = this.this$0.weather;
            WeatherView var365 = var575;
            if (var575 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("weather");
               var365 = null;
            }

            if (var58.getStarted() && var58.s("weather") && var58.getWeatherId() > 0) {
               var156 = (byte)0;
            } else {
               var156 = (byte)8;
            }

            var365.setVisibility(var156);
            TextView var576 = this.this$0.compass;
            TextView var366 = var576;
            if (var576 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("compass");
               var366 = null;
            }

            CharSequence var577;
            if (var58.getStarted() && var58.s("compass") && !Float.isNaN(var58.getBearingDeg())) {
               var156 = (int)(((var58.getBearingDeg() + 22.5F) % 360.0F + 360.0F) % 360.0F / 45.0F);
               String var578 = new String[]{"N", "NE", "E", "SE", "S", "SW", "W", "NW"}[var156 % 8];
               var577 = "- " + var578 + " -";
            } else {
               var577 = "";
            }

            var366.setText(var577);
            TextView var579 = this.this$0.devMetrics;
            TextView var367 = var579;
            if (var579 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("devMetrics");
               var367 = null;
            }

            CharSequence var580;
            if (var58.getStarted() && var58.s("dev_metrics")) {
               int var200 = MathKt.roundToInt(var58.getCpuUsage());
               var156 = MathKt.roundToInt(var58.getCpuTempC());
               var20 = var58.getMemUsage();
               int var204 = MathKt.roundToInt(var58.getFreeSpacePct());
               var580 = "CPU " + var200 + "%  |  TEMP " + var156 + "°C  |  RAM " + var20 + "%  |  DISK " + var204 + "% free";
            } else {
               var580 = "";
            }

            var367.setText(var580);
            long var236 = System.nanoTime();
            if (var58.getStarted()) {
               if (this.this$0.onroadSinceNs == 0L) {
                  this.this$0.onroadSinceNs = var236;
               }
            } else {
               this.this$0.onroadSinceNs = 0L;
            }

            if (var58.getStarted() && var58.getStandstill()) {
               if (this.this$0.standstillSinceNs == 0L) {
                  this.this$0.standstillSinceNs = var236;
               }
            } else {
               this.this$0.standstillSinceNs = 0L;
            }

            if (this.this$0.standstillSinceNs != 0L) {
               var9 = (float)(var236 - this.this$0.standstillSinceNs) / 1.0E9F;
            } else {
               var9 = 0.0F;
            }

            if (this.this$0.onroadSinceNs != 0L) {
               var10 = (float)(var236 - this.this$0.onroadSinceNs) / 1.0E9F;
            } else {
               var10 = 0.0F;
            }

            if (var58.s("stopped_timer") && var9 >= 60.0F && var10 >= 60.0F) {
               var10 = 60;
               var156 = (int)(var9 / var10);
               var20 = (int)(var9 % var10);
               TextView var582 = this.this$0.speedTv;
               TextView var369 = var582;
               if (var582 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("speedTv");
                  var369 = null;
               }

               String var583 = String.format("%d:%02d", Arrays.copyOf(new Object[]{var156, var20}, 2));
               Intrinsics.checkNotNullExpressionValue(var583, "format(...)");
               var369.setText(var583);
               TextView var584 = this.this$0.speedUnit;
               TextView var370 = var584;
               if (var584 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("speedUnit");
                  var370 = null;
               }

               var370.setText("stopped");
               if (var9 < 150.0F) {
                  var156 = ColorUtils.blendARGB(Color.rgb(22, 127, 64), Color.rgb(218, 111, 37), (var9 - 60.0F) / 90.0F);
               } else if (var9 < 300.0F) {
                  var156 = ColorUtils.blendARGB(Color.rgb(218, 111, 37), Color.rgb(201, 34, 49), (var9 - 150.0F) / 150.0F);
               } else {
                  var156 = Color.rgb(201, 34, 49);
               }

               TextView var585 = this.this$0.speedTv;
               TextView var371 = var585;
               if (var585 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("speedTv");
                  var371 = null;
               }

               var371.setTextColor(var156);
            } else {
               TextView var581 = this.this$0.speedTv;
               TextView var368 = var581;
               if (var581 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("speedTv");
                  var368 = null;
               }

               if (this.this$0.night) {
                  var156 = -1;
               } else {
                  var156 = Color.rgb(29, 29, 31);
               }

               var368.setTextColor(var156);
            }

            label1694: {
               var28 = var58.s("alerts");
               if (var58.getStarted()) {
                  boolean var163;
                  if (var58.getAlertText1().length() > 0) {
                     var163 = true;
                  } else {
                     var163 = false;
                  }

                  if (var163 && !Intrinsics.areEqual(var58.getAlertSize(), "none") && !(var28 ^ true)) {
                     var164 = true;
                     break label1694;
                  }
               }

               var164 = false;
            }

            MainActivity var372 = this.this$0;
            var10 = this.this$0.alertAlpha;
            var9 = var11;
            if (var164) {
               var9 = 1.0F;
            }

            var372.alertAlpha = var10 + (var9 - this.this$0.alertAlpha) * 0.35F;
            if (this.this$0.alertAlpha > 0.02F) {
               LinearLayout var586 = this.this$0.alertBox;
               LinearLayout var373 = var586;
               if (var586 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("alertBox");
                  var373 = null;
               }

               var373.setVisibility(0);
               LinearLayout var587 = this.this$0.alertBox;
               LinearLayout var374 = var587;
               if (var587 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("alertBox");
                  var374 = null;
               }

               var374.setAlpha(this.this$0.alertAlpha);
               if (var164) {
                  TextView var588 = this.this$0.alert1;
                  TextView var375 = var588;
                  if (var588 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("alert1");
                     var375 = null;
                  }

                  var375.setText(var58.getAlertText1());
                  TextView var589 = this.this$0.alert2;
                  TextView var376 = var589;
                  if (var589 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("alert2");
                     var376 = null;
                  }

                  var376.setText(var58.getAlertText2());
                  TextView var590 = this.this$0.alert2;
                  TextView var377 = var590;
                  if (var590 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("alert2");
                     var377 = null;
                  }

                  boolean var165;
                  if (var58.getAlertText2().length() == 0) {
                     var165 = 1;
                  } else {
                     var165 = 0;
                  }

                  if (var165) {
                     var165 = 8;
                  } else {
                     var165 = 0;
                  }

                  var377.setVisibility(var165);
               }

               var28 = Intrinsics.areEqual(var58.getAlertSize(), "full");
               TextView var591 = this.this$0.alert1;
               TextView var378 = var591;
               if (var591 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("alert1");
                  var378 = null;
               }

               String var592 = var58.getAlertSize();
               if (Intrinsics.areEqual(var592, "full")) {
                  var9 = 52.0F;
               } else if (Intrinsics.areEqual(var592, "mid")) {
                  var9 = 36.0F;
               } else {
                  var9 = 26.0F;
               }

               var378.setTextSize(var9);
               TextView var593 = this.this$0.alert2;
               TextView var379 = var593;
               if (var593 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("alert2");
                  var379 = null;
               }

               if (var28) {
                  var9 = 24.0F;
               } else {
                  var9 = 20.0F;
               }

               var379.setTextSize(var9);
               LinearLayout var594 = this.this$0.alertBox;
               LinearLayout var380 = var594;
               if (var594 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("alertBox");
                  var380 = null;
               }

               LayoutParams var381 = var380.getLayoutParams();
               Intrinsics.checkNotNull(var381, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
               var var666 = (android.widget.FrameLayout.LayoutParams & LayoutParams)var381;
               byte var167;
               if (var28) {
                  var167 = -1;
               } else {
                  var167 = -2;
               }

               var666.height = var167;
               LinearLayout var595 = this.this$0.alertBox;
               LinearLayout var382 = var595;
               if (var595 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("alertBox");
                  var382 = null;
               }

               label1670: {
                  var382.setLayoutParams((LayoutParams)var666);
                  String var383 = var58.getAlertStatus();
                  switch (var383.hashCode()) {
                     case -2132360282:
                        if (var383.equals("starpilot")) {
                           var167 = Color.rgb(23, 134, 68);
                           break label1670;
                        }
                        break;
                     case -1874525969:
                        if (var383.equals("userprompt")) {
                           var167 = Color.rgb(255, 115, 0);
                           break label1670;
                        }
                        break;
                     case 1952151455:
                        if (var383.equals("critical")) {
                           var167 = Color.rgb(255, 0, 21);
                           break label1670;
                        }
                  }

                  var167 = -16777216;
               }

               boolean var193;
               if (var58.s("alert_tint") && !Intrinsics.areEqual(var58.getAlertStatus(), "normal")) {
                  var193 = 1;
               } else {
                  var193 = 0;
               }

               if (var193) {
                  View var596 = this.this$0.alertTint;
                  View var384 = var596;
                  if (var596 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("alertTint");
                     var384 = null;
                  }

                  var384.setVisibility(0);
                  View var597 = this.this$0.alertTint;
                  View var385 = var597;
                  if (var597 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("alertTint");
                     var385 = null;
                  }

                  var385.setAlpha(this.this$0.alertAlpha);
                  this.this$0.tintDrawable.setOrientation(Orientation.TOP_BOTTOM);
                  GradientDrawable var598 = this.this$0.tintDrawable;
                  int[] var386;
                  if (var28) {
                     var386 = new int[]{OverlayView.Companion.withAlpha(var167, 230), OverlayView.Companion.withAlpha(var167, 230)};
                  } else {
                     var386 = new int[]{
                        OverlayView.Companion.withAlpha(var167, 0), OverlayView.Companion.withAlpha(var167, 60), OverlayView.Companion.withAlpha(var167, 230)
                     };
                  }

                  var598.setColors(var386);
                  LinearLayout var599 = this.this$0.alertBox;
                  LinearLayout var387 = var599;
                  if (var599 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("alertBox");
                     var387 = null;
                  }

                  if (var28) {
                     var167 = 0;
                  } else {
                     var167 = OverlayView.Companion.withAlpha(var167, 120);
                  }

                  var387.setBackgroundColor(var167);
               } else {
                  View var600 = this.this$0.alertTint;
                  View var388 = var600;
                  if (var600 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("alertTint");
                     var388 = null;
                  }

                  var388.setVisibility(8);
                  LinearLayout var601 = this.this$0.alertBox;
                  LinearLayout var389 = var601;
                  if (var601 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("alertBox");
                     var389 = null;
                  }

                  OverlayView.Companion var602 = OverlayView.Companion;
                  if (var28) {
                     var193 = 230;
                  } else {
                     var193 = 200;
                  }

                  var389.setBackgroundColor(var602.withAlpha(var167, var193));
               }
            } else {
               LinearLayout var603 = this.this$0.alertBox;
               LinearLayout var390 = var603;
               if (var603 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("alertBox");
                  var390 = null;
               }

               var390.setVisibility(8);
               View var604 = this.this$0.alertTint;
               View var391 = var604;
               if (var604 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("alertTint");
                  var391 = null;
               }

               var391.setVisibility(8);
            }

            ModelSnapshot var392 = var58.getModel();
            if (var392 != null) {
               var236 = var392.getFrameId();
            } else {
               var236 = -1L;
            }

            if (var236 != this.this$0.lastModelFrame || this.this$0.tick % 1 == 0) {
               this.this$0.lastModelFrame = var236;
               OverlayView var605 = this.this$0.overlay;
               OverlayView var393 = var605;
               if (var605 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("overlay");
                  var393 = null;
               }

               var393.invalidate();
            }

            WheelButton var606 = this.this$0.wheel;
            WheelButton var394 = var606;
            if (var606 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("wheel");
               var394 = null;
            }

            var394.invalidate();
            DmView var607 = this.this$0.dm;
            DmView var395 = var607;
            if (var607 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("dm");
               var395 = null;
            }

            var395.invalidate();
            MiciDistanceView var608 = this.this$0.personality;
            MiciDistanceView var396 = var608;
            if (var608 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("personality");
               var396 = null;
            }

            var396.invalidate();
            SlcView var609 = this.this$0.slc;
            SlcView var397 = var609;
            if (var609 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("slc");
               var397 = null;
            }

            var397.invalidate();
            SlcActionsView var610 = this.this$0.slcActions;
            SlcActionsView var398 = var610;
            if (var610 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("slcActions");
               var398 = null;
            }

            var398.invalidate();
            DevSidebarView var611 = this.this$0.devSidebar;
            DevSidebarView var399 = var611;
            if (var611 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("devSidebar");
               var399 = null;
            }

            if (var399.getVisibility() == 0 && this.this$0.tick % 4 == 0) {
               DevSidebarView var612 = this.this$0.devSidebar;
               DevSidebarView var400 = var612;
               if (var612 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("devSidebar");
                  var400 = null;
               }

               var400.invalidate();
            }

            AetherGaugeView var613 = this.this$0.gauge;
            AetherGaugeView var401 = var613;
            if (var613 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("gauge");
               var401 = null;
            }

            var401.invalidate();
            NavCardView var614 = this.this$0.navCard;
            NavCardView var402 = var614;
            if (var614 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("navCard");
               var402 = null;
            }

            var402.invalidate();
            SlowReasonView var615 = this.this$0.slowCard;
            SlowReasonView var403 = var615;
            if (var615 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("slowCard");
               var403 = null;
            }

            var403.invalidate();
            LaneCardView var616 = this.this$0.laneCard;
            LaneCardView var404 = var616;
            if (var616 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("laneCard");
               var404 = null;
            }

            var404.invalidate();
            BlinkerCardView var617 = this.this$0.blinkCard;
            BlinkerCardView var405 = var617;
            if (var617 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("blinkCard");
               var405 = null;
            }

            var405.invalidate();
            GpsCardView var618 = this.this$0.gpsCard;
            GpsCardView var406 = var618;
            if (var618 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("gpsCard");
               var406 = null;
            }

            if (var406.getVisibility() == 0) {
               GpsCardView var619 = this.this$0.gpsCard;
               GpsCardView var407 = var619;
               if (var619 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("gpsCard");
                  var407 = null;
               }

               var407.invalidate();
            }

            NudgeHintView var620 = this.this$0.nudgeHint;
            NudgeHintView var408 = var620;
            if (var620 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("nudgeHint");
               var408 = null;
            }

            var408.invalidate();
            WeatherView var621 = this.this$0.weather;
            WeatherView var409 = var621;
            if (var621 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("weather");
               var409 = null;
            }

            var409.invalidate();
            SlcSourcesView var622 = this.this$0.slcSources;
            SlcSourcesView var410 = var622;
            if (var622 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("slcSources");
               var410 = null;
            }

            var410.invalidate();
            LinearLayout var623 = this.this$0.sidebar;
            LinearLayout var411 = var623;
            if (var623 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("sidebar");
               var411 = null;
            }

            if (var411.getVisibility() == 0 && this.this$0.tick % 5 == 0) {
               TextView var624 = this.this$0.findViewById(R.id.sbName);
               var243 = Telemetry.pStr$default(var58, "GalaxyDeviceName", null, 2, null);
               boolean var170;
               if (var243.length() == 0) {
                  var170 = 1;
               } else {
                  var170 = 0;
               }

               if (var170) {
                  var243 = var58.pStr("DongleId", "comma");
               }

               label1620: {
                  var624.setText((CharSequence)var243);
                  String var625 = StringsKt.repeat("●", RangesKt.coerceIn(var58.getNetworkStrength(), 0, 4));
                  String var413 = StringsKt.repeat("○", 4 - RangesKt.coerceIn(var58.getNetworkStrength(), 0, 4));
                  var667 = var625 + var413;
                  var626 = this.this$0.findViewById(R.id.sbNet);
                  String var414 = var58.getNetworkType();
                  switch (var414.hashCode()) {
                     case -1419358249:
                        if (var414.equals("ethernet")) {
                           var415 = "ETH";
                           break label1620;
                        }
                        break;
                     case -1364082857:
                        if (var414.equals("cell2g")) {
                           var415 = "2G";
                           break label1620;
                        }
                        break;
                     case -1364082826:
                        if (var414.equals("cell3g")) {
                           var415 = "3G";
                           break label1620;
                        }
                        break;
                     case -1364082795:
                        if (var414.equals("cell4g")) {
                           var415 = "LTE";
                           break label1620;
                        }
                        break;
                     case -1364082764:
                        if (var414.equals("cell5g")) {
                           var415 = "5G";
                           break label1620;
                        }
                        break;
                     case 3649301:
                        if (var414.equals("wifi")) {
                           var415 = "Wi-Fi";
                           break label1620;
                        }
                  }

                  var415 = "--";
               }

               var626.setText(var667 + "  " + var415);
               TextView var416 = this.this$0.findViewById(R.id.sbTempV);
               var170 = MathKt.roundToInt(var58.getMaxTempC());
               var416.setText(var170 + "°C");
               String var627 = var58.getThermalStatus();
               if (Intrinsics.areEqual(var627, "green")) {
                  var170 = -1;
               } else {
                  short var173;
                  short var195;
                  byte var201;
                  if (Intrinsics.areEqual(var627, "yellow")) {
                     var173 = 202;
                     var201 = 37;
                     var195 = 218;
                  } else {
                     var173 = 34;
                     var201 = 49;
                     var195 = 201;
                  }

                  var170 = Color.rgb(var195, var173, var201);
               }

               var416.setTextColor(var170);
               TextView var628 = this.this$0.findViewById(R.id.sbVehV);
               String var417;
               if (var58.getStarted()) {
                  var417 = "ONLINE";
               } else {
                  var417 = "OFFROAD";
               }

               var628.setText(var417);
               if (var58.getStarted()) {
                  var170 = -1;
               } else {
                  var170 = Color.rgb(166, 166, 166);
               }

               var628.setTextColor(var170);
               boolean var175;
               if (var58.getLastAthenaPing() > 0L && System.currentTimeMillis() / 1000 - var58.getLastAthenaPing() < 80L) {
                  var175 = 1;
               } else {
                  var175 = 0;
               }

               TextView var629 = this.this$0.findViewById(R.id.sbConnV);
               String var418;
               if (var175) {
                  var418 = "ONLINE";
               } else {
                  var418 = "OFFLINE";
               }

               var629.setText(var418);
               if (var175) {
                  var175 = -1;
               } else {
                  var175 = Color.rgb(201, 34, 49);
               }

               var629.setTextColor(var175);
               var29 = DriveState.INSTANCE.forcedOnroad();
               var28 = DriveState.INSTANCE.forcedOffroad();
               Button var419 = this.this$0.findViewById(R.id.dsDefault);
               if (!var29 && !var28) {
                  var9 = 1.0F;
               } else {
                  var9 = 0.5F;
               }

               var419.setAlpha(var9);
               Button var420 = this.this$0.findViewById(R.id.dsOnroad);
               if (var29) {
                  var9 = 1.0F;
               } else {
                  var9 = 0.5F;
               }

               var420.setAlpha(var9);
               Button var421 = this.this$0.findViewById(R.id.dsOffroad);
               if (var28) {
                  var9 = 1.0F;
               } else {
                  var9 = 0.5F;
               }

               var421.setAlpha(var9);
            }

            if (this.this$0.tick % 10 == 0) {
               var54 = this.this$0.data;
               DataClient var422 = var54;
               if (var54 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("data");
                  var422 = null;
               }

               var422.ping();
               if (this.this$0.statsVisible) {
                  Collection var423 = var58.getByteCount().values();
                  Intrinsics.checkNotNullExpressionValue(var423, "<get-values>(...)");
                  var236 = CollectionsKt.sumOfLong(var423) / 1024;
                  TextView var424 = this.this$0.statsTv;
                  TextView var631 = var424;
                  if (var424 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("statsTv");
                     var631 = null;
                  }

                  MainActivity var715 = this.this$0;
                  StringBuilder var714 = new StringBuilder();
                  String var668 = var715.linkIf;
                  String var425;
                  if (var58.getRttMs() >= 0.0) {
                     var425 = String.format("%.1f", Arrays.copyOf(new Object[]{var58.getRttMs()}, 1));
                     Intrinsics.checkNotNullExpressionValue(var425, "format(...)");
                  } else {
                     var425 = "–";
                  }

                  var714.append("link   " + var668 + "  rtt " + var425 + " ms  " + var236 + " kB\n");
                  var20 = MathKt.roundToInt(var58.getCpuUsage());
                  var156 = MathKt.roundToInt(var58.getCpuTempC());
                  String var669 = var58.getThermalStatus();
                  String var705 = var58.getDeviceType();
                  String var426 = var58.getSensor();
                  var714.append("comma  cpu " + var20 + "%  " + var156 + "°C  " + var669 + "  " + var705 + "/" + var426 + "\n");
                  VideoClient var670 = var715.video;
                  VideoClient var427 = var670;
                  if (var670 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("video");
                     var427 = null;
                  }

                  String var706 = String.format("%.1f", Arrays.copyOf(new Object[]{var427.getFps()}, 1));
                  Intrinsics.checkNotNullExpressionValue(var706, "format(...)");
                  VideoClient var671 = var715.video;
                  VideoClient var428 = var671;
                  if (var671 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("video");
                     var428 = null;
                  }

                  var156 = var428.getLastW();
                  VideoClient var672 = var715.video;
                  VideoClient var429 = var672;
                  if (var672 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("video");
                     var429 = null;
                  }

                  var20 = var429.getLastH();
                  VideoClient var673 = var715.video;
                  VideoClient var430 = var673;
                  if (var673 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("video");
                     var430 = null;
                  }

                  String var716 = String.format("%.1f", Arrays.copyOf(new Object[]{var430.getDecodeMs()}, 1));
                  Intrinsics.checkNotNullExpressionValue(var716, "format(...)");
                  VideoClient var674 = var715.video;
                  VideoClient var431 = var674;
                  if (var674 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("video");
                     var431 = null;
                  }

                  var236 = var431.getDropped();
                  var714.append("video  " + var706 + " fps  " + var156 + "x" + var20 + "  dec " + var716 + " ms  drop " + var236 + "\n");
                  Detector var717 = var715.detectorRef;
                  if (var717 != null && var717.isRunning()) {
                     String var720 = CollectionsKt.joinToString$default(
                        CollectionsKt.sortedWith(
                           GroupingKt.eachCount(new Grouping<Detector.Det, String>(var717.getDets()) {
                              final Iterable $this_groupingBy;

                              {
                                 this.$this_groupingBy = var1;
                              }

                              @Override
                              public String keyOf(Detector.Det var1) {
                                 return ((Detector.Det)var1).getName();
                              }

                              @Override
                              public Iterator<Detector.Det> sourceIterator() {
                                 return this.$this_groupingBy.iterator();
                              }
                           }).entrySet(),
                           new Comparator() {
                              @Override
                              public final int compare(T var1, T var2) {
                                 return ComparisonsKt.compareValues(
                                    (T)((Integer & Comparable)((Entry)var2).getValue()), (T)((Integer & Comparable)((Entry)var1).getValue())
                                 );
                              }
                           }
                        ),
                        " ",
                        null,
                        null,
                        0,
                        null,
                        new MainActivity$uiTick$1$$ExternalSyntheticLambda1(),
                        30,
                        null
                     );
                     String var64 = var717.getStatus();
                     String var68 = var717.getAccel();
                     String var66 = String.format("%.0f", Arrays.copyOf(new Object[]{var717.getInferMs()}, 1));
                     Intrinsics.checkNotNullExpressionValue(var66, "format(...)");
                     String var67 = String.format("%.0f", Arrays.copyOf(new Object[]{var717.getPrepMs()}, 1));
                     Intrinsics.checkNotNullExpressionValue(var67, "format(...)");
                     String var65 = String.format("%.1f", Arrays.copyOf(new Object[]{var717.getFps()}, 1));
                     Intrinsics.checkNotNullExpressionValue(var65, "format(...)");
                     String var725 = String.format("%.0f", Arrays.copyOf(new Object[]{var717.getRoadFps()}, 1));
                     Intrinsics.checkNotNullExpressionValue(var725, "format(...)");
                     String var722 = String.format("%.0f", Arrays.copyOf(new Object[]{var717.getWideFps()}, 1));
                     Intrinsics.checkNotNullExpressionValue(var722, "format(...)");
                     VideoClient var675 = var715.detRoad;
                     VideoClient var432 = var675;
                     if (var675 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("detRoad");
                        var432 = null;
                     }

                     String var433;
                     if (var432.isRunning()) {
                        var433 = "road ";
                     } else {
                        var433 = "";
                     }

                     VideoClient var707 = var715.detWide;
                     VideoClient var676 = var707;
                     if (var707 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("detWide");
                        var676 = null;
                     }

                     String var677;
                     if (var676.isRunning()) {
                        var677 = "wide";
                     } else {
                        var677 = "";
                     }

                     var714.append(
                        "detect "
                           + var64
                           + "  "
                           + var68
                           + "  model "
                           + var66
                           + " ms  prep "
                           + var67
                           + " ms  "
                           + var65
                           + " /s (road "
                           + var725
                           + " wide "
                           + var722
                           + ")  cams "
                           + var433
                           + var677
                           + "\n"
                     );
                     if (var717.getLaneMarks() != null) {
                        String var434 = var715.laneMarks.getDebug();
                        var714.append("lines  " + var434 + "\n");
                     }

                     LayoutSeg var678 = var715.layoutSeg;
                     LayoutSeg var435 = var678;
                     if (var678 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("layoutSeg");
                        var435 = null;
                     }

                     String var443;
                     if (var435.getEnabled()) {
                        LayoutSeg var679 = var715.layoutSeg;
                        LayoutSeg var436 = var679;
                        if (var679 == null) {
                           Intrinsics.throwUninitializedPropertyAccessException("layoutSeg");
                           var436 = null;
                        }

                        String var708 = var436.getStatus();
                        LayoutSeg var680 = var715.layoutSeg;
                        LayoutSeg var437 = var680;
                        if (var680 == null) {
                           Intrinsics.throwUninitializedPropertyAccessException("layoutSeg");
                           var437 = null;
                        }

                        var722 = var437.getAccel();
                        LayoutSeg var681 = var715.layoutSeg;
                        LayoutSeg var438 = var681;
                        if (var681 == null) {
                           Intrinsics.throwUninitializedPropertyAccessException("layoutSeg");
                           var438 = null;
                        }

                        var725 = String.format("%.0f", Arrays.copyOf(new Object[]{var438.getMs()}, 1));
                        Intrinsics.checkNotNullExpressionValue(var725, "format(...)");
                        LayoutSeg var682 = var715.layoutSeg;
                        LayoutSeg var439 = var682;
                        if (var682 == null) {
                           Intrinsics.throwUninitializedPropertyAccessException("layoutSeg");
                           var439 = null;
                        }

                        boolean var179;
                        if (var439.getGpuError().length() > 0) {
                           var179 = true;
                        } else {
                           var179 = false;
                        }

                        String var442;
                        if (var179) {
                           LayoutSeg var683 = var715.layoutSeg;
                           LayoutSeg var440 = var683;
                           if (var683 == null) {
                              Intrinsics.throwUninitializedPropertyAccessException("layoutSeg");
                              var440 = null;
                           }

                           String var441 = var440.getGpuError();
                           var442 = "  gpu error: " + var441;
                        } else {
                           var442 = "";
                        }

                        var443 = var708 + " " + var722 + " " + var725 + " ms" + var442;
                     } else {
                        LayoutSeg var684 = var715.layoutSeg;
                        LayoutSeg var444 = var684;
                        if (var684 == null) {
                           Intrinsics.throwUninitializedPropertyAccessException("layoutSeg");
                           var444 = null;
                        }

                        if (!var444.getHasModel()) {
                           var443 = "no model";
                        } else {
                           var443 = "off";
                        }
                     }

                     var714.append("layout " + var443 + "\n");
                     boolean var180;
                     if (var717.getOrientError().length() > 0) {
                        var180 = true;
                     } else {
                        var180 = false;
                     }

                     String var446;
                     if (var180) {
                        String var445 = StringsKt.take(var717.getOrientError(), 60);
                        var446 = "ERROR " + var445;
                     } else {
                        var446 = String.format("%.1f ms/car", Arrays.copyOf(new Object[]{var717.getOrientMs()}, 1));
                        Intrinsics.checkNotNullExpressionValue(var446, "format(...)");
                     }

                     var714.append("orient " + var446 + "\n");
                     double var79 = var717.getLoadMs();
                     double var84 = 1000;
                     String var709 = String.format("%.0f", Arrays.copyOf(new Object[]{var79 / var84}, 1));
                     Intrinsics.checkNotNullExpressionValue(var709, "format(...)");
                     var722 = String.format("%.1f", Arrays.copyOf(new Object[]{var717.getFirstRunMs() / var84}, 1));
                     Intrinsics.checkNotNullExpressionValue(var722, "format(...)");
                     var243 = var720;
                     if (var243.length() == 0) {
                        var180 = true;
                     } else {
                        var180 = false;
                     }

                     if (var180) {
                        var243 = "nothing";
                     }

                     if (var717.getGpuError().length() > 0) {
                        var180 = true;
                     } else {
                        var180 = false;
                     }

                     String var686;
                     if (var180) {
                        String var685 = StringsKt.take(var717.getGpuError(), 90);
                        var686 = "   gpu: " + var685;
                     } else {
                        var686 = "";
                     }

                     var714.append("  load " + var709 + " s  first run " + var722 + " s  found: " + var243 + var686 + "\n");
                  }

                  boolean var183;
                  if (var715.lastCrash.length() > 0) {
                     var183 = 1;
                  } else {
                     var183 = 0;
                  }

                  if (var183) {
                     String var448 = StringsKt.take(StringsKt.replace$default(var715.lastCrash, "\n", " | ", false, 4, null), 300);
                     var714.append("LAST CRASH  " + var448 + "\n");
                  }

                  SceneView var449 = var715.scene;
                  if (var449 != null) {
                     var20 = var449.getShownCars();
                     if (var449.getFailure().length() > 0) {
                        var183 = (boolean)1;
                     } else {
                        var183 = (boolean)0;
                     }

                     String var451;
                     if (var183) {
                        String var450 = StringsKt.take(var449.getFailure(), 90);
                        var451 = "ERROR " + var450;
                     } else {
                        var451 = "ok";
                     }

                     var714.append("scene  cars " + var20 + "  " + var451 + "\n");
                  }

                  UiSettings var687 = var715.settings;
                  UiSettings var452 = var687;
                  if (var687 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("settings");
                     var452 = null;
                  }

                  if (var452.get("map_roads")) {
                     label1576: {
                        var710 = OsmRoads.INSTANCE.getTileStatus();
                        SceneView var453 = var715.scene;
                        if (var453 != null) {
                           String var688 = var453.getMapStatus();
                           var454 = var688;
                           if (var688 != null) {
                              break label1576;
                           }
                        }

                        var454 = "";
                     }

                     var714.append("map    " + var710 + "  " + var454 + "\n");
                  }

                  String var455;
                  if (var58.getCalValid()) {
                     var455 = "ok";
                  } else {
                     var455 = "NO";
                  }

                  var716 = String.format("%.3f %.3f %.3f", Arrays.copyOf(new Object[]{var58.getCalRpy()[0], var58.getCalRpy()[1], var58.getCalRpy()[2]}, 3));
                  Intrinsics.checkNotNullExpressionValue(var716, "format(...)");
                  String var721 = String.format("%.2f", Arrays.copyOf(new Object[]{var58.getCalHeight()}, 1));
                  Intrinsics.checkNotNullExpressionValue(var721, "format(...)");
                  OverlayView var711 = var715.overlay;
                  OverlayView var689 = var711;
                  if (var711 == null) {
                     Intrinsics.throwUninitializedPropertyAccessException("overlay");
                     var689 = null;
                  }

                  String var690 = String.format("%.2f", Arrays.copyOf(new Object[]{var689.getProj().getZoom()}, 1));
                  Intrinsics.checkNotNullExpressionValue(var690, "format(...)");
                  var714.append("calib  " + var455 + "  rpy " + var716 + "  h " + var721 + "  zoom " + var690 + "\n");
                  Long var456 = var58.getMsgCount().get("modelV2");
                  if (var456 != null) {
                     var236 = var456;
                  } else {
                     var236 = 0L;
                  }

                  Long var457 = var58.getMsgCount().get("carState");
                  long var241;
                  if (var457 != null) {
                     var241 = var457;
                  } else {
                     var241 = 0L;
                  }

                  var20 = var58.getToggles().size();
                  var183 = var58.getParams().size();
                  var714.append("model  " + var236 + "  cs " + var241 + "  toggles " + var20 + "  params " + var183 + "\n");
                  String var458;
                  if (var58.getLeadOne().getStatus()) {
                     var458 = String.format("%.1f m  %.1f m/s", Arrays.copyOf(new Object[]{var58.getLeadOne().getDRel(), var58.getLeadOne().getVRel()}, 2));
                     Intrinsics.checkNotNullExpressionValue(var458, "format(...)");
                  } else {
                     var458 = "none";
                  }

                  String var691 = String.format("%.1f", Arrays.copyOf(new Object[]{var58.getSteeringAngleDeg()}, 1));
                  Intrinsics.checkNotNullExpressionValue(var691, "format(...)");
                  String var712 = String.format("%.2f", Arrays.copyOf(new Object[]{var58.getOutTorque()}, 1));
                  Intrinsics.checkNotNullExpressionValue(var712, "format(...)");
                  var714.append("lead   " + var458 + "  steer " + var691 + "°  torque " + var712);
                  Unit var459 = Unit.INSTANCE;
                  String var460 = var714.toString();
                  Intrinsics.checkNotNullExpressionValue(var460, "toString(...)");
                  var631.setText(var460);
               }
            }

            this.this$0.applyHud();
            GpsBridge.INSTANCE.fallback(Telemetry.pStr$default(this.this$0.telemetry, "LastGPSPosition", null, 2, null), this.this$0.telemetry.getVEgo());
            this.this$0.ui.postDelayed(this, 50L);
         }
         }
}
