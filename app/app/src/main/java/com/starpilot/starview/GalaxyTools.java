package com.starpilot.starview;

import android.content.Context;
import com.starpilot.starview.GalaxyTools;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: GalaxyTools.kt */
public final class GalaxyTools {
    private static final String PREFS = "galaxy_tools";
    public static final GalaxyTools INSTANCE = new GalaxyTools();
    private static final Set<String> PLACED_ELSEWHERE = SetsKt.setOf((Object[]) new String[]{"/theme_maker", "/cameras", "/vehicle", "/device-preferences", "/system"});
    private static final List<Tool> ALWAYS = CollectionsKt.listOf((Object[]) new Tool[]{new Tool("Overview", "/"), new Tool("Recordings", "/recordings")});
    private static final List<Tool> DEFAULT = CollectionsKt.listOf((Object[]) new Tool[]{new Tool("Android Auto", "/android-auto"), new Tool("Bluetooth", "/bluetooth"), new Tool("Galaxy Access & Install", "/galaxy"), new Tool("Logs & Diagnostics", "/logs"), new Tool("Model Manager", "/manage_models"), new Tool("Model Laboratory", "/model_laboratory"), new Tool("Navigation & Maps", "/navigation"), new Tool("Plots & Analysis", "/tuning/plots")});

    /* compiled from: GalaxyTools.kt */
    public static final class Tool {
        private final String name;
        private final String path;

        public Tool(String name, String path) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(path, "path");
            this.name = name;
            this.path = path;
        }

        public final String getName() {
            return this.name;
        }

        public final String getPath() {
            return this.path;
        }
    }

    private GalaxyTools() {
    }

    public final Set<String> getPLACED_ELSEWHERE() {
        return PLACED_ELSEWHERE;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x00aa A[Catch: all -> 0x0118, TryCatch #1 {all -> 0x0118, blocks: (B:3:0x0007, B:4:0x0038, B:6:0x003e, B:8:0x0051, B:9:0x0066, B:15:0x0090, B:17:0x0096, B:21:0x00aa, B:38:0x00b8, B:39:0x00d8, B:41:0x00de, B:43:0x00fe), top: B:2:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00ad A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0129  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x012b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final List<Tool> parse(String text) {
        Object obj;
        boolean z;
        Intrinsics.checkNotNullParameter(text, "text");
        try {
            Result.Companion companion = Result.Companion;
            GalaxyTools $this$parse_u24lambda_u244 = this;
            int i = 0;
            JSONArray a = new JSONObject(text).getJSONArray("tools");
            Iterable $this$map$iv = RangesKt.until(0, a.length());
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            Iterator<Integer> it = $this$map$iv.iterator();
            while (it.hasNext()) {
                int item$iv$iv = ((IntIterator) it).nextInt();
                destination$iv$iv.add(a.getJSONObject(item$iv$iv));
            }
            Iterable $this$filter$iv = (List) destination$iv$iv;
            Collection destination$iv$iv2 = new ArrayList();
            for (Object element$iv$iv : $this$filter$iv) {
                try {
                    JSONObject it2 = (JSONObject) element$iv$iv;
                    String optString = it2.optString("path");
                    Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
                    GalaxyTools $this$parse_u24lambda_u2442 = $this$parse_u24lambda_u244;
                    int i2 = i;
                    if (StringsKt.startsWith$default(optString, "/", false, 2, (Object) null)) {
                        String optString2 = it2.optString("name");
                        Intrinsics.checkNotNullExpressionValue(optString2, "optString(...)");
                        if (!StringsKt.isBlank(optString2)) {
                            z = true;
                            if (!z) {
                                destination$iv$iv2.add(element$iv$iv);
                            }
                            i = i2;
                            $this$parse_u24lambda_u244 = $this$parse_u24lambda_u2442;
                        }
                    }
                    z = false;
                    if (!z) {
                    }
                    i = i2;
                    $this$parse_u24lambda_u244 = $this$parse_u24lambda_u2442;
                } catch (Throwable th) {
                    th = th;
                    Result.Companion companion2 = Result.Companion;
                    obj = Result.m1191constructorimpl(ResultKt.createFailure(th));
                    return (List) (!Result.m1197isFailureimpl(obj) ? null : obj);
                }
            }
            Iterable $this$map$iv2 = (List) destination$iv$iv2;
            Collection destination$iv$iv3 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv2, 10));
            for (Object item$iv$iv2 : $this$map$iv2) {
                JSONObject it3 = (JSONObject) item$iv$iv2;
                String optString3 = it3.optString("name");
                Intrinsics.checkNotNullExpressionValue(optString3, "optString(...)");
                String optString4 = it3.optString("path");
                Intrinsics.checkNotNullExpressionValue(optString4, "optString(...)");
                destination$iv$iv3.add(new Tool(optString3, optString4));
            }
            Iterable $this$sortedBy$iv = (List) destination$iv$iv3;
            obj = Result.m1191constructorimpl(CollectionsKt.sortedWith($this$sortedBy$iv, new Comparator() { // from class: com.starpilot.starview.GalaxyTools$parse$lambda$4$$inlined$sortedBy$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    GalaxyTools.Tool it4 = (GalaxyTools.Tool) t;
                    String lowerCase = it4.getName().toLowerCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                    GalaxyTools.Tool it5 = (GalaxyTools.Tool) t2;
                    String lowerCase2 = it5.getName().toLowerCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue(lowerCase2, "toLowerCase(...)");
                    return ComparisonsKt.compareValues(lowerCase, lowerCase2);
                }
            }));
        } catch (Throwable th2) {
            th = th2;
        }
        return (List) (!Result.m1197isFailureimpl(obj) ? null : obj);
    }

    public final List<Tool> list(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        String it = ctx.getSharedPreferences("galaxy_tools", 0).getString("catalog", null);
        List saved = it != null ? INSTANCE.parse(it) : null;
        refresh(ctx);
        return CollectionsKt.plus((Collection) ALWAYS, (Iterable) (saved == null ? DEFAULT : saved));
    }

    private final void refresh(Context ctx) {
        Galaxy7 g = Galaxy7.Companion.current();
        if (g == null) {
            return;
        }
        final Context app = ctx.getApplicationContext();
        g.get("/data/catalog.json", new Function2() { // from class: com.starpilot.starview.GalaxyTools$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit refresh$lambda$6;
                refresh$lambda$6 = GalaxyTools.refresh$lambda$6(app, (JSONObject) obj, (String) obj2);
                return refresh$lambda$6;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit refresh$lambda$6(Context $app, JSONObject o, String str) {
        if (o != null) {
            GalaxyTools galaxyTools = INSTANCE;
            String jSONObject = o.toString();
            Intrinsics.checkNotNullExpressionValue(jSONObject, "toString(...)");
            if (galaxyTools.parse(jSONObject) != null) {
                $app.getSharedPreferences("galaxy_tools", 0).edit().putString("catalog", o.toString()).apply();
            }
        }
        return Unit.INSTANCE;
    }

    public final int icon(String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        if (Intrinsics.areEqual(path, "/")) {
            return R.drawable.ic_info;
        }
        if (StringsKt.contains$default((CharSequence) path, (CharSequence) "recording", false, 2, (Object) null)) {
            return R.drawable.ic_files;
        }
        if (StringsKt.contains$default((CharSequence) path, (CharSequence) "bluetooth", false, 2, (Object) null)) {
            return R.drawable.ic_link;
        }
        if (StringsKt.contains$default((CharSequence) path, (CharSequence) "model", false, 2, (Object) null)) {
            return R.drawable.ic_dev;
        }
        if (StringsKt.contains$default((CharSequence) path, (CharSequence) "navigation", false, 2, (Object) null)) {
            return R.drawable.ic_pin;
        }
        if (StringsKt.contains$default((CharSequence) path, (CharSequence) "plots", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) path, (CharSequence) "tuning", false, 2, (Object) null)) {
            return R.drawable.ic_steering;
        }
        return StringsKt.contains$default((CharSequence) path, (CharSequence) "logs", false, 2, (Object) null) ? R.drawable.ic_terminal : StringsKt.contains$default((CharSequence) path, (CharSequence) "galaxy", false, 2, (Object) null) ? R.drawable.ic_network : StringsKt.contains$default((CharSequence) path, (CharSequence) "android", false, 2, (Object) null) ? R.drawable.ic_car : R.drawable.ic_tools;
    }
}
