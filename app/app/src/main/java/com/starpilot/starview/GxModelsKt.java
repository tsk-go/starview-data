package com.starpilot.starview;

import android.view.View;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: GxModels.kt */
public final class GxModelsKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final String gxModelsHardwareLabel(JSONObject m) {
        Object v = m.opt("requiresGpu");
        return Intrinsics.areEqual(v, (Object) true) ? "GPU · External" : Intrinsics.areEqual(v, (Object) false) ? "Comma · On-device" : "Hardware unknown";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean gxModelsMatchesHardware(JSONObject m, String filter) {
        if (Intrinsics.areEqual(filter, "gpu")) {
            return Intrinsics.areEqual(m.opt("requiresGpu"), (Object) true);
        }
        if (Intrinsics.areEqual(filter, "comma")) {
            return Intrinsics.areEqual(m.opt("requiresGpu"), (Object) false);
        }
        return true;
    }

    private static final Double gxModelsSafeBytes(JSONObject m, String k) {
        Object opt = m.opt(k);
        Number v = opt instanceof Number ? (Number) opt : null;
        if (v == null) {
            return null;
        }
        double d = v.doubleValue();
        if (d <= 0.0d) {
            return null;
        }
        if (!(d == Math.floor(d)) || d > 9.007199254740991E15d) {
            return null;
        }
        return Double.valueOf(d);
    }

    private static final String gxModelsFormatBytes(double v) {
        String format;
        if (v >= 1.0E9d) {
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            format = String.format(Locale.US, "%.2f GB", Arrays.copyOf(new Object[]{Double.valueOf(v / 1.0E9d)}, 1));
        } else {
            StringCompanionObject stringCompanionObject2 = StringCompanionObject.INSTANCE;
            format = String.format(Locale.US, "%.1f MB", Arrays.copyOf(new Object[]{Double.valueOf(v / 1000000.0d)}, 1));
        }
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format;
    }

    /* VF: from Vineflower (jadx: Code restructure failed;incorrect) */
    public static final String gxModelsFileSizeText(JSONObject var0) {
        String var11 = null; String var8 = null; // declarations Vineflower left out
        Double var4 = gxModelsSafeBytes(var0, "fileSizeBytes");
        Double var5 = gxModelsSafeBytes(var0, "declaredSizeBytes");
        Double var3 = gxModelsSafeBytes(var0, "downloadedBytes");
        if (var0.optBoolean("partial") || Intrinsics.areEqual(var0.optString("sizeStatus"), "partial")) {
            label39: {
                var11 = "unknown";
                if (var3 != null) {
                    String var9 = gxModelsFormatBytes(var3.doubleValue());
                    var8 = var9;
                    if (var9 != null) {
                        break label39;
                    }
                }

                var8 = "unknown";
            }

            String var10 = var11;
            if (var5 != null) {
                double var1 = var5.doubleValue();
                var10 = gxModelsFormatBytes(var1);
                if (var10 == null) {
                    var10 = var11;
                }
            }

            return "Partial: " + var8 + " / " + var10;
        } else if (var4 != null && var5 != null && !Intrinsics.areEqual(var4, var5)) {
            String var7 = gxModelsFormatBytes(var4);
            return var7 + " · size mismatch";
        } else if (var4 != null) {
            return gxModelsFormatBytes(var4);
        } else if (var5 != null) {
            String var6 = gxModelsFormatBytes(var5);
            return var6 + " · declared";
        } else {
            return "Unavailable";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String gxModelsText(JSONObject o, String k, String def) {
        if (o == null || !o.has(k) || o.isNull(k)) {
            return def;
        }
        String optString = o.optString(k, def);
        Intrinsics.checkNotNullExpressionValue(optString, "optString(...)");
        return optString;
    }

    static /* synthetic */ String gxModelsText$default(JSONObject jSONObject, String str, String str2, int i, Object obj) {
        if ((i & 4) != 0) {
            str2 = "";
        }
        return gxModelsText(jSONObject, str, str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final long gxModelsReleasedTs(String s) {
        String t = StringsKt.trim((CharSequence) s).toString();
        int i = 1;
        if (t.length() == 0) {
            return 0L;
        }
        try {
            Result.Companion companion = Result.Companion;
            return Instant.parse(t).toEpochMilli();
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            KtCompat.resultOf(ResultKt.createFailure(th));
            try {
                Result.Companion companion3 = Result.Companion;
                return OffsetDateTime.parse(t).toInstant().toEpochMilli();
            } catch (Throwable th2) {
                Result.Companion companion4 = Result.Companion;
                KtCompat.resultOf(ResultKt.createFailure(th2));
                try {
                    Result.Companion companion5 = Result.Companion;
                    return LocalDateTime.parse(t).toInstant(ZoneOffset.UTC).toEpochMilli();
                } catch (Throwable th3) {
                    Result.Companion companion6 = Result.Companion;
                    KtCompat.resultOf(ResultKt.createFailure(th3));
                    try {
                        Result.Companion companion7 = Result.Companion;
                        return LocalDate.parse(StringsKt.take(t, 10)).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
                    } catch (Throwable th4) {
                        Result.Companion companion8 = Result.Companion;
                        KtCompat.resultOf(ResultKt.createFailure(th4));
                        Iterator it = CollectionsKt.listOf((Object[]) new String[]{"yyyy/MM/dd", "MM/dd/yyyy", "M/d/yyyy", "MMMM d, yyyy", "MMM d, yyyy", "d MMMM yyyy", "d MMM yyyy"}).iterator();
                        while (it.hasNext()) {
                            String p = (String) it.next();
                            try {
                                Result.Companion companion9 = Result.Companion;
                                return LocalDate.parse(t, DateTimeFormatter.ofPattern(p, Locale.US)).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
                            } catch (Throwable th5) {
                                Result.Companion companion10 = Result.Companion;
                                KtCompat.resultOf(ResultKt.createFailure(th5));
                            }
                        }
                        for (String p2 : ((String) CollectionsKt.listOf((Object[]) new String[]{"MMMM yyyy", "MMM yyyy", "yyyy-MM"}))) {
                            try {
                                Result.Companion companion11 = Result.Companion;
                                return YearMonth.parse(t, DateTimeFormatter.ofPattern(p2, Locale.US)).atDay(i).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
                            } catch (Throwable th6) {
                                Result.Companion companion12 = Result.Companion;
                                KtCompat.resultOf(ResultKt.createFailure(th6));
                            }
                        }
                        return 0L;
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int gxModelsNatCompare(String a, String b) {
        int i = 0;
        int j = 0;
        while (i < a.length() && j < b.length()) {
            char ca = a.charAt(i);
            char cb = b.charAt(j);
            if (Character.isDigit(ca) && Character.isDigit(cb)) {
                int ie = i;
                while (ie < a.length() && Character.isDigit(a.charAt(ie))) {
                    ie++;
                }
                int je = j;
                while (je < b.length() && Character.isDigit(b.charAt(je))) {
                    je++;
                }
                String substring = a.substring(i, ie);
                Intrinsics.checkNotNullExpressionValue(substring, "substring(...)");
                String na = StringsKt.trimStart(substring, '0');
                String substring2 = b.substring(j, je);
                Intrinsics.checkNotNullExpressionValue(substring2, "substring(...)");
                String nb = StringsKt.trimStart(substring2, '0');
                if (na.length() != nb.length()) {
                    return na.length() - nb.length();
                }
                int c = na.compareTo(nb);
                if (c != 0) {
                    return c;
                }
                i = ie;
                j = je;
            } else {
                int c2 = Intrinsics.compare((int) Character.toLowerCase(ca), (int) Character.toLowerCase(cb));
                if (c2 != 0) {
                    return c2;
                }
                i++;
                j++;
            }
        }
        return (a.length() - i) - (b.length() - j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void gxModelsEnable(View v, boolean enabled) {
        v.setEnabled(enabled);
        v.setClickable(enabled);
        v.setAlpha(enabled ? 1.0f : 0.4f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List<JSONObject> gxModelsObjects(JSONArray $this$gxModelsObjects) {
        if ($this$gxModelsObjects == null) {
            return CollectionsKt.emptyList();
        }
        Iterable $this$mapNotNull$iv = RangesKt.until(0, $this$gxModelsObjects.length());
        Collection destination$iv$iv = new ArrayList();
        Iterator<Integer> it = $this$mapNotNull$iv.iterator();
        while (it.hasNext()) {
            int element$iv$iv$iv = ((IntIterator) it).nextInt();
            JSONObject optJSONObject = $this$gxModelsObjects.optJSONObject(element$iv$iv$iv);
            if (optJSONObject != null) {
                destination$iv$iv.add(optJSONObject);
            }
        }
        return (List) destination$iv$iv;
    }
}
