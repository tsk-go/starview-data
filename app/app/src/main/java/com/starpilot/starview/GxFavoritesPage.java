package com.starpilot.starview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import com.starpilot.starview.GalaxyApi;
import com.starpilot.starview.GxFavoritesPage;
import com.starpilot.starview.GxPage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.collections.MapsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: GxHome.kt */
public final class GxFavoritesPage extends GxPage {
    private final String actionPrefix;
    private LinearLayout content;
    private boolean isMetric;
    private boolean loading;
    private List<? extends JSONObject> options;
    private List<Slot> savedSlots;
    private boolean saving;
    private final String setSpeed;
    private List<Slot> slots;
    private Runnable speedTimer;
    private final HashMap<String, Object> values;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GxFavoritesPage(Activity act, GxHost gx) {
        super(act, gx);
        Intrinsics.checkNotNullParameter(act, "act");
        Intrinsics.checkNotNullParameter(gx, "gx");
        this.actionPrefix = "__starpilot_favorite_action__:";
        this.setSpeed = "__starpilot_controller_action__:set_speed";
        this.loading = true;
        ArrayList arrayList = new ArrayList(3);
        for (int i = 0; i < 3; i++) {
            arrayList.add(new Slot(false, false, null, null, null, 31, null));
        }
        this.slots = arrayList;
        ArrayList arrayList2 = new ArrayList(3);
        int i2 = 0;
        for (int i3 = 3; i2 < i3; i3 = 3) {
            arrayList2.add(new Slot(false, false, null, null, null, 31, null));
            i2++;
        }
        this.savedSlots = arrayList2;
        this.options = CollectionsKt.emptyList();
        this.values = new HashMap<>();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: GxHome.kt */
    static final /* data */ class Slot {
        private final boolean enabled;
        private final String key;
        private final String label;
        private final boolean showOnroad;
        private final Object value;

        public Slot() {
            this(false, false, null, null, null, 31, null);
        }

        public static /* synthetic */ Slot copy$default(Slot slot, boolean z, boolean z2, String str, Object obj, String str2, int i, Object obj2) {
            if ((i & 1) != 0) {
                z = slot.enabled;
            }
            if ((i & 2) != 0) {
                z2 = slot.showOnroad;
            }
            boolean z3 = z2;
            if ((i & 4) != 0) {
                str = slot.key;
            }
            String str3 = str;
            if ((i & 8) != 0) {
                obj = slot.value;
            }
            Object obj3 = obj;
            if ((i & 16) != 0) {
                str2 = slot.label;
            }
            return slot.copy(z, z3, str3, obj3, str2);
        }

        /* renamed from: component1, reason: from getter */
        public final boolean getEnabled() {
            return this.enabled;
        }

        /* renamed from: component2, reason: from getter */
        public final boolean getShowOnroad() {
            return this.showOnroad;
        }

        /* renamed from: component3, reason: from getter */
        public final String getKey() {
            return this.key;
        }

        /* renamed from: component4, reason: from getter */
        public final Object getValue() {
            return this.value;
        }

        /* renamed from: component5, reason: from getter */
        public final String getLabel() {
            return this.label;
        }

        public final Slot copy(boolean enabled, boolean showOnroad, String key, Object value, String label) {
            Intrinsics.checkNotNullParameter(label, "label");
            return new Slot(enabled, showOnroad, key, value, label);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Slot)) {
                return false;
            }
            Slot slot = (Slot) other;
            return this.enabled == slot.enabled && this.showOnroad == slot.showOnroad && Intrinsics.areEqual(this.key, slot.key) && Intrinsics.areEqual(this.value, slot.value) && Intrinsics.areEqual(this.label, slot.label);
        }

        public int hashCode() {
            return (((((((Boolean.hashCode(this.enabled) * 31) + Boolean.hashCode(this.showOnroad)) * 31) + (this.key == null ? 0 : this.key.hashCode())) * 31) + (this.value != null ? this.value.hashCode() : 0)) * 31) + this.label.hashCode();
        }

        public String toString() {
            return "Slot(enabled=" + this.enabled + ", showOnroad=" + this.showOnroad + ", key=" + this.key + ", value=" + this.value + ", label=" + this.label + ")";
        }

        public Slot(boolean enabled, boolean showOnroad, String key, Object value, String label) {
            Intrinsics.checkNotNullParameter(label, "label");
            this.enabled = enabled;
            this.showOnroad = showOnroad;
            this.key = key;
            this.value = value;
            this.label = label;
        }

        public /* synthetic */ Slot(boolean z, boolean z2, String str, Object obj, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? false : z, (i & 2) != 0 ? false : z2, (i & 4) != 0 ? null : str, (i & 8) != 0 ? null : obj, (i & 16) != 0 ? "" : str2);
        }

        public final boolean getEnabled() {
            return this.enabled;
        }

        public final String getKey() {
            return this.key;
        }

        public final String getLabel() {
            return this.label;
        }

        public final boolean getShowOnroad() {
            return this.showOnroad;
        }

        public final Object getValue() {
            return this.value;
        }
    }

    @Override // com.starpilot.starview.GxPage
    public void build() {
        GalaxyApi.Param param;
        String str;
        Iterable catalog;
        Object element$iv;
        GalaxyApi api = getApi();
        if (api == null || (catalog = api.getCatalog()) == null) {
            param = null;
        } else {
            Iterable $this$flatMap$iv = catalog;
            Collection destination$iv$iv = new ArrayList();
            for (Object element$iv$iv : $this$flatMap$iv) {
                Iterable list$iv$iv = ((GalaxyApi.Category) element$iv$iv).getParams();
                CollectionsKt.addAll(destination$iv$iv, list$iv$iv);
            }
            Iterable $this$firstOrNull$iv = (List) destination$iv$iv;
            Iterator it = $this$firstOrNull$iv.iterator();
            while (true) {
                if (!it.hasNext()) {
                    element$iv = null;
                    break;
                }
                element$iv = it.next();
                GalaxyApi.Param it2 = (GalaxyApi.Param) element$iv;
                String optString = it2.getO().optString("galaxy_ui_type");
                if (StringsKt.isBlank(optString)) {
                    optString = it2.getUiType();
                }
                if (Intrinsics.areEqual(optString, "favorites")) {
                    break;
                }
            }
            param = (GalaxyApi.Param) element$iv;
        }
        GalaxyApi.Param fav = param;
        GxFavoritesPage gxFavoritesPage = this;
        if (fav == null || (str = fav.getLabel()) == null) {
            str = "Favorites";
        }
        GxPage.Row r = GxPage.row$default(gxFavoritesPage, str, fav != null ? fav.getDescription() : null, null, 0, 12, null);
        r.getTitleView().setTypeface(Typeface.DEFAULT_BOLD);
        this.content = GxPage.box$default(this, null, 1, null);
        every(5000L, new Function0() { // from class: com.starpilot.starview.GxFavoritesPage$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Unit build$lambda$5;
                build$lambda$5 = GxFavoritesPage.build$lambda$5(GxFavoritesPage.this);
                return build$lambda$5;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit build$lambda$5(GxFavoritesPage this$0) {
        this$0.refreshValues();
        return Unit.INSTANCE;
    }

    @Override // com.starpilot.starview.GxPage
    public void onShow() {
        load();
    }

    @Override // com.starpilot.starview.GxPage
    public void onHide() {
        Runnable it = this.speedTimer;
        if (it != null) {
            getMain().removeCallbacks(it);
        }
        this.speedTimer = null;
    }

    private final JSONObject optionByKey(String k) {
        String str = k;
        Object obj = null;
        if (str == null || str.length() == 0) {
            return null;
        }
        Iterable $this$firstOrNull$iv = this.options;
        Iterator it = $this$firstOrNull$iv.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Object element$iv = it.next();
            JSONObject it2 = (JSONObject) element$iv;
            if (Intrinsics.areEqual(it2.optString("key"), k)) {
                obj = element$iv;
                break;
            }
        }
        return (JSONObject) obj;
    }

    private final List<JSONObject> sortOptions(JSONArray a) {
        List<JSONObject> emptyList;
        if (a == null || (emptyList = objects(a)) == null) {
            emptyList = CollectionsKt.emptyList();
        }
        final Function2 function2 = new Function2() { // from class: com.starpilot.starview.GxFavoritesPage$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                int sortOptions$lambda$10;
                sortOptions$lambda$10 = GxFavoritesPage.sortOptions$lambda$10((JSONObject) obj, (JSONObject) obj2);
                return Integer.valueOf(sortOptions$lambda$10);
            }
        };
        return CollectionsKt.sortedWith(emptyList, new Comparator() { // from class: com.starpilot.starview.GxFavoritesPage$$ExternalSyntheticLambda8
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int sortOptions$lambda$11;
                sortOptions$lambda$11 = GxFavoritesPage.sortOptions$lambda$11(Function2.this, obj, obj2);
                return sortOptions$lambda$11;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int sortOptions$lambda$10(JSONObject x, JSONObject y) {
        int gxHomeNatural;
        String optString = x.optString("label");
        if (optString.length() == 0) {
            optString = x.optString("key");
        }
        Intrinsics.checkNotNullExpressionValue(optString, "ifEmpty(...)");
        String str = optString;
        String optString2 = y.optString("label");
        if (optString2.length() == 0) {
            optString2 = y.optString("key");
        }
        Intrinsics.checkNotNullExpressionValue(optString2, "ifEmpty(...)");
        gxHomeNatural = GxHomeKt.gxHomeNatural(str, optString2);
        return gxHomeNatural;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int sortOptions$lambda$11(Function2 $tmp0, Object p0, Object p1) {
        return ((Number) $tmp0.invoke(p0, p1)).intValue();
    }

    private final List<Slot> normalizeSlots(JSONArray a) {
        boolean gxHomeTruthy;
        boolean gxHomeTruthy2;
        boolean gxHomeTruthy3;
        String str;
        boolean gxHomeTruthy4;
        ArrayList arrayList = new ArrayList(3);
        for (int i = 0; i < 3; i++) {
            arrayList.add(new Slot(false, false, null, null, null, 31, null));
        }
        ArrayList base = arrayList;
        if (a == null) {
            return base;
        }
        int min = Math.min(3, a.length());
        for (int i2 = 0; i2 < min; i2++) {
            JSONObject o = a.optJSONObject(i2);
            if (o != null) {
                Object kRaw = o.opt("key");
                gxHomeTruthy = GxHomeKt.gxHomeTruthy(kRaw);
                Object obj = null;
                String k = gxHomeTruthy ? kRaw.toString() : null;
                gxHomeTruthy2 = GxHomeKt.gxHomeTruthy(o.opt("enabled"));
                gxHomeTruthy3 = GxHomeKt.gxHomeTruthy(o.opt("show_onroad"));
                if (Intrinsics.areEqual(k, this.setSpeed) && o.has("value") && !o.isNull("value")) {
                    obj = o.opt("value");
                }
                Object obj2 = obj;
                if (k != null) {
                    Object l = o.opt("label");
                    gxHomeTruthy4 = GxHomeKt.gxHomeTruthy(l);
                    str = gxHomeTruthy4 ? l.toString() : k;
                } else {
                    str = "";
                }
                base.set(i2, new Slot(gxHomeTruthy2, gxHomeTruthy3, k, obj2, str));
            }
        }
        return base;
    }

    private final JSONObject slotJson(Slot s) {
        JSONObject put = new JSONObject().put("enabled", s.getEnabled()).put("show_onroad", s.getShowOnroad());
        Object key = s.getKey();
        if (key == null) {
            key = JSONObject.NULL;
        }
        JSONObject $this$slotJson_u24lambda_u2414 = put.put("key", key).put("label", s.getLabel());
        if (Intrinsics.areEqual(s.getKey(), this.setSpeed) && s.getValue() != null) {
            $this$slotJson_u24lambda_u2414.put("value", s.getValue());
        }
        Intrinsics.checkNotNullExpressionValue($this$slotJson_u24lambda_u2414, "apply(...)");
        return $this$slotJson_u24lambda_u2414;
    }

    private final void mergeValues(JSONObject o) {
        if (o == null) {
            return;
        }
        Iterator<String> keys = o.keys();
        Intrinsics.checkNotNullExpressionValue(keys, "keys(...)");
        while (keys.hasNext()) {
            String k = keys.next();
            this.values.put(k, o.isNull(k) ? null : o.opt(k));
        }
    }

    private final String unitLabel() {
        return this.isMetric ? "km/h" : "mph";
    }

    private final double speedValue(Slot s) {
        double gxHomeNum;
        if (s.getValue() == null) {
            return 30.0d;
        }
        gxHomeNum = GxHomeKt.gxHomeNum(s.getValue());
        return gxHomeNum;
    }

    private final boolean isSpeedSlot(Slot s) {
        return Intrinsics.areEqual(s.getKey(), this.setSpeed);
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0026, code lost:
    
        if (r0 == null) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final String controlLabel(Slot s) {
        String l;
        String gxHomeFmt;
        String optString;
        JSONObject optionByKey = optionByKey(s.getKey());
        String str = null;
        if (optionByKey != null && (optString = optionByKey.optString("label")) != null) {
            String str2 = optString;
            if (str2.length() == 0) {
                str2 = null;
            }
            l = str2;
        }
        String label = s.getLabel();
        if (!(label.length() == 0)) {
            str = label;
        }
        l = str;
        if (l == null && (l = s.getKey()) == null) {
            l = "Not configured";
        }
        if (!isSpeedSlot(s)) {
            return l;
        }
        gxHomeFmt = GxHomeKt.gxHomeFmt(speedValue(s));
        return l + " " + gxHomeFmt + " " + unitLabel();
    }

    private final boolean isActionSlot(Slot s) {
        boolean gxHomeTruthy;
        String key = s.getKey();
        if (key == null) {
            key = "";
        }
        if (!StringsKt.startsWith$default(key, this.actionPrefix, false, 2, (Object) null)) {
            JSONObject optionByKey = optionByKey(s.getKey());
            gxHomeTruthy = GxHomeKt.gxHomeTruthy(optionByKey != null ? optionByKey.opt("action") : null);
            if (!gxHomeTruthy) {
                return false;
            }
        }
        return true;
    }

    private final void load() {
        getJson("/api/favorites/slots", new Function2() { // from class: com.starpilot.starview.GxFavoritesPage$$ExternalSyntheticLambda18
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit load$lambda$17;
                load$lambda$17 = GxFavoritesPage.load$lambda$17(GxFavoritesPage.this, (String) obj, (JSONObject) obj2);
                return load$lambda$17;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit load$lambda$17(GxFavoritesPage this$0, String e, JSONObject o) {
        boolean gxHomeTruthy;
        if (e == null && o != null) {
            this$0.options = this$0.sortOptions(o.optJSONArray("options"));
            this$0.slots = this$0.normalizeSlots(o.optJSONArray("slots"));
            this$0.savedSlots = this$0.slots;
            this$0.mergeValues(o.optJSONObject("values"));
            gxHomeTruthy = GxHomeKt.gxHomeTruthy(o.opt("is_metric"));
            this$0.isMetric = gxHomeTruthy;
        } else {
            this$0.toast("Failed to load favorite slots.");
        }
        this$0.loading = false;
        this$0.render();
        return Unit.INSTANCE;
    }

    private final void refreshValues() {
        if (this.loading || this.saving) {
            return;
        }
        getJson("/api/favorites/values", new Function2() { // from class: com.starpilot.starview.GxFavoritesPage$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit refreshValues$lambda$18;
                refreshValues$lambda$18 = GxFavoritesPage.refreshValues$lambda$18(GxFavoritesPage.this, (String) obj, (JSONObject) obj2);
                return refreshValues$lambda$18;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit refreshValues$lambda$18(GxFavoritesPage this$0, String e, JSONObject o) {
        if (e == null && o != null && !this$0.saving) {
            this$0.mergeValues(o.optJSONObject("values"));
            this$0.render();
        }
        return Unit.INSTANCE;
    }

    private final void saveSlots() {
        if (this.saving) {
            return;
        }
        this.saving = true;
        render();
        JSONObject jSONObject = new JSONObject();
        JSONArray $this$saveSlots_u24lambda_u2419 = new JSONArray();
        for (Slot s : this.slots) {
            $this$saveSlots_u24lambda_u2419.put(slotJson(s));
        }
        Unit unit = Unit.INSTANCE;
        JSONObject body = jSONObject.put("slots", $this$saveSlots_u24lambda_u2419);
        GxPage.send$default(this, "PUT", "/api/favorites/slots", body, 0L, new Function2() { // from class: com.starpilot.starview.GxFavoritesPage$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit saveSlots$lambda$23;
                saveSlots$lambda$23 = GxFavoritesPage.saveSlots$lambda$23(GxFavoritesPage.this, (String) obj, (JSONObject) obj2);
                return saveSlots$lambda$23;
            }
        }, 8, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x004d, code lost:
    
        if (r0 == null) goto L25;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Unit saveSlots$lambda$23(GxFavoritesPage this$0, String e, JSONObject o) {
        String str;
        String optString;
        JSONArray it;
        if (e == null) {
            String str2 = null;
            this$0.slots = this$0.normalizeSlots(o != null ? o.optJSONArray("slots") : null);
            this$0.savedSlots = this$0.slots;
            if (o != null && (it = o.optJSONArray("options")) != null) {
                this$0.options = this$0.sortOptions(it);
            }
            this$0.mergeValues(o != null ? o.optJSONObject("values") : null);
            if (o != null && (optString = o.optString("message")) != null) {
                String str3 = optString;
                if (!StringsKt.isBlank(str3)) {
                    str2 = str3;
                }
                str = str2;
            }
            str = "Favorite slots saved.";
            this$0.toast(str);
        } else {
            this$0.slots = this$0.savedSlots;
            String str4 = e;
            if (StringsKt.isBlank(str4)) {
                str4 = "Failed to save favorite slots.";
            }
            this$0.toast(str4);
        }
        this$0.saving = false;
        this$0.render();
        return Unit.INSTANCE;
    }

    static /* synthetic */ void updateSlot$default(GxFavoritesPage gxFavoritesPage, int i, boolean z, Function1 function1, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        gxFavoritesPage.updateSlot(i, z, function1);
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x0089, code lost:
    
        if (r3 == null) goto L32;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void updateSlot(int index, boolean keyChanged, Function1<? super Slot, Slot> patch) {
        String key;
        String optString;
        int i;
        Object obj;
        boolean z;
        boolean z2;
        String str;
        Object obj2;
        if (this.saving) {
            return;
        }
        Slot prev = this.slots.get(index);
        Object s = patch.invoke(prev);
        if (keyChanged) {
            Slot slot = (Slot) s;
            if (Intrinsics.areEqual(((Slot) s).getKey(), this.setSpeed)) {
                obj2 = Intrinsics.areEqual(prev.getKey(), this.setSpeed) ? prev.getValue() : 30;
                i = 23;
                obj = null;
                z = false;
                z2 = false;
                str = null;
            } else {
                i = 23;
                obj = null;
                z = false;
                z2 = false;
                str = null;
                obj2 = null;
            }
            s = Slot.copy$default(slot, z, z2, str, obj2, null, i, obj);
        }
        Slot slot2 = (Slot) s;
        if (((Slot) s).getKey() == null) {
            key = "";
        } else {
            JSONObject optionByKey = optionByKey(((Slot) s).getKey());
            if (optionByKey != null && (optString = optionByKey.optString("label")) != null) {
                String str2 = optString;
                if (str2.length() == 0) {
                    str2 = null;
                }
                key = str2;
            }
            key = ((Slot) s).getKey();
            Intrinsics.checkNotNull(key);
        }
        Slot copy$default = Slot.copy$default(slot2, false, false, null, null, key, 15, null);
        List it = CollectionsKt.toMutableList((Collection) this.slots);
        it.set(index, copy$default);
        this.slots = it;
        saveSlots();
    }

    private final void toggleValue(final String k, boolean checked) {
        final Object previous = this.values.get(k);
        this.values.put(k, Boolean.valueOf(checked));
        GxPage.send$default(this, "PUT", "/api/params", new JSONObject().put("key", k).put("value", checked), 0L, new Function2() { // from class: com.starpilot.starview.GxFavoritesPage$$ExternalSyntheticLambda15
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit unit;
                unit = GxFavoritesPage.toggleValue$lambda$29(GxFavoritesPage.this, k, previous, (String) obj, (JSONObject) obj2);
                return unit;
            }
        }, 8, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0027, code lost:
    
        if (r1 == null) goto L16;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Unit toggleValue$lambda$29(GxFavoritesPage this$0, String $k, Object $previous, String e, JSONObject o) {
        String str;
        String optString;
        JSONObject it;
        if (e == null) {
            if (o != null && (it = o.optJSONObject("updated")) != null) {
                this$0.mergeValues(it);
            }
            if (o != null && (optString = o.optString("message")) != null) {
                String str2 = optString;
                if (StringsKt.isBlank(str2)) {
                    str2 = null;
                }
                str = str2;
            }
            str = "Parameter '" + $k + "' updated.";
            this$0.toast(str);
            GalaxyApi api = this$0.getApi();
            if (api != null) {
                GalaxyApi.refreshValues$default(api, null, 1, null);
            }
        } else {
            this$0.values.put($k, $previous);
            String str3 = e;
            if (StringsKt.isBlank(str3)) {
                str3 = "Network error — is the device reachable?";
            }
            this$0.toast(str3);
        }
        this$0.render();
        return Unit.INSTANCE;
    }

    private final void runAction(String k, Object value) {
        JSONObject body = new JSONObject().put("key", k);
        if (value != null) {
            body.put("value", value);
        }
        GxPage.send$default(this, "POST", "/api/favorites/action", body, 0L, new Function2() { // from class: com.starpilot.starview.GxFavoritesPage$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit runAction$lambda$33;
                runAction$lambda$33 = GxFavoritesPage.runAction$lambda$33(GxFavoritesPage.this, (String) obj, (JSONObject) obj2);
                return runAction$lambda$33;
            }
        }, 8, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0019, code lost:
    
        if (r0 == null) goto L11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Unit runAction$lambda$33(GxFavoritesPage this$0, String e, JSONObject o) {
        String str;
        String optString;
        if (e == null) {
            if (o != null && (optString = o.optString("message")) != null) {
                String str2 = optString;
                if (StringsKt.isBlank(str2)) {
                    str2 = null;
                }
                str = str2;
            }
            str = "Favorite action sent.";
        } else {
            String str3 = e;
            if (StringsKt.isBlank(str3)) {
                str3 = "Failed to send favorite action.";
            }
            str = str3;
        }
        this$0.toast(str);
        return Unit.INSTANCE;
    }

    private final void render() {
        boolean z;
        int i;
        char c;
        String str;
        boolean z2;
        int i2;
        boolean z3;
        LinearLayout linearLayout;
        Slot s;
        String gxHomeFmt;
        String gxHomeFmt2;
        String str2;
        boolean gxHomeTruthy;
        if (this.speedTimer != null) {
            return;
        }
        LinearLayout linearLayout2 = this.content;
        LinearLayout linearLayout3 = null;
        if (linearLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("content");
            linearLayout2 = null;
        }
        linearLayout2.removeAllViews();
        if (this.loading) {
            LinearLayout linearLayout4 = this.content;
            if (linearLayout4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("content");
            } else {
                linearLayout3 = linearLayout4;
            }
            linearLayout3.addView(empty("Loading favorite slots..."));
            return;
        }
        Iterable $this$filter$iv = CollectionsKt.withIndex(this.slots);
        Collection destination$iv$iv = new ArrayList();
        Iterator it = $this$filter$iv.iterator();
        while (true) {
            z = false;
            i = 1;
            if (!it.hasNext()) {
                break;
            }
            Object element$iv$iv = it.next();
            Slot s2 = (Slot) ((IndexedValue) element$iv$iv).component2();
            if (s2.getEnabled() && s2.getKey() != null && optionByKey(s2.getKey()) != null) {
                z = true;
            }
            if (z) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        List<IndexedValue> quick = (List) destination$iv$iv;
        String str3 = "Favorite #";
        if (!quick.isEmpty()) {
            LinearLayout $this$render_u24lambda_u2435 = new LinearLayout(getAct());
            $this$render_u24lambda_u2435.setOrientation(0);
            LinearLayout linearLayout5 = this.content;
            if (linearLayout5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("content");
                linearLayout5 = null;
            }
            linearLayout5.addView($this$render_u24lambda_u2435, CarUi.lp$default(getUi(), 0, 0, 3, null));
            int i3 = 0;
            for (IndexedValue iv : quick) {
                int n = i3;
                int i4 = i3 + 1;
                int i5 = iv.getIndex();
                final Slot s3 = (Slot) iv.component2();
                LinearLayout $this$render_u24lambda_u2436 = new LinearLayout(getAct());
                $this$render_u24lambda_u2436.setOrientation(i);
                $this$render_u24lambda_u2436.setBackground(getUi().rounded(CarUi.Companion.getCARD(), 14, Integer.valueOf(Color.rgb(50, 55, 66))));
                $this$render_u24lambda_u2436.setPadding(getUi().dp(18), getUi().dp(14), getUi().dp(18), getUi().dp(14));
                LinearLayout linearLayout6 = $this$render_u24lambda_u2436;
                LinearLayout.LayoutParams $this$render_u24lambda_u2437 = new LinearLayout.LayoutParams(0, -2, 1.0f);
                if (n < quick.size() - 1) {
                    $this$render_u24lambda_u2437.rightMargin = getUi().dp(8);
                }
                Unit unit = Unit.INSTANCE;
                $this$render_u24lambda_u2435.addView(linearLayout6, $this$render_u24lambda_u2437);
                GxPage.text$default(this, "Favorite #" + (i5 + 1), $this$render_u24lambda_u2436, 14.0f, 0, false, 24, null);
                text(controlLabel(s3), $this$render_u24lambda_u2436, 19.0f, CarUi.Companion.getTEXT(), true);
                GxFavoritesPage gxFavoritesPage = this;
                JSONObject optionByKey = optionByKey(s3.getKey());
                if (optionByKey == null || (str2 = optionByKey.optString("section")) == null) {
                    str2 = "";
                }
                GxPage.text$default(gxFavoritesPage, str2, $this$render_u24lambda_u2436, 14.0f, 0, false, 24, null);
                if (isActionSlot(s3)) {
                    TextView b = CarUi.button$default(getUi(), "Press", false, new Function0() { // from class: com.starpilot.starview.GxFavoritesPage$$ExternalSyntheticLambda20
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            Unit render$lambda$38;
                            render$lambda$38 = GxFavoritesPage.render$lambda$38(GxFavoritesPage.this, s3);
                            return render$lambda$38;
                        }
                    }, 2, null);
                    if (this.saving) {
                        b.setAlpha(0.5f);
                    }
                    LinearLayout.LayoutParams $this$render_u24lambda_u2439 = new LinearLayout.LayoutParams(-2, -2);
                    $this$render_u24lambda_u2439.topMargin = getUi().dp(6);
                    Unit unit2 = Unit.INSTANCE;
                    $this$render_u24lambda_u2436.addView(b, $this$render_u24lambda_u2439);
                } else {
                    Switch sw = getUi().bigSwitch();
                    gxHomeTruthy = GxHomeKt.gxHomeTruthy(this.values.get(s3.getKey()));
                    sw.setChecked(gxHomeTruthy);
                    sw.setEnabled(!this.saving);
                    sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.starpilot.starview.GxFavoritesPage$$ExternalSyntheticLambda21
                        @Override // android.widget.CompoundButton.OnCheckedChangeListener
                        public final void onCheckedChanged(CompoundButton compoundButton, boolean z4) {
                            GxFavoritesPage.render$lambda$40(GxFavoritesPage.this, s3, compoundButton, z4);
                        }
                    });
                    LinearLayout.LayoutParams $this$render_u24lambda_u2441 = new LinearLayout.LayoutParams(-2, -2);
                    $this$render_u24lambda_u2441.topMargin = getUi().dp(8);
                    $this$render_u24lambda_u2441.leftMargin = getUi().dp(10);
                    Unit unit3 = Unit.INSTANCE;
                    $this$render_u24lambda_u2436.addView(sw, $this$render_u24lambda_u2441);
                }
                i3 = i4;
                i = 1;
            }
        }
        int i6 = 0;
        for (Slot s4 : this.slots) {
            final int i7 = i6;
            int i8 = i6 + 1;
            String str4 = str3 + (i7 + 1);
            LinearLayout linearLayout7 = this.content;
            if (linearLayout7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("content");
                linearLayout7 = null;
            }
            header(str4, linearLayout7);
            String str5 = str3 + (i7 + 1);
            String str6 = s4.getEnabled() ? "Enabled" : "Off";
            boolean enabled = s4.getEnabled();
            LinearLayout linearLayout8 = this.content;
            if (linearLayout8 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("content");
                linearLayout8 = null;
            }
            Switch en = toggle(str5, str6, enabled, linearLayout8, new Function1() { // from class: com.starpilot.starview.GxFavoritesPage$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit render$lambda$43;
                    render$lambda$43 = GxFavoritesPage.render$lambda$43(GxFavoritesPage.this, i7, ((Boolean) obj).booleanValue());
                    return render$lambda$43;
                }
            }).component2();
            en.setEnabled(!this.saving);
            GxFavoritesPage gxFavoritesPage2 = this;
            String controlLabel = controlLabel(s4);
            LinearLayout linearLayout9 = this.content;
            if (linearLayout9 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("content");
                linearLayout9 = null;
            }
            GxPage.Row cr = GxPage.row$default(gxFavoritesPage2, controlLabel, "Control for this favorite", linearLayout9, 0, 8, null);
            TextView cb = GxPage.Row.button$default(cr, "Choose control", false, new Function0() { // from class: com.starpilot.starview.GxFavoritesPage$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit render$lambda$44;
                    render$lambda$44 = GxFavoritesPage.render$lambda$44(GxFavoritesPage.this, i7);
                    return render$lambda$44;
                }
            }, 2, null);
            if (this.saving) {
                c = 0;
                cb.setAlpha(0.5f);
            } else {
                c = 0;
            }
            if (isSpeedSlot(s4)) {
                double min = this.isMetric ? 8.0d : 5.0d;
                double max = this.isMetric ? 145.0d : 90.0d;
                String str7 = "Set speed (" + unitLabel() + ")";
                gxHomeFmt = GxHomeKt.gxHomeFmt(min);
                gxHomeFmt2 = GxHomeKt.gxHomeFmt(max);
                String str8 = gxHomeFmt + "–" + gxHomeFmt2;
                double speedValue = speedValue(s4);
                Function1<? super Double, String> function1 = new Function1() { // from class: com.starpilot.starview.GxFavoritesPage$$ExternalSyntheticLambda3
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        String access$gxHomeFmt;
                        access$gxHomeFmt = GxHomeKt.access$gxHomeFmt(((Double) obj).doubleValue());
                        return access$gxHomeFmt;
                    }
                };
                LinearLayout linearLayout10 = this.content;
                if (linearLayout10 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("content");
                    linearLayout10 = null;
                }
                str = str3;
                z3 = true;
                s = s4;
                z2 = z;
                i2 = i7;
                linearLayout = null;
                stepper(str7, str8, speedValue, min, max, 1.0d, function1, linearLayout10, new Function1() { // from class: com.starpilot.starview.GxFavoritesPage$$ExternalSyntheticLambda4
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        Unit render$lambda$49;
                        render$lambda$49 = GxFavoritesPage.render$lambda$49(GxFavoritesPage.this, i7, ((Double) obj).doubleValue());
                        return render$lambda$49;
                    }
                });
            } else {
                str = str3;
                z2 = z;
                i2 = i7;
                z3 = true;
                linearLayout = null;
                s = s4;
            }
            boolean showOnroad = s.getShowOnroad();
            LinearLayout linearLayout11 = this.content;
            if (linearLayout11 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("content");
                linearLayout11 = linearLayout;
            }
            final int i9 = i2;
            Switch on = toggle("On-Road Button (C4: tap invisible third)", null, showOnroad, linearLayout11, new Function1() { // from class: com.starpilot.starview.GxFavoritesPage$$ExternalSyntheticLambda5
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    Unit render$lambda$51;
                    render$lambda$51 = GxFavoritesPage.render$lambda$51(GxFavoritesPage.this, i9, ((Boolean) obj).booleanValue());
                    return render$lambda$51;
                }
            }).component2();
            on.setEnabled((this.saving || !s.getEnabled() || s.getKey() == null) ? z2 : z3);
            i6 = i8;
            z = z2;
            str3 = str;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit render$lambda$38(GxFavoritesPage this$0, Slot $s) {
        if (!this$0.saving) {
            String key = $s.getKey();
            Intrinsics.checkNotNull(key);
            this$0.runAction(key, $s.getValue());
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void render$lambda$40(GxFavoritesPage this$0, Slot $s, CompoundButton b, boolean v) {
        if (b.isPressed()) {
            String key = $s.getKey();
            Intrinsics.checkNotNull(key);
            this$0.toggleValue(key, v);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit render$lambda$43(GxFavoritesPage this$0, int $i, final boolean v) {
        updateSlot$default(this$0, $i, false, new Function1() { // from class: com.starpilot.starview.GxFavoritesPage$$ExternalSyntheticLambda14
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                GxFavoritesPage.Slot render$lambda$43$lambda$42;
                render$lambda$43$lambda$42 = GxFavoritesPage.render$lambda$43$lambda$42(v, (GxFavoritesPage.Slot) obj);
                return render$lambda$43$lambda$42;
            }
        }, 2, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Slot render$lambda$43$lambda$42(boolean $v, Slot it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return Slot.copy$default(it, $v, false, null, null, null, 30, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit render$lambda$44(GxFavoritesPage this$0, int $i) {
        if (!this$0.loading && !this$0.saving) {
            this$0.chooseControl($i);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit render$lambda$49(final GxFavoritesPage this$0, final int $i, final double v) {
        if (this$0.saving) {
            return Unit.INSTANCE;
        }
        Runnable it = this$0.speedTimer;
        if (it != null) {
            this$0.getMain().removeCallbacks(it);
        }
        Runnable r = new Runnable() { // from class: com.starpilot.starview.GxFavoritesPage$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                GxFavoritesPage.render$lambda$49$lambda$48(GxFavoritesPage.this, $i, v);
            }
        };
        this$0.speedTimer = r;
        this$0.getMain().postDelayed(r, 900L);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void render$lambda$49$lambda$48(GxFavoritesPage this$0, int $i, final double $v) {
        this$0.speedTimer = null;
        updateSlot$default(this$0, $i, false, new Function1() { // from class: com.starpilot.starview.GxFavoritesPage$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                GxFavoritesPage.Slot render$lambda$49$lambda$48$lambda$47;
                render$lambda$49$lambda$48$lambda$47 = GxFavoritesPage.render$lambda$49$lambda$48$lambda$47($v, (GxFavoritesPage.Slot) obj);
                return render$lambda$49$lambda$48$lambda$47;
            }
        }, 2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Slot render$lambda$49$lambda$48$lambda$47(double $v, Slot it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return Slot.copy$default(it, false, false, null, Long.valueOf((long) $v), null, 23, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit render$lambda$51(GxFavoritesPage this$0, int $i, final boolean v) {
        updateSlot$default(this$0, $i, false, new Function1() { // from class: com.starpilot.starview.GxFavoritesPage$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                GxFavoritesPage.Slot render$lambda$51$lambda$50;
                render$lambda$51$lambda$50 = GxFavoritesPage.render$lambda$51$lambda$50(v, (GxFavoritesPage.Slot) obj);
                return render$lambda$51$lambda$50;
            }
        }, 2, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Slot render$lambda$51$lambda$50(boolean $v, Slot it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return Slot.copy$default(it, false, $v, null, null, null, 29, null);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:10:0x002c, code lost:
    
        if (r2.equals("ExperimentalMode") == false) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0054, code lost:
    
        r5 = "LongitudinalControlMode";
     */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x0035, code lost:
    
        if (r2.equals("__starpilot_favorite_action__:toggle_traffic_mode") == false) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x0041, code lost:
    
        r5 = "CustomPersonalities";
     */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x003e, code lost:
    
        if (r2.equals("__starpilot_controller_action__:cycle_driving_personality") == false) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x004a, code lost:
    
        if (r2.equals("ConditionalChill") == false) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:128:0x0051, code lost:
    
        if (r2.equals("ConditionalExperimental") == false) goto L29;
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    /* JADX WARN: Removed duplicated region for block: B:118:0x00ac A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00b1  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0113  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0144  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final Pair<String, List<String>> categoryOf(JSONObject o) {
        List cat;
        Object anchor;
        ListIterator iterator$iv;
        Object element$iv;
        GalaxyApi.Category category;
        GalaxyApi.Category section;
        String it;
        String str;
        Object element$iv2;
        boolean z;
        boolean z2;
        GalaxyApi api = getApi();
        if (api == null || (cat = api.getCatalog()) == null) {
            cat = CollectionsKt.emptyList();
        }
        String k = o.optString("key");
        if (k != null) {
            switch (k.hashCode()) {
                case -1223291282:
                    break;
                case -1119957410:
                    break;
                case -878798333:
                    break;
                case -370744618:
                    break;
                case 499325643:
                    break;
            }
            List $this$lastOrNull$iv = cat;
            iterator$iv = $this$lastOrNull$iv.listIterator($this$lastOrNull$iv.size());
            while (true) {
                if (iterator$iv.hasPrevious()) {
                    element$iv = null;
                } else {
                    element$iv = iterator$iv.previous();
                    GalaxyApi.Category s = (GalaxyApi.Category) element$iv;
                    Iterable $this$any$iv = s.getParams();
                    if (($this$any$iv instanceof Collection) && ((Collection) $this$any$iv).isEmpty()) {
                        z2 = false;
                    } else {
                        Iterator it2 = $this$any$iv.iterator();
                        while (true) {
                            if (it2.hasNext()) {
                                Object element$iv3 = it2.next();
                                if (Intrinsics.areEqual(((GalaxyApi.Param) element$iv3).getKey(), anchor)) {
                                    z2 = true;
                                }
                            } else {
                                z2 = false;
                            }
                        }
                    }
                    if (z2) {
                    }
                }
            }
            category = (GalaxyApi.Category) element$iv;
            if (category == null) {
                if (Intrinsics.areEqual(anchor, "LongitudinalControlMode")) {
                    List $this$lastOrNull$iv2 = cat;
                    ListIterator iterator$iv2 = $this$lastOrNull$iv2.listIterator($this$lastOrNull$iv2.size());
                    while (true) {
                        if (iterator$iv2.hasPrevious()) {
                            element$iv2 = iterator$iv2.previous();
                            GalaxyApi.Category s2 = (GalaxyApi.Category) element$iv2;
                            Iterable $this$any$iv2 = s2.getParams();
                            if (($this$any$iv2 instanceof Collection) && ((Collection) $this$any$iv2).isEmpty()) {
                                z = false;
                            } else {
                                Iterator it3 = $this$any$iv2.iterator();
                                while (true) {
                                    if (it3.hasNext()) {
                                        Object element$iv4 = it3.next();
                                        if (Intrinsics.areEqual(((GalaxyApi.Param) element$iv4).getKey(), "ConditionalExperimental")) {
                                            z = true;
                                        }
                                    } else {
                                        z = false;
                                    }
                                }
                            }
                            if (z) {
                            }
                        } else {
                            element$iv2 = null;
                        }
                    }
                    category = (GalaxyApi.Category) element$iv2;
                } else {
                    category = null;
                }
            }
            section = category;
            if (section != null) {
                if (Intrinsics.areEqual(o.optString("section"), "Actions")) {
                    str = "Controller Actions";
                } else {
                    String optString = o.optString("section");
                    if (optString.length() == 0) {
                        optString = "Other";
                    }
                    str = optString;
                }
                return TuplesKt.to(str, CollectionsKt.emptyList());
            }
            Iterable $this$associateBy$iv = section.getParams();
            int capacity$iv = RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault($this$associateBy$iv, 10)), 16);
            Map destination$iv$iv = new LinkedHashMap(capacity$iv);
            for (Object element$iv$iv : $this$associateBy$iv) {
                destination$iv$iv.put(((GalaxyApi.Param) element$iv$iv).getKey(), element$iv$iv);
            }
            GalaxyApi.Param own = (GalaxyApi.Param) destination$iv$iv.get(anchor);
            GalaxyApi.Param node = ((own != null && own.getIsParentToggle()) || !Intrinsics.areEqual(anchor, k)) ? own : (own == null || (it = own.getParentKey()) == null) ? null : (GalaxyApi.Param) destination$iv$iv.get(it);
            ArrayList path = new ArrayList();
            HashSet seen = new HashSet();
            while (node != null && seen.add(node.getKey())) {
                path.add(0, node.getLabel());
                String it4 = node.getParentKey();
                node = it4 != null ? (GalaxyApi.Param) destination$iv$iv.get(it4) : null;
            }
            return TuplesKt.to(section.getName(), path);
        }
        anchor = k;
        List $this$lastOrNull$iv3 = cat;
        iterator$iv = $this$lastOrNull$iv3.listIterator($this$lastOrNull$iv3.size());
        while (true) {
            if (iterator$iv.hasPrevious()) {
            }
        }
        category = (GalaxyApi.Category) element$iv;
        if (category == null) {
        }
        section = category;
        if (section != null) {
        }
    }

    private final void chooseControl(final int index) {
        String str;
        Iterable emptyList;
        List<GalaxyApi.Category> emptyList2;
        Slot slot = this.slots.get(index);
        String key = slot.getKey();
        String str2 = "";
        final String cur = key == null ? "" : key;
        LinearLayout $this$chooseControl_u24lambda_u2460 = new LinearLayout(getAct());
        $this$chooseControl_u24lambda_u2460.setOrientation(1);
        $this$chooseControl_u24lambda_u2460.setPadding(getUi().dp(20), getUi().dp(8), getUi().dp(20), 0);
        $this$chooseControl_u24lambda_u2460.setBackgroundColor(CarUi.Companion.getBG());
        JSONObject selected = optionByKey(slot.getKey());
        TextView $this$chooseControl_u24lambda_u2462 = new TextView(getAct());
        if (selected == null || (str = selected.optString("label")) == null) {
            String label = slot.getLabel();
            if (label.length() == 0) {
                label = null;
            }
            str = label;
            if (str == null && (str = slot.getKey()) == null) {
                str = "Not configured";
            }
        }
        if (slot.getKey() != null && selected == null) {
            str2 = " (unavailable)";
        }
        $this$chooseControl_u24lambda_u2462.setText("Current: " + str + str2);
        $this$chooseControl_u24lambda_u2462.setTextSize(17.0f);
        $this$chooseControl_u24lambda_u2462.setTextColor(CarUi.Companion.getTEXT());
        $this$chooseControl_u24lambda_u2460.addView($this$chooseControl_u24lambda_u2462);
        final EditText $this$chooseControl_u24lambda_u2463 = new EditText(getAct());
        $this$chooseControl_u24lambda_u2463.setHint("Search by name, setting or description");
        $this$chooseControl_u24lambda_u2463.setTextSize(19.0f);
        $this$chooseControl_u24lambda_u2463.setTextColor(CarUi.Companion.getTEXT());
        $this$chooseControl_u24lambda_u2463.setHintTextColor(CarUi.Companion.getDIM());
        $this$chooseControl_u24lambda_u2463.setSingleLine();
        $this$chooseControl_u24lambda_u2460.addView($this$chooseControl_u24lambda_u2463);
        final TextView $this$chooseControl_u24lambda_u2464 = new TextView(getAct());
        $this$chooseControl_u24lambda_u2464.setTextSize(15.0f);
        $this$chooseControl_u24lambda_u2464.setTextColor(CarUi.Companion.getDIM());
        $this$chooseControl_u24lambda_u2464.setPadding(0, getUi().dp(4), 0, getUi().dp(4));
        $this$chooseControl_u24lambda_u2460.addView($this$chooseControl_u24lambda_u2464);
        final LinearLayout $this$chooseControl_u24lambda_u2465 = new LinearLayout(getAct());
        $this$chooseControl_u24lambda_u2465.setOrientation(1);
        final ScrollView $this$chooseControl_u24lambda_u2466 = new ScrollView(getAct());
        $this$chooseControl_u24lambda_u2466.addView($this$chooseControl_u24lambda_u2465);
        $this$chooseControl_u24lambda_u2460.addView($this$chooseControl_u24lambda_u2466, new LinearLayout.LayoutParams(-1, (int) (getAct().getResources().getDisplayMetrics().heightPixels * 0.55d)));
        final AlertDialog dlg = new AlertDialog.Builder(getAct()).setTitle("Favorite #" + (index + 1)).setView($this$chooseControl_u24lambda_u2460).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).create();
        final HashSet expanded = new HashSet();
        Iterable $this$associateWith$iv = this.options;
        Map result$iv = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault($this$associateWith$iv, 10)), 16));
        for (Object element$iv$iv : $this$associateWith$iv) {
            JSONObject it = (JSONObject) element$iv$iv;
            result$iv.put(element$iv$iv, categoryOf(it));
        }
        final Map cats = result$iv;
        GalaxyApi api = getApi();
        if (api == null || (emptyList = api.getCatalog()) == null) {
            emptyList = CollectionsKt.emptyList();
        }
        Iterable $this$map$iv = emptyList;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            GalaxyApi.Category it2 = (GalaxyApi.Category) item$iv$iv;
            destination$iv$iv.add(it2.getName());
        }
        final List layoutOrder = (List) destination$iv$iv;
        final HashMap $this$chooseControl_u24lambda_u2469 = new HashMap();
        int i = 0;
        int n = 0;
        GalaxyApi api2 = getApi();
        if (api2 == null || (emptyList2 = api2.getCatalog()) == null) {
            emptyList2 = CollectionsKt.emptyList();
        }
        for (GalaxyApi.Category c : emptyList2) {
            for (GalaxyApi.Param p : c.getParams()) {
                $this$chooseControl_u24lambda_u2469.put(p.getLabel(), Integer.valueOf(n));
                i = i;
                n++;
            }
        }
        $this$chooseControl_u24lambda_u2463.addTextChangedListener(new TextWatcher() { // from class: com.starpilot.starview.GxFavoritesPage$chooseControl$2
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int a, int b, int c2) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int a, int b, int c2) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                GxFavoritesPage.chooseControl$renderList($this$chooseControl_u24lambda_u2465, $this$chooseControl_u24lambda_u2463, cats, $this$chooseControl_u24lambda_u2464, this, layoutOrder, expanded, cur, dlg, index, $this$chooseControl_u24lambda_u2469);
                $this$chooseControl_u24lambda_u2466.scrollTo(0, 0);
            }
        });
        chooseControl$renderList($this$chooseControl_u24lambda_u2465, $this$chooseControl_u24lambda_u2463, cats, $this$chooseControl_u24lambda_u2464, this, layoutOrder, expanded, cur, dlg, index, $this$chooseControl_u24lambda_u2469);
        dlg.show();
    }

    private static final void chooseControl$item(final GxFavoritesPage this$0, LinearLayout list, String cur, final AlertDialog dlg, final int $index, JSONObject o, String detail) {
        final String k;
        String str;
        String dt;
        if (o == null || (k = o.optString("key")) == null) {
            k = "";
        }
        LinearLayout v = new LinearLayout(this$0.getAct());
        v.setOrientation(1);
        v.setBackground(this$0.getUi().rounded(Intrinsics.areEqual(k, cur) ? CarUi.Companion.getCARD_ON() : CarUi.Companion.getCARD(), 12, Intrinsics.areEqual(k, cur) ? Integer.valueOf(CarUi.Companion.getACCENT()) : null));
        v.setPadding(this$0.getUi().dp(16), this$0.getUi().dp(10), this$0.getUi().dp(16), this$0.getUi().dp(10));
        v.setMinimumHeight(this$0.getUi().dp(60));
        v.setClickable(true);
        TextView $this$chooseControl_u24item_u24lambda_u2471 = new TextView(this$0.getAct());
        String str2 = Intrinsics.areEqual(k, cur) ? "✓ " : "";
        if (o == null || (str = o.optString("label")) == null) {
            str = "Not configured";
        }
        $this$chooseControl_u24item_u24lambda_u2471.setText(str2 + str);
        $this$chooseControl_u24item_u24lambda_u2471.setTextSize(18.0f);
        $this$chooseControl_u24item_u24lambda_u2471.setTextColor(CarUi.Companion.getTEXT());
        $this$chooseControl_u24item_u24lambda_u2471.setTypeface(Typeface.DEFAULT_BOLD);
        v.addView($this$chooseControl_u24item_u24lambda_u2471);
        if (o == null) {
            dt = "Clear this assignment";
        } else {
            Iterable $this$filter$iv = CollectionsKt.listOf((Object[]) new String[]{detail, o.optString("description")});
            Collection destination$iv$iv = new ArrayList();
            for (Object element$iv$iv : $this$filter$iv) {
                String it = (String) element$iv$iv;
                Intrinsics.checkNotNull(it);
                if (!StringsKt.isBlank(it)) {
                    destination$iv$iv.add(element$iv$iv);
                }
            }
            dt = CollectionsKt.joinToString$default((List) destination$iv$iv, " · ", null, null, 0, null, null, 62, null);
        }
        if (!StringsKt.isBlank(dt)) {
            TextView $this$chooseControl_u24item_u24lambda_u2473 = new TextView(this$0.getAct());
            $this$chooseControl_u24item_u24lambda_u2473.setText(dt);
            $this$chooseControl_u24item_u24lambda_u2473.setTextSize(14.0f);
            $this$chooseControl_u24item_u24lambda_u2473.setTextColor(CarUi.Companion.getDIM());
            v.addView($this$chooseControl_u24item_u24lambda_u2473);
        }
        v.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.GxFavoritesPage$$ExternalSyntheticLambda19
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                GxFavoritesPage.chooseControl$item$lambda$77(GxFavoritesPage.this, k, dlg, $index, view);
            }
        });
        LinearLayout.LayoutParams $this$chooseControl_u24item_u24lambda_u2478 = new LinearLayout.LayoutParams(-1, -2);
        $this$chooseControl_u24item_u24lambda_u2478.bottomMargin = this$0.getUi().dp(6);
        Unit unit = Unit.INSTANCE;
        list.addView(v, $this$chooseControl_u24item_u24lambda_u2478);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void chooseControl$item$lambda$77(GxFavoritesPage this$0, final String $k, AlertDialog $dlg, int $index, View it) {
        if (this$0.saving || this$0.loading) {
            return;
        }
        boolean z = false;
        if ($k.length() > 0) {
            Iterable $this$none$iv = this$0.options;
            if (!($this$none$iv instanceof Collection) || !((Collection) $this$none$iv).isEmpty()) {
                Iterator it2 = $this$none$iv.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        z = true;
                        break;
                    }
                    Object element$iv = it2.next();
                    JSONObject it3 = (JSONObject) element$iv;
                    if (Intrinsics.areEqual(it3.optString("key"), $k)) {
                        break;
                    }
                }
            } else {
                z = true;
            }
            if (z) {
                return;
            }
        }
        $dlg.dismiss();
        this$0.updateSlot($index, true, new Function1() { // from class: com.starpilot.starview.GxFavoritesPage$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                GxFavoritesPage.Slot chooseControl$item$lambda$77$lambda$76;
                chooseControl$item$lambda$77$lambda$76 = GxFavoritesPage.chooseControl$item$lambda$77$lambda$76($k, (GxFavoritesPage.Slot) obj);
                return chooseControl$item$lambda$77$lambda$76;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Slot chooseControl$item$lambda$77$lambda$76(String $k, Slot it) {
        Intrinsics.checkNotNullParameter(it, "it");
        String str = $k;
        if (str.length() == 0) {
            str = null;
        }
        return Slot.copy$default(it, false, false, str, null, null, 27, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:84:0x02df  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0414 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0396 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:98:0x02e2  */
    /* JADX WARN: Type inference failed for: r13v0 */
    /* JADX WARN: Type inference failed for: r13v1 */
    /* JADX WARN: Type inference failed for: r13v12 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void chooseControl$renderList(final LinearLayout list, final EditText search, final Map<JSONObject, ? extends Pair<String, ? extends List<String>>> map, final TextView count, final GxFavoritesPage this$0, final List<String> list2, final HashSet<String> hashSet, final String cur, final AlertDialog dlg, final int $index, final HashMap<String, Integer> hashMap) {
        list.removeAllViews();
        String needle = StringsKt.trim((CharSequence) search.getText().toString()).toString().toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(needle, "toLowerCase(...)");
        final List<String> q = new ArrayList<>();
        for (String w : new Regex("\\s+").split(needle, 0)) {
            if (w.length() > 0) {
                q.add(w);
            }
        }
        // every search word somewhere in the control's label, key, descriptions, category or path
        List<JSONObject> matches = new ArrayList<>();
        for (JSONObject o : map.keySet()) {
            Pair<String, ? extends List<String>> cp = map.get(o);
            Intrinsics.checkNotNull(cp);
            String cn = cp.component1();
            List<String> path = cp.component2();
            String hay = CollectionsKt.joinToString$default(CollectionsKt.plus((Collection) CollectionsKt.listOf(new String[]{o.optString("label"), o.optString("key"), o.optString("description"), o.optString("picker_label"), o.optString("picker_description"), cn}), (Iterable) path), " ", null, null, 0, null, null, 62, null).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(hay, "toLowerCase(...)");
            boolean all = true;
            for (String w : q) {
                if (!StringsKt.contains$default((CharSequence) hay, (CharSequence) w, false, 2, (Object) null)) {
                    all = false;
                    break;
                }
            }
            if (all) {
                matches.add(o);
            }
        }
        count.setText(this$0.saving ? "Selection is currently unavailable." : matches.size() + " " + (matches.size() == 1 ? "control" : "controls"));
        chooseControl$item(this$0, list, cur, dlg, $index, null, "");
        List<String> catsOfMatches = new ArrayList<>();
        for (JSONObject o : matches) {
            Pair<String, ? extends List<String>> cp = map.get(o);
            Intrinsics.checkNotNull(cp);
            catsOfMatches.add(cp.getFirst());
        }
        Set available = CollectionsKt.toSet(catsOfMatches);
        List<String> known = new ArrayList<>();
        for (String c : list2) {
            if (available.contains(c)) {
                known.add(c);
            }
        }
        List<String> orderedCats = CollectionsKt.distinct(CollectionsKt.plus((Collection) known, (Iterable) available));
        for (final String cn2 : orderedCats) {
            List<JSONObject> items = new ArrayList<>();
            for (JSONObject o : matches) {
                Pair<String, ? extends List<String>> cp = map.get(o);
                Intrinsics.checkNotNull(cp);
                if (Intrinsics.areEqual(cp.getFirst(), cn2)) {
                    items.add(o);
                }
            }
            if (items.isEmpty()) {
                continue;
            }
            boolean open = !q.isEmpty() || hashSet.contains(cn2);
            TextView head = new TextView(this$0.getAct());
            head.setText((open ? "▾" : "▸") + "  " + cn2 + " (" + items.size() + ")");
            head.setTextSize(18.0f);
            head.setTextColor(CarUi.Companion.getACCENT());
            head.setTypeface(Typeface.DEFAULT_BOLD);
            head.setMinHeight(this$0.getUi().dp(56));
            head.setGravity(16);
            head.setPadding(this$0.getUi().dp(6), this$0.getUi().dp(6), this$0.getUi().dp(6), this$0.getUi().dp(6));
            head.setOnClickListener(new View.OnClickListener() { // from class: com.starpilot.starview.GxFavoritesPage$$ExternalSyntheticLambda13
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    GxFavoritesPage.chooseControl$renderList$lambda$86(q, hashSet, cn2, list, search, map, count, this$0, list2, cur, dlg, $index, hashMap, view);
                }
            });
            list.addView(head);
            if (!open) {
                continue;
            }
            // in a category: by the first path step's place in the settings, then by the whole path
            final Comparator<JSONObject> byFirst = new Comparator<JSONObject>() {
                private int rank(JSONObject o) {
                    Pair<String, ? extends List<String>> cp = map.get(o);
                    Intrinsics.checkNotNull(cp);
                    List<String> p = cp.getSecond();
                    if (p.isEmpty()) {
                        return -1;
                    }
                    Integer n = hashMap.get(p.get(0));
                    return n != null ? n.intValue() : Integer.MAX_VALUE;
                }

                @Override // java.util.Comparator
                public final int compare(JSONObject a, JSONObject b) {
                    return ComparisonsKt.compareValues(Integer.valueOf(rank(a)), Integer.valueOf(rank(b)));
                }
            };
            List<JSONObject> sorted = CollectionsKt.sortedWith(items, new Comparator<JSONObject>() {
                @Override // java.util.Comparator
                public final int compare(JSONObject a, JSONObject b) {
                    int c = byFirst.compare(a, b);
                    if (c != 0) {
                        return c;
                    }
                    Pair<String, ? extends List<String>> pa = map.get(a);
                    Intrinsics.checkNotNull(pa);
                    Pair<String, ? extends List<String>> pb = map.get(b);
                    Intrinsics.checkNotNull(pb);
                    return ComparisonsKt.compareValues(CollectionsKt.joinToString$default(pa.getSecond(), "/", null, null, 0, null, null, 62, null), CollectionsKt.joinToString$default(pb.getSecond(), "/", null, null, 0, null, null, 62, null));
                }
            });
            for (JSONObject o2 : sorted) {
                Pair<String, ? extends List<String>> cp = map.get(o2);
                Intrinsics.checkNotNull(cp);
                chooseControl$item(this$0, list, cur, dlg, $index, o2, CollectionsKt.joinToString$default(CollectionsKt.plus((Collection) CollectionsKt.listOf(cn2), (Iterable) cp.getSecond()), " · ", null, null, 0, null, null, 62, null));
            }
        }
        if (matches.isEmpty()) {
            TextView none = new TextView(this$0.getAct());
            none.setText("No matching controls. Try another search.");
            none.setTextSize(16.0f);
            none.setTextColor(CarUi.Companion.getDIM());
            none.setPadding(0, this$0.getUi().dp(10), 0, this$0.getUi().dp(10));
            list.addView(none);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void chooseControl$renderList$lambda$86(List $q, HashSet $expanded, String $cn, LinearLayout $list, EditText $search, Map $cats, TextView $count, GxFavoritesPage this$0, List $layoutOrder, String $cur, AlertDialog $dlg, int $index, HashMap $paramOrder, View it) {
        if ($q.isEmpty()) {
            if ($expanded.contains($cn)) {
                $expanded.remove($cn);
            } else {
                $expanded.add($cn);
            }
            chooseControl$renderList($list, $search, $cats, $count, this$0, $layoutOrder, $expanded, $cur, $dlg, $index, $paramOrder);
        }
    }
}
