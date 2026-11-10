package com.starpilot.starview;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.starpilot.starview.GxPage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: GxHome.kt */
public final class GxHomePage extends GxPage {
    private LinearLayout body;
    private Boolean devOnline;
    private String devStatus;
    private String error;
    private final List<Integer> favColors;
    private GxPage.Row hero;
    private boolean keepRefreshing;
    private JSONObject payload;
    private boolean refreshing;
    private String status;
    private String togglingKey;
    private String unit;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GxHomePage(Activity act, GxHost gx) {
        super(act, gx);
        Intrinsics.checkNotNullParameter(act, "act");
        Intrinsics.checkNotNullParameter(gx, "gx");
        this.status = "loading";
        this.error = "";
        this.unit = "miles";
        this.togglingKey = "";
        Iterable $this$map$iv = CollectionsKt.listOf((Object[]) new String[]{"#5ec8c8", "#8b6cc5", "#d4a060", "#e05577", "#6cc56e", "#8aa3ff"});
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            String it = (String) item$iv$iv;
            destination$iv$iv.add(Integer.valueOf(Color.parseColor(it)));
        }
        this.favColors = (List) destination$iv$iv;
    }

    @Override // com.starpilot.starview.GxPage
    public void build() {
        GxPage.Row row;
        this.hero = GxPage.row$default(this, "Dashboard", "Loading dashboard...", null, 0, 12, null);
        GxPage.Row row2 = this.hero;
        if (row2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("hero");
            row2 = null;
        }
        row2.getTitleView().setTextSize(26.0f);
        GxPage.Row row3 = this.hero;
        if (row3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("hero");
            row3 = null;
        }
        row3.getTitleView().setTypeface(Typeface.DEFAULT_BOLD);
        GxPage.Row row4 = this.hero;
        if (row4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("hero");
            row = null;
        } else {
            row = row4;
        }
        GxPage.Row.button$default(row, "Refresh", false, new Function0() { // from class: com.starpilot.starview.GxHomePage$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit build$lambda$1;
                build$lambda$1 = GxHomePage.build$lambda$1(GxHomePage.this);
                return build$lambda$1;
            }
        }, 2, null);
        this.body = GxPage.box$default(this, null, 1, null);
        every(3500L, new Function0() { // from class: com.starpilot.starview.GxHomePage$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit build$lambda$2;
                build$lambda$2 = GxHomePage.build$lambda$2(GxHomePage.this);
                return build$lambda$2;
            }
        });
        every(5000L, new Function0() { // from class: com.starpilot.starview.GxHomePage$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit build$lambda$3;
                build$lambda$3 = GxHomePage.build$lambda$3(GxHomePage.this);
                return build$lambda$3;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$1(GxHomePage this$0) {
        this$0.refresh();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$2(GxHomePage this$0) {
        if (this$0.keepRefreshing) {
            this$0.load();
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$3(GxHomePage this$0) {
        this$0.refreshStatus();
        return Unit.INSTANCE;
    }

    @Override // com.starpilot.starview.GxPage
    public void onShow() {
        load();
    }

    private final JSONObject dash() {
        JSONObject optJSONObject;
        JSONObject jSONObject = this.payload;
        return (jSONObject == null || (optJSONObject = jSONObject.optJSONObject("dashboard")) == null) ? new JSONObject() : optJSONObject;
    }

    private final void refreshStatus() {
        getJson("/api/device/status", new Function2() { // from class: com.starpilot.starview.GxHomePage$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit refreshStatus$lambda$5;
                refreshStatus$lambda$5 = GxHomePage.refreshStatus$lambda$5(GxHomePage.this, (String) obj, (JSONObject) obj2);
                return refreshStatus$lambda$5;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit refreshStatus$lambda$5(GxHomePage this$0, String e, JSONObject o) {
        if (e != null || o == null) {
            this$0.devOnline = false;
        } else {
            this$0.devOnline = true;
            String str = "Parked";
            String str2 = this$0.str(o, "status", "Parked");
            if (!StringsKt.isBlank(str2)) {
                str = str2;
            }
            this$0.devStatus = str;
        }
        this$0.paintHero();
        return Unit.INSTANCE;
    }

    private final void paintHero() {
        GxPage.Row row = null;
        if (this.payload == null) {
            GxPage.Row row2 = this.hero;
            if (row2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("hero");
            } else {
                row = row2;
            }
            row.setDesc(Intrinsics.areEqual(this.status, "error") ? "Failed to load dashboard" : "Loading dashboard...");
            return;
        }
        JSONObject device = dash().optJSONObject("device");
        if (device == null) {
            device = new JSONObject();
        }
        boolean z = false;
        if (!Intrinsics.areEqual((Object) this.devOnline, (Object) false) && !Intrinsics.areEqual(device.opt("online"), (Object) false)) {
            z = true;
        }
        boolean online = z;
        String st = this.devStatus;
        if (st == null) {
            String str$default = GxPage.str$default(this, device, "status", null, 2, null);
            if (StringsKt.isBlank(str$default)) {
                str$default = "Parked";
            }
            st = str$default;
        }
        GxPage.Row row3 = this.hero;
        if (row3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("hero");
        } else {
            row = row3;
        }
        row.setDesc(st + " - " + (online ? "device online" : "device offline"));
    }

    private final void refresh() {
        if (Intrinsics.areEqual(this.status, "loading")) {
            return;
        }
        this.status = this.payload != null ? "ready" : "loading";
        this.error = "";
        load();
    }

    private final boolean driveReady(JSONObject d) {
        return Intrinsics.areEqual(d.opt("ignored"), (Object) true) || !Intrinsics.areEqual(d.opt("attentionKnown"), (Object) false);
    }

    private final boolean hasPendingWork(JSONObject dash) {
        double gxHomeNum;
        List recent;
        boolean gxHomeTruthy;
        Iterable $this$any$iv;
        JSONObject a = dash.optJSONObject("analysis");
        if (a == null) {
            a = new JSONObject();
        }
        gxHomeNum = GxHomeKt.gxHomeNum(a.opt("pendingRoutes"));
        long pending = Math.max(0L, Math.round(gxHomeNum));
        JSONArray optJSONArray = dash.optJSONArray("recentDrives");
        if (optJSONArray == null || (recent = objects(optJSONArray)) == null) {
            recent = CollectionsKt.emptyList();
        }
        gxHomeTruthy = GxHomeKt.gxHomeTruthy(a.opt("running"));
        if (gxHomeTruthy || pending > 0) {
            return true;
        }
        List $this$any$iv2 = recent;
        if (!($this$any$iv2 instanceof Collection) || !$this$any$iv2.isEmpty()) {
            Iterator it = $this$any$iv2.iterator();
            while (true) {
                if (it.hasNext()) {
                    Object element$iv = it.next();
                    JSONObject it2 = (JSONObject) element$iv;
                    if (!driveReady(it2)) {
                        $this$any$iv = 1;
                        break;
                    }
                } else {
                    $this$any$iv = null;
                    break;
                }
            }
        } else {
            $this$any$iv = null;
        }
        return $this$any$iv != null;
    }

    private final void load() {
        if (this.refreshing) {
            return;
        }
        this.refreshing = true;
        if (this.payload == null) {
            this.status = "loading";
        }
        this.error = "";
        getJson("/api/stats", new Function2() { // from class: com.starpilot.starview.GxHomePage$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit load$lambda$11;
                load$lambda$11 = GxHomePage.load$lambda$11(GxHomePage.this, (String) obj, (JSONObject) obj2);
                return load$lambda$11;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0078  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Unit load$lambda$11(GxHomePage this$0, String e, JSONObject data) {
        JSONObject optJSONObject;
        String str$default;
        String pu;
        String str;
        JSONObject optJSONObject2;
        JSONObject optJSONObject3;
        String str$default2;
        this$0.refreshing = false;
        if (e == null && data != null) {
            JSONObject optJSONObject4 = data.optJSONObject("dashboard");
            String str2 = null;
            if (optJSONObject4 != null && (optJSONObject3 = optJSONObject4.optJSONObject("week")) != null && (str$default2 = GxPage.str$default(this$0, optJSONObject3, "distanceUnit", null, 2, null)) != null) {
                String str3 = str$default2;
                if (StringsKt.isBlank(str3)) {
                    str3 = null;
                }
                String str4 = str3;
                if (str4 != null) {
                    str2 = str4;
                    pu = str2;
                    this$0.payload = data;
                    if (pu != null) {
                        String str5 = this$0.unit;
                        if (StringsKt.isBlank(str5)) {
                            str5 = "miles";
                        }
                        str = str5;
                    } else {
                        str = pu;
                    }
                    this$0.unit = str;
                    this$0.status = "ready";
                    optJSONObject2 = data.optJSONObject("dashboard");
                    if (optJSONObject2 == null) {
                        optJSONObject2 = new JSONObject();
                    }
                    this$0.keepRefreshing = this$0.hasPendingWork(optJSONObject2);
                }
            }
            JSONObject optJSONObject5 = data.optJSONObject("driveStats");
            if (optJSONObject5 != null && (optJSONObject = optJSONObject5.optJSONObject("all")) != null && (str$default = GxPage.str$default(this$0, optJSONObject, "unit", null, 2, null)) != null) {
                String str6 = str$default;
                if (!StringsKt.isBlank(str6)) {
                    str2 = str6;
                }
                str2 = str2;
            }
            pu = str2;
            this$0.payload = data;
            if (pu != null) {
            }
            this$0.unit = str;
            this$0.status = "ready";
            optJSONObject2 = data.optJSONObject("dashboard");
            if (optJSONObject2 == null) {
            }
            this$0.keepRefreshing = this$0.hasPendingWork(optJSONObject2);
        } else if (this$0.payload != null) {
            this$0.toast("Couldn't refresh dashboard.");
        } else {
            this$0.status = "error";
            this$0.error = e == null ? "empty stats payload" : e;
        }
        this$0.render();
        return Unit.INSTANCE;
    }

    static /* synthetic */ List columns$default(GxHomePage gxHomePage, int i, ViewGroup viewGroup, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            LinearLayout linearLayout = gxHomePage.body;
            if (linearLayout == null) {
                Intrinsics.throwUninitializedPropertyAccessException("body");
                linearLayout = null;
            }
            viewGroup = linearLayout;
        }
        return gxHomePage.columns(i, viewGroup);
    }

    private final List<LinearLayout> columns(int n, ViewGroup into) {
        LinearLayout rowL = new LinearLayout(getAct());
        rowL.setOrientation(0);
        into.addView(rowL, new LinearLayout.LayoutParams(-1, -2));
        Iterable $this$map$iv = RangesKt.until(0, n);
        int $i$f$map = 0;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        Iterator<Integer> it = $this$map$iv.iterator();
        while (it.hasNext()) {
            int item$iv$iv = ((IntIterator) it).nextInt();
            LinearLayout $this$columns_u24lambda_u2416_u24lambda_u2413 = new LinearLayout(getAct());
            $this$columns_u24lambda_u2416_u24lambda_u2413.setOrientation(1);
            LinearLayout linearLayout = $this$columns_u24lambda_u2416_u24lambda_u2413;
            Iterable $this$map$iv2 = $this$map$iv;
            int $i$f$map2 = $i$f$map;
            LinearLayout.LayoutParams $this$columns_u24lambda_u2416_u24lambda_u2415_u24lambda_u2414 = new LinearLayout.LayoutParams(0, -2, 1.0f);
            if (item$iv$iv < n - 1) {
                $this$columns_u24lambda_u2416_u24lambda_u2415_u24lambda_u2414.rightMargin = getUi().dp(8);
            }
            Unit unit = Unit.INSTANCE;
            rowL.addView(linearLayout, $this$columns_u24lambda_u2416_u24lambda_u2415_u24lambda_u2414);
            destination$iv$iv.add($this$columns_u24lambda_u2416_u24lambda_u2413);
            $this$map$iv = $this$map$iv2;
            $i$f$map = $i$f$map2;
        }
        return (List) destination$iv$iv;
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void cardHead$default(GxHomePage gxHomePage, LinearLayout linearLayout, String str, Pair pair, int i, Object obj) {
        if ((i & 4) != 0) {
            pair = null;
        }
        gxHomePage.cardHead(linearLayout, str, pair);
    }

    private final void cardHead(LinearLayout c, String title, final Pair<String, String> link) {
        LinearLayout h = new LinearLayout(getAct());
        h.setOrientation(0);
        h.setGravity(16);
        h.setPadding(0, 0, 0, getUi().dp(6));
        TextView $this$cardHead_u24lambda_u2418 = new TextView(getAct());
        $this$cardHead_u24lambda_u2418.setText(title);
        $this$cardHead_u24lambda_u2418.setTextSize(21.0f);
        $this$cardHead_u24lambda_u2418.setTextColor(CarUi.Companion.getTEXT());
        $this$cardHead_u24lambda_u2418.setTypeface(Typeface.DEFAULT_BOLD);
        h.addView($this$cardHead_u24lambda_u2418, new LinearLayout.LayoutParams(0, -2, 1.0f));
        if (link != null) {
            h.addView(CarUi.button$default(getUi(), link.getFirst(), false, new Function0() { // from class: com.starpilot.starview.GxHomePage$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit cardHead$lambda$19;
                    cardHead$lambda$19 = GxHomePage.cardHead$lambda$19(GxHomePage.this, link);
                    return cardHead$lambda$19;
                }
            }, 2, null));
        }
        c.addView(h);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit cardHead$lambda$19(GxHomePage this$0, Pair $link) {
        this$0.getGx().openPage((String) $link.getSecond());
        return Unit.INSTANCE;
    }

    private final void metrics(LinearLayout c, List<Pair<String, String>> items) {
        LinearLayout r = new LinearLayout(getAct());
        r.setOrientation(0);
        r.setPadding(0, getUi().dp(6), 0, getUi().dp(6));
        for (Pair<String, String> pair : items) {
            String v = pair.component1();
            String l = pair.component2();
            LinearLayout cell = new LinearLayout(getAct());
            cell.setOrientation(1);
            TextView $this$metrics_u24lambda_u2422 = new TextView(getAct());
            $this$metrics_u24lambda_u2422.setText(v);
            $this$metrics_u24lambda_u2422.setTextSize(26.0f);
            $this$metrics_u24lambda_u2422.setTextColor(CarUi.Companion.getTEXT());
            $this$metrics_u24lambda_u2422.setTypeface(Typeface.DEFAULT_BOLD);
            cell.addView($this$metrics_u24lambda_u2422);
            TextView $this$metrics_u24lambda_u2423 = new TextView(getAct());
            $this$metrics_u24lambda_u2423.setText(l);
            $this$metrics_u24lambda_u2423.setTextSize(14.0f);
            $this$metrics_u24lambda_u2423.setTextColor(CarUi.Companion.getDIM());
            cell.addView($this$metrics_u24lambda_u2423);
            r.addView(cell, new LinearLayout.LayoutParams(0, -2, 1.0f));
        }
        c.addView(r);
    }

    private final void kv(LinearLayout c, String label, String value) {
        LinearLayout r = new LinearLayout(getAct());
        r.setOrientation(0);
        r.setGravity(16);
        r.setPadding(0, getUi().dp(8), 0, getUi().dp(8));
        TextView $this$kv_u24lambda_u2425 = new TextView(getAct());
        $this$kv_u24lambda_u2425.setText(label);
        $this$kv_u24lambda_u2425.setTextSize(17.0f);
        $this$kv_u24lambda_u2425.setTextColor(CarUi.Companion.getDIM());
        r.addView($this$kv_u24lambda_u2425, new LinearLayout.LayoutParams(0, -2, 1.0f));
        TextView $this$kv_u24lambda_u2426 = new TextView(getAct());
        $this$kv_u24lambda_u2426.setText(value);
        $this$kv_u24lambda_u2426.setTextSize(17.0f);
        $this$kv_u24lambda_u2426.setTextColor(CarUi.Companion.getTEXT());
        $this$kv_u24lambda_u2426.setTypeface(Typeface.DEFAULT_BOLD);
        $this$kv_u24lambda_u2426.setGravity(8388613);
        $this$kv_u24lambda_u2426.setPadding(getUi().dp(12), 0, 0, 0);
        r.addView($this$kv_u24lambda_u2426);
        c.addView(r);
    }

    /* JADX WARN: Code restructure failed: missing block: B:152:0x094a, code lost:
    
        if (r5 != null) goto L203;
     */
    /* JADX WARN: Removed duplicated region for block: B:217:0x0c28  */
    /* JADX WARN: Removed duplicated region for block: B:223:0x0cdc  */
    /* JADX WARN: Removed duplicated region for block: B:230:0x0df2  */
    /* JADX WARN: Removed duplicated region for block: B:237:0x0ee5  */
    /* JADX WARN: Removed duplicated region for block: B:251:0x0f5b  */
    /* JADX WARN: Removed duplicated region for block: B:255:0x0d31  */
    /* JADX WARN: Removed duplicated region for block: B:257:0x0c3a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void render() {
        JSONObject driveStats;
        JSONObject jSONObject;
        double gxHomeNum;
        Iterable emptyList;
        int count$iv;
        boolean running;
        double gxHomeNum2;
        int pendingRoutes;
        String gxHomeRange;
        String str;
        String str2;
        JSONObject software;
        String str3;
        String gxHomeDuration;
        JSONObject device;
        String str4;
        String str5;
        String gxHomePct;
        String gxHomeDec;
        String gxHomeDec2;
        String gxHomeInt;
        List it;
        List days;
        Double valueOf;
        List drives;
        List<JSONObject> emptyList2;
        double n;
        String gxHomeInt2;
        double gxHomeNum3;
        String gxHomeInt3;
        String gxHomeInt4;
        String gxHomeInt5;
        String gxHomeBytes;
        String str6;
        String str7;
        String gxHomeInt6;
        String gxHomeInt7;
        String gxHomeBytes2;
        String gxHomeBytes3;
        int i;
        JSONObject dash;
        String str8;
        Iterator it2;
        int i2;
        List emptyList3;
        String gxHomeDec3;
        String str9;
        String distance;
        String gxHomePct2;
        String str10;
        String gxHomeInt8;
        String gxHomeInt9;
        Pair pair;
        String str11;
        String str12;
        String gxHomeRange2;
        String str$default;
        String gxHomeDuration2;
        boolean gxHomeTruthy;
        List cols;
        JSONObject records;
        String value;
        Object it3;
        Object it4;
        String gxHomeDec4;
        double gxHomeNum4;
        String gxHomeInt10;
        String gxHomeDec5;
        String gxHomeDec6;
        String gxHomeInt11;
        String gxHomeInt12;
        String gxHomeDec7;
        LinearLayout linearLayout;
        LinearLayout linearLayout2;
        paintHero();
        LinearLayout linearLayout3 = this.body;
        String str13 = "body";
        if (linearLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("body");
            linearLayout3 = null;
        }
        linearLayout3.removeAllViews();
        if (Intrinsics.areEqual(this.status, "loading") && this.payload == null) {
            LinearLayout linearLayout4 = this.body;
            if (linearLayout4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("body");
                linearLayout2 = null;
            } else {
                linearLayout2 = linearLayout4;
            }
            linearLayout2.addView(empty("Loading dashboard..."));
            return;
        }
        if (Intrinsics.areEqual(this.status, "error") && this.payload == null) {
            LinearLayout linearLayout5 = this.body;
            if (linearLayout5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("body");
                linearLayout = null;
            } else {
                linearLayout = linearLayout5;
            }
            LinearLayout c = card(linearLayout);
            text("Failed to load dashboard", c, 21.0f, CarUi.Companion.getWARN(), true);
            GxPage.text$default(this, this.error, c, 0.0f, 0, false, 28, null);
            GxPage.buttons$default(this, new Pair[]{TuplesKt.to("Refresh", new Function0() { // from class: com.starpilot.starview.GxHomePage$$ExternalSyntheticLambda5
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit render$lambda$27;
                    render$lambda$27 = GxHomePage.render$lambda$27(GxHomePage.this);
                    return render$lambda$27;
                }
            })}, c, false, 4, null);
            return;
        }
        JSONObject dash2 = dash();
        JSONObject jSONObject2 = this.payload;
        if (jSONObject2 == null || (driveStats = jSONObject2.optJSONObject("driveStats")) == null) {
            driveStats = new JSONObject();
        }
        JSONObject jSONObject3 = this.payload;
        if (jSONObject3 == null || (jSONObject = jSONObject3.optJSONObject("softwareInfo")) == null) {
            jSONObject = new JSONObject();
        }
        JSONObject software2 = jSONObject;
        JSONObject device2 = dash2.optJSONObject("device");
        if (device2 == null) {
            device2 = new JSONObject();
        }
        JSONObject week = dash2.optJSONObject("week");
        if (week == null) {
            week = new JSONObject();
        }
        JSONObject storage = dash2.optJSONObject("storage");
        if (storage == null) {
            storage = new JSONObject();
        }
        GxHomePage $this$render_u24lambda_u2429 = this;
        JSONObject a = dash2.optJSONObject("analysis");
        if (a == null) {
            a = new JSONObject();
        }
        gxHomeNum = GxHomeKt.gxHomeNum(a.opt("pendingRoutes"));
        JSONObject driveStats2 = driveStats;
        int pendingRoutes2 = (int) Math.max(0L, Math.round(gxHomeNum));
        JSONArray optJSONArray = dash2.optJSONArray("recentDrives");
        if (optJSONArray == null || (emptyList = $this$render_u24lambda_u2429.objects(optJSONArray)) == null) {
            emptyList = CollectionsKt.emptyList();
        }
        Iterable $this$count$iv = emptyList;
        if (($this$count$iv instanceof Collection) && ((Collection) $this$count$iv).isEmpty()) {
            count$iv = 0;
        } else {
            count$iv = 0;
            for (Object element$iv : $this$count$iv) {
                JSONObject it5 = (JSONObject) element$iv;
                if (!$this$render_u24lambda_u2429.driveReady(it5)) {
                    count$iv++;
                    if (count$iv < 0) {
                        CollectionsKt.throwCountOverflow();
                    }
                }
            }
        }
        int pendingDrives = count$iv;
        int count = Math.max(pendingRoutes2, pendingDrives);
        running = GxHomeKt.gxHomeTruthy(a.opt("running"));
        String str14 = "drives";
        if (running || count > 0) {
            if (count != 0) {
                pendingRoutes = count;
            } else {
                gxHomeNum2 = GxHomeKt.gxHomeNum(a.opt("batchSize"));
                pendingRoutes = (int) Math.max(1L, Math.round(gxHomeNum2));
            }
            String label = (running ? new StringBuilder().append("Analyzing ").append(pendingRoutes).append(" ").append(pendingRoutes == 1 ? "drive" : "drives") : new StringBuilder().append(count).append(" ").append(count == 1 ? "drive" : "drives").append(" queued")).toString();
            LinearLayout linearLayout6 = $this$render_u24lambda_u2429.body;
            if (linearLayout6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("body");
                linearLayout6 = null;
            }
            $this$render_u24lambda_u2429.note(label, linearLayout6).setTextColor(CarUi.Companion.getACCENT());
        }
        Unit unit = Unit.INSTANCE;
        GxHomePage $this$render_u24lambda_u2433 = this;
        JSONObject d = dash2.optJSONObject("lastDrive");
        if (d == null) {
            d = new JSONObject();
        }
        boolean ready = $this$render_u24lambda_u2433.driveReady(d);
        String str$default2 = GxPage.str$default($this$render_u24lambda_u2433, d, "distanceUnit", null, 2, null);
        if (StringsKt.isBlank(str$default2)) {
            str$default2 = $this$render_u24lambda_u2433.unit;
        }
        String u = str$default2;
        String str$default3 = GxPage.str$default($this$render_u24lambda_u2433, d, "speedUnit", null, 2, null);
        if (StringsKt.isBlank(str$default3)) {
            str$default3 = Intrinsics.areEqual(u, "kilometers") ? "kph" : "mph";
        }
        String su = str$default3;
        LinearLayout linearLayout7 = $this$render_u24lambda_u2433.body;
        if (linearLayout7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("body");
            linearLayout7 = null;
        }
        LinearLayout c2 = $this$render_u24lambda_u2433.card(linearLayout7);
        cardHead$default($this$render_u24lambda_u2433, c2, "Last drive", null, 4, null);
        JSONObject jSONObject4 = d;
        gxHomeRange = GxHomeKt.gxHomeRange(GxPage.str$default($this$render_u24lambda_u2433, jSONObject4, "date", null, 2, null), GxPage.str$default($this$render_u24lambda_u2433, jSONObject4, "endDate", null, 2, null));
        GxPage.text$default($this$render_u24lambda_u2433, gxHomeRange, c2, 18.0f, CarUi.Companion.getTEXT(), false, 16, null);
        Pair[] pairArr = new Pair[4];
        String engaged = "distance";
        if (ready) {
            gxHomeDec7 = GxHomeKt.gxHomeDec(d.opt("distance"));
            str = " ";
            str2 = gxHomeDec7;
        } else {
            str = " ";
            str2 = "...";
        }
        if (ready) {
            software = software2;
            str3 = u;
        } else {
            software = software2;
            str3 = "analyzing";
        }
        pairArr[0] = TuplesKt.to(str2, str3);
        String str15 = "duration";
        gxHomeDuration = GxHomeKt.gxHomeDuration(d.opt("duration"));
        pairArr[1] = TuplesKt.to(gxHomeDuration, "duration");
        String gxHomeInt13 = ready ? GxHomeKt.gxHomeInt(d.opt("avgSpeed")) : "...";
        if (ready) {
            device = device2;
            str4 = su + " avg";
        } else {
            device = device2;
            str4 = "speed";
        }
        pairArr[2] = TuplesKt.to(gxHomeInt13, str4);
        String str16 = "engagedPercent";
        pairArr[3] = TuplesKt.to(ready ? GxHomeKt.gxHomePct(d.opt("engagedPercent")) : "...", "engaged");
        $this$render_u24lambda_u2433.metrics(c2, CollectionsKt.listOf((Object[]) pairArr));
        String[] strArr = new String[1];
        String str$default4 = GxPage.str$default($this$render_u24lambda_u2433, d, "model", null, 2, null);
        if (StringsKt.isBlank(str$default4)) {
            str$default4 = "Unknown model";
        }
        strArr[0] = str$default4;
        List tags = CollectionsKt.mutableListOf(strArr);
        if (ready) {
            gxHomeInt11 = GxHomeKt.gxHomeInt(d.opt("distractedMoments"));
            tags.add(gxHomeInt11 + " distracted");
            gxHomeInt12 = GxHomeKt.gxHomeInt(d.opt("unresponsiveMoments"));
            str5 = gxHomeInt12 + " unresponsive";
        } else {
            str5 = "Analyzing stats";
        }
        tags.add(str5);
        GxPage.text$default($this$render_u24lambda_u2433, CollectionsKt.joinToString(tags, "   ·   ", "", "", -1, "...", null), c2, 0.0f, 0, false, 28, null);
        LinearLayout linearLayout8 = this.body;
        if (linearLayout8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("body");
            linearLayout8 = null;
        }
        header("Your driving", linearLayout8);
        GxHomePage $this$render_u24lambda_u2435 = this;
        int i3 = 0;
        List cols2 = columns$default($this$render_u24lambda_u2435, 3, null, 2, null);
        int i4 = 0;
        for (Pair g : CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to("All time", "all"), TuplesKt.to("Past week", "week"), TuplesKt.to("StarPilot", "starpilot")})) {
            int i5 = i4;
            int i6 = i4 + 1;
            JSONObject driveStats3 = driveStats2;
            JSONObject s = driveStats3.optJSONObject((String) g.getSecond());
            if (s == null) {
                s = new JSONObject();
            }
            int i7 = i3;
            LinearLayout c3 = $this$render_u24lambda_u2435.card((ViewGroup) cols2.get(i5));
            JSONObject s2 = s;
            JSONObject storage2 = storage;
            List cols3 = cols2;
            cardHead$default($this$render_u24lambda_u2435, c3, (String) g.getFirst(), null, 4, null);
            Pair[] pairArr2 = new Pair[3];
            gxHomeInt10 = GxHomeKt.gxHomeInt(s2.opt("drives"));
            pairArr2[0] = TuplesKt.to(gxHomeInt10, "drives");
            gxHomeDec5 = GxHomeKt.gxHomeDec(s2.opt("distance"));
            String str$default5 = GxPage.str$default($this$render_u24lambda_u2435, s2, "unit", null, 2, null);
            if (StringsKt.isBlank(str$default5)) {
                str$default5 = $this$render_u24lambda_u2435.unit;
            }
            pairArr2[1] = TuplesKt.to(gxHomeDec5, str$default5);
            gxHomeDec6 = GxHomeKt.gxHomeDec(s2.opt("hours"));
            pairArr2[2] = TuplesKt.to(gxHomeDec6, "hours");
            $this$render_u24lambda_u2435.metrics(c3, CollectionsKt.listOf((Object[]) pairArr2));
            i3 = i7;
            i4 = i6;
            cols2 = cols3;
            driveStats2 = driveStats3;
            storage = storage2;
        }
        JSONObject storage3 = storage;
        Unit unit2 = Unit.INSTANCE;
        GxHomePage $this$render_u24lambda_u2454 = this;
        int i8 = 0;
        List cols4 = columns$default($this$render_u24lambda_u2454, 2, null, 2, null);
        LinearLayout c4 = $this$render_u24lambda_u2454.card((ViewGroup) cols4.get(0));
        cardHead$default($this$render_u24lambda_u2454, c4, "This week", null, 4, null);
        String str17 = str;
        String str$default6 = GxPage.str$default($this$render_u24lambda_u2454, week, "distanceUnit", null, 2, null);
        if (StringsKt.isBlank(str$default6)) {
            str$default6 = $this$render_u24lambda_u2454.unit;
        }
        String wu = str$default6;
        gxHomePct = GxHomeKt.gxHomePct(week.opt("engagedPercent"));
        String str18 = gxHomePct + " engaged";
        LinearLayout c5 = c4;
        $this$render_u24lambda_u2454.text(str18, c5, 24.0f, CarUi.Companion.getACCENT(), true);
        $this$render_u24lambda_u2454.setProgress($this$render_u24lambda_u2454.progress(c5), GxHomeKt.gxHomeClamp$default(week.opt("engagedPercent"), 0.0d, 0.0d, 6, null));
        gxHomeDec = GxHomeKt.gxHomeDec(week.opt("distance"));
        gxHomeDec2 = GxHomeKt.gxHomeDec(week.opt("hours"));
        gxHomeInt = GxHomeKt.gxHomeInt(week.opt("drives"));
        $this$render_u24lambda_u2454.metrics(c5, CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to(gxHomeDec, wu), TuplesKt.to(gxHomeDec2, "hours"), TuplesKt.to(gxHomeInt, "drives")}));
        JSONArray optJSONArray2 = week.optJSONArray("dailyDistance");
        if (optJSONArray2 == null || (it = $this$render_u24lambda_u2454.objects(optJSONArray2)) == null || it.isEmpty()) {
            it = null;
        }
        if (it != null) {
            List $this$map$iv = it;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            for (Object item$iv$iv : $this$map$iv) {
                JSONObject it6 = (JSONObject) item$iv$iv;
                int i9 = i8;
                String str$default7 = GxPage.str$default($this$render_u24lambda_u2454, it6, "label", null, 2, null);
                gxHomeNum4 = GxHomeKt.gxHomeNum(it6.opt("distance"));
                List daysArr = it;
                destination$iv$iv.add(TuplesKt.to(str$default7, Double.valueOf(gxHomeNum4)));
                it = daysArr;
                i8 = i9;
            }
            days = (List) destination$iv$iv;
        } else {
            Iterable $this$map$iv2 = CollectionsKt.listOf((Object[]) new String[]{"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"});
            int $i$f$map = 0;
            Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv2, 10));
            for (Object item$iv$iv2 : $this$map$iv2) {
                Iterable $this$map$iv3 = $this$map$iv2;
                String it7 = (String) item$iv$iv2;
                destination$iv$iv2.add(TuplesKt.to(it7, Double.valueOf(0.0d)));
                $this$map$iv2 = $this$map$iv3;
                $i$f$map = $i$f$map;
            }
            days = (List) destination$iv$iv2;
        }
        Iterator it8 = days.iterator();
        if (it8.hasNext()) {
            Pair it9 = (Pair) it8.next();
            double doubleValue = ((Number) it9.getSecond()).doubleValue();
            while (it8.hasNext()) {
                Pair it10 = (Pair) it8.next();
                doubleValue = Math.max(doubleValue, ((Number) it10.getSecond()).doubleValue());
                it8 = it8;
            }
            valueOf = Double.valueOf(doubleValue);
        } else {
            valueOf = null;
        }
        double maxD = Math.max(1.0d, valueOf != null ? valueOf.doubleValue() : 1.0d);
        LinearLayout $this$render_u24lambda_u2454_u24lambda_u2441 = new LinearLayout($this$render_u24lambda_u2454.getAct());
        $this$render_u24lambda_u2454_u24lambda_u2441.setOrientation(0);
        $this$render_u24lambda_u2454_u24lambda_u2441.setGravity(80);
        $this$render_u24lambda_u2454_u24lambda_u2441.setPadding(0, $this$render_u24lambda_u2454.getUi().dp(8), 0, 0);
        LinearLayout bars = $this$render_u24lambda_u2454_u24lambda_u2441;
        int barMax = $this$render_u24lambda_u2454.getUi().dp(110);
        Iterator it11 = days.iterator();
        while (true) {
            List days2 = days;
            if (!it11.hasNext()) {
                break;
            }
            Pair pair2 = (Pair) it11.next();
            String label2 = (String) pair2.component1();
            double dist = ((Number) pair2.component2()).doubleValue();
            LinearLayout $this$render_u24lambda_u2454_u24lambda_u2442 = new LinearLayout($this$render_u24lambda_u2454.getAct());
            Iterator it12 = it11;
            $this$render_u24lambda_u2454_u24lambda_u2442.setOrientation(1);
            $this$render_u24lambda_u2454_u24lambda_u2442.setGravity(81);
            TextView $this$render_u24lambda_u2454_u24lambda_u2443 = new TextView($this$render_u24lambda_u2454.getAct());
            gxHomeDec4 = GxHomeKt.gxHomeDec(Double.valueOf(dist));
            String str19 = str14;
            $this$render_u24lambda_u2454_u24lambda_u2443.setText(gxHomeDec4);
            $this$render_u24lambda_u2454_u24lambda_u2443.setTextSize(12.0f);
            $this$render_u24lambda_u2454_u24lambda_u2443.setTextColor(CarUi.Companion.getDIM());
            $this$render_u24lambda_u2454_u24lambda_u2443.setGravity(17);
            $this$render_u24lambda_u2454_u24lambda_u2442.addView($this$render_u24lambda_u2454_u24lambda_u2443);
            double h = (Math.max(4.0d, (dist / maxD) * 100) / 100.0d) * barMax;
            View $this$render_u24lambda_u2454_u24lambda_u2444 = new View($this$render_u24lambda_u2454.getAct());
            $this$render_u24lambda_u2454_u24lambda_u2444.setBackground(CarUi.rounded$default($this$render_u24lambda_u2454.getUi(), CarUi.Companion.getACCENT(), 4, null, 4, null));
            LinearLayout.LayoutParams $this$render_u24lambda_u2454_u24lambda_u2445 = new LinearLayout.LayoutParams($this$render_u24lambda_u2454.getUi().dp(26), (int) h);
            $this$render_u24lambda_u2454_u24lambda_u2445.topMargin = $this$render_u24lambda_u2454.getUi().dp(2);
            Unit unit3 = Unit.INSTANCE;
            $this$render_u24lambda_u2454_u24lambda_u2442.addView($this$render_u24lambda_u2454_u24lambda_u2444, $this$render_u24lambda_u2454_u24lambda_u2445);
            TextView $this$render_u24lambda_u2454_u24lambda_u2446 = new TextView($this$render_u24lambda_u2454.getAct());
            $this$render_u24lambda_u2454_u24lambda_u2446.setText(label2);
            $this$render_u24lambda_u2454_u24lambda_u2446.setTextSize(14.0f);
            $this$render_u24lambda_u2454_u24lambda_u2446.setTextColor(CarUi.Companion.getTEXT());
            $this$render_u24lambda_u2454_u24lambda_u2446.setGravity(17);
            $this$render_u24lambda_u2454_u24lambda_u2446.setPadding(0, $this$render_u24lambda_u2454.getUi().dp(4), 0, 0);
            $this$render_u24lambda_u2454_u24lambda_u2442.addView($this$render_u24lambda_u2454_u24lambda_u2446);
            bars.addView($this$render_u24lambda_u2454_u24lambda_u2442, new LinearLayout.LayoutParams(0, $this$render_u24lambda_u2454.getUi().dp(48) + barMax, 1.0f));
            it11 = it12;
            days = days2;
            str14 = str19;
            str15 = str15;
            maxD = maxD;
        }
        String str20 = str14;
        String str21 = str15;
        c5.addView(bars);
        List cols5 = cols4;
        LinearLayout r = $this$render_u24lambda_u2454.card((ViewGroup) cols5.get(1));
        cardHead$default($this$render_u24lambda_u2454, r, "Personal records", null, 4, null);
        JSONObject records2 = dash2.optJSONObject("records");
        if (records2 == null) {
            records2 = new JSONObject();
        }
        Iterator it13 = CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to("Longest drive", "longestDrive"), TuplesKt.to("Most-engaged day", "mostEngagedDay"), TuplesKt.to("Best week", "bestWeek"), TuplesKt.to("Highest streak", "highestStreak"), TuplesKt.to("Longest undistracted drive", "longestUndistractedDrive"), TuplesKt.to("Clean-drive streak", "cleanDriveStreak")}).iterator();
        while (it13.hasNext()) {
            Pair pair3 = (Pair) it13.next();
            String title = (String) pair3.component1();
            String k = (String) pair3.component2();
            JSONObject rec = records2.optJSONObject(k);
            if (rec == null || (it4 = rec.opt("value")) == null) {
                cols = cols5;
                records = records2;
            } else {
                cols = cols5;
                records = records2;
                if (Intrinsics.areEqual(it4, JSONObject.NULL)) {
                    it4 = null;
                }
                if (it4 != null) {
                    value = it4.toString();
                }
            }
            value = "0";
            if (rec != null && (it3 = rec.opt("detail")) != null) {
                Object obj = it3;
                if (Intrinsics.areEqual(it3, JSONObject.NULL)) {
                    obj = null;
                }
                if (obj != null && (detail = obj.toString()) != null) {
                    LinearLayout $this$render_u24lambda_u2454_u24lambda_u2449 = new LinearLayout($this$render_u24lambda_u2454.getAct());
                    $this$render_u24lambda_u2454_u24lambda_u2449.setOrientation(0);
                    $this$render_u24lambda_u2454_u24lambda_u2449.setGravity(16);
                    LinearLayout bars2 = bars;
                    $this$render_u24lambda_u2454_u24lambda_u2449.setPadding(0, $this$render_u24lambda_u2454.getUi().dp(7), 0, $this$render_u24lambda_u2454.getUi().dp(7));
                    LinearLayout tb = new LinearLayout($this$render_u24lambda_u2454.getAct());
                    tb.setOrientation(1);
                    TextView $this$render_u24lambda_u2454_u24lambda_u2451 = new TextView($this$render_u24lambda_u2454.getAct());
                    $this$render_u24lambda_u2454_u24lambda_u2451.setText(title);
                    $this$render_u24lambda_u2454_u24lambda_u2451.setTextSize(15.0f);
                    $this$render_u24lambda_u2454_u24lambda_u2451.setTextColor(CarUi.Companion.getDIM());
                    tb.addView($this$render_u24lambda_u2454_u24lambda_u2451);
                    TextView $this$render_u24lambda_u2454_u24lambda_u2452 = new TextView($this$render_u24lambda_u2454.getAct());
                    $this$render_u24lambda_u2454_u24lambda_u2452.setText(value);
                    $this$render_u24lambda_u2454_u24lambda_u2452.setTextSize(19.0f);
                    $this$render_u24lambda_u2454_u24lambda_u2452.setTextColor(CarUi.Companion.getTEXT());
                    $this$render_u24lambda_u2454_u24lambda_u2452.setTypeface(Typeface.DEFAULT_BOLD);
                    tb.addView($this$render_u24lambda_u2454_u24lambda_u2452);
                    $this$render_u24lambda_u2454_u24lambda_u2449.addView(tb, new LinearLayout.LayoutParams(0, -2, 1.0f));
                    TextView $this$render_u24lambda_u2454_u24lambda_u2453 = new TextView($this$render_u24lambda_u2454.getAct());
                    $this$render_u24lambda_u2454_u24lambda_u2453.setText(detail);
                    $this$render_u24lambda_u2454_u24lambda_u2453.setTextSize(14.0f);
                    $this$render_u24lambda_u2454_u24lambda_u2453.setTextColor(CarUi.Companion.getDIM());
                    $this$render_u24lambda_u2454_u24lambda_u2453.setGravity(8388613);
                    $this$render_u24lambda_u2454_u24lambda_u2453.setPadding($this$render_u24lambda_u2454.getUi().dp(10), 0, 0, 0);
                    $this$render_u24lambda_u2454_u24lambda_u2449.addView($this$render_u24lambda_u2454_u24lambda_u2453);
                    r.addView($this$render_u24lambda_u2454_u24lambda_u2449);
                    barMax = barMax;
                    it13 = it13;
                    records2 = records;
                    bars = bars2;
                    c5 = c5;
                    cols5 = cols;
                }
            }
            String detail = "";
            LinearLayout $this$render_u24lambda_u2454_u24lambda_u24492 = new LinearLayout($this$render_u24lambda_u2454.getAct());
            $this$render_u24lambda_u2454_u24lambda_u24492.setOrientation(0);
            $this$render_u24lambda_u2454_u24lambda_u24492.setGravity(16);
            LinearLayout bars22 = bars;
            $this$render_u24lambda_u2454_u24lambda_u24492.setPadding(0, $this$render_u24lambda_u2454.getUi().dp(7), 0, $this$render_u24lambda_u2454.getUi().dp(7));
            LinearLayout tb2 = new LinearLayout($this$render_u24lambda_u2454.getAct());
            tb2.setOrientation(1);
            TextView $this$render_u24lambda_u2454_u24lambda_u24512 = new TextView($this$render_u24lambda_u2454.getAct());
            $this$render_u24lambda_u2454_u24lambda_u24512.setText(title);
            $this$render_u24lambda_u2454_u24lambda_u24512.setTextSize(15.0f);
            $this$render_u24lambda_u2454_u24lambda_u24512.setTextColor(CarUi.Companion.getDIM());
            tb2.addView($this$render_u24lambda_u2454_u24lambda_u24512);
            TextView $this$render_u24lambda_u2454_u24lambda_u24522 = new TextView($this$render_u24lambda_u2454.getAct());
            $this$render_u24lambda_u2454_u24lambda_u24522.setText(value);
            $this$render_u24lambda_u2454_u24lambda_u24522.setTextSize(19.0f);
            $this$render_u24lambda_u2454_u24lambda_u24522.setTextColor(CarUi.Companion.getTEXT());
            $this$render_u24lambda_u2454_u24lambda_u24522.setTypeface(Typeface.DEFAULT_BOLD);
            tb2.addView($this$render_u24lambda_u2454_u24lambda_u24522);
            $this$render_u24lambda_u2454_u24lambda_u24492.addView(tb2, new LinearLayout.LayoutParams(0, -2, 1.0f));
            TextView $this$render_u24lambda_u2454_u24lambda_u24532 = new TextView($this$render_u24lambda_u2454.getAct());
            $this$render_u24lambda_u2454_u24lambda_u24532.setText(detail);
            $this$render_u24lambda_u2454_u24lambda_u24532.setTextSize(14.0f);
            $this$render_u24lambda_u2454_u24lambda_u24532.setTextColor(CarUi.Companion.getDIM());
            $this$render_u24lambda_u2454_u24lambda_u24532.setGravity(8388613);
            $this$render_u24lambda_u2454_u24lambda_u24532.setPadding($this$render_u24lambda_u2454.getUi().dp(10), 0, 0, 0);
            $this$render_u24lambda_u2454_u24lambda_u24492.addView($this$render_u24lambda_u2454_u24lambda_u24532);
            r.addView($this$render_u24lambda_u2454_u24lambda_u24492);
            barMax = barMax;
            it13 = it13;
            records2 = records;
            bars = bars22;
            c5 = c5;
            cols5 = cols;
        }
        Unit unit4 = Unit.INSTANCE;
        final GxHomePage $this$render_u24lambda_u2471 = this;
        int i10 = 0;
        LinearLayout linearLayout9 = $this$render_u24lambda_u2471.body;
        if (linearLayout9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("body");
            linearLayout9 = null;
        }
        LinearLayout c6 = $this$render_u24lambda_u2471.card(linearLayout9);
        cardHead$default($this$render_u24lambda_u2471, c6, "Recent drives", null, 4, null);
        JSONArray optJSONArray3 = dash2.optJSONArray("recentDrives");
        if (optJSONArray3 == null || (drives = $this$render_u24lambda_u2471.objects(optJSONArray3)) == null) {
            drives = CollectionsKt.emptyList();
        }
        if (drives.isEmpty()) {
            GxPage.text$default($this$render_u24lambda_u2471, "No local drives found yet.", c6, 17.0f, 0, false, 24, null);
        }
        Iterator it14 = drives.iterator();
        int i11 = 0;
        while (it14.hasNext()) {
            int i12 = i11;
            int i13 = i11 + 1;
            JSONObject d2 = (JSONObject) it14.next();
            boolean ignored = Intrinsics.areEqual(d2.opt("ignored"), (Object) true);
            boolean ready2 = $this$render_u24lambda_u2471.driveReady(d2);
            boolean pending = (ready2 || ignored) ? false : true;
            String str$default8 = GxPage.str$default($this$render_u24lambda_u2471, d2, "distanceUnit", null, 2, null);
            if (StringsKt.isBlank(str$default8)) {
                i = i10;
                str$default8 = $this$render_u24lambda_u2471.unit;
            } else {
                i = i10;
            }
            String u2 = str$default8;
            List drives2 = drives;
            JSONArray a2 = d2.optJSONArray("routeNames");
            if (a2 != null) {
                it2 = it14;
                i2 = i13;
                Iterable $this$map$iv4 = RangesKt.until(0, a2.length());
                dash = dash2;
                str8 = str13;
                Collection destination$iv$iv3 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv4, 10));
                Iterable $this$mapTo$iv$iv = $this$map$iv4;
                Iterator<Integer> it15 = $this$mapTo$iv$iv.iterator();
                while (it15.hasNext()) {
                    int item$iv$iv3 = ((IntIterator) it15).nextInt();
                    destination$iv$iv3.add(a2.opt(item$iv$iv3));
                    $this$mapTo$iv$iv = $this$mapTo$iv$iv;
                }
                Iterable $this$filter$iv = (List) destination$iv$iv3;
                Collection destination$iv$iv4 = new ArrayList();
                for (Object element$iv$iv : $this$filter$iv) {
                    Iterable $this$filter$iv2 = $this$filter$iv;
                    gxHomeTruthy = GxHomeKt.gxHomeTruthy(element$iv$iv);
                    if (gxHomeTruthy) {
                        destination$iv$iv4.add(element$iv$iv);
                    }
                    $this$filter$iv = $this$filter$iv2;
                }
                Iterable $this$map$iv5 = (List) destination$iv$iv4;
                int $i$f$map2 = CollectionsKt.collectionSizeOrDefault($this$map$iv5, 10);
                Collection destination$iv$iv5 = new ArrayList($i$f$map2);
                for (Object item$iv$iv4 : $this$map$iv5) {
                    destination$iv$iv5.add(item$iv$iv4.toString());
                }
                emptyList3 = (List) destination$iv$iv5;
            } else {
                dash = dash2;
                str8 = str13;
                it2 = it14;
                i2 = i13;
                emptyList3 = CollectionsKt.emptyList();
            }
            final List routeNames = emptyList3;
            if (ignored) {
                distance = "Stats excluded";
            } else if (pending) {
                distance = "Analyzing stats";
            } else {
                gxHomeDec3 = GxHomeKt.gxHomeDec(d2.opt(engaged));
                str9 = str17;
                distance = gxHomeDec3 + str9 + u2;
                if (!ignored) {
                    pair = TuplesKt.to("Excluded", CollectionsKt.listOf("Ignored from stats"));
                    str10 = engaged;
                } else if (pending) {
                    pair = TuplesKt.to("Pending", CollectionsKt.listOf("Waiting for full route analysis"));
                    str10 = engaged;
                } else {
                    gxHomePct2 = GxHomeKt.gxHomePct(d2.opt(str16));
                    str10 = engaged;
                    gxHomeInt8 = GxHomeKt.gxHomeInt(d2.opt("distractedMoments"));
                    gxHomeInt9 = GxHomeKt.gxHomeInt(d2.opt("unresponsiveMoments"));
                    pair = TuplesKt.to(gxHomePct2 + " engaged", CollectionsKt.listOf((Object[]) new String[]{gxHomeInt8 + " distracted", gxHomeInt9 + " unresponsive"}));
                }
                String engaged2 = (String) pair.component1();
                List attention = (List) pair.component2();
                double engagedValue = (ready2 || ignored) ? 0.0d : GxHomeKt.gxHomeClamp$default(d2.opt(str16), 0.0d, 0.0d, 6, null);
                if (i12 <= 0) {
                    str11 = str16;
                    View $this$render_u24lambda_u2471_u24lambda_u2460 = new View($this$render_u24lambda_u2471.getAct());
                    str12 = str9;
                    $this$render_u24lambda_u2471_u24lambda_u2460.setBackgroundColor(Color.rgb(40, 44, 52));
                    LinearLayout.LayoutParams $this$render_u24lambda_u2471_u24lambda_u2461 = new LinearLayout.LayoutParams(-1, $this$render_u24lambda_u2471.getUi().dp(1));
                    $this$render_u24lambda_u2471_u24lambda_u2461.topMargin = $this$render_u24lambda_u2471.getUi().dp(6);
                    $this$render_u24lambda_u2471_u24lambda_u2461.bottomMargin = $this$render_u24lambda_u2471.getUi().dp(6);
                    Unit unit5 = Unit.INSTANCE;
                    c6.addView($this$render_u24lambda_u2471_u24lambda_u2460, $this$render_u24lambda_u2471_u24lambda_u2461);
                } else {
                    str11 = str16;
                    str12 = str9;
                }
                LinearLayout line = new LinearLayout($this$render_u24lambda_u2471.getAct());
                line.setOrientation(0);
                line.setGravity(16);
                line.setPadding(0, $this$render_u24lambda_u2471.getUi().dp(6), 0, $this$render_u24lambda_u2471.getUi().dp(6));
                LinearLayout left = new LinearLayout($this$render_u24lambda_u2471.getAct());
                left.setOrientation(1);
                left.setAlpha((!ignored || pending) ? 0.7f : 1.0f);
                TextView $this$render_u24lambda_u2471_u24lambda_u2464 = new TextView($this$render_u24lambda_u2471.getAct());
                LinearLayout c7 = c6;
                gxHomeRange2 = GxHomeKt.gxHomeRange(GxPage.str$default($this$render_u24lambda_u2471, d2, "date", null, 2, null), GxPage.str$default($this$render_u24lambda_u2471, d2, "endDate", null, 2, null));
                $this$render_u24lambda_u2471_u24lambda_u2464.setText(gxHomeRange2);
                $this$render_u24lambda_u2471_u24lambda_u2464.setTextSize(18.0f);
                $this$render_u24lambda_u2471_u24lambda_u2464.setTextColor(CarUi.Companion.getTEXT());
                $this$render_u24lambda_u2471_u24lambda_u2464.setTypeface(Typeface.DEFAULT_BOLD);
                left.addView($this$render_u24lambda_u2471_u24lambda_u2464);
                TextView $this$render_u24lambda_u2471_u24lambda_u2466 = new TextView($this$render_u24lambda_u2471.getAct());
                str$default = GxPage.str$default($this$render_u24lambda_u2471, d2, "model", null, 2, null);
                if (StringsKt.isBlank(str$default)) {
                    str$default = "Unknown model";
                }
                $this$render_u24lambda_u2471_u24lambda_u2466.setText(str$default);
                $this$render_u24lambda_u2471_u24lambda_u2466.setTextSize(15.0f);
                $this$render_u24lambda_u2471_u24lambda_u2466.setTextColor(CarUi.Companion.getDIM());
                left.addView($this$render_u24lambda_u2471_u24lambda_u2466);
                TextView $this$render_u24lambda_u2471_u24lambda_u2467 = new TextView($this$render_u24lambda_u2471.getAct());
                String str22 = str21;
                gxHomeDuration2 = GxHomeKt.gxHomeDuration(d2.opt(str22));
                $this$render_u24lambda_u2471_u24lambda_u2467.setText(distance + "   ·   " + gxHomeDuration2);
                $this$render_u24lambda_u2471_u24lambda_u2467.setTextSize(16.0f);
                $this$render_u24lambda_u2471_u24lambda_u2467.setTextColor(CarUi.Companion.getTEXT());
                $this$render_u24lambda_u2471_u24lambda_u2467.setPadding(0, $this$render_u24lambda_u2471.getUi().dp(4), 0, 0);
                left.addView($this$render_u24lambda_u2471_u24lambda_u2467);
                $this$render_u24lambda_u2471.setProgress($this$render_u24lambda_u2471.progress(left), engagedValue);
                TextView $this$render_u24lambda_u2471_u24lambda_u2468 = new TextView($this$render_u24lambda_u2471.getAct());
                $this$render_u24lambda_u2471_u24lambda_u2468.setText(CollectionsKt.joinToString(CollectionsKt.plus((Collection) CollectionsKt.listOf(engaged2), (Iterable) attention), "   ·   ", "", "", -1, "...", null));
                $this$render_u24lambda_u2471_u24lambda_u2468.setTextSize(15.0f);
                $this$render_u24lambda_u2471_u24lambda_u2468.setTextColor((!pending || ignored) ? CarUi.Companion.getDIM() : CarUi.Companion.getTEXT());
                left.addView($this$render_u24lambda_u2471_u24lambda_u2468);
                line.addView(left, new LinearLayout.LayoutParams(0, -2, 1.0f));
                if (routeNames.isEmpty()) {
                    final String key = CollectionsKt.joinToString(routeNames, ",", "", "", -1, "...", null);
                    final String action = ignored ? "include" : "ignore";
                    TextView b = CarUi.button$default($this$render_u24lambda_u2471.getUi(), ignored ? "Include drive stats" : "Ignore drive stats", false, new Function0() { // from class: com.starpilot.starview.GxHomePage$$ExternalSyntheticLambda6
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            Unit render$lambda$71$lambda$69;
                            render$lambda$71$lambda$69 = GxHomePage.render$lambda$71$lambda$69(GxHomePage.this, key, action, routeNames);
                            return render$lambda$71$lambda$69;
                        }
                    }, 2, null);
                    if (Intrinsics.areEqual($this$render_u24lambda_u2471.togglingKey, key)) {
                        b.setAlpha(0.45f);
                    }
                    LinearLayout.LayoutParams $this$render_u24lambda_u2471_u24lambda_u2470 = new LinearLayout.LayoutParams(-2, -2);
                    $this$render_u24lambda_u2471_u24lambda_u2470.leftMargin = $this$render_u24lambda_u2471.getUi().dp(12);
                    Unit unit6 = Unit.INSTANCE;
                    line.addView(b, $this$render_u24lambda_u2471_u24lambda_u2470);
                }
                c7.addView(line);
                c6 = c7;
                str21 = str22;
                drives = drives2;
                i10 = i;
                engaged = str10;
                it14 = it2;
                i11 = i2;
                str13 = str8;
                str16 = str11;
                dash2 = dash;
                str17 = str12;
            }
            str9 = str17;
            if (!ignored) {
            }
            String engaged22 = (String) pair.component1();
            List attention2 = (List) pair.component2();
            if (ready2) {
            }
            if (i12 <= 0) {
            }
            LinearLayout line2 = new LinearLayout($this$render_u24lambda_u2471.getAct());
            line2.setOrientation(0);
            line2.setGravity(16);
            line2.setPadding(0, $this$render_u24lambda_u2471.getUi().dp(6), 0, $this$render_u24lambda_u2471.getUi().dp(6));
            LinearLayout left2 = new LinearLayout($this$render_u24lambda_u2471.getAct());
            left2.setOrientation(1);
            left2.setAlpha((!ignored || pending) ? 0.7f : 1.0f);
            TextView $this$render_u24lambda_u2471_u24lambda_u24642 = new TextView($this$render_u24lambda_u2471.getAct());
            LinearLayout c72 = c6;
            gxHomeRange2 = GxHomeKt.gxHomeRange(GxPage.str$default($this$render_u24lambda_u2471, d2, "date", null, 2, null), GxPage.str$default($this$render_u24lambda_u2471, d2, "endDate", null, 2, null));
            $this$render_u24lambda_u2471_u24lambda_u24642.setText(gxHomeRange2);
            $this$render_u24lambda_u2471_u24lambda_u24642.setTextSize(18.0f);
            $this$render_u24lambda_u2471_u24lambda_u24642.setTextColor(CarUi.Companion.getTEXT());
            $this$render_u24lambda_u2471_u24lambda_u24642.setTypeface(Typeface.DEFAULT_BOLD);
            left2.addView($this$render_u24lambda_u2471_u24lambda_u24642);
            TextView $this$render_u24lambda_u2471_u24lambda_u24662 = new TextView($this$render_u24lambda_u2471.getAct());
            str$default = GxPage.str$default($this$render_u24lambda_u2471, d2, "model", null, 2, null);
            if (StringsKt.isBlank(str$default)) {
            }
            $this$render_u24lambda_u2471_u24lambda_u24662.setText(str$default);
            $this$render_u24lambda_u2471_u24lambda_u24662.setTextSize(15.0f);
            $this$render_u24lambda_u2471_u24lambda_u24662.setTextColor(CarUi.Companion.getDIM());
            left2.addView($this$render_u24lambda_u2471_u24lambda_u24662);
            TextView $this$render_u24lambda_u2471_u24lambda_u24672 = new TextView($this$render_u24lambda_u2471.getAct());
            String str222 = str21;
            gxHomeDuration2 = GxHomeKt.gxHomeDuration(d2.opt(str222));
            $this$render_u24lambda_u2471_u24lambda_u24672.setText(distance + "   ·   " + gxHomeDuration2);
            $this$render_u24lambda_u2471_u24lambda_u24672.setTextSize(16.0f);
            $this$render_u24lambda_u2471_u24lambda_u24672.setTextColor(CarUi.Companion.getTEXT());
            $this$render_u24lambda_u2471_u24lambda_u24672.setPadding(0, $this$render_u24lambda_u2471.getUi().dp(4), 0, 0);
            left2.addView($this$render_u24lambda_u2471_u24lambda_u24672);
            $this$render_u24lambda_u2471.setProgress($this$render_u24lambda_u2471.progress(left2), engagedValue);
            TextView $this$render_u24lambda_u2471_u24lambda_u24682 = new TextView($this$render_u24lambda_u2471.getAct());
            $this$render_u24lambda_u2471_u24lambda_u24682.setText(CollectionsKt.joinToString(CollectionsKt.plus((Collection) CollectionsKt.listOf(engaged22), (Iterable) attention2), "   ·   ", "", "", -1, "...", null));
            $this$render_u24lambda_u2471_u24lambda_u24682.setTextSize(15.0f);
            $this$render_u24lambda_u2471_u24lambda_u24682.setTextColor((!pending || ignored) ? CarUi.Companion.getDIM() : CarUi.Companion.getTEXT());
            left2.addView($this$render_u24lambda_u2471_u24lambda_u24682);
            line2.addView(left2, new LinearLayout.LayoutParams(0, -2, 1.0f));
            if (routeNames.isEmpty()) {
            }
            c72.addView(line2);
            c6 = c72;
            str21 = str222;
            drives = drives2;
            i10 = i;
            engaged = str10;
            it14 = it2;
            i11 = i2;
            str13 = str8;
            str16 = str11;
            dash2 = dash;
            str17 = str12;
        }
        JSONObject dash3 = dash2;
        String str23 = str13;
        String str24 = str17;
        Unit unit7 = Unit.INSTANCE;
        GxHomePage $this$render_u24lambda_u2482 = this;
        int i14 = 0;
        List cols6 = columns$default($this$render_u24lambda_u2482, 2, null, 2, null);
        LinearLayout m = $this$render_u24lambda_u2482.card((ViewGroup) cols6.get(0));
        $this$render_u24lambda_u2482.cardHead(m, "Most used models", TuplesKt.to("Models", "gx:models"));
        JSONObject dash4 = dash3;
        JSONArray optJSONArray4 = dash4.optJSONArray("favoriteModels");
        if (optJSONArray4 == null || (emptyList2 = $this$render_u24lambda_u2482.objects(optJSONArray4)) == null) {
            emptyList2 = CollectionsKt.emptyList();
        }
        List models = CollectionsKt.take(emptyList2, 3);
        if (models.isEmpty()) {
            GxPage.text$default($this$render_u24lambda_u2482, "No model usage recorded yet.", m, 17.0f, 0, false, 24, null);
        } else {
            LinearLayout share = new LinearLayout($this$render_u24lambda_u2482.getAct());
            share.setOrientation(0);
            Iterator it16 = models.iterator();
            int i15 = 0;
            while (it16.hasNext()) {
                int i16 = i15;
                JSONObject mo = (JSONObject) it16.next();
                View $this$render_u24lambda_u2482_u24lambda_u2473 = new View($this$render_u24lambda_u2482.getAct());
                $this$render_u24lambda_u2482_u24lambda_u2473.setBackgroundColor($this$render_u24lambda_u2482.favColors.get(i16).intValue());
                int dp = $this$render_u24lambda_u2482.getUi().dp(18);
                Iterator it17 = it16;
                gxHomeNum3 = GxHomeKt.gxHomeNum(mo.opt("weight"));
                share.addView($this$render_u24lambda_u2482_u24lambda_u2473, new LinearLayout.LayoutParams(0, dp, (float) Math.max(1.0d, gxHomeNum3)));
                it16 = it17;
                i15++;
            }
            LinearLayout.LayoutParams $this$render_u24lambda_u2482_u24lambda_u2474 = new LinearLayout.LayoutParams(-1, $this$render_u24lambda_u2482.getUi().dp(18));
            $this$render_u24lambda_u2482_u24lambda_u2474.topMargin = $this$render_u24lambda_u2482.getUi().dp(4);
            $this$render_u24lambda_u2482_u24lambda_u2474.bottomMargin = $this$render_u24lambda_u2482.getUi().dp(8);
            Unit unit8 = Unit.INSTANCE;
            m.addView(share, $this$render_u24lambda_u2482_u24lambda_u2474);
            Iterator it18 = models.iterator();
            int i17 = 0;
            while (it18.hasNext()) {
                int i18 = i17;
                int i19 = i17 + 1;
                JSONObject mo2 = (JSONObject) it18.next();
                LinearLayout $this$render_u24lambda_u2482_u24lambda_u2475 = new LinearLayout($this$render_u24lambda_u2482.getAct());
                $this$render_u24lambda_u2482_u24lambda_u2475.setOrientation(0);
                $this$render_u24lambda_u2482_u24lambda_u2475.setGravity(16);
                int i20 = i14;
                $this$render_u24lambda_u2482_u24lambda_u2475.setPadding(0, $this$render_u24lambda_u2482.getUi().dp(6), 0, $this$render_u24lambda_u2482.getUi().dp(6));
                View $this$render_u24lambda_u2482_u24lambda_u2476 = new View($this$render_u24lambda_u2482.getAct());
                $this$render_u24lambda_u2482_u24lambda_u2476.setBackground(CarUi.rounded$default($this$render_u24lambda_u2482.getUi(), $this$render_u24lambda_u2482.favColors.get(i18).intValue(), 5, null, 4, null));
                Iterator it19 = it18;
                LinearLayout.LayoutParams $this$render_u24lambda_u2482_u24lambda_u2477 = new LinearLayout.LayoutParams($this$render_u24lambda_u2482.getUi().dp(20), $this$render_u24lambda_u2482.getUi().dp(20));
                List models2 = models;
                $this$render_u24lambda_u2482_u24lambda_u2477.rightMargin = $this$render_u24lambda_u2482.getUi().dp(12);
                Unit unit9 = Unit.INSTANCE;
                $this$render_u24lambda_u2482_u24lambda_u2475.addView($this$render_u24lambda_u2482_u24lambda_u2476, $this$render_u24lambda_u2482_u24lambda_u2477);
                LinearLayout tb3 = new LinearLayout($this$render_u24lambda_u2482.getAct());
                tb3.setOrientation(1);
                TextView $this$render_u24lambda_u2482_u24lambda_u2479 = new TextView($this$render_u24lambda_u2482.getAct());
                $this$render_u24lambda_u2482_u24lambda_u2479.setText(GxPage.str$default($this$render_u24lambda_u2482, mo2, "name", null, 2, null));
                $this$render_u24lambda_u2482_u24lambda_u2479.setTextSize(18.0f);
                $this$render_u24lambda_u2482_u24lambda_u2479.setTextColor(CarUi.Companion.getTEXT());
                $this$render_u24lambda_u2482_u24lambda_u2479.setTypeface(Typeface.DEFAULT_BOLD);
                tb3.addView($this$render_u24lambda_u2482_u24lambda_u2479);
                n = GxHomeKt.gxHomeNum(mo2.opt(str20));
                TextView $this$render_u24lambda_u2482_u24lambda_u2480 = new TextView($this$render_u24lambda_u2482.getAct());
                gxHomeInt2 = GxHomeKt.gxHomeInt(Double.valueOf(n));
                JSONObject dash5 = dash4;
                $this$render_u24lambda_u2482_u24lambda_u2480.setText(gxHomeInt2 + str24 + ((n > 1.0d ? 1 : (n == 1.0d ? 0 : -1)) == 0 ? "drive" : str20) + " using this model");
                $this$render_u24lambda_u2482_u24lambda_u2480.setTextSize(15.0f);
                $this$render_u24lambda_u2482_u24lambda_u2480.setTextColor(CarUi.Companion.getDIM());
                tb3.addView($this$render_u24lambda_u2482_u24lambda_u2480);
                $this$render_u24lambda_u2482_u24lambda_u2475.addView(tb3);
                m.addView($this$render_u24lambda_u2482_u24lambda_u2475);
                it18 = it19;
                i14 = i20;
                models = models2;
                i17 = i19;
                dash4 = dash5;
            }
            Unit unit10 = Unit.INSTANCE;
        }
        LinearLayout s3 = $this$render_u24lambda_u2482.card((ViewGroup) cols6.get(1));
        $this$render_u24lambda_u2482.cardHead(s3, "Storage", TuplesKt.to("Recordings", "gx:recordings"));
        JSONObject counts = storage3.optJSONObject("segmentCounts");
        if (counts == null) {
            counts = new JSONObject();
        }
        GxHomePage gxHomePage = $this$render_u24lambda_u2482;
        String str$default9 = GxPage.str$default($this$render_u24lambda_u2482, storage3, "legacyText", null, 2, null);
        if (StringsKt.isBlank(str$default9)) {
            gxHomeBytes2 = GxHomeKt.gxHomeBytes(storage3.opt("usedBytes"));
            gxHomeBytes3 = GxHomeKt.gxHomeBytes(storage3.opt("totalBytes"));
            str$default9 = gxHomeBytes2 + " used of " + gxHomeBytes3;
        }
        GxPage.text$default(gxHomePage, str$default9, s3, 17.0f, CarUi.Companion.getTEXT(), false, 16, null);
        $this$render_u24lambda_u2482.setProgress($this$render_u24lambda_u2482.progress(s3), GxHomeKt.gxHomeClamp$default(storage3.opt("usedPercent"), 0.0d, 0.0d, 6, null));
        gxHomeInt3 = GxHomeKt.gxHomeInt(counts.opt("standard"));
        $this$render_u24lambda_u2482.kv(s3, "Dashcam footage", gxHomeInt3 + " segments");
        gxHomeInt4 = GxHomeKt.gxHomeInt(counts.opt("highResolution"));
        $this$render_u24lambda_u2482.kv(s3, "High-resolution footage", gxHomeInt4 + " segments");
        gxHomeInt5 = GxHomeKt.gxHomeInt(counts.opt("alternate"));
        $this$render_u24lambda_u2482.kv(s3, "Konik footage", gxHomeInt5 + " segments");
        gxHomeBytes = GxHomeKt.gxHomeBytes(storage3.opt("freeBytes"));
        $this$render_u24lambda_u2482.kv(s3, "Free space", gxHomeBytes);
        Unit unit11 = Unit.INSTANCE;
        LinearLayout linearLayout10 = this.body;
        if (linearLayout10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(str23);
            linearLayout10 = null;
        }
        header("Your device", linearLayout10);
        GxHomePage $this$render_u24lambda_u2487 = this;
        List cols7 = columns$default($this$render_u24lambda_u2487, 2, null, 2, null);
        LinearLayout v = $this$render_u24lambda_u2487.card((ViewGroup) cols7.get(0));
        $this$render_u24lambda_u2487.cardHead(v, "Vitals", TuplesKt.to("System", "gx:system"));
        String str$default10 = GxPage.str$default($this$render_u24lambda_u2487, device, "status", null, 2, null);
        if (StringsKt.isBlank(str$default10)) {
            str$default10 = "Parked";
        }
        $this$render_u24lambda_u2487.kv(v, "Status", str$default10);
        String str$default11 = GxPage.str$default($this$render_u24lambda_u2487, device, "lanIp", null, 2, null);
        if (StringsKt.isBlank(str$default11)) {
            str$default11 = "unknown";
        }
        $this$render_u24lambda_u2487.kv(v, "LAN IP", str$default11);
        String str$default12 = GxPage.str$default($this$render_u24lambda_u2487, device, "networkName", null, 2, null);
        if (StringsKt.isBlank(str$default12)) {
            str$default12 = "No wireless connectivity";
        }
        $this$render_u24lambda_u2487.kv(v, "Network", str$default12);
        JSONObject device3 = device;
        $this$render_u24lambda_u2487.kv(v, "Uptime", (!device3.has("uptimeSeconds") || device3.isNull("uptimeSeconds")) ? "unknown" : GxHomeKt.gxHomeDuration(device3.opt("uptimeSeconds")));
        if (!device3.has("cpuTempC") || device3.isNull("cpuTempC")) {
            str6 = "unknown";
        } else {
            gxHomeInt7 = GxHomeKt.gxHomeInt(device3.opt("cpuTempC"));
            str6 = gxHomeInt7 + " C";
        }
        $this$render_u24lambda_u2487.kv(v, "CPU temp", str6);
        if (!device3.has("gpuTempC") || device3.isNull("gpuTempC")) {
            str7 = "unknown";
        } else {
            gxHomeInt6 = GxHomeKt.gxHomeInt(device3.opt("gpuTempC"));
            str7 = gxHomeInt6 + " C";
        }
        $this$render_u24lambda_u2487.kv(v, "GPU temp", str7);
        LinearLayout sw = $this$render_u24lambda_u2487.card((ViewGroup) cols7.get(1));
        $this$render_u24lambda_u2487.cardHead(sw, "Software", TuplesKt.to("Updates", "native:software"));
        for (Pair pair4 : CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to("Branch", "branchName"), TuplesKt.to("Build", "buildEnvironment"), TuplesKt.to("Commit", "commitHash"), TuplesKt.to("Version date", "versionDate"), TuplesKt.to("Fork maintainer", "forkMaintainer"), TuplesKt.to("Update available", "updateAvailable")})) {
            String label3 = (String) pair4.component1();
            String k2 = (String) pair4.component2();
            JSONObject software3 = software;
            String str$default13 = GxPage.str$default($this$render_u24lambda_u2487, software3, k2, null, 2, null);
            if (StringsKt.isBlank(str$default13)) {
                str$default13 = "unknown";
            }
            $this$render_u24lambda_u2487.kv(sw, label3, str$default13);
            software = software3;
        }
        Unit unit12 = Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit render$lambda$27(GxHomePage this$0) {
        this$0.refresh();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit render$lambda$71$lambda$69(GxHomePage $this_run, String $key, String $action, List $routeNames) {
        if (!Intrinsics.areEqual($this_run.togglingKey, $key)) {
            $this_run.toggleDriveStats($action, $routeNames);
        }
        return Unit.INSTANCE;
    }

    private static final void toggleDriveStats$go(final GxHomePage this$0, List<String> list, final String $action) {
        this$0.togglingKey = CollectionsKt.joinToString(list, ",", "", "", -1, "...", null);
        this$0.render();
        GxPage.send$default(this$0, "POST", "/api/stats/" + $action + "_drive", new JSONObject().put("routeNames", new JSONArray((Collection) list)), 0L, new Function2() { // from class: com.starpilot.starview.GxHomePage$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit unit;
                unit = GxHomePage.toggleDriveStats$go$lambda$89(this$0, $action, (String) obj, (JSONObject) obj2);
                return unit;
            }
        }, 8, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit toggleDriveStats$go$lambda$89(GxHomePage this$0, String $action, String e, JSONObject jSONObject) {
        if (e != null) {
            String str = e;
            if (StringsKt.isBlank(str)) {
                str = "Unable to " + $action + " drive statistics.";
            }
            this$0.toast(str);
            this$0.togglingKey = "";
            this$0.render();
        } else {
            this$0.togglingKey = "";
            this$0.refreshing = false;
            this$0.load();
        }
        return Unit.INSTANCE;
    }

    private final void toggleDriveStats(final String action, final List<String> routeNames) {
        if (!Intrinsics.areEqual(action, "ignore")) {
            toggleDriveStats$go(this, routeNames, action);
        } else {
            confirm("Ignore this drive's statistics?", "It will no longer affect local weekly totals, records, model usage, engagement, or attention streaks.", "Ignore", new Function0() { // from class: com.starpilot.starview.GxHomePage$$ExternalSyntheticLambda7
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit unit;
                    unit = GxHomePage.toggleDriveStats$lambda$90(GxHomePage.this, routeNames, action);
                    return unit;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit toggleDriveStats$lambda$90(GxHomePage this$0, List $routeNames, String $action) {
        toggleDriveStats$go(this$0, $routeNames, $action);
        return Unit.INSTANCE;
    }
}
