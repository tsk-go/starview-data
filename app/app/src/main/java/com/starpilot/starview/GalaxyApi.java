package com.starpilot.starview;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/* compiled from: GalaxyApi.kt */
public final class GalaxyApi {
    public static final Companion Companion = new Companion(null);
    private static final MediaType JSON = MediaType.Companion.get("application/json; charset=utf-8");
    public static final long PENDING_MS = 10000;
    private static final String TAG = "StarView/Galaxy";
    private static volatile GalaxyApi inst;
    private final File cacheFile;
    private List<Category> catalog;
    private final OkHttpClient client;
    private final Context ctx;
    private final String host;
    private String lastValuesJson;
    private final Handler main;
    private Function0<Unit> onChanged;
    private final HashMap<String, Pair<Object, Long>> pending;
    private final HashMap<String, Object> values;
    private volatile long valuesLoadedAt;
    private volatile int version;
    private volatile int writeGen;

    public GalaxyApi(Context ctx, String host) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(host, "host");
        this.ctx = ctx;
        this.host = host;
        this.client = new OkHttpClient.Builder().connectTimeout(4L, TimeUnit.SECONDS).readTimeout(12L, TimeUnit.SECONDS).build();
        this.main = new Handler(Looper.getMainLooper());
        this.cacheFile = new File(this.ctx.getFilesDir(), "galaxy_catalog.json");
        this.catalog = CollectionsKt.emptyList();
        this.values = new HashMap<>();
        this.lastValuesJson = "";
        this.pending = new HashMap<>();
    }

    private final String getBase() {
        return "http://" + this.host + ":8082";
    }

    /* compiled from: GalaxyApi.kt */
    public static final class Param {
        private final String dataType;
        private final String description;
        private final boolean isParentToggle;
        private final String key;
        private final String label;
        private final JSONObject o;
        private final List<Pair<String, String>> options;
        private final String parentKey;
        private final String tier;
        private final String uiType;
        private final String unit;
        private final String unitType;

        public Param(JSONObject o) {
            List<Pair<String, String>> emptyList;
            Intrinsics.checkNotNullParameter(o, "o");
            this.o = o;
            String optString = this.o.optString("key");
            Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
            this.key = optString;
            String optString2 = this.o.optString("label", this.key);
            Intrinsics.checkNotNullExpressionValue(optString2, "optString(...)");
            this.label = optString2;
            String optString3 = this.o.optString("description", "");
            String str = null;
            this.description = StringsKt.isBlank(optString3) ? null : optString3;
            String optString4 = this.o.optString("data_type", "");
            Intrinsics.checkNotNullExpressionValue(optString4, "optString(...)");
            this.dataType = optString4;
            String optString5 = this.o.optString("ui_type", "");
            Intrinsics.checkNotNullExpressionValue(optString5, "optString(...)");
            this.uiType = optString5;
            String optString6 = this.o.optString("parent_key", "");
            this.parentKey = StringsKt.isBlank(optString6) ? null : optString6;
            this.isParentToggle = this.o.optBoolean("is_parent_toggle", false);
            String optString7 = this.o.optString("settings_tier", "simple");
            Intrinsics.checkNotNullExpressionValue(optString7, "optString(...)");
            this.tier = optString7;
            String optString8 = this.o.optString("unit_type", "");
            if (!StringsKt.isBlank(optString8)) {
                str = optString8;
            }
            this.unitType = str;
            String optString9 = this.o.optString("unit", "");
            Intrinsics.checkNotNullExpressionValue(optString9, "optString(...)");
            this.unit = optString9;
            JSONArray a = this.o.optJSONArray("options");
            if (a != null) {
                Iterable $this$map$iv = RangesKt.until(0, a.length());
                Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                Iterator<Integer> it = $this$map$iv.iterator();
                while (it.hasNext()) {
                    int item$iv$iv = ((IntIterator) it).nextInt();
                    JSONObject e = a.getJSONObject(item$iv$iv);
                    destination$iv$iv.add(TuplesKt.to(e.opt("value").toString(), e.optString("label", e.opt("value").toString())));
                }
                emptyList = (List) destination$iv$iv;
            } else {
                emptyList = CollectionsKt.emptyList();
            }
            this.options = emptyList;
        }

        public final JSONObject getO() {
            return this.o;
        }

        public final String getKey() {
            return this.key;
        }

        public final String getLabel() {
            return this.label;
        }

        public final String getDescription() {
            return this.description;
        }

        public final String getDataType() {
            return this.dataType;
        }

        public final String getUiType() {
            return this.uiType;
        }

        public final String getParentKey() {
            return this.parentKey;
        }

        /* renamed from: isParentToggle, reason: from getter */
        public final boolean getIsParentToggle() {
            return this.isParentToggle;
        }

        public final String getTier() {
            return this.tier;
        }

        public final String getUnitType() {
            return this.unitType;
        }

        public final String getUnit() {
            return this.unit;
        }

        public final List<Pair<String, String>> getOptions() {
            return this.options;
        }

        public final double num(String name, double def) {
            Intrinsics.checkNotNullParameter(name, "name");
            return (!this.o.has(name) || this.o.isNull(name)) ? def : this.o.optDouble(name, def);
        }

        public final boolean has(String name) {
            Intrinsics.checkNotNullParameter(name, "name");
            return this.o.has(name) && !this.o.isNull(name);
        }

        public final String str(String name) {
            Intrinsics.checkNotNullParameter(name, "name");
            if (has(name)) {
                return this.o.optString(name);
            }
            return null;
        }

        public final List<String> strList(String name) {
            Intrinsics.checkNotNullParameter(name, "name");
            JSONArray a = this.o.optJSONArray(name);
            if (a == null) {
                return CollectionsKt.emptyList();
            }
            Iterable $this$map$iv = RangesKt.until(0, a.length());
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            Iterator<Integer> it = $this$map$iv.iterator();
            while (it.hasNext()) {
                int item$iv$iv = ((IntIterator) it).nextInt();
                destination$iv$iv.add(a.optString(item$iv$iv));
            }
            return (List) destination$iv$iv;
        }
    }

    /* compiled from: GalaxyApi.kt */
    public static final class Category {
        private final String icon;
        private final String name;
        private final List<Param> params;

        public Category(String name, String icon, List<Param> params) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(icon, "icon");
            Intrinsics.checkNotNullParameter(params, "params");
            this.name = name;
            this.icon = icon;
            this.params = params;
        }

        public final String getIcon() {
            return this.icon;
        }

        public final String getName() {
            return this.name;
        }

        public final List<Param> getParams() {
            return this.params;
        }
    }

    public final List<Category> getCatalog() {
        return this.catalog;
    }

    public final HashMap<String, Object> getValues() {
        return this.values;
    }

    public final long getValuesLoadedAt() {
        return this.valuesLoadedAt;
    }

    public final void setValuesLoadedAt(long j) {
        this.valuesLoadedAt = j;
    }

    public final int getVersion() {
        return this.version;
    }

    public final void setVersion(int i) {
        this.version = i;
    }

    private final String norm(Object v) {
        if (v == null) {
            return "";
        }
        String t = "1";
        if (v instanceof Boolean) {
            return ((Boolean) v).booleanValue() ? "1" : "0";
        }
        if (v instanceof Number) {
            double it = ((Number) v).doubleValue();
            String t2 = (!((it > Math.floor(it) ? 1 : (it == Math.floor(it) ? 0 : -1)) == 0) || Double.isInfinite(it)) ? String.valueOf(it) : String.valueOf((long) it);
            return t2;
        }
        String t3 = StringsKt.trim((CharSequence) v.toString()).toString();
        String lowerCase = t3.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        if (!Intrinsics.areEqual(lowerCase, "true")) {
            if (Intrinsics.areEqual(lowerCase, "false")) {
                return "0";
            }
            Double doubleOrNull = StringsKt.toDoubleOrNull(t3);
            if (doubleOrNull == null || (t = norm(Double.valueOf(doubleOrNull.doubleValue()))) == null) {
                return t3;
            }
        }
        return t;
    }

    private final void applyPending(HashMap<String, Object> m) {
        long now = System.currentTimeMillis();
        Iterator it = this.pending.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Pair<Object, Long>> e = it.next();
            Pair pair = TuplesKt.to(e.getKey(), e.getValue());
            Object component1 = pair.component1();
            Intrinsics.checkNotNullExpressionValue(component1, "component1(...)");
            String k = (String) component1;
            Object component2 = pair.component2();
            Intrinsics.checkNotNullExpressionValue(component2, "component2(...)");
            Pair pv = (Pair) component2;
            if (now - ((Number) pv.getSecond()).longValue() > 10000 || Intrinsics.areEqual(norm(m.get(k)), norm(pv.getFirst()))) {
                it.remove();
            } else {
                m.put(k, pv.getFirst());
            }
        }
    }

    public final Function0<Unit> getOnChanged() {
        return this.onChanged;
    }

    public final void setOnChanged(Function0<Unit> function0) {
        this.onChanged = function0;
    }

    private final List<Category> parseCatalog(String text) {
        JSONArray arr = new JSONArray(text);
        Iterable $this$map$iv = RangesKt.until(0, arr.length());
        int $i$f$map = 0;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        Iterator<Integer> it = $this$map$iv.iterator();
        while (it.hasNext()) {
            int item$iv$iv = ((IntIterator) it).nextInt();
            JSONObject c = arr.getJSONObject(item$iv$iv);
            JSONArray ps = c.optJSONArray("params");
            if (ps == null) {
                ps = new JSONArray();
            }
            String optString = c.optString("name");
            Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
            String optString2 = c.optString("icon");
            Intrinsics.checkNotNullExpressionValue(optString2, "optString(...)");
            JSONArray arr2 = arr;
            Iterable $this$map$iv2 = RangesKt.until(0, ps.length());
            Iterable $this$map$iv3 = $this$map$iv;
            Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv2, 10));
            Iterable $this$mapTo$iv$iv = $this$map$iv2;
            Iterator<Integer> it2 = $this$mapTo$iv$iv.iterator();
            while (it2.hasNext()) {
                int item$iv$iv2 = ((IntIterator) it2).nextInt();
                int it3 = $i$f$map;
                JSONObject jSONObject = ps.getJSONObject(item$iv$iv2);
                Intrinsics.checkNotNullExpressionValue(jSONObject, "getJSONObject(...)");
                destination$iv$iv2.add(new Param(jSONObject));
                $i$f$map = it3;
                $this$mapTo$iv$iv = $this$mapTo$iv$iv;
            }
            destination$iv$iv.add(new Category(optString, optString2, (List) destination$iv$iv2));
            $this$map$iv = $this$map$iv3;
            arr = arr2;
        }
        return (List) destination$iv$iv;
    }

    public final void loadCatalog() {
        Object m1191constructorimpl;
        if (this.catalog.isEmpty() && this.cacheFile.exists()) {
            try {
                Result.Companion companion = Result.Companion;
                GalaxyApi $this$loadCatalog_u24lambda_u246 = this;
                $this$loadCatalog_u24lambda_u246.catalog = $this$loadCatalog_u24lambda_u246.parseCatalog(FilesKt.readText($this$loadCatalog_u24lambda_u246.cacheFile, kotlin.text.Charsets.UTF_8));
                m1191constructorimpl = KtCompat.resultOf(Unit.INSTANCE);
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                m1191constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
            }
            Throwable it = KtCompat.exceptionOrNull(m1191constructorimpl);
            if (it != null) {
                Log.w("StarView/Galaxy", "cached catalog: " + it);
            }
        }
        new Thread(new Runnable() { // from class: com.starpilot.starview.GalaxyApi$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                GalaxyApi.loadCatalog$lambda$9(GalaxyApi.this);
            }
        }).start();
        if (!this.catalog.isEmpty()) {
            this.main.post(new Runnable() { // from class: com.starpilot.starview.GalaxyApi$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    GalaxyApi.loadCatalog$lambda$10(GalaxyApi.this);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void loadCatalog$lambda$9(final GalaxyApi this$0) {
        try {
            String text = this$0.get("/assets/components/tools/device_settings_layout.json");
            final List parsed = this$0.parseCatalog(text);
            if (!parsed.isEmpty()) {
                FilesKt.writeText(this$0.cacheFile, text, kotlin.text.Charsets.UTF_8);
                this$0.main.post(new Runnable() { // from class: com.starpilot.starview.GalaxyApi$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        GalaxyApi.loadCatalog$lambda$9$lambda$8(this$0, parsed);
                    }
                });
            }
        } catch (Exception e) {
            Log.w("StarView/Galaxy", "catalog: " + e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void loadCatalog$lambda$9$lambda$8(GalaxyApi this$0, List $parsed) {
        this$0.catalog = $parsed;
        Function0<Unit> function0 = this$0.onChanged;
        if (function0 != null) {
            function0.invoke();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void loadCatalog$lambda$10(GalaxyApi this$0) {
        Function0<Unit> function0 = this$0.onChanged;
        if (function0 != null) {
            function0.invoke();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void refreshValues$default(GalaxyApi galaxyApi, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            function1 = null;
        }
        galaxyApi.refreshValues(function1);
    }

    public final void refreshValues(final Function1<? super Boolean, Unit> then) {
        final int gen = this.writeGen;
        new Thread(new Runnable() { // from class: com.starpilot.starview.GalaxyApi$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                GalaxyApi.refreshValues$lambda$16(GalaxyApi.this, gen, then);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00ab  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void refreshValues$lambda$16(final GalaxyApi this$0, final int $gen, final Function1 $then) {
        Object m1191constructorimpl;
        HashMap hashMap;
        String it;
        boolean z;
        try {
            JSONObject o = new JSONObject(this$0.get("/api/params/all"));
            final HashMap m = new HashMap();
            Iterator<String> keys = o.keys();
            Intrinsics.checkNotNullExpressionValue(keys, "keys(...)");
            while (keys.hasNext()) {
                String k = keys.next();
                m.put(k, o.isNull(k) ? null : o.get(k));
            }
            Boolean live = DriveState.INSTANCE.onroad();
            if (live != null) {
                m.put("IsOnroad", live);
                m.put("IsOffroad", Boolean.valueOf(live.booleanValue() ? false : true));
            } else {
                try {
                    Result.Companion companion = Result.Companion;
                    hashMap = m;
                    it = StringsKt.trim((CharSequence) this$0.get("/api/params?key=IsOnroad")).toString();
                } catch (Throwable th) {
                    Result.Companion companion2 = Result.Companion;
                    m1191constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
                }
                if (!Intrinsics.areEqual(it, "1") && !Intrinsics.areEqual(it, "true") && !Intrinsics.areEqual(it, "True")) {
                    z = false;
                    hashMap.put("IsOnroad", Boolean.valueOf(z));
                    m.put("IsOffroad", Boolean.valueOf(Intrinsics.areEqual(m.get("IsOnroad"), (Object) true) ? false : true));
                    m1191constructorimpl = KtCompat.resultOf(Unit.INSTANCE);
                    KtCompat.box(m1191constructorimpl);
                }
                z = true;
                hashMap.put("IsOnroad", Boolean.valueOf(z));
                m.put("IsOffroad", Boolean.valueOf(Intrinsics.areEqual(m.get("IsOnroad"), (Object) true) ? false : true));
                m1191constructorimpl = KtCompat.resultOf(Unit.INSTANCE);
                KtCompat.box(m1191constructorimpl);
            }
            this$0.main.post(new Runnable() { // from class: com.starpilot.starview.GalaxyApi$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    GalaxyApi.refreshValues$lambda$16$lambda$14(this$0, $gen, $then, m);
                }
            });
        } catch (Exception e) {
            Log.w("StarView/Galaxy", "values: " + e);
            this$0.main.post(new Runnable() { // from class: com.starpilot.starview.GalaxyApi$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    GalaxyApi.refreshValues$lambda$16$lambda$15($then);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void refreshValues$lambda$16$lambda$14(GalaxyApi this$0, int $gen, Function1 $then, HashMap $m) {
        Object m1191constructorimpl;
        this$0.valuesLoadedAt = System.currentTimeMillis();
        if ($gen != this$0.writeGen) {
            if ($then != null) {
                $then.invoke(true);
                return;
            }
            return;
        }
        this$0.applyPending($m);
        try {
            Result.Companion companion = Result.Companion;
            Intrinsics.checkNotNull($m, "null cannot be cast to non-null type kotlin.collections.Map<*, *>");
            m1191constructorimpl = KtCompat.resultOf(new JSONObject($m).toString());
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            m1191constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
        }
        if (KtCompat.isFailure(m1191constructorimpl)) {
            m1191constructorimpl = "";
        }
        Intrinsics.checkNotNullExpressionValue(m1191constructorimpl, "getOrDefault(...)");
        String js = (String) m1191constructorimpl;
        boolean changed = (js.length() == 0) || !Intrinsics.areEqual(js, this$0.lastValuesJson);
        if (changed) {
            this$0.lastValuesJson = js;
            this$0.values.clear();
            this$0.values.putAll($m);
            this$0.version++;
            Function0<Unit> function0 = this$0.onChanged;
            if (function0 != null) {
                function0.invoke();
            }
        }
        if ($then != null) {
            $then.invoke(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void refreshValues$lambda$16$lambda$15(Function1 $then) {
        if ($then != null) {
            $then.invoke(false);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void put$default(GalaxyApi galaxyApi, String str, Object obj, Map map, Function1 function1, int i, Object obj2) {
        if ((i & 4) != 0) {
            map = MapsKt.emptyMap();
        }
        if ((i & 8) != 0) {
            function1 = null;
        }
        galaxyApi.put(str, obj, map, function1);
    }

    public final void put(final String key, final Object value, final Map<String, ? extends Object> extra, final Function1<? super String, Unit> then) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(extra, "extra");
        this.values.put(key, value);
        this.version++;
        this.lastValuesJson = "";
        this.writeGen++;
        this.pending.put(key, TuplesKt.to(value, Long.valueOf(System.currentTimeMillis())));
        Function0<Unit> function0 = this.onChanged;
        if (function0 != null) {
            function0.invoke();
        }
        new Thread(new Runnable() { // from class: com.starpilot.starview.GalaxyApi$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                GalaxyApi.put$lambda$24(key, value, extra, this, then);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00dd, code lost:
    
        if (r0 == null) goto L34;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void put$lambda$24(final String str, final Object obj, Map map, final GalaxyApi galaxyApi, final Function1 function1) {
        Response execute;
        Response response;
        String str2;
        String str3;
        Object m1191constructorimpl;
        T t;
        Object m1191constructorimpl2;
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        try {
            JSONObject put = new JSONObject().put("key", str).put("value", obj == null ? JSONObject.NULL : obj);
            for (Map.Entry entry : map.entrySet()) {
                put.put((String) entry.getKey(), entry.getValue());
            }
            Request.Builder url = new Request.Builder().url(galaxyApi.getBase() + "/api/params");
            RequestBody.Companion companion = RequestBody.Companion;
            String jSONObject = put.toString();
            Intrinsics.checkNotNullExpressionValue(jSONObject, "toString(...)");
            execute = galaxyApi.client.newCall(url.put(companion.create(jSONObject, JSON)).build()).execute();
            try {
                response = execute;
                ResponseBody body = response.body();
                if (body == null || (str2 = body.string()) == null) {
                    str2 = "";
                }
                str3 = str2;
            } finally {
            }
        } catch (Exception e) {
            String message = e.getMessage();
            T t2 = message;
            if (message == null) {
                t2 = e.toString();
            }
            objectRef.element = t2;
        }
        if (response.isSuccessful()) {
            try {
                Result.Companion companion2 = Result.Companion;
                m1191constructorimpl2 = KtCompat.resultOf(new JSONObject(str3).optJSONObject("updated"));
            } catch (Throwable th) {
                Result.Companion companion3 = Result.Companion;
                m1191constructorimpl2 = KtCompat.resultOf(ResultKt.createFailure(th));
            }
            if (KtCompat.isFailure(m1191constructorimpl2)) {
                m1191constructorimpl2 = null;
            }
            final JSONObject jSONObject2 = (JSONObject) m1191constructorimpl2;
            if (jSONObject2 != null) {
                Boolean.valueOf(galaxyApi.main.post(new Runnable() { // from class: com.starpilot.starview.GalaxyApi$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        GalaxyApi.put$lambda$24$lambda$22$lambda$21$lambda$20(jSONObject2, galaxyApi, str);
                    }
                }));
            }
            CloseableKt.closeFinally(execute, null);
            galaxyApi.main.post(new Runnable() { // from class: com.starpilot.starview.GalaxyApi$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    GalaxyApi.put$lambda$24$lambda$23(galaxyApi, objectRef, str, obj, function1);
                }
            });
        }
        try {
            Result.Companion companion4 = Result.Companion;
            m1191constructorimpl = KtCompat.resultOf(new JSONObject(str3).optString("error"));
        } catch (Throwable th2) {
            Result.Companion companion5 = Result.Companion;
            m1191constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th2));
        }
        if (KtCompat.isFailure(m1191constructorimpl)) {
            m1191constructorimpl = null;
        }
        String str4 = (String) m1191constructorimpl;
        if (str4 != null) {
            String str5 = str4;
            if (StringsKt.isBlank(str5)) {
                str5 = null;
            }
            String str6 = str5;
            t = str6;
        }
        t = "HTTP " + response.code();
        objectRef.element = t;
        Unit unit = Unit.INSTANCE;
        CloseableKt.closeFinally(execute, null);
        galaxyApi.main.post(new Runnable() { // from class: com.starpilot.starview.GalaxyApi$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                GalaxyApi.put$lambda$24$lambda$23(galaxyApi, objectRef, str, obj, function1);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void put$lambda$24$lambda$22$lambda$21$lambda$20(JSONObject $u, GalaxyApi this$0, String $key) {
        Iterator<String> keys = $u.keys();
        Intrinsics.checkNotNullExpressionValue(keys, "keys(...)");
        while (keys.hasNext()) {
            String k = keys.next();
            Object v = $u.isNull(k) ? null : $u.get(k);
            this$0.values.put(k, v);
            if (!Intrinsics.areEqual(k, $key)) {
                this$0.pending.remove(k);
            }
        }
        this$0.version++;
        this$0.lastValuesJson = "";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void put$lambda$24$lambda$23(GalaxyApi this$0, Ref.ObjectRef $err, String $key, Object $value, Function1 $then) {
        this$0.writeGen++;
        if ($err.element != 0) {
            this$0.pending.remove($key);
            refreshValues$default(this$0, null, 1, null);
        } else {
            this$0.pending.put($key, TuplesKt.to($value, Long.valueOf(System.currentTimeMillis())));
        }
        Function0<Unit> function0 = this$0.onChanged;
        if (function0 != null) {
            function0.invoke();
        }
        if ($then != null) {
            $then.invoke($err.element);
        }
    }

    public final String get(String path) {
        String str;
        Intrinsics.checkNotNullParameter(path, "path");
        Response execute = this.client.newCall(new Request.Builder().url(getBase() + path).build()).execute();
        try {
            Response r = execute;
            if (!r.isSuccessful()) {
                throw new IllegalStateException("HTTP " + r.code() + " " + path);
            }
            ResponseBody body = r.body();
            if (body == null || (str = body.string()) == null) {
                str = "";
            }
            CloseableKt.closeFinally(execute, null);
            return str;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(execute, th);
                throw th2;
            }
        }
    }

    public static /* synthetic */ void post$default(GalaxyApi galaxyApi, String str, JSONObject jSONObject, Function2 function2, int i, Object obj) {
        if ((i & 2) != 0) {
            jSONObject = null;
        }
        galaxyApi.post(str, jSONObject, function2);
    }

    public final void post(final String path, final JSONObject body, final Function2<? super String, ? super JSONObject, Unit> then) {
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(then, "then");
        new Thread(new Runnable() { // from class: com.starpilot.starview.GalaxyApi$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                GalaxyApi.post$lambda$30(GalaxyApi.this, path, body, then);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00ac, code lost:
    
        if (r7 == null) goto L31;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void post$lambda$30(GalaxyApi galaxyApi, String str, JSONObject jSONObject, final Function2 function2) {
        String str2;
        Object obj;
        T t;
        String optString;
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        final Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
        try {
            Request.Builder url = new Request.Builder().url(galaxyApi.getBase() + str);
            RequestBody.Companion companion = RequestBody.Companion;
            String jSONObject2 = (jSONObject == null ? new JSONObject() : jSONObject).toString();
            Intrinsics.checkNotNullExpressionValue(jSONObject2, "toString(...)");
            Response execute = galaxyApi.client.newCall(url.post(companion.create(jSONObject2, JSON)).build()).execute();
            try {
                Response response = execute;
                ResponseBody body = response.body();
                if (body == null || (str2 = body.string()) == null) {
                    str2 = "";
                }
                try {
                    Result.Companion companion2 = Result.Companion;
                    obj = KtCompat.resultOf(new JSONObject(str2));
                } catch (Throwable th) {
                    Result.Companion companion3 = Result.Companion;
                    obj = KtCompat.resultOf(ResultKt.createFailure(th));
                }
                boolean m1197isFailureimpl = KtCompat.isFailure(obj);
                T t2 = obj;
                if (m1197isFailureimpl) {
                    t2 = 0;
                }
                objectRef2.element = t2;
                if (!response.isSuccessful()) {
                    JSONObject jSONObject3 = (JSONObject) objectRef2.element;
                    if (jSONObject3 != null && (optString = jSONObject3.optString("error")) != null) {
                        String str3 = optString;
                        if (StringsKt.isBlank(str3)) {
                            str3 = null;
                        }
                        String str4 = str3;
                        t = str4;
                    }
                    t = "HTTP " + response.code();
                    objectRef.element = t;
                }
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(execute, null);
            } finally {
            }
        } catch (Exception e) {
            String message = e.getMessage();
            T t3 = message;
            if (message == null) {
                t3 = e.toString();
            }
            objectRef.element = t3;
        }
        galaxyApi.main.post(new Runnable() { // from class: com.starpilot.starview.GalaxyApi$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                GalaxyApi.post$lambda$30$lambda$29(function2, objectRef, objectRef2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void post$lambda$30$lambda$29(Function2 $then, Ref.ObjectRef $err, Ref.ObjectRef $out) {
        $then.invoke($err.element, $out.element);
    }

    public final void options(final String path, final Function1<? super List<Pair<String, String>>, Unit> then) {
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(then, "then");
        new Thread(new Runnable() { // from class: com.starpilot.starview.GalaxyApi$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                GalaxyApi.options$lambda$32(GalaxyApi.this, path, then);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void options$lambda$32(GalaxyApi this$0, String $path, final Function1 $then) {
        JSONArray arr;
        final ArrayList out = new ArrayList();
        try {
            String text = this$0.get($path);
            Object any = new JSONTokener(text).nextValue();
            if (any instanceof JSONArray) {
                arr = (JSONArray) any;
            } else if (any instanceof JSONObject) {
                arr = ((JSONObject) any).optJSONArray("options");
                if (arr == null && (arr = ((JSONObject) any).optJSONArray("items")) == null && (arr = ((JSONObject) any).optJSONArray("makes")) == null && (arr = ((JSONObject) any).optJSONArray("models")) == null) {
                    arr = new JSONArray();
                }
            } else {
                arr = new JSONArray();
            }
            int length = arr.length();
            for (int i = 0; i < length; i++) {
                Object e = arr.get(i);
                out.add(e instanceof JSONObject ? TuplesKt.to(((JSONObject) e).opt("value").toString(), ((JSONObject) e).optString("label", ((JSONObject) e).opt("value").toString())) : TuplesKt.to(e.toString(), e.toString()));
            }
        } catch (Exception e2) {
            Log.w("StarView/Galaxy", "options " + $path + ": " + e2);
        }
        this$0.main.post(new Runnable() { // from class: com.starpilot.starview.GalaxyApi$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                $then.invoke(out);
            }
        });
    }

    public final boolean bool(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        Object v = this.values.get(key);
        if (v instanceof Boolean) {
            return ((Boolean) v).booleanValue();
        }
        if (v instanceof Number) {
            if (((Number) v).intValue() != 0) {
                return true;
            }
        } else if (v instanceof String) {
            Set of = SetsKt.setOf((Object[]) new String[]{"1", "true", "yes", "on"});
            String lowerCase = StringsKt.trim((CharSequence) v).toString().toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
            return of.contains(lowerCase);
        }
        return false;
    }

    /* renamed from: double, reason: not valid java name */
    public final Double m661double(String key) {
        double d;
        Intrinsics.checkNotNullParameter(key, "key");
        Object v = this.values.get(key);
        if (v instanceof Number) {
            d = ((Number) v).doubleValue();
        } else {
            if (v instanceof String) {
                return StringsKt.toDoubleOrNull((String) v);
            }
            if (!(v instanceof Boolean)) {
                return null;
            }
            d = ((Boolean) v).booleanValue() ? 1.0d : 0.0d;
        }
        return Double.valueOf(d);
    }

    public final String string(String key) {
        String obj;
        Intrinsics.checkNotNullParameter(key, "key");
        Object obj2 = this.values.get(key);
        return (obj2 == null || (obj = obj2.toString()) == null) ? "" : obj;
    }

    public final boolean getMetric() {
        return bool("IsMetric");
    }

    /* compiled from: GalaxyApi.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final GalaxyApi current() {
            return GalaxyApi.inst;
        }

        public final GalaxyApi get(Context ctx, String host) {
            Intrinsics.checkNotNullParameter(ctx, "ctx");
            Intrinsics.checkNotNullParameter(host, "host");
            GalaxyApi i = GalaxyApi.inst;
            if (i != null && Intrinsics.areEqual(i.host, host)) {
                return i;
            }
            Context applicationContext = ctx.getApplicationContext();
            Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
            GalaxyApi it = new GalaxyApi(applicationContext, host);
            Companion companion = GalaxyApi.Companion;
            GalaxyApi.inst = it;
            return it;
        }
    }
}
