package com.starpilot.starview;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;

/* compiled from: JournalPages.kt */
public final class JournalPagesKt {
    static final SimpleDateFormat dayFmt = new SimpleDateFormat("EEE MMM d, h:mm a", Locale.US);
    static final SimpleDateFormat timeFmt = new SimpleDateFormat("h:mm a", Locale.US);

    /* JADX INFO: Access modifiers changed from: private */
    public static final String miles(double m) {
        if (m < 1609.0d) {
            return (Math.round((m / 0.3048d) / 10) * 10) + " ft";
        }
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String format = String.format(Locale.US, "%.1f mi", Arrays.copyOf(new Object[]{Double.valueOf(m / 1609.344d)}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String dur(double sec) {
        if (sec < 3600.0d) {
            return Math.round(sec / 60) + " min";
        }
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        double d = 3600;
        String format = String.format(Locale.US, "%dh %02dm", Arrays.copyOf(new Object[]{Integer.valueOf((int) (sec / d)), Integer.valueOf((int) Math.round((sec % d) / 60))}, 2));
        Intrinsics.checkNotNullExpressionValue(format, "format(...)");
        return format;
    }
}
