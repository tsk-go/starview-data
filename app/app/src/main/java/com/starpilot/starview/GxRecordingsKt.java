package com.starpilot.starview;

import android.graphics.Color;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.Regex;
import kotlin.text.StringsKt;

/* compiled from: GxRecordings.kt */
public final class GxRecordingsKt {
    private static final int GxRecordingsMaxRendered = 250;
    private static final List<String> GxRecordingsMonths = CollectionsKt.listOf((Object[]) new String[]{"january", "february", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december"});
    private static final int GxRecordingsDanger = Color.rgb(255, 120, 120);

    /* JADX INFO: Access modifiers changed from: private */
    public static final String gxRecordingsNorm(String v) {
        String normalize = Normalizer.normalize(v == null ? "" : v, Normalizer.Form.NFKD);
        Intrinsics.checkNotNullExpressionValue(normalize, "normalize(...)");
        String replace = new Regex("\\p{M}").replace(normalize, "");
        Locale ROOT = Locale.ROOT;
        Intrinsics.checkNotNullExpressionValue(ROOT, "ROOT");
        String lowerCase = replace.toLowerCase(ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        return StringsKt.trim((CharSequence) new Regex("[^\\p{L}\\p{N}]+").replace(new Regex("(\\d+)(?:st|nd|rd|th)\\b").replace(lowerCase, "$1"), " ")).toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String gxRecordingsDuration(int seconds) {
        StringBuilder sb;
        int h = seconds / 3600;
        int m = (seconds % 3600) / 60;
        if (h > 0) {
            sb = new StringBuilder();
            sb = sb.append(h).append("h ");
        } else {
            sb = new StringBuilder();
        }
        return sb.append(m).append("m").toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String gxRecordingsBytes(double b) {
        String format;
        if (b <= 0.0d) {
            return "0 MB";
        }
        double mb = b / 1000000.0d;
        if (mb >= 1000.0d) {
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            format = String.format(Locale.US, "%.2f GB", Arrays.copyOf(new Object[]{Double.valueOf(mb / 1000)}, 1));
        } else {
            StringCompanionObject stringCompanionObject2 = StringCompanionObject.INSTANCE;
            format = String.format(Locale.US, "%.1f MB", Arrays.copyOf(new Object[]{Double.valueOf(mb)}, 1));
        }
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String gxRecordingsSafeName(String s) {
        String obj = StringsKt.trim((CharSequence) new Regex("[\\\\/:*?\"<>|]+").replace(s, "_")).toString();
        if (StringsKt.isBlank(obj)) {
            obj = "route";
        }
        return obj;
    }
}
