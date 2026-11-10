package com.starpilot.starview;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
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
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import com.starpilot.starview.AmySynth;
import com.starpilot.starview.AmyVoice;
import com.starpilot.starview.DriveState;
import com.starpilot.starview.GalaxyApi;
import com.starpilot.starview.GalaxyExtras;
import com.starpilot.starview.GalaxySettingsView;
import com.starpilot.starview.GalaxyTools;
import com.starpilot.starview.IdeaSpots;
import com.starpilot.starview.MapdMaps;
import com.starpilot.starview.NewIdeas;
import com.starpilot.starview.Ntrip;
import com.starpilot.starview.NtripCore;
import com.starpilot.starview.RtkTools;
import com.starpilot.starview.SettingIcons;
import com.starpilot.starview.SettingsActivity;
import com.starpilot.starview.SettingsIndex;
import com.starpilot.starview.StorageInfo;
import com.starpilot.starview.TermWeb;
import com.starpilot.starview.UiSettings;
import com.starpilot.starview.VoiceStudio;
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
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.random.Random;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.json.JSONObject;

/* compiled from: SettingsActivity.kt */
public final class SettingsActivity extends AppCompatActivity implements GxHost {
    public static final String EXTRA_HOST = "host";
    public static final String EXTRA_TAB = "tab";
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
    final Handler handler;
    private FrameLayout headerSlot;
    String host;
    private SettingsLauncher launcher;
    private DriveState.OffroadBar offroadBar;
    private Function1<? super Uri, Unit> onFilePicked;
    private Function1<? super String, Unit> onModelPicked;
    private final HashMap<String, View> panels;
    private final ActivityResultLauncher<String[]> pickAny;
    private final ActivityResultLauncher<String[]> pickModel;
    private FrameLayout root;
    private final SettingsActivity$ticker$1 ticker;
    private View togglesSearch;
    private CarUi ui;
    private final SettingsActivity$valuePoll$1 valuePoll;
    private String webReturn;
    public static final Companion Companion = new Companion(null);
    private static final Set<String> PLACED_PAGES = SetsKt.setOf((Object[]) new String[]{"lane", "lane_change", "torque", "aol", "profiles", "conditional", "curve", "traffic", "aggressive", "standard", "relaxed", "slc", "vehicle", "wheel", "sounds", "sentry", "vasm", "data", "developer", "display", "appearance", "pip"});
    private static final HashMap<String, String> GALAXY_SUB_TITLES = new HashMap<>();
    private static final Map<String, String> GALAXY_TITLES = MapsKt.mapOf(TuplesKt.to("personalities", "Personalities"), TuplesKt.to("slc", "Speed limits"), TuplesKt.to("wheel", "Wheel controls"), TuplesKt.to("sounds", "Sounds & alerts"), TuplesKt.to("data", "Data uploads"), TuplesKt.to("developer", "Developer"), TuplesKt.to("display", "Screen"), TuplesKt.to("appearance", "Driving screen"), TuplesKt.to("pip", "Blind spot camera"), TuplesKt.to("vehicle", "Vehicle"));

    /* JADX WARN: Type inference failed for: r0v13, types: [com.starpilot.starview.SettingsActivity$ticker$1] */
    /* JADX WARN: Type inference failed for: r0v14, types: [com.starpilot.starview.SettingsActivity$valuePoll$1] */
    public SettingsActivity() {
        ActivityResultLauncher<String[]> registerForActivityResult = registerForActivityResult(new ActivityResultContracts.OpenDocument(), new ActivityResultCallback() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda148
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                SettingsActivity.pickModel$lambda$2(SettingsActivity.this, (Uri) obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(registerForActivityResult, "registerForActivityResult(...)");
        this.pickModel = registerForActivityResult;
        ActivityResultLauncher<String[]> registerForActivityResult2 = registerForActivityResult(new ActivityResultContracts.OpenDocument(), new ActivityResultCallback() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda149
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                SettingsActivity.pickAny$lambda$3(SettingsActivity.this, (Uri) obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(registerForActivityResult2, "registerForActivityResult(...)");
        this.pickAny = registerForActivityResult2;
        this.panels = new HashMap<>();
        this.handler = new Handler(Looper.getMainLooper());
        this.areaIndex = -1;
        this.driveModes = CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to("default", "Auto"), TuplesKt.to("onroad", "Onroad"), TuplesKt.to("offroad", "Offroad")});
        this.buildGroup = "tablet";
        this.ticker = new Runnable() { // from class: com.starpilot.starview.SettingsActivity$ticker$1
            @Override // java.lang.Runnable
            public void run() {
                FrameLayout frameLayout;
                View view;
                View view2;
                View view3;
                View view4;
                DriveState.OffroadBar offroadBar;
                Handler handler;
                View view5;
                boolean pageNeedsOffroad;
                TextView textView;
                frameLayout = SettingsActivity.this.contentHolder;
                if (frameLayout == null) {
                    textView = SettingsActivity.this.driveBtn;
                    if (textView != null) {
                        SettingsActivity.this.syncDriveState();
                    }
                }
                view = SettingsActivity.this.current;
                DevicePanel devicePanel = view instanceof DevicePanel ? (DevicePanel) view : null;
                if (devicePanel != null) {
                    devicePanel.onTick();
                }
                view2 = SettingsActivity.this.current;
                CatalogPanel catalogPanel = view2 instanceof CatalogPanel ? (CatalogPanel) view2 : null;
                if (catalogPanel != null) {
                    catalogPanel.onTick();
                }
                view3 = SettingsActivity.this.current;
                SettingsActivity.ComboPage comboPage = view3 instanceof SettingsActivity.ComboPage ? (SettingsActivity.ComboPage) view3 : null;
                if (comboPage != null) {
                    comboPage.tick();
                }
                view4 = SettingsActivity.this.current;
                GxPage gxPage = view4 instanceof GxPage ? (GxPage) view4 : null;
                if (gxPage != null) {
                    gxPage.tick();
                }
                offroadBar = SettingsActivity.this.offroadBar;
                if (offroadBar != null) {
                    SettingsActivity settingsActivity = SettingsActivity.this;
                    view5 = SettingsActivity.this.current;
                    pageNeedsOffroad = settingsActivity.pageNeedsOffroad(view5);
                    offroadBar.sync(pageNeedsOffroad);
                }
                handler = SettingsActivity.this.handler;
                handler.postDelayed(this, 700L);
            }
        };
        this.valuePoll = new Runnable() { // from class: com.starpilot.starview.SettingsActivity$valuePoll$1
            /* JADX WARN: Code restructure failed: missing block: B:4:0x0012, code lost:
            
                if ((r0 instanceof com.starpilot.starview.SettingsActivity.ComboPage) != false) goto L6;
             */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void run() {
                View view;
                GalaxyApi galaxyApi;
                Handler handler;
                View view2;
                view = SettingsActivity.this.current;
                if (!(view instanceof CatalogPanel)) {
                    view2 = SettingsActivity.this.current;
                }
                galaxyApi = SettingsActivity.this.api;
                if (galaxyApi != null) {
                    GalaxyApi.refreshValues$default(galaxyApi, null, 1, null);
                }
                handler = SettingsActivity.this.handler;
                handler.postDelayed(this, 4000L);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void pickModel$lambda$2(final SettingsActivity this$0, final Uri uri) {
        if (uri != null) {
            new Thread(new Runnable() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    SettingsActivity.pickModel$lambda$2$lambda$1(this$0, uri);
                }
            }).start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void pickModel$lambda$2$lambda$1(final SettingsActivity this$0, Uri $uri) {
        final String err = new LayoutSeg(this$0).importModel($uri);
        this$0.runOnUiThread(new Runnable() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda181
            @Override // java.lang.Runnable
            public final void run() {
                SettingsActivity.pickModel$lambda$2$lambda$1$lambda$0(this$0, err);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void pickModel$lambda$2$lambda$1$lambda$0(SettingsActivity this$0, String $err) {
        Function1<? super String, Unit> function1 = this$0.onModelPicked;
        if (function1 != null) {
            function1.invoke($err == null ? "Installed - the Scene view uses it from its next start" : $err);
        }
    }

    /* compiled from: SettingsActivity.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Set<String> getPLACED_PAGES() {
            return SettingsActivity.PLACED_PAGES;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:103:0x020b, code lost:
        
            if (r18.equals("tablet:ideas") == false) goto L185;
         */
        /* JADX WARN: Code restructure failed: missing block: B:122:0x0261, code lost:
        
            if (r18.equals("tablet:maps") == false) goto L185;
         */
        /* JADX WARN: Code restructure failed: missing block: B:123:0x031c, code lost:
        
            r7 = "map";
         */
        /* JADX WARN: Code restructure failed: missing block: B:164:0x0318, code lost:
        
            if (r18.equals("tablet:nav") == false) goto L185;
         */
        /* JADX WARN: Code restructure failed: missing block: B:63:0x0183, code lost:
        
            if (r1.equals("Road") == false) goto L65;
         */
        /* JADX WARN: Code restructure failed: missing block: B:64:0x018f, code lost:
        
            r12 = "map";
         */
        /* JADX WARN: Code restructure failed: missing block: B:66:0x018c, code lost:
        
            if (r1.equals("Map") == false) goto L65;
         */
        /* JADX WARN: Code restructure failed: missing block: B:72:0x01a3, code lost:
        
            if (r1.equals("Effects") == false) goto L65;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final SettingIcons.Icon railIcon(String page) {
            String str;
            Intrinsics.checkNotNullParameter(page, "page");
            if (Intrinsics.areEqual(page, "drivingmap")) {
                return new SettingIcons.Icon("gauge2", "speed", false, 4, null);
            }
            if (Intrinsics.areEqual(page, "toolshome")) {
                return new SettingIcons.Icon("widgets", "device", false, 4, null);
            }
            if (Intrinsics.areEqual(page, "connections")) {
                return new SettingIcons.Icon("link", "device", false, 4, null);
            }
            if (Intrinsics.areEqual(page, "health")) {
                return new SettingIcons.Icon("plots", "device", false, 4, null);
            }
            if (Intrinsics.areEqual(page, "journal:last")) {
                return new SettingIcons.Icon("car", "tablet", false, 4, null);
            }
            if (Intrinsics.areEqual(page, "journal:trends")) {
                return new SettingIcons.Icon("plots", "tablet", false, 4, null);
            }
            if (Intrinsics.areEqual(page, "journal:alerts")) {
                return new SettingIcons.Icon("bell", "tablet", false, 4, null);
            }
            if (Intrinsics.areEqual(page, "journal:spots")) {
                return new SettingIcons.Icon("pin", "tablet", false, 4, null);
            }
            if (Intrinsics.areEqual(page, "profiles")) {
                return new SettingIcons.Icon("standard", "person", false, 4, null);
            }
            if (StringsKt.startsWith(page, "galaxy:", false)) {
                return SettingIcons.INSTANCE.forPage(StringsKt.removePrefix(page, (CharSequence) "galaxy:"));
            }
            if (Intrinsics.areEqual(page, "quickselect")) {
                return new SettingIcons.Icon("star", "display", false, 4, null);
            }
            if (StringsKt.startsWith(page, "gweb:", false)) {
                return new SettingIcons.Icon(toolIcon(StringsKt.substringBefore(StringsKt.removePrefix(page, (CharSequence) "gweb:"), "|", (StringsKt.removePrefix(page, (CharSequence) "gweb:")))), "device", false, 4, null);
            }
            String str2 = "star";
            if (StringsKt.startsWith(page, "uigroup:", false)) {
                String removePrefix = StringsKt.removePrefix(page, (CharSequence) "uigroup:");
                switch (removePrefix.hashCode()) {
                    case -169275166:
                        break;
                    case 71895:
                        if (removePrefix.equals("HUD")) {
                            str2 = "widgets";
                            str = str2;
                            break;
                        }
                        str2 = "eye";
                        str = str2;
                    case 77116:
                        break;
                    case 2552640:
                        break;
                    case 73186279:
                        if (removePrefix.equals("Lanes")) {
                            str = "lane";
                            break;
                        }
                        str2 = "eye";
                        str = str2;
                        break;
                    case 2011082565:
                        if (removePrefix.equals("Camera")) {
                            str = "camera";
                            break;
                        }
                        str2 = "eye";
                        str = str2;
                        break;
                    default:
                        str2 = "eye";
                        str = str2;
                        break;
                }
                return new SettingIcons.Icon(str, "tablet", false, 4, null);
            }
            switch (page.hashCode()) {
                case -1844774923:
                    if (page.equals("tablet:blinker")) {
                        str2 = "blinker";
                        break;
                    }
                    str2 = "info";
                    break;
                case -1680479212:
                    if (page.equals("tablet:braking")) {
                        str2 = "decel";
                        break;
                    }
                    str2 = "info";
                    break;
                case -1486773745:
                    break;
                case -1486769323:
                    if (page.equals("tablet:rtk")) {
                        str2 = "satellite";
                        break;
                    }
                    str2 = "info";
                    break;
                case -1417203926:
                    if (page.equals("tablet:techstream")) {
                        str2 = "wrench";
                        break;
                    }
                    str2 = "info";
                    break;
                case -1134666246:
                    if (page.equals("keyfob")) {
                        str2 = "key";
                        break;
                    }
                    str2 = "info";
                    break;
                case -1073114497:
                    if (page.equals("native:terminal")) {
                        str2 = "terminal";
                        break;
                    }
                    str2 = "info";
                    break;
                case -1023481817:
                    if (page.equals("native:developer")) {
                        str2 = "console";
                        break;
                    }
                    str2 = "info";
                    break;
                case -960904562:
                    if (page.equals("activity:sensors")) {
                        str2 = "gps";
                        break;
                    }
                    str2 = "info";
                    break;
                case -661461902:
                    if (page.equals("tablet:connection")) {
                        str2 = "link";
                        break;
                    }
                    str2 = "info";
                    break;
                case -581968182:
                    if (page.equals("roaddata")) {
                        str2 = "lane";
                        break;
                    }
                    str2 = "info";
                    break;
                case 101886580:
                    if (page.equals("tablet:voicestudio")) {
                        str2 = "mic";
                        break;
                    }
                    str2 = "info";
                    break;
                case 184405451:
                    if (page.equals("native:network")) {
                        str2 = "wifi";
                        break;
                    }
                    str2 = "info";
                    break;
                case 239912240:
                    if (page.equals("hubpower")) {
                        str2 = "power";
                        break;
                    }
                    str2 = "info";
                    break;
                case 273892121:
                    if (page.equals("native:device")) {
                        str2 = "info";
                        break;
                    }
                    str2 = "info";
                    break;
                case 592569703:
                    if (page.equals("tablet:storage")) {
                        str2 = "data";
                        break;
                    }
                    str2 = "info";
                    break;
                case 1154624299:
                    break;
                case 1154899769:
                    if (page.equals("tablet:view")) {
                        str2 = "camera";
                        break;
                    }
                    str2 = "info";
                    break;
                case 1166998092:
                    if (page.equals("activity:files")) {
                        str2 = "folder";
                        break;
                    }
                    str2 = "info";
                    break;
                case 1177077078:
                    if (page.equals("tablet:backup")) {
                        str2 = "backup";
                        break;
                    }
                    str2 = "info";
                    break;
                case 1202172345:
                    if (page.equals("doorbelt")) {
                        str2 = "car";
                        break;
                    }
                    str2 = "info";
                    break;
                case 1422561657:
                    if (!page.equals("tablet:about")) {
                    }
                    str2 = "info";
                    break;
                case 1427464618:
                    if (page.equals("native:software")) {
                        str2 = "update";
                        break;
                    }
                    str2 = "info";
                    break;
                case 1429999176:
                    break;
                case 1440277429:
                    if (page.equals("tablet:theme")) {
                        str2 = "palette";
                        break;
                    }
                    str2 = "info";
                    break;
                case 1442336542:
                    if (page.equals("tablet:voice")) {
                        str2 = "speaker";
                        break;
                    }
                    str2 = "info";
                    break;
                case 1488266044:
                    if (page.equals("native:toggles")) {
                        str2 = "toggle";
                        break;
                    }
                    str2 = "info";
                    break;
                case 1694003232:
                    if (page.equals("activity:lanepics")) {
                        str2 = "camera";
                        break;
                    }
                    str2 = "info";
                    break;
                case 2037187069:
                    if (page.equals("bookmarks")) {
                        str2 = "bookmark";
                        break;
                    }
                    str2 = "info";
                    break;
                default:
                    str2 = "info";
                    break;
            }
            return new SettingIcons.Icon(str2, ((!StringsKt.startsWith(page, "tablet:", false) || Intrinsics.areEqual(page, "tablet:techstream")) && !StringsKt.startsWith(page, "activity:", false)) ? "device" : "tablet", false, 4, null);
        }

        public final String toolIcon(String path) {
            Intrinsics.checkNotNullParameter(path, "path");
            if (Intrinsics.areEqual(path, "/")) {
                return "info";
            }
            if (StringsKt.contains((CharSequence) path, (CharSequence) "recording", false)) {
                return "film";
            }
            if (StringsKt.contains((CharSequence) path, (CharSequence) "bluetooth", false)) {
                return "bluetooth";
            }
            if (StringsKt.contains((CharSequence) path, (CharSequence) "laboratory", false)) {
                return "flask";
            }
            if (StringsKt.contains((CharSequence) path, (CharSequence) "model", false)) {
                return "chip";
            }
            if (StringsKt.contains((CharSequence) path, (CharSequence) "navigation", false)) {
                return "map";
            }
            if (StringsKt.contains((CharSequence) path, (CharSequence) "plots", false) || StringsKt.contains((CharSequence) path, (CharSequence) "tuning", false)) {
                return "plots";
            }
            if (StringsKt.contains((CharSequence) path, (CharSequence) "logs", false)) {
                return "console";
            }
            if (StringsKt.contains((CharSequence) path, (CharSequence) "galaxy", false)) {
                return "star";
            }
            if (StringsKt.contains((CharSequence) path, (CharSequence) "android", false)) {
                return "phone";
            }
            String str = "camera";
            if (!StringsKt.contains((CharSequence) path, (CharSequence) "camera", false)) {
                if (StringsKt.contains((CharSequence) path, (CharSequence) "device-pref", false)) {
                    return "shuffle";
                }
                str = "wrench";
                if (!StringsKt.contains((CharSequence) path, (CharSequence) "system", false)) {
                    return StringsKt.contains((CharSequence) path, (CharSequence) "theme", false) ? "palette" : StringsKt.contains((CharSequence) path, (CharSequence) "vehicle", false) ? "car" : "wrench";
                }
            }
            return str;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void pickAny$lambda$3(SettingsActivity this$0, Uri uri) {
        Function1 f = this$0.onFilePicked;
        this$0.onFilePicked = null;
        if (f != null) {
            f.invoke(uri);
        }
    }

    @Override // com.starpilot.starview.GxHost
    /* renamed from: getGxHost, reason: from getter */
    public String getHost() {
        return this.host;
    }

    @Override // com.starpilot.starview.GxHost
    /* renamed from: getGxApi, reason: from getter */
    public GalaxyApi getApi() {
        return this.api;
    }

    @Override // com.starpilot.starview.GxHost
    public void pickFile(String[] mimes, Function1<? super Uri, Unit> then) {
        Object m1191constructorimpl;
        Intrinsics.checkNotNullParameter(mimes, "mimes");
        Intrinsics.checkNotNullParameter(then, "then");
        this.onFilePicked = then;
        try {
            Result.Companion companion = Result.Companion;
            SettingsActivity $this$pickFile_u24lambda_u244 = this;
            $this$pickFile_u24lambda_u244.pickAny.launch(mimes);
            m1191constructorimpl = KtCompat.resultOf(Unit.INSTANCE);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            m1191constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
        }
        if (KtCompat.exceptionOrNull(m1191constructorimpl) != null) {
            this.onFilePicked = null;
            then.invoke(null);
        }
    }

    @Override // com.starpilot.starview.GxHost
    public void openPage(String page) {
        List $this$indexOfFirst$iv;
        boolean z;
        List $this$indexOfFirst$iv2;
        Intrinsics.checkNotNullParameter(page, "page");
        List $this$indexOfFirst$iv3 = areas();
        int index$iv = 0;
        Iterator<Area> it = $this$indexOfFirst$iv3.iterator();
        while (true) {
            if (!it.hasNext()) {
                index$iv = -1;
                break;
            }
            Object item$iv = it.next();
            Area a = (Area) item$iv;
            Iterable $this$any$iv = a.getRails();
            if (!($this$any$iv instanceof Collection) || !((Collection) $this$any$iv).isEmpty()) {
                Iterator it2 = $this$any$iv.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        $this$indexOfFirst$iv = $this$indexOfFirst$iv3;
                        z = false;
                        break;
                    }
                    Object element$iv = it2.next();
                    Iterable rs = (List) ((Pair) element$iv).component2();
                    Iterable $this$any$iv2 = rs;
                    if (!($this$any$iv2 instanceof Collection) || !((Collection) $this$any$iv2).isEmpty()) {
                        Iterator it3 = $this$any$iv2.iterator();
                        while (true) {
                            if (!it3.hasNext()) {
                                $this$indexOfFirst$iv = $this$indexOfFirst$iv3;
                                $this$indexOfFirst$iv2 = null;
                                break;
                            }
                            Object element$iv2 = it3.next();
                            Rail it4 = (Rail) element$iv2;
                            $this$indexOfFirst$iv = $this$indexOfFirst$iv3;
                            if (Intrinsics.areEqual(it4.getPage(), page)) {
                                $this$indexOfFirst$iv2 = 1;
                                break;
                            }
                            $this$indexOfFirst$iv3 = $this$indexOfFirst$iv;
                        }
                    } else {
                        $this$indexOfFirst$iv = $this$indexOfFirst$iv3;
                        $this$indexOfFirst$iv2 = null;
                    }
                    if ($this$indexOfFirst$iv2 != null) {
                        z = true;
                        break;
                    }
                    $this$indexOfFirst$iv3 = $this$indexOfFirst$iv;
                }
            } else {
                $this$indexOfFirst$iv = $this$indexOfFirst$iv3;
                z = false;
            }
            if (z) {
                break;
            }
            index$iv++;
            $this$indexOfFirst$iv3 = $this$indexOfFirst$iv;
        }
        int idx = index$iv;
        if (idx >= 0) {
            showArea(idx, page);
        } else {
            open(page);
        }
    }

    private final void openRoute(String route) {
        String str;
        if (StringsKt.contains((CharSequence) route, (CharSequence) "favorites", false)) {
            str = "gx:favorites";
        } else if (StringsKt.contains((CharSequence) route, (CharSequence) "longitudinal", false) || StringsKt.contains((CharSequence) route, (CharSequence) "personalit", false)) {
            str = "gx:personalities";
        } else if (StringsKt.contains((CharSequence) route, (CharSequence) "model", false)) {
            str = "gx:models";
        } else if (StringsKt.contains((CharSequence) route, (CharSequence) "navigation", false) || StringsKt.contains((CharSequence) route, (CharSequence) "maps", false)) {
            str = "gx:navigation";
        } else {
            str = "gx:home";
        }
        openPage(str);
    }

    private final void setHeaderTool(View v) {
        FrameLayout slot = this.headerSlot;
        if (slot == null) {
            return;
        }
        if (v == null || v.getParent() != slot) {
            slot.removeAllViews();
            if (v != null) {
                ViewParent parent = v.getParent();
                ViewGroup viewGroup = parent instanceof ViewGroup ? (ViewGroup) parent : null;
                if (viewGroup != null) {
                    viewGroup.removeView(v);
                }
                slot.addView(v, new FrameLayout.LayoutParams(-1, -2));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean pageNeedsOffroad(View v) {
        if (v instanceof CatalogPanel) {
            return ((CatalogPanel) v).getNeedsOffroad();
        }
        if (!(v instanceof ComboPage)) {
            return v instanceof DevicePanel ? Intrinsics.areEqual(((DevicePanel) v).getKind(), "developer") || Intrinsics.areEqual(((DevicePanel) v).getKind(), "software") || Intrinsics.areEqual(((DevicePanel) v).getKind(), "device") : v instanceof GxPage ? ((GxPage) v).getNeedsOffroad() : v instanceof GalaxySettingsView ? ((GalaxySettingsView) v).getNeedsParked() : v instanceof GalaxyWeb;
        }
        Iterable $this$any$iv = ((ComboPage) v).getParts();
        if (($this$any$iv instanceof Collection) && ((Collection) $this$any$iv).isEmpty()) {
            return false;
        }
        for (Object element$iv : $this$any$iv) {
            View it = (View) element$iv;
            if (pageNeedsOffroad(it)) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: SettingsActivity.kt */
    static final class Rail {
        private final int icon;
        private final String label;
        private final String page;

        public Rail(String label, int icon, String page) {
            Intrinsics.checkNotNullParameter(label, "label");
            Intrinsics.checkNotNullParameter(page, "page");
            this.label = label;
            this.icon = icon;
            this.page = page;
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

    /* compiled from: SettingsActivity.kt */
    private static final class Area {
        private final int icon;
        private final List<Pair<String, List<Rail>>> rails;
        private final String sub;
        private final String title;

        /* JADX WARN: Multi-variable type inference failed */
        public Area(String title, String sub, int icon, List<? extends Pair<String, ? extends List<Rail>>> rails) {
            Intrinsics.checkNotNullParameter(title, "title");
            Intrinsics.checkNotNullParameter(sub, "sub");
            Intrinsics.checkNotNullParameter(rails, "rails");
            this.title = title;
            this.sub = sub;
            this.icon = icon;
            this.rails = rails;
        }

        public final int getIcon() {
            return this.icon;
        }

        public final List<Pair<String, List<Rail>>> getRails() {
            return this.rails;
        }

        public final String getSub() {
            return this.sub;
        }

        public final String getTitle() {
            return this.title;
        }
    }

    private final List<Area> areas() {
        int $i$f$map;
        int $i$f$map2;
        Area[] areaArr = new Area[5];
        areaArr[0] = new Area("Device", "Settings", R.drawable.ic_car, CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to("Driving", CollectionsKt.listOf((Object[]) new Rail[]{new Rail("Driving controls", R.drawable.ic_speed, "drivingmap"), new Rail("Steering", R.drawable.ic_steering, "galaxy:steering"), new Rail("Speed & following", R.drawable.ic_speed, "galaxy:speed"), new Rail("Personalities", R.drawable.ic_speed, "galaxy:personalities"), new Rail("Speed limits", R.drawable.ic_limit, "galaxy:slc"), new Rail("Vehicle", R.drawable.ic_car, "galaxy:vehicle"), new Rail("TechStream", R.drawable.ic_wheel_controls, "tablet:techstream"), new Rail("Doors & belts", R.drawable.ic_car, "doorbelt"), new Rail("Key fob", R.drawable.ic_car, "keyfob"), new Rail("Wheel controls", R.drawable.ic_wheel_controls, "galaxy:wheel"), new Rail("Sounds & alerts", R.drawable.ic_sound, "galaxy:sounds"), new Rail("Cameras & monitoring", R.drawable.ic_camera, "galaxy:cameras")})), TuplesKt.to("System", CollectionsKt.listOf((Object[]) new Rail[]{new Rail("Comma health", R.drawable.ic_info, "health"), new Rail("Power management", R.drawable.ic_link, "hubpower"), new Rail("Connections", R.drawable.ic_network, "connections"), new Rail("Toggles", R.drawable.ic_gear, "native:toggles"), new Rail("Device", R.drawable.ic_info, "native:device"), new Rail("Network", R.drawable.ic_network, "native:network"), new Rail("Software", R.drawable.ic_data, "native:software"), new Rail("Data uploads", R.drawable.ic_data, "galaxy:data"), new Rail("Developer", R.drawable.ic_dev, "galaxy:developer"), new Rail("Developer (comma)", R.drawable.ic_terminal, "native:developer"), new Rail("More from Galaxy", R.drawable.ic_star, "galaxy:more")}))}));
        areaArr[1] = new Area("Device", "Display", R.drawable.ic_display, CollectionsKt.listOf(TuplesKt.to(null, CollectionsKt.listOf((Object[]) new Rail[]{new Rail("Screen", R.drawable.ic_sun, "galaxy:display"), new Rail("Driving screen", R.drawable.ic_eye, "galaxy:appearance"), new Rail("Quick Select", R.drawable.ic_star, "quickselect"), new Rail("Blind spot camera", R.drawable.ic_camera, "galaxy:pip")}))));
        areaArr[2] = new Area("Tablet", "Settings", R.drawable.ic_tablet, CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to(null, CollectionsKt.listOf((Object[]) new Rail[]{new Rail("Connection", R.drawable.ic_link, "tablet:connection"), new Rail("Navigation map", R.drawable.ic_pin, "tablet:nav"), new Rail("Camera & view", R.drawable.ic_camera, "tablet:view"), new Rail("Street maps", R.drawable.ic_pin, "tablet:maps"), new Rail("GPS & sensors", R.drawable.ic_pin, "activity:sensors"), new Rail("Lane pictures", R.drawable.ic_camera, "activity:lanepics"), new Rail("Lane road data", R.drawable.ic_pin, "roaddata"), new Rail("Bookmarks", R.drawable.ic_pin, "bookmarks"), new Rail("RTK GPS", R.drawable.ic_pin, "tablet:rtk"), new Rail("Braking log", R.drawable.ic_speed, "tablet:braking"), new Rail("Blinker & lane changes", R.drawable.ic_steering, "tablet:blinker"), new Rail("New ideas", R.drawable.ic_star, "tablet:ideas"), new Rail("Voice alerts", R.drawable.ic_info, "tablet:voice"), new Rail("Voice studio", R.drawable.ic_info, "tablet:voicestudio"), new Rail("Terminal", R.drawable.ic_terminal, "native:terminal"), new Rail("Files", R.drawable.ic_files, "activity:files"), new Rail("Storage", R.drawable.ic_data, "tablet:storage"), new Rail("Backup & restore", R.drawable.ic_data, "tablet:backup"), new Rail("About", R.drawable.ic_info, "tablet:about")})), TuplesKt.to("Drives", CollectionsKt.listOf((Object[]) new Rail[]{new Rail("Last drive", R.drawable.ic_info, "journal:last"), new Rail("Your driving", R.drawable.ic_info, "journal:trends"), new Rail("Alert history", R.drawable.ic_info, "journal:alerts"), new Rail("Takeover spots", R.drawable.ic_info, "journal:spots"), new Rail("Driver profiles", R.drawable.ic_info, "profiles")}))}));
        int i = R.drawable.ic_view;
        List listOf = CollectionsKt.listOf(new Rail("Theme", R.drawable.ic_sun, "tablet:theme"));
        Iterable $this$map$iv = UiSettings.Companion.getITEMS();
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            destination$iv$iv.add(((UiSettings.Item) item$iv$iv).getGroup());
        }
        Iterable $this$filter$iv = CollectionsKt.distinct((List) destination$iv$iv);
        Collection destination$iv$iv2 = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            String it = (String) element$iv$iv;
            if ((Intrinsics.areEqual(it, "Widgets") || Intrinsics.areEqual(it, "Internal") || Intrinsics.areEqual(it, "Camera") || Intrinsics.areEqual(it, "Blinker")) ? false : true) {
                destination$iv$iv2.add(element$iv$iv);
            }
        }
        Iterable $this$map$iv2 = (List) destination$iv$iv2;
        int $i$f$map3 = 0;
        Collection destination$iv$iv3 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv2, 10));
        Iterable $this$mapTo$iv$iv = $this$map$iv2;
        int $i$f$mapTo = 0;
        for (Object item$iv$iv2 : $this$mapTo$iv$iv) {
            String g = (String) item$iv$iv2;
            Iterable $this$map$iv3 = $this$map$iv2;
            switch (g.hashCode()) {
                case -169275166:
                    $i$f$map = $i$f$map3;
                    if (g.equals("Effects")) {
                        $i$f$map2 = R.drawable.ic_fx;
                        break;
                    }
                    $i$f$map2 = R.drawable.ic_eye;
                    break;
                case 71895:
                    $i$f$map = $i$f$map3;
                    if (g.equals("HUD")) {
                        $i$f$map2 = R.drawable.ic_hud;
                        break;
                    }
                    $i$f$map2 = R.drawable.ic_eye;
                    break;
                case 2552640:
                    $i$f$map = $i$f$map3;
                    if (g.equals("Road")) {
                        $i$f$map2 = R.drawable.ic_road;
                        break;
                    }
                    $i$f$map2 = R.drawable.ic_eye;
                    break;
                case 2011082565:
                    if (g.equals("Camera")) {
                        $i$f$map = $i$f$map3;
                        $i$f$map2 = R.drawable.ic_camera;
                        break;
                    } else {
                        $i$f$map = $i$f$map3;
                        $i$f$map2 = R.drawable.ic_eye;
                        break;
                    }
                default:
                    $i$f$map = $i$f$map3;
                    $i$f$map2 = R.drawable.ic_eye;
                    break;
            }
            destination$iv$iv3.add(new Rail(g, $i$f$map2, "uigroup:" + g));
            $this$map$iv2 = $this$map$iv3;
            $i$f$map3 = $i$f$map;
            $this$mapTo$iv$iv = $this$mapTo$iv$iv;
            $i$f$mapTo = $i$f$mapTo;
        }
        areaArr[3] = new Area("Tablet", "Display", i, CollectionsKt.listOf(TuplesKt.to(null, CollectionsKt.plus((Collection) listOf, destination$iv$iv3))));
        areaArr[4] = new Area("Device", "Tools", R.drawable.ic_tools, CollectionsKt.listOf(TuplesKt.to(null, galaxyToolRails())));
        return CollectionsKt.listOf((Object[]) areaArr);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterative(DepthRegionTraversal.java:31)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visit(SwitchOverStringVisitor.java:60)
     */
    private final GalaxySettingsView.Root galaxyRoot(String id) {
        switch (id.hashCode()) {
            case 3357525:
                if (id.equals("more")) {
                    return new GalaxySettingsView.Root("More from Galaxy", null, CollectionsKt.listOf(new GalaxySettingsView.Entry.Unplaced(PLACED_PAGES)), "Settings pages Galaxy has that aren't placed in a rail yet - new ones from StarPilot updates show up here.", 2, null);
                }
                break;
            case 109641799:
                if (id.equals("speed")) {
                    return new GalaxySettingsView.Root("Speed & following", null, CollectionsKt.listOf(new GalaxySettingsView.Entry.Hub(CollectionsKt.listOf((Object[]) new String[]{"profiles", "conditional", "curve", "slc"}))), null, 10, null);
                }
                break;
            case 342069036:
                if (id.equals("vehicle")) {
                    return new GalaxySettingsView.Root("Vehicle", "vehicle", null, "Choosing the car (or Auto) and the Toyota tools are under Car selection & tools below.", 4, null);
                }
                break;
            case 549364206:
                if (id.equals("cameras")) {
                    return new GalaxySettingsView.Root("Cameras & monitoring", null, CollectionsKt.listOf((Object[]) new GalaxySettingsView.Entry[]{new GalaxySettingsView.Entry.Page("Sentry motion monitoring", "Watch for motion while parked: sensitivity and warning time", "sentry"), new GalaxySettingsView.Entry.Web("Sentry events & notifications", "What it caught, and where it sends alerts", "/cameras/events"), new GalaxySettingsView.Entry.Page("V-ASM spot monitoring", "Draw the side windows in the cabin camera, and visual blind-spot warning options", "vasm"), new GalaxySettingsView.Entry.Web("Live cameras", "Cabin and side camera previews", "/cameras")}), null, 10, null);
                }
                break;
            case 590460812:
                if (id.equals("personalities")) {
                    return new GalaxySettingsView.Root("Personalities", null, CollectionsKt.listOf(new GalaxySettingsView.Entry.Hub(CollectionsKt.listOf((Object[]) new String[]{"traffic", "aggressive", "standard", "relaxed"}))), null, 10, null);
                }
                break;
            case 1419257969:
                if (id.equals("steering")) {
                    return new GalaxySettingsView.Root("Steering", null, CollectionsKt.listOf(new GalaxySettingsView.Entry.Hub(CollectionsKt.listOf((Object[]) new String[]{"lane", "lane_change", "torque", "aol"}))), null, 10, null);
                }
                break;
        }
        String str = GALAXY_TITLES.get(id);
        return new GalaxySettingsView.Root((str == null && (str = GALAXY_SUB_TITLES.get(id)) == null) ? id : str, id, null, null, 12, null);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterative(DepthRegionTraversal.java:31)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visit(SwitchOverStringVisitor.java:60)
     */
    private final GalaxySettingsView.Root galaxyExtras(String id) {
        GalaxySettingsView.Root r = galaxyRoot(id);
        switch (id.hashCode()) {
            case 110999:
                if (id.equals("pip")) {
                    return new GalaxySettingsView.Root(r.getTitle(), r.getPage(), CollectionsKt.listOf(new GalaxySettingsView.Entry.Web("Camera crop", "Pick the part of the side camera picture to show", "/cameras/pip")), null, 8, null);
                }
                break;
            case 342069036:
                if (id.equals("vehicle")) {
                    return new GalaxySettingsView.Root(r.getTitle(), r.getPage(), CollectionsKt.listOf(new GalaxySettingsView.Entry.Web("Car selection & tools", "Pick the car (or Auto) for the next start, Toyota door and key tools", "/vehicle")), r.getNote());
                }
                break;
            case 1796717668:
                if (id.equals("appearance")) {
                    return new GalaxySettingsView.Root(r.getTitle(), r.getPage(), CollectionsKt.listOf(new GalaxySettingsView.Entry.Web("Widgets, colors & layout", "Where each widget sits on the comma's screen, colors, path style, clock", "/theme_maker")), null, 8, null);
                }
                break;
        }
        return r;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x005c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final View galaxyPage(String id) {
        CarUi carUi;
        Function1 function1;
        Function1 function12;
        String h = this.host;
        if (h == null) {
            return offline("Not connected to the comma. Connect first, then come back.");
        }
        SettingsActivity settingsActivity = this;
        CarUi carUi2 = this.ui;
        if (carUi2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi = null;
        } else {
            carUi = carUi2;
        }
        GalaxySettingsView.Root galaxyExtras = galaxyExtras(id);
        Function2 function2 = new Function2() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda109
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit galaxyPage$lambda$14;
                galaxyPage$lambda$14 = SettingsActivity.galaxyPage$lambda$14(SettingsActivity.this, (String) obj, (String) obj2);
                return galaxyPage$lambda$14;
            }
        };
        if (Intrinsics.areEqual(id, "sounds")) {
            function12 = new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda112
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit galaxyPage$lambda$15;
                    galaxyPage$lambda$15 = SettingsActivity.galaxyPage$lambda$15(SettingsActivity.this, (LinearLayout) obj);
                    return galaxyPage$lambda$15;
                }
            };
        } else {
            if (!Intrinsics.areEqual(id, "developer")) {
                function1 = null;
                GalaxySettingsView galaxySettingsView = new GalaxySettingsView(settingsActivity, carUi, galaxyExtras, function2, function1, null, 32, null);
                if (Link.INSTANCE.getHost() == null) {
                    Link.INSTANCE.setHost(h);
                }
                return galaxySettingsView;
            }
            function12 = new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda113
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit galaxyPage$lambda$16;
                    galaxyPage$lambda$16 = SettingsActivity.galaxyPage$lambda$16(SettingsActivity.this, (LinearLayout) obj);
                    return galaxyPage$lambda$16;
                }
            };
        }
        function1 = function12;
        GalaxySettingsView galaxySettingsView2 = new GalaxySettingsView(settingsActivity, carUi, galaxyExtras, function2, function1, null, 32, null);
        if (Link.INSTANCE.getHost() == null) {
        }
        return galaxySettingsView2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit galaxyPage$lambda$14(SettingsActivity this$0, String path, String title) {
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(title, "title");
        this$0.openGalaxyWeb(path, title);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit galaxyPage$lambda$15(SettingsActivity this$0, LinearLayout col) {
        Intrinsics.checkNotNullParameter(col, "col");
        GalaxyExtras galaxyExtras = GalaxyExtras.INSTANCE;
        SettingsActivity settingsActivity = this$0;
        CarUi carUi = this$0.ui;
        if (carUi == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi = null;
        }
        galaxyExtras.soundPacks(settingsActivity, carUi, col);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit galaxyPage$lambda$16(SettingsActivity this$0, LinearLayout col) {
        Intrinsics.checkNotNullParameter(col, "col");
        GalaxyExtras galaxyExtras = GalaxyExtras.INSTANCE;
        SettingsActivity settingsActivity = this$0;
        CarUi carUi = this$0.ui;
        if (carUi == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi = null;
        }
        galaxyExtras.cloudProvider(settingsActivity, carUi, col);
        return Unit.INSTANCE;
    }

    public final void openGalaxyWeb(String path, String title) {
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(title, "title");
        String from = this.currentPage;
        open("gweb:" + path + "|" + title);
        this.webReturn = from;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean returnFromWeb() {
        String r = this.webReturn;
        if (r == null) {
            return false;
        }
        this.webReturn = null;
        open(r);
        return true;
    }

    private final List<Rail> galaxyToolRails() {
        Iterable $this$filter$iv = GalaxyTools.INSTANCE.list(this);
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            GalaxyTools.Tool it = (GalaxyTools.Tool) element$iv$iv;
            if (!GalaxyTools.INSTANCE.getPLACED_ELSEWHERE().contains(it.getPath())) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        List tools = (List) destination$iv$iv;
        List listOf = CollectionsKt.listOf(new Rail("All tools", R.drawable.ic_tools, "toolshome"));
        List $this$map$iv = tools;
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            GalaxyTools.Tool t = (GalaxyTools.Tool) item$iv$iv;
            destination$iv$iv2.add(new Rail(t.getName(), GalaxyTools.INSTANCE.icon(t.getPath()), "gweb:" + t.getPath() + "|" + t.getName()));
            tools = tools;
            $this$map$iv = $this$map$iv;
        }
        return CollectionsKt.plus((Collection) listOf, destination$iv$iv2);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper
    protected void attachBaseContext(Context base) {
        Intrinsics.checkNotNullParameter(base, "base");
        super.attachBaseContext(NarrowDensity.INSTANCE.wrap(base, 1000, 0.62f));
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        Hpms hpms = Hpms.INSTANCE;
        Context applicationContext = getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        hpms.init(applicationContext);
        super.onCreate(savedInstanceState);
        DriveJournal.INSTANCE.init(this);
        CarUi u = new CarUi(this, false, 2, null);
        u.setAutoIcon(new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda16
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                SettingIcons.Icon onCreate$lambda$21$lambda$20;
                onCreate$lambda$21$lambda$20 = SettingsActivity.onCreate$lambda$21$lambda$20(SettingsActivity.this, (String) obj);
                return onCreate$lambda$21$lambda$20;
            }
        });
        this.ui = u;
        String stringExtra = getIntent().getStringExtra("host");
        if (stringExtra == null) {
            stringExtra = Link.INSTANCE.getHost();
        }
        this.host = stringExtra;
        FrameLayout $this$onCreate_u24lambda_u2422 = new FrameLayout(this);
        $this$onCreate_u24lambda_u2422.setBackgroundColor(CarUi.Companion.getBG());
        this.root = $this$onCreate_u24lambda_u2422;
        FrameLayout frameLayout = this.root;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout = null;
        }
        setContentView(frameLayout);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        FrameLayout frameLayout2 = this.root;
        if (frameLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout2 = null;
        }
        ViewCompat.setOnApplyWindowInsetsListener(frameLayout2, new OnApplyWindowInsetsListener() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda17
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                WindowInsetsCompat onCreate$lambda$23;
                onCreate$lambda$23 = SettingsActivity.onCreate$lambda$23(view, windowInsetsCompat);
                return onCreate$lambda$23;
            }
        });
        String h = this.host;
        if (h != null) {
            this.api = GalaxyApi.Companion.get(this, h);
            if (Link.INSTANCE.getHost() == null) {
                Link.INSTANCE.setHost(h);
            }
        }
        final String h2 = this.host;
        if (h2 != null) {
            this.handler.postDelayed(new Runnable() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda18
                @Override // java.lang.Runnable
                public final void run() {
                    SettingsActivity.onCreate$lambda$26$lambda$25(SettingsActivity.this, h2);
                }
            }, 400L);
        }
        if (savedInstanceState != null) {
            int area = savedInstanceState.getInt("area", -1);
            String page = savedInstanceState.getString("page");
            if (area >= 0) {
                showArea(area, page);
                return;
            } else if (page == null || !savedInstanceState.getBoolean("open")) {
                showHome();
                return;
            } else {
                showHome();
                openTarget(page);
                return;
            }
        }
        String stringExtra2 = getIntent().getStringExtra("tab");
        if (stringExtra2 == null) {
            showHome();
            return;
        }
        switch (stringExtra2.hashCode()) {
            case -1335157162:
                if (stringExtra2.equals("device")) {
                    showArea(0, null);
                    return;
                }
                break;
            case -1181248900:
                if (stringExtra2.equals("terminal")) {
                    showArea(2, "native:terminal");
                    return;
                }
                break;
            case -1147621473:
                if (stringExtra2.equals("toggles")) {
                    showArea(0, "native:toggles");
                    return;
                }
                break;
            case 110545371:
                if (stringExtra2.equals("tools")) {
                    showArea(4, null);
                    return;
                }
                break;
            case 1319330215:
                if (stringExtra2.equals("software")) {
                    showArea(0, "native:software");
                    return;
                }
                break;
        }
        showHome();
        String stringExtra3 = getIntent().getStringExtra("tab");
        Intrinsics.checkNotNull(stringExtra3);
        openTarget(stringExtra3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final SettingIcons.Icon onCreate$lambda$21$lambda$20(SettingsActivity this$0, String t) {
        Intrinsics.checkNotNullParameter(t, "t");
        return SettingIcons.INSTANCE.forLabel(t, this$0.buildGroup);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final WindowInsetsCompat onCreate$lambda$23(View v, WindowInsetsCompat insets) {
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(insets, "insets");
        int ime = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom;
        v.setPadding(0, 0, 0, ime);
        return insets;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$26$lambda$25(SettingsActivity this$0, String $h) {
        if (this$0.isFinishing() || this$0.isDestroyed()) {
            return;
        }
        GalaxyWebPool.INSTANCE.warm(this$0, $h);
    }

    private final void showHome() {
        CarUi carUi;
        CarUi carUi2;
        View view = this.current;
        FrameLayout frameLayout = null;
        GxPage gxPage = view instanceof GxPage ? (GxPage) view : null;
        if (gxPage != null) {
            gxPage.hide();
        }
        FrameLayout frameLayout2 = this.root;
        if (frameLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout2 = null;
        }
        frameLayout2.removeAllViews();
        this.current = null;
        this.contentHolder = null;
        this.areaIndex = -1;
        this.offroadBar = null;
        LinearLayout col = new LinearLayout(this);
        col.setOrientation(1);
        CarUi carUi3 = this.ui;
        if (carUi3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi3 = null;
        }
        int dp = carUi3.dp(30);
        CarUi carUi4 = this.ui;
        if (carUi4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi4 = null;
        }
        int dp2 = carUi4.dp(16);
        CarUi carUi5 = this.ui;
        if (carUi5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi5 = null;
        }
        int dp3 = carUi5.dp(30);
        CarUi carUi6 = this.ui;
        if (carUi6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi6 = null;
        }
        col.setPadding(dp, dp2, dp3, carUi6.dp(16));
        col.addView(headerBar("Settings", null, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda194
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit showHome$lambda$28;
                showHome$lambda$28 = SettingsActivity.showHome$lambda$28(SettingsActivity.this);
                return showHome$lambda$28;
            }
        }));
        SettingsActivity settingsActivity = this;
        CarUi carUi7 = this.ui;
        if (carUi7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi = null;
        } else {
            carUi = carUi7;
        }
        final SettingsLauncher l = new SettingsLauncher(settingsActivity, carUi, homeAreas(), new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda195
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                List tabletEntries;
                tabletEntries = SettingsActivity.this.tabletEntries();
                return tabletEntries;
            }
        }, new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda196
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit showHome$lambda$30;
                showHome$lambda$30 = SettingsActivity.showHome$lambda$30(SettingsActivity.this, (String) obj);
                return showHome$lambda$30;
            }
        });
        this.launcher = l;
        SettingsActivity settingsActivity2 = this;
        CarUi carUi8 = this.ui;
        if (carUi8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi2 = null;
        } else {
            carUi2 = carUi8;
        }
        col.addView(new SettingsHomeView(settingsActivity2, carUi2, l, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda197
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                List tabletEntries;
                tabletEntries = SettingsActivity.this.tabletEntries();
                return tabletEntries;
            }
        }, new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda198
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit showHome$lambda$32;
                showHome$lambda$32 = SettingsActivity.showHome$lambda$32(l, (SettingsIndex.Entry) obj);
                return showHome$lambda$32;
            }
        }, new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda199
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit showHome$lambda$33;
                showHome$lambda$33 = SettingsActivity.showHome$lambda$33(SettingsActivity.this, (String) obj);
                return showHome$lambda$33;
            }
        }), new LinearLayout.LayoutParams(-1, 0, 1.0f));
        FrameLayout frameLayout3 = this.root;
        if (frameLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
        } else {
            frameLayout = frameLayout3;
        }
        frameLayout.addView(col);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit showHome$lambda$28(SettingsActivity this$0) {
        this$0.finish();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit showHome$lambda$30(SettingsActivity this$0, String it) {
        Intrinsics.checkNotNullParameter(it, "it");
        this$0.openTarget(it);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit showHome$lambda$32(SettingsLauncher $l, SettingsIndex.Entry it) {
        Intrinsics.checkNotNullParameter(it, "it");
        SettingsLauncher.addShortcut$default($l, it, 0, 2, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit showHome$lambda$33(SettingsActivity this$0, String it) {
        Intrinsics.checkNotNullParameter(it, "it");
        this$0.openTarget(it);
        return Unit.INSTANCE;
    }

    private final View homeAreas() {
        final SettingsActivity settingsActivity = this;
        GridLayout grid = new GridLayout(settingsActivity);
        grid.setColumnCount(3);
        grid.setRowCount(2);
        int i = 0;
        grid.setUseDefaultMargins(false);
        List all = areas();
        Quad[] quadArr = new Quad[5];
        quadArr[0] = new Quad(0, 0, 0, "Driving behaviour, comma system");
        quadArr[1] = new Quad(1, 0, 1, "What the comma's screen shows");
        quadArr[2] = new Quad(4, 0, 2, "Galaxy's tools: models, recordings, logs…");
        DataClient data = Link.INSTANCE.getData();
        quadArr[3] = new Quad(2, 1, 0, data != null && data.getConnected() ? "Linked to the comma" : "Connection, camera, tools");
        quadArr[4] = new Quad(3, 1, 1, "What this tablet draws");
        Iterator it = CollectionsKt.listOf((Object[]) quadArr).iterator();
        while (it.hasNext()) {
            Quad quad = (Quad) it.next();
            final int i2 = quad.getI();
            int r = quad.getR();
            int c = quad.getC();
            String sub = quad.getSub();
            Area a = all.get(i2);
            CarUi carUi = settingsActivity.ui;
            if (carUi == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi = null;
            }
            LinearLayout t = carUi.tile(a.getTitle() + " · " + a.getSub(), sub, a.getIcon(), 64, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda11
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit homeAreas$lambda$35;
                    homeAreas$lambda$35 = SettingsActivity.homeAreas$lambda$35(SettingsActivity.this, i2);
                    return homeAreas$lambda$35;
                }
            });
            LinearLayout linearLayout = t;
            GridLayout.LayoutParams $this$homeAreas_u24lambda_u2436 = new GridLayout.LayoutParams(GridLayout.spec(r, 1.0f), GridLayout.spec(c, 1.0f));
            $this$homeAreas_u24lambda_u2436.width = i;
            $this$homeAreas_u24lambda_u2436.height = i;
            CarUi carUi2 = settingsActivity.ui;
            if (carUi2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi2 = null;
            }
            int dp = carUi2.dp(10);
            CarUi carUi3 = settingsActivity.ui;
            if (carUi3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi3 = null;
            }
            List all2 = all;
            int dp2 = carUi3.dp(10);
            CarUi carUi4 = settingsActivity.ui;
            if (carUi4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi4 = null;
            }
            Iterator it2 = it;
            int dp3 = carUi4.dp(10);
            CarUi carUi5 = settingsActivity.ui;
            if (carUi5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi5 = null;
            }
            $this$homeAreas_u24lambda_u2436.setMargins(dp, dp2, dp3, carUi5.dp(10));
            Unit unit = Unit.INSTANCE;
            grid.addView(linearLayout, $this$homeAreas_u24lambda_u2436);
            i = 0;
            settingsActivity = this;
            all = all2;
            it = it2;
        }
        return grid;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit homeAreas$lambda$35(SettingsActivity this$0, int $i) {
        this$0.showArea($i, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<SettingsIndex.Entry> tabletEntries() {
        ArrayList out = new ArrayList();
        Iterator it = areas().iterator();
        int i = 0;
        while (it.hasNext()) {
            int i2 = i;
            i++;
            Area a = (Area) it.next();
            Iterator<Pair<String, List<Rail>>> it2 = a.getRails().iterator();
            while (it2.hasNext()) {
                List rs = it2.next().component2();
                for (Rail r : rs) {
                    String label = r.getLabel();
                    String str = a.getTitle() + " · " + a.getSub();
                    String it3 = r.getPage();
                    Iterator it4 = it;
                    int i3 = i;
                    if (StringsKt.startsWith(it3, "gweb:", false)) {
                        it3 = null;
                    }
                    out.add(new SettingsIndex.Entry(label, "", str, it3 == null ? "tool:" + i2 + "|" + r.getPage() : it3, null, null, r.getSv(), 48, null));
                    it = it4;
                    i = i3;
                }
            }
        }
        for (UiSettings.Item it5 : UiSettings.Companion.getITEMS()) {
            if (!Intrinsics.areEqual(it5.getGroup(), "Internal")) {
                out.add(new SettingsIndex.Entry(it5.getTitle(), "", "Tablet · Display · " + it5.getGroup(), "uigroup:" + it5.getGroup(), null, null, new SettingIcons.Icon(Companion.railIcon("uigroup:" + it5.getGroup()).getName(), "tablet", false, 4, null), 48, null));
            }
        }
        return out;
    }

    private final void openTarget(String t) {
        if (StringsKt.startsWith(t, "area:", false)) {
            showArea(Integer.parseInt(StringsKt.removePrefix(t, (CharSequence) "area:")), null);
            return;
        }
        if (StringsKt.startsWith(t, "gsub:", false)) {
            openSub(StringsKt.substringBefore(StringsKt.removePrefix(t, (CharSequence) "gsub:"), "|", (StringsKt.removePrefix(t, (CharSequence) "gsub:"))), StringsKt.substringAfter(t, "|", t));
        } else if (StringsKt.startsWith(t, "tool:", false)) {
            showArea(Integer.parseInt(StringsKt.substringBefore(StringsKt.removePrefix(t, (CharSequence) "tool:"), "|", (StringsKt.removePrefix(t, (CharSequence) "tool:")))), StringsKt.substringAfter(t, "|", t));
        } else {
            openPage(t);
        }
    }

    private final void openToolTarget(String t) {
        List $this$indexOfFirst$iv;
        boolean z;
        boolean z2;
        boolean z3;
        if (!Intrinsics.areEqual(t, "activity:files")) {
            boolean z4 = false;
            if (StringsKt.startsWith(t, "galaxy:", false)) {
                Iterable $this$none$iv = areas().get(0).getRails();
                if (!($this$none$iv instanceof Collection) || !((Collection) $this$none$iv).isEmpty()) {
                    Iterator it = $this$none$iv.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            z2 = true;
                            break;
                        }
                        Object element$iv = it.next();
                        Pair r = (Pair) element$iv;
                        Iterable $this$any$iv = (Iterable) r.getSecond();
                        if (!($this$any$iv instanceof Collection) || !((Collection) $this$any$iv).isEmpty()) {
                            Iterator it2 = $this$any$iv.iterator();
                            while (true) {
                                if (!it2.hasNext()) {
                                    z3 = false;
                                    break;
                                }
                                Object element$iv2 = it2.next();
                                Rail it3 = (Rail) element$iv2;
                                if (Intrinsics.areEqual(it3.getPage(), t)) {
                                    z3 = true;
                                    break;
                                }
                            }
                        } else {
                            z3 = false;
                        }
                        if (z3) {
                            z2 = false;
                            break;
                        }
                    }
                } else {
                    z2 = true;
                }
                if (z2) {
                }
            }
            List $this$indexOfFirst$iv2 = areas();
            int index$iv = 0;
            Iterator<Area> it4 = $this$indexOfFirst$iv2.iterator();
            while (true) {
                if (!it4.hasNext()) {
                    index$iv = -1;
                    break;
                }
                Object item$iv = it4.next();
                Area a = (Area) item$iv;
                Iterable $this$any$iv2 = a.getRails();
                if (!($this$any$iv2 instanceof Collection) || !((Collection) $this$any$iv2).isEmpty()) {
                    Iterator it5 = $this$any$iv2.iterator();
                    while (true) {
                        if (!it5.hasNext()) {
                            $this$indexOfFirst$iv = $this$indexOfFirst$iv2;
                            z4 = false;
                            break;
                        }
                        Object element$iv3 = it5.next();
                        Iterable rs = (List) ((Pair) element$iv3).component2();
                        Iterable $this$any$iv3 = rs;
                        if (!($this$any$iv3 instanceof Collection) || !((Collection) $this$any$iv3).isEmpty()) {
                            Iterator it6 = $this$any$iv3.iterator();
                            while (true) {
                                if (!it6.hasNext()) {
                                    $this$indexOfFirst$iv = $this$indexOfFirst$iv2;
                                    z = false;
                                    break;
                                }
                                Object element$iv4 = it6.next();
                                Rail it7 = (Rail) element$iv4;
                                $this$indexOfFirst$iv = $this$indexOfFirst$iv2;
                                if (Intrinsics.areEqual(it7.getPage(), t)) {
                                    z = true;
                                    break;
                                }
                                $this$indexOfFirst$iv2 = $this$indexOfFirst$iv;
                            }
                        } else {
                            $this$indexOfFirst$iv = $this$indexOfFirst$iv2;
                            z = false;
                        }
                        if (z) {
                            z4 = true;
                            break;
                        }
                        $this$indexOfFirst$iv2 = $this$indexOfFirst$iv;
                    }
                } else {
                    $this$indexOfFirst$iv = $this$indexOfFirst$iv2;
                }
                if (z4) {
                    break;
                }
                index$iv++;
                $this$indexOfFirst$iv2 = $this$indexOfFirst$iv;
                z4 = false;
            }
            int idx = index$iv;
            if (idx >= 0) {
                showArea(idx, t);
                return;
            } else {
                open(t);
                return;
            }
        }
        open(t);
    }

    private final void openSub(String page, String title) {
        Object obj;
        boolean contains = SetsKt.setOf((Object[]) new String[]{"display", "appearance", "pip", "favorites"}).contains(page);
        List<Pair<String, List<Rail>>> rails = areas().get(contains ? 1 : 0).getRails();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = rails.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, (Iterable) ((Pair) it.next()).getSecond());
        }
        Iterator it2 = arrayList.iterator();
        while (true) {
            if (!it2.hasNext()) {
                obj = null;
                break;
            } else {
                obj = it2.next();
                if (Intrinsics.areEqual(((Rail) obj).getPage(), "galaxy:" + page)) {
                    break;
                }
            }
        }
        Rail rail = (Rail) obj;
        String page2 = rail != null ? rail.getPage() : null;
        if (page2 != null) {
            showArea(contains ? 1 : 0, page2);
            return;
        }
        if (this.areaIndex != contains) {
            showArea(contains ? 1 : 0, galaxyRailFor(page));
        }
        GALAXY_SUB_TITLES.put(page, title);
        open("galaxy:" + page);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterative(DepthRegionTraversal.java:31)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visit(SwitchOverStringVisitor.java:60)
     */
    /* JADX WARN: Removed duplicated region for block: B:12:0x008c A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0080 A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x009c A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:5:0x0074 A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0099 A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final String galaxyRailFor(String page) {
        String substringBefore$default = StringsKt.substringBefore(page, '/', page);
        switch (substringBefore$default.hashCode()) {
            case -1067310595:
                return !substringBefore$default.equals("traffic") ? "drivingmap" : "galaxy:personalities";
            case -1002263574:
                if (substringBefore$default.equals("profiles")) {
                    return "galaxy:speed";
                }
                break;
            case -905947169:
                if (substringBefore$default.equals("sentry")) {
                    return "galaxy:cameras";
                }
                break;
            case -867966454:
                if (substringBefore$default.equals("torque")) {
                    return "galaxy:steering";
                }
                break;
            case 96766:
                if (!substringBefore$default.equals("aol")) {
                }
                break;
            case 113962:
                if (!substringBefore$default.equals("slc")) {
                }
                break;
            case 3314156:
                if (!substringBefore$default.equals("lane")) {
                }
                break;
            case 3612229:
                if (!substringBefore$default.equals("vasm")) {
                }
                break;
            case 95027439:
                if (!substringBefore$default.equals("curve")) {
                }
                break;
            case 1090497327:
                if (!substringBefore$default.equals("relaxed")) {
                }
                break;
            case 1147132932:
                if (!substringBefore$default.equals("aggressive")) {
                }
                break;
            case 1208131206:
                if (!substringBefore$default.equals("conditional")) {
                }
                break;
            case 1312628413:
                if (!substringBefore$default.equals("standard")) {
                }
                break;
            case 1641332067:
                if (!substringBefore$default.equals("lane_change")) {
                }
                break;
        }
    }

    /* compiled from: SettingsActivity.kt */
    private static final /* data */ class Quad {
        private final int c;
        private final int i;
        private final int r;
        private final String sub;

        public static /* synthetic */ Quad copy$default(Quad quad, int i, int i2, int i3, String str, int i4, Object obj) {
            if ((i4 & 1) != 0) {
                i = quad.i;
            }
            if ((i4 & 2) != 0) {
                i2 = quad.r;
            }
            if ((i4 & 4) != 0) {
                i3 = quad.c;
            }
            if ((i4 & 8) != 0) {
                str = quad.sub;
            }
            return quad.copy(i, i2, i3, str);
        }

        /* renamed from: component1, reason: from getter */
        public final int getI() {
            return this.i;
        }

        /* renamed from: component2, reason: from getter */
        public final int getR() {
            return this.r;
        }

        /* renamed from: component3, reason: from getter */
        public final int getC() {
            return this.c;
        }

        /* renamed from: component4, reason: from getter */
        public final String getSub() {
            return this.sub;
        }

        public final Quad copy(int i, int r, int c, String sub) {
            Intrinsics.checkNotNullParameter(sub, "sub");
            return new Quad(i, r, c, sub);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Quad)) {
                return false;
            }
            Quad quad = (Quad) other;
            return this.i == quad.i && this.r == quad.r && this.c == quad.c && Intrinsics.areEqual(this.sub, quad.sub);
        }

        public int hashCode() {
            return (((((Integer.hashCode(this.i) * 31) + Integer.hashCode(this.r)) * 31) + Integer.hashCode(this.c)) * 31) + this.sub.hashCode();
        }

        public String toString() {
            return "Quad(i=" + this.i + ", r=" + this.r + ", c=" + this.c + ", sub=" + this.sub + ")";
        }

        public Quad(int i, int r, int c, String sub) {
            Intrinsics.checkNotNullParameter(sub, "sub");
            this.i = i;
            this.r = r;
            this.c = c;
            this.sub = sub;
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
    }

    private final View headerBar(String title, String sub, final Function0<Unit> onBack) {
        CarUi carUi;
        boolean home = Intrinsics.areEqual(title, "Settings") && sub == null;
        LinearLayout bar = new LinearLayout(this);
        bar.setOrientation(0);
        bar.setGravity(16);
        CarUi carUi2 = this.ui;
        if (carUi2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi2 = null;
        }
        bar.setPadding(0, 0, 0, carUi2.dp(14));
        ImageView $this$headerBar_u24lambda_u2447 = new ImageView(this);
        $this$headerBar_u24lambda_u2447.setImageResource(R.drawable.ic_back);
        $this$headerBar_u24lambda_u2447.setColorFilter(CarUi.Companion.getACCENT());
        CarUi carUi3 = this.ui;
        if (carUi3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi3 = null;
        }
        int dp = carUi3.dp(8);
        CarUi carUi4 = this.ui;
        if (carUi4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi4 = null;
        }
        int dp2 = carUi4.dp(8);
        CarUi carUi5 = this.ui;
        if (carUi5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi5 = null;
        }
        int dp3 = carUi5.dp(8);
        CarUi carUi6 = this.ui;
        if (carUi6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi6 = null;
        }
        $this$headerBar_u24lambda_u2447.setPadding(dp, dp2, dp3, carUi6.dp(8));
        $this$headerBar_u24lambda_u2447.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda91
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                onBack.invoke();
            }
        });
        ImageView imageView = $this$headerBar_u24lambda_u2447;
        CarUi carUi7 = this.ui;
        if (carUi7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi7 = null;
        }
        int dp4 = carUi7.dp(56);
        CarUi carUi8 = this.ui;
        if (carUi8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi8 = null;
        }
        bar.addView(imageView, new LinearLayout.LayoutParams(dp4, carUi8.dp(56)));
        TextView $this$headerBar_u24lambda_u2449 = new TextView(this);
        $this$headerBar_u24lambda_u2449.setText(title);
        $this$headerBar_u24lambda_u2449.setTextColor(CarUi.Companion.getTEXT());
        $this$headerBar_u24lambda_u2449.setTextSize(28.0f);
        $this$headerBar_u24lambda_u2449.setTypeface(Typeface.DEFAULT_BOLD);
        CarUi carUi9 = this.ui;
        if (carUi9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi9 = null;
        }
        $this$headerBar_u24lambda_u2449.setPadding(carUi9.dp(6), 0, 0, 0);
        $this$headerBar_u24lambda_u2449.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda92
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                onBack.invoke();
            }
        });
        bar.addView($this$headerBar_u24lambda_u2449);
        if (sub != null) {
            TextView $this$headerBar_u24lambda_u2450 = new TextView(this);
            $this$headerBar_u24lambda_u2450.setText(sub);
            $this$headerBar_u24lambda_u2450.setTextColor(CarUi.Companion.getDIM());
            $this$headerBar_u24lambda_u2450.setTextSize(18.0f);
            CarUi carUi10 = this.ui;
            if (carUi10 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi10 = null;
            }
            int dp5 = carUi10.dp(16);
            CarUi carUi11 = this.ui;
            if (carUi11 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi11 = null;
            }
            $this$headerBar_u24lambda_u2450.setPadding(dp5, carUi11.dp(6), 0, 0);
            bar.addView($this$headerBar_u24lambda_u2450);
        }
        if (home) {
            bar.addView(new View(this), new LinearLayout.LayoutParams(0, 1, 1.0f));
        } else {
            View view = new View(this);
            CarUi carUi12 = this.ui;
            if (carUi12 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi12 = null;
            }
            bar.addView(view, new LinearLayout.LayoutParams(carUi12.dp(getResources().getConfiguration().screenWidthDp < 1150 ? 40 : 120), 1));
            FrameLayout slot = new FrameLayout(this);
            this.headerSlot = slot;
            FrameLayout frameLayout = slot;
            LinearLayout.LayoutParams $this$headerBar_u24lambda_u2451 = new LinearLayout.LayoutParams(0, -2, 1.0f);
            CarUi carUi13 = this.ui;
            if (carUi13 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi13 = null;
            }
            $this$headerBar_u24lambda_u2451.rightMargin = carUi13.dp(14);
            Unit unit = Unit.INSTANCE;
            bar.addView(frameLayout, $this$headerBar_u24lambda_u2451);
            DriveState.OffroadBar ob = this.offroadBar;
            if (ob != null) {
                ViewParent parent = ob.getView().getParent();
                ViewGroup viewGroup = parent instanceof ViewGroup ? (ViewGroup) parent : null;
                if (viewGroup != null) {
                    viewGroup.removeView(ob.getView());
                }
                LinearLayout view2 = ob.getView();
                CarUi carUi14 = this.ui;
                if (carUi14 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ui");
                    carUi14 = null;
                }
                LinearLayout.LayoutParams $this$headerBar_u24lambda_u2453_u24lambda_u2452 = new LinearLayout.LayoutParams(-2, carUi14.dp(56));
                CarUi carUi15 = this.ui;
                if (carUi15 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ui");
                    carUi15 = null;
                }
                $this$headerBar_u24lambda_u2453_u24lambda_u2452.rightMargin = carUi15.dp(14);
                Unit unit2 = Unit.INSTANCE;
                bar.addView(view2, $this$headerBar_u24lambda_u2453_u24lambda_u2452);
            }
        }
        CarUi carUi16 = this.ui;
        if (carUi16 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi = null;
        } else {
            carUi = carUi16;
        }
        bar.addView(carUi.button("Done", true, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda93
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit headerBar$lambda$54;
                headerBar$lambda$54 = SettingsActivity.headerBar$lambda$54(SettingsActivity.this);
                return headerBar$lambda$54;
            }
        }));
        if (!home) {
            return bar;
        }
        FrameLayout $this$headerBar_u24lambda_u2456 = new FrameLayout(this);
        $this$headerBar_u24lambda_u2456.addView(bar, new FrameLayout.LayoutParams(-1, -2));
        View driveStateSwitch = driveStateSwitch();
        FrameLayout.LayoutParams $this$headerBar_u24lambda_u2456_u24lambda_u2455 = new FrameLayout.LayoutParams(-2, -2, 49);
        $this$headerBar_u24lambda_u2456_u24lambda_u2455.topMargin = 0;
        Unit unit3 = Unit.INSTANCE;
        $this$headerBar_u24lambda_u2456.addView(driveStateSwitch, $this$headerBar_u24lambda_u2456_u24lambda_u2455);
        return $this$headerBar_u24lambda_u2456;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit headerBar$lambda$54(SettingsActivity this$0) {
        this$0.finish();
        return Unit.INSTANCE;
    }

    private final View driveStateSwitch() {
        CarUi carUi;
        CarUi carUi2 = this.ui;
        CarUi carUi3 = null;
        if (carUi2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi = null;
        } else {
            carUi = carUi2;
        }
        TextView b = CarUi.button$default(carUi, "Auto", false, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda44
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit driveStateSwitch$lambda$59;
                driveStateSwitch$lambda$59 = SettingsActivity.driveStateSwitch$lambda$59(SettingsActivity.this);
                return driveStateSwitch$lambda$59;
            }
        }, 2, null);
        CarUi carUi4 = this.ui;
        if (carUi4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
        } else {
            carUi3 = carUi4;
        }
        b.setMinWidth(carUi3.dp(170));
        this.driveBtn = b;
        syncDriveState();
        return b;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit driveStateSwitch$lambda$59(final SettingsActivity this$0) {
        String cur = this$0.currentDriveState();
        Iterable $this$map$iv = this$0.driveModes;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            Pair pair = (Pair) item$iv$iv;
            String m = (String) pair.component1();
            String l = (String) pair.component2();
            if (Intrinsics.areEqual(m, cur)) {
                l = l + "   (current)";
            }
            destination$iv$iv.add(l);
        }
        Collection $this$toTypedArray$iv = CollectionsKt.plus((Collection<? extends String>) destination$iv$iv, "Restart openpilot (offroad, then back)");
        String[] labels = (String[]) $this$toTypedArray$iv.toArray(new String[0]);
        new AlertDialog.Builder(this$0).setTitle("Drive state").setItems(labels, new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                SettingsActivity.driveStateSwitch$lambda$59$lambda$58(this$0, dialogInterface, i);
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void driveStateSwitch$lambda$59$lambda$58(SettingsActivity this$0, DialogInterface dialogInterface, int i) {
        if (i == this$0.driveModes.size()) {
            this$0.confirmRestart();
            return;
        }
        Pair<String, String> pair = this$0.driveModes.get(i);
        String m = pair.component1();
        String l = pair.component2();
        this$0.confirmDriveState(m, l);
    }

    private final String currentDriveState() {
        return DriveState.mode$default(DriveState.INSTANCE, false, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda121
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit currentDriveState$lambda$60;
                currentDriveState$lambda$60 = SettingsActivity.currentDriveState$lambda$60(SettingsActivity.this);
                return currentDriveState$lambda$60;
            }
        }, 1, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit currentDriveState$lambda$60(SettingsActivity this$0) {
        this$0.syncDriveState();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void syncDriveState() {
        Integer valueOf;
        int text;
        TextView b = this.driveBtn;
        if (b == null) {
            return;
        }
        String cur = currentDriveState();
        Iterable $this$first$iv = this.driveModes;
        for (Object element$iv : $this$first$iv) {
            Pair it = (Pair) element$iv;
            if (Intrinsics.areEqual(it.getFirst(), cur)) {
                String upperCase = ((String) ((Pair) element$iv).getSecond()).toUpperCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
                b.setText(upperCase);
                if (Intrinsics.areEqual(cur, "onroad")) {
                    valueOf = Integer.valueOf(Color.rgb(52, 190, 112));
                    text = -16777216;
                } else if (Intrinsics.areEqual(cur, "offroad")) {
                    valueOf = Integer.valueOf(Color.rgb(201, 34, 49));
                    text = -1;
                } else {
                    valueOf = Integer.valueOf(Color.rgb(38, 42, 52));
                    text = CarUi.Companion.getTEXT();
                }
                Pair pair = TuplesKt.to(valueOf, Integer.valueOf(text));
                int bg = ((Number) pair.component1()).intValue();
                int fg = ((Number) pair.component2()).intValue();
                b.setTextColor(fg);
                GradientDrawable $this$syncDriveState_u24lambda_u2462 = new GradientDrawable();
                CarUi carUi = this.ui;
                CarUi carUi2 = null;
                if (carUi == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ui");
                    carUi = null;
                }
                $this$syncDriveState_u24lambda_u2462.setCornerRadius(carUi.dp(12));
                $this$syncDriveState_u24lambda_u2462.setColor(bg);
                CarUi carUi3 = this.ui;
                if (carUi3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ui");
                } else {
                    carUi2 = carUi3;
                }
                $this$syncDriveState_u24lambda_u2462.setStroke(carUi2.dp(1), Color.rgb(60, 66, 78));
                b.setBackground($this$syncDriveState_u24lambda_u2462);
                return;
            }
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }

    private final void confirmRestart() {
        new AlertDialog.Builder(this).setTitle("Restart openpilot?").setMessage("The comma goes offroad, waits until openpilot has stopped (about 10-20 s), then goes back to " + (Intrinsics.areEqual(currentDriveState(), "onroad") ? "Onroad" : "Auto") + ".\n\nUse it parked or stopped: openpilot doesn't drive while it restarts. The big model reloads on the eGPU after it starts again.").setPositiveButton("Restart", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda185
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                SettingsActivity.confirmRestart$lambda$64(SettingsActivity.this, dialogInterface, i);
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void confirmRestart$lambda$64(final SettingsActivity this$0, DialogInterface dialogInterface, int i) {
        DriveState.INSTANCE.restart(this$0, new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda120
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit confirmRestart$lambda$64$lambda$63;
                confirmRestart$lambda$64$lambda$63 = SettingsActivity.confirmRestart$lambda$64$lambda$63(this$0, ((Boolean) obj).booleanValue());
                return confirmRestart$lambda$64$lambda$63;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit confirmRestart$lambda$64$lambda$63(SettingsActivity this$0, boolean it) {
        this$0.syncDriveState();
        return Unit.INSTANCE;
    }

    private final void confirmDriveState(final String mode, String label) {
        String msg;
        if (Intrinsics.areEqual(mode, currentDriveState())) {
            return;
        }
        if (Intrinsics.areEqual(mode, "onroad")) {
            msg = "Force the comma ONROAD?\n\nopenpilot starts all driving processes even with the car off. Use for testing; set it back to Auto before driving normally.";
        } else {
            msg = Intrinsics.areEqual(mode, "offroad") ? "Force the comma OFFROAD?\n\nopenpilot stops all driving processes - it will NOT drive, even with the car on. Set it back to Auto before driving." : "Back to Auto?\n\nThe comma goes onroad / offroad by itself with the car's ignition again.";
        }
        new AlertDialog.Builder(this).setTitle("Drive state: " + label).setMessage(msg).setPositiveButton(label, new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda183
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                SettingsActivity.confirmDriveState$lambda$67(SettingsActivity.this, mode, dialogInterface, i);
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void confirmDriveState$lambda$67(final SettingsActivity this$0, String $mode, DialogInterface dialogInterface, int i) {
        DataClient d = Link.INSTANCE.getData();
        if (d == null || !d.getConnected()) {
            Toast.makeText(this$0, "Not connected to the comma", 0).show();
        } else {
            d.request("drive_state", MapsKt.mapOf(TuplesKt.to("mode", $mode)), new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda182
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit confirmDriveState$lambda$67$lambda$66;
                    confirmDriveState$lambda$67$lambda$66 = SettingsActivity.confirmDriveState$lambda$67$lambda$66(this$0, (JSONObject) obj);
                    return confirmDriveState$lambda$67$lambda$66;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit confirmDriveState$lambda$67$lambda$66(final SettingsActivity this$0, JSONObject ack) {
        Intrinsics.checkNotNullParameter(ack, "ack");
        if (!ack.optBoolean("ok")) {
            Toast.makeText(this$0, ack.optString("error", "rejected"), 0).show();
        }
        DriveState.INSTANCE.mode(true, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda33
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit confirmDriveState$lambda$67$lambda$66$lambda$65;
                confirmDriveState$lambda$67$lambda$66$lambda$65 = SettingsActivity.confirmDriveState$lambda$67$lambda$66$lambda$65(this$0);
                return confirmDriveState$lambda$67$lambda$66$lambda$65;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit confirmDriveState$lambda$67$lambda$66$lambda$65(SettingsActivity this$0) {
        this$0.syncDriveState();
        return Unit.INSTANCE;
    }

    private final void showArea(int index, String firstPage) {
        CarUi carUi;
        Object obj;
        final SettingsActivity settingsActivity = this;
        Area a = areas().get(index);
        View view = settingsActivity.current;
        GxPage gxPage = view instanceof GxPage ? (GxPage) view : null;
        if (gxPage != null) {
            gxPage.hide();
        }
        FrameLayout frameLayout = settingsActivity.root;
        if (frameLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout = null;
        }
        frameLayout.removeAllViews();
        settingsActivity.current = null;
        settingsActivity.areaIndex = index;
        LinearLayout col = new LinearLayout(settingsActivity);
        col.setOrientation(1);
        CarUi carUi2 = settingsActivity.ui;
        if (carUi2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi2 = null;
        }
        int dp = carUi2.dp(24);
        CarUi carUi3 = settingsActivity.ui;
        if (carUi3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi3 = null;
        }
        int dp2 = carUi3.dp(16);
        CarUi carUi4 = settingsActivity.ui;
        if (carUi4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi4 = null;
        }
        int dp3 = carUi4.dp(16);
        CarUi carUi5 = settingsActivity.ui;
        if (carUi5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi5 = null;
        }
        col.setPadding(dp, dp2, dp3, carUi5.dp(16));
        settingsActivity.offroadBar = new DriveState.OffroadBar(settingsActivity);
        col.addView(settingsActivity.headerBar(a.getTitle(), a.getSub(), new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda209
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit showArea$lambda$69;
                showArea$lambda$69 = SettingsActivity.showArea$lambda$69(SettingsActivity.this);
                return showArea$lambda$69;
            }
        }));
        LinearLayout body = new LinearLayout(settingsActivity);
        body.setOrientation(0);
        LinearLayout rail = new LinearLayout(settingsActivity);
        rail.setOrientation(1);
        CarUi carUi6 = settingsActivity.ui;
        if (carUi6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi6 = null;
        }
        rail.setPadding(0, 0, 0, carUi6.dp(30));
        ScrollView $this$showArea_u24lambda_u2472 = new ScrollView(settingsActivity);
        $this$showArea_u24lambda_u2472.addView(rail);
        $this$showArea_u24lambda_u2472.setVerticalScrollBarEnabled(false);
        ScrollView scrollView = $this$showArea_u24lambda_u2472;
        CarUi carUi7 = settingsActivity.ui;
        if (carUi7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi7 = null;
        }
        body.addView(scrollView, new LinearLayout.LayoutParams(carUi7.dp(getResources().getConfiguration().screenWidthDp < 1150 ? 200 : 230), -1));
        FrameLayout $this$showArea_u24lambda_u2473 = new FrameLayout(settingsActivity);
        CarUi carUi8 = settingsActivity.ui;
        if (carUi8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi = null;
        } else {
            carUi = carUi8;
        }
        $this$showArea_u24lambda_u2473.setBackground(carUi.corners(CarUi.Companion.getFRAME(), 0, 14, 14, 14));
        CarUi carUi9 = settingsActivity.ui;
        if (carUi9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi9 = null;
        }
        int dp4 = carUi9.dp(14);
        CarUi carUi10 = settingsActivity.ui;
        if (carUi10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi10 = null;
        }
        int dp5 = carUi10.dp(14);
        CarUi carUi11 = settingsActivity.ui;
        if (carUi11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi11 = null;
        }
        int dp6 = carUi11.dp(14);
        CarUi carUi12 = settingsActivity.ui;
        if (carUi12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi12 = null;
        }
        $this$showArea_u24lambda_u2473.setPadding(dp4, dp5, dp6, carUi12.dp(14));
        FrameLayout holder = $this$showArea_u24lambda_u2473;
        settingsActivity.contentHolder = holder;
        body.addView(holder, new LinearLayout.LayoutParams(0, -1, 1.0f));
        col.addView(body, new LinearLayout.LayoutParams(-1, 0, 1.0f));
        FrameLayout frameLayout2 = settingsActivity.root;
        if (frameLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("root");
            frameLayout2 = null;
        }
        frameLayout2.addView(col);
        final ArrayList items = new ArrayList();
        for (Pair<String, List<Rail>> pair : a.getRails()) {
            String section = pair.component1();
            List rails = pair.component2();
            if (section != null) {
                CarUi carUi13 = settingsActivity.ui;
                if (carUi13 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ui");
                    carUi13 = null;
                }
                rail.addView(carUi13.header(section));
            }
            for (final Rail r : rails) {
                SettingIcons.Icon ic = r.getSv();
                CarUi carUi14 = settingsActivity.ui;
                if (carUi14 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ui");
                    carUi14 = null;
                }
                Area a2 = a;
                LinearLayout col2 = col;
                FrameLayout holder2 = holder;
                final TextView v = carUi14.railItem(r.getLabel(), ic.getName(), ic.getGroup());
                v.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda210
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        SettingsActivity.showArea$lambda$75(items, settingsActivity, v, r, view2);
                    }
                });
                items.add(TuplesKt.to(v, r));
                TextView textView = v;
                CarUi carUi15 = settingsActivity.ui;
                if (carUi15 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ui");
                    carUi15 = null;
                }
                LinearLayout.LayoutParams $this$showArea_u24lambda_u2476 = carUi15.lp(0, 8);
                CarUi carUi16 = settingsActivity.ui;
                if (carUi16 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ui");
                    carUi16 = null;
                }
                $this$showArea_u24lambda_u2476.rightMargin = carUi16.dp(18);
                Unit unit = Unit.INSTANCE;
                rail.addView(textView, $this$showArea_u24lambda_u2476);
                settingsActivity = this;
                a = a2;
                col = col2;
                holder = holder2;
            }
            settingsActivity = this;
        }
        ArrayList $this$firstOrNull$iv = items;
        Iterator it = $this$firstOrNull$iv.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            Object element$iv = it.next();
            Pair it2 = (Pair) element$iv;
            if (Intrinsics.areEqual(((Rail) it2.getSecond()).getPage(), firstPage)) {
                obj = element$iv;
                break;
            }
        }
        Pair pair2 = (Pair) obj;
        if (pair2 == null) {
            pair2 = (Pair) CollectionsKt.first((List) items);
        }
        Pair start = pair2;
        ((TextView) start.getFirst()).performClick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit showArea$lambda$69(SettingsActivity this$0) {
        this$0.showHome();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showArea$lambda$75(ArrayList $items, SettingsActivity this$0, TextView $v, Rail $r, View it) {
        CarUi carUi;
        Iterator it2 = $items.iterator();
        Intrinsics.checkNotNullExpressionValue(it2, "iterator(...)");
        while (true) {
            carUi = null;
            if (!it2.hasNext()) {
                break;
            }
            Object next = it2.next();
            Intrinsics.checkNotNullExpressionValue(next, "next(...)");
            TextView o = (TextView) ((Pair) next).component1();
            CarUi carUi2 = this$0.ui;
            if (carUi2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
            } else {
                carUi = carUi2;
            }
            carUi.railSelect(o, false);
        }
        CarUi carUi3 = this$0.ui;
        if (carUi3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
        } else {
            carUi = carUi3;
        }
        carUi.railSelect($v, true);
        this$0.open($r.getPage());
    }

    private final void open(String page) {
        View view;
        FrameLayout holder = this.contentHolder;
        if (holder == null) {
            return;
        }
        if (Intrinsics.areEqual(page, "activity:files")) {
            startActivity(new Intent(this, (Class<?>) FilesActivity.class));
            return;
        }
        if (Intrinsics.areEqual(page, "activity:sensors")) {
            startActivity(new Intent(this, (Class<?>) SensorsActivity.class));
            return;
        }
        if (Intrinsics.areEqual(page, "activity:lanepics")) {
            startActivity(new Intent(this, (Class<?>) LanePicturesActivity.class));
            return;
        }
        if (Intrinsics.areEqual(page, "action:navigation")) {
            startActivity(new Intent(this, (Class<?>) MainActivity.class).putExtra("open_nav", true).addFlags(603979776));
            finish();
            return;
        }
        Map $this$getOrPut$iv = this.panels;
        View view2 = $this$getOrPut$iv.get(page);
        if (view2 == null) {
            view = buildPage(page);
            $this$getOrPut$iv.put(page, view);
        } else {
            view = view2;
        }
        View v = view;
        this.currentPage = page;
        this.webReturn = null;
        setHeaderTool(Intrinsics.areEqual(page, "toggles:all") ? this.togglesSearch : null);
        if (this.current != v) {
            View view3 = this.current;
            GxPage gxPage = view3 instanceof GxPage ? (GxPage) view3 : null;
            if (gxPage != null) {
                gxPage.hide();
            }
            hideGalaxy(this.current);
            holder.removeAllViews();
            TermWeb.INSTANCE.detach();
            ViewParent parent = v.getParent();
            ViewGroup viewGroup = parent instanceof ViewGroup ? (ViewGroup) parent : null;
            if (viewGroup != null) {
                viewGroup.removeView(v);
            }
            holder.addView(v, new FrameLayout.LayoutParams(-1, -1));
            this.current = v;
        }
        if (v instanceof CommaHealthView) {
            ((CommaHealthView) v).start();
        } else if (v instanceof CardPage) {
            ((CardPage) v).refresh();
        } else if (v instanceof DrivingMapView) {
            ((DrivingMapView) v).refresh();
        } else if (v instanceof ToolsHomeView) {
            ((ToolsHomeView) v).refresh();
        } else if (v instanceof ConnectionsView) {
            ((ConnectionsView) v).refresh();
        } else if (v instanceof GalaxySettingsView) {
            ((GalaxySettingsView) v).show();
        } else if (v instanceof GalaxyExtras.QuickSelect) {
            ((GalaxyExtras.QuickSelect) v).show();
        } else if (v instanceof DevicePanel) {
            ((DevicePanel) v).refresh(true);
        } else if (v instanceof CatalogPanel) {
            GalaxyApi galaxyApi = this.api;
            if (galaxyApi != null) {
                GalaxyApi.refreshValues$default(galaxyApi, null, 1, null);
            }
            ((CatalogPanel) v).syncAll();
        } else if (v instanceof ComboPage) {
            GalaxyApi galaxyApi2 = this.api;
            if (galaxyApi2 != null) {
                GalaxyApi.refreshValues$default(galaxyApi2, null, 1, null);
            }
            ((ComboPage) v).refresh();
        } else if (v instanceof GxPage) {
            ((GxPage) v).show();
        } else if (v instanceof GalaxyWeb) {
            ((GalaxyWeb) v).show();
        } else {
            Object tag = v.getTag();
            Runnable runnable = tag instanceof Runnable ? (Runnable) tag : null;
            if (runnable != null) {
                runnable.run();
            }
        }
        if (Intrinsics.areEqual(page, "native:terminal")) {
            holder.removeAllViews();
            String h = this.host;
            if (h == null) {
                return;
            }
            WebView w = TermWeb.INSTANCE.get(this, h);
            TermWeb.INSTANCE.detach();
            holder.addView(w, new FrameLayout.LayoutParams(-1, -1));
            w.requestFocus();
            this.current = w;
        }
    }

    private final void hideGalaxy(View v) {
        GalaxySettingsView galaxySettingsView = v instanceof GalaxySettingsView ? (GalaxySettingsView) v : null;
        if (galaxySettingsView != null) {
            galaxySettingsView.hide();
        }
        GalaxyExtras.QuickSelect quickSelect = v instanceof GalaxyExtras.QuickSelect ? (GalaxyExtras.QuickSelect) v : null;
        if (quickSelect != null) {
            quickSelect.hide();
        }
        CommaHealthView commaHealthView = v instanceof CommaHealthView ? (CommaHealthView) v : null;
        if (commaHealthView != null) {
            commaHealthView.stop();
        }
        DoorsBeltsView doorsBeltsView = v instanceof DoorsBeltsView ? (DoorsBeltsView) v : null;
        if (doorsBeltsView != null) {
            doorsBeltsView.stop();
        }
        KeyFobView keyFobView = v instanceof KeyFobView ? (KeyFobView) v : null;
        if (keyFobView != null) {
            keyFobView.stop();
        }
        HubPowerView hubPowerView = v instanceof HubPowerView ? (HubPowerView) v : null;
        if (hubPowerView != null) {
            hubPowerView.stop();
        }
        BookmarksView bookmarksView = v instanceof BookmarksView ? (BookmarksView) v : null;
        if (bookmarksView != null) {
            bookmarksView.stop();
        }
        if (v instanceof GalaxyWeb) {
            GalaxyWebPool.INSTANCE.park();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: SettingsActivity.kt */
    final class ComboPage extends ScrollView {
        private final List<View> parts;
        final /* synthetic */ SettingsActivity this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public ComboPage(SettingsActivity this$0, List<? extends View> parts) {
            super(this$0);
            Intrinsics.checkNotNullParameter(parts, "parts");
            this.this$0 = this$0;
            this.parts = parts;
            LinearLayout col = new LinearLayout(getContext());
            col.setOrientation(1);
            for (View p : this.parts) {
                col.addView(p, new LinearLayout.LayoutParams(-1, -2));
            }
            addView(col);
        }

        public final List<View> getParts() {
            return this.parts;
        }

        public final void tick() {
            for (View p : this.parts) {
                DevicePanel devicePanel = p instanceof DevicePanel ? (DevicePanel) p : null;
                if (devicePanel != null) {
                    devicePanel.onTick();
                }
                CatalogPanel catalogPanel = p instanceof CatalogPanel ? (CatalogPanel) p : null;
                if (catalogPanel != null) {
                    catalogPanel.onTick();
                }
            }
        }

        public final void refresh() {
            for (View p : this.parts) {
                DevicePanel devicePanel = p instanceof DevicePanel ? (DevicePanel) p : null;
                if (devicePanel != null) {
                    devicePanel.refresh(true);
                }
                CatalogPanel catalogPanel = p instanceof CatalogPanel ? (CatalogPanel) p : null;
                if (catalogPanel != null) {
                    catalogPanel.syncAll();
                }
            }
        }
    }

    private final View comboPage(List<? extends View> parts) {
        return new ComboPage(this, parts);
    }

    private final View buildPage(String page) {
        try {
            this.buildGroup = Companion.railIcon(page).getGroup();
            return buildPageUnsafe(page);
        } catch (Exception e) {
            Log.e("StarView/Settings", "page " + page, e);
            StackTraceElement[] stackTrace = e.getStackTrace();
            Intrinsics.checkNotNullExpressionValue(stackTrace, "getStackTrace(...)");
            return offline("This page failed to build:\n" + e + "\n\n" + CollectionsKt.joinToString(ArraysKt.take(stackTrace, 6), "\n", "", "", -1, "...", null));
        }
    }

    private final View buildPageUnsafe(String page) {
        CarUi carUi;
        CarUi carUi2 = null;
        if (Intrinsics.areEqual(page, "drivingmap")) {
            SettingsActivity settingsActivity = this;
            CarUi carUi3 = this.ui;
            if (carUi3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
            } else {
                carUi2 = carUi3;
            }
            return new DrivingMapView(settingsActivity, carUi2, new Function2() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda189
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Unit buildPageUnsafe$lambda$79;
                    buildPageUnsafe$lambda$79 = SettingsActivity.buildPageUnsafe$lambda$79(SettingsActivity.this, (String) obj, (String) obj2);
                    return buildPageUnsafe$lambda$79;
                }
            });
        }
        if (!Intrinsics.areEqual(page, "toolshome")) {
            if (Intrinsics.areEqual(page, "connections")) {
                SettingsActivity settingsActivity2 = this;
                CarUi carUi4 = this.ui;
                if (carUi4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ui");
                } else {
                    carUi2 = carUi4;
                }
                return new ConnectionsView(settingsActivity2, carUi2, new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda192
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        Unit buildPageUnsafe$lambda$82;
                        buildPageUnsafe$lambda$82 = SettingsActivity.buildPageUnsafe$lambda$82(SettingsActivity.this, (String) obj);
                        return buildPageUnsafe$lambda$82;
                    }
                });
            }
            if (Intrinsics.areEqual(page, "health")) {
                SettingsActivity settingsActivity3 = this;
                CarUi carUi5 = this.ui;
                if (carUi5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ui");
                } else {
                    carUi2 = carUi5;
                }
                return new CommaHealthView(settingsActivity3, carUi2);
            }
            if (Intrinsics.areEqual(page, "doorbelt")) {
                SettingsActivity settingsActivity4 = this;
                CarUi carUi6 = this.ui;
                if (carUi6 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ui");
                } else {
                    carUi2 = carUi6;
                }
                return new DoorsBeltsView(settingsActivity4, carUi2);
            }
            if (Intrinsics.areEqual(page, "keyfob")) {
                SettingsActivity settingsActivity5 = this;
                CarUi carUi7 = this.ui;
                if (carUi7 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ui");
                } else {
                    carUi2 = carUi7;
                }
                return new KeyFobView(settingsActivity5, carUi2);
            }
            if (Intrinsics.areEqual(page, "hubpower")) {
                SettingsActivity settingsActivity6 = this;
                CarUi carUi8 = this.ui;
                if (carUi8 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ui");
                } else {
                    carUi2 = carUi8;
                }
                return new HubPowerView(settingsActivity6, carUi2);
            }
            if (Intrinsics.areEqual(page, "roaddata")) {
                SettingsActivity settingsActivity7 = this;
                CarUi carUi9 = this.ui;
                if (carUi9 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ui");
                } else {
                    carUi2 = carUi9;
                }
                return new RoadDataView(settingsActivity7, carUi2);
            }
            if (Intrinsics.areEqual(page, "bookmarks")) {
                SettingsActivity settingsActivity8 = this;
                CarUi carUi10 = this.ui;
                if (carUi10 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ui");
                } else {
                    carUi2 = carUi10;
                }
                return new BookmarksView(settingsActivity8, carUi2);
            }
            if (Intrinsics.areEqual(page, "journal:last")) {
                SettingsActivity settingsActivity9 = this;
                CarUi carUi11 = this.ui;
                if (carUi11 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ui");
                } else {
                    carUi2 = carUi11;
                }
                return new LastDriveView(settingsActivity9, carUi2);
            }
            if (Intrinsics.areEqual(page, "journal:trends")) {
                SettingsActivity settingsActivity10 = this;
                CarUi carUi12 = this.ui;
                if (carUi12 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ui");
                } else {
                    carUi2 = carUi12;
                }
                return new DrivingTrendsView(settingsActivity10, carUi2);
            }
            if (Intrinsics.areEqual(page, "journal:alerts")) {
                SettingsActivity settingsActivity11 = this;
                CarUi carUi13 = this.ui;
                if (carUi13 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ui");
                } else {
                    carUi2 = carUi13;
                }
                return new AlertHistoryView(settingsActivity11, carUi2);
            }
            if (Intrinsics.areEqual(page, "journal:spots")) {
                SettingsActivity settingsActivity12 = this;
                CarUi carUi14 = this.ui;
                if (carUi14 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ui");
                } else {
                    carUi2 = carUi14;
                }
                return new TakeoverSpotsView(settingsActivity12, carUi2);
            }
            if (Intrinsics.areEqual(page, "profiles")) {
                SettingsActivity settingsActivity13 = this;
                CarUi carUi15 = this.ui;
                if (carUi15 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ui");
                } else {
                    carUi2 = carUi15;
                }
                return new DriverProfilesView(settingsActivity13, carUi2);
            }
            if (StringsKt.startsWith(page, "galaxy:", false)) {
                return galaxyPage(StringsKt.removePrefix(page, (CharSequence) "galaxy:"));
            }
            if (StringsKt.startsWith(page, "gweb:", false)) {
                String h = this.host;
                if (h == null) {
                    return offline("Not connected to the comma.");
                }
                SettingsActivity settingsActivity14 = this;
                CarUi carUi16 = this.ui;
                if (carUi16 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ui");
                    carUi = null;
                } else {
                    carUi = carUi16;
                }
                return new GalaxyWeb(settingsActivity14, carUi, h, StringsKt.substringBefore(StringsKt.removePrefix(page, (CharSequence) "gweb:"), "|", (StringsKt.removePrefix(page, (CharSequence) "gweb:"))), StringsKt.substringAfter(page, "|", page), new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda193
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        boolean returnFromWeb;
                        returnFromWeb = SettingsActivity.this.returnFromWeb();
                        return Boolean.valueOf(returnFromWeb);
                    }
                });
            }
            if (Intrinsics.areEqual(page, "quickselect")) {
                if (this.host == null) {
                    return offline("Not connected to the comma.");
                }
                SettingsActivity settingsActivity15 = this;
                CarUi carUi17 = this.ui;
                if (carUi17 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ui");
                } else {
                    carUi2 = carUi17;
                }
                return new GalaxyExtras.QuickSelect(settingsActivity15, carUi2);
            }
            if (StringsKt.startsWith(page, "cat:", false)) {
                return catalogPage$default(this, StringsKt.removePrefix(page, (CharSequence) "cat:"), false, 2, null);
            }
            if (StringsKt.startsWith(page, "native:", false)) {
                return Intrinsics.areEqual(page, "native:terminal") ? new View(this) : new DevicePanel(this, StringsKt.removePrefix(page, (CharSequence) "native:"));
            }
            if (StringsKt.startsWith(page, "gx:", false)) {
                return gxPage(StringsKt.removePrefix(page, (CharSequence) "gx:"));
            }
            if (Intrinsics.areEqual(page, "toggles:all")) {
                return togglesPage();
            }
            if (StringsKt.startsWith(page, "uigroup:", false)) {
                return uiGroupPage(StringsKt.removePrefix(page, (CharSequence) "uigroup:"));
            }
            if (Intrinsics.areEqual(page, "tablet:theme")) {
                return tabletThemePage();
            }
            if (Intrinsics.areEqual(page, "tablet:connection")) {
                return tabletConnectionPage();
            }
            if (Intrinsics.areEqual(page, "tablet:view")) {
                return tabletViewPage();
            }
            if (Intrinsics.areEqual(page, "tablet:maps")) {
                return tabletMapsPage();
            }
            if (Intrinsics.areEqual(page, "tablet:about")) {
                return tabletAboutPage();
            }
            if (Intrinsics.areEqual(page, "tablet:storage")) {
                return tabletStoragePage();
            }
            if (Intrinsics.areEqual(page, "tablet:backup")) {
                return tabletBackupPage();
            }
            if (Intrinsics.areEqual(page, "tablet:nav")) {
                return tabletNavPage();
            }
            if (Intrinsics.areEqual(page, "tablet:braking")) {
                return tabletBrakingPage();
            }
            if (Intrinsics.areEqual(page, "tablet:voice")) {
                return tabletVoicePage();
            }
            if (Intrinsics.areEqual(page, "tablet:blinker")) {
                return tabletBlinkerPage();
            }
            if (Intrinsics.areEqual(page, "tablet:ideas")) {
                return newIdeasPage();
            }
            if (Intrinsics.areEqual(page, "tablet:voicestudio")) {
                return tabletVoiceStudioPage();
            }
            if (Intrinsics.areEqual(page, "tablet:techstream")) {
                return techStreamPage();
            }
            if (Intrinsics.areEqual(page, "tablet:rtk")) {
                return tabletRtkPage();
            }
            TextView $this$buildPageUnsafe_u24lambda_u2485 = new TextView(this);
            $this$buildPageUnsafe_u24lambda_u2485.setText(page);
            return $this$buildPageUnsafe_u24lambda_u2485;
        }
        SettingsActivity settingsActivity16 = this;
        CarUi carUi18 = this.ui;
        if (carUi18 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
        } else {
            carUi2 = carUi18;
        }
        Iterable $this$filter$iv = GalaxyTools.INSTANCE.list(this);
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            GalaxyTools.Tool it = (GalaxyTools.Tool) element$iv$iv;
            if (!GalaxyTools.INSTANCE.getPLACED_ELSEWHERE().contains(it.getPath())) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        return new ToolsHomeView(settingsActivity16, carUi2, (List) destination$iv$iv, new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda190
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit buildPageUnsafe$lambda$81;
                buildPageUnsafe$lambda$81 = SettingsActivity.buildPageUnsafe$lambda$81(SettingsActivity.this, (String) obj);
                return buildPageUnsafe$lambda$81;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildPageUnsafe$lambda$79(SettingsActivity this$0, String p, String t) {
        Intrinsics.checkNotNullParameter(p, "p");
        Intrinsics.checkNotNullParameter(t, "t");
        this$0.openSub(p, t);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildPageUnsafe$lambda$81(SettingsActivity this$0, String it) {
        Intrinsics.checkNotNullParameter(it, "it");
        this$0.openToolTarget(it);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildPageUnsafe$lambda$82(SettingsActivity this$0, String it) {
        Intrinsics.checkNotNullParameter(it, "it");
        this$0.openToolTarget(it);
        return Unit.INSTANCE;
    }

    static /* synthetic */ View catalogPage$default(SettingsActivity settingsActivity, String str, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return settingsActivity.catalogPage(str, z);
    }

    /* JADX WARN: Removed duplicated region for block: B:69:0x01f0  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x01f3 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final View catalogPage(final String spec, boolean inPanel) {
        Object element$iv;
        List<GalaxyApi.Param> list;
        Iterable $this$filter$iv;
        int $i$f$filter;
        boolean z;
        boolean z2;
        ArrayList arrayList;
        GalaxyApi a = this.api;
        if (a == null) {
            return offline("Not connected to the comma. Connect first, then come back.");
        }
        String name = StringsKt.substringBefore(spec, ":", spec);
        String filter = StringsKt.substringAfter(spec, ":", "");
        Iterable $this$firstOrNull$iv = a.getCatalog();
        Iterator it = $this$firstOrNull$iv.iterator();
        while (true) {
            if (!it.hasNext()) {
                element$iv = null;
                break;
            }
            element$iv = it.next();
            if (Intrinsics.areEqual(((GalaxyApi.Category) element$iv).getName(), name)) {
                break;
            }
        }
        GalaxyApi.Category cat = (GalaxyApi.Category) element$iv;
        if (cat == null) {
            final View v = offline(a.getCatalog().isEmpty() ? "Loading the settings catalog from the comma…" : "Section \"" + name + "\" not found in this StarPilot version.");
            this.handler.postDelayed(new Runnable() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda158
                @Override // java.lang.Runnable
                public final void run() {
                    SettingsActivity.catalogPage$lambda$87(SettingsActivity.this, v, spec);
                }
            }, 1500L);
            return v;
        }
        List<GalaxyApi.Param> params = cat.getParams();
        if (filter.length() > 0) {
            String key = filter.substring(1);
            Intrinsics.checkNotNullExpressionValue(key, "substring(...)");
            HashSet sub = new HashSet();
            ArrayDeque pending = new ArrayDeque(CollectionsKt.listOf(key));
            sub.add(key);
            while (!pending.isEmpty()) {
                String k = (String) pending.removeFirst();
                for (GalaxyApi.Param p : cat.getParams()) {
                    if (Intrinsics.areEqual(p.getParentKey(), k) && sub.add(p.getKey())) {
                        pending.add(p.getKey());
                    }
                }
            }
            if (StringsKt.startsWith(filter, "+", false)) {
                Iterable $this$filter$iv2 = cat.getParams();
                Collection destination$iv$iv = new ArrayList();
                for (Object element$iv$iv : $this$filter$iv2) {
                    if (sub.contains(((GalaxyApi.Param) element$iv$iv).getKey())) {
                        destination$iv$iv.add(element$iv$iv);
                    }
                }
                arrayList = (List) destination$iv$iv;
            } else {
                Iterable $this$filter$iv3 = cat.getParams();
                Collection destination$iv$iv2 = new ArrayList();
                for (Object element$iv$iv2 : $this$filter$iv3) {
                    List<GalaxyApi.Param> list2 = params;
                    Object params2 = ((GalaxyApi.Param) element$iv$iv2).getKey();
                    if (!sub.contains(params2)) {
                        destination$iv$iv2.add(element$iv$iv2);
                    }
                    params = list2;
                }
                arrayList = (List) destination$iv$iv2;
            }
            list = arrayList;
        } else {
            list = params;
        }
        Iterable params3 = list;
        Iterable $this$filter$iv4 = params3;
        int $i$f$filter2 = 0;
        Collection destination$iv$iv3 = new ArrayList();
        for (Object element$iv$iv3 : $this$filter$iv4) {
            GalaxyApi.Param it2 = (GalaxyApi.Param) element$iv$iv3;
            if (it2.getParentKey() == null) {
                Iterable $this$any$iv = list;
                $this$filter$iv = $this$filter$iv4;
                if (($this$any$iv instanceof Collection) && ((Collection) $this$any$iv).isEmpty()) {
                    $i$f$filter = $i$f$filter2;
                    z2 = false;
                } else {
                    Iterator it3 = $this$any$iv.iterator();
                    while (true) {
                        if (!it3.hasNext()) {
                            $i$f$filter = $i$f$filter2;
                            z2 = false;
                            break;
                        }
                        Object element$iv2 = it3.next();
                        GalaxyApi.Param c = (GalaxyApi.Param) element$iv2;
                        Iterator it4 = it3;
                        $i$f$filter = $i$f$filter2;
                        if (Intrinsics.areEqual(c.getParentKey(), it2.getKey())) {
                            z2 = true;
                            break;
                        }
                        it3 = it4;
                        $i$f$filter2 = $i$f$filter;
                    }
                }
                if (z2) {
                    z = true;
                    if (!z) {
                        destination$iv$iv3.add(element$iv$iv3);
                    }
                    $this$filter$iv4 = $this$filter$iv;
                    $i$f$filter2 = $i$f$filter;
                }
            } else {
                $this$filter$iv = $this$filter$iv4;
                $i$f$filter = $i$f$filter2;
            }
            z = false;
            if (!z) {
            }
            $this$filter$iv4 = $this$filter$iv;
            $i$f$filter2 = $i$f$filter;
        }
        List parents = (List) destination$iv$iv3;
        if (list.size() > 24 && parents.size() >= 2) {
            return subTabbedCatalog(a, list, parents, name, inPanel);
        }
        final CatalogPanel panel = new CatalogPanel(this, a, list, name, true, new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda159
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit catalogPage$lambda$92;
                catalogPage$lambda$92 = SettingsActivity.catalogPage$lambda$92(SettingsActivity.this, (String) obj);
                return catalogPage$lambda$92;
            }
        });
        FrameLayout $this$catalogPage_u24lambda_u2494 = pageBox$default(this, panel, inPanel, false, 4, null);
        $this$catalogPage_u24lambda_u2494.setTag(new Runnable() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda160
            @Override // java.lang.Runnable
            public final void run() {
                SettingsActivity.catalogPage$lambda$94$lambda$93(panel, this);
            }
        });
        return $this$catalogPage_u24lambda_u2494;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void catalogPage$lambda$87(SettingsActivity this$0, View $v, String $spec) {
        if (this$0.current == $v) {
            this$0.panels.remove("cat:" + $spec);
            this$0.open("cat:" + $spec);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit catalogPage$lambda$92(SettingsActivity this$0, String route) {
        Intrinsics.checkNotNullParameter(route, "route");
        this$0.openRoute(route);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void catalogPage$lambda$94$lambda$93(CatalogPanel $panel, SettingsActivity this$0) {
        $panel.syncAll();
        this$0.current = $panel;
    }

    private final FrameLayout pageBox(View content, boolean inPanel, boolean joinTop) {
        CarUi carUi;
        GradientDrawable rounded$default;
        CarUi carUi2;
        FrameLayout $this$pageBox_u24lambda_u2496 = new FrameLayout(this);
        CarUi carUi3 = null;
        CarUi carUi4 = this.ui;
        if (joinTop) {
            if (carUi4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi2 = null;
            } else {
                carUi2 = carUi4;
            }
            rounded$default = carUi2.corners(CarUi.Companion.getPAGE(), 0, 0, 12, 12);
        } else {
            if (carUi4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi = null;
            } else {
                carUi = carUi4;
            }
            rounded$default = CarUi.rounded$default(carUi, CarUi.Companion.getPAGE(), inPanel ? 12 : 14, null, 4, null);
        }
        $this$pageBox_u24lambda_u2496.setBackground(rounded$default);
        $this$pageBox_u24lambda_u2496.setClipToOutline(true);
        $this$pageBox_u24lambda_u2496.addView(content, new FrameLayout.LayoutParams(-1, -1));
        if (inPanel || joinTop) {
            int i = 0;
            LinearLayout.LayoutParams $this$pageBox_u24lambda_u2496_u24lambda_u2495 = new LinearLayout.LayoutParams(-1, 0, 1.0f);
            CarUi carUi5 = this.ui;
            if (carUi5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi5 = null;
            }
            int dp = carUi5.dp(14);
            if (!joinTop) {
                CarUi carUi6 = this.ui;
                if (carUi6 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ui");
                    carUi6 = null;
                }
                i = carUi6.dp(14);
            }
            CarUi carUi7 = this.ui;
            if (carUi7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi7 = null;
            }
            int dp2 = carUi7.dp(14);
            CarUi carUi8 = this.ui;
            if (carUi8 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
            } else {
                carUi3 = carUi8;
            }
            $this$pageBox_u24lambda_u2496_u24lambda_u2495.setMargins(dp, i, dp2, carUi3.dp(14));
            $this$pageBox_u24lambda_u2496.setLayoutParams($this$pageBox_u24lambda_u2496_u24lambda_u2495);
        }
        return $this$pageBox_u24lambda_u2496;
    }

    static /* synthetic */ FrameLayout pageBox$default(SettingsActivity settingsActivity, View view, boolean z, boolean z2, int i, Object obj) {
        if ((i & 4) != 0) {
            z2 = false;
        }
        return settingsActivity.pageBox(view, z, z2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x00db, code lost:
    
        if (kotlin.text.StringsKt.startsWith(r32, "Longitudinal", false) == false) goto L42;
     */
    /* JADX WARN: Removed duplicated region for block: B:17:0x00b3  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00b6 A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r0v24, types: [T, kotlin.jvm.functions.Function1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final View subTabbedCatalog(final GalaxyApi a, List<GalaxyApi.Param> params, List<GalaxyApi.Param> parents, final String name, boolean inPanel) {
        CarUi carUi;
        GradientDrawable rounded$default;
        CarUi carUi2;
        CarUi carUi3;
        Iterable $this$filter$iv;
        boolean z;
        boolean z2;
        LinearLayout $this$subTabbedCatalog_u24lambda_u2497 = new LinearLayout(this);
        $this$subTabbedCatalog_u24lambda_u2497.setOrientation(1);
        if (inPanel) {
            rounded$default = null;
        } else {
            CarUi carUi4 = this.ui;
            if (carUi4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi = null;
            } else {
                carUi = carUi4;
            }
            rounded$default = CarUi.rounded$default(carUi, CarUi.Companion.getSECTION(), 14, null, 4, null);
        }
        $this$subTabbedCatalog_u24lambda_u2497.setBackground(rounded$default);
        final FrameLayout holder = new FrameLayout(this);
        List<GalaxyApi.Param> $this$filter$iv2 = params;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv2) {
            GalaxyApi.Param it = (GalaxyApi.Param) element$iv$iv;
            if (it.getParentKey() == null) {
                List<GalaxyApi.Param> $this$none$iv = params;
                if (($this$none$iv instanceof Collection) && $this$none$iv.isEmpty()) {
                    $this$filter$iv = $this$filter$iv2;
                    z2 = true;
                } else {
                    Iterator it2 = $this$none$iv.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            $this$filter$iv = $this$filter$iv2;
                            z2 = true;
                            break;
                        }
                        Object element$iv = it2.next();
                        $this$filter$iv = $this$filter$iv2;
                        if (Intrinsics.areEqual(((GalaxyApi.Param) element$iv).getParentKey(), it.getKey())) {
                            z2 = false;
                            break;
                        }
                        $this$filter$iv2 = $this$filter$iv;
                    }
                }
                if (z2) {
                    z = true;
                    if (!z) {
                        destination$iv$iv.add(element$iv$iv);
                    }
                    $this$filter$iv2 = $this$filter$iv;
                }
            } else {
                $this$filter$iv = $this$filter$iv2;
            }
            z = false;
            if (!z) {
            }
            $this$filter$iv2 = $this$filter$iv;
        }
        List leaves = (List) destination$iv$iv;
        final ArrayList tabs = new ArrayList();
        if (!leaves.isEmpty()) {
        }
        tabs.add(TuplesKt.to("General", leaves));
        Iterator<GalaxyApi.Param> it3 = parents.iterator();
        while (it3.hasNext()) {
            GalaxyApi.Param p = it3.next();
            HashSet sub = new HashSet();
            ArrayDeque pending = new ArrayDeque(CollectionsKt.listOf(p.getKey()));
            sub.add(p.getKey());
            while (!pending.isEmpty()) {
                String k = (String) pending.removeFirst();
                for (GalaxyApi.Param c : params) {
                    if (Intrinsics.areEqual(c.getParentKey(), k) && sub.add(c.getKey())) {
                        pending.add(c.getKey());
                    }
                }
            }
            String k2 = p.getLabel();
            Collection destination$iv$iv2 = new ArrayList();
            for (Object element$iv$iv2 : params) {
                GalaxyApi.Param it4 = (GalaxyApi.Param) element$iv$iv2;
                Iterator<GalaxyApi.Param> it5 = it3;
                if (sub.contains(it4.getKey())) {
                    destination$iv$iv2.add(element$iv$iv2);
                }
                it3 = it5;
            }
            tabs.add(TuplesKt.to(k2, (List) destination$iv$iv2));
            it3 = it3;
        }
        final HashMap cache = new HashMap();
        final Ref.IntRef sel = new Ref.IntRef();
        final Ref.ObjectRef paint = new Ref.ObjectRef();
        CarUi carUi5 = this.ui;
        if (carUi5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi2 = null;
        } else {
            carUi2 = carUi5;
        }
        ArrayList $this$map$iv = tabs;
        Collection destination$iv$iv3 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            Pair it6 = (Pair) item$iv$iv;
            destination$iv$iv3.add((String) it6.getFirst());
            $this$map$iv = $this$map$iv;
        }
        Pair<View, Function1<Integer, Unit>> browserTabs = carUi2.browserTabs((List) destination$iv$iv3, 2, new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda95
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit subTabbedCatalog$lambda$104;
                subTabbedCatalog$lambda$104 = SettingsActivity.subTabbedCatalog$lambda$104(sel, paint, cache, holder, this, a, tabs, name, ((Integer) obj).intValue());
                return subTabbedCatalog$lambda$104;
            }
        });
        View strip = browserTabs.component1();
        paint.element = (Function1) browserTabs.component2();
        if (!inPanel) {
            CarUi carUi6 = this.ui;
            if (carUi6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi3 = null;
            } else {
                carUi3 = carUi6;
            }
            strip.setBackground(carUi3.corners(CarUi.Companion.getSECTION(), 14, 14, 0, 0));
        }
        $this$subTabbedCatalog_u24lambda_u2497.addView(strip, new LinearLayout.LayoutParams(-1, -2));
        $this$subTabbedCatalog_u24lambda_u2497.addView(pageBox(holder, true, true));
        $this$subTabbedCatalog_u24lambda_u2497.post(new Runnable() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda96
            @Override // java.lang.Runnable
            public final void run() {
                SettingsActivity.subTabbedCatalog$lambda$105(sel, paint, cache, holder, this, a, tabs, name);
            }
        });
        $this$subTabbedCatalog_u24lambda_u2497.setTag(new Runnable() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda97
            @Override // java.lang.Runnable
            public final void run() {
                SettingsActivity.subTabbedCatalog$lambda$106(sel, paint, cache, holder, this, a, tabs, name);
            }
        });
        return $this$subTabbedCatalog_u24lambda_u2497;
    }

    private static final void subTabbedCatalog$select(Ref.IntRef sel, Ref.ObjectRef<Function1<Integer, Unit>> objectRef, HashMap<Integer, CatalogPanel> hashMap, FrameLayout holder, final SettingsActivity this$0, GalaxyApi $a, ArrayList<Pair<String, List<GalaxyApi.Param>>> arrayList, String $name, int i) {
        Function1<Integer, Unit> function1;
        CatalogPanel catalogPanel;
        sel.element = i;
        if (objectRef.element == null) {
            Intrinsics.throwUninitializedPropertyAccessException("paint");
            function1 = null;
        } else {
            function1 = objectRef.element;
        }
        function1.invoke(Integer.valueOf(i));
        HashMap<Integer, CatalogPanel> $this$getOrPut$iv = hashMap;
        Integer valueOf = Integer.valueOf(i);
        CatalogPanel catalogPanel2 = $this$getOrPut$iv.get(valueOf);
        if (catalogPanel2 == null) {
            catalogPanel = new CatalogPanel(this$0, $a, arrayList.get(i).getSecond(), $name, true, new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda242
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit subTabbedCatalog$select$lambda$102$lambda$101;
                    subTabbedCatalog$select$lambda$102$lambda$101 = SettingsActivity.subTabbedCatalog$select$lambda$102$lambda$101(this$0, (String) obj);
                    return subTabbedCatalog$select$lambda$102$lambda$101;
                }
            });
            $this$getOrPut$iv.put(valueOf, catalogPanel);
        } else {
            catalogPanel = catalogPanel2;
        }
        CatalogPanel v = catalogPanel;
        if (v.getParent() != holder) {
            holder.removeAllViews();
            ViewParent parent = v.getParent();
            ViewGroup viewGroup = parent instanceof ViewGroup ? (ViewGroup) parent : null;
            if (viewGroup != null) {
                viewGroup.removeView(v);
            }
            holder.addView(v);
        }
        v.syncAll();
        this$0.current = v;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit subTabbedCatalog$select$lambda$102$lambda$101(SettingsActivity this$0, String r) {
        Intrinsics.checkNotNullParameter(r, "r");
        this$0.openRoute(r);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit subTabbedCatalog$lambda$104(Ref.IntRef $sel, Ref.ObjectRef $paint, HashMap $cache, FrameLayout $holder, SettingsActivity this$0, GalaxyApi $a, ArrayList $tabs, String $name, int i) {
        subTabbedCatalog$select($sel, $paint, $cache, $holder, this$0, $a, $tabs, $name, i);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void subTabbedCatalog$lambda$105(Ref.IntRef $sel, Ref.ObjectRef $paint, HashMap $cache, FrameLayout $holder, SettingsActivity this$0, GalaxyApi $a, ArrayList $tabs, String $name) {
        subTabbedCatalog$select($sel, $paint, $cache, $holder, this$0, $a, $tabs, $name, $sel.element);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void subTabbedCatalog$lambda$106(Ref.IntRef $sel, Ref.ObjectRef $paint, HashMap $cache, FrameLayout $holder, SettingsActivity this$0, GalaxyApi $a, ArrayList $tabs, String $name) {
        subTabbedCatalog$select($sel, $paint, $cache, $holder, this$0, $a, $tabs, $name, $sel.element);
    }

    /* JADX WARN: Type inference failed for: r5v16, types: [T, com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda10] */
    private final View togglesPage() {
        CarUi carUi;
        CarUi carUi2;
        CarUi carUi3;
        final GalaxyApi a = this.api;
        if (a == null) {
            return offline("Not connected to the comma. Connect first, then come back.");
        }
        Iterable $this$filter$iv = a.getCatalog();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            GalaxyApi.Category it = (GalaxyApi.Category) element$iv$iv;
            if (!it.getParams().isEmpty()) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterable $this$map$iv = (List) destination$iv$iv;
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            GalaxyApi.Category it2 = (GalaxyApi.Category) item$iv$iv;
            destination$iv$iv2.add(it2.getName());
        }
        final List sections = (List) destination$iv$iv2;
        if (sections.isEmpty()) {
            final View v = offline("Loading the settings catalog from the comma…");
            this.handler.postDelayed(new Runnable() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    SettingsActivity.togglesPage$lambda$109(SettingsActivity.this, v);
                }
            }, 1500L);
            return v;
        }
        LinearLayout $this$togglesPage_u24lambda_u24110 = new LinearLayout(this);
        $this$togglesPage_u24lambda_u24110.setOrientation(1);
        final EditText $this$togglesPage_u24lambda_u24111 = new EditText(this);
        $this$togglesPage_u24lambda_u24111.setHint("Search settings");
        $this$togglesPage_u24lambda_u24111.setTextSize(19.0f);
        $this$togglesPage_u24lambda_u24111.setSingleLine();
        $this$togglesPage_u24lambda_u24111.setTextColor(CarUi.Companion.getTEXT());
        $this$togglesPage_u24lambda_u24111.setHintTextColor(CarUi.Companion.getDIM());
        $this$togglesPage_u24lambda_u24111.setBackground(null);
        CarUi carUi4 = this.ui;
        if (carUi4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi4 = null;
        }
        int dp = carUi4.dp(20);
        CarUi carUi5 = this.ui;
        if (carUi5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi5 = null;
        }
        int dp2 = carUi5.dp(12);
        CarUi carUi6 = this.ui;
        if (carUi6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi6 = null;
        }
        int dp3 = carUi6.dp(8);
        CarUi carUi7 = this.ui;
        if (carUi7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi7 = null;
        }
        $this$togglesPage_u24lambda_u24111.setPadding(dp, dp2, dp3, carUi7.dp(12));
        $this$togglesPage_u24lambda_u24111.setImeOptions(3);
        final TextView $this$togglesPage_u24lambda_u24113 = new TextView(this);
        $this$togglesPage_u24lambda_u24113.setText("✕");
        $this$togglesPage_u24lambda_u24113.setTextSize(17.0f);
        $this$togglesPage_u24lambda_u24113.setTextColor(CarUi.Companion.getBG());
        $this$togglesPage_u24lambda_u24113.setGravity(17);
        $this$togglesPage_u24lambda_u24113.setTypeface(Typeface.DEFAULT_BOLD);
        CarUi carUi8 = this.ui;
        if (carUi8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi = null;
        } else {
            carUi = carUi8;
        }
        $this$togglesPage_u24lambda_u24113.setBackground(CarUi.rounded$default(carUi, CarUi.Companion.getDIM(), 15, null, 4, null));
        $this$togglesPage_u24lambda_u24113.setVisibility(8);
        $this$togglesPage_u24lambda_u24113.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SettingsActivity.togglesPage$lambda$113$lambda$112($this$togglesPage_u24lambda_u24111, this, view);
            }
        });
        LinearLayout $this$togglesPage_u24lambda_u24115 = new LinearLayout(this);
        $this$togglesPage_u24lambda_u24115.setOrientation(0);
        $this$togglesPage_u24lambda_u24115.setGravity(16);
        CarUi carUi9 = this.ui;
        if (carUi9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi2 = null;
        } else {
            carUi2 = carUi9;
        }
        $this$togglesPage_u24lambda_u24115.setBackground(CarUi.rounded$default(carUi2, CarUi.Companion.getCARD(), 14, null, 4, null));
        $this$togglesPage_u24lambda_u24115.addView($this$togglesPage_u24lambda_u24111, new LinearLayout.LayoutParams(0, -2, 1.0f));
        TextView textView = $this$togglesPage_u24lambda_u24113;
        CarUi carUi10 = this.ui;
        if (carUi10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi10 = null;
        }
        int dp4 = carUi10.dp(30);
        CarUi carUi11 = this.ui;
        if (carUi11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi11 = null;
        }
        LinearLayout.LayoutParams $this$togglesPage_u24lambda_u24115_u24lambda_u24114 = new LinearLayout.LayoutParams(dp4, carUi11.dp(30));
        CarUi carUi12 = this.ui;
        if (carUi12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi12 = null;
        }
        $this$togglesPage_u24lambda_u24115_u24lambda_u24114.rightMargin = carUi12.dp(14);
        Unit unit = Unit.INSTANCE;
        $this$togglesPage_u24lambda_u24115.addView(textView, $this$togglesPage_u24lambda_u24115_u24lambda_u24114);
        this.togglesSearch = $this$togglesPage_u24lambda_u24115;
        setHeaderTool($this$togglesPage_u24lambda_u24115);
        List $this$map$iv2 = sections;
        Collection destination$iv$iv3 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv2, 10));
        for (Object item$iv$iv2 : $this$map$iv2) {
            String it3 = (String) item$iv$iv2;
            destination$iv$iv3.add(shortSection(it3));
        }
        List labels = CollectionsKt.plus((Collection<? extends String>) destination$iv$iv3, "Language");
        final List names = CollectionsKt.plus((Collection<? extends String>) sections, "Language");
        final Ref.ObjectRef select = new Ref.ObjectRef();
        final Ref.IntRef lastSel = new Ref.IntRef();
        CarUi carUi13 = this.ui;
        if (carUi13 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi13 = null;
        }
        Pair<View, Function1<Integer, Unit>> browserTabs = carUi13.browserTabs(labels, 1, new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit unit2;
                unit2 = SettingsActivity.togglesPage$lambda$117(lastSel, $this$togglesPage_u24lambda_u24111, select, ((Integer) obj).intValue());
                return unit2;
            }
        });
        View strip = browserTabs.component1();
        final Function1 paint = browserTabs.component2();
        $this$togglesPage_u24lambda_u24110.addView(strip, new LinearLayout.LayoutParams(-1, -2));
        final FrameLayout $this$togglesPage_u24lambda_u24118 = new FrameLayout(this);
        CarUi carUi14 = this.ui;
        if (carUi14 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi3 = null;
        } else {
            carUi3 = carUi14;
        }
        $this$togglesPage_u24lambda_u24118.setBackground(carUi3.corners(CarUi.Companion.getSECTION(), 0, 0, 14, 14));
        $this$togglesPage_u24lambda_u24110.addView($this$togglesPage_u24lambda_u24118, new LinearLayout.LayoutParams(-1, 0, 1.0f));
        final HashMap cache = new HashMap();
        select.element = new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit unit2;
                unit2 = SettingsActivity.togglesPage$lambda$124(paint, names, cache, sections, this, $this$togglesPage_u24lambda_u24118, ((Integer) obj).intValue());
                return unit2;
            }
        };
        final Runnable runSearch = new Runnable() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                SettingsActivity.togglesPage$lambda$132($this$togglesPage_u24lambda_u24111, select, lastSel, paint, a, this, $this$togglesPage_u24lambda_u24118);
            }
        };
        $this$togglesPage_u24lambda_u24111.addTextChangedListener(new TextWatcher() { // from class: com.starpilot.starview.SettingsActivity$togglesPage$4
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int st, int c, int a2) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int st, int b, int c) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable e) {
                Handler handler;
                Handler handler2;
                Editable editable = e;
                $this$togglesPage_u24lambda_u24113.setVisibility(editable == null || editable.length() == 0 ? 8 : 0);
                handler = this.handler;
                handler.removeCallbacks(runSearch);
                handler2 = this.handler;
                handler2.postDelayed(runSearch, 350L);
            }
        });
        $this$togglesPage_u24lambda_u24110.post(new Runnable() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                SettingsActivity.togglesPage$lambda$133(select);
            }
        });
        $this$togglesPage_u24lambda_u24110.setTag(new Runnable() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                SettingsActivity.togglesPage$lambda$135($this$togglesPage_u24lambda_u24118, this);
            }
        });
        return $this$togglesPage_u24lambda_u24110;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void togglesPage$lambda$109(SettingsActivity this$0, View $v) {
        if (this$0.current == $v) {
            this$0.panels.remove("toggles:all");
            this$0.open("toggles:all");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void togglesPage$lambda$113$lambda$112(EditText $search, SettingsActivity this$0, View it) {
        $search.setText("");
        Object systemService = this$0.getSystemService("input_method");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.inputmethod.InputMethodManager");
        ((InputMethodManager) systemService).hideSoftInputFromWindow($search.getWindowToken(), 0);
        $search.clearFocus();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit togglesPage$lambda$117(Ref.IntRef $lastSel, EditText $search, Ref.ObjectRef $select, int i) {
        Function1 function1;
        $lastSel.element = i;
        $search.setText("");
        if ($select.element == 0) {
            Intrinsics.throwUninitializedPropertyAccessException("select");
            function1 = null;
        } else {
            function1 = (Function1) $select.element;
        }
        function1.invoke(Integer.valueOf(i));
        return Unit.INSTANCE;
    }

    private static final void togglesPage$showView(FrameLayout holder, SettingsActivity this$0, View v) {
        if (v.getParent() != holder) {
            holder.removeAllViews();
            ViewParent parent = v.getParent();
            ViewGroup viewGroup = parent instanceof ViewGroup ? (ViewGroup) parent : null;
            if (viewGroup != null) {
                viewGroup.removeView(v);
            }
            holder.addView(v, new FrameLayout.LayoutParams(-1, -1));
        }
        if (v instanceof CatalogPanel) {
            ((CatalogPanel) v).syncAll();
        } else {
            if (!(v instanceof ComboPage)) {
                Object tag = v.getTag();
                Runnable runnable = tag instanceof Runnable ? (Runnable) tag : null;
                if (runnable != null) {
                    runnable.run();
                    return;
                }
                return;
            }
            ((ComboPage) v).refresh();
        }
        this$0.current = v;
    }

    private static final View togglesPage$inset(final SettingsActivity this$0, final View v) {
        if ((v instanceof LinearLayout) && (((LinearLayout) v).getTag() instanceof Runnable) && ((LinearLayout) v).getChildCount() == 2) {
            return v;
        }
        LinearLayout $this$togglesPage_u24inset_u24lambda_u24121 = new LinearLayout(this$0);
        $this$togglesPage_u24inset_u24lambda_u24121.setOrientation(1);
        $this$togglesPage_u24inset_u24lambda_u24121.addView(v);
        Object tag = v.getTag();
        if (tag == null) {
            tag = new Runnable() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda106
                @Override // java.lang.Runnable
                public final void run() {
                    SettingsActivity.togglesPage$inset$lambda$121$lambda$120(v, this$0);
                }
            };
        }
        $this$togglesPage_u24inset_u24lambda_u24121.setTag(tag);
        return $this$togglesPage_u24inset_u24lambda_u24121;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void togglesPage$inset$lambda$121$lambda$120(View $v, SettingsActivity this$0) {
        CatalogPanel it = $v instanceof CatalogPanel ? (CatalogPanel) $v : null;
        if (it != null) {
            it.syncAll();
            this$0.current = it;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final Unit togglesPage$lambda$124(Function1 $paint, List $names, HashMap $cache, List $sections, SettingsActivity this$0, FrameLayout $holder, int i) {
        View v;
        $paint.invoke(Integer.valueOf(i));
        String name = (String) $names.get(i);
        HashMap $this$getOrPut$iv = $cache;
        Object obj = $this$getOrPut$iv.get(name);
        if (obj == 0) {
            if (Intrinsics.areEqual(name, "Language") && !$sections.contains(name)) {
                v = togglesPage$inset(this$0, pageBox$default(this$0, this$0.languagePage(), true, false, 4, null));
                $this$getOrPut$iv.put(name, v);
            }
            v = this$0.catalogPage(name, true);
            if (v instanceof FrameLayout) {
                v = togglesPage$inset(this$0, v);
            }
            $this$getOrPut$iv.put(name, v);
        } else {
            v = obj;
        }
        togglesPage$showView($holder, this$0, v);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void togglesPage$lambda$132(EditText editText, Ref.ObjectRef objectRef, Ref.IntRef intRef, Function1 function1, GalaxyApi galaxyApi, final SettingsActivity settingsActivity, FrameLayout frameLayout) {
        Iterator<GalaxyApi.Category> it;
        String str;
        List<GalaxyApi.Param> list;
        char c;
        boolean z;
        Function1 function12;
        String lowerCase = StringsKt.trim((CharSequence) editText.getText().toString()).toString().toLowerCase(Locale.ROOT);
        String str2 = "toLowerCase(...)";
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        char c2 = 2;
        if (lowerCase.length() < 2) {
            if (objectRef.element == 0) {
                Intrinsics.throwUninitializedPropertyAccessException("select");
                function12 = null;
            } else {
                function12 = (Function1) objectRef.element;
            }
            function12.invoke(Integer.valueOf(intRef.element));
            return;
        }
        function1.invoke(-1);
        ArrayList arrayList = new ArrayList();
        Iterator<GalaxyApi.Category> it2 = galaxyApi.getCatalog().iterator();
        while (it2.hasNext()) {
            GalaxyApi.Category next = it2.next();
            List<GalaxyApi.Param> params = next.getParams();
            ArrayList arrayList2 = new ArrayList();
            char c3 = c2;
            for (Object obj : params) {
                GalaxyApi.Param param = (GalaxyApi.Param) obj;
                String[] strArr = new String[4];
                strArr[0] = param.getLabel();
                strArr[1] = param.getKey();
                String description = param.getDescription();
                if (description == null) {
                    description = "";
                }
                strArr[2] = description;
                strArr[3] = param.getUnit();
                List listOf = CollectionsKt.listOf((Object[]) strArr);
                if ((listOf instanceof Collection) && listOf.isEmpty()) {
                    it = it2;
                    str = str2;
                    list = params;
                    z = false;
                    c = 2;
                } else {
                    Iterator it3 = listOf.iterator();
                    while (true) {
                        if (!it3.hasNext()) {
                            it = it2;
                            str = str2;
                            list = params;
                            c = 2;
                            z = false;
                            break;
                        }
                        Iterator it4 = it3;
                        it = it2;
                        String lowerCase2 = ((String) it3.next()).toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(lowerCase2, str2);
                        str = str2;
                        List list2 = listOf;
                        list = params;
                        c = 2;
                        if (StringsKt.contains((CharSequence) lowerCase2, (CharSequence) lowerCase, false)) {
                            z = true;
                            break;
                        }
                        listOf = list2;
                        params = list;
                        it3 = it4;
                        it2 = it;
                        str2 = str;
                    }
                }
                if (z) {
                    arrayList2.add(obj);
                }
                c3 = c;
                params = list;
                it2 = it;
                str2 = str;
            }
            Iterator<GalaxyApi.Category> it5 = it2;
            String str3 = str2;
            ArrayList arrayList3 = arrayList2;
            if (arrayList3.isEmpty()) {
                it2 = it5;
                str2 = str3;
                c2 = 2;
            } else {
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                List<GalaxyApi.Param> params2 = next.getParams();
                LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(params2, 10)), 16));
                for (Object obj2 : params2) {
                    linkedHashMap.put(((GalaxyApi.Param) obj2).getKey(), obj2);
                }
                Iterator it6 = arrayList3.iterator();
                while (it6.hasNext()) {
                    String key = ((GalaxyApi.Param) it6.next()).getKey();
                    while (key != null && linkedHashSet.add(key)) {
                        GalaxyApi.Param param2 = (GalaxyApi.Param) linkedHashMap.get(key);
                        key = param2 != null ? param2.getParentKey() : null;
                    }
                }
                CarUi carUi = settingsActivity.ui;
                if (carUi == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ui");
                    carUi = null;
                }
                arrayList.add(carUi.header(next.getName() + " (" + arrayList3.size() + ")"));
                SettingsActivity settingsActivity2 = settingsActivity;
                List<GalaxyApi.Param> params3 = next.getParams();
                ArrayList arrayList4 = new ArrayList();
                for (Object obj3 : params3) {
                    ArrayList arrayList5 = arrayList3;
                    if (linkedHashSet.contains(((GalaxyApi.Param) obj3).getKey())) {
                        arrayList4.add(obj3);
                    }
                    arrayList3 = arrayList5;
                }
                arrayList.add(new CatalogPanel(settingsActivity2, galaxyApi, arrayList4, "Search · " + next.getName(), true, new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda88
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj4) {
                        Unit unit;
                        unit = SettingsActivity.togglesPage$lambda$132$lambda$129(settingsActivity2, (String) obj4);
                        return unit;
                    }
                }));
                it2 = it5;
                str2 = str3;
                c2 = 2;
            }
        }
        if (arrayList.isEmpty()) {
            arrayList.add(settingsActivity.offline("No settings match \"" + lowerCase + "\"."));
        }
        final ComboPage comboPage = new ComboPage(settingsActivity, arrayList);
        View view = togglesPage$inset(settingsActivity, pageBox$default(settingsActivity, comboPage, true, false, 4, null));
        view.setTag(new Runnable() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda99
            @Override // java.lang.Runnable
            public final void run() {
                SettingsActivity.togglesPage$lambda$132$lambda$131$lambda$130(SettingsActivity.ComboPage.this, settingsActivity);
            }
        });
        Unit unit = Unit.INSTANCE;
        togglesPage$showView(frameLayout, settingsActivity, view);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit togglesPage$lambda$132$lambda$129(SettingsActivity this$0, String r) {
        Intrinsics.checkNotNullParameter(r, "r");
        this$0.openRoute(r);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void togglesPage$lambda$132$lambda$131$lambda$130(ComboPage $combo, SettingsActivity this$0) {
        $combo.refresh();
        this$0.current = $combo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void togglesPage$lambda$133(Ref.ObjectRef $select) {
        Function1 function1;
        if ($select.element == 0) {
            Intrinsics.throwUninitializedPropertyAccessException("select");
            function1 = null;
        } else {
            function1 = (Function1) $select.element;
        }
        function1.invoke(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void togglesPage$lambda$135(FrameLayout $holder, SettingsActivity this$0) {
        View it = $holder.getChildAt(0);
        if (it != null) {
            togglesPage$showView($holder, this$0, it);
        }
    }

    private final String shortSection(String n) {
        if (StringsKt.startsWith(n, "Lateral", false)) {
            return "Lateral (Steering)";
        }
        if (StringsKt.startsWith(n, "Longitudinal", false)) {
            return "Longitudinal";
        }
        if (StringsKt.startsWith(n, "Vision Speed", false)) {
            return "Speed Limits";
        }
        if (StringsKt.startsWith(n, "Visual", false)) {
            return "Visual";
        }
        if (StringsKt.startsWith(n, "Sounds", false)) {
            return "Sounds";
        }
        if (StringsKt.startsWith(n, "Wheel", false)) {
            return "Wheel";
        }
        return n;
    }

    private final View languagePage() {
        String str;
        Pair<ScrollView, LinearLayout> page = page();
        ScrollView sv = page.component1();
        LinearLayout col = page.component2();
        String str2 = "en";
        List langs = CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to("en", "English"), TuplesKt.to("es", "Spanish"), TuplesKt.to("fr", "French"), TuplesKt.to("ko", "Korean"), TuplesKt.to("zh-CHS", "Chinese")});
        GalaxyApi galaxyApi = this.api;
        if (galaxyApi == null || (str = galaxyApi.string("LanguageSetting")) == null) {
            str = "";
        }
        String removePrefix = StringsKt.removePrefix(str, (CharSequence) "main_");
        if (!(removePrefix.length() == 0)) {
            str2 = removePrefix;
        }
        final String cur = str2;
        choiceCard(col, "Language", "Select language. Galaxy uses English when no language is selected.", langs, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda131
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String languagePage$lambda$137;
                languagePage$lambda$137 = SettingsActivity.languagePage$lambda$137(cur);
                return languagePage$lambda$137;
            }
        }, new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda132
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit languagePage$lambda$139;
                languagePage$lambda$139 = SettingsActivity.languagePage$lambda$139(SettingsActivity.this, (String) obj);
                return languagePage$lambda$139;
            }
        });
        return sv;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String languagePage$lambda$137(String $cur) {
        return $cur;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit languagePage$lambda$139(final SettingsActivity this$0, String code) {
        Intrinsics.checkNotNullParameter(code, "code");
        GalaxyApi galaxyApi = this$0.api;
        if (galaxyApi != null) {
            GalaxyApi.put$default(galaxyApi, "LanguageSetting", "main_" + code, null, new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda111
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit languagePage$lambda$139$lambda$138;
                    languagePage$lambda$139$lambda$138 = SettingsActivity.languagePage$lambda$139$lambda$138(this$0, (String) obj);
                    return languagePage$lambda$139$lambda$138;
                }
            }, 4, null);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit languagePage$lambda$139$lambda$138(SettingsActivity this$0, String e) {
        Toast.makeText(this$0, e == null ? "Language updated." : e, 0).show();
        return Unit.INSTANCE;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterative(DepthRegionTraversal.java:31)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visit(SwitchOverStringVisitor.java:60)
     */
    private final View gxPage(String id) {
        switch (id.hashCode()) {
            case -1785238953:
                if (id.equals("favorites")) {
                    return new GxFavoritesPage(this, this);
                }
                break;
            case -1253268720:
                if (id.equals("galaxy")) {
                    return new GxGalaxyPage(this, this);
                }
                break;
            case -1068799382:
                if (id.equals("models")) {
                    return new GxModelsPage(this, this);
                }
                break;
            case -887328209:
                if (id.equals("system")) {
                    return new GxSystemPage(this, this);
                }
                break;
            case -862552395:
                if (id.equals("tuning")) {
                    return new GxTuningPage(this, this);
                }
                break;
            case -619025980:
                if (id.equals("modellab")) {
                    return new GxModelLabPage(this, this);
                }
                break;
            case 3208415:
                if (id.equals("home")) {
                    return new GxHomePage(this, this);
                }
                break;
            case 3327407:
                if (id.equals("logs")) {
                    return new GxLogsPage(this, this);
                }
                break;
            case 110327241:
                if (id.equals("theme")) {
                    return new GxThemePage(this, this);
                }
                break;
            case 342069036:
                if (id.equals("vehicle")) {
                    return new GxVehiclePage(this, this);
                }
                break;
            case 549364206:
                if (id.equals("cameras")) {
                    return new GxCamerasPage(this, this);
                }
                break;
            case 590460812:
                if (id.equals("personalities")) {
                    return new GxPersonalitiesPage(this, this);
                }
                break;
            case 735527074:
                if (id.equals("recordings")) {
                    return new GxRecordingsPage(this, this);
                }
                break;
            case 1862666772:
                if (id.equals("navigation")) {
                    return new GxNavigationPage(this, this);
                }
                break;
            case 1968882350:
                if (id.equals("bluetooth")) {
                    return new GxBluetoothPage(this, this);
                }
                break;
        }
        return offline("Unknown page " + id);
    }

    private final View offline(String msg) {
        TextView $this$offline_u24lambda_u24140 = new TextView(this);
        $this$offline_u24lambda_u24140.setText(msg);
        $this$offline_u24lambda_u24140.setTextColor(CarUi.Companion.getDIM());
        $this$offline_u24lambda_u24140.setTextSize(20.0f);
        $this$offline_u24lambda_u24140.setGravity(17);
        CarUi carUi = this.ui;
        CarUi carUi2 = null;
        if (carUi == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi = null;
        }
        int dp = carUi.dp(40);
        CarUi carUi3 = this.ui;
        if (carUi3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi3 = null;
        }
        int dp2 = carUi3.dp(40);
        CarUi carUi4 = this.ui;
        if (carUi4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi4 = null;
        }
        int dp3 = carUi4.dp(40);
        CarUi carUi5 = this.ui;
        if (carUi5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
        } else {
            carUi2 = carUi5;
        }
        $this$offline_u24lambda_u24140.setPadding(dp, dp2, dp3, carUi2.dp(40));
        return $this$offline_u24lambda_u24140;
    }

    private final View tabletBackupPage() {
        CarUi carUi;
        CarUi carUi2;
        Pair<ScrollView, LinearLayout> page = page();
        ScrollView sv = page.component1();
        LinearLayout col = page.component2();
        CarUi carUi3 = this.ui;
        if (carUi3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi3 = null;
        }
        TextView header = carUi3.header("Backup");
        CarUi carUi4 = this.ui;
        if (carUi4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi4 = null;
        }
        col.addView(header, CarUi.lp$default(carUi4, 0, 0, 3, null));
        CarUi carUi5 = this.ui;
        if (carUi5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi5 = null;
        }
        TextView note = carUi5.note("Saves every tablet setting - the display switches, the driving-screen layout, voice, maps, the lane card and the rest - to the tablet's Download/StarView folder. That folder stays when StarView is uninstalled, so a backup brings everything back after a reinstall. The comma pairing key is never saved: after a reinstall, pair again from the comma's QR code.");
        CarUi carUi6 = this.ui;
        if (carUi6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi6 = null;
        }
        col.addView(note, CarUi.lp$default(carUi6, 0, 0, 3, null));
        long last = SettingsBackup.INSTANCE.lastAuto(this);
        CarUi carUi7 = this.ui;
        if (carUi7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi7 = null;
        }
        TextView note2 = carUi7.note("Automatic backup: after you edit the driving screen and whenever StarView goes to the background, when something changed (starview-settings-auto.json, plus one copy a day, the last 7 kept). Last one: " + (last > 0 ? new SimpleDateFormat("EEE MMM d, h:mm a", Locale.US).format(new Date(last)) : "not yet") + ".");
        CarUi carUi8 = this.ui;
        if (carUi8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi8 = null;
        }
        col.addView(note2, CarUi.lp$default(carUi8, 0, 0, 3, null));
        LinearLayout r = new LinearLayout(this);
        r.setOrientation(0);
        CarUi carUi9 = this.ui;
        if (carUi9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi9 = null;
        }
        r.addView(carUi9.button("Back up now", true, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda50
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit tabletBackupPage$lambda$142;
                tabletBackupPage$lambda$142 = SettingsActivity.tabletBackupPage$lambda$142(SettingsActivity.this);
                return tabletBackupPage$lambda$142;
            }
        }));
        CarUi carUi10 = this.ui;
        if (carUi10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi = null;
        } else {
            carUi = carUi10;
        }
        TextView button$default = CarUi.button$default(carUi, "Restore from a backup…", false, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda51
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit tabletBackupPage$lambda$144;
                tabletBackupPage$lambda$144 = SettingsActivity.tabletBackupPage$lambda$144(SettingsActivity.this);
                return tabletBackupPage$lambda$144;
            }
        }, 2, null);
        LinearLayout.LayoutParams $this$tabletBackupPage_u24lambda_u24145 = new LinearLayout.LayoutParams(-2, -2);
        CarUi carUi11 = this.ui;
        if (carUi11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi11 = null;
        }
        $this$tabletBackupPage_u24lambda_u24145.setMarginStart(carUi11.dp(10));
        Unit unit = Unit.INSTANCE;
        r.addView(button$default, $this$tabletBackupPage_u24lambda_u24145);
        LinearLayout linearLayout = r;
        CarUi carUi12 = this.ui;
        if (carUi12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi12 = null;
        }
        col.addView(linearLayout, CarUi.lp$default(carUi12, 0, 0, 3, null));
        CarUi carUi13 = this.ui;
        if (carUi13 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi13 = null;
        }
        TextView header2 = carUi13.header("Lane tracker log");
        CarUi carUi14 = this.ui;
        if (carUi14 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi14 = null;
        }
        col.addView(header2, CarUi.lp$default(carUi14, 0, 0, 3, null));
        CarUi carUi15 = this.ui;
        if (carUi15 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi15 = null;
        }
        TextView note3 = carUi15.note("Every lane the card showed, why, and your ✓ / ✗ checks with the place. Saved as lane_log.txt next to the backups, so you can send it to tune the lane tracker.");
        CarUi carUi16 = this.ui;
        if (carUi16 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi16 = null;
        }
        col.addView(note3, CarUi.lp$default(carUi16, 0, 0, 3, null));
        CarUi carUi17 = this.ui;
        if (carUi17 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi2 = null;
        } else {
            carUi2 = carUi17;
        }
        TextView button$default2 = CarUi.button$default(carUi2, "Save the lane log", false, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda52
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit tabletBackupPage$lambda$146;
                tabletBackupPage$lambda$146 = SettingsActivity.tabletBackupPage$lambda$146(SettingsActivity.this);
                return tabletBackupPage$lambda$146;
            }
        }, 2, null);
        CarUi carUi18 = this.ui;
        if (carUi18 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi18 = null;
        }
        col.addView(button$default2, CarUi.lp$default(carUi18, 0, 0, 3, null));
        return sv;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletBackupPage$lambda$142(SettingsActivity this$0) {
        String msg;
        try {
            msg = "Saved to " + SettingsBackup.INSTANCE.manual(this$0);
        } catch (Exception e) {
            msg = "Backup failed: " + e.getMessage();
        }
        new AlertDialog.Builder(this$0).setMessage(msg).setPositiveButton("OK", (DialogInterface.OnClickListener) null).show();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletBackupPage$lambda$144(final SettingsActivity this$0) {
        this$0.pickFile(new String[]{"application/json", "text/plain", "application/octet-stream"}, new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda73
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit tabletBackupPage$lambda$144$lambda$143;
                tabletBackupPage$lambda$144$lambda$143 = SettingsActivity.tabletBackupPage$lambda$144$lambda$143(this$0, (Uri) obj);
                return tabletBackupPage$lambda$144$lambda$143;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletBackupPage$lambda$144$lambda$143(SettingsActivity this$0, Uri uri) {
        if (uri != null) {
            SettingsBackup.restoreFrom$default(SettingsBackup.INSTANCE, this$0, uri, null, 4, null);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletBackupPage$lambda$146(SettingsActivity this$0) {
        String msg;
        try {
            msg = "Saved to " + SettingsBackup.INSTANCE.saveCopy(this$0, new File(this$0.getFilesDir(), "lane_log.txt"), "lane_log.txt");
        } catch (Exception e) {
            msg = "Couldn't save it: " + e.getMessage();
        }
        new AlertDialog.Builder(this$0).setMessage(msg).setPositiveButton("OK", (DialogInterface.OnClickListener) null).show();
        return Unit.INSTANCE;
    }

    private final Pair<ScrollView, LinearLayout> page() {
        LinearLayout col = new LinearLayout(this);
        col.setOrientation(1);
        CarUi carUi = this.ui;
        CarUi carUi2 = null;
        if (carUi == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi = null;
        }
        int dp = carUi.dp(10);
        CarUi carUi3 = this.ui;
        if (carUi3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi3 = null;
        }
        int dp2 = carUi3.dp(4);
        CarUi carUi4 = this.ui;
        if (carUi4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi4 = null;
        }
        int dp3 = carUi4.dp(24);
        CarUi carUi5 = this.ui;
        if (carUi5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
        } else {
            carUi2 = carUi5;
        }
        col.setPadding(dp, dp2, dp3, carUi2.dp(60));
        ScrollView $this$page_u24lambda_u24148 = new ScrollView(this);
        $this$page_u24lambda_u24148.addView(col);
        return TuplesKt.to($this$page_u24lambda_u24148, col);
    }

    /* JADX WARN: Type inference failed for: r7v5, types: [T, kotlin.jvm.functions.Function1] */
    private final void choiceCard(LinearLayout col, String title, String desc, final List<Pair<String, String>> options, Function0<String> get, final Function1<? super String, Unit> set) {
        CarUi carUi;
        CarUi carUi2;
        Function1 function1;
        LinearLayout outer = new LinearLayout(this);
        outer.setOrientation(1);
        CarUi carUi3 = this.ui;
        if (carUi3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi = null;
        } else {
            carUi = carUi3;
        }
        outer.setBackground(CarUi.rounded$default(carUi, CarUi.Companion.getCARD(), 0, null, 6, null));
        CarUi carUi4 = this.ui;
        if (carUi4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi4 = null;
        }
        int dp = carUi4.dp(20);
        CarUi carUi5 = this.ui;
        if (carUi5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi5 = null;
        }
        int dp2 = carUi5.dp(14);
        CarUi carUi6 = this.ui;
        if (carUi6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi6 = null;
        }
        int dp3 = carUi6.dp(18);
        CarUi carUi7 = this.ui;
        if (carUi7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi7 = null;
        }
        outer.setPadding(dp, dp2, dp3, carUi7.dp(14));
        CarUi carUi8 = this.ui;
        if (carUi8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi2 = null;
        } else {
            carUi2 = carUi8;
        }
        LinearLayout tr = (LinearLayout) CarUi.row$default(carUi2, title, desc, 0, null, null, 28, null).component1();
        tr.setBackground(null);
        tr.setPadding(0, 0, 0, 0);
        tr.setMinimumHeight(0);
        outer.addView(tr);
        final Ref.ObjectRef paint = new Ref.ObjectRef();
        CarUi carUi9 = this.ui;
        if (carUi9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi9 = null;
        }
        List<Pair<String, String>> $this$map$iv = options;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            Pair it = (Pair) item$iv$iv;
            destination$iv$iv.add((String) it.getSecond());
        }
        Pair<LinearLayout, Function1<Integer, Unit>> chips = carUi9.chips((List) destination$iv$iv, new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda188
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit choiceCard$lambda$151;
                choiceCard$lambda$151 = SettingsActivity.choiceCard$lambda$151(set, options, paint, ((Integer) obj).intValue());
                return choiceCard$lambda$151;
            }
        });
        LinearLayout bar = chips.component1();
        paint.element = (Function1) chips.component2();
        CarUi carUi10 = this.ui;
        if (carUi10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi10 = null;
        }
        bar.setPadding(0, carUi10.dp(10), 0, 0);
        outer.addView(bar);
        if (paint.element == 0) {
            Intrinsics.throwUninitializedPropertyAccessException("paint");
            function1 = null;
        } else {
            function1 = (Function1) paint.element;
        }
        int index$iv = 0;
        Iterator<Pair<String, String>> it2 = options.iterator();
        while (true) {
            if (it2.hasNext()) {
                Pair item$iv = it2.next();
                Pair it3 = item$iv;
                if (Intrinsics.areEqual(it3.getFirst(), get.invoke())) {
                    break;
                } else {
                    index$iv++;
                }
            } else {
                index$iv = -1;
                break;
            }
        }
        function1.invoke(Integer.valueOf(index$iv));
        LinearLayout linearLayout = outer;
        CarUi carUi11 = this.ui;
        if (carUi11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi11 = null;
        }
        col.addView(linearLayout, CarUi.lp$default(carUi11, 0, 0, 3, null));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit choiceCard$lambda$151(Function1 $set, List $options, Ref.ObjectRef $paint, int i) {
        Function1 function1;
        $set.invoke(((Pair) $options.get(i)).getFirst());
        if ($paint.element == 0) {
            Intrinsics.throwUninitializedPropertyAccessException("paint");
            function1 = null;
        } else {
            function1 = (Function1) $paint.element;
        }
        function1.invoke(Integer.valueOf(i));
        return Unit.INSTANCE;
    }

    private final void toggleCard(LinearLayout col, String title, String desc, Function0<Boolean> get, final Function1<? super Boolean, Unit> set) {
        CarUi carUi;
        CarUi carUi2 = this.ui;
        if (carUi2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi = null;
        } else {
            carUi = carUi2;
        }
        LinearLayout r = (LinearLayout) CarUi.row$default(carUi, title, desc, 0, null, null, 28, null).component1();
        CarUi carUi3 = this.ui;
        if (carUi3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi3 = null;
        }
        Switch sw = carUi3.bigSwitch();
        sw.setChecked(get.invoke().booleanValue());
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda110
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                SettingsActivity.toggleCard$lambda$153(set, compoundButton, z);
            }
        });
        r.addView(sw);
        LinearLayout linearLayout = r;
        CarUi carUi4 = this.ui;
        if (carUi4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi4 = null;
        }
        col.addView(linearLayout, CarUi.lp$default(carUi4, 0, 0, 3, null));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void toggleCard$lambda$153(Function1 $set, CompoundButton compoundButton, boolean v) {
        $set.invoke(Boolean.valueOf(v));
    }

    private final View uiGroupPage(String group) {
        Pair<ScrollView, LinearLayout> page = page();
        ScrollView sv = page.component1();
        LinearLayout col = page.component2();
        final UiSettings s = new UiSettings(this);
        CarUi carUi = this.ui;
        if (carUi == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi = null;
        }
        col.addView(carUi.note("These only change what the tablet draws. Driving behaviour and units come from the comma."));
        Iterable $this$filter$iv = UiSettings.Companion.getITEMS();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            UiSettings.Item it = (UiSettings.Item) element$iv$iv;
            if (Intrinsics.areEqual(it.getGroup(), group)) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        for (final UiSettings.Item item : (List) destination$iv$iv) {
            toggleCard(col, item.getTitle(), null, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda191
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    boolean uiGroupPage$lambda$155;
                    uiGroupPage$lambda$155 = SettingsActivity.uiGroupPage$lambda$155(s, item);
                    return Boolean.valueOf(uiGroupPage$lambda$155);
                }
            }, new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda202
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit uiGroupPage$lambda$156;
                    uiGroupPage$lambda$156 = SettingsActivity.uiGroupPage$lambda$156(s, item, ((Boolean) obj).booleanValue());
                    return uiGroupPage$lambda$156;
                }
            });
            if (Intrinsics.areEqual(item.getKey(), "road_texture_flip")) {
                stepperCard(col, "Road texture brightness", "Raise it if the road looks too dark (night doubles it automatically).", 0.3f, 3.0f, 0.1f, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda213
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        float uiGroupPage$lambda$157;
                        uiGroupPage$lambda$157 = SettingsActivity.uiGroupPage$lambda$157(s);
                        return Float.valueOf(uiGroupPage$lambda$157);
                    }
                }, new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda224
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        Unit uiGroupPage$lambda$158;
                        uiGroupPage$lambda$158 = SettingsActivity.uiGroupPage$lambda$158(s, ((Float) obj).floatValue());
                        return uiGroupPage$lambda$158;
                    }
                });
            }
        }
        return sv;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean uiGroupPage$lambda$155(UiSettings $s, UiSettings.Item $item) {
        return $s.get($item.getKey());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit uiGroupPage$lambda$156(UiSettings $s, UiSettings.Item $item, boolean v) {
        $s.set($item.getKey(), v);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final float uiGroupPage$lambda$157(UiSettings $s) {
        return $s.getFloat("road_texture_gain", 1.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit uiGroupPage$lambda$158(UiSettings $s, float v) {
        $s.setFloat("road_texture_gain", v);
        return Unit.INSTANCE;
    }

    private final void stepperCard(LinearLayout col, String title, String desc, final float min, final float max, final float step, final Function0<Float> get, final Function1<? super Float, Unit> set) {
        CarUi carUi;
        CarUi carUi2 = this.ui;
        if (carUi2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi = null;
        } else {
            carUi = carUi2;
        }
        LinearLayout r = (LinearLayout) CarUi.row$default(carUi, title, desc, 0, null, null, 28, null).component1();
        final TextView tv = new TextView(this);
        tv.setTextSize(22.0f);
        tv.setTextColor(CarUi.Companion.getTEXT());
        tv.setTypeface(Typeface.DEFAULT_BOLD);
        CarUi carUi3 = this.ui;
        if (carUi3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi3 = null;
        }
        tv.setMinWidth(carUi3.dp(70));
        tv.setGravity(17);
        CarUi carUi4 = this.ui;
        if (carUi4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi4 = null;
        }
        r.addView(carUi4.stepper("−", new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda19
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit stepperCard$lambda$160;
                stepperCard$lambda$160 = SettingsActivity.stepperCard$lambda$160(get, step, set, min, max, tv);
                return stepperCard$lambda$160;
            }
        }));
        TextView textView = tv;
        LinearLayout.LayoutParams $this$stepperCard_u24lambda_u24161 = new LinearLayout.LayoutParams(-2, -2);
        CarUi carUi5 = this.ui;
        if (carUi5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi5 = null;
        }
        $this$stepperCard_u24lambda_u24161.leftMargin = carUi5.dp(6);
        CarUi carUi6 = this.ui;
        if (carUi6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi6 = null;
        }
        $this$stepperCard_u24lambda_u24161.rightMargin = carUi6.dp(6);
        Unit unit = Unit.INSTANCE;
        r.addView(textView, $this$stepperCard_u24lambda_u24161);
        CarUi carUi7 = this.ui;
        if (carUi7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi7 = null;
        }
        r.addView(carUi7.stepper("+", new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda20
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit stepperCard$lambda$162;
                stepperCard$lambda$162 = SettingsActivity.stepperCard$lambda$162(get, step, set, min, max, tv);
                return stepperCard$lambda$162;
            }
        }));
        stepperCard$paint(tv, get);
        LinearLayout linearLayout = r;
        CarUi carUi8 = this.ui;
        if (carUi8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi8 = null;
        }
        col.addView(linearLayout, CarUi.lp$default(carUi8, 0, 0, 3, null));
    }

    private static final void stepperCard$paint(TextView tv, Function0<Float> function0) {
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String format = String.format(Locale.US, "%.1f", Arrays.copyOf(new Object[]{function0.invoke()}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        tv.setText(format);
    }

    private static final void stepperCard$write(Function1<? super Float, Unit> function1, float $min, float $max, float $step, TextView tv, Function0<Float> function0, float v) {
        function1.invoke(Float.valueOf(Math.round(RangesKt.coerceIn(v, $min, $max) / $step) * $step));
        stepperCard$paint(tv, function0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit stepperCard$lambda$160(Function0 $get, float $step, Function1 $set, float $min, float $max, TextView $tv) {
        stepperCard$write($set, $min, $max, $step, $tv, $get, ((Number) $get.invoke()).floatValue() - $step);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit stepperCard$lambda$162(Function0 $get, float $step, Function1 $set, float $min, float $max, TextView $tv) {
        stepperCard$write($set, $min, $max, $step, $tv, $get, ((Number) $get.invoke()).floatValue() + $step);
        return Unit.INSTANCE;
    }

    private final View tabletThemePage() {
        Pair<ScrollView, LinearLayout> page = page();
        ScrollView sv = page.component1();
        LinearLayout col = page.component2();
        final UiSettings s = new UiSettings(this);
        CarUi carUi = this.ui;
        if (carUi == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi = null;
        }
        col.addView(carUi.note("Which widgets are on the main screen, where they sit and how big they are: long-press the road view while driving to open the layout editor. Layouts are saved per theme (day / night)."));
        choiceCard(col, "Theme", "Auto switches at sunset and sunrise from the GPS position.", CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to("auto", "Auto"), TuplesKt.to("day", "Day"), TuplesKt.to("night", "Night")}), new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda54
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String tabletThemePage$lambda$163;
                tabletThemePage$lambda$163 = SettingsActivity.tabletThemePage$lambda$163(s);
                return tabletThemePage$lambda$163;
            }
        }, new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda56
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit tabletThemePage$lambda$164;
                tabletThemePage$lambda$164 = SettingsActivity.tabletThemePage$lambda$164(s, (String) obj);
                return tabletThemePage$lambda$164;
            }
        });
        return sv;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String tabletThemePage$lambda$163(UiSettings $s) {
        return $s.getStr("theme_mode", "auto");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletThemePage$lambda$164(UiSettings $s, String it) {
        Intrinsics.checkNotNullParameter(it, "it");
        $s.setStr("theme_mode", it);
        return Unit.INSTANCE;
    }

    private final String lanModelText(LaneModel lm) {
        if (!lm.getHasModel()) {
            return "Not installed: download lane_count_v1.lite from the chat on this tablet, then tap Import";
        }
        String format = String.format("%.1f", Arrays.copyOf(new Object[]{Double.valueOf(lm.modelFile().length() / 1000000.0d)}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return "Installed (" + format + " MB, " + new SimpleDateFormat("MMM d", Locale.US).format(new Date(lm.modelFile().lastModified())) + "): the lane card uses it on city streets (with the lane card on and the camera detector running)";
    }

    private final View tabletViewPage() {
        CarUi carUi;
        String str;
        CarUi carUi2;
        CarUi carUi3;
        CarUi carUi4;
        CarUi carUi5;
        CarUi carUi6;
        CarUi carUi7;
        CarUi carUi8;
        Pair<ScrollView, LinearLayout> page = page();
        ScrollView sv = page.component1();
        LinearLayout col = page.component2();
        final UiSettings s = new UiSettings(this);
        Iterable $this$filter$iv = UiSettings.Companion.getITEMS();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            UiSettings.Item it = (UiSettings.Item) element$iv$iv;
            if (Intrinsics.areEqual(it.getGroup(), "Camera")) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        for (final UiSettings.Item item : (List) destination$iv$iv) {
            toggleCard(col, item.getTitle(), null, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda100
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    boolean tabletViewPage$lambda$166;
                    tabletViewPage$lambda$166 = SettingsActivity.tabletViewPage$lambda$166(s, item);
                    return Boolean.valueOf(tabletViewPage$lambda$166);
                }
            }, new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda101
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit tabletViewPage$lambda$167;
                    tabletViewPage$lambda$167 = SettingsActivity.tabletViewPage$lambda$167(s, item, ((Boolean) obj).booleanValue());
                    return tabletViewPage$lambda$167;
                }
            });
        }
        LayoutSeg lay = new LayoutSeg(this);
        CarUi carUi9 = this.ui;
        if (carUi9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi = null;
        } else {
            carUi = carUi9;
        }
        if (lay.getHasModel()) {
            String format = String.format("%.1f", Arrays.copyOf(new Object[]{Double.valueOf(lay.modelFile().length() / 1000000.0d)}, 1));
            Intrinsics.checkNotNullExpressionValue(format, "format(...)");
            str = "Installed (" + format + " MB): the Scene shows sidewalks and grass";
        } else {
            str = "Not installed: the Scene shows the road only. Download layout-model.lite from the chat on this tablet, then tap Import";
        }
        Pair row$default = CarUi.row$default(carUi, "Scene sidewalk model", str, 0, null, null, 28, null);
        LinearLayout r = (LinearLayout) row$default.component1();
        final TextView value = (TextView) row$default.component2();
        CarUi carUi10 = this.ui;
        if (carUi10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi2 = null;
        } else {
            carUi2 = carUi10;
        }
        r.addView(CarUi.button$default(carUi2, "Import…", false, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda102
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit tabletViewPage$lambda$169;
                tabletViewPage$lambda$169 = SettingsActivity.tabletViewPage$lambda$169(SettingsActivity.this, value);
                return tabletViewPage$lambda$169;
            }
        }, 2, null));
        LinearLayout linearLayout = r;
        CarUi carUi11 = this.ui;
        if (carUi11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi11 = null;
        }
        col.addView(linearLayout, CarUi.lp$default(carUi11, 0, 0, 3, null));
        final LaneModel lm = new LaneModel(this);
        CarUi carUi12 = this.ui;
        if (carUi12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi3 = null;
        } else {
            carUi3 = carUi12;
        }
        Pair row$default2 = CarUi.row$default(carUi3, "Lane model", lanModelText(lm), 0, null, null, 28, null);
        LinearLayout rl = (LinearLayout) row$default2.component1();
        final TextView vl = (TextView) row$default2.component2();
        CarUi carUi13 = this.ui;
        if (carUi13 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi4 = null;
        } else {
            carUi4 = carUi13;
        }
        rl.addView(CarUi.button$default(carUi4, "Import…", false, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda103
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit tabletViewPage$lambda$173;
                tabletViewPage$lambda$173 = SettingsActivity.tabletViewPage$lambda$173(SettingsActivity.this, lm, vl);
                return tabletViewPage$lambda$173;
            }
        }, 2, null));
        LinearLayout linearLayout2 = rl;
        CarUi carUi14 = this.ui;
        if (carUi14 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi14 = null;
        }
        col.addView(linearLayout2, CarUi.lp$default(carUi14, 0, 0, 3, null));
        CarUi carUi15 = this.ui;
        if (carUi15 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi5 = null;
        } else {
            carUi5 = carUi15;
        }
        Pair row$default3 = CarUi.row$default(carUi5, "Detector model", tabletViewPage$detText(this), 0, null, null, 28, null);
        LinearLayout rd = (LinearLayout) row$default3.component1();
        final TextView vd = (TextView) row$default3.component2();
        CarUi carUi16 = this.ui;
        if (carUi16 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi6 = null;
        } else {
            carUi6 = carUi16;
        }
        rd.addView(CarUi.button$default(carUi6, "Import…", false, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda104
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit tabletViewPage$lambda$177;
                tabletViewPage$lambda$177 = SettingsActivity.tabletViewPage$lambda$177(SettingsActivity.this, vd);
                return tabletViewPage$lambda$177;
            }
        }, 2, null));
        LinearLayout linearLayout3 = rd;
        CarUi carUi17 = this.ui;
        if (carUi17 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi17 = null;
        }
        col.addView(linearLayout3, CarUi.lp$default(carUi17, 0, 0, 3, null));
        DetMasks.INSTANCE.init(this);
        CarUi carUi18 = this.ui;
        if (carUi18 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi7 = null;
        } else {
            carUi7 = carUi18;
        }
        LinearLayout rm = (LinearLayout) CarUi.row$default(carUi7, "Detector masks", "Areas the object detector ignores: your hood, the dash, the mirror mount, reflections. Drawn on the camera picture while the car is on. Now: road camera " + DetMasks.INSTANCE.get(false).size() + ", wide camera " + DetMasks.INSTANCE.get(true).size() + " area(s).", 0, null, null, 28, null).component1();
        CarUi carUi19 = this.ui;
        if (carUi19 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi8 = null;
        } else {
            carUi8 = carUi19;
        }
        rm.addView(CarUi.button$default(carUi8, "Edit…", false, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda105
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit tabletViewPage$lambda$178;
                tabletViewPage$lambda$178 = SettingsActivity.tabletViewPage$lambda$178(SettingsActivity.this);
                return tabletViewPage$lambda$178;
            }
        }, 2, null));
        LinearLayout linearLayout4 = rm;
        CarUi carUi20 = this.ui;
        if (carUi20 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi20 = null;
        }
        col.addView(linearLayout4, CarUi.lp$default(carUi20, 0, 0, 3, null));
        return sv;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean tabletViewPage$lambda$166(UiSettings $s, UiSettings.Item $item) {
        return $s.get($item.getKey());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletViewPage$lambda$167(UiSettings $s, UiSettings.Item $item, boolean v) {
        $s.set($item.getKey(), v);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletViewPage$lambda$169(SettingsActivity this$0, final TextView $value) {
        this$0.onModelPicked = new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda147
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit tabletViewPage$lambda$169$lambda$168;
                tabletViewPage$lambda$169$lambda$168 = SettingsActivity.tabletViewPage$lambda$169$lambda$168($value, (String) obj);
                return tabletViewPage$lambda$169$lambda$168;
            }
        };
        this$0.pickModel.launch(new String[]{"*/*"});
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletViewPage$lambda$169$lambda$168(TextView $value, String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        if ($value != null) {
            $value.setText(msg);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletViewPage$lambda$173(final SettingsActivity this$0, final LaneModel $lm, final TextView $vl) {
        this$0.onFilePicked = new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda184
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit tabletViewPage$lambda$173$lambda$172;
                tabletViewPage$lambda$173$lambda$172 = SettingsActivity.tabletViewPage$lambda$173$lambda$172($lm, this$0, $vl, (Uri) obj);
                return tabletViewPage$lambda$173$lambda$172;
            }
        };
        this$0.pickAny.launch(new String[]{"*/*"});
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletViewPage$lambda$173$lambda$172(final LaneModel $lm, final SettingsActivity this$0, final TextView $vl, final Uri uri) {
        if (uri != null) {
            new Thread(new Runnable() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda90
                @Override // java.lang.Runnable
                public final void run() {
                    SettingsActivity.tabletViewPage$lambda$173$lambda$172$lambda$171($lm, uri, this$0, $vl);
                }
            }).start();
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void tabletViewPage$lambda$173$lambda$172$lambda$171(final LaneModel $lm, Uri $uri, final SettingsActivity this$0, final TextView $vl) {
        final String err = $lm.importModel($uri);
        this$0.runOnUiThread(new Runnable() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda161
            @Override // java.lang.Runnable
            public final void run() {
                SettingsActivity.tabletViewPage$lambda$173$lambda$172$lambda$171$lambda$170($vl, err, this$0, $lm);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void tabletViewPage$lambda$173$lambda$172$lambda$171$lambda$170(TextView $vl, String $err, SettingsActivity this$0, LaneModel $lm) {
        if ($vl != null) {
            $vl.setText($err != null ? $err : this$0.lanModelText($lm));
        }
    }

    private static final String tabletViewPage$detText(SettingsActivity this$0) {
        return ModelDownload.INSTANCE.installed(this$0) ? "Installed (18 MB): the camera finds cars, people, lights and signs" : "Not installed: download yolox_s_384x640_fp16.lite from the chat on this tablet, then tap Import";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletViewPage$lambda$177(final SettingsActivity this$0, final TextView $vd) {
        this$0.onFilePicked = new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda74
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit tabletViewPage$lambda$177$lambda$176;
                tabletViewPage$lambda$177$lambda$176 = SettingsActivity.tabletViewPage$lambda$177$lambda$176($vd, this$0, (Uri) obj);
                return tabletViewPage$lambda$177$lambda$176;
            }
        };
        this$0.pickAny.launch(new String[]{"*/*"});
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletViewPage$lambda$177$lambda$176(final TextView $vd, final SettingsActivity this$0, final Uri uri) {
        if (uri != null) {
            if ($vd != null) {
                $vd.setText("checking…");
            }
            new Thread(new Runnable() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda180
                @Override // java.lang.Runnable
                public final void run() {
                    SettingsActivity.tabletViewPage$lambda$177$lambda$176$lambda$175(this$0, uri, $vd);
                }
            }).start();
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void tabletViewPage$lambda$177$lambda$176$lambda$175(final SettingsActivity this$0, Uri $uri, final TextView $vd) {
        final String err = ModelDownload.INSTANCE.importDetector(this$0, $uri);
        this$0.runOnUiThread(new Runnable() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda162
            @Override // java.lang.Runnable
            public final void run() {
                SettingsActivity.tabletViewPage$lambda$177$lambda$176$lambda$175$lambda$174($vd, err, this$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void tabletViewPage$lambda$177$lambda$176$lambda$175$lambda$174(TextView $vd, String $err, SettingsActivity this$0) {
        if ($vd != null) {
            $vd.setText($err != null ? $err : tabletViewPage$detText(this$0));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletViewPage$lambda$178(SettingsActivity this$0) {
        this$0.startActivity(new Intent(this$0, (Class<?>) MainActivity.class).putExtra("edit_masks", true).addFlags(603979776));
        this$0.finish();
        return Unit.INSTANCE;
    }

    private final View tabletMapsPage() {
        MapdMaps.INSTANCE.init(this);
        Pair<ScrollView, LinearLayout> page = page();
        ScrollView sv = page.component1();
        final LinearLayout col = page.component2();
        final SimpleDateFormat date = new SimpleDateFormat("MMM d", Locale.US);
        MapdMaps.INSTANCE.setOnChange(new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda122
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit tabletMapsPage$lambda$189;
                tabletMapsPage$lambda$189 = SettingsActivity.tabletMapsPage$lambda$189(SettingsActivity.this, col, date);
                return tabletMapsPage$lambda$189;
            }
        });
        tabletMapsPage$refresh(col, this, date);
        return sv;
    }

    private static final String tabletMapsPage$mb(long b) {
        if (b < 1000000000) {
            return (b / 1000000) + " MB";
        }
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String format = String.format(Locale.US, "%.1f GB", Arrays.copyOf(new Object[]{Double.valueOf(b / 1.0E9d)}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format;
    }

    private static final void tabletMapsPage$refresh(LinearLayout col, SettingsActivity this$0, SimpleDateFormat date) {
        CarUi carUi;
        String busy;
        List mine;
        Iterator it;
        String desc;
        CarUi carUi2;
        CarUi carUi3;
        CarUi carUi4;
        CarUi carUi5;
        CarUi carUi6;
        CarUi carUi7;
        final SettingsActivity settingsActivity = this$0;
        col.removeAllViews();
        CarUi carUi8 = settingsActivity.ui;
        if (carUi8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi8 = null;
        }
        TextView note = carUi8.note("Street maps for the Scene, downloaded to this tablet: the same OpenStreetMap files StarPilot's maps use (map-data.pfeifer.dev, rebuilt every few days), so the comma does nothing for it. Pick the states you drive in. Where nothing is downloaded, streets are fetched online as you drive.");
        CarUi carUi9 = settingsActivity.ui;
        if (carUi9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi9 = null;
        }
        col.addView(note, CarUi.lp$default(carUi9, 0, 0, 3, null));
        String busy2 = MapdMaps.INSTANCE.getBusy();
        if (busy2.length() > 0) {
            CarUi carUi10 = settingsActivity.ui;
            if (carUi10 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi6 = null;
            } else {
                carUi6 = carUi10;
            }
            LinearLayout r = (LinearLayout) CarUi.row$default(carUi6, "Downloading", busy2, 0, null, null, 28, null).component1();
            CarUi carUi11 = settingsActivity.ui;
            if (carUi11 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi7 = null;
            } else {
                carUi7 = carUi11;
            }
            r.addView(CarUi.button$default(carUi7, "Cancel", false, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda200
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit tabletMapsPage$refresh$lambda$179;
                    tabletMapsPage$refresh$lambda$179 = SettingsActivity.tabletMapsPage$refresh$lambda$179();
                    return tabletMapsPage$refresh$lambda$179;
                }
            }, 2, null));
            LinearLayout linearLayout = r;
            CarUi carUi12 = settingsActivity.ui;
            if (carUi12 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi12 = null;
            }
            col.addView(linearLayout, CarUi.lp$default(carUi12, 0, 0, 3, null));
        }
        if (MapdMaps.INSTANCE.getLastError().length() > 0) {
            CarUi carUi13 = settingsActivity.ui;
            if (carUi13 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi13 = null;
            }
            TextView note2 = carUi13.note("Last problem: " + MapdMaps.INSTANCE.getLastError());
            CarUi carUi14 = settingsActivity.ui;
            if (carUi14 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi14 = null;
            }
            col.addView(note2, CarUi.lp$default(carUi14, 0, 0, 3, null));
        }
        Set sel = MapdMaps.INSTANCE.selected();
        Iterable $this$filter$iv = MapdMaps.INSTANCE.getStates();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            MapdMaps.State it2 = (MapdMaps.State) element$iv$iv;
            if (sel.contains(it2.getCode())) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        final List<MapdMaps.State> mine2 = (List) destination$iv$iv;
        long free = this$0.getFilesDir().getUsableSpace();
        CarUi carUi15 = settingsActivity.ui;
        if (carUi15 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi = null;
        } else {
            carUi = carUi15;
        }
        long j = 0;
        for (MapdMaps.State it3 : mine2) {
            j += MapdMaps.INSTANCE.stateBytes(it3);
        }
        LinearLayout rs = (LinearLayout) CarUi.row$default(carUi, "On this tablet", tabletMapsPage$mb(j) + " of maps, " + tabletMapsPage$mb(free) + " free", 0, null, null, 28, null).component1();
        if (!mine2.isEmpty()) {
            CarUi carUi16 = settingsActivity.ui;
            if (carUi16 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi5 = null;
            } else {
                carUi5 = carUi16;
            }
            rs.addView(CarUi.button$default(carUi5, "Update all", false, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda201
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit tabletMapsPage$refresh$lambda$182;
                    tabletMapsPage$refresh$lambda$182 = SettingsActivity.tabletMapsPage$refresh$lambda$182(mine2);
                    return tabletMapsPage$refresh$lambda$182;
                }
            }, 2, null));
        }
        LinearLayout linearLayout2 = rs;
        CarUi carUi17 = settingsActivity.ui;
        if (carUi17 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi17 = null;
        }
        col.addView(linearLayout2, carUi17.lp(0, 14));
        Pair[] pairArr = new Pair[2];
        pairArr[0] = TuplesKt.to("Your states", mine2);
        Iterable $this$filter$iv2 = MapdMaps.INSTANCE.getStates();
        Collection destination$iv$iv2 = new ArrayList();
        for (Object element$iv$iv2 : $this$filter$iv2) {
            MapdMaps.State it4 = (MapdMaps.State) element$iv$iv2;
            if (!sel.contains(it4.getCode())) {
                destination$iv$iv2.add(element$iv$iv2);
            }
        }
        pairArr[1] = TuplesKt.to("All states", (List) destination$iv$iv2);
        Iterator it5 = CollectionsKt.listOf((Object[]) pairArr).iterator();
        while (it5.hasNext()) {
            Pair pair = (Pair) it5.next();
            String title = (String) pair.component1();
            List<MapdMaps.State> list = (List) pair.component2();
            if (list.isEmpty()) {
                settingsActivity = this$0;
                free = free;
            } else {
                CarUi carUi18 = settingsActivity.ui;
                if (carUi18 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ui");
                    carUi18 = null;
                }
                col.addView(carUi18.header(title));
                for (final MapdMaps.State st : list) {
                    Pair<Integer, Long> stateStatus = MapdMaps.INSTANCE.stateStatus(st);
                    int status = stateStatus.component1().intValue();
                    long free2 = free;
                    long t = stateStatus.component2().longValue();
                    int areas = MapdMaps.INSTANCE.groupsOf(st).size();
                    switch (status) {
                        case 1:
                            busy = busy2;
                            mine = mine2;
                            it = it5;
                            desc = "Partly downloaded - " + tabletMapsPage$mb(MapdMaps.INSTANCE.stateBytes(st)) + " (tap Download to finish)";
                            break;
                        case 2:
                            busy = busy2;
                            mine = mine2;
                            it = it5;
                            desc = "Downloaded " + date.format(new Date(t)) + " - " + tabletMapsPage$mb(MapdMaps.INSTANCE.stateBytes(st));
                            break;
                        default:
                            busy = busy2;
                            mine = mine2;
                            it = it5;
                            desc = areas + " map area" + (areas == 1 ? "" : "s") + " to download";
                            break;
                    }
                    CarUi carUi19 = settingsActivity.ui;
                    if (carUi19 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("ui");
                        carUi2 = null;
                    } else {
                        carUi2 = carUi19;
                    }
                    LinearLayout r2 = (LinearLayout) CarUi.row$default(carUi2, st.getName(), desc, 0, null, null, 28, null).component1();
                    if (status == 2) {
                        CarUi carUi20 = settingsActivity.ui;
                        if (carUi20 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("ui");
                            carUi4 = null;
                        } else {
                            carUi4 = carUi20;
                        }
                        r2.addView(CarUi.button$default(carUi4, "Update", false, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda203
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                Unit tabletMapsPage$refresh$lambda$184;
                                tabletMapsPage$refresh$lambda$184 = SettingsActivity.tabletMapsPage$refresh$lambda$184(it4);
                                return tabletMapsPage$refresh$lambda$184;
                            }
                        }, 2, null));
                    } else {
                        CarUi carUi21 = settingsActivity.ui;
                        if (carUi21 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("ui");
                            carUi21 = null;
                        }
                        r2.addView(carUi21.button("Download", !sel.contains(st.getCode()), new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda204
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                Unit tabletMapsPage$refresh$lambda$185;
                                tabletMapsPage$refresh$lambda$185 = SettingsActivity.tabletMapsPage$refresh$lambda$185(it4);
                                return tabletMapsPage$refresh$lambda$185;
                            }
                        }));
                    }
                    if (sel.contains(st.getCode()) || status > 0) {
                        View view = new View(settingsActivity);
                        CarUi carUi22 = settingsActivity.ui;
                        if (carUi22 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("ui");
                            carUi22 = null;
                        }
                        r2.addView(view, new LinearLayout.LayoutParams(carUi22.dp(8), 1));
                        CarUi carUi23 = settingsActivity.ui;
                        if (carUi23 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("ui");
                            carUi3 = null;
                        } else {
                            carUi3 = carUi23;
                        }
                        r2.addView(CarUi.button$default(carUi3, "Delete", false, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda205
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                Unit tabletMapsPage$refresh$lambda$187;
                                tabletMapsPage$refresh$lambda$187 = SettingsActivity.tabletMapsPage$refresh$lambda$187(this$0, st);
                                return tabletMapsPage$refresh$lambda$187;
                            }
                        }, 2, null));
                    }
                    LinearLayout linearLayout3 = r2;
                    CarUi carUi24 = settingsActivity.ui;
                    if (carUi24 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("ui");
                        carUi24 = null;
                    }
                    col.addView(linearLayout3, CarUi.lp$default(carUi24, 0, 0, 3, null));
                    settingsActivity = this$0;
                    busy2 = busy;
                    mine2 = mine;
                    free = free2;
                    it5 = it;
                }
                settingsActivity = this$0;
                free = free;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletMapsPage$refresh$lambda$179() {
        MapdMaps.INSTANCE.cancelAll();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletMapsPage$refresh$lambda$182(List $mine) {
        Iterator it = $mine.iterator();
        while (it.hasNext()) {
            MapdMaps.State st = (MapdMaps.State) it.next();
            MapdMaps.INSTANCE.download(st, true);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletMapsPage$refresh$lambda$184(MapdMaps.State $st) {
        MapdMaps.INSTANCE.download($st, true);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletMapsPage$refresh$lambda$185(MapdMaps.State $st) {
        MapdMaps.INSTANCE.download($st, false);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletMapsPage$refresh$lambda$187(SettingsActivity this$0, final MapdMaps.State $st) {
        new AlertDialog.Builder(this$0).setMessage("Delete the " + $st.getName() + " street maps from this tablet?").setPositiveButton("Delete", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda84
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                SettingsActivity.tabletMapsPage$refresh$lambda$187$lambda$186($st, dialogInterface, i);
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void tabletMapsPage$refresh$lambda$187$lambda$186(MapdMaps.State $st, DialogInterface dialogInterface, int i) {
        MapdMaps.INSTANCE.delete($st);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletMapsPage$lambda$189(final SettingsActivity this$0, final LinearLayout $col, final SimpleDateFormat $date) {
        this$0.runOnUiThread(new Runnable() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda206
            @Override // java.lang.Runnable
            public final void run() {
                SettingsActivity.tabletMapsPage$lambda$189$lambda$188(this$0, $col, $date);
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void tabletMapsPage$lambda$189$lambda$188(SettingsActivity this$0, LinearLayout $col, SimpleDateFormat $date) {
        if (this$0.isFinishing()) {
            return;
        }
        tabletMapsPage$refresh($col, this$0, $date);
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x007a, code lost:
    
        if (r7 == null) goto L27;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final View tabletConnectionPage() {
        CarUi carUi;
        CarUi carUi2;
        String str;
        CarUi carUi3;
        CarUi carUi4;
        CarUi carUi5;
        String pStr$default;
        Pair<ScrollView, LinearLayout> page = page();
        ScrollView sv = page.component1();
        LinearLayout col = page.component2();
        Telemetry t = Link.INSTANCE.getTelemetry();
        CarUi carUi6 = this.ui;
        if (carUi6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi = null;
        } else {
            carUi = carUi6;
        }
        String str2 = this.host;
        if (str2 == null) {
            str2 = "not connected";
        }
        LinearLayout r1 = (LinearLayout) CarUi.row$default(carUi, "Comma", str2, 0, null, null, 28, null).component1();
        LinearLayout linearLayout = r1;
        CarUi carUi7 = this.ui;
        if (carUi7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi7 = null;
        }
        col.addView(linearLayout, CarUi.lp$default(carUi7, 0, 0, 3, null));
        CarUi carUi8 = this.ui;
        if (carUi8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi2 = null;
        } else {
            carUi2 = carUi8;
        }
        if (t != null && (pStr$default = Telemetry.pStr$default(t, "GalaxyDeviceName", null, 2, null)) != null) {
            String str3 = pStr$default;
            if (str3.length() == 0) {
                str3 = null;
            }
            str = str3;
        }
        str = "-";
        if (t != null) {
            str = t.pStr("DongleId", "-");
        }
        LinearLayout r2 = (LinearLayout) CarUi.row$default(carUi2, "Device", str, 0, null, null, 28, null).component1();
        LinearLayout linearLayout2 = r2;
        CarUi carUi9 = this.ui;
        if (carUi9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi9 = null;
        }
        col.addView(linearLayout2, CarUi.lp$default(carUi9, 0, 0, 3, null));
        CarUi carUi10 = this.ui;
        if (carUi10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi3 = null;
        } else {
            carUi3 = carUi10;
        }
        LinearLayout r3 = (LinearLayout) CarUi.row$default(carUi3, "Link", "USB tether (usb0), Wi-Fi as fallback; moves back to USB by itself when the cable returns. The tablet finds the comma by broadcast on port 8091.", 0, null, null, 28, null).component1();
        LinearLayout linearLayout3 = r3;
        CarUi carUi11 = this.ui;
        if (carUi11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi11 = null;
        }
        col.addView(linearLayout3, CarUi.lp$default(carUi11, 0, 0, 3, null));
        CarUi carUi12 = this.ui;
        if (carUi12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi4 = null;
        } else {
            carUi4 = carUi12;
        }
        LinearLayout r4 = (LinearLayout) CarUi.row$default(carUi4, "Reconnect", "Drop the data link and search for the comma again.", 0, null, null, 28, null).component1();
        CarUi carUi13 = this.ui;
        if (carUi13 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi5 = null;
        } else {
            carUi5 = carUi13;
        }
        r4.addView(CarUi.button$default(carUi5, "Reconnect", false, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda94
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit tabletConnectionPage$lambda$191;
                tabletConnectionPage$lambda$191 = SettingsActivity.tabletConnectionPage$lambda$191(SettingsActivity.this);
                return tabletConnectionPage$lambda$191;
            }
        }, 2, null));
        LinearLayout linearLayout4 = r4;
        CarUi carUi14 = this.ui;
        if (carUi14 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi14 = null;
        }
        col.addView(linearLayout4, CarUi.lp$default(carUi14, 0, 0, 3, null));
        return sv;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletConnectionPage$lambda$191(SettingsActivity this$0) {
        DataClient data = Link.INSTANCE.getData();
        if (data != null) {
            data.close();
        }
        this$0.finish();
        return Unit.INSTANCE;
    }

    private final View tabletRtkPage() {
        CarUi carUi;
        CarUi carUi2;
        CarUi carUi3;
        CarUi carUi4;
        CarUi carUi5;
        CarUi carUi6;
        Pair<ScrollView, LinearLayout> page = page();
        final ScrollView sv = page.component1();
        LinearLayout col = page.component2();
        CarUi carUi7 = this.ui;
        if (carUi7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi7 = null;
        }
        TextView note = carUi7.note("A u-blox RTK receiver (SparkFun ZED-F9R) plugged into the comma's USB hub. Its position shows on the GPS card; with corrections from an NTRIP caster it gets to about an inch (\"RTK Fixed\"). The comma has no internet in the car, so this tablet fetches the corrections over its own connection and passes them to the receiver. NYSNet (New York State) is free: sign up at cors.dot.ny.gov, the third email has the caster, port and mountpoint. The password stays on this tablet (never in a settings backup).");
        CarUi carUi8 = this.ui;
        if (carUi8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi8 = null;
        }
        col.addView(note, CarUi.lp$default(carUi8, 0, 0, 3, null));
        CarUi carUi9 = this.ui;
        if (carUi9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi9 = null;
        }
        TextView header = carUi9.header("Now");
        CarUi carUi10 = this.ui;
        if (carUi10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi10 = null;
        }
        col.addView(header, CarUi.lp$default(carUi10, 0, 0, 3, null));
        CarUi carUi11 = this.ui;
        if (carUi11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi = null;
        } else {
            carUi = carUi11;
        }
        Pair row$default = CarUi.row$default(carUi, "Receiver", "…", 0, null, null, 28, null);
        LinearLayout rs = (LinearLayout) row$default.component1();
        final TextView rsd = (TextView) row$default.component2();
        LinearLayout linearLayout = rs;
        CarUi carUi12 = this.ui;
        if (carUi12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi12 = null;
        }
        col.addView(linearLayout, CarUi.lp$default(carUi12, 0, 0, 3, null));
        CarUi carUi13 = this.ui;
        if (carUi13 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi2 = null;
        } else {
            carUi2 = carUi13;
        }
        Pair row$default2 = CarUi.row$default(carUi2, "Corrections", "…", 0, null, null, 28, null);
        LinearLayout rn = (LinearLayout) row$default2.component1();
        final TextView rnd = (TextView) row$default2.component2();
        LinearLayout linearLayout2 = rn;
        CarUi carUi14 = this.ui;
        if (carUi14 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi14 = null;
        }
        col.addView(linearLayout2, CarUi.lp$default(carUi14, 0, 0, 3, null));
        CarUi carUi15 = this.ui;
        if (carUi15 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi3 = null;
        } else {
            carUi3 = carUi15;
        }
        Pair row$default3 = CarUi.row$default(carUi3, "Connection log", "…", 0, null, null, 28, null);
        LinearLayout rl = (LinearLayout) row$default3.component1();
        final TextView rld = (TextView) row$default3.component2();
        LinearLayout linearLayout3 = rl;
        CarUi carUi16 = this.ui;
        if (carUi16 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi16 = null;
        }
        col.addView(linearLayout3, CarUi.lp$default(carUi16, 0, 0, 3, null));
        Telemetry telemetry = Link.INSTANCE.getTelemetry();
        final boolean metric = telemetry != null && telemetry.isMetric();
        final Ref.BooleanRef seen = new Ref.BooleanRef();
        livePoll(sv, new Runnable() { // from class: com.starpilot.starview.SettingsActivity$tabletRtkPage$poll$1
            @Override // java.lang.Runnable
            public void run() {
                Handler handler;
                String str;
                String str2;
                if (sv.isAttachedToWindow()) {
                    seen.element = true;
                } else if (seen.element) {
                    return;
                }
                RtkStatus k = Ntrip.INSTANCE.getRtk();
                TextView textView = rsd;
                String str3 = "";
                if (textView != null) {
                    if (k != null) {
                        if (k.getFresh()) {
                            String words = k.getWords();
                            String accuracy = RtkStatus.Companion.accuracy(k.getHAccM(), metric);
                            int used = k.getUsed();
                            String str4 = k.getFusion().length() > 0 ? "   sensors " + k.getFusion() + ", mount " + k.getMount() : "";
                            if (k.getHasFix()) {
                                str = String.format(Locale.US, "\n%.7f, %.7f", Arrays.copyOf(new Object[]{Double.valueOf(k.getLat()), Double.valueOf(k.getLon())}, 2));
                                Intrinsics.checkNotNullExpressionValue(str, "format(...)");
                            } else {
                                str = "";
                            }
                            str2 = words + "  ± " + accuracy + "   " + used + " satellites" + str4 + str;
                        } else {
                            str2 = k.getWords();
                        }
                    }
                    textView.setText(str2);
                }
                TextView textView2 = rnd;
                if (textView2 != null) {
                    String str5 = Ntrip.INSTANCE.getSource().length() > 0 ? Ntrip.INSTANCE.getSource() + "\n" : "";
                    String state = Ntrip.INSTANCE.getState();
                    String str6 = (k != null ? k.getCorrectionAgeS() : null) != null ? "   (receiver using corrections ≤ " + k.getCorrectionAgeS() + " s old)" : "";
                    if ((k != null ? k.getRtcmAgeS() : null) != null) {
                        String format = String.format("%.0f", Arrays.copyOf(new Object[]{k.getRtcmAgeS()}, 1));
                        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
                        str3 = "\ncomma: last corrections " + format + " s ago, " + NtripCore.INSTANCE.size(k.getRtcmBytes()) + " in all";
                    } else if (k != null) {
                        str3 = "\ncomma: no corrections since it started";
                    }
                    textView2.setText(str5 + state + str6 + str3);
                }
                TextView textView3 = rld;
                if (textView3 != null) {
                    String joinToString$default = CollectionsKt.joinToString(Ntrip.INSTANCE.recent(25), "\n", "", "", -1, "...", null);
                    if (joinToString$default.length() == 0) {
                        joinToString$default = "nothing yet";
                    }
                    textView3.setText(joinToString$default);
                }
                handler = this.handler;
                handler.postDelayed(this, 1000L);
            }
        });
        rtkFusionSection(sv, col);
        CarUi carUi17 = this.ui;
        if (carUi17 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi17 = null;
        }
        TextView header2 = carUi17.header("NTRIP caster");
        CarUi carUi18 = this.ui;
        if (carUi18 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi18 = null;
        }
        col.addView(header2, CarUi.lp$default(carUi18, 0, 0, 3, null));
        toggleCard(col, "Use RTK corrections", "Connect to the caster while the comma and the receiver are connected.", new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda134
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean tabletRtkPage$lambda$192;
                tabletRtkPage$lambda$192 = SettingsActivity.tabletRtkPage$lambda$192(SettingsActivity.this);
                return Boolean.valueOf(tabletRtkPage$lambda$192);
            }
        }, new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda137
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit tabletRtkPage$lambda$193;
                tabletRtkPage$lambda$193 = SettingsActivity.tabletRtkPage$lambda$193(SettingsActivity.this, ((Boolean) obj).booleanValue());
                return tabletRtkPage$lambda$193;
            }
        });
        tabletRtkPage$field$default(this, col, "Caster", new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda138
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String tabletRtkPage$lambda$199;
                tabletRtkPage$lambda$199 = SettingsActivity.tabletRtkPage$lambda$199(SettingsActivity.this);
                return tabletRtkPage$lambda$199;
            }
        }, "e.g. the address in NYSNet's activation email", false, false, new Function2() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda139
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Ntrip.Config tabletRtkPage$lambda$200;
                tabletRtkPage$lambda$200 = SettingsActivity.tabletRtkPage$lambda$200((Ntrip.Config) obj, (String) obj2);
                return tabletRtkPage$lambda$200;
            }
        }, 96, null);
        tabletRtkPage$field$default(this, col, "Port", new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda140
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String tabletRtkPage$lambda$201;
                tabletRtkPage$lambda$201 = SettingsActivity.tabletRtkPage$lambda$201(SettingsActivity.this);
                return tabletRtkPage$lambda$201;
            }
        }, "2101", false, true, new Function2() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda141
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Ntrip.Config tabletRtkPage$lambda$202;
                tabletRtkPage$lambda$202 = SettingsActivity.tabletRtkPage$lambda$202((Ntrip.Config) obj, (String) obj2);
                return tabletRtkPage$lambda$202;
            }
        }, 32, null);
        tabletRtkPage$field$default(this, col, "Username", new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda142
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String tabletRtkPage$lambda$203;
                tabletRtkPage$lambda$203 = SettingsActivity.tabletRtkPage$lambda$203(SettingsActivity.this);
                return tabletRtkPage$lambda$203;
            }
        }, "your NTRIP username", false, false, new Function2() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda143
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Ntrip.Config tabletRtkPage$lambda$204;
                tabletRtkPage$lambda$204 = SettingsActivity.tabletRtkPage$lambda$204((Ntrip.Config) obj, (String) obj2);
                return tabletRtkPage$lambda$204;
            }
        }, 96, null);
        tabletRtkPage$field$default(this, col, "Password", new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda145
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String tabletRtkPage$lambda$205;
                tabletRtkPage$lambda$205 = SettingsActivity.tabletRtkPage$lambda$205(SettingsActivity.this);
                return tabletRtkPage$lambda$205;
            }
        }, "your NTRIP password", true, false, new Function2() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda146
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Ntrip.Config tabletRtkPage$lambda$206;
                tabletRtkPage$lambda$206 = SettingsActivity.tabletRtkPage$lambda$206((Ntrip.Config) obj, (String) obj2);
                return tabletRtkPage$lambda$206;
            }
        }, 64, null);
        CarUi carUi19 = this.ui;
        if (carUi19 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi4 = null;
        } else {
            carUi4 = carUi19;
        }
        String mount = tabletRtkPage$cfg(this).getMount();
        if (mount.length() == 0) {
            mount = "not set";
        }
        Pair row$default4 = CarUi.row$default(carUi4, "Mountpoint", mount, 0, null, null, 28, null);
        LinearLayout rm = (LinearLayout) row$default4.component1();
        final TextView rmd = (TextView) row$default4.component2();
        CarUi carUi20 = this.ui;
        if (carUi20 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi5 = null;
        } else {
            carUi5 = carUi20;
        }
        rm.addView(CarUi.button$default(carUi5, "Pick", false, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda135
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit tabletRtkPage$lambda$212;
                tabletRtkPage$lambda$212 = SettingsActivity.tabletRtkPage$lambda$212(SettingsActivity.this, rmd);
                return tabletRtkPage$lambda$212;
            }
        }, 2, null));
        CarUi carUi21 = this.ui;
        if (carUi21 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi6 = null;
        } else {
            carUi6 = carUi21;
        }
        rm.addView(CarUi.button$default(carUi6, "Type", false, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda136
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit tabletRtkPage$lambda$216;
                tabletRtkPage$lambda$216 = SettingsActivity.tabletRtkPage$lambda$216(SettingsActivity.this, rmd);
                return tabletRtkPage$lambda$216;
            }
        }, 2, null));
        LinearLayout linearLayout4 = rm;
        CarUi carUi22 = this.ui;
        if (carUi22 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi22 = null;
        }
        col.addView(linearLayout4, CarUi.lp$default(carUi22, 0, 0, 3, null));
        CarUi carUi23 = this.ui;
        if (carUi23 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi23 = null;
        }
        TextView note2 = carUi23.note("Pick: lists the caster's mountpoints, nearest first once the receiver has a fix. A network mountpoint (one that builds a station for your position) works anywhere in the network; a single station's works best within ~20 miles of it. Your position goes to the caster every 10 s, as NTRIP requires.");
        CarUi carUi24 = this.ui;
        if (carUi24 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi24 = null;
        }
        col.addView(note2, CarUi.lp$default(carUi24, 0, 0, 3, null));
        backupCasterSection(col);
        return sv;
    }

    private static final Ntrip.Config tabletRtkPage$cfg(SettingsActivity this$0) {
        return Ntrip.INSTANCE.config(this$0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean tabletRtkPage$lambda$192(SettingsActivity this$0) {
        return tabletRtkPage$cfg(this$0).getOn();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletRtkPage$lambda$193(SettingsActivity this$0, boolean v) {
        Ntrip.Config c = tabletRtkPage$cfg(this$0);
        Ntrip.INSTANCE.save(this$0, new Ntrip.Config(v, c.getHost(), c.getPort(), c.getMount(), c.getUser(), c.getPass()));
        return Unit.INSTANCE;
    }

    static /* synthetic */ void tabletRtkPage$field$default(SettingsActivity settingsActivity, LinearLayout linearLayout, String str, Function0 function0, String str2, boolean z, boolean z2, Function2 function2, int i, Object obj) {
        tabletRtkPage$field(settingsActivity, linearLayout, str, function0, str2, (i & 32) != 0 ? false : z, (i & 64) != 0 ? false : z2, function2);
    }

    private static final void tabletRtkPage$field(final SettingsActivity this$0, LinearLayout col, final String title, final Function0<String> function0, final String hint, final boolean password, final boolean numeric, final Function2<? super Ntrip.Config, ? super String, Ntrip.Config> function2) {
        CarUi carUi;
        CarUi carUi2;
        CarUi carUi3 = this$0.ui;
        if (carUi3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi = null;
        } else {
            carUi = carUi3;
        }
        String invoke = function0.invoke();
        if (invoke.length() == 0) {
            invoke = "not set";
        }
        Pair row$default = CarUi.row$default(carUi, title, invoke, 0, null, null, 28, null);
        LinearLayout r = (LinearLayout) row$default.component1();
        final TextView d = (TextView) row$default.component2();
        CarUi carUi4 = this$0.ui;
        if (carUi4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi2 = null;
        } else {
            carUi2 = carUi4;
        }
        r.addView(CarUi.button$default(carUi2, "Change", false, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda244
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit tabletRtkPage$field$lambda$198;
                tabletRtkPage$field$lambda$198 = SettingsActivity.tabletRtkPage$field$lambda$198(this$0, title, password, function0, hint, numeric, function2, d);
                return tabletRtkPage$field$lambda$198;
            }
        }, 2, null));
        LinearLayout linearLayout = r;
        CarUi carUi5 = this$0.ui;
        if (carUi5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi5 = null;
        }
        col.addView(linearLayout, CarUi.lp$default(carUi5, 0, 0, 3, null));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletRtkPage$field$lambda$198(final SettingsActivity this$0, String $title, boolean $password, final Function0 $value, String $hint, boolean $numeric, final Function2 $set, final TextView $d) {
        int i;
        final EditText $this$tabletRtkPage_u24field_u24lambda_u24198_u24lambda_u24195 = new EditText(this$0);
        $this$tabletRtkPage_u24field_u24lambda_u24198_u24lambda_u24195.setText($password ? "" : (String) $value.invoke());
        $this$tabletRtkPage_u24field_u24lambda_u24198_u24lambda_u24195.setHint($hint);
        if ($numeric) {
            i = 2;
        } else {
            i = $password ? 129 : 524289;
        }
        $this$tabletRtkPage_u24field_u24lambda_u24198_u24lambda_u24195.setInputType(i);
        CarUi carUi = this$0.ui;
        if (carUi == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi = null;
        }
        int dp = carUi.dp(20);
        CarUi carUi2 = this$0.ui;
        if (carUi2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi2 = null;
        }
        int dp2 = carUi2.dp(16);
        CarUi carUi3 = this$0.ui;
        if (carUi3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi3 = null;
        }
        int dp3 = carUi3.dp(20);
        CarUi carUi4 = this$0.ui;
        if (carUi4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi4 = null;
        }
        $this$tabletRtkPage_u24field_u24lambda_u24198_u24lambda_u24195.setPadding(dp, dp2, dp3, carUi4.dp(16));
        new AlertDialog.Builder(this$0).setTitle($title).setView($this$tabletRtkPage_u24field_u24lambda_u24198_u24lambda_u24195).setPositiveButton("Save", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda85
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                SettingsActivity.tabletRtkPage$field$lambda$198$lambda$197(this$0, $set, $this$tabletRtkPage_u24field_u24lambda_u24198_u24lambda_u24195, $d, $value, dialogInterface, i2);
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void tabletRtkPage$field$lambda$198$lambda$197(SettingsActivity this$0, Function2 $set, EditText $e, TextView $d, Function0 $value, DialogInterface dialogInterface, int i) {
        Ntrip.INSTANCE.save(this$0, (Ntrip.Config) $set.invoke(tabletRtkPage$cfg(this$0), $e.getText().toString()));
        if ($d != null) {
            CharSequence charSequence = (CharSequence) $value.invoke();
            if (charSequence.length() == 0) {
                charSequence = "not set";
            }
            $d.setText(charSequence);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String tabletRtkPage$lambda$199(SettingsActivity this$0) {
        return tabletRtkPage$cfg(this$0).getHost();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Ntrip.Config tabletRtkPage$lambda$200(Ntrip.Config c, String v) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(v, "v");
        return new Ntrip.Config(c.getOn(), v, c.getPort(), c.getMount(), c.getUser(), c.getPass());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String tabletRtkPage$lambda$201(SettingsActivity this$0) {
        return String.valueOf(tabletRtkPage$cfg(this$0).getPort());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Ntrip.Config tabletRtkPage$lambda$202(Ntrip.Config c, String v) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(v, "v");
        boolean on = c.getOn();
        String host = c.getHost();
        Integer intOrNull = StringsKt.toIntOrNull(v);
        return new Ntrip.Config(on, host, intOrNull != null ? intOrNull.intValue() : c.getPort(), c.getMount(), c.getUser(), c.getPass());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String tabletRtkPage$lambda$203(SettingsActivity this$0) {
        return tabletRtkPage$cfg(this$0).getUser();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Ntrip.Config tabletRtkPage$lambda$204(Ntrip.Config c, String v) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(v, "v");
        return new Ntrip.Config(c.getOn(), c.getHost(), c.getPort(), c.getMount(), v, c.getPass());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String tabletRtkPage$lambda$205(SettingsActivity this$0) {
        return tabletRtkPage$cfg(this$0).getPass().length() == 0 ? "" : "••••••";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Ntrip.Config tabletRtkPage$lambda$206(Ntrip.Config c, String v) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(v, "v");
        return new Ntrip.Config(c.getOn(), c.getHost(), c.getPort(), c.getMount(), c.getUser(), v);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletRtkPage$lambda$212(final SettingsActivity this$0, final TextView $rmd) {
        Ntrip.Config c = tabletRtkPage$cfg(this$0);
        if (StringsKt.isBlank(c.getHost())) {
            Toast.makeText(this$0, "Set the caster first", 0).show();
            return Unit.INSTANCE;
        }
        if ($rmd != null) {
            $rmd.setText("loading the caster's list…");
        }
        Ntrip.INSTANCE.loadMounts(c.getHost(), c.getPort(), c.getUser(), c.getPass(), new Function2() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda115
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit tabletRtkPage$lambda$212$lambda$211;
                tabletRtkPage$lambda$212$lambda$211 = SettingsActivity.tabletRtkPage$lambda$212$lambda$211($rmd, this$0, (List) obj, (String) obj2);
                return tabletRtkPage$lambda$212$lambda$211;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletRtkPage$lambda$212$lambda$211(final TextView $rmd, final SettingsActivity this$0, final List list, String err) {
        Iterable $this$map$iv;
        double mi;
        Iterable $this$mapTo$iv$iv;
        int $i$f$mapTo;
        String str;
        if ($rmd != null) {
            String mount = tabletRtkPage$cfg(this$0).getMount();
            if (mount.length() == 0) {
                mount = "not set";
            }
            $rmd.setText(mount);
        }
        List list2 = list;
        if (list2 == null || list2.isEmpty()) {
            new AlertDialog.Builder(this$0).setMessage("Couldn't get the mountpoints: " + (err == null ? "the caster listed none" : err)).setPositiveButton("OK", (DialogInterface.OnClickListener) null).show();
            return Unit.INSTANCE;
        }
        RtkStatus k = Ntrip.INSTANCE.getRtk();
        List $this$map$iv2 = list;
        int $i$f$map = 0;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv2, 10));
        Iterable $this$mapTo$iv$iv2 = $this$map$iv2;
        int $i$f$mapTo2 = 0;
        for (Object item$iv$iv : $this$mapTo$iv$iv2) {
            NtripCore.Mount m = (NtripCore.Mount) item$iv$iv;
            if (k == null || !k.getHasFix()) {
                $this$map$iv = $this$map$iv2;
                mi = Double.NaN;
            } else {
                $this$map$iv = $this$map$iv2;
                mi = m.milesFrom(k.getLat(), k.getLon());
            }
            String name = m.getName();
            String place = m.getPlace();
            RtkStatus k2 = k;
            String format = m.getFormat();
            int $i$f$map2 = $i$f$map;
            if ((Double.isInfinite(mi) || Double.isNaN(mi)) ? false : true) {
                $this$mapTo$iv$iv = $this$mapTo$iv$iv2;
                $i$f$mapTo = $i$f$mapTo2;
                str = "   " + Math.round(mi) + " mi";
            } else {
                $this$mapTo$iv$iv = $this$mapTo$iv$iv2;
                $i$f$mapTo = $i$f$mapTo2;
                str = "";
            }
            destination$iv$iv.add(name + "   " + place + "   " + format + str);
            $this$map$iv2 = $this$map$iv;
            k = k2;
            $this$mapTo$iv$iv2 = $this$mapTo$iv$iv;
            $i$f$map = $i$f$map2;
            $i$f$mapTo2 = $i$f$mapTo;
        }
        Collection labels = (List) destination$iv$iv;
        Collection $this$toTypedArray$iv = labels;
        new AlertDialog.Builder(this$0).setTitle("Mountpoint (nearest first)").setItems((CharSequence[]) $this$toTypedArray$iv.toArray(new String[0]), new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda48
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                SettingsActivity.tabletRtkPage$lambda$212$lambda$211$lambda$210(this$0, list, $rmd, dialogInterface, i);
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void tabletRtkPage$lambda$212$lambda$211$lambda$210(SettingsActivity this$0, List $list, TextView $rmd, DialogInterface dialogInterface, int i) {
        Ntrip.Config cur = tabletRtkPage$cfg(this$0);
        Ntrip.INSTANCE.save(this$0, new Ntrip.Config(cur.getOn(), cur.getHost(), cur.getPort(), ((NtripCore.Mount) $list.get(i)).getName(), cur.getUser(), cur.getPass()));
        if ($rmd != null) {
            $rmd.setText(((NtripCore.Mount) $list.get(i)).getName());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletRtkPage$lambda$216(final SettingsActivity this$0, final TextView $rmd) {
        final EditText $this$tabletRtkPage_u24lambda_u24216_u24lambda_u24213 = new EditText(this$0);
        $this$tabletRtkPage_u24lambda_u24216_u24lambda_u24213.setText(tabletRtkPage$cfg(this$0).getMount());
        CarUi carUi = this$0.ui;
        if (carUi == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi = null;
        }
        int dp = carUi.dp(20);
        CarUi carUi2 = this$0.ui;
        if (carUi2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi2 = null;
        }
        int dp2 = carUi2.dp(16);
        CarUi carUi3 = this$0.ui;
        if (carUi3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi3 = null;
        }
        int dp3 = carUi3.dp(20);
        CarUi carUi4 = this$0.ui;
        if (carUi4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi4 = null;
        }
        $this$tabletRtkPage_u24lambda_u24216_u24lambda_u24213.setPadding(dp, dp2, dp3, carUi4.dp(16));
        new AlertDialog.Builder(this$0).setTitle("Mountpoint").setView($this$tabletRtkPage_u24lambda_u24216_u24lambda_u24213).setPositiveButton("Save", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda49
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                SettingsActivity.tabletRtkPage$lambda$216$lambda$215(this$0, $this$tabletRtkPage_u24lambda_u24216_u24lambda_u24213, $rmd, dialogInterface, i);
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void tabletRtkPage$lambda$216$lambda$215(SettingsActivity this$0, EditText $e, TextView $rmd, DialogInterface dialogInterface, int i) {
        Ntrip.Config c = tabletRtkPage$cfg(this$0);
        Ntrip.INSTANCE.save(this$0, new Ntrip.Config(c.getOn(), c.getHost(), c.getPort(), $e.getText().toString(), c.getUser(), c.getPass()));
        if ($rmd != null) {
            String mount = tabletRtkPage$cfg(this$0).getMount();
            if (mount.length() == 0) {
                mount = "not set";
            }
            $rmd.setText(mount);
        }
    }

    private final void backupCasterSection(LinearLayout col) {
        CarUi carUi;
        CarUi carUi2;
        CarUi carUi3;
        CarUi carUi4 = this.ui;
        if (carUi4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi4 = null;
        }
        TextView header = carUi4.header("Backup caster");
        CarUi carUi5 = this.ui;
        if (carUi5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi5 = null;
        }
        col.addView(header, CarUi.lp$default(carUi5, 0, 0, 3, null));
        toggleCard(col, "Use a backup caster", "Only when the main caster gives no RTK for 3 minutes (e.g. outside NYSNet's area). The main one is tried again every 10 minutes.", new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda57
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean backupCasterSection$lambda$217;
                backupCasterSection$lambda$217 = SettingsActivity.backupCasterSection$lambda$217(SettingsActivity.this);
                return Boolean.valueOf(backupCasterSection$lambda$217);
            }
        }, new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda62
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit backupCasterSection$lambda$218;
                backupCasterSection$lambda$218 = SettingsActivity.backupCasterSection$lambda$218(SettingsActivity.this, ((Boolean) obj).booleanValue());
                return backupCasterSection$lambda$218;
            }
        });
        toggleCard(col, "Use only the backup (testing)", "The main caster isn't used at all while this is on.", new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda63
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean backupCasterSection$lambda$219;
                backupCasterSection$lambda$219 = SettingsActivity.backupCasterSection$lambda$219(SettingsActivity.this);
                return Boolean.valueOf(backupCasterSection$lambda$219);
            }
        }, new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda64
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit backupCasterSection$lambda$220;
                backupCasterSection$lambda$220 = SettingsActivity.backupCasterSection$lambda$220(SettingsActivity.this, ((Boolean) obj).booleanValue());
                return backupCasterSection$lambda$220;
            }
        });
        backupCasterSection$field$226$default(this, col, "Caster", new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda65
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String backupCasterSection$lambda$227;
                backupCasterSection$lambda$227 = SettingsActivity.backupCasterSection$lambda$227(SettingsActivity.this);
                return backupCasterSection$lambda$227;
            }
        }, "rtk2go.com", false, new Function2() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda67
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Ntrip.Config backupCasterSection$lambda$228;
                backupCasterSection$lambda$228 = SettingsActivity.backupCasterSection$lambda$228((Ntrip.Config) obj, (String) obj2);
                return backupCasterSection$lambda$228;
            }
        }, 32, null);
        backupCasterSection$field$226(this, col, "Port", new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda68
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String backupCasterSection$lambda$229;
                backupCasterSection$lambda$229 = SettingsActivity.backupCasterSection$lambda$229(SettingsActivity.this);
                return backupCasterSection$lambda$229;
            }
        }, "2101", true, new Function2() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda69
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Ntrip.Config backupCasterSection$lambda$230;
                backupCasterSection$lambda$230 = SettingsActivity.backupCasterSection$lambda$230((Ntrip.Config) obj, (String) obj2);
                return backupCasterSection$lambda$230;
            }
        });
        backupCasterSection$field$226$default(this, col, "Username", new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda70
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String backupCasterSection$lambda$231;
                backupCasterSection$lambda$231 = SettingsActivity.backupCasterSection$lambda$231(SettingsActivity.this);
                return backupCasterSection$lambda$231;
            }
        }, "RTK2go: your email address", false, new Function2() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda71
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Ntrip.Config backupCasterSection$lambda$232;
                backupCasterSection$lambda$232 = SettingsActivity.backupCasterSection$lambda$232((Ntrip.Config) obj, (String) obj2);
                return backupCasterSection$lambda$232;
            }
        }, 32, null);
        backupCasterSection$field$226$default(this, col, "Password", new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda58
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String backupCasterSection$lambda$233;
                backupCasterSection$lambda$233 = SettingsActivity.backupCasterSection$lambda$233(SettingsActivity.this);
                return backupCasterSection$lambda$233;
            }
        }, "RTK2go: none", false, new Function2() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda59
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Ntrip.Config backupCasterSection$lambda$234;
                backupCasterSection$lambda$234 = SettingsActivity.backupCasterSection$lambda$234((Ntrip.Config) obj, (String) obj2);
                return backupCasterSection$lambda$234;
            }
        }, 32, null);
        CarUi carUi6 = this.ui;
        if (carUi6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi = null;
        } else {
            carUi = carUi6;
        }
        String mount = backupCasterSection$b(this).getMount();
        if (mount.length() == 0) {
            mount = "Auto: the nearest base";
        }
        Pair row$default = CarUi.row$default(carUi, "Mountpoint", mount, 0, null, null, 28, null);
        LinearLayout rm = (LinearLayout) row$default.component1();
        final TextView rmd = (TextView) row$default.component2();
        CarUi carUi7 = this.ui;
        if (carUi7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi2 = null;
        } else {
            carUi2 = carUi7;
        }
        rm.addView(CarUi.button$default(carUi2, "Auto", false, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda60
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit backupCasterSection$lambda$236;
                backupCasterSection$lambda$236 = SettingsActivity.backupCasterSection$lambda$236(rmd, this);
                return backupCasterSection$lambda$236;
            }
        }, 2, null));
        CarUi carUi8 = this.ui;
        if (carUi8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi3 = null;
        } else {
            carUi3 = carUi8;
        }
        rm.addView(CarUi.button$default(carUi3, "Type", false, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda61
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit backupCasterSection$lambda$240;
                backupCasterSection$lambda$240 = SettingsActivity.backupCasterSection$lambda$240(SettingsActivity.this, rmd);
                return backupCasterSection$lambda$240;
            }
        }, 2, null));
        LinearLayout linearLayout = rm;
        CarUi carUi9 = this.ui;
        if (carUi9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi9 = null;
        }
        col.addView(linearLayout, CarUi.lp$default(carUi9, 0, 0, 3, null));
        CarUi carUi10 = this.ui;
        if (carUi10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi10 = null;
        }
        TextView note = carUi10.note("Auto picks the nearest base within 25 miles from the caster's list (read at most every 30 minutes) and moves to a closer one when you're 15+ miles from it. Only one connection at a time. RTK2go: Caster rtk2go.com, Port 2101, Username your email, Password none.");
        CarUi carUi11 = this.ui;
        if (carUi11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi11 = null;
        }
        col.addView(note, CarUi.lp$default(carUi11, 0, 0, 3, null));
    }

    private static final Ntrip.Config backupCasterSection$b(SettingsActivity this$0) {
        return Ntrip.INSTANCE.backup(this$0);
    }

    private static final void backupCasterSection$put(SettingsActivity this$0, Ntrip.Config c) {
        Ntrip.INSTANCE.saveBackup(this$0, c);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean backupCasterSection$lambda$217(SettingsActivity this$0) {
        return backupCasterSection$b(this$0).getOn();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit backupCasterSection$lambda$218(SettingsActivity this$0, boolean v) {
        Ntrip.Config c = backupCasterSection$b(this$0);
        backupCasterSection$put(this$0, new Ntrip.Config(v, c.getHost(), c.getPort(), c.getMount(), c.getUser(), c.getPass()));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean backupCasterSection$lambda$219(SettingsActivity this$0) {
        return Ntrip.INSTANCE.backupOnly(this$0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit backupCasterSection$lambda$220(SettingsActivity this$0, boolean v) {
        Ntrip.INSTANCE.setBackupOnly(this$0, v);
        return Unit.INSTANCE;
    }

    static /* synthetic */ void backupCasterSection$field$226$default(SettingsActivity settingsActivity, LinearLayout linearLayout, String str, Function0 function0, String str2, boolean z, Function2 function2, int i, Object obj) {
        if ((i & 32) != 0) {
            z = false;
        }
        backupCasterSection$field$226(settingsActivity, linearLayout, str, function0, str2, z, function2);
    }

    private static final void backupCasterSection$field$226(final SettingsActivity this$0, LinearLayout $col, final String title, final Function0<String> function0, final String hint, final boolean numeric, final Function2<? super Ntrip.Config, ? super String, Ntrip.Config> function2) {
        CarUi carUi;
        CarUi carUi2;
        CarUi carUi3 = this$0.ui;
        if (carUi3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi = null;
        } else {
            carUi = carUi3;
        }
        String invoke = function0.invoke();
        if (invoke.length() == 0) {
            invoke = "not set";
        }
        Pair row$default = CarUi.row$default(carUi, title, invoke, 0, null, null, 28, null);
        LinearLayout r = (LinearLayout) row$default.component1();
        final TextView d = (TextView) row$default.component2();
        CarUi carUi4 = this$0.ui;
        if (carUi4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi2 = null;
        } else {
            carUi2 = carUi4;
        }
        r.addView(CarUi.button$default(carUi2, "Change", false, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda108
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit backupCasterSection$field$226$lambda$225;
                backupCasterSection$field$226$lambda$225 = SettingsActivity.backupCasterSection$field$226$lambda$225(this$0, title, function0, hint, numeric, function2, d);
                return backupCasterSection$field$226$lambda$225;
            }
        }, 2, null));
        LinearLayout linearLayout = r;
        CarUi carUi5 = this$0.ui;
        if (carUi5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi5 = null;
        }
        $col.addView(linearLayout, CarUi.lp$default(carUi5, 0, 0, 3, null));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit backupCasterSection$field$226$lambda$225(final SettingsActivity this$0, String $title, final Function0 $value, String $hint, boolean $numeric, final Function2 $set, final TextView $d) {
        final EditText $this$backupCasterSection_u24field_u24226_u24lambda_u24225_u24lambda_u24222 = new EditText(this$0);
        $this$backupCasterSection_u24field_u24226_u24lambda_u24225_u24lambda_u24222.setText((CharSequence) $value.invoke());
        $this$backupCasterSection_u24field_u24226_u24lambda_u24225_u24lambda_u24222.setHint($hint);
        $this$backupCasterSection_u24field_u24226_u24lambda_u24225_u24lambda_u24222.setInputType($numeric ? 2 : 524289);
        CarUi carUi = this$0.ui;
        if (carUi == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi = null;
        }
        int dp = carUi.dp(20);
        CarUi carUi2 = this$0.ui;
        if (carUi2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi2 = null;
        }
        int dp2 = carUi2.dp(16);
        CarUi carUi3 = this$0.ui;
        if (carUi3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi3 = null;
        }
        int dp3 = carUi3.dp(20);
        CarUi carUi4 = this$0.ui;
        if (carUi4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi4 = null;
        }
        $this$backupCasterSection_u24field_u24226_u24lambda_u24225_u24lambda_u24222.setPadding(dp, dp2, dp3, carUi4.dp(16));
        new AlertDialog.Builder(this$0).setTitle($title).setView($this$backupCasterSection_u24field_u24226_u24lambda_u24225_u24lambda_u24222).setPositiveButton("Save", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda77
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                SettingsActivity.backupCasterSection$field$226$lambda$225$lambda$224($set, $this$backupCasterSection_u24field_u24226_u24lambda_u24225_u24lambda_u24222, $d, $value, this$0, dialogInterface, i);
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void backupCasterSection$field$226$lambda$225$lambda$224(Function2 $set, EditText $e, TextView $d, Function0 $value, SettingsActivity this$0, DialogInterface dialogInterface, int i) {
        backupCasterSection$put(this$0, (Ntrip.Config) $set.invoke(backupCasterSection$b(this$0), $e.getText().toString()));
        if ($d != null) {
            CharSequence charSequence = (CharSequence) $value.invoke();
            if (charSequence.length() == 0) {
                charSequence = "not set";
            }
            $d.setText(charSequence);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String backupCasterSection$lambda$227(SettingsActivity this$0) {
        return backupCasterSection$b(this$0).getHost();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Ntrip.Config backupCasterSection$lambda$228(Ntrip.Config c, String v) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(v, "v");
        return new Ntrip.Config(c.getOn(), v, c.getPort(), c.getMount(), c.getUser(), c.getPass());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String backupCasterSection$lambda$229(SettingsActivity this$0) {
        return String.valueOf(backupCasterSection$b(this$0).getPort());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Ntrip.Config backupCasterSection$lambda$230(Ntrip.Config c, String v) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(v, "v");
        boolean on = c.getOn();
        String host = c.getHost();
        Integer intOrNull = StringsKt.toIntOrNull(v);
        return new Ntrip.Config(on, host, intOrNull != null ? intOrNull.intValue() : c.getPort(), c.getMount(), c.getUser(), c.getPass());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String backupCasterSection$lambda$231(SettingsActivity this$0) {
        return backupCasterSection$b(this$0).getUser();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Ntrip.Config backupCasterSection$lambda$232(Ntrip.Config c, String v) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(v, "v");
        return new Ntrip.Config(c.getOn(), c.getHost(), c.getPort(), c.getMount(), v, c.getPass());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String backupCasterSection$lambda$233(SettingsActivity this$0) {
        return backupCasterSection$b(this$0).getPass();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Ntrip.Config backupCasterSection$lambda$234(Ntrip.Config c, String v) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(v, "v");
        return new Ntrip.Config(c.getOn(), c.getHost(), c.getPort(), c.getMount(), c.getUser(), v);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit backupCasterSection$lambda$236(TextView $rmd, SettingsActivity this$0) {
        Ntrip.Config c = backupCasterSection$b(this$0);
        backupCasterSection$put(this$0, new Ntrip.Config(c.getOn(), c.getHost(), c.getPort(), "", c.getUser(), c.getPass()));
        if ($rmd != null) {
            $rmd.setText("Auto: the nearest base");
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit backupCasterSection$lambda$240(final SettingsActivity this$0, final TextView $rmd) {
        final EditText $this$backupCasterSection_u24lambda_u24240_u24lambda_u24237 = new EditText(this$0);
        $this$backupCasterSection_u24lambda_u24240_u24lambda_u24237.setText(backupCasterSection$b(this$0).getMount());
        CarUi carUi = this$0.ui;
        if (carUi == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi = null;
        }
        int dp = carUi.dp(20);
        CarUi carUi2 = this$0.ui;
        if (carUi2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi2 = null;
        }
        int dp2 = carUi2.dp(16);
        CarUi carUi3 = this$0.ui;
        if (carUi3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi3 = null;
        }
        int dp3 = carUi3.dp(20);
        CarUi carUi4 = this$0.ui;
        if (carUi4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi4 = null;
        }
        $this$backupCasterSection_u24lambda_u24240_u24lambda_u24237.setPadding(dp, dp2, dp3, carUi4.dp(16));
        new AlertDialog.Builder(this$0).setTitle("Mountpoint (case matters)").setView($this$backupCasterSection_u24lambda_u24240_u24lambda_u24237).setPositiveButton("Save", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                SettingsActivity.backupCasterSection$lambda$240$lambda$239($this$backupCasterSection_u24lambda_u24240_u24lambda_u24237, $rmd, this$0, dialogInterface, i);
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void backupCasterSection$lambda$240$lambda$239(EditText $e, TextView $rmd, SettingsActivity this$0, DialogInterface dialogInterface, int i) {
        Ntrip.Config c = backupCasterSection$b(this$0);
        backupCasterSection$put(this$0, new Ntrip.Config(c.getOn(), c.getHost(), c.getPort(), $e.getText().toString(), c.getUser(), c.getPass()));
        if ($rmd != null) {
            String mount = backupCasterSection$b(this$0).getMount();
            if (mount.length() == 0) {
                mount = "Auto: the nearest base";
            }
            $rmd.setText(mount);
        }
    }

    static /* synthetic */ void rtkFusionSection$send$default(SettingsActivity settingsActivity, String str, Function0 function0, int i, Object obj) {
        if ((i & 4) != 0) {
            function0 = null;
        }
        rtkFusionSection$send(settingsActivity, str, function0);
    }

    private static final void rtkFusionSection$send(final SettingsActivity this$0, final String op, final Function0<Unit> function0) {
        DataClient dc = Link.INSTANCE.getData();
        if (dc == null || !dc.getConnected()) {
            Toast.makeText(this$0, "Not connected to the comma", 0).show();
            if (function0 != null) {
                function0.invoke();
                return;
            }
            return;
        }
        dc.request("rtk_action", MapsKt.mapOf(TuplesKt.to("op", op)), new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda118
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit rtkFusionSection$send$lambda$242;
                rtkFusionSection$send$lambda$242 = SettingsActivity.rtkFusionSection$send$lambda$242(this$0, op, function0, (JSONObject) obj);
                return rtkFusionSection$send$lambda$242;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit rtkFusionSection$send$lambda$242(SettingsActivity this$0, String $op, Function0 $done, JSONObject ack) {
        Intrinsics.checkNotNullParameter(ack, "ack");
        if (!ack.optBoolean("ok")) {
            String it = ack.optString("error", "failed");
            if (Intrinsics.areEqual(it, "unknown action")) {
                it = "This needs the comma update with the RTK tools (patch 31)";
            }
            new AlertDialog.Builder(this$0).setTitle(RtkTools.INSTANCE.label($op)).setMessage(it).setPositiveButton("OK", (DialogInterface.OnClickListener) null).show();
        }
        if ($done != null) {
            $done.invoke();
        }
        return Unit.INSTANCE;
    }

    private final void rtkFusionSection(final View sv, LinearLayout col) {
        CarUi carUi;
        CarUi carUi2;
        CarUi carUi3;
        CarUi carUi4;
        CarUi carUi5;
        CarUi carUi6;
        CarUi carUi7;
        String str;
        CharSequence text;
        CarUi carUi8;
        CarUi carUi9;
        CarUi carUi10;
        CarUi carUi11;
        CarUi carUi12 = this.ui;
        CarUi carUi13 = null;
        if (carUi12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi12 = null;
        }
        TextView header = carUi12.header("Sensor fusion");
        CarUi carUi14 = this.ui;
        if (carUi14 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi14 = null;
        }
        col.addView(header, CarUi.lp$default(carUi14, 0, 0, 3, null));
        CarUi carUi15 = this.ui;
        if (carUi15 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi15 = null;
        }
        TextView note = carUi15.note("The ZED-F9R has its own gyro and accelerometer. Once it has learned how it sits in the car it carries the position through tunnels, garages and tall buildings, like a car's built-in navigation. The steps below go by themselves while you drive; keep the board taped flat and don't move it afterwards (if you do: Recalibrate mount).");
        CarUi carUi16 = this.ui;
        if (carUi16 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi16 = null;
        }
        col.addView(note, CarUi.lp$default(carUi16, 0, 0, 3, null));
        CarUi carUi17 = this.ui;
        if (carUi17 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi = null;
        } else {
            carUi = carUi17;
        }
        Pair row$default = CarUi.row$default(carUi, "Firmware", "…", 0, null, null, 28, null);
        LinearLayout rf = (LinearLayout) row$default.component1();
        final TextView rfd = (TextView) row$default.component2();
        LinearLayout linearLayout = rf;
        CarUi carUi18 = this.ui;
        if (carUi18 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi18 = null;
        }
        col.addView(linearLayout, CarUi.lp$default(carUi18, 0, 0, 3, null));
        CarUi carUi19 = this.ui;
        if (carUi19 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi2 = null;
        } else {
            carUi2 = carUi19;
        }
        Pair row$default2 = CarUi.row$default(carUi2, "Calibration", "…", 0, null, null, 28, null);
        LinearLayout rsum = (LinearLayout) row$default2.component1();
        final TextView rsumd = (TextView) row$default2.component2();
        LinearLayout linearLayout2 = rsum;
        CarUi carUi20 = this.ui;
        if (carUi20 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi20 = null;
        }
        col.addView(linearLayout2, CarUi.lp$default(carUi20, 0, 0, 3, null));
        final BarView overallBar = rtkFusionSection$barIn(this, rsum, 16);
        Iterable $this$map$iv = RangesKt.until(0, 4);
        int $i$f$map = 0;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        Iterable $this$mapTo$iv$iv = $this$map$iv;
        Iterator<Integer> it = $this$mapTo$iv$iv.iterator();
        while (it.hasNext()) {
            ((IntIterator) it).nextInt();
            CarUi carUi21 = this.ui;
            if (carUi21 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi11 = carUi13;
            } else {
                carUi11 = carUi21;
            }
            Pair row$default3 = CarUi.row$default(carUi11, "…", "…", 0, null, null, 28, null);
            LinearLayout r = (LinearLayout) row$default3.component1();
            TextView d = (TextView) row$default3.component2();
            LinearLayout linearLayout3 = r;
            CarUi carUi22 = this.ui;
            if (carUi22 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi22 = null;
            }
            col.addView(linearLayout3, CarUi.lp$default(carUi22, 1, 0, 2, null));
            destination$iv$iv.add(new Triple(r, d, rtkFusionSection$barIn(this, r, 8)));
            $this$map$iv = $this$map$iv;
            $i$f$map = $i$f$map;
            $this$mapTo$iv$iv = $this$mapTo$iv$iv;
            carUi13 = null;
        }
        final List stepViews = (List) destination$iv$iv;
        CarUi carUi23 = this.ui;
        if (carUi23 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi3 = null;
        } else {
            carUi3 = carUi23;
        }
        Pair row$default4 = CarUi.row$default(carUi3, "Sensors", "…", 0, null, null, 28, null);
        LinearLayout rsen = (LinearLayout) row$default4.component1();
        final TextView rsend = (TextView) row$default4.component2();
        LinearLayout linearLayout4 = rsen;
        CarUi carUi24 = this.ui;
        if (carUi24 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi24 = null;
        }
        col.addView(linearLayout4, CarUi.lp$default(carUi24, 0, 0, 3, null));
        CarUi carUi25 = this.ui;
        if (carUi25 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi4 = null;
        } else {
            carUi4 = carUi25;
        }
        Pair row$default5 = CarUi.row$default(carUi4, "Mount angles", "…", 0, null, null, 28, null);
        LinearLayout rang = (LinearLayout) row$default5.component1();
        final TextView rangd = (TextView) row$default5.component2();
        LinearLayout linearLayout5 = rang;
        CarUi carUi26 = this.ui;
        if (carUi26 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi26 = null;
        }
        col.addView(linearLayout5, CarUi.lp$default(carUi26, 0, 0, 3, null));
        CarUi carUi27 = this.ui;
        if (carUi27 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi27 = null;
        }
        TextView header2 = carUi27.header("What the drive gave it so far");
        CarUi carUi28 = this.ui;
        if (carUi28 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi28 = null;
        }
        col.addView(header2, CarUi.lp$default(carUi28, 0, 0, 3, null));
        CarUi carUi29 = this.ui;
        if (carUi29 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi29 = null;
        }
        TextView note2 = carUi29.note("Counted by the comma from the receiver's own position and heading since the receiver connected (or since Recalibrate mount). u-blox asks for a stop with the engine on, then about 10 left and right turns of 90° or more at 20+ mph.");
        CarUi carUi30 = this.ui;
        if (carUi30 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi30 = null;
        }
        col.addView(note2, CarUi.lp$default(carUi30, 0, 0, 3, null));
        Iterable $this$map$iv2 = RangesKt.until(0, 3);
        int $i$f$map2 = 0;
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv2, 10));
        Iterable $this$mapTo$iv$iv2 = $this$map$iv2;
        int $i$f$mapTo = 0;
        Iterator<Integer> it2 = $this$mapTo$iv$iv2.iterator();
        while (it2.hasNext()) {
            ((IntIterator) it2).nextInt();
            CarUi carUi31 = this.ui;
            if (carUi31 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi10 = null;
            } else {
                carUi10 = carUi31;
            }
            Pair row$default6 = CarUi.row$default(carUi10, "…", "…", 0, null, null, 28, null);
            LinearLayout r2 = (LinearLayout) row$default6.component1();
            TextView d2 = (TextView) row$default6.component2();
            Iterable $this$map$iv3 = $this$map$iv2;
            LinearLayout linearLayout6 = r2;
            int $i$f$map3 = $i$f$map2;
            CarUi carUi32 = this.ui;
            if (carUi32 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi32 = null;
            }
            col.addView(linearLayout6, CarUi.lp$default(carUi32, 1, 0, 2, null));
            destination$iv$iv2.add(new Triple(r2, d2, rtkFusionSection$barIn(this, r2, 8)));
            $this$map$iv2 = $this$map$iv3;
            $i$f$mapTo = $i$f$mapTo;
            it2 = it2;
            $i$f$map2 = $i$f$map3;
            $this$mapTo$iv$iv2 = $this$mapTo$iv$iv2;
        }
        final List needViews = (List) destination$iv$iv2;
        CarUi carUi33 = this.ui;
        if (carUi33 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi5 = null;
        } else {
            carUi5 = carUi33;
        }
        Pair row$default7 = CarUi.row$default(carUi5, "Timeline", "nothing changed yet", 0, null, null, 28, null);
        LinearLayout rtl = (LinearLayout) row$default7.component1();
        final TextView rtld = (TextView) row$default7.component2();
        LinearLayout linearLayout7 = rtl;
        CarUi carUi34 = this.ui;
        if (carUi34 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi34 = null;
        }
        col.addView(linearLayout7, CarUi.lp$default(carUi34, 0, 0, 3, null));
        CarUi carUi35 = this.ui;
        if (carUi35 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi6 = null;
        } else {
            carUi6 = carUi35;
        }
        Pair row$default8 = CarUi.row$default(carUi6, "Speed from the car", "Sends the car's speed to the receiver 10 times a second, the way a built-in navigation gets wheel speed. Steadier position and heading, and it keeps going when the satellites drop out.", 0, null, null, 28, null);
        LinearLayout rsp = (LinearLayout) row$default8.component1();
        final TextView rspd = (TextView) row$default8.component2();
        CarUi carUi36 = this.ui;
        if (carUi36 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi36 = null;
        }
        final Switch sw = carUi36.bigSwitch();
        final Ref.BooleanRef painting = new Ref.BooleanRef();
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda235
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                SettingsActivity.rtkFusionSection$lambda$247(painting, sw, this, compoundButton, z);
            }
        });
        rsp.addView(sw);
        LinearLayout linearLayout8 = rsp;
        CarUi carUi37 = this.ui;
        if (carUi37 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi37 = null;
        }
        col.addView(linearLayout8, CarUi.lp$default(carUi37, 0, 0, 3, null));
        CarUi carUi38 = this.ui;
        if (carUi38 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi38 = null;
        }
        TextView header3 = carUi38.header("RTK tools");
        CarUi carUi39 = this.ui;
        if (carUi39 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi39 = null;
        }
        col.addView(header3, CarUi.lp$default(carUi39, 0, 0, 3, null));
        CarUi carUi40 = this.ui;
        if (carUi40 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi7 = null;
        } else {
            carUi7 = carUi40;
        }
        Pair row$default9 = CarUi.row$default(carUi7, "Last used", "nothing yet", 0, null, null, 28, null);
        LinearLayout rlast = (LinearLayout) row$default9.component1();
        final TextView rlastd = (TextView) row$default9.component2();
        LinearLayout linearLayout9 = rlast;
        CarUi carUi41 = this.ui;
        if (carUi41 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi41 = null;
        }
        col.addView(linearLayout9, CarUi.lp$default(carUi41, 0, 0, 3, null));
        for (Iterator<RtkTools.Tool> it3 = RtkTools.INSTANCE.getTOOLS().iterator(); it3.hasNext(); it3 = it3) {
            final RtkTools.Tool t = it3.next();
            CarUi carUi42 = this.ui;
            if (carUi42 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi8 = null;
            } else {
                carUi8 = carUi42;
            }
            LinearLayout r3 = (LinearLayout) CarUi.row$default(carUi8, t.getLabel(), t.getWhat(), 0, null, null, 28, null).component1();
            CarUi carUi43 = this.ui;
            if (carUi43 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi9 = null;
            } else {
                carUi9 = carUi43;
            }
            r3.addView(CarUi.button$default(carUi9, t.getDanger() ? "Reset" : "Run", false, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda246
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit rtkFusionSection$lambda$249;
                    rtkFusionSection$lambda$249 = SettingsActivity.rtkFusionSection$lambda$249(SettingsActivity.this, t);
                    return rtkFusionSection$lambda$249;
                }
            }, 2, null));
            LinearLayout linearLayout10 = r3;
            CarUi carUi44 = this.ui;
            if (carUi44 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi44 = null;
            }
            col.addView(linearLayout10, CarUi.lp$default(carUi44, 0, 0, 3, null));
        }
        if (rspd == null || (text = rspd.getText()) == null || (str = text.toString()) == null) {
            str = "";
        }
        final String speedDesc = str;
        final Ref.BooleanRef seen = new Ref.BooleanRef();
        livePoll(sv, new Runnable() { // from class: com.starpilot.starview.SettingsActivity$rtkFusionSection$poll$1

            /* compiled from: SettingsActivity.kt */
            public /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[RtkTools.State.values().length];
                    try {
                        iArr[RtkTools.State.DONE.ordinal()] = 1;
                    } catch (NoSuchFieldError e) {
                    }
                    try {
                        iArr[RtkTools.State.NOW.ordinal()] = 2;
                    } catch (NoSuchFieldError e2) {
                    }
                    try {
                        iArr[RtkTools.State.WAITING.ordinal()] = 3;
                    } catch (NoSuchFieldError e3) {
                    }
                    try {
                        iArr[RtkTools.State.OFF.ordinal()] = 4;
                    } catch (NoSuchFieldError e4) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            /* JADX WARN: Code restructure failed: missing block: B:129:0x0305, code lost:
            
                if (r8 == null) goto L124;
             */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void run() {
                String str2;
                String str3;
                String str4;
                Integer speedBadPct;
                Iterator it4;
                String str5;
                Handler handler;
                int i = 1;
                if (sv.isAttachedToWindow()) {
                    seen.element = true;
                } else if (seen.element) {
                    return;
                }
                RtkStatus k = Ntrip.INSTANCE.getRtk();
                if (k == null) {
                    TextView textView = rfd;
                    if (textView != null) {
                        textView.setText("No RTK receiver reported by the comma");
                    }
                    TextView textView2 = rsumd;
                    if (textView2 != null) {
                        textView2.setText("–");
                    }
                    TextView textView3 = rsend;
                    if (textView3 != null) {
                        textView3.setText("–");
                    }
                    Iterable $this$forEach$iv = stepViews;
                    for (Object element$iv : $this$forEach$iv) {
                        ((LinearLayout) ((Triple) element$iv).component1()).setVisibility(8);
                    }
                    Iterable $this$forEach$iv2 = needViews;
                    for (Object element$iv2 : $this$forEach$iv2) {
                        ((LinearLayout) ((Triple) element$iv2).component1()).setVisibility(8);
                    }
                } else {
                    TextView textView4 = rfd;
                    if (textView4 != null) {
                        textView4.setText(RtkTools.INSTANCE.firmware(k));
                    }
                    float pct = RtkTools.INSTANCE.overall(k);
                    TextView textView5 = rsumd;
                    if (textView5 != null) {
                        textView5.setText(Math.round(100 * pct) + "%  ·  " + RtkTools.INSTANCE.summary(k));
                    }
                    overallBar.setFraction(pct);
                    TextView textView6 = rsend;
                    if (textView6 != null) {
                        textView6.setText(RtkTools.INSTANCE.sensors(k.getSensors()));
                    }
                    TextView textView7 = rangd;
                    if (textView7 != null) {
                        textView7.setText(RtkTools.INSTANCE.mountAngles(k));
                    }
                    List needs = RtkTools.INSTANCE.needs(k);
                    Iterator<T> it5 = needViews.iterator();
                    int i2 = 0;
                    int i3 = 0;
                    while (true) {
                        if (!it5.hasNext()) {
                            break;
                        }
                        int i4 = i3;
                        i3 += i;
                        Triple v = (Triple) it5.next();
                        LinearLayout r4 = (LinearLayout) v.component1();
                        TextView d3 = (TextView) v.component2();
                        BarView bar = (BarView) v.component3();
                        RtkTools.Need n = (RtkTools.Need) CollectionsKt.getOrNull(needs, i4);
                        r4.setVisibility(n == null ? 8 : 0);
                        if (n != null) {
                            View childAt = r4.getChildAt(r4.getChildCount() - i);
                            LinearLayout linearLayout11 = childAt instanceof LinearLayout ? (LinearLayout) childAt : null;
                            View childAt2 = linearLayout11 != null ? linearLayout11.getChildAt(0) : null;
                            TextView textView8 = childAt2 instanceof TextView ? (TextView) childAt2 : null;
                            if (textView8 != null) {
                                textView8.setText((n.getMet() ? "✓  " : "●  ") + n.getTitle());
                            }
                            if (d3 != null) {
                                d3.setText(n.getWords());
                            }
                            bar.setFraction(n.getProgress());
                        }
                        i = 1;
                    }
                    TextView textView9 = rtld;
                    if (textView9 != null) {
                        String joinToString$default = CollectionsKt.joinToString(CollectionsKt.take(RtkTools.timeline$default(RtkTools.INSTANCE, k, System.currentTimeMillis(), null, 4, null), 12), "\n", "", "", -1, "...", null);
                        if (joinToString$default.length() == 0) {
                            joinToString$default = k.getCalibration() == null ? "needs the comma update with the calibration view (patch 32)" : "nothing changed yet";
                        }
                        textView9.setText(joinToString$default);
                    }
                    Iterator it6 = RtkTools.INSTANCE.steps(k).iterator();
                    int i5 = 0;
                    while (it6.hasNext()) {
                        int i6 = i5;
                        i5++;
                        RtkTools.Step step = (RtkTools.Step) it6.next();
                        Triple<LinearLayout, TextView, BarView> triple = stepViews.get(i6);
                        LinearLayout r5 = triple.component1();
                        TextView d4 = triple.component2();
                        BarView bar2 = triple.component3();
                        bar2.setFraction(RtkTools.INSTANCE.stepProgress(k, step.getTitle()));
                        r5.setVisibility(i2);
                        View childAt3 = r5.getChildAt(r5.getChildCount() - 1);
                        LinearLayout linearLayout12 = childAt3 instanceof LinearLayout ? (LinearLayout) childAt3 : null;
                        View childAt4 = linearLayout12 != null ? linearLayout12.getChildAt(i2) : null;
                        TextView textView10 = childAt4 instanceof TextView ? (TextView) childAt4 : null;
                        if (textView10 != null) {
                            switch (WhenMappings.$EnumSwitchMapping$0[step.getState().ordinal()]) {
                                case 1:
                                    str5 = "✓  ";
                                    break;
                                case 2:
                                    str5 = "●  ";
                                    break;
                                case 3:
                                    str5 = "○  ";
                                    break;
                                case 4:
                                    str5 = "–  ";
                                    break;
                                default:
                                    throw new NoWhenBranchMatchedException();
                            }
                            it4 = it6;
                            textView10.setText(str5 + step.getTitle());
                        } else {
                            it4 = it6;
                        }
                        if (d4 != null) {
                            d4.setText(step.getWords());
                        }
                        it6 = it4;
                        i2 = 0;
                    }
                    painting.element = true;
                    if (sw.isEnabled()) {
                        sw.setChecked(k.getSpeedFeedOn());
                    }
                    painting.element = false;
                    TextView textView11 = rspd;
                    if (textView11 != null) {
                        if (k.getSpeedFeedOn()) {
                            String str6 = speedDesc;
                            if (k.getSpeedFeedHz() > 0.0d) {
                                long round = Math.round(k.getSpeedFeedHz());
                                Integer speedFeedDelayMs = k.getSpeedFeedDelayMs();
                                String str7 = "";
                                if (speedFeedDelayMs != null) {
                                    int it7 = speedFeedDelayMs.intValue();
                                    str4 = ", " + it7 + " ms behind the car";
                                }
                                str4 = "";
                                RtkCalibration calibration = k.getCalibration();
                                if (calibration != null && (speedBadPct = calibration.getSpeedBadPct()) != null) {
                                    int it8 = speedBadPct.intValue();
                                    String str8 = ", receiver accepted " + (100 - it8) + "% in the last minute";
                                    if (str8 != null) {
                                        str7 = str8;
                                    }
                                }
                                str3 = round + " a second" + str4 + str7;
                            } else {
                                str3 = "waiting for the car's speed";
                            }
                            str2 = str6 + "\nNow: " + str3;
                        } else {
                            str2 = speedDesc;
                        }
                        textView11.setText(str2);
                    }
                    TextView textView12 = rlastd;
                    if (textView12 != null) {
                        String lastAction = RtkTools.INSTANCE.lastAction(k);
                        if (lastAction.length() == 0) {
                            lastAction = "nothing yet";
                        }
                        textView12.setText(lastAction);
                    }
                }
                handler = this.handler;
                handler.postDelayed(this, 1000L);
            }
        });
    }

    private static final BarView rtkFusionSection$barIn(SettingsActivity this$0, LinearLayout row, int heightDp) {
        BarView bar = new BarView(this$0);
        View childAt = row.getChildAt(row.getChildCount() - 1);
        Intrinsics.checkNotNull(childAt, "null cannot be cast to non-null type android.widget.LinearLayout");
        LinearLayout linearLayout = (LinearLayout) childAt;
        BarView barView = bar;
        CarUi carUi = this$0.ui;
        CarUi carUi2 = null;
        if (carUi == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi = null;
        }
        LinearLayout.LayoutParams $this$rtkFusionSection_u24barIn_u24lambda_u24243 = new LinearLayout.LayoutParams(-1, carUi.dp(heightDp));
        CarUi carUi3 = this$0.ui;
        if (carUi3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
        } else {
            carUi2 = carUi3;
        }
        $this$rtkFusionSection_u24barIn_u24lambda_u24243.topMargin = carUi2.dp(10);
        Unit unit = Unit.INSTANCE;
        linearLayout.addView(barView, $this$rtkFusionSection_u24barIn_u24lambda_u24243);
        return bar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void rtkFusionSection$lambda$247(Ref.BooleanRef $painting, final Switch $sw, SettingsActivity this$0, CompoundButton compoundButton, boolean v) {
        if ($painting.element) {
            return;
        }
        $sw.setEnabled(false);
        rtkFusionSection$send(this$0, v ? "speed_on" : "speed_off", new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda133
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit rtkFusionSection$lambda$247$lambda$246;
                rtkFusionSection$lambda$247$lambda$246 = SettingsActivity.rtkFusionSection$lambda$247$lambda$246($sw);
                return rtkFusionSection$lambda$247$lambda$246;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit rtkFusionSection$lambda$247$lambda$246(Switch $sw) {
        $sw.setEnabled(true);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit rtkFusionSection$lambda$249(final SettingsActivity this$0, final RtkTools.Tool $t) {
        new AlertDialog.Builder(this$0).setTitle($t.getLabel() + "?").setMessage($t.getWhat()).setPositiveButton($t.getLabel(), new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda187
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                SettingsActivity.rtkFusionSection$lambda$249$lambda$248($t, this$0, dialogInterface, i);
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void rtkFusionSection$lambda$249$lambda$248(RtkTools.Tool $t, SettingsActivity this$0, DialogInterface dialogInterface, int i) {
        rtkFusionSection$send$default(this$0, $t.getOp(), null, 4, null);
    }

    private final void livePoll(View sv, final Runnable poll) {
        sv.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.starpilot.starview.SettingsActivity$livePoll$1
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View v) {
                Handler handler;
                Handler handler2;
                Intrinsics.checkNotNullParameter(v, "v");
                handler = SettingsActivity.this.handler;
                handler.removeCallbacks(poll);
                handler2 = SettingsActivity.this.handler;
                handler2.post(poll);
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View v) {
                Handler handler;
                Intrinsics.checkNotNullParameter(v, "v");
                handler = SettingsActivity.this.handler;
                handler.removeCallbacks(poll);
            }
        });
        if (sv.isAttachedToWindow()) {
            this.handler.post(poll);
        }
    }

    private final View tabletStoragePage() {
        CarUi carUi;
        Pair<ScrollView, LinearLayout> page = page();
        ScrollView sv = page.component1();
        LinearLayout col = page.component2();
        LinearLayout $this$tabletStoragePage_u24lambda_u24250 = new LinearLayout(this);
        $this$tabletStoragePage_u24lambda_u24250.setOrientation(1);
        LinearLayout linearLayout = $this$tabletStoragePage_u24lambda_u24250;
        CarUi carUi2 = this.ui;
        if (carUi2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi2 = null;
        }
        col.addView(linearLayout, CarUi.lp$default(carUi2, 0, 0, 3, null));
        Ref.BooleanRef copyingNow = new Ref.BooleanRef();
        CarUi carUi3 = this.ui;
        if (carUi3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi = null;
        } else {
            carUi = carUi3;
        }
        LinearLayout r0 = (LinearLayout) CarUi.row$default(carUi, "Storage", "loading…", 0, null, null, 28, null).component1();
        LinearLayout linearLayout2 = r0;
        CarUi carUi4 = this.ui;
        if (carUi4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi4 = null;
        }
        $this$tabletStoragePage_u24lambda_u24250.addView(linearLayout2, CarUi.lp$default(carUi4, 0, 0, 3, null));
        Ref.BooleanRef seen = new Ref.BooleanRef();
        SettingsActivity$tabletStoragePage$poll$1 poll = new SettingsActivity$tabletStoragePage$poll$1(sv, seen, this, copyingNow, $this$tabletStoragePage_u24lambda_u24250);
        livePoll(sv, poll);
        return sv;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void tabletStoragePage$render(LinearLayout list, Ref.BooleanRef copyingNow, SettingsActivity this$0, JSONObject j) {
        list.removeAllViews();
        StorageInfo.Copying cp = StorageInfo.INSTANCE.copying(j);
        copyingNow.element = cp != null;
        for (Pair<String, String> pair : StorageInfo.INSTANCE.rows(j)) {
            String t = pair.component1();
            String d = pair.component2();
            CarUi carUi = this$0.ui;
            if (carUi == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi = null;
            }
            LinearLayout r = (LinearLayout) CarUi.row$default(carUi, t, d, 0, null, null, 28, null).component1();
            LinearLayout linearLayout = r;
            CarUi carUi2 = this$0.ui;
            if (carUi2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi2 = null;
            }
            list.addView(linearLayout, CarUi.lp$default(carUi2, 0, 0, 3, null));
            if (Intrinsics.areEqual(t, "Copying to the stick now")) {
                if ((cp != null ? cp.getPct() : null) != null) {
                    ProgressBar $this$tabletStoragePage_u24render_u24lambda_u24251 = new ProgressBar(this$0, null, 16842872);
                    $this$tabletStoragePage_u24render_u24lambda_u24251.setMax(100);
                    $this$tabletStoragePage_u24render_u24lambda_u24251.setProgress(cp.getPct().intValue());
                    $this$tabletStoragePage_u24render_u24lambda_u24251.setProgressTintList(ColorStateList.valueOf(Color.rgb(94, 229, 160)));
                    LinearLayout.LayoutParams $this$tabletStoragePage_u24render_u24lambda_u24252 = new LinearLayout.LayoutParams(-1, (int) (6 * this$0.getResources().getDisplayMetrics().density));
                    int m = (int) (16 * this$0.getResources().getDisplayMetrics().density);
                    $this$tabletStoragePage_u24render_u24lambda_u24252.setMargins(m, 0, m, m);
                    Unit unit = Unit.INSTANCE;
                    list.addView($this$tabletStoragePage_u24render_u24lambda_u24251, $this$tabletStoragePage_u24render_u24lambda_u24252);
                }
            }
        }
    }

    private final View tabletNavPage() {
        CarUi carUi;
        CarUi carUi2;
        Pair<ScrollView, LinearLayout> page = page();
        final ScrollView sv = page.component1();
        LinearLayout col = page.component2();
        final UiSettings s = new UiSettings(this);
        CarUi carUi3 = this.ui;
        if (carUi3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi3 = null;
        }
        TextView note = carUi3.note("StarPilot's own navigation, on a map beside the road view: search places like Google Maps (as you type, or gas / coffee / food nearby or along the route), live traffic, incidents and closures from Mapbox (the comma's key), up to three routes to choose from, the turn-by-turn list, and route options (avoid tolls, highways, ferries, unpaved roads, carpool lanes…) under ⚙ on the map. Drag the bar between the map and the road view to make either bigger; drag it to the edge to close the map.");
        CarUi carUi4 = this.ui;
        if (carUi4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi4 = null;
        }
        col.addView(note, CarUi.lp$default(carUi4, 0, 0, 3, null));
        CarUi carUi5 = this.ui;
        if (carUi5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi5 = null;
        }
        TextView button = carUi5.button("Open the map", true, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda223
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit tabletNavPage$lambda$253;
                tabletNavPage$lambda$253 = SettingsActivity.tabletNavPage$lambda$253(SettingsActivity.this);
                return tabletNavPage$lambda$253;
            }
        });
        CarUi carUi6 = this.ui;
        if (carUi6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi6 = null;
        }
        col.addView(button, CarUi.lp$default(carUi6, 0, 0, 3, null));
        CarUi carUi7 = this.ui;
        if (carUi7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi7 = null;
        }
        TextView header = carUi7.header("Map");
        CarUi carUi8 = this.ui;
        if (carUi8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi8 = null;
        }
        col.addView(header, CarUi.lp$default(carUi8, 0, 0, 3, null));
        toggleCard(col, "Open the map when a route starts", "Also a destination set in Galaxy or on the comma.", new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda229
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean tabletNavPage$lambda$254;
                tabletNavPage$lambda$254 = SettingsActivity.tabletNavPage$lambda$254(s);
                return Boolean.valueOf(tabletNavPage$lambda$254);
            }
        }, new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda230
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit tabletNavPage$lambda$255;
                tabletNavPage$lambda$255 = SettingsActivity.tabletNavPage$lambda$255(s, ((Boolean) obj).booleanValue());
                return tabletNavPage$lambda$255;
            }
        });
        toggleCard(col, "Live traffic on every road", "Off: plain street map (traffic is still drawn on your route).", new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda231
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean tabletNavPage$lambda$256;
                tabletNavPage$lambda$256 = SettingsActivity.tabletNavPage$lambda$256(s);
                return Boolean.valueOf(tabletNavPage$lambda$256);
            }
        }, new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda232
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit tabletNavPage$lambda$257;
                tabletNavPage$lambda$257 = SettingsActivity.tabletNavPage$lambda$257(s, ((Boolean) obj).booleanValue());
                return tabletNavPage$lambda$257;
            }
        });
        toggleCard(col, "North up", "Off: the way you drive is up.", new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda233
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean tabletNavPage$lambda$258;
                tabletNavPage$lambda$258 = SettingsActivity.tabletNavPage$lambda$258(s);
                return Boolean.valueOf(tabletNavPage$lambda$258);
            }
        }, new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda234
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit tabletNavPage$lambda$259;
                tabletNavPage$lambda$259 = SettingsActivity.tabletNavPage$lambda$259(s, ((Boolean) obj).booleanValue());
                return tabletNavPage$lambda$259;
            }
        });
        toggleCard(col, "Map on the right side", null, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda236
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean tabletNavPage$lambda$260;
                tabletNavPage$lambda$260 = SettingsActivity.tabletNavPage$lambda$260(s);
                return Boolean.valueOf(tabletNavPage$lambda$260);
            }
        }, new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda237
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit tabletNavPage$lambda$261;
                tabletNavPage$lambda$261 = SettingsActivity.tabletNavPage$lambda$261(s, ((Boolean) obj).booleanValue());
                return tabletNavPage$lambda$261;
            }
        });
        CarUi carUi9 = this.ui;
        if (carUi9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi9 = null;
        }
        TextView header2 = carUi9.header("Voice");
        CarUi carUi10 = this.ui;
        if (carUi10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi10 = null;
        }
        col.addView(header2, CarUi.lp$default(carUi10, 0, 0, 3, null));
        CarUi carUi11 = this.ui;
        if (carUi11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi11 = null;
        }
        TextView note2 = carUi11.note("Turn-by-turn: Mapbox's spoken directions, timed for the road, plus the alerts. Alerts only: a crash, closure or hazard about a mile ahead, and a faster route. The 🔊 button on the map switches it too.");
        CarUi carUi12 = this.ui;
        if (carUi12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi12 = null;
        }
        col.addView(note2, CarUi.lp$default(carUi12, 0, 0, 3, null));
        for (Pair pair : CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to("all", "Turn-by-turn directions and alerts"), TuplesKt.to("alerts", "Alerts only"), TuplesKt.to("off", "Off")})) {
            final String v = (String) pair.component1();
            String title = (String) pair.component2();
            toggleCard(col, title, null, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda238
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    boolean tabletNavPage$lambda$262;
                    tabletNavPage$lambda$262 = SettingsActivity.tabletNavPage$lambda$262(s, v);
                    return Boolean.valueOf(tabletNavPage$lambda$262);
                }
            }, new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda225
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit tabletNavPage$lambda$263;
                    tabletNavPage$lambda$263 = SettingsActivity.tabletNavPage$lambda$263(s, v, this, ((Boolean) obj).booleanValue());
                    return tabletNavPage$lambda$263;
                }
            });
        }
        CarUi carUi13 = this.ui;
        if (carUi13 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi13 = null;
        }
        TextView header3 = carUi13.header("GPS from the comma");
        CarUi carUi14 = this.ui;
        if (carUi14 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi14 = null;
        }
        col.addView(header3, CarUi.lp$default(carUi14, 0, 0, 3, null));
        CarUi carUi15 = this.ui;
        if (carUi15 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi15 = null;
        }
        TextView note3 = carUi15.note("This tablet has no GPS of its own; the map always uses the comma's. StarView can also pass it to Android, so other apps see the car's real position. One-time setup: Developer options › Select mock location app › StarView.");
        CarUi carUi16 = this.ui;
        if (carUi16 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi16 = null;
        }
        col.addView(note3, CarUi.lp$default(carUi16, 0, 0, 3, null));
        toggleCard(col, "Share the comma's GPS with other apps", null, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda226
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean tabletNavPage$lambda$264;
                tabletNavPage$lambda$264 = SettingsActivity.tabletNavPage$lambda$264(SettingsActivity.this);
                return Boolean.valueOf(tabletNavPage$lambda$264);
            }
        }, new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda227
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit tabletNavPage$lambda$265;
                tabletNavPage$lambda$265 = SettingsActivity.tabletNavPage$lambda$265(SettingsActivity.this, ((Boolean) obj).booleanValue());
                return tabletNavPage$lambda$265;
            }
        });
        CarUi carUi17 = this.ui;
        if (carUi17 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi = null;
        } else {
            carUi = carUi17;
        }
        Pair row$default = CarUi.row$default(carUi, "GPS", "-", 0, null, null, 28, null);
        LinearLayout rg = (LinearLayout) row$default.component1();
        final TextView gpsVal = (TextView) row$default.component2();
        CarUi carUi18 = this.ui;
        if (carUi18 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi2 = null;
        } else {
            carUi2 = carUi18;
        }
        rg.addView(CarUi.button$default(carUi2, "Developer options", false, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda228
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit tabletNavPage$lambda$267;
                tabletNavPage$lambda$267 = SettingsActivity.tabletNavPage$lambda$267(SettingsActivity.this);
                return tabletNavPage$lambda$267;
            }
        }, 2, null));
        LinearLayout linearLayout = rg;
        CarUi carUi19 = this.ui;
        if (carUi19 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi19 = null;
        }
        col.addView(linearLayout, CarUi.lp$default(carUi19, 0, 0, 3, null));
        final Ref.BooleanRef seen = new Ref.BooleanRef();
        livePoll(sv, new Runnable() { // from class: com.starpilot.starview.SettingsActivity$tabletNavPage$poll$1
            @Override // java.lang.Runnable
            public void run() {
                Handler handler;
                String str;
                if (sv.isAttachedToWindow()) {
                    seen.element = true;
                } else if (seen.element) {
                    return;
                }
                long gAge = (System.currentTimeMillis() - GpsBridge.INSTANCE.getLastFixMs()) / 1000;
                Boolean allowed = GpsBridge.INSTANCE.permitted();
                TextView textView = gpsVal;
                if (textView != null) {
                    if (GpsBridge.INSTANCE.enabled(this)) {
                        if (!Intrinsics.areEqual((Object) allowed, (Object) false)) {
                            if (GpsBridge.INSTANCE.getLastFixMs() <= 0 || gAge >= 3) {
                                str = "allowed · " + GpsBridge.INSTANCE.getStatus() + (GpsBridge.INSTANCE.getLastFixMs() > 0 ? " (last fix " + gAge + "s ago)" : " (nothing from the comma yet)");
                            } else {
                                str = GpsBridge.INSTANCE.getStatus() + " (" + GpsBridge.INSTANCE.getFixes() + " fixes)";
                            }
                        }
                    }
                    textView.setText(str);
                }
                handler = this.handler;
                handler.postDelayed(this, 1000L);
            }
        });
        return sv;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletNavPage$lambda$253(SettingsActivity this$0) {
        this$0.startActivity(new Intent(this$0, (Class<?>) MainActivity.class).putExtra("open_nav", true).addFlags(603979776));
        this$0.finish();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean tabletNavPage$lambda$254(UiSettings $s) {
        return $s.get("map_auto_open");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletNavPage$lambda$255(UiSettings $s, boolean it) {
        $s.set("map_auto_open", it);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean tabletNavPage$lambda$256(UiSettings $s) {
        return $s.get("map_traffic");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletNavPage$lambda$257(UiSettings $s, boolean it) {
        $s.set("map_traffic", it);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean tabletNavPage$lambda$258(UiSettings $s) {
        return $s.get("map_north_up");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletNavPage$lambda$259(UiSettings $s, boolean it) {
        $s.set("map_north_up", it);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean tabletNavPage$lambda$260(UiSettings $s) {
        return Intrinsics.areEqual($s.getStr("map_side", "left"), "right");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletNavPage$lambda$261(UiSettings $s, boolean it) {
        $s.setStr("map_side", it ? "right" : "left");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean tabletNavPage$lambda$262(UiSettings $s, String $v) {
        return Intrinsics.areEqual(Nav.INSTANCE.voiceMode($s), $v);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletNavPage$lambda$263(UiSettings $s, String $v, SettingsActivity this$0, boolean on) {
        if (on) {
            $s.setStr("nav_voice", $v);
            this$0.panels.remove("tablet:nav");
            this$0.open("tablet:nav");
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean tabletNavPage$lambda$264(SettingsActivity this$0) {
        return GpsBridge.INSTANCE.enabled(this$0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletNavPage$lambda$265(SettingsActivity this$0, boolean it) {
        GpsBridge.INSTANCE.setEnabled(this$0, it);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletNavPage$lambda$267(SettingsActivity this$0) {
        try {
            Result.Companion companion = Result.Companion;
            this$0.startActivity(new Intent("android.settings.APPLICATION_DEVELOPMENT_SETTINGS"));
            KtCompat.resultOf(Unit.INSTANCE);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            KtCompat.resultOf(ResultKt.createFailure(th));
        }
        return Unit.INSTANCE;
    }

    private final View tabletBrakingPage() {
        final List<String> lines;
        CarUi carUi;
        CarUi carUi2;
        CarUi carUi3;
        CarUi carUi4;
        Pair<ScrollView, LinearLayout> page = page();
        ScrollView sv = page.component1();
        LinearLayout col = page.component2();
        CarUi carUi5 = this.ui;
        if (carUi5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi5 = null;
        }
        String format = String.format("%.2f", Arrays.copyOf(new Object[]{Float.valueOf(0.45f)}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        TextView note = carUi5.note("Each time openpilot braked by itself (at least " + format + " m/s², you not on the brake or gas): when, the speed before and after, the hardest braking, and what the planner was braking for. The same reason shows live on the road in the \"Why it's slowing\" card (Tablet › Display › Widgets).");
        CarUi carUi6 = this.ui;
        if (carUi6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi6 = null;
        }
        col.addView(note, CarUi.lp$default(carUi6, 0, 0, 3, null));
        final File f = new File(getFilesDir(), "braking_log.txt");
        if (f.exists()) {
            Iterable $this$filter$iv = FilesKt.readLines(f, kotlin.text.Charsets.UTF_8);
            Collection destination$iv$iv = new ArrayList();
            for (Object element$iv$iv : $this$filter$iv) {
                String it = (String) element$iv$iv;
                if (!StringsKt.isBlank(it)) {
                    destination$iv$iv.add(element$iv$iv);
                }
            }
            lines = CollectionsKt.reversed(CollectionsKt.takeLast((List) destination$iv$iv, 300));
        } else {
            lines = CollectionsKt.emptyList();
        }
        CarUi carUi7 = this.ui;
        if (carUi7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi = null;
        } else {
            carUi = carUi7;
        }
        LinearLayout r = (LinearLayout) CarUi.row$default(carUi, "Braking log", lines.isEmpty() ? "Nothing logged yet." : lines.size() + " braking episode(s), newest first", 0, null, null, 28, null).component1();
        CarUi carUi8 = this.ui;
        if (carUi8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi2 = null;
        } else {
            carUi2 = carUi8;
        }
        r.addView(CarUi.button$default(carUi2, "Copy", false, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda207
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit tabletBrakingPage$lambda$269;
                tabletBrakingPage$lambda$269 = SettingsActivity.tabletBrakingPage$lambda$269(SettingsActivity.this, lines);
                return tabletBrakingPage$lambda$269;
            }
        }, 2, null));
        View view = new View(this);
        CarUi carUi9 = this.ui;
        if (carUi9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi9 = null;
        }
        r.addView(view, new LinearLayout.LayoutParams(carUi9.dp(8), 1));
        CarUi carUi10 = this.ui;
        if (carUi10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi3 = null;
        } else {
            carUi3 = carUi10;
        }
        r.addView(CarUi.button$default(carUi3, "Clear", false, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda208
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit tabletBrakingPage$lambda$271;
                tabletBrakingPage$lambda$271 = SettingsActivity.tabletBrakingPage$lambda$271(SettingsActivity.this, f);
                return tabletBrakingPage$lambda$271;
            }
        }, 2, null));
        LinearLayout linearLayout = r;
        CarUi carUi11 = this.ui;
        if (carUi11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi11 = null;
        }
        col.addView(linearLayout, CarUi.lp$default(carUi11, 0, 0, 3, null));
        for (String l : lines) {
            TextView $this$tabletBrakingPage_u24lambda_u24272 = new TextView(this);
            $this$tabletBrakingPage_u24lambda_u24272.setText(StringsKt.replace(l, " | ", "\n", false));
            $this$tabletBrakingPage_u24lambda_u24272.setTextColor(CarUi.Companion.getTEXT());
            $this$tabletBrakingPage_u24lambda_u24272.setTextSize(15.0f);
            CarUi carUi12 = this.ui;
            if (carUi12 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi4 = null;
            } else {
                carUi4 = carUi12;
            }
            $this$tabletBrakingPage_u24lambda_u24272.setBackground(CarUi.rounded$default(carUi4, CarUi.Companion.getCARD(), 10, null, 4, null));
            CarUi carUi13 = this.ui;
            if (carUi13 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi13 = null;
            }
            int dp = carUi13.dp(16);
            CarUi carUi14 = this.ui;
            if (carUi14 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi14 = null;
            }
            int dp2 = carUi14.dp(10);
            CarUi carUi15 = this.ui;
            if (carUi15 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi15 = null;
            }
            int dp3 = carUi15.dp(16);
            CarUi carUi16 = this.ui;
            if (carUi16 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi16 = null;
            }
            $this$tabletBrakingPage_u24lambda_u24272.setPadding(dp, dp2, dp3, carUi16.dp(10));
            TextView textView = $this$tabletBrakingPage_u24lambda_u24272;
            CarUi carUi17 = this.ui;
            if (carUi17 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi17 = null;
            }
            col.addView(textView, carUi17.lp(0, 6));
        }
        return sv;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletBrakingPage$lambda$269(SettingsActivity this$0, List $lines) {
        Object systemService = this$0.getSystemService("clipboard");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.content.ClipboardManager");
        ((ClipboardManager) systemService).setPrimaryClip(ClipData.newPlainText("StarView braking log", CollectionsKt.joinToString($lines, "\n", "", "", -1, "...", null)));
        Toast.makeText(this$0, "Copied", 0).show();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletBrakingPage$lambda$271(final SettingsActivity this$0, final File $f) {
        new AlertDialog.Builder(this$0).setMessage("Clear the braking log?").setPositiveButton("Clear", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda87
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                SettingsActivity.tabletBrakingPage$lambda$271$lambda$270($f, this$0, dialogInterface, i);
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void tabletBrakingPage$lambda$271$lambda$270(File $f, SettingsActivity this$0, DialogInterface dialogInterface, int i) {
        $f.delete();
        this$0.panels.remove("tablet:braking");
        this$0.open("tablet:braking");
    }

    static /* synthetic */ void slider$default(SettingsActivity settingsActivity, LinearLayout linearLayout, String str, float f, float f2, float f3, float f4, Function1 function1, Float f5, Function1 function12, Function0 function0, int i, Object obj) {
        Float f6;
        if ((i & 128) == 0) {
            f6 = f5;
        } else {
            f6 = null;
        }
        settingsActivity.slider(linearLayout, str, f, f2, f3, f4, function1, f6, function12, function0);
    }

    private final void slider(LinearLayout col, String title, final float min, float max, final float step, float value, final Function1<? super Float, String> fmt, final Float reset, final Function1<? super Float, Unit> set, final Function0<Unit> onRelease) {
        CarUi carUi;
        CarUi carUi2;
        int steps = Math.round((max - min) / step);
        CarUi carUi3 = this.ui;
        CarUi carUi4 = null;
        if (carUi3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi = null;
        } else {
            carUi = carUi3;
        }
        Pair row$default = CarUi.row$default(carUi, title, fmt.invoke(Float.valueOf(value)), 0, null, null, 28, null);
        LinearLayout r = (LinearLayout) row$default.component1();
        final TextView d = (TextView) row$default.component2();
        final SeekBar $this$slider_u24lambda_u24273 = new SeekBar(this);
        $this$slider_u24lambda_u24273.setMax(steps);
        $this$slider_u24lambda_u24273.setProgress(RangesKt.coerceIn(Math.round((value - min) / step), 0, steps));
        CarUi carUi5 = this.ui;
        if (carUi5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi5 = null;
        }
        $this$slider_u24lambda_u24273.setMinimumHeight(carUi5.dp(48));
        $this$slider_u24lambda_u24273.setThumbTintList(ColorStateList.valueOf(-1));
        $this$slider_u24lambda_u24273.setProgressTintList(ColorStateList.valueOf(CarUi.Companion.getACCENT()));
        $this$slider_u24lambda_u24273.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.starpilot.starview.SettingsActivity$slider$bar$1$1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar sb, int p, boolean fromUser) {
                float slider$of;
                TextView textView = d;
                if (textView != null) {
                    Function1<Float, String> function1 = fmt;
                    slider$of = SettingsActivity.slider$of(min, step, p);
                    textView.setText(function1.invoke(Float.valueOf(slider$of)));
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar sb) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar sb) {
                float slider$of;
                Function1<Float, Unit> function1 = set;
                slider$of = SettingsActivity.slider$of(min, step, $this$slider_u24lambda_u24273.getProgress());
                function1.invoke(Float.valueOf(slider$of));
                onRelease.invoke();
            }
        });
        if (reset != null) {
            CarUi carUi6 = this.ui;
            if (carUi6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi2 = null;
            } else {
                carUi2 = carUi6;
            }
            r.addView(CarUi.button$default(carUi2, "Reset", false, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda72
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit slider$lambda$274;
                    slider$lambda$274 = SettingsActivity.slider$lambda$274(set, reset, $this$slider_u24lambda_u24273, min, step, d, fmt, onRelease);
                    return slider$lambda$274;
                }
            }, 2, null));
        }
        LinearLayout linearLayout = r;
        CarUi carUi7 = this.ui;
        if (carUi7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi7 = null;
        }
        col.addView(linearLayout, CarUi.lp$default(carUi7, 0, 0, 3, null));
        SeekBar seekBar = $this$slider_u24lambda_u24273;
        CarUi carUi8 = this.ui;
        if (carUi8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi8 = null;
        }
        LinearLayout.LayoutParams $this$slider_u24lambda_u24275 = new LinearLayout.LayoutParams(-1, carUi8.dp(48));
        CarUi carUi9 = this.ui;
        if (carUi9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi9 = null;
        }
        $this$slider_u24lambda_u24275.leftMargin = carUi9.dp(12);
        CarUi carUi10 = this.ui;
        if (carUi10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
        } else {
            carUi4 = carUi10;
        }
        $this$slider_u24lambda_u24275.rightMargin = carUi4.dp(12);
        Unit unit = Unit.INSTANCE;
        col.addView(seekBar, $this$slider_u24lambda_u24275);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final float slider$of(float $min, float $step, int p) {
        return (p * $step) + $min;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit slider$lambda$274(Function1 $set, Float $reset, SeekBar $bar, float $min, float $step, TextView $d, Function1 $fmt, Function0 $onRelease) {
        $set.invoke($reset);
        $bar.setProgress(Math.round(($reset.floatValue() - $min) / $step));
        if ($d != null) {
            $d.setText((CharSequence) $fmt.invoke($reset));
        }
        $onRelease.invoke();
        return Unit.INSTANCE;
    }

    private final View newIdeasPage() {
        String str;
        String it;
        CarUi carUi;
        CarUi carUi2;
        Pair<ScrollView, LinearLayout> page = page();
        ScrollView sv = page.component1();
        LinearLayout col = page.component2();
        final UiSettings s = new UiSettings(this);
        Boolean commaOk = Ideas.INSTANCE.getCommaOk();
        boolean z = true;
        if (Intrinsics.areEqual((Object) commaOk, (Object) true)) {
            str = "Comma: ready (patch 39).";
        } else if (Intrinsics.areEqual((Object) commaOk, (Object) false)) {
            str = "Comma: needs the update with patch 39 - until then only the warnings show.";
        } else {
            if (commaOk != null) {
                throw new NoWhenBranchMatchedException();
            }
            str = "Comma: not checked yet (it is checked when an idea first has advice).";
        }
        String comma = str;
        CarUi carUi3 = this.ui;
        if (carUi3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi3 = null;
        }
        TextView note = carUi3.note("Experiments, one switch each - all off until you turn them on. All the thinking runs on this tablet; the comma only gets a tiny \"advice\" twice a second, and only to drive gentler: a lower set speed (never under 10 mph, never higher), up to 0.6 s more following room, or up to 1 ft over inside the lane (only while Lane Centering is on). A blue chip at the top of the drive screen always says why. " + comma);
        CarUi carUi4 = this.ui;
        if (carUi4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi4 = null;
        }
        col.addView(note, CarUi.lp$default(carUi4, 0, 0, 3, null));
        String section = "";
        for (final NewIdeas.Idea idea : NewIdeas.INSTANCE.getALL()) {
            if (!Intrinsics.areEqual(idea.getSection(), section)) {
                section = idea.getSection();
                CarUi carUi5 = this.ui;
                if (carUi5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ui");
                    carUi5 = null;
                }
                TextView header = carUi5.header(section);
                CarUi carUi6 = this.ui;
                if (carUi6 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ui");
                    carUi6 = null;
                }
                col.addView(header, CarUi.lp$default(carUi6, 0, 0, 3, null));
            }
            String section2 = section;
            String it2 = NewIdeas.INSTANCE.getFIRST().get(idea.getId());
            if (it2 == null || (it = "\n" + it2) == null) {
                it = "";
            }
            String first = it;
            toggleCard(col, idea.getTitle(), idea.getDesc() + first + status(idea.getId()), new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda76
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    boolean newIdeasPage$lambda$277;
                    newIdeasPage$lambda$277 = SettingsActivity.newIdeasPage$lambda$277(s, idea);
                    return Boolean.valueOf(newIdeasPage$lambda$277);
                }
            }, new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda78
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit newIdeasPage$lambda$278;
                    newIdeasPage$lambda$278 = SettingsActivity.newIdeasPage$lambda$278(s, idea, this, ((Boolean) obj).booleanValue());
                    return newIdeasPage$lambda$278;
                }
            });
            String id = idea.getId();
            if (Intrinsics.areEqual(id, "teach")) {
                LinearLayout r = new LinearLayout(this);
                r.setOrientation(0);
                CarUi carUi7 = this.ui;
                if (carUi7 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ui");
                    carUi7 = null;
                }
                r.addView(carUi7.button(Ideas.INSTANCE.getMemory().getTeaching() != null ? "Stop and keep" : "Learn a path from here", z, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda79
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Unit newIdeasPage$lambda$280;
                        newIdeasPage$lambda$280 = SettingsActivity.newIdeasPage$lambda$280(SettingsActivity.this);
                        return newIdeasPage$lambda$280;
                    }
                }));
                CarUi carUi8 = this.ui;
                if (carUi8 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ui");
                    carUi2 = null;
                } else {
                    carUi2 = carUi8;
                }
                TextView button$default = CarUi.button$default(carUi2, "Forget taught paths", false, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda80
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Unit newIdeasPage$lambda$281;
                        newIdeasPage$lambda$281 = SettingsActivity.newIdeasPage$lambda$281(SettingsActivity.this);
                        return newIdeasPage$lambda$281;
                    }
                }, 2, null);
                LinearLayout.LayoutParams $this$newIdeasPage_u24lambda_u24282 = new LinearLayout.LayoutParams(-2, -2);
                CarUi carUi9 = this.ui;
                if (carUi9 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ui");
                    carUi9 = null;
                }
                $this$newIdeasPage_u24lambda_u24282.setMarginStart(carUi9.dp(10));
                Unit unit = Unit.INSTANCE;
                r.addView(button$default, $this$newIdeasPage_u24lambda_u24282);
                LinearLayout linearLayout = r;
                CarUi carUi10 = this.ui;
                if (carUi10 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ui");
                    carUi10 = null;
                }
                col.addView(linearLayout, CarUi.lp$default(carUi10, 0, 0, 3, null));
            } else if (Intrinsics.areEqual(id, "friends_fleet")) {
                LinearLayout r2 = new LinearLayout(this);
                r2.setOrientation(0);
                CarUi carUi11 = this.ui;
                if (carUi11 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ui");
                    carUi11 = null;
                }
                r2.addView(carUi11.button("Share mine now", true, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda81
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Unit newIdeasPage$lambda$286;
                        newIdeasPage$lambda$286 = SettingsActivity.newIdeasPage$lambda$286(SettingsActivity.this);
                        return newIdeasPage$lambda$286;
                    }
                }));
                CarUi carUi12 = this.ui;
                if (carUi12 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ui");
                    carUi = null;
                } else {
                    carUi = carUi12;
                }
                TextView button$default2 = CarUi.button$default(carUi, "Take friends' in", false, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda82
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Unit newIdeasPage$lambda$289;
                        newIdeasPage$lambda$289 = SettingsActivity.newIdeasPage$lambda$289(SettingsActivity.this);
                        return newIdeasPage$lambda$289;
                    }
                }, 2, null);
                LinearLayout.LayoutParams $this$newIdeasPage_u24lambda_u24290 = new LinearLayout.LayoutParams(-2, -2);
                CarUi carUi13 = this.ui;
                if (carUi13 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ui");
                    carUi13 = null;
                }
                $this$newIdeasPage_u24lambda_u24290.setMarginStart(carUi13.dp(10));
                Unit unit2 = Unit.INSTANCE;
                r2.addView(button$default2, $this$newIdeasPage_u24lambda_u24290);
                LinearLayout linearLayout2 = r2;
                CarUi carUi14 = this.ui;
                if (carUi14 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ui");
                    carUi14 = null;
                }
                col.addView(linearLayout2, CarUi.lp$default(carUi14, 0, 0, 3, null));
                CarUi carUi15 = this.ui;
                if (carUi15 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ui");
                    carUi15 = null;
                }
                TextView note2 = carUi15.note("Sync the Download/StarView/fleet folder with your friends (Google Drive app, or any shared folder). Each tablet writes its own file there after every drive and reads the others' when a drive starts.");
                CarUi carUi16 = this.ui;
                if (carUi16 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ui");
                    carUi16 = null;
                }
                col.addView(note2, CarUi.lp$default(carUi16, 0, 0, 3, null));
            }
            section = section2;
            z = true;
        }
        return sv;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean newIdeasPage$lambda$277(UiSettings $s, NewIdeas.Idea $idea) {
        return $s.get(NewIdeas.INSTANCE.key($idea.getId()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit newIdeasPage$lambda$278(UiSettings $s, NewIdeas.Idea $idea, SettingsActivity this$0, boolean v) {
        $s.set(NewIdeas.INSTANCE.key($idea.getId()), v);
        if (v && ((Intrinsics.areEqual($idea.getId(), "sirens") || Intrinsics.areEqual($idea.getId(), "horn")) && this$0.checkSelfPermission("android.permission.RECORD_AUDIO") != 0)) {
            this$0.requestPermissions(new String[]{"android.permission.RECORD_AUDIO"}, 77);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit newIdeasPage$lambda$280(SettingsActivity this$0) {
        if (Ideas.INSTANCE.getMemory().getTeaching() == null) {
            Ideas.INSTANCE.getMemory().startTeaching();
            this$0.toast("Learning: drive the path, then tap Stop and keep");
        } else {
            int n = Ideas.INSTANCE.getMemory().stopTeaching("path " + (Ideas.INSTANCE.getMemory().taughtCount() + 1));
            this$0.toast(n > 0 ? "Kept: " + n + " points" : "Too short (needs RTK fixed)");
        }
        this$0.open("tablet:ideas");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit newIdeasPage$lambda$281(SettingsActivity this$0) {
        Ideas.INSTANCE.getMemory().forgetTaught();
        this$0.toast("Forgotten");
        this$0.open("tablet:ideas");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit newIdeasPage$lambda$286(final SettingsActivity this$0) {
        new Thread(new Runnable() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda117
            @Override // java.lang.Runnable
            public final void run() {
                SettingsActivity.newIdeasPage$lambda$286$lambda$285(this$0);
            }
        }).start();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void newIdeasPage$lambda$286$lambda$285(final SettingsActivity this$0) {
        final int n = IdeaSpots.INSTANCE.exportFleet(NewIdeas.INSTANCE.deviceName(this$0));
        this$0.runOnUiThread(new Runnable() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda211
            @Override // java.lang.Runnable
            public final void run() {
                SettingsActivity.newIdeasPage$lambda$286$lambda$285$lambda$284(this$0, n);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void newIdeasPage$lambda$286$lambda$285$lambda$284(SettingsActivity this$0, int $n) {
        this$0.toast($n >= 0 ? "Shared " + $n + " places to Download/StarView/fleet" : "Couldn't write there");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit newIdeasPage$lambda$289(final SettingsActivity this$0) {
        new Thread(new Runnable() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda245
            @Override // java.lang.Runnable
            public final void run() {
                SettingsActivity.newIdeasPage$lambda$289$lambda$288(this$0);
            }
        }).start();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void newIdeasPage$lambda$289$lambda$288(final SettingsActivity this$0) {
        final int n = IdeaSpots.INSTANCE.importFleet(NewIdeas.INSTANCE.deviceName(this$0));
        this$0.runOnUiThread(new Runnable() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda89
            @Override // java.lang.Runnable
            public final void run() {
                SettingsActivity.newIdeasPage$lambda$289$lambda$288$lambda$287(this$0, n);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void newIdeasPage$lambda$289$lambda$288$lambda$287(SettingsActivity this$0, int $n) {
        this$0.toast($n + " places from friends");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x009c, code lost:
    
        if (r14.equals("spot_settings") == false) goto L109;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0287, code lost:
    
        r0 = "Cut-in spots: " + com.starpilot.starview.IdeaSpots.INSTANCE.count("cutin") + " - takeover spots: " + com.starpilot.starview.IdeaSpots.INSTANCE.count("takeover") + ".";
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0137, code lost:
    
        if (r14.equals("green_countdown") == false) goto L109;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x019c, code lost:
    
        r0 = "Lights with a green seen: " + com.starpilot.starview.Ideas.INSTANCE.getMemory().getLights().known() + ".";
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0198, code lost:
    
        if (r14.equals("hybrid") == false) goto L109;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x0283, code lost:
    
        if (r14.equals("nyc_cutins") == false) goto L109;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final String status(String id) {
        String it;
        int count$iv = 0;
        switch (id.hashCode()) {
            case -2038685998:
                if (id.equals("city_potholes")) {
                    Iterable $this$count$iv = IdeaSpots.INSTANCE.all();
                    if (!($this$count$iv instanceof Collection) || !((Collection) $this$count$iv).isEmpty()) {
                        count$iv = 0;
                        for (Object element$iv : $this$count$iv) {
                            IdeaSpots.Spot it2 = (IdeaSpots.Spot) element$iv;
                            if (Intrinsics.areEqual(it2.getSrc(), "nyc311") && (count$iv = count$iv + 1) < 0) {
                                CollectionsKt.throwCountOverflow();
                            }
                        }
                    }
                    it = "From 311: " + count$iv + " open potholes loaded.";
                    break;
                }
                it = "";
                break;
            case -1996341101:
                break;
            case -1889818619:
                if (id.equals("speed_bumps")) {
                    it = "Knows " + (IdeaSpots.INSTANCE.count("bump") + IdeaSpots.INSTANCE.count("rough")) + " it felt, " + IdeaSpots.INSTANCE.count("map_bump") + " from the map.";
                    break;
                }
                it = "";
                break;
            case -1664260572:
                if (id.equals("friends_fleet")) {
                    Iterable $this$count$iv2 = IdeaSpots.INSTANCE.all();
                    if (!($this$count$iv2 instanceof Collection) || !((Collection) $this$count$iv2).isEmpty()) {
                        int count$iv2 = 0;
                        for (Object element$iv2 : $this$count$iv2) {
                            IdeaSpots.Spot it3 = (IdeaSpots.Spot) element$iv2;
                            if (!SetsKt.setOf((Object[]) new String[]{"me", "osm", "nyc311"}).contains(it3.getSrc()) && (count$iv2 = count$iv2 + 1) < 0) {
                                CollectionsKt.throwCountOverflow();
                            }
                        }
                        count$iv = count$iv2;
                    }
                    it = "From friends: " + count$iv + " places.";
                    break;
                }
                it = "";
                break;
            case -1202757124:
                break;
            case -902148466:
                if (id.equals("sirens")) {
                    it = "Microphone: " + (checkSelfPermission("android.permission.RECORD_AUDIO") != 0 ? "not allowed yet" : "allowed") + " - " + SirenEar.INSTANCE.getStatus() + ".";
                    break;
                }
                it = "";
                break;
            case -891990146:
                if (id.equals("streak")) {
                    it = Ideas.INSTANCE.getMore().streakText(new UiSettings(this));
                    break;
                }
                it = "";
                break;
            case -789415947:
                break;
            case 3208579:
                if (id.equals("horn")) {
                    it = "Microphone: " + (checkSelfPermission("android.permission.RECORD_AUDIO") != 0 ? "not allowed yet" : "allowed") + ".";
                    break;
                }
                it = "";
                break;
            case 11067959:
                if (id.equals("double_parked")) {
                    it = "Blocked spots seen: " + IdeaSpots.INSTANCE.count("dpark") + ".";
                    break;
                }
                it = "";
                break;
            case 110233717:
                if (id.equals("teach")) {
                    it = "Taught paths: " + Ideas.INSTANCE.getMemory().taughtCount() + (Ideas.INSTANCE.getMemory().getTeaching() != null ? " - learning now" : "");
                    break;
                }
                it = "";
                break;
            case 502068928:
                break;
            case 554390290:
                if (id.equals("carsick")) {
                    String lastComfort = Ideas.INSTANCE.getMemory().lastComfort();
                    if (lastComfort.length() == 0) {
                        lastComfort = "No score yet.";
                    }
                    it = lastComfort;
                    break;
                }
                it = "";
                break;
            case 631785340:
                if (id.equals("speed_cams")) {
                    it = "Cameras known: " + IdeaSpots.INSTANCE.count("speed_cam") + ".";
                    break;
                }
                it = "";
                break;
            case 775364222:
                if (id.equals("potholes")) {
                    it = "Knows " + IdeaSpots.INSTANCE.count("pothole") + " potholes.";
                    break;
                }
                it = "";
                break;
            default:
                it = "";
                break;
        }
        return "\n" + ((Object) (Intrinsics.areEqual(it, "\n") ? "" : it));
    }

    private final void toast(String msg) {
        Toast.makeText(this, msg, 0).show();
    }

    private final View tabletBlinkerPage() {
        CarUi carUi;
        String str;
        String str2;
        Pair<ScrollView, LinearLayout> page = page();
        ScrollView sv = page.component1();
        LinearLayout col = page.component2();
        final UiSettings s = new UiSettings(this);
        CarUi carUi2 = this.ui;
        if (carUi2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi2 = null;
        }
        TextView note = carUi2.note("The tablet can turn the blinker on through the relay box: when you confirm a \"Change lanes?\" card, and (if switched on) before the route's turns. Every time it does, it is written in the list at the bottom with the reason.");
        CarUi carUi3 = this.ui;
        if (carUi3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi3 = null;
        }
        col.addView(note, CarUi.lp$default(carUi3, 0, 0, 3, null));
        CarUi carUi4 = this.ui;
        if (carUi4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi4 = null;
        }
        TextView header = carUi4.header("Lane changes");
        CarUi carUi5 = this.ui;
        if (carUi5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi5 = null;
        }
        col.addView(header, CarUi.lp$default(carUi5, 0, 0, 3, null));
        toggleCard(col, "\"Change lanes?\" card", "Asks when the driving model or the route wants a lane change. Nothing happens unless you confirm.", new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda21
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean tabletBlinkerPage$lambda$295;
                tabletBlinkerPage$lambda$295 = SettingsActivity.tabletBlinkerPage$lambda$295(s);
                return Boolean.valueOf(tabletBlinkerPage$lambda$295);
            }
        }, new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda34
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit tabletBlinkerPage$lambda$296;
                tabletBlinkerPage$lambda$296 = SettingsActivity.tabletBlinkerPage$lambda$296(s, ((Boolean) obj).booleanValue());
                return tabletBlinkerPage$lambda$296;
            }
        });
        toggleCard(col, "Confirm with a steering nudge", "Besides tapping Change: a nudge of the wheel toward that side while the card asks - how hard is set under Steering nudge strength below.", new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda39
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean tabletBlinkerPage$lambda$297;
                tabletBlinkerPage$lambda$297 = SettingsActivity.tabletBlinkerPage$lambda$297(s);
                return Boolean.valueOf(tabletBlinkerPage$lambda$297);
            }
        }, new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda40
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit tabletBlinkerPage$lambda$298;
                tabletBlinkerPage$lambda$298 = SettingsActivity.tabletBlinkerPage$lambda$298(s, ((Boolean) obj).booleanValue());
                return tabletBlinkerPage$lambda$298;
            }
        });
        toggleCard(col, "Take the route's exits", "With a destination: about 8 seconds before an exit or a fork (when you're in or next to its lane) it signals by itself and openpilot moves into the exit lane as soon as it sees it and the blind spot is clear. Cancel on the card skips that exit.", new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda41
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean tabletBlinkerPage$lambda$299;
                tabletBlinkerPage$lambda$299 = SettingsActivity.tabletBlinkerPage$lambda$299(s);
                return Boolean.valueOf(tabletBlinkerPage$lambda$299);
            }
        }, new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda42
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit tabletBlinkerPage$lambda$300;
                tabletBlinkerPage$lambda$300 = SettingsActivity.tabletBlinkerPage$lambda$300(s, ((Boolean) obj).booleanValue());
                return tabletBlinkerPage$lambda$300;
            }
        });
        toggleCard(col, "Pass a slower car", "Above 30 mph, when the car ahead holds you 5 mph or more under your set speed for 5 seconds and there's a lane on your left: asks \"Change lanes left?\". Always waits for your nudge (or Change) - never automatic.", new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda43
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean tabletBlinkerPage$lambda$301;
                tabletBlinkerPage$lambda$301 = SettingsActivity.tabletBlinkerPage$lambda$301(s);
                return Boolean.valueOf(tabletBlinkerPage$lambda$301);
            }
        }, new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda45
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit tabletBlinkerPage$lambda$302;
                tabletBlinkerPage$lambda$302 = SettingsActivity.tabletBlinkerPage$lambda$302(s, ((Boolean) obj).booleanValue());
                return tabletBlinkerPage$lambda$302;
            }
        });
        toggleCard(col, "Lane ends ahead", "The lane card counts down to a lane that ends (the road data's lane count drops ahead; the camera tells which side as you get close). On: when you're in the ending lane, about 8 s before it, asks \"Change lanes?\" toward the lane that goes on. Always waits for your nudge (or Change). Off: it only shows and logs it.", new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda46
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean tabletBlinkerPage$lambda$303;
                tabletBlinkerPage$lambda$303 = SettingsActivity.tabletBlinkerPage$lambda$303(s);
                return Boolean.valueOf(tabletBlinkerPage$lambda$303);
            }
        }, new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda47
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit tabletBlinkerPage$lambda$304;
                tabletBlinkerPage$lambda$304 = SettingsActivity.tabletBlinkerPage$lambda$304(s, ((Boolean) obj).booleanValue());
                return tabletBlinkerPage$lambda$304;
            }
        });
        toggleCard(col, "Move back right after passing", "After you moved left (a pass, or any lane change to the left): asks \"Change lanes right?\" once the passed car is out of the right blind spot and the right lane ahead is clear - nobody within 6 seconds, or a car there keeping up with your set speed. Always waits for your nudge (or Change). No = stay left.", new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda23
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean tabletBlinkerPage$lambda$305;
                tabletBlinkerPage$lambda$305 = SettingsActivity.tabletBlinkerPage$lambda$305(s);
                return Boolean.valueOf(tabletBlinkerPage$lambda$305);
            }
        }, new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda24
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit tabletBlinkerPage$lambda$306;
                tabletBlinkerPage$lambda$306 = SettingsActivity.tabletBlinkerPage$lambda$306(s, ((Boolean) obj).booleanValue());
                return tabletBlinkerPage$lambda$306;
            }
        });
        toggleCard(col, "Automatic (no confirming)", "The card counts down 3 seconds and signals by itself unless you tap No. Off = it always waits for you.", new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda25
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean tabletBlinkerPage$lambda$307;
                tabletBlinkerPage$lambda$307 = SettingsActivity.tabletBlinkerPage$lambda$307(s);
                return Boolean.valueOf(tabletBlinkerPage$lambda$307);
            }
        }, new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda26
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit tabletBlinkerPage$lambda$308;
                tabletBlinkerPage$lambda$308 = SettingsActivity.tabletBlinkerPage$lambda$308(s, ((Boolean) obj).booleanValue());
                return tabletBlinkerPage$lambda$308;
            }
        });
        CarUi carUi6 = this.ui;
        if (carUi6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi6 = null;
        }
        TextView header2 = carUi6.header("Steering nudge strength");
        CarUi carUi7 = this.ui;
        if (carUi7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi7 = null;
        }
        col.addView(header2, CarUi.lp$default(carUi7, 0, 0, 3, null));
        CarUi carUi8 = this.ui;
        if (carUi8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi8 = null;
        }
        TextView note2 = carUi8.note("How hard you push the wheel toward a side for it to count as a nudge, as a percentage of the car's own nudge (100 % = stock). Lower = lighter push. The push has to last 0.15 s, so a bump in the road doesn't count.");
        CarUi carUi9 = this.ui;
        if (carUi9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi9 = null;
        }
        col.addView(note2, CarUi.lp$default(carUi9, 0, 0, 3, null));
        slider(col, "To answer the \"Change lanes?\" card", 30.0f, 150.0f, 10.0f, s.getFloat("nudge_card_pct", 60.0f), SettingsActivity$tabletBlinkerPage$15.INSTANCE, Float.valueOf(60.0f), new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda27
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit tabletBlinkerPage$lambda$309;
                tabletBlinkerPage$lambda$309 = SettingsActivity.tabletBlinkerPage$lambda$309(s, ((Float) obj).floatValue());
                return tabletBlinkerPage$lambda$309;
            }
        }, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda28
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit unit;
                unit = Unit.INSTANCE;
                return unit;
            }
        });
        slider(col, "To start a lane change with the blinker on", 30.0f, 150.0f, 10.0f, s.getFloat("nudge_lc_pct", 100.0f), SettingsActivity$tabletBlinkerPage$18.INSTANCE, Float.valueOf(100.0f), new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda29
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit tabletBlinkerPage$lambda$311;
                tabletBlinkerPage$lambda$311 = SettingsActivity.tabletBlinkerPage$lambda$311(s, ((Float) obj).floatValue());
                return tabletBlinkerPage$lambda$311;
            }
        }, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda30
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit unit;
                unit = Unit.INSTANCE;
                return unit;
            }
        });
        CarUi carUi10 = this.ui;
        if (carUi10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi10 = null;
        }
        TextView note3 = carUi10.note("The second one is used by the comma (it needs the StarView comma patch with the nudge strength; Toyota only - other cars keep their stock nudge).");
        CarUi carUi11 = this.ui;
        if (carUi11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi11 = null;
        }
        col.addView(note3, CarUi.lp$default(carUi11, 0, 0, 3, null));
        CarUi carUi12 = this.ui;
        if (carUi12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi12 = null;
        }
        TextView header3 = carUi12.header("Drive replay");
        CarUi carUi13 = this.ui;
        if (carUi13 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi13 = null;
        }
        col.addView(header3, CarUi.lp$default(carUi13, 0, 0, 3, null));
        CarUi carUi14 = this.ui;
        if (carUi14 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi14 = null;
        }
        TextView note4 = carUi14.note("Parked: the comma plays one of its recorded drives to this tablet as if you were driving it, so you can watch when the cards ask, signal and take exits. The real blinkers never flash during a replay, and it stops by itself if the car is put in gear. Controls appear at the top of the drive screen.");
        CarUi carUi15 = this.ui;
        if (carUi15 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi15 = null;
        }
        col.addView(note4, CarUi.lp$default(carUi15, 0, 0, 3, null));
        final SettingsActivity $this$tabletBlinkerPage_u24lambda_u24317 = this;
        LinearLayout r = new LinearLayout($this$tabletBlinkerPage_u24lambda_u24317);
        r.setOrientation(0);
        CarUi carUi16 = $this$tabletBlinkerPage_u24lambda_u24317.ui;
        if (carUi16 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi16 = null;
        }
        r.addView(carUi16.button("Replay a drive…", true, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda31
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit tabletBlinkerPage$lambda$317$lambda$314;
                tabletBlinkerPage$lambda$317$lambda$314 = SettingsActivity.tabletBlinkerPage$lambda$317$lambda$314(SettingsActivity.this);
                return tabletBlinkerPage$lambda$317$lambda$314;
            }
        }));
        CarUi carUi17 = $this$tabletBlinkerPage_u24lambda_u24317.ui;
        if (carUi17 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi = null;
        } else {
            carUi = carUi17;
        }
        TextView button$default = CarUi.button$default(carUi, "Stop replay", false, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda32
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit tabletBlinkerPage$lambda$317$lambda$315;
                tabletBlinkerPage$lambda$317$lambda$315 = SettingsActivity.tabletBlinkerPage$lambda$317$lambda$315();
                return tabletBlinkerPage$lambda$317$lambda$315;
            }
        }, 2, null);
        LinearLayout.LayoutParams $this$tabletBlinkerPage_u24lambda_u24317_u24lambda_u24316 = new LinearLayout.LayoutParams(-2, -2);
        CarUi carUi18 = $this$tabletBlinkerPage_u24lambda_u24317.ui;
        if (carUi18 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi18 = null;
        }
        $this$tabletBlinkerPage_u24lambda_u24317_u24lambda_u24316.setMarginStart(carUi18.dp(10));
        Unit unit = Unit.INSTANCE;
        r.addView(button$default, $this$tabletBlinkerPage_u24lambda_u24317_u24lambda_u24316);
        LinearLayout linearLayout = r;
        CarUi carUi19 = $this$tabletBlinkerPage_u24lambda_u24317.ui;
        if (carUi19 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi19 = null;
        }
        col.addView(linearLayout, CarUi.lp$default(carUi19, 0, 0, 3, null));
        CarUi carUi20 = this.ui;
        if (carUi20 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi20 = null;
        }
        TextView header4 = carUi20.header("Turns");
        CarUi carUi21 = this.ui;
        if (carUi21 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi21 = null;
        }
        col.addView(header4, CarUi.lp$default(carUi21, 0, 0, 3, null));
        toggleCard(col, "Automatic turn signals", "With a destination: signals before each left / right turn of the route - about 12 s ahead, after the last cross street, never under 100 ft - and holds it through red lights until the turn is done. Cancel on the blue card.", new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda35
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean tabletBlinkerPage$lambda$318;
                tabletBlinkerPage$lambda$318 = SettingsActivity.tabletBlinkerPage$lambda$318(s);
                return Boolean.valueOf(tabletBlinkerPage$lambda$318);
            }
        }, new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda36
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit tabletBlinkerPage$lambda$319;
                tabletBlinkerPage$lambda$319 = SettingsActivity.tabletBlinkerPage$lambda$319(s, ((Boolean) obj).booleanValue());
                return tabletBlinkerPage$lambda$319;
            }
        });
        Boolean commaOk = BlinkerTurn.INSTANCE.getCommaOk();
        if (Intrinsics.areEqual((Object) commaOk, (Object) true)) {
            str = " Comma: ready.";
        } else if (Intrinsics.areEqual((Object) commaOk, (Object) false)) {
            str = " Comma: not updated yet.";
        } else {
            if (commaOk != null) {
                throw new NoWhenBranchMatchedException();
            }
            str = "";
        }
        toggleCard(col, "Check the street map for turns (no route)", "StarPilot's Turn Signal Guidance (Steering › Steering and Torque, its on/off) makes your blinker under 20 mph a turn. With this on, the tablet checks its street map first: a turn only when there's a street to turn into on that side within 250 ft (not a one-way coming at you, not a driveway or parking lot) - signalling to pull over isn't a turn. Where the tablet has no map, the comma decides by itself. The blinker card shows \"turn kept\" while there's a street. Needs the comma update from the Oct 23 sync." + str, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda37
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean tabletBlinkerPage$lambda$320;
                tabletBlinkerPage$lambda$320 = SettingsActivity.tabletBlinkerPage$lambda$320(s);
                return Boolean.valueOf(tabletBlinkerPage$lambda$320);
            }
        }, new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda38
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit tabletBlinkerPage$lambda$321;
                tabletBlinkerPage$lambda$321 = SettingsActivity.tabletBlinkerPage$lambda$321(s, ((Boolean) obj).booleanValue());
                return tabletBlinkerPage$lambda$321;
            }
        });
        Boolean cc = LaneSuggestView.Companion.getLastCommaConfirms();
        CarUi carUi22 = this.ui;
        if (carUi22 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi22 = null;
        }
        if (Intrinsics.areEqual((Object) cc, (Object) true)) {
            str2 = "Comma: up to date - it knows a confirmed lane change needs no second nudge and a turn signal is never a lane change.";
        } else if (Intrinsics.areEqual((Object) cc, (Object) false)) {
            str2 = "Comma: needs the StarPilot \"automatic blinker\" update - until then turn signals and automatic mode stay off, and openpilot may want a nudge after you confirm.";
        } else {
            if (cc != null) {
                throw new NoWhenBranchMatchedException();
            }
            str2 = "Comma: not checked yet (it is checked when the tablet connects).";
        }
        TextView note5 = carUi22.note(str2);
        CarUi carUi23 = this.ui;
        if (carUi23 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi23 = null;
        }
        col.addView(note5, CarUi.lp$default(carUi23, 0, 0, 3, null));
        CarUi carUi24 = this.ui;
        if (carUi24 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi24 = null;
        }
        TextView header5 = carUi24.header("What it did lately");
        CarUi carUi25 = this.ui;
        if (carUi25 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi25 = null;
        }
        col.addView(header5, CarUi.lp$default(carUi25, 0, 0, 3, null));
        List rec = BlinkLog.INSTANCE.recent(30);
        CarUi carUi26 = this.ui;
        if (carUi26 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi26 = null;
        }
        TextView note6 = carUi26.note(rec.isEmpty() ? "Nothing yet." : CollectionsKt.joinToString(rec, "\n", "", "", -1, "...", null));
        CarUi carUi27 = this.ui;
        if (carUi27 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi27 = null;
        }
        col.addView(note6, CarUi.lp$default(carUi27, 0, 0, 3, null));
        return sv;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean tabletBlinkerPage$lambda$295(UiSettings $s) {
        return $s.get("lane_suggest");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletBlinkerPage$lambda$296(UiSettings $s, boolean v) {
        $s.set("lane_suggest", v);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean tabletBlinkerPage$lambda$297(UiSettings $s) {
        return $s.get("lane_nudge_confirm");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletBlinkerPage$lambda$298(UiSettings $s, boolean v) {
        $s.set("lane_nudge_confirm", v);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean tabletBlinkerPage$lambda$299(UiSettings $s) {
        return $s.get("exit_signals");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletBlinkerPage$lambda$300(UiSettings $s, boolean v) {
        $s.set("exit_signals", v);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean tabletBlinkerPage$lambda$301(UiSettings $s) {
        return $s.get("lane_pass");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletBlinkerPage$lambda$302(UiSettings $s, boolean v) {
        $s.set("lane_pass", v);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean tabletBlinkerPage$lambda$303(UiSettings $s) {
        return $s.get("lane_ends_ask");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletBlinkerPage$lambda$304(UiSettings $s, boolean v) {
        $s.set("lane_ends_ask", v);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean tabletBlinkerPage$lambda$305(UiSettings $s) {
        return $s.get("lane_return");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletBlinkerPage$lambda$306(UiSettings $s, boolean v) {
        $s.set("lane_return", v);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean tabletBlinkerPage$lambda$307(UiSettings $s) {
        return $s.get("lane_auto");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletBlinkerPage$lambda$308(UiSettings $s, boolean v) {
        $s.set("lane_auto", v);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String tabletBlinkerPage$pctText(float v) {
        return ((int) v) + " %" + (v < 100.0f ? " (lighter)" : v > 100.0f ? " (firmer)" : " (stock)");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletBlinkerPage$lambda$309(UiSettings $s, float v) {
        $s.setFloat("nudge_card_pct", v);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletBlinkerPage$lambda$311(UiSettings $s, float v) {
        $s.setFloat("nudge_lc_pct", v);
        NudgeSync.INSTANCE.push(v);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletBlinkerPage$lambda$317$lambda$314(SettingsActivity $this_run) {
        if (Replay.INSTANCE.getSupported()) {
            Replay.INSTANCE.pick($this_run);
        } else {
            new AlertDialog.Builder($this_run).setMessage("The comma needs StarView's drive replay (comma patch 7) first.").setPositiveButton("OK", (DialogInterface.OnClickListener) null).show();
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletBlinkerPage$lambda$317$lambda$315() {
        Replay.control$default(Replay.INSTANCE, "stop", 0.0d, null, 6, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean tabletBlinkerPage$lambda$318(UiSettings $s) {
        return $s.get("turn_signals");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletBlinkerPage$lambda$319(UiSettings $s, boolean v) {
        $s.set("turn_signals", v);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean tabletBlinkerPage$lambda$320(UiSettings $s) {
        return $s.get("blinker_turn");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletBlinkerPage$lambda$321(UiSettings $s, boolean v) {
        $s.set("blinker_turn", v);
        return Unit.INSTANCE;
    }

    private final View tabletVoicePage() {
        CarUi carUi;
        CarUi carUi2;
        CarUi carUi3;
        CarUi carUi4;
        CarUi carUi5;
        CarUi carUi6;
        Pair<ScrollView, LinearLayout> page = page();
        ScrollView sv = page.component1();
        LinearLayout col = page.component2();
        CarUi carUi7 = this.ui;
        if (carUi7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi7 = null;
        }
        TextView note = carUi7.note("The \"Change lanes?\" card says what it wants, so you don't have to look: \"Change lanes left?\", \"Exit on the right in half a mile. Two lanes to go. Change lanes right?\", then \"Signaling left.\", \"Changing lanes.\", \"Done.\" - or why it stopped (\"Car in the left blind spot. Cancelled.\"). It plays on the tablet's ALARM volume, set below - music and navigation volume don't affect it.");
        CarUi carUi8 = this.ui;
        if (carUi8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi8 = null;
        }
        col.addView(note, CarUi.lp$default(carUi8, 0, 0, 3, null));
        CarUi carUi9 = this.ui;
        if (carUi9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi = null;
        } else {
            carUi = carUi9;
        }
        LinearLayout r0 = (LinearLayout) CarUi.row$default(carUi, "Voice alerts", Speech.INSTANCE.getEnabled() ? "On" : "Off - chimes only", 0, null, null, 28, null).component1();
        CarUi carUi10 = this.ui;
        if (carUi10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi2 = null;
        } else {
            carUi2 = carUi10;
        }
        r0.addView(CarUi.button$default(carUi2, Speech.INSTANCE.getEnabled() ? "Turn off" : "Turn on", false, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda144
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit tabletVoicePage$lambda$322;
                tabletVoicePage$lambda$322 = SettingsActivity.tabletVoicePage$lambda$322(SettingsActivity.this);
                return tabletVoicePage$lambda$322;
            }
        }, 2, null));
        LinearLayout linearLayout = r0;
        CarUi carUi11 = this.ui;
        if (carUi11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi11 = null;
        }
        col.addView(linearLayout, CarUi.lp$default(carUi11, 0, 0, 3, null));
        CarUi carUi12 = this.ui;
        if (carUi12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi12 = null;
        }
        TextView header = carUi12.header("Sound");
        CarUi carUi13 = this.ui;
        if (carUi13 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi13 = null;
        }
        col.addView(header, CarUi.lp$default(carUi13, 0, 0, 3, null));
        slider(col, "Talking speed", 0.6f, 1.8f, 0.05f, Speech.INSTANCE.getSpeed(), new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda171
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                String tabletVoicePage$lambda$323;
                tabletVoicePage$lambda$323 = SettingsActivity.tabletVoicePage$lambda$323(((Float) obj).floatValue());
                return tabletVoicePage$lambda$323;
            }
        }, Float.valueOf(1.0f), new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda172
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit tabletVoicePage$lambda$324;
                tabletVoicePage$lambda$324 = SettingsActivity.tabletVoicePage$lambda$324(((Float) obj).floatValue());
                return tabletVoicePage$lambda$324;
            }
        }, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda173
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit tabletVoicePage$lambda$325;
                tabletVoicePage$lambda$325 = SettingsActivity.tabletVoicePage$lambda$325();
                return tabletVoicePage$lambda$325;
            }
        });
        slider(col, "Pitch", -6.0f, 6.0f, 0.5f, Speech.INSTANCE.getPitch(), new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda174
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                String tabletVoicePage$lambda$326;
                tabletVoicePage$lambda$326 = SettingsActivity.tabletVoicePage$lambda$326(((Float) obj).floatValue());
                return tabletVoicePage$lambda$326;
            }
        }, Float.valueOf(0.0f), new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda175
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit tabletVoicePage$lambda$327;
                tabletVoicePage$lambda$327 = SettingsActivity.tabletVoicePage$lambda$327(((Float) obj).floatValue());
                return tabletVoicePage$lambda$327;
            }
        }, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda176
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit tabletVoicePage$lambda$328;
                tabletVoicePage$lambda$328 = SettingsActivity.tabletVoicePage$lambda$328();
                return tabletVoicePage$lambda$328;
            }
        });
        slider(col, "Chime", 0.0f, 2.0f, 0.05f, Speech.INSTANCE.getChime(), new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda177
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                String tabletVoicePage$lambda$329;
                tabletVoicePage$lambda$329 = SettingsActivity.tabletVoicePage$lambda$329(((Float) obj).floatValue());
                return tabletVoicePage$lambda$329;
            }
        }, Float.valueOf(1.0f), new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda178
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit tabletVoicePage$lambda$330;
                tabletVoicePage$lambda$330 = SettingsActivity.tabletVoicePage$lambda$330(((Float) obj).floatValue());
                return tabletVoicePage$lambda$330;
            }
        }, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda179
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit tabletVoicePage$lambda$331;
                tabletVoicePage$lambda$331 = SettingsActivity.tabletVoicePage$lambda$331();
                return tabletVoicePage$lambda$331;
            }
        });
        slider(col, "Background music", 0.0f, 2.0f, 0.05f, Speech.INSTANCE.getPad(), new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda155
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                String tabletVoicePage$lambda$332;
                tabletVoicePage$lambda$332 = SettingsActivity.tabletVoicePage$lambda$332(((Float) obj).floatValue());
                return tabletVoicePage$lambda$332;
            }
        }, Float.valueOf(1.0f), new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda163
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit tabletVoicePage$lambda$333;
                tabletVoicePage$lambda$333 = SettingsActivity.tabletVoicePage$lambda$333(((Float) obj).floatValue());
                return tabletVoicePage$lambda$333;
            }
        }, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda164
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit tabletVoicePage$lambda$334;
                tabletVoicePage$lambda$334 = SettingsActivity.tabletVoicePage$lambda$334();
                return tabletVoicePage$lambda$334;
            }
        });
        CarUi carUi14 = this.ui;
        if (carUi14 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi14 = null;
        }
        TextView note2 = carUi14.note("Speed, pitch, chime and music change instantly, for every voice. The tablet's own voices below only follow the speed.");
        CarUi carUi15 = this.ui;
        if (carUi15 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi15 = null;
        }
        col.addView(note2, CarUi.lp$default(carUi15, 0, 0, 3, null));
        Object systemService = getSystemService("audio");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.media.AudioManager");
        final AudioManager am = (AudioManager) systemService;
        final int st = 4;
        final int maxV = am.getStreamMaxVolume(4);
        CarUi carUi16 = this.ui;
        if (carUi16 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi3 = null;
        } else {
            carUi3 = carUi16;
        }
        Pair row$default = CarUi.row$default(carUi3, "Volume", "…", 0, null, null, 28, null);
        LinearLayout r2 = (LinearLayout) row$default.component1();
        final TextView d2 = (TextView) row$default.component2();
        CarUi carUi17 = this.ui;
        if (carUi17 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi4 = null;
        } else {
            carUi4 = carUi17;
        }
        r2.addView(CarUi.button$default(carUi4, "−", false, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda165
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit tabletVoicePage$lambda$335;
                tabletVoicePage$lambda$335 = SettingsActivity.tabletVoicePage$lambda$335(am, st, maxV, this, d2);
                return tabletVoicePage$lambda$335;
            }
        }, 2, null));
        View view = new View(this);
        CarUi carUi18 = this.ui;
        if (carUi18 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi18 = null;
        }
        r2.addView(view, new LinearLayout.LayoutParams(carUi18.dp(8), 1));
        CarUi carUi19 = this.ui;
        if (carUi19 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi5 = null;
        } else {
            carUi5 = carUi19;
        }
        r2.addView(CarUi.button$default(carUi5, "+", false, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda166
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit tabletVoicePage$lambda$336;
                tabletVoicePage$lambda$336 = SettingsActivity.tabletVoicePage$lambda$336(am, st, maxV, this, d2);
                return tabletVoicePage$lambda$336;
            }
        }, 2, null));
        View view2 = new View(this);
        CarUi carUi20 = this.ui;
        if (carUi20 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi20 = null;
        }
        r2.addView(view2, new LinearLayout.LayoutParams(carUi20.dp(8), 1));
        CarUi carUi21 = this.ui;
        if (carUi21 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi6 = null;
        } else {
            carUi6 = carUi21;
        }
        r2.addView(CarUi.button$default(carUi6, "Max", false, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda167
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit tabletVoicePage$lambda$337;
                tabletVoicePage$lambda$337 = SettingsActivity.tabletVoicePage$lambda$337(maxV, am, st, this, d2);
                return tabletVoicePage$lambda$337;
            }
        }, 2, null));
        tabletVoicePage$showV(am, 4, d2, maxV);
        LinearLayout linearLayout2 = r2;
        CarUi carUi22 = this.ui;
        if (carUi22 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi22 = null;
        }
        col.addView(linearLayout2, CarUi.lp$default(carUi22, 0, 0, 3, null));
        CarUi carUi23 = this.ui;
        if (carUi23 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi23 = null;
        }
        TextView header2 = carUi23.header("Voice");
        CarUi carUi24 = this.ui;
        if (carUi24 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi24 = null;
        }
        col.addView(header2, CarUi.lp$default(carUi24, 0, 0, 3, null));
        final LinearLayout list = new LinearLayout(this);
        list.setOrientation(1);
        LinearLayout linearLayout3 = list;
        CarUi carUi25 = this.ui;
        if (carUi25 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi25 = null;
        }
        col.addView(linearLayout3, CarUi.lp$default(carUi25, 0, 0, 3, null));
        final String sampleText = "Exit on the right in half a mile, two lanes to go. Change lanes right?";
        Speech.INSTANCE.init(this, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda168
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit tabletVoicePage$lambda$353;
                tabletVoicePage$lambda$353 = SettingsActivity.tabletVoicePage$lambda$353(SettingsActivity.this, list, sampleText);
                return tabletVoicePage$lambda$353;
            }
        });
        tabletVoicePage$fill(list, this, "Exit on the right in half a mile, two lanes to go. Change lanes right?");
        sv.setTag(new Runnable() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda170
            @Override // java.lang.Runnable
            public final void run() {
                SettingsActivity.tabletVoicePage$fill(list, this, sampleText);
            }
        });
        return sv;
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

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletVoicePage$lambda$322(SettingsActivity this$0) {
        Speech.INSTANCE.setEnabled(!Speech.INSTANCE.getEnabled());
        this$0.panels.remove("tablet:voice");
        this$0.open("tablet:voice");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String tabletVoicePage$lambda$323(float v) {
        String format = String.format("%.2fx", Arrays.copyOf(new Object[]{Float.valueOf(v)}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format + (Math.abs(v - 1.0f) < 0.01f ? " - normal" : v < 1.0f ? " - slower" : " - faster");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletVoicePage$lambda$324(float it) {
        Speech.INSTANCE.setSpeed(it);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletVoicePage$lambda$325() {
        tabletVoicePage$sample();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String tabletVoicePage$lambda$326(float v) {
        if (Math.abs(v) < 0.01f) {
            return "as recorded";
        }
        String str = v > 0.0f ? "+" : "";
        String format = String.format("%.1f semitones", Arrays.copyOf(new Object[]{Float.valueOf(v)}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return str + format + (v > 0.0f ? " - higher" : " - lower");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletVoicePage$lambda$327(float it) {
        Speech.INSTANCE.setPitch(it);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletVoicePage$lambda$328() {
        tabletVoicePage$sample();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String tabletVoicePage$lambda$329(float v) {
        if (v < 0.01f) {
            return "off";
        }
        return Math.round(100 * v) + "%";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletVoicePage$lambda$330(float it) {
        Speech.INSTANCE.setChime(it);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletVoicePage$lambda$331() {
        tabletVoicePage$sampleFull();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String tabletVoicePage$lambda$332(float v) {
        if (v < 0.01f) {
            return "off";
        }
        return Math.round(100 * v) + "%";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletVoicePage$lambda$333(float it) {
        Speech.INSTANCE.setPad(it);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletVoicePage$lambda$334() {
        tabletVoicePage$sampleFull();
        return Unit.INSTANCE;
    }

    private static final void tabletVoicePage$showV(AudioManager am, int st, TextView d2, int maxV) {
        int v = am.getStreamVolume(st);
        if (d2 != null) {
            d2.setText(v + " of " + maxV + (v == 0 ? " - muted, you won't hear the alerts" : ""));
        }
    }

    private static final void tabletVoicePage$setV(AudioManager am, int st, int maxV, SettingsActivity this$0, TextView d2, int v) {
        try {
            am.setStreamVolume(st, RangesKt.coerceIn(v, 0, maxV), 0);
        } catch (Exception e) {
            Toast.makeText(this$0, "Can't change it here (Do Not Disturb?) - use the tablet's volume buttons", 1).show();
        }
        tabletVoicePage$showV(am, st, d2, maxV);
        tabletVoicePage$sample();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletVoicePage$lambda$335(AudioManager $am, int $st, int $maxV, SettingsActivity this$0, TextView $d2) {
        tabletVoicePage$setV($am, $st, $maxV, this$0, $d2, $am.getStreamVolume($st) - 1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletVoicePage$lambda$336(AudioManager $am, int $st, int $maxV, SettingsActivity this$0, TextView $d2) {
        tabletVoicePage$setV($am, $st, $maxV, this$0, $d2, $am.getStreamVolume($st) + 1);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletVoicePage$lambda$337(int $maxV, AudioManager $am, int $st, SettingsActivity this$0, TextView $d2) {
        tabletVoicePage$setV($am, $st, $maxV, this$0, $d2, $maxV);
        return Unit.INSTANCE;
    }

    private static final View tabletVoicePage$gap(SettingsActivity this$0) {
        View it = new View(this$0);
        CarUi carUi = this$0.ui;
        if (carUi == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi = null;
        }
        it.setLayoutParams(new LinearLayout.LayoutParams(carUi.dp(8), 1));
        return it;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void tabletVoicePage$fill(final LinearLayout list, SettingsActivity this$0, String sampleText) {
        boolean z;
        CarUi carUi;
        CarUi carUi2;
        CarUi carUi3;
        CarUi carUi4;
        CarUi carUi5;
        CarUi carUi6;
        CarUi carUi7;
        final SettingsActivity settingsActivity = this$0;
        final String str = sampleText;
        list.removeAllViews();
        List packs = AmyVoice.INSTANCE.packs();
        AmyVoice.Pack act = Speech.INSTANCE.getUseAmy() ? AmyVoice.INSTANCE.active() : null;
        Iterator<AmyVoice.Pack> it = packs.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            final AmyVoice.Pack p = it.next();
            boolean chosen = Intrinsics.areEqual(act != null ? act.getId() : null, p.getId());
            CarUi carUi8 = settingsActivity.ui;
            if (carUi8 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi5 = null;
            } else {
                carUi5 = carUi8;
            }
            LinearLayout r = (LinearLayout) CarUi.row$default(carUi5, (chosen ? "✓ " : "") + p.getLabel(), (Intrinsics.areEqual(p.getId(), "amy") ? "Recorded on the PC with Piper. " : "Made on this tablet in Voice studio. ") + AmyVoice.INSTANCE.recordings(p) + " phrases, works offline.", 0, null, null, 28, null).component1();
            CarUi carUi9 = settingsActivity.ui;
            if (carUi9 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi6 = null;
            } else {
                carUi6 = carUi9;
            }
            r.addView(CarUi.button$default(carUi6, "▶ Play", false, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda123
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit tabletVoicePage$fill$lambda$340;
                    tabletVoicePage$fill$lambda$340 = SettingsActivity.tabletVoicePage$fill$lambda$340(p, list, settingsActivity, str);
                    return tabletVoicePage$fill$lambda$340;
                }
            }, 2, null));
            r.addView(tabletVoicePage$gap(this$0));
            CarUi carUi10 = settingsActivity.ui;
            if (carUi10 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi10 = null;
            }
            r.addView(carUi10.button(chosen ? "In use" : "Use", chosen, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda124
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit tabletVoicePage$fill$lambda$341;
                    tabletVoicePage$fill$lambda$341 = SettingsActivity.tabletVoicePage$fill$lambda$341(p, list, settingsActivity, str);
                    return tabletVoicePage$fill$lambda$341;
                }
            }));
            r.addView(tabletVoicePage$gap(this$0));
            CarUi carUi11 = settingsActivity.ui;
            if (carUi11 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi7 = null;
            } else {
                carUi7 = carUi11;
            }
            r.addView(CarUi.button$default(carUi7, "Delete", false, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda125
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit tabletVoicePage$fill$lambda$343;
                    tabletVoicePage$fill$lambda$343 = SettingsActivity.tabletVoicePage$fill$lambda$343(this$0, p, list, str);
                    return tabletVoicePage$fill$lambda$343;
                }
            }, 2, null));
            LinearLayout linearLayout = r;
            CarUi carUi12 = settingsActivity.ui;
            if (carUi12 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi12 = null;
            }
            list.addView(linearLayout, CarUi.lp$default(carUi12, 0, 0, 3, null));
        }
        List $this$none$iv = packs;
        if (!($this$none$iv instanceof Collection) || !$this$none$iv.isEmpty()) {
            Iterator it2 = $this$none$iv.iterator();
            while (true) {
                if (it2.hasNext()) {
                    Object element$iv = it2.next();
                    AmyVoice.Pack it3 = (AmyVoice.Pack) element$iv;
                    if (Intrinsics.areEqual(it3.getId(), "amy")) {
                        z = false;
                        break;
                    }
                } else {
                    z = true;
                    break;
                }
            }
        } else {
            z = true;
        }
        if (z) {
            CarUi carUi13 = settingsActivity.ui;
            if (carUi13 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi4 = null;
            } else {
                carUi4 = carUi13;
            }
            LinearLayout ra = (LinearLayout) CarUi.row$default(carUi4, "Amy · recorded on the PC", "Not installed - tap Install and pick StarView-Amy-voice.zip (from Drive or Downloads).", 0, null, null, 28, null).component1();
            CarUi carUi14 = settingsActivity.ui;
            if (carUi14 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi14 = null;
            }
            ra.addView(carUi14.button("Install…", packs.isEmpty(), new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda126
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit tabletVoicePage$fill$lambda$348;
                    tabletVoicePage$fill$lambda$348 = SettingsActivity.tabletVoicePage$fill$lambda$348(this$0, list, str);
                    return tabletVoicePage$fill$lambda$348;
                }
            }));
            LinearLayout linearLayout2 = ra;
            CarUi carUi15 = settingsActivity.ui;
            if (carUi15 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi15 = null;
            }
            list.addView(linearLayout2, CarUi.lp$default(carUi15, 0, 0, 3, null));
        }
        CarUi carUi16 = settingsActivity.ui;
        if (carUi16 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi = null;
        } else {
            carUi = carUi16;
        }
        LinearLayout rs = (LinearLayout) CarUi.row$default(carUi, "Make a new voice", "Voice studio: pick any of 38 English voices (some have hundreds of different speakers), adjust it, and the tablet records every phrase in it.", 0, null, null, 28, null).component1();
        CarUi carUi17 = settingsActivity.ui;
        if (carUi17 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi17 = null;
        }
        rs.addView(carUi17.button("Voice studio ›", true, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda127
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit tabletVoicePage$fill$lambda$349;
                tabletVoicePage$fill$lambda$349 = SettingsActivity.tabletVoicePage$fill$lambda$349(this$0);
                return tabletVoicePage$fill$lambda$349;
            }
        }));
        LinearLayout linearLayout3 = rs;
        CarUi carUi18 = settingsActivity.ui;
        if (carUi18 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi18 = null;
        }
        list.addView(linearLayout3, CarUi.lp$default(carUi18, 0, 0, 3, null));
        CarUi carUi19 = settingsActivity.ui;
        if (carUi19 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi19 = null;
        }
        TextView note = carUi19.note("The tablet's own voices (no chime):");
        CarUi carUi20 = settingsActivity.ui;
        if (carUi20 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi20 = null;
        }
        list.addView(note, CarUi.lp$default(carUi20, 0, 0, 3, null));
        List vs = Speech.INSTANCE.voices();
        if (vs.isEmpty()) {
            CarUi carUi21 = settingsActivity.ui;
            if (carUi21 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi21 = null;
            }
            TextView note2 = carUi21.note(Speech.INSTANCE.getReady() ? "No English voices installed - add one in Android Settings › Text-to-speech." : "Loading the tablet's voices…");
            CarUi carUi22 = settingsActivity.ui;
            if (carUi22 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi22 = null;
            }
            list.addView(note2, CarUi.lp$default(carUi22, 0, 0, 3, null));
            return;
        }
        for (final Voice v : vs) {
            boolean chosen2 = act == null && Intrinsics.areEqual(v.getName(), Speech.INSTANCE.getVoiceName());
            CarUi carUi23 = settingsActivity.ui;
            if (carUi23 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi2 = null;
            } else {
                carUi2 = carUi23;
            }
            List packs2 = packs;
            AmyVoice.Pack act2 = act;
            LinearLayout r2 = (LinearLayout) CarUi.row$default(carUi2, (chosen2 ? "✓ " : "") + Speech.INSTANCE.label(v), v.getName(), 0, null, null, 28, null).component1();
            CarUi carUi24 = settingsActivity.ui;
            if (carUi24 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi3 = null;
            } else {
                carUi3 = carUi24;
            }
            r2.addView(CarUi.button$default(carUi3, "▶ Play", false, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda128
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit tabletVoicePage$fill$lambda$350;
                    tabletVoicePage$fill$lambda$350 = SettingsActivity.tabletVoicePage$fill$lambda$350(v, str);
                    return tabletVoicePage$fill$lambda$350;
                }
            }, 2, null));
            r2.addView(tabletVoicePage$gap(this$0));
            CarUi carUi25 = settingsActivity.ui;
            if (carUi25 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi25 = null;
            }
            LinearLayout rs2 = rs;
            r2.addView(carUi25.button(chosen2 ? "In use" : "Use", chosen2, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda129
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit tabletVoicePage$fill$lambda$351;
                    tabletVoicePage$fill$lambda$351 = SettingsActivity.tabletVoicePage$fill$lambda$351(v, list, settingsActivity, str);
                    return tabletVoicePage$fill$lambda$351;
                }
            }));
            LinearLayout linearLayout4 = r2;
            CarUi carUi26 = settingsActivity.ui;
            if (carUi26 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi26 = null;
            }
            list.addView(linearLayout4, CarUi.lp$default(carUi26, 0, 0, 3, null));
            str = sampleText;
            packs = packs2;
            act = act2;
            rs = rs2;
            settingsActivity = this$0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletVoicePage$fill$lambda$340(AmyVoice.Pack $p, LinearLayout $list, SettingsActivity this$0, String $sampleText) {
        AmyVoice.INSTANCE.use($p.getId());
        AmyVoice.INSTANCE.demo();
        tabletVoicePage$fill($list, this$0, $sampleText);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletVoicePage$fill$lambda$341(AmyVoice.Pack $p, LinearLayout $list, SettingsActivity this$0, String $sampleText) {
        AmyVoice.INSTANCE.use($p.getId());
        tabletVoicePage$sample();
        tabletVoicePage$fill($list, this$0, $sampleText);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletVoicePage$fill$lambda$343(final SettingsActivity this$0, final AmyVoice.Pack $p, final LinearLayout $list, final String $sampleText) {
        new AlertDialog.Builder(this$0).setMessage("Delete the voice \"" + $p.getLabel() + "\"?").setPositiveButton("Delete", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda116
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                SettingsActivity.tabletVoicePage$fill$lambda$343$lambda$342($p, $list, this$0, $sampleText, dialogInterface, i);
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void tabletVoicePage$fill$lambda$343$lambda$342(AmyVoice.Pack $p, LinearLayout $list, SettingsActivity this$0, String $sampleText, DialogInterface dialogInterface, int i) {
        AmyVoice.INSTANCE.stop();
        AmyVoice.INSTANCE.delete($p.getId());
        tabletVoicePage$fill($list, this$0, $sampleText);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletVoicePage$fill$lambda$348(final SettingsActivity this$0, final LinearLayout $list, final String $sampleText) {
        this$0.pickFile(new String[]{"application/zip", "application/x-zip-compressed", "application/octet-stream", "*/*"}, new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda169
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit tabletVoicePage$fill$lambda$348$lambda$347;
                tabletVoicePage$fill$lambda$348$lambda$347 = SettingsActivity.tabletVoicePage$fill$lambda$348$lambda$347(this$0, $list, $sampleText, (Uri) obj);
                return tabletVoicePage$fill$lambda$348$lambda$347;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletVoicePage$fill$lambda$348$lambda$347(final SettingsActivity this$0, final LinearLayout $list, final String $sampleText, final Uri uri) {
        if (uri == null) {
            return Unit.INSTANCE;
        }
        Toast.makeText(this$0, "Installing Amy's voice…", 0).show();
        new Thread(new Runnable() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda222
            @Override // java.lang.Runnable
            public final void run() {
                SettingsActivity.tabletVoicePage$fill$lambda$348$lambda$347$lambda$346(this$0, uri, $list, $sampleText);
            }
        }).start();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void tabletVoicePage$fill$lambda$348$lambda$347$lambda$346(final SettingsActivity this$0, Uri $uri, final LinearLayout $list, final String $sampleText) {
        final String err = AmyVoice.INSTANCE.install(this$0, $uri);
        this$0.runOnUiThread(new Runnable() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda243
            @Override // java.lang.Runnable
            public final void run() {
                SettingsActivity.tabletVoicePage$fill$lambda$348$lambda$347$lambda$346$lambda$345(this$0, err, $list, $sampleText);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void tabletVoicePage$fill$lambda$348$lambda$347$lambda$346$lambda$345(SettingsActivity this$0, String $err, LinearLayout $list, String $sampleText) {
        Toast.makeText(this$0, $err == null ? "Amy installed" : $err, 1).show();
        if ($err == null) {
            tabletVoicePage$sampleFull();
        }
        tabletVoicePage$fill($list, this$0, $sampleText);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletVoicePage$fill$lambda$349(SettingsActivity this$0) {
        this$0.open("tablet:voicestudio");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletVoicePage$fill$lambda$350(Voice $v, String $sampleText) {
        AmyVoice.INSTANCE.stop();
        Speech.INSTANCE.preview($v, $sampleText);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletVoicePage$fill$lambda$351(Voice $v, LinearLayout $list, SettingsActivity this$0, String $sampleText) {
        Speech.INSTANCE.setVoiceName($v.getName());
        Speech.speak$default(Speech.INSTANCE, "Change lanes left?", false, 2, null);
        tabletVoicePage$fill($list, this$0, $sampleText);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletVoicePage$lambda$353(final SettingsActivity this$0, final LinearLayout $list, final String $sampleText) {
        this$0.runOnUiThread(new Runnable() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                SettingsActivity.tabletVoicePage$fill($list, this$0, $sampleText);
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v4, types: [T, com.starpilot.starview.VoiceStudio$Style] */
    /* JADX WARN: Type inference failed for: r7v18, types: [T, java.util.List] */
    /* JADX WARN: Type inference failed for: r8v8, types: [T, com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda220] */
    private final View tabletVoiceStudioPage() {
        CarUi carUi;
        CarUi carUi2;
        CarUi carUi3;
        Function0 function0;
        Pair<ScrollView, LinearLayout> page = page();
        ScrollView component1 = page.component1();
        LinearLayout component2 = page.component2();
        final SharedPreferences sharedPreferences = getSharedPreferences("speech", 0);
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = new VoiceStudio.Style(sharedPreferences.getFloat("st_expr", 0.667f), sharedPreferences.getFloat("st_rhythm", 0.8f), 1.08f);
        final Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
        String string = sharedPreferences.getString("st_filter", "all");
        objectRef2.element = string != null ? string : "all";
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        CarUi carUi4 = this.ui;
        if (carUi4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi4 = null;
        }
        TextView note = carUi4.note("Pick a voice, download it, ▶ Try it, then \"Make my voice\": the tablet speaks all 60 lane-change phrases in it (about a minute) and the card uses it from then on - with your chime, music, speed and pitch. Voices with many speakers have a speaker slider: each number is a different person.");
        CarUi carUi5 = this.ui;
        if (carUi5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi5 = null;
        }
        component2.addView(note, CarUi.lp$default(carUi5, 0, 0, 3, null));
        CarUi carUi6 = this.ui;
        if (carUi6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi = null;
        } else {
            carUi = carUi6;
        }
        Pair row$default = CarUi.row$default(carUi, "Status", "…", 0, null, null, 28, null);
        LinearLayout linearLayout = (LinearLayout) row$default.component1();
        final TextView textView = (TextView) row$default.component2();
        CarUi carUi7 = this.ui;
        if (carUi7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi2 = null;
        } else {
            carUi2 = carUi7;
        }
        final TextView button$default = CarUi.button$default(carUi2, "Stop", false, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda212
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit tabletVoiceStudioPage$lambda$355;
                tabletVoiceStudioPage$lambda$355 = SettingsActivity.tabletVoiceStudioPage$lambda$355(atomicBoolean);
                return tabletVoiceStudioPage$lambda$355;
            }
        }, 2, null);
        button$default.setVisibility(8);
        linearLayout.addView(button$default);
        LinearLayout linearLayout2 = linearLayout;
        CarUi carUi8 = this.ui;
        if (carUi8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi8 = null;
        }
        component2.addView(linearLayout2, CarUi.lp$default(carUi8, 0, 0, 3, null));
        LinearLayout linearLayout3 = new LinearLayout(this);
        linearLayout3.setOrientation(1);
        LinearLayout linearLayout4 = linearLayout3;
        CarUi carUi9 = this.ui;
        if (carUi9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi9 = null;
        }
        component2.addView(linearLayout4, CarUi.lp$default(carUi9, 0, 0, 3, null));
        final Ref.ObjectRef objectRef3 = new Ref.ObjectRef();
        CarUi carUi10 = this.ui;
        if (carUi10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi10 = null;
        }
        TextView header = carUi10.header("How it speaks");
        CarUi carUi11 = this.ui;
        if (carUi11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi11 = null;
        }
        component2.addView(header, CarUi.lp$default(carUi11, 0, 0, 3, null));
        slider(component2, "Expressive", 0.1f, 1.2f, 0.05f, ((VoiceStudio.Style) objectRef.element).getExpressive(), new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda214
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                String tabletVoiceStudioPage$lambda$364;
                tabletVoiceStudioPage$lambda$364 = SettingsActivity.tabletVoiceStudioPage$lambda$364(((Float) obj).floatValue());
                return tabletVoiceStudioPage$lambda$364;
            }
        }, Float.valueOf(0.667f), new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda215
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit tabletVoiceStudioPage$lambda$365;
                tabletVoiceStudioPage$lambda$365 = SettingsActivity.tabletVoiceStudioPage$lambda$365(objectRef3, sharedPreferences, ((Float) obj).floatValue());
                return tabletVoiceStudioPage$lambda$365;
            }
        }, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda216
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit unit;
                unit = Unit.INSTANCE;
                return unit;
            }
        });
        slider(component2, "Rhythm variation", 0.1f, 1.4f, 0.05f, ((VoiceStudio.Style) objectRef.element).getRhythm(), new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda217
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                String tabletVoiceStudioPage$lambda$367;
                tabletVoiceStudioPage$lambda$367 = SettingsActivity.tabletVoiceStudioPage$lambda$367(((Float) obj).floatValue());
                return tabletVoiceStudioPage$lambda$367;
            }
        }, Float.valueOf(0.8f), new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda218
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit tabletVoiceStudioPage$lambda$368;
                tabletVoiceStudioPage$lambda$368 = SettingsActivity.tabletVoiceStudioPage$lambda$368(objectRef3, sharedPreferences, ((Float) obj).floatValue());
                return tabletVoiceStudioPage$lambda$368;
            }
        }, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda219
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit unit;
                unit = Unit.INSTANCE;
                return unit;
            }
        });
        CarUi carUi12 = this.ui;
        if (carUi12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi12 = null;
        }
        TextView note2 = carUi12.note("These two are baked in when the voice is made - ▶ Try after changing them. Speed, pitch, chime and music are on the Voice alerts page and change any time.");
        CarUi carUi13 = this.ui;
        if (carUi13 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi13 = null;
        }
        component2.addView(note2, CarUi.lp$default(carUi13, 0, 0, 3, null));
        CarUi carUi14 = this.ui;
        if (carUi14 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi14 = null;
        }
        TextView header2 = carUi14.header("Voices");
        CarUi carUi15 = this.ui;
        if (carUi15 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi15 = null;
        }
        component2.addView(header2, CarUi.lp$default(carUi15, 0, 0, 3, null));
        LinearLayout linearLayout5 = new LinearLayout(this);
        linearLayout5.setOrientation(1);
        LinearLayout linearLayout6 = linearLayout5;
        CarUi carUi16 = this.ui;
        if (carUi16 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi16 = null;
        }
        component2.addView(linearLayout6, CarUi.lp$default(carUi16, 0, 0, 3, null));
        tabletVoiceStudioPage$fillFilter(linearLayout5, this, objectRef2, sharedPreferences, objectRef3);
        final LinearLayout linearLayout7 = new LinearLayout(this);
        linearLayout7.setOrientation(1);
        LinearLayout linearLayout8 = linearLayout7;
        CarUi carUi17 = this.ui;
        if (carUi17 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi17 = null;
        }
        component2.addView(linearLayout8, CarUi.lp$default(carUi17, 0, 0, 3, null));
        final Ref.ObjectRef objectRef4 = new Ref.ObjectRef();
        objectRef4.element = CollectionsKt.emptyList();
        final List listOf = CollectionsKt.listOf((Object[]) new String[]{"Exit on the right in half a mile.", "Two lanes to go. Change lanes right?"});
        objectRef3.element = new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda220
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit tabletVoiceStudioPage$lambda$397;
                tabletVoiceStudioPage$lambda$397 = SettingsActivity.tabletVoiceStudioPage$lambda$397(linearLayout7, this, objectRef4, sharedPreferences, objectRef2, atomicBoolean, textView, booleanRef, button$default, objectRef3, objectRef, listOf);
                return tabletVoiceStudioPage$lambda$397;
            }
        };
        View view = new View(this);
        CarUi carUi18 = this.ui;
        if (carUi18 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi18 = null;
        }
        linearLayout.addView(view, new LinearLayout.LayoutParams(carUi18.dp(8), 1));
        CarUi carUi19 = this.ui;
        if (carUi19 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi3 = null;
        } else {
            carUi3 = carUi19;
        }
        linearLayout.addView(CarUi.button$default(carUi3, "Refresh list", false, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda221
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit tabletVoiceStudioPage$lambda$400;
                tabletVoiceStudioPage$lambda$400 = SettingsActivity.tabletVoiceStudioPage$lambda$400(objectRef3, this, textView, objectRef3);
                return tabletVoiceStudioPage$lambda$400;
            }
        }, 2, null));
        tabletVoiceStudioPage$fillEngine(linearLayout3, this, textView, objectRef3);
        if (objectRef3.element == 0) {
            Intrinsics.throwUninitializedPropertyAccessException("fillVoices");
            function0 = null;
        } else {
            function0 = (Function0) objectRef3.element;
        }
        function0.invoke();
        tabletVoiceStudioPage$loadCat(objectRef4, this, textView, objectRef3, false);
        return component1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletVoiceStudioPage$lambda$355(AtomicBoolean $cancel) {
        $cancel.set(true);
        return Unit.INSTANCE;
    }

    private static final void tabletVoiceStudioPage$say(SettingsActivity this$0, final TextView status, final String t) {
        this$0.runOnUiThread(new Runnable() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SettingsActivity.tabletVoiceStudioPage$say$lambda$357(status, t);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void tabletVoiceStudioPage$say$lambda$357(TextView $status, String $t) {
        if ($status != null) {
            $status.setText($t);
        }
    }

    private static final void tabletVoiceStudioPage$fillEngine(final LinearLayout engineBox, final SettingsActivity this$0, final TextView status, final Ref.ObjectRef<Function0<Unit>> objectRef) {
        CarUi carUi;
        engineBox.removeAllViews();
        boolean inst = VoiceStudio.INSTANCE.engineInstalled(this$0);
        CarUi carUi2 = this$0.ui;
        if (carUi2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi = null;
        } else {
            carUi = carUi2;
        }
        LinearLayout r = (LinearLayout) CarUi.row$default(carUi, "Voice engine", inst ? "Installed (sherpa-onnx, runs on the tablet, offline)" : "Not installed - tap Install and pick StarView-Voice-engine.zip (about 18 MB, from Drive or Downloads). Needed once.", 0, null, null, 28, null).component1();
        if (!inst) {
            CarUi carUi3 = this$0.ui;
            if (carUi3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi3 = null;
            }
            r.addView(carUi3.button("Install…", true, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda119
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit tabletVoiceStudioPage$fillEngine$lambda$363;
                    tabletVoiceStudioPage$fillEngine$lambda$363 = SettingsActivity.tabletVoiceStudioPage$fillEngine$lambda$363(this$0, status, objectRef, engineBox);
                    return tabletVoiceStudioPage$fillEngine$lambda$363;
                }
            }));
        }
        LinearLayout linearLayout = r;
        CarUi carUi4 = this$0.ui;
        if (carUi4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi4 = null;
        }
        engineBox.addView(linearLayout, CarUi.lp$default(carUi4, 0, 0, 3, null));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletVoiceStudioPage$fillEngine$lambda$363(final SettingsActivity this$0, final TextView $status, final Ref.ObjectRef $fillVoices, final LinearLayout $engineBox) {
        this$0.pickFile(new String[]{"application/zip", "application/x-zip-compressed", "application/octet-stream", "*/*"}, new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda22
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit tabletVoiceStudioPage$fillEngine$lambda$363$lambda$362;
                tabletVoiceStudioPage$fillEngine$lambda$363$lambda$362 = SettingsActivity.tabletVoiceStudioPage$fillEngine$lambda$363$lambda$362(this$0, $status, $fillVoices, $engineBox, (Uri) obj);
                return tabletVoiceStudioPage$fillEngine$lambda$363$lambda$362;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletVoiceStudioPage$fillEngine$lambda$363$lambda$362(final SettingsActivity this$0, final TextView $status, final Ref.ObjectRef $fillVoices, final LinearLayout $engineBox, final Uri uri) {
        if (uri == null) {
            return Unit.INSTANCE;
        }
        tabletVoiceStudioPage$say(this$0, $status, "Installing the voice engine…");
        new Thread(new Runnable() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda186
            @Override // java.lang.Runnable
            public final void run() {
                SettingsActivity.tabletVoiceStudioPage$fillEngine$lambda$363$lambda$362$lambda$361(this$0, uri, $status, $fillVoices, $engineBox);
            }
        }).start();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void tabletVoiceStudioPage$fillEngine$lambda$363$lambda$362$lambda$361(final SettingsActivity this$0, Uri $uri, final TextView $status, final Ref.ObjectRef $fillVoices, final LinearLayout $engineBox) {
        String err = VoiceStudio.INSTANCE.installEngine(this$0, $uri, new Function1() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda55
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit tabletVoiceStudioPage$fillEngine$lambda$363$lambda$362$lambda$361$lambda$359;
                tabletVoiceStudioPage$fillEngine$lambda$363$lambda$362$lambda$361$lambda$359 = SettingsActivity.tabletVoiceStudioPage$fillEngine$lambda$363$lambda$362$lambda$361$lambda$359(this$0, $status, (String) obj);
                return tabletVoiceStudioPage$fillEngine$lambda$363$lambda$362$lambda$361$lambda$359;
            }
        });
        tabletVoiceStudioPage$say(this$0, $status, err == null ? "Voice engine installed" : err);
        this$0.runOnUiThread(new Runnable() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda66
            @Override // java.lang.Runnable
            public final void run() {
                SettingsActivity.tabletVoiceStudioPage$fillEngine$lambda$363$lambda$362$lambda$361$lambda$360($fillVoices, $engineBox, this$0, $status);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletVoiceStudioPage$fillEngine$lambda$363$lambda$362$lambda$361$lambda$359(SettingsActivity this$0, TextView $status, String it) {
        Intrinsics.checkNotNullParameter(it, "it");
        tabletVoiceStudioPage$say(this$0, $status, it);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void tabletVoiceStudioPage$fillEngine$lambda$363$lambda$362$lambda$361$lambda$360(Ref.ObjectRef $fillVoices, LinearLayout $engineBox, SettingsActivity this$0, TextView $status) {
        Function0 function0;
        tabletVoiceStudioPage$fillEngine($engineBox, this$0, $status, $fillVoices);
        if ($fillVoices.element == 0) {
            Intrinsics.throwUninitializedPropertyAccessException("fillVoices");
            function0 = null;
        } else {
            function0 = (Function0) $fillVoices.element;
        }
        function0.invoke();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String tabletVoiceStudioPage$lambda$364(float v) {
        String format = String.format("%.2f", Arrays.copyOf(new Object[]{Float.valueOf(v)}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format + (v < 0.45f ? " - flat, calm" : v > 0.85f ? " - lively" : "");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [T, com.starpilot.starview.VoiceStudio$Style] */
    public static final Unit tabletVoiceStudioPage$lambda$365(Ref.ObjectRef $style, SharedPreferences $sp, float it) {
        $style.element = VoiceStudio.Style.copy$default((VoiceStudio.Style) $style.element, it, 0.0f, 0.0f, 6, null);
        $sp.edit().putFloat("st_expr", it).apply();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String tabletVoiceStudioPage$lambda$367(float v) {
        String format = String.format("%.2f", Arrays.copyOf(new Object[]{Float.valueOf(v)}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format + (v < 0.45f ? " - even, steady" : v > 1.0f ? " - loose" : "");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [T, com.starpilot.starview.VoiceStudio$Style] */
    public static final Unit tabletVoiceStudioPage$lambda$368(Ref.ObjectRef $style, SharedPreferences $sp, float it) {
        $style.element = VoiceStudio.Style.copy$default((VoiceStudio.Style) $style.element, 0.0f, it, 0.0f, 5, null);
        $sp.edit().putFloat("st_rhythm", it).apply();
        return Unit.INSTANCE;
    }

    private static final void tabletVoiceStudioPage$fillFilter(final LinearLayout filterBox, final SettingsActivity this$0, final Ref.ObjectRef<String> objectRef, final SharedPreferences sp, final Ref.ObjectRef<Function0<Unit>> objectRef2) {
        CarUi carUi;
        CarUi carUi2;
        CarUi carUi3;
        filterBox.removeAllViews();
        CarUi carUi4 = this$0.ui;
        if (carUi4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi = null;
        } else {
            carUi = carUi4;
        }
        LinearLayout rf = (LinearLayout) CarUi.row$default(carUi, "Show", "Filter the list", 0, null, null, 28, null).component1();
        for (Pair pair : CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to("all", "All"), TuplesKt.to("US", "American"), TuplesKt.to("GB", "British"), TuplesKt.to("many", "Many speakers"), TuplesKt.to("have", "Downloaded")})) {
            final String k = (String) pair.component1();
            String lab = (String) pair.component2();
            CarUi carUi5 = this$0.ui;
            if (carUi5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi3 = null;
            } else {
                carUi3 = carUi5;
            }
            rf.addView(carUi3.button(lab, Intrinsics.areEqual(objectRef.element, k), new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda241
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit tabletVoiceStudioPage$fillFilter$lambda$371;
                    tabletVoiceStudioPage$fillFilter$lambda$371 = SettingsActivity.tabletVoiceStudioPage$fillFilter$lambda$371(objectRef2, k, sp, objectRef2, filterBox, this$0);
                    return tabletVoiceStudioPage$fillFilter$lambda$371;
                }
            }));
            View view = new View(this$0);
            CarUi carUi6 = this$0.ui;
            if (carUi6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi6 = null;
            }
            rf.addView(view, new LinearLayout.LayoutParams(carUi6.dp(6), 1));
        }
        LinearLayout linearLayout = rf;
        CarUi carUi7 = this$0.ui;
        if (carUi7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi2 = null;
        } else {
            carUi2 = carUi7;
        }
        filterBox.addView(linearLayout, carUi2.lp(0, 0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final Unit tabletVoiceStudioPage$fillFilter$lambda$371(Ref.ObjectRef $filter, String str, SharedPreferences $sp, Ref.ObjectRef $fillVoices, LinearLayout $filterBox, SettingsActivity this$0) {
        Function0 function0;
        $filter.element = str;
        $sp.edit().putString("st_filter", str).apply();
        tabletVoiceStudioPage$fillFilter($filterBox, this$0, $filter, $sp, $fillVoices);
        if ($fillVoices.element == 0) {
            Intrinsics.throwUninitializedPropertyAccessException("fillVoices");
            function0 = null;
        } else {
            function0 = (Function0) $fillVoices.element;
        }
        function0.invoke();
        return Unit.INSTANCE;
    }

    private static final View tabletVoiceStudioPage$gap$374(SettingsActivity this$0) {
        View it = new View(this$0);
        CarUi carUi = this$0.ui;
        if (carUi == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi = null;
        }
        it.setLayoutParams(new LinearLayout.LayoutParams(carUi.dp(8), 1));
        return it;
    }

    private static final String tabletVoiceStudioPage$mb$375(long b) {
        return Math.round(b / 1048576.0d) + " MB";
    }

    private static final void tabletVoiceStudioPage$job(final Ref.BooleanRef busy, final SettingsActivity this$0, AtomicBoolean cancel, final TextView stopBtn, final TextView status, final Ref.ObjectRef<Function0<Unit>> objectRef, String label, final Function0<String> function0) {
        if (busy.element) {
            Toast.makeText(this$0, "Wait for the current job (or Stop it)", 0).show();
            return;
        }
        busy.element = true;
        cancel.set(false);
        stopBtn.setVisibility(0);
        tabletVoiceStudioPage$say(this$0, status, label);
        new Thread(new Runnable() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                SettingsActivity.tabletVoiceStudioPage$job$lambda$377(function0, busy, this$0, stopBtn, status, objectRef);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void tabletVoiceStudioPage$job$lambda$377(Function0 $work, Ref.BooleanRef $busy, SettingsActivity this$0, final TextView $stopBtn, final TextView $status, final Ref.ObjectRef $fillVoices) {
        final String err;
        try {
            err = (String) $work.invoke();
        } catch (Throwable e) {
            String message = e.getMessage();
            if (message == null) {
                message = e.toString();
            }
            err = message;
        }
        $busy.element = false;
        this$0.runOnUiThread(new Runnable() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                SettingsActivity.tabletVoiceStudioPage$job$lambda$377$lambda$376($stopBtn, err, $status, $fillVoices);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void tabletVoiceStudioPage$job$lambda$377$lambda$376(TextView $stopBtn, String $err, TextView $status, Ref.ObjectRef $fillVoices) {
        Function0 function0;
        $stopBtn.setVisibility(8);
        if ($err != null && $status != null) {
            $status.setText($err);
        }
        if ($fillVoices.element == 0) {
            Intrinsics.throwUninitializedPropertyAccessException("fillVoices");
            function0 = null;
        } else {
            function0 = (Function0) $fillVoices.element;
        }
        function0.invoke();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00ca, code lost:
    
        if (r10.equals("US") != false) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00d6, code lost:
    
        r9 = kotlin.jvm.internal.Intrinsics.areEqual(r8.getRegion(), r41.element);
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00d3, code lost:
    
        if (r10.equals("GB") == false) goto L46;
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Unit tabletVoiceStudioPage$lambda$397(LinearLayout linearLayout, final SettingsActivity settingsActivity, Ref.ObjectRef objectRef, final SharedPreferences sharedPreferences, Ref.ObjectRef objectRef2, final AtomicBoolean atomicBoolean, final TextView textView, final Ref.BooleanRef booleanRef, final TextView textView2, final Ref.ObjectRef objectRef3, final Ref.ObjectRef objectRef4, final List list) {
        CarUi carUi;
        final Ref.IntRef intRef;
        LinearLayout linearLayout2;
        LinearLayout linearLayout3;
        CarUi carUi2;
        ArrayList arrayList;
        CarUi carUi3;
        CarUi carUi4;
        int i;
        CarUi carUi5;
        CarUi carUi6;
        CarUi carUi7;
        CarUi carUi8;
        CarUi carUi9;
        LinearLayout linearLayout4 = linearLayout;
        linearLayout.removeAllViews();
        int i2 = 0;
        if (!VoiceStudio.INSTANCE.engineInstalled(settingsActivity)) {
            CarUi carUi10 = settingsActivity.ui;
            if (carUi10 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi10 = null;
            }
            TextView note = carUi10.note("Install the voice engine first (above).");
            CarUi carUi11 = settingsActivity.ui;
            if (carUi11 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi11 = null;
            }
            linearLayout4.addView(note, CarUi.lp$default(carUi11, 0, 0, 3, null));
            return Unit.INSTANCE;
        }
        if (((List) objectRef.element).isEmpty()) {
            CarUi carUi12 = settingsActivity.ui;
            if (carUi12 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi12 = null;
            }
            TextView note2 = carUi12.note("Loading the voice list from Hugging Face…");
            CarUi carUi13 = settingsActivity.ui;
            if (carUi13 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ui");
                carUi13 = null;
            }
            linearLayout4.addView(note2, CarUi.lp$default(carUi13, 0, 0, 3, null));
            return Unit.INSTANCE;
        }
        Iterable iterable = (Iterable) objectRef.element;
        ArrayList arrayList2 = new ArrayList();
        Iterator it = iterable.iterator();
        while (true) {
            int i3 = 1;
            r9 = true;
            r9 = true;
            r9 = true;
            r9 = true;
            r9 = true;
            boolean z = true;
            if (it.hasNext()) {
                Object next = it.next();
                VoiceStudio.Voice voice = (VoiceStudio.Voice) next;
                String str = (String) objectRef2.element;
                switch (str.hashCode()) {
                    case 2267:
                        break;
                    case 2718:
                        break;
                    case 3195240:
                        if (str.equals("have")) {
                            z = VoiceStudio.INSTANCE.downloaded(settingsActivity, voice.getKey());
                            break;
                        }
                        break;
                    case 3343967:
                        if (str.equals("many") && voice.getSpeakers() <= 1) {
                            z = false;
                            break;
                        }
                        break;
                }
                if (z) {
                    arrayList2.add(next);
                }
            } else {
                ArrayList<VoiceStudio.Voice> arrayList3 = arrayList2;
                for (final VoiceStudio.Voice voice2 : arrayList3) {
                    boolean downloaded = VoiceStudio.INSTANCE.downloaded(settingsActivity, voice2.getKey());
                    LinearLayout linearLayout5 = new LinearLayout(settingsActivity);
                    linearLayout5.setOrientation(i3);
                    CarUi carUi14 = settingsActivity.ui;
                    if (carUi14 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("ui");
                        carUi = null;
                    } else {
                        carUi = carUi14;
                    }
                    Pair row$default = CarUi.row$default(carUi, voice2.getTitle() + " · " + voice2.getAccent() + " · " + voice2.getQuality(), (voice2.getSpeakers() > i3 ? voice2.getSpeakers() + " speakers · " : "") + tabletVoiceStudioPage$mb$375(voice2.getBytes()) + (downloaded ? " · downloaded" : ""), 0, null, null, 28, null);
                    LinearLayout linearLayout6 = (LinearLayout) row$default.component1();
                    Ref.IntRef intRef2 = new Ref.IntRef();
                    intRef2.element = RangesKt.coerceIn(sharedPreferences.getInt("st_sid_" + voice2.getKey(), i2), i2, Math.max(i2, voice2.getSpeakers() - i3));
                    if (!downloaded) {
                        CarUi carUi15 = settingsActivity.ui;
                        if (carUi15 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("ui");
                            carUi9 = null;
                        } else {
                            carUi9 = carUi15;
                        }
                        intRef = intRef2;
                        linearLayout2 = linearLayout6;
                        linearLayout3 = linearLayout5;
                        linearLayout2.addView(CarUi.button$default(carUi9, "Download " + tabletVoiceStudioPage$mb$375(voice2.getBytes()), false, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda150
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                Unit tabletVoiceStudioPage$lambda$397$lambda$383;
                                tabletVoiceStudioPage$lambda$397$lambda$383 = SettingsActivity.tabletVoiceStudioPage$lambda$397$lambda$383(voice, settingsActivity, atomicBoolean, textView, booleanRef, textView2, objectRef3);
                                return tabletVoiceStudioPage$lambda$397$lambda$383;
                            }
                        }, 2, null));
                        arrayList = arrayList3;
                        i = i3;
                    } else {
                        intRef = intRef2;
                        linearLayout2 = linearLayout6;
                        linearLayout3 = linearLayout5;
                        CarUi carUi16 = settingsActivity.ui;
                        if (carUi16 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("ui");
                            carUi2 = null;
                        } else {
                            carUi2 = carUi16;
                        }
                        arrayList = arrayList3;
                        boolean z2 = i3;
                        linearLayout2.addView(CarUi.button$default(carUi2, "▶ Try", false, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda151
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                Unit tabletVoiceStudioPage$lambda$397$lambda$385;
                                tabletVoiceStudioPage$lambda$397$lambda$385 = SettingsActivity.tabletVoiceStudioPage$lambda$397$lambda$385(voice, intRef, settingsActivity, objectRef4, list, textView, booleanRef, atomicBoolean, textView2, objectRef3);
                                return tabletVoiceStudioPage$lambda$397$lambda$385;
                            }
                        }, 2, null));
                        linearLayout2.addView(tabletVoiceStudioPage$gap$374(settingsActivity));
                        CarUi carUi17 = settingsActivity.ui;
                        if (carUi17 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("ui");
                            carUi3 = null;
                        } else {
                            carUi3 = carUi17;
                        }
                        linearLayout2.addView(carUi3.button("Make my voice", z2, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda152
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                Unit tabletVoiceStudioPage$lambda$397$lambda$389;
                                tabletVoiceStudioPage$lambda$397$lambda$389 = SettingsActivity.tabletVoiceStudioPage$lambda$397$lambda$389(voice, intRef, settingsActivity, objectRef4, atomicBoolean, textView, booleanRef, textView2, objectRef3);
                                return tabletVoiceStudioPage$lambda$397$lambda$389;
                            }
                        }));
                        linearLayout2.addView(tabletVoiceStudioPage$gap$374(settingsActivity));
                        CarUi carUi18 = settingsActivity.ui;
                        if (carUi18 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("ui");
                            carUi4 = null;
                        } else {
                            carUi4 = carUi18;
                        }
                        voice2 = voice2;
                        linearLayout2.addView(CarUi.button$default(carUi4, "Delete", false, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda153
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                Unit tabletVoiceStudioPage$lambda$397$lambda$390;
                                tabletVoiceStudioPage$lambda$397$lambda$390 = SettingsActivity.tabletVoiceStudioPage$lambda$397$lambda$390(settingsActivity, voice2, objectRef3, textView);
                                return tabletVoiceStudioPage$lambda$397$lambda$390;
                            }
                        }, 2, null));
                        i = z2;
                    }
                    LinearLayout linearLayout7 = linearLayout2;
                    CarUi carUi19 = settingsActivity.ui;
                    if (carUi19 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("ui");
                        carUi19 = null;
                    }
                    linearLayout3.addView(linearLayout7, carUi19.lp(0, 0));
                    if (downloaded && voice2.getSpeakers() > i) {
                        CarUi carUi20 = settingsActivity.ui;
                        if (carUi20 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("ui");
                            carUi5 = null;
                        } else {
                            carUi5 = carUi20;
                        }
                        final Ref.IntRef intRef3 = intRef;
                        Pair row$default2 = CarUi.row$default(carUi5, "Speaker", tabletVoiceStudioPage$lambda$397$who(voice2, intRef3.element), 1, null, null, 24, null);
                        LinearLayout linearLayout8 = (LinearLayout) row$default2.component1();
                        final TextView textView3 = (TextView) row$default2.component2();
                        final SeekBar seekBar = new SeekBar(settingsActivity);
                        seekBar.setMax(voice2.getSpeakers() - i);
                        seekBar.setProgress(intRef3.element);
                        seekBar.setThumbTintList(ColorStateList.valueOf(-1));
                        seekBar.setProgressTintList(ColorStateList.valueOf(CarUi.Companion.getACCENT()));
                        final VoiceStudio.Voice voice3 = voice2;
                        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.starpilot.starview.SettingsActivity$tabletVoiceStudioPage$7$bar$1$1
                            @Override // android.widget.SeekBar.OnSeekBarChangeListener
                            public void onProgressChanged(SeekBar sb, int p, boolean fromUser) {
                                String tabletVoiceStudioPage$lambda$397$who;
                                TextView textView4 = textView3;
                                if (textView4 != null) {
                                    tabletVoiceStudioPage$lambda$397$who = SettingsActivity.tabletVoiceStudioPage$lambda$397$who(voice3, p);
                                    textView4.setText(tabletVoiceStudioPage$lambda$397$who);
                                }
                            }

                            @Override // android.widget.SeekBar.OnSeekBarChangeListener
                            public void onStartTrackingTouch(SeekBar sb) {
                            }

                            @Override // android.widget.SeekBar.OnSeekBarChangeListener
                            public void onStopTrackingTouch(SeekBar sb) {
                                intRef3.element = seekBar.getProgress();
                                sharedPreferences.edit().putInt("st_sid_" + voice3.getKey(), intRef3.element).apply();
                            }
                        });
                        CarUi carUi21 = settingsActivity.ui;
                        if (carUi21 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("ui");
                            carUi6 = null;
                        } else {
                            carUi6 = carUi21;
                        }
                        final VoiceStudio.Voice voice4 = voice2;
                        linearLayout8.addView(CarUi.button$default(carUi6, "◀", false, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda154
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                Unit tabletVoiceStudioPage$lambda$397$lambda$393;
                                tabletVoiceStudioPage$lambda$397$lambda$393 = SettingsActivity.tabletVoiceStudioPage$lambda$397$lambda$393(intRef, seekBar, textView3, sharedPreferences, voice4);
                                return tabletVoiceStudioPage$lambda$397$lambda$393;
                            }
                        }, 2, null));
                        linearLayout8.addView(tabletVoiceStudioPage$gap$374(settingsActivity));
                        CarUi carUi22 = settingsActivity.ui;
                        if (carUi22 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("ui");
                            carUi7 = null;
                        } else {
                            carUi7 = carUi22;
                        }
                        final VoiceStudio.Voice voice5 = voice2;
                        linearLayout8.addView(CarUi.button$default(carUi7, "▶", false, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda156
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                Unit tabletVoiceStudioPage$lambda$397$lambda$394;
                                tabletVoiceStudioPage$lambda$397$lambda$394 = SettingsActivity.tabletVoiceStudioPage$lambda$397$lambda$394(intRef, voice5, seekBar, textView3, sharedPreferences);
                                return tabletVoiceStudioPage$lambda$397$lambda$394;
                            }
                        }, 2, null));
                        linearLayout8.addView(tabletVoiceStudioPage$gap$374(settingsActivity));
                        CarUi carUi23 = settingsActivity.ui;
                        if (carUi23 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("ui");
                            carUi8 = null;
                        } else {
                            carUi8 = carUi23;
                        }
                        final VoiceStudio.Voice voice6 = voice2;
                        linearLayout8.addView(CarUi.button$default(carUi8, "🎲", false, new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda157
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                Unit tabletVoiceStudioPage$lambda$397$lambda$395;
                                tabletVoiceStudioPage$lambda$397$lambda$395 = SettingsActivity.tabletVoiceStudioPage$lambda$397$lambda$395(intRef, voice6, seekBar, textView3, sharedPreferences);
                                return tabletVoiceStudioPage$lambda$397$lambda$395;
                            }
                        }, 2, null));
                        LinearLayout linearLayout9 = linearLayout8;
                        CarUi carUi24 = settingsActivity.ui;
                        CarUi carUi25 = carUi24;
                        if (carUi24 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("ui");
                            carUi25 = null;
                        }
                        linearLayout3.addView(linearLayout9, carUi25.lp(i, 0));
                        SeekBar seekBar2 = seekBar;
                        CarUi carUi26 = settingsActivity.ui;
                        if (carUi26 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("ui");
                            carUi26 = null;
                        }
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, carUi26.dp(44));
                        CarUi carUi27 = settingsActivity.ui;
                        if (carUi27 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("ui");
                            carUi27 = null;
                        }
                        layoutParams.leftMargin = carUi27.dp(28);
                        CarUi carUi28 = settingsActivity.ui;
                        if (carUi28 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("ui");
                            carUi28 = null;
                        }
                        layoutParams.rightMargin = carUi28.dp(12);
                        Unit unit = Unit.INSTANCE;
                        linearLayout3.addView(seekBar2, layoutParams);
                    }
                    LinearLayout linearLayout10 = linearLayout3;
                    CarUi carUi29 = settingsActivity.ui;
                    if (carUi29 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("ui");
                        carUi29 = null;
                    }
                    linearLayout.addView(linearLayout10, CarUi.lp$default(carUi29, 0, 0, 3, null));
                    linearLayout4 = linearLayout;
                    i3 = i;
                    arrayList3 = arrayList;
                    i2 = 0;
                }
                LinearLayout linearLayout11 = linearLayout4;
                if (arrayList3.isEmpty()) {
                    CarUi carUi30 = settingsActivity.ui;
                    if (carUi30 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("ui");
                        carUi30 = null;
                    }
                    TextView note3 = carUi30.note("Nothing here yet.");
                    CarUi carUi31 = settingsActivity.ui;
                    if (carUi31 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("ui");
                        carUi31 = null;
                    }
                    linearLayout11.addView(note3, CarUi.lp$default(carUi31, 0, 0, 3, null));
                }
                CarUi carUi32 = settingsActivity.ui;
                if (carUi32 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ui");
                    carUi32 = null;
                }
                TextView note4 = carUi32.note("Downloaded voices use " + tabletVoiceStudioPage$mb$375(VoiceStudio.INSTANCE.downloadedBytes(settingsActivity)) + " on the tablet. Voices are Piper voices from rhasspy/piper-voices on Hugging Face.");
                CarUi carUi33 = settingsActivity.ui;
                if (carUi33 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("ui");
                    carUi33 = null;
                }
                linearLayout11.addView(note4, CarUi.lp$default(carUi33, 0, 0, 3, null));
                return Unit.INSTANCE;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletVoiceStudioPage$lambda$397$lambda$383(final VoiceStudio.Voice $v, final SettingsActivity this$0, final AtomicBoolean $cancel, final TextView $status, Ref.BooleanRef $busy, TextView $stopBtn, Ref.ObjectRef $fillVoices) {
        tabletVoiceStudioPage$job($busy, this$0, $cancel, $stopBtn, $status, $fillVoices, "Downloading " + $v.getTitle() + "…", new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda53
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String tabletVoiceStudioPage$lambda$397$lambda$383$lambda$382;
                tabletVoiceStudioPage$lambda$397$lambda$383$lambda$382 = SettingsActivity.tabletVoiceStudioPage$lambda$397$lambda$383$lambda$382(this$0, $v, $cancel, $status);
                return tabletVoiceStudioPage$lambda$397$lambda$383$lambda$382;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String tabletVoiceStudioPage$lambda$397$lambda$383$lambda$382(final SettingsActivity this$0, final VoiceStudio.Voice $v, AtomicBoolean $cancel, final TextView $status) {
        String download = VoiceStudio.INSTANCE.download(this$0, $v, $cancel, new Function2() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda98
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit tabletVoiceStudioPage$lambda$397$lambda$383$lambda$382$lambda$380;
                tabletVoiceStudioPage$lambda$397$lambda$383$lambda$382$lambda$380 = SettingsActivity.tabletVoiceStudioPage$lambda$397$lambda$383$lambda$382$lambda$380($v, this$0, $status, ((Long) obj).longValue(), ((Long) obj2).longValue());
                return tabletVoiceStudioPage$lambda$397$lambda$383$lambda$382$lambda$380;
            }
        });
        if (download != null) {
            return download;
        }
        tabletVoiceStudioPage$say(this$0, $status, $v.getTitle() + " downloaded - ▶ Try it");
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletVoiceStudioPage$lambda$397$lambda$383$lambda$382$lambda$380(VoiceStudio.Voice $v, SettingsActivity this$0, TextView $status, long got, long tot) {
        tabletVoiceStudioPage$say(this$0, $status, "Downloading " + $v.getTitle() + "… " + tabletVoiceStudioPage$mb$375(got) + " of " + tabletVoiceStudioPage$mb$375(tot));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletVoiceStudioPage$lambda$397$lambda$385(final VoiceStudio.Voice $v, final Ref.IntRef $sid, final SettingsActivity this$0, final Ref.ObjectRef $style, final List $tryText, final TextView $status, Ref.BooleanRef $busy, AtomicBoolean $cancel, TextView $stopBtn, Ref.ObjectRef $fillVoices) {
        String str;
        String title = $v.getTitle();
        if ($v.getSpeakers() > 1) {
            str = " #" + $sid.element;
        } else {
            str = "";
        }
        tabletVoiceStudioPage$job($busy, this$0, $cancel, $stopBtn, $status, $fillVoices, title + str + ": speaking…", new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda130
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String tabletVoiceStudioPage$lambda$397$lambda$385$lambda$384;
                tabletVoiceStudioPage$lambda$397$lambda$385$lambda$384 = SettingsActivity.tabletVoiceStudioPage$lambda$397$lambda$385$lambda$384(this$0, $v, $sid, $style, $tryText, $status);
                return tabletVoiceStudioPage$lambda$397$lambda$385$lambda$384;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final String tabletVoiceStudioPage$lambda$397$lambda$385$lambda$384(SettingsActivity this$0, VoiceStudio.Voice $v, Ref.IntRef $sid, Ref.ObjectRef $style, List $tryText, TextView $status) {
        String str;
        List clips = VoiceStudio.INSTANCE.say(this$0, $v.getKey(), $sid.element, (VoiceStudio.Style) $style.element, $tryText);
        AmyVoice.INSTANCE.playClips(AmySynth.Style.ASK, clips);
        String title = $v.getTitle();
        if ($v.getSpeakers() > 1) {
            str = " speaker " + $sid.element;
        } else {
            str = "";
        }
        tabletVoiceStudioPage$say(this$0, $status, title + str + " - like it? Make my voice");
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletVoiceStudioPage$lambda$397$lambda$389(final VoiceStudio.Voice $v, final Ref.IntRef $sid, final SettingsActivity this$0, final Ref.ObjectRef $style, final AtomicBoolean $cancel, final TextView $status, Ref.BooleanRef $busy, TextView $stopBtn, Ref.ObjectRef $fillVoices) {
        String str;
        String title = $v.getTitle();
        if ($v.getSpeakers() > 1) {
            str = " #" + $sid.element;
        } else {
            str = "";
        }
        final String label = title + str + " · " + $v.getAccent();
        tabletVoiceStudioPage$job($busy, this$0, $cancel, $stopBtn, $status, $fillVoices, "Making \"" + label + "\"…", new Function0() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda75
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String tabletVoiceStudioPage$lambda$397$lambda$389$lambda$388;
                tabletVoiceStudioPage$lambda$397$lambda$389$lambda$388 = SettingsActivity.tabletVoiceStudioPage$lambda$397$lambda$389$lambda$388(this$0, $v, $sid, $style, label, $cancel, $status);
                return tabletVoiceStudioPage$lambda$397$lambda$389$lambda$388;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final String tabletVoiceStudioPage$lambda$397$lambda$389$lambda$388(final SettingsActivity this$0, VoiceStudio.Voice $v, Ref.IntRef $sid, Ref.ObjectRef $style, final String $label, AtomicBoolean $cancel, final TextView $status) {
        String make = VoiceStudio.INSTANCE.make(this$0, $v, $sid.element, (VoiceStudio.Style) $style.element, $label, $cancel, new Function2() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda86
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit tabletVoiceStudioPage$lambda$397$lambda$389$lambda$388$lambda$386;
                tabletVoiceStudioPage$lambda$397$lambda$389$lambda$388$lambda$386 = SettingsActivity.tabletVoiceStudioPage$lambda$397$lambda$389$lambda$388$lambda$386($label, this$0, $status, ((Integer) obj).intValue(), ((Integer) obj2).intValue());
                return tabletVoiceStudioPage$lambda$397$lambda$389$lambda$388$lambda$386;
            }
        });
        if (make == null) {
            tabletVoiceStudioPage$say(this$0, $status, "\"" + $label + "\" is now the lane-change voice");
            AmyVoice.INSTANCE.demo();
            return null;
        }
        return make;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletVoiceStudioPage$lambda$397$lambda$389$lambda$388$lambda$386(String $label, SettingsActivity this$0, TextView $status, int i, int n) {
        tabletVoiceStudioPage$say(this$0, $status, "Making \"" + $label + "\"… " + i + " of " + n + " phrases");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletVoiceStudioPage$lambda$397$lambda$390(SettingsActivity this$0, VoiceStudio.Voice $v, Ref.ObjectRef $fillVoices, TextView $status) {
        Function0 function0;
        VoiceStudio.INSTANCE.freeEngine();
        VoiceStudio.INSTANCE.deleteModel(this$0, $v.getKey());
        tabletVoiceStudioPage$say(this$0, $status, $v.getTitle() + ": download deleted (voices you made from it stay)");
        if ($fillVoices.element == 0) {
            Intrinsics.throwUninitializedPropertyAccessException("fillVoices");
            function0 = null;
        } else {
            function0 = (Function0) $fillVoices.element;
        }
        function0.invoke();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String tabletVoiceStudioPage$lambda$397$who(VoiceStudio.Voice v, int i) {
        String it;
        int speakers = v.getSpeakers();
        String it2 = (String) CollectionsKt.getOrNull(v.getSpeakerNames(), i);
        if (it2 == null || (it = " (" + it2 + ")") == null) {
            it = "";
        }
        return "Speaker " + i + " of " + speakers + it;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletVoiceStudioPage$lambda$397$lambda$393(Ref.IntRef $sid, SeekBar $bar, TextView $d2, SharedPreferences $sp, VoiceStudio.Voice $v) {
        if ($sid.element > 0) {
            $sid.element--;
            $bar.setProgress($sid.element);
            if ($d2 != null) {
                $d2.setText(tabletVoiceStudioPage$lambda$397$who($v, $sid.element));
            }
            $sp.edit().putInt("st_sid_" + $v.getKey(), $sid.element).apply();
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletVoiceStudioPage$lambda$397$lambda$394(Ref.IntRef $sid, VoiceStudio.Voice $v, SeekBar $bar, TextView $d2, SharedPreferences $sp) {
        if ($sid.element < $v.getSpeakers() - 1) {
            $sid.element++;
            $bar.setProgress($sid.element);
            if ($d2 != null) {
                $d2.setText(tabletVoiceStudioPage$lambda$397$who($v, $sid.element));
            }
            $sp.edit().putInt("st_sid_" + $v.getKey(), $sid.element).apply();
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletVoiceStudioPage$lambda$397$lambda$395(Ref.IntRef $sid, VoiceStudio.Voice $v, SeekBar $bar, TextView $d2, SharedPreferences $sp) {
        $sid.element = RangesKt.random(RangesKt.until(0, $v.getSpeakers()), Random.Default);
        $bar.setProgress($sid.element);
        if ($d2 != null) {
            $d2.setText(tabletVoiceStudioPage$lambda$397$who($v, $sid.element));
        }
        $sp.edit().putInt("st_sid_" + $v.getKey(), $sid.element).apply();
        return Unit.INSTANCE;
    }

    private static final void tabletVoiceStudioPage$loadCat(final Ref.ObjectRef<List<VoiceStudio.Voice>> objectRef, final SettingsActivity this$0, final TextView status, final Ref.ObjectRef<Function0<Unit>> objectRef2, final boolean refresh) {
        new Thread(new Runnable() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda107
            @Override // java.lang.Runnable
            public final void run() {
                SettingsActivity.tabletVoiceStudioPage$loadCat$lambda$399(objectRef2, this$0, refresh, status, objectRef2);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r0v3, types: [T, java.util.List] */
    public static final void tabletVoiceStudioPage$loadCat$lambda$399(Ref.ObjectRef $cat, SettingsActivity this$0, boolean $refresh, TextView $status, final Ref.ObjectRef $fillVoices) {
        try {
            $cat.element = VoiceStudio.INSTANCE.catalog(this$0, $refresh);
            tabletVoiceStudioPage$say(this$0, $status, ((List) $cat.element).size() + " English voices");
        } catch (Exception e) {
            tabletVoiceStudioPage$say(this$0, $status, "Couldn't get the voice list (" + e.getMessage() + ") - is the tablet online?");
        }
        this$0.runOnUiThread(new Runnable() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                SettingsActivity.tabletVoiceStudioPage$loadCat$lambda$399$lambda$398($fillVoices);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void tabletVoiceStudioPage$loadCat$lambda$399$lambda$398(Ref.ObjectRef $fillVoices) {
        Function0 function0;
        if ($fillVoices.element == 0) {
            Intrinsics.throwUninitializedPropertyAccessException("fillVoices");
            function0 = null;
        } else {
            function0 = (Function0) $fillVoices.element;
        }
        function0.invoke();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tabletVoiceStudioPage$lambda$400(Ref.ObjectRef $cat, SettingsActivity this$0, TextView $status, Ref.ObjectRef $fillVoices) {
        tabletVoiceStudioPage$loadCat($cat, this$0, $status, $fillVoices, true);
        return Unit.INSTANCE;
    }

    private final View techStreamPage() {
        final String h = Link.INSTANCE.getHost();
        if (h == null) {
            View offline = offline("Not connected to the comma. Connect first, then come back.");
            this.handler.post(new Runnable() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda239
                @Override // java.lang.Runnable
                public final void run() {
                    SettingsActivity.techStreamPage$lambda$402$lambda$401(SettingsActivity.this);
                }
            });
            return offline;
        }
        FrameLayout box = new FrameLayout(this);
        box.setBackgroundColor(Color.rgb(16, 16, 16));
        final WebView w = new WebView(this);
        w.getSettings().setJavaScriptEnabled(true);
        w.getSettings().setDomStorageEnabled(true);
        w.getSettings().setSupportZoom(false);
        w.setBackgroundColor(Color.rgb(16, 16, 16));
        w.setWebViewClient(new WebViewClient() { // from class: com.starpilot.starview.SettingsActivity$techStreamPage$w$1$1
            @Override // android.webkit.WebViewClient
            public void onReceivedError(WebView v, WebResourceRequest r, WebResourceError e) {
                Intrinsics.checkNotNullParameter(v, "v");
                Intrinsics.checkNotNullParameter(r, "r");
                Intrinsics.checkNotNullParameter(e, "e");
                if (r.isForMainFrame()) {
                    v.loadData("<body style='background:#101010;color:#aaa;font:18px sans-serif;padding:40px'>Couldn't open TechStream on the comma (" + TextUtils.htmlEncode(e.getDescription().toString()) + "). Update StarPilot on the comma to the version with TechStream, then come back.</body>", "text/html", "utf-8");
                }
            }
        });
        w.setWebChromeClient(new WebChromeClient());
        Context applicationContext = getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        w.addJavascriptInterface(new TermWeb.Bridge(applicationContext, w), "StarView");
        box.addView(w, new FrameLayout.LayoutParams(-1, -1));
        techStreamPage$load(this, h, w);
        box.setTag(new Runnable() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda240
            @Override // java.lang.Runnable
            public final void run() {
                SettingsActivity.techStreamPage$lambda$407(w, this, h);
            }
        });
        return box;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void techStreamPage$lambda$402$lambda$401(SettingsActivity this$0) {
        this$0.panels.remove("tablet:techstream");
    }

    private static final void techStreamPage$load(final SettingsActivity this$0, final String h, final WebView w) {
        new Thread(new Runnable() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda114
            @Override // java.lang.Runnable
            public final void run() {
                SettingsActivity.techStreamPage$load$lambda$406(this$0, h, w);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void techStreamPage$load$lambda$406(SettingsActivity this$0, final String $h, final WebView $w) {
        Link.INSTANCE.loadKey(this$0);
        Link.INSTANCE.ensureSession($h, Link.DAEMON_PORT);
        final String s = Link.INSTANCE.sessionFor($h, Link.DAEMON_PORT);
        this$0.runOnUiThread(new Runnable() { // from class: com.starpilot.starview.SettingsActivity$$ExternalSyntheticLambda83
            @Override // java.lang.Runnable
            public final void run() {
                SettingsActivity.techStreamPage$load$lambda$406$lambda$405($w, $h, s);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void techStreamPage$load$lambda$406$lambda$405(WebView $w, String $h, String $s) {
        $w.loadUrl("http://" + $h + ":8090/techstream/" + ($s.length() > 0 ? "?s=" + URLEncoder.encode($s, "UTF-8") : ""));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void techStreamPage$lambda$407(WebView $w, SettingsActivity this$0, String $h) {
        if ($w.getUrl() != null) {
            String url = $w.getUrl();
            Intrinsics.checkNotNull(url);
            if (!StringsKt.startsWith(url, "data:", false)) {
                return;
            }
        }
        techStreamPage$load(this$0, $h, $w);
    }

    private final View tabletAboutPage() {
        String v;
        CarUi carUi;
        CarUi carUi2;
        Pair<ScrollView, LinearLayout> page = page();
        ScrollView sv = page.component1();
        LinearLayout col = page.component2();
        try {
            v = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (Exception e) {
            v = "?";
        }
        CarUi carUi3 = this.ui;
        if (carUi3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi = null;
        } else {
            carUi = carUi3;
        }
        LinearLayout r1 = (LinearLayout) CarUi.row$default(carUi, "StarView", "Version " + v, 0, null, null, 28, null).component1();
        LinearLayout linearLayout = r1;
        CarUi carUi4 = this.ui;
        if (carUi4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi4 = null;
        }
        col.addView(linearLayout, CarUi.lp$default(carUi4, 0, 0, 3, null));
        CarUi carUi5 = this.ui;
        if (carUi5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi2 = null;
        } else {
            carUi2 = carUi5;
        }
        LinearLayout r2 = (LinearLayout) CarUi.row$default(carUi2, "Views", "Camera: the video with the openpilot overlay. Scene: a 3D drawing of the road. The button at the bottom left switches.", 0, null, null, 28, null).component1();
        LinearLayout linearLayout2 = r2;
        CarUi carUi6 = this.ui;
        if (carUi6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ui");
            carUi6 = null;
        }
        col.addView(linearLayout2, CarUi.lp$default(carUi6, 0, 0, 3, null));
        return sv;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        this.handler.post(this.ticker);
        this.handler.postDelayed(this.valuePoll, 4000L);
        View view = this.current;
        GxPage it = view instanceof GxPage ? (GxPage) view : null;
        if (it != null && !it.getShown() && it.getParent() != null) {
            it.show();
        }
        View view2 = this.current;
        GalaxyWeb galaxyWeb = view2 instanceof GalaxyWeb ? (GalaxyWeb) view2 : null;
        if (galaxyWeb != null) {
            galaxyWeb.show();
        }
        View view3 = this.current;
        CommaHealthView commaHealthView = view3 instanceof CommaHealthView ? (CommaHealthView) view3 : null;
        if (commaHealthView != null) {
            commaHealthView.start();
        }
        View view4 = this.current;
        DoorsBeltsView doorsBeltsView = view4 instanceof DoorsBeltsView ? (DoorsBeltsView) view4 : null;
        if (doorsBeltsView != null) {
            doorsBeltsView.start();
        }
        View view5 = this.current;
        KeyFobView keyFobView = view5 instanceof KeyFobView ? (KeyFobView) view5 : null;
        if (keyFobView != null) {
            keyFobView.start();
        }
        View view6 = this.current;
        HubPowerView hubPowerView = view6 instanceof HubPowerView ? (HubPowerView) view6 : null;
        if (hubPowerView != null) {
            hubPowerView.start();
        }
        View view7 = this.current;
        GalaxySettingsView galaxySettingsView = view7 instanceof GalaxySettingsView ? (GalaxySettingsView) view7 : null;
        if (galaxySettingsView != null) {
            galaxySettingsView.show();
        }
        View view8 = this.current;
        GalaxyExtras.QuickSelect quickSelect = view8 instanceof GalaxyExtras.QuickSelect ? (GalaxyExtras.QuickSelect) view8 : null;
        if (quickSelect != null) {
            quickSelect.show();
        }
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onSaveInstanceState(Bundle outState) {
        Intrinsics.checkNotNullParameter(outState, "outState");
        super.onSaveInstanceState(outState);
        outState.putInt("area", this.areaIndex);
        String it = this.currentPage;
        if (it != null) {
            outState.putString("page", it);
        }
        outState.putBoolean("open", this.contentHolder != null);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        this.handler.removeCallbacks(this.ticker);
        this.handler.removeCallbacks(this.valuePoll);
        View view = this.current;
        GxPage gxPage = view instanceof GxPage ? (GxPage) view : null;
        if (gxPage != null) {
            gxPage.hide();
        }
        hideGalaxy(this.current);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    @Deprecated(message = "Deprecated in Java")
    public void onBackPressed() {
        if (this.contentHolder == null) {
            SettingsLauncher settingsLauncher = this.launcher;
            if (settingsLauncher != null && settingsLauncher.getDrawerOpen()) {
                SettingsLauncher settingsLauncher2 = this.launcher;
                if (settingsLauncher2 != null) {
                    settingsLauncher2.closeDrawer();
                    return;
                }
                return;
            }
        }
        View view = this.current;
        GalaxySettingsView galaxySettingsView = view instanceof GalaxySettingsView ? (GalaxySettingsView) view : null;
        if (galaxySettingsView != null && galaxySettingsView.back()) {
            return;
        }
        View view2 = this.current;
        GalaxyWeb galaxyWeb = view2 instanceof GalaxyWeb ? (GalaxyWeb) view2 : null;
        if (galaxyWeb != null && galaxyWeb.back()) {
            return;
        }
        if (this.contentHolder != null) {
            showHome();
        } else {
            super.onBackPressed();
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        View view = this.current;
        GxPage gxPage = view instanceof GxPage ? (GxPage) view : null;
        if (gxPage != null) {
            gxPage.hide();
        }
        hideGalaxy(this.current);
        for (View view2 : this.panels.values()) {
            Intrinsics.checkNotNullExpressionValue(view2, "next(...)");
            View w = view2;
            GalaxyWeb galaxyWeb = w instanceof GalaxyWeb ? (GalaxyWeb) w : null;
            if (galaxyWeb != null) {
                galaxyWeb.destroy();
            }
        }
        TermWeb.INSTANCE.detach();
        GalaxyApi galaxyApi = this.api;
        if (galaxyApi != null) {
            galaxyApi.setOnChanged(null);
        }
        MapdMaps.INSTANCE.setOnChange(null);
        super.onDestroy();
    }
}
