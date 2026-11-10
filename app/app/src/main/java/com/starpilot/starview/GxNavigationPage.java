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
import android.content.Context;
import android.widget.LinearLayout.LayoutParams;
import java.util.Map.Entry;

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

    /* VF: from Vineflower (jadx: ) */
    public GxNavigationPage(Activity var1, GxHost var2) {
        super(var1, var2);
        Intrinsics.checkNotNullParameter(var1, "act");
        Intrinsics.checkNotNullParameter(var2, "gx");
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

    @Override // com.starpilot.starview.GxPage
    public boolean getNeedsOffroad() {
        return this.needsOffroad;
    }

    @Override // com.starpilot.starview.GxPage
    public void build() {
        GxPage.header$default(this, "Navigation & Maps", null, 2, null);
        this.selectTab = tabs(CollectionsKt.listOf(new String[]{"Destination", "Maps", "App Keys", "Speed Limits"}), 0, new Function1() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda68
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

    /* VF: from Vineflower (jadx: ) */
    private final String labelFor(JSONObject var1) {
        Object var11 = null; // declarations Vineflower left out
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

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
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
                GxNavigationPage.extGet$lambda$19(u, GxNavigationPage.this, then);
            }
        }).start();
    }

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
            JSONObject d = new JSONObject(saved.toString()).put("latitude", ((Number) savedCoords.getFirst()).doubleValue()).put("longitude", ((Number) savedCoords.getSecond()).doubleValue());
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

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    private final List<Pair<String, String>> searchContext(String var1) {
        boolean var15 = false; // declarations Vineflower left out
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
                search$lambda$38 = GxNavigationPage.search$lambda$38(req, GxNavigationPage.this, (String) obj, (JSONObject) obj2);
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
        JSONObject put = jSONObject.put("name", str$default).put("longitude", ((Number) c.getSecond()).doubleValue()).put("latitude", ((Number) c.getFirst()).doubleValue());
        Triple<Double, Double, String> triple = this.routeSummary;
        if (triple == null || (str = triple.getThird()) == null) {
            str = ((String) JSONObject.NULL);
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
        Double d = (!Double.isInfinite(it2) && !Double.isNaN(it2)) ? valueOf3 : null;
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

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    public static final Unit previewDestination$lambda$71(GxNavigationPage var0, JSONObject var1, String var2, Pair var3, String var4, JSONObject var5) {
        Object var13 = null; List var17 = null; // declarations Vineflower left out
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

    /* VF: from Vineflower (jadx: ) */
    private final void renderDest() {
        LinearLayout var32 = null; // declarations Vineflower left out
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
                int var1;
                if (var19.length() == 0) {
                    var1 = 1;
                } else {
                    var1 = 0;
                }

                if ((var1 != 0)) {
                    var var20 = this.getQuery();
                    if (var20.length() == 0) {
                        var1 = (((boolean)true) ? 1 : 0);
                    } else {
                        var1 = (((boolean)false) ? 1 : 0);
                    }

                    if ((var1 != 0)) {
                        var20 = var36;
                    }

                    var19 = var20;
                }

                this.text(var19, (ViewGroup)var6, 24.0F, CarUi.Companion.getTEXT(), true);
                String var21 = GxPage.str$default(this, var7, "place_name", null, 2, null);
                if (var21.length() > 0) {
                    var1 = (((boolean)true) ? 1 : 0);
                } else {
                    var1 = (((boolean)false) ? 1 : 0);
                }

                if ((var1 != 0) && !StringsKt.equals(var21, GxPage.str$default(this, var7, "name", null, 2, null), true)) {
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
                    var1 = (((boolean)true) ? 1 : 0);
                } else {
                    var1 = (((boolean)false) ? 1 : 0);
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
                if ((var1 != 0)) {
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
                        if (Intrinsics.areEqual(this.routeIdOf(((Number)(Object)var5).intValue()), this.selectedRouteId)) {
                            var32 = var5;
                            break;
                        }
                    }

                    Integer var33 = (Integer)(Object)var32;
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
        for (final JSONObject f : (Iterable<JSONObject>) CollectionsKt.sortedWith($this$sortedBy$iv, new Comparator() { // from class: com.starpilot.starview.GxNavigationPage$renderFavorites$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(Object t, Object t2) {
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
                renameFavorite$lambda$91 = GxNavigationPage.renameFavorite$lambda$91(old, f, GxNavigationPage.this, (String) obj);
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
                homeWork$lambda$93 = GxNavigationPage.homeWork$lambda$93(f, home, work, GxNavigationPage.this, ((Integer) obj).intValue());
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

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
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

                    for (JSONObject var33 : (Iterable<JSONObject>) var32) {
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
        List units = CollectionsKt.listOf(new String[]{"B", "KB", "MB", "GB", "TB"});
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
        ArrayList arrayList = (ArrayList) destination$iv$iv;
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

    /* VF: from Vineflower (jadx: Code restructure failed;Removed duplicated region;incorrect) */
    private final void paintMaps() {
        if (this.vDownloader != null) {
            Object var12 = this.mapsErrorNote;
            TextView var11 = ((TextView) var12);
            if (var12 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mapsErrorNote");
                var11 = null;
            }

            var11.setText(this.mapsError);
            var12 = this.mapsErrorNote;
            var11 = ((TextView) var12);
            if (var12 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mapsErrorNote");
                var11 = null;
            }

            int var5;
            if (this.mapsError.length() == 0) {
                var5 = 1;
            } else {
                var5 = 0;
            }

            if ((var5 != 0)) {
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

            ((TextView) var12).setText(var48);
            var12 = this.vSaved;
            var11 = ((TextView) var12);
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
            int var22;
            if (!Double.isInfinite(var1) && !Double.isNaN(var1)) {
                var22 = 1;
            } else {
                var22 = 0;
            }

            if ((var22 == 0)) {
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
            ((TextView) var12).setText(var55);
            var11 = this.vLastUpdate;
            if (var11 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("vLastUpdate");
                var11 = null;
            }

            var12 = GxPage.str$default(this, var14, "lastUpdate", null, 2, null);
            if (var12.length() == 0) {
                var22 = (((boolean)true) ? 1 : 0);
            } else {
                var22 = (((boolean)false) ? 1 : 0);
            }

            if ((var22 != 0)) {
                var12 = "Never";
            }

            var11.setText((CharSequence)var12);
            var12 = this.vStorage;
            var11 = ((TextView) var12);
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
            var11 = ((TextView) var12);
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
            var11 = ((TextView) var12);
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
            var11 = ((TextView) var12);
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
            int var27;
            if (!this.mapsLoadingStatus && !this.mapsActionBusy && !this.isOnroad() && !this.isDownloading() && !this.selectedDraft.isEmpty()) {
                var27 = 1;
            } else {
                var27 = 0;
            }

            int var6;
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
                TextView var12s = (TextView)var62;
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

                ((TextView) var12).setText(var63);
                boolean var8;
                if ((var27 == 0) && (var6 == 0)) {
                    var8 = false;
                } else {
                    var8 = true;
                }

                this.setOn(((TextView) var12), var8);
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
                TextView var11sssss = (TextView)var67;
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
                TextView var11ssss = (TextView)var70;
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
                TextView var11sss = (TextView)var75;
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
                TextView var11ss = (TextView)var78;
            } else {
                var11 = null;
            }

            this.setOn(var11, this.selectedDraft.isEmpty() ^ true);
            boolean var45 = var13.optBoolean("completed");
            var7 = var13.optBoolean("cancelled");
            if (this.mapsLoadingStatus || !this.isDownloading() && !var45 && !var7 && !(var1 > 0.0)) {
                var27 = (((boolean)false) ? 1 : 0);
            } else {
                var27 = (((boolean)true) ? 1 : 0);
            }

            LinearLayout var125 = this.progCard;
            LinearLayout var80 = var125;
            if (var125 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("progCard");
                var80 = null;
            }

            if ((var27 != 0)) {
                var6 = (byte)0;
            } else {
                var6 = (byte)8;
            }

            var80.setVisibility(var6);
            if ((var27 != 0)) {
                Double var81 = var13.optDouble("percent", 0.0);
                double var3 = var81.doubleValue();
                if (!Double.isInfinite(var3) && !Double.isNaN(var3)) {
                    var27 = (((boolean)true) ? 1 : 0);
                } else {
                    var27 = (((boolean)false) ? 1 : 0);
                }

                if ((var27 == 0)) {
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
                ((TextView) var12).setText(var83 + "  ·  " + var9 + "%");
                ProgressBar var127 = this.progBar;
                ProgressBar var84 = var127;
                if (var127 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("progBar");
                    var84 = null;
                }

                this.setProgress(var84, var3);
                var12 = this.pEstimate;
                var11 = ((TextView) var12);
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
                var11 = ((TextView) var12);
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
                var11 = ((TextView) var12);
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
                var11 = ((TextView) var12);
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
                var11 = ((TextView) var12);
                if (var12 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("pRegion");
                    var11 = null;
                }

                var11.setText("Current region: " + var156);
                var12 = this.pRegion;
                var11 = ((TextView) var12);
                if (var12 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("pRegion");
                    var11 = null;
                }

                int var31;
                if (var156.length() == 0) {
                    var31 = 1;
                } else {
                    var31 = 0;
                }

                if ((var31 != 0)) {
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
                    TextView var11s = (TextView)var98;
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
            var11 = ((TextView) var12);
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

                for (String var157 : (Iterable<String>) var147) {
                    String var148 = this.tokenLabels.get(var157);
                    if (var148 == null) {
                        var148 = var157;
                    }

                    var15.add(var148);
                }

                List var149 = var15;
                Iterable var150 = var149;
                var146 = CollectionsKt.joinToString(CollectionsKt.sortedWith(var150, new Comparator() {
                    @Override
                    public final int compare(Object var1, Object var2) {
                        var1 = var1.toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(var1, "toLowerCase(...)");
                        Comparable var4 = ((Comparable) var1);
                        var2 = var2.toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(var2, "toLowerCase(...)");
                        return ComparisonsKt.compareValues((Comparable)var4, (Comparable)var2);
                    }
                }), ", ", "", "", -1, "...", null);
            }

            var11.setText(var146);
            var12 = this.selRegionsText;
            var11 = ((TextView) var12);
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
            var11 = ((TextView) var12);
            if (var12 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("schedNote");
                var11 = null;
            }

            var12 = GxPage.str$default(this, var14, "scheduleLabel", null, 2, null);
            int var35;
            if (var12.length() == 0) {
                var35 = 1;
            } else {
                var35 = 0;
            }

            if ((var35 != 0)) {
                var12 = "Monthly";
            }

            var35 = this.selectedDraft.size();
            var11.setText("Saved schedule: " + var12 + " · Draft regions: " + var35);

            for (java.util.Map.Entry var158 : this.regionSwitches.entrySet()) {
                String var154 = (String)var158.getKey();
                Switch var159 = (Switch)var158.getValue();
                var7 = this.selectedDraft.contains(var154);
                if (var159.isChecked() != var7) {
                    var159.setChecked(var7);
                }
            }

            Iterator var160 = this.groupRows.entrySet().iterator();

            while (var160.hasNext()) {
                Pair var106 = (Pair)((java.util.Map.Entry)var160.next()).getValue();
                GxPage.Row var155 = (GxPage.Row)var106.getFirst();
                var35 = this.groupSelectedCount((JSONObject)var106.getSecond());
                var155.setDesc(var35 + " selected");
            }
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

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    public final void renderCatalog() {
        List var48 = null; // declarations Vineflower left out
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

                    for (JSONObject var16 : (Iterable<JSONObject>) var10) {
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
                                    if (!StringsKt.contains(var19, var13, false)) {
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

                                for (JSONObject var61 : (Iterable<JSONObject>) var73) {
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

                if ((var1 == 0)) {
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
        Iterable $this$firstOrNull$iv = CollectionsKt.listOf(new String[]{"pk.", "sk."});
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

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public static final Unit keyRow$lambda$162$lambda$161$lambda$160(GxNavigationPage var0, GxNavigationPage.KeyMeta var1, String var2, JSONObject var3) {
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

    private final void editKey(final KeyMeta m) {
        GxPage.prompt$default(this, m.getTitle() + " key", m.getPrefix() + "xxxxxx...", "", 524433, null, new Function1() { // from class: com.starpilot.starview.GxNavigationPage$$ExternalSyntheticLambda31
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit editKey$lambda$166;
                editKey$lambda$166 = GxNavigationPage.editKey$lambda$166(m, GxNavigationPage.this, (String) obj);
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

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public static final Unit editKey$lambda$166$lambda$165(GxNavigationPage var0, GxNavigationPage.KeyMeta var1, String var2, String var3, JSONObject var4) {
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

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    private final void paintSpeed() {
        JSONObject var14 = this.speedObj;
        boolean var7 = var14.optBoolean("visionEnabled");
        Object var11 = this.sStatus;
        TextView var10 = ((TextView) var11);
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

        ((TextView) var11).setText(var30);
        var11 = this.sVision;
        var10 = ((TextView) var11);
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
        int var23;
        if (var11.length() == 0) {
            var23 = 1;
        } else {
            var23 = 0;
        }

        if ((var23 != 0)) {
            var11 = "No active session";
        }

        String var62 = "";
        String var59 = var62;
        if (var4 > 0) {
            if (var4 != 1) {
                var62 = "s";
            }

            var59 = ", " + var4 + " bookmark" + var62;
        }

        var10.setText(var11 + var59);
        var59 = GxPage.str$default(this, var14, "visionLastEvent", null, 2, null);
        var11 = this.sLastEvent;
        var10 = ((TextView) var11);
        if (var11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sLastEvent");
            var10 = null;
        }

        var10.setText((CharSequence)var59);
        var11 = this.sLastEvent;
        var10 = ((TextView) var11);
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
        var10 = ((TextView) var11);
        if (var11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sReason");
            var10 = null;
        }

        var10.setText(this.speedReason);
        var11 = this.sReason;
        var10 = ((TextView) var11);
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
