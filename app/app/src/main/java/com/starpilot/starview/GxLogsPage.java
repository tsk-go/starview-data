package com.starpilot.starview;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.SystemClock;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.starpilot.starview.CarUi;
import com.starpilot.starview.GxPage;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.ranges.RangesKt;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import android.content.Context;
import android.text.TextUtils.TruncateAt;
import android.view.ViewGroup.LayoutParams;
import kotlin.jvm.internal.Ref.ObjectRef;

/* compiled from: GxLogs.kt */
public final class GxLogsPage extends GxPage {
    private String errContent;
    private TextView errDelAll;
    private List<ErrFile> errFiles;
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
    private final HashMap<String, MonTile> monTiles;
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

    /* VF: from Vineflower (jadx: ) */
    public GxLogsPage(Activity var1, GxHost var2) {
        super(var1, var2);
        Intrinsics.checkNotNullParameter(var1, "act");
        Intrinsics.checkNotNullParameter(var2, "gx");
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

    @Override // com.starpilot.starview.GxPage
    public void build() {
        GxPage.header$default(this, "Logs & Diagnostics", null, 2, null);
        this.selectTab = tabs(CollectionsKt.listOf(new String[]{"Troubleshoot", "Error Logs", "Tmux Live Log", "System Monitor", "Get Help via Discord"}), this.tab, new Function1() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit build$lambda$0;
                build$lambda$0 = GxLogsPage.build$lambda$0(GxLogsPage.this, ((Integer) obj).intValue());
                return build$lambda$0;
            }
        }, new Function2() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit build$lambda$1;
                build$lambda$1 = GxLogsPage.build$lambda$1(GxLogsPage.this, ((Integer) obj).intValue(), (LinearLayout) obj2);
                return build$lambda$1;
            }
        });
        every(2000L, new Function0() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit build$lambda$2;
                build$lambda$2 = GxLogsPage.build$lambda$2(GxLogsPage.this);
                return build$lambda$2;
            }
        });
        every(1000L, new Function0() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit build$lambda$3;
                build$lambda$3 = GxLogsPage.build$lambda$3(GxLogsPage.this);
                return build$lambda$3;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$0(GxLogsPage this$0, int i) {
        this$0.tab = i;
        if (this$0.getShown()) {
            this$0.tabShown(i);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$1(GxLogsPage this$0, int i, LinearLayout box) {
        Intrinsics.checkNotNullParameter(box, "box");
        switch (i) {
            case 0:
                this$0.buildTroubleshoot(box);
                break;
            case 1:
                this$0.buildErrors(box);
                break;
            case 2:
                this$0.buildTmux(box);
                break;
            case 3:
                this$0.buildMonitor(box);
                break;
            default:
                this$0.buildDiscord(box);
                break;
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$2(GxLogsPage this$0) {
        if (this$0.tab == 2) {
            this$0.tmuxSnapshot();
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$3(GxLogsPage this$0) {
        if (this$0.tab == 3) {
            this$0.monRefresh();
        }
        return Unit.INSTANCE;
    }

    @Override // com.starpilot.starview.GxPage
    public void onShow() {
        tabShown(this.tab);
    }

    @Override // com.starpilot.starview.GxPage
    public void onTick() {
        if (this.tab != 3 || this.monPaused || this.monSnap == null) {
            return;
        }
        this.monSensorNow = SystemClock.elapsedRealtime();
        monPaintVitals();
    }

    private final void tabShown(int i) {
        switch (i) {
            case 0:
                tsLoad(false);
                break;
            case 1:
                loadErrors();
                break;
            case 2:
                loadTmuxFiles();
                tmuxSnapshot();
                break;
        }
    }

    private final void copyText(String text, String okMsg) {
        try {
            Object systemService = getAct().getSystemService("clipboard");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.content.ClipboardManager");
            ((ClipboardManager) systemService).setPrimaryClip(ClipData.newPlainText("StarView", text));
            toast(okMsg);
        } catch (Exception e) {
            toast("Failed to copy: " + e.getMessage());
        }
    }

    private final EditText searchField(String hint, ViewGroup into, final Function1<? super String, Unit> onChange) {
        EditText e = new EditText(getAct());
        e.setHint(hint);
        e.setTextSize(19.0f);
        e.setTextColor(CarUi.Companion.getTEXT());
        e.setHintTextColor(CarUi.Companion.getDIM());
        e.setSingleLine(true);
        e.setInputType(1);
        e.setBackground(getUi().rounded(CarUi.Companion.getCARD(), 12, Integer.valueOf(Color.rgb(50, 55, 66))));
        e.setPadding(getUi().dp(18), getUi().dp(12), getUi().dp(18), getUi().dp(12));
        e.setMinHeight(getUi().dp(58));
        e.addTextChangedListener(new TextWatcher() { // from class: com.starpilot.starview.GxLogsPage$searchField$1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int a, int b, int c) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int a, int b, int c) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                String str;
                Function1<String, Unit> function1 = (Function1) onChange;
                if (s == null || (str = s.toString()) == null) {
                    str = "";
                }
                function1.invoke(str);
            }
        });
        LinearLayout.LayoutParams $this$searchField_u24lambda_u245 = new LinearLayout.LayoutParams(-1, -2);
        $this$searchField_u24lambda_u245.bottomMargin = getUi().dp(8);
        Unit unit = Unit.INSTANCE;
        into.addView(e, $this$searchField_u24lambda_u245);
        return e;
    }

    private final void getJsonAccept(final String path, final Function2<? super String, Object, Unit> then) {
        final String h = getHost();
        if (h == null) {
            then.invoke("Not connected", null);
        } else {
            new Thread(new Runnable() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    GxLogsPage.getJsonAccept$lambda$10(h, path, GxLogsPage.this, then);
                }
            }).start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v12, types: [T, java.lang.String] */
    public static final void getJsonAccept$lambda$10(String str, String str2, GxLogsPage gxLogsPage, final Function2 function2) {
        String str3;
        Object obj;
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        final Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
        try {
            Response execute = GxPage.Companion.client(20L).newCall(new Request.Builder().url("http://" + str + ":8082" + str2).header("Accept", "application/json").build()).execute();
            Throwable execute$c = null;
            try {
                Response response = execute;
                ResponseBody body = response.body();
                if (body == null || (str3 = body.string()) == null) {
                    str3 = "";
                }
                if (response.isSuccessful()) {
                    try {
                        Result.Companion companion = Result.Companion;
                        obj = KtCompat.resultOf(new JSONTokener(str3).nextValue());
                    } catch (Throwable th) {
                        Result.Companion companion2 = Result.Companion;
                        obj = KtCompat.resultOf(ResultKt.createFailure(th));
                    }
                    boolean m1197isFailureimpl = KtCompat.isFailure(obj);
                    T t = obj;
                    if (m1197isFailureimpl) {
                        t = 0;
                    }
                    objectRef2.element = t;
                } else {
                    objectRef.element = "HTTP " + response.code();
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable execute$t) {
                execute$c = execute$t;
                throw execute$t;
            } finally {
                CloseableKt.closeFinally(execute, execute$c);
            }
        } catch (Exception e) {
            String message = e.getMessage();
            T t2 = message;
            if (message == null) {
                t2 = e.toString();
            }
            objectRef.element = t2;
        }
        gxLogsPage.getMain().post(new Runnable() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                GxLogsPage.getJsonAccept$lambda$10$lambda$9(function2, objectRef, objectRef2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void getJsonAccept$lambda$10$lambda$9(Function2 $then, Ref.ObjectRef $err, Ref.ObjectRef $v) {
        $then.invoke($err.element, $v.element);
    }

    private final Double optNum(JSONObject o, String k) {
        if (o == null || !o.has(k) || o.isNull(k)) {
            return null;
        }
        double d = o.optDouble(k);
        if (Double.isNaN(d)) {
            return null;
        }
        return Double.valueOf(d);
    }

    private final String jsNum(double d) {
        return (!((d > Math.floor(d) ? 1 : (d == Math.floor(d) ? 0 : -1)) == 0) || Double.isInfinite(d) || Math.abs(d) >= 1.0E15d) ? String.valueOf(d) : String.valueOf((long) d);
    }

    private final TextView chip(String text, int color, ViewGroup into) {
        TextView t = new TextView(getAct());
        t.setText(text);
        t.setTextSize(14.0f);
        t.setTypeface(Typeface.DEFAULT_BOLD);
        t.setTextColor(CarUi.Companion.getBG());
        t.setBackground(CarUi.rounded$default(getUi(), color, 10, null, 4, null));
        t.setPadding(getUi().dp(10), getUi().dp(4), getUi().dp(10), getUi().dp(4));
        LinearLayout.LayoutParams $this$chip_u24lambda_u2412 = new LinearLayout.LayoutParams(-2, -2);
        $this$chip_u24lambda_u2412.leftMargin = getUi().dp(10);
        Unit unit = Unit.INSTANCE;
        into.addView(t, $this$chip_u24lambda_u2412);
        return t;
    }

    private final LinearLayout titleBar(String title, ViewGroup into) {
        LinearLayout bar = new LinearLayout(getAct());
        bar.setOrientation(0);
        bar.setGravity(16);
        bar.setPadding(0, 0, 0, getUi().dp(6));
        TextView $this$titleBar_u24lambda_u2414 = new TextView(getAct());
        $this$titleBar_u24lambda_u2414.setText(title);
        $this$titleBar_u24lambda_u2414.setTextSize(21.0f);
        $this$titleBar_u24lambda_u2414.setTextColor(CarUi.Companion.getTEXT());
        $this$titleBar_u24lambda_u2414.setTypeface(Typeface.DEFAULT_BOLD);
        bar.addView($this$titleBar_u24lambda_u2414, new LinearLayout.LayoutParams(0, -2, 1.0f));
        into.addView(bar);
        return bar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: GxLogs.kt */
    static final class ErrFile {
        private final String date;
        private final String filename;

        public ErrFile(String filename, String date) {
            Intrinsics.checkNotNullParameter(filename, "filename");
            Intrinsics.checkNotNullParameter(date, "date");
            this.filename = filename;
            this.date = date;
        }

        public final String getDate() {
            return this.date;
        }

        public final String getFilename() {
            return this.filename;
        }
    }

    private final void buildErrors(LinearLayout box) {
        int i;
        LinearLayout top = new LinearLayout(getAct());
        top.setOrientation(0);
        top.setGravity(16);
        box.addView(top, new LinearLayout.LayoutParams(-1, -2));
        LinearLayout $this$buildErrors_u24lambda_u2416 = new LinearLayout(getAct());
        $this$buildErrors_u24lambda_u2416.setOrientation(1);
        top.addView($this$buildErrors_u24lambda_u2416, new LinearLayout.LayoutParams(0, -2, 1.0f));
        searchField("Search logs...", $this$buildErrors_u24lambda_u2416, new Function1() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda20
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit buildErrors$lambda$17;
                buildErrors$lambda$17 = GxLogsPage.buildErrors$lambda$17(GxLogsPage.this, (String) obj);
                return buildErrors$lambda$17;
            }
        });
        TextView $this$buildErrors_u24lambda_u2419 = CarUi.button$default(getUi(), "Delete All", false, new Function0() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda21
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildErrors$lambda$18;
                buildErrors$lambda$18 = GxLogsPage.buildErrors$lambda$18(GxLogsPage.this);
                return buildErrors$lambda$18;
            }
        }, 2, null);
        i = GxLogsKt.GXLOGS_RED;
        $this$buildErrors_u24lambda_u2419.setTextColor(i);
        $this$buildErrors_u24lambda_u2419.setVisibility(8);
        this.errDelAll = $this$buildErrors_u24lambda_u2419;
        TextView textView = this.errDelAll;
        LinearLayout linearLayout = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("errDelAll");
            textView = null;
        }
        LinearLayout.LayoutParams $this$buildErrors_u24lambda_u2420 = new LinearLayout.LayoutParams(-2, -2);
        $this$buildErrors_u24lambda_u2420.leftMargin = getUi().dp(10);
        $this$buildErrors_u24lambda_u2420.bottomMargin = getUi().dp(8);
        Unit unit = Unit.INSTANCE;
        top.addView(textView, $this$buildErrors_u24lambda_u2420);
        this.errList = box(box);
        LinearLayout $this$buildErrors_u24lambda_u2421 = card(box);
        $this$buildErrors_u24lambda_u2421.setVisibility(8);
        this.errViewCard = $this$buildErrors_u24lambda_u2421;
        LinearLayout linearLayout2 = this.errViewCard;
        if (linearLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("errViewCard");
            linearLayout2 = null;
        }
        LinearLayout bar = titleBar("", linearLayout2);
        View childAt = bar.getChildAt(0);
        Intrinsics.checkNotNull(childAt, "null cannot be cast to non-null type android.widget.TextView");
        this.errViewTitle = (TextView) childAt;
        bar.addView(CarUi.button$default(getUi(), "Copy", false, new Function0() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda23
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildErrors$lambda$22;
                buildErrors$lambda$22 = GxLogsPage.buildErrors$lambda$22(GxLogsPage.this);
                return buildErrors$lambda$22;
            }
        }, 2, null));
        LinearLayout linearLayout3 = this.errViewCard;
        if (linearLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("errViewCard");
        } else {
            linearLayout = linearLayout3;
        }
        this.errViewLog = logBox(520, linearLayout);
        errRender();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildErrors$lambda$17(GxLogsPage this$0, String q) {
        Intrinsics.checkNotNullParameter(q, "q");
        this$0.errFilter = q;
        this$0.errRender();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildErrors$lambda$18(GxLogsPage this$0) {
        this$0.errDeleteAll();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildErrors$lambda$22(GxLogsPage this$0) {
        this$0.copyText(this$0.errContent, "Copied to clipboard!");
        return Unit.INSTANCE;
    }

    private final String parseLogDate(String filename) {
        Object m1191constructorimpl;
        Date d;
        MatchResult m = (new Regex("(\\d{4})-(\\d{2})-(\\d{2})[T_]?(\\d{2})-?(\\d{2})-?(\\d{2})?")).find(filename, 0);
        if (m == null) {
            d = new Date();
        } else {
            try {
                Result.Companion companion = Result.Companion;
                GxLogsPage gxLogsPage = this;
                List g = m.getGroupValues();
                Calendar $this$parseLogDate_u24lambda_u2424_u24lambda_u2423 = Calendar.getInstance();
                $this$parseLogDate_u24lambda_u2424_u24lambda_u2423.setLenient(true);
                int parseInt = Integer.parseInt(((String) g.get(1)));
                int parseInt2 = Integer.parseInt(((String) g.get(2))) - 1;
                int parseInt3 = Integer.parseInt(((String) g.get(3)));
                Integer intOrNull = StringsKt.toIntOrNull(((String) g.get(4)));
                int intValue = intOrNull != null ? intOrNull.intValue() : 0;
                Integer intOrNull2 = StringsKt.toIntOrNull(((String) g.get(5)));
                int intValue2 = intOrNull2 != null ? intOrNull2.intValue() : 0;
                Integer intOrNull3 = StringsKt.toIntOrNull(((String) g.get(6)));
                $this$parseLogDate_u24lambda_u2424_u24lambda_u2423.set(parseInt, parseInt2, parseInt3, intValue, intValue2, intOrNull3 != null ? intOrNull3.intValue() : 0);
                $this$parseLogDate_u24lambda_u2424_u24lambda_u2423.set(14, 0);
                m1191constructorimpl = KtCompat.resultOf($this$parseLogDate_u24lambda_u2424_u24lambda_u2423.getTime());
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                m1191constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
            }
            if (KtCompat.exceptionOrNull(m1191constructorimpl) != null) {
                m1191constructorimpl = new Date();
            }
            d = (Date) m1191constructorimpl;
        }
        String format = DateFormat.getDateTimeInstance(2, 2).format(d);
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format;
    }

    private final void loadErrors() {
        getJsonAccept("/api/error_logs", new Function2() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda18
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit loadErrors$lambda$27;
                loadErrors$lambda$27 = GxLogsPage.loadErrors$lambda$27(GxLogsPage.this, (String) obj, obj2);
                return loadErrors$lambda$27;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadErrors$lambda$27(GxLogsPage this$0, String e, Object v) {
        this$0.errLoading = false;
        if (e != null || !(v instanceof JSONArray)) {
            this$0.toast("Failed to load error logs.");
        } else {
            Iterable $this$map$iv = this$0.strings((JSONArray) v);
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            for (Object item$iv$iv : $this$map$iv) {
                String it = (String) item$iv$iv;
                destination$iv$iv.add(new ErrFile(it, this$0.parseLogDate(it)));
            }
            this$0.errFiles = (List) destination$iv$iv;
        }
        this$0.errRender();
        return Unit.INSTANCE;
    }

    private final void errRender() {
        List list;
        int i;
        if (this.errList == null) {
            return;
        }
        LinearLayout linearLayout = this.errList;
        LinearLayout linearLayout2 = null;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("errList");
            linearLayout = null;
        }
        linearLayout.removeAllViews();
        TextView textView = this.errDelAll;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("errDelAll");
            textView = null;
        }
        textView.setVisibility(!this.errFiles.isEmpty() ? 0 : 8);
        String q = this.errFilter.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(q, "toLowerCase(...)");
        if (q.length() == 0) {
            list = this.errFiles;
        } else {
            Iterable $this$filter$iv = this.errFiles;
            Collection destination$iv$iv = new ArrayList();
            for (Object element$iv$iv : $this$filter$iv) {
                ErrFile it = (ErrFile) element$iv$iv;
                String lowerCase = it.getFilename().toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                String q2 = q;
                if (StringsKt.contains((CharSequence) lowerCase, (CharSequence) q, false)) {
                    destination$iv$iv.add(element$iv$iv);
                }
                q = q2;
            }
            list = (List) destination$iv$iv;
        }
        List<ErrFile> shownFiles = list;
        if (this.errLoading) {
            LinearLayout linearLayout3 = this.errList;
            if (linearLayout3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("errList");
                linearLayout3 = null;
            }
            linearLayout3.addView(empty("Loading..."));
        } else if (shownFiles.isEmpty()) {
            LinearLayout linearLayout4 = this.errList;
            if (linearLayout4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("errList");
                linearLayout4 = null;
            }
            linearLayout4.addView(empty("No error logs!"));
        } else {
            for (final ErrFile f : shownFiles) {
                GxLogsPage gxLogsPage = this;
                String date = f.getDate();
                String filename = f.getFilename();
                LinearLayout linearLayout5 = this.errList;
                if (linearLayout5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("errList");
                    linearLayout5 = null;
                }
                GxPage.Row r = GxPage.row$default(gxLogsPage, date, filename, linearLayout5, 0, 8, null);
                if (Intrinsics.areEqual(f.getFilename(), this.errSelected)) {
                    r.getView().setBackground(getUi().rounded(CarUi.Companion.getCARD_ON(), 14, Integer.valueOf(CarUi.Companion.getACCENT())));
                }
                r.getView().setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda8
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        GxLogsPage.this.errView(f);
                    }
                });
                GxPage.Row.button$default(r, "Download", false, new Function0() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda9
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Unit errRender$lambda$30;
                        errRender$lambda$30 = GxLogsPage.errRender$lambda$30(GxLogsPage.this, f);
                        return errRender$lambda$30;
                    }
                }, 2, null);
                TextView button$default = GxPage.Row.button$default(r, "Delete", false, new Function0() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda10
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Unit errRender$lambda$31;
                        errRender$lambda$31 = GxLogsPage.errRender$lambda$31(GxLogsPage.this, f);
                        return errRender$lambda$31;
                    }
                }, 2, null);
                i = GxLogsKt.GXLOGS_RED;
                button$default.setTextColor(i);
            }
        }
        if (this.errSelected.length() == 0) {
            LinearLayout linearLayout6 = this.errViewCard;
            if (linearLayout6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("errViewCard");
            } else {
                linearLayout2 = linearLayout6;
            }
            linearLayout2.setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit errRender$lambda$30(GxLogsPage this$0, ErrFile $f) {
        this$0.download("/api/error_logs/" + this$0.enc($f.getFilename()), $f.getFilename(), "text/plain");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit errRender$lambda$31(GxLogsPage this$0, ErrFile $f) {
        this$0.errDelete($f);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void errView(final ErrFile f) {
        this.errSelected = f.getFilename();
        errRender();
        LinearLayout linearLayout = this.errViewCard;
        TextView textView = null;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("errViewCard");
            linearLayout = null;
        }
        linearLayout.setVisibility(0);
        TextView textView2 = this.errViewTitle;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("errViewTitle");
            textView2 = null;
        }
        textView2.setText(f.getFilename());
        TextView textView3 = this.errViewLog;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("errViewLog");
        } else {
            textView = textView3;
        }
        textView.setText("Loading...");
        getText("/api/error_logs/" + enc(f.getFilename()), new Function2() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda35
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit errView$lambda$33;
                errView$lambda$33 = GxLogsPage.errView$lambda$33(GxLogsPage.this, f, (String) obj, (String) obj2);
                return errView$lambda$33;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit errView$lambda$33(final GxLogsPage this$0, ErrFile $f, String e, String t) {
        Intrinsics.checkNotNullParameter(t, "t");
        if (!Intrinsics.areEqual(this$0.errSelected, $f.getFilename())) {
            return Unit.INSTANCE;
        }
        this$0.errContent = e != null ? "Could not load this log." : t;
        TextView textView = this$0.errViewLog;
        TextView textView2 = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("errViewLog");
            textView = null;
        }
        textView.setText(this$0.errContent);
        TextView textView3 = this$0.errViewLog;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("errViewLog");
        } else {
            textView2 = textView3;
        }
        this$0.logToEnd(textView2);
        this$0.getMain().post(new Runnable() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda46
            @Override // java.lang.Runnable
            public final void run() {
                GxLogsPage.errView$lambda$33$lambda$32(this$0);
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void errView$lambda$33$lambda$32(GxLogsPage this$0) {
        int y = 0;
        View view = this$0.errViewCard;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("errViewCard");
            view = null;
        }
        View v = view;
        while (v != null && v != this$0.getCol()) {
            y += v.getTop();
            Object parent = v.getParent();
            v = parent instanceof View ? (View) parent : null;
        }
        this$0.getScroll().smoothScrollTo(0, y);
    }

    private final void errDelete(final ErrFile f) {
        confirm("Delete log?", "Delete " + f.getFilename() + "?", "Delete", new Function0() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda49
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit errDelete$lambda$36;
                errDelete$lambda$36 = GxLogsPage.errDelete$lambda$36(GxLogsPage.this, f);
                return errDelete$lambda$36;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit errDelete$lambda$36(final GxLogsPage this$0, final ErrFile $f) {
        GxPage.send$default(this$0, "DELETE", "/api/error_logs/" + this$0.enc($f.getFilename()), null, 0L, new Function2() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda31
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit errDelete$lambda$36$lambda$35;
                errDelete$lambda$36$lambda$35 = GxLogsPage.errDelete$lambda$36$lambda$35(this$0, $f, (String) obj, (JSONObject) obj2);
                return errDelete$lambda$36$lambda$35;
            }
        }, 12, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit errDelete$lambda$36$lambda$35(GxLogsPage this$0, ErrFile $f, String e, JSONObject jSONObject) {
        if (e != null) {
            this$0.toast(e);
            return Unit.INSTANCE;
        }
        Iterable $this$filter$iv = this$0.errFiles;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            ErrFile it = (ErrFile) element$iv$iv;
            if (!Intrinsics.areEqual(it.getFilename(), $f.getFilename())) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        this$0.errFiles = (List) destination$iv$iv;
        if (Intrinsics.areEqual(this$0.errSelected, $f.getFilename())) {
            this$0.errSelected = "";
        }
        this$0.errRender();
        this$0.toast("Log deleted!");
        return Unit.INSTANCE;
    }

    private final void errDeleteAll() {
        confirm("Delete all error logs?", "This cannot be undone.", "Delete All", new Function0() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit errDeleteAll$lambda$38;
                errDeleteAll$lambda$38 = GxLogsPage.errDeleteAll$lambda$38(GxLogsPage.this);
                return errDeleteAll$lambda$38;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit errDeleteAll$lambda$38(final GxLogsPage this$0) {
        GxPage.send$default(this$0, "DELETE", "/api/error_logs/delete_all", null, 0L, new Function2() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda28
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit errDeleteAll$lambda$38$lambda$37;
                errDeleteAll$lambda$38$lambda$37 = GxLogsPage.errDeleteAll$lambda$38$lambda$37(this$0, (String) obj, (JSONObject) obj2);
                return errDeleteAll$lambda$38$lambda$37;
            }
        }, 12, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit errDeleteAll$lambda$38$lambda$37(GxLogsPage this$0, String e, JSONObject jSONObject) {
        if (e != null) {
            this$0.toast("Delete all failed.");
            return Unit.INSTANCE;
        }
        this$0.errFiles = CollectionsKt.emptyList();
        this$0.errSelected = "";
        this$0.errRender();
        this$0.toast("All error logs deleted!");
        return Unit.INSTANCE;
    }

    private final void buildTmux(LinearLayout box) {
        LinearLayout c = card(box);
        LinearLayout bar = titleBar("Tmux Live Log", c);
        this.tmuxTransport = chip("polling", CarUi.Companion.getDIM(), bar);
        GxPage.text$default(this, "Newest lines are at the top.", c, 0.0f, 0, false, 28, null);
        this.tmuxLog = logBox(520, c);
        TextView textView = this.tmuxLog;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tmuxLog");
            textView = null;
        }
        textView.setText("(waiting for log output…)");
        LinearLayout btns = GxPage.buttons$default(this, new Pair[]{TuplesKt.to("Pause", new Function0() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda41
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildTmux$lambda$39;
                buildTmux$lambda$39 = GxLogsPage.buildTmux$lambda$39(GxLogsPage.this);
                return buildTmux$lambda$39;
            }
        }), TuplesKt.to("Capture Log", new Function0() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda42
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildTmux$lambda$40;
                buildTmux$lambda$40 = GxLogsPage.buildTmux$lambda$40(GxLogsPage.this);
                return buildTmux$lambda$40;
            }
        }), TuplesKt.to("Delete All", new Function0() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda43
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildTmux$lambda$41;
                buildTmux$lambda$41 = GxLogsPage.buildTmux$lambda$41(GxLogsPage.this);
                return buildTmux$lambda$41;
            }
        })}, c, false, 4, null);
        View childAt = btns.getChildAt(0);
        Intrinsics.checkNotNull(childAt, "null cannot be cast to non-null type android.widget.TextView");
        this.tmuxPauseBtn = (TextView) childAt;
        LinearLayout c2 = card(box);
        titleBar("Saved Session Logs", c2);
        this.tmuxList = box(c2);
        tmuxRenderList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildTmux$lambda$39(GxLogsPage this$0) {
        this$0.tmuxTogglePause();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildTmux$lambda$40(GxLogsPage this$0) {
        this$0.tmuxCapture();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildTmux$lambda$41(GxLogsPage this$0) {
        this$0.tmuxDeleteAll();
        return Unit.INSTANCE;
    }

    private final void tmuxSnapshot() {
        if (this.tmuxBusy) {
            return;
        }
        this.tmuxBusy = true;
        getJson("/api/tmux_log/snapshot", new Function2() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda32
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit tmuxSnapshot$lambda$43;
                tmuxSnapshot$lambda$43 = GxLogsPage.tmuxSnapshot$lambda$43(GxLogsPage.this, (String) obj, (JSONObject) obj2);
                return tmuxSnapshot$lambda$43;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tmuxSnapshot$lambda$43(GxLogsPage this$0, String e, JSONObject o) {
        this$0.tmuxBusy = false;
        if (e != null || o == null) {
            return Unit.INSTANCE;
        }
        this$0.tmuxLatest = GxPage.str$default(this$0, o, "data", null, 2, null);
        if (!this$0.tmuxPaused && this$0.tmuxLog != null) {
            TextView textView = this$0.tmuxLog;
            if (textView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tmuxLog");
                textView = null;
            }
            String str = this$0.tmuxLatest;
            if (str.length() == 0) {
                str = "(waiting for log output…)";
            }
            textView.setText(str);
        }
        return Unit.INSTANCE;
    }

    private final void tmuxTogglePause() {
        this.tmuxPaused = !this.tmuxPaused;
        TextView textView = this.tmuxPauseBtn;
        TextView textView2 = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tmuxPauseBtn");
            textView = null;
        }
        textView.setText(this.tmuxPaused ? "Resume" : "Pause");
        TextView textView3 = this.tmuxTransport;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tmuxTransport");
            textView3 = null;
        }
        textView3.setText(this.tmuxPaused ? "paused" : "polling");
        if (!this.tmuxPaused) {
            TextView textView4 = this.tmuxLog;
            if (textView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tmuxLog");
            } else {
                textView2 = textView4;
            }
            String str = this.tmuxLatest;
            if (str.length() == 0) {
                str = "(waiting for log output…)";
            }
            textView2.setText(str);
        }
    }

    private final void loadTmuxFiles() {
        getArray("/api/tmux_log/list", new Function2() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda19
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit loadTmuxFiles$lambda$46;
                loadTmuxFiles$lambda$46 = GxLogsPage.loadTmuxFiles$lambda$46(GxLogsPage.this, (String) obj, (JSONArray) obj2);
                return loadTmuxFiles$lambda$46;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadTmuxFiles$lambda$46(GxLogsPage this$0, String e, JSONArray a) {
        List<Pair<String, Double>> emptyList;
        if (e != null || a == null) {
            emptyList = CollectionsKt.emptyList();
        } else {
            Iterable $this$map$iv = this$0.objects(a);
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            for (Object item$iv$iv : $this$map$iv) {
                JSONObject it = (JSONObject) item$iv$iv;
                destination$iv$iv.add(TuplesKt.to(GxPage.str$default(this$0, it, "filename", null, 2, null), Double.valueOf(it.optDouble("timestamp", 0.0d))));
            }
            emptyList = (List) destination$iv$iv;
        }
        this$0.tmuxFiles = emptyList;
        this$0.tmuxListLoading = false;
        this$0.tmuxRenderList();
        return Unit.INSTANCE;
    }

    private final void tmuxRenderList() {
        int i;
        if (this.tmuxList == null) {
            return;
        }
        LinearLayout linearLayout = this.tmuxList;
        LinearLayout linearLayout2 = null;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tmuxList");
            linearLayout = null;
        }
        linearLayout.removeAllViews();
        if (this.tmuxListLoading) {
            LinearLayout linearLayout3 = this.tmuxList;
            if (linearLayout3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tmuxList");
            } else {
                linearLayout2 = linearLayout3;
            }
            linearLayout2.addView(empty("Loading..."));
            return;
        }
        if (this.tmuxFiles.isEmpty()) {
            LinearLayout linearLayout4 = this.tmuxList;
            if (linearLayout4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tmuxList");
            } else {
                linearLayout2 = linearLayout4;
            }
            linearLayout2.addView(empty("No tmux logs found."));
            return;
        }
        for (Pair<String, Double> pair : this.tmuxFiles) {
            final String name = pair.component1();
            double ts = pair.component2().doubleValue();
            GxLogsPage gxLogsPage = this;
            String format = DateFormat.getDateTimeInstance(2, 2).format(new Date((long) (1000 * ts)));
            LinearLayout linearLayout5 = this.tmuxList;
            if (linearLayout5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tmuxList");
                linearLayout5 = null;
            }
            GxPage.Row r = GxPage.row$default(gxLogsPage, name, format, linearLayout5, 0, 8, null);
            r.getView().setBackground(CarUi.rounded$default(getUi(), CarUi.Companion.getCARD_ON(), 14, null, 4, null));
            GxPage.Row.button$default(r, "Rename", false, new Function0() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda25
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit tmuxRenderList$lambda$47;
                    tmuxRenderList$lambda$47 = GxLogsPage.tmuxRenderList$lambda$47(GxLogsPage.this, name);
                    return tmuxRenderList$lambda$47;
                }
            }, 2, null);
            GxPage.Row.button$default(r, "Download", false, new Function0() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda26
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit tmuxRenderList$lambda$48;
                    tmuxRenderList$lambda$48 = GxLogsPage.tmuxRenderList$lambda$48(GxLogsPage.this, name);
                    return tmuxRenderList$lambda$48;
                }
            }, 2, null);
            TextView button$default = GxPage.Row.button$default(r, "Delete", false, new Function0() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda27
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit tmuxRenderList$lambda$49;
                    tmuxRenderList$lambda$49 = GxLogsPage.tmuxRenderList$lambda$49(GxLogsPage.this, name);
                    return tmuxRenderList$lambda$49;
                }
            }, 2, null);
            i = GxLogsKt.GXLOGS_RED;
            button$default.setTextColor(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tmuxRenderList$lambda$47(GxLogsPage this$0, String $name) {
        this$0.tmuxRename($name);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tmuxRenderList$lambda$48(GxLogsPage this$0, String $name) {
        this$0.download("/api/tmux_log/download/" + this$0.enc($name), $name, "application/json");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tmuxRenderList$lambda$49(GxLogsPage this$0, String $name) {
        this$0.tmuxDelete($name);
        return Unit.INSTANCE;
    }

    private final void tmuxCapture() {
        GxPage.send$default(this, "POST", "/api/tmux_log/capture", null, 0L, new Function2() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit tmuxCapture$lambda$50;
                tmuxCapture$lambda$50 = GxLogsPage.tmuxCapture$lambda$50(GxLogsPage.this, (String) obj, (JSONObject) obj2);
                return tmuxCapture$lambda$50;
            }
        }, 12, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tmuxCapture$lambda$50(GxLogsPage this$0, String e, JSONObject jSONObject) {
        this$0.toast(e == null ? "Current session captured!" : "Capture failed.");
        this$0.tmuxListLoading = true;
        this$0.tmuxRenderList();
        this$0.loadTmuxFiles();
        return Unit.INSTANCE;
    }

    private final void tmuxDelete(final String name) {
        confirm("Delete log?", "Delete " + name + "?", "Delete", new Function0() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit tmuxDelete$lambda$53;
                tmuxDelete$lambda$53 = GxLogsPage.tmuxDelete$lambda$53(GxLogsPage.this, name);
                return tmuxDelete$lambda$53;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tmuxDelete$lambda$53(final GxLogsPage this$0, final String $name) {
        GxPage.send$default(this$0, "DELETE", "/api/tmux_log/delete/" + this$0.enc($name), null, 0L, new Function2() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda29
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit tmuxDelete$lambda$53$lambda$52;
                tmuxDelete$lambda$53$lambda$52 = GxLogsPage.tmuxDelete$lambda$53$lambda$52(this$0, $name, (String) obj, (JSONObject) obj2);
                return tmuxDelete$lambda$53$lambda$52;
            }
        }, 12, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tmuxDelete$lambda$53$lambda$52(GxLogsPage this$0, String $name, String e, JSONObject jSONObject) {
        this$0.toast(e == null ? "Deleted!" : "Delete failed.");
        Iterable $this$filter$iv = this$0.tmuxFiles;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            Pair it = (Pair) element$iv$iv;
            if (!Intrinsics.areEqual(it.getFirst(), $name)) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        this$0.tmuxFiles = (List) destination$iv$iv;
        this$0.tmuxRenderList();
        return Unit.INSTANCE;
    }

    private final void tmuxDeleteAll() {
        confirm("Delete all logs?", "This cannot be undone.", "Delete All", new Function0() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda36
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit tmuxDeleteAll$lambda$55;
                tmuxDeleteAll$lambda$55 = GxLogsPage.tmuxDeleteAll$lambda$55(GxLogsPage.this);
                return tmuxDeleteAll$lambda$55;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tmuxDeleteAll$lambda$55(final GxLogsPage this$0) {
        GxPage.send$default(this$0, "DELETE", "/api/tmux_log/delete_all", null, 0L, new Function2() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda50
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit tmuxDeleteAll$lambda$55$lambda$54;
                tmuxDeleteAll$lambda$55$lambda$54 = GxLogsPage.tmuxDeleteAll$lambda$55$lambda$54(this$0, (String) obj, (JSONObject) obj2);
                return tmuxDeleteAll$lambda$55$lambda$54;
            }
        }, 12, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tmuxDeleteAll$lambda$55$lambda$54(GxLogsPage this$0, String e, JSONObject jSONObject) {
        this$0.toast(e == null ? "All logs deleted!" : "Delete failed.");
        this$0.tmuxFiles = CollectionsKt.emptyList();
        this$0.tmuxRenderList();
        return Unit.INSTANCE;
    }

    private final void tmuxRename(final String old) {
        GxPage.prompt$default(this, "Rename log", "New file name", old, 0, null, new Function1() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda39
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit tmuxRename$lambda$57;
                tmuxRename$lambda$57 = GxLogsPage.tmuxRename$lambda$57(old, GxLogsPage.this, (String) obj);
                return tmuxRename$lambda$57;
            }
        }, 24, null);
    }

    /* VF: from Vineflower (jadx: Type inference failed) */
    public static final Unit tmuxRename$lambda$57(String var0, GxLogsPage var1, String var2) {
        Intrinsics.checkNotNullParameter(var2, "input");
        ObjectRef var4 = new ObjectRef();
        var4.element = StringsKt.trim(var2).toString();
        boolean var3;
        if (((CharSequence)var4.element).length() == 0) {
            var3 = true;
        } else {
            var3 = false;
        }

        if (!var3 && !Intrinsics.areEqual(var4.element, var0)) {
            if (!StringsKt.endsWith((String)var4.element, ".json", false)) {
                Object var7 = var4.element;
                var4.element = (var7 + ".json");
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

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tmuxRename$lambda$57$lambda$56(GxLogsPage this$0, String $old, Ref.ObjectRef $newName, String e, JSONObject jSONObject) {
        StringBuilder append;
        if (e != null) {
            append = new StringBuilder().append("Rename failed: ").append(e);
        } else {
            append = new StringBuilder().append($old).append(" renamed to ").append($newName.element).append("!");
        }
        this$0.toast(append.toString());
        this$0.loadTmuxFiles();
        return Unit.INSTANCE;
    }

    /* compiled from: GxLogs.kt */
    private static final class MonTile {
        private final ProgressBar bar;
        private final LinearLayout root;
        private final TextView small;
        private final GxLogsSpark spark;
        private final TextView value;

        public MonTile(LinearLayout root, TextView value, TextView small, ProgressBar bar, GxLogsSpark spark) {
            Intrinsics.checkNotNullParameter(root, "root");
            Intrinsics.checkNotNullParameter(value, "value");
            Intrinsics.checkNotNullParameter(small, "small");
            this.root = root;
            this.value = value;
            this.small = small;
            this.bar = bar;
            this.spark = spark;
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

    private final void buildMonitor(LinearLayout box) {
        GxPage.Row row;
        int i;
        LinearLayout line;
        int i2;
        int i3;
        GxLogsSpark sp;
        this.monStatus = GxPage.row$default(this, "System Monitor", "Live updates · Captured —", box, 0, 8, null);
        GxPage.Row row2 = this.monStatus;
        if (row2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("monStatus");
            row = null;
        } else {
            row = row2;
        }
        this.monPauseBtn = GxPage.Row.button$default(row, "Pause", false, new Function0() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda22
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildMonitor$lambda$58;
                buildMonitor$lambda$58 = GxLogsPage.buildMonitor$lambda$58(GxLogsPage.this);
                return buildMonitor$lambda$58;
            }
        }, 2, null);
        TextView $this$buildMonitor_u24lambda_u2459 = note("", box);
        i = GxLogsKt.GXLOGS_RED;
        $this$buildMonitor_u24lambda_u2459.setTextColor(i);
        $this$buildMonitor_u24lambda_u2459.setVisibility(8);
        this.monErrNote = $this$buildMonitor_u24lambda_u2459;
        this.monWait = empty("Reading system activity…");
        TextView textView = this.monWait;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("monWait");
            textView = null;
        }
        box.addView(textView);
        LinearLayout $this$buildMonitor_u24lambda_u2460 = box(box);
        $this$buildMonitor_u24lambda_u2460.setVisibility(8);
        this.monBody = $this$buildMonitor_u24lambda_u2460;
        int i4 = 0;
        int i5 = 1;
        int i6 = 4;
        String str = "vram";
        List<Pair> specs = CollectionsKt.listOf(new Pair[]{TuplesKt.to("cpu", "CPU"), TuplesKt.to("mem", "Memory"), TuplesKt.to("procs", "Processes"), TuplesKt.to("storage", "Storage"), TuplesKt.to("cpuTemp", "Onboard CPU temperature"), TuplesKt.to("gpuTemp", "Onboard GPU temperature"), TuplesKt.to("hotspot", "eGPU hotspot temperature"), TuplesKt.to("edge", "eGPU temperature"), TuplesKt.to("vram", "eGPU VRAM")});
        LinearLayout line2 = null;
        int i7 = 0;
        for (Pair s : specs) {
            int i8 = i7;
            int i9 = i7 + 1;
            if (i8 % 3 == 0) {
                LinearLayout line3 = new LinearLayout(getAct());
                line3.setOrientation(i4);
                LinearLayout $this$buildMonitor_u24lambda_u2461 = this.monBody;
                if ($this$buildMonitor_u24lambda_u2461 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("monBody");
                    $this$buildMonitor_u24lambda_u2461 = null;
                }
                $this$buildMonitor_u24lambda_u2461.addView(line3, new LinearLayout.LayoutParams(-1, -2));
                line = line3;
            } else {
                line = line2;
            }
            LinearLayout $this$buildMonitor_u24lambda_u2462 = new LinearLayout(getAct());
            $this$buildMonitor_u24lambda_u2462.setOrientation(i5);
            $this$buildMonitor_u24lambda_u2462.setBackground(CarUi.rounded$default(getUi(), CarUi.Companion.getCARD(), 0, null, 6, null));
            $this$buildMonitor_u24lambda_u2462.setPadding(getUi().dp(18), getUi().dp(14), getUi().dp(18), getUi().dp(14));
            $this$buildMonitor_u24lambda_u2462.setMinimumHeight(getUi().dp(130));
            Intrinsics.checkNotNull(line);
            LinearLayout linearLayout = $this$buildMonitor_u24lambda_u2462;
            LinearLayout.LayoutParams $this$buildMonitor_u24lambda_u2463 = new LinearLayout.LayoutParams(i4, -1, 1.0f);
            if (i8 % 3 < 2) {
                i2 = 8;
                i3 = getUi().dp(8);
            } else {
                i2 = 8;
                i3 = i4;
            }
            $this$buildMonitor_u24lambda_u2463.rightMargin = i3;
            $this$buildMonitor_u24lambda_u2463.bottomMargin = getUi().dp(i2);
            Unit unit = Unit.INSTANCE;
            line.addView(linearLayout, $this$buildMonitor_u24lambda_u2463);
            GxPage.text$default(this, (String) s.getSecond(), $this$buildMonitor_u24lambda_u2462, 0.0f, 0, false, 28, null);
            String str2 = str;
            TextView v = text("—", $this$buildMonitor_u24lambda_u2462, 28.0f, CarUi.Companion.getTEXT(), true);
            TextView sm = GxPage.text$default(this, "", $this$buildMonitor_u24lambda_u2462, 14.0f, 0, false, 24, null);
            ProgressBar bar = (Intrinsics.areEqual(s.getFirst(), "mem") || Intrinsics.areEqual(s.getFirst(), str2)) ? progress($this$buildMonitor_u24lambda_u2462) : null;
            if (Intrinsics.areEqual(s.getFirst(), "cpu")) {
                GxLogsSpark it = new GxLogsSpark(getAct());
                GxLogsSpark gxLogsSpark = it;
                LinearLayout.LayoutParams $this$buildMonitor_u24lambda_u2465_u24lambda_u2464 = new LinearLayout.LayoutParams(-1, getUi().dp(40));
                i6 = 4;
                $this$buildMonitor_u24lambda_u2465_u24lambda_u2464.topMargin = getUi().dp(4);
                Unit unit2 = Unit.INSTANCE;
                $this$buildMonitor_u24lambda_u2462.addView(gxLogsSpark, $this$buildMonitor_u24lambda_u2465_u24lambda_u2464);
                sp = it;
            } else {
                i6 = 4;
                sp = null;
            }
            this.monTiles.put(((String) s.getFirst()), new MonTile($this$buildMonitor_u24lambda_u2462, v, sm, bar, sp));
            line2 = line;
            str = str2;
            i7 = i9;
            i4 = 0;
            i5 = 1;
        }
        LinearLayout linearLayout2 = this.monBody;
        if (linearLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("monBody");
            linearLayout2 = null;
        }
        LinearLayout cores = card(linearLayout2);
        LinearLayout cbar = titleBar("CPU cores", cores);
        this.monCoresBtn = CarUi.button$default(getUi(), "Show", false, new Function0() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda33
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildMonitor$lambda$66;
                buildMonitor$lambda$66 = GxLogsPage.buildMonitor$lambda$66(GxLogsPage.this);
                return buildMonitor$lambda$66;
            }
        }, 2, null);
        TextView textView2 = this.monCoresBtn;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("monCoresBtn");
            textView2 = null;
        }
        cbar.addView(textView2);
        LinearLayout $this$buildMonitor_u24lambda_u2467 = box(cores);
        $this$buildMonitor_u24lambda_u2467.setVisibility(8);
        this.monCoresBox = $this$buildMonitor_u24lambda_u2467;
        LinearLayout linearLayout3 = this.monBody;
        if (linearLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("monBody");
            linearLayout3 = null;
        }
        searchField("Search feature, process, PID or user…", linearLayout3, new Function1() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda44
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit buildMonitor$lambda$68;
                buildMonitor$lambda$68 = GxLogsPage.buildMonitor$lambda$68(GxLogsPage.this, (String) obj);
                return buildMonitor$lambda$68;
            }
        });
        final List scopes = CollectionsKt.listOf(new String[]{"comma", "users", "all"});
        List<String> listOf = CollectionsKt.listOf(new String[]{"Comma processes", "Apps and services", "All processes"});
        LinearLayout linearLayout4 = this.monBody;
        if (linearLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("monBody");
            linearLayout4 = null;
        }
        choice("Process group", null, listOf, 0, linearLayout4, new Function1() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda47
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit buildMonitor$lambda$69;
                buildMonitor$lambda$69 = GxLogsPage.buildMonitor$lambda$69(GxLogsPage.this, scopes, ((Integer) obj).intValue());
                return buildMonitor$lambda$69;
            }
        });
        LinearLayout linearLayout5 = this.monBody;
        if (linearLayout5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("monBody");
            linearLayout5 = null;
        }
        this.monCountNote = note("", linearLayout5);
        LinearLayout linearLayout6 = this.monBody;
        if (linearLayout6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("monBody");
            linearLayout6 = null;
        }
        header("Sort by", linearLayout6);
        GxLogsPage gxLogsPage = this;
        Iterable $this$map$iv = this.monSortCols;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            final Pair c = (Pair) item$iv$iv;
            destination$iv$iv.add(TuplesKt.to(c.getSecond(), new Function0() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda48
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit buildMonitor$lambda$71$lambda$70;
                    buildMonitor$lambda$71$lambda$70 = GxLogsPage.buildMonitor$lambda$71$lambda$70(GxLogsPage.this, c);
                    return buildMonitor$lambda$71$lambda$70;
                }
            }));
        }
        Collection thisCollection$iv = (List) destination$iv$iv;
        Pair[] pairArr = (Pair[]) thisCollection$iv.toArray(new Pair[0]);
        Pair[] pairArr2 = (Pair[]) Arrays.copyOf(pairArr, pairArr.length);
        LinearLayout linearLayout7 = this.monBody;
        if (linearLayout7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("monBody");
            linearLayout7 = null;
        }
        this.monSortBar = GxPage.buttons$default(gxLogsPage, pairArr2, linearLayout7, false, 4, null);
        LinearLayout linearLayout8 = this.monBody;
        if (linearLayout8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("monBody");
            linearLayout8 = null;
        }
        LinearLayout tcard = card(linearLayout8);
        LinearLayout head = new LinearLayout(getAct());
        head.setOrientation(0);
        head.setPadding(0, getUi().dp(4), 0, getUi().dp(8));
        Iterator it2 = this.monSortCols.iterator();
        int i10 = 0;
        while (it2.hasNext()) {
            int i11 = i10;
            i10++;
            Pair c2 = (Pair) it2.next();
            TextView $this$buildMonitor_u24lambda_u2473 = new TextView(getAct());
            $this$buildMonitor_u24lambda_u2473.setText((CharSequence) c2.getSecond());
            $this$buildMonitor_u24lambda_u2473.setTextSize(15.0f);
            $this$buildMonitor_u24lambda_u2473.setTextColor(CarUi.Companion.getDIM());
            $this$buildMonitor_u24lambda_u2473.setTypeface(Typeface.DEFAULT_BOLD);
            head.addView($this$buildMonitor_u24lambda_u2473, new LinearLayout.LayoutParams(0, -2, this.monWeights[i11]));
        }
        tcard.addView(head);
        this.monTable = box(tcard);
        monPaintSortBar();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildMonitor$lambda$58(GxLogsPage this$0) {
        this$0.monTogglePause();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildMonitor$lambda$66(GxLogsPage this$0) {
        this$0.monCoresOpen = !this$0.monCoresOpen;
        TextView textView = this$0.monCoresBtn;
        LinearLayout linearLayout = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("monCoresBtn");
            textView = null;
        }
        textView.setText(this$0.monCoresOpen ? "Hide" : "Show");
        LinearLayout linearLayout2 = this$0.monCoresBox;
        if (linearLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("monCoresBox");
        } else {
            linearLayout = linearLayout2;
        }
        linearLayout.setVisibility(this$0.monCoresOpen ? 0 : 8);
        this$0.monPaintCores();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildMonitor$lambda$68(GxLogsPage this$0, String q) {
        Intrinsics.checkNotNullParameter(q, "q");
        this$0.monQuery = q;
        this$0.monPaintTable();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildMonitor$lambda$69(GxLogsPage this$0, List $scopes, int i) {
        this$0.monScope = (String) $scopes.get(i);
        this$0.monPaintTable();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildMonitor$lambda$71$lambda$70(GxLogsPage this$0, Pair $c) {
        this$0.monSortBy((String) $c.getFirst());
        return Unit.INSTANCE;
    }

    private final void monTogglePause() {
        this.monPaused = !this.monPaused;
        TextView textView = this.monPauseBtn;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("monPauseBtn");
            textView = null;
        }
        textView.setText(this.monPaused ? "Resume" : "Pause");
        if (!this.monPaused) {
            this.monSensorNow = SystemClock.elapsedRealtime();
            monRefresh();
        }
        monPaintStatus();
        monPaintVitals();
    }

    private final void monRefresh() {
        if (this.monLoading || this.monPaused || this.monStatus == null) {
            return;
        }
        this.monLoading = true;
        final long started = SystemClock.elapsedRealtime();
        GxPage.json$default(this, "GET", "/api/system/monitor", null, 8L, new Function2() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda30
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit monRefresh$lambda$75;
                monRefresh$lambda$75 = GxLogsPage.monRefresh$lambda$75(GxLogsPage.this, started, (String) obj, obj2);
                return monRefresh$lambda$75;
            }
        }, 4, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit monRefresh$lambda$75(GxLogsPage this$0, long $started, String e, Object v) {
        List<GxLogsProc> emptyList;
        Iterable objects;
        boolean z = false;
        this$0.monLoading = false;
        JSONObject data = v instanceof JSONObject ? (JSONObject) v : null;
        if (e == null && data != null) {
            if (!data.has("error") || data.has("processes")) {
                Double cpu = this$0.optNum(data, "cpuPercent");
                if (!Intrinsics.areEqual(this$0.optNum(data, "sampledAt"), this$0.optNum(this$0.monSnap, "sampledAt")) && cpu != null) {
                    this$0.monHistory = CollectionsKt.takeLast(CollectionsKt.plus((Collection<? extends Double>) this$0.monHistory, cpu), 30);
                }
                this$0.monSnap = data;
                this$0.monError = "";
                this$0.monReceivedAt = $started;
                this$0.monSensorNow = SystemClock.elapsedRealtime();
                JSONArray optJSONArray = data.optJSONArray("processes");
                if (optJSONArray == null || (objects = this$0.objects(optJSONArray)) == null) {
                    emptyList = CollectionsKt.emptyList();
                } else {
                    Iterable $this$map$iv = objects;
                    Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                    for (Object item$iv$iv : $this$map$iv) {
                        JSONObject p = (JSONObject) item$iv$iv;
                        String name = GxPage.str$default(this$0, p, "name", null, 2, null);
                        boolean kernel = p.optBoolean("kernel", z);
                        destination$iv$iv.add(new GxLogsProc(p.optInt("pid"), name, GxPage.str$default(this$0, p, "user", null, 2, null), kernel, GxPage.str$default(this$0, p, "state", null, 2, null), this$0.optNum(p, "cpu"), this$0.optNum(p, "memoryMiB"), this$0.monFeature(name, kernel)));
                        data = data;
                        cpu = cpu;
                        z = false;
                    }
                    emptyList = (List) destination$iv$iv;
                }
                this$0.monProcs = emptyList;
                this$0.monPaintAll();
                return Unit.INSTANCE;
            }
        }
        this$0.monError = "Cannot refresh system monitor. Showing the last captured values.";
        this$0.monPaintAll();
        return Unit.INSTANCE;
    }

    private final String monFeature(String name, boolean kernel) {
        Map map;
        if (kernel) {
            return "";
        }
        String n = StringsKt.removePrefix(StringsKt.replace(StringsKt.removeSuffix(StringsKt.removePrefix(StringsKt.removePrefix(name, (CharSequence) "/data/openpilot/"), (CharSequence) "./"), (CharSequence) ".py"), "/", ".", false), (CharSequence) "openpilot.");
        map = GxLogsKt.GXLOGS_FEATURES;
        String str = (String) map.get(n);
        return str == null ? "" : str;
    }

    private final String monNumber(Double v, String suffix) {
        if (v == null) {
            return "—";
        }
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String format = String.format(Locale.US, "%.1f", Arrays.copyOf(new Object[]{v}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format + suffix;
    }

    static /* synthetic */ String monNumber$default(GxLogsPage gxLogsPage, Double d, String str, int i, Object obj) {
        if ((i & 2) != 0) {
            str = "";
        }
        return gxLogsPage.monNumber(d, str);
    }

    private final String monState(String s) {
        String str = (String) MapsKt.mapOf(TuplesKt.to("R", "Running"), TuplesKt.to("S", "Sleeping"), TuplesKt.to("D", "Waiting"), TuplesKt.to("T", "Stopped"), TuplesKt.to("t", "Tracing"), TuplesKt.to("Z", "Zombie"), TuplesKt.to("I", "Idle")).get(s);
        return str == null ? s : str;
    }

    private final Double monSensor(String field, String ageKey) {
        JSONObject vit;
        Double optNum;
        JSONObject jSONObject = this.monSnap;
        if (jSONObject == null || (vit = jSONObject.optJSONObject("vitals")) == null || (optNum = optNum(vit, ageKey)) == null) {
            return null;
        }
        double age = optNum.doubleValue();
        if ((this.monError.length() > 0) || age <= 0.0d || this.monSensorNow - this.monReceivedAt >= age) {
            return null;
        }
        return optNum(vit, field);
    }

    private final void monPaintAll() {
        monPaintStatus();
        monPaintVitals();
        monPaintCores();
        monPaintTable();
    }

    private final void monPaintStatus() {
        String captured;
        String str;
        JSONObject s = this.monSnap;
        if (s == null) {
            captured = "—";
        } else {
            DateFormat timeInstance = DateFormat.getTimeInstance(2);
            Double optNum = optNum(s, "sampledAt");
            captured = timeInstance.format(new Date((long) ((optNum != null ? optNum.doubleValue() : 0.0d) * 1000)));
        }
        GxPage.Row row = this.monStatus;
        LinearLayout linearLayout = null;
        if (row == null) {
            Intrinsics.throwUninitializedPropertyAccessException("monStatus");
            row = null;
        }
        if (this.monPaused) {
            str = "Paused";
        } else {
            str = this.monError.length() > 0 ? "Connection interrupted" : "Live updates";
        }
        row.setDesc(str + " · Captured " + captured);
        TextView textView = this.monErrNote;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("monErrNote");
            textView = null;
        }
        textView.setText(this.monError);
        TextView textView2 = this.monErrNote;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("monErrNote");
            textView2 = null;
        }
        textView2.setVisibility(this.monError.length() > 0 ? 0 : 8);
        TextView textView3 = this.monWait;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("monWait");
            textView3 = null;
        }
        textView3.setVisibility(s == null ? 0 : 8);
        TextView textView4 = this.monWait;
        if (textView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("monWait");
            textView4 = null;
        }
        textView4.setText(this.monError.length() > 0 ? "Waiting for the device…" : "Reading system activity…");
        LinearLayout linearLayout2 = this.monBody;
        if (linearLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("monBody");
        } else {
            linearLayout = linearLayout2;
        }
        linearLayout.setVisibility(s == null ? 8 : 0);
    }

    private final void monPaintVitals() {
        JSONObject mem;
        Double d;
        String str;
        String str2;
        TextView textView;
        String str3;
        String str4;
        ProgressBar it;
        JSONObject s = this.monSnap;
        if (s == null || this.monStatus == null) {
            return;
        }
        JSONArray optJSONArray = s.optJSONArray("cores");
        int cores = optJSONArray != null ? optJSONArray.length() : 0;
        MonTile $this$monPaintVitals_u24lambda_u2476 = monPaintVitals$tile(this, "cpu");
        $this$monPaintVitals_u24lambda_u2476.getValue().setText(monNumber(optNum(s, "cpuPercent"), "%"));
        $this$monPaintVitals_u24lambda_u2476.getSmall().setText(cores + " cores · overall usage");
        GxLogsSpark spark = $this$monPaintVitals_u24lambda_u2476.getSpark();
        if (spark != null) {
            spark.setValues(this.monHistory);
        }
        JSONObject mem2 = s.optJSONObject("memory");
        MonTile $this$monPaintVitals_u24lambda_u2478 = monPaintVitals$tile(this, "mem");
        Double pct = optNum(mem2, "percent");
        $this$monPaintVitals_u24lambda_u2478.getValue().setText(monNumber(pct, "%"));
        TextView small = $this$monPaintVitals_u24lambda_u2478.getSmall();
        Double optNum = optNum(mem2, "usedMiB");
        if (optNum != null) {
            mem = mem2;
            d = Double.valueOf(optNum.doubleValue() / 1024);
        } else {
            mem = mem2;
            d = null;
        }
        String monNumber$default = monNumber$default(this, d, null, 2, null);
        Double optNum2 = optNum(mem, "totalMiB");
        small.setText(monNumber$default + " / " + monNumber$default(this, optNum2 != null ? Double.valueOf(optNum2.doubleValue() / 1024) : null, null, 2, null) + " GiB");
        ProgressBar it2 = $this$monPaintVitals_u24lambda_u2478.getBar();
        if (it2 != null) {
            setProgress(it2, pct != null ? pct.doubleValue() : 0.0d);
        }
        Double optNum3 = optNum(s, "uptimeSeconds");
        double up = optNum3 != null ? optNum3.doubleValue() : 0.0d;
        MonTile $this$monPaintVitals_u24lambda_u2479 = monPaintVitals$tile(this, "procs");
        TextView value = $this$monPaintVitals_u24lambda_u2479.getValue();
        Double optNum4 = optNum(s, "processCount");
        value.setText(String.valueOf(optNum4 != null ? (long) optNum4.doubleValue() : 0L));
        $this$monPaintVitals_u24lambda_u2479.getSmall().setText("Uptime " + ((long) (up / 3600)) + "h " + (((long) (up / 60)) % 60) + "m");
        JSONObject st = s.optJSONObject("storage");
        MonTile $this$monPaintVitals_u24lambda_u2482 = monPaintVitals$tile(this, "storage");
        TextView value2 = $this$monPaintVitals_u24lambda_u2482.getValue();
        Double optNum5 = optNum(st, "usedGiB");
        if (optNum5 == null || (str = jsNum(optNum5.doubleValue())) == null) {
            str = "—";
        }
        value2.setText(str + " GiB");
        TextView small2 = $this$monPaintVitals_u24lambda_u2482.getSmall();
        Double optNum6 = optNum(st, "totalGiB");
        if (optNum6 == null || (str2 = jsNum(optNum6.doubleValue())) == null) {
            str2 = "—";
        }
        small2.setText(str2 + " GiB total");
        monPaintVitals$tile(this, "cpuTemp").getValue().setText(monNumber(monSensor("cpuTempC", "onboardMaxAgeMs"), " °C"));
        monPaintVitals$tile(this, "gpuTemp").getValue().setText(monNumber(monSensor("gpuTempC", "onboardMaxAgeMs"), " °C"));
        monPaintVitals$tile(this, "hotspot").getValue().setText(monNumber(monSensor("hotspotTempC", "maxAgeMs"), " °C"));
        JSONObject vit = s.optJSONObject("vitals");
        MonTile $this$monPaintVitals_u24lambda_u2483 = monPaintVitals$tile(this, "edge");
        $this$monPaintVitals_u24lambda_u2483.getRoot().setVisibility((vit == null || !vit.has("gpuEdgeTempC") || vit.isNull("gpuEdgeTempC")) ? 4 : 0);
        $this$monPaintVitals_u24lambda_u2483.getValue().setText(monNumber(monSensor("gpuEdgeTempC", "maxAgeMs"), " °C"));
        Double used = monSensor("memoryUsedBytes", "memoryMaxAgeMs");
        Double total = monSensor("memoryTotalBytes", "memoryMaxAgeMs");
        MonTile $this$monPaintVitals_u24lambda_u2485 = monPaintVitals$tile(this, "vram");
        TextView value3 = $this$monPaintVitals_u24lambda_u2485.getValue();
        if (used == null) {
            textView = value3;
        } else {
            textView = value3;
            str3 = monNumber$default(this, Double.valueOf(used.doubleValue() / 1.073741824E9d), null, 2, null) + " GiB";
        }
        textView.setText(str3);
        TextView small3 = $this$monPaintVitals_u24lambda_u2485.getSmall();
        if (total != null) {
            str4 = monNumber$default(this, Double.valueOf(total.doubleValue() / 1.073741824E9d), null, 2, null) + " GiB total · " + monNumber(used == null ? null : Double.valueOf((100 * used.doubleValue()) / total.doubleValue()), "%");
        }
        small3.setText(str4);
        ProgressBar bar = $this$monPaintVitals_u24lambda_u2485.getBar();
        if (bar != null) {
            bar.setVisibility((total == null || total.doubleValue() <= 0.0d || used == null) ? 8 : 0);
        }
        if (total == null || total.doubleValue() <= 0.0d || used == null || (it = $this$monPaintVitals_u24lambda_u2485.getBar()) == null) {
            return;
        }
        setProgress(it, (100 * used.doubleValue()) / total.doubleValue());
    }

    private static final MonTile monPaintVitals$tile(GxLogsPage this$0, String k) {
        MonTile monTile = this$0.monTiles.get(k);
        Intrinsics.checkNotNull(monTile);
        return monTile;
    }

    private final void monPaintCores() {
        List arr;
        int i;
        LinearLayout linearLayout;
        JSONArray optJSONArray;
        if (this.monCoresOpen) {
            JSONObject jSONObject = this.monSnap;
            if (jSONObject == null || (optJSONArray = jSONObject.optJSONArray("cores")) == null || (arr = objects(optJSONArray)) == null) {
                arr = CollectionsKt.emptyList();
            }
            while (true) {
                LinearLayout linearLayout2 = this.monCoresBox;
                if (linearLayout2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("monCoresBox");
                    linearLayout2 = null;
                }
                i = 1;
                if (linearLayout2.getChildCount() <= arr.size()) {
                    break;
                }
                LinearLayout linearLayout3 = this.monCoresBox;
                if (linearLayout3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("monCoresBox");
                    linearLayout3 = null;
                }
                LinearLayout linearLayout4 = this.monCoresBox;
                if (linearLayout4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("monCoresBox");
                    linearLayout = null;
                } else {
                    linearLayout = linearLayout4;
                }
                linearLayout3.removeViewAt(linearLayout.getChildCount() - 1);
            }
            Iterator it = arr.iterator();
            int i2 = 0;
            int i3 = 0;
            while (it.hasNext()) {
                int i4 = i3;
                i3 += i;
                JSONObject c = (JSONObject) it.next();
                LinearLayout linearLayout5 = this.monCoresBox;
                if (linearLayout5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("monCoresBox");
                    linearLayout5 = null;
                }
                View childAt = linearLayout5.getChildAt(i4);
                LinearLayout $this$monPaintCores_u24lambda_u2489 = childAt instanceof LinearLayout ? (LinearLayout) childAt : null;
                if ($this$monPaintCores_u24lambda_u2489 == null) {
                    $this$monPaintCores_u24lambda_u2489 = new LinearLayout(getAct());
                    $this$monPaintCores_u24lambda_u2489.setOrientation(i2);
                    $this$monPaintCores_u24lambda_u2489.setGravity(16);
                    $this$monPaintCores_u24lambda_u2489.setPadding(i2, getUi().dp(4), i2, getUi().dp(4));
                    TextView $this$monPaintCores_u24lambda_u2489_u24lambda_u2486 = new TextView(getAct());
                    $this$monPaintCores_u24lambda_u2489_u24lambda_u2486.setTextSize(16.0f);
                    $this$monPaintCores_u24lambda_u2489_u24lambda_u2486.setTextColor(CarUi.Companion.getTEXT());
                    $this$monPaintCores_u24lambda_u2489.addView($this$monPaintCores_u24lambda_u2489_u24lambda_u2486, new LinearLayout.LayoutParams(getUi().dp(90), -2));
                    TextView $this$monPaintCores_u24lambda_u2489_u24lambda_u2487 = new TextView(getAct());
                    $this$monPaintCores_u24lambda_u2489_u24lambda_u2487.setTextSize(16.0f);
                    $this$monPaintCores_u24lambda_u2489_u24lambda_u2487.setTextColor(CarUi.Companion.getTEXT());
                    $this$monPaintCores_u24lambda_u2489_u24lambda_u2487.setTypeface(Typeface.DEFAULT_BOLD);
                    $this$monPaintCores_u24lambda_u2489.addView($this$monPaintCores_u24lambda_u2489_u24lambda_u2487, new LinearLayout.LayoutParams(getUi().dp(90), -2));
                    ProgressBar $this$monPaintCores_u24lambda_u2489_u24lambda_u2488 = new ProgressBar(getAct(), null, 16842872);
                    $this$monPaintCores_u24lambda_u2489_u24lambda_u2488.setMax(100);
                    $this$monPaintCores_u24lambda_u2489_u24lambda_u2488.setProgressTintList(ColorStateList.valueOf(CarUi.Companion.getACCENT()));
                    $this$monPaintCores_u24lambda_u2489.addView($this$monPaintCores_u24lambda_u2489_u24lambda_u2488, new LinearLayout.LayoutParams(0, getUi().dp(14), 1.0f));
                    LinearLayout linearLayout6 = this.monCoresBox;
                    if (linearLayout6 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("monCoresBox");
                        linearLayout6 = null;
                    }
                    linearLayout6.addView($this$monPaintCores_u24lambda_u2489);
                }
                LinearLayout line = $this$monPaintCores_u24lambda_u2489;
                Double pct = optNum(c, "percent");
                View childAt2 = line.getChildAt(0);
                Intrinsics.checkNotNull(childAt2, "null cannot be cast to non-null type android.widget.TextView");
                TextView textView = (TextView) childAt2;
                List arr2 = arr;
                Iterator it2 = it;
                String upperCase = GxPage.str$default(this, c, "name", null, 2, null).toUpperCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
                textView.setText(upperCase);
                View childAt3 = line.getChildAt(1);
                Intrinsics.checkNotNull(childAt3, "null cannot be cast to non-null type android.widget.TextView");
                ((TextView) childAt3).setText(monNumber(pct, "%"));
                View childAt4 = line.getChildAt(2);
                Intrinsics.checkNotNull(childAt4, "null cannot be cast to non-null type android.widget.ProgressBar");
                ((ProgressBar) childAt4).setProgress(RangesKt.coerceIn((int) (pct != null ? pct.doubleValue() : 0.0d), 0, 100));
                i = 1;
                i2 = 0;
                it = it2;
                arr = arr2;
            }
        }
    }

    private final void monSortBy(String key) {
        boolean z = true;
        if (Intrinsics.areEqual(this.monSort, key)) {
            this.monDesc = !this.monDesc;
        } else {
            this.monSort = key;
            if (!Intrinsics.areEqual(key, "cpu") && !Intrinsics.areEqual(key, "memoryMiB")) {
                z = false;
            }
            this.monDesc = z;
        }
        monPaintSortBar();
        monPaintTable();
    }

    private final void monPaintSortBar() {
        Iterator it = this.monSortCols.iterator();
        int i = 0;
        while (it.hasNext()) {
            int i2 = i;
            i++;
            Pair c = (Pair) it.next();
            LinearLayout linearLayout = this.monSortBar;
            if (linearLayout == null) {
                Intrinsics.throwUninitializedPropertyAccessException("monSortBar");
                linearLayout = null;
            }
            View childAt = linearLayout.getChildAt(i2);
            TextView textView = childAt instanceof TextView ? (TextView) childAt : null;
            if (textView != null) {
                TextView b = textView;
                boolean on = Intrinsics.areEqual(this.monSort, c.getFirst());
                b.setText(c.getSecond() + (on ? this.monDesc ? " ↓" : " ↑" : ""));
                CarUi.Companion_ companion = CarUi.Companion;
                b.setTextColor(on ? companion.getACCENT() : companion.getTEXT());
            }
        }
    }

    /* VF: from Vineflower (jadx: ) */
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

    private final int monCompare(GxLogsProc a, GxLogsProc b) {
        Object av = monCompare$key(this, a);
        Object bv = monCompare$key(this, b);
        if (av == null) {
            if (bv == null) {
                return a.getPid() - b.getPid();
            }
            return 1;
        }
        if (bv == null) {
            return -1;
        }
        int r = ((av instanceof Double) && (bv instanceof Double)) ? Double.compare(((Number) av).doubleValue(), ((Number) bv).doubleValue()) : StringsKt.compareTo(av.toString(), bv.toString(), true);
        int res = this.monDesc ? -r : r;
        return res != 0 ? res : a.getPid() - b.getPid();
    }

    /* VF: from Vineflower (jadx: Code restructure failed;Removed duplicated region;incorrect) */
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
                            var16 = (((boolean)true) ? 1 : 0);
                        } else {
                            var16 = (((boolean)false) ? 1 : 0);
                        }

                        if ((var16 == 0)) {
                            String var13 = var14.getName();
                            String var12 = var14.getFeature();
                            var16 = var14.getPid();
                            String var11 = var14.getUser();
                            String var72 = this.monState(var14.getState());
                            var11 = (var13 + " " + var12 + " " + var16 + " " + var11 + " " + var72).toLowerCase(Locale.ROOT);
                            Intrinsics.checkNotNullExpressionValue(var11, "toLowerCase(...)");
                            if (!StringsKt.contains(var11, var8, false)) {
                                break label180;
                            }
                        }

                        var16 = 1;
                        break label167;
                    }

                    var16 = 0;
                }

                if ((var16 != 0)) {
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
                                    var49.addView((View)var55, (android.view.ViewGroup.LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, this.monWeights[var17])));
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
                            int var20;
                            if (var71.getFeature().length() > 0) {
                                var20 = 1;
                            } else {
                                var20 = 0;
                            }

                            if ((var20 != 0)) {
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

    /* JADX INFO: Access modifiers changed from: private */
    public static final int monPaintTable$lambda$91(GxLogsPage this$0, GxLogsProc a, GxLogsProc b) {
        Intrinsics.checkNotNull(a);
        Intrinsics.checkNotNull(b);
        return this$0.monCompare(a, b);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int monPaintTable$lambda$92(Function2 $tmp0, Object p0, Object p1) {
        return ((Number) $tmp0.invoke(p0, p1)).intValue();
    }

    private final void buildDiscord(LinearLayout box) {
        LinearLayout c = card(box);
        titleBar("Get Help via Discord", c);
        GxPage.text$default(this, "StarPilot has a vibrant, welcoming community discord. Stop by to chat or ask questions!", c, 17.0f, 0, false, 24, null);
        GxPage.text$default(this, "firestar.link/discord", c, 15.0f, CarUi.Companion.getACCENT(), false, 16, null);
        buttons(new Pair[]{TuplesKt.to("Get Help via Discord", new Function0() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda45
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildDiscord$lambda$97;
                buildDiscord$lambda$97 = GxLogsPage.buildDiscord$lambda$97(GxLogsPage.this);
                return buildDiscord$lambda$97;
            }
        })}, c, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildDiscord$lambda$97(GxLogsPage this$0) {
        try {
            this$0.getAct().startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://firestar.link/discord")));
        } catch (Exception e) {
            this$0.toast("No browser found. Visit https://firestar.link/discord");
        }
        return Unit.INSTANCE;
    }

    private final void buildTroubleshoot(LinearLayout box) {
        int i;
        LinearLayout c = card(box);
        GxPage.text$default(this, "Quick diagnostics snapshot for weird behavior reports and copy-ready debug logs.", c, 17.0f, 0, false, 24, null);
        LinearLayout btns = GxPage.buttons$default(this, new Pair[]{TuplesKt.to("Refresh", new Function0() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda14
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildTroubleshoot$lambda$99;
                buildTroubleshoot$lambda$99 = GxLogsPage.buildTroubleshoot$lambda$99(GxLogsPage.this);
                return buildTroubleshoot$lambda$99;
            }
        }), TuplesKt.to("Copy to Clipboard", new Function0() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda15
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildTroubleshoot$lambda$100;
                buildTroubleshoot$lambda$100 = GxLogsPage.buildTroubleshoot$lambda$100(GxLogsPage.this);
                return buildTroubleshoot$lambda$100;
            }
        })}, c, false, 4, null);
        View childAt = btns.getChildAt(0);
        Intrinsics.checkNotNull(childAt, "null cannot be cast to non-null type android.widget.TextView");
        this.tsRefreshBtn = (TextView) childAt;
        toggle("Only non-default values", null, false, c, new Function1() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda16
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit buildTroubleshoot$lambda$101;
                buildTroubleshoot$lambda$101 = GxLogsPage.buildTroubleshoot$lambda$101(GxLogsPage.this, ((Boolean) obj).booleanValue());
                return buildTroubleshoot$lambda$101;
            }
        }).getFirst().getView().setBackground(CarUi.rounded$default(getUi(), CarUi.Companion.getCARD_ON(), 14, null, 4, null));
        LinearLayout $this$buildTroubleshoot_u24lambda_u24102 = new LinearLayout(getAct());
        $this$buildTroubleshoot_u24lambda_u24102.setOrientation(1);
        $this$buildTroubleshoot_u24lambda_u24102.setPadding(0, getUi().dp(8), 0, 0);
        c.addView($this$buildTroubleshoot_u24lambda_u24102);
        searchField("Search settings or categories...", $this$buildTroubleshoot_u24lambda_u24102, new Function1() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit buildTroubleshoot$lambda$103;
                buildTroubleshoot$lambda$103 = GxLogsPage.buildTroubleshoot$lambda$103(GxLogsPage.this, (String) obj);
                return buildTroubleshoot$lambda$103;
            }
        });
        i = GxLogsKt.GXLOGS_RED;
        TextView $this$buildTroubleshoot_u24lambda_u24104 = GxPage.text$default(this, "", c, 15.0f, i, false, 16, null);
        $this$buildTroubleshoot_u24lambda_u24104.setVisibility(8);
        this.tsErrNote = $this$buildTroubleshoot_u24lambda_u24104;
        this.tsOnroadText = GxPage.text$default(this, "Onroad: No", c, 0.0f, 0, false, 28, null);
        this.tsChangedText = GxPage.text$default(this, "Changed Settings: 0", c, 0.0f, 0, false, 28, null);
        this.tsBody = box(box);
        tsRender();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildTroubleshoot$lambda$100(GxLogsPage this$0) {
        this$0.tsCopyReport();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildTroubleshoot$lambda$99(GxLogsPage this$0) {
        this$0.tsLoad(true);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildTroubleshoot$lambda$101(GxLogsPage this$0, boolean v) {
        this$0.tsOnlyChanged = v;
        this$0.tsRender();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildTroubleshoot$lambda$103(GxLogsPage this$0, String q) {
        Intrinsics.checkNotNullParameter(q, "q");
        this$0.tsSearch = q;
        this$0.getMain().removeCallbacks(this$0.tsSearchRun);
        this$0.getMain().postDelayed(this$0.tsSearchRun, 250L);
        return Unit.INSTANCE;
    }

    private final void tsLoad(final boolean showToast) {
        if (this.tsRefreshing) {
            return;
        }
        this.tsRefreshing = true;
        if (this.tsSections.isEmpty()) {
            this.tsLoading = true;
        }
        this.tsError = "";
        tsPaintHead();
        if (this.tsLoading) {
            tsRender();
        }
        getJson("/api/troubleshoot", new Function2() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda51
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit tsLoad$lambda$105;
                tsLoad$lambda$105 = GxLogsPage.tsLoad$lambda$105(GxLogsPage.this, showToast, (String) obj, (JSONObject) obj2);
                return tsLoad$lambda$105;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tsLoad$lambda$105(GxLogsPage this$0, boolean $showToast, String e, JSONObject p) {
        List<JSONObject> emptyList;
        List<JSONObject> emptyList2;
        this$0.tsRefreshing = false;
        this$0.tsLoading = false;
        if (e != null || p == null) {
            this$0.tsError = "Failed to load troubleshoot data";
            if ($showToast) {
                this$0.toast(this$0.tsError);
            }
        } else {
            JSONArray optJSONArray = p.optJSONArray("snapshot");
            if (optJSONArray == null || (emptyList = this$0.objects(optJSONArray)) == null) {
                emptyList = CollectionsKt.emptyList();
            }
            this$0.tsSnapshot = emptyList;
            JSONArray optJSONArray2 = p.optJSONArray("sections");
            if (optJSONArray2 == null || (emptyList2 = this$0.objects(optJSONArray2)) == null) {
                emptyList2 = CollectionsKt.emptyList();
            }
            this$0.tsSections = emptyList2;
            JSONObject optJSONObject = p.optJSONObject("vehicleStatus");
            if (optJSONObject == null) {
                optJSONObject = new JSONObject();
            }
            this$0.tsVehicle = optJSONObject;
            this$0.tsOnroad = p.optBoolean("isOnroad", false);
            if ($showToast) {
                this$0.toast("Troubleshoot data refreshed.");
            }
        }
        this$0.tsRender();
        return Unit.INSTANCE;
    }

    private final Object tsVal(JSONObject o, String k) {
        Object v = o.opt(k);
        if (v == null || Intrinsics.areEqual(v, JSONObject.NULL)) {
            return null;
        }
        return v;
    }

    private final String tsFormat(Object v) {
        if (v == null || Intrinsics.areEqual(v, JSONObject.NULL)) {
            return "n/a";
        }
        if (v instanceof Boolean) {
            return ((Boolean) v).booleanValue() ? "On" : "Off";
        }
        if ((v instanceof Integer) || (v instanceof Long)) {
            return v.toString();
        }
        if (!(v instanceof Number)) {
            if ((v instanceof JSONObject) || (v instanceof JSONArray)) {
                return v.toString();
            }
            String obj = StringsKt.trim((CharSequence) v.toString()).toString();
            if (obj.length() == 0) {
                obj = "(empty)";
            }
            return obj;
        }
        double d = ((Number) v).doubleValue();
        if (Double.isNaN(d) || Double.isInfinite(d)) {
            return String.valueOf(d);
        }
        if (d == Math.floor(d)) {
            return jsNum(d);
        }
        String plainString = new BigDecimal(d).setScale(4, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
        Intrinsics.checkNotNullExpressionValue(plainString, "toPlainString(...)");
        return plainString;
    }

    private final String tsFormatLearned(Object v) {
        if (v == null || Intrinsics.areEqual(v, JSONObject.NULL)) {
            return "";
        }
        return StringsKt.trim((CharSequence) v.toString()).toString().length() == 0 ? "" : tsFormat(v);
    }

    private final String tsJsStr(Object v) {
        return (v == null || Intrinsics.areEqual(v, JSONObject.NULL)) ? "" : v instanceof Number ? jsNum(((Number) v).doubleValue()) : v.toString();
    }

    private final boolean tsMatch(Object l, Object r) {
        Object a = Intrinsics.areEqual(l, JSONObject.NULL) ? null : l;
        Object b = Intrinsics.areEqual(r, JSONObject.NULL) ? null : r;
        if (Intrinsics.areEqual(a, b)) {
            return true;
        }
        if (((a instanceof JSONObject) || (a instanceof JSONArray)) && ((b instanceof JSONObject) || (b instanceof JSONArray))) {
            return Intrinsics.areEqual(a.toString(), b.toString());
        }
        if (a == null && b == null) {
            return true;
        }
        if ((a instanceof Number) && (b instanceof Number)) {
            return Math.abs(((Number) a).doubleValue() - ((Number) b).doubleValue()) < 1.0E-9d;
        }
        String lt = StringsKt.trim((CharSequence) tsJsStr(a)).toString();
        String rt = StringsKt.trim((CharSequence) tsJsStr(b)).toString();
        if (lt.length() == 0) {
            if (rt.length() == 0) {
                return true;
            }
        }
        Double ln = StringsKt.toDoubleOrNull(lt);
        Double rn = StringsKt.toDoubleOrNull(rt);
        if (lt.length() > 0) {
            if ((rt.length() > 0) && ln != null && rn != null) {
                double doubleValue = ln.doubleValue();
                if ((Double.isInfinite(doubleValue) || Double.isNaN(doubleValue)) ? false : true) {
                    double doubleValue2 = rn.doubleValue();
                    if ((Double.isInfinite(doubleValue2) || Double.isNaN(doubleValue2)) ? false : true) {
                        return Math.abs(ln.doubleValue() - rn.doubleValue()) < 1.0E-9d;
                    }
                }
            }
        }
        return Intrinsics.areEqual(lt, rt);
    }

    private final boolean tsChanged(JSONObject i) {
        return !tsMatch(tsVal(i, "value"), tsVal(i, "defaultValue"));
    }

    private final List<JSONObject> tsItems(JSONObject s) {
        List<JSONObject> objects;
        JSONArray optJSONArray = s.optJSONArray("items");
        return (optJSONArray == null || (objects = objects(optJSONArray)) == null) ? CollectionsKt.emptyList() : objects;
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
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
                    if (!StringsKt.contains(var15, var16, false)) {
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

    private final int tsCountChanged() {
        int count$iv;
        int i = 0;
        for (JSONObject s : this.tsSections) {
            Iterable $this$count$iv = tsItems(s);
            if (($this$count$iv instanceof Collection) && ((Collection) $this$count$iv).isEmpty()) {
                count$iv = 0;
            } else {
                count$iv = 0;
                for (Object element$iv : $this$count$iv) {
                    JSONObject it = (JSONObject) element$iv;
                    if (tsChanged(it) && (count$iv = count$iv + 1) < 0) {
                        CollectionsKt.throwCountOverflow();
                    }
                }
            }
            i += count$iv;
        }
        return i;
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
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
                    return GxLogsKt.GXLOGS_RED;
                }
                break;
            case 1124446108:
                if (var1.equals("warning")) {
                    return CarUi.Companion.getWARN();
                }
                break;
            case 1952151455:
                if (var1.equals("critical")) {
                    return GxLogsKt.GXLOGS_RED;
                }
        }

        return CarUi.Companion.getDIM();
    }

    private final void tsPaintHead() {
        if (this.tsRefreshBtn == null) {
            return;
        }
        TextView textView = this.tsRefreshBtn;
        TextView textView2 = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tsRefreshBtn");
            textView = null;
        }
        textView.setText(this.tsRefreshing ? "Refreshing..." : "Refresh");
        TextView textView3 = this.tsErrNote;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tsErrNote");
            textView3 = null;
        }
        textView3.setText(this.tsError);
        TextView textView4 = this.tsErrNote;
        if (textView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tsErrNote");
            textView4 = null;
        }
        textView4.setVisibility(this.tsError.length() > 0 ? 0 : 8);
        TextView textView5 = this.tsOnroadText;
        if (textView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tsOnroadText");
            textView5 = null;
        }
        textView5.setText("Onroad: " + (this.tsOnroad ? "Yes" : "No"));
        TextView textView6 = this.tsChangedText;
        if (textView6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tsChangedText");
        } else {
            textView2 = textView6;
        }
        textView2.setText("Changed Settings: " + tsCountChanged());
    }

    static /* synthetic */ void tsPair$default(GxLogsPage gxLogsPage, String str, String str2, ViewGroup viewGroup, int i, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            i = CarUi.Companion.getTEXT();
        }
        gxLogsPage.tsPair(str, str2, viewGroup, i);
    }

    private final void tsPair(String label, String value, ViewGroup into, int color) {
        LinearLayout l = new LinearLayout(getAct());
        l.setOrientation(0);
        l.setGravity(16);
        l.setBackground(CarUi.rounded$default(getUi(), CarUi.Companion.getCARD_ON(), 10, null, 4, null));
        l.setPadding(getUi().dp(14), getUi().dp(10), getUi().dp(14), getUi().dp(10));
        TextView $this$tsPair_u24lambda_u24111 = new TextView(getAct());
        $this$tsPair_u24lambda_u24111.setText(label);
        $this$tsPair_u24lambda_u24111.setTextSize(16.0f);
        $this$tsPair_u24lambda_u24111.setTextColor(CarUi.Companion.getDIM());
        l.addView($this$tsPair_u24lambda_u24111, new LinearLayout.LayoutParams(0, -2, 1.0f));
        TextView $this$tsPair_u24lambda_u24112 = new TextView(getAct());
        $this$tsPair_u24lambda_u24112.setText(value);
        $this$tsPair_u24lambda_u24112.setTextSize(17.0f);
        $this$tsPair_u24lambda_u24112.setTextColor(color);
        $this$tsPair_u24lambda_u24112.setTypeface(Typeface.DEFAULT_BOLD);
        $this$tsPair_u24lambda_u24112.setGravity(8388613);
        $this$tsPair_u24lambda_u24112.setPadding(getUi().dp(12), 0, 0, 0);
        l.addView($this$tsPair_u24lambda_u24112, new LinearLayout.LayoutParams(0, -2, 1.3f));
        LinearLayout.LayoutParams $this$tsPair_u24lambda_u24113 = new LinearLayout.LayoutParams(-1, -2);
        $this$tsPair_u24lambda_u24113.bottomMargin = getUi().dp(6);
        Unit unit = Unit.INSTANCE;
        into.addView(l, $this$tsPair_u24lambda_u24113);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void tsRender() {
        int dim;
        List<JSONObject> emptyList;
        LinearLayout linearLayout;
        String id;
        Integer num;
        int i;
        LinearLayout linearLayout2;
        if (this.tsBody == null) {
            return;
        }
        tsPaintHead();
        LinearLayout linearLayout3 = this.tsBody;
        if (linearLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tsBody");
            linearLayout3 = null;
        }
        linearLayout3.removeAllViews();
        if (this.tsLoading) {
            LinearLayout linearLayout4 = this.tsBody;
            if (linearLayout4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tsBody");
                linearLayout2 = null;
            } else {
                linearLayout2 = linearLayout4;
            }
            linearLayout2.addView(empty("Loading troubleshoot data..."));
            return;
        }
        LinearLayout linearLayout5 = this.tsBody;
        if (linearLayout5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tsBody");
            linearLayout5 = null;
        }
        LinearLayout vc = card(linearLayout5);
        LinearLayout vbar = titleBar("Vehicle Fault Status", vc);
        boolean z = false;
        boolean available = this.tsVehicle.optBoolean("available", false);
        String str = available ? "Live" : "Unavailable";
        if (available) {
            dim = CarUi.Companion.getACCENT();
        } else {
            String lowerCase = GxPage.str$default(this, this.tsVehicle, "summarySeverity", null, 2, null).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
            dim = Intrinsics.areEqual(lowerCase, "error") ? GxLogsKt.GXLOGS_RED : CarUi.Companion.getDIM();
        }
        chip(str, dim, vbar);
        GxLogsPage gxLogsPage = this;
        String str$default = GxPage.str$default(this, this.tsVehicle, "summary", null, 2, null);
        boolean z2 = true;
        if (str$default.length() == 0) {
            str$default = "Vehicle fault status unavailable.";
        }
        GxPage.text$default(gxLogsPage, str$default, vc, 17.0f, CarUi.Companion.getTEXT(), false, 16, null);
        JSONArray optJSONArray = this.tsVehicle.optJSONArray("items");
        if (optJSONArray == null || (emptyList = objects(optJSONArray)) == null) {
            emptyList = CollectionsKt.emptyList();
        }
        for (JSONObject it : emptyList) {
            tsPair(GxPage.str$default(this, it, "label", null, 2, null), tsFormat(tsVal(it, "value")), vc, tsSevColor(str(it, "severity", "neutral")));
        }
        LinearLayout linearLayout6 = this.tsBody;
        if (linearLayout6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tsBody");
            linearLayout6 = null;
        }
        LinearLayout sc = card(linearLayout6);
        titleBar("Snapshot", sc);
        if (this.tsSnapshot.isEmpty()) {
            GxPage.text$default(this, "No snapshot data.", sc, 0.0f, 0, false, 28, null);
        }
        for (JSONObject it2 : this.tsSnapshot) {
            tsPair$default(this, GxPage.str$default(this, it2, "label", null, 2, null), tsFormat(tsVal(it2, "value")), sc, 0, 8, null);
            z2 = z2;
        }
        Iterator<? extends JSONObject> it3 = this.tsSections.iterator();
        while (it3.hasNext()) {
            JSONObject s = it3.next();
            List items = tsVisible(s);
            if (items.isEmpty()) {
                z = false;
            } else {
                LinearLayout linearLayout7 = this.tsBody;
                if (linearLayout7 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tsBody");
                    linearLayout7 = null;
                }
                LinearLayout c = card(linearLayout7);
                LinearLayout bar = titleBar(GxPage.str$default(this, s, "title", null, 2, null), c);
                String id2 = GxPage.str$default(this, s, "id", null, 2, null);
                if (s.optBoolean("resettable", z)) {
                    final var s$f = s;
                    TextView b = CarUi.button$default(getUi(), Intrinsics.areEqual(this.tsBusySection, id2) ? "Resetting..." : "Reset to Default", false, new Function0() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda40
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            Unit tsRender$lambda$115;
                            tsRender$lambda$115 = GxLogsPage.tsRender$lambda$115(GxLogsPage.this, s$f);
                            return tsRender$lambda$115;
                        }
                    }, 2, null);
                    i = GxLogsKt.GXLOGS_RED;
                    b.setTextColor(i);
                    bar.addView(b);
                }
                boolean learnedCol = s.optBoolean("hasLearnedColumn", z);
                Iterator<JSONObject> it4 = items.iterator();
                while (it4.hasNext()) {
                    JSONObject i2 = it4.next();
                    boolean changed = tsChanged(i2);
                    Iterator<? extends JSONObject> it5 = it3;
                    JSONObject s2 = s;
                    LinearLayout l = new LinearLayout(getAct());
                    List items2 = items;
                    l.setOrientation(0);
                    l.setGravity(16);
                    CarUi ui = getUi();
                    LinearLayout bar2 = bar;
                    int card_on = CarUi.Companion.getCARD_ON();
                    if (changed) {
                        id = id2;
                        num = Integer.valueOf(CarUi.Companion.getWARN());
                    } else {
                        id = id2;
                        num = null;
                    }
                    Iterator<JSONObject> it6 = it4;
                    l.setBackground(ui.rounded(card_on, 10, num));
                    LinearLayout vc2 = vc;
                    l.setPadding(getUi().dp(14), getUi().dp(10), getUi().dp(14), getUi().dp(10));
                    LinearLayout left = new LinearLayout(getAct());
                    left.setOrientation(0);
                    left.setGravity(16);
                    TextView $this$tsRender_u24lambda_u24118 = new TextView(getAct());
                    $this$tsRender_u24lambda_u24118.setText(GxPage.str$default(this, i2, "label", null, 2, null));
                    $this$tsRender_u24lambda_u24118.setTextSize(16.0f);
                    $this$tsRender_u24lambda_u24118.setTextColor(CarUi.Companion.getTEXT());
                    left.addView($this$tsRender_u24lambda_u24118, new LinearLayout.LayoutParams(0, -2, 1.0f));
                    if (changed) {
                        chip("Changed", CarUi.Companion.getWARN(), left);
                    }
                    l.addView(left, new LinearLayout.LayoutParams(0, -2, 1.0f));
                    LinearLayout right = new LinearLayout(getAct());
                    right.setOrientation(1);
                    right.setPadding(getUi().dp(16), 0, 0, 0);
                    TextView $this$tsRender_u24lambda_u24120 = new TextView(getAct());
                    SpannableStringBuilder sb = new SpannableStringBuilder("current ");
                    LinearLayout vbar2 = vbar;
                    boolean available2 = available;
                    sb.setSpan(new ForegroundColorSpan(CarUi.Companion.getDIM()), 0, sb.length(), 33);
                    int st = sb.length();
                    sb.append((CharSequence) tsFormat(tsVal(i2, "value")));
                    sb.setSpan(new StyleSpan(1), st, sb.length(), 33);
                    $this$tsRender_u24lambda_u24120.setText(sb);
                    $this$tsRender_u24lambda_u24120.setTextSize(16.0f);
                    $this$tsRender_u24lambda_u24120.setTextColor(CarUi.Companion.getTEXT());
                    right.addView($this$tsRender_u24lambda_u24120);
                    TextView $this$tsRender_u24lambda_u24121 = new TextView(getAct());
                    $this$tsRender_u24lambda_u24121.setText("default " + tsFormat(tsVal(i2, "defaultValue")));
                    $this$tsRender_u24lambda_u24121.setTextSize(15.0f);
                    $this$tsRender_u24lambda_u24121.setTextColor(CarUi.Companion.getDIM());
                    right.addView($this$tsRender_u24lambda_u24121);
                    String learned = tsFormatLearned(tsVal(i2, "learnedValue"));
                    if (learnedCol) {
                        if (learned.length() > 0) {
                            TextView $this$tsRender_u24lambda_u24122 = new TextView(getAct());
                            $this$tsRender_u24lambda_u24122.setText("learned " + learned);
                            $this$tsRender_u24lambda_u24122.setTextSize(15.0f);
                            $this$tsRender_u24lambda_u24122.setTextColor(CarUi.Companion.getDIM());
                            right.addView($this$tsRender_u24lambda_u24122);
                        }
                    }
                    l.addView(right, new LinearLayout.LayoutParams(0, -2, 1.0f));
                    LinearLayout.LayoutParams $this$tsRender_u24lambda_u24123 = new LinearLayout.LayoutParams(-1, -2);
                    $this$tsRender_u24lambda_u24123.bottomMargin = getUi().dp(6);
                    Unit unit = Unit.INSTANCE;
                    c.addView(l, $this$tsRender_u24lambda_u24123);
                    vbar = vbar2;
                    it3 = it5;
                    s = s2;
                    items = items2;
                    available = available2;
                    bar = bar2;
                    id2 = id;
                    it4 = it6;
                    vc = vc2;
                }
                z = false;
            }
        }
        if (this.tsOnlyChanged && tsCountChanged() == 0) {
            LinearLayout linearLayout8 = this.tsBody;
            if (linearLayout8 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tsBody");
                linearLayout = null;
            } else {
                linearLayout = linearLayout8;
            }
            linearLayout.addView(empty("No settings are currently different from their defaults."));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tsRender$lambda$115(GxLogsPage this$0, JSONObject $s) {
        this$0.tsResetSection($s);
        return Unit.INSTANCE;
    }

    private final void tsResetSection(JSONObject s) {
        final String id = GxPage.str$default(this, s, "id", null, 2, null);
        if (id.length() == 0) {
            return;
        }
        if (this.tsBusySection.length() > 0) {
            return;
        }
        String str$default = GxPage.str$default(this, s, "title", null, 2, null);
        if (str$default.length() == 0) {
            str$default = "this section";
        }
        final String title = str$default;
        confirm("Reset to defaults?", "Reset " + title + " to defaults?", "Reset", new Function0() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda24
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit tsResetSection$lambda$128;
                tsResetSection$lambda$128 = GxLogsPage.tsResetSection$lambda$128(GxLogsPage.this, id, title);
                return tsResetSection$lambda$128;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tsResetSection$lambda$128(final GxLogsPage this$0, String $id, final String $title) {
        this$0.tsBusySection = $id;
        this$0.tsRender();
        GxPage.send$default(this$0, "POST", "/api/troubleshoot/reset", new JSONObject().put("sectionId", $id), 0L, new Function2() { // from class: com.starpilot.starview.GxLogsPage$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit tsResetSection$lambda$128$lambda$127;
                tsResetSection$lambda$128$lambda$127 = GxLogsPage.tsResetSection$lambda$128$lambda$127(this$0, $title, (String) obj, (JSONObject) obj2);
                return tsResetSection$lambda$128$lambda$127;
            }
        }, 8, null);
        return Unit.INSTANCE;
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public static final Unit tsResetSection$lambda$128$lambda$127(GxLogsPage var0, String var1, String var2, JSONObject var3) {
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

    private final void tsCopyReport() {
        String iso;
        String summary;
        String iso2;
        List<JSONObject> emptyList;
        SimpleDateFormat $this$tsCopyReport_u24lambda_u24129 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        $this$tsCopyReport_u24lambda_u24129.setTimeZone(TimeZone.getTimeZone("UTC"));
        String iso3 = $this$tsCopyReport_u24lambda_u24129.format(new Date());
        ArrayList lines = new ArrayList();
        lines.add("StarPilot Troubleshoot Report");
        lines.add("Generated: " + iso3);
        lines.add("Onroad: " + (this.tsOnroad ? "Yes" : "No"));
        lines.add("Only non-default values: " + (this.tsOnlyChanged ? "Yes" : "No"));
        String summary2 = GxPage.str$default(this, this.tsVehicle, "summary", null, 2, null);
        if (summary2.length() > 0) {
            lines.add("");
            lines.add("Vehicle Fault Status");
            lines.add("Summary: " + summary2);
            JSONArray optJSONArray = this.tsVehicle.optJSONArray("items");
            if (optJSONArray == null || (emptyList = objects(optJSONArray)) == null) {
                emptyList = CollectionsKt.emptyList();
            }
            for (JSONObject it : emptyList) {
                lines.add("- " + GxPage.str$default(this, it, "label", null, 2, null) + ": " + tsFormat(tsVal(it, "value")));
            }
        }
        lines.add("");
        lines.add("Snapshot");
        for (JSONObject it2 : this.tsSnapshot) {
            lines.add("- " + GxPage.str$default(this, it2, "label", null, 2, null) + ": " + tsFormat(tsVal(it2, "value")));
        }
        for (JSONObject s : this.tsSections) {
            List items = tsVisible(s);
            if (!items.isEmpty()) {
                lines.add("");
                lines.add(GxPage.str$default(this, s, "title", null, 2, null));
                for (JSONObject i : (Iterable<JSONObject>) items) {
                    String learned = tsFormatLearned(tsVal(i, "learnedValue"));
                    String str$default = GxPage.str$default(this, i, "label", null, 2, null);
                    String tsFormat = tsFormat(tsVal(i, "value"));
                    String tsFormat2 = tsFormat(tsVal(i, "defaultValue"));
                    if (learned.length() > 0) {
                        iso = iso3;
                        summary = summary2;
                        iso2 = ", learned: " + learned;
                    } else {
                        iso = iso3;
                        summary = summary2;
                        iso2 = "";
                    }
                    lines.add("- " + str$default + ": " + tsFormat + " (default: " + tsFormat2 + iso2 + ")");
                    iso3 = iso;
                    summary2 = summary;
                }
            }
        }
        copyText(CollectionsKt.joinToString(lines, "\n", "", "", -1, "...", null), "Troubleshoot report copied to clipboard.");
    }
}
