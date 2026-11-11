package com.starpilot.starview;

import android.content.Context;
import android.content.SharedPreferences;
import com.starpilot.starview.HudLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.json.JSONObject;

/* compiled from: QuickPanel.kt */
public final class HudPresets {
    private static final Map<String, Pair<Float, Float>> COCKPIT;
    public static final HudPresets INSTANCE = new HudPresets();
    private static final Set<String> FOCUS_KEYS = SetsKt.setOf(new String[]{"speedBox", "maxCard", "slc", "navCard", "laneSuggest", "gear", "nudgeHint", "dm"});

    private HudPresets() {
    }

    static {
        Float valueOf = Float.valueOf(0.7f);
        Float valueOf2 = Float.valueOf(0.02f);
        Float valueOf3 = Float.valueOf(0.03f);
        COCKPIT = MapsKt.mapOf(TuplesKt.to("navCard", TuplesKt.to(valueOf, Float.valueOf(0.04f))), TuplesKt.to("slowCard", TuplesKt.to(valueOf, Float.valueOf(0.34f))), TuplesKt.to("gpsCard", TuplesKt.to(valueOf, Float.valueOf(0.52f))), TuplesKt.to("laneCard", TuplesKt.to(valueOf, valueOf)), TuplesKt.to("maxCard", TuplesKt.to(valueOf2, valueOf3)), TuplesKt.to("slc", TuplesKt.to(Float.valueOf(0.1f), valueOf3)), TuplesKt.to("speedBox", TuplesKt.to(valueOf2, Float.valueOf(0.8f))), TuplesKt.to("gauge", TuplesKt.to(valueOf2, Float.valueOf(0.22f))));
    }

    public final Set<String> getFOCUS_KEYS() {
        return FOCUS_KEYS;
    }

    public final String only(String layout, List<String> allKeys, Set<String> keep) {
        Object m1191constructorimpl;
        Intrinsics.checkNotNullParameter(allKeys, "allKeys");
        Intrinsics.checkNotNullParameter(keep, "keep");
        try {
            Result.Companion companion = Result.Companion;
            HudPresets hudPresets = this;
            m1191constructorimpl = KtCompat.resultOf(new JSONObject(layout == null ? "{}" : layout));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            m1191constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
        }
        JSONObject jSONObject = new JSONObject();
        if (KtCompat.isFailure(m1191constructorimpl)) {
            m1191constructorimpl = jSONObject;
        }
        JSONObject o = (JSONObject) m1191constructorimpl;
        for (String k : allKeys) {
            JSONObject it = o.optJSONObject(k);
            if (it == null) {
                it = new JSONObject().put("s", 1.0d);
                o.put(k, it);
            }
            it.put("on", keep.contains(k));
        }
        String jSONObject2 = o.toString();
        Intrinsics.checkNotNullExpressionValue(jSONObject2, "toString(...)");
        return jSONObject2;
    }

    public final String everything(String layout, List<String> allKeys) {
        Intrinsics.checkNotNullParameter(allKeys, "allKeys");
        return only(layout, allKeys, CollectionsKt.toSet(allKeys));
    }

    public final Map<String, Pair<Float, Float>> getCOCKPIT() {
        return COCKPIT;
    }

    public final String cockpit(String layout, List<String> allKeys) {
        Object m1191constructorimpl;
        Intrinsics.checkNotNullParameter(allKeys, "allKeys");
        try {
            Result.Companion companion = Result.Companion;
            HudPresets $this$cockpit_u24lambda_u242 = this;
            m1191constructorimpl = KtCompat.resultOf(new JSONObject($this$cockpit_u24lambda_u242.everything(layout, allKeys)));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            m1191constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
        }
        JSONObject jSONObject = new JSONObject();
        if (KtCompat.isFailure(m1191constructorimpl)) {
            m1191constructorimpl = jSONObject;
        }
        JSONObject o = (JSONObject) m1191constructorimpl;
        for (Map.Entry<String, Pair<Float, Float>> entry : COCKPIT.entrySet()) {
            String k = entry.getKey();
            Pair xy = entry.getValue();
            JSONObject it = o.optJSONObject(k);
            if (it == null) {
                it = new JSONObject();
                o.put(k, it);
            }
            it.put("on", true).put("x", ((Number) xy.getFirst()).floatValue()).put("y", ((Number) xy.getSecond()).floatValue()).put("cw", JSONObject.NULL).put("ch", JSONObject.NULL);
            if (!it.has("s")) {
                it.put("s", 1.0d);
            }
        }
        String jSONObject2 = o.toString();
        Intrinsics.checkNotNullExpressionValue(jSONObject2, "toString(...)");
        return jSONObject2;
    }

    private final SharedPreferences prefs(Context ctx) {
        return ctx.getSharedPreferences("starview_hud", 0);
    }

    public final Map<String, String> saved(Context ctx) {
        Object m1191constructorimpl;
        String str = "{}";
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        try {
            Result.Companion companion = Result.Companion;
            HudPresets $this$saved_u24lambda_u244 = this;
            String string = $this$saved_u24lambda_u244.prefs(ctx).getString("presets", "{}");
            if (string != null) {
                str = string;
            }
            m1191constructorimpl = KtCompat.resultOf(new JSONObject(str));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            m1191constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
        }
        if (KtCompat.isFailure(m1191constructorimpl)) {
            m1191constructorimpl = null;
        }
        JSONObject o = (JSONObject) m1191constructorimpl;
        if (o == null) {
            return MapsKt.emptyMap();
        }
        Iterator<String> keys = o.keys();
        Intrinsics.checkNotNullExpressionValue(keys, "keys(...)");
        // 2.85.6: the keys are a one-time sequence, not a list (the cast to Iterable crashed Presets)
        LinkedHashMap result$iv = new LinkedHashMap();
        while (keys.hasNext()) {
            String it = keys.next();
            result$iv.put(it, o.getString(it));
        }
        return result$iv;
    }

    public final void save(Context ctx, String name, String layout) {
        Object m1191constructorimpl;
        String str = "{}";
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(layout, "layout");
        try {
            Result.Companion companion = Result.Companion;
            HudPresets $this$save_u24lambda_u247 = this;
            String string = $this$save_u24lambda_u247.prefs(ctx).getString("presets", "{}");
            if (string != null) {
                str = string;
            }
            m1191constructorimpl = KtCompat.resultOf(new JSONObject(str));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            m1191constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
        }
        JSONObject jSONObject = new JSONObject();
        if (KtCompat.isFailure(m1191constructorimpl)) {
            m1191constructorimpl = jSONObject;
        }
        JSONObject o = (JSONObject) m1191constructorimpl;
        o.put(name, layout);
        prefs(ctx).edit().putString("presets", o.toString()).apply();
    }

    public final void delete(Context ctx, String name) {
        Object m1191constructorimpl;
        String str = "{}";
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(name, "name");
        try {
            Result.Companion companion = Result.Companion;
            HudPresets $this$delete_u24lambda_u248 = this;
            String string = $this$delete_u24lambda_u248.prefs(ctx).getString("presets", "{}");
            if (string != null) {
                str = string;
            }
            m1191constructorimpl = KtCompat.resultOf(new JSONObject(str));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            m1191constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
        }
        JSONObject jSONObject = new JSONObject();
        if (KtCompat.isFailure(m1191constructorimpl)) {
            m1191constructorimpl = jSONObject;
        }
        JSONObject o = (JSONObject) m1191constructorimpl;
        o.remove(name);
        prefs(ctx).edit().putString("presets", o.toString()).apply();
    }

    public final boolean focusOn(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return prefs(ctx).getString("before_focus", null) != null;
    }

    public final boolean toggleFocus(Context ctx, HudLayout hud) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(hud, "hud");
        String before = prefs(ctx).getString("before_focus", null);
        if (before != null) {
            hud.restore(before);
            hud.save();
            prefs(ctx).edit().remove("before_focus").apply();
            return false;
        }
        String now = hud.snapshot();
        prefs(ctx).edit().putString("before_focus", now).apply();
        Iterable $this$map$iv = hud.getItems();
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            HudLayout.Item it = (HudLayout.Item) item$iv$iv;
            destination$iv$iv.add(it.getKey());
        }
        hud.restore(only(now, (List) destination$iv$iv, FOCUS_KEYS));
        hud.save();
        return true;
    }
}
