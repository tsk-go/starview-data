package com.starpilot.starview;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
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
import com.starpilot.starview.GxNavigationPage;
import com.starpilot.starview.GxPage;
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
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SpreadBuilder;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.ranges.IntRange;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.RegexOption;
import kotlin.text.StringsKt;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: GxNavigation.kt */
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
    private final List<KeyMeta> keyMeta;
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GxNavigationPage(Activity act, GxHost gx) {
        super(act, gx);
        Intrinsics.checkNotNullParameter(act, "act");
        Intrinsics.checkNotNullParameter(gx, "gx");
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
        String uuid = UUID.randomUUID().toString();
        Intrinsics.checkNotNullExpressionValue(uuid, "toString(...)");
        this.sessionToken = uuid;
        this.searchRunner = new Runnable() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda55
            @Override // java.lang.Runnable
            public final void run() {
                GxNavigationPage.searchRunner$lambda$22(GxNavigationPage.this);
            }
        };
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
        this.keyMeta = CollectionsKt.listOf((Object[]) new KeyMeta[]{new KeyMeta("amap1", "amap1", "", 39, "AMap / Gaode 1", "amap1Key"), new KeyMeta("amap2", "amap2", "", 39, "AMap / Gaode 2", "amap2Key"), new KeyMeta("public", "public", "pk.", 80, "Public Mapbox", "mapboxPublic"), new KeyMeta("secret", "secret", "sk.", 80, "Secret Mapbox", "mapboxSecret")});
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

    @Override // com.starpilot.starview.GxPage
    public boolean getNeedsOffroad() {
        return this.needsOffroad;
    }

    @Override // com.starpilot.starview.GxPage
    public void build() {
        GxPage.header$default(this, "Navigation & Maps", null, 2, null);
        this.selectTab = tabs(CollectionsKt.listOf((Object[]) new String[]{"Destination", "Maps", "App Keys", "Speed Limits"}), 0, new Function1() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda68
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit build$lambda$0;
                build$lambda$0 = GxNavigationPage.build$lambda$0(GxNavigationPage.this, ((Integer) obj).intValue());
                return build$lambda$0;
            }
        }, new Function2() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda69
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit build$lambda$1;
                build$lambda$1 = GxNavigationPage.build$lambda$1(GxNavigationPage.this, ((Integer) obj).intValue(), (LinearLayout) obj2);
                return build$lambda$1;
            }
        });
        every(2500L, new Function0() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda70
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit build$lambda$2;
                build$lambda$2 = GxNavigationPage.build$lambda$2(GxNavigationPage.this);
                return build$lambda$2;
            }
        });
        every(3000L, new Function0() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda71
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit build$lambda$3;
                build$lambda$3 = GxNavigationPage.build$lambda$3(GxNavigationPage.this);
                return build$lambda$3;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$0(GxNavigationPage this$0, int i) {
        this$0.curTab = i;
        switch (i) {
            case 0:
                this$0.loadNav();
                break;
            case 1:
                this$0.refreshMapsStatus();
                break;
            case 2:
                this$0.loadKeys();
                break;
            case 3:
                this$0.loadSpeed();
                break;
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$1(GxNavigationPage this$0, int i, LinearLayout box) {
        Intrinsics.checkNotNullParameter(box, "box");
        switch (i) {
            case 0:
                this$0.buildDest(box);
                break;
            case 1:
                this$0.buildMaps(box);
                break;
            case 2:
                this$0.buildKeys(box);
                break;
            case 3:
                this$0.buildSpeed(box);
                break;
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$2(GxNavigationPage this$0) {
        if (this$0.curTab == 1) {
            this$0.refreshMapsStatus();
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$3(GxNavigationPage this$0) {
        if (this$0.curTab == 3) {
            this$0.loadSpeed();
        }
        return Unit.INSTANCE;
    }

    @Override // com.starpilot.starview.GxPage
    public void onShow() {
        if (this.curTab == 0) {
            loadNav();
        } else if (this.curTab == 2) {
            loadKeys();
        }
    }

    @Override // com.starpilot.starview.GxPage
    public void onHide() {
        getMain().removeCallbacks(this.searchRunner);
    }

    private final Double numOf(Object v) {
        Double doubleOrNull;
        if (v instanceof Number) {
            Double valueOf = Double.valueOf(((Number) v).doubleValue());
            double it = valueOf.doubleValue();
            if ((Double.isInfinite(it) || Double.isNaN(it)) ? false : true) {
                return valueOf;
            }
            return null;
        }
        if (!(v instanceof String) || (doubleOrNull = StringsKt.toDoubleOrNull(StringsKt.trim((CharSequence) v).toString())) == null) {
            return null;
        }
        double it2 = doubleOrNull.doubleValue();
        if ((Double.isInfinite(it2) || Double.isNaN(it2)) ? false : true) {
            return doubleOrNull;
        }
        return null;
    }

    private final Pair<Double, Double> coordsArr(JSONArray a) {
        Double numOf;
        if (a != null && a.length() >= 2 && (numOf = numOf(a.opt(0))) != null) {
            double lon = numOf.doubleValue();
            Double numOf2 = numOf(a.opt(1));
            if (numOf2 != null) {
                double lat = numOf2.doubleValue();
                return TuplesKt.to(Double.valueOf(lat), Double.valueOf(lon));
            }
        }
        return null;
    }

    private final Pair<Double, Double> coordsOf(JSONObject o) {
        if (o == null) {
            return null;
        }
        JSONObject optJSONObject = o.optJSONObject("geometry");
        Pair it = coordsArr(optJSONObject != null ? optJSONObject.optJSONArray("coordinates") : null);
        if (it != null) {
            return it;
        }
        Double numOf = numOf(o.opt("latitude"));
        if (numOf != null) {
            double lat = numOf.doubleValue();
            Double numOf2 = numOf(o.opt("longitude"));
            if (numOf2 != null) {
                double lon = numOf2.doubleValue();
                return TuplesKt.to(Double.valueOf(lat), Double.valueOf(lon));
            }
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final String labelFor(JSONObject p) {
        String str;
        String str$default;
        Iterable $this$map$iv = CollectionsKt.listOf((Object[]) new String[]{"full_address", "place_name", "name", "address"});
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        Iterator it = $this$map$iv.iterator();
        while (true) {
            String str2 = "";
            str = null;
            if (!it.hasNext()) {
                break;
            }
            Object item$iv$iv = it.next();
            String it2 = (String) item$iv$iv;
            if (p != null && (str$default = GxPage.str$default(this, p, it2, null, 2, null)) != null) {
                str = StringsKt.trim((CharSequence) str$default).toString();
            }
            if (str != null) {
                str2 = str;
            }
            destination$iv$iv.add(str2);
        }
        Iterable $this$firstOrNull$iv = (List) destination$iv$iv;
        Iterator it3 = $this$firstOrNull$iv.iterator();
        while (true) {
            if (!it3.hasNext()) {
                break;
            }
            Object next = it3.next();
            String it4 = (String) next;
            if (it4.length() > 0) {
                str = next;
                break;
            }
        }
        String str3 = str;
        return str3 == null ? "" : str3;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0050 A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:18:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final String primaryOf(JSONObject p) {
        String str$default;
        String str$default2;
        String obj;
        String str = null;
        if (p != null && (str$default2 = GxPage.str$default(this, p, "name", null, 2, null)) != null && (obj = StringsKt.trim((CharSequence) str$default2).toString()) != null) {
            String str2 = obj;
            if (str2.length() == 0) {
                str2 = null;
            }
            String str3 = str2;
            if (str3 != null) {
                str = str3;
                return str != null ? "" : str;
            }
        }
        if (p != null && (str$default = GxPage.str$default(this, p, "text", null, 2, null)) != null) {
            str = StringsKt.trim((CharSequence) str$default).toString();
        }
        if (str != null) {
        }
    }

    private final String secondaryLabel(JSONObject p) {
        String primary = primaryOf(p);
        String full = labelFor(p);
        return (!(full.length() > 0) || StringsKt.equals(full, primary, true)) ? "" : full;
    }

    private final boolean sameSpot(Pair<Double, Double> a, Pair<Double, Double> b) {
        return a != null && b != null && Math.abs(a.getFirst().doubleValue() - b.getFirst().doubleValue()) < 1.0E-5d && Math.abs(a.getSecond().doubleValue() - b.getSecond().doubleValue()) < 1.0E-5d;
    }

    private final List<JSONObject> parseArr(Object v) {
        Object m1191constructorimpl;
        if (v instanceof JSONArray) {
            return objects((JSONArray) v);
        }
        if (v instanceof String) {
            try {
                Result.Companion companion = Result.Companion;
                GxNavigationPage $this$parseArr_u24lambda_u2410 = this;
                m1191constructorimpl = KtCompat.resultOf($this$parseArr_u24lambda_u2410.objects(new JSONArray((String) v)));
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                m1191constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
            }
            List emptyList = CollectionsKt.emptyList();
            if (KtCompat.isFailure(m1191constructorimpl)) {
                m1191constructorimpl = emptyList;
            }
            return (List) m1191constructorimpl;
        }
        return CollectionsKt.emptyList();
    }

    private final JSONObject parseObj(Object v) {
        Object m1191constructorimpl;
        if (v instanceof JSONObject) {
            return (JSONObject) v;
        }
        if ((v instanceof String) && !StringsKt.isBlank((CharSequence) v)) {
            try {
                Result.Companion companion = Result.Companion;
                GxNavigationPage gxNavigationPage = this;
                m1191constructorimpl = KtCompat.resultOf(new JSONObject((String) v));
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                m1191constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
            }
            return (JSONObject) (KtCompat.isFailure(m1191constructorimpl) ? null : m1191constructorimpl);
        }
        return null;
    }

    private final void openUrl(String u) {
        try {
            getAct().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(u)));
        } catch (Exception e) {
            toast("No app can open " + u);
        }
    }

    private final void setOn(TextView b, boolean on) {
        if (b == null) {
            return;
        }
        b.setEnabled(on);
        b.setAlpha(on ? 1.0f : 0.4f);
    }

    private final EditText field(String hint) {
        EditText $this$field_u24lambda_u2412 = new EditText(getAct());
        $this$field_u24lambda_u2412.setHint(hint);
        $this$field_u24lambda_u2412.setTextSize(20.0f);
        $this$field_u24lambda_u2412.setTextColor(CarUi.Companion.getTEXT());
        $this$field_u24lambda_u2412.setHintTextColor(CarUi.Companion.getDIM());
        $this$field_u24lambda_u2412.setSingleLine(true);
        $this$field_u24lambda_u2412.setBackground(getUi().rounded(CarUi.Companion.getCARD_ON(), 12, Integer.valueOf(Color.rgb(60, 66, 78))));
        $this$field_u24lambda_u2412.setPadding(getUi().dp(16), getUi().dp(12), getUi().dp(16), getUi().dp(12));
        $this$field_u24lambda_u2412.setMinHeight(getUi().dp(56));
        return $this$field_u24lambda_u2412;
    }

    private final void clickable(GxPage.Row r, final Function0<Unit> onClick) {
        r.getView().setClickable(true);
        r.getView().setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda49
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                onClick.invoke();
            }
        });
    }

    private final void extGet(final String u, final Function2<? super String, ? super JSONObject, Unit> then) {
        new Thread(new Runnable() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda76
            @Override // java.lang.Runnable
            public final void run() {
                GxNavigationPage.extGet$lambda$19(u, this, then);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x007f, code lost:
    
        if (r6 == 0) goto L27;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v1, types: [T] */
    /* JADX WARN: Type inference failed for: r3v12 */
    /* JADX WARN: Type inference failed for: r3v13 */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v14, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r6v21, types: [T] */
    /* JADX WARN: Type inference failed for: r6v25, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r6v28 */
    /* JADX WARN: Type inference failed for: r6v34 */
    /* JADX WARN: Type inference failed for: r6v35 */
    /* JADX WARN: Type inference failed for: r6v4, types: [T] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void extGet$lambda$19(String str, GxNavigationPage gxNavigationPage, final Function2 function2) {
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        final Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
        try {
            Response execute = GxPage.Companion.client(20L).newCall(new Request.Builder().url(str).build()).execute();
            Throwable thrown = null;
            try {
                ResponseBody body = execute.body();
                String text = body != null ? body.string() : null;
                if (text == null) {
                    text = "";
                }
                JSONObject obj;
                try {
                    obj = new JSONObject(text);
                } catch (Throwable th) {
                    obj = null;
                }
                objectRef2.element = obj;
                if (!execute.isSuccessful()) {
                    String msg = obj != null ? obj.optString("message") : null;
                    if (msg != null && StringsKt.isBlank(msg)) {
                        msg = null;
                    }
                    if (msg == null) {
                        String er = obj != null ? obj.optString("error") : null;
                        if (er != null && StringsKt.isBlank(er)) {
                            er = null;
                        }
                        msg = er;
                    }
                    if (msg == null) {
                        msg = "HTTP " + execute.code();
                    }
                    objectRef.element = msg;
                }
            } catch (Throwable t) {
                thrown = t;
                throw t;
            } finally {
                CloseableKt.closeFinally(execute, thrown);
            }
        } catch (Exception e) {
            String message = e.getMessage();
            objectRef.element = message != null ? message : e.toString();
        }
        gxNavigationPage.getMain().post(new Runnable() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda33
            @Override // java.lang.Runnable
            public final void run() {
                GxNavigationPage.extGet$lambda$19$lambda$18(function2, objectRef, objectRef2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void extGet$lambda$19$lambda$18(Function2 $then, Ref.ObjectRef $err, Ref.ObjectRef $obj) {
        $then.invoke($err.element, $obj.element);
    }

    private final String qs(Pair<String, String>... kv) {
        Collection destination$iv$iv = new ArrayList();
        for (Pair<String, String> pair : kv) {
            String second = pair.getSecond();
            if (!(second == null || second.length() == 0)) {
                destination$iv$iv.add(pair);
            }
        }
        return CollectionsKt.joinToString((List) destination$iv$iv, "&", "", "", -1, "...", new Function1() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda80
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                CharSequence qs$lambda$21;
                qs$lambda$21 = GxNavigationPage.qs$lambda$21(GxNavigationPage.this, (Pair) obj);
                return qs$lambda$21;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence qs$lambda$21(GxNavigationPage this$0, Pair it) {
        Intrinsics.checkNotNullParameter(it, "it");
        String enc = this$0.enc((String) it.getFirst());
        Object second = it.getSecond();
        Intrinsics.checkNotNull(second);
        return enc + "=" + this$0.enc((String) second);
    }

    private final boolean getHasMapbox() {
        return this.mapboxPublic.length() > 0;
    }

    private final boolean getHasRoutingKey() {
        return this.mapboxSecret.length() > 0;
    }

    private final String getQuery() {
        if (this.queryEdit == null) {
            return "";
        }
        EditText editText = this.queryEdit;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("queryEdit");
            editText = null;
        }
        return editText.getText().toString();
    }

    private final void setQuery(String s) {
        this.quietEdit = true;
        EditText editText = this.queryEdit;
        EditText editText2 = null;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("queryEdit");
            editText = null;
        }
        editText.setText(s);
        EditText editText3 = this.queryEdit;
        if (editText3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("queryEdit");
            editText3 = null;
        }
        EditText editText4 = this.queryEdit;
        if (editText4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("queryEdit");
        } else {
            editText2 = editText4;
        }
        editText3.setSelection(editText2.getText().length());
        this.quietEdit = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void searchRunner$lambda$22(GxNavigationPage this$0) {
        this$0.search(StringsKt.trim((CharSequence) this$0.getQuery()).toString());
    }

    private final void buildDest(LinearLayout tab) {
        this.keyBox = box(tab);
        this.searchCard = card(tab);
        GxNavigationPage gxNavigationPage = this;
        LinearLayout linearLayout = this.searchCard;
        LinearLayout linearLayout2 = null;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("searchCard");
            linearLayout = null;
        }
        GxPage.text$default(gxNavigationPage, "Search for a destination", linearLayout, 21.0f, CarUi.Companion.getTEXT(), false, 16, null);
        EditText $this$buildDest_u24lambda_u2424 = field("Search here");
        $this$buildDest_u24lambda_u2424.setImeOptions(3);
        $this$buildDest_u24lambda_u2424.setInputType(524289);
        $this$buildDest_u24lambda_u2424.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda54
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean buildDest$lambda$24$lambda$23;
                buildDest$lambda$24$lambda$23 = GxNavigationPage.buildDest$lambda$24$lambda$23(GxNavigationPage.this, textView, i, keyEvent);
                return buildDest$lambda$24$lambda$23;
            }
        });
        $this$buildDest_u24lambda_u2424.addTextChangedListener(new TextWatcher() { // from class: com.starpilot.starview.GxNavigationPage$buildDest$1$2
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int a, int b, int c) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int a, int b, int c) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                boolean z;
                z = GxNavigationPage.this.quietEdit;
                if (z) {
                    return;
                }
                GxNavigationPage.this.onInput();
            }
        });
        this.queryEdit = $this$buildDest_u24lambda_u2424;
        LinearLayout linearLayout3 = this.searchCard;
        if (linearLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("searchCard");
            linearLayout3 = null;
        }
        EditText editText = this.queryEdit;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("queryEdit");
            editText = null;
        }
        LinearLayout.LayoutParams $this$buildDest_u24lambda_u2425 = new LinearLayout.LayoutParams(-1, -2);
        $this$buildDest_u24lambda_u2425.topMargin = getUi().dp(10);
        $this$buildDest_u24lambda_u2425.bottomMargin = getUi().dp(8);
        Unit unit = Unit.INSTANCE;
        linearLayout3.addView(editText, $this$buildDest_u24lambda_u2425);
        Pair<String, ? extends Function0<Unit>>[] pairArr = {TuplesKt.to("Set Destination", new Function0() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda56
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildDest$lambda$26;
                buildDest$lambda$26 = GxNavigationPage.buildDest$lambda$26(GxNavigationPage.this);
                return buildDest$lambda$26;
            }
        }), TuplesKt.to("Search", new Function0() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda57
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildDest$lambda$27;
                buildDest$lambda$27 = GxNavigationPage.buildDest$lambda$27(GxNavigationPage.this);
                return buildDest$lambda$27;
            }
        }), TuplesKt.to("Clear", new Function0() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda58
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildDest$lambda$28;
                buildDest$lambda$28 = GxNavigationPage.buildDest$lambda$28(GxNavigationPage.this);
                return buildDest$lambda$28;
            }
        })};
        LinearLayout linearLayout4 = this.searchCard;
        if (linearLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("searchCard");
        } else {
            linearLayout2 = linearLayout4;
        }
        buttons(pairArr, linearLayout2, true);
        this.statusNote = note("", tab);
        this.suggestBox = box(tab);
        this.destBox = box(tab);
        this.favBox = box(tab);
        this.recentBox = box(tab);
        renderDestAll();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean buildDest$lambda$24$lambda$23(GxNavigationPage this$0, TextView textView, int a, KeyEvent keyEvent) {
        switch (a) {
            case 3:
            case 6:
                this$0.setDestination(null);
                return true;
            default:
                return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildDest$lambda$26(GxNavigationPage this$0) {
        this$0.setDestination(null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildDest$lambda$27(GxNavigationPage this$0) {
        this$0.getMain().removeCallbacks(this$0.searchRunner);
        String v = StringsKt.trim((CharSequence) this$0.getQuery()).toString();
        if (v.length() >= 3) {
            this$0.search(v);
        } else {
            this$0.toast("Type at least 3 letters.");
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildDest$lambda$28(GxNavigationPage this$0) {
        this$0.setQuery("");
        this$0.onInput();
        return Unit.INSTANCE;
    }

    private final void loadNav() {
        if (this.keyBox == null) {
            return;
        }
        getJson("/api/navigation", new Function2() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit loadNav$lambda$32;
                loadNav$lambda$32 = GxNavigationPage.loadNav$lambda$32(GxNavigationPage.this, (String) obj, (JSONObject) obj2);
                return loadNav$lambda$32;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadNav$lambda$32(final GxNavigationPage this$0, String e, final JSONObject nav) {
        if (e != null || nav == null) {
            this$0.navError = e == null ? "Failed to load navigation." : e;
            this$0.navLoading = false;
            this$0.renderDestAll();
            return Unit.INSTANCE;
        }
        this$0.getJson("/api/navigation/favorite", new Function2() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda30
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit loadNav$lambda$32$lambda$31;
                loadNav$lambda$32$lambda$31 = GxNavigationPage.loadNav$lambda$32$lambda$31(this$0, nav, (String) obj, (JSONObject) obj2);
                return loadNav$lambda$32$lambda$31;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadNav$lambda$32$lambda$31(GxNavigationPage this$0, JSONObject $nav, String str, JSONObject fp) {
        List<JSONObject> emptyList;
        JSONArray optJSONArray;
        this$0.mapboxPublic = StringsKt.trim((CharSequence) GxPage.str$default(this$0, $nav, "mapboxPublic", null, 2, null)).toString();
        this$0.mapboxSecret = StringsKt.trim((CharSequence) GxPage.str$default(this$0, $nav, "mapboxSecret", null, 2, null)).toString();
        this$0.language = StringsKt.trim((CharSequence) GxPage.str$default(this$0, $nav, "language", null, 2, null)).toString();
        this$0.isMetric = $nav.optBoolean("isMetric");
        this$0.lastPosition = this$0.coordsOf($nav.optJSONObject("lastPosition"));
        if (fp == null || (optJSONArray = fp.optJSONArray("favorites")) == null || (emptyList = this$0.objects(optJSONArray)) == null) {
            emptyList = CollectionsKt.emptyList();
        }
        this$0.favorites = emptyList;
        this$0.recentDestinations = this$0.parseArr($nav.opt("previousDestinations"));
        JSONObject saved = this$0.parseObj($nav.opt("destination"));
        Pair savedCoords = this$0.coordsOf(saved);
        if (savedCoords != null && saved != null) {
            String savedName = this$0.primaryOf(saved);
            String rid = this$0.str(saved, "routeId", "main");
            this$0.selectedRouteId = new Regex("^(?:main|alt-[1-9]\\d*)$").matches(rid) ? rid : "main";
            JSONObject d = new JSONObject(saved.toString()).put("latitude", savedCoords.getFirst().doubleValue()).put("longitude", savedCoords.getSecond().doubleValue());
            String str2 = savedName;
            if (str2.length() == 0) {
                String labelFor = this$0.labelFor(saved);
                if (labelFor.length() == 0) {
                    labelFor = "Current destination";
                }
                str2 = labelFor;
            }
            d.put("name", str2);
            if (!this$0.navigationStarted || this$0.destination == null || !this$0.sameSpot(this$0.coordsOf(this$0.destination), savedCoords)) {
                this$0.destination = d;
                this$0.navigationStarted = true;
                Intrinsics.checkNotNull(d);
                this$0.setQuery(GxPage.str$default(this$0, d, "name", null, 2, null));
                this$0.suggestions = CollectionsKt.emptyList();
                this$0.previewDestination(d, null);
            }
        } else if (this$0.navigationStarted) {
            this$0.navigationStarted = false;
            this$0.destination = null;
            this$0.routes = CollectionsKt.emptyList();
            this$0.routeSummary = null;
            this$0.selectedRouteId = "main";
        }
        this$0.navError = "";
        this$0.navLoading = false;
        this$0.renderDestAll();
        return Unit.INSTANCE;
    }

    private final void renderDestAll() {
        renderKeyNotice();
        renderStatus();
        renderSuggestions();
        renderDest();
        renderFavorites();
        renderRecents();
    }

    private final void renderKeyNotice() {
        LinearLayout linearLayout = this.keyBox;
        LinearLayout linearLayout2 = null;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("keyBox");
            linearLayout = null;
        }
        linearLayout.removeAllViews();
        if (this.navLoading) {
            LinearLayout linearLayout3 = this.keyBox;
            if (linearLayout3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("keyBox");
                linearLayout3 = null;
            }
            linearLayout3.addView(empty("Loading navigation..."));
            LinearLayout linearLayout4 = this.searchCard;
            if (linearLayout4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("searchCard");
            } else {
                linearLayout2 = linearLayout4;
            }
            linearLayout2.setVisibility(8);
            return;
        }
        LinearLayout linearLayout5 = this.searchCard;
        if (linearLayout5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("searchCard");
            linearLayout5 = null;
        }
        linearLayout5.setVisibility(getHasMapbox() ? 0 : 8);
        if (!getHasMapbox()) {
            LinearLayout linearLayout6 = this.keyBox;
            if (linearLayout6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("keyBox");
            } else {
                linearLayout2 = linearLayout6;
            }
            LinearLayout c = card(linearLayout2);
            GxPage.text$default(this, "Map unavailable until a Mapbox key is configured.", c, 21.0f, CarUi.Companion.getTEXT(), false, 16, null);
            GxPage.text$default(this, "Add a Mapbox public key in App Keys to search destinations and show the map.", c, 0.0f, 0, false, 28, null);
            buttons(new Pair[]{TuplesKt.to("Open App Keys", new Function0() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda59
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit renderKeyNotice$lambda$33;
                    renderKeyNotice$lambda$33 = GxNavigationPage.renderKeyNotice$lambda$33(GxNavigationPage.this);
                    return renderKeyNotice$lambda$33;
                }
            })}, c, true);
            return;
        }
        if (!getHasRoutingKey()) {
            LinearLayout linearLayout7 = this.keyBox;
            if (linearLayout7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("keyBox");
            } else {
                linearLayout2 = linearLayout7;
            }
            LinearLayout c2 = card(linearLayout2);
            text("Secret Mapbox key needed", c2, 21.0f, CarUi.Companion.getWARN(), true);
            GxPage.text$default(this, "The map and destination search only use your public Mapbox key. Add a secret Mapbox key in App Keys before starting navigation so the comma can calculate the on-device route and provide turn desires.", c2, 0.0f, 0, false, 28, null);
            GxPage.buttons$default(this, new Pair[]{TuplesKt.to("Open App Keys", new Function0() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda60
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit renderKeyNotice$lambda$34;
                    renderKeyNotice$lambda$34 = GxNavigationPage.renderKeyNotice$lambda$34(GxNavigationPage.this);
                    return renderKeyNotice$lambda$34;
                }
            })}, c2, false, 4, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderKeyNotice$lambda$33(GxNavigationPage this$0) {
        Function1<? super Integer, Unit> function1 = this$0.selectTab;
        if (function1 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("selectTab");
            function1 = null;
        }
        function1.invoke(2);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderKeyNotice$lambda$34(GxNavigationPage this$0) {
        Function1<? super Integer, Unit> function1 = this$0.selectTab;
        if (function1 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("selectTab");
            function1 = null;
        }
        function1.invoke(2);
        return Unit.INSTANCE;
    }

    private final void renderStatus() {
        String s;
        int dim;
        if (this.searching) {
            s = "Searching...";
        } else {
            s = this.navError.length() > 0 ? this.navError : "";
        }
        TextView textView = this.statusNote;
        TextView textView2 = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("statusNote");
            textView = null;
        }
        textView.setText(s);
        TextView textView3 = this.statusNote;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("statusNote");
            textView3 = null;
        }
        textView3.setVisibility(s.length() == 0 ? 8 : 0);
        TextView textView4 = this.statusNote;
        if (textView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("statusNote");
        } else {
            textView2 = textView4;
        }
        if (!this.searching) {
            if (this.navError.length() > 0) {
                dim = CarUi.Companion.getWARN();
                textView2.setTextColor(dim);
            }
        }
        dim = CarUi.Companion.getDIM();
        textView2.setTextColor(dim);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onInput() {
        this.destination = null;
        this.routeSummary = null;
        this.routes = CollectionsKt.emptyList();
        this.selectedRouteId = "main";
        this.navigationStarted = false;
        this.searchSeq++;
        this.searching = false;
        this.navError = "";
        getMain().removeCallbacks(this.searchRunner);
        String v = StringsKt.trim((CharSequence) getQuery()).toString();
        if (v.length() < 3 || !getHasMapbox()) {
            this.suggestions = CollectionsKt.emptyList();
        } else {
            getMain().postDelayed(this.searchRunner, 350L);
        }
        renderStatus();
        renderSuggestions();
        renderDest();
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x0093, code lost:
    
        if ((129.2d <= r10 && r10 <= 146.0d) == false) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00d4, code lost:
    
        r7 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00b4, code lost:
    
        if ((122.8d <= r10 && r10 <= 131.5d) == false) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00d2, code lost:
    
        if ((136.0d <= r10 && r10 <= 154.0d) != false) goto L60;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final List<Pair<String, String>> searchContext(String q) {
        CharSequence $this$any$iv;
        String $this$any$iv2 = q;
        boolean inJapan = false;
        int i = 0;
        while (true) {
            if (i < $this$any$iv2.length()) {
                char element$iv = $this$any$iv2.charAt(i);
                char it = (12352 > element$iv || element$iv >= 12544) ? (char) 0 : (char) 1;
                if (it != 0) {
                    $this$any$iv = 1;
                    break;
                }
                i++;
            } else {
                $this$any$iv = null;
                break;
            }
        }
        boolean japaneseLang = new Regex("^(?:main_)?ja(?:[-_]|$)", RegexOption.IGNORE_CASE).containsMatchIn(this.language) || Intrinsics.areEqual(Locale.getDefault().getLanguage(), "ja");
        Pair p = this.lastPosition;
        if (p != null) {
            double lat = p.component1().doubleValue();
            double lon = p.component2().doubleValue();
            if (30.0d <= lat && lat <= 45.8d) {
            }
            if (lat >= 24.0d && lat < 30.0d) {
            }
            if (lat >= 20.0d && lat < 30.0d) {
            }
            int i2 = 0;
            if (i2 != 0) {
                inJapan = true;
            }
        }
        ArrayList out = new ArrayList();
        if (inJapan || (($this$any$iv != null || japaneseLang) && p == null)) {
            out.add(TuplesKt.to("language", "ja"));
            out.add(TuplesKt.to("country", "jp"));
        } else if ($this$any$iv != null || japaneseLang) {
            out.add(TuplesKt.to("language", "ja"));
        }
        if (p != null) {
            out.add(TuplesKt.to("proximity", p.getSecond() + "," + p.getFirst()));
        }
        return out;
    }

    private final void search(String value) {
        if (value.length() < 3 || !getHasMapbox()) {
            return;
        }
        this.searchSeq++;
        final int req = this.searchSeq;
        this.searching = true;
        renderStatus();
        SpreadBuilder spreadBuilder = new SpreadBuilder(5);
        spreadBuilder.add(TuplesKt.to("access_token", this.mapboxPublic));
        spreadBuilder.add(TuplesKt.to("session_token", this.sessionToken));
        spreadBuilder.add(TuplesKt.to("q", value));
        spreadBuilder.add(TuplesKt.to("limit", "4"));
        Collection $this$toTypedArray$iv = searchContext(value);
        spreadBuilder.addSpread($this$toTypedArray$iv.toArray(new Pair[0]));
        String u = "https://api.mapbox.com/search/searchbox/v1/suggest?" + qs((Pair[]) spreadBuilder.toArray(new Pair[spreadBuilder.size()]));
        extGet(u, new Function2() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda74
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit search$lambda$38;
                search$lambda$38 = GxNavigationPage.search$lambda$38(req, this, (String) obj, (JSONObject) obj2);
                return search$lambda$38;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit search$lambda$38(int $req, GxNavigationPage this$0, String e, JSONObject o) {
        List<JSONObject> emptyList;
        JSONArray optJSONArray;
        if ($req != this$0.searchSeq) {
            return Unit.INSTANCE;
        }
        this$0.searching = false;
        if (e != null) {
            String str = e;
            if (StringsKt.isBlank(str)) {
                str = "Destination search failed.";
            }
            this$0.navError = str;
        } else {
            if (o == null || (optJSONArray = o.optJSONArray("suggestions")) == null || (emptyList = this$0.objects(optJSONArray)) == null) {
                emptyList = CollectionsKt.emptyList();
            }
            this$0.suggestions = emptyList;
        }
        this$0.renderStatus();
        this$0.renderSuggestions();
        return Unit.INSTANCE;
    }

    private final void renderSuggestions() {
        LinearLayout linearLayout = this.suggestBox;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("suggestBox");
            linearLayout = null;
        }
        linearLayout.removeAllViews();
        if (this.suggestions.isEmpty()) {
            return;
        }
        LinearLayout linearLayout2 = this.suggestBox;
        if (linearLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("suggestBox");
            linearLayout2 = null;
        }
        header("Results", linearLayout2);
        for (final JSONObject p : this.suggestions) {
            String primaryOf = primaryOf(p);
            if (primaryOf.length() == 0) {
                String str$default = GxPage.str$default(this, p, "place_name", null, 2, null);
                if (str$default.length() == 0) {
                    str$default = "Unnamed location";
                }
                primaryOf = str$default;
            }
            String title = primaryOf;
            GxNavigationPage gxNavigationPage = this;
            String secondaryLabel = secondaryLabel(p);
            if (secondaryLabel.length() == 0) {
                String str$default2 = GxPage.str$default(this, p, "place_formatted", null, 2, null);
                if (str$default2.length() == 0) {
                    str$default2 = null;
                }
                secondaryLabel = str$default2;
            }
            String str = secondaryLabel;
            LinearLayout linearLayout3 = this.suggestBox;
            if (linearLayout3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("suggestBox");
                linearLayout3 = null;
            }
            GxPage.Row r = GxPage.row$default(gxNavigationPage, title, str, linearLayout3, 0, 8, null);
            clickable(r, new Function0() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda34
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit renderSuggestions$lambda$43;
                    renderSuggestions$lambda$43 = GxNavigationPage.renderSuggestions$lambda$43(GxNavigationPage.this, p);
                    return renderSuggestions$lambda$43;
                }
            });
            GxPage.Row.button$default(r, "Select", false, new Function0() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda35
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit renderSuggestions$lambda$44;
                    renderSuggestions$lambda$44 = GxNavigationPage.renderSuggestions$lambda$44(GxNavigationPage.this, p);
                    return renderSuggestions$lambda$44;
                }
            }, 2, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderSuggestions$lambda$43(GxNavigationPage this$0, JSONObject $p) {
        this$0.chooseSuggestion($p);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderSuggestions$lambda$44(GxNavigationPage this$0, JSONObject $p) {
        this$0.chooseSuggestion($p);
        return Unit.INSTANCE;
    }

    private final void resolvePlace(final JSONObject place, final Function2<? super String, ? super JSONObject, Unit> then) {
        final String primary = primaryOf(place);
        String str = primary;
        if (str.length() == 0) {
            String labelFor = labelFor(place);
            if (labelFor.length() == 0) {
                labelFor = StringsKt.trim((CharSequence) getQuery()).toString();
            }
            str = labelFor;
        }
        final String placeLabel = str;
        Pair c = coordsOf(place);
        if (c != null) {
            resolvePlace$done(then, primary, this, place, placeLabel, c);
            return;
        }
        String mid = GxPage.str$default(this, place, "mapbox_id", null, 2, null);
        if (!(mid.length() > 0) || !getHasMapbox()) {
            resolvePlace$geocode(this, placeLabel, then, primary, place);
        } else {
            extGet("https://api.mapbox.com/search/searchbox/v1/retrieve/" + enc(mid) + "?" + qs(TuplesKt.to("access_token", this.mapboxPublic), TuplesKt.to("session_token", this.sessionToken)), new Function2() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda53
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Unit resolvePlace$lambda$50;
                    resolvePlace$lambda$50 = GxNavigationPage.resolvePlace$lambda$50(GxNavigationPage.this, then, primary, place, placeLabel, (String) obj, (JSONObject) obj2);
                    return resolvePlace$lambda$50;
                }
            });
        }
    }

    private static final void resolvePlace$done(Function2<? super String, ? super JSONObject, Unit> function2, String primary, GxNavigationPage this$0, JSONObject $place, String placeLabel, Pair<Double, Double> pair) {
        if (pair == null) {
            function2.invoke("Could not determine that location.", null);
            return;
        }
        JSONObject put = new JSONObject().put("latitude", pair.getFirst().doubleValue()).put("longitude", pair.getSecond().doubleValue());
        String str = primary;
        if (str.length() == 0) {
            str = placeLabel;
        }
        JSONObject put2 = put.put("name", str);
        String labelFor = this$0.labelFor($place);
        if (labelFor.length() == 0) {
            labelFor = placeLabel;
        }
        function2.invoke(null, put2.put("place_name", labelFor));
    }

    private static final void resolvePlace$geocode(final GxNavigationPage this$0, final String placeLabel, final Function2<? super String, ? super JSONObject, Unit> function2, final String primary, final JSONObject $place) {
        if (this$0.getHasMapbox()) {
            if (!(placeLabel.length() == 0)) {
                SpreadBuilder spreadBuilder = new SpreadBuilder(3);
                spreadBuilder.add(TuplesKt.to("access_token", this$0.mapboxPublic));
                spreadBuilder.add(TuplesKt.to("q", placeLabel));
                Collection $this$toTypedArray$iv = this$0.searchContext(placeLabel);
                spreadBuilder.addSpread($this$toTypedArray$iv.toArray(new Pair[0]));
                this$0.extGet("https://api.mapbox.com/search/geocode/v6/forward?" + this$0.qs((Pair[]) spreadBuilder.toArray(new Pair[spreadBuilder.size()])), new Function2() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda79
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        Unit resolvePlace$geocode$lambda$49;
                        resolvePlace$geocode$lambda$49 = GxNavigationPage.resolvePlace$geocode$lambda$49(this$0, function2, primary, $place, placeLabel, (String) obj, (JSONObject) obj2);
                        return resolvePlace$geocode$lambda$49;
                    }
                });
                return;
            }
        }
        resolvePlace$done(function2, primary, this$0, $place, placeLabel, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit resolvePlace$geocode$lambda$49(GxNavigationPage this$0, Function2 $then, String $primary, JSONObject $place, String $placeLabel, String str, JSONObject o) {
        JSONArray optJSONArray;
        JSONObject optJSONObject;
        JSONObject optJSONObject2;
        resolvePlace$done($then, $primary, this$0, $place, $placeLabel, this$0.coordsArr((o == null || (optJSONArray = o.optJSONArray("features")) == null || (optJSONObject = optJSONArray.optJSONObject(0)) == null || (optJSONObject2 = optJSONObject.optJSONObject("geometry")) == null) ? null : optJSONObject2.optJSONArray("coordinates")));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit resolvePlace$lambda$50(GxNavigationPage this$0, Function2 $then, String $primary, JSONObject $place, String $placeLabel, String str, JSONObject o) {
        JSONArray optJSONArray;
        JSONObject optJSONObject;
        JSONObject optJSONObject2;
        Pair rc = this$0.coordsArr((o == null || (optJSONArray = o.optJSONArray("features")) == null || (optJSONObject = optJSONArray.optJSONObject(0)) == null || (optJSONObject2 = optJSONObject.optJSONObject("geometry")) == null) ? null : optJSONObject2.optJSONArray("coordinates"));
        if (rc != null) {
            resolvePlace$done($then, $primary, this$0, $place, $placeLabel, rc);
        } else {
            resolvePlace$geocode(this$0, $placeLabel, $then, $primary, $place);
        }
        return Unit.INSTANCE;
    }

    private final void chooseSuggestion(JSONObject place) {
        this.searching = true;
        renderStatus();
        resolvePlace(place, new Function2() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda75
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit chooseSuggestion$lambda$51;
                chooseSuggestion$lambda$51 = GxNavigationPage.chooseSuggestion$lambda$51(GxNavigationPage.this, (String) obj, (JSONObject) obj2);
                return chooseSuggestion$lambda$51;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit chooseSuggestion$lambda$51(GxNavigationPage this$0, String e, JSONObject d) {
        this$0.searching = false;
        if (e != null || d == null) {
            this$0.navError = e == null ? "Could not determine that location." : e;
            this$0.toast(this$0.navError);
            this$0.renderStatus();
            return Unit.INSTANCE;
        }
        this$0.selectedRouteId = "main";
        this$0.routes = CollectionsKt.emptyList();
        this$0.routeSummary = null;
        this$0.navigationStarted = false;
        this$0.destination = d;
        this$0.setQuery(GxPage.str$default(this$0, d, "name", null, 2, null));
        this$0.suggestions = CollectionsKt.emptyList();
        this$0.navError = "";
        this$0.renderStatus();
        this$0.renderSuggestions();
        this$0.renderDest();
        this$0.previewDestination(d, null);
        return Unit.INSTANCE;
    }

    private final void resolveQuery(Function2<? super String, ? super JSONObject, Unit> then) {
        String v = StringsKt.trim((CharSequence) getQuery()).toString();
        if (v.length() == 0) {
            then.invoke("Enter a destination first.", null);
            return;
        }
        JSONObject d = this.destination;
        if (d != null && Intrinsics.areEqual(GxPage.str$default(this, d, "name", null, 2, null), v)) {
            then.invoke(null, d);
            return;
        }
        JSONObject put = new JSONObject().put("name", v);
        Intrinsics.checkNotNullExpressionValue(put, "put(...)");
        resolvePlace(put, then);
    }

    private final void setDestination(JSONObject place) {
        if (!getHasMapbox()) {
            toast("Add a Mapbox public key in App Keys first.");
            return;
        }
        if (!getHasRoutingKey()) {
            alert("Secret Mapbox key needed", "Add a Mapbox secret key in App Keys first. It is required for the comma to calculate the on-device route and provide navigation turn desires.");
            return;
        }
        if (this.loadingRoute) {
            return;
        }
        this.loadingRoute = true;
        renderDest();
        Function2 go = new Function2() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda39
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit destination$lambda$55;
                destination$lambda$55 = GxNavigationPage.setDestination$lambda$55(GxNavigationPage.this, (String) obj, (JSONObject) obj2);
                return destination$lambda$55;
            }
        };
        if (place != null) {
            go.invoke(null, place);
        } else {
            resolveQuery(go);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit setDestination$lambda$55(final GxNavigationPage this$0, String e, JSONObject d) {
        Triple<Double, Double, String> triple;
        if (e != null || d == null) {
            this$0.loadingRoute = false;
            this$0.navError = e == null ? "Enter a destination first." : e;
            this$0.toast(this$0.navError);
            this$0.renderStatus();
            this$0.renderDest();
            return Unit.INSTANCE;
        }
        String str = this$0.selectedRouteId;
        if ((str.length() == 0) && ((triple = this$0.routeSummary) == null || (str = triple.getThird()) == null)) {
            str = "main";
        }
        final String rid = str;
        final JSONObject dest = new JSONObject(d.toString()).put("routeId", rid);
        this$0.destination = dest;
        GxPage.send$default(this$0, "POST", "/api/navigation", dest, 0L, new Function2() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda77
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit destination$lambda$55$lambda$54;
                destination$lambda$55$lambda$54 = GxNavigationPage.setDestination$lambda$55$lambda$54(this$0, dest, rid, (String) obj, (JSONObject) obj2);
                return destination$lambda$55$lambda$54;
            }
        }, 8, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit setDestination$lambda$55$lambda$54(final GxNavigationPage this$0, JSONObject $dest, String $rid, String err, JSONObject jSONObject) {
        this$0.loadingRoute = false;
        if (err != null) {
            this$0.navError = err;
            this$0.toast(err);
            this$0.renderStatus();
            this$0.renderDest();
            return Unit.INSTANCE;
        }
        this$0.navigationStarted = true;
        this$0.navError = "";
        Intrinsics.checkNotNull($dest);
        this$0.setQuery(GxPage.str$default(this$0, $dest, "name", null, 2, null));
        this$0.suggestions = CollectionsKt.emptyList();
        this$0.toast("Destination set.");
        this$0.renderStatus();
        this$0.renderSuggestions();
        this$0.renderDest();
        this$0.previewDestination($dest, $rid);
        this$0.getJson("/api/navigation", new Function2() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit destination$lambda$55$lambda$54$lambda$53;
                destination$lambda$55$lambda$54$lambda$53 = GxNavigationPage.setDestination$lambda$55$lambda$54$lambda$53(this$0, (String) obj, (JSONObject) obj2);
                return destination$lambda$55$lambda$54$lambda$53;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit setDestination$lambda$55$lambda$54$lambda$53(GxNavigationPage this$0, String str, JSONObject nav) {
        if (nav != null) {
            this$0.recentDestinations = this$0.parseArr(nav.opt("previousDestinations"));
            this$0.renderRecents();
        }
        return Unit.INSTANCE;
    }

    private final void cancelNavigation() {
        GxPage.send$default(this, "DELETE", "/api/navigation", null, 0L, new Function2() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda38
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit cancelNavigation$lambda$57;
                cancelNavigation$lambda$57 = GxNavigationPage.cancelNavigation$lambda$57(GxNavigationPage.this, (String) obj, (JSONObject) obj2);
                return cancelNavigation$lambda$57;
            }
        }, 12, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit cancelNavigation$lambda$57(GxNavigationPage this$0, String e, JSONObject jSONObject) {
        if (e != null) {
            String str = e;
            if (StringsKt.isBlank(str)) {
                str = "Could not cancel navigation.";
            }
            this$0.toast(str);
            return Unit.INSTANCE;
        }
        this$0.navigationStarted = false;
        this$0.destination = null;
        this$0.routeSummary = null;
        this$0.routes = CollectionsKt.emptyList();
        this$0.selectedRouteId = "main";
        this$0.suggestions = CollectionsKt.emptyList();
        this$0.setQuery("");
        this$0.toast("Navigation cancelled.");
        this$0.renderSuggestions();
        this$0.renderDest();
        return Unit.INSTANCE;
    }

    private final JSONObject favoriteOf(JSONObject p) {
        Pair c = coordsOf(p);
        Object obj = null;
        if (c == null) {
            return null;
        }
        Iterable $this$firstOrNull$iv = this.favorites;
        Iterator it = $this$firstOrNull$iv.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Object element$iv = it.next();
            JSONObject it2 = (JSONObject) element$iv;
            if (sameSpot(coordsOf(it2), c)) {
                obj = element$iv;
                break;
            }
        }
        return (JSONObject) obj;
    }

    private final void reloadFavorites() {
        getJson("/api/navigation/favorite", new Function2() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda32
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit reloadFavorites$lambda$59;
                reloadFavorites$lambda$59 = GxNavigationPage.reloadFavorites$lambda$59(GxNavigationPage.this, (String) obj, (JSONObject) obj2);
                return reloadFavorites$lambda$59;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit reloadFavorites$lambda$59(GxNavigationPage this$0, String str, JSONObject fp) {
        List<? extends JSONObject> list;
        JSONArray optJSONArray;
        if (fp == null || (optJSONArray = fp.optJSONArray("favorites")) == null || (list = this$0.objects(optJSONArray)) == null) {
            list = this$0.favorites;
        }
        this$0.favorites = list;
        this$0.renderDest();
        this$0.renderFavorites();
        this$0.renderRecents();
        return Unit.INSTANCE;
    }

    private final void toggleFavorite() {
        Pair c;
        String str;
        JSONObject d = this.destination;
        if (d == null || (c = coordsOf(d)) == null) {
            return;
        }
        JSONObject fav = favoriteOf(d);
        if (fav != null) {
            GxPage.send$default(this, "DELETE", "/api/navigation/favorite", fav, 0L, new Function2() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda36
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Unit unit;
                    unit = GxNavigationPage.toggleFavorite$lambda$60(GxNavigationPage.this, (String) obj, (JSONObject) obj2);
                    return unit;
                }
            }, 8, null);
            return;
        }
        JSONObject jSONObject = new JSONObject();
        String str$default = GxPage.str$default(this, d, "name", null, 2, null);
        if (str$default.length() == 0) {
            String query = getQuery();
            if (query.length() == 0) {
                query = "Favorite destination";
            }
            str$default = query;
        }
        JSONObject put = jSONObject.put("name", str$default).put("longitude", c.getSecond().doubleValue()).put("latitude", c.getFirst().doubleValue());
        Triple<Double, Double, String> triple = this.routeSummary;
        if (triple == null || (str = triple.getThird()) == null) {
            str = JSONObject.NULL;
        }
        JSONObject body = put.put("routeId", str);
        GxPage.send$default(this, "POST", "/api/navigation/favorite", body, 0L, new Function2() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda37
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit unit;
                unit = GxNavigationPage.toggleFavorite$lambda$63(GxNavigationPage.this, (String) obj, (JSONObject) obj2);
                return unit;
            }
        }, 8, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit toggleFavorite$lambda$60(GxNavigationPage this$0, String e, JSONObject jSONObject) {
        if (e != null) {
            this$0.toast(e);
        } else {
            this$0.toast("Removed from favorites.");
            this$0.reloadFavorites();
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit toggleFavorite$lambda$63(GxNavigationPage this$0, String e, JSONObject jSONObject) {
        if (e != null) {
            this$0.toast(e);
        } else {
            this$0.toast("Added to favorites.");
            this$0.reloadFavorites();
        }
        return Unit.INSTANCE;
    }

    private final String routeIdOf(int i) {
        return i == 0 ? "main" : "alt-" + i;
    }

    private final String fmtDistance(double m) {
        if (this.isMetric) {
            if (m < 1000.0d) {
                return Math.round(m) + " m";
            }
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            String format = String.format(Locale.US, "%.1f km", Arrays.copyOf(new Object[]{Double.valueOf(m / 1000)}, 1));
            Intrinsics.checkNotNullExpressionValue(format, "format(...)");
            return format;
        }
        double mi = m / 1609.34d;
        if (mi < 0.1d) {
            return Math.round(5280 * mi) + " ft";
        }
        StringCompanionObject stringCompanionObject2 = StringCompanionObject.INSTANCE;
        String format2 = String.format(Locale.US, "%.1f mi", Arrays.copyOf(new Object[]{Double.valueOf(mi)}, 1));
        Intrinsics.checkNotNullExpressionValue(format2, "format(...)");
        return format2;
    }

    private final String fmtDuration(double s) {
        StringBuilder sb;
        double d = 3600;
        int h = (int) (s / d);
        int m = (int) ((s % d) / 60);
        if (h > 0) {
            sb = new StringBuilder();
            sb = sb.append(h).append("h ");
        } else {
            sb = new StringBuilder();
        }
        return sb.append(m).append(" min").toString();
    }

    private final String fmtEta(double s) {
        String format = DateFormat.getTimeInstance(3).format(new Date(System.currentTimeMillis() + ((long) (1000 * s))));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format;
    }

    private final void selectRoute(int i) {
        JSONObject r = (JSONObject) CollectionsKt.getOrNull(this.routes, i);
        if (r == null) {
            return;
        }
        String rid = routeIdOf(i);
        this.selectedRouteId = rid;
        JSONObject jSONObject = this.destination;
        if (jSONObject != null) {
            jSONObject.put("routeId", rid);
        }
        Double valueOf = Double.valueOf(r.optDouble("distance", 0.0d));
        double it = valueOf.doubleValue();
        if (!((Double.isInfinite(it) || Double.isNaN(it)) ? false : true)) {
            valueOf = null;
        }
        Double valueOf2 = Double.valueOf(valueOf != null ? valueOf.doubleValue() : 0.0d);
        Double valueOf3 = Double.valueOf(r.optDouble("duration", 0.0d));
        double it2 = valueOf3.doubleValue();
        Double d = (Double.isInfinite(it2) || Double.isNaN(it2)) ? false : true ? valueOf3 : null;
        this.routeSummary = new Triple<>(valueOf2, Double.valueOf(d != null ? d.doubleValue() : 0.0d), rid);
    }

    private final void previewDestination(final JSONObject place, final String preferredRouteId) {
        Pair from = this.lastPosition;
        final Pair to = coordsOf(place);
        if (from == null || to == null || !getHasMapbox()) {
            this.routeSummary = null;
            this.routes = CollectionsKt.emptyList();
            String str = this.selectedRouteId;
            if (str.length() == 0) {
                str = "main";
            }
            this.selectedRouteId = str;
            renderDest();
            return;
        }
        String u = "https://api.mapbox.com/directions/v5/mapbox/driving-traffic/" + from.getSecond() + "," + from.getFirst() + ";" + to.getSecond() + "," + to.getFirst() + "?" + qs(TuplesKt.to("geometries", "geojson"), TuplesKt.to("overview", "false"), TuplesKt.to("alternatives", "true"), TuplesKt.to("access_token", this.mapboxPublic));
        extGet(u, new Function2() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda46
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit previewDestination$lambda$71;
                previewDestination$lambda$71 = GxNavigationPage.previewDestination$lambda$71(GxNavigationPage.this, place, preferredRouteId, to, (String) obj, (JSONObject) obj2);
                return previewDestination$lambda$71;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Unit previewDestination$lambda$71(GxNavigationPage this$0, JSONObject $place, String $preferredRouteId, Pair $to, String str, JSONObject o) {
        List rs;
        String str2;
        JSONArray optJSONArray;
        boolean z;
        if (this$0.destination != $place) {
            JSONObject it = this$0.destination;
            if (it != null && this$0.sameSpot(this$0.coordsOf(it), $to)) {
                z = true;
                if (!z) {
                    return Unit.INSTANCE;
                }
            }
            z = false;
            if (!z) {
            }
        }
        if (o == null || (optJSONArray = o.optJSONArray("routes")) == null || (rs = this$0.objects(optJSONArray)) == null) {
            rs = CollectionsKt.emptyList();
        }
        String str3 = "main";
        Object obj = null;
        if (!rs.isEmpty()) {
            if ($preferredRouteId == null) {
                String str$default = GxPage.str$default(this$0, $place, "routeId", null, 2, null);
                if (str$default.length() == 0) {
                    String str4 = this$0.selectedRouteId;
                    if (!(str4.length() == 0)) {
                        str3 = str4;
                    }
                    str$default = str3;
                }
                str2 = str$default;
            } else {
                str2 = $preferredRouteId;
            }
            String want = str2;
            Iterable $this$firstOrNull$iv = CollectionsKt.getIndices(rs);
            Iterator<Integer> it2 = $this$firstOrNull$iv.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                Object element$iv = it2.next();
                if (Intrinsics.areEqual(this$0.routeIdOf(((Number) element$iv).intValue()), want)) {
                    obj = element$iv;
                    break;
                }
            }
            Integer num = (Integer) obj;
            int idx = num != null ? num.intValue() : 0;
            this$0.routes = rs;
            this$0.selectRoute(idx);
        } else {
            this$0.routeSummary = null;
            this$0.routes = CollectionsKt.emptyList();
            this$0.selectedRouteId = "main";
        }
        this$0.renderDest();
        return Unit.INSTANCE;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void renderDest() {
        int i;
        int i2;
        Object obj;
        ViewGroup viewGroup;
        if (this.destBox == null) {
            return;
        }
        LinearLayout linearLayout = this.destBox;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("destBox");
            linearLayout = null;
        }
        linearLayout.removeAllViews();
        JSONObject jSONObject = this.destination;
        if (jSONObject == null) {
            return;
        }
        ViewGroup viewGroup2 = this.destBox;
        if (viewGroup2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("destBox");
            viewGroup2 = null;
        }
        String str = "Destination";
        header("Destination", viewGroup2);
        ViewGroup viewGroup3 = this.destBox;
        if (viewGroup3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("destBox");
            viewGroup3 = null;
        }
        LinearLayout card = card(viewGroup3);
        String str$default = GxPage.str$default(this, jSONObject, "name", null, 2, null);
        if ((str$default.length() == 0) != false) {
            String query = getQuery();
            if ((query.length() == 0) == false) {
                str = query;
            }
            str$default = str;
        }
        text(str$default, card, 24.0f, CarUi.Companion.getTEXT(), true);
        String str$default2 = GxPage.str$default(this, jSONObject, "place_name", null, 2, null);
        if ((str$default2.length() > 0) != false && !StringsKt.equals(str$default2, GxPage.str$default(this, jSONObject, "name", null, 2, null), true)) {
            GxPage.text$default(this, str$default2, card, 0.0f, 0, false, 28, null);
        }
        Triple<Double, Double, String> triple = this.routeSummary;
        if (triple != null) {
            i = 0;
            i2 = 1;
            text("Distance  " + fmtDistance(triple.getFirst().doubleValue()) + "     Duration  " + fmtDuration(triple.getSecond().doubleValue()) + "     ETA  " + fmtEta(triple.getSecond().doubleValue()), card, 19.0f, CarUi.Companion.getACCENT(), true);
        } else {
            i = 0;
            i2 = 1;
            if (this.lastPosition == null) {
                GxPage.text$default(this, "The car's position is unknown, so no route preview is shown.", card, 0.0f, 0, false, 28, null);
            }
        }
        int i3 = favoriteOf(jSONObject) != null ? i2 : i;
        ArrayList arrayList = new ArrayList();
        if (this.navigationStarted) {
            arrayList.add(TuplesKt.to("Cancel Navigation", new Function0() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda63
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit renderDest$lambda$74;
                    renderDest$lambda$74 = GxNavigationPage.renderDest$lambda$74(GxNavigationPage.this);
                    return renderDest$lambda$74;
                }
            }));
        } else {
            arrayList.add(TuplesKt.to(this.loadingRoute ? "Calculating..." : "Start Navigation", new Function0() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda64
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit renderDest$lambda$75;
                    renderDest$lambda$75 = GxNavigationPage.renderDest$lambda$75(GxNavigationPage.this);
                    return renderDest$lambda$75;
                }
            }));
        }
        arrayList.add(TuplesKt.to(i3 != 0 ? "Remove from favorites" : "Add to favorites", new Function0() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda65
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit renderDest$lambda$76;
                renderDest$lambda$76 = GxNavigationPage.renderDest$lambda$76(GxNavigationPage.this);
                return renderDest$lambda$76;
            }
        }));
        Pair[] pairArr = (Pair[]) arrayList.toArray(new Pair[i]);
        LinearLayout buttons = buttons((Pair[]) Arrays.copyOf(pairArr, pairArr.length), card, (this.navigationStarted ? 1 : 0) ^ i2);
        if (this.navigationStarted) {
            View childAt = buttons.getChildAt(i);
            TextView textView = childAt instanceof TextView ? (TextView) childAt : null;
            if (textView != null) {
                textView.setTextColor(CarUi.Companion.getWARN());
            }
        } else {
            View childAt2 = buttons.getChildAt(i);
            setOn(childAt2 instanceof TextView ? (TextView) childAt2 : null, (this.loadingRoute || !getHasRoutingKey()) ? i : i2);
        }
        if (this.routes.size() > i2) {
            List<? extends JSONObject> list = this.routes;
            int i4 = 0;
            List<String> arrayList2 = new ArrayList<>(CollectionsKt.collectionSizeOrDefault(list, 10));
            List<? extends JSONObject> list2 = list;
            int i5 = 0;
            int i6 = 0;
            for (Object obj2 : list2) {
                int i7 = i6 + 1;
                if (i6 < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                JSONObject jSONObject2 = (JSONObject) obj2;
                arrayList2.add("Route " + (i6 + 1) + "\n" + fmtDistance(jSONObject2.optDouble("distance", 0.0d)) + " · " + fmtDuration(jSONObject2.optDouble("duration", 0.0d)));
                i6 = i7;
                list = list;
                list2 = list2;
                i5 = i5;
                i4 = i4;
            }
            List<String> list3 = arrayList2;
            IntRange indices = CollectionsKt.getIndices(this.routes);
            Iterator<Integer> it = indices.iterator();
            while (true) {
                if (!it.hasNext()) {
                    obj = null;
                    break;
                }
                obj = it.next();
                IntRange intRange = indices;
                if (Intrinsics.areEqual(routeIdOf(((Number) obj).intValue()), this.selectedRouteId)) {
                    break;
                } else {
                    indices = intRange;
                }
            }
            Integer num = (Integer) obj;
            int intValue = num != null ? num.intValue() : 0;
            String str2 = this.navigationStarted ? "Picking another route here only changes the preview; tap Start Navigation again to send it." : null;
            ViewGroup viewGroup4 = this.destBox;
            if (viewGroup4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("destBox");
                viewGroup = null;
            } else {
                viewGroup = viewGroup4;
            }
            choice("Choose a route", str2, list3, intValue, viewGroup, new Function1() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda67
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj3) {
                    Unit renderDest$lambda$79;
                    renderDest$lambda$79 = GxNavigationPage.renderDest$lambda$79(GxNavigationPage.this, ((Integer) obj3).intValue());
                    return renderDest$lambda$79;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderDest$lambda$74(GxNavigationPage this$0) {
        this$0.cancelNavigation();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderDest$lambda$75(GxNavigationPage this$0) {
        this$0.setDestination(this$0.destination);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderDest$lambda$76(GxNavigationPage this$0) {
        this$0.toggleFavorite();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderDest$lambda$79(GxNavigationPage this$0, int i) {
        this$0.selectRoute(i);
        if (this$0.navigationStarted) {
            this$0.navigationStarted = false;
        }
        this$0.renderDest();
        return Unit.INSTANCE;
    }

    private final void renderFavorites() {
        if (this.favBox == null) {
            return;
        }
        LinearLayout linearLayout = this.favBox;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("favBox");
            linearLayout = null;
        }
        linearLayout.removeAllViews();
        if (this.navLoading || this.favorites.isEmpty()) {
            return;
        }
        LinearLayout linearLayout2 = this.favBox;
        if (linearLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("favBox");
            linearLayout2 = null;
        }
        header("Favorite destinations", linearLayout2);
        Iterable $this$sortedBy$iv = this.favorites;
        for (final JSONObject f : CollectionsKt.sortedWith($this$sortedBy$iv, new Comparator() { // from class: com.starpilot.starview.GxNavigationPage$renderFavorites$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                JSONObject it = (JSONObject) t;
                String lowerCase = GxPage.str$default(GxNavigationPage.this, it, "name", null, 2, null).toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                JSONObject it2 = (JSONObject) t2;
                String lowerCase2 = GxPage.str$default(GxNavigationPage.this, it2, "name", null, 2, null).toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase2, "toLowerCase(...)");
                return ComparisonsKt.compareValues(lowerCase, lowerCase2);
            }
        })) {
            String tag = f.optBoolean("is_home") ? "Home: " : f.optBoolean("is_work") ? "Work: " : "";
            GxNavigationPage gxNavigationPage = this;
            String str$default = GxPage.str$default(this, f, "name", null, 2, null);
            if (str$default.length() == 0) {
                String str$default2 = GxPage.str$default(this, f, "place_name", null, 2, null);
                if (str$default2.length() == 0) {
                    str$default2 = "Favorite destination";
                }
                str$default = str$default2;
            }
            String str = tag + ((Object) str$default);
            String secondaryLabel = secondaryLabel(f);
            if (secondaryLabel.length() == 0) {
                secondaryLabel = null;
            }
            String str2 = secondaryLabel;
            LinearLayout linearLayout3 = this.favBox;
            if (linearLayout3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("favBox");
                linearLayout3 = null;
            }
            GxPage.Row r = GxPage.row$default(gxNavigationPage, str, str2, linearLayout3, 0, 8, null);
            r.button("Go", true, new Function0() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda41
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit renderFavorites$lambda$84;
                    renderFavorites$lambda$84 = GxNavigationPage.renderFavorites$lambda$84(GxNavigationPage.this, f);
                    return renderFavorites$lambda$84;
                }
            });
            GxPage.Row.button$default(r, "Rename", false, new Function0() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda42
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit renderFavorites$lambda$85;
                    renderFavorites$lambda$85 = GxNavigationPage.renderFavorites$lambda$85(GxNavigationPage.this, f);
                    return renderFavorites$lambda$85;
                }
            }, 2, null);
            GxPage.Row.button$default(r, "Home / Work", false, new Function0() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda43
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit renderFavorites$lambda$86;
                    renderFavorites$lambda$86 = GxNavigationPage.renderFavorites$lambda$86(GxNavigationPage.this, f);
                    return renderFavorites$lambda$86;
                }
            }, 2, null);
            GxPage.Row.button$default(r, "Delete", false, new Function0() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda45
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit renderFavorites$lambda$89;
                    renderFavorites$lambda$89 = GxNavigationPage.renderFavorites$lambda$89(GxNavigationPage.this, f);
                    return renderFavorites$lambda$89;
                }
            }, 2, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderFavorites$lambda$84(GxNavigationPage this$0, JSONObject $f) {
        this$0.chooseSuggestion($f);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderFavorites$lambda$85(GxNavigationPage this$0, JSONObject $f) {
        this$0.renameFavorite($f);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderFavorites$lambda$86(GxNavigationPage this$0, JSONObject $f) {
        this$0.homeWork($f);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderFavorites$lambda$89(final GxNavigationPage this$0, final JSONObject $f) {
        this$0.confirm("Remove Favorite", "Are you sure you want to remove " + GxPage.str$default(this$0, $f, "name", null, 2, null) + " from your favorites?", "Remove", new Function0() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda62
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit renderFavorites$lambda$89$lambda$88;
                renderFavorites$lambda$89$lambda$88 = GxNavigationPage.renderFavorites$lambda$89$lambda$88($f, this$0);
                return renderFavorites$lambda$89$lambda$88;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderFavorites$lambda$89$lambda$88(JSONObject $f, final GxNavigationPage this$0) {
        JSONObject jSONObject = new JSONObject();
        Object opt = $f.opt("id");
        if (opt == null) {
            opt = JSONObject.NULL;
        }
        JSONObject put = jSONObject.put("id", opt);
        Object opt2 = $f.opt("name");
        if (opt2 == null) {
            opt2 = JSONObject.NULL;
        }
        JSONObject put2 = put.put("name", opt2);
        Object opt3 = $f.opt("latitude");
        if (opt3 == null) {
            opt3 = JSONObject.NULL;
        }
        JSONObject put3 = put2.put("latitude", opt3);
        Object opt4 = $f.opt("longitude");
        if (opt4 == null) {
            opt4 = JSONObject.NULL;
        }
        JSONObject put4 = put3.put("longitude", opt4);
        Object opt5 = $f.opt("routeId");
        if (opt5 == null) {
            opt5 = JSONObject.NULL;
        }
        JSONObject body = put4.put("routeId", opt5);
        GxPage.send$default(this$0, "DELETE", "/api/navigation/favorite", body, 0L, new Function2() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda78
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit renderFavorites$lambda$89$lambda$88$lambda$87;
                renderFavorites$lambda$89$lambda$88$lambda$87 = GxNavigationPage.renderFavorites$lambda$89$lambda$88$lambda$87(this$0, (String) obj, (JSONObject) obj2);
                return renderFavorites$lambda$89$lambda$88$lambda$87;
            }
        }, 8, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderFavorites$lambda$89$lambda$88$lambda$87(GxNavigationPage this$0, String e, JSONObject jSONObject) {
        if (e != null) {
            this$0.toast("Failed to remove favorite...");
        } else {
            this$0.toast("Favorite removed!");
            this$0.reloadFavorites();
        }
        return Unit.INSTANCE;
    }

    private final void renameFavorite(final JSONObject f) {
        final String old = GxPage.str$default(this, f, "name", null, 2, null);
        GxPage.prompt$default(this, "Rename Favorite", "Name", old, 0, "Rename " + old + " to:", new Function1() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit renameFavorite$lambda$91;
                renameFavorite$lambda$91 = GxNavigationPage.renameFavorite$lambda$91(old, f, this, (String) obj);
                return renameFavorite$lambda$91;
            }
        }, 8, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renameFavorite$lambda$91(String $old, JSONObject $f, final GxNavigationPage this$0, String v) {
        Intrinsics.checkNotNullParameter(v, "v");
        String n = StringsKt.trim((CharSequence) v).toString();
        if ((n.length() == 0) || Intrinsics.areEqual(n, $old)) {
            return Unit.INSTANCE;
        }
        JSONObject jSONObject = new JSONObject();
        Object opt = $f.opt("id");
        if (opt == null) {
            opt = JSONObject.NULL;
        }
        JSONObject put = jSONObject.put("id", opt);
        Object opt2 = $f.opt("routeId");
        if (opt2 == null) {
            opt2 = JSONObject.NULL;
        }
        JSONObject body = put.put("routeId", opt2).put("name", n);
        GxPage.send$default(this$0, "POST", "/api/navigation/favorite/rename", body, 0L, new Function2() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit renameFavorite$lambda$91$lambda$90;
                renameFavorite$lambda$91$lambda$90 = GxNavigationPage.renameFavorite$lambda$91$lambda$90(this$0, (String) obj, (JSONObject) obj2);
                return renameFavorite$lambda$91$lambda$90;
            }
        }, 8, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renameFavorite$lambda$91$lambda$90(GxNavigationPage this$0, String e, JSONObject jSONObject) {
        if (e != null) {
            this$0.toast("Failed to rename favorite...");
        } else {
            this$0.toast("Favorite renamed!");
            this$0.reloadFavorites();
        }
        return Unit.INSTANCE;
    }

    private final void homeWork(final JSONObject f) {
        final boolean home = f.optBoolean("is_home");
        final boolean work = f.optBoolean("is_work");
        String str$default = GxPage.str$default(this, f, "name", null, 2, null);
        String[] strArr = new String[2];
        strArr[0] = home ? "Remove Home" : "Set as Home";
        strArr[1] = work ? "Remove Work" : "Set as Work";
        pick(str$default, CollectionsKt.listOf((Object[]) strArr), new Function1() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda61
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit homeWork$lambda$93;
                homeWork$lambda$93 = GxNavigationPage.homeWork$lambda$93(f, home, work, this, ((Integer) obj).intValue());
                return homeWork$lambda$93;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit homeWork$lambda$93(JSONObject $f, boolean $home, boolean $work, final GxNavigationPage this$0, final int i) {
        final String msg;
        JSONObject jSONObject = new JSONObject();
        Object opt = $f.opt("routeId");
        if (opt == null) {
            opt = JSONObject.NULL;
        }
        JSONObject put = jSONObject.put("routeId", opt);
        Object opt2 = $f.opt("id");
        if (opt2 == null) {
            opt2 = JSONObject.NULL;
        }
        JSONObject body = put.put("id", opt2);
        if (i == 0) {
            if ($home) {
                body.put("is_home", false);
                msg = "Home location removed!";
            } else {
                body.put("is_home", true);
                if ($work) {
                    body.put("is_work", false);
                }
                msg = "Home location set!";
            }
        } else if ($work) {
            body.put("is_work", false);
            msg = "Work location removed!";
        } else {
            body.put("is_work", true);
            if ($home) {
                body.put("is_home", false);
            }
            msg = "Work location set!";
        }
        GxPage.send$default(this$0, "POST", "/api/navigation/favorite/rename", body, 0L, new Function2() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda40
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit homeWork$lambda$93$lambda$92;
                homeWork$lambda$93$lambda$92 = GxNavigationPage.homeWork$lambda$93$lambda$92(this$0, i, msg, (String) obj, (JSONObject) obj2);
                return homeWork$lambda$93$lambda$92;
            }
        }, 8, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit homeWork$lambda$93$lambda$92(GxNavigationPage this$0, int $i, String $msg, String e, JSONObject jSONObject) {
        if (e != null) {
            this$0.toast("Failed to update " + ($i == 0 ? "home" : "work") + " location...");
        } else {
            this$0.toast($msg);
            this$0.reloadFavorites();
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x0122  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0125 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void renderRecents() {
        Iterable $this$filter$iv;
        String key;
        HashSet seen;
        boolean z;
        boolean z2;
        String lowerCase;
        if (this.recentBox == null) {
            return;
        }
        LinearLayout linearLayout = this.recentBox;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recentBox");
            linearLayout = null;
        }
        linearLayout.removeAllViews();
        if (this.navLoading) {
            return;
        }
        HashSet seen2 = new HashSet();
        for (JSONObject f : this.favorites) {
            Pair c = coordsOf(f);
            if (c != null) {
                lowerCase = c.getFirst() + ":" + c.getSecond();
            } else {
                lowerCase = labelFor(f).toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
            }
            seen2.add(lowerCase);
        }
        Iterable $this$filter$iv2 = this.recentDestinations;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv2) {
            JSONObject p = (JSONObject) element$iv$iv;
            Pair c2 = coordsOf(p);
            if (c2 != null) {
                $this$filter$iv = $this$filter$iv2;
                key = c2.getFirst() + ":" + c2.getSecond();
            } else {
                $this$filter$iv = $this$filter$iv2;
                key = labelFor(p).toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(key, "toLowerCase(...)");
            }
            if ((key.length() > 0) && seen2.add(key)) {
                Iterable $this$none$iv = this.favorites;
                seen = seen2;
                if (($this$none$iv instanceof Collection) && ((Collection) $this$none$iv).isEmpty()) {
                    z2 = true;
                } else {
                    Iterator it = $this$none$iv.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            z2 = true;
                            break;
                        }
                        Object element$iv = it.next();
                        Iterator it2 = it;
                        JSONObject it3 = (JSONObject) element$iv;
                        String key2 = key;
                        if (sameSpot(coordsOf(it3), c2)) {
                            z2 = false;
                            break;
                        } else {
                            it = it2;
                            key = key2;
                        }
                    }
                }
                if (z2) {
                    z = true;
                    if (!z) {
                        destination$iv$iv.add(element$iv$iv);
                    }
                    $this$filter$iv2 = $this$filter$iv;
                    seen2 = seen;
                }
            } else {
                seen = seen2;
            }
            z = false;
            if (!z) {
            }
            $this$filter$iv2 = $this$filter$iv;
            seen2 = seen;
        }
        List<JSONObject> list = CollectionsKt.take((List) destination$iv$iv, 10);
        if (list.isEmpty()) {
            return;
        }
        LinearLayout linearLayout2 = this.recentBox;
        if (linearLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recentBox");
            linearLayout2 = null;
        }
        header("Recent destinations", linearLayout2);
        for (final JSONObject p2 : list) {
            GxNavigationPage gxNavigationPage = this;
            String str$default = GxPage.str$default(this, p2, "name", null, 2, null);
            if (str$default.length() == 0) {
                String str$default2 = GxPage.str$default(this, p2, "place_name", null, 2, null);
                if (str$default2.length() == 0) {
                    str$default2 = "Destination";
                }
                str$default = str$default2;
            }
            String str = str$default;
            String secondaryLabel = secondaryLabel(p2);
            if (secondaryLabel.length() == 0) {
                secondaryLabel = null;
            }
            String str2 = secondaryLabel;
            LinearLayout linearLayout3 = this.recentBox;
            if (linearLayout3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("recentBox");
                linearLayout3 = null;
            }
            GxPage.Row r = GxPage.row$default(gxNavigationPage, str, str2, linearLayout3, 0, 8, null);
            clickable(r, new Function0() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda15
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit renderRecents$lambda$99;
                    renderRecents$lambda$99 = GxNavigationPage.renderRecents$lambda$99(GxNavigationPage.this, p2);
                    return renderRecents$lambda$99;
                }
            });
            GxPage.Row.button$default(r, "Go", false, new Function0() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda16
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit renderRecents$lambda$100;
                    renderRecents$lambda$100 = GxNavigationPage.renderRecents$lambda$100(GxNavigationPage.this, p2);
                    return renderRecents$lambda$100;
                }
            }, 2, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderRecents$lambda$99(GxNavigationPage this$0, JSONObject $p) {
        this$0.chooseSuggestion($p);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderRecents$lambda$100(GxNavigationPage this$0, JSONObject $p) {
        this$0.chooseSuggestion($p);
        return Unit.INSTANCE;
    }

    private final boolean getSelectionDirty() {
        return !Intrinsics.areEqual(CollectionsKt.toSet(this.selectedDraft), this.selectedSaved);
    }

    private final boolean getScheduleDirty() {
        return !Intrinsics.areEqual(this.scheduleDraft, this.scheduleSaved);
    }

    private final boolean isDownloading() {
        return this.mapsStatus.optBoolean("downloading");
    }

    private final boolean isOnroad() {
        return this.mapsStatus.optBoolean("isOnroad");
    }

    private final String gxBytes(double v) {
        String format;
        if (v <= 0.0d) {
            return "0 MB";
        }
        List units = CollectionsKt.listOf((Object[]) new String[]{"B", "KB", "MB", "GB", "TB"});
        int i = Math.min((int) (Math.log(v) / Math.log(1024.0d)), units.size() - 1);
        double s = v / Math.pow(1024.0d, i);
        if (s >= 10.0d || i == 0) {
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            format = String.format(Locale.US, "%.0f", Arrays.copyOf(new Object[]{Double.valueOf(s)}, 1));
        } else {
            StringCompanionObject stringCompanionObject2 = StringCompanionObject.INSTANCE;
            format = String.format(Locale.US, "%.2f", Arrays.copyOf(new Object[]{Double.valueOf(s)}, 1));
        }
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format + " " + units.get(i);
    }

    private final String gxDuration(double sec) {
        long v = Math.max(0L, Math.round(sec));
        if (v < 60) {
            return v + "s";
        }
        long j = 60;
        long m = v / j;
        if (m < 60) {
            return m + "m " + (v % j) + "s";
        }
        return (m / j) + "h " + (m % j) + "m";
    }

    private final TextView statRow(String title, LinearLayout into) {
        return GxPage.row$default(this, title, null, into, 0, 8, null).value("…");
    }

    private final TextView noteIn(String s, LinearLayout into) {
        TextView $this$noteIn_u24lambda_u24101 = GxPage.text$default(this, s, into, 15.0f, CarUi.Companion.getWARN(), false, 16, null);
        $this$noteIn_u24lambda_u24101.setVisibility(8);
        return $this$noteIn_u24lambda_u24101;
    }

    private final void buildMaps(LinearLayout tab) {
        TextView $this$buildMaps_u24lambda_u24102 = note("", tab);
        $this$buildMaps_u24lambda_u24102.setTextColor(CarUi.Companion.getWARN());
        $this$buildMaps_u24lambda_u24102.setVisibility(8);
        this.mapsErrorNote = $this$buildMaps_u24lambda_u24102;
        header("Offline Maps", tab);
        note("Select regions, start downloads, and manage offline maps entirely from Galaxy.", tab);
        this.vDownloader = statRow("Downloader", tab);
        this.vSaved = statRow("Saved Regions", tab);
        this.vEstimate = statRow("Additional Storage", tab);
        this.vLastUpdate = statRow("Last Updated", tab);
        this.vStorage = statRow("Storage Used", tab);
        this.nOnroad = noteIn("Map downloads and removal are blocked while driving.", tab);
        this.nSelDirty = noteIn("You have unsaved region changes. Downloading now will use the current selection.", tab);
        this.nSchDirty = noteIn("You have an unsaved schedule change. Downloading now will also apply it.", tab);
        this.mapsBar = buttons(new Pair[]{TuplesKt.to("Download Maps", new Function0() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda25
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildMaps$lambda$103;
                buildMaps$lambda$103 = GxNavigationPage.buildMaps$lambda$103(GxNavigationPage.this);
                return buildMaps$lambda$103;
            }
        }), TuplesKt.to("Remove Maps", new Function0() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda26
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildMaps$lambda$104;
                buildMaps$lambda$104 = GxNavigationPage.buildMaps$lambda$104(GxNavigationPage.this);
                return buildMaps$lambda$104;
            }
        }), TuplesKt.to("Save Selection", new Function0() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda27
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildMaps$lambda$105;
                buildMaps$lambda$105 = GxNavigationPage.buildMaps$lambda$105(GxNavigationPage.this);
                return buildMaps$lambda$105;
            }
        }), TuplesKt.to("Reset", new Function0() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda28
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildMaps$lambda$106;
                buildMaps$lambda$106 = GxNavigationPage.buildMaps$lambda$106(GxNavigationPage.this);
                return buildMaps$lambda$106;
            }
        }), TuplesKt.to("Clear All", new Function0() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda29
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildMaps$lambda$107;
                buildMaps$lambda$107 = GxNavigationPage.buildMaps$lambda$107(GxNavigationPage.this);
                return buildMaps$lambda$107;
            }
        })}, tab, true);
        LinearLayout linearLayout = this.mapsBar;
        LinearLayout linearLayout2 = null;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mapsBar");
            linearLayout = null;
        }
        View childAt = linearLayout.getChildAt(1);
        TextView textView = childAt instanceof TextView ? (TextView) childAt : null;
        if (textView != null) {
            textView.setTextColor(CarUi.Companion.getWARN());
        }
        LinearLayout $this$buildMaps_u24lambda_u24108 = card(tab);
        $this$buildMaps_u24lambda_u24108.setVisibility(8);
        this.progCard = $this$buildMaps_u24lambda_u24108;
        LinearLayout linearLayout3 = this.progCard;
        if (linearLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("progCard");
            linearLayout3 = null;
        }
        this.progTitle = text("Download Progress", linearLayout3, 21.0f, CarUi.Companion.getTEXT(), true);
        LinearLayout linearLayout4 = this.progCard;
        if (linearLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("progCard");
            linearLayout4 = null;
        }
        this.progBar = progress(linearLayout4);
        GxNavigationPage gxNavigationPage = this;
        LinearLayout linearLayout5 = this.progCard;
        if (linearLayout5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("progCard");
            linearLayout5 = null;
        }
        this.pEstimate = GxPage.text$default(gxNavigationPage, "", linearLayout5, 0.0f, 0, false, 28, null);
        GxNavigationPage gxNavigationPage2 = this;
        LinearLayout linearLayout6 = this.progCard;
        if (linearLayout6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("progCard");
            linearLayout6 = null;
        }
        this.pStored = GxPage.text$default(gxNavigationPage2, "", linearLayout6, 0.0f, 0, false, 28, null);
        GxNavigationPage gxNavigationPage3 = this;
        LinearLayout linearLayout7 = this.progCard;
        if (linearLayout7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("progCard");
            linearLayout7 = null;
        }
        this.pFiles = GxPage.text$default(gxNavigationPage3, "", linearLayout7, 0.0f, 0, false, 28, null);
        GxNavigationPage gxNavigationPage4 = this;
        LinearLayout linearLayout8 = this.progCard;
        if (linearLayout8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("progCard");
            linearLayout8 = null;
        }
        this.pEta = GxPage.text$default(gxNavigationPage4, "", linearLayout8, 0.0f, 0, false, 28, null);
        GxNavigationPage gxNavigationPage5 = this;
        LinearLayout linearLayout9 = this.progCard;
        if (linearLayout9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("progCard");
        } else {
            linearLayout2 = linearLayout9;
        }
        this.pRegion = GxPage.text$default(gxNavigationPage5, "", linearLayout2, 0.0f, 0, false, 28, null);
        header("Regions & Schedule", tab);
        LinearLayout sc = card(tab);
        GxPage.text$default(this, "Search", sc, 21.0f, CarUi.Companion.getTEXT(), false, 16, null);
        EditText se = field("Filter by name or code");
        se.addTextChangedListener(new TextWatcher() { // from class: com.starpilot.starview.GxNavigationPage$buildMaps$8
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int a, int b, int c) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int a, int b, int c) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                GxNavigationPage gxNavigationPage6 = GxNavigationPage.this;
                String obj = s != null ? s.toString() : null;
                if (obj == null) {
                    obj = "";
                }
                gxNavigationPage6.mapsSearch = obj;
                GxNavigationPage.this.renderCatalog();
            }
        });
        LinearLayout.LayoutParams $this$buildMaps_u24lambda_u24109 = new LinearLayout.LayoutParams(-1, -2);
        $this$buildMaps_u24lambda_u24109.topMargin = getUi().dp(8);
        $this$buildMaps_u24lambda_u24109.bottomMargin = getUi().dp(6);
        Unit unit = Unit.INSTANCE;
        sc.addView(se, $this$buildMaps_u24lambda_u24109);
        this.scheduleBox = box(tab);
        LinearLayout sr = card(tab);
        GxPage.text$default(this, "Selected Regions", sr, 21.0f, CarUi.Companion.getTEXT(), false, 16, null);
        this.selRegionsText = GxPage.text$default(this, "No regions selected.", sr, 0.0f, 0, false, 28, null);
        this.schedNote = note("", tab);
        this.catalogBox = box(tab);
        loadCatalog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildMaps$lambda$103(GxNavigationPage this$0) {
        if (this$0.isDownloading()) {
            this$0.cancelDownload();
        } else {
            this$0.startDownload();
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildMaps$lambda$104(GxNavigationPage this$0) {
        this$0.removeMaps();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildMaps$lambda$105(GxNavigationPage this$0) {
        this$0.saveSelection();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildMaps$lambda$106(GxNavigationPage this$0) {
        this$0.selectedDraft.clear();
        this$0.selectedDraft.addAll(this$0.selectedSaved);
        this$0.scheduleDraft = this$0.scheduleSaved;
        this$0.paintMaps();
        this$0.renderCatalog();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildMaps$lambda$107(GxNavigationPage this$0) {
        this$0.selectedDraft.clear();
        this$0.paintMaps();
        return Unit.INSTANCE;
    }

    private final void loadCatalog() {
        getJson("/api/maps/catalog", new Function2() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda44
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit loadCatalog$lambda$111;
                loadCatalog$lambda$111 = GxNavigationPage.loadCatalog$lambda$111(GxNavigationPage.this, (String) obj, (JSONObject) obj2);
                return loadCatalog$lambda$111;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadCatalog$lambda$111(GxNavigationPage this$0, String e, JSONObject o) {
        List<JSONObject> emptyList;
        List<Pair<String, String>> emptyList2;
        List<JSONObject> emptyList3;
        List<JSONObject> emptyList4;
        Iterable objects;
        this$0.mapsLoadingCatalog = false;
        if (e != null || o == null) {
            this$0.mapsError = e == null ? "Failed to load the map catalog." : e;
        } else {
            JSONArray optJSONArray = o.optJSONArray("sections");
            if (optJSONArray == null || (emptyList = this$0.objects(optJSONArray)) == null) {
                emptyList = CollectionsKt.emptyList();
            }
            this$0.mapsSections = emptyList;
            JSONArray optJSONArray2 = o.optJSONArray("scheduleOptions");
            if (optJSONArray2 == null || (objects = this$0.objects(optJSONArray2)) == null) {
                emptyList2 = CollectionsKt.emptyList();
            } else {
                Iterable $this$map$iv = objects;
                Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                for (Object item$iv$iv : $this$map$iv) {
                    JSONObject it = (JSONObject) item$iv$iv;
                    destination$iv$iv.add(TuplesKt.to(it.opt("value").toString(), GxPage.str$default(this$0, it, "label", null, 2, null)));
                }
                emptyList2 = (List) destination$iv$iv;
            }
            this$0.scheduleOptions = emptyList2;
            this$0.tokenLabels.clear();
            for (JSONObject s : this$0.mapsSections) {
                JSONArray optJSONArray3 = s.optJSONArray("groups");
                if (optJSONArray3 == null || (emptyList3 = this$0.objects(optJSONArray3)) == null) {
                    emptyList3 = CollectionsKt.emptyList();
                }
                for (JSONObject g : emptyList3) {
                    JSONArray optJSONArray4 = g.optJSONArray("regions");
                    if (optJSONArray4 == null || (emptyList4 = this$0.objects(optJSONArray4)) == null) {
                        emptyList4 = CollectionsKt.emptyList();
                    }
                    for (JSONObject r : emptyList4) {
                        this$0.tokenLabels.put(GxPage.str$default(this$0, r, "token", null, 2, null), GxPage.str$default(this$0, r, "label", null, 2, null));
                    }
                }
            }
        }
        this$0.buildSchedule();
        this$0.renderCatalog();
        this$0.paintMaps();
        return Unit.INSTANCE;
    }

    private final void buildSchedule() {
        int i;
        LinearLayout linearLayout = this.scheduleBox;
        LinearLayout linearLayout2 = null;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scheduleBox");
            linearLayout = null;
        }
        linearLayout.removeAllViews();
        this.paintSchedule = null;
        if (this.scheduleOptions.isEmpty()) {
            return;
        }
        Iterable $this$map$iv = this.scheduleOptions;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            Pair it = (Pair) item$iv$iv;
            destination$iv$iv.add((String) it.getSecond());
        }
        ArrayList arrayList = (List) destination$iv$iv;
        List $this$indexOfFirst$iv = this.scheduleOptions;
        int index$iv = 0;
        Iterator<Pair<String, String>> it2 = $this$indexOfFirst$iv.iterator();
        while (true) {
            if (!it2.hasNext()) {
                i = -1;
                break;
            }
            Pair item$iv = it2.next();
            Pair it3 = item$iv;
            if (Intrinsics.areEqual(it3.getFirst(), this.scheduleDraft)) {
                i = index$iv;
                break;
            }
            index$iv++;
        }
        LinearLayout linearLayout3 = this.scheduleBox;
        if (linearLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scheduleBox");
            linearLayout3 = null;
        }
        this.paintSchedule = choice("Auto Update", "How often the comma refreshes the downloaded maps.", arrayList, i, linearLayout3, new Function1() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda47
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit buildSchedule$lambda$114;
                buildSchedule$lambda$114 = GxNavigationPage.buildSchedule$lambda$114(GxNavigationPage.this, ((Integer) obj).intValue());
                return buildSchedule$lambda$114;
            }
        });
        GxNavigationPage gxNavigationPage = this;
        Pair[] pairArr = {TuplesKt.to("Apply", new Function0() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda48
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildSchedule$lambda$115;
                buildSchedule$lambda$115 = GxNavigationPage.buildSchedule$lambda$115(GxNavigationPage.this);
                return buildSchedule$lambda$115;
            }
        })};
        LinearLayout linearLayout4 = this.scheduleBox;
        if (linearLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scheduleBox");
        } else {
            linearLayout2 = linearLayout4;
        }
        GxPage.buttons$default(gxNavigationPage, pairArr, linearLayout2, false, 4, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildSchedule$lambda$114(GxNavigationPage this$0, int i) {
        this$0.scheduleDraft = this$0.scheduleOptions.get(i).getFirst();
        this$0.paintMaps();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildSchedule$lambda$115(GxNavigationPage this$0) {
        this$0.saveSchedule();
        return Unit.INSTANCE;
    }

    private final void refreshMapsStatus() {
        if (this.vDownloader == null || this.mapsStatusInFlight) {
            return;
        }
        this.mapsStatusInFlight = true;
        getJson("/api/maps/status", new Function2() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit refreshMapsStatus$lambda$116;
                refreshMapsStatus$lambda$116 = GxNavigationPage.refreshMapsStatus$lambda$116(GxNavigationPage.this, (String) obj, (JSONObject) obj2);
                return refreshMapsStatus$lambda$116;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit refreshMapsStatus$lambda$116(GxNavigationPage this$0, String e, JSONObject o) {
        List<String> emptyList;
        this$0.mapsStatusInFlight = false;
        this$0.mapsLoadingStatus = false;
        if (e != null || o == null) {
            this$0.mapsError = e == null ? "Failed to load map status." : e;
            this$0.paintMaps();
            return Unit.INSTANCE;
        }
        boolean wasSel = this$0.getSelectionDirty();
        boolean wasSch = this$0.getScheduleDirty();
        this$0.mapsStatus = o;
        JSONObject optJSONObject = o.optJSONObject("downloadProgress");
        if (optJSONObject == null) {
            optJSONObject = new JSONObject();
        }
        this$0.mapsProgress = optJSONObject;
        JSONArray optJSONArray = o.optJSONArray("selectedLocations");
        if (optJSONArray == null || (emptyList = this$0.strings(optJSONArray)) == null) {
            emptyList = CollectionsKt.emptyList();
        }
        this$0.selectedSaved = CollectionsKt.toSet(CollectionsKt.toSortedSet(emptyList, StringsKt.getCASE_INSENSITIVE_ORDER(StringCompanionObject.INSTANCE)));
        if (!wasSel) {
            this$0.selectedDraft.clear();
            this$0.selectedDraft.addAll(this$0.selectedSaved);
        }
        if (o.has("scheduleValue") && !o.isNull("scheduleValue")) {
            this$0.scheduleSaved = o.opt("scheduleValue").toString();
        }
        if (!wasSch) {
            this$0.scheduleDraft = this$0.scheduleSaved;
        }
        this$0.mapsError = "";
        this$0.paintMaps();
        return Unit.INSTANCE;
    }

    /* JADX WARN: Code restructure failed: missing block: B:177:0x02fb, code lost:
    
        if (r10 > 0.0d) goto L215;
     */
    /* JADX WARN: Removed duplicated region for block: B:181:0x030b  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x0313  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x031c  */
    /* JADX WARN: Removed duplicated region for block: B:244:0x0548  */
    /* JADX WARN: Removed duplicated region for block: B:256:0x057e  */
    /* JADX WARN: Removed duplicated region for block: B:259:0x058d  */
    /* JADX WARN: Removed duplicated region for block: B:261:0x0593  */
    /* JADX WARN: Removed duplicated region for block: B:283:0x05de  */
    /* JADX WARN: Removed duplicated region for block: B:286:0x05ea  */
    /* JADX WARN: Removed duplicated region for block: B:289:0x0661  */
    /* JADX WARN: Removed duplicated region for block: B:292:0x066d  */
    /* JADX WARN: Removed duplicated region for block: B:295:0x0681  */
    /* JADX WARN: Removed duplicated region for block: B:298:0x069c  */
    /* JADX WARN: Removed duplicated region for block: B:300:0x06a3  */
    /* JADX WARN: Removed duplicated region for block: B:304:0x06e1  */
    /* JADX WARN: Removed duplicated region for block: B:315:0x0715 A[LOOP:2: B:313:0x070f->B:315:0x0715, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:318:0x069f  */
    /* JADX WARN: Removed duplicated region for block: B:319:0x0674  */
    /* JADX WARN: Removed duplicated region for block: B:320:0x05ef  */
    /* JADX WARN: Removed duplicated region for block: B:334:0x05d7  */
    /* JADX WARN: Removed duplicated region for block: B:335:0x0590  */
    /* JADX WARN: Removed duplicated region for block: B:349:0x053d  */
    /* JADX WARN: Removed duplicated region for block: B:350:0x0315  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void paintMaps() {
        TextView textView;
        double est;
        boolean z;
        boolean showProgress;
        LinearLayout linearLayout;
        Function1<? super Integer, Unit> function1;
        LinearLayout linearLayout2;
        HorizontalScrollView hs;
        boolean z2;
        TextView textView2;
        String joinToString$default;
        TextView textView3;
        TextView textView4;
        String str$default;
        Iterator<Map.Entry<String, Pair<GxPage.Row, JSONObject>>> it;
        if (this.vDownloader == null) {
            return;
        }
        TextView textView5 = this.mapsErrorNote;
        if (textView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mapsErrorNote");
            textView5 = null;
        }
        textView5.setText(this.mapsError);
        TextView textView6 = this.mapsErrorNote;
        if (textView6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mapsErrorNote");
            textView6 = null;
        }
        textView6.setVisibility(this.mapsError.length() == 0 ? 8 : 0);
        JSONObject st = this.mapsStatus;
        JSONObject p = this.mapsProgress;
        TextView textView7 = this.vDownloader;
        if (textView7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vDownloader");
            textView7 = null;
        }
        textView7.setText(!this.mapsLoadingStatus ? isDownloading() ? st.optBoolean("cancelling") ? "Cancelling" : "Downloading" : "Idle" : "Checking...");
        TextView textView8 = this.vSaved;
        if (textView8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vSaved");
            textView8 = null;
        }
        textView8.setText(this.mapsLoadingStatus ? "Checking..." : String.valueOf(st.optInt("selectedCount", 0)));
        Double valueOf = Double.valueOf(p.optDouble("estimatedDownloadBytes", 0.0d));
        double it2 = valueOf.doubleValue();
        if (!((Double.isInfinite(it2) || Double.isNaN(it2)) ? false : true)) {
            valueOf = null;
        }
        double est2 = valueOf != null ? valueOf.doubleValue() : 0.0d;
        TextView textView9 = this.vEstimate;
        if (textView9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vEstimate");
            textView9 = null;
        }
        textView9.setText((getSelectionDirty() || est2 <= 0.0d) ? !this.selectedDraft.isEmpty() ? "Not yet available" : "Select regions" : "~" + gxBytes(est2) + " additional");
        TextView textView10 = this.vLastUpdate;
        if (textView10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vLastUpdate");
            textView = null;
        } else {
            textView = textView10;
        }
        TextView textView11 = textView;
        String str$default2 = GxPage.str$default(this, st, "lastUpdate", null, 2, null);
        if (str$default2.length() == 0) {
            str$default2 = "Never";
        }
        textView11.setText(str$default2);
        TextView textView12 = this.vStorage;
        if (textView12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("vStorage");
            textView12 = null;
        }
        textView12.setText(st.optBoolean("storageKnown") ? gxBytes(st.optDouble("storageBytes", 0.0d)) : "Calculating…");
        TextView textView13 = this.nOnroad;
        if (textView13 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("nOnroad");
            textView13 = null;
        }
        textView13.setVisibility(isOnroad() ? 0 : 8);
        TextView textView14 = this.nSelDirty;
        if (textView14 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("nSelDirty");
            textView14 = null;
        }
        textView14.setVisibility(getSelectionDirty() ? 0 : 8);
        TextView textView15 = this.nSchDirty;
        if (textView15 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("nSchDirty");
            textView15 = null;
        }
        textView15.setVisibility(getScheduleDirty() ? 0 : 8);
        boolean canDownload = (this.mapsLoadingStatus || this.mapsActionBusy || isOnroad() || isDownloading() || this.selectedDraft.isEmpty()) ? false : true;
        boolean canCancel = (this.mapsLoadingStatus || this.mapsActionBusy || !isDownloading()) ? false : true;
        boolean canRemove = (this.mapsLoadingStatus || this.mapsActionBusy || isDownloading() || isOnroad() || !st.optBoolean("mapsPresent")) ? false : true;
        LinearLayout linearLayout3 = this.mapsBar;
        if (linearLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mapsBar");
            linearLayout3 = null;
        }
        View childAt = linearLayout3.getChildAt(0);
        TextView b = childAt instanceof TextView ? (TextView) childAt : null;
        if (b != null) {
            b.setText(isDownloading() ? st.optBoolean("cancelling") ? "Cancelling..." : "Cancel Download" : "Download Maps");
            setOn(b, canDownload || canCancel);
            Unit unit = Unit.INSTANCE;
            Unit unit2 = Unit.INSTANCE;
        }
        LinearLayout linearLayout4 = this.mapsBar;
        if (linearLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mapsBar");
            linearLayout4 = null;
        }
        View childAt2 = linearLayout4.getChildAt(1);
        setOn(childAt2 instanceof TextView ? (TextView) childAt2 : null, canRemove);
        LinearLayout linearLayout5 = this.mapsBar;
        if (linearLayout5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mapsBar");
            linearLayout5 = null;
        }
        View childAt3 = linearLayout5.getChildAt(2);
        TextView b2 = childAt3 instanceof TextView ? (TextView) childAt3 : null;
        if (b2 != null) {
            b2.setText(this.mapsSavingSelection ? "Saving..." : "Save Selection");
            setOn(b2, (this.mapsSavingSelection || this.mapsLoadingCatalog || !getSelectionDirty()) ? false : true);
            Unit unit3 = Unit.INSTANCE;
            Unit unit4 = Unit.INSTANCE;
        }
        LinearLayout linearLayout6 = this.mapsBar;
        if (linearLayout6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mapsBar");
            linearLayout6 = null;
        }
        View childAt4 = linearLayout6.getChildAt(3);
        setOn(childAt4 instanceof TextView ? (TextView) childAt4 : null, getSelectionDirty() || getScheduleDirty());
        LinearLayout linearLayout7 = this.mapsBar;
        if (linearLayout7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mapsBar");
            linearLayout7 = null;
        }
        View childAt5 = linearLayout7.getChildAt(4);
        setOn(childAt5 instanceof TextView ? (TextView) childAt5 : null, !this.selectedDraft.isEmpty());
        boolean completed = p.optBoolean("completed");
        boolean cancelled = p.optBoolean("cancelled");
        if (!this.mapsLoadingStatus) {
            if (isDownloading() || completed || cancelled) {
                est = est2;
            } else {
                est = est2;
            }
            z = true;
            showProgress = z;
            linearLayout = this.progCard;
            if (linearLayout == null) {
                Intrinsics.throwUninitializedPropertyAccessException("progCard");
                linearLayout = null;
            }
            linearLayout.setVisibility(!showProgress ? 0 : 8);
            if (!showProgress) {
                Double valueOf2 = Double.valueOf(p.optDouble("percent", 0.0d));
                double it3 = valueOf2.doubleValue();
                if (!((Double.isInfinite(it3) || Double.isNaN(it3)) ? false : true)) {
                    valueOf2 = null;
                }
                double pct = valueOf2 != null ? valueOf2.doubleValue() : 0.0d;
                TextView textView16 = this.progTitle;
                if (textView16 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("progTitle");
                    textView16 = null;
                }
                textView16.setText((isDownloading() ? "Download Progress" : completed ? "Last Download" : cancelled ? "Download Cancelled" : "Download Estimate") + "  ·  " + Math.round(pct) + "%");
                ProgressBar progressBar = this.progBar;
                if (progressBar == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("progBar");
                    progressBar = null;
                }
                setProgress(progressBar, pct);
                TextView textView17 = this.pEstimate;
                if (textView17 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("pEstimate");
                    textView17 = null;
                }
                textView17.setText("Estimate: " + (est > 0.0d ? "~" + gxBytes(est) + " additional storage" : "Storage estimate unavailable"));
                double dl = p.optDouble("downloadedBytes", 0.0d);
                TextView textView18 = this.pStored;
                if (textView18 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("pStored");
                    textView18 = null;
                }
                textView18.setText("Stored: " + (dl > 0.0d ? gxBytes(dl) + " added storage" : "Storage reconciles after completion"));
                int tf = p.optInt("totalFiles", 0);
                TextView textView19 = this.pFiles;
                if (textView19 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("pFiles");
                    textView19 = null;
                }
                textView19.setText("Files: " + (tf > 0 ? p.optInt("downloadedFiles", 0) + " / " + tf + " files" : "Waiting for map service..."));
                double eta = p.optDouble("etaSeconds", 0.0d);
                TextView textView20 = this.pEta;
                if (textView20 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("pEta");
                    textView20 = null;
                }
                textView20.setText("ETA: " + ((!isDownloading() || eta <= 0.0d) ? "ETA unavailable until files start arriving" : "About " + gxDuration(eta) + " remaining"));
                String loc = GxPage.str$default(this, p, "primaryLocation", null, 2, null);
                TextView textView21 = this.pRegion;
                if (textView21 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("pRegion");
                    textView21 = null;
                }
                textView21.setText("Current region: " + loc);
                TextView textView22 = this.pRegion;
                if (textView22 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("pRegion");
                    textView22 = null;
                }
                textView22.setVisibility(loc.length() == 0 ? 8 : 0);
            }
            function1 = this.paintSchedule;
            if (function1 != null) {
                List $this$indexOfFirst$iv = this.scheduleOptions;
                int index$iv = 0;
                Iterator<Pair<String, String>> it4 = $this$indexOfFirst$iv.iterator();
                while (true) {
                    if (!it4.hasNext()) {
                        index$iv = -1;
                        break;
                    }
                    Pair item$iv = it4.next();
                    if (Intrinsics.areEqual(item$iv.getFirst(), this.scheduleDraft)) {
                        break;
                    } else {
                        index$iv++;
                    }
                }
                function1.invoke(Integer.valueOf(index$iv));
                Unit unit5 = Unit.INSTANCE;
            }
            linearLayout2 = this.scheduleBox;
            if (linearLayout2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("scheduleBox");
                linearLayout2 = null;
            }
            View childAt6 = linearLayout2.getChildAt(1);
            hs = !(childAt6 instanceof HorizontalScrollView) ? (HorizontalScrollView) childAt6 : null;
            if (hs == null) {
                z2 = false;
                View childAt7 = hs.getChildAt(0);
                LinearLayout linearLayout8 = childAt7 instanceof LinearLayout ? (LinearLayout) childAt7 : null;
                View childAt8 = linearLayout8 != null ? linearLayout8.getChildAt(0) : null;
                TextView b3 = childAt8 instanceof TextView ? (TextView) childAt8 : null;
                if (b3 != null) {
                    b3.setText(this.mapsSavingSchedule ? "Applying..." : "Apply");
                    setOn(b3, !this.mapsSavingSchedule && getScheduleDirty());
                    Unit unit6 = Unit.INSTANCE;
                    Unit unit7 = Unit.INSTANCE;
                }
            } else {
                z2 = false;
            }
            textView2 = this.selRegionsText;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("selRegionsText");
                textView2 = null;
            }
            if (this.selectedDraft.isEmpty()) {
                Iterable $this$map$iv = this.selectedDraft;
                Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                for (Object item$iv$iv : $this$map$iv) {
                    String it5 = (String) item$iv$iv;
                    String str = this.tokenLabels.get(it5);
                    if (str != null) {
                        it5 = str;
                    }
                    destination$iv$iv.add(it5);
                }
                Iterable $this$sortedBy$iv = (List) destination$iv$iv;
                joinToString$default = CollectionsKt.joinToString(CollectionsKt.sortedWith($this$sortedBy$iv, new Comparator() { // from class: com.starpilot.starview.GxNavigationPage$paintMaps$$inlined$sortedBy$1
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // java.util.Comparator
                    public final int compare(T t, T t2) {
                        String it6 = (String) t;
                        String lowerCase = it6.toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                        String it7 = (String) t2;
                        String lowerCase2 = it7.toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(lowerCase2, "toLowerCase(...)");
                        return ComparisonsKt.compareValues(lowerCase, lowerCase2);
                    }
                }), ", ", "", "", -1, "...", null);
            }
            textView2.setText(joinToString$default);
            textView3 = this.selRegionsText;
            if (textView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("selRegionsText");
                textView3 = null;
            }
            textView3.setTextColor(!this.selectedDraft.isEmpty() ? CarUi.Companion.getDIM() : CarUi.Companion.getTEXT());
            textView4 = this.schedNote;
            if (textView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("schedNote");
                textView4 = null;
            }
            str$default = GxPage.str$default(this, st, "scheduleLabel", null, 2, null);
            if (str$default.length() != 0 ? true : z2) {
                str$default = "Monthly";
            }
            textView4.setText("Saved schedule: " + ((Object) str$default) + " · Draft regions: " + this.selectedDraft.size());
            for (Map.Entry<String, Switch> entry : this.regionSwitches.entrySet()) {
                String tok = entry.getKey();
                Switch sw = entry.getValue();
                boolean on = this.selectedDraft.contains(tok);
                if (sw.isChecked() != on) {
                    sw.setChecked(on);
                }
            }
            it = this.groupRows.entrySet().iterator();
            while (it.hasNext()) {
                Pair pr = it.next().getValue();
                pr.getFirst().setDesc(groupSelectedCount(pr.getSecond()) + " selected");
            }
        }
        est = est2;
        z = false;
        showProgress = z;
        linearLayout = this.progCard;
        if (linearLayout == null) {
        }
        linearLayout.setVisibility(!showProgress ? 0 : 8);
        if (!showProgress) {
        }
        function1 = this.paintSchedule;
        if (function1 != null) {
        }
        linearLayout2 = this.scheduleBox;
        if (linearLayout2 == null) {
        }
        View childAt62 = linearLayout2.getChildAt(1);
        if (!(childAt62 instanceof HorizontalScrollView)) {
        }
        if (hs == null) {
        }
        textView2 = this.selRegionsText;
        if (textView2 == null) {
        }
        if (this.selectedDraft.isEmpty()) {
        }
        textView2.setText(joinToString$default);
        textView3 = this.selRegionsText;
        if (textView3 == null) {
        }
        textView3.setTextColor(!this.selectedDraft.isEmpty() ? CarUi.Companion.getDIM() : CarUi.Companion.getTEXT());
        textView4 = this.schedNote;
        if (textView4 == null) {
        }
        str$default = GxPage.str$default(this, st, "scheduleLabel", null, 2, null);
        if (str$default.length() != 0 ? true : z2) {
        }
        textView4.setText("Saved schedule: " + ((Object) str$default) + " · Draft regions: " + this.selectedDraft.size());
        while (r4.hasNext()) {
        }
        it = this.groupRows.entrySet().iterator();
        while (it.hasNext()) {
        }
    }

    private final int groupSelectedCount(JSONObject g) {
        Iterable emptyList;
        JSONArray optJSONArray = g.optJSONArray("regions");
        if (optJSONArray == null || (emptyList = objects(optJSONArray)) == null) {
            emptyList = CollectionsKt.emptyList();
        }
        Iterable $this$map$iv = emptyList;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            JSONObject it = (JSONObject) item$iv$iv;
            destination$iv$iv.add(GxPage.str$default(this, it, "token", null, 2, null));
        }
        Set toks = CollectionsKt.toSet((List) destination$iv$iv);
        Iterable $this$count$iv = this.selectedDraft;
        if (($this$count$iv instanceof Collection) && ((Collection) $this$count$iv).isEmpty()) {
            return 0;
        }
        int count$iv = 0;
        for (Object element$iv : $this$count$iv) {
            String it2 = (String) element$iv;
            if (toks.contains(it2) && (count$iv = count$iv + 1) < 0) {
                CollectionsKt.throwCountOverflow();
            }
        }
        return count$iv;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0161  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0164 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void renderCatalog() {
        LinearLayout linearLayout;
        Iterable emptyList;
        Iterator<? extends JSONObject> it;
        List emptyList2;
        Iterable emptyList3;
        Iterable $this$mapNotNullTo$iv$iv;
        Iterable $this$filter$iv;
        String str;
        int $i$f$mapNotNullTo;
        Iterable $this$forEach$iv$iv$iv;
        boolean z;
        LinearLayout linearLayout2;
        if (this.catalogBox == null) {
            return;
        }
        LinearLayout linearLayout3 = this.catalogBox;
        if (linearLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("catalogBox");
            linearLayout3 = null;
        }
        linearLayout3.removeAllViews();
        this.regionSwitches.clear();
        this.groupRows.clear();
        if (this.mapsLoadingCatalog) {
            LinearLayout linearLayout4 = this.catalogBox;
            if (linearLayout4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("catalogBox");
                linearLayout2 = null;
            } else {
                linearLayout2 = linearLayout4;
            }
            linearLayout2.addView(empty("Loading map catalog..."));
            return;
        }
        String q = StringsKt.trim((CharSequence) this.mapsSearch).toString().toLowerCase(Locale.ROOT);
        String str2 = "toLowerCase(...)";
        Intrinsics.checkNotNullExpressionValue(q, "toLowerCase(...)");
        boolean any = false;
        Iterator<? extends JSONObject> it2 = this.mapsSections.iterator();
        while (it2.hasNext()) {
            JSONObject s = it2.next();
            JSONArray optJSONArray = s.optJSONArray("groups");
            if (optJSONArray == null || (emptyList = objects(optJSONArray)) == null) {
                emptyList = CollectionsKt.emptyList();
            }
            Iterable $this$mapNotNull$iv = emptyList;
            int $i$f$mapNotNull = 0;
            Collection destination$iv$iv = new ArrayList();
            Iterable $this$mapNotNullTo$iv$iv2 = $this$mapNotNull$iv;
            int $i$f$mapNotNullTo2 = 0;
            Iterable $this$forEach$iv$iv$iv2 = $this$mapNotNullTo$iv$iv2;
            Iterator it3 = $this$forEach$iv$iv$iv2.iterator();
            while (true) {
                it = it2;
                if (!it3.hasNext()) {
                    break;
                }
                Object element$iv$iv$iv = it3.next();
                JSONObject g = (JSONObject) element$iv$iv$iv;
                JSONArray optJSONArray2 = g.optJSONArray("regions");
                if (optJSONArray2 == null || (emptyList3 = objects(optJSONArray2)) == null) {
                    emptyList3 = CollectionsKt.emptyList();
                }
                Iterable $this$filter$iv2 = emptyList3;
                Iterable $this$mapNotNull$iv2 = $this$mapNotNull$iv;
                Collection destination$iv$iv2 = new ArrayList();
                for (Object element$iv$iv : $this$filter$iv2) {
                    int $i$f$mapNotNull2 = $i$f$mapNotNull;
                    JSONObject r = (JSONObject) element$iv$iv;
                    if (q.length() == 0) {
                        $this$mapNotNullTo$iv$iv = $this$mapNotNullTo$iv$iv2;
                        $this$filter$iv = $this$filter$iv2;
                        str = str2;
                        $i$f$mapNotNullTo = $i$f$mapNotNullTo2;
                        $this$forEach$iv$iv$iv = $this$forEach$iv$iv$iv2;
                    } else {
                        $this$mapNotNullTo$iv$iv = $this$mapNotNullTo$iv$iv2;
                        $this$filter$iv = $this$filter$iv2;
                        $i$f$mapNotNullTo = $i$f$mapNotNullTo2;
                        $this$forEach$iv$iv$iv = $this$forEach$iv$iv$iv2;
                        String lowerCase = (GxPage.str$default(this, r, "label", null, 2, null) + " " + GxPage.str$default(this, r, "code", null, 2, null) + " " + GxPage.str$default(this, r, "token", null, 2, null)).toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(lowerCase, str2);
                        str = str2;
                        if (!StringsKt.contains((CharSequence) lowerCase, (CharSequence) q, false)) {
                            z = false;
                            if (!z) {
                                destination$iv$iv2.add(element$iv$iv);
                            }
                            str2 = str;
                            $i$f$mapNotNull = $i$f$mapNotNull2;
                            $this$forEach$iv$iv$iv2 = $this$forEach$iv$iv$iv;
                            $this$mapNotNullTo$iv$iv2 = $this$mapNotNullTo$iv$iv;
                            $this$filter$iv2 = $this$filter$iv;
                            $i$f$mapNotNullTo2 = $i$f$mapNotNullTo;
                        }
                    }
                    z = true;
                    if (!z) {
                    }
                    str2 = str;
                    $i$f$mapNotNull = $i$f$mapNotNull2;
                    $this$forEach$iv$iv$iv2 = $this$forEach$iv$iv$iv;
                    $this$mapNotNullTo$iv$iv2 = $this$mapNotNullTo$iv$iv;
                    $this$filter$iv2 = $this$filter$iv;
                    $i$f$mapNotNullTo2 = $i$f$mapNotNullTo;
                }
                int $i$f$mapNotNull3 = $i$f$mapNotNull;
                Iterable $this$mapNotNullTo$iv$iv3 = $this$mapNotNullTo$iv$iv2;
                String str3 = str2;
                int $i$f$mapNotNullTo3 = $i$f$mapNotNullTo2;
                Iterable $this$forEach$iv$iv$iv3 = $this$forEach$iv$iv$iv2;
                List regs = (List) destination$iv$iv2;
                Pair pair = regs.isEmpty() ? null : TuplesKt.to(g, regs);
                if (pair != null) {
                    destination$iv$iv.add(pair);
                }
                str2 = str3;
                it2 = it;
                $this$mapNotNull$iv = $this$mapNotNull$iv2;
                $i$f$mapNotNull = $i$f$mapNotNull3;
                $this$forEach$iv$iv$iv2 = $this$forEach$iv$iv$iv3;
                $this$mapNotNullTo$iv$iv2 = $this$mapNotNullTo$iv$iv3;
                $i$f$mapNotNullTo2 = $i$f$mapNotNullTo3;
            }
            String str4 = str2;
            List<Pair> groups = (List) destination$iv$iv;
            if (groups.isEmpty()) {
                str2 = str4;
                it2 = it;
            } else {
                String str5 = GxPage.str$default(this, s, "title", null, 2, null) + "  (" + groups.size() + ")";
                LinearLayout linearLayout5 = this.catalogBox;
                if (linearLayout5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("catalogBox");
                    linearLayout5 = null;
                }
                header(str5, linearLayout5);
                for (Pair pair2 : groups) {
                    JSONObject g2 = (JSONObject) pair2.component1();
                    List<JSONObject> regs2 = (List) pair2.component2();
                    final String key = GxPage.str$default(this, s, "key", null, 2, null) + ":" + GxPage.str$default(this, g2, "key", null, 2, null);
                    boolean open = (q.length() > 0) || this.expandedGroups.contains(key);
                    GxNavigationPage gxNavigationPage = this;
                    String str$default = GxPage.str$default(this, g2, "title", null, 2, null);
                    String str6 = groupSelectedCount(g2) + " selected";
                    LinearLayout linearLayout6 = this.catalogBox;
                    if (linearLayout6 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("catalogBox");
                        linearLayout6 = null;
                    }
                    GxPage.Row r2 = GxPage.row$default(gxNavigationPage, str$default, str6, linearLayout6, 0, 8, null);
                    this.groupRows.put(key, TuplesKt.to(r2, g2));
                    final Function0 toggleOpen = new Function0() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda17
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            Unit renderCatalog$lambda$132;
                            renderCatalog$lambda$132 = GxNavigationPage.renderCatalog$lambda$132(GxNavigationPage.this, key);
                            return renderCatalog$lambda$132;
                        }
                    };
                    if (q.length() == 0) {
                        clickable(r2, new Function0() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda18
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                Unit renderCatalog$lambda$133;
                                renderCatalog$lambda$133 = GxNavigationPage.renderCatalog$lambda$133(toggleOpen);
                                return renderCatalog$lambda$133;
                            }
                        });
                        GxPage.Row.button$default(r2, open ? "Hide" : "Show", false, new Function0() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda19
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                Unit renderCatalog$lambda$134;
                                renderCatalog$lambda$134 = GxNavigationPage.renderCatalog$lambda$134(toggleOpen);
                                return renderCatalog$lambda$134;
                            }
                        }, 2, null);
                    }
                    if (open) {
                        LinearLayout $this$renderCatalog_u24lambda_u24135 = new LinearLayout(getAct());
                        $this$renderCatalog_u24lambda_u24135.setOrientation(1);
                        $this$renderCatalog_u24lambda_u24135.setPadding(getUi().dp(28), 0, 0, getUi().dp(8));
                        LinearLayout linearLayout7 = this.catalogBox;
                        if (linearLayout7 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("catalogBox");
                            linearLayout7 = null;
                        }
                        linearLayout7.addView($this$renderCatalog_u24lambda_u24135, new LinearLayout.LayoutParams(-1, -2));
                        JSONArray optJSONArray3 = g2.optJSONArray("regions");
                        if (optJSONArray3 == null || (emptyList2 = objects(optJSONArray3)) == null) {
                            emptyList2 = CollectionsKt.emptyList();
                        }
                        final List allRegs = emptyList2;
                        boolean z2 = true;
                        GxPage.buttons$default(this, new Pair[]{TuplesKt.to("Select All", new Function0() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda20
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                Unit renderCatalog$lambda$136;
                                renderCatalog$lambda$136 = GxNavigationPage.renderCatalog$lambda$136(allRegs, this);
                                return renderCatalog$lambda$136;
                            }
                        }), TuplesKt.to("Clear", new Function0() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda21
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                Unit renderCatalog$lambda$137;
                                renderCatalog$lambda$137 = GxNavigationPage.renderCatalog$lambda$137(allRegs, this);
                                return renderCatalog$lambda$137;
                            }
                        })}, $this$renderCatalog_u24lambda_u24135, false, 4, null);
                        for (JSONObject reg : regs2) {
                            final String tok = GxPage.str$default(this, reg, "token", null, 2, null);
                            String str$default2 = GxPage.str$default(this, reg, "label", null, 2, null);
                            String key2 = GxPage.str$default(this, reg, "code", null, 2, null);
                            if (key2.length() == 0 ? z2 : false) {
                                key2 = null;
                            }
                            Switch sw = toggle(str$default2, key2, this.selectedDraft.contains(tok), $this$renderCatalog_u24lambda_u24135, new Function1() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda23
                                @Override // kotlin.jvm.functions.Function1
                                public final Object invoke(Object obj) {
                                    Unit renderCatalog$lambda$139;
                                    renderCatalog$lambda$139 = GxNavigationPage.renderCatalog$lambda$139(GxNavigationPage.this, tok, ((Boolean) obj).booleanValue());
                                    return renderCatalog$lambda$139;
                                }
                            }).component2();
                            this.regionSwitches.put(tok, sw);
                            r2 = r2;
                            key = key;
                            g2 = g2;
                            s = s;
                            z2 = true;
                        }
                    }
                }
                any = true;
                str2 = str4;
                it2 = it;
            }
        }
        if (any) {
            return;
        }
        LinearLayout linearLayout8 = this.catalogBox;
        if (linearLayout8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("catalogBox");
            linearLayout = null;
        } else {
            linearLayout = linearLayout8;
        }
        linearLayout.addView(empty("No regions match the current search."));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderCatalog$lambda$132(GxNavigationPage this$0, String $key) {
        if (this$0.expandedGroups.contains($key)) {
            this$0.expandedGroups.remove($key);
        } else {
            this$0.expandedGroups.add($key);
        }
        this$0.renderCatalog();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderCatalog$lambda$133(Function0 $toggleOpen) {
        $toggleOpen.invoke();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderCatalog$lambda$134(Function0 $toggleOpen) {
        $toggleOpen.invoke();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderCatalog$lambda$136(List $allRegs, GxNavigationPage this$0) {
        Iterator it = $allRegs.iterator();
        while (it.hasNext()) {
            JSONObject x = (JSONObject) it.next();
            this$0.selectedDraft.add(GxPage.str$default(this$0, x, "token", null, 2, null));
        }
        this$0.paintMaps();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderCatalog$lambda$137(List $allRegs, GxNavigationPage this$0) {
        Iterator it = $allRegs.iterator();
        while (it.hasNext()) {
            JSONObject x = (JSONObject) it.next();
            this$0.selectedDraft.remove(GxPage.str$default(this$0, x, "token", null, 2, null));
        }
        this$0.paintMaps();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderCatalog$lambda$139(GxNavigationPage this$0, String $tok, boolean on) {
        LinkedHashSet<String> linkedHashSet = this$0.selectedDraft;
        if (on) {
            linkedHashSet.add($tok);
        } else {
            linkedHashSet.remove($tok);
        }
        this$0.paintMaps();
        return Unit.INSTANCE;
    }

    private final void saveSelection() {
        if (this.mapsSavingSelection || !getSelectionDirty()) {
            return;
        }
        this.mapsSavingSelection = true;
        paintMaps();
        GxPage.send$default(this, "POST", "/api/maps/selection", new JSONObject().put("selectedLocations", new JSONArray((Collection) CollectionsKt.toList(this.selectedDraft))), 0L, new Function2() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda22
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit saveSelection$lambda$141;
                saveSelection$lambda$141 = GxNavigationPage.saveSelection$lambda$141(GxNavigationPage.this, (String) obj, (JSONObject) obj2);
                return saveSelection$lambda$141;
            }
        }, 8, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit saveSelection$lambda$141(GxNavigationPage this$0, String e, JSONObject o) {
        String str;
        String optString;
        this$0.mapsSavingSelection = false;
        if (e == null) {
            str = null;
            if (o != null && (optString = o.optString("message")) != null) {
                String str2 = optString;
                if (!StringsKt.isBlank(str2)) {
                    str = str2;
                }
                str = str;
            }
            if (str == null) {
                str = "Map selection saved.";
            }
        } else {
            str = e;
        }
        this$0.toast(str);
        if (e == null) {
            this$0.selectedSaved = CollectionsKt.toSet(this$0.selectedDraft);
        }
        this$0.paintMaps();
        this$0.refreshMapsStatus();
        return Unit.INSTANCE;
    }

    private final void saveSchedule() {
        if (this.mapsSavingSchedule || !getScheduleDirty()) {
            return;
        }
        this.mapsSavingSchedule = true;
        paintMaps();
        GxPage.send$default(this, "POST", "/api/maps/schedule", new JSONObject().put("schedule", this.scheduleDraft), 0L, new Function2() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda72
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit saveSchedule$lambda$143;
                saveSchedule$lambda$143 = GxNavigationPage.saveSchedule$lambda$143(GxNavigationPage.this, (String) obj, (JSONObject) obj2);
                return saveSchedule$lambda$143;
            }
        }, 8, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit saveSchedule$lambda$143(GxNavigationPage this$0, String e, JSONObject o) {
        String str;
        String optString;
        this$0.mapsSavingSchedule = false;
        if (e == null) {
            str = null;
            if (o != null && (optString = o.optString("message")) != null) {
                String str2 = optString;
                if (!StringsKt.isBlank(str2)) {
                    str = str2;
                }
                str = str;
            }
            if (str == null) {
                str = "Map schedule updated.";
            }
        } else {
            str = e;
        }
        this$0.toast(str);
        if (e == null) {
            this$0.scheduleSaved = this$0.scheduleDraft;
        }
        this$0.paintMaps();
        this$0.refreshMapsStatus();
        return Unit.INSTANCE;
    }

    private final void mapsOp(String op, JSONObject body, final String okMsg) {
        this.mapsActionBusy = true;
        paintMaps();
        GxPage.send$default(this, "POST", "/api/maps/" + op, body, 0L, new Function2() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit mapsOp$lambda$145;
                mapsOp$lambda$145 = GxNavigationPage.mapsOp$lambda$145(GxNavigationPage.this, okMsg, (String) obj, (JSONObject) obj2);
                return mapsOp$lambda$145;
            }
        }, 8, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit mapsOp$lambda$145(GxNavigationPage this$0, String $okMsg, String e, JSONObject o) {
        String str;
        String optString;
        this$0.mapsActionBusy = false;
        if (e == null) {
            str = null;
            if (o != null && (optString = o.optString("message")) != null) {
                String str2 = optString;
                if (!StringsKt.isBlank(str2)) {
                    str = str2;
                }
                str = str;
            }
            if (str == null) {
                str = $okMsg;
            }
        } else {
            str = e;
        }
        this$0.toast(str);
        this$0.paintMaps();
        this$0.refreshMapsStatus();
        return Unit.INSTANCE;
    }

    private final void startDownload() {
        if (this.mapsActionBusy || isDownloading() || isOnroad() || this.selectedDraft.isEmpty()) {
            return;
        }
        JSONObject put = new JSONObject().put("selectedLocations", new JSONArray((Collection) CollectionsKt.toList(this.selectedDraft))).put("schedule", this.scheduleDraft);
        Intrinsics.checkNotNullExpressionValue(put, "put(...)");
        mapsOp("download", put, "Map download started.");
    }

    private final void cancelDownload() {
        if (this.mapsActionBusy || !isDownloading()) {
            return;
        }
        mapsOp("cancel", new JSONObject(), "Map download cancellation requested.");
    }

    private final void removeMaps() {
        if (this.mapsActionBusy || isDownloading() || isOnroad() || !this.mapsStatus.optBoolean("mapsPresent")) {
            return;
        }
        confirm("Remove Maps", "Are you sure you want to remove all downloaded offline maps?", "Yes, Remove", new Function0() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit removeMaps$lambda$146;
                removeMaps$lambda$146 = GxNavigationPage.removeMaps$lambda$146(GxNavigationPage.this);
                return removeMaps$lambda$146;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit removeMaps$lambda$146(GxNavigationPage this$0) {
        this$0.mapsOp("remove", new JSONObject(), "Maps removed.");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: GxNavigation.kt */
    static final /* data */ class KeyMeta {
        private final String body;
        private final String kind;
        private final int min;
        private final String navField;
        private final String prefix;
        private final String title;

        public static /* synthetic */ KeyMeta copy$default(KeyMeta keyMeta, String str, String str2, String str3, int i, String str4, String str5, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                str = keyMeta.kind;
            }
            if ((i2 & 2) != 0) {
                str2 = keyMeta.body;
            }
            String str6 = str2;
            if ((i2 & 4) != 0) {
                str3 = keyMeta.prefix;
            }
            String str7 = str3;
            if ((i2 & 8) != 0) {
                i = keyMeta.min;
            }
            int i3 = i;
            if ((i2 & 16) != 0) {
                str4 = keyMeta.title;
            }
            String str8 = str4;
            if ((i2 & 32) != 0) {
                str5 = keyMeta.navField;
            }
            return keyMeta.copy(str, str6, str7, i3, str8, str5);
        }

        /* renamed from: component1, reason: from getter */
        public final String getKind() {
            return this.kind;
        }

        /* renamed from: component2, reason: from getter */
        public final String getBody() {
            return this.body;
        }

        /* renamed from: component3, reason: from getter */
        public final String getPrefix() {
            return this.prefix;
        }

        /* renamed from: component4, reason: from getter */
        public final int getMin() {
            return this.min;
        }

        /* renamed from: component5, reason: from getter */
        public final String getTitle() {
            return this.title;
        }

        /* renamed from: component6, reason: from getter */
        public final String getNavField() {
            return this.navField;
        }

        public final KeyMeta copy(String kind, String body, String prefix, int min, String title, String navField) {
            Intrinsics.checkNotNullParameter(kind, "kind");
            Intrinsics.checkNotNullParameter(body, "body");
            Intrinsics.checkNotNullParameter(prefix, "prefix");
            Intrinsics.checkNotNullParameter(title, "title");
            Intrinsics.checkNotNullParameter(navField, "navField");
            return new KeyMeta(kind, body, prefix, min, title, navField);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof KeyMeta)) {
                return false;
            }
            KeyMeta keyMeta = (KeyMeta) other;
            return Intrinsics.areEqual(this.kind, keyMeta.kind) && Intrinsics.areEqual(this.body, keyMeta.body) && Intrinsics.areEqual(this.prefix, keyMeta.prefix) && this.min == keyMeta.min && Intrinsics.areEqual(this.title, keyMeta.title) && Intrinsics.areEqual(this.navField, keyMeta.navField);
        }

        public int hashCode() {
            return (((((((((this.kind.hashCode() * 31) + this.body.hashCode()) * 31) + this.prefix.hashCode()) * 31) + Integer.hashCode(this.min)) * 31) + this.title.hashCode()) * 31) + this.navField.hashCode();
        }

        public String toString() {
            return "KeyMeta(kind=" + this.kind + ", body=" + this.body + ", prefix=" + this.prefix + ", min=" + this.min + ", title=" + this.title + ", navField=" + this.navField + ")";
        }

        public KeyMeta(String kind, String body, String prefix, int min, String title, String navField) {
            Intrinsics.checkNotNullParameter(kind, "kind");
            Intrinsics.checkNotNullParameter(body, "body");
            Intrinsics.checkNotNullParameter(prefix, "prefix");
            Intrinsics.checkNotNullParameter(title, "title");
            Intrinsics.checkNotNullParameter(navField, "navField");
            this.kind = kind;
            this.body = body;
            this.prefix = prefix;
            this.min = min;
            this.title = title;
            this.navField = navField;
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
    }

    private final void buildKeys(LinearLayout tab) {
        this.keysBox = box(tab);
        renderKeys();
    }

    private final void loadKeys() {
        if (this.keysBox == null) {
            return;
        }
        getJson("/api/navigation", new Function2() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit loadKeys$lambda$150;
                loadKeys$lambda$150 = GxNavigationPage.loadKeys$lambda$150(GxNavigationPage.this, (String) obj, (JSONObject) obj2);
                return loadKeys$lambda$150;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadKeys$lambda$150(final GxNavigationPage this$0, String e, JSONObject nav) {
        if (e != null || nav == null) {
            this$0.keysError = e == null ? "Failed to load keys..." : e;
        } else {
            this$0.keysError = "";
            for (KeyMeta m : this$0.keyMeta) {
                this$0.savedKeys.put(m.getKind(), GxPage.str$default(this$0, nav, m.getNavField(), null, 2, null));
            }
            this$0.mapboxPublic = StringsKt.trim((CharSequence) GxPage.str$default(this$0, nav, "mapboxPublic", null, 2, null)).toString();
            this$0.mapboxSecret = StringsKt.trim((CharSequence) GxPage.str$default(this$0, nav, "mapboxSecret", null, 2, null)).toString();
        }
        this$0.getJson("/api/galaxy/session", new Function2() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit loadKeys$lambda$150$lambda$149;
                loadKeys$lambda$150$lambda$149 = GxNavigationPage.loadKeys$lambda$150$lambda$149(this$0, (String) obj, (JSONObject) obj2);
                return loadKeys$lambda$150$lambda$149;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadKeys$lambda$150$lambda$149(GxNavigationPage this$0, String se, JSONObject d) {
        if (se != null || d == null) {
            this$0.toast(se == null ? "Failed to load Galaxy session..." : se);
        } else {
            String str$default = GxPage.str$default(this$0, d, "appUrl", null, 2, null);
            if (str$default.length() == 0) {
                str$default = this$0.appUrl;
            }
            this$0.appUrl = str$default;
            String str$default2 = GxPage.str$default(this$0, d, "cookieName", null, 2, null);
            if (str$default2.length() == 0) {
                str$default2 = "galaxy_session";
            }
            this$0.cookieName = str$default2;
            this$0.paired = d.optBoolean("paired");
            this$0.galaxyToken = GxPage.str$default(this$0, d, "sessionToken", null, 2, null);
            this$0.tokenVisible = false;
        }
        this$0.keysLoading = false;
        this$0.renderKeys();
        return Unit.INSTANCE;
    }

    private final String maskKey(String raw) {
        Object obj;
        if (raw.length() == 0) {
            return "";
        }
        Iterable $this$firstOrNull$iv = CollectionsKt.listOf((Object[]) new String[]{"pk.", "sk."});
        Iterator it = $this$firstOrNull$iv.iterator();
        while (true) {
            obj = null;
            if (!it.hasNext()) {
                break;
            }
            Object element$iv = it.next();
            String it2 = (String) element$iv;
            if (StringsKt.startsWith(raw, it2, false)) {
                obj = element$iv;
                break;
            }
        }
        String str = (String) obj;
        if (str == null) {
            str = "";
        }
        String prefix = str;
        int n = raw.length() - prefix.length();
        return prefix + StringsKt.repeat("x", Math.min(n, 24)) + (n > 24 ? "… (" + n + " characters)" : "");
    }

    private final void renderKeys() {
        LinearLayout linearLayout = this.keysBox;
        LinearLayout linearLayout2 = null;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("keysBox");
            linearLayout = null;
        }
        linearLayout.removeAllViews();
        if (this.keysLoading) {
            LinearLayout linearLayout3 = this.keysBox;
            if (linearLayout3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("keysBox");
            } else {
                linearLayout2 = linearLayout3;
            }
            linearLayout2.addView(empty("Loading keys..."));
            return;
        }
        if (this.keysError.length() > 0) {
            String str = this.keysError;
            LinearLayout linearLayout4 = this.keysBox;
            if (linearLayout4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("keysBox");
                linearLayout4 = null;
            }
            note(str, linearLayout4).setTextColor(CarUi.Companion.getWARN());
        }
        LinearLayout linearLayout5 = this.keysBox;
        if (linearLayout5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("keysBox");
            linearLayout5 = null;
        }
        header("AMap / Gaode Keys", linearLayout5);
        LinearLayout linearLayout6 = this.keysBox;
        if (linearLayout6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("keysBox");
            linearLayout6 = null;
        }
        note("AMap is the Gaode provider, not Google Maps.", linearLayout6);
        for (KeyMeta m : CollectionsKt.take(this.keyMeta, 2)) {
            keyRow(m);
        }
        LinearLayout linearLayout7 = this.keysBox;
        if (linearLayout7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("keysBox");
            linearLayout7 = null;
        }
        header("Mapbox Keys", linearLayout7);
        LinearLayout linearLayout8 = this.keysBox;
        if (linearLayout8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("keysBox");
            linearLayout8 = null;
        }
        note("Public and secret Mapbox tokens used by the navigation app.", linearLayout8);
        for (KeyMeta m2 : CollectionsKt.drop(this.keyMeta, 2)) {
            keyRow(m2);
        }
        LinearLayout linearLayout9 = this.keysBox;
        if (linearLayout9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("keysBox");
            linearLayout9 = null;
        }
        header("App Keys", linearLayout9);
        GxNavigationPage gxNavigationPage = this;
        LinearLayout linearLayout10 = this.keysBox;
        if (linearLayout10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("keysBox");
            linearLayout10 = null;
        }
        GxPage.Row ar = GxPage.row$default(gxNavigationPage, "Install The App", "The Galaxy navigation app on Google Play.", linearLayout10, 0, 8, null);
        GxPage.Row.button$default(ar, "Open Play Store", false, new Function0() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda81
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit renderKeys$lambda$152;
                renderKeys$lambda$152 = GxNavigationPage.renderKeys$lambda$152(GxNavigationPage.this);
                return renderKeys$lambda$152;
            }
        }, 2, null);
        GxNavigationPage gxNavigationPage2 = this;
        LinearLayout linearLayout11 = this.keysBox;
        if (linearLayout11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("keysBox");
            linearLayout11 = null;
        }
        GxPage.row$default(gxNavigationPage2, "Cookie Name", null, linearLayout11, 0, 8, null).value(this.cookieName);
        GxNavigationPage gxNavigationPage3 = this;
        String repeat = this.galaxyToken.length() == 0 ? this.paired ? "Session token unavailable..." : "Pair Galaxy to create a session token..." : this.tokenVisible ? this.galaxyToken : StringsKt.repeat("•", Math.min(this.galaxyToken.length(), 24));
        LinearLayout linearLayout12 = this.keysBox;
        if (linearLayout12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("keysBox");
        } else {
            linearLayout2 = linearLayout12;
        }
        GxPage.Row tr = GxPage.row$default(gxNavigationPage3, "Session Token", repeat, linearLayout2, 0, 8, null);
        if (this.galaxyToken.length() > 0) {
            tr.getDesc().setTextIsSelectable(this.tokenVisible);
            GxPage.Row.button$default(tr, this.tokenVisible ? "Hide" : "Show", false, new Function0() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda82
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit renderKeys$lambda$153;
                    renderKeys$lambda$153 = GxNavigationPage.renderKeys$lambda$153(GxNavigationPage.this);
                    return renderKeys$lambda$153;
                }
            }, 2, null);
            GxPage.Row.button$default(tr, "Copy", false, new Function0() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit renderKeys$lambda$154;
                    renderKeys$lambda$154 = GxNavigationPage.renderKeys$lambda$154(GxNavigationPage.this);
                    return renderKeys$lambda$154;
                }
            }, 2, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderKeys$lambda$152(GxNavigationPage this$0) {
        this$0.openUrl(this$0.appUrl);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderKeys$lambda$153(GxNavigationPage this$0) {
        this$0.tokenVisible = !this$0.tokenVisible;
        this$0.renderKeys();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderKeys$lambda$154(GxNavigationPage this$0) {
        try {
            Object systemService = this$0.getAct().getSystemService("clipboard");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.content.ClipboardManager");
            ((ClipboardManager) systemService).setPrimaryClip(ClipData.newPlainText("Galaxy session token", this$0.galaxyToken));
            this$0.toast("Session token copied!");
        } catch (Exception e) {
            this$0.toast("Copy failed...");
        }
        return Unit.INSTANCE;
    }

    private final void keyRow(final KeyMeta m) {
        String raw = this.savedKeys.get(m.getKind());
        if (raw == null) {
            raw = "";
        }
        String kind = m.getKind();
        if (kind.length() > 0) {
            StringBuilder sb = new StringBuilder();
            char it = kind.charAt(0);
            String valueOf = String.valueOf(it);
            Intrinsics.checkNotNull(valueOf, "null cannot be cast to non-null type java.lang.String");
            String upperCase = valueOf.toUpperCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
            StringBuilder append = sb.append((Object) upperCase);
            String substring = kind.substring(1);
            Intrinsics.checkNotNullExpressionValue(substring, "substring(...)");
            kind = append.append(substring).toString();
        }
        String label = new Regex("[0-9]").replace(kind, new Function1() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                CharSequence keyRow$lambda$156;
                keyRow$lambda$156 = GxNavigationPage.keyRow$lambda$156((MatchResult) obj);
                return keyRow$lambda$156;
            }
        }) + " Key";
        GxNavigationPage gxNavigationPage = this;
        String str = m.getTitle() + "  (" + label + ")";
        String maskKey = raw.length() == 0 ? "Not set" : maskKey(raw);
        LinearLayout linearLayout = this.keysBox;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("keysBox");
            linearLayout = null;
        }
        GxPage.Row r = GxPage.row$default(gxNavigationPage, str, maskKey, linearLayout, 0, 8, null);
        r.getDesc().setTypeface(raw.length() == 0 ? Typeface.DEFAULT : Typeface.MONOSPACE);
        r.button(raw.length() == 0 ? "Set" : "Change", raw.length() == 0, new Function0() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit keyRow$lambda$157;
                keyRow$lambda$157 = GxNavigationPage.keyRow$lambda$157(GxNavigationPage.this, m);
                return keyRow$lambda$157;
            }
        });
        if (raw.length() > 0) {
            GxPage.Row.button$default(r, "Delete", false, new Function0() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda14
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit keyRow$lambda$162;
                    keyRow$lambda$162 = GxNavigationPage.keyRow$lambda$162(GxNavigationPage.this, m);
                    return keyRow$lambda$162;
                }
            }, 2, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence keyRow$lambda$156(MatchResult it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return " " + it.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit keyRow$lambda$157(GxNavigationPage this$0, KeyMeta $m) {
        this$0.editKey($m);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit keyRow$lambda$162(final GxNavigationPage this$0, final KeyMeta $m) {
        this$0.confirm("Confirm Delete", "Are you sure you want to delete your " + $m.getTitle() + " key?", "Yes, Delete", new Function0() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit keyRow$lambda$162$lambda$161;
                keyRow$lambda$162$lambda$161 = GxNavigationPage.keyRow$lambda$162$lambda$161(this$0, $m);
                return keyRow$lambda$162$lambda$161;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit keyRow$lambda$162$lambda$161(final GxNavigationPage this$0, final KeyMeta $m) {
        GxPage.send$default(this$0, "DELETE", "/api/navigation_key?type=" + this$0.enc($m.getKind()), null, 0L, new Function2() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda24
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit keyRow$lambda$162$lambda$161$lambda$160;
                keyRow$lambda$162$lambda$161$lambda$160 = GxNavigationPage.keyRow$lambda$162$lambda$161$lambda$160(this$0, $m, (String) obj, (JSONObject) obj2);
                return keyRow$lambda$162$lambda$161$lambda$160;
            }
        }, 12, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x002e, code lost:
    
        if (r0 == null) goto L16;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Unit keyRow$lambda$162$lambda$161$lambda$160(GxNavigationPage this$0, KeyMeta $m, String e, JSONObject o) {
        String str;
        String optString;
        if (e != null) {
            String str2 = e;
            if (StringsKt.isBlank(str2)) {
                str2 = "Delete failed...";
            }
            this$0.toast(str2);
            return Unit.INSTANCE;
        }
        if (o != null && (optString = o.optString("message")) != null) {
            String str3 = optString;
            if (StringsKt.isBlank(str3)) {
                str3 = null;
            }
            str = str3;
        }
        str = "Deleted!";
        this$0.toast(str);
        this$0.savedKeys.put($m.getKind(), "");
        if (Intrinsics.areEqual($m.getKind(), "public")) {
            this$0.mapboxPublic = "";
        } else if (Intrinsics.areEqual($m.getKind(), "secret")) {
            this$0.mapboxSecret = "";
        }
        this$0.renderKeys();
        this$0.renderNavAfterKeys();
        return Unit.INSTANCE;
    }

    private final void editKey(final KeyMeta m) {
        GxPage.prompt$default(this, m.getTitle() + " key", m.getPrefix() + "xxxxxx...", "", 524433, null, new Function1() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda31
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit editKey$lambda$166;
                editKey$lambda$166 = GxNavigationPage.editKey$lambda$166(m, this, (String) obj);
                return editKey$lambda$166;
            }
        }, 16, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit editKey$lambda$166(final KeyMeta $m, final GxNavigationPage this$0, String v) {
        Intrinsics.checkNotNullParameter(v, "v");
        String t = StringsKt.trim((CharSequence) v).toString();
        if (t.length() == 0) {
            return Unit.INSTANCE;
        }
        final String value = StringsKt.startsWith(t, $m.getPrefix(), false) ? t : $m.getPrefix() + t;
        if (value.length() < $m.getMin()) {
            this$0.toast($m.getTitle() + " key is invalid or too short...");
            return Unit.INSTANCE;
        }
        GxPage.send$default(this$0, "POST", "/api/navigation_key", new JSONObject().put($m.getBody(), value), 0L, new Function2() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda66
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit editKey$lambda$166$lambda$165;
                editKey$lambda$166$lambda$165 = GxNavigationPage.editKey$lambda$166$lambda$165(this$0, $m, value, (String) obj, (JSONObject) obj2);
                return editKey$lambda$166$lambda$165;
            }
        }, 8, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x002e, code lost:
    
        if (r0 == null) goto L16;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Unit editKey$lambda$166$lambda$165(GxNavigationPage this$0, KeyMeta $m, String $value, String e, JSONObject o) {
        String str;
        String optString;
        if (e != null) {
            String str2 = e;
            if (StringsKt.isBlank(str2)) {
                str2 = "Save failed...";
            }
            this$0.toast(str2);
            return Unit.INSTANCE;
        }
        if (o != null && (optString = o.optString("message")) != null) {
            String str3 = optString;
            if (StringsKt.isBlank(str3)) {
                str3 = null;
            }
            str = str3;
        }
        str = "Saved!";
        this$0.toast(str);
        this$0.savedKeys.put($m.getKind(), $value);
        if (Intrinsics.areEqual($m.getKind(), "public")) {
            this$0.mapboxPublic = $value;
        } else if (Intrinsics.areEqual($m.getKind(), "secret")) {
            this$0.mapboxSecret = $value;
        }
        this$0.renderKeys();
        this$0.renderNavAfterKeys();
        return Unit.INSTANCE;
    }

    private final void renderNavAfterKeys() {
        if (this.keyBox == null || this.navLoading) {
            return;
        }
        renderKeyNotice();
        renderDest();
    }

    private final void buildSpeed(LinearLayout tab) {
        header("Download Speed Limits", tab);
        note("Enable \"Speed Limit Filler\" on the device, drive to collect data, then process and download it here when parked.", tab);
        this.sStatus = statRow("Processor Status", tab);
        this.sVision = statRow("Vision Detector", tab);
        this.sDebugRow = GxPage.row$default(this, "Vision Debug", null, tab, 0, 8, null);
        GxPage.Row row = this.sDebugRow;
        if (row == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sDebugRow");
            row = null;
        }
        this.sDebug = row.value("");
        this.sLastEvent = note("", tab);
        TextView $this$buildSpeed_u24lambda_u24167 = note("", tab);
        $this$buildSpeed_u24lambda_u24167.setTextColor(CarUi.Companion.getWARN());
        this.sReason = $this$buildSpeed_u24lambda_u24167;
        this.sBar = buttons(new Pair[]{TuplesKt.to("Download", new Function0() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda50
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildSpeed$lambda$168;
                buildSpeed$lambda$168 = GxNavigationPage.buildSpeed$lambda$168(GxNavigationPage.this);
                return buildSpeed$lambda$168;
            }
        }), TuplesKt.to("Process Now", new Function0() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda51
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildSpeed$lambda$169;
                buildSpeed$lambda$169 = GxNavigationPage.buildSpeed$lambda$169(GxNavigationPage.this);
                return buildSpeed$lambda$169;
            }
        }), TuplesKt.to("Submit speed limits here", new Function0() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda52
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildSpeed$lambda$170;
                buildSpeed$lambda$170 = GxNavigationPage.buildSpeed$lambda$170(GxNavigationPage.this);
                return buildSpeed$lambda$170;
            }
        })}, tab, true);
        paintSpeed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildSpeed$lambda$168(GxNavigationPage this$0) {
        this$0.download("/api/speed_limits", "speed_limits.json", "application/json");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildSpeed$lambda$169(GxNavigationPage this$0) {
        this$0.processSpeed();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildSpeed$lambda$170(GxNavigationPage this$0) {
        this$0.openUrl("https://nerf.077769.xyz/");
        return Unit.INSTANCE;
    }

    private final void loadSpeed() {
        if (this.sStatus == null) {
            return;
        }
        getJson("/api/speed_limits/status", new Function2() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda73
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit loadSpeed$lambda$172;
                loadSpeed$lambda$172 = GxNavigationPage.loadSpeed$lambda$172(GxNavigationPage.this, (String) obj, (JSONObject) obj2);
                return loadSpeed$lambda$172;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit loadSpeed$lambda$172(GxNavigationPage this$0, String e, JSONObject d) {
        this$0.speedLoading = false;
        if (e != null || d == null) {
            this$0.speedStatus = "Unavailable";
            this$0.speedObj = new JSONObject().put("visionStatus", "Unavailable");
            this$0.canProcessNow = false;
            this$0.speedProcessing = false;
        } else {
            this$0.canProcessNow = d.optBoolean("canProcessNow");
            this$0.speedProcessing = d.optBoolean("processing");
            this$0.speedReason = GxPage.str$default(this$0, d, "reason", null, 2, null);
            String str$default = GxPage.str$default(this$0, d, "status", null, 2, null);
            if (str$default.length() == 0) {
                str$default = "Idle";
            }
            this$0.speedStatus = str$default;
            this$0.speedObj = d;
        }
        this$0.paintSpeed();
        return Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:53:0x0286  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0295  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x029d  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x02be  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x02cd  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x02d6  */
    /* JADX WARN: Removed duplicated region for block: B:91:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:92:0x02d2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void paintSpeed() {
        TextView textView;
        String str;
        TextView textView2;
        String str2;
        int i;
        TextView textView3;
        TextView textView4;
        LinearLayout linearLayout;
        TextView textView5;
        JSONObject d = this.speedObj;
        boolean enabled = d.optBoolean("visionEnabled");
        TextView textView6 = this.sStatus;
        if (textView6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sStatus");
            textView6 = null;
        }
        textView6.setText(this.speedLoading ? "Checking..." : this.speedStatus);
        TextView textView7 = this.sVision;
        if (textView7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sVision");
            textView = null;
        } else {
            textView = textView7;
        }
        if (!this.speedLoading) {
            String str$default = GxPage.str$default(this, d, "visionStatus", null, 2, null);
            if (str$default.length() == 0) {
                str$default = enabled ? "Idle" : "Disabled";
            }
            String out = str$default;
            String stream = GxPage.str$default(this, d, "visionStream", null, 2, null);
            if (stream.length() > 0) {
                out = out + " on " + stream;
            }
            Double valueOf = Double.valueOf(d.optDouble("visionDisplaySpeed", 0.0d));
            double it = valueOf.doubleValue();
            if (!((Double.isInfinite(it) || Double.isNaN(it)) ? false : true)) {
                valueOf = null;
            }
            double spd = valueOf != null ? valueOf.doubleValue() : 0.0d;
            if (spd > 0.0d) {
                String valueOf2 = ((spd % 1.0d) > 0.0d ? 1 : ((spd % 1.0d) == 0.0d ? 0 : -1)) == 0 ? String.valueOf((long) spd) : String.valueOf(spd);
                String str$default2 = GxPage.str$default(this, d, "visionSpeedUnit", null, 2, null);
                if (str$default2.length() == 0) {
                    str$default2 = "mph";
                }
                String out2 = out + " (" + valueOf2 + " " + ((Object) str$default2);
                Double valueOf3 = Double.valueOf(d.optDouble("visionConfidence", 0.0d));
                double it2 = valueOf3.doubleValue();
                if (!((Double.isInfinite(it2) || Double.isNaN(it2)) ? false : true)) {
                    valueOf3 = null;
                }
                double conf = valueOf3 != null ? valueOf3.doubleValue() : 0.0d;
                if (conf > 0.0d) {
                    out2 = out2 + ", " + Math.round(100 * conf) + "%";
                }
                out = out2 + ")";
            }
            str = out;
        }
        textView.setText(str);
        TextView textView8 = this.sVision;
        if (textView8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sVision");
            textView8 = null;
        }
        textView8.setMaxWidth(getUi().dp(520));
        GxPage.Row row = this.sDebugRow;
        if (row == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sDebugRow");
            row = null;
        }
        row.setVisible(!this.speedLoading && enabled);
        int bm = d.optInt("visionBookmarkCount", 0);
        TextView textView9 = this.sDebug;
        if (textView9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sDebug");
            textView2 = null;
        } else {
            textView2 = textView9;
        }
        String str$default3 = GxPage.str$default(this, d, "visionDebugSession", null, 2, null);
        if (str$default3.length() == 0) {
            str$default3 = "No active session";
        }
        if (bm > 0) {
            str2 = ", " + bm + " bookmark" + (bm != 1 ? "s" : "");
        }
        textView2.setText(((Object) str$default3) + str2);
        String ev = GxPage.str$default(this, d, "visionLastEvent", null, 2, null);
        TextView textView10 = this.sLastEvent;
        if (textView10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sLastEvent");
            textView10 = null;
        }
        textView10.setText(ev);
        TextView textView11 = this.sLastEvent;
        if (textView11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sLastEvent");
            textView11 = null;
        }
        int i2 = 8;
        if (!this.speedLoading && enabled) {
            if (ev.length() > 0) {
                i = 0;
                textView11.setVisibility(i);
                textView3 = this.sReason;
                if (textView3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("sReason");
                    textView3 = null;
                }
                textView3.setText(this.speedReason);
                textView4 = this.sReason;
                if (textView4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("sReason");
                    textView4 = null;
                }
                if (!this.speedLoading) {
                    if ((this.speedReason.length() > 0) && !Intrinsics.areEqual(this.speedReason, this.speedStatus)) {
                        i2 = 0;
                    }
                }
                textView4.setVisibility(i2);
                linearLayout = this.sBar;
                if (linearLayout == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("sBar");
                    linearLayout = null;
                }
                View childAt = linearLayout.getChildAt(1);
                textView5 = !(childAt instanceof TextView) ? (TextView) childAt : null;
                if (textView5 == null) {
                    TextView b = textView5;
                    b.setText((this.speedProcessing || this.speedSubmitting) ? "Processing..." : "Process Now");
                    setOn(b, (this.speedSubmitting || this.speedProcessing || !this.canProcessNow) ? false : true);
                    return;
                }
                return;
            }
        }
        i = 8;
        textView11.setVisibility(i);
        textView3 = this.sReason;
        if (textView3 == null) {
        }
        textView3.setText(this.speedReason);
        textView4 = this.sReason;
        if (textView4 == null) {
        }
        if (!this.speedLoading) {
        }
        textView4.setVisibility(i2);
        linearLayout = this.sBar;
        if (linearLayout == null) {
        }
        View childAt2 = linearLayout.getChildAt(1);
        if (!(childAt2 instanceof TextView)) {
        }
        if (textView5 == null) {
        }
    }

    private final void processSpeed() {
        if (this.speedSubmitting || this.speedProcessing || !this.canProcessNow) {
            return;
        }
        this.speedSubmitting = true;
        paintSpeed();
        GxPage.send$default(this, "POST", "/api/speed_limits/process", null, 0L, new Function2() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit processSpeed$lambda$180;
                processSpeed$lambda$180 = GxNavigationPage.processSpeed$lambda$180(GxNavigationPage.this, (String) obj, (JSONObject) obj2);
                return processSpeed$lambda$180;
            }
        }, 12, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit processSpeed$lambda$180(GxNavigationPage this$0, String e, JSONObject o) {
        String str;
        String optString;
        this$0.speedSubmitting = false;
        if (e == null) {
            str = null;
            if (o != null && (optString = o.optString("message")) != null) {
                String str2 = optString;
                if (!StringsKt.isBlank(str2)) {
                    str = str2;
                }
                str = str;
            }
            if (str == null) {
                str = "Speed limit processing started.";
            }
        } else {
            str = e;
        }
        this$0.toast(str);
        this$0.loadSpeed();
        return Unit.INSTANCE;
    }
}
