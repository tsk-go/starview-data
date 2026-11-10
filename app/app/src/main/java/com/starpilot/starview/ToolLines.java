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
        if (StringsKt.contains$default((CharSequence) path, (CharSequence) "recording", false, 2, (Object) null)) {
            return "/api/recordings/local";
        }
        if (StringsKt.contains$default((CharSequence) path, (CharSequence) "manage_models", false, 2, (Object) null)) {
            return "/api/models/status";
        }
        if (StringsKt.contains$default((CharSequence) path, (CharSequence) "logs", false, 2, (Object) null)) {
            return "/api/crash-reports";
        }
        if (StringsKt.contains$default((CharSequence) path, (CharSequence) "bluetooth", false, 2, (Object) null)) {
            return "/api/bluetooth/status";
        }
        if (StringsKt.contains$default((CharSequence) path, (CharSequence) "galaxy", false, 2, (Object) null)) {
            return "/api/galaxy/status";
        }
        if (StringsKt.contains$default((CharSequence) path, (CharSequence) "navigation", false, 2, (Object) null)) {
            return "/api/navigation/offline";
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:195:0x0352 A[Catch: all -> 0x0380, TryCatch #0 {all -> 0x0380, blocks: (B:33:0x037a, B:188:0x032f, B:190:0x0335, B:192:0x033b, B:193:0x033f, B:195:0x0352, B:198:0x035e, B:201:0x0344, B:203:0x034a), top: B:187:0x032f }] */
    /* JADX WARN: Removed duplicated region for block: B:200:0x0377  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x039a  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x039c  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0173 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0167 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String line(String path, JSONObject o) {
        Object obj;
        Object m1191constructorimpl;
        String str;
        Integer num;
        int length;
        String str2;
        int i;
        String str3;
        int i2;
        int i3;
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(o, "o");
        try {
            Result.Companion companion = Result.Companion;
            ToolLines toolLines = this;
            int i4 = 0;
            if (Intrinsics.areEqual(path, "/")) {
                JSONObject t = o.optJSONObject("totals");
                if (t != null) {
                    Double valueOf = Double.valueOf(t.optDouble("distanceMeters", Double.NaN));
                    double it = valueOf.doubleValue();
                    if (!((Double.isInfinite(it) || Double.isNaN(it)) ? false : true)) {
                        valueOf = null;
                    }
                    Long mi = valueOf != null ? Long.valueOf(Math.round(valueOf.doubleValue() / 1609.344d)) : null;
                    Integer n = Integer.valueOf(t.optInt("drives", -1));
                    if ((n.intValue() >= 0 ? 1 : 0) == 0) {
                        n = null;
                    }
                    String[] strArr = new String[2];
                    strArr[0] = n != null ? n.intValue() + " drives" : null;
                    strArr[1] = mi != null ? mi.longValue() + " mi" : null;
                    String joinToString$default = CollectionsKt.joinToString$default(CollectionsKt.listOfNotNull((Object[]) strArr), " · ", null, null, 0, null, null, 62, null);
                    if (StringsKt.isBlank(joinToString$default)) {
                        joinToString$default = null;
                    }
                    str = joinToString$default;
                } else {
                    str = null;
                }
                obj = null;
            } else {
                try {
                    if (StringsKt.contains$default((CharSequence) path, (CharSequence) "recording", false, 2, (Object) null)) {
                        JSONArray a = o.optJSONArray("routes");
                        if (a != null) {
                            int i5 = 0;
                            Iterable $this$count$iv = RangesKt.until(0, a.length());
                            if (!($this$count$iv instanceof Collection) || !((Collection) $this$count$iv).isEmpty()) {
                                int count$iv = 0;
                                Iterator it2 = $this$count$iv.iterator();
                                while (it2.hasNext()) {
                                    int element$iv = ((IntIterator) it2).nextInt();
                                    JSONObject optJSONObject = a.optJSONObject(element$iv);
                                    if (optJSONObject != null) {
                                        i2 = i5;
                                        if (optJSONObject.optBoolean("preserved")) {
                                            i3 = 1;
                                            if (i3 == 0) {
                                                count$iv++;
                                                if (count$iv < 0) {
                                                    CollectionsKt.throwCountOverflow();
                                                }
                                                i5 = i2;
                                            } else {
                                                i5 = i2;
                                            }
                                        }
                                    } else {
                                        i2 = i5;
                                    }
                                    i3 = 0;
                                    if (i3 == 0) {
                                    }
                                }
                                i4 = count$iv;
                            }
                            int preserved = i4;
                            str = a.length() + " drives" + (preserved > 0 ? " · " + preserved + " kept" : "");
                        } else {
                            str = null;
                        }
                        obj = null;
                    } else {
                        Object obj2 = null;
                        try {
                            if (StringsKt.contains$default((CharSequence) path, (CharSequence) "manage_models", false, 2, (Object) null)) {
                                String optString = o.optString("loadedName");
                                if (StringsKt.isBlank(optString)) {
                                    optString = o.optString("loadedId");
                                }
                                String str4 = optString;
                                if (StringsKt.isBlank(str4)) {
                                    str4 = null;
                                }
                                String it3 = str4;
                                if (it3 != null) {
                                    str = "Running: " + it3;
                                    obj = null;
                                } else {
                                    str = null;
                                    obj = null;
                                }
                            } else if (StringsKt.contains$default((CharSequence) path, (CharSequence) "logs", false, 2, (Object) null)) {
                                Integer valueOf2 = Integer.valueOf(o.optInt("reportCount", -1));
                                if (!(valueOf2.intValue() >= 0)) {
                                    valueOf2 = null;
                                }
                                if (valueOf2 != null) {
                                    int it4 = valueOf2.intValue();
                                    if (it4 == 0) {
                                        str3 = "No crash reports";
                                    } else {
                                        if (it4 != 1) {
                                            str2 = "s";
                                        }
                                        str3 = it4 + " crash report" + str2;
                                    }
                                    str = str3;
                                } else {
                                    str = null;
                                }
                                obj = null;
                            } else if (StringsKt.contains$default((CharSequence) path, (CharSequence) "bluetooth", false, 2, (Object) null)) {
                                if (o.optBoolean("powered", false)) {
                                    JSONArray a2 = o.optJSONArray("devices");
                                    if (a2 != null) {
                                        Iterable $this$count$iv2 = RangesKt.until(0, a2.length());
                                        if (($this$count$iv2 instanceof Collection) && ((Collection) $this$count$iv2).isEmpty()) {
                                            i = 0;
                                        } else {
                                            int count$iv2 = 0;
                                            Iterator it5 = $this$count$iv2.iterator();
                                            while (it5.hasNext()) {
                                                int element$iv2 = ((IntIterator) it5).nextInt();
                                                JSONObject optJSONObject2 = a2.optJSONObject(element$iv2);
                                                if (((optJSONObject2 == null || !optJSONObject2.optBoolean("connected")) ? 0 : 1) != 0 && (count$iv2 = count$iv2 + 1) < 0) {
                                                    CollectionsKt.throwCountOverflow();
                                                }
                                            }
                                            i = count$iv2;
                                        }
                                        int on = i;
                                        str = (on > 0 ? new StringBuilder().append(on).append(" connected") : new StringBuilder().append("On · ").append(a2.length()).append(" known")).toString();
                                    } else {
                                        str = null;
                                    }
                                } else {
                                    str = "Bluetooth off";
                                }
                                obj = null;
                            } else {
                                obj2 = null;
                                if (StringsKt.contains$default((CharSequence) path, (CharSequence) "galaxy", false, 2, (Object) null)) {
                                    str = o.optBoolean("paired") ? "Remote access paired" : "Remote access not paired";
                                    obj = null;
                                } else {
                                    obj = null;
                                    try {
                                        if (StringsKt.contains$default((CharSequence) path, (CharSequence) "navigation", false, 2, (Object) null)) {
                                            JSONArray optJSONArray = o.optJSONArray("areas");
                                            if (optJSONArray != null) {
                                                length = optJSONArray.length();
                                            } else {
                                                JSONObject optJSONObject3 = o.optJSONObject("areas");
                                                if (optJSONObject3 != null) {
                                                    length = optJSONObject3.length();
                                                } else {
                                                    num = null;
                                                    if (num == null) {
                                                        int it6 = num.intValue();
                                                        if (it6 != 1) {
                                                            str2 = "s";
                                                        }
                                                        str = it6 + " offline map area" + str2;
                                                    } else {
                                                        str = null;
                                                    }
                                                }
                                            }
                                            num = Integer.valueOf(length);
                                            if (num == null) {
                                            }
                                        } else {
                                            str = null;
                                        }
                                    } catch (Throwable th) {
                                        th = th;
                                        Result.Companion companion2 = Result.Companion;
                                        m1191constructorimpl = Result.m1191constructorimpl(ResultKt.createFailure(th));
                                        return (String) (Result.m1197isFailureimpl(m1191constructorimpl) ? obj : m1191constructorimpl);
                                    }
                                }
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            obj = obj2;
                            Result.Companion companion22 = Result.Companion;
                            m1191constructorimpl = Result.m1191constructorimpl(ResultKt.createFailure(th));
                            return (String) (Result.m1197isFailureimpl(m1191constructorimpl) ? obj : m1191constructorimpl);
                        }
                    }
                } catch (Throwable th3) {
                    th = th3;
                    obj = null;
                }
            }
            m1191constructorimpl = Result.m1191constructorimpl(str);
        } catch (Throwable th4) {
            th = th4;
            obj = null;
        }
        return (String) (Result.m1197isFailureimpl(m1191constructorimpl) ? obj : m1191constructorimpl);
    }
}
