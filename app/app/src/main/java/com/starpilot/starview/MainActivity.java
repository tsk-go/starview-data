package com.starpilot.starview;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import com.starpilot.starview.AmySynth;
import com.starpilot.starview.Bookmarks;
import com.starpilot.starview.Discovery;
import com.starpilot.starview.HudLayout;
import com.starpilot.starview.LaneAhead;
import com.starpilot.starview.LaneBrain;
import com.starpilot.starview.LaneCardView;
import com.starpilot.starview.LaneModel;
import com.starpilot.starview.LaneShots;
import com.starpilot.starview.Link;
import com.starpilot.starview.MapPane;
import com.starpilot.starview.Nav;
import com.starpilot.starview.OffroadHome;
import com.starpilot.starview.QuickPanel;
import com.starpilot.starview.SharedPlace;
import com.starpilot.starview.SlowReason;
import com.starpilot.starview.SlowReasonView;
import com.starpilot.starview.VoiceCommands;
import java.io.File;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
import kotlin.collections.MapsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: MainActivity.kt */
public final class MainActivity extends AppCompatActivity {
    public static final Companion Companion = new Companion(null);
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

    /* renamed from: floating$delegate, reason: from kotlin metadata */
    private final Lazy floating;
    private int frameH;
    private int frameW;
    private AetherGaugeView gauge;
    private GpsCardView gpsCard;
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
    private LaneCardView laneCard;
    private LaneMapLog laneMapLog;
    private LaneSuggestView laneSuggest;
    private boolean laneWasStarted;
    private String lastCrash;
    private String lastHomeKey;
    private boolean lastHudEditing;
    private String lastNavDest;
    private boolean lastThemeApplied;
    private long lastThemeCheck;
    private float lastWs;
    private LayoutSeg layoutSeg;
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
    private long standstillSinceNs;
    private TextView statsTv;
    private boolean statsVisible;
    private TextView statusTv;

    /* renamed from: storageHttp$delegate, reason: from kotlin metadata */
    private final Lazy storageHttp;
    private boolean surfaceReady;
    private Function0<Unit> tabletModePusher;
    private int tick;
    private final MainActivity$uiTick$1 uiTick;
    private boolean usbProbe;
    private VideoClient video;
    private boolean videoConnected;
    private SurfaceView videoView;
    private volatile boolean visible;
    private final ActivityResultLauncher<Intent> voiceLauncher;
    private WeatherView weather;
    private WheelButton wheel;
    private TextView whereBtn;
    private final Handler ui = new Handler(Looper.getMainLooper());
    private final Telemetry telemetry = new Telemetry();
    private final LaneMarks laneMarks = new LaneMarks();

    /* renamed from: stallBanner$delegate, reason: from kotlin metadata */
    private final Lazy stallBanner = LazyKt.lazy(new Function0() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda112
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            TextView stallBanner_delegate$lambda$4;
            stallBanner_delegate$lambda$4 = MainActivity.stallBanner_delegate$lambda$4(MainActivity.this);
            return stallBanner_delegate$lambda$4;
        }
    });

    /* renamed from: gpuBanner$delegate, reason: from kotlin metadata */
    private final Lazy gpuBanner = LazyKt.lazy(new Function0() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda113
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            TextView gpuBanner_delegate$lambda$8;
            gpuBanner_delegate$lambda$8 = MainActivity.gpuBanner_delegate$lambda$8(MainActivity.this);
            return gpuBanner_delegate$lambda$8;
        }
    });
    private final CarMemory carMemory = new CarMemory();

    /* renamed from: laneShots$delegate, reason: from kotlin metadata */
    private final Lazy laneShots = LazyKt.lazy(new Function0() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda114
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            LaneShots laneShots_delegate$lambda$9;
            laneShots_delegate$lambda$9 = MainActivity.laneShots_delegate$lambda$9(MainActivity.this);
            return laneShots_delegate$lambda$9;
        }
    });

    /* renamed from: laneModel$delegate, reason: from kotlin metadata */
    private final Lazy laneModel = LazyKt.lazy(new Function0() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda115
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            LaneModel laneModel_delegate$lambda$10;
            laneModel_delegate$lambda$10 = MainActivity.laneModel_delegate$lambda$10(MainActivity.this);
            return laneModel_delegate$lambda$10;
        }
    });
    private final LaneBrain laneTracker = new LaneBrain();
    private final LaneFeed laneFeed = new LaneFeed();
    private final LaneAhead laneAhead = new LaneAhead();
    private final GradientDrawable tintDrawable = new GradientDrawable();
    private int port = 8090;
    private String linkIf = "";
    private String lastStatus = "";
    private long lastModelFrame = -1;

    /* compiled from: MainActivity.kt */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[VoiceCommands.Kind.values().length];
            try {
                iArr[VoiceCommands.Kind.EXP_ON.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[VoiceCommands.Kind.EXP_OFF.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[VoiceCommands.Kind.MAP_OPEN.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[VoiceCommands.Kind.MAP_CLOSE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr[VoiceCommands.Kind.FOCUS_ON.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                iArr[VoiceCommands.Kind.FOCUS_OFF.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                iArr[VoiceCommands.Kind.VOICE_ON.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                iArr[VoiceCommands.Kind.VOICE_OFF.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                iArr[VoiceCommands.Kind.SAVER_ON.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                iArr[VoiceCommands.Kind.SAVER_OFF.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                iArr[VoiceCommands.Kind.KEEP.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                iArr[VoiceCommands.Kind.FOLLOW.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
            try {
                iArr[VoiceCommands.Kind.FARTHER.ordinal()] = 13;
            } catch (NoSuchFieldError e13) {
            }
            try {
                iArr[VoiceCommands.Kind.CLOSER.ordinal()] = 14;
            } catch (NoSuchFieldError e14) {
            }
            try {
                iArr[VoiceCommands.Kind.SCENE.ordinal()] = 15;
            } catch (NoSuchFieldError e15) {
            }
            try {
                iArr[VoiceCommands.Kind.CAMERA.ordinal()] = 16;
            } catch (NoSuchFieldError e16) {
            }
            try {
                iArr[VoiceCommands.Kind.NAVIGATE.ordinal()] = 17;
            } catch (NoSuchFieldError e17) {
            }
            try {
                iArr[VoiceCommands.Kind.NUDGE.ordinal()] = 18;
            } catch (NoSuchFieldError e18) {
            }
            try {
                iArr[VoiceCommands.Kind.BOOKMARK.ordinal()] = 19;
            } catch (NoSuchFieldError e19) {
            }
            try {
                iArr[VoiceCommands.Kind.UNKNOWN.ordinal()] = 20;
            } catch (NoSuchFieldError e20) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* compiled from: MainActivity.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public MainActivity() {
        ActivityResultLauncher<Intent> registerForActivityResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda116
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                MainActivity.destLauncher$lambda$12(MainActivity.this, (ActivityResult) obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(registerForActivityResult, "registerForActivityResult(...)");
        this.destLauncher = registerForActivityResult;
        this.lastCrash = "";
        ActivityResultLauncher<String[]> registerForActivityResult2 = registerForActivityResult(new ActivityResultContracts.OpenDocument(), new ActivityResultCallback() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda1
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                MainActivity.pickBackup$lambda$24(MainActivity.this, (Uri) obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(registerForActivityResult2, "registerForActivityResult(...)");
        this.pickBackup = registerForActivityResult2;
        this.backupArmed = true;
        this.visible = true;
        this.storageHttp = LazyKt.lazy(new Function0() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                OkHttpClient storageHttp_delegate$lambda$162;
                storageHttp_delegate$lambda$162 = MainActivity.storageHttp_delegate$lambda$162();
                return storageHttp_delegate$lambda$162;
            }
        });
        this.lastWs = -1.0f;
        this.floating = LazyKt.lazy(new Function0() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                List floating_delegate$lambda$172;
                floating_delegate$lambda$172 = MainActivity.floating_delegate$lambda$172(MainActivity.this);
                return floating_delegate$lambda$172;
            }
        });
        this.favKey = "";
        ActivityResultLauncher<Intent> registerForActivityResult3 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda4
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                MainActivity.voiceLauncher$lambda$192(MainActivity.this, (ActivityResult) obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(registerForActivityResult3, "registerForActivityResult(...)");
        this.voiceLauncher = registerForActivityResult3;
        this.lastNavDest = "";
        this.lastHomeKey = "";
        this.uiTick = new MainActivity$uiTick$1(this);
        this.hudSettingKeys = MapsKt.mapOf(TuplesKt.to("speedBox", "show_speed"), TuplesKt.to("maxCard", "show_max"), TuplesKt.to("slc", "speed_limit"), TuplesKt.to("wheel", "wheel_button"), TuplesKt.to("egpu", "egpu_status"), TuplesKt.to("personality", "personality_button"), TuplesKt.to("dm", "dm_icon"), TuplesKt.to("roadName", "road_name"), TuplesKt.to("gauge", "aether_gauge"), TuplesKt.to("navCard", "nav_card"), TuplesKt.to("slowCard", "slow_card"), TuplesKt.to("laneCard", "lane_card"), TuplesKt.to("blinkCard", "blink_card"), TuplesKt.to("gpsCard", "gps_card"), TuplesKt.to("nudgeHint", "nudge_hint"), TuplesKt.to("weather", "weather"), TuplesKt.to("compass", "compass"), TuplesKt.to("devMetrics", "dev_metrics"), TuplesKt.to("clock", "clock"), TuplesKt.to("modeText", "mode_text"), TuplesKt.to("devSidebar", "dev_sidebar"), TuplesKt.to("pipLeft", "side_views"), TuplesKt.to("pipRight", "side_views"));
        Paint $this$greyPaint_u24lambda_u24228 = new Paint();
        ColorMatrix $this$greyPaint_u24lambda_u24228_u24lambda_u24227 = new ColorMatrix();
        $this$greyPaint_u24lambda_u24228_u24lambda_u24227.setSaturation(0.0f);
        $this$greyPaint_u24lambda_u24228.setColorFilter(new ColorMatrixColorFilter($this$greyPaint_u24lambda_u24228_u24lambda_u24227));
        this.greyPaint = $this$greyPaint_u24lambda_u24228;
    }

    private final Detector getDetector() {
        Detector it = this.detectorRef;
        if (it == null) {
            Context applicationContext = getApplicationContext();
            Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
            it = new Detector(applicationContext);
            this.detectorRef = it;
            OverlayView overlayView = this.overlay;
            if (overlayView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("overlay");
                overlayView = null;
            }
            overlayView.setDetector(it);
            it.setTelemetry(this.telemetry);
        }
        return it;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final TextView getStallBanner() {
        return (TextView) this.stallBanner.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final TextView stallBanner_delegate$lambda$4(MainActivity this$0) {
        TextView $this$stallBanner_delegate_u24lambda_u244_u24lambda_u243 = new TextView(this$0);
        $this$stallBanner_delegate_u24lambda_u244_u24lambda_u243.setTextColor(-1);
        $this$stallBanner_delegate_u24lambda_u244_u24lambda_u243.setTextSize(17.0f);
        $this$stallBanner_delegate_u24lambda_u244_u24lambda_u243.setGravity(17);
        $this$stallBanner_delegate_u24lambda_u244_u24lambda_u243.setPadding(36, 20, 36, 20);
        $this$stallBanner_delegate_u24lambda_u244_u24lambda_u243.setVisibility(8);
        GradientDrawable $this$stallBanner_delegate_u24lambda_u244_u24lambda_u243_u24lambda_u241 = new GradientDrawable();
        $this$stallBanner_delegate_u24lambda_u244_u24lambda_u243_u24lambda_u241.setCornerRadius(28.0f);
        $this$stallBanner_delegate_u24lambda_u244_u24lambda_u243_u24lambda_u241.setColor(Color.rgb(179, 38, 30));
        $this$stallBanner_delegate_u24lambda_u244_u24lambda_u243.setBackground($this$stallBanner_delegate_u24lambda_u244_u24lambda_u243_u24lambda_u241);
        $this$stallBanner_delegate_u24lambda_u244_u24lambda_u243.setElevation(20.0f);
        FrameLayout frameLayout = this$0.root;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout = null;
        }
        FrameLayout.LayoutParams $this$stallBanner_delegate_u24lambda_u244_u24lambda_u243_u24lambda_u242 = new FrameLayout.LayoutParams(-2, -2, 49);
        $this$stallBanner_delegate_u24lambda_u244_u24lambda_u243_u24lambda_u242.topMargin = 170;
        Unit unit = Unit.INSTANCE;
        frameLayout.addView($this$stallBanner_delegate_u24lambda_u244_u24lambda_u243, $this$stallBanner_delegate_u24lambda_u244_u24lambda_u243_u24lambda_u242);
        return $this$stallBanner_delegate_u24lambda_u244_u24lambda_u243;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final TextView getGpuBanner() {
        return (TextView) this.gpuBanner.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final TextView gpuBanner_delegate$lambda$8(MainActivity this$0) {
        TextView $this$gpuBanner_delegate_u24lambda_u248_u24lambda_u247 = new TextView(this$0);
        $this$gpuBanner_delegate_u24lambda_u248_u24lambda_u247.setTextColor(-1);
        $this$gpuBanner_delegate_u24lambda_u248_u24lambda_u247.setTextSize(16.0f);
        $this$gpuBanner_delegate_u24lambda_u248_u24lambda_u247.setGravity(17);
        $this$gpuBanner_delegate_u24lambda_u248_u24lambda_u247.setPadding(32, 16, 32, 16);
        $this$gpuBanner_delegate_u24lambda_u248_u24lambda_u247.setVisibility(8);
        GradientDrawable $this$gpuBanner_delegate_u24lambda_u248_u24lambda_u247_u24lambda_u245 = new GradientDrawable();
        $this$gpuBanner_delegate_u24lambda_u248_u24lambda_u247_u24lambda_u245.setCornerRadius(28.0f);
        $this$gpuBanner_delegate_u24lambda_u248_u24lambda_u247_u24lambda_u245.setColor(Color.argb(220, 48, 52, 60));
        $this$gpuBanner_delegate_u24lambda_u248_u24lambda_u247.setBackground($this$gpuBanner_delegate_u24lambda_u248_u24lambda_u247_u24lambda_u245);
        $this$gpuBanner_delegate_u24lambda_u248_u24lambda_u247.setElevation(20.0f);
        $this$gpuBanner_delegate_u24lambda_u248_u24lambda_u247.setText("Big model loading on the GPU - camera video back when it's ready");
        FrameLayout frameLayout = this$0.root;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout = null;
        }
        FrameLayout.LayoutParams $this$gpuBanner_delegate_u24lambda_u248_u24lambda_u247_u24lambda_u246 = new FrameLayout.LayoutParams(-2, -2, 49);
        $this$gpuBanner_delegate_u24lambda_u248_u24lambda_u247_u24lambda_u246.topMargin = 170;
        Unit unit = Unit.INSTANCE;
        frameLayout.addView($this$gpuBanner_delegate_u24lambda_u248_u24lambda_u247, $this$gpuBanner_delegate_u24lambda_u248_u24lambda_u247_u24lambda_u246);
        return $this$gpuBanner_delegate_u24lambda_u248_u24lambda_u247;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final LaneShots getLaneShots() {
        return (LaneShots) this.laneShots.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final LaneShots laneShots_delegate$lambda$9(MainActivity this$0) {
        Context applicationContext = this$0.getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        return new LaneShots(applicationContext);
    }

    private final LaneModel getLaneModel() {
        return (LaneModel) this.laneModel.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final LaneModel laneModel_delegate$lambda$10(MainActivity this$0) {
        Context applicationContext = this$0.getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        return new LaneModel(applicationContext);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void destLauncher$lambda$12(MainActivity this$0, ActivityResult r) {
        Intent it;
        if (r.getResultCode() == -1 && (it = r.getData()) != null) {
            this$0.navigateTo(it);
        }
    }

    private final void installCrashLog() {
        final String ver;
        String str;
        final File f = new File(getFilesDir(), "last_crash.txt");
        if (f.exists()) {
            try {
                str = StringsKt.take(FilesKt.readText$default(f, null, 1, null), 600);
            } catch (Exception e) {
                str = "";
            }
            this.lastCrash = str;
            f.delete();
        }
        try {
            ver = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (Exception e2) {
            ver = "?";
        }
        final Thread.UncaughtExceptionHandler prev = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda28
            @Override // java.lang.Thread.UncaughtExceptionHandler
            public final void uncaughtException(Thread thread, Throwable th) {
                MainActivity.installCrashLog$lambda$13(f, ver, prev, thread, th);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void installCrashLog$lambda$13(File $f, String $ver, Thread.UncaughtExceptionHandler $prev, Thread th, Throwable e) {
        try {
            FilesKt.writeText$default($f, new Date() + " " + $ver + " [" + th.getName() + "]\n" + Log.getStackTraceString(e), null, 2, null);
        } catch (Throwable th2) {
        }
        if ($prev != null) {
            $prev.uncaughtException(th, e);
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    protected void onNewIntent(Intent intent) {
        Intrinsics.checkNotNullParameter(intent, "intent");
        super.onNewIntent(intent);
        setIntent(intent);
        if (intent.getBooleanExtra("open_nav", false)) {
            intent.removeExtra("open_nav");
            getWindow().getDecorView().postDelayed(new Runnable() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda103
                @Override // java.lang.Runnable
                public final void run() {
                    MainActivity.this.openMap();
                }
            }, 300L);
        }
        maskIntent(intent);
        shareIntent(intent);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0032 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0033  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void shareIntent(Intent i) {
        final String text;
        if (i == null) {
            return;
        }
        String action = i.getAction();
        if (action != null) {
            switch (action.hashCode()) {
                case -1173264947:
                    if (action.equals("android.intent.action.SEND")) {
                        text = i.getStringExtra("android.intent.extra.TEXT");
                        break;
                    }
                    break;
                case -1173171990:
                    if (action.equals("android.intent.action.VIEW")) {
                        text = i.getDataString();
                        break;
                    }
                    break;
            }
            if (text != null) {
                return;
            }
            i.setAction(null);
            final String subject = i.getStringExtra("android.intent.extra.SUBJECT");
            if (subject == null) {
                subject = "";
            }
            new Thread(new Runnable() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda99
                @Override // java.lang.Runnable
                public final void run() {
                    MainActivity.shareIntent$lambda$18(text, subject, this);
                }
            }).start();
            return;
        }
        text = null;
        if (text != null) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [T, com.starpilot.starview.SharedPlace$Place] */
    /* JADX WARN: Type inference failed for: r3v2, types: [T, com.starpilot.starview.SharedPlace$Place] */
    public static final void shareIntent$lambda$18(String $text, String $subject, final MainActivity this$0) {
        String full;
        String link = SharedPlace.INSTANCE.link($text);
        if (link == null) {
            link = StringsKt.startsWith$default($text, "geo:", false, 2, (Object) null) ? $text : null;
        }
        final Ref.ObjectRef place = new Ref.ObjectRef();
        place.element = SharedPlace.parse$default(SharedPlace.INSTANCE, (!($subject.length() > 0) || StringsKt.contains$default((CharSequence) $text, (CharSequence) $subject, false, 2, (Object) null)) ? $text : $subject + "\n" + $text, null, 2, null);
        if (!((SharedPlace.Place) place.element).getHasCoords() && link != null && SharedPlace.INSTANCE.isShort(link) && (full = SharedPlace.INSTANCE.follow(link)) != null) {
            place.element = SharedPlace.INSTANCE.parse($text, full);
        }
        this$0.runOnUiThread(new Runnable() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda97
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.shareIntent$lambda$18$lambda$17(MainActivity.this, place);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final void shareIntent$lambda$18$lambda$17(MainActivity this$0, Ref.ObjectRef $place) {
        this$0.sharedPending = (SharedPlace.Place) $place.element;
        this$0.useShared();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void useShared() {
        SharedPlace.Place p = this.sharedPending;
        if (p == null) {
            return;
        }
        DataClient dataClient = this.data;
        if (dataClient == null) {
            Intrinsics.throwUninitializedPropertyAccessException("data");
            dataClient = null;
        }
        if (!dataClient.getConnected()) {
            Toast.makeText(this, p.getName() + ": the route shows once the comma is connected", 1).show();
            return;
        }
        this.sharedPending = null;
        if (!p.getHasCoords()) {
            if (p.getQuery().length() > 0) {
                ActivityResultLauncher<Intent> activityResultLauncher = this.destLauncher;
                Intent $this$useShared_u24lambda_u2419 = new Intent(this, (Class<?>) DestinationActivity.class).putExtra("query", p.getQuery());
                double gpsLat = this.telemetry.getGpsLat();
                if ((Double.isInfinite(gpsLat) || Double.isNaN(gpsLat)) ? false : true) {
                    $this$useShared_u24lambda_u2419.putExtra("lat", this.telemetry.getGpsLat()).putExtra("lon", this.telemetry.getGpsLon());
                }
                activityResultLauncher.launch($this$useShared_u24lambda_u2419);
                return;
            }
            Toast.makeText(this, "Couldn't find a place in what was shared", 1).show();
            return;
        }
        driveTo(p.getName(), p.getAddress(), p.getLat(), p.getLon());
    }

    private final void maskIntent(Intent i) {
        boolean z = false;
        if (i != null && i.getBooleanExtra("edit_masks", false)) {
            z = true;
        }
        if (z) {
            i.removeExtra("edit_masks");
            getWindow().getDecorView().postDelayed(new Runnable() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda19
                @Override // java.lang.Runnable
                public final void run() {
                    MainActivity.this.startMaskEdit();
                }
            }, 300L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void startMaskEdit() {
        if (this.maskEditor != null) {
            return;
        }
        LinearLayout linearLayout = this.sidebar;
        FrameLayout frameLayout = null;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sidebar");
            linearLayout = null;
        }
        linearLayout.setVisibility(8);
        MainActivity mainActivity = this;
        Detector detector = getDetector();
        UiSettings uiSettings = this.settings;
        if (uiSettings == null) {
            Intrinsics.throwUninitializedPropertyAccessException("settings");
            uiSettings = null;
        }
        MaskEditor ed = new MaskEditor(mainActivity, detector, uiSettings.get("wide_camera"), new Function1() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda21
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit startMaskEdit$lambda$21;
                startMaskEdit$lambda$21 = MainActivity.startMaskEdit$lambda$21(((Boolean) obj).booleanValue());
                return startMaskEdit$lambda$21;
            }
        }, new Function0() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda22
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit startMaskEdit$lambda$22;
                startMaskEdit$lambda$22 = MainActivity.startMaskEdit$lambda$22(MainActivity.this);
                return startMaskEdit$lambda$22;
            }
        });
        this.maskEditor = ed;
        ed.setElevation(40.0f);
        ed.getBar().setElevation(41.0f);
        FrameLayout frameLayout2 = this.root;
        if (frameLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout2 = null;
        }
        frameLayout2.addView(ed, new FrameLayout.LayoutParams(-1, -1));
        FrameLayout frameLayout3 = this.root;
        if (frameLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
        } else {
            frameLayout = frameLayout3;
        }
        frameLayout.addView(ed.getBar(), new FrameLayout.LayoutParams(-2, -2, 49));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit startMaskEdit$lambda$21(boolean it) {
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit startMaskEdit$lambda$22(MainActivity this$0) {
        this$0.stopMaskEdit();
        return Unit.INSTANCE;
    }

    private final void stopMaskEdit() {
        MaskEditor ed = this.maskEditor;
        if (ed == null) {
            return;
        }
        FrameLayout frameLayout = this.root;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout = null;
        }
        frameLayout.removeView(ed);
        FrameLayout frameLayout2 = this.root;
        if (frameLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout2 = null;
        }
        frameLayout2.removeView(ed.getBar());
        this.maskEditor = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void pickBackup$lambda$24(final MainActivity this$0, Uri uri) {
        if (uri != null) {
            SettingsBackup.INSTANCE.restoreFrom(this$0, uri, new Function0() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda27
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit pickBackup$lambda$24$lambda$23;
                    pickBackup$lambda$24$lambda$23 = MainActivity.pickBackup$lambda$24$lambda$23(MainActivity.this);
                    return pickBackup$lambda$24$lambda$23;
                }
            });
        } else {
            this$0.backupArmed = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit pickBackup$lambda$24$lambda$23(MainActivity this$0) {
        this$0.backupArmed = true;
        return Unit.INSTANCE;
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
        this.visible = false;
        if (this.backupArmed) {
            SettingsBackup.INSTANCE.auto(this);
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        super.onStart();
        this.visible = true;
        LinkService.Companion.start(this);
        if (!this.askedNotify && Build.VERSION.SDK_INT >= 33 && checkSelfPermission("android.permission.POST_NOTIFICATIONS") != 0) {
            this.askedNotify = true;
            requestPermissions(new String[]{"android.permission.POST_NOTIFICATIONS"}, 71);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        SurfaceView surfaceView;
        super.onCreate(savedInstanceState);
        boolean freshInstall = savedInstanceState == null && SettingsBackup.INSTANCE.looksFresh(this);
        this.backupArmed = !freshInstall;
        ModelDownload.INSTANCE.detector(this);
        GpsBridge.INSTANCE.init(this);
        Odometry.INSTANCE.init(this);
        ContextCompat.registerReceiver(this, new BroadcastReceiver() { // from class: com.starpilot.starview.MainActivity$onCreate$1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context c, Intent i) {
                LaneSuggestView laneSuggestView;
                Intrinsics.checkNotNullParameter(c, "c");
                Intrinsics.checkNotNullParameter(i, "i");
                laneSuggestView = MainActivity.this.laneSuggest;
                if (laneSuggestView == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("laneSuggest");
                    laneSuggestView = null;
                }
                String stringExtra = i.getStringExtra("side");
                if (stringExtra == null) {
                    stringExtra = "left";
                }
                String stringExtra2 = i.getStringExtra("why");
                if (stringExtra2 == null) {
                    stringExtra2 = "";
                }
                laneSuggestView.testAsk(stringExtra, stringExtra2);
            }
        }, new IntentFilter("com.starpilot.starview.TEST_LANE_SUGGEST"), 2);
        ContextCompat.registerReceiver(this, new BroadcastReceiver() { // from class: com.starpilot.starview.MainActivity$onCreate$2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context c, Intent i) {
                AmySynth.Style style;
                Intrinsics.checkNotNullParameter(c, "c");
                Intrinsics.checkNotNullParameter(i, "i");
                String keys = i.getStringExtra("keys");
                if (i.getStringExtra("demo") != null) {
                    AmyVoice.INSTANCE.demo();
                    return;
                }
                if (keys == null) {
                    String it = i.getStringExtra("text");
                    if (it != null) {
                        Speech.INSTANCE.speak(it, false);
                        return;
                    }
                    return;
                }
                AmyVoice amyVoice = AmyVoice.INSTANCE;
                try {
                    String stringExtra = i.getStringExtra("style");
                    if (stringExtra == null) {
                        stringExtra = "short";
                    }
                    String upperCase = stringExtra.toUpperCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
                    style = AmySynth.Style.valueOf(upperCase);
                } catch (Exception e) {
                    style = AmySynth.Style.SHORT;
                }
                Iterable $this$map$iv = StringsKt.split$default((CharSequence) keys, new String[]{","}, false, 0, 6, (Object) null);
                Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                for (Object item$iv$iv : $this$map$iv) {
                    destination$iv$iv.add(StringsKt.trim((CharSequence) item$iv$iv).toString());
                }
                amyVoice.play(style, (List) destination$iv$iv);
            }
        }, new IntentFilter("com.starpilot.starview.TEST_SPEAK"), 2);
        Speech.init$default(Speech.INSTANCE, this, null, 2, null);
        OsmRoads.INSTANCE.init(this);
        BlinkLog.INSTANCE.init(this);
        MapdMaps.INSTANCE.init(this);
        installCrashLog();
        if (this.lastCrash.length() > 0) {
            Toast.makeText(this, "StarView closed unexpectedly last time: the details are on the diagnostics panel", 1).show();
        }
        setContentView(R.layout.activity_main);
        getWindow().addFlags(128);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        WindowInsetsControllerCompat $this$onCreate_u24lambda_u2425 = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        $this$onCreate_u24lambda_u2425.hide(WindowInsetsCompat.Type.systemBars());
        $this$onCreate_u24lambda_u2425.setSystemBarsBehavior(2);
        this.settings = new UiSettings(this);
        Telemetry telemetry = this.telemetry;
        UiSettings uiSettings = this.settings;
        if (uiSettings == null) {
            Intrinsics.throwUninitializedPropertyAccessException("settings");
            uiSettings = null;
        }
        telemetry.setUi(uiSettings);
        UiSettings uiSettings2 = this.settings;
        if (uiSettings2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("settings");
            uiSettings2 = null;
        }
        this.statsVisible = uiSettings2.get("stats");
        if (freshInstall) {
            getWindow().getDecorView().postDelayed(new Runnable() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda35
                @Override // java.lang.Runnable
                public final void run() {
                    MainActivity.onCreate$lambda$31(MainActivity.this);
                }
            }, 1500L);
        }
        SharedPreferences p = getSharedPreferences("starview_migr", 0);
        if (!p.getBoolean("m185", false)) {
            UiSettings uiSettings3 = this.settings;
            if (uiSettings3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("settings");
                uiSettings3 = null;
            }
            uiSettings3.set("road_texture", false);
            p.edit().putBoolean("m185", true).apply();
        }
        Unit unit = Unit.INSTANCE;
        this.root = (FrameLayout) findViewById(R.id.root);
        this.videoView = (SurfaceView) findViewById(R.id.video);
        this.overlay = (OverlayView) findViewById(R.id.overlay);
        new Thread(new Runnable() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda47
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.onCreate$lambda$33(MainActivity.this);
            }
        }).start();
        this.speedTv = (TextView) findViewById(R.id.speed);
        this.speedUnit = (TextView) findViewById(R.id.speedUnit);
        this.maxCard = (LinearLayout) findViewById(R.id.maxCard);
        this.maxLabel = (TextView) findViewById(R.id.maxLabel);
        this.setSpeedTv = (TextView) findViewById(R.id.setSpeed);
        this.slc = (SlcView) findViewById(R.id.slc);
        this.wheel = (WheelButton) findViewById(R.id.wheel);
        this.dm = (DmView) findViewById(R.id.dm);
        this.personality = (MiciDistanceView) findViewById(R.id.personality);
        this.slcActions = (SlcActionsView) findViewById(R.id.slcActions);
        this.curveLabel = (TextView) findViewById(R.id.curveLabel);
        this.clockTv = (TextView) findViewById(R.id.clockTv);
        this.modeText = (TextView) findViewById(R.id.modeText);
        this.devSidebar = (DevSidebarView) findViewById(R.id.devSidebar);
        SideView it = (SideView) findViewById(R.id.pipLeft);
        it.setSide("left");
        Unit unit2 = Unit.INSTANCE;
        this.pipLeft = it;
        SideView it2 = (SideView) findViewById(R.id.pipRight);
        it2.setSide("right");
        Unit unit3 = Unit.INSTANCE;
        this.pipRight = it2;
        SideCam.INSTANCE.setOnFrame(new Function0() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda59
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit onCreate$lambda$37;
                onCreate$lambda$37 = MainActivity.onCreate$lambda$37(MainActivity.this);
                return onCreate$lambda$37;
            }
        });
        this.egpu = (ImageView) findViewById(R.id.egpu);
        ImageView imageView = this.egpu;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("egpu");
            imageView = null;
        }
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda71
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.onCreate$lambda$40(MainActivity.this, view);
            }
        });
        this.roadName = (TextView) findViewById(R.id.roadName);
        this.statsTv = (TextView) findViewById(R.id.stats);
        this.statusTv = (TextView) findViewById(R.id.status);
        this.alertBox = (LinearLayout) findViewById(R.id.alertBox);
        this.alert1 = (TextView) findViewById(R.id.alert1);
        this.alert2 = (TextView) findViewById(R.id.alert2);
        this.alertTint = findViewById(R.id.alertTint);
        View view = this.alertTint;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("alertTint");
            view = null;
        }
        view.setBackground(this.tintDrawable);
        this.connectBar = (LinearLayout) findViewById(R.id.connectBar);
        this.hostEdit = (EditText) findViewById(R.id.hostEdit);
        this.gauge = (AetherGaugeView) findViewById(R.id.gauge);
        this.navCard = (NavCardView) findViewById(R.id.navCard);
        this.slowCard = (SlowReasonView) findViewById(R.id.slowCard);
        this.laneCard = (LaneCardView) findViewById(R.id.laneCard);
        this.blinkCard = (BlinkerCardView) findViewById(R.id.blinkCard);
        this.gpsCard = (GpsCardView) findViewById(R.id.gpsCard);
        this.laneSuggest = (LaneSuggestView) findViewById(R.id.laneSuggest);
        LaneSuggestView laneSuggestView = this.laneSuggest;
        if (laneSuggestView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("laneSuggest");
            laneSuggestView = null;
        }
        laneSuggestView.setTranslationZ(dp(40));
        this.nudgeHint = (NudgeHintView) findViewById(R.id.nudgeHint);
        this.weather = (WeatherView) findViewById(R.id.weather);
        this.slcSources = (SlcSourcesView) findViewById(R.id.slcSources);
        this.compass = (TextView) findViewById(R.id.compass);
        this.devMetrics = (TextView) findViewById(R.id.devMetrics);
        this.sidebar = (LinearLayout) findViewById(R.id.sidebar);
        OffroadHome $this$onCreate_u24lambda_u2441 = new OffroadHome(this, new OffroadHome.Actions() { // from class: com.starpilot.starview.MainActivity$onCreate$11
            @Override // com.starpilot.starview.OffroadHome.Actions
            public void control(JSONObject req) {
                DataClient dataClient;
                Intrinsics.checkNotNullParameter(req, "req");
                dataClient = MainActivity.this.data;
                if (dataClient == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("data");
                    dataClient = null;
                }
                dataClient.control(req);
            }

            @Override // com.starpilot.starview.OffroadHome.Actions
            public void openSettings(String tab) {
                String str;
                MainActivity mainActivity = MainActivity.this;
                Intent intent = new Intent(MainActivity.this, (Class<?>) SettingsActivity.class);
                str = MainActivity.this.host;
                mainActivity.startActivity(intent.putExtra("host", str).putExtra("tab", tab == null ? "device" : tab));
            }

            @Override // com.starpilot.starview.OffroadHome.Actions
            public void openFiles() {
                MainActivity.this.startActivity(new Intent(MainActivity.this, (Class<?>) FilesActivity.class));
            }

            @Override // com.starpilot.starview.OffroadHome.Actions
            public void openTerminal() {
                String str;
                MainActivity mainActivity = MainActivity.this;
                Intent intent = new Intent(MainActivity.this, (Class<?>) SettingsActivity.class);
                str = MainActivity.this.host;
                mainActivity.startActivity(intent.putExtra("host", str).putExtra("tab", "terminal"));
            }
        });
        $this$onCreate_u24lambda_u2441.setVisibility(8);
        Unit unit4 = Unit.INSTANCE;
        this.offroadHome = $this$onCreate_u24lambda_u2441;
        FrameLayout frameLayout = this.root;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout = null;
        }
        OffroadHome offroadHome = this.offroadHome;
        if (offroadHome == null) {
            Intrinsics.throwUninitializedPropertyAccessException("offroadHome");
            offroadHome = null;
        }
        OffroadHome offroadHome2 = offroadHome;
        FrameLayout frameLayout2 = this.root;
        if (frameLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout2 = null;
        }
        LinearLayout linearLayout = this.sidebar;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sidebar");
            linearLayout = null;
        }
        int indexOfChild = frameLayout2.indexOfChild(linearLayout);
        FrameLayout.LayoutParams $this$onCreate_u24lambda_u2442 = new FrameLayout.LayoutParams(-1, -1);
        $this$onCreate_u24lambda_u2442.setMarginStart(dp(240));
        Unit unit5 = Unit.INSTANCE;
        frameLayout.addView(offroadHome2, indexOfChild, $this$onCreate_u24lambda_u2442);
        OverlayView overlayView = this.overlay;
        if (overlayView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("overlay");
            overlayView = null;
        }
        overlayView.setT(this.telemetry);
        SlcView slcView = this.slc;
        if (slcView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("slc");
            slcView = null;
        }
        slcView.setT(this.telemetry);
        WheelButton wheelButton = this.wheel;
        if (wheelButton == null) {
            Intrinsics.throwUninitializedPropertyAccessException("wheel");
            wheelButton = null;
        }
        wheelButton.setT(this.telemetry);
        DmView dmView = this.dm;
        if (dmView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dm");
            dmView = null;
        }
        dmView.setT(this.telemetry);
        MiciDistanceView miciDistanceView = this.personality;
        if (miciDistanceView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("personality");
            miciDistanceView = null;
        }
        miciDistanceView.setT(this.telemetry);
        SlcActionsView slcActionsView = this.slcActions;
        if (slcActionsView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("slcActions");
            slcActionsView = null;
        }
        slcActionsView.setT(this.telemetry);
        SlcActionsView slcActionsView2 = this.slcActions;
        if (slcActionsView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("slcActions");
            slcActionsView2 = null;
        }
        slcActionsView2.setSend(new Function1() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda75
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit onCreate$lambda$43;
                onCreate$lambda$43 = MainActivity.onCreate$lambda$43(MainActivity.this, (JSONObject) obj);
                return onCreate$lambda$43;
            }
        });
        DevSidebarView devSidebarView = this.devSidebar;
        if (devSidebarView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("devSidebar");
            devSidebarView = null;
        }
        devSidebarView.setT(this.telemetry);
        SideView sideView = this.pipLeft;
        if (sideView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pipLeft");
            sideView = null;
        }
        sideView.setT(this.telemetry);
        SideView sideView2 = this.pipRight;
        if (sideView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pipRight");
            sideView2 = null;
        }
        sideView2.setT(this.telemetry);
        AetherGaugeView aetherGaugeView = this.gauge;
        if (aetherGaugeView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("gauge");
            aetherGaugeView = null;
        }
        aetherGaugeView.setT(this.telemetry);
        NavCardView navCardView = this.navCard;
        if (navCardView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("navCard");
            navCardView = null;
        }
        navCardView.setT(this.telemetry);
        SlowReasonView slowReasonView = this.slowCard;
        if (slowReasonView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("slowCard");
            slowReasonView = null;
        }
        slowReasonView.setT(this.telemetry);
        this.telemetry.getSlow().setOnEvent(new Function1() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda76
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit onCreate$lambda$44;
                onCreate$lambda$44 = MainActivity.onCreate$lambda$44(MainActivity.this, (SlowReason.Event) obj);
                return onCreate$lambda$44;
            }
        });
        BlinkerCardView blinkerCardView = this.blinkCard;
        if (blinkerCardView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("blinkCard");
            blinkerCardView = null;
        }
        blinkerCardView.setT(this.telemetry);
        GpsCardView gpsCardView = this.gpsCard;
        if (gpsCardView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("gpsCard");
            gpsCardView = null;
        }
        gpsCardView.setT(this.telemetry);
        LaneCardView laneCardView = this.laneCard;
        if (laneCardView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("laneCard");
            laneCardView = null;
        }
        laneCardView.setTracker(this.laneTracker);
        LaneCardView laneCardView2 = this.laneCard;
        if (laneCardView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("laneCard");
            laneCardView2 = null;
        }
        laneCardView2.setWhere(new Function0() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda77
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Triple onCreate$lambda$45;
                onCreate$lambda$45 = MainActivity.onCreate$lambda$45(MainActivity.this);
                return onCreate$lambda$45;
            }
        });
        this.laneTracker.setOnEvent(new Function1() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda78
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit onCreate$lambda$46;
                onCreate$lambda$46 = MainActivity.onCreate$lambda$46(MainActivity.this, (LaneBrain.Event) obj);
                return onCreate$lambda$46;
            }
        });
        WeatherView weatherView = this.weather;
        if (weatherView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("weather");
            weatherView = null;
        }
        weatherView.setT(this.telemetry);
        SlcSourcesView slcSourcesView = this.slcSources;
        if (slcSourcesView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("slcSources");
            slcSourcesView = null;
        }
        slcSourcesView.setT(this.telemetry);
        LaneCardView laneCardView3 = this.laneCard;
        if (laneCardView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("laneCard");
            laneCardView3 = null;
        }
        laneCardView3.setShots(getLaneShots());
        LaneCardView laneCardView4 = this.laneCard;
        if (laneCardView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("laneCard");
            laneCardView4 = null;
        }
        laneCardView4.setRamps(new Function0() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda79
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                List onCreate$lambda$47;
                onCreate$lambda$47 = MainActivity.onCreate$lambda$47(MainActivity.this);
                return onCreate$lambda$47;
            }
        });
        getLaneShots().setScene(new Function0() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda80
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SceneView onCreate$lambda$49;
                onCreate$lambda$49 = MainActivity.onCreate$lambda$49(MainActivity.this);
                return onCreate$lambda$49;
            }
        });
        getLaneShots().setLanes(new Function0() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda36
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Pair onCreate$lambda$50;
                onCreate$lambda$50 = MainActivity.onCreate$lambda$50(MainActivity.this);
                return onCreate$lambda$50;
            }
        });
        getLaneShots().setRoad(new Function0() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda37
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                LaneBrain.Road onCreate$lambda$51;
                onCreate$lambda$51 = MainActivity.onCreate$lambda$51(MainActivity.this);
                return onCreate$lambda$51;
            }
        });
        getLaneShots().setRamps(new Function0() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda38
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                List onCreate$lambda$52;
                onCreate$lambda$52 = MainActivity.onCreate$lambda$52(MainActivity.this);
                return onCreate$lambda$52;
            }
        });
        getLaneShots().setModelSays(new Function0() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda39
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String onCreate$lambda$54;
                onCreate$lambda$54 = MainActivity.onCreate$lambda$54(MainActivity.this);
                return onCreate$lambda$54;
            }
        });
        getLaneShots().setAnswer(new Function0() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda41
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String onCreate$lambda$55;
                onCreate$lambda$55 = MainActivity.onCreate$lambda$55(MainActivity.this);
                return onCreate$lambda$55;
            }
        });
        LaneBrain.Live.INSTANCE.setBrain(this.laneTracker);
        Hpms hpms = Hpms.INSTANCE;
        Context applicationContext = getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        hpms.init(applicationContext);
        Context applicationContext2 = getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext2, "getApplicationContext(...)");
        this.laneMapLog = new LaneMapLog(applicationContext2);
        LaneCardView laneCardView5 = this.laneCard;
        if (laneCardView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("laneCard");
            laneCardView5 = null;
        }
        laneCardView5.setAhead(new Function0() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda42
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Pair onCreate$lambda$56;
                onCreate$lambda$56 = MainActivity.onCreate$lambda$56(MainActivity.this);
                return onCreate$lambda$56;
            }
        });
        this.laneAhead.setOnEvent(new Function2() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda43
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit onCreate$lambda$57;
                onCreate$lambda$57 = MainActivity.onCreate$lambda$57(MainActivity.this, (String) obj, ((Boolean) obj2).booleanValue());
                return onCreate$lambda$57;
            }
        });
        final File countsFile = new File(getFilesDir(), "lane_counts.json");
        try {
            JSONObject j = new JSONObject(FilesKt.readText$default(countsFile, null, 1, null));
            Iterator<String> keys = j.keys();
            Intrinsics.checkNotNullExpressionValue(keys, "keys(...)");
            while (keys.hasNext()) {
                String k = keys.next();
                HashMap<Long, Integer> myCounts = this.laneTracker.getMyCounts();
                Intrinsics.checkNotNull(k);
                myCounts.put(Long.valueOf(Long.parseLong(k)), Integer.valueOf(j.getInt(k)));
            }
        } catch (Exception e) {
        }
        this.laneTracker.setOnCountsChanged(new Function0() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda44
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit onCreate$lambda$58;
                onCreate$lambda$58 = MainActivity.onCreate$lambda$58(MainActivity.this, countsFile);
                return onCreate$lambda$58;
            }
        });
        Link.INSTANCE.loadKey(this);
        DriveJournal.INSTANCE.init(this);
        Ideas.INSTANCE.init(this);
        MapBumps.INSTANCE.init(this);
        MapCams.INSTANCE.init(this);
        DriveTrack.INSTANCE.init(this);
        final SharedPreferences prefs = getSharedPreferences("starview", 0);
        EditText editText = this.hostEdit;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("hostEdit");
            editText = null;
        }
        editText.setText(prefs.getString("host", ""));
        ((Button) findViewById(R.id.connectBtn)).setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda45
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                MainActivity.onCreate$lambda$59(MainActivity.this, prefs, view2);
            }
        });
        ((Button) findViewById(R.id.findBtn)).setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda46
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                MainActivity.this.discover();
            }
        });
        final Ref.ObjectRef lastTabletMode = new Ref.ObjectRef();
        this.tabletModePusher = new Function0() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda48
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit onCreate$lambda$62;
                onCreate$lambda$62 = MainActivity.onCreate$lambda$62(MainActivity.this, lastTabletMode);
                return onCreate$lambda$62;
            }
        };
        this.data = new DataClient(this.telemetry, new Function2() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda49
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit onCreate$lambda$66;
                onCreate$lambda$66 = MainActivity.onCreate$lambda$66(MainActivity.this, lastTabletMode, (String) obj, ((Boolean) obj2).booleanValue());
                return onCreate$lambda$66;
            }
        });
        Link.INSTANCE.setTelemetry(this.telemetry);
        Link link = Link.INSTANCE;
        DataClient dataClient = this.data;
        if (dataClient == null) {
            Intrinsics.throwUninitializedPropertyAccessException("data");
            dataClient = null;
        }
        link.setData(dataClient);
        findViewById(R.id.gear).setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda50
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                MainActivity.onCreate$lambda$67(MainActivity.this, view2);
            }
        });
        this.video = new VideoClient(new Function1() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda52
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit onCreate$lambda$69;
                onCreate$lambda$69 = MainActivity.onCreate$lambda$69(MainActivity.this, (String) obj);
                return onCreate$lambda$69;
            }
        }, new Function2() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda53
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit onCreate$lambda$71;
                onCreate$lambda$71 = MainActivity.onCreate$lambda$71(MainActivity.this, ((Integer) obj).intValue(), ((Integer) obj2).intValue());
                return onCreate$lambda$71;
            }
        });
        OverlayView overlayView2 = this.overlay;
        if (overlayView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("overlay");
            overlayView2 = null;
        }
        overlayView2.setOnResized(new Function0() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda54
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit onCreate$lambda$73;
                onCreate$lambda$73 = MainActivity.onCreate$lambda$73(MainActivity.this);
                return onCreate$lambda$73;
            }
        });
        Context applicationContext3 = getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext3, "getApplicationContext(...)");
        this.layoutSeg = new LayoutSeg(applicationContext3);
        VideoClient c = new VideoClient(new Function1() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda55
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit onCreate$lambda$74;
                onCreate$lambda$74 = MainActivity.onCreate$lambda$74((String) obj);
                return onCreate$lambda$74;
            }
        }, new Function2() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda56
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit onCreate$lambda$75;
                onCreate$lambda$75 = MainActivity.onCreate$lambda$75(((Integer) obj).intValue(), ((Integer) obj2).intValue());
                return onCreate$lambda$75;
            }
        });
        c.setOnImage(new Function1() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda57
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit onCreate$lambda$77$lambda$76;
                onCreate$lambda$77$lambda$76 = MainActivity.onCreate$lambda$77$lambda$76(MainActivity.this, (Image) obj);
                return onCreate$lambda$77$lambda$76;
            }
        });
        Unit unit6 = Unit.INSTANCE;
        this.detRoad = c;
        VideoClient c2 = new VideoClient(new Function1() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda58
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit onCreate$lambda$78;
                onCreate$lambda$78 = MainActivity.onCreate$lambda$78((String) obj);
                return onCreate$lambda$78;
            }
        }, new Function2() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda60
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit onCreate$lambda$79;
                onCreate$lambda$79 = MainActivity.onCreate$lambda$79(((Integer) obj).intValue(), ((Integer) obj2).intValue());
                return onCreate$lambda$79;
            }
        });
        c2.setOnImage(new Function1() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda61
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit onCreate$lambda$81$lambda$80;
                onCreate$lambda$81$lambda$80 = MainActivity.onCreate$lambda$81$lambda$80(MainActivity.this, (Image) obj);
                return onCreate$lambda$81$lambda$80;
            }
        });
        Unit unit7 = Unit.INSTANCE;
        this.detWide = c2;
        SurfaceView surfaceView2 = this.videoView;
        if (surfaceView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("videoView");
            surfaceView2 = null;
        }
        surfaceView2.getHolder().addCallback(new SurfaceHolder.Callback() { // from class: com.starpilot.starview.MainActivity$onCreate$42
            @Override // android.view.SurfaceHolder.Callback
            public void surfaceCreated(SurfaceHolder holder) {
                Intrinsics.checkNotNullParameter(holder, "holder");
                MainActivity.this.surfaceReady = true;
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Intrinsics.checkNotNullParameter(holder, "holder");
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceDestroyed(SurfaceHolder holder) {
                Intrinsics.checkNotNullParameter(holder, "holder");
                MainActivity.this.surfaceReady = false;
                MainActivity.this.stopVideo();
            }
        });
        wireControls();
        TextView $this$onCreate_u24lambda_u2484 = new TextView(this);
        $this$onCreate_u24lambda_u2484.setTextSize(15.0f);
        $this$onCreate_u24lambda_u2484.setTypeface(null, 1);
        $this$onCreate_u24lambda_u2484.setTextColor(-1);
        $this$onCreate_u24lambda_u2484.setGravity(17);
        $this$onCreate_u24lambda_u2484.setPadding(dp(18), dp(10), dp(18), dp(10));
        $this$onCreate_u24lambda_u2484.setVisibility(8);
        GradientDrawable $this$onCreate_u24lambda_u2484_u24lambda_u2482 = new GradientDrawable();
        $this$onCreate_u24lambda_u2484_u24lambda_u2482.setCornerRadius(dp(22));
        $this$onCreate_u24lambda_u2484_u24lambda_u2482.setColor(Color.argb(150, 20, 22, 28));
        $this$onCreate_u24lambda_u2484_u24lambda_u2482.setStroke(dp(1), Color.argb(90, 255, 255, 255));
        $this$onCreate_u24lambda_u2484.setBackground($this$onCreate_u24lambda_u2484_u24lambda_u2482);
        $this$onCreate_u24lambda_u2484.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda63
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                MainActivity.onCreate$lambda$84$lambda$83(MainActivity.this, view2);
            }
        });
        Unit unit8 = Unit.INSTANCE;
        this.sceneBtn = $this$onCreate_u24lambda_u2484;
        FrameLayout frameLayout3 = this.root;
        if (frameLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout3 = null;
        }
        TextView textView = this.sceneBtn;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sceneBtn");
            textView = null;
        }
        TextView textView2 = textView;
        FrameLayout frameLayout4 = this.root;
        if (frameLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout4 = null;
        }
        LinearLayout linearLayout2 = this.sidebar;
        if (linearLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sidebar");
            linearLayout2 = null;
        }
        int indexOfChild2 = frameLayout4.indexOfChild(linearLayout2);
        FrameLayout.LayoutParams $this$onCreate_u24lambda_u2485 = new FrameLayout.LayoutParams(-2, -2, 8388691);
        $this$onCreate_u24lambda_u2485.setMarginStart(dp(24));
        $this$onCreate_u24lambda_u2485.bottomMargin = dp(24);
        Unit unit9 = Unit.INSTANCE;
        frameLayout3.addView(textView2, indexOfChild2, $this$onCreate_u24lambda_u2485);
        TextView $this$onCreate_u24lambda_u2488 = new TextView(this);
        $this$onCreate_u24lambda_u2488.setText("⌕  Where to?");
        $this$onCreate_u24lambda_u2488.setTextSize(15.0f);
        $this$onCreate_u24lambda_u2488.setTypeface(null, 1);
        $this$onCreate_u24lambda_u2488.setTextColor(-1);
        $this$onCreate_u24lambda_u2488.setGravity(17);
        $this$onCreate_u24lambda_u2488.setPadding(dp(18), dp(10), dp(18), dp(10));
        $this$onCreate_u24lambda_u2488.setVisibility(8);
        GradientDrawable $this$onCreate_u24lambda_u2488_u24lambda_u2486 = new GradientDrawable();
        $this$onCreate_u24lambda_u2488_u24lambda_u2486.setCornerRadius(dp(22));
        $this$onCreate_u24lambda_u2488_u24lambda_u2486.setColor(Color.argb(150, 20, 22, 28));
        $this$onCreate_u24lambda_u2488_u24lambda_u2486.setStroke(dp(1), Color.argb(90, 255, 255, 255));
        $this$onCreate_u24lambda_u2488.setBackground($this$onCreate_u24lambda_u2488_u24lambda_u2486);
        $this$onCreate_u24lambda_u2488.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda64
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                MainActivity.this.openDestinations();
            }
        });
        Unit unit10 = Unit.INSTANCE;
        this.whereBtn = $this$onCreate_u24lambda_u2488;
        FrameLayout frameLayout5 = this.root;
        if (frameLayout5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout5 = null;
        }
        TextView textView3 = this.whereBtn;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("whereBtn");
            textView3 = null;
        }
        TextView textView4 = textView3;
        FrameLayout frameLayout6 = this.root;
        if (frameLayout6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout6 = null;
        }
        LinearLayout linearLayout3 = this.sidebar;
        if (linearLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sidebar");
            linearLayout3 = null;
        }
        int indexOfChild3 = frameLayout6.indexOfChild(linearLayout3);
        FrameLayout.LayoutParams $this$onCreate_u24lambda_u2489 = new FrameLayout.LayoutParams(-2, -2, 8388691);
        $this$onCreate_u24lambda_u2489.setMarginStart(dp(160));
        $this$onCreate_u24lambda_u2489.bottomMargin = dp(24);
        Unit unit11 = Unit.INSTANCE;
        frameLayout5.addView(textView4, indexOfChild3, $this$onCreate_u24lambda_u2489);
        TextView $this$onCreate_u24lambda_u2492 = new TextView(this);
        $this$onCreate_u24lambda_u2492.setText("MAP");
        $this$onCreate_u24lambda_u2492.setTextSize(15.0f);
        $this$onCreate_u24lambda_u2492.setTypeface(null, 1);
        $this$onCreate_u24lambda_u2492.setTextColor(-1);
        $this$onCreate_u24lambda_u2492.setGravity(17);
        $this$onCreate_u24lambda_u2492.setPadding(dp(18), dp(10), dp(18), dp(10));
        $this$onCreate_u24lambda_u2492.setVisibility(8);
        GradientDrawable $this$onCreate_u24lambda_u2492_u24lambda_u2490 = new GradientDrawable();
        $this$onCreate_u24lambda_u2492_u24lambda_u2490.setCornerRadius(dp(22));
        $this$onCreate_u24lambda_u2492_u24lambda_u2490.setColor(Color.argb(150, 20, 22, 28));
        $this$onCreate_u24lambda_u2492_u24lambda_u2490.setStroke(dp(1), Color.argb(90, 255, 255, 255));
        $this$onCreate_u24lambda_u2492.setBackground($this$onCreate_u24lambda_u2492_u24lambda_u2490);
        $this$onCreate_u24lambda_u2492.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda65
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                MainActivity.this.openMap();
            }
        });
        Unit unit12 = Unit.INSTANCE;
        this.mapBtn = $this$onCreate_u24lambda_u2492;
        FrameLayout frameLayout7 = this.root;
        if (frameLayout7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout7 = null;
        }
        TextView textView5 = this.mapBtn;
        if (textView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mapBtn");
            textView5 = null;
        }
        TextView textView6 = textView5;
        FrameLayout frameLayout8 = this.root;
        if (frameLayout8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout8 = null;
        }
        LinearLayout linearLayout4 = this.sidebar;
        if (linearLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sidebar");
            linearLayout4 = null;
        }
        int indexOfChild4 = frameLayout8.indexOfChild(linearLayout4);
        FrameLayout.LayoutParams $this$onCreate_u24lambda_u2493 = new FrameLayout.LayoutParams(-2, -2, 8388691);
        $this$onCreate_u24lambda_u2493.setMarginStart(dp(330));
        $this$onCreate_u24lambda_u2493.bottomMargin = dp(24);
        Unit unit13 = Unit.INSTANCE;
        frameLayout7.addView(textView6, indexOfChild4, $this$onCreate_u24lambda_u2493);
        TextView $this$onCreate_u24lambda_u2496 = new TextView(this);
        $this$onCreate_u24lambda_u2496.setText("☰  QUICK");
        $this$onCreate_u24lambda_u2496.setTextSize(15.0f);
        $this$onCreate_u24lambda_u2496.setTypeface(null, 1);
        $this$onCreate_u24lambda_u2496.setTextColor(-1);
        $this$onCreate_u24lambda_u2496.setGravity(17);
        $this$onCreate_u24lambda_u2496.setPadding(dp(18), dp(10), dp(18), dp(10));
        $this$onCreate_u24lambda_u2496.setVisibility(8);
        GradientDrawable $this$onCreate_u24lambda_u2496_u24lambda_u2494 = new GradientDrawable();
        $this$onCreate_u24lambda_u2496_u24lambda_u2494.setCornerRadius(dp(22));
        $this$onCreate_u24lambda_u2496_u24lambda_u2494.setColor(Color.argb(150, 20, 22, 28));
        $this$onCreate_u24lambda_u2496_u24lambda_u2494.setStroke(dp(1), Color.argb(90, 255, 255, 255));
        $this$onCreate_u24lambda_u2496.setBackground($this$onCreate_u24lambda_u2496_u24lambda_u2494);
        $this$onCreate_u24lambda_u2496.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda66
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                MainActivity.this.openQuick();
            }
        });
        Unit unit14 = Unit.INSTANCE;
        this.quickBtn = $this$onCreate_u24lambda_u2496;
        FrameLayout frameLayout9 = this.root;
        if (frameLayout9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout9 = null;
        }
        TextView textView7 = this.quickBtn;
        if (textView7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("quickBtn");
            textView7 = null;
        }
        TextView textView8 = textView7;
        FrameLayout frameLayout10 = this.root;
        if (frameLayout10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout10 = null;
        }
        LinearLayout linearLayout5 = this.sidebar;
        if (linearLayout5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sidebar");
            linearLayout5 = null;
        }
        int indexOfChild5 = frameLayout10.indexOfChild(linearLayout5);
        FrameLayout.LayoutParams $this$onCreate_u24lambda_u2497 = new FrameLayout.LayoutParams(-2, -2, 8388691);
        $this$onCreate_u24lambda_u2497.setMarginStart(dp(440));
        $this$onCreate_u24lambda_u2497.bottomMargin = dp(24);
        Unit unit15 = Unit.INSTANCE;
        frameLayout9.addView(textView8, indexOfChild5, $this$onCreate_u24lambda_u2497);
        ImageView $this$onCreate_u24lambda_u24100 = new ImageView(this);
        $this$onCreate_u24lambda_u24100.setImageDrawable(SvIcon.INSTANCE.drawable("mic", -1, dp(26)));
        $this$onCreate_u24lambda_u24100.setScaleType(ImageView.ScaleType.CENTER);
        $this$onCreate_u24lambda_u24100.setContentDescription("Say it");
        $this$onCreate_u24lambda_u24100.setVisibility(8);
        GradientDrawable $this$onCreate_u24lambda_u24100_u24lambda_u2498 = new GradientDrawable();
        $this$onCreate_u24lambda_u24100_u24lambda_u2498.setShape(1);
        $this$onCreate_u24lambda_u24100_u24lambda_u2498.setColor(Color.argb(150, 20, 22, 28));
        $this$onCreate_u24lambda_u24100_u24lambda_u2498.setStroke(dp(1), Color.argb(90, 255, 255, 255));
        $this$onCreate_u24lambda_u24100.setBackground($this$onCreate_u24lambda_u24100_u24lambda_u2498);
        $this$onCreate_u24lambda_u24100.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda67
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                MainActivity.this.listenVoice();
            }
        });
        Unit unit16 = Unit.INSTANCE;
        this.micBtn = $this$onCreate_u24lambda_u24100;
        FrameLayout frameLayout11 = this.root;
        if (frameLayout11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout11 = null;
        }
        ImageView imageView2 = this.micBtn;
        if (imageView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("micBtn");
            imageView2 = null;
        }
        ImageView imageView3 = imageView2;
        FrameLayout frameLayout12 = this.root;
        if (frameLayout12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout12 = null;
        }
        LinearLayout linearLayout6 = this.sidebar;
        if (linearLayout6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sidebar");
            linearLayout6 = null;
        }
        int indexOfChild6 = frameLayout12.indexOfChild(linearLayout6);
        FrameLayout.LayoutParams $this$onCreate_u24lambda_u24101 = new FrameLayout.LayoutParams(dp(48), dp(48), 8388691);
        $this$onCreate_u24lambda_u24101.setMarginStart(dp(380));
        $this$onCreate_u24lambda_u24101.bottomMargin = dp(24);
        Unit unit17 = Unit.INSTANCE;
        frameLayout11.addView(imageView3, indexOfChild6, $this$onCreate_u24lambda_u24101);
        ImageView $this$onCreate_u24lambda_u24104 = new ImageView(this);
        $this$onCreate_u24lambda_u24104.setImageDrawable(SvIcon.INSTANCE.drawable("bookmark", Color.rgb(255, 196, 0), dp(26)));
        $this$onCreate_u24lambda_u24104.setScaleType(ImageView.ScaleType.CENTER);
        $this$onCreate_u24lambda_u24104.setContentDescription("Bookmark this moment");
        $this$onCreate_u24lambda_u24104.setVisibility(8);
        GradientDrawable $this$onCreate_u24lambda_u24104_u24lambda_u24102 = new GradientDrawable();
        $this$onCreate_u24lambda_u24104_u24lambda_u24102.setShape(1);
        $this$onCreate_u24lambda_u24104_u24lambda_u24102.setColor(Color.argb(150, 20, 22, 28));
        $this$onCreate_u24lambda_u24104_u24lambda_u24102.setStroke(dp(1), Color.argb(90, 255, 255, 255));
        $this$onCreate_u24lambda_u24104.setBackground($this$onCreate_u24lambda_u24104_u24lambda_u24102);
        $this$onCreate_u24lambda_u24104.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda68
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                MainActivity.this.bookmarkNow();
            }
        });
        Unit unit18 = Unit.INSTANCE;
        this.bookmarkBtn = $this$onCreate_u24lambda_u24104;
        FrameLayout frameLayout13 = this.root;
        if (frameLayout13 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout13 = null;
        }
        ImageView imageView4 = this.bookmarkBtn;
        if (imageView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bookmarkBtn");
            imageView4 = null;
        }
        ImageView imageView5 = imageView4;
        FrameLayout frameLayout14 = this.root;
        if (frameLayout14 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout14 = null;
        }
        LinearLayout linearLayout7 = this.sidebar;
        if (linearLayout7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sidebar");
            linearLayout7 = null;
        }
        int indexOfChild7 = frameLayout14.indexOfChild(linearLayout7);
        FrameLayout.LayoutParams $this$onCreate_u24lambda_u24105 = new FrameLayout.LayoutParams(dp(48), dp(48), 8388691);
        $this$onCreate_u24lambda_u24105.setMarginStart(dp(320));
        $this$onCreate_u24lambda_u24105.bottomMargin = dp(24);
        Unit unit19 = Unit.INSTANCE;
        frameLayout13.addView(imageView5, indexOfChild7, $this$onCreate_u24lambda_u24105);
        this.bookmarkCard = new BookmarkCard(this, new Function0() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda69
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean onCreate$lambda$106;
                onCreate$lambda$106 = MainActivity.onCreate$lambda$106(MainActivity.this);
                return Boolean.valueOf(onCreate$lambda$106);
            }
        }, new Function0() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda70
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit onCreate$lambda$107;
                onCreate$lambda$107 = MainActivity.onCreate$lambda$107(MainActivity.this);
                return onCreate$lambda$107;
            }
        });
        BookmarkCard bookmarkCard = this.bookmarkCard;
        if (bookmarkCard == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bookmarkCard");
            bookmarkCard = null;
        }
        bookmarkCard.setTranslationZ(dp(42));
        FrameLayout frameLayout15 = this.root;
        if (frameLayout15 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout15 = null;
        }
        BookmarkCard bookmarkCard2 = this.bookmarkCard;
        if (bookmarkCard2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bookmarkCard");
            bookmarkCard2 = null;
        }
        FrameLayout.LayoutParams $this$onCreate_u24lambda_u24108 = new FrameLayout.LayoutParams(-2, -2, 8388691);
        $this$onCreate_u24lambda_u24108.setMarginStart(dp(24));
        $this$onCreate_u24lambda_u24108.bottomMargin = dp(86);
        Unit unit20 = Unit.INSTANCE;
        frameLayout15.addView(bookmarkCard2, $this$onCreate_u24lambda_u24108);
        TextView $this$onCreate_u24lambda_u24111 = new TextView(this);
        $this$onCreate_u24lambda_u24111.setText("SAVER ON");
        $this$onCreate_u24lambda_u24111.setTextSize(15.0f);
        $this$onCreate_u24lambda_u24111.setTypeface(null, 1);
        $this$onCreate_u24lambda_u24111.setTextColor(Color.rgb(111, 227, 161));
        $this$onCreate_u24lambda_u24111.setGravity(17);
        $this$onCreate_u24lambda_u24111.setPadding(dp(18), dp(10), dp(18), dp(10));
        $this$onCreate_u24lambda_u24111.setVisibility(8);
        GradientDrawable $this$onCreate_u24lambda_u24111_u24lambda_u24109 = new GradientDrawable();
        $this$onCreate_u24lambda_u24111_u24lambda_u24109.setCornerRadius(dp(22));
        int i = 12;
        $this$onCreate_u24lambda_u24111_u24lambda_u24109.setColor(Color.argb(170, 12, 34, 22));
        $this$onCreate_u24lambda_u24111_u24lambda_u24109.setStroke(dp(1), Color.rgb(43, 107, 75));
        $this$onCreate_u24lambda_u24111.setBackground($this$onCreate_u24lambda_u24111_u24lambda_u24109);
        $this$onCreate_u24lambda_u24111.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda72
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                MainActivity.this.setSaver(false);
            }
        });
        Unit unit21 = Unit.INSTANCE;
        this.saverPill = $this$onCreate_u24lambda_u24111;
        FrameLayout frameLayout16 = this.root;
        if (frameLayout16 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout16 = null;
        }
        TextView textView9 = this.saverPill;
        if (textView9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("saverPill");
            textView9 = null;
        }
        TextView textView10 = textView9;
        FrameLayout frameLayout17 = this.root;
        if (frameLayout17 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout17 = null;
        }
        LinearLayout linearLayout8 = this.sidebar;
        if (linearLayout8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sidebar");
            linearLayout8 = null;
        }
        int indexOfChild8 = frameLayout17.indexOfChild(linearLayout8);
        FrameLayout.LayoutParams $this$onCreate_u24lambda_u24112 = new FrameLayout.LayoutParams(-2, -2, 8388691);
        $this$onCreate_u24lambda_u24112.setMarginStart(dp(570));
        $this$onCreate_u24lambda_u24112.bottomMargin = dp(24);
        Unit unit22 = Unit.INSTANCE;
        frameLayout16.addView(textView10, indexOfChild8, $this$onCreate_u24lambda_u24112);
        applySaver();
        LinearLayout $this$onCreate_u24lambda_u24113 = new LinearLayout(this);
        $this$onCreate_u24lambda_u24113.setOrientation(0);
        $this$onCreate_u24lambda_u24113.setVisibility(8);
        Unit unit23 = Unit.INSTANCE;
        this.favBar = $this$onCreate_u24lambda_u24113;
        FrameLayout frameLayout18 = this.root;
        if (frameLayout18 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout18 = null;
        }
        LinearLayout linearLayout9 = this.favBar;
        if (linearLayout9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("favBar");
            linearLayout9 = null;
        }
        LinearLayout linearLayout10 = linearLayout9;
        FrameLayout frameLayout19 = this.root;
        if (frameLayout19 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout19 = null;
        }
        LinearLayout linearLayout11 = this.sidebar;
        if (linearLayout11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sidebar");
            linearLayout11 = null;
        }
        int indexOfChild9 = frameLayout19.indexOfChild(linearLayout11);
        FrameLayout.LayoutParams $this$onCreate_u24lambda_u24114 = new FrameLayout.LayoutParams(-2, -2, 8388691);
        $this$onCreate_u24lambda_u24114.setMarginStart(dp(24));
        $this$onCreate_u24lambda_u24114.bottomMargin = dp(84);
        Unit unit24 = Unit.INSTANCE;
        frameLayout18.addView(linearLayout10, indexOfChild9, $this$onCreate_u24lambda_u24114);
        setupMapFrame();
        ReplayBar $this$onCreate_u24lambda_u24115 = new ReplayBar(this, null, 2, null);
        $this$onCreate_u24lambda_u24115.setVisibility(8);
        $this$onCreate_u24lambda_u24115.setTranslationZ(dp(60));
        Unit unit25 = Unit.INSTANCE;
        this.replayBar = $this$onCreate_u24lambda_u24115;
        FrameLayout frameLayout20 = this.root;
        if (frameLayout20 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout20 = null;
        }
        ReplayBar replayBar = this.replayBar;
        if (replayBar == null) {
            Intrinsics.throwUninitializedPropertyAccessException("replayBar");
            replayBar = null;
        }
        FrameLayout.LayoutParams $this$onCreate_u24lambda_u24116 = new FrameLayout.LayoutParams(-2, -2, 49);
        $this$onCreate_u24lambda_u24116.topMargin = dp(12);
        Unit unit26 = Unit.INSTANCE;
        frameLayout20.addView(replayBar, $this$onCreate_u24lambda_u24116);
        TextView $this$onCreate_u24lambda_u24118 = new TextView(this);
        $this$onCreate_u24lambda_u24118.setTextColor(-1);
        $this$onCreate_u24lambda_u24118.setTextSize(17.0f);
        $this$onCreate_u24lambda_u24118.setTypeface(null, 1);
        $this$onCreate_u24lambda_u24118.setVisibility(8);
        $this$onCreate_u24lambda_u24118.setPadding(dp(16), dp(8), dp(16), dp(8));
        $this$onCreate_u24lambda_u24118.setTranslationZ(dp(58));
        GradientDrawable $this$onCreate_u24lambda_u24118_u24lambda_u24117 = new GradientDrawable();
        $this$onCreate_u24lambda_u24118_u24lambda_u24117.setCornerRadius(dp(20));
        $this$onCreate_u24lambda_u24118_u24lambda_u24117.setColor(Color.argb(200, 20, 60, 140));
        $this$onCreate_u24lambda_u24118_u24lambda_u24117.setStroke(dp(1), Color.argb(140, 120, 180, 255));
        $this$onCreate_u24lambda_u24118.setBackground($this$onCreate_u24lambda_u24118_u24lambda_u24117);
        Unit unit27 = Unit.INSTANCE;
        this.ideasChip = $this$onCreate_u24lambda_u24118;
        FrameLayout frameLayout21 = this.root;
        if (frameLayout21 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout21 = null;
        }
        TextView textView11 = this.ideasChip;
        if (textView11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ideasChip");
            textView11 = null;
        }
        FrameLayout.LayoutParams $this$onCreate_u24lambda_u24119 = new FrameLayout.LayoutParams(-2, -2, 49);
        $this$onCreate_u24lambda_u24119.topMargin = dp(70);
        Unit unit28 = Unit.INSTANCE;
        frameLayout21.addView(textView11, $this$onCreate_u24lambda_u24119);
        LinearLayout $this$onCreate_u24lambda_u24127 = new LinearLayout(this);
        int i2 = 0;
        $this$onCreate_u24lambda_u24127.setOrientation(1);
        $this$onCreate_u24lambda_u24127.setVisibility(8);
        $this$onCreate_u24lambda_u24127.setTranslationZ(dp(62));
        $this$onCreate_u24lambda_u24127.setPadding(dp(16), dp(12), dp(16), dp(12));
        GradientDrawable $this$onCreate_u24lambda_u24127_u24lambda_u24120 = new GradientDrawable();
        $this$onCreate_u24lambda_u24127_u24lambda_u24120.setCornerRadius(dp(18));
        $this$onCreate_u24lambda_u24127_u24lambda_u24120.setColor(Color.argb(235, 24, 26, 32));
        $this$onCreate_u24lambda_u24127.setBackground($this$onCreate_u24lambda_u24127_u24lambda_u24120);
        TextView $this$onCreate_u24lambda_u24127_u24lambda_u24121 = new TextView(this);
        $this$onCreate_u24lambda_u24127_u24lambda_u24121.setText("Why did you take over?");
        $this$onCreate_u24lambda_u24127_u24lambda_u24121.setTextColor(-1);
        $this$onCreate_u24lambda_u24127_u24lambda_u24121.setTextSize(18.0f);
        $this$onCreate_u24lambda_u24127_u24lambda_u24121.setTypeface(null, 1);
        $this$onCreate_u24lambda_u24127.addView($this$onCreate_u24lambda_u24127_u24lambda_u24121);
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(0);
        Iterator it3 = CollectionsKt.listOf((Object[]) new String[]{"Too close", "Swerved", "Wrong lane", "Braked late", "Just because"}).iterator();
        while (it3.hasNext()) {
            final String w = (String) it3.next();
            TextView $this$onCreate_u24lambda_u24127_u24lambda_u24125 = new TextView(this);
            $this$onCreate_u24lambda_u24127_u24lambda_u24125.setText(w);
            $this$onCreate_u24lambda_u24127_u24lambda_u24125.setTextColor(-1);
            $this$onCreate_u24lambda_u24127_u24lambda_u24125.setTextSize(16.0f);
            int i3 = i2;
            Iterator it4 = it3;
            $this$onCreate_u24lambda_u24127_u24lambda_u24125.setPadding(dp(14), dp(i), dp(14), dp(i));
            GradientDrawable $this$onCreate_u24lambda_u24127_u24lambda_u24125_u24lambda_u24123 = new GradientDrawable();
            $this$onCreate_u24lambda_u24127_u24lambda_u24125_u24lambda_u24123.setCornerRadius(dp(i));
            $this$onCreate_u24lambda_u24127_u24lambda_u24125_u24lambda_u24123.setColor(Color.rgb(48, 54, 66));
            $this$onCreate_u24lambda_u24127_u24lambda_u24125.setBackground($this$onCreate_u24lambda_u24127_u24lambda_u24125_u24lambda_u24123);
            $this$onCreate_u24lambda_u24127_u24lambda_u24125.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda74
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    MainActivity.onCreate$lambda$127$lambda$125$lambda$124(MainActivity.this, w, view2);
                }
            });
            LinearLayout.LayoutParams $this$onCreate_u24lambda_u24127_u24lambda_u24126 = new LinearLayout.LayoutParams(-2, -2);
            $this$onCreate_u24lambda_u24127_u24lambda_u24126.setMarginEnd(dp(8));
            $this$onCreate_u24lambda_u24127_u24lambda_u24126.topMargin = dp(10);
            Unit unit29 = Unit.INSTANCE;
            row.addView($this$onCreate_u24lambda_u24127_u24lambda_u24125, $this$onCreate_u24lambda_u24127_u24lambda_u24126);
            i2 = i3;
            it3 = it4;
            countsFile = countsFile;
            i = 12;
        }
        $this$onCreate_u24lambda_u24127.addView(row);
        Unit unit30 = Unit.INSTANCE;
        this.ideasWhy = $this$onCreate_u24lambda_u24127;
        FrameLayout frameLayout22 = this.root;
        if (frameLayout22 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout22 = null;
        }
        LinearLayout linearLayout12 = this.ideasWhy;
        if (linearLayout12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ideasWhy");
            linearLayout12 = null;
        }
        FrameLayout.LayoutParams $this$onCreate_u24lambda_u24128 = new FrameLayout.LayoutParams(-2, -2, 49);
        $this$onCreate_u24lambda_u24128.topMargin = dp(120);
        Unit unit31 = Unit.INSTANCE;
        frameLayout22.addView(linearLayout12, $this$onCreate_u24lambda_u24128);
        ImageView $this$onCreate_u24lambda_u24130 = new ImageView(this);
        $this$onCreate_u24lambda_u24130.setVisibility(8);
        $this$onCreate_u24lambda_u24130.setTranslationZ(dp(40));
        $this$onCreate_u24lambda_u24130.setScaleType(ImageView.ScaleType.FIT_CENTER);
        GradientDrawable $this$onCreate_u24lambda_u24130_u24lambda_u24129 = new GradientDrawable();
        $this$onCreate_u24lambda_u24130_u24lambda_u24129.setCornerRadius(dp(10));
        $this$onCreate_u24lambda_u24130_u24lambda_u24129.setStroke(dp(2), Color.argb(200, 255, 255, 255));
        $this$onCreate_u24lambda_u24130.setBackground($this$onCreate_u24lambda_u24130_u24lambda_u24129);
        Unit unit32 = Unit.INSTANCE;
        this.ideasDay = $this$onCreate_u24lambda_u24130;
        FrameLayout frameLayout23 = this.root;
        if (frameLayout23 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout23 = null;
        }
        ImageView imageView6 = this.ideasDay;
        if (imageView6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ideasDay");
            imageView6 = null;
        }
        FrameLayout.LayoutParams $this$onCreate_u24lambda_u24131 = new FrameLayout.LayoutParams(dp(320), dp(160), 8388693);
        $this$onCreate_u24lambda_u24131.setMarginEnd(dp(24));
        $this$onCreate_u24lambda_u24131.bottomMargin = dp(100);
        Unit unit33 = Unit.INSTANCE;
        frameLayout23.addView(imageView6, $this$onCreate_u24lambda_u24131);
        OverlayView overlayView3 = this.overlay;
        if (overlayView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("overlay");
            overlayView3 = null;
        }
        overlayView3.setVisibility(0);
        SurfaceView surfaceView3 = this.videoView;
        if (surfaceView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("videoView");
            surfaceView = null;
        } else {
            surfaceView = surfaceView3;
        }
        surfaceView.setVisibility(0);
        updateTheme();
        this.ui.post(this.uiTick);
        discover();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$31(final MainActivity this$0) {
        if (this$0.isFinishing()) {
            return;
        }
        new AlertDialog.Builder(this$0).setTitle("Restore your settings?").setMessage("StarView looks newly installed. If you made a backup (Download/StarView), pick it to bring back your layout and settings.").setPositiveButton("Choose backup…", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda10
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.onCreate$lambda$31$lambda$28(MainActivity.this, dialogInterface, i);
            }
        }).setNegativeButton("Not now", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda12
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.this.backupArmed = true;
            }
        }).setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda13
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                MainActivity.this.backupArmed = true;
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$31$lambda$28(MainActivity this$0, DialogInterface dialogInterface, int i) {
        Object m1191constructorimpl;
        try {
            Result.Companion companion = Result.Companion;
            this$0.pickBackup.launch(new String[]{"application/json", "text/plain", "application/octet-stream"});
            m1191constructorimpl = Result.m1191constructorimpl(Unit.INSTANCE);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            m1191constructorimpl = Result.m1191constructorimpl(ResultKt.createFailure(th));
        }
        if (Result.m1194exceptionOrNullimpl(m1191constructorimpl) != null) {
            this$0.backupArmed = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$33(MainActivity this$0) {
        try {
            FilesKt.deleteRecursively(new File(this$0.getFilesDir(), "models"));
        } catch (Exception e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$37(final MainActivity this$0) {
        this$0.ui.post(new Runnable() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.onCreate$lambda$37$lambda$36(MainActivity.this);
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$37$lambda$36(MainActivity this$0) {
        SideView sideView = this$0.pipLeft;
        SideView sideView2 = null;
        if (sideView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pipLeft");
            sideView = null;
        }
        sideView.invalidate();
        SideView sideView3 = this$0.pipRight;
        if (sideView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pipRight");
        } else {
            sideView2 = sideView3;
        }
        sideView2.invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0093  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void onCreate$lambda$40(final MainActivity this$0, View it) {
        String str;
        String str2;
        String st = Telemetry.pStr$default(this$0.telemetry, "ChestnutState", null, 2, null);
        String why = Telemetry.Companion.chestnutWhy(Telemetry.pStr$default(this$0.telemetry, "ChestnutWhy", null, 2, null));
        if (Intrinsics.areEqual(st, "failed") || this$0.egpuFallback) {
            new AlertDialog.Builder(this$0).setTitle("Big model (eGPU) failed").setMessage("Driving on the small model" + (why.length() > 0 ? " (" + why + ")" : "") + ".\n\nRestart openpilot to reload it? The comma goes offroad and comes back by itself (about 20 s). Do it parked or stopped.").setPositiveButton("Restart", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda26
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    MainActivity.onCreate$lambda$40$lambda$38(MainActivity.this, dialogInterface, i);
                }
            }).setNegativeButton("Not now", (DialogInterface.OnClickListener) null).show();
            return;
        }
        MainActivity mainActivity = this$0;
        switch (st.hashCode()) {
            case -1422950650:
                if (st.equals("active")) {
                    str2 = "Driving on the big model (eGPU)";
                    break;
                }
                str = st;
                if (str.length() == 0) {
                    str = "unknown";
                }
                str2 = "eGPU: " + ((Object) str);
                break;
            case -1281977283:
                if (st.equals("failed")) {
                    str2 = "Big model (eGPU) failed - driving on the small model" + (why.length() > 0 ? ": " + why : "");
                    break;
                }
                str = st;
                if (str.length() == 0) {
                }
                str2 = "eGPU: " + ((Object) str);
                break;
            case 336650556:
                if (st.equals("loading")) {
                    str2 = "Big model (eGPU) loading";
                    break;
                }
                str = st;
                if (str.length() == 0) {
                }
                str2 = "eGPU: " + ((Object) str);
                break;
            default:
                str = st;
                if (str.length() == 0) {
                }
                str2 = "eGPU: " + ((Object) str);
                break;
        }
        Toast.makeText(mainActivity, str2, 1).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$40$lambda$38(MainActivity this$0, DialogInterface dialogInterface, int i) {
        DriveState.restart$default(DriveState.INSTANCE, this$0, null, 2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$43(MainActivity this$0, JSONObject req) {
        Intrinsics.checkNotNullParameter(req, "req");
        DataClient dataClient = this$0.data;
        if (dataClient == null) {
            Intrinsics.throwUninitializedPropertyAccessException("data");
            dataClient = null;
        }
        dataClient.control(req);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$44(MainActivity this$0, SlowReason.Event e) {
        Intrinsics.checkNotNullParameter(e, "e");
        SlowReasonView.Companion companion = SlowReasonView.Companion;
        Context applicationContext = this$0.getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        companion.log(applicationContext, e);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Triple onCreate$lambda$45(MainActivity this$0) {
        return new Triple(Double.valueOf(this$0.telemetry.getGpsLat()), Double.valueOf(this$0.telemetry.getGpsLon()), Float.valueOf(this$0.telemetry.getVEgo() * 2.23694f));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$46(MainActivity this$0, LaneBrain.Event e) {
        Intrinsics.checkNotNullParameter(e, "e");
        LaneCardView.Companion companion = LaneCardView.Companion;
        Context applicationContext = this$0.getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        companion.log(applicationContext, this$0.laneTracker, e, this$0.telemetry.getGpsLat(), this$0.telemetry.getGpsLon(), this$0.telemetry.getVEgo() * 2.23694f);
        LaneCardView laneCardView = this$0.laneCard;
        if (laneCardView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("laneCard");
            laneCardView = null;
        }
        if (laneCardView.getVisibility() == 0) {
            LaneShots.request$default(this$0.getLaneShots(), LaneShots.Companion.caption(this$0.laneTracker, e.getText(), this$0.telemetry.getVEgo() * 2.23694f), LaneShots.Why.EVENT, null, 4, null);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List onCreate$lambda$47(MainActivity this$0) {
        return this$0.laneFeed.getRamps();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final SceneView onCreate$lambda$49(MainActivity this$0) {
        SceneView it = this$0.scene;
        if (it != null) {
            if (this$0.sceneOn && it.getVisibility() == 0) {
                return it;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Pair onCreate$lambda$50(MainActivity this$0) {
        return TuplesKt.to(this$0.laneTracker.laneCount().getFirst(), this$0.laneTracker.laneFromLeft().getFirst());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final LaneBrain.Road onCreate$lambda$51(MainActivity this$0) {
        return this$0.laneTracker.getRoad();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List onCreate$lambda$52(MainActivity this$0) {
        return this$0.laneFeed.getRamps();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String onCreate$lambda$54(MainActivity this$0) {
        LaneModel laneModel;
        LaneModel.Answer it;
        String code;
        Detector detector = this$0.detectorRef;
        if (detector != null && (laneModel = detector.getLaneModel()) != null && (it = laneModel.getAnswer()) != null) {
            if (!(System.currentTimeMillis() - it.getAtMs() < 5000)) {
                it = null;
            }
            if (it != null && (code = it.code()) != null) {
                return code;
            }
        }
        return "";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String onCreate$lambda$55(MainActivity this$0) {
        Pair<Integer, Double> laneFromLeft = this$0.laneTracker.laneFromLeft();
        int n = laneFromLeft.component1().intValue();
        double q = laneFromLeft.component2().doubleValue();
        int m = this$0.laneTracker.laneCount().component1().intValue();
        String format = String.format(Locale.US, "%.2f", Arrays.copyOf(new Object[]{Double.valueOf(q)}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return n + "," + m + "," + format;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Pair onCreate$lambda$56(MainActivity this$0) {
        return TuplesKt.to(this$0.laneAhead.text(this$0.laneTracker.getOdo(), this$0.telemetry.isMetric()), Boolean.valueOf(this$0.laneAhead.getStage() == LaneAhead.Stage.URGENT));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$57(MainActivity this$0, String text, boolean pic) {
        Intrinsics.checkNotNullParameter(text, "text");
        LaneCardView.Companion companion = LaneCardView.Companion;
        Context applicationContext = this$0.getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        companion.log(applicationContext, this$0.laneTracker, new LaneBrain.Event(System.currentTimeMillis(), "[lane ends] " + text), this$0.telemetry.getGpsLat(), this$0.telemetry.getGpsLon(), this$0.telemetry.getVEgo() * 2.23694f);
        if (pic) {
            LaneShots.request$default(this$0.getLaneShots(), LaneShots.Companion.caption(this$0.laneTracker, "[lane ends] " + text, this$0.telemetry.getVEgo() * 2.23694f), LaneShots.Why.EVENT, null, 4, null);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$58(MainActivity this$0, File $countsFile) {
        try {
            JSONObject j = new JSONObject();
            for (Map.Entry<Long, Integer> entry : this$0.laneTracker.getMyCounts().entrySet()) {
                long k = entry.getKey().longValue();
                int v = entry.getValue().intValue();
                j.put(String.valueOf(k), v);
            }
            String jSONObject = j.toString();
            Intrinsics.checkNotNullExpressionValue(jSONObject, "toString(...)");
            FilesKt.writeText$default($countsFile, jSONObject, null, 2, null);
        } catch (Exception e) {
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$59(MainActivity this$0, SharedPreferences $prefs, View it) {
        EditText editText = this$0.hostEdit;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("hostEdit");
            editText = null;
        }
        String h = StringsKt.trim((CharSequence) editText.getText().toString()).toString();
        if (h.length() > 0) {
            $prefs.edit().putString("host", h).apply();
            this$0.connectTo(h, 8090);
        }
    }

    /* JADX WARN: Type inference failed for: r2v6, types: [T, java.lang.Boolean] */
    private static final void onCreate$pushTabletMode(MainActivity this$0, Ref.ObjectRef<Boolean> objectRef) {
        UiSettings uiSettings = this$0.settings;
        DataClient dataClient = null;
        if (uiSettings == null) {
            Intrinsics.throwUninitializedPropertyAccessException("settings");
            uiSettings = null;
        }
        boolean on = uiSettings.get("comma_screen_off");
        if (Intrinsics.areEqual(Boolean.valueOf(on), objectRef.element)) {
            return;
        }
        DataClient dataClient2 = this$0.data;
        if (dataClient2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("data");
            dataClient2 = null;
        }
        if (dataClient2.getConnected()) {
            objectRef.element = Boolean.valueOf(on);
            DataClient dataClient3 = this$0.data;
            if (dataClient3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("data");
            } else {
                dataClient = dataClient3;
            }
            dataClient.request("tablet_mode", MapsKt.mapOf(TuplesKt.to("on", Boolean.valueOf(on))), new Function1() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda96
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit onCreate$pushTabletMode$lambda$61;
                    onCreate$pushTabletMode$lambda$61 = MainActivity.onCreate$pushTabletMode$lambda$61((JSONObject) obj);
                    return onCreate$pushTabletMode$lambda$61;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$pushTabletMode$lambda$61(JSONObject it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$62(MainActivity this$0, Ref.ObjectRef $lastTabletMode) {
        onCreate$pushTabletMode(this$0, $lastTabletMode);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$66(final MainActivity this$0, final Ref.ObjectRef $lastTabletMode, final String s, final boolean ok) {
        Intrinsics.checkNotNullParameter(s, "s");
        this$0.ui.post(new Runnable() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda98
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.onCreate$lambda$66$lambda$65(s, this$0, ok, $lastTabletMode);
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$66$lambda$65(String $s, final MainActivity this$0, boolean $ok, Ref.ObjectRef $lastTabletMode) {
        if (!StringsKt.startsWith$default($s, "ack:", false, 2, (Object) null)) {
            this$0.setStatus($s);
            if (!$ok) {
                $lastTabletMode.element = null;
                this$0.scheduleReconnect();
                PairScanner.INSTANCE.promptIfNeeded(this$0, new Function0() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda30
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Unit onCreate$lambda$66$lambda$65$lambda$64;
                        onCreate$lambda$66$lambda$65$lambda$64 = MainActivity.onCreate$lambda$66$lambda$65$lambda$64(MainActivity.this);
                        return onCreate$lambda$66$lambda$65$lambda$64;
                    }
                });
                return;
            } else {
                if (StringsKt.startsWith$default($s, "data: hello", false, 2, (Object) null)) {
                    onCreate$pushTabletMode(this$0, $lastTabletMode);
                    return;
                }
                return;
            }
        }
        Toast.makeText(this$0, StringsKt.removePrefix($s, (CharSequence) "ack: "), 0).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$66$lambda$65$lambda$64(MainActivity this$0) {
        String it = this$0.host;
        if (it != null) {
            this$0.connectTo(it, this$0.port);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$67(MainActivity this$0, View it) {
        this$0.startActivity(new Intent(this$0, (Class<?>) SettingsActivity.class).putExtra("host", this$0.host));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$69(final MainActivity this$0, final String s) {
        Intrinsics.checkNotNullParameter(s, "s");
        this$0.ui.post(new Runnable() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda107
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.this.setStatus(s);
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$71(final MainActivity this$0, final int w, final int h) {
        this$0.ui.post(new Runnable() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda20
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.onCreate$lambda$71$lambda$70(MainActivity.this, w, h);
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$71$lambda$70(MainActivity this$0, int $w, int $h) {
        this$0.frameW = $w;
        this$0.frameH = $h;
        OverlayView overlayView = this$0.overlay;
        if (overlayView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("overlay");
            overlayView = null;
        }
        overlayView.setFrameSize($w, $h);
        this$0.placeVideo();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$73(final MainActivity this$0) {
        this$0.ui.post(new Runnable() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda111
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.this.placeVideo();
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$74(String s) {
        Intrinsics.checkNotNullParameter(s, "s");
        Log.i("StarView/Main", "detector road video " + s);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$75(int i, int i2) {
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$77$lambda$76(MainActivity this$0, Image img) {
        Intrinsics.checkNotNullParameter(img, "img");
        this$0.getDetector().offer(img, false);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$78(String s) {
        Intrinsics.checkNotNullParameter(s, "s");
        Log.i("StarView/Main", "detector wide video " + s);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$79(int i, int i2) {
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$81$lambda$80(MainActivity this$0, Image img) {
        Intrinsics.checkNotNullParameter(img, "img");
        this$0.getDetector().offer(img, true);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$84$lambda$83(MainActivity this$0, View it) {
        UiSettings uiSettings = this$0.settings;
        UiSettings uiSettings2 = null;
        if (uiSettings == null) {
            Intrinsics.throwUninitializedPropertyAccessException("settings");
            uiSettings = null;
        }
        UiSettings uiSettings3 = this$0.settings;
        if (uiSettings3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("settings");
        } else {
            uiSettings2 = uiSettings3;
        }
        uiSettings.set("scene_view", !uiSettings2.get("scene_view"));
        this$0.uiTickNow();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean onCreate$lambda$106(MainActivity this$0) {
        return this$0.telemetry.getVEgo() < 0.5f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$107(MainActivity this$0) {
        this$0.requestPermissions(new String[]{"android.permission.RECORD_AUDIO"}, 84);
        Toast.makeText(this$0, "Allow the microphone, then hold 🎤 again", 1).show();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$127$lambda$125$lambda$124(MainActivity this$0, String $w, View it) {
        Ideas.INSTANCE.road().answerWhy(this$0, $w);
        LinearLayout linearLayout = this$0.ideasWhy;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ideasWhy");
            linearLayout = null;
        }
        linearLayout.setVisibility(8);
    }

    private final void wireControls() {
        OverlayView overlayView;
        MiciDistanceView miciDistanceView = this.personality;
        TextView textView = null;
        if (miciDistanceView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("personality");
            miciDistanceView = null;
        }
        miciDistanceView.setOnTouchListener(new View.OnTouchListener() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda81
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                boolean wireControls$lambda$133;
                wireControls$lambda$133 = MainActivity.wireControls$lambda$133(MainActivity.this, view, motionEvent);
                return wireControls$lambda$133;
            }
        });
        WheelButton wheelButton = this.wheel;
        if (wheelButton == null) {
            Intrinsics.throwUninitializedPropertyAccessException("wheel");
            wheelButton = null;
        }
        wheelButton.setOnTouchListener(new View.OnTouchListener() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda86
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                boolean wireControls$lambda$134;
                wireControls$lambda$134 = MainActivity.wireControls$lambda$134(MainActivity.this, view, motionEvent);
                return wireControls$lambda$134;
            }
        });
        SlcView slcView = this.slc;
        if (slcView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("slc");
            slcView = null;
        }
        slcView.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda87
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.wireControls$lambda$135(MainActivity.this, view);
            }
        });
        final Ref.LongRef navDownNs = new Ref.LongRef();
        NavCardView navCardView = this.navCard;
        if (navCardView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("navCard");
            navCardView = null;
        }
        navCardView.setOnTouchListener(new View.OnTouchListener() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda88
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                boolean wireControls$lambda$136;
                wireControls$lambda$136 = MainActivity.wireControls$lambda$136(Ref.LongRef.this, this, view, motionEvent);
                return wireControls$lambda$136;
            }
        });
        final MainActivity $this$wireControls_u24lambda_u24139 = this;
        final Ref.FloatRef downX = new Ref.FloatRef();
        final Ref.FloatRef downY = new Ref.FloatRef();
        final Ref.BooleanRef armed = new Ref.BooleanRef();
        final float slop = ViewConfiguration.get($this$wireControls_u24lambda_u24139).getScaledTouchSlop() * 2.0f;
        final Runnable open = new Runnable() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda89
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.wireControls$lambda$139$lambda$137(Ref.BooleanRef.this, $this$wireControls_u24lambda_u24139);
            }
        };
        OverlayView overlayView2 = $this$wireControls_u24lambda_u24139.overlay;
        if (overlayView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("overlay");
            overlayView = null;
        } else {
            overlayView = overlayView2;
        }
        overlayView.setOnTouchListener(new View.OnTouchListener() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda90
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                boolean wireControls$lambda$139$lambda$138;
                wireControls$lambda$139$lambda$138 = MainActivity.wireControls$lambda$139$lambda$138(MainActivity.this, downX, downY, armed, open, slop, view, motionEvent);
                return wireControls$lambda$139$lambda$138;
            }
        });
        this.hud = new HudLayout(this);
        HudLayout hudLayout = this.hud;
        if (hudLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("hud");
            hudLayout = null;
        }
        FrameLayout frameLayout = this.root;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout = null;
        }
        hudLayout.adopt(frameLayout);
        HudLayout hudLayout2 = this.hud;
        if (hudLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("hud");
            hudLayout2 = null;
        }
        hudLayout2.load(this.night ? "night" : "day");
        maskIntent(getIntent());
        shareIntent(getIntent());
        FrameLayout frameLayout2 = this.root;
        if (frameLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout2 = null;
        }
        frameLayout2.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda91
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                MainActivity.this.layoutHud();
            }
        });
        ((Button) findViewById(R.id.sbSettings)).setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda92
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.wireControls$lambda$141(MainActivity.this, view);
            }
        });
        ((Button) findViewById(R.id.sbTerminal)).setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda93
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.wireControls$lambda$142(MainActivity.this, view);
            }
        });
        ((Button) findViewById(R.id.sbFiles)).setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda94
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.wireControls$lambda$143(MainActivity.this, view);
            }
        });
        ((Button) findViewById(R.id.sbDisplay)).setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda82
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.wireControls$lambda$144(MainActivity.this, view);
            }
        });
        for (Pair pair : CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to(Integer.valueOf(R.id.dsDefault), "default"), TuplesKt.to(Integer.valueOf(R.id.dsOnroad), "onroad"), TuplesKt.to(Integer.valueOf(R.id.dsOffroad), "offroad")})) {
            int id = ((Number) pair.component1()).intValue();
            final String mode = (String) pair.component2();
            ((Button) findViewById(id)).setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda83
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MainActivity.wireControls$lambda$146(MainActivity.this, mode, view);
                }
            });
        }
        TextView textView2 = this.speedTv;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("speedTv");
        } else {
            textView = textView2;
        }
        textView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda85
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                boolean wireControls$lambda$147;
                wireControls$lambda$147 = MainActivity.wireControls$lambda$147(MainActivity.this, view);
                return wireControls$lambda$147;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0098, code lost:
    
        return true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final boolean wireControls$lambda$133(final MainActivity mainActivity, View view, MotionEvent motionEvent) {
        MiciDistanceView miciDistanceView = null;
        MiciDistanceView miciDistanceView2 = null;
        DataClient dataClient = null;
        switch (motionEvent.getActionMasked()) {
            case 0:
                MiciDistanceView miciDistanceView3 = mainActivity.personality;
                if (miciDistanceView3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("personality");
                } else {
                    miciDistanceView = miciDistanceView3;
                }
                miciDistanceView.setDown(true);
                view.invalidate();
                break;
            case 1:
                MiciDistanceView miciDistanceView4 = mainActivity.personality;
                if (miciDistanceView4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("personality");
                    miciDistanceView4 = null;
                }
                miciDistanceView4.setDown(false);
                view.invalidate();
                final int next = Personality.INSTANCE.next(mainActivity.telemetry.getPersonality());
                DataClient dataClient2 = mainActivity.data;
                if (dataClient2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("data");
                    dataClient2 = null;
                }
                if (!dataClient2.getConnected()) {
                    Toast.makeText(mainActivity, "Not connected to the comma", 0).show();
                    break;
                } else {
                    DataClient dataClient3 = mainActivity.data;
                    if (dataClient3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("data");
                    } else {
                        dataClient = dataClient3;
                    }
                    dataClient.request("param", MapsKt.mapOf(TuplesKt.to("key", "LongitudinalPersonality"), TuplesKt.to("value", Integer.valueOf(next))), new Function1() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda84
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            Unit wireControls$lambda$133$lambda$132;
                            wireControls$lambda$133$lambda$132 = MainActivity.wireControls$lambda$133$lambda$132(MainActivity.this, next, (JSONObject) obj);
                            return wireControls$lambda$133$lambda$132;
                        }
                    });
                    break;
                }
            case 3:
                MiciDistanceView miciDistanceView5 = mainActivity.personality;
                if (miciDistanceView5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("personality");
                } else {
                    miciDistanceView2 = miciDistanceView5;
                }
                miciDistanceView2.setDown(false);
                view.invalidate();
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit wireControls$lambda$133$lambda$132(MainActivity this$0, int $next, JSONObject ack) {
        Intrinsics.checkNotNullParameter(ack, "ack");
        if (ack.optBoolean("ok")) {
            this$0.telemetry.setPersonality(Personality.INSTANCE.getNAMES()[$next]);
            MiciDistanceView miciDistanceView = this$0.personality;
            if (miciDistanceView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("personality");
                miciDistanceView = null;
            }
            miciDistanceView.invalidate();
            Toast.makeText(this$0, "Following distance: " + Personality.INSTANCE.getLABELS()[$next], 0).show();
        } else {
            Toast.makeText(this$0, ack.optString("error", "The comma refused"), 0).show();
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x005a, code lost:
    
        return true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final boolean wireControls$lambda$134(MainActivity mainActivity, View view, MotionEvent motionEvent) {
        WheelButton wheelButton = null;
        WheelButton wheelButton2 = null;
        DataClient dataClient = null;
        switch (motionEvent.getActionMasked()) {
            case 0:
                WheelButton wheelButton3 = mainActivity.wheel;
                if (wheelButton3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("wheel");
                } else {
                    wheelButton = wheelButton3;
                }
                wheelButton.setDown(true);
                view.invalidate();
                break;
            case 1:
                WheelButton wheelButton4 = mainActivity.wheel;
                if (wheelButton4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("wheel");
                    wheelButton4 = null;
                }
                wheelButton4.setDown(false);
                view.invalidate();
                DataClient dataClient2 = mainActivity.data;
                if (dataClient2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("data");
                } else {
                    dataClient = dataClient2;
                }
                dataClient.control(new JSONObject(MapsKt.mapOf(TuplesKt.to("action", "exp_toggle"))));
                break;
            case 3:
                WheelButton wheelButton5 = mainActivity.wheel;
                if (wheelButton5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("wheel");
                } else {
                    wheelButton2 = wheelButton5;
                }
                wheelButton2.setDown(false);
                view.invalidate();
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void wireControls$lambda$135(MainActivity this$0, View it) {
        Telemetry t = this$0.telemetry;
        SlcActionsView slcActionsView = this$0.slcActions;
        DataClient dataClient = null;
        if (slcActionsView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("slcActions");
            slcActionsView = null;
        }
        if (slcActionsView.openPrompt()) {
            return;
        }
        if (Telemetry.slcLive$default(t, 0L, 1, null) != null) {
            if (t.getSp7()) {
                t.getUi().set("slc_sources_sp7", true ^ t.s("slc_sources_sp7"));
                return;
            }
            DataClient dataClient2 = this$0.data;
            if (dataClient2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("data");
            } else {
                dataClient = dataClient2;
            }
            dataClient.control(new JSONObject(MapsKt.mapOf(TuplesKt.to("action", "param"), TuplesKt.to("key", "SpeedLimitSources"), TuplesKt.to("value", Boolean.valueOf(true ^ t.pBool("SpeedLimitSources", false))))));
            return;
        }
        if (t.getSlcChanged() && t.getSlcUnconfirmed() > 0.0f) {
            DataClient dataClient3 = this$0.data;
            if (dataClient3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("data");
            } else {
                dataClient = dataClient3;
            }
            dataClient.control(new JSONObject(MapsKt.mapOf(TuplesKt.to("action", "param"), TuplesKt.to("key", "SpeedLimitAccepted"), TuplesKt.to("value", true), TuplesKt.to("memory", true))));
            return;
        }
        if (!t.getSp7()) {
            DataClient dataClient4 = this$0.data;
            if (dataClient4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("data");
            } else {
                dataClient = dataClient4;
            }
            dataClient.control(new JSONObject(MapsKt.mapOf(TuplesKt.to("action", "param"), TuplesKt.to("key", "SpeedLimitSources"), TuplesKt.to("value", Boolean.valueOf(true ^ t.pBool("SpeedLimitSources", false))))));
            return;
        }
        t.getUi().set("slc_sources_sp7", true ^ t.s("slc_sources_sp7"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0096, code lost:
    
        return true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final boolean wireControls$lambda$136(Ref.LongRef $navDownNs, MainActivity this$0, View view, MotionEvent e) {
        switch (e.getActionMasked()) {
            case 0:
                $navDownNs.element = System.nanoTime();
                break;
            case 1:
                DataClient dataClient = null;
                if (Telemetry.navCard$default(this$0.telemetry, 0L, 1, null) != null) {
                    if (System.nanoTime() - $navDownNs.element < 650000000) {
                        DataClient dataClient2 = this$0.data;
                        if (dataClient2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("data");
                        } else {
                            dataClient = dataClient2;
                        }
                        dataClient.control(new JSONObject(MapsKt.mapOf(TuplesKt.to("action", "param"), TuplesKt.to("key", "NavInstructionCollapsed"), TuplesKt.to("value", Boolean.valueOf(!this$0.telemetry.mBool("NavInstructionCollapsed", false))), TuplesKt.to("memory", true))));
                        break;
                    } else {
                        DataClient dataClient3 = this$0.data;
                        if (dataClient3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("data");
                        } else {
                            dataClient = dataClient3;
                        }
                        dataClient.control(new JSONObject(MapsKt.mapOf(TuplesKt.to("action", "nav_cancel"))));
                        break;
                    }
                }
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void wireControls$lambda$139$lambda$137(Ref.BooleanRef $armed, MainActivity $this_run) {
        if ($armed.element) {
            $armed.element = false;
            $this_run.startHudEdit();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean wireControls$lambda$139$lambda$138(MainActivity $this_run, Ref.FloatRef $downX, Ref.FloatRef $downY, Ref.BooleanRef $armed, Runnable $open, float $slop, View v, MotionEvent e) {
        if ($this_run.sceneOn) {
            return false;
        }
        switch (e.getActionMasked()) {
            case 0:
                $downX.element = e.getX();
                $downY.element = e.getY();
                EdgeGuard edgeGuard = EdgeGuard.INSTANCE;
                Intrinsics.checkNotNull(v);
                $armed.element = !edgeGuard.inSystemGestureZone(v, e.getX(), e.getY());
                $this_run.ui.removeCallbacks($open);
                if ($armed.element) {
                    $this_run.ui.postDelayed($open, 800L);
                }
                return true;
            case 1:
            default:
                $armed.element = false;
                $this_run.ui.removeCallbacks($open);
                return true;
            case 2:
                if ($armed.element && (Math.abs(e.getX() - $downX.element) > $slop || Math.abs(e.getY() - $downY.element) > $slop)) {
                    $armed.element = false;
                    $this_run.ui.removeCallbacks($open);
                }
                return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void wireControls$lambda$141(MainActivity this$0, View it) {
        LinearLayout linearLayout = this$0.sidebar;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sidebar");
            linearLayout = null;
        }
        linearLayout.setVisibility(8);
        this$0.startActivity(new Intent(this$0, (Class<?>) SettingsActivity.class).putExtra("host", this$0.host).putExtra("tab", "device"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void wireControls$lambda$142(MainActivity this$0, View it) {
        LinearLayout linearLayout = this$0.sidebar;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sidebar");
            linearLayout = null;
        }
        linearLayout.setVisibility(8);
        this$0.startActivity(new Intent(this$0, (Class<?>) SettingsActivity.class).putExtra("host", this$0.host).putExtra("tab", "terminal"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void wireControls$lambda$143(MainActivity this$0, View it) {
        LinearLayout linearLayout = this$0.sidebar;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sidebar");
            linearLayout = null;
        }
        linearLayout.setVisibility(8);
        this$0.startActivity(new Intent(this$0, (Class<?>) FilesActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void wireControls$lambda$144(MainActivity this$0, View it) {
        LinearLayout linearLayout = this$0.sidebar;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sidebar");
            linearLayout = null;
        }
        linearLayout.setVisibility(8);
        this$0.startActivity(new Intent(this$0, (Class<?>) SettingsActivity.class).putExtra("host", this$0.host));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void wireControls$lambda$146(final MainActivity this$0, String $mode, View it) {
        DataClient dataClient = this$0.data;
        if (dataClient == null) {
            Intrinsics.throwUninitializedPropertyAccessException("data");
            dataClient = null;
        }
        dataClient.request("drive_state", MapsKt.mapOf(TuplesKt.to("mode", $mode)), new Function1() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit wireControls$lambda$146$lambda$145;
                wireControls$lambda$146$lambda$145 = MainActivity.wireControls$lambda$146$lambda$145(MainActivity.this, (JSONObject) obj);
                return wireControls$lambda$146$lambda$145;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit wireControls$lambda$146$lambda$145(MainActivity this$0, JSONObject ack) {
        Intrinsics.checkNotNullParameter(ack, "ack");
        if (!ack.optBoolean("ok")) {
            Toast.makeText(this$0, ack.optString("error", "rejected"), 0).show();
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean wireControls$lambda$147(MainActivity this$0, View it) {
        this$0.statsVisible = !this$0.statsVisible;
        UiSettings uiSettings = this$0.settings;
        if (uiSettings == null) {
            Intrinsics.throwUninitializedPropertyAccessException("settings");
            uiSettings = null;
        }
        uiSettings.set("stats", this$0.statsVisible);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setStatus(String s) {
        if (Intrinsics.areEqual(s, this.lastStatus)) {
            return;
        }
        this.lastStatus = s;
        TextView textView = this.statusTv;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("statusTv");
            textView = null;
        }
        textView.setText(s);
        Log.i("StarView/Main", s);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void discover() {
        if (this.searching) {
            return;
        }
        this.searching = true;
        setStatus("searching for comma…");
        new Thread(new Runnable() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda105
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.discover$lambda$153(MainActivity.this);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void discover$lambda$153(final MainActivity this$0) {
        final Discovery.Found found = Discovery.INSTANCE.find(2000);
        this$0.ui.post(new Runnable() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda23
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.discover$lambda$153$lambda$152(MainActivity.this, found);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void discover$lambda$153$lambda$152(final MainActivity mainActivity, Discovery.Found found) {
        mainActivity.searching = false;
        if (found != null && Link.INSTANCE.getPaired() && !found.getVerified()) {
            Link.INSTANCE.setPairingRequired(true);
            mainActivity.setStatus(found.getHost() + " doesn't have this tablet's pairing: not connecting. On the comma: Settings › starview › show qr, then scan it");
            PairScanner.INSTANCE.promptIfNeeded(mainActivity, new Function0() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda6
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit discover$lambda$153$lambda$152$lambda$148;
                    discover$lambda$153$lambda$152$lambda$148 = MainActivity.discover$lambda$153$lambda$152$lambda$148(MainActivity.this);
                    return discover$lambda$153$lambda$152$lambda$148;
                }
            });
            mainActivity.ui.postDelayed(new Runnable() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    MainActivity.this.discover();
                }
            }, 5000L);
            return;
        }
        LinearLayout linearLayout = null;
        EditText editText = null;
        if (found != null) {
            EditText editText2 = mainActivity.hostEdit;
            if (editText2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("hostEdit");
            } else {
                editText = editText2;
            }
            editText.setText(found.getHost());
            mainActivity.getSharedPreferences("starview", 0).edit().putString("host", found.getHost()).apply();
            String interfaceFor = Discovery.INSTANCE.interfaceFor(found.getHost());
            if (interfaceFor.length() == 0) {
                interfaceFor = "?";
            }
            mainActivity.linkIf = interfaceFor;
            mainActivity.setStatus("found " + found.getDongleId() + " (" + found.getBranch() + " " + found.getCommit() + ") at " + found.getHost() + " via " + mainActivity.linkIf);
            mainActivity.connectTo(found.getHost(), found.getPort());
            return;
        }
        EditText editText3 = mainActivity.hostEdit;
        if (editText3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("hostEdit");
            editText3 = null;
        }
        String obj = StringsKt.trim((CharSequence) editText3.getText().toString()).toString();
        if (!(obj.length() > 0)) {
            mainActivity.setStatus("comma not found. Is USB tethering on?");
            LinearLayout linearLayout2 = mainActivity.connectBar;
            if (linearLayout2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("connectBar");
            } else {
                linearLayout = linearLayout2;
            }
            linearLayout.setVisibility(0);
            mainActivity.ui.postDelayed(new Runnable() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    MainActivity.this.discover();
                }
            }, 5000L);
            return;
        }
        mainActivity.setStatus("no reply to discovery; trying " + obj);
        mainActivity.connectTo(obj, 8090);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit discover$lambda$153$lambda$152$lambda$148(MainActivity this$0) {
        this$0.discover();
        return Unit.INSTANCE;
    }

    private final void connectTo(final String h, final int p) {
        this.host = h;
        this.port = p;
        new Thread(new Runnable() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda17
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.connectTo$lambda$157(h, p, this);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void connectTo$lambda$157(final String $h, final int $p, final MainActivity this$0) {
        final Link.Auth auth = Link.INSTANCE.ensureSession($h, $p);
        this$0.ui.post(new Runnable() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda32
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.connectTo$lambda$157$lambda$156(MainActivity.this, $h, auth, $p);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void connectTo$lambda$157$lambda$156(final MainActivity this$0, String $h, Link.Auth $auth, int $p) {
        if (Intrinsics.areEqual(this$0.host, $h)) {
            if ($auth == Link.Auth.NOT_OUR_COMMA) {
                this$0.setStatus($h + " didn't prove it's your paired comma: not connecting. If the comma was unpaired, scan its QR code again");
                this$0.scheduleReconnect();
                PairScanner.INSTANCE.promptIfNeeded(this$0, new Function0() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda18
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Unit connectTo$lambda$157$lambda$156$lambda$155;
                        connectTo$lambda$157$lambda$156$lambda$155 = MainActivity.connectTo$lambda$157$lambda$156$lambda$155(MainActivity.this);
                        return connectTo$lambda$157$lambda$156$lambda$155;
                    }
                });
            } else {
                Link.INSTANCE.setHost($h);
                DataClient dataClient = this$0.data;
                if (dataClient == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("data");
                    dataClient = null;
                }
                dataClient.connect($h, $p);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit connectTo$lambda$157$lambda$156$lambda$155(MainActivity this$0) {
        String it = this$0.host;
        if (it != null) {
            this$0.connectTo(it, this$0.port);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void checkUsbReturn() {
        String h = this.host;
        if (h == null || this.usbProbe || this.searching || Discovery.INSTANCE.isUsb(Discovery.INSTANCE.interfaceFor(h)) || !Discovery.INSTANCE.usbInterfaceUp()) {
            return;
        }
        this.usbProbe = true;
        new Thread(new Runnable() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda24
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.checkUsbReturn$lambda$160(MainActivity.this);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void checkUsbReturn$lambda$160(final MainActivity this$0) {
        final Discovery.Found found;
        try {
            found = Discovery.INSTANCE.find(1500);
        } catch (Exception e) {
            found = null;
        }
        this$0.ui.post(new Runnable() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda109
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.checkUsbReturn$lambda$160$lambda$159(MainActivity.this, found);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void checkUsbReturn$lambda$160$lambda$159(MainActivity this$0, Discovery.Found $found) {
        this$0.usbProbe = false;
        if ($found == null || Intrinsics.areEqual($found.getHost(), this$0.host) || !Discovery.INSTANCE.isUsb(Discovery.INSTANCE.interfaceFor($found.getHost()))) {
            return;
        }
        if (Link.INSTANCE.getPaired() && !$found.getVerified()) {
            return;
        }
        Log.i("StarView/Main", "USB is back: moving from " + this$0.host + " to " + $found.getHost());
        this$0.stopVideo();
        this$0.syncDetector(false);
        this$0.getSharedPreferences("starview", 0).edit().putString("host", $found.getHost()).apply();
        EditText editText = this$0.hostEdit;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("hostEdit");
            editText = null;
        }
        editText.setText($found.getHost());
        String interfaceFor = Discovery.INSTANCE.interfaceFor($found.getHost());
        if (interfaceFor.length() == 0) {
            interfaceFor = "?";
        }
        this$0.linkIf = interfaceFor;
        this$0.setStatus("back on USB: " + $found.getHost() + " via " + this$0.linkIf);
        this$0.connectTo($found.getHost(), $found.getPort());
    }

    private final void scheduleReconnect() {
        if (this.reconnectPending) {
            return;
        }
        this.reconnectPending = true;
        stopVideo();
        this.ui.postDelayed(new Runnable() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda62
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.scheduleReconnect$lambda$161(MainActivity.this);
            }
        }, 2000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void scheduleReconnect$lambda$161(MainActivity this$0) {
        this$0.reconnectPending = false;
        this$0.discover();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void startVideo() {
        String h = this.host;
        if (h == null || !this.surfaceReady || this.videoConnected) {
            return;
        }
        String cam = this.telemetry.s("wide_camera") ? "wide" : "road";
        OverlayView overlayView = this.overlay;
        SurfaceView surfaceView = null;
        if (overlayView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("overlay");
            overlayView = null;
        }
        overlayView.getProj().setWide(Intrinsics.areEqual(cam, "wide"));
        VideoClient videoClient = this.video;
        if (videoClient == null) {
            Intrinsics.throwUninitializedPropertyAccessException("video");
            videoClient = null;
        }
        int i = this.port;
        SurfaceView surfaceView2 = this.videoView;
        if (surfaceView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("videoView");
        } else {
            surfaceView = surfaceView2;
        }
        videoClient.connect(h, i, cam, surfaceView.getHolder().getSurface());
        this.videoConnected = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void stopVideo() {
        if (this.videoConnected) {
            VideoClient videoClient = this.video;
            if (videoClient == null) {
                Intrinsics.throwUninitializedPropertyAccessException("video");
                videoClient = null;
            }
            videoClient.close();
            this.videoConnected = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0029, code lost:
    
        r5 = false;
     */
    /* JADX WARN: Removed duplicated region for block: B:18:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00be  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00e1 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00f6 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:74:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0042  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void syncDetector(boolean want) {
        boolean wantRoad;
        boolean wantWide;
        boolean z;
        if (!want && this.detectorRef == null) {
            return;
        }
        String h = this.host;
        boolean shownWide = this.telemetry.s("wide_camera");
        getDetector().setShownWide(shownWide);
        MaskEditor ed = this.maskEditor;
        if (want && h != null) {
            boolean z2 = ed != null ? true : true;
            if (z2) {
                wantRoad = true;
                if (want && h != null) {
                    if (ed == null) {
                        z = ed.getWide();
                    } else {
                        z = this.sceneOn || shownWide;
                    }
                    if (z) {
                        wantWide = true;
                        VideoClient videoClient = null;
                        if (wantRoad) {
                            VideoClient videoClient2 = this.detRoad;
                            if (videoClient2 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("detRoad");
                                videoClient2 = null;
                            }
                            if (!videoClient2.getRunning()) {
                                VideoClient videoClient3 = this.detRoad;
                                if (videoClient3 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("detRoad");
                                    videoClient3 = null;
                                }
                                Intrinsics.checkNotNull(h);
                                videoClient3.connect(h, this.port, "road", null);
                            }
                        }
                        if (!wantRoad) {
                            VideoClient videoClient4 = this.detRoad;
                            if (videoClient4 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("detRoad");
                                videoClient4 = null;
                            }
                            if (videoClient4.getRunning()) {
                                VideoClient videoClient5 = this.detRoad;
                                if (videoClient5 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("detRoad");
                                    videoClient5 = null;
                                }
                                videoClient5.close();
                                getDetector().clearSource(false);
                            }
                        }
                        if (wantWide) {
                            VideoClient videoClient6 = this.detWide;
                            if (videoClient6 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("detWide");
                                videoClient6 = null;
                            }
                            if (!videoClient6.getRunning()) {
                                VideoClient videoClient7 = this.detWide;
                                if (videoClient7 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("detWide");
                                    videoClient7 = null;
                                }
                                Intrinsics.checkNotNull(h);
                                videoClient7.connect(h, this.port, "wide", null);
                            }
                        }
                        if (!wantWide) {
                            VideoClient videoClient8 = this.detWide;
                            if (videoClient8 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("detWide");
                                videoClient8 = null;
                            }
                            if (videoClient8.getRunning()) {
                                VideoClient videoClient9 = this.detWide;
                                if (videoClient9 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("detWide");
                                } else {
                                    videoClient = videoClient9;
                                }
                                videoClient.close();
                                getDetector().clearSource(true);
                            }
                        }
                        if ((!wantRoad || wantWide) && !getDetector().getRunning()) {
                            getDetector().start();
                        }
                        if (wantRoad || wantWide || !getDetector().getRunning()) {
                            return;
                        }
                        getDetector().stop();
                        return;
                    }
                }
                wantWide = false;
                VideoClient videoClient10 = null;
                if (wantRoad) {
                }
                if (!wantRoad) {
                }
                if (wantWide) {
                }
                if (!wantWide) {
                }
                if (!wantRoad) {
                }
                getDetector().start();
                if (wantRoad) {
                    return;
                } else {
                    return;
                }
            }
        }
        wantRoad = false;
        if (want) {
            if (ed == null) {
            }
            if (z) {
            }
        }
        wantWide = false;
        VideoClient videoClient102 = null;
        if (wantRoad) {
        }
        if (!wantRoad) {
        }
        if (wantWide) {
        }
        if (!wantWide) {
        }
        if (!wantRoad) {
        }
        getDetector().start();
        if (wantRoad) {
        }
    }

    private final OkHttpClient getStorageHttp() {
        return (OkHttpClient) this.storageHttp.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final OkHttpClient storageHttp_delegate$lambda$162() {
        return new OkHttpClient.Builder().addInterceptor(Link.INSTANCE.getAuthInterceptor()).connectTimeout(3L, TimeUnit.SECONDS).readTimeout(5L, TimeUnit.SECONDS).build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void fetchStorage() {
        final String h = this.host;
        if (h == null) {
            return;
        }
        new Thread(new Runnable() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda73
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.fetchStorage$lambda$165(MainActivity.this, h);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void fetchStorage$lambda$165(final MainActivity this$0, String $h) {
        try {
            Response execute = this$0.getStorageHttp().newCall(new Request.Builder().url("http://" + $h + ":" + this$0.port + "/storage").build()).execute();
            try {
                Response r = execute;
                if (r.isSuccessful()) {
                    ResponseBody body = r.body();
                    Intrinsics.checkNotNull(body);
                    final JSONObject j = new JSONObject(body.string());
                    this$0.ui.post(new Runnable() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda110
                        @Override // java.lang.Runnable
                        public final void run() {
                            MainActivity.fetchStorage$lambda$165$lambda$164$lambda$163(MainActivity.this, j);
                        }
                    });
                }
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(execute, null);
            } finally {
            }
        } catch (Exception e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void fetchStorage$lambda$165$lambda$164$lambda$163(MainActivity this$0, JSONObject $j) {
        OffroadHome offroadHome = this$0.offroadHome;
        if (offroadHome == null) {
            Intrinsics.throwUninitializedPropertyAccessException("offroadHome");
            offroadHome = null;
        }
        offroadHome.setStorage($j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void uiTickNow() {
        this.ui.removeCallbacks(this.uiTick);
        this.ui.post(this.uiTick);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:101:0x01c7  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x01d0  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x01dd  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x01e6  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x020e  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x021b  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0221  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x022b  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x0241  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x0257  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x026c  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x0273  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x0276  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x0270  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x0223  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x01ac  */
    /* JADX WARN: Removed duplicated region for block: B:175:0x017e  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00ba  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x010b  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0136  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0149  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x01a2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void syncScene(boolean onroad) {
        boolean z;
        SceneView sceneView;
        SceneView sceneView2;
        Object[] objArr;
        Detector detector;
        LaneMarks laneMarks;
        SceneView sceneView3;
        SceneView sceneView4;
        SceneView sceneView5;
        Detector detector2;
        TextView textView;
        TextView textView2;
        LaneModel laneModel;
        UiSettings uiSettings;
        UiSettings uiSettings2;
        FrameLayout frameLayout;
        FrameLayout frameLayout2;
        OverlayView overlayView;
        TextView textView3 = null;
        Object[] objArr2 = 0;
        Object[] objArr3 = 0;
        if (onroad) {
            UiSettings uiSettings3 = this.settings;
            if (uiSettings3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("settings");
                uiSettings3 = null;
            }
            if (uiSettings3.get("scene_view")) {
                z = true;
                if (z && this.scene == null) {
                    SceneView sceneView6 = new SceneView(this, objArr3 == true ? 1 : 0, 2, objArr2 == true ? 1 : 0);
                    sceneView6.setT(this.telemetry);
                    uiSettings = this.settings;
                    if (uiSettings == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("settings");
                        uiSettings = null;
                    }
                    sceneView6.setDistance(uiSettings.getFloat("scene_distance4", 26.0f));
                    uiSettings2 = this.settings;
                    if (uiSettings2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("settings");
                        uiSettings2 = null;
                    }
                    sceneView6.setTiltDeg(uiSettings2.getFloat("scene_tilt4", 17.0f));
                    sceneView6.setOnViewChanged(new Function2() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda101
                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            Unit syncScene$lambda$168$lambda$166;
                            syncScene$lambda$168$lambda$166 = MainActivity.syncScene$lambda$168$lambda$166(MainActivity.this, ((Float) obj).floatValue(), ((Float) obj2).floatValue());
                            return syncScene$lambda$168$lambda$166;
                        }
                    });
                    sceneView6.setOnLongPress(new Function0() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda102
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            Unit syncScene$lambda$168$lambda$167;
                            syncScene$lambda$168$lambda$167 = MainActivity.syncScene$lambda$168$lambda$167(MainActivity.this);
                            return syncScene$lambda$168$lambda$167;
                        }
                    });
                    sceneView6.setDetector(getDetector());
                    frameLayout = this.root;
                    if (frameLayout == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("root");
                        frameLayout = null;
                    }
                    SceneView sceneView7 = sceneView6;
                    frameLayout2 = this.root;
                    if (frameLayout2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("root");
                        frameLayout2 = null;
                    }
                    overlayView = this.overlay;
                    if (overlayView == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("overlay");
                        overlayView = null;
                    }
                    frameLayout.addView(sceneView7, frameLayout2.indexOfChild(overlayView), new FrameLayout.LayoutParams(-1, -1));
                    this.scene = sceneView6;
                }
                sceneView = this.scene;
                if (sceneView != null) {
                    int i = z ? 0 : 8;
                    if (sceneView.getVisibility() != i) {
                        sceneView.setVisibility(i);
                    }
                }
                if (z != this.sceneOn) {
                    this.sceneOn = z;
                    OverlayView overlayView2 = this.overlay;
                    if (overlayView2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("overlay");
                        overlayView2 = null;
                    }
                    overlayView2.setSceneMode(z);
                    OverlayView overlayView3 = this.overlay;
                    if (overlayView3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("overlay");
                        overlayView3 = null;
                    }
                    overlayView3.setLongClickable(!z);
                    OverlayView overlayView4 = this.overlay;
                    if (overlayView4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("overlay");
                        overlayView4 = null;
                    }
                    overlayView4.setClickable(!z);
                    SurfaceView surfaceView = this.videoView;
                    if (surfaceView == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("videoView");
                        surfaceView = null;
                    }
                    surfaceView.setVisibility(z ? 4 : 0);
                    applyRootBg();
                    OverlayView overlayView5 = this.overlay;
                    if (overlayView5 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("overlay");
                        overlayView5 = null;
                    }
                    overlayView5.invalidate();
                }
                sceneView2 = this.scene;
                if (sceneView2 != null) {
                    sceneView2.setNight(this.night);
                    sceneView2.setAlwaysDark(false);
                    sceneView2.setUiStatus(UiStatus.Companion.from(this.telemetry));
                    UiSettings uiSettings4 = this.settings;
                    if (uiSettings4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("settings");
                        uiSettings4 = null;
                    }
                    sceneView2.setShowTraffic(uiSettings4.get("scene_cars"));
                }
                if (this.sceneOn) {
                    LayoutSeg layoutSeg = this.layoutSeg;
                    if (layoutSeg == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("layoutSeg");
                        layoutSeg = null;
                    }
                    if (layoutSeg.getHasModel()) {
                        objArr = true;
                        if (objArr == true) {
                            LayoutSeg layoutSeg2 = this.layoutSeg;
                            if (layoutSeg2 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("layoutSeg");
                                layoutSeg2 = null;
                            }
                            layoutSeg2.start();
                            LayoutSeg layoutSeg3 = this.layoutSeg;
                            if (layoutSeg3 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("layoutSeg");
                                layoutSeg3 = null;
                            }
                            layoutSeg3.setEnabled(true);
                            Detector detector3 = getDetector();
                            LayoutSeg layoutSeg4 = this.layoutSeg;
                            if (layoutSeg4 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("layoutSeg");
                                layoutSeg4 = null;
                            }
                            detector3.setLayout(layoutSeg4);
                            SceneView sceneView8 = this.scene;
                            if (sceneView8 != null) {
                                LayoutSeg layoutSeg5 = this.layoutSeg;
                                if (layoutSeg5 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("layoutSeg");
                                    layoutSeg5 = null;
                                }
                                sceneView8.setLayout(layoutSeg5);
                            }
                        } else {
                            Detector detector4 = this.detectorRef;
                            if ((detector4 != null ? detector4.getLayout() : null) != null) {
                                Detector detector5 = this.detectorRef;
                                if (detector5 != null) {
                                    detector5.setLayout(null);
                                }
                                LayoutSeg layoutSeg6 = this.layoutSeg;
                                if (layoutSeg6 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("layoutSeg");
                                    layoutSeg6 = null;
                                }
                                layoutSeg6.stop();
                            }
                        }
                        if (!this.sceneOn) {
                            detector = this.detectorRef;
                            if (detector != null) {
                                UiSettings uiSettings5 = this.settings;
                                if (uiSettings5 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("settings");
                                    uiSettings5 = null;
                                }
                                laneMarks = uiSettings5.get("lane_card") ? this.laneMarks : null;
                            }
                            sceneView3 = this.scene;
                            if (sceneView3 != null) {
                                sceneView3.setLaneMarks(this.laneMarks);
                            }
                            sceneView4 = this.scene;
                            if (sceneView4 != null) {
                                sceneView4.setLaneMemory(this.laneTracker.getMemory());
                            }
                            sceneView5 = this.scene;
                            if (sceneView5 != null) {
                                sceneView5.setCarMemory(this.carMemory);
                            }
                            detector2 = this.detectorRef;
                            if (detector2 != null) {
                                UiSettings uiSettings6 = this.settings;
                                if (uiSettings6 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("settings");
                                    uiSettings6 = null;
                                }
                                if (uiSettings6.get("lane_card") && getLaneModel().getHasModel()) {
                                    laneModel = getLaneModel();
                                    laneModel.setEnabled(true);
                                } else {
                                    laneModel = null;
                                }
                                detector2.setLaneModel(laneModel);
                            }
                            boolean z2 = !onroad && this.hudEditor == null;
                            textView = this.sceneBtn;
                            if (textView == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("sceneBtn");
                                textView = null;
                            }
                            textView.setVisibility(!z2 ? 0 : 8);
                            if (this.quickBtn != null) {
                                TextView textView4 = this.quickBtn;
                                if (textView4 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("quickBtn");
                                    textView4 = null;
                                }
                                textView4.setVisibility(z2 ? 0 : 8);
                            }
                            if (this.micBtn != null) {
                                ImageView imageView = this.micBtn;
                                if (imageView == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("micBtn");
                                    imageView = null;
                                }
                                imageView.setVisibility(z2 ? 0 : 8);
                            }
                            if (this.bookmarkBtn != null) {
                                ImageView imageView2 = this.bookmarkBtn;
                                if (imageView2 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("bookmarkBtn");
                                    imageView2 = null;
                                }
                                imageView2.setVisibility(z2 ? 0 : 8);
                            }
                            textView2 = this.sceneBtn;
                            if (textView2 != null) {
                                Intrinsics.throwUninitializedPropertyAccessException("sceneBtn");
                            } else {
                                textView3 = textView2;
                            }
                            textView3.setText(!z ? "CAMERA" : "SCENE");
                        }
                        detector = getDetector();
                        laneMarks = this.laneMarks;
                        detector.setLaneMarks(laneMarks);
                        sceneView3 = this.scene;
                        if (sceneView3 != null) {
                        }
                        sceneView4 = this.scene;
                        if (sceneView4 != null) {
                        }
                        sceneView5 = this.scene;
                        if (sceneView5 != null) {
                        }
                        detector2 = this.detectorRef;
                        if (detector2 != null) {
                        }
                        boolean z22 = !onroad && this.hudEditor == null;
                        textView = this.sceneBtn;
                        if (textView == null) {
                        }
                        textView.setVisibility(!z22 ? 0 : 8);
                        if (this.quickBtn != null) {
                        }
                        if (this.micBtn != null) {
                        }
                        if (this.bookmarkBtn != null) {
                        }
                        textView2 = this.sceneBtn;
                        if (textView2 != null) {
                        }
                        textView3.setText(!z ? "CAMERA" : "SCENE");
                    }
                }
                objArr = false;
                if (objArr == true) {
                }
                if (!this.sceneOn) {
                }
                detector.setLaneMarks(laneMarks);
                sceneView3 = this.scene;
                if (sceneView3 != null) {
                }
                sceneView4 = this.scene;
                if (sceneView4 != null) {
                }
                sceneView5 = this.scene;
                if (sceneView5 != null) {
                }
                detector2 = this.detectorRef;
                if (detector2 != null) {
                }
                boolean z222 = !onroad && this.hudEditor == null;
                textView = this.sceneBtn;
                if (textView == null) {
                }
                textView.setVisibility(!z222 ? 0 : 8);
                if (this.quickBtn != null) {
                }
                if (this.micBtn != null) {
                }
                if (this.bookmarkBtn != null) {
                }
                textView2 = this.sceneBtn;
                if (textView2 != null) {
                }
                textView3.setText(!z ? "CAMERA" : "SCENE");
            }
        }
        z = false;
        if (z) {
            SceneView sceneView62 = new SceneView(this, objArr3 == true ? 1 : 0, 2, objArr2 == true ? 1 : 0);
            sceneView62.setT(this.telemetry);
            uiSettings = this.settings;
            if (uiSettings == null) {
            }
            sceneView62.setDistance(uiSettings.getFloat("scene_distance4", 26.0f));
            uiSettings2 = this.settings;
            if (uiSettings2 == null) {
            }
            sceneView62.setTiltDeg(uiSettings2.getFloat("scene_tilt4", 17.0f));
            sceneView62.setOnViewChanged(new Function2() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda101
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Unit syncScene$lambda$168$lambda$166;
                    syncScene$lambda$168$lambda$166 = MainActivity.syncScene$lambda$168$lambda$166(MainActivity.this, ((Float) obj).floatValue(), ((Float) obj2).floatValue());
                    return syncScene$lambda$168$lambda$166;
                }
            });
            sceneView62.setOnLongPress(new Function0() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda102
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit syncScene$lambda$168$lambda$167;
                    syncScene$lambda$168$lambda$167 = MainActivity.syncScene$lambda$168$lambda$167(MainActivity.this);
                    return syncScene$lambda$168$lambda$167;
                }
            });
            sceneView62.setDetector(getDetector());
            frameLayout = this.root;
            if (frameLayout == null) {
            }
            SceneView sceneView72 = sceneView62;
            frameLayout2 = this.root;
            if (frameLayout2 == null) {
            }
            overlayView = this.overlay;
            if (overlayView == null) {
            }
            frameLayout.addView(sceneView72, frameLayout2.indexOfChild(overlayView), new FrameLayout.LayoutParams(-1, -1));
            this.scene = sceneView62;
        }
        sceneView = this.scene;
        if (sceneView != null) {
        }
        if (z != this.sceneOn) {
        }
        sceneView2 = this.scene;
        if (sceneView2 != null) {
        }
        if (this.sceneOn) {
        }
        objArr = false;
        if (objArr == true) {
        }
        if (!this.sceneOn) {
        }
        detector.setLaneMarks(laneMarks);
        sceneView3 = this.scene;
        if (sceneView3 != null) {
        }
        sceneView4 = this.scene;
        if (sceneView4 != null) {
        }
        sceneView5 = this.scene;
        if (sceneView5 != null) {
        }
        detector2 = this.detectorRef;
        if (detector2 != null) {
        }
        boolean z2222 = !onroad && this.hudEditor == null;
        textView = this.sceneBtn;
        if (textView == null) {
        }
        textView.setVisibility(!z2222 ? 0 : 8);
        if (this.quickBtn != null) {
        }
        if (this.micBtn != null) {
        }
        if (this.bookmarkBtn != null) {
        }
        textView2 = this.sceneBtn;
        if (textView2 != null) {
        }
        textView3.setText(!z ? "CAMERA" : "SCENE");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit syncScene$lambda$168$lambda$166(MainActivity this$0, float d, float tilt) {
        UiSettings uiSettings = this$0.settings;
        UiSettings uiSettings2 = null;
        if (uiSettings == null) {
            Intrinsics.throwUninitializedPropertyAccessException("settings");
            uiSettings = null;
        }
        uiSettings.setFloat("scene_distance4", d);
        UiSettings uiSettings3 = this$0.settings;
        if (uiSettings3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("settings");
        } else {
            uiSettings2 = uiSettings3;
        }
        uiSettings2.setFloat("scene_tilt4", tilt);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit syncScene$lambda$168$lambda$167(MainActivity this$0) {
        this$0.startHudEdit();
        return Unit.INSTANCE;
    }

    private final float windowScale() {
        float f;
        float f2;
        FrameLayout frameLayout = this.root;
        HudLayout hudLayout = null;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout = null;
        }
        int w = frameLayout.getWidth();
        FrameLayout frameLayout2 = this.root;
        if (frameLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout2 = null;
        }
        int h = frameLayout2.getHeight();
        if (w == 0 || h == 0) {
            return 1.0f;
        }
        Rect b = Build.VERSION.SDK_INT >= 30 ? getWindowManager().getMaximumWindowMetrics().getBounds() : new Rect(0, 0, getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels);
        Intrinsics.checkNotNull(b);
        float max = Math.max(b.width(), b.height());
        float min = Math.min(b.width(), b.height());
        if (max <= 0.0f || min <= 0.0f) {
            return 1.0f;
        }
        HudLayout hudLayout2 = this.hud;
        if (hudLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("hud");
            hudLayout2 = null;
        }
        hudLayout2.setDisplayLong(max);
        HudLayout hudLayout3 = this.hud;
        if (hudLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("hud");
        } else {
            hudLayout = hudLayout3;
        }
        hudLayout.setDisplayShort(min);
        float f3 = w;
        if (w >= h) {
            f = f3 / max;
            f2 = h / min;
        } else {
            f = f3 / min;
            f2 = h / max;
        }
        float f4 = Math.min(f, f2);
        return RangesKt.coerceIn((float) Math.pow(RangesKt.coerceIn(f4, 0.2f, 1.0f), 0.8d), 0.5f, 1.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List floating_delegate$lambda$172(MainActivity this$0) {
        View[] viewArr = new View[8];
        TextView textView = this$0.statsTv;
        LinearLayout linearLayout = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("statsTv");
            textView = null;
        }
        viewArr[0] = textView;
        TextView textView2 = this$0.statusTv;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("statusTv");
            textView2 = null;
        }
        viewArr[1] = textView2;
        TextView textView3 = this$0.sceneBtn;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sceneBtn");
            textView3 = null;
        }
        viewArr[2] = textView3;
        TextView textView4 = this$0.whereBtn;
        if (textView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("whereBtn");
            textView4 = null;
        }
        viewArr[3] = textView4;
        TextView textView5 = this$0.mapBtn;
        if (textView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mapBtn");
            textView5 = null;
        }
        viewArr[4] = textView5;
        LinearLayout linearLayout2 = this$0.favBar;
        if (linearLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("favBar");
        } else {
            linearLayout = linearLayout2;
        }
        viewArr[5] = linearLayout;
        viewArr[6] = this$0.findViewById(R.id.alertBox);
        viewArr[7] = this$0.findViewById(R.id.connectBar);
        return CollectionsKt.listOf((Object[]) viewArr);
    }

    private final List<View> getFloating() {
        return (List) this.floating.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void openDestinations() {
        String it;
        Intent i = new Intent(this, (Class<?>) DestinationActivity.class);
        double gpsLat = this.telemetry.getGpsLat();
        if ((Double.isInfinite(gpsLat) || Double.isNaN(gpsLat)) ? false : true) {
            i.putExtra("lat", this.telemetry.getGpsLat()).putExtra("lon", this.telemetry.getGpsLon());
        } else {
            MapPane p = this.mapPane;
            if (p != null) {
                double la = p.carLat();
                double lo = p.carLon();
                if ((Double.isInfinite(la) || Double.isNaN(la)) ? false : true) {
                    i.putExtra("lat", la).putExtra("lon", lo);
                }
            }
        }
        if (Nav.INSTANCE.getActive() && (it = Nav.INSTANCE.routeAhead()) != null) {
            i.putExtra("route", it);
        }
        this.destLauncher.launch(i);
    }

    private final void navigateTo(Intent r) {
        String stringExtra = r.getStringExtra("name");
        if (stringExtra == null) {
            stringExtra = "";
        }
        final String name = stringExtra;
        String stringExtra2 = r.getStringExtra("address");
        if (stringExtra2 == null) {
            stringExtra2 = "";
        }
        String address = stringExtra2;
        double lat = r.getDoubleExtra("latitude", Double.NaN);
        double lon = r.getDoubleExtra("longitude", Double.NaN);
        String stringExtra3 = r.getStringExtra("mapbox_id");
        if (stringExtra3 == null) {
            stringExtra3 = "";
        }
        final String mapboxId = stringExtra3;
        if (mapboxId.length() > 0) {
            String stringExtra4 = r.getStringExtra("search_id");
            final String searchId = stringExtra4 != null ? stringExtra4 : "";
            Map<String, ? extends Object> mapOf = MapsKt.mapOf(TuplesKt.to("placeId", mapboxId), TuplesKt.to("searchId", searchId));
            JSONObject put = new JSONObject().put("name", name).put("address", address);
            Intrinsics.checkNotNullExpressionValue(put, "put(...)");
            previewPlace(mapOf, put, new Function0() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda5
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit navigateTo$lambda$177;
                    navigateTo$lambda$177 = MainActivity.navigateTo$lambda$177(MainActivity.this, mapboxId, searchId, name);
                    return navigateTo$lambda$177;
                }
            });
            return;
        }
        if ((Double.isInfinite(lat) || Double.isNaN(lat)) ? false : true) {
            if (!((Double.isInfinite(lon) || Double.isNaN(lon)) ? false : true)) {
                return;
            }
            driveTo(name, address, lat, lon);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit navigateTo$lambda$177(final MainActivity this$0, String $mapboxId, String $searchId, final String $name) {
        DataClient dataClient = this$0.data;
        if (dataClient == null) {
            Intrinsics.throwUninitializedPropertyAccessException("data");
            dataClient = null;
        }
        dataClient.request("nav_pick", MapsKt.mapOf(TuplesKt.to("id", $mapboxId), TuplesKt.to("searchId", $searchId)), new Function1() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda100
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit navigateTo$lambda$177$lambda$176;
                navigateTo$lambda$177$lambda$176 = MainActivity.navigateTo$lambda$177$lambda$176(MainActivity.this, $name, (JSONObject) obj);
                return navigateTo$lambda$177$lambda$176;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit navigateTo$lambda$177$lambda$176(MainActivity this$0, String $name, JSONObject ack) {
        Intrinsics.checkNotNullParameter(ack, "ack");
        if (!ack.optBoolean("ok")) {
            MainActivity mainActivity = this$0;
            String optString = ack.optString("error");
            if (optString.length() == 0) {
                optString = "no answer";
            }
            Toast.makeText(mainActivity, "StarPilot didn't take " + $name + ": " + ((Object) optString), 1).show();
            return Unit.INSTANCE;
        }
        Nav nav = Nav.INSTANCE;
        DataClient dataClient = this$0.data;
        if (dataClient == null) {
            Intrinsics.throwUninitializedPropertyAccessException("data");
            dataClient = null;
        }
        nav.newDestination(dataClient);
        this$0.openMap();
        MapPane mapPane = this$0.mapPane;
        if (mapPane != null) {
            mapPane.expectRoutes();
        }
        return Unit.INSTANCE;
    }

    private final void previewPlace(final Map<String, ? extends Object> place, JSONObject shown, final Function0<Unit> setAtOnce) {
        openMap();
        Nav nav = Nav.INSTANCE;
        DataClient dataClient = this.data;
        if (dataClient == null) {
            Intrinsics.throwUninitializedPropertyAccessException("data");
            dataClient = null;
        }
        MapPane mapPane = this.mapPane;
        nav.startPreview(dataClient, place, shown, mapPane != null ? mapPane.liveOrigin() : null, new Function1() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda25
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit previewPlace$lambda$178;
                previewPlace$lambda$178 = MainActivity.previewPlace$lambda$178(place, setAtOnce, (String) obj);
                return previewPlace$lambda$178;
            }
        });
        MapPane mapPane2 = this.mapPane;
        if (mapPane2 != null) {
            mapPane2.showPreview();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit previewPlace$lambda$178(Map $place, Function0 $setAtOnce, String e) {
        if (e != null && (StringsKt.contains$default((CharSequence) e, (CharSequence) "unknown action", false, 2, (Object) null) || (StringsKt.contains$default((CharSequence) e, (CharSequence) "Choose a place from search results", false, 2, (Object) null) && !$place.containsKey("placeId")))) {
            Nav.INSTANCE.cancelPreview();
            $setAtOnce.invoke();
        }
        return Unit.INSTANCE;
    }

    public final void driveTo(final String name, final String address, final double lat, final double lon) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(address, "address");
        Map<String, ? extends Object> mapOf = MapsKt.mapOf(TuplesKt.to("name", name), TuplesKt.to("address", address), TuplesKt.to("latitude", Double.valueOf(lat)), TuplesKt.to("longitude", Double.valueOf(lon)));
        JSONObject put = new JSONObject().put("name", name).put("address", address).put("latitude", lat).put("longitude", lon);
        Intrinsics.checkNotNullExpressionValue(put, "put(...)");
        previewPlace(mapOf, put, new Function0() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda108
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit driveTo$lambda$179;
                driveTo$lambda$179 = MainActivity.driveTo$lambda$179(MainActivity.this, name, address, lat, lon);
                return driveTo$lambda$179;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit driveTo$lambda$179(MainActivity this$0, String $name, String $address, double $lat, double $lon) {
        this$0.setDestinationNow($name, $address, $lat, $lon);
        return Unit.INSTANCE;
    }

    private final void setDestinationNow(String name, String address, double lat, double lon) {
        DataClient dataClient = this.data;
        if (dataClient == null) {
            Intrinsics.throwUninitializedPropertyAccessException("data");
            dataClient = null;
        }
        dataClient.request("nav_set", MapsKt.mapOf(TuplesKt.to("name", name), TuplesKt.to("address", address), TuplesKt.to("latitude", Double.valueOf(lat)), TuplesKt.to("longitude", Double.valueOf(lon))), new Function1() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda104
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit destinationNow$lambda$181;
                destinationNow$lambda$181 = MainActivity.setDestinationNow$lambda$181(MainActivity.this, (JSONObject) obj);
                return destinationNow$lambda$181;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit setDestinationNow$lambda$181(MainActivity this$0, JSONObject ack) {
        String msg;
        Intrinsics.checkNotNullParameter(ack, "ack");
        if (!ack.optBoolean("ok")) {
            String err = ack.optString("error");
            Intrinsics.checkNotNull(err);
            if (StringsKt.contains((CharSequence) err, (CharSequence) "unknown", true) || StringsKt.contains((CharSequence) err, (CharSequence) "action", true)) {
                msg = "The comma doesn't take destinations from the tablet yet (StarView patch 9)";
            } else {
                String str = err;
                if (str.length() == 0) {
                    str = "no answer";
                }
                msg = "StarPilot didn't take the destination: " + ((Object) str);
            }
            Toast.makeText(this$0, msg, 1).show();
            return Unit.INSTANCE;
        }
        Nav nav = Nav.INSTANCE;
        DataClient dataClient = this$0.data;
        if (dataClient == null) {
            Intrinsics.throwUninitializedPropertyAccessException("data");
            dataClient = null;
        }
        nav.newDestination(dataClient);
        this$0.openMap();
        MapPane mapPane = this$0.mapPane;
        if (mapPane != null) {
            mapPane.expectRoutes();
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void syncFavBar(boolean show) {
        Favorites favorites = Favorites.INSTANCE;
        UiSettings uiSettings = this.settings;
        if (uiSettings == null) {
            Intrinsics.throwUninitializedPropertyAccessException("settings");
            uiSettings = null;
        }
        List pins = favorites.pins(uiSettings);
        int i = 1;
        boolean want = show && !pins.isEmpty() && System.currentTimeMillis() > this.favHiddenUntil;
        LinearLayout linearLayout = this.favBar;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("favBar");
            linearLayout = null;
        }
        linearLayout.setVisibility(want ? 0 : 8);
        if (want) {
            String key = CollectionsKt.joinToString$default(pins, "|", null, null, 0, null, new Function1() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda14
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    CharSequence syncFavBar$lambda$182;
                    syncFavBar$lambda$182 = MainActivity.syncFavBar$lambda$182((JSONObject) obj);
                    return syncFavBar$lambda$182;
                }
            }, 30, null) + Favorites.INSTANCE.getVersion();
            if (Intrinsics.areEqual(key, this.favKey)) {
                return;
            }
            this.favKey = key;
            LinearLayout linearLayout2 = this.favBar;
            if (linearLayout2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("favBar");
                linearLayout2 = null;
            }
            linearLayout2.removeAllViews();
            Iterator it = CollectionsKt.take(pins, 6).iterator();
            while (it.hasNext()) {
                final JSONObject p = (JSONObject) it.next();
                Pair<String, String> pinTitle = Favorites.INSTANCE.pinTitle(p);
                String icon = pinTitle.component1();
                final String title = pinTitle.component2();
                LinearLayout linearLayout3 = this.favBar;
                if (linearLayout3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("favBar");
                    linearLayout3 = null;
                }
                TextView $this$syncFavBar_u24lambda_u24185 = new TextView(this);
                $this$syncFavBar_u24lambda_u24185.setText(icon + "  " + title);
                $this$syncFavBar_u24lambda_u24185.setTextSize(15.0f);
                $this$syncFavBar_u24lambda_u24185.setTypeface(null, i);
                $this$syncFavBar_u24lambda_u24185.setTextColor(-1);
                $this$syncFavBar_u24lambda_u24185.setMaxLines(i);
                List pins2 = pins;
                String key2 = key;
                $this$syncFavBar_u24lambda_u24185.setPadding(dp(16), dp(10), dp(16), dp(10));
                GradientDrawable $this$syncFavBar_u24lambda_u24185_u24lambda_u24183 = new GradientDrawable();
                $this$syncFavBar_u24lambda_u24185_u24lambda_u24183.setCornerRadius(dp(22));
                $this$syncFavBar_u24lambda_u24185_u24lambda_u24183.setColor(Color.argb(170, 20, 22, 28));
                $this$syncFavBar_u24lambda_u24185_u24lambda_u24183.setStroke(dp(1), Color.argb(110, 255, 200, 87));
                $this$syncFavBar_u24lambda_u24185.setBackground($this$syncFavBar_u24lambda_u24185_u24lambda_u24183);
                $this$syncFavBar_u24lambda_u24185.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda15
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        MainActivity.syncFavBar$lambda$185$lambda$184(MainActivity.this, title, p, view);
                    }
                });
                LinearLayout.LayoutParams $this$syncFavBar_u24lambda_u24186 = new LinearLayout.LayoutParams(-2, -2);
                $this$syncFavBar_u24lambda_u24186.setMarginEnd(dp(10));
                Unit unit = Unit.INSTANCE;
                linearLayout3.addView($this$syncFavBar_u24lambda_u24185, $this$syncFavBar_u24lambda_u24186);
                pins = pins2;
                key = key2;
                want = want;
                it = it;
                i = 1;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence syncFavBar$lambda$182(JSONObject it) {
        Intrinsics.checkNotNullParameter(it, "it");
        Pair<String, String> pinTitle = Favorites.INSTANCE.pinTitle(it);
        String i = pinTitle.component1();
        String t = pinTitle.component2();
        return i + t;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void syncFavBar$lambda$185$lambda$184(MainActivity this$0, String $title, JSONObject $p, View it) {
        this$0.favHiddenUntil = System.currentTimeMillis() + 10000;
        LinearLayout linearLayout = this$0.favBar;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("favBar");
            linearLayout = null;
        }
        linearLayout.setVisibility(8);
        String optString = $p.optString("address");
        Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
        this$0.driveTo($title, optString, $p.optDouble("latitude"), $p.optDouble("longitude"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean getSaver() {
        UiSettings uiSettings = this.settings;
        if (uiSettings == null) {
            Intrinsics.throwUninitializedPropertyAccessException("settings");
            uiSettings = null;
        }
        return uiSettings.get("battery_saver");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setSaver(boolean on) {
        UiSettings uiSettings = this.settings;
        if (uiSettings == null) {
            Intrinsics.throwUninitializedPropertyAccessException("settings");
            uiSettings = null;
        }
        uiSettings.set("battery_saver", on);
        applySaver();
        QuickPanel quickPanel = this.quickPanel;
        if (quickPanel != null) {
            quickPanel.paint();
        }
        Toast.makeText(this, on ? "Battery saver: quieting the tablet…" : "Battery saver off: putting the tablet back…", 0).show();
        sendSaver(on, true, true);
    }

    private final void sendSaver(final boolean on, boolean force, final boolean report) {
        DataClient dataClient = this.data;
        if (dataClient == null) {
            Intrinsics.throwUninitializedPropertyAccessException("data");
            dataClient = null;
        }
        dataClient.request("tablet_saver", MapsKt.mapOf(TuplesKt.to("on", Boolean.valueOf(on)), TuplesKt.to("force", Boolean.valueOf(force))), new Function1() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda95
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit sendSaver$lambda$187;
                sendSaver$lambda$187 = MainActivity.sendSaver$lambda$187(on, report, this, (JSONObject) obj);
                return sendSaver$lambda$187;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit sendSaver$lambda$187(boolean $on, boolean $report, MainActivity this$0, JSONObject r) {
        String msg;
        Intrinsics.checkNotNullParameter(r, "r");
        if (!r.optBoolean("ok")) {
            msg = "Battery saver: " + r.optString("error", "no answer");
        } else if (r.optBoolean("already")) {
            msg = null;
        } else if ($on) {
            msg = "Battery saver on: " + r.optInt("stopped", r.optInt("quieted")) + " other apps stopped and kept asleep; StarView untouched";
        } else {
            msg = "Battery saver off: " + r.optInt("restored") + " apps and the scanning settings back as they were";
        }
        if (msg != null && ($report || !r.optBoolean("ok"))) {
            Toast.makeText(this$0, msg, 1).show();
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void saverTick(boolean connected) {
        if (connected && !this.saverLinked && getSaver()) {
            sendSaver(true, false, false);
        }
        this.saverLinked = connected;
    }

    private final void applySaver() {
        if (this.saverPill != null) {
            TextView textView = this.saverPill;
            if (textView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("saverPill");
                textView = null;
            }
            textView.setVisibility(getSaver() ? 0 : 8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x020c, code lost:
    
        if ((r7 - r2.getAtMs() < 3000 && r2.getQL() > 0.85f && r2.getQR() > 0.85f) != false) goto L114;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00ed, code lost:
    
        if ((r3.getWanted() && r30.getStarted() && r7 - r3.getShowingAtMs() < 3000 && !r29.sceneOn) != false) goto L61;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void ideasTick(Telemetry t) {
        UiSettings uiSettings;
        LaneModel laneModel;
        long now = System.currentTimeMillis();
        Ideas ideas = Ideas.INSTANCE;
        UiSettings uiSettings2 = this.settings;
        if (uiSettings2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("settings");
            uiSettings = null;
        } else {
            uiSettings = uiSettings2;
        }
        ideas.tick(t, uiSettings, this.detectorRef, now);
        String chipText = Ideas.INSTANCE.getChip();
        if (chipText == null || !t.getStarted()) {
            TextView textView = this.ideasChip;
            if (textView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ideasChip");
                textView = null;
            }
            textView.setVisibility(8);
        } else {
            TextView textView2 = this.ideasChip;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ideasChip");
                textView2 = null;
            }
            if (!Intrinsics.areEqual(textView2.getText(), chipText)) {
                TextView textView3 = this.ideasChip;
                if (textView3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ideasChip");
                    textView3 = null;
                }
                textView3.setText(chipText);
            }
            TextView textView4 = this.ideasChip;
            if (textView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ideasChip");
                textView4 = null;
            }
            textView4.setVisibility(0);
        }
        long ask = Ideas.INSTANCE.road().getAskWhy();
        if (ask != 0 && ask != this.ideasWhyShownFor) {
            this.ideasWhyShownFor = ask;
            LinearLayout linearLayout = this.ideasWhy;
            if (linearLayout == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ideasWhy");
                linearLayout = null;
            }
            linearLayout.setVisibility(0);
        }
        LinearLayout linearLayout2 = this.ideasWhy;
        if (linearLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ideasWhy");
            linearLayout2 = null;
        }
        if (linearLayout2.getVisibility() == 0 && now - this.ideasWhyShownFor > 12000) {
            LinearLayout linearLayout3 = this.ideasWhy;
            if (linearLayout3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ideasWhy");
                linearLayout3 = null;
            }
            linearLayout3.setVisibility(8);
            Ideas.INSTANCE.road().answerWhy(this, null);
        }
        DayView dv = Ideas.INSTANCE.getMemory().getDayView();
        Bitmap showing = dv.getShowing();
        if (showing != null) {
        }
        showing = null;
        Bitmap pic = showing;
        if (pic != null) {
            ImageView imageView = this.ideasDay;
            if (imageView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ideasDay");
                imageView = null;
            }
            imageView.setImageBitmap(pic);
            ImageView imageView2 = this.ideasDay;
            if (imageView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ideasDay");
                imageView2 = null;
            }
            imageView2.setVisibility(0);
        } else {
            ImageView imageView3 = this.ideasDay;
            if (imageView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ideasDay");
                imageView3 = null;
            }
            imageView3.setVisibility(8);
        }
        if (t.getStarted() && t.getVEgo() > 3.0f) {
            if (now - this.ideasLabelMs > 20000) {
                Pair<Integer, Double> laneFromLeft = this.laneTracker.laneFromLeft();
                int n = laneFromLeft.component1().intValue();
                double q = laneFromLeft.component2().doubleValue();
                int m = this.laneTracker.laneCount().component1().intValue();
                NewIdeas newIdeas = NewIdeas.INSTANCE;
                UiSettings uiSettings3 = this.settings;
                if (uiSettings3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("settings");
                    uiSettings3 = null;
                }
                if (newIdeas.on(uiSettings3, "self_labels") && Intrinsics.areEqual(RtkPosition.INSTANCE.getKind(), "fixed") && q >= 0.9d && n > 0 && m > 0) {
                    this.ideasLabelMs = now;
                    LaneShots.request$default(getLaneShots(), "[auto] " + LaneShots.Companion.caption(this.laneTracker, "self-labelled (RTK fixed)", t.getVEgo() * 2.23694f), LaneShots.Why.EVENT, null, 4, null);
                } else {
                    NewIdeas newIdeas2 = NewIdeas.INSTANCE;
                    UiSettings uiSettings4 = this.settings;
                    if (uiSettings4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("settings");
                        uiSettings4 = null;
                    }
                    if (newIdeas2.on(uiSettings4, "model_disagree")) {
                        Detector detector = this.detectorRef;
                        if (detector != null && (laneModel = detector.getLaneModel()) != null && (it = laneModel.getAnswer()) != null) {
                        }
                        LaneModel.Answer it = null;
                        if (it != null && q >= 0.6d && m > 0) {
                            if (it.getLeft() != RangesKt.coerceAtMost(this.laneTracker.lanesLeft(), 3) || it.getRight() != RangesKt.coerceAtMost(this.laneTracker.lanesRight(), 3)) {
                                this.ideasLabelMs = now;
                                LaneShots.request$default(getLaneShots(), "[disagree] model " + it.getLeft() + "/" + it.getRight() + " vs card " + this.laneTracker.lanesLeft() + "/" + this.laneTracker.lanesRight() + " - " + LaneShots.Companion.caption(this.laneTracker, "model and card disagree", t.getVEgo() * 2.23694f), LaneShots.Why.EVENT, null, 4, null);
                            }
                        }
                    }
                }
            }
        }
        if (t.getStarted() && !this.ideasWasStarted) {
            NewIdeas newIdeas3 = NewIdeas.INSTANCE;
            UiSettings uiSettings5 = this.settings;
            if (uiSettings5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("settings");
                uiSettings5 = null;
            }
            if (newIdeas3.on(uiSettings5, "friends_fleet")) {
                new Thread(new Runnable() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda33
                    @Override // java.lang.Runnable
                    public final void run() {
                        MainActivity.ideasTick$lambda$190(MainActivity.this);
                    }
                }).start();
            }
            NewIdeas newIdeas4 = NewIdeas.INSTANCE;
            UiSettings uiSettings6 = this.settings;
            if (uiSettings6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("settings");
                uiSettings6 = null;
            }
            if (newIdeas4.on(uiSettings6, "night_school")) {
                NewIdeas.INSTANCE.importNightModel(this);
            }
        }
        if (!t.getStarted() && this.ideasWasStarted) {
            Ideas.INSTANCE.endOfDrive();
            NewIdeas newIdeas5 = NewIdeas.INSTANCE;
            UiSettings uiSettings7 = this.settings;
            if (uiSettings7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("settings");
                uiSettings7 = null;
            }
            if (newIdeas5.on(uiSettings7, "friends_fleet")) {
                new Thread(new Runnable() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda34
                    @Override // java.lang.Runnable
                    public final void run() {
                        MainActivity.ideasTick$lambda$191(MainActivity.this);
                    }
                }).start();
            }
        }
        this.ideasWasStarted = t.getStarted();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void ideasTick$lambda$190(MainActivity this$0) {
        IdeaSpots.INSTANCE.importFleet(NewIdeas.INSTANCE.deviceName(this$0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void ideasTick$lambda$191(MainActivity this$0) {
        IdeaSpots.INSTANCE.exportFleet(NewIdeas.INSTANCE.deviceName(this$0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void voiceLauncher$lambda$192(MainActivity this$0, ActivityResult r) {
        ArrayList<String> stringArrayListExtra;
        String said;
        Intent data = r.getData();
        if (data == null || (stringArrayListExtra = data.getStringArrayListExtra("android.speech.extra.RESULTS")) == null || (said = (String) CollectionsKt.firstOrNull((List) stringArrayListExtra)) == null) {
            return;
        }
        this$0.runVoice(said);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void listenVoice() {
        try {
            this.voiceLauncher.launch(new Intent("android.speech.action.RECOGNIZE_SPEECH").putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form").putExtra("android.speech.extra.PROMPT", "Say a quick setting, or \"take me to…\""));
        } catch (Exception e) {
            Toast.makeText(this, "No speech recognizer on this tablet", 1).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x011d, code lost:
    
        if (r10 == null) goto L36;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void bookmarkNow() {
        String str;
        String str2;
        double[] rp;
        String str3;
        String str4;
        String str5;
        String str6;
        String roadName;
        String str7;
        String name;
        try {
            Telemetry t = this.telemetry;
            long now = System.currentTimeMillis();
            JSONObject j = new JSONObject();
            if (RtkPosition.INSTANCE.fast(now)) {
                str = "turn_signals";
                str2 = "exit_signals";
                rp = RtkPosition.INSTANCE.now(now, t.getVEgo());
            } else {
                str = "turn_signals";
                str2 = "exit_signals";
                rp = null;
            }
            j.put("lat", rp != null ? rp[0] : t.getGpsLat()).put("lon", rp != null ? rp[1] : t.getGpsLon());
            if (rp != null) {
                str4 = "side";
                str3 = "";
                str5 = "RTK " + RtkPosition.INSTANCE.getKind() + " ±" + bookmarkNow$r1(RtkPosition.INSTANCE.getAccM() * 39.37d) + " in";
            } else {
                str3 = "";
                str4 = "side";
                str5 = "comma GPS";
            }
            j.put("position", str5);
            j.put("speed", Math.round(t.getVEgo() * 2.23694f) + " mph").put("speed_ms", t.getVEgo());
            try {
                str6 = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            } catch (Exception e) {
                str6 = str3;
            }
            j.put("app", str6);
            LaneBrain b = this.laneTracker;
            LaneBrain.Road road = b.getRoad();
            if (road != null && (name = road.getName()) != null) {
                String str8 = name;
                if (str8.length() == 0) {
                    str8 = road.getKind();
                }
                roadName = str8;
            }
            roadName = t.getRoadName();
            j.put("place", roadName);
            JSONObject ln = new JSONObject();
            Pair<Integer, Double> laneFromLeft = b.laneFromLeft();
            int n = laneFromLeft.component1().intValue();
            double q = laneFromLeft.component2().doubleValue();
            Pair<Integer, String> laneCount = b.laneCount();
            int m = laneCount.component1().intValue();
            String src = laneCount.component2();
            String str9 = str4;
            double d = 100;
            ln.put("label", b.label()).put("lane_from_left", n).put("sure", bookmarkNow$r1(q * d)).put("lanes", m).put("lanes_from", src);
            double[] $this$map$iv = b.marg(-1);
            int m2 = $this$map$iv.length;
            Collection destination$iv$iv = new ArrayList(m2);
            int i = 0;
            for (int length = $this$map$iv.length; i < length; length = length) {
                double item$iv$iv = $this$map$iv[i];
                destination$iv$iv.add(Double.valueOf(bookmarkNow$r1(item$iv$iv * d)));
                i++;
            }
            JSONObject put = ln.put("lanes_left_probabilities", new JSONArray(destination$iv$iv));
            double[] $this$map$iv2 = b.marg(1);
            Collection destination$iv$iv2 = new ArrayList($this$map$iv2.length);
            int $i$f$map = 0;
            for (int length2 = $this$map$iv2.length; $i$f$map < length2; length2 = length2) {
                double item$iv$iv2 = $this$map$iv2[$i$f$map];
                destination$iv$iv2.add(Double.valueOf(bookmarkNow$r1(item$iv$iv2 * d)));
                $i$f$map++;
            }
            put.put("lanes_right_probabilities", new JSONArray(destination$iv$iv2));
            Iterable $this$map$iv3 = b.getWhy();
            int $i$f$map2 = 0;
            Collection destination$iv$iv3 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv3, 10));
            Iterable $this$mapTo$iv$iv = $this$map$iv3;
            int $i$f$mapTo = 0;
            Iterator<LaneBrain.Event> it = $this$mapTo$iv$iv.iterator();
            while (true) {
                Iterable $this$map$iv4 = $this$map$iv3;
                if (!it.hasNext()) {
                    break;
                }
                Object item$iv$iv3 = it.next();
                LaneBrain.Event it2 = (LaneBrain.Event) item$iv$iv3;
                destination$iv$iv3.add(((now - it2.getAtMs()) / 1000) + "s ago: " + it2.getText());
                $this$map$iv3 = $this$map$iv4;
                $this$mapTo$iv$iv = $this$mapTo$iv$iv;
                $i$f$mapTo = $i$f$mapTo;
                $i$f$map2 = $i$f$map2;
            }
            ln.put("why", new JSONArray(destination$iv$iv3));
            LaneBrain.RoadModel rm = b.getModel();
            ln.put("road_model", new JSONObject().put("lanes", rm.getLanes()).put("from", rm.getSrc()).put("conf", rm.getConf()).put("extra_left", rm.getExtraL()).put("extra_right", rm.getExtraR()).put("kind_left", rm.getKindL()).put("kind_right", rm.getKindR()).put("tunnel", rm.getTunnel()).put("bridge", rm.getBridge()));
            LaneBrain.RoadInfo i2 = b.getInfo();
            if (i2 != null) {
                ln.put("road_data", new JSONObject().put("hpms_lanes", i2.getHpmsLanes()).put("hpms_name", i2.getHpmsName()).put("hpms_ramp", i2.getHpmsRamp()).put("hpms_sure", i2.getHpmsSure()).put("extra_why", i2.getExtraWhy()).put("route_straight_lanes", i2.getNavStraight()));
            }
            if (road != null) {
                str7 = " mph";
                ln.put("map_street", new JSONObject().put("id", road.getId()).put("name", road.getName()).put("kind", road.getKind()).put("oneway", road.getOneway()).put("highway", road.getHighway()).put("lanes_tag", road.getLanesOurWay()));
            } else {
                str7 = " mph";
            }
            Iterable $this$filter$iv = b.getEvents();
            Collection destination$iv$iv4 = new ArrayList();
            for (LaneBrain.Event it3 : $this$filter$iv) {
                LaneBrain.Road road2 = road;
                if (now - it3.getAtMs() < 120000) {
                    destination$iv$iv4.add(it3);
                }
                road = road2;
            }
            Iterable $this$map$iv5 = (List) destination$iv$iv4;
            int $i$f$map3 = 0;
            Collection destination$iv$iv5 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv5, 10));
            Iterable $this$mapTo$iv$iv2 = $this$map$iv5;
            int $i$f$mapTo2 = 0;
            for (Object item$iv$iv4 : $this$mapTo$iv$iv2) {
                LaneBrain.Event it4 = (LaneBrain.Event) item$iv$iv4;
                destination$iv$iv5.add(((now - it4.getAtMs()) / 1000) + "s ago: " + it4.getText());
                $this$mapTo$iv$iv2 = $this$mapTo$iv$iv2;
                $i$f$map3 = $i$f$map3;
                $this$map$iv5 = $this$map$iv5;
                $i$f$mapTo2 = $i$f$mapTo2;
            }
            ln.put("events_last_2_min", new JSONArray(destination$iv$iv5));
            ln.put("ahead", this.laneAhead.text(b.getOdo(), false)).put("ahead_stage", this.laneAhead.getStage().name()).put("drive", b.summary());
            j.put("lane", ln);
            JSONObject it5 = t.navState();
            if (it5 != null) {
                j.put("route", it5);
            }
            Pair<Nav.Step, Double> nextLanes = Nav.INSTANCE.nextLanes();
            if (nextLanes != null) {
                Nav.Step s = nextLanes.component1();
                double d2 = nextLanes.component2().doubleValue();
                JSONObject put2 = new JSONObject().put("at_m", Math.round(d2)).put("type", s.getType()).put("modifier", s.getModifier()).put("text", s.getText());
                Iterable $this$map$iv6 = s.getLanes();
                Collection destination$iv$iv6 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv6, 10));
                for (Object item$iv$iv5 : $this$map$iv6) {
                    Nav.Lane l = (Nav.Lane) item$iv$iv5;
                    destination$iv$iv6.add(new JSONObject().put("arrows", new JSONArray((Collection) l.getArrows())).put("ok", l.getOk()).put("use", l.getUse()));
                    $this$map$iv6 = $this$map$iv6;
                    s = s;
                    d2 = d2;
                }
                j.put("route_next_lanes", put2.put("lanes", new JSONArray(destination$iv$iv6)));
            }
            ModelSnapshot md = t.getModel();
            JSONObject put3 = new JSONObject().put("enabled", t.getEnabled()).put("active", t.getActive()).put("state", t.getOpState()).put("experimental", t.getExperimentalMode());
            Iterable $this$filter$iv2 = CollectionsKt.listOf((Object[]) new String[]{t.getAlertText1(), t.getAlertText2()});
            Collection destination$iv$iv7 = new ArrayList();
            for (Object element$iv$iv : $this$filter$iv2) {
                Iterable $this$filter$iv3 = $this$filter$iv2;
                if (((String) element$iv$iv).length() > 0) {
                    destination$iv$iv7.add(element$iv$iv);
                }
                $this$filter$iv2 = $this$filter$iv3;
            }
            String str10 = str7;
            JSONObject put4 = put3.put("alert", CollectionsKt.joinToString$default((List) destination$iv$iv7, " / ", null, null, 0, null, null, 62, null)).put("set_speed", Math.round((t.getVCruiseCluster() > 0.0f ? t.getVCruiseCluster() : t.getVCruise()) * 0.621371f) + str10).put("lead", t.getLeadOne().getStatus() ? Math.round(t.getLeadOne().getDRel() * 3.28084f) + " ft, " + Math.round(t.getLeadOne().getVLead() * 2.23694f) + str10 : "none");
            String str11 = "both";
            JSONObject put5 = put4.put("blinker", (t.getLeftBlinker() && t.getRightBlinker()) ? "both" : t.getLeftBlinker() ? "left" : t.getRightBlinker() ? "right" : "off");
            if (!t.getLeftBlindspot() || !t.getRightBlindspot()) {
                str11 = t.getLeftBlindspot() ? "left" : t.getRightBlindspot() ? "right" : "none";
            }
            j.put("openpilot", put5.put("blind_spot", str11).put("lane_change", md != null && md.getLaneChanging() ? md.getLaneChangeDir() < 0 ? "left" : "right" : "none").put("gas", t.getGasPressed()).put("brake", t.getBrakePressed()).put("steering_pressed", t.getSteeringPressed()).put("steering_angle", bookmarkNow$r1(t.getSteeringAngleDeg())));
            LaneSuggestView laneSuggestView = this.laneSuggest;
            if (laneSuggestView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("laneSuggest");
                laneSuggestView = null;
            }
            LaneSuggest lg = laneSuggestView.getLogic();
            j.put("lane_question", new JSONObject().put("state", lg.getState().name()).put("kind", lg.getKind()).put(str9, lg.getDir() < 0 ? "left" : lg.getDir() > 0 ? "right" : str3).put("why", lg.getWhy()));
            LaneSuggestView laneSuggestView2 = this.laneSuggest;
            if (laneSuggestView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("laneSuggest");
                laneSuggestView2 = null;
            }
            TurnSignal ts = laneSuggestView2.getTurn();
            JSONObject put6 = new JSONObject().put("state", ts.getState().name()).put(str9, ts.getDir() < 0 ? "left" : ts.getDir() > 0 ? "right" : str3).put("street", ts.getStreet());
            double distM = ts.getDistM();
            j.put("turn_signal", put6.put("ft_to_turn", !Double.isInfinite(distM) && !Double.isNaN(distM) ? Math.round(ts.getDistM() * 3.28084d) : -1L).put("holding", ts.getHoldSide() != 0).put("not_a_turn_lane", ts.getLaneHold()).put("last", ts.getResult()));
            String str12 = str2;
            JSONObject put7 = new JSONObject().put("change_lanes_card", t.s("lane_suggest")).put("automatic_lane_changes", t.s("lane_auto")).put(str12, t.s(str12));
            String str13 = str;
            j.put("blinker_settings", put7.put(str13, t.s(str13)).put("lane_ends_question", t.s("lane_ends_ask")));
            j.put("blinker_log", new JSONArray((Collection) BlinkLog.INSTANCE.recent(15)));
            j.put("lane_log_tail", Bookmarks.INSTANCE.tail(new File(getFilesDir(), "lane_log.txt"), 80));
            Bookmarks.Item item = Bookmarks.INSTANCE.create(this, j);
            getLaneShots().request(LaneShots.Companion.caption(this.laneTracker, "[bookmark]", t.getVEgo() * 2.23694f), LaneShots.Why.USER, new File(item.getDir(), "picture.jpg"));
            BookmarkCard bookmarkCard = this.bookmarkCard;
            if (bookmarkCard == null) {
                Intrinsics.throwUninitializedPropertyAccessException("bookmarkCard");
                bookmarkCard = null;
            }
            bookmarkCard.show(item);
            ImageView imageView = this.bookmarkBtn;
            if (imageView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("bookmarkBtn");
                imageView = null;
            }
            imageView.performHapticFeedback(0);
        } catch (Exception e2) {
            Toast.makeText(this, "Couldn't bookmark: " + e2.getMessage(), 1).show();
        }
    }

    private static final double bookmarkNow$r1(double v) {
        return Math.round(10 * v) / 10.0d;
    }

    private final void runVoice(String said) {
        String str;
        VoiceCommands.Cmd parse = VoiceCommands.INSTANCE.parse(said);
        boolean pBool = this.telemetry.pBool("ExperimentalMode", false);
        int pInt = this.telemetry.pInt("LongitudinalPersonality", 1);
        DataClient dataClient = null;
        UiSettings uiSettings = null;
        UiSettings uiSettings2 = null;
        HudLayout hudLayout = null;
        HudLayout hudLayout2 = null;
        DataClient dataClient2 = null;
        switch (WhenMappings.$EnumSwitchMapping$0[parse.getKind().ordinal()]) {
            case 1:
                if (!pBool) {
                    DataClient dataClient3 = this.data;
                    if (dataClient3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("data");
                    } else {
                        dataClient = dataClient3;
                    }
                    dataClient.control(new JSONObject(MapsKt.mapOf(TuplesKt.to("action", "exp_toggle"))));
                }
                str = "Experimental on";
                break;
            case 2:
                if (pBool) {
                    DataClient dataClient4 = this.data;
                    if (dataClient4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("data");
                    } else {
                        dataClient2 = dataClient4;
                    }
                    dataClient2.control(new JSONObject(MapsKt.mapOf(TuplesKt.to("action", "exp_toggle"))));
                }
                str = "Chill mode";
                break;
            case 3:
                if (!getMapOpen()) {
                    openMap();
                }
                str = "Map open";
                break;
            case 4:
                if (getMapOpen()) {
                    closeMap();
                }
                str = "Map closed";
                break;
            case 5:
                if (!HudPresets.INSTANCE.focusOn(this)) {
                    HudPresets hudPresets = HudPresets.INSTANCE;
                    MainActivity mainActivity = this;
                    HudLayout hudLayout3 = this.hud;
                    if (hudLayout3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("hud");
                    } else {
                        hudLayout2 = hudLayout3;
                    }
                    hudPresets.toggleFocus(mainActivity, hudLayout2);
                    layoutHud();
                    applyHud();
                }
                str = "Highway focus on";
                break;
            case 6:
                if (HudPresets.INSTANCE.focusOn(this)) {
                    HudPresets hudPresets2 = HudPresets.INSTANCE;
                    MainActivity mainActivity2 = this;
                    HudLayout hudLayout4 = this.hud;
                    if (hudLayout4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("hud");
                    } else {
                        hudLayout = hudLayout4;
                    }
                    hudPresets2.toggleFocus(mainActivity2, hudLayout);
                    layoutHud();
                    applyHud();
                }
                str = "All widgets back";
                break;
            case 7:
                Speech.INSTANCE.setEnabled(true);
                str = "Voice on";
                break;
            case 8:
                Speech.INSTANCE.setEnabled(false);
                str = "Chimes only";
                break;
            case 9:
                setSaver(true);
                str = "Battery saver on";
                break;
            case 10:
                setSaver(false);
                str = "Battery saver off";
                break;
            case 11:
                KeepDrive.INSTANCE.keepNewest(this);
                str = "Keeping this drive";
                break;
            case 12:
                runVoice$setP(this, Integer.parseInt(parse.getArg()));
                str = (String) CollectionsKt.listOf((Object[]) new String[]{"Aggressive", "Standard", "Relaxed"}).get(Integer.parseInt(parse.getArg()));
                break;
            case 13:
                runVoice$setP(this, pInt + 1);
                str = "More room";
                break;
            case 14:
                runVoice$setP(this, pInt - 1);
                str = "Following closer";
                break;
            case 15:
                UiSettings uiSettings3 = this.settings;
                if (uiSettings3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("settings");
                } else {
                    uiSettings2 = uiSettings3;
                }
                uiSettings2.set("scene_view", true);
                uiTickNow();
                str = "Scene view";
                break;
            case 16:
                UiSettings uiSettings4 = this.settings;
                if (uiSettings4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("settings");
                } else {
                    uiSettings = uiSettings4;
                }
                uiSettings.set("scene_view", false);
                uiTickNow();
                str = "Camera view";
                break;
            case 17:
                str = voiceNavigate(parse.getArg(), true);
                break;
            case 18:
                str = voiceNudge(parse.getArg());
                break;
            case 19:
                bookmarkNow();
                str = "Bookmarked";
                break;
            case 20:
                str = "Didn't catch that: \"" + said + "\"";
                break;
            default:
                throw new NoWhenBranchMatchedException();
        }
        QuickPanel quickPanel = this.quickPanel;
        if (quickPanel != null) {
            quickPanel.paint();
        }
        Toast.makeText(this, str, 0).show();
    }

    private static final void runVoice$setP(MainActivity this$0, int v) {
        DataClient dataClient = this$0.data;
        if (dataClient == null) {
            Intrinsics.throwUninitializedPropertyAccessException("data");
            dataClient = null;
        }
        dataClient.control(new JSONObject(MapsKt.mapOf(TuplesKt.to("action", "param"), TuplesKt.to("key", "LongitudinalPersonality"), TuplesKt.to("value", Integer.valueOf(RangesKt.coerceIn(v, 0, 2))))));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0039, code lost:
    
        if (r10.equals("turn_right") == false) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0083, code lost:
    
        r3 = com.starpilot.starview.Ideas.INSTANCE.getMemory();
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x008d, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual(r10, "turn_left") == false) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x008f, code lost:
    
        r4 = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0092, code lost:
    
        r3.voiceTurn(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0099, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual(r10, "turn_left") == false) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x009b, code lost:
    
        r6 = "left";
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:?, code lost:
    
        return "Signaling for the next " + r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0091, code lost:
    
        r4 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0080, code lost:
    
        if (r10.equals("turn_left") == false) goto L42;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final String voiceNudge(String arg) {
        NewIdeas newIdeas = NewIdeas.INSTANCE;
        UiSettings uiSettings = this.settings;
        if (uiSettings == null) {
            Intrinsics.throwUninitializedPropertyAccessException("settings");
            uiSettings = null;
        }
        if (!newIdeas.on(uiSettings, "voice_nudges")) {
            return "Voice nudges are off (Settings > New ideas)";
        }
        IdeasMemory mem = Ideas.INSTANCE.getMemory();
        long now = System.currentTimeMillis();
        String str = "right";
        switch (arg.hashCode()) {
            case -1364013995:
                if (arg.equals("center")) {
                    mem.setNudgeOffset(0.0d);
                    mem.setNudgeUntil(0L);
                    return "Back to the middle";
                }
                return "Didn't catch that";
            case -124477815:
                break;
            case 3317767:
                if (arg.equals("left")) {
                    mem.setNudgeOffset(-0.3d);
                    mem.setNudgeUntil(90000 + now);
                    return "Moving over left a bit";
                }
                return "Didn't catch that";
            case 3506395:
                if (arg.equals("room")) {
                    mem.setRoomUntil(180000 + now);
                    return "More room for 3 minutes";
                }
                return "Didn't catch that";
            case 108511772:
                if (arg.equals("right")) {
                    mem.setNudgeOffset(0.3d);
                    mem.setNudgeUntil(90000 + now);
                    return "Moving over right a bit";
                }
                return "Didn't catch that";
            case 441816026:
                break;
            default:
                return "Didn't catch that";
        }
    }

    private final String voiceNavigate(final String arg, boolean retry) {
        DataClient dataClient;
        Object element$iv;
        String str;
        String str2;
        StringBuilder append;
        Object element$iv2;
        Object element$iv3;
        String a = StringsKt.trim((CharSequence) arg).toString().toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(a, "toLowerCase(...)");
        Iterable $this$firstOrNull$iv = Favorites.INSTANCE.getList();
        Iterator it = $this$firstOrNull$iv.iterator();
        while (true) {
            dataClient = null;
            if (!it.hasNext()) {
                element$iv = null;
                break;
            }
            element$iv = it.next();
            JSONObject it2 = (JSONObject) element$iv;
            if (Intrinsics.areEqual(it2.optString("label"), a)) {
                break;
            }
        }
        JSONObject jSONObject = (JSONObject) element$iv;
        if (jSONObject == null) {
            Iterable $this$firstOrNull$iv2 = Favorites.INSTANCE.getList();
            Iterator it3 = $this$firstOrNull$iv2.iterator();
            while (true) {
                if (!it3.hasNext()) {
                    element$iv2 = null;
                    break;
                }
                element$iv2 = it3.next();
                JSONObject it4 = (JSONObject) element$iv2;
                if (StringsKt.equals(Favorites.INSTANCE.title(it4), a, true)) {
                    break;
                }
            }
            jSONObject = (JSONObject) element$iv2;
            if (jSONObject == null) {
                if (a.length() >= 4) {
                    Iterable $this$firstOrNull$iv3 = Favorites.INSTANCE.getList();
                    Iterator it5 = $this$firstOrNull$iv3.iterator();
                    while (true) {
                        if (!it5.hasNext()) {
                            element$iv3 = null;
                            break;
                        }
                        element$iv3 = it5.next();
                        JSONObject it6 = (JSONObject) element$iv3;
                        String title = Favorites.INSTANCE.title(it6);
                        Intrinsics.checkNotNullExpressionValue(title, "title(...)");
                        String lowerCase = title.toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                        if (StringsKt.contains$default((CharSequence) lowerCase, (CharSequence) a, false, 2, (Object) null)) {
                            break;
                        }
                    }
                    jSONObject = (JSONObject) element$iv3;
                } else {
                    jSONObject = null;
                }
            }
        }
        JSONObject fav = jSONObject;
        if (fav != null) {
            Intent putExtra = new Intent().putExtra("name", Favorites.INSTANCE.title(fav)).putExtra("address", fav.optString("address")).putExtra("latitude", fav.optDouble("latitude")).putExtra("longitude", fav.optDouble("longitude"));
            Intrinsics.checkNotNullExpressionValue(putExtra, "putExtra(...)");
            navigateTo(putExtra);
            return "Taking you to " + Favorites.INSTANCE.title(fav);
        }
        if (retry && Favorites.INSTANCE.getList().isEmpty()) {
            DataClient dataClient2 = this.data;
            if (dataClient2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("data");
                dataClient2 = null;
            }
            if (dataClient2.getConnected()) {
                Favorites favorites = Favorites.INSTANCE;
                DataClient dataClient3 = this.data;
                if (dataClient3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("data");
                } else {
                    dataClient = dataClient3;
                }
                favorites.load(dataClient, new Function0() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Unit voiceNavigate$lambda$208;
                        voiceNavigate$lambda$208 = MainActivity.voiceNavigate$lambda$208(MainActivity.this, arg);
                        return voiceNavigate$lambda$208;
                    }
                });
                return "Looking up your saved places…";
            }
        }
        ActivityResultLauncher<Intent> activityResultLauncher = this.destLauncher;
        Intent $this$voiceNavigate_u24lambda_u24209 = new Intent(this, (Class<?>) DestinationActivity.class).putExtra("query", arg);
        double gpsLat = this.telemetry.getGpsLat();
        if ((Double.isInfinite(gpsLat) || Double.isNaN(gpsLat)) ? false : true) {
            $this$voiceNavigate_u24lambda_u24209.putExtra("lat", this.telemetry.getGpsLat()).putExtra("lon", this.telemetry.getGpsLon());
        }
        activityResultLauncher.launch($this$voiceNavigate_u24lambda_u24209);
        if (Intrinsics.areEqual(a, "home") || Intrinsics.areEqual(a, "work")) {
            if (a.length() > 0) {
                StringBuilder sb = new StringBuilder();
                char it7 = a.charAt(0);
                String valueOf = String.valueOf(it7);
                Intrinsics.checkNotNull(valueOf, "null cannot be cast to non-null type java.lang.String");
                String upperCase = valueOf.toUpperCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
                StringBuilder append2 = sb.append((Object) upperCase);
                String substring = a.substring(1);
                Intrinsics.checkNotNullExpressionValue(substring, "substring(...)");
                str = append2.append(substring).toString();
            } else {
                str = a;
            }
            if (a.length() > 0) {
                StringBuilder sb2 = new StringBuilder();
                char it8 = a.charAt(0);
                String valueOf2 = String.valueOf(it8);
                Intrinsics.checkNotNull(valueOf2, "null cannot be cast to non-null type java.lang.String");
                String upperCase2 = valueOf2.toUpperCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(upperCase2, "toUpperCase(...)");
                StringBuilder append3 = sb2.append((Object) upperCase2);
                String substring2 = a.substring(1);
                Intrinsics.checkNotNullExpressionValue(substring2, "substring(...)");
                str2 = append3.append(substring2).toString();
            } else {
                str2 = a;
            }
            append = new StringBuilder().append("No ").append(str).append(" saved yet: pick it, then ★ › Make it ").append(str2);
        } else {
            append = new StringBuilder().append("Searching ").append(arg);
        }
        return append.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit voiceNavigate$lambda$208(MainActivity this$0, String $arg) {
        String msg = this$0.voiceNavigate($arg, false);
        Toast.makeText(this$0, msg, 0).show();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void doorCommand(final String cmd) {
        DataClient dataClient = this.data;
        DataClient dataClient2 = null;
        if (dataClient == null) {
            Intrinsics.throwUninitializedPropertyAccessException("data");
            dataClient = null;
        }
        if (!dataClient.getConnected()) {
            Toast.makeText(this, "Not connected to the comma", 0).show();
            return;
        }
        DataClient dataClient3 = this.data;
        if (dataClient3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("data");
        } else {
            dataClient2 = dataClient3;
        }
        dataClient2.request("door", MapsKt.mapOf(TuplesKt.to("cmd", cmd)), new Function1() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda31
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit doorCommand$lambda$212;
                doorCommand$lambda$212 = MainActivity.doorCommand$lambda$212(cmd, this, (JSONObject) obj);
                return doorCommand$lambda$212;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit doorCommand$lambda$212(String $cmd, MainActivity this$0, JSONObject ack) {
        Intrinsics.checkNotNullParameter(ack, "ack");
        String msg = ack.optBoolean("ok") ? Intrinsics.areEqual($cmd, "lock") ? "Doors locked" : "Doors unlocked" : "Doors: " + ack.optString("error", "refused");
        Toast.makeText(this$0, msg, 0).show();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void openQuick() {
        if (this.quickPanel != null) {
            return;
        }
        QuickPanel p = new QuickPanel(this, new QuickPanel.Actions() { // from class: com.starpilot.starview.MainActivity$openQuick$p$1
            @Override // com.starpilot.starview.QuickPanel.Actions
            public boolean experimental() {
                Telemetry telemetry;
                telemetry = MainActivity.this.telemetry;
                return telemetry.pBool("ExperimentalMode", false);
            }

            @Override // com.starpilot.starview.QuickPanel.Actions
            public void toggleExperimental() {
                DataClient dataClient;
                dataClient = MainActivity.this.data;
                if (dataClient == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("data");
                    dataClient = null;
                }
                dataClient.control(new JSONObject(MapsKt.mapOf(TuplesKt.to("action", "exp_toggle"))));
            }

            @Override // com.starpilot.starview.QuickPanel.Actions
            public int personality() {
                Telemetry telemetry;
                telemetry = MainActivity.this.telemetry;
                return telemetry.pInt("LongitudinalPersonality", 1);
            }

            @Override // com.starpilot.starview.QuickPanel.Actions
            public void setPersonality(int p2) {
                DataClient dataClient;
                dataClient = MainActivity.this.data;
                if (dataClient == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("data");
                    dataClient = null;
                }
                dataClient.control(new JSONObject(MapsKt.mapOf(TuplesKt.to("action", "param"), TuplesKt.to("key", "LongitudinalPersonality"), TuplesKt.to("value", Integer.valueOf(p2)))));
            }

            @Override // com.starpilot.starview.QuickPanel.Actions
            public boolean focus() {
                return HudPresets.INSTANCE.focusOn(MainActivity.this);
            }

            @Override // com.starpilot.starview.QuickPanel.Actions
            public void toggleFocus() {
                HudLayout hudLayout;
                HudPresets hudPresets = HudPresets.INSTANCE;
                MainActivity mainActivity = MainActivity.this;
                hudLayout = MainActivity.this.hud;
                if (hudLayout == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("hud");
                    hudLayout = null;
                }
                hudPresets.toggleFocus(mainActivity, hudLayout);
                MainActivity.this.layoutHud();
                MainActivity.this.applyHud();
            }

            @Override // com.starpilot.starview.QuickPanel.Actions
            public boolean mapOpen() {
                boolean mapOpen;
                mapOpen = MainActivity.this.getMapOpen();
                return mapOpen;
            }

            @Override // com.starpilot.starview.QuickPanel.Actions
            public void toggleMap() {
                boolean mapOpen;
                mapOpen = MainActivity.this.getMapOpen();
                if (mapOpen) {
                    MainActivity.this.closeMap();
                } else {
                    MainActivity.this.openMap();
                }
            }

            @Override // com.starpilot.starview.QuickPanel.Actions
            public boolean sceneOn() {
                UiSettings uiSettings;
                uiSettings = MainActivity.this.settings;
                if (uiSettings == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("settings");
                    uiSettings = null;
                }
                return uiSettings.get("scene_view");
            }

            @Override // com.starpilot.starview.QuickPanel.Actions
            public void toggleScene() {
                UiSettings uiSettings;
                UiSettings uiSettings2;
                uiSettings = MainActivity.this.settings;
                UiSettings uiSettings3 = null;
                if (uiSettings == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("settings");
                    uiSettings = null;
                }
                uiSettings2 = MainActivity.this.settings;
                if (uiSettings2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("settings");
                } else {
                    uiSettings3 = uiSettings2;
                }
                uiSettings.set("scene_view", !uiSettings3.get("scene_view"));
                MainActivity.this.uiTickNow();
            }

            @Override // com.starpilot.starview.QuickPanel.Actions
            public void listen() {
                MainActivity.this.listenVoice();
            }

            @Override // com.starpilot.starview.QuickPanel.Actions
            public boolean saver() {
                boolean saver;
                saver = MainActivity.this.getSaver();
                return saver;
            }

            @Override // com.starpilot.starview.QuickPanel.Actions
            public void toggleSaver() {
                boolean saver;
                MainActivity mainActivity = MainActivity.this;
                saver = MainActivity.this.getSaver();
                mainActivity.setSaver(!saver);
            }

            @Override // com.starpilot.starview.QuickPanel.Actions
            public void door(String cmd) {
                Intrinsics.checkNotNullParameter(cmd, "cmd");
                MainActivity.this.doorCommand(cmd);
            }

            @Override // com.starpilot.starview.QuickPanel.Actions
            public void close() {
                QuickPanel it;
                FrameLayout frameLayout;
                it = MainActivity.this.quickPanel;
                if (it != null) {
                    frameLayout = MainActivity.this.root;
                    if (frameLayout == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("root");
                        frameLayout = null;
                    }
                    frameLayout.removeView(it);
                }
                MainActivity.this.quickPanel = null;
            }
        });
        this.quickPanel = p;
        FrameLayout frameLayout = this.root;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout = null;
        }
        frameLayout.addView(p, new FrameLayout.LayoutParams(-1, -1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean getMapOpen() {
        MapPane mapPane = this.mapPane;
        return mapPane != null && mapPane.getVisibility() == 0;
    }

    private final boolean getMapRight() {
        UiSettings uiSettings = this.settings;
        if (uiSettings == null) {
            Intrinsics.throwUninitializedPropertyAccessException("settings");
            uiSettings = null;
        }
        return Intrinsics.areEqual(uiSettings.getStr("map_side", "left"), "right");
    }

    /* JADX WARN: Type inference failed for: r1v9, types: [com.starpilot.starview.MainActivity$setupMapFrame$2] */
    private final void setupMapFrame() {
        ViewGroup content = (ViewGroup) findViewById(16908290);
        FrameLayout frameLayout = this.root;
        FrameLayout frameLayout2 = null;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout = null;
        }
        content.removeView(frameLayout);
        FrameLayout $this$setupMapFrame_u24lambda_u24213 = new FrameLayout(this);
        $this$setupMapFrame_u24lambda_u24213.setBackgroundColor(-16777216);
        this.outer = $this$setupMapFrame_u24lambda_u24213;
        FrameLayout frameLayout3 = this.outer;
        if (frameLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("outer");
            frameLayout3 = null;
        }
        FrameLayout frameLayout4 = this.root;
        if (frameLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout4 = null;
        }
        frameLayout3.addView(frameLayout4, new FrameLayout.LayoutParams(-1, -1));
        FrameLayout frameLayout5 = this.outer;
        if (frameLayout5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("outer");
            frameLayout5 = null;
        }
        content.addView(frameLayout5, new FrameLayout.LayoutParams(-1, -1));
        View r1 = new View(MainActivity.this) { // from class: com.starpilot.starview.MainActivity$setupMapFrame$2
            private final Paint p = new Paint(1);

            @Override // android.view.View
            protected void onDraw(Canvas c) {
                int dp;
                int dp2;
                int dp3;
                int dp4;
                int dp5;
                int dp6;
                int dp7;
                int dp8;
                Intrinsics.checkNotNullParameter(c, "c");
                float w = getWidth();
                float h = getHeight();
                this.p.setColor(Color.rgb(16, 18, 22));
                float f = 2;
                dp = MainActivity.this.dp(3);
                dp2 = MainActivity.this.dp(3);
                c.drawRect((w / f) - dp, 0.0f, (w / f) + dp2, h, this.p);
                this.p.setColor(Color.argb(220, 210, 215, 225));
                dp3 = MainActivity.this.dp(3);
                float f2 = (w / f) - dp3;
                dp4 = MainActivity.this.dp(36);
                float f3 = (h / f) - dp4;
                dp5 = MainActivity.this.dp(3);
                float f4 = dp5 + (w / f);
                dp6 = MainActivity.this.dp(36);
                float f5 = (h / f) + dp6;
                dp7 = MainActivity.this.dp(3);
                float f6 = dp7;
                dp8 = MainActivity.this.dp(3);
                c.drawRoundRect(f2, f3, f4, f5, f6, dp8, this.p);
            }
        };
        r1.setVisibility(8);
        r1.setElevation(30.0f);
        this.divider = r1;
        FrameLayout frameLayout6 = this.outer;
        if (frameLayout6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("outer");
            frameLayout6 = null;
        }
        View view = this.divider;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("divider");
            view = null;
        }
        frameLayout6.addView(view, new FrameLayout.LayoutParams(dp(28), -1));
        final Ref.IntRef dragW = new Ref.IntRef();
        final Ref.LongRef lastApply = new Ref.LongRef();
        View view2 = this.divider;
        if (view2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("divider");
            view2 = null;
        }
        view2.setOnTouchListener(new View.OnTouchListener() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda29
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view3, MotionEvent motionEvent) {
                boolean z;
                z = MainActivity.setupMapFrame$lambda$215(MainActivity.this, dragW, lastApply, view3, motionEvent);
                return z;
            }
        });
        FrameLayout frameLayout7 = this.outer;
        if (frameLayout7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("outer");
            frameLayout7 = null;
        }
        frameLayout7.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda40
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view3, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                MainActivity.setupMapFrame$lambda$216(MainActivity.this, view3, i, i2, i3, i4, i5, i6, i7, i8);
            }
        });
        UiSettings uiSettings = this.settings;
        if (uiSettings == null) {
            Intrinsics.throwUninitializedPropertyAccessException("settings");
            uiSettings = null;
        }
        if (uiSettings.get("map_open")) {
            FrameLayout frameLayout8 = this.outer;
            if (frameLayout8 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("outer");
            } else {
                frameLayout2 = frameLayout8;
            }
            frameLayout2.post(new Runnable() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda51
                @Override // java.lang.Runnable
                public final void run() {
                    MainActivity.this.openMap();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean setupMapFrame$lambda$215(MainActivity this$0, Ref.IntRef $dragW, Ref.LongRef $lastApply, View view, MotionEvent e) {
        FrameLayout frameLayout = this$0.outer;
        UiSettings uiSettings = null;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("outer");
            frameLayout = null;
        }
        int total = frameLayout.getWidth();
        float rawX = e.getRawX();
        FrameLayout frameLayout2 = this$0.outer;
        if (frameLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("outer");
            frameLayout2 = null;
        }
        float x = rawX - this$0.locationOnScreenX(frameLayout2);
        int w = MathKt.roundToInt(this$0.getMapRight() ? total - x : x);
        switch (e.getActionMasked()) {
            case 0:
                $dragW.element = this$0.mapWidth();
                break;
            case 1:
            case 3:
                if ($dragW.element >= total * 0.17f) {
                    UiSettings uiSettings2 = this$0.settings;
                    if (uiSettings2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("settings");
                    } else {
                        uiSettings = uiSettings2;
                    }
                    uiSettings.setFloat("map_frac", $dragW.element / total);
                    this$0.layoutMap(this$0.mapWidth());
                    break;
                } else {
                    this$0.closeMap();
                    break;
                }
            case 2:
                $dragW.element = RangesKt.coerceIn(w, 0, MathKt.roundToInt(total * 0.72f));
                long now = System.currentTimeMillis();
                if (now - $lastApply.element > 70) {
                    $lastApply.element = now;
                    this$0.layoutMap(RangesKt.coerceAtLeast($dragW.element, this$0.dp(220)));
                }
                this$0.placeDivider($dragW.element);
                break;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupMapFrame$lambda$216(MainActivity this$0, View view, int l, int i, int r, int i2, int ol, int i3, int orr, int i4) {
        if (r - l == orr - ol || !this$0.getMapOpen()) {
            return;
        }
        this$0.layoutMap(this$0.mapWidth());
    }

    private final float locationOnScreenX(View $this$locationOnScreenX) {
        int[] a = new int[2];
        $this$locationOnScreenX.getLocationOnScreen(a);
        return a[0];
    }

    private final int mapWidth() {
        FrameLayout frameLayout = this.outer;
        UiSettings uiSettings = null;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("outer");
            frameLayout = null;
        }
        Integer valueOf = Integer.valueOf(frameLayout.getWidth());
        int it = valueOf.intValue();
        if (!(it > 0)) {
            valueOf = null;
        }
        int total = valueOf != null ? valueOf.intValue() : getResources().getDisplayMetrics().widthPixels;
        float f = total;
        UiSettings uiSettings2 = this.settings;
        if (uiSettings2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("settings");
        } else {
            uiSettings = uiSettings2;
        }
        return RangesKt.coerceIn(MathKt.roundToInt(f * uiSettings.getFloat("map_frac", 0.4f)), RangesKt.coerceAtMost(dp(260), total / 2), MathKt.roundToInt(total * 0.72f));
    }

    private final void layoutMap(int w) {
        MapPane pane = this.mapPane;
        if (pane == null) {
            return;
        }
        int g = getMapRight() ? 8388613 : 8388611;
        pane.setLayoutParams(new FrameLayout.LayoutParams(w, -1, g));
        FrameLayout frameLayout = this.root;
        FrameLayout frameLayout2 = null;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout = null;
        }
        ViewGroup.LayoutParams layoutParams = frameLayout.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) layoutParams;
        lp.setMarginStart(getMapRight() ? 0 : w);
        lp.setMarginEnd(getMapRight() ? w : 0);
        FrameLayout frameLayout3 = this.root;
        if (frameLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
        } else {
            frameLayout2 = frameLayout3;
        }
        frameLayout2.setLayoutParams(lp);
        placeDivider(w);
    }

    private final void placeDivider(int w) {
        FrameLayout frameLayout = this.outer;
        View view = null;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("outer");
            frameLayout = null;
        }
        int total = frameLayout.getWidth();
        View view2 = this.divider;
        if (view2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("divider");
        } else {
            view = view2;
        }
        view.setTranslationX((getMapRight() ? total - w : w) - dp(14));
    }

    public final void openMap() {
        if (this.outer == null) {
            return;
        }
        MapPane pane = this.mapPane;
        DataClient dataClient = null;
        if (pane == null) {
            pane = new MapPane(this, new MapPane.Host() { // from class: com.starpilot.starview.MainActivity$openMap$1
                @Override // com.starpilot.starview.MapPane.Host
                public Telemetry getTelemetry() {
                    Telemetry telemetry;
                    telemetry = MainActivity.this.telemetry;
                    return telemetry;
                }

                @Override // com.starpilot.starview.MapPane.Host
                public DataClient getData() {
                    DataClient dataClient2;
                    dataClient2 = MainActivity.this.data;
                    if (dataClient2 != null) {
                        return dataClient2;
                    }
                    Intrinsics.throwUninitializedPropertyAccessException("data");
                    return null;
                }

                @Override // com.starpilot.starview.MapPane.Host
                public UiSettings getSettings() {
                    UiSettings uiSettings;
                    uiSettings = MainActivity.this.settings;
                    if (uiSettings != null) {
                        return uiSettings;
                    }
                    Intrinsics.throwUninitializedPropertyAccessException("settings");
                    return null;
                }

                @Override // com.starpilot.starview.MapPane.Host
                public boolean getNight() {
                    boolean z;
                    z = MainActivity.this.night;
                    return z;
                }

                @Override // com.starpilot.starview.MapPane.Host
                public void openSearch() {
                    MainActivity.this.openDestinations();
                }

                @Override // com.starpilot.starview.MapPane.Host
                public void closeMap() {
                    MainActivity.this.closeMap();
                }

                @Override // com.starpilot.starview.MapPane.Host
                public void navigateTo(String name, String address, double lat, double lon) {
                    Intrinsics.checkNotNullParameter(name, "name");
                    Intrinsics.checkNotNullParameter(address, "address");
                    MainActivity.this.driveTo(name, address, lat, lon);
                }
            });
            this.mapPane = pane;
            FrameLayout frameLayout = this.outer;
            if (frameLayout == null) {
                Intrinsics.throwUninitializedPropertyAccessException("outer");
                frameLayout = null;
            }
            MapPane mapPane = pane;
            FrameLayout frameLayout2 = this.outer;
            if (frameLayout2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("outer");
                frameLayout2 = null;
            }
            View view = this.divider;
            if (view == null) {
                Intrinsics.throwUninitializedPropertyAccessException("divider");
                view = null;
            }
            frameLayout.addView(mapPane, frameLayout2.indexOfChild(view), new FrameLayout.LayoutParams(mapWidth(), -1));
        }
        pane.setVisibility(0);
        View view2 = this.divider;
        if (view2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("divider");
            view2 = null;
        }
        view2.setVisibility(0);
        UiSettings uiSettings = this.settings;
        if (uiSettings == null) {
            Intrinsics.throwUninitializedPropertyAccessException("settings");
            uiSettings = null;
        }
        uiSettings.set("map_open", true);
        layoutMap(mapWidth());
        Nav nav = Nav.INSTANCE;
        DataClient dataClient2 = this.data;
        if (dataClient2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("data");
        } else {
            dataClient = dataClient2;
        }
        nav.refresh(dataClient);
    }

    public final void closeMap() {
        MapPane pane = this.mapPane;
        if (pane == null) {
            return;
        }
        pane.setVisibility(8);
        View view = this.divider;
        FrameLayout frameLayout = null;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("divider");
            view = null;
        }
        view.setVisibility(8);
        UiSettings uiSettings = this.settings;
        if (uiSettings == null) {
            Intrinsics.throwUninitializedPropertyAccessException("settings");
            uiSettings = null;
        }
        uiSettings.set("map_open", false);
        pane.getTiles().clearMemory();
        FrameLayout frameLayout2 = this.root;
        if (frameLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout2 = null;
        }
        ViewGroup.LayoutParams layoutParams = frameLayout2.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) layoutParams;
        lp.setMarginStart(0);
        lp.setMarginEnd(0);
        FrameLayout frameLayout3 = this.root;
        if (frameLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
        } else {
            frameLayout = frameLayout3;
        }
        frameLayout.setLayoutParams(lp);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0094, code lost:
    
        if (r2 == null) goto L30;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void navTick(long now) {
        DataClient dataClient;
        DataClient dataClient2;
        String dest;
        MapPane pane = this.mapPane;
        UiSettings uiSettings = null;
        if (pane == null || !getMapOpen()) {
            Nav nav = Nav.INSTANCE;
            DataClient dataClient3 = this.data;
            if (dataClient3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("data");
                dataClient = null;
            } else {
                dataClient = dataClient3;
            }
            Nav.poll$default(nav, dataClient, false, now, false, 8, null);
        } else {
            pane.tick(now);
        }
        MapboxRelay mapboxRelay = MapboxRelay.INSTANCE;
        MainActivity mainActivity = this;
        DataClient dataClient4 = this.data;
        if (dataClient4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("data");
            dataClient2 = null;
        } else {
            dataClient2 = dataClient4;
        }
        mapboxRelay.tick(mainActivity, dataClient2, this.host, now);
        if ((Favorites.INSTANCE.getList().isEmpty() && this.tick % 200 == 0) || this.tick % 2400 == 0) {
            Favorites favorites = Favorites.INSTANCE;
            DataClient dataClient5 = this.data;
            if (dataClient5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("data");
                dataClient5 = null;
            }
            Favorites.load$default(favorites, dataClient5, null, 2, null);
        }
        JSONObject it = Nav.INSTANCE.getDestination();
        if (it != null) {
            dest = it.optDouble("latitude") + "," + it.optDouble("longitude");
        }
        dest = "";
        if (!Intrinsics.areEqual(dest, this.lastNavDest)) {
            if (dest.length() > 0) {
                if (!(this.lastNavDest.length() > 0) && Nav.INSTANCE.getLoaded() && !getMapOpen()) {
                    UiSettings uiSettings2 = this.settings;
                    if (uiSettings2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("settings");
                    } else {
                        uiSettings = uiSettings2;
                    }
                    if (uiSettings.get("map_auto_open")) {
                        openMap();
                    }
                }
            }
            this.lastNavDest = dest;
        }
    }

    private final void pivotToAnchor(View v) {
        float width;
        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        FrameLayout.LayoutParams layoutParams2 = layoutParams instanceof FrameLayout.LayoutParams ? (FrameLayout.LayoutParams) layoutParams : null;
        if (layoutParams2 == null) {
            return;
        }
        int g = layoutParams2.gravity;
        int hg = g & 7;
        int vg = g & 112;
        float f = 0.0f;
        switch (hg) {
            case 1:
                width = v.getWidth() / 2.0f;
                break;
            case 5:
            case 8388613:
                width = v.getWidth();
                break;
            default:
                width = 0.0f;
                break;
        }
        v.setPivotX(width);
        switch (vg) {
            case 16:
                f = v.getHeight() / 2.0f;
                break;
            case 80:
                f = v.getHeight();
                break;
        }
        v.setPivotY(f);
    }

    private final void layoutHome() {
        FrameLayout frameLayout = this.root;
        OffroadHome offroadHome = null;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout = null;
        }
        int rw = frameLayout.getWidth();
        FrameLayout frameLayout2 = this.root;
        if (frameLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout2 = null;
        }
        int rh = frameLayout2.getHeight();
        if (rw == 0 || rh == 0) {
            return;
        }
        float d = getResources().getDisplayMetrics().density;
        float k = RangesKt.coerceIn((rw / d) / 1150.0f, 0.62f, 1.0f);
        String key = rw + " x " + rh + " x " + k;
        if (Intrinsics.areEqual(key, this.lastHomeKey)) {
            return;
        }
        this.lastHomeKey = key;
        int side = dp(240);
        LinearLayout linearLayout = this.sidebar;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sidebar");
            linearLayout = null;
        }
        ViewGroup.LayoutParams layoutParams = linearLayout.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
        FrameLayout.LayoutParams it = (FrameLayout.LayoutParams) layoutParams;
        it.width = side;
        it.height = (int) (rh / k);
        LinearLayout linearLayout2 = this.sidebar;
        if (linearLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sidebar");
            linearLayout2 = null;
        }
        linearLayout2.setLayoutParams(it);
        LinearLayout linearLayout3 = this.sidebar;
        if (linearLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sidebar");
            linearLayout3 = null;
        }
        linearLayout3.setPivotX(0.0f);
        LinearLayout linearLayout4 = this.sidebar;
        if (linearLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sidebar");
            linearLayout4 = null;
        }
        linearLayout4.setPivotY(0.0f);
        LinearLayout linearLayout5 = this.sidebar;
        if (linearLayout5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sidebar");
            linearLayout5 = null;
        }
        linearLayout5.setScaleX(k);
        LinearLayout linearLayout6 = this.sidebar;
        if (linearLayout6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sidebar");
            linearLayout6 = null;
        }
        linearLayout6.setScaleY(k);
        OffroadHome offroadHome2 = this.offroadHome;
        if (offroadHome2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("offroadHome");
            offroadHome2 = null;
        }
        ViewGroup.LayoutParams layoutParams2 = offroadHome2.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams2, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
        FrameLayout.LayoutParams it2 = (FrameLayout.LayoutParams) layoutParams2;
        it2.width = (int) ((rw - (side * k)) / k);
        it2.height = (int) (rh / k);
        it2.setMarginStart((int) (side * k));
        OffroadHome offroadHome3 = this.offroadHome;
        if (offroadHome3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("offroadHome");
            offroadHome3 = null;
        }
        offroadHome3.setLayoutParams(it2);
        OffroadHome offroadHome4 = this.offroadHome;
        if (offroadHome4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("offroadHome");
            offroadHome4 = null;
        }
        offroadHome4.setPivotX(0.0f);
        OffroadHome offroadHome5 = this.offroadHome;
        if (offroadHome5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("offroadHome");
            offroadHome5 = null;
        }
        offroadHome5.setPivotY(0.0f);
        OffroadHome offroadHome6 = this.offroadHome;
        if (offroadHome6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("offroadHome");
            offroadHome6 = null;
        }
        offroadHome6.setScaleX(k);
        OffroadHome offroadHome7 = this.offroadHome;
        if (offroadHome7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("offroadHome");
        } else {
            offroadHome = offroadHome7;
        }
        offroadHome.setScaleY(k);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void layoutHud() {
        float ws = windowScale();
        HudLayout hudLayout = this.hud;
        FrameLayout frameLayout = null;
        if (hudLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("hud");
            hudLayout = null;
        }
        hudLayout.setWindowScale(ws);
        HudLayout hudLayout2 = this.hud;
        if (hudLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("hud");
            hudLayout2 = null;
        }
        FrameLayout frameLayout2 = this.root;
        if (frameLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
        } else {
            frameLayout = frameLayout2;
        }
        hudLayout2.apply(frameLayout);
        layoutHome();
        if (this.lastWs < 0.0f) {
            Iterator<View> it = getFloating().iterator();
            while (it.hasNext()) {
                it.next().addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda16
                    @Override // android.view.View.OnLayoutChangeListener
                    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                        MainActivity.layoutHud$lambda$224(MainActivity.this, view, i, i2, i3, i4, i5, i6, i7, i8);
                    }
                });
            }
        }
        if (ws == this.lastWs) {
            return;
        }
        this.lastWs = ws;
        for (View v : getFloating()) {
            v.setScaleX(ws);
            v.setScaleY(ws);
            pivotToAnchor(v);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void layoutHud$lambda$224(MainActivity this$0, View vv, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        Intrinsics.checkNotNull(vv);
        this$0.pivotToAnchor(vv);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void placeVideo() {
        OverlayView overlayView = this.overlay;
        SurfaceView surfaceView = null;
        if (overlayView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("overlay");
            overlayView = null;
        }
        Projection p = overlayView.getProj();
        if (this.frameW == 0 || !p.getValid()) {
            return;
        }
        SurfaceView surfaceView2 = this.videoView;
        if (surfaceView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("videoView");
            surfaceView2 = null;
        }
        ViewGroup.LayoutParams layoutParams = surfaceView2.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) layoutParams;
        lp.gravity = 8388659;
        lp.width = MathKt.roundToInt(this.frameW * p.getVideoScale());
        lp.height = MathKt.roundToInt(this.frameH * p.getVideoScale());
        SurfaceView surfaceView3 = this.videoView;
        if (surfaceView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("videoView");
            surfaceView3 = null;
        }
        surfaceView3.setLayoutParams(lp);
        SurfaceView surfaceView4 = this.videoView;
        if (surfaceView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("videoView");
            surfaceView4 = null;
        }
        surfaceView4.setTranslationX((float) p.getVideoTx());
        SurfaceView surfaceView5 = this.videoView;
        if (surfaceView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("videoView");
        } else {
            surfaceView = surfaceView5;
        }
        surfaceView.setTranslationY((float) p.getVideoTy());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateTheme() {
        boolean n;
        TextView textView;
        UiSettings uiSettings = this.settings;
        if (uiSettings == null) {
            Intrinsics.throwUninitializedPropertyAccessException("settings");
            uiSettings = null;
        }
        String mode = uiSettings.getStr("theme_mode", "auto");
        Telemetry t = this.telemetry;
        if (Intrinsics.areEqual(mode, "day")) {
            n = false;
        } else if (Intrinsics.areEqual(mode, "night")) {
            n = true;
        } else {
            double lat = t.getGpsLat();
            double lon = t.getGpsLon();
            if (Double.isNaN(lat)) {
                try {
                    JSONObject j = new JSONObject(Telemetry.pStr$default(t, "LastGPSPosition", null, 2, null));
                    try {
                        lat = j.optDouble("latitude", Double.NaN);
                        lon = j.optDouble("longitude", Double.NaN);
                    } catch (Exception e) {
                    }
                } catch (Exception e2) {
                }
                if (!Double.isNaN(lat) || Double.isNaN(lon)) {
                    int h = Calendar.getInstance().get(11);
                    n = h >= 6 || h >= 19;
                } else {
                    n = Theme.INSTANCE.isNight(lat, lon, System.currentTimeMillis());
                }
            }
            if (Double.isNaN(lat)) {
            }
            int h2 = Calendar.getInstance().get(11);
            if (h2 >= 6) {
            }
        }
        if (n == this.night && this.lastThemeApplied) {
            return;
        }
        this.night = n;
        this.lastThemeApplied = true;
        if (this.hud != null) {
            HudLayout hudLayout = this.hud;
            if (hudLayout == null) {
                Intrinsics.throwUninitializedPropertyAccessException("hud");
                hudLayout = null;
            }
            hudLayout.load(n ? "night" : "day");
            layoutHud();
        }
        int fg = n ? -1 : Color.rgb(29, 29, 31);
        int fg2 = n ? Color.argb(200, 255, 255, 255) : Color.rgb(110, 110, 115);
        TextView[] textViewArr = new TextView[3];
        TextView textView2 = this.speedTv;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("speedTv");
            textView2 = null;
        }
        textViewArr[0] = textView2;
        TextView textView3 = this.roadName;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("roadName");
            textView3 = null;
        }
        textViewArr[1] = textView3;
        TextView textView4 = this.compass;
        if (textView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("compass");
            textView4 = null;
        }
        textViewArr[2] = textView4;
        for (TextView tv : CollectionsKt.listOf((Object[]) textViewArr)) {
            tv.setTextColor(fg);
            tv.setShadowLayer(n ? 12.0f : 0.0f, 0.0f, 0.0f, -16777216);
        }
        if (this.clockTv != null) {
            TextView[] textViewArr2 = new TextView[2];
            TextView textView5 = this.clockTv;
            if (textView5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("clockTv");
                textView5 = null;
            }
            textViewArr2[0] = textView5;
            TextView textView6 = this.modeText;
            if (textView6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("modeText");
                textView6 = null;
            }
            textViewArr2[1] = textView6;
            for (TextView tv2 : CollectionsKt.listOf((Object[]) textViewArr2)) {
                tv2.setTextColor(fg);
                tv2.setShadowLayer(n ? 2.0f : 0.0f, 2.0f, 2.0f, Color.argb(170, 0, 0, 0));
            }
        }
        TextView textView7 = this.speedUnit;
        if (textView7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("speedUnit");
            textView7 = null;
        }
        textView7.setTextColor(fg2);
        TextView textView8 = this.speedUnit;
        if (textView8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("speedUnit");
            textView8 = null;
        }
        textView8.setShadowLayer(n ? 8.0f : 0.0f, 0.0f, 0.0f, -16777216);
        TextView textView9 = this.devMetrics;
        if (textView9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("devMetrics");
            textView9 = null;
        }
        textView9.setTextColor(fg2);
        TextView textView10 = this.devMetrics;
        if (textView10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("devMetrics");
            textView = null;
        } else {
            textView = textView10;
        }
        textView.setShadowLayer(n ? 6.0f : 0.0f, 0.0f, 0.0f, -16777216);
        applyRootBg();
    }

    private final void applyRootBg() {
        int i;
        int i2;
        int i3;
        int rgb;
        FrameLayout frameLayout = this.root;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout = null;
        }
        if (this.sceneOn) {
            if (this.night) {
                i = 13;
                i2 = 18;
                i3 = 11;
            } else {
                i = 245;
                i2 = 247;
                i3 = 244;
            }
            rgb = Color.rgb(i3, i, i2);
        } else {
            rgb = -16777216;
        }
        frameLayout.setBackgroundColor(rgb);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int dp(int v) {
        return MathKt.roundToInt(v * getResources().getDisplayMetrics().density);
    }

    private final void startHudEdit() {
        FrameLayout frameLayout;
        HudLayout hudLayout;
        if (this.hudEditor != null) {
            return;
        }
        LinearLayout linearLayout = this.sidebar;
        FrameLayout frameLayout2 = null;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sidebar");
            linearLayout = null;
        }
        linearLayout.setVisibility(8);
        MainActivity mainActivity = this;
        FrameLayout frameLayout3 = this.root;
        if (frameLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout = null;
        } else {
            frameLayout = frameLayout3;
        }
        HudLayout hudLayout2 = this.hud;
        if (hudLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("hud");
            hudLayout = null;
        } else {
            hudLayout = hudLayout2;
        }
        HudEditor ed = new HudEditor(mainActivity, frameLayout, hudLayout, new Function0() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda106
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit startHudEdit$lambda$225;
                startHudEdit$lambda$225 = MainActivity.startHudEdit$lambda$225(MainActivity.this);
                return startHudEdit$lambda$225;
            }
        }, new Function0() { // from class: com.starpilot.starview.MainActivity$$ExternalSyntheticLambda117
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit startHudEdit$lambda$226;
                startHudEdit$lambda$226 = MainActivity.startHudEdit$lambda$226(MainActivity.this);
                return startHudEdit$lambda$226;
            }
        });
        this.hudEditor = ed;
        FrameLayout frameLayout4 = this.root;
        if (frameLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
        } else {
            frameLayout2 = frameLayout4;
        }
        frameLayout2.addView(ed, new FrameLayout.LayoutParams(-1, -1));
        applyHud();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit startHudEdit$lambda$225(MainActivity this$0) {
        this$0.applyHud();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit startHudEdit$lambda$226(MainActivity this$0) {
        this$0.stopHudEdit();
        return Unit.INSTANCE;
    }

    private final void stopHudEdit() {
        HudEditor ed = this.hudEditor;
        if (ed == null) {
            return;
        }
        FrameLayout frameLayout = this.root;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout = null;
        }
        frameLayout.removeView(ed);
        this.hudEditor = null;
        if (this.backupArmed) {
            SettingsBackup.INSTANCE.auto(this);
        }
        applyHud();
    }

    private final void applyHudGrey() {
        UiSettings uiSettings = this.settings;
        if (uiSettings == null) {
            Intrinsics.throwUninitializedPropertyAccessException("settings");
            uiSettings = null;
        }
        boolean grey = uiSettings.get("hud_grey");
        if (Intrinsics.areEqual(Boolean.valueOf(grey), this.hudGrey)) {
            return;
        }
        this.hudGrey = Boolean.valueOf(grey);
        HudLayout hudLayout = this.hud;
        if (hudLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("hud");
            hudLayout = null;
        }
        Iterable $this$mapNotNull$iv = hudLayout.getItems();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv$iv : $this$mapNotNull$iv) {
            HudLayout.Item it = (HudLayout.Item) element$iv$iv$iv;
            FrameLayout frameLayout = this.root;
            if (frameLayout == null) {
                Intrinsics.throwUninitializedPropertyAccessException("root");
                frameLayout = null;
            }
            Iterable $this$mapNotNull$iv2 = $this$mapNotNull$iv;
            View findViewById = frameLayout.findViewById(it.getId());
            if (findViewById != null) {
                destination$iv$iv.add(findViewById);
            }
            $this$mapNotNull$iv = $this$mapNotNull$iv2;
        }
        List<View> views = CollectionsKt.plus(destination$iv$iv, (Iterable) CollectionsKt.listOfNotNull((Object[]) new View[]{findViewById(R.id.alertBox), findViewById(R.id.alertTint)}));
        for (View v : views) {
            if (grey) {
                v.setLayerType(2, this.greyPaint);
            } else {
                v.setLayerType(0, null);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void applyHud() {
        if (this.hud == null) {
            return;
        }
        layoutHud();
        applyHudGrey();
        boolean editing = this.hudEditor != null;
        boolean wasEditing = this.lastHudEditing && !editing;
        this.lastHudEditing = editing;
        HudLayout hudLayout = this.hud;
        if (hudLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("hud");
            hudLayout = null;
        }
        for (HudLayout.Item item : hudLayout.getItems()) {
            FrameLayout frameLayout = this.root;
            if (frameLayout == null) {
                Intrinsics.throwUninitializedPropertyAccessException("root");
                frameLayout = null;
            }
            View v = frameLayout.findViewById(item.getId());
            if (v != null) {
                HudLayout hudLayout2 = this.hud;
                if (hudLayout2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("hud");
                    hudLayout2 = null;
                }
                boolean on = hudLayout2.enabled(item.getKey());
                if (editing) {
                    String k = this.hudSettingKeys.get(item.getKey());
                    if (k != null && on) {
                        UiSettings uiSettings = this.settings;
                        if (uiSettings == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("settings");
                            uiSettings = null;
                        }
                        if (!uiSettings.get(k)) {
                            UiSettings uiSettings2 = this.settings;
                            if (uiSettings2 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("settings");
                                uiSettings2 = null;
                            }
                            uiSettings2.set(k, true);
                        }
                    }
                    v.setVisibility(0);
                    v.setAlpha(1.0f);
                } else {
                    if (wasEditing) {
                        v.setAlpha(1.0f);
                    }
                    if (!on) {
                        v.setVisibility(8);
                    }
                }
            }
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        SideCam.INSTANCE.close();
        if (isFinishing()) {
            LinkService.Companion.stop(this);
        }
        DataClient dataClient = null;
        this.ui.removeCallbacksAndMessages(null);
        stopVideo();
        syncDetector(false);
        Detector detector = this.detectorRef;
        if (detector != null) {
            detector.shutdown();
        }
        this.detectorRef = null;
        LayoutSeg layoutSeg = this.layoutSeg;
        if (layoutSeg == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutSeg");
            layoutSeg = null;
        }
        layoutSeg.shutdown();
        DataClient dataClient2 = this.data;
        if (dataClient2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("data");
        } else {
            dataClient = dataClient2;
        }
        dataClient.close();
    }
}
