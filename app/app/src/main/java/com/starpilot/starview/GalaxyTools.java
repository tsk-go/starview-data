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
    private static final Set<String> PLACED_ELSEWHERE = SetsKt.setOf(new String[]{"/theme_maker", "/cameras", "/vehicle", "/device-preferences", "/system"});
    private static final List<Tool> ALWAYS = CollectionsKt.listOf(new Tool[]{new Tool("Overview", "/"), new Tool("Recordings", "/recordings")});
    private static final List<Tool> DEFAULT = CollectionsKt.listOf(new Tool[]{new Tool("Android Auto", "/android-auto"), new Tool("Bluetooth", "/bluetooth"), new Tool("Galaxy Access & Install", "/galaxy"), new Tool("Logs & Diagnostics", "/logs"), new Tool("Model Manager", "/manage_models"), new Tool("Model Laboratory", "/model_laboratory"), new Tool("Navigation & Maps", "/navigation"), new Tool("Plots & Analysis", "/tuning/plots")});

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

    public final List<Tool> parse(String text) {
        Object obj;
        Intrinsics.checkNotNullParameter(text, "text");
        try {
            JSONArray a = new JSONObject(text).getJSONArray("tools");
            Iterable $this$map$iv = RangesKt.until(0, a.length());
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            Iterator<Integer> it = $this$map$iv.iterator();
            while (it.hasNext()) {
                int item$iv$iv = ((IntIterator) it).nextInt();
                destination$iv$iv.add(a.getJSONObject(item$iv$iv));
            }
            Collection destination$iv$iv2 = new ArrayList();
            for (Object element$iv$iv : (List) destination$iv$iv) {
                JSONObject it2 = (JSONObject) element$iv$iv;
                String optString = it2.optString("path");
                Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
                if (StringsKt.startsWith(optString, "/", false)) {
                    String optString2 = it2.optString("name");
                    Intrinsics.checkNotNullExpressionValue(optString2, "optString(...)");
                    if (!StringsKt.isBlank(optString2)) {
                        destination$iv$iv2.add(element$iv$iv);
                    }
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
            obj = KtCompat.resultOf(CollectionsKt.sortedWith((List) destination$iv$iv3, new Comparator() { // from class: com.starpilot.starview.GalaxyTools$parse$lambda$4$$inlined$sortedBy$1
                @Override // java.util.Comparator
                public final int compare(Object t, Object t2) {
                    GalaxyTools.Tool it4 = (GalaxyTools.Tool) t;
                    String lowerCase = it4.getName().toLowerCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                    GalaxyTools.Tool it5 = (GalaxyTools.Tool) t2;
                    String lowerCase2 = it5.getName().toLowerCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue(lowerCase2, "toLowerCase(...)");
                    return ComparisonsKt.compareValues(lowerCase, lowerCase2);
                }
            }));
        } catch (Throwable th) {
            obj = KtCompat.resultOf(ResultKt.createFailure(th));
        }
        return (List) (KtCompat.isFailure(obj) ? null : obj);
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
        if (StringsKt.contains((CharSequence) path, (CharSequence) "recording", false)) {
            return R.drawable.ic_files;
        }
        if (StringsKt.contains((CharSequence) path, (CharSequence) "bluetooth", false)) {
            return R.drawable.ic_link;
        }
        if (StringsKt.contains((CharSequence) path, (CharSequence) "model", false)) {
            return R.drawable.ic_dev;
        }
        if (StringsKt.contains((CharSequence) path, (CharSequence) "navigation", false)) {
            return R.drawable.ic_pin;
        }
        if (StringsKt.contains((CharSequence) path, (CharSequence) "plots", false) || StringsKt.contains((CharSequence) path, (CharSequence) "tuning", false)) {
            return R.drawable.ic_steering;
        }
        return StringsKt.contains((CharSequence) path, (CharSequence) "logs", false) ? R.drawable.ic_terminal : StringsKt.contains((CharSequence) path, (CharSequence) "galaxy", false) ? R.drawable.ic_network : StringsKt.contains((CharSequence) path, (CharSequence) "android", false) ? R.drawable.ic_car : R.drawable.ic_tools;
    }
}
