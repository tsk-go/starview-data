package com.starpilot.starview;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: DeviceHubs.kt */
public final class ToolLines {
    public static final ToolLines INSTANCE = new ToolLines();

    private ToolLines() {
    }

    public final String endpoint(String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        if (Intrinsics.areEqual(path, "/")) {
            return "/api/drives/stats";
        }
        if (StringsKt.contains((CharSequence) path, (CharSequence) "recording", false)) {
            return "/api/recordings/local";
        }
        if (StringsKt.contains((CharSequence) path, (CharSequence) "manage_models", false)) {
            return "/api/models/status";
        }
        if (StringsKt.contains((CharSequence) path, (CharSequence) "logs", false)) {
            return "/api/crash-reports";
        }
        if (StringsKt.contains((CharSequence) path, (CharSequence) "bluetooth", false)) {
            return "/api/bluetooth/status";
        }
        if (StringsKt.contains((CharSequence) path, (CharSequence) "galaxy", false)) {
            return "/api/galaxy/status";
        }
        if (StringsKt.contains((CharSequence) path, (CharSequence) "navigation", false)) {
            return "/api/navigation/offline";
        }
        return null;
    }

    /* recovered by hand from the bytecode (jadx inverted the "kept" count and lost branches) */
    public final String line(String path, JSONObject o) {
        Object res;
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(o, "o");
        try {
            Result.Companion companion = Result.Companion;
            String str = null;
            if (Intrinsics.areEqual(path, "/")) {
                JSONObject t = o.optJSONObject("totals");
                if (t != null) {
                    Double valueOf = Double.valueOf(t.optDouble("distanceMeters", Double.NaN));
                    double it = valueOf.doubleValue();
                    if (Double.isInfinite(it) || Double.isNaN(it)) {
                        valueOf = null;
                    }
                    Long mi = valueOf != null ? Long.valueOf(Math.round(valueOf.doubleValue() / 1609.344d)) : null;
                    Integer n = Integer.valueOf(t.optInt("drives", -1));
                    if (n.intValue() < 0) {
                        n = null;
                    }
                    String[] strArr = new String[2];
                    strArr[0] = n != null ? n.intValue() + " drives" : null;
                    strArr[1] = mi != null ? mi.longValue() + " mi" : null;
                    String joined = CollectionsKt.joinToString(CollectionsKt.listOfNotNull(strArr), " · ", "", "", -1, "...", null);
                    str = StringsKt.isBlank(joined) ? null : joined;
                }
            } else if (StringsKt.contains((CharSequence) path, (CharSequence) "recording", false)) {
                JSONArray a = o.optJSONArray("routes");
                if (a != null) {
                    int kept = 0;
                    Iterable range = RangesKt.until(0, a.length());
                    if (!(range instanceof Collection) || !((Collection) range).isEmpty()) {
                        Iterator it2 = range.iterator();
                        while (it2.hasNext()) {
                            JSONObject r = a.optJSONObject(((IntIterator) it2).nextInt());
                            if (r != null && r.optBoolean("preserved")) {
                                kept++;
                                if (kept < 0) {
                                    CollectionsKt.throwCountOverflow();
                                }
                            }
                        }
                    }
                    str = a.length() + " drives" + (kept > 0 ? " · " + kept + " kept" : "");
                }
            } else if (StringsKt.contains((CharSequence) path, (CharSequence) "manage_models", false)) {
                String name = o.optString("loadedName");
                if (StringsKt.isBlank(name)) {
                    name = o.optString("loadedId");
                }
                if (StringsKt.isBlank(name)) {
                    name = null;
                }
                str = name != null ? "Running: " + name : null;
            } else if (StringsKt.contains((CharSequence) path, (CharSequence) "logs", false)) {
                Integer count = Integer.valueOf(o.optInt("reportCount", -1));
                if (count.intValue() < 0) {
                    count = null;
                }
                if (count != null) {
                    int c = count.intValue();
                    str = c == 0 ? "No crash reports" : c + " crash report" + (c == 1 ? "" : "s");
                }
            } else if (StringsKt.contains((CharSequence) path, (CharSequence) "bluetooth", false)) {
                if (!o.optBoolean("powered", false)) {
                    str = "Bluetooth off";
                } else {
                    JSONArray a2 = o.optJSONArray("devices");
                    if (a2 != null) {
                        int on = 0;
                        Iterable range2 = RangesKt.until(0, a2.length());
                        if (!(range2 instanceof Collection) || !((Collection) range2).isEmpty()) {
                            Iterator it5 = range2.iterator();
                            while (it5.hasNext()) {
                                JSONObject d = a2.optJSONObject(((IntIterator) it5).nextInt());
                                if (d != null && d.optBoolean("connected")) {
                                    on++;
                                    if (on < 0) {
                                        CollectionsKt.throwCountOverflow();
                                    }
                                }
                            }
                        }
                        str = on > 0 ? on + " connected" : "On · " + a2.length() + " known";
                    }
                }
            } else if (StringsKt.contains((CharSequence) path, (CharSequence) "galaxy", false)) {
                str = o.optBoolean("paired") ? "Remote access paired" : "Remote access not paired";
            } else if (StringsKt.contains((CharSequence) path, (CharSequence) "navigation", false)) {
                Integer num;
                JSONArray arr = o.optJSONArray("areas");
                if (arr != null) {
                    num = Integer.valueOf(arr.length());
                } else {
                    JSONObject obj = o.optJSONObject("areas");
                    num = obj != null ? Integer.valueOf(obj.length()) : null;
                }
                if (num != null) {
                    int n2 = num.intValue();
                    str = n2 + " offline map area" + (n2 == 1 ? "" : "s");
                }
            }
            res = KtCompat.resultOf(str);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            res = KtCompat.resultOf(ResultKt.createFailure(th));
        }
        return (String) (KtCompat.isFailure(res) ? null : res);
    }
}
