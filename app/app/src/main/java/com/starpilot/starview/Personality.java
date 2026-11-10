package com.starpilot.starview;

import java.util.Locale;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BigUiViews.kt */
public final class Personality {
    public static final Personality INSTANCE = new Personality();
    private static final String[] NAMES = {"aggressive", "standard", "relaxed"};
    private static final String[] LABELS = {"Aggressive", "Standard", "Relaxed"};

    private Personality() {
    }

    public final String[] getNAMES() {
        return NAMES;
    }

    public final String[] getLABELS() {
        return LABELS;
    }

    public final int index(String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        String[] strArr = NAMES;
        String lowerCase = name.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        int it = ArraysKt.indexOf(strArr, lowerCase);
        if (it < 0) {
            return 1;
        }
        return it;
    }

    public final int next(String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        return (index(name) + 2) % 3;
    }
}
