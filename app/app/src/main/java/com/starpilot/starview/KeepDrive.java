package com.starpilot.starview;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.Regex;
import kotlin.text.RegexOption;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: QuickPanel.kt */
public final class KeepDrive {
    public static final KeepDrive INSTANCE = new KeepDrive();

    private KeepDrive() {
    }

    private final SharedPreferences prefs(Context ctx) {
        return ctx.getSharedPreferences("starview_keep", 0);
    }

    public final void keepNewest(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        final Context app = ctx.getApplicationContext();
        Galaxy7 g = Galaxy7.Companion.current();
        if (g == null) {
            keepNewest$toast(app, "Not connected to the comma");
        } else {
            g.get("/api/recordings/local", new Function2() { // from class: com.starpilot.starview.KeepDrive$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Unit keepNewest$lambda$3;
                    keepNewest$lambda$3 = KeepDrive.keepNewest$lambda$3(app, (JSONObject) obj, (String) obj2);
                    return keepNewest$lambda$3;
                }
            });
        }
    }

    private static final void keepNewest$toast(Context app, String s) {
        Toast.makeText(app, s, 1).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit keepNewest$lambda$3(Context $app, JSONObject o, String err) {
        JSONArray a;
        Object maxElem$iv;
        if (o != null && (a = o.optJSONArray("routes")) != null) {
            Iterable $this$mapNotNull$iv = RangesKt.until(0, a.length());
            Collection destination$iv$iv = new ArrayList();
            Iterator<Integer> it = $this$mapNotNull$iv.iterator();
            while (it.hasNext()) {
                int element$iv$iv$iv = ((IntIterator) it).nextInt();
                JSONObject optJSONObject = a.optJSONObject(element$iv$iv$iv);
                if (optJSONObject != null) {
                    destination$iv$iv.add(optJSONObject);
                }
            }
            Iterable $this$maxByOrNull$iv = (List) destination$iv$iv;
            Iterator iterator$iv = $this$maxByOrNull$iv.iterator();
            if (iterator$iv.hasNext()) {
                maxElem$iv = iterator$iv.next();
                if (iterator$iv.hasNext()) {
                    JSONObject it2 = (JSONObject) maxElem$iv;
                    double maxValue$iv = it2.optDouble("startTime", 0.0d);
                    do {
                        Object e$iv = iterator$iv.next();
                        JSONObject it3 = (JSONObject) e$iv;
                        double v$iv = it3.optDouble("startTime", 0.0d);
                        if (Double.compare(maxValue$iv, v$iv) < 0) {
                            maxElem$iv = e$iv;
                            maxValue$iv = v$iv;
                        }
                    } while (iterator$iv.hasNext());
                }
            } else {
                maxElem$iv = null;
            }
            JSONObject r = (JSONObject) maxElem$iv;
            if (r != null) {
                if (r.optBoolean("preserved")) {
                    keepNewest$toast($app, "This drive is already kept");
                    return Unit.INSTANCE;
                }
                KeepDrive keepDrive = INSTANCE;
                Intrinsics.checkNotNull($app);
                String optString = r.optString("routeId");
                Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
                keepDrive.request($app, optString);
                keepNewest$toast($app, "This drive will be kept (Galaxy saves it once you park)");
                INSTANCE.applyPending($app);
                return Unit.INSTANCE;
            }
        }
        keepNewest$toast($app, err == null ? "No drive found" : err);
        return Unit.INSTANCE;
    }

    public final void request(Context ctx, String routeId) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(routeId, "routeId");
        Set<String> stringSet = prefs(ctx).getStringSet("pending", SetsKt.emptySet());
        Intrinsics.checkNotNull(stringSet);
        Set s = CollectionsKt.toMutableSet(stringSet);
        s.add(routeId);
        prefs(ctx).edit().putStringSet("pending", s).apply();
    }

    public final void applyPending(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Galaxy7 g = Galaxy7.Companion.current();
        if (g == null) {
            return;
        }
        final Context app = ctx.getApplicationContext();
        Intrinsics.checkNotNull(app);
        Set<String> stringSet = prefs(app).getStringSet("pending", SetsKt.emptySet());
        Intrinsics.checkNotNull(stringSet);
        for (final String id : CollectionsKt.toList(stringSet)) {
            JSONObject put = new JSONObject().put("action", "preserve").put("routeId", id).put("preserved", true);
            Intrinsics.checkNotNullExpressionValue(put, "put(...)");
            g.post("/api/recordings/action", put, new Function2() { // from class: com.starpilot.starview.KeepDrive$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Unit applyPending$lambda$4;
                    applyPending$lambda$4 = KeepDrive.applyPending$lambda$4(app, id, (JSONObject) obj, (String) obj2);
                    return applyPending$lambda$4;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit applyPending$lambda$4(Context $app, String $id, JSONObject o, String err) {
        boolean drop = false;
        boolean later = err != null && new Regex("turn off the vehicle|active segment|refresh|sign in|timeout|connect", RegexOption.IGNORE_CASE).containsMatchIn(err);
        if (o != null || (err != null && !later)) {
            drop = true;
        }
        if (drop) {
            KeepDrive keepDrive = INSTANCE;
            Intrinsics.checkNotNull($app);
            Set<String> stringSet = keepDrive.prefs($app).getStringSet("pending", SetsKt.emptySet());
            Intrinsics.checkNotNull(stringSet);
            Set s = CollectionsKt.toMutableSet(stringSet);
            s.remove($id);
            INSTANCE.prefs($app).edit().putStringSet("pending", s).apply();
            if (o == null) {
                Toast.makeText($app, "Couldn't keep the drive: " + err, 1).show();
            }
        }
        return Unit.INSTANCE;
    }
}
