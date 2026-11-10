package com.starpilot.starview;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.ranges.RangesKt;

/* compiled from: HubPower.kt */
public final class HubDelay {
    public static final int MAX = 300;
    public static final int MIN = 1;
    public static final HubDelay INSTANCE = new HubDelay();
    private static final List<Integer> PRESETS = CollectionsKt.listOf(new Integer[]{1, 5, 15, 30, 60, 120, 300});

    private HubDelay() {
    }

    public final List<Integer> getPRESETS() {
        return PRESETS;
    }

    public final int clamp(int m) {
        return RangesKt.coerceIn(m, 1, 300);
    }

    private static final int step$grid(int v, boolean up) {
        if (up) {
            if (v < 10) {
                return 1;
            }
            if (v < 60) {
                return 5;
            }
            return v < 120 ? 15 : 30;
        }
        if (v <= 10) {
            return 1;
        }
        if (v <= 60) {
            return 5;
        }
        return v <= 120 ? 15 : 30;
    }

    public final int step(int m, boolean up) {
        int g = step$grid(m, up);
        int n = (up ? (m / g) + 1 : (((m + g) - 1) / g) - 1) * g;
        return clamp(n);
    }

    public final String text(int m) {
        if (m < 60) {
            return m + " min";
        }
        if (m % 60 == 0) {
            return (m / 60) + " h";
        }
        return (m / 60) + " h " + (m % 60) + " min";
    }
}
