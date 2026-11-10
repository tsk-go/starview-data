package com.starpilot.starview;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
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
import com.starpilot.starview.CarUi;
import com.starpilot.starview.GxPage;
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
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.ranges.RangesKt;
import kotlin.sequences.SequencesKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.LinearLayout.LayoutParams;

/* compiled from: GxTuning.kt */
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
    private final ArrayList<ChartViews> chartViews;
    private final List<ChartCfg> charts;
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
    private final QualityCfg latQ;
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
    private final QualityCfg lonQ;
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

    /* renamed from: prefs$delegate, reason: from kotlin metadata */
    private final Lazy prefs;
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

    /* VF: from Vineflower (jadx: ) */
    public GxTuningPage(Activity var1, GxHost var2) {
        super(var1, var2);
        Intrinsics.checkNotNullParameter(var1, "act");
        Intrinsics.checkNotNullParameter(var2, "gx");
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

    @Override // com.starpilot.starview.GxPage
    public boolean getNeedsOffroad() {
        return this.needsOffroad;
    }

    @Override // com.starpilot.starview.GxPage
    public void build() {
        GxPage.header$default(this, "Tuning, Plots & Testing", null, 2, null);
        GxPage.tabs$default(this, CollectionsKt.listOf((Object[]) new String[]{"Lateral Tuning", "Plots", "Testing Ground", "Maneuvers"}), 0, new Function1() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda59
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit build$lambda$0;
                build$lambda$0 = GxTuningPage.build$lambda$0(GxTuningPage.this, ((Integer) obj).intValue());
                return build$lambda$0;
            }
        }, new Function2() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda60
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit build$lambda$1;
                build$lambda$1 = GxTuningPage.build$lambda$1(GxTuningPage.this, ((Integer) obj).intValue(), (LinearLayout) obj2);
                return build$lambda$1;
            }
        }, 2, null);
        every(4000L, new Function0() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda61
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit build$lambda$2;
                build$lambda$2 = GxTuningPage.build$lambda$2(GxTuningPage.this);
                return build$lambda$2;
            }
        });
        every(1000L, new Function0() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda62
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit build$lambda$3;
                build$lambda$3 = GxTuningPage.build$lambda$3(GxTuningPage.this);
                return build$lambda$3;
            }
        });
        every(3000L, new Function0() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda63
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit build$lambda$4;
                build$lambda$4 = GxTuningPage.build$lambda$4(GxTuningPage.this);
                return build$lambda$4;
            }
        });
        every(3000L, new Function0() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda64
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit build$lambda$5;
                build$lambda$5 = GxTuningPage.build$lambda$5(GxTuningPage.this);
                return build$lambda$5;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$0(GxTuningPage this$0, int i) {
        int was = this$0.tab;
        this$0.tab = i;
        if (this$0.getShown() && was != i) {
            this$0.kick(i);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$1(GxTuningPage this$0, int i, LinearLayout b) {
        Intrinsics.checkNotNullParameter(b, "b");
        switch (i) {
            case 0:
                this$0.buildLateral(b);
                break;
            case 1:
                this$0.buildPlots(b);
                break;
            case 2:
                this$0.buildTesting(b);
                break;
            default:
                this$0.buildManeuvers(b);
                break;
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$2(GxTuningPage this$0) {
        if (this$0.tab == 0) {
            flmRefresh$default(this$0, true, null, 2, null);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$3(GxTuningPage this$0) {
        if (this$0.tab == 1 && !this$0.plotsPaused) {
            plotsLoad$default(this$0, false, 1, null);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$4(GxTuningPage this$0) {
        if (this$0.tab == 2 && !this$0.tgBusy) {
            this$0.tgLoad();
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$5(GxTuningPage this$0) {
        if (this$0.tab == 3) {
            this$0.manLoad(true);
            this$0.manLoad(false);
        }
        return Unit.INSTANCE;
    }

    @Override // com.starpilot.starview.GxPage
    public void onShow() {
        if (!this.flmBuilt || !this.routes.isEmpty() || this.loadingRoutes) {
            return;
        }
        loadRoutes();
    }

    private final void kick(int i) {
        switch (i) {
            case 0:
                flmRefresh$default(this, false, null, 3, null);
                break;
            case 1:
                if (!this.plotsPaused) {
                    plotsLoad$default(this, false, 1, null);
                    break;
                }
                break;
            case 2:
                tgLoad();
                break;
            default:
                manLoad(true);
                manLoad(false);
                break;
        }
    }

    static /* synthetic */ double num$default(GxTuningPage gxTuningPage, Object obj, double d, int i, Object obj2) {
        if ((i & 2) != 0) {
            d = 0.0d;
        }
        return gxTuningPage.num(obj, d);
    }

    private final double num(Object v, double fb) {
        double d;
        if (v != null && !Intrinsics.areEqual(v, JSONObject.NULL)) {
            if (v instanceof Number) {
                d = ((Number) v).doubleValue();
            } else if (v instanceof Boolean) {
                if (((Boolean) v).booleanValue()) {
                    d = 1.0d;
                }
                d = 0.0d;
            } else if (v instanceof String) {
                if (!StringsKt.isBlank((CharSequence) v)) {
                    Double doubleOrNull = StringsKt.toDoubleOrNull(StringsKt.trim((CharSequence) v).toString());
                    if (doubleOrNull != null) {
                        d = doubleOrNull.doubleValue();
                    }
                }
                d = 0.0d;
            }
            return !Double.isInfinite(d) && !Double.isNaN(d) ? d : fb;
        }
        return fb;
    }

    private final boolean truthy(Object v) {
        if (v == null || Intrinsics.areEqual(v, JSONObject.NULL)) {
            return false;
        }
        if (v instanceof Boolean) {
            return ((Boolean) v).booleanValue();
        }
        if (v instanceof Number) {
            if (((Number) v).doubleValue() == 0.0d) {
                return false;
            }
        } else if ((v instanceof String) && ((CharSequence) v).length() <= 0) {
            return false;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String s(JSONObject o, String k) {
        String str;
        Object opt;
        if (o != null) {
            if (!o.has(k) || o.isNull(k) || (opt = o.opt(k)) == null || (str = opt.toString()) == null) {
                str = "";
            }
            return str == null ? "" : str;
        }
        return "";
    }

    private final String f(double v, int d) {
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String format = String.format(Locale.US, "%." + d + "f", Arrays.copyOf(new Object[]{Double.valueOf(v)}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format;
    }

    private final JSONArray arr(JSONObject $this$arr, String k) {
        JSONArray optJSONArray;
        return ($this$arr == null || (optJSONArray = $this$arr.optJSONArray(k)) == null) ? new JSONArray() : optJSONArray;
    }

    private final JSONObject obj(JSONObject $this$obj, String k) {
        JSONObject optJSONObject;
        return ($this$obj == null || (optJSONObject = $this$obj.optJSONObject(k)) == null) ? new JSONObject() : optJSONObject;
    }

    private final List<Pair<String, Object>> entries(final JSONObject $this$entries) {
        Iterator<String> keys = $this$entries.keys();
        Intrinsics.checkNotNullExpressionValue(keys, "keys(...)");
        return SequencesKt.toList(SequencesKt.map(SequencesKt.asSequence(keys), new Function1() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda65
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Pair entries$lambda$7;
                entries$lambda$7 = GxTuningPage.entries$lambda$7($this$entries, (String) obj);
                return entries$lambda$7;
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Pair entries$lambda$7(JSONObject $this_entries, String it) {
        return TuplesKt.to(it, $this_entries.opt(it));
    }

    private final void en(TextView $this$en, boolean on) {
        $this$en.setEnabled(on);
        $this$en.setAlpha(on ? 1.0f : 0.4f);
    }

    static /* synthetic */ TextView line$default(GxTuningPage gxTuningPage, ViewGroup viewGroup, String str, String str2, int i, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            i = CarUi.Companion.getTEXT();
        }
        return gxTuningPage.line(viewGroup, str, str2, i);
    }

    private final TextView line(ViewGroup into, String label, String value, int valueColor) {
        LinearLayout l = new LinearLayout(getAct());
        l.setOrientation(0);
        l.setGravity(16);
        l.setPadding(0, getUi().dp(5), 0, getUi().dp(5));
        TextView $this$line_u24lambda_u249 = new TextView(getAct());
        $this$line_u24lambda_u249.setText(label);
        $this$line_u24lambda_u249.setTextSize(17.0f);
        $this$line_u24lambda_u249.setTextColor(CarUi.Companion.getDIM());
        l.addView($this$line_u24lambda_u249, new LinearLayout.LayoutParams(0, -2, 1.0f));
        TextView v = new TextView(getAct());
        v.setText(value);
        v.setTextSize(17.0f);
        v.setTextColor(valueColor);
        v.setTypeface(Typeface.DEFAULT_BOLD);
        v.setGravity(8388613);
        l.addView(v, new LinearLayout.LayoutParams(0, -2, 1.0f));
        into.addView(l);
        return v;
    }

    static /* synthetic */ LinearLayout headLine$default(GxTuningPage gxTuningPage, ViewGroup viewGroup, String str, int i, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            i = CarUi.Companion.getTEXT();
        }
        return gxTuningPage.headLine(viewGroup, str, i);
    }

    private final LinearLayout headLine(ViewGroup into, String title, int color) {
        LinearLayout l = new LinearLayout(getAct());
        l.setOrientation(0);
        l.setGravity(16);
        l.setPadding(0, 0, 0, getUi().dp(6));
        TextView $this$headLine_u24lambda_u2412 = new TextView(getAct());
        $this$headLine_u24lambda_u2412.setText(title);
        $this$headLine_u24lambda_u2412.setTextSize(21.0f);
        $this$headLine_u24lambda_u2412.setTextColor(color);
        $this$headLine_u24lambda_u2412.setTypeface(Typeface.DEFAULT_BOLD);
        l.addView($this$headLine_u24lambda_u2412, new LinearLayout.LayoutParams(0, -2, 1.0f));
        into.addView(l);
        return l;
    }

    static /* synthetic */ TextView headBtn$default(GxTuningPage gxTuningPage, LinearLayout linearLayout, String str, boolean z, Function0 function0, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        return gxTuningPage.headBtn(linearLayout, str, z, function0);
    }

    private final TextView headBtn(LinearLayout head, String label, boolean accent, Function0<Unit> onClick) {
        TextView b = getUi().button(label, accent, onClick);
        LinearLayout.LayoutParams $this$headBtn_u24lambda_u2413 = new LinearLayout.LayoutParams(-2, -2);
        $this$headBtn_u24lambda_u2413.leftMargin = getUi().dp(8);
        Unit unit = Unit.INSTANCE;
        head.addView(b, $this$headBtn_u24lambda_u2413);
        return b;
    }

    private final TextView sub(String title, ViewGroup into) {
        TextView $this$sub_u24lambda_u2414 = text(title, into, 18.0f, CarUi.Companion.getTEXT(), true);
        $this$sub_u24lambda_u2414.setPadding(0, getUi().dp(14), 0, getUi().dp(4));
        return $this$sub_u24lambda_u2414;
    }

    private final LinearLayout inner(ViewGroup into) {
        LinearLayout c = new LinearLayout(getAct());
        c.setOrientation(1);
        c.setBackground(getUi().rounded(CarUi.Companion.getCARD_ON(), 12, Integer.valueOf(Color.rgb(50, 55, 66))));
        c.setPadding(getUi().dp(16), getUi().dp(12), getUi().dp(16), getUi().dp(12));
        LinearLayout.LayoutParams $this$inner_u24lambda_u2416 = new LinearLayout.LayoutParams(-1, -2);
        $this$inner_u24lambda_u2416.topMargin = getUi().dp(6);
        $this$inner_u24lambda_u2416.bottomMargin = getUi().dp(6);
        Unit unit = Unit.INSTANCE;
        into.addView(c, $this$inner_u24lambda_u2416);
        return c;
    }

    static /* synthetic */ void notice$default(GxTuningPage gxTuningPage, ViewGroup viewGroup, String str, int i, String str2, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            i = CarUi.Companion.getWARN();
        }
        if ((i2 & 8) != 0) {
            str2 = null;
        }
        gxTuningPage.notice(viewGroup, str, i, str2);
    }

    private final void notice(ViewGroup into, String text, int color, String title) {
        LinearLayout c = new LinearLayout(getAct());
        c.setOrientation(1);
        c.setBackground(getUi().rounded(Color.argb(40, Color.red(color), Color.green(color), Color.blue(color)), 12, Integer.valueOf(color)));
        c.setPadding(getUi().dp(16), getUi().dp(10), getUi().dp(16), getUi().dp(10));
        SpannableStringBuilder sb = new SpannableStringBuilder();
        if (title != null) {
            sb.append((CharSequence) title).append((CharSequence) " ");
            sb.setSpan(new StyleSpan(1), 0, title.length(), 33);
        }
        sb.append((CharSequence) text);
        TextView $this$notice_u24lambda_u2418 = new TextView(getAct());
        $this$notice_u24lambda_u2418.setText(sb);
        $this$notice_u24lambda_u2418.setTextSize(15.0f);
        $this$notice_u24lambda_u2418.setTextColor(CarUi.Companion.getTEXT());
        c.addView($this$notice_u24lambda_u2418);
        LinearLayout.LayoutParams $this$notice_u24lambda_u2419 = new LinearLayout.LayoutParams(-1, -2);
        $this$notice_u24lambda_u2419.topMargin = getUi().dp(4);
        $this$notice_u24lambda_u2419.bottomMargin = getUi().dp(8);
        Unit unit = Unit.INSTANCE;
        into.addView(c, $this$notice_u24lambda_u2419);
    }

    static /* synthetic */ void bullets$default(GxTuningPage gxTuningPage, ViewGroup viewGroup, List list, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        gxTuningPage.bullets(viewGroup, list, z);
    }

    private final void bullets(ViewGroup into, List<String> items, boolean ordered) {
        StringBuilder sb;
        String str;
        int i = 0;
        for (String it : items) {
            int i2 = i;
            i++;
            GxTuningPage gxTuningPage = this;
            if (ordered) {
                sb = new StringBuilder().append(i2 + 1);
                str = ". ";
            } else {
                sb = new StringBuilder();
                str = "•  ";
            }
            TextView $this$bullets_u24lambda_u2420 = GxPage.text$default(gxTuningPage, sb.append(str).append(it).toString(), into, 16.0f, CarUi.Companion.getDIM(), false, 16, null);
            $this$bullets_u24lambda_u2420.setPadding(getUi().dp(6), getUi().dp(3), 0, getUi().dp(3));
        }
    }

    private final void para(ViewGroup into, String label, String value) {
        SpannableStringBuilder sb = new SpannableStringBuilder(label).append((CharSequence) " ").append((CharSequence) value);
        sb.setSpan(new StyleSpan(1), 0, label.length(), 33);
        TextView $this$para_u24lambda_u2421 = GxPage.text$default(this, "", into, 16.0f, CarUi.Companion.getTEXT(), false, 16, null);
        $this$para_u24lambda_u2421.setText(sb);
        $this$para_u24lambda_u2421.setPadding(0, getUi().dp(3), 0, getUi().dp(3));
    }

    private final TextView mono(ViewGroup into, String s) {
        TextView $this$mono_u24lambda_u2422 = GxPage.text$default(this, s, into, 14.0f, Color.rgb(210, 220, 230), false, 16, null);
        $this$mono_u24lambda_u2422.setTypeface(Typeface.MONOSPACE);
        return $this$mono_u24lambda_u2422;
    }

    private final LinearLayout btnBar(ViewGroup into) {
        LinearLayout bar = new LinearLayout(getAct());
        bar.setOrientation(0);
        bar.setPadding(0, getUi().dp(6), 0, getUi().dp(6));
        HorizontalScrollView $this$btnBar_u24lambda_u2424 = new HorizontalScrollView(getAct());
        $this$btnBar_u24lambda_u2424.setHorizontalScrollBarEnabled(false);
        $this$btnBar_u24lambda_u2424.addView(bar);
        into.addView($this$btnBar_u24lambda_u2424);
        return bar;
    }

    static /* synthetic */ TextView barBtn$default(GxTuningPage gxTuningPage, LinearLayout linearLayout, String str, boolean z, Function0 function0, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        return gxTuningPage.barBtn(linearLayout, str, z, function0);
    }

    private final TextView barBtn(LinearLayout bar, String label, boolean accent, Function0<Unit> onClick) {
        TextView b = getUi().button(label, accent, onClick);
        LinearLayout.LayoutParams $this$barBtn_u24lambda_u2425 = new LinearLayout.LayoutParams(-2, -2);
        $this$barBtn_u24lambda_u2425.rightMargin = getUi().dp(10);
        Unit unit = Unit.INSTANCE;
        bar.addView(b, $this$barBtn_u24lambda_u2425);
        return b;
    }

    private final String fmtAge(Object epoch) {
        StringBuilder append;
        String str;
        double at = num(epoch, Double.NaN);
        if (Double.isNaN(at) || at <= 0.0d) {
            return "unknown";
        }
        long age = Math.max(0L, Math.round((System.currentTimeMillis() / 1000.0d) - at));
        if (age < 5) {
            return "just now";
        }
        if (age < 60) {
            append = new StringBuilder().append(age);
            str = "s ago";
        } else if (age < 3600) {
            append = new StringBuilder().append(Math.round(age / 60.0d));
            str = "m ago";
        } else {
            append = new StringBuilder().append(Math.round(age / 3600.0d));
            str = "h ago";
        }
        return append.append(str).toString();
    }

    private final String fmtAgeSeconds(Object v) {
        StringBuilder append;
        String str;
        double sec = num(v, Double.NaN);
        if (Double.isNaN(sec) || sec < 0.0d) {
            return "unknown";
        }
        if (sec < 1.0d) {
            return "just now";
        }
        if (sec < 60.0d) {
            append = new StringBuilder().append(Math.round(sec));
            str = "s ago";
        } else {
            double d = 60;
            if (sec / d < 60.0d) {
                append = new StringBuilder().append(Math.round(sec / d));
                str = "m ago";
            } else {
                append = new StringBuilder().append(Math.round(sec / 3600));
                str = "h ago";
            }
        }
        return append.append(str).toString();
    }

    /* VF: from Vineflower (jadx: Type inference failed) */
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
                    return ((String) var1);
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

    private final JSONObject status() {
        JSONObject m = new JSONObject(this.wsStatus.toString());
        JSONObject sd = this.statusData;
        if (sd != null) {
            Iterator<String> keys = sd.keys();
            Intrinsics.checkNotNullExpressionValue(keys, "keys(...)");
            while (keys.hasNext()) {
                String k = keys.next();
                m.put(k, sd.opt(k));
            }
        }
        return m;
    }

    private final boolean rollbackUnavailable() {
        JSONObject jSONObject = this.activeTrial;
        if (jSONObject != null) {
            return Intrinsics.areEqual(jSONObject.opt("rollbackAvailable"), (Object) false);
        }
        return false;
    }

    private final boolean canApplyTrial() {
        return (this.busy || rollbackUnavailable()) ? false : true;
    }

    private final boolean canAnalyze() {
        int c = selectedSegmentCount();
        return (this.selectedRoutes.isEmpty() || c <= 0 || c > this.maxSegments || this.isOnroad || this.laneCentering) ? false : true;
    }

    private final void buildLateral(LinearLayout b) {
        this.flmBuilt = true;
        LinearLayout c = card(b);
        headLine$default(this, c, "Lateral Tuning (FLM)", 0, 4, null);
        GxPage.text$default(this, "Analyze one or more local routes, review deterministic lateral findings, apply a bounded trial, drive, then revert or refine.", c, 0.0f, 0, false, 28, null);
        this.flmNotices = box(c);
        LinearLayout bar = btnBar(c);
        this.bAnalyze = barBtn(bar, "Analyze Selected", true, new Function0() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildLateral$lambda$30;
                buildLateral$lambda$30 = GxTuningPage.buildLateral$lambda$30(GxTuningPage.this);
                return buildLateral$lambda$30;
            }
        });
        this.bStop = barBtn$default(this, bar, "Stop", false, new Function0() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda22
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildLateral$lambda$31;
                buildLateral$lambda$31 = GxTuningPage.buildLateral$lambda$31(GxTuningPage.this);
                return buildLateral$lambda$31;
            }
        }, 4, null);
        this.bRevert = barBtn$default(this, bar, "Revert Trial", false, new Function0() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda33
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildLateral$lambda$32;
                buildLateral$lambda$32 = GxTuningPage.buildLateral$lambda$32(GxTuningPage.this);
                return buildLateral$lambda$32;
            }
        }, 4, null);
        this.bKeep = barBtn$default(this, bar, "Keep as Baseline", false, new Function0() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda44
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildLateral$lambda$34;
                buildLateral$lambda$34 = GxTuningPage.buildLateral$lambda$34(GxTuningPage.this);
                return buildLateral$lambda$34;
            }
        }, 4, null);
        this.bSave = barBtn$default(this, bar, "Save Tune", false, new Function0() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda55
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildLateral$lambda$35;
                buildLateral$lambda$35 = GxTuningPage.buildLateral$lambda$35(GxTuningPage.this);
                return buildLateral$lambda$35;
            }
        }, 4, null);
        this.bRefresh = barBtn$default(this, bar, "Refresh", false, new Function0() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda66
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildLateral$lambda$36;
                buildLateral$lambda$36 = GxTuningPage.buildLateral$lambda$36(GxTuningPage.this);
                return buildLateral$lambda$36;
            }
        }, 4, null);
        sub("Workspace status", c);
        this.flmStatusBox = box(c);
        header("Saved Tunes", b);
        note("Save a working FLM trial, switch between setups, then use Revert Trial to return to the exact manual settings from before FLM.", b);
        this.tunesBox = box(b);
        header("Local Routes", b);
        note("Pick routes and segment ranges to analyze. A run is limited to " + this.maxSegments + " segments total.", b);
        this.routesHead = box(b);
        this.routesBox = box(b);
        header("Reports", b);
        this.reportsBox = box(b);
        this.reportBox = box(b);
        LinearLayout pc = card(b);
        LinearLayout ph = headLine$default(this, pc, "Trial Profiles", 0, 4, null);
        this.bSaveCurrent = headBtn$default(this, ph, "Save Current", false, new Function0() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda70
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildLateral$lambda$37;
                buildLateral$lambda$37 = GxTuningPage.buildLateral$lambda$37(GxTuningPage.this);
                return buildLateral$lambda$37;
            }
        }, 4, null);
        GxPage.text$default(this, "Apply one bounded profile at a time. Revert restores the exact advanced-lateral and FLM state from before the trial.", pc, 0.0f, 0, false, 28, null);
        this.profilesBox = box(pc);
        paintFlm();
        renderReport();
        renderRoutesAll();
        loadRoutes();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildLateral$lambda$30(GxTuningPage this$0) {
        this$0.analyze();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildLateral$lambda$31(GxTuningPage this$0) {
        runWith$default(this$0, "POST", "/api/flm/analyze/stop", null, "FLM analysis stopped.", null, 16, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildLateral$lambda$32(GxTuningPage this$0) {
        runWith$default(this$0, "POST", "/api/flm/trials/revert", null, "Trial reverted.", null, 16, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildLateral$lambda$34(final GxTuningPage this$0) {
        this$0.confirm("Keep Current as Baseline", "Keep the currently applied tuning values and end this trial? This does not restore the previous tune.", "Yes, Keep", new Function0() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildLateral$lambda$34$lambda$33;
                buildLateral$lambda$34$lambda$33 = GxTuningPage.buildLateral$lambda$34$lambda$33(this$0);
                return buildLateral$lambda$34$lambda$33;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildLateral$lambda$34$lambda$33(GxTuningPage this$0) {
        runWith$default(this$0, "POST", "/api/flm/trials/accept", null, "Current tune kept as baseline.", null, 16, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildLateral$lambda$35(GxTuningPage this$0) {
        this$0.saveCurrent();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildLateral$lambda$36(GxTuningPage this$0) {
        flmRefresh$default(this$0, false, null, 3, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildLateral$lambda$37(GxTuningPage this$0) {
        this$0.saveCurrent();
        return Unit.INSTANCE;
    }

    private final void paintFlm() {
        if (this.flmBuilt) {
            paintNotices();
            paintActions();
            paintStatus();
            paintTunes();
            paintReports();
            paintComparison();
            paintProfiles();
        }
    }

    private final void paintNotices() {
        LinearLayout linearLayout = this.flmNotices;
        LinearLayout linearLayout2 = null;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("flmNotices");
            linearLayout = null;
        }
        linearLayout.removeAllViews();
        if (this.laneCentering) {
            LinearLayout linearLayout3 = this.flmNotices;
            if (linearLayout3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("flmNotices");
                linearLayout3 = null;
            }
            notice$default(this, linearLayout3, "Turn Lane Centering off. FLM must analyze the model's unmodified lateral request; routes recorded with Lane Centering enabled are excluded.", 0, "Before using FLM:", 4, null);
        }
        if (this.isOnroad) {
            LinearLayout linearLayout4 = this.flmNotices;
            if (linearLayout4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("flmNotices");
                linearLayout4 = null;
            }
            notice$default(this, linearLayout4, "FLM analysis is offroad-only. Stop the car and go offroad before starting a run.", 0, null, 12, null);
        }
        if (rollbackUnavailable()) {
            LinearLayout linearLayout5 = this.flmNotices;
            if (linearLayout5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("flmNotices");
                linearLayout5 = null;
            }
            notice$default(this, linearLayout5, "The original rollback data is unavailable. Keep the current tune as the new baseline before applying another trial.", 0, null, 12, null);
        }
        if (!StringsKt.isBlank(this.flmError)) {
            LinearLayout linearLayout6 = this.flmNotices;
            if (linearLayout6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("flmNotices");
                linearLayout6 = null;
            }
            notice$default(this, linearLayout6, this.flmError, Color.rgb(224, 85, 119), null, 8, null);
        }
        if (this.flmLoading) {
            GxTuningPage gxTuningPage = this;
            LinearLayout linearLayout7 = this.flmNotices;
            if (linearLayout7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("flmNotices");
            } else {
                linearLayout2 = linearLayout7;
            }
            GxPage.text$default(gxTuningPage, "Loading tuning workspace...", linearLayout2, 0.0f, 0, false, 28, null);
        }
    }

    private final void paintActions() {
        if (this.flmBuilt) {
            JSONObject st = status();
            TextView textView = this.bAnalyze;
            TextView textView2 = null;
            if (textView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("bAnalyze");
                textView = null;
            }
            boolean z = false;
            en(textView, !this.busy && canAnalyze());
            TextView textView3 = this.bStop;
            if (textView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("bStop");
                textView3 = null;
            }
            en(textView3, !this.busy && truthy(st.opt("running")));
            TextView textView4 = this.bRevert;
            if (textView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("bRevert");
                textView4 = null;
            }
            en(textView4, (this.busy || this.activeTrial == null || rollbackUnavailable()) ? false : true);
            TextView textView5 = this.bKeep;
            if (textView5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("bKeep");
                textView5 = null;
            }
            textView5.setVisibility(rollbackUnavailable() ? 0 : 8);
            TextView textView6 = this.bKeep;
            if (textView6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("bKeep");
                textView6 = null;
            }
            en(textView6, !this.busy);
            TextView textView7 = this.bSave;
            if (textView7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("bSave");
                textView7 = null;
            }
            en(textView7, (this.busy || this.activeTrial == null) ? false : true);
            TextView textView8 = this.bRefresh;
            if (textView8 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("bRefresh");
                textView8 = null;
            }
            en(textView8, !this.busy);
            TextView textView9 = this.bSaveCurrent;
            if (textView9 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("bSaveCurrent");
            } else {
                textView2 = textView9;
            }
            if (!this.busy && this.activeTrial != null) {
                z = true;
            }
            en(textView2, z);
        }
    }

    private final void paintStatus() {
        LinearLayout linearLayout = this.flmStatusBox;
        LinearLayout linearLayout2 = null;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("flmStatusBox");
            linearLayout = null;
        }
        linearLayout.removeAllViews();
        JSONObject st = status();
        LinearLayout linearLayout3 = this.flmStatusBox;
        if (linearLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("flmStatusBox");
            linearLayout3 = null;
        }
        LinearLayout linearLayout4 = linearLayout3;
        String s = s(st, "state");
        if (StringsKt.isBlank(s)) {
            s = "idle";
        }
        line$default(this, linearLayout4, "State", s, 0, 8, null);
        LinearLayout linearLayout5 = this.flmStatusBox;
        if (linearLayout5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("flmStatusBox");
            linearLayout5 = null;
        }
        line$default(this, linearLayout5, "Running", truthy(st.opt("running")) ? "Yes" : "No", 0, 8, null);
        LinearLayout linearLayout6 = this.flmStatusBox;
        if (linearLayout6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("flmStatusBox");
            linearLayout6 = null;
        }
        line$default(this, linearLayout6, "Onroad", this.isOnroad ? "Yes" : "No", 0, 8, null);
        LinearLayout linearLayout7 = this.flmStatusBox;
        if (linearLayout7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("flmStatusBox");
            linearLayout7 = null;
        }
        line$default(this, linearLayout7, "Lane Centering", this.laneCentering ? "On" : "Off", 0, 8, null);
        LinearLayout linearLayout8 = this.flmStatusBox;
        if (linearLayout8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("flmStatusBox");
            linearLayout8 = null;
        }
        line$default(this, linearLayout8, "Updated", fmtAge(st.opt("updatedAt")), 0, 8, null);
        if (truthy(st.opt("currentSegment"))) {
            LinearLayout linearLayout9 = this.flmStatusBox;
            if (linearLayout9 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("flmStatusBox");
                linearLayout9 = null;
            }
            line$default(this, linearLayout9, "Current segment", s(st, "currentSegment"), 0, 8, null);
        }
        if (truthy(st.opt("lastSkippedSegment"))) {
            LinearLayout linearLayout10 = this.flmStatusBox;
            if (linearLayout10 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("flmStatusBox");
                linearLayout10 = null;
            }
            line$default(this, linearLayout10, "Skipped", s(st, "lastSkippedSegment"), 0, 8, null);
        }
        JSONObject t = this.activeTrial;
        if (t != null) {
            LinearLayout linearLayout11 = this.flmStatusBox;
            if (linearLayout11 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("flmStatusBox");
                linearLayout11 = null;
            }
            LinearLayout linearLayout12 = linearLayout11;
            String s2 = s(t, "profileLabel");
            if (StringsKt.isBlank(s2)) {
                s2 = s(t, "profileId");
            }
            String str = s2;
            if (StringsKt.isBlank(str)) {
                str = "Active";
            }
            line(linearLayout12, "Active trial", str, CarUi.Companion.getACCENT());
        }
        if (truthy(st.opt("running")) && truthy(st.opt("total"))) {
            LinearLayout linearLayout13 = this.flmStatusBox;
            if (linearLayout13 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("flmStatusBox");
            } else {
                linearLayout2 = linearLayout13;
            }
            line$default(this, linearLayout2, "Progress", ((long) num$default(this, st.opt("progress"), 0.0d, 2, null)) + " / " + ((long) num$default(this, st.opt("total"), 0.0d, 2, null)), 0, 8, null);
        }
    }

    private final void paintTunes() {
        LinearLayout linearLayout = this.tunesBox;
        LinearLayout linearLayout2 = null;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tunesBox");
            linearLayout = null;
        }
        linearLayout.removeAllViews();
        List list = objects(this.savedTunes);
        if (list.isEmpty()) {
            LinearLayout linearLayout3 = this.tunesBox;
            if (linearLayout3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tunesBox");
            } else {
                linearLayout2 = linearLayout3;
            }
            linearLayout2.addView(empty("No saved tunes yet. Apply a trial, then save it here."));
            return;
        }
        for (final JSONObject t : (Iterable<JSONObject>) list) {
            final boolean active = truthy(t.opt("active"));
            LinearLayout linearLayout4 = this.tunesBox;
            if (linearLayout4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tunesBox");
                linearLayout4 = null;
            }
            LinearLayout c = card(linearLayout4);
            String s = s(t, "name");
            if (StringsKt.isBlank(s)) {
                s = "Saved Tune";
            }
            String str = ((Object) s) + (active ? " (Active)" : "");
            LinearLayout linearLayout5 = c;
            CarUi.Companion_ companion = CarUi.Companion;
            text(str, linearLayout5, 21.0f, active ? companion.getACCENT() : companion.getTEXT(), true);
            GxTuningPage gxTuningPage = this;
            String s2 = s(t, "carFingerprint");
            if (StringsKt.isBlank(s2)) {
                s2 = "Unknown car";
            }
            String it = s(t, "pathLabel");
            GxPage.text$default(gxTuningPage, new StringBuilder().append((Object) s2).append((Object) (StringsKt.isBlank(it) ? "" : " / " + it)).toString(), c, 0.0f, 0, false, 28, null);
            GxPage.text$default(this, s(t, "genericParamCount") + " generic, " + s(t, "frictionCurveCount") + " friction curve, " + s(t, "vehicleKnobCount") + " knobs", c, 0.0f, 0, false, 28, null);
            GxPage.text$default(this, fmtAge(t.opt("updatedAt")), c, 0.0f, 0, false, 28, null);
            LinearLayout bar = btnBar(c);
            en(barBtn(bar, active ? "Active" : "Apply", !active, new Function0() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda27
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit paintTunes$lambda$45;
                    paintTunes$lambda$45 = GxTuningPage.paintTunes$lambda$45(GxTuningPage.this, active, t);
                    return paintTunes$lambda$45;
                }
            }), (this.busy || active) ? false : true);
            en(barBtn$default(this, bar, "Rename", false, new Function0() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda28
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit paintTunes$lambda$46;
                    paintTunes$lambda$46 = GxTuningPage.paintTunes$lambda$46(GxTuningPage.this, t);
                    return paintTunes$lambda$46;
                }
            }, 4, null), !this.busy);
            TextView $this$paintTunes_u24lambda_u2450 = barBtn$default(this, bar, "Delete", false, new Function0() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda29
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit paintTunes$lambda$49;
                    paintTunes$lambda$49 = GxTuningPage.paintTunes$lambda$49(GxTuningPage.this, active, t);
                    return paintTunes$lambda$49;
                }
            }, 4, null);
            $this$paintTunes_u24lambda_u2450.setTextColor(Color.rgb(240, 110, 130));
            en($this$paintTunes_u24lambda_u2450, (this.busy || active) ? false : true);
            en(barBtn$default(this, bar, "Firestar", false, new Function0() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda30
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit paintTunes$lambda$51;
                    paintTunes$lambda$51 = GxTuningPage.paintTunes$lambda$51(GxTuningPage.this, t);
                    return paintTunes$lambda$51;
                }
            }, 4, null), !this.busy);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintTunes$lambda$45(GxTuningPage this$0, boolean $active, JSONObject $t) {
        if (!this$0.busy && !$active) {
            runWith$default(this$0, "POST", "/api/flm/saved-tunes/" + this$0.enc(this$0.s($t, "tuneId")) + "/apply", null, "Saved tune applied.", null, 16, null);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintTunes$lambda$46(GxTuningPage this$0, JSONObject $t) {
        this$0.renameSavedTune($t);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintTunes$lambda$49(final GxTuningPage this$0, boolean $active, final JSONObject $t) {
        if (this$0.busy || $active) {
            return Unit.INSTANCE;
        }
        String s = this$0.s($t, "name");
        if (StringsKt.isBlank(s)) {
            s = "Saved Tune";
        }
        this$0.confirm("Delete Saved Tune", "Delete saved tune \"" + ((Object) s) + "\"?", "Yes, Delete", new Function0() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit paintTunes$lambda$49$lambda$48;
                paintTunes$lambda$49$lambda$48 = GxTuningPage.paintTunes$lambda$49$lambda$48(this$0, $t);
                return paintTunes$lambda$49$lambda$48;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintTunes$lambda$49$lambda$48(GxTuningPage this$0, JSONObject $t) {
        runWith$default(this$0, "DELETE", "/api/flm/saved-tunes/" + this$0.enc(this$0.s($t, "tuneId")), null, "Saved tune deleted.", null, 16, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintTunes$lambda$51(GxTuningPage this$0, JSONObject $t) {
        this$0.submitTune($t);
        return Unit.INSTANCE;
    }

    private final void renameSavedTune(JSONObject t) {
        final String id = s(t, "tuneId");
        if (StringsKt.isBlank(id) || this.busy) {
            return;
        }
        GxTuningPage gxTuningPage = this;
        String s = s(t, "name");
        String s2 = s(t, "name");
        if (StringsKt.isBlank(s2)) {
            s2 = "Saved Tune";
        }
        GxPage.prompt$default(gxTuningPage, "Rename Saved Tune", "Name this tune...", s, 0, "Choose a new name for “" + ((Object) s2) + "”.", new Function1() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda15
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit renameSavedTune$lambda$53;
                renameSavedTune$lambda$53 = GxTuningPage.renameSavedTune$lambda$53(GxTuningPage.this, id, (String) obj);
                return renameSavedTune$lambda$53;
            }
        }, 8, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renameSavedTune$lambda$53(GxTuningPage this$0, String $id, String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        runWith$default(this$0, "PATCH", "/api/flm/saved-tunes/" + this$0.enc($id), new JSONObject().put("name", name), "Tune renamed.", null, 16, null);
        return Unit.INSTANCE;
    }

    private final void submitTune(final JSONObject t) {
        confirm("Send to Firestar", "Think this FLM tune is genuinely good and worth sharing? Only the tune values, car identity, and your Discord username are sent; routes and driving logs are not included.", "Continue", new Function0() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda32
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit submitTune$lambda$55;
                submitTune$lambda$55 = GxTuningPage.submitTune$lambda$55(GxTuningPage.this, t);
                return submitTune$lambda$55;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit submitTune$lambda$55(final GxTuningPage this$0, final JSONObject $t) {
        GxPage.prompt$default(this$0, "Send to Firestar", "Enter your Discord username...", null, 0, null, new Function1() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda53
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit submitTune$lambda$55$lambda$54;
                submitTune$lambda$55$lambda$54 = GxTuningPage.submitTune$lambda$55$lambda$54(this$0, $t, (String) obj);
                return submitTune$lambda$55$lambda$54;
            }
        }, 28, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit submitTune$lambda$55$lambda$54(GxTuningPage this$0, JSONObject $t, String raw) {
        Intrinsics.checkNotNullParameter(raw, "raw");
        String name = StringsKt.trim((CharSequence) raw).toString();
        if (name.length() == 0) {
            this$0.toast("A Discord username is required.");
            return Unit.INSTANCE;
        }
        runWith$default(this$0, "POST", "/api/flm/saved-tunes/" + this$0.enc(this$0.s($t, "tuneId")) + "/submit", new JSONObject().put("discordUsername", name), "Tune submitted to Firestar.", null, 16, null);
        return Unit.INSTANCE;
    }

    private final void saveCurrent() {
        JSONObject t = this.activeTrial;
        if (t == null || this.busy) {
            return;
        }
        String s = s(t, "profileLabel");
        if (StringsKt.isBlank(s)) {
            s = this.carFp;
        }
        String str = s;
        if (StringsKt.isBlank(str)) {
            str = "Saved Tune";
        }
        String def = str;
        GxPage.prompt$default(this, "Save Current Tune", "Name this tune...", def, 0, null, new Function1() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda39
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit saveCurrent$lambda$58;
                saveCurrent$lambda$58 = GxTuningPage.saveCurrent$lambda$58(GxTuningPage.this, (String) obj);
                return saveCurrent$lambda$58;
            }
        }, 24, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit saveCurrent$lambda$58(GxTuningPage this$0, String raw) {
        Intrinsics.checkNotNullParameter(raw, "raw");
        String name = StringsKt.trim((CharSequence) raw).toString();
        if (name.length() == 0) {
            this$0.toast("A name is required.");
            return Unit.INSTANCE;
        }
        runWith$default(this$0, "POST", "/api/flm/saved-tunes", new JSONObject().put("name", name), "Tune saved.", null, 16, null);
        return Unit.INSTANCE;
    }

    static /* synthetic */ void runWith$default(GxTuningPage gxTuningPage, String str, String str2, JSONObject jSONObject, String str3, Function1 function1, int i, Object obj) {
        if ((i & 16) != 0) {
            function1 = null;
        }
        gxTuningPage.runWith(str, str2, jSONObject, str3, function1);
    }

    private final void runWith(String method, String path, JSONObject body, final String okMsg, final Function1<? super JSONObject, Unit> after) {
        if (this.busy) {
            return;
        }
        this.busy = true;
        this.flmError = "";
        paintActions();
        send(method, path, body, 60L, new Function2() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda31
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit runWith$lambda$61;
                runWith$lambda$61 = GxTuningPage.runWith$lambda$61(GxTuningPage.this, after, okMsg, (String) obj, (JSONObject) obj2);
                return runWith$lambda$61;
            }
        });
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public static final Unit runWith$lambda$61(GxTuningPage var0, Function1 var1, String var2, String var3, JSONObject var4) {
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

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit runWith$lambda$61$lambda$60(GxTuningPage this$0, Function1 $after, JSONObject $o) {
        this$0.busy = false;
        this$0.paintFlm();
        if ($after != null) {
            $after.invoke($o == null ? new JSONObject() : $o);
        }
        return Unit.INSTANCE;
    }

    /* VF: from Vineflower (jadx: ) */
    static void flmRefresh$default(GxTuningPage var0, boolean var1, Function0 var2, int var3, Object var4) {
        if ((var3 & 1) != 0) {
            var1 = false;
        }

        if ((var3 & 2) != 0) {
            var2 = null;
        }

        var0.flmRefresh(var1, var2);
    }

    private final void flmRefresh(boolean poll, final Function0<Unit> then) {
        if (poll && (this.flmFetching || this.busy)) {
            return;
        }
        this.flmFetching = true;
        getJson("/api/flm/workspace", new Function2() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda56
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit flmRefresh$lambda$66;
                flmRefresh$lambda$66 = GxTuningPage.flmRefresh$lambda$66(GxTuningPage.this, then, (String) obj, (JSONObject) obj2);
                return flmRefresh$lambda$66;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit flmRefresh$lambda$66(final GxTuningPage this$0, final Function0 $then, final String e, final JSONObject ws) {
        if (ws != null) {
            this$0.reports = this$0.arr(ws, "reports");
            this$0.savedTunes = this$0.arr(ws, "savedTunes");
            this$0.carFp = this$0.s(ws, "currentCarFingerprint");
            this$0.activeTrial = ws.optJSONObject("activeTrial");
            JSONObject optJSONObject = ws.optJSONObject("status");
            if (optJSONObject == null) {
                optJSONObject = new JSONObject();
            }
            this$0.wsStatus = optJSONObject;
        }
        this$0.getJson("/api/flm/status", new Function2() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda58
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit flmRefresh$lambda$66$lambda$65;
                flmRefresh$lambda$66$lambda$65 = GxTuningPage.flmRefresh$lambda$66$lambda$65(this$0, ws, e, $then, (String) obj, (JSONObject) obj2);
                return flmRefresh$lambda$66$lambda$65;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit flmRefresh$lambda$66$lambda$65(GxTuningPage this$0, JSONObject $ws, String $e, Function0 $then, String e2, JSONObject st) {
        this$0.flmFetching = false;
        if (st != null) {
            this$0.isOnroad = this$0.truthy(st.opt("isOnroad"));
            this$0.laneCentering = this$0.truthy(st.opt("laneCentering"));
            this$0.statusData = st.optJSONObject("status");
            JSONArray it = st.optJSONArray("savedTunes");
            if (it != null && it.length() > 0) {
                this$0.savedTunes = it;
            }
            JSONObject it2 = st.optJSONObject("activeTrial");
            if (it2 != null) {
                this$0.activeTrial = it2;
            }
            JSONArray it3 = st.optJSONArray("reports");
            if (it3 != null && it3.length() > 0) {
                this$0.reports = it3;
            }
        }
        this$0.flmError = ($ws == null && st == null) ? $e == null ? e2 == null ? "Failed to load tuning workspace." : e2 : $e : "";
        this$0.flmLoading = false;
        this$0.paintFlm();
        this$0.autoOpenReport();
        if ($then != null) {
            $then.invoke();
        }
        return Unit.INSTANCE;
    }

    private final void autoOpenReport() {
        String first;
        String statusId = s(status(), "reportId");
        if (!StringsKt.isBlank(statusId) && !Intrinsics.areEqual(statusId, this.lastStatusReportId)) {
            this.lastStatusReportId = statusId;
            loadReport(statusId);
            return;
        }
        JSONObject it = this.reports.optJSONObject(0);
        if (it == null || (first = s(it, "reportId")) == null) {
            first = "";
        }
        if (!StringsKt.isBlank(this.loadedReportId) || StringsKt.isBlank(first)) {
            return;
        }
        loadReport(first);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void loadReport(String id) {
        if (StringsKt.isBlank(id)) {
            return;
        }
        if (Intrinsics.areEqual(id, this.loadedReportId) && this.report != null) {
            return;
        }
        this.loadedReportId = id;
        this.reportLoading = true;
        renderReport();
        paintReports();
        paintProfiles();
        getJson("/api/flm/report/" + enc(id), new Function2() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda19
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit loadReport$lambda$68;
                loadReport$lambda$68 = GxTuningPage.loadReport$lambda$68(GxTuningPage.this, (String) obj, (JSONObject) obj2);
                return loadReport$lambda$68;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadReport$lambda$68(GxTuningPage this$0, String e, JSONObject o) {
        this$0.reportLoading = false;
        if (o != null) {
            this$0.report = o;
            this$0.syncFeedback(o);
            this$0.flmError = "";
        } else {
            this$0.report = null;
            this$0.toast(e == null ? "Failed to load report." : e);
        }
        this$0.renderReport();
        this$0.paintFlm();
        return Unit.INSTANCE;
    }

    private final void syncFeedback(JSONObject rep) {
        JSONObject fb = obj(rep, "feedback");
        this.fbAccepted.clear();
        this.fbAccepted.addAll(strings(arr(fb, "acceptedDimensions")));
        this.fbIgnored.clear();
        this.fbIgnored.addAll(strings(arr(fb, "ignoredDimensions")));
        Object opt = fb.opt("notes");
        String str = opt instanceof String ? (String) opt : null;
        if (str == null) {
            str = "";
        }
        this.fbNotes = str;
    }

    private final void paintReports() {
        LinearLayout linearLayout = this.reportsBox;
        LinearLayout linearLayout2 = null;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("reportsBox");
            linearLayout = null;
        }
        linearLayout.removeAllViews();
        List list = objects(this.reports);
        if (this.flmLoading || list.isEmpty()) {
            LinearLayout linearLayout3 = this.reportsBox;
            if (linearLayout3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("reportsBox");
            } else {
                linearLayout2 = linearLayout3;
            }
            linearLayout2.addView(empty("No reports yet. Analyze routes to generate findings."));
            return;
        }
        for (JSONObject r : (Iterable<JSONObject>) list) {
            final String id = s(r, "reportId");
            boolean open = Intrinsics.areEqual(this.loadedReportId, id);
            String fmtDate = fmtDate(r.opt("createdAt"));
            String it = s(r, "controlPath");
            String desc = fmtDate + ((Object) (!StringsKt.isBlank(it) ? " / " + it : ""));
            GxTuningPage gxTuningPage = this;
            String s = s(r, "carFingerprint");
            if (StringsKt.isBlank(s)) {
                s = "Unknown car";
            }
            String str = s;
            LinearLayout linearLayout4 = this.reportsBox;
            if (linearLayout4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("reportsBox");
                linearLayout4 = null;
            }
            GxPage.Row row = GxPage.row$default(gxTuningPage, str, desc, linearLayout4, 0, 8, null);
            row.button(open ? "Open ✓" : "Open", open, new Function0() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda9
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit paintReports$lambda$71;
                    paintReports$lambda$71 = GxTuningPage.paintReports$lambda$71(GxTuningPage.this, id);
                    return paintReports$lambda$71;
                }
            });
            row.getView().setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda10
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    GxTuningPage.this.loadReport(id);
                }
            });
            if (open) {
                row.getView().setBackground(getUi().rounded(CarUi.Companion.getCARD_ON(), 14, Integer.valueOf(CarUi.Companion.getACCENT())));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintReports$lambda$71(GxTuningPage this$0, String $id) {
        this$0.loadReport($id);
        return Unit.INSTANCE;
    }

    private final String fmtVal(Object v) {
        if (v instanceof JSONArray) {
            return curveStr((JSONArray) v);
        }
        if (v instanceof Boolean) {
            return ((Boolean) v).booleanValue() ? "On" : "Off";
        }
        if (v == null || Intrinsics.areEqual(v, JSONObject.NULL)) {
            return "-";
        }
        if (v instanceof Number) {
            double doubleValue = ((Number) v).doubleValue();
            return !Double.isInfinite(doubleValue) && !Double.isNaN(doubleValue) ? f(((Number) v).doubleValue(), 3) : ((Number) v).toString();
        }
        if (!(v instanceof String)) {
            return v.toString();
        }
        Double valueOf = StringsKt.isBlank((CharSequence) v) ? Double.valueOf(0.0d) : StringsKt.toDoubleOrNull(StringsKt.trim((CharSequence) v).toString());
        if (valueOf != null) {
            double it = valueOf.doubleValue();
            String f = f(it, 3);
            if (f != null) {
                return f;
            }
        }
        return (String) v;
    }

    private final String curveStr(final JSONArray a) {
        return "[" + CollectionsKt.joinToString(RangesKt.until(0, a != null ? a.length() : 0), ", ", "", "", -1, "...", new Function1() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda54
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                CharSequence curveStr$lambda$74;
                curveStr$lambda$74 = GxTuningPage.curveStr$lambda$74(GxTuningPage.this, a, ((Integer) obj).intValue());
                return curveStr$lambda$74;
            }
        }) + "]";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence curveStr$lambda$74(GxTuningPage this$0, JSONArray $a, int it) {
        Intrinsics.checkNotNull($a);
        return this$0.fmtNum3($a.opt(it));
    }

    private final String fmtNum3(Object v) {
        double d = num(v, Double.NaN);
        return Double.isNaN(d) ? "NaN" : f(d, 3);
    }

    private final List<JSONObject> pathList() {
        JSONObject rep = this.report;
        if (rep == null) {
            return CollectionsKt.emptyList();
        }
        JSONArray p = rep.optJSONArray("paths");
        if (p != null && p.length() > 0) {
            return objects(p);
        }
        JSONObject jSONObject = new JSONObject();
        String s = s(rep, "primaryPathKey");
        if (StringsKt.isBlank(s)) {
            s = "cleanup_pass";
        }
        return CollectionsKt.listOf(jSONObject.put("key", s).put("title", "Recommendations").put("description", "").put("whySelected", "").put("isPrimary", true).put("suggestions", arr(rep, "suggestions")).put("profiles", arr(rep, "profiles")));
    }

    private final String activePathKey() {
        JSONObject it = this.report;
        if (it != null) {
            String s = s(it, "selectedPathKey");
            if (StringsKt.isBlank(s)) {
                s = s(it, "primaryPathKey");
            }
            String str = s;
            if (str != null) {
                return str;
            }
        }
        return "";
    }

    private final JSONObject primaryPath() {
        Object obj;
        Object element$iv;
        List ps = pathList();
        String k = activePathKey();
        List $this$firstOrNull$iv = ps;
        Iterator it = $this$firstOrNull$iv.iterator();
        while (true) {
            obj = null;
            if (!it.hasNext()) {
                element$iv = null;
                break;
            }
            element$iv = it.next();
            JSONObject it2 = (JSONObject) element$iv;
            if (Intrinsics.areEqual(s(it2, "key"), k)) {
                break;
            }
        }
        JSONObject jSONObject = (JSONObject) element$iv;
        if (jSONObject != null) {
            return jSONObject;
        }
        List $this$firstOrNull$iv2 = ps;
        Iterator it3 = $this$firstOrNull$iv2.iterator();
        while (true) {
            if (!it3.hasNext()) {
                break;
            }
            Object element$iv2 = it3.next();
            JSONObject it4 = (JSONObject) element$iv2;
            if (truthy(it4.opt("isPrimary"))) {
                obj = element$iv2;
                break;
            }
        }
        JSONObject jSONObject2 = (JSONObject) obj;
        return jSONObject2 == null ? (JSONObject) CollectionsKt.firstOrNull(ps) : jSONObject2;
    }

    private final List<JSONObject> suggestions() {
        JSONObject p = primaryPath();
        return objects(p != null ? arr(p, "suggestions") : arr(this.report, "suggestions"));
    }

    private final List<JSONObject> profiles() {
        List l = objects(arr(primaryPath(), "profiles"));
        return !l.isEmpty() ? l : objects(arr(this.report, "profiles"));
    }

    private final boolean angleControl() {
        JSONObject it;
        JSONObject jSONObject = this.report;
        return Intrinsics.areEqual((jSONObject == null || (it = jSONObject.optJSONObject("car")) == null) ? null : s(it, "controlPath"), "angle");
    }

    /* compiled from: GxTuning.kt */
    private static final /* data */ class CmpRow {
        private final Object current;
        private final boolean curve;
        private final String key;
        private final String label;
        private final Object stock;

        public static /* synthetic */ CmpRow copy$default(CmpRow cmpRow, String str, String str2, boolean z, Object obj, Object obj2, int i, Object obj3) {
            if ((i & 1) != 0) {
                str = cmpRow.key;
            }
            if ((i & 2) != 0) {
                str2 = cmpRow.label;
            }
            String str3 = str2;
            if ((i & 4) != 0) {
                z = cmpRow.curve;
            }
            boolean z2 = z;
            if ((i & 8) != 0) {
                obj = cmpRow.stock;
            }
            Object obj4 = obj;
            if ((i & 16) != 0) {
                obj2 = cmpRow.current;
            }
            return cmpRow.copy(str, str3, z2, obj4, obj2);
        }





        public final CmpRow copy(String key, String label, boolean curve, Object stock, Object current) {
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(label, "label");
            return new CmpRow(key, label, curve, stock, current);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof CmpRow)) {
                return false;
            }
            CmpRow cmpRow = (CmpRow) other;
            return Intrinsics.areEqual(this.key, cmpRow.key) && Intrinsics.areEqual(this.label, cmpRow.label) && this.curve == cmpRow.curve && Intrinsics.areEqual(this.stock, cmpRow.stock) && Intrinsics.areEqual(this.current, cmpRow.current);
        }

        public int hashCode() {
            return (((((((this.key.hashCode() * 31) + this.label.hashCode()) * 31) + Boolean.hashCode(this.curve)) * 31) + (this.stock == null ? 0 : this.stock.hashCode())) * 31) + (this.current != null ? this.current.hashCode() : 0);
        }

        public String toString() {
            return "CmpRow(key=" + this.key + ", label=" + this.label + ", curve=" + this.curve + ", stock=" + this.stock + ", current=" + this.current + ")";
        }

        public CmpRow(String key, String label, boolean curve, Object stock, Object current) {
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(label, "label");
            this.key = key;
            this.label = label;
            this.curve = curve;
            this.stock = stock;
            this.current = current;
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
    }

    private final List<CmpRow> comparisonRows() {
        JSONArray jSONArray;
        JSONObject rep = this.report;
        if (rep == null) {
            return CollectionsKt.emptyList();
        }
        JSONObject stock = obj(rep, "stockParams");
        JSONObject current = obj(rep, "currentParams");
        JSONObject trial = obj(this.activeTrial, "appliedGenericParams");
        boolean angle = angleControl();
        List<Pair> pairs = angle ? CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to("Auto steer delay", "UseAutoSteerDelay"), TuplesKt.to("Steer delay", "SteerDelay"), TuplesKt.to("Steer ratio", "SteerRatio")}) : CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to("Lat accel", "SteerLatAccel"), TuplesKt.to("Friction", "SteerFriction"), TuplesKt.to("Auto steer delay", "UseAutoSteerDelay"), TuplesKt.to("Steer delay", "SteerDelay"), TuplesKt.to("Steer ratio", "SteerRatio"), TuplesKt.to("KP", "SteerKP")});
        ArrayList rows = new ArrayList();
        for (Pair pair : pairs) {
            String label = (String) pair.component1();
            String key = (String) pair.component2();
            rows.add(new CmpRow(key, label, false, stock.opt(key), trial.has(key) ? trial.opt(key) : current.opt(key)));
        }
        if (angle) {
            return rows;
        }
        JSONObject ovFriction = obj(this.activeTrial, "appliedFrictionThresholds");
        for (Pair<String, Object> pair2 : entries(obj(stock, "FLMBaseFrictionThresholds"))) {
            String family = pair2.component1();
            Object payload = pair2.component2();
            JSONObject jSONObject = payload instanceof JSONObject ? (JSONObject) payload : null;
            if (jSONObject == null || (jSONArray = jSONObject.optJSONArray("values")) == null) {
                jSONArray = new JSONArray();
            }
            JSONArray pv = jSONArray;
            JSONObject optJSONObject = ovFriction.optJSONObject(family);
            JSONArray ov = optJSONObject != null ? optJSONObject.optJSONArray("values") : null;
            rows.add(new CmpRow("friction-" + family, family + " friction threshold", true, pv, ov == null ? pv : ov));
        }
        JSONObject stockKnobs = obj(stock, "FLMVehicleKnobs");
        for (Pair<String, Object> pair3 : entries(obj(this.activeTrial, "appliedVehicleKnobs"))) {
            String symbol = pair3.component1();
            Object value = pair3.component2();
            if (stockKnobs.has(symbol)) {
                rows.add(new CmpRow(symbol, CollectionsKt.joinToString(CollectionsKt.drop(StringsKt.split((CharSequence) symbol, new String[]{"."}, false, 0), 1), ".", "", "", -1, "...", null), false, stockKnobs.opt(symbol), value));
            }
        }
        return rows;
    }

    private final void paintComparison() {
        String st;
        String cu;
        LinearLayout box = this.comparisonBox;
        if (box == null) {
            return;
        }
        box.removeAllViews();
        List rows = comparisonRows();
        if (rows.isEmpty()) {
            return;
        }
        sub(angleControl() ? "Applicable Angle Settings" : "Stock vs Current FLM", box);
        if (!angleControl()) {
            GxPage.text$default(this, "Active trial values are shown in the current column when one is applied.", box, 0.0f, 0, false, 28, null);
        }
        for (CmpRow r : (Iterable<CmpRow>) rows) {
            LinearLayout l = new LinearLayout(getAct());
            l.setOrientation(0);
            l.setGravity(16);
            l.setPadding(0, getUi().dp(6), 0, getUi().dp(6));
            TextView $this$paintComparison_u24lambda_u2482 = new TextView(getAct());
            $this$paintComparison_u24lambda_u2482.setText(r.getLabel());
            $this$paintComparison_u24lambda_u2482.setTextSize(17.0f);
            $this$paintComparison_u24lambda_u2482.setTextColor(CarUi.Companion.getDIM());
            l.addView($this$paintComparison_u24lambda_u2482, new LinearLayout.LayoutParams(0, -2, 1.0f));
            LinearLayout right = new LinearLayout(getAct());
            right.setOrientation(1);
            right.setGravity(8388613);
            if (r.getCurve()) {
                Object stock = r.getStock();
                st = curveStr(stock instanceof JSONArray ? (JSONArray) stock : null);
            } else {
                st = fmtVal(r.getStock());
            }
            if (r.getCurve()) {
                Object current = r.getCurrent();
                cu = curveStr(current instanceof JSONArray ? (JSONArray) current : null);
            } else {
                cu = fmtVal(r.getCurrent());
            }
            TextView $this$paintComparison_u24lambda_u2484 = new TextView(getAct());
            $this$paintComparison_u24lambda_u2484.setText("stock " + st);
            $this$paintComparison_u24lambda_u2484.setTextSize(14.0f);
            $this$paintComparison_u24lambda_u2484.setTextColor(CarUi.Companion.getDIM());
            $this$paintComparison_u24lambda_u2484.setGravity(8388613);
            right.addView($this$paintComparison_u24lambda_u2484);
            TextView $this$paintComparison_u24lambda_u2485 = new TextView(getAct());
            $this$paintComparison_u24lambda_u2485.setText(cu);
            $this$paintComparison_u24lambda_u2485.setTextSize(17.0f);
            $this$paintComparison_u24lambda_u2485.setTextColor(CarUi.Companion.getACCENT());
            $this$paintComparison_u24lambda_u2485.setTypeface(Typeface.DEFAULT_BOLD);
            $this$paintComparison_u24lambda_u2485.setGravity(8388613);
            right.addView($this$paintComparison_u24lambda_u2485);
            l.addView(right, new LinearLayout.LayoutParams(0, -2, 1.4f));
            box.addView(l);
        }
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    private final void renderReport() {
        String var21 = null; Object var4 = null; // declarations Vineflower left out
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
                Object var22 = var44;
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
                int var3;
                if (!Double.isInfinite(var1) && !Double.isNaN(var1)) {
                    var3 = 1;
                } else {
                    var3 = 0;
                }

                if ((var3 != 0) && (var1 != 0) > 1.0E9) {
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

                for (JSONObject var70 : (JSONObject) (JSONObject) (JSONObject) (JSONObject) (JSONObject) var22) {
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
                ((EditText) var22).setText(this.fbNotes);
                var22.setHint("Optional tuning notes");
                var22.setTextSize(18.0F);
                var22.setTextColor(CarUi.Companion.getTEXT());
                var22.setHintTextColor(CarUi.Companion.getDIM());
                var22.setInputType(147457);
                var22.setMinLines(2);
                ((LinearLayout) var22).setGravity(8388659);
                var22.setBackground((Drawable)this.getUi().rounded(Color.rgb(6, 8, 11), 12, Color.rgb(50, 55, 66)));
                var22.setPadding(this.getUi().dp(14), this.getUi().dp(10), this.getUi().dp(14), this.getUi().dp(10));
                var22.addTextChangedListener(new TextWatcher() {
                    final GxTuningPage this$0 = GxTuningPage.this;

                    public void afterTextChanged(Editable var1) {
                        GxTuningPage var3x;
                        label11: {
                            var3x = this.this$0;
                            if (var1 != null) {
                                String var2 = var1.toString();
                                var4 = var2;
                                if (var2 != null) {
                                    break label11;
                                }
                            }

                            var4 = "";
                        }

                        var3x.fbNotes = ((String) var4);
                    }

                    public void beforeTextChanged(CharSequence var1, int var2, int var3x, int var4) {
                    }

                    public void onTextChanged(CharSequence var1, int var2, int var3x, int var4) {
                    }
                });
                View var51 = (View)var22;
                Object var31 = new LayoutParams(-1, -2);
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

                for (JSONObject var79 : (Iterable<JSONObject>) var33) {
                    LinearLayout var75 = this.inner((ViewGroup)var7);
                    CharSequence var53 = this.s(var79, "bucket");
                    var22 = var53;
                    if (StringsKt.isBlank(var53)) {
                        var22 = "finding";
                    }

                    this.text(StringsKt.replace((String)var22, "_", " ", false), (ViewGroup)var75, 18.0F, CarUi.Companion.getTEXT(), true);
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

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderReport$lambda$88(GxTuningPage this$0, JSONObject $rep) {
        this$0.deleteReport($rep);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderReport$lambda$92(GxTuningPage this$0, String $key) {
        this$0.selectPath($key);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderReport$lambda$95(GxTuningPage this$0) {
        this$0.saveFeedback();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderReport$lambda$101(GxTuningPage this$0, String $dim) {
        this$0.toggleFeedback($dim, "accepted");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderReport$lambda$102(GxTuningPage this$0, String $dim) {
        this$0.toggleFeedback($dim, "ignored");
        return Unit.INSTANCE;
    }

    private final void paintProfiles() {
        LinearLayout linearLayout;
        LinearLayout linearLayout2;
        LinearLayout linearLayout3;
        if (this.flmBuilt) {
            LinearLayout linearLayout4 = this.profilesBox;
            if (linearLayout4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("profilesBox");
                linearLayout4 = null;
            }
            linearLayout4.removeAllViews();
            JSONObject rep = this.report;
            if (this.reportLoading) {
                GxTuningPage gxTuningPage = this;
                LinearLayout linearLayout5 = this.profilesBox;
                if (linearLayout5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("profilesBox");
                    linearLayout3 = null;
                } else {
                    linearLayout3 = linearLayout5;
                }
                GxPage.text$default(gxTuningPage, "Loading report profiles...", linearLayout3, 0.0f, 0, false, 28, null);
                return;
            }
            if (rep == null) {
                LinearLayout linearLayout6 = this.profilesBox;
                if (linearLayout6 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("profilesBox");
                    linearLayout2 = null;
                } else {
                    linearLayout2 = linearLayout6;
                }
                linearLayout2.addView(empty("Open a report to view and apply its trial profiles."));
                return;
            }
            List list = profiles();
            if (list.isEmpty()) {
                LinearLayout linearLayout7 = this.profilesBox;
                if (linearLayout7 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("profilesBox");
                    linearLayout = null;
                } else {
                    linearLayout = linearLayout7;
                }
                linearLayout.addView(empty("No trial profiles for this report."));
                return;
            }
            for (final JSONObject p : (Iterable<JSONObject>) list) {
                LinearLayout linearLayout8 = this.profilesBox;
                if (linearLayout8 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("profilesBox");
                    linearLayout8 = null;
                }
                LinearLayout ic = inner(linearLayout8);
                LinearLayout h = headLine$default(this, ic, s(p, "label"), 0, 4, null);
                en(headBtn(h, "Apply Trial", true, new Function0() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda7
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Unit paintProfiles$lambda$104;
                        paintProfiles$lambda$104 = GxTuningPage.paintProfiles$lambda$104(GxTuningPage.this, p);
                        return paintProfiles$lambda$104;
                    }
                }), canApplyTrial());
                if (!StringsKt.isBlank(s(p, "description"))) {
                    GxPage.text$default(this, s(p, "description"), ic, 16.0f, 0, false, 24, null);
                }
                Iterable $this$filter$iv = entries(obj(p, "genericParams"));
                Collection destination$iv$iv = new ArrayList();
                for (Object element$iv$iv : $this$filter$iv) {
                    Pair it = (Pair) element$iv$iv;
                    LinearLayout h2 = h;
                    if (!Intrinsics.areEqual(it.getFirst(), "AdvancedLateralTune")) {
                        destination$iv$iv.add(element$iv$iv);
                    }
                    h = h2;
                }
                List<Pair> gen = (List) destination$iv$iv;
                JSONObject ov = obj(p, "flmOverrides");
                List fr = entries(obj(ov, "baseFrictionThresholds"));
                List kn = entries(obj(ov, "vehicleKnobs"));
                if (gen.isEmpty() && fr.isEmpty() && kn.isEmpty()) {
                    GxPage.text$default(this, "No parameter overrides in this profile.", ic, 0.0f, 0, false, 28, null);
                } else {
                    for (Pair pair : gen) {
                        String k = (String) pair.component1();
                        Object v = pair.component2();
                        mono(ic, k + ": " + fmtVal(v));
                        gen = gen;
                        ov = ov;
                    }
                    for (Pair<String, Object> pair2 : ((Pair<String,Object>) fr)) {
                        String fam = pair2.component1();
                        Object pl = pair2.component2();
                        LinearLayout linearLayout9 = ic;
                        JSONObject jSONObject = pl instanceof JSONObject ? (JSONObject) pl : null;
                        mono(linearLayout9, fam + ": " + curveStr(jSONObject != null ? jSONObject.optJSONArray("values") : null));
                    }
                    for (Pair<String, Object> pair3 : ((Pair<String,Object>) kn)) {
                        String sym = pair3.component1();
                        Object v2 = pair3.component2();
                        mono(ic, sym + ": " + fmtVal(v2));
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintProfiles$lambda$104(GxTuningPage this$0, JSONObject $p) {
        this$0.applyTrial(this$0.s($p, "id"));
        return Unit.INSTANCE;
    }

    private final void applyTrial(String profileId) {
        JSONObject rep = this.report;
        if (rep == null) {
            return;
        }
        String id = s(rep, "reportId");
        if (StringsKt.isBlank(id) || !canApplyTrial()) {
            return;
        }
        runWith$default(this, "POST", "/api/flm/trials/apply", new JSONObject().put("reportId", id).put("profileId", profileId), "Trial profile applied.", null, 16, null);
    }

    private final void selectPath(String key) {
        JSONObject rep = this.report;
        if (rep == null) {
            return;
        }
        String id = s(rep, "reportId");
        if (StringsKt.isBlank(id) || Intrinsics.areEqual(key, activePathKey())) {
            return;
        }
        runWith("POST", "/api/flm/report/" + enc(id) + "/path", new JSONObject().put("pathKey", key), "Path selected.", new Function1() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda38
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit selectPath$lambda$107;
                selectPath$lambda$107 = GxTuningPage.selectPath$lambda$107(GxTuningPage.this, (JSONObject) obj);
                return selectPath$lambda$107;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit selectPath$lambda$107(GxTuningPage this$0, JSONObject o) {
        JSONObject r;
        if (o != null && (r = o.optJSONObject("report")) != null) {
            this$0.report = r;
            this$0.syncFeedback(r);
            this$0.renderReport();
            this$0.paintProfiles();
        }
        return Unit.INSTANCE;
    }

    private final void deleteReport(JSONObject rep) {
        final String id = s(rep, "reportId");
        if (StringsKt.isBlank(id)) {
            return;
        }
        confirm("Delete Report", "Delete this saved tuning report and its generated trial data?", "Yes, Delete", new Function0() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda14
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit deleteReport$lambda$109;
                deleteReport$lambda$109 = GxTuningPage.deleteReport$lambda$109(GxTuningPage.this, id);
                return deleteReport$lambda$109;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit deleteReport$lambda$109(final GxTuningPage this$0, final String $id) {
        this$0.runWith("DELETE", "/api/flm/report/" + this$0.enc($id), null, "Report deleted.", new Function1() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit deleteReport$lambda$109$lambda$108;
                deleteReport$lambda$109$lambda$108 = GxTuningPage.deleteReport$lambda$109$lambda$108(this$0, $id, (JSONObject) obj);
                return deleteReport$lambda$109$lambda$108;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit deleteReport$lambda$109$lambda$108(GxTuningPage this$0, String $id, JSONObject o) {
        if (o != null && Intrinsics.areEqual(this$0.loadedReportId, $id)) {
            this$0.report = null;
            this$0.loadedReportId = "";
            this$0.renderReport();
            this$0.paintFlm();
            this$0.autoOpenReport();
        }
        return Unit.INSTANCE;
    }

    private final void toggleFeedback(String dim, String mode) {
        String cur = this.fbAccepted.contains(dim) ? "accepted" : this.fbIgnored.contains(dim) ? "ignored" : "unset";
        String next = Intrinsics.areEqual(cur, mode) ? "unset" : mode;
        this.fbAccepted.remove(dim);
        this.fbIgnored.remove(dim);
        if (Intrinsics.areEqual(next, "accepted")) {
            this.fbAccepted.add(dim);
        }
        if (Intrinsics.areEqual(next, "ignored")) {
            this.fbIgnored.add(dim);
        }
        renderReport();
        saveFeedback();
    }

    private final void saveFeedback() {
        JSONObject rep = this.report;
        if (rep == null) {
            return;
        }
        String id = s(rep, "reportId");
        if (StringsKt.isBlank(id) || this.busy) {
            return;
        }
        this.busy = true;
        paintActions();
        JSONObject body = new JSONObject().put("reportId", id).put("acceptedDimensions", new JSONArray((Collection) CollectionsKt.toList(this.fbAccepted))).put("ignoredDimensions", new JSONArray((Collection) CollectionsKt.toList(this.fbIgnored))).put("notes", this.fbNotes);
        send("POST", "/api/flm/feedback", body, 60L, new Function2() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda52
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit saveFeedback$lambda$114;
                saveFeedback$lambda$114 = GxTuningPage.saveFeedback$lambda$114(GxTuningPage.this, (String) obj, (JSONObject) obj2);
                return saveFeedback$lambda$114;
            }
        });
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public static final Unit saveFeedback$lambda$114(GxTuningPage var0, String var1, JSONObject var2) {
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

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit saveFeedback$lambda$114$lambda$113(GxTuningPage this$0) {
        this$0.busy = false;
        this$0.paintFlm();
        return Unit.INSTANCE;
    }

    private final void analyze() {
        if (!canAnalyze() || this.busy) {
            return;
        }
        JSONObject ranges = new JSONObject();
        Iterator<String> it = this.selectedRoutes.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (it.hasNext()) {
            String next = it.next();
            Intrinsics.checkNotNullExpressionValue(next, "next(...)");
            String r = next;
            Pair<String, String> pair = this.segmentRanges.get(r);
            if (pair == null) {
                pair = TuplesKt.to("", "");
            }
            String st = pair.component1();
            String en = pair.component2();
            if (!StringsKt.isBlank(st) || !StringsKt.isBlank(en)) {
                ranges.put(r, new JSONObject().put("start", StringsKt.isBlank(st) ? JSONObject.NULL : st).put("end", StringsKt.isBlank(en) ? JSONObject.NULL : en));
            }
        }
        JSONObject body = new JSONObject().put("routes", new JSONArray((Collection) CollectionsKt.toList(this.selectedRoutes))).put("segmentRanges", ranges);
        runWith("POST", "/api/flm/analyze", body, "FLM analysis started.", new Function1() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit analyze$lambda$115;
                analyze$lambda$115 = GxTuningPage.analyze$lambda$115(GxTuningPage.this, (JSONObject) obj);
                return analyze$lambda$115;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit analyze$lambda$115(GxTuningPage this$0, JSONObject o) {
        if (o != null && !this$0.selectedRoutes.isEmpty()) {
            this$0.selectedRoutes.clear();
            this$0.segmentRanges.clear();
            this$0.renderRoutesAll();
        }
        return Unit.INSTANCE;
    }

    private final void loadRoutes() {
        if (this.loadingRoutes) {
            return;
        }
        this.loadingRoutes = true;
        this.routes.clear();
        renderRoutesAll();
        call("GET", "/api/routes", null, 300L, new Function3() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda25
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                Unit loadRoutes$lambda$122;
                loadRoutes$lambda$122 = GxTuningPage.loadRoutes$lambda$122(GxTuningPage.this, ((Integer) obj).intValue(), (String) obj2, (String) obj3);
                return loadRoutes$lambda$122;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadRoutes$lambda$122(final GxTuningPage gxTuningPage, int i, String text, String str) {
        Object obj;
        Intrinsics.checkNotNullParameter(text, "text");
        gxTuningPage.loadingRoutes = false;
        HashSet hashSet = new HashSet();
        Iterator<String> it = new Regex("\\r?\\n\\r?\\n").split(text, 0).iterator();
        while (it.hasNext()) {
            List<String> split = new Regex("\\r?\\n").split(it.next(), 0);
            ArrayList arrayList = new ArrayList();
            Iterator it2 = split.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                Object next = it2.next();
                List<String> list = split;
                if (StringsKt.startsWith((String) next, "data:", false)) {
                    arrayList.add(next);
                }
                split = list;
            }
            ArrayList arrayList2 = arrayList;
            if (!arrayList2.isEmpty()) {
                try {
                    Result.Companion companion = Result.Companion;
                    obj = KtCompat.resultOf(new JSONObject(CollectionsKt.joinToString(arrayList2, "\n", "", "", -1, "...", new Function1() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda68
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj2) {
                            CharSequence loadRoutes$lambda$122$lambda$118$lambda$117;
                            loadRoutes$lambda$122$lambda$118$lambda$117 = GxTuningPage.loadRoutes$lambda$122$lambda$118$lambda$117((String) obj2);
                            return loadRoutes$lambda$122$lambda$118$lambda$117;
                        }
                    })));
                } catch (Throwable th) {
                    Result.Companion companion2 = Result.Companion;
                    obj = KtCompat.resultOf(ResultKt.createFailure(th));
                }
                JSONObject jSONObject = (JSONObject) (KtCompat.isFailure(obj) ? null : obj);
                if (jSONObject != null) {
                    for (JSONObject jSONObject2 : gxTuningPage.objects(gxTuningPage.arr(jSONObject, "routes"))) {
                        String s = gxTuningPage.s(jSONObject2, "name");
                        if (!StringsKt.isBlank(s) && hashSet.add(s) && gxTuningPage.routes.size() < gxTuningPage.maxRoutes) {
                            gxTuningPage.routes.add(jSONObject2);
                        }
                    }
                }
            }
        }
        ArrayList<JSONObject> arrayList3 = gxTuningPage.routes;
        if (arrayList3.size() > 1) {
            CollectionsKt.sortWith(arrayList3, new Comparator() { // from class: com.starpilot.starview.GxTuningPage$loadRoutes$lambda$122$$inlined$sortByDescending$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.util.Comparator
                public final int compare(Object t, Object t2) {
                    String s2;
                    String s3;
                    JSONObject it3 = (JSONObject) t2;
                    s2 = gxTuningPage.s(it3, "startedAt");
                    JSONObject it4 = (JSONObject) t;
                    s3 = gxTuningPage.s(it4, "startedAt");
                    return ComparisonsKt.compareValues(s2, s3);
                }
            });
        }
        if (str != null) {
            String str2 = str;
            if (StringsKt.isBlank(str2)) {
                str2 = "Failed to load routes.";
            }
            gxTuningPage.toast(str2);
        }
        LinkedHashSet<String> linkedHashSet = gxTuningPage.selectedRoutes;
        ArrayList<JSONObject> arrayList4 = gxTuningPage.routes;
        ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList4, 10));
        Iterator it3 = arrayList4.iterator();
        while (it3.hasNext()) {
            arrayList5.add(gxTuningPage.s((JSONObject) it3.next(), "name"));
        }
        linkedHashSet.retainAll(CollectionsKt.toSet(arrayList5));
        gxTuningPage.renderRoutesAll();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence loadRoutes$lambda$122$lambda$118$lambda$117(String it) {
        Intrinsics.checkNotNullParameter(it, "it");
        String substring = it.substring(5);
        Intrinsics.checkNotNullExpressionValue(substring, "substring(...)");
        return StringsKt.trimStart((CharSequence) substring).toString();
    }

    /* compiled from: GxTuning.kt */
    private static final /* data */ class Bounds {
        private final int count;
        private final int first;
        private final int last;

        public static /* synthetic */ Bounds copy$default(Bounds bounds, int i, int i2, int i3, int i4, Object obj) {
            if ((i4 & 1) != 0) {
                i = bounds.first;
            }
            if ((i4 & 2) != 0) {
                i2 = bounds.last;
            }
            if ((i4 & 4) != 0) {
                i3 = bounds.count;
            }
            return bounds.copy(i, i2, i3);
        }



        public final Bounds copy(int first, int last, int count) {
            return new Bounds(first, last, count);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Bounds)) {
                return false;
            }
            Bounds bounds = (Bounds) other;
            return this.first == bounds.first && this.last == bounds.last && this.count == bounds.count;
        }

        public int hashCode() {
            return (((Integer.hashCode(this.first) * 31) + Integer.hashCode(this.last)) * 31) + Integer.hashCode(this.count);
        }

        public String toString() {
            return "Bounds(first=" + this.first + ", last=" + this.last + ", count=" + this.count + ")";
        }

        public Bounds(int first, int last, int count) {
            this.first = first;
            this.last = last;
            this.count = count;
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
    }

    private final Bounds bounds(String name) {
        Object element$iv;
        Iterable $this$firstOrNull$iv = this.routes;
        Iterator it = $this$firstOrNull$iv.iterator();
        while (true) {
            if (it.hasNext()) {
                element$iv = it.next();
                JSONObject it2 = (JSONObject) element$iv;
                if (Intrinsics.areEqual(s(it2, "name"), name)) {
                    break;
                }
            } else {
                element$iv = null;
                break;
            }
        }
        JSONObject r = (JSONObject) element$iv;
        int count = Math.max(0, (int) Math.round(num$default(this, r != null ? r.opt("segmentCount") : null, 0.0d, 2, null)));
        int first = Math.max(0, (int) Math.round(num$default(this, r != null ? r.opt("firstSegmentNum") : null, 0.0d, 2, null)));
        return new Bounds(first, Math.max(0, count - 1) + first, count);
    }

    private final int routeSelectedCount(String name) {
        Bounds b = bounds(name);
        if (b.getCount() == 0) {
            return 0;
        }
        Pair<String, String> pair = this.segmentRanges.get(name);
        if (pair == null) {
            pair = TuplesKt.to("", "");
        }
        String rs = pair.component1();
        String re = pair.component2();
        int start = (int) Math.min(b.getLast(), Math.max(b.getFirst(), StringsKt.isBlank(rs) ? b.getFirst() : num(rs, b.getFirst())));
        int end = (int) Math.max(b.getFirst(), Math.min(b.getLast(), StringsKt.isBlank(re) ? b.getLast() : num(re, b.getLast())));
        if (end >= start) {
            return (end - start) + 1;
        }
        return 0;
    }

    private final int selectedSegmentCount() {
        int i = 0;
        for (String it : this.selectedRoutes) {
            i += routeSelectedCount(it);
        }
        return i;
    }

    private final String fmtLen(JSONObject r) {
        StringBuilder append;
        String str;
        long seg = Math.max(0L, Math.round(num$default(this, r.opt("segmentCount"), 0.0d, 2, null)));
        if (seg == 0) {
            return "Length unavailable";
        }
        long m0 = Math.round(num$default(this, r.opt("approxDurationSeconds"), 0.0d, 2, null) / 60.0d);
        long min = Math.max(1L, m0 != 0 ? m0 : seg);
        if (min >= 60) {
            long j = 60;
            append = new StringBuilder().append(min / j).append("h ").append(min % j);
            str = "m";
        } else {
            append = new StringBuilder().append("~").append(min);
            str = " min";
        }
        String dur = append.append(str).toString();
        return seg + " segment" + (seg == 1 ? "" : "s") + " (" + dur + ")";
    }

    private final void toggleRoute(String name) {
        if (this.selectedRoutes.contains(name)) {
            this.selectedRoutes.remove(name);
            this.segmentRanges.remove(name);
        } else {
            int remaining = this.maxSegments - selectedSegmentCount();
            if (remaining <= 0) {
                toast("FLM is limited to " + this.maxSegments + " segments at a time.");
                renderRoute(name);
                return;
            } else {
                Bounds b = bounds(name);
                if (b.getCount() == 0) {
                    renderRoute(name);
                    return;
                } else {
                    this.selectedRoutes.add(name);
                    if (b.getCount() > remaining) {
                        this.segmentRanges.put(name, TuplesKt.to(String.valueOf(b.getFirst()), String.valueOf((b.getFirst() + remaining) - 1)));
                    }
                }
            }
        }
        renderRoute(name);
        afterSelectionChange();
    }

    private final void selectFirstSegments() {
        this.selectedRoutes.clear();
        this.segmentRanges.clear();
        int remaining = this.maxSegments;
        Iterator<JSONObject> it = this.routes.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (it.hasNext()) {
            JSONObject next = it.next();
            Intrinsics.checkNotNullExpressionValue(next, "next(...)");
            JSONObject r = next;
            if (remaining <= 0) {
                break;
            }
            String name = s(r, "name");
            Bounds b = bounds(name);
            if (b.getCount() != 0) {
                int take = Math.min(b.getCount(), remaining);
                this.selectedRoutes.add(name);
                if (take < b.getCount()) {
                    this.segmentRanges.put(name, TuplesKt.to(String.valueOf(b.getFirst()), String.valueOf((b.getFirst() + take) - 1)));
                }
                remaining -= take;
            }
        }
        renderRoutesAll();
    }

    private final void afterSelectionChange() {
        boolean full = selectedSegmentCount() >= this.maxSegments;
        for (Map.Entry<String, Switch> entry : this.routeSwitches.entrySet()) {
            String n = entry.getKey();
            Switch sw = entry.getValue();
            boolean on = this.selectedRoutes.contains(n) || !full;
            sw.setEnabled(on);
            sw.setAlpha(on ? 1.0f : 0.4f);
        }
        paintRoutesHead();
        paintActions();
    }

    private final void paintRoutesHead() {
        if (this.flmBuilt) {
            LinearLayout linearLayout = this.routesHead;
            LinearLayout linearLayout2 = null;
            if (linearLayout == null) {
                Intrinsics.throwUninitializedPropertyAccessException("routesHead");
                linearLayout = null;
            }
            linearLayout.removeAllViews();
            if (this.loadingRoutes) {
                LinearLayout linearLayout3 = this.routesHead;
                if (linearLayout3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("routesHead");
                } else {
                    linearLayout2 = linearLayout3;
                }
                linearLayout2.addView(empty("Loading local routes..."));
                return;
            }
            if (this.routes.isEmpty()) {
                LinearLayout linearLayout4 = this.routesHead;
                if (linearLayout4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("routesHead");
                    linearLayout4 = null;
                }
                linearLayout4.addView(empty("No local routes found."));
                GxTuningPage gxTuningPage = this;
                Pair[] pairArr = {TuplesKt.to("Reload routes", new Function0() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda71
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Unit paintRoutesHead$lambda$125;
                        paintRoutesHead$lambda$125 = GxTuningPage.paintRoutesHead$lambda$125(GxTuningPage.this);
                        return paintRoutesHead$lambda$125;
                    }
                })};
                LinearLayout linearLayout5 = this.routesHead;
                if (linearLayout5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("routesHead");
                } else {
                    linearLayout2 = linearLayout5;
                }
                GxPage.buttons$default(gxTuningPage, pairArr, linearLayout2, false, 4, null);
                return;
            }
            int n = selectedSegmentCount();
            GxTuningPage gxTuningPage2 = this;
            String str = n + "/" + this.maxSegments + " segments selected";
            LinearLayout linearLayout6 = this.routesHead;
            if (linearLayout6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("routesHead");
                linearLayout6 = null;
            }
            GxPage.Row r = GxPage.row$default(gxTuningPage2, str, null, linearLayout6, 0, 8, null);
            if (n > this.maxSegments) {
                r.getTitleView().setTextColor(Color.rgb(240, 110, 130));
            }
            GxTuningPage gxTuningPage3 = this;
            Pair[] pairArr2 = {TuplesKt.to("Select first " + this.maxSegments, new Function0() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda72
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit paintRoutesHead$lambda$126;
                    paintRoutesHead$lambda$126 = GxTuningPage.paintRoutesHead$lambda$126(GxTuningPage.this);
                    return paintRoutesHead$lambda$126;
                }
            }), TuplesKt.to("Clear", new Function0() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit paintRoutesHead$lambda$127;
                    paintRoutesHead$lambda$127 = GxTuningPage.paintRoutesHead$lambda$127(GxTuningPage.this);
                    return paintRoutesHead$lambda$127;
                }
            }), TuplesKt.to("Reload routes", new Function0() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit paintRoutesHead$lambda$128;
                    paintRoutesHead$lambda$128 = GxTuningPage.paintRoutesHead$lambda$128(GxTuningPage.this);
                    return paintRoutesHead$lambda$128;
                }
            })};
            LinearLayout linearLayout7 = this.routesHead;
            if (linearLayout7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("routesHead");
            } else {
                linearLayout2 = linearLayout7;
            }
            GxPage.buttons$default(gxTuningPage3, pairArr2, linearLayout2, false, 4, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintRoutesHead$lambda$125(GxTuningPage this$0) {
        this$0.loadRoutes();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintRoutesHead$lambda$126(GxTuningPage this$0) {
        this$0.selectFirstSegments();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintRoutesHead$lambda$127(GxTuningPage this$0) {
        this$0.selectedRoutes.clear();
        this$0.segmentRanges.clear();
        this$0.renderRoutesAll();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintRoutesHead$lambda$128(GxTuningPage this$0) {
        this$0.loadRoutes();
        return Unit.INSTANCE;
    }

    private final void renderRoutesAll() {
        if (this.flmBuilt) {
            LinearLayout linearLayout = this.routesBox;
            if (linearLayout == null) {
                Intrinsics.throwUninitializedPropertyAccessException("routesBox");
                linearLayout = null;
            }
            linearLayout.removeAllViews();
            this.routeBoxes.clear();
            this.routeSwitches.clear();
            if (!this.loadingRoutes) {
                Iterator<JSONObject> it = this.routes.iterator();
                Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
                while (it.hasNext()) {
                    JSONObject next = it.next();
                    Intrinsics.checkNotNullExpressionValue(next, "next(...)");
                    JSONObject r = next;
                    String name = s(r, "name");
                    HashMap<String, LinearLayout> hashMap = this.routeBoxes;
                    LinearLayout linearLayout2 = this.routesBox;
                    if (linearLayout2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("routesBox");
                        linearLayout2 = null;
                    }
                    hashMap.put(name, box(linearLayout2));
                    renderRoute(name);
                }
            }
            afterSelectionChange();
        }
    }

    private final void renderRoute(final String name) {
        Object element$iv;
        LinearLayout b = this.routeBoxes.get(name);
        if (b == null) {
            return;
        }
        Iterable $this$firstOrNull$iv = this.routes;
        Iterator it = $this$firstOrNull$iv.iterator();
        while (true) {
            if (it.hasNext()) {
                element$iv = it.next();
                JSONObject it2 = (JSONObject) element$iv;
                if (Intrinsics.areEqual(s(it2, "name"), name)) {
                    break;
                }
            } else {
                element$iv = null;
                break;
            }
        }
        JSONObject r = (JSONObject) element$iv;
        if (r == null) {
            return;
        }
        b.removeAllViews();
        boolean sel = this.selectedRoutes.contains(name);
        GxPage.Row row = GxPage.row$default(this, fmtDate(r.opt("timestamp")), name + "\n" + fmtLen(r) + " · " + routeSelectedCount(name) + " selected", b, 0, 8, null);
        if (sel) {
            row.getView().setBackground(getUi().rounded(CarUi.Companion.getCARD_ON(), 14, Integer.valueOf(CarUi.Companion.getACCENT())));
        }
        Switch sw = row.m806switch(sel, new Function1() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda16
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit renderRoute$lambda$130;
                renderRoute$lambda$130 = GxTuningPage.renderRoute$lambda$130(GxTuningPage.this, name, ((Boolean) obj).booleanValue());
                return renderRoute$lambda$130;
            }
        });
        this.routeSwitches.put(name, sw);
        boolean full = selectedSegmentCount() >= this.maxSegments;
        sw.setEnabled(sel || !full);
        sw.setAlpha(sw.isEnabled() ? 1.0f : 0.4f);
        if (sel) {
            Bounds bd = bounds(name);
            Pair<String, String> pair = this.segmentRanges.get(name);
            if (pair == null) {
                pair = TuplesKt.to("", "");
            }
            String st = pair.component1();
            String en = pair.component2();
            String str = "–";
            GxPage.Row sr = row("Segments", "Segments " + bd.getFirst() + "–" + bd.getLast() + ". Blank uses the whole route.", b, 1);
            String str2 = st;
            if (StringsKt.isBlank(str2)) {
                str2 = "–";
            }
            GxPage.Row.button$default(sr, "First: " + ((Object) str2), false, new Function0() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda17
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit renderRoute$lambda$132;
                    renderRoute$lambda$132 = GxTuningPage.renderRoute$lambda$132(GxTuningPage.this, name);
                    return renderRoute$lambda$132;
                }
            }, 2, null);
            String str3 = en;
            if (!StringsKt.isBlank(str3)) {
                str = str3;
            }
            GxPage.Row.button$default(sr, "Last: " + ((Object) str), false, new Function0() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda18
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit renderRoute$lambda$134;
                    renderRoute$lambda$134 = GxTuningPage.renderRoute$lambda$134(GxTuningPage.this, name);
                    return renderRoute$lambda$134;
                }
            }, 2, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderRoute$lambda$130(GxTuningPage this$0, String $name, boolean it) {
        this$0.toggleRoute($name);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderRoute$lambda$132(GxTuningPage this$0, String $name) {
        this$0.editRange($name, true);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderRoute$lambda$134(GxTuningPage this$0, String $name) {
        this$0.editRange($name, false);
        return Unit.INSTANCE;
    }

    private final void editRange(final String name, final boolean start) {
        Pair<String, String> pair = this.segmentRanges.get(name);
        if (pair == null) {
            pair = TuplesKt.to("", "");
        }
        final String st = pair.component1();
        final String en = pair.component2();
        prompt(start ? "First segment" : "Last segment", start ? "First" : "Last", start ? st : en, 2, "Leave blank to use the whole route.", new Function1() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda57
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit editRange$lambda$135;
                editRange$lambda$135 = GxTuningPage.editRange$lambda$135(GxTuningPage.this, name, start, en, st, (String) obj);
                return editRange$lambda$135;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit editRange$lambda$135(GxTuningPage this$0, String $name, boolean $start, String $en, String $st, String raw) {
        Intrinsics.checkNotNullParameter(raw, "raw");
        String cleaned = new Regex("[^\\d]").replace(raw, "");
        this$0.segmentRanges.put($name, $start ? TuplesKt.to(cleaned, $en) : TuplesKt.to($st, cleaned));
        this$0.renderRoute($name);
        this$0.afterSelectionChange();
        return Unit.INSTANCE;
    }

    /* compiled from: GxTuning.kt */
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

        public ChartCfg(String id, boolean advanced, String title, String unit, int legendDigits, int rangeDigits, double rangeFloor, double rangeMult, double roundDiv, String sourceKind, String sourceKey, List<Triple<String, String, Integer>> series) {
            Intrinsics.checkNotNullParameter(id, "id");
            Intrinsics.checkNotNullParameter(title, "title");
            Intrinsics.checkNotNullParameter(unit, "unit");
            Intrinsics.checkNotNullParameter(sourceKind, "sourceKind");
            Intrinsics.checkNotNullParameter(sourceKey, "sourceKey");
            Intrinsics.checkNotNullParameter(series, "series");
            this.id = id;
            this.advanced = advanced;
            this.title = title;
            this.unit = unit;
            this.legendDigits = legendDigits;
            this.rangeDigits = rangeDigits;
            this.rangeFloor = rangeFloor;
            this.rangeMult = rangeMult;
            this.roundDiv = roundDiv;
            this.sourceKind = sourceKind;
            this.sourceKey = sourceKey;
            this.series = series;
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

        public final String getTitle() {
            return this.title;
        }

        public final String getUnit() {
            return this.unit;
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

        public final String getSourceKey() {
            return this.sourceKey;
        }

        public final String getSourceKind() {
            return this.sourceKind;
        }

        public final List<Triple<String, String, Integer>> getSeries() {
            return this.series;
        }
    }

    /* compiled from: GxTuning.kt */
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

        public QualityCfg(String desiredKey, String actualKey, double minSpeed, double minDemand, boolean allowFallback, double fallbackMinSpeed, double fallbackMinPeak, boolean persistence, double warnError, double severeError, double great, double good, double fair) {
            Intrinsics.checkNotNullParameter(desiredKey, "desiredKey");
            Intrinsics.checkNotNullParameter(actualKey, "actualKey");
            this.desiredKey = desiredKey;
            this.actualKey = actualKey;
            this.minSpeed = minSpeed;
            this.minDemand = minDemand;
            this.allowFallback = allowFallback;
            this.fallbackMinSpeed = fallbackMinSpeed;
            this.fallbackMinPeak = fallbackMinPeak;
            this.persistence = persistence;
            this.warnError = warnError;
            this.severeError = severeError;
            this.great = great;
            this.good = good;
            this.fair = fair;
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

        public final double getMinDemand() {
            return this.minDemand;
        }

        public final double getMinSpeed() {
            return this.minSpeed;
        }

        public final double getFallbackMinPeak() {
            return this.fallbackMinPeak;
        }

        public final double getFallbackMinSpeed() {
            return this.fallbackMinSpeed;
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

        public final double getFair() {
            return this.fair;
        }

        public final double getGood() {
            return this.good;
        }

        public final double getGreat() {
            return this.great;
        }
    }

    /* compiled from: GxTuning.kt */
    private static final class Quality {
        private final String detail;
        private final String label;
        private final Double value;

        public Quality(String label, Double value, String detail) {
            Intrinsics.checkNotNullParameter(label, "label");
            Intrinsics.checkNotNullParameter(detail, "detail");
            this.label = label;
            this.value = value;
            this.detail = detail;
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

    private final SharedPreferences getPrefs() {
        return (SharedPreferences) this.prefs.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final SharedPreferences prefs_delegate$lambda$136(Activity $act) {
        return $act.getSharedPreferences("gx_tuning", 0);
    }

    /* compiled from: GxTuning.kt */
    private static final class ChartViews {
        private final ChartCfg cfg;
        private final GxTuningChartView chart;
        private final LinearLayout legend;
        private final TextView source;
        private final TextView waiting;

        public ChartViews(ChartCfg cfg, TextView source, LinearLayout legend, GxTuningChartView chart, TextView waiting) {
            Intrinsics.checkNotNullParameter(cfg, "cfg");
            Intrinsics.checkNotNullParameter(source, "source");
            Intrinsics.checkNotNullParameter(legend, "legend");
            Intrinsics.checkNotNullParameter(chart, "chart");
            Intrinsics.checkNotNullParameter(waiting, "waiting");
            this.cfg = cfg;
            this.source = source;
            this.legend = legend;
            this.chart = chart;
            this.waiting = waiting;
        }

        public final ChartCfg getCfg() {
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

    private final void buildPlots(LinearLayout b) {
        Object m1191constructorimpl;
        this.plotsBuilt = true;
        try {
            Result.Companion companion = Result.Companion;
            GxTuningPage $this$buildPlots_u24lambda_u24137 = this;
            m1191constructorimpl = KtCompat.resultOf(Boolean.valueOf($this$buildPlots_u24lambda_u24137.getPrefs().getBoolean("plotsShowAdvancedTerms", false)));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            m1191constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
        }
        if (KtCompat.isFailure(m1191constructorimpl)) {
            m1191constructorimpl = false;
        }
        this.showAdvanced = ((Boolean) m1191constructorimpl).booleanValue();
        LinearLayout c = card(b);
        headLine$default(this, c, "Live tuning status", 0, 4, null);
        GxPage.text$default(this, "Live comparison view for tuning diagnostics. These scores are a quick health check, not a final verdict. Short spikes from bumps, lane changes, traffic transitions, and manual inputs can temporarily lower a score.", c, 0.0f, 0, false, 28, null);
        this.plotsStatus = box(c);
        GxPage.text$default(this, "Match rating uses a 30-second rolling window. Strong steering or accel moments are preferred, but gentler windows can still earn a rating. Longitudinal also checks how much of the window stays above error limits.", c, 14.0f, 0, false, 24, null);
        LinearLayout bar = btnBar(c);
        this.bPause = barBtn$default(this, bar, "Pause Live", false, new Function0() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda20
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildPlots$lambda$138;
                buildPlots$lambda$138 = GxTuningPage.buildPlots$lambda$138(GxTuningPage.this);
                return buildPlots$lambda$138;
            }
        }, 4, null);
        barBtn$default(this, bar, "Clear History", false, new Function0() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda21
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildPlots$lambda$139;
                buildPlots$lambda$139 = GxTuningPage.buildPlots$lambda$139(GxTuningPage.this);
                return buildPlots$lambda$139;
            }
        }, 4, null);
        barBtn$default(this, bar, "Refresh", false, new Function0() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda23
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildPlots$lambda$140;
                buildPlots$lambda$140 = GxTuningPage.buildPlots$lambda$140(GxTuningPage.this);
                return buildPlots$lambda$140;
            }
        }, 4, null);
        Iterable $this$filter$iv = this.charts;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            ChartCfg it = (ChartCfg) element$iv$iv;
            if (!it.getAdvanced()) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        for (ChartCfg cfg : (ChartCfg) destination$iv$iv) {
            chartCard(b, cfg);
        }
        this.bAdvanced = getUi().button("", false, new Function0() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda24
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildPlots$lambda$142;
                buildPlots$lambda$142 = GxTuningPage.buildPlots$lambda$142(GxTuningPage.this);
                return buildPlots$lambda$142;
            }
        });
        TextView textView = this.bAdvanced;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bAdvanced");
            textView = null;
        }
        LinearLayout.LayoutParams $this$buildPlots_u24lambda_u24143 = new LinearLayout.LayoutParams(-2, -2);
        $this$buildPlots_u24lambda_u24143.topMargin = getUi().dp(8);
        $this$buildPlots_u24lambda_u24143.bottomMargin = getUi().dp(10);
        Unit unit = Unit.INSTANCE;
        b.addView(textView, $this$buildPlots_u24lambda_u24143);
        this.advancedBox = box(b);
        Iterable $this$filter$iv2 = this.charts;
        Collection destination$iv$iv2 = new ArrayList();
        for (Object element$iv$iv2 : $this$filter$iv2) {
            ChartCfg it2 = (ChartCfg) element$iv$iv2;
            if (it2.getAdvanced()) {
                destination$iv$iv2.add(element$iv$iv2);
            }
        }
        for (ChartCfg cfg2 : (ChartCfg) destination$iv$iv2) {
            LinearLayout linearLayout = this.advancedBox;
            if (linearLayout == null) {
                Intrinsics.throwUninitializedPropertyAccessException("advancedBox");
                linearLayout = null;
            }
            chartCard(linearLayout, cfg2);
        }
        paintPlots();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildPlots$lambda$138(GxTuningPage this$0) {
        this$0.togglePaused();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildPlots$lambda$139(GxTuningPage this$0) {
        this$0.samples.clear();
        this$0.lastTs = 0.0d;
        this$0.paintPlots();
        this$0.toast("Plot history cleared.");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildPlots$lambda$140(GxTuningPage this$0) {
        this$0.plotsLoading = true;
        this$0.plotsError = "";
        this$0.paintPlots();
        this$0.plotsLoad(true);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildPlots$lambda$142(GxTuningPage this$0) {
        this$0.toggleAdvanced();
        return Unit.INSTANCE;
    }

    private final void chartCard(LinearLayout into, ChartCfg cfg) {
        LinearLayout c = card(into);
        headLine$default(this, c, cfg.getTitle(), 0, 4, null);
        TextView src = GxPage.text$default(this, "", c, 14.0f, 0, false, 24, null);
        LinearLayout $this$chartCard_u24lambda_u24145 = new LinearLayout(getAct());
        $this$chartCard_u24lambda_u24145.setOrientation(0);
        $this$chartCard_u24lambda_u24145.setPadding(0, getUi().dp(4), 0, getUi().dp(8));
        c.addView($this$chartCard_u24lambda_u24145);
        Activity act = getAct();
        Iterable $this$map$iv = cfg.getSeries();
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            Triple it = (Triple) item$iv$iv;
            destination$iv$iv.add(TuplesKt.to(it.getFirst(), it.getThird()));
        }
        GxTuningChartView chart = new GxTuningChartView(act, (List) destination$iv$iv, cfg.getUnit(), cfg.getRangeDigits(), cfg.getRangeFloor(), cfg.getRangeMult(), cfg.getRoundDiv(), this.samples);
        c.addView(chart, new LinearLayout.LayoutParams(-1, getUi().dp(220)));
        TextView waiting = empty("Waiting for enough live samples...");
        c.addView(waiting);
        this.chartViews.add(new ChartViews(cfg, src, $this$chartCard_u24lambda_u24145, chart, waiting));
    }

    private final void togglePaused() {
        this.plotsPaused = !this.plotsPaused;
        paintPlots();
        if (!this.plotsPaused) {
            plotsLoad(true);
        }
    }

    private final void toggleAdvanced() {
        this.showAdvanced = !this.showAdvanced;
        try {
            Result.Companion companion = Result.Companion;
            GxTuningPage $this$toggleAdvanced_u24lambda_u24147 = this;
            $this$toggleAdvanced_u24lambda_u24147.getPrefs().edit().putBoolean("plotsShowAdvancedTerms", $this$toggleAdvanced_u24lambda_u24147.showAdvanced).apply();
            KtCompat.resultOf(Unit.INSTANCE);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            KtCompat.resultOf(ResultKt.createFailure(th));
        }
        paintPlots();
    }

    static /* synthetic */ void plotsLoad$default(GxTuningPage gxTuningPage, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        gxTuningPage.plotsLoad(z);
    }

    private final void plotsLoad(boolean force) {
        if (this.plotsBuilt) {
            if (this.plotsFetching && !force) {
                return;
            }
            this.plotsFetching = true;
            getJson("/api/plots/live", new Function2() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda46
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Unit plotsLoad$lambda$148;
                    plotsLoad$lambda$148 = GxTuningPage.plotsLoad$lambda$148(GxTuningPage.this, (String) obj, (JSONObject) obj2);
                    return plotsLoad$lambda$148;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit plotsLoad$lambda$148(GxTuningPage this$0, String e, JSONObject o) {
        this$0.plotsFetching = false;
        if (o != null) {
            this$0.live = o;
            this$0.plotsError = "";
            if (!this$0.truthy(o.opt("stale"))) {
                this$0.pushSample(o);
            }
        } else {
            this$0.plotsError = e == null ? "Failed to load live plot data" : e;
        }
        this$0.plotsLoading = false;
        this$0.paintPlots();
        return Unit.INSTANCE;
    }

    private final void pushSample(JSONObject p) {
        double ts = num$default(this, p.opt("timestamp"), 0.0d, 2, null);
        if (ts > 0.0d) {
            if (ts == this.lastTs) {
                return;
            }
            this.lastTs = ts;
            HashMap m = new HashMap();
            for (String k : this.sampleKeys) {
                m.put(k, Double.valueOf(num$default(this, p.opt(k), 0.0d, 2, null)));
            }
            this.samples.add(new GxTuningSample(ts, m));
            if (this.samples.size() > 240) {
                this.samples.subList(0, this.samples.size() - 240).clear();
            }
        }
    }

    private final String sourceLabel(String kind, String source) {
        String k = kind.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(k, "toLowerCase(...)");
        String src = source.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(src, "toLowerCase(...)");
        if (Intrinsics.areEqual(k, "lateral")) {
            if (Intrinsics.areEqual(src, "torquestate")) {
                return "Steering controller output";
            }
            if (Intrinsics.areEqual(src, "curvature")) {
                return "Path model estimate";
            }
        }
        if (Intrinsics.areEqual(k, "longitudinal")) {
            if (StringsKt.contains((CharSequence) src, (CharSequence) "atarget", false)) {
                return "Planner target acceleration + measured acceleration";
            }
            if (StringsKt.contains((CharSequence) src, (CharSequence) "pid sum", false)) {
                return "PID term sum + measured acceleration";
            }
            if (StringsKt.contains((CharSequence) src, (CharSequence) "caroutput", false)) {
                return "Final accel command + measured acceleration";
            }
            if (StringsKt.contains((CharSequence) src, (CharSequence) "livelocationkalman", false)) {
                return "Control output + calibrated acceleration";
            }
            if (Intrinsics.areEqual(src, "controlsstate")) {
                return "Planner target output";
            }
        }
        if (Intrinsics.areEqual(k, "lateralterms")) {
            if (Intrinsics.areEqual(src, "torquestate")) {
                return "Steering torque controller";
            }
            if (Intrinsics.areEqual(src, "pidstate")) {
                return "Steering angle PID controller";
            }
        }
        return (Intrinsics.areEqual(k, "longitudinalterms") && Intrinsics.areEqual(src, "controlsstate")) ? "Longitudinal controller terms" : "Live control signal";
    }

    private final String formatAge(double seconds) {
        StringBuilder append;
        String str;
        double v = Math.max(0.0d, seconds);
        if (v < 1.0d) {
            append = new StringBuilder().append(Math.round(1000 * v));
            str = " ms";
        } else {
            append = new StringBuilder().append(f(v, 1));
            str = " s";
        }
        return append.append(str).toString();
    }

    private final double percentile(List<Double> sorted, double p) {
        if (sorted.isEmpty()) {
            return 0.0d;
        }
        double idx = (sorted.size() - 1) * RangesKt.coerceIn(p, 0.0d, 1.0d);
        int lo = (int) Math.floor(idx);
        int hi = (int) Math.ceil(idx);
        if (lo == hi) {
            return sorted.get(lo).doubleValue();
        }
        double w = idx - lo;
        return (sorted.get(lo).doubleValue() * (1 - w)) + (sorted.get(hi).doubleValue() * w);
    }

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
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
                            var14 = (((boolean)true) ? 1 : 0);
                        } else {
                            var14 = (((boolean)false) ? 1 : 0);
                        }

                        if ((var14 != 0)) {
                            var25.add(var27);
                        }
                    }

                    List var64 = var25;
                    var14 = (((boolean)false) ? 1 : 0);
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
                                var14 = (((boolean)true) ? 1 : 0);
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

                        for (GxTuningSample var89 : (Iterable<GxTuningSample>) var78) {
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
                            if ((var14 != 0)) {
                                var66 = ", low-demand fallback";
                            } else {
                                var66 = "";
                            }

                            String var74 = var16 + " samples / " + var15 + "s" + var66;
                            if (var1.getPersistence()) {
                                int var49;
                                if (var1.getWarnError() == 0.0) {
                                    var49 = 1;
                                } else {
                                    var49 = 0;
                                }

                                var4 = 0.35;
                                if ((var49 == 0)) {
                                    var2 = var1.getWarnError();
                                } else {
                                    var2 = 0.35;
                                }

                                if (var1.getGood() == 0.0) {
                                    var49 = (((boolean)true) ? 1 : 0);
                                } else {
                                    var49 = (((boolean)false) ? 1 : 0);
                                }

                                if ((var49 == 0)) {
                                    var4 = var1.getGood();
                                }

                                double var8 = Math.max(var2, var4);
                                if (var1.getSevereError() == 0.0) {
                                    var49 = (((boolean)true) ? 1 : 0);
                                } else {
                                    var49 = (((boolean)false) ? 1 : 0);
                                }

                                if ((var49 == 0)) {
                                    var2 = var1.getSevereError();
                                } else {
                                    var2 = 0.7;
                                }

                                if (var1.getFair() == 0.0) {
                                    var49 = (((boolean)true) ? 1 : 0);
                                } else {
                                    var49 = (((boolean)false) ? 1 : 0);
                                }

                                if ((var49 == 0)) {
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
                                        int var58;
                                        if (((Number)var68.next()).doubleValue() > var8) {
                                            var58 = 1;
                                        } else {
                                            var58 = 0;
                                        }

                                        if ((var58 != 0)) {
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
                                        int var60;
                                        if (((Number)var85.next()).doubleValue() > var2) {
                                            var60 = 1;
                                        } else {
                                            var60 = 0;
                                        }

                                        if ((var60 != 0)) {
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

                if ((var14 != 0)) {
                    var22.add(var24);
                }
            }
        }
    }

    private static final double computeQuality$mag(QualityCfg $cfg, GxTuningSample s) {
        Double d = s.getV().get($cfg.getDesiredKey());
        double abs = Math.abs(d != null ? d.doubleValue() : 0.0d);
        Double d2 = s.getV().get($cfg.getActualKey());
        return Math.max(abs, Math.abs(d2 != null ? d2.doubleValue() : 0.0d));
    }

    /* VF: from Vineflower (jadx: ) */
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

    private final void paintPlots() {
        String label;
        int color;
        String label2;
        double value;
        Map<String, Double> v;
        Double d;
        if (this.plotsBuilt) {
            JSONObject jSONObject = this.live;
            if (jSONObject == null) {
                jSONObject = new JSONObject();
            }
            JSONObject lv = jSONObject;
            LinearLayout linearLayout = this.plotsStatus;
            LinearLayout linearLayout2 = null;
            if (linearLayout == null) {
                Intrinsics.throwUninitializedPropertyAccessException("plotsStatus");
                linearLayout = null;
            }
            linearLayout.removeAllViews();
            if (this.plotsLoading) {
                GxTuningPage gxTuningPage = this;
                LinearLayout linearLayout3 = this.plotsStatus;
                if (linearLayout3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("plotsStatus");
                    linearLayout3 = null;
                }
                GxPage.text$default(gxTuningPage, "Loading live data...", linearLayout3, 0.0f, 0, false, 28, null);
            }
            if (!StringsKt.isBlank(this.plotsError)) {
                LinearLayout linearLayout4 = this.plotsStatus;
                if (linearLayout4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("plotsStatus");
                    linearLayout4 = null;
                }
                notice$default(this, linearLayout4, this.plotsError, 0, "Error:", 4, null);
            }
            int i = 33;
            int i2 = 1;
            if (!this.plotsLoading || this.live != null) {
                LinearLayout linearLayout5 = this.plotsStatus;
                if (linearLayout5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("plotsStatus");
                    linearLayout5 = null;
                }
                line$default(this, linearLayout5, "Onroad", truthy(lv.opt("isOnroad")) ? "Yes" : "No", 0, 8, null);
                LinearLayout linearLayout6 = this.plotsStatus;
                if (linearLayout6 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("plotsStatus");
                    linearLayout6 = null;
                }
                line$default(this, linearLayout6, "Sample Age", formatAge(num$default(this, lv.opt("sampleAgeSeconds"), 0.0d, 2, null)), 0, 8, null);
                LinearLayout linearLayout7 = this.plotsStatus;
                if (linearLayout7 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("plotsStatus");
                    linearLayout7 = null;
                }
                line$default(this, linearLayout7, "Vehicle Speed", f(num$default(this, lv.opt("speed"), 0.0d, 2, null), 2) + " m/s", 0, 8, null);
                LinearLayout linearLayout8 = this.plotsStatus;
                if (linearLayout8 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("plotsStatus");
                    linearLayout8 = null;
                }
                line$default(this, linearLayout8, "Samples", String.valueOf(this.samples.size()), 0, 8, null);
                if (truthy(lv.opt("bootStabilizing"))) {
                    LinearLayout linearLayout9 = this.plotsStatus;
                    if (linearLayout9 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("plotsStatus");
                        linearLayout9 = null;
                    }
                    notice$default(this, linearLayout9, "Plots are warming up after startup.", 0, "Boot stabilizing", 4, null);
                }
                if (!StringsKt.isBlank(s(lv, "lastError"))) {
                    LinearLayout linearLayout10 = this.plotsStatus;
                    if (linearLayout10 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("plotsStatus");
                        linearLayout10 = null;
                    }
                    notice$default(this, linearLayout10, s(lv, "lastError"), 0, "Source Error:", 4, null);
                }
                for (Pair pair : (Iterable<Pair>) CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to("Your lateral tuning is", computeQuality(this.latQ)), TuplesKt.to("Your longitudinal tuning is", computeQuality(this.lonQ))})) {
                    String base = (String) pair.component1();
                    Quality q = (Quality) pair.component2();
                    if (Intrinsics.areEqual(q.getLabel(), "na")) {
                        label = "N/A";
                    } else {
                        label = q.getLabel();
                        if (label.length() > 0) {
                            StringBuilder sb = new StringBuilder();
                            char it = label.charAt(0);
                            String valueOf = String.valueOf(it);
                            Intrinsics.checkNotNull(valueOf, "null cannot be cast to non-null type java.lang.String");
                            String upperCase = valueOf.toUpperCase(Locale.ROOT);
                            Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
                            StringBuilder append = sb.append((Object) upperCase);
                            String substring = label.substring(1);
                            Intrinsics.checkNotNullExpressionValue(substring, "substring(...)");
                            label = append.append(substring).toString();
                        }
                    }
                    String tone = label;
                    SpannableStringBuilder sb2 = new SpannableStringBuilder(base + " ");
                    int st = sb2.length();
                    sb2.append((CharSequence) tone);
                    sb2.setSpan(new ForegroundColorSpan(toneColor(q.getLabel())), st, sb2.length(), 33);
                    LinearLayout linearLayout11 = this.plotsStatus;
                    if (linearLayout11 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("plotsStatus");
                        linearLayout11 = null;
                    }
                    TextView $this$paintPlots_u24lambda_u24157 = text("", linearLayout11, 18.0f, CarUi.Companion.getTEXT(), true);
                    $this$paintPlots_u24lambda_u24157.setText(sb2);
                    $this$paintPlots_u24lambda_u24157.setPadding(0, getUi().dp(10), 0, 0);
                    GxTuningPage gxTuningPage2 = this;
                    String detail = q.getValue() == null ? q.getDetail() : f(q.getValue().doubleValue(), 2) + " m/s² error (" + q.getDetail() + ")";
                    LinearLayout linearLayout12 = this.plotsStatus;
                    if (linearLayout12 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("plotsStatus");
                        linearLayout12 = null;
                    }
                    GxPage.text$default(gxTuningPage2, detail, linearLayout12, 14.0f, 0, false, 24, null);
                }
            }
            TextView textView = this.bPause;
            if (textView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("bPause");
                textView = null;
            }
            textView.setText(this.plotsPaused ? "Resume Live" : "Pause Live");
            TextView textView2 = this.bAdvanced;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("bAdvanced");
                textView2 = null;
            }
            textView2.setText(this.showAdvanced ? "Hide Advanced Controller Terms" : "Show Advanced Controller Terms");
            LinearLayout linearLayout13 = this.advancedBox;
            if (linearLayout13 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("advancedBox");
            } else {
                linearLayout2 = linearLayout13;
            }
            linearLayout2.setVisibility(this.showAdvanced ? 0 : 8);
            boolean empty = this.samples.size() <= 1;
            GxTuningSample latest = (GxTuningSample) CollectionsKt.lastOrNull((List) this.samples);
            Iterator<ChartViews> it2 = this.chartViews.iterator();
            Intrinsics.checkNotNullExpressionValue(it2, "iterator(...)");
            while (it2.hasNext()) {
                ChartViews next = it2.next();
                Intrinsics.checkNotNullExpressionValue(next, "next(...)");
                ChartViews cv = next;
                cv.getSource().setText("Source: " + sourceLabel(cv.getCfg().getSourceKind(), s(lv, cv.getCfg().getSourceKey())));
                cv.getLegend().removeAllViews();
                for (Triple<String, String, Integer> triple : cv.getCfg().getSeries()) {
                    String key = triple.component1();
                    String label3 = triple.component2();
                    int color2 = triple.component3().intValue();
                    if (latest == null || (v = latest.getV()) == null || (d = v.get(key)) == null) {
                        color = color2;
                        label2 = label3;
                        value = num$default(this, lv.opt(key), 0.0d, 2, null);
                    } else {
                        value = d.doubleValue();
                        color = color2;
                        label2 = label3;
                    }
                    SpannableStringBuilder sb3 = new SpannableStringBuilder("▬ ");
                    sb3.setSpan(new ForegroundColorSpan(color), 0, i2, i);
                    sb3.append((CharSequence) (label2 + ": " + f(value, cv.getCfg().getLegendDigits())));
                    LinearLayout legend = cv.getLegend();
                    TextView $this$paintPlots_u24lambda_u24158 = new TextView(getAct());
                    $this$paintPlots_u24lambda_u24158.setText(sb3);
                    $this$paintPlots_u24lambda_u24158.setTextSize(16.0f);
                    $this$paintPlots_u24lambda_u24158.setTextColor(CarUi.Companion.getTEXT());
                    $this$paintPlots_u24lambda_u24158.setPadding(0, 0, getUi().dp(20), 0);
                    legend.addView($this$paintPlots_u24lambda_u24158);
                    i = 33;
                    i2 = 1;
                }
                cv.getChart().setVisibility(empty ? 8 : 0);
                cv.getWaiting().setVisibility(empty ? 0 : 8);
                if (empty) {
                    i = 33;
                    i2 = 1;
                } else {
                    cv.getChart().invalidate();
                    i = 33;
                    i2 = 1;
                }
            }
        }
    }

    private final String slotId(JSONObject slot) {
        String obj;
        return (slot == null || (obj = StringsKt.trim((CharSequence) s(slot, "id")).toString()) == null) ? "" : obj;
    }

    private final boolean isUnused(JSONObject slot) {
        String n = StringsKt.trim((CharSequence) s(slot, "name")).toString().toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(n, "toLowerCase(...)");
        return Intrinsics.areEqual(n, "unused") || StringsKt.startsWith(n, "unused ", false);
    }

    private final TreeMap<String, String> variantLabels(JSONObject slot) {
        TreeMap labels = new TreeMap();
        if (slot == null) {
            labels.put("A", "A");
            return labels;
        }
        JSONObject raw = slot.optJSONObject("variantLabels");
        if (raw != null) {
            Iterator<String> keys = raw.keys();
            Intrinsics.checkNotNullExpressionValue(keys, "keys(...)");
            while (keys.hasNext()) {
                String k = keys.next();
                Intrinsics.checkNotNull(k);
                String mode = StringsKt.trim((CharSequence) k).toString().toUpperCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(mode, "toUpperCase(...)");
                String label = StringsKt.trim((CharSequence) s(raw, k)).toString();
                if (mode.length() == 1) {
                    char charAt = mode.charAt(0);
                    if ('A' <= charAt && charAt < '[') {
                        if (label.length() > 0) {
                            labels.put(mode, label);
                        }
                    }
                }
            }
        }
        String a = StringsKt.trim((CharSequence) s(slot, "aLabel")).toString();
        String b = StringsKt.trim((CharSequence) s(slot, "bLabel")).toString();
        if ((a.length() > 0) && labels.get("A") == null) {
            labels.put("A", a);
        }
        if ((b.length() > 0) && labels.get("B") == null) {
            labels.put("B", b);
        }
        if (labels.get("A") == null) {
            labels.put("A", "A");
        }
        return labels;
    }

    private final String modeLabel(JSONObject slot, String mode) {
        String m = StringsKt.trim((CharSequence) mode).toString().toUpperCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(m, "toUpperCase(...)");
        String str = variantLabels(slot).get(m);
        if (str != null) {
            return str;
        }
        String str2 = m;
        if (StringsKt.isBlank(str2)) {
            str2 = "A";
        }
        return str2;
    }

    private final String defaultMode(JSONObject slot) {
        String str;
        Set modes = variantLabels(slot).keySet();
        Intrinsics.checkNotNullExpressionValue(modes, "<get-keys>(...)");
        return (modes.contains("A") || (str = (String) CollectionsKt.firstOrNull(modes)) == null) ? "A" : str;
    }

    private final List<JSONObject> tgSlots() {
        return objects(arr(this.tgData, "slots"));
    }

    private final List<JSONObject> tgSelectable() {
        List sel = objects(arr(this.tgData, "selectableSlots"));
        if (!sel.isEmpty()) {
            return sel;
        }
        Iterable $this$filter$iv = tgSlots();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            JSONObject it = (JSONObject) element$iv$iv;
            if (!isUnused(it)) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        return (List) destination$iv$iv;
    }

    private final String tgActiveId() {
        return StringsKt.trim((CharSequence) s(this.tgData, "activeSlot")).toString();
    }

    private final String tgActiveVariant() {
        String upperCase = StringsKt.trim((CharSequence) s(this.tgData, "activeVariant")).toString().toUpperCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
        return upperCase;
    }

    private final void buildTesting(LinearLayout b) {
        this.tgBuilt = true;
        header("A/B Tuning Sandbox", b);
        note("A/B Tuning Sandbox. If you don't know what this is, you probably shouldn't be here ;)", b);
        this.tgBox = box(b);
        paintTg();
    }

    private final void tgApplyPayload(JSONObject p) {
        Iterable $this$none$iv;
        this.tgData = p;
        this.tgError = "";
        List selectable = objects(arr(p, "selectableSlots"));
        List $this$none$iv2 = selectable;
        boolean z = false;
        if (!($this$none$iv2 instanceof Collection) || !$this$none$iv2.isEmpty()) {
            Iterator it = $this$none$iv2.iterator();
            while (true) {
                if (it.hasNext()) {
                    Object element$iv = it.next();
                    JSONObject it2 = (JSONObject) element$iv;
                    if (Intrinsics.areEqual(slotId(it2), StringsKt.trim((CharSequence) this.tgSelected).toString())) {
                        $this$none$iv = null;
                        break;
                    }
                } else {
                    $this$none$iv = 1;
                    break;
                }
            }
        } else {
            $this$none$iv = 1;
        }
        if ($this$none$iv != null) {
            String active = StringsKt.trim((CharSequence) s(p, "activeSlot")).toString();
            List $this$any$iv = selectable;
            if (!($this$any$iv instanceof Collection) || !$this$any$iv.isEmpty()) {
                Iterator it3 = $this$any$iv.iterator();
                while (true) {
                    if (!it3.hasNext()) {
                        break;
                    }
                    Object element$iv2 = it3.next();
                    JSONObject it4 = (JSONObject) element$iv2;
                    if (Intrinsics.areEqual(slotId(it4), active)) {
                        z = true;
                        break;
                    }
                }
            }
            this.tgSelected = z ? active : slotId((JSONObject) CollectionsKt.firstOrNull(selectable));
        }
    }

    private final void tgLoad() {
        if (!this.tgBuilt || this.tgFetching) {
            return;
        }
        this.tgFetching = true;
        getJson("/api/testing_grounds", new Function2() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit tgLoad$lambda$165;
                tgLoad$lambda$165 = GxTuningPage.tgLoad$lambda$165(GxTuningPage.this, (String) obj, (JSONObject) obj2);
                return tgLoad$lambda$165;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tgLoad$lambda$165(GxTuningPage this$0, String e, JSONObject o) {
        this$0.tgFetching = false;
        this$0.tgLoading = false;
        if (o != null) {
            this$0.tgApplyPayload(o);
        } else {
            this$0.tgError = e == null ? "Failed to load testing grounds" : e;
        }
        this$0.paintTg();
        return Unit.INSTANCE;
    }

    private final void tgSelectMode(String mode) {
        Object element$iv;
        if (this.tgBusy) {
            return;
        }
        Iterable $this$firstOrNull$iv = tgSlots();
        Iterator it = $this$firstOrNull$iv.iterator();
        while (true) {
            if (it.hasNext()) {
                element$iv = it.next();
                JSONObject it2 = (JSONObject) element$iv;
                if (Intrinsics.areEqual(slotId(it2), StringsKt.trim((CharSequence) this.tgSelected).toString())) {
                    break;
                }
            } else {
                element$iv = null;
                break;
            }
        }
        JSONObject slot = (JSONObject) element$iv;
        if (slot == null) {
            toast("Select a testing ground first.");
            return;
        }
        final String sid = slotId(slot);
        final String m = StringsKt.trim((CharSequence) mode).toString().toUpperCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(m, "toUpperCase(...)");
        if (StringsKt.isBlank(sid) || StringsKt.isBlank(m)) {
            return;
        }
        this.tgBusy = true;
        paintTg();
        GxPage.send$default(this, "POST", "/api/testing_grounds/select", new JSONObject().put("slotId", sid).put("variant", m), 0L, new Function2() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda26
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit tgSelectMode$lambda$170;
                tgSelectMode$lambda$170 = GxTuningPage.tgSelectMode$lambda$170(GxTuningPage.this, sid, m, (String) obj, (JSONObject) obj2);
                return tgSelectMode$lambda$170;
            }
        }, 8, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit tgSelectMode$lambda$170(GxTuningPage this$0, String $sid, String $m, String e, JSONObject o) {
        Object element$iv;
        this$0.tgBusy = false;
        if (e != null || o == null) {
            this$0.tgError = e == null ? "Failed to update testing ground mode" : e;
            this$0.toast(this$0.tgError);
        } else {
            this$0.tgApplyPayload(o);
            this$0.tgSelected = $sid;
            this$0.tgError = "";
            Iterable $this$firstOrNull$iv = this$0.objects(this$0.arr(o, "slots"));
            Iterator it = $this$firstOrNull$iv.iterator();
            while (true) {
                if (it.hasNext()) {
                    element$iv = it.next();
                    JSONObject it2 = (JSONObject) element$iv;
                    if (Intrinsics.areEqual(this$0.slotId(it2), $sid)) {
                        break;
                    }
                } else {
                    element$iv = null;
                    break;
                }
            }
            JSONObject selSlot = (JSONObject) element$iv;
            String obj = StringsKt.trim((CharSequence) this$0.s(o, "activeVariantLabel")).toString();
            if (StringsKt.isBlank(obj)) {
                obj = this$0.modeLabel(selSlot, $m);
            }
            String label = obj;
            String s = this$0.s(o, "message");
            if (StringsKt.isBlank(s)) {
                s = "Testing Ground " + $sid + " set to " + label + ".";
            }
            this$0.toast(s);
        }
        this$0.paintTg();
        return Unit.INSTANCE;
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    private final void paintTg() {
        E var16 = null; E var17 = null; String var21 = null; String var27 = null; Row var74 = null; // declarations Vineflower left out
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

                    for (JSONObject var12 : (Iterable<JSONObject>) var9) {
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
                        Object var82 = new LayoutParams(-1, -2);
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

                    this.en(GxPage.Row.button$default(((GxPage.Row) var74), var27, false, new GxTuningPage$$ExternalSyntheticLambda40(this, var9), 2, null), this.tgBusy ^ true);
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

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintTg$lambda$182(final GxTuningPage this$0, final List $selectable) {
        if (this$0.tgBusy) {
            return Unit.INSTANCE;
        }
        List $this$map$iv = $selectable;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            JSONObject it = (JSONObject) item$iv$iv;
            destination$iv$iv.add(this$0.slotId(it) + ". " + this$0.s(it, "name"));
        }
        this$0.pick("View Slot", (List) destination$iv$iv, new Function1() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit paintTg$lambda$182$lambda$181;
                paintTg$lambda$182$lambda$181 = GxTuningPage.paintTg$lambda$182$lambda$181(this$0, $selectable, ((Integer) obj).intValue());
                return paintTg$lambda$182$lambda$181;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintTg$lambda$182$lambda$181(GxTuningPage this$0, List $selectable, int i) {
        this$0.tgSelected = this$0.slotId((JSONObject) $selectable.get(i));
        this$0.paintTg();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit paintTg$lambda$184(GxTuningPage this$0, String $mode) {
        this$0.tgSelectMode($mode);
        return Unit.INSTANCE;
    }

    private final void buildManeuvers(LinearLayout b) {
        this.manBuilt = true;
        header("Lateral Maneuvers", b);
        LinearLayout lc = card(b);
        GxPage.text$default(this, "Arm the lateral maneuver suite from your phone and monitor progress live.", lc, 0.0f, 0, false, 28, null);
        LinearLayout lb = btnBar(lc);
        TextView barBtn = barBtn(lb, "Start / Arm", true, new Function0() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda34
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildManeuvers$lambda$187;
                buildManeuvers$lambda$187 = GxTuningPage.buildManeuvers$lambda$187(GxTuningPage.this);
                return buildManeuvers$lambda$187;
            }
        });
        TextView $this$buildManeuvers_u24lambda_u24189 = barBtn$default(this, lb, "Stop", false, new Function0() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda35
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildManeuvers$lambda$188;
                buildManeuvers$lambda$188 = GxTuningPage.buildManeuvers$lambda$188(GxTuningPage.this);
                return buildManeuvers$lambda$188;
            }
        }, 4, null);
        $this$buildManeuvers_u24lambda_u24189.setTextColor(Color.rgb(240, 110, 130));
        Unit unit = Unit.INSTANCE;
        this.latBtns = TuplesKt.to(barBtn, $this$buildManeuvers_u24lambda_u24189);
        this.latBox = box(lc);
        header("Long Maneuvers", b);
        LinearLayout oc = card(b);
        GxPage.text$default(this, "Run the longitudinal maneuver suite from your phone and monitor progress live.", oc, 0.0f, 0, false, 28, null);
        LinearLayout ob = btnBar(oc);
        TextView barBtn2 = barBtn(ob, "Start / Arm", true, new Function0() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda36
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildManeuvers$lambda$190;
                buildManeuvers$lambda$190 = GxTuningPage.buildManeuvers$lambda$190(GxTuningPage.this);
                return buildManeuvers$lambda$190;
            }
        });
        TextView $this$buildManeuvers_u24lambda_u24192 = barBtn$default(this, ob, "Stop", false, new Function0() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda37
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildManeuvers$lambda$191;
                buildManeuvers$lambda$191 = GxTuningPage.buildManeuvers$lambda$191(GxTuningPage.this);
                return buildManeuvers$lambda$191;
            }
        }, 4, null);
        $this$buildManeuvers_u24lambda_u24192.setTextColor(Color.rgb(240, 110, 130));
        Unit unit2 = Unit.INSTANCE;
        this.lonBtns = TuplesKt.to(barBtn2, $this$buildManeuvers_u24lambda_u24192);
        this.lonBox = box(oc);
        paintLat();
        paintLon();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildManeuvers$lambda$187(GxTuningPage this$0) {
        this$0.manRun(true, "start");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildManeuvers$lambda$188(GxTuningPage this$0) {
        this$0.manRun(true, "stop");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildManeuvers$lambda$190(GxTuningPage this$0) {
        this$0.manRun(false, "start");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildManeuvers$lambda$191(GxTuningPage this$0) {
        this$0.manRun(false, "stop");
        return Unit.INSTANCE;
    }

    private final void manLoad(final boolean lateral) {
        if (this.manBuilt) {
            if (lateral) {
                if (this.latFetching) {
                    return;
                } else {
                    this.latFetching = true;
                }
            } else if (this.lonFetching) {
                return;
            } else {
                this.lonFetching = true;
            }
            getJson(lateral ? "/api/lateral_maneuvers/status" : "/api/longitudinal_maneuvers/status", new Function2() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda8
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Unit manLoad$lambda$193;
                    manLoad$lambda$193 = GxTuningPage.manLoad$lambda$193(lateral, GxTuningPage.this, (String) obj, (JSONObject) obj2);
                    return manLoad$lambda$193;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit manLoad$lambda$193(boolean $lateral, GxTuningPage this$0, String e, JSONObject o) {
        String str;
        if ($lateral) {
            this$0.latFetching = false;
            this$0.latLoading = false;
            if (o != null) {
                this$0.latData = o;
            }
            this$0.paintLat();
        } else {
            this$0.lonFetching = false;
            this$0.lonLoading = false;
            if (o != null) {
                this$0.lonData = o;
                str = "";
            } else {
                str = e == null ? "Failed to load maneuver status" : e;
            }
            this$0.lonError = str;
            this$0.paintLon();
        }
        return Unit.INSTANCE;
    }

    private final void manRun(final boolean lateral, final String action) {
        if (lateral ? this.latBusy : this.lonBusy) {
            return;
        }
        if (lateral) {
            this.latBusy = true;
        } else {
            this.lonBusy = true;
        }
        paintLat();
        paintLon();
        GxPage.send$default(this, "POST", "/api/" + (lateral ? "lateral" : "longitudinal") + "_maneuvers/" + action, null, 0L, new Function2() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda67
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit manRun$lambda$197;
                manRun$lambda$197 = GxTuningPage.manRun$lambda$197(lateral, GxTuningPage.this, action, (String) obj, (JSONObject) obj2);
                return manRun$lambda$197;
            }
        }, 12, null);
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public static final Unit manRun$lambda$197(boolean var0, GxTuningPage var1, String var2, String var3, JSONObject var4) {
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

    private final void history(ViewGroup into, JSONObject d, String emptyText) {
        List h = strings(arr(d, "history"));
        if (h.isEmpty() && emptyText == null) {
            return;
        }
        sub("Progress chain", into);
        if (h.isEmpty()) {
            GxPage.text$default(this, emptyText == null ? "" : emptyText, into, 0.0f, 0, false, 28, null);
        } else {
            LinearLayout ic = inner(into);
            bullets(ic, CollectionsKt.reversed(h), true);
        }
    }

    private final void paintLat() {
        if (this.manBuilt) {
            Pair<? extends TextView, ? extends TextView> pair = this.latBtns;
            LinearLayout linearLayout = null;
            if (pair == null) {
                Intrinsics.throwUninitializedPropertyAccessException("latBtns");
                pair = null;
            }
            en(pair.getFirst(), !this.latBusy);
            Pair<? extends TextView, ? extends TextView> pair2 = this.latBtns;
            if (pair2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("latBtns");
                pair2 = null;
            }
            en(pair2.getSecond(), !this.latBusy);
            LinearLayout linearLayout2 = this.latBox;
            if (linearLayout2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("latBox");
                linearLayout2 = null;
            }
            linearLayout2.removeAllViews();
            if (this.latLoading) {
                GxTuningPage gxTuningPage = this;
                LinearLayout linearLayout3 = this.latBox;
                if (linearLayout3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("latBox");
                } else {
                    linearLayout = linearLayout3;
                }
                GxPage.text$default(gxTuningPage, "Loading status...", linearLayout, 0.0f, 0, false, 28, null);
                return;
            }
            JSONObject d = this.latData;
            if (d == null) {
                return;
            }
            LinearLayout linearLayout4 = this.latBox;
            if (linearLayout4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("latBox");
                linearLayout4 = null;
            }
            line$default(this, linearLayout4, "Mode Enabled", truthy(d.opt("modeEnabled")) ? "Yes" : "No", 0, 8, null);
            LinearLayout linearLayout5 = this.latBox;
            if (linearLayout5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("latBox");
                linearLayout5 = null;
            }
            LinearLayout linearLayout6 = linearLayout5;
            String s = s(d, "state");
            if (StringsKt.isBlank(s)) {
                s = "idle";
            }
            line$default(this, linearLayout6, "State", s, 0, 8, null);
            LinearLayout linearLayout7 = this.latBox;
            if (linearLayout7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("latBox");
                linearLayout7 = null;
            }
            line$default(this, linearLayout7, "Onroad", truthy(d.opt("isOnroad")) ? "Yes" : "No", 0, 8, null);
            LinearLayout linearLayout8 = this.latBox;
            if (linearLayout8 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("latBox");
                linearLayout8 = null;
            }
            line$default(this, linearLayout8, "Engaged", truthy(d.opt("isEngaged")) ? "Yes" : "No", 0, 8, null);
            LinearLayout linearLayout9 = this.latBox;
            if (linearLayout9 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("latBox");
                linearLayout9 = null;
            }
            LinearLayout linearLayout10 = linearLayout9;
            String s2 = s(d, "phase");
            String str = "n/a";
            if (StringsKt.isBlank(s2)) {
                s2 = "n/a";
            }
            line$default(this, linearLayout10, "Phase", s2, 0, 8, null);
            LinearLayout linearLayout11 = this.latBox;
            if (linearLayout11 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("latBox");
                linearLayout11 = null;
            }
            line$default(this, linearLayout11, "Step", ((long) num$default(this, d.opt("stepIndex"), 0.0d, 2, null)) + " / " + ((long) num$default(this, d.opt("stepTotal"), 0.0d, 2, null)), 0, 8, null);
            LinearLayout linearLayout12 = this.latBox;
            if (linearLayout12 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("latBox");
                linearLayout12 = null;
            }
            line$default(this, linearLayout12, "Run", ((long) num$default(this, d.opt("runIndex"), 0.0d, 2, null)) + " / " + ((long) num$default(this, d.opt("runTotal"), 0.0d, 2, null)), 0, 8, null);
            LinearLayout linearLayout13 = this.latBox;
            if (linearLayout13 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("latBox");
                linearLayout13 = null;
            }
            line$default(this, linearLayout13, "Updated", fmtAgeSeconds(d.opt("updatedAgeSec")), 0, 8, null);
            LinearLayout linearLayout14 = this.latBox;
            if (linearLayout14 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("latBox");
                linearLayout14 = null;
            }
            sub("Current maneuver", linearLayout14);
            String s3 = s(d, "maneuver");
            if (!StringsKt.isBlank(s3)) {
                str = s3;
            }
            String str2 = str;
            LinearLayout linearLayout15 = this.latBox;
            if (linearLayout15 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("latBox");
                linearLayout15 = null;
            }
            text(str2, linearLayout15, 18.0f, CarUi.Companion.getTEXT(), true);
            String s4 = s(d, "uiText1");
            String it = s(d, "uiText2");
            String popup = s4 + ((Object) (!StringsKt.isBlank(it) ? " | " + it : ""));
            if (!StringsKt.isBlank(popup)) {
                GxTuningPage gxTuningPage2 = this;
                LinearLayout linearLayout16 = this.latBox;
                if (linearLayout16 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("latBox");
                    linearLayout16 = null;
                }
                GxPage.text$default(gxTuningPage2, popup, linearLayout16, 0.0f, 0, false, 28, null);
            }
            LinearLayout linearLayout17 = this.latBox;
            if (linearLayout17 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("latBox");
                linearLayout17 = null;
            }
            sub("Quick guide", linearLayout17);
            LinearLayout linearLayout18 = this.latBox;
            if (linearLayout18 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("latBox");
                linearLayout18 = null;
            }
            bullets(linearLayout18, CollectionsKt.listOf((Object[]) new String[]{"Find a large, empty, straight, flat road or lot with no traffic.", "Press Start / Arm here, then engage openpilot and set the requested speed manually.", "Keep full supervision and be ready to steer or disengage immediately at all times.", "Do not touch the wheel during a run. Steering input or speed drift can reset the current maneuver.", "When the status says complete, collect logs and generate the HTML report if needed."}), true);
            LinearLayout linearLayout19 = this.latBox;
            if (linearLayout19 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("latBox");
            } else {
                linearLayout = linearLayout19;
            }
            history(linearLayout, d, "No steps logged yet.");
        }
    }

    private final void paintLon() {
        LinearLayout linearLayout;
        if (this.manBuilt) {
            Pair<? extends TextView, ? extends TextView> pair = this.lonBtns;
            if (pair == null) {
                Intrinsics.throwUninitializedPropertyAccessException("lonBtns");
                pair = null;
            }
            en(pair.getFirst(), !this.lonBusy);
            Pair<? extends TextView, ? extends TextView> pair2 = this.lonBtns;
            if (pair2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("lonBtns");
                pair2 = null;
            }
            en(pair2.getSecond(), !this.lonBusy);
            LinearLayout linearLayout2 = this.lonBox;
            if (linearLayout2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                linearLayout2 = null;
            }
            linearLayout2.removeAllViews();
            if (!StringsKt.isBlank(this.lonError)) {
                LinearLayout linearLayout3 = this.lonBox;
                if (linearLayout3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                    linearLayout3 = null;
                }
                notice$default(this, linearLayout3, this.lonError, 0, null, 12, null);
            }
            if (this.lonLoading) {
                GxTuningPage gxTuningPage = this;
                LinearLayout linearLayout4 = this.lonBox;
                if (linearLayout4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                    linearLayout = null;
                } else {
                    linearLayout = linearLayout4;
                }
                GxPage.text$default(gxTuningPage, "Loading status...", linearLayout, 0.0f, 0, false, 28, null);
                return;
            }
            JSONObject d = this.lonData;
            if (d == null) {
                return;
            }
            JSONObject optJSONObject = d.optJSONObject("support");
            if (optJSONObject == null) {
                optJSONObject = new JSONObject();
            }
            JSONObject sup = optJSONObject;
            LinearLayout linearLayout5 = this.lonBox;
            if (linearLayout5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                linearLayout5 = null;
            }
            sub("Live status", linearLayout5);
            LinearLayout linearLayout6 = this.lonBox;
            if (linearLayout6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                linearLayout6 = null;
            }
            line$default(this, linearLayout6, "Mode Enabled", truthy(d.opt("modeEnabled")) ? "Yes" : "No", 0, 8, null);
            LinearLayout linearLayout7 = this.lonBox;
            if (linearLayout7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                linearLayout7 = null;
            }
            LinearLayout linearLayout8 = linearLayout7;
            String s = s(d, "state");
            if (StringsKt.isBlank(s)) {
                s = "idle";
            }
            line$default(this, linearLayout8, "State", s, 0, 8, null);
            LinearLayout linearLayout9 = this.lonBox;
            if (linearLayout9 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                linearLayout9 = null;
            }
            line$default(this, linearLayout9, "Onroad", truthy(d.opt("isOnroad")) ? "Yes" : "No", 0, 8, null);
            LinearLayout linearLayout10 = this.lonBox;
            if (linearLayout10 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                linearLayout10 = null;
            }
            line$default(this, linearLayout10, "Engaged", truthy(d.opt("isEngaged")) ? "Yes" : "No", 0, 8, null);
            LinearLayout linearLayout11 = this.lonBox;
            if (linearLayout11 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                linearLayout11 = null;
            }
            LinearLayout linearLayout12 = linearLayout11;
            String s2 = s(d, "phase");
            String str = "n/a";
            if (StringsKt.isBlank(s2)) {
                s2 = "n/a";
            }
            line$default(this, linearLayout12, "Phase", s2, 0, 8, null);
            LinearLayout linearLayout13 = this.lonBox;
            if (linearLayout13 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                linearLayout13 = null;
            }
            LinearLayout linearLayout14 = linearLayout13;
            String s3 = s(d, "paddleMode");
            if (StringsKt.isBlank(s3)) {
                s3 = "auto";
            }
            line$default(this, linearLayout14, "Paddle Mode", s3, 0, 8, null);
            LinearLayout linearLayout15 = this.lonBox;
            if (linearLayout15 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                linearLayout15 = null;
            }
            line$default(this, linearLayout15, "Step", ((long) num$default(this, d.opt("stepIndex"), 0.0d, 2, null)) + " / " + ((long) num$default(this, d.opt("stepTotal"), 0.0d, 2, null)), 0, 8, null);
            LinearLayout linearLayout16 = this.lonBox;
            if (linearLayout16 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                linearLayout16 = null;
            }
            line$default(this, linearLayout16, "Run", ((long) num$default(this, d.opt("runIndex"), 0.0d, 2, null)) + " / " + ((long) num$default(this, d.opt("runTotal"), 0.0d, 2, null)), 0, 8, null);
            LinearLayout linearLayout17 = this.lonBox;
            if (linearLayout17 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                linearLayout17 = null;
            }
            line$default(this, linearLayout17, "Updated", fmtAgeSeconds(d.opt("updatedAgeSec")), 0, 8, null);
            LinearLayout linearLayout18 = this.lonBox;
            if (linearLayout18 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                linearLayout18 = null;
            }
            sub("Current maneuver", linearLayout18);
            String s4 = s(d, "maneuver");
            if (!StringsKt.isBlank(s4)) {
                str = s4;
            }
            String str2 = str;
            LinearLayout linearLayout19 = this.lonBox;
            if (linearLayout19 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                linearLayout19 = null;
            }
            text(str2, linearLayout19, 18.0f, CarUi.Companion.getTEXT(), true);
            String s5 = s(d, "uiText1");
            String it = s(d, "uiText2");
            String popup = s5 + ((Object) (!StringsKt.isBlank(it) ? " | " + it : ""));
            if (!StringsKt.isBlank(popup)) {
                GxTuningPage gxTuningPage2 = this;
                LinearLayout linearLayout20 = this.lonBox;
                if (linearLayout20 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                    linearLayout20 = null;
                }
                GxPage.text$default(gxTuningPage2, popup, linearLayout20, 0.0f, 0, false, 28, null);
            }
            LinearLayout linearLayout21 = this.lonBox;
            if (linearLayout21 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                linearLayout21 = null;
            }
            sub("Longitudinal support", linearLayout21);
            LinearLayout linearLayout22 = this.lonBox;
            if (linearLayout22 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                linearLayout22 = null;
            }
            line$default(this, linearLayout22, "openpilot Longitudinal", paintLon$yes(this, sup, "openpilotLongitudinalControl"), 0, 8, null);
            LinearLayout linearLayout23 = this.lonBox;
            if (linearLayout23 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                linearLayout23 = null;
            }
            line$default(this, linearLayout23, "Full Stop + Go", paintLon$yes(this, sup, "fullStopAndGo"), 0, 8, null);
            LinearLayout linearLayout24 = this.lonBox;
            if (linearLayout24 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                linearLayout24 = null;
            }
            line$default(this, linearLayout24, "Auto Resume from Stop", paintLon$yes(this, sup, "autoResumeFromStop"), 0, 8, null);
            LinearLayout linearLayout25 = this.lonBox;
            if (linearLayout25 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                linearLayout25 = null;
            }
            line$default(this, linearLayout25, "Requires Resume Assist", paintLon$yes(this, sup, "requiresResumeAssist"), 0, 8, null);
            LinearLayout linearLayout26 = this.lonBox;
            if (linearLayout26 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                linearLayout26 = null;
            }
            line$default(this, linearLayout26, "Expected to Reach Zero", paintLon$yes(this, sup, "expectedToReachZero"), 0, 8, null);
            LinearLayout linearLayout27 = this.lonBox;
            if (linearLayout27 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                linearLayout27 = null;
            }
            line$default(this, linearLayout27, "Minimum Enable Speed", paintLon$number(this, sup, "minEnableSpeed", "m/s"), 0, 8, null);
            LinearLayout linearLayout28 = this.lonBox;
            if (linearLayout28 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                linearLayout28 = null;
            }
            line$default(this, linearLayout28, "Stop Acceleration", paintLon$number(this, sup, "stopAccel", "m/s²"), 0, 8, null);
            List caveats = strings(arr(d, "caveats"));
            if (!caveats.isEmpty()) {
                LinearLayout linearLayout29 = this.lonBox;
                if (linearLayout29 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                    linearLayout29 = null;
                }
                notice$default(this, linearLayout29, CollectionsKt.joinToString(caveats, "\n", "", "", -1, "...", new Function1() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda43
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        CharSequence paintLon$lambda$207;
                        paintLon$lambda$207 = GxTuningPage.paintLon$lambda$207((String) obj);
                        return paintLon$lambda$207;
                    }
                }), 0, "Platform caveats\n", 4, null);
            }
            List skipped = strings(arr(d, "skippedManeuvers"));
            if (!skipped.isEmpty()) {
                LinearLayout linearLayout30 = this.lonBox;
                if (linearLayout30 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                    linearLayout30 = null;
                }
                notice(linearLayout30, CollectionsKt.joinToString(skipped, "\n", "", "", -1, "...", new Function1() { // from class: com.starpilot.starview.GxTuningPage$$ExternalSyntheticLambda45
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        CharSequence paintLon$lambda$208;
                        paintLon$lambda$208 = GxTuningPage.paintLon$lambda$208((String) obj);
                        return paintLon$lambda$208;
                    }
                }), Color.rgb(94, 200, 200), "Expected skips\n");
            }
            LinearLayout linearLayout31 = this.lonBox;
            if (linearLayout31 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                linearLayout31 = null;
            }
            sub("Quick guide", linearLayout31);
            LinearLayout linearLayout32 = this.lonBox;
            if (linearLayout32 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                linearLayout32 = null;
            }
            bullets(linearLayout32, CollectionsKt.listOf((Object[]) new String[]{"Find a large, empty, straight road or lot with no traffic.", "Press Start / Arm, then engage openpilot with SET.", "Keep full supervision and be ready to disengage at all times.", "Review the platform caveats first. Low-speed maneuvers can be skipped automatically on cars that do not fully stop.", "When the status says complete, collect the logs and generate your HTML report."}), true);
            LinearLayout linearLayout33 = this.lonBox;
            if (linearLayout33 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("lonBox");
                linearLayout33 = null;
            }
            history(linearLayout33, d, null);
        }
    }

    private static final String paintLon$yes(GxTuningPage this$0, JSONObject sup, String k) {
        return this$0.truthy(sup.opt(k)) ? "Yes" : "No";
    }

    private static final String paintLon$number(GxTuningPage this$0, JSONObject sup, String k, String unit) {
        double v = this$0.num(sup.opt(k), Double.NaN);
        if (Double.isNaN(v)) {
            return "n/a";
        }
        return this$0.f(v, 2) + " " + unit;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence paintLon$lambda$207(String it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return "•  " + it;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence paintLon$lambda$208(String it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return "•  " + it;
    }
}
