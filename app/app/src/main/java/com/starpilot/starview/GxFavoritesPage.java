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
import android.content.Context;

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

    /* VF: from Vineflower (jadx: ) */
    public GxFavoritesPage(Activity var1, GxHost var2) {
        super(var1, var2);
        Intrinsics.checkNotNullParameter(var1, "act");
        Intrinsics.checkNotNullParameter(var2, "gx");
        this.actionPrefix = "__starpilot_favorite_action__:";
        this.setSpeed = "__starpilot_controller_action__:set_speed";
        this.loading = true;
        ArrayList var4 = new ArrayList(3);

        for (int var3 = 0; var3 < 3; var3++) {
            var4.add(new GxFavoritesPage.Slot(false, false, null, null, null, 31, null));
        }

        this.slots = var4;
        ArrayList var5 = new ArrayList(3);

        for (int var6 = 0; var6 < 3; var6++) {
            var5.add(new GxFavoritesPage.Slot(false, false, null, null, null, 31, null));
        }

        this.savedSlots = var5;
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
                sortOptions$lambda$11 = GxFavoritesPage.sortOptions$lambda$11(function2, obj, obj2);
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

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    private final String controlLabel(GxFavoritesPage.Slot var1) {
        String var12;
        label43: {
            JSONObject var4 = this.optionByKey(var1.getKey());
            Object var5 = null;
            boolean var3 = true;
            if (var4 != null) {
                var12 = var4.optString("label");
                if (var12 != null) {
                    var12 = var12;
                    boolean var2;
                    if (var12.length() == 0) {
                        var2 = true;
                    } else {
                        var2 = false;
                    }

                    if (var2) {
                        var12 = null;
                    }

                    String var6 = var12;
                    var12 = var6;
                    if (var6 != null) {
                        break label43;
                    }
                }
            }

            var12 = var1.getLabel();
            boolean var9;
            if (var12.length() == 0) {
                var9 = var3;
            } else {
                var9 = false;
            }

            if (var9) {
                var12 = (String)var5;
            }

            var5 = var12;
            var12 = (String)var5;
            if (var5 == null) {
                var5 = var1.getKey();
                var12 = (String)var5;
                if (var5 == null) {
                    var12 = "Not configured";
                }
            }
        }

        String var8;
        if (this.isSpeedSlot(var1)) {
            String var16 = GxHomeKt.gxHomeFmt(this.speedValue(var1));
            String var7 = this.unitLabel();
            var8 = var12 + " " + var16 + " " + var7;
        } else {
            var8 = var12;
        }

        return var8;
    }

    private final boolean isActionSlot(Slot s) {
        boolean gxHomeTruthy;
        String key = s.getKey();
        if (key == null) {
            key = "";
        }
        if (!StringsKt.startsWith(key, this.actionPrefix, false)) {
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

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public static final Unit saveSlots$lambda$23(GxFavoritesPage var0, String var1, JSONObject var2) {
        if (var1 == null) {
            Object var3 = null;
            JSONArray var4;
            if (var2 != null) {
                var4 = var2.optJSONArray("slots");
            } else {
                var4 = null;
            }

            var0.slots = var0.normalizeSlots(var4);
            var0.savedSlots = var0.slots;
            if (var2 != null) {
                JSONArray var5 = var2.optJSONArray("options");
                if (var5 != null) {
                    var0.options = var0.sortOptions(var5);
                }
            }

            JSONObject var6;
            if (var2 != null) {
                var6 = var2.optJSONObject("values");
            } else {
                var6 = null;
            }

            label36: {
                var0.mergeValues(var6);
                if (var2 != null) {
                    var1 = var2.optString("message");
                    if (var1 != null) {
                        var1 = var1;
                        if (StringsKt.isBlank(var1)) {
                            var1 = (String)var3;
                        }

                        String var11 = var1;
                        var1 = var11;
                        if (var11 != null) {
                            break label36;
                        }
                    }
                }

                var1 = "Favorite slots saved.";
            }

            var0.toast(var1);
        } else {
            var0.slots = var0.savedSlots;
            CharSequence var12 = var1;
            Object var10 = var12;
            if (StringsKt.isBlank(var12)) {
                var10 = "Failed to save favorite slots.";
            }

            var0.toast((String)var10);
        }

        var0.saving = false;
        var0.render();
        return Unit.INSTANCE;
    }

    static /* synthetic */ void updateSlot$default(GxFavoritesPage gxFavoritesPage, int i, boolean z, Function1 function1, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        gxFavoritesPage.updateSlot(i, z, function1);
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    private final void updateSlot(int var1, boolean var2, Function1<? super GxFavoritesPage.Slot, GxFavoritesPage.Slot> var3) {
        if (!this.saving) {
            GxFavoritesPage.Slot var6 = this.slots.get(var1);
            JSONObject var9 = (JSONObject)var3.invoke(var6);
            List var5 = var9;
            if (var2) {
                var2 = Intrinsics.areEqual(((GxFavoritesPage.Slot)var9).getKey(), this.setSpeed);
                var5 = (GxFavoritesPage.Slot)var9;
                if (var2) {
                    if (Intrinsics.areEqual(var6.getKey(), this.setSpeed)) {
                        var9 = var6.getValue();
                    } else {
                        var9 = 30;
                    }
                } else {
                    var9 = null;
                }

                var5 = GxFavoritesPage.Slot.copy$default((GxFavoritesPage.Slot)var5, false, false, null, var9, null, 23, null);
            }

            GxFavoritesPage.Slot var7 = (GxFavoritesPage.Slot)var5;
            if (((GxFavoritesPage.Slot)var5).getKey() == null) {
                var9 = "";
            } else {
                label39: {
                    var9 = this.optionByKey(((GxFavoritesPage.Slot)var5).getKey());
                    if (var9 != null) {
                        String var13 = var9.optString("label");
                        if (var13 != null) {
                            var var14 = var13;
                            boolean var4;
                            if (var14.length() == 0) {
                                var4 = true;
                            } else {
                                var4 = false;
                            }

                            if (var4) {
                                var14 = null;
                            }

                            String var18 = var14;
                            var9 = var18;
                            if (var18 != null) {
                                break label39;
                            }
                        }
                    }

                    var9 = ((GxFavoritesPage.Slot)var5).getKey();
                    Intrinsics.checkNotNull(var9);
                }
            }

            GxFavoritesPage.Slot var15 = GxFavoritesPage.Slot.copy$default(var7, false, false, null, null, (String)var9, 15, null);
            var5 = CollectionsKt.toMutableList(this.slots);
            var5.set(var1, var15);
            this.slots = var5;
            this.saveSlots();
        }
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

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public static final Unit toggleValue$lambda$29(GxFavoritesPage var0, String var1, Object var2, String var3, JSONObject var4) {
        if (var3 == null) {
            if (var4 != null) {
                var2 = var4.optJSONObject("updated");
                if (var2 != null) {
                    var0.mergeValues(var2);
                }
            }

            label31: {
                if (var4 != null) {
                    var2 = var4.optString("message");
                    if (var2 != null) {
                        CharSequence var12 = var2;
                        CharSequence var9 = var12;
                        if (StringsKt.isBlank(var12)) {
                            var9 = null;
                        }

                        var3 = (String)var9;
                        var2 = var3;
                        if (var3 != null) {
                            break label31;
                        }
                    }
                }

                var2 = "Parameter '" + var1 + "' updated.";
            }

            var0.toast(var2);
            GalaxyApi var5 = var0.getApi();
            if (var5 != null) {
                GalaxyApi.refreshValues$default(var5, null, 1, null);
            }
        } else {
            var0.values.put(var1, var2);
            CharSequence var11 = var3;
            Object var6 = var11;
            if (StringsKt.isBlank(var11)) {
                var6 = "Network error — is the device reachable?";
            }

            var0.toast((String)var6);
        }

        var0.render();
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

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public static final Unit runAction$lambda$33(GxFavoritesPage var0, String var1, JSONObject var2) {
        label24:
        if (var1 == null) {
            if (var2 != null) {
                var1 = var2.optString("message");
                if (var1 != null) {
                    CharSequence var7 = var1;
                    CharSequence var4 = var7;
                    if (StringsKt.isBlank(var7)) {
                        var4 = null;
                    }

                    String var8 = (String)var4;
                    var1 = var8;
                    if (var8 != null) {
                        break label24;
                    }
                }
            }

            var1 = "Favorite action sent.";
        } else {
            CharSequence var9 = var1;
            Object var6 = var9;
            if (StringsKt.isBlank(var9)) {
                var6 = "Failed to send favorite action.";
            }

            var1 = (String)var6;
        }

        var0.toast(var1);
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
                        access$gxHomeFmt = GxHomeKt.gxHomeFmt(((Double) obj).doubleValue());
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
                GxFavoritesPage.render$lambda$49$lambda$48(this$0, $i, v);
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

    /* VF: from Vineflower (jadx: Code restructure failed;Removed duplicated region;incorrect) */
    private final Pair<String, List<String>> categoryOf(JSONObject var1) {
        List var5;
        label176: {
            GalaxyApi var3 = this.getApi();
            if (var3 != null) {
                List var19 = var3.getCatalog();
                var5 = var19;
                if (var19 != null) {
                    break label176;
                }
            }

            var5 = CollectionsKt.emptyList();
        }

        String var4;
        String var6;
        label171: {
            label170: {
                label169: {
                    var6 = var1.optString("key");
                    if (var6 != null) {
                        switch (var6.hashCode()) {
                            case -1223291282:
                                if (var6.equals("ConditionalExperimental")) {
                                    break label169;
                                }
                                break;
                            case -1119957410:
                                if (var6.equals("ConditionalChill")) {
                                    break label169;
                                }
                                break;
                            case -878798333:
                                if (var6.equals("__starpilot_controller_action__:cycle_driving_personality")) {
                                    break label170;
                                }
                                break;
                            case -370744618:
                                if (var6.equals("__starpilot_favorite_action__:toggle_traffic_mode")) {
                                    break label170;
                                }
                                break;
                            case 499325643:
                                if (var6.equals("ExperimentalMode")) {
                                    break label169;
                                }
                        }
                    }

                    var4 = var6;
                    break label171;
                }

                var4 = "LongitudinalControlMode";
                break label171;
            }

            var4 = "CustomPersonalities";
        }

        ListIterator var7 = var5.listIterator(var5.size());

        boolean var2;
        Object var20;
        label155:
        do {
            if (!var7.hasPrevious()) {
                var20 = null;
                break;
            }

            var20 = (GalaxyApi.Category)var7.previous();
            var var8 = var20.getParams();
            if (var8 instanceof Collection && ((Collection)var8).isEmpty()) {
                var2 = false;
            } else {
                Iterator var31 = var8.iterator();

                while (var31.hasNext()) {
                    if (Intrinsics.areEqual(((GalaxyApi.Param)var31.next()).getKey(), var4)) {
                        var2 = true;
                        continue label155;
                    }
                }

                var2 = false;
            }
        } while (!var2);

        GalaxyApi.Category var27 = var20;
        var20 = var27;
        if (var27 == null) {
            if (Intrinsics.areEqual(var4, "LongitudinalControlMode")) {
                ListIterator var24 = var5.listIterator(var5.size());

                label134:
                do {
                    if (!var24.hasPrevious()) {
                        var20 = null;
                        break;
                    }

                    var20 = (GalaxyApi.Category)var24.previous();
                    var var28 = var20.getParams();
                    if (var28 instanceof Collection && ((Collection)var28).isEmpty()) {
                        var2 = false;
                    } else {
                        Iterator var29 = var28.iterator();

                        while (var29.hasNext()) {
                            if (Intrinsics.areEqual(((GalaxyApi.Param)var29.next()).getKey(), "ConditionalExperimental")) {
                                var2 = true;
                                continue label134;
                            }
                        }

                        var2 = false;
                    }
                } while (!var2);

                var20 = var20;
            } else {
                var20 = null;
            }
        }

        if (var20 == null) {
            String var14;
            if (Intrinsics.areEqual(var1.optString("section"), "Actions")) {
                var14 = "Controller Actions";
            } else {
                var var15 = var1.optString("section");
                if (var15.length() == 0) {
                    var2 = true;
                } else {
                    var2 = false;
                }

                if (var2) {
                    var15 = "Other";
                }

                var14 = var15;
            }

            return TuplesKt.to(var14, CollectionsKt.emptyList());
        } else {
            Iterable var9 = var20.getParams();
            Map var25 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(var9, 10)), 16));

            for (Object var30 : var9) {
                var25.put(((GalaxyApi.Param)var30).getKey(), var30);
            }

            GalaxyApi.Param var11 = (GalaxyApi.Param)var25.get(var4);
            if (var11 != null && var11.isParentToggle()) {
                var2 = true;
            } else {
                var2 = false;
            }

            label107:
            if (!var2 && Intrinsics.areEqual(var4, var6)) {
                if (var11 != null) {
                    String var12 = var11.getParentKey();
                    if (var12 != null) {
                        var11 = (GalaxyApi.Param)var25.get(var12);
                        break label107;
                    }
                }

                var11 = null;
            }

            ArrayList var26 = new ArrayList();
            HashSet var23 = new HashSet();

            while (var11 != null && var23.add(var11.getKey())) {
                var26.add(0, var11.getLabel());
                String var13 = var11.getParentKey();
                if (var13 != null) {
                    var11 = (GalaxyApi.Param)var25.get(var13);
                } else {
                    var11 = null;
                }
            }

            return TuplesKt.to(var20.getName(), var26);
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
            dt = CollectionsKt.joinToString((List) destination$iv$iv, " · ", "", "", -1, "...", null);
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
                GxFavoritesPage.chooseControl$item$lambda$77(this$0, k, dlg, $index, view);
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

    /* VF: from Vineflower (jadx: Removed duplicated region;Type inference failed;incorrect) */
    public static final void chooseControl$renderList(
        LinearLayout var0,
        EditText var1,
        Map<JSONObject, ? extends Pair<String, ? extends List<String>>> var2,
        TextView var3,
        GxFavoritesPage var4,
        List<String> var5,
        HashSet<String> var6,
        String var7,
        AlertDialog var8,
        int var9,
        HashMap<String, Integer> var10
    ) {
        var0.removeAllViews();
        String var17 = StringsKt.trim(var1.getText().toString()).toString();
        Locale var15 = Locale.ROOT;
        String var16 = "ROOT";
        Intrinsics.checkNotNullExpressionValue(var15, "ROOT");
        var17 = var17.toLowerCase(var15);
        String var32 = "toLowerCase(...)";
        Intrinsics.checkNotNullExpressionValue(var17, "toLowerCase(...)");
        CharSequence var49 = var17;
        Regex var18 = new Regex("\\s+");
        Iterable var55 = var18.split(var49, 0);
        var var50 = new ArrayList();

        for (Object var56 : var55) {
            boolean var11;
            if (((String)var56).length() > 0) {
                var11 = true;
            } else {
                var11 = false;
            }

            if (var11) {
                var50.add(var56);
            }
        }

        List var21 = var50;
        Iterable var51 = var2.keySet();
        boolean var14 = false;
        var var23 = new ArrayList();

        for (Object var24 : var51) {
            JSONObject var57 = (JSONObject)var24;
            Object var68 = var2.get(var57);
            Intrinsics.checkNotNull(var68);
            Pair var20 = (Pair)var68;
            var68 = (String)var20.component1();
            List var25 = (List)var20.component2();
            String[] var75 = new String[]{
                var57.optString("label"),
                var57.optString("key"),
                var57.optString("description"),
                var57.optString("picker_label"),
                var57.optString("picker_description"),
                (String)var68
            };
            String var58 = CollectionsKt.joinToString(CollectionsKt.plus(CollectionsKt.listOf(var75), var25), " ", "", "", -1, "...", null);
            var68 = Locale.ROOT;
            Intrinsics.checkNotNullExpressionValue(var68, var16);
            String var26 = var58.toLowerCase((Locale)var68);
            Intrinsics.checkNotNullExpressionValue(var26, var32);
            var var76 = var21;
            boolean var29;
            Iterable var34;
            if (var76 instanceof Collection && ((Collection)var76).isEmpty()) {
                var17 = var16;
                var29 = true;
                var34 = var51;
                var16 = var32;
            } else {
                Iterator var86 = var76.iterator();

                while (true) {
                    if (!var86.hasNext()) {
                        var29 = true;
                        var17 = var16;
                        var16 = var32;
                        var34 = var51;
                        break;
                    }

                    String var59 = (String)var86.next();
                    CharSequence var27 = var26;
                    CharSequence var33 = var59;
                    if (!StringsKt.contains(var27, var33, false)) {
                        var29 = false;
                        var34 = var51;
                        var16 = var32;
                        var17 = var16;
                        break;
                    }

                    var32 = var32;
                    var16 = var16;
                    var51 = var51;
                }
            }

            if (var29) {
                var23.add(var24);
            }

            var16 = var17;
            var51 = var34;
            var32 = var16;
        }

        byte var30 = 6;
        List var54 = var23;
        CharSequence var35;
        if (var4.saving) {
            var35 = "Selection is currently unavailable.";
        } else {
            int var12 = var54.size();
            String var36;
            if (var54.size() == 1) {
                var36 = "control";
            } else {
                var36 = "controls";
            }

            var35 = var12 + " " + var36;
        }

        var3.setText(var35);
        chooseControl$item(var4, var0, var7, var8, var9, null, "");
        Iterable var41 = var54;
        var var37 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var41, 10));
        Iterator var42 = var41.iterator();

        while (var42.hasNext()) {
            Object var64 = var2.get((JSONObject)var42.next());
            Intrinsics.checkNotNull(var64);
            var37.add((String)((Pair)var64).getFirst());
        }

        List var38 = var37;
        Set var39 = CollectionsKt.toSet(var38);
        Iterable var65 = var5;
        var var43 = new ArrayList();

        for (Object var66 : var65) {
            if (var39.contains((String)var66)) {
                var43.add(var66);
            }
        }

        List var44 = var43;

        for (String var74 : CollectionsKt.distinct(CollectionsKt.plus(var44, var39))) {
            Iterable var77 = var54;
            var var45 = new ArrayList();

            for (Object var81 : var77) {
                Object var84 = var2.get((JSONObject)var81);
                Intrinsics.checkNotNull(var84);
                if (Intrinsics.areEqual(((Pair)var84).getFirst(), var74)) {
                    var45.add(var81);
                }
            }

            List var82 = var45;
            if (!var82.isEmpty()) {
                boolean var31;
                if (var21.isEmpty() && !var6.contains(var74)) {
                    var31 = false;
                } else {
                    var31 = true;
                }

                TextView var79 = new TextView((Context)var4.getAct());
                if (var31) {
                    var16 = "▾";
                } else {
                    var16 = "▸";
                }

                int var13 = var82.size();
                var79.setText(var16 + "  " + var74 + " (" + var13 + ")");
                var79.setTextSize(18.0F);
                var79.setTextColor(CarUi.Companion.getACCENT());
                var79.setTypeface(Typeface.DEFAULT_BOLD);
                var79.setMinHeight(var4.getUi().dp(56));
                var79.setGravity(16);
                var79.setPadding(var4.getUi().dp(var30), var4.getUi().dp(var30), var4.getUi().dp(var30), var4.getUi().dp(var30));
                var79.setOnClickListener(
                    new GxFavoritesPage$$ExternalSyntheticLambda13(var21, var6, var74, var0, var1, var2, var3, var4, var5, var7, var8, var9, var10)
                );
                var0.addView((View)var79);
                if (var31) {
                    for (JSONObject var85 : CollectionsKt.sortedWith(
                        var82,
                        new Comparator() {
                            final Map $cats$inlined = var2;
                            final Comparator $this_thenBy = new Comparator(var2, var10) {
                            final Map $cats$inlined;
                            final HashMap $paramOrder$inlined;

                            {
                                GxFavoritesPage.this.$cats$inlined = var1;
                                GxFavoritesPage.this.$paramOrder$inlined = var2x;
                            }

                            @Override
                            public final int compare(T var1, T var2) {
                                var1 = (JSONObject)var1;
                                var1 = GxFavoritesPage.this.$cats$inlined.get(var1);
                                Intrinsics.checkNotNull(var1);
                                var1 = (List)((Pair)var1).getSecond();
                                boolean var5 = var1.isEmpty();
                                int var4 = Integer.MAX_VALUE;
                                Integer var6 = -1;
                                Comparable var10;
                                if (var5) {
                                    var10 = var6;
                                } else {
                                    Integer var11 = (Integer)GxFavoritesPage.this.$paramOrder$inlined.get(var1.get(0));
                                    int var3;
                                    if (var11 != null) {
                                        var3 = var11;
                                    } else {
                                        var3 = Integer.MAX_VALUE;
                                    }

                                    var10 = var3;
                                }

                                var2 = (JSONObject)var2;
                                var2 = GxFavoritesPage.this.$cats$inlined.get(var2);
                                Intrinsics.checkNotNull(var2);
                                var2 = (List)((Pair)var2).getSecond();
                                Comparable var15;
                                if (var2.isEmpty()) {
                                    var15 = var6;
                                } else {
                                    Integer var16 = (Integer)GxFavoritesPage.this.$paramOrder$inlined.get(var2.get(0));
                                    int var17 = var4;
                                    if (var16 != null) {
                                        var17 = var16;
                                    }

                                    var15 = var17;
                                }

                                return ComparisonsKt.compareValues((T)var10, (T)var15);
                            }
                        };

                            @Override
                            public final int compare(T var1, T var2x) {
                                int var3x = this.$this_thenBy.compare(var1, (Comparable)var2x);
                                if (var3x == 0) {
                                    var1 = (JSONObject)var1;
                                    var1 = this.$cats$inlined.get(var1);
                                    Intrinsics.checkNotNull(var1);
                                    var1 = CollectionsKt.joinToString((Iterable)((Pair)var1).getSecond(), "/", "", "", -1, "...", null);
                                    var2x = (JSONObject)var2x;
                                    var2x = this.$cats$inlined.get(var2x);
                                    Intrinsics.checkNotNull(var2x);
                                    var3x = ComparisonsKt.compareValues(
                                        var1, CollectionsKt.joinToString((Iterable)((Pair)var2x).getSecond(), "/", "", "", -1, "...", null)
                                    );
                                }

                                return var3x;
                            }
                        }
                    )) {
                        Collection var47 = CollectionsKt.listOf(var74);
                        Object var83 = var2.get(var85);
                        Intrinsics.checkNotNull(var83);
                        chooseControl$item(
                            var4,
                            var0,
                            var7,
                            var8,
                            var9,
                            var85,
                            CollectionsKt.joinToString(CollectionsKt.plus(var47, (Iterable)((Pair)var83).getSecond()), " · ", "", "", -1, "...", null)
                        );
                    }
                }
            }
        }

        if (var54.isEmpty()) {
            TextView var28 = new TextView((Context)var4.getAct());
            var28.setText("No matching controls. Try another search.");
            var28.setTextSize(16.0F);
            var28.setTextColor(CarUi.Companion.getDIM());
            var28.setPadding(0, var4.getUi().dp(10), 0, var4.getUi().dp(10));
            var0.addView((View)var28);
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
