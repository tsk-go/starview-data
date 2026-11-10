package com.starpilot.starview;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import com.starpilot.starview.GalaxyApi;
import com.starpilot.starview.GxPage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeSet;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.ranges.RangesKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: GxHome.kt */
public final class GxPersonalitiesPage extends GxPage {
    private final HashSet<String> advancedCustom;
    private final HashMap<String, String> advancedErrors;
    private final HashSet<String> advancedOpen;
    private final HashMap<String, Double> advancedPendingValue;
    private final HashMap<String, Runnable> advancedTimers;
    private boolean busy;
    private final List<Pair<String, String>> categories;
    private LinearLayout content;
    private boolean contextPending;
    private final HashMap<String, String> curveErrors;
    private boolean curvePending;
    private JSONObject data;
    private final HashMap<String, List<Double>> drafts;
    private Drag drag;
    private String error;
    private final List<String> jerkSuffixes;
    private String lastSig;
    private boolean loadPending;
    private Map<String, GalaxyApi.Param> meta;
    private final boolean needsOffroad;
    private String notice;
    private Boolean offroad;
    private Boolean onroad;
    private final HashMap<String, List<TextView>> pointViews;
    private final Map<String, String> profileKeys;
    private final List<String> profilesIds;
    private boolean ready;
    private boolean recovery;
    private final HashMap<String, Object> vals;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GxPersonalitiesPage(Activity act, GxHost gx) {
        super(act, gx);
        Intrinsics.checkNotNullParameter(act, "act");
        Intrinsics.checkNotNullParameter(gx, "gx");
        this.needsOffroad = true;
        this.profilesIds = CollectionsKt.listOf((Object[]) new String[]{"traffic", "aggressive", "standard", "relaxed"});
        this.categories = CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to("acceleration", "Acceleration"), TuplesKt.to("braking", "Braking"), TuplesKt.to("following", "Following")});
        this.profileKeys = MapsKt.mapOf(TuplesKt.to("traffic", "TrafficPersonalityProfile"), TuplesKt.to("aggressive", "AggressivePersonalityProfile"), TuplesKt.to("standard", "StandardPersonalityProfile"), TuplesKt.to("relaxed", "RelaxedPersonalityProfile"));
        this.jerkSuffixes = CollectionsKt.listOf((Object[]) new String[]{"Acceleration", "Deceleration", "Danger", "SpeedDecrease", "Speed"});
        this.vals = new HashMap<>();
        this.meta = MapsKt.emptyMap();
        this.error = "";
        this.notice = "";
        this.advancedOpen = new HashSet<>();
        this.drafts = new HashMap<>();
        this.curveErrors = new HashMap<>();
        this.advancedErrors = new HashMap<>();
        this.advancedCustom = new HashSet<>();
        this.advancedPendingValue = new HashMap<>();
        this.advancedTimers = new HashMap<>();
        this.lastSig = "";
        this.pointViews = new HashMap<>();
    }

    @Override // com.starpilot.starview.GxPage
    public boolean getNeedsOffroad() {
        return this.needsOffroad;
    }

    /* compiled from: GxHome.kt */
    private static final class Drag {
        private final String category;
        private final int index;
        private final double max;
        private final double min;
        private final List<Double> previous;
        private final String profile;

        public Drag(String profile, String category, int index, double min, double max, List<Double> list) {
            Intrinsics.checkNotNullParameter(profile, "profile");
            Intrinsics.checkNotNullParameter(category, "category");
            this.profile = profile;
            this.category = category;
            this.index = index;
            this.min = min;
            this.max = max;
            this.previous = list;
        }

        public final String getCategory() {
            return this.category;
        }

        public final int getIndex() {
            return this.index;
        }

        public final double getMax() {
            return this.max;
        }

        public final double getMin() {
            return this.min;
        }

        public final List<Double> getPrevious() {
            return this.previous;
        }

        public final String getProfile() {
            return this.profile;
        }
    }

    @Override // com.starpilot.starview.GxPage
    public void build() {
        this.content = GxPage.box$default(this, null, 1, null);
        every(4000L, new Function0() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda14
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit build$lambda$0;
                build$lambda$0 = GxPersonalitiesPage.build$lambda$0(GxPersonalitiesPage.this);
                return build$lambda$0;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$0(GxPersonalitiesPage this$0) {
        if (this$0.ready) {
            this$0.refreshContext();
        } else {
            this$0.load();
        }
        return Unit.INSTANCE;
    }

    @Override // com.starpilot.starview.GxPage
    public void onShow() {
        forceRender();
        load();
    }

    @Override // com.starpilot.starview.GxPage
    public void onHide() {
        this.drag = null;
        this.drafts.clear();
        for (Runnable runnable : this.advancedTimers.values()) {
            Intrinsics.checkNotNullExpressionValue(runnable, "next(...)");
            Runnable r = runnable;
            getMain().removeCallbacks(r);
        }
        this.advancedTimers.clear();
        this.advancedPendingValue.clear();
    }

    private final Boolean roadFlag(Object v) {
        if (Intrinsics.areEqual(v, (Object) true) || Intrinsics.areEqual(v, (Object) 1) || Intrinsics.areEqual(v, (Object) 1L) || Intrinsics.areEqual(v, Double.valueOf(1.0d)) || Intrinsics.areEqual(v, "1") || Intrinsics.areEqual(v, "true") || Intrinsics.areEqual(v, "True")) {
            return true;
        }
        if (Intrinsics.areEqual(v, (Object) false) || Intrinsics.areEqual(v, (Object) 0) || Intrinsics.areEqual(v, (Object) 0L) || Intrinsics.areEqual(v, Double.valueOf(0.0d)) || Intrinsics.areEqual(v, "") || Intrinsics.areEqual(v, "0") || Intrinsics.areEqual(v, "false") || Intrinsics.areEqual(v, "False")) {
            return false;
        }
        return null;
    }

    private final boolean isOnroad() {
        return Intrinsics.areEqual((Object) this.onroad, (Object) true);
    }

    private final boolean getRoadStateKnown() {
        return (this.onroad == null || this.offroad == null || Intrinsics.areEqual(this.onroad, this.offroad)) ? false : true;
    }

    private final boolean getMigrationRequired() {
        JSONObject jSONObject = this.data;
        return jSONObject != null && jSONObject.optBoolean("migration_required", false);
    }

    private final boolean getLocked() {
        return (this.ready && !this.busy && getRoadStateKnown()) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean getEditingLocked() {
        return getLocked() || this.curvePending || getMigrationRequired();
    }

    private final boolean enabled(Object v) {
        return Intrinsics.areEqual(v, (Object) true) || Intrinsics.areEqual(v, (Object) 1) || Intrinsics.areEqual(v, (Object) 1L) || Intrinsics.areEqual(v, "1") || Intrinsics.areEqual(v, "True") || Intrinsics.areEqual(v, "true");
    }

    private final String key(String profile) {
        String str = this.profileKeys.get(profile);
        return str == null ? "" : str;
    }

    private final boolean metric() {
        return enabled(this.vals.get("IsMetric"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String speedUnit() {
        return metric() ? "km/h" : "mph";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String speed(double mph) {
        if (!((Double.isInfinite(mph) || Double.isNaN(mph)) ? false : true)) {
            return "—";
        }
        if (metric()) {
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            String format = String.format(Locale.US, "%.1f", Arrays.copyOf(new Object[]{Double.valueOf(1.609344d * mph)}, 1));
            Intrinsics.checkNotNullExpressionValue(format, "format(...)");
            return StringsKt.removeSuffix(format, (CharSequence) ".0");
        }
        if (mph == Math.floor(mph)) {
            return String.valueOf((long) mph);
        }
        StringCompanionObject stringCompanionObject2 = StringCompanionObject.INSTANCE;
        String format2 = String.format(Locale.US, "%.1f", Arrays.copyOf(new Object[]{Double.valueOf(mph)}, 1));
        Intrinsics.checkNotNullExpressionValue(format2, "format(...)");
        return StringsKt.removeSuffix(format2, (CharSequence) ".0");
    }

    private final String label(String value) {
        return StringsKt.replace$default(CollectionsKt.joinToString$default(StringsKt.split$default((CharSequence) value, new String[]{"_"}, false, 0, 6, (Object) null), " ", null, null, 0, null, new Function1() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda24
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                CharSequence label$lambda$1;
                label$lambda$1 = GxPersonalitiesPage.label$lambda$1((String) obj);
                return label$lambda$1;
            }
        }, 30, null), " +", "+", false, 4, (Object) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:10:0x001e, code lost:
    
        if (r5.equals("") == false) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final CharSequence label$lambda$1(String s) {
        String str;
        Intrinsics.checkNotNullParameter(s, "s");
        switch (s.hashCode()) {
            case -1106578487:
                if (s.equals("legacy")) {
                    str = "Previous";
                    break;
                }
                String substring = s.substring(0, 1);
                Intrinsics.checkNotNullExpressionValue(substring, "substring(...)");
                Locale ROOT = Locale.ROOT;
                Intrinsics.checkNotNullExpressionValue(ROOT, "ROOT");
                String upperCase = substring.toUpperCase(ROOT);
                Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
                String substring2 = s.substring(1);
                Intrinsics.checkNotNullExpressionValue(substring2, "substring(...)");
                str = upperCase + substring2;
                break;
            case 0:
                str = "";
                break;
            case 3444122:
                if (s.equals("plus")) {
                    str = "+";
                    break;
                }
                String substring3 = s.substring(0, 1);
                Intrinsics.checkNotNullExpressionValue(substring3, "substring(...)");
                Locale ROOT2 = Locale.ROOT;
                Intrinsics.checkNotNullExpressionValue(ROOT2, "ROOT");
                String upperCase2 = substring3.toUpperCase(ROOT2);
                Intrinsics.checkNotNullExpressionValue(upperCase2, "toUpperCase(...)");
                String substring22 = s.substring(1);
                Intrinsics.checkNotNullExpressionValue(substring22, "substring(...)");
                str = upperCase2 + substring22;
                break;
            default:
                String substring32 = s.substring(0, 1);
                Intrinsics.checkNotNullExpressionValue(substring32, "substring(...)");
                Locale ROOT22 = Locale.ROOT;
                Intrinsics.checkNotNullExpressionValue(ROOT22, "ROOT");
                String upperCase22 = substring32.toUpperCase(ROOT22);
                Intrinsics.checkNotNullExpressionValue(upperCase22, "toUpperCase(...)");
                String substring222 = s.substring(1);
                Intrinsics.checkNotNullExpressionValue(substring222, "substring(...)");
                str = upperCase22 + substring222;
                break;
        }
        return str;
    }

    private final List<Double> nums(JSONArray a) {
        double gxHomeNum;
        if (a == null) {
            return CollectionsKt.emptyList();
        }
        Iterable $this$map$iv = RangesKt.until(0, a.length());
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        Iterator<Integer> it = $this$map$iv.iterator();
        while (it.hasNext()) {
            int item$iv$iv = ((IntIterator) it).nextInt();
            gxHomeNum = GxHomeKt.gxHomeNum(a.opt(item$iv$iv));
            destination$iv$iv.add(Double.valueOf(gxHomeNum));
        }
        return (List) destination$iv$iv;
    }

    private final JSONObject config(String p, String c) {
        JSONObject jSONObject = this.data;
        Intrinsics.checkNotNull(jSONObject);
        JSONObject jSONObject2 = jSONObject.getJSONObject("profiles").getJSONObject(p).getJSONObject(c);
        Intrinsics.checkNotNullExpressionValue(jSONObject2, "getJSONObject(...)");
        return jSONObject2;
    }

    private final String preset(String p, String c) {
        String optString = config(p, c).optString("preset");
        Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
        return optString;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Pair<Double, Double> bounds(String c) {
        JSONObject jSONObject = this.data;
        Intrinsics.checkNotNull(jSONObject);
        List it = nums(jSONObject.getJSONObject("bounds").getJSONArray(c));
        return TuplesKt.to(it.get(0), it.get(1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<Double> speeds(String c) {
        JSONObject jSONObject = this.data;
        Intrinsics.checkNotNull(jSONObject);
        return nums(jSONObject.getJSONObject("speed_breakpoints_mph").getJSONArray(c));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<Double> reference(String p, String c) {
        JSONObject optJSONObject;
        JSONObject optJSONObject2;
        JSONObject jSONObject = this.data;
        return nums((jSONObject == null || (optJSONObject = jSONObject.optJSONObject("reference_curves")) == null || (optJSONObject2 = optJSONObject.optJSONObject(p)) == null) ? null : optJSONObject2.optJSONArray(c));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<Double> draft(String p, String c) {
        List<Double> list = this.drafts.get(p + c);
        return list == null ? nums(config(p, c).optJSONArray("curve")) : list;
    }

    private final void discard(String p, String c) {
        this.drafts.remove(p + c);
        this.curveErrors.remove(p + c);
    }

    private final List<String> options(String c, String p) {
        JSONObject optJSONObject;
        JSONObject optJSONObject2;
        JSONObject jSONObject = this.data;
        Intrinsics.checkNotNull(jSONObject);
        JSONArray jSONArray = jSONObject.getJSONObject("options").getJSONArray(c);
        Intrinsics.checkNotNullExpressionValue(jSONArray, "getJSONArray(...)");
        List avail = strings(jSONArray);
        Iterable $this$filter$iv = Intrinsics.areEqual(c, "following") ? CollectionsKt.listOf((Object[]) new String[]{"close", "medium", "far", "traffic", "custom"}) : CollectionsKt.listOf((Object[]) new String[]{"eco", "standard", "sport", "sport_plus", "custom"});
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            String it = (String) element$iv$iv;
            if (avail.contains(it)) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        List choices = CollectionsKt.toMutableList(destination$iv$iv);
        JSONObject jSONObject2 = this.data;
        Intrinsics.checkNotNull(jSONObject2);
        JSONObject optJSONObject3 = jSONObject2.optJSONObject("profiles");
        String sel = (optJSONObject3 == null || (optJSONObject = optJSONObject3.optJSONObject(p)) == null || (optJSONObject2 = optJSONObject.optJSONObject(c)) == null) ? null : optJSONObject2.optString("preset");
        if (Intrinsics.areEqual(c, "following") && sel != null && StringsKt.startsWith$default(sel, "legacy_", false, 2, (Object) null) && avail.contains(sel)) {
            choices.add(0, sel);
        }
        return choices;
    }

    private final List<GalaxyApi.Param> advancedParams(String profile) {
        Iterable $this$filter$iv = this.meta.values();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            GalaxyApi.Param it = (GalaxyApi.Param) element$iv$iv;
            boolean z = false;
            if (Intrinsics.areEqual(it.getParentKey(), key(profile)) && StringsKt.contains$default((CharSequence) it.getKey(), (CharSequence) "Jerk", false, 2, (Object) null)) {
                z = true;
            }
            if (z) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        return (List) destination$iv$iv;
    }

    private final String advancedMode(String k) {
        double v;
        if (this.advancedCustom.contains(k)) {
            return "custom";
        }
        v = GxHomeKt.gxHomeNum(this.vals.get(k));
        if (v == 100.0d) {
            return "standard";
        }
        if (!StringsKt.endsWith$default(k, "JerkDanger", false, 2, (Object) null)) {
            if (v == 50.0d) {
                return "chill";
            }
        }
        return "custom";
    }

    private final Triple<Double, Double, Double> paramBounds(GalaxyApi.Param p) {
        if (new Regex("^(Traffic|Aggressive|Standard|Relaxed)Jerk(Acceleration|Deceleration|Danger|SpeedDecrease|Speed)$").matches(p.getKey())) {
            return new Triple<>(Double.valueOf(25.0d), Double.valueOf(200.0d), Double.valueOf(1.0d));
        }
        boolean f = Intrinsics.areEqual(p.getDataType(), "float");
        return new Triple<>(Double.valueOf(p.num("min", 0.0d)), Double.valueOf(p.num("max", 100.0d)), Double.valueOf(p.num("step", f ? 0.01d : 1.0d)));
    }

    private final boolean paramLocked(String k) {
        boolean gxHomeTruthy;
        boolean gxHomeTruthy2;
        boolean gxHomeTruthy3;
        boolean gxHomeTruthy4;
        boolean gxHomeTruthy5;
        GalaxyApi.Param p = this.meta.get(k);
        if (p == null || getEditingLocked()) {
            return true;
        }
        gxHomeTruthy = GxHomeKt.gxHomeTruthy(p.getO().opt("requires_parked"));
        if (gxHomeTruthy) {
            gxHomeTruthy5 = GxHomeKt.gxHomeTruthy(this.vals.get("VehicleParked"));
            if (!gxHomeTruthy5) {
                return true;
            }
        }
        String it = p.str("requires_capability");
        if (it != null) {
            if (!(it.length() > 0)) {
                it = null;
            }
            if (it != null) {
                gxHomeTruthy4 = GxHomeKt.gxHomeTruthy(this.vals.get(it));
                if (!gxHomeTruthy4) {
                    return true;
                }
            }
        }
        String it2 = p.str("disabled_when_key_true");
        if (it2 != null) {
            if (!(it2.length() > 0)) {
                it2 = null;
            }
            if (it2 != null) {
                gxHomeTruthy3 = GxHomeKt.gxHomeTruthy(this.vals.get(it2));
                if (gxHomeTruthy3) {
                    return true;
                }
            }
        }
        String it3 = p.str("requires_nonempty_key");
        if (it3 != null) {
            String it4 = it3.length() > 0 ? it3 : null;
            if (it4 != null) {
                Object v = this.vals.get(it4);
                gxHomeTruthy2 = GxHomeKt.gxHomeTruthy(v);
                if (!gxHomeTruthy2 || Intrinsics.areEqual(v, "{}")) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0056 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:29:? A[LOOP:0: B:14:0x0022->B:29:?, LOOP_END, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static final boolean validate$finite(JSONArray a) {
        Iterable $this$all$iv;
        int it;
        if (a != null) {
            Iterable $this$all$iv2 = RangesKt.until(0, a.length());
            if (!($this$all$iv2 instanceof Collection) || !((Collection) $this$all$iv2).isEmpty()) {
                Iterator it2 = $this$all$iv2.iterator();
                while (true) {
                    if (it2.hasNext()) {
                        int element$iv = ((IntIterator) it2).nextInt();
                        Object v = a.opt(element$iv);
                        if (v instanceof Number) {
                            double doubleValue = ((Number) v).doubleValue();
                            if ((Double.isInfinite(doubleValue) || Double.isNaN(doubleValue)) ? false : true) {
                                it = 1;
                                if (it != 0) {
                                    $this$all$iv = null;
                                    break;
                                }
                            }
                        }
                        it = 0;
                        if (it != 0) {
                        }
                    } else {
                        $this$all$iv = 1;
                        break;
                    }
                }
            } else {
                $this$all$iv = 1;
            }
            return $this$all$iv != null;
        }
        return false;
    }

    private final JSONObject validate(JSONObject d) {
        Iterator<String> it;
        boolean z;
        boolean z2;
        JSONObject optJSONObject;
        JSONObject optJSONObject2;
        Iterator<String> it2 = this.profilesIds.iterator();
        while (it2.hasNext()) {
            String p = it2.next();
            Iterator<Pair<String, String>> it3 = this.categories.iterator();
            while (it3.hasNext()) {
                String c = it3.next().component1();
                JSONObject optJSONObject3 = d.optJSONObject("profiles");
                JSONObject config = (optJSONObject3 == null || (optJSONObject2 = optJSONObject3.optJSONObject(p)) == null) ? null : optJSONObject2.optJSONObject(c);
                JSONArray curve = config != null ? config.optJSONArray("curve") : null;
                JSONObject optJSONObject4 = d.optJSONObject("speed_breakpoints_mph");
                JSONArray speeds = optJSONObject4 != null ? optJSONObject4.optJSONArray(c) : null;
                JSONObject optJSONObject5 = d.optJSONObject("bounds");
                JSONArray bounds = optJSONObject5 != null ? optJSONObject5.optJSONArray(c) : null;
                JSONObject optJSONObject6 = d.optJSONObject("reference_curves");
                JSONArray ref = (optJSONObject6 == null || (optJSONObject = optJSONObject6.optJSONObject(p)) == null) ? null : optJSONObject.optJSONArray(c);
                JSONObject optJSONObject7 = d.optJSONObject("options");
                JSONArray opts = optJSONObject7 != null ? optJSONObject7.optJSONArray(c) : null;
                List sp = nums(speeds);
                if (config == null || curve == null || speeds == null || speeds.length() == 0 || !validate$finite(speeds)) {
                    it = it2;
                    z = true;
                } else {
                    Iterable $this$any$iv = CollectionsKt.withIndex(sp);
                    it = it2;
                    if (!($this$any$iv instanceof Collection) || !((Collection) $this$any$iv).isEmpty()) {
                        Iterator it4 = $this$any$iv.iterator();
                        while (true) {
                            if (!it4.hasNext()) {
                                z2 = false;
                                break;
                            }
                            Object element$iv = it4.next();
                            IndexedValue indexedValue = (IndexedValue) element$iv;
                            int i = indexedValue.getIndex();
                            double v = ((Number) indexedValue.component2()).doubleValue();
                            if (v < 0.0d || (i > 0 && v <= sp.get(i + (-1)).doubleValue())) {
                                z2 = true;
                                break;
                            }
                        }
                    } else {
                        z2 = false;
                    }
                    if (z2 || ((Number) CollectionsKt.last(sp)).doubleValue() <= 0.0d) {
                        z = true;
                    } else if (bounds != null && bounds.length() == 2 && validate$finite(bounds)) {
                        z = true;
                        if (nums(bounds).get(0).doubleValue() < nums(bounds).get(1).doubleValue() && ref != null && ref.length() == speeds.length() && validate$finite(ref) && opts != null && strings(opts).contains(config.optString("preset")) && ((!Intrinsics.areEqual(config.optString("preset"), "custom") || curve.length() == speeds.length()) && validate$finite(curve))) {
                            z = false;
                        }
                    } else {
                        z = true;
                    }
                }
                boolean bad = z;
                if (bad) {
                    throw new IllegalStateException("Profile data is unavailable or malformed. Retrying automatically…");
                }
                it2 = it;
            }
        }
        return d;
    }

    private final void acceptData(JSONObject d) {
        JSONObject optJSONObject;
        JSONObject optJSONObject2;
        JSONObject optJSONObject3;
        JSONObject next = validate(d);
        JSONObject old = this.data;
        for (String p : this.profilesIds) {
            Iterator<Pair<String, String>> it = this.categories.iterator();
            while (it.hasNext()) {
                String c = it.next().component1();
                String k = p + c;
                String before = (old == null || (optJSONObject = old.optJSONObject("profiles")) == null || (optJSONObject2 = optJSONObject.optJSONObject(p)) == null || (optJSONObject3 = optJSONObject2.optJSONObject(c)) == null) ? null : optJSONObject3.toString();
                String after = next.getJSONObject("profiles").getJSONObject(p).getJSONObject(c).toString();
                Intrinsics.checkNotNullExpressionValue(after, "toString(...)");
                if (this.drafts.containsKey(k) && !Intrinsics.areEqual(before, after)) {
                    discard(p, c);
                    if (!this.busy && !this.curvePending) {
                        this.drag = null;
                        this.notice = "Saved profiles changed. The affected preview was cancelled.";
                    }
                }
            }
        }
        this.data = next;
    }

    private final void fetchContext(final boolean apply, final Function1<? super Boolean, Unit> then) {
        final GalaxyApi a = getApi();
        if (a == null) {
            then.invoke(false);
        } else {
            a.refreshValues(new Function1() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda36
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit fetchContext$lambda$18;
                    fetchContext$lambda$18 = GxPersonalitiesPage.fetchContext$lambda$18(Function1.this, a, this, apply, ((Boolean) obj).booleanValue());
                    return fetchContext$lambda$18;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit fetchContext$lambda$18(final Function1 $then, GalaxyApi $a, final GxPersonalitiesPage this$0, final boolean $apply, boolean ok) {
        if (!ok) {
            $then.invoke(false);
            return Unit.INSTANCE;
        }
        final HashMap m = new HashMap($a.getValues());
        this$0.getJson("/api/device/status", new Function2() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit fetchContext$lambda$18$lambda$17;
                fetchContext$lambda$18$lambda$17 = GxPersonalitiesPage.fetchContext$lambda$18$lambda$17(GxPersonalitiesPage.this, m, $apply, $then, (String) obj, (JSONObject) obj2);
                return fetchContext$lambda$18$lambda$17;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit fetchContext$lambda$18$lambda$17(final GxPersonalitiesPage this$0, final HashMap $m, final boolean $apply, final Function1 $then, final String e1, final JSONObject st) {
        this$0.getText("/api/params?key=IsOffroad", new Function2() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit fetchContext$lambda$18$lambda$17$lambda$16;
                fetchContext$lambda$18$lambda$17$lambda$16 = GxPersonalitiesPage.fetchContext$lambda$18$lambda$17$lambda$16(GxPersonalitiesPage.this, $m, e1, st, $apply, $then, (String) obj, (String) obj2);
                return fetchContext$lambda$18$lambda$17$lambda$16;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0063  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Unit fetchContext$lambda$18$lambda$17$lambda$16(GxPersonalitiesPage this$0, HashMap $m, String $e1, JSONObject $st, boolean $apply, Function1 $then, String e2, String t) {
        Intrinsics.checkNotNullParameter(t, "t");
        Boolean on = this$0.roadFlag($m.get("IsOnroad"));
        Boolean off = null;
        if (on == null) {
            on = ($e1 != null || $st == null) ? null : Boolean.valueOf(Intrinsics.areEqual($st.optString("status"), "Driving"));
        }
        Boolean roadFlag = $m.containsKey("IsOffroad") ? this$0.roadFlag($m.get("IsOffroad")) : null;
        if (roadFlag == null) {
            roadFlag = e2 == null ? this$0.roadFlag(StringsKt.trim((CharSequence) t).toString()) : null;
            if (roadFlag == null) {
                if (on != null) {
                    boolean it = on.booleanValue();
                    off = Boolean.valueOf(!it);
                }
                if ($apply) {
                    this$0.vals.clear();
                    this$0.vals.putAll($m);
                    this$0.vals.put("IsOnroad", on);
                    this$0.vals.put("IsOffroad", off);
                    this$0.onroad = on;
                    this$0.offroad = off;
                }
                $then.invoke(true);
                return Unit.INSTANCE;
            }
        }
        off = roadFlag;
        if ($apply) {
        }
        $then.invoke(true);
        return Unit.INSTANCE;
    }

    private final void load() {
        if (this.busy || this.loadPending || this.contextPending) {
            return;
        }
        this.loadPending = true;
        this.ready = false;
        getJson("/api/personality_profiles", new Function2() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda27
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit load$lambda$23;
                load$lambda$23 = GxPersonalitiesPage.load$lambda$23(GxPersonalitiesPage.this, (String) obj, (JSONObject) obj2);
                return load$lambda$23;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit load$lambda$23(final GxPersonalitiesPage this$0, final String e, final JSONObject d) {
        this$0.fetchContext(true, new Function1() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit load$lambda$23$lambda$22;
                load$lambda$23$lambda$22 = GxPersonalitiesPage.load$lambda$23$lambda$22(GxPersonalitiesPage.this, e, d, ((Boolean) obj).booleanValue());
                return load$lambda$23$lambda$22;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0161, code lost:
    
        r3.loadCatalog();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Unit load$lambda$23$lambda$22(GxPersonalitiesPage this$0, String $e, JSONObject $d, boolean ok) {
        Iterable $this$any$iv;
        this$0.loadPending = false;
        try {
        } catch (Exception ex) {
            String message = ex.getMessage();
            if (message == null) {
                message = ex.toString();
            }
            this$0.error = message;
        }
        if ($e != null || $d == null) {
            throw new IllegalStateException($e == null ? "bad reply" : $e);
        }
        GalaxyApi a = this$0.getApi();
        if (ok && a != null && !a.getCatalog().isEmpty()) {
            this$0.acceptData($d);
            LinkedHashMap m = new LinkedHashMap();
            for (GalaxyApi.Category cat : a.getCatalog()) {
                for (GalaxyApi.Param p : cat.getParams()) {
                    m.put(p.getKey(), p);
                }
            }
            this$0.meta = m;
            List listOf = CollectionsKt.listOf("CustomPersonalities");
            Iterable $this$flatMap$iv = this$0.profilesIds;
            Collection destination$iv$iv = new ArrayList();
            for (Object element$iv$iv : $this$flatMap$iv) {
                String p2 = (String) element$iv$iv;
                List listOf2 = CollectionsKt.listOf(this$0.key(p2));
                Iterable $this$map$iv = this$0.jerkSuffixes;
                LinkedHashMap m2 = m;
                Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                Iterable $this$mapTo$iv$iv = $this$map$iv;
                for (Object item$iv$iv : $this$mapTo$iv$iv) {
                    String it = (String) item$iv$iv;
                    destination$iv$iv2.add(this$0.label(p2) + "Jerk" + it);
                    $this$map$iv = $this$map$iv;
                    $this$mapTo$iv$iv = $this$mapTo$iv$iv;
                }
                Iterable list$iv$iv = CollectionsKt.plus((Collection) listOf2, destination$iv$iv2);
                CollectionsKt.addAll(destination$iv$iv, list$iv$iv);
                m = m2;
            }
            Iterable required = CollectionsKt.plus((Collection) listOf, destination$iv$iv);
            Iterable $this$any$iv2 = required;
            if (!($this$any$iv2 instanceof Collection) || !((Collection) $this$any$iv2).isEmpty()) {
                Iterator it2 = $this$any$iv2.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        $this$any$iv = null;
                        break;
                    }
                    Object element$iv = it2.next();
                    String it3 = (String) element$iv;
                    if (!this$0.meta.containsKey(it3)) {
                        $this$any$iv = 1;
                        break;
                    }
                }
            } else {
                $this$any$iv = null;
            }
            if ($this$any$iv != null) {
                throw new IllegalStateException("Personality metadata is incomplete. Retrying automatically…");
            }
            this$0.ready = true;
            this$0.error = "";
            if (this$0.recovery) {
                this$0.notice = "Save could not be confirmed. Showing verified saved state; review it before editing again.";
                this$0.recovery = false;
            }
            this$0.render();
            return Unit.INSTANCE;
        }
        throw new IllegalStateException("Settings metadata is unavailable. Retrying automatically…");
    }

    private final void refreshContext() {
        if (this.busy || !this.ready || this.contextPending) {
            return;
        }
        this.contextPending = true;
        final Boolean prevOn = roadFlag(this.vals.get("IsOnroad"));
        fetchContext(true ^ this.busy, new Function1() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit refreshContext$lambda$24;
                refreshContext$lambda$24 = GxPersonalitiesPage.refreshContext$lambda$24(GxPersonalitiesPage.this, prevOn, ((Boolean) obj).booleanValue());
                return refreshContext$lambda$24;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit refreshContext$lambda$24(GxPersonalitiesPage this$0, Boolean $prevOn, boolean ok) {
        this$0.contextPending = false;
        if (!ok) {
            this$0.ready = false;
            this$0.error = "Connection lost. Reconnecting…";
            this$0.render();
            return Unit.INSTANCE;
        }
        Boolean nextOn = this$0.roadFlag(this$0.vals.get("IsOnroad"));
        if (!this$0.getRoadStateKnown() || ($prevOn != null && !Intrinsics.areEqual(nextOn, $prevOn))) {
            this$0.drag = null;
            this$0.drafts.clear();
        }
        this$0.render();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void whenContextIdle(final Function0<Unit> block) {
        if (getAct().isFinishing() || getAct().isDestroyed()) {
            return;
        }
        if (this.contextPending) {
            getMain().postDelayed(new Runnable() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda50
                @Override // java.lang.Runnable
                public final void run() {
                    GxPersonalitiesPage.this.whenContextIdle(block);
                }
            }, 150L);
        } else {
            block.invoke();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void write$default(final GxPersonalitiesPage gxPersonalitiesPage, Function1 function1, Function0 function0, Function1 function12, int i, Object obj) {
        if ((i & 2) != 0) {
            function0 = new Function0() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda34
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    boolean write$lambda$26;
                    write$lambda$26 = GxPersonalitiesPage.write$lambda$26(GxPersonalitiesPage.this);
                    return Boolean.valueOf(write$lambda$26);
                }
            };
        }
        if ((i & 4) != 0) {
            function12 = new Function1() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda35
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    Unit write$lambda$27;
                    write$lambda$27 = GxPersonalitiesPage.write$lambda$27(((Boolean) obj2).booleanValue());
                    return write$lambda$27;
                }
            };
        }
        gxPersonalitiesPage.write(function1, function0, function12);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean write$lambda$26(GxPersonalitiesPage this$0) {
        return !this$0.getEditingLocked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit write$lambda$27(boolean it) {
        return Unit.INSTANCE;
    }

    private final void write(final Function1<? super Function2<? super String, ? super JSONObject, Unit>, Unit> action, final Function0<Boolean> check, final Function1<? super Boolean, Unit> onDone) {
        if (!getRoadStateKnown()) {
            onDone.invoke(false);
        } else {
            final boolean startOn = isOnroad();
            whenContextIdle(new Function0() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda16
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit write$lambda$31;
                    write$lambda$31 = GxPersonalitiesPage.write$lambda$31(GxPersonalitiesPage.this, startOn, check, onDone, action);
                    return write$lambda$31;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit write$lambda$31(final GxPersonalitiesPage this$0, boolean $startOn, Function0 $check, final Function1 $onDone, Function1 $action) {
        if (!this$0.getRoadStateKnown() || this$0.isOnroad() != $startOn || !((Boolean) $check.invoke()).booleanValue()) {
            $onDone.invoke(false);
            return Unit.INSTANCE;
        }
        this$0.busy = true;
        this$0.error = "";
        this$0.notice = "";
        this$0.render();
        $action.invoke(new Function2() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit write$lambda$31$lambda$30;
                write$lambda$31$lambda$30 = GxPersonalitiesPage.write$lambda$31$lambda$30(GxPersonalitiesPage.this, $onDone, (String) obj, (JSONObject) obj2);
                return write$lambda$31$lambda$30;
            }
        });
        return Unit.INSTANCE;
    }

    private static final void write$lambda$31$fail(GxPersonalitiesPage this$0, Function1<? super Boolean, Unit> function1, String msg) {
        this$0.busy = false;
        this$0.error = msg + " Rechecking saved state…";
        this$0.ready = false;
        this$0.recovery = true;
        this$0.drafts.clear();
        this$0.render();
        function1.invoke(false);
        this$0.load();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit write$lambda$31$lambda$30(final GxPersonalitiesPage this$0, final Function1 $onDone, String e, JSONObject jSONObject) {
        if (e != null) {
            write$lambda$31$fail(this$0, $onDone, e);
            return Unit.INSTANCE;
        }
        this$0.getJson("/api/personality_profiles", new Function2() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda37
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit write$lambda$31$lambda$30$lambda$29;
                write$lambda$31$lambda$30$lambda$29 = GxPersonalitiesPage.write$lambda$31$lambda$30$lambda$29(GxPersonalitiesPage.this, $onDone, (String) obj, (JSONObject) obj2);
                return write$lambda$31$lambda$30$lambda$29;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit write$lambda$31$lambda$30$lambda$29(final GxPersonalitiesPage this$0, final Function1 $onDone, final String e2, final JSONObject d) {
        this$0.fetchContext(true, new Function1() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda43
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit write$lambda$31$lambda$30$lambda$29$lambda$28;
                write$lambda$31$lambda$30$lambda$29$lambda$28 = GxPersonalitiesPage.write$lambda$31$lambda$30$lambda$29$lambda$28(e2, d, this$0, $onDone, ((Boolean) obj).booleanValue());
                return write$lambda$31$lambda$30$lambda$29$lambda$28;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit write$lambda$31$lambda$30$lambda$29$lambda$28(String $e2, JSONObject $d, GxPersonalitiesPage this$0, Function1 $onDone, boolean ok) {
        if ($e2 != null || $d == null) {
            write$lambda$31$fail(this$0, $onDone, $e2 == null ? "bad reply" : $e2);
            return Unit.INSTANCE;
        }
        if (!ok) {
            write$lambda$31$fail(this$0, $onDone, "Connection lost.");
            return Unit.INSTANCE;
        }
        try {
            this$0.acceptData($d);
            this$0.busy = false;
            this$0.toast("Driving personalities saved.");
            this$0.render();
            $onDone.invoke(true);
            return Unit.INSTANCE;
        } catch (Exception ex) {
            String message = ex.getMessage();
            if (message == null) {
                message = ex.toString();
            }
            write$lambda$31$fail(this$0, $onDone, message);
            return Unit.INSTANCE;
        }
    }

    private final JSONObject putParamBody(String k, Object v) {
        JSONObject put = new JSONObject().put("key", k).put("value", v);
        Intrinsics.checkNotNullExpressionValue(put, "put(...)");
        return put;
    }

    private final void toggle(final String k, final boolean v) {
        write(new Function1() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit unit;
                unit = GxPersonalitiesPage.toggle$lambda$32(GxPersonalitiesPage.this, k, v, (Function2) obj);
                return unit;
            }
        }, new Function0() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda18
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean z;
                z = GxPersonalitiesPage.toggle$lambda$33(GxPersonalitiesPage.this, k);
                return Boolean.valueOf(z);
            }
        }, new Function1() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda19
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit unit;
                unit = GxPersonalitiesPage.toggle$lambda$34(GxPersonalitiesPage.this, ((Boolean) obj).booleanValue());
                return unit;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit toggle$lambda$32(GxPersonalitiesPage this$0, String $k, boolean $v, Function2 cb) {
        Intrinsics.checkNotNullParameter(cb, "cb");
        GxPage.send$default(this$0, "PUT", "/api/params", this$0.putParamBody($k, Boolean.valueOf($v)), 0L, cb, 8, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean toggle$lambda$33(GxPersonalitiesPage this$0, String $k) {
        return !this$0.paramLocked($k);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit toggle$lambda$34(GxPersonalitiesPage this$0, boolean it) {
        this$0.forceRender();
        return Unit.INSTANCE;
    }

    private final void migrate() {
        write(new Function1() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda20
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit migrate$lambda$35;
                migrate$lambda$35 = GxPersonalitiesPage.migrate$lambda$35(GxPersonalitiesPage.this, (Function2) obj);
                return migrate$lambda$35;
            }
        }, new Function0() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda21
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean migrate$lambda$36;
                migrate$lambda$36 = GxPersonalitiesPage.migrate$lambda$36(GxPersonalitiesPage.this);
                return Boolean.valueOf(migrate$lambda$36);
            }
        }, new Function1() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda23
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit migrate$lambda$37;
                migrate$lambda$37 = GxPersonalitiesPage.migrate$lambda$37(GxPersonalitiesPage.this, ((Boolean) obj).booleanValue());
                return migrate$lambda$37;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit migrate$lambda$35(GxPersonalitiesPage this$0, Function2 cb) {
        Intrinsics.checkNotNullParameter(cb, "cb");
        GxPage.send$default(this$0, "POST", "/api/personality_profiles/migrate", null, 0L, cb, 8, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean migrate$lambda$36(GxPersonalitiesPage this$0) {
        return (this$0.getLocked() || this$0.isOnroad()) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit migrate$lambda$37(GxPersonalitiesPage this$0, boolean it) {
        this$0.forceRender();
        return Unit.INSTANCE;
    }

    private final void choosePreset(final String p, final String c, final String pr) {
        if (Intrinsics.areEqual(preset(p, c), pr)) {
            return;
        }
        write$default(this, new Function1() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit choosePreset$lambda$38;
                choosePreset$lambda$38 = GxPersonalitiesPage.choosePreset$lambda$38(GxPersonalitiesPage.this, p, c, pr, (Function2) obj);
                return choosePreset$lambda$38;
            }
        }, null, new Function1() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit choosePreset$lambda$39;
                choosePreset$lambda$39 = GxPersonalitiesPage.choosePreset$lambda$39(GxPersonalitiesPage.this, p, c, pr, ((Boolean) obj).booleanValue());
                return choosePreset$lambda$39;
            }
        }, 2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit choosePreset$lambda$38(GxPersonalitiesPage this$0, String $p, String $c, String $pr, Function2 cb) {
        Intrinsics.checkNotNullParameter(cb, "cb");
        GxPage.send$default(this$0, "PUT", "/api/personality_profiles", new JSONObject().put("profile", $p).put("category", $c).put("preset", $pr).put("curve", new JSONArray()).put("expected", this$0.config($p, $c)), 0L, cb, 8, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit choosePreset$lambda$39(GxPersonalitiesPage this$0, String $p, String $c, String $pr, boolean ok) {
        if (ok) {
            this$0.discard($p, $c);
            this$0.notice = "";
            if (Intrinsics.areEqual($pr, "custom")) {
                this$0.advancedOpen.add($p);
            }
        }
        this$0.forceRender();
        return Unit.INSTANCE;
    }

    static /* synthetic */ void saveCurve$default(GxPersonalitiesPage gxPersonalitiesPage, String str, String str2, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        gxPersonalitiesPage.saveCurve(str, str2, z);
    }

    private final void saveCurve(final String p, final String c, final boolean reset) {
        if (getEditingLocked() || !getShown()) {
            return;
        }
        final boolean startOn = isOnroad();
        final List snapshot = reset ? CollectionsKt.emptyList() : CollectionsKt.toList(draft(p, c));
        this.curvePending = true;
        whenContextIdle(new Function0() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit saveCurve$lambda$43;
                saveCurve$lambda$43 = GxPersonalitiesPage.saveCurve$lambda$43(GxPersonalitiesPage.this, startOn, p, c, snapshot, reset);
                return saveCurve$lambda$43;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit saveCurve$lambda$43(final GxPersonalitiesPage this$0, final boolean $startOn, final String $p, final String $c, final List $snapshot, final boolean $reset) {
        if (!this$0.getRoadStateKnown() || this$0.isOnroad() != $startOn) {
            this$0.discard($p, $c);
            this$0.curvePending = false;
            this$0.forceRender();
            return Unit.INSTANCE;
        }
        this$0.write(new Function1() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda29
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit saveCurve$lambda$43$lambda$40;
                saveCurve$lambda$43$lambda$40 = GxPersonalitiesPage.saveCurve$lambda$43$lambda$40($p, $c, $snapshot, $reset, this$0, (Function2) obj);
                return saveCurve$lambda$43$lambda$40;
            }
        }, new Function0() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda30
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean saveCurve$lambda$43$lambda$41;
                saveCurve$lambda$43$lambda$41 = GxPersonalitiesPage.saveCurve$lambda$43$lambda$41(GxPersonalitiesPage.this, $startOn);
                return Boolean.valueOf(saveCurve$lambda$43$lambda$41);
            }
        }, new Function1() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda31
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit saveCurve$lambda$43$lambda$42;
                saveCurve$lambda$43$lambda$42 = GxPersonalitiesPage.saveCurve$lambda$43$lambda$42(GxPersonalitiesPage.this, $p, $c, ((Boolean) obj).booleanValue());
                return saveCurve$lambda$43$lambda$42;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit saveCurve$lambda$43$lambda$40(String $p, String $c, List $snapshot, boolean $reset, GxPersonalitiesPage this$0, Function2 cb) {
        Intrinsics.checkNotNullParameter(cb, "cb");
        JSONObject body = new JSONObject().put("profile", $p).put("category", $c).put("preset", "custom").put("curve", new JSONArray((Collection) $snapshot));
        if ($reset) {
            body.put("reset", true);
        }
        body.put("expected", this$0.config($p, $c));
        GxPage.send$default(this$0, "PUT", "/api/personality_profiles", body, 0L, cb, 8, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean saveCurve$lambda$43$lambda$41(GxPersonalitiesPage this$0, boolean $startOn) {
        return (this$0.getLocked() || this$0.isOnroad() != $startOn || this$0.getMigrationRequired()) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit saveCurve$lambda$43$lambda$42(GxPersonalitiesPage this$0, String $p, String $c, boolean ok) {
        if (ok) {
            this$0.notice = "";
        }
        this$0.discard($p, $c);
        this$0.curvePending = false;
        this$0.forceRender();
        return Unit.INSTANCE;
    }

    private final void editPoint(final String p, final String c, final int i) {
        String gxHomeFmt;
        if (getEditingLocked()) {
            return;
        }
        List cur = draft(p, c);
        Pair<Double, Double> bounds = bounds(c);
        final double min = bounds.component1().doubleValue();
        final double max = bounds.component2().doubleValue();
        GxPersonalitiesPage gxPersonalitiesPage = this;
        String label = label(p);
        Iterable $this$first$iv = this.categories;
        for (Object element$iv : $this$first$iv) {
            Pair it = (Pair) element$iv;
            if (Intrinsics.areEqual(it.getFirst(), c)) {
                String str = label + " " + ((Pair) element$iv).getSecond() + " at " + speed(speeds(c).get(i).doubleValue()) + " " + speedUnit();
                String str2 = Intrinsics.areEqual(c, "following") ? "seconds" : "m/s²";
                gxHomeFmt = GxHomeKt.gxHomeFmt(cur.get(i).doubleValue());
                GxPage.prompt$default(gxPersonalitiesPage, str, str2, gxHomeFmt, 8194, null, new Function1() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda13
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        Unit editPoint$lambda$45;
                        editPoint$lambda$45 = GxPersonalitiesPage.editPoint$lambda$45(GxPersonalitiesPage.this, min, max, p, c, i, (String) obj);
                        return editPoint$lambda$45;
                    }
                }, 16, null);
                return;
            }
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit editPoint$lambda$45(GxPersonalitiesPage this$0, double $min, double $max, String $p, String $c, int $i, String raw) {
        String gxHomeFmt;
        String gxHomeFmt2;
        Intrinsics.checkNotNullParameter(raw, "raw");
        if (this$0.getEditingLocked()) {
            return Unit.INSTANCE;
        }
        Double v = StringsKt.toDoubleOrNull(StringsKt.trim((CharSequence) raw).toString());
        if (!StringsKt.isBlank(raw) && v != null) {
            double doubleValue = v.doubleValue();
            if (((Double.isInfinite(doubleValue) || Double.isNaN(doubleValue)) ? false : true) && v.doubleValue() >= $min && v.doubleValue() <= $max) {
                if (Math.abs((v.doubleValue() / 0.05d) - Math.round(v.doubleValue() / 0.05d)) > 1.0E-7d) {
                    gxHomeFmt = GxHomeKt.gxHomeFmt($min);
                    gxHomeFmt2 = GxHomeKt.gxHomeFmt($max);
                    this$0.curveErrors.put($p + $c, "Edited points must be between " + gxHomeFmt + " and " + gxHomeFmt2 + ", in 0.05 increments.");
                    this$0.forceRender();
                    return Unit.INSTANCE;
                }
                List curve = CollectionsKt.toMutableList((Collection) this$0.draft($p, $c));
                curve.set($i, v);
                this$0.drafts.put($p + $c, curve);
                this$0.curveErrors.remove($p + $c);
                saveCurve$default(this$0, $p, $c, false, 4, null);
                return Unit.INSTANCE;
            }
        }
        gxHomeFmt = GxHomeKt.gxHomeFmt($min);
        gxHomeFmt2 = GxHomeKt.gxHomeFmt($max);
        this$0.curveErrors.put($p + $c, "Edited points must be between " + gxHomeFmt + " and " + gxHomeFmt2 + ", in 0.05 increments.");
        this$0.forceRender();
        return Unit.INSTANCE;
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void setAdvanced$default(GxPersonalitiesPage gxPersonalitiesPage, GalaxyApi.Param param, String str, Function1 function1, int i, Object obj) {
        if ((i & 4) != 0) {
            function1 = new Function1() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda4
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    Unit advanced$lambda$46;
                    advanced$lambda$46 = GxPersonalitiesPage.setAdvanced$lambda$46(((Boolean) obj2).booleanValue());
                    return advanced$lambda$46;
                }
            };
        }
        gxPersonalitiesPage.setAdvanced(param, str, function1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit setAdvanced$lambda$46(boolean it) {
        return Unit.INSTANCE;
    }

    private final void setAdvanced(final GalaxyApi.Param param, final String raw, final Function1<? super Boolean, Unit> onDone) {
        if (!getRoadStateKnown()) {
            onDone.invoke(false);
        } else {
            final boolean startOn = isOnroad();
            whenContextIdle(new Function0() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda45
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit advanced$lambda$49;
                    advanced$lambda$49 = GxPersonalitiesPage.setAdvanced$lambda$49(GxPersonalitiesPage.this, startOn, onDone, raw, param);
                    return advanced$lambda$49;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit setAdvanced$lambda$49(final GxPersonalitiesPage this$0, final boolean $startOn, Function1 $onDone, String $raw, final GalaxyApi.Param $param) {
        Boolean bool;
        Boolean bool2;
        String gxHomeFmt;
        String gxHomeFmt2;
        String gxHomeFmt3;
        boolean z = false;
        if (!this$0.getRoadStateKnown()) {
            bool = false;
        } else {
            if (this$0.isOnroad() == $startOn) {
                Double v = StringsKt.toDoubleOrNull(StringsKt.trim((CharSequence) $raw).toString());
                Triple<Double, Double, Double> paramBounds = this$0.paramBounds($param);
                double min = paramBounds.component1().doubleValue();
                double max = paramBounds.component2().doubleValue();
                double step = paramBounds.component3().doubleValue();
                if (!StringsKt.isBlank($raw) && v != null) {
                    double doubleValue = v.doubleValue();
                    if (!Double.isInfinite(doubleValue) && !Double.isNaN(doubleValue)) {
                        z = true;
                    }
                    if (z && v.doubleValue() >= min && v.doubleValue() <= max) {
                        bool2 = false;
                        if (Math.abs(((v.doubleValue() - min) / step) - Math.round((v.doubleValue() - min) / step)) <= 1.0E-7d) {
                            this$0.advancedErrors.remove($param.getKey());
                            final Object num = Intrinsics.areEqual(v, Math.floor(v.doubleValue())) ? Long.valueOf((long) v.doubleValue()) : v;
                            this$0.write(new Function1() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda25
                                @Override // kotlin.jvm.functions.Function1
                                public final Object invoke(Object obj) {
                                    Unit advanced$lambda$49$lambda$47;
                                    advanced$lambda$49$lambda$47 = GxPersonalitiesPage.setAdvanced$lambda$49$lambda$47(GxPersonalitiesPage.this, $param, num, (Function2) obj);
                                    return advanced$lambda$49$lambda$47;
                                }
                            }, new Function0() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda26
                                @Override // kotlin.jvm.functions.Function0
                                public final Object invoke() {
                                    boolean advanced$lambda$49$lambda$48;
                                    advanced$lambda$49$lambda$48 = GxPersonalitiesPage.setAdvanced$lambda$49$lambda$48(GxPersonalitiesPage.this, $param, $startOn);
                                    return Boolean.valueOf(advanced$lambda$49$lambda$48);
                                }
                            }, $onDone);
                            return Unit.INSTANCE;
                        }
                        HashMap<String, String> hashMap = this$0.advancedErrors;
                        String key = $param.getKey();
                        gxHomeFmt = GxHomeKt.gxHomeFmt(min);
                        gxHomeFmt2 = GxHomeKt.gxHomeFmt(max);
                        gxHomeFmt3 = GxHomeKt.gxHomeFmt(step);
                        hashMap.put(key, "Enter " + gxHomeFmt + "–" + gxHomeFmt2 + "% in increments of " + gxHomeFmt3 + ".");
                        this$0.forceRender();
                        $onDone.invoke(bool2);
                        return Unit.INSTANCE;
                    }
                }
                bool2 = false;
                HashMap<String, String> hashMap2 = this$0.advancedErrors;
                String key2 = $param.getKey();
                gxHomeFmt = GxHomeKt.gxHomeFmt(min);
                gxHomeFmt2 = GxHomeKt.gxHomeFmt(max);
                gxHomeFmt3 = GxHomeKt.gxHomeFmt(step);
                hashMap2.put(key2, "Enter " + gxHomeFmt + "–" + gxHomeFmt2 + "% in increments of " + gxHomeFmt3 + ".");
                this$0.forceRender();
                $onDone.invoke(bool2);
                return Unit.INSTANCE;
            }
            bool = false;
        }
        $onDone.invoke(bool);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit setAdvanced$lambda$49$lambda$47(GxPersonalitiesPage this$0, GalaxyApi.Param $param, Object $num, Function2 cb) {
        Intrinsics.checkNotNullParameter(cb, "cb");
        GxPage.send$default(this$0, "PUT", "/api/params", this$0.putParamBody($param.getKey(), $num), 0L, cb, 8, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean setAdvanced$lambda$49$lambda$48(GxPersonalitiesPage this$0, GalaxyApi.Param $param, boolean $startOn) {
        return !this$0.paramLocked($param.getKey()) && this$0.isOnroad() == $startOn;
    }

    private final void advancedPreset(final GalaxyApi.Param param, String mode) {
        if (paramLocked(param.getKey())) {
            return;
        }
        if (!Intrinsics.areEqual(mode, "custom")) {
            setAdvanced(param, Intrinsics.areEqual(mode, "chill") ? "50" : "100", new Function1() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda42
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit advancedPreset$lambda$50;
                    advancedPreset$lambda$50 = GxPersonalitiesPage.advancedPreset$lambda$50(GxPersonalitiesPage.this, param, ((Boolean) obj).booleanValue());
                    return advancedPreset$lambda$50;
                }
            });
        } else {
            this.advancedCustom.add(param.getKey());
            forceRender();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit advancedPreset$lambda$50(GxPersonalitiesPage this$0, GalaxyApi.Param $param, boolean ok) {
        if (ok) {
            this$0.advancedCustom.remove($param.getKey());
        }
        this$0.forceRender();
        return Unit.INSTANCE;
    }

    private final void nudgeAdvanced(final GalaxyApi.Param param, int delta, TextView tv) {
        String gxHomeFmt;
        if (paramLocked(param.getKey())) {
            return;
        }
        Triple<Double, Double, Double> paramBounds = paramBounds(param);
        double min = paramBounds.component1().doubleValue();
        double max = paramBounds.component2().doubleValue();
        double step = paramBounds.component3().doubleValue();
        Double d = this.advancedPendingValue.get(param.getKey());
        double base = d != null ? d.doubleValue() : GxHomeKt.gxHomeNum(this.vals.get(param.getKey()));
        double next = RangesKt.coerceIn((delta * step) + base, min, max);
        this.advancedPendingValue.put(param.getKey(), Double.valueOf(next));
        gxHomeFmt = GxHomeKt.gxHomeFmt(next);
        tv.setText(gxHomeFmt + "%");
        Runnable it = this.advancedTimers.remove(param.getKey());
        if (it != null) {
            getMain().removeCallbacks(it);
        }
        Runnable r = new Runnable() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda38
            @Override // java.lang.Runnable
            public final void run() {
                GxPersonalitiesPage.nudgeAdvanced$lambda$53(GxPersonalitiesPage.this, param);
            }
        };
        this.advancedTimers.put(param.getKey(), r);
        getMain().postDelayed(r, 900L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void nudgeAdvanced$lambda$53(final GxPersonalitiesPage this$0, GalaxyApi.Param $param) {
        String gxHomeFmt;
        this$0.advancedTimers.remove($param.getKey());
        Double remove = this$0.advancedPendingValue.remove($param.getKey());
        if (remove != null) {
            double v = remove.doubleValue();
            gxHomeFmt = GxHomeKt.gxHomeFmt(v);
            this$0.setAdvanced($param, gxHomeFmt, new Function1() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda28
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit nudgeAdvanced$lambda$53$lambda$52;
                    nudgeAdvanced$lambda$53$lambda$52 = GxPersonalitiesPage.nudgeAdvanced$lambda$53$lambda$52(GxPersonalitiesPage.this, ((Boolean) obj).booleanValue());
                    return nudgeAdvanced$lambda$53$lambda$52;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit nudgeAdvanced$lambda$53$lambda$52(GxPersonalitiesPage this$0, boolean it) {
        this$0.forceRender();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void forceRender() {
        this.lastSig = "";
        render();
    }

    private final String signature() {
        boolean z = false;
        TreeSet keys = SetsKt.sortedSetOf("CustomPersonalities", "IsMetric", "VehicleParked", "IsOnroad", "IsOffroad");
        keys.addAll(this.profileKeys.values());
        Iterator<GalaxyApi.Param> it = this.meta.values().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            GalaxyApi.Param p = it.next();
            if (StringsKt.contains$default((CharSequence) p.getKey(), (CharSequence) "Jerk", false, 2, (Object) null) && CollectionsKt.contains(this.profileKeys.values(), p.getParentKey())) {
                keys.add(p.getKey());
                for (String n : CollectionsKt.listOf((Object[]) new String[]{"requires_capability", "disabled_when_key_true", "requires_nonempty_key"})) {
                    String it2 = p.str(n);
                    if (it2 != null) {
                        keys.add(it2);
                    }
                }
            }
        }
        JSONObject jSONObject = this.data;
        String jSONObject2 = jSONObject != null ? jSONObject.toString() : null;
        String joinToString$default = CollectionsKt.joinToString$default(keys, null, null, null, 0, null, new Function1() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda32
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                CharSequence signature$lambda$55;
                signature$lambda$55 = GxPersonalitiesPage.signature$lambda$55(GxPersonalitiesPage.this, (String) obj);
                return signature$lambda$55;
            }
        }, 31, null);
        Boolean valueOf = Boolean.valueOf(this.busy);
        Boolean valueOf2 = Boolean.valueOf(this.ready);
        String str = this.error;
        String str2 = this.notice;
        Boolean valueOf3 = Boolean.valueOf(this.curvePending);
        Boolean valueOf4 = Boolean.valueOf(this.loadPending);
        if (this.contextPending && !this.ready) {
            z = true;
        }
        return CollectionsKt.joinToString$default(CollectionsKt.listOf(jSONObject2, joinToString$default, valueOf, valueOf2, str, str2, valueOf3, valueOf4, Boolean.valueOf(z), CollectionsKt.sorted(this.advancedOpen), MapsKt.toSortedMap(this.drafts), MapsKt.toSortedMap(this.curveErrors), MapsKt.toSortedMap(this.advancedErrors), CollectionsKt.sorted(this.advancedCustom), Integer.valueOf(this.meta.size())), "|", null, null, 0, null, null, 62, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence signature$lambda$55(GxPersonalitiesPage this$0, String it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return it + "=" + this$0.vals.get(it);
    }

    private final void render() {
        if (this.drag != null || !this.advancedTimers.isEmpty()) {
            return;
        }
        String sig = signature();
        if (Intrinsics.areEqual(sig, this.lastSig)) {
            return;
        }
        this.lastSig = sig;
        LinearLayout linearLayout = this.content;
        LinearLayout linearLayout2 = null;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("content");
            linearLayout = null;
        }
        linearLayout.removeAllViews();
        this.pointViews.clear();
        GxPersonalitiesPage gxPersonalitiesPage = this;
        LinearLayout linearLayout3 = this.content;
        if (linearLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("content");
            linearLayout3 = null;
        }
        GxPage.Row head = GxPage.row$default(gxPersonalitiesPage, "Driving Personalities", "Acceleration, braking and following for each driving style.", linearLayout3, 0, 8, null);
        head.getTitleView().setTypeface(Typeface.DEFAULT_BOLD);
        Switch hs = head.m806switch(enabled(this.vals.get("CustomPersonalities")), new Function1() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda39
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit render$lambda$56;
                render$lambda$56 = GxPersonalitiesPage.render$lambda$56(GxPersonalitiesPage.this, ((Boolean) obj).booleanValue());
                return render$lambda$56;
            }
        });
        hs.setEnabled(!paramLocked("CustomPersonalities"));
        if (this.error.length() > 0) {
            String str = this.error;
            LinearLayout linearLayout4 = this.content;
            if (linearLayout4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("content");
                linearLayout4 = null;
            }
            note(str, linearLayout4).setTextColor(CarUi.Companion.getWARN());
        }
        if ((this.error.length() > 0) && !this.ready) {
            GxPersonalitiesPage gxPersonalitiesPage2 = this;
            Pair[] pairArr = {TuplesKt.to("Retry loading", new Function0() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda40
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit render$lambda$57;
                    render$lambda$57 = GxPersonalitiesPage.render$lambda$57(GxPersonalitiesPage.this);
                    return render$lambda$57;
                }
            })};
            LinearLayout linearLayout5 = this.content;
            if (linearLayout5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("content");
                linearLayout5 = null;
            }
            LinearLayout bar = GxPage.buttons$default(gxPersonalitiesPage2, pairArr, linearLayout5, false, 4, null);
            if (this.busy || this.loadPending || this.contextPending) {
                bar.setAlpha(0.45f);
            }
        }
        if (this.notice.length() > 0) {
            String str2 = this.notice;
            LinearLayout linearLayout6 = this.content;
            if (linearLayout6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("content");
                linearLayout6 = null;
            }
            note(str2, linearLayout6).setTextColor(CarUi.Companion.getACCENT());
        }
        if (this.busy) {
            LinearLayout linearLayout7 = this.content;
            if (linearLayout7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("content");
                linearLayout7 = null;
            }
            note("Saving…", linearLayout7).setTextColor(CarUi.Companion.getACCENT());
        }
        JSONObject d = this.data;
        if (d == null) {
            if (this.error.length() == 0) {
                LinearLayout linearLayout8 = this.content;
                if (linearLayout8 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("content");
                } else {
                    linearLayout2 = linearLayout8;
                }
                linearLayout2.addView(empty("Loading profiles…"));
                return;
            }
            return;
        }
        if (!getRoadStateKnown()) {
            LinearLayout linearLayout9 = this.content;
            if (linearLayout9 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("content");
                linearLayout9 = null;
            }
            note("Driving state is not confirmed. Editing is temporarily disabled.", linearLayout9);
        } else if (isOnroad()) {
            LinearLayout linearLayout10 = this.content;
            if (linearLayout10 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("content");
                linearLayout10 = null;
            }
            note("Changes to the active profile can take effect immediately and alter acceleration, braking, or following behavior. Make adjustments only when it is safe, and stay ready to take control.", linearLayout10).setTextColor(CarUi.Companion.getWARN());
        }
        if (getMigrationRequired()) {
            GxPersonalitiesPage gxPersonalitiesPage3 = this;
            LinearLayout linearLayout11 = this.content;
            if (linearLayout11 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("content");
                linearLayout11 = null;
            }
            GxPage.Row r = GxPage.row$default(gxPersonalitiesPage3, "Stored profiles need migration before editing.", null, linearLayout11, 0, 8, null);
            r.getTitleView().setTextColor(CarUi.Companion.getWARN());
            TextView b = r.button("Migrate profiles", true, new Function0() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda41
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit render$lambda$58;
                    render$lambda$58 = GxPersonalitiesPage.render$lambda$58(GxPersonalitiesPage.this);
                    return render$lambda$58;
                }
            });
            if (getLocked() || isOnroad()) {
                b.setAlpha(0.45f);
            }
        }
        if (!enabled(this.vals.get("CustomPersonalities"))) {
            LinearLayout linearLayout12 = this.content;
            if (linearLayout12 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("content");
            } else {
                linearLayout2 = linearLayout12;
            }
            note("Enable to configure profiles. Existing defaults remain active while off.", linearLayout2);
        }
        for (String p : this.profilesIds) {
            renderProfile(p);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit render$lambda$56(GxPersonalitiesPage this$0, boolean v) {
        this$0.toggle("CustomPersonalities", v);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit render$lambda$57(GxPersonalitiesPage this$0) {
        if (!this$0.busy && !this$0.loadPending && !this$0.contextPending) {
            this$0.load();
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit render$lambda$58(GxPersonalitiesPage this$0) {
        if (!this$0.getLocked() && !this$0.isOnroad()) {
            this$0.migrate();
        }
        return Unit.INSTANCE;
    }

    private final void renderProfile(final String p) {
        String str;
        float f;
        String gxHomeFmt;
        String gxHomeFmt2;
        String gxHomeFmt3;
        double gxHomeNum;
        String gxHomeFmt4;
        boolean z;
        int i;
        List dr;
        double bmax;
        Object obj;
        boolean open;
        LinearLayout top;
        String gxHomeFmt5;
        String gxHomeFmt6;
        final String pk = key(p);
        String label = Intrinsics.areEqual(p, "traffic") ? "Traffic Mode" : label(p);
        LinearLayout linearLayout = this.content;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("content");
            linearLayout = null;
        }
        header(label, linearLayout);
        LinearLayout linearLayout2 = this.content;
        if (linearLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("content");
            linearLayout2 = null;
        }
        LinearLayout c = card(linearLayout2);
        LinearLayout $this$renderProfile_u24lambda_u2459 = new LinearLayout(getAct());
        int i2 = 0;
        $this$renderProfile_u24lambda_u2459.setOrientation(0);
        $this$renderProfile_u24lambda_u2459.setGravity(16);
        LinearLayout top2 = $this$renderProfile_u24lambda_u2459;
        TextView $this$renderProfile_u24lambda_u2460 = new TextView(getAct());
        $this$renderProfile_u24lambda_u2460.setText(Intrinsics.areEqual(p, "traffic") ? "Traffic Mode" : label(p));
        $this$renderProfile_u24lambda_u2460.setTextSize(22.0f);
        $this$renderProfile_u24lambda_u2460.setTextColor(CarUi.Companion.getTEXT());
        $this$renderProfile_u24lambda_u2460.setTypeface(Typeface.DEFAULT_BOLD);
        top2.addView($this$renderProfile_u24lambda_u2460, new LinearLayout.LayoutParams(0, -2, 1.0f));
        Switch sw = getUi().bigSwitch();
        sw.setChecked(enabled(this.vals.get(pk)));
        sw.setEnabled(!paramLocked(pk));
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda0
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                GxPersonalitiesPage.renderProfile$lambda$61(GxPersonalitiesPage.this, pk, compoundButton, z2);
            }
        });
        top2.addView(sw);
        c.addView(top2);
        if (!enabled(this.vals.get(pk))) {
            GxPage.text$default(this, "Turn on to configure this profile.", c, 0.0f, 0, false, 28, null);
            return;
        }
        for (Pair<String, String> pair : this.categories) {
            final String cat = pair.component1();
            Switch sw2 = sw;
            text(pair.component2(), c, 18.0f, CarUi.Companion.getTEXT(), true).setPadding(i2, getUi().dp(12), i2, getUi().dp(4));
            final List opts = options(cat, p);
            CarUi ui = getUi();
            List $this$map$iv = opts;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            for (Object item$iv$iv : $this$map$iv) {
                destination$iv$iv.add(label((String) item$iv$iv));
            }
            Pair<LinearLayout, Function1<Integer, Unit>> chips = ui.chips((List) destination$iv$iv, new Function1() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda11
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    Unit renderProfile$lambda$63;
                    renderProfile$lambda$63 = GxPersonalitiesPage.renderProfile$lambda$63(GxPersonalitiesPage.this, p, cat, opts, ((Integer) obj2).intValue());
                    return renderProfile$lambda$63;
                }
            });
            LinearLayout bar = chips.component1();
            Function1 paint = chips.component2();
            paint.invoke(Integer.valueOf(opts.indexOf(preset(p, cat))));
            if (getEditingLocked()) {
                bar.setAlpha(0.5f);
            }
            c.addView(bar);
            if (Intrinsics.areEqual(preset(p, cat), "dom_default")) {
                GxPage.text$default(this, "Using existing Dom default.", c, 0.0f, 0, false, 28, null);
            }
            if (Intrinsics.areEqual(cat, "following") && StringsKt.startsWith$default(preset(p, cat), "legacy_", false, 2, (Object) null)) {
                GxPage.text$default(this, "Previous fixed following distance retained. Select a preset to use its current curve.", c, 0.0f, 0, false, 28, null);
            }
            sw = sw2;
            i2 = 0;
        }
        Object obj2 = "following";
        final boolean open2 = this.advancedOpen.contains(p);
        TextView adv = CarUi.button$default(getUi(), open2 ? "Advanced  ▲" : "Advanced  ▼", false, new Function0() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda22
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit renderProfile$lambda$64;
                renderProfile$lambda$64 = GxPersonalitiesPage.renderProfile$lambda$64(open2, this, p);
                return renderProfile$lambda$64;
            }
        }, 2, null);
        LinearLayout.LayoutParams $this$renderProfile_u24lambda_u2465 = new LinearLayout.LayoutParams(-2, -2);
        $this$renderProfile_u24lambda_u2465.topMargin = getUi().dp(14);
        Unit unit = Unit.INSTANCE;
        c.addView(adv, $this$renderProfile_u24lambda_u2465);
        if (open2) {
            Iterator<Pair<String, String>> it = this.categories.iterator();
            while (true) {
                str = "custom";
                if (!it.hasNext()) {
                    break;
                }
                Pair<String, String> next = it.next();
                final String cat2 = next.component1();
                final String title = next.component2();
                if (Intrinsics.areEqual(preset(p, cat2), "custom")) {
                    String k = p + cat2;
                    Locale ROOT = Locale.ROOT;
                    Intrinsics.checkNotNullExpressionValue(ROOT, "ROOT");
                    String lowerCase = title.toLowerCase(ROOT);
                    Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                    String pk2 = pk;
                    text("Custom " + lowerCase + " graph", c, 18.0f, CarUi.Companion.getTEXT(), true).setPadding(0, getUi().dp(16), 0, 0);
                    GxPage.text$default(this, (Intrinsics.areEqual(cat2, obj2) ? "Seconds" : "m/s²") + " · " + speedUnit() + ". Dashed: Dom default. Drag a point up or down, or tap a value below to type it.", c, 0.0f, 0, false, 28, null);
                    Graph graph = new Graph(this, p, cat2);
                    LinearLayout.LayoutParams $this$renderProfile_u24lambda_u2466 = new LinearLayout.LayoutParams(-1, getUi().dp(300));
                    $this$renderProfile_u24lambda_u2466.topMargin = getUi().dp(6);
                    $this$renderProfile_u24lambda_u2466.bottomMargin = getUi().dp(6);
                    Unit unit2 = Unit.INSTANCE;
                    c.addView(graph, $this$renderProfile_u24lambda_u2466);
                    double bmax2 = bounds(cat2).getSecond().doubleValue();
                    List dr2 = draft(p, cat2);
                    List $this$any$iv = dr2;
                    if (!($this$any$iv instanceof Collection) || !$this$any$iv.isEmpty()) {
                        Iterator it2 = $this$any$iv.iterator();
                        while (true) {
                            if (!it2.hasNext()) {
                                z = false;
                                break;
                            }
                            Object element$iv = it2.next();
                            if (((Number) element$iv).doubleValue() > bmax2) {
                                z = true;
                                break;
                            }
                        }
                    } else {
                        z = false;
                    }
                    if (z) {
                        gxHomeFmt6 = GxHomeKt.gxHomeFmt(bmax2);
                        GxPage.text$default(this, "Saved values above " + gxHomeFmt6 + " are shown at their original scale. Only edited points use the current authoring bounds.", c, 0.0f, 0, false, 28, null);
                    }
                    String it3 = this.curveErrors.get(k);
                    if (it3 != null) {
                        GxPage.text$default(this, it3, c, 0.0f, CarUi.Companion.getWARN(), false, 20, null);
                    }
                    List sp = speeds(cat2);
                    ArrayList views = new ArrayList();
                    LinearLayout linearLayout3 = null;
                    Iterator<T> it4 = dr2.iterator();
                    int i3 = 0;
                    while (it4.hasNext()) {
                        final int i4 = i3;
                        i3++;
                        double v = ((Number) it4.next()).doubleValue();
                        if (i4 % 5 == 0) {
                            bmax = bmax2;
                            LinearLayout $this$renderProfile_u24lambda_u2469 = new LinearLayout(getAct());
                            obj = obj2;
                            $this$renderProfile_u24lambda_u2469.setOrientation(0);
                            open = open2;
                            top = top2;
                            $this$renderProfile_u24lambda_u2469.setPadding(0, getUi().dp(4), 0, getUi().dp(4));
                            c.addView($this$renderProfile_u24lambda_u2469);
                            linearLayout3 = $this$renderProfile_u24lambda_u2469;
                        } else {
                            bmax = bmax2;
                            obj = obj2;
                            open = open2;
                            top = top2;
                        }
                        CarUi ui2 = getUi();
                        String speed = speed((i4 >= 0 && i4 < sp.size() ? sp.get(i4) : Double.valueOf(0.0d)).doubleValue());
                        String speedUnit = speedUnit();
                        gxHomeFmt5 = GxHomeKt.gxHomeFmt(v);
                        TextView b = CarUi.button$default(ui2, speed + " " + speedUnit + "\n" + gxHomeFmt5, false, new Function0() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda33
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                Unit renderProfile$lambda$71;
                                renderProfile$lambda$71 = GxPersonalitiesPage.renderProfile$lambda$71(GxPersonalitiesPage.this, p, cat2, i4);
                                return renderProfile$lambda$71;
                            }
                        }, 2, null);
                        b.setTextSize(16.0f);
                        b.setMinWidth(0);
                        if (getEditingLocked()) {
                            b.setAlpha(0.5f);
                        }
                        views.add(b);
                        Intrinsics.checkNotNull(linearLayout3);
                        TextView textView = b;
                        LinearLayout.LayoutParams $this$renderProfile_u24lambda_u2472 = new LinearLayout.LayoutParams(0, -2, 1.0f);
                        $this$renderProfile_u24lambda_u2472.rightMargin = getUi().dp(6);
                        Unit unit3 = Unit.INSTANCE;
                        linearLayout3.addView(textView, $this$renderProfile_u24lambda_u2472);
                        bmax2 = bmax;
                        obj2 = obj;
                        open2 = open;
                        top2 = top;
                        sp = sp;
                    }
                    Object obj3 = obj2;
                    boolean open3 = open2;
                    LinearLayout top3 = top2;
                    if (dr2.size() % 5 != 0) {
                        int size = 5 - (dr2.size() % 5);
                        int i5 = 0;
                        while (i5 < size) {
                            if (linearLayout3 != null) {
                                View view = new View(getAct());
                                i = size;
                                dr = dr2;
                                LinearLayout.LayoutParams $this$renderProfile_u24lambda_u2474_u24lambda_u2473 = new LinearLayout.LayoutParams(0, 1, 1.0f);
                                $this$renderProfile_u24lambda_u2474_u24lambda_u2473.rightMargin = getUi().dp(6);
                                Unit unit4 = Unit.INSTANCE;
                                linearLayout3.addView(view, $this$renderProfile_u24lambda_u2474_u24lambda_u2473);
                                Unit unit5 = Unit.INSTANCE;
                            } else {
                                i = size;
                                dr = dr2;
                            }
                            i5++;
                            size = i;
                            dr2 = dr;
                        }
                    }
                    this.pointViews.put(k, views);
                    LinearLayout rb = GxPage.buttons$default(this, new Pair[]{TuplesKt.to("Reset to default", new Function0() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda44
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            Unit renderProfile$lambda$76;
                            renderProfile$lambda$76 = GxPersonalitiesPage.renderProfile$lambda$76(GxPersonalitiesPage.this, p, title, cat2);
                            return renderProfile$lambda$76;
                        }
                    })}, c, false, 4, null);
                    if (getEditingLocked()) {
                        rb.setAlpha(0.5f);
                        pk = pk2;
                        obj2 = obj3;
                        open2 = open3;
                        top2 = top3;
                    } else {
                        pk = pk2;
                        obj2 = obj3;
                        open2 = open3;
                        top2 = top3;
                    }
                } else {
                    pk = pk;
                }
            }
            float f2 = 0.5f;
            GxPage.text$default(this, "Custom values are untested and may not be supported by the developer.", c, 0.0f, CarUi.Companion.getWARN(), false, 20, null).setPadding(0, getUi().dp(14), 0, getUi().dp(4));
            for (final GalaxyApi.Param param : advancedParams(p)) {
                boolean lockedP = paramLocked(param.getKey());
                String str2 = str;
                text(param.getLabel(), c, 18.0f, CarUi.Companion.getTEXT(), true).setPadding(0, getUi().dp(12), 0, 0);
                String it5 = param.getDescription();
                if (it5 != null) {
                    GxPage.text$default(this, it5, c, 0.0f, 0, false, 28, null);
                }
                String mode = advancedMode(param.getKey());
                if (Intrinsics.areEqual(mode, str2)) {
                    Triple<Double, Double, Double> paramBounds = paramBounds(param);
                    final double min = paramBounds.component1().doubleValue();
                    final double max = paramBounds.component2().doubleValue();
                    double step = paramBounds.component3().doubleValue();
                    LinearLayout $this$renderProfile_u24lambda_u2478 = new LinearLayout(getAct());
                    $this$renderProfile_u24lambda_u2478.setOrientation(0);
                    $this$renderProfile_u24lambda_u2478.setGravity(16);
                    $this$renderProfile_u24lambda_u2478.setPadding(0, getUi().dp(6), 0, getUi().dp(6));
                    TextView $this$renderProfile_u24lambda_u2479 = new TextView(getAct());
                    gxHomeFmt = GxHomeKt.gxHomeFmt(min);
                    gxHomeFmt2 = GxHomeKt.gxHomeFmt(max);
                    gxHomeFmt3 = GxHomeKt.gxHomeFmt(step);
                    $this$renderProfile_u24lambda_u2479.setText(gxHomeFmt + "–" + gxHomeFmt2 + "% · step " + gxHomeFmt3);
                    $this$renderProfile_u24lambda_u2479.setTextSize(15.0f);
                    $this$renderProfile_u24lambda_u2479.setTextColor(CarUi.Companion.getDIM());
                    $this$renderProfile_u24lambda_u2478.addView($this$renderProfile_u24lambda_u2479, new LinearLayout.LayoutParams(0, -2, 1.0f));
                    final TextView $this$renderProfile_u24lambda_u2480 = new TextView(getAct());
                    gxHomeNum = GxHomeKt.gxHomeNum(this.vals.get(param.getKey()));
                    gxHomeFmt4 = GxHomeKt.gxHomeFmt(gxHomeNum);
                    $this$renderProfile_u24lambda_u2480.setText(gxHomeFmt4 + "%");
                    $this$renderProfile_u24lambda_u2480.setTextSize(22.0f);
                    $this$renderProfile_u24lambda_u2480.setTextColor(CarUi.Companion.getTEXT());
                    $this$renderProfile_u24lambda_u2480.setTypeface(Typeface.DEFAULT_BOLD);
                    $this$renderProfile_u24lambda_u2480.setMinWidth(getUi().dp(96));
                    $this$renderProfile_u24lambda_u2480.setGravity(17);
                    $this$renderProfile_u24lambda_u2478.addView(getUi().stepper("−", new Function0() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda46
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            Unit renderProfile$lambda$81;
                            renderProfile$lambda$81 = GxPersonalitiesPage.renderProfile$lambda$81(GxPersonalitiesPage.this, param, $this$renderProfile_u24lambda_u2480);
                            return renderProfile$lambda$81;
                        }
                    }));
                    $this$renderProfile_u24lambda_u2478.addView($this$renderProfile_u24lambda_u2480);
                    $this$renderProfile_u24lambda_u2478.addView(getUi().stepper("+", new Function0() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda47
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            Unit renderProfile$lambda$82;
                            renderProfile$lambda$82 = GxPersonalitiesPage.renderProfile$lambda$82(GxPersonalitiesPage.this, param, $this$renderProfile_u24lambda_u2480);
                            return renderProfile$lambda$82;
                        }
                    }));
                    $this$renderProfile_u24lambda_u2478.addView(new View(getAct()), new LinearLayout.LayoutParams(getUi().dp(10), 1));
                    f = 0.5f;
                    $this$renderProfile_u24lambda_u2478.addView(CarUi.button$default(getUi(), "Edit", false, new Function0() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda48
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            Unit renderProfile$lambda$85;
                            renderProfile$lambda$85 = GxPersonalitiesPage.renderProfile$lambda$85(GxPersonalitiesPage.this, param, min, max);
                            return renderProfile$lambda$85;
                        }
                    }, 2, null));
                    if (lockedP) {
                        $this$renderProfile_u24lambda_u2478.setAlpha(0.5f);
                    }
                    c.addView($this$renderProfile_u24lambda_u2478);
                } else {
                    f = f2;
                }
                String it6 = this.advancedErrors.get(param.getKey());
                if (it6 != null) {
                    GxPage.text$default(this, it6, c, 0.0f, CarUi.Companion.getWARN(), false, 20, null);
                }
                final List modes = StringsKt.endsWith$default(param.getKey(), "JerkDanger", false, 2, (Object) null) ? CollectionsKt.listOf((Object[]) new String[]{"standard", str2}) : CollectionsKt.listOf((Object[]) new String[]{"chill", "standard", str2});
                CarUi ui3 = getUi();
                Iterable $this$map$iv2 = modes;
                Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv2, 10));
                for (Object item$iv$iv2 : $this$map$iv2) {
                    destination$iv$iv2.add(label((String) item$iv$iv2));
                }
                Pair<LinearLayout, Function1<Integer, Unit>> chips2 = ui3.chips((List) destination$iv$iv2, new Function1() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda49
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj4) {
                        Unit renderProfile$lambda$88;
                        renderProfile$lambda$88 = GxPersonalitiesPage.renderProfile$lambda$88(GxPersonalitiesPage.this, param, modes, ((Integer) obj4).intValue());
                        return renderProfile$lambda$88;
                    }
                });
                LinearLayout bar2 = chips2.component1();
                Function1 paint2 = chips2.component2();
                paint2.invoke(Integer.valueOf(modes.indexOf(mode)));
                if (lockedP) {
                    bar2.setAlpha(f);
                }
                bar2.setPadding(0, getUi().dp(6), 0, getUi().dp(4));
                c.addView(bar2);
                f2 = f;
                str = str2;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void renderProfile$lambda$61(GxPersonalitiesPage this$0, String $pk, CompoundButton b, boolean v) {
        if (b.isPressed()) {
            this$0.toggle($pk, v);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderProfile$lambda$63(GxPersonalitiesPage this$0, String $p, String $cat, List $opts, int i) {
        if (!this$0.getEditingLocked()) {
            this$0.choosePreset($p, $cat, (String) $opts.get(i));
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderProfile$lambda$64(boolean $open, GxPersonalitiesPage this$0, String $p) {
        HashSet<String> hashSet = this$0.advancedOpen;
        if ($open) {
            hashSet.remove($p);
        } else {
            hashSet.add($p);
        }
        this$0.forceRender();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderProfile$lambda$71(GxPersonalitiesPage this$0, String $p, String $cat, int $i) {
        this$0.editPoint($p, $cat, $i);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderProfile$lambda$76(final GxPersonalitiesPage this$0, final String $p, String $title, final String $cat) {
        if (!this$0.getEditingLocked()) {
            String label = this$0.label($p);
            Locale ROOT = Locale.ROOT;
            Intrinsics.checkNotNullExpressionValue(ROOT, "ROOT");
            String lowerCase = $title.toLowerCase(ROOT);
            Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
            this$0.confirm("Reset to default?", label + " " + lowerCase + " goes back to the Dom default curve.", "Reset", new Function0() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda6
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit renderProfile$lambda$76$lambda$75;
                    renderProfile$lambda$76$lambda$75 = GxPersonalitiesPage.renderProfile$lambda$76$lambda$75(GxPersonalitiesPage.this, $p, $cat);
                    return renderProfile$lambda$76$lambda$75;
                }
            });
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderProfile$lambda$76$lambda$75(GxPersonalitiesPage this$0, String $p, String $cat) {
        this$0.saveCurve($p, $cat, true);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderProfile$lambda$81(GxPersonalitiesPage this$0, GalaxyApi.Param $param, TextView $tv) {
        this$0.nudgeAdvanced($param, -5, $tv);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderProfile$lambda$82(GxPersonalitiesPage this$0, GalaxyApi.Param $param, TextView $tv) {
        this$0.nudgeAdvanced($param, 5, $tv);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderProfile$lambda$85(final GxPersonalitiesPage this$0, final GalaxyApi.Param $param, double $min, double $max) {
        String gxHomeFmt;
        String gxHomeFmt2;
        double gxHomeNum;
        String gxHomeFmt3;
        if (!this$0.paramLocked($param.getKey())) {
            String label = $param.getLabel();
            gxHomeFmt = GxHomeKt.gxHomeFmt($min);
            gxHomeFmt2 = GxHomeKt.gxHomeFmt($max);
            gxHomeNum = GxHomeKt.gxHomeNum(this$0.vals.get($param.getKey()));
            gxHomeFmt3 = GxHomeKt.gxHomeFmt(gxHomeNum);
            GxPage.prompt$default(this$0, label, gxHomeFmt + "–" + gxHomeFmt2, gxHomeFmt3, 8194, null, new Function1() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda8
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit renderProfile$lambda$85$lambda$84;
                    renderProfile$lambda$85$lambda$84 = GxPersonalitiesPage.renderProfile$lambda$85$lambda$84(GxPersonalitiesPage.this, $param, (String) obj);
                    return renderProfile$lambda$85$lambda$84;
                }
            }, 16, null);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderProfile$lambda$85$lambda$84(final GxPersonalitiesPage this$0, GalaxyApi.Param $param, String raw) {
        Intrinsics.checkNotNullParameter(raw, "raw");
        this$0.setAdvanced($param, raw, new Function1() { // from class: com.starpilot.starview.GxPersonalitiesPage$$ExternalSyntheticLambda15
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit renderProfile$lambda$85$lambda$84$lambda$83;
                renderProfile$lambda$85$lambda$84$lambda$83 = GxPersonalitiesPage.renderProfile$lambda$85$lambda$84$lambda$83(GxPersonalitiesPage.this, ((Boolean) obj).booleanValue());
                return renderProfile$lambda$85$lambda$84$lambda$83;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderProfile$lambda$85$lambda$84$lambda$83(GxPersonalitiesPage this$0, boolean it) {
        this$0.forceRender();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit renderProfile$lambda$88(GxPersonalitiesPage this$0, GalaxyApi.Param $param, List $modes, int i) {
        this$0.advancedPreset($param, (String) $modes.get(i));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final double graphMax(String p, String c) {
        Drag it = this.drag;
        if (it != null && Intrinsics.areEqual(it.getProfile(), p) && Intrinsics.areEqual(it.getCategory(), c)) {
            return it.getMax();
        }
        Double maxOrNull = CollectionsKt.maxOrNull((Iterable<? extends Double>) CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) CollectionsKt.listOf(bounds(c).getSecond()), (Iterable) draft(p, c)), (Iterable) reference(p, c)));
        if (maxOrNull != null) {
            return maxOrNull.doubleValue();
        }
        return 1.0d;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final double graphMin(String p, String c) {
        Drag it = this.drag;
        if (it != null && Intrinsics.areEqual(it.getProfile(), p) && Intrinsics.areEqual(it.getCategory(), c)) {
            return it.getMin();
        }
        Double minOrNull = CollectionsKt.minOrNull((Iterable<? extends Double>) CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) CollectionsKt.listOf(bounds(c).getFirst()), (Iterable) draft(p, c)), (Iterable) reference(p, c)));
        if (minOrNull != null) {
            return minOrNull.doubleValue();
        }
        return 0.0d;
    }

    /* compiled from: GxHome.kt */
    private final class Graph extends View {
        private final String category;
        private final Paint dot;
        private final Paint dotRing;
        private final Paint grid;
        private final String k;
        private final Paint lineP;
        private float ox;
        private float oy;
        private final String profile;
        private final Paint refP;
        private float s;
        final /* synthetic */ GxPersonalitiesPage this$0;
        private final Paint txt;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Graph(GxPersonalitiesPage this$0, String profile, String category) {
            super(this$0.getAct());
            Intrinsics.checkNotNullParameter(profile, "profile");
            Intrinsics.checkNotNullParameter(category, "category");
            this.this$0 = this$0;
            this.profile = profile;
            this.category = category;
            this.k = this.profile + this.category;
            Paint $this$grid_u24lambda_u240 = new Paint(1);
            GxPersonalitiesPage gxPersonalitiesPage = this.this$0;
            $this$grid_u24lambda_u240.setColor(Color.argb(60, 255, 255, 255));
            $this$grid_u24lambda_u240.setStrokeWidth(gxPersonalitiesPage.getUi().dp(1));
            this.grid = $this$grid_u24lambda_u240;
            Paint $this$txt_u24lambda_u241 = new Paint(1);
            $this$txt_u24lambda_u241.setColor(CarUi.Companion.getDIM());
            this.txt = $this$txt_u24lambda_u241;
            Paint $this$refP_u24lambda_u242 = new Paint(1);
            GxPersonalitiesPage gxPersonalitiesPage2 = this.this$0;
            $this$refP_u24lambda_u242.setColor(Color.argb(140, 255, 255, 255));
            $this$refP_u24lambda_u242.setStyle(Paint.Style.STROKE);
            $this$refP_u24lambda_u242.setStrokeWidth(gxPersonalitiesPage2.getUi().dp(2));
            $this$refP_u24lambda_u242.setPathEffect(new DashPathEffect(new float[]{gxPersonalitiesPage2.getUi().dp(6), gxPersonalitiesPage2.getUi().dp(6)}, 0.0f));
            this.refP = $this$refP_u24lambda_u242;
            Paint $this$lineP_u24lambda_u243 = new Paint(1);
            GxPersonalitiesPage gxPersonalitiesPage3 = this.this$0;
            $this$lineP_u24lambda_u243.setColor(CarUi.Companion.getACCENT());
            $this$lineP_u24lambda_u243.setStyle(Paint.Style.STROKE);
            $this$lineP_u24lambda_u243.setStrokeWidth(gxPersonalitiesPage3.getUi().dp(3));
            this.lineP = $this$lineP_u24lambda_u243;
            Paint $this$dot_u24lambda_u244 = new Paint(1);
            $this$dot_u24lambda_u244.setColor(CarUi.Companion.getACCENT());
            this.dot = $this$dot_u24lambda_u244;
            Paint $this$dotRing_u24lambda_u245 = new Paint(1);
            GxPersonalitiesPage gxPersonalitiesPage4 = this.this$0;
            $this$dotRing_u24lambda_u245.setColor(-1);
            $this$dotRing_u24lambda_u245.setStyle(Paint.Style.STROKE);
            $this$dotRing_u24lambda_u245.setStrokeWidth(gxPersonalitiesPage4.getUi().dp(1));
            this.dotRing = $this$dotRing_u24lambda_u245;
            this.s = 1.0f;
            setBackground(CarUi.rounded$default(this.this$0.getUi(), Color.rgb(16, 18, 24), 12, null, 4, null));
        }

        public final String getCategory() {
            return this.category;
        }

        public final String getProfile() {
            return this.profile;
        }

        private final float X(double x) {
            return this.ox + ((float) ((30 + x) * this.s));
        }

        private final float Y(double y) {
            return this.oy + ((float) ((12 + y) * this.s));
        }

        private final List<Pair<Double, Double>> points(List<Double> curve) {
            List sp = this.this$0.speeds(this.category);
            double max = this.this$0.graphMax(this.profile, this.category);
            double min = this.this$0.graphMin(this.profile, this.category);
            Double d = (Double) CollectionsKt.lastOrNull(sp);
            double last = d != null ? d.doubleValue() : 1.0d;
            double span = ((max - min) > 0.0d ? 1 : ((max - min) == 0.0d ? 0 : -1)) == 0 ? 1.0d : max - min;
            List<Double> $this$mapIndexed$iv = curve;
            int i = 10;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$mapIndexed$iv, 10));
            int index$iv$iv = 0;
            for (Object item$iv$iv : $this$mapIndexed$iv) {
                int index$iv$iv2 = index$iv$iv + 1;
                if (index$iv$iv < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                double v = ((Number) item$iv$iv).doubleValue();
                double max2 = max;
                double max3 = i;
                int i2 = index$iv$iv;
                destination$iv$iv.add(TuplesKt.to(Double.valueOf(max3 + ((((Number) (i2 >= 0 && i2 < sp.size() ? sp.get(i2) : Double.valueOf(0.0d))).doubleValue() / last) * 280)), Double.valueOf(90 - (((v - min) / span) * 80))));
                index$iv$iv = index$iv$iv2;
                max = max2;
                sp = sp;
                min = min;
                i = 10;
            }
            return (List) destination$iv$iv;
        }

        @Override // android.view.View
        protected void onDraw(Canvas cv) {
            String gxHomeFmt;
            Intrinsics.checkNotNullParameter(cv, "cv");
            if (this.this$0.data == null) {
                return;
            }
            this.s = Math.min(getWidth() / 340.0f, getHeight() / 140.0f);
            this.ox = (getWidth() - (340 * this.s)) / 2.0f;
            this.oy = (getHeight() - (140 * this.s)) / 2.0f;
            this.txt.setTextSize(10 * this.s);
            double min = this.this$0.graphMin(this.profile, this.category);
            double max = this.this$0.graphMax(this.profile, this.category);
            this.txt.setTextAlign(Paint.Align.LEFT);
            double d = 10.0d;
            cv.drawText(Intrinsics.areEqual(this.category, "following") ? "Seconds" : "m/s²", X(10.0d), Y(-3.0d), this.txt);
            int t = 0;
            while (t < 5) {
                cv.drawLine(X(d), Y(90.0d - (t * 20)), X(290.0d), Y(90.0d - (t * 20)), this.grid);
                this.txt.setTextAlign(Paint.Align.RIGHT);
                StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                double d2 = 4;
                String format = String.format(Locale.US, "%.2f", Arrays.copyOf(new Object[]{Double.valueOf((((max - min) * t) / d2) + min)}, 1));
                Intrinsics.checkNotNullExpressionValue(format, "format(...)");
                gxHomeFmt = GxHomeKt.gxHomeFmt(Double.parseDouble(format));
                cv.drawText(gxHomeFmt, X(5.0d), Y(93.0d - (t * 20)), this.txt);
                double min2 = min;
                double min3 = t * 70;
                cv.drawLine(X((t * 70) + 10.0d), Y(10.0d), X(min3 + 10.0d), Y(94.0d), this.grid);
                this.txt.setTextAlign(Paint.Align.CENTER);
                GxPersonalitiesPage gxPersonalitiesPage = this.this$0;
                Double d3 = (Double) CollectionsKt.lastOrNull(this.this$0.speeds(this.category));
                cv.drawText(gxPersonalitiesPage.speed(((d3 != null ? d3.doubleValue() : 0.0d) * t) / d2), X((t * 70) + 10.0d), Y(105.0d), this.txt);
                t++;
                d = 10.0d;
                min = min2;
            }
            cv.drawText("Speed (" + this.this$0.speedUnit() + ")", X(150.0d), Y(121.0d), this.txt);
            onDraw$poly(cv, this, points(this.this$0.reference(this.profile, this.category)), this.refP);
            List pts = points(this.this$0.draft(this.profile, this.category));
            onDraw$poly(cv, this, pts, this.lineP);
            for (Pair<Double, Double> pair : pts) {
                double x = pair.component1().doubleValue();
                double y = pair.component2().doubleValue();
                cv.drawCircle(X(x), Y(y), this.s * 4.5f, this.dot);
                cv.drawCircle(X(x), Y(y), this.s * 4.5f, this.dotRing);
            }
        }

        private static final void onDraw$poly(Canvas $cv, Graph this$0, List<Pair<Double, Double>> list, Paint paint) {
            if (list.isEmpty()) {
                return;
            }
            Path path = new Path();
            List<Pair<Double, Double>> $this$forEachIndexed$iv = list;
            int i = 0;
            for (Object item$iv : $this$forEachIndexed$iv) {
                int index$iv = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                Pair pair = (Pair) item$iv;
                double x = ((Number) pair.component1()).doubleValue();
                double y = ((Number) pair.component2()).doubleValue();
                float X = this$0.X(x);
                float Y = this$0.Y(y);
                if (i == 0) {
                    path.moveTo(X, Y);
                } else {
                    path.lineTo(X, Y);
                }
                i = index$iv;
            }
            $cv.drawPath(path, paint);
        }

        private final void move(MotionEvent e) {
            TextView tv;
            String gxHomeFmt;
            Drag d = this.this$0.drag;
            if (d == null) {
                return;
            }
            float vy = ((e.getY() - this.oy) / this.s) - 12;
            Pair bounds = this.this$0.bounds(this.category);
            double bmin = ((Number) bounds.component1()).doubleValue();
            double bmax = ((Number) bounds.component2()).doubleValue();
            double value = RangesKt.coerceIn(Math.round((d.getMin() + (((90 - vy) / 80) * (d.getMax() - d.getMin()))) * 20) / 20.0d, bmin, bmax);
            List curve = CollectionsKt.toMutableList((Collection) this.this$0.draft(this.profile, this.category));
            int size = curve.size();
            int index = d.getIndex();
            if (index >= 0 && index < size) {
                curve.set(d.getIndex(), Double.valueOf(value));
                this.this$0.drafts.put(this.k, curve);
                this.this$0.curveErrors.remove(this.k);
                List list = (List) this.this$0.pointViews.get(this.k);
                if (list != null && (tv = (TextView) CollectionsKt.getOrNull(list, d.getIndex())) != null) {
                    GxPersonalitiesPage gxPersonalitiesPage = this.this$0;
                    List speeds = gxPersonalitiesPage.speeds(this.category);
                    int index2 = d.getIndex();
                    String speed = gxPersonalitiesPage.speed(((Number) (index2 >= 0 && index2 < speeds.size() ? speeds.get(index2) : Double.valueOf(0.0d))).doubleValue());
                    String speedUnit = gxPersonalitiesPage.speedUnit();
                    gxHomeFmt = GxHomeKt.gxHomeFmt(value);
                    tv.setText(speed + " " + speedUnit + "\n" + gxHomeFmt);
                }
                invalidate();
            }
        }

        private final void restore() {
            Drag d = this.this$0.drag;
            if (d == null) {
                return;
            }
            if (d.getPrevious() != null) {
                this.this$0.drafts.put(this.k, d.getPrevious());
            } else {
                this.this$0.drafts.remove(this.k);
            }
            this.this$0.drag = null;
            invalidate();
            this.this$0.forceRender();
        }

        @Override // android.view.View
        public boolean onTouchEvent(MotionEvent e) {
            Object minElem$iv;
            boolean z;
            Intrinsics.checkNotNullParameter(e, "e");
            switch (e.getActionMasked()) {
                case 0:
                    if (this.this$0.getEditingLocked() || this.this$0.drag != null || this.this$0.data == null) {
                        return false;
                    }
                    float x = ((e.getX() - this.ox) / this.s) - 30;
                    List pts = points(this.this$0.draft(this.profile, this.category));
                    if (pts.isEmpty()) {
                        return false;
                    }
                    Iterable $this$minByOrNull$iv = CollectionsKt.getIndices(pts);
                    Iterator iterator$iv = $this$minByOrNull$iv.iterator();
                    if (iterator$iv.hasNext()) {
                        minElem$iv = iterator$iv.next();
                        if (iterator$iv.hasNext()) {
                            int it = ((Number) minElem$iv).intValue();
                            double minValue$iv = Math.abs(pts.get(it).getFirst().doubleValue() - x);
                            while (true) {
                                Object e$iv = iterator$iv.next();
                                int it2 = ((Number) e$iv).intValue();
                                List pts2 = pts;
                                double v$iv = Math.abs(pts.get(it2).getFirst().doubleValue() - x);
                                if (Double.compare(minValue$iv, v$iv) > 0) {
                                    minElem$iv = e$iv;
                                    minValue$iv = v$iv;
                                }
                                if (iterator$iv.hasNext()) {
                                    pts = pts2;
                                }
                            }
                        }
                    } else {
                        minElem$iv = null;
                    }
                    Integer num = (Integer) minElem$iv;
                    int idx = num != null ? num.intValue() : 0;
                    GxPersonalitiesPage gxPersonalitiesPage = this.this$0;
                    String str = this.profile;
                    String str2 = this.category;
                    double graphMin = this.this$0.graphMin(this.profile, this.category);
                    double graphMax = this.this$0.graphMax(this.profile, this.category);
                    List list = (List) this.this$0.drafts.get(this.k);
                    gxPersonalitiesPage.drag = new Drag(str, str2, idx, graphMin, graphMax, list != null ? CollectionsKt.toMutableList((Collection) list) : null);
                    ViewParent parent = getParent();
                    if (parent != null) {
                        z = true;
                        parent.requestDisallowInterceptTouchEvent(true);
                    } else {
                        z = true;
                    }
                    move(e);
                    return z;
                case 1:
                    if (this.this$0.drag == null) {
                        return false;
                    }
                    ViewParent parent2 = getParent();
                    if (parent2 != null) {
                        parent2.requestDisallowInterceptTouchEvent(false);
                    }
                    if (this.this$0.getEditingLocked()) {
                        restore();
                        return true;
                    }
                    this.this$0.drag = null;
                    GxPersonalitiesPage.saveCurve$default(this.this$0, this.profile, this.category, false, 4, null);
                    this.this$0.forceRender();
                    return true;
                case 2:
                    if (this.this$0.drag == null) {
                        return false;
                    }
                    if (this.this$0.getEditingLocked()) {
                        restore();
                        return true;
                    }
                    move(e);
                    return true;
                case 3:
                    ViewParent parent3 = getParent();
                    if (parent3 != null) {
                        parent3.requestDisallowInterceptTouchEvent(false);
                    }
                    restore();
                    return true;
                default:
                    return false;
            }
        }
    }
}
