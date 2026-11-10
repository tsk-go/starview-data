package com.starpilot.starview;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.ranges.RangesKt;
import kotlin.sequences.SequencesKt;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.json.JSONObject;

/* compiled from: GxHome.kt */
public final class GxHomeKt {
    private static final DateTimeFormatter gxHomeDateFmt = DateTimeFormatter.ofPattern("MMM d", Locale.US);
    private static final DateTimeFormatter gxHomeTimeFmt = DateTimeFormatter.ofPattern("h:mm a", Locale.US);

    /* VF: from Vineflower (jadx: Removed duplicated region;incorrect) */
    public static final double gxHomeNum(Object var0) {
        double var1;
        double var3;
        boolean var6;
        label48: {
            var6 = true;
            var3 = 0.0;
            if (var0 != null && !Intrinsics.areEqual(var0, JSONObject.NULL)) {
                if (var0 instanceof Number) {
                    var1 = ((Number)var0).doubleValue();
                    break label48;
                }

                if (var0 instanceof Boolean) {
                    if ((Boolean)var0) {
                        var1 = 1.0;
                        break label48;
                    }
                } else if (var0 instanceof String) {
                    var0 = StringsKt.trim(var0).toString();
                    boolean var5;
                    if (var0.length() == 0) {
                        var5 = true;
                    } else {
                        var5 = false;
                    }

                    if (var5) {
                        var0 = "0";
                    }

                    Double var8 = StringsKt.toDoubleOrNull(var0);
                    if (var8 != null) {
                        var1 = var8;
                        break label48;
                    }
                }
            }

            var1 = 0.0;
        }

        boolean var9;
        if (!Double.isInfinite(var1) && !Double.isNaN(var1)) {
            var9 = var6;
        } else {
            var9 = false;
        }

        if (var9) {
            var3 = var1;
        }

        return var3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean gxHomeTruthy(Object v) {
        if (v == null || Intrinsics.areEqual(v, JSONObject.NULL)) {
            return false;
        }
        if (v instanceof Boolean) {
            return ((Boolean) v).booleanValue();
        }
        if (!(v instanceof Number)) {
            return !(v instanceof String) || ((CharSequence) v).length() > 0;
        }
        double d = ((Number) v).doubleValue();
        return (((d > 0.0d ? 1 : (d == 0.0d ? 0 : -1)) == 0) || Double.isNaN(d)) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String gxHomeInt(Object v) {
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String format = String.format(Locale.US, "%,d", Arrays.copyOf(new Object[]{Long.valueOf(Math.round(gxHomeNum(v)))}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String gxHomeDec(Object v) {
        String format = new DecimalFormat("#,##0.#", new DecimalFormatSymbols(Locale.US)).format(gxHomeNum(v));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format;
    }

    private static final double gxHomeClamp(Object v, double min, double max) {
        return RangesKt.coerceIn(gxHomeNum(v), min, max);
    }

    static /* synthetic */ double gxHomeClamp$default(Object obj, double d, double d2, int i, Object obj2) {
        if ((i & 2) != 0) {
            d = 0.0d;
        }
        if ((i & 4) != 0) {
            d2 = 100.0d;
        }
        return gxHomeClamp(obj, d, d2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String gxHomePct(Object v) {
        return Math.round(gxHomeClamp$default(v, 0.0d, 0.0d, 6, null)) + "%";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String gxHomeDuration(Object seconds) {
        long total = Math.max(0L, Math.round(gxHomeNum(seconds)));
        long j = 3600;
        long h = total / j;
        long m = (total % j) / 60;
        return (h > 0 ? new StringBuilder().append(h).append("h ") : new StringBuilder()).append(m).append("m").toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String gxHomeBytes(Object bytes) {
        StringBuilder append;
        String str;
        double v = Math.max(0.0d, gxHomeNum(bytes));
        if (v >= 1.073741824E9d) {
            append = new StringBuilder().append(gxHomeDec(Double.valueOf(v / 1.073741824E9d)));
            str = " GB";
        } else if (v >= 1048576.0d) {
            append = new StringBuilder().append(gxHomeDec(Double.valueOf(v / 1048576.0d)));
            str = " MB";
        } else {
            append = new StringBuilder().append(gxHomeInt(Double.valueOf(v / 1024)));
            str = " KB";
        }
        return append.append(str).toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String gxHomeFmt(double d) {
        if ((Double.isInfinite(d) || Double.isNaN(d)) ? false : true) {
            if ((d == Math.floor(d)) && Math.abs(d) < 1.0E15d) {
                return String.valueOf((long) d);
            }
        }
        String plainString = new BigDecimal(String.valueOf(d)).stripTrailingZeros().toPlainString();
        Intrinsics.checkNotNullExpressionValue(plainString, "toPlainString(...)");
        return plainString;
    }

    private static final LocalDateTime gxHomeParseDate(String s) {
        Object m1191constructorimpl;
        Object m1191constructorimpl2;
        Object m1191constructorimpl3;
        if (StringsKt.isBlank(s)) {
            return null;
        }
        try {
            Result.Companion companion = Result.Companion;
            m1191constructorimpl = KtCompat.resultOf(OffsetDateTime.parse(s).atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime());
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            m1191constructorimpl = KtCompat.resultOf(ResultKt.createFailure(th));
        }
        if (KtCompat.isFailure(m1191constructorimpl)) {
            m1191constructorimpl = null;
        }
        LocalDateTime localDateTime = (LocalDateTime) m1191constructorimpl;
        if (localDateTime != null) {
            return localDateTime;
        }
        try {
            Result.Companion companion3 = Result.Companion;
            m1191constructorimpl2 = KtCompat.resultOf(LocalDateTime.parse(s));
        } catch (Throwable th2) {
            Result.Companion companion4 = Result.Companion;
            m1191constructorimpl2 = KtCompat.resultOf(ResultKt.createFailure(th2));
        }
        if (KtCompat.isFailure(m1191constructorimpl2)) {
            m1191constructorimpl2 = null;
        }
        LocalDateTime localDateTime2 = (LocalDateTime) m1191constructorimpl2;
        if (localDateTime2 != null) {
            return localDateTime2;
        }
        try {
            Result.Companion companion5 = Result.Companion;
            m1191constructorimpl3 = KtCompat.resultOf(LocalDate.parse(s).atStartOfDay());
        } catch (Throwable th3) {
            Result.Companion companion6 = Result.Companion;
            m1191constructorimpl3 = KtCompat.resultOf(ResultKt.createFailure(th3));
        }
        return (LocalDateTime) (KtCompat.isFailure(m1191constructorimpl3) ? null : m1191constructorimpl3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String gxHomeRange(String startValue, String endValue) {
        if (StringsKt.isBlank(startValue)) {
            return "No drives yet";
        }
        LocalDateTime start = gxHomeParseDate(startValue);
        if (start == null) {
            return startValue;
        }
        LocalDateTime end = gxHomeParseDate(endValue);
        if (end == null || !end.isAfter(start)) {
            return start.format(gxHomeDateFmt) + ", " + start.format(gxHomeTimeFmt);
        }
        if (Intrinsics.areEqual(start.toLocalDate(), end.toLocalDate())) {
            return start.format(gxHomeDateFmt) + ", " + start.format(gxHomeTimeFmt) + "-" + end.format(gxHomeTimeFmt);
        }
        return start.format(gxHomeDateFmt) + ", " + start.format(gxHomeTimeFmt) + "-" + end.format(gxHomeDateFmt) + ", " + end.format(gxHomeTimeFmt);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int gxHomeNatural(String a, String b) {
        int c;
        Regex regex = new Regex("\\d+|\\D+");
        Locale ROOT = Locale.ROOT;
        Intrinsics.checkNotNullExpressionValue(ROOT, "ROOT");
        String lowerCase = a.toLowerCase(ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        List ra = SequencesKt.toList(SequencesKt.map(regex.findAll(lowerCase, 0), new Function1() { // from class: com.starpilot.starview.GxHomeKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                String gxHomeNatural$lambda$4;
                gxHomeNatural$lambda$4 = GxHomeKt.gxHomeNatural$lambda$4((MatchResult) obj);
                return gxHomeNatural$lambda$4;
            }
        }));
        Regex regex2 = new Regex("\\d+|\\D+");
        Locale ROOT2 = Locale.ROOT;
        Intrinsics.checkNotNullExpressionValue(ROOT2, "ROOT");
        String lowerCase2 = b.toLowerCase(ROOT2);
        Intrinsics.checkNotNullExpressionValue(lowerCase2, "toLowerCase(...)");
        List rb = SequencesKt.toList(SequencesKt.map(regex2.findAll(lowerCase2, 0), new Function1() { // from class: com.starpilot.starview.GxHomeKt$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                String gxHomeNatural$lambda$5;
                gxHomeNatural$lambda$5 = GxHomeKt.gxHomeNatural$lambda$5((MatchResult) obj);
                return gxHomeNatural$lambda$5;
            }
        }));
        int min = Math.min(ra.size(), rb.size());
        for (int i = 0; i < min; i++) {
            String x = (String) ra.get(i);
            String y = (String) rb.get(i);
            if (Character.isDigit(x.charAt(0)) && Character.isDigit(y.charAt(0))) {
                BigInteger bigIntegerOrNull = StringsKt.toBigIntegerOrNull(x);
                if (bigIntegerOrNull == null) {
                    bigIntegerOrNull = BigInteger.ZERO;
                }
                BigInteger bigIntegerOrNull2 = StringsKt.toBigIntegerOrNull(y);
                if (bigIntegerOrNull2 == null) {
                    bigIntegerOrNull2 = BigInteger.ZERO;
                }
                c = bigIntegerOrNull.compareTo(bigIntegerOrNull2);
            } else {
                c = x.compareTo(y);
            }
            if (c != 0) {
                return c;
            }
        }
        int i2 = ra.size();
        return i2 - rb.size();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String gxHomeNatural$lambda$4(MatchResult it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return it.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String gxHomeNatural$lambda$5(MatchResult it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return it.getValue();
    }
}
