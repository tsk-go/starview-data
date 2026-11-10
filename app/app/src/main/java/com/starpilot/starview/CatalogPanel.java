package com.starpilot.starview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.starpilot.starview.GalaxyApi;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.json.JSONObject;
import android.content.Context;
import android.widget.LinearLayout.LayoutParams;
import kotlin.jvm.internal.Ref.IntRef;
import kotlin.jvm.internal.Ref.ObjectRef;

/* compiled from: CatalogPanel.kt */
public final class CatalogPanel extends ScrollView {
    private final Activity act;
    private boolean advanced;
    private final GalaxyApi api;
    private final Map<String, GalaxyApi.Param> byKey;
    private final Map<String, List<GalaxyApi.Param>> children;
    private final LinearLayout col;
    private View emptyNote;
    private String lastSig;
    private final boolean needsOffroad;
    private final Function1<String, Unit> openWeb;
    private final HashMap<String, List<Pair<String, String>>> optionCache;
    private final List<GalaxyApi.Param> params;
    private final ArrayList<Row> rows;
    private final String sectionName;
    private boolean syncing;
    private final CarUi ui;
    public static final Companion Companion = new Companion(null);
    private static final Set<String> HIDDEN = SetsKt.setOf(new String[]{"AccelerationProfile", "AggressiveFollow", "AggressiveFollowHigh", "CustomAccelProfile", "CustomAccelProfile0MPH", "CustomAccelProfile11MPH", "CustomAccelProfile22MPH", "CustomAccelProfile34MPH", "CustomAccelProfile45MPH", "CustomAccelProfile56MPH", "CustomAccelProfile89MPH", "DecelerationProfile", "EVTuning", "HumanAcceleration", "RelaxedFollow", "RelaxedFollowHigh", "StandardFollow", "StandardFollowHigh", "TrafficFollow", "TruckTuning", "TrafficPersonalityProfile", "AggressivePersonalityProfile", "StandardPersonalityProfile", "RelaxedPersonalityProfile", "AlphaLongitudinalEnabled", "ForceOffroad", "GsmMetered", "IsRHD"});
    private static final Set<String> RADAR_REQUIRED = SetsKt.setOf(new String[]{"HumanLaneChanges", "RadarTakeoffs"});
    private static final List<String> GM = CollectionsKt.listOf(new String[]{"Buick", "Cadillac", "Chevrolet", "GMC", "Holden"});
    private static final List<String> HKG = CollectionsKt.listOf(new String[]{"Genesis", "Hyundai", "Kia"});
    private static final Map<String, List<String>> VEHICLE_MAKES = MapsKt.mapOf(TuplesKt.to("RivianAngleControl", CollectionsKt.listOf("Rivian")), TuplesKt.to("TeslaCoopSteering", CollectionsKt.listOf("Tesla")), TuplesKt.to("NAPRadarEnabled", CollectionsKt.listOf("Tesla")), TuplesKt.to("NAPRadarBehindNosecone", CollectionsKt.listOf("Tesla")), TuplesKt.to("NAPRadarOffset", CollectionsKt.listOf("Tesla")), TuplesKt.to("NAPPedalEnabled", CollectionsKt.listOf("Tesla")), TuplesKt.to("NAPPedalCanBus", CollectionsKt.listOf("Tesla")), TuplesKt.to("NAPAdaptiveAccel", CollectionsKt.listOf("Tesla")), TuplesKt.to("NAPPedalCalibDone", CollectionsKt.listOf("Tesla")), TuplesKt.to("NAPPedalCalibFactor", CollectionsKt.listOf("Tesla")), TuplesKt.to("NAPPedalCalibZero", CollectionsKt.listOf("Tesla")), TuplesKt.to("GMPedalLongitudinal", GM), TuplesKt.to("GMDashSpoofOffsets", GM), TuplesKt.to("IgnoreIgnitionLine", GM), TuplesKt.to("LongPitch", GM), TuplesKt.to("RemoteStartBootsComma", GM), TuplesKt.to("HKGRemoteStartBootsComma", HKG), TuplesKt.to("VoltSNG", CollectionsKt.listOf(new String[]{"Chevrolet", "Holden"})), TuplesKt.to("GMAutoHold", CollectionsKt.listOf(new String[]{"Buick", "Chevrolet", "Holden"})), TuplesKt.to("VoltOnePedalMode", CollectionsKt.listOf(new String[]{"Chevrolet", "Holden"})), TuplesKt.to("RemapCancelToDistance", CollectionsKt.listOf(new String[]{"Chevrolet", "Holden"})), TuplesKt.to("JeepBrakeHold", CollectionsKt.listOf("Jeep")), TuplesKt.to("SubaruSNG", CollectionsKt.listOf("Subaru")), TuplesKt.to("SubaruSNGManualParkingBrake", CollectionsKt.listOf("Subaru")), TuplesKt.to("SubaruStopStartOff", CollectionsKt.listOf("Subaru")), TuplesKt.to("SubaruAvhStartup", CollectionsKt.listOf("Subaru")), TuplesKt.to("SubaruRedneckCruise", CollectionsKt.listOf("Subaru")), TuplesKt.to("SNGHack", CollectionsKt.listOf(new String[]{"Lexus", "Toyota"})), TuplesKt.to("ToyotaAutoHold", CollectionsKt.listOf(new String[]{"Lexus", "Toyota"})));
    private static final List<Pair<String, List<String>>> PREFIX_MAKES = CollectionsKt.listOf(new Pair[]{TuplesKt.to("Ford", CollectionsKt.listOf(new String[]{"Ford", "Lincoln"})), TuplesKt.to("Toyota", CollectionsKt.listOf(new String[]{"Toyota", "Lexus"})), TuplesKt.to("Lexus", CollectionsKt.listOf(new String[]{"Toyota", "Lexus"})), TuplesKt.to("Tesla", CollectionsKt.listOf("Tesla")), TuplesKt.to("Subaru", CollectionsKt.listOf("Subaru")), TuplesKt.to("Hyundai", CollectionsKt.listOf(new String[]{"Hyundai", "Kia", "Genesis"})), TuplesKt.to("HKG", CollectionsKt.listOf(new String[]{"Hyundai", "Kia", "Genesis"})), TuplesKt.to("GM", CollectionsKt.listOf(new String[]{"Buick", "Cadillac", "Chevrolet", "GMC", "Holden"})), TuplesKt.to("Honda", CollectionsKt.listOf(new String[]{"Honda", "Acura"})), TuplesKt.to("Rivian", CollectionsKt.listOf("Rivian")), TuplesKt.to("Nissan", CollectionsKt.listOf("Nissan")), TuplesKt.to("Mazda", CollectionsKt.listOf("Mazda")), TuplesKt.to("VW", CollectionsKt.listOf(new String[]{"Volkswagen", "Audi", "Skoda", "Seat"})), TuplesKt.to("Chrysler", CollectionsKt.listOf(new String[]{"Chrysler", "Jeep", "Ram", "Dodge"})), TuplesKt.to("Jeep", CollectionsKt.listOf("Jeep"))});
    private static final List<String> SPEED_RANGES_IMP = CollectionsKt.listOf(new String[]{"0–24", "25–34", "35–44", "45–54", "55–64", "65–74", "75–99"});
    private static final List<String> SPEED_RANGES_MET = CollectionsKt.listOf(new String[]{"0–29", "30–49", "50–59", "60–79", "80–99", "100–119", "120–140"});

    public /* synthetic */ CatalogPanel(Activity activity, GalaxyApi galaxyApi, List list, String str, boolean z, Function1 function1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(activity, galaxyApi, list, str, (i & 16) != 0 ? false : z, function1);
    }

    /* VF: from Vineflower (jadx: ) */
    public CatalogPanel(Activity var1, GalaxyApi var2, List<GalaxyApi.Param> var3, String var4, boolean var5, Function1<? super String, Unit> var6) {
        super((Context)var1);
        Intrinsics.checkNotNullParameter(var1, "act");
        Intrinsics.checkNotNullParameter(var2, "api");
        Intrinsics.checkNotNullParameter(var3, "params");
        Intrinsics.checkNotNullParameter(var4, "sectionName");
        Intrinsics.checkNotNullParameter(var6, "openWeb");
        this.act = var1;
        this.api = var2;
        this.params = var3;
        this.sectionName = var4;
        this.openWeb = (Function1) var6;
        this.ui = new CarUi((Context)this.act, var5);
        LinearLayout var13 = new LinearLayout((Context)this.act);
        var13.setOrientation(1);
        CarUi var7 = this.ui;
        if (var5) {
            var13.setPadding(0, 0, 0, var7.dp(40));
        } else {
            var13.setPadding(var7.dp(10), this.ui.dp(4), this.ui.dp(24), this.ui.dp(60));
        }

        this.col = var13;
        Iterable var14 = this.params;
        Map var8 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(var14, 10)), 16));

        for (Object var17 : var14) {
            var8.put(((GalaxyApi.Param)var17).getKey(), var17);
        }

        this.byKey = var8;
        Iterable var9 = this.params;
        Map var16 = new LinkedHashMap();

        for (Object var19 : var9) {
            String var21 = ((GalaxyApi.Param)var19).getParentKey();
            Object var10 = var16.get(var21);
            if (var10 == null) {
                var10 = new ArrayList();
                var16.put(var21, var10);
            }

            ((List)var10).add(var19);
        }

        this.children = var16;
        this.rows = new ArrayList<>();
        this.optionCache = new HashMap<>();
        var var11 = this.params;
        if (var11 instanceof Collection && ((Collection)var11).isEmpty()) {
            var5 = false;
        } else {
            Iterator var12 = var11.iterator();

            while (true) {
                if (!var12.hasNext()) {
                    var5 = false;
                    break;
                }

                if (((GalaxyApi.Param)var12.next()).getO().optBoolean("requires_offroad")) {
                    var5 = true;
                    break;
                }
            }
        }

        this.needsOffroad = var5;
        this.addView((View)this.col);
        this.advanced = this.api.bool("GalaxyDeveloperMode");
        this.build();
        this.lastSig = "";
    }

    /* compiled from: CatalogPanel.kt */
    private static final class Row {
        private final int depth;
        private final GalaxyApi.Param p;
        private final Function0<Unit> sync;
        private final View view;

        public Row(GalaxyApi.Param p, View view, int depth, Function0<Unit> sync) {
            Intrinsics.checkNotNullParameter(p, "p");
            Intrinsics.checkNotNullParameter(view, "view");
            Intrinsics.checkNotNullParameter(sync, "sync");
            this.p = p;
            this.view = view;
            this.depth = depth;
            this.sync = sync;
        }

        public final int getDepth() {
            return this.depth;
        }

        public final GalaxyApi.Param getP() {
            return this.p;
        }

        public final Function0<Unit> getSync() {
            return this.sync;
        }

        public final View getView() {
            return this.view;
        }
    }

    public final boolean getNeedsOffroad() {
        return this.needsOffroad;
    }

    /* compiled from: CatalogPanel.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private final boolean tierVisible(GalaxyApi.Param p) {
        return this.advanced || Intrinsics.areEqual(this.sectionName, "Favorites") || Intrinsics.areEqual(p.getTier(), "simple");
    }

    private final boolean visible(GalaxyApi.Param p) {
        boolean z;
        boolean z2;
        boolean z3;
        Object obj;
        List emptyList;
        if (HIDDEN.contains(p.getKey())) {
            return false;
        }
        String make = StringsKt.trim((CharSequence) this.api.string("CarMake")).toString().toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(make, "toLowerCase(...)");
        List strList = p.strList("vehicle_makes");
        if (strList.isEmpty()) {
            List list = VEHICLE_MAKES.get(p.getKey());
            if (list == null) {
                list = CollectionsKt.emptyList();
            }
            strList = list;
        }
        List allowed = strList;
        if (allowed.isEmpty()) {
            Iterable $this$firstOrNull$iv = PREFIX_MAKES;
            Iterator it = $this$firstOrNull$iv.iterator();
            while (true) {
                obj = null;
                if (!it.hasNext()) {
                    break;
                }
                Object element$iv = it.next();
                Pair it2 = (Pair) element$iv;
                if (StringsKt.startsWith(p.getKey(), (String) it2.getFirst(), false) && p.getKey().length() > ((String) it2.getFirst()).length() && Character.isUpperCase(p.getKey().charAt(((String) it2.getFirst()).length()))) {
                    obj = element$iv;
                    break;
                }
            }
            Pair pair = (Pair) obj;
            if (pair == null || (emptyList = (List) pair.getSecond()) == null) {
                emptyList = CollectionsKt.emptyList();
            }
            allowed = emptyList;
        }
        if (!allowed.isEmpty()) {
            if (make.length() > 0) {
                Iterable $this$none$iv = allowed;
                if (!($this$none$iv instanceof Collection) || !((Collection) $this$none$iv).isEmpty()) {
                    Iterator it3 = $this$none$iv.iterator();
                    while (true) {
                        if (!it3.hasNext()) {
                            z3 = true;
                            break;
                        }
                        Object element$iv2 = it3.next();
                        String lowerCase = ((String) element$iv2).toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                        if (Intrinsics.areEqual(lowerCase, make)) {
                            z3 = false;
                            break;
                        }
                    }
                } else {
                    z3 = true;
                }
                if (z3) {
                    return false;
                }
            }
        }
        Iterable $this$any$iv = p.strList("excluded_vehicle_makes");
        if (!($this$any$iv instanceof Collection) || !((Collection) $this$any$iv).isEmpty()) {
            Iterator it4 = $this$any$iv.iterator();
            while (true) {
                if (!it4.hasNext()) {
                    z = false;
                    break;
                }
                Object element$iv3 = it4.next();
                String lowerCase2 = ((String) element$iv3).toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase2, "toLowerCase(...)");
                if (Intrinsics.areEqual(lowerCase2, make)) {
                    z = true;
                    break;
                }
            }
        } else {
            z = false;
        }
        if (z) {
            return false;
        }
        String k = p.str("visible_when_key");
        if (k != null) {
            String cur = this.api.string(k);
            Iterable $this$none$iv2 = p.strList("visible_when_values");
            if (!($this$none$iv2 instanceof Collection) || !((Collection) $this$none$iv2).isEmpty()) {
                Iterator it5 = $this$none$iv2.iterator();
                while (true) {
                    if (!it5.hasNext()) {
                        z2 = true;
                        break;
                    }
                    Object element$iv4 = it5.next();
                    if (Intrinsics.areEqual((String) element$iv4, cur)) {
                        z2 = false;
                        break;
                    }
                }
            } else {
                z2 = true;
            }
            if (z2) {
                return false;
            }
        }
        String it6 = p.str("requires_capability");
        if (it6 != null && !this.api.bool(it6)) {
            return false;
        }
        if (RADAR_REQUIRED.contains(p.getKey()) && !this.api.bool("HasRadar")) {
            return false;
        }
        if ((Intrinsics.areEqual(p.getKey(), "AlphaLongitudinalEnabled") && !this.api.bool("AlphaLongitudinalAvailable")) || !tierVisible(p)) {
            return false;
        }
        String parent = p.getParentKey();
        while (parent != null) {
            GalaxyApi.Param pp = this.byKey.get(parent);
            if (pp == null) {
                return true;
            }
            if (!visible(pp)) {
                return false;
            }
            if (!Intrinsics.areEqual(pp.getUiType(), "group") && !this.api.bool(pp.getKey())) {
                return false;
            }
            parent = pp.getParentKey();
        }
        return true;
    }

    private final String lockReason(GalaxyApi.Param p) {
        if (Intrinsics.areEqual(p.getKey(), "ForceOffroad")) {
            Telemetry telemetry = Link.INSTANCE.getTelemetry();
            boolean z = false;
            if (telemetry != null && telemetry.getEnabled()) {
                z = true;
            }
            return z ? "Disengage first." : "";
        }
        if (p.getO().optBoolean("requires_offroad") && this.api.bool("IsOnroad")) {
            return "Only while offroad - use the Offroad switch at the top.";
        }
        if (p.getO().optBoolean("requires_parked") && !this.api.bool("VehicleParked")) {
            return "Only while the car is in Park.";
        }
        String it = p.str("disabled_when_key_true");
        if (it != null && this.api.bool(it)) {
            String str = p.str("disabled_reason");
            return str == null ? "Disabled by another setting." : str;
        }
        String it2 = p.str("requires_nonempty_key");
        if (it2 != null) {
            String v = this.api.string(it2);
            if (StringsKt.isBlank(v) || Intrinsics.areEqual(v, "{}")) {
                String str2 = p.str("disabled_reason");
                return str2 == null ? "Required configuration missing." : str2;
            }
        }
        return "";
    }

    private final int depthOf(GalaxyApi.Param p) {
        int d = 0;
        String k = p.getParentKey();
        while (k != null) {
            d++;
            GalaxyApi.Param param = this.byKey.get(k);
            k = param != null ? param.getParentKey() : null;
        }
        return d;
    }

    /* compiled from: CatalogPanel.kt */
    private static final class Num {
        private final String desc;
        private final String label;
        private final double max;
        private final double min;
        private final int precision;
        private final double step;
        private final String unit;

        public Num(double min, double max, double step, int precision, String unit, String label, String desc) {
            Intrinsics.checkNotNullParameter(unit, "unit");
            Intrinsics.checkNotNullParameter(label, "label");
            this.min = min;
            this.max = max;
            this.step = step;
            this.precision = precision;
            this.unit = unit;
            this.label = label;
            this.desc = desc;
        }

        public final String getDesc() {
            return this.desc;
        }

        public final String getLabel() {
            return this.label;
        }

        public final double getMax() {
            return this.max;
        }

        public final double getMin() {
            return this.min;
        }

        public final int getPrecision() {
            return this.precision;
        }

        public final double getStep() {
            return this.step;
        }

        public final String getUnit() {
            return this.unit;
        }
    }

    private final Num numeric(GalaxyApi.Param p) {
        boolean metric = this.api.getMetric();
        String mode = metric ? "metric" : "imperial";
        boolean isFloat = Intrinsics.areEqual(p.getDataType(), "float");
        double min = p.num("min", 0.0d);
        double max = p.num("max", 100.0d);
        double step = p.num("step", isFloat ? 0.01d : 1.0d);
        int prec = (int) p.num("precision", isFloat ? 2.0d : 0.0d);
        String unit = p.getUnit();
        String label = p.getLabel();
        String desc = p.getDescription();
        if (Intrinsics.areEqual(p.getUnitType(), "vehicle_speed")) {
            unit = metric ? " km/h" : " mph";
            if (p.has(mode + "_min")) {
                min = p.num(mode + "_min", min);
            }
            if (p.has(mode + "_max")) {
                max = p.num(mode + "_max", max);
            }
            if (p.has(mode + "_step")) {
                step = p.num(mode + "_step", step);
            }
            if (p.has(mode + "_precision")) {
                prec = (int) p.num(mode + "_precision", prec);
            }
            if (p.has("unit_range_index")) {
                int idx = (int) p.num("unit_range_index", -1.0d);
                String r = (String) CollectionsKt.getOrNull(metric ? SPEED_RANGES_MET : SPEED_RANGES_IMP, idx);
                if (r != null) {
                    label = "Speed Offset (" + r + unit + ")";
                    desc = "How much to offset posted speed limits between " + r + unit + ".";
                }
            }
        }
        if (Intrinsics.areEqual(p.getKey(), "ScreenBrightness") || Intrinsics.areEqual(p.getKey(), "ScreenBrightnessOnroad")) {
            min = 1.0d;
            max = 101.0d;
            step = 1.0d;
        }
        return new Num(min, max, step, prec, unit, label, desc);
    }

    private final String fmt(double v, Num n) {
        String s;
        if (n.getPrecision() <= 0) {
            s = String.valueOf(MathKt.roundToInt(v));
        } else {
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            s = String.format(Locale.US, "%." + n.getPrecision() + "f", Arrays.copyOf(new Object[]{Double.valueOf(v)}, 1));
            Intrinsics.checkNotNullExpressionValue(s, "format(...)");
        }
        return s + n.getUnit();
    }

    private final void build() {
        boolean z;
        this.col.removeAllViews();
        this.rows.clear();
        if (!Intrinsics.areEqual(this.sectionName, "Favorites")) {
            Iterable $this$any$iv = this.params;
            if (!($this$any$iv instanceof Collection) || !((Collection) $this$any$iv).isEmpty()) {
                Iterator it = $this$any$iv.iterator();
                while (true) {
                    if (it.hasNext()) {
                        Object element$iv = it.next();
                        GalaxyApi.Param it2 = (GalaxyApi.Param) element$iv;
                        if (Intrinsics.areEqual(it2.getTier(), "advanced")) {
                            z = true;
                            break;
                        }
                    } else {
                        z = false;
                        break;
                    }
                }
            } else {
                z = false;
            }
            if (z) {
                LinearLayout r = (LinearLayout) CarUi.row$default(this.ui, "Show advanced settings", "Reveals the fine-tuning options in every section (Galaxy Developer Mode).", 0, null, null, 28, null).component1();
                Switch sw = this.ui.bigSwitch();
                sw.setChecked(this.advanced);
                sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.starpilot.starview.CatalogPanel$$ExternalSyntheticLambda8
                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
                    public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                        CatalogPanel.build$lambda$15(CatalogPanel.this, compoundButton, z2);
                    }
                });
                r.addView(sw);
                this.col.addView(r, this.ui.lp(0, this.ui.getFlat() ? 0 : 14));
            }
        }
        if (StringsKt.startsWith(this.sectionName, "Longitudinal", false)) {
            buildLongitudinalMode();
        }
        build$tree(this, null);
        this.emptyNote = this.ui.note("Nothing to show here for your car at this level. Turn on \"Show advanced settings\" for the fine-tuning options.");
        this.col.addView(this.emptyNote, CarUi.lp$default(this.ui, 0, 0, 3, null));
        syncAll();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void build$lambda$15(final CatalogPanel this$0, CompoundButton compoundButton, boolean v) {
        if (this$0.syncing) {
            return;
        }
        this$0.advanced = v;
        GalaxyApi.put$default(this$0.api, "GalaxyDeveloperMode", Boolean.valueOf(v), null, new Function1() { // from class: com.starpilot.starview.CatalogPanel$$ExternalSyntheticLambda34
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit build$lambda$15$lambda$14;
                build$lambda$15$lambda$14 = CatalogPanel.build$lambda$15$lambda$14(this$0, (String) obj);
                return build$lambda$15$lambda$14;
            }
        }, 4, null);
        this$0.build();
        this$0.syncAll();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$15$lambda$14(CatalogPanel this$0, String e) {
        if (e != null) {
            this$0.toast(e);
        }
        return Unit.INSTANCE;
    }

    private static final void build$tree(CatalogPanel this$0, String parent) {
        List<GalaxyApi.Param> list = this$0.children.get(parent);
        if (list == null) {
            list = CollectionsKt.emptyList();
        }
        for (GalaxyApi.Param p : list) {
            this$0.addRow(p);
            if (Intrinsics.areEqual(p.getKey(), "CustomPersonalities")) {
                this$0.addLink("Driving personalities editor", "Braking, acceleration and following distance per profile.", p, "#/settings/longitudinal-speed-following");
            } else {
                build$tree(this$0, p.getKey());
            }
        }
    }

    private final void addLink(String title, String desc, final GalaxyApi.Param p, final String route) {
        final LinearLayout r = (LinearLayout) CarUi.row$default(this.ui, title, desc, 1, null, null, 24, null).component1();
        r.addView(CarUi.button$default(this.ui, "Open", false, new Function0() { // from class: com.starpilot.starview.CatalogPanel$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit addLink$lambda$16;
                addLink$lambda$16 = CatalogPanel.addLink$lambda$16(CatalogPanel.this, route);
                return addLink$lambda$16;
            }
        }, 2, null));
        this.col.addView(r, CarUi.lp$default(this.ui, p != null ? depthOf(p) + 1 : 0, 0, 2, null));
        if (p != null) {
            this.rows.add(new Row(p, r, 0, new Function0() { // from class: com.starpilot.starview.CatalogPanel$$ExternalSyntheticLambda14
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit addLink$lambda$17;
                    addLink$lambda$17 = CatalogPanel.addLink$lambda$17(r, CatalogPanel.this, p);
                    return addLink$lambda$17;
                }
            }));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit addLink$lambda$16(CatalogPanel this$0, String $route) {
        this$0.openWeb.invoke($route);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit addLink$lambda$17(LinearLayout $r, CatalogPanel this$0, GalaxyApi.Param $p) {
        $r.setVisibility((this$0.visible($p) && this$0.api.bool($p.getKey())) ? 0 : 8);
        return Unit.INSTANCE;
    }

    private final void addRow(GalaxyApi.Param p) {
        try {
            addRowUnsafe(p);
        } catch (Exception e) {
            Log.e("StarView/Catalog", "row " + p.getKey(), e);
            LinearLayout r = (LinearLayout) CarUi.row$default(this.ui, p.getLabel(), "Could not render this setting: " + e, 0, null, null, 28, null).component1();
            this.col.addView(r, CarUi.lp$default(this.ui, depthOf(p), 0, 2, null));
        }
    }

    /* VF: from Vineflower (jadx: ) */
    private final void addRowUnsafe(GalaxyApi.Param var1) {
        int var3 = this.depthOf(var1);
        String var5 = var1.getUiType();
        int var2 = var5.hashCode();
        TextView var4 = null;
        switch (var2) {
            case -2000413939:
                if (var5.equals("numeric")) {
                    this.addNumeric(var1, var3);
                }
                break;
            case -1785238953:
                if (var5.equals("favorites")) {
                    LinearLayout var12 = (LinearLayout)CarUi.row$default(this.ui, var1.getLabel(), var1.getDescription(), 0, null, null, 28, null).component1();
                    var12.addView((View)CarUi.button$default(this.ui, "Open", false, new CatalogPanel$$ExternalSyntheticLambda35(this), 2, null));
                    this.col.addView((View)var12, (android.view.ViewGroup.LayoutParams)CarUi.lp$default(this.ui, var3, 0, 2, null));
                    this.rows.add(new CatalogPanel.Row(var1, (View)var12, var3, new CatalogPanel$$ExternalSyntheticLambda36(var12, this, var1)));
                }
                break;
            case -1422950858:
                if (var5.equals("action")) {
                    Pair var10 = CarUi.row$default(this.ui, var1.getLabel(), var1.getDescription(), var3, null, null, 24, null);
                    LinearLayout var6 = (LinearLayout)var10.component1();
                    TextView var8 = (TextView)var10.component2();
                    CarUi var7 = this.ui;
                    var5 = var1.str("action_label");
                    String var11 = var5;
                    if (var5 == null) {
                        var11 = "Run";
                    }

                    var6.addView((View)CarUi.button$default(var7, var11, false, new CatalogPanel$$ExternalSyntheticLambda38(var1, this), 2, null));
                    this.col.addView((View)var6, (android.view.ViewGroup.LayoutParams)CarUi.lp$default(this.ui, var3, 0, 2, null));
                    this.rows.add(new CatalogPanel.Row(var1, (View)var6, var3, new CatalogPanel$$ExternalSyntheticLambda1(var6, this, var1, var8)));
                }
                break;
            case -868304044:
                if (var5.equals("toggle")) {
                    this.addToggle(var1, var3);
                }
                break;
            case -432061423:
                if (var5.equals("dropdown")) {
                    this.addDropdown(var1, var3);
                }
                break;
            case 94842723:
                if (var5.equals("color")) {
                    this.addColor(var1, var3);
                }
                break;
            case 98629247:
                if (var5.equals("group")) {
                    TextView var14 = this.ui.header(var1.getLabel());
                    this.col.addView((View)var14, (android.view.ViewGroup.LayoutParams)this.ui.lp(var3, 0));
                    if (var1.getDescription() != null) {
                        var4 = this.ui.note(var1.getDescription());
                    }

                    if (var4 != null) {
                        this.col.addView((View)var4, (android.view.ViewGroup.LayoutParams)this.ui.lp(var3, 4));
                    }

                    this.rows.add(new CatalogPanel.Row(var1, (View)var14, var3, new CatalogPanel$$ExternalSyntheticLambda2(this, var1, var14, var4)));
                }
                break;
            case 1080419832:
                if (var5.equals("readout")) {
                    LinearLayout var13 = (LinearLayout)CarUi.row$default(this.ui, var1.getLabel(), var1.getDescription(), var3, null, null, 24, null).component1();
                    var4 = this.value();
                    var13.addView((View)var4);
                    this.col.addView((View)var13, (android.view.ViewGroup.LayoutParams)CarUi.lp$default(this.ui, var3, 0, 2, null));
                    this.rows.add(new CatalogPanel.Row(var1, (View)var13, var3, new CatalogPanel$$ExternalSyntheticLambda37(var13, this, var1, var4)));
                }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit addRowUnsafe$lambda$18(CatalogPanel this$0) {
        this$0.openWeb.invoke("#/settings/favorites");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit addRowUnsafe$lambda$19(LinearLayout $r, CatalogPanel this$0, GalaxyApi.Param $p) {
        CatalogPanelKt.vis($r, this$0.visible($p));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit addRowUnsafe$lambda$20(LinearLayout $r, CatalogPanel this$0, GalaxyApi.Param $p, TextView $tv) {
        String str;
        CatalogPanelKt.vis($r, this$0.visible($p));
        Double v = this$0.api.m661double($p.getKey());
        if (v == null) {
            str = "--";
        } else {
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            String format = String.format(Locale.US, "%." + ((int) $p.num("precision", 2.0d)) + "f", Arrays.copyOf(new Object[]{v}, 1));
            Intrinsics.checkNotNullExpressionValue(format, "format(...)");
            str = format + $p.getUnit();
        }
        $tv.setText(str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit addRowUnsafe$lambda$25(final GalaxyApi.Param $p, final CatalogPanel this$0) {
        final Function0 run = new Function0() { // from class: com.starpilot.starview.CatalogPanel$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit addRowUnsafe$lambda$25$lambda$23;
                addRowUnsafe$lambda$25$lambda$23 = CatalogPanel.addRowUnsafe$lambda$25$lambda$23(this$0, $p);
                return addRowUnsafe$lambda$25$lambda$23;
            }
        };
        String msg = $p.str("confirm_message");
        if (msg != null) {
            new AlertDialog.Builder(this$0.act).setMessage(msg).setPositiveButton("Yes", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.CatalogPanel$$ExternalSyntheticLambda5
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    run.invoke();
                }
            }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
        } else {
            run.invoke();
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit addRowUnsafe$lambda$25$lambda$23(final CatalogPanel this$0, GalaxyApi.Param $p) {
        GalaxyApi galaxyApi = this$0.api;
        String str = $p.str("action_endpoint");
        if (str == null) {
            str = "";
        }
        galaxyApi.post(str, null, new Function2() { // from class: com.starpilot.starview.CatalogPanel$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit addRowUnsafe$lambda$25$lambda$23$lambda$22;
                addRowUnsafe$lambda$25$lambda$23$lambda$22 = CatalogPanel.addRowUnsafe$lambda$25$lambda$23$lambda$22(this$0, (String) obj, (JSONObject) obj2);
                return addRowUnsafe$lambda$25$lambda$23$lambda$22;
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit addRowUnsafe$lambda$25$lambda$23$lambda$22(CatalogPanel this$0, String e, JSONObject o) {
        String str;
        String optString;
        if (e == null) {
            if (o == null || (optString = o.optString("message")) == null) {
                str = null;
            } else {
                String str2 = optString;
                if (StringsKt.isBlank(str2)) {
                    str2 = null;
                }
                str = str2;
            }
            if (str == null) {
                str = "Done";
            }
        } else {
            str = e;
        }
        this$0.toast(str);
        GalaxyApi.refreshValues$default(this$0.api, null, 1, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit addRowUnsafe$lambda$26(LinearLayout $r, CatalogPanel this$0, GalaxyApi.Param $p, TextView $descTv) {
        CatalogPanelKt.vis($r, this$0.visible($p));
        this$0.lock($r, $descTv, $p);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit addRowUnsafe$lambda$27(CatalogPanel this$0, GalaxyApi.Param $p, TextView $h, TextView $n) {
        int v = this$0.visible($p) ? 0 : 8;
        $h.setVisibility(v);
        if ($n != null) {
            $n.setVisibility(v);
        }
        return Unit.INSTANCE;
    }

    private final TextView value() {
        TextView $this$value_u24lambda_u2428 = new TextView(this.act);
        $this$value_u24lambda_u2428.setTextColor(CarUi.Companion.getACCENT());
        $this$value_u24lambda_u2428.setTextSize(22.0f);
        $this$value_u24lambda_u2428.setTypeface(Typeface.DEFAULT_BOLD);
        $this$value_u24lambda_u2428.setGravity(17);
        $this$value_u24lambda_u2428.setMinWidth(this.ui.dp(120));
        return $this$value_u24lambda_u2428;
    }

    private final void lock(View row, TextView descTv, GalaxyApi.Param p) {
        String reason = lockReason(p);
        float a = reason.length() == 0 ? 1.0f : 0.5f;
        if (!(row.getAlpha() == a)) {
            row.setAlpha(a);
        }
        if (descTv != null) {
            String base = numeric(p).getDesc();
            if (base == null && (base = p.getDescription()) == null) {
                base = "";
            }
            CatalogPanelKt.txt(descTv, reason.length() == 0 ? base : base + "\n⚠ " + reason);
            CharSequence text = descTv.getText();
            Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
            CatalogPanelKt.vis(descTv, true ^ StringsKt.isBlank(text));
        }
    }

    private final void addToggle(final GalaxyApi.Param p, int depth) {
        Pair row$default = CarUi.row$default(this.ui, p.getLabel(), p.getDescription(), depth, null, null, 24, null);
        final LinearLayout r = (LinearLayout) row$default.component1();
        final TextView descTv = (TextView) row$default.component2();
        final Switch sw = this.ui.bigSwitch();
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.starpilot.starview.CatalogPanel$$ExternalSyntheticLambda16
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                CatalogPanel.addToggle$lambda$31(CatalogPanel.this, p, sw, compoundButton, z);
            }
        });
        r.addView(sw);
        this.col.addView(r, CarUi.lp$default(this.ui, depth, 0, 2, null));
        this.rows.add(new Row(p, r, depth, new Function0() { // from class: com.starpilot.starview.CatalogPanel$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit addToggle$lambda$32;
                addToggle$lambda$32 = CatalogPanel.addToggle$lambda$32(r, CatalogPanel.this, p, sw, descTv);
                return addToggle$lambda$32;
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void addToggle$lambda$31(final CatalogPanel this$0, GalaxyApi.Param $p, Switch $sw, CompoundButton compoundButton, final boolean v) {
        if (this$0.syncing) {
            return;
        }
        String reason = this$0.lockReason($p);
        if (reason.length() > 0) {
            this$0.toast(reason);
            this$0.syncing = true;
            $sw.setChecked(!v);
            this$0.syncing = false;
            return;
        }
        if (Intrinsics.areEqual($p.getKey(), "ForceOffroad")) {
            DataClient d = Link.INSTANCE.getData();
            if (d == null || !d.getConnected()) {
                this$0.toast("Not connected to the comma");
                this$0.syncing = true;
                $sw.setChecked(!v);
                this$0.syncing = false;
                return;
            }
            d.request("drive_state", MapsKt.mapOf(TuplesKt.to("mode", v ? "offroad" : "default")), new Function1() { // from class: com.starpilot.starview.CatalogPanel$$ExternalSyntheticLambda6
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit addToggle$lambda$31$lambda$29;
                    addToggle$lambda$31$lambda$29 = CatalogPanel.addToggle$lambda$31$lambda$29(this$0, v, (JSONObject) obj);
                    return addToggle$lambda$31$lambda$29;
                }
            });
            return;
        }
        GalaxyApi.put$default(this$0.api, $p.getKey(), Boolean.valueOf(v), null, new Function1() { // from class: com.starpilot.starview.CatalogPanel$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit addToggle$lambda$31$lambda$30;
                addToggle$lambda$31$lambda$30 = CatalogPanel.addToggle$lambda$31$lambda$30(this$0, (String) obj);
                return addToggle$lambda$31$lambda$30;
            }
        }, 4, null);
        this$0.syncAll();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit addToggle$lambda$31$lambda$29(CatalogPanel this$0, boolean $v, JSONObject ack) {
        Intrinsics.checkNotNullParameter(ack, "ack");
        if (ack.optBoolean("ok")) {
            this$0.api.getValues().put("ForceOffroad", Boolean.valueOf($v));
            this$0.api.getValues().put("ForceOnroad", false);
        } else {
            String optString = ack.optString("error", "rejected");
            Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
            this$0.toast(optString);
            GalaxyApi.refreshValues$default(this$0.api, null, 1, null);
        }
        this$0.syncAll();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit addToggle$lambda$31$lambda$30(CatalogPanel this$0, String e) {
        if (e != null) {
            this$0.toast(e);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit addToggle$lambda$32(LinearLayout $r, CatalogPanel this$0, GalaxyApi.Param $p, Switch $sw, TextView $descTv) {
        CatalogPanelKt.vis($r, this$0.visible($p));
        $sw.setChecked(this$0.api.bool($p.getKey()));
        this$0.lock($r, $descTv, $p);
        return Unit.INSTANCE;
    }

    private final void addNumeric(final GalaxyApi.Param p, int depth) {
        Num n0 = numeric(p);
        Pair row$default = CarUi.row$default(this.ui, n0.getLabel(), n0.getDesc(), depth, null, null, 24, null);
        final LinearLayout r = (LinearLayout) row$default.component1();
        final TextView descTv = (TextView) row$default.component2();
        final TextView tv = value();
        LinearLayout $this$addNumeric_u24lambda_u2433 = new LinearLayout(this.act);
        $this$addNumeric_u24lambda_u2433.setOrientation(0);
        $this$addNumeric_u24lambda_u2433.setGravity(16);
        $this$addNumeric_u24lambda_u2433.addView(this.ui.stepper("−", new Function0() { // from class: com.starpilot.starview.CatalogPanel$$ExternalSyntheticLambda19
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit addNumeric$lambda$35;
                addNumeric$lambda$35 = CatalogPanel.addNumeric$lambda$35(CatalogPanel.this, p);
                return addNumeric$lambda$35;
            }
        }));
        LinearLayout.LayoutParams $this$addNumeric_u24lambda_u2436 = new LinearLayout.LayoutParams(-2, -2);
        $this$addNumeric_u24lambda_u2436.leftMargin = this.ui.dp(6);
        $this$addNumeric_u24lambda_u2436.rightMargin = this.ui.dp(6);
        Unit unit = Unit.INSTANCE;
        $this$addNumeric_u24lambda_u2433.addView(tv, $this$addNumeric_u24lambda_u2436);
        $this$addNumeric_u24lambda_u2433.addView(this.ui.stepper("+", new Function0() { // from class: com.starpilot.starview.CatalogPanel$$ExternalSyntheticLambda20
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit addNumeric$lambda$37;
                addNumeric$lambda$37 = CatalogPanel.addNumeric$lambda$37(CatalogPanel.this, p);
                return addNumeric$lambda$37;
            }
        }));
        tv.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.CatalogPanel$$ExternalSyntheticLambda21
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CatalogPanel.addNumeric$lambda$41(CatalogPanel.this, p, view);
            }
        });
        View childAt = $this$addNumeric_u24lambda_u2433.getChildAt(0);
        Intrinsics.checkNotNull(childAt, "null cannot be cast to non-null type android.widget.TextView");
        ((TextView) childAt).setOnLongClickListener(new View.OnLongClickListener() { // from class: com.starpilot.starview.CatalogPanel$$ExternalSyntheticLambda23
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                boolean addNumeric$lambda$42;
                addNumeric$lambda$42 = CatalogPanel.addNumeric$lambda$42(CatalogPanel.this, p, view);
                return addNumeric$lambda$42;
            }
        });
        View childAt2 = $this$addNumeric_u24lambda_u2433.getChildAt(2);
        Intrinsics.checkNotNull(childAt2, "null cannot be cast to non-null type android.widget.TextView");
        ((TextView) childAt2).setOnLongClickListener(new View.OnLongClickListener() { // from class: com.starpilot.starview.CatalogPanel$$ExternalSyntheticLambda24
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                boolean addNumeric$lambda$43;
                addNumeric$lambda$43 = CatalogPanel.addNumeric$lambda$43(CatalogPanel.this, p, view);
                return addNumeric$lambda$43;
            }
        });
        r.addView($this$addNumeric_u24lambda_u2433);
        this.col.addView(r, CarUi.lp$default(this.ui, depth, 0, 2, null));
        this.rows.add(new Row(p, r, depth, new Function0() { // from class: com.starpilot.starview.CatalogPanel$$ExternalSyntheticLambda25
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit addNumeric$lambda$47;
                addNumeric$lambda$47 = CatalogPanel.addNumeric$lambda$47(r, CatalogPanel.this, p, tv, descTv);
                return addNumeric$lambda$47;
            }
        }));
    }

    private static final double addNumeric$cur(CatalogPanel this$0, GalaxyApi.Param $p) {
        Double m661double = this$0.api.m661double($p.getKey());
        return m661double != null ? m661double.doubleValue() : $p.num("default", this$0.numeric($p).getMin());
    }

    private static final void addNumeric$write(final CatalogPanel this$0, GalaxyApi.Param $p, double v) {
        Object valueOf;
        Num n = this$0.numeric($p);
        double c = Math.min(n.getMax(), Math.max(n.getMin(), v));
        double snapped = n.getStep() > 0.0d ? Math.round(c / n.getStep()) * n.getStep() : c;
        if (Intrinsics.areEqual($p.getDataType(), "int")) {
            valueOf = Integer.valueOf(MathKt.roundToInt(snapped));
        } else {
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            String format = String.format(Locale.US, "%." + Math.max(n.getPrecision(), 0) + "f", Arrays.copyOf(new Object[]{Double.valueOf(snapped)}, 1));
            Intrinsics.checkNotNullExpressionValue(format, "format(...)");
            valueOf = Double.valueOf(Double.parseDouble(format));
        }
        Object out = valueOf;
        GalaxyApi.put$default(this$0.api, $p.getKey(), out, null, new Function1() { // from class: com.starpilot.starview.CatalogPanel$$ExternalSyntheticLambda32
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit addNumeric$write$lambda$34;
                addNumeric$write$lambda$34 = CatalogPanel.addNumeric$write$lambda$34(this$0, (String) obj);
                return addNumeric$write$lambda$34;
            }
        }, 4, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit addNumeric$write$lambda$34(CatalogPanel this$0, String e) {
        if (e != null) {
            this$0.toast(e);
        }
        return Unit.INSTANCE;
    }

    private static final boolean addNumeric$guard(CatalogPanel this$0, GalaxyApi.Param $p) {
        String reason = this$0.lockReason($p);
        if (!(reason.length() > 0)) {
            return true;
        }
        this$0.toast(reason);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit addNumeric$lambda$35(CatalogPanel this$0, GalaxyApi.Param $p) {
        if (addNumeric$guard(this$0, $p)) {
            addNumeric$write(this$0, $p, addNumeric$cur(this$0, $p) - this$0.numeric($p).getStep());
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit addNumeric$lambda$37(CatalogPanel this$0, GalaxyApi.Param $p) {
        if (addNumeric$guard(this$0, $p)) {
            addNumeric$write(this$0, $p, addNumeric$cur(this$0, $p) + this$0.numeric($p).getStep());
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void addNumeric$lambda$41(final CatalogPanel this$0, final GalaxyApi.Param $p, View it) {
        if (addNumeric$guard(this$0, $p)) {
            Num n = this$0.numeric($p);
            final EditText $this$addNumeric_u24lambda_u2441_u24lambda_u2438 = new EditText(this$0.act);
            $this$addNumeric_u24lambda_u2441_u24lambda_u2438.setInputType(12290);
            $this$addNumeric_u24lambda_u2441_u24lambda_u2438.setText(this$0.fmt(addNumeric$cur(this$0, $p), new Num(n.getMin(), n.getMax(), n.getStep(), n.getPrecision(), "", "", null)));
            $this$addNumeric_u24lambda_u2441_u24lambda_u2438.setTextSize(24.0f);
            new AlertDialog.Builder(this$0.act).setTitle(n.getLabel() + " (" + this$0.fmt(n.getMin(), n) + " – " + this$0.fmt(n.getMax(), n) + ")").setView($this$addNumeric_u24lambda_u2441_u24lambda_u2438).setPositiveButton("Set", new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.CatalogPanel$$ExternalSyntheticLambda15
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    CatalogPanel.addNumeric$lambda$41$lambda$40($this$addNumeric_u24lambda_u2441_u24lambda_u2438, this$0, $p, dialogInterface, i);
                }
            }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void addNumeric$lambda$41$lambda$40(EditText $input, CatalogPanel this$0, GalaxyApi.Param $p, DialogInterface dialogInterface, int i) {
        Double doubleOrNull = StringsKt.toDoubleOrNull($input.getText().toString());
        if (doubleOrNull != null) {
            double it = doubleOrNull.doubleValue();
            addNumeric$write(this$0, $p, it);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean addNumeric$lambda$42(CatalogPanel this$0, GalaxyApi.Param $p, View it) {
        if (!addNumeric$guard(this$0, $p)) {
            return true;
        }
        addNumeric$write(this$0, $p, addNumeric$cur(this$0, $p) - (this$0.numeric($p).getStep() * 10));
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean addNumeric$lambda$43(CatalogPanel this$0, GalaxyApi.Param $p, View it) {
        if (!addNumeric$guard(this$0, $p)) {
            return true;
        }
        addNumeric$write(this$0, $p, addNumeric$cur(this$0, $p) + (this$0.numeric($p).getStep() * 10));
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit addNumeric$lambda$47(LinearLayout $r, CatalogPanel this$0, GalaxyApi.Param $p, TextView $tv, TextView $descTv) {
        CatalogPanelKt.vis($r, this$0.visible($p));
        Num n = this$0.numeric($p);
        CatalogPanelKt.txt($tv, this$0.fmt(addNumeric$cur(this$0, $p), n));
        Iterable $this$map$iv = RangesKt.until(0, $r.getChildCount());
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        Iterator<Integer> it = $this$map$iv.iterator();
        while (it.hasNext()) {
            int item$iv$iv = ((IntIterator) it).nextInt();
            destination$iv$iv.add($r.getChildAt(item$iv$iv));
        }
        Iterable $this$first$iv = (List) destination$iv$iv;
        for (Object element$iv : $this$first$iv) {
            if (((View) element$iv) instanceof LinearLayout) {
                Intrinsics.checkNotNull(element$iv, "null cannot be cast to non-null type android.widget.LinearLayout");
                View it2 = ((LinearLayout) element$iv).getChildAt(0);
                TextView textView = it2 instanceof TextView ? (TextView) it2 : null;
                if (textView == null) {
                    Intrinsics.checkNotNull(it2, "null cannot be cast to non-null type android.widget.LinearLayout");
                    View childAt = ((LinearLayout) it2).getChildAt(0);
                    Intrinsics.checkNotNull(childAt, "null cannot be cast to non-null type android.widget.TextView");
                    textView = (TextView) childAt;
                }
                CatalogPanelKt.txt(textView, n.getLabel());
                this$0.lock($r, $descTv, $p);
                return Unit.INSTANCE;
            }
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }

    /* VF: from Vineflower (jadx: Type inference failed) */
    private final void addDropdown(GalaxyApi.Param var1, int var2) {
        LinearLayout var3 = new LinearLayout((Context)this.act);
        var3.setOrientation(1);
        var3.setBackground(this.ui.rowBg());
        var3.setPadding(this.ui.dp(20), this.ui.dp(14), this.ui.dp(18), this.ui.dp(14));
        Pair var4 = CarUi.row$default(this.ui, var1.getLabel(), var1.getDescription(), 0, null, null, 28, null);
        LinearLayout var5 = (LinearLayout)var4.component1();
        TextView var11 = (TextView)var4.component2();
        var5.setBackground(null);
        var5.setPadding(0, 0, 0, 0);
        var5.setMinimumHeight(0);
        var3.addView((View)var5);
        LinearLayout var8 = new LinearLayout((Context)this.act);
        var8.setOrientation(1);
        var8.setPadding(0, this.ui.dp(10), 0, 0);
        var3.addView((View)var8);
        ObjectRef var12 = new ObjectRef();
        var12.element = var1.getOptions();
        ObjectRef var6 = new ObjectRef();
        ObjectRef var10 = new ObjectRef();
        var10.element = "";
        IntRef var7 = new IntRef();
        var7.element = -2;
        ObjectRef var9 = new ObjectRef();
        addDropdown$rebuild(var8, var6, var12, this, var1);
        this.col.addView((View)var3, (android.view.ViewGroup.LayoutParams)CarUi.lp$default(this.ui, var2, 0, 2, null));
        this.rows
            .add(
                new CatalogPanel.Row(
                    var1, (View)var3, var2, new CatalogPanel$$ExternalSyntheticLambda22(var3, this, var1, var12, var7, var6, var9, var11, var10, var8)
                )
            );
    }

    /* VF: from Vineflower (jadx: Type inference failed) */
    private static final void addDropdown$rebuild(
        LinearLayout var0, ObjectRef<Function1<Integer, Unit>> var1, ObjectRef<List<Pair<String, String>>> var2, CatalogPanel var3, GalaxyApi.Param var4
    ) {
        var0.removeAllViews();
        var1.element = null;
        if (((List)var2.element).isEmpty()) {
            var0.addView((View)var3.ui.note("No options available."));
        } else {
            CatalogPanel$$ExternalSyntheticLambda28 var5 = new CatalogPanel$$ExternalSyntheticLambda28(var3, var4, var2);
            if (((List)var2.element).size() <= 4) {
                CarUi var11 = var3.ui;
                Iterable var14 = (Iterable)var2.element;
                var var8 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var14, 10));
                Iterator var15 = var14.iterator();

                while (var15.hasNext()) {
                    var8.add((String)((Pair)var15.next()).getSecond());
                }

                List var9 = var8;
                Pair var12 = var11.chips(var9, var5);
                LinearLayout var10 = (LinearLayout)var12.component1();
                Function1 var13 = (Function1)var12.component2();
                var0.addView((View)var10);
                var1.element = var13;
            } else {
                TextView var16 = CarUi.button$default(var3.ui, "Choose…", false, new CatalogPanel$$ExternalSyntheticLambda29(var3, var4, var2, var5), 2, null);
                TextView var7 = var3.value();
                var7.setGravity(8388611);
                var7.setTextSize(20.0F);
                LinearLayout var6 = new LinearLayout((Context)var3.act);
                var6.setOrientation(0);
                var6.setGravity(16);
                var6.addView((View)var7, (android.view.ViewGroup.LayoutParams)(new android.widget.LinearLayout.LayoutParams(0, -2, 1.0F)));
                var6.addView((View)var16);
                var0.addView((View)var6);
                var1.element = (new CatalogPanel$$ExternalSyntheticLambda30(var7, var2, var3, var4));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit addDropdown$rebuild$lambda$51(final CatalogPanel this$0, GalaxyApi.Param $p, Ref.ObjectRef $opts, int i) {
        Double doubleOrNull;
        String reason = this$0.lockReason($p);
        if (reason.length() > 0) {
            this$0.toast(reason);
        } else {
            String v = (String) ((Pair) ((List) $opts.element).get(i)).getFirst();
            this$0.api.put($p.getKey(), (!Intrinsics.areEqual($p.getDataType(), "int") || (doubleOrNull = StringsKt.toDoubleOrNull(v)) == null) ? v : Integer.valueOf(MathKt.roundToInt(doubleOrNull.doubleValue())), MapsKt.mapOf(TuplesKt.to("label", ((Pair) ((List) $opts.element).get(i)).getSecond())), new Function1() { // from class: com.starpilot.starview.CatalogPanel$$ExternalSyntheticLambda18
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit addDropdown$rebuild$lambda$51$lambda$50;
                    addDropdown$rebuild$lambda$51$lambda$50 = CatalogPanel.addDropdown$rebuild$lambda$51$lambda$50(this$0, (String) obj);
                    return addDropdown$rebuild$lambda$51$lambda$50;
                }
            });
            this$0.syncAll();
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit addDropdown$rebuild$lambda$51$lambda$50(CatalogPanel this$0, String e) {
        if (e != null) {
            this$0.toast(e);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit addDropdown$rebuild$lambda$55(CatalogPanel this$0, GalaxyApi.Param $p, Ref.ObjectRef $opts, final Function1 $pick) {
        AlertDialog.Builder title = new AlertDialog.Builder(this$0.act).setTitle($p.getLabel());
        Iterable $this$map$iv = (Iterable) $opts.element;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            Pair it = (Pair) item$iv$iv;
            destination$iv$iv.add((String) it.getSecond());
        }
        Collection thisCollection$iv = (List) destination$iv$iv;
        title.setItems((CharSequence[]) thisCollection$iv.toArray(new String[0]), new DialogInterface.OnClickListener() { // from class: com.starpilot.starview.CatalogPanel$$ExternalSyntheticLambda31
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                CatalogPanel.addDropdown$rebuild$lambda$55$lambda$54($pick, dialogInterface, i);
            }
        }).show();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void addDropdown$rebuild$lambda$55$lambda$54(Function1 $pick, DialogInterface dialogInterface, int i) {
        $pick.invoke(Integer.valueOf(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit addDropdown$rebuild$lambda$59(TextView $cur, Ref.ObjectRef $opts, CatalogPanel this$0, GalaxyApi.Param $p, int i) {
        String str;
        Pair pair = (Pair) CollectionsKt.getOrNull((List) $opts.element, i);
        if (pair == null || (str = (String) pair.getSecond()) == null) {
            String string = this$0.api.string($p.getKey());
            if (StringsKt.isBlank(string)) {
                string = "(not set)";
            }
            str = string;
        }
        CatalogPanelKt.txt($cur, str);
        return Unit.INSTANCE;
    }

    /* VF: from Vineflower (jadx: Type inference failed) */
    public static final Unit addDropdown$lambda$64(
        LinearLayout var0,
        CatalogPanel var1,
        GalaxyApi.Param var2,
        ObjectRef var3,
        IntRef var4,
        ObjectRef var5,
        ObjectRef var6,
        TextView var7,
        ObjectRef var8,
        LinearLayout var9
    ) {
        CatalogPanelKt.vis((View)var0, var1.visible(var2));
        String var12 = var2.str("options_endpoint");
        if (var12 != null) {
            CharSequence var19 = var12;
            var12 = new Regex("\\{(\\w+)}").replace(var19, new CatalogPanel$$ExternalSyntheticLambda26(var1));
            if (!Intrinsics.areEqual(var12, var8.element)) {
                var8.element = var12;
                List var16 = var1.optionCache.get(var12);
                if (var16 != null) {
                    var3.element = var16;
                    addDropdown$rebuild(var9, var5, var3, var1, var2);
                } else {
                    var1.api.options(var12, new CatalogPanel$$ExternalSyntheticLambda27(var1, var12, var3, var9, var5, var2));
                }
            }
        }

        String var17 = var1.api.string(var2.getKey());
        List var13 = (List)var3.element;
        int var10 = 0;
        Iterator var14 = var13.iterator();

        while (true) {
            if (!var14.hasNext()) {
                var10 = -1;
                break;
            }

            Pair var18 = (Pair)var14.next();
            boolean var11;
            if (Intrinsics.areEqual(var18.getFirst(), var17)
                || StringsKt.toDoubleOrNull((String)var18.getFirst()) != null
                    && StringsKt.toDoubleOrNull(var17) != null
                    && Math.abs(Double.parseDouble((String)var18.getFirst()) - Double.parseDouble(var17)) < 1.0E-9) {
                var11 = true;
            } else {
                var11 = false;
            }

            if (var11) {
                break;
            }

            var10++;
        }

        if (var10 != var4.element || var5.element != var6.element) {
            var4.element = var10;
            var6.element = var5.element;
            Function1 var15 = (Function1)var5.element;
            if (var15 != null) {
                var15.invoke(var10);
            }
        }

        var1.lock((View)var0, var7, var2);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence addDropdown$lambda$64$lambda$62$lambda$60(CatalogPanel this$0, MatchResult m) {
        Intrinsics.checkNotNullParameter(m, "m");
        String encode = URLEncoder.encode(this$0.api.string(m.getGroupValues().get(1)), "UTF-8");
        Intrinsics.checkNotNullExpressionValue(encode, "encode(...)");
        return encode;
    }

    /* VF: from Vineflower (jadx: ) */
    public static final Unit addDropdown$lambda$64$lambda$62$lambda$61(
        CatalogPanel var0, String var1, ObjectRef var2, LinearLayout var3, ObjectRef var4, GalaxyApi.Param var5, List var6
    ) {
        Intrinsics.checkNotNullParameter(var6, "o");
        var0.optionCache.put(var1, var6);
        var2.element = var6;
        addDropdown$rebuild(var3, var4, var2, var0, var5);
        var0.syncAll();
        return Unit.INSTANCE;
    }

    private final void addColor(final GalaxyApi.Param p, int depth) {
        String str = "Stock";
        String str2 = "";
        final List presets = CollectionsKt.listOf(new Pair[]{TuplesKt.to("Stock", ""), TuplesKt.to("White", "#FFFFFF"), TuplesKt.to("Green", "#00FF00"), TuplesKt.to("Blue", "#1E90FF"), TuplesKt.to("Red", "#FF3B30"), TuplesKt.to("Yellow", "#FFD60A"), TuplesKt.to("Orange", "#FF8C00"), TuplesKt.to("Purple", "#A24BFF"), TuplesKt.to("Cyan", "#00E5FF"), TuplesKt.to("Pink", "#FF4FA3")});
        final LinearLayout $this$addColor_u24lambda_u2465 = new LinearLayout(this.act);
        $this$addColor_u24lambda_u2465.setOrientation(1);
        $this$addColor_u24lambda_u2465.setBackground(this.ui.rowBg());
        $this$addColor_u24lambda_u2465.setPadding(this.ui.dp(20), this.ui.dp(14), this.ui.dp(18), this.ui.dp(14));
        Pair row$default = CarUi.row$default(this.ui, p.getLabel(), p.getDescription(), 0, null, null, 28, null);
        LinearLayout titleRow = (LinearLayout) row$default.component1();
        TextView descTv = (TextView) row$default.component2();
        titleRow.setBackground(null);
        titleRow.setPadding(0, 0, 0, 0);
        titleRow.setMinimumHeight(0);
        $this$addColor_u24lambda_u2465.addView(titleRow);
        LinearLayout $this$addColor_u24lambda_u2466 = new LinearLayout(this.act);
        $this$addColor_u24lambda_u2466.setOrientation(0);
        $this$addColor_u24lambda_u2466.setPadding(0, this.ui.dp(12), 0, 0);
        final ArrayList swatches = new ArrayList();
        Iterator it = presets.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            String name = (String) pair.component1();
            final String hex = (String) pair.component2();
            TextView $this$addColor_u24lambda_u2470 = new TextView(this.act);
            Iterator it2 = it;
            $this$addColor_u24lambda_u2470.setText(hex.length() == 0 ? str : str2);
            $this$addColor_u24lambda_u2470.setTextSize(13.0f);
            $this$addColor_u24lambda_u2470.setTextColor(CarUi.Companion.getTEXT());
            $this$addColor_u24lambda_u2470.setGravity(17);
            CarUi carUi = this.ui;
            String str3 = str;
            String str4 = str2;
            $this$addColor_u24lambda_u2470.setBackground(carUi.rounded(hex.length() == 0 ? CarUi.Companion.getCARD_ON() : Color.parseColor(hex), 26, Integer.valueOf(Color.rgb(60, 66, 78))));
            $this$addColor_u24lambda_u2470.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.CatalogPanel$$ExternalSyntheticLambda10
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    CatalogPanel.addColor$lambda$70$lambda$69(CatalogPanel.this, p, hex, view);
                }
            });
            $this$addColor_u24lambda_u2470.setContentDescription(name);
            swatches.add($this$addColor_u24lambda_u2470);
            LinearLayout.LayoutParams $this$addColor_u24lambda_u2471 = new LinearLayout.LayoutParams(this.ui.dp(52), this.ui.dp(52));
            $this$addColor_u24lambda_u2471.rightMargin = this.ui.dp(10);
            Unit unit = Unit.INSTANCE;
            $this$addColor_u24lambda_u2466.addView($this$addColor_u24lambda_u2470, $this$addColor_u24lambda_u2471);
            str2 = str4;
            it = it2;
            str = str3;
            titleRow = titleRow;
            descTv = descTv;
        }
        final TextView descTv2 = descTv;
        $this$addColor_u24lambda_u2465.addView($this$addColor_u24lambda_u2466);
        this.col.addView($this$addColor_u24lambda_u2465, CarUi.lp$default(this.ui, depth, 0, 2, null));
        final Ref.ObjectRef paintedColor = new Ref.ObjectRef();
        this.rows.add(new Row(p, $this$addColor_u24lambda_u2465, depth, new Function0() { // from class: com.starpilot.starview.CatalogPanel$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit addColor$lambda$72;
                addColor$lambda$72 = CatalogPanel.addColor$lambda$72($this$addColor_u24lambda_u2465, CatalogPanel.this, p, paintedColor, swatches, presets, descTv2);
                return addColor$lambda$72;
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void addColor$lambda$70$lambda$69(final CatalogPanel this$0, GalaxyApi.Param $p, String $hex, View it) {
        String reason = this$0.lockReason($p);
        if (reason.length() > 0) {
            this$0.toast(reason);
            return;
        }
        GalaxyApi galaxyApi = this$0.api;
        String key = $p.getKey();
        String str = $hex;
        if (str.length() == 0) {
            str = "Stock";
        }
        GalaxyApi.put$default(galaxyApi, key, str, null, new Function1() { // from class: com.starpilot.starview.CatalogPanel$$ExternalSyntheticLambda33
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit addColor$lambda$70$lambda$69$lambda$68;
                addColor$lambda$70$lambda$69$lambda$68 = CatalogPanel.addColor$lambda$70$lambda$69$lambda$68(this$0, (String) obj);
                return addColor$lambda$70$lambda$69$lambda$68;
            }
        }, 4, null);
        this$0.syncAll();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit addColor$lambda$70$lambda$69$lambda$68(CatalogPanel this$0, String e) {
        if (e != null) {
            this$0.toast(e);
        }
        return Unit.INSTANCE;
    }

    public static final Unit addColor$lambda$72(LinearLayout $outer, CatalogPanel this$0, GalaxyApi.Param $p, Ref.ObjectRef $paintedColor, ArrayList $swatches, List $presets, TextView $descTv) {
        CatalogPanelKt.vis($outer, this$0.visible($p));
        String cur = StringsKt.trim((CharSequence) this$0.api.string($p.getKey())).toString().toUpperCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(cur, "toUpperCase(...)");
        boolean isStock = cur.length() == 0 || Intrinsics.areEqual(cur, "STOCK");
        if (!Intrinsics.areEqual(cur, $paintedColor.element)) {
            $paintedColor.element = cur;
            int i = 0;
            for (Object o : $swatches) {
                View sw = (View) o;
                String hex = (String) ((Pair) $presets.get(i)).getSecond();
                i++;
                boolean on;
                if (hex.length() == 0) {
                    on = isStock;
                } else {
                    String up = hex.toUpperCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue(up, "toUpperCase(...)");
                    on = Intrinsics.areEqual(up, cur);
                }
                sw.setBackground(this$0.ui.rounded(hex.length() == 0 ? CarUi.Companion.getCARD_ON() : Color.parseColor(hex), 26, Integer.valueOf(on ? CarUi.Companion.getACCENT() : Color.rgb(60, 66, 78))));
                sw.setScaleX(on ? 1.15f : 1.0f);
                sw.setScaleY(sw.getScaleX());
            }
        }
        this$0.lock($outer, $descTv, $p);
        return Unit.INSTANCE;
    }

    private final void buildLongitudinalMode() {
        LinearLayout outer = new LinearLayout(this.act);
        outer.setOrientation(1);
        outer.setBackground(this.ui.rowBg());
        outer.setPadding(this.ui.dp(20), this.ui.dp(14), this.ui.dp(18), this.ui.dp(14));
        LinearLayout titleRow = (LinearLayout) CarUi.row$default(this.ui, "Longitudinal control mode", "Chill follows traffic conservatively. Experimental lets the driving model handle stops and turns. The conditional modes switch automatically.", 0, null, null, 28, null).component1();
        titleRow.setBackground(null);
        titleRow.setPadding(0, 0, 0, 0);
        titleRow.setMinimumHeight(0);
        outer.addView(titleRow);
        List modes = CollectionsKt.listOf(new String[]{"Chill", "Experimental", "Conditional Experimental", "Conditional Chill"});
        Pair<LinearLayout, Function1<Integer, Unit>> chips = this.ui.chips(modes, new Function1() { // from class: com.starpilot.starview.CatalogPanel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit buildLongitudinalMode$lambda$75;
                buildLongitudinalMode$lambda$75 = CatalogPanel.buildLongitudinalMode$lambda$75(CatalogPanel.this, ((Integer) obj).intValue());
                return buildLongitudinalMode$lambda$75;
            }
        });
        LinearLayout bar = chips.component1();
        final Function1 paint = chips.component2();
        bar.setPadding(0, this.ui.dp(10), 0, 0);
        outer.addView(bar);
        this.col.addView(outer, this.ui.lp(0, 14));
        JSONObject put = new JSONObject().put("key", "__mode");
        Intrinsics.checkNotNullExpressionValue(put, "put(...)");
        GalaxyApi.Param fake = new GalaxyApi.Param(put);
        this.rows.add(new Row(fake, outer, 0, new Function0() { // from class: com.starpilot.starview.CatalogPanel$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit buildLongitudinalMode$lambda$76;
                buildLongitudinalMode$lambda$76 = CatalogPanel.buildLongitudinalMode$lambda$76(paint, CatalogPanel.this);
                return buildLongitudinalMode$lambda$76;
            }
        }));
    }

    /* VF: from Vineflower (jadx: ) */
    public static final Unit buildLongitudinalMode$lambda$75(CatalogPanel var0, int var1) {
        Boolean var4;
        Boolean var5;
        Pair var7;
        label17: {
            String var3 = "ExperimentalMode";
            var4 = false;
            var5 = true;
            switch (var1) {
                case 0:
                    var7 = TuplesKt.to("ExperimentalMode", var4);
                    break label17;
                case 1:
                    break;
                case 2:
                    var3 = "ConditionalExperimental";
                    break;
                default:
                    var3 = "ConditionalChill";
            }

            var7 = TuplesKt.to(var3, var5);
        }

        String var6 = (String)var7.component1();
        boolean var2 = (Boolean)var7.component2();
        if (var1 <= 1) {
            GalaxyApi.put$default(var0.api, "ConditionalExperimental", var4, null, null, 12, null);
            GalaxyApi.put$default(var0.api, "ConditionalChill", var4, null, null, 12, null);
        }

        var0.api.put(var6, var2, MapsKt.mapOf(TuplesKt.to("acknowledged", var5)), new CatalogPanel$$ExternalSyntheticLambda3(var0));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildLongitudinalMode$lambda$75$lambda$74(CatalogPanel this$0, String e) {
        if (e != null) {
            this$0.toast(e);
        }
        GalaxyApi.refreshValues$default(this$0.api, null, 1, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit buildLongitudinalMode$lambda$76(Function1 $paint, CatalogPanel this$0) {
        $paint.invoke(Integer.valueOf(this$0.api.bool("ConditionalExperimental") ? 2 : this$0.api.bool("ConditionalChill") ? 3 : this$0.api.bool("ExperimentalMode") ? 1 : 0));
        return Unit.INSTANCE;
    }

    public final void syncAll() {
        Object it;
        int version = this.api.getVersion();
        Telemetry telemetry = Link.INSTANCE.getTelemetry();
        this.lastSig = version + "|" + (telemetry != null ? Boolean.valueOf(telemetry.getEnabled()) : null) + "|" + DriveState.INSTANCE.onroad();
        boolean z = true;
        this.syncing = true;
        try {
            Iterator<Row> it2 = this.rows.iterator();
            Intrinsics.checkNotNullExpressionValue(it2, "iterator(...)");
            while (it2.hasNext()) {
                Row next = it2.next();
                Intrinsics.checkNotNullExpressionValue(next, "next(...)");
                Row r = next;
                try {
                    r.getSync().invoke();
                } catch (Exception e) {
                    Log.e("StarView/Catalog", "sync " + r.getP().getKey(), e);
                }
            }
            this.syncing = false;
            View view = this.emptyNote;
            if (view == null) {
                return;
            }
            Iterable $this$any$iv = this.rows;
            if (($this$any$iv instanceof Collection) && ((Collection) $this$any$iv).isEmpty()) {
                z = false;
            } else {
                Iterator it3 = $this$any$iv.iterator();
                while (true) {
                    if (it3.hasNext()) {
                        Object element$iv = it3.next();
                        Row it4 = (Row) element$iv;
                        if (!Intrinsics.areEqual(it4.getP().getKey(), "__mode") && it4.getView().getVisibility() == 0) {
                            it = 1;
                        } else {
                            it = null;
                        }
                        if (it != null) {
                            break;
                        }
                    } else {
                        z = false;
                        break;
                    }
                }
            }
            view.setVisibility(z ? 8 : 0);
        } catch (Throwable th) {
            this.syncing = false;
            throw th;
        }
    }

    public final void onTick() {
        if (this.api.bool("GalaxyDeveloperMode") != this.advanced) {
            this.advanced = !this.advanced;
            build();
            return;
        }
        int version = this.api.getVersion();
        Telemetry telemetry = Link.INSTANCE.getTelemetry();
        String sig = version + "|" + (telemetry != null ? Boolean.valueOf(telemetry.getEnabled()) : null) + "|" + DriveState.INSTANCE.onroad();
        if (!Intrinsics.areEqual(sig, this.lastSig)) {
            syncAll();
        }
    }

    private final void toast(String m) {
        Toast.makeText(this.act, m, 1).show();
    }
}
