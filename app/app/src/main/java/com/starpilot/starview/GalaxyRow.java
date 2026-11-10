package com.starpilot.starview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.SetsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: GalaxyRows.kt */
public final class GalaxyRow {
    public static final Companion Companion = new Companion(null);
    private final boolean action;
    private final boolean available;
    private final List<String> choices;
    private final boolean confirm;
    private final Control control;
    private final String coordinate;
    private final String defaultValue;
    private final boolean heading;
    private final int index;
    private final String label;
    private final boolean laneStrength;
    private final double maximum;
    private final double minimum;
    private final String page;
    private final String previewSound;
    private final String reason;
    private final String repairValue;
    private final boolean resetAvailable;
    private final double step;
    private final String unit;
    private final String value;

    public GalaxyRow(int index, JSONObject o) {
        List<String> emptyList;
        Control control;
        Intrinsics.checkNotNullParameter(o, "o");
        this.index = index;
        String optString = o.optString("label");
        Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
        this.label = optString;
        this.value = Companion.str(o, "value");
        JSONArray a = o.optJSONArray("choices");
        if (a != null) {
            Iterable $this$map$iv = RangesKt.until(0, a.length());
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            Iterator<Integer> it = $this$map$iv.iterator();
            while (it.hasNext()) {
                int item$iv$iv = ((IntIterator) it).nextInt();
                destination$iv$iv.add(a.opt(item$iv$iv).toString());
            }
            emptyList = (List) destination$iv$iv;
        } else {
            emptyList = CollectionsKt.emptyList();
        }
        this.choices = emptyList;
        this.step = Companion.num(o, "step");
        this.minimum = Companion.num(o, "minimum");
        this.maximum = Companion.num(o, "maximum");
        this.unit = Companion.str(o, "unit");
        this.available = o.optBoolean("available", false);
        this.reason = Companion.str(o, "reason");
        this.page = Companion.str(o, "page");
        this.action = o.optBoolean("action", false);
        this.confirm = o.optBoolean("confirm", false);
        this.repairValue = Companion.str(o, "repairValue");
        this.defaultValue = Companion.str(o, "defaultValue");
        this.resetAvailable = o.optBoolean("resetAvailable", false);
        this.laneStrength = Intrinsics.areEqual(Companion.str(o, "displayScale"), "lane-strength");
        this.heading = o.optBoolean("heading", false) || StringsKt.startsWith$default(Companion.str(o, "key"), "heading:", false, 2, (Object) null);
        this.coordinate = Companion.str(o, "coordinate");
        this.previewSound = Companion.str(o, "previewSound");
        if (this.heading) {
            control = Control.READOUT;
        } else {
            if (this.page.length() > 0) {
                control = Control.GROUP;
            } else {
                if (!this.confirm) {
                    if (!(this.repairValue.length() > 0)) {
                        control = (this.choices.size() == 2 && this.choices.contains("Off") && this.choices.contains("On") && this.choices.contains(this.value)) ? Control.SWITCH : (bounds() == null || (StringsKt.toDoubleOrNull(this.value) == null && !(Intrinsics.areEqual(this.value, "Auto") && this.choices.contains("Auto")))) ? (this.choices.isEmpty() || !this.choices.contains(this.value)) ? Control.READOUT : Control.SELECT : Control.SLIDER;
                    }
                }
                control = Control.ACTION;
            }
        }
        this.control = control;
    }

    public final int getIndex() {
        return this.index;
    }

    public final String getLabel() {
        return this.label;
    }

    public final String getValue() {
        return this.value;
    }

    public final List<String> getChoices() {
        return this.choices;
    }

    public final double getMaximum() {
        return this.maximum;
    }

    public final double getMinimum() {
        return this.minimum;
    }

    public final double getStep() {
        return this.step;
    }

    public final String getUnit() {
        return this.unit;
    }

    public final boolean getAvailable() {
        return this.available;
    }

    public final String getReason() {
        return this.reason;
    }

    public final String getPage() {
        return this.page;
    }

    public final boolean getAction() {
        return this.action;
    }

    public final boolean getConfirm() {
        return this.confirm;
    }

    public final String getRepairValue() {
        return this.repairValue;
    }

    public final String getDefaultValue() {
        return this.defaultValue;
    }

    public final boolean getResetAvailable() {
        return this.resetAvailable;
    }

    public final boolean getLaneStrength() {
        return this.laneStrength;
    }

    public final boolean getHeading() {
        return this.heading;
    }

    public final String getCoordinate() {
        return this.coordinate;
    }

    public final String getPreviewSound() {
        return this.previewSound;
    }

    /* compiled from: GalaxyRows.kt */
    public enum Control {
        GROUP,
        ACTION,
        SWITCH,
        SLIDER,
        SELECT,
        READOUT;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries($VALUES);

        public static EnumEntries<Control> getEntries() {
            return $ENTRIES;
        }
    }

    public final Control getControl() {
        return this.control;
    }

    public final boolean getEditable() {
        if (!this.available) {
            return false;
        }
        if (!this.action) {
            if (!(this.page.length() > 0)) {
                return false;
            }
        }
        return true;
    }

    /* compiled from: GalaxyRows.kt */
    public static final class Bounds {
        private final boolean auto;
        private final double max;
        private final double min;
        private final double step;

        public Bounds(double min, double max, double step, boolean auto) {
            this.min = min;
            this.max = max;
            this.step = step;
            this.auto = auto;
        }

        public final boolean getAuto() {
            return this.auto;
        }

        public final double getMax() {
            return this.max;
        }

        public final double getMin() {
            return this.min;
        }

        public final double getStep() {
            return this.step;
        }
    }

    public final Bounds bounds() {
        double min = this.minimum;
        double max = this.maximum;
        double st = this.step;
        if (this.laneStrength && min < 100.0d && max > 100.0d) {
            min = 1.0d;
            max = 10.0d;
            st = this.step > 0.0d ? 1.0d : 0.0d;
        }
        if (!((Double.isInfinite(min) || Double.isNaN(min)) ? false : true)) {
            return null;
        }
        if (!((Double.isInfinite(max) || Double.isNaN(max)) ? false : true)) {
            return null;
        }
        if (!((Double.isInfinite(st) || Double.isNaN(st)) ? false : true) || max <= min || st <= 0.0d) {
            return null;
        }
        return new Bounds(min, max, st, this.choices.contains("Auto"));
    }

    public static /* synthetic */ String shown$default(GalaxyRow galaxyRow, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = galaxyRow.value;
        }
        return galaxyRow.shown(str);
    }

    public final String shown(String v) {
        double d;
        Intrinsics.checkNotNullParameter(v, "v");
        if (!this.laneStrength) {
            return v;
        }
        Double doubleOrNull = StringsKt.toDoubleOrNull(v);
        if (doubleOrNull == null) {
            return v;
        }
        double raw = doubleOrNull.doubleValue();
        if (this.minimum >= 100.0d || this.maximum <= 100.0d) {
            return v;
        }
        if (raw <= 100.0d) {
            d = 1 + (((raw - this.minimum) * 4) / (100 - this.minimum));
        } else {
            double d2 = 5;
            double d3 = 100;
            d = d2 + (((raw - d3) * d2) / (this.maximum - d3));
        }
        double d4 = 100;
        return Companion.fmt(Math.rint(d * d4) / d4);
    }

    public final String saved(double shownValue) {
        double d;
        if (!this.laneStrength) {
            return Companion.fmt(shownValue);
        }
        if (shownValue < 1.0d || shownValue > 10.0d) {
            return null;
        }
        Companion companion = Companion;
        if (shownValue <= 5.0d) {
            d = this.minimum + (((shownValue - 1) * (100 - this.minimum)) / 4);
        } else {
            double d2 = 100;
            double d3 = 5;
            d = d2 + (((shownValue - d3) * (this.maximum - d2)) / d3);
        }
        return companion.fmt(Math.round(d));
    }

    public final String withUnit(String v) {
        String raw;
        Intrinsics.checkNotNullParameter(v, "v");
        if (!this.laneStrength) {
            if (!Intrinsics.areEqual(v, "Auto")) {
                if (!(this.unit.length() == 0)) {
                    return v + " " + this.unit;
                }
            }
            return v;
        }
        Double doubleOrNull = StringsKt.toDoubleOrNull(v);
        if (doubleOrNull != null) {
            double it = doubleOrNull.doubleValue();
            raw = saved(it);
        } else {
            raw = null;
        }
        return raw != null ? v + " of 10 (" + raw + "%)" : v;
    }

    public final String getShownReason() {
        if (!this.laneStrength || !this.available || this.minimum >= 100.0d || this.maximum <= 100.0d) {
            return this.reason;
        }
        return "1–10; 5 is normal (100%). Lower values reduce centering, higher values strengthen it (10 = " + Companion.fmt(this.maximum) + "%).";
    }

    public final boolean getShowDefault() {
        if (this.resetAvailable) {
            if (!(this.defaultValue.length() == 0)) {
                if (!(this.page.length() > 0)) {
                    Double a = StringsKt.toDoubleOrNull(shown(this.value));
                    Double b = StringsKt.toDoubleOrNull(shown(this.defaultValue));
                    if (a == null || b == null) {
                        if (Intrinsics.areEqual(shown(this.value), shown(this.defaultValue))) {
                            return false;
                        }
                    } else if (Intrinsics.areEqual(a, b)) {
                        return false;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    /* compiled from: GalaxyRows.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final String str(JSONObject o, String k) {
            if (!o.has(k) || o.isNull(k)) {
                return "";
            }
            Object v = o.opt(k);
            return (!(v instanceof Number) || (v instanceof Integer) || (v instanceof Long)) ? v.toString() : GalaxyRow.Companion.fmt(((Number) v).doubleValue());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final double num(JSONObject o, String k) {
            if (!o.has(k) || o.isNull(k)) {
                return Double.NaN;
            }
            return o.optDouble(k, Double.NaN);
        }

        public final String fmt(double v) {
            if ((v == Math.floor(v)) && Math.abs(v) < 1.0E15d) {
                return String.valueOf((long) v);
            }
            String format = String.format(Locale.US, "%.8f", Arrays.copyOf(new Object[]{Double.valueOf(v)}, 1));
            Intrinsics.checkNotNullExpressionValue(format, "format(...)");
            return StringsKt.trimEnd(StringsKt.trimEnd(format, '0'), '.');
        }

        public final double snap(double v, Bounds b) {
            Intrinsics.checkNotNullParameter(b, "b");
            int last = (int) Math.floor(((b.getMax() - b.getMin()) / b.getStep()) + 1.0E-9d);
            if (Math.rint((b.getMin() + (last * b.getStep())) * 1.0E8d) / 1.0E8d > b.getMax()) {
                last--;
            }
            int i = RangesKt.coerceIn((int) Math.rint((v - b.getMin()) / b.getStep()), 0, last);
            return Math.rint((b.getMin() + (i * b.getStep())) * 1.0E8d) / 1.0E8d;
        }

        public final boolean isPipCrop(GalaxyRow r) {
            Intrinsics.checkNotNullParameter(r, "r");
            return SetsKt.setOf((Object[]) new String[]{"Visual camera crop editor", "Camera crop size", "Restore default camera crop"}).contains(r.getLabel()) || new Regex("^Vehicle (left|right) crop").containsMatchIn(r.getLabel());
        }

        public final List<GalaxyRow> visible(String page, List<GalaxyRow> rows) {
            Intrinsics.checkNotNullParameter(page, "page");
            Intrinsics.checkNotNullParameter(rows, "rows");
            List<GalaxyRow> $this$filter$iv = rows;
            Collection destination$iv$iv = new ArrayList();
            for (Object element$iv$iv : $this$filter$iv) {
                GalaxyRow r = (GalaxyRow) element$iv$iv;
                boolean z = false;
                if (!GalaxyRow.Companion.isPipCrop(r)) {
                    GalaxyRow prev = (GalaxyRow) CollectionsKt.getOrNull(rows, r.getIndex() - 1);
                    z = (SetsKt.setOf((Object[]) new String[]{"sounds", "display"}).contains(page) && Intrinsics.areEqual(r.getRepairValue(), "Auto") && prev != null && prev.getChoices().contains("Auto") && prev.getControl() == Control.SLIDER && Intrinsics.areEqual(r.getLabel(), new StringBuilder().append("Use Auto ").append(prev.getLabel()).toString())) ? false : true;
                }
                if (z) {
                    destination$iv$iv.add(element$iv$iv);
                }
            }
            return (List) destination$iv$iv;
        }

        public final List<GalaxyRow> parse(JSONObject page) {
            Intrinsics.checkNotNullParameter(page, "page");
            JSONArray a = page.optJSONArray("rows");
            if (a == null) {
                a = new JSONArray();
            }
            Iterable $this$map$iv = RangesKt.until(0, a.length());
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            Iterator<Integer> it = $this$map$iv.iterator();
            while (it.hasNext()) {
                int item$iv$iv = ((IntIterator) it).nextInt();
                JSONObject jSONObject = a.getJSONObject(item$iv$iv);
                Intrinsics.checkNotNullExpressionValue(jSONObject, "getJSONObject(...)");
                destination$iv$iv.add(new GalaxyRow(item$iv$iv, jSONObject));
            }
            return (List) destination$iv$iv;
        }
    }
}
