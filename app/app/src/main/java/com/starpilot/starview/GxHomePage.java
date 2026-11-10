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
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup.LayoutParams;

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

    /* VF: from Vineflower (jadx: ) */
    public GxHomePage(Activity var1, GxHost var2) {
        super(var1, var2);
        Intrinsics.checkNotNullParameter(var1, "act");
        Intrinsics.checkNotNullParameter(var2, "gx");
        this.status = "loading";
        this.error = "";
        this.unit = "miles";
        this.togglingKey = "";
        Iterable var5 = CollectionsKt.listOf(new String[]{"#5ec8c8", "#8b6cc5", "#d4a060", "#e05577", "#6cc56e", "#8aa3ff"});
        var var3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var5, 10));
        Iterator var6 = var5.iterator();

        while (var6.hasNext()) {
            var3.add(Color.parseColor((String)var6.next()));
        }

        List var4 = var3;
        this.favColors = var4;
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

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    public static final Unit load$lambda$11(GxHomePage var0, String var1, JSONObject var2) {
        var0.refreshing = false;
        if (var1 == null && var2 != null) {
            label53: {
                JSONObject var6 = var2.optJSONObject("dashboard");
                Object var4 = null;
                Object var3 = null;
                if (var6 != null) {
                    JSONObject var7 = var6.optJSONObject("week");
                    if (var7 != null) {
                        var1 = GxPage.str$default(var0, var7, "distanceUnit", null, 2, null);
                        if (var1 != null) {
                            CharSequence var5 = var1;
                            CharSequence var9 = var5;
                            if (StringsKt.isBlank(var5)) {
                                var9 = null;
                            }

                            var1 = (String)var9;
                            if (var1 != null) {
                                break label53;
                            }
                        }
                    }
                }

                JSONObject var16 = var2.optJSONObject("driveStats");
                var1 = (String)var4;
                if (var16 != null) {
                    var16 = var16.optJSONObject("all");
                    var1 = (String)var4;
                    if (var16 != null) {
                        String var18 = GxPage.str$default(var0, var16, "unit", null, 2, null);
                        var1 = (String)var4;
                        if (var18 != null) {
                            var1 = var18;
                            if (StringsKt.isBlank(var1)) {
                                var1 = (String)var3;
                            }

                            var1 = var1;
                        }
                    }
                }
            }

            var0.payload = var2;
            if (var1 == null) {
                CharSequence var15 = var0.unit;
                Object var12 = var15;
                if (StringsKt.isBlank(var15)) {
                    var12 = "miles";
                }

                var1 = (String)var12;
            }

            var0.unit = var1;
            var0.status = "ready";
            var2 = var2.optJSONObject("dashboard");
            JSONObject var13 = var2;
            if (var2 == null) {
                var13 = new JSONObject();
            }

            var0.keepRefreshing = var0.hasPendingWork(var13);
        } else if (var0.payload != null) {
            var0.toast("Couldn't refresh dashboard.");
        } else {
            var0.status = "error";
            if (var1 == null) {
                var1 = "empty stats payload";
            }

            var0.error = var1;
        }

        var0.render();
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

    /* VF: from Vineflower (jadx: ) */
    static void cardHead$default(GxHomePage var0, LinearLayout var1, String var2, Pair var3, int var4, Object var5) {
        if ((var4 & 4) != 0) {
            var3 = null;
        }

        var0.cardHead(var1, var2, var3);
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

    /* VF: from Vineflower (jadx: Code restructure failed;Removed duplicated region;incorrect) */
    private final void render() {
        List var119 = null; List var133 = null; List var182 = null; GxHomePage var239 = null; Object var247 = null; Object var250 = null; JSONObject var256 = null; String var274 = null; String var304 = null; JSONObject var99 = null; // declarations Vineflower left out
        this.paintHero();
        Object var17 = this.body;
        String var30 = "body";
        LinearLayout var16 = ((LinearLayout) var17);
        if (var17 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("body");
            var16 = null;
        }

        var16.removeAllViews();
        if (Intrinsics.areEqual(this.status, "loading") && this.payload == null) {
            var16 = this.body;
            if (var16 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("body");
                var16 = null;
            }

            var16.addView((View)this.empty("Loading dashboard..."));
        } else if (Intrinsics.areEqual(this.status, "error") && this.payload == null) {
            var16 = this.body;
            if (var16 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("body");
                var16 = null;
            }

            LinearLayout var188 = this.card((ViewGroup)var16);
            this.text("Failed to load dashboard", (ViewGroup)var188, 21.0F, CarUi.Companion.getWARN(), true);
            GxPage.text$default(this, this.error, (ViewGroup)var188, 0.0F, 0, false, 28, null);
            GxPage var131 = this;
            Pair var97 = TuplesKt.to("Refresh", new GxHomePage$$ExternalSyntheticLambda5(this));
            ViewGroup var189 = (ViewGroup)var188;
            GxPage.buttons$default(var131, new Pair[]{var97}, var189, false, 4, null);
        } else {
            Object var18;
            JSONObject var31;
            label560: {
                var31 = this.dash();
                JSONObject var61 = this.payload;
                if (var61 != null) {
                    JSONObject var62 = var61.optJSONObject("driveStats");
                    var18 = var62;
                    if (var62 != null) {
                        break label560;
                    }
                }

                var18 = new JSONObject();
            }

            label555: {
                JSONObject var63 = this.payload;
                if (var63 != null) {
                    JSONObject var64 = var63.optJSONObject("softwareInfo");
                    var99 = var64;
                    if (var64 != null) {
                        break label555;
                    }
                }

                var99 = new JSONObject();
            }

            JSONObject var65 = var31.optJSONObject("device");
            JSONObject var19 = var65;
            if (var65 == null) {
                var19 = new JSONObject();
            }

            JSONObject var66 = var31.optJSONObject("week");
            JSONObject var22 = var66;
            if (var66 == null) {
                var22 = new JSONObject();
            }

            JSONObject var20 = var31.optJSONObject("storage");
            JSONObject var67 = var20;
            if (var20 == null) {
                var67 = new JSONObject();
            }

            GxHomePage var24 = this;
            JSONObject var21 = var31.optJSONObject("analysis");
            var20 = var21;
            if (var21 == null) {
                var20 = new JSONObject();
            }

            int var9;
            label544: {
                long var14 = Math.round(GxHomeKt.gxHomeNum(var20.opt("pendingRoutes")));
                var9 = (int)Math.max(0L, var14);
                JSONArray var132 = var31.optJSONArray("recentDrives");
                if (var132 != null) {
                    List var23 = var24.objects(var132);
                    var133 = var23;
                    if (var23 != null) {
                        break label544;
                    }
                }

                var133 = CollectionsKt.emptyList();
            }

            var var134 = var133;
            int var8;
            if (var134 instanceof Collection && ((Collection)var134).isEmpty()) {
                var8 = 0;
            } else {
                var8 = 0;
                Iterator var135 = var134.iterator();

                while (var135.hasNext()) {
                    if (!var24.driveReady((JSONObject)var135.next())) {
                        if (++var8 < 0) {
                            CollectionsKt.throwCountOverflow();
                        }
                    }
                }
            }

            var9 = Math.max(var9, var8);
            boolean var11 = GxHomeKt.gxHomeTruthy(var20.opt("running"));
            String var25 = "drives";
            if (var11 || var9 > 0) {
                if (var9 != 0) {
                    var8 = var9;
                } else {
                    var8 = (int)Math.max(1L, Math.round(GxHomeKt.gxHomeNum(var20.opt("batchSize"))));
                }

                StringBuilder var137;
                if (var11) {
                    String var136;
                    if (var8 == 1) {
                        var136 = "drive";
                    } else {
                        var136 = "drives";
                    }

                    var137 = new StringBuilder().append("Analyzing ").append(var8).append(" ").append(var136);
                } else {
                    String var138;
                    if (var9 == 1) {
                        var138 = "drive";
                    } else {
                        var138 = "drives";
                    }

                    var137 = new StringBuilder().append(var9).append(" ").append(var138).append(" queued");
                }

                String var206 = var137.toString();
                LinearLayout var192 = var24.body;
                LinearLayout var139 = var192;
                if (var192 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("body");
                    var139 = null;
                }

                var24.note(var206, (ViewGroup)var139).setTextColor(CarUi.Companion.getACCENT());
            }

            Unit var140 = Unit.INSTANCE;
            GxHomePage var28 = this;
            var18 = var31.optJSONObject("lastDrive");
            Object var207 = var18;
            if (var18 == null) {
                var207 = new JSONObject();
            }

            var11 = var28.driveReady(((JSONObject) var207));
            CharSequence var193 = GxPage.str$default(var28, ((JSONObject) var207), "distanceUnit", null, 2, null);
            var18 = var193;
            if (StringsKt.isBlank(var193)) {
                var18 = var28.unit;
            }

            Object var213 = (String)var18;
            CharSequence var194 = GxPage.str$default(var28, ((JSONObject) var207), "speedUnit", null, 2, null);
            var18 = var194;
            if (StringsKt.isBlank(var194)) {
                if (Intrinsics.areEqual(var213, "kilometers")) {
                    var18 = "kph";
                } else {
                    var18 = "mph";
                }
            }

            Object var33 = (String)var18;
            LinearLayout var195 = var28.body;
            LinearLayout var144 = var195;
            if (var195 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("body");
                var144 = null;
            }

            LinearLayout var29 = var28.card((ViewGroup)var144);
            cardHead$default(var28, var29, "Last drive", null, 4, null);
            GxPage.text$default(
                var28,
                GxHomeKt.gxHomeRange(GxPage.str$default(var28, ((JSONObject) var207), "date", null, 2, null), GxPage.str$default(var28, ((JSONObject) var207), "endDate", null, 2, null)),
                (ViewGroup)var29,
                18.0F,
                CarUi.Companion.getTEXT(),
                false,
                16,
                null
            );
            Object var27 = "...";
            String var32 = "distance";
            String var145;
            if (var11) {
                var145 = GxHomeKt.gxHomeDec(((JSONObject) var207).opt("distance"));
            } else {
                var145 = "...";
            }

            if (!var11) {
                var213 = "analyzing";
            }

            var20 = var99;
            Pair var34 = TuplesKt.to(var145, var213);
            String var26 = "duration";
            Pair var214 = TuplesKt.to(GxHomeKt.gxHomeDuration(((JSONObject) var207).opt("duration")), "duration");
            String var100;
            if (var11) {
                var100 = GxHomeKt.gxHomeInt(((JSONObject) var207).opt("avgSpeed"));
            } else {
                var100 = "...";
            }

            String var146;
            if (var11) {
                var146 = var33 + " avg";
            } else {
                var146 = "speed";
            }

            Pair var147 = TuplesKt.to(var100, var146);
            var33 = "engagedPercent";
            String var101 = ((String) var27);
            if (var11) {
                var101 = GxHomeKt.gxHomePct(((JSONObject) var207).opt("engagedPercent"));
            }

            Pair var102 = TuplesKt.to(var101, "engaged");
            var28.metrics(var29, CollectionsKt.listOf(new Pair[]{var34, var214, var147, var102}));
            CharSequence var148 = GxPage.str$default(var28, ((JSONObject) var207), "model", null, 2, null);
            var17 = var148;
            if (StringsKt.isBlank(var148)) {
                var17 = "Unknown model";
            }

            List var149 = CollectionsKt.mutableListOf(new String[]{var17});
            String var106;
            if (var11) {
                String var104 = GxHomeKt.gxHomeInt(((JSONObject) var207).opt("distractedMoments"));
                var149.add(var104 + " distracted");
                String var105 = GxHomeKt.gxHomeInt(((JSONObject) var207).opt("unresponsiveMoments"));
                var106 = var105 + " unresponsive";
            } else {
                var106 = "Analyzing stats";
            }

            var149.add(var106);
            GxPage.text$default(
                var28, CollectionsKt.joinToString(var149, "   ·   ", "", "", -1, "...", null), (ViewGroup)var29, 0.0F, 0, false, 28, null
            );
            LinearLayout var150 = this.body;
            var17 = var150;
            if (var150 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("body");
                var17 = null;
            }

            this.header("Your driving", (ViewGroup)var17);
            GxHomePage var240 = this;
            var11 = false;
            List var151 = columns$default(var240, 3, null, 2, null);
            Iterator var248 = CollectionsKt.listOf(
                    new Pair[]{TuplesKt.to("All time", "all"), TuplesKt.to("Past week", "week"), TuplesKt.to("StarPilot", "starpilot")}
                )
                .iterator();
            var8 = 0;

            for (JSONObject var108 = ((JSONObject) var18); var248.hasNext(); var8++) {
                Pair var215 = (Pair)var248.next();
                var207 = var108.optJSONObject((String)var215.getSecond());
                JSONObject var68 = ((JSONObject) var207);
                if (var207 == null) {
                    var68 = new JSONObject();
                }

                var29 = var240.card((ViewGroup)var151.get(var8));
                cardHead$default(var240, var29, (String)var215.getFirst(), null, 4, null);
                var34 = TuplesKt.to(GxHomeKt.gxHomeInt(var68.opt("drives")), "drives");
                String var35 = GxHomeKt.gxHomeDec(var68.opt("distance"));
                CharSequence var216 = GxPage.str$default(var240, var68, "unit", null, 2, null);
                var207 = var216;
                if (StringsKt.isBlank(var216)) {
                    var207 = var240.unit;
                }

                var240.metrics(
                    var29, CollectionsKt.listOf(new Pair[]{var34, TuplesKt.to(var35, var207), TuplesKt.to(GxHomeKt.gxHomeDec(var68.opt("hours")), "hours")})
                );
            }

            Unit var69 = Unit.INSTANCE;
            GxHomePage var294 = this;
            var11 = false;
            List var241 = columns$default(var294, 2, null, 2, null);
            LinearLayout var210 = var294.card((ViewGroup)var241.get(0));
            cardHead$default(var294, var210, "This week", null, 4, null);
            GxPage var70 = var294;
            String var249 = " ";
            CharSequence var109 = GxPage.str$default(var70, var22, "distanceUnit", null, 2, null);
            Object var71 = var109;
            if (StringsKt.isBlank(var109)) {
                var71 = var294.unit;
            }

            label517: {
                var71 = (String)var71;
                String var110 = GxHomeKt.gxHomePct(var22.opt("engagedPercent"));
                String var111 = var110 + " engaged";
                var294.text(var111, (ViewGroup)var210, 24.0F, CarUi.Companion.getACCENT(), true);
                var294.setProgress(var294.progress((ViewGroup)var210), GxHomeKt.gxHomeClamp$default(var22.opt("engagedPercent"), 0.0, 0.0, 6, null));
                var294.metrics(
                    var210,
                    CollectionsKt.listOf(
                        new Pair[]{
                            TuplesKt.to(GxHomeKt.gxHomeDec(var22.opt("distance")), var71),
                            TuplesKt.to(GxHomeKt.gxHomeDec(var22.opt("hours")), "hours"),
                            TuplesKt.to(GxHomeKt.gxHomeInt(var22.opt("drives")), "drives")
                        }
                    )
                );
                var71 = var22.optJSONArray("dailyDistance");
                if (var71 != null) {
                    var71 = var294.objects((JSONArray)var71);
                    if (var71 != null && !((Collection)var71).isEmpty()) {
                        break label517;
                    }
                }

                var71 = null;
            }

            List var154;
            if (var71 != null) {
                Iterable var152 = (Iterable)var71;
                var var112 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var152, 10));

                for (JSONObject var201 : (Iterable<JSONObject>) var152) {
                    var112.add(TuplesKt.to(GxPage.str$default(var294, var201, "label", null, 2, null), GxHomeKt.gxHomeNum(var201.opt("distance"))));
                }

                var154 = var112;
            } else {
                var71 = CollectionsKt.listOf(new String[]{"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"});
                var11 = false;
                var var113 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var71, 10));
                Iterator var155 = ((Iterable) var71).iterator();

                while (var155.hasNext()) {
                    var113.add(TuplesKt.to((String)var155.next(), 0.0));
                }

                var154 = var113;
            }

            Iterator var76 = var154.iterator();
            Double var77;
            if (!var76.hasNext()) {
                var77 = null;
            } else {
                double var1 = ((Number)((Pair)var76.next()).getSecond()).doubleValue();

                while (var76.hasNext()) {
                    var1 = Math.max(var1, ((Number)((Pair)var76.next()).getSecond()).doubleValue());
                }

                var77 = var1;
            }

            double var40;
            if (var77 != null) {
                var40 = var77;
            } else {
                var40 = 1.0;
            }

            var40 = Math.max(1.0, var40);
            LinearLayout var202 = new LinearLayout((Context)var294.getAct());
            var202.setOrientation(0);
            var202.setGravity(80);
            var202.setPadding(0, var294.getUi().dp(8), 0, 0);
            var8 = var294.getUi().dp(110);
            Iterator var217 = var154.iterator();
            String var78 = var25;

            while (var217.hasNext()) {
                Pair var236 = (Pair)var217.next();
                var25 = (String)var236.component1();
                double var3 = ((Number)var236.component2()).doubleValue();
                LinearLayout var237 = new LinearLayout((Context)var294.getAct());
                var237.setOrientation(1);
                var237.setGravity(81);
                TextView var252 = new TextView((Context)var294.getAct());
                var252.setText(GxHomeKt.gxHomeDec(var3));
                var252.setTextSize(12.0F);
                var252.setTextColor(CarUi.Companion.getDIM());
                var252.setGravity(17);
                var237.addView((View)var252);
                double var5 = Math.max(4.0, var3 / var40 * 100) / 100.0;
                var3 = var8;
                View var296 = new View((Context)var294.getAct());
                var296.setBackground((Drawable)CarUi.rounded$default(var294.getUi(), CarUi.Companion.getACCENT(), 4, null, 4, null));
                android.widget.LinearLayout.LayoutParams var253 = new android.widget.LinearLayout.LayoutParams(var294.getUi().dp(26), (int)(var5 * var3));
                var253.topMargin = var294.getUi().dp(2);
                Unit var36 = Unit.INSTANCE;
                var237.addView(var296, (android.view.ViewGroup.LayoutParams)var253);
                TextView var254 = new TextView((Context)var294.getAct());
                var254.setText(var25);
                var254.setTextSize(14.0F);
                var254.setTextColor(CarUi.Companion.getTEXT());
                var254.setGravity(17);
                var254.setPadding(0, var294.getUi().dp(4), 0, 0);
                var237.addView((View)var254);
                var202.addView((View)var237, (android.view.ViewGroup.LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, var294.getUi().dp(48) + var8, 1.0F)));
            }

            var26 = var26;
            var210.addView((View)var202);
            LinearLayout var297 = var294.card((ViewGroup)var241.get(1));
            cardHead$default(var294, var297, "Personal records", null, 4, null);
            var18 = var31.optJSONObject("records");
            JSONObject var115 = ((JSONObject) var18);
            if (var18 == null) {
                var115 = new JSONObject();
            }

            Pair var157 = TuplesKt.to("Longest drive", "longestDrive");
            Pair var255 = TuplesKt.to("Most-engaged day", "mostEngagedDay");
            Pair var218 = TuplesKt.to("Best week", "bestWeek");
            Pair var303 = TuplesKt.to("Highest streak", "highestStreak");
            Pair var242 = TuplesKt.to("Longest undistracted drive", "longestUndistractedDrive");
            Iterator var158 = CollectionsKt.listOf(new Pair[]{var157, var255, var218, var303, var242, TuplesKt.to("Clean-drive streak", "cleanDriveStreak")})
                .iterator();
            JSONObject var219 = var115;

            while (var158.hasNext()) {
                label480: {
                    Pair var226 = (Pair)var158.next();
                    var304 = (String)var226.component1();
                    var256 = var219.optJSONObject((String)var226.component2());
                    if (var256 != null) {
                        var25 = (String)var256.opt("value");
                        if (var25 != null) {
                            if (Intrinsics.areEqual(var25, JSONObject.NULL)) {
                                var25 = null;
                            }

                            if (var25 != null) {
                                var27 = var25.toString();
                                var25 = ((String) var27);
                                if (var27 != null) {
                                    break label480;
                                }
                            }
                        }
                    }

                    var25 = "0";
                }

                label472: {
                    if (var256 != null) {
                        var29 = (LinearLayout)var256.opt("detail");
                        if (var29 != null) {
                            var27 = var29;
                            if (Intrinsics.areEqual(var29, JSONObject.NULL)) {
                                var27 = null;
                            }

                            if (var27 != null) {
                                String var258 = var27.toString();
                                var27 = var258;
                                if (var258 != null) {
                                    break label472;
                                }
                            }
                        }
                    }

                    var27 = "";
                }

                var29 = new LinearLayout((Context)var294.getAct());
                var29.setOrientation(0);
                var29.setGravity(16);
                var29.setPadding(0, var294.getUi().dp(7), 0, var294.getUi().dp(7));
                LinearLayout var37 = new LinearLayout((Context)var294.getAct());
                var37.setOrientation(1);
                TextView var38 = new TextView((Context)var294.getAct());
                var38.setText(var304);
                var38.setTextSize(15.0F);
                var38.setTextColor(CarUi.Companion.getDIM());
                var37.addView((View)var38);
                TextView var305 = new TextView((Context)var294.getAct());
                var305.setText(var25);
                var305.setTextSize(19.0F);
                var305.setTextColor(CarUi.Companion.getTEXT());
                var305.setTypeface(Typeface.DEFAULT_BOLD);
                var37.addView((View)var305);
                var29.addView((View)var37, (android.view.ViewGroup.LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
                TextView var229 = new TextView((Context)var294.getAct());
                var229.setText(var27);
                var229.setTextSize(14.0F);
                var229.setTextColor(CarUi.Companion.getDIM());
                var229.setGravity(8388613);
                var229.setPadding(var294.getUi().dp(10), 0, 0, 0);
                var29.addView((View)var229);
                var297.addView((View)var29);
            }

            Unit var116 = Unit.INSTANCE;
            GxHomePage var295 = this;
            var11 = false;
            LinearLayout var159 = var295.body;
            var17 = var159;
            if (var159 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("body");
                var17 = null;
            }

            label461: {
                var29 = var295.card((ViewGroup)var17);
                cardHead$default(var295, var29, "Recent drives", null, 4, null);
                JSONArray var118 = var31.optJSONArray("recentDrives");
                if (var118 != null) {
                    List var160 = var295.objects(var118);
                    var119 = var160;
                    if (var160 != null) {
                        break label461;
                    }
                }

                var119 = CollectionsKt.emptyList();
            }

            if (var119.isEmpty()) {
                GxPage.text$default(var295, "No local drives found yet.", (ViewGroup)var29, 17.0F, 0, false, 24, null);
            }

            Iterator var230 = var119.iterator();
            var8 = 0;
            String var211 = var249;
            String var220 = ((String) var33);
            var27 = var32;

            while (var230.hasNext()) {
                int var10 = var8 + 1;
                JSONObject var291 = (JSONObject)var230.next();
                boolean var13 = Intrinsics.areEqual(var291.opt("ignored"), true);
                boolean var12 = var295.driveReady(var291);
                boolean var53;
                if (!var12 && !var13) {
                    var53 = true;
                } else {
                    var53 = false;
                }

                var var161 = GxPage.str$default(var295, var291, "distanceUnit", null, 2, null);
                if (StringsKt.isBlank(var161)) {
                    var161 = var295.unit;
                }

                String var273 = var161;
                JSONArray var264 = var291.optJSONArray("routeNames");
                List var268;
                if (var264 == null) {
                    var268 = CollectionsKt.emptyList();
                } else {
                    Iterable var162 = RangesKt.until(0, var264.length());
                    var var280 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var162, 10));
                    Iterator var298 = var162.iterator();

                    while (var298.hasNext()) {
                        var280.add(var264.opt(((IntIterator)var298).nextInt()));
                    }

                    List var163 = var280;
                    Iterable var164 = var163;
                    var var281 = new ArrayList();

                    for (Object var299 : var164) {
                        if (GxHomeKt.gxHomeTruthy(var299)) {
                            var281.add(var299);
                        }
                    }

                    List var165 = var281;
                    Iterable var266 = var165;
                    var var166 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var266, 10));
                    Iterator var267 = var266.iterator();

                    while (var267.hasNext()) {
                        var166.add(var267.next().toString());
                    }

                    var268 = var166;
                }

                label453: {
                    String var167;
                    if (var13) {
                        var167 = "Stats excluded";
                    } else {
                        if (!var53) {
                            String var168 = GxHomeKt.gxHomeDec(var291.opt(((String) var27)));
                            var274 = var168 + var211 + var273;
                            break label453;
                        }

                        var167 = "Analyzing stats";
                    }

                    var274 = var167;
                }

                Pair var169;
                if (var13) {
                    var169 = TuplesKt.to("Excluded", CollectionsKt.listOf("Ignored from stats"));
                } else if (var53) {
                    var169 = TuplesKt.to("Pending", CollectionsKt.listOf("Waiting for full route analysis"));
                } else {
                    String var170 = GxHomeKt.gxHomePct(var291.opt(var220));
                    String var171 = var170 + " engaged";
                    var32 = GxHomeKt.gxHomeInt(var291.opt("distractedMoments"));
                    var32 = var32 + " distracted";
                    String var300 = GxHomeKt.gxHomeInt(var291.opt("unresponsiveMoments"));
                    var169 = TuplesKt.to(var171, CollectionsKt.listOf(new String[]{var32, var300 + " unresponsive"}));
                }

                String var306 = (String)var169.component1();
                List var307 = (List)var169.component2();
                if (var12 && !var13) {
                    var40 = GxHomeKt.gxHomeClamp$default(var291.opt(var220), 0.0, 0.0, 6, null);
                } else {
                    var40 = 0.0;
                }

                if (var8 > 0) {
                    View var284 = new View((Context)var295.getAct());
                    var284.setBackgroundColor(Color.rgb(40, 44, 52));
                    android.widget.LinearLayout.LayoutParams var301 = new android.widget.LinearLayout.LayoutParams(-1, var295.getUi().dp(1));
                    var301.topMargin = var295.getUi().dp(6);
                    var301.bottomMargin = var295.getUi().dp(6);
                    Unit var172 = Unit.INSTANCE;
                    var29.addView(var284, (android.view.ViewGroup.LayoutParams)var301);
                }

                var297 = new LinearLayout((Context)var295.getAct());
                var297.setOrientation(0);
                var297.setGravity(16);
                var297.setPadding(0, var295.getUi().dp(6), 0, var295.getUi().dp(6));
                LinearLayout var308 = new LinearLayout((Context)var295.getAct());
                var308.setOrientation(1);
                float var7;
                if (!var13 && !var53) {
                    var7 = 1.0F;
                } else {
                    var7 = 0.7F;
                }

                var308.setAlpha(var7);
                TextView var173 = new TextView((Context)var295.getAct());
                var173.setText(
                    GxHomeKt.gxHomeRange(GxPage.str$default(var295, var291, "date", null, 2, null), GxPage.str$default(var295, var291, "endDate", null, 2, null))
                );
                var173.setTextSize(18.0F);
                var173.setTextColor(CarUi.Companion.getTEXT());
                var173.setTypeface(Typeface.DEFAULT_BOLD);
                var308.addView((View)var173);
                TextView var39 = new TextView((Context)var295.getAct());
                CharSequence var285 = GxPage.str$default(var295, var291, "model", null, 2, null);
                var18 = var285;
                if (StringsKt.isBlank(var285)) {
                    var18 = "Unknown model";
                }

                var39.setText((CharSequence)var18);
                var39.setTextSize(15.0F);
                var39.setTextColor(CarUi.Companion.getDIM());
                var308.addView((View)var39);
                TextView var175 = new TextView((Context)var295.getAct());
                var32 = GxHomeKt.gxHomeDuration(var291.opt(var26));
                var175.setText(var274 + "   ·   " + var32);
                var175.setTextSize(16.0F);
                var175.setTextColor(CarUi.Companion.getTEXT());
                var175.setPadding(0, var295.getUi().dp(4), 0, 0);
                var308.addView((View)var175);
                var295.setProgress(var295.progress((ViewGroup)var308), var40);
                TextView var176 = new TextView((Context)var295.getAct());
                var176.setText(
                    CollectionsKt.joinToString(CollectionsKt.plus(CollectionsKt.listOf(var306), var307), "   ·   ", "", "", -1, "...", null)
                );
                var176.setTextSize(15.0F);
                if (!var53 && !var13) {
                    var8 = CarUi.Companion.getTEXT();
                } else {
                    var8 = CarUi.Companion.getDIM();
                }

                var176.setTextColor(var8);
                var308.addView((View)var176);
                var297.addView((View)var308, (android.view.ViewGroup.LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
                if (!var268.isEmpty()) {
                    var32 = CollectionsKt.joinToString(var268, ",", "", "", -1, "...", null);
                    String var177;
                    if (var13) {
                        var177 = "include";
                    } else {
                        var177 = "ignore";
                    }

                    CarUi var292 = var295.getUi();
                    String var275;
                    if (var13) {
                        var275 = "Include drive stats";
                    } else {
                        var275 = "Ignore drive stats";
                    }

                    TextView var178 = CarUi.button$default(var292, var275, false, new GxHomePage$$ExternalSyntheticLambda6(var295, var32, var177, var268), 2, null);
                    if (Intrinsics.areEqual(var295.togglingKey, var32)) {
                        var178.setAlpha(0.45F);
                    }

                    View var269 = (View)var178;
                    android.widget.LinearLayout.LayoutParams var179 = new android.widget.LinearLayout.LayoutParams(-2, -2);
                    var179.leftMargin = var295.getUi().dp(12);
                    Unit var276 = Unit.INSTANCE;
                    var297.addView(var269, (android.view.ViewGroup.LayoutParams)var179);
                }

                var29.addView((View)var297);
                var8 = var10;
            }

            label399: {
                Unit var180 = Unit.INSTANCE;
                var239 = this;
                var11 = false;
                var247 = columns$default(((GxHomePage) var239), 2, null, 2, null);
                var250 = var239.card((ViewGroup)var247.get(0));
                var239.cardHead(var250, "Most used models", TuplesKt.to("Models", "gx:models"));
                JSONArray var181 = var31.optJSONArray("favoriteModels");
                if (var181 != null) {
                    List var221 = var239.objects(var181);
                    var182 = var221;
                    if (var221 != null) {
                        break label399;
                    }
                }

                var182 = CollectionsKt.emptyList();
            }

            List var222 = CollectionsKt.take(var182, 3);
            if (var222.isEmpty()) {
                GxPage.text$default(((GxPage) var239), "No model usage recorded yet.", (ViewGroup)var250, 17.0F, 0, false, 24, null);
            } else {
                LinearLayout var231 = new LinearLayout((Context)var239.getAct());
                var231.setOrientation(0);
                Iterator var183 = var222.iterator();

                for (int var50 = 0; var183.hasNext(); var50++) {
                    JSONObject var261 = (JSONObject)var183.next();
                    View var270 = new View((Context)var239.getAct());
                    var270.setBackgroundColor(var239.favColors.get(var50).intValue());
                    var231.addView(
                        var270,
                        (android.view.ViewGroup.LayoutParams)(
                            new android.widget.LinearLayout.LayoutParams(
                                0, var239.getUi().dp(18), (float)Math.max(1.0, GxHomeKt.gxHomeNum(var261.opt("weight")))
                            )
                        )
                    );
                }

                View var262 = (View)var231;
                android.widget.LinearLayout.LayoutParams var184 = new android.widget.LinearLayout.LayoutParams(-1, var239.getUi().dp(18));
                var184.topMargin = var239.getUi().dp(4);
                var184.bottomMargin = var239.getUi().dp(8);
                Unit var232 = Unit.INSTANCE;
                var250.addView(var262, (android.view.ViewGroup.LayoutParams)var184);
                Iterator var233 = var222.iterator();
                var8 = 0;

                for (Iterator var223 = var233; var223.hasNext(); var8++) {
                    JSONObject var234 = (JSONObject)var223.next();
                    var29 = new LinearLayout((Context)var239.getAct());
                    var29.setOrientation(0);
                    var29.setGravity(16);
                    var29.setPadding(0, var239.getUi().dp(6), 0, var239.getUi().dp(6));
                    View var288 = new View((Context)var239.getAct());
                    var288.setBackground((Drawable)CarUi.rounded$default(var239.getUi(), var239.favColors.get(var8).intValue(), 5, null, 4, null));
                    android.widget.LinearLayout.LayoutParams var271 = new android.widget.LinearLayout.LayoutParams(var239.getUi().dp(20), var239.getUi().dp(20));
                    var271.rightMargin = var239.getUi().dp(12);
                    Unit var277 = Unit.INSTANCE;
                    var29.addView(var288, (android.view.ViewGroup.LayoutParams)var271);
                    LinearLayout var272 = new LinearLayout((Context)var239.getAct());
                    var272.setOrientation(1);
                    TextView var278 = new TextView((Context)var239.getAct());
                    var278.setText(GxPage.str$default(((GxPage) var239), var234, "name", null, 2, null));
                    var278.setTextSize(18.0F);
                    var278.setTextColor(CarUi.Companion.getTEXT());
                    var278.setTypeface(Typeface.DEFAULT_BOLD);
                    var272.addView((View)var278);
                    var40 = GxHomeKt.gxHomeNum(var234.opt(var78));
                    TextView var279 = new TextView((Context)var239.getAct());
                    var32 = GxHomeKt.gxHomeInt(var40);
                    boolean var54;
                    if (var40 == 1.0) {
                        var54 = true;
                    } else {
                        var54 = false;
                    }

                    if (var54) {
                        var25 = "drive";
                    } else {
                        var25 = var78;
                    }

                    var279.setText(var32 + var211 + var25 + " using this model");
                    var279.setTextSize(15.0F);
                    var279.setTextColor(CarUi.Companion.getDIM());
                    var272.addView((View)var279);
                    var29.addView((View)var272);
                    var250.addView((View)var29);
                }

                Unit var79 = Unit.INSTANCE;
            }

            LinearLayout var212 = var239.card((ViewGroup)var247.get(1));
            var239.cardHead(var212, "Storage", TuplesKt.to("Recordings", "gx:recordings"));
            JSONObject var121 = var67.optJSONObject("segmentCounts");
            JSONObject var80 = var121;
            if (var121 == null) {
                var80 = new JSONObject();
            }

            GxPage var224 = ((GxPage) var239);
            CharSequence var185 = GxPage.str$default(((GxPage) var239), var67, "legacyText", null, 2, null);
            var17 = var185;
            if (StringsKt.isBlank(var185)) {
                String var186 = GxHomeKt.gxHomeBytes(var67.opt("usedBytes"));
                String var123 = GxHomeKt.gxHomeBytes(var67.opt("totalBytes"));
                var17 = var186 + " used of " + var123;
            }

            GxPage.text$default(var224, (String)var17, (ViewGroup)var212, 17.0F, CarUi.Companion.getTEXT(), false, 16, null);
            var239.setProgress(var239.progress((ViewGroup)var212), GxHomeKt.gxHomeClamp$default(var67.opt("usedPercent"), 0.0, 0.0, 6, null));
            String var124 = GxHomeKt.gxHomeInt(var80.opt("standard"));
            var239.kv(var212, "Dashcam footage", var124 + " segments");
            String var125 = GxHomeKt.gxHomeInt(var80.opt("highResolution"));
            var239.kv(var212, "High-resolution footage", var125 + " segments");
            String var81 = GxHomeKt.gxHomeInt(var80.opt("alternate"));
            var239.kv(var212, "Konik footage", var81 + " segments");
            var239.kv(var212, "Free space", GxHomeKt.gxHomeBytes(var67.opt("freeBytes")));
            Unit var82 = Unit.INSTANCE;
            var17 = this.body;
            LinearLayout var83 = ((LinearLayout) var17);
            if (var17 == null) {
                Intrinsics.throwUninitializedPropertyAccessException(var30);
                var83 = null;
            }

            this.header("Your device", (ViewGroup)var83);
            GxHomePage var187 = this;
            List var204 = columns$default(var187, 2, null, 2, null);
            LinearLayout var199 = var187.card((ViewGroup)var204.get(0));
            var187.cardHead(var199, "Vitals", TuplesKt.to("System", "gx:system"));
            CharSequence var127 = GxPage.str$default(var187, var19, "status", null, 2, null);
            var71 = var127;
            if (StringsKt.isBlank(var127)) {
                var71 = "Parked";
            }

            var187.kv(var199, "Status", (String)var71);
            CharSequence var128 = GxPage.str$default(var187, var19, "lanIp", null, 2, null);
            var71 = var128;
            if (StringsKt.isBlank(var128)) {
                var71 = "unknown";
            }

            var187.kv(var199, "LAN IP", (String)var71);
            CharSequence var129 = GxPage.str$default(var187, var19, "networkName", null, 2, null);
            var71 = var129;
            if (StringsKt.isBlank(var129)) {
                var71 = "No wireless connectivity";
            }

            var187.kv(var199, "Network", (String)var71);
            String var87;
            if (var19.has("uptimeSeconds") && !var19.isNull("uptimeSeconds")) {
                var87 = GxHomeKt.gxHomeDuration(var19.opt("uptimeSeconds"));
            } else {
                var87 = "unknown";
            }

            var187.kv(var199, "Uptime", var87);
            String var88;
            if (var19.has("cpuTempC") && !var19.isNull("cpuTempC")) {
                String var89 = GxHomeKt.gxHomeInt(var19.opt("cpuTempC"));
                var88 = var89 + " C";
            } else {
                var88 = "unknown";
            }

            var187.kv(var199, "CPU temp", var88);
            String var90;
            if (var19.has("gpuTempC") && !var19.isNull("gpuTempC")) {
                String var91 = GxHomeKt.gxHomeInt(var19.opt("gpuTempC"));
                var90 = var91 + " C";
            } else {
                var90 = "unknown";
            }

            var187.kv(var199, "GPU temp", var90);
            LinearLayout var200 = var187.card((ViewGroup)var204.get(1));
            var187.cardHead(var200, "Software", TuplesKt.to("Updates", "native:software"));

            for (Pair var92 : CollectionsKt.listOf(
                new Pair[]{
                    TuplesKt.to("Branch", "branchName"),
                    TuplesKt.to("Build", "buildEnvironment"),
                    TuplesKt.to("Commit", "commitHash"),
                    TuplesKt.to("Version date", "versionDate"),
                    TuplesKt.to("Fork maintainer", "forkMaintainer"),
                    TuplesKt.to("Update available", "updateAvailable")
                }
            )) {
                String var205 = (String)var92.component1();
                String var93 = (String)var92.component2();
                CharSequence var130 = GxPage.str$default(var187, var20, var93, null, 2, null);
                var71 = var130;
                if (StringsKt.isBlank(var130)) {
                    var71 = "unknown";
                }

                var187.kv(var200, var205, (String)var71);
            }

            Unit var95 = Unit.INSTANCE;
        }
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
